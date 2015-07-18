package uy.edu.um.telmovil.commons.components;

import java.util.ArrayList;

import uy.edu.um.telmovil.commons.ConstantesGenerales;
import uy.edu.um.telmovil.commons.ResourceUtils;
import uy.edu.um.telmovil.commons.components.mtp.MTP;
import uy.edu.um.telmovil.msg.IAMMessage;
import uy.edu.um.telmovil.msg.Msg;
import uy.edu.um.telmovil.msg.SRIMessage;
import uy.edu.um.telmovil.msg.SRI_ACKMessage;

/**
 * Esta clase se encarga de verificar si el ID del terminal es borrado
 * @author mcaamano
 *
 */
public class GMSC extends MTPUser{
	
	private MTP mtpToMSC;
	private MTP mtpToHLR;
	
	private int sendingMSC;
	private int receivingMSC;
	
	private int sendingHLR;
	private int receivingHLR;
	
	private String HLRHost;
	private String MSCHost;
	
	public GMSC() {
		//asigno los puertos para envio y recibo de datos de los MTP
		this.receivingHLR= Integer.valueOf(ResourceUtils.obtenerProperty(ResourceUtils.GMSC_FROM_HLR_RECEIVING));
		this.receivingMSC = Integer.valueOf(ResourceUtils.obtenerProperty(ResourceUtils.GMSC_FROM_MSC_RECEIVING));
		this.sendingHLR = Integer.valueOf(ResourceUtils.obtenerProperty(ResourceUtils.GMSC_TO_HLR_SENDING));
		this.sendingMSC = Integer.valueOf(ResourceUtils.obtenerProperty(ResourceUtils.GMSC_TO_MSC_SENDING));

		this.HLRHost=ResourceUtils.obtenerProperty(ResourceUtils.HLR_HOST);
		this.MSCHost=ResourceUtils.obtenerProperty(ResourceUtils.MSC_HOST);
		
		
		// creo cada uno de los MTP
		MTP mtptoHLR = new MTP(this.receivingHLR, this.sendingHLR);
		mtptoHLR.setMtpUser(this);

		MTP mtptoMSC = new MTP(this.receivingMSC, this.sendingMSC);
		mtptoMSC.setMtpUser(this);

		// seteo los mtp
		this.mtpToHLR = mtptoHLR;
		this.mtpToMSC = mtptoMSC;

	}	
	
	@Override
	/**
	 * Este metodo se encarga de decidir que hacer cada vez que recibe un mensaje de sus MTP
	 */
	public void MTPTransferIndication(Msg mensaje, long mTP_ID) {
		switch (mensaje.getMsg_type()) {
		case ConstantesGenerales.TIPO_MSG_SRI_ACK:
			System.out.println("[GMSC]{Recibi un TIPO_MSG_SRI_ACK de <HLR>}");
			//recibi del HLR un SRI_ACK
			SRI_ACKMessage msg = (SRI_ACKMessage) mensaje;
			
			//ahora debo enviar un IAM al MSC
			
			IAMMessage msgIAM  = new IAMMessage();
			msgIAM.setMsrn(msg.getMsrn());
			this.mtpToMSC.send(MSCHost, msgIAM);
			
			break;
		case ConstantesGenerales.TIPO_MSG_SRI_ERROR:
			System.out.println("[GMSC]{Recibi un TIPO_MSG_SRI_ERROR}");
			break;
		case ConstantesGenerales.TIPO_MSG_IAM:
			System.out.println("[GMSC]{Recibi un TIPO_MSG_IAM del <PSTN>}");
			IAMMessage msg2 = (IAMMessage) mensaje;

			SRIMessage sriMsg = new SRIMessage();
			sriMsg.setMsisdn(msg2.getMsisdn());

			mtpToHLR.send(HLRHost, sriMsg);
			
			break;	
		case ConstantesGenerales.TIPO_MSG_IAM_ERROR:
			System.out.println("[GMSC]{Recibi un TIPO_MSG_IAM_ERROR}");
			//callmethod1
			break;
		}
		
	}
	
	public static void main(String[] args) {
		GMSC gmsc = new GMSC();
		gmsc.initializeMTP(gmsc.mtpToHLR);
		gmsc.initializeMTP(gmsc.mtpToMSC);
		
		System.out.println("[GMSC] HOLA!");
		
		IAMMessage iam_autogenerado = new IAMMessage();
		iam_autogenerado.setMsisdn("1111111111111");
		
		gmsc.MTPTransferIndication(iam_autogenerado, 1);
	}
	
	public MTP getMtpToHLR() {
		return mtpToHLR;
	}

	public void setMtpToHLR(MTP mtpToHLR) {
		this.mtpToHLR = mtpToHLR;
	}

	public MTP getMtpToMSC() {
		return mtpToMSC;
	}

	public void setMtpToMSC(MTP mtpToMSC) {
		this.mtpToMSC = mtpToMSC;
	}

}
