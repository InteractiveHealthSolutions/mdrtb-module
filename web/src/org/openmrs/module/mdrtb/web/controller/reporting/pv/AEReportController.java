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
import org.openmrs.module.mdrtb.reporting.pv.PVDataTable2;
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
    	Integer regimenWithCfzLzdConceptId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.INDIVIDUAL_WITH_CFZ_LZD).getId();
    	Integer otherRegimenConceptId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OTHER_MDRTB_REGIMEN).getId();
    	
    	PVDataTable1 table1 = new PVDataTable1();
    	PVDataTable2 table2 = new PVDataTable2();
    	//PVDataTable1 table3 = new PVDataTable3();
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
      	    		table1.setRegimenWithBdqDlmEver(table1.getRegimenWithBdqDlmEver() + 1);
      	    		table1.setRegimenWithBdqDlmStarting(table1.getRegimenWithBdqDlmStarting() + 1);
      	    		
      	    	}
      	    	
      	    	else if(q.getConceptId().intValue()==regimenWithCfzLzdConceptId) {
      	    		table1.setRegimenWithCfzLzdEver(table1.getRegimenWithCfzLzdEver() + 1);
      	    		table1.setRegimenWithCfzLzdStarting(table1.getRegimenWithCfzLzdStarting() + 1);
      	    		
      	    	}
      	    	
      	    	else if(q.getConceptId().intValue()==otherRegimenConceptId) {
      	    		table1.setOtherRegimenEver(table1.getOtherRegimenEver() + 1);
      	    		table1.setOtherRegimenStarting(table1.getOtherRegimenStarting() + 1);
      	    		
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
    		      	             //table1.setStandardRegimenEver(table1.getStandardRegimenStarting() + 1);
    		      	    	}
    		      	    	
    		      	    	else if(q.getConceptId().intValue()==shortRegimenConceptId.intValue()) {
    		     	             table1.setShortRegimenEver(table1.getShortRegimenEver() + 1);
    		     	             //table1.setShortRegimenEver(table1.getShortRegimenStarting() + 1);
    		     	    	}
    		      	    	
    		      	    	else if(q.getConceptId().intValue()==regimenWithBdqConceptId.intValue()) {
    		     	             table1.setRegimenWithBdqEver(table1.getRegimenWithBdqEver() + 1);
    		     	             //table1.setRegimenWithBdqEver(table1.getRegimenWithBdqStarting() + 1);
    		     	    	}
    		      	    	
    		      	    	else if(q.getConceptId().intValue()==regimenWithDlmConceptId.intValue()) {
    		    	             table1.setRegimenWithDlmEver(table1.getRegimenWithDlmEver() + 1);
    		    	             //table1.setRegimenWithDlmEver(table1.getRegimenWithDlmStarting() + 1);
    		    	    	}
    		      	    	
    		      	    	else if(q.getConceptId().intValue()==regimenWithBdqDlmConceptId) {
    		      	    		table1.setRegimenWithBdqDlmEver(table1.getRegimenWithBdqDlmEver() + 1);
    		      	    		//table1.setRegimenWithBdqDlmStarting(table1.getRegimenWithBdqDlmStarting() + 1);
    		      	    		
    		      	    	}
    		      	    	
    		      	    	else if(q.getConceptId().intValue()==regimenWithCfzLzdConceptId) {
    		      	    		table1.setRegimenWithCfzLzdEver(table1.getRegimenWithCfzLzdEver() + 1);
    		      	    		//table1.setRegimenWithBdqDlmStarting(table1.getRegimenWithCfzLzdStarting() + 1);
    		      	    		
    		      	    	}
    		      	    	
    		      	    	else if(q.getConceptId().intValue()==otherRegimenConceptId) {
    		      	    		table1.setOtherRegimenEver(table1.getOtherRegimenEver() + 1);
    		      	    		
    		      	    		
    		      	    	}
    		      	    }
    				}
    			}
    		}
    	   
    	//end of Table 1
    	
    	
    	
    	
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
    	

    	Integer saeId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SERIOUS).getId();
    	Integer specialInterestId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OF_SPECIAL_INTEREST).getId();
    	Integer saeTypeId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SAE_TYPE).getId();
    	Integer specialInterestTypeId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SPECIAL_INTEREST_EVENT_TYPE).getId();
    	
    	Integer deathId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DEATH).getId();
    	Integer hospitilizationId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HOSPITALIZATION).getId();
    	Integer disabilityId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DISABILITY).getId();
    	Integer congenitalAbnormalityId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CONGENITAL_ANOMALY).getId();
    	Integer lifeThreateningExperienceId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LIFE_THREATENING_EXPERIENCE).getId();
    	
    	Integer psychiatricDisorderId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PSYCHIATRIC_DISORDER).getId();
    	Integer myelosuppressionId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MYELOSUPPRESSION).getId();
    	Integer lacticAcidosisId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LACTIC_ACIDOSIS).getId();
    	Integer hypokalemiaId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HYPOKALEMIA).getId();
    	Integer pancreatitisId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PANCREATITIS).getId();
    	Integer phospholipidosisId = Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PHOSPHOLIPIDOSIS).getId();
    	
    	ArrayList<AEForm> aeForms = Context.getService(MdrtbService.class).getAEFormsFilled(locList, year, quarter, month);
    	System.out.println("SZ:" + aeForms.size());
    	
    	boolean isStandard = false;
    	boolean isShort = false;
    	boolean isBdq = false;
    	boolean isDlm = false;
    	boolean isBdqDlm = false;
    	boolean isCfzLzd = false;
    	boolean isOther = false;
    	
    	
    	for(AEForm ae : aeForms) {
    		
    		 isStandard = false;
        	 isShort = false;
        	 isBdq = false;
        	 isDlm = false;
        	 isBdqDlm = false;
        	 isCfzLzd = false;
        	 isOther = false;
    		
    		//TABLE2
    		Concept q = ae.getTypeOfEvent();
    		Concept aq = null;
    		
    		Integer id = null;
    		Integer qi = null;
    		
    		if(q!=null) {
    			id = q.getId();
    			System.out.println("ID2: " + id);
    			Patient p = ae.getPatient();
    			RegimenForm currentRegimenForm = Context.getService(MdrtbService.class).getCurrentRegimenFormForPatient(p, ae.getEncounterDatetime());
    			Integer regimenTypeConceptId = null;
    			if(currentRegimenForm != null) {
    				Concept c = currentRegimenForm.getSldRegimenType();
    				if(c!=null) {
    					regimenTypeConceptId = c.getId();
    				}
    				
    			}
    			
    			if(regimenTypeConceptId != null) {
    				if(regimenTypeConceptId.intValue()==standardRegimenConceptId.intValue()) {
    					isStandard = true;
    					
    				}
    			
    				else if(regimenTypeConceptId.intValue()==shortRegimenConceptId.intValue()) {
    					isShort = true;
    				}
    				
    				else if(regimenTypeConceptId.intValue()==regimenWithBdqConceptId.intValue()) {
    					isBdq = true;
    				}
    				
    				else if(regimenTypeConceptId.intValue()==regimenWithDlmConceptId.intValue()) {
    					isDlm = true;
    				}
    				
    				else if(regimenTypeConceptId.intValue()==regimenWithBdqDlmConceptId.intValue()) {
    					isBdqDlm = true;
    				}
    				
    				else if(regimenTypeConceptId.intValue()==regimenWithCfzLzdConceptId.intValue()) {
    					isCfzLzd = true;
    				}
    				
    				else if(regimenTypeConceptId.intValue()==otherRegimenConceptId.intValue()) {
    					isOther = true;
    				}
    				
    				
    			
    			}
    			
    			
    			
    			
    			if(id!=null && id.intValue() == saeId.intValue()) {
    				aq = ae.getTypeOfSAE();
    				if(isStandard) {
						table2.setSaeStandard(table2.getSaeStandard()+1);
					}
					else if(isShort) {
						table2.setSaeShort(table2.getSaeShort()+1);
					}
					else if(isBdq) {
						table2.setSaeBdq(table2.getSaeBdq()+1);
					}
					else if(isDlm) {
						table2.setSaeDlm(table2.getSaeDlm()+1);
					}
					else if(isBdqDlm) {
						table2.setSaeBdqAndDlm(table2.getSaeBdqAndDlm()+1);
					}
					else if(isCfzLzd) {
						table2.setSaeCfzLzd(table2.getSaeCfzLzd()+1);
					}
					else if(isOther) {
						table2.setSaeOther(table2.getSaeOther()+1);
					}
					
					
    				table2.setSaeTotal(table2.getSaeTotal()+1);
    				if(aq==null)
    					continue;
    				
    				qi = aq.getId();
    				
    				
    				if(qi.intValue() == deathId.intValue()) {
    					if(isStandard) {
    						table2.setDeathStandard(table2.getDeathStandard()+1);
    					}
    					else if(isShort) {
    						table2.setDeathShort(table2.getDeathShort()+1);
    					}
    					else if(isBdq) {
    						table2.setDeathBdq(table2.getDeathBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setDeathDlm(table2.getDeathDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setDeathBdqAndDlm(table2.getDeathBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setDeathCfzLzd(table2.getDeathCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setDeathOther(table2.getDeathOther()+1);
    					}
    					
    					table2.setDeathTotal(table2.getDeathTotal() + 1);
    				}
    				
    				else if(qi.intValue() == hospitilizationId.intValue()) {
    					if(isStandard) {
    						table2.setHospitalizationStandard(table2.getHospitalizationStandard()+1);
    					}
    					else if(isShort) {
    						table2.setHospitalizationShort(table2.getHospitalizationShort()+1);
    					}
    					else if(isBdq) {
    						table2.setHospitalizationBdq(table2.getHospitalizationBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setHospitalizationDlm(table2.getHospitalizationDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setHospitalizationBdqAndDlm(table2.getHospitalizationBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setHospitalizationCfzLzd(table2.getHospitalizationCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setHospitalizationOther(table2.getHospitalizationOther()+1);
    					}
    					
    					table2.setHospitalizationTotal(table2.getHospitalizationTotal() + 1);
    				}
    				
    				else if(qi.intValue() == disabilityId.intValue()) {
    					if(isStandard) {
    						table2.setDisabilityStandard(table2.getDisabilityStandard()+1);
    					}
    					else if(isShort) {
    						table2.setDisabilityShort(table2.getDisabilityShort()+1);
    					}
    					else if(isBdq) {
    						table2.setDisabilityBdq(table2.getDisabilityBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setDisabilityDlm(table2.getDisabilityDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setDisabilityBdqAndDlm(table2.getDisabilityBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setDisabilityCfzLzd(table2.getDisabilityCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setDisabilityOther(table2.getDisabilityOther()+1);
    					}
    					
    					table2.setDisabilityTotal(table2.getDisabilityTotal() + 1);
    				}
    				
    				else if(qi.intValue() == congenitalAbnormalityId.intValue()) {
    					if(isStandard) {
    						table2.setCongenitalAbnormalityStandard(table2.getCongenitalAbnormalityStandard()+1);
    					}
    					else if(isShort) {
    						table2.setCongenitalAbnormalityShort(table2.getCongenitalAbnormalityShort()+1);
    					}
    					else if(isBdq) {
    						table2.setCongenitalAbnormalityBdq(table2.getCongenitalAbnormalityBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setCongenitalAbnormalityDlm(table2.getCongenitalAbnormalityDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setCongenitalAbnormalityBdqAndDlm(table2.getCongenitalAbnormalityBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setCongenitalAbnormalityCfzLzd(table2.getCongenitalAbnormalityCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setCongenitalAbnormalityOther(table2.getCongenitalAbnormalityOther()+1);
    					}
    					
    					table2.setCongenitalAbnormalityTotal(table2.getCongenitalAbnormalityTotal() + 1);
    				}
    				
    				else if(qi.intValue() == lifeThreateningExperienceId.intValue()) {
    					if(isStandard) {
    						table2.setLifeThreateningExperienceStandard(table2.getLifeThreateningExperienceStandard()+1);
    					}
    					else if(isShort) {
    						table2.setLifeThreateningExperienceShort(table2.getLifeThreateningExperienceShort()+1);
    					}
    					else if(isBdq) {
    						table2.setLifeThreateningExperienceBdq(table2.getLifeThreateningExperienceBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setLifeThreateningExperienceDlm(table2.getLifeThreateningExperienceDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setLifeThreateningExperienceBdqAndDlm(table2.getLifeThreateningExperienceBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setLifeThreateningExperienceCfzLzd(table2.getLifeThreateningExperienceCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setLifeThreateningExperienceOther(table2.getLifeThreateningExperienceOther()+1);
    					}
    					
    					table2.setLifeThreateningExperienceTotal(table2.getLifeThreateningExperienceTotal() + 1);
    				}
    				
    			}
    			
    			
    			else if(id!=null && id.intValue() == specialInterestId.intValue()) {
    				aq = ae.getTypeOfSpecialEvent();
    				if(isStandard) {
						table2.setSpecialInterestStandard(table2.getSpecialInterestStandard()+1);
					}
					else if(isShort) {
						table2.setSpecialInterestShort(table2.getSpecialInterestShort()+1);
					}
					else if(isBdq) {
						table2.setSpecialInterestBdq(table2.getSpecialInterestBdq()+1);
					}
					else if(isDlm) {
						table2.setSpecialInterestDlm(table2.getSpecialInterestDlm()+1);
					}
					else if(isBdqDlm) {
						table2.setSpecialInterestBdqAndDlm(table2.getSpecialInterestBdqAndDlm()+1);
					}
					else if(isCfzLzd) {
						table2.setSpecialInterestCfzLzd(table2.getSpecialInterestCfzLzd()+1);
					}
					else if(isOther) {
						table2.setSpecialInterestOther(table2.getSpecialInterestOther()+1);
					}
					
					
    				table2.setSpecialInterestTotal(table2.getSpecialInterestTotal()+1);
    				
    				if(aq==null)
    					continue;
    				
    				qi = aq.getId();
    				
    				if(qi.intValue() == peripheralNeuropathyId.intValue()) {
    					if(isStandard) {
    						table2.setPeripheralNeuropathyStandard(table2.getPeripheralNeuropathyStandard()+1);
    					}
    					else if(isShort) {
    						table2.setPeripheralNeuropathyShort(table2.getPeripheralNeuropathyShort()+1);
    					}
    					else if(isBdq) {
    						table2.setPeripheralNeuropathyBdq(table2.getPeripheralNeuropathyBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setPeripheralNeuropathyDlm(table2.getPeripheralNeuropathyDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setPeripheralNeuropathyBdqAndDlm(table2.getPeripheralNeuropathyBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setPeripheralNeuropathyCfzLzd(table2.getPeripheralNeuropathyCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setPeripheralNeuropathyOther(table2.getPeripheralNeuropathyOther()+1);
    					}
    					
    					table2.setPeripheralNeuropathyTotal(table2.getPeripheralNeuropathyTotal() + 1);
    				}
    				
    				else if(qi.intValue() == psychiatricDisorderId.intValue()) {
    					if(isStandard) {
    						table2.setPsychiatricDisorderStandard(table2.getPsychiatricDisorderStandard()+1);
    					}
    					else if(isShort) {
    						table2.setPsychiatricDisorderShort(table2.getPsychiatricDisorderShort()+1);
    					}
    					else if(isBdq) {
    						table2.setPsychiatricDisorderBdq(table2.getPsychiatricDisorderBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setPsychiatricDisorderDlm(table2.getPsychiatricDisorderDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setPsychiatricDisorderBdqAndDlm(table2.getPsychiatricDisorderBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setPsychiatricDisorderCfzLzd(table2.getPsychiatricDisorderCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setPsychiatricDisorderOther(table2.getPsychiatricDisorderOther()+1);
    					}
    					
    					table2.setPsychiatricDisorderTotal(table2.getPsychiatricDisorderTotal() + 1);
    				}
    				
    				else if(qi.intValue() == visualDisturbancesId.intValue()) {
    					if(isStandard) {
    						table2.setOpticNerveDisorderStandard(table2.getOpticNerveDisorderStandard()+1);
    					}
    					else if(isShort) {
    						table2.setOpticNerveDisorderShort(table2.getOpticNerveDisorderShort()+1);
    					}
    					else if(isBdq) {
    						table2.setOpticNerveDisorderBdq(table2.getOpticNerveDisorderBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setOpticNerveDisorderDlm(table2.getOpticNerveDisorderDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setOpticNerveDisorderBdqAndDlm(table2.getOpticNerveDisorderBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setOpticNerveDisorderCfzLzd(table2.getOpticNerveDisorderCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setOpticNerveDisorderOther(table2.getOpticNerveDisorderOther()+1);
    					}
    					
    					table2.setOpticNerveDisorderTotal(table2.getOpticNerveDisorderTotal() + 1);
    				}
    				
    				else if(qi.intValue() == hearingDisturbancesId.intValue()) {
    					if(isStandard) {
    						table2.setOtotoxicityStandard(table2.getOtotoxicityStandard()+1);
    					}
    					else if(isShort) {
    						table2.setOtotoxicityShort(table2.getOtotoxicityShort()+1);
    					}
    					else if(isBdq) {
    						table2.setOtotoxicityBdq(table2.getOtotoxicityBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setOtotoxicityDlm(table2.getOtotoxicityDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setOtotoxicityBdqAndDlm(table2.getOtotoxicityBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setOtotoxicityCfzLzd(table2.getOtotoxicityCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setOtotoxicityOther(table2.getOtotoxicityOther()+1);
    					}
    					
    					table2.setOtotoxicityTotal(table2.getOtotoxicityTotal() + 1);
    				}
    				
    				else if(qi.intValue() == myelosuppressionId.intValue()) {
    					if(isStandard) {
    						table2.setMyelosuppressionStandard(table2.getMyelosuppressionStandard()+1);
    					}
    					else if(isShort) {
    						table2.setMyelosuppressionShort(table2.getMyelosuppressionShort()+1);
    					}
    					else if(isBdq) {
    						table2.setMyelosuppressionBdq(table2.getMyelosuppressionBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setMyelosuppressionDlm(table2.getMyelosuppressionDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setMyelosuppressionBdqAndDlm(table2.getMyelosuppressionBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setMyelosuppressionCfzLzd(table2.getMyelosuppressionCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setMyelosuppressionOther(table2.getMyelosuppressionOther()+1);
    					}
    					
    					table2.setMyelosuppressionTotal(table2.getMyelosuppressionTotal() + 1);
    				}
    				
    				else if(qi.intValue() == qtProlongationId.intValue()) {
    					if(isStandard) {
    						table2.setQtProlongationStandard(table2.getQtProlongationStandard()+1);
    					}
    					else if(isShort) {
    						table2.setQtProlongationShort(table2.getQtProlongationShort()+1);
    					}
    					else if(isBdq) {
    						table2.setQtProlongationBdq(table2.getQtProlongationBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setQtProlongationDlm(table2.getQtProlongationDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setQtProlongationBdqAndDlm(table2.getQtProlongationBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setQtProlongationCfzLzd(table2.getQtProlongationCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setQtProlongationOther(table2.getQtProlongationOther()+1);
    					}
    					
    					table2.setQtProlongationTotal(table2.getQtProlongationTotal() + 1);
    				}
    				
    				else if(qi.intValue() == lacticAcidosisId.intValue()) {
    					if(isStandard) {
    						table2.setLacticAcidosisStandard(table2.getLacticAcidosisStandard()+1);
    					}
    					else if(isShort) {
    						table2.setLacticAcidosisShort(table2.getLacticAcidosisShort()+1);
    					}
    					else if(isBdq) {
    						table2.setLacticAcidosisBdq(table2.getLacticAcidosisBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setLacticAcidosisDlm(table2.getLacticAcidosisDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setLacticAcidosisBdqAndDlm(table2.getLacticAcidosisBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setLacticAcidosisCfzLzd(table2.getLacticAcidosisCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setLacticAcidosisOther(table2.getLacticAcidosisOther()+1);
    					}
    					
    					table2.setLacticAcidosisTotal(table2.getLacticAcidosisTotal() + 1);
    				}
    				
    				else if(qi.intValue() == hepatitisId.intValue()) {
    					if(isStandard) {
    						table2.setHepatitisStandard(table2.getHepatitisStandard()+1);
    					}
    					else if(isShort) {
    						table2.setHepatitisShort(table2.getHepatitisShort()+1);
    					}
    					else if(isBdq) {
    						table2.setHepatitisBdq(table2.getHepatitisBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setHepatitisDlm(table2.getHepatitisDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setHepatitisBdqAndDlm(table2.getHepatitisBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setHepatitisCfzLzd(table2.getHepatitisCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setHepatitisOther(table2.getHepatitisOther()+1);
    					}
    					
    					table2.setHepatitisTotal(table2.getHepatitisTotal() + 1);
    				}
    				
    				else if(qi.intValue() == hypothyroidismId.intValue()) {
    					if(isStandard) {
    						table2.setHypothyroidismStandard(table2.getHypothyroidismStandard()+1);
    					}
    					else if(isShort) {
    						table2.setHypothyroidismShort(table2.getHypothyroidismShort()+1);
    					}
    					else if(isBdq) {
    						table2.setHypothyroidismBdq(table2.getHypothyroidismBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setHypothyroidismDlm(table2.getHypothyroidismDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setHypothyroidismBdqAndDlm(table2.getHypothyroidismBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setHypothyroidismCfzLzd(table2.getHypothyroidismCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setHypothyroidismOther(table2.getHypothyroidismOther()+1);
    					}
    					
    					table2.setHypothyroidismTotal(table2.getHypothyroidismTotal() + 1);
    				}
    				
    				else if(qi.intValue() == hypokalemiaId.intValue()) {
    					if(isStandard) {
    						table2.setHypokalemiaStandard(table2.getHypokalemiaStandard()+1);
    					}
    					else if(isShort) {
    						table2.setHypokalemiaShort(table2.getHypokalemiaShort()+1);
    					}
    					else if(isBdq) {
    						table2.setHypokalemiaBdq(table2.getHypokalemiaBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setHypokalemiaDlm(table2.getHypokalemiaDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setHypokalemiaBdqAndDlm(table2.getHypokalemiaBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setHypokalemiaCfzLzd(table2.getHypokalemiaCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setHypokalemiaOther(table2.getHypokalemiaOther()+1);
    					}
    					
    					table2.setHypokalemiaTotal(table2.getHypokalemiaTotal() + 1);
    				}
    				
    				else if(qi.intValue() == pancreatitisId.intValue()) {
    					if(isStandard) {
    						table2.setPancreatitisStandard(table2.getPancreatitisStandard()+1);
    					}
    					else if(isShort) {
    						table2.setPancreatitisShort(table2.getPancreatitisShort()+1);
    					}
    					else if(isBdq) {
    						table2.setPancreatitisBdq(table2.getPancreatitisBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setPancreatitisDlm(table2.getPancreatitisDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setPancreatitisBdqAndDlm(table2.getPancreatitisBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setPancreatitisCfzLzd(table2.getPancreatitisCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setPancreatitisOther(table2.getPancreatitisOther()+1);
    					}
    					
    					table2.setPancreatitisTotal(table2.getPancreatitisTotal() + 1);
    				}
    				
    				else if(qi.intValue() == phospholipidosisId.intValue()) {
    					if(isStandard) {
    						table2.setPhospholipidosisStandard(table2.getPhospholipidosisStandard()+1);
    					}
    					else if(isShort) {
    						table2.setPhospholipidosisShort(table2.getPhospholipidosisShort()+1);
    					}
    					else if(isBdq) {
    						table2.setPhospholipidosisBdq(table2.getPhospholipidosisBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setPhospholipidosisDlm(table2.getPhospholipidosisDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setPhospholipidosisBdqAndDlm(table2.getPhospholipidosisBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setPhospholipidosisCfzLzd(table2.getPhospholipidosisCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setPhospholipidosisOther(table2.getPhospholipidosisOther()+1);
    					}
    					
    					table2.setPhospholipidosisTotal(table2.getPhospholipidosisTotal() + 1);
    				}
    				
    				else if(qi.intValue() == renalFailureId.intValue()) {
    					if(isStandard) {
    						table2.setRenalFailureStandard(table2.getRenalFailureStandard()+1);
    					}
    					else if(isShort) {
    						table2.setRenalFailureShort(table2.getRenalFailureShort()+1);
    					}
    					else if(isBdq) {
    						table2.setRenalFailureBdq(table2.getRenalFailureBdq()+1);
    					}
    					else if(isDlm) {
    						table2.setRenalFailureDlm(table2.getRenalFailureDlm()+1);
    					}
    					else if(isBdqDlm) {
    						table2.setRenalFailureBdqAndDlm(table2.getRenalFailureBdqAndDlm()+1);
    					}
    					else if(isCfzLzd) {
    						table2.setRenalFailureCfzLzd(table2.getRenalFailureCfzLzd()+1);
    					}
    					else if(isOther) {
    						table2.setRenalFailureOther(table2.getRenalFailureOther()+1);
    					}
    					
    					table2.setRenalFailureTotal(table2.getRenalFailureTotal() + 1);
    				}
    			}
    			
    			if(isStandard) {
					table2.setTotalStandard(table2.getTotalStandard()+1);
				}
				else if(isShort) {
					table2.setTotalShort(table2.getTotalShort()+1);
				}
				else if(isBdq) {
					table2.setTotalBdq(table2.getTotalBdq()+1);
				}
				else if(isDlm) {
					table2.setTotalDlm(table2.getTotalDlm()+1);
				}
				else if(isBdqDlm) {
					table2.setTotalBdqAndDlm(table2.getTotalBdqAndDlm()+1);
				}
				else if(isCfzLzd) {
					table2.setTotalCfzLzd(table2.getTotalCfzLzd()+1);
				}
				else if(isOther) {
					table2.setTotalOther(table2.getTotalOther()+1);
				}
				
				table2.setTotalTotal(table2.getTotalTotal() + 1);
    			
    			
    		}
    	}
    		
    		
    		
    		//TABLE3
    		
    		
    		/*q = ae.getActionTaken();
    		id = null;
 */   		
    		
    	for(AEForm ae : aeForms) {	
    		
    		// TABLE 4
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
    	model.addAttribute("table2", table2);
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
