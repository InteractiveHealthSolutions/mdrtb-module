package org.openmrs.module.mdrtb.form.pv;

import java.util.ArrayList;
import java.util.Date;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.form.AbstractSimpleForm;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.specimen.Culture;
import org.openmrs.module.mdrtb.specimen.CultureImpl;


public class AEForm extends AbstractSimpleForm implements Comparable<AEForm>{
	
	public AEForm() {
		super();
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("Adverse Event"));		
		
	}
	
	public AEForm(Patient patient) {
		super(patient);
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("Adverse Event"));		
	}
	
	public AEForm(Encounter encounter) {
		super(encounter);
	}
	
	public Concept getAdverseEvent() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ADVERSE_EVENT), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setAdverseEvent(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ADVERSE_EVENT), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ADVERSE_EVENT), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getDiagnosticInvestigation() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LAB_TEST_CONFIRMING_AE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setDiagnosticInvestigation(Concept test) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LAB_TEST_CONFIRMING_AE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && test == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(test)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(test != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LAB_TEST_CONFIRMING_AE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(test);
				encounter.addObs(obs);
			}
		} 
	}
	/////////////////////
	
	public String getSuspectedDrug() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SUSPECTED_DRUG), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueText();
		}
	}
	
	public void setSuspectedDrug(String drug) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SUSPECTED_DRUG), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && drug == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueText() == null || (drug == null && obs != null) || !obs.getValueText().equals(drug)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(drug != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SUSPECTED_DRUG), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueText(drug);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	public String getTreatmentRegimenAtOnset() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_REGIMEN), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueText();
		}
	}
	
	public void setTreatmentRegimenAtOnset(String regimen) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_REGIMEN), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && regimen == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueText() == null || (regimen == null && obs != null) || !obs.getValueText().equals(regimen)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(regimen != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_REGIMEN), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueText(regimen);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	
	
	public Concept getTypeOfEvent() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_TYPE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setTypeOfEvent(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_TYPE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_TYPE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	////////////////////
	
	public Concept getTypeOfSAE() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SAE_TYPE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setTypeOfSAE(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SAE_TYPE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SAE_TYPE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	///////////////
	
	public Concept getTypeOfSpecialEvent() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SPECIAL_INTEREST_EVENT_TYPE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setTypeOfSpecialEvent(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SPECIAL_INTEREST_EVENT_TYPE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SPECIAL_INTEREST_EVENT_TYPE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	///////////////
	
	
	
	public Date getYellowCardDate() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.YELLOW_CARD_DATE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueDatetime();
		}
	}
	
	public void setYellowCardDate(Date date) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.YELLOW_CARD_DATE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && date == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueDatetime() == null || (date == null && obs != null) || !obs.getValueDatetime().equals(date)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(date != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.YELLOW_CARD_DATE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueDatetime(date);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getCausalityAssessmentResult1() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_ASSESSMENT_RESULT_1), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setCausalityAssessmentResult1(Concept result) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_ASSESSMENT_RESULT_1), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && result == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(result)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(result != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_ASSESSMENT_RESULT_1), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(result);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getCausalityAssessmentResult2() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_ASSESSMENT_RESULT_2), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setCausalityAssessmentResult2(Concept result) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_ASSESSMENT_RESULT_2), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && result == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(result)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(result != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_ASSESSMENT_RESULT_2), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(result);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getCausalityAssessmentResult3() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_ASSESSMENT_RESULT_3), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setCausalityAssessmentResult3(Concept result) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_ASSESSMENT_RESULT_3), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && result == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(result)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(result != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_ASSESSMENT_RESULT_3), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(result);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	public Concept getCausalityDrug1() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_DRUG_1), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setCausalityDrug1(Concept result) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_DRUG_1), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && result == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(result)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(result != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_DRUG_1), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(result);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getCausalityDrug2() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_DRUG_2), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setCausalityDrug2(Concept result) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_DRUG_2), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && result == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(result)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(result != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_DRUG_2), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(result);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getCausalityDrug3() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_DRUG_3), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setCausalityDrug3(Concept result) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_DRUG_3), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && result == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(result)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(result != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSALITY_DRUG_3), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(result);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getActionTaken() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setActionTaken(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getActionTaken2() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION_2), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setActionTaken2(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION_2), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION_2), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getActionTaken3() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION_3), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setActionTaken3(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION_3), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION_3), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getActionTaken4() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION_4), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setActionTaken4(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION_4), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION_4), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getActionTaken5() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION_5), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setActionTaken5(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION_5), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION_5), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	
	public Concept getActionOutcome() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_OUTCOME), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setActionOutcome(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_OUTCOME), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_OUTCOME), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Date getOutcomeDate() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_OUTCOME_DATE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueDatetime();
		}
	}
	
	public void setOutcomeDate(Date date) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_OUTCOME_DATE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && date == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueDatetime() == null || (date == null && obs != null) || !obs.getValueDatetime().equals(date)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(date != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_OUTCOME_DATE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueDatetime(date);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getMeddraCode() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MEDDRA_CODE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setMeddraCode(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MEDDRA_CODE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MEDDRA_CODE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getDrugRechallenge() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DRUG_RECHALLENGE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setDrugRechallenge(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DRUG_RECHALLENGE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DRUG_RECHALLENGE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	public String getComments() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CLINICIAN_NOTES), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueText();
		}
	}
	
	public void setComments(String comment) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CLINICIAN_NOTES), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && comment == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueText() == null || (comment == null && obs != null) || !obs.getValueText().equals(comment)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(comment != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CLINICIAN_NOTES), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueText(comment);
				encounter.addObs(obs);
			}
		} 
	}
	
	//////////////

	
	public int compareTo(AEForm form) {
		
		if(this.encounter.getEncounterDatetime()==null)
			return 1;
		if(form.encounter.getEncounterDatetime()==null)
			return -1;
		
		return this.encounter.getEncounterDatetime().compareTo(form.encounter.getEncounterDatetime());
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
	
	public String getFacility() {
		String ret = "";
		Location loc = this.getEncounter().getLocation();
		
		ret = loc.getStateProvince();
		if(loc.getCountyDistrict()!=null) {
			ret += "/" + loc.getCountyDistrict();
		}
		if(loc.getRegion()!=null && loc.getRegion().length()>0) {
			ret += "/" + loc.getRegion();
		}
		
		return ret;
	}
	
	
	
	public String getLink() {
		return "/module/mdrtb/form/ae.form?patientProgramId=" + getPatProgId() + "&encounterId=" + getEncounter().getId();
	}
	
	public ArrayList<Concept> getSuspectedDrugs() {
		ArrayList<Concept> drugs = new ArrayList<Concept>();
		
		Concept c = getCausalityAssessmentResult1();
		Concept d = getCausalityDrug1();
		if(c!=null && c.getId() != null && c.getId().intValue() != Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NOT_CLASSIFIED).getId().intValue());
		{
			if(d!=null)
				drugs.add(d);
		}
		
		c = getCausalityAssessmentResult2();
		d = getCausalityDrug2();
		if(c!=null && c.getId() != null && c.getId().intValue() != Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NOT_CLASSIFIED).getId().intValue());
		{
			if(d!=null)
				drugs.add(d);
		}
	
		c = getCausalityAssessmentResult3();
		d = getCausalityDrug3();
		if(c!=null && c.getId() != null && c.getId().intValue() != Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NOT_CLASSIFIED).getId().intValue());
		{
			if(d!=null)
				drugs.add(d);
		}

		return drugs;
	}
	
	public Concept getRequiresAncillaryDrugs() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REQUIRES_ANCILLARY_DRUGS), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setRequiresAncillaryDrugs(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REQUIRES_ANCILLARY_DRUGS), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REQUIRES_ANCILLARY_DRUGS), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getRequiresDoseChange() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REQUIRES_DOSE_CHANGE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setRequiresDoseChange(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REQUIRES_DOSE_CHANGE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REQUIRES_DOSE_CHANGE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	///////////////////
	
	public Concept getClinicalScreenDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CLINICAL_SCREEN_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setClinicalScreenDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CLINICAL_SCREEN_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CLINICAL_SCREEN_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	////////////////////////////////////////////////////////////////////////////
	
	public Concept getVisualAcuityDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.VISUAL_ACUITY_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setVisualAcuityDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.VISUAL_ACUITY_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.VISUAL_ACUITY_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	//////////////////////
	
	public Concept getSimpleHearingTestDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SIMPLE_HEARING_TEST_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setSimpleHearingTestDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SIMPLE_HEARING_TEST_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SIMPLE_HEARING_TEST_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	
	
	//////////////
	
	public Concept getAudiogramDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AUDIOGRAM_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setAudiogramDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AUDIOGRAM_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AUDIOGRAM_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getNeuroInvestigationDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NEURO_INVESTIGATION_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setNeuroInvestigationDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NEURO_INVESTIGATION_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NEURO_INVESTIGATION_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getSerumCreatnineDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CREATNINE_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setSerumCreatnineDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CREATNINE_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CREATNINE_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getAstDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AST_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setAstDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AST_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AST_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getAltDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ALT_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setAltDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ALT_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ALT_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getBilirubinDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.BILIRUBIN_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setBilirubinDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.BILIRUBIN_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.BILIRUBIN_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getAlkalinePhosphataseDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ALKALINE_PHOSPHATASE_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setAlkalinePhosphataseDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ALKALINE_PHOSPHATASE_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ALKALINE_PHOSPHATASE_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getYgtDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.YGT_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setYgtDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.YGT_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.YGT_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getEcgDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ECG_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setEcgDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ECG_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ECG_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getLipaseDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LIPASE_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setLipaseDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LIPASE_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LIPASE_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getAmylaseDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AMYLASE_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setAmylaseDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AMYLASE_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AMYLASE_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getPotassiumDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.POTASSIUM_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setPotassiumDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.POTASSIUM_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.POTASSIUM_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getMagnesiumDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MAGNESIUM_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setMagnesiumDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MAGNESIUM_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MAGNESIUM_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getCalciumDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CALCIUM_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setCalciumDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CALCIUM_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CALCIUM_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getAlbuminDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ALBUMIN_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setAlbuminDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ALBUMIN_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ALBUMIN_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getCbcDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CBC_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setCbcDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CBC_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CBC_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getBloodGlucoseDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.BLOOD_GLUCOSE_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setBloodGlucoseDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.BLOOD_GLUCOSE_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.BLOOD_GLUCOSE_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	public Concept getThyroidTestDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.THYROID_TEST_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setThyroidTestDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.THYROID_TEST_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.THYROID_TEST_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getOtherTestDone() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OTHER_TEST_DONE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setOtherTestDone(Concept event) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OTHER_TEST_DONE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && event == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(event)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(event != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OTHER_TEST_DONE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(event);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////
	
	
	//////////////
	
	public String getActionTakenSummary() {

		String at = "";
		
		Concept q = getActionTaken();
		if(q!=null) {
			at =  q.getName().getName();
		}
	
		q = getActionTaken2();
		if(q!=null) {
			at += ", " + q.getName().getName();
		}
		
		q = getActionTaken3();
		if(q!=null) {
			at += ", " + q.getName().getName();
		}
		
		q = getActionTaken4();
		if(q!=null) {
			at += ", " + q.getName().getName();
		}
		
		q = getActionTaken5();
		if(q!=null) {
			at += ", " + q.getName().getName();
		}
		
		
		return at;
		
		
	}
	

	public String getDiagnosticSummary() {

		String at = "";
		Concept yes = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.YES);
		int yesId = yes.getId().intValue();
		Concept q = getClinicalScreenDone();
		MessageSourceService mss = Context.getMessageSourceService();
		
		if(q!=null && q.getId().intValue()==yesId) {
			at = mss.getMessage("mdrtb.pv.clinicalScreen") + ", ";
		}
	
		q = getVisualAcuityDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.visualAcuity") + ", ";
		}
		
		q = getSimpleHearingTestDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.hearingTest") + ", ";
		}
		
		q = getAudiogramDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.audiogram") + ", ";
		}
		
		q = getNeuroInvestigationDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.neuroInvestigations") + ", ";
		}
		
		q = getSerumCreatnineDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.serumCreatnine") + ", ";
		}
		
		q = getAltDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.alt") + ", ";
		}
		
		q = getAstDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.ast") + ", ";
		}
		
		q = getBilirubinDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.bilirubin") + ", ";
		}
		
		q = getAlkalinePhosphataseDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.alkalinePhophatase") + ", ";
		}
		
		q = getYgtDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.ygt") + ", ";
		}
		
		q = getLipaseDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.lipase") + ", ";
		}
		
		q = getAmylaseDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.amylase") + ", ";
		}
		
		q = getPotassiumDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.potassium") + ", ";
		}
		
		q = getMagnesiumDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.magnesium") + ", ";
		}
		
		q = getCalciumDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.calcium") + ", ";
		}
		
		q = getAlbuminDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.albumin") + ", ";
		}
		
		q = getCbcDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.cbc") + ", ";
		}
		
		q = getBloodGlucoseDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.bloodGlucose") + ", ";
		}
		
		q = getThyroidTestDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.thyroidTest") + ", ";
		}
		
		q = getOtherTestDone();
		if(q!=null && q.getId().intValue()==yesId) {
			at += mss.getMessage("mdrtb.pv.other") + ", ";
		}
		
		if(at!=null && at.length()>0 )
			at = at.substring(0, at.length()-2);

		return at;
		
		
	}
}
