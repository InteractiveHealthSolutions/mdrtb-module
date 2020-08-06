package org.openmrs.module.mdrtb.form;

import java.util.Date;
import java.util.HashSet;

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
import org.openmrs.module.mdrtb.specimen.Smear;
import org.openmrs.module.mdrtb.specimen.SmearImpl;
import org.openmrs.module.mdrtb.specimen.Specimen;


public class TransferOutForm extends AbstractSimpleForm {

	
	
	public TransferOutForm() {
		super();
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("Transfer Out"));		
		
	}
	
	public TransferOutForm(Patient patient) {
		super(patient);
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("Transfer Out"));		
	}
	
	public TransferOutForm(Encounter encounter) {
		super(encounter);
		
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
	
	
	
	public String getLink() {
		return "/module/mdrtb/form/transferOut.form?patientProgramId=" + getPatProgId() + "&encounterId=" + getEncounter().getId();
	}
	
}
