package org.openmrs.module.mdrtb.web.controller.reporting;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbConstants;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.form.Form89;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.program.TbPatientProgram;
import org.openmrs.module.mdrtb.reporting.PDFHelper;
import org.openmrs.module.mdrtb.reporting.ReportUtil;
import org.openmrs.module.mdrtb.reporting.TB07Table1Data;
import org.openmrs.module.mdrtb.reporting.TB07Util;
import org.openmrs.module.mdrtb.reporting.data.Cohorts;
import org.openmrs.module.mdrtb.service.MdrtbService;
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
import org.springframework.web.servlet.ModelAndView;


@Controller

public class TB07ReportController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
        
    
    @RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/tb07")
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
    	 return new ModelAndView("/module/mdrtb/reporting/tb07", model);	
    	
    }
    

    @RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/tb07")
    public static String doTB07(
    		@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException {
    	
    	System.out.println("---POST-----");
    	//System.out.println("PARAMS:" + location + " " + oblast + " " + year + " " + quarter + " " + month);
    	
    	
    	/*Oblast o = null;
    	if(oblast!=null && !oblast.equals("") && location == null)
			o =  Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblast));
		
		List<Location> locList = new ArrayList<Location>();*/
		/*if(o != null && location == null)
			locList = Context.getService(MdrtbService.class).getLocationsFromOblastName(o);
		else if (location != null)
			locList.add(location);*/
		
		//int iterations = 1;
		
		//TB07Table1Data fin = new TB07Table1Data();
		
	/*	if((month==null || month=="") && (quarter==null || quarter==""))
			iterations = 4;
	*/	
	/*	else
			iterations = 1;
	*/	
		SimpleDateFormat sdf = new SimpleDateFormat();
    	sdf.applyPattern("dd.MM.yyyy");
    	SimpleDateFormat rdateSDF = new SimpleDateFormat();
    	rdateSDF.applyPattern("dd.MM.yyyy HH:mm:ss");
		
		//for(int j=0; j<iterations; j++) {	
			
			/*Map<String, Date> dateMap;
			
			if((month==null || month=="") && (quarter==null || quarter==""))	 {
				
				dateMap = ReportUtil.getPeriodDates(year, (j + 1) + "", month);
				
			}
			
			else 
				dateMap = ReportUtil.getPeriodDates(year, quarter, month);*/
		
		
		
		
//		Date startDate = (Date)(dateMap.get("startDate"));
//		Date endDate = (Date)(dateMap.get("endDate"));
//		
//		System.out.println("ST: " + startDate);
//		System.out.println("ED: " + endDate);
    	ArrayList<Location> locList = null;
		if(oblastId.intValue()==186) {
			locList = Context.getService(MdrtbService.class).getLocationListForDushanbe(oblastId,districtId,facilityId);
		}
		else {
			locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
		}
		//System.out.println(locList.size());
		ArrayList<TB03Form> tb03List = Context.getService(MdrtbService.class).getTB03FormsFilled(locList, year, quarter, month);
		System.out.println("list size:" + tb03List.size());
		//CohortDefinition baseCohort = null;
		
	/*	//OBLAST
		if (!locList.isEmpty()){
			List<CohortDefinition> cohortDefinitions = new ArrayList<CohortDefinition>();
			for(Location loc : locList)
				cohortDefinitions.add(Cohorts.getTB03ByDatesAndLocation(startDate, endDate, loc));
				
			if(!cohortDefinitions.isEmpty()){
				baseCohort = ReportUtil.getCompositionCohort("OR", cohortDefinitions);
			}
		}
		
		else
			baseCohort = Cohorts.getTB03ByDatesAndLocation(startDate, endDate, null);*/
    	
		
		/////////NEW MODEL/////////////////////////////
		/*EncounterType encType = Context.getEncounterService().getEncounterType(Context.getAdministrationService().getGlobalProperty("mdrtb.intake_encounter_type"));
    	ArrayList<EncounterType> typeList = new ArrayList<EncounterType>();
		typeList.add(encType);
		
		ArrayList<Encounter> tb03List = new ArrayList<Encounter>();
		List<Encounter> tempList = null;
		// OBLAST
		if (!locList.isEmpty()) {
			List<CohortDefinition> cohortDefinitions = new ArrayList<CohortDefinition>();
			for (Location loc : locList) {
				tempList = Context.getEncounterService().getEncounters(null,loc, startDate, endDate, null, typeList, false);
				for(Encounter e : tempList) {
					tb03List.add(e);
				}
			}
							
		}

		else
			tb03List = null;*/
		
		
    	//Cohort patients = Context.getService(CohortDefinitionService.class).evaluate(baseCohort, new EvaluationContext());
    	//Cohort patients = TbUtil.getDOTSPatientsTJK(null, null, location, oblast, null, null, null, null,year,quarter,month);
    	
		
		/*Form tb03Form = Context.getFormService().getForm(MdrtbConstants.TB03_FORM_ID);
		ArrayList<Form> formList = new ArrayList<Form>();
		formList.add(tb03Form);
    	*/
    	
    	/**/
    	
    	//ArrayList<TB03Data> patientSet  = new ArrayList<TB03Data>();
    	
    	
//    	ArrayList<Person> patientList = new ArrayList<Person>();
//    	ArrayList<Concept> conceptQuestionList = new ArrayList<Concept>();
    	/*ArrayList<Concept> conceptAnswerList = new ArrayList<Concept>();
    	Integer regimenConceptId = null;*/
    	//Integer codId = null;
    	//List<Obs> obsList = null;
    	Integer ageAtRegistration = 0;
    	
    	Concept pulmonaryConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.PULMONARY_TB);
    	Concept extrapulmonaryConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.EXTRA_PULMONARY_TB);
    	Concept positiveConcept =  Context.getService(MdrtbService.class).getConcept(TbConcepts.POSITIVE);
    	Concept negativeConcept =  Context.getService(MdrtbService.class).getConcept(TbConcepts.NEGATIVE);
    	/*Concept hivDateConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_HIV_TEST);
    	Concept artStartConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_ART_TREATMENT_START);
    	Concept pctStartConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.DATE_OF_PCT_TREATMENT_START);*/
    	Concept contact = Context.getService(MdrtbService.class).getConcept(TbConcepts.CONTACT);
    	Concept migrant = Context.getService(MdrtbService.class).getConcept(TbConcepts.MIGRANT);
    	Concept phcWorker = Context.getService(MdrtbService.class).getConcept(TbConcepts.PHC_WORKER);
    	Concept tbServices = Context.getService(MdrtbService.class).getConcept(TbConcepts.TB_SERVICES_WORKER);
    	
    	
    	Boolean pulmonary = null;
    	Boolean bacPositive = null;
    	Boolean hivPositive = null;
    	
    	
    	TB07Table1Data table1 = new TB07Table1Data();
    	
    	for (TB03Form tf : tb03List) {
    		
    		ageAtRegistration = -1;
    		pulmonary = null;
    		bacPositive = null;
    		hivPositive = null;
    		System.out.println("PID:" + tf.getPatient().getPatientId());
    		Integer programId = tf.getPatProgId();
    		
    		TbPatientProgram tpp = null;
    		Form89 f89 = null;
    		
    		if(programId!=null) {
    			System.out.println("ProgID:" + programId);
    			tpp = Context.getService(MdrtbService.class).getTbPatientProgram(programId);
    			
    			if(tpp!=null) {
    				
    				f89 = tpp.getForm89();
    				//f89.
    			}
    		}
    		
    		
    		Patient patient = tf.getPatient();
    	    if(patient==null || patient.isVoided()) {
    	    	continue;
    	    	
    	    }
    	      
    	   
    	    
    	  //  conceptQuestionList.add(q);
    	    ageAtRegistration = tf.getAgeAtTB03Registration();
    	    
    	    
    	    
    	    
    	    //get disease site
    	    Concept q = tf.getAnatomicalSite();
    	    
    	    
    	    if(q!=null) {
    	    	if(q.getConceptId().intValue()==pulmonaryConcept.getConceptId().intValue()) {
    	    		pulmonary = Boolean.TRUE;
    	    		//System.out.println("PULMONARY");
    	    	}
    	    	
    	    	else if (q.getConceptId().intValue()==extrapulmonaryConcept.getConceptId().intValue()) {
    	    		pulmonary = Boolean.FALSE;
    	    		//System.out.println("EXTRAPULMONARY");
    	    	}
    	    	
    	    	else {
    	    		
    	    		//System.out.println("PULMONARY NULL");
    	    		pulmonary = null;
    	    	}
    	    	
    	    }
    	    
    	    else {
    	    	//System.out.println("NO SITE");
    	    	continue;
    	    }
    	    
    	    
    	    
    	    bacPositive = MdrtbUtil.isDiagnosticBacPositive(tf);
    	    
    	    
    	    q = tf.getHivStatus();//Context.getService(MdrtbService.class).getConcept(TbConcepts.RESULT_OF_HIV_TEST);
    	    
    	    if(q!=null) {
    	    	if(q.getConceptId().intValue()==positiveConcept.getConceptId().intValue()) {
    	    		hivPositive = Boolean.TRUE;
    	    		table1.setHivPositive(table1.getHivPositive() + 1);
    	    		table1.setHivTested(table1.getHivTested() + 1);
    	    		//System.out.println("HIV +");
    	    	}
    	    	
    	    	else if(q.getConceptId().intValue()==negativeConcept.getConceptId().intValue()) {
    	    		hivPositive = Boolean.FALSE;
    	    		table1.setHivTested(table1.getHivTested() + 1);
    	    		//System.out.println("HIV -");
    	    	}
    	    	
    	    	else {
    	    		hivPositive = Boolean.FALSE;
    	    		//System.out.println("NOT TESTED");
    	    	}
    	    }
    	    
    	    else {
    	    	hivPositive = Boolean.FALSE;
	    		//System.out.println("HIV NOT ENTERED");
    	    }
    	    
    	   
    	    
    	   
    	    Date artStartDate = tf.getArtStartDate();
    	    
    	   
    	    if(artStartDate != null) {
    	    		table1.setArtStarted(table1.getArtStarted() + 1);
    	    	}
    	    
    	    
    	    Date pctStartDate = tf.getPctStartDate();
    	    
    	   
    	    if(pctStartDate != null) {
    	    		table1.setPctStarted(table1.getPctStarted() + 1);
    	    	}
    	    

    	  
    	    
    	    
    	    //get registration group
    	    //REGISTRATION GROUP
    	    q = tf.getRegistrationGroup();
    	    
    	    
    	   /* obsList = Context.getObsService().getObservations(patientList, tb03List, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    if(obsList ==null || obsList.size()==0) {
    	    	
    	    	q = Context.getService(MdrtbService.class).getConcept(TbConcepts.CAT_4_CLASSIFICATION_PREVIOUS_TX);
        	    conceptQuestionList.clear();
        	    conceptQuestionList.add(q);
        	    obsList = Context.getObsService().getObservations(patientList, null, conceptQuestionList, null, null, null, null, null, null, startDate, endDate, false);
    	    }*/
    	    
    	    if(q!=null) {
    	    	
    	    	if(tf.getPatient().isDead()) {
    	    		table1.setDied(table1.getDied() + 1);
    	    		if(ageAtRegistration < 15) {
    	    			table1.setDiedChildren(table1.getDiedChildren() + 1);
    	    		}
    	    		
    	    		else if (ageAtRegistration >= 15 && ageAtRegistration <=49 && tf.getPatient().getGender().equals("F")) {
    	    			table1.setDiedWomenOfChildBearingAge(table1.getDiedWomenOfChildBearingAge() + 1);
    	    		}
    	    	}
    	    	
    	    	
    	    	
    	    	//NEW
    	    	if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.new.conceptId"))) {
    	    		
    	    		if(f89!=null) {
        				Concept x = f89.getCircumstancesOfDetection();
        				
        				if(x!=null) {
        					if(x.getId().intValue()==contact.getId().intValue()) {
        						table1.setContacts(table1.getContacts()+1);
        					}
        					
        					else if(x.getId().intValue()==migrant.getId().intValue()) {
        						table1.setMigrants(table1.getMigrants()+1);
        					}
        				}
        				
        				x = f89.getProfession();
        				
        				if(x!=null) {
        					if(x.getId().intValue()==phcWorker.getId().intValue()) {
        						table1.setPhcWorkers(table1.getPhcWorkers()+1);
        					}
        					
        					else if(x.getId().intValue()==tbServices.getId().intValue()) {
        						table1.setTbServicesWorkers(table1.getTbServicesWorkers()+1);
        					}
        				}
        				
        			}
    	    		
    	    		table1.setNewAll(table1.getNewAll() + 1);
	    			if(hivPositive)
	    				table1.setNewAllHIV(table1.getNewAllHIV() + 1);
    	    		
	    			//System.out.println("NEW MALE");
    	    		
    	    		if(patient.getGender().equals("M")) {
    	    			
    	    			table1.setNewMale(table1.getNewMale() + 1);
    	    			if(hivPositive)
    	    				table1.setNewMaleHIV(table1.getNewMaleHIV() + 1);
    	    			
    	    			if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
	    					table1.setNewMale04(table1.getNewMale04()+1);
	    					if(hivPositive)
	    						table1.setNewMaleHIV04(table1.getNewMaleHIV04()+1);
	    				}
	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
	    					table1.setNewMale0514(table1.getNewMale0514()+1);
	    					if(hivPositive)
	    						table1.setNewMaleHIV0514(table1.getNewMaleHIV0514()+1);
	    				}
	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
	    					table1.setNewMale1517(table1.getNewMale1517()+1);
	    					if(hivPositive)
	    						table1.setNewMaleHIV1517(table1.getNewMaleHIV1517()+1);
	    				}
	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
	    					table1.setNewMale1819(table1.getNewMale1819()+1);
	    					if(hivPositive)
	    						table1.setNewMaleHIV1819(table1.getNewMaleHIV1819()+1);
	    				}
	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
	    					table1.setNewMale2024(table1.getNewMale2024()+1);
	    					if(hivPositive)
	    						table1.setNewMaleHIV2024(table1.getNewMaleHIV2024()+1);
	    				}
	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
	    					table1.setNewMale2534(table1.getNewMale2534()+1);
	    					if(hivPositive)
	    						table1.setNewMaleHIV2534(table1.getNewMaleHIV2534()+1);
	    				}
	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
	    					table1.setNewMale3544(table1.getNewMale3544()+1);
	    					if(hivPositive)
	    						table1.setNewMaleHIV3544(table1.getNewMaleHIV3544()+1);
	    				}
	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
	    					table1.setNewMale4554(table1.getNewMale4554()+1);
	    					if(hivPositive)
	    						table1.setNewMaleHIV4554(table1.getNewMaleHIV4554()+1);
	    				}
	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
	    					table1.setNewMale5564(table1.getNewMale5564()+1);
	    					if(hivPositive)
	    						table1.setNewMaleHIV5564(table1.getNewMaleHIV5564()+1);
	    				}
	    				else if(ageAtRegistration >= 65) {
	    					table1.setNewMale65(table1.getNewMale65()+1);
	    					if(hivPositive)
	    						table1.setNewMaleHIV65(table1.getNewMaleHIV65()+1);
	    				}
    	    			
    	    			///
    	    			
    	    			
    	    			
    	    			
    	    			///
    	    			
    	    			
    	    			//P
    	    			if(pulmonary != null &&  pulmonary) {
    	    				
    	    				//BC
    	    				if(bacPositive) {
    	    					
    	    					table1.setNewMalePulmonaryBC(table1.getNewMalePulmonaryBC() + 1);
            	    			
        	    				if(hivPositive)
        	    					table1.setNewMalePulmonaryBCHIV(table1.getNewMalePulmonaryBCHIV() + 1);
    	    					
    	    					if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
        	    					table1.setNewMalePulmonaryBC04(table1.getNewMalePulmonaryBC04()+1);
        	    					if(hivPositive)
        	    						table1.setNewMalePulmonaryBCHIV04(table1.getNewMalePulmonaryBCHIV04()+1);
        	    				}
        	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
        	    					table1.setNewMalePulmonaryBC0514(table1.getNewMalePulmonaryBC0514()+1);
        	    					if(hivPositive)
        	    						table1.setNewMalePulmonaryBCHIV0514(table1.getNewMalePulmonaryBCHIV0514()+1);
        	    				}
        	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
        	    					table1.setNewMalePulmonaryBC1517(table1.getNewMalePulmonaryBC1517()+1);
        	    					if(hivPositive)
        	    						table1.setNewMalePulmonaryBCHIV1517(table1.getNewMalePulmonaryBCHIV1517()+1);
        	    				}
        	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
        	    					table1.setNewMalePulmonaryBC1819(table1.getNewMalePulmonaryBC1819()+1);
        	    					if(hivPositive)
        	    						table1.setNewMalePulmonaryBCHIV1819(table1.getNewMalePulmonaryBCHIV1819()+1);
        	    				}
        	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
        	    					table1.setNewMalePulmonaryBC2024(table1.getNewMalePulmonaryBC2024()+1);
        	    					if(hivPositive)
        	    						table1.setNewMalePulmonaryBCHIV2024(table1.getNewMalePulmonaryBCHIV2024()+1);
        	    				}
        	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
        	    					table1.setNewMalePulmonaryBC2534(table1.getNewMalePulmonaryBC2534()+1);
        	    					if(hivPositive)
        	    						table1.setNewMalePulmonaryBCHIV2534(table1.getNewMalePulmonaryBCHIV2534()+1);
        	    				}
        	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
        	    					table1.setNewMalePulmonaryBC3544(table1.getNewMalePulmonaryBC3544()+1);
        	    					if(hivPositive)
        	    						table1.setNewMalePulmonaryBCHIV3544(table1.getNewMalePulmonaryBCHIV3544()+1);
        	    				}
        	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
        	    					table1.setNewMalePulmonaryBC4554(table1.getNewMalePulmonaryBC4554()+1);
        	    					if(hivPositive)
        	    						table1.setNewMalePulmonaryBCHIV4554(table1.getNewMalePulmonaryBCHIV4554()+1);
        	    				}
        	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
        	    					table1.setNewMalePulmonaryBC5564(table1.getNewMalePulmonaryBC5564()+1);
        	    					if(hivPositive)
        	    						table1.setNewMalePulmonaryBCHIV5564(table1.getNewMalePulmonaryBCHIV5564()+1);
        	    				}
        	    				else if(ageAtRegistration >= 65) {
        	    					table1.setNewMalePulmonaryBC65(table1.getNewMalePulmonaryBC65()+1);
        	    					if(hivPositive)
        	    						table1.setNewMalePulmonaryBCHIV65(table1.getNewMalePulmonaryBCHIV65()+1);
        	    				}
    	    				}
    	    				
    	    				//CD
    	    				else {
    	    					
    	    					table1.setNewMalePulmonaryCD(table1.getNewMalePulmonaryCD() + 1);
            	    			
        	    				if(hivPositive)
        	    					table1.setNewMalePulmonaryCDHIV(table1.getNewMalePulmonaryCDHIV() + 1);
    	    					
    	    					if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
        	    					table1.setNewMalePulmonaryCD04(table1.getNewMalePulmonaryCD04()+1);
        	    					if(hivPositive) {
        	    						table1.setNewMalePulmonaryCDHIV04(table1.getNewMalePulmonaryCDHIV04()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
        	    					table1.setNewMalePulmonaryCD0514(table1.getNewMalePulmonaryCD0514()+1);
        	    					if(hivPositive) {
        	    						table1.setNewMalePulmonaryCDHIV0514(table1.getNewMalePulmonaryCDHIV0514()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
        	    					table1.setNewMalePulmonaryCD1517(table1.getNewMalePulmonaryCD1517()+1);
        	    					if(hivPositive) {
        	    						table1.setNewMalePulmonaryCDHIV1517(table1.getNewMalePulmonaryCDHIV1517()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
        	    					table1.setNewMalePulmonaryCD1819(table1.getNewMalePulmonaryCD1819()+1);
        	    					if(hivPositive) {
        	    						table1.setNewMalePulmonaryCDHIV1819(table1.getNewMalePulmonaryCDHIV1819()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
        	    					table1.setNewMalePulmonaryCD2024(table1.getNewMalePulmonaryCD2024()+1);
        	    					if(hivPositive) {
        	    						table1.setNewMalePulmonaryCDHIV2024(table1.getNewMalePulmonaryCDHIV2024()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
        	    					table1.setNewMalePulmonaryCD2534(table1.getNewMalePulmonaryCD2534()+1);
        	    					if(hivPositive) {
        	    						table1.setNewMalePulmonaryCDHIV2534(table1.getNewMalePulmonaryCDHIV2534()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
        	    					table1.setNewMalePulmonaryCD3544(table1.getNewMalePulmonaryCD3544()+1);
        	    					if(hivPositive) {
        	    						table1.setNewMalePulmonaryCDHIV3544(table1.getNewMalePulmonaryCDHIV3544()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
        	    					table1.setNewMalePulmonaryCD4554(table1.getNewMalePulmonaryCD4554()+1);
        	    					if(hivPositive) {
        	    						table1.setNewMalePulmonaryCDHIV4554(table1.getNewMalePulmonaryCDHIV4554()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
        	    					table1.setNewMalePulmonaryCD5564(table1.getNewMalePulmonaryCD5564()+1);
        	    					if(hivPositive) {
        	    						table1.setNewMalePulmonaryCDHIV5564(table1.getNewMalePulmonaryCDHIV5564()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 65) {
        	    					table1.setNewMalePulmonaryCD65(table1.getNewMalePulmonaryCD65()+1);
        	    					if(hivPositive) {
        	    						table1.setNewMalePulmonaryCDHIV65(table1.getNewMalePulmonaryCDHIV65()+1);
        	    					}
        	    				}
    	    				}
    	    			}
    	    			
    	    			
    	    			//EP
    	    			else if(pulmonary!=null && pulmonary==Boolean.FALSE) {
    	    				
    	    				table1.setNewMaleExtrapulmonary(table1.getNewMaleExtrapulmonary() + 1);
    	    				
    	    				if(hivPositive)
        	    				table1.setNewMaleExtrapulmonaryHIV(table1.getNewMaleExtrapulmonaryHIV() + 1);
    	    				
    	    				
    	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
    	    					table1.setNewMaleExtrapulmonary04(table1.getNewMaleExtrapulmonary04()+1);
    	    					if(hivPositive) {
    	    						table1.setNewMaleExtrapulmonaryHIV04(table1.getNewMaleExtrapulmonaryHIV04()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
    	    					table1.setNewMaleExtrapulmonary0514(table1.getNewMaleExtrapulmonary0514()+1);
    	    					if(hivPositive) {
    	    						table1.setNewMaleExtrapulmonaryHIV0514(table1.getNewMaleExtrapulmonaryHIV0514()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
    	    					table1.setNewMaleExtrapulmonary1517(table1.getNewMaleExtrapulmonary1517()+1);
    	    					if(hivPositive) {
    	    						table1.setNewMaleExtrapulmonaryHIV1517(table1.getNewMaleExtrapulmonaryHIV1517()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
    	    					table1.setNewMaleExtrapulmonary1819(table1.getNewMaleExtrapulmonary1819()+1);
    	    					if(hivPositive) {
    	    						table1.setNewMaleExtrapulmonaryHIV1819(table1.getNewMaleExtrapulmonaryHIV1819()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
    	    					table1.setNewMaleExtrapulmonary2024(table1.getNewMaleExtrapulmonary2024()+1);
    	    					if(hivPositive) {
    	    						table1.setNewMaleExtrapulmonaryHIV2024(table1.getNewMaleExtrapulmonaryHIV2024()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
    	    					table1.setNewMaleExtrapulmonary2534(table1.getNewMaleExtrapulmonary2534()+1);
    	    					if(hivPositive) {
    	    						table1.setNewMaleExtrapulmonaryHIV2534(table1.getNewMaleExtrapulmonaryHIV2534()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
    	    					table1.setNewMaleExtrapulmonary3544(table1.getNewMaleExtrapulmonary3544()+1);
    	    					if(hivPositive) {
    	    						table1.setNewMaleExtrapulmonaryHIV3544(table1.getNewMaleExtrapulmonaryHIV3544()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
    	    					table1.setNewMaleExtrapulmonary4554(table1.getNewMaleExtrapulmonary4554()+1);
    	    					if(hivPositive) {
    	    						table1.setNewMaleExtrapulmonaryHIV4554(table1.getNewMaleExtrapulmonaryHIV4554()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
    	    					table1.setNewMaleExtrapulmonary5564(table1.getNewMaleExtrapulmonary5564()+1);
    	    					if(hivPositive) {
    	    						table1.setNewMaleExtrapulmonaryHIV5564(table1.getNewMaleExtrapulmonaryHIV5564()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 65) {
    	    					table1.setNewMaleExtrapulmonary65(table1.getNewMaleExtrapulmonary65()+1);
    	    					if(hivPositive) {
    	    						table1.setNewMaleExtrapulmonaryHIV65(table1.getNewMaleExtrapulmonaryHIV65()+1);
    	    					}
    	    				}
    	    			}
    	    		}
    	    		
    	    		else if(patient.getGender().equals("F")) {
    	    			table1.setNewFemale(table1.getNewFemale() + 1);
    	    			
    	    			if(hivPositive)
    	    				table1.setNewFemaleHIV(table1.getNewFemaleHIV() + 1);
    	    			
    	    			if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
	    					table1.setNewFemale04(table1.getNewFemale04()+1);
	    					if(hivPositive)
	    						table1.setNewFemaleHIV04(table1.getNewFemaleHIV04()+1);
	    				}
	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
	    					table1.setNewFemale0514(table1.getNewFemale0514()+1);
	    					if(hivPositive)
	    						table1.setNewFemaleHIV0514(table1.getNewFemaleHIV0514()+1);
	    				}
	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
	    					table1.setNewFemale1517(table1.getNewFemale1517()+1);
	    					if(hivPositive)
	    						table1.setNewFemaleHIV1517(table1.getNewFemaleHIV1517()+1);
	    				}
	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
	    					table1.setNewFemale1819(table1.getNewFemale1819()+1);
	    					if(hivPositive)
	    						table1.setNewFemaleHIV1819(table1.getNewFemaleHIV1819()+1);
	    				}
	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
	    					table1.setNewFemale2024(table1.getNewFemale2024()+1);
	    					if(hivPositive)
	    						table1.setNewFemaleHIV2024(table1.getNewFemaleHIV2024()+1);
	    				}
	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
	    					table1.setNewFemale2534(table1.getNewFemale2534()+1);
	    					if(hivPositive)
	    						table1.setNewFemaleHIV2534(table1.getNewFemaleHIV2534()+1);
	    				}
	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
	    					table1.setNewFemale3544(table1.getNewFemale3544()+1);
	    					if(hivPositive)
	    						table1.setNewFemaleHIV3544(table1.getNewFemaleHIV3544()+1);
	    				}
	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
	    					table1.setNewFemale4554(table1.getNewFemale4554()+1);
	    					if(hivPositive)
	    						table1.setNewFemaleHIV4554(table1.getNewFemaleHIV4554()+1);
	    				}
	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
	    					table1.setNewFemale5564(table1.getNewFemale5564()+1);
	    					if(hivPositive)
	    						table1.setNewFemaleHIV5564(table1.getNewFemaleHIV5564()+1);
	    				}
	    				else if(ageAtRegistration >= 65) {
	    					table1.setNewFemale65(table1.getNewFemale65()+1);
	    					if(hivPositive)
	    						table1.setNewFemaleHIV65(table1.getNewFemaleHIV65()+1);
	    				}
    	    			
    	    			if(ageAtRegistration >=15 && ageAtRegistration <= 49) {
    	    				table1.setWomenOfChildBearingAge(table1.getWomenOfChildBearingAge()+1);
    	    				
    	    				if(f89!=null) {
    	    					Concept c = f89.getPregnant();
    	    					if(c!=null)
    	    						System.out.println("NAME:"+c.getName().getName());
    	    					else
    	    						System.out.println("NAME: NULL");
    	    					if(c!=null && c.getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.YES).getId().intValue()) {
    	    						table1.setPregnant(table1.getPregnant() + 1);
    	    					}
    	    				}
    	    			}
    	    			
    	    			//P
    	    			if(pulmonary != null &&  pulmonary) {

    	    				//BC
    	    				if(bacPositive) {
    	    					
    	    					
    	    					table1.setNewFemalePulmonaryBC(table1.getNewFemalePulmonaryBC() + 1);
    	    	    			
    	    	    			if(hivPositive)
    	    	    				table1.setNewFemalePulmonaryBCHIV(table1.getNewFemalePulmonaryBCHIV() + 1);
    	    					
    	    					if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
        	    					table1.setNewFemalePulmonaryBC04(table1.getNewFemalePulmonaryBC04()+1);
        	    					if(hivPositive)
        	    						table1.setNewFemalePulmonaryBCHIV04(table1.getNewFemalePulmonaryBCHIV04()+1);
        	    				}
        	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
        	    					table1.setNewFemalePulmonaryBC0514(table1.getNewFemalePulmonaryBC0514()+1);
        	    					if(hivPositive)
        	    						table1.setNewFemalePulmonaryBCHIV0514(table1.getNewFemalePulmonaryBCHIV0514()+1);
        	    				}
        	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
        	    					table1.setNewFemalePulmonaryBC1517(table1.getNewFemalePulmonaryBC1517()+1);
        	    					if(hivPositive)
        	    						table1.setNewFemalePulmonaryBCHIV1517(table1.getNewFemalePulmonaryBCHIV1517()+1);
        	    				}
        	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
        	    					table1.setNewFemalePulmonaryBC1819(table1.getNewFemalePulmonaryBC1819()+1);
        	    					if(hivPositive)
        	    						table1.setNewFemalePulmonaryBCHIV1819(table1.getNewFemalePulmonaryBCHIV1819()+1);
        	    				}
        	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
        	    					table1.setNewFemalePulmonaryBC2024(table1.getNewFemalePulmonaryBC2024()+1);
        	    					if(hivPositive)
        	    						table1.setNewFemalePulmonaryBCHIV2024(table1.getNewFemalePulmonaryBCHIV2024()+1);
        	    				}
        	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
        	    					table1.setNewFemalePulmonaryBC2534(table1.getNewFemalePulmonaryBC2534()+1);
        	    					if(hivPositive)
        	    						table1.setNewFemalePulmonaryBCHIV2534(table1.getNewFemalePulmonaryBCHIV2534()+1);
        	    				}
        	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
        	    					table1.setNewFemalePulmonaryBC3544(table1.getNewFemalePulmonaryBC3544()+1);
        	    					if(hivPositive)
        	    						table1.setNewFemalePulmonaryBCHIV3544(table1.getNewFemalePulmonaryBCHIV3544()+1);
        	    				}
        	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
        	    					table1.setNewFemalePulmonaryBC4554(table1.getNewFemalePulmonaryBC4554()+1);
        	    					if(hivPositive)
        	    						table1.setNewFemalePulmonaryBCHIV4554(table1.getNewFemalePulmonaryBCHIV4554()+1);
        	    				}
        	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
        	    					table1.setNewFemalePulmonaryBC5564(table1.getNewFemalePulmonaryBC5564()+1);
        	    					if(hivPositive)
        	    						table1.setNewFemalePulmonaryBCHIV5564(table1.getNewFemalePulmonaryBCHIV5564()+1);
        	    				}
        	    				else if(ageAtRegistration >= 65) {
        	    					table1.setNewFemalePulmonaryBC65(table1.getNewFemalePulmonaryBC65()+1);
        	    					if(hivPositive)
        	    						table1.setNewFemalePulmonaryBCHIV65(table1.getNewFemalePulmonaryBCHIV65()+1);
        	    				}
    	    				}
    	    				
    	    				//CD
    	    				else {
    	    					
    	    					table1.setNewFemalePulmonaryCD(table1.getNewFemalePulmonaryCD() + 1);
    	    	    			
    	    	    			if(hivPositive)
    	    	    				table1.setNewFemalePulmonaryCDHIV(table1.getNewFemalePulmonaryCDHIV() + 1);
    	    					
    	    					if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
        	    					table1.setNewFemalePulmonaryCD04(table1.getNewFemalePulmonaryCD04()+1);
        	    					if(hivPositive) {
        	    						table1.setNewFemalePulmonaryCDHIV04(table1.getNewFemalePulmonaryCDHIV04()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
        	    					table1.setNewFemalePulmonaryCD0514(table1.getNewFemalePulmonaryCD0514()+1);
        	    					if(hivPositive) {
        	    						table1.setNewFemalePulmonaryCDHIV0514(table1.getNewFemalePulmonaryCDHIV0514()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
        	    					table1.setNewFemalePulmonaryCD1517(table1.getNewFemalePulmonaryCD1517()+1);
        	    					if(hivPositive) {
        	    						table1.setNewFemalePulmonaryCDHIV1517(table1.getNewFemalePulmonaryCDHIV1517()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
        	    					table1.setNewFemalePulmonaryCD1819(table1.getNewFemalePulmonaryCD1819()+1);
        	    					if(hivPositive) {
        	    						table1.setNewFemalePulmonaryCDHIV1819(table1.getNewFemalePulmonaryCDHIV1819()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
        	    					table1.setNewFemalePulmonaryCD2024(table1.getNewFemalePulmonaryCD2024()+1);
        	    					if(hivPositive) {
        	    						table1.setNewFemalePulmonaryCDHIV2024(table1.getNewFemalePulmonaryCDHIV2024()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
        	    					table1.setNewFemalePulmonaryCD2534(table1.getNewFemalePulmonaryCD2534()+1);
        	    					if(hivPositive) {
        	    						table1.setNewFemalePulmonaryCDHIV2534(table1.getNewFemalePulmonaryCDHIV2534()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
        	    					table1.setNewFemalePulmonaryCD3544(table1.getNewFemalePulmonaryCD3544()+1);
        	    					if(hivPositive) {
        	    						table1.setNewFemalePulmonaryCDHIV3544(table1.getNewFemalePulmonaryCDHIV3544()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
        	    					table1.setNewFemalePulmonaryCD4554(table1.getNewFemalePulmonaryCD4554()+1);
        	    					if(hivPositive) {
        	    						table1.setNewFemalePulmonaryCDHIV4554(table1.getNewFemalePulmonaryCDHIV4554()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
        	    					table1.setNewFemalePulmonaryCD5564(table1.getNewFemalePulmonaryCD5564()+1);
        	    					if(hivPositive) {
        	    						table1.setNewFemalePulmonaryCDHIV5564(table1.getNewFemalePulmonaryCDHIV5564()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 65) {
        	    					table1.setNewFemalePulmonaryCD65(table1.getNewFemalePulmonaryCD65()+1);
        	    					if(hivPositive) {
        	    						table1.setNewFemalePulmonaryCDHIV65(table1.getNewFemalePulmonaryCDHIV65()+1);
        	    					}
        	    				}
    	    				}
    	    			}
    	    			
    	    			
    	    			//EP
    	    			else if(pulmonary!=null && !pulmonary) {
    	    				
    	    				table1.setNewFemaleExtrapulmonary(table1.getNewFemaleExtrapulmonary() + 1);
	    	    			
	    	    			if(hivPositive)
	    	    				table1.setNewFemaleExtrapulmonaryHIV(table1.getNewFemaleExtrapulmonaryHIV() + 1);
    	    				
    	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
    	    					table1.setNewFemaleExtrapulmonary04(table1.getNewFemaleExtrapulmonary04()+1);
    	    					if(hivPositive) {
    	    						table1.setNewFemaleExtrapulmonaryHIV04(table1.getNewFemaleExtrapulmonaryHIV04()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
    	    					table1.setNewFemaleExtrapulmonary0514(table1.getNewFemaleExtrapulmonary0514()+1);
    	    					if(hivPositive) {
    	    						table1.setNewFemaleExtrapulmonaryHIV0514(table1.getNewFemaleExtrapulmonaryHIV0514()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
    	    					table1.setNewFemaleExtrapulmonary1517(table1.getNewFemaleExtrapulmonary1517()+1);
    	    					if(hivPositive) {
    	    						table1.setNewFemaleExtrapulmonaryHIV1517(table1.getNewFemaleExtrapulmonaryHIV1517()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
    	    					table1.setNewFemaleExtrapulmonary1819(table1.getNewFemaleExtrapulmonary1819()+1);
    	    					if(hivPositive) {
    	    						table1.setNewFemaleExtrapulmonaryHIV1819(table1.getNewFemaleExtrapulmonaryHIV1819()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
    	    					table1.setNewFemaleExtrapulmonary2024(table1.getNewFemaleExtrapulmonary2024()+1);
    	    					if(hivPositive) {
    	    						table1.setNewFemaleExtrapulmonaryHIV2024(table1.getNewFemaleExtrapulmonaryHIV2024()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
    	    					table1.setNewFemaleExtrapulmonary2534(table1.getNewFemaleExtrapulmonary2534()+1);
    	    					if(hivPositive) {
    	    						table1.setNewFemaleExtrapulmonaryHIV2534(table1.getNewFemaleExtrapulmonaryHIV2534()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
    	    					table1.setNewFemaleExtrapulmonary3544(table1.getNewFemaleExtrapulmonary3544()+1);
    	    					if(hivPositive) {
    	    						table1.setNewFemaleExtrapulmonaryHIV3544(table1.getNewFemaleExtrapulmonaryHIV3544()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
    	    					table1.setNewFemaleExtrapulmonary4554(table1.getNewFemaleExtrapulmonary4554()+1);
    	    					if(hivPositive) {
    	    						table1.setNewFemaleExtrapulmonaryHIV4554(table1.getNewFemaleExtrapulmonaryHIV4554()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
    	    					table1.setNewFemaleExtrapulmonary5564(table1.getNewFemaleExtrapulmonary5564()+1);
    	    					if(hivPositive) {
    	    						table1.setNewFemaleExtrapulmonaryHIV5564(table1.getNewFemaleExtrapulmonaryHIV5564()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 65) {
    	    					table1.setNewFemaleExtrapulmonary65(table1.getNewFemaleExtrapulmonary65()+1);
    	    					if(hivPositive) {
    	    						table1.setNewFemaleExtrapulmonaryHIV65(table1.getNewFemaleExtrapulmonaryHIV65()+1);
    	    					}
    	    				}
    	    			}
    	    			
    	    		}
    	    		
    	    		
    	    	}
    	    		
    	    
    	    	//RELAPSE
    	    	else if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterRelapse1.conceptId")) || q.getConceptId()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterRelapse2.conceptId"))) {
    	    		
    	    		table1.setRelapseAll(table1.getRelapseAll() + 1);
	    			if(hivPositive)
	    				table1.setRelapseAllHIV(table1.getRelapseAllHIV() + 1);
    	    		
    	    		if(patient.getGender().equals("M")) {
    	    			
    	    			table1.setRelapseMale(table1.getRelapseMale() + 1);
    	    			if(hivPositive)
    	    				table1.setRelapseMaleHIV(table1.getRelapseMaleHIV() + 1);
    	    			
    	    			if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
	    					table1.setRelapseMale04(table1.getRelapseMale04()+1);
	    					if(hivPositive)
	    						table1.setRelapseMaleHIV04(table1.getRelapseMaleHIV04()+1);
	    				}
	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
	    					table1.setRelapseMale0514(table1.getRelapseMale0514()+1);
	    					if(hivPositive)
	    						table1.setRelapseMaleHIV0514(table1.getRelapseMaleHIV0514()+1);
	    				}
	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
	    					table1.setRelapseMale1517(table1.getRelapseMale1517()+1);
	    					if(hivPositive)
	    						table1.setRelapseMaleHIV1517(table1.getRelapseMaleHIV1517()+1);
	    				}
	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
	    					table1.setRelapseMale1819(table1.getRelapseMale1819()+1);
	    					if(hivPositive)
	    						table1.setRelapseMaleHIV1819(table1.getRelapseMaleHIV1819()+1);
	    				}
	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
	    					table1.setRelapseMale2024(table1.getRelapseMale2024()+1);
	    					if(hivPositive)
	    						table1.setRelapseMaleHIV2024(table1.getRelapseMaleHIV2024()+1);
	    				}
	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
	    					table1.setRelapseMale2534(table1.getRelapseMale2534()+1);
	    					if(hivPositive)
	    						table1.setRelapseMaleHIV2534(table1.getRelapseMaleHIV2534()+1);
	    				}
	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
	    					table1.setRelapseMale3544(table1.getRelapseMale3544()+1);
	    					if(hivPositive)
	    						table1.setRelapseMaleHIV3544(table1.getRelapseMaleHIV3544()+1);
	    				}
	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
	    					table1.setRelapseMale4554(table1.getRelapseMale4554()+1);
	    					if(hivPositive)
	    						table1.setRelapseMaleHIV4554(table1.getRelapseMaleHIV4554()+1);
	    				}
	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
	    					table1.setRelapseMale5564(table1.getRelapseMale5564()+1);
	    					if(hivPositive)
	    						table1.setRelapseMaleHIV5564(table1.getRelapseMaleHIV5564()+1);
	    				}
	    				else if(ageAtRegistration >= 65) {
	    					table1.setRelapseMale65(table1.getRelapseMale65()+1);
	    					if(hivPositive)
	    						table1.setRelapseMaleHIV65(table1.getRelapseMaleHIV65()+1);
	    				}
    	    			
    	    			//P
    	    			if(pulmonary != null &&  pulmonary) {
    	    				
    	    				
    	    				
    	    				//BC
    	    				if(bacPositive) {
    	    					
    	    					table1.setRelapseMalePulmonaryBC(table1.getRelapseMalePulmonaryBC() + 1);
            	    			
        	    				if(hivPositive)
        	    					table1.setRelapseMalePulmonaryBCHIV(table1.getRelapseMalePulmonaryBCHIV() + 1);
    	    					
    	    					if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
        	    					table1.setRelapseMalePulmonaryBC04(table1.getRelapseMalePulmonaryBC04()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseMalePulmonaryBCHIV04(table1.getRelapseMalePulmonaryBCHIV04()+1);
        	    				}
        	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
        	    					table1.setRelapseMalePulmonaryBC0514(table1.getRelapseMalePulmonaryBC0514()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseMalePulmonaryBCHIV0514(table1.getRelapseMalePulmonaryBCHIV0514()+1);
        	    				}
        	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
        	    					table1.setRelapseMalePulmonaryBC1517(table1.getRelapseMalePulmonaryBC1517()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseMalePulmonaryBCHIV1517(table1.getRelapseMalePulmonaryBCHIV1517()+1);
        	    				}
        	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
        	    					table1.setRelapseMalePulmonaryBC1819(table1.getRelapseMalePulmonaryBC1819()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseMalePulmonaryBCHIV1819(table1.getRelapseMalePulmonaryBCHIV1819()+1);
        	    				}
        	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
        	    					table1.setRelapseMalePulmonaryBC2024(table1.getRelapseMalePulmonaryBC2024()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseMalePulmonaryBCHIV2024(table1.getRelapseMalePulmonaryBCHIV2024()+1);
        	    				}
        	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
        	    					table1.setRelapseMalePulmonaryBC2534(table1.getRelapseMalePulmonaryBC2534()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseMalePulmonaryBCHIV2534(table1.getRelapseMalePulmonaryBCHIV2534()+1);
        	    				}
        	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
        	    					table1.setRelapseMalePulmonaryBC3544(table1.getRelapseMalePulmonaryBC3544()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseMalePulmonaryBCHIV3544(table1.getRelapseMalePulmonaryBCHIV3544()+1);
        	    				}
        	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
        	    					table1.setRelapseMalePulmonaryBC4554(table1.getRelapseMalePulmonaryBC4554()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseMalePulmonaryBCHIV4554(table1.getRelapseMalePulmonaryBCHIV4554()+1);
        	    				}
        	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
        	    					table1.setRelapseMalePulmonaryBC5564(table1.getRelapseMalePulmonaryBC5564()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseMalePulmonaryBCHIV5564(table1.getRelapseMalePulmonaryBCHIV5564()+1);
        	    				}
        	    				else if(ageAtRegistration >= 65) {
        	    					table1.setRelapseMalePulmonaryBC65(table1.getRelapseMalePulmonaryBC65()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseMalePulmonaryBCHIV65(table1.getRelapseMalePulmonaryBCHIV65()+1);
        	    				}
    	    				}
    	    				
    	    				//CD
    	    				else {
    	    					
    	    					table1.setRelapseMalePulmonaryCD(table1.getRelapseMalePulmonaryCD() + 1);
            	    			
        	    				if(hivPositive)
        	    					table1.setRelapseMalePulmonaryCDHIV(table1.getRelapseMalePulmonaryCDHIV() + 1);
    	    					
    	    					if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
        	    					table1.setRelapseMalePulmonaryCD04(table1.getRelapseMalePulmonaryCD04()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseMalePulmonaryCDHIV04(table1.getRelapseMalePulmonaryCDHIV04()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
        	    					table1.setRelapseMalePulmonaryCD0514(table1.getRelapseMalePulmonaryCD0514()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseMalePulmonaryCDHIV0514(table1.getRelapseMalePulmonaryCDHIV0514()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
        	    					table1.setRelapseMalePulmonaryCD1517(table1.getRelapseMalePulmonaryCD1517()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseMalePulmonaryCDHIV1517(table1.getRelapseMalePulmonaryCDHIV1517()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
        	    					table1.setRelapseMalePulmonaryCD1819(table1.getRelapseMalePulmonaryCD1819()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseMalePulmonaryCDHIV1819(table1.getRelapseMalePulmonaryCDHIV1819()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
        	    					table1.setRelapseMalePulmonaryCD2024(table1.getRelapseMalePulmonaryCD2024()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseMalePulmonaryCDHIV2024(table1.getRelapseMalePulmonaryCDHIV2024()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
        	    					table1.setRelapseMalePulmonaryCD2534(table1.getRelapseMalePulmonaryCD2534()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseMalePulmonaryCDHIV2534(table1.getRelapseMalePulmonaryCDHIV2534()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
        	    					table1.setRelapseMalePulmonaryCD3544(table1.getRelapseMalePulmonaryCD3544()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseMalePulmonaryCDHIV3544(table1.getRelapseMalePulmonaryCDHIV3544()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
        	    					table1.setRelapseMalePulmonaryCD4554(table1.getRelapseMalePulmonaryCD4554()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseMalePulmonaryCDHIV4554(table1.getRelapseMalePulmonaryCDHIV4554()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
        	    					table1.setRelapseMalePulmonaryCD5564(table1.getRelapseMalePulmonaryCD5564()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseMalePulmonaryCDHIV5564(table1.getRelapseMalePulmonaryCDHIV5564()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 65) {
        	    					table1.setRelapseMalePulmonaryCD65(table1.getRelapseMalePulmonaryCD65()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseMalePulmonaryCDHIV65(table1.getRelapseMalePulmonaryCDHIV65()+1);
        	    					}
        	    				}
    	    				}
    	    			}
    	    			
    	    			
    	    			//EP
    	    			else if(pulmonary!=null && !pulmonary) {
    	    				
    	    				table1.setRelapseMaleExtrapulmonary(table1.getRelapseMaleExtrapulmonary() + 1);
    	    				
    	    				if(hivPositive)
        	    				table1.setRelapseMaleExtrapulmonaryHIV(table1.getRelapseMaleExtrapulmonaryHIV() + 1);
    	    				
    	    				
    	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
    	    					table1.setRelapseMaleExtrapulmonary04(table1.getRelapseMaleExtrapulmonary04()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseMaleExtrapulmonaryHIV04(table1.getRelapseMaleExtrapulmonaryHIV04()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
    	    					table1.setRelapseMaleExtrapulmonary0514(table1.getRelapseMaleExtrapulmonary0514()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseMaleExtrapulmonaryHIV0514(table1.getRelapseMaleExtrapulmonaryHIV0514()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
    	    					table1.setRelapseMaleExtrapulmonary1517(table1.getRelapseMaleExtrapulmonary1517()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseMaleExtrapulmonaryHIV1517(table1.getRelapseMaleExtrapulmonaryHIV1517()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
    	    					table1.setRelapseMaleExtrapulmonary1819(table1.getRelapseMaleExtrapulmonary1819()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseMaleExtrapulmonaryHIV1819(table1.getRelapseMaleExtrapulmonaryHIV1819()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
    	    					table1.setRelapseMaleExtrapulmonary2024(table1.getRelapseMaleExtrapulmonary2024()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseMaleExtrapulmonaryHIV2024(table1.getRelapseMaleExtrapulmonaryHIV2024()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
    	    					table1.setRelapseMaleExtrapulmonary2534(table1.getRelapseMaleExtrapulmonary2534()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseMaleExtrapulmonaryHIV2534(table1.getRelapseMaleExtrapulmonaryHIV2534()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
    	    					table1.setRelapseMaleExtrapulmonary3544(table1.getRelapseMaleExtrapulmonary3544()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseMaleExtrapulmonaryHIV3544(table1.getRelapseMaleExtrapulmonaryHIV3544()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
    	    					table1.setRelapseMaleExtrapulmonary4554(table1.getRelapseMaleExtrapulmonary4554()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseMaleExtrapulmonaryHIV4554(table1.getRelapseMaleExtrapulmonaryHIV4554()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
    	    					table1.setRelapseMaleExtrapulmonary5564(table1.getRelapseMaleExtrapulmonary5564()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseMaleExtrapulmonaryHIV5564(table1.getRelapseMaleExtrapulmonaryHIV5564()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 65) {
    	    					table1.setRelapseMaleExtrapulmonary65(table1.getRelapseMaleExtrapulmonary65()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseMaleExtrapulmonaryHIV65(table1.getRelapseMaleExtrapulmonaryHIV65()+1);
    	    					}
    	    				}
    	    			}
    	    		}
    	    			
    	    		
    	    		
    	    		else if(patient.getGender().equals("F")) {
    	    			table1.setRelapseFemale(table1.getRelapseFemale() + 1);
    	    			
    	    			if(hivPositive)
    	    				table1.setRelapseFemaleHIV(table1.getRelapseFemaleHIV() + 1);
    	    			
    	    			if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
	    					table1.setRelapseFemale04(table1.getRelapseFemale04()+1);
	    					if(hivPositive)
	    						table1.setRelapseFemaleHIV04(table1.getRelapseFemaleHIV04()+1);
	    				}
	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
	    					table1.setRelapseFemale0514(table1.getRelapseFemale0514()+1);
	    					if(hivPositive)
	    						table1.setRelapseFemaleHIV0514(table1.getRelapseFemaleHIV0514()+1);
	    				}
	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
	    					table1.setRelapseFemale1517(table1.getRelapseFemale1517()+1);
	    					if(hivPositive)
	    						table1.setRelapseFemaleHIV1517(table1.getRelapseFemaleHIV1517()+1);
	    				}
	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
	    					table1.setRelapseFemale1819(table1.getRelapseFemale1819()+1);
	    					if(hivPositive)
	    						table1.setRelapseFemaleHIV1819(table1.getRelapseFemaleHIV1819()+1);
	    				}
	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
	    					table1.setRelapseFemale2024(table1.getRelapseFemale2024()+1);
	    					if(hivPositive)
	    						table1.setRelapseFemaleHIV2024(table1.getRelapseFemaleHIV2024()+1);
	    				}
	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
	    					table1.setRelapseFemale2534(table1.getRelapseFemale2534()+1);
	    					if(hivPositive)
	    						table1.setRelapseFemaleHIV2534(table1.getRelapseFemaleHIV2534()+1);
	    				}
	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
	    					table1.setRelapseFemale3544(table1.getRelapseFemale3544()+1);
	    					if(hivPositive)
	    						table1.setRelapseFemaleHIV3544(table1.getRelapseFemaleHIV3544()+1);
	    				}
	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
	    					table1.setRelapseFemale4554(table1.getRelapseFemale4554()+1);
	    					if(hivPositive)
	    						table1.setRelapseFemaleHIV4554(table1.getRelapseFemaleHIV4554()+1);
	    				}
	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
	    					table1.setRelapseFemale5564(table1.getRelapseFemale5564()+1);
	    					if(hivPositive)
	    						table1.setRelapseFemaleHIV5564(table1.getRelapseFemaleHIV5564()+1);
	    				}
	    				else if(ageAtRegistration >= 65) {
	    					table1.setRelapseFemale65(table1.getRelapseFemale65()+1);
	    					if(hivPositive)
	    						table1.setRelapseFemaleHIV65(table1.getRelapseFemaleHIV65()+1);
	    				}
    	    			
    	    			//P
    	    			if(pulmonary != null &&  pulmonary) {
    	    				
    	    				
    	    				
    	    				
    	    				
    	    				//BC
    	    				if(bacPositive) {
    	    					
    	    					
    	    					table1.setRelapseFemalePulmonaryBC(table1.getRelapseFemalePulmonaryBC() + 1);
    	    	    			
    	    	    			if(hivPositive)
    	    	    				table1.setRelapseFemalePulmonaryBCHIV(table1.getRelapseFemalePulmonaryBCHIV() + 1);
    	    					
    	    					if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
        	    					table1.setRelapseFemalePulmonaryBC04(table1.getRelapseFemalePulmonaryBC04()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseFemalePulmonaryBCHIV04(table1.getRelapseFemalePulmonaryBCHIV04()+1);
        	    				}
        	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
        	    					table1.setRelapseFemalePulmonaryBC0514(table1.getRelapseFemalePulmonaryBC0514()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseFemalePulmonaryBCHIV0514(table1.getRelapseFemalePulmonaryBCHIV0514()+1);
        	    				}
        	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
        	    					table1.setRelapseFemalePulmonaryBC1517(table1.getRelapseFemalePulmonaryBC1517()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseFemalePulmonaryBCHIV1517(table1.getRelapseFemalePulmonaryBCHIV1517()+1);
        	    				}
        	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
        	    					table1.setRelapseFemalePulmonaryBC1819(table1.getRelapseFemalePulmonaryBC1819()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseFemalePulmonaryBCHIV1819(table1.getRelapseFemalePulmonaryBCHIV1819()+1);
        	    				}
        	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
        	    					table1.setRelapseFemalePulmonaryBC2024(table1.getRelapseFemalePulmonaryBC2024()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseFemalePulmonaryBCHIV2024(table1.getRelapseFemalePulmonaryBCHIV2024()+1);
        	    				}
        	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
        	    					table1.setRelapseFemalePulmonaryBC2534(table1.getRelapseFemalePulmonaryBC2534()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseFemalePulmonaryBCHIV2534(table1.getRelapseFemalePulmonaryBCHIV2534()+1);
        	    				}
        	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
        	    					table1.setRelapseFemalePulmonaryBC3544(table1.getRelapseFemalePulmonaryBC3544()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseFemalePulmonaryBCHIV3544(table1.getRelapseFemalePulmonaryBCHIV3544()+1);
        	    				}
        	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
        	    					table1.setRelapseFemalePulmonaryBC4554(table1.getRelapseFemalePulmonaryBC4554()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseFemalePulmonaryBCHIV4554(table1.getRelapseFemalePulmonaryBCHIV4554()+1);
        	    				}
        	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
        	    					table1.setRelapseFemalePulmonaryBC5564(table1.getRelapseFemalePulmonaryBC5564()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseFemalePulmonaryBCHIV5564(table1.getRelapseFemalePulmonaryBCHIV5564()+1);
        	    				}
        	    				else if(ageAtRegistration >= 65) {
        	    					table1.setRelapseFemalePulmonaryBC65(table1.getRelapseFemalePulmonaryBC65()+1);
        	    					if(hivPositive)
        	    						table1.setRelapseFemalePulmonaryBCHIV65(table1.getRelapseFemalePulmonaryBCHIV65()+1);
        	    				}
    	    				}
    	    				
    	    				//CD
    	    				else {
    	    					
    	    					table1.setRelapseFemalePulmonaryCD(table1.getRelapseFemalePulmonaryCD() + 1);
    	    	    			
    	    	    			if(hivPositive)
    	    	    				table1.setRelapseFemalePulmonaryCDHIV(table1.getRelapseFemalePulmonaryCDHIV() + 1);
    	    					
    	    					if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
        	    					table1.setRelapseFemalePulmonaryCD04(table1.getRelapseFemalePulmonaryCD04()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseFemalePulmonaryCDHIV04(table1.getRelapseFemalePulmonaryCDHIV04()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
        	    					table1.setRelapseFemalePulmonaryCD0514(table1.getRelapseFemalePulmonaryCD0514()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseFemalePulmonaryCDHIV0514(table1.getRelapseFemalePulmonaryCDHIV0514()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
        	    					table1.setRelapseFemalePulmonaryCD1517(table1.getRelapseFemalePulmonaryCD1517()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseFemalePulmonaryCDHIV1517(table1.getRelapseFemalePulmonaryCDHIV1517()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
        	    					table1.setRelapseFemalePulmonaryCD1819(table1.getRelapseFemalePulmonaryCD1819()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseFemalePulmonaryCDHIV1819(table1.getRelapseFemalePulmonaryCDHIV1819()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
        	    					table1.setRelapseFemalePulmonaryCD2024(table1.getRelapseFemalePulmonaryCD2024()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseFemalePulmonaryCDHIV2024(table1.getRelapseFemalePulmonaryCDHIV2024()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
        	    					table1.setRelapseFemalePulmonaryCD2534(table1.getRelapseFemalePulmonaryCD2534()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseFemalePulmonaryCDHIV2534(table1.getRelapseFemalePulmonaryCDHIV2534()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
        	    					table1.setRelapseFemalePulmonaryCD3544(table1.getRelapseFemalePulmonaryCD3544()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseFemalePulmonaryCDHIV3544(table1.getRelapseFemalePulmonaryCDHIV3544()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
        	    					table1.setRelapseFemalePulmonaryCD4554(table1.getRelapseFemalePulmonaryCD4554()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseFemalePulmonaryCDHIV4554(table1.getRelapseFemalePulmonaryCDHIV4554()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
        	    					table1.setRelapseFemalePulmonaryCD5564(table1.getRelapseFemalePulmonaryCD5564()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseFemalePulmonaryCDHIV5564(table1.getRelapseFemalePulmonaryCDHIV5564()+1);
        	    					}
        	    				}
        	    				else if(ageAtRegistration >= 65) {
        	    					table1.setRelapseFemalePulmonaryCD65(table1.getRelapseFemalePulmonaryCD65()+1);
        	    					if(hivPositive) {
        	    						table1.setRelapseFemalePulmonaryCDHIV65(table1.getRelapseFemalePulmonaryCDHIV65()+1);
        	    					}
        	    				}
    	    				}
    	    			}
    	    			
    	    			
    	    			//EP
    	    			else if(pulmonary!=null && !pulmonary) {
    	    				
    	    				table1.setRelapseFemaleExtrapulmonary(table1.getRelapseFemaleExtrapulmonary() + 1);
	    	    			
	    	    			if(hivPositive)
	    	    				table1.setRelapseFemaleExtrapulmonaryHIV(table1.getRelapseFemaleExtrapulmonaryHIV() + 1);
    	    				
    	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
    	    					table1.setRelapseFemaleExtrapulmonary04(table1.getRelapseFemaleExtrapulmonary04()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseFemaleExtrapulmonaryHIV04(table1.getRelapseFemaleExtrapulmonaryHIV04()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
    	    					table1.setRelapseFemaleExtrapulmonary0514(table1.getRelapseFemaleExtrapulmonary0514()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseFemaleExtrapulmonaryHIV0514(table1.getRelapseFemaleExtrapulmonaryHIV0514()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
    	    					table1.setRelapseFemaleExtrapulmonary1517(table1.getRelapseFemaleExtrapulmonary1517()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseFemaleExtrapulmonaryHIV1517(table1.getRelapseFemaleExtrapulmonaryHIV1517()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 18 && ageAtRegistration < 20) {
    	    					table1.setRelapseFemaleExtrapulmonary1819(table1.getRelapseFemaleExtrapulmonary1819()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseFemaleExtrapulmonaryHIV1819(table1.getRelapseFemaleExtrapulmonaryHIV1819()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 20 && ageAtRegistration < 25) {
    	    					table1.setRelapseFemaleExtrapulmonary2024(table1.getRelapseFemaleExtrapulmonary2024()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseFemaleExtrapulmonaryHIV2024(table1.getRelapseFemaleExtrapulmonaryHIV2024()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 25 && ageAtRegistration < 35) {
    	    					table1.setRelapseFemaleExtrapulmonary2534(table1.getRelapseFemaleExtrapulmonary2534()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseFemaleExtrapulmonaryHIV2534(table1.getRelapseFemaleExtrapulmonaryHIV2534()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 35 && ageAtRegistration < 45) {
    	    					table1.setRelapseFemaleExtrapulmonary3544(table1.getRelapseFemaleExtrapulmonary3544()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseFemaleExtrapulmonaryHIV3544(table1.getRelapseFemaleExtrapulmonaryHIV3544()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 45 && ageAtRegistration < 55) {
    	    					table1.setRelapseFemaleExtrapulmonary4554(table1.getRelapseFemaleExtrapulmonary4554()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseFemaleExtrapulmonaryHIV4554(table1.getRelapseFemaleExtrapulmonaryHIV4554()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 55 && ageAtRegistration < 65) {
    	    					table1.setRelapseFemaleExtrapulmonary5564(table1.getRelapseFemaleExtrapulmonary5564()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseFemaleExtrapulmonaryHIV5564(table1.getRelapseFemaleExtrapulmonaryHIV5564()+1);
    	    					}
    	    				}
    	    				else if(ageAtRegistration >= 65) {
    	    					table1.setRelapseFemaleExtrapulmonary65(table1.getRelapseFemaleExtrapulmonary65()+1);
    	    					if(hivPositive) {
    	    						table1.setRelapseFemaleExtrapulmonaryHIV65(table1.getRelapseFemaleExtrapulmonaryHIV65()+1);
    	    					}
    	    				}
    	    			}
    	    			
    	    		}
    	    	}
    	    	
    	    	//FAILURE
    	    	else if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterFailure1.conceptId")) || q.getConceptId()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterFailure2.conceptId"))) { 
    	    		table1.setFailureAll(table1.getFailureAll() + 1);
	    			if(hivPositive)
	    				table1.setFailureAllHIV(table1.getFailureAllHIV() + 1);
	    			
	    			table1.setRetreatmentAll(table1.getRetreatmentAll() + 1);
	    			if(hivPositive)
	    				table1.setRetreatmentAllHIV(table1.getRetreatmentAllHIV() + 1);
    	    		
    	    		if(patient.getGender().equals("M")) {
    	    			
    	    			table1.setFailureMale(table1.getFailureMale() + 1);
    	    			if(hivPositive)
    	    				table1.setFailureMaleHIV(table1.getFailureMaleHIV() + 1);
    	    			
    	    			table1.setRetreatmentMale(table1.getRetreatmentMale() + 1);
    	    			if(hivPositive)
    	    				table1.setRetreatmentMaleHIV(table1.getRetreatmentMaleHIV() + 1);
    	    		
    	    			//P
    	    			if(pulmonary != null &&  pulmonary) {
    	    				
    	    				//BC
    	    				if(bacPositive) {
    	    					table1.setFailureMalePulmonaryBC(table1.getFailureMalePulmonaryBC() + 1);
    	    					if(hivPositive) {
    	    						table1.setFailureMalePulmonaryBCHIV(table1.getFailureMalePulmonaryBCHIV() + 1);
    	    					}
    	    				}
    	    				
    	    				//CD
    	    				else {
    	    					table1.setFailureMalePulmonaryCD(table1.getFailureMalePulmonaryCD() + 1);
    	    					if(hivPositive) {
    	    						table1.setFailureMalePulmonaryCDHIV(table1.getFailureMalePulmonaryCDHIV() + 1);
    	    					}
    	    					
    	    				}

    	    			}
    	    			
    	    			else if(pulmonary != null && !pulmonary) {
    	    				table1.setFailureMaleExtrapulmonary(table1.getFailureMaleExtrapulmonary() + 1);
	    					if(hivPositive) {
	    						table1.setFailureMaleExtrapulmonaryHIV(table1.getFailureMaleExtrapulmonaryHIV() + 1);
	    					}
    	    			}
    	    		}
    	    			
    	    		else if(patient.getGender().equals("F")) {
    	    			table1.setFailureFemale(table1.getFailureFemale() + 1);
    	    			if(hivPositive)
    	    				table1.setFailureFemaleHIV(table1.getFailureFemaleHIV() + 1);
    	    			
    	    			table1.setRetreatmentFemale(table1.getRetreatmentFemale() + 1);
    	    			if(hivPositive)
    	    				table1.setRetreatmentFemaleHIV(table1.getRetreatmentFemaleHIV() + 1);
    	    		
    	    			//P
    	    			if(pulmonary != null &&  pulmonary) {
    	    				
    	    				//BC
    	    				if(bacPositive) {
    	    					table1.setFailureFemalePulmonaryBC(table1.getFailureFemalePulmonaryBC() + 1);
    	    					if(hivPositive) {
    	    						table1.setFailureFemalePulmonaryBCHIV(table1.getFailureFemalePulmonaryBCHIV() + 1);
    	    					}
    	    				}
    	    				
    	    				//CD
    	    				else {
    	    					table1.setFailureFemalePulmonaryCD(table1.getFailureFemalePulmonaryCD() + 1);
    	    					if(hivPositive) {
    	    						table1.setFailureFemalePulmonaryCDHIV(table1.getFailureFemalePulmonaryCDHIV() + 1);
    	    					}
    	    					
    	    				}

    	    			}
    	    			
    	    			else if(pulmonary != null && !pulmonary) {
    	    				table1.setFailureFemaleExtrapulmonary(table1.getFailureFemaleExtrapulmonary() + 1);
	    					if(hivPositive) {
	    						table1.setFailureFemaleExtrapulmonaryHIV(table1.getFailureFemaleExtrapulmonaryHIV() + 1);
	    					}
    	    			}
    	    		}
    	    		
    	    		
    	    	}
    	    
    	    	//DEFAULT
	    		else if(q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterDefault1.conceptId")) || q.getConceptId()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterDefault2.conceptId"))) {
	    			table1.setDefaultAll(table1.getDefaultAll() + 1);
	    			if(hivPositive)
	    				table1.setDefaultAllHIV(table1.getDefaultAllHIV() + 1);
    	    		
	    			table1.setRetreatmentAll(table1.getRetreatmentAll() + 1);
	    			if(hivPositive)
	    				table1.setRetreatmentAllHIV(table1.getRetreatmentAllHIV() + 1);
	    			
    	    		if(patient.getGender().equals("M")) {
    	    			
    	    			table1.setDefaultMale(table1.getDefaultMale() + 1);
    	    			if(hivPositive)
    	    				table1.setDefaultMaleHIV(table1.getDefaultMaleHIV() + 1);
    	    			
    	    			table1.setRetreatmentMale(table1.getRetreatmentMale() + 1);
    	    			if(hivPositive)
    	    				table1.setRetreatmentMaleHIV(table1.getRetreatmentMaleHIV() + 1);
    	    		
    	    			//P
    	    			if(pulmonary != null &&  pulmonary) {
    	    				
    	    				//BC
    	    				if(bacPositive) {
    	    					table1.setDefaultMalePulmonaryBC(table1.getDefaultMalePulmonaryBC() + 1);
    	    					if(hivPositive) {
    	    						table1.setDefaultMalePulmonaryBCHIV(table1.getDefaultMalePulmonaryBCHIV() + 1);
    	    					}
    	    				}
    	    				
    	    				//CD
    	    				else {
    	    					table1.setDefaultMalePulmonaryCD(table1.getDefaultMalePulmonaryCD() + 1);
    	    					if(hivPositive) {
    	    						table1.setDefaultMalePulmonaryCDHIV(table1.getDefaultMalePulmonaryCDHIV() + 1);
    	    					}
    	    					
    	    				}

    	    			}
    	    			
    	    			else if(pulmonary != null && !pulmonary) {
    	    				table1.setDefaultMaleExtrapulmonary(table1.getDefaultMaleExtrapulmonary() + 1);
	    					if(hivPositive) {
	    						table1.setDefaultMaleExtrapulmonaryHIV(table1.getDefaultMaleExtrapulmonaryHIV() + 1);
	    					}
    	    			}
    	    		}
    	    			
    	    		else if(patient.getGender().equals("F")) {
    	    			table1.setDefaultFemale(table1.getDefaultFemale() + 1);
    	    			if(hivPositive)
    	    				table1.setDefaultFemaleHIV(table1.getDefaultFemaleHIV() + 1);
    	    			
    	    			table1.setRetreatmentFemale(table1.getRetreatmentFemale() + 1);
    	    			if(hivPositive)
    	    				table1.setRetreatmentFemaleHIV(table1.getRetreatmentFemaleHIV() + 1);
    	    		
    	    			//P
    	    			if(pulmonary != null &&  pulmonary) {
    	    				
    	    				//BC
    	    				if(bacPositive) {
    	    					table1.setDefaultFemalePulmonaryBC(table1.getDefaultFemalePulmonaryBC() + 1);
    	    					if(hivPositive) {
    	    						table1.setDefaultFemalePulmonaryBCHIV(table1.getDefaultFemalePulmonaryBCHIV() + 1);
    	    					}
    	    				}
    	    				
    	    				//CD
    	    				else {
    	    					table1.setDefaultFemalePulmonaryCD(table1.getDefaultFemalePulmonaryCD() + 1);
    	    					if(hivPositive) {
    	    						table1.setDefaultFemalePulmonaryCDHIV(table1.getDefaultFemalePulmonaryCDHIV() + 1);
    	    					}
    	    					
    	    				}

    	    			}
    	    			
    	    			else if(pulmonary != null && !pulmonary) {
    	    				table1.setDefaultFemaleExtrapulmonary(table1.getDefaultFemaleExtrapulmonary() + 1);
	    					if(hivPositive) {
	    						table1.setDefaultFemaleExtrapulmonaryHIV(table1.getDefaultFemaleExtrapulmonaryHIV() + 1);
	    					}
    	    			}
    	    		}
	    			
	    		}
    	    
    	    	
    	    			
    	    	//OTHER		
    	    	else if (q.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.other.conceptId"))) {
    	    		table1.setOtherAll(table1.getOtherAll() + 1);
	    			if(hivPositive)
	    				table1.setOtherAllHIV(table1.getOtherAllHIV() + 1);
	    			
	    			table1.setRetreatmentAll(table1.getRetreatmentAll() + 1);
	    			if(hivPositive)
	    				table1.setRetreatmentAllHIV(table1.getRetreatmentAllHIV() + 1);
    	    		
    	    		if(patient.getGender().equals("M")) {
    	    			
    	    			table1.setOtherMale(table1.getOtherMale() + 1);
    	    			if(hivPositive)
    	    				table1.setOtherMaleHIV(table1.getOtherMaleHIV() + 1);
    	    			
    	    			table1.setRetreatmentMale(table1.getRetreatmentMale() + 1);
    	    			if(hivPositive)
    	    				table1.setRetreatmentMaleHIV(table1.getRetreatmentMaleHIV() + 1);
    	    		
    	    			//P
    	    			if(pulmonary != null &&  pulmonary) {
    	    				
    	    				//BC
    	    				if(bacPositive) {
    	    					table1.setOtherMalePulmonaryBC(table1.getOtherMalePulmonaryBC() + 1);
    	    					if(hivPositive) {
    	    						table1.setOtherMalePulmonaryBCHIV(table1.getOtherMalePulmonaryBCHIV() + 1);
    	    					}
    	    				}
    	    				
    	    				//CD
    	    				else {
    	    					table1.setOtherMalePulmonaryCD(table1.getOtherMalePulmonaryCD() + 1);
    	    					if(hivPositive) {
    	    						table1.setOtherMalePulmonaryCDHIV(table1.getOtherMalePulmonaryCDHIV() + 1);
    	    					}
    	    					
    	    				}

    	    			}
    	    			
    	    			else if(pulmonary != null && !pulmonary) {
    	    				table1.setOtherMaleExtrapulmonary(table1.getOtherMaleExtrapulmonary() + 1);
	    					if(hivPositive) {
	    						table1.setOtherMaleExtrapulmonaryHIV(table1.getOtherMaleExtrapulmonaryHIV() + 1);
	    					}
    	    			}
    	    		}
    	    			
    	    		else if(patient.getGender().equals("F")) {
    	    			table1.setOtherFemale(table1.getOtherFemale() + 1);
    	    			if(hivPositive)
    	    				table1.setOtherFemaleHIV(table1.getOtherFemaleHIV() + 1);
    	    			
    	    			table1.setRetreatmentFemale(table1.getRetreatmentFemale() + 1);
    	    			if(hivPositive)
    	    				table1.setRetreatmentFemaleHIV(table1.getRetreatmentFemaleHIV() + 1);
    	    		
    	    			//P
    	    			if(pulmonary != null &&  pulmonary) {
    	    				
    	    				//BC
    	    				if(bacPositive) {
    	    					table1.setOtherFemalePulmonaryBC(table1.getOtherFemalePulmonaryBC() + 1);
    	    					if(hivPositive) {
    	    						table1.setOtherFemalePulmonaryBCHIV(table1.getOtherFemalePulmonaryBCHIV() + 1);
    	    					}
    	    				}
    	    				
    	    				//CD
    	    				else {
    	    					table1.setOtherFemalePulmonaryCD(table1.getOtherFemalePulmonaryCD() + 1);
    	    					if(hivPositive) {
    	    						table1.setOtherFemalePulmonaryCDHIV(table1.getOtherFemalePulmonaryCDHIV() + 1);
    	    					}
    	    				}
    	    			}
    	    			
    	    			else if(pulmonary != null && !pulmonary) {
    	    				table1.setOtherFemaleExtrapulmonary(table1.getOtherFemaleExtrapulmonary() + 1);
	    					if(hivPositive) {
	    						table1.setOtherFemaleExtrapulmonaryHIV(table1.getOtherFemaleExtrapulmonaryHIV() + 1);
	    					}
    	    			}
    	    		}
    	    	}
    	    }
    	    
    	    else {
    	    	//System.out.println("NO GROUP");
    	    	continue;
    	    }

    	}
    	
    	
    	
    	//TOTALS
    	table1.setNewPulmonaryBC(table1.getNewMalePulmonaryBC() + table1.getNewFemalePulmonaryBC());
    	table1.setNewPulmonaryBCHIV(table1.getNewMalePulmonaryBCHIV() + table1.getNewFemalePulmonaryBCHIV());
    	table1.setNewPulmonaryCD(table1.getNewMalePulmonaryCD() + table1.getNewFemalePulmonaryCD());
    	table1.setNewPulmonaryCDHIV(table1.getNewMalePulmonaryCDHIV() + table1.getNewFemalePulmonaryCDHIV());
    	table1.setNewExtrapulmonary(table1.getNewMaleExtrapulmonary() + table1.getNewFemaleExtrapulmonary());
    	table1.setNewExtrapulmonaryHIV(table1.getNewMaleExtrapulmonaryHIV() + table1.getNewFemaleExtrapulmonaryHIV());
    	
    	table1.setRelapsePulmonaryBC(table1.getRelapseMalePulmonaryBC() + table1.getRelapseFemalePulmonaryBC());
    	table1.setRelapsePulmonaryBCHIV(table1.getRelapseMalePulmonaryBCHIV() + table1.getRelapseFemalePulmonaryBCHIV());
    	table1.setRelapsePulmonaryCD(table1.getRelapseMalePulmonaryCD() + table1.getRelapseFemalePulmonaryCD());
    	table1.setRelapsePulmonaryCDHIV(table1.getRelapseMalePulmonaryCDHIV() + table1.getRelapseFemalePulmonaryCDHIV());
    	table1.setRelapseExtrapulmonary(table1.getRelapseMaleExtrapulmonary() + table1.getRelapseFemaleExtrapulmonary());
    	table1.setRelapseExtrapulmonaryHIV(table1.getRelapseMaleExtrapulmonaryHIV() + table1.getRelapseFemaleExtrapulmonaryHIV());
    	
    	table1.setFailurePulmonaryBC(table1.getFailureMalePulmonaryBC() + table1.getFailureFemalePulmonaryBC());
    	table1.setFailurePulmonaryBCHIV(table1.getFailureMalePulmonaryBCHIV() + table1.getFailureFemalePulmonaryBCHIV());
    	table1.setFailurePulmonaryCD(table1.getFailureMalePulmonaryCD() + table1.getFailureFemalePulmonaryCD());
    	table1.setFailurePulmonaryCDHIV(table1.getFailureMalePulmonaryCDHIV() + table1.getFailureFemalePulmonaryCDHIV());
    	table1.setFailureExtrapulmonary(table1.getFailureMaleExtrapulmonary() + table1.getFailureFemaleExtrapulmonary());
    	table1.setFailureExtrapulmonaryHIV(table1.getFailureMaleExtrapulmonaryHIV() + table1.getFailureFemaleExtrapulmonaryHIV());
    	
    	table1.setDefaultPulmonaryBC(table1.getDefaultMalePulmonaryBC() + table1.getDefaultFemalePulmonaryBC());
    	table1.setDefaultPulmonaryBCHIV(table1.getDefaultMalePulmonaryBCHIV() + table1.getDefaultFemalePulmonaryBCHIV());
    	table1.setDefaultPulmonaryCD(table1.getDefaultMalePulmonaryCD() + table1.getDefaultFemalePulmonaryCD());
    	table1.setDefaultPulmonaryCDHIV(table1.getDefaultMalePulmonaryCDHIV() + table1.getDefaultFemalePulmonaryCDHIV());
    	table1.setDefaultExtrapulmonary(table1.getDefaultMaleExtrapulmonary() + table1.getDefaultFemaleExtrapulmonary());
    	table1.setDefaultExtrapulmonaryHIV(table1.getDefaultMaleExtrapulmonaryHIV() + table1.getDefaultFemaleExtrapulmonaryHIV());
    	
    	table1.setOtherPulmonaryBC(table1.getOtherMalePulmonaryBC() + table1.getOtherFemalePulmonaryBC());
    	table1.setOtherPulmonaryBCHIV(table1.getOtherMalePulmonaryBCHIV() + table1.getOtherFemalePulmonaryBCHIV());
    	table1.setOtherPulmonaryCD(table1.getOtherMalePulmonaryCD() + table1.getOtherFemalePulmonaryCD());
    	table1.setOtherPulmonaryCDHIV(table1.getOtherMalePulmonaryCDHIV() + table1.getOtherFemalePulmonaryCDHIV());
    	table1.setOtherExtrapulmonary(table1.getOtherMaleExtrapulmonary() + table1.getOtherFemaleExtrapulmonary());
    	table1.setOtherExtrapulmonaryHIV(table1.getOtherMaleExtrapulmonaryHIV() + table1.getOtherFemaleExtrapulmonaryHIV());
    	
    	//RETREATMENT TOTAL
    	table1.setRetreatmentMalePulmonaryBC(table1.getFailureMalePulmonaryBC() + table1.getDefaultMalePulmonaryBC() + table1.getOtherMalePulmonaryBC()); 
    	table1.setRetreatmentFemalePulmonaryBC(table1.getFailureFemalePulmonaryBC() + table1.getDefaultFemalePulmonaryBC() + table1.getOtherFemalePulmonaryBC());
    	table1.setRetreatmentPulmonaryBC(table1.getRetreatmentMalePulmonaryBC() + table1.getRetreatmentFemalePulmonaryBC());
    	table1.setRetreatmentMalePulmonaryBCHIV(table1.getFailureMalePulmonaryBCHIV() + table1.getDefaultMalePulmonaryBCHIV() + table1.getOtherMalePulmonaryBCHIV()); 
    	table1.setRetreatmentFemalePulmonaryBCHIV(table1.getFailureFemalePulmonaryBCHIV() + table1.getDefaultFemalePulmonaryBCHIV() + table1.getOtherFemalePulmonaryBCHIV());
    	table1.setRetreatmentPulmonaryBCHIV(table1.getRetreatmentMalePulmonaryBCHIV() + table1.getRetreatmentFemalePulmonaryBCHIV());
    	table1.setRetreatmentMalePulmonaryCD(table1.getFailureMalePulmonaryCD() + table1.getDefaultMalePulmonaryCD() + table1.getOtherMalePulmonaryCD()); 
    	table1.setRetreatmentFemalePulmonaryCD(table1.getFailureFemalePulmonaryCD() + table1.getDefaultFemalePulmonaryCD() + table1.getOtherFemalePulmonaryCD());
    	table1.setRetreatmentPulmonaryCD(table1.getRetreatmentMalePulmonaryCD() + table1.getRetreatmentFemalePulmonaryCD());
    	table1.setRetreatmentMalePulmonaryCDHIV(table1.getFailureMalePulmonaryCDHIV() + table1.getDefaultMalePulmonaryCDHIV() + table1.getOtherMalePulmonaryCDHIV()); 
    	table1.setRetreatmentFemalePulmonaryCDHIV(table1.getFailureFemalePulmonaryCDHIV() + table1.getDefaultFemalePulmonaryCDHIV() + table1.getOtherFemalePulmonaryCDHIV());
    	table1.setRetreatmentPulmonaryCDHIV(table1.getRetreatmentMalePulmonaryCDHIV() + table1.getRetreatmentFemalePulmonaryCDHIV());
    	table1.setRetreatmentMaleExtrapulmonary(table1.getFailureMaleExtrapulmonary() + table1.getDefaultMaleExtrapulmonary() + table1.getOtherMaleExtrapulmonary()); 
    	table1.setRetreatmentFemaleExtrapulmonary(table1.getFailureFemaleExtrapulmonary() + table1.getDefaultFemaleExtrapulmonary() + table1.getOtherFemaleExtrapulmonary());
    	table1.setRetreatmentExtrapulmonary(table1.getRetreatmentMaleExtrapulmonary() + table1.getRetreatmentFemaleExtrapulmonary());
    	table1.setRetreatmentMaleExtrapulmonaryHIV(table1.getFailureMaleExtrapulmonaryHIV() + table1.getDefaultMaleExtrapulmonaryHIV() + table1.getOtherMaleExtrapulmonaryHIV()); 
    	table1.setRetreatmentFemaleExtrapulmonaryHIV(table1.getFailureFemaleExtrapulmonaryHIV() + table1.getDefaultFemaleExtrapulmonaryHIV() + table1.getOtherFemaleExtrapulmonaryHIV());
    	table1.setRetreatmentExtrapulmonaryHIV(table1.getRetreatmentMaleExtrapulmonaryHIV() + table1.getRetreatmentFemaleExtrapulmonaryHIV());
    	
    	//GRAND TOTALS
    	table1.setTotalMalePulmonaryBC(table1.getNewMalePulmonaryBC() + table1.getRelapseMalePulmonaryBC() + table1.getRetreatmentMalePulmonaryBC());
    	table1.setTotalFemalePulmonaryBC(table1.getNewFemalePulmonaryBC() + table1.getRelapseFemalePulmonaryBC() + table1.getRetreatmentFemalePulmonaryBC());
    	table1.setTotalPulmonaryBC(table1.getTotalMalePulmonaryBC() + table1.getTotalFemalePulmonaryBC());
    	
    	table1.setTotalMalePulmonaryBCHIV(table1.getNewMalePulmonaryBCHIV() + table1.getRelapseMalePulmonaryBCHIV() + table1.getRetreatmentMalePulmonaryBCHIV());
    	table1.setTotalFemalePulmonaryBCHIV(table1.getNewFemalePulmonaryBCHIV() + table1.getRelapseFemalePulmonaryBCHIV() + table1.getRetreatmentFemalePulmonaryBCHIV());
    	table1.setTotalPulmonaryBCHIV(table1.getTotalMalePulmonaryBCHIV() + table1.getTotalFemalePulmonaryBCHIV());
    	
    	table1.setTotalMalePulmonaryCD(table1.getNewMalePulmonaryCD() + table1.getRelapseMalePulmonaryCD() + table1.getRetreatmentMalePulmonaryCD());
    	table1.setTotalFemalePulmonaryCD(table1.getNewFemalePulmonaryCD() + table1.getRelapseFemalePulmonaryCD() + table1.getRetreatmentFemalePulmonaryCD());
    	table1.setTotalPulmonaryCD(table1.getTotalMalePulmonaryCD() + table1.getTotalFemalePulmonaryCD());
    	
    	table1.setTotalMalePulmonaryCDHIV(table1.getNewMalePulmonaryCDHIV() + table1.getRelapseMalePulmonaryCDHIV() + table1.getRetreatmentMalePulmonaryCDHIV());
    	table1.setTotalFemalePulmonaryCDHIV(table1.getNewFemalePulmonaryCDHIV() + table1.getRelapseFemalePulmonaryCDHIV() + table1.getRetreatmentFemalePulmonaryCDHIV());
    	table1.setTotalPulmonaryCDHIV(table1.getTotalMalePulmonaryCDHIV() + table1.getTotalFemalePulmonaryCDHIV());
    	
    	table1.setTotalMaleExtrapulmonary(table1.getNewMaleExtrapulmonary() + table1.getRelapseMaleExtrapulmonary() + table1.getRetreatmentMaleExtrapulmonary());
    	table1.setTotalFemaleExtrapulmonary(table1.getNewFemaleExtrapulmonary() + table1.getRelapseFemaleExtrapulmonary() + table1.getRetreatmentFemaleExtrapulmonary());
    	table1.setTotalExtrapulmonary(table1.getTotalMaleExtrapulmonary() + table1.getTotalFemaleExtrapulmonary());
    	
    	table1.setTotalMaleExtrapulmonaryHIV(table1.getNewMaleExtrapulmonaryHIV() + table1.getRelapseMaleExtrapulmonaryHIV() + table1.getRetreatmentMaleExtrapulmonaryHIV());
    	table1.setTotalFemaleExtrapulmonaryHIV(table1.getNewFemaleExtrapulmonaryHIV() + table1.getRelapseFemaleExtrapulmonaryHIV() + table1.getRetreatmentFemaleExtrapulmonaryHIV());
    	table1.setTotalExtrapulmonaryHIV(table1.getTotalMaleExtrapulmonaryHIV() + table1.getTotalFemaleExtrapulmonaryHIV());
    	
    	table1.setTotalMale(table1.getNewMale() + table1.getRelapseMale() + table1.getRetreatmentMale());
    	table1.setTotalFemale(table1.getNewFemale() + table1.getRelapseFemale() + table1.getRetreatmentFemale());
    	table1.setTotalAll(table1.getTotalMale() + table1.getTotalFemale());
    	
    	table1.setTotalMaleHIV(table1.getNewMaleHIV() + table1.getRelapseMaleHIV() + table1.getRetreatmentMaleHIV());
    	table1.setTotalFemaleHIV(table1.getNewFemaleHIV() + table1.getRelapseFemaleHIV() + table1.getRetreatmentFemaleHIV());
    	table1.setTotalAllHIV(table1.getTotalMaleHIV() + table1.getTotalFemaleHIV());
    	
    	
    	
    	//table1.setTotalAll(table1.getTotalMale() + getTotalFemale());
    	
    	//fin.add(table1);
		//}
    	
		// TO CHECK WHETHER REPORT IS CLOSED OR NOT
    	//Integer report_oblast = null; Integer report_quarter = null; Integer report_month = null;
		/*if(new PDFHelper().isInt(oblast)) { report_oblast = Integer.parseInt(oblast); }
		if(new PDFHelper().isInt(quarter)) { report_quarter = Integer.parseInt(quarter); }
		if(new PDFHelper().isInt(month)) { report_month = Integer.parseInt(month); }*/
		
    	boolean reportStatus;// = Context.getService(MdrtbService.class).readReportStatus(report_oblast, location.getId(), year, report_quarter, report_month, "TB 07");
		/*if(location!=null)
			 reportStatus = Context.getService(MdrtbService.class).readReportStatus(report_oblast, location.getId(), year, report_quarter, report_month, "TB-07","DOTSTB");
		else*/
			reportStatus = Context.getService(MdrtbService.class).readReportStatus(oblastId, districtId, facilityId, year, quarter, month, "TB-07","DOTSTB");
		
		System.out.println(reportStatus);
		
		String oName = null;
		String dName = null;
		String fName = null;
		
		if(oblastId!=null) {
			Oblast o = Context.getService(MdrtbService.class).getOblast(oblastId);
			if(o!=null) {
				oName = o.getName();
			}
		}
		
		if(districtId!=null) {
			District d = Context.getService(MdrtbService.class).getDistrict(districtId);
			if(d!=null) {
				dName = d.getName();
			}
		}
		
		if(facilityId!=null) {
			Facility f = Context.getService(MdrtbService.class).getFacility(facilityId);
			if(f!=null) {
				fName = f.getName();
			}
		}
		
    	model.addAttribute("table1", table1);
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
		
		model.addAttribute("oName", oName);
		model.addAttribute("dName", dName);
		model.addAttribute("fName", fName);
		
    	model.addAttribute("reportDate", rdateSDF.format(new Date()));
    	model.addAttribute("reportStatus", reportStatus);
        return "/module/mdrtb/reporting/tb07Results";
        //_" + Context.getLocale().toString().substring(0, 2);
    }
    
    
  
    
}
