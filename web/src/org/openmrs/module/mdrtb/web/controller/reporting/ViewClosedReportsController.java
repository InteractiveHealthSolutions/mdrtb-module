package org.openmrs.module.mdrtb.web.controller.reporting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
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
    public void viewClosedReportsGet(ModelMap model) {
    	List<List<Integer>> closedReports = Context.getService(MdrtbService.class).PDFRows();
    	List<Integer> reportIds = closedReports.get(0);
    	List<Integer> oblastIds = closedReports.get(1);
    	List<Integer> locationIds = closedReports.get(2);
    	List<Integer> years = closedReports.get(3);
    	List<Integer> quarters = closedReports.get(4);
    	List<Integer> months = closedReports.get(5);
    	List<Integer> reportDates = closedReports.get(6);
    	List<Integer> reportStatuses = closedReports.get(7);
    	List<Integer> reportNames = closedReports.get(8);
    	
		List<Oblast> oblasts = new ArrayList<Oblast>();
		List<Location> locations = new ArrayList<Location>();

		for (Integer oblastId : oblastIds) {
        	oblasts.add(Context.getService(MdrtbService.class).getOblast(oblastId));
		}

		for (Integer locationId : locationIds) {
        	Location l = Context.getLocationService().getLocation(locationId);
        	locations.add(l);
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
    	model.addAttribute("locationIds", locationIds);
    	model.addAttribute("years", years);
    	model.addAttribute("quarters", quarters);
    	model.addAttribute("months", months);
    	model.addAttribute("reportDates", reportDates);
    	model.addAttribute("reportStatuses", reportStatuses);
    	model.addAttribute("reportNames", reportNames);
        
        model.addAttribute("reportOblasts", oblasts);
    	model.addAttribute("reportLocations", locations);
        model.addAttribute("oblasts", o);
    	model.addAttribute("oblastLocations", oblastLocations);
	}


	@RequestMapping(method=RequestMethod.POST)//, value="/module/mdrtb/reporting/viewClosedReports")
    public ModelAndView viewClosedReportsPost(
    		HttpServletRequest request, HttpServletResponse response,
    		@RequestParam("oblast") String oblastId, 
    		@RequestParam("location") String locationId, 
    		@RequestParam("year") Integer year, 
    		@RequestParam("quarter") Integer quarter, 
    		@RequestParam("month") Integer month, 
    		@RequestParam("reportName") String reportName, 
    		@RequestParam("reportDate") String reportDate, 
    		@RequestParam("formAction") String formAction, 
            ModelMap model) throws EvaluationException {
		System.out.println("-----POST-All-----");
		
		Integer oblast = null; 
		Integer location = null; 
		String html = "";
		String returnStr = "";
		try {
			if(new PDFHelper().isInt(locationId)) { 
				location = (Context.getLocationService().getLocation(Integer.parseInt(locationId))).getId(); 
			}
			if(new PDFHelper().isInt(oblastId)) { 
				oblast = (Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblastId))).getId(); 
			}
			
			if(formAction.equals("unlock")) {
				System.out.println("-----UNLOCK-----");
				Context.getService(MdrtbService.class).unlockReport(oblast, location, year, quarter, month, reportName.replaceAll(" ", "_").toUpperCase(), reportDate);
				viewClosedReportsGet(model);
				returnStr = "/module/mdrtb/reporting/viewClosedReports";
			}
			else if(formAction.equals("view")) {
				System.out.println("-----VIEW-----");
				List<String> allReports = (List<String>) Context.getService(MdrtbService.class).readTableData(oblast, location, year, quarter, month, reportName.replaceAll(" ", "_").toUpperCase(), reportDate);

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
				model.addAttribute("location", location); 
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
