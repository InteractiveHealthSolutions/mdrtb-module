package org.openmrs.module.mdrtb.web.controller.reporting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientProgram;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.form.Form89;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.form.TB03uForm;
import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.TbConcepts;
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

public class PatientListContoller {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
    
    @RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/patientLists")
    public ModelAndView showRegimenOptions(@RequestParam(value="loc", required=false) String district,
			@RequestParam(value="ob", required=false) String oblast,
			@RequestParam(value="yearSelected", required=false) Integer year,
			@RequestParam(value="quarterSelected", required=false) String quarter,
			@RequestParam(value="monthSelected", required=false) String month,
			ModelMap model) {
        /*List<Location> locations = Context.getLocationService().getAllLocations(false);//ms = (MdrtbDrugForecastService) Context.getService(MdrtbDrugForecastService.class);
        List<Oblast> oblasts = Context.getService(MdrtbService.class).getOblasts();
        //drugSets =  ms.getMdrtbDrugs();
        model.addAttribute("locations", locations);
        model.addAttribute("oblasts", oblasts);*/
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
    	 return new ModelAndView("/module/mdrtb/reporting/patientLists", model);
    }
    
    @RequestMapping("/module/mdrtb/reporting/allCasesEnrolled")
    public  String allCasesEnrolled(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		
    		ArrayList<TB03Form> tb03s = Context.getService(MdrtbService.class).getTB03FormsFilled(locList, year,quarter,month);
    		model.addAttribute("listName", getMessage("mdrtb.allCasesEnrolled"));
    		String report = "";
    		
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
    public  String dotsCasesByRegistrationGroup(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.dotsCasesByRegistrationGroup"));
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.PATIENT_GROUP);
    		
    		
    		ArrayList<TB03Form> tb03s = Context.getService(MdrtbService.class).getTB03FormsFilled(locList,year,quarter,month);
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

    
    @RequestMapping("/module/mdrtb/reporting/byDrugResistance")
	public String byDrugResistance(
			@RequestParam("district") Integer districtId,
			@RequestParam("oblast") Integer oblastId,
			@RequestParam("facility") Integer facilityId,
			@RequestParam(value = "year", required = true) Integer year,
			@RequestParam(value = "quarter", required = false) String quarter,
			@RequestParam(value = "month", required = false) String month,
			ModelMap model) throws EvaluationException {

		MdrtbService ms = Context.getService(MdrtbService.class);

		String oName = "";
		if (oblastId != null) {
			oName = ms.getOblast(oblastId).getName();
		}

		String dName = "";
		if (districtId != null) {
			dName = ms.getDistrict(districtId).getName();

		}

		String fName = "";
		if (facilityId != null) {
			fName = ms.getFacility(facilityId).getName();

		}

		model.addAttribute("oblast", oName);
		model.addAttribute("district", dName);
		model.addAttribute("facility", fName);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("quarter", quarter);

		ArrayList<Location> locList = Context.getService(MdrtbService.class)
				.getLocationList(oblastId, districtId, facilityId);
		model.addAttribute("listName", getMessage("mdrtb.byPulmonaryLocation"));

		String report = "";

		Concept groupConcept = ms.getConcept(TbConcepts.RESISTANCE_TYPE);

		ArrayList<TB03Form> tb03s = Context.getService(MdrtbService.class)
				.getTB03FormsFilled(locList, year, quarter, month);
		/*
		 * Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter,
		 * month);
		 * 
		 * Date startDate = (Date)(dateMap.get("startDate")); Date endDate =
		 * (Date)(dateMap.get("endDate"));
		 */

		// FOCAL
		Concept q = ms.getConcept(MdrtbConcepts.MONO);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		for (TB03Form tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// RIF
		q = ms.getConcept(MdrtbConcepts.RR_TB);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (TB03Form tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// POLY
		q = ms.getConcept(MdrtbConcepts.PDR_TB);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (TB03Form tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// MDR
		q = ms.getConcept(MdrtbConcepts.MDR_TB);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (TB03Form tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// PRE_XDR_TB
		q = ms.getConcept(MdrtbConcepts.PRE_XDR_TB);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (TB03Form tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// XDR_TB
		q = ms.getConcept(MdrtbConcepts.XDR_TB);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (TB03Form tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";
		
		// TDR
				q = ms.getConcept(MdrtbConcepts.TDR_TB);
				report += "<h4>" + q.getName().getName() + "</h4>";
				report += openTable();
				report += openTR();
				report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
						+ closeTD();
				report += openTD() + getMessage("mdrtb.name") + closeTD();
				report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
				report += openTD() + "" + closeTD();
				report += closeTR();

				temp = null;
				p = null;
				for (TB03Form tf : tb03s) {
					temp = MdrtbUtil.getObsFromEncounter(groupConcept,
							tf.getEncounter());
					if (temp != null
							&& temp.getValueCoded() != null
							&& temp.getValueCoded().getId().intValue() == q.getId()
									.intValue()) {
						p = Context.getPersonService().getPerson(
								tf.getPatient().getId());
						report += openTR();
						report += openTD() + getRegistrationNumber(tf) + closeTD();
						report += renderPerson(p);
						report += openTD() + getPatientLink(tf) + closeTD();
						report += closeTR();

					}
				}

				report += closeTable();

				report += "<br/>";

		// UNKNOWN
		q = ms.getConcept(MdrtbConcepts.UNKNOWN);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (TB03Form tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// NO
		q = ms.getConcept(TbConcepts.NO);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (TB03Form tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		model.addAttribute("report", report);
		return "/module/mdrtb/reporting/patientListsResults";

	}
    
    //////////////////////////////
    
    @RequestMapping("/module/mdrtb/reporting/dotsPulmonaryCasesByRegisrationGroupAndBacStatus")
    public  String dotsPulmonaryCasesByRegisrationGroupAndBacStatus(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
		model.addAttribute("listName", getMessage("mdrtb.dotsPulmonaryCasesByRegisrationGroupAndBacStatus"));
    	            
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.PATIENT_GROUP);
    		Concept siteConcept = ms.getConcept(TbConcepts.ANATOMICAL_SITE_OF_TB);
    		Concept pulConcept = ms.getConcept(TbConcepts.PULMONARY_TB);
    		
    		ArrayList<TB03Form> tb03s = Context.getService(MdrtbService.class).getTB03FormsFilled(locList,year,quarter,month);
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
    public  String mdrXdrPatientsNoTreatment(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.mdrXdrPatientsNoTreatment"));
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.RESISTANCE_TYPE);
    		Concept treatmentStartDate = ms.getConcept(MdrtbConcepts.MDR_TREATMENT_START_DATE);
    		
    		
    		ArrayList<TB03uForm> tb03s = Context.getService(MdrtbService.class).getTB03uFormsFilled(locList,year,quarter,month);
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
    public  String mdrSuccessfulTreatmentOutcome(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.mdrSuccessfulTreatmentOutcome"));
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(MdrtbConcepts.MDR_TB_TX_OUTCOME);
    		Concept curedConcept = ms.getConcept(MdrtbConcepts.CURED);
    		Concept txCompleted = ms.getConcept(MdrtbConcepts.TREATMENT_COMPLETE);
    		
    		
    		ArrayList<TB03uForm> tb03s = Context.getService(MdrtbService.class).getTB03uFormsFilled(locList,year,quarter,month);
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
    public  String mdrXdrPatients(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.mdrXdrPatients"));
    		
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.RESISTANCE_TYPE);
    		
    		
    		ArrayList<TB03uForm> tb03s = Context.getService(MdrtbService.class).getTB03uFormsFilled(locList,year,quarter,month);
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
    public  String womenOfChildbearingAge(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.womenOfChildbearingAge"));
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.AGE_AT_FORM89_REGISTRATION);
    		
    		
    		
    		ArrayList<Form89> forms = Context.getService(MdrtbService.class).getForm89FormsFilled(locList,year,quarter,month);
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
    					//report += openTD() + tf.getTb03RegistrationNumber() +  closeTD();
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
    public  String menOfConscriptAge(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.menOfConscriptAge"));
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.AGE_AT_FORM89_REGISTRATION);
    		
    		
    		
    		ArrayList<Form89> forms = Context.getService(MdrtbService.class).getForm89FormsFilled(locList,year,quarter,month);
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
    					//report += openTD() + tf.getTb03RegistrationNumber() +  closeTD();
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
    public  String detectedFromContact(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.detectedFromContact"));
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.CIRCUMSTANCES_OF_DETECTION);
    		
    		
    		
    		ArrayList<Form89> forms = Context.getService(MdrtbService.class).getForm89FormsFilled(locList,year,quarter,month);
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
    					//report += openTD() + tf.getTb03RegistrationNumber() +  closeTD();
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
    public  String withDiabetes(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.withDiabetes"));
    		
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.DIABETES);
    		
    		
    		
    		ArrayList<Form89> forms = Context.getService(MdrtbService.class).getForm89FormsFilled(locList,year,quarter,month);
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
    					//report += openTD() + tf.getTb03RegistrationNumber() +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    			}
    			
    	}
    				
    		report += closeTable();
    		
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    
    @RequestMapping("/module/mdrtb/reporting/withCancer")
    public  String withCancer(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.withCancer"));
    		
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.CANCER);
    		
    		
    		
    		ArrayList<Form89> forms = Context.getService(MdrtbService.class).getForm89FormsFilled(locList,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		Concept yes = ms.getConcept(TbConcepts.YES);
    		
    		report += "<h4>" + getMessage("mdrtb.withCancer") + "</h4>";
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
    					//report += openTD() + tf.getTb03RegistrationNumber() +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    			}
    			
    	}
    				
    		report += closeTable();
    		
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    
    /////////////////
    
    @RequestMapping("/module/mdrtb/reporting/withCOPD")
    public  String withCOPD(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.withCOPD"));
    		
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.CNSDL);
    		
    		
    		
    		ArrayList<Form89> forms = Context.getService(MdrtbService.class).getForm89FormsFilled(locList,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		Concept yes = ms.getConcept(TbConcepts.YES);
    		
    		report += "<h4>" + getMessage("mdrtb.withCOPD") + "</h4>";
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
    					//report += openTD() + tf.getTb03RegistrationNumber() +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    			}
    			
    	}
    				
    		report += closeTable();
    		
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    
 /////////////////
    
    @RequestMapping("/module/mdrtb/reporting/withHypertension")
    public  String withHypertension(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.withHypertension"));
    		
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.HYPERTENSION_OR_HEART_DISEASE);
    		
    		
    		
    		ArrayList<Form89> forms = Context.getService(MdrtbService.class).getForm89FormsFilled(locList,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		Concept yes = ms.getConcept(TbConcepts.YES);
    		
    		report += "<h4>" + getMessage("mdrtb.withHypertension") + "</h4>";
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
    					//report += openTD() + tf.getTb03RegistrationNumber() +  closeTD();
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
    
	@RequestMapping("/module/mdrtb/reporting/withUlcer")
	public String withUlcer(@RequestParam("district") Integer districtId,
			@RequestParam("oblast") Integer oblastId,
			@RequestParam("facility") Integer facilityId,
			@RequestParam(value = "year", required = true) Integer year,
			@RequestParam(value = "quarter", required = false) String quarter,
			@RequestParam(value = "month", required = false) String month,
			ModelMap model) throws EvaluationException {

		MdrtbService ms = Context.getService(MdrtbService.class);

		String oName = "";
		if (oblastId != null) {
			oName = ms.getOblast(oblastId).getName();
		}

		String dName = "";
		if (districtId != null) {
			dName = ms.getDistrict(districtId).getName();

		}

		String fName = "";
		if (facilityId != null) {
			fName = ms.getFacility(facilityId).getName();

		}

		model.addAttribute("oblast", oName);
		model.addAttribute("district", dName);
		model.addAttribute("facility", fName);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("quarter", quarter);

		ArrayList<Location> locList = Context.getService(MdrtbService.class)
				.getLocationList(oblastId, districtId, facilityId);
		model.addAttribute("listName", getMessage("mdrtb.withUlcer"));

		String report = "";

		Concept groupConcept = ms.getConcept(TbConcepts.ULCER);

		ArrayList<Form89> forms = Context.getService(MdrtbService.class)
				.getForm89FormsFilled(locList, year, quarter, month);
		/*
		 * Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter,
		 * month);
		 * 
		 * Date startDate = (Date)(dateMap.get("startDate")); Date endDate =
		 * (Date)(dateMap.get("endDate"));
		 */
		Concept yes = ms.getConcept(TbConcepts.YES);

		report += "<h4>" + getMessage("mdrtb.withUlcer") + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		for (Form89 tf : forms) {

			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& (temp.getValueCoded().getId().intValue() == yes.getId()
							.intValue())) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				// report += openTD() + tf.getTb03RegistrationNumber() +
				// closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

		}

		report += closeTable();

		model.addAttribute("report", report);
		return "/module/mdrtb/reporting/patientListsResults";

	}

	// ////////

	@RequestMapping("/module/mdrtb/reporting/withMentalDisorder")
	public String withMentalDisorder(@RequestParam("district") Integer districtId,
			@RequestParam("oblast") Integer oblastId,
			@RequestParam("facility") Integer facilityId,
			@RequestParam(value = "year", required = true) Integer year,
			@RequestParam(value = "quarter", required = false) String quarter,
			@RequestParam(value = "month", required = false) String month,
			ModelMap model) throws EvaluationException {

		MdrtbService ms = Context.getService(MdrtbService.class);

		String oName = "";
		if (oblastId != null) {
			oName = ms.getOblast(oblastId).getName();
		}

		String dName = "";
		if (districtId != null) {
			dName = ms.getDistrict(districtId).getName();

		}

		String fName = "";
		if (facilityId != null) {
			fName = ms.getFacility(facilityId).getName();

		}

		model.addAttribute("oblast", oName);
		model.addAttribute("district", dName);
		model.addAttribute("facility", fName);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("quarter", quarter);

		ArrayList<Location> locList = Context.getService(MdrtbService.class)
				.getLocationList(oblastId, districtId, facilityId);
		model.addAttribute("listName", getMessage("mdrtb.withMentalDisorder"));

		String report = "";

		Concept groupConcept = ms.getConcept(TbConcepts.MENTAL_DISORDER);

		ArrayList<Form89> forms = Context.getService(MdrtbService.class)
				.getForm89FormsFilled(locList, year, quarter, month);
		/*
		 * Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter,
		 * month);
		 * 
		 * Date startDate = (Date)(dateMap.get("startDate")); Date endDate =
		 * (Date)(dateMap.get("endDate"));
		 */
		Concept yes = ms.getConcept(TbConcepts.YES);

		report += "<h4>" + getMessage("mdrtb.withMentalDisorder") + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		for (Form89 tf : forms) {

			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& (temp.getValueCoded().getId().intValue() == yes.getId()
							.intValue())) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				// report += openTD() + tf.getTb03RegistrationNumber() +
				// closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

		}

		report += closeTable();

		model.addAttribute("report", report);
		return "/module/mdrtb/reporting/patientListsResults";

	}

	// ////////

	@RequestMapping("/module/mdrtb/reporting/withHIV")
	public String withHIV(@RequestParam("district") Integer districtId,
			@RequestParam("oblast") Integer oblastId,
			@RequestParam("facility") Integer facilityId,
			@RequestParam(value = "year", required = true) Integer year,
			@RequestParam(value = "quarter", required = false) String quarter,
			@RequestParam(value = "month", required = false) String month,
			ModelMap model) throws EvaluationException {

		MdrtbService ms = Context.getService(MdrtbService.class);

		String oName = "";
		if (oblastId != null) {
			oName = ms.getOblast(oblastId).getName();
		}

		String dName = "";
		if (districtId != null) {
			dName = ms.getDistrict(districtId).getName();

		}

		String fName = "";
		if (facilityId != null) {
			fName = ms.getFacility(facilityId).getName();

		}

		model.addAttribute("oblast", oName);
		model.addAttribute("district", dName);
		model.addAttribute("facility", fName);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("quarter", quarter);

		ArrayList<Location> locList = Context.getService(MdrtbService.class)
				.getLocationList(oblastId, districtId, facilityId);
		model.addAttribute("listName", getMessage("mdrtb.withHIV"));

		String report = "";

		Concept groupConcept = ms.getConcept(TbConcepts.ICD20);

		ArrayList<Form89> forms = Context.getService(MdrtbService.class)
				.getForm89FormsFilled(locList, year, quarter, month);
		/*
		 * Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter,
		 * month);
		 * 
		 * Date startDate = (Date)(dateMap.get("startDate")); Date endDate =
		 * (Date)(dateMap.get("endDate"));
		 */
		Concept yes = ms.getConcept(TbConcepts.YES);

		report += "<h4>" + getMessage("mdrtb.withHIV") + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		for (Form89 tf : forms) {

			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& (temp.getValueCoded().getId().intValue() == yes.getId()
							.intValue())) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				// report += openTD() + tf.getTb03RegistrationNumber() +
				// closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

		}

		report += closeTable();

		model.addAttribute("report", report);
		return "/module/mdrtb/reporting/patientListsResults";

	}

	// ////////

	@RequestMapping("/module/mdrtb/reporting/withOtherDisease")
	public String withOtherDisease(@RequestParam("district") Integer districtId,
			@RequestParam("oblast") Integer oblastId,
			@RequestParam("facility") Integer facilityId,
			@RequestParam(value = "year", required = true) Integer year,
			@RequestParam(value = "quarter", required = false) String quarter,
			@RequestParam(value = "month", required = false) String month,
			ModelMap model) throws EvaluationException {

		MdrtbService ms = Context.getService(MdrtbService.class);

		String oName = "";
		if (oblastId != null) {
			oName = ms.getOblast(oblastId).getName();
		}

		String dName = "";
		if (districtId != null) {
			dName = ms.getDistrict(districtId).getName();

		}

		String fName = "";
		if (facilityId != null) {
			fName = ms.getFacility(facilityId).getName();

		}

		model.addAttribute("oblast", oName);
		model.addAttribute("district", dName);
		model.addAttribute("facility", fName);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("quarter", quarter);

		ArrayList<Location> locList = Context.getService(MdrtbService.class)
				.getLocationList(oblastId, districtId, facilityId);
		model.addAttribute("listName", getMessage("mdrtb.withOtherDisease"));

		String report = "";

		Concept groupConcept = ms.getConcept(TbConcepts.OTHER_DISEASE);

		ArrayList<Form89> forms = Context.getService(MdrtbService.class)
				.getForm89FormsFilled(locList, year, quarter, month);
		/*
		 * Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter,
		 * month);
		 * 
		 * Date startDate = (Date)(dateMap.get("startDate")); Date endDate =
		 * (Date)(dateMap.get("endDate"));
		 */
		Concept yes = ms.getConcept(TbConcepts.YES);

		report += "<h4>" + getMessage("mdrtb.withOtherDisease") + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		for (Form89 tf : forms) {

			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& (temp.getValueCoded().getId().intValue() == yes.getId()
							.intValue())) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				// report += openTD() + tf.getTb03RegistrationNumber() +
				// closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

		}

		report += closeTable();

		model.addAttribute("report", report);
		return "/module/mdrtb/reporting/patientListsResults";

	}

	// ////////

	
    
    @RequestMapping("/module/mdrtb/reporting/bySocProfStatus")
    public  String bySocProfStatus(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.bySocProfStatus"));
    		
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.PROFESSION);
    		
    		
    		
    		ArrayList<Form89> tb03s = Context.getService(MdrtbService.class).getForm89FormsFilled(locList,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		
    		//WORKER
    		Concept q = ms.getConcept(TbConcepts.WORKER);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//GOVT SERVANT
    		q = ms.getConcept(TbConcepts.GOVT_SERVANT);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//STUDENT
    		q = ms.getConcept(TbConcepts.STUDENT);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//DISABLED
    		q = ms.getConcept(TbConcepts.DISABLED);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//UNEMPLOYED
    		q = ms.getConcept(TbConcepts.UNEMPLOYED);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//PHC WORKER
    		q = ms.getConcept(TbConcepts.PHC_WORKER);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//MILITARY SERVANT
    		q = ms.getConcept(TbConcepts.MILITARY_SERVANT);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//SCHOOLCHILD
    		q = ms.getConcept(TbConcepts.SCHOOLCHILD);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//TB SERVICES WORKER
    		q = ms.getConcept(TbConcepts.TB_SERVICES_WORKER);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		/*report += "<br/>";
    		
    		
    		
    		report += closeTable();*/
    		
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    
    ////////////////////////////////////
    @RequestMapping("/module/mdrtb/reporting/byPopCategory")
    public  String byPopulationCategory(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.byPopCategory"));
    		
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.POPULATION_CATEGORY);
    		
    		ArrayList<Form89> tb03s = Context.getService(MdrtbService.class).getForm89FormsFilled(locList,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		
    		//RESIDENT_OF_TERRITORY
    		Concept q = ms.getConcept(TbConcepts.RESIDENT_OF_TERRITORY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//RESIDENT_OTHER_TERRITORY
    		q = ms.getConcept(TbConcepts.RESIDENT_OTHER_TERRITORY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//FOREIGNER
    		q = ms.getConcept(TbConcepts.FOREIGNER);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//RESIDENT_SOCIAL_SECURITY_FACILITY
    		q = ms.getConcept(TbConcepts.RESIDENT_SOCIAL_SECURITY_FACILITY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//Form89report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//HOMELESS
    		q = ms.getConcept(TbConcepts.HOMELESS);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//CONVICTED
    		q = ms.getConcept(TbConcepts.CONVICTED);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//ON_REMAND
    		q = ms.getConcept(TbConcepts.ON_REMAND);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		/*report += "<br/>";
    		

    		report += closeTable();*/
    		
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    
    
    ////////////////////////////////////
    @RequestMapping("/module/mdrtb/reporting/byDwelling")
    public  String byDwelling(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.byDwelling"));
    		
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.LOCATION_TYPE);
    		
    		ArrayList<Form89> tb03s = Context.getService(MdrtbService.class).getForm89FormsFilled(locList,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		
    		//RESIDENT_OF_TERRITORY
    		Concept q = ms.getConcept(TbConcepts.CITY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//RESIDENT_OTHER_TERRITORY
    		q = ms.getConcept(TbConcepts.VILLAGE);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    //////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////
    @RequestMapping("/module/mdrtb/reporting/byPlaceOfDetection")
    public  String byPlaceOfDetection(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.byPlaceOfDetection"));
    		
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.PLACE_OF_DETECTION);
    		
    		ArrayList<Form89> tb03s = Context.getService(MdrtbService.class).getForm89FormsFilled(locList,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		
    		//TB FACILITY
    		Concept q = ms.getConcept(TbConcepts.TB_FACILITY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//PHC
    		q = ms.getConcept(TbConcepts.PHC_FACILITY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//OTHER MED FAC
    		q = ms.getConcept(TbConcepts.OTHER_MEDICAL_FACILITY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    
    ////////////////////////////////////////////////
    
    @RequestMapping("/module/mdrtb/reporting/byCircumstancesOfDetection")
    public  String byCircumstancesOfDetection(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.byCircumstancesOfDetection"));
    		
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.CIRCUMSTANCES_OF_DETECTION);
    		
    		ArrayList<Form89> tb03s = Context.getService(MdrtbService.class).getForm89FormsFilled(locList,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		
    		//SELF_REFERRAL
    		Concept q = ms.getConcept(TbConcepts.SELF_REFERRAL);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//BASELINE_EXAM
    		q = ms.getConcept(TbConcepts.BASELINE_EXAM);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//POSTMORTERM_IDENTIFICATION
    		q = ms.getConcept(TbConcepts.POSTMORTERM_IDENTIFICATION);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		//CONTACT
    		q = ms.getConcept(TbConcepts.CONTACT);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//MIGRANT
    		q = ms.getConcept(TbConcepts.MIGRANT);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    
////////////////////////////////////////////////
    
	@RequestMapping("/module/mdrtb/reporting/byMethodOfDetection")
	
    public  String byMethodOfDetection(@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException{
    		
    		
    		MdrtbService ms = Context.getService(MdrtbService.class);
    		
    		String oName = "";
    		if(oblastId!=null) {
    			oName = ms.getOblast(oblastId).getName();
    		}
    		
    		String dName = "";
    		if(districtId!=null) {
    			dName = ms.getDistrict(districtId).getName();

    		}
    		
    		String fName = "";
    		if(facilityId!=null) {
    			fName = ms.getFacility(facilityId).getName();

    		}
    		
    		model.addAttribute("oblast", oName);
    		model.addAttribute("district", dName);
    		model.addAttribute("facility", fName);
    		model.addAttribute("year", year);
    		model.addAttribute("month", month);
    		model.addAttribute("quarter", quarter);
    		
    		ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		model.addAttribute("listName", getMessage("mdrtb.byMethodOfDetection"));
    		
    		
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.BASIS_FOR_TB_DIAGNOSIS);
    		
    		
    		
    		ArrayList<Form89> tb03s = Context.getService(MdrtbService.class).getForm89FormsFilled(locList,year,quarter,month);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		
    		//FLUOROGRAPHY
    		Concept q = ms.getConcept(TbConcepts.FLUOROGRAPHY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//GENEXPERT
    		q = ms.getConcept(TbConcepts.GENEXPERT);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//FLURORESCENT_MICROSCOPY
    		q = ms.getConcept(TbConcepts.FLURORESCENT_MICROSCOPY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//TUBERCULIN_TEST
    		q = ms.getConcept(TbConcepts.TUBERCULIN_TEST);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//ZIEHLNELSEN
    		q = ms.getConcept(TbConcepts.ZIEHLNELSEN);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//HAIN_TEST
    		q = ms.getConcept(TbConcepts.HAIN_TEST);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//CULTURE_DETECTION
    		q = ms.getConcept(TbConcepts.CULTURE_DETECTION);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//HISTOLOGY
    		q = ms.getConcept(TbConcepts.HISTOLOGY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//CXR_RESULT
    		q = ms.getConcept(TbConcepts.CXR_RESULT);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		report += "<br/>";
    		
    		//OTHER
    		q = ms.getConcept(TbConcepts.OTHER);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		for(Form89 tf : tb03s) {
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				//report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		
    		
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
	
////////////////////////////////////////////////
    
	@RequestMapping("/module/mdrtb/reporting/byPulmonaryLocation")
	public String byPulmonaryLocation(
			@RequestParam("district") Integer districtId,
			@RequestParam("oblast") Integer oblastId,
			@RequestParam("facility") Integer facilityId,
			@RequestParam(value = "year", required = true) Integer year,
			@RequestParam(value = "quarter", required = false) String quarter,
			@RequestParam(value = "month", required = false) String month,
			ModelMap model) throws EvaluationException {

		MdrtbService ms = Context.getService(MdrtbService.class);

		String oName = "";
		if (oblastId != null) {
			oName = ms.getOblast(oblastId).getName();
		}

		String dName = "";
		if (districtId != null) {
			dName = ms.getDistrict(districtId).getName();
		}

		String fName = "";
		if (facilityId != null) {
			fName = ms.getFacility(facilityId).getName();
		}

		model.addAttribute("oblast", oName);
		model.addAttribute("district", dName);
		model.addAttribute("facility", fName);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("quarter", quarter);

		ArrayList<Location> locList = Context.getService(MdrtbService.class)
				.getLocationList(oblastId, districtId, facilityId);
		model.addAttribute("listName", getMessage("mdrtb.byPulmonaryLocation"));

		String report = "";

		Concept groupConcept = ms.getConcept(TbConcepts.PTB_SITE);

		ArrayList<Form89> tb03s = Context.getService(MdrtbService.class)
				.getForm89FormsFilled(locList, year, quarter, month);

		// FOCAL
		Concept q = ms.getConcept(TbConcepts.FOCAL);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// INFILTRATIVE
		q = ms.getConcept(TbConcepts.INFILTRATIVE);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// DISSEMINATED
		q = ms.getConcept(TbConcepts.DISSEMINATED);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// CAVERNOUS
		q = ms.getConcept(TbConcepts.CAVERNOUS);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// FIBROUS_CAVERNOUS
		q = ms.getConcept(TbConcepts.FIBROUS_CAVERNOUS);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// CIRRHOTIC
		q = ms.getConcept(TbConcepts.CIRRHOTIC);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// TB_PRIMARY_COMPLEX
		q = ms.getConcept(TbConcepts.TB_PRIMARY_COMPLEX);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// MILIARY
		q = ms.getConcept(TbConcepts.MILIARY);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// TUBERCULOMA
		q = ms.getConcept(TbConcepts.TUBERCULOMA);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();


		model.addAttribute("report", report);
		return "/module/mdrtb/reporting/patientListsResults";

	}
	
	// //////////////////////////////////////////////

	@RequestMapping("/module/mdrtb/reporting/byExtraPulmonaryLocation")
	public String byExtraPulmonaryLocation(
			@RequestParam("district") Integer districtId,
			@RequestParam("oblast") Integer oblastId,
			@RequestParam("facility") Integer facilityId,
			@RequestParam(value = "year", required = true) Integer year,
			@RequestParam(value = "quarter", required = false) String quarter,
			@RequestParam(value = "month", required = false) String month,
			ModelMap model) throws EvaluationException {

		MdrtbService ms = Context.getService(MdrtbService.class);

		String oName = "";
		if (oblastId != null) {
			oName = ms.getOblast(oblastId).getName();
		}

		String dName = "";
		if (districtId != null) {
			dName = ms.getDistrict(districtId).getName();

		}

		String fName = "";
		if (facilityId != null) {
			fName = ms.getFacility(facilityId).getName();

		}

		model.addAttribute("oblast", oName);
		model.addAttribute("district", dName);
		model.addAttribute("facility", fName);
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("quarter", quarter);

		ArrayList<Location> locList = Context.getService(MdrtbService.class)
				.getLocationList(oblastId, districtId, facilityId);
		model.addAttribute("listName", getMessage("mdrtb.byExtraPulmonaryLocation"));

		String report = "";

		Concept groupConcept = ms.getConcept(TbConcepts.PTB_SITE);

		ArrayList<Form89> tb03s = Context.getService(MdrtbService.class)
				.getForm89FormsFilled(locList, year, quarter, month);
		/*
		 * Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter,
		 * month);
		 * 
		 * Date startDate = (Date)(dateMap.get("startDate")); Date endDate =
		 * (Date)(dateMap.get("endDate"));
		 */

		// PLEVRITIS
		Concept q = ms.getConcept(TbConcepts.PLEVRITIS);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// OF_LYMPH_NODES
		q = ms.getConcept(TbConcepts.OF_LYMPH_NODES);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// OSTEOARTICULAR
		q = ms.getConcept(TbConcepts.OSTEOARTICULAR);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// GENITOURINARY
		q = ms.getConcept(TbConcepts.GENITOURINARY);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// OF_PERIPHERAL_LYMPH_NODES
		q = ms.getConcept(TbConcepts.OF_PERIPHERAL_LYMPH_NODES);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// ABDOMINAL
		q = ms.getConcept(TbConcepts.ABDOMINAL);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// TUBERCULODERMA
		q = ms.getConcept(TbConcepts.TUBERCULODERMA);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// OCULAR
		q = ms.getConcept(TbConcepts.OCULAR);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
			//	report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		report += "<br/>";

		// OF_CNS
		q = ms.getConcept(TbConcepts.OF_CNS);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				//report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();

		model.addAttribute("report", report);
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
