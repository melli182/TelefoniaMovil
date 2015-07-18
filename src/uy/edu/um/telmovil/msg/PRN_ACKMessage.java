package uy.edu.um.telmovil.msg;

import uy.edu.um.telmovil.commons.ConstantesGenerales;

public class PRN_ACKMessage extends Msg {

	String msrn;

	public PRN_ACKMessage() {
		this.msg_type=ConstantesGenerales.TIPO_MSG_PRN_ACK;
	}

	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	public String getMsrn() {
		return msrn;
	}
	public void setMsrn(String msrn) {
		this.msrn = msrn;
	}
	
}
