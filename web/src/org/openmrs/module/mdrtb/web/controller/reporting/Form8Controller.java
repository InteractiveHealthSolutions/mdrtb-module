package org.openmrs.module.mdrtb.web.controller.reporting;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.form.Form89;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.reporting.Form8Table1Data;
import org.openmrs.module.mdrtb.reporting.Form8Table2Data;
import org.openmrs.module.mdrtb.reporting.Form8Table3Data;
import org.openmrs.module.mdrtb.reporting.Form8Table4Data;
import org.openmrs.module.mdrtb.reporting.Form8Table5aData;
import org.openmrs.module.mdrtb.reporting.TB08Data;
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

public class Form8Controller {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
        
    
    @RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/form8")
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
         	
    		else {oblasts = Context.getService(MdrtbService.class).getOblasts();
    			districts= Context.getService(MdrtbService.class).getDistricts(Integer.parseInt(oblast));
    			model.addAttribute("oblastSelected", oblast);
    			model.addAttribute("oblasts", oblasts);
    			model.addAttribute("districts", districts);
    		}
         }
         else
         {
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
    	 return new ModelAndView("/module/mdrtb/reporting/form8", model);	
    }

    @RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/form8")
    public static String doTB08(
    		@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException {
    	
    	System.out.println("---POST-----");

    	ArrayList<Location> locList = null;
    	if(oblastId.intValue()==186) {
			locList = Context.getService(MdrtbService.class).getLocationListForDushanbe(oblastId,districtId,facilityId);
		}
    	else {
    		locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    	}
		ArrayList<TB03Form> tb03List = Context.getService(MdrtbService.class).getTB03FormsFilled(locList, year, quarter, month);
		System.out.println("list size:" + tb03List.size());

		
		SimpleDateFormat sdf = new SimpleDateFormat();
    	sdf.applyPattern("dd.MM.yyyy");
    	SimpleDateFormat rdateSDF = new SimpleDateFormat();
    	rdateSDF.applyPattern("dd.MM.yyyy HH:mm:ss");

    	Integer ageAtRegistration = 0;
    	
    	Concept pulmonaryConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.PULMONARY_TB);
    	Concept extrapulmonaryConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.EXTRA_PULMONARY_TB);
    	Concept positiveConcept =  Context.getService(MdrtbService.class).getConcept(TbConcepts.POSITIVE);
    	Concept negativeConcept =  Context.getService(MdrtbService.class).getConcept(TbConcepts.NEGATIVE);
    	//Concept contact = Context.getService(MdrtbService.class).getConcept(TbConcepts.CONTACT);
    	//Concept migrant = Context.getService(MdrtbService.class).getConcept(TbConcepts.MIGRANT);
    	//Concept phcWorker = Context.getService(MdrtbService.class).getConcept(TbConcepts.PHC_WORKER);
    	//Concept privateSectorFacility = Context.getService(MdrtbService.class).getConcept(TbConcepts.TB_SERVICES_WORKER);
    	
    	String gender = null;
    	
    	Boolean pulmonary = null;
    	Boolean bacPositive = null;
    	Boolean cured = null;
    	Boolean txCompleted = null;
    	Boolean diedTB = null;
    	Boolean diedNotTB = null;
    	Boolean failed = null;
    	Boolean defaulted = null;
    	Boolean transferOut = null;
    	Boolean canceled = null;
    	Boolean sld = null;
    	
    	Boolean male = null;
    	Boolean female = null;
    	
    	Boolean hospitalised = null;
    	
    	
    	Form8Table1Data table1 = new Form8Table1Data();
    	Form8Table2Data table2 = new Form8Table2Data();
    	Form8Table3Data table3 = new Form8Table3Data();
    	Form8Table4Data table4 = new Form8Table4Data();
    	Form8Table5aData table5a = new Form8Table5aData();
    	TB08Data table6 = new TB08Data();
    	
    	
    	Concept regGroup = null;
    	Form89 f89 = null;
    	
    	Boolean rural = null;
    	Concept ruralConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.VILLAGE);
    	int ruralId = ruralConcept.getConceptId().intValue();
    	Concept locationType = null;
    	
    	/*Boolean bacEx = null;
    	Boolean phc = null;
    	*/
    	Boolean miliary = null;
    	Boolean focal = null;
    	Boolean infiltrative = null;
    	Boolean disseminated = null;
    	Boolean cavernous = null;
    	Boolean fibroCav = null;
    	Boolean cirrhotic = null;
    	Boolean tbComplex = null;
    	Boolean tuberculoma = null;
    	Boolean bronchi = null;
    	
    	Boolean plevritis = null;
    	Boolean itLymph = null;
    	Boolean cns = null;
    	Boolean osteoArticular = null;
    	Boolean urogenital = null;
    	Boolean peripheralLymphNodes = null;
    	Boolean abdominal = null;
    	Boolean eye = null;
    	Boolean liver = null;
    	Boolean skin = null;
    	
    	Boolean resistant = null;
    	Boolean hivPositive = null;
    	
    	Boolean phcFacility = null;
    	Boolean tbFacility = null;
    	Boolean privateSectorFacility = null;
    	Boolean otherFacility = null;
    	Boolean phcWorker = null;
    	Boolean tbServicesWorker = null;
    	Boolean contact = null;
    	Boolean migrant = null;
    	
    	Boolean decay = null;
    	
    	
    	//PULMONARY
    	Concept fibroCavConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.FIBROUS_CAVERNOUS);
    	int fibroCavId = fibroCavConcept.getConceptId().intValue();
    	Concept miliaryConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.MILIARY);
    	int miliaryId = miliaryConcept.getConceptId().intValue();
    	Concept focalConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.FOCAL);
    	int focalId = focalConcept.getConceptId().intValue();
    	Concept infiltrativeConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.INFILTRATIVE);
    	int infiltrativeId = infiltrativeConcept.getConceptId().intValue();
    	Concept disseminatedConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.DISSEMINATED);
    	int disseminatedId = disseminatedConcept.getConceptId().intValue();
    	Concept cavernousConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.CAVERNOUS);
    	int cavernousId = cavernousConcept.getConceptId().intValue();
    	Concept cirrhoticConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.CIRRHOTIC);
    	int cirrhoticId = cirrhoticConcept.getConceptId().intValue();
    	Concept primaryComplexConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.TB_PRIMARY_COMPLEX);
    	int primaryComplexId = primaryComplexConcept.getConceptId().intValue();
    	Concept tuberculomaConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.TUBERCULOMA);
    	int tuberculomaId = tuberculomaConcept.getConceptId().intValue();
    	Concept bronchiConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.BRONCHUS);
    	int bronchiId = bronchiConcept.getConceptId().intValue();
    	
    	//EP TB
    	Concept plevConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.PLEVRITIS);
    	int plevId = plevConcept.getConceptId().intValue();
    	Concept itLymphConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.OF_LYMPH_NODES);
    	int itLymphId = itLymphConcept.getConceptId().intValue();
    	Concept cnsConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.OF_CNS);
    	int cnsId = cnsConcept.getConceptId().intValue();
    	Concept osteoArticularConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.OSTEOARTICULAR);
    	int osteoArticularId = osteoArticularConcept.getConceptId().intValue();
    	Concept urogenitalConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.GENITOURINARY);
    	int urogenitalId = urogenitalConcept.getConceptId().intValue();
    	Concept peripheralLymphNodesConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.OF_PERIPHERAL_LYMPH_NODES);
    	int peripheralLymphNodesId = peripheralLymphNodesConcept.getConceptId().intValue();
    	Concept abdominalConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.ABDOMINAL);
    	int abdominalId = abdominalConcept.getConceptId().intValue();
    	Concept eyeConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.OCULAR);
    	int eyeId = eyeConcept.getConceptId().intValue();
    	Concept skinConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.TUBERCULODERMA);
    	int skinId = skinConcept.getConceptId().intValue();
    	Concept liverConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.OF_LIVER);
    	int liverId = liverConcept.getConceptId().intValue();
    	
    	//Other
    	Concept phcFacilityConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.PHC_FACILITY);
    	int phcFacilityId = phcFacilityConcept.getConceptId().intValue();
    	Concept tbFacilityConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.TB_FACILITY);
    	int tbFacilityId = tbFacilityConcept.getConceptId().intValue();
    	Concept privateSectorFacilityConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.PRIVATE_SECTOR_FACILITY);
    	int privateSectorFacilityId = privateSectorFacilityConcept.getConceptId().intValue();
    	Concept otherFacilityConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.OTHER_MEDICAL_FACILITY);
    	int otherFacilityId = otherFacilityConcept.getConceptId().intValue();
    	
    
    	Concept phcWorkerConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.PHC_WORKER);
    	int phcWorkerId = phcWorkerConcept.getConceptId().intValue();
    	Concept tbWorkerConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.TB_SERVICES_WORKER);
    	int tbWorkerId = tbWorkerConcept.getConceptId().intValue();
    	Concept contactConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.CONTACT_INVESTIGATION);
    	int contactId = contactConcept.getConceptId().intValue();
    	Concept migrantConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.MIGRANT);
    	int migrantId = migrantConcept.getConceptId().intValue();
    	Concept yesConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.YES);
    	int yesId = yesConcept.getConceptId().intValue();
    	Concept hospConcept = Context.getService(MdrtbService.class).getConcept(TbConcepts.HOSPITALIZED);
    	int hospId = hospConcept.getConceptId().intValue();
    	
    	Concept pulSite = null;
    	Concept epulSite = null;
    	
    	
    	
    	
    	
    	int age = 0;
    	int resId = 0;
    	
    	
    	int noResId = Context.getService(MdrtbService.class).getConcept(TbConcepts.NONE).getConceptId().intValue();
    	int unknownId = Context.getService(MdrtbService.class).getConcept(TbConcepts.UNKNOWN).getConceptId().intValue();
    	int monoId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MONO).getConceptId().intValue();
    	
    	Boolean pregnant = null;
    	Concept detectedAt = null;
    	Concept circOf = null;
    	Concept prof = null;
    	
    	//table1
    	for(TB03Form tf: tb03List) {
    		ageAtRegistration = -1;
    		pulmonary = null;
    		bacPositive = null;
    		gender = null;
    		male = null;
    		female = null;
    		regGroup = null;
    		rural = null;
    		locationType = null;
    		 plevritis = null;
        	 itLymph = null;
        	 
        	 fibroCav = null;
        	 cns = null;
        	 osteoArticular = null;
        	 urogenital = null;
        	 peripheralLymphNodes = null;
        	 abdominal = null;
        	 eye = null;
        	 miliary = null;
        	 resistant = null;
        	 hivPositive = null;
        	 pulSite = null;
        	 epulSite = null;
        	 
        	 phcFacility = null;
        	 phcWorker = null;
        	 tbFacility = null;
        	 tbServicesWorker = null;
        	 migrant = null;
        	 contact = null;
        	 otherFacility = null;
        	 privateSectorFacility = null;
        	 focal = null;
        	 infiltrative = null;
        	 disseminated = null;
        	 cavernous = null;
        	 cirrhotic = null;
        	 tbComplex = null;
        	 tuberculoma = null;
        	 bronchi = null;
        	 skin = null;
        	 liver = null;
        	 
        	 phcFacility = null;
         	 tbFacility = null;
         	 privateSectorFacility = null;
         	 otherFacility = null;
         	 
         	 phcWorker = null;
         	 tbServicesWorker = null;
         	 contact = null;
         	 migrant = null;
        	 pregnant = null;
        	 
        	 detectedAt = null;
        	 circOf = null;
        	 prof = null;
        	 
        	 decay = null;
        	 
        	 hospitalised = null;
    		
    		if(tf.getPatient()==null || tf.getPatient().isVoided()) {
    			System.out.println("patient void - skipping ENC: " + tf.getEncounter().getEncounterId());
    			
    			continue;
    		}
    		
    		regGroup = tf.getRegistrationGroup();
    		
    		if(regGroup==null || regGroup.getConceptId().intValue()!=Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.new.conceptId"))) {
    			
    			if(regGroup!=null && 
    					((regGroup.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterRelapse1.conceptId"))) || 
    					(regGroup.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterRelapse2.conceptId")))
    					)) {
    					
    					table2.setRelapseCount(table2.getRelapseCount() + 1);
    					table3.setGroup2To1(table3.getGroup2To1() + 1);
    					table3.setRelapse(table3.getRelapse() + 1);
    			}
    			
    			else if(regGroup!=null && 
    					((regGroup.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterFailure1.conceptId"))) || 
    					(regGroup.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterFailure2.conceptId")))
    					)) {
    				
    					table2.setFailCount(table2.getFailCount() + 1);
    					table3.setGroup2To1(table3.getGroup2To1() + 1);
    			}
    			
    			else if(regGroup!=null && 
    					((regGroup.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterDefault1.conceptId"))) || 
    					(regGroup.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterDefault2.conceptId")))
    					)) {
    				
    					table2.setLtfuCount(table2.getLtfuCount() + 1);
    					table3.setGroup2To1(table3.getGroup2To1() + 1);
    			}
    			
    			else {
					table2.setOtherCount(table2.getOtherCount() + 1);
					table3.setGroup2To1(table3.getGroup2To1() + 1);
    			}
    			
    			System.out.println("patient not new - skipping " + tf.getPatient().getPatientId());
    			continue;
    		
    			
    		}
    		
    		ArrayList<Form89> fList = Context.getService(MdrtbService.class).getForm89FormsFilledForPatientProgram(tf.getPatient(), null, tf.getPatProgId(), null, null, null);
    		
    		if(fList==null || fList.size()!=1) {
    			System.out.println("no f89 - skipping " + tf.getPatient().getPatientId());
    			continue;
    		}
    		
    		f89 = fList.get(0);
    		
    		locationType = f89.getLocationType();
    		if(locationType!=null && locationType.getConceptId().intValue()==ruralId) {
    			rural = Boolean.TRUE;
    		}
    		
    		else if(locationType!=null) {
    			rural  = Boolean.FALSE;
    		}
    		
    		else {
    			rural = null;
    		}
    		
    		gender = tf.getPatient().getGender();
    		if(gender!=null && gender.equals("M")) {
    			male = Boolean.TRUE;
    		}
    		
    		else if(gender!=null && gender.equals("F")) {
    			female = Boolean.TRUE;
    		}
    		
    		else {
    			male = null;
    			female = null;
    		}
    		
    		 ageAtRegistration = tf.getAgeAtTB03Registration();
    		 age = ageAtRegistration.intValue();
    		  
     	    //get disease site
     	    Concept q = tf.getAnatomicalSite();
     	    
     	   if(q!=null) {
     		   if(q.getConceptId().intValue()==pulmonaryConcept.getConceptId().intValue()) {
     			   pulmonary = Boolean.TRUE;
     			   System.out.println("PULMONARY");
     			   
     			   pulSite = f89.getPulSite();
     			   if(pulSite!=null && pulSite.getConceptId().intValue() == fibroCavId) {
     				   fibroCav = Boolean.TRUE;
     				   table2.setFibrousTotal(table2.getFibrousTotal() + 1);
     			   }
     			   
     			  if(pulSite!=null && pulSite.getConceptId().intValue() == miliaryId) {
    				   miliary = Boolean.TRUE;
    				   table2.setMiliaryTotal(table2.getMiliaryTotal() + 1);
    			   }
     			  
     			 if(pulSite!=null && pulSite.getConceptId().intValue() == focalId) {
     				 focal = Boolean.TRUE;
     				 table2.setFocalTotal(table2.getFocalTotal() + 1);
     			 }
     			 
     			 if(pulSite!=null && pulSite.getConceptId().intValue() == infiltrativeId) {
    				   infiltrative = Boolean.TRUE;
    				   table2.setInfiltrativeTotal(table2.getInfiltrativeTotal() + 1);
       			 }
     			 
     			 if(pulSite!=null && pulSite.getConceptId().intValue() == disseminatedId) {
    				   disseminated = Boolean.TRUE;
    				   table2.setDisseminatedTotal(table2.getDisseminatedTotal() + 1);
       			 }
     			 
     			 if(pulSite!=null && pulSite.getConceptId().intValue() == cavernousId) {
    				   cavernous = Boolean.TRUE;
    				   table2.setCavernousTotal(table2.getCavernousTotal() + 1);
       			 }
     			 
     			 if(pulSite!=null && pulSite.getConceptId().intValue() == cirrhoticId) {
    				   cirrhotic = Boolean.TRUE;
    				   table2.setCirrhoticTotal(table2.getCirrhoticTotal() + 1);
       			 }
     			 
     			 if(pulSite!=null && pulSite.getConceptId().intValue() == primaryComplexId) {
    				   tbComplex = Boolean.TRUE;
    				   table2.setTbComplexTotal(table2.getTbComplexTotal() + 1);
       			 }
     			 
     			 if(pulSite!=null && pulSite.getConceptId().intValue() == tuberculomaId) {
    				   tuberculoma = Boolean.TRUE;
    				   table2.setTuberculomaTotal(table2.getTuberculomaTotal() + 1);
       			 }
     			 
     			 if(pulSite!=null && pulSite.getConceptId().intValue() == bronchiId) {
    				   bronchi = Boolean.TRUE;
    				   table2.setBronchiTotal(table2.getBronchiTotal() + 1);
       			 }
     		   }
   	    	
     		   else if (q.getConceptId().intValue()==extrapulmonaryConcept.getConceptId().intValue()) {
     			   pulmonary = Boolean.FALSE;
     			   System.out.println("EXTRAPULMONARY");
     			   epulSite = f89.getEpLocation();
     			   
     			   if(epulSite!=null) {
     				   if(epulSite.getConceptId().intValue() == cnsId) {
     					   cns = Boolean.TRUE;
     					   table2.setNervousSystemTotal(table2.getNervousSystemTotal() + 1);
     				   }
     				   
     				   else if(epulSite.getConceptId().intValue() == osteoArticularId) {
     					   osteoArticular = Boolean.TRUE;
     					   table2.setOsteoarticularTotal(table2.getOsteoarticularTotal() + 1);
     				   }
     				   
     				   else if(epulSite.getConceptId().intValue() == urogenitalId) {
    					   urogenital = Boolean.TRUE;
    					   table2.setUrogenitalTotal(table2.getUrogenitalTotal() + 1);
    				   }
     				   
     				  else if(epulSite.getConceptId().intValue() == peripheralLymphNodesId) {
     					  peripheralLymphNodes = Boolean.TRUE;
     					  table2.setPeripheralLymphNodesTotal(table2.getPeripheralLymphNodesTotal() + 1);
     				  }
     				   
     				  else if(epulSite.getConceptId().intValue() == abdominalId) {
     					 abdominal = Boolean.TRUE;
     					 table2.setAbdominalTotal(table2.getAbdominalTotal() + 1);
     				  }
     				   
     				  else if(epulSite.getConceptId().intValue() == eyeId) {
     					 eye = Boolean.TRUE;
     					 table2.setEyeTotal(table2.getEyeTotal() + 1);
     				  }
     				   
     				 else if(epulSite.getConceptId().intValue() == plevId) {
     					 plevritis = Boolean.TRUE;
     					 table2.setPleurisyTotal(table2.getPleurisyTotal() + 1);
     				  }
     				   
     				 else if(epulSite.getConceptId().intValue() == itLymphId) {
     					 itLymph = Boolean.TRUE;
     					 table2.setHilarLymphNodesTotal(table2.getHilarLymphNodesTotal() + 1);
     				  }
     				   
     				 else if(epulSite.getConceptId().intValue() == liverId) {
     					 liver = Boolean.TRUE;
     					 table2.setLiverTotal(table2.getLiverTotal() + 1);
     				  }
     				   
     				 else if(epulSite.getConceptId().intValue() == skinId) {
     					 skin = Boolean.TRUE;
     					 table2.setSkinTotal(table2.getSkinTotal() + 1);
     				  }

     			   }

     		   }
     		   
     		   else {
     			   
     			   System.out.println("PULMONARY NULL: " + tf.getPatient().getPatientId());
     			   pulmonary = null;
   	    		}
     	   	}
     	   
     	  /* if(pulmonary!=null && !pulmonary) {
     		   Concept epLoc = f89.getEpLocation();
     	   }*/
     	   
     	   bacPositive = MdrtbUtil.isDiagnosticBacPositive(tf);
     	   
     	  q = tf.getHivStatus();//Context.getService(MdrtbService.class).getConcept(TbConcepts.RESULT_OF_HIV_TEST);
  	    
     	  if(q!=null) {
     		  if(q.getConceptId().intValue()==positiveConcept.getConceptId().intValue()) {
     			  hivPositive = Boolean.TRUE;
     		  }  	    	
     	  
     	  
     		  else if(q.getConceptId().intValue()==negativeConcept.getConceptId().intValue()) {
     			  hivPositive = Boolean.FALSE;
     		  }
  	    	
     		  else {
     			  hivPositive = Boolean.FALSE;
     		  }
     	  }
  	    
     	  else {
     		  hivPositive = Boolean.FALSE;
     	  }
     	  
     	  q = tf.getResistanceType();
     	  
     	  if(q!=null) {
     		  resId = q.getConceptId().intValue();
     		  
     		  if(resId!=noResId && resId!=unknownId && resId!=monoId) {
     			  resistant = Boolean.TRUE;
     		  }
     		  
     		  else if(resId==noResId) {
     			  resistant = Boolean.FALSE;
     		  }
     		  
     		  else {
     			  resistant = null;
     		  }
     		  
     	  }
     	  
     	  else {
     		  resistant = null;
     	  }
     	  
     	  detectedAt = f89.getPlaceOfDetection();
     	  if(detectedAt!=null) {
     		  int detId = detectedAt.getConceptId().intValue();
     		  
     		  table2.setDetectedByRoutineCheckups(table2.getDetectedByRoutineCheckups() + 1);
     		  
     		  if(age>=0 && age < 15) {
     			  table2.setRoutine014(table2.getRoutine014() + 1);
     		  }
     		  
     		  else  if(age>=15 && age < 18) {
     			  table2.setRoutine1517(table2.getRoutine1517() + 1);
     		  }
     		  
     		 else  if(age>=18 && age < 20) {
    			  table2.setRoutine1819(table2.getRoutine1819() + 1);
    		  }
     		  
     		  if(detId==phcFacilityId) {
     			  table2.setDetectedBySpecialistsTotal(table2.getDetectedBySpecialistsTotal() + 1);
     			  phcFacility = Boolean.TRUE;
     			  
     		  }
     		  
     		  else if(detId==tbFacilityId) {
     			  table2.setDetectedBySpecialistsTotal(table2.getDetectedByTBDoctors() + 1);
     			  tbFacility = Boolean.TRUE;

     		  }
     		  
     		  else if(detId==privateSectorFacilityId) {
     			  table2.setDetectedBySpecialistsTotal(table2.getDetectedBySpecialistsTotal() + 1);
     			  privateSectorFacility = Boolean.TRUE;
     		  }
     		  
     		  else if(detId==otherFacilityId) {
     			 table2.setDetectedByOtherSpecialists(table2.getDetectedByOtherSpecialists() + 1);
     			  otherFacility = Boolean.TRUE;
     		  }

     	  }
     	  
     	  circOf = f89.getCircumstancesOfDetection();
     	  if(circOf != null) {
     		  int circId = circOf.getConceptId().intValue();
     		  
     		  if(circId == contactId) {
     			  contact = Boolean.TRUE;
     			  table2.setContact(table2.getContact() + 1);
     		  }
     		  
     		  else if(circId==migrantId) {
     			  migrant = Boolean.TRUE;
     			  table2.setMigrants(table2.getMigrants() + 1);
     		  }
     	  }
     	  
     	  prof = f89.getProfession();
     	  
     	  if(prof!=null) {
     		  int profId = prof.getConceptId().intValue();
     		  
     		  if(profId==phcWorkerId) {
     			  phcWorker = Boolean.TRUE;
     			  table2.setPhcWorkers(table2.getPhcWorkers() + 1);
     		  }
     		  
     		  else if(profId==tbWorkerId) {
     			  tbServicesWorker = Boolean.TRUE;
     			  table2.setTbServiceWorkers(table2.getTbServiceWorkers() + 1);
     		  }
     	  }
     	  
     	  if(f89.getPregnant()!=null) {
     		  if(f89.getPregnant().getConceptId().intValue()==yesId) {
     			  pregnant = Boolean.TRUE;
     		  }
     	  }
     	  
     	  //decay
     	  if(f89.getPresenceOfDecay()!=null) {
     		  if(f89.getPresenceOfDecay().getConceptId().intValue()==yesId) {
     			  
     			  table2.setDecayPhaseTotal(table2.getDecayPhaseTotal() + 1 );
     			  
     			  if(phcFacility!=null && phcFacility) {
     				 table2.setDecayPhasePHCTotal(table2.getDecayPhasePHCTotal() + 1 );
     			  }
     			  
     			  if(age>=0 && age < 15) {
     				 table2.setDecayPhase014(table2.getDecayPhase014() + 1 );
     			  }
     			  
     			  else if(age>=15 && age < 18) {
      				 table2.setDecayPhase1517(table2.getDecayPhase1517() + 1 );
      			  }
     			  
     			 else if(age>=18 && age < 20) {
      				 table2.setDecayPhase1819(table2.getDecayPhase1819() + 1 );
      			  }
     			  
     			  
     		  }
     	  }
     	  
     	  if((tf.getTreatmentSiteIP()!=null && tf.getTreatmentSiteIP().getConceptId().intValue()==hospId) ||  (tf.getTreatmentSiteCP()!=null && tf.getTreatmentSiteCP().getConceptId().intValue()==hospId)) {
     		  hospitalised = Boolean.TRUE;
     		 
     		  table4.setHospitalised(table4.getHospitalised() + 1);
     		  table4.setInHospital(table4.getInHospital() + 1);
     		  
     		  if(age>=0 && age < 15) {
     			  table4.setHospitalised014(table4.getHospitalised014() + 1);
        		  table4.setInHospital(table4.getInHospital() + 1);
     		  }
     		  
     		  else if(age>=15 && age < 18) {
    			  table4.setHospitalised1517(table4.getHospitalised1517() + 1);
    			  table4.setInHospital(table4.getInHospital() + 1);
    		  }
     		  
     		 else if(age>=18 && age < 19) {
     			 table4.setHospitalised1819(table4.getHospitalised1819() + 1);
     			 table4.setInHospital(table4.getInHospital() + 1);
     		 }
     		  
     		  if(regGroup!=null && regGroup.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.new.conceptId"))) {
     			  
     			 if(age>=0 && age < 15) {
	     			  table4.setFirstNew014(table4.getFirstNew014() + 1);
	        		 
	     		  }
	     		  
	     		  else if(age>=15 && age < 18) {
	    			  table4.setFirstNew1517(table4.getFirstNew1517() + 1);
	    			  
	    		  }
	     		  
	     		 else if(age>=18 && age < 19) {
	     			 table4.setFirstNew1819(table4.getFirstNew1819() + 1);
	     			 
	     		 }
     			  
     			  if(bacPositive) {
     				 if(age>=0 && age < 15) {
     	     			  table4.setNewBac014(table4.getNewBac014() + 1);
     	        		 
     	     		  }
     	     		  
     	     		  else if(age>=15 && age < 18) {
     	    			  table4.setNewBac1517(table4.getNewBac1517() + 1);
     	    			  
     	    		  }
     	     		  
     	     		 else if(age>=18 && age < 19) {
     	     			 table4.setNewBac1819(table4.getNewBac1819() + 1);
     	     			 
     	     		 }
     			  }
     			  
     			  else {
     				 if(age>=0 && age < 15) {
    	     			  table4.setNewOther014(table4.getNewOther014() + 1);
    	        		 
    	     		  }
    	     		  
    	     		  else if(age>=15 && age < 18) {
    	    			  table4.setNewOther1517(table4.getNewOther1517() + 1);
    	    			  
    	    		  }
    	     		  
    	     		 else if(age>=18 && age < 19) {
    	     			 table4.setNewOther1819(table4.getNewOther1819() + 1);
    	     			 
    	     		 }
     			  }
     		  }
     		  
     		 
     		  
     	  }
     	 
     	  
     	  if(bacPositive!=null && bacPositive && pulmonary!=null && pulmonary) {
     		  
     		  if(regGroup!=null && regGroup.getConceptId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.new.conceptId"))) {
     			  table5a.setRespBacNew(table5a.getRespBacNew() + 1);
     			  
     			  if(rural != null && rural) {
     				 table5a.setRespBacNewVillager(table5a.getRespBacNewVillager() + 1);
     			  }
     		  }
     		  
     		  else if(regGroup!=null) {
     			  table5a.setRespBacOther(table5a.getRespBacOther() + 1);
    			  
    			  if(rural != null && rural) {
    				 table5a.setRespBacOtherVillager(table5a.getRespBacOtherVillager() + 1);
    			  }
     		  }
     	  }
     		 
     		 
     	 
     	  
     	 
     	  
     	  if(male!=null && male) {
     		   table1.setActiveTBTotalMale(table1.getActiveTBTotalMale() + 1);
     		   
     		   if(phcFacility!=null && phcFacility) {
     			   table2.setActivePHCTotal(table2.getActivePHCTotal() + 1);
     		   }
     		   
     		  if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
				   table1.setRespiratoryTBTotalMale(table1.getRespiratoryTBTotalMale() + 1); 
				   
				   if(phcFacility!=null && phcFacility) {
	     			   table2.setRespiratoryPHCTotal(table2.getRespiratoryPHCTotal() + 1);
	     		   }
			   }
			   
			   if(pulmonary!=null && pulmonary) {
				   table1.setPulmonaryTBTotalMale(table1.getPulmonaryTBTotalMale() + 1);
				   if(phcFacility!=null && phcFacility) {
	     			   table2.setPulmonaryPHCTotal(table2.getPulmonaryPHCTotal() + 1);
	     		   }
				        				   
				   if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
					   table1.setBacExTBTotalMale(table1.getBacExTBTotalMale() + 1);
					   
					   if(phcFacility!=null && phcFacility) {
		     			   table2.setBacExPHCTotal(table2.getBacExPHCTotal() + 1);
		     		   }
				   }
				   
				  if(miliary!=null && miliary) {
    				  table1.setMiliaryTBTotalMale(table1.getMiliaryTBTotalMale() + 1);
    				  
    				  if(phcFacility!=null && phcFacility) {
    	     			   table2.setMiliaryPHCTotal(table2.getMiliaryPHCTotal() + 1);
    	     		   }
    			   }
			   }
			   
			   if(fibroCav!=null && fibroCav) {
				   table1.setFibCavTBTotalMale(table1.getFibCavTBTotalMale() + 1);
			   }
			  
			   else if(cns!=null && cns) {
				  table1.setNervousSystemTBTotalMale(table1.getNervousSystemTBTotalMale() + 1);
				  
				  if(phcFacility!=null && phcFacility) {
	     			   table2.setNervousSystemPHCTotal(table2.getNervousSystemPHCTotal() + 1);
	     		   }
			   }
			  
			   else if(osteoArticular!=null && osteoArticular) {
				  table1.setOtherOrgansTBTotalMale(table1.getOtherOrgansTBTotalMale() + 1);
				  if(phcFacility!=null && phcFacility) {
	     			   table2.setOtherOrgansPHCTotal(table2.getOtherOrgansPHCTotal() + 1);
	     		   }
				  table1.setOsteoarticularTBTotalMale(table1.getOsteoarticularTBTotalMale() + 1);
			   }
			   
			   else if(urogenital!=null && urogenital) {
				  table1.setOtherOrgansTBTotalMale(table1.getOtherOrgansTBTotalMale() + 1);
				  if(phcFacility!=null && phcFacility) {
	     			   table2.setOtherOrgansPHCTotal(table2.getOtherOrgansPHCTotal() + 1);
	     		   }
				  table1.setUrogenitalTBTotalMale(table1.getUrogenitalTBTotalMale() + 1);
			   }
			   
			   else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
				  table1.setOtherOrgansTBTotalMale(table1.getOtherOrgansTBTotalMale() + 1);
				  if(phcFacility!=null && phcFacility) {
	     			   table2.setOtherOrgansPHCTotal(table2.getOtherOrgansPHCTotal() + 1);
	     		   }
				  table1.setLymphNodesTBTotalMale(table1.getLymphNodesTBTotalMale() + 1);
			   }
			   
			  else if(abdominal!=null && abdominal) {
				  table1.setOtherOrgansTBTotalMale(table1.getOtherOrgansTBTotalMale() + 1);
				  if(phcFacility!=null && phcFacility) {
	     			   table2.setOtherOrgansPHCTotal(table2.getOtherOrgansPHCTotal() + 1);
	     		   }
				  table1.setAbdominalTBTotalMale(table1.getAbdominalTBTotalMale() + 1);
			   }
			   
			  else if(eye!=null && eye) {
				  table1.setOtherOrgansTBTotalMale(table1.getOtherOrgansTBTotalMale() + 1);
				  if(phcFacility!=null && phcFacility) {
	     			   table2.setOtherOrgansPHCTotal(table2.getOtherOrgansPHCTotal() + 1);
	     		   }
				  table1.setEyeTBTotalMale(table1.getEyeTBTotalMale() + 1);
			   }
			   
			   if(resistant!=null && resistant) {
				  table1.setResistantTBTotalMale(table1.getResistantTBTotalMale() + 1);
			   }
			   
			  if(hivPositive!=null && hivPositive) {
				  table1.setTbhivTBTotalMale(table1.getTbhivTBTotalMale() + 1);
			   }
			  
			   if(rural!=null && rural) {
				   table1.setRuralTBTotalMale(table1.getRuralTBTotalMale() + 1); 
			   }

     		   if(age>=0 && age <5) {
     			   table1.setActiveTB04Male(table1.getActiveTB04Male() + 1);
     			   if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
     				   table1.setRespiratoryTB04Male(table1.getRespiratoryTB04Male() + 1); 
     			   }
     			   
     			   if(pulmonary!=null && pulmonary) {
     				   table1.setPulmonaryTB04Male(table1.getPulmonaryTB04Male() + 1);
     				        				   
     				   if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
     					   table1.setBacExTB04Male(table1.getBacExTB04Male() + 1);
     				   }
     				   
     				  if(miliary!=null && miliary) {
         				  table1.setMiliaryTB04Male(table1.getMiliaryTB04Male() + 1);
         			   }
     			   }
     			   
     			   if(fibroCav!=null && fibroCav) {
    				   table1.setFibCavTB04Male(table1.getFibCavTB04Male() + 1);
     			   }
     			  
     			   else if(cns!=null && cns) {
     				  table1.setNervousSystemTB04Male(table1.getNervousSystemTB04Male() + 1);
     			   }
     			  
     			   else if(osteoArticular!=null && osteoArticular) {
    				  table1.setOtherOrgansTB04Male(table1.getOtherOrgansTB04Male() + 1);
    				  table1.setOsteoarticularTB04Male(table1.getOsteoarticularTB04Male() + 1);
    			   }
     			   
     			   else if(urogenital!=null && urogenital) {
     				  table1.setOtherOrgansTB04Male(table1.getOtherOrgansTB04Male() + 1);
     				  table1.setUrogenitalTB04Male(table1.getUrogenitalTB04Male() + 1);
     			   }
     			   
     			   else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
     				  table1.setOtherOrgansTB04Male(table1.getOtherOrgansTB04Male() + 1);
     				  table1.setLymphNodesTB04Male(table1.getLymphNodesTB04Male() + 1);
     			   }
     			   
     			  else if(abdominal!=null && abdominal) {
     				  table1.setOtherOrgansTB04Male(table1.getOtherOrgansTB04Male() + 1);
     				  table1.setAbdominalTB04Male(table1.getAbdominalTB04Male() + 1);
     			   }
     			   
     			  else if(eye!=null && eye) {
    				  table1.setOtherOrgansTB04Male(table1.getOtherOrgansTB04Male() + 1);
    				  table1.setEyeTB04Male(table1.getEyeTB04Male() + 1);
    			   }
     			   
     			   if(resistant!=null && resistant) {
     				  table1.setResistantTB04Male(table1.getResistantTB04Male() + 1);
     			   }
     			   
     			  if(hivPositive!=null && hivPositive) {
     				  table1.setTbhivTB04Male(table1.getTbhivTB04Male() + 1);
     			   }
    			  
     			   if(rural!=null && rural) {
     				   table1.setRuralTB04Male(table1.getRuralTB04Male() + 1); 
     			   }
     		   }
     		   
     		   else if(age>=5 && age <15) {
     			   table1.setActiveTB0514Male(table1.getActiveTB0514Male() + 1);
     			   
    			   if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
    				   table1.setRespiratoryTB0514Male(table1.getRespiratoryTB0514Male() + 1); 
    			   }
    			   
    			   if(pulmonary!=null && pulmonary) {
    				   table1.setPulmonaryTB0514Male(table1.getPulmonaryTB0514Male() + 1);
    				        				   
    				   if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
    					   table1.setBacExTB0514Male(table1.getBacExTB0514Male() + 1);
    				   }
    				   
    				  if(miliary!=null && miliary) {
        				  table1.setMiliaryTB0514Male(table1.getMiliaryTB0514Male() + 1);
        			   }
    			   }
    			   
    			   if(fibroCav!=null && fibroCav) {
    				   table1.setFibCavTB0514Male(table1.getFibCavTB0514Male() + 1);
    			   }
    			  
    			   else if(cns!=null && cns) {
    				   table1.setNervousSystemTB0514Male(table1.getNervousSystemTB0514Male() + 1);
    			   }
    			  
    			   else if(osteoArticular!=null && osteoArticular) {
    				   table1.setOtherOrgansTB0514Male(table1.getOtherOrgansTB0514Male() + 1);
    				   table1.setOsteoarticularTB0514Male(table1.getOsteoarticularTB0514Male() + 1);
    			   }
    			   
    			   else if(urogenital!=null && urogenital) {
    				   table1.setOtherOrgansTB0514Male(table1.getOtherOrgansTB0514Male() + 1);
    				   table1.setUrogenitalTB0514Male(table1.getUrogenitalTB0514Male() + 1);
    			   }
    			   
    			   else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
    				   table1.setOtherOrgansTB0514Male(table1.getOtherOrgansTB0514Male() + 1);
    				   table1.setLymphNodesTB0514Male(table1.getLymphNodesTB0514Male() + 1);
    			   }
    			   
    			   else if(abdominal!=null && abdominal) {
    				   table1.setOtherOrgansTB0514Male(table1.getOtherOrgansTB0514Male() + 1);
    				   table1.setAbdominalTB0514Male(table1.getAbdominalTB0514Male() + 1);
    			   }
    			   
    			   else if(eye!=null && eye) {
    				   table1.setOtherOrgansTB0514Male(table1.getOtherOrgansTB0514Male() + 1);
    				   table1.setEyeTB0514Male(table1.getEyeTB0514Male() + 1);
    			   }
    			   
    			   if(resistant!=null && resistant) {
    				  table1.setResistantTB0514Male(table1.getResistantTB0514Male() + 1);
    			   }
    			   
    			   if(hivPositive!=null && hivPositive) {
    				   table1.setTbhivTB0514Male(table1.getTbhivTB0514Male() + 1);
    			   }
   			  
    			   if(rural!=null && rural) {
    				   table1.setRuralTB0514Male(table1.getRuralTB0514Male() + 1); 
    			   }
     		   }
     		   
     		   else if(age>=15 && age <18) {
    			   table1.setActiveTB1517Male(table1.getActiveTB1517Male() + 1);
    			   
    			   if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
    				   table1.setRespiratoryTB1517Male(table1.getRespiratoryTB1517Male() + 1); 
    			   }
    			   
    			   if(pulmonary!=null && pulmonary) {
    				   table1.setPulmonaryTB1517Male(table1.getPulmonaryTB1517Male() + 1);
    				        				   
    				   if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
    					   table1.setBacExTB1517Male(table1.getBacExTB1517Male() + 1);
    				   }
    				   
    				  if(miliary!=null && miliary) {
        				  table1.setMiliaryTB1517Male(table1.getMiliaryTB1517Male() + 1);
        			   }
    			   }
    			   
    			   if(fibroCav!=null && fibroCav) {
    				   table1.setFibCavTB1517Male(table1.getFibCavTB1517Male() + 1);
    			   }
    			  
    			   else if(cns!=null && cns) {
    				   table1.setNervousSystemTB1517Male(table1.getNervousSystemTB1517Male() + 1);
    			   }
    			  
    			   else if(osteoArticular!=null && osteoArticular) {
    				   table1.setOtherOrgansTB1517Male(table1.getOtherOrgansTB1517Male() + 1);
    				   table1.setOsteoarticularTB1517Male(table1.getOsteoarticularTB1517Male() + 1);
    			   }
    			   
    			   else if(urogenital!=null && urogenital) {
    				   table1.setOtherOrgansTB1517Male(table1.getOtherOrgansTB1517Male() + 1);
    				   table1.setUrogenitalTB1517Male(table1.getUrogenitalTB1517Male() + 1);
    			   }
    			   
    			   else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
    				   table1.setOtherOrgansTB1517Male(table1.getOtherOrgansTB1517Male() + 1);
    				   table1.setLymphNodesTB1517Male(table1.getLymphNodesTB1517Male() + 1);
    			   }
    			   
    			   else if(abdominal!=null && abdominal) {
    				   table1.setOtherOrgansTB1517Male(table1.getOtherOrgansTB1517Male() + 1);
    				   table1.setAbdominalTB1517Male(table1.getAbdominalTB1517Male() + 1);
    			   }
    			   
    			   else if(eye!=null && eye) {
    				   table1.setOtherOrgansTB1517Male(table1.getOtherOrgansTB1517Male() + 1);
    				   table1.setEyeTB1517Male(table1.getEyeTB1517Male() + 1);
    			   }
    			   
    			   if(resistant!=null && resistant) {
    				  table1.setResistantTB1517Male(table1.getResistantTB1517Male() + 1);
    			   }
    			   
    			   if(hivPositive!=null && hivPositive) {
    				   table1.setTbhivTB1517Male(table1.getTbhivTB1517Male() + 1);
    			   }
   			  
    			   if(rural!=null && rural) {
    				   table1.setRuralTB1517Male(table1.getRuralTB1517Male() + 1); 
    			   }
    		   }
     		   
     		   else if(age>=18 && age <20) {
     			  table1.setActiveTB1819Male(table1.getActiveTB1819Male() + 1);
     			  
     			 if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
  				   table1.setRespiratoryTB1819Male(table1.getRespiratoryTB1819Male() + 1); 
     			 }
  			   
     			 if(pulmonary!=null && pulmonary) {
  				   table1.setPulmonaryTB1819Male(table1.getPulmonaryTB1819Male() + 1);
  				        				   
  				   if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
  					   table1.setBacExTB1819Male(table1.getBacExTB1819Male() + 1);
  				   }
  				   
  				  if(miliary!=null && miliary) {
      				  table1.setMiliaryTB1819Male(table1.getMiliaryTB1819Male() + 1);
      			   }
     			 }
  			   
     			 if(fibroCav!=null && fibroCav) {
     				 table1.setFibCavTB1819Male(table1.getFibCavTB1819Male() + 1);
     			 }
  			  
     			 else if(cns!=null && cns) {
     				 table1.setNervousSystemTB1819Male(table1.getNervousSystemTB1819Male() + 1);
     			 }
  			  
     			 else if(osteoArticular!=null && osteoArticular) {
     				 table1.setOtherOrgansTB1819Male(table1.getOtherOrgansTB1819Male() + 1);
     				 table1.setOsteoarticularTB1819Male(table1.getOsteoarticularTB1819Male() + 1);
     			 }
  			   
     			 else if(urogenital!=null && urogenital) {
     				 table1.setOtherOrgansTB1819Male(table1.getOtherOrgansTB1819Male() + 1);
     				 table1.setUrogenitalTB1819Male(table1.getUrogenitalTB1819Male() + 1);
     			 }
  			   
     			 else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
     				 table1.setOtherOrgansTB1819Male(table1.getOtherOrgansTB1819Male() + 1);
     				 table1.setLymphNodesTB1819Male(table1.getLymphNodesTB1819Male() + 1);
     			 }
  			   
     			 else if(abdominal!=null && abdominal) {
     				 table1.setOtherOrgansTB1819Male(table1.getOtherOrgansTB1819Male() + 1);
     				 table1.setAbdominalTB1819Male(table1.getAbdominalTB1819Male() + 1);
     			 }
  			   
     			 else if(eye!=null && eye) {
     				 table1.setOtherOrgansTB1819Male(table1.getOtherOrgansTB1819Male() + 1);
     				 table1.setEyeTB1819Male(table1.getEyeTB1819Male() + 1);
     			 }
  			   
     			 if(resistant!=null && resistant) {
     				 table1.setResistantTB1819Male(table1.getResistantTB1819Male() + 1);
     			 }
  			   
     			 if(hivPositive!=null && hivPositive) {
     				 table1.setTbhivTB1819Male(table1.getTbhivTB1819Male() + 1);
     			 }
 			  
     			 if(rural!=null && rural) {
     				 table1.setRuralTB1819Male(table1.getRuralTB1819Male() + 1); 
     			 }
     		   }
     		   
     		   else if(age>=20 && age <25) {
    			  table1.setActiveTB2024Male(table1.getActiveTB2024Male() + 1);
    			  
    			  if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
   				   table1.setRespiratoryTB2024Male(table1.getRespiratoryTB2024Male() + 1); 
    			  }
   			   
    			  if(pulmonary!=null && pulmonary) {
    				  table1.setPulmonaryTB2024Male(table1.getPulmonaryTB2024Male() + 1);
   				        				   
    				  if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
   					   	table1.setBacExTB2024Male(table1.getBacExTB2024Male() + 1);
   				   }
   				   
   				 	 if(miliary!=null && miliary) {
       				  table1.setMiliaryTB2024Male(table1.getMiliaryTB2024Male() + 1);
       			   }
    			  }
   			   
   			   	if(fibroCav!=null && fibroCav) {
   				   table1.setFibCavTB2024Male(table1.getFibCavTB2024Male() + 1);
   			   	}
   			  
   			   	else if(cns!=null && cns) {
   				   table1.setNervousSystemTB2024Male(table1.getNervousSystemTB2024Male() + 1);
   			   	}
   			  
   			   	else if(osteoArticular!=null && osteoArticular) {
   				   table1.setOtherOrgansTB2024Male(table1.getOtherOrgansTB2024Male() + 1);
   				   table1.setOsteoarticularTB2024Male(table1.getOsteoarticularTB2024Male() + 1);
   			   	}
   			   
   			   	else if(urogenital!=null && urogenital) {
   				   table1.setOtherOrgansTB2024Male(table1.getOtherOrgansTB2024Male() + 1);
   				   table1.setUrogenitalTB2024Male(table1.getUrogenitalTB2024Male() + 1);
   			   	}
   			   
   			   	else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
   				   table1.setOtherOrgansTB2024Male(table1.getOtherOrgansTB2024Male() + 1);
   				   table1.setLymphNodesTB2024Male(table1.getLymphNodesTB2024Male() + 1);
   			   	}
   			   
   			   	else if(abdominal!=null && abdominal) {
   				   table1.setOtherOrgansTB2024Male(table1.getOtherOrgansTB2024Male() + 1);
   				   table1.setAbdominalTB2024Male(table1.getAbdominalTB2024Male() + 1);
   			   	}
   			   
   			   	else if(eye!=null && eye) {
   				   table1.setOtherOrgansTB2024Male(table1.getOtherOrgansTB2024Male() + 1);
   				   table1.setEyeTB2024Male(table1.getEyeTB2024Male() + 1);
   			   	}
   			   
   			   	if(resistant!=null && resistant) {
   				  table1.setResistantTB2024Male(table1.getResistantTB2024Male() + 1);
   			   	}
   			   
   			   	if(hivPositive!=null && hivPositive) {
   				   table1.setTbhivTB2024Male(table1.getTbhivTB2024Male() + 1);
   			   	}
  			  
   			   	if(rural!=null && rural) {
   				   table1.setRuralTB2024Male(table1.getRuralTB2024Male() + 1); 
   			   	}
     		   }
     		   
     		   else if(age>=25 && age <35) {
    			  table1.setActiveTB2534Male(table1.getActiveTB2534Male() + 1);
    			  
    			  if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
      				   table1.setRespiratoryTB2534Male(table1.getRespiratoryTB2534Male() + 1); 
       			  }
      			   
       			  if(pulmonary!=null && pulmonary) {
       				  table1.setPulmonaryTB2534Male(table1.getPulmonaryTB2534Male() + 1);
      				        				   
       				  if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
      					   	table1.setBacExTB2534Male(table1.getBacExTB2534Male() + 1);
      				   }
      				   
      				 	 if(miliary!=null && miliary) {
          				  table1.setMiliaryTB2534Male(table1.getMiliaryTB2534Male() + 1);
          			   }
       			  }
      			   
      			   	if(fibroCav!=null && fibroCav) {
      				   table1.setFibCavTB2534Male(table1.getFibCavTB2534Male() + 1);
      			   	}
      			  
      			   	else if(cns!=null && cns) {
      				   table1.setNervousSystemTB2534Male(table1.getNervousSystemTB2534Male() + 1);
      			   	}
      			  
      			   	else if(osteoArticular!=null && osteoArticular) {
      				   table1.setOtherOrgansTB2534Male(table1.getOtherOrgansTB2534Male() + 1);
      				   table1.setOsteoarticularTB2534Male(table1.getOsteoarticularTB2534Male() + 1);
      			   	}
      			   
      			   	else if(urogenital!=null && urogenital) {
      				   table1.setOtherOrgansTB2534Male(table1.getOtherOrgansTB2534Male() + 1);
      				   table1.setUrogenitalTB2534Male(table1.getUrogenitalTB2534Male() + 1);
      			   	}
      			   
      			   	else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
      				   table1.setOtherOrgansTB2534Male(table1.getOtherOrgansTB2534Male() + 1);
      				   table1.setLymphNodesTB2534Male(table1.getLymphNodesTB2534Male() + 1);
      			   	}
      			   
      			   	else if(abdominal!=null && abdominal) {
      				   table1.setOtherOrgansTB2534Male(table1.getOtherOrgansTB2534Male() + 1);
      				   table1.setAbdominalTB2534Male(table1.getAbdominalTB2534Male() + 1);
      			   	}
      			   
      			   	else if(eye!=null && eye) {
      				   table1.setOtherOrgansTB2534Male(table1.getOtherOrgansTB2534Male() + 1);
      				   table1.setEyeTB2534Male(table1.getEyeTB2534Male() + 1);
      			   	}
      			   
      			   	if(resistant!=null && resistant) {
      				  table1.setResistantTB2534Male(table1.getResistantTB2534Male() + 1);
      			   	}
      			   
      			   	if(hivPositive!=null && hivPositive) {
      				   table1.setTbhivTB2534Male(table1.getTbhivTB2534Male() + 1);
      			   	}
     			  
      			   	if(rural!=null && rural) {
      				   table1.setRuralTB2534Male(table1.getRuralTB2534Male() + 1); 
      			   	}
     		   }
     		   
     		   else if(age>=35 && age <45) {
     			   table1.setActiveTB3544Male(table1.getActiveTB3544Male() + 1);
     			   
     			  if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
      				   table1.setRespiratoryTB3544Male(table1.getRespiratoryTB3544Male() + 1); 
       			  }
      			   
       			  if(pulmonary!=null && pulmonary) {
       				  table1.setPulmonaryTB3544Male(table1.getPulmonaryTB3544Male() + 1);
      				        				   
       				  if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
      					   	table1.setBacExTB3544Male(table1.getBacExTB3544Male() + 1);
      				   }
      				   
      				 	 if(miliary!=null && miliary) {
          				  table1.setMiliaryTB3544Male(table1.getMiliaryTB3544Male() + 1);
          			   }
       			  }
      			   
      			   	if(fibroCav!=null && fibroCav) {
      				   table1.setFibCavTB3544Male(table1.getFibCavTB3544Male() + 1);
      			   	}
      			  
      			   	else if(cns!=null && cns) {
      				   table1.setNervousSystemTB3544Male(table1.getNervousSystemTB3544Male() + 1);
      			   	}
      			  
      			   	else if(osteoArticular!=null && osteoArticular) {
      				   table1.setOtherOrgansTB3544Male(table1.getOtherOrgansTB3544Male() + 1);
      				   table1.setOsteoarticularTB3544Male(table1.getOsteoarticularTB3544Male() + 1);
      			   	}
      			   
      			   	else if(urogenital!=null && urogenital) {
      				   table1.setOtherOrgansTB3544Male(table1.getOtherOrgansTB3544Male() + 1);
      				   table1.setUrogenitalTB3544Male(table1.getUrogenitalTB3544Male() + 1);
      			   	}
      			   
      			   	else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
      				   table1.setOtherOrgansTB3544Male(table1.getOtherOrgansTB3544Male() + 1);
      				   table1.setLymphNodesTB3544Male(table1.getLymphNodesTB3544Male() + 1);
      			   	}
      			   
      			   	else if(abdominal!=null && abdominal) {
      				   table1.setOtherOrgansTB3544Male(table1.getOtherOrgansTB3544Male() + 1);
      				   table1.setAbdominalTB3544Male(table1.getAbdominalTB3544Male() + 1);
      			   	}
      			   
      			   	else if(eye!=null && eye) {
      				   table1.setOtherOrgansTB3544Male(table1.getOtherOrgansTB3544Male() + 1);
      				   table1.setEyeTB3544Male(table1.getEyeTB3544Male() + 1);
      			   	}
      			   
      			   	if(resistant!=null && resistant) {
      				  table1.setResistantTB3544Male(table1.getResistantTB3544Male() + 1);
      			   	}
      			   
      			   	if(hivPositive!=null && hivPositive) {
      				   table1.setTbhivTB3544Male(table1.getTbhivTB3544Male() + 1);
      			   	}
     			  
      			   	if(rural!=null && rural) {
      				   table1.setRuralTB3544Male(table1.getRuralTB3544Male() + 1); 
      			   	}
     		   }
     		   
     		   else if(age>=45 && age <55) {
    			   table1.setActiveTB4554Male(table1.getActiveTB4554Male() + 1);
    			   
    			   if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
       				   table1.setRespiratoryTB4554Male(table1.getRespiratoryTB4554Male() + 1); 
        			  }
       			   
        			  if(pulmonary!=null && pulmonary) {
        				  table1.setPulmonaryTB4554Male(table1.getPulmonaryTB4554Male() + 1);
       				        				   
        				  if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
       					   	table1.setBacExTB4554Male(table1.getBacExTB4554Male() + 1);
       				   }
       				   
       				 	 if(miliary!=null && miliary) {
           				  table1.setMiliaryTB4554Male(table1.getMiliaryTB4554Male() + 1);
           			   }
        			  }
       			   
       			   	if(fibroCav!=null && fibroCav) {
       				   table1.setFibCavTB4554Male(table1.getFibCavTB4554Male() + 1);
       			   	}
       			  
       			   	else if(cns!=null && cns) {
       				   table1.setNervousSystemTB4554Male(table1.getNervousSystemTB4554Male() + 1);
       			   	}
       			  
       			   	else if(osteoArticular!=null && osteoArticular) {
       				   table1.setOtherOrgansTB4554Male(table1.getOtherOrgansTB4554Male() + 1);
       				   table1.setOsteoarticularTB4554Male(table1.getOsteoarticularTB4554Male() + 1);
       			   	}
       			   
       			   	else if(urogenital!=null && urogenital) {
       				   table1.setOtherOrgansTB4554Male(table1.getOtherOrgansTB4554Male() + 1);
       				   table1.setUrogenitalTB4554Male(table1.getUrogenitalTB4554Male() + 1);
       			   	}
       			   
       			   	else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
       				   table1.setOtherOrgansTB4554Male(table1.getOtherOrgansTB4554Male() + 1);
       				   table1.setLymphNodesTB4554Male(table1.getLymphNodesTB4554Male() + 1);
       			   	}
       			   
       			   	else if(abdominal!=null && abdominal) {
       				   table1.setOtherOrgansTB4554Male(table1.getOtherOrgansTB4554Male() + 1);
       				   table1.setAbdominalTB4554Male(table1.getAbdominalTB4554Male() + 1);
       			   	}
       			   
       			   	else if(eye!=null && eye) {
       				   table1.setOtherOrgansTB4554Male(table1.getOtherOrgansTB4554Male() + 1);
       				   table1.setEyeTB4554Male(table1.getEyeTB4554Male() + 1);
       			   	}
       			   
       			   	if(resistant!=null && resistant) {
       				  table1.setResistantTB4554Male(table1.getResistantTB4554Male() + 1);
       			   	}
       			   
       			   	if(hivPositive!=null && hivPositive) {
       				   table1.setTbhivTB4554Male(table1.getTbhivTB4554Male() + 1);
       			   	}
      			  
       			   	if(rural!=null && rural) {
       				   table1.setRuralTB4554Male(table1.getRuralTB4554Male() + 1); 
       			   	}
    		   }
     		   
     		   else if(age>=55 && age <65) {
     			   table1.setActiveTB5564Male(table1.getActiveTB5564Male() + 1);
     			   
     			  if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
      				   table1.setRespiratoryTB5564Male(table1.getRespiratoryTB5564Male() + 1); 
       			  }
      			   
       			  if(pulmonary!=null && pulmonary) {
       				  table1.setPulmonaryTB5564Male(table1.getPulmonaryTB5564Male() + 1);
      				        				   
       				  if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
      					   	table1.setBacExTB5564Male(table1.getBacExTB5564Male() + 1);
      				   }
      				   
      				 	 if(miliary!=null && miliary) {
          				  table1.setMiliaryTB5564Male(table1.getMiliaryTB5564Male() + 1);
          			   }
       			  }
      			   
      			   	if(fibroCav!=null && fibroCav) {
      				   table1.setFibCavTB5564Male(table1.getFibCavTB5564Male() + 1);
      			   	}
      			  
      			   	else if(cns!=null && cns) {
      				   table1.setNervousSystemTB5564Male(table1.getNervousSystemTB5564Male() + 1);
      			   	}
      			  
      			   	else if(osteoArticular!=null && osteoArticular) {
      				   table1.setOtherOrgansTB5564Male(table1.getOtherOrgansTB5564Male() + 1);
      				   table1.setOsteoarticularTB5564Male(table1.getOsteoarticularTB5564Male() + 1);
      			   	}
      			   
      			   	else if(urogenital!=null && urogenital) {
      				   table1.setOtherOrgansTB5564Male(table1.getOtherOrgansTB5564Male() + 1);
      				   table1.setUrogenitalTB5564Male(table1.getUrogenitalTB5564Male() + 1);
      			   	}
      			   
      			   	else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
      				   table1.setOtherOrgansTB5564Male(table1.getOtherOrgansTB5564Male() + 1);
      				   table1.setLymphNodesTB5564Male(table1.getLymphNodesTB5564Male() + 1);
      			   	}
      			   
      			   	else if(abdominal!=null && abdominal) {
      				   table1.setOtherOrgansTB5564Male(table1.getOtherOrgansTB5564Male() + 1);
      				   table1.setAbdominalTB5564Male(table1.getAbdominalTB5564Male() + 1);
      			   	}
      			   
      			   	else if(eye!=null && eye) {
      				   table1.setOtherOrgansTB5564Male(table1.getOtherOrgansTB5564Male() + 1);
      				   table1.setEyeTB5564Male(table1.getEyeTB5564Male() + 1);
      			   	}
      			   
      			   	if(resistant!=null && resistant) {
      				  table1.setResistantTB5564Male(table1.getResistantTB5564Male() + 1);
      			   	}
      			   
      			   	if(hivPositive!=null && hivPositive) {
      				   table1.setTbhivTB5564Male(table1.getTbhivTB5564Male() + 1);
      			   	}
     			  
      			   	if(rural!=null && rural) {
      				   table1.setRuralTB5564Male(table1.getRuralTB5564Male() + 1); 
      			   	}
     		   }
     		   
     		   else if(age>=65) {
    			   table1.setActiveTB65Male(table1.getActiveTB65Male() + 1);
    			   
    			   if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
       				   table1.setRespiratoryTB65Male(table1.getRespiratoryTB65Male() + 1); 
        			  }
       			   
        			  if(pulmonary!=null && pulmonary) {
        				  table1.setPulmonaryTB65Male(table1.getPulmonaryTB65Male() + 1);
       				        				   
        				  if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
       					   	table1.setBacExTB65Male(table1.getBacExTB65Male() + 1);
       				   }
       				   
       				 	 if(miliary!=null && miliary) {
           				  table1.setMiliaryTB65Male(table1.getMiliaryTB65Male() + 1);
           			   }
        			  }
       			   
       			   	if(fibroCav!=null && fibroCav) {
       				   table1.setFibCavTB65Male(table1.getFibCavTB65Male() + 1);
       			   	}
       			  
       			   	else if(cns!=null && cns) {
       				   table1.setNervousSystemTB65Male(table1.getNervousSystemTB65Male() + 1);
       			   	}
       			  
       			   	else if(osteoArticular!=null && osteoArticular) {
       				   table1.setOtherOrgansTB65Male(table1.getOtherOrgansTB65Male() + 1);
       				   table1.setOsteoarticularTB65Male(table1.getOsteoarticularTB65Male() + 1);
       			   	}
       			   
       			   	else if(urogenital!=null && urogenital) {
       				   table1.setOtherOrgansTB65Male(table1.getOtherOrgansTB65Male() + 1);
       				   table1.setUrogenitalTB65Male(table1.getUrogenitalTB65Male() + 1);
       			   	}
       			   
       			   	else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
       				   table1.setOtherOrgansTB65Male(table1.getOtherOrgansTB65Male() + 1);
       				   table1.setLymphNodesTB65Male(table1.getLymphNodesTB65Male() + 1);
       			   	}
       			   
       			   	else if(abdominal!=null && abdominal) {
       				   table1.setOtherOrgansTB65Male(table1.getOtherOrgansTB65Male() + 1);
       				   table1.setAbdominalTB65Male(table1.getAbdominalTB65Male() + 1);
       			   	}
       			   
       			   	else if(eye!=null && eye) {
       				   table1.setOtherOrgansTB65Male(table1.getOtherOrgansTB65Male() + 1);
       				   table1.setEyeTB65Male(table1.getEyeTB65Male() + 1);
       			   	}
       			   
       			   	if(resistant!=null && resistant) {
       				  table1.setResistantTB65Male(table1.getResistantTB65Male() + 1);
       			   	}
       			   
       			   	if(hivPositive!=null && hivPositive) {
       				   table1.setTbhivTB65Male(table1.getTbhivTB65Male() + 1);
       			   	}
      			  
       			   	if(rural!=null && rural) {
       				   table1.setRuralTB65Male(table1.getRuralTB65Male() + 1); 
       			   	}
    		   }
     		   
     		   if(rural!=null && rural) {
     			  table1.setActiveTBRuralMale(table1.getActiveTBRuralMale() + 1);
     			  
     			 if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
     				   table1.setRespiratoryTBRuralMale(table1.getRespiratoryTBRuralMale() + 1); 
      			  }
     			   
      			  if(pulmonary!=null && pulmonary) {
      				  table1.setPulmonaryTBRuralMale(table1.getPulmonaryTBRuralMale() + 1);
     				        				   
      				  if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
     					   	table1.setBacExTBRuralMale(table1.getBacExTBRuralMale() + 1);
     				   }
     				   
     				 	 if(miliary!=null && miliary) {
         				  table1.setMiliaryTBRuralMale(table1.getMiliaryTBRuralMale() + 1);
         			   }
      			  }
     			   
     			   	if(fibroCav!=null && fibroCav) {
     				   table1.setFibCavTBRuralMale(table1.getFibCavTBRuralMale() + 1);
     			   	}
     			  
     			   	else if(cns!=null && cns) {
     				   table1.setNervousSystemTBRuralMale(table1.getNervousSystemTBRuralMale() + 1);
     			   	}
     			  
     			   	else if(osteoArticular!=null && osteoArticular) {
     				   table1.setOtherOrgansTBRuralMale(table1.getOtherOrgansTBRuralMale() + 1);
     				   table1.setOsteoarticularTBRuralMale(table1.getOsteoarticularTBRuralMale() + 1);
     			   	}
     			   
     			   	else if(urogenital!=null && urogenital) {
     				   table1.setOtherOrgansTBRuralMale(table1.getOtherOrgansTBRuralMale() + 1);
     				   table1.setUrogenitalTBRuralMale(table1.getUrogenitalTBRuralMale() + 1);
     			   	}
     			   
     			   	else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
     				   table1.setOtherOrgansTBRuralMale(table1.getOtherOrgansTBRuralMale() + 1);
     				   table1.setLymphNodesTBRuralMale(table1.getLymphNodesTBRuralMale() + 1);
     			   	}
     			   
     			   	else if(abdominal!=null && abdominal) {
     				   table1.setOtherOrgansTBRuralMale(table1.getOtherOrgansTBRuralMale() + 1);
     				   table1.setAbdominalTBRuralMale(table1.getAbdominalTBRuralMale() + 1);
     			   	}
     			   
     			   	else if(eye!=null && eye) {
     				   table1.setOtherOrgansTBRuralMale(table1.getOtherOrgansTBRuralMale() + 1);
     				   table1.setEyeTBRuralMale(table1.getEyeTBRuralMale() + 1);
     			   	}
     			   
     			   	if(resistant!=null && resistant) {
     				  table1.setResistantTBRuralMale(table1.getResistantTBRuralMale() + 1);
     			   	}
     			   
     			   	if(hivPositive!=null && hivPositive) {
     				   table1.setTbhivTBRuralMale(table1.getTbhivTBRuralMale() + 1);
     			   	}
    			  
     			   
     		   }
     		   
     		   
     	   }
     	   
     	   
     	   else if(female!=null && female) {
     		   
     		   if(age >=15 && age <=49) {
     			   table2.setChildbearing(table2.getChildbearing() + 1);
     			   
     			   if(pregnant!=null && pregnant) {
     				   table2.setPregnant(table2.getPregnant() + 1);
     			   }
     			   
     		   }
     		   
     		  table1.setActiveTBTotalFemale(table1.getActiveTBTotalFemale() + 1);
    		   
    		   if(phcFacility!=null && phcFacility) {
    			   table2.setActivePHCTotal(table2.getActivePHCTotal() + 1);
    		   }
    		   
    		  if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
				   table1.setRespiratoryTBTotalFemale(table1.getRespiratoryTBTotalFemale() + 1); 
				   
				   if(phcFacility!=null && phcFacility) {
	     			   table2.setRespiratoryPHCTotal(table2.getRespiratoryPHCTotal() + 1);
	     		   }
			   }
			   
			   if(pulmonary!=null && pulmonary) {
				   table1.setPulmonaryTBTotalFemale(table1.getPulmonaryTBTotalFemale() + 1);
				   if(phcFacility!=null && phcFacility) {
	     			   table2.setPulmonaryPHCTotal(table2.getPulmonaryPHCTotal() + 1);
	     		   }
				        				   
				   if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
					   table1.setBacExTBTotalFemale(table1.getBacExTBTotalFemale() + 1);
					   
					   if(phcFacility!=null && phcFacility) {
		     			   table2.setBacExPHCTotal(table2.getBacExPHCTotal() + 1);
		     		   }
				   }
				   
				  if(miliary!=null && miliary) {
   				  table1.setMiliaryTBTotalFemale(table1.getMiliaryTBTotalFemale() + 1);
   				  
   				  if(phcFacility!=null && phcFacility) {
   	     			   table2.setMiliaryPHCTotal(table2.getMiliaryPHCTotal() + 1);
   	     		   }
   			   }
			   }
			   
			   if(fibroCav!=null && fibroCav) {
				   table1.setFibCavTBTotalFemale(table1.getFibCavTBTotalFemale() + 1);
			   }
			  
			   else if(cns!=null && cns) {
				  table1.setNervousSystemTBTotalFemale(table1.getNervousSystemTBTotalFemale() + 1);
				  
				  if(phcFacility!=null && phcFacility) {
	     			   table2.setNervousSystemPHCTotal(table2.getNervousSystemPHCTotal() + 1);
	     		   }
			   }
			  
			   else if(osteoArticular!=null && osteoArticular) {
				  table1.setOtherOrgansTBTotalFemale(table1.getOtherOrgansTBTotalFemale() + 1);
				  if(phcFacility!=null && phcFacility) {
	     			   table2.setOtherOrgansPHCTotal(table2.getOtherOrgansPHCTotal() + 1);
	     		   }
				  table1.setOsteoarticularTBTotalFemale(table1.getOsteoarticularTBTotalFemale() + 1);
			   }
			   
			   else if(urogenital!=null && urogenital) {
				  table1.setOtherOrgansTBTotalFemale(table1.getOtherOrgansTBTotalFemale() + 1);
				  if(phcFacility!=null && phcFacility) {
	     			   table2.setOtherOrgansPHCTotal(table2.getOtherOrgansPHCTotal() + 1);
	     		   }
				  table1.setUrogenitalTBTotalFemale(table1.getUrogenitalTBTotalFemale() + 1);
			   }
			   
			   else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
				  table1.setOtherOrgansTBTotalFemale(table1.getOtherOrgansTBTotalFemale() + 1);
				  if(phcFacility!=null && phcFacility) {
	     			   table2.setOtherOrgansPHCTotal(table2.getOtherOrgansPHCTotal() + 1);
	     		   }
				  table1.setLymphNodesTBTotalFemale(table1.getLymphNodesTBTotalFemale() + 1);
			   }
			   
			  else if(abdominal!=null && abdominal) {
				  table1.setOtherOrgansTBTotalFemale(table1.getOtherOrgansTBTotalFemale() + 1);
				  if(phcFacility!=null && phcFacility) {
	     			   table2.setOtherOrgansPHCTotal(table2.getOtherOrgansPHCTotal() + 1);
	     		   }
				  table1.setAbdominalTBTotalFemale(table1.getAbdominalTBTotalFemale() + 1);
			   }
			   
			  else if(eye!=null && eye) {
				  table1.setOtherOrgansTBTotalFemale(table1.getOtherOrgansTBTotalFemale() + 1);
				  if(phcFacility!=null && phcFacility) {
	     			   table2.setOtherOrgansPHCTotal(table2.getOtherOrgansPHCTotal() + 1);
	     		   }
				  table1.setEyeTBTotalFemale(table1.getEyeTBTotalFemale() + 1);
			   }
			   
			   if(resistant!=null && resistant) {
				  table1.setResistantTBTotalFemale(table1.getResistantTBTotalFemale() + 1);
			   }
			   
			  if(hivPositive!=null && hivPositive) {
				  table1.setTbhivTBTotalFemale(table1.getTbhivTBTotalFemale() + 1);
			   }
			  
			   if(rural!=null && rural) {
				   table1.setRuralTBTotalFemale(table1.getRuralTBTotalFemale() + 1); 
			   }

    		   if(age>=0 && age <5) {
    			   table1.setActiveTB04Female(table1.getActiveTB04Female() + 1);
    			   if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
    				   table1.setRespiratoryTB04Female(table1.getRespiratoryTB04Female() + 1); 
    			   }
    			   
    			   if(pulmonary!=null && pulmonary) {
    				   table1.setPulmonaryTB04Female(table1.getPulmonaryTB04Female() + 1);
    				        				   
    				   if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
    					   table1.setBacExTB04Female(table1.getBacExTB04Female() + 1);
    				   }
    				   
    				  if(miliary!=null && miliary) {
        				  table1.setMiliaryTB04Female(table1.getMiliaryTB04Female() + 1);
        			   }
    			   }
    			   
    			   if(fibroCav!=null && fibroCav) {
   				   table1.setFibCavTB04Female(table1.getFibCavTB04Female() + 1);
    			   }
    			  
    			   else if(cns!=null && cns) {
    				  table1.setNervousSystemTB04Female(table1.getNervousSystemTB04Female() + 1);
    			   }
    			  
    			   else if(osteoArticular!=null && osteoArticular) {
   				  table1.setOtherOrgansTB04Female(table1.getOtherOrgansTB04Female() + 1);
   				  table1.setOsteoarticularTB04Female(table1.getOsteoarticularTB04Female() + 1);
   			   }
    			   
    			   else if(urogenital!=null && urogenital) {
    				  table1.setOtherOrgansTB04Female(table1.getOtherOrgansTB04Female() + 1);
    				  table1.setUrogenitalTB04Female(table1.getUrogenitalTB04Female() + 1);
    			   }
    			   
    			   else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
    				  table1.setOtherOrgansTB04Female(table1.getOtherOrgansTB04Female() + 1);
    				  table1.setLymphNodesTB04Female(table1.getLymphNodesTB04Female() + 1);
    			   }
    			   
    			  else if(abdominal!=null && abdominal) {
    				  table1.setOtherOrgansTB04Female(table1.getOtherOrgansTB04Female() + 1);
    				  table1.setAbdominalTB04Female(table1.getAbdominalTB04Female() + 1);
    			   }
    			   
    			  else if(eye!=null && eye) {
   				  table1.setOtherOrgansTB04Female(table1.getOtherOrgansTB04Female() + 1);
   				  table1.setEyeTB04Female(table1.getEyeTB04Female() + 1);
   			   }
    			   
    			   if(resistant!=null && resistant) {
    				  table1.setResistantTB04Female(table1.getResistantTB04Female() + 1);
    			   }
    			   
    			  if(hivPositive!=null && hivPositive) {
    				  table1.setTbhivTB04Female(table1.getTbhivTB04Female() + 1);
    			   }
   			  
    			   if(rural!=null && rural) {
    				   table1.setRuralTB04Female(table1.getRuralTB04Female() + 1); 
    			   }
    		   }
    		   
    		   else if(age>=5 && age <15) {
    			   table1.setActiveTB0514Female(table1.getActiveTB0514Female() + 1);
    			   
   			   if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
   				   table1.setRespiratoryTB0514Female(table1.getRespiratoryTB0514Female() + 1); 
   			   }
   			   
   			   if(pulmonary!=null && pulmonary) {
   				   table1.setPulmonaryTB0514Female(table1.getPulmonaryTB0514Female() + 1);
   				        				   
   				   if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
   					   table1.setBacExTB0514Female(table1.getBacExTB0514Female() + 1);
   				   }
   				   
   				  if(miliary!=null && miliary) {
       				  table1.setMiliaryTB0514Female(table1.getMiliaryTB0514Female() + 1);
       			   }
   			   }
   			   
   			   if(fibroCav!=null && fibroCav) {
   				   table1.setFibCavTB0514Female(table1.getFibCavTB0514Female() + 1);
   			   }
   			  
   			   else if(cns!=null && cns) {
   				   table1.setNervousSystemTB0514Female(table1.getNervousSystemTB0514Female() + 1);
   			   }
   			  
   			   else if(osteoArticular!=null && osteoArticular) {
   				   table1.setOtherOrgansTB0514Female(table1.getOtherOrgansTB0514Female() + 1);
   				   table1.setOsteoarticularTB0514Female(table1.getOsteoarticularTB0514Female() + 1);
   			   }
   			   
   			   else if(urogenital!=null && urogenital) {
   				   table1.setOtherOrgansTB0514Female(table1.getOtherOrgansTB0514Female() + 1);
   				   table1.setUrogenitalTB0514Female(table1.getUrogenitalTB0514Female() + 1);
   			   }
   			   
   			   else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
   				   table1.setOtherOrgansTB0514Female(table1.getOtherOrgansTB0514Female() + 1);
   				   table1.setLymphNodesTB0514Female(table1.getLymphNodesTB0514Female() + 1);
   			   }
   			   
   			   else if(abdominal!=null && abdominal) {
   				   table1.setOtherOrgansTB0514Female(table1.getOtherOrgansTB0514Female() + 1);
   				   table1.setAbdominalTB0514Female(table1.getAbdominalTB0514Female() + 1);
   			   }
   			   
   			   else if(eye!=null && eye) {
   				   table1.setOtherOrgansTB0514Female(table1.getOtherOrgansTB0514Female() + 1);
   				   table1.setEyeTB0514Female(table1.getEyeTB0514Female() + 1);
   			   }
   			   
   			   if(resistant!=null && resistant) {
   				  table1.setResistantTB0514Female(table1.getResistantTB0514Female() + 1);
   			   }
   			   
   			   if(hivPositive!=null && hivPositive) {
   				   table1.setTbhivTB0514Female(table1.getTbhivTB0514Female() + 1);
   			   }
  			  
   			   if(rural!=null && rural) {
   				   table1.setRuralTB0514Female(table1.getRuralTB0514Female() + 1); 
   			   }
    		   }
    		   
    		   else if(age>=15 && age <18) {
   			   table1.setActiveTB1517Female(table1.getActiveTB1517Female() + 1);
   			   
   			   if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
   				   table1.setRespiratoryTB1517Female(table1.getRespiratoryTB1517Female() + 1); 
   			   }
   			   
   			   if(pulmonary!=null && pulmonary) {
   				   table1.setPulmonaryTB1517Female(table1.getPulmonaryTB1517Female() + 1);
   				        				   
   				   if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
   					   table1.setBacExTB1517Female(table1.getBacExTB1517Female() + 1);
   				   }
   				   
   				  if(miliary!=null && miliary) {
       				  table1.setMiliaryTB1517Female(table1.getMiliaryTB1517Female() + 1);
       			   }
   			   }
   			   
   			   if(fibroCav!=null && fibroCav) {
   				   table1.setFibCavTB1517Female(table1.getFibCavTB1517Female() + 1);
   			   }
   			  
   			   else if(cns!=null && cns) {
   				   table1.setNervousSystemTB1517Female(table1.getNervousSystemTB1517Female() + 1);
   			   }
   			  
   			   else if(osteoArticular!=null && osteoArticular) {
   				   table1.setOtherOrgansTB1517Female(table1.getOtherOrgansTB1517Female() + 1);
   				   table1.setOsteoarticularTB1517Female(table1.getOsteoarticularTB1517Female() + 1);
   			   }
   			   
   			   else if(urogenital!=null && urogenital) {
   				   table1.setOtherOrgansTB1517Female(table1.getOtherOrgansTB1517Female() + 1);
   				   table1.setUrogenitalTB1517Female(table1.getUrogenitalTB1517Female() + 1);
   			   }
   			   
   			   else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
   				   table1.setOtherOrgansTB1517Female(table1.getOtherOrgansTB1517Female() + 1);
   				   table1.setLymphNodesTB1517Female(table1.getLymphNodesTB1517Female() + 1);
   			   }
   			   
   			   else if(abdominal!=null && abdominal) {
   				   table1.setOtherOrgansTB1517Female(table1.getOtherOrgansTB1517Female() + 1);
   				   table1.setAbdominalTB1517Female(table1.getAbdominalTB1517Female() + 1);
   			   }
   			   
   			   else if(eye!=null && eye) {
   				   table1.setOtherOrgansTB1517Female(table1.getOtherOrgansTB1517Female() + 1);
   				   table1.setEyeTB1517Female(table1.getEyeTB1517Female() + 1);
   			   }
   			   
   			   if(resistant!=null && resistant) {
   				  table1.setResistantTB1517Female(table1.getResistantTB1517Female() + 1);
   			   }
   			   
   			   if(hivPositive!=null && hivPositive) {
   				   table1.setTbhivTB1517Female(table1.getTbhivTB1517Female() + 1);
   			   }
  			  
   			   if(rural!=null && rural) {
   				   table1.setRuralTB1517Female(table1.getRuralTB1517Female() + 1); 
   			   }
   		   }
    		   
    		   else if(age>=18 && age <20) {
    			  table1.setActiveTB1819Female(table1.getActiveTB1819Female() + 1);
    			  
    			 if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
 				   table1.setRespiratoryTB1819Female(table1.getRespiratoryTB1819Female() + 1); 
    			 }
 			   
    			 if(pulmonary!=null && pulmonary) {
 				   table1.setPulmonaryTB1819Female(table1.getPulmonaryTB1819Female() + 1);
 				        				   
 				   if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
 					   table1.setBacExTB1819Female(table1.getBacExTB1819Female() + 1);
 				   }
 				   
 				  if(miliary!=null && miliary) {
     				  table1.setMiliaryTB1819Female(table1.getMiliaryTB1819Female() + 1);
     			   }
    			 }
 			   
    			 if(fibroCav!=null && fibroCav) {
    				 table1.setFibCavTB1819Female(table1.getFibCavTB1819Female() + 1);
    			 }
 			  
    			 else if(cns!=null && cns) {
    				 table1.setNervousSystemTB1819Female(table1.getNervousSystemTB1819Female() + 1);
    			 }
 			  
    			 else if(osteoArticular!=null && osteoArticular) {
    				 table1.setOtherOrgansTB1819Female(table1.getOtherOrgansTB1819Female() + 1);
    				 table1.setOsteoarticularTB1819Female(table1.getOsteoarticularTB1819Female() + 1);
    			 }
 			   
    			 else if(urogenital!=null && urogenital) {
    				 table1.setOtherOrgansTB1819Female(table1.getOtherOrgansTB1819Female() + 1);
    				 table1.setUrogenitalTB1819Female(table1.getUrogenitalTB1819Female() + 1);
    			 }
 			   
    			 else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
    				 table1.setOtherOrgansTB1819Female(table1.getOtherOrgansTB1819Female() + 1);
    				 table1.setLymphNodesTB1819Female(table1.getLymphNodesTB1819Female() + 1);
    			 }
 			   
    			 else if(abdominal!=null && abdominal) {
    				 table1.setOtherOrgansTB1819Female(table1.getOtherOrgansTB1819Female() + 1);
    				 table1.setAbdominalTB1819Female(table1.getAbdominalTB1819Female() + 1);
    			 }
 			   
    			 else if(eye!=null && eye) {
    				 table1.setOtherOrgansTB1819Female(table1.getOtherOrgansTB1819Female() + 1);
    				 table1.setEyeTB1819Female(table1.getEyeTB1819Female() + 1);
    			 }
 			   
    			 if(resistant!=null && resistant) {
    				 table1.setResistantTB1819Female(table1.getResistantTB1819Female() + 1);
    			 }
 			   
    			 if(hivPositive!=null && hivPositive) {
    				 table1.setTbhivTB1819Female(table1.getTbhivTB1819Female() + 1);
    			 }
			  
    			 if(rural!=null && rural) {
    				 table1.setRuralTB1819Female(table1.getRuralTB1819Female() + 1); 
    			 }
    		   }
    		   
    		   else if(age>=20 && age <25) {
   			  table1.setActiveTB2024Female(table1.getActiveTB2024Female() + 1);
   			  
   			  if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
  				   table1.setRespiratoryTB2024Female(table1.getRespiratoryTB2024Female() + 1); 
   			  }
  			   
   			  if(pulmonary!=null && pulmonary) {
   				  table1.setPulmonaryTB2024Female(table1.getPulmonaryTB2024Female() + 1);
  				        				   
   				  if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
  					   	table1.setBacExTB2024Female(table1.getBacExTB2024Female() + 1);
  				   }
  				   
  				 	 if(miliary!=null && miliary) {
      				  table1.setMiliaryTB2024Female(table1.getMiliaryTB2024Female() + 1);
      			   }
   			  }
  			   
  			   	if(fibroCav!=null && fibroCav) {
  				   table1.setFibCavTB2024Female(table1.getFibCavTB2024Female() + 1);
  			   	}
  			  
  			   	else if(cns!=null && cns) {
  				   table1.setNervousSystemTB2024Female(table1.getNervousSystemTB2024Female() + 1);
  			   	}
  			  
  			   	else if(osteoArticular!=null && osteoArticular) {
  				   table1.setOtherOrgansTB2024Female(table1.getOtherOrgansTB2024Female() + 1);
  				   table1.setOsteoarticularTB2024Female(table1.getOsteoarticularTB2024Female() + 1);
  			   	}
  			   
  			   	else if(urogenital!=null && urogenital) {
  				   table1.setOtherOrgansTB2024Female(table1.getOtherOrgansTB2024Female() + 1);
  				   table1.setUrogenitalTB2024Female(table1.getUrogenitalTB2024Female() + 1);
  			   	}
  			   
  			   	else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
  				   table1.setOtherOrgansTB2024Female(table1.getOtherOrgansTB2024Female() + 1);
  				   table1.setLymphNodesTB2024Female(table1.getLymphNodesTB2024Female() + 1);
  			   	}
  			   
  			   	else if(abdominal!=null && abdominal) {
  				   table1.setOtherOrgansTB2024Female(table1.getOtherOrgansTB2024Female() + 1);
  				   table1.setAbdominalTB2024Female(table1.getAbdominalTB2024Female() + 1);
  			   	}
  			   
  			   	else if(eye!=null && eye) {
  				   table1.setOtherOrgansTB2024Female(table1.getOtherOrgansTB2024Female() + 1);
  				   table1.setEyeTB2024Female(table1.getEyeTB2024Female() + 1);
  			   	}
  			   
  			   	if(resistant!=null && resistant) {
  				  table1.setResistantTB2024Female(table1.getResistantTB2024Female() + 1);
  			   	}
  			   
  			   	if(hivPositive!=null && hivPositive) {
  				   table1.setTbhivTB2024Female(table1.getTbhivTB2024Female() + 1);
  			   	}
 			  
  			   	if(rural!=null && rural) {
  				   table1.setRuralTB2024Female(table1.getRuralTB2024Female() + 1); 
  			   	}
    		   }
    		   
    		   else if(age>=25 && age <35) {
   			  table1.setActiveTB2534Female(table1.getActiveTB2534Female() + 1);
   			  
   			  if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
     				   table1.setRespiratoryTB2534Female(table1.getRespiratoryTB2534Female() + 1); 
      			  }
     			   
      			  if(pulmonary!=null && pulmonary) {
      				  table1.setPulmonaryTB2534Female(table1.getPulmonaryTB2534Female() + 1);
     				        				   
      				  if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
     					   	table1.setBacExTB2534Female(table1.getBacExTB2534Female() + 1);
     				   }
     				   
     				 	 if(miliary!=null && miliary) {
         				  table1.setMiliaryTB2534Female(table1.getMiliaryTB2534Female() + 1);
         			   }
      			  }
     			   
     			   	if(fibroCav!=null && fibroCav) {
     				   table1.setFibCavTB2534Female(table1.getFibCavTB2534Female() + 1);
     			   	}
     			  
     			   	else if(cns!=null && cns) {
     				   table1.setNervousSystemTB2534Female(table1.getNervousSystemTB2534Female() + 1);
     			   	}
     			  
     			   	else if(osteoArticular!=null && osteoArticular) {
     				   table1.setOtherOrgansTB2534Female(table1.getOtherOrgansTB2534Female() + 1);
     				   table1.setOsteoarticularTB2534Female(table1.getOsteoarticularTB2534Female() + 1);
     			   	}
     			   
     			   	else if(urogenital!=null && urogenital) {
     				   table1.setOtherOrgansTB2534Female(table1.getOtherOrgansTB2534Female() + 1);
     				   table1.setUrogenitalTB2534Female(table1.getUrogenitalTB2534Female() + 1);
     			   	}
     			   
     			   	else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
     				   table1.setOtherOrgansTB2534Female(table1.getOtherOrgansTB2534Female() + 1);
     				   table1.setLymphNodesTB2534Female(table1.getLymphNodesTB2534Female() + 1);
     			   	}
     			   
     			   	else if(abdominal!=null && abdominal) {
     				   table1.setOtherOrgansTB2534Female(table1.getOtherOrgansTB2534Female() + 1);
     				   table1.setAbdominalTB2534Female(table1.getAbdominalTB2534Female() + 1);
     			   	}
     			   
     			   	else if(eye!=null && eye) {
     				   table1.setOtherOrgansTB2534Female(table1.getOtherOrgansTB2534Female() + 1);
     				   table1.setEyeTB2534Female(table1.getEyeTB2534Female() + 1);
     			   	}
     			   
     			   	if(resistant!=null && resistant) {
     				  table1.setResistantTB2534Female(table1.getResistantTB2534Female() + 1);
     			   	}
     			   
     			   	if(hivPositive!=null && hivPositive) {
     				   table1.setTbhivTB2534Female(table1.getTbhivTB2534Female() + 1);
     			   	}
    			  
     			   	if(rural!=null && rural) {
     				   table1.setRuralTB2534Female(table1.getRuralTB2534Female() + 1); 
     			   	}
    		   }
    		   
    		   else if(age>=35 && age <45) {
    			   table1.setActiveTB3544Female(table1.getActiveTB3544Female() + 1);
    			   
    			  if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
     				   table1.setRespiratoryTB3544Female(table1.getRespiratoryTB3544Female() + 1); 
      			  }
     			   
      			  if(pulmonary!=null && pulmonary) {
      				  table1.setPulmonaryTB3544Female(table1.getPulmonaryTB3544Female() + 1);
     				        				   
      				  if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
     					   	table1.setBacExTB3544Female(table1.getBacExTB3544Female() + 1);
     				   }
     				   
     				 	 if(miliary!=null && miliary) {
         				  table1.setMiliaryTB3544Female(table1.getMiliaryTB3544Female() + 1);
         			   }
      			  }
     			   
     			   	if(fibroCav!=null && fibroCav) {
     				   table1.setFibCavTB3544Female(table1.getFibCavTB3544Female() + 1);
     			   	}
     			  
     			   	else if(cns!=null && cns) {
     				   table1.setNervousSystemTB3544Female(table1.getNervousSystemTB3544Female() + 1);
     			   	}
     			  
     			   	else if(osteoArticular!=null && osteoArticular) {
     				   table1.setOtherOrgansTB3544Female(table1.getOtherOrgansTB3544Female() + 1);
     				   table1.setOsteoarticularTB3544Female(table1.getOsteoarticularTB3544Female() + 1);
     			   	}
     			   
     			   	else if(urogenital!=null && urogenital) {
     				   table1.setOtherOrgansTB3544Female(table1.getOtherOrgansTB3544Female() + 1);
     				   table1.setUrogenitalTB3544Female(table1.getUrogenitalTB3544Female() + 1);
     			   	}
     			   
     			   	else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
     				   table1.setOtherOrgansTB3544Female(table1.getOtherOrgansTB3544Female() + 1);
     				   table1.setLymphNodesTB3544Female(table1.getLymphNodesTB3544Female() + 1);
     			   	}
     			   
     			   	else if(abdominal!=null && abdominal) {
     				   table1.setOtherOrgansTB3544Female(table1.getOtherOrgansTB3544Female() + 1);
     				   table1.setAbdominalTB3544Female(table1.getAbdominalTB3544Female() + 1);
     			   	}
     			   
     			   	else if(eye!=null && eye) {
     				   table1.setOtherOrgansTB3544Female(table1.getOtherOrgansTB3544Female() + 1);
     				   table1.setEyeTB3544Female(table1.getEyeTB3544Female() + 1);
     			   	}
     			   
     			   	if(resistant!=null && resistant) {
     				  table1.setResistantTB3544Female(table1.getResistantTB3544Female() + 1);
     			   	}
     			   
     			   	if(hivPositive!=null && hivPositive) {
     				   table1.setTbhivTB3544Female(table1.getTbhivTB3544Female() + 1);
     			   	}
    			  
     			   	if(rural!=null && rural) {
     				   table1.setRuralTB3544Female(table1.getRuralTB3544Female() + 1); 
     			   	}
    		   }
    		   
    		   else if(age>=45 && age <55) {
   			   table1.setActiveTB4554Female(table1.getActiveTB4554Female() + 1);
   			   
   			   if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
      				   table1.setRespiratoryTB4554Female(table1.getRespiratoryTB4554Female() + 1); 
       			  }
      			   
       			  if(pulmonary!=null && pulmonary) {
       				  table1.setPulmonaryTB4554Female(table1.getPulmonaryTB4554Female() + 1);
      				        				   
       				  if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
      					   	table1.setBacExTB4554Female(table1.getBacExTB4554Female() + 1);
      				   }
      				   
      				 	 if(miliary!=null && miliary) {
          				  table1.setMiliaryTB4554Female(table1.getMiliaryTB4554Female() + 1);
          			   }
       			  }
      			   
      			   	if(fibroCav!=null && fibroCav) {
      				   table1.setFibCavTB4554Female(table1.getFibCavTB4554Female() + 1);
      			   	}
      			  
      			   	else if(cns!=null && cns) {
      				   table1.setNervousSystemTB4554Female(table1.getNervousSystemTB4554Female() + 1);
      			   	}
      			  
      			   	else if(osteoArticular!=null && osteoArticular) {
      				   table1.setOtherOrgansTB4554Female(table1.getOtherOrgansTB4554Female() + 1);
      				   table1.setOsteoarticularTB4554Female(table1.getOsteoarticularTB4554Female() + 1);
      			   	}
      			   
      			   	else if(urogenital!=null && urogenital) {
      				   table1.setOtherOrgansTB4554Female(table1.getOtherOrgansTB4554Female() + 1);
      				   table1.setUrogenitalTB4554Female(table1.getUrogenitalTB4554Female() + 1);
      			   	}
      			   
      			   	else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
      				   table1.setOtherOrgansTB4554Female(table1.getOtherOrgansTB4554Female() + 1);
      				   table1.setLymphNodesTB4554Female(table1.getLymphNodesTB4554Female() + 1);
      			   	}
      			   
      			   	else if(abdominal!=null && abdominal) {
      				   table1.setOtherOrgansTB4554Female(table1.getOtherOrgansTB4554Female() + 1);
      				   table1.setAbdominalTB4554Female(table1.getAbdominalTB4554Female() + 1);
      			   	}
      			   
      			   	else if(eye!=null && eye) {
      				   table1.setOtherOrgansTB4554Female(table1.getOtherOrgansTB4554Female() + 1);
      				   table1.setEyeTB4554Female(table1.getEyeTB4554Female() + 1);
      			   	}
      			   
      			   	if(resistant!=null && resistant) {
      				  table1.setResistantTB4554Female(table1.getResistantTB4554Female() + 1);
      			   	}
      			   
      			   	if(hivPositive!=null && hivPositive) {
      				   table1.setTbhivTB4554Female(table1.getTbhivTB4554Female() + 1);
      			   	}
     			  
      			   	if(rural!=null && rural) {
      				   table1.setRuralTB4554Female(table1.getRuralTB4554Female() + 1); 
      			   	}
   		   }
    		   
    		   else if(age>=55 && age <65) {
    			   table1.setActiveTB5564Female(table1.getActiveTB5564Female() + 1);
    			   
    			  if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
     				   table1.setRespiratoryTB5564Female(table1.getRespiratoryTB5564Female() + 1); 
      			  }
     			   
      			  if(pulmonary!=null && pulmonary) {
      				  table1.setPulmonaryTB5564Female(table1.getPulmonaryTB5564Female() + 1);
     				        				   
      				  if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
     					   	table1.setBacExTB5564Female(table1.getBacExTB5564Female() + 1);
     				   }
     				   
     				 	 if(miliary!=null && miliary) {
         				  table1.setMiliaryTB5564Female(table1.getMiliaryTB5564Female() + 1);
         			   }
      			  }
     			   
     			   	if(fibroCav!=null && fibroCav) {
     				   table1.setFibCavTB5564Female(table1.getFibCavTB5564Female() + 1);
     			   	}
     			  
     			   	else if(cns!=null && cns) {
     				   table1.setNervousSystemTB5564Female(table1.getNervousSystemTB5564Female() + 1);
     			   	}
     			  
     			   	else if(osteoArticular!=null && osteoArticular) {
     				   table1.setOtherOrgansTB5564Female(table1.getOtherOrgansTB5564Female() + 1);
     				   table1.setOsteoarticularTB5564Female(table1.getOsteoarticularTB5564Female() + 1);
     			   	}
     			   
     			   	else if(urogenital!=null && urogenital) {
     				   table1.setOtherOrgansTB5564Female(table1.getOtherOrgansTB5564Female() + 1);
     				   table1.setUrogenitalTB5564Female(table1.getUrogenitalTB5564Female() + 1);
     			   	}
     			   
     			   	else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
     				   table1.setOtherOrgansTB5564Female(table1.getOtherOrgansTB5564Female() + 1);
     				   table1.setLymphNodesTB5564Female(table1.getLymphNodesTB5564Female() + 1);
     			   	}
     			   
     			   	else if(abdominal!=null && abdominal) {
     				   table1.setOtherOrgansTB5564Female(table1.getOtherOrgansTB5564Female() + 1);
     				   table1.setAbdominalTB5564Female(table1.getAbdominalTB5564Female() + 1);
     			   	}
     			   
     			   	else if(eye!=null && eye) {
     				   table1.setOtherOrgansTB5564Female(table1.getOtherOrgansTB5564Female() + 1);
     				   table1.setEyeTB5564Female(table1.getEyeTB5564Female() + 1);
     			   	}
     			   
     			   	if(resistant!=null && resistant) {
     				  table1.setResistantTB5564Female(table1.getResistantTB5564Female() + 1);
     			   	}
     			   
     			   	if(hivPositive!=null && hivPositive) {
     				   table1.setTbhivTB5564Female(table1.getTbhivTB5564Female() + 1);
     			   	}
    			  
     			   	if(rural!=null && rural) {
     				   table1.setRuralTB5564Female(table1.getRuralTB5564Female() + 1); 
     			   	}
    		   }
    		   
    		   else if(age>=65) {
   			   table1.setActiveTB65Female(table1.getActiveTB65Female() + 1);
   			   
   			   if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
      				   table1.setRespiratoryTB65Female(table1.getRespiratoryTB65Female() + 1); 
       			  }
      			   
       			  if(pulmonary!=null && pulmonary) {
       				  table1.setPulmonaryTB65Female(table1.getPulmonaryTB65Female() + 1);
      				        				   
       				  if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
      					   	table1.setBacExTB65Female(table1.getBacExTB65Female() + 1);
      				   }
      				   
      				 	 if(miliary!=null && miliary) {
          				  table1.setMiliaryTB65Female(table1.getMiliaryTB65Female() + 1);
          			   }
       			  }
      			   
      			   	if(fibroCav!=null && fibroCav) {
      				   table1.setFibCavTB65Female(table1.getFibCavTB65Female() + 1);
      			   	}
      			  
      			   	else if(cns!=null && cns) {
      				   table1.setNervousSystemTB65Female(table1.getNervousSystemTB65Female() + 1);
      			   	}
      			  
      			   	else if(osteoArticular!=null && osteoArticular) {
      				   table1.setOtherOrgansTB65Female(table1.getOtherOrgansTB65Female() + 1);
      				   table1.setOsteoarticularTB65Female(table1.getOsteoarticularTB65Female() + 1);
      			   	}
      			   
      			   	else if(urogenital!=null && urogenital) {
      				   table1.setOtherOrgansTB65Female(table1.getOtherOrgansTB65Female() + 1);
      				   table1.setUrogenitalTB65Female(table1.getUrogenitalTB65Female() + 1);
      			   	}
      			   
      			   	else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
      				   table1.setOtherOrgansTB65Female(table1.getOtherOrgansTB65Female() + 1);
      				   table1.setLymphNodesTB65Female(table1.getLymphNodesTB65Female() + 1);
      			   	}
      			   
      			   	else if(abdominal!=null && abdominal) {
      				   table1.setOtherOrgansTB65Female(table1.getOtherOrgansTB65Female() + 1);
      				   table1.setAbdominalTB65Female(table1.getAbdominalTB65Female() + 1);
      			   	}
      			   
      			   	else if(eye!=null && eye) {
      				   table1.setOtherOrgansTB65Female(table1.getOtherOrgansTB65Female() + 1);
      				   table1.setEyeTB65Female(table1.getEyeTB65Female() + 1);
      			   	}
      			   
      			   	if(resistant!=null && resistant) {
      				  table1.setResistantTB65Female(table1.getResistantTB65Female() + 1);
      			   	}
      			   
      			   	if(hivPositive!=null && hivPositive) {
      				   table1.setTbhivTB65Female(table1.getTbhivTB65Female() + 1);
      			   	}
     			  
      			   	if(rural!=null && rural) {
      				   table1.setRuralTB65Female(table1.getRuralTB65Female() + 1); 
      			   	}
   		   }
    		   
    		   if(rural!=null && rural) {
    			  table1.setActiveTBRuralFemale(table1.getActiveTBRuralFemale() + 1);
    			  
    			 if((pulmonary!=null && pulmonary) || (plevritis!=null && plevritis) || (itLymph!=null && itLymph) ) {
    				   table1.setRespiratoryTBRuralFemale(table1.getRespiratoryTBRuralFemale() + 1); 
     			  }
    			   
     			  if(pulmonary!=null && pulmonary) {
     				  table1.setPulmonaryTBRuralFemale(table1.getPulmonaryTBRuralFemale() + 1);
    				        				   
     				  if(MdrtbUtil.isDiagnosticBacPositive(tf)) {
    					   	table1.setBacExTBRuralFemale(table1.getBacExTBRuralFemale() + 1);
    				   }
    				   
    				 	 if(miliary!=null && miliary) {
        				  table1.setMiliaryTBRuralFemale(table1.getMiliaryTBRuralFemale() + 1);
        			   }
     			  }
    			   
    			   	if(fibroCav!=null && fibroCav) {
    				   table1.setFibCavTBRuralFemale(table1.getFibCavTBRuralFemale() + 1);
    			   	}
    			  
    			   	else if(cns!=null && cns) {
    				   table1.setNervousSystemTBRuralFemale(table1.getNervousSystemTBRuralFemale() + 1);
    			   	}
    			  
    			   	else if(osteoArticular!=null && osteoArticular) {
    				   table1.setOtherOrgansTBRuralFemale(table1.getOtherOrgansTBRuralFemale() + 1);
    				   table1.setOsteoarticularTBRuralFemale(table1.getOsteoarticularTBRuralFemale() + 1);
    			   	}
    			   
    			   	else if(urogenital!=null && urogenital) {
    				   table1.setOtherOrgansTBRuralFemale(table1.getOtherOrgansTBRuralFemale() + 1);
    				   table1.setUrogenitalTBRuralFemale(table1.getUrogenitalTBRuralFemale() + 1);
    			   	}
    			   
    			   	else if(peripheralLymphNodes!=null && peripheralLymphNodes) {
    				   table1.setOtherOrgansTBRuralFemale(table1.getOtherOrgansTBRuralFemale() + 1);
    				   table1.setLymphNodesTBRuralFemale(table1.getLymphNodesTBRuralFemale() + 1);
    			   	}
    			   
    			   	else if(abdominal!=null && abdominal) {
    				   table1.setOtherOrgansTBRuralFemale(table1.getOtherOrgansTBRuralFemale() + 1);
    				   table1.setAbdominalTBRuralFemale(table1.getAbdominalTBRuralFemale() + 1);
    			   	}
    			   
    			   	else if(eye!=null && eye) {
    				   table1.setOtherOrgansTBRuralFemale(table1.getOtherOrgansTBRuralFemale() + 1);
    				   table1.setEyeTBRuralFemale(table1.getEyeTBRuralFemale() + 1);
    			   	}
    			   
    			   	if(resistant!=null && resistant) {
    				  table1.setResistantTBRuralFemale(table1.getResistantTBRuralFemale() + 1);
    			   	}
    			   
    			   	if(hivPositive!=null && hivPositive) {
    				   table1.setTbhivTBRuralFemale(table1.getTbhivTBRuralFemale() + 1);
    			   	}
    		   }
     	   }
		}
    	
    	
    	//Table 6
    	 tb03List = Context.getService(MdrtbService.class).getTB03FormsFilled(locList, year - 1, quarter, month);
    	
    	for (TB03Form tf : tb03List) {//for (Integer i : idSet) {
    		
    		ageAtRegistration = -1;
    		pulmonary = null;
    		bacPositive = null;
    		
    		cured = null;
    		txCompleted = null;
    		diedTB = null;
    		diedNotTB = null;
    		failed = null;
    		defaulted = null;
    		transferOut = null;
    		canceled = null;
    		sld = null;
    		
    		Patient patient = tf.getPatient();
    	    if(patient==null || patient.isVoided()) {
    	    	continue;
    	    	
    	    }
    		
    	    ageAtRegistration = tf.getAgeAtTB03Registration();
  
    	    //get disease site
    	    Concept q = tf.getAnatomicalSite();
    	    
    	    if(q!=null) {
    	    	if(q.getConceptId().intValue()==pulmonaryConcept.getConceptId().intValue()) {
    	    		pulmonary = Boolean.TRUE;
    	    		System.out.println("PULMONARY");
    	    	}
    	    	
    	    	else if (q.getConceptId().intValue()==extrapulmonaryConcept.getConceptId().intValue()) {
    	    		pulmonary = Boolean.FALSE;
    	    		System.out.println("EXTRAPULMONARY");
    	    	}
    	    	
    	    	else {
    	    		
    	    		System.out.println("PULMONARY NULL");
    	    		pulmonary = null;
    	    	}
    	    	
    	    }
    	    
    	    else {
    	    	System.out.println("NO SITE");
    	    	continue;
    	    }
    	    
    	    bacPositive = MdrtbUtil.isDiagnosticBacPositive(tf);
    	    
    	    //OUTCOMES
    	    q = tf.getTreatmentOutcome();

    	    if(q!=null) {
    	    	
    	    	if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.cured.conceptId"))) {
    	    		cured = Boolean.TRUE;
    	    		System.out.println("CURED");
    	    		
    	    	}
    	    	
    	    	else if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.txCompleted.conceptId"))) {
    	    		txCompleted = Boolean.TRUE;
    	    		System.out.println("TxC");
    	    	}
    	    	
    	    	else if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.txFailure.conceptId"))) {
    	    		failed = Boolean.TRUE;
    	    		System.out.println("FAIL");
    	    	}
    	    	
    	    	else if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.died.conceptId"))) {
    	    		System.out.println("DIED");
    	    		q = tf.getCauseOfDeath();//Context.getService(MdrtbService.class).getConcept(TbConcepts.CAUSE_OF_DEATH);
    	     	   
    	     	    
    	     	   
    	     	    if(q!=null)
    	     	    {	
    	     	    	if(q.getId().intValue() == Context.getService(MdrtbService.class).getConcept(TbConcepts.DEATH_BY_TB).getConceptId().intValue())
    	     	    		diedTB = Boolean.TRUE;
    	     	    	else
    	     	    		diedNotTB = Boolean.TRUE;
    	     	    }
    	    	}
    	    	
    	    	else if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.ltfu.conceptId"))) {
    	    		defaulted = Boolean.TRUE;
    	    		System.out.println("DEF");
    	    	}
    	    	
    	    	else if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.canceled.conceptId"))) {
    	    		canceled = Boolean.TRUE;
    	    		System.out.println("CANCEL");
    	    	}
    	    	
    	    	else if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.transferout.conceptId"))) {
    	    		transferOut = Boolean.TRUE;
    	    		System.out.println("TOUT");
    	    	}
    	    	
    	    	else if(q.getId().intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.sld2.conceptId"))) {
    	    		sld = Boolean.TRUE;
    	    		System.out.println("SLD2");
    	    	}
    	    }
    	    
    	    else {
	    		System.out.println("NO OUTCOME");
	    	}
    	    
    	    //get registration group
    	    //REGISTRATION GROUP
    	    q = tf.getRegistrationGroup();
    	   
    	  
    	    
    	    if(q!=null) {
    	    	
    	    	
    	    	if(q.getId().intValue()!=Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.transferIn.conceptId"))) {
    	    		
    	    		table6.setAllDetected(table6.getAllDetected() + 1);
    	    		
    	    		if(cured!=null && cured) {
						table6.setAllCured(table6.getAllCured() + 1);
						table6.setAllEligible(table6.getAllEligible() + 1);
					}
					
					else if (txCompleted!=null && txCompleted) {
						table6.setAllCompleted(table6.getAllCompleted() + 1);
						table6.setAllEligible(table6.getAllEligible() + 1);
					}
					
					else if (diedTB!=null && diedTB) {
						table6.setAllDiedTB(table6.getAllDiedTB() + 1);
						table6.setAllEligible(table6.getAllEligible() + 1);
					}
					
					else if (diedNotTB!=null && diedNotTB) {
						table6.setAllDiedNotTB(table6.getAllDiedNotTB() + 1);
						table6.setAllEligible(table6.getAllEligible() + 1);
					}
					
					else if (failed!=null && failed) {
						table6.setAllFailed(table6.getAllFailed() + 1);
						table6.setAllEligible(table6.getAllEligible() + 1);
					}
					
					else if (defaulted!=null && defaulted) {
						table6.setAllDefaulted(table6.getAllDefaulted() + 1);
						table6.setAllEligible(table6.getAllEligible() + 1);
					}
					
					else if (transferOut!=null && transferOut) {
						table6.setAllTransferOut(table6.getAllTransferOut() + 1);
						
					}
					
					else if (canceled!=null && canceled) {
						table6.setAllCanceled(table6.getAllCanceled() + 1);
						
					}
					
					else if (sld!=null && sld) {
						table6.setAllSLD(table6.getAllSLD() + 1);
						
					}
    	    	}
    	    	//NEW
    	    	if(q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.new.conceptId"))) {
    	    		
    	    		table6.setNewAllDetected(table6.getNewAllDetected() + 1);
    	    		
    	    		//P
    	    		if(pulmonary!=null && pulmonary) {
    	    			
    	    			//BC
    	    			if(bacPositive) {
    	    				
    	    				table6.setNewPulmonaryBCDetected(table6.getNewPulmonaryBCDetected() + 1);
    	    				
    	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
    	    					
    	    					table6.setNewPulmonaryBCDetected04(table6.getNewPulmonaryBCDetected04() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setNewAllCured(table6.getNewAllCured() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCCured04(table6.getNewPulmonaryBCCured04() + 1);
    	    						table6.setNewPulmonaryBCEligible04(table6.getNewPulmonaryBCEligible04() + 1);
    	    						table6.setNewPulmonaryBCCured(table6.getNewPulmonaryBCCured() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    						
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setNewAllCompleted(table6.getNewAllCompleted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCCompleted04(table6.getNewPulmonaryBCCompleted04() + 1);
    	    						table6.setNewPulmonaryBCEligible04(table6.getNewPulmonaryBCEligible04() + 1);
    	    						table6.setNewPulmonaryBCCompleted(table6.getNewPulmonaryBCCompleted() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setNewAllDiedTB(table6.getNewAllDiedTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCDiedTB04(table6.getNewPulmonaryBCDiedTB04() + 1);
    	    						table6.setNewPulmonaryBCEligible04(table6.getNewPulmonaryBCEligible04() + 1);
    	    						table6.setNewPulmonaryBCDiedTB(table6.getNewPulmonaryBCDiedTB() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setNewAllDiedNotTB(table6.getNewAllDiedNotTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCDiedNotTB04(table6.getNewPulmonaryBCDiedNotTB04() + 1);
    	    						table6.setNewPulmonaryBCEligible04(table6.getNewPulmonaryBCEligible04() + 1);
    	    						table6.setNewPulmonaryBCDiedNotTB(table6.getNewPulmonaryBCDiedNotTB() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setNewAllFailed(table6.getNewAllFailed() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCFailed04(table6.getNewPulmonaryBCFailed04() + 1);
    	    						table6.setNewPulmonaryBCEligible04(table6.getNewPulmonaryBCEligible04() + 1);
    	    						table6.setNewPulmonaryBCFailed(table6.getNewPulmonaryBCFailed() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setNewAllDefaulted(table6.getNewAllDefaulted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCDefaulted04(table6.getNewPulmonaryBCDefaulted04() + 1);
    	    						table6.setNewPulmonaryBCEligible04(table6.getNewPulmonaryBCEligible04() + 1);
    	    						table6.setNewPulmonaryBCDefaulted(table6.getNewPulmonaryBCDefaulted() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setNewAllTransferOut(table6.getNewAllTransferOut() + 1);
    	    						table6.setNewPulmonaryBCTransferOut04(table6.getNewPulmonaryBCTransferOut04() + 1);
    	    						table6.setNewPulmonaryBCTransferOut(table6.getNewPulmonaryBCTransferOut() + 1);
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setNewAllCanceled(table6.getNewAllCanceled() + 1);
    	    						table6.setNewPulmonaryBCCanceled04(table6.getNewPulmonaryBCCanceled04() + 1);
    	    						table6.setNewPulmonaryBCCanceled(table6.getNewPulmonaryBCCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setNewAllSLD(table6.getNewAllSLD() + 1);
    	    						table6.setNewPulmonaryBCSLD04(table6.getNewPulmonaryBCSLD04() + 1);
    	    						table6.setNewPulmonaryBCSLD(table6.getNewPulmonaryBCSLD() + 1);
    	    					}
    	    					
    	    					

    	    				}
    	    				
    	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
    	    					
    	    					table6.setNewPulmonaryBCDetected0514(table6.getNewPulmonaryBCDetected0514() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setNewAllCured(table6.getNewAllCured() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCCured0514(table6.getNewPulmonaryBCCured0514() + 1);
    	    						table6.setNewPulmonaryBCEligible0514(table6.getNewPulmonaryBCEligible0514() + 1);
    	    						table6.setNewPulmonaryBCCured(table6.getNewPulmonaryBCCured() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setNewAllCompleted(table6.getNewAllCompleted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCCompleted0514(table6.getNewPulmonaryBCCompleted0514() + 1);
    	    						table6.setNewPulmonaryBCEligible0514(table6.getNewPulmonaryBCEligible0514() + 1);
    	    						table6.setNewPulmonaryBCCompleted(table6.getNewPulmonaryBCCompleted() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setNewAllDiedTB(table6.getNewAllDiedTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCDiedTB0514(table6.getNewPulmonaryBCDiedTB0514() + 1);
    	    						table6.setNewPulmonaryBCEligible0514(table6.getNewPulmonaryBCEligible0514() + 1);
    	    						table6.setNewPulmonaryBCDiedTB(table6.getNewPulmonaryBCDiedTB() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setNewAllDiedNotTB(table6.getNewAllDiedNotTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCDiedNotTB0514(table6.getNewPulmonaryBCDiedNotTB0514() + 1);
    	    						table6.setNewPulmonaryBCEligible0514(table6.getNewPulmonaryBCEligible0514() + 1);
    	    						table6.setNewPulmonaryBCDiedNotTB(table6.getNewPulmonaryBCDiedNotTB() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setNewAllFailed(table6.getNewAllFailed() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCFailed0514(table6.getNewPulmonaryBCFailed0514() + 1);
    	    						table6.setNewPulmonaryBCEligible0514(table6.getNewPulmonaryBCEligible0514() + 1);
    	    						table6.setNewPulmonaryBCFailed(table6.getNewPulmonaryBCFailed() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setNewAllDefaulted(table6.getNewAllDefaulted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCDefaulted0514(table6.getNewPulmonaryBCDefaulted0514() + 1);
    	    						table6.setNewPulmonaryBCEligible0514(table6.getNewPulmonaryBCEligible0514() + 1);
    	    						table6.setNewPulmonaryBCDefaulted(table6.getNewPulmonaryBCDefaulted() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setNewAllTransferOut(table6.getNewAllTransferOut() + 1);
    	    						table6.setNewPulmonaryBCTransferOut0514(table6.getNewPulmonaryBCTransferOut0514() + 1);
    	    						table6.setNewPulmonaryBCTransferOut(table6.getNewPulmonaryBCTransferOut() + 1);
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setNewAllCanceled(table6.getNewAllCanceled() + 1);
    	    						table6.setNewPulmonaryBCCanceled0514(table6.getNewPulmonaryBCCanceled0514() + 1);
    	    						table6.setNewPulmonaryBCCanceled(table6.getNewPulmonaryBCCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setNewAllSLD(table6.getNewAllSLD() + 1);
    	    						table6.setNewPulmonaryBCSLD0514(table6.getNewPulmonaryBCSLD0514() + 1);
    	    						table6.setNewPulmonaryBCSLD(table6.getNewPulmonaryBCSLD() + 1);
    	    					}
    	    					
    	    				}
    	    				
    	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
    	    					
    	    					table6.setNewPulmonaryBCDetected1517(table6.getNewPulmonaryBCDetected1517() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setNewAllCured(table6.getNewAllCured() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCCured1517(table6.getNewPulmonaryBCCured1517() + 1);
    	    						table6.setNewPulmonaryBCEligible1517(table6.getNewPulmonaryBCEligible1517() + 1);
    	    						table6.setNewPulmonaryBCCured(table6.getNewPulmonaryBCCured() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setNewAllCompleted(table6.getNewAllCompleted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCCompleted1517(table6.getNewPulmonaryBCCompleted1517() + 1);
    	    						table6.setNewPulmonaryBCEligible1517(table6.getNewPulmonaryBCEligible1517() + 1);
    	    						table6.setNewPulmonaryBCCompleted(table6.getNewPulmonaryBCCompleted() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setNewAllDiedTB(table6.getNewAllDiedTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCDiedTB1517(table6.getNewPulmonaryBCDiedTB1517() + 1);
    	    						table6.setNewPulmonaryBCEligible1517(table6.getNewPulmonaryBCEligible1517() + 1);
    	    						table6.setNewPulmonaryBCDiedTB(table6.getNewPulmonaryBCDiedTB() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setNewAllDiedNotTB(table6.getNewAllDiedNotTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCDiedNotTB1517(table6.getNewPulmonaryBCDiedNotTB1517() + 1);
    	    						table6.setNewPulmonaryBCEligible1517(table6.getNewPulmonaryBCEligible1517() + 1);
    	    						table6.setNewPulmonaryBCDiedNotTB(table6.getNewPulmonaryBCDiedNotTB() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setNewAllFailed(table6.getNewAllFailed() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCFailed1517(table6.getNewPulmonaryBCFailed1517() + 1);
    	    						table6.setNewPulmonaryBCEligible1517(table6.getNewPulmonaryBCEligible1517() + 1);
    	    						table6.setNewPulmonaryBCFailed(table6.getNewPulmonaryBCFailed() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setNewAllDefaulted(table6.getNewAllDefaulted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCDefaulted1517(table6.getNewPulmonaryBCDefaulted1517() + 1);
    	    						table6.setNewPulmonaryBCEligible1517(table6.getNewPulmonaryBCEligible1517() + 1);
    	    						table6.setNewPulmonaryBCDefaulted(table6.getNewPulmonaryBCDefaulted() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setNewAllTransferOut(table6.getNewAllTransferOut() + 1);
    	    						table6.setNewPulmonaryBCTransferOut1517(table6.getNewPulmonaryBCTransferOut1517() + 1);
    	    						table6.setNewPulmonaryBCTransferOut(table6.getNewPulmonaryBCTransferOut() + 1);
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setNewAllCanceled(table6.getNewAllCanceled() + 1);
    	    						table6.setNewPulmonaryBCCanceled1517(table6.getNewPulmonaryBCCanceled1517() + 1);
    	    						table6.setNewPulmonaryBCCanceled(table6.getNewPulmonaryBCCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setNewAllSLD(table6.getNewAllSLD() + 1);
    	    						table6.setNewPulmonaryBCSLD1517(table6.getNewPulmonaryBCSLD1517() + 1);
    	    						table6.setNewPulmonaryBCSLD(table6.getNewPulmonaryBCSLD() + 1);
    	    					}
    	    					
    	    				}
    	    				
    	    				else {
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setNewAllCured(table6.getNewAllCured() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCCured(table6.getNewPulmonaryBCCured() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setNewAllCompleted(table6.getNewAllCompleted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCCompleted(table6.getNewPulmonaryBCCompleted() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setNewAllDiedTB(table6.getNewAllDiedTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCDiedTB(table6.getNewPulmonaryBCDiedTB() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setNewAllDiedNotTB(table6.getNewAllDiedNotTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCDiedNotTB(table6.getNewPulmonaryBCDiedNotTB() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setNewAllFailed(table6.getNewAllFailed() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCFailed(table6.getNewPulmonaryBCFailed() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setNewAllDefaulted(table6.getNewAllDefaulted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryBCDefaulted(table6.getNewPulmonaryBCDefaulted() + 1);
    	    						table6.setNewPulmonaryBCEligible(table6.getNewPulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setNewAllTransferOut(table6.getNewAllTransferOut() + 1);
    	    						table6.setNewPulmonaryBCTransferOut(table6.getNewPulmonaryBCTransferOut() + 1);

    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setNewAllCanceled(table6.getNewAllCanceled() + 1);
    	    						table6.setNewPulmonaryBCCanceled(table6.getNewPulmonaryBCCanceled() + 1);
    	    						
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setNewAllSLD(table6.getNewAllSLD() + 1);
    	    						table6.setNewPulmonaryBCSLD(table6.getNewPulmonaryBCSLD() + 1);
    	    						
    	    					}
    	    				}
    	    				
    	    			}
    	    			
    	    			//CD
    	    			else {
    	    				
    	    				table6.setNewPulmonaryCDDetected(table6.getNewPulmonaryCDDetected() + 1);
    	    				
    	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
    	    					
    	    					table6.setNewPulmonaryCDDetected04(table6.getNewPulmonaryCDDetected04() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setNewAllCured(table6.getNewAllCured() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDCured04(table6.getNewPulmonaryCDCured04() + 1);
    	    						table6.setNewPulmonaryCDEligible04(table6.getNewPulmonaryCDEligible04() + 1);
    	    						table6.setNewPulmonaryCDCured(table6.getNewPulmonaryCDCured() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setNewAllCompleted(table6.getNewAllCompleted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDCompleted04(table6.getNewPulmonaryCDCompleted04() + 1);
    	    						table6.setNewPulmonaryCDEligible04(table6.getNewPulmonaryCDEligible04() + 1);
    	    						table6.setNewPulmonaryCDCompleted(table6.getNewPulmonaryCDCompleted() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setNewAllDiedTB(table6.getNewAllDiedTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDDiedTB04(table6.getNewPulmonaryCDDiedTB04() + 1);
    	    						table6.setNewPulmonaryCDEligible04(table6.getNewPulmonaryCDEligible04() + 1);
    	    						table6.setNewPulmonaryCDDiedTB(table6.getNewPulmonaryCDDiedTB() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setNewAllDiedNotTB(table6.getNewAllDiedNotTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDDiedNotTB04(table6.getNewPulmonaryCDDiedNotTB04() + 1);
    	    						table6.setNewPulmonaryCDEligible04(table6.getNewPulmonaryCDEligible04() + 1);
    	    						table6.setNewPulmonaryCDDiedNotTB(table6.getNewPulmonaryCDDiedNotTB() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setNewAllFailed(table6.getNewAllFailed() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDFailed04(table6.getNewPulmonaryCDFailed04() + 1);
    	    						table6.setNewPulmonaryCDEligible04(table6.getNewPulmonaryCDEligible04() + 1);
    	    						table6.setNewPulmonaryCDFailed(table6.getNewPulmonaryCDFailed() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setNewAllDefaulted(table6.getNewAllDefaulted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDDefaulted04(table6.getNewPulmonaryCDDefaulted04() + 1);
    	    						table6.setNewPulmonaryCDEligible04(table6.getNewPulmonaryCDEligible04() + 1);
    	    						table6.setNewPulmonaryCDDefaulted(table6.getNewPulmonaryCDDefaulted() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setNewAllTransferOut(table6.getNewAllTransferOut() + 1);
    	    						table6.setNewPulmonaryCDTransferOut04(table6.getNewPulmonaryCDTransferOut04() + 1);
    	    						table6.setNewPulmonaryCDTransferOut(table6.getNewPulmonaryCDTransferOut() + 1);
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setNewAllCanceled(table6.getNewAllCanceled() + 1);
    	    						table6.setNewPulmonaryCDCanceled04(table6.getNewPulmonaryCDCanceled04() + 1);
    	    						table6.setNewPulmonaryCDCanceled(table6.getNewPulmonaryCDCanceled() + 1);
    	    						
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setNewAllSLD(table6.getNewAllSLD() + 1);
    	    						table6.setNewPulmonaryCDSLD04(table6.getNewPulmonaryCDSLD04() + 1);
    	    						table6.setNewPulmonaryCDSLD(table6.getNewPulmonaryCDSLD() + 1);
    	    						
    	    					}
    	    					
    	    					

    	    				}
    	    				
    	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
    	    					
    	    					table6.setNewPulmonaryCDDetected0514(table6.getNewPulmonaryCDDetected0514() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setNewAllCured(table6.getNewAllCured() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDCured0514(table6.getNewPulmonaryCDCured0514() + 1);
    	    						table6.setNewPulmonaryCDEligible0514(table6.getNewPulmonaryCDEligible0514() + 1);
    	    						table6.setNewPulmonaryCDCured(table6.getNewPulmonaryCDCured() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setNewAllCompleted(table6.getNewAllCompleted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDCompleted0514(table6.getNewPulmonaryCDCompleted0514() + 1);
    	    						table6.setNewPulmonaryCDEligible0514(table6.getNewPulmonaryCDEligible0514() + 1);
    	    						table6.setNewPulmonaryCDCompleted(table6.getNewPulmonaryCDCompleted() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setNewAllDiedTB(table6.getNewAllDiedTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDDiedTB0514(table6.getNewPulmonaryCDDiedTB0514() + 1);
    	    						table6.setNewPulmonaryCDEligible0514(table6.getNewPulmonaryCDEligible0514() + 1);
    	    						table6.setNewPulmonaryCDDiedTB(table6.getNewPulmonaryCDDiedTB() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setNewAllDiedNotTB(table6.getNewAllDiedNotTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDDiedNotTB0514(table6.getNewPulmonaryCDDiedNotTB0514() + 1);
    	    						table6.setNewPulmonaryCDEligible0514(table6.getNewPulmonaryCDEligible0514() + 1);
    	    						table6.setNewPulmonaryCDDiedNotTB(table6.getNewPulmonaryCDDiedNotTB() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setNewAllFailed(table6.getNewAllFailed() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDFailed0514(table6.getNewPulmonaryCDFailed0514() + 1);
    	    						table6.setNewPulmonaryCDEligible0514(table6.getNewPulmonaryCDEligible0514() + 1);
    	    						table6.setNewPulmonaryCDFailed(table6.getNewPulmonaryCDFailed() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setNewAllDefaulted(table6.getNewAllDefaulted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDDefaulted0514(table6.getNewPulmonaryCDDefaulted0514() + 1);
    	    						table6.setNewPulmonaryCDEligible0514(table6.getNewPulmonaryCDEligible0514() + 1);
    	    						table6.setNewPulmonaryCDDefaulted(table6.getNewPulmonaryCDDefaulted() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setNewAllTransferOut(table6.getNewAllTransferOut() + 1);
    	    						table6.setNewPulmonaryCDTransferOut0514(table6.getNewPulmonaryCDTransferOut0514() + 1);
    	    						table6.setNewPulmonaryCDTransferOut(table6.getNewPulmonaryCDTransferOut() + 1);
    	    						
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setNewAllCanceled(table6.getNewAllCanceled() + 1);
    	    						table6.setNewPulmonaryCDCanceled0514(table6.getNewPulmonaryCDCanceled0514() + 1);
    	    						table6.setNewPulmonaryCDCanceled(table6.getNewPulmonaryCDCanceled() + 1);
    	    						
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setNewAllSLD(table6.getNewAllSLD() + 1);
    	    						table6.setNewPulmonaryCDSLD0514(table6.getNewPulmonaryCDSLD0514() + 1);
    	    						table6.setNewPulmonaryCDSLD(table6.getNewPulmonaryCDSLD() + 1);
    	    						
    	    					}
    	    					
    	    				}
    	    				
    	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
    	    					
    	    					table6.setNewPulmonaryCDDetected1517(table6.getNewPulmonaryCDDetected1517() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setNewAllCured(table6.getNewAllCured() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDCured1517(table6.getNewPulmonaryCDCured1517() + 1);
    	    						table6.setNewPulmonaryCDEligible1517(table6.getNewPulmonaryCDEligible1517() + 1);
    	    						table6.setNewPulmonaryCDCured(table6.getNewPulmonaryCDCured() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setNewAllCompleted(table6.getNewAllCompleted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDCompleted1517(table6.getNewPulmonaryCDCompleted1517() + 1);
    	    						table6.setNewPulmonaryCDEligible1517(table6.getNewPulmonaryCDEligible1517() + 1);
    	    						table6.setNewPulmonaryCDCompleted(table6.getNewPulmonaryCDCompleted() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setNewAllDiedTB(table6.getNewAllDiedTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDDiedTB1517(table6.getNewPulmonaryCDDiedTB1517() + 1);
    	    						table6.setNewPulmonaryCDEligible1517(table6.getNewPulmonaryCDEligible1517() + 1);
    	    						table6.setNewPulmonaryCDDiedTB(table6.getNewPulmonaryCDDiedTB() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setNewAllDiedNotTB(table6.getNewAllDiedNotTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDDiedNotTB1517(table6.getNewPulmonaryCDDiedNotTB1517() + 1);
    	    						table6.setNewPulmonaryCDEligible1517(table6.getNewPulmonaryCDEligible1517() + 1);
    	    						table6.setNewPulmonaryCDDiedNotTB(table6.getNewPulmonaryCDDiedNotTB() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setNewAllFailed(table6.getNewAllFailed() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDFailed1517(table6.getNewPulmonaryCDFailed1517() + 1);
    	    						table6.setNewPulmonaryCDEligible1517(table6.getNewPulmonaryCDEligible1517() + 1);
    	    						table6.setNewPulmonaryCDFailed(table6.getNewPulmonaryCDFailed() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setNewAllDefaulted(table6.getNewAllDefaulted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDDefaulted1517(table6.getNewPulmonaryCDDefaulted1517() + 1);
    	    						table6.setNewPulmonaryCDEligible1517(table6.getNewPulmonaryCDEligible1517() + 1);
    	    						table6.setNewPulmonaryCDDefaulted(table6.getNewPulmonaryCDDefaulted() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setNewAllTransferOut(table6.getNewAllTransferOut() + 1);
    	    						table6.setNewPulmonaryCDTransferOut1517(table6.getNewPulmonaryCDTransferOut1517() + 1);
    	    						table6.setNewPulmonaryCDTransferOut(table6.getNewPulmonaryCDTransferOut() + 1);
    	    						
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setNewAllCanceled(table6.getNewAllCanceled() + 1);
    	    						table6.setNewPulmonaryCDCanceled1517(table6.getNewPulmonaryCDCanceled1517() + 1);
    	    						table6.setNewPulmonaryCDCanceled(table6.getNewPulmonaryCDCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setNewAllSLD(table6.getNewAllSLD() + 1);
    	    						table6.setNewPulmonaryCDSLD1517(table6.getNewPulmonaryCDSLD1517() + 1);
    	    						table6.setNewPulmonaryCDSLD(table6.getNewPulmonaryCDSLD() + 1);
    	    					}
    	    					
    	    				}
    	    				
    	    				else {
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setNewAllCured(table6.getNewAllCured() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDCured(table6.getNewPulmonaryCDCured() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setNewAllCompleted(table6.getNewAllCompleted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDCompleted(table6.getNewPulmonaryCDCompleted() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setNewAllDiedTB(table6.getNewAllDiedTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDDiedTB(table6.getNewPulmonaryCDDiedTB() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setNewAllDiedNotTB(table6.getNewAllDiedNotTB() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDDiedNotTB(table6.getNewPulmonaryCDDiedNotTB() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setNewAllFailed(table6.getNewAllFailed() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDFailed(table6.getNewPulmonaryCDFailed() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setNewAllDefaulted(table6.getNewAllDefaulted() + 1);
    	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
    	    						table6.setNewPulmonaryCDDefaulted(table6.getNewPulmonaryCDDefaulted() + 1);
    	    						table6.setNewPulmonaryCDEligible(table6.getNewPulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setNewAllTransferOut(table6.getNewAllTransferOut() + 1);
    	    						table6.setNewPulmonaryCDTransferOut(table6.getNewPulmonaryCDTransferOut() + 1);

    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setNewAllCanceled(table6.getNewAllCanceled() + 1);
    	    						table6.setNewPulmonaryCDCanceled(table6.getNewPulmonaryCDCanceled() + 1);
    	    						
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setNewAllSLD(table6.getNewAllSLD() + 1);
    	    						table6.setNewPulmonaryCDSLD(table6.getNewPulmonaryCDSLD() + 1);
    	    						
    	    					}
    	    				}
    	    			}
    	    		}
    	    		
    	    		//EP
    	    		else if(pulmonary!=null && !pulmonary) {
    	    			
    	    			table6.setNewExtrapulmonaryDetected(table6.getNewExtrapulmonaryDetected() + 1);
	    				
	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
	    					
	    					table6.setNewExtrapulmonaryDetected04(table6.getNewExtrapulmonaryDetected04() + 1);
	    					
	    					if(cured!=null && cured) {
	    						table6.setNewAllCured(table6.getNewAllCured() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryCured04(table6.getNewExtrapulmonaryCured04() + 1);
	    						table6.setNewExtrapulmonaryEligible04(table6.getNewExtrapulmonaryEligible04() + 1);
	    						table6.setNewExtrapulmonaryCured(table6.getNewExtrapulmonaryCured() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table6.setNewAllCompleted(table6.getNewAllCompleted() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryCompleted04(table6.getNewExtrapulmonaryCompleted04() + 1);
	    						table6.setNewExtrapulmonaryEligible04(table6.getNewExtrapulmonaryEligible04() + 1);
	    						table6.setNewExtrapulmonaryCompleted(table6.getNewExtrapulmonaryCompleted() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table6.setNewAllDiedTB(table6.getNewAllDiedTB() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryDiedTB04(table6.getNewExtrapulmonaryDiedTB04() + 1);
	    						table6.setNewExtrapulmonaryEligible04(table6.getNewExtrapulmonaryEligible04() + 1);
	    						table6.setNewExtrapulmonaryDiedTB(table6.getNewExtrapulmonaryDiedTB() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table6.setNewAllDiedNotTB(table6.getNewAllDiedNotTB() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryDiedNotTB04(table6.getNewExtrapulmonaryDiedNotTB04() + 1);
	    						table6.setNewExtrapulmonaryEligible04(table6.getNewExtrapulmonaryEligible04() + 1);
	    						table6.setNewExtrapulmonaryDiedNotTB(table6.getNewExtrapulmonaryDiedNotTB() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table6.setNewAllFailed(table6.getNewAllFailed() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryFailed04(table6.getNewExtrapulmonaryFailed04() + 1);
	    						table6.setNewExtrapulmonaryEligible04(table6.getNewExtrapulmonaryEligible04() + 1);
	    						table6.setNewExtrapulmonaryFailed(table6.getNewExtrapulmonaryFailed() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table6.setNewAllDefaulted(table6.getNewAllDefaulted() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryDefaulted04(table6.getNewExtrapulmonaryDefaulted04() + 1);
	    						table6.setNewExtrapulmonaryEligible04(table6.getNewExtrapulmonaryEligible04() + 1);
	    						table6.setNewExtrapulmonaryDefaulted(table6.getNewExtrapulmonaryDefaulted() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table6.setNewAllTransferOut(table6.getNewAllTransferOut() + 1);
	    						table6.setNewExtrapulmonaryTransferOut04(table6.getNewExtrapulmonaryTransferOut04() + 1);
	    						table6.setNewExtrapulmonaryTransferOut(table6.getNewExtrapulmonaryTransferOut() + 1);
	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table6.setNewAllCanceled(table6.getNewAllCanceled() + 1);
	    						table6.setNewExtrapulmonaryCanceled04(table6.getNewExtrapulmonaryCanceled04() + 1);
	    						table6.setNewExtrapulmonaryCanceled(table6.getNewExtrapulmonaryCanceled() + 1);
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table6.setNewAllSLD(table6.getNewAllSLD() + 1);
	    						table6.setNewExtrapulmonarySLD04(table6.getNewExtrapulmonarySLD04() + 1);
	    						table6.setNewExtrapulmonarySLD(table6.getNewExtrapulmonarySLD() + 1);
	    					}
	    					
	    					

	    				}
	    				
	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
	    					
	    					table6.setNewExtrapulmonaryDetected0514(table6.getNewExtrapulmonaryDetected0514() + 1);
	    					
	    					if(cured!=null && cured) {
	    						table6.setNewAllCured(table6.getNewAllCured() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryCured0514(table6.getNewExtrapulmonaryCured0514() + 1);
	    						table6.setNewExtrapulmonaryEligible0514(table6.getNewExtrapulmonaryEligible0514() + 1);
	    						table6.setNewExtrapulmonaryCured(table6.getNewExtrapulmonaryCured() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table6.setNewAllCompleted(table6.getNewAllCompleted() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryCompleted0514(table6.getNewExtrapulmonaryCompleted0514() + 1);
	    						table6.setNewExtrapulmonaryEligible0514(table6.getNewExtrapulmonaryEligible0514() + 1);
	    						table6.setNewExtrapulmonaryCompleted(table6.getNewExtrapulmonaryCompleted() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table6.setNewAllDiedTB(table6.getNewAllDiedTB() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryDiedTB0514(table6.getNewExtrapulmonaryDiedTB0514() + 1);
	    						table6.setNewExtrapulmonaryEligible0514(table6.getNewExtrapulmonaryEligible0514() + 1);
	    						table6.setNewExtrapulmonaryDiedTB(table6.getNewExtrapulmonaryDiedTB() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table6.setNewAllDiedNotTB(table6.getNewAllDiedNotTB() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryDiedNotTB0514(table6.getNewExtrapulmonaryDiedNotTB0514() + 1);
	    						table6.setNewExtrapulmonaryEligible0514(table6.getNewExtrapulmonaryEligible0514() + 1);
	    						table6.setNewExtrapulmonaryDiedNotTB(table6.getNewExtrapulmonaryDiedNotTB() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table6.setNewAllFailed(table6.getNewAllFailed() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryFailed0514(table6.getNewExtrapulmonaryFailed0514() + 1);
	    						table6.setNewExtrapulmonaryEligible0514(table6.getNewExtrapulmonaryEligible0514() + 1);
	    						table6.setNewExtrapulmonaryFailed(table6.getNewExtrapulmonaryFailed() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table6.setNewAllDefaulted(table6.getNewAllDefaulted() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryDefaulted0514(table6.getNewExtrapulmonaryDefaulted0514() + 1);
	    						table6.setNewExtrapulmonaryEligible0514(table6.getNewExtrapulmonaryEligible0514() + 1);
	    						table6.setNewExtrapulmonaryDefaulted(table6.getNewExtrapulmonaryDefaulted() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table6.setNewAllTransferOut(table6.getNewAllTransferOut() + 1);
	    						table6.setNewExtrapulmonaryTransferOut0514(table6.getNewExtrapulmonaryTransferOut0514() + 1);
	    						table6.setNewExtrapulmonaryTransferOut(table6.getNewExtrapulmonaryTransferOut() + 1);
	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table6.setNewAllCanceled(table6.getNewAllCanceled() + 1);
	    						table6.setNewExtrapulmonaryCanceled0514(table6.getNewExtrapulmonaryCanceled0514() + 1);
	    						table6.setNewExtrapulmonaryCanceled(table6.getNewExtrapulmonaryCanceled() + 1);
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table6.setNewAllSLD(table6.getNewAllSLD() + 1);
	    						table6.setNewExtrapulmonarySLD0514(table6.getNewExtrapulmonarySLD0514() + 1);
	    						table6.setNewExtrapulmonarySLD(table6.getNewExtrapulmonarySLD() + 1);
	    					}
	    					
	    				}
	    				
	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
	    					
	    					table6.setNewExtrapulmonaryDetected1517(table6.getNewExtrapulmonaryDetected1517() + 1);
	    					
	    					if(cured!=null && cured) {
	    						table6.setNewAllCured(table6.getNewAllCured() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryCured1517(table6.getNewExtrapulmonaryCured1517() + 1);
	    						table6.setNewExtrapulmonaryEligible1517(table6.getNewExtrapulmonaryEligible1517() + 1);
	    						table6.setNewExtrapulmonaryCured(table6.getNewExtrapulmonaryCured() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table6.setNewAllCompleted(table6.getNewAllCompleted() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryCompleted1517(table6.getNewExtrapulmonaryCompleted1517() + 1);
	    						table6.setNewExtrapulmonaryEligible1517(table6.getNewExtrapulmonaryEligible1517() + 1);
	    						table6.setNewExtrapulmonaryCompleted(table6.getNewExtrapulmonaryCompleted() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table6.setNewAllDiedTB(table6.getNewAllDiedTB() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryDiedTB1517(table6.getNewExtrapulmonaryDiedTB1517() + 1);
	    						table6.setNewExtrapulmonaryEligible1517(table6.getNewExtrapulmonaryEligible1517() + 1);
	    						table6.setNewExtrapulmonaryDiedTB(table6.getNewExtrapulmonaryDiedTB() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table6.setNewAllDiedNotTB(table6.getNewAllDiedNotTB() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryDiedNotTB1517(table6.getNewExtrapulmonaryDiedNotTB1517() + 1);
	    						table6.setNewExtrapulmonaryEligible1517(table6.getNewExtrapulmonaryEligible1517() + 1);
	    						table6.setNewExtrapulmonaryDiedNotTB(table6.getNewExtrapulmonaryDiedNotTB() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table6.setNewAllFailed(table6.getNewAllFailed() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryFailed1517(table6.getNewExtrapulmonaryFailed1517() + 1);
	    						table6.setNewExtrapulmonaryEligible1517(table6.getNewExtrapulmonaryEligible1517() + 1);
	    						table6.setNewExtrapulmonaryFailed(table6.getNewExtrapulmonaryFailed() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table6.setNewAllDefaulted(table6.getNewAllDefaulted() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryDefaulted1517(table6.getNewExtrapulmonaryDefaulted1517() + 1);
	    						table6.setNewExtrapulmonaryEligible1517(table6.getNewExtrapulmonaryEligible1517() + 1);
	    						table6.setNewExtrapulmonaryDefaulted(table6.getNewExtrapulmonaryDefaulted() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table6.setNewAllTransferOut(table6.getNewAllTransferOut() + 1);
	    						table6.setNewExtrapulmonaryTransferOut1517(table6.getNewExtrapulmonaryTransferOut1517() + 1);
	    						table6.setNewExtrapulmonaryTransferOut(table6.getNewExtrapulmonaryTransferOut() + 1);
	    						
	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table6.setNewAllCanceled(table6.getNewAllCanceled() + 1);
	    						table6.setNewExtrapulmonaryCanceled1517(table6.getNewExtrapulmonaryCanceled1517() + 1);
	    						table6.setNewExtrapulmonaryCanceled(table6.getNewExtrapulmonaryCanceled() + 1);
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table6.setNewAllSLD(table6.getNewAllSLD() + 1);
	    						table6.setNewExtrapulmonarySLD1517(table6.getNewExtrapulmonarySLD1517() + 1);
	    						table6.setNewExtrapulmonarySLD(table6.getNewExtrapulmonarySLD() + 1);
	    					}
	    					
	    				}
	    				
	    				else {
	    					
	    					if(cured!=null && cured) {
	    						table6.setNewAllCured(table6.getNewAllCured() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryCured(table6.getNewExtrapulmonaryCured() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table6.setNewAllCompleted(table6.getNewAllCompleted() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryCompleted(table6.getNewExtrapulmonaryCompleted() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table6.setNewAllDiedTB(table6.getNewAllDiedTB() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryDiedTB(table6.getNewExtrapulmonaryDiedTB() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table6.setNewAllDiedNotTB(table6.getNewAllDiedNotTB() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryDiedNotTB(table6.getNewExtrapulmonaryDiedNotTB() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table6.setNewAllFailed(table6.getNewAllFailed() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryFailed(table6.getNewExtrapulmonaryFailed() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table6.setNewAllDefaulted(table6.getNewAllDefaulted() + 1);
	    						table6.setNewAllEligible(table6.getNewAllEligible() + 1);
	    						table6.setNewExtrapulmonaryDefaulted(table6.getNewExtrapulmonaryDefaulted() + 1);
	    						table6.setNewExtrapulmonaryEligible(table6.getNewExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table6.setNewAllTransferOut(table6.getNewAllTransferOut() + 1);
	    						table6.setNewExtrapulmonaryTransferOut(table6.getNewExtrapulmonaryTransferOut() + 1);

	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table6.setNewAllCanceled(table6.getNewAllCanceled() + 1);
	    						table6.setNewExtrapulmonaryCanceled(table6.getNewExtrapulmonaryCanceled() + 1);
	    						
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table6.setNewAllSLD(table6.getNewAllSLD() + 1);
	    						table6.setNewExtrapulmonarySLD(table6.getNewExtrapulmonarySLD() + 1);
	    						
	    					}
	    				}
	    			
    	    		}

    	    	} 	
    	    	
    	    	//RELAPSE
    	    	else if(q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterRelapse1.conceptId")) || q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterRelapse2.conceptId"))) {
    	    		
    	    		table6.setRelapseAllDetected(table6.getRelapseAllDetected() + 1);
    	    		
    	    		//P
    	    		if(pulmonary!=null && pulmonary) {
    	    			
    	    			//BC
    	    			if(bacPositive) {
    	    				
    	    				table6.setRelapsePulmonaryBCDetected(table6.getRelapsePulmonaryBCDetected() + 1);
    	    				
    	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
    	    					
    	    					table6.setRelapsePulmonaryBCDetected04(table6.getRelapsePulmonaryBCDetected04() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setRelapseAllCured(table6.getRelapseAllCured() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCCured04(table6.getRelapsePulmonaryBCCured04() + 1);
    	    						table6.setRelapsePulmonaryBCEligible04(table6.getRelapsePulmonaryBCEligible04() + 1);
    	    						table6.setRelapsePulmonaryBCCured(table6.getRelapsePulmonaryBCCured() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    						
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setRelapseAllCompleted(table6.getRelapseAllCompleted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCCompleted04(table6.getRelapsePulmonaryBCCompleted04() + 1);
    	    						table6.setRelapsePulmonaryBCEligible04(table6.getRelapsePulmonaryBCEligible04() + 1);
    	    						table6.setRelapsePulmonaryBCCompleted(table6.getRelapsePulmonaryBCCompleted() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setRelapseAllDiedTB(table6.getRelapseAllDiedTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCDiedTB04(table6.getRelapsePulmonaryBCDiedTB04() + 1);
    	    						table6.setRelapsePulmonaryBCEligible04(table6.getRelapsePulmonaryBCEligible04() + 1);
    	    						table6.setRelapsePulmonaryBCDiedTB(table6.getRelapsePulmonaryBCDiedTB() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setRelapseAllDiedNotTB(table6.getRelapseAllDiedNotTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCDiedNotTB04(table6.getRelapsePulmonaryBCDiedNotTB04() + 1);
    	    						table6.setRelapsePulmonaryBCEligible04(table6.getRelapsePulmonaryBCEligible04() + 1);
    	    						table6.setRelapsePulmonaryBCDiedNotTB(table6.getRelapsePulmonaryBCDiedNotTB() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setRelapseAllFailed(table6.getRelapseAllFailed() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCFailed04(table6.getRelapsePulmonaryBCFailed04() + 1);
    	    						table6.setRelapsePulmonaryBCEligible04(table6.getRelapsePulmonaryBCEligible04() + 1);
    	    						table6.setRelapsePulmonaryBCFailed(table6.getRelapsePulmonaryBCFailed() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setRelapseAllDefaulted(table6.getRelapseAllDefaulted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCDefaulted04(table6.getRelapsePulmonaryBCDefaulted04() + 1);
    	    						table6.setRelapsePulmonaryBCEligible04(table6.getRelapsePulmonaryBCEligible04() + 1);
    	    						table6.setRelapsePulmonaryBCDefaulted(table6.getRelapsePulmonaryBCDefaulted() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setRelapseAllTransferOut(table6.getRelapseAllTransferOut() + 1);
    	    						table6.setRelapsePulmonaryBCTransferOut04(table6.getRelapsePulmonaryBCTransferOut04() + 1);
    	    						table6.setRelapsePulmonaryBCTransferOut(table6.getRelapsePulmonaryBCTransferOut() + 1);
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setRelapseAllCanceled(table6.getRelapseAllCanceled() + 1);
    	    						table6.setRelapsePulmonaryBCCanceled04(table6.getRelapsePulmonaryBCCanceled04() + 1);
    	    						table6.setRelapsePulmonaryBCCanceled(table6.getRelapsePulmonaryBCCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setRelapseAllSLD(table6.getRelapseAllSLD() + 1);
    	    						table6.setRelapsePulmonaryBCSLD04(table6.getRelapsePulmonaryBCSLD04() + 1);
    	    						table6.setRelapsePulmonaryBCSLD(table6.getRelapsePulmonaryBCSLD() + 1);
    	    					}
    	    					
    	    					

    	    				}
    	    				
    	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
    	    					
    	    					table6.setRelapsePulmonaryBCDetected0514(table6.getRelapsePulmonaryBCDetected0514() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setRelapseAllCured(table6.getRelapseAllCured() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCCured0514(table6.getRelapsePulmonaryBCCured0514() + 1);
    	    						table6.setRelapsePulmonaryBCEligible0514(table6.getRelapsePulmonaryBCEligible0514() + 1);
    	    						table6.setRelapsePulmonaryBCCured(table6.getRelapsePulmonaryBCCured() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setRelapseAllCompleted(table6.getRelapseAllCompleted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCCompleted0514(table6.getRelapsePulmonaryBCCompleted0514() + 1);
    	    						table6.setRelapsePulmonaryBCEligible0514(table6.getRelapsePulmonaryBCEligible0514() + 1);
    	    						table6.setRelapsePulmonaryBCCompleted(table6.getRelapsePulmonaryBCCompleted() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setRelapseAllDiedTB(table6.getRelapseAllDiedTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCDiedTB0514(table6.getRelapsePulmonaryBCDiedTB0514() + 1);
    	    						table6.setRelapsePulmonaryBCEligible0514(table6.getRelapsePulmonaryBCEligible0514() + 1);
    	    						table6.setRelapsePulmonaryBCDiedTB(table6.getRelapsePulmonaryBCDiedTB() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setRelapseAllDiedNotTB(table6.getRelapseAllDiedNotTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCDiedNotTB0514(table6.getRelapsePulmonaryBCDiedNotTB0514() + 1);
    	    						table6.setRelapsePulmonaryBCEligible0514(table6.getRelapsePulmonaryBCEligible0514() + 1);
    	    						table6.setRelapsePulmonaryBCDiedNotTB(table6.getRelapsePulmonaryBCDiedNotTB() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setRelapseAllFailed(table6.getRelapseAllFailed() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCFailed0514(table6.getRelapsePulmonaryBCFailed0514() + 1);
    	    						table6.setRelapsePulmonaryBCEligible0514(table6.getRelapsePulmonaryBCEligible0514() + 1);
    	    						table6.setRelapsePulmonaryBCFailed(table6.getRelapsePulmonaryBCFailed() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setRelapseAllDefaulted(table6.getRelapseAllDefaulted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCDefaulted0514(table6.getRelapsePulmonaryBCDefaulted0514() + 1);
    	    						table6.setRelapsePulmonaryBCEligible0514(table6.getRelapsePulmonaryBCEligible0514() + 1);
    	    						table6.setRelapsePulmonaryBCDefaulted(table6.getRelapsePulmonaryBCDefaulted() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setRelapseAllTransferOut(table6.getRelapseAllTransferOut() + 1);
    	    						table6.setRelapsePulmonaryBCTransferOut0514(table6.getRelapsePulmonaryBCTransferOut0514() + 1);
    	    						table6.setRelapsePulmonaryBCTransferOut(table6.getRelapsePulmonaryBCTransferOut() + 1);
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setRelapseAllCanceled(table6.getRelapseAllCanceled() + 1);
    	    						table6.setRelapsePulmonaryBCCanceled0514(table6.getRelapsePulmonaryBCCanceled0514() + 1);
    	    						table6.setRelapsePulmonaryBCCanceled(table6.getRelapsePulmonaryBCCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setRelapseAllSLD(table6.getRelapseAllSLD() + 1);
    	    						table6.setRelapsePulmonaryBCSLD0514(table6.getRelapsePulmonaryBCSLD0514() + 1);
    	    						table6.setRelapsePulmonaryBCSLD(table6.getRelapsePulmonaryBCSLD() + 1);
    	    					}
    	    					
    	    				}
    	    				
    	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
    	    					
    	    					table6.setRelapsePulmonaryBCDetected1517(table6.getRelapsePulmonaryBCDetected1517() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setRelapseAllCured(table6.getRelapseAllCured() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCCured1517(table6.getRelapsePulmonaryBCCured1517() + 1);
    	    						table6.setRelapsePulmonaryBCEligible1517(table6.getRelapsePulmonaryBCEligible1517() + 1);
    	    						table6.setRelapsePulmonaryBCCured(table6.getRelapsePulmonaryBCCured() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setRelapseAllCompleted(table6.getRelapseAllCompleted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCCompleted1517(table6.getRelapsePulmonaryBCCompleted1517() + 1);
    	    						table6.setRelapsePulmonaryBCEligible1517(table6.getRelapsePulmonaryBCEligible1517() + 1);
    	    						table6.setRelapsePulmonaryBCCompleted(table6.getRelapsePulmonaryBCCompleted() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setRelapseAllDiedTB(table6.getRelapseAllDiedTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCDiedTB1517(table6.getRelapsePulmonaryBCDiedTB1517() + 1);
    	    						table6.setRelapsePulmonaryBCEligible1517(table6.getRelapsePulmonaryBCEligible1517() + 1);
    	    						table6.setRelapsePulmonaryBCDiedTB(table6.getRelapsePulmonaryBCDiedTB() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setRelapseAllDiedNotTB(table6.getRelapseAllDiedNotTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCDiedNotTB1517(table6.getRelapsePulmonaryBCDiedNotTB1517() + 1);
    	    						table6.setRelapsePulmonaryBCEligible1517(table6.getRelapsePulmonaryBCEligible1517() + 1);
    	    						table6.setRelapsePulmonaryBCDiedNotTB(table6.getRelapsePulmonaryBCDiedNotTB() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setRelapseAllFailed(table6.getRelapseAllFailed() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCFailed1517(table6.getRelapsePulmonaryBCFailed1517() + 1);
    	    						table6.setRelapsePulmonaryBCEligible1517(table6.getRelapsePulmonaryBCEligible1517() + 1);
    	    						table6.setRelapsePulmonaryBCFailed(table6.getRelapsePulmonaryBCFailed() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setRelapseAllDefaulted(table6.getRelapseAllDefaulted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCDefaulted1517(table6.getRelapsePulmonaryBCDefaulted1517() + 1);
    	    						table6.setRelapsePulmonaryBCEligible1517(table6.getRelapsePulmonaryBCEligible1517() + 1);
    	    						table6.setRelapsePulmonaryBCDefaulted(table6.getRelapsePulmonaryBCDefaulted() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setRelapseAllTransferOut(table6.getRelapseAllTransferOut() + 1);
    	    						table6.setRelapsePulmonaryBCTransferOut1517(table6.getRelapsePulmonaryBCTransferOut1517() + 1);
    	    						table6.setRelapsePulmonaryBCTransferOut(table6.getRelapsePulmonaryBCTransferOut() + 1);
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setRelapseAllCanceled(table6.getRelapseAllCanceled() + 1);
    	    						table6.setRelapsePulmonaryBCCanceled1517(table6.getRelapsePulmonaryBCCanceled1517() + 1);
    	    						table6.setRelapsePulmonaryBCCanceled(table6.getRelapsePulmonaryBCCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setRelapseAllSLD(table6.getRelapseAllSLD() + 1);
    	    						table6.setRelapsePulmonaryBCSLD1517(table6.getRelapsePulmonaryBCSLD1517() + 1);
    	    						table6.setRelapsePulmonaryBCSLD(table6.getRelapsePulmonaryBCSLD() + 1);
    	    					}
    	    					
    	    				}
    	    				
    	    				else {
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setRelapseAllCured(table6.getRelapseAllCured() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCCured(table6.getRelapsePulmonaryBCCured() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setRelapseAllCompleted(table6.getRelapseAllCompleted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCCompleted(table6.getRelapsePulmonaryBCCompleted() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setRelapseAllDiedTB(table6.getRelapseAllDiedTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCDiedTB(table6.getRelapsePulmonaryBCDiedTB() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setRelapseAllDiedNotTB(table6.getRelapseAllDiedNotTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCDiedNotTB(table6.getRelapsePulmonaryBCDiedNotTB() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setRelapseAllFailed(table6.getRelapseAllFailed() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCFailed(table6.getRelapsePulmonaryBCFailed() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setRelapseAllDefaulted(table6.getRelapseAllDefaulted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryBCDefaulted(table6.getRelapsePulmonaryBCDefaulted() + 1);
    	    						table6.setRelapsePulmonaryBCEligible(table6.getRelapsePulmonaryBCEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setRelapseAllTransferOut(table6.getRelapseAllTransferOut() + 1);
    	    						table6.setRelapsePulmonaryBCTransferOut(table6.getRelapsePulmonaryBCTransferOut() + 1);

    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setRelapseAllCanceled(table6.getRelapseAllCanceled() + 1);
    	    						table6.setRelapsePulmonaryBCCanceled(table6.getRelapsePulmonaryBCCanceled() + 1);
    	    						
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setRelapseAllSLD(table6.getRelapseAllSLD() + 1);
    	    						table6.setRelapsePulmonaryBCSLD(table6.getRelapsePulmonaryBCSLD() + 1);
    	    						
    	    					}
    	    				}
    	    				
    	    			}
    	    			
    	    			//CD
    	    			else {
    	    				
    	    				table6.setRelapsePulmonaryCDDetected(table6.getRelapsePulmonaryCDDetected() + 1);
    	    				
    	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
    	    					
    	    					table6.setRelapsePulmonaryCDDetected04(table6.getRelapsePulmonaryCDDetected04() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setRelapseAllCured(table6.getRelapseAllCured() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDCured04(table6.getRelapsePulmonaryCDCured04() + 1);
    	    						table6.setRelapsePulmonaryCDEligible04(table6.getRelapsePulmonaryCDEligible04() + 1);
    	    						table6.setRelapsePulmonaryCDCured(table6.getRelapsePulmonaryCDCured() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setRelapseAllCompleted(table6.getRelapseAllCompleted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDCompleted04(table6.getRelapsePulmonaryCDCompleted04() + 1);
    	    						table6.setRelapsePulmonaryCDEligible04(table6.getRelapsePulmonaryCDEligible04() + 1);
    	    						table6.setRelapsePulmonaryCDCompleted(table6.getRelapsePulmonaryCDCompleted() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setRelapseAllDiedTB(table6.getRelapseAllDiedTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDDiedTB04(table6.getRelapsePulmonaryCDDiedTB04() + 1);
    	    						table6.setRelapsePulmonaryCDEligible04(table6.getRelapsePulmonaryCDEligible04() + 1);
    	    						table6.setRelapsePulmonaryCDDiedTB(table6.getRelapsePulmonaryCDDiedTB() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setRelapseAllDiedNotTB(table6.getRelapseAllDiedNotTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDDiedNotTB04(table6.getRelapsePulmonaryCDDiedNotTB04() + 1);
    	    						table6.setRelapsePulmonaryCDEligible04(table6.getRelapsePulmonaryCDEligible04() + 1);
    	    						table6.setRelapsePulmonaryCDDiedNotTB(table6.getRelapsePulmonaryCDDiedNotTB() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setRelapseAllFailed(table6.getRelapseAllFailed() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDFailed04(table6.getRelapsePulmonaryCDFailed04() + 1);
    	    						table6.setRelapsePulmonaryCDEligible04(table6.getRelapsePulmonaryCDEligible04() + 1);
    	    						table6.setRelapsePulmonaryCDFailed(table6.getRelapsePulmonaryCDFailed() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setRelapseAllDefaulted(table6.getRelapseAllDefaulted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDDefaulted04(table6.getRelapsePulmonaryCDDefaulted04() + 1);
    	    						table6.setRelapsePulmonaryCDEligible04(table6.getRelapsePulmonaryCDEligible04() + 1);
    	    						table6.setRelapsePulmonaryCDDefaulted(table6.getRelapsePulmonaryCDDefaulted() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setRelapseAllTransferOut(table6.getRelapseAllTransferOut() + 1);
    	    						table6.setRelapsePulmonaryCDTransferOut04(table6.getRelapsePulmonaryCDTransferOut04() + 1);
    	    						table6.setRelapsePulmonaryCDTransferOut(table6.getRelapsePulmonaryCDTransferOut() + 1);
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setRelapseAllCanceled(table6.getRelapseAllCanceled() + 1);
    	    						table6.setRelapsePulmonaryCDCanceled04(table6.getRelapsePulmonaryCDCanceled04() + 1);
    	    						table6.setRelapsePulmonaryCDCanceled(table6.getRelapsePulmonaryCDCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setRelapseAllSLD(table6.getRelapseAllSLD() + 1);
    	    						table6.setRelapsePulmonaryCDSLD04(table6.getRelapsePulmonaryCDSLD04() + 1);
    	    						table6.setRelapsePulmonaryCDSLD(table6.getRelapsePulmonaryCDSLD() + 1);
    	    					}
    	    					
    	    					

    	    				}
    	    				
    	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
    	    					
    	    					table6.setRelapsePulmonaryCDDetected0514(table6.getRelapsePulmonaryCDDetected0514() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setRelapseAllCured(table6.getRelapseAllCured() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDCured0514(table6.getRelapsePulmonaryCDCured0514() + 1);
    	    						table6.setRelapsePulmonaryCDEligible0514(table6.getRelapsePulmonaryCDEligible0514() + 1);
    	    						table6.setRelapsePulmonaryCDCured(table6.getRelapsePulmonaryCDCured() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setRelapseAllCompleted(table6.getRelapseAllCompleted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDCompleted0514(table6.getRelapsePulmonaryCDCompleted0514() + 1);
    	    						table6.setRelapsePulmonaryCDEligible0514(table6.getRelapsePulmonaryCDEligible0514() + 1);
    	    						table6.setRelapsePulmonaryCDCompleted(table6.getRelapsePulmonaryCDCompleted() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setRelapseAllDiedTB(table6.getRelapseAllDiedTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDDiedTB0514(table6.getRelapsePulmonaryCDDiedTB0514() + 1);
    	    						table6.setRelapsePulmonaryCDEligible0514(table6.getRelapsePulmonaryCDEligible0514() + 1);
    	    						table6.setRelapsePulmonaryCDDiedTB(table6.getRelapsePulmonaryCDDiedTB() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setRelapseAllDiedNotTB(table6.getRelapseAllDiedNotTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDDiedNotTB0514(table6.getRelapsePulmonaryCDDiedNotTB0514() + 1);
    	    						table6.setRelapsePulmonaryCDEligible0514(table6.getRelapsePulmonaryCDEligible0514() + 1);
    	    						table6.setRelapsePulmonaryCDDiedNotTB(table6.getRelapsePulmonaryCDDiedNotTB() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setRelapseAllFailed(table6.getRelapseAllFailed() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDFailed0514(table6.getRelapsePulmonaryCDFailed0514() + 1);
    	    						table6.setRelapsePulmonaryCDEligible0514(table6.getRelapsePulmonaryCDEligible0514() + 1);
    	    						table6.setRelapsePulmonaryCDFailed(table6.getRelapsePulmonaryCDFailed() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setRelapseAllDefaulted(table6.getRelapseAllDefaulted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDDefaulted0514(table6.getRelapsePulmonaryCDDefaulted0514() + 1);
    	    						table6.setRelapsePulmonaryCDEligible0514(table6.getRelapsePulmonaryCDEligible0514() + 1);
    	    						table6.setRelapsePulmonaryCDDefaulted(table6.getRelapsePulmonaryCDDefaulted() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setRelapseAllTransferOut(table6.getRelapseAllTransferOut() + 1);
    	    						table6.setRelapsePulmonaryCDTransferOut0514(table6.getRelapsePulmonaryCDTransferOut0514() + 1);
    	    						table6.setRelapsePulmonaryCDTransferOut(table6.getRelapsePulmonaryCDTransferOut() + 1);
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setRelapseAllCanceled(table6.getRelapseAllCanceled() + 1);
    	    						table6.setRelapsePulmonaryCDCanceled0514(table6.getRelapsePulmonaryCDCanceled0514() + 1);
    	    						table6.setRelapsePulmonaryCDCanceled(table6.getRelapsePulmonaryCDCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setRelapseAllSLD(table6.getRelapseAllSLD() + 1);
    	    						table6.setRelapsePulmonaryCDSLD0514(table6.getRelapsePulmonaryCDSLD0514() + 1);
    	    						table6.setRelapsePulmonaryCDSLD(table6.getRelapsePulmonaryCDSLD() + 1);
    	    					}
    	    					
    	    				}
    	    				
    	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
    	    					
    	    					table6.setRelapsePulmonaryCDDetected1517(table6.getRelapsePulmonaryCDDetected1517() + 1);
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setRelapseAllCured(table6.getRelapseAllCured() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDCured1517(table6.getRelapsePulmonaryCDCured1517() + 1);
    	    						table6.setRelapsePulmonaryCDEligible1517(table6.getRelapsePulmonaryCDEligible1517() + 1);
    	    						table6.setRelapsePulmonaryCDCured(table6.getRelapsePulmonaryCDCured() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setRelapseAllCompleted(table6.getRelapseAllCompleted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDCompleted1517(table6.getRelapsePulmonaryCDCompleted1517() + 1);
    	    						table6.setRelapsePulmonaryCDEligible1517(table6.getRelapsePulmonaryCDEligible1517() + 1);
    	    						table6.setRelapsePulmonaryCDCompleted(table6.getRelapsePulmonaryCDCompleted() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setRelapseAllDiedTB(table6.getRelapseAllDiedTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDDiedTB1517(table6.getRelapsePulmonaryCDDiedTB1517() + 1);
    	    						table6.setRelapsePulmonaryCDEligible1517(table6.getRelapsePulmonaryCDEligible1517() + 1);
    	    						table6.setRelapsePulmonaryCDDiedTB(table6.getRelapsePulmonaryCDDiedTB() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setRelapseAllDiedNotTB(table6.getRelapseAllDiedNotTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDDiedNotTB1517(table6.getRelapsePulmonaryCDDiedNotTB1517() + 1);
    	    						table6.setRelapsePulmonaryCDEligible1517(table6.getRelapsePulmonaryCDEligible1517() + 1);
    	    						table6.setRelapsePulmonaryCDDiedNotTB(table6.getRelapsePulmonaryCDDiedNotTB() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setRelapseAllFailed(table6.getRelapseAllFailed() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDFailed1517(table6.getRelapsePulmonaryCDFailed1517() + 1);
    	    						table6.setRelapsePulmonaryCDEligible1517(table6.getRelapsePulmonaryCDEligible1517() + 1);
    	    						table6.setRelapsePulmonaryCDFailed(table6.getRelapsePulmonaryCDFailed() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setRelapseAllDefaulted(table6.getRelapseAllDefaulted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDDefaulted1517(table6.getRelapsePulmonaryCDDefaulted1517() + 1);
    	    						table6.setRelapsePulmonaryCDEligible1517(table6.getRelapsePulmonaryCDEligible1517() + 1);
    	    						table6.setRelapsePulmonaryCDDefaulted(table6.getRelapsePulmonaryCDDefaulted() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setRelapseAllTransferOut(table6.getRelapseAllTransferOut() + 1);
    	    						table6.setRelapsePulmonaryCDTransferOut1517(table6.getRelapsePulmonaryCDTransferOut1517() + 1);
    	    						table6.setRelapsePulmonaryCDTransferOut(table6.getRelapsePulmonaryCDTransferOut() + 1);
    	    						
    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setRelapseAllCanceled(table6.getRelapseAllCanceled() + 1);
    	    						table6.setRelapsePulmonaryCDCanceled1517(table6.getRelapsePulmonaryCDCanceled1517() + 1);
    	    						table6.setRelapsePulmonaryCDCanceled(table6.getRelapsePulmonaryCDCanceled() + 1);
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setRelapseAllSLD(table6.getRelapseAllSLD() + 1);
    	    						table6.setRelapsePulmonaryCDSLD1517(table6.getRelapsePulmonaryCDSLD1517() + 1);
    	    						table6.setRelapsePulmonaryCDSLD(table6.getRelapsePulmonaryCDSLD() + 1);
    	    					}
    	    					
    	    				}
    	    				
    	    				else {
    	    					
    	    					if(cured!=null && cured) {
    	    						table6.setRelapseAllCured(table6.getRelapseAllCured() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDCured(table6.getRelapsePulmonaryCDCured() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (txCompleted!=null && txCompleted) {
    	    						table6.setRelapseAllCompleted(table6.getRelapseAllCompleted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDCompleted(table6.getRelapsePulmonaryCDCompleted() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedTB!=null && diedTB) {
    	    						table6.setRelapseAllDiedTB(table6.getRelapseAllDiedTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDDiedTB(table6.getRelapsePulmonaryCDDiedTB() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (diedNotTB!=null && diedNotTB) {
    	    						table6.setRelapseAllDiedNotTB(table6.getRelapseAllDiedNotTB() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDDiedNotTB(table6.getRelapsePulmonaryCDDiedNotTB() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (failed!=null && failed) {
    	    						table6.setRelapseAllFailed(table6.getRelapseAllFailed() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDFailed(table6.getRelapsePulmonaryCDFailed() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (defaulted!=null && defaulted) {
    	    						table6.setRelapseAllDefaulted(table6.getRelapseAllDefaulted() + 1);
    	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
    	    						table6.setRelapsePulmonaryCDDefaulted(table6.getRelapsePulmonaryCDDefaulted() + 1);
    	    						table6.setRelapsePulmonaryCDEligible(table6.getRelapsePulmonaryCDEligible() + 1);
    	    					}
    	    					
    	    					else if (transferOut!=null && transferOut) {
    	    						table6.setRelapseAllTransferOut(table6.getRelapseAllTransferOut() + 1);
    	    						table6.setRelapsePulmonaryCDTransferOut(table6.getRelapsePulmonaryCDTransferOut() + 1);

    	    					}
    	    					
    	    					else if (canceled!=null && canceled) {
    	    						table6.setRelapseAllCanceled(table6.getRelapseAllCanceled() + 1);
    	    						table6.setRelapsePulmonaryCDCanceled(table6.getRelapsePulmonaryCDCanceled() + 1);
    	    						
    	    					}
    	    					
    	    					else if (sld!=null && sld) {
    	    						table6.setRelapseAllSLD(table6.getRelapseAllSLD() + 1);
    	    						table6.setRelapsePulmonaryCDSLD(table6.getRelapsePulmonaryCDSLD() + 1);
    	    						
    	    					}
    	    				}
    	    			}
    	    		}
    	    		
    	    		//EP
    	    		else if(pulmonary!=null && !pulmonary) {
    	    			
    	    			table6.setRelapseExtrapulmonaryDetected(table6.getRelapseExtrapulmonaryDetected() + 1);
	    				
	    				if(ageAtRegistration >= 0 && ageAtRegistration < 5) {
	    					
	    					table6.setRelapseExtrapulmonaryDetected04(table6.getRelapseExtrapulmonaryDetected04() + 1);
	    					
	    					if(cured!=null && cured) {
	    						table6.setRelapseAllCured(table6.getRelapseAllCured() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryCured04(table6.getRelapseExtrapulmonaryCured04() + 1);
	    						table6.setRelapseExtrapulmonaryEligible04(table6.getRelapseExtrapulmonaryEligible04() + 1);
	    						table6.setRelapseExtrapulmonaryCured(table6.getRelapseExtrapulmonaryCured() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table6.setRelapseAllCompleted(table6.getRelapseAllCompleted() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryCompleted04(table6.getRelapseExtrapulmonaryCompleted04() + 1);
	    						table6.setRelapseExtrapulmonaryEligible04(table6.getRelapseExtrapulmonaryEligible04() + 1);
	    						table6.setRelapseExtrapulmonaryCompleted(table6.getRelapseExtrapulmonaryCompleted() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table6.setRelapseAllDiedTB(table6.getRelapseAllDiedTB() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryDiedTB04(table6.getRelapseExtrapulmonaryDiedTB04() + 1);
	    						table6.setRelapseExtrapulmonaryEligible04(table6.getRelapseExtrapulmonaryEligible04() + 1);
	    						table6.setRelapseExtrapulmonaryDiedTB(table6.getRelapseExtrapulmonaryDiedTB() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table6.setRelapseAllDiedNotTB(table6.getRelapseAllDiedNotTB() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryDiedNotTB04(table6.getRelapseExtrapulmonaryDiedNotTB04() + 1);
	    						table6.setRelapseExtrapulmonaryEligible04(table6.getRelapseExtrapulmonaryEligible04() + 1);
	    						table6.setRelapseExtrapulmonaryDiedNotTB(table6.getRelapseExtrapulmonaryDiedNotTB() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table6.setRelapseAllFailed(table6.getRelapseAllFailed() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryFailed04(table6.getRelapseExtrapulmonaryFailed04() + 1);
	    						table6.setRelapseExtrapulmonaryEligible04(table6.getRelapseExtrapulmonaryEligible04() + 1);
	    						table6.setRelapseExtrapulmonaryFailed(table6.getRelapseExtrapulmonaryFailed() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table6.setRelapseAllDefaulted(table6.getRelapseAllDefaulted() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryDefaulted04(table6.getRelapseExtrapulmonaryDefaulted04() + 1);
	    						table6.setRelapseExtrapulmonaryEligible04(table6.getRelapseExtrapulmonaryEligible04() + 1);
	    						table6.setRelapseExtrapulmonaryDefaulted(table6.getRelapseExtrapulmonaryDefaulted() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table6.setRelapseAllTransferOut(table6.getRelapseAllTransferOut() + 1);
	    						table6.setRelapseExtrapulmonaryTransferOut04(table6.getRelapseExtrapulmonaryTransferOut04() + 1);
	    						table6.setRelapseExtrapulmonaryTransferOut(table6.getRelapseExtrapulmonaryTransferOut() + 1);
	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table6.setRelapseAllCanceled(table6.getRelapseAllCanceled() + 1);
	    						table6.setRelapseExtrapulmonaryCanceled04(table6.getRelapseExtrapulmonaryCanceled04() + 1);
	    						table6.setRelapseExtrapulmonaryCanceled(table6.getRelapseExtrapulmonaryCanceled() + 1);
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table6.setRelapseAllSLD(table6.getRelapseAllSLD() + 1);
	    						table6.setRelapseExtrapulmonarySLD04(table6.getRelapseExtrapulmonarySLD04() + 1);
	    						table6.setRelapseExtrapulmonarySLD(table6.getRelapseExtrapulmonarySLD() + 1);
	    					}
	    					
	    					

	    				}
	    				
	    				else if(ageAtRegistration >= 5 && ageAtRegistration < 15) {
	    					
	    					table6.setRelapseExtrapulmonaryDetected0514(table6.getRelapseExtrapulmonaryDetected0514() + 1);
	    					
	    					if(cured!=null && cured) {
	    						table6.setRelapseAllCured(table6.getRelapseAllCured() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryCured0514(table6.getRelapseExtrapulmonaryCured0514() + 1);
	    						table6.setRelapseExtrapulmonaryEligible0514(table6.getRelapseExtrapulmonaryEligible0514() + 1);
	    						table6.setRelapseExtrapulmonaryCured(table6.getRelapseExtrapulmonaryCured() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table6.setRelapseAllCompleted(table6.getRelapseAllCompleted() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryCompleted0514(table6.getRelapseExtrapulmonaryCompleted0514() + 1);
	    						table6.setRelapseExtrapulmonaryEligible0514(table6.getRelapseExtrapulmonaryEligible0514() + 1);
	    						table6.setRelapseExtrapulmonaryCompleted(table6.getRelapseExtrapulmonaryCompleted() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table6.setRelapseAllDiedTB(table6.getRelapseAllDiedTB() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryDiedTB0514(table6.getRelapseExtrapulmonaryDiedTB0514() + 1);
	    						table6.setRelapseExtrapulmonaryEligible0514(table6.getRelapseExtrapulmonaryEligible0514() + 1);
	    						table6.setRelapseExtrapulmonaryDiedTB(table6.getRelapseExtrapulmonaryDiedTB() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table6.setRelapseAllDiedNotTB(table6.getRelapseAllDiedNotTB() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryDiedNotTB0514(table6.getRelapseExtrapulmonaryDiedNotTB0514() + 1);
	    						table6.setRelapseExtrapulmonaryEligible0514(table6.getRelapseExtrapulmonaryEligible0514() + 1);
	    						table6.setRelapseExtrapulmonaryDiedNotTB(table6.getRelapseExtrapulmonaryDiedNotTB() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table6.setRelapseAllFailed(table6.getRelapseAllFailed() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryFailed0514(table6.getRelapseExtrapulmonaryFailed0514() + 1);
	    						table6.setRelapseExtrapulmonaryEligible0514(table6.getRelapseExtrapulmonaryEligible0514() + 1);
	    						table6.setRelapseExtrapulmonaryFailed(table6.getRelapseExtrapulmonaryFailed() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table6.setRelapseAllDefaulted(table6.getRelapseAllDefaulted() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryDefaulted0514(table6.getRelapseExtrapulmonaryDefaulted0514() + 1);
	    						table6.setRelapseExtrapulmonaryEligible0514(table6.getRelapseExtrapulmonaryEligible0514() + 1);
	    						table6.setRelapseExtrapulmonaryDefaulted(table6.getRelapseExtrapulmonaryDefaulted() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table6.setRelapseAllTransferOut(table6.getRelapseAllTransferOut() + 1);
	    						table6.setRelapseExtrapulmonaryTransferOut0514(table6.getRelapseExtrapulmonaryTransferOut0514() + 1);
	    						table6.setRelapseExtrapulmonaryTransferOut(table6.getRelapseExtrapulmonaryTransferOut() + 1);
	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table6.setRelapseAllCanceled(table6.getRelapseAllCanceled() + 1);
	    						table6.setRelapseExtrapulmonaryCanceled0514(table6.getRelapseExtrapulmonaryCanceled0514() + 1);
	    						table6.setRelapseExtrapulmonaryCanceled(table6.getRelapseExtrapulmonaryCanceled() + 1);
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table6.setRelapseAllSLD(table6.getRelapseAllSLD() + 1);
	    						table6.setRelapseExtrapulmonarySLD0514(table6.getRelapseExtrapulmonarySLD0514() + 1);
	    						table6.setRelapseExtrapulmonarySLD(table6.getRelapseExtrapulmonarySLD() + 1);
	    					}
	    					
	    				}
	    				
	    				else if(ageAtRegistration >= 15 && ageAtRegistration < 18) {
	    					
	    					table6.setRelapseExtrapulmonaryDetected1517(table6.getRelapseExtrapulmonaryDetected1517() + 1);
	    					
	    					if(cured!=null && cured) {
	    						table6.setRelapseAllCured(table6.getRelapseAllCured() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryCured1517(table6.getRelapseExtrapulmonaryCured1517() + 1);
	    						table6.setRelapseExtrapulmonaryEligible1517(table6.getRelapseExtrapulmonaryEligible1517() + 1);
	    						table6.setRelapseExtrapulmonaryCured(table6.getRelapseExtrapulmonaryCured() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table6.setRelapseAllCompleted(table6.getRelapseAllCompleted() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryCompleted1517(table6.getRelapseExtrapulmonaryCompleted1517() + 1);
	    						table6.setRelapseExtrapulmonaryEligible1517(table6.getRelapseExtrapulmonaryEligible1517() + 1);
	    						table6.setRelapseExtrapulmonaryCompleted(table6.getRelapseExtrapulmonaryCompleted() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table6.setRelapseAllDiedTB(table6.getRelapseAllDiedTB() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryDiedTB1517(table6.getRelapseExtrapulmonaryDiedTB1517() + 1);
	    						table6.setRelapseExtrapulmonaryEligible1517(table6.getRelapseExtrapulmonaryEligible1517() + 1);
	    						table6.setRelapseExtrapulmonaryDiedTB(table6.getRelapseExtrapulmonaryDiedTB() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table6.setRelapseAllDiedNotTB(table6.getRelapseAllDiedNotTB() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryDiedNotTB1517(table6.getRelapseExtrapulmonaryDiedNotTB1517() + 1);
	    						table6.setRelapseExtrapulmonaryEligible1517(table6.getRelapseExtrapulmonaryEligible1517() + 1);
	    						table6.setRelapseExtrapulmonaryDiedNotTB(table6.getRelapseExtrapulmonaryDiedNotTB() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table6.setRelapseAllFailed(table6.getRelapseAllFailed() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryFailed1517(table6.getRelapseExtrapulmonaryFailed1517() + 1);
	    						table6.setRelapseExtrapulmonaryEligible1517(table6.getRelapseExtrapulmonaryEligible1517() + 1);
	    						table6.setRelapseExtrapulmonaryFailed(table6.getRelapseExtrapulmonaryFailed() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table6.setRelapseAllDefaulted(table6.getRelapseAllDefaulted() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryDefaulted1517(table6.getRelapseExtrapulmonaryDefaulted1517() + 1);
	    						table6.setRelapseExtrapulmonaryEligible1517(table6.getRelapseExtrapulmonaryEligible1517() + 1);
	    						table6.setRelapseExtrapulmonaryDefaulted(table6.getRelapseExtrapulmonaryDefaulted() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table6.setRelapseAllTransferOut(table6.getRelapseAllTransferOut() + 1);
	    						table6.setRelapseExtrapulmonaryTransferOut1517(table6.getRelapseExtrapulmonaryTransferOut1517() + 1);
	    						table6.setRelapseExtrapulmonaryTransferOut(table6.getRelapseExtrapulmonaryTransferOut() + 1);
	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table6.setRelapseAllCanceled(table6.getRelapseAllCanceled() + 1);
	    						table6.setRelapseExtrapulmonaryCanceled1517(table6.getRelapseExtrapulmonaryCanceled1517() + 1);
	    						table6.setRelapseExtrapulmonaryCanceled(table6.getRelapseExtrapulmonaryCanceled() + 1);
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table6.setRelapseAllSLD(table6.getRelapseAllSLD() + 1);
	    						table6.setRelapseExtrapulmonarySLD1517(table6.getRelapseExtrapulmonarySLD1517() + 1);
	    						table6.setRelapseExtrapulmonarySLD(table6.getRelapseExtrapulmonarySLD() + 1);
	    					}
	    					
	    				}
	    				
	    				else {
	    					
	    					if(cured!=null && cured) {
	    						table6.setRelapseAllCured(table6.getRelapseAllCured() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryCured(table6.getRelapseExtrapulmonaryCured() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (txCompleted!=null && txCompleted) {
	    						table6.setRelapseAllCompleted(table6.getRelapseAllCompleted() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryCompleted(table6.getRelapseExtrapulmonaryCompleted() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedTB!=null && diedTB) {
	    						table6.setRelapseAllDiedTB(table6.getRelapseAllDiedTB() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryDiedTB(table6.getRelapseExtrapulmonaryDiedTB() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (diedNotTB!=null && diedNotTB) {
	    						table6.setRelapseAllDiedNotTB(table6.getRelapseAllDiedNotTB() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryDiedNotTB(table6.getRelapseExtrapulmonaryDiedNotTB() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (failed!=null && failed) {
	    						table6.setRelapseAllFailed(table6.getRelapseAllFailed() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryFailed(table6.getRelapseExtrapulmonaryFailed() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (defaulted!=null && defaulted) {
	    						table6.setRelapseAllDefaulted(table6.getRelapseAllDefaulted() + 1);
	    						table6.setRelapseAllEligible(table6.getRelapseAllEligible() + 1);
	    						table6.setRelapseExtrapulmonaryDefaulted(table6.getRelapseExtrapulmonaryDefaulted() + 1);
	    						table6.setRelapseExtrapulmonaryEligible(table6.getRelapseExtrapulmonaryEligible() + 1);
	    					}
	    					
	    					else if (transferOut!=null && transferOut) {
	    						table6.setRelapseAllTransferOut(table6.getRelapseAllTransferOut() + 1);
	    						table6.setRelapseExtrapulmonaryTransferOut(table6.getRelapseExtrapulmonaryTransferOut() + 1);

	    					}
	    					
	    					else if (canceled!=null && canceled) {
	    						table6.setRelapseAllCanceled(table6.getRelapseAllCanceled() + 1);
	    						table6.setRelapseExtrapulmonaryCanceled(table6.getRelapseExtrapulmonaryCanceled() + 1);
	    						
	    					}
	    					
	    					else if (sld!=null && sld) {
	    						table6.setRelapseAllSLD(table6.getRelapseAllSLD() + 1);
	    						table6.setRelapseExtrapulmonarySLD(table6.getRelapseExtrapulmonarySLD() + 1);
	    						
	    					}
	    				}
	    			
    	    		}
    	    	} 	
    	    	
    	    	//FAILURE
    	    	else if(q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterFailure1.conceptId")) || q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterFailure2.conceptId"))) {
    	    		table6.setFailureAllDetected(table6.getFailureAllDetected() + 1);
    	    		
    	    		//P
    	    		if(pulmonary!=null && pulmonary) {
    	    			
    	    			//BC
    	    			if(bacPositive) {
    	    				
    	    				table6.setFailurePulmonaryBCDetected(table6.getFailurePulmonaryBCDetected() + 1);
    	    			
							if (cured != null && cured) {
								table6.setFailureAllCured(table6
										.getFailureAllCured() + 1);
								table6.setFailureAllEligible(table6
										.getFailureAllEligible() + 1);
								table6.setFailurePulmonaryBCCured(table6
										.getFailurePulmonaryBCCured() + 1);
								table6.setFailurePulmonaryBCEligible(table6
										.getFailurePulmonaryBCEligible() + 1);
							}

							else if (txCompleted != null && txCompleted) {
								table6.setFailureAllCompleted(table6
										.getFailureAllCompleted() + 1);
								table6.setFailureAllEligible(table6
										.getFailureAllEligible() + 1);
								table6.setFailurePulmonaryBCCompleted(table6
										.getFailurePulmonaryBCCompleted() + 1);
								table6.setFailurePulmonaryBCEligible(table6
										.getFailurePulmonaryBCEligible() + 1);
							}

							else if (diedTB != null && diedTB) {
								table6.setFailureAllDiedTB(table6
										.getFailureAllDiedTB() + 1);
								table6.setFailureAllEligible(table6
										.getFailureAllEligible() + 1);
								table6.setFailurePulmonaryBCDiedTB(table6
										.getFailurePulmonaryBCDiedTB() + 1);
								table6.setFailurePulmonaryBCEligible(table6
										.getFailurePulmonaryBCEligible() + 1);
							}

							else if (diedNotTB != null && diedNotTB) {
								table6.setFailureAllDiedNotTB(table6
										.getFailureAllDiedNotTB() + 1);
								table6.setFailureAllEligible(table6
										.getFailureAllEligible() + 1);
								table6.setFailurePulmonaryBCDiedNotTB(table6
										.getFailurePulmonaryBCDiedNotTB() + 1);
								table6.setFailurePulmonaryBCEligible(table6
										.getFailurePulmonaryBCEligible() + 1);
							}

							else if (failed != null && failed) {
								table6.setFailureAllFailed(table6
										.getFailureAllFailed() + 1);
								table6.setFailureAllEligible(table6
										.getFailureAllEligible() + 1);
								table6.setFailurePulmonaryBCFailed(table6
										.getFailurePulmonaryBCFailed() + 1);
								table6.setFailurePulmonaryBCEligible(table6
										.getFailurePulmonaryBCEligible() + 1);
							}

							else if (defaulted != null && defaulted) {
								table6.setFailureAllDefaulted(table6
										.getFailureAllDefaulted() + 1);
								table6.setFailureAllEligible(table6
										.getFailureAllEligible() + 1);
								table6.setFailurePulmonaryBCDefaulted(table6
										.getFailurePulmonaryBCDefaulted() + 1);
								table6.setFailurePulmonaryBCEligible(table6
										.getFailurePulmonaryBCEligible() + 1);
							}

							else if (transferOut != null && transferOut) {
								table6.setFailureAllTransferOut(table6
										.getFailureAllTransferOut() + 1);
								table6.setFailurePulmonaryBCTransferOut(table6
										.getFailurePulmonaryBCTransferOut() + 1);

							}

							else if (canceled != null && canceled) {
								table6.setFailureAllCanceled(table6
										.getFailureAllCanceled() + 1);
								table6.setFailurePulmonaryBCCanceled(table6
										.getFailurePulmonaryBCCanceled() + 1);

							}

							else if (sld != null && sld) {
								table6.setFailureAllSLD(table6
										.getFailureAllSLD() + 1);
								table6.setFailurePulmonaryBCSLD(table6
										.getFailurePulmonaryBCSLD() + 1);

							}
						}
    	    			
    	    			
    	    			//CD
    	    			else {
    	    				
    	    				table6.setFailurePulmonaryCDDetected(table6.getFailurePulmonaryCDDetected() + 1);

							if (cured != null && cured) {
								table6.setFailureAllCured(table6
										.getFailureAllCured() + 1);
								table6.setFailureAllEligible(table6
										.getFailureAllEligible() + 1);
								table6.setFailurePulmonaryCDCured(table6
										.getFailurePulmonaryCDCured() + 1);
								table6.setFailurePulmonaryCDEligible(table6
										.getFailurePulmonaryCDEligible() + 1);
							}

							else if (txCompleted != null && txCompleted) {
								table6.setFailureAllCompleted(table6
										.getFailureAllCompleted() + 1);
								table6.setFailureAllEligible(table6
										.getFailureAllEligible() + 1);
								table6.setFailurePulmonaryCDCompleted(table6
										.getFailurePulmonaryCDCompleted() + 1);
								table6.setFailurePulmonaryCDEligible(table6
										.getFailurePulmonaryCDEligible() + 1);
							}

							else if (diedTB != null && diedTB) {
								table6.setFailureAllDiedTB(table6
										.getFailureAllDiedTB() + 1);
								table6.setFailureAllEligible(table6
										.getFailureAllEligible() + 1);
								table6.setFailurePulmonaryCDDiedTB(table6
										.getFailurePulmonaryCDDiedTB() + 1);
								table6.setFailurePulmonaryCDEligible(table6
										.getFailurePulmonaryCDEligible() + 1);
							}

							else if (diedNotTB != null && diedNotTB) {
								table6.setFailureAllDiedNotTB(table6
										.getFailureAllDiedNotTB() + 1);
								table6.setFailureAllEligible(table6
										.getFailureAllEligible() + 1);
								table6.setFailurePulmonaryCDDiedNotTB(table6
										.getFailurePulmonaryCDDiedNotTB() + 1);
								table6.setFailurePulmonaryCDEligible(table6
										.getFailurePulmonaryCDEligible() + 1);
							}

							else if (failed != null && failed) {
								table6.setFailureAllFailed(table6
										.getFailureAllFailed() + 1);
								table6.setFailureAllEligible(table6
										.getFailureAllEligible() + 1);
								table6.setFailurePulmonaryCDFailed(table6
										.getFailurePulmonaryCDFailed() + 1);
								table6.setFailurePulmonaryCDEligible(table6
										.getFailurePulmonaryCDEligible() + 1);
							}

							else if (defaulted != null && defaulted) {
								table6.setFailureAllDefaulted(table6
										.getFailureAllDefaulted() + 1);
								table6.setFailureAllEligible(table6
										.getFailureAllEligible() + 1);
								table6.setFailurePulmonaryCDDefaulted(table6
										.getFailurePulmonaryCDDefaulted() + 1);
								table6.setFailurePulmonaryCDEligible(table6
										.getFailurePulmonaryCDEligible() + 1);
							}

							else if (transferOut != null && transferOut) {
								table6.setFailureAllTransferOut(table6
										.getFailureAllTransferOut() + 1);
								table6.setFailurePulmonaryCDTransferOut(table6
										.getFailurePulmonaryCDTransferOut() + 1);

							}

							else if (canceled != null && canceled) {
								table6.setFailureAllCanceled(table6
										.getFailureAllCanceled() + 1);
								table6.setFailurePulmonaryCDCanceled(table6
										.getFailurePulmonaryCDCanceled() + 1);

							}

							else if (sld != null && sld) {
								table6.setFailureAllSLD(table6
										.getFailureAllSLD() + 1);
								table6.setFailurePulmonaryCDSLD(table6
										.getFailurePulmonaryCDSLD() + 1);

							}

						}
    	    		}
    	    		
    	    		//EP
    	    		else if(pulmonary!=null && !pulmonary) {
    	    			
						table6.setFailureExtrapulmonaryDetected(table6
								.getFailureExtrapulmonaryDetected() + 1);

						if (cured != null && cured) {
							table6.setFailureAllCured(table6
									.getFailureAllCured() + 1);
							table6.setFailureAllEligible(table6
									.getFailureAllEligible() + 1);
							table6.setFailureExtrapulmonaryCured(table6
									.getFailureExtrapulmonaryCured() + 1);
							table6.setFailureExtrapulmonaryEligible(table6
									.getFailureExtrapulmonaryEligible() + 1);
						}

						else if (txCompleted != null && txCompleted) {
							table6.setFailureAllCompleted(table6
									.getFailureAllCompleted() + 1);
							table6.setFailureAllEligible(table6
									.getFailureAllEligible() + 1);
							table6.setFailureExtrapulmonaryCompleted(table6
									.getFailureExtrapulmonaryCompleted() + 1);
							table6.setFailureExtrapulmonaryEligible(table6
									.getFailureExtrapulmonaryEligible() + 1);
						}

						else if (diedTB != null && diedTB) {
							table6.setFailureAllDiedTB(table6
									.getFailureAllDiedTB() + 1);
							table6.setFailureAllEligible(table6
									.getFailureAllEligible() + 1);
							table6.setFailureExtrapulmonaryDiedTB(table6
									.getFailureExtrapulmonaryDiedTB() + 1);
							table6.setFailureExtrapulmonaryEligible(table6
									.getFailureExtrapulmonaryEligible() + 1);
						}

						else if (diedNotTB != null && diedNotTB) {
							table6.setFailureAllDiedNotTB(table6
									.getFailureAllDiedNotTB() + 1);
							table6.setFailureAllEligible(table6
									.getFailureAllEligible() + 1);
							table6.setFailureExtrapulmonaryDiedNotTB(table6
									.getFailureExtrapulmonaryDiedNotTB() + 1);
							table6.setFailureExtrapulmonaryEligible(table6
									.getFailureExtrapulmonaryEligible() + 1);
						}

						else if (failed != null && failed) {
							table6.setFailureAllFailed(table6
									.getFailureAllFailed() + 1);
							table6.setFailureAllEligible(table6
									.getFailureAllEligible() + 1);
							table6.setFailureExtrapulmonaryFailed(table6
									.getFailureExtrapulmonaryFailed() + 1);
							table6.setFailureExtrapulmonaryEligible(table6
									.getFailureExtrapulmonaryEligible() + 1);
						}

						else if (defaulted != null && defaulted) {
							table6.setFailureAllDefaulted(table6
									.getFailureAllDefaulted() + 1);
							table6.setFailureAllEligible(table6
									.getFailureAllEligible() + 1);
							table6.setFailureExtrapulmonaryDefaulted(table6
									.getFailureExtrapulmonaryDefaulted() + 1);
							table6.setFailureExtrapulmonaryEligible(table6
									.getFailureExtrapulmonaryEligible() + 1);
						}

						else if (transferOut != null && transferOut) {
							table6.setFailureAllTransferOut(table6
									.getFailureAllTransferOut() + 1);
							table6.setFailureExtrapulmonaryTransferOut(table6
									.getFailureExtrapulmonaryTransferOut() + 1);

						}

						else if (canceled != null && canceled) {
							table6.setFailureAllCanceled(table6
									.getFailureAllCanceled() + 1);
							table6.setFailureExtrapulmonaryCanceled(table6
									.getFailureExtrapulmonaryCanceled() + 1);

						}

						else if (sld != null && sld) {
							table6.setFailureAllSLD(table6.getFailureAllSLD() + 1);
							table6.setFailureExtrapulmonarySLD(table6
									.getFailureExtrapulmonarySLD() + 1);

						}

					}
				}
    	    	
    	    	else if(q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterDefault1.conceptId")) || q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.afterDefault2.conceptId"))) {
    	    		table6.setDefaultAllDetected(table6.getDefaultAllDetected() + 1);
    	    		
    	    		//P
    	    		if(pulmonary!=null && pulmonary) {
    	    			
    	    			//BC
    	    			if(bacPositive) {
    	    				
    	    				table6.setDefaultPulmonaryBCDetected(table6.getDefaultPulmonaryBCDetected() + 1);
    	    			
							if (cured != null && cured) {
								table6.setDefaultAllCured(table6
										.getDefaultAllCured() + 1);
								table6.setDefaultAllEligible(table6
										.getDefaultAllEligible() + 1);
								table6.setDefaultPulmonaryBCCured(table6
										.getDefaultPulmonaryBCCured() + 1);
								table6.setDefaultPulmonaryBCEligible(table6
										.getDefaultPulmonaryBCEligible() + 1);
							}

							else if (txCompleted != null && txCompleted) {
								table6.setDefaultAllCompleted(table6
										.getDefaultAllCompleted() + 1);
								table6.setDefaultAllEligible(table6
										.getDefaultAllEligible() + 1);
								table6.setDefaultPulmonaryBCCompleted(table6
										.getDefaultPulmonaryBCCompleted() + 1);
								table6.setDefaultPulmonaryBCEligible(table6
										.getDefaultPulmonaryBCEligible() + 1);
							}

							else if (diedTB != null && diedTB) {
								table6.setDefaultAllDiedTB(table6
										.getDefaultAllDiedTB() + 1);
								table6.setDefaultAllEligible(table6
										.getDefaultAllEligible() + 1);
								table6.setDefaultPulmonaryBCDiedTB(table6
										.getDefaultPulmonaryBCDiedTB() + 1);
								table6.setDefaultPulmonaryBCEligible(table6
										.getDefaultPulmonaryBCEligible() + 1);
							}

							else if (diedNotTB != null && diedNotTB) {
								table6.setDefaultAllDiedNotTB(table6
										.getDefaultAllDiedNotTB() + 1);
								table6.setDefaultAllEligible(table6
										.getDefaultAllEligible() + 1);
								table6.setDefaultPulmonaryBCDiedNotTB(table6
										.getDefaultPulmonaryBCDiedNotTB() + 1);
								table6.setDefaultPulmonaryBCEligible(table6
										.getDefaultPulmonaryBCEligible() + 1);
							}

							else if (failed != null && failed) {
								table6.setDefaultAllFailed(table6
										.getDefaultAllFailed() + 1);
								table6.setDefaultAllEligible(table6
										.getDefaultAllEligible() + 1);
								table6.setDefaultPulmonaryBCFailed(table6
										.getDefaultPulmonaryBCFailed() + 1);
								table6.setDefaultPulmonaryBCEligible(table6
										.getDefaultPulmonaryBCEligible() + 1);
							}

							else if (defaulted != null && defaulted) {
								table6.setDefaultAllDefaulted(table6
										.getDefaultAllDefaulted() + 1);
								table6.setDefaultAllEligible(table6
										.getDefaultAllEligible() + 1);
								table6.setDefaultPulmonaryBCDefaulted(table6
										.getDefaultPulmonaryBCDefaulted() + 1);
								table6.setDefaultPulmonaryBCEligible(table6
										.getDefaultPulmonaryBCEligible() + 1);
							}

							else if (transferOut != null && transferOut) {
								table6.setDefaultAllTransferOut(table6
										.getDefaultAllTransferOut() + 1);
								table6.setDefaultPulmonaryBCTransferOut(table6
										.getDefaultPulmonaryBCTransferOut() + 1);

							}

							else if (canceled != null && canceled) {
								table6.setDefaultAllCanceled(table6
										.getDefaultAllCanceled() + 1);
								table6.setDefaultPulmonaryBCCanceled(table6
										.getDefaultPulmonaryBCCanceled() + 1);

							}

							else if (sld != null && sld) {
								table6.setDefaultAllSLD(table6
										.getDefaultAllSLD() + 1);
								table6.setDefaultPulmonaryBCSLD(table6
										.getDefaultPulmonaryBCSLD() + 1);

							}
						}
    	    			
    	    			
    	    			//CD
    	    			else {
    	    				
    	    				table6.setDefaultPulmonaryCDDetected(table6.getDefaultPulmonaryCDDetected() + 1);

							if (cured != null && cured) {
								table6.setDefaultAllCured(table6
										.getDefaultAllCured() + 1);
								table6.setDefaultAllEligible(table6
										.getDefaultAllEligible() + 1);
								table6.setDefaultPulmonaryCDCured(table6
										.getDefaultPulmonaryCDCured() + 1);
								table6.setDefaultPulmonaryCDEligible(table6
										.getDefaultPulmonaryCDEligible() + 1);
							}

							else if (txCompleted != null && txCompleted) {
								table6.setDefaultAllCompleted(table6
										.getDefaultAllCompleted() + 1);
								table6.setDefaultAllEligible(table6
										.getDefaultAllEligible() + 1);
								table6.setDefaultPulmonaryCDCompleted(table6
										.getDefaultPulmonaryCDCompleted() + 1);
								table6.setDefaultPulmonaryCDEligible(table6
										.getDefaultPulmonaryCDEligible() + 1);
							}

							else if (diedTB != null && diedTB) {
								table6.setDefaultAllDiedTB(table6
										.getDefaultAllDiedTB() + 1);
								table6.setDefaultAllEligible(table6
										.getDefaultAllEligible() + 1);
								table6.setDefaultPulmonaryCDDiedTB(table6
										.getDefaultPulmonaryCDDiedTB() + 1);
								table6.setDefaultPulmonaryCDEligible(table6
										.getDefaultPulmonaryCDEligible() + 1);
							}

							else if (diedNotTB != null && diedNotTB) {
								table6.setDefaultAllDiedNotTB(table6
										.getDefaultAllDiedNotTB() + 1);
								table6.setDefaultAllEligible(table6
										.getDefaultAllEligible() + 1);
								table6.setDefaultPulmonaryCDDiedNotTB(table6
										.getDefaultPulmonaryCDDiedNotTB() + 1);
								table6.setDefaultPulmonaryCDEligible(table6
										.getDefaultPulmonaryCDEligible() + 1);
							}

							else if (failed != null && failed) {
								table6.setDefaultAllFailed(table6
										.getDefaultAllFailed() + 1);
								table6.setDefaultAllEligible(table6
										.getDefaultAllEligible() + 1);
								table6.setDefaultPulmonaryCDFailed(table6
										.getDefaultPulmonaryCDFailed() + 1);
								table6.setDefaultPulmonaryCDEligible(table6
										.getDefaultPulmonaryCDEligible() + 1);
							}

							else if (defaulted != null && defaulted) {
								table6.setDefaultAllDefaulted(table6
										.getDefaultAllDefaulted() + 1);
								table6.setDefaultAllEligible(table6
										.getDefaultAllEligible() + 1);
								table6.setDefaultPulmonaryCDDefaulted(table6
										.getDefaultPulmonaryCDDefaulted() + 1);
								table6.setDefaultPulmonaryCDEligible(table6
										.getDefaultPulmonaryCDEligible() + 1);
							}

							else if (transferOut != null && transferOut) {
								table6.setDefaultAllTransferOut(table6
										.getDefaultAllTransferOut() + 1);
								table6.setDefaultPulmonaryCDTransferOut(table6
										.getDefaultPulmonaryCDTransferOut() + 1);

							}

							else if (canceled != null && canceled) {
								table6.setDefaultAllCanceled(table6
										.getDefaultAllCanceled() + 1);
								table6.setDefaultPulmonaryCDCanceled(table6
										.getDefaultPulmonaryCDCanceled() + 1);

							}

							else if (sld != null && sld) {
								table6.setDefaultAllSLD(table6
										.getDefaultAllSLD() + 1);
								table6.setDefaultPulmonaryCDSLD(table6
										.getDefaultPulmonaryCDSLD() + 1);

							}

						}
    	    		}
    	    		
    	    		//EP
    	    		else if(pulmonary!=null && !pulmonary) {
    	    			
						table6.setDefaultExtrapulmonaryDetected(table6
								.getDefaultExtrapulmonaryDetected() + 1);

						if (cured != null && cured) {
							table6.setDefaultAllCured(table6
									.getDefaultAllCured() + 1);
							table6.setDefaultAllEligible(table6
									.getDefaultAllEligible() + 1);
							table6.setDefaultExtrapulmonaryCured(table6
									.getDefaultExtrapulmonaryCured() + 1);
							table6.setDefaultExtrapulmonaryEligible(table6
									.getDefaultExtrapulmonaryEligible() + 1);
						}

						else if (txCompleted != null && txCompleted) {
							table6.setDefaultAllCompleted(table6
									.getDefaultAllCompleted() + 1);
							table6.setDefaultAllEligible(table6
									.getDefaultAllEligible() + 1);
							table6.setDefaultExtrapulmonaryCompleted(table6
									.getDefaultExtrapulmonaryCompleted() + 1);
							table6.setDefaultExtrapulmonaryEligible(table6
									.getDefaultExtrapulmonaryEligible() + 1);
						}

						else if (diedTB != null && diedTB) {
							table6.setDefaultAllDiedTB(table6
									.getDefaultAllDiedTB() + 1);
							table6.setDefaultAllEligible(table6
									.getDefaultAllEligible() + 1);
							table6.setDefaultExtrapulmonaryDiedTB(table6
									.getDefaultExtrapulmonaryDiedTB() + 1);
							table6.setDefaultExtrapulmonaryEligible(table6
									.getDefaultExtrapulmonaryEligible() + 1);
						}

						else if (diedNotTB != null && diedNotTB) {
							table6.setDefaultAllDiedNotTB(table6
									.getDefaultAllDiedNotTB() + 1);
							table6.setDefaultAllEligible(table6
									.getDefaultAllEligible() + 1);
							table6.setDefaultExtrapulmonaryDiedNotTB(table6
									.getDefaultExtrapulmonaryDiedNotTB() + 1);
							table6.setDefaultExtrapulmonaryEligible(table6
									.getDefaultExtrapulmonaryEligible() + 1);
						}

						else if (failed != null && failed) {
							table6.setDefaultAllFailed(table6
									.getDefaultAllFailed() + 1);
							table6.setDefaultAllEligible(table6
									.getDefaultAllEligible() + 1);
							table6.setDefaultExtrapulmonaryFailed(table6
									.getDefaultExtrapulmonaryFailed() + 1);
							table6.setDefaultExtrapulmonaryEligible(table6
									.getDefaultExtrapulmonaryEligible() + 1);
						}

						else if (defaulted != null && defaulted) {
							table6.setDefaultAllDefaulted(table6
									.getDefaultAllDefaulted() + 1);
							table6.setDefaultAllEligible(table6
									.getDefaultAllEligible() + 1);
							table6.setDefaultExtrapulmonaryDefaulted(table6
									.getDefaultExtrapulmonaryDefaulted() + 1);
							table6.setDefaultExtrapulmonaryEligible(table6
									.getDefaultExtrapulmonaryEligible() + 1);
						}

						else if (transferOut != null && transferOut) {
							table6.setDefaultAllTransferOut(table6
									.getDefaultAllTransferOut() + 1);
							table6.setDefaultExtrapulmonaryTransferOut(table6
									.getDefaultExtrapulmonaryTransferOut() + 1);

						}

						else if (canceled != null && canceled) {
							table6.setDefaultAllCanceled(table6
									.getDefaultAllCanceled() + 1);
							table6.setDefaultExtrapulmonaryCanceled(table6
									.getDefaultExtrapulmonaryCanceled() + 1);

						}

						else if (sld != null && sld) {
							table6.setDefaultAllSLD(table6.getDefaultAllSLD() + 1);
							table6.setDefaultExtrapulmonarySLD(table6
									.getDefaultExtrapulmonarySLD() + 1);

						}

					}
			
    	    	}
    	    	
    	    	//OTHER
    	    	else if (q.getId().intValue()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.other.conceptId"))) {
    	    		table6.setOtherAllDetected(table6.getOtherAllDetected() + 1);
    	    		
    	    		//P
    	    		if(pulmonary!=null && pulmonary) {
    	    			
    	    			//BC
    	    			if(bacPositive) {
    	    				
    	    				table6.setOtherPulmonaryBCDetected(table6.getOtherPulmonaryBCDetected() + 1);
    	    			
							if (cured != null && cured) {
								table6.setOtherAllCured(table6
										.getOtherAllCured() + 1);
								table6.setOtherAllEligible(table6
										.getOtherAllEligible() + 1);
								table6.setOtherPulmonaryBCCured(table6
										.getOtherPulmonaryBCCured() + 1);
								table6.setOtherPulmonaryBCEligible(table6
										.getOtherPulmonaryBCEligible() + 1);
							}

							else if (txCompleted != null && txCompleted) {
								table6.setOtherAllCompleted(table6
										.getOtherAllCompleted() + 1);
								table6.setOtherAllEligible(table6
										.getOtherAllEligible() + 1);
								table6.setOtherPulmonaryBCCompleted(table6
										.getOtherPulmonaryBCCompleted() + 1);
								table6.setOtherPulmonaryBCEligible(table6
										.getOtherPulmonaryBCEligible() + 1);
							}

							else if (diedTB != null && diedTB) {
								table6.setOtherAllDiedTB(table6
										.getOtherAllDiedTB() + 1);
								table6.setOtherAllEligible(table6
										.getOtherAllEligible() + 1);
								table6.setOtherPulmonaryBCDiedTB(table6
										.getOtherPulmonaryBCDiedTB() + 1);
								table6.setOtherPulmonaryBCEligible(table6
										.getOtherPulmonaryBCEligible() + 1);
							}

							else if (diedNotTB != null && diedNotTB) {
								table6.setOtherAllDiedNotTB(table6
										.getOtherAllDiedNotTB() + 1);
								table6.setOtherAllEligible(table6
										.getOtherAllEligible() + 1);
								table6.setOtherPulmonaryBCDiedNotTB(table6
										.getOtherPulmonaryBCDiedNotTB() + 1);
								table6.setOtherPulmonaryBCEligible(table6
										.getOtherPulmonaryBCEligible() + 1);
							}

							else if (failed != null && failed) {
								table6.setOtherAllFailed(table6
										.getOtherAllFailed() + 1);
								table6.setOtherAllEligible(table6
										.getOtherAllEligible() + 1);
								table6.setOtherPulmonaryBCFailed(table6
										.getOtherPulmonaryBCFailed() + 1);
								table6.setOtherPulmonaryBCEligible(table6
										.getOtherPulmonaryBCEligible() + 1);
							}

							else if (defaulted != null && defaulted) {
								table6.setOtherAllDefaulted(table6
										.getOtherAllDefaulted() + 1);
								table6.setOtherAllEligible(table6
										.getOtherAllEligible() + 1);
								table6.setOtherPulmonaryBCDefaulted(table6
										.getOtherPulmonaryBCDefaulted() + 1);
								table6.setOtherPulmonaryBCEligible(table6
										.getOtherPulmonaryBCEligible() + 1);
							}

							else if (transferOut != null && transferOut) {
								table6.setOtherAllTransferOut(table6
										.getOtherAllTransferOut() + 1);
								table6.setOtherPulmonaryBCTransferOut(table6
										.getOtherPulmonaryBCTransferOut() + 1);

							}

							else if (canceled != null && canceled) {
								table6.setOtherAllCanceled(table6
										.getOtherAllCanceled() + 1);
								table6.setOtherPulmonaryBCCanceled(table6
										.getOtherPulmonaryBCCanceled() + 1);

							}

							else if (sld != null && sld) {
								table6.setOtherAllSLD(table6
										.getOtherAllSLD() + 1);
								table6.setOtherPulmonaryBCSLD(table6
										.getOtherPulmonaryBCSLD() + 1);

							}
						}
    	    			
    	    			
    	    			//CD
    	    			else {
    	    				
    	    				table6.setOtherPulmonaryCDDetected(table6.getOtherPulmonaryCDDetected() + 1);

							if (cured != null && cured) {
								table6.setOtherAllCured(table6
										.getOtherAllCured() + 1);
								table6.setOtherAllEligible(table6
										.getOtherAllEligible() + 1);
								table6.setOtherPulmonaryCDCured(table6
										.getOtherPulmonaryCDCured() + 1);
								table6.setOtherPulmonaryCDEligible(table6
										.getOtherPulmonaryCDEligible() + 1);
							}

							else if (txCompleted != null && txCompleted) {
								table6.setOtherAllCompleted(table6
										.getOtherAllCompleted() + 1);
								table6.setOtherAllEligible(table6
										.getOtherAllEligible() + 1);
								table6.setOtherPulmonaryCDCompleted(table6
										.getOtherPulmonaryCDCompleted() + 1);
								table6.setOtherPulmonaryCDEligible(table6
										.getOtherPulmonaryCDEligible() + 1);
							}

							else if (diedTB != null && diedTB) {
								table6.setOtherAllDiedTB(table6
										.getOtherAllDiedTB() + 1);
								table6.setOtherAllEligible(table6
										.getOtherAllEligible() + 1);
								table6.setOtherPulmonaryCDDiedTB(table6
										.getOtherPulmonaryCDDiedTB() + 1);
								table6.setOtherPulmonaryCDEligible(table6
										.getOtherPulmonaryCDEligible() + 1);
							}

							else if (diedNotTB != null && diedNotTB) {
								table6.setOtherAllDiedNotTB(table6
										.getOtherAllDiedNotTB() + 1);
								table6.setOtherAllEligible(table6
										.getOtherAllEligible() + 1);
								table6.setOtherPulmonaryCDDiedNotTB(table6
										.getOtherPulmonaryCDDiedNotTB() + 1);
								table6.setOtherPulmonaryCDEligible(table6
										.getOtherPulmonaryCDEligible() + 1);
							}

							else if (failed != null && failed) {
								table6.setOtherAllFailed(table6
										.getOtherAllFailed() + 1);
								table6.setOtherAllEligible(table6
										.getOtherAllEligible() + 1);
								table6.setOtherPulmonaryCDFailed(table6
										.getOtherPulmonaryCDFailed() + 1);
								table6.setOtherPulmonaryCDEligible(table6
										.getOtherPulmonaryCDEligible() + 1);
							}

							else if (defaulted != null && defaulted) {
								table6.setOtherAllDefaulted(table6
										.getOtherAllDefaulted() + 1);
								table6.setOtherAllEligible(table6
										.getOtherAllEligible() + 1);
								table6.setOtherPulmonaryCDDefaulted(table6
										.getOtherPulmonaryCDDefaulted() + 1);
								table6.setOtherPulmonaryCDEligible(table6
										.getOtherPulmonaryCDEligible() + 1);
							}

							else if (transferOut != null && transferOut) {
								table6.setOtherAllTransferOut(table6
										.getOtherAllTransferOut() + 1);
								table6.setOtherPulmonaryCDTransferOut(table6
										.getOtherPulmonaryCDTransferOut() + 1);

							}

							else if (canceled != null && canceled) {
								table6.setOtherAllCanceled(table6
										.getOtherAllCanceled() + 1);
								table6.setOtherPulmonaryCDCanceled(table6
										.getOtherPulmonaryCDCanceled() + 1);

							}

							else if (sld != null && sld) {
								table6.setOtherAllSLD(table6
										.getOtherAllSLD() + 1);
								table6.setOtherPulmonaryCDSLD(table6
										.getOtherPulmonaryCDSLD() + 1);

							}

						}
    	    		}
    	    		
    	    		//EP
    	    		else if(pulmonary!=null && !pulmonary) {
    	    			
						table6.setOtherExtrapulmonaryDetected(table6
								.getOtherExtrapulmonaryDetected() + 1);

						if (cured != null && cured) {
							table6.setOtherAllCured(table6
									.getOtherAllCured() + 1);
							table6.setOtherAllEligible(table6
									.getOtherAllEligible() + 1);
							table6.setOtherExtrapulmonaryCured(table6
									.getOtherExtrapulmonaryCured() + 1);
							table6.setOtherExtrapulmonaryEligible(table6
									.getOtherExtrapulmonaryEligible() + 1);
						}

						else if (txCompleted != null && txCompleted) {
							table6.setOtherAllCompleted(table6
									.getOtherAllCompleted() + 1);
							table6.setOtherAllEligible(table6
									.getOtherAllEligible() + 1);
							table6.setOtherExtrapulmonaryCompleted(table6
									.getOtherExtrapulmonaryCompleted() + 1);
							table6.setOtherExtrapulmonaryEligible(table6
									.getOtherExtrapulmonaryEligible() + 1);
						}

						else if (diedTB != null && diedTB) {
							table6.setOtherAllDiedTB(table6
									.getOtherAllDiedTB() + 1);
							table6.setOtherAllEligible(table6
									.getOtherAllEligible() + 1);
							table6.setOtherExtrapulmonaryDiedTB(table6
									.getOtherExtrapulmonaryDiedTB() + 1);
							table6.setOtherExtrapulmonaryEligible(table6
									.getOtherExtrapulmonaryEligible() + 1);
						}

						else if (diedNotTB != null && diedNotTB) {
							table6.setOtherAllDiedNotTB(table6
									.getOtherAllDiedNotTB() + 1);
							table6.setOtherAllEligible(table6
									.getOtherAllEligible() + 1);
							table6.setOtherExtrapulmonaryDiedNotTB(table6
									.getOtherExtrapulmonaryDiedNotTB() + 1);
							table6.setOtherExtrapulmonaryEligible(table6
									.getOtherExtrapulmonaryEligible() + 1);
						}

						else if (failed != null && failed) {
							table6.setOtherAllFailed(table6
									.getOtherAllFailed() + 1);
							table6.setOtherAllEligible(table6
									.getOtherAllEligible() + 1);
							table6.setOtherExtrapulmonaryFailed(table6
									.getOtherExtrapulmonaryFailed() + 1);
							table6.setOtherExtrapulmonaryEligible(table6
									.getOtherExtrapulmonaryEligible() + 1);
						}

						else if (defaulted != null && defaulted) {
							table6.setOtherAllDefaulted(table6
									.getOtherAllDefaulted() + 1);
							table6.setOtherAllEligible(table6
									.getOtherAllEligible() + 1);
							table6.setOtherExtrapulmonaryDefaulted(table6
									.getOtherExtrapulmonaryDefaulted() + 1);
							table6.setOtherExtrapulmonaryEligible(table6
									.getOtherExtrapulmonaryEligible() + 1);
						}

						else if (transferOut != null && transferOut) {
							table6.setOtherAllTransferOut(table6
									.getOtherAllTransferOut() + 1);
							table6.setOtherExtrapulmonaryTransferOut(table6
									.getOtherExtrapulmonaryTransferOut() + 1);

						}

						else if (canceled != null && canceled) {
							table6.setOtherAllCanceled(table6
									.getOtherAllCanceled() + 1);
							table6.setOtherExtrapulmonaryCanceled(table6
									.getOtherExtrapulmonaryCanceled() + 1);

						}

						else if (sld != null && sld) {
							table6.setOtherAllSLD(table6.getOtherAllSLD() + 1);
							table6.setOtherExtrapulmonarySLD(table6
									.getOtherExtrapulmonarySLD() + 1);

						}
					}
    	    	}
    	    }
    	//}
    	
    	//fin.add(f8Table6);
    	
    	//TOTALS
		}
		
    	
		/*Integer report_oblast = null; Integer report_quarter = null; Integer report_month = null;
		//if(new PDFHelper().isInt(oblast)) { report_oblast = Integer.parseInt(oblast); }
		if(new PDFHelper().isInt(quarter)) { report_quarter = Integer.parseInt(quarter); }
		if(new PDFHelper().isInt(month)) { report_month = Integer.parseInt(month); }*/
		
		boolean reportStatus;
		/*if(location!=null)
			 reportStatus = Context.getService(MdrtbService.class).readReportStatus(report_oblast, location.getId(), year, report_quarter, report_month, "TB-08","DOTSTB");
		else*/
			reportStatus = Context.getService(MdrtbService.class).readReportStatus(oblastId, districtId, facilityId, year, quarter, month, "TB-08","DOTSTB");
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
		model.addAttribute("table2", table2);
		model.addAttribute("table3", table3);
		model.addAttribute("table4", table4);
		model.addAttribute("table5a", table5a);
    	model.addAttribute("table6", table6);
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
		model.addAttribute("oName", oName);
		model.addAttribute("dName", dName);
		model.addAttribute("fName", fName);
    	model.addAttribute("reportDate", rdateSDF.format(new Date()));
    	model.addAttribute("reportStatus", reportStatus);
        return "/module/mdrtb/reporting/form8Results";
        //_" + Context.getLocale().toString().substring(0, 2);
    }
    
    
  
    
}
