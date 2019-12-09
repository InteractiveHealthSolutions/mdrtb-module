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
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbConstants;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.form.TB03uForm;
import org.openmrs.module.mdrtb.reporting.DQItem;
import org.openmrs.module.mdrtb.reporting.DQUtil;
import org.openmrs.module.mdrtb.reporting.PDFHelper;
import org.openmrs.module.mdrtb.reporting.ReportUtil;
import org.openmrs.module.mdrtb.reporting.TB03Util;
import org.openmrs.module.mdrtb.reporting.TB03uUtil;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.specimen.Culture;
import org.openmrs.module.mdrtb.specimen.Dst;
import org.openmrs.module.mdrtb.specimen.DstResult;
import org.openmrs.module.mdrtb.specimen.HAIN;
import org.openmrs.module.mdrtb.specimen.Smear;
import org.openmrs.module.mdrtb.specimen.Xpert;


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

public class MDRDQController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
        
    
    @RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/dq")
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
         	districts= Context.getService(MdrtbService.class).getDistricts(Integer.parseInt(oblast));
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
     			districts= Context.getService(MdrtbService.class).getDistricts(Integer.parseInt(oblast));
     			facilities = Context.getService(MdrtbService.class).getFacilities(Integer.parseInt(district));
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
    	 return new ModelAndView("/module/mdrtb/reporting/dq", model);
    }
  
    
    
    @RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/dq")
    public static String doDQ(
    		@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException {
    	
    	//Cohort patients = MdrtbUtil.getMdrPatientsTJK(null, null, location, oblast, null, null, null, null,year,quarter,month);
    	/*Cohort patients = new Cohort();
    	Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);*/
		
    	//String oName = null;
    	
//    	Oblast o = null;
//		if(!oblast.equals("")) {
//			o =  Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblast));
//			oName = o.getName();
//			
//		}
    	
		/*Date startDate = (Date)(dateMap.get("startDate"));
		Date endDate = (Date)(dateMap.get("endDate"));
		
		Form tb03Form = Context.getFormService().getForm(MdrtbConstants.TB03_FORM_ID);
		ArrayList<Form> formList = new ArrayList<Form>();
		formList.add(tb03Form);
    	
    	
    	Set<Integer> idSet = patients.getMemberIds();*/
    	//ArrayList<TB03Data> patientSet  = new ArrayList<TB03Data>();
    	SimpleDateFormat sdf = new SimpleDateFormat();
    	
    	/*ArrayList<Person> patientList = new ArrayList<Person>();
    	ArrayList<Concept> conceptQuestionList = new ArrayList<Concept>();
    	ArrayList<Concept> conceptAnswerList = new ArrayList<Concept>();*/
    	
    	//List<Obs> obsList = null;
    	
    	List<DQItem> missingTB03 = new ArrayList<DQItem>();
    	List<DQItem> missingAge = new ArrayList<DQItem>();
    	List<DQItem> missingPatientGroup = new ArrayList<DQItem>();
    	List<DQItem> missingDST = new ArrayList<DQItem>();
    	List<DQItem> notStartedTreatment = new ArrayList<DQItem>();
    	List<DQItem> missingOutcomes = new ArrayList<DQItem>();
    	//List<DQItem> missingAddress = new ArrayList<DQItem>();
    	List<DQItem> noMDRId = new ArrayList<DQItem>();
    	List<DQItem> noSite = new ArrayList<DQItem>();
    	
    	Boolean errorFlag = Boolean.FALSE;
    	Integer errorCount = 0;
    	
    	Date treatmentStartDate = null;
    	Calendar tCal = null;
    	Calendar nowCal = null;
    	long timeDiff = 0;
    	double diffInWeeks = 0;
    	
    	Smear diagnosticSmear = null;
	    Xpert firstXpert = null;
	    HAIN firstHAIN = null;
	    Culture diagnosticCulture  = null;
	    
    	sdf.applyPattern("dd.MM.yyyy");
    	
    	SimpleDateFormat rdateSDF = new SimpleDateFormat();
    	rdateSDF.applyPattern("dd.MM.yyyy HH:mm:ss");
    	
    	//ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId, districtId, facilityId);
    	
    	ArrayList<Location> locList = null;
    	if(oblastId!=null) {
    		if(oblastId.intValue()==186) {
    			locList = Context.getService(MdrtbService.class).getLocationListForDushanbe(oblastId,districtId,facilityId);
    		}
    		else {
    			locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		}
    	}
		
    	ArrayList<TB03uForm> tb03uList = Context.getService(MdrtbService.class).getTB03uFormsFilled(locList, year, quarter, month);
    	
    	
    	for (TB03uForm tf : tb03uList) {
    		
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
    		
    		
    		DQItem dqi= new DQItem();
    	    Patient patient = tf.getPatient();
    	    
    	    if(patient==null || patient.isVoided()) {
    	    	continue;
    	    }
    	    //patientList.add(patient);
    	    dqi.setPatient(patient);
    	    dqi.setDateOfBirth(sdf.format(patient.getBirthdate()));
    	    

    	   /* //Missing TB03
    	    List<Encounter> tb03EncList = Context.getEncounterService().getEncounters(patient, null, startDate, endDate, formList, null, null, false);
    	    if(tb03EncList==null || tb03EncList.size() == 0) {
    	    	missingTB03.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }*/
    	    
    	    //Missing Age at Registration
    	   /* Concept q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AGE_AT_MDR_REGISTRATION);
    	    
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList==null || obsList.size()==0) {*/
    	    
    	    if(tf.getAgeAtMDRRegistration()==null) {
    	    	missingAge.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    //MISSING REGISTRATION GROUP
    	    //Concept q = tf.getRegistrationGroup();// Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAT_4_CLASSIFICATION_PREVIOUS_TX);
    	   /* conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList==null || obsList.size()==0) {*/
    	    
    	    if(tf.getRegistrationGroup()==null) {
    	    	missingPatientGroup.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    
    	    //NOT STARTED TREATMENT
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TREATMENT_START_DATE);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);*/
    	    if(tf.getMdrTreatmentStartDate()==null) {
    	    	notStartedTreatment.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    else {
    	    	 //MISSING OUTCOMES
    	    	 
    	    		 treatmentStartDate = tf.getMdrTreatmentStartDate();
    	    		 tCal = new GregorianCalendar();
    	    		 tCal.setTime(treatmentStartDate);
    	    		 nowCal = new GregorianCalendar();
    	    		 timeDiff = nowCal.getTimeInMillis() - tCal.getTimeInMillis();
    	    		 diffInWeeks = DQUtil.timeDiffInWeeks(timeDiff);
    	    		 if(diffInWeeks > 96) {
    	    			 
    	    	    	    if(tf.getTreatmentOutcome()==null) {
    	    	    	    	missingOutcomes.add(dqi);
    	    	    	    	errorFlag = Boolean.TRUE;
    	    	    	    }
    	    		 }
    	    		 
    	    	 }
    	    
    	    
    	    //NO SITE
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANATOMICAL_SITE_OF_TB);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList==null || obsList.size()==0) {*/
    	
    		if(tf.getAnatomicalSite()==null) {
    	    	noSite.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    //MISSING DST

    	   
    	   
    	   
    	    if(TB03uUtil.getDiagnosticDST(tf)==null) {
    	    	missingDST.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    
    	    
    	   /* //MISSING DOTS ID
    	    List<PatientIdentifier> ids = patient.getActiveIdentifiers();
    	    Boolean idFound = Boolean.FALSE;
    	    for(PatientIdentifier pi : ids) {
    	    	if(pi.getIdentifierType().getId()==5) 
    	    	{
    	    		idFound = Boolean.TRUE;
    	    		break;
    	    	}
    	    }*/
    	    
    	    if(tf.getPatProgId()!=null) {
    	    	
    	    	if(Context.getService(MdrtbService.class).getPatientProgramIdentifier(Context.getService(MdrtbService.class).getMdrtbPatientProgram(tf.getPatProgId()))==null) {
    	    		noMDRId.add(dqi);
        	    	errorFlag = Boolean.TRUE;
    	    	}
    	    	
    	    }
    	    
    	    else {
    	    	noMDRId.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    
    	    if(errorFlag) {
    	    	errorCount ++;
    	    }

    	    
    	}
    	
    	Integer num = tb03uList.size();
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
    	//model.addAttribute("missingTB03", missingTB03);
    	model.addAttribute("missingAge", missingAge);
    	model.addAttribute("missingPatientGroup", missingPatientGroup);
    	model.addAttribute("missingDST", missingDST);
    	model.addAttribute("notStartedTreatment", notStartedTreatment);
    	model.addAttribute("missingOutcomes", missingOutcomes);
    	model.addAttribute("noMDRId", noMDRId);
    	model.addAttribute("noSite", noSite);
    	model.addAttribute("errorCount", new Integer(errorCount));
    	model.addAttribute("errorPercentage", errorPercentage.toString() + "%");
    	model.addAttribute("oblastName", oName);
    	model.addAttribute("oName", oName);
    	model.addAttribute("dName", dName);
    	model.addAttribute("fName", fName);
    	
    	
    	
    	model.addAttribute("locale", Context.getLocale().toString());
    	
    	
    	
    	// TO CHECK WHETHER REPORT IS CLOSED OR NOT
    //	Integer report_oblast = null; Integer report_quarter = null; Integer report_month = null;
		/*if(new PDFHelper().isInt(oblast)) { report_oblast = Integer.parseInt(oblast); }
		if(new PDFHelper().isInt(quarter)) { report_quarter = Integer.parseInt(quarter); }
		if(new PDFHelper().isInt(month)) { report_month = Integer.parseInt(month); }*/
		
    	boolean reportStatus = Context.getService(MdrtbService.class).readReportStatus(oblastId, districtId, facilityId, year, quarter, month, "DQ","MDRTB");
		System.out.println(reportStatus);
		
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
        return "/module/mdrtb/reporting/dqResults";
    }


	/*@RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/dq")
	    public static String doDQ(
	    		@RequestParam("district") Integer districtId,
	    		@RequestParam("oblast") Integer oblastId,
	    		@RequestParam("facility") Integer facilityId,
	            @RequestParam(value="year", required=true) Integer year,
	            @RequestParam(value="quarter", required=false) String quarter,
	            @RequestParam(value="month", required=false) String month,
	            ModelMap model) throws EvaluationException {
	    	
	    	//Cohort patients = MdrtbUtil.getMdrPatientsTJK(null, null, location, oblast, null, null, null, null,year,quarter,month);
	    	Cohort patients = new Cohort();
	    	Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
			
	    	String oName = null;
	    	
	//    	Oblast o = null;
	//		if(!oblast.equals("")) {
	//			o =  Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblast));
	//			oName = o.getName();
	//			
	//		}
	    	
			Date startDate = (Date)(dateMap.get("startDate"));
			Date endDate = (Date)(dateMap.get("endDate"));
			
			Form tb03Form = Context.getFormService().getForm(MdrtbConstants.TB03_FORM_ID);
			ArrayList<Form> formList = new ArrayList<Form>();
			formList.add(tb03Form);
	    	
	    	
	    	Set<Integer> idSet = patients.getMemberIds();
	    	//ArrayList<TB03Data> patientSet  = new ArrayList<TB03Data>();
	    	SimpleDateFormat sdf = new SimpleDateFormat();
	    	
	    	ArrayList<Person> patientList = new ArrayList<Person>();
	    	ArrayList<Concept> conceptQuestionList = new ArrayList<Concept>();
	    	ArrayList<Concept> conceptAnswerList = new ArrayList<Concept>();
	    	
	    	//List<Obs> obsList = null;
	    	
	    	List<DQItem> missingTB03 = new ArrayList<DQItem>();
	    	List<DQItem> missingAge = new ArrayList<DQItem>();
	    	List<DQItem> missingPatientGroup = new ArrayList<DQItem>();
	    	List<DQItem> missingDST = new ArrayList<DQItem>();
	    	List<DQItem> notStartedTreatment = new ArrayList<DQItem>();
	    	List<DQItem> missingOutcomes = new ArrayList<DQItem>();
	    	//List<DQItem> missingAddress = new ArrayList<DQItem>();
	    	List<DQItem> noMDRId = new ArrayList<DQItem>();
	    	List<DQItem> noSite = new ArrayList<DQItem>();
	    	
	    	Boolean errorFlag = Boolean.FALSE;
	    	Integer errorCount = 0;
	    	
	    	Date treatmentStartDate = null;
	    	Calendar tCal = null;
	    	Calendar nowCal = null;
	    	long timeDiff = 0;
	    	double diffInWeeks = 0;
	    	
	    	Smear diagnosticSmear = null;
		    Xpert firstXpert = null;
		    HAIN firstHAIN = null;
		    Culture diagnosticCulture  = null;
		    
	    	sdf.applyPattern("dd.MM.yyyy");
	    	
	    	ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId, districtId, facilityId);
	    	ArrayList<TB03uForm> tb03uList = Context.getService(MdrtbService.class).getTB03uFormsFilled(locList, year, quarter, month);
	    	
	    	
	    	for (TB03uForm tf : tb03uList) {
	    		
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
	    		
	    		
	    		DQItem dqi= new DQItem();
	    	    Patient patient = tf.getPatient();
	    	    
	    	    if(patient==null) {
	    	    	continue;
	    	    }
	    	    //patientList.add(patient);
	    	    dqi.setPatient(patient);
	    	    dqi.setDateOfBirth(sdf.format(patient.getBirthdate()));
	    	    
	
	    	    //Missing TB03
	    	    List<Encounter> tb03EncList = Context.getEncounterService().getEncounters(patient, null, startDate, endDate, formList, null, null, false);
	    	    if(tb03EncList==null || tb03EncList.size() == 0) {
	    	    	missingTB03.add(dqi);
	    	    	errorFlag = Boolean.TRUE;
	    	    }
	    	    
	    	    //Missing Age at Registration
	    	    Concept q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AGE_AT_MDR_REGISTRATION);
	    	    
	    	    conceptQuestionList.add(q);
	    	    
	    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
	    	    if(obsList==null || obsList.size()==0) {
	    	    
	    	    if(tf.getAgeAtMDRRegistration()==null) {
	    	    	missingAge.add(dqi);
	    	    	errorFlag = Boolean.TRUE;
	    	    }
	    	    //MISSING REGISTRATION GROUP
	    	    //Concept q = tf.getRegistrationGroup();// Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAT_4_CLASSIFICATION_PREVIOUS_TX);
	    	    conceptQuestionList.clear();
	    	    conceptQuestionList.add(q);
	    	    
	    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
	    	    if(obsList==null || obsList.size()==0) {
	    	    
	    	    if(tf.getRegistrationGroup()==null) {
	    	    	missingPatientGroup.add(dqi);
	    	    	errorFlag = Boolean.TRUE;
	    	    }
	    	    
	    	    //NOT STARTED TREATMENT
	    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TREATMENT_START_DATE);
	    	    conceptQuestionList.clear();
	    	    conceptQuestionList.add(q);
	    	    
	    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
	    	    if(tf.getMdrTreatmentStartDate()==null) {
	    	    	notStartedTreatment.add(dqi);
	    	    	errorFlag = Boolean.TRUE;
	    	    }
	    	    else {
	    	    	 //MISSING OUTCOMES
	    	    	 
	    	    		 treatmentStartDate = tf.getMdrTreatmentStartDate();
	    	    		 tCal = new GregorianCalendar();
	    	    		 tCal.setTime(treatmentStartDate);
	    	    		 nowCal = new GregorianCalendar();
	    	    		 timeDiff = nowCal.getTimeInMillis() - tCal.getTimeInMillis();
	    	    		 diffInWeeks = DQUtil.timeDiffInWeeks(timeDiff);
	    	    		 if(diffInWeeks > 96) {
	    	    			 
	    	    	    	    if(tf.getTreatmentOutcome()==null) {
	    	    	    	    	missingOutcomes.add(dqi);
	    	    	    	    	errorFlag = Boolean.TRUE;
	    	    	    	    }
	    	    		 }
	    	    		 
	    	    	 }
	    	    
	    	    
	    	    //NO SITE
	    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANATOMICAL_SITE_OF_TB);
	    	    conceptQuestionList.clear();
	    	    conceptQuestionList.add(q);
	    	    
	    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
	    	    if(obsList==null || obsList.size()==0) {
	    	
	    		if(tf.getAnatomicalSite()==null) {
	    	    	noSite.add(dqi);
	    	    	errorFlag = Boolean.TRUE;
	    	    }
	    	    //MISSING DST
	
	    	   
	    	   
	    	   
	    	    if(TB03uUtil.getDiagnosticDST(tf)==null) {
	    	    	missingDST.add(dqi);
	    	    	errorFlag = Boolean.TRUE;
	    	    }
	    	    
	    	    
	    	    //MISSING DOTS ID
	    	    List<PatientIdentifier> ids = patient.getActiveIdentifiers();
	    	    Boolean idFound = Boolean.FALSE;
	    	    for(PatientIdentifier pi : ids) {
	    	    	if(pi.getIdentifierType().getId()==5) 
	    	    	{
	    	    		idFound = Boolean.TRUE;
	    	    		break;
	    	    	}
	    	    }
	    	    
	    	    if(tf.getPatProgId()!=null) {
	    	    	
	    	    	if(Context.getService(MdrtbService.class).getPatientProgramIdentifier(Context.getService(MdrtbService.class).getMdrtbPatientProgram(tf.getPatProgId()))==null) {
	    	    		noMDRId.add(dqi);
	        	    	errorFlag = Boolean.TRUE;
	    	    	}
	    	    	
	    	    }
	    	    
	    	    else {
	    	    	noMDRId.add(dqi);
	    	    	errorFlag = Boolean.TRUE;
	    	    }
	    	    
	    	    if(errorFlag) {
	    	    	errorCount ++;
	    	    }
	
	    	    
	    	}
	    	
	    	Integer num = tb03uList.size();
	    	Integer errorPercentage = null;
	    	if(num==0)
	    		errorPercentage = 0;
	    	else
	    		errorPercentage = (errorCount*100)/num;
	    	
	    	
	    	model.addAttribute("num", num);
	    	model.addAttribute("missingTB03", missingTB03);
	    	model.addAttribute("missingAge", missingAge);
	    	model.addAttribute("missingPatientGroup", missingPatientGroup);
	    	model.addAttribute("missingDST", missingDST);
	    	model.addAttribute("notStartedTreatment", notStartedTreatment);
	    	model.addAttribute("missingOutcomes", missingOutcomes);
	    	model.addAttribute("noMDRId", noMDRId);
	    	model.addAttribute("noSite", noSite);
	    	model.addAttribute("errorCount", new Integer(errorCount));
	    	model.addAttribute("errorPercentage", errorPercentage.toString() + "%");
	    	model.addAttribute("oblastName", oName);
	    	
	    	
	    	
	    	
	    	model.addAttribute("locale", Context.getLocale().toString());
	    	
	    	
	    	
	    	// TO CHECK WHETHER REPORT IS CLOSED OR NOT
	    //	Integer report_oblast = null; Integer report_quarter = null; Integer report_month = null;
			if(new PDFHelper().isInt(oblast)) { report_oblast = Integer.parseInt(oblast); }
			if(new PDFHelper().isInt(quarter)) { report_quarter = Integer.parseInt(quarter); }
			if(new PDFHelper().isInt(month)) { report_month = Integer.parseInt(month); }
			
	    	boolean reportStatus = Context.getService(MdrtbService.class).readReportStatus(oblastId, districtId, facilityId, year, quarter, month, "DQ","MDRTB");
			System.out.println(reportStatus);
			
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
	    	model.addAttribute("reportDate", sdf.format(new Date()));
	    	model.addAttribute("reportStatus", reportStatus);
	        return "/module/mdrtb/reporting/dqResults";
	    }*/
    
    
  
    
}
