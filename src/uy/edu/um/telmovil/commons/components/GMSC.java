package uy.edu.um.telmovil.commons.components;

import uy.edu.um.telmovil.commons.components.mtp.MTP;
import uy.edu.um.telmovil.msg.Msg;

/**
 * Esta clase se encarga de verificar si el ID del terminal es borrado
 * @author mcaamano
 *
 */
public class GMSC extends MTPUser{
	
	private MTP mtpToMSC;
	private MTP mtpToHLR;
	
	
	@Override
	public void MTPTransferIndication(Msg mensaje, long mTP_ID) {
		// TODO Auto-generated method stub
		
	}
	

}
