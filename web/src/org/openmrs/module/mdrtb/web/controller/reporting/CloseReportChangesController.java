package org.openmrs.module.mdrtb.web.controller.reporting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.reporting.ReportUtil;
import org.openmrs.module.mdrtb.service.MdrtbService;
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
public class CloseReportChangesController {

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/viewClosedReportChanges")
    public void viewClosedReportsGet(ModelMap model) {
		System.out.println("-----View Closed Report Changes GET-----");
	}


	@RequestMapping(method=RequestMethod.POST)//, value="/module/mdrtb/reporting/viewClosedReportChanges"
    public ModelAndView viewClosedReportsPost(
    		HttpServletRequest request, HttpServletResponse response,
    		@RequestParam("oblast") String oblast, 
    		@RequestParam("location") Location location, 
    		@RequestParam("year") Integer year, 
    		@RequestParam("quarter") String quarter, 
    		@RequestParam("month") String month, 

    		@RequestParam("reportName") String reportName, 
    		@RequestParam("reportDate") String reportDate, 
            ModelMap model) throws Exception {
		System.out.println("-----View Closed Report Changes POST-----");

		/* CHANGE DETECTION LOGIC CODE*/

		/*MDRTB EncounterTypes For Report Generation*/
		List<String> reportEncounterTypes = new ArrayList<String>();
		reportEncounterTypes.add("TB03u - MDR");
//		reportEncounterTypes.add("Specimen Collection");
		
		Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
		
		Date startDate = (Date)(dateMap.get("startDate"));
		Date endDate = (Date)(dateMap.get("endDate"));
		Date closeDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(reportDate);
		
		List<Encounter> modifiedEncounters = new ArrayList<Encounter>();
		Map<Integer, Obs> modifiedObs = new HashMap<Integer, Obs>();
		Map<Integer, Patient> modifiedPatients = new HashMap<Integer, Patient>();

		List<Encounter> encounters = (List<Encounter>) Context.getService(MdrtbService.class).getEncountersByEncounterTypes(reportEncounterTypes);
		if(encounters != null) {
			for (Encounter encounter : encounters) {
				if(encounter != null) {
					if(encounter.getEncounterDatetime() != null) {
						//Encounter Date Time Between Start Date and End Date
						if(encounter.getEncounterDatetime().after(startDate) && encounter.getEncounterDatetime().before(endDate)) {
							if(encounter.getDateChanged() != null) {
								//Encounter Created Or Changed After Report Closed Date
								if(encounter.getDateCreated().after(closeDate) || encounter.getDateChanged().after(closeDate)) {
									modifiedEncounters.add(encounter);
								}
							}
						}
					}
					
					Patient patient = encounter.getPatient();
					if(patient != null) {
						if(patient.getDateCreated() != null) {
							//Patient By Encounter Created Between Start Date and End Date Or Created After Report Close Date
							if((patient.getDateCreated().after(startDate) && patient.getDateCreated().before(endDate)) || patient.getDateCreated().after(closeDate)) {
								if(patient.getDateChanged() != null) {
									//Patient By Encounter Changed After Report Closed Date
									if(patient.getDateCreated().after(closeDate) || patient.getDateChanged().after(closeDate)) {
										modifiedPatients.put(encounter.getId(), patient);
									}
								}
							}
						}	
					}
					
					Set<Obs> observationList = encounter.getAllObs(true); // include voided
					if(observationList != null) {
						for (Obs obs : observationList) {
							if(obs.getDateCreated() != null) {
								//Obs By Encounter Created Between Start Date and End Date Or Created After Report Close Date
								if(obs.getDateCreated().after(startDate) && obs.getDateCreated().before(endDate)) {
									if(obs.getDateChanged() != null) {
										//Obs By Encounter Changed After Report Closed Date
										if(obs.getDateCreated().after(closeDate) || obs.getDateChanged().after(closeDate)) {
											modifiedObs.put(encounter.getId(), obs);
										}
									}
								}
							}	
						}
					}
				}
			}
		}
		/*System.out.println("\n\n\n");
    	System.out.println("oblast: " + oblast);
    	System.out.println("location: " + location);
		System.out.println("year: " + year);
		System.out.println("quarter: " + quarter);
		System.out.println("month: " + month);
    	System.out.println("reportName: " + reportName);
    	System.out.println("reportDate: " + reportDate);
    	
    	System.out.println("modifiedObsSize: "+ modifiedObs.size());
		System.out.println("modifiedPatientsSize: "+ modifiedPatients.size());
		System.out.println("modifiedEncountersSize: "+ modifiedEncounters.size());*/

		System.out.println("modifiedObs: "+ modifiedObs);
		System.out.println("modifiedPatients: "+ modifiedPatients);
		System.out.println("modifiedEncounters: "+ modifiedEncounters);

    	System.out.println("startDate: " + startDate);
    	System.out.println("endDate: " + endDate);
    	System.out.println("closeDate: " + closeDate);
		
		model.addAttribute("modifiedObs", modifiedObs);
		model.addAttribute("modifiedPatients", modifiedPatients);
		model.addAttribute("modifiedEncounters", modifiedEncounters);
		
		model.addAttribute("modifiedObsSize", modifiedObs.size());
		model.addAttribute("modifiedPatientsSize", modifiedPatients.size());
		model.addAttribute("modifiedEncountersSize", modifiedEncounters.size());

		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("closeDate", closeDate);

    	model.addAttribute("oblast", oblast); 
		model.addAttribute("location", location); 
		model.addAttribute("year", year); 
		model.addAttribute("quarter", quarter); 
		model.addAttribute("month", month); 
		model.addAttribute("reportName", reportName.replaceAll("_", " ").toUpperCase()); 
		model.addAttribute("reportDate", reportDate);

		return new ModelAndView("/module/mdrtb/reporting/viewClosedReportChanges", model);
	}
}
	