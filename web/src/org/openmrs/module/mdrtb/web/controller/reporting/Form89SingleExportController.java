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
import org.openmrs.module.mdrtb.MdrtbConstants;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.form.CultureForm;
import org.openmrs.module.mdrtb.form.Form89;
import org.openmrs.module.mdrtb.form.HAIN2Form;
import org.openmrs.module.mdrtb.form.HAINForm;
import org.openmrs.module.mdrtb.form.SmearForm;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.form.XpertForm;
import org.openmrs.module.mdrtb.reporting.Form89Data;
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
import org.springframework.web.servlet.ModelAndView;

@Controller

public class Form89SingleExportController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
        
    
    @RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/form89Single")
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
				districts = Context.getService(MdrtbService.class)
						.getDistricts(Integer.parseInt(oblast));
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
				districts = Context.getService(MdrtbService.class)
						.getDistricts(Integer.parseInt(oblast));
				facilities = Context.getService(MdrtbService.class)
						.getFacilities(Integer.parseInt(district));
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
    	 return new ModelAndView("/module/mdrtb/reporting/form89Single", model);	
    	
    }
    
  
    
    
    @SuppressWarnings({ "deprecation", "unchecked" })
	@RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/form89Single")
    public static String doForm89(
    		@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException {
    	
    	
    	System.out.println("---POST-----");
    	System.out.println("PARAMS:" + oblastId + " " + districtId + " " + facilityId + " " + year + " " + quarter + " " + month);
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
    	
    	
    	
    	//ArrayList<TB03Form> tb03List = Context.getService(MdrtbService.class).getTB03FormsFilled(location, oblastId+"", year, quarter, month);
    	
    	//ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    	
    	ArrayList<Location> locList = null;
    	if(oblastId!=null) {
    		if(oblastId.intValue()==186) {
    			locList = Context.getService(MdrtbService.class).getLocationListForDushanbe(oblastId,districtId,facilityId);
    		}
    		else {
    			locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    		}
    	}
		
    	ArrayList<Form89> f89List = Context.getService(MdrtbService.class).getForm89FormsFilled(locList, year, quarter, month);
    	
    	
    	ArrayList<Form89Data> patientSet  = new ArrayList<Form89Data>();
    	SimpleDateFormat sdf = new SimpleDateFormat();
    	
    	/*ArrayList<Person> patientList = new ArrayList<Person>();
    	ArrayList<Concept> conceptQuestionList = new ArrayList<Concept>();
    	ArrayList<Concept> conceptAnswerList = new ArrayList<Concept>();*/
  /*  	Integer regimenConceptId = null;
    	Integer codId = null;*/
    	//List<Obs> obsList = null;
    	
    	/*Concept reg1New = Context.getService(MdrtbService.class).getConcept(TbConcepts.REGIMEN_1_NEW);
    	Concept reg1Rtx = Context.getService(MdrtbService.class).getConcept(TbConcepts.REGIMEN_1_RETREATMENT);*/
    	
    	sdf.applyPattern("dd.MM.yyyy");
    	
    	SimpleDateFormat rdateSDF = new SimpleDateFormat();
    	rdateSDF.applyPattern("dd.MM.yyyy HH:mm:ss");
    	for (Form89 f89 : f89List) {
    		
    		
    		Form89Data f89Data = new Form89Data();
    		/*f89Data.setReg1New(Boolean.FALSE);
    		f89Data.setReg1Rtx(Boolean.FALSE);*/
    	    
    		Patient patient = f89.getPatient();
    	    if(patient==null || patient.isVoided()) {
    	    	continue;
    	    	
    	    }
    	    
    	    
    	    f89Data.setPatient(patient);
    	    f89Data.setForm89(f89);
    	    //PATIENT IDENTIFIER
    	   /* tb03Data.setIdentifier(patient.getActiveIdentifiers().get(0).toString());*/
    	   
    	    String identifier = f89.getRegistrationNumber();
    	    f89Data.setIdentifier(identifier);
    	    
    	    //DATE OF TB03 REGISTRATION
    	    
    	    Date encDate = null;
    	    
    	    if(f89.getTB03()!=null) {
    	    	encDate = f89.getTB03().getEncounterDatetime();
    	   
    	    	f89Data.setTb03RegistrationDate(sdf.format(encDate));
    	    }
    	    	
    	   
    	   
    	    
    	    //FORMATTED DATE OF BIRTH
    	    if(patient.getBirthdate()!=null)
    	    	f89Data.setDateOfBirth(sdf.format(patient.getBirthdate()));
    	    
    	    //AGE AT TB03 Registration
    	    Integer age = f89.getAgeAtRegistration();//Context.getService(MdrtbService.class).getConcept(TbConcepts.AGE_AT_DOTS_REGISTRATION);
    	    if(age!=null)
    	    	f89Data.setAgeAtTB03Registration(age);
    	    
    	   
    	    
    	  //SITE OF DISEASE (P/EP)
    	    Concept q = f89.getAnatomicalSite();
    	  
    	    if(q!=null)
    	    	f89Data.setSiteOfDisease(q.getName().getShortName());
    	    
    	  
    	    if(f89.getForm89Date()!=null) {
    	    	f89Data.setForm89Date(sdf.format(f89.getForm89Date()));
    	    }
    	    
    	    if(f89.getDateFirstSeekingHelp()!=null) {
    	    	f89Data.setDateFirstSeekingHelp(sdf.format(f89.getDateFirstSeekingHelp()));
    	    }
    	    
    	    if(f89.getCmacDate()!=null) {
    	    	f89Data.setCmacDate(sdf.format(f89.getCmacDate()));
    	    }
    	    
    	    if(f89.getDateOfReturn()!=null) {
    	    	f89Data.setDateOfReturn(sdf.format(f89.getDateOfReturn()));
    	    }
    	    
    	    if(f89.getDateOfDecaySurvey()!=null) {
    	    	f89Data.setDateOfDecaySurvey(sdf.format(f89.getDateOfDecaySurvey()));
    	    }
    	    
    	    //DIAGNOSTIC RESULTS
    	    
    	    //DIAGNOSTIC SMEAR
    	    
    	   /* Smear diagnosticSmear = TB03Util.getDiagnosticSmear(tf);
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
    	    }*/
    	    List<SmearForm> smears = f89.getSmears();
    	    SmearForm diagnosticSmear = null;
    	    
    	    if(smears!=null && smears.size()!=0) {
    	    	diagnosticSmear = smears.get(0);
    	    }
    	    
    	   
    	    if(diagnosticSmear!=null) {
    	    	System.out.println("SMEAR ID:" + diagnosticSmear.getId());
    	    		if(diagnosticSmear.getSmearResult()!=null) {
    	    			f89Data.setDiagnosticSmearResult(diagnosticSmear.getSmearResult().getName().getShortName());
    	    			//System.out.println("RESULT:" + diagnosticSmear.getResult());
    	    		}
    	    		if(diagnosticSmear.getEncounterDatetime()!=null) {
    	    			f89Data.setDiagnosticSmearDate(sdf.format(diagnosticSmear.getEncounterDatetime()));
    	    			//System.out.println("DATE:" + diagnosticSmear.getResultDate());
    	    		}
    	    		
    	    		f89Data.setDiagnosticSmearTestNumber(diagnosticSmear.getSpecimenId());
    	    		//System.out.println("SPEC ID:" + diagnosticSmear.getRealSpecimenId());
    	    		
    	    		Location loc = diagnosticSmear.getLocation();
    	    		if(loc!=null) {
    	    			if(loc.getRegion()!=null && loc.getRegion().length()!=0) {
    	    				f89Data.setDiagnosticSmearLab(loc.getRegion());
    	    			}
    	    			
    	    			else if(loc.getCountyDistrict()!=null && loc.getCountyDistrict().length()!=0) {
    	    				f89Data.setDiagnosticSmearLab(loc.getCountyDistrict());
    	    			}
    	    		}
    	    		
    	    		//System.out.println(tb03Data.getDiagnosticSmearResult() + "," + tb03Data.getDiagnosticSmearDate() + "," + tb03Data.getDiagnosticSmearTestNumber());
    	    }
    	    
    	    else {
    	    	System.out.println("NULL DIAG SMEAR");
    	    }
    	    
    	    List<XpertForm> xperts = f89.getXperts();
    	    
    	    XpertForm firstXpert = null;
    	    		
    	    if(xperts!=null && xperts.size()!=0)
    	    	firstXpert = xperts.get(0);
    	    if(firstXpert!=null) {
    	    	if(firstXpert.getMtbResult()!=null)
    	    		f89Data.setXpertMTBResult(firstXpert.getMtbResult().getName().getShortName());
    	    	if(firstXpert.getRifResult()!=null)
    	    		f89Data.setXpertRIFResult(firstXpert.getRifResult().getName().getShortName());
    	    	if(firstXpert.getEncounterDatetime()!=null)
    	    		f89Data.setXpertTestDate(sdf.format(firstXpert.getEncounterDatetime()));
    	    	
    	    	f89Data.setXpertTestNumber(firstXpert.getSpecimenId());
    	    	
    	    	Location loc = firstXpert.getLocation();
	    		if(loc!=null) {
	    			if(loc.getRegion()!=null && loc.getRegion().length()!=0) {
	    				f89Data.setXpertLab(loc.getRegion());
	    			}
	    			
	    			else if(loc.getCountyDistrict()!=null && loc.getCountyDistrict().length()!=0) {
	    				f89Data.setXpertLab(loc.getCountyDistrict());
	    			}
	    		}
    	    }
    	    
    	    else {
    	    	System.out.println("NULL DIAG XPERT");
    	    }
    	    
    	    List<HAINForm> hains = f89.getHains();
    	    HAINForm firstHAIN = null;
    	    if(hains!=null && hains.size()!=0)
    	    	firstHAIN = hains.get(0);
    	    
    	    if(firstHAIN!=null) {
    	    	if(firstHAIN.getMtbResult()!=null)
    	    		f89Data.setHainMTBResult(firstHAIN.getMtbResult().getName().getShortName());
    	    	if(firstHAIN.getRifResult()!=null)
    	    		f89Data.setHainRIFResult(firstHAIN.getRifResult().getName().getShortName());
    	    	if(firstHAIN.getInhResult()!=null)
    	    		f89Data.setHainINHResult(firstHAIN.getInhResult().getName().getShortName());
    	    	if(firstHAIN.getEncounterDatetime()!=null)
    	    		f89Data.setHainTestDate(sdf.format(firstHAIN.getEncounterDatetime()));
    	    	
    	    	f89Data.setHainTestNumber(firstHAIN.getSpecimenId());
    	    	
    	    	Location loc = firstHAIN.getLocation();
	    		if(loc!=null) {
	    			if(loc.getRegion()!=null && loc.getRegion().length()!=0) {
	    				f89Data.setHainLab(loc.getRegion());
	    			}
	    			
	    			else if(loc.getCountyDistrict()!=null && loc.getCountyDistrict().length()!=0) {
	    				f89Data.setHainLab(loc.getCountyDistrict());
	    			}
	    		}
    	    }
    	    
    	    else {
    	    	System.out.println("NULL DIAG HAIN");
    	    }
    	    
    	    List<HAIN2Form> hain2s = f89.getHain2s();
    	    
    	    HAIN2Form firstHAIN2 = null;
    	    
    	    if(hain2s != null && hain2s.size()!=0)
    	    	firstHAIN2 = hain2s.get(0);
    	    
    	    if(firstHAIN2!=null) {
    	    	if(firstHAIN2.getMtbResult()!=null)
    	    		f89Data.setHain2MTBResult(firstHAIN2.getMtbResult().getName().getShortName());
    	    	if(firstHAIN2.getFqResult()!=null)
    	    		f89Data.setHain2FqResult(firstHAIN2.getFqResult().getName().getShortName());
    	    	if(firstHAIN2.getInjResult()!=null)
    	    		f89Data.setHain2InjResult(firstHAIN2.getInjResult().getName().getShortName());
    	    	if(firstHAIN2.getEncounterDatetime()!=null)
    	    		f89Data.setHain2TestDate(sdf.format(firstHAIN2.getEncounterDatetime()));
    	    	
    	    	f89Data.setHain2TestNumber(firstHAIN2.getSpecimenId());
    	    	Location loc = firstHAIN2.getLocation();
	    		if(loc!=null) {
	    			if(loc.getRegion()!=null && loc.getRegion().length()!=0) {
	    				f89Data.setHain2Lab(loc.getRegion());
	    			}
	    			
	    			else if(loc.getCountyDistrict()!=null && loc.getCountyDistrict().length()!=0) {
	    				f89Data.setHain2Lab(loc.getCountyDistrict());
	    			}
	    		}
    	    }
    	    
    	    else {
    	    	System.out.println("NULL DIAG HAIN");
    	    }
    	    
    	    q = null;
    	    patientSet.add(f89Data);
    	   
    	}
    	
    	Collections.sort(patientSet);
    	
    	Integer num = patientSet.size();
    	model.addAttribute("num", num);
    	model.addAttribute("patientSet", patientSet);
    	model.addAttribute("locale", Context.getLocale().toString());

    	
    	/*// TO CHECK WHETHER REPORT IS CLOSED OR NOT
    	Integer report_oblast = null, report_district = null, report_facility = null;
    	String report_quarter = null, report_month = null;*/
		/*if(oblastId!=null)
			report_oblast = oblastId;
		if(districtId!=null)
			report_district = districtId;
		if(facilityId!=null)
			report_facility = facilityId;*/
		
		/*if(new PDFHelper().isInt(quarter)) { report_quarter = Integer.parseInt(quarter); }
		if(new PDFHelper().isInt(month)) { report_month = Integer.parseInt(month); }*/
		
    	boolean reportStatus;// = Context.getService(MdrtbService.class).readReportStatus(report_oblast, location.getId(), year, report_quarter, report_month, "TB 03");
    	
    	/*if(location!=null)
			 reportStatus = Context.getService(MdrtbService.class).readReportStatus(report_oblast, location.getId(), year, report_quarter, report_month, "TB-03", "DOTSTB");
		else
			reportStatus = Context.getService(MdrtbService.class).readReportStatus(report_oblast, null, year, report_quarter, report_month, "TB-03", "DOTSTB");*/
    	
    	reportStatus = Context.getService(MdrtbService.class).readReportStatus(oblastId, districtId, facilityId, year, quarter, month, "TB-03", "DOTSTB");
		
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
    	model.addAttribute("reportDate", rdateSDF.format(new Date()));
    	model.addAttribute("reportStatus", reportStatus);
    	return "/module/mdrtb/reporting/form89SingleResults";
    }
    
    
  
    
}
