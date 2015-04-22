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
import uy.edu.um.telmovil.utils.ConstantesGenerales;

import com.google.gson.Gson;

public class BaseStation {
	
	private static final Gson gson = new Gson();
	
	
	private String radioBaseName = "Default";
	private ArrayList<Conection> conexiones;
	private long maxConections = 10;
	private int conexionSocket = 2182;
	
	
	public BaseStation(long maxConections) {
		this.maxConections=maxConections;
		this.conexiones=new ArrayList<Conection>();
	}
	
	@SuppressWarnings(value={"resource"})
	public void startListening() throws IOException{
		ServerSocket welcomeSocket = new ServerSocket(conexionSocket);
		System.out.println("RadioBase initiated, start listening...");
		String clientSentence;
		while(true)
		{
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("INFO: incoming conection from: "+connectionSocket.getInetAddress());
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			clientSentence = inFromClient.readLine();
			
			Msg mensaje = MsgManager.obtenerMsgFromString(clientSentence);
			Msg responseToClient = null;
			if(mensaje!=null && mensaje.getTipo().equals(ConstantesGenerales.TIPO_MSG_REGISTRATION_MSG)){
				responseToClient = doRegistration(gson.fromJson(clientSentence, RegistrationMsg.class));
			}

			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			outToClient.writeBytes(gson.toJson(responseToClient)+"\n");
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
	
	public int getConexionSocket() {
		return conexionSocket;
	}

	public void setConexionSocket(int conexionSocket) {
		this.conexionSocket = conexionSocket;
	}
}
