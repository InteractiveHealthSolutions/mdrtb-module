package org.openmrs.module.mdrtb.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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



public class TB03uForm extends AbstractSimpleForm {
	
	public TB03uForm() {
		super();
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("TB03u - MDR"));		
		
	}
	
	public TB03uForm(Patient patient) {
		super(patient);
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("TB03u - MDR"));		
	}
	
	public TB03uForm(Encounter encounter) {
		super(encounter);
	}
	
	
	
	public String getTb03RegistrationNumber() {
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
	}
	
	public Integer getTb03RegistrationYear() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.TB03_REGISTRATION_YEAR), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueNumeric().intValue();
		}
	}
	
	public void setTb03RegistrationYear(Integer year) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.TB03_REGISTRATION_YEAR), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && year == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueNumeric() == null || obs.getValueNumeric().intValue() != year.intValue()) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(year != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.TB03_REGISTRATION_YEAR), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueNumeric(new Double(year));
				encounter.addObs(obs);
			}
		} 
	}
	
	
	
	public Integer getAgeAtMDRRegistration() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.AGE_AT_MDR_REGISTRATION), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueNumeric().intValue();
		}
	}
	
	public void setAgeAtMDRRegistration(Integer age) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.AGE_AT_MDR_REGISTRATION), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.AGE_AT_MDR_REGISTRATION), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueNumeric(new Double(age));
				encounter.addObs(obs);
			}
		} 
	}
	
	public String getSldRegisterNumber() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REGIMEN_2_REG_NUMBER), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueText();
		}
	}
	
	public void setSldRegisterNumber(String number) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REGIMEN_2_REG_NUMBER), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REGIMEN_2_REG_NUMBER), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueText(number);
				encounter.addObs(obs);
			}
		} 
	}
	
	public String getGender() {
		if(getPatient().getGender().equals("M"))
			return Context.getMessageSourceService().getMessage("mdrtb.tb03.gender.male");
		if(getPatient().getGender().equals("F"))
			return Context.getMessageSourceService().getMessage("mdrtb.tb03.gender.female");
		
		return "";
	}
	
	public Date getDateOfBirth() {
		return getPatient().getBirthdate();
	}
	
	public String getAddress() {
		
		PersonAddress pa = getPatient().getPersonAddress();
		String address = pa.getCountry() + "," + pa.getStateProvince() + "," + pa.getCountyDistrict();
		if(pa.getAddress1()!=null && pa.getAddress1().length()!=0) {
			address += "," + pa.getAddress1();
			if(pa.getAddress2()!=null && pa.getAddress2().length()!=0)
				address += "," + pa.getAddress2();
		}

		return address;
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
	
	public Concept getRegistrationGroup() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAT_4_CLASSIFICATION_PREVIOUS_TX), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setRegistrationGroup(Concept group) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAT_4_CLASSIFICATION_PREVIOUS_TX), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAT_4_CLASSIFICATION_PREVIOUS_TX), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(group);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getMdrStatus() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_STATUS), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setMdrStatus(Concept status) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_STATUS), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_STATUS), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(status);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Date getConfirmationDate() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDTRB_CONFIRMATION_DATE) , encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueDatetime();
		}
	}
	
	public void setConfirmationDate(Date date) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDTRB_CONFIRMATION_DATE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDTRB_CONFIRMATION_DATE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueDatetime(date);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	
	/*public void setTreatmentSiteIP(Concept site) {
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
	}*/
	
	
	/*public Concept getTreatmentSiteCP() {
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
	}*/
	
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
	
	public Date getMdrTreatmentStartDate() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.MDR_TREATMENT_START_DATE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueDatetime();
		}
	}
	
	public void setMdrTreatmentStartDate(Date date) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.MDR_TREATMENT_START_DATE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.MDR_TREATMENT_START_DATE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueDatetime(date);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getTxLocation() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.TX_LOCATION), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setTxLocation(Concept cat) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.TX_LOCATION), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.TX_LOCATION), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(cat);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getResistanceType() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.RESISTANCE_TYPE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setResistanceType(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.RESISTANCE_TYPE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.RESISTANCE_TYPE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getBasisForDiagnosis() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.BASIS_FOR_TB_DIAGNOSIS), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setBasisForDiagnosis(Concept basis) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.BASIS_FOR_TB_DIAGNOSIS), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && basis == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(basis)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(basis != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.BASIS_FOR_TB_DIAGNOSIS), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(basis);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Date getHivTestDate() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_HIV_TEST), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueDatetime();
		}
	}
	
	public void setHivTestDate(Date date) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_HIV_TEST), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_HIV_TEST), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueDatetime(date);
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
	
	public Date getArtStartDate() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_ART_TREATMENT_START), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueDatetime();
		}
	}
	
	public void setArtStartDate(Date date) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_ART_TREATMENT_START), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_ART_TREATMENT_START), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueDatetime(date);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Date getPctStartDate() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_PCT_TREATMENT_START), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueDatetime();
		}
	}
	
	public void setPctStartDate(Date date) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_PCT_TREATMENT_START), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_PCT_TREATMENT_START), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueDatetime(date);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	
	public Concept getTreatmentOutcome() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TB_TX_OUTCOME), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setTreatmentOutcome(Concept outcome) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.MDR_TB_TX_OUTCOME), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && outcome == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(outcome)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(outcome != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.MDR_TB_TX_OUTCOME), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(outcome);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Date getTreatmentOutcomeDate() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.TX_OUTCOME_DATE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueDatetime();
		}
	}
	
	public void setTreatmentOutcomeDate(Date date) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.TX_OUTCOME_DATE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.TX_OUTCOME_DATE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueDatetime(date);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Date getDateOfDeathAfterOutcome() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_DEATH_AFTER_OUTCOME), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueDatetime();
		}
	}
	
	public void setDateOfDeathAfterOutcome(Date date) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_DEATH_AFTER_OUTCOME), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_DEATH_AFTER_OUTCOME), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueDatetime(date);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getRelapsed() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RELAPSED), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setRelapsed(Concept rel) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.RELAPSED), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && rel == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(rel)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(rel != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.RELAPSED), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(rel);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Integer getRelapseMonth() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.RELAPSE_MONTH), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueNumeric().intValue();
		}
	}
	
	public void setRelapseMonth(Integer month) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.RELAPSE_MONTH), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && month == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueNumeric() == null || obs.getValueNumeric().intValue() != month.intValue()) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(month != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.RELAPSE_MONTH), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueNumeric(new Double(month));
				encounter.addObs(obs);
			}
		} 
	}
	
	public String getCliniciansNotes() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.CLINICIAN_NOTES), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueText();
		}
	}
	
	public void setCliniciansNotes(String notes) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.CLINICIAN_NOTES), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && notes == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueText() == null || obs.getValueText() != notes) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(notes != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.CLINICIAN_NOTES), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueText(notes);
				encounter.addObs(obs);
			}
		} 
	}
	
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
			System.out.println("GM: null program");
			return new ArrayList<SmearForm>();
		}
		return Context.getService(MdrtbService.class).getSmearForms(getPatProgId());
		
	}

	public List<CultureForm> getCultures() {
		if(getPatProgId()==null) {
			System.out.println("GM: null program");
			return new ArrayList<CultureForm>();
		}
		return Context.getService(MdrtbService.class).getCultureForms(getPatProgId());
	
	}

}
