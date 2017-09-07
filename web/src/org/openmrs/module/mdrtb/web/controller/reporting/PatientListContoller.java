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
import org.openmrs.PatientProgram;
import org.openmrs.Person;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.form.CultureForm;
import org.openmrs.module.mdrtb.form.Form89;
import org.openmrs.module.mdrtb.form.HAINForm;
import org.openmrs.module.mdrtb.form.SmearForm;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.form.TB03uForm;
import org.openmrs.module.mdrtb.form.XpertForm;
import org.openmrs.module.mdrtb.reporting.data.Cohorts;
import org.openmrs.module.mdrtb.MdrtbConceptMap;
import org.openmrs.module.mdrtb.MdrtbConstants;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.TbUtil;
import org.openmrs.module.mdrtb.reporting.PDFHelper;
import org.openmrs.module.mdrtb.reporting.ReportUtil;
import org.openmrs.module.mdrtb.reporting.TB03uData;
import org.openmrs.module.mdrtb.reporting.TB03uUtil;
import org.openmrs.module.mdrtb.reporting.TB08uData;
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
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
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

public class PatientListContoller {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
    
    @RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/patientLists")
    public void showRegimenOptions(ModelMap model) {
        List<Location> locations = Context.getLocationService().getAllLocations(false);//ms = (MdrtbDrugForecastService) Context.getService(MdrtbDrugForecastService.class);
        List<Oblast> oblasts = Context.getService(MdrtbService.class).getOblasts();
        //drugSets =  ms.getMdrtbDrugs();
        model.addAttribute("locations", locations);
        model.addAttribute("oblasts", oblasts);
    }
    
    @RequestMapping("/module/mdrtb/reporting/allCasesEnrolled")
    public  String allCasesEnrolled(@RequestParam("location") Location location,
    		@RequestParam("oblast") String oblast,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		System.out.println("allCasesEnrolled");
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblast!=null &&  oblast.length()!=0) {
    			oName = ms.getOblast(Integer.parseInt(oblast)).getName();
    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("location", location);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		model.addAttribute("listName", getMessage("mdrtb.allCasesEnrolled"));

    		String report = "";

    		ArrayList<TB03Form> tb03s = Context.getService(MdrtbService.class).getTB03FormsFilled(location, oblast,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		//NEW CASES 
    		
    		report += "<h4>" + getMessage("mdrtb.pulmonary") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Person p = null;
    		for(TB03Form tf : tb03s) {
    			
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    		}
    		
    				
    		report += closeTable();

    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    
    @RequestMapping("/module/mdrtb/reporting/dotsCasesByRegistrationGroup")
    public  String dotsCasesByRegistrationGroup(@RequestParam("location") Location location,
    		@RequestParam("oblast") String oblast,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		System.out.println("dotsCasesByRegistrationGroup");
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblast!=null && oblast.length()!=0) {
    			oName = ms.getOblast(Integer.parseInt(oblast)).getName();
    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("location", location);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		model.addAttribute("listName", getMessage("mdrtb.dotsCasesByRegistrationGroup"));
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.PATIENT_GROUP);
    		
    		
    		ArrayList<TB03Form> tb03s = Context.getService(MdrtbService.class).getTB03FormsFilled(location, oblast,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		//NEW CASES 
    		Concept newConcept = ms.getConcept(TbConcepts.NEW);
    		report += "<h4>" + getMessage("mdrtb.new") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		for(TB03Form tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==newConcept.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		//Relapse
    		Concept relapse1Concept = ms.getConcept(TbConcepts.RELAPSE_AFTER_REGIMEN_1);
    		Concept relapse2Concept = ms.getConcept(TbConcepts.RELAPSE_AFTER_REGIMEN_2);
    		report += "<h4>" + getMessage("mdrtb.relapsed") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		
    		p = null;
    		for(TB03Form tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && (temp.getValueCoded().getId().intValue()==relapse1Concept.getId().intValue() || temp.getValueCoded().getId().intValue()==relapse2Concept.getId().intValue())) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//Retreament
    		Concept default1Concept = ms.getConcept(TbConcepts.DEFAULT_AFTER_REGIMEN_1);
    		Concept default2Concept = ms.getConcept(TbConcepts.DEFAULT_AFTER_REGIMEN_2);
    		Concept failure1Concept = ms.getConcept(TbConcepts.AFTER_FAILURE_REGIMEN_1);
    		Concept failure2Concept = ms.getConcept(TbConcepts.AFTER_FAILURE_REGIMEN_1);
    		report += "<h4>" + getMessage("mdrtb.retreatment") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		
    		p = null;
    		for(TB03Form tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && 
    					(temp.getValueCoded().getId().intValue()==default1Concept.getId().intValue() ||
    					temp.getValueCoded().getId().intValue()==default2Concept.getId().intValue() || 
    					temp.getValueCoded().getId().intValue()==failure1Concept.getId().intValue() ||
    					temp.getValueCoded().getId().intValue()==failure2Concept.getId().intValue())
    					) {
    				
    				
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		report += "<br/>";
    		
    		//Transfer In
    		Concept transferInConcept = ms.getConcept(TbConcepts.TRANSFER);
    		
    		report += "<h4>" + getMessage("mdrtb.transferIn") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		temp = null;
    		
    		p = null;
    		for(TB03Form tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && 
    					temp.getValueCoded().getId().intValue()==transferInConcept.getId().intValue())
    					 {
    				
    				
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    //////////
    
    @RequestMapping("/module/mdrtb/reporting/dotsCasesByAnatomicalSite")
    public  String dotsCasesByAnatomicalSite(@RequestParam("location") Location location,
    		@RequestParam("oblast") String oblast,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		System.out.println("dotsCasesByAnatomicalSite");
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblast!=null &&  oblast.length()!=0) {
    			oName = ms.getOblast(Integer.parseInt(oblast)).getName();
    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("location", location);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		model.addAttribute("listName", getMessage("mdrtb.dotsCasesByAnatomicalSite"));
    		
    		
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.ANATOMICAL_SITE_OF_TB);
    		
    		
    		ArrayList<TB03Form> tb03s = Context.getService(MdrtbService.class).getTB03FormsFilled(location, oblast,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		//NEW CASES 
    		Concept pulConcept = ms.getConcept(TbConcepts.PULMONARY_TB);
    		report += "<h4>" + getMessage("mdrtb.pulmonary") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		for(TB03Form tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==pulConcept.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		
    		//EP
    		Concept epConcept = ms.getConcept(TbConcepts.EXTRA_PULMONARY_TB);
    		
    		report += "<h4>" + getMessage("mdrtb.extrapulmonary") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		temp = null;
    		
    		p = null;
    		for(TB03Form tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && 
    					temp.getValueCoded().getId().intValue()==epConcept.getId().intValue()) {
    				
    				
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    
    //////////////////////////////
    
    @RequestMapping("/module/mdrtb/reporting/dotsPulmonaryCasesByRegisrationGroupAndBacStatus")
    public  String dotsPulmonaryCasesByRegisrationGroupAndBacStatus(@RequestParam("location") Location location,
    		@RequestParam("oblast") String oblast,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    	
    	MdrtbService ms = Context.getService(MdrtbService.class);
		
		String oName = "";
		if(oblast!=null &&  oblast.length()!=0) {
			oName = ms.getOblast(Integer.parseInt(oblast)).getName();
		}
		
		model.addAttribute("oblast", oName);
		model.addAttribute("location", location);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("quarter", quarter);
		model.addAttribute("listName", getMessage("mdrtb.dotsPulmonaryCasesByRegisrationGroupAndBacStatus"));
    	            
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.PATIENT_GROUP);
    		Concept siteConcept = ms.getConcept(TbConcepts.ANATOMICAL_SITE_OF_TB);
    		Concept pulConcept = ms.getConcept(TbConcepts.PULMONARY_TB);
    		
    		ArrayList<TB03Form> tb03s = Context.getService(MdrtbService.class).getTB03FormsFilled(location, oblast,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		//NEW CASES + Positive
    		Concept newConcept = ms.getConcept(TbConcepts.NEW);
    		report += "<h4>" + getMessage("mdrtb.new") + "-" +  getMessage("mdrtb.pulmonary") + "-" + getMessage("mdrtb.bacPositive") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Obs temp2 = null;
    		Person p = null;
    		for(TB03Form tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(siteConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==newConcept.getId().intValue()) {
    				if(temp2!=null && temp2.getValueCoded()!=null && temp2.getValueCoded().getId().intValue()==pulConcept.getId().intValue()) {
    					if(MdrtbUtil.isBacPositive(tf)) {
    						p = Context.getPersonService().getPerson(tf.getPatient().getId());
    						report += openTR();
    						report += openTD() + getRegistrationNumber(tf) +  closeTD();
    						report += renderPerson(p);
    						report += openTD() + getPatientLink(tf) + closeTD(); 
    						report += closeTR();
    					}
    				}
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//NEW CASES + Negative
    		
    		report += "<h4>" + getMessage("mdrtb.new") + "-" +  getMessage("mdrtb.pulmonary") + "-" + getMessage("mdrtb.bacNegative") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		temp2 = null;
    		p = null;
    		for(TB03Form tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(siteConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==newConcept.getId().intValue()) {
    				if(temp2!=null && temp2.getValueCoded()!=null && temp2.getValueCoded().getId().intValue()==pulConcept.getId().intValue()) {
    					if(!MdrtbUtil.isBacPositive(tf)) {
    						p = Context.getPersonService().getPerson(tf.getPatient().getId());
    						report += openTR();
    						report += openTD() + getRegistrationNumber(tf) +  closeTD();
    						report += renderPerson(p);
    						report += openTD() + getPatientLink(tf) + closeTD(); 
    						report += closeTR();
    					}
    				}
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		//Relapse + positive
    		Concept relapse1Concept = ms.getConcept(TbConcepts.RELAPSE_AFTER_REGIMEN_1);
    		Concept relapse2Concept = ms.getConcept(TbConcepts.RELAPSE_AFTER_REGIMEN_2);
    		report += "<h4>" + getMessage("mdrtb.relapsed")  + "-" +  getMessage("mdrtb.pulmonary") + "-" + getMessage("mdrtb.bacPositive") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		
    		p = null;
    		for(TB03Form tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(siteConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && (temp.getValueCoded().getId().intValue()==relapse1Concept.getId().intValue() || temp.getValueCoded().getId().intValue()==relapse2Concept.getId().intValue())) {
    				if(temp2!=null && temp2.getValueCoded()!=null && temp2.getValueCoded().getId().intValue()==pulConcept.getId().intValue()) {
    					if(MdrtbUtil.isBacPositive(tf)) {
    							p = Context.getPersonService().getPerson(tf.getPatient().getId());
    							report += openTR();
    							report += openTD() + getRegistrationNumber(tf) +  closeTD();
    							report += renderPerson(p);
    							report += openTD() + getPatientLink(tf) + closeTD(); 
    							report += closeTR();
    					}
    				}
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		//Relapse + negative
    		report += "<h4>" + getMessage("mdrtb.relapsed")  + "-" +  getMessage("mdrtb.pulmonary") + "-" + getMessage("mdrtb.bacNegative") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		
    		p = null;
    		for(TB03Form tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(siteConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && (temp.getValueCoded().getId().intValue()==relapse1Concept.getId().intValue() || temp.getValueCoded().getId().intValue()==relapse2Concept.getId().intValue())) {
    				if(temp2!=null && temp2.getValueCoded()!=null && temp2.getValueCoded().getId().intValue()==pulConcept.getId().intValue()) {
    					if(!MdrtbUtil.isBacPositive(tf)) {
    							p = Context.getPersonService().getPerson(tf.getPatient().getId());
    							report += openTR();
    							report += openTD() + getRegistrationNumber(tf) +  closeTD();
    							report += renderPerson(p);
    							report += openTD() + getPatientLink(tf) + closeTD(); 
    							report += closeTR();
    					}
    				}
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//Retreament - Negative
    		Concept default1Concept = ms.getConcept(TbConcepts.DEFAULT_AFTER_REGIMEN_1);
    		Concept default2Concept = ms.getConcept(TbConcepts.DEFAULT_AFTER_REGIMEN_2);
    		Concept failure1Concept = ms.getConcept(TbConcepts.AFTER_FAILURE_REGIMEN_1);
    		Concept failure2Concept = ms.getConcept(TbConcepts.AFTER_FAILURE_REGIMEN_1);
    		report += "<h4>" + getMessage("mdrtb.retreatment") + "-" + getMessage("mdrtb.pulmonary") + "-" + getMessage("mdrtb.bacNegative") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		
    		p = null;
    		for(TB03Form tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(siteConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && 
    					(temp.getValueCoded().getId().intValue()==default1Concept.getId().intValue() ||
    					temp.getValueCoded().getId().intValue()==default2Concept.getId().intValue() || 
    					temp.getValueCoded().getId().intValue()==failure1Concept.getId().intValue() ||
    					temp.getValueCoded().getId().intValue()==failure2Concept.getId().intValue())
    					) {
    				if(temp2!=null && temp2.getValueCoded()!=null && temp2.getValueCoded().getId().intValue()==pulConcept.getId().intValue()) {
    					if(!MdrtbUtil.isBacPositive(tf)) {
    				
    						p = Context.getPersonService().getPerson(tf.getPatient().getId());
    						report += openTR();
    						report += openTD() + getRegistrationNumber(tf) +  closeTD();
    						report += renderPerson(p);
    						report += openTD() + getPatientLink(tf) + closeTD(); 
    						report += closeTR();
    					}
    				}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += "<br/>";
    		
    		report += "<h4>" + getMessage("mdrtb.retreatment") + "-" + getMessage("mdrtb.pulmonary") + "-" + getMessage("mdrtb.bacPositive") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		
    		p = null;
    		for(TB03Form tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(siteConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && 
    					(temp.getValueCoded().getId().intValue()==default1Concept.getId().intValue() ||
    					temp.getValueCoded().getId().intValue()==default2Concept.getId().intValue() || 
    					temp.getValueCoded().getId().intValue()==failure1Concept.getId().intValue() ||
    					temp.getValueCoded().getId().intValue()==failure2Concept.getId().intValue())
    					) {
    				if(temp2!=null && temp2.getValueCoded()!=null && temp2.getValueCoded().getId().intValue()==pulConcept.getId().intValue()) {
    					if(MdrtbUtil.isBacPositive(tf)) {
    				
    						p = Context.getPersonService().getPerson(tf.getPatient().getId());
    						report += openTR();
    						report += openTD() + getRegistrationNumber(tf) +  closeTD();
    						report += renderPerson(p);
    						report += openTD() + getPatientLink(tf) + closeTD(); 
    						report += closeTR();
    					}
    				}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += "<br/>";
    		
    		//Transfer In
    		Concept transferInConcept = ms.getConcept(TbConcepts.TRANSFER);
    		
    		report += "<h4>" + getMessage("mdrtb.transferIn") + "-" + getMessage("mdrtb.pulmonary") + "-" + getMessage("mdrtb.bacPositive") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		temp = null;
    		
    		p = null;
    		for(TB03Form tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(siteConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && 
    					temp.getValueCoded().getId().intValue()==transferInConcept.getId().intValue())
    					 {
    					if(temp2!=null && temp2.getValueCoded()!=null && temp2.getValueCoded().getId().intValue()==pulConcept.getId().intValue()) {
    						if(MdrtbUtil.isBacPositive(tf)) {
    							
    							p = Context.getPersonService().getPerson(tf.getPatient().getId());
    							report += openTR();
    							report += openTD() + getRegistrationNumber(tf) +  closeTD();
    							report += renderPerson(p);
    							report += openTD() + getPatientLink(tf) + closeTD(); 
    							report += closeTR();
    				
    						}
    					}
    			  }
    		}
    				
    		report += closeTable();
    		report += "<br/>";
    		
    		//Transfer In
    		
    		
    		report += "<h4>" + getMessage("mdrtb.transferIn") + "-" + getMessage("mdrtb.pulmonary") + "-" + getMessage("mdrtb.bacNegative") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		temp = null;
    		
    		p = null;
    		for(TB03Form tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(siteConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && 
    					temp.getValueCoded().getId().intValue()==transferInConcept.getId().intValue())
    					 {
    					if(temp2!=null && temp2.getValueCoded()!=null && temp2.getValueCoded().getId().intValue()==pulConcept.getId().intValue()) {
    						if(!MdrtbUtil.isBacPositive(tf)) {
    							
    							p = Context.getPersonService().getPerson(tf.getPatient().getId());
    							report += openTR();
    							report += openTD() + getRegistrationNumber(tf) +  closeTD();
    							report += renderPerson(p);
    							report += openTD() + getPatientLink(tf) + closeTD(); 
    							report += closeTR();
    				
    						}
    					}
    			  }
    		}
    				
    		report += closeTable();
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    //////////
    @RequestMapping("/module/mdrtb/reporting/mdrXdrPatientsNoTreatment")
    public  String mdrXdrPatientsNoTreatment(@RequestParam("location") Location location,
    		@RequestParam("oblast") String oblast,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		System.out.println("mdrXdrPatientsNoTreatment");
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblast!=null &&  oblast.length()!=0) {
    			oName = ms.getOblast(Integer.parseInt(oblast)).getName();
    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("location", location);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		model.addAttribute("listName", getMessage("mdrtb.mdrXdrPatientsNoTreatment"));
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.RESISTANCE_TYPE);
    		Concept treatmentStartDate = ms.getConcept(MdrtbConcepts.MDR_TREATMENT_START_DATE);
    		
    		
    		ArrayList<TB03uForm> tb03s = Context.getService(MdrtbService.class).getTB03uFormsFilled(location, oblast,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		//NEW CASES 
    		Concept mdr = ms.getConcept(MdrtbConcepts.MDR_TB);
    		report += "<h4>" + getMessage("mdrtb.mdrtb") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Obs temp2 = null;
    		Person p = null;
    		for(TB03uForm tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(treatmentStartDate, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==mdr.getId().intValue() && (temp2==null || temp2.getValueDatetime()==null)) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		
    		Concept xdr = ms.getConcept(TbConcepts.XDR_TB);
    		
    		report += "<h4>" + getMessage("mdrtb.xdrtb") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		temp = null;
    		
    		p = null;
    		for(TB03uForm tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(treatmentStartDate, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && 
    					temp.getValueCoded().getId().intValue()==xdr.getId().intValue()  && (temp2==null || temp2.getValueDatetime()==null)) {
    				
    				
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    
    //////////////////////////////

    @RequestMapping("/module/mdrtb/reporting/mdrSuccessfulTreatmentOutcome")
    public  String mdrSuccessfulTreatmentOutcome(@RequestParam("location") Location location,
    		@RequestParam("oblast") String oblast,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		System.out.println("mdrSuccessfulTreatmentOutcome");
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblast!=null &&  oblast.length()!=0) {
    			oName = ms.getOblast(Integer.parseInt(oblast)).getName();
    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("location", location);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		model.addAttribute("listName", getMessage("mdrtb.mdrSuccessfulTreatmentOutcome"));
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(MdrtbConcepts.MDR_TB_TX_OUTCOME);
    		Concept curedConcept = ms.getConcept(MdrtbConcepts.CURED);
    		Concept txCompleted = ms.getConcept(MdrtbConcepts.TREATMENT_COMPLETE);
    		
    		
    		ArrayList<TB03uForm> tb03s = Context.getService(MdrtbService.class).getTB03uFormsFilled(location, oblast,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		//NEW CASES 
    		
    		report += "<h4>" + getMessage("mdrtb.mdrSuccessfulTreatment") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		for(TB03uForm tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && (temp.getValueCoded().getId().intValue()==curedConcept.getId().intValue() || 
    					temp.getValueCoded().getId().intValue()==txCompleted.getId().intValue())) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    
    //////////
    @RequestMapping("/module/mdrtb/reporting/mdrXdrPatients")
    public  String mdrXdrPatients(@RequestParam("location") Location location,
    		@RequestParam("oblast") String oblast,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		System.out.println("mdrXdrPatients");
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblast!=null &&  oblast.length()!=0) {
    			oName = ms.getOblast(Integer.parseInt(oblast)).getName();
    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("location", location);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		model.addAttribute("listName", getMessage("mdrtb.mdrXdrPatients"));
    		
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.RESISTANCE_TYPE);
    		
    		
    		ArrayList<TB03uForm> tb03s = Context.getService(MdrtbService.class).getTB03uFormsFilled(location, oblast,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		//NEW CASES 
    		Concept mdr = ms.getConcept(MdrtbConcepts.MDR_TB);
    		report += "<h4>" + getMessage("mdrtb.mdrtb") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		for(TB03uForm tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==mdr.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		
    		//EP
    		Concept xdr = ms.getConcept(TbConcepts.XDR_TB);
    		
    		report += "<h4>" + getMessage("mdrtb.xdrtb") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		temp = null;
    		
    		p = null;
    		for(TB03uForm tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && 
    					temp.getValueCoded().getId().intValue()==xdr.getId().intValue()) {
    				
    				
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    //
    //////////////////////////////

    @RequestMapping("/module/mdrtb/reporting/womenOfChildbearingAge")
    public  String womenOfChildbearingAge(@RequestParam("location") Location location,
    		@RequestParam("oblast") String oblast,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		System.out.println("womenOfChildbearingAge");
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblast!=null &&  oblast.length()!=0) {
    			oName = ms.getOblast(Integer.parseInt(oblast)).getName();
    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("location", location);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		model.addAttribute("listName", getMessage("mdrtb.womenOfChildbearingAge"));
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.AGE_AT_FORM89_REGISTRATION);
    		
    		
    		
    		ArrayList<Form89> forms = Context.getService(MdrtbService.class).getForm89FormsFilled(location, oblast,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		//NEW CASES 
    		
    		report += "<h4>" + getMessage("mdrtb.womenOfChildbearingAge") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		for(Form89 tf : forms) {
    			if(tf.getPatient().getGender().equals("F")) {
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && temp.getValueNumeric().intValue()>=15  && temp.getValueNumeric().intValue()<=49) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					report += openTR();
    					report += openTD() + tf.getTb03RegistrationNumber() +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    			}
    				
    		}
    	}
    				
    		report += closeTable();
    		
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    //////////
    @RequestMapping("/module/mdrtb/reporting/menOfConscriptAge")
    public  String menOfConscriptAge(@RequestParam("location") Location location,
    		@RequestParam("oblast") String oblast,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		System.out.println("menOfConscriptAge");
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblast!=null &&  oblast.length()!=0) {
    			oName = ms.getOblast(Integer.parseInt(oblast)).getName();
    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("location", location);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		model.addAttribute("listName", getMessage("mdrtb.menOfConscriptAge"));
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.AGE_AT_FORM89_REGISTRATION);
    		
    		
    		
    		ArrayList<Form89> forms = Context.getService(MdrtbService.class).getForm89FormsFilled(location, oblast,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		//NEW CASES 
    		
    		report += "<h4>" + getMessage("mdrtb.menOfConscriptAge") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		for(Form89 tf : forms) {
    			if(tf.getPatient().getGender().equals("M")) {
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && temp.getValueNumeric().intValue()>=18  && temp.getValueNumeric().intValue()<=41) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					report += openTR();
    					report += openTD() + tf.getTb03RegistrationNumber() +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    			}
    				
    		}
    	}
    				
    		report += closeTable();
    		
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    ////
    
    @RequestMapping("/module/mdrtb/reporting/detectedFromContact")
    public  String detectedFromContact(@RequestParam("location") Location location,
    		@RequestParam("oblast") String oblast,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		System.out.println("detectedFromContact");
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblast!=null &&  oblast.length()!=0) {
    			oName = ms.getOblast(Integer.parseInt(oblast)).getName();
    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("location", location);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		model.addAttribute("listName", getMessage("mdrtb.detectedFromContact"));
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.CIRCUMSTANCES_OF_DETECTION);
    		
    		
    		
    		ArrayList<Form89> forms = Context.getService(MdrtbService.class).getForm89FormsFilled(location, oblast,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		Concept fromContact = ms.getConcept(TbConcepts.CONTACT_INVESTIGATION);
    		
    		report += "<h4>" + getMessage("mdrtb.detectedFromContact") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		for(Form89 tf : forms) {
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == fromContact.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					report += openTR();
    					report += openTD() + tf.getTb03RegistrationNumber() +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    			}
    				
    		
    	}
    				
    		report += closeTable();
    		
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    //////////
    
    @RequestMapping("/module/mdrtb/reporting/withDiabetes")
    public  String withDiabetes(@RequestParam("location") Location location,
    		@RequestParam("oblast") String oblast,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		System.out.println("withDiabetes");
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblast!=null &&  oblast.length()!=0) {
    			oName = ms.getOblast(Integer.parseInt(oblast)).getName();
    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("location", location);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		model.addAttribute("listName", getMessage("mdrtb.withDiabetes"));
    		
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.DIABETES);
    		
    		
    		
    		ArrayList<Form89> forms = Context.getService(MdrtbService.class).getForm89FormsFilled(location, oblast,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		Concept yes = ms.getConcept(TbConcepts.YES);
    		
    		report += "<h4>" + getMessage("mdrtb.withDiabetes") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		for(Form89 tf : forms) {
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					report += openTR();
    					report += openTD() + tf.getTb03RegistrationNumber() +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    			}
    			
    	}
    				
    		report += closeTable();
    		
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    
    ////////////////////// UTILITY FUNCTIONS???????????????????????????????????????
    private String getMessage(String code) {
    	return Context.getMessageSourceService().getMessage(code);
    }
    
    private String openTable() {
    	return "<table border=\"1\">";
    	
    }
    
    private String closeTable() {
    	return "</table>";
    	
    }
    
    private String openTR() {
    	return "<tr>";
    	
    }
    
    private String closeTR() {
    	return "</tr>";
    	
    }
    
    private String openTD() {
    	return "<td align=\"left\">";
    }
    
    private String closeTD() {
    	return "</td>";
    	
    }
    
    private String renderPerson(Person p) {
    	SimpleDateFormat dateFormat = Context.getDateFormat();
    	dateFormat.setLenient(false);
    	
    	String ret = "";
    	ret += openTD() + p.getFamilyName() + "," + p.getGivenName() + closeTD();
    	ret += openTD() + dateFormat.format(p.getBirthdate()) + closeTD();
    	
    	return ret;
    	
    }
    
    private String getRegistrationNumber(TB03Form form) {
    	String val = "";
    	PatientIdentifier pi = null;
    	Integer ppid = null;
    	Concept ppidConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.PATIENT_PROGRAM_ID);
    	Obs idObs  = MdrtbUtil.getObsFromEncounter(ppidConcept, form.getEncounter());
    	if(idObs==null) {
    		val = null;
    	}
    	
    	else {
    		ppid = idObs.getValueNumeric().intValue();
    		PatientProgram pp = Context.getProgramWorkflowService().getPatientProgram(ppid);
    		
    		if(pp!=null) {
    			pi = Context.getService(MdrtbService.class).getGenPatientProgramIdentifier(pp);
    			if(pi==null) {
    				val = null;
    			}
    			
    			else {
    				val = pi.getIdentifier();
    			}
    		}
    		
    		else {
    			val = null;
    		}
    	}
    	if(val==null || val.length()==0) {
    		val = getMessage("mdrtb.unassigned");
    	}
    	
    	return val;
    }
    
    private String getRegistrationNumber(TB03uForm form) {
    	String val = "";
    	PatientIdentifier pi = null;
    	Integer ppid = null;
    	Concept ppidConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.PATIENT_PROGRAM_ID);
    	Obs idObs  = MdrtbUtil.getObsFromEncounter(ppidConcept, form.getEncounter());
    	if(idObs==null) {
    		val = null;
    	}
    	
    	else {
    		ppid = idObs.getValueNumeric().intValue();
    		PatientProgram pp = Context.getProgramWorkflowService().getPatientProgram(ppid);
    		
    		if(pp!=null) {
    			pi = Context.getService(MdrtbService.class).getGenPatientProgramIdentifier(pp);
    			if(pi==null) {
    				val = null;
    			}
    			
    			else {
    				val = pi.getIdentifier();
    			}
    		}
    		
    		else {
    			val = null;
    		}
    	}
    	if(val==null || val.length()==0) {
    		val = getMessage("mdrtb.unassigned");
    	}
    	
    	return val;
    }
    
   public String getPatientLink(TB03Form form) {
	   
	   String link = null;
	   link = "../program/enrollment.form?patientId=" + form.getPatient().getId();
	   link = "<a href=\"" + link + "\">" + getMessage("mdrtb.view") + "</a>";
	   return link;
   }
   
   
   public String getPatientLink(TB03uForm form) {
	   
	   String link = null;
	   link = "../program/enrollment.form?patientId=" + form.getPatient().getId();
	   link = "<a href=\"" + link + "\">" + getMessage("mdrtb.view") + "</a>";
	   return link;
   }    
   
   public String getPatientLink(Form89 form) {
	   
	   String link = null;
	   link = "../program/enrollment.form?patientId=" + form.getPatient().getId();
	   link = "<a href=\"" + link + "\">" + getMessage("mdrtb.view") + "</a>";
	   return link;
   }
   
   
   
  
  
   
   
}
