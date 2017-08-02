package org.openmrs.module.mdrtb.web.controller.reporting;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConcepts;
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

import com.itextpdf.text.DocumentException;

@Controller
public class CloseReportController {

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/closeReport")
    public void closeReportGet(ModelMap model) {
        System.out.println("-----Close Report-----");
        List<Location> locations = Context.getLocationService().getAllLocations(false);
        List<Oblast> oblasts = Context.getService(MdrtbService.class).getOblasts();
        model.addAttribute("locations", locations);
        model.addAttribute("oblasts", oblasts);
	}

	@RequestMapping(method=RequestMethod.POST)//, value="/module/mdrtb/reporting/closeReport"
    public void closeReportPost(
    		@RequestParam("oblast") String oblastId, 
    		@RequestParam("location") String locationId, 
    		@RequestParam("year") Integer year, 
    		@RequestParam("quarter") Integer quarter, 
    		@RequestParam("month") Integer month, 
    		@RequestParam("reportDate") String reportDate, 
    		@RequestParam("table") String table, 
    		ModelMap model) throws EvaluationException {

		try {

			Integer oblast = null;
			Integer location = null;
			byte[] tableData = null;
			
			if(isInt(locationId)) { location = (Context.getLocationService().getLocation(Integer.parseInt(locationId))).getId(); }
			if(isInt(oblastId)) { oblast = (Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblastId))).getId(); }
			if(!(reportDate.equals(""))) { reportDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new SimpleDateFormat("dd.MM.yyyy").parse(reportDate)); }
			if(!(table.equals(""))) {
				table = table.replaceAll("<br>", " ");
		    	table = table.replaceAll("<br/>", " ");
		    	table = table.replaceAll("\"", "'");
		    	String html = "<html><body><table>" + table + "</table></body></html>"; 
		    	PDFHelper pdf = new PDFHelper();
		    	//pdf.createPdf(html);
		    	tableData = pdf.createAndSavePdf(html);
			}

			System.out.println("---POST CLOSE-----");
	    	System.out.println("oblast:" + oblast);
	    	System.out.println("location:" + location);
			System.out.println("year" + year);
			System.out.println("quarter" + quarter);
			System.out.println("month" + month);
			System.out.println("tableData" + tableData);
	    	System.out.println("reportDate:" + reportDate);
			System.out.println("\n\n\n");

			Context.getService(MdrtbService.class).savePDF(oblast, location, year, quarter, month, reportDate, tableData);

		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        //return "/module/mdrtb/reporting/alltb08u";
    
    }

	private static boolean isInt(String str) { try { Integer.parseInt(str); } catch(NumberFormatException e) { return false; } catch(NullPointerException e) { return false; } return true; }
}
