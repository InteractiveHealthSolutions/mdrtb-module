package org.openmrs.module.mdrtb.web.controller.reporting.pv;


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
import org.openmrs.module.mdrtb.reporting.pv.AERegisterData;
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

public class AERegisterController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
        
    
    @RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/pv/aeRegister")
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
    	 return new ModelAndView("/module/mdrtb/reporting/pv/aeRegister", model);	
    	
    }
    

    @RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/pv/aeRegister")
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
		
		ArrayList<AEForm> forms = Context.getService(MdrtbService.class).getAEFormsFilled(locList, year, quarter, month);
		
		ArrayList<AERegisterData> aeRegister = new ArrayList<AERegisterData>();
		
		System.out.println("list size:" + forms.size());
		//CohortDefinition baseCohort = null;
		
    	
    	
    	//start of Table 1
    	for (AEForm af : forms) {
    		AERegisterData aerd = new AERegisterData(af);
    		aeRegister.add(aerd);
    	}
    	
    	if(aeRegister!=null && aeRegister.size()!=0) {
    		Collections.sort(aeRegister);
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
		
    	model.addAttribute("forms", aeRegister);
    	
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
        return "/module/mdrtb/reporting/pv/aeRegisterResults";
        //_" + Context.getLocale().toString().substring(0, 2);
    }
    
    
  
    
}
