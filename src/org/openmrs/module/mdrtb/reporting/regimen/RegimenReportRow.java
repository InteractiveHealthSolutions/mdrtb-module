package org.openmrs.module.mdrtb.reporting.regimen;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openmrs.Patient;

public class RegimenReportRow {
	
	private Patient patient;
	private Date treatmentStartDate;
	private Boolean cm;
	private Boolean am;
	private Boolean mfx;
	private Boolean lfx;
	private Boolean pto;
	private Boolean cs;
	private Boolean pas;
	private Boolean z;
	private Boolean e;
	private Boolean h;
	private Boolean lzd;
	private Boolean cfz;
	private Boolean bdq;
	private Boolean hr;
	
	
	
	
	
	public Patient getPatient() {
		return patient;
	}
	public void setP(Patient patient) {
		this.patient = patient;
	}
	public Date getTreatmentStartDate() {
		return treatmentStartDate;
	}
	public void setTreatmentStartDate(Date treatmentStartDate) {
		this.treatmentStartDate = treatmentStartDate;
	}
	public Boolean getCm() {
		return cm;
	}
	public void setCm(Boolean cm) {
		this.cm = cm;
	}
	public Boolean getAm() {
		return am;
	}
	public void setAm(Boolean am) {
		this.am = am;
	}
	public Boolean getMfx() {
		return mfx;
	}
	public void setMfx(Boolean mfx) {
		this.mfx = mfx;
	}
	public Boolean getLfx() {
		return lfx;
	}
	public void setLfx(Boolean lfx) {
		this.lfx = lfx;
	}
	public Boolean getPto() {
		return pto;
	}
	public void setPto(Boolean pto) {
		this.pto = pto;
	}
	public Boolean getCs() {
		return cs;
	}
	public void setCs(Boolean cs) {
		this.cs = cs;
	}
	public Boolean getPas() {
		return pas;
	}
	public void setPas(Boolean pas) {
		this.pas = pas;
	}
	public Boolean getZ() {
		return z;
	}
	public void setZ(Boolean z) {
		this.z = z;
	}
	public Boolean getE() {
		return e;
	}
	public void setE(Boolean e) {
		this.e = e;
	}
	public Boolean getH() {
		return h;
	}
	public void setH(Boolean h) {
		this.h = h;
	}
	public Boolean getLzd() {
		return lzd;
	}
	public void setLzd(Boolean lzd) {
		this.lzd = lzd;
	}
	public Boolean getCfz() {
		return cfz;
	}
	public void setCfz(Boolean cfz) {
		this.cfz = cfz;
	}
	public Boolean getBdq() {
		return bdq;
	}
	public void setBdq(Boolean bdq) {
		this.bdq = bdq;
	}
	public Boolean getHr() {
		return hr;
	}
	public void setHr(Boolean hr) {
		this.hr = hr;
	}
	
	public String getFormattedTreatmentStartDate() {

		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("MMM-yy");
		
		return sdf.format(treatmentStartDate);
	}
}
