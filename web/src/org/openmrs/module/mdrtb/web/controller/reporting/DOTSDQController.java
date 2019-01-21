package org.openmrs.module.mdrtb.web.controller.reporting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.MdrtbConstants;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.form.CultureForm;
import org.openmrs.module.mdrtb.form.Form89;
import org.openmrs.module.mdrtb.form.HAINForm;
import org.openmrs.module.mdrtb.form.SmearForm;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.form.TransferInForm;
import org.openmrs.module.mdrtb.form.TransferOutForm;
import org.openmrs.module.mdrtb.form.XpertForm;
import org.openmrs.module.mdrtb.reporting.DQItem;
import org.openmrs.module.mdrtb.reporting.DQUtil;
import org.openmrs.module.mdrtb.reporting.PDFHelper;
import org.openmrs.module.mdrtb.reporting.ReportUtil;
import org.openmrs.module.mdrtb.reporting.TB03Data;
import org.openmrs.module.mdrtb.reporting.TB03Util;
import org.openmrs.module.mdrtb.reporting.data.Cohorts;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.specimen.Culture;
import org.openmrs.module.mdrtb.specimen.HAIN;
import org.openmrs.module.mdrtb.specimen.Smear;
import org.openmrs.module.mdrtb.specimen.Xpert;
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
/*import org.openmrs.module.mdrtbdrugforecast.DrugCount;
import org.openmrs.module.mdrtbdrugforecast.MdrtbDrugStock;
import org.openmrs.module.mdrtbdrugforecast.MdrtbUtil;
import org.openmrs.module.mdrtbdrugforecast.MdrtbConcepts;
import org.openmrs.module.mdrtbdrugforecast.drugneeds.DrugForecastUtil;
import org.openmrs.module.mdrtbdrugforecast.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtbdrugforecast.regimen.Regimen;
import org.openmrs.module.mdrtbdrugforecast.regimen.RegimenUtils;
import org.openmrs.module.mdrtbdrugforecast.reporting.definition.MdrtbDrugForecastTreatmentStartedCohortDefinition;
import org.openmrs.module.mdrtbdrugforecast.reporting.definition.MdrtbDrugForecastTreatmentStartedOnDrugCohortDefinition;
import org.openmrs.module.mdrtbdrugforecast.service.MdrtbDrugForecastService;
import org.openmrs.module.mdrtbdrugforecast.status.TreatmentStatusCalculator;
import org.openmrs.module.mdrtbdrugforecast.web.controller.status.DashboardTreatmentStatusRenderer;*/
import org.springframework.web.servlet.ModelAndView;

@Controller

public class DOTSDQController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
        
    
    @RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/dotsdq")
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
    		//DUSHANBE
    		if(Integer.parseInt(oblast)==186) {
    			oblasts = Context.getService(MdrtbService.class).getOblasts();
    			districts= Context.getService(MdrtbService.class).getDistricts(Integer.parseInt(oblast));
    			District d = districts.get(0);
    			facilities = Context.getService(MdrtbService.class).getFacilities(d.getId());
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
    	
         else
         {
        	 /*
      		 * if oblast is dushanbe, return both districts and facilities
      		 */
     		if(Integer.parseInt(oblast)==186) {
     			oblasts = Context.getService(MdrtbService.class).getOblasts();
     			districts= Context.getService(MdrtbService.class).getDistricts(Integer.parseInt(oblast));
     			District d = districts.get(0);
     			facilities = Context.getService(MdrtbService.class).getFacilities(d.getId());
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
       
        /*List<Location> locations = Context.getLocationService().getAllLocations(false);// Context.getLocationService().getAllLocations();//ms = (MdrtbDrugForecastService) Context.getService(MdrtbDrugForecastService.class);
        List<Oblast> oblasts = Context.getService(MdrtbService.class).getOblasts();
        //drugSets =  ms.getMdrtbDrugs();
        
       

        model.addAttribute("locations", locations);
        model.addAttribute("oblasts", oblasts);*/
    	 return new ModelAndView("/module/mdrtb/reporting/dotsdq", model);
    
    }
    
    
    @SuppressWarnings("unused")
	@RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/dotsdq")
    public static String doDQ(
    		@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException {
    	
    //	System.out.println("---POST-----");
    //	System.out.println("PARAMS:" + location + " " + oblast + " " + year + " " + quarter + " " + month);
    	
    	//Cohort patients = TbUtil.getDOTSPatientsTJK(null, null, location, oblast, null, null, null, null,year,quarter,month);
    	//Cohort patients = 
    	
    	/*Oblast o = null;
    	if(oblast!=null && !oblast.equals("") && location == null)
			o =  Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblast));
    	String oName = "";
    	if(o!=null) {
    		oName = o.getName();
    	}
		
		List<Location> locList = new ArrayList<Location>();
		if(o != null && location == null)
			locList = Context.getService(MdrtbService.class).getLocationsFromOblastName(o);
		else if (location != null)
			locList.add(location);
		
		Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
		
		Date startDate = (Date)(dateMap.get("startDate"));
		Date endDate = (Date)(dateMap.get("endDate"));
		
		System.out.println("ST: " + startDate);
		System.out.println("ED: " + endDate);
		
		CohortDefinition baseCohort = null;
		
		//OBLAST
		if (!locList.isEmpty()){
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
 
		Form tb03Form = Context.getFormService().getForm(MdrtbConstants.TB03_FORM_ID);
		ArrayList<Form> formList = new ArrayList<Form>();
		formList.add(tb03Form);
    	
    	
    	Set<Integer> idSet = patients.getMemberIds();
    	ArrayList<TB03Data> patientSet  = new ArrayList<TB03Data>();*/
    	SimpleDateFormat sdf = new SimpleDateFormat();
    	
    	/*ArrayList<Person> patientList = new ArrayList<Person>();
    	ArrayList<Concept> conceptQuestionList = new ArrayList<Concept>();
    	ArrayList<Concept> conceptAnswerList = new ArrayList<Concept>();
    	
    	List<Obs> obsList = null;*/
    	
    	List<DQItem> missingTB03 = new ArrayList<DQItem>();
    	List<DQItem> missingAge = new ArrayList<DQItem>();
    	List<DQItem> missingPatientGroup = new ArrayList<DQItem>();
    	List<DQItem> noForm89 = new ArrayList<DQItem>();
    	List<DQItem> missingDiagnosticTests = new ArrayList<DQItem>();
    	List<DQItem> notStartedTreatment = new ArrayList<DQItem>();
    	List<DQItem> missingOutcomes = new ArrayList<DQItem>();
    	//List<DQItem> missingAddress = new ArrayList<DQItem>();
    	List<DQItem> noDOTSId = new ArrayList<DQItem>();
    	List<DQItem> noSite = new ArrayList<DQItem>();
    	List<DQItem> noTifAfterTransferOut = new ArrayList<DQItem>();
    	List<DQItem> noTofBeforeTransferIn = new ArrayList<DQItem>();
    	List<DQItem> duplicateTB03 = new ArrayList<DQItem>();
    	List<DQItem> unlinkedTB03 = new ArrayList<DQItem>();
    	
    	Boolean errorFlag = Boolean.FALSE;
    	Integer errorCount = 0;
    	
    	Date treatmentStartDate = null;
    	Calendar tCal = null;
    	Calendar nowCal = null;
    	long timeDiff = 0;
    	double diffInWeeks = 0;
    	
    	SmearForm diagnosticSmear = null;
	    XpertForm firstXpert = null;
	    HAINForm firstHAIN = null;
	    CultureForm diagnosticCulture  = null;
	    Boolean eptb = Boolean.FALSE;
	    Boolean child = Boolean.FALSE;
	    DQItem dqi = null;
	    
	    Integer eptbConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.EXTRA_PULMONARY_TB).getConceptId();
	    
    	sdf.applyPattern("dd.MM.yyyy");
    	SimpleDateFormat rdateSDF = new SimpleDateFormat();
    	rdateSDF.applyPattern("dd.MM.yyyy HH:mm:ss");
    	
    	//ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId, districtId, facilityId);
    	
    	ArrayList<Location> locList = null;
		if(oblastId.intValue()==186) {
			locList = Context.getService(MdrtbService.class).getLocationListForDushanbe(oblastId,districtId,facilityId);
		}
		else {
			locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
		}
    	
    	ArrayList<TB03Form> tb03List = Context.getService(MdrtbService.class).getTB03FormsFilled(locList, year, quarter, month);
    	ArrayList<TransferOutForm> tofList = Context.getService(MdrtbService.class).getTransferOutFormsFilled(locList, year, quarter, month);
    	ArrayList<TransferInForm> tifList = Context.getService(MdrtbService.class).getTransferInFormsFilled(locList, year, quarter, month);
    	ArrayList<TransferOutForm> allTofs = null;// Context.getService(MdrtbService.class).getTransferOutFormsFilled(locList, year, quarter, month);
    	ArrayList<TransferInForm> allTifs = null;// Context.getService(MdrtbService.class).getTransferInFormsFilled(locList, year, quarter, month);
    	
    	for (TB03Form  tf : tb03List) {
    		
    		 //INIT
    	    treatmentStartDate = null;
    		tCal = null;
    		nowCal = null;
    		timeDiff = 0;
    		diffInWeeks = 0;
    		diagnosticSmear = null;
    	    firstXpert = null;
    	    firstHAIN = null;
    	    diagnosticCulture  = null;
    	   // patientList.clear();
    	    errorFlag = Boolean.FALSE;
    	    eptb = Boolean.FALSE;
    	    child = Boolean.FALSE;
    		
    		dqi = new DQItem();
    	    Patient patient = tf.getPatient();//Context.getPatientService().getPatient(i);
    	    
    	    if(patient==null || patient.isVoided()) {
    	    	continue;
    	    }
    	    
    	    if(patient.getGender().equals("F") && Context.getLocale().equals("ru")) {
    	    	patient.setGender(Context.getMessageSourceService().getMessage("mdrtb.tb03.gender.female"));
    	    }
    	   // patientList.add(patient);
    	    dqi.setPatient(patient);
    	    dqi.setDateOfBirth(sdf.format(patient.getBirthdate()));
    	    

    	   /* //Missing TB03
    	    List<Encounter> tb03EncList = Context.getEncounterService().getEncounters(patient, null, startDate, endDate, formList, null, null, false);
    	    if(tb03EncList==null || tb03EncList.size() == 0) {
    	    	missingTB03.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }*/
    	    
    	    //Missing Age at Registration
    	    /*Concept q = Context.getService(MdrtbService.class).getConcept(TbConcepts.AGE_AT_DOTS_REGISTRATION);
    	    
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);*/
    	    
    	    //DUPLICATE TB03
    	    Integer patProgId   = null;
    	    
    	    patProgId = tf.getPatProgId();
    	    Boolean found = Boolean.FALSE;
    	    if(patProgId!=null) {
    	    	List<TB03Form> dupList = Context.getService(MdrtbService.class).getTB03FormsForProgram(patient, patProgId);
    	    	
    	    	if(dupList!=null) {
    	    		
    	    		if(dupList.size() > 1) {
    	    			for(TB03Form form : dupList) {
    	    				if(form.getPatProgId().intValue()==patProgId.intValue()) {
    	    					dqi.addLink(form.getLink());
    	    					found = Boolean.TRUE;
    	    					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>FOUND:" + tf.getPatProgId());
    	    				}
    	    			}
    	    		}
    	    	}
    	    	
    	    	if(found) {
    	    		errorFlag = Boolean.TRUE;
    	    		duplicateTB03.add(dqi);
    	    	}
    	    }
    	    
    	    else { //UNLINKED TB03
    	    	String link = "";
    	    	dqi.addLink(link);
    	    	unlinkedTB03.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    
    	    
    	    
    	    
    	    
    	    if(tf.getAgeAtTB03Registration()==null) { //obsList==null || obsList.size()==0) {
    	    	missingAge.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    //MISSING REGISTRATION GROUP
    	   /* q = Context.getService(MdrtbService.class).getConcept(TbConcepts.PATIENT_GROUP);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList==null || obsList.size()==0) {*/
    	    
    	    if(tf.getRegistrationGroup()==null) {
    	    	missingPatientGroup.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    
    	    
    	    
    	    else if(tf.getRegistrationGroup().getId().intValue()== (Context.getService(MdrtbService.class).getConcept(TbConcepts.NEW).getId().intValue())) {
    	    	ArrayList<Form89> f89 = Context.getService(MdrtbService.class).getForm89FormsFilledForPatientProgram(tf.getPatient(), null, tf.getPatProgId(), year, quarter, month);
    	    	if(f89==null || f89.size()==0) {
    	    		noForm89.add(dqi);
    	    		errorFlag = Boolean.TRUE;
    	    	}
    	    }
    	    
    	    //NOT STARTED TREATMENT
    	   /* q = Context.getService(MdrtbService.class).getConcept(TbConcepts.DOTS_TREATMENT_START_DATE);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList==null || obsList.size()==0) {*/
    	    
    	    if(tf.getTreatmentStartDate()==null) {
    	    	notStartedTreatment.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    else {
    	    	 //MISSING OUTCOMES
    	    	
    	    		 treatmentStartDate = tf.getTreatmentStartDate();
    	    		 tCal = new GregorianCalendar();
    	    		 tCal.setTime(treatmentStartDate);
    	    		 nowCal = new GregorianCalendar();
    	    		 timeDiff = nowCal.getTimeInMillis() - tCal.getTimeInMillis();
    	    		 diffInWeeks = DQUtil.timeDiffInWeeks(timeDiff);
    	    		 if(diffInWeeks > 32) {
    	    			 
    	    	    	    if(tf.getTreatmentOutcome()==null) {
    	    	    	    	missingOutcomes.add(dqi);
    	    	    	    	errorFlag = Boolean.TRUE;
    	    	    	    }
    	    		 } 
    	    	 }
    	    
    	  /*  if(tf.getTreatmentStartDate()==null) {
    	    	notStartedTreatment.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    else {
    	    	 //MISSING OUTCOMES
    	    	
    	    		 treatmentStartDate = tf.getTreatmentStartDate();
    	    		 tCal = new GregorianCalendar();
    	    		 tCal.setTime(treatmentStartDate);
    	    		 nowCal = new GregorianCalendar();
    	    		 timeDiff = nowCal.getTimeInMillis() - tCal.getTimeInMillis();
    	    		 diffInWeeks = DQUtil.timeDiffInWeeks(timeDiff);
    	    		 if(diffInWeeks > 32) {
    	    			 
    	    	    	    if(tf.getTreatmentOutcome()==null) {
    	    	    	    	missingOutcomes.add(dqi);
    	    	    	    	errorFlag = Boolean.TRUE;
    	    	    	    }
    	    		 } 
    	    	 }*/
    	    //NO SITE
    	    
    	   /* q = Context.getService(MdrtbService.class).getConcept(TbConcepts.ANATOMICAL_SITE_OF_TB);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList==null || obsList.size()==0) {*/
    	     if(tf.getAnatomicalSite()==null) {
    	    	noSite.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    
    	    else {
    	    	
    	    	
    	    	if(tf.getAnatomicalSite().getConceptId().intValue()==eptbConcept.intValue())
    	    	{
    	    		
    	    		eptb = Boolean.TRUE;
    	    	}
    	    }
    	    
    	    
    	    
    	   /* q = Context.getService(MdrtbService.class).getConcept(TbConcepts.AGE_AT_DOTS_REGISTRATION);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList!=null && obsList.size()!=0) {*/
    	     if(tf.getAgeAtTB03Registration()!=null) {
    	    	Integer age =  tf.getAgeAtTB03Registration();
    	    	
    	    	if(age > 14)
    	    		child = Boolean.FALSE;
    	    	else
    	    		child = Boolean.TRUE;
    	    }
    	    
    	    
    	    //MISSING DIAGNOSTIC TESTS

    	    
    	    diagnosticSmear = TB03Util.getDiagnosticSmearForm(tf);
    	    firstXpert = TB03Util.getFirstXpertForm(tf);
    	    firstHAIN = TB03Util.getFirstHAINForm(tf);
    	    diagnosticCulture  = TB03Util.getDiagnosticCultureForm(tf);
    	    
    	   
    	    if(diagnosticSmear == null && diagnosticCulture == null && firstXpert==null && firstHAIN == null && eptb == Boolean.FALSE && child ==Boolean.FALSE) {
    	    	
    	    	missingDiagnosticTests.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    
    	    
    	    //MISSING DOTS ID
    	   /* List<PatientIdentifier> ids = patient.getActiveIdentifiers();
    	    Boolean idFound = Boolean.FALSE;
    	    for(PatientIdentifier pi : ids) {
    	    	if(pi.getIdentifierType().getId()==2) 
    	    	{
    	    		idFound = Boolean.TRUE;
    	    		break;
    	    	}
    	    }
    	    
    	    if(!idFound) {
    	    	noDOTSId.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }*/
    	    
    	    if(tf.getPatProgId()!=null) {
    	    	
    	    	if(Context.getService(MdrtbService.class).getGenPatientProgramIdentifier(Context.getService(MdrtbService.class).getTbPatientProgram(tf.getPatProgId()).getPatientProgram())==null) {
    	    		noDOTSId.add(dqi);
        	    	errorFlag = Boolean.TRUE;
    	    	}
    	    	
    	    }
    	    
    	    
    	    
    	    if(errorFlag) {
    	    	errorCount ++;
    	    }

    	    
    	}
    	
    	  //TRANSFER OUT BUT NO TRANSFER IN
	    //get latest transfer out with any of these locations for any patient
	    //if no transferIn in list entered after that date for patient add error
    	Boolean foundFlag = Boolean.FALSE;
    	
    	for(TransferOutForm tof : tofList) {
    		Location tofLoc = tof.getLocation();
    		Date tofDate = tof.getEncounterDatetime();
    		Patient tofPatient = tof.getPatient();
    		dqi = new DQItem();
    	    Patient patient = tof.getPatient();//Context.getPatientService().getPatient(i);
    	    
    	    if(patient==null || patient.isVoided()) {
    	    	continue;
    	    }
    	   // patientList.add(patient);
    	    dqi.setPatient(patient);
    	    dqi.setLocName(tofLoc.getDisplayString());
    	    dqi.setDateOfBirth(sdf.format(patient.getBirthdate()));
    		foundFlag = Boolean.FALSE;
    		errorFlag = Boolean.FALSE;
    		allTifs = Context.getService(MdrtbService.class).getTransferInFormsFilledForPatient(patient);
    		for(TransferInForm tif : allTifs) {
    			if(tofLoc.equals(tif.getLocation()) && tofPatient.equals(tif.getPatient())) {
    				if(tif.getEncounterDatetime().after(tofDate)) {
    					foundFlag = Boolean.TRUE;
    					break;
    				}
    			}
    		}
    		
    		if(!foundFlag) {
    			
    			errorCount++;
    			noTifAfterTransferOut.add(dqi);
    			
    			
    		}
    	}
    	
    	
    	  //TRANSFER In  BUT NO TRANSFER Out
	    //get latest transfer out with any of these locations for any patient
	    //if no transferIn in list entered after that date for patient add error
    	foundFlag = Boolean.FALSE;
    	
    	for(TransferInForm tif : tifList) {
    		Location tifLoc = tif.getLocation();
    		Date tifDate = tif.getEncounterDatetime();
    		Patient tifPatient = tif.getPatient();
    		dqi = new DQItem();
    	    Patient patient = tif.getPatient();//Context.getPatientService().getPatient(i);
    	    
    	    if(patient==null || patient.isVoided()) {
    	    	continue;
    	    }
    	   // patientList.add(patient);
    	    dqi.setPatient(patient);
    	    dqi.setLocName(tifLoc.getDisplayString());
    	    dqi.setDateOfBirth(sdf.format(patient.getBirthdate()));
    		foundFlag = Boolean.FALSE;
    		errorFlag = Boolean.FALSE;
    		allTofs = Context.getService(MdrtbService.class).getTransferOutFormsFilledForPatient(patient);
    		for(TransferOutForm tof : tofList) {
    			if(tifLoc.equals(tof.getLocation()) && tifPatient.equals(tof.getPatient())) {
    				if(tof.getEncounterDatetime().before(tifDate)) {
    					foundFlag = Boolean.TRUE;
    					break;
    				}
    			}
    		}
    		
    		if(!foundFlag) {
    			
    			errorCount++;
    			noTofBeforeTransferIn.add(dqi);
    			
    			
    		}
    	}
    	
    	Integer num = tb03List.size();// + tofList.size();
    	Integer errorPercentage = null;
    	if(num==0)
    		errorPercentage = 0;
    	else
    		errorPercentage = (errorCount*100)/num;
    	
    	String oName = null;
    	Oblast obl = Context.getService(MdrtbService.class).getOblast(oblastId);
    	if(obl!=null)
    		oName = obl.getName();
    	
    	String dName = null;
    	if(districtId!=null) {
    		District dist = Context.getService(MdrtbService.class).getDistrict(districtId);
    		if(dist!=null)
    			dName = dist.getName();
    	}
    	
    	String fName = null;
    	if(facilityId!=null) {
    		Facility fac = Context.getService(MdrtbService.class).getFacility(facilityId);
    		if(fac!=null)
    			fName = fac.getName();
    	}
    	
    	model.addAttribute("num", num);
    	model.addAttribute("missingTB03", missingTB03);
    	model.addAttribute("duplicateTB03", duplicateTB03);
    	model.addAttribute("unlinkedTB03", unlinkedTB03);
    	model.addAttribute("missingForm89", noForm89);
    	model.addAttribute("missingAge", missingAge);
    	model.addAttribute("missingPatientGroup", missingPatientGroup);
    	model.addAttribute("missingDiagnosticTests", missingDiagnosticTests);
    	model.addAttribute("notStartedTreatment", notStartedTreatment);
    	model.addAttribute("missingOutcomes", missingOutcomes);
    	model.addAttribute("noDOTSId", noDOTSId);
    	model.addAttribute("noSite", noSite);
    	model.addAttribute("noTrasnferIn", noTifAfterTransferOut);
    	model.addAttribute("noTransferOut", noTofBeforeTransferIn);
    	model.addAttribute("errorCount", new Integer(errorCount));
    	model.addAttribute("errorPercentage", errorPercentage.toString() + "%");
    	model.addAttribute("oName", oName);
    	model.addAttribute("dName", dName);
    	model.addAttribute("fName", fName);
    	
    	
    	
    	
    	model.addAttribute("locale", Context.getLocale().toString());
    	
    	// TO CHECK WHETHER REPORT IS CLOSED OR NOT
    	/*Integer report_oblast = null; Integer report_quarter = null; Integer report_month = null;
		if(new PDFHelper().isInt(oblast)) { report_oblast = Integer.parseInt(oblast); }
		if(new PDFHelper().isInt(quarter)) { report_quarter = Integer.parseInt(quarter); }
		if(new PDFHelper().isInt(month)) { report_month = Integer.parseInt(month); }*/
		
    	boolean reportStatus = Context.getService(MdrtbService.class).readReportStatus(oblastId, districtId, facilityId, year, quarter, month, "DOTSDQ", "DOTSTB");
    	
    	model.addAttribute("oblast", oblastId);
    	model.addAttribute("facility", facilityId);
    	model.addAttribute("district", districtId);
    	model.addAttribute("year", year);
    	if(month!=null && month.length()!=0)
			model.addAttribute("month", month.replace("\"", ""));
		else
			model.addAttribute("month", "");
		
		if(quarter!=null && quarter.length()!=0)
			model.addAttribute("quarter", quarter.replace("\"", "'"));
		else
			model.addAttribute("quarter", "");
    	model.addAttribute("reportDate", rdateSDF.format(new Date()));
    	model.addAttribute("reportStatus", reportStatus);
        return "/module/mdrtb/reporting/dotsdqResults";
    }
    
    
  
    
}
