package org.epis.integration.dsl.to;

public class PatientDataTO {

	private String employeeid;
	
	private String rxNumber;
	private String storenum;
	private String	daysSupply;
	public String getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}
	public String getRxNumber() {
		return rxNumber;
	}
	public void setRxNumber(String rxNumber) {
		this.rxNumber = rxNumber;
	}
	public String getStorenum() {
		return storenum;
	}
	public void setStorenum(String storenum) {
		this.storenum = storenum;
	}
	public String getDaysSupply() {
		return daysSupply;
	}
	public void setDaysSupply(String daysSupply) {
		this.daysSupply = daysSupply;
	}
	@Override
	public String toString() {
		return "Patient {employeeid=" + employeeid + ", rxNumber=" + rxNumber + ", storenum=" + storenum
				+ ", daysSupply=" + daysSupply + "}";
	}


	
	
	
}
