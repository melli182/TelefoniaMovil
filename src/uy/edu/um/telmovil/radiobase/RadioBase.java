package uy.edu.um.telmovil.radiobase;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import uy.edu.um.telmovil.msg.SimpleMsg;

import com.google.gson.Gson;

public class RadioBase {
	
	private static final Gson gson = new Gson();

	@SuppressWarnings(value={ "unused", "resource" })
	private void startListening() throws IOException{
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(2182);
		System.out.println("START LISTENING");
		while(true)
		{
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("INFO: conexion entrante desde: "+connectionSocket.getInetAddress());
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			
			SimpleMsg msg = gson.fromJson(clientSentence, SimpleMsg.class);
			
			System.out.println("Operacion solicitada: "+msg.getOperacion());
			
			if(msg.getOperacion().equals("iniciarLlamada")){
				System.out.println("Se solicito inicar llamada");
			}
//			outToClient.writeBytes(clientSentence);
//			outToClient.flush();
		
		}
	}
	
	public static void main(String[] args) throws IOException {
		RadioBase radio1 = new RadioBase();
		radio1.startListening();
	}
}
