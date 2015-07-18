package uy.edu.um.telmovil.commons.components;

public class HLRRow {

	private String imsi;
	private String countryCodeOwner;
	private String countryCodeVisitor;
	private String msisdn;
	

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String oImsi) {
		this.imsi = oImsi;
	}

	public String getCountryCodeOwner() {
		return countryCodeOwner;
	}

	public void setCountryCodeOwner(String countryCodeOwner) {
		this.countryCodeOwner = countryCodeOwner;
	}

	public String getCountryCodeVisitor() {
		return countryCodeVisitor;
	}

	public void setCountryCodeVisitor(String countryCodeVisitor) {
		this.countryCodeVisitor = countryCodeVisitor;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String oMsisdn) {
		this.msisdn = oMsisdn;
	}

}
