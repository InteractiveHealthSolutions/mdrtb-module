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
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbConstants;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.form.TB03uForm;
import org.openmrs.module.mdrtb.reporting.PDFHelper;
import org.openmrs.module.mdrtb.reporting.ReportUtil;
import org.openmrs.module.mdrtb.reporting.TB08uData;
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

public class TB08uController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
        
	@RequestMapping(method = RequestMethod.GET, value = "/module/mdrtb/reporting/tb08u")
	public ModelAndView showRegimenOptions(
			@RequestParam(value = "loc", required = false) String district,
			@RequestParam(value = "ob", required = false) String oblast,
			@RequestParam(value = "yearSelected", required = false) Integer year,
			@RequestParam(value = "quarterSelected", required = false) String quarter,
			@RequestParam(value = "monthSelected", required = false) String month,
			ModelMap model) {

		List<Oblast> oblasts;
		List<Facility> facilities;
		List<District> districts;

		if (oblast == null) {
			oblasts = Context.getService(MdrtbService.class).getOblasts();
			model.addAttribute("oblasts", oblasts);
		}

		else if (district == null) {
			oblasts = Context.getService(MdrtbService.class).getOblasts();
			districts = Context.getService(MdrtbService.class).getDistricts(
					Integer.parseInt(oblast));
			model.addAttribute("oblastSelected", oblast);
			model.addAttribute("oblasts", oblasts);
			model.addAttribute("districts", districts);
		} else {
			oblasts = Context.getService(MdrtbService.class).getOblasts();
			districts = Context.getService(MdrtbService.class).getDistricts(
					Integer.parseInt(oblast));
			facilities = Context.getService(MdrtbService.class).getFacilities(
					Integer.parseInt(district));
			model.addAttribute("oblastSelected", oblast);
			model.addAttribute("oblasts", oblasts);
			model.addAttribute("districts", districts);
			model.addAttribute("districtSelected", district);
			model.addAttribute("facilities", facilities);
		}

		model.addAttribute("yearSelected", year);
		model.addAttribute("monthSelected", month);
		model.addAttribute("quarterSelected", quarter);

		/*
		 * List<Location> locations =
		 * Context.getLocationService().getAllLocations(false);//
		 * Context.getLocationService().getAllLocations();//ms =
		 * (MdrtbDrugForecastService)
		 * Context.getService(MdrtbDrugForecastService.class); List<Oblast>
		 * oblasts = Context.getService(MdrtbService.class).getOblasts();
		 * //drugSets = ms.getMdrtbDrugs();
		 * 
		 * 
		 * 
		 * model.addAttribute("locations", locations);
		 * model.addAttribute("oblasts", oblasts);
		 */
		return new ModelAndView("/module/mdrtb/reporting/tb08u", model);

	}

	@RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/tb08u")
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
    	
    	SimpleDateFormat sdf = new SimpleDateFormat();
    	sdf.applyPattern("dd.MM.yyyy");
    	SimpleDateFormat rdateSDF = new SimpleDateFormat();
    	rdateSDF.applyPattern("dd.MM.yyyy HH:mm:ss");
    	/*Oblast o = null;
    	if(oblast!=null && !oblast.equals("") && location == null)
			o =  Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblast));
		*/
		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId, districtId, facilityId);
		
		ArrayList<TB03uForm> tb03uList = Context.getService(MdrtbService.class).getTB03uFormsFilled(locList, year, quarter, month);
		/*if(o != null && location == null)
			locList = Context.getService(MdrtbService.class).getLocationsFromOblastName(o);
		else if (location != null)
			locList.add(location);*/
		
		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
		
		Date startDate = (Date)(dateMap.get("startDate"));
		Date endDate = (Date)(dateMap.get("endDate"));
		
		CohortDefinition baseCohort = null;
		
		//OBLAST
		if (!locList.isEmpty()){
			List<CohortDefinition> cohortDefinitions = new ArrayList<CohortDefinition>();
			for(Location loc : locList)
				cohortDefinitions.add(Cohorts.getTB03uByDatesAndLocation(startDate, endDate, loc));
				
			if(!cohortDefinitions.isEmpty()){
				baseCohort = ReportUtil.getCompositionCohort("OR", cohortDefinitions);
			}
		}
		
		else
			baseCohort = Cohorts.getTB03uByDatesAndLocation(startDate, endDate, null);
    	
    	Cohort patients = Context.getService(CohortDefinitionService.class).evaluate(baseCohort, new EvaluationContext());
    	//Cohort patients = TbUtil.getDOTSPatientsTJK(null, null, location, oblast, null, null, null, null,year,quarter,month);
    	
		Form tb03Form = Context.getFormService().getForm(MdrtbConstants.TB03U_FORM_ID);
		ArrayList<Form> formList = new ArrayList<Form>();
		formList.add(tb03Form);
    	
    	Set<Integer> idSet = patients.getMemberIds();
    	
    	ArrayList<Person> patientList = new ArrayList<Person>();
    	ArrayList<Concept> conceptQuestionList = new ArrayList<Concept>();
    	
    	List<Obs> obsList = null;*/
    	
    	TB08uData table1 = new TB08uData();
    	Concept q  = null;
    	
    	Boolean cured = null;
    	Boolean txCompleted = null;
    	Boolean diedTB = null;
    	Boolean diedNotTB = null;
    	Boolean failed = null;
    	Boolean defaulted = null;
    	Boolean transferOut = null;
    	Boolean canceled = null;
    	Boolean sld = null;
    	Boolean txStarted = null;
    	
    	for (TB03uForm tf : tb03uList) {
    		
    		cured = null;
    		txCompleted = null;
    		diedTB = null;
    		diedNotTB = null;
    		failed = null;
    		defaulted = null;
    		transferOut = null;
    		
    		txStarted = null;
    		
    		/*patientList.clear();
    		conceptQuestionList.clear();
    		System.out.println("PATIENT ID " + i);*/
    		
    		
    		Patient patient = tf.getPatient();
    	    if(patient==null || patient.isVoided()) {
    	    	continue;
    	    }
    	      
    	    //patientList.add(patient);
    	    
    	    //DATE OF MDR TREATMENT START
    	    Date txStartDate = tf.getMdrTreatmentStartDate();//Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TREATMENT_START_DATE);
    	    /*conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)*/
    	    if(txStartDate!=null)
    	    {
    	    	txStarted = Boolean.TRUE;
    	    }
    	    
    	    else {
    	    	txStarted = Boolean.FALSE;
    	    }
    	    
    	    q = tf.getTreatmentOutcome();//Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TB_TX_OUTCOME);
    	   /* conceptQuestionList.clear();
    	    conceptQuestionList.add(q);*/
    	    
    	    //obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    
    	    if(q!=null) {
    	    	
    	    	int outcomeId = q.getConceptId().intValue();
    	    	
    	    	if(outcomeId == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.outcome.cured.conceptId"))) {
    	    		cured = Boolean.TRUE;
    	    		System.out.println("CURED");
    	    	}
    	    	
    	    	else if(outcomeId == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.outcome.txCompleted.conceptId"))) {
    	    		txCompleted = Boolean.TRUE;
    	    		System.out.println("TxC");
    	    	}
    	    	
    	    	else if(outcomeId == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.outcome.txFailure.conceptId"))) {
    	    		failed = Boolean.TRUE;
    	    		System.out.println("FAIL");
    	    	}
    	    	
    	    	else if(outcomeId == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.outcome.died.conceptId"))) {
    	    		System.out.println("DIED");
    	    		q = tf.getCauseOfDeath();//Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSE_OF_DEATH);
    	     	    /*conceptQuestionList.clear();
    	     	    conceptQuestionList.add(q);*/
    	     	    
    	     	    //obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	     	    if(q!=null)
    	     	    {	
    	     	    	if(q.getConceptId().intValue() == Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DEATH_BY_TB).getConceptId().intValue())
    	     	    		diedTB = Boolean.TRUE;
    	     	    	else
    	     	    		diedNotTB = Boolean.TRUE;
    	     	    }
    	    	}
    	    	
    	    	else if(outcomeId == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.outcome.ltfu.conceptId"))) {
    	    		defaulted = Boolean.TRUE;
    	    		System.out.println("DEF");
    	    	}

    	    	else if(outcomeId == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.outcome.transferout.conceptId")) || txStarted) {
    	    		transferOut = Boolean.TRUE;
    	    		System.out.println("TOUT");
    	    	}

    	    }
    	    
    	    else {
	    		System.out.println("NO OUTCOME");
	    	}
    	    
    	    //REGISTRATION GROUP
    	    q = tf.getRegistrationGroup();//Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAT_4_CLASSIFICATION_PREVIOUS_TX);
    	   /* conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);*/
    	    
    	    if(q!=null) {
    	    	/*System.out.println (obsList.get(0).getValueCoded().getConceptId());*/
    	    	
    	    	if(q.getConceptId().intValue()!=Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.transferIn.conceptId"))) {
    	    		
    	    		table1.setTotalRegistered(table1.getTotalRegistered() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setTotalCured(table1.getTotalCured() + 1);
						table1.setTotalTxSuccess(table1.getTotalTxSuccess() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setTotalCompleted(table1.getTotalCompleted() + 1);
						table1.setTotalTxSuccess(table1.getTotalTxSuccess() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setTotalDiedTB(table1.getTotalDiedTB() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setTotalDiedNotTB(table1.getTotalDiedNotTB() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setTotalFailed(table1.getTotalFailed() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setTotalDefaulted(table1.getTotalDefaulted() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setTotalNotAssessed(table1.getTotalNotAssessed() + 1);
	
					}
    	    	}
    	    	
    	    	//NEW
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.new.conceptId"))) {
    	    		
    	    		table1.setNewRegistered(table1.getNewRegistered() + 1);
    	    		table1.setNewTotal(table1.getNewTotal() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setNewCured(table1.getNewCured() + 1);
						table1.setNewTxSuccess(table1.getNewTxSuccess() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setNewCompleted(table1.getNewCompleted() + 1);
						table1.setNewTxSuccess(table1.getNewTxSuccess() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setNewDiedTB(table1.getNewDiedTB() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setNewDiedNotTB(table1.getNewDiedNotTB() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setNewFailed(table1.getNewFailed() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setNewDefaulted(table1.getNewDefaulted() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setNewNotAssessed(table1.getNewNotAssessed() + 1);
	
					}
    	    	}
    	    	
    	    	//RELAPSE1
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterRelapse1.conceptId"))) {
    	    	
    	    		table1.setRelapse1Registered(table1.getRelapse1Registered() + 1);
    	    		table1.setRelapse1Total(table1.getRelapse1Total() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setRelapse1Cured(table1.getRelapse1Cured() + 1);
						table1.setRelapse1TxSuccess(table1.getRelapse1TxSuccess() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setRelapse1Completed(table1.getRelapse1Completed() + 1);
						table1.setRelapse1TxSuccess(table1.getRelapse1TxSuccess() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setRelapse1DiedTB(table1.getRelapse1DiedTB() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setRelapse1DiedNotTB(table1.getRelapse1DiedNotTB() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setRelapse1Failed(table1.getRelapse1Failed() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setRelapse1Defaulted(table1.getRelapse1Defaulted() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setRelapse1NotAssessed(table1.getRelapse1NotAssessed() + 1);
	
					}
    	    	}
    	    	
    	    	//RELAPSE2
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterRelapse2.conceptId"))) {
    	    		
    	    		table1.setRelapse2Registered(table1.getRelapse2Registered() + 1);
    	    		table1.setRelapse2Total(table1.getRelapse2Total() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setRelapse2Cured(table1.getRelapse2Cured() + 1);
						table1.setRelapse2TxSuccess(table1.getRelapse2TxSuccess() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setRelapse2Completed(table1.getRelapse2Completed() + 1);
						table1.setRelapse2TxSuccess(table1.getRelapse2TxSuccess() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setRelapse2DiedTB(table1.getRelapse2DiedTB() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setRelapse2DiedNotTB(table1.getRelapse2DiedNotTB() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setRelapse2Failed(table1.getRelapse2Failed() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setRelapse2Defaulted(table1.getRelapse2Defaulted() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setRelapse2NotAssessed(table1.getRelapse2NotAssessed() + 1);
	
					}
    	    	}
    	    	
    	    	//DEFAULT1
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterDefault1.conceptId"))) {
        	    	
    	    		table1.setDefault1Registered(table1.getDefault1Registered() + 1);
    	    		table1.setDefault1Total(table1.getDefault1Total() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setDefault1Cured(table1.getDefault1Cured() + 1);
						table1.setDefault1TxSuccess(table1.getDefault1TxSuccess() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setDefault1Completed(table1.getDefault1Completed() + 1);
						table1.setDefault1TxSuccess(table1.getDefault1TxSuccess() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setDefault1DiedTB(table1.getDefault1DiedTB() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setDefault1DiedNotTB(table1.getDefault1DiedNotTB() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setDefault1Failed(table1.getDefault1Failed() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setDefault1Defaulted(table1.getDefault1Defaulted() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setDefault1NotAssessed(table1.getDefault1NotAssessed() + 1);
	
					}
    	    	}
    	    	
    	    	
    	    	//DEFAULT2
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterDefault2.conceptId"))) {
        	    	
    	    		table1.setDefault2Registered(table1.getDefault2Registered() + 1);
    	    		table1.setDefault2Total(table1.getDefault2Total() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setDefault2Cured(table1.getDefault2Cured() + 1);
						table1.setDefault2TxSuccess(table1.getDefault2TxSuccess() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setDefault2Completed(table1.getDefault2Completed() + 1);
						table1.setDefault2TxSuccess(table1.getDefault2TxSuccess() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setDefault2DiedTB(table1.getDefault2DiedTB() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setDefault2DiedNotTB(table1.getDefault2DiedNotTB() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setDefault2Failed(table1.getDefault2Failed() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setDefault2Defaulted(table1.getDefault2Defaulted() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setDefault2NotAssessed(table1.getDefault2NotAssessed() + 1);
	
					}
    	    	}
    	    	
    	    	
    	    	//FAILURE1
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterFailure1.conceptId"))) {
        	    	
    	    		table1.setFailure1Registered(table1.getFailure1Registered() + 1);
    	    		table1.setFailure1Total(table1.getFailure1Total() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setFailure1Cured(table1.getFailure1Cured() + 1);
						table1.setFailure1TxSuccess(table1.getFailure1TxSuccess() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setFailure1Completed(table1.getFailure1Completed() + 1);
						table1.setFailure1TxSuccess(table1.getFailure1TxSuccess() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setFailure1DiedTB(table1.getFailure1DiedTB() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setFailure1DiedNotTB(table1.getFailure1DiedNotTB() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setFailure1Failed(table1.getFailure1Failed() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setFailure1Defaulted(table1.getFailure1Defaulted() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setFailure1NotAssessed(table1.getFailure1NotAssessed() + 1);
	
					}
    	    	}
    	    	
    	    	
    	    	//FAILURE2
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterFailure2.conceptId"))) {
        	    	
    	    		table1.setFailure2Registered(table1.getFailure2Registered() + 1);
    	    		table1.setFailure2Total(table1.getFailure2Total() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setFailure2Cured(table1.getFailure2Cured() + 1);
						table1.setFailure2TxSuccess(table1.getFailure2TxSuccess() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setFailure2Completed(table1.getFailure2Completed() + 1);
						table1.setFailure2TxSuccess(table1.getFailure2TxSuccess() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setFailure2DiedTB(table1.getFailure2DiedTB() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setFailure2DiedNotTB(table1.getFailure2DiedNotTB() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setFailure2Failed(table1.getFailure2Failed() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setFailure2Defaulted(table1.getFailure2Defaulted() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setFailure2NotAssessed(table1.getFailure2NotAssessed() + 1);
	
					}
    	    	}

    	    	//OTHER
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.other.conceptId"))) {
        	    	
    	    		table1.setOtherRegistered(table1.getOtherRegistered() + 1);
    	    		table1.setOtherTotal(table1.getOtherTotal() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setOtherCured(table1.getOtherCured() + 1);
						table1.setOtherTxSuccess(table1.getOtherTxSuccess() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setOtherCompleted(table1.getOtherCompleted() + 1);
						table1.setOtherTxSuccess(table1.getOtherTxSuccess() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setOtherDiedTB(table1.getOtherDiedTB() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setOtherDiedNotTB(table1.getOtherDiedNotTB() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setOtherFailed(table1.getOtherFailed() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setOtherDefaulted(table1.getOtherDefaulted() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setOtherNotAssessed(table1.getOtherNotAssessed() + 1);
					}
    	    	}
    	    	
    	    }
    	}
    	
    	// TO CHECK WHETHER REPORT IS CLOSED OR NOT
    	Integer report_oblast = null; Integer report_quarter = null; Integer report_month = null;
		/*if(new PDFHelper().isInt(oblast)) { report_oblast = Integer.parseInt(oblast); }
		if(new PDFHelper().isInt(quarter)) { report_quarter = Integer.parseInt(quarter); }
		if(new PDFHelper().isInt(month)) { report_month = Integer.parseInt(month); }*/
		model.addAttribute("table1", table1);
    	boolean reportStatus = Context.getService(MdrtbService.class).readReportStatus(oblastId, districtId, facilityId, year, quarter, month, "TB-08u","MDRTB");
		System.out.println(reportStatus);
		
		String oName = null;
		String dName = null;
		String fName = null;
		
		if(oblastId!=null) {
			Oblast o = Context.getService(MdrtbService.class).getOblast(oblastId);
			if(o!=null) {
				oName = o.getName();
			}
		}
		
		if(districtId!=null) {
			District d = Context.getService(MdrtbService.class).getDistrict(districtId);
			if(d!=null) {
				dName = d.getName();
			}
		}
		
		if(facilityId!=null) {
			Facility f = Context.getService(MdrtbService.class).getFacility(facilityId);
			if(f!=null) {
				fName = f.getName();
			}
		}
		
		model.addAttribute("oblast", oblastId);
    	model.addAttribute("facility", facilityId);
    	model.addAttribute("district", districtId);
    	model.addAttribute("year", year);
    	if(month!=null && month.length()!=0)
			model.addAttribute("month", month.replace("\"", ""));
		else
			model.addAttribute("month", "");
		
		if(quarter!=null && quarter.length()!=0)
			model.addAttribute("quarter", quarter.replace("\"", ""));
		else
			model.addAttribute("quarter", "");
		
		model.addAttribute("oName", oName);
		model.addAttribute("dName", dName);
		model.addAttribute("fName", fName);
    	model.addAttribute("reportDate", rdateSDF.format(new Date()));
    	model.addAttribute("reportStatus", reportStatus);
        return "/module/mdrtb/reporting/tb08uResults_" + Context.getLocale().toString().substring(0, 2);
    }
}