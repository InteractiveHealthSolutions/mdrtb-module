package org.openmrs.module.mdrtb.web.controller.reporting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
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

	@SuppressWarnings("rawtypes")
	@RequestMapping(method=RequestMethod.POST)//, value="/module/mdrtb/reporting/viewClosedReportChanges"
    public ModelAndView viewClosedReportsPost(
    		HttpServletRequest request, HttpServletResponse response,
    		@RequestParam("oblast") Integer oblast, 
    		@RequestParam("district") Integer district,
    		@RequestParam("facility") Integer facility, 
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
		
		if(reportName.equals("TB-03u") || reportName.equals("TB-08u") || reportName.equals("TB-07u") || reportName.equals("DQ"))
				reportEncounterTypes.add("TB03u - MDR");
		else 
			reportEncounterTypes.add("TB03");
		
		reportEncounterTypes.add("Specimen Collection");
		
		Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
		
		Date startDate = (Date)(dateMap.get("startDate"));
		Date endDate = (Date)(dateMap.get("endDate"));
		Date closeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(reportDate);
		
		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println(closeDate);
		
		List<Encounter> modifiedEncounters = new ArrayList<Encounter>();
		Map<Integer, Obs> modifiedObs = new HashMap<Integer, Obs>();
		Map<Encounter, Patient> modifiedPatients = new HashMap<Encounter, Patient>();

		List<Encounter> encounters = (List<Encounter>) Context.getService(MdrtbService.class).getEncountersByEncounterTypes(reportEncounterTypes, startDate, endDate, null);
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
					Person person = Context.getPersonService().getPerson(patient.getId());
					if(patient != null) {
						if(patient.getDateCreated() != null) {
							//Patient By Encounter Created Between Start Date and End Date Or Created After Report Close Date
							//if((patient.getDateCreated().after(startDate) && patient.getDateCreated().before(endDate)) || (person.getDateCreated().after(startDate) && person.getDateCreated().before(endDate)) || patient.getDateCreated().after(closeDate) || person.getDateCreated().after(closeDate)) {
								if(patient.getDateChanged() != null) {
									//Patient By Encounter Changed After Report Closed Date
									if(patient.getDateCreated().after(closeDate) || patient.getDateChanged().after(closeDate)) {
										
										if(!modifiedPatients.containsValue(patient))
											modifiedPatients.put(encounter, patient);
									}
								}
								
								else if(person.getDateChanged() != null) {
									//Patient By Encounter Changed After Report Closed Date
									if(person.getDateCreated().after(closeDate) || person.getDateChanged().after(closeDate)) {
										if(!modifiedPatients.containsValue(patient))
											modifiedPatients.put(encounter, patient);
									}
								}
							//}
						}	
					}
					
					
					
					Set<Obs> observationList = encounter.getAllObs(true); // include voided
					if(observationList != null) {
						for (Obs obs : observationList) {
							if(obs.getDateCreated() != null) {
								//Obs By Encounter Created Between Start Date and End Date Or Created After Report Close Date
								//if(obs.getObsDatetime().after(startDate) && obs.getObsDatetime().before(endDate)) {
									//if(obs.getDateVoided() != null) {
										//Obs By Encounter Changed After Report Closed Date
										if(obs.getDateCreated().after(closeDate) || (obs.getDateVoided()!=null && obs.getDateVoided().after(closeDate))) {
											System.out.println("PUTTING:" + obs.getId());
											modifiedObs.put(obs.getId(), obs);
										}
									//}
								//}
							}	
						}
					}
				}
			}
		}
		
		ArrayList<ArrayList<String>> encounterData = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> patientData = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> obsData = new ArrayList<ArrayList<String>>();
		
		for (Encounter encounter : modifiedEncounters) {
			ArrayList<String> tempData = new ArrayList<String>();

			tempData.add(Integer.toString(encounter.getId())); // encounter id
			tempData.add(Integer.toString(encounter.getEncounterType().getId())); // encounter type id
			tempData.add(encounter.getEncounterType().getName()); // encounter type name 
			tempData.add(encounter.getEncounterDatetime().toString()); // encounter date time
			tempData.add(encounter.getDateCreated().toString()); // encounter date create
			tempData.add(encounter.getDateChanged().toString()); // encounter date change
			tempData.add(Integer.toString(encounter.getPatient().getId())); // encounter patient id
			tempData.add(encounter.getPatient().getPersonName().toString()); // encounter patient name
			
			Set<Obs> tempDataObs = encounter.getAllObs();
			String data = "";
			for (Obs obs : tempDataObs) {
				data += obs.getConcept().getName() + " (" + obs.getId() + ")<br/>";
			}
			
			tempData.add(data); // encounter obs data
			encounterData.add(tempData);
		}
		
		Iterator iterator1 = modifiedPatients.entrySet().iterator();
	    while (iterator1.hasNext()) {
			ArrayList<String> tempData = new ArrayList<String>();
	        Map.Entry patientMap = (Map.Entry) iterator1.next();
	        Encounter encounter = (Encounter) patientMap.getKey();
	        Patient patient = (Patient) patientMap.getValue();
			
			tempData.add(Integer.toString(patient.getId())); // patient id
			tempData.add(patient.getPersonName().toString()); // patient name
			tempData.add(Integer.toString(patient.getAge())); // patient age
			tempData.add(patient.getDateCreated().toString()); // patient date created
			tempData.add(patient.getDateChanged().toString()); // patient date changed
	       /* tempData.add(Integer.toString(encounter.getId())); // encounter id
			tempData.add(Integer.toString(encounter.getEncounterType().getId())); // encounter type id
			tempData.add(encounter.getEncounterType().getName()); // encounter type name 
*/			patientData.add(tempData);
			iterator1.remove();
	    }
	    
	    Iterator iterator2 = modifiedObs.entrySet().iterator();
	    while (iterator2.hasNext()) {
			ArrayList<String> tempData = new ArrayList<String>();
	        Map.Entry obsMap = (Map.Entry) iterator2.next();
	        Integer id = (Integer) obsMap.getKey();
	        
	        Obs obs = (Obs) obsMap.getValue();
	        Encounter encounter = obs.getEncounter();
			tempData.add(Integer.toString(obs.getId())); // obs id
			tempData.add(obs.getConcept().getName().toString()); // obs concept name
			tempData.add(obs.getObsDatetime().toString()); // obs date time
			tempData.add(obs.getDateCreated().toString()); // obs create date
			if(obs.getDateVoided()!=null)
				tempData.add(obs.getDateVoided().toString()); // obs change date
			else {
				tempData.add("");
			}
	        tempData.add(Integer.toString(encounter.getId())); //encounter id
			tempData.add(Integer.toString(encounter.getEncounterType().getId())); //encounter type id
			tempData.add(encounter.getEncounterType().getName()); //encounter type name 
			obsData.add(tempData);
			iterator2.remove();
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
		System.out.println("modifiedEncountersSize: "+ modifiedEncounters.size());

		System.out.println("modifiedObs: "+ modifiedObs);
		System.out.println("modifiedPatients: "+ modifiedPatients);
		System.out.println("modifiedEncounters: "+ modifiedEncounters);
		
    	System.out.println("startDate: " + startDate);
    	System.out.println("endDate: " + endDate);
    	System.out.println("closeDate: " + closeDate);*/

    	model.addAttribute("oblast", oblast); 
		model.addAttribute("district", district); 
		model.addAttribute("facility", facility);
		model.addAttribute("year", year); 
		model.addAttribute("quarter", quarter); 
		model.addAttribute("month", month); 
		model.addAttribute("reportName", reportName); 
		model.addAttribute("reportDate", reportDate);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("closeDate", closeDate);

		model.addAttribute("modifiedObs", modifiedObs);
		model.addAttribute("modifiedObsSize", modifiedObs.size());
		
		model.addAttribute("modifiedPatients", modifiedPatients);
		model.addAttribute("modifiedPatientsSize", modifiedPatients.size());

		model.addAttribute("modifiedEncounters", modifiedEncounters);
		model.addAttribute("modifiedEncountersSize", modifiedEncounters.size());

		model.addAttribute("encounterData", encounterData);
		model.addAttribute("encounterDataSize", encounterData.size());
		
		model.addAttribute("patientData", patientData);
		model.addAttribute("patientDataSize", patientData.size());
		
		model.addAttribute("obsData", obsData);
		model.addAttribute("obsDataSize", obsData.size());
		
		/*
		Map<Integer, Patient> testPatient = new HashMap<Integer, Patient>();
		Encounter test1 = Context.getEncounterService().getEncounter(28634);
		testPatient.put(test1.getId(), test1.getPatient());
		model.addAttribute("testPatient", testPatient);
		model.addAttribute("testPatientSize", testPatient.size());

		Map<Integer, Obs> testObs = new HashMap<Integer, Obs>();
		Encounter test2 = Context.getEncounterService().getEncounter(28634);
		Obs o = Context.getObsService().getObs(1);
		testObs.put(test2.getId(), o);
		model.addAttribute("testObs", testObs);
		model.addAttribute("testObsSize", testObs.size());*/

		return new ModelAndView("/module/mdrtb/reporting/viewClosedReportChanges", model);
	}
}
	