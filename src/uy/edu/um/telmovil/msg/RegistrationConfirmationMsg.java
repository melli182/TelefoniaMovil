package uy.edu.um.telmovil.msg;

import uy.edu.um.telmovil.commons.ConstantesGenerales;

public class RegistrationConfirmationMsg extends Msg {

	
	public final static String RESPONSE_OK = "OK";
	public final static String RESPONSE_FAIL = "FAIL";
	public final static String RESPONSE_INVALID = "INVALID";
	
	private String response;
	private String id;
 
	public String getResponse() {
		return response;
	}
	
	public void setResponse(String response) {
		this.response = response;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public RegistrationConfirmationMsg() {
		this.setTipo(ConstantesGenerales.TIPO_MSG_REGISTRATION_CONFIRMATION_MSG);
	}
}
