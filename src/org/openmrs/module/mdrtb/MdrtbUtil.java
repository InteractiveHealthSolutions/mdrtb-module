package org.openmrs.module.mdrtb;

import java.lang.reflect.Method;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.ConceptNameTag;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAddress;
import org.openmrs.Program;
import org.openmrs.ProgramWorkflowState;
import org.openmrs.api.BlankIdentifierException;
import org.openmrs.api.IdentifierNotUniqueException;
import org.openmrs.api.PatientIdentifierException;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.reporting.definition.AgeAtMDRRegistrationCohortDefinition;
import org.openmrs.module.mdrtb.reporting.ReportUtil;
import org.openmrs.module.mdrtb.exception.MdrtbAPIException;
import org.openmrs.module.mdrtb.form.CultureForm;
import org.openmrs.module.mdrtb.form.HAIN2Form;
import org.openmrs.module.mdrtb.form.HAINForm;
import org.openmrs.module.mdrtb.form.SmearForm;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.form.XpertForm;
import org.openmrs.module.mdrtb.regimen.Regimen;
import org.openmrs.module.mdrtb.regimen.RegimenUtils;
import org.openmrs.module.mdrtb.reporting.data.Cohorts;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.specimen.Specimen;
import org.openmrs.module.mdrtb.specimen.Test;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.springframework.validation.Errors;
import org.joda.time.LocalDate;
import org.joda.time.Years;

public class MdrtbUtil {
    
    protected static final Log log = LogFactory.getLog(MdrtbUtil.class);
    
    public static String getMdrtbPatientIdentifier(Patient p){
        String ret = "";
        String piList = Context.getAdministrationService().getGlobalProperty("mdrtb.patient_identifier_type");    
        Set<PatientIdentifier> identifiers = p.getIdentifiers();
        for (PatientIdentifier pi : identifiers){
            if (pi.getIdentifierType().getName().equals(piList)){
                return pi.getIdentifier();
            }
        }
        if (identifiers.size() > 0){
            for (PatientIdentifier pi : identifiers){
                return pi.getIdentifier();
            }
        } 
        return ret;
    }

    public static Obs getMostRecentObs(Integer conceptId, Patient p){
        Concept c = Context.getConceptService().getConcept(conceptId);
        List<Obs> oList = Context.getObsService().getObservationsByPersonAndConcept(p, c);
        if (oList.size() > 0)
            return oList.get(oList.size()-1);
        return null;
    }
   
      
    /**
  	 * Iterates through all the obs in the test obs group and
  	 * returns the first one that who concept matches the specified concept
  	 * Returns null if obs not found
  	 * @param group TODO
  	 */
      public static Obs getObsFromObsGroup(Concept concept, Obs group) {
      	if (group.getGroupMembers() != null) {
      		for(Obs obs : group.getGroupMembers()) {
      			// need to check for voided obs here because getGroupMembers returns voided obs
      			if (!obs.isVoided() && obs.getConcept().equals(concept)) {
      				return obs;
      			}
      		}
      	}
      	return null;
      }
      
     /**
  	 * Iterates through all the top-level obs in the encounter and
  	 * returns the first one that who concept matches the specified concept
  	 * Returns null if obs not found
  	 */
  	public static Obs getObsFromEncounter(Concept concept, Encounter encounter) {
  		if (encounter.getObsAtTopLevel(false) != null) {
  			for (Obs obs : encounter.getObsAtTopLevel(false)) {
  				if (!obs.isVoided() && obs.getConcept().equals(concept)) {
  					return obs;
  				}
  			}
  		}
  		return null;
  	}
	
	/**
	 * Gets the antiretroviral regimens for a current patient
	 */
	public static List<Regimen> getAntiretroviralRegimens(Patient patient) {
    	
    	if (patient == null) {
    		return null;
    	}
    	
    	return RegimenUtils.getHivRegimenHistory(patient).getAllRegimens();
    }

	/**
	 * Returns a set of all encounter types associated with the MDR-TB Program
	 */
	public static Set<EncounterType> getMdrtbEncounterTypes() {

		Set<EncounterType> types = new HashSet<EncounterType>();
		types.add(Context.getEncounterService().getEncounterType(Context.getAdministrationService().getGlobalProperty("mdrtb.mdrtbIntake_encounter_type")));
    	types.add(Context.getEncounterService().getEncounterType("TB03u - XDR"));
    	types.add(Context.getEncounterService().getEncounterType(Context.getAdministrationService().getGlobalProperty("mdrtb.specimen_collection_encounter_type")));
    	types.add(Context.getEncounterService().getEncounterType(Context.getAdministrationService().getGlobalProperty("mdrtb.transfer_out_encounter_type")));
    	types.add(Context.getEncounterService().getEncounterType(Context.getAdministrationService().getGlobalProperty("mdrtb.transfer_in_encounter_type")));
    	types.add(Context.getEncounterService().getEncounterType("Resistance During Treatment"));
    	
    	return types;
	}
	
	
	/**
	 * Returns all the concepts that represent positive results for a smear or culture
	 */
    public static Set<Concept> getPositiveResultConcepts() {
    	MdrtbService service = Context.getService(MdrtbService.class);
    	
    	// create a list of all concepts that represent positive results
    	Set<Concept> positiveResults = new HashSet<Concept>();
    	positiveResults.add(service.getConcept(MdrtbConcepts.STRONGLY_POSITIVE));
    	positiveResults.add(service.getConcept(MdrtbConcepts.MODERATELY_POSITIVE));
    	positiveResults.add(service.getConcept(MdrtbConcepts.WEAKLY_POSITIVE));
    	positiveResults.add(service.getConcept(MdrtbConcepts.POSITIVE));
    	positiveResults.add(service.getConcept(MdrtbConcepts.SCANTY));
    	positiveResults.add(service.getConcept(TbConcepts.LOWAFB));
    	
    	return positiveResults;
    }
    
    /**
     * Returns the concept ids of all the concepts that represent positive results for a smear or culture
     */
    public static Integer [] getPositiveResultConceptIds() {
    	Set<Concept> positiveConcepts = getPositiveResultConcepts();
    	Integer [] positiveResultIds = new Integer[positiveConcepts.size()];
    	
    	int i = 0;
    	for (Concept positiveConcept : positiveConcepts) {
    		positiveResultIds[i] = positiveConcept.getConceptId();
    		i++;
    	}
    	
    	return positiveResultIds;
    }
 
    /**
     * Loads and sorts the drugs stored in the global property mdtrb.defaultDstDrugs
     */
    public static List<List<Object>> getDefaultDstDrugs() {
    	List<List<Object>> drugs = new LinkedList<List<Object>>();
    	
    	String defaultDstDrugs = Context.getAdministrationService().getGlobalProperty("mdrtb.defaultDstDrugs");
    	
    	if(StringUtils.isNotBlank(defaultDstDrugs)) {
    		// split on the pipe
    		for (String drugString : defaultDstDrugs.split("\\|")) {
    			
    			// now split into a name and concentration 
    			String drug = drugString.split(":")[0];
    			String concentration = null;
    			if (drugString.split(":").length > 1) {
    				concentration = drugString.split(":")[1];
    			}
    			
    			try {
    				// see if this is a concept id
    				Integer conceptId = Integer.valueOf(drug);
    				Concept concept = Context.getConceptService().getConcept(conceptId);
    				
    				if (concept == null) {
    					log.error("Unable to find concept referenced by id " + conceptId);
    				}
    				// add the concept/concentration pair to the list
    				else {
    					addDefaultDstDrugToMap(drugs, concept, concentration);
    				}
    			}    	
    			catch (NumberFormatException e) {
    				// if not a concept id, must be a concept map
    				Concept concept = Context.getService(MdrtbService.class).getConcept(drug);
    				
    				if (concept == null) {
    					log.error("Unable to find concept referenced by " + drug);
    				}
    				// add the concept to the list
    				else {
    					addDefaultDstDrugToMap(drugs, concept, concentration);
    				}
    			}
    		}
    	}
    	
    	return drugs;
    }
    
    /**
     * Private helper method used by getDefaultDstDrugs
     * (Adds an element to the default dst drug map)
     */
    private static void addDefaultDstDrugToMap(List<List<Object>> drugs, Concept concept, String concentration) {
    	List<Object> data = new LinkedList<Object>();
    	data.add(concept);
    	data.add(concentration);
    	drugs.add(data);
    }
    
    
	/**
	 * Given a list of concepts, sorts them in the same order as the list of MDR-TB drugs
	 * (All non-MDR-TB drugs are ignored) 
	 */
    // returns by getMdrtbDrugs(); all non-MDR-TB drug are ignored
    public static List<Concept> sortMdrtbDrugs(List<Concept> drugs) {
    	return MdrtbUtil.sortDrugs(drugs, Context.getService(MdrtbService.class).getMdrtbDrugs());
    }

    /**
     * Given a list of concepts, sorts them in the same order as the list of antiretrovirals
     * (All non-antiretrovirals are ignored)
     */
	public static List<Concept> sortAntiretrovirals(List<Concept> drugs) {
    	return MdrtbUtil.sortDrugs(drugs, Context.getService(MdrtbService.class).getAntiretrovirals());
    }

	/**
	 * Given a list of drugs to sort and a drug list, sorts the first list so that the
	 * drugs are in the same order as the second list; any drugs in the list to sort not
	 * found in the drug list are discarded
	 */
    public static List<Concept> sortDrugs(List<Concept> drugsToSort, List<Concept> drugList) {
    	List<Concept> sortedDrugs = new LinkedList<Concept>();
    	
    	for (Concept drug : drugList) {
    		if (drugsToSort.contains(drug)) {
    			sortedDrugs.add(drug);
    		}
    	}
    	
    	return sortedDrugs;
    }
    
    /**
	 * Gets a specific ProgramWorkflowState, given the concept associated with the state
	 */
    public static ProgramWorkflowState getProgramWorkflowState(Concept programWorkflowStateConcept) {
		for (ProgramWorkflowState state : Context.getProgramWorkflowService().getStates()) {
			if (state.getConcept().equals(programWorkflowStateConcept)) {
				return state;
			}
		}
		return null;
	}
    
    /**
     * Auto-assign a patient identifier for a specific identifier type, if required, if the idgen module is installed, using reflection
     * Auto generated method comment
     */
	@SuppressWarnings("unchecked")
    public static String assignIdentifier(PatientIdentifierType type) {		
		try {
			Class identifierSourceServiceClass = Context.loadClass("org.openmrs.module.idgen.service.IdentifierSourceService");
			Object idgen = Context.getService(identifierSourceServiceClass);
	        Method generateIdentifier = identifierSourceServiceClass.getMethod("generateIdentifier", PatientIdentifierType.class, String.class);
	        
	        // note that generate identifier returns null if this identifier type is not set to be auto-generated
	        return (String) generateIdentifier.invoke(idgen, type, "auto-assigned during patient creation");
		}
		catch (Exception e) {
			log.error("Unable to access IdentifierSourceService for automatic id generation.  Is the Idgen module installed and up-to-date?", e);
		} 
		
		return null;
	}
	
	/**
	 * Given a concept, locale, and a string that represents a concept name tag,
	 * returns the first concept name for that concept that matches the language and is tagged with the specified tag
	 */
	public static ConceptName getConceptName(Concept concept, String language, String conceptNameTag) {
		if (concept == null) {
			log.error("No concept provided to findConceptName");
			return null;
		}
		
		ConceptNameTag tag = Context.getConceptService().getConceptNameTagByName(conceptNameTag);
		
		if (tag == null) {
			log.warn("Invalid concept name tag parameter " + conceptNameTag + " passed to findConceptName");
		}
		
		for (ConceptName name : concept.getNames()) {
			if ((language == null || name.getLocale() == null || name.getLocale().getLanguage() == null || name.getLocale().getLanguage().equals(language)) 
				&& ((tag == null) || (name.getTags().contains(tag)))) {
				return name;
			}				
		}
		
		return null;
	}
	
	/**
     * Configures the default values for a Test, based on the existing values for other tests in the specimen
     * Implements the following rule:
     * 
     * If this is the first test, and the specimen has a sample id, set the accession # field with this sample id.
     * If this is not the first test, then--
     * If the Accession # on all the existing tests and the sample ID on the specimen are all the same, set the accession # field with this number.
     * If the Lab, Date Ordered, or Date Received on all the existing tests are identical, set these fields with these values.
     * 
     */
    public static void setTestDefaults(Specimen specimen, Test test) { 	
    	
    	Set<String> accessionNumberSet = new HashSet<String>();
    	Set<Date> dateOrderedSet = new HashSet<Date>();
    	Set<Date> dateReceivedSet = new HashSet<Date>();
    	Set<Location> labSet = new HashSet<Location>();
    	
    	// first add the identifier of the sample to the accession number set
    	accessionNumberSet.add(specimen.getIdentifier());
    	
    	// now loop through all the tests for this sample, excluding the test we want to set the defaults for
    	for (Test t : specimen.getTests()) {
    		if (t != test) {
    			accessionNumberSet.add(t.getAccessionNumber());
    			dateOrderedSet.add(t.getDateOrdered());
    			dateReceivedSet.add(t.getDateReceived());
    			labSet.add(t.getLab());
    		}
    	}
    	
    	// test if any of are sets contain exactly one non-null member
    	if (accessionNumberSet.size() == 1 && !accessionNumberSet.contains(null)) {
    		test.setAccessionNumber(accessionNumberSet.iterator().next());
    	}
    	if (dateOrderedSet.size() == 1 && !dateOrderedSet.contains(null)) {
    		test.setDateOrdered(dateOrderedSet.iterator().next());
    	}
    	if (dateReceivedSet.size() == 1 && !dateReceivedSet.contains(null)) {
    		test.setDateReceived(dateReceivedSet.iterator().next());
    	}
    	if (labSet.size() == 1 && !labSet.contains(null)) {
    		test.setLab(labSet.iterator().next());
    	}
    }
    
    /**
	 * Utility method to return patients matching passed criteria
	 * @return Cohort
	 */
	public static Cohort getMdrPatients(String identifier, String name, String enrollment, Location location, List<ProgramWorkflowState> states) {
		
		Cohort cohort = Context.getPatientSetService().getAllPatients();
		
		MdrtbService ms = (MdrtbService) Context.getService(MdrtbService.class);
		
		Date now = new Date();
		Program mdrtbProgram = ms.getMdrtbProgram();
		
		if ("current".equals(enrollment)) {
			Cohort current = Context.getPatientSetService().getPatientsInProgram(mdrtbProgram, now, now);
			cohort = Cohort.intersect(cohort, current);
		}
		else {
			Cohort ever = Context.getPatientSetService().getPatientsInProgram(mdrtbProgram, null, null);
			if ("previous".equals(enrollment)) {
				Cohort current = Context.getPatientSetService().getPatientsInProgram(mdrtbProgram, now, now);
				Cohort previous = Cohort.subtract(ever, current);
				cohort = Cohort.intersect(cohort, previous);   			
			}
			else if ("never".equals(enrollment)) {
				cohort = Cohort.subtract(cohort, ever);
			}
			else {
				cohort = Cohort.intersect(cohort, ever);
			}	
		}
		
		if (StringUtils.isNotBlank(name) || StringUtils.isNotBlank(identifier)) {
			name = "".equals(name) ? null : name;
			identifier = "".equals(identifier) ? null : identifier;
			Cohort nameIdMatches = new Cohort(Context.getPatientService().getPatients(name, identifier, null, false));
			cohort = Cohort.intersect(cohort, nameIdMatches);
		}
		
		// If Location is specified, limit to patients at this Location
		if (location != null) {
			CohortDefinition lcd = Cohorts.getLocationFilter(location, now, now);
			Cohort locationCohort;
            try {
	            locationCohort = Context.getService(CohortDefinitionService.class).evaluate(lcd, new EvaluationContext());
            }
            catch (EvaluationException e) {
            	  throw new MdrtbAPIException("Unable to evalute location cohort",e);
            }
			cohort = Cohort.intersect(cohort, locationCohort);
		}
		
		if (states != null) {
			Cohort inStates = Context.getPatientSetService().getPatientsByProgramAndState(null, states, now, now);
			cohort = Cohort.intersect(cohort, inStates);
		}
		
		return cohort;
	}
	

	/**
	 * Tests whether a String is parseable as an Integer
	 */
	public static boolean isInteger(String string) {
	    try {
	        Integer.valueOf(string);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	
	/**
	 * Returns true/false if all the fields in the address are empty or null
	 */
	public static Boolean isBlank(PersonAddress address) {
		return StringUtils.isBlank(address.getAddress1()) && StringUtils.isBlank(address.getAddress2()) && StringUtils.isBlank(address.getCityVillage())
				&& StringUtils.isBlank(address.getStateProvince()) && StringUtils.isBlank(address.getCountry()) && StringUtils.isBlank(address.getCountyDistrict())
				&& StringUtils.isBlank(address.getNeighborhoodCell()) && StringUtils.isBlank(address.getPostalCode()) && StringUtils.isBlank(address.getTownshipDivision()) 
				&& StringUtils.isBlank(address.getLatitude()) && StringUtils.isBlank(address.getLongitude()) && StringUtils.isBlank(address.getRegion()) && StringUtils.isBlank(address.getSubregion()) 
				&& StringUtils.isBlank(address.getPostalCode());
	}
	
	/**
	 * Utility method to return patients matching passed criteria. Difference between this and main method is that locations are matched by patient rayon
	 * @return Cohort
	 */
	public static Cohort getMdrPatientsTJK(String identifier, String name, /*String enrollment,*/ Location location, String oblast, List<ProgramWorkflowState> states, Integer minage, Integer maxage, String gender, Integer year, String quarter, String month) {
		
		Cohort cohort = Context.getPatientSetService().getAllPatients();
		
		MdrtbService ms = (MdrtbService) Context.getService(MdrtbService.class);
		
		Date now = new Date();
		Program mdrtbProgram = ms.getMdrtbProgram();
		
		Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
		
		Date startDate = (Date)(dateMap.get("startDate"));
		Date endDate = (Date)(dateMap.get("endDate"));
		
		CohortDefinition drtb = Cohorts.getEnrolledInMDRProgramDuring(startDate, endDate);
		
		try {
			Cohort enrollmentCohort = Context.getService(CohortDefinitionService.class).evaluate(drtb, new EvaluationContext());
			cohort = Cohort.intersect(cohort, enrollmentCohort);
		}
		
		 catch (EvaluationException e) {
       	  throw new MdrtbAPIException("Unable to evalute location cohort",e);
       }
		
		/*if ("current".equals(enrollment)) {
			Cohort current = Context.getPatientSetService().getPatientsInProgram(mdrtbProgram, now, now);
			cohort = Cohort.intersect(cohort, current);
		}
		else {
			Cohort ever = Context.getPatientSetService().getPatientsInProgram(mdrtbProgram, null, null);
			if ("previous".equals(enrollment)) {
				Cohort current = Context.getPatientSetService().getPatientsInProgram(mdrtbProgram, now, now);
				Cohort previous = Cohort.subtract(ever, current);
				cohort = Cohort.intersect(cohort, previous);   			
			}
			else if ("never".equals(enrollment)) {
				cohort = Cohort.subtract(cohort, ever);
			}
			else {
				cohort = Cohort.intersect(cohort, ever);
			}	
		}*/
		
		if (StringUtils.isNotBlank(name) || StringUtils.isNotBlank(identifier)) {
			name = "".equals(name) ? null : name;
			identifier = "".equals(identifier) ? null : identifier;
			Cohort nameIdMatches = new Cohort(Context.getPatientService().getPatients(name, identifier, null, false));
			cohort = Cohort.intersect(cohort, nameIdMatches);
		}
		
		
		
		if (states != null) {
			Cohort inStates = Context.getPatientSetService().getPatientsByProgramAndState(null, states, now, now);
			cohort = Cohort.intersect(cohort, inStates);
		}
		
		Oblast o = null;
		if(!oblast.equals("") && location == null)
			o =  Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblast));
		
		List<Location> locList = new ArrayList<Location>();
		if(o != null && location == null)
			locList = Context.getService(MdrtbService.class).getLocationsFromOblastName(o);
		else if (location != null)
			locList.add(location);
		
		CohortDefinition temp = null;
		CohortDefinition lcd = null;
		
		for(Location loc : locList) {
			temp = Cohorts.getLocationFilter(loc, null,null);
			if(lcd == null)
				lcd = temp;
			
			else 
				lcd = ReportUtil.getCompositionCohort("OR", lcd, temp);
		}
		
		Cohort locationCohort;
        try {
            locationCohort = Context.getService(CohortDefinitionService.class).evaluate(lcd, new EvaluationContext());
        }
        catch (EvaluationException e) {
        	  throw new MdrtbAPIException("Unable to evalute location cohort",e);
        }
		cohort = Cohort.intersect(cohort, locationCohort);
		
		// If Location is specified, limit to patients at this Location
				/*if (location != null) {
					CohortDefinition lcd = Cohorts.getLocationFilter(location, now, now);
					Cohort locationCohort;
		            try {
			            locationCohort = Context.getService(CohortDefinitionService.class).evaluate(lcd, new EvaluationContext());
		            }
		            catch (EvaluationException e) {
		            	  throw new MdrtbAPIException("Unable to evalute location cohort",e);
		            }
					cohort = Cohort.intersect(cohort, locationCohort);
				}*/
		
		/*// If Location is specified, limit to patients at this Location
		if (location != null) {
			//System.out.println("ENTERED!!!!!!!!!");
			//System.out.println("L:" + location.getCountyDistrict());
			Cohort fc = new Cohort();
			Set<Integer> idSet = cohort.getMemberIds();
	    	//System.out.println("SET SIZE:" + idSet.size());
	    	Iterator<Integer> itr = idSet.iterator();
	    	Integer idCheck = null;
	    	Patient patient = null;
	    	PersonAddress addr = null;
	    	PatientService ps = Context.getService(PatientService.class);
	    	while(itr.hasNext()) {
	    		idCheck = (Integer)itr.next();
	    		patient = ps.getPatient(idCheck);
	    		addr = patient.getPersonAddress();
	    		if(addr==null)
	    			continue;
	    		//System.out.println("A:"+ addr.getCountyDistrict());
	    		
	    		if(areRussianStringsEqual(addr.getCountyDistrict(),location.getCountyDistrict())==true)
	    			fc.addMember(idCheck);
	    		
	    	}
	    	
	    	cohort  = fc;
		}*/
		
		if(minage != null || maxage != null) {
			Cohort ageCohort = new Cohort();
			AgeAtMDRRegistrationCohortDefinition ageatEnrollmentCohort = new AgeAtMDRRegistrationCohortDefinition();
			ageatEnrollmentCohort.setMaxAge(maxage);
			ageatEnrollmentCohort.setMinAge(minage);
			ageatEnrollmentCohort.setStartDate(startDate);
			ageatEnrollmentCohort.setEndDate(endDate);
			
			;
			
			//eval.evaluate(ageatEnrollmentCohort, context)
			 try {
				 ageCohort = Context.getService(CohortDefinitionService.class).evaluate(ageatEnrollmentCohort, new EvaluationContext());
			 }
			 
			 catch (EvaluationException e) {
           	  throw new MdrtbAPIException("Unable to evalute age cohort",e);
           }
			/*Patient patient = null;
			Set<Integer> idSet = cohort.getMemberIds();
			Iterator<Integer> itr = idSet.iterator();
	    	Integer idCheck = null;
	   
	    	PatientService ps = Context.getService(PatientService.class);
	    	boolean use = false;
	    	while(itr.hasNext()) {
	    		use = false;
	    		idCheck = (Integer)itr.next();
	    		patient = ps.getPatient(idCheck);
	    		MdrtbService svc = Context.getService(MdrtbService.class);
	    		
	    		MdrtbPatientProgram mpp = svc.getMostRecentMdrtbPatientProgram(patient);
	    		
	    		Date tsd = mpp.getDateEnrolled();
	    		//mpp.getTreatmentStartDateDuringProgram();
	    		
	    		
	    		if(minage != null && maxage !=null ) {
	    			if(patient.getAge(tsd)>= minage.intValue() && patient.getAge(tsd)<= maxage.intValue() ) {
	    				use = true;
	    			}
	    		}
	    		
	    		else if(minage!=null) {
	    			if(patient.getAge(tsd)>= minage.intValue()) {
	    				use = true;
	    			}
	    			else
	    				use = false;
	    				
	    		}
	    		
	    		else if(maxage!=null) {
	    			if(patient.getAge(tsd)<= maxage.intValue()) {
	    				use = true;
	    			}
	    			else
	    				use = false;
	    		} 
	    		
	    		if(use) {
	    			ageCohort.addMember(patient.getPatientId());
	    		}
	    		
	    	}*/
	    	
			 cohort = Cohort.intersect(cohort, ageCohort);
			
			
		}
		
		if(gender!=null && gender.length()!=0) {
			Cohort genderCohort = new Cohort();
			Patient patient = null;
			Set<Integer> idSet = cohort.getMemberIds();
			Iterator<Integer> itr = idSet.iterator();
	    	Integer idCheck = null;
	   
	    	PatientService ps = Context.getService(PatientService.class);
	    	boolean use = false;
	    	while(itr.hasNext()) {
	    		use = false;
	    		idCheck = (Integer)itr.next();
	    		patient = ps.getPatient(idCheck);
	    		
	    		
	    		if(patient.getGender().equalsIgnoreCase(gender)) {
	    			genderCohort.addMember(patient.getPatientId());
	    		}
	    		
	    	}
	    	
	    	cohort = Cohort.intersect(cohort, genderCohort);
		}
		
		
		return cohort;
	}
	
	public static boolean areRussianStringsEqual(String s1, String s2) {
		boolean result = false;
		
		if(s1==null || s2==null)
			return false;
		
		if(s1.length()==0 || s2.length()==0)
			return false;
		
		java.text.Collator collator = java.text.Collator.getInstance();
		collator.setStrength(Collator.SECONDARY);
		
		int compResult = collator.compare(s1, s2);
		if(compResult==0)
			return true;
				
		return result;
	}
	
	public void updateProgramWithPatientIdentifier(Integer patientProgramId, Integer patId) {
		
		
	}
	
	public static Cohort getMdrPatientsTJKFacility(String identifier, String name, /*String enrollment,*/ String districtId, String oblastId,String facilityId, List<ProgramWorkflowState> states, Integer minage, Integer maxage, String gender, Integer year, String quarter, String month) {
		
		Location location=null;
		List<Location> locList = null;
		/*if(districtId!=null)
		{
			District district= Context.getService(MdrtbService.class).getDistrict(Integer.parseInt(districtId));
		
			List<Location> locations = Context.getLocationService().getAllLocations(false);// Context.getService(MdrtbService.class).getEnrollmentLocations();//ms = (MdrtbDrugForecastService) Context.getService(MdrtbDrugForecastService.class);
			for (Location l : locations) {
				if(MdrtbUtil.areRussianStringsEqual(l.getName(), district.getName()))
	    		{
					location=l;
	    		}
			}
		}*/
		
		if(facilityId!=null && facilityId.length()!=0) {
    		//all fields selected
    		Facility fac = Context.getService(MdrtbService.class).getFacility(Integer.parseInt(facilityId));
    		District dist = Context.getService(MdrtbService.class).getDistrict(Integer.parseInt(districtId));
    		Oblast obl = Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblastId));
    		//List<Location> facList = Context.getService(MdrtbService.class).getLocationsFromFacilityName(fac);
    		location = Context.getService(MdrtbService.class).getLocation(obl.getId(),dist.getId(),fac.getId());
    		
    	}
    	
    	else if(districtId!=null && districtId.length()!=0) {
    		//district and oblast selected
    		District dist = Context.getService(MdrtbService.class).getDistrict(Integer.parseInt(districtId));
    		
    		
    		locList = Context.getService(MdrtbService.class).getLocationsFromDistrictName(dist);
    		
    	}
    	
    	else if(oblastId!=null && oblastId.length()!=0) {
    		Oblast obl = Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblastId));
    		locList = Context.getService(MdrtbService.class).getLocationsFromOblastName(obl);
    	}
		
		
			Cohort cohort = Context.getPatientSetService().getAllPatients();
			
			MdrtbService ms = (MdrtbService) Context.getService(MdrtbService.class);
			
			Date now = new Date();
			Program mdrtbProgram = ms.getMdrtbProgram();
			
			Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
			
			Date startDate = (Date)(dateMap.get("startDate"));
			Date endDate = (Date)(dateMap.get("endDate"));
			
			CohortDefinition drtb = Cohorts.getEnrolledInMDRProgramDuring(startDate, endDate);
			
			try {
				Cohort enrollmentCohort = Context.getService(CohortDefinitionService.class).evaluate(drtb, new EvaluationContext());
				cohort = Cohort.intersect(cohort, enrollmentCohort);
			}
			
			 catch (EvaluationException e) {
	       	  throw new MdrtbAPIException("Unable to evalute location cohort",e);
	       }
			
			if (StringUtils.isNotBlank(name) || StringUtils.isNotBlank(identifier)) {
				name = "".equals(name) ? null : name;
				identifier = "".equals(identifier) ? null : identifier;
				Cohort nameIdMatches = new Cohort(Context.getPatientService().getPatients(name, identifier, null, false));
				cohort = Cohort.intersect(cohort, nameIdMatches);
			}
			
			if (states != null) {
				Cohort inStates = Context.getPatientSetService().getPatientsByProgramAndState(null, states, now, now);
				cohort = Cohort.intersect(cohort, inStates);
			}
			
			/*Oblast o = null;
			Facility f = null;
			if(!oblastId.equals("") && location == null)
				o =  Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblastId));
			
			List<Location> locList = new ArrayList<Location>();
			if(o != null && location == null)
				locList = Context.getService(MdrtbService.class).getLocationsFromOblastName(o);
			else if (location != null)
			{
				if(facilityId != null && facilityId.length()!=0)
				{
					f= Context.getService(MdrtbService.class).getFacility(Integer.parseInt(facilityId));
					if(f!=null)
					locList = Context.getService(MdrtbService.class).getLocationsFromFacilityName(f);
				}
				else
				{
					locList.add(location);
					List<Facility> fs = Context.getService(MdrtbService.class).getFacilities(Integer.parseInt(districtId));
					if(fs!=null)
					{
						for (Facility f1 : fs) {
							locList.addAll(Context.getService(MdrtbService.class).getLocationsFromFacilityName(f1));
						}
					}
				}
			}*/
			
			//if facility is selected
			if(location!=null && locList==null) {
				locList = new ArrayList<Location>();
				locList.add(location);
			}
			
			System.out.println("Location>>" + location);
			if(locList==null) {
				System.out.println("Location List >>" + null);
			}
			
			else {
				System.out.println("Location List >>" + locList.size());
			}
			
			CohortDefinition temp = null;
			CohortDefinition lcd = null;
			
			if(locList!=null) {
			for(Location loc : locList) {
				temp = Cohorts.getLocationFilter(loc, null,null);
				if(lcd == null)
					lcd = temp;
				
				else 
					lcd = ReportUtil.getCompositionCohort("OR", lcd, temp);
			}
			}
			
			else 
				lcd = Cohorts.getLocationFilter(location, null,null);
			
			Cohort locationCohort;
	        try {
	            locationCohort = Context.getService(CohortDefinitionService.class).evaluate(lcd, new EvaluationContext());
	        }
	        catch (EvaluationException e) {
	        	  throw new MdrtbAPIException("Unable to evalute location cohort",e);
	        }
			cohort = Cohort.intersect(cohort, locationCohort);
			
			if(minage != null || maxage != null) {
				Cohort ageCohort = new Cohort();
				AgeAtMDRRegistrationCohortDefinition ageatEnrollmentCohort = new AgeAtMDRRegistrationCohortDefinition();
				ageatEnrollmentCohort.setMaxAge(maxage);
				ageatEnrollmentCohort.setMinAge(minage);
				ageatEnrollmentCohort.setStartDate(startDate);
				ageatEnrollmentCohort.setEndDate(endDate);
				
				;
				
				//eval.evaluate(ageatEnrollmentCohort, context)
				 try {
					 ageCohort = Context.getService(CohortDefinitionService.class).evaluate(ageatEnrollmentCohort, new EvaluationContext());
				 }
				 
				 catch (EvaluationException e) {
	           	  throw new MdrtbAPIException("Unable to evalute age cohort",e);
	           }
				/*Patient patient = null;
				Set<Integer> idSet = cohort.getMemberIds();
				Iterator<Integer> itr = idSet.iterator();
		    	Integer idCheck = null;
		   
		    	PatientService ps = Context.getService(PatientService.class);
		    	boolean use = false;
		    	while(itr.hasNext()) {
		    		use = false;
		    		idCheck = (Integer)itr.next();
		    		patient = ps.getPatient(idCheck);
		    		MdrtbService svc = Context.getService(MdrtbService.class);
		    		
		    		MdrtbPatientProgram mpp = svc.getMostRecentMdrtbPatientProgram(patient);
		    		
		    		Date tsd = mpp.getDateEnrolled();
		    		//mpp.getTreatmentStartDateDuringProgram();
		    		
		    		
		    		if(minage != null && maxage !=null ) {
		    			if(patient.getAge(tsd)>= minage.intValue() && patient.getAge(tsd)<= maxage.intValue() ) {
		    				use = true;
		    			}
		    		}
		    		
		    		else if(minage!=null) {
		    			if(patient.getAge(tsd)>= minage.intValue()) {
		    				use = true;
		    			}
		    			else
		    				use = false;
		    				
		    		}
		    		
		    		else if(maxage!=null) {
		    			if(patient.getAge(tsd)<= maxage.intValue()) {
		    				use = true;
		    			}
		    			else
		    				use = false;
		    		} 
		    		
		    		if(use) {
		    			ageCohort.addMember(patient.getPatientId());
		    		}
		    		
		    	}*/
		    	
				 cohort = Cohort.intersect(cohort, ageCohort);
				
				
			}
			
			if(gender!=null && gender.length()!=0) {
				Cohort genderCohort = new Cohort();
				Patient patient = null;
				Set<Integer> idSet = cohort.getMemberIds();
				Iterator<Integer> itr = idSet.iterator();
		    	Integer idCheck = null;
		   
		    	PatientService ps = Context.getService(PatientService.class);
		    	boolean use = false;
		    	while(itr.hasNext()) {
		    		use = false;
		    		idCheck = (Integer)itr.next();
		    		patient = ps.getPatient(idCheck);
		    		
		    		
		    		if(patient.getGender().equalsIgnoreCase(gender)) {
		    			genderCohort.addMember(patient.getPatientId());
		    		}
		    		
		    	}
		    	
		    	cohort = Cohort.intersect(cohort, genderCohort);
			}
			
			
			return cohort;
		}
	
	public static Boolean isBacPositive(TB03Form tf) {
		   Boolean ret = false;
		   List<SmearForm> smears = tf.getSmears();
		   
		   for(SmearForm sf : smears) {
			   if(MdrtbUtil.getPositiveResultConcepts().contains(sf.getSmearResult())) {
				   return true;
			   }
		   }
		   
		   List<CultureForm> cultures = tf.getCultures();
		   
		   for(CultureForm cf : cultures) {
			   if(MdrtbUtil.getPositiveResultConcepts().contains(cf.getCultureResult())) {
				   return true;
			   }
		   }
		   
		   List<XpertForm> xperts = tf.getXperts();
		   Concept positive = Context.getService(MdrtbService.class).getConcept(TbConcepts.MTB_POSITIVE);
		   Concept mtbResult = Context.getService(MdrtbService.class).getConcept(TbConcepts.MTB_RESULT);
		   Concept xpertConstructs = Context.getService(MdrtbService.class).getConcept(TbConcepts.XPERT_CONSTRUCT);
		   Obs constructObs = null;
		   Obs resultObs = null;
		   for(XpertForm xf : xperts) {
			   resultObs = null;
			   constructObs = MdrtbUtil.getObsFromEncounter(xpertConstructs, xf.getEncounter());
			   if(constructObs!=null) {
				   resultObs = MdrtbUtil.getObsFromObsGroup(mtbResult, constructObs);
				   if(resultObs!=null && resultObs.getValueCoded()!=null && resultObs.getValueCoded().getId().intValue()==positive.getId().intValue()) {
					   return true;
				   }
			   }
			   
			  
		   }
		   
		   List<HAINForm> hains = tf.getHains();
		  
		   Concept hainConstructs = Context.getService(MdrtbService.class).getConcept(TbConcepts.HAIN_CONSTRUCT);
		   constructObs = null;
		   resultObs = null;
		   for(HAINForm hf : hains) {
			   resultObs = null;
			   constructObs = MdrtbUtil.getObsFromEncounter(hainConstructs, hf.getEncounter());
			   if(constructObs!=null) {
				   resultObs = MdrtbUtil.getObsFromObsGroup(mtbResult, constructObs);
				   if(resultObs!=null && resultObs.getValueCoded()!=null && resultObs.getValueCoded().getId().intValue()==positive.getId().intValue()) {
					   return true;
				   }
			   }
			   
			  
		   }
		   
		   return ret;
	   }
	
	
	public static Boolean isDiagnosticBacPositive(TB03Form tf) {
		   Boolean ret = false;
		   List<SmearForm> smears = tf.getSmears();
		   
		   for(SmearForm sf : smears) {
			   if(sf.getMonthOfTreatment()!=null && sf.getMonthOfTreatment().intValue()==0 && MdrtbUtil.getPositiveResultConcepts().contains(sf.getSmearResult())) {
				   return true;
			   }
		   }
		   
		   List<CultureForm> cultures = tf.getCultures();
		   
		   for(CultureForm cf : cultures) {
			   if(cf.getMonthOfTreatment()!=null && cf.getMonthOfTreatment().intValue()==0 && MdrtbUtil.getPositiveResultConcepts().contains(cf.getCultureResult())) {
				   return true;
			   }
		   }
		   
		   List<XpertForm> xperts = tf.getXperts();
		   Concept positive = Context.getService(MdrtbService.class).getConcept(TbConcepts.MTB_POSITIVE);
		   Concept mtbResult = Context.getService(MdrtbService.class).getConcept(TbConcepts.MTB_RESULT);
		   Concept xpertConstructs = Context.getService(MdrtbService.class).getConcept(TbConcepts.XPERT_CONSTRUCT);
		   Obs constructObs = null;
		   Obs resultObs = null;
		   for(XpertForm xf : xperts) {
			   
			   if(xf.getMonthOfTreatment()!=null && xf.getMonthOfTreatment().intValue()==0) {
				   resultObs = null;
				   constructObs = MdrtbUtil.getObsFromEncounter(xpertConstructs, xf.getEncounter());
				   if(constructObs!=null) {
					   resultObs = MdrtbUtil.getObsFromObsGroup(mtbResult, constructObs);
					   if(resultObs!=null && resultObs.getValueCoded()!=null && resultObs.getValueCoded().getId().intValue()==positive.getId().intValue()) {
						   return true;
					   }
				   }
			   }
		   }
		   
		   List<HAINForm> hains = tf.getHains();
		  
		   Concept hainConstructs = Context.getService(MdrtbService.class).getConcept(TbConcepts.HAIN_CONSTRUCT);
		   constructObs = null;
		   resultObs = null;
		   for(HAINForm hf : hains) {
			   if(hf.getMonthOfTreatment()!=null && hf.getMonthOfTreatment().intValue()==0) {
			   
			   resultObs = null;
			   constructObs = MdrtbUtil.getObsFromEncounter(hainConstructs, hf.getEncounter());
			   if(constructObs!=null) {
				   resultObs = MdrtbUtil.getObsFromObsGroup(mtbResult, constructObs);
				   if(resultObs!=null && resultObs.getValueCoded()!=null && resultObs.getValueCoded().getId().intValue()==positive.getId().intValue()) {
					   return true;
				   }
			   }
			 
			  }
			  
		   }
		   
		   List<HAIN2Form> hain2s = tf.getHain2s();
			  
		   Concept hain2Constructs = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HAIN2_CONSTRUCT);
		   constructObs = null;
		   resultObs = null;
		   for(HAIN2Form hf : hain2s) {
			   if(hf.getMonthOfTreatment()!=null && hf.getMonthOfTreatment().intValue()==0) {
			   
			   resultObs = null;
			   constructObs = MdrtbUtil.getObsFromEncounter(hain2Constructs, hf.getEncounter());
			   if(constructObs!=null) {
				   resultObs = MdrtbUtil.getObsFromObsGroup(mtbResult, constructObs);
				   if(resultObs!=null && resultObs.getValueCoded()!=null && resultObs.getValueCoded().getId().intValue()==positive.getId().intValue()) {
					   return true;
				   }
			   }
			 
			  }
			  
		   }
		   
		   return ret;
	   }
	
	public static Integer calculateAge(Date fromDate, Date toDate) {
		Integer ret = null;
		GregorianCalendar fromCal = new GregorianCalendar();
		fromCal.setTimeInMillis(fromDate.getTime());
		/*fromCal.set(GregorianCalendar.HOUR_OF_DAY, 0);
		fromCal.set(GregorianCalendar.MINUTE, 0);
		fromCal.set(GregorianCalendar.SECOND, 1);*/
		GregorianCalendar toCal = new GregorianCalendar();
		toCal.setTimeInMillis(toDate.getTime());
		/*fromCal.set(GregorianCalendar.HOUR_OF_DAY, 23);
		fromCal.set(GregorianCalendar.MINUTE, 59);
		fromCal.set(GregorianCalendar.SECOND, 59);*/
		
		LocalDate fromLocalDate = new LocalDate(fromCal.get(GregorianCalendar.YEAR),  fromCal.get(GregorianCalendar.MONTH)+1, fromCal.get(GregorianCalendar.DATE));
		LocalDate toLocalDate = new LocalDate(toCal.get(GregorianCalendar.YEAR),  toCal.get(GregorianCalendar.MONTH)+1, toCal.get(GregorianCalendar.DATE));
		Years age = Years.yearsBetween(fromLocalDate, toLocalDate);
		ret = age.getYears();
		
		
		return ret;
	}
	
	/*
	 * Validates a PatientIdentifier.
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 */
	
	public static void validateIdentifier(PatientIdentifier pi, Errors errors) throws PatientIdentifierException {
		
		// Validate that the identifier is non-null
		if (pi == null) {
			errors.reject(Context.getMessageSourceService().getMessage("mdrtb.emptyId"));
			// BlankIdentifierException("Patient Identifier cannot be null.");
		}
		
		// Only validate if the PatientIdentifier is not voided
		if (!pi.isVoided()) {
			
			// Check is already in use by another patient
			String id = pi.getIdentifier();
			System.out.println("VAL_ID:" + id);
			GregorianCalendar now = new GregorianCalendar();
			int year = now.get(GregorianCalendar.YEAR);
			String yearString = "" + year;
			String firstTwoDigits = yearString.substring(0,2);
			System.out.println("FTD:" + firstTwoDigits);
			int centuryYear = Integer.parseInt(firstTwoDigits) * 100;
			System.out.println("CY:" + centuryYear);
			int yearFromId = Integer.parseInt(id.substring(2,4)) + centuryYear;
			System.out.println("YFI:" + yearFromId);
			
			if(yearFromId > year) {
				errors.reject(Context.getMessageSourceService().getMessage("mdrtb.yearInIdInFuture"));
			}
			
			/*if(programStartDate!=null) {
			{
				GregorianCalendar stDateCal = new GregorianCalendar();	
				stDateCal.setTimeInMillis(programStartDate.getTime());
				int stDateYear = stDateCal.get(GregorianCalendar.YEAR);
			
				if(yearFromId != stDateYear) {
					Context.getMessageSourceService().getMessage("mdrtb.yearInIdMismatch");
				}
			}*/
			
			
			
		
		}
	}
	
	public static void validateIdentifierString(String id, Errors errors) throws PatientIdentifierException {
		
		// Validate that the identifier is non-null
		if (id==null) {
			errors.reject(Context.getMessageSourceService().getMessage("mdrtb.emptyId"));
			// BlankIdentifierException("Patient Identifier cannot be null.");
		}
		
		// Only validate if the PatientIdentifier is not voided
		else {
			
			// Check is already in use by another patient
			
			System.out.println("VAL_ID:" + id);
			GregorianCalendar now = new GregorianCalendar();
			int year = now.get(GregorianCalendar.YEAR);
			String yearString = "" + year;
			String firstTwoDigits = yearString.substring(0,2);
			System.out.println("FTD:" + firstTwoDigits);
			int centuryYear = Integer.parseInt(firstTwoDigits) * 100;
			System.out.println("CY:" + centuryYear);
			int yearFromId = Integer.parseInt(id.substring(2,4)) + centuryYear;
			System.out.println("YFI:" + yearFromId);
			
			if(yearFromId > year) {
				errors.reject(Context.getMessageSourceService().getMessage("mdrtb.yearInIdInFuture"));
			}
			
			/*if(programStartDate!=null) {
			{
				GregorianCalendar stDateCal = new GregorianCalendar();	
				stDateCal.setTimeInMillis(programStartDate.getTime());
				int stDateYear = stDateCal.get(GregorianCalendar.YEAR);
			
				if(yearFromId != stDateYear) {
					Context.getMessageSourceService().getMessage("mdrtb.yearInIdMismatch");
				}
			}*/
			
			
			
		
		}
	}
	

}
