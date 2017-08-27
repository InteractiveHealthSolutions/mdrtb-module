package org.openmrs.module.mdrtb.form;



import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.specimen.Xpert;
import org.openmrs.module.mdrtb.specimen.XpertImpl;


public class XpertForm extends AbstractSimpleForm {

	private Xpert xpert;
	
	public XpertForm() {
		super();
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("Specimen Collection"));		
		
	}
	
	public XpertForm(Patient patient) {
		super(patient);
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("Specimen Collection"));		
	}
	
	public XpertForm(Encounter encounter) {
		super(encounter);
		xpert = new XpertImpl(encounter);
	}

	/*public Integer getMonthOfTreatment() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.MONTH_OF_TREATMENT), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueNumeric().intValue();
		}
	}
	
	public void setMonthOfTreatment(Integer month) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.MONTH_OF_TREATMENT), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.MONTH_OF_TREATMENT), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueNumeric(new Double(month));
				encounter.addObs(obs);
			}
		} 
	}*/
	
	public String getSpecimenId() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.SPECIMEN_ID), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueText();
		}
	}
	
	public void setSpecimenId(String id) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.SPECIMEN_ID), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && id == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueText() == null || obs.getValueText() != id) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(id != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.SPECIMEN_ID), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueText(id);
				encounter.addObs(obs);
			}
		} 
	}
	
	public Concept getMtbResult() {
		Obs obsgroup = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.XPERT_CONSTRUCT), encounter);
		Obs obs = null;
		
		if(obsgroup!=null)
			obs = MdrtbUtil.getObsFromObsGroup(Context.getService(MdrtbService.class).getConcept(TbConcepts.MTB_RESULT), obsgroup);
		
		if (obs == null) {
			System.out.println("Null result");
			return null;
		}
		else {
			System.out.println("ValCo: " + obs.getValueCoded() );
			return obs.getValueCoded();
		}
	}
	
	public void setMtbResult(Concept result) {
		System.out.println("result" + result);
		Obs obsgroup = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.XPERT_CONSTRUCT), encounter);
		Obs obs = null;
		
		if(obsgroup!=null)
		{
			obs = MdrtbUtil.getObsFromObsGroup(Context.getService(MdrtbService.class).getConcept(TbConcepts.MTB_RESULT), obsgroup);
			
		}
		
		else {
			obsgroup = new Obs(encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.XPERT_CONSTRUCT), encounter.getEncounterDatetime(), encounter.getLocation());
		}
		
		// if this obs have not been created, and there is no data to add, do nothing
		System.out.println("OG:" + obsgroup);
		System.out.println("O:" + obs);
		if (obs == null && result == null) {
			System.out.println("no xpert result hcange");
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(result)) {
			System.out.println("new obs or value change");
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				System.out.println("not null obs");
//				obsgroup.setVoided(true);
//				obsgroup.setVoidReason("voided by Mdr-tb module specimen tracking UI");
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(result != null) {
				System.out.println("creating new obs");
				//obsgroup = new Obs(encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.XPERT_CONSTRUCT), encounter.getEncounterDatetime(), encounter.getLocation());
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.MTB_RESULT), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(result);
				obs.setObsGroup(obsgroup);
				obsgroup.addGroupMember(obs);
				encounter.addObs(obs);
				encounter.addObs(obsgroup);
				//encounter.
			}
		} 
	}
	
	public Concept getRifResult() {
		Obs obsgroup = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.XPERT_CONSTRUCT), encounter);
		Obs obs = null;
		
		if(obsgroup!=null)
			obs = MdrtbUtil.getObsFromObsGroup(Context.getService(MdrtbService.class).getConcept(TbConcepts.RIFAMPICIN_RESISTANCE), obsgroup);
		
		if (obs == null) {
			System.out.println("Null result");
			return null;
		}
		else {
			System.out.println("ValCo: " + obs.getValueCoded() );
			return obs.getValueCoded();
		}
	}
	
	public void setRifResult(Concept result) {
		System.out.println("result" + result);
		Obs obsgroup = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.XPERT_CONSTRUCT), encounter);
		Obs obs = null;
		
		if(obsgroup!=null)
		{
			obs = MdrtbUtil.getObsFromObsGroup(Context.getService(MdrtbService.class).getConcept(TbConcepts.RIFAMPICIN_RESISTANCE), obsgroup);
			
		}
		
		else {
			obsgroup = new Obs(encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.XPERT_CONSTRUCT), encounter.getEncounterDatetime(), encounter.getLocation());
		}
		
		// if this obs have not been created, and there is no data to add, do nothing
		System.out.println("OG:" + obsgroup);
		System.out.println("O:" + obs);
		if (obs == null && result == null) {
			System.out.println("no xpert result hcange");
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(result)) {
			System.out.println("new obs or value change");
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				System.out.println("not null obs");
//				obsgroup.setVoided(true);
//				obsgroup.setVoidReason("voided by Mdr-tb module specimen tracking UI");
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(result != null) {
				System.out.println("creating new obs");
				//obsgroup = new Obs(encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.XPERT_CONSTRUCT), encounter.getEncounterDatetime(), encounter.getLocation());
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.RIFAMPICIN_RESISTANCE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(result);
				obs.setObsGroup(obsgroup);
				obsgroup.addGroupMember(obs);
				encounter.addObs(obs);
				encounter.addObs(obsgroup);
				//encounter.
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
}
