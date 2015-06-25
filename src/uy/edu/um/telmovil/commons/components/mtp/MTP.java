package uy.edu.um.telmovil.commons.components.mtp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import uy.edu.um.telmovil.commons.ConstantesGenerales;
import uy.edu.um.telmovil.commons.components.MTPUser;
import uy.edu.um.telmovil.mgr.MsgManager;
import uy.edu.um.telmovil.msg.Msg;
import uy.edu.um.telmovil.msg.RegistrationMsg;

import com.google.gson.Gson;


/**
 *	Componente donde se agrupa toda la logica de comunicacion.
 * 	Todos los componentes de la aplicacion que deseen comunicarse con otros
 * 	deberan utilzar este componente. 
 *
 */
public class MTP implements Runnable{
	
	private int SSN;
	private MTPUser mtpUser;
	
	private static final Gson gson = new Gson();
	
	private int sendingSocket;
	private int receivingSocket;
	private String host= "localhost";
	private long  MTP_ID;
	
	public MTP(int sendingScoket, int recSocket) {
		this.sendingSocket=sendingScoket;
		this.receivingSocket=recSocket;
	}
	
	@Override
	public void run() {
		try {
			this.listen();
		} catch (IOException e) {
			System.out.println("Error inicializando thread para escuchar puertos");
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	public String send(Object msg) throws UnknownHostException, IOException{
		@SuppressWarnings("resource")
		Socket clientSocket = new Socket(host, sendingSocket);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String mensaje = gson.toJson(msg);
		outToServer.writeBytes(mensaje + '\n');
		String response = inFromServer.readLine();
		System.out.println("Respuesta desde el servidor: "+(response));
		return response;
	}
	
	@SuppressWarnings("unused")
	public void listen() throws IOException{
		@SuppressWarnings("resource")
		ServerSocket welcomeSocket = new ServerSocket(receivingSocket);
		System.out.println("MTP started, start listening...");
		String clientSentence;
		while(true)
		{
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("INFO: incoming conection from: "+connectionSocket.getInetAddress());
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			clientSentence = inFromClient.readLine();
			
			Msg mensaje = MsgManager.obtenerMsgFromString(clientSentence);
			this.mtpUser.MTPTransferIndication(mensaje, MTP_ID);

			
//			Object responseToClient = this.owner.msgReceived(mensaje);

//			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
//			outToClient.writeBytes(gson.toJson(responseToClient)+"\n");
//			outToClient.flush();
		}
	}
	
	public MTPUser getMtpUser() {
		return mtpUser;
	}

	public void setMtpUser(MTPUser mtpUser) {
		this.mtpUser = mtpUser;
	}

}
