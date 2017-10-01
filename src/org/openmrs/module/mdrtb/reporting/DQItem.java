package org.openmrs.module.mdrtb.reporting;

import org.openmrs.Patient;

public class DQItem {
	
	private Patient patient;
	private String dateOfBirth;
	private String locName;
	
	
	public String getLocName() {
		return locName;
	}
	public void setLocName(String locName) {
		this.locName = locName;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public DQItem() {
		patient = null;
		dateOfBirth = null;
	}

}
