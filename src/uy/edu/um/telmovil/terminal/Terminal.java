package uy.edu.um.telmovil.terminal;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import uy.edu.um.telmovil.msg.SimpleMsg;

import com.google.gson.Gson;

public class Terminal {
	
	private static final Gson gson = new Gson();

	private void sendRequest(SimpleMsg msg) throws IOException{
		String sentence = gson.toJson(msg);
		String modifiedSentence;
		Socket clientSocket = new Socket("localhost", 2182);
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outToServer.writeBytes(sentence + '\n');
		
		System.out.println("Se envio al server: "+sentence);
		
		
//		modifiedSentence = inFromServer.readLine();
//		System.out.println("FROM SERVER: " + modifiedSentence);
		clientSocket.close();
	}
	
	public static void main(String[] args) throws IOException {
		Terminal terminal1 = new Terminal();
		SimpleMsg msg = new SimpleMsg();
		msg.setNumeroDestino("09090909");
		msg.setEsn("laraila");
		msg.setMin("asdad");
		msg.setScm("adsdasdas");
		msg.setOperacion("iniciarLlamada");
		terminal1.sendRequest(msg);
	}
}
