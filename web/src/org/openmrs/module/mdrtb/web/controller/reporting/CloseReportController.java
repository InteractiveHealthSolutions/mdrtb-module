package org.openmrs.module.mdrtb.web.controller.reporting;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
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
public class CloseReportController {

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
	
	@RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/closeReport")
    public void closeReportGet(ModelMap model) {
        System.out.println("-----Close Report GET-----");
        List<Location> locations = Context.getLocationService().getAllLocations(false);
        List<Oblast> oblasts = Context.getService(MdrtbService.class).getOblasts();
        model.addAttribute("locations", locations);
        model.addAttribute("oblasts", oblasts);
	}

	@RequestMapping(method=RequestMethod.POST)//, value="/module/mdrtb/reporting/closeReport"
    public String closeReportPost(
    		HttpServletRequest request, HttpServletResponse response,
    		@RequestParam("oblast") Integer oblastId, 
    		@RequestParam("district") Integer districtId,
    		@RequestParam("facility") Integer facilityId, 
    		@RequestParam("year") Integer year, 
    		@RequestParam("quarter") String quarter, 
    		@RequestParam("month") String month, 
    		@RequestParam("reportDate") String reportDate, 
    		@RequestParam("table") String table, 
    		@RequestParam("reportName") String reportName, 
    		@RequestParam("formPath") String formPath, 
    		ModelMap model) throws EvaluationException, IOException, ServletException {
        System.out.println("-----Close Report POST-----");
        
        System.out.println("CRP-PARAMS:"+oblastId +":" + districtId + ":" + facilityId +":" + year + ":" + quarter + ":" + month + ":" + reportDate + ":" + reportName + ":" + formPath);
		
        Integer o = oblastId;
        Integer d = districtId;
        Integer f = facilityId;
        Integer y = year;
        String q = quarter;
        if(q!=null)
        	q = q.replace("\"", "");
        String m = month;
        if(m!=null && m.length()!=0)
        	m = m.replace("\"", "");
        String r = reportDate;
        String t = table;
        String rn = reportName;
        String fp = formPath;
        
	/*	Integer oblast = null;
		Integer district = null;
		Integer facility = null;*/
		//Integer location = null;
		String date = reportDate;
		String tableData = null;
		boolean reportStatus = false;
		
		/*String report_district = null;
		String report_facility = null;
    	String report_oblast = oblastId;
        Integer report_year = year;
        String report_quarter = "";
        String report_month = "";*/
		
        try {
			/*if(new PDFHelper().isString(quarter)) { 
				report_quarter = Integer.toString(quarter); 
			}
			if(new PDFHelper().isString(month)) { 
				report_month = Integer.toString(month); 
			}
			if(new PDFHelper().isInt(districtId)) {
				district = (Context.getService(MdrtbService.class).getDistrict(Integer.parseInt(districtId))).getId(); 
			}
			if(new PDFHelper().isInt(oblastId)) { 
				oblast = (Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblastId))).getId(); 
			}
			if(new PDFHelper().isInt(oblastId)) { 
				oblast = (Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblastId))).getId(); 
			}*/
			if(!(reportDate.equals(""))) {
				date = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(reportDate)); 
			}
			if(!(table.equals(""))) {
		    	tableData = new PDFHelper().compressCode(table);
			}
			reportStatus = true;
			
			String td = tableData;
			
	    	System.out.println("oblast: " + o);
	    	System.out.println("districtId: " + d);
	    	System.out.println("facilityId: " + f);
	    	System.out.println("year: " + y);
			System.out.println("quarter: " + q);
			System.out.println("month: " + m);
			System.out.println("tableData: " + td);
	    	System.out.println("reportDate: " + date);
	    	System.out.println("formPath: " + fp);
	    	System.out.println("reportStatus: " + reportStatus);
	    	System.out.println("reportName: " + rn);
			System.out.println("\n\n\n");
			
			
			
			
			if(formPath.equals("tb08uResults") || formPath.equals("tb07uResults") || formPath.equals("tb03uResults") || formPath.equals("dquResults")) {
				Context.getService(MdrtbService.class).doPDF(o, d, f, y, q, m, date, td, reportStatus, rn, "MDRTB");
			}
			else {
				try {
					System.out.println("Saving PDF in try...");
					Context.getService(MdrtbService.class).doPDF(o, d, f, y, q, m, date, td, reportStatus, rn, "DOTSTB");
				}
				
				catch(Exception ee) {
					System.out.println("Caught in inner catch:" + ee.getMessage());
					ee.printStackTrace();
					model.addAttribute("ex", ee); 
					model.addAttribute("reportStatus", reportStatus);
				}
			}
			model.addAttribute("reportStatus", reportStatus);
			request.getSession().setAttribute("reportStatus", reportStatus);
			
			System.out.println("---POST CLOSE-----");
		} catch (Exception e) {
			reportStatus = false;
			System.out.println("Caught in outer catch:" + e.getMessage());
			e.printStackTrace();

			model.addAttribute("ex", e); 
			model.addAttribute("reportStatus", reportStatus);
		} 
        
        
        String url = "";
        if(formPath.equals("tb08uResults")) {
        	url = TB08uController.doTB08(d, o, f, y, q, m, model);//(report_location, report_oblast, report_year, report_quarter, report_month, model);
	    }
        else if(formPath.equals("tb07uResults")) {
        	url = TB07uController.doTB08(d, o, f, y, q, m, model);//(report_location, report_oblast, report_year, report_quarter, report_month, model);
        }
       
        else if(formPath.equals("dquResults")) {
        	url = MDRDQController.doDQ(d, o, f, y, q, m, model);//(report_location, report_oblast, report_year, report_quarter, report_month, model);
        }
        
        else if(formPath.equals("dqResults")) {
        	url = DOTSDQController.doDQ(d, o, f, y, q, m, model);//(report_location, report_oblast, report_year, report_quarter, report_month, model);
        }
        else if(formPath.equals("tb07Results")) {
        	url = TB07ReportController.doTB07(d, o, f, y, q, m, model);//report_year, report_quarter, report_month, model);
        }
        else if(formPath.equals("tb08Results")) {
        	url = TB08ReportController.doTB08(d, o, f, y, q, m, model);//(report_location, report_oblast, report_year, report_quarter, report_month, model);
        	System.out.println("URL:" + url);
        }
        else if(formPath.equals("tb03Results")) {
        	url = TB03ExportController.doTB03(d, o, f, y, q, m, model);
        	System.out.println("URL:" + url);
        }
        
        else  if(formPath.equals("tb03uResults")) {
        	url = TB03uController.doTB03(d, o, f, y, q, m, model);
        	System.out.println("URL:" + url);
        }
        
        System.out.println("url: " + url);
		return url;
	}
	
}
