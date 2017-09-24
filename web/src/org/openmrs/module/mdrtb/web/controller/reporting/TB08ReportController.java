package org.openmrs.module.mdrtb.web.controller.reporting;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.MdrtbConstants;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.reporting.PDFHelper;
import org.openmrs.module.mdrtb.reporting.ReportUtil;
import org.openmrs.module.mdrtb.reporting.TB07Util;
import org.openmrs.module.mdrtb.reporting.TB08Data;
import org.openmrs.module.mdrtb.reporting.data.Cohorts;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.propertyeditor.ConceptEditor;
import org.openmrs.propertyeditor.LocationEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller

public class TB08ReportController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
        
    
    @RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/tb08")
    public ModelAndView showRegimenOptions(@RequestParam(value="loc", required=false) String district,
			@RequestParam(value="ob", required=false) String oblast,
			@RequestParam(value="yearSelected", required=false) Integer year,
			@RequestParam(value="quarterSelected", required=false) String quarter,
			@RequestParam(value="monthSelected", required=false) String month,
			ModelMap model) {
    	
    	List<Oblast> oblasts;
        List<Facility> facilities;
        List<District> districts;
    	
    	if(oblast==null) {
    		oblasts = Context.getService(MdrtbService.class).getOblasts();
    		model.addAttribute("oblasts", oblasts);
    	}
    	 
    	
    	else if(district==null)
         { 
         	oblasts = Context.getService(MdrtbService.class).getOblasts();
         	districts= Context.getService(MdrtbService.class).getDistricts(Integer.parseInt(oblast));
         	model.addAttribute("oblastSelected", oblast);
             model.addAttribute("oblasts", oblasts);
             model.addAttribute("districts", districts);
         }
         else
         {
         	oblasts = Context.getService(MdrtbService.class).getOblasts();
         	districts= Context.getService(MdrtbService.class).getDistricts(Integer.parseInt(oblast));
         	facilities = Context.getService(MdrtbService.class).getFacilities(Integer.parseInt(district));
             model.addAttribute("oblastSelected", oblast);
             model.addAttribute("oblasts", oblasts);
             model.addAttribute("districts", districts);
             model.addAttribute("districtSelected", district);
             model.addAttribute("facilities", facilities);
         }
    	
    	 model.addAttribute("yearSelected", year);
    	 model.addAttribute("monthSelected", month);
    	 model.addAttribute("quarterSelected", quarter);
       
        /*List<Location> locations = Context.getLocationService().getAllLocations(false);// Context.getLocationService().getAllLocations();//ms = (MdrtbDrugForecastService) Context.getService(MdrtbDrugForecastService.class);
        List<Oblast> oblasts = Context.getService(MdrtbService.class).getOblasts();
        //drugSets =  ms.getMdrtbDrugs();
        
       

        model.addAttribute("locations", locations);
        model.addAttribute("oblasts", oblasts);*/
    	 return new ModelAndView("/module/mdrtb/reporting/tb08", model);	
    }

    @RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/tb08")
    public static String doTB08(
    		@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException {
    	
    	System.out.println("---POST-----");
    //	System.out.println("PARAMS:" + location + " " + oblast + " " + year + " " + quarter + " " + month);
    	
    	
    	
    	
    	/*Oblast o = null;
    	if(oblast!=null && !oblast.equals("") && location == null)
			o =  Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblast));
		*/
    	ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
		
		ArrayList<TB03Form> tb03List = Context.getService(MdrtbService.class).getTB03FormsFilled(locList, year, quarter, month);
		System.out.println("list size:" + tb03List.size());
		/*if(o != null && location == null)
			locList = Context.getService(MdrtbService.class).getLocationsFromOblastName(o);
		else if (location != null)
			locList.add(location);*/
		
		//int iterations = 1;
		
		SimpleDateFormat sdf = new SimpleDateFormat();
    	sdf.applyPattern("dd.MM.yyyy");
    	
	/*	TB08Data fin = new TB08Data();
		
		if((month==null || month=="") && (quarter==null || quarter==""))
			iterations = 4;
		
		else
			iterations = 1;
		
		for(int j=0; j<iterations; j++) {	
			
		Map<String, Date> dateMap;
			
		if((month==null || month=="") && (quarter==null || quarter==""))	 {
			
			dateMap = ReportUtil.getPeriodDates(year, (j + 1) + "", month);
			
		}
		
		else 
			dateMap = ReportUtil.getPeriodDates(year, quarter, month);
		
		
		Date startDate = (Date)(dateMap.get("startDate"));
		Date endDate = (Date)(dateMap.get("endDate"));
	
		ArrayList<TB03Form> tb03List = Context.getService(MdrtbService.class).getTB03FormsFilled(locList, year, quarter, month);
		System.out.println("FORMS: " + tb03List.size())*/;
		//CohortDefinition baseCohort = null;
		
		//OBLAST
		/*if (!locList.isEmpty()){
			List<CohortDefinition> cohortDefinitions = new ArrayList<CohortDefinition>();
			for(Location loc : locList)
				cohortDefinitions.add(Cohorts.getTB03ByDatesAndLocation(startDate, endDate, loc));
				
			if(!cohortDefinitions.isEmpty()){
				baseCohort = ReportUtil.getCompositionCohort("OR", cohortDefinitions);
			}
		}
		
		else
			baseCohort = Cohorts.getTB03ByDatesAndLocation(startDate, endDate, null);
    	
    	Cohort patients = Context.getService(CohortDefinitionService.class).evaluate(baseCohort, new EvaluationContext());
    	//Cohort patients = TbUtil.getDOTSPatientsTJK(null, null, location, oblast, null, null, null, null,year,quarter,month);
    	
		
		Form tb03Form = Context.getFormService().getForm(MdrtbConstants.TB03_FORM_ID);
		ArrayList<Form> formList = new ArrayList<Form>();
		formList.add(tb03Form);
    	
    	
    	Set<Integer> idSet = patients.getMemberIds();
    	
    	System.out.println("PATIENTS: " + idSet.size());
    	
    	//ArrayList<TB03Data> patientSet  = new ArrayList<TB03Data>();
    	*/
    	
    	
    	/*ArrayList<Person> patientList = new ArrayList<Person>();
    	ArrayList<Concept> conceptQuestionList = new ArrayList<Concept>();
    	ArrayList<Concept> conceptAnswerList = new ArrayList<Concept>();
    	Integer regimenConceptId = null;
    	//Integer codId = null;
*/    	//List<Obs> obsList = null;
    
    	Integer ageAtRegistration = 0;
    	
    	Concept pulmonaryConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.PULMONARY_TB);
    	Concept extrapulmonaryConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.EXTRA_PULMONARY_TB);
    	
    
    	
    	Boolean pulmonary = null;
    	Boolean bacPositive = null;
    	Boolean cured = null;
    	Boolean txCompleted = null;
    	Boolean diedTB = null;
    	Boolean diedNotTB = null;
    	Boolean failed = null;
    	Boolean defaulted = null;
    	Boolean transferOut = null;
    	Boolean canceled = null;
    	Boolean sld = null;
    	
    	
    	
    	TB08Data table1 = new TB08Data();
    	
    	for (TB03Form tf : tb03List) {//for (Integer i : idSet) {
    		
    		ageAtRegistration = -1;
    		pulmonary = null;
    		bacPositive = null;
    		
    		cured = null;
    		txCompleted = null;
    		diedTB = null;
    		diedNotTB = null;
    		failed = null;
    		defaulted = null;
    		transferOut = null;
    		canceled = null;
    		sld = null;
    		
    		/*patientList.clear();
    		conceptQuestionList.clear();
    		System.out.println("PATIENT ID " + i);*/
    		
    		Patient patient = tf.getPatient();
    	    if(patient==null) {
    	    	continue;
    	    	
    	    }
    		
    		
    		/*Patient patient = Context.getPatientService().getPatient(i);
    	    if(patient==null) {
    	    	continue;
    	    }*/
    	      
    	   /* patientList.add(patient);
    	    Concept q = Context.getService(MdrtbService.class).getConcept(TbConcepts.AGE_AT_DOTS_REGISTRATION);
    	    
    	    conceptQuestionList.add(q);*/
    	    ageAtRegistration = tf.getAgeAtTB03Registration();
    	    
    	   /* obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null && obsList.get(0).getValueNumeric()!=null)
    	    	ageAtRegistration = obsList.get(0).getValueNumeric().intValue();
    	    else {
    	    	
    	    	 q = Context.getService(MdrtbService.class).getConcept(TbConcepts.AGE_AT_MDR_REGISTRATION);
    	    	    
    	    	 	conceptQuestionList.clear();
    	    	    conceptQuestionList.add(q);
    	    	    
    	    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    	    if(obsList.size()>0 && obsList.get(0)!=null && obsList.get(0).getValueNumeric()!=null)
    	    	    	ageAtRegistration = obsList.get(0).getValueNumeric().intValue();
    	    	
    	    	    else {
    	    	    	System.out.println("NO AGE");
    	    	    	
    	    	    	
    	    	    }
    	    	
    	    }
    	    	
    	    
    	    System.out.println("AGE: " + ageAtRegistration);*/
    	    
    	    
    	    //get disease site
    	    Concept q = tf.getAnatomicalSite();// Context.getService(MdrtbService.class).getConcept(TbConcepts.ANATOMICAL_SITE_OF_TB);
    	    /*conceptQuestionList.clear();
    	    conceptQuestionList.add(q);*/
    	    
    	    
    	    
    	   // obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(q!=null) {
    	    	if(q.getConceptId().intValue()==pulmonaryConcept.getConceptId().intValue()) {
    	    		pulmonary = Boolean.TRUE;
    	    		System.out.println("PULMONARY");
    	    	}
    	    	
    	    	else if (q.getConceptId().intValue()==extrapulmonaryConcept.getConceptId().intValue()) {
    	    		pulmonary = Boolean.FALSE;
    	    		System.out.println("EXTRAPULMONARY");
    	    	}
    	    	
    	    	else {
    	    		
    	    		System.out.println("PULMONARY NULL");
    	    		pulmonary = null;
    	    	}
    	    	
    	    }
    	    
    	    else {
    	    	System.out.println("NO SITE");
    	    	continue;
    	    }
    	    
    	    bacPositive = MdrtbUtil.isBacPositive(tf);
    	    
    	    //OUTCOMES
    	    q = tf.getTreatmentOutcome();
    	    
    	    
    	    
    	  
    	    
    	    if(q!=null) {
    	    	
    	    	
    	    	
    	    	if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.cured.conceptId"))) {
    	    		cured = Boolean.TRUE;
    	    		System.out.println("CURED");
    	    		
    	    	}
    	    	
    	    	else if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.txCompleted.conceptId"))) {
    	    		txCompleted = Boolean.TRUE;
    	    		System.out.println("TxC");
    	    	}
    	    	
    	    	else if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.txFailure.conceptId"))) {
    	    		failed = Boolean.TRUE;
    	    		System.out.println("FAIL");
    	    	}
    	    	
    	    	else if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.died.conceptId"))) {
    	    		System.out.println("DIED");
    	    		q = tf.getCauseOfDeath();//Context.getService(MdrtbService.class).getConcept(TbConcepts.CAUSE_OF_DEATH);
    	     	   
    	     	    
    	     	   
    	     	    if(q!=null)
    	     	    {	
    	     	    	if(q.getId().intValue() == Context.getService(MdrtbService.class).getConcept(TbConcepts.DEATH_BY_TB).getConceptId().intValue())
    	     	    		diedTB = Boolean.TRUE;
    	     	    	else
    	     	    		diedNotTB = Boolean.TRUE;
    	     	    }
    	    	}
    	    	
    	    	else if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.ltfu.conceptId"))) {
    	    		defaulted = Boolean.TRUE;
    	    		System.out.println("DEF");
    	    	}
    	    	
    	    	else if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.canceled.conceptId"))) {
    	    		canceled = Boolean.TRUE;
    	    		System.out.println("CANCEL");
    	    	}
    	    	
    	    	else if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.transferout.conceptId"))) {
    	    		transferOut = Boolean.TRUE;
    	    		System.out.println("TOUT");
    	    	}
    	    	
    	    	else if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.sld2.conceptId"))) {
    	    		sld = Boolean.TRUE;
    	    		System.out.println("SLD2");
    	    	}
    	    }
    	    
    	    else {
	    		System.out.println("NO OUTCOME");
	    	}
    	    
    	    //get registration group
    	    //REGISTRATION GROUP
    	    q = tf.getRegistrationGroup();
    	   
    	  
    	    
    	    if(q!=null) {
    	    	
    	    	
    	    	if(q.getId().intValue()!=Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.transferIn.conceptId"))) {
    	    		
    	    		table1.setAllDetected(table1.getAllDetected() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setAllCured(table1.getAllCured() + 1);
						table1.setAllEligible(table1.getAllEligible() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setAllCompleted(table1.getAllCompleted() + 1);
						table1.setAllEligible(table1.getAllEligible() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setAllDiedTB(table1.getAllDiedTB() + 1);
						table1.setAllEligible(table1.getAllEligible() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setAllDiedNotTB(table1.getAllDiedNotTB() + 1);
						table1.setAllEligible(table1.getAllEligible() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setAllFailed(table1.getAllFailed() + 1);
						table1.setAllEligible(table1.getAllEligible() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setAllDefaulted(table1.getAllDefaulted() + 1);
						table1.setAllEligible(table1.getAllEligible() + 1);
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setAllTransferOut(table1.getAllTransferOut() + 1);
						
					}
					
					else if (canceled!=null && canceled) {
						table1.setAllCanceled(table1.getAllCanceled() + 1);
						
					}
					
					else if (sld!=null && sld) {
						table1.setAllSLD(table1.getAllSLD() + 1);
						
					}
    	    	}
    	    	//NEW
    	    	if(q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.new.conceptId"))) {
    	    		
    	    		table1.setNewAllDetected(table1.getNewAllDetected() + 1);
    	    		
    	    		//P
    	    		if(pulmonary!=null && pulmonary) {
    	    			
    	    			//BC
    	    			if(bacPositive) {
    	    				
    	    				table1.setNewPulmonaryBCDetected(table1.getNewPulmonaryBCDetected() + 1);
    	    				
    	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
    	    					
    	    					table1.setNewPulmonaryBCDetected04(table1.getNewPulmonaryBCDetected04() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setNewAllCured(table1.getNewAllCured() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCCured04(table1.getNewPulmonaryBCCured04() + 1);
    	    						table1.setNewPulmonaryBCEligible04(table1.getNewPulmonaryBCEligible04() + 1);
    	    						table1.setNewPulmonaryBCCured(table1.getNewPulmonaryBCCured() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    						
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setNewAllCompleted(table1.getNewAllCompleted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCCompleted04(table1.getNewPulmonaryBCCompleted04() + 1);
    	    						table1.setNewPulmonaryBCEligible04(table1.getNewPulmonaryBCEligible04() + 1);
    	    						table1.setNewPulmonaryBCCompleted(table1.getNewPulmonaryBCCompleted() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setNewAllDiedTB(table1.getNewAllDiedTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCDiedTB04(table1.getNewPulmonaryBCDiedTB04() + 1);
    	    						table1.setNewPulmonaryBCEligible04(table1.getNewPulmonaryBCEligible04() + 1);
    	    						table1.setNewPulmonaryBCDiedTB(table1.getNewPulmonaryBCDiedTB() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setNewAllDiedNotTB(table1.getNewAllDiedNotTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCDiedNotTB04(table1.getNewPulmonaryBCDiedNotTB04() + 1);
    	    						table1.setNewPulmonaryBCEligible04(table1.getNewPulmonaryBCEligible04() + 1);
    	    						table1.setNewPulmonaryBCDiedNotTB(table1.getNewPulmonaryBCDiedNotTB() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setNewAllFailed(table1.getNewAllFailed() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCFailed04(table1.getNewPulmonaryBCFailed04() + 1);
    	    						table1.setNewPulmonaryBCEligible04(table1.getNewPulmonaryBCEligible04() + 1);
    	    						table1.setNewPulmonaryBCFailed(table1.getNewPulmonaryBCFailed() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setNewAllDefaulted(table1.getNewAllDefaulted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCDefaulted04(table1.getNewPulmonaryBCDefaulted04() + 1);
    	    						table1.setNewPulmonaryBCEligible04(table1.getNewPulmonaryBCEligible04() + 1);
    	    						table1.setNewPulmonaryBCDefaulted(table1.getNewPulmonaryBCDefaulted() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setNewAllTransferOut(table1.getNewAllTransferOut() + 1);
    	    						table1.setNewPulmonaryBCTransferOut04(table1.getNewPulmonaryBCTransferOut04() + 1);
    	    						table1.setNewPulmonaryBCTransferOut(table1.getNewPulmonaryBCTransferOut() + 1);
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setNewAllCanceled(table1.getNewAllCanceled() + 1);
    	    						table1.setNewPulmonaryBCCanceled04(table1.getNewPulmonaryBCCanceled04() + 1);
    	    						table1.setNewPulmonaryBCCanceled(table1.getNewPulmonaryBCCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setNewAllSLD(table1.getNewAllSLD() + 1);
    	    						table1.setNewPulmonaryBCSLD04(table1.getNewPulmonaryBCSLD04() + 1);
    	    						table1.setNewPulmonaryBCSLD(table1.getNewPulmonaryBCSLD() + 1);
    	    					}
    	    					
    	    					

    	    				}
    	    				
    	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
    	    					
    	    					table1.setNewPulmonaryBCDetected0514(table1.getNewPulmonaryBCDetected0514() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setNewAllCured(table1.getNewAllCured() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCCured0514(table1.getNewPulmonaryBCCured0514() + 1);
    	    						table1.setNewPulmonaryBCEligible0514(table1.getNewPulmonaryBCEligible0514() + 1);
    	    						table1.setNewPulmonaryBCCured(table1.getNewPulmonaryBCCured() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setNewAllCompleted(table1.getNewAllCompleted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCCompleted0514(table1.getNewPulmonaryBCCompleted0514() + 1);
    	    						table1.setNewPulmonaryBCEligible0514(table1.getNewPulmonaryBCEligible0514() + 1);
    	    						table1.setNewPulmonaryBCCompleted(table1.getNewPulmonaryBCCompleted() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setNewAllDiedTB(table1.getNewAllDiedTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCDiedTB0514(table1.getNewPulmonaryBCDiedTB0514() + 1);
    	    						table1.setNewPulmonaryBCEligible0514(table1.getNewPulmonaryBCEligible0514() + 1);
    	    						table1.setNewPulmonaryBCDiedTB(table1.getNewPulmonaryBCDiedTB() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setNewAllDiedNotTB(table1.getNewAllDiedNotTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCDiedNotTB0514(table1.getNewPulmonaryBCDiedNotTB0514() + 1);
    	    						table1.setNewPulmonaryBCEligible0514(table1.getNewPulmonaryBCEligible0514() + 1);
    	    						table1.setNewPulmonaryBCDiedNotTB(table1.getNewPulmonaryBCDiedNotTB() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setNewAllFailed(table1.getNewAllFailed() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCFailed0514(table1.getNewPulmonaryBCFailed0514() + 1);
    	    						table1.setNewPulmonaryBCEligible0514(table1.getNewPulmonaryBCEligible0514() + 1);
    	    						table1.setNewPulmonaryBCFailed(table1.getNewPulmonaryBCFailed() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setNewAllDefaulted(table1.getNewAllDefaulted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCDefaulted0514(table1.getNewPulmonaryBCDefaulted0514() + 1);
    	    						table1.setNewPulmonaryBCEligible0514(table1.getNewPulmonaryBCEligible0514() + 1);
    	    						table1.setNewPulmonaryBCDefaulted(table1.getNewPulmonaryBCDefaulted() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setNewAllTransferOut(table1.getNewAllTransferOut() + 1);
    	    						table1.setNewPulmonaryBCTransferOut0514(table1.getNewPulmonaryBCTransferOut0514() + 1);
    	    						table1.setNewPulmonaryBCTransferOut(table1.getNewPulmonaryBCTransferOut() + 1);
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setNewAllCanceled(table1.getNewAllCanceled() + 1);
    	    						table1.setNewPulmonaryBCCanceled0514(table1.getNewPulmonaryBCCanceled0514() + 1);
    	    						table1.setNewPulmonaryBCCanceled(table1.getNewPulmonaryBCCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setNewAllSLD(table1.getNewAllSLD() + 1);
    	    						table1.setNewPulmonaryBCSLD0514(table1.getNewPulmonaryBCSLD0514() + 1);
    	    						table1.setNewPulmonaryBCSLD(table1.getNewPulmonaryBCSLD() + 1);
    	    					}
    	    					
    	    				}
    	    				
    	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
    	    					
    	    					table1.setNewPulmonaryBCDetected1517(table1.getNewPulmonaryBCDetected1517() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setNewAllCured(table1.getNewAllCured() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCCured1517(table1.getNewPulmonaryBCCured1517() + 1);
    	    						table1.setNewPulmonaryBCEligible1517(table1.getNewPulmonaryBCEligible1517() + 1);
    	    						table1.setNewPulmonaryBCCured(table1.getNewPulmonaryBCCured() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setNewAllCompleted(table1.getNewAllCompleted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCCompleted1517(table1.getNewPulmonaryBCCompleted1517() + 1);
    	    						table1.setNewPulmonaryBCEligible1517(table1.getNewPulmonaryBCEligible1517() + 1);
    	    						table1.setNewPulmonaryBCCompleted(table1.getNewPulmonaryBCCompleted() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setNewAllDiedTB(table1.getNewAllDiedTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCDiedTB1517(table1.getNewPulmonaryBCDiedTB1517() + 1);
    	    						table1.setNewPulmonaryBCEligible1517(table1.getNewPulmonaryBCEligible1517() + 1);
    	    						table1.setNewPulmonaryBCDiedTB(table1.getNewPulmonaryBCDiedTB() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setNewAllDiedNotTB(table1.getNewAllDiedNotTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCDiedNotTB1517(table1.getNewPulmonaryBCDiedNotTB1517() + 1);
    	    						table1.setNewPulmonaryBCEligible1517(table1.getNewPulmonaryBCEligible1517() + 1);
    	    						table1.setNewPulmonaryBCDiedNotTB(table1.getNewPulmonaryBCDiedNotTB() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setNewAllFailed(table1.getNewAllFailed() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCFailed1517(table1.getNewPulmonaryBCFailed1517() + 1);
    	    						table1.setNewPulmonaryBCEligible1517(table1.getNewPulmonaryBCEligible1517() + 1);
    	    						table1.setNewPulmonaryBCFailed(table1.getNewPulmonaryBCFailed() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setNewAllDefaulted(table1.getNewAllDefaulted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCDefaulted1517(table1.getNewPulmonaryBCDefaulted1517() + 1);
    	    						table1.setNewPulmonaryBCEligible1517(table1.getNewPulmonaryBCEligible1517() + 1);
    	    						table1.setNewPulmonaryBCDefaulted(table1.getNewPulmonaryBCDefaulted() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setNewAllTransferOut(table1.getNewAllTransferOut() + 1);
    	    						table1.setNewPulmonaryBCTransferOut1517(table1.getNewPulmonaryBCTransferOut1517() + 1);
    	    						table1.setNewPulmonaryBCTransferOut(table1.getNewPulmonaryBCTransferOut() + 1);
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setNewAllCanceled(table1.getNewAllCanceled() + 1);
    	    						table1.setNewPulmonaryBCCanceled1517(table1.getNewPulmonaryBCCanceled1517() + 1);
    	    						table1.setNewPulmonaryBCCanceled(table1.getNewPulmonaryBCCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setNewAllSLD(table1.getNewAllSLD() + 1);
    	    						table1.setNewPulmonaryBCSLD1517(table1.getNewPulmonaryBCSLD1517() + 1);
    	    						table1.setNewPulmonaryBCSLD(table1.getNewPulmonaryBCSLD() + 1);
    	    					}
    	    					
    	    				}
    	    				
    	    				else {
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setNewAllCured(table1.getNewAllCured() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCCured(table1.getNewPulmonaryBCCured() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setNewAllCompleted(table1.getNewAllCompleted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCCompleted(table1.getNewPulmonaryBCCompleted() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setNewAllDiedTB(table1.getNewAllDiedTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCDiedTB(table1.getNewPulmonaryBCDiedTB() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setNewAllDiedNotTB(table1.getNewAllDiedNotTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCDiedNotTB(table1.getNewPulmonaryBCDiedNotTB() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setNewAllFailed(table1.getNewAllFailed() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCFailed(table1.getNewPulmonaryBCFailed() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setNewAllDefaulted(table1.getNewAllDefaulted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryBCDefaulted(table1.getNewPulmonaryBCDefaulted() + 1);
    	    						table1.setNewPulmonaryBCEligible(table1.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setNewAllTransferOut(table1.getNewAllTransferOut() + 1);
    	    						table1.setNewPulmonaryBCTransferOut(table1.getNewPulmonaryBCTransferOut() + 1);

    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setNewAllCanceled(table1.getNewAllCanceled() + 1);
    	    						table1.setNewPulmonaryBCCanceled(table1.getNewPulmonaryBCCanceled() + 1);
    	    						
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setNewAllSLD(table1.getNewAllSLD() + 1);
    	    						table1.setNewPulmonaryBCSLD(table1.getNewPulmonaryBCSLD() + 1);
    	    						
    	    					}
    	    				}
    	    				
    	    			}
    	    			
    	    			//CD
    	    			else {
    	    				
    	    				table1.setNewPulmonaryCDDetected(table1.getNewPulmonaryCDDetected() + 1);
    	    				
    	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
    	    					
    	    					table1.setNewPulmonaryCDDetected04(table1.getNewPulmonaryCDDetected04() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setNewAllCured(table1.getNewAllCured() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDCured04(table1.getNewPulmonaryCDCured04() + 1);
    	    						table1.setNewPulmonaryCDEligible04(table1.getNewPulmonaryCDEligible04() + 1);
    	    						table1.setNewPulmonaryCDCured(table1.getNewPulmonaryCDCured() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setNewAllCompleted(table1.getNewAllCompleted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDCompleted04(table1.getNewPulmonaryCDCompleted04() + 1);
    	    						table1.setNewPulmonaryCDEligible04(table1.getNewPulmonaryCDEligible04() + 1);
    	    						table1.setNewPulmonaryCDCompleted(table1.getNewPulmonaryCDCompleted() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setNewAllDiedTB(table1.getNewAllDiedTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDDiedTB04(table1.getNewPulmonaryCDDiedTB04() + 1);
    	    						table1.setNewPulmonaryCDEligible04(table1.getNewPulmonaryCDEligible04() + 1);
    	    						table1.setNewPulmonaryCDDiedTB(table1.getNewPulmonaryCDDiedTB() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setNewAllDiedNotTB(table1.getNewAllDiedNotTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDDiedNotTB04(table1.getNewPulmonaryCDDiedNotTB04() + 1);
    	    						table1.setNewPulmonaryCDEligible04(table1.getNewPulmonaryCDEligible04() + 1);
    	    						table1.setNewPulmonaryCDDiedNotTB(table1.getNewPulmonaryCDDiedNotTB() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setNewAllFailed(table1.getNewAllFailed() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDFailed04(table1.getNewPulmonaryCDFailed04() + 1);
    	    						table1.setNewPulmonaryCDEligible04(table1.getNewPulmonaryCDEligible04() + 1);
    	    						table1.setNewPulmonaryCDFailed(table1.getNewPulmonaryCDFailed() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setNewAllDefaulted(table1.getNewAllDefaulted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDDefaulted04(table1.getNewPulmonaryCDDefaulted04() + 1);
    	    						table1.setNewPulmonaryCDEligible04(table1.getNewPulmonaryCDEligible04() + 1);
    	    						table1.setNewPulmonaryCDDefaulted(table1.getNewPulmonaryCDDefaulted() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setNewAllTransferOut(table1.getNewAllTransferOut() + 1);
    	    						table1.setNewPulmonaryCDTransferOut04(table1.getNewPulmonaryCDTransferOut04() + 1);
    	    						table1.setNewPulmonaryCDTransferOut(table1.getNewPulmonaryCDTransferOut() + 1);
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setNewAllCanceled(table1.getNewAllCanceled() + 1);
    	    						table1.setNewPulmonaryCDCanceled04(table1.getNewPulmonaryCDCanceled04() + 1);
    	    						table1.setNewPulmonaryCDCanceled(table1.getNewPulmonaryCDCanceled() + 1);
    	    						
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setNewAllSLD(table1.getNewAllSLD() + 1);
    	    						table1.setNewPulmonaryCDSLD04(table1.getNewPulmonaryCDSLD04() + 1);
    	    						table1.setNewPulmonaryCDSLD(table1.getNewPulmonaryCDSLD() + 1);
    	    						
    	    					}
    	    					
    	    					

    	    				}
    	    				
    	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
    	    					
    	    					table1.setNewPulmonaryCDDetected0514(table1.getNewPulmonaryCDDetected0514() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setNewAllCured(table1.getNewAllCured() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDCured0514(table1.getNewPulmonaryCDCured0514() + 1);
    	    						table1.setNewPulmonaryCDEligible0514(table1.getNewPulmonaryCDEligible0514() + 1);
    	    						table1.setNewPulmonaryCDCured(table1.getNewPulmonaryCDCured() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setNewAllCompleted(table1.getNewAllCompleted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDCompleted0514(table1.getNewPulmonaryCDCompleted0514() + 1);
    	    						table1.setNewPulmonaryCDEligible0514(table1.getNewPulmonaryCDEligible0514() + 1);
    	    						table1.setNewPulmonaryCDCompleted(table1.getNewPulmonaryCDCompleted() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setNewAllDiedTB(table1.getNewAllDiedTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDDiedTB0514(table1.getNewPulmonaryCDDiedTB0514() + 1);
    	    						table1.setNewPulmonaryCDEligible0514(table1.getNewPulmonaryCDEligible0514() + 1);
    	    						table1.setNewPulmonaryCDDiedTB(table1.getNewPulmonaryCDDiedTB() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setNewAllDiedNotTB(table1.getNewAllDiedNotTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDDiedNotTB0514(table1.getNewPulmonaryCDDiedNotTB0514() + 1);
    	    						table1.setNewPulmonaryCDEligible0514(table1.getNewPulmonaryCDEligible0514() + 1);
    	    						table1.setNewPulmonaryCDDiedNotTB(table1.getNewPulmonaryCDDiedNotTB() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setNewAllFailed(table1.getNewAllFailed() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDFailed0514(table1.getNewPulmonaryCDFailed0514() + 1);
    	    						table1.setNewPulmonaryCDEligible0514(table1.getNewPulmonaryCDEligible0514() + 1);
    	    						table1.setNewPulmonaryCDFailed(table1.getNewPulmonaryCDFailed() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setNewAllDefaulted(table1.getNewAllDefaulted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDDefaulted0514(table1.getNewPulmonaryCDDefaulted0514() + 1);
    	    						table1.setNewPulmonaryCDEligible0514(table1.getNewPulmonaryCDEligible0514() + 1);
    	    						table1.setNewPulmonaryCDDefaulted(table1.getNewPulmonaryCDDefaulted() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setNewAllTransferOut(table1.getNewAllTransferOut() + 1);
    	    						table1.setNewPulmonaryCDTransferOut0514(table1.getNewPulmonaryCDTransferOut0514() + 1);
    	    						table1.setNewPulmonaryCDTransferOut(table1.getNewPulmonaryCDTransferOut() + 1);
    	    						
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setNewAllCanceled(table1.getNewAllCanceled() + 1);
    	    						table1.setNewPulmonaryCDCanceled0514(table1.getNewPulmonaryCDCanceled0514() + 1);
    	    						table1.setNewPulmonaryCDCanceled(table1.getNewPulmonaryCDCanceled() + 1);
    	    						
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setNewAllSLD(table1.getNewAllSLD() + 1);
    	    						table1.setNewPulmonaryCDSLD0514(table1.getNewPulmonaryCDSLD0514() + 1);
    	    						table1.setNewPulmonaryCDSLD(table1.getNewPulmonaryCDSLD() + 1);
    	    						
    	    					}
    	    					
    	    				}
    	    				
    	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
    	    					
    	    					table1.setNewPulmonaryCDDetected1517(table1.getNewPulmonaryCDDetected1517() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setNewAllCured(table1.getNewAllCured() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDCured1517(table1.getNewPulmonaryCDCured1517() + 1);
    	    						table1.setNewPulmonaryCDEligible1517(table1.getNewPulmonaryCDEligible1517() + 1);
    	    						table1.setNewPulmonaryCDCured(table1.getNewPulmonaryCDCured() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setNewAllCompleted(table1.getNewAllCompleted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDCompleted1517(table1.getNewPulmonaryCDCompleted1517() + 1);
    	    						table1.setNewPulmonaryCDEligible1517(table1.getNewPulmonaryCDEligible1517() + 1);
    	    						table1.setNewPulmonaryCDCompleted(table1.getNewPulmonaryCDCompleted() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setNewAllDiedTB(table1.getNewAllDiedTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDDiedTB1517(table1.getNewPulmonaryCDDiedTB1517() + 1);
    	    						table1.setNewPulmonaryCDEligible1517(table1.getNewPulmonaryCDEligible1517() + 1);
    	    						table1.setNewPulmonaryCDDiedTB(table1.getNewPulmonaryCDDiedTB() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setNewAllDiedNotTB(table1.getNewAllDiedNotTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDDiedNotTB1517(table1.getNewPulmonaryCDDiedNotTB1517() + 1);
    	    						table1.setNewPulmonaryCDEligible1517(table1.getNewPulmonaryCDEligible1517() + 1);
    	    						table1.setNewPulmonaryCDDiedNotTB(table1.getNewPulmonaryCDDiedNotTB() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setNewAllFailed(table1.getNewAllFailed() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDFailed1517(table1.getNewPulmonaryCDFailed1517() + 1);
    	    						table1.setNewPulmonaryCDEligible1517(table1.getNewPulmonaryCDEligible1517() + 1);
    	    						table1.setNewPulmonaryCDFailed(table1.getNewPulmonaryCDFailed() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setNewAllDefaulted(table1.getNewAllDefaulted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDDefaulted1517(table1.getNewPulmonaryCDDefaulted1517() + 1);
    	    						table1.setNewPulmonaryCDEligible1517(table1.getNewPulmonaryCDEligible1517() + 1);
    	    						table1.setNewPulmonaryCDDefaulted(table1.getNewPulmonaryCDDefaulted() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setNewAllTransferOut(table1.getNewAllTransferOut() + 1);
    	    						table1.setNewPulmonaryCDTransferOut1517(table1.getNewPulmonaryCDTransferOut1517() + 1);
    	    						table1.setNewPulmonaryCDTransferOut(table1.getNewPulmonaryCDTransferOut() + 1);
    	    						
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setNewAllCanceled(table1.getNewAllCanceled() + 1);
    	    						table1.setNewPulmonaryCDCanceled1517(table1.getNewPulmonaryCDCanceled1517() + 1);
    	    						table1.setNewPulmonaryCDCanceled(table1.getNewPulmonaryCDCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setNewAllSLD(table1.getNewAllSLD() + 1);
    	    						table1.setNewPulmonaryCDSLD1517(table1.getNewPulmonaryCDSLD1517() + 1);
    	    						table1.setNewPulmonaryCDSLD(table1.getNewPulmonaryCDSLD() + 1);
    	    					}
    	    					
    	    				}
    	    				
    	    				else {
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setNewAllCured(table1.getNewAllCured() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDCured(table1.getNewPulmonaryCDCured() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setNewAllCompleted(table1.getNewAllCompleted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDCompleted(table1.getNewPulmonaryCDCompleted() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setNewAllDiedTB(table1.getNewAllDiedTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDDiedTB(table1.getNewPulmonaryCDDiedTB() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setNewAllDiedNotTB(table1.getNewAllDiedNotTB() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDDiedNotTB(table1.getNewPulmonaryCDDiedNotTB() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setNewAllFailed(table1.getNewAllFailed() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDFailed(table1.getNewPulmonaryCDFailed() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setNewAllDefaulted(table1.getNewAllDefaulted() + 1);
    	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
    	    						table1.setNewPulmonaryCDDefaulted(table1.getNewPulmonaryCDDefaulted() + 1);
    	    						table1.setNewPulmonaryCDEligible(table1.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setNewAllTransferOut(table1.getNewAllTransferOut() + 1);
    	    						table1.setNewPulmonaryCDTransferOut(table1.getNewPulmonaryCDTransferOut() + 1);

    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setNewAllCanceled(table1.getNewAllCanceled() + 1);
    	    						table1.setNewPulmonaryCDCanceled(table1.getNewPulmonaryCDCanceled() + 1);
    	    						
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setNewAllSLD(table1.getNewAllSLD() + 1);
    	    						table1.setNewPulmonaryCDSLD(table1.getNewPulmonaryCDSLD() + 1);
    	    						
    	    					}
    	    				}
    	    			}
    	    		}
    	    		
    	    		//EP
    	    		else if(pulmonary!=null && !pulmonary) {
    	    			
    	    			table1.setNewExtrapulmonaryDetected(table1.getNewExtrapulmonaryDetected() + 1);
	    				
	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
	    					
	    					table1.setNewExtrapulmonaryDetected04(table1.getNewExtrapulmonaryDetected04() + 1);
	    					
	    					if(cured!=null && cured) {
	    						table1.setNewAllCured(table1.getNewAllCured() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryCured04(table1.getNewExtrapulmonaryCured04() + 1);
	    						table1.setNewExtrapulmonaryEligible04(table1.getNewExtrapulmonaryEligible04() + 1);
	    						table1.setNewExtrapulmonaryCured(table1.getNewExtrapulmonaryCured() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table1.setNewAllCompleted(table1.getNewAllCompleted() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryCompleted04(table1.getNewExtrapulmonaryCompleted04() + 1);
	    						table1.setNewExtrapulmonaryEligible04(table1.getNewExtrapulmonaryEligible04() + 1);
	    						table1.setNewExtrapulmonaryCompleted(table1.getNewExtrapulmonaryCompleted() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table1.setNewAllDiedTB(table1.getNewAllDiedTB() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryDiedTB04(table1.getNewExtrapulmonaryDiedTB04() + 1);
	    						table1.setNewExtrapulmonaryEligible04(table1.getNewExtrapulmonaryEligible04() + 1);
	    						table1.setNewExtrapulmonaryDiedTB(table1.getNewExtrapulmonaryDiedTB() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table1.setNewAllDiedNotTB(table1.getNewAllDiedNotTB() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryDiedNotTB04(table1.getNewExtrapulmonaryDiedNotTB04() + 1);
	    						table1.setNewExtrapulmonaryEligible04(table1.getNewExtrapulmonaryEligible04() + 1);
	    						table1.setNewExtrapulmonaryDiedNotTB(table1.getNewExtrapulmonaryDiedNotTB() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table1.setNewAllFailed(table1.getNewAllFailed() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryFailed04(table1.getNewExtrapulmonaryFailed04() + 1);
	    						table1.setNewExtrapulmonaryEligible04(table1.getNewExtrapulmonaryEligible04() + 1);
	    						table1.setNewExtrapulmonaryFailed(table1.getNewExtrapulmonaryFailed() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table1.setNewAllDefaulted(table1.getNewAllDefaulted() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryDefaulted04(table1.getNewExtrapulmonaryDefaulted04() + 1);
	    						table1.setNewExtrapulmonaryEligible04(table1.getNewExtrapulmonaryEligible04() + 1);
	    						table1.setNewExtrapulmonaryDefaulted(table1.getNewExtrapulmonaryDefaulted() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table1.setNewAllTransferOut(table1.getNewAllTransferOut() + 1);
	    						table1.setNewExtrapulmonaryTransferOut04(table1.getNewExtrapulmonaryTransferOut04() + 1);
	    						table1.setNewExtrapulmonaryTransferOut(table1.getNewExtrapulmonaryTransferOut() + 1);
	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table1.setNewAllCanceled(table1.getNewAllCanceled() + 1);
	    						table1.setNewExtrapulmonaryCanceled04(table1.getNewExtrapulmonaryCanceled04() + 1);
	    						table1.setNewExtrapulmonaryCanceled(table1.getNewExtrapulmonaryCanceled() + 1);
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table1.setNewAllSLD(table1.getNewAllSLD() + 1);
	    						table1.setNewExtrapulmonarySLD04(table1.getNewExtrapulmonarySLD04() + 1);
	    						table1.setNewExtrapulmonarySLD(table1.getNewExtrapulmonarySLD() + 1);
	    					}
	    					
	    					

	    				}
	    				
	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
	    					
	    					table1.setNewExtrapulmonaryDetected0514(table1.getNewExtrapulmonaryDetected0514() + 1);
	    					
	    					if(cured!=null && cured) {
	    						table1.setNewAllCured(table1.getNewAllCured() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryCured0514(table1.getNewExtrapulmonaryCured0514() + 1);
	    						table1.setNewExtrapulmonaryEligible0514(table1.getNewExtrapulmonaryEligible0514() + 1);
	    						table1.setNewExtrapulmonaryCured(table1.getNewExtrapulmonaryCured() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table1.setNewAllCompleted(table1.getNewAllCompleted() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryCompleted0514(table1.getNewExtrapulmonaryCompleted0514() + 1);
	    						table1.setNewExtrapulmonaryEligible0514(table1.getNewExtrapulmonaryEligible0514() + 1);
	    						table1.setNewExtrapulmonaryCompleted(table1.getNewExtrapulmonaryCompleted() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table1.setNewAllDiedTB(table1.getNewAllDiedTB() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryDiedTB0514(table1.getNewExtrapulmonaryDiedTB0514() + 1);
	    						table1.setNewExtrapulmonaryEligible0514(table1.getNewExtrapulmonaryEligible0514() + 1);
	    						table1.setNewExtrapulmonaryDiedTB(table1.getNewExtrapulmonaryDiedTB() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table1.setNewAllDiedNotTB(table1.getNewAllDiedNotTB() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryDiedNotTB0514(table1.getNewExtrapulmonaryDiedNotTB0514() + 1);
	    						table1.setNewExtrapulmonaryEligible0514(table1.getNewExtrapulmonaryEligible0514() + 1);
	    						table1.setNewExtrapulmonaryDiedNotTB(table1.getNewExtrapulmonaryDiedNotTB() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table1.setNewAllFailed(table1.getNewAllFailed() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryFailed0514(table1.getNewExtrapulmonaryFailed0514() + 1);
	    						table1.setNewExtrapulmonaryEligible0514(table1.getNewExtrapulmonaryEligible0514() + 1);
	    						table1.setNewExtrapulmonaryFailed(table1.getNewExtrapulmonaryFailed() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table1.setNewAllDefaulted(table1.getNewAllDefaulted() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryDefaulted0514(table1.getNewExtrapulmonaryDefaulted0514() + 1);
	    						table1.setNewExtrapulmonaryEligible0514(table1.getNewExtrapulmonaryEligible0514() + 1);
	    						table1.setNewExtrapulmonaryDefaulted(table1.getNewExtrapulmonaryDefaulted() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table1.setNewAllTransferOut(table1.getNewAllTransferOut() + 1);
	    						table1.setNewExtrapulmonaryTransferOut0514(table1.getNewExtrapulmonaryTransferOut0514() + 1);
	    						table1.setNewExtrapulmonaryTransferOut(table1.getNewExtrapulmonaryTransferOut() + 1);
	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table1.setNewAllCanceled(table1.getNewAllCanceled() + 1);
	    						table1.setNewExtrapulmonaryCanceled0514(table1.getNewExtrapulmonaryCanceled0514() + 1);
	    						table1.setNewExtrapulmonaryCanceled(table1.getNewExtrapulmonaryCanceled() + 1);
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table1.setNewAllSLD(table1.getNewAllSLD() + 1);
	    						table1.setNewExtrapulmonarySLD0514(table1.getNewExtrapulmonarySLD0514() + 1);
	    						table1.setNewExtrapulmonarySLD(table1.getNewExtrapulmonarySLD() + 1);
	    					}
	    					
	    				}
	    				
	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
	    					
	    					table1.setNewExtrapulmonaryDetected1517(table1.getNewExtrapulmonaryDetected1517() + 1);
	    					
	    					if(cured!=null && cured) {
	    						table1.setNewAllCured(table1.getNewAllCured() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryCured1517(table1.getNewExtrapulmonaryCured1517() + 1);
	    						table1.setNewExtrapulmonaryEligible1517(table1.getNewExtrapulmonaryEligible1517() + 1);
	    						table1.setNewExtrapulmonaryCured(table1.getNewExtrapulmonaryCured() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table1.setNewAllCompleted(table1.getNewAllCompleted() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryCompleted1517(table1.getNewExtrapulmonaryCompleted1517() + 1);
	    						table1.setNewExtrapulmonaryEligible1517(table1.getNewExtrapulmonaryEligible1517() + 1);
	    						table1.setNewExtrapulmonaryCompleted(table1.getNewExtrapulmonaryCompleted() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table1.setNewAllDiedTB(table1.getNewAllDiedTB() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryDiedTB1517(table1.getNewExtrapulmonaryDiedTB1517() + 1);
	    						table1.setNewExtrapulmonaryEligible1517(table1.getNewExtrapulmonaryEligible1517() + 1);
	    						table1.setNewExtrapulmonaryDiedTB(table1.getNewExtrapulmonaryDiedTB() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table1.setNewAllDiedNotTB(table1.getNewAllDiedNotTB() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryDiedNotTB1517(table1.getNewExtrapulmonaryDiedNotTB1517() + 1);
	    						table1.setNewExtrapulmonaryEligible1517(table1.getNewExtrapulmonaryEligible1517() + 1);
	    						table1.setNewExtrapulmonaryDiedNotTB(table1.getNewExtrapulmonaryDiedNotTB() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table1.setNewAllFailed(table1.getNewAllFailed() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryFailed1517(table1.getNewExtrapulmonaryFailed1517() + 1);
	    						table1.setNewExtrapulmonaryEligible1517(table1.getNewExtrapulmonaryEligible1517() + 1);
	    						table1.setNewExtrapulmonaryFailed(table1.getNewExtrapulmonaryFailed() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table1.setNewAllDefaulted(table1.getNewAllDefaulted() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryDefaulted1517(table1.getNewExtrapulmonaryDefaulted1517() + 1);
	    						table1.setNewExtrapulmonaryEligible1517(table1.getNewExtrapulmonaryEligible1517() + 1);
	    						table1.setNewExtrapulmonaryDefaulted(table1.getNewExtrapulmonaryDefaulted() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table1.setNewAllTransferOut(table1.getNewAllTransferOut() + 1);
	    						table1.setNewExtrapulmonaryTransferOut1517(table1.getNewExtrapulmonaryTransferOut1517() + 1);
	    						table1.setNewExtrapulmonaryTransferOut(table1.getNewExtrapulmonaryTransferOut() + 1);
	    						
	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table1.setNewAllCanceled(table1.getNewAllCanceled() + 1);
	    						table1.setNewExtrapulmonaryCanceled1517(table1.getNewExtrapulmonaryCanceled1517() + 1);
	    						table1.setNewExtrapulmonaryCanceled(table1.getNewExtrapulmonaryCanceled() + 1);
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table1.setNewAllSLD(table1.getNewAllSLD() + 1);
	    						table1.setNewExtrapulmonarySLD1517(table1.getNewExtrapulmonarySLD1517() + 1);
	    						table1.setNewExtrapulmonarySLD(table1.getNewExtrapulmonarySLD() + 1);
	    					}
	    					
	    				}
	    				
	    				else {
	    					
	    					if(cured!=null && cured) {
	    						table1.setNewAllCured(table1.getNewAllCured() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryCured(table1.getNewExtrapulmonaryCured() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table1.setNewAllCompleted(table1.getNewAllCompleted() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryCompleted(table1.getNewExtrapulmonaryCompleted() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table1.setNewAllDiedTB(table1.getNewAllDiedTB() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryDiedTB(table1.getNewExtrapulmonaryDiedTB() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table1.setNewAllDiedNotTB(table1.getNewAllDiedNotTB() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryDiedNotTB(table1.getNewExtrapulmonaryDiedNotTB() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table1.setNewAllFailed(table1.getNewAllFailed() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryFailed(table1.getNewExtrapulmonaryFailed() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table1.setNewAllDefaulted(table1.getNewAllDefaulted() + 1);
	    						table1.setNewAllEligible(table1.getNewAllEligible() + 1);
	    						table1.setNewExtrapulmonaryDefaulted(table1.getNewExtrapulmonaryDefaulted() + 1);
	    						table1.setNewExtrapulmonaryEligible(table1.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table1.setNewAllTransferOut(table1.getNewAllTransferOut() + 1);
	    						table1.setNewExtrapulmonaryTransferOut(table1.getNewExtrapulmonaryTransferOut() + 1);

	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table1.setNewAllCanceled(table1.getNewAllCanceled() + 1);
	    						table1.setNewExtrapulmonaryCanceled(table1.getNewExtrapulmonaryCanceled() + 1);
	    						
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table1.setNewAllSLD(table1.getNewAllSLD() + 1);
	    						table1.setNewExtrapulmonarySLD(table1.getNewExtrapulmonarySLD() + 1);
	    						
	    					}
	    				}
	    			
    	    		}

    	    	} 	
    	    	
    	    	//RELAPSE
    	    	else if(q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterRelapse1.conceptId")) || q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterRelapse2.conceptId"))) {
    	    		
    	    		table1.setRelapseAllDetected(table1.getRelapseAllDetected() + 1);
    	    		
    	    		//P
    	    		if(pulmonary!=null && pulmonary) {
    	    			
    	    			//BC
    	    			if(bacPositive) {
    	    				
    	    				table1.setRelapsePulmonaryBCDetected(table1.getRelapsePulmonaryBCDetected() + 1);
    	    				
    	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
    	    					
    	    					table1.setRelapsePulmonaryBCDetected04(table1.getRelapsePulmonaryBCDetected04() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setRelapseAllCured(table1.getRelapseAllCured() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCCured04(table1.getRelapsePulmonaryBCCured04() + 1);
    	    						table1.setRelapsePulmonaryBCEligible04(table1.getRelapsePulmonaryBCEligible04() + 1);
    	    						table1.setRelapsePulmonaryBCCured(table1.getRelapsePulmonaryBCCured() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    						
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setRelapseAllCompleted(table1.getRelapseAllCompleted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCCompleted04(table1.getRelapsePulmonaryBCCompleted04() + 1);
    	    						table1.setRelapsePulmonaryBCEligible04(table1.getRelapsePulmonaryBCEligible04() + 1);
    	    						table1.setRelapsePulmonaryBCCompleted(table1.getRelapsePulmonaryBCCompleted() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setRelapseAllDiedTB(table1.getRelapseAllDiedTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCDiedTB04(table1.getRelapsePulmonaryBCDiedTB04() + 1);
    	    						table1.setRelapsePulmonaryBCEligible04(table1.getRelapsePulmonaryBCEligible04() + 1);
    	    						table1.setRelapsePulmonaryBCDiedTB(table1.getRelapsePulmonaryBCDiedTB() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setRelapseAllDiedNotTB(table1.getRelapseAllDiedNotTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCDiedNotTB04(table1.getRelapsePulmonaryBCDiedNotTB04() + 1);
    	    						table1.setRelapsePulmonaryBCEligible04(table1.getRelapsePulmonaryBCEligible04() + 1);
    	    						table1.setRelapsePulmonaryBCDiedNotTB(table1.getRelapsePulmonaryBCDiedNotTB() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setRelapseAllFailed(table1.getRelapseAllFailed() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCFailed04(table1.getRelapsePulmonaryBCFailed04() + 1);
    	    						table1.setRelapsePulmonaryBCEligible04(table1.getRelapsePulmonaryBCEligible04() + 1);
    	    						table1.setRelapsePulmonaryBCFailed(table1.getRelapsePulmonaryBCFailed() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setRelapseAllDefaulted(table1.getRelapseAllDefaulted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCDefaulted04(table1.getRelapsePulmonaryBCDefaulted04() + 1);
    	    						table1.setRelapsePulmonaryBCEligible04(table1.getRelapsePulmonaryBCEligible04() + 1);
    	    						table1.setRelapsePulmonaryBCDefaulted(table1.getRelapsePulmonaryBCDefaulted() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setRelapseAllTransferOut(table1.getRelapseAllTransferOut() + 1);
    	    						table1.setRelapsePulmonaryBCTransferOut04(table1.getRelapsePulmonaryBCTransferOut04() + 1);
    	    						table1.setRelapsePulmonaryBCTransferOut(table1.getRelapsePulmonaryBCTransferOut() + 1);
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setRelapseAllCanceled(table1.getRelapseAllCanceled() + 1);
    	    						table1.setRelapsePulmonaryBCCanceled04(table1.getRelapsePulmonaryBCCanceled04() + 1);
    	    						table1.setRelapsePulmonaryBCCanceled(table1.getRelapsePulmonaryBCCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setRelapseAllSLD(table1.getRelapseAllSLD() + 1);
    	    						table1.setRelapsePulmonaryBCSLD04(table1.getRelapsePulmonaryBCSLD04() + 1);
    	    						table1.setRelapsePulmonaryBCSLD(table1.getRelapsePulmonaryBCSLD() + 1);
    	    					}
    	    					
    	    					

    	    				}
    	    				
    	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
    	    					
    	    					table1.setRelapsePulmonaryBCDetected0514(table1.getRelapsePulmonaryBCDetected0514() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setRelapseAllCured(table1.getRelapseAllCured() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCCured0514(table1.getRelapsePulmonaryBCCured0514() + 1);
    	    						table1.setRelapsePulmonaryBCEligible0514(table1.getRelapsePulmonaryBCEligible0514() + 1);
    	    						table1.setRelapsePulmonaryBCCured(table1.getRelapsePulmonaryBCCured() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setRelapseAllCompleted(table1.getRelapseAllCompleted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCCompleted0514(table1.getRelapsePulmonaryBCCompleted0514() + 1);
    	    						table1.setRelapsePulmonaryBCEligible0514(table1.getRelapsePulmonaryBCEligible0514() + 1);
    	    						table1.setRelapsePulmonaryBCCompleted(table1.getRelapsePulmonaryBCCompleted() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setRelapseAllDiedTB(table1.getRelapseAllDiedTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCDiedTB0514(table1.getRelapsePulmonaryBCDiedTB0514() + 1);
    	    						table1.setRelapsePulmonaryBCEligible0514(table1.getRelapsePulmonaryBCEligible0514() + 1);
    	    						table1.setRelapsePulmonaryBCDiedTB(table1.getRelapsePulmonaryBCDiedTB() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setRelapseAllDiedNotTB(table1.getRelapseAllDiedNotTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCDiedNotTB0514(table1.getRelapsePulmonaryBCDiedNotTB0514() + 1);
    	    						table1.setRelapsePulmonaryBCEligible0514(table1.getRelapsePulmonaryBCEligible0514() + 1);
    	    						table1.setRelapsePulmonaryBCDiedNotTB(table1.getRelapsePulmonaryBCDiedNotTB() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setRelapseAllFailed(table1.getRelapseAllFailed() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCFailed0514(table1.getRelapsePulmonaryBCFailed0514() + 1);
    	    						table1.setRelapsePulmonaryBCEligible0514(table1.getRelapsePulmonaryBCEligible0514() + 1);
    	    						table1.setRelapsePulmonaryBCFailed(table1.getRelapsePulmonaryBCFailed() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setRelapseAllDefaulted(table1.getRelapseAllDefaulted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCDefaulted0514(table1.getRelapsePulmonaryBCDefaulted0514() + 1);
    	    						table1.setRelapsePulmonaryBCEligible0514(table1.getRelapsePulmonaryBCEligible0514() + 1);
    	    						table1.setRelapsePulmonaryBCDefaulted(table1.getRelapsePulmonaryBCDefaulted() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setRelapseAllTransferOut(table1.getRelapseAllTransferOut() + 1);
    	    						table1.setRelapsePulmonaryBCTransferOut0514(table1.getRelapsePulmonaryBCTransferOut0514() + 1);
    	    						table1.setRelapsePulmonaryBCTransferOut(table1.getRelapsePulmonaryBCTransferOut() + 1);
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setRelapseAllCanceled(table1.getRelapseAllCanceled() + 1);
    	    						table1.setRelapsePulmonaryBCCanceled0514(table1.getRelapsePulmonaryBCCanceled0514() + 1);
    	    						table1.setRelapsePulmonaryBCCanceled(table1.getRelapsePulmonaryBCCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setRelapseAllSLD(table1.getRelapseAllSLD() + 1);
    	    						table1.setRelapsePulmonaryBCSLD0514(table1.getRelapsePulmonaryBCSLD0514() + 1);
    	    						table1.setRelapsePulmonaryBCSLD(table1.getRelapsePulmonaryBCSLD() + 1);
    	    					}
    	    					
    	    				}
    	    				
    	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
    	    					
    	    					table1.setRelapsePulmonaryBCDetected1517(table1.getRelapsePulmonaryBCDetected1517() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setRelapseAllCured(table1.getRelapseAllCured() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCCured1517(table1.getRelapsePulmonaryBCCured1517() + 1);
    	    						table1.setRelapsePulmonaryBCEligible1517(table1.getRelapsePulmonaryBCEligible1517() + 1);
    	    						table1.setRelapsePulmonaryBCCured(table1.getRelapsePulmonaryBCCured() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setRelapseAllCompleted(table1.getRelapseAllCompleted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCCompleted1517(table1.getRelapsePulmonaryBCCompleted1517() + 1);
    	    						table1.setRelapsePulmonaryBCEligible1517(table1.getRelapsePulmonaryBCEligible1517() + 1);
    	    						table1.setRelapsePulmonaryBCCompleted(table1.getRelapsePulmonaryBCCompleted() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setRelapseAllDiedTB(table1.getRelapseAllDiedTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCDiedTB1517(table1.getRelapsePulmonaryBCDiedTB1517() + 1);
    	    						table1.setRelapsePulmonaryBCEligible1517(table1.getRelapsePulmonaryBCEligible1517() + 1);
    	    						table1.setRelapsePulmonaryBCDiedTB(table1.getRelapsePulmonaryBCDiedTB() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setRelapseAllDiedNotTB(table1.getRelapseAllDiedNotTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCDiedNotTB1517(table1.getRelapsePulmonaryBCDiedNotTB1517() + 1);
    	    						table1.setRelapsePulmonaryBCEligible1517(table1.getRelapsePulmonaryBCEligible1517() + 1);
    	    						table1.setRelapsePulmonaryBCDiedNotTB(table1.getRelapsePulmonaryBCDiedNotTB() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setRelapseAllFailed(table1.getRelapseAllFailed() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCFailed1517(table1.getRelapsePulmonaryBCFailed1517() + 1);
    	    						table1.setRelapsePulmonaryBCEligible1517(table1.getRelapsePulmonaryBCEligible1517() + 1);
    	    						table1.setRelapsePulmonaryBCFailed(table1.getRelapsePulmonaryBCFailed() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setRelapseAllDefaulted(table1.getRelapseAllDefaulted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCDefaulted1517(table1.getRelapsePulmonaryBCDefaulted1517() + 1);
    	    						table1.setRelapsePulmonaryBCEligible1517(table1.getRelapsePulmonaryBCEligible1517() + 1);
    	    						table1.setRelapsePulmonaryBCDefaulted(table1.getRelapsePulmonaryBCDefaulted() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setRelapseAllTransferOut(table1.getRelapseAllTransferOut() + 1);
    	    						table1.setRelapsePulmonaryBCTransferOut1517(table1.getRelapsePulmonaryBCTransferOut1517() + 1);
    	    						table1.setRelapsePulmonaryBCTransferOut(table1.getRelapsePulmonaryBCTransferOut() + 1);
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setRelapseAllCanceled(table1.getRelapseAllCanceled() + 1);
    	    						table1.setRelapsePulmonaryBCCanceled1517(table1.getRelapsePulmonaryBCCanceled1517() + 1);
    	    						table1.setRelapsePulmonaryBCCanceled(table1.getRelapsePulmonaryBCCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setRelapseAllSLD(table1.getRelapseAllSLD() + 1);
    	    						table1.setRelapsePulmonaryBCSLD1517(table1.getRelapsePulmonaryBCSLD1517() + 1);
    	    						table1.setRelapsePulmonaryBCSLD(table1.getRelapsePulmonaryBCSLD() + 1);
    	    					}
    	    					
    	    				}
    	    				
    	    				else {
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setRelapseAllCured(table1.getRelapseAllCured() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCCured(table1.getRelapsePulmonaryBCCured() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setRelapseAllCompleted(table1.getRelapseAllCompleted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCCompleted(table1.getRelapsePulmonaryBCCompleted() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setRelapseAllDiedTB(table1.getRelapseAllDiedTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCDiedTB(table1.getRelapsePulmonaryBCDiedTB() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setRelapseAllDiedNotTB(table1.getRelapseAllDiedNotTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCDiedNotTB(table1.getRelapsePulmonaryBCDiedNotTB() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setRelapseAllFailed(table1.getRelapseAllFailed() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCFailed(table1.getRelapsePulmonaryBCFailed() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setRelapseAllDefaulted(table1.getRelapseAllDefaulted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryBCDefaulted(table1.getRelapsePulmonaryBCDefaulted() + 1);
    	    						table1.setRelapsePulmonaryBCEligible(table1.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setRelapseAllTransferOut(table1.getRelapseAllTransferOut() + 1);
    	    						table1.setRelapsePulmonaryBCTransferOut(table1.getRelapsePulmonaryBCTransferOut() + 1);

    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setRelapseAllCanceled(table1.getRelapseAllCanceled() + 1);
    	    						table1.setRelapsePulmonaryBCCanceled(table1.getRelapsePulmonaryBCCanceled() + 1);
    	    						
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setRelapseAllSLD(table1.getRelapseAllSLD() + 1);
    	    						table1.setRelapsePulmonaryBCSLD(table1.getRelapsePulmonaryBCSLD() + 1);
    	    						
    	    					}
    	    				}
    	    				
    	    			}
    	    			
    	    			//CD
    	    			else {
    	    				
    	    				table1.setRelapsePulmonaryCDDetected(table1.getRelapsePulmonaryCDDetected() + 1);
    	    				
    	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
    	    					
    	    					table1.setRelapsePulmonaryCDDetected04(table1.getRelapsePulmonaryCDDetected04() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setRelapseAllCured(table1.getRelapseAllCured() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDCured04(table1.getRelapsePulmonaryCDCured04() + 1);
    	    						table1.setRelapsePulmonaryCDEligible04(table1.getRelapsePulmonaryCDEligible04() + 1);
    	    						table1.setRelapsePulmonaryCDCured(table1.getRelapsePulmonaryCDCured() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setRelapseAllCompleted(table1.getRelapseAllCompleted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDCompleted04(table1.getRelapsePulmonaryCDCompleted04() + 1);
    	    						table1.setRelapsePulmonaryCDEligible04(table1.getRelapsePulmonaryCDEligible04() + 1);
    	    						table1.setRelapsePulmonaryCDCompleted(table1.getRelapsePulmonaryCDCompleted() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setRelapseAllDiedTB(table1.getRelapseAllDiedTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDDiedTB04(table1.getRelapsePulmonaryCDDiedTB04() + 1);
    	    						table1.setRelapsePulmonaryCDEligible04(table1.getRelapsePulmonaryCDEligible04() + 1);
    	    						table1.setRelapsePulmonaryCDDiedTB(table1.getRelapsePulmonaryCDDiedTB() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setRelapseAllDiedNotTB(table1.getRelapseAllDiedNotTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDDiedNotTB04(table1.getRelapsePulmonaryCDDiedNotTB04() + 1);
    	    						table1.setRelapsePulmonaryCDEligible04(table1.getRelapsePulmonaryCDEligible04() + 1);
    	    						table1.setRelapsePulmonaryCDDiedNotTB(table1.getRelapsePulmonaryCDDiedNotTB() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setRelapseAllFailed(table1.getRelapseAllFailed() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDFailed04(table1.getRelapsePulmonaryCDFailed04() + 1);
    	    						table1.setRelapsePulmonaryCDEligible04(table1.getRelapsePulmonaryCDEligible04() + 1);
    	    						table1.setRelapsePulmonaryCDFailed(table1.getRelapsePulmonaryCDFailed() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setRelapseAllDefaulted(table1.getRelapseAllDefaulted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDDefaulted04(table1.getRelapsePulmonaryCDDefaulted04() + 1);
    	    						table1.setRelapsePulmonaryCDEligible04(table1.getRelapsePulmonaryCDEligible04() + 1);
    	    						table1.setRelapsePulmonaryCDDefaulted(table1.getRelapsePulmonaryCDDefaulted() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setRelapseAllTransferOut(table1.getRelapseAllTransferOut() + 1);
    	    						table1.setRelapsePulmonaryCDTransferOut04(table1.getRelapsePulmonaryCDTransferOut04() + 1);
    	    						table1.setRelapsePulmonaryCDTransferOut(table1.getRelapsePulmonaryCDTransferOut() + 1);
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setRelapseAllCanceled(table1.getRelapseAllCanceled() + 1);
    	    						table1.setRelapsePulmonaryCDCanceled04(table1.getRelapsePulmonaryCDCanceled04() + 1);
    	    						table1.setRelapsePulmonaryCDCanceled(table1.getRelapsePulmonaryCDCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setRelapseAllSLD(table1.getRelapseAllSLD() + 1);
    	    						table1.setRelapsePulmonaryCDSLD04(table1.getRelapsePulmonaryCDSLD04() + 1);
    	    						table1.setRelapsePulmonaryCDSLD(table1.getRelapsePulmonaryCDSLD() + 1);
    	    					}
    	    					
    	    					

    	    				}
    	    				
    	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
    	    					
    	    					table1.setRelapsePulmonaryCDDetected0514(table1.getRelapsePulmonaryCDDetected0514() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setRelapseAllCured(table1.getRelapseAllCured() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDCured0514(table1.getRelapsePulmonaryCDCured0514() + 1);
    	    						table1.setRelapsePulmonaryCDEligible0514(table1.getRelapsePulmonaryCDEligible0514() + 1);
    	    						table1.setRelapsePulmonaryCDCured(table1.getRelapsePulmonaryCDCured() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setRelapseAllCompleted(table1.getRelapseAllCompleted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDCompleted0514(table1.getRelapsePulmonaryCDCompleted0514() + 1);
    	    						table1.setRelapsePulmonaryCDEligible0514(table1.getRelapsePulmonaryCDEligible0514() + 1);
    	    						table1.setRelapsePulmonaryCDCompleted(table1.getRelapsePulmonaryCDCompleted() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setRelapseAllDiedTB(table1.getRelapseAllDiedTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDDiedTB0514(table1.getRelapsePulmonaryCDDiedTB0514() + 1);
    	    						table1.setRelapsePulmonaryCDEligible0514(table1.getRelapsePulmonaryCDEligible0514() + 1);
    	    						table1.setRelapsePulmonaryCDDiedTB(table1.getRelapsePulmonaryCDDiedTB() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setRelapseAllDiedNotTB(table1.getRelapseAllDiedNotTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDDiedNotTB0514(table1.getRelapsePulmonaryCDDiedNotTB0514() + 1);
    	    						table1.setRelapsePulmonaryCDEligible0514(table1.getRelapsePulmonaryCDEligible0514() + 1);
    	    						table1.setRelapsePulmonaryCDDiedNotTB(table1.getRelapsePulmonaryCDDiedNotTB() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setRelapseAllFailed(table1.getRelapseAllFailed() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDFailed0514(table1.getRelapsePulmonaryCDFailed0514() + 1);
    	    						table1.setRelapsePulmonaryCDEligible0514(table1.getRelapsePulmonaryCDEligible0514() + 1);
    	    						table1.setRelapsePulmonaryCDFailed(table1.getRelapsePulmonaryCDFailed() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setRelapseAllDefaulted(table1.getRelapseAllDefaulted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDDefaulted0514(table1.getRelapsePulmonaryCDDefaulted0514() + 1);
    	    						table1.setRelapsePulmonaryCDEligible0514(table1.getRelapsePulmonaryCDEligible0514() + 1);
    	    						table1.setRelapsePulmonaryCDDefaulted(table1.getRelapsePulmonaryCDDefaulted() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setRelapseAllTransferOut(table1.getRelapseAllTransferOut() + 1);
    	    						table1.setRelapsePulmonaryCDTransferOut0514(table1.getRelapsePulmonaryCDTransferOut0514() + 1);
    	    						table1.setRelapsePulmonaryCDTransferOut(table1.getRelapsePulmonaryCDTransferOut() + 1);
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setRelapseAllCanceled(table1.getRelapseAllCanceled() + 1);
    	    						table1.setRelapsePulmonaryCDCanceled0514(table1.getRelapsePulmonaryCDCanceled0514() + 1);
    	    						table1.setRelapsePulmonaryCDCanceled(table1.getRelapsePulmonaryCDCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setRelapseAllSLD(table1.getRelapseAllSLD() + 1);
    	    						table1.setRelapsePulmonaryCDSLD0514(table1.getRelapsePulmonaryCDSLD0514() + 1);
    	    						table1.setRelapsePulmonaryCDSLD(table1.getRelapsePulmonaryCDSLD() + 1);
    	    					}
    	    					
    	    				}
    	    				
    	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
    	    					
    	    					table1.setRelapsePulmonaryCDDetected1517(table1.getRelapsePulmonaryCDDetected1517() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setRelapseAllCured(table1.getRelapseAllCured() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDCured1517(table1.getRelapsePulmonaryCDCured1517() + 1);
    	    						table1.setRelapsePulmonaryCDEligible1517(table1.getRelapsePulmonaryCDEligible1517() + 1);
    	    						table1.setRelapsePulmonaryCDCured(table1.getRelapsePulmonaryCDCured() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setRelapseAllCompleted(table1.getRelapseAllCompleted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDCompleted1517(table1.getRelapsePulmonaryCDCompleted1517() + 1);
    	    						table1.setRelapsePulmonaryCDEligible1517(table1.getRelapsePulmonaryCDEligible1517() + 1);
    	    						table1.setRelapsePulmonaryCDCompleted(table1.getRelapsePulmonaryCDCompleted() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setRelapseAllDiedTB(table1.getRelapseAllDiedTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDDiedTB1517(table1.getRelapsePulmonaryCDDiedTB1517() + 1);
    	    						table1.setRelapsePulmonaryCDEligible1517(table1.getRelapsePulmonaryCDEligible1517() + 1);
    	    						table1.setRelapsePulmonaryCDDiedTB(table1.getRelapsePulmonaryCDDiedTB() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setRelapseAllDiedNotTB(table1.getRelapseAllDiedNotTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDDiedNotTB1517(table1.getRelapsePulmonaryCDDiedNotTB1517() + 1);
    	    						table1.setRelapsePulmonaryCDEligible1517(table1.getRelapsePulmonaryCDEligible1517() + 1);
    	    						table1.setRelapsePulmonaryCDDiedNotTB(table1.getRelapsePulmonaryCDDiedNotTB() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setRelapseAllFailed(table1.getRelapseAllFailed() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDFailed1517(table1.getRelapsePulmonaryCDFailed1517() + 1);
    	    						table1.setRelapsePulmonaryCDEligible1517(table1.getRelapsePulmonaryCDEligible1517() + 1);
    	    						table1.setRelapsePulmonaryCDFailed(table1.getRelapsePulmonaryCDFailed() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setRelapseAllDefaulted(table1.getRelapseAllDefaulted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDDefaulted1517(table1.getRelapsePulmonaryCDDefaulted1517() + 1);
    	    						table1.setRelapsePulmonaryCDEligible1517(table1.getRelapsePulmonaryCDEligible1517() + 1);
    	    						table1.setRelapsePulmonaryCDDefaulted(table1.getRelapsePulmonaryCDDefaulted() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setRelapseAllTransferOut(table1.getRelapseAllTransferOut() + 1);
    	    						table1.setRelapsePulmonaryCDTransferOut1517(table1.getRelapsePulmonaryCDTransferOut1517() + 1);
    	    						table1.setRelapsePulmonaryCDTransferOut(table1.getRelapsePulmonaryCDTransferOut() + 1);
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setRelapseAllCanceled(table1.getRelapseAllCanceled() + 1);
    	    						table1.setRelapsePulmonaryCDCanceled1517(table1.getRelapsePulmonaryCDCanceled1517() + 1);
    	    						table1.setRelapsePulmonaryCDCanceled(table1.getRelapsePulmonaryCDCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setRelapseAllSLD(table1.getRelapseAllSLD() + 1);
    	    						table1.setRelapsePulmonaryCDSLD1517(table1.getRelapsePulmonaryCDSLD1517() + 1);
    	    						table1.setRelapsePulmonaryCDSLD(table1.getRelapsePulmonaryCDSLD() + 1);
    	    					}
    	    					
    	    				}
    	    				
    	    				else {
    	    					
    	    					if(cured!=null && cured) {
    	    						table1.setRelapseAllCured(table1.getRelapseAllCured() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDCured(table1.getRelapsePulmonaryCDCured() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table1.setRelapseAllCompleted(table1.getRelapseAllCompleted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDCompleted(table1.getRelapsePulmonaryCDCompleted() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table1.setRelapseAllDiedTB(table1.getRelapseAllDiedTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDDiedTB(table1.getRelapsePulmonaryCDDiedTB() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table1.setRelapseAllDiedNotTB(table1.getRelapseAllDiedNotTB() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDDiedNotTB(table1.getRelapsePulmonaryCDDiedNotTB() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table1.setRelapseAllFailed(table1.getRelapseAllFailed() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDFailed(table1.getRelapsePulmonaryCDFailed() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table1.setRelapseAllDefaulted(table1.getRelapseAllDefaulted() + 1);
    	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
    	    						table1.setRelapsePulmonaryCDDefaulted(table1.getRelapsePulmonaryCDDefaulted() + 1);
    	    						table1.setRelapsePulmonaryCDEligible(table1.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table1.setRelapseAllTransferOut(table1.getRelapseAllTransferOut() + 1);
    	    						table1.setRelapsePulmonaryCDTransferOut(table1.getRelapsePulmonaryCDTransferOut() + 1);

    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table1.setRelapseAllCanceled(table1.getRelapseAllCanceled() + 1);
    	    						table1.setRelapsePulmonaryCDCanceled(table1.getRelapsePulmonaryCDCanceled() + 1);
    	    						
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table1.setRelapseAllSLD(table1.getRelapseAllSLD() + 1);
    	    						table1.setRelapsePulmonaryCDSLD(table1.getRelapsePulmonaryCDSLD() + 1);
    	    						
    	    					}
    	    				}
    	    			}
    	    		}
    	    		
    	    		//EP
    	    		else if(pulmonary!=null && !pulmonary) {
    	    			
    	    			table1.setRelapseExtrapulmonaryDetected(table1.getRelapseExtrapulmonaryDetected() + 1);
	    				
	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
	    					
	    					table1.setRelapseExtrapulmonaryDetected04(table1.getRelapseExtrapulmonaryDetected04() + 1);
	    					
	    					if(cured!=null && cured) {
	    						table1.setRelapseAllCured(table1.getRelapseAllCured() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryCured04(table1.getRelapseExtrapulmonaryCured04() + 1);
	    						table1.setRelapseExtrapulmonaryEligible04(table1.getRelapseExtrapulmonaryEligible04() + 1);
	    						table1.setRelapseExtrapulmonaryCured(table1.getRelapseExtrapulmonaryCured() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table1.setRelapseAllCompleted(table1.getRelapseAllCompleted() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryCompleted04(table1.getRelapseExtrapulmonaryCompleted04() + 1);
	    						table1.setRelapseExtrapulmonaryEligible04(table1.getRelapseExtrapulmonaryEligible04() + 1);
	    						table1.setRelapseExtrapulmonaryCompleted(table1.getRelapseExtrapulmonaryCompleted() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table1.setRelapseAllDiedTB(table1.getRelapseAllDiedTB() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryDiedTB04(table1.getRelapseExtrapulmonaryDiedTB04() + 1);
	    						table1.setRelapseExtrapulmonaryEligible04(table1.getRelapseExtrapulmonaryEligible04() + 1);
	    						table1.setRelapseExtrapulmonaryDiedTB(table1.getRelapseExtrapulmonaryDiedTB() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table1.setRelapseAllDiedNotTB(table1.getRelapseAllDiedNotTB() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryDiedNotTB04(table1.getRelapseExtrapulmonaryDiedNotTB04() + 1);
	    						table1.setRelapseExtrapulmonaryEligible04(table1.getRelapseExtrapulmonaryEligible04() + 1);
	    						table1.setRelapseExtrapulmonaryDiedNotTB(table1.getRelapseExtrapulmonaryDiedNotTB() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table1.setRelapseAllFailed(table1.getRelapseAllFailed() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryFailed04(table1.getRelapseExtrapulmonaryFailed04() + 1);
	    						table1.setRelapseExtrapulmonaryEligible04(table1.getRelapseExtrapulmonaryEligible04() + 1);
	    						table1.setRelapseExtrapulmonaryFailed(table1.getRelapseExtrapulmonaryFailed() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table1.setRelapseAllDefaulted(table1.getRelapseAllDefaulted() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryDefaulted04(table1.getRelapseExtrapulmonaryDefaulted04() + 1);
	    						table1.setRelapseExtrapulmonaryEligible04(table1.getRelapseExtrapulmonaryEligible04() + 1);
	    						table1.setRelapseExtrapulmonaryDefaulted(table1.getRelapseExtrapulmonaryDefaulted() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table1.setRelapseAllTransferOut(table1.getRelapseAllTransferOut() + 1);
	    						table1.setRelapseExtrapulmonaryTransferOut04(table1.getRelapseExtrapulmonaryTransferOut04() + 1);
	    						table1.setRelapseExtrapulmonaryTransferOut(table1.getRelapseExtrapulmonaryTransferOut() + 1);
	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table1.setRelapseAllCanceled(table1.getRelapseAllCanceled() + 1);
	    						table1.setRelapseExtrapulmonaryCanceled04(table1.getRelapseExtrapulmonaryCanceled04() + 1);
	    						table1.setRelapseExtrapulmonaryCanceled(table1.getRelapseExtrapulmonaryCanceled() + 1);
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table1.setRelapseAllSLD(table1.getRelapseAllSLD() + 1);
	    						table1.setRelapseExtrapulmonarySLD04(table1.getRelapseExtrapulmonarySLD04() + 1);
	    						table1.setRelapseExtrapulmonarySLD(table1.getRelapseExtrapulmonarySLD() + 1);
	    					}
	    					
	    					

	    				}
	    				
	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
	    					
	    					table1.setRelapseExtrapulmonaryDetected0514(table1.getRelapseExtrapulmonaryDetected0514() + 1);
	    					
	    					if(cured!=null && cured) {
	    						table1.setRelapseAllCured(table1.getRelapseAllCured() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryCured0514(table1.getRelapseExtrapulmonaryCured0514() + 1);
	    						table1.setRelapseExtrapulmonaryEligible0514(table1.getRelapseExtrapulmonaryEligible0514() + 1);
	    						table1.setRelapseExtrapulmonaryCured(table1.getRelapseExtrapulmonaryCured() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table1.setRelapseAllCompleted(table1.getRelapseAllCompleted() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryCompleted0514(table1.getRelapseExtrapulmonaryCompleted0514() + 1);
	    						table1.setRelapseExtrapulmonaryEligible0514(table1.getRelapseExtrapulmonaryEligible0514() + 1);
	    						table1.setRelapseExtrapulmonaryCompleted(table1.getRelapseExtrapulmonaryCompleted() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table1.setRelapseAllDiedTB(table1.getRelapseAllDiedTB() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryDiedTB0514(table1.getRelapseExtrapulmonaryDiedTB0514() + 1);
	    						table1.setRelapseExtrapulmonaryEligible0514(table1.getRelapseExtrapulmonaryEligible0514() + 1);
	    						table1.setRelapseExtrapulmonaryDiedTB(table1.getRelapseExtrapulmonaryDiedTB() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table1.setRelapseAllDiedNotTB(table1.getRelapseAllDiedNotTB() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryDiedNotTB0514(table1.getRelapseExtrapulmonaryDiedNotTB0514() + 1);
	    						table1.setRelapseExtrapulmonaryEligible0514(table1.getRelapseExtrapulmonaryEligible0514() + 1);
	    						table1.setRelapseExtrapulmonaryDiedNotTB(table1.getRelapseExtrapulmonaryDiedNotTB() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table1.setRelapseAllFailed(table1.getRelapseAllFailed() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryFailed0514(table1.getRelapseExtrapulmonaryFailed0514() + 1);
	    						table1.setRelapseExtrapulmonaryEligible0514(table1.getRelapseExtrapulmonaryEligible0514() + 1);
	    						table1.setRelapseExtrapulmonaryFailed(table1.getRelapseExtrapulmonaryFailed() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table1.setRelapseAllDefaulted(table1.getRelapseAllDefaulted() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryDefaulted0514(table1.getRelapseExtrapulmonaryDefaulted0514() + 1);
	    						table1.setRelapseExtrapulmonaryEligible0514(table1.getRelapseExtrapulmonaryEligible0514() + 1);
	    						table1.setRelapseExtrapulmonaryDefaulted(table1.getRelapseExtrapulmonaryDefaulted() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table1.setRelapseAllTransferOut(table1.getRelapseAllTransferOut() + 1);
	    						table1.setRelapseExtrapulmonaryTransferOut0514(table1.getRelapseExtrapulmonaryTransferOut0514() + 1);
	    						table1.setRelapseExtrapulmonaryTransferOut(table1.getRelapseExtrapulmonaryTransferOut() + 1);
	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table1.setRelapseAllCanceled(table1.getRelapseAllCanceled() + 1);
	    						table1.setRelapseExtrapulmonaryCanceled0514(table1.getRelapseExtrapulmonaryCanceled0514() + 1);
	    						table1.setRelapseExtrapulmonaryCanceled(table1.getRelapseExtrapulmonaryCanceled() + 1);
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table1.setRelapseAllSLD(table1.getRelapseAllSLD() + 1);
	    						table1.setRelapseExtrapulmonarySLD0514(table1.getRelapseExtrapulmonarySLD0514() + 1);
	    						table1.setRelapseExtrapulmonarySLD(table1.getRelapseExtrapulmonarySLD() + 1);
	    					}
	    					
	    				}
	    				
	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
	    					
	    					table1.setRelapseExtrapulmonaryDetected1517(table1.getRelapseExtrapulmonaryDetected1517() + 1);
	    					
	    					if(cured!=null && cured) {
	    						table1.setRelapseAllCured(table1.getRelapseAllCured() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryCured1517(table1.getRelapseExtrapulmonaryCured1517() + 1);
	    						table1.setRelapseExtrapulmonaryEligible1517(table1.getRelapseExtrapulmonaryEligible1517() + 1);
	    						table1.setRelapseExtrapulmonaryCured(table1.getRelapseExtrapulmonaryCured() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table1.setRelapseAllCompleted(table1.getRelapseAllCompleted() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryCompleted1517(table1.getRelapseExtrapulmonaryCompleted1517() + 1);
	    						table1.setRelapseExtrapulmonaryEligible1517(table1.getRelapseExtrapulmonaryEligible1517() + 1);
	    						table1.setRelapseExtrapulmonaryCompleted(table1.getRelapseExtrapulmonaryCompleted() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table1.setRelapseAllDiedTB(table1.getRelapseAllDiedTB() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryDiedTB1517(table1.getRelapseExtrapulmonaryDiedTB1517() + 1);
	    						table1.setRelapseExtrapulmonaryEligible1517(table1.getRelapseExtrapulmonaryEligible1517() + 1);
	    						table1.setRelapseExtrapulmonaryDiedTB(table1.getRelapseExtrapulmonaryDiedTB() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table1.setRelapseAllDiedNotTB(table1.getRelapseAllDiedNotTB() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryDiedNotTB1517(table1.getRelapseExtrapulmonaryDiedNotTB1517() + 1);
	    						table1.setRelapseExtrapulmonaryEligible1517(table1.getRelapseExtrapulmonaryEligible1517() + 1);
	    						table1.setRelapseExtrapulmonaryDiedNotTB(table1.getRelapseExtrapulmonaryDiedNotTB() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table1.setRelapseAllFailed(table1.getRelapseAllFailed() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryFailed1517(table1.getRelapseExtrapulmonaryFailed1517() + 1);
	    						table1.setRelapseExtrapulmonaryEligible1517(table1.getRelapseExtrapulmonaryEligible1517() + 1);
	    						table1.setRelapseExtrapulmonaryFailed(table1.getRelapseExtrapulmonaryFailed() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table1.setRelapseAllDefaulted(table1.getRelapseAllDefaulted() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryDefaulted1517(table1.getRelapseExtrapulmonaryDefaulted1517() + 1);
	    						table1.setRelapseExtrapulmonaryEligible1517(table1.getRelapseExtrapulmonaryEligible1517() + 1);
	    						table1.setRelapseExtrapulmonaryDefaulted(table1.getRelapseExtrapulmonaryDefaulted() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table1.setRelapseAllTransferOut(table1.getRelapseAllTransferOut() + 1);
	    						table1.setRelapseExtrapulmonaryTransferOut1517(table1.getRelapseExtrapulmonaryTransferOut1517() + 1);
	    						table1.setRelapseExtrapulmonaryTransferOut(table1.getRelapseExtrapulmonaryTransferOut() + 1);
	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table1.setRelapseAllCanceled(table1.getRelapseAllCanceled() + 1);
	    						table1.setRelapseExtrapulmonaryCanceled1517(table1.getRelapseExtrapulmonaryCanceled1517() + 1);
	    						table1.setRelapseExtrapulmonaryCanceled(table1.getRelapseExtrapulmonaryCanceled() + 1);
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table1.setRelapseAllSLD(table1.getRelapseAllSLD() + 1);
	    						table1.setRelapseExtrapulmonarySLD1517(table1.getRelapseExtrapulmonarySLD1517() + 1);
	    						table1.setRelapseExtrapulmonarySLD(table1.getRelapseExtrapulmonarySLD() + 1);
	    					}
	    					
	    				}
	    				
	    				else {
	    					
	    					if(cured!=null && cured) {
	    						table1.setRelapseAllCured(table1.getRelapseAllCured() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryCured(table1.getRelapseExtrapulmonaryCured() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table1.setRelapseAllCompleted(table1.getRelapseAllCompleted() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryCompleted(table1.getRelapseExtrapulmonaryCompleted() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table1.setRelapseAllDiedTB(table1.getRelapseAllDiedTB() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryDiedTB(table1.getRelapseExtrapulmonaryDiedTB() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table1.setRelapseAllDiedNotTB(table1.getRelapseAllDiedNotTB() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryDiedNotTB(table1.getRelapseExtrapulmonaryDiedNotTB() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table1.setRelapseAllFailed(table1.getRelapseAllFailed() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryFailed(table1.getRelapseExtrapulmonaryFailed() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table1.setRelapseAllDefaulted(table1.getRelapseAllDefaulted() + 1);
	    						table1.setRelapseAllEligible(table1.getRelapseAllEligible() + 1);
	    						table1.setRelapseExtrapulmonaryDefaulted(table1.getRelapseExtrapulmonaryDefaulted() + 1);
	    						table1.setRelapseExtrapulmonaryEligible(table1.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table1.setRelapseAllTransferOut(table1.getRelapseAllTransferOut() + 1);
	    						table1.setRelapseExtrapulmonaryTransferOut(table1.getRelapseExtrapulmonaryTransferOut() + 1);

	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table1.setRelapseAllCanceled(table1.getRelapseAllCanceled() + 1);
	    						table1.setRelapseExtrapulmonaryCanceled(table1.getRelapseExtrapulmonaryCanceled() + 1);
	    						
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table1.setRelapseAllSLD(table1.getRelapseAllSLD() + 1);
	    						table1.setRelapseExtrapulmonarySLD(table1.getRelapseExtrapulmonarySLD() + 1);
	    						
	    					}
	    				}
	    			
    	    		}
    	    	} 	
    	    	
    	    	//FAILURE
    	    	else if(q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterFailure1.conceptId")) || q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterFailure2.conceptId"))) {
    	    		table1.setFailureAllDetected(table1.getFailureAllDetected() + 1);
    	    		
    	    		//P
    	    		if(pulmonary!=null && pulmonary) {
    	    			
    	    			//BC
    	    			if(bacPositive) {
    	    				
    	    				table1.setFailurePulmonaryBCDetected(table1.getFailurePulmonaryBCDetected() + 1);
    	    			
							if (cured != null && cured) {
								table1.setFailureAllCured(table1
										.getFailureAllCured() + 1);
								table1.setFailureAllEligible(table1
										.getFailureAllEligible() + 1);
								table1.setFailurePulmonaryBCCured(table1
										.getFailurePulmonaryBCCured() + 1);
								table1.setFailurePulmonaryBCEligible(table1
										.getFailurePulmonaryBCEligible() + 1);
							}

							else if (txCompleted != null && txCompleted) {
								table1.setFailureAllCompleted(table1
										.getFailureAllCompleted() + 1);
								table1.setFailureAllEligible(table1
										.getFailureAllEligible() + 1);
								table1.setFailurePulmonaryBCCompleted(table1
										.getFailurePulmonaryBCCompleted() + 1);
								table1.setFailurePulmonaryBCEligible(table1
										.getFailurePulmonaryBCEligible() + 1);
							}

							else if (diedTB != null && diedTB) {
								table1.setFailureAllDiedTB(table1
										.getFailureAllDiedTB() + 1);
								table1.setFailureAllEligible(table1
										.getFailureAllEligible() + 1);
								table1.setFailurePulmonaryBCDiedTB(table1
										.getFailurePulmonaryBCDiedTB() + 1);
								table1.setFailurePulmonaryBCEligible(table1
										.getFailurePulmonaryBCEligible() + 1);
							}

							else if (diedNotTB != null && diedNotTB) {
								table1.setFailureAllDiedNotTB(table1
										.getFailureAllDiedNotTB() + 1);
								table1.setFailureAllEligible(table1
										.getFailureAllEligible() + 1);
								table1.setFailurePulmonaryBCDiedNotTB(table1
										.getFailurePulmonaryBCDiedNotTB() + 1);
								table1.setFailurePulmonaryBCEligible(table1
										.getFailurePulmonaryBCEligible() + 1);
							}

							else if (failed != null && failed) {
								table1.setFailureAllFailed(table1
										.getFailureAllFailed() + 1);
								table1.setFailureAllEligible(table1
										.getFailureAllEligible() + 1);
								table1.setFailurePulmonaryBCFailed(table1
										.getFailurePulmonaryBCFailed() + 1);
								table1.setFailurePulmonaryBCEligible(table1
										.getFailurePulmonaryBCEligible() + 1);
							}

							else if (defaulted != null && defaulted) {
								table1.setFailureAllDefaulted(table1
										.getFailureAllDefaulted() + 1);
								table1.setFailureAllEligible(table1
										.getFailureAllEligible() + 1);
								table1.setFailurePulmonaryBCDefaulted(table1
										.getFailurePulmonaryBCDefaulted() + 1);
								table1.setFailurePulmonaryBCEligible(table1
										.getFailurePulmonaryBCEligible() + 1);
							}

							else if (transferOut != null && transferOut) {
								table1.setFailureAllTransferOut(table1
										.getFailureAllTransferOut() + 1);
								table1.setFailurePulmonaryBCTransferOut(table1
										.getFailurePulmonaryBCTransferOut() + 1);

							}

							else if (canceled != null && canceled) {
								table1.setFailureAllCanceled(table1
										.getFailureAllCanceled() + 1);
								table1.setFailurePulmonaryBCCanceled(table1
										.getFailurePulmonaryBCCanceled() + 1);

							}

							else if (sld != null && sld) {
								table1.setFailureAllSLD(table1
										.getFailureAllSLD() + 1);
								table1.setFailurePulmonaryBCSLD(table1
										.getFailurePulmonaryBCSLD() + 1);

							}
						}
    	    			
    	    			
    	    			//CD
    	    			else {
    	    				
    	    				table1.setFailurePulmonaryCDDetected(table1.getFailurePulmonaryCDDetected() + 1);

							if (cured != null && cured) {
								table1.setFailureAllCured(table1
										.getFailureAllCured() + 1);
								table1.setFailureAllEligible(table1
										.getFailureAllEligible() + 1);
								table1.setFailurePulmonaryCDCured(table1
										.getFailurePulmonaryCDCured() + 1);
								table1.setFailurePulmonaryCDEligible(table1
										.getFailurePulmonaryCDEligible() + 1);
							}

							else if (txCompleted != null && txCompleted) {
								table1.setFailureAllCompleted(table1
										.getFailureAllCompleted() + 1);
								table1.setFailureAllEligible(table1
										.getFailureAllEligible() + 1);
								table1.setFailurePulmonaryCDCompleted(table1
										.getFailurePulmonaryCDCompleted() + 1);
								table1.setFailurePulmonaryCDEligible(table1
										.getFailurePulmonaryCDEligible() + 1);
							}

							else if (diedTB != null && diedTB) {
								table1.setFailureAllDiedTB(table1
										.getFailureAllDiedTB() + 1);
								table1.setFailureAllEligible(table1
										.getFailureAllEligible() + 1);
								table1.setFailurePulmonaryCDDiedTB(table1
										.getFailurePulmonaryCDDiedTB() + 1);
								table1.setFailurePulmonaryCDEligible(table1
										.getFailurePulmonaryCDEligible() + 1);
							}

							else if (diedNotTB != null && diedNotTB) {
								table1.setFailureAllDiedNotTB(table1
										.getFailureAllDiedNotTB() + 1);
								table1.setFailureAllEligible(table1
										.getFailureAllEligible() + 1);
								table1.setFailurePulmonaryCDDiedNotTB(table1
										.getFailurePulmonaryCDDiedNotTB() + 1);
								table1.setFailurePulmonaryCDEligible(table1
										.getFailurePulmonaryCDEligible() + 1);
							}

							else if (failed != null && failed) {
								table1.setFailureAllFailed(table1
										.getFailureAllFailed() + 1);
								table1.setFailureAllEligible(table1
										.getFailureAllEligible() + 1);
								table1.setFailurePulmonaryCDFailed(table1
										.getFailurePulmonaryCDFailed() + 1);
								table1.setFailurePulmonaryCDEligible(table1
										.getFailurePulmonaryCDEligible() + 1);
							}

							else if (defaulted != null && defaulted) {
								table1.setFailureAllDefaulted(table1
										.getFailureAllDefaulted() + 1);
								table1.setFailureAllEligible(table1
										.getFailureAllEligible() + 1);
								table1.setFailurePulmonaryCDDefaulted(table1
										.getFailurePulmonaryCDDefaulted() + 1);
								table1.setFailurePulmonaryCDEligible(table1
										.getFailurePulmonaryCDEligible() + 1);
							}

							else if (transferOut != null && transferOut) {
								table1.setFailureAllTransferOut(table1
										.getFailureAllTransferOut() + 1);
								table1.setFailurePulmonaryCDTransferOut(table1
										.getFailurePulmonaryCDTransferOut() + 1);

							}

							else if (canceled != null && canceled) {
								table1.setFailureAllCanceled(table1
										.getFailureAllCanceled() + 1);
								table1.setFailurePulmonaryCDCanceled(table1
										.getFailurePulmonaryCDCanceled() + 1);

							}

							else if (sld != null && sld) {
								table1.setFailureAllSLD(table1
										.getFailureAllSLD() + 1);
								table1.setFailurePulmonaryCDSLD(table1
										.getFailurePulmonaryCDSLD() + 1);

							}

						}
    	    		}
    	    		
    	    		//EP
    	    		else if(pulmonary!=null && !pulmonary) {
    	    			
						table1.setFailureExtrapulmonaryDetected(table1
								.getFailureExtrapulmonaryDetected() + 1);

						if (cured != null && cured) {
							table1.setFailureAllCured(table1
									.getFailureAllCured() + 1);
							table1.setFailureAllEligible(table1
									.getFailureAllEligible() + 1);
							table1.setFailureExtrapulmonaryCured(table1
									.getFailureExtrapulmonaryCured() + 1);
							table1.setFailureExtrapulmonaryEligible(table1
									.getFailureExtrapulmonaryEligible() + 1);
						}

						else if (txCompleted != null && txCompleted) {
							table1.setFailureAllCompleted(table1
									.getFailureAllCompleted() + 1);
							table1.setFailureAllEligible(table1
									.getFailureAllEligible() + 1);
							table1.setFailureExtrapulmonaryCompleted(table1
									.getFailureExtrapulmonaryCompleted() + 1);
							table1.setFailureExtrapulmonaryEligible(table1
									.getFailureExtrapulmonaryEligible() + 1);
						}

						else if (diedTB != null && diedTB) {
							table1.setFailureAllDiedTB(table1
									.getFailureAllDiedTB() + 1);
							table1.setFailureAllEligible(table1
									.getFailureAllEligible() + 1);
							table1.setFailureExtrapulmonaryDiedTB(table1
									.getFailureExtrapulmonaryDiedTB() + 1);
							table1.setFailureExtrapulmonaryEligible(table1
									.getFailureExtrapulmonaryEligible() + 1);
						}

						else if (diedNotTB != null && diedNotTB) {
							table1.setFailureAllDiedNotTB(table1
									.getFailureAllDiedNotTB() + 1);
							table1.setFailureAllEligible(table1
									.getFailureAllEligible() + 1);
							table1.setFailureExtrapulmonaryDiedNotTB(table1
									.getFailureExtrapulmonaryDiedNotTB() + 1);
							table1.setFailureExtrapulmonaryEligible(table1
									.getFailureExtrapulmonaryEligible() + 1);
						}

						else if (failed != null && failed) {
							table1.setFailureAllFailed(table1
									.getFailureAllFailed() + 1);
							table1.setFailureAllEligible(table1
									.getFailureAllEligible() + 1);
							table1.setFailureExtrapulmonaryFailed(table1
									.getFailureExtrapulmonaryFailed() + 1);
							table1.setFailureExtrapulmonaryEligible(table1
									.getFailureExtrapulmonaryEligible() + 1);
						}

						else if (defaulted != null && defaulted) {
							table1.setFailureAllDefaulted(table1
									.getFailureAllDefaulted() + 1);
							table1.setFailureAllEligible(table1
									.getFailureAllEligible() + 1);
							table1.setFailureExtrapulmonaryDefaulted(table1
									.getFailureExtrapulmonaryDefaulted() + 1);
							table1.setFailureExtrapulmonaryEligible(table1
									.getFailureExtrapulmonaryEligible() + 1);
						}

						else if (transferOut != null && transferOut) {
							table1.setFailureAllTransferOut(table1
									.getFailureAllTransferOut() + 1);
							table1.setFailureExtrapulmonaryTransferOut(table1
									.getFailureExtrapulmonaryTransferOut() + 1);

						}

						else if (canceled != null && canceled) {
							table1.setFailureAllCanceled(table1
									.getFailureAllCanceled() + 1);
							table1.setFailureExtrapulmonaryCanceled(table1
									.getFailureExtrapulmonaryCanceled() + 1);

						}

						else if (sld != null && sld) {
							table1.setFailureAllSLD(table1.getFailureAllSLD() + 1);
							table1.setFailureExtrapulmonarySLD(table1
									.getFailureExtrapulmonarySLD() + 1);

						}

					}
				}
    	    	
    	    	else if(q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterDefault1.conceptId")) || q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterDefault2.conceptId"))) {
    	    		table1.setDefaultAllDetected(table1.getDefaultAllDetected() + 1);
    	    		
    	    		//P
    	    		if(pulmonary!=null && pulmonary) {
    	    			
    	    			//BC
    	    			if(bacPositive) {
    	    				
    	    				table1.setDefaultPulmonaryBCDetected(table1.getDefaultPulmonaryBCDetected() + 1);
    	    			
							if (cured != null && cured) {
								table1.setDefaultAllCured(table1
										.getDefaultAllCured() + 1);
								table1.setDefaultAllEligible(table1
										.getDefaultAllEligible() + 1);
								table1.setDefaultPulmonaryBCCured(table1
										.getDefaultPulmonaryBCCured() + 1);
								table1.setDefaultPulmonaryBCEligible(table1
										.getDefaultPulmonaryBCEligible() + 1);
							}

							else if (txCompleted != null && txCompleted) {
								table1.setDefaultAllCompleted(table1
										.getDefaultAllCompleted() + 1);
								table1.setDefaultAllEligible(table1
										.getDefaultAllEligible() + 1);
								table1.setDefaultPulmonaryBCCompleted(table1
										.getDefaultPulmonaryBCCompleted() + 1);
								table1.setDefaultPulmonaryBCEligible(table1
										.getDefaultPulmonaryBCEligible() + 1);
							}

							else if (diedTB != null && diedTB) {
								table1.setDefaultAllDiedTB(table1
										.getDefaultAllDiedTB() + 1);
								table1.setDefaultAllEligible(table1
										.getDefaultAllEligible() + 1);
								table1.setDefaultPulmonaryBCDiedTB(table1
										.getDefaultPulmonaryBCDiedTB() + 1);
								table1.setDefaultPulmonaryBCEligible(table1
										.getDefaultPulmonaryBCEligible() + 1);
							}

							else if (diedNotTB != null && diedNotTB) {
								table1.setDefaultAllDiedNotTB(table1
										.getDefaultAllDiedNotTB() + 1);
								table1.setDefaultAllEligible(table1
										.getDefaultAllEligible() + 1);
								table1.setDefaultPulmonaryBCDiedNotTB(table1
										.getDefaultPulmonaryBCDiedNotTB() + 1);
								table1.setDefaultPulmonaryBCEligible(table1
										.getDefaultPulmonaryBCEligible() + 1);
							}

							else if (failed != null && failed) {
								table1.setDefaultAllFailed(table1
										.getDefaultAllFailed() + 1);
								table1.setDefaultAllEligible(table1
										.getDefaultAllEligible() + 1);
								table1.setDefaultPulmonaryBCFailed(table1
										.getDefaultPulmonaryBCFailed() + 1);
								table1.setDefaultPulmonaryBCEligible(table1
										.getDefaultPulmonaryBCEligible() + 1);
							}

							else if (defaulted != null && defaulted) {
								table1.setDefaultAllDefaulted(table1
										.getDefaultAllDefaulted() + 1);
								table1.setDefaultAllEligible(table1
										.getDefaultAllEligible() + 1);
								table1.setDefaultPulmonaryBCDefaulted(table1
										.getDefaultPulmonaryBCDefaulted() + 1);
								table1.setDefaultPulmonaryBCEligible(table1
										.getDefaultPulmonaryBCEligible() + 1);
							}

							else if (transferOut != null && transferOut) {
								table1.setDefaultAllTransferOut(table1
										.getDefaultAllTransferOut() + 1);
								table1.setDefaultPulmonaryBCTransferOut(table1
										.getDefaultPulmonaryBCTransferOut() + 1);

							}

							else if (canceled != null && canceled) {
								table1.setDefaultAllCanceled(table1
										.getDefaultAllCanceled() + 1);
								table1.setDefaultPulmonaryBCCanceled(table1
										.getDefaultPulmonaryBCCanceled() + 1);

							}

							else if (sld != null && sld) {
								table1.setDefaultAllSLD(table1
										.getDefaultAllSLD() + 1);
								table1.setDefaultPulmonaryBCSLD(table1
										.getDefaultPulmonaryBCSLD() + 1);

							}
						}
    	    			
    	    			
    	    			//CD
    	    			else {
    	    				
    	    				table1.setDefaultPulmonaryCDDetected(table1.getDefaultPulmonaryCDDetected() + 1);

							if (cured != null && cured) {
								table1.setDefaultAllCured(table1
										.getDefaultAllCured() + 1);
								table1.setDefaultAllEligible(table1
										.getDefaultAllEligible() + 1);
								table1.setDefaultPulmonaryCDCured(table1
										.getDefaultPulmonaryCDCured() + 1);
								table1.setDefaultPulmonaryCDEligible(table1
										.getDefaultPulmonaryCDEligible() + 1);
							}

							else if (txCompleted != null && txCompleted) {
								table1.setDefaultAllCompleted(table1
										.getDefaultAllCompleted() + 1);
								table1.setDefaultAllEligible(table1
										.getDefaultAllEligible() + 1);
								table1.setDefaultPulmonaryCDCompleted(table1
										.getDefaultPulmonaryCDCompleted() + 1);
								table1.setDefaultPulmonaryCDEligible(table1
										.getDefaultPulmonaryCDEligible() + 1);
							}

							else if (diedTB != null && diedTB) {
								table1.setDefaultAllDiedTB(table1
										.getDefaultAllDiedTB() + 1);
								table1.setDefaultAllEligible(table1
										.getDefaultAllEligible() + 1);
								table1.setDefaultPulmonaryCDDiedTB(table1
										.getDefaultPulmonaryCDDiedTB() + 1);
								table1.setDefaultPulmonaryCDEligible(table1
										.getDefaultPulmonaryCDEligible() + 1);
							}

							else if (diedNotTB != null && diedNotTB) {
								table1.setDefaultAllDiedNotTB(table1
										.getDefaultAllDiedNotTB() + 1);
								table1.setDefaultAllEligible(table1
										.getDefaultAllEligible() + 1);
								table1.setDefaultPulmonaryCDDiedNotTB(table1
										.getDefaultPulmonaryCDDiedNotTB() + 1);
								table1.setDefaultPulmonaryCDEligible(table1
										.getDefaultPulmonaryCDEligible() + 1);
							}

							else if (failed != null && failed) {
								table1.setDefaultAllFailed(table1
										.getDefaultAllFailed() + 1);
								table1.setDefaultAllEligible(table1
										.getDefaultAllEligible() + 1);
								table1.setDefaultPulmonaryCDFailed(table1
										.getDefaultPulmonaryCDFailed() + 1);
								table1.setDefaultPulmonaryCDEligible(table1
										.getDefaultPulmonaryCDEligible() + 1);
							}

							else if (defaulted != null && defaulted) {
								table1.setDefaultAllDefaulted(table1
										.getDefaultAllDefaulted() + 1);
								table1.setDefaultAllEligible(table1
										.getDefaultAllEligible() + 1);
								table1.setDefaultPulmonaryCDDefaulted(table1
										.getDefaultPulmonaryCDDefaulted() + 1);
								table1.setDefaultPulmonaryCDEligible(table1
										.getDefaultPulmonaryCDEligible() + 1);
							}

							else if (transferOut != null && transferOut) {
								table1.setDefaultAllTransferOut(table1
										.getDefaultAllTransferOut() + 1);
								table1.setDefaultPulmonaryCDTransferOut(table1
										.getDefaultPulmonaryCDTransferOut() + 1);

							}

							else if (canceled != null && canceled) {
								table1.setDefaultAllCanceled(table1
										.getDefaultAllCanceled() + 1);
								table1.setDefaultPulmonaryCDCanceled(table1
										.getDefaultPulmonaryCDCanceled() + 1);

							}

							else if (sld != null && sld) {
								table1.setDefaultAllSLD(table1
										.getDefaultAllSLD() + 1);
								table1.setDefaultPulmonaryCDSLD(table1
										.getDefaultPulmonaryCDSLD() + 1);

							}

						}
    	    		}
    	    		
    	    		//EP
    	    		else if(pulmonary!=null && !pulmonary) {
    	    			
						table1.setDefaultExtrapulmonaryDetected(table1
								.getDefaultExtrapulmonaryDetected() + 1);

						if (cured != null && cured) {
							table1.setDefaultAllCured(table1
									.getDefaultAllCured() + 1);
							table1.setDefaultAllEligible(table1
									.getDefaultAllEligible() + 1);
							table1.setDefaultExtrapulmonaryCured(table1
									.getDefaultExtrapulmonaryCured() + 1);
							table1.setDefaultExtrapulmonaryEligible(table1
									.getDefaultExtrapulmonaryEligible() + 1);
						}

						else if (txCompleted != null && txCompleted) {
							table1.setDefaultAllCompleted(table1
									.getDefaultAllCompleted() + 1);
							table1.setDefaultAllEligible(table1
									.getDefaultAllEligible() + 1);
							table1.setDefaultExtrapulmonaryCompleted(table1
									.getDefaultExtrapulmonaryCompleted() + 1);
							table1.setDefaultExtrapulmonaryEligible(table1
									.getDefaultExtrapulmonaryEligible() + 1);
						}

						else if (diedTB != null && diedTB) {
							table1.setDefaultAllDiedTB(table1
									.getDefaultAllDiedTB() + 1);
							table1.setDefaultAllEligible(table1
									.getDefaultAllEligible() + 1);
							table1.setDefaultExtrapulmonaryDiedTB(table1
									.getDefaultExtrapulmonaryDiedTB() + 1);
							table1.setDefaultExtrapulmonaryEligible(table1
									.getDefaultExtrapulmonaryEligible() + 1);
						}

						else if (diedNotTB != null && diedNotTB) {
							table1.setDefaultAllDiedNotTB(table1
									.getDefaultAllDiedNotTB() + 1);
							table1.setDefaultAllEligible(table1
									.getDefaultAllEligible() + 1);
							table1.setDefaultExtrapulmonaryDiedNotTB(table1
									.getDefaultExtrapulmonaryDiedNotTB() + 1);
							table1.setDefaultExtrapulmonaryEligible(table1
									.getDefaultExtrapulmonaryEligible() + 1);
						}

						else if (failed != null && failed) {
							table1.setDefaultAllFailed(table1
									.getDefaultAllFailed() + 1);
							table1.setDefaultAllEligible(table1
									.getDefaultAllEligible() + 1);
							table1.setDefaultExtrapulmonaryFailed(table1
									.getDefaultExtrapulmonaryFailed() + 1);
							table1.setDefaultExtrapulmonaryEligible(table1
									.getDefaultExtrapulmonaryEligible() + 1);
						}

						else if (defaulted != null && defaulted) {
							table1.setDefaultAllDefaulted(table1
									.getDefaultAllDefaulted() + 1);
							table1.setDefaultAllEligible(table1
									.getDefaultAllEligible() + 1);
							table1.setDefaultExtrapulmonaryDefaulted(table1
									.getDefaultExtrapulmonaryDefaulted() + 1);
							table1.setDefaultExtrapulmonaryEligible(table1
									.getDefaultExtrapulmonaryEligible() + 1);
						}

						else if (transferOut != null && transferOut) {
							table1.setDefaultAllTransferOut(table1
									.getDefaultAllTransferOut() + 1);
							table1.setDefaultExtrapulmonaryTransferOut(table1
									.getDefaultExtrapulmonaryTransferOut() + 1);

						}

						else if (canceled != null && canceled) {
							table1.setDefaultAllCanceled(table1
									.getDefaultAllCanceled() + 1);
							table1.setDefaultExtrapulmonaryCanceled(table1
									.getDefaultExtrapulmonaryCanceled() + 1);

						}

						else if (sld != null && sld) {
							table1.setDefaultAllSLD(table1.getDefaultAllSLD() + 1);
							table1.setDefaultExtrapulmonarySLD(table1
									.getDefaultExtrapulmonarySLD() + 1);

						}

					}
			
    	    	}
    	    	
    	    	//OTHER
    	    	else if (q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.other.conceptId"))) {
    	    		table1.setOtherAllDetected(table1.getOtherAllDetected() + 1);
    	    		
    	    		//P
    	    		if(pulmonary!=null && pulmonary) {
    	    			
    	    			//BC
    	    			if(bacPositive) {
    	    				
    	    				table1.setOtherPulmonaryBCDetected(table1.getOtherPulmonaryBCDetected() + 1);
    	    			
							if (cured != null && cured) {
								table1.setOtherAllCured(table1
										.getOtherAllCured() + 1);
								table1.setOtherAllEligible(table1
										.getOtherAllEligible() + 1);
								table1.setOtherPulmonaryBCCured(table1
										.getOtherPulmonaryBCCured() + 1);
								table1.setOtherPulmonaryBCEligible(table1
										.getOtherPulmonaryBCEligible() + 1);
							}

							else if (txCompleted != null && txCompleted) {
								table1.setOtherAllCompleted(table1
										.getOtherAllCompleted() + 1);
								table1.setOtherAllEligible(table1
										.getOtherAllEligible() + 1);
								table1.setOtherPulmonaryBCCompleted(table1
										.getOtherPulmonaryBCCompleted() + 1);
								table1.setOtherPulmonaryBCEligible(table1
										.getOtherPulmonaryBCEligible() + 1);
							}

							else if (diedTB != null && diedTB) {
								table1.setOtherAllDiedTB(table1
										.getOtherAllDiedTB() + 1);
								table1.setOtherAllEligible(table1
										.getOtherAllEligible() + 1);
								table1.setOtherPulmonaryBCDiedTB(table1
										.getOtherPulmonaryBCDiedTB() + 1);
								table1.setOtherPulmonaryBCEligible(table1
										.getOtherPulmonaryBCEligible() + 1);
							}

							else if (diedNotTB != null && diedNotTB) {
								table1.setOtherAllDiedNotTB(table1
										.getOtherAllDiedNotTB() + 1);
								table1.setOtherAllEligible(table1
										.getOtherAllEligible() + 1);
								table1.setOtherPulmonaryBCDiedNotTB(table1
										.getOtherPulmonaryBCDiedNotTB() + 1);
								table1.setOtherPulmonaryBCEligible(table1
										.getOtherPulmonaryBCEligible() + 1);
							}

							else if (failed != null && failed) {
								table1.setOtherAllFailed(table1
										.getOtherAllFailed() + 1);
								table1.setOtherAllEligible(table1
										.getOtherAllEligible() + 1);
								table1.setOtherPulmonaryBCFailed(table1
										.getOtherPulmonaryBCFailed() + 1);
								table1.setOtherPulmonaryBCEligible(table1
										.getOtherPulmonaryBCEligible() + 1);
							}

							else if (defaulted != null && defaulted) {
								table1.setOtherAllDefaulted(table1
										.getOtherAllDefaulted() + 1);
								table1.setOtherAllEligible(table1
										.getOtherAllEligible() + 1);
								table1.setOtherPulmonaryBCDefaulted(table1
										.getOtherPulmonaryBCDefaulted() + 1);
								table1.setOtherPulmonaryBCEligible(table1
										.getOtherPulmonaryBCEligible() + 1);
							}

							else if (transferOut != null && transferOut) {
								table1.setOtherAllTransferOut(table1
										.getOtherAllTransferOut() + 1);
								table1.setOtherPulmonaryBCTransferOut(table1
										.getOtherPulmonaryBCTransferOut() + 1);

							}

							else if (canceled != null && canceled) {
								table1.setOtherAllCanceled(table1
										.getOtherAllCanceled() + 1);
								table1.setOtherPulmonaryBCCanceled(table1
										.getOtherPulmonaryBCCanceled() + 1);

							}

							else if (sld != null && sld) {
								table1.setOtherAllSLD(table1
										.getOtherAllSLD() + 1);
								table1.setOtherPulmonaryBCSLD(table1
										.getOtherPulmonaryBCSLD() + 1);

							}
						}
    	    			
    	    			
    	    			//CD
    	    			else {
    	    				
    	    				table1.setOtherPulmonaryCDDetected(table1.getOtherPulmonaryCDDetected() + 1);

							if (cured != null && cured) {
								table1.setOtherAllCured(table1
										.getOtherAllCured() + 1);
								table1.setOtherAllEligible(table1
										.getOtherAllEligible() + 1);
								table1.setOtherPulmonaryCDCured(table1
										.getOtherPulmonaryCDCured() + 1);
								table1.setOtherPulmonaryCDEligible(table1
										.getOtherPulmonaryCDEligible() + 1);
							}

							else if (txCompleted != null && txCompleted) {
								table1.setOtherAllCompleted(table1
										.getOtherAllCompleted() + 1);
								table1.setOtherAllEligible(table1
										.getOtherAllEligible() + 1);
								table1.setOtherPulmonaryCDCompleted(table1
										.getOtherPulmonaryCDCompleted() + 1);
								table1.setOtherPulmonaryCDEligible(table1
										.getOtherPulmonaryCDEligible() + 1);
							}

							else if (diedTB != null && diedTB) {
								table1.setOtherAllDiedTB(table1
										.getOtherAllDiedTB() + 1);
								table1.setOtherAllEligible(table1
										.getOtherAllEligible() + 1);
								table1.setOtherPulmonaryCDDiedTB(table1
										.getOtherPulmonaryCDDiedTB() + 1);
								table1.setOtherPulmonaryCDEligible(table1
										.getOtherPulmonaryCDEligible() + 1);
							}

							else if (diedNotTB != null && diedNotTB) {
								table1.setOtherAllDiedNotTB(table1
										.getOtherAllDiedNotTB() + 1);
								table1.setOtherAllEligible(table1
										.getOtherAllEligible() + 1);
								table1.setOtherPulmonaryCDDiedNotTB(table1
										.getOtherPulmonaryCDDiedNotTB() + 1);
								table1.setOtherPulmonaryCDEligible(table1
										.getOtherPulmonaryCDEligible() + 1);
							}

							else if (failed != null && failed) {
								table1.setOtherAllFailed(table1
										.getOtherAllFailed() + 1);
								table1.setOtherAllEligible(table1
										.getOtherAllEligible() + 1);
								table1.setOtherPulmonaryCDFailed(table1
										.getOtherPulmonaryCDFailed() + 1);
								table1.setOtherPulmonaryCDEligible(table1
										.getOtherPulmonaryCDEligible() + 1);
							}

							else if (defaulted != null && defaulted) {
								table1.setOtherAllDefaulted(table1
										.getOtherAllDefaulted() + 1);
								table1.setOtherAllEligible(table1
										.getOtherAllEligible() + 1);
								table1.setOtherPulmonaryCDDefaulted(table1
										.getOtherPulmonaryCDDefaulted() + 1);
								table1.setOtherPulmonaryCDEligible(table1
										.getOtherPulmonaryCDEligible() + 1);
							}

							else if (transferOut != null && transferOut) {
								table1.setOtherAllTransferOut(table1
										.getOtherAllTransferOut() + 1);
								table1.setOtherPulmonaryCDTransferOut(table1
										.getOtherPulmonaryCDTransferOut() + 1);

							}

							else if (canceled != null && canceled) {
								table1.setOtherAllCanceled(table1
										.getOtherAllCanceled() + 1);
								table1.setOtherPulmonaryCDCanceled(table1
										.getOtherPulmonaryCDCanceled() + 1);

							}

							else if (sld != null && sld) {
								table1.setOtherAllSLD(table1
										.getOtherAllSLD() + 1);
								table1.setOtherPulmonaryCDSLD(table1
										.getOtherPulmonaryCDSLD() + 1);

							}

						}
    	    		}
    	    		
    	    		//EP
    	    		else if(pulmonary!=null && !pulmonary) {
    	    			
						table1.setOtherExtrapulmonaryDetected(table1
								.getOtherExtrapulmonaryDetected() + 1);

						if (cured != null && cured) {
							table1.setOtherAllCured(table1
									.getOtherAllCured() + 1);
							table1.setOtherAllEligible(table1
									.getOtherAllEligible() + 1);
							table1.setOtherExtrapulmonaryCured(table1
									.getOtherExtrapulmonaryCured() + 1);
							table1.setOtherExtrapulmonaryEligible(table1
									.getOtherExtrapulmonaryEligible() + 1);
						}

						else if (txCompleted != null && txCompleted) {
							table1.setOtherAllCompleted(table1
									.getOtherAllCompleted() + 1);
							table1.setOtherAllEligible(table1
									.getOtherAllEligible() + 1);
							table1.setOtherExtrapulmonaryCompleted(table1
									.getOtherExtrapulmonaryCompleted() + 1);
							table1.setOtherExtrapulmonaryEligible(table1
									.getOtherExtrapulmonaryEligible() + 1);
						}

						else if (diedTB != null && diedTB) {
							table1.setOtherAllDiedTB(table1
									.getOtherAllDiedTB() + 1);
							table1.setOtherAllEligible(table1
									.getOtherAllEligible() + 1);
							table1.setOtherExtrapulmonaryDiedTB(table1
									.getOtherExtrapulmonaryDiedTB() + 1);
							table1.setOtherExtrapulmonaryEligible(table1
									.getOtherExtrapulmonaryEligible() + 1);
						}

						else if (diedNotTB != null && diedNotTB) {
							table1.setOtherAllDiedNotTB(table1
									.getOtherAllDiedNotTB() + 1);
							table1.setOtherAllEligible(table1
									.getOtherAllEligible() + 1);
							table1.setOtherExtrapulmonaryDiedNotTB(table1
									.getOtherExtrapulmonaryDiedNotTB() + 1);
							table1.setOtherExtrapulmonaryEligible(table1
									.getOtherExtrapulmonaryEligible() + 1);
						}

						else if (failed != null && failed) {
							table1.setOtherAllFailed(table1
									.getOtherAllFailed() + 1);
							table1.setOtherAllEligible(table1
									.getOtherAllEligible() + 1);
							table1.setOtherExtrapulmonaryFailed(table1
									.getOtherExtrapulmonaryFailed() + 1);
							table1.setOtherExtrapulmonaryEligible(table1
									.getOtherExtrapulmonaryEligible() + 1);
						}

						else if (defaulted != null && defaulted) {
							table1.setOtherAllDefaulted(table1
									.getOtherAllDefaulted() + 1);
							table1.setOtherAllEligible(table1
									.getOtherAllEligible() + 1);
							table1.setOtherExtrapulmonaryDefaulted(table1
									.getOtherExtrapulmonaryDefaulted() + 1);
							table1.setOtherExtrapulmonaryEligible(table1
									.getOtherExtrapulmonaryEligible() + 1);
						}

						else if (transferOut != null && transferOut) {
							table1.setOtherAllTransferOut(table1
									.getOtherAllTransferOut() + 1);
							table1.setOtherExtrapulmonaryTransferOut(table1
									.getOtherExtrapulmonaryTransferOut() + 1);

						}

						else if (canceled != null && canceled) {
							table1.setOtherAllCanceled(table1
									.getOtherAllCanceled() + 1);
							table1.setOtherExtrapulmonaryCanceled(table1
									.getOtherExtrapulmonaryCanceled() + 1);

						}

						else if (sld != null && sld) {
							table1.setOtherAllSLD(table1.getOtherAllSLD() + 1);
							table1.setOtherExtrapulmonarySLD(table1
									.getOtherExtrapulmonarySLD() + 1);

						}
					}
    	    	}
    	    }
    	//}
    	
    	//fin.add(table1);
    	
    	//TOTALS
		}
		
    	
		/*Integer report_oblast = null; Integer report_quarter = null; Integer report_month = null;
		//if(new PDFHelper().isInt(oblast)) { report_oblast = Integer.parseInt(oblast); }
		if(new PDFHelper().isInt(quarter)) { report_quarter = Integer.parseInt(quarter); }
		if(new PDFHelper().isInt(month)) { report_month = Integer.parseInt(month); }*/
		
		boolean reportStatus;
		/*if(location!=null)
			 reportStatus = Context.getService(MdrtbService.class).readReportStatus(report_oblast, location.getId(), year, report_quarter, report_month, "TB-08","DOTSTB");
		else*/
			reportStatus = Context.getService(MdrtbService.class).readReportStatus(oblastId, districtId, facilityId, year, quarter, month, "TB-08","DOTSTB");
		System.out.println(reportStatus);
		
    	model.addAttribute("table1", table1);
    	model.addAttribute("oblast", oblastId);
    	model.addAttribute("district", districtId);
    	model.addAttribute("faciloty", facilityId);
    	model.addAttribute("year", year);
    	if(month!=null && month.length()!=0)
			model.addAttribute("month", month.replace("\"", ""));
		else
			model.addAttribute("month", "");
		
		if(quarter!=null && quarter.length()!=0)
			model.addAttribute("quarter", quarter.replace("\"", "'"));
		else
			model.addAttribute("quarter", "");
    	model.addAttribute("reportDate", sdf.format(new Date()));
    	model.addAttribute("reportStatus", reportStatus);
        return "/module/mdrtb/reporting/tb08Results";
        //_" + Context.getLocale().toString().substring(0, 2);
    }
    
    
  
    
}
