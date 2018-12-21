package org.openmrs.module.mdrtb.web.controller.reporting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.form.TB03uForm;
import org.openmrs.module.mdrtb.reporting.TB08uData;
import org.openmrs.module.mdrtb.service.MdrtbService;
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
			// DUSHANBE
			if (Integer.parseInt(oblast) == 186) {
				oblasts = Context.getService(MdrtbService.class).getOblasts();
				districts = Context.getService(MdrtbService.class)
						.getDistricts(Integer.parseInt(oblast));
				District d = districts.get(0);
				facilities = Context.getService(MdrtbService.class)
						.getFacilities(d.getId());
				model.addAttribute("oblastSelected", oblast);
				model.addAttribute("oblasts", oblasts);
				model.addAttribute("districts", districts);
				model.addAttribute("facilities", facilities);
				model.addAttribute("dushanbe", 186);
			}

			else {

				oblasts = Context.getService(MdrtbService.class).getOblasts();
				districts = Context.getService(MdrtbService.class)
						.getDistricts(Integer.parseInt(oblast));
				model.addAttribute("oblastSelected", oblast);
				model.addAttribute("oblasts", oblasts);
				model.addAttribute("districts", districts);
			}
		}
		
		else {
			
			/*
			 * if oblast is dushanbe, return both districts and facilities
			 */
			if (Integer.parseInt(oblast) == 186) {
				oblasts = Context.getService(MdrtbService.class).getOblasts();
				districts = Context.getService(MdrtbService.class)
						.getDistricts(Integer.parseInt(oblast));
				District d = districts.get(0);
				facilities = Context.getService(MdrtbService.class)
						.getFacilities(d.getId());
				model.addAttribute("oblastSelected", oblast);
				model.addAttribute("oblasts", oblasts);
				model.addAttribute("districts", districts);
				model.addAttribute("facilities", facilities);
				model.addAttribute("dushanbe", 186);
			}

			else {
				oblasts = Context.getService(MdrtbService.class).getOblasts();
				districts = Context.getService(MdrtbService.class)
						.getDistricts(Integer.parseInt(oblast));
				facilities = Context.getService(MdrtbService.class)
						.getFacilities(Integer.parseInt(district));
				model.addAttribute("oblastSelected", oblast);
				model.addAttribute("oblasts", oblasts);
				model.addAttribute("districts", districts);
				model.addAttribute("districtSelected", district);
				model.addAttribute("facilities", facilities);
			}
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
		//ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId, districtId, facilityId);
    	ArrayList<Location> locList = null;
		if(oblastId.intValue()==186) {
			locList = Context.getService(MdrtbService.class).getLocationListForDushanbe(oblastId,districtId,facilityId);
		}
		else {
			locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
		}
    	
    	
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
    	
    	//Concept regimenType = null;
    	/*Concept shortCnc = null;
    	Concept indivCnc = null;
    	Concept stdCnc = null;*/
    	
    	Concept regimen = null;
    	
    	int shrt = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REGIMEN_2_SHORT).getConceptId().intValue();
    	int ind = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REGIMEN_2_INDIVIDUALIZED).getConceptId().intValue();
    	int standard = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REGIMEN_2_STANDARD).getConceptId().intValue();
    	
    	for (TB03uForm tf : tb03uList) {
    		
    		cured = null;
    		txCompleted = null;
    		diedTB = null;
    		diedNotTB = null;
    		failed = null;
    		defaulted = null;
    		transferOut = null;
    		
    		txStarted = null;
    		regimen = null;
    		
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
    	    regimen = tf.getPatientCategory();
    	    
    	    if(regimen==null)
    	    	continue;
    	    
    	    int regId = regimen.getConceptId().intValue();
    	    /* conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);*/
    	    if (regId==shrt) {
    	    if(q!=null) {
    	    	/*System.out.println (obsList.get(0).getValueCoded().getConceptId());*/
    	    	
    	    	if(q.getConceptId().intValue()!=Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.transferIn.conceptId"))) {
    	    		
    	    		table1.setTotalRegisteredShort(table1.getTotalRegisteredShort() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setTotalCuredShort(table1.getTotalCuredShort() + 1);
						table1.setTotalTxSuccessShort(table1.getTotalTxSuccessShort() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setTotalCompletedShort(table1.getTotalCompletedShort() + 1);
						table1.setTotalTxSuccessShort(table1.getTotalTxSuccessShort() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setTotalDiedTBShort(table1.getTotalDiedTBShort() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setTotalDiedNotTBShort(table1.getTotalDiedNotTBShort() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setTotalFailedShort(table1.getTotalFailedShort() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setTotalDefaultedShort(table1.getTotalDefaultedShort() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setTotalNotAssessedShort(table1.getTotalNotAssessedShort() + 1);
	
					}
    	    		
					else {
						table1.setTotalNotAssessedShort(table1.getTotalNotAssessedShort() + 1);
					}
    	    	}
    	    	
    	    	//NEW
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.new.conceptId"))) {
    	    		
    	    		table1.setNewRegisteredShort(table1.getNewRegisteredShort() + 1);
    	    		table1.setNewTotalShort(table1.getNewTotalShort() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setNewCuredShort(table1.getNewCuredShort() + 1);
						table1.setNewTxSuccessShort(table1.getNewTxSuccessShort() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setNewCompletedShort(table1.getNewCompletedShort() + 1);
						table1.setNewTxSuccessShort(table1.getNewTxSuccessShort() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setNewDiedTBShort(table1.getNewDiedTBShort() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setNewDiedNotTBShort(table1.getNewDiedNotTBShort() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setNewFailedShort(table1.getNewFailedShort() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setNewDefaultedShort(table1.getNewDefaultedShort() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setNewNotAssessedShort(table1.getNewNotAssessedShort() + 1);
	
					}
    	    		
					else {
							table1.setNewNotAssessedShort(table1.getNewNotAssessedShort() + 1);
		
						
					}
    	    	}
    	    	
    	    	//RELAPSE1
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterRelapse1.conceptId"))) {
    	    	
    	    		table1.setRelapse1RegisteredShort(table1.getRelapse1RegisteredShort() + 1);
    	    		table1.setRelapse1TotalShort(table1.getRelapse1TotalShort() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setRelapse1CuredShort(table1.getRelapse1CuredShort() + 1);
						table1.setRelapse1TxSuccessShort(table1.getRelapse1TxSuccessShort() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setRelapse1CompletedShort(table1.getRelapse1CompletedShort() + 1);
						table1.setRelapse1TxSuccessShort(table1.getRelapse1TxSuccessShort() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setRelapse1DiedTBShort(table1.getRelapse1DiedTBShort() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setRelapse1DiedNotTBShort(table1.getRelapse1DiedNotTBShort() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setRelapse1FailedShort(table1.getRelapse1FailedShort() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setRelapse1DefaultedShort(table1.getRelapse1DefaultedShort() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setRelapse1NotAssessedShort(table1.getRelapse1NotAssessedShort() + 1);
	
					}
    	    		
					else {
						table1.setRelapse1NotAssessedShort(table1.getRelapse1NotAssessedShort() + 1);
	
					}
    	    	}
    	    	
    	    	//RELAPSE2
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterRelapse2.conceptId"))) {
    	    		
    	    		table1.setRelapse2RegisteredShort(table1.getRelapse2RegisteredShort() + 1);
    	    		table1.setRelapse2TotalShort(table1.getRelapse2TotalShort() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setRelapse2CuredShort(table1.getRelapse2CuredShort() + 1);
						table1.setRelapse2TxSuccessShort(table1.getRelapse2TxSuccessShort() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setRelapse2CompletedShort(table1.getRelapse2CompletedShort() + 1);
						table1.setRelapse2TxSuccessShort(table1.getRelapse2TxSuccessShort() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setRelapse2DiedTBShort(table1.getRelapse2DiedTBShort() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setRelapse2DiedNotTBShort(table1.getRelapse2DiedNotTBShort() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setRelapse2FailedShort(table1.getRelapse2FailedShort() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setRelapse2DefaultedShort(table1.getRelapse2DefaultedShort() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setRelapse2NotAssessedShort(table1.getRelapse2NotAssessedShort() + 1);
	
					}
    	    		
					else {
						table1.setRelapse2NotAssessedShort(table1.getRelapse2NotAssessedShort() + 1);
	
					}
    	    	}
    	    	
    	    	//DEFAULT1
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterDefault1.conceptId"))) {
        	    	
    	    		table1.setDefault1RegisteredShort(table1.getDefault1RegisteredShort() + 1);
    	    		table1.setDefault1TotalShort(table1.getDefault1TotalShort() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setDefault1CuredShort(table1.getDefault1CuredShort() + 1);
						table1.setDefault1TxSuccessShort(table1.getDefault1TxSuccessShort() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setDefault1CompletedShort(table1.getDefault1CompletedShort() + 1);
						table1.setDefault1TxSuccessShort(table1.getDefault1TxSuccessShort() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setDefault1DiedTBShort(table1.getDefault1DiedTBShort() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setDefault1DiedNotTBShort(table1.getDefault1DiedNotTBShort() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setDefault1FailedShort(table1.getDefault1FailedShort() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setDefault1DefaultedShort(table1.getDefault1DefaultedShort() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setDefault1NotAssessedShort(table1.getDefault1NotAssessedShort() + 1);
	
					}
    	    		
					else {
						table1.setDefault1NotAssessedShort(table1.getDefault1NotAssessedShort() + 1);
	
					}
    	    	}
    	    	
    	    	
    	    	//DEFAULT2
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterDefault2.conceptId"))) {
        	    	
    	    		table1.setDefault2RegisteredShort(table1.getDefault2RegisteredShort() + 1);
    	    		table1.setDefault2TotalShort(table1.getDefault2TotalShort() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setDefault2CuredShort(table1.getDefault2CuredShort() + 1);
						table1.setDefault2TxSuccessShort(table1.getDefault2TxSuccessShort() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setDefault2CompletedShort(table1.getDefault2CompletedShort() + 1);
						table1.setDefault2TxSuccessShort(table1.getDefault2TxSuccessShort() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setDefault2DiedTBShort(table1.getDefault2DiedTBShort() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setDefault2DiedNotTBShort(table1.getDefault2DiedNotTBShort() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setDefault2FailedShort(table1.getDefault2FailedShort() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setDefault2DefaultedShort(table1.getDefault2DefaultedShort() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setDefault2NotAssessedShort(table1.getDefault2NotAssessedShort() + 1);
	
					}
    	    		
					else {
						table1.setDefault2NotAssessedShort(table1.getDefault2NotAssessedShort() + 1);
	
					}
    	    	}
    	    	
    	    	
    	    	//FAILURE1
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterFailure1.conceptId"))) {
        	    	
    	    		table1.setFailure1RegisteredShort(table1.getFailure1RegisteredShort() + 1);
    	    		table1.setFailure1TotalShort(table1.getFailure1TotalShort() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setFailure1CuredShort(table1.getFailure1CuredShort() + 1);
						table1.setFailure1TxSuccessShort(table1.getFailure1TxSuccessShort() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setFailure1CompletedShort(table1.getFailure1CompletedShort() + 1);
						table1.setFailure1TxSuccessShort(table1.getFailure1TxSuccessShort() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setFailure1DiedTBShort(table1.getFailure1DiedTBShort() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setFailure1DiedNotTBShort(table1.getFailure1DiedNotTBShort() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setFailure1FailedShort(table1.getFailure1FailedShort() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setFailure1DefaultedShort(table1.getFailure1DefaultedShort() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setFailure1NotAssessedShort(table1.getFailure1NotAssessedShort() + 1);
	
					}
    	    		
					else {
						table1.setFailure1NotAssessedShort(table1.getFailure1NotAssessedShort() + 1);
	
					}
    	    	}
    	    	
    	    	
    	    	//FAILURE2
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterFailure2.conceptId"))) {
        	    	
    	    		table1.setFailure2RegisteredShort(table1.getFailure2RegisteredShort() + 1);
    	    		table1.setFailure2TotalShort(table1.getFailure2TotalShort() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setFailure2CuredShort(table1.getFailure2CuredShort() + 1);
						table1.setFailure2TxSuccessShort(table1.getFailure2TxSuccessShort() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setFailure2CompletedShort(table1.getFailure2CompletedShort() + 1);
						table1.setFailure2TxSuccessShort(table1.getFailure2TxSuccessShort() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setFailure2DiedTBShort(table1.getFailure2DiedTBShort() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setFailure2DiedNotTBShort(table1.getFailure2DiedNotTBShort() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setFailure2FailedShort(table1.getFailure2FailedShort() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setFailure2DefaultedShort(table1.getFailure2DefaultedShort() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setFailure2NotAssessedShort(table1.getFailure2NotAssessedShort() + 1);
	
					}
    	    		
					else {
						table1.setFailure2NotAssessedShort(table1.getFailure2NotAssessedShort() + 1);
	
					}
    	    	}

    	    	//OTHER
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.other.conceptId"))) {
        	    	
    	    		table1.setOtherRegisteredShort(table1.getOtherRegisteredShort() + 1);
    	    		table1.setOtherTotalShort(table1.getOtherTotalShort() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table1.setOtherCuredShort(table1.getOtherCuredShort() + 1);
						table1.setOtherTxSuccessShort(table1.getOtherTxSuccessShort() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table1.setOtherCompletedShort(table1.getOtherCompletedShort() + 1);
						table1.setOtherTxSuccessShort(table1.getOtherTxSuccessShort() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table1.setOtherDiedTBShort(table1.getOtherDiedTBShort() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table1.setOtherDiedNotTBShort(table1.getOtherDiedNotTBShort() + 1);
					}
					
					else if (failed!=null && failed) {
						table1.setOtherFailedShort(table1.getOtherFailedShort() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table1.setOtherDefaultedShort(table1.getOtherDefaultedShort() + 1);
						
					}
					
					else if (transferOut!=null && transferOut) {
						table1.setOtherNotAssessedShort(table1.getOtherNotAssessedShort() + 1);
					}
    	    		
					else {
						table1.setOtherNotAssessedShort(table1.getOtherNotAssessedShort() + 1);
					}
    	    	}
    	    }
    	    	
    	    }
    	    
    	    else if(regId==ind) {
    	    	if(q!=null) {
        	    	/*System.out.println (obsList.get(0).getValueCoded().getConceptId());*/
        	    	
        	    	if(q.getConceptId().intValue()!=Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.transferIn.conceptId"))) {
        	    		
        	    		table1.setTotalRegisteredIndiv(table1.getTotalRegisteredIndiv() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setTotalCuredIndiv(table1.getTotalCuredIndiv() + 1);
    						table1.setTotalTxSuccessIndiv(table1.getTotalTxSuccessIndiv() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setTotalCompletedIndiv(table1.getTotalCompletedIndiv() + 1);
    						table1.setTotalTxSuccessIndiv(table1.getTotalTxSuccessIndiv() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setTotalDiedTBIndiv(table1.getTotalDiedTBIndiv() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setTotalDiedNotTBIndiv(table1.getTotalDiedNotTBIndiv() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setTotalFailedIndiv(table1.getTotalFailedIndiv() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setTotalDefaultedIndiv(table1.getTotalDefaultedIndiv() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setTotalNotAssessedIndiv(table1.getTotalNotAssessedIndiv() + 1);
    	
    					}
        	    		
    					else {
    						table1.setTotalNotAssessedIndiv(table1.getTotalNotAssessedIndiv() + 1);
    	
    					}
        	    	}
        	    	
        	    	//NEW
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.new.conceptId"))) {
        	    		
        	    		table1.setNewRegisteredIndiv(table1.getNewRegisteredIndiv() + 1);
        	    		table1.setNewTotalIndiv(table1.getNewTotalIndiv() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setNewCuredIndiv(table1.getNewCuredIndiv() + 1);
    						table1.setNewTxSuccessIndiv(table1.getNewTxSuccessIndiv() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setNewCompletedIndiv(table1.getNewCompletedIndiv() + 1);
    						table1.setNewTxSuccessIndiv(table1.getNewTxSuccessIndiv() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setNewDiedTBIndiv(table1.getNewDiedTBIndiv() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setNewDiedNotTBIndiv(table1.getNewDiedNotTBIndiv() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setNewFailedIndiv(table1.getNewFailedIndiv() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setNewDefaultedIndiv(table1.getNewDefaultedIndiv() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setNewNotAssessedIndiv(table1.getNewNotAssessedIndiv() + 1);
    	
    					}
        	    		
    					else {
    						table1.setNewNotAssessedIndiv(table1.getNewNotAssessedIndiv() + 1);
    	
    					}
        	    	}
        	    	
        	    	//RELAPSE1
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterRelapse1.conceptId"))) {
        	    	
        	    		table1.setRelapse1RegisteredIndiv(table1.getRelapse1RegisteredIndiv() + 1);
        	    		table1.setRelapse1TotalIndiv(table1.getRelapse1TotalIndiv() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setRelapse1CuredIndiv(table1.getRelapse1CuredIndiv() + 1);
    						table1.setRelapse1TxSuccessIndiv(table1.getRelapse1TxSuccessIndiv() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setRelapse1CompletedIndiv(table1.getRelapse1CompletedIndiv() + 1);
    						table1.setRelapse1TxSuccessIndiv(table1.getRelapse1TxSuccessIndiv() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setRelapse1DiedTBIndiv(table1.getRelapse1DiedTBIndiv() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setRelapse1DiedNotTBIndiv(table1.getRelapse1DiedNotTBIndiv() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setRelapse1FailedIndiv(table1.getRelapse1FailedIndiv() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setRelapse1DefaultedIndiv(table1.getRelapse1DefaultedIndiv() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setRelapse1NotAssessedIndiv(table1.getRelapse1NotAssessedIndiv() + 1);
    	
    					}
        	    		
    					else {
    						table1.setRelapse1NotAssessedIndiv(table1.getRelapse1NotAssessedIndiv() + 1);
    	
    					}
        	    	}
        	    	
        	    	//RELAPSE2
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterRelapse2.conceptId"))) {
        	    		
        	    		table1.setRelapse2RegisteredIndiv(table1.getRelapse2RegisteredIndiv() + 1);
        	    		table1.setRelapse2TotalIndiv(table1.getRelapse2TotalIndiv() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setRelapse2CuredIndiv(table1.getRelapse2CuredIndiv() + 1);
    						table1.setRelapse2TxSuccessIndiv(table1.getRelapse2TxSuccessIndiv() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setRelapse2CompletedIndiv(table1.getRelapse2CompletedIndiv() + 1);
    						table1.setRelapse2TxSuccessIndiv(table1.getRelapse2TxSuccessIndiv() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setRelapse2DiedTBIndiv(table1.getRelapse2DiedTBIndiv() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setRelapse2DiedNotTBIndiv(table1.getRelapse2DiedNotTBIndiv() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setRelapse2FailedIndiv(table1.getRelapse2FailedIndiv() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setRelapse2DefaultedIndiv(table1.getRelapse2DefaultedIndiv() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setRelapse2NotAssessedIndiv(table1.getRelapse2NotAssessedIndiv() + 1);
    	
    					}
        	    		
    					else {
    						table1.setRelapse2NotAssessedIndiv(table1.getRelapse2NotAssessedIndiv() + 1);
    	
    					}
        	    	}
        	    	
        	    	//DEFAULT1
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterDefault1.conceptId"))) {
            	    	
        	    		table1.setDefault1RegisteredIndiv(table1.getDefault1RegisteredIndiv() + 1);
        	    		table1.setDefault1TotalIndiv(table1.getDefault1TotalIndiv() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setDefault1CuredIndiv(table1.getDefault1CuredIndiv() + 1);
    						table1.setDefault1TxSuccessIndiv(table1.getDefault1TxSuccessIndiv() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setDefault1CompletedIndiv(table1.getDefault1CompletedIndiv() + 1);
    						table1.setDefault1TxSuccessIndiv(table1.getDefault1TxSuccessIndiv() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setDefault1DiedTBIndiv(table1.getDefault1DiedTBIndiv() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setDefault1DiedNotTBIndiv(table1.getDefault1DiedNotTBIndiv() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setDefault1FailedIndiv(table1.getDefault1FailedIndiv() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setDefault1DefaultedIndiv(table1.getDefault1DefaultedIndiv() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setDefault1NotAssessedIndiv(table1.getDefault1NotAssessedIndiv() + 1);
    	
    					}
        	    		
    					else  {
    						table1.setDefault1NotAssessedIndiv(table1.getDefault1NotAssessedIndiv() + 1);
    	
    					}
        	    	}
        	    	
        	    	
        	    	//DEFAULT2
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterDefault2.conceptId"))) {
            	    	
        	    		table1.setDefault2RegisteredIndiv(table1.getDefault2RegisteredIndiv() + 1);
        	    		table1.setDefault2TotalIndiv(table1.getDefault2TotalIndiv() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setDefault2CuredIndiv(table1.getDefault2CuredIndiv() + 1);
    						table1.setDefault2TxSuccessIndiv(table1.getDefault2TxSuccessIndiv() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setDefault2CompletedIndiv(table1.getDefault2CompletedIndiv() + 1);
    						table1.setDefault2TxSuccessIndiv(table1.getDefault2TxSuccessIndiv() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setDefault2DiedTBIndiv(table1.getDefault2DiedTBIndiv() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setDefault2DiedNotTBIndiv(table1.getDefault2DiedNotTBIndiv() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setDefault2FailedIndiv(table1.getDefault2FailedIndiv() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setDefault2DefaultedIndiv(table1.getDefault2DefaultedIndiv() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setDefault2NotAssessedIndiv(table1.getDefault2NotAssessedIndiv() + 1);
    	
    					}
        	    		
    					else {
    						table1.setDefault2NotAssessedIndiv(table1.getDefault2NotAssessedIndiv() + 1);
    	
    					}
        	    	}
        	    	
        	    	
        	    	//FAILURE1
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterFailure1.conceptId"))) {
            	    	
        	    		table1.setFailure1RegisteredIndiv(table1.getFailure1RegisteredIndiv() + 1);
        	    		table1.setFailure1TotalIndiv(table1.getFailure1TotalIndiv() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setFailure1CuredIndiv(table1.getFailure1CuredIndiv() + 1);
    						table1.setFailure1TxSuccessIndiv(table1.getFailure1TxSuccessIndiv() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setFailure1CompletedIndiv(table1.getFailure1CompletedIndiv() + 1);
    						table1.setFailure1TxSuccessIndiv(table1.getFailure1TxSuccessIndiv() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setFailure1DiedTBIndiv(table1.getFailure1DiedTBIndiv() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setFailure1DiedNotTBIndiv(table1.getFailure1DiedNotTBIndiv() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setFailure1FailedIndiv(table1.getFailure1FailedIndiv() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setFailure1DefaultedIndiv(table1.getFailure1DefaultedIndiv() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setFailure1NotAssessedIndiv(table1.getFailure1NotAssessedIndiv() + 1);
    	
    					}
        	    		
    					else {
    						table1.setFailure1NotAssessedIndiv(table1.getFailure1NotAssessedIndiv() + 1);
    	
    					}
        	    	}
        	    	
        	    	
        	    	//FAILURE2
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterFailure2.conceptId"))) {
            	    	
        	    		table1.setFailure2RegisteredIndiv(table1.getFailure2RegisteredIndiv() + 1);
        	    		table1.setFailure2TotalIndiv(table1.getFailure2TotalIndiv() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setFailure2CuredIndiv(table1.getFailure2CuredIndiv() + 1);
    						table1.setFailure2TxSuccessIndiv(table1.getFailure2TxSuccessIndiv() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setFailure2CompletedIndiv(table1.getFailure2CompletedIndiv() + 1);
    						table1.setFailure2TxSuccessIndiv(table1.getFailure2TxSuccessIndiv() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setFailure2DiedTBIndiv(table1.getFailure2DiedTBIndiv() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setFailure2DiedNotTBIndiv(table1.getFailure2DiedNotTBIndiv() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setFailure2FailedIndiv(table1.getFailure2FailedIndiv() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setFailure2DefaultedIndiv(table1.getFailure2DefaultedIndiv() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setFailure2NotAssessedIndiv(table1.getFailure2NotAssessedIndiv() + 1);
    	
    					}
        	    		
    					else {
    						table1.setFailure2NotAssessedIndiv(table1.getFailure2NotAssessedIndiv() + 1);
    	
    					}
        	    	}

        	    	//OTHER
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.other.conceptId"))) {
            	    	
        	    		table1.setOtherRegisteredIndiv(table1.getOtherRegisteredIndiv() + 1);
        	    		table1.setOtherTotalIndiv(table1.getOtherTotalIndiv() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setOtherCuredIndiv(table1.getOtherCuredIndiv() + 1);
    						table1.setOtherTxSuccessIndiv(table1.getOtherTxSuccessIndiv() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setOtherCompletedIndiv(table1.getOtherCompletedIndiv() + 1);
    						table1.setOtherTxSuccessIndiv(table1.getOtherTxSuccessIndiv() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setOtherDiedTBIndiv(table1.getOtherDiedTBIndiv() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setOtherDiedNotTBIndiv(table1.getOtherDiedNotTBIndiv() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setOtherFailedIndiv(table1.getOtherFailedIndiv() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setOtherDefaultedIndiv(table1.getOtherDefaultedIndiv() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setOtherNotAssessedIndiv(table1.getOtherNotAssessedIndiv() + 1);
    					}
        	    		
    					else  {
    						table1.setOtherNotAssessedIndiv(table1.getOtherNotAssessedIndiv() + 1);
    					}
        	    	}
        	    }
    	    }
    	    
    	    
    	    else if(regId==standard) {
    	    	if(q!=null) {
        	    	/*System.out.println (obsList.get(0).getValueCoded().getConceptId());*/
        	    	
        	    	if(q.getConceptId().intValue()!=Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.transferIn.conceptId"))) {
        	    		
        	    		table1.setTotalRegisteredStandard(table1.getTotalRegisteredStandard() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setTotalCuredStandard(table1.getTotalCuredStandard() + 1);
    						table1.setTotalTxSuccessStandard(table1.getTotalTxSuccessStandard() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setTotalCompletedStandard(table1.getTotalCompletedStandard() + 1);
    						table1.setTotalTxSuccessStandard(table1.getTotalTxSuccessStandard() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setTotalDiedTBStandard(table1.getTotalDiedTBStandard() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setTotalDiedNotTBStandard(table1.getTotalDiedNotTBStandard() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setTotalFailedStandard(table1.getTotalFailedStandard() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setTotalDefaultedStandard(table1.getTotalDefaultedStandard() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setTotalNotAssessedStandard(table1.getTotalNotAssessedStandard() + 1);
    	
    					}
        	    		
    					else {
    						table1.setTotalNotAssessedStandard(table1.getTotalNotAssessedStandard() + 1);
    	
    					}
        	    	}
        	    	
        	    	//NEW
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.new.conceptId"))) {
        	    		
        	    		table1.setNewRegisteredStandard(table1.getNewRegisteredStandard() + 1);
        	    		table1.setNewTotalStandard(table1.getNewTotalStandard() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setNewCuredStandard(table1.getNewCuredStandard() + 1);
    						table1.setNewTxSuccessStandard(table1.getNewTxSuccessStandard() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setNewCompletedStandard(table1.getNewCompletedStandard() + 1);
    						table1.setNewTxSuccessStandard(table1.getNewTxSuccessStandard() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setNewDiedTBStandard(table1.getNewDiedTBStandard() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setNewDiedNotTBStandard(table1.getNewDiedNotTBStandard() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setNewFailedStandard(table1.getNewFailedStandard() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setNewDefaultedStandard(table1.getNewDefaultedStandard() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setNewNotAssessedStandard(table1.getNewNotAssessedStandard() + 1);
    	
    					}
        	    		
    					else  {
    						table1.setNewNotAssessedStandard(table1.getNewNotAssessedStandard() + 1);
    	
    					}
        	    	}
        	    	
        	    	//RELAPSE1
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterRelapse1.conceptId"))) {
        	    	
        	    		table1.setRelapse1RegisteredStandard(table1.getRelapse1RegisteredStandard() + 1);
        	    		table1.setRelapse1TotalStandard(table1.getRelapse1TotalStandard() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setRelapse1CuredStandard(table1.getRelapse1CuredStandard() + 1);
    						table1.setRelapse1TxSuccessStandard(table1.getRelapse1TxSuccessStandard() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setRelapse1CompletedStandard(table1.getRelapse1CompletedStandard() + 1);
    						table1.setRelapse1TxSuccessStandard(table1.getRelapse1TxSuccessStandard() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setRelapse1DiedTBStandard(table1.getRelapse1DiedTBStandard() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setRelapse1DiedNotTBStandard(table1.getRelapse1DiedNotTBStandard() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setRelapse1FailedStandard(table1.getRelapse1FailedStandard() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setRelapse1DefaultedStandard(table1.getRelapse1DefaultedStandard() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setRelapse1NotAssessedStandard(table1.getRelapse1NotAssessedStandard() + 1);
    	
    					}
        	    		
    					else  {
    						table1.setRelapse1NotAssessedStandard(table1.getRelapse1NotAssessedStandard() + 1);
    	
    					}
        	    	}
        	    	
        	    	//RELAPSE2
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterRelapse2.conceptId"))) {
        	    		
        	    		table1.setRelapse2RegisteredStandard(table1.getRelapse2RegisteredStandard() + 1);
        	    		table1.setRelapse2TotalStandard(table1.getRelapse2TotalStandard() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setRelapse2CuredStandard(table1.getRelapse2CuredStandard() + 1);
    						table1.setRelapse2TxSuccessStandard(table1.getRelapse2TxSuccessStandard() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setRelapse2CompletedStandard(table1.getRelapse2CompletedStandard() + 1);
    						table1.setRelapse2TxSuccessStandard(table1.getRelapse2TxSuccessStandard() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setRelapse2DiedTBStandard(table1.getRelapse2DiedTBStandard() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setRelapse2DiedNotTBStandard(table1.getRelapse2DiedNotTBStandard() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setRelapse2FailedStandard(table1.getRelapse2FailedStandard() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setRelapse2DefaultedStandard(table1.getRelapse2DefaultedStandard() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setRelapse2NotAssessedStandard(table1.getRelapse2NotAssessedStandard() + 1);
    	
    					}
        	    		
    					else {
    						table1.setRelapse2NotAssessedStandard(table1.getRelapse2NotAssessedStandard() + 1);
    	
    					}
        	    	}
        	    	
        	    	//DEFAULT1
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterDefault1.conceptId"))) {
            	    	
        	    		table1.setDefault1RegisteredStandard(table1.getDefault1RegisteredStandard() + 1);
        	    		table1.setDefault1TotalStandard(table1.getDefault1TotalStandard() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setDefault1CuredStandard(table1.getDefault1CuredStandard() + 1);
    						table1.setDefault1TxSuccessStandard(table1.getDefault1TxSuccessStandard() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setDefault1CompletedStandard(table1.getDefault1CompletedStandard() + 1);
    						table1.setDefault1TxSuccessStandard(table1.getDefault1TxSuccessStandard() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setDefault1DiedTBStandard(table1.getDefault1DiedTBStandard() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setDefault1DiedNotTBStandard(table1.getDefault1DiedNotTBStandard() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setDefault1FailedStandard(table1.getDefault1FailedStandard() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setDefault1DefaultedStandard(table1.getDefault1DefaultedStandard() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setDefault1NotAssessedStandard(table1.getDefault1NotAssessedStandard() + 1);
    	
    					}
        	    		
    					else  {
    						table1.setDefault1NotAssessedStandard(table1.getDefault1NotAssessedStandard() + 1);
    	
    					}
        	    	}
        	    	
        	    	
        	    	//DEFAULT2
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterDefault2.conceptId"))) {
            	    	
        	    		table1.setDefault2RegisteredStandard(table1.getDefault2RegisteredStandard() + 1);
        	    		table1.setDefault2TotalStandard(table1.getDefault2TotalStandard() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setDefault2CuredStandard(table1.getDefault2CuredStandard() + 1);
    						table1.setDefault2TxSuccessStandard(table1.getDefault2TxSuccessStandard() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setDefault2CompletedStandard(table1.getDefault2CompletedStandard() + 1);
    						table1.setDefault2TxSuccessStandard(table1.getDefault2TxSuccessStandard() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setDefault2DiedTBStandard(table1.getDefault2DiedTBStandard() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setDefault2DiedNotTBStandard(table1.getDefault2DiedNotTBStandard() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setDefault2FailedStandard(table1.getDefault2FailedStandard() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setDefault2DefaultedStandard(table1.getDefault2DefaultedStandard() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setDefault2NotAssessedStandard(table1.getDefault2NotAssessedStandard() + 1);
    	
    					}
        	    		
    					else  {
    						table1.setDefault2NotAssessedStandard(table1.getDefault2NotAssessedStandard() + 1);
    	
    					}
        	    	}
        	    	
        	    	
        	    	//FAILURE1
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterFailure1.conceptId"))) {
            	    	
        	    		table1.setFailure1RegisteredStandard(table1.getFailure1RegisteredStandard() + 1);
        	    		table1.setFailure1TotalStandard(table1.getFailure1TotalStandard() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setFailure1CuredStandard(table1.getFailure1CuredStandard() + 1);
    						table1.setFailure1TxSuccessStandard(table1.getFailure1TxSuccessStandard() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setFailure1CompletedStandard(table1.getFailure1CompletedStandard() + 1);
    						table1.setFailure1TxSuccessStandard(table1.getFailure1TxSuccessStandard() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setFailure1DiedTBStandard(table1.getFailure1DiedTBStandard() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setFailure1DiedNotTBStandard(table1.getFailure1DiedNotTBStandard() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setFailure1FailedStandard(table1.getFailure1FailedStandard() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setFailure1DefaultedStandard(table1.getFailure1DefaultedStandard() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setFailure1NotAssessedStandard(table1.getFailure1NotAssessedStandard() + 1);
    	
    					}
        	    		
    					else {
    						table1.setFailure1NotAssessedStandard(table1.getFailure1NotAssessedStandard() + 1);
    	
    					}
        	    	}
        	    	
        	    	
        	    	//FAILURE2
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterFailure2.conceptId"))) {
            	    	
        	    		table1.setFailure2RegisteredStandard(table1.getFailure2RegisteredStandard() + 1);
        	    		table1.setFailure2TotalStandard(table1.getFailure2TotalStandard() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setFailure2CuredStandard(table1.getFailure2CuredStandard() + 1);
    						table1.setFailure2TxSuccessStandard(table1.getFailure2TxSuccessStandard() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setFailure2CompletedStandard(table1.getFailure2CompletedStandard() + 1);
    						table1.setFailure2TxSuccessStandard(table1.getFailure2TxSuccessStandard() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setFailure2DiedTBStandard(table1.getFailure2DiedTBStandard() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setFailure2DiedNotTBStandard(table1.getFailure2DiedNotTBStandard() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setFailure2FailedStandard(table1.getFailure2FailedStandard() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setFailure2DefaultedStandard(table1.getFailure2DefaultedStandard() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setFailure2NotAssessedStandard(table1.getFailure2NotAssessedStandard() + 1);
    	
    					}
        	    		
    					else {
    						table1.setFailure2NotAssessedStandard(table1.getFailure2NotAssessedStandard() + 1);
    	
    					}
        	    	}

        	    	//OTHER
        	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.other.conceptId"))) {
            	    	
        	    		table1.setOtherRegisteredStandard(table1.getOtherRegisteredStandard() + 1);
        	    		table1.setOtherTotalStandard(table1.getOtherTotalStandard() + 1);
        	    		
        	    		if(cured!=null && cured) {
    						table1.setOtherCuredStandard(table1.getOtherCuredStandard() + 1);
    						table1.setOtherTxSuccessStandard(table1.getOtherTxSuccessStandard() + 1);
    					}
    					
    					else if (txCompleted!=null && txCompleted) {
    						table1.setOtherCompletedStandard(table1.getOtherCompletedStandard() + 1);
    						table1.setOtherTxSuccessStandard(table1.getOtherTxSuccessStandard() + 1);
    					}
    					
    					else if (diedTB!=null && diedTB) {
    						table1.setOtherDiedTBStandard(table1.getOtherDiedTBStandard() + 1);
    					}
    					
    					else if (diedNotTB!=null && diedNotTB) {
    						table1.setOtherDiedNotTBStandard(table1.getOtherDiedNotTBStandard() + 1);
    					}
    					
    					else if (failed!=null && failed) {
    						table1.setOtherFailedStandard(table1.getOtherFailedStandard() + 1);
    					}
    					
    					else if (defaulted!=null && defaulted) {
    						table1.setOtherDefaultedStandard(table1.getOtherDefaultedStandard() + 1);
    						
    					}
    					
    					else if (transferOut!=null && transferOut) {
    						table1.setOtherNotAssessedStandard(table1.getOtherNotAssessedStandard() + 1);
    					}
        	    		
    					else  {
    						table1.setOtherNotAssessedStandard(table1.getOtherNotAssessedStandard() + 1);
    					}
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
        return "/module/mdrtb/reporting/tb08uResults";
        		//+ "_" + Context.getLocale().toString().substring(0, 2);
    }
}