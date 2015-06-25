package uy.edu.um.telmovil.commons.components;

import uy.edu.um.telmovil.commons.components.mtp.MTP;
import uy.edu.um.telmovil.msg.Msg;

public abstract class MTPUser {

	/**
	 * Metodo que debe ser sobreescrito por cada clase que extienda de MTPUser
	 * define que es lo que va a hacer cada vez que reciba esta llamada desde 
	 * uno de sus MTP
	 * 
	 * @param mensaje
	 * @param mTP_ID
	 */
	public abstract void MTPTransferIndication(Msg mensaje, long mTP_ID);
	
	/**
	 * Este metodo se encarga de inicializar el MTP indicado
	 * para que el mismo pueda escuchar en un hilo distinto al de ejecucion
	 * actual y asi no dejar congelado la app
	 * 
	 * @param mptToInitialize
	 */
	public void initializeMTP(MTP mptToInitialize){
		mptToInitialize.setMtpUser(this);
		(new Thread(mptToInitialize)).start();
	}

}
