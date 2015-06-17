package uy.edu.um.telmovil.commons.components;

public class Channel {
	
	public enum CHANNELS_TYPE{
		CONTROL, USER 
	}
	
	private long id;
	private double minFrecuency;
	private double maxFrecuency;
	private boolean inUse;

}
