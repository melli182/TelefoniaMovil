package uy.edu.um.telmovil.msg;

import uy.edu.um.telmovil.commons.ConstantesGenerales;

public class IAM_ERRORMessage extends Msg {

	String error_code;
	String num;
	
	public IAM_ERRORMessage() {
		this.msg_type=ConstantesGenerales.TIPO_MSG_IAM_ERROR;
	}
	
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
	@Override
	public String getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}
	
}
