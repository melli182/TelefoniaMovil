package uy.edu.um.telmovil.commons.components;

public class QoSParameters {

	public enum QosType{
		DELAY, JITTER, PACKET_LOSS, THROUGHPUT
	}
	
	private QosType type;
	private String value;
}
