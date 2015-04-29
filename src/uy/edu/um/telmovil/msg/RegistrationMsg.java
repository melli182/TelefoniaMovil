package uy.edu.um.telmovil.msg;

import uy.edu.um.telmovil.commons.ConstantesGenerales;

public class RegistrationMsg extends Msg {

	private String min;
	private String msn;
	
	
	public RegistrationMsg() {
		this.setTipo(ConstantesGenerales.TIPO_MSG_REGISTRATION_MSG);
	}
	
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
}
