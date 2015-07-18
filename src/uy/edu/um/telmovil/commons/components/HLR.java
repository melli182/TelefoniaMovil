package uy.edu.um.telmovil.commons.components;

import java.util.ArrayList;
import java.util.List;

import uy.edu.um.telmovil.commons.ConstantesGenerales;
import uy.edu.um.telmovil.commons.ResourceUtils;
import uy.edu.um.telmovil.commons.components.mtp.MTP;
import uy.edu.um.telmovil.msg.Msg;
import uy.edu.um.telmovil.msg.PRNMessage;
import uy.edu.um.telmovil.msg.PRN_ACKMessage;
import uy.edu.um.telmovil.msg.PRN_ERRORMessage;
import uy.edu.um.telmovil.msg.SRIMessage;
import uy.edu.um.telmovil.msg.SRI_ACKMessage;
import uy.edu.um.telmovil.msg.SRI_ERRORMessage;

public class HLR extends MTPUser {

	private MTP mtpToMSC;
	private MTP mtpToGMSC;

	private int sendingMSC;
	private int receivingMSC;

	private int sendingGMSC;
	private int receivingGMSC;

	private String MSCHost;
	private String GMSCHost;

	private ArrayList<HLRRow> registrosHLRUruguay;
	private ArrayList<HLRRow> registrosHLRArgentina;

	public HLR() {
		// asigno los puertos para envio y recibo de datos de los MTP
		this.receivingGMSC = Integer.valueOf(ResourceUtils.obtenerProperty(ResourceUtils.HLR_FROM_GMSC_RECEIVING));
		this.receivingMSC = Integer.valueOf(ResourceUtils.obtenerProperty(ResourceUtils.HLR_FROM_MSC_RECEIVING));
		this.sendingGMSC = Integer.valueOf(ResourceUtils.obtenerProperty(ResourceUtils.HLR_TO_GMSC_SENDING));
		this.sendingMSC = Integer.valueOf(ResourceUtils.obtenerProperty(ResourceUtils.HLR_TO_MSC_SENDING));

		this.GMSCHost = ResourceUtils.obtenerProperty(ResourceUtils.GMSC_HOST);
		this.MSCHost = ResourceUtils.obtenerProperty(ResourceUtils.MSC_HOST);

		// creo cada uno de los MTP
		MTP mtptoGMSC = new MTP(this.receivingGMSC, this.sendingGMSC);
		mtptoGMSC.setMtpUser(this);

		MTP mtptoMSC = new MTP(this.receivingMSC, this.sendingMSC);
		mtptoGMSC.setMtpUser(this);

		// seteo los mtp
		this.mtpToGMSC = mtptoGMSC;
		this.mtpToMSC = mtptoMSC;

		// inicializo la lista de hlrRows
		this.setRows(new ArrayList<HLRRow>());

	}

	@Override
	public void MTPTransferIndication(Msg mensaje, long mTP_ID) {

		switch (mensaje.getMsg_type()) {
		case ConstantesGenerales.TIPO_MSG_PRN_ACK:
			System.out.println("[HLR]{Recibi un TIPO_MSG_PRN_ACK de <MSC/VLR>}");
			// recibi del MSC/VLR un PRN_ACK
			PRN_ACKMessage msg = (PRN_ACKMessage) mensaje;

			SRI_ACKMessage sri_ackMSG = new SRI_ACKMessage();
			sri_ackMSG.setMsrn(msg.getMsrn());

			System.out.println("1");
			this.mtpToGMSC.send(GMSCHost, sri_ackMSG);

			break;
		case ConstantesGenerales.TIPO_MSG_SRI:
			System.out.println("[HLR]{Recibi un TIPO_MSG_SRI de <GMSC>}");
			SRIMessage sri = (SRIMessage) mensaje;

			HLRRow row = this.findRowByMsisdn(sri.getMsisdn());
			
			if (row != null) {
				PRNMessage prnMsg = new PRNMessage();
				prnMsg.setImsi(row.getImsi());
				this.mtpToMSC.send(MSCHost, prnMsg);
			} else {
				PRNMessage prnMsg = new PRNMessage();
				prnMsg.setImsi(null);
				this.mtpToMSC.send(MSCHost, "");
//				PRN_ERRORMessage prn_error = new PRN_ERRORMessage();
//				prn_error.setMsg_type(ConstantesGenerales.TIPO_MSG_PRN_ERROR);
//				prn_error.setMsrn(sri.getMsisdn());
//				this.mtpToMSC.send(MSCHost, prn_error);
			}

			break;
		case ConstantesGenerales.TIPO_MSG_PRN_ERROR:
			System.out.println("[HLR]{Recibi un TIPO_MSG_PRN_ERROR de <MSC>}");
			PRN_ERRORMessage prn_error = (PRN_ERRORMessage) mensaje;
			
			SRI_ERRORMessage sri_error = new SRI_ERRORMessage();
			sri_error.setError_code(prn_error.getError_code());
			
			this.mtpToGMSC.send(GMSCHost, sri_error);
			
		}
	}

	private HLRRow findRowByMsisdn(String msisdn) {

		if (msisdn != null) {
			ArrayList<HLRRow> rows = getHLRToIterate(msisdn);
			if (rows != null) {
				HLRRow toRet = new HLRRow();

				for (HLRRow row : rows) {
					if (row.getMsisdn().equals(msisdn)) {
						toRet = row;
					}
				}

				return toRet;
			}
		}

		return null;
	}

	private ArrayList<HLRRow> getHLRToIterate(String msisdn) {
		String cc = msisdn.substring(1, 3);
		if (cc.equals("598"))
			return registrosHLRUruguay;
		if (cc.equals("722"))
			return registrosHLRArgentina;
		return null;
	}

	private void addRegistrosUruguay() {

		registrosHLRUruguay = new ArrayList<HLRRow>();
		
		HLRRow row = new HLRRow();
		row.setImsi("SIM-123456");
		row.setCountryCodeOwner("Uruguay");
		row.setCountryCodeVisitor("Argentina");
		row.setMsisdn("59895010840");
		registrosHLRUruguay.add(row);

		HLRRow row2 = new HLRRow();
		row2.setImsi("SIM-456789");
		row2.setCountryCodeOwner("Uruguay");
		row2.setCountryCodeVisitor("Argentina");
		row2.setMsisdn("59899029163");
		registrosHLRUruguay.add(row2);

		HLRRow row3 = new HLRRow();
		row3.setImsi("SIM-123789");
		row3.setCountryCodeOwner("Uruguay");
		row3.setCountryCodeVisitor("Argentina");
		row3.setMsisdn("59896977267");
		registrosHLRUruguay.add(row3);

		HLRRow row4 = new HLRRow();
		row4.setImsi("SIM-112233");
		row4.setCountryCodeOwner("Uruguay");
		row4.setCountryCodeVisitor("Argentina");
		row4.setMsisdn("59895262076");
		registrosHLRUruguay.add(row4);

		HLRRow row5 = new HLRRow();
		row5.setImsi("SIM-147258");
		row5.setCountryCodeOwner("Uruguay");
		row5.setCountryCodeVisitor("Argentina");
		row5.setMsisdn("59899112233");
		registrosHLRUruguay.add(row5);

	}

	private void addRegistrosArgentina() {

		registrosHLRArgentina = new ArrayList<HLRRow>();
		
		HLRRow row6 = new HLRRow();
		row6.setImsi("SIM-369852");
		row6.setCountryCodeOwner("Argentina");
		row6.setCountryCodeVisitor("Uruguay");
		row6.setMsisdn("7220104001122");
		registrosHLRArgentina.add(row6);

		HLRRow row7 = new HLRRow();
		row7.setImsi("SIM-321987");
		row7.setCountryCodeOwner("Argentina");
		row7.setCountryCodeVisitor("Uruguay");
		row7.setMsisdn("7220104123456");
		registrosHLRArgentina.add(row7);

		HLRRow row8 = new HLRRow();
		row8.setImsi("SIM-159753");
		row8.setCountryCodeOwner("Argentina");
		row8.setCountryCodeVisitor("Uruguay");
		row8.setMsisdn("7220104147258");
		registrosHLRArgentina.add(row8);

		HLRRow row9 = new HLRRow();
		row9.setImsi("SIM-426751");
		row9.setCountryCodeOwner("Argentina");
		row9.setCountryCodeVisitor("Uruguay");
		row9.setMsisdn("7220104456789");
		registrosHLRArgentina.add(row9);

		HLRRow row10 = new HLRRow();
		row10.setImsi("SIM-103971");
		row10.setCountryCodeOwner("Argentina");
		row10.setCountryCodeVisitor("Uruguay");
		row10.setMsisdn("7220104369852");
		registrosHLRArgentina.add(row10);

	}

	public List<HLRRow> getRows() {
		return registrosHLRArgentina;
	}

	public void setRows(ArrayList<HLRRow> rows) {
		this.registrosHLRArgentina = rows;
	}

	public static void main(String[] args) {
		HLR hlr = new HLR();

		hlr.addRegistrosUruguay();
		hlr.addRegistrosArgentina();

		hlr.initializeMTP(hlr.mtpToGMSC);
		hlr.initializeMTP(hlr.mtpToMSC);

		System.out.println("[HLR] HOLA!");
	}
}
