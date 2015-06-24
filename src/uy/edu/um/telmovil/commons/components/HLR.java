package uy.edu.um.telmovil.commons.components;

import java.util.List;

import uy.edu.um.telmovil.commons.components.mtp.MTP;
import uy.edu.um.telmovil.msg.Msg;

public class HLR extends MTPUser{
	
	
	
	private MTP mtpToMSC;
	private MTP mtpToGMSC;
	
	private List<HLRRow> rows;

	@Override
	public void MTPTransferIndication(Msg mensaje, long mTP_ID) {
		// TODO Auto-generated method stub
		
	}
	

}
