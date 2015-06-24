package uy.edu.um.telmovil.commons.components;

import uy.edu.um.telmovil.msg.Msg;

public abstract class MTPUser {

	public abstract void MTPTransferIndication(Msg mensaje, long mTP_ID);
}
