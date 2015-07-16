package uy.edu.um.telmovil.msg;

import uy.edu.um.telmovil.commons.ConstantesGenerales;

public class SRI_ACKMessage extends Msg {

	String msrn;
	String msisdn;


	public SRI_ACKMessage() {
		this.msg_type=ConstantesGenerales.TIPO_MSG_SRI_ACK;
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
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
}
