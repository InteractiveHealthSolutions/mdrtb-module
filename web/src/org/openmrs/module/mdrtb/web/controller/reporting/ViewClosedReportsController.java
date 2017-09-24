package org.openmrs.module.mdrtb.web.controller.reporting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.reporting.PDFHelper;
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
public class ViewClosedReportsController {

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/viewClosedReports")
    public void viewClosedReportsGet(@RequestParam(required = true, value = "type") String reportType, ModelMap model) {
    	List<List<Integer>> closedReports = Context.getService(MdrtbService.class).PDFRows(reportType);
    	List<Integer> reportIds = closedReports.get(0);
    	List<Integer> oblastIds = closedReports.get(1);
    	List<Integer> districtIds = closedReports.get(2);
    	List<Integer> facilityIds = closedReports.get(3);
    	List<Integer> years = closedReports.get(4);
    	List<Integer> quarters = closedReports.get(5);
    	List<Integer> months = closedReports.get(6);
    	List<Integer> reportDates = closedReports.get(7);
    	List<Integer> reportStatuses = closedReports.get(8);
    	List<Integer> reportNames = closedReports.get(9);
    	
		List<Oblast> oblasts = new ArrayList<Oblast>();
		List<District> districts = new ArrayList<District>();
		List<Facility> facilities = new ArrayList<Facility>();

		for (Integer oblastId : oblastIds) {
        	oblasts.add(Context.getService(MdrtbService.class).getOblast(oblastId));
		}
		
		for (Integer districtId : districtIds) {
        	districts.add(Context.getService(MdrtbService.class).getDistrict(districtId));
		}
		
		for (Integer facilityId : facilityIds) {
        	facilities.add(Context.getService(MdrtbService.class).getFacility(facilityId));
		}

    	
        //List<Location> locations = Context.getLocationService().getAllLocations(false);
		List<Oblast> o = Context.getService(MdrtbService.class).getOblasts();
        List<List<Location>> oblastLocations = new ArrayList<List<Location>>();
    	for (Oblast oblast : o) {
    		List<Location> l = Context.getService(MdrtbService.class).getLocationsFromOblastName(oblast);
    		oblastLocations.add(l);
		}

    	model.addAttribute("closedReports", closedReports);
    	model.addAttribute("reportIds", reportIds);
    	model.addAttribute("oblastIds", oblastIds);
    	model.addAttribute("districtIds", districtIds);
    	model.addAttribute("facilityIds", facilityIds);
    	model.addAttribute("years", years);
    	model.addAttribute("quarters", quarters);
    	model.addAttribute("months", months);
    	model.addAttribute("reportDates", reportDates);
    	model.addAttribute("reportStatuses", reportStatuses);
    	model.addAttribute("reportNames", reportNames);
        
        model.addAttribute("reportOblasts", oblasts);
        model.addAttribute("reportDistricts", districts);
        model.addAttribute("reportFacilities", facilities);
    	//model.addAttribute("reportLocations", locations);
        model.addAttribute("oblasts", o);
    	model.addAttribute("oblastLocations", oblastLocations);
    	model.addAttribute("reportType", reportType);
	}


	@RequestMapping(method=RequestMethod.POST)//, value="/module/mdrtb/reporting/viewClosedReports")
    public ModelAndView viewClosedReportsPost(
    		HttpServletRequest request, HttpServletResponse response,
    		@RequestParam("oblast") Integer oblastId, 
    		@RequestParam("district") Integer districtId,
    		@RequestParam("facility") Integer facilityId,
    		@RequestParam("year") Integer year, 
    		@RequestParam("quarter") String quarter, 
    		@RequestParam("month") String month, 
    		@RequestParam("reportName") String reportName, 
    		@RequestParam("reportDate") String reportDate, 
    		@RequestParam("formAction") String formAction,
    		@RequestParam("reportType") String reportType,
            ModelMap model) throws EvaluationException {
		System.out.println("-----POST-All-----");
		
		Integer oblast = oblastId; 
		Integer district = districtId; 
		Integer facility = facilityId;
		String html = "";
		String returnStr = "";
		try {
			
			
			if(formAction.equals("unlock")) {
				System.out.println("-----UNLOCK-----");
				Context.getService(MdrtbService.class).unlockReport(oblast, district, facility, year, quarter, month, reportName.replaceAll(" ", "_").toUpperCase(), reportDate, reportType);
				viewClosedReportsGet(reportType, model);
				returnStr = "/module/mdrtb/reporting/viewClosedReports";
			}
			else if(formAction.equals("view")) {
				System.out.println("-----VIEW-----");
				List<String> allReports = (List<String>) Context.getService(MdrtbService.class).readTableData(oblast, district, facility, year, quarter, month, reportName.replaceAll(" ", "_").toUpperCase(), reportDate, reportType);

				System.out.println(allReports);
		    	
				if(allReports.isEmpty() && allReports.size() == 0) {
					html = "<p>No Data Found</p>";
				}
				else {
			    	html = new PDFHelper().decompressCode(allReports.get(0));
//			    	html = html.replaceAll("BR-TAG", "<br>");
				}
				model.addAttribute("html", html); 
				model.addAttribute("oblast", oblast); 
				model.addAttribute("district", district);
				model.addAttribute("facility", facility); 
				model.addAttribute("year", year); 
				model.addAttribute("quarter", quarter); 
				model.addAttribute("month", month); 
				model.addAttribute("reportName", reportName.replaceAll("_", " ").toUpperCase()); 
				model.addAttribute("reportDate", reportDate); 
				model.addAttribute("formAction", formAction); 
				returnStr = "/module/mdrtb/reporting/viewClosedReportContent";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("ex", e); 
		} 
		
//		viewClosedReportsGet(model);
//      return "/module/mdrtb/reporting/viewClosedReports";
		return new ModelAndView(returnStr, model);
	}
}
