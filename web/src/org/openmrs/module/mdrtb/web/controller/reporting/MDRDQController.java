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


@Controller

public class MDRDQController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
        
    
    @RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/dq")
    public void showRegimenOptions(ModelMap model) {
    	
    	
    
       
        List<Location> locations = Context.getLocationService().getAllLocations(false);// Context.getService(MdrtbService.class).getEnrollmentLocations();//ms = (MdrtbDrugForecastService) Context.getService(MdrtbDrugForecastService.class);
        List<Oblast> oblasts = Context.getService(MdrtbService.class).getOblasts();
        //drugSets =  ms.getMdrtbDrugs();
        
       

        model.addAttribute("locations", locations);
        model.addAttribute("oblasts", oblasts);
      
    	
    }
    
  
    
    
    @RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/dq")
    public static String doDQ(
    		@RequestParam("facility") Integer facilityId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("district") Integer districtId,
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
    	
    	List<Obs> obsList = null;
    	
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
    	for (Integer i : idSet) {
    		
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
    	    patientList.clear();
    	    errorFlag = Boolean.FALSE;
    		
    		
    		DQItem dqi= new DQItem();
    	    Patient patient = Context.getPatientService().getPatient(i);
    	    
    	    if(patient==null) {
    	    	continue;
    	    }
    	    patientList.add(patient);
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
    	    	missingAge.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    //MISSING REGISTRATION GROUP
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAT_4_CLASSIFICATION_PREVIOUS_TX);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList==null || obsList.size()==0) {
    	    	missingPatientGroup.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    
    	    //NOT STARTED TREATMENT
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TREATMENT_START_DATE);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList==null || obsList.size()==0) {
    	    	notStartedTreatment.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    else {
    	    	 //MISSING OUTCOMES
    	    	 if(obsList.size()>0 && obsList.get(0)!=null) {
    	    		 treatmentStartDate = obsList.get(0).getValueDatetime();
    	    		 tCal = new GregorianCalendar();
    	    		 tCal.setTime(treatmentStartDate);
    	    		 nowCal = new GregorianCalendar();
    	    		 timeDiff = nowCal.getTimeInMillis() - tCal.getTimeInMillis();
    	    		 diffInWeeks = DQUtil.timeDiffInWeeks(timeDiff);
    	    		 if(diffInWeeks > 96) {
    	    			  q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TB_TX_OUTCOME);
    	    	    	    conceptQuestionList.clear();
    	    	    	    conceptQuestionList.add(q);
    	    	    	    
    	    	    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    	    	    if(obsList==null || obsList.size()==0) {
    	    	    	    	missingOutcomes.add(dqi);
    	    	    	    	errorFlag = Boolean.TRUE;
    	    	    	    }
    	    		 }
    	    		 
    	    	 }
    	    }
    	    
    	    //NO SITE
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANATOMICAL_SITE_OF_TB);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList==null || obsList.size()==0) {
    	    	noSite.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    //MISSING DST

    	    TB03uForm tf = new TB03uForm(patient);
    	    Dst firstDst = TB03uUtil.getDiagnosticDST(tf);
    	   
    	   
    	    if(firstDst==null) {
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
    	    
    	    if(!idFound) {
    	    	noMDRId.add(dqi);
    	    	errorFlag = Boolean.TRUE;
    	    }
    	    
    	    if(errorFlag) {
    	    	errorCount ++;
    	    }

    	    
    	}
    	
    	Integer num = patients.getSize();
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
//    	if(location!=null)
//    		model.addAttribute("location", location.getName());
//    	else
//    		model.addAttribute("location", "");
    	model.addAttribute("year", year);
    	model.addAttribute("quarter", quarter);
    	model.addAttribute("month", month);
    	
    	
    	
    	model.addAttribute("locale", Context.getLocale().toString());
    	
    	
    	
    	// TO CHECK WHETHER REPORT IS CLOSED OR NOT
    	Integer report_oblast = null; Integer report_quarter = null; Integer report_month = null;
		/*if(new PDFHelper().isInt(oblast)) { report_oblast = Integer.parseInt(oblast); }
		if(new PDFHelper().isInt(quarter)) { report_quarter = Integer.parseInt(quarter); }
		if(new PDFHelper().isInt(month)) { report_month = Integer.parseInt(month); }*/
		
    	boolean reportStatus = Context.getService(MdrtbService.class).readReportStatus(oblastId, districtId, facilityId, year, quarter, month, "DQ","MDRTB");
		System.out.println(reportStatus);
		
    	model.addAttribute("oblast", oblastId);
    	model.addAttribute("location", districtId);
    	model.addAttribute("year", year);
    	model.addAttribute("quarter", quarter);
    	model.addAttribute("reportDate", sdf.format(new Date()));
    	model.addAttribute("reportStatus", reportStatus);
        return "/module/mdrtb/reporting/dqResults";
    }
    
    
  
    
}
