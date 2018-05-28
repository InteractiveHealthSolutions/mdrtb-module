package org.openmrs.module.mdrtb.reporting;

import java.util.ArrayList;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;

public class DQItem {
	
	private Patient patient;
	private String dateOfBirth;
	private String locName;
	private ArrayList<String> links;
	
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
	
	public ArrayList<String> getLinks() {
		return links;
	}
	public void setLinks(ArrayList<String> links) {
		this.links = links;
	}
	
	public String getLink(int i) {
		return links.get(i);
	}
	
	public void setLink(int i, String lnk) {
		links.set(i, lnk);
	}
	
	public void addLink(String lnk) {
		links.add(lnk);
	}
	
	public DQItem() {
		patient = null;
		dateOfBirth = null;
		links = new ArrayList<String>();
		
	}
	
	public String getGender() {
		if(patient==null || patient.getGender()==null) {
			return null;
		}
		
		if(patient.getGender().equals("M")) {
			return Context.getMessageSourceService().getMessage("mdrtb.tb03.gender.male");
		}
		
		else if(patient.getGender().equals("F")) {
			return	Context.getMessageSourceService().getMessage("mdrtb.tb03.gender.female");
		}
		
		return null;
	}
	
}
