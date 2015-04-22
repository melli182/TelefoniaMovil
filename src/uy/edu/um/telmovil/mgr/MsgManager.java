package uy.edu.um.telmovil.mgr;

import com.google.gson.Gson;

import uy.edu.um.telmovil.msg.GenericMsg;
import uy.edu.um.telmovil.msg.Msg;
import uy.edu.um.telmovil.msg.SimpleMsg;
import uy.edu.um.telmovil.utils.ConstantesGenerales;

public class MsgManager {

	private static final Gson gson = new Gson();
	
	public static Msg obtenerMsgFromString(String msgString){
		Msg toRet = null;
		toRet = gson.fromJson(msgString, Msg.class);
		if(toRet!=null && toRet.getTipo()!=null){
			switch (toRet.getTipo()) {
			case ConstantesGenerales.TIPO_MSG_GENERIC_MSG:
				toRet = gson.fromJson(msgString, GenericMsg.class);
				break;
			case ConstantesGenerales.TIPO_MSG_SIMPLE_MSG:
				toRet = gson.fromJson(msgString, SimpleMsg.class);
				break;
			default:
				break;
			}
		}
		
		return toRet;
	}
	
	public static void main(String[] args) {
		String smsg = "{'tipo':'simpleMSG', 'operacion':'laraila'}";
		MsgManager.obtenerMsgFromString(smsg);
		
	}
}
