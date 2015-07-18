package uy.edu.um.telmovil.commons.components;

import java.util.ArrayList;
import java.util.List;

import uy.edu.um.telmovil.commons.ConstantesGenerales;
import uy.edu.um.telmovil.commons.ResourceUtils;
import uy.edu.um.telmovil.commons.components.mtp.MTP;
import uy.edu.um.telmovil.msg.Msg;
import uy.edu.um.telmovil.msg.PRNMessage;
import uy.edu.um.telmovil.msg.PRN_ACKMessage;
import uy.edu.um.telmovil.msg.SRIMessage;
import uy.edu.um.telmovil.msg.SRI_ACKMessage;

public class HLR extends MTPUser{
	
	private MTP mtpToMSC;
	private MTP mtpToGMSC;
	
	private int sendingMSC;
	private int receivingMSC;
	
	private int sendingGMSC;
	private int receivingGMSC;
	
	private String MSCHost;
	private String GMSCHost;
	
	private List<HLRRow> rows;
	
	
	public HLR() {
		//asigno los puertos para envio y recibo de datos de los MTP
		this.receivingGMSC = Integer.valueOf(ResourceUtils.obtenerProperty(ResourceUtils.HLR_FROM_GMSC_RECEIVING));
		this.receivingMSC = Integer.valueOf(ResourceUtils.obtenerProperty(ResourceUtils.HLR_FROM_MSC_RECEIVING));
		this.sendingGMSC = Integer.valueOf(ResourceUtils.obtenerProperty(ResourceUtils.HLR_TO_GMSC_SENDING));
		this.sendingMSC = Integer.valueOf(ResourceUtils.obtenerProperty(ResourceUtils.HLR_TO_MSC_SENDING));
		
		this.GMSCHost=ResourceUtils.obtenerProperty(ResourceUtils.GMSC_HOST);
		this.MSCHost=ResourceUtils.obtenerProperty(ResourceUtils.MSC_HOST);
		
		
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
		switch (mensaje.getMsg_type()) {
		case ConstantesGenerales.TIPO_MSG_PRN_ACK:
			System.out.println("[HLR]{Recibi un TIPO_MSG_PRN_ACK de <MSC/VLR>}");
			//recibi del MSC/VLR un PRN_ACK
			PRN_ACKMessage msg = (PRN_ACKMessage) mensaje;
			
			SRI_ACKMessage sri_ackMSG = new SRI_ACKMessage();
			sri_ackMSG.setMsrn(msg.getMsrn());
			
			System.out.println("1");
			break;
		case ConstantesGenerales.TIPO_MSG_SRI:
			System.out.println("[HLR]{Recibi un TIPO_MSG_SRI de <GMSC>}");
			SRIMessage sri = (SRIMessage) mensaje;
			
			PRNMessage prnMsg = new PRNMessage();
			prnMsg.setImsi(sri.getMsisdn()+"Debo Obtener el IMSI de la tabla del HLR");
			
			this.mtpToMSC.send(MSCHost, prnMsg);
			
		default:
			System.out.println("EL tipo ingresado no coincide con ninguno valido");
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
		HLR hlr = new HLR();
		hlr.initializeMTP(hlr.mtpToGMSC);
		hlr.initializeMTP(hlr.mtpToMSC);
		
		System.out.println("[HLR] HOLA!");
	}
}
