package org.openmrs.module.mdrtb.web.controller.reporting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientProgram;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.form.CultureForm;
import org.openmrs.module.mdrtb.form.DSTForm;
import org.openmrs.module.mdrtb.form.Form89;
import org.openmrs.module.mdrtb.form.HAIN2Form;
import org.openmrs.module.mdrtb.form.HAINForm;
import org.openmrs.module.mdrtb.form.SmearForm;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.form.TB03uForm;
import org.openmrs.module.mdrtb.form.TransferInForm;
import org.openmrs.module.mdrtb.form.XpertForm;
import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.program.TbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.specimen.DstImpl;


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
    		
    	SimpleDateFormat sdf = Context.getDateFormat();
    	sdf.setLenient(false);	
    	
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
    		Collections.sort(tb03s);
    		
    		model.addAttribute("listName", getMessage("mdrtb.allCasesEnrolled"));
    		String report = "";
    		
    		//NEW CASES 
    		
    		//report += "<h4>" + getMessage("mdrtb.pulmonary") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfRegistration") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.treatmentStartDate") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.treatmentSiteIP") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.gender") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.tbLocalization") + closeTD();
    		report += openTD() + getMessage("mdrtb.lists.caseDefinition") + closeTD();
    		//report += openTD() + getMessage("mdrtb.tb03.tbLocalization") + closeTD();
    		report += openTD() + getMessage("mdrtb.lists.microscopy") + closeTD();
    		report += openTD() + getMessage("mdrtb.xpert") + closeTD();
    		report += "<td align=\"center\" colspan=\"2\">" + getMessage("mdrtb.hain1") + closeTD();
    		report += "<td align=\"center\" colspan=\"2\">" + getMessage("mdrtb.hain2") + closeTD();
    		report += openTD() + getMessage("mdrtb.culture") + closeTD();
    		report += openTD() + getMessage("mdrtb.lists.drugResistance") + closeTD();
    		report += openTD() + getMessage("mdrtb.lists.resistantTo") + closeTD();
    		report += openTD() + getMessage("mdrtb.lists.sensitiveTo") + closeTD();
    		report += openTD() + getMessage("mdrtb.hivStatus") + closeTD();
    		report += openTD() + getMessage("mdrtb.lists.outcome") + closeTD();
    		report += openTD() + getMessage("mdrtb.lists.endOfTreatmentDate") + closeTD();
    		report += openTD() + getMessage("mdrtb.lists.reregisrationNumber") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		report += openTR();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + getMessage("mdrtb.resistantShort") + closeTD();
    		report += openTD() + getMessage("mdrtb.sensitiveShort") + closeTD();
    		report += openTD() + getMessage("mdrtb.resistantShort") + closeTD();
    		report += openTD() + getMessage("mdrtb.sensitiveShort") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		report += openTD() + "" + closeTD();
    		
    		report += closeTR();
    		
    		int i = 0;
    		Person p = null;
    		for(TB03Form tf : tb03s) {
    				if(tf.getPatient()==null || tf.getPatient().isVoided())
    					continue;
    				i++;
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += openTD() + sdf.format(tf.getEncounterDatetime()) +  closeTD();
    				if(tf.getTreatmentStartDate()!=null)
    					report += openTD() + sdf.format(tf.getTreatmentStartDate()) +  closeTD();
    				else 
    					report += openTD() + "" +  closeTD();
    				
    				if(tf.getTreatmentSiteIP()!=null) {
    					report += openTD() + tf.getTreatmentSiteIP().getName().getName() +  closeTD();
    				}
    				
    				else
    					report += openTD() + "" +  closeTD();
    				
    				report += openTD() + p.getFamilyName() + "," + p.getGivenName() + closeTD();
    		    	report += openTD() + sdf.format(p.getBirthdate()) + closeTD();
    		    	report += openTD() + tf.getAgeAtTB03Registration() + closeTD();
    		    	report += openTD() + getGender(p) + closeTD();
    		    	
    		    	if( tf.getAnatomicalSite()!=null)
    		    		report += openTD() + tf.getAnatomicalSite().getName().getName().charAt(0) + closeTD();
    		    	else
    					report += openTD() + "" +  closeTD();
    		    	
    		    	if(tf.getRegistrationGroup()!=null)
    		    		report += openTD() + tf.getRegistrationGroup().getName().getName() +  closeTD();
    		    	else 
    		    		report += openTD() + "" +  closeTD();
    		    	
    		    	//SMEAR
    		    	List<SmearForm> smears = tf.getSmears();
    		    	if(smears!=null && smears.size()!=0) {
    		    		Collections.sort(smears);
    		    		
    		    		SmearForm ds = smears.get(0);
    		    		
    		    		if(ds.getSmearResult()!=null) {
    		    			
    		    			if(ds.getSmearResult().getConceptId().intValue()==ms.getConcept(TbConcepts.NEGATIVE).getConceptId().intValue()) {
    		    				report += openTD() + getMessage("mdrtb.negativeShort") + closeTD();
    		    			}
    		    			
    		    			else {
    		    				Integer[] concs = MdrtbUtil.getPositiveResultConceptIds();
    		    				for (int index = 0; index<concs.length; index++) {
    		    					if(concs[index].intValue() == ds.getSmearResult().getConceptId().intValue()) {
    		    						report += openTD() + getMessage("mdrtb.positiveShort") + closeTD();
    		    						break;
    		    					}
    		    					
    		    				}
    		    			}
    		    		}
    		    		
    		    		else {
    		    			report += openTD() + "" + closeTD();
    		    		}
    		    	}
    		    	
    		    	else {
		    			report += openTD() + "" + closeTD();
		    		}
    		    	
    		    	//XPERT
    		    	List<XpertForm> xperts = tf.getXperts();
    		    	if(xperts!=null && xperts.size()!=0) {
    		    		Collections.sort(xperts);
    		    		
    		    		XpertForm dx = xperts.get(0);
    		    		Concept mtb = dx.getMtbResult();
    		    		Concept res = dx.getRifResult();
    		    		
    		    		if(mtb==null) {
    		    			report += openTD() + "" + closeTD();
    		    		}
    		    		
    		    		else {
    		    			if(mtb.getConceptId().intValue()==ms.getConcept(TbConcepts.POSITIVE).getConceptId().intValue() || mtb.getConceptId().intValue()==ms.getConcept(TbConcepts.MTB_POSITIVE).getConceptId().intValue()) {
    		    				String xr = getMessage("mdrtb.positiveShort");
    		    				
    		    				if(res!=null) {
    		    					int resId = res.getConceptId().intValue();
    		    					
    		    					if(resId == ms.getConcept(TbConcepts.DETECTED).getConceptId().intValue()) {
    		    						xr += "/" + getMessage("mdrtb.resistantShort");
    		    						report += openTD() + xr + closeTD();
    		    					}
    		    					
    		    					else if(resId == ms.getConcept(TbConcepts.NOT_DETECTED).getConceptId().intValue()) {
    		    						xr += "/" + getMessage("mdrtb.sensitiveShort");
    		    						report += openTD() + xr + closeTD();
    		    					}
    		    					
    		    					else {
    		    						report += openTD() + xr + closeTD();
    		    					}
    		    				}
    		    				
    		    				else {
    		    					report += openTD() + xr + closeTD();
    		    				}
    		    			} 
    		    			
    		    			else if(mtb.getConceptId().intValue()==ms.getConcept(TbConcepts.MTB_NEGATIVE).getConceptId().intValue()) {
    		    				report += openTD() + getMessage("mdrtb.negativeShort") + closeTD();
    		    			}
    		    			
    		    			else {
    		    				report += openTD() + "" + closeTD();
    		    			}
    		    		}
    		    		
    		    	}
    		    	
    		    	else {
    		    		report += openTD() + "" + closeTD();
    		    	}
    		    		
    		    	//HAIN 1	
    		    	List<HAINForm> hains = tf.getHains();
    		    	if(hains!=null && hains.size()!=0) {
    		    		Collections.sort(hains);
    		    		
    		    		HAINForm h = hains.get(0);
    		    		
    		    		Concept ih = h.getInhResult();
    		    		Concept rh = h.getRifResult();
    		    		
    		    		String res = "";
    		    		String sen = "";
    		    		
    		    		if(ih!=null) {
    		    			int concId = ih.getConceptId().intValue();
    		    			
    		    			if(concId==ms.getConcept(TbConcepts.DETECTED).getConceptId().intValue()) {
    		    				res = ms.getConcept(TbConcepts.ISONIAZID).getName().getShortName();
    		    			}
    		    			
    		    			else if(concId==ms.getConcept(TbConcepts.NOT_DETECTED).getConceptId().intValue()) {
    		    				sen = ms.getConcept(TbConcepts.ISONIAZID).getName().getShortName();
    		    			}
    		    		}
    		    		
    		    		if(rh!=null) {
    		    			int concId = rh.getConceptId().intValue();
    		    			
    		    			if(concId==ms.getConcept(TbConcepts.DETECTED).getConceptId().intValue()) {
    		    				if(res.length()==0)
    		    					res = ms.getConcept(TbConcepts.RIFAMPICIN).getName().getShortName();
    		    				else 
    		    					res += "," + ms.getConcept(TbConcepts.RIFAMPICIN).getName().getShortName();
    		    			}
    		    			
    		    			else if(concId==ms.getConcept(TbConcepts.NOT_DETECTED).getConceptId().intValue()) {
    		    				if(sen.length()==0)
    		    					sen = ms.getConcept(TbConcepts.RIFAMPICIN).getName().getShortName();
    		    				else 
    		    					sen += "," + ms.getConcept(TbConcepts.RIFAMPICIN).getName().getShortName();
    		    			}
    		    		}
    		    		
    		    		report += openTD() + res + closeTD();
    		    		report += openTD() + sen + closeTD();
    		    	}
    		    	
    		    	else {
    		    		report += openTD() + "" + closeTD();
    		    		report += openTD() + "" + closeTD();
    		    	}
    		    	
    		    	
    		    	//HAIN 2
    		    	List<HAIN2Form> hain2s = tf.getHain2s();
    		    	if(hain2s!=null && hain2s.size()!=0) {
    		    		Collections.sort(hain2s);
    		    		
    		    		HAIN2Form h = hain2s.get(0);
    		    		
    		    		Concept ih = h.getInjResult();
    		    		Concept fh = h.getFqResult();
    		    		
    		    		String res = "";
    		    		String sen = "";
    		    		
    		    		if(ih!=null) {
    		    			int concId = ih.getConceptId().intValue();
    		    			
    		    			if(concId==ms.getConcept(TbConcepts.DETECTED).getConceptId().intValue()) {
    		    				res = getMessage("mdrtb.lists.injShort");
    		    			}
    		    			
    		    			else if(concId==ms.getConcept(TbConcepts.NOT_DETECTED).getConceptId().intValue()) {
    		    				sen = getMessage("mdrtb.lists.injShort");
    		    			}
    		    		}
    		    		
    		    		if(fh!=null) {
    		    			int concId = fh.getConceptId().intValue();
    		    			
    		    			if(concId==ms.getConcept(TbConcepts.DETECTED).getConceptId().intValue()) {
    		    				if(res.length()==0)
    		    					res = getMessage("mdrtb.lists.fqShort");
    		    				else 
    		    					res += "," + getMessage("mdrtb.lists.fqShort");
    		    			}
    		    			
    		    			else if(concId==ms.getConcept(TbConcepts.NOT_DETECTED).getConceptId().intValue()) {
    		    				if(sen.length()==0)
    		    					sen = getMessage("mdrtb.lists.fqShort");
    		    				else 
    		    					sen += "," + getMessage("mdrtb.lists.fqShort");
    		    			}
    		    		}
    		    		
    		    		report += openTD() + res + closeTD();
    		    		report += openTD() + sen + closeTD();
    		    	}
    		    	
    		    	else {
    		    		report += openTD() + "" + closeTD();
    		    		report += openTD() + "" + closeTD();
    		    	}
    		    	
    		    	
    		    	//CULTURE
    		    	List<CultureForm> cultures = tf.getCultures();
    		    	if(cultures!=null && cultures.size()!=0) {
    		    		Collections.sort(cultures);
    		    		
    		    		CultureForm dc = cultures.get(0);
    		    		
    		    		if(dc.getCultureResult()!=null) {
    		    			
    		    			if(dc.getCultureResult().getConceptId().intValue()==ms.getConcept(TbConcepts.NEGATIVE).getConceptId().intValue()) {
    		    				report += openTD() + getMessage("mdrtb.negativeShort") + closeTD();
    		    			}
    		    			
    		    			else if(dc.getCultureResult().getConceptId().intValue()==ms.getConcept(TbConcepts.CULTURE_GROWTH).getConceptId().intValue()) {
    		    				report += openTD() + getMessage("mdrtb.lists.growth") + closeTD();
    		    			}
    		    			
    		    			else {
    		    				Integer[] concs = MdrtbUtil.getPositiveResultConceptIds();
    		    				for (int index = 0; index<concs.length; index++) {
    		    					if(concs[index].intValue() == dc.getCultureResult().getConceptId().intValue()) {
    		    						report += openTD() + getMessage("mdrtb.positiveShort") + closeTD();
    		    						break;
    		    					}
    		    					
    		    				}
    		    			}
    		    		}
    		    		
    		    		else {
    		    			report += openTD() + "" + closeTD();
    		    		}
    		    	}
    		    	
    		    	else {
		    			report += openTD() + "" + closeTD();
		    		}
    		    	
    		    	//Drug Resistance
    		    	if(tf.getResistanceType()!=null) {
    		    		report += openTD() + tf.getResistanceType().getName().getName() + closeTD();
    		    	}
    		    	
    		    	else {
    		    		report += openTD() + "" + closeTD();
    		    	}
    		    	
    		    	report += openTD() + getResistantDrugs(tf) + closeTD();
    		    	report += openTD() + getSensitiveDrugs(tf) + closeTD();
    		    	
    		    	
    		    	
    		    	if(tf.getHivStatus()!=null) {
    		    		report += openTD() + tf.getHivStatus().getName().getName() + closeTD();
	    			}
	    	
    		    	else {
    		    		report += openTD() + "" + closeTD();
    		    	}
    		    	
    		    	if(tf.getTreatmentOutcome()!=null) {
    		    		report += openTD() + tf.getTreatmentOutcome().getName().getName() + closeTD();
	    			}
	    	
    		    	else {
    		    		report += openTD() + "" + closeTD();
    		    	}
    		    	
    		    	if(tf.getTreatmentOutcomeDate()!=null) {
    		    		report += openTD() + sdf.format(tf.getTreatmentOutcomeDate()) + closeTD();
	    			}
	    	
    		    	else {
    		    		report += openTD() + "" + closeTD();
    		    	}
    		    	
    		    	//OTHER NUMBER
    		    	report += openTD() + getReRegistrationNumber(tf) + closeTD();
    		
    		    	report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    		}
    		
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
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
    		
    		Collections.sort(tb03s);
    		
    		//NEW CASES 
    		Concept newConcept = ms.getConcept(TbConcepts.NEW);
    		report += "<h4>" + getMessage("mdrtb.lists.new") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i = 0;
    		for(TB03Form tf : tb03s) {
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==newConcept.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + tf.getAgeAtTB03Registration() +  closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//Relapse
    		
    		Concept relapse1Concept = ms.getConcept(TbConcepts.RELAPSE_AFTER_REGIMEN_1);
    		Concept relapse2Concept = ms.getConcept(TbConcepts.RELAPSE_AFTER_REGIMEN_2);
    		report += "<h4>" + getMessage("mdrtb.lists.relapses") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		
    		p = null;
    		i = 0;
    		for(TB03Form tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			
    			if(temp!=null && temp.getValueCoded()!=null && (temp.getValueCoded().getId().intValue()==relapse1Concept.getId().intValue() || temp.getValueCoded().getId().intValue()==relapse2Concept.getId().intValue())) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + tf.getAgeAtTB03Registration() +  closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//Retreament
    		Concept default1Concept = ms.getConcept(TbConcepts.DEFAULT_AFTER_REGIMEN_1);
    		Concept default2Concept = ms.getConcept(TbConcepts.DEFAULT_AFTER_REGIMEN_2);
    		Concept failure1Concept = ms.getConcept(TbConcepts.AFTER_FAILURE_REGIMEN_1);
    		Concept failure2Concept = ms.getConcept(TbConcepts.AFTER_FAILURE_REGIMEN_1);
    		report += "<h4>" + getMessage("mdrtb.lists.retreatment") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		
    		p = null;
    		i=0;
    		for(TB03Form tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && 
    					(temp.getValueCoded().getId().intValue()==default1Concept.getId().intValue() ||
    					temp.getValueCoded().getId().intValue()==default2Concept.getId().intValue() || 
    					temp.getValueCoded().getId().intValue()==failure1Concept.getId().intValue() ||
    					temp.getValueCoded().getId().intValue()==failure2Concept.getId().intValue())
    					) {
    				
    				i++;
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + tf.getAgeAtTB03Registration() +  closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//Transfer In
    		Concept transferInConcept = ms.getConcept(TbConcepts.TRANSFER);
    		
    		report += "<h4>" + getMessage("mdrtb.lists.transferIn") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.transferLocation") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfTransfer") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		temp = null;
    		i=0;
    		p = null;
    		for(TB03Form tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && 
    					temp.getValueCoded().getId().intValue()==transferInConcept.getId().intValue())
    					 {
    				
    				i++;
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + tf.getAgeAtTB03Registration() +  closeTD();
    				report += openTD() + getTransferFrom(tf) +  closeTD();
    				report += openTD() + getTransferFromDate(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    //////////

    @RequestMapping("/module/mdrtb/reporting/dotsCasesByAnatomicalSite")
    public  String dotsCasesByAnatomicalSite(@RequestParam("district") Integer districtId,
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
    		model.addAttribute("listName", getMessage("mdrtb.dotsCasesByAnatomicalSite"));
    		String report = "";
    		
    		Concept groupConcept = ms.getConcept(TbConcepts.ANATOMICAL_SITE_OF_TB);
    		
    		
    		ArrayList<TB03Form> tb03s = Context.getService(MdrtbService.class).getTB03FormsFilled(locList,year,quarter,month);
    		Collections.sort(tb03s);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		//NEW CASES 
    		Concept pulmonaryConcept = ms.getConcept(TbConcepts.PULMONARY_TB);
    		report += "<h4>" + getMessage("mdrtb.pulmonary") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i = 0;
    		for(TB03Form tf : tb03s) {
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==pulmonaryConcept.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + tf.getAgeAtTB03Registration() +  closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//Relapse
    		
    		Concept epConcept = ms.getConcept(TbConcepts.EXTRA_PULMONARY_TB);
    		
    		report += "<h4>" + getMessage("mdrtb.extrapulmonary") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		
    		p = null;
    		i = 0;
    		for(TB03Form tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==epConcept.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + tf.getAgeAtTB03Registration() +  closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
    		
    		
    		
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
		model.addAttribute("listName", getMessage("mdrtb.byDrugResistance"));

		String report = "";

		Concept groupConcept = ms.getConcept(TbConcepts.RESISTANCE_TYPE);

		ArrayList<TB03Form> tb03s = Context.getService(MdrtbService.class)
				.getTB03FormsFilled(locList, year, quarter, month);
		
		
		Collections.sort(tb03s);
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
		report += openTD() + getMessage("mdrtb.serialNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.localization") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.drugNames") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		int i = 0;
		for (TB03Form tf : tb03s) {
			
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + tf.getAgeAtTB03Registration() + closeTD();
				report += openTD() + getSiteOfDisease(tf) + closeTD();
				report += openTD() + getResistantDrugs(tf) + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// RIF
		q = ms.getConcept(MdrtbConcepts.RR_TB);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.serialNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.localization") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.drugNames") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i=0;
		for (TB03Form tf : tb03s) {
			
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + getSiteOfDisease(tf) + closeTD();
				report += openTD() + getResistantDrugs(tf) + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// POLY
		q = ms.getConcept(MdrtbConcepts.PDR_TB);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.serialNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.localization") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.drugNames") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i=0;
		for (TB03Form tf : tb03s) {
			
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + tf.getAgeAtTB03Registration() + closeTD();
				report += openTD() + getSiteOfDisease(tf) + closeTD();
				report += openTD() + getResistantDrugs(tf) + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// MDR
		q = ms.getConcept(MdrtbConcepts.MDR_TB);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.serialNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.localization") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.drugNames") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i=0;
		for (TB03Form tf : tb03s) {
			
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + tf.getAgeAtTB03Registration() + closeTD();
				report += openTD() + getSiteOfDisease(tf) + closeTD();
				report += openTD() + getResistantDrugs(tf) + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// PRE_XDR_TB
		q = ms.getConcept(MdrtbConcepts.PRE_XDR_TB);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.serialNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.localization") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.drugNames") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i=0;
		for (TB03Form tf : tb03s) {
			
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + tf.getAgeAtTB03Registration() + closeTD();
				report += openTD() + getSiteOfDisease(tf) + closeTD();
				report += openTD() + getResistantDrugs(tf) + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// XDR_TB
		q = ms.getConcept(MdrtbConcepts.XDR_TB);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.serialNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.localization") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.drugNames") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i=0;
		for (TB03Form tf : tb03s) {
			
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + tf.getAgeAtTB03Registration() + closeTD();
				report += openTD() + getSiteOfDisease(tf) + closeTD();
				report += openTD() + getResistantDrugs(tf) + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";
		
		// TDR
				q = ms.getConcept(MdrtbConcepts.TDR_TB);
				report += "<h4>" + q.getName().getName() + "</h4>";
				report += openTable();
				report += openTR();
				report += openTD() + getMessage("mdrtb.serialNumber")
						+ closeTD();
				report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
						+ closeTD();
				report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
				report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
				report += openTD() + getMessage("mdrtb.lists.localization") + closeTD();
				report += openTD() + getMessage("mdrtb.lists.drugNames") + closeTD();
				report += openTD() + "" + closeTD();
				report += closeTR();

				temp = null;
				p = null;
				i=0;
				for (TB03Form tf : tb03s) {
					
					if(tf.getPatient()==null || tf.getPatient().isVoided())
	    				continue;
					
					temp = MdrtbUtil.getObsFromEncounter(groupConcept,
							tf.getEncounter());
					if (temp != null
							&& temp.getValueCoded() != null
							&& temp.getValueCoded().getId().intValue() == q.getId()
									.intValue()) {
						i++;
						p = Context.getPersonService().getPerson(
								tf.getPatient().getId());
						report += openTR();
						report += openTD() + i + closeTD();
						report += openTD() + getRegistrationNumber(tf) + closeTD();
						report += renderPerson(p);
						report += openTD() + tf.getAgeAtTB03Registration() + closeTD();
						report += openTD() + getSiteOfDisease(tf) + closeTD();
						report += openTD() + getResistantDrugs(tf) + closeTD();
						report += openTD() + getPatientLink(tf) + closeTD();
						report += closeTR();

					}
				}

				report += closeTable();
				report += getMessage("mdrtb.numberOfRecords") + ": " + i;
				report += "<br/>";

		// UNKNOWN
		q = ms.getConcept(MdrtbConcepts.UNKNOWN);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.serialNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.localization") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.drugNames") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i=0;
		for (TB03Form tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + tf.getAgeAtTB03Registration() + closeTD();
				report += openTD() + getSiteOfDisease(tf) + closeTD();
				report += openTD() + getResistantDrugs(tf) + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// NO
		q = ms.getConcept(TbConcepts.NO);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.serialNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.localization") + closeTD();
		report += openTD() + getMessage("mdrtb.lists.drugNames") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i=0;
		for (TB03Form tf : tb03s) {
			
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tf) + closeTD();
				report += renderPerson(p);
				report += openTD() + tf.getAgeAtTB03Registration() + closeTD();
				report += openTD() + getSiteOfDisease(tf) + closeTD();
				report += openTD() + "" + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
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
    		Collections.sort(tb03s);
    		
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		//NEW CASES + Positive
    		Concept newConcept = ms.getConcept(TbConcepts.NEW);
    		report += "<h4>" + getMessage("mdrtb.lists.newPulmonaryBacPositive") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Obs temp2 = null;
    		Person p = null;
    		int i = 0;
    		for(TB03Form tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			i++;
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(siteConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==newConcept.getId().intValue()) {
    				if(temp2!=null && temp2.getValueCoded()!=null && temp2.getValueCoded().getId().intValue()==pulConcept.getId().intValue()) {
    					if(MdrtbUtil.isBacPositive(tf)) {
    						p = Context.getPersonService().getPerson(tf.getPatient().getId());
    						report += openTR();
    						report += openTD() + i +  closeTD();
    						report += openTD() + getRegistrationNumber(tf) +  closeTD();
    						report += renderPerson(p);
    						report +=  openTD() + tf.getAgeAtTB03Registration() + closeTD();
    						report += openTD() + getPatientLink(tf) + closeTD(); 
    						report += closeTR();
    					}
    				}
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//NEW CASES + Negative
    		
    		report += "<h4>" + getMessage("mdrtb.lists.newPulmonaryBacNegative") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		temp2 = null;
    		p = null;
    		i=0;
    		for(TB03Form tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(siteConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==newConcept.getId().intValue()) {
    				if(temp2!=null && temp2.getValueCoded()!=null && temp2.getValueCoded().getId().intValue()==pulConcept.getId().intValue()) {
    					if(!MdrtbUtil.isBacPositive(tf)) {
    						i++;
    						p = Context.getPersonService().getPerson(tf.getPatient().getId());
    						report += openTR();
    						report += openTD() + i +  closeTD();
    						report += openTD() + getRegistrationNumber(tf) +  closeTD();
    						report += renderPerson(p);
    						report +=  openTD() + tf.getAgeAtTB03Registration() + closeTD();
    						report += openTD() + getPatientLink(tf) + closeTD(); 
    						report += closeTR();
    					}
    				}
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		//Relapse + positive
    		Concept relapse1Concept = ms.getConcept(TbConcepts.RELAPSE_AFTER_REGIMEN_1);
    		Concept relapse2Concept = ms.getConcept(TbConcepts.RELAPSE_AFTER_REGIMEN_2);
    		report += "<h4>" + getMessage("mdrtb.lists.relapsePulmonaryBacPositive") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		i=0;
    		p = null;
    		for(TB03Form tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(siteConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && (temp.getValueCoded().getId().intValue()==relapse1Concept.getId().intValue() || temp.getValueCoded().getId().intValue()==relapse2Concept.getId().intValue())) {
    				if(temp2!=null && temp2.getValueCoded()!=null && temp2.getValueCoded().getId().intValue()==pulConcept.getId().intValue()) {
    					if(MdrtbUtil.isBacPositive(tf)) {
    							p = Context.getPersonService().getPerson(tf.getPatient().getId());
    							i++;
    							report += openTR();
    							report += openTD() + i +  closeTD();
    							report += openTD() + getRegistrationNumber(tf) +  closeTD();
    							report += renderPerson(p);
    							report +=  openTD() + tf.getAgeAtTB03Registration() + closeTD();
    							report += openTD() + getPatientLink(tf) + closeTD(); 
    							report += closeTR();
    					}
    				}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		//Relapse + negative
    		report += "<h4>" + getMessage("mdrtb.lists.relapsePulmonaryBacNegative") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		
    		p = null;
    		i=0;
    		for(TB03Form tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(siteConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && (temp.getValueCoded().getId().intValue()==relapse1Concept.getId().intValue() || temp.getValueCoded().getId().intValue()==relapse2Concept.getId().intValue())) {
    				if(temp2!=null && temp2.getValueCoded()!=null && temp2.getValueCoded().getId().intValue()==pulConcept.getId().intValue()) {
    					if(!MdrtbUtil.isBacPositive(tf)) {
    							p = Context.getPersonService().getPerson(tf.getPatient().getId());
    							i++;
    							report += openTR();
    							report += openTD() + i +  closeTD();
    							report += openTD() + getRegistrationNumber(tf) +  closeTD();
    							report += renderPerson(p);
    							report +=  openTD() + tf.getAgeAtTB03Registration() + closeTD();
    							report += openTD() + getPatientLink(tf) + closeTD(); 
    							report += closeTR();
    					}
    				}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//Retreament - Negative
    		Concept default1Concept = ms.getConcept(TbConcepts.DEFAULT_AFTER_REGIMEN_1);
    		Concept default2Concept = ms.getConcept(TbConcepts.DEFAULT_AFTER_REGIMEN_2);
    		Concept failure1Concept = ms.getConcept(TbConcepts.AFTER_FAILURE_REGIMEN_1);
    		Concept failure2Concept = ms.getConcept(TbConcepts.AFTER_FAILURE_REGIMEN_1);
    		report += "<h4>" + getMessage("mdrtb.lists.retreatmentPulmonaryBacPositive") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		
    		p = null;
    		i=0;
    		for(TB03Form tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
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
    						i++;
    						report += openTR();
    						report += openTD() + i +  closeTD();
    						report += openTD() + getRegistrationNumber(tf) +  closeTD();
    						report += renderPerson(p);
    						report +=  openTD() + tf.getAgeAtTB03Registration() + closeTD();
    						report += openTD() + getPatientLink(tf) + closeTD(); 
    						report += closeTR();
    					}
    				}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    	    report += "<h4>" + getMessage("mdrtb.lists.retreatmentPulmonaryBacNegative") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		
    		p = null;
    		i=0;
    		for(TB03Form tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
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
    						i++;
    						report += openTR();
    						report += openTD() + i +  closeTD();
    						report += openTD() + getRegistrationNumber(tf) +  closeTD();
    						report += renderPerson(p);
    						report +=  openTD() + tf.getAgeAtTB03Registration() + closeTD();
    						report += openTD() + getPatientLink(tf) + closeTD(); 
    						report += closeTR();
    					}
    				}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//Transfer In
    		Concept transferInConcept = ms.getConcept(TbConcepts.TRANSFER);
    		
    		report += "<h4>" + getMessage("mdrtb.lists.transferInPulmonaryBacPositive") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		temp = null;
    		
    		p = null;
    		i=0;
    		for(TB03Form tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(siteConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && 
    					temp.getValueCoded().getId().intValue()==transferInConcept.getId().intValue())
    					 {
    					if(temp2!=null && temp2.getValueCoded()!=null && temp2.getValueCoded().getId().intValue()==pulConcept.getId().intValue()) {
    						if(MdrtbUtil.isBacPositive(tf)) {
    							
    							p = Context.getPersonService().getPerson(tf.getPatient().getId());
    							i++;
    							report += openTR();
    							report += openTD() + i +  closeTD();
    							report += openTD() + getRegistrationNumber(tf) +  closeTD();
    							report += renderPerson(p);
    							report +=  openTD() + tf.getAgeAtTB03Registration() + closeTD();
    							report += openTD() + getPatientLink(tf) + closeTD(); 
    							report += closeTR();
    				
    						}
    					}
    			  }
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//Transfer In
    		
    		
    		report += "<h4>" + getMessage("mdrtb.lists.transferInPulmonaryBacNegative") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		temp = null;
    		
    		p = null;
    		i=0;
    		for(TB03Form tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(siteConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && 
    					temp.getValueCoded().getId().intValue()==transferInConcept.getId().intValue())
    					 {
    					if(temp2!=null && temp2.getValueCoded()!=null && temp2.getValueCoded().getId().intValue()==pulConcept.getId().intValue()) {
    						if(!MdrtbUtil.isBacPositive(tf)) {
    							
    							p = Context.getPersonService().getPerson(tf.getPatient().getId());
    							i++;
    							report += openTR();
    							report += openTD() + i +  closeTD();
    							report += openTD() + getRegistrationNumber(tf) +  closeTD();
    							report += renderPerson(p);
    							report +=  openTD() + tf.getAgeAtTB03Registration() + closeTD();
    							report += openTD() + getPatientLink(tf) + closeTD(); 
    							report += closeTR();
    				
    						}
    					}
    			  }
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
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
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Obs temp2 = null;
    		Person p = null;
    		int i=0;
    		for(TB03uForm tf : tb03s) {
    			
    			
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(treatmentStartDate, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==mdr.getId().intValue() && (temp2==null || temp2.getValueDatetime()==null)) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
    		Concept xdr = ms.getConcept(TbConcepts.XDR_TB);
    		
    		report += "<h4>" + getMessage("mdrtb.xdrtb") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		temp = null;
    		
    		p = null;
    		i=0;
    		for(TB03uForm tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			temp2 = MdrtbUtil.getObsFromEncounter(treatmentStartDate, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && 
    					temp.getValueCoded().getId().intValue()==xdr.getId().intValue()  && (temp2==null || temp2.getValueDatetime()==null)) {
    				
    				
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
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
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i =0;
    		for(TB03uForm tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && (temp.getValueCoded().getId().intValue()==curedConcept.getId().intValue() || 
    					temp.getValueCoded().getId().intValue()==txCompleted.getId().intValue())) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
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
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i = 0;
    		for(TB03uForm tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==mdr.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
    		//EP
    		Concept xdr = ms.getConcept(TbConcepts.XDR_TB);
    		
    		report += "<h4>" + getMessage("mdrtb.xdrtb") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		temp = null;
    		
    		p = null;
    		i=0;
    		for(TB03uForm tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && 
    					temp.getValueCoded().getId().intValue()==xdr.getId().intValue()) {
    				
    				
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tf) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
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
    		
    		Collections.sort(forms);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		//NEW CASES 
    		
    		//report += "<h4>" + getMessage("mdrtb.womenOfChildbearingAge") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		//Obs temp = null;
    		Person p = null;
    		int i =0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			if(tf.getPatient().getGender().equals("F")) {
    				TB03Form tb03 = null;
    				tf.initTB03(tf.getPatProgId());
    				tb03 = tf.getTB03();
    				
    				if(tb03!=null) {
    					Integer age = tb03.getAgeAtTB03Registration();
    				
    				//temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(age!=null && age.intValue()>=15  && age.intValue()<=49) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    				
    		}
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
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
    		Collections.sort(forms);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		//NEW CASES 
    		
    		//report += "<h4>" + getMessage("mdrtb.menOfConscriptAge") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + getMessage("mdrtb.lists.caseDefinition") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		//Obs temp = null;
    		Person p = null;
    		int i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			if(tf.getPatient().getGender().equals("M")) {
    				TB03Form tb03 = null;
    				tf.initTB03(tf.getPatProgId());
    				tb03 = tf.getTB03();
    				
    				if(tb03!=null) {
    					Integer age = tb03.getAgeAtTB03Registration();
    					
    					if(age!=null && age.intValue()>=18  && age.intValue()<=27) {
    						p = Context.getPersonService().getPerson(tf.getPatient().getId());
    						i++;
    						report += openTR();
    						report += openTD() + i +  closeTD();
    						report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    						report += renderPerson(p);
    						report += openTD() + age +  closeTD();
    						if(tb03.getRegistrationGroup()!=null)
    							report += openTD() + tb03.getRegistrationGroup().getName().getName() +  closeTD();
    						else
    							report += openTD() + "" + closeTD();
    						
    						report += openTD() + getPatientLink(tf) + closeTD(); 
    						report += closeTR();
    					}
    				
    				}
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    ////
    
   /* @RequestMapping("/module/mdrtb/reporting/withDiabetes")
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
    		Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));
    		Concept yes = ms.getConcept(TbConcepts.YES);
    		
    		report += "<h4>" + getMessage("mdrtb.withDiabetes") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }*/
    
    @RequestMapping("/module/mdrtb/reporting/withConcomitantDisease")
    public  String withConcomitantDisease(@RequestParam("district") Integer districtId,
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
    		model.addAttribute("listName", getMessage("mdrtb.withConcomitantDisease"));
    		
    		
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
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    				
    		groupConcept = ms.getConcept(TbConcepts.CANCER);
    		report += "<h4>" + getMessage("mdrtb.withCancer") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;	
    		report += "<br/>";
			
    		groupConcept = ms.getConcept(TbConcepts.CNSDL);
    		report += "<h4>" + getMessage("mdrtb.withCOPD") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;	
report += "<br/>";
			
    		groupConcept = ms.getConcept(TbConcepts.HYPERTENSION_OR_HEART_DISEASE);
    		report += "<h4>" + getMessage("mdrtb.withHypertension") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
			
    		groupConcept = ms.getConcept(TbConcepts.ULCER);
    		report += "<h4>" + getMessage("mdrtb.withUlcer") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
report += "<br/>";
			
    		groupConcept = ms.getConcept(TbConcepts.MENTAL_DISORDER);
    		report += "<h4>" + getMessage("mdrtb.withMentalDisorder") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
report += "<br/>";
			
    		groupConcept = ms.getConcept(TbConcepts.CNSDL);
    		report += "<h4>" + getMessage("mdrtb.withCOPD") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
report += "<br/>";
			
    		groupConcept = ms.getConcept(TbConcepts.ICD20);
    		report += "<h4>" + getMessage("mdrtb.withHIV") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
report += "<br/>";
			
    		groupConcept = ms.getConcept(TbConcepts.COMORBID_HEPATITIS);
    		report += "<h4>" + getMessage("mdrtb.withHepatitis") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
			
    		groupConcept = ms.getConcept(TbConcepts.KIDNEY_DISEASE);
    		report += "<h4>" + getMessage("mdrtb.withLKidneyDisease") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
report += "<br/>";
			
    		groupConcept = ms.getConcept(TbConcepts.OTHER_DISEASE);
    		report += "<h4>" + getMessage("mdrtb.withOtherDisease") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && temp.getValueCoded()!= null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
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
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    
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
    		Collections.sort(forms);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		Concept fromContact = ms.getConcept(TbConcepts.CONTACT_INVESTIGATION);
    		
    		report += "<h4>" + getMessage("mdrtb.detectedFromContact") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i= 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == fromContact.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    				
    		
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }
    //////////
    
    /*@RequestMapping("/module/mdrtb/reporting/withDiabetes")
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
    		Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));
    		Concept yes = ms.getConcept(TbConcepts.YES);
    		
    		report += "<h4>" + getMessage("mdrtb.withDiabetes") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
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
    		Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
 	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));
    		Concept yes = ms.getConcept(TbConcepts.YES);
    		
    		report += "<h4>" + getMessage("mdrtb.withCancer") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i = 0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
    		model.addAttribute("report",report);
    		return "/module/mdrtb/reporting/patientListsResults";
    		
    
    }*/
    
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
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i = 0;
    		for(Form89 tf : forms) {
    			
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
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
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i=0;
    		for(Form89 tf : forms) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
				tf.initTB03(tf.getPatProgId());
				tb03 = tf.getTB03();
				
				if(tb03!=null) {
					Integer age = tb03.getAgeAtTB03Registration();
    			
    				temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    				if(temp!=null && (temp.getValueCoded().getId().intValue()  == yes.getId().intValue())) {
    					p = Context.getPersonService().getPerson(tf.getPatient().getId());
    					i++;
    					report += openTR();
    					report += openTD() + i +  closeTD();
    					report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    					report += renderPerson(p);
    					report += openTD() + age +  closeTD();
    					report += openTD() + getPatientLink(tf) + closeTD(); 
    					report += closeTR();
    				}
    			}
    			
    	}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
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
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		int i =0;
		for (Form89 tf : forms) {

			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& (temp.getValueCoded().getId().intValue() == yes.getId()
							.intValue())) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				i++;
				report += openTR();
				report += openTD() + i +  closeTD();
				 report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}
			}

		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
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
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		int i = 0;
		for (Form89 tf : forms) {

			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& (temp.getValueCoded().getId().intValue() == yes.getId()
							.intValue())) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i +  closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}
			}

		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
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
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		int i =0; 
		for (Form89 tf : forms) {

			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& (temp.getValueCoded().getId().intValue() == yes.getId()
							.intValue())) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				i++;
				report += openTR();
				report += openTD() + i +  closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}
			}

		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		model.addAttribute("report", report);
		return "/module/mdrtb/reporting/patientListsResults";

	}

	// ////////
	
	@RequestMapping("/module/mdrtb/reporting/withHepatitis")
	public String withHepatitis(@RequestParam("district") Integer districtId,
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
		model.addAttribute("listName", getMessage("mdrtb.withHepatitis"));

		String report = "";

		Concept groupConcept = ms.getConcept(TbConcepts.COMORBID_HEPATITIS);

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

		report += "<h4>" + getMessage("mdrtb.withHepatitis") + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		int i = 0;
		for (Form89 tf : forms) {

			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& (temp.getValueCoded().getId().intValue() == yes.getId()
							.intValue())) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i +  closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}
			}

		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		model.addAttribute("report", report);
		return "/module/mdrtb/reporting/patientListsResults";

	}
	
	/////////
	
	@RequestMapping("/module/mdrtb/reporting/withKidneyDisease")
	public String withKidneyDisease(@RequestParam("district") Integer districtId,
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
		model.addAttribute("listName", getMessage("mdrtb.withKidneyDisease"));

		String report = "";

		Concept groupConcept = ms.getConcept(TbConcepts.KIDNEY_DISEASE);

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

		report += "<h4>" + getMessage("mdrtb.withKidneyDisease") + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		int i = 0;
		for (Form89 tf : forms) {

			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& (temp.getValueCoded().getId().intValue() == yes.getId()
							.intValue())) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i +  closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}
			}

		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		model.addAttribute("report", report);
		return "/module/mdrtb/reporting/patientListsResults";

	}

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
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		int i =0;
		for (Form89 tf : forms) {

			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& (temp.getValueCoded().getId().intValue() == yes.getId()
							.intValue())) {
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				i++;
				report += openTR();
				report += openTD() + i +  closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}
			}

		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
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
    		Collections.sort(tb03s);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		
    		//WORKER
    		Concept q = ms.getConcept(TbConcepts.WORKER);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i =0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age +  closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//GOVT SERVANT
    		q = ms.getConcept(TbConcepts.GOVT_SERVANT);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i =0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age +  closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//STUDENT
    		q = ms.getConcept(TbConcepts.STUDENT);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    				
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age +  closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//DISABLED
    		q = ms.getConcept(TbConcepts.DISABLED);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age +  closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//UNEMPLOYED
    		q = ms.getConcept(TbConcepts.UNEMPLOYED);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i=0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//PHC WORKER
    		q = ms.getConcept(TbConcepts.PHC_WORKER);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//MILITARY SERVANT
    		q = ms.getConcept(TbConcepts.MILITARY_SERVANT);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//SCHOOLCHILD
    		q = ms.getConcept(TbConcepts.SCHOOLCHILD);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		 i =0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//TB SERVICES WORKER
    		q = ms.getConcept(TbConcepts.TB_SERVICES_WORKER);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//PRIVATE SECTOR WORKER
    		q = ms.getConcept(TbConcepts.PRIVATE_SECTOR);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//HOUSEWIFE
    		q = ms.getConcept(TbConcepts.HOUSEWIFE);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
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
    		Collections.sort(tb03s);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		
    		//RESIDENT_OF_TERRITORY
    		Concept q = ms.getConcept(TbConcepts.RESIDENT_OF_TERRITORY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i =0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//RESIDENT_OTHER_TERRITORY
    		q = ms.getConcept(TbConcepts.RESIDENT_OTHER_TERRITORY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i=0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//FOREIGNER
    		q = ms.getConcept(TbConcepts.FOREIGNER);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}	
    			}
    			
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//RESIDENT_SOCIAL_SECURITY_FACILITY
    		q = ms.getConcept(TbConcepts.RESIDENT_SOCIAL_SECURITY_FACILITY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//HOMELESS
    		q = ms.getConcept(TbConcepts.HOMELESS);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//CONVICTED
    		q = ms.getConcept(TbConcepts.CONVICTED);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    				
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//ON_REMAND
    		q = ms.getConcept(TbConcepts.ON_REMAND);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
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
    		Collections.sort(tb03s);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		
    		//RESIDENT_OF_TERRITORY
    		Concept q = ms.getConcept(TbConcepts.CITY);
    		report += "<h4>" + getMessage("mdrtb.lists.city") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i = 0;
    		for(Form89 tf : tb03s) {
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age +  closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//RESIDENT_OTHER_TERRITORY
    		q = ms.getConcept(TbConcepts.VILLAGE);
    		report += "<h4>" + getMessage("mdrtb.lists.village") + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age +  closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
    		
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
    		Collections.sort(tb03s);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		
    		//TB FACILITY
    		Concept q = ms.getConcept(TbConcepts.TB_FACILITY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i = 0;
    		for(Form89 tf : tb03s) {
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD()  + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//PHC
    		q = ms.getConcept(TbConcepts.PHC_FACILITY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i=0;
    		for(Form89 tf : tb03s) {
    		
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		
    		//PHC
    		q = ms.getConcept(TbConcepts.PRIVATE_SECTOR_FACILITY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i=0;
    		for(Form89 tf : tb03s) {
    		
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		
    		
    		//OTHER MED FAC
    		q = ms.getConcept(TbConcepts.OTHER_MEDICAL_FACILITY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i=0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
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
    		Collections.sort(tb03s);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		
    		//SELF_REFERRAL
    		Concept q = ms.getConcept(TbConcepts.SELF_REFERRAL);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i =0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//BASELINE_EXAM
    		q = ms.getConcept(TbConcepts.BASELINE_EXAM);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i=0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//POSTMORTERM_IDENTIFICATION
    		q = ms.getConcept(TbConcepts.POSTMORTERM_IDENTIFICATION);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i=0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		//CONTACT
    		q = ms.getConcept(TbConcepts.CONTACT);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i=0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//MIGRANT
    		q = ms.getConcept(TbConcepts.MIGRANT);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i=0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
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
    		Collections.sort(tb03s);
    		/*Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
	
    		Date startDate = (Date)(dateMap.get("startDate"));
    		Date endDate = (Date)(dateMap.get("endDate"));*/
    		
    		//FLUOROGRAPHY
    		Concept q = ms.getConcept(TbConcepts.FLUOROGRAPHY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		Obs temp = null;
    		Person p = null;
    		int i =0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//GENEXPERT
    		q = ms.getConcept(TbConcepts.GENEXPERT);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//FLURORESCENT_MICROSCOPY
    		q = ms.getConcept(TbConcepts.FLURORESCENT_MICROSCOPY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//TUBERCULIN_TEST
    		q = ms.getConcept(TbConcepts.TUBERCULIN_TEST);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i=0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		/*//ZIEHLNELSEN
    		q = ms.getConcept(TbConcepts.ZIEHLNELSEN);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";*/
    		
    		//HAIN_TEST
    		q = ms.getConcept(TbConcepts.HAIN_TEST);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//CULTURE_DETECTION
    		q = ms.getConcept(TbConcepts.CULTURE_DETECTION);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//HISTOLOGY
    		q = ms.getConcept(TbConcepts.HISTOLOGY);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//CXR_RESULT
    		q = ms.getConcept(TbConcepts.CXR_RESULT);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    				
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		report += "<br/>";
    		
    		//OTHER
    		q = ms.getConcept(TbConcepts.OTHER);
    		report += "<h4>" + q.getName().getName() + "</h4>";
    		report += openTable();
    		report += openTR();
    		report += openTD() + getMessage("mdrtb.serialNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.registrationNumber") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
    		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
    		report += openTD() + "" + closeTD();
    		report += closeTR();
    		
    		temp = null;
    		p = null;
    		i = 0;
    		for(Form89 tf : tb03s) {
    			
    			if(tf.getPatient()==null || tf.getPatient().isVoided())
    				continue;
    			
    			TB03Form tb03 = null;
    			tf.initTB03(tf.getPatProgId());
    			tb03 = tf.getTB03();
    			
    			if(tb03!=null) {
    				Integer age = tb03.getAgeAtTB03Registration();
    			
    			temp = MdrtbUtil.getObsFromEncounter(groupConcept, tf.getEncounter());
    			if(temp!=null && temp.getValueCoded()!=null && temp.getValueCoded().getId().intValue()==q.getId().intValue()) {
    				p = Context.getPersonService().getPerson(tf.getPatient().getId());
    				i++;
    				report += openTR();
    				report += openTD() + i +  closeTD();
    				report += openTD() + getRegistrationNumber(tb03) +  closeTD();
    				report += renderPerson(p);
    				report += openTD() + age + closeTD();
    				report += openTD() + getPatientLink(tf) + closeTD(); 
    				report += closeTR();
    			}
    			}
    		}
    				
    		report += closeTable();
    		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
    		
    		
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
		
		Collections.sort(tb03s);

		// FOCAL
		Concept q = ms.getConcept(TbConcepts.FOCAL);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		int i =0;
		for (Form89 tf : tb03s) {
			
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// INFILTRATIVE
		q = ms.getConcept(TbConcepts.INFILTRATIVE);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// DISSEMINATED
		q = ms.getConcept(TbConcepts.DISSEMINATED);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i = 0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// CAVERNOUS
		q = ms.getConcept(TbConcepts.CAVERNOUS);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i=0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// FIBROUS_CAVERNOUS
		q = ms.getConcept(TbConcepts.FIBROUS_CAVERNOUS);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i=0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// CIRRHOTIC
		q = ms.getConcept(TbConcepts.CIRRHOTIC);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i = 0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// TB_PRIMARY_COMPLEX
		q = ms.getConcept(TbConcepts.TB_PRIMARY_COMPLEX);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i=0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// MILIARY
		q = ms.getConcept(TbConcepts.MILIARY);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i = 0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// TUBERCULOMA
		q = ms.getConcept(TbConcepts.TUBERCULOMA);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i = 0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// BRONCHUS
		q = ms.getConcept(TbConcepts.BRONCHUS);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i = 0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		

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
		
		Collections.sort(tb03s);
		
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
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		Obs temp = null;
		Person p = null;
		int i = 0;
		for (Form89 tf : tb03s) {
			
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// OF_LYMPH_NODES
		q = ms.getConcept(TbConcepts.OF_LYMPH_NODES);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i=0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// OSTEOARTICULAR
		q = ms.getConcept(TbConcepts.OSTEOARTICULAR);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i=0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// GENITOURINARY
		q = ms.getConcept(TbConcepts.GENITOURINARY);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i = 0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// OF_PERIPHERAL_LYMPH_NODES
		q = ms.getConcept(TbConcepts.OF_PERIPHERAL_LYMPH_NODES);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i = 0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// ABDOMINAL
		q = ms.getConcept(TbConcepts.ABDOMINAL);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i = 0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// TUBERCULODERMA
		q = ms.getConcept(TbConcepts.TUBERCULODERMA);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i = 0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// OCULAR
		q = ms.getConcept(TbConcepts.OCULAR);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD(); report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i =0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// OF_CNS
		q = ms.getConcept(TbConcepts.OF_CNS);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i = 0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}
		
		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
		report += "<br/>";

		// OF_CNS
		q = ms.getConcept(TbConcepts.OF_LIVER);
		report += "<h4>" + q.getName().getName() + "</h4>";
		report += openTable();
		report += openTR();
		report += openTD() + getMessage("mdrtb.tb03.registrationNumber")
				+ closeTD();
		report += openTD() + getMessage("mdrtb.tb03.name") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.dateOfBirth") + closeTD();
		report += openTD() + getMessage("mdrtb.tb03.ageAtRegistration") + closeTD();
		report += openTD() + "" + closeTD();
		report += closeTR();

		temp = null;
		p = null;
		i = 0;
		for (Form89 tf : tb03s) {
			if(tf.getPatient()==null || tf.getPatient().isVoided())
				continue;
			TB03Form tb03 = null;
			tf.initTB03(tf.getPatProgId());
			tb03 = tf.getTB03();
			
			if(tb03!=null) {
				Integer age = tb03.getAgeAtTB03Registration();
			
			
			temp = MdrtbUtil.getObsFromEncounter(groupConcept,
					tf.getEncounter());
			if (temp != null
					&& temp.getValueCoded() != null
					&& temp.getValueCoded().getId().intValue() == q.getId()
							.intValue()) {
				i++;
				p = Context.getPersonService().getPerson(
						tf.getPatient().getId());
				report += openTR();
				report += openTD() + i + closeTD();
				report += openTD() + getRegistrationNumber(tb03) + closeTD();
				report += renderPerson(p);
				report += openTD() + age + closeTD();
				report += openTD() + getPatientLink(tf) + closeTD();
				report += closeTR();
			}

			}
		}

		report += closeTable();
		report += getMessage("mdrtb.numberOfRecords") + ": " + i;
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
    
    private String openTD(String attribute) {
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
    	
    	
    	//ret += openTD() + dateFormat.format(p.getBirthdate()) + closeTD();
    	
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
	   link = "<a href=\"" + link + "\" target=\"_blank\">" + getMessage("mdrtb.view") + "</a>";
	   return link;
   }
   
   
   public String getPatientLink(TB03uForm form) {
	   
	   String link = null;
	   link = "../program/enrollment.form?patientId=" + form.getPatient().getId();
	   link = "<a href=\"" + link + "\" target=\"_blank\">" + getMessage("mdrtb.view") + "</a>";
	   return link;
   }    
   
   public String getPatientLink(Form89 form) {
	   
	   String link = null;
	   link = "../program/enrollment.form?patientId=" + form.getPatient().getId();
	   link = "<a href=\"" + link + "\" target=\"_blank\">" + getMessage("mdrtb.view") + "</a>";
	   return link;
   }
   
public String getGender(Person p) {
	   
	   String ret = "";;
	   String gender = p.getGender();
	   
	 //  System.out.println(gender);
	   
	   if(gender.equals("F")) {
		   return getMessage("mdrtb.tb03.gender.female");
	   }
	   
	   else if(gender.equals("M"))
		   return getMessage("mdrtb.tb03.gender.male");
	   
	   return ret;
   }
   
   
 public String getTransferFrom(TB03Form tf) {
	   TransferInForm tif = getTransferInForm(tf);
	   if(tif!=null) {
		   return tif.getLocation().toString();
	   }
	   
	   else {
		   return "";
	   }
	  
   }
 
 public String getTransferFromDate(TB03Form tf) {
	 SimpleDateFormat dateFormat = Context.getDateFormat();
 	 dateFormat.setLenient(false);
	 
	 
	 TransferInForm tif = getTransferInForm(tf);
	   if(tif!=null) {
		   return dateFormat.format(tif.getEncounterDatetime());
	   }
	   
	   else {
		   return "";
	   }
 }
 
 public TransferInForm getTransferInForm(TB03Form tf) {
	 TransferInForm tif = null;
	 
	 Integer ppid = tf.getPatProgId();
	 TbPatientProgram tpp = Context.getService(MdrtbService.class).getTbPatientProgram(ppid);
	 Date startDate = tpp.getDateEnrolled();
	 Date endDate = tpp.getDateCompleted();
	 
	 ArrayList<TransferInForm> allTifs = Context.getService(MdrtbService.class).getTransferInFormsFilledForPatient(tf.getPatient());
	 
	 for(TransferInForm temp : allTifs) {
		 if(tf.getEncounterDatetime().equals(temp.getEncounterDatetime())) {
				 tif = temp;
		 		 break;
		 }
		 
		 else if (tf.getEncounterDatetime().before (temp.getEncounterDatetime())) {
			 if(endDate!=null && temp.getEncounterDatetime().before(endDate)) {
				 tif = temp;
				 break;
			 }
			 
			 else if(endDate==null) {
				 tif = temp;
				 break;
			 }
		 }
	 }
	 
	 return tif;
 }
   
 public String getSiteOfDisease(TB03Form tf) {
	   
	   if(tf.getAnatomicalSite()!=null) {
		   return tf.getAnatomicalSite().getName().getName();
	   }
	   
	   else {
		   return "";
	   }
	  
 }
   
  
  public String getResistantDrugs(TB03Form tf) {
	  String drugs = "";
	  List<DSTForm> dsts = tf.getDsts();
	  
	  if(dsts==null || dsts.size()==0) {
		  drugs = "";
	  }
	  
	  else {
		  DSTForm latest = dsts.get(dsts.size()-1);
		  DstImpl dst = latest.getDi();
		  return dst.getResistantDrugs();
		  
	  }
	  
	  return drugs;
  }
  
  public String getSensitiveDrugs(TB03Form tf) {
	  String drugs = "";
	  List<DSTForm> dsts = tf.getDsts();
	  
	  if(dsts==null || dsts.size()==0) {
		  drugs = "";
	  }
	  
	  else {
		  DSTForm latest = dsts.get(dsts.size()-1);
		  DstImpl dst = latest.getDi();
		  return dst.getSensitiveDrugs();
		  
	  }
	  
	  return drugs;
  }
  
  public String getReRegistrationNumber(TB03Form tf) {
	  String ret = "";
	  
	  Integer ppid = tf.getPatProgId();
	  
	  if(ppid==null)
		  return ret;
	  
	  Patient p = tf.getPatient();
	  
	  MdrtbService ms = Context.getService(MdrtbService.class);
	  
	  List<TbPatientProgram> tpps = ms.getTbPatientPrograms(p);
	  
	  if(tpps==null || tpps.size() <= 1) {
		  return ret;
	  }
	  
	  //TbPatientProgram currentProg = ms.getTbPatientProgram(ppid);
	  
	  Collections.sort(tpps);
	  
	  int numPrograms = tpps.size();
	  int index = 0;
	  int foundIndex = -1;
	  for(TbPatientProgram tpp : tpps) {
		  
		  if(tpp==null || tpp.getId()==null)
			  continue;
		
		  if(tpp.getId().intValue() == ppid.intValue()) {
			  foundIndex = index;
			  break;
		  }
		  
		  index++;
		  
	  }
	  
	  if(foundIndex!=-1) {
		  if(foundIndex+1 < numPrograms ) {
			  
			  if(tpps.get(foundIndex+1).getPatientIdentifier()!=null)
				  return tpps.get(foundIndex+1).getPatientIdentifier().getIdentifier();
		  }
	  }
	  
	  
	  
	  return ret;
  }
   
   
}
