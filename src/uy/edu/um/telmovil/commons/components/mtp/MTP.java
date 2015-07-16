package uy.edu.um.telmovil.commons.components.mtp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import uy.edu.um.telmovil.commons.components.MTPUser;
import uy.edu.um.telmovil.mgr.MsgManager;
import uy.edu.um.telmovil.msg.Msg;

import com.google.gson.Gson;

/**
 * Componente donde se agrupa toda la logica de comunicacion. Todos los
 * componentes de la aplicacion que deseen comunicarse con otros deberan utilzar
 * este componente.
 *
 */
public class MTP implements Runnable {

	private int SSN;
	private MTPUser mtpUser;

	private final Gson gson = new Gson();

	private int sendingSocket;
	private int receivingSocket;
	private String host = "localhost";
	private long MTP_ID;

	public MTP(int sendingScoket, int recSocket) {
		this.sendingSocket = sendingScoket;
		this.receivingSocket = recSocket;
	}

	@Override
	public void run() {
		try {
			this.listen();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	public String send(Object msg) {
		try {
			@SuppressWarnings("resource")
			Socket clientSocket = new Socket(host, sendingSocket);
			DataOutputStream outToServer = new DataOutputStream(
					clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			String mensaje = gson.toJson(msg);
			outToServer.writeBytes(mensaje + '\n');
			String response = inFromServer.readLine();
			return response;
		} catch (IOException e) {
			return null;
		}
	}

	@SuppressWarnings("unused")
	public void listen() {
		try {
			while (true) {
				ServerSocket welcomeSocket = new ServerSocket(receivingSocket);
				System.out.println("MTP started, start listening...");
				String clientSentence;
				Socket connectionSocket = welcomeSocket.accept();
				System.out.println("INFO: incoming conection from: "
						+ connectionSocket.getInetAddress());
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));
				clientSentence = inFromClient.readLine();

				Msg mensaje = MsgManager.obtenerMsgFromString(clientSentence);
				this.mtpUser.MTPTransferIndication(mensaje, MTP_ID);
				inFromClient.close();
				welcomeSocket.close();

				// Object responseToClient = this.owner.msgReceived(mensaje);

				// DataOutputStream outToClient = new
				// DataOutputStream(connectionSocket.getOutputStream());
				// outToClient.writeBytes(gson.toJson(responseToClient)+"\n");
				// outToClient.flush();
			}
		} catch (IOException e) {
		}
	}

	public MTPUser getMtpUser() {
		return mtpUser;
	}

	public void setMtpUser(MTPUser mtpUser) {
		this.mtpUser = mtpUser;
	}

}
