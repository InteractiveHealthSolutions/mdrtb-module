package org.openmrs.module.mdrtb.form;



import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.specimen.Dst;
import org.openmrs.module.mdrtb.specimen.DstImpl;
import org.openmrs.module.mdrtb.specimen.DstResult;
import org.openmrs.module.mdrtb.specimen.DstResultImpl;
import org.openmrs.module.mdrtb.specimen.HAIN;
import org.openmrs.module.mdrtb.specimen.HAINImpl;


public class DSTForm extends AbstractSimpleForm {

	/*Map<Integer,List<DstResult>> resultsMap = null;
	Obs dstResult;
	Concept drug; // a secondary location to store drug-type; used as a workaround for the odd way that the result and the drug are stored in a single obs
	
	Set<Concept> resultSet; // stores all the possible values for the results
*/	
	public DstImpl di;
	
	public DSTForm() {
		super();
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("Specimen Collection"));
		di = new DstImpl(this.encounter);
		
		/*dstResult = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DST_CONSTRUCT), encounter);
		if(dstResult==null) {
			dstResult = new Obs();
			dstResult.setConcept(Context.getService(MdrtbService.class).getConcept(TbConcepts.DST_CONSTRUCT));
			dstResult.setEncounter(this.encounter);
		}
		getResultsMap();*/
		
	}
	
	public DSTForm(Patient patient) {
		super(patient);
		this.encounter.setEncounterType(Context.getEncounterService().getEncounterType("Specimen Collection"));
		di = new DstImpl(this.encounter);
		/*dstResult = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DST_CONSTRUCT), encounter);
		if(dstResult==null) {
			dstResult = new Obs();
			dstResult.setConcept(Context.getService(MdrtbService.class).getConcept(TbConcepts.DST_CONSTRUCT));
			dstResult.setEncounter(this.encounter);
		}
		getResultsMap();*/
	}
	
	public DSTForm(Encounter encounter) {
		super(encounter);
		di = new DstImpl(this.encounter);
		/*dstResult = MdrtbUtil.getObsFromEncounter(Context.getService(MdrtbService.class).getConcept(TbConcepts.DST_CONSTRUCT), encounter);
		if(dstResult==null) {
			dstResult = new Obs();
			dstResult.setConcept(Context.getService(MdrtbService.class).getConcept(TbConcepts.DST_CONSTRUCT));
			dstResult.setEncounter(this.encounter);
		}
		getResultsMap();*/
		
	}
	
	//public Map<Integer, List<DstResult>> getResultsMap

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
	
	public DstResult addResult() {
		return di.addResult();
	}
	
	 public List<DstResult> getResults() {
		 return di.getResults();
	 }
	 
	 public Map<Integer,List<DstResult>> getResultsMap() {
		 return di.getResultsMap();
	 }
	 
	 public void removeResult(DstResult result) {
		 di.removeResult(result);
	 }
	 
	 public DstImpl getDi() {
		 return di;
	 }
	 
	 public void setDi(DstImpl di) {
		 this.di = di;
	 }
	
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
	
	/*public DstResult addResult() {
		// create a new obs for the result, set to the proper values
		Obs resultObs = new Obs(getPatient(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DST_RESULT), dstResult.getObsDatetime(), dstResult.getLocation());
		resultObs.setEncounter(getEncounter());
		
		// add the result to this obs group
		dstResult.addGroupMember(resultObs);
		
		// now create and return a new DstResult
		return new DstResultImpl(resultObs);
	}
	
	 public List<DstResult> getResults() {
	    	List<DstResult> results = new LinkedList<DstResult>();
			
			// iterate through all the obs groups, create dst results from them, and add them to the list
			if(dstResult.getGroupMembers() != null) {
				for(Obs obs : dstResult.getGroupMembers()) {
					// need to filter for voided obs, since get group members returns voided and non-voided
					if (!obs.isVoided() && obs.getConcept().equals(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DST_RESULT))) {
						results.add(new DstResultImpl(obs));
					}
				}
			}
			return results;
	    }
	    
	    // Note this is created ONCE per instantiation, for performance reasons, so if underlying drugs change, this will be inaccurate
	    public Map<Integer,List<DstResult>> getResultsMap() {  
	    
	    	if (resultsMap == null) {   		
	    		resultsMap = new HashMap<Integer,List<DstResult>>();
	    	
	    		// map the results based on a key created by concatenating the string representation of the drug concept id and the
	    		// string representation of the concentration
	    		for(DstResult result : getResults()) {
	    			
	    			Integer drug = result.getDrug().getId();
	    			
	    			// if a result for this drug already exists in the map, attach this result to that list
	    			if(resultsMap.containsKey(drug)) {
	    				resultsMap.get(drug).add(result);
	    				// re-sort, so that the concentrations are in order
	    				Collections.sort(resultsMap.get(drug));
	    			}
	    			// otherwise, create a new entry for this drug
	    			else {
	    				List<DstResult> drugResults = new LinkedList<DstResult>();
	    				drugResults.add(result);
	    				resultsMap.put(drug, drugResults);
	    			}
	    			
	    			// TODO: remove this when we are sure we don't need it
	    			*//**
	    			if(result.getConcentration() != null) {
	    				resultsMap.put((result.getDrug().getId()).toString() + "|" + result.getConcentration().toString(), result);
	    			}
	    			else {
	    				resultsMap.put((result.getDrug().getId()).toString(), result);
	    			}
	    			*//*
	    		}
	    	}
	    	
	    	return resultsMap;
	    }
	    
	    public void removeResult(DstResult result) {
	    	((DstResultImpl) result).voidResult();
	    }
	    
	    public Obs getDstResult() {
	    	return dstResult;
	    }*/
}
