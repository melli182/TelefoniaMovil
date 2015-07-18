package uy.edu.um.telmovil.msg;

import uy.edu.um.telmovil.commons.ConstantesGenerales;

public class SRIMessage extends Msg {

	String msisdn;
	
	public SRIMessage() {
		this.msg_type=ConstantesGenerales.TIPO_MSG_SRI;
	}
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
}
