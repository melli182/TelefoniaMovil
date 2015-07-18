package uy.edu.um.telmovil.mgr;

import uy.edu.um.telmovil.commons.ConstantesGenerales;
import uy.edu.um.telmovil.msg.IAMMessage;
import uy.edu.um.telmovil.msg.IAM_ERRORMessage;
import uy.edu.um.telmovil.msg.Msg;
import uy.edu.um.telmovil.msg.PRNMessage;
import uy.edu.um.telmovil.msg.PRN_ACKMessage;
import uy.edu.um.telmovil.msg.PRN_ERRORMessage;
import uy.edu.um.telmovil.msg.SRIMessage;
import uy.edu.um.telmovil.msg.SRI_ACKMessage;
import uy.edu.um.telmovil.msg.SRI_ERRORMessage;

import com.google.gson.Gson;

public class MsgManager {

	private static final Gson gson = new Gson();
	
	public static Msg obtenerMsgFromString(String msgString){
		Msg toRet = null;
		toRet = gson.fromJson(msgString, Msg.class);
		switch (toRet.getMsg_type()) {
		case ConstantesGenerales.TIPO_MSG_IAM:
			return gson.fromJson(msgString, IAMMessage.class);
		case ConstantesGenerales.TIPO_MSG_IAM_ERROR:
			return gson.fromJson(msgString, IAM_ERRORMessage.class);
		case ConstantesGenerales.TIPO_MSG_PRN:
			return gson.fromJson(msgString, PRNMessage.class);
		case ConstantesGenerales.TIPO_MSG_PRN_ACK:
			return gson.fromJson(msgString, PRN_ACKMessage.class);
		case ConstantesGenerales.TIPO_MSG_PRN_ERROR:
			return gson.fromJson(msgString, PRN_ERRORMessage.class);
		case ConstantesGenerales.TIPO_MSG_SRI:
			return gson.fromJson(msgString, SRIMessage.class);
		case ConstantesGenerales.TIPO_MSG_SRI_ACK:
			return gson.fromJson(msgString, SRI_ACKMessage.class);
		case ConstantesGenerales.TIPO_MSG_SRI_ERROR:
			return gson.fromJson(msgString, SRI_ERRORMessage.class);
		default:
			break;
		}
		return toRet;
	}
	
	public static void main(String[] args) {
		String smsg = "{'tipo':'simpleMSG', 'operacion':'laraila'}";
		MsgManager.obtenerMsgFromString(smsg);
		
	}
}
