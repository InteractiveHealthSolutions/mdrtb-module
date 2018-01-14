package org.openmrs.module.mdrtb.form;



import java.util.Date;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.specimen.Culture;
import org.openmrs.module.mdrtb.specimen.CultureImpl;


public class RegimenForm extends AbstractSimpleForm implements Comparable<RegimenForm>{
	
	
	
	public RegimenForm() {
		super();
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("PV Regimen"));		
		
	}
	
	public RegimenForm(Patient patient) {
		super(patient);
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("PV Regimen"));		
	}
	
	public RegimenForm(Encounter encounter) {
		super(encounter);
	}

	public Date getCouncilDate() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.CMAC_DATE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueDatetime();
		}
	}
	
	public void setCouncilDate(Date date) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.CMAC_DATE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.CMAC_DATE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueDatetime(date);
				encounter.addObs(obs);
			}
		} 
	}
	
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
		if (obs == null || obs.getValueText() == null || (number == null && obs != null) || !obs.getValueText().equals(number)) {
			
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
	
	public Concept getPlaceOfCommission() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.PLACE_OF_ELECTORAL_COMMISSION), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
		
	}
	
	public void setPlaceOfCommission(Concept place) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.PLACE_OF_ELECTORAL_COMMISSION), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && place == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(place)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(place != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(TbConcepts.PLACE_OF_ELECTORAL_COMMISSION), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(place);
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
	
	public Concept getFundingSource() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.FUNDING_SOURCE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setFundingSource(Concept source) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.FUNDING_SOURCE), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && source == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueCoded() == null || !obs.getValueCoded().equals(source)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(source != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.FUNDING_SOURCE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(source);
				encounter.addObs(obs);
			}
		} 
	}
	/////////////////////
	
	public Concept getSldRegimenType() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SLD_REGIMEN_TYPE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueCoded();
		}
	}
	
	public void setSldRegimenType(Concept type) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SLD_REGIMEN_TYPE), encounter);
		
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
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SLD_REGIMEN_TYPE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueCoded(type);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	//////////////////////
	public Double getCmDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CM_DOSE), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueNumeric();
		}
	}
	
	public void setCmDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CM_DOSE), encounter);
		/*System.out.println("CM: " + dose );
		System.out.println("Obs: " + obs ); */
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && dose == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueNumeric() == null || (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(dose != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CM_DOSE), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		} 
	}
	//////////////////
	
	///////////////
	
	public Double getAmDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.AM_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setAmDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.AM_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null || (dose == null && obs != null)
				|| obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.AM_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	//////////////////
	
	public Double getMfxDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.MFX_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setMfxDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.MFX_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.MFX_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	//////////////////////////////////
	
	public Double getLfxDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.LFX_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setLfxDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.LFX_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.LFX_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	/////////////////////////////////////
	
	public Double getPtoDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.PTO_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setPtoDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.PTO_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.PTO_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	/////////////////////////////////////////////
	
	public Double getCsDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.CS_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setCsDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.CS_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.CS_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	//////////////////////////////////////
	
	public Double getPasDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.PAS_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setPasDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.PAS_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.PAS_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	///////////////////////////////////////
	
	public Double getZDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.Z_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setZDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.Z_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.Z_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	///////////////////////////////////////////
	
	public Double getEDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.E_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setEDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.E_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.E_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	/////////////////////////////////////////
	
	public Double getHDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.H_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setHDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.H_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.H_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	/////////////////////////////////////////////
	
	public Double getLzdDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.LZD_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setLzdDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.LZD_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.LZD_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	/////////////////////////////////////////////////////////
	
	public Double getCfzDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.CFZ_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setCfzDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.CFZ_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.CFZ_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	//////////////////////////////////////////////////
	
	public Double getBdqDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.BDQ_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setBdqDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.BDQ_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.BDQ_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	/////////////////////////////////////////////////
	
	public Double getDlmDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.DLM_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setDlmDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.DLM_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.DLM_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	//////////////////////////////////////////////////
	
	public Double getImpDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.IMP_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setImpDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.IMP_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.IMP_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	////////////////////////////////////////////////
	
	public Double getHrDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.HR_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setHrDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.HR_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.HR_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	///////////////////////////////////////////////
	
	public Double getHrzeDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.HRZE_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setHrzeDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.HRZE_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.HRZE_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	///////////////////////////////////////////////
	
	public Double getSDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.S_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setSDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.S_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.S_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	///////////////////////////////////////////////
	
	public Double getOtherDrug1Dose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.OTHER_DRUG_1_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setOtherDrug1Dose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.OTHER_DRUG_1_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.OTHER_DRUG_1_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	/////////////////////////////////////////////
	
	public String getOtherDrug1Name() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OTHER_DRUG_1_NAME), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueText();
		}
	}
	
	public void setOtherDrug1Name(String name) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OTHER_DRUG_1_NAME), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && name == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueText() == null || (name == null && obs != null) || !obs.getValueText().equals(name)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(name != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OTHER_DRUG_1_NAME), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueText(name);
				encounter.addObs(obs);
			}
		} 
	}
	
	////////////////////////////////////////////
	
	public Double getAmxDose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.AMX_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setAmxDose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.AMX_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| (dose == null && obs != null) || obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.AMX_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}
	
	///////////////////////////////////////
	
	public String getOtherRegimen() {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SLD_TREATMENT_REGIMEN), encounter);
		
		if (obs == null) {
			return null;
		}
		else {
			return obs.getValueText();
		}
	}
	
	public void setOtherRegimen(String name) {
		Obs obs = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SLD_TREATMENT_REGIMEN), encounter);
		
		// if this obs have not been created, and there is no data to add, do nothing
		if (obs == null && name == null) {
			return;
		}
		
		// we only need to update this if this is a new obs or if the value has changed.
		if (obs == null || obs.getValueText() == null || (name == null && obs != null) || !obs.getValueText().equals(name)) {
			
			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}
				
			// now create the new Obs and add it to the encounter	
			if(name != null) {
				obs = new Obs (encounter.getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SLD_TREATMENT_REGIMEN), encounter.getEncounterDatetime(), encounter.getLocation());
				obs.setValueText(name);
				encounter.addObs(obs);
			}
		} 
	}
	
	
	
	/////////////////////////////////////
	
	
	
	
	
	/*public Double getOtherDrug2Dose() {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.OTHER_DRUG_2_DOSE), encounter);

		if (obs == null) {
			return null;
		} else {
			return obs.getValueNumeric();
		}
	}

	public void setOtherDrug2Dose(Double dose) {
		Obs obs = MdrtbUtil.getObsFromEncounter(
				Context.getService(MdrtbService.class).getConcept(
						MdrtbConcepts.OTHER_DRUG_2_DOSE), encounter);

		// if this obs have not been created, and there is no data to add, do
		// nothing
		if (obs == null && dose == null) {
			return;
		}

		// we only need to update this if this is a new obs or if the value has
		// changed.
		if (obs == null || obs.getValueNumeric() == null
				|| obs.getValueNumeric() != dose.doubleValue()) {

			// void the existing obs if it exists
			// (we have to do this manually because openmrs doesn't void obs
			// when saved via encounters)
			if (obs != null) {
				obs.setVoided(true);
				obs.setVoidReason("voided by Mdr-tb module specimen tracking UI");
			}

			// now create the new Obs and add it to the encounter
			if (dose != null) {
				obs = new Obs(encounter.getPatient(), Context.getService(
						MdrtbService.class).getConcept(MdrtbConcepts.OTHER_DRUG_2_DOSE),
						encounter.getEncounterDatetime(),
						encounter.getLocation());
				obs.setValueNumeric(new Double(dose));
				encounter.addObs(obs);
			}
		}
	}*/
	
	public int compareTo(RegimenForm form) {
		
		if(this.getCouncilDate()==null)
			return 1;
		if(form.getCouncilDate()==null)
			return -1;
		
		return this.getCouncilDate().compareTo(form.getCouncilDate());
	}
	
	
	
	
	
	public String getLink() {
		return "/module/mdrtb/form/regimen.form?patientProgramId=" + getPatProgId() + "&encounterId=" + getEncounter().getId();
	}
}
