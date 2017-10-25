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
import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbConstants;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.form.TB03uForm;
import org.openmrs.module.mdrtb.reporting.PDFHelper;
import org.openmrs.module.mdrtb.reporting.ReportUtil;
import org.openmrs.module.mdrtb.reporting.TB03Util;
import org.openmrs.module.mdrtb.reporting.TB03uData;
import org.openmrs.module.mdrtb.reporting.TB03uUtil;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.specimen.Culture;
import org.openmrs.module.mdrtb.specimen.Dst;
import org.openmrs.module.mdrtb.specimen.DstResult;
import org.openmrs.module.mdrtb.specimen.Smear;
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

@SuppressWarnings({ "deprecation", "unused", "unchecked" })
@Controller
public class TB03uController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
        
    
    @RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/tb03u")
    public ModelAndView showRegimenOptions(@RequestParam(value="loc", required=false) String district,
    									  @RequestParam(value="ob", required=false) String oblast,
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
		} 
		
		else {
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
		if(month!=null && month.length()!=0)
			model.addAttribute("monthSelected", month.replace("\"", ""));
		else
			model.addAttribute("monthSelected", "");
		
		if(quarter!=null && quarter.length()!=0)
			model.addAttribute("quarterSelected", quarter.replace("\"", "'"));
		else
			model.addAttribute("quarterSelected", "");
		//"bb".get
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
		return new ModelAndView("/module/mdrtb/reporting/tb03u", model);

	}
    
  
    
    
	@RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/tb03u")
    public static String doTB03(
    		@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException {
		System.out.println("---POST-----");
    	System.out.println("PARAMS:" + oblastId + " " + districtId + " " + facilityId + " " + year + " " + quarter + " " + month);
    	
		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    	
    	ArrayList<TB03uForm> tb03uList = Context.getService(MdrtbService.class).getTB03uFormsFilled(locList, year, quarter, month);
		
		
    	/*Cohort patients = new Cohort();// MdrtbUtil.getMdrPatientsTJK(null, null, location, oblast, null, null, null, null,year,quarter,month);
    	Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
		
		Date startDate = (Date)(dateMap.get("startDate"));
		Date endDate = (Date)(dateMap.get("endDate"));
		
		Form tb03uForm = Context.getFormService().getForm(MdrtbConstants.TB03U_FORM_ID);
		ArrayList<Form> formList = new ArrayList<Form>();
		formList.add(tb03uForm);
    	
		Form tb03Form = Context.getFormService().getForm(MdrtbConstants.TB03_FORM_ID);
		ArrayList<Form> oldformList = new ArrayList<Form>();
		oldformList.add(tb03Form);
    	
    	
    	Set<Integer> idSet = patients.getMemberIds();*/
    	ArrayList<TB03uData> patientSet  = new ArrayList<TB03uData>();
    	SimpleDateFormat sdf = new SimpleDateFormat();
    	
    	/*ArrayList<Person> patientList = new ArrayList<Person>();
    	ArrayList<Concept> conceptQuestionList = new ArrayList<Concept>();
    	ArrayList<Concept> conceptAnswerList = new ArrayList<Concept>();*/
    	Integer regimenConceptId = null;
    	Integer codId = null;
    	//List<Obs> obsList = null;
    	
    	/*Concept reg1New = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REGIMEN_1_NEW);
    	Concept reg1Rtx = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REGIMEN_1_RETREATMENT);
    	*/
    	sdf.applyPattern("dd.MM.yyyy");
    	for (TB03uForm tf : tb03uList) {
    		
    		TB03uData tb03uData = new TB03uData();
    		
    		Patient patient = tf.getPatient();
    	    if(patient==null || patient.isVoided()) {
    	    	continue;
    	    	
    	    }
    	    	
    	    
    	    tb03uData.setPatient(patient);
    	    
    	    
    	    //PATIENT IDENTIFIER
    	   /* List<PatientIdentifier> idList = patient.getActiveIdentifiers();
    	    
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
    	    	
    	    }*/
    	    
    	    String identifier = TB03Util.getRegistrationNumber(tf);
    	    tb03uData.setIdentifierMDR(identifier);
    	    
    	    String identifierDOTS = TB03Util.getRegistrationNumber(tf.getTb03());
    	    tb03uData.setIdentifierDOTS(identifierDOTS);
    	    
    	    Date encDate = tf.getEncounterDatetime();
     	   
    	    tb03uData.setTb03uRegistrationDate(sdf.format(encDate));
    	    tb03uData.setDotsYear(tf.getTb03RegistrationYear());
    	   
    	    
//    	    //DATE OF TB03U REGISTRATION
//    	    List<Encounter> tb03uEncList = Context.getEncounterService().getEncounters(patient, null, startDate, endDate, formList, null, null, false);
//    	    Date encDate = null;
//    	    if(tb03uEncList.size() > 0 && tb03uEncList.get(0)!=null) {
//    	    	encDate = tb03uEncList.get(0).getEncounterDatetime();
//    	    	tb03Data.setTb03uRegistrationDate(sdf.format(encDate));
//    	    	
//    	    }
//    	    
//    	    else
//    	    	continue;
//    	    
//    	    List<Encounter> tb03EncList = Context.getEncounterService().getEncounters(patient, null, startDate, endDate, formList, null, null, false);
//    	    Date oldencDate = null;
//    	    if(tb03uEncList.size() > 0 && tb03uEncList.get(0)!=null) {
//    	    	oldencDate = tb03EncList.get(0).getEncounterDatetime();
//    	    	tb03Data.setDotsYear(oldencDate.getYear()+1900);
//    	    	
//    	    }
    	   
    	    
    	   /* //FORMATTED DATE OF BIRTH
    	    tb03Data.setDateOfBirth(sdf.format(patient.getBirthdate()));
    	    
    	    //AGE AT TB03U Registration
    	    Concept q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AGE_AT_MDR_REGISTRATION);
    	    
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, tb03uEncList, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setAgeAtTB03uRegistration(obsList.get(0).getValueNumeric().intValue());*/
    	    
    	    if(patient.getBirthdate()!=null)
    	    	tb03uData.setDateOfBirth(sdf.format(patient.getBirthdate()));
    	    
    	   /* //SITE OF DISEASE (P/EP)
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANATOMICAL_SITE_OF_TB);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setSiteOfDisease(obsList.get(0).getValueCoded().getName().getShortName());*/
    	    
    	    Concept q = tf.getAnatomicalSite();
      	  
    	    if(q!=null)
    	    	tb03uData.setSiteOfDisease(q.getName().getShortName());
    	    
    	   /* //SLD Register Number
     	   q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REGIMEN_2_REG_NUMBER);
     	    conceptQuestionList.clear();
     	    conceptQuestionList.add(q);
     	    
     	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
     	    if(obsList.size()>0 && obsList.get(0)!=null)
     	    	tb03Data.setReg2Number(obsList.get(0).getValueText());*/
    	    
    	    String reg2Number = tf.getSldRegisterNumber();
        	  
    	    tb03uData.setReg2Number(reg2Number);

    	    //REGISTRATION GROUP
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAT_4_CLASSIFICATION_PREVIOUS_TX);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null) {
    	    	tb03Data.setRegGroup(obsList.get(0).getValueCoded().getConceptId());
    	    }*/
    	    
    	    q = tf.getRegistrationGroup();
     	   
    	    if(q!=null) {
    	    	tb03uData.setRegGroup(q.getConceptId());
    	    }
    	    
    	    //MDR STATUS
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_STATUS);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setMdrtbStatus((obsList.get(0).getValueCoded().getName().getName()));*/
    	    
    	    q = tf.getMdrStatus();
    	    
    	    if(q!=null) {
    	    	tb03uData.setMdrtbStatus(q.getName().getName());
    	    }
    	    
    	    //MDR CONF DATE
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DATE_OF_MDR_CONFIRMATION);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setMdrConfDate(sdf.format(obsList.get(0).getValueDatetime()));*/
    	    
    	    Date confDate = tf.getConfirmationDate();
    	    if(confDate!=null)
      	    	tb03uData.setMdrConfDate(sdf.format(confDate));
    	    
    	    
    	    //MDR TREATMENT REGIMEN
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.TUBERCULOSIS_PATIENT_CATEGORY);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setTreatmentRegimen(obsList.get(0).getValueCoded().getName().getName());*/
    	    
    	    q = tf.getPatientCategory();
    	    
    	    if(q!=null)
    	    		tb03uData.setTreatmentRegimen(q.getName().getName());
    	    
    	    //DATE OF MDR TREATMENT START
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TREATMENT_START_DATE);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setTb03uTreatmentStartDate(sdf.format(obsList.get(0).getValueDatetime()));*/
    	    
    	    Date txStartDate = tf.getMdrTreatmentStartDate();
    	    if(txStartDate!=null)
      	    	tb03uData.setTb03uTreatmentStartDate(sdf.format(txStartDate));
    	    
    	    //TREATMENT LOCATION
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.TREATMENT_LOCATION);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setTreatmentLocation(obsList.get(0).getValueCoded().getName().getName());*/
    	    
    	    q = tf.getTxLocation();
    	    
    	    if(q!=null) {
    	    	tb03uData.setTreatmentLocation(q.getName().getName());
    	    }
    	    
    	    //DST
    	   /* Dst firstDst = TB03uUtil.getDiagnosticDST(tf);
    	    
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
    	    }*/
    	    
    	    Dst firstDst = TB03uUtil.getDiagnosticDST(tf);
    	    
    	    if(firstDst!=null) {
    	    	if(firstDst.getDateCollected()!=null)
    	    		tb03uData.setDstCollectionDate(sdf.format(firstDst.getDateCollected()));
    	    	if(firstDst.getResultDate()!=null)
    	    		tb03uData.setDstResultDate(sdf.format(firstDst.getResultDate()));
    	    	List<DstResult> resList = firstDst.getResults();
    	    	String drugName = null;
    	    	String result = null;
    	    	for(DstResult res : resList)
    	    	{
    	    		if(res.getDrug()!=null) {
    	    			drugName = res.getDrug().getShortestName(Context.getLocale(), false).toString();
    	    			result = res.getResult().getName().getShortName();
    	    			tb03uData.getDstResults().put(drugName,result);
    	    			//System.out.println(drugName + "-" + result + " | " + res.getResult());
    	    			
    	    		}
    	    	}
    	    	System.out.println("-------");
    	    }
    	    
    	    //DRUG RESISTANCE
    	    
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RESISTANCE_TYPE);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setDrugResistance(obsList.get(0).getValueCoded().getName().getShortName());*/
    	    
    	    q = tf.getResistanceType();
    	    
    	    if(q!=null) {
    	    	tb03uData.setDrugResistance(q.getName().getName());
    	    }
    	    
    	    //DIAGNOSTIC METHOD
    	    
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.METHOD_OF_DIAGNOSTIC);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setDiagnosticMethod(obsList.get(0).getValueCoded().getName().getName());*/
    	    
    	    q = tf.getBasisForDiagnosis();
    	    
    	    if(q!=null) {
    	    	tb03uData.setDiagnosticMethod(q.getName().getName());
    	    }
    	    
    	    
    	   /* //HIV TEST RESULT
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RESULT_OF_HIV_TEST);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setHivTestResult(obsList.get(0).getValueCoded().getName().getName());*/

    	    q = tf.getHivStatus();
    	    
    	    if(q!=null) {
    	    	tb03uData.setHivTestResult(q.getName().getName());
    	    }
    	    
    	    
    	    /*//DATE OF HIV TEST
    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DATE_OF_HIV_TEST);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setHivTestDate(sdf.format(obsList.get(0).getValueDatetime()));*/
    	    
    	    Date hivTestDate = tf.getHivTestDate();
    	    if(hivTestDate!=null)
      	    	tb03uData.setHivTestDate(sdf.format(hivTestDate));
    	    
    	  //DATE OF ART START
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DATE_OF_ART_TREATMENT_START);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setArtStartDate(sdf.format(obsList.get(0).getValueDatetime()));*/
    	    
    	    Date artStartDate = tf.getArtStartDate();
    	    if(artStartDate!=null)
      	    	tb03uData.setArtStartDate(sdf.format(artStartDate));
    	    
    	  //DATE OF CP START
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DATE_OF_PCT_TREATMENT_START);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setCpStartDate(sdf.format(obsList.get(0).getValueDatetime()));  */
    	    
    	    Date pctStartDate = tf.getPctStartDate();
    	    if(pctStartDate!=null)
      	    	tb03uData.setCpStartDate(sdf.format(pctStartDate));
    	    
    	
    	    
    	    //FOLLOW-UP SMEARS
    	    //accordingly look for smears
    	  
    	    Smear followupSmear = TB03uUtil.getFollowupSmear(tf, 0);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth0SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth0SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 1);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth1SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth1SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 2);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth2SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth2SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 3);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth3SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth3SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 4);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth4SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth4SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 5);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth5SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth5SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 6);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth6SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth6SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 7);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth7SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth7SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 8);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth8SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth8SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 9);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth9SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth9SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 10);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth10SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth10SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 11);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth11SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth11SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 12);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth12SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth12SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 15);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth15SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth15SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 18);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth18SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth18SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 21);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth21SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth21SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 24);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth24SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth24SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 27);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth27SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth27SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 30);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth30SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth30SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 33);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth33SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth33SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    followupSmear = TB03uUtil.getFollowupSmear(tf, 36);
    	    if(followupSmear!=null) {
    	    	if(followupSmear.getResult()!=null) 
    	    		tb03uData.setMonth36SmearResult(followupSmear.getResult().getName().getShortName());
    	    	if(followupSmear.getResultDate()!=null)
    	    	    tb03uData.setMonth36SmearResultDate(sdf.format(followupSmear.getResultDate()));	
    	   }
    	    
    	    //follow CULTURES
    	    Culture followupCulture = TB03uUtil.getFollowupCulture(tf, 0);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth0CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth0CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 1);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth1CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth1CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 2);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth2CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth2CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 3);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth3CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth3CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 4);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth4CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth4CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 5);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth5CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth5CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 6);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth6CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth6CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 7);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth7CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth7CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 8);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth8CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth8CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 9);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth9CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth9CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 10);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth10CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth10CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 11);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth11CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth11CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 12);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth12CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth12CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 15);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth15CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth15CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 18);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth18CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth18CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 21);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth21CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth21CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 24);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth24CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth24CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 27);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth27CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth27CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 30);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth30CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth30CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 33);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth33CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth33CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    followupCulture = TB03uUtil.getFollowupCulture(tf, 36);
    	    if(followupCulture!=null) {
    	    	if(followupCulture.getResult()!=null) 
    	    		tb03uData.setMonth36CultureResult(followupCulture.getResult().getName().getShortName());
    	    	if(followupCulture.getResultDate()!=null)
    	    	    tb03uData.setMonth36CultureResultDate(sdf.format(followupCulture.getResultDate()));	
    	   }
    	    
    	    
    	    //TX OUTCOME
    	    //CHECK CAUSE OF DEATH
    	   
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAUSE_OF_DEATH);
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
	    		tb03Data.setDiedOfTB(false);*/
    	    
    	    q = tf.getCauseOfDeath();//Context.getService(MdrtbService.class).getConcept(TbConcepts.CAUSE_OF_DEATH);
    	    
    	    if(q!=null)
    	    {	
    	    	codId = q.getConceptId();
    	    	if(codId.equals(Context.getService(MdrtbService.class).getConcept(TbConcepts.DEATH_BY_TB).getConceptId()))
    	    		tb03uData.setDiedOfTB(true);
    	    	else
    	    		tb03uData.setDiedOfTB(false);
    	    }
    	    
    	    else
	    		tb03uData.setDiedOfTB(false);
    	    
    	    
    	    //RELAPSED
//    	    q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RELAPSED);
//    	    conceptQuestionList.clear();
//    	    conceptQuestionList.add(q);
//    	    
//    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
//    	    if(obsList.size()>0 && obsList.get(0)!=null)
//    	    	tb03Data.setRelapsed(obsList.get(0).getValueCoded().getName().getName());
    	    
    	    q = tf.getRelapsed();
    	    
    	    if(q!=null) {
    	    	tb03uData.setRelapsed(q.getName().getName());
    	    }
    	    
    	    //RELAPSED AT MONTH?
    	    //RELAPSED
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RELAPSE_MONTH);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
        	    if(obsList.size()>0 && obsList.get(0)!=null)
        	    	tb03Data.setRelapseMonth(obsList.get(0).getValueNumeric().intValue());*/
        	 
    	   Integer relMonth = tf.getRelapseMonth();
    	   
    	   if(relMonth!=null) {
    		   tb03uData.setRelapseMonth(relMonth);
    	   }
        	    	
        	//TX OUTCOME
    	    
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TB_TX_OUTCOME);
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
    	    }*/
    	    
    	   q = tf.getTreatmentOutcome();
    	   if(q!=null) {
   	    	tb03uData.setTb03uTreatmentOutcome(q.getConceptId());
   	    	
   	    	 Date txOutcomeDate = tf.getTreatmentOutcomeDate();
   	    	 if(txOutcomeDate!=null) {
   	    	    	tb03uData.setTb03uTreatmentOutcomeDate(sdf.format(txOutcomeDate));
   	    	    }
   	    	}
    	    
    	    //NOTES
    	    
    	   /* q = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CLINICIAN_NOTES);
    	    conceptQuestionList.clear();
    	    conceptQuestionList.add(q);
    	    
    	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList.size()>0 && obsList.get(0)!=null)
    	    	tb03Data.setNotes(obsList.get(0).getValueText());
    	    
    	    patientSet.add(tb03Data);*/
    	   
    	   String notes = tf.getClinicianNotes();
    	   
    	   if(notes!=null)
   	    	tb03uData.setNotes(notes);
    	    
    	    regimenConceptId = null;
        	codId = null;
        	q = null;
        	
        	patientSet.add(tb03uData);
    	   
    	}
    	
    	Collections.sort(patientSet);
    	Integer num = patientSet.size();
    	model.addAttribute("num", num);
    	model.addAttribute("patientSet", patientSet);
    	
    	// TO CHECK WHETHER REPORT IS CLOSED OR NOT
    	Integer report_oblast = null; Integer report_quarter = null; Integer report_month = null;
		/*if(new PDFHelper().isInt(oblast)) { report_oblast = Integer.parseInt(oblast); }
		if(new PDFHelper().isInt(quartert)) { report_quarter = Integer.parseInt(quarter); }
		if(new PDFHelper().isInt(month)) { report_month = Integer.parseInt(month); }*/
		
    	boolean reportStatus = Context.getService(MdrtbService.class).readReportStatus(oblastId, districtId, facilityId, year, quarter, month, "TB-03u","MDRTB");
		System.out.println(reportStatus);
		model.addAttribute("oblast", oblastId);
    	model.addAttribute("district", districtId);
    	model.addAttribute("facility", facilityId);
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
    	
    	
        return "/module/mdrtb/reporting/tb03uResults";
    }
    
    
  
    
}
