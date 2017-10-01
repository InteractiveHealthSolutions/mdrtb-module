package org.openmrs.module.mdrtb.form;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.databene.domain.person.Person;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.program.TbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.status.StatusItem;


public class Form89 extends AbstractSimpleForm {

	private TB03Form tb03;
	
	public Form89() {
		super();
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("Form 89"));		
		
	}
	
	public Form89(Patient patient) {
		super(patient);
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("Form 89"));		
	}
	
	public Form89(Encounter encounter) {
		super(encounter);
		
	}
	
	public void initTB03(Integer patientProgramId) {
		
		TbPatientProgram tpp = Context.getService(MdrtbService.class).getTbPatientProgram(patientProgramId);
		List<Encounter> encounters = null;
		EncounterType intakeType = Context.getEncounterService().getEncounterType(Context.getAdministrationService().getGlobalProperty("mdrtb.intake_encounter_type"));
		if (tpp != null) {
    		encounters = tpp.getTbEncountersDuringProgramObs();
    	}
		
		if (encounters != null) {
    		for (Encounter encounter : encounters) {
    			// create a new status item for this encounter
    			
    			// now place the visit in the appropriate "bucket"
    			if (encounter.getEncounterType().equals(intakeType)) {
    				tb03 = new TB03Form(encounter);
    				break;
    			}
    		}
		}
    			
	}
	
	public TB03Form getTB03() {
		return tb03;
	}
	
	
	public Integer getYearOfTB03Registration() {
		if(tb03==null || tb03.getEncounterDatetime()==null)
			return null;
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(tb03.getEncounterDatetime().getTime());
		Integer year = gc.get(Calendar.YEAR);
		
		return year;
			
	}
	
	public String getPatientName() {
		PersonName p = getPatient().getPersonName();
		
		return p.getFamilyName() + "," + p.getGivenName();
	}
	
	
	/*public String getTb03RegistrationNumber() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.TB03_REGISTRATION_NUMBER), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueText();
		}
	}
	
	public void setTb03RegistrationNumber(String number) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.TB03_REGISTRATION_NUMBER), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && number == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueText() == null || !obs.getValueText().equals(number)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(number != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.TB03_REGISTRATION_NUMBER), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueText(number);
				encounter.addObs(obs);
			}
		} 
	}*/
	
	public Concept getAnatomicalSite() {
		Obs obs = null;
		if(tb03!=null)
			obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANATOMICAL_SITE_OF_TB), tb03.getEncounter());
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	/*public void setAnatomicalSite(Concept site) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANATOMICAL_SITE_OF_TB), tb03.getEncounter());
		
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
	}*/
	
	public Integer getAgeAtRegistration() {
		/*Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.AGE_AT_FORM89_REGISTRATION), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueNumeric().intValue();
		}*/
		return MdrtbUtil.calculateAge(getPatient().getBirthdate(), getEncounterDatetime());
	}
	
	/*public void setAgeAtRegistration(Integer age) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.AGE_AT_FORM89_REGISTRATION), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.AGE_AT_FORM89_REGISTRATION), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueNumeric(new Double(age));
				encounter.addObs(obs);
			}
		} 
	}*/
	
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
		if(pa==null)
			return null;
		
		String address = pa.getCountry() + "," + pa.getStateProvince() + "," + pa.getCountyDistrict();
		if(pa.getAddress1()!=null && pa.getAddress1().length()!=0) {
			address += "," + pa.getAddress1();
			if(pa.getAddress2()!=null && pa.getAddress2().length()!=0)
				address += "," + pa.getAddress2();
		}

		return address;
	}
	
	public Concept getLocationType() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.LOCATION_TYPE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setLocationType(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.LOCATION_TYPE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && type == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(type)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(type != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.LOCATION_TYPE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getProfession() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.PROFESSION), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setProfession(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.PROFESSION), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && type == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(type)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(type != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.PROFESSION), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	/////////////
	public Concept getPopulationCategory() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.POPULATION_CATEGORY), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setPopulationCategory(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.POPULATION_CATEGORY), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && type == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(type)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(type != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.POPULATION_CATEGORY), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}///////////
	
	
	public Concept getPlaceOfDetection() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.PLACE_OF_DETECTION), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setPlaceOfDetection(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.PLACE_OF_DETECTION), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && type == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(type)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(type != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.PLACE_OF_DETECTION), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	/////////////////
	
	public Date getDateFirstSeekingHelp() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_FIRST_SEEKING_HELP), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueDatetime();
		}
	}
	
	public void setDateFirstSeekingHelp(Date date) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_FIRST_SEEKING_HELP), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_FIRST_SEEKING_HELP), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueDatetime(date);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getCircumstancesOfDetection() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.CIRCUMSTANCES_OF_DETECTION), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setCircumstancesOfDetection(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.CIRCUMSTANCES_OF_DETECTION), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && type == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(type)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(type != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.CIRCUMSTANCES_OF_DETECTION), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	/////////////////
	
	public Concept getMethodOfDetection() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.METHOD_OF_DETECTION), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setMethodOfDetection(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.METHOD_OF_DETECTION), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && type == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(type)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(type != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.METHOD_OF_DETECTION), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	/////////////////
	
	public Concept getEpSite() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.SITE_OF_EPTB), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setEpSite(Concept site) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.SITE_OF_EPTB), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.SITE_OF_EPTB), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(site);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	/////////////////
	
	public Concept getPulSite() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.PTB_SITE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setPulSite(Concept site) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.PTB_SITE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.PTB_SITE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(site);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getEpLocation() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.EPTB_SITE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setEpLocation(Concept site) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.EPTB_SITE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.EPTB_SITE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(site);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	/////////////////
	public Concept getPresenceOfDecay() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.PRESENCE_OF_DECAY), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setPresenceOfDecay(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.PRESENCE_OF_DECAY), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && type == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(type)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(type != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.PRESENCE_OF_DECAY), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	/////////////////
	
	public Date getDateOfDecaySurvey() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_DECAY_SURVEY), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueDatetime();
		}
	}
	
	public void setDateOfDecaySurvey(Date date) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_DECAY_SURVEY), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_DECAY_SURVEY), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueDatetime(date);
				encounter.addObs(obs);
			}
		} 
	}
	//////////////
	
	public Concept getDiabetes() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DIABETES), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setDiabetes(Concept site) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DIABETES), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.DIABETES), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(site);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getCnsdl() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.CNSDL), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setCnsdl(Concept site) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.CNSDL), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.CNSDL), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(site);
				encounter.addObs(obs);
			}
		} 
	}
	
	/////////////////////
	
	public Concept getHtHeartDisease() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.HYPERTENSION_OR_HEART_DISEASE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setHtHeartDisease(Concept site) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.HYPERTENSION_OR_HEART_DISEASE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.HYPERTENSION_OR_HEART_DISEASE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(site);
				encounter.addObs(obs);
			}
		} 
	}
	
	//////////////////
	
	public Concept getUlcer() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.ULCER), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setUlcer(Concept site) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.ULCER), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.ULCER), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(site);
				encounter.addObs(obs);
			}
		} 
	}
	
	/////////////////////
	
	public Concept getMentalDisorder() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.MENTAL_DISORDER), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setMentalDisorder(Concept site) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.MENTAL_DISORDER), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.MENTAL_DISORDER), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(site);
				encounter.addObs(obs);
			}
		} 
	}
	
	/////////////////////
	
	public Concept getIbc20() {
		Obs obs = null;
		
		if(tb03!=null){ 
			obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.RESULT_OF_HIV_TEST), tb03.getEncounter());
		}	
	
		if (obs == null) {
			return null;
		}
		else {
			if(obs.getValueCoded().equals(Context.getService(MdrtbService.class).getConcept(TbConcepts.POSITIVE))) {
				return Context.getService(MdrtbService.class).getConcept(TbConcepts.YES);
			}
			
			else if(obs.getValueCoded().equals(Context.getService(MdrtbService.class).getConcept(TbConcepts.NEGATIVE))) {
				return Context.getService(MdrtbService.class).getConcept(TbConcepts.NO);
			}
			
			return null;
		}
		
	}
	
	/*public void setIbc20(Concept site) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.ICD20), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.ICD20), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(site);
				encounter.addObs(obs);
			}
		} 
	}
	
	/////////////////////
*/	
	public Concept getCancer() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.CANCER), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setCancer(Concept site) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.CANCER), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.CANCER), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(site);
				encounter.addObs(obs);
			}
		} 
	}
	
/////////////////////
	
	public Concept getNoDisease() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						TbConcepts.NO_DISEASE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueCoded();
		}
	}

	public void setNoDisease(Concept site) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						TbConcepts.NO_DISEASE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && site == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueCoded() == null
				|| !obs.getValueCoded().equals(site)) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (site != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(TbConcepts.NO_DISEASE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueCoded(site);
				encounter.addObs(obs);
			}
		}
	}
	
	
	/////////////////////
	
	public String getOtherDisease() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.OTHER_DISEASE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueText();
		}
	}

	public void setOtherDisease(String site) {
		

		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.OTHER_DISEASE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && site == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueText() == null || !obs.getValueText().equals(site)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(site != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.OTHER_DISEASE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueText(site);
				encounter.addObs(obs);
			}
		} 
	}
	
	//////////
	public Date getCmacDate() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.CMAC_DATE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueDatetime();
		}
	}
	
	public void setCmacDate(Date date) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.CMAC_DATE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.CMAC_DATE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueDatetime(date);
				encounter.addObs(obs);
			}
		} 
	}
	//////////////
	
	public String getCmacNumber() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.CMAC_NUMBER), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueText();
		}
	}
	
	public void setCmacNumber(String number) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.CMAC_NUMBER), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && number == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueText() == null || !obs.getValueText().equals(number)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(number != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.CMAC_NUMBER), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueText(number);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	////////////////
	
	
	public Date getForm89Date() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.FORM89_DATE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueDatetime();
		}
	}
	
	public void setForm89Date(Date date) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.FORM89_DATE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.FORM89_DATE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueDatetime(date);
				encounter.addObs(obs);
			}
		} 
	}
	
	////////////////
	
	public Concept getPrescribedTreatment() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.GPT), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setPrescribedTreatment(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.GPT), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && type == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(type)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(type != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.GPT), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	////////
	
	public Integer getPatProgId() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.PATIENT_PROGRAM_ID), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueNumeric().intValue();
		}
	}
	
	public void setPatProgId(Integer id) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.PATIENT_PROGRAM_ID), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && id == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueNumeric() == null || obs.getValueNumeric().intValue() != id.intValue()) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(id != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.PATIENT_PROGRAM_ID), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueNumeric(new Double(id));
				encounter.addObs(obs);
			}
		} 
	}
	
	public List<SmearForm> getSmears() {
		if(getPatProgId()==null) {
			//System.out.println("GM: null program");
			return new ArrayList<SmearForm>();
		}
		
		ArrayList<SmearForm> ret = new ArrayList<SmearForm>();
		List<SmearForm> allSmears = Context.getService(MdrtbService.class).getSmearForms(getPatProgId());
		
		if(allSmears==null)
			return ret;
		else {
			for(SmearForm sf : allSmears) {
				if(sf.getMonthOfTreatment()==0) {
					ret.add(sf);
				}
			}
		}
			
		return ret;
		
	}

	public List<XpertForm> getXperts() {
		/*if(getPatProgId()==null) {
			//System.out.println("GM: null program");
			return new ArrayList<XpertForm>();
		}
		return Context.getService(MdrtbService.class).getXpertForms(getPatProgId());*/
		
		if(getPatProgId()==null) {
			//System.out.println("GM: null program");
			return new ArrayList<XpertForm>();
		}
		
		ArrayList<XpertForm> ret = new ArrayList<XpertForm>();
		List<XpertForm> allXperts = Context.getService(MdrtbService.class).getXpertForms(getPatProgId());
		
		if(allXperts==null)
			return ret;
		else {
			for(XpertForm sf : allXperts) {
				if(sf.getMonthOfTreatment()==0) {
					ret.add(sf);
				}
			}
		}
			
		return ret;
	
	}
	
	public List<HAINForm> getHains() {
/*		if(getPatProgId()==null) {
			//System.out.println("GM: null program");
			return new ArrayList<HAINForm>();
		}
		return Context.getService(MdrtbService.class).getHAINForms(getPatProgId());*/
		if(getPatProgId()==null) {
			//System.out.println("GM: null program");
			return new ArrayList<HAINForm>();
		}
		
		ArrayList<HAINForm> ret = new ArrayList<HAINForm>();
		List<HAINForm> allHains = Context.getService(MdrtbService.class).getHAINForms(getPatProgId());
		
		if(allHains==null)
			return ret;
		else {
			for(HAINForm sf : allHains) {
				if(sf.getMonthOfTreatment()==0) {
					ret.add(sf);
				}
			}
		}
			
		return ret;
	}
	
	public List<HAIN2Form> getHain2s() {
		/*if(getPatProgId()==null) {
			//System.out.println("GM: null program");
			return new ArrayList<HAIN2Form>();
		}
		return Context.getService(MdrtbService.class).getHAIN2Forms(getPatProgId());*/
		
		if(getPatProgId()==null) {
			//System.out.println("GM: null program");
			return new ArrayList<HAIN2Form>();
		}
		
		ArrayList<HAIN2Form> ret = new ArrayList<HAIN2Form>();
		List<HAIN2Form> allHains = Context.getService(MdrtbService.class).getHAIN2Forms(getPatProgId());
		
		if(allHains==null)
			return ret;
		else {
			for(HAIN2Form sf : allHains) {
				if(sf.getMonthOfTreatment()==0) {
					ret.add(sf);
				}
			}
		}
			
		return ret;
	
	}
	
	public Boolean getIsPulmonary() {
		Boolean result = null;
		
		Concept site = getAnatomicalSite();
		if(site!=null) {
		
			if(site.equals(Context.getService(MdrtbService.class).getConcept(TbConcepts.PULMONARY_TB)))
				result = new Boolean(true);
		
			else if(site.equals(Context.getService(MdrtbService.class).getConcept(TbConcepts.EXTRA_PULMONARY_TB)))
				result = new Boolean(false);
		}
		
		return result;
		
	}
	
	public String getNameOfDoctor() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.NAME_OF_DOCTOR), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueText();
		}
		
	}
	
	public void setNameOfDoctor(String name) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.NAME_OF_DOCTOR), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && name == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueText() == null || !obs.getValueText().equals(name)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(name != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.NAME_OF_DOCTOR), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueText(name);
				encounter.addObs(obs);
			}
		} 
	}
	
	public String getLink() {
		return "/module/mdrtb/form/form89.form?patientProgramId=" + getPatProgId() + "&encounterId=" + getEncounter().getId();
	}
	
}
