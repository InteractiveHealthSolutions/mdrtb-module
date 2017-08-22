package org.openmrs.module.mdrtb.web.controller.reporting;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
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

@Controller
public class ExportReportController {

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/exportReport")
    public void exportReportGet(ModelMap model) {
        System.out.println("-----Export Report GET-----");
        List<Location> locations = Context.getLocationService().getAllLocations(false);
        List<Oblast> oblasts = Context.getService(MdrtbService.class).getOblasts();
        model.addAttribute("locations", locations);
        model.addAttribute("oblasts", oblasts);
	}

	@RequestMapping(method=RequestMethod.POST)//, value="/module/mdrtb/reporting/exportReport"
    public String exportReportPost(
    		HttpServletRequest request, HttpServletResponse response,
    		@RequestParam("oblast") String oblastId, 
    		@RequestParam("location") String locationId, 
    		@RequestParam("year") Integer year, 
    		@RequestParam("quarter") Integer quarter, 
    		@RequestParam("month") Integer month, 
    		@RequestParam("reportDate") String reportDate, 
    		@RequestParam("table") String table, 
    		@RequestParam("reportName") String reportName, 
    		@RequestParam("formPath") String formPath, 
    		ModelMap model) throws Exception {
        System.out.println("-----Export Report POST-----");
        
		Integer oblast = null;
		Integer location = null;
		ByteArrayOutputStream baos = null;
		
		Location tb08u_location = null;
    	String tb08u_oblast = oblastId;
    	String date = reportDate;
        Integer tb08u_year = year;
        String tb08u_quarter = "";
        String tb08u_month = "";
        
		try {
			if(new PDFHelper().isString(quarter)) { 
				tb08u_quarter = Integer.toString(quarter); 
			}
			if(new PDFHelper().isString(month)) { 
				tb08u_month = Integer.toString(month); 
			}
			if(new PDFHelper().isInt(locationId)) { 
				tb08u_location = Context.getLocationService().getLocation(Integer.parseInt(locationId));
				location = tb08u_location.getId(); 
			}
			if(new PDFHelper().isInt(oblastId)) { 
				oblast = (Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblastId))).getId(); 
			}
			if(!(reportDate.equals(""))) {
				date = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new SimpleDateFormat("dd.MM.yyyy").parse(reportDate)); 
			}
			if(!(table.equals(""))) {
				table = table.replaceAll("<br>", " ");
		    	table = table.replaceAll("<br/>", " ");
		    	table = table.replaceAll("\"", "'");
		    	
		    	String html = "<html><body>" + table + "</body></html>"; 
	    		//System.out.println(html);
		    	baos = new PDFHelper().createAndDownloadPdf(html);
		    	response.setHeader("Expires", "0");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Pragma", "public");
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "attachment; filename=download.pdf" );
				OutputStream os = response.getOutputStream();
				baos.writeTo(os);
				os.flush();
				os.close();
			}
			
			System.out.println("---POST EXPORT-----");
	    	System.out.println("oblast: " + oblast);
	    	System.out.println("location: " + location);
			System.out.println("year: " + year);
			System.out.println("quarter: " + quarter);
			System.out.println("month: " + month);
	    	System.out.println("reportDate: " + date);
	    	System.out.println("formPath: " + formPath);
	    	System.out.println("reportName: " + reportName);
			System.out.println("\n\n\n");
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("ex", e); 
		}
        return TB08uController.doTB08(tb08u_location, tb08u_oblast, tb08u_year, tb08u_quarter, tb08u_month, model);
    }
	
}
