package org.openmrs.module.mdrtb.web.controller.reporting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.Drug;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.Person;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConceptMap;
import org.openmrs.module.mdrtb.MdrtbConstants;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.reporting.ReportUtil;
import org.openmrs.module.mdrtb.reporting.TB03uData;
import org.openmrs.module.mdrtb.reporting.TB03uUtil;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.specimen.Culture;
import org.openmrs.module.mdrtb.specimen.Dst;
import org.openmrs.module.mdrtb.specimen.DstResult;
import org.openmrs.module.mdrtb.specimen.HAIN;
import org.openmrs.module.mdrtb.specimen.Smear;
import org.openmrs.module.mdrtb.specimen.Xpert;
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
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.propertyeditor.ConceptEditor;
import org.openmrs.propertyeditor.LocationEditor;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller

public class TB03uController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
        
    
    @RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/tb03u")
    public void showRegimenOptions(ModelMap model) {
    	
    	
    
       
        List<Location> locations = Context.getLocationService().getAllLocations(false);//ms = (MdrtbDrugForecastService) Context.getService(MdrtbDrugForecastService.class);
        List<Oblast> oblasts = Context.getService(MdrtbService.class).getOblasts();
        //drugSets =  ms.getMdrtbDrugs();
        
       

        model.addAttribute("locations", locations);
        model.addAttribute("oblasts", oblasts);
      
    	
    }
    
  
    
    
    @RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/tb03u")
    public String doTB03(
    		@RequestParam("location") Location location,
    		@RequestParam("oblast") String oblast,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException {
    	
    	Cohort patients = MdrtbUtil.getMdrPatientsTJK(null, null, location, oblast, null, null, null, null,year,quarter,month);
    	Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
		
		Date startDate = (Date)(dateMap.get("startDate"));
		Date endDate = (Date)(dateMap.get("endDate"));
		
		Form tb03uForm = Context.getFormService().getForm(MdrtbConstants.TB03U_FORM_ID);
		ArrayList<Form> formList = new ArrayList<Form>();
		formList.add(tb03uForm);
    	
		Form tb03Form = Context.getFormService().getForm(MdrtbConstants.TB03_FORM_ID);
		ArrayList<Form> oldformList = new ArrayList<Form>();
		oldformList.add(tb03Form);
    	
    	
    	Set<Integer> idSet = patients.getMemberIds();
    	ArrayList<TB03uData> patientSet  = new ArrayList<TB03uData>();
    	SimpleDateFormat sdf = new SimpleDateFormat();
    	
    	ArrayList<Person> patientList = new ArrayList<Person>();
    	ArrayList<Concept> conceptQuestionList = new ArrayList<Concept>();
    	ArrayList<Concept> conceptAnswerList = new ArrayList<Concept>();
    	Integer regimenConceptId = null;
    	Integer codId = null;
    	List<Obs> obsList = null;
    	
    	/*Concept reg1New = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REGIMEN_1_NEW);
    	Concept reg1Rtx = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REGIMEN_1_RETREATMENT);
    	*/
    	sdf.applyPattern("dd.MM.yyyy");
    	for (Integer i : idSet) {
    		patientList.clear();
         	conceptQuestionList.clear();
         	conceptAnswerList.clear();
    		
    		TB03uData tb03Data = new TB03uData();
    		
    	    Patient patient = Context.getPatientService().getPatient(i);
    	    if(patient==null) {
    	    	continue;
    	    	
    	    }
    	    	
    	    
    	    
    	    patientList.add(patient);
    	    
    	    
    	    tb03Data.setPatient(patient);
    	    
    	    
    	    //PATIENT IDENTIFIER
    	    List<PatientIdentifier> idList = patient.getActiveIdentifiers();
    	    
    	    for(PatientIdentifier pi : idList) {
    	    	
    	    	if(pi.getIdentifierType().getId()==2 && tb03Data.getIdentifierDOTS()==null) {
    	    		tb03Data.setIdentifierDOTS(pi.getIdentifier());
    	    		if(tb03Data.getIdentifierMDR()!=null)
    	    			break;
    	    	}
    	    	
    	    	if(pi.getIdentifierType().getId()==5 && tb03Data.getIdentifierMDR()==null) {
    	    		tb03Data.setIdentifierMDR(pi.getIdentifier());
    	    		if(tb03Data.getIdentifierDOTS()!=null)
    	    			break;
    	    	}
    	    	
    	    }
    	    
    	    /*tb03Data.setIdentifierDOTS(patient.getActiveIdentifiers().get(0).toString());
    	    tb03Data.setIdentifierMDR(patient.getActiveIdentifiers().get(0).toString());*/
    	   
    	    
    	    //DATE OF TB03U REGISTRATION
    	    List<Encounter> tb03uEncList = Context.getEncounterService().getEncounters(patient, null, startDate, endDate, formList, null, null, false);
    	    Date encDate = null;
    	    if(tb03uEncList.size() > 0 && tb03uEncList.get(0)!=null) {
    	    	encDate = tb03uEncList.get(0).getEncounterDatetime();
    	    	tb03Data.setTb03uRegistrationDate(sdf.format(encDate));
    	    	
    	    }
    	    
    	    else
    	    	continue;
    	    
    	    List<Encounter> tb03EncList = Context.getEncounterService().getEncounters(patient, null, startDate, endDate, formList, null, null, false);
    	    Date oldencDate = null;
    	    if(tb03uEncList.size() > 0 && tb03uEncList.get(0)!=null) {
    	    	oldencDate = tb03EncList.get(0).getEncounterDatetime();
    	    	tb03Data.setDotsYear(oldencDate.getYear()+1900);
    	    	
    	    }
    	   
    	    
    	    //FORMATTED DATE OF BIRTH
    	    tb03Data.setDateOfBirth(sdf.format(patient.getBirthdate()));
    	    
    	    //AGE AT TB03U Registration
    	    Concept q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AGE_AT_MDR_REGISTRATION);
    	    
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, tb03uEncList, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setAgeAtTB03uRegistration(obsList.get(0).getValueNumeric().intValue());
    	    
    	 
    	    
    	    //SITE OF DISEASE (P/EP)
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANATOMICAL_SITE_OF_TB);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setSiteOfDisease(obsList.get(0).getValueCoded().getName().getShortName());
    	    
    	    //SLD Register Number
     	   q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REGIMEN_2_REG_NUMBER);
     	    conceptQuestionList.clear();
     	    conceptQuestionList.add(q);
     	    
     	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
     	    if(obsList.size()>0 && obsList.get(0)!=null)
     	    	tb03Data.setReg2Number(obsList.get(0).getValueText());

    	    //REGISTRATION GROUP
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAT_4_CLASSIFICATION_PREVIOUS_TX);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null) {
    	    	tb03Data.setRegGroup(obsList.get(0).getValueCoded().getConceptId());
    	    }
    	    
    	    //MDR STATUS
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_STATUS);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setMdrtbStatus((obsList.get(0).getValueCoded().getName().getName()));
    	    
    	    //MDR CONF DATE
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DATE_OF_MDR_CONFIRMATION);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setMdrConfDate(sdf.format(obsList.get(0).getValueDatetime()));
    	    
    	    
    	    //MDR TREATMENT REGIMEN
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.TUBERCULOSIS_PATIENT_CATEGORY);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setTreatmentRegimen(obsList.get(0).getValueCoded().getName().getName());
    	    
    	    //DATE OF MDR TREATMENT START
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TREATMENT_START_DATE);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setTb03uTreatmentStartDate(sdf.format(obsList.get(0).getValueDatetime()));
    	    
    	    //TREATMENT LOCATION
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.TREATMENT_LOCATION);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setTreatmentLocation(obsList.get(0).getValueCoded().getName().getName());
    	    
    	    //DST
    	    Dst firstDst = TB03uUtil.getDiagnosticDST(patient);
    	    
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
    	    
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RESISTANCE_TYPE);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setDrugResistance(obsList.get(0).getValueCoded().getName().getShortName());
    	    
    	    //DIAGNOSTIC METHOD
    	    
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.METHOD_OF_DIAGNOSTIC);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setDiagnosticMethod(obsList.get(0).getValueCoded().getName().getName());
    	    
    	    //HIV TEST RESULT
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RESULT_OF_HIV_TEST);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setHivTestResult(obsList.get(0).getValueCoded().getName().getName());

    	    //DATE OF HIV TEST
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DATE_OF_HIV_TEST);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setHivTestDate(sdf.format(obsList.get(0).getValueDatetime()));
    	    
    	  //DATE OF ART START
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DATE_OF_ART_TREATMENT_START);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setArtStartDate(sdf.format(obsList.get(0).getValueDatetime()));
    	    
    	  //DATE OF CP START
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DATE_OF_PCT_TREATMENT_START);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setCpStartDate(sdf.format(obsList.get(0).getValueDatetime()));  
    	    
    	
    	    
    	    //FOLLOW-UP SMEARS
    	    //accordingly look for smears
    	  
    	    Smear followupSmear = TB03uUtil.getFollowupSmear(patient, 0);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth0SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth0SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 1);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth1SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth1SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 2);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth2SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth2SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 3);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth3SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth3SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 4);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth4SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth4SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 5);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth5SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth5SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 6);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth6SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth6SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 7);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth7SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth7SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 8);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth8SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth8SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 9);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth9SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth9SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 10);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth10SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth10SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 11);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth11SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth11SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 12);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth12SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth12SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 15);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth15SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth15SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 18);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth18SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth18SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 21);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth21SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth21SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 24);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth24SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth24SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 27);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth27SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth27SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 30);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth30SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth30SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 33);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth33SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth33SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(patient, 36);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03Data.setMonth36SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03Data.setMonth36SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    //follow CULTURES
    	    Culture followupCulture = TB03uUtil.getFollowupCulture(patient, 0);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth0CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth0CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 1);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth1CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth1CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 2);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth2CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth2CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 3);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth3CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth3CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 4);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth4CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth4CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 5);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth5CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth5CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 6);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth6CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth6CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 7);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth7CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth7CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 8);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth8CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth8CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 9);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth9CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth9CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 10);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth10CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth10CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 11);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth11CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth11CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 12);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth12CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth12CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 15);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth15CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth15CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 18);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth18CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth18CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 21);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth21CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth21CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 24);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth24CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth24CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 27);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth27CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth27CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 30);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth30CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth30CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 33);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth33CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth33CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(patient, 36);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03Data.setMonth36CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03Data.setMonth36CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    
    	    //TX OUTCOME
    	    //CHECK CAUSE OF DEATH
    	   
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSE_OF_DEATH);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    {	
    	    	codId = obsList.get(0).getValueCoded().getConceptId();
    	    	if(codId.equals(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DEATH_BY_TB).getConceptId()))
    	    		tb03Data.setDiedOfTB(true);
    	    	else
    	    		tb03Data.setDiedOfTB(false);
    	    }
    	    
    	    else
	    		tb03Data.setDiedOfTB(false);
    	    
    	    
    	    //RELAPSED
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RELAPSED);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setRelapsed(obsList.get(0).getValueCoded().getName().getName());
    	    
    	    
    	    
    	    //RELAPSED AT MONTH?
    	    //RELAPSED
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RELAPSE_MONTH);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
        	    if(obsList.size()>0 && obsList.get(0)!=null)
        	    	tb03Data.setRelapseMonth(obsList.get(0).getValueNumeric().intValue());
        	    	
        	    	
        	//TX OUTCOME
    	    
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TB_TX_OUTCOME);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null) {
    	    	tb03Data.setTb03uTreatmentOutcome(obsList.get(0).getValueCoded().getConceptId());
    	    	 q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.TX_OUTCOME_DATE);
 	    	    conceptQuestionList.clear();
 	    	    conceptQuestionList.add(q);
 	    	    
 	    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
 	    	    if(obsList.size()>0 && obsList.get(0)!=null) {
 	    	    	tb03Data.setTb03uTreatmentOutcomeDate(sdf.format(obsList.get(0).getValueDatetime()));
 	    	    }
    	    }
    	    
    	    //NOTES
    	    
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CLINICIAN_NOTES);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setNotes(obsList.get(0).getValueText());
    	    
    	    patientSet.add(tb03Data);
    	    
    	    regimenConceptId = null;
        	codId = null;
        	obsList = null;
    	   
    	}
    	
    	Collections.sort(patientSet);
    	
    	Integer num = patients.getSize();
    	model.addAttribute("num", num);
    	model.addAttribute("patientSet", patientSet);
        return "/module/mdrtb/reporting/tb03uResults";
    }
    
    
  
    
}
