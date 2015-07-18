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
			this.mtpToGMSC.send(GMSCHost, sri_ackMSG);
			
			break;
		case ConstantesGenerales.TIPO_MSG_SRI:
			System.out.println("[HLR]{Recibi un TIPO_MSG_SRI de <GMSC>}");
			SRIMessage sri = (SRIMessage) mensaje;
			
			PRNMessage prnMsg = new PRNMessage();
			HLRRow row = this.findRowByMsisdn(sri.getMsisdn());
			prnMsg.setImsi(row.getImsi());
			
			this.mtpToMSC.send(MSCHost, prnMsg);
			
			break;
		}
	}

	private HLRRow findRowByMsisdn(String msisdn) {
		// TODO Auto-generated method stub
		HLRRow toRet = new HLRRow();
		
		for (HLRRow row : rows) {
			if (row.getMsisdn().equals(msisdn)) {
				toRet = row;
			}
		}
		
		return toRet;
	}


	private void addRows() {
		
		HLRRow row = new HLRRow();
		row.setImsi("SIM-123456");
		row.setCountryCodeOwner("Uruguay");
		row.setCountryCodeVisitor("Argentina");
		rows.add(row);
		
		HLRRow row2 = new HLRRow();
		row.setImsi("SIM-456789");
		row.setCountryCodeOwner("Uruguay");
		row.setCountryCodeVisitor("Argentina");
		rows.add(row2);
		
		HLRRow row3 = new HLRRow();
		row.setImsi("SIM-123789");
		row.setCountryCodeOwner("Uruguay");
		row.setCountryCodeVisitor("Argentina");
		rows.add(row3);
		
		HLRRow row4 = new HLRRow();
		row.setImsi("SIM-112233");
		row.setCountryCodeOwner("Uruguay");
		row.setCountryCodeVisitor("Argentina");
		rows.add(row4);
		
		HLRRow row5 = new HLRRow();
		row.setImsi("SIM-147258");
		row.setCountryCodeOwner("Uruguay");
		row.setCountryCodeVisitor("Argentina");
		rows.add(row5);
		
		HLRRow row6 = new HLRRow();
		row.setImsi("SIM-369852");
		row.setCountryCodeOwner("Argentina");
		row.setCountryCodeVisitor("Uruguay");
		rows.add(row6);
		
		HLRRow row7 = new HLRRow();
		row.setImsi("SIM-321987");
		row.setCountryCodeOwner("Argentina");
		row.setCountryCodeVisitor("Uruguay");
		rows.add(row7);
		
		HLRRow row8 = new HLRRow();
		row.setImsi("SIM-159753");
		row.setCountryCodeOwner("Argentina");
		row.setCountryCodeVisitor("Uruguay");
		rows.add(row8);
		
		HLRRow row9 = new HLRRow();
		row.setImsi("SIM-426751");
		row.setCountryCodeOwner("Argentina");
		row.setCountryCodeVisitor("Uruguay");
		rows.add(row9);
		
		HLRRow row10 = new HLRRow();
		row.setImsi("SIM-103971");
		row.setCountryCodeOwner("Argentina");
		row.setCountryCodeVisitor("Uruguay");
		rows.add(row10);
		
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
