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
import uy.edu.um.telmovil.terminal.Terminal;
import uy.edu.um.telmovil.utils.ConstantesGenerales;

import com.google.gson.Gson;

public class BaseStation {
	
	private static final Gson gson = new Gson();
	
	
	private String radioBaseName = "Default";
	private ArrayList<Terminal> conexiones;
	private long maxConections = 10;
	private int conexionSocket = 2182;
	
	
	public BaseStation(long maxConections) {
		this.maxConections=maxConections;
		this.conexiones=new ArrayList<Terminal>();
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
			Terminal newTerminal = addTerminalToRadioBase(regMsg);
			RegistrationConfirmationMsg msg = new RegistrationConfirmationMsg();
			msg.setResponse(RegistrationConfirmationMsg.RESPONSE_OK);
			msg.setId(newTerminal.getMin());
			return msg;
		}else{
			return null;
			//throw exception not valid min!!!
		}
		
	}

	private Terminal addTerminalToRadioBase(RegistrationMsg regMsg) {
		if(this.conexiones.size()>= this.maxConections){
			//throw nomoreconectionavailables!!!
			
		}else{
			Terminal ter = new Terminal(regMsg.getMin(),regMsg.getMsn());
			if(!isInList(ter)){
				this.getConexiones().add(ter);
			}
			return ter;
		}
		return null;
	}
	
	
	private boolean isInList(Terminal ter) {
		for (Terminal terminal : conexiones) {
			if(terminal.getMin().equals(ter.getMin()))
				return true;
		}
		return false;
	}

	/**
	 * Valida contra una base de datos si no es robado...
	 * @param min
	 * @return
	 */
	private boolean validate(String min) {
		return true;
	}

	public static void main(String[] args) throws IOException {
	}

	public ArrayList<Terminal> getConexiones() {
		return conexiones;
	}

	public void setConexiones(ArrayList<Terminal> conexiones) {
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
