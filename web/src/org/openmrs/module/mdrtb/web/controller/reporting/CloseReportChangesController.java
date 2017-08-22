package org.openmrs.module.mdrtb.web.controller.reporting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.reporting.ReportUtil;
import org.openmrs.module.mdrtb.reporting.data.Cohorts;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
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


	@RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/viewClosedReportChanges")
    public void viewClosedReportsPost(
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

		/*System.out.println("\n\n\n");
    	System.out.println("oblast: " + oblast);
    	System.out.println("location: " + location);
		System.out.println("year: " + year);
		System.out.println("quarter: " + quarter);
		System.out.println("month: " + month);
    	System.out.println("reportName: " + reportName);
    	System.out.println("reportDate: " + reportDate);
		System.out.println("\n\n\n");*/
		
		
		List<Patient> modifiedPatients1 = new ArrayList<Patient>();
		HashMap<Integer, Obs> modifiedObs1 = new HashMap<Integer, Obs>();
		HashMap<Integer, Encounter> modifiedEncounters1 = new HashMap<Integer, Encounter>();
		
		List<Patient> modifiedPatients2 = new ArrayList<Patient>();
		HashMap<Integer, Obs> modifiedObs2 = new HashMap<Integer, Obs>();
		HashMap<Integer, Encounter> modifiedEncounters2 = new HashMap<Integer, Encounter>();

		System.out.println("\n\n\n");
		test1(oblast, location, year, quarter, month, reportName, reportDate, modifiedPatients1, modifiedObs1, modifiedEncounters1);
		System.out.println("modifiedPatients1: "+ modifiedPatients1);
		System.out.println("modifiedObs1: "+ modifiedObs1);
		System.out.println("modifiedEncounters1: "+ modifiedEncounters1);
		System.out.println("\n\n\n");
		
		
		
		test2(oblast, location, year, quarter, month, reportName, reportDate, modifiedPatients2, modifiedObs2, modifiedEncounters2);
		System.out.println("modifiedPatients2: "+ modifiedPatients2);
		System.out.println("modifiedObs2: "+ modifiedObs2);
		System.out.println("modifiedEncounters2: "+ modifiedEncounters2);
		System.out.println("\n\n\n");

		
		model.addAttribute("modifiedPatients1", modifiedPatients1);
		model.addAttribute("modifiedObs1", modifiedObs1);
		model.addAttribute("modifiedEncounters1", modifiedEncounters1);

		model.addAttribute("modifiedPatients2", modifiedPatients2);
		model.addAttribute("modifiedObs2", modifiedObs2);
		model.addAttribute("modifiedEncounters2", modifiedEncounters2);
		
//		return new ModelAndView("/module/mdrtb/reporting/viewClosedReportContent", model);
	}
	
	@SuppressWarnings("deprecation")
	public void test1(String oblast, Location location, Integer year, String quarter, String month, String reportName, String reportDate
			, List<Patient> modifiedPatients, HashMap<Integer, Obs> modifiedObs, HashMap<Integer, Encounter> modifiedEncounters 
		) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("dd.MM.yyyy");
		
		Oblast o = null;
		if(oblast!=null && !oblast.equals("") && location == null)
			o =  Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblast));
		
		List<Location> locList = new ArrayList<Location>();
		if(o != null && location == null) {
			locList = Context.getService(MdrtbService.class).getLocationsFromOblastName(o);
		}
		else if (location != null) {
			locList.add(location);
		}
		
		Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
		
		Date startDate = (Date)(dateMap.get("startDate"));
		Date endDate = (Date)(dateMap.get("endDate"));
		
		CohortDefinition baseCohort = null;
		
		//OBLAST
		if (!locList.isEmpty()){
			List<CohortDefinition> cohortDefinitions = new ArrayList<CohortDefinition>();
			for(Location loc : locList) {
				cohortDefinitions.add(Cohorts.getTB03uByDatesAndLocation(startDate, endDate, loc));
			}
			if(!cohortDefinitions.isEmpty()){
				baseCohort = ReportUtil.getCompositionCohort("OR", cohortDefinitions);
			}
		}
		
		else {
			baseCohort = Cohorts.getTB03uByDatesAndLocation(startDate, endDate, null);
		}
		Cohort patients = Context.getService(CohortDefinitionService.class).evaluate(baseCohort, new EvaluationContext());
		//Cohort patients = TbUtil.getDOTSPatientsTJK(null, null, location, oblast, null, null, null, null,year,quarter,month);
		
		/* CHANGE DETECTION LOGIC CODE*/
		
		Date closedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(reportDate); 
		EncounterType reportEncounterType = Context.getEncounterService().getEncounterType("TB03u - MDR");

		// Patients enrolled during reporting time frame and location
		List<Patient> patientList = new ArrayList<Patient>();
		for (Integer patientId : patients.getPatientIds()) {
			patientList.add(Context.getPatientService().getPatient(patientId));
		}

//		List<Patient> modifiedPatients = new ArrayList<Patient>();
//		HashMap<Integer, Obs> modifiedObs = new HashMap<Integer, Obs>();
//		HashMap<Integer, Encounter> modifiedEncounters = new HashMap<Integer, Encounter>();
		
		if(reportEncounterType != null) {
			List<Patient> encounterPatients = (List<Patient>) Context.getService(MdrtbService.class).getEncounterByEncounterType(reportEncounterType);
			
			for (Patient patient : patientList) {
				if(encounterPatients.contains(patient)) {
					encounterPatients.indexOf(patient);
					Person person = Context.getPersonService().getPerson(patient.getPersonId());

					List<Obs> observations = Context.getObsService().getObservationsByPerson(person);

					//Patient Changes Detection
					if(patient.getDateChanged() != null) {
						if(patient.getDateChanged().after(closedDate)) {
							//Patient Changed After Report Closed Date
							modifiedPatients.add(patient);
						}
					}
					
					for(Obs obs : observations) {
						//Obs Changes Detection
						if(obs.getDateCreated() != null) {
							
							if(obs.getDateCreated().after(startDate) && obs.getDateCreated().before(endDate)) {

								//Obs Created Between Start Date and End Date;
								if(obs.getDateChanged() != null) {

									if(obs.getDateChanged().after(closedDate)) {
										//Obs Changed After Report Closed Date
										modifiedObs.put(person.getId(), obs);
									}
								}
							}
						}
						
						//Obs Encounter Changes Detection
						if(obs.getEncounter() != null) {
							Encounter encounter = obs.getEncounter();
							if(encounter.getDateCreated() != null) {

								if(encounter.getDateCreated().after(startDate) && encounter.getDateCreated().before(endDate)) {
									//Obs Encounter Created Between Start Date and End Date;
									if(encounter.getDateChanged() != null) {

										if(encounter.getDateChanged().after(closedDate)) {
											//Obs Encounter Changed After Report Closed Date
											modifiedEncounters.put(person.getId(), encounter);
										}
									}
								}
							}
						}
					}
				}
			}
//			System.out.println("modifiedPatients: "+ modifiedPatients);
//			System.out.println("modifiedObs: "+ modifiedObs);
//			System.out.println("modifiedEncounters: "+ modifiedEncounters);
//			System.out.println("modifiedEncounters: "+ modifiedEncounters.size());
//			System.out.println("\n\n\n");
		}
	}

	public void test2(String oblast, Location location, Integer year, String quarter, String month, String reportName, String reportDate
			, List<Patient> modifiedPatients, HashMap<Integer, Obs> modifiedObs, HashMap<Integer, Encounter> modifiedEncounters 
		) throws Exception {
		/* CHANGE DETECTION LOGIC CODE*/
		
		Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
		
		Date startDate = (Date)(dateMap.get("startDate"));
		Date endDate = (Date)(dateMap.get("endDate"));
		
		Date closedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(reportDate); 
		EncounterType reportEncounterType = Context.getEncounterService().getEncounterType("TB03u - MDR");

//		List<Patient> modifiedPatients = new ArrayList<Patient>();
//		HashMap<Integer, Obs> modifiedObs = new HashMap<Integer, Obs>();
//		HashMap<Integer, Encounter> modifiedEncounters = new HashMap<Integer, Encounter>();

		if(reportEncounterType != null) {
			List<Patient> patients = (List<Patient>) Context.getService(MdrtbService.class).getEncounterByEncounterType(reportEncounterType);
			List<Encounter> encounterList = new ArrayList<Encounter>();
			List<Obs> observationList = new ArrayList<Obs>();
			
			for (Patient patient : patients) {

				encounterList = Context.getEncounterService().getEncounters(patient, null, null, null, null, null, null, true);
				observationList = Context.getObsService().getObservationsByPerson(patient);
				
				
				// Patient Changes Detection
				if(patient.getDateChanged() != null) {
					if(patient.getDateChanged().after(closedDate)) {
						modifiedPatients.add(patient);
					}
				}
				
				for (Encounter encounter : encounterList) {
					if(encounter.getDateCreated() != null) {
						
						//Obs Encounter Created Between Start Date and End Date Logic
						if(encounter.getDateCreated().after(startDate) && encounter.getDateCreated().before(endDate)) {

							if(encounter.getDateChanged() != null) {
						
								//Obs Encounter Changed After Report Closed Date Logic
								if(encounter.getDateChanged().after(closedDate)) {
									modifiedEncounters.put(patient.getId(), encounter);
								}
							}
						}
					}	
				}
				
				for (Obs obs : observationList) {
					if(obs.getDateCreated() != null) {
						
						//Obs Created Between Start Date and End Date Logic
						if(obs.getDateCreated().after(startDate) && obs.getDateCreated().before(endDate)) {

							if(obs.getDateChanged() != null) {
						
								//Obs Changed After Report Closed Date Logic
								if(obs.getDateChanged().after(closedDate)) {
									modifiedObs.put(patient.getId(), obs);
								}
							}
						}
					}	
				}
			}
		}
//		System.out.println("modifiedPatients: "+ modifiedPatients);
//		System.out.println("modifiedObs: "+ modifiedObs);
//		System.out.println("modifiedEncounters: "+ modifiedEncounters);
//		System.out.println("modifiedEncounters: "+ modifiedEncounters.size());
//		System.out.println("\n\n\n");
	}
}
	