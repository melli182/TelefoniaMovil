package uy.edu.um.telmovil.msg;

import uy.edu.um.telmovil.commons.ConstantesGenerales;

public class PRN_ERRORMessage extends Msg {

	String msrn;
	String error_code;

	public PRN_ERRORMessage() {
		this.msg_type=ConstantesGenerales.TIPO_MSG_PRN_ERROR;
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


	public String getError_code() {
		return error_code;
	}


	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	
	
	
}
