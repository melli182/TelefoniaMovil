package uy.edu.um.telmovil.commons.components;

import uy.edu.um.telmovil.commons.components.mtp.MTP;
import uy.edu.um.telmovil.msg.Msg;


/**
 * Esta clase se encarga de verificar si el ID del terminal es borrado
 * @author mcaamano
 *
 */
public class MSC extends MTPUser{

//	private List<RNC> rncs; /// ??? aca deberiamos meter otro mtp???
	
	private VLR vlr;
	
	private MTP mtpToGMSC;
	private MTP mtpToHLR;
	
	@Override
	public void MTPTransferIndication(Msg mensaje, long MTP_ID) {
		// TODO Auto-generated method stub
		
	}

}
