package org.openmrs.module.mdrtb.form;

import java.util.Date;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PersonAddress;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.service.MdrtbService;


public class TB03Form extends AbstractSimpleForm {

	public TB03Form() {
		super();
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("TB03"));		
		
	}
	
	public TB03Form(Patient patient) {
		super(patient);
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("TB03"));		
	}
	
	public TB03Form(Encounter encounter) {
		super(encounter);
	}
	
	public Concept getAnatomicalSite() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANATOMICAL_SITE_OF_TB), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setAnatomicalSite(Concept site) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANATOMICAL_SITE_OF_TB), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && site == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(site)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(site != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANATOMICAL_SITE_OF_TB), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(site);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Integer getAgeAtTB03Registration() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.AGE_AT_DOTS_REGISTRATION), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueNumeric().intValue();
		}
	}
	
	public void setAgeAtTB03Registration(Integer age) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.AGE_AT_DOTS_REGISTRATION), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && age == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueNumeric() == null || obs.getValueNumeric().intValue() != age.intValue()) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(age != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.AGE_AT_DOTS_REGISTRATION), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueNumeric(new Double(age));
				encounter.addObs(obs);
			}
		} 
	}
	
	public String getGender() {
		if(encounter.getPatient().getGender().equals("M"))
			return Context.getMessageSourceService().getMessage("mdrtb.tb03.gender.male");
		if(encounter.getPatient().getGender().equals("F"))
			return Context.getMessageSourceService().getMessage("mdrtb.tb03.gender.female");
		
		return "";
	}
	
	public Date getDateOfBirth() {
		return encounter.getPatient().getBirthdate();
	}
	
	public String getAddress() {
		
		PersonAddress pa = encounter.getPatient().getPersonAddress();
		String address = pa.getCountry() + "," + pa.getStateProvince() + "," + pa.getCountyDistrict();
		if(pa.getAddress1()!=null && pa.getAddress1().length()!=0) {
			address += "," + pa.getAddress1();
			if(pa.getAddress2()!=null && pa.getAddress2().length()!=0)
				address += "," + pa.getAddress2();
		}

		return address;
	}
	
	public Concept getTreatmentSiteIP() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.TREATMENT_CENTER_FOR_IP), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setTreatmentSiteIP(Concept site) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.TREATMENT_CENTER_FOR_IP), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && site == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(site)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(site != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.TREATMENT_CENTER_FOR_IP), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(site);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	public Concept getTreatmentSiteCP() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.TREATMENT_CENTER_FOR_CP), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setTreatmentSiteCP(Concept site) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.TREATMENT_CENTER_FOR_CP), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && site == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(site)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(site != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.TREATMENT_CENTER_FOR_CP), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(site);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getPatientCategory() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.TUBERCULOSIS_PATIENT_CATEGORY), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setPatientCategory(Concept cat) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.TUBERCULOSIS_PATIENT_CATEGORY), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && cat == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(cat)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(cat != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.TUBERCULOSIS_PATIENT_CATEGORY), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(cat);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Date getTreatmentStartDate() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DOTS_TREATMENT_START_DATE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueDatetime();
		}
	}
	
	public void setTreatmentStartDate(Date date) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DOTS_TREATMENT_START_DATE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && date == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueDatetime() == null || !obs.getValueDatetime().equals(date)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(date != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.DOTS_TREATMENT_START_DATE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueDatetime(date);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getRegistrationGroup() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.PATIENT_GROUP), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setRegistrationGroup(Concept group) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.PATIENT_GROUP), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && group == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(group)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(group != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.PATIENT_GROUP), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(group);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getHivStatus() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.RESULT_OF_HIV_TEST), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setHivStatus(Concept status) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.RESULT_OF_HIV_TEST), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && status == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(status)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(status != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.RESULT_OF_HIV_TEST), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(status);
				encounter.addObs(obs);
			}
		} 
	}
	
	
}
