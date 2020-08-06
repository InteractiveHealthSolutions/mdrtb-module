package org.openmrs.module.mdrtb.reporting;

import java.util.ArrayList;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;

public class DQItem {
	
	private Patient patient;
	private String dateOfBirth;
	private String locName;
	private ArrayList<String> tb03Links;
	private ArrayList<String> form89Links;
	
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
	
	public ArrayList<String> getTb03Links() {
		return tb03Links;
	}
	public void setTb03Links(ArrayList<String> tb03Links) {
		this.tb03Links = tb03Links;
	}
	
	public String getTb03Link(int i) {
		return tb03Links.get(i);
	}
	
	public void setTb03Link(int i, String lnk) {
		tb03Links.set(i, lnk);
	}
	
	public void addTb03Link(String lnk) {
		tb03Links.add(lnk);
	}
	
	public DQItem() {
		patient = null;
		dateOfBirth = null;
		tb03Links = new ArrayList<String>();
		form89Links = new ArrayList<String>();
		
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
	
	public ArrayList<String> getForm89Links() {
		return form89Links;
	}
	public void setForm89Links(ArrayList<String> form89Links) {
		this.form89Links = form89Links;
	}
	
	public String getForm89Link(int i) {
		return form89Links.get(i);
	}
	
	public void setForm89Link(int i, String lnk) {
		form89Links.set(i, lnk);
	}
	
	public void addForm89Link(String lnk) {
		form89Links.add(lnk);
	}
	
}
