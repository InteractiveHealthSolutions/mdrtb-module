package org.openmrs.module.mdrtb.reporting;

public class Form8Table4Data {
	
	private Integer hospitalised;
	private Integer inHospital;
	
	private Integer firstNew;
	private Integer newBac;
	private Integer newOther;
	
	public Form8Table4Data() {
		 hospitalised = 0;
		 inHospital = 0;
		
		 firstNew = 0;
		 newBac = 0;
		 newOther = 0;
	}
	
	public Integer getHospitalised() {
		return hospitalised;
	}
	public void setHospitalised(Integer hospitalised) {
		this.hospitalised = hospitalised;
	}
	public Integer getInHospital() {
		return inHospital;
	}
	public void setInHospital(Integer inHospital) {
		this.inHospital = inHospital;
	}
	public Integer getFirstNew() {
		return firstNew;
	}
	public void setFirstNew(Integer firstNew) {
		this.firstNew = firstNew;
	}
	public Integer getNewBac() {
		return newBac;
	}
	public void setNewBac(Integer newBac) {
		this.newBac = newBac;
	}
	public Integer getNewOther() {
		return newOther;
	}
	public void setNewOther(Integer newOther) {
		this.newOther = newOther;
	}
	
	

}
