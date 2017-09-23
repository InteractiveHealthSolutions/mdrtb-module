package org.openmrs.module.mdrtb.web.controller.reporting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import org.openmrs.module.mdrtb.MdrtbConstants;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.reporting.PDFHelper;
import org.openmrs.module.mdrtb.reporting.ReportUtil;
import org.openmrs.module.mdrtb.reporting.TB03Data;
import org.openmrs.module.mdrtb.reporting.TB03Util;
import org.openmrs.module.mdrtb.reporting.data.Cohorts;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.specimen.Culture;
import org.openmrs.module.mdrtb.specimen.Dst;
import org.openmrs.module.mdrtb.specimen.DstResult;
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

@Controller

public class TB03ExportController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
        
    
    @RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/tb03")
    public void showRegimenOptions(ModelMap model) {
    	
    	
    
       
        List<Location> locations = Context.getLocationService().getAllLocations(false);// Context.getLocationService().getAllLocations();//ms = (MdrtbDrugForecastService) Context.getService(MdrtbDrugForecastService.class);
        List<Oblast> oblasts = Context.getService(MdrtbService.class).getOblasts();
        //drugSets =  ms.getMdrtbDrugs();
        
       

        model.addAttribute("locations", locations);
        model.addAttribute("oblasts", oblasts);
      
    	
    }
    
  
    
    
    @SuppressWarnings({ "deprecation", "unchecked" })
	@RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/tb03")
    public static String doTB03(
    		@RequestParam("location") Location location,
    		@RequestParam("oblast") String oblast,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException {
    	
    	/*Oblast o = null;
    	if(oblast!=null && !oblast.equals("") && location == null)
			o =  Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblast));
		
		List<Location> locList = new ArrayList<Location>();
		if(o != null && location == null)
			locList = Context.getService(MdrtbService.class).getLocationsFromOblastName(o);
		else if (location != null)
			locList.add(location);
		
		Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
		
		Date startDate = (Date)(dateMap.get("startDate"));
		Date endDate = (Date)(dateMap.get("endDate"));
		
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
    	//Cohort patients = TbUtil.getDOTSPatientsTJK(null, null, location, oblast, null, null, null, null,year,quarter,month);
    	
		
		Form tb03Form = Context.getFormService().getForm(MdrtbConstants.TB03_FORM_ID);
		ArrayList<Form> formList = new ArrayList<Form>();
		formList.add(tb03Form);
    	
    	
    	Set<Integer> idSet = patients.getMemberIds();*/
    	ArrayList<TB03Form> tb03List = Context.getService(MdrtbService.class).getTB03FormsFilled(location, oblast, year, quarter, month);
    	
    	
    	ArrayList<TB03Data> patientSet  = new ArrayList<TB03Data>();
    	SimpleDateFormat sdf = new SimpleDateFormat();
    	
    	/*ArrayList<Person> patientList = new ArrayList<Person>();
    	ArrayList<Concept> conceptQuestionList = new ArrayList<Concept>();
    	ArrayList<Concept> conceptAnswerList = new ArrayList<Concept>();*/
    	Integer regimenConceptId = null;
    	Integer codId = null;
    	//List<Obs> obsList = null;
    	
    	Concept reg1New = Context.getService(MdrtbService.class).getConcept(TbConcepts.REGIMEN_1_NEW);
    	Concept reg1Rtx = Context.getService(MdrtbService.class).getConcept(TbConcepts.REGIMEN_1_RETREATMENT);
    	
    	sdf.applyPattern("dd.MM.yyyy");
    	for (TB03Form tf : tb03List) {
    		
    		
    		TB03Data tb03Data = new TB03Data();
    		tb03Data.setReg1New(Boolean.FALSE);
    		tb03Data.setReg1Rtx(Boolean.FALSE);
    	    Patient patient = tf.getPatient();
    	    if(patient==null) {
    	    	continue;
    	    	
    	    }
    	    
    	    
    	    tb03Data.setPatient(patient);
    	    
    	    //PATIENT IDENTIFIER
    	   /* tb03Data.setIdentifier(patient.getActiveIdentifiers().get(0).toString());*/
    	   
    	    String identifier = TB03Util.getRegistrationNumber(tf);
    	    tb03Data.setIdentifier(identifier);
    	    
    	    //DATE OF TB03 REGISTRATION
    	    
    	    Date encDate = tf.getEncounterDatetime();
    	   
    	    tb03Data.setTb03RegistrationDate(sdf.format(encDate));
    	    	
    	   
    	   
    	    
    	    //FORMATTED DATE OF BIRTH
    	    if(patient.getBirthdate()!=null)
    	    	tb03Data.setDateOfBirth(sdf.format(patient.getBirthdate()));
    	    
    	    //AGE AT TB03 Registration
    	    Integer age = tf.getAgeAtTB03Registration();//Context.getService(MdrtbService.class).getConcept(TbConcepts.AGE_AT_DOTS_REGISTRATION);
    	    if(age!=null)
    	    	tb03Data.setAgeAtTB03Registration(age);
    	    
    	    //TX CENTER FOR IP
    	    Concept q = tf.getTreatmentSiteIP();
    	    
    	    
    	    if(q!=null)
    	    	tb03Data.setIntensivePhaseFacility(q.getName().getName());
    	    
    	    //TX CENTER FOR CP
    	    q = tf.getTreatmentSiteCP();
    	    
    	    if(q!=null)
    	    	tb03Data.setContinuationPhaseFacility(q.getName().getName());
    	    
    	    //DOTS TREATMENT REGIMEN
    	    q = tf.getPatientCategory();
    	   
    	    if(q!=null)
    	    	tb03Data.setTreatmentRegimen(q.getName().getName());
    	    
    	    //DATE OF TB03 TREATMENT START
    	    Date txStart = tf.getTreatmentStartDate();
    	    if(txStart!=null)
    	    	tb03Data.setTb03TreatmentStartDate(sdf.format(txStart));
    	    
    	  //SITE OF DISEASE (P/EP)
    	    q = tf.getAnatomicalSite();
    	  
    	    if(q!=null)
    	    	tb03Data.setSiteOfDisease(q.getName().getShortName());
    	    
    	  //HIV TEST RESULT
    	    q = tf.getHivStatus();
    	   
    	    if(q!=null)
    	    	tb03Data.setHivTestResult(q.getName().getName());

    	   /* //DATE OF HIV TEST
    	    Date hivTestDate = tf.get
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setHivTestDate(sdf.format(obsList.get(0).getValueDatetime()));*/
    	    
    	  //DATE OF ART START
    	  Date artStartDate = tf.getArtStartDate();
    	  if(artStartDate!=null)
    	    	tb03Data.setArtStartDate(sdf.format(artStartDate));
    	    
    	  //DATE OF CP START
    	  Date pctStartDate = tf.getPctStartDate();
    	  if(pctStartDate!=null)
    	    	tb03Data.setArtStartDate(sdf.format(pctStartDate)); 
    	    
    	    
    	    
    	    //REGISTRATION GROUP
    	    q = tf.getRegistrationGroup();
    	   
    	    if(q!=null) {
    	    	tb03Data.setRegGroup(q.getConceptId());
    	    }
    	    
    	    
    	    //DIAGNOSTIC RESULTS
    	    
    	    //DIAGNOSTIC SMEAR
    	    
    	    Smear diagnosticSmear = TB03Util.getDiagnosticSmear(tf);
    	    if(diagnosticSmear!=null) {
    	    		if(diagnosticSmear.getResult()!=null) 
    	    			tb03Data.setDiagnosticSmearResult(diagnosticSmear.getResult().getName().getShortName());
    	    		if(diagnosticSmear.getResultDate()!=null)
    	    			tb03Data.setDiagnosticSmearDate(sdf.format(diagnosticSmear.getResultDate()));
    	    		
    	    		tb03Data.setDiagnosticSmearTestNumber(diagnosticSmear.getRealSpecimenId());
    	    }
    	    
    	    
    	    //DIAGNOSTIC XPERT
    	    Xpert firstXpert = TB03Util.getFirstXpert(tf);
    	    if(firstXpert!=null) {
    	    	if(firstXpert.getResult()!=null)
    	    		tb03Data.setXpertMTBResult(firstXpert.getResult().getName().getShortName());
    	    	if(firstXpert.getRifResistance()!=null)
    	    		tb03Data.setXpertRIFResult(firstXpert.getRifResistance().getName().getShortName());
    	    	if(firstXpert.getResultDate()!=null)
    	    		tb03Data.setXpertTestDate(sdf.format(firstXpert.getResultDate()));
    	    	
    	    	tb03Data.setXpertTestNumber(firstXpert.getRealSpecimenId());
    	    }
    	    
    	    
    	    
    	    //DIAGNOSTIC HAIN
    	    HAIN firstHAIN = TB03Util.getFirstHAIN(tf);
    	    if(firstHAIN!=null) {
    	    	if(firstHAIN.getResult()!=null)
    	    		tb03Data.setHainMTBResult(firstHAIN.getResult().getName().getShortName());
    	    	if(firstHAIN.getRifResistance()!=null)
    	    		tb03Data.setHainRIFResult(firstHAIN.getRifResistance().getName().getShortName());
    	    	if(firstHAIN.getInhResistance()!=null)
    	    		tb03Data.setHainINHResult(firstHAIN.getInhResistance().getName().getShortName());
    	    	if(firstHAIN.getResultDate()!=null)
    	    		tb03Data.setHainTestDate(sdf.format(firstHAIN.getResultDate()));
    	    	
    	    	tb03Data.setHainTestNumber(firstHAIN.getRealSpecimenId());
    	    }
    	    
    	    //DIAGNOSTIC CULTURE
    	    Culture diagnosticCulture  = TB03Util.getDiagnosticCulture(tf);
    	    if(diagnosticCulture!=null) {
    	    	if(diagnosticCulture.getResult()!=null)
    	    		tb03Data.setCultureResult(diagnosticCulture.getResult().getName().getShortName());
    	    	if(diagnosticCulture.getResultDate()!=null)
    	    		tb03Data.setCultureTestDate(sdf.format(diagnosticCulture.getResultDate()));
    	    	tb03Data.setCultureTestNumber(diagnosticCulture.getRealSpecimenId());
    	    }
    	    
    	    
    	   
    	    
    	    //DST
    	    Dst firstDst = TB03Util.getDiagnosticDST(tf);
    	    
    	    if(firstDst!=null) {
    	    	if(firstDst.getDateCollected()!=null)
    	    		tb03Data.setDstCollectionDate(sdf.format(firstDst.getDateCollected()));
    	    	if(firstDst.getResultDate()!=null)
    	    		tb03Data.setDstResultDate(sdf.format(firstDst.getResultDate()));
    	    	List<DstResult> resList = firstDst.getResults();
    	    	String drugName = null;
    	    	String result = null;
    	    	for(DstResult res : resList)
    	    	{
    	    		if(res.getDrug()!=null) {
    	    			drugName = res.getDrug().getShortestName(Context.getLocale(), false).toString();
    	    			result = res.getResult().getName().getShortName();
    	    			tb03Data.getDstResults().put(drugName,result);
    	    			//System.out.println(drugName + "-" + result + " | " + res.getResult());
    	    			
    	    		}
    	    	}
    	    	System.out.println("-------");
    	    }
    	    
    	    
    	    
    	    //DRUG RESISTANCE
    	    
    	    q = tf.getResistanceType();//Context.getService(MdrtbService.class).getConcept(TbConcepts.RESISTANCE_TYPE);
    	   
    	    
    	    if(q!=null)
    	    	tb03Data.setDrugResistance(q.getName().getShortName());
    	    
    	
    	    
    	    //FOLLOW-UP SMEARS
    	    
    	    //first check patient regimen
    	    Smear followupSmear = null;
    	    q = tf.getPatientCategory();//
    	    
    	    if(q!=null)
    	    	regimenConceptId = q.getConceptId();
    	    
    	    //accordingly look for smears
    	    if(regimenConceptId!=null) {
    	    	if(regimenConceptId.equals(reg1New.getConceptId())) {
    	    		
    	    		tb03Data.setReg1New(Boolean.TRUE);
    	    		
    	    		followupSmear = TB03Util.getFollowupSmear(tf, 2);
    	    	    if(followupSmear!=null) {
    	    	    		if(followupSmear.getResult()!=null) 
    	    	    			tb03Data.setMonth2SmearResult(followupSmear.getResult().getName().getShortName());
    	    	    		if(followupSmear.getResultDate()!=null)
    	    	    			tb03Data.setMonth2SmearDate(sdf.format(followupSmear.getResultDate()));
    	    	    		
    	    	    		tb03Data.setMonth2TestNumber(followupSmear.getRealSpecimenId());
    	    	    }
    	    	    
    	    	    followupSmear = TB03Util.getFollowupSmear(tf, 3);
    	    	    if(followupSmear!=null) {
    	    	    		if(followupSmear.getResult()!=null) 
    	    	    			tb03Data.setMonth3SmearResult(followupSmear.getResult().getName().getShortName());
    	    	    		if(followupSmear.getResultDate()!=null)
    	    	    			tb03Data.setMonth3SmearDate(sdf.format(followupSmear.getResultDate()));
    	    	    		
    	    	    		tb03Data.setMonth3TestNumber(followupSmear.getRealSpecimenId());
    	    	    }
    	    	    
    	    	    followupSmear = TB03Util.getFollowupSmear(tf, 5);
    	    	    if(followupSmear!=null) {
    	    	    		if(followupSmear.getResult()!=null) 
    	    	    			tb03Data.setMonth5SmearResult(followupSmear.getResult().getName().getShortName());
    	    	    		if(followupSmear.getResultDate()!=null)
    	    	    			tb03Data.setMonth5SmearDate(sdf.format(followupSmear.getResultDate()));
    	    	    		
    	    	    		tb03Data.setMonth5TestNumber(followupSmear.getRealSpecimenId());
    	    	    }
    	    	    
    	    	    followupSmear = TB03Util.getFollowupSmear(tf, 6);
    	    	    if(followupSmear!=null) {
    	    	    		if(followupSmear.getResult()!=null) 
    	    	    			tb03Data.setMonth6SmearResult(followupSmear.getResult().getName().getShortName());
    	    	    		if(followupSmear.getResultDate()!=null)
    	    	    			tb03Data.setMonth6SmearDate(sdf.format(followupSmear.getResultDate()));
    	    	    		
    	    	    		tb03Data.setMonth6TestNumber(followupSmear.getRealSpecimenId());
    	    	    }
    	    	}
    	    	
    	    	else if(regimenConceptId.equals(reg1Rtx.getConceptId())) {
    	    		tb03Data.setReg1Rtx(Boolean.TRUE);
    	    		 followupSmear = TB03Util.getFollowupSmear(tf, 3);
     	    	    if(followupSmear!=null) {
     	    	    		if(followupSmear.getResult()!=null) 
     	    	    			tb03Data.setMonth3SmearResult(followupSmear.getResult().getName().getShortName());
     	    	    		if(followupSmear.getResultDate()!=null)
     	    	    			tb03Data.setMonth3SmearDate(sdf.format(followupSmear.getResultDate()));
     	    	    		
     	    	    		tb03Data.setMonth3TestNumber(followupSmear.getRealSpecimenId());
     	    	    }
    	    	    
    	    	    followupSmear = TB03Util.getFollowupSmear(tf, 4);
    	    	    if(followupSmear!=null) {
    	    	    		if(followupSmear.getResult()!=null) 
    	    	    			tb03Data.setMonth4SmearResult(followupSmear.getResult().getName().getShortName());
    	    	    		if(followupSmear.getResultDate()!=null)
    	    	    			tb03Data.setMonth4SmearDate(sdf.format(followupSmear.getResultDate()));
    	    	    		
    	    	    		tb03Data.setMonth4TestNumber(followupSmear.getRealSpecimenId());
    	    	    }
    	    	    
    	    	    followupSmear = TB03Util.getFollowupSmear(tf, 5);
    	    	    if(followupSmear!=null) {
    	    	    		if(followupSmear.getResult()!=null) 
    	    	    			tb03Data.setMonth5SmearResult(followupSmear.getResult().getName().getShortName());
    	    	    		if(followupSmear.getResultDate()!=null)
    	    	    			tb03Data.setMonth5SmearDate(sdf.format(followupSmear.getResultDate()));
    	    	    		
    	    	    		tb03Data.setMonth5TestNumber(followupSmear.getRealSpecimenId());
    	    	    }
    	    	    
    	    	    followupSmear = TB03Util.getFollowupSmear(tf, 8);
    	    	    if(followupSmear!=null) {
    	    	    		if(followupSmear.getResult()!=null) 
    	    	    			tb03Data.setMonth8SmearResult(followupSmear.getResult().getName().getShortName());
    	    	    		if(followupSmear.getResultDate()!=null)
    	    	    			tb03Data.setMonth8SmearDate(sdf.format(followupSmear.getResultDate()));
    	    	    		
    	    	    		tb03Data.setMonth8TestNumber(followupSmear.getRealSpecimenId());
    	    	    }
    	    	}
    	    }
    	    
    	    //TX OUTCOME
    	    //CHECK CAUSE OF DEATH
    	   
    	    q = tf.getCauseOfDeath();//Context.getService(MdrtbService.class).getConcept(TbConcepts.CAUSE_OF_DEATH);
    	    
    	    if(q!=null)
    	    {	
    	    	codId = q.getConceptId();
    	    	if(codId.equals(Context.getService(MdrtbService.class).getConcept(TbConcepts.DEATH_BY_TB).getConceptId()))
    	    		tb03Data.setDiedOfTB(true);
    	    	else
    	    		tb03Data.setDiedOfTB(false);
    	    }
    	    
    	    else
	    		tb03Data.setDiedOfTB(false);
    	    
    	    
    	    	
    	    
    	    
    	    q = tf.getTreatmentOutcome();
    	    if(q!=null) {
    	    	tb03Data.setTb03TreatmentOutcome(q.getConceptId());
    	    	
    	    	 Date txOutcomeDate = tf.getTreatmentOutcomeDate();
    	    	 if(txOutcomeDate!=null) {
    	    	    	tb03Data.setTb03TreatmentOutcomeDate(sdf.format(txOutcomeDate));
    	    	    }
    	    }
    	    
    	    //NOTES
    	    
    	    String notes = tf.getClinicianNotes();
    	   
    	    if(notes!=null)
    	    	tb03Data.setNotes(notes);
    	    
    	    patientSet.add(tb03Data);
    	    
    	    q = null;
    	   
    	}
    	
    	Collections.sort(patientSet);
    	
    	Integer num = patientSet.size();
    	model.addAttribute("num", num);
    	model.addAttribute("patientSet", patientSet);
    	model.addAttribute("locale", Context.getLocale().toString());

    	
    	// TO CHECK WHETHER REPORT IS CLOSED OR NOT
    	Integer report_oblast = null; Integer report_quarter = null; Integer report_month = null;
		if(new PDFHelper().isInt(oblast)) { report_oblast = Integer.parseInt(oblast); }
		if(new PDFHelper().isInt(quarter)) { report_quarter = Integer.parseInt(quarter); }
		if(new PDFHelper().isInt(month)) { report_month = Integer.parseInt(month); }
		
    	boolean reportStatus;// = Context.getService(MdrtbService.class).readReportStatus(report_oblast, location.getId(), year, report_quarter, report_month, "TB 03");
    	
    	if(location!=null)
			 reportStatus = Context.getService(MdrtbService.class).readReportStatus(report_oblast, location.getId(), year, report_quarter, report_month, "TB-03", "DOTSTB");
		else
			reportStatus = Context.getService(MdrtbService.class).readReportStatus(report_oblast, null, year, report_quarter, report_month, "TB-03", "DOTSTB");
		
    	System.out.println(reportStatus);
    	model.addAttribute("oblast", oblast);
    	model.addAttribute("location", location);
    	model.addAttribute("year", year);
    	model.addAttribute("quarter", quarter);
    	model.addAttribute("reportDate", sdf.format(new Date()));
    	model.addAttribute("reportStatus", reportStatus);
    	return "/module/mdrtb/reporting/tb03Results";
    }
    
    
  
    
}