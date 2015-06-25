package uy.edu.um.telmovil.commons.components;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import uy.edu.um.telmovil.commons.components.mtp.MTP;
import uy.edu.um.telmovil.msg.Msg;

public class HLR extends MTPUser{
	
	private MTP mtpToMSC;
	private MTP mtpToGMSC;
	
	private int sendingMSC;
	private int receivingMSC;
	
	private int sendingGMSC;
	private int receivingGMSC;
	
	private List<HLRRow> rows;
	
	
	public HLR(int rMSC, int sMSC, int rGMSC, int sGMSC) {
		//asigno los puertos para envio y recibo de datos de los MTP
		this.receivingGMSC = rGMSC;
		this.receivingMSC = rMSC;
		this.sendingGMSC = sGMSC;
		this.sendingMSC = sMSC;
		
		
		//creo cada uno de los MTP
		MTP mtptoGMSC = new MTP(this.receivingGMSC, this.sendingGMSC);
		mtptoGMSC.setMtpUser(this);
		
		MTP mtptoMSC = new MTP(this.receivingMSC, this.sendingMSC);
		mtptoGMSC.setMtpUser(this);
		
		//seteo los mtp
		this.mtpToGMSC = mtptoGMSC;
		this.mtpToMSC = mtptoMSC;
		
		//inicializo la lista de hlrRows
		this.setRows(new ArrayList<HLRRow>());
		
	}
	

	@Override
	public void MTPTransferIndication(Msg mensaje, long mTP_ID) {
		switch (mensaje.getTipo()) {
		case "Tipo1":
			//callmethod1
			break;
		case "Tipo2":
			//callmethod1
			break;	
		case "Tipo3":
			//callmethod1
			break;
		default:
			break;
		}
	}



	public List<HLRRow> getRows() {
		return rows;
	}

	public void setRows(List<HLRRow> rows) {
		this.rows = rows;
	}
	
	public static void main(String[] args) {
		HLR test = new HLR(1,2,2,1);
		test.initializeMTP(test.mtpToGMSC);
		test.initializeMTP(test.mtpToMSC);
		try {
			Msg mensaje = new Msg();
			mensaje.setTipo("TipoDePrueba");
			test.mtpToGMSC.send(mensaje);
			test.mtpToGMSC.send(mensaje);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
