package uy.edu.um.telmovil.radiobase;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import uy.edu.um.telmovil.mgr.MsgManager;
import uy.edu.um.telmovil.msg.Msg;
import uy.edu.um.telmovil.msg.RegistrationConfirmationMsg;
import uy.edu.um.telmovil.msg.RegistrationMsg;
import uy.edu.um.telmovil.msg.SimpleMsg;

import com.google.gson.Gson;

public class BaseStation {
	
	private static final Gson gson = new Gson();
	
	
	private String radioBaseName = "Default";
	private ArrayList<Conection> conexiones;
	private long maxConections = 10;
	
	@SuppressWarnings(value={ "unused", "resource" })
	private void startListening() throws IOException{
		ServerSocket welcomeSocket = new ServerSocket(2182);
		System.out.println("RadioBase initiated, start listening...");
		String clientSentence;
		while(true)
		{
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("INFO: incoming conection from: "+connectionSocket.getInetAddress());
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			
			
			Msg mensaje = MsgManager.obtenerMsgFromString(clientSentence);
			Msg responseToClient = null;
			if(mensaje instanceof RegistrationMsg){
				responseToClient = doRegistration(mensaje);
			}

			outToClient.writeBytes(gson.toJson(responseToClient));
			outToClient.flush();
		
		}
	}
	
	private Msg doRegistration(Msg mensaje) {
		RegistrationMsg regMsg = (RegistrationMsg) mensaje;
		if(validate(regMsg.getMin())){
			Conection newConection = addConectionToRadioBase(regMsg);
			RegistrationConfirmationMsg msg = new RegistrationConfirmationMsg();
			msg.setResponse(RegistrationConfirmationMsg.RESPONSE_OK);
			msg.setId(newConection.getId());
			return msg;
		}else{
			return null;
			//throw exception not valid min!!!
		}
		
	}

	private Conection addConectionToRadioBase(RegistrationMsg regMsg) {
		if(this.conexiones.size()>= this.maxConections){
			//throw nomoreconectionavailables!!!
			
		}else{
			Conection con = new Conection();
			con.setId(this.getRadioBaseName()+"-"+Long.toHexString(System.currentTimeMillis()));
			con.setMin(regMsg.getMin());
			con.setState(Conection.ESTADO_REGISTERED);
			return con;
		}
		return null;
	}

	private boolean validate(String min) {
		return true;
	}

	public static void main(String[] args) throws IOException {
		BaseStation radio1 = new BaseStation();
		radio1.startListening();
	}

	public ArrayList<Conection> getConexiones() {
		return conexiones;
	}

	public void setConexiones(ArrayList<Conection> conexiones) {
		this.conexiones = conexiones;
	}

	public String getRadioBaseName() {
		return radioBaseName;
	}

	public void setRadioBaseName(String radioBaseName) {
		this.radioBaseName = radioBaseName;
	}
}
