package org.openmrs.module.mdrtb.reporting.pv;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.form.pv.AEForm;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.programlocation.PatientProgram;

public class AERegisterData implements Comparable <AERegisterData>{
	
	//private Patient patient;
	private AEForm aeForm;
	//private String identifier;
    
	public AERegisterData(AEForm aeForm) {
		// TODO Auto-generated constructor stub
		this.aeForm = aeForm;
	}

	public Patient getPatient() {
		return aeForm.getPatient();
	}

	public String getIdentifier() {
		
		String id = "";
		Integer patProgId = aeForm.getPatProgId();
		if(patProgId==null)
			return id;
		
		org.openmrs.PatientProgram pp = Context.getProgramWorkflowService().getPatientProgram(patProgId);
	
		if(pp!=null) {
			PatientIdentifier pi = Context.getService(MdrtbService.class).getGenPatientProgramIdentifier(pp);
			if(pi==null) {
				id = null;
			}
			
			else {
				id = pi.getIdentifier();
			}
		}
		
		else {
			id = null;
		}
		
		return id;
	}

	
	
	public AEForm getAEForm() {
		return aeForm;
	}
	
	public void setAEForm(AEForm aeForm) {
		this.aeForm = aeForm;
		
	}

	
	

	@Override
	public int compareTo(AERegisterData aerd) {
		
		if(aerd.getAEForm()==null || getAEForm()==null)
			return 0;
		
		
		if(aerd.getAEForm().getEncounterDatetime() ==null || this.getAEForm().getEncounterDatetime()==null)
			return 0;
		
		return this.getAEForm().getEncounterDatetime().compareTo(aerd.getAEForm().getEncounterDatetime());

	}
	
	public String getPatientName() {
		if(aeForm==null)
			return null;
		
		Patient p = aeForm.getPatient();
		
		String lastName = p.getFamilyName();
		String firstName = p.getGivenName();
				
		return lastName + ", " + firstName;
	}
	
	public String getBirthDate() {
		if(aeForm==null)
			return null;
		
		Patient p = aeForm.getPatient();
		
		Date dob = p.getBirthdate();
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy.MM.dd");
		
		return sdf.format(dob);
		
		
	}
	
	public String getOnsetDate() {
		if(aeForm==null)
			return null;

		Date od = aeForm.getEncounterDatetime();
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy.MM.dd");
		
		return sdf.format(od);

	}
	
	public String getAEDescription() {
		if(aeForm==null)
			return null;
		
		Concept q = aeForm.getAdverseEvent();
		
		if(q==null)
			return null;
		
		return q.getName().getName();
	}
	
	public String getDiagnosticInvestigation() {
		if(aeForm==null)
			return null;
		
		Concept q = aeForm.getDiagnosticInvestigation();
		
		if(q==null)
			return null;
		
		return q.getName().getName();
	}
	
	public String getSerious() {
		if(aeForm==null)
			return null;
		
		Concept q = aeForm.getTypeOfEvent();
		
		if(q==null)
			return null;
		
		if(q.getId()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SERIOUS).getId()) {
			return Context.getMessageSourceService().getMessage("mdrtb.yes");
		}
		
		else {
			return Context.getMessageSourceService().getMessage("mdrtb.no");
		}
	}
	
	public String getOfSpecialInterest() {
		if(aeForm==null)
			return null;
		
		Concept q = aeForm.getTypeOfEvent();
		
		if(q==null)
			return null;
		
		if(q.getId()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OF_SPECIAL_INTEREST).getId()) {
			return Context.getMessageSourceService().getMessage("mdrtb.yes");
		}
		
		else {
			return Context.getMessageSourceService().getMessage("mdrtb.no");
		}
	}
	
	public String getAncillaryDrugs() {
		if(aeForm==null)
			return null;
		
		Concept q = aeForm.getActionTaken();
		
		if(q==null)
			return null;
		
		if(q.getId()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANCILLARY_DRUG_GIVEN).getId()) {
			return Context.getMessageSourceService().getMessage("mdrtb.yes");
		}
		
		else {
			return Context.getMessageSourceService().getMessage("mdrtb.no");
		}
	}
	
	public String getDoseChanged() {
		if(aeForm==null)
			return null;
		
		Concept q = aeForm.getActionTaken();
		
		if(q==null)
			return null;
		
		if(q.getId()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DRUG_INTERRUPTED).getId() ||
				q.getId()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DOSE_REDUCED).getId() || 
				q.getId()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DRUG_WITHDRAWN).getId()) {
			return Context.getMessageSourceService().getMessage("mdrtb.yes");
		}
		
		else {
			return Context.getMessageSourceService().getMessage("mdrtb.no");
		}
	}
	
	public String getSuspectedDrug() {
		
		String ret = "";
		
		if(aeForm==null)
			return null;
		
		Concept q = aeForm.getCausalityDrug1();
		if(q==null) {
			return null;
		}
		
		ret += q.getName().getName();
		
		q = aeForm.getCausalityDrug2();
		
		if(q!=null) {
			ret += "/" + q.getName().getName();
		
			q = aeForm.getCausalityDrug3();
		
			if(q!=null) {
				ret += "/" + q.getName().getName();
			}
		}
		return ret;
	}

	public String getSuspectedDrugStartDate() {
		
		String ret = "";
		
		if(aeForm==null)
			return null;
		
		Concept q = aeForm.getCausalityDrug1();
		if(q==null) {
			return null;
		}
		
		ret += q.getName().getName();
		
		q = aeForm.getCausalityDrug2();
		
		if(q!=null) {
			ret += "/" + q.getName().getName();
		
			q = aeForm.getCausalityDrug3();
		
			if(q!=null) {
				ret += "/" + q.getName().getName();
			}
		}
		return ret;
	}
	
	public String getActionOutcome() {

		if(aeForm==null)
			return null;
		
		Concept q = aeForm.getActionOutcome();
		if(q==null) {
			return null;
		}
		
		
		return q.getName().getName();
	}
	
	public String getActionTaken() {

		if(aeForm==null)
			return null;
		
		Concept q = aeForm.getActionTaken();
		if(q==null) {
			return null;
		}
		
		
		return q.getName().getName();
	}
	
	public String getTxRegimen() {

		if(aeForm==null)
			return null;
		
		String q = aeForm.getTreatmentRegimenAtOnset();
		
		return q;
	
	}
	
	public String getDrugRechallenge() {

		if(aeForm==null)
			return null;
		
		Concept q = aeForm.getDrugRechallenge();
		if(q==null) {
			return null;
		}
		
		
		return q.getName().getName();
	}
	
	public String getCausalityAssessment() {
		
		String ret = "";
		
		if(aeForm==null)
			return null;
		
		Concept q = aeForm.getCausalityAssessmentResult1();
		if(q==null) {
			return null;
		}
		
		ret += q.getName().getName();
		
		q = aeForm.getCausalityAssessmentResult2();
		
		if(q!=null) {
			ret += "/" + q.getName().getName();
		
			q = aeForm.getCausalityAssessmentResult3();
		
			if(q!=null) {
				ret += "/" + q.getName().getName();
			}
		}
		return ret;
	}
	
	public String getYellowCardDate() {
		if(aeForm==null)
			return null;

		Date ycd = aeForm.getYellowCardDate();
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy.MM.dd");
		
		return sdf.format(ycd);

	}
	
	public String getComments() {

		if(aeForm==null)
			return null;
		
		String q = aeForm.getComments();
		
		return q;
	
	}

	/*public String getGender() {
		if(patient.getGender().equals("M"))
			return Context.getMessageSourceService().getMessage("dotsreports.tb03.gender.male");
		else if (patient.getGender().equals("F"))
			return Context.getMessageSourceService().getMessage("dotsreports.tb03.gender.female");
		
		return "";
	}

	public void setGender(String gender) {
		this.gender = gender;
	}*/
	
		
	
	
	

}
