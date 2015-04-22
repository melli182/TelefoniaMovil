package uy.edu.um.telmovil.radiobase;

public class Conection{

	public static final String ESTADO_REGISTERED = "REGISTERED";
	
	private String id;
	private String state;
	private String min;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	
	
	
}
