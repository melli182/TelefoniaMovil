package uy.edu.um.telmovil.msg;

import uy.edu.um.telmovil.commons.ConstantesGenerales;

public class PRNMessage extends Msg {

	String imsi;

	public PRNMessage() {
		this.msg_type=ConstantesGenerales.TIPO_MSG_PRN;
	}
	
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	
}
