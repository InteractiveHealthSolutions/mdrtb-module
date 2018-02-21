package org.openmrs.module.mdrtb.web.controller.reporting.pv;


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
import org.openmrs.module.mdrtb.form.RegimenForm;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.form.pv.AEForm;
import org.openmrs.module.mdrtb.reporting.PDFHelper;
import org.openmrs.module.mdrtb.reporting.ReportUtil;
import org.openmrs.module.mdrtb.reporting.TB07Table1Data;
import org.openmrs.module.mdrtb.reporting.TB07Util;
import org.openmrs.module.mdrtb.reporting.data.Cohorts;
import org.openmrs.module.mdrtb.reporting.pv.PVDataTable1;
import org.openmrs.module.mdrtb.reporting.pv.PVDataTable4;
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

public class AEReportController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
        
    
    @RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/pv/ae")
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
       
        /*List<Location> locations = Context.getLocationService().getAllLocations(false);// Context.getLocationService().getAllLocations();//ms = (MdrtbDrugForecastService) Context.getService(MdrtbDrugForecastService.class);
        List<Oblast> oblasts = Context.getService(MdrtbService.class).getOblasts();
        //drugSets =  ms.getMdrtbDrugs();
        
       

        model.addAttribute("locations", locations);
        model.addAttribute("oblasts", oblasts);*/
    	 return new ModelAndView("/module/mdrtb/reporting/pv/ae", model);	
    	
    }
    

    @RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/pv/ae")
    public static String doAE(
    		@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month,
            ModelMap model) throws EvaluationException {
    	
    	System.out.println("---POST-----");
    	
		SimpleDateFormat sdf = new SimpleDateFormat();
    	sdf.applyPattern("dd.MM.yyyy");
    	SimpleDateFormat rdateSDF = new SimpleDateFormat();
    	rdateSDF.applyPattern("dd.MM.yyyy HH:mm:ss");
			
    	Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
		
		Date startDate = (Date)(dateMap.get("startDate"));
		Date endDate = (Date)(dateMap.get("endDate"));
    	
    	ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
		
		ArrayList<RegimenForm> regimenList = Context.getService(MdrtbService.class).getRegimenFormsFilled(locList, year, quarter, month);
		ArrayList<Patient> countedPatients = new ArrayList<Patient>();
		
		
		
		System.out.println("list size:" + regimenList.size());
		//CohortDefinition baseCohort = null;
		ArrayList<Patient> allPatients = Context.getService(MdrtbService.class).getAllPatientsWithRegimenForms();
    	
    	
    	//Integer regimenTypeConceptId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SLD_REGIMEN_TYPE).getId();
    	Integer standardRegimenConceptId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.STANDARD_MDR_REGIMEN).getId();
    	Integer shortRegimenConceptId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SHORT_MDR_REGIMEN).getId();
    	Integer regimenWithBdqConceptId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.INDIVIDUAL_WITH_BDQ).getId();
    	Integer regimenWithDlmConceptId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.INDIVIDUAL_WITH_DLM).getId();
    	Integer regimenWithBdqDlmConceptId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.INDIVIDUAL_WITH_BDQ_AND_DLM).getId();

    	PVDataTable1 table1 = new PVDataTable1();
    	PVDataTable4 table4 = new PVDataTable4();
    	
    	
    	//start of Table 1
    	for (RegimenForm rf : regimenList) {
    		
    	  if(!countedPatients.contains(rf.getPatient())) {
    		  countedPatients.add(rf.getPatient());
    		  
    		  //get disease site
      	    Concept q = rf.getSldRegimenType();
      	    
      	    
      	    if(q!=null) {
      	    	if(q.getConceptId().intValue()==standardRegimenConceptId) {
      	             table1.setStandardRegimenEver(table1.getStandardRegimenEver() + 1);
      	             table1.setStandardRegimenStarting(table1.getStandardRegimenStarting() + 1);
      	    	}
      	    	
      	    	else if(q.getConceptId().intValue()==shortRegimenConceptId) {
     	             table1.setShortRegimenEver(table1.getShortRegimenEver() + 1);
     	             table1.setShortRegimenStarting(table1.getShortRegimenStarting() + 1);
     	    	}
      	    	
      	    	else if(q.getConceptId().intValue()==regimenWithBdqConceptId) {
     	             table1.setRegimenWithBdqEver(table1.getRegimenWithBdqEver() + 1);
     	             table1.setRegimenWithBdqStarting(table1.getRegimenWithBdqStarting() + 1);
     	    	}
      	    	
      	    	else if(q.getConceptId().intValue()==regimenWithDlmConceptId) {
    	             table1.setRegimenWithDlmEver(table1.getRegimenWithDlmEver() + 1);
    	             table1.setRegimenWithDlmStarting(table1.getRegimenWithDlmStarting() + 1);
    	    	}
      	    	
      	    	else if(q.getConceptId().intValue()==regimenWithBdqDlmConceptId) {
      	    		table1.setRegimenWithDlmEver(table1.getRegimenWithDlmEver() + 1);
      	    		table1.setRegimenWithDlmStarting(table1.getRegimenWithDlmStarting() + 1);
      	    		table1.setRegimenWithBdqEver(table1.getRegimenWithBdqEver() + 1);
      	    		table1.setRegimenWithBdqStarting(table1.getRegimenWithBdqStarting() + 1);
      	    	}
      	    }
    	  }
    	}
    	
    	for(Patient p : allPatients) {
    		if(!countedPatients.contains(p)) {
    			RegimenForm rfp = Context.getService(MdrtbService.class).getPreviousRegimenFormForPatient(p, locList, endDate);
    			
    			if(rfp!=null) {
    				 Concept q = rfp.getSldRegimenType();
    		      	    
    		      	    
    		      	    if(q!=null) {
    		      	    	if(q.getConceptId().intValue()==standardRegimenConceptId.intValue()) {
    		      	             table1.setStandardRegimenEver(table1.getStandardRegimenEver() + 1);
    		      	             table1.setStandardRegimenEver(table1.getStandardRegimenStarting() + 1);
    		      	    	}
    		      	    	
    		      	    	else if(q.getConceptId().intValue()==shortRegimenConceptId.intValue()) {
    		     	             table1.setShortRegimenEver(table1.getShortRegimenEver() + 1);
    		     	             table1.setShortRegimenEver(table1.getShortRegimenStarting() + 1);
    		     	    	}
    		      	    	
    		      	    	else if(q.getConceptId().intValue()==regimenWithBdqConceptId.intValue()) {
    		     	             table1.setRegimenWithBdqEver(table1.getRegimenWithBdqEver() + 1);
    		     	             table1.setRegimenWithBdqEver(table1.getRegimenWithBdqStarting() + 1);
    		     	    	}
    		      	    	
    		      	    	else if(q.getConceptId().intValue()==regimenWithDlmConceptId.intValue()) {
    		    	             table1.setRegimenWithDlmEver(table1.getRegimenWithDlmEver() + 1);
    		    	             table1.setRegimenWithDlmEver(table1.getRegimenWithDlmStarting() + 1);
    		    	    	}
    		      	    	
    		      	    	else if(q.getConceptId().intValue()==regimenWithBdqDlmConceptId.intValue()) {
    		      	    		table1.setRegimenWithDlmEver(table1.getRegimenWithDlmEver() + 1);
    		      	    		table1.setRegimenWithDlmEver(table1.getRegimenWithDlmStarting() + 1);
    		      	    		table1.setRegimenWithBdqEver(table1.getRegimenWithBdqEver() + 1);
    		      	    		table1.setRegimenWithBdqEver(table1.getRegimenWithBdqStarting() + 1);
    		      	    	}
    		      	    }
    				}
    			}
    		}
    	   
    	//end of Table 1
    	
    	
    	//start of Table 4
    	//
    	
    	Integer ancillaryDrugsId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANCILLARY_DRUG_GIVEN).getId();
    	//Integer actionId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AE_ACTION).getId();
    	//Integer aeId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ADVERSE_EVENT).getId();
    	Integer nauseaId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NAUSEA).getId();
    	Integer diarrhoeaId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DIARRHOEA).getId();
    	Integer arthalgiaId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ARTHALGIA).getId();
    	Integer dizzinessId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DIZZINESS).getId();
    	Integer hearingDisturbancesId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HEARING_DISTURBANCES).getId();
    	Integer headachesId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HEADACHE).getId();
    	Integer sleepDisturbancesId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SLEEP_DISTURBANCES).getId();
    	Integer electrolyteDisturbancesId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ELECTROLYTE_DISTURBANCES).getId();
    	Integer abdominalPainId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ABDOMINAL_PAIN).getId();
    	Integer anorexiaId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANOREXIA).getId();
    	Integer gastritisId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.GASTRITIS).getId();
    	Integer peripheralNeuropathyId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PERIPHERAL_NEUROPATHY).getId();
    	Integer depressionId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DEPRESSION).getId();
    	Integer tinnitusId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.TINNITUS).getId();
    	Integer allergicReactionId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ALLERGIC_REACTION).getId();
    	Integer rashId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RASH).getId();
    	Integer visualDisturbancesId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.VISUAL_DISTURBANCES).getId();
    	Integer seizuresId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SEIZURES).getId();
    	Integer hypothyroidismId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HYPOTHYROIDISM).getId();
    	Integer psychosisId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PSYCHOSIS).getId();
    	Integer suicidalIdeationId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SUICIDAL_IDEATION).getId();
    	Integer hepatitisId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HEPATITIS_AE).getId();
    	Integer renalFailureId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RENAL_FAILURE).getId();
    	Integer qtProlongationId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.QT_PROLONGATION).getId();
    	

    	ArrayList<AEForm> aeForms = Context.getService(MdrtbService.class).getAEFormsFilled(locList, year, quarter, month);
    	System.out.println("SZ:" + aeForms.size());
    	for(AEForm ae : aeForms) {
    		
    		Concept q = ae.getActionTaken();
    		Integer id = null;
    		if(q!=null) {
    			
    			id = q.getId();
    			System.out.println("ID1: " + id);
    			if(id!=null && id.intValue()==ancillaryDrugsId.intValue()) {
    				
    				q = ae.getAdverseEvent();
    				
    				if(q!=null) {
    					id = q.getId();
    					System.out.println("ID2: " + id);
    					if(id.intValue()==nauseaId.intValue()) {
    						table4.setNausea(table4.getNausea() + 1);
    					}
    					
    					else if(id.intValue()==diarrhoeaId.intValue()) {
    						table4.setDiarrhoea(table4.getDiarrhoea() + 1);
    					}
    					
    					else if(id.intValue()==arthalgiaId.intValue()) {
    						table4.setArthalgia(table4.getArthalgia() + 1);
    					}
    					
    					else if(id.intValue()==dizzinessId.intValue()) {
    						table4.setDizziness(table4.getDizziness() + 1);
    					}
    					
    					else if(id.intValue()==hearingDisturbancesId.intValue()) {
    						table4.setHearingDisturbances(table4.getHearingDisturbances() + 1);
    					}
    					
    					else if(id.intValue()==headachesId.intValue()) {
    						table4.setHeadaches(table4.getHeadaches() + 1);
    					}
    					
    					else if(id.intValue()==sleepDisturbancesId.intValue()) {
    						table4.setSleepDisturbances(table4.getSleepDisturbances() + 1);
    					}
    					
    					else if(id.intValue()==electrolyteDisturbancesId.intValue()) {
    						table4.setElectrolyteDisturbance(table4.getElectrolyteDisturbance() + 1);
    					}
    					
    					else if(id.intValue()==abdominalPainId.intValue()) {
    						table4.setAbdominalPain(table4.getAbdominalPain() + 1);
    					}
    					
    					else if(id.intValue()==anorexiaId.intValue()) {
    						table4.setAnorexia(table4.getAnorexia() + 1);
    					}
    					
    					else if(id.intValue()==gastritisId.intValue()) {
    						table4.setGastritis(table4.getGastritis() + 1);
    					}
    					
    					else if(id.intValue()==peripheralNeuropathyId.intValue()) {
    						table4.setPeripheralNeuropathy(table4.getPeripheralNeuropathy() + 1);
    					}
    					
    					else if(id.intValue()==depressionId.intValue()) {
    						table4.setDepression(table4.getDepression() + 1);
    					}
    					
    					else if(id.intValue()==tinnitusId.intValue()) {
    						table4.setTinnitus(table4.getTinnitus() + 1);
    					}
    					
    					else if(id.intValue()==allergicReactionId.intValue()) {
    						table4.setAllergicReaction(table4.getAllergicReaction() + 1);
    					}
    					
    					else if(id.intValue()==rashId.intValue()) {
    						table4.setRash(table4.getRash() + 1);
    					}
    					
    					else if(id.intValue()==visualDisturbancesId.intValue()) {
    						table4.setVisualDisturbances(table4.getVisualDisturbances() + 1);
    					}
    					
    					else if(id.intValue()==seizuresId.intValue()) {
    						table4.setSeizures(table4.getSeizures() + 1);
    					}
    					
    					else if(id.intValue()==hypothyroidismId.intValue()) {
    						table4.setHypothyroidism(table4.getHypothyroidism() + 1);
    					}
    					
    					else if(id.intValue()==psychosisId.intValue()) {
    						table4.setPsychosis(table4.getPsychosis() + 1);
    					}
    					
    					else if(id.intValue()==suicidalIdeationId.intValue()) {
    						table4.setSuicidalIdeation(table4.getSuicidalIdeation() + 1);
    					}
    					
    					else if(id.intValue()==hepatitisId.intValue()) {
    						table4.setHepatitis(table4.getHepatitis() + 1);
    					}
    					
    					else if(id.intValue()==renalFailureId.intValue()) {
    						table4.setRenalFailure(table4.getRenalFailure() + 1);
    					}
    					
    					else if(id.intValue()==qtProlongationId.intValue()) {
    						table4.setQtProlongation(table4.getQtProlongation() + 1);
    					}
    					
    				}
    			}
    		}
    	}
    	
    	
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
    	model.addAttribute("table4", table4);
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
        return "/module/mdrtb/reporting/pv/aeResults";
        //_" + Context.getLocale().toString().substring(0, 2);
    }
    
    
  
    
}
