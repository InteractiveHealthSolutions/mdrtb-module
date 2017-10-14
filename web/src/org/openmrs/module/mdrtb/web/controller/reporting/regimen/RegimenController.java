package org.openmrs.module.mdrtb.web.controller.reporting.regimen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.model.Sheet;
import org.apache.poi.hssf.model.Workbook;
import org.apache.poi.hssf.record.formula.functions.Cell;
import org.apache.poi.hssf.record.formula.functions.Row;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.openmrs.Cohort;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.MdrtbConstants;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.form.TB03uForm;
import org.openmrs.module.mdrtb.regimen.Regimen;
import org.openmrs.module.mdrtb.regimen.RegimenHistory;
import org.openmrs.module.mdrtb.regimen.RegimenUtils;
import org.openmrs.module.mdrtb.reporting.PDFHelper;
import org.openmrs.module.mdrtb.reporting.ReportUtil;
import org.openmrs.module.mdrtb.reporting.TB03Data;
import org.openmrs.module.mdrtb.reporting.TB03Util;
import org.openmrs.module.mdrtb.reporting.data.Cohorts;
import org.openmrs.module.mdrtb.reporting.regimen.RegimenReportRow;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.specimen.Culture;
import org.openmrs.module.mdrtb.specimen.Dst;
import org.openmrs.module.mdrtb.specimen.DstResult;
import org.openmrs.module.mdrtb.specimen.HAIN;
import org.openmrs.module.mdrtb.specimen.Smear;
import org.openmrs.module.mdrtb.specimen.Xpert;
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
/*import org.openmrs.module.mdrtbdrugforecast.DrugCount;
import org.openmrs.module.mdrtbdrugforecast.MdrtbDrugStock;
import org.openmrs.module.mdrtbdrugforecast.MdrtbUtil;
import org.openmrs.module.mdrtbdrugforecast.MdrtbConcepts;
import org.openmrs.module.mdrtbdrugforecast.drugneeds.DrugForecastUtil;
import org.openmrs.module.mdrtbdrugforecast.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtbdrugforecast.regimen.Regimen;
import org.openmrs.module.mdrtbdrugforecast.regimen.RegimenUtils;
import org.openmrs.module.mdrtbdrugforecast.reporting.definition.MdrtbDrugForecastTreatmentStartedCohortDefinition;
import org.openmrs.module.mdrtbdrugforecast.reporting.definition.MdrtbDrugForecastTreatmentStartedOnDrugCohortDefinition;
import org.openmrs.module.mdrtbdrugforecast.service.MdrtbDrugForecastService;
import org.openmrs.module.mdrtbdrugforecast.status.TreatmentStatusCalculator;
import org.openmrs.module.mdrtbdrugforecast.web.controller.status.DashboardTreatmentStatusRenderer;*/
import org.springframework.web.servlet.ModelAndView;


import com.itextpdf.text.Font.FontStyle;

@Controller

public class RegimenController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Context.getDateFormat(), true, 10));
        binder.registerCustomEditor(Concept.class, new ConceptEditor());
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
        
    
    @RequestMapping(method=RequestMethod.GET, value="/module/mdrtb/reporting/regimen")
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
       
  
		// create a new workbook
		    	
    	         
    	 return new ModelAndView("/module/mdrtb/reporting/regimen", model);	
    	
    }
    
  
    
    
    @SuppressWarnings({ "deprecation", "unchecked" })
	@RequestMapping(method=RequestMethod.POST, value="/module/mdrtb/reporting/regimen")
    public static void doRegimen(
    		@RequestParam("district") Integer districtId,
    		@RequestParam("oblast") Integer oblastId,
    		@RequestParam("facility") Integer facilityId,
            @RequestParam(value="year", required=true) Integer year,
            @RequestParam(value="quarter", required=false) String quarter,
            @RequestParam(value="month", required=false) String month, HttpServletResponse response,
            ModelMap model) throws EvaluationException, IOException {
    	
    	
    	System.out.println("---POST-----");
    	System.out.println("PARAMS:" + oblastId + " " + districtId + " " + facilityId + " " + year + " " + quarter + " " + month);
    	
    	
    	ArrayList<Location> locList = Context.getService(MdrtbService.class).getLocationList(oblastId,districtId,facilityId);
    	
    	ArrayList<TB03uForm> tb03uList = Context.getService(MdrtbService.class).getTB03uFormsFilledWithTxStartDateDuring(locList, year, quarter, month);
    	
    	Map<String, Date> dateMap = ReportUtil.getPeriodDates(year, quarter, month);
    	
    	Date startDate = (Date)(dateMap.get("startDate"));
    	Date endDate = (Date)(dateMap.get("endDate"));
    	
    	MdrtbService ms = Context.getService(MdrtbService.class);
    	ArrayList<RegimenReportRow> rows = new ArrayList<RegimenReportRow>();
    	Integer programId = null;
    	Patient p = null;
    	Date treatmentStartDate = null;
    	RegimenReportRow rrr = null;
    	for(TB03uForm tf : tb03uList) {
    		
    		p = tf.getPatient();
    		programId = tf.getPatProgId();
    		
    		if(programId==null || p==null) {
    			continue;
    		}
    		
    		treatmentStartDate  = tf.getMdrTreatmentStartDate();
    		
    		if(treatmentStartDate==null) {
    			continue;
    		}
    		
    		RegimenHistory rh = RegimenUtils.getTbRegimenHistory(p);
    		rrr = new RegimenReportRow();
			rrr.setP(p);
			rrr.setTreatmentStartDate(treatmentStartDate);
    		if(rh!=null) {
    			Regimen regimen = rh.getRegimenOnDate(endDate);
    			
    			if(regimen.containsGeneric(ms.getConcept(TbConcepts.CAPREOMYCIN))) {
    				rrr.setCm(Boolean.TRUE);
    			}
    			
    			else {
    				rrr.setCm(Boolean.FALSE);
    			}
    			
    			if(regimen.containsGeneric(ms.getConcept(TbConcepts.AMIKACIN))) {
    				rrr.setAm(Boolean.TRUE);
    			}
    			
    			else {
    				rrr.setAm(Boolean.FALSE);
    			}
    			
    			if(regimen.containsGeneric(ms.getConcept(TbConcepts.MOXIFLOXACIN))) {
    				rrr.setMfx(Boolean.TRUE);
    				
    			}
    			
    			else {
    				rrr.setMfx(Boolean.FALSE);
    			}
    			
    			if(regimen.containsGeneric(ms.getConcept(TbConcepts.LEVOFLOXACIN))) {
    				rrr.setLfx(Boolean.TRUE);
    			}
    			
    			else {
    				rrr.setLfx(Boolean.FALSE);
    			}
    		
    			
    			if(regimen.containsGeneric(ms.getConcept(TbConcepts.PROTHIONAMIDE))) {
    				rrr.setPto(Boolean.TRUE);
    			}
    			
    			else {
    				rrr.setPto(Boolean.FALSE);
    			}
    			
    			if(regimen.containsGeneric(ms.getConcept(TbConcepts.CYCLOSERINE))) {
    				rrr.setCs(Boolean.TRUE);
    			}
    			
    			else {
    				rrr.setCs(Boolean.FALSE);
    			}
    			
    			if(regimen.containsGeneric(ms.getConcept(TbConcepts.P_AMINOSALICYLIC_ACID))) {
    				rrr.setPas(Boolean.TRUE);
    			}
    			
    			else {
    				rrr.setPas(Boolean.FALSE);
    			}
    			
    			if(regimen.containsGeneric(ms.getConcept(TbConcepts.PYRAZINAMIDE))) {
    				rrr.setZ(Boolean.TRUE);
    			}
    			
    			else {
    				rrr.setZ(Boolean.FALSE);
    			}
    			
    			if(regimen.containsGeneric(ms.getConcept(TbConcepts.ISONIAZID)) && regimen.containsGeneric(ms.getConcept(TbConcepts.RIFAMPICIN))) {
    				rrr.setHr(Boolean.TRUE);
    				rrr.setH(Boolean.FALSE);
    			}
    			
    			else {
    				rrr.setHr(Boolean.FALSE);
    				if(regimen.containsGeneric(ms.getConcept(TbConcepts.ISONIAZID))) {
    					rrr.setH(Boolean.TRUE);
    				}
    				else {
    					rrr.setH(Boolean.FALSE);
    				}
    			}
    			
    			if(regimen.containsGeneric(ms.getConcept(TbConcepts.ETHAMBUTOL))) {
    				rrr.setE(Boolean.TRUE);
    			}
    			
    			else {
    				rrr.setE(Boolean.FALSE);
    			}
    			
    			if(regimen.containsGeneric(ms.getConcept(TbConcepts.LINEZOLID))) {
    				rrr.setLzd(Boolean.TRUE);
    			}
    			
    			else {
    				rrr.setLzd(Boolean.FALSE);
    			}
    			
    			if(regimen.containsGeneric(ms.getConcept(TbConcepts.CLOFAZIMINE))) {
    				rrr.setCfz(Boolean.TRUE);
    			}
    			
    			else {
    				rrr.setCfz(Boolean.FALSE);
    			}
    			
    			if(regimen.containsGeneric(ms.getConcept(TbConcepts.BEDAQUILINE))) {
    				rrr.setBdq(Boolean.TRUE);
    			}
    			
    			else {
    				rrr.setBdq(Boolean.FALSE);
    			}
    			
    		}
    		
    		rows.add(rrr);
    		
    		programId = null;
    		p = null;
    		treatmentStartDate = null;
    		rrr = null;
    	}
    	
    	String oName = null;
    	String dName = null;
    	String fName = null;
    	
    	if(facilityId!=null) {
    		Facility f = ms.getFacility(facilityId);
    		if(f!=null)
    			fName = f.getName();
    	}
    	
       if(districtId!=null) {
    	   District d = ms.getDistrict(districtId);
    	   if(d!=null) {
    		   dName = d.getName();
    	   }
       }
       
       if(oblastId!=null) {
    	   Oblast o = ms.getOblast(oblastId);
    	   if(o!=null) {
    		   oName = o.getName();
    	   }
       }
    	
    	 FileOutputStream out = null;
 		try {
 			out = new FileOutputStream(System.getProperty("user.home") + File.separator + "regimenReport.xls");
 		} catch (FileNotFoundException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    	
    	HSSFWorkbook wb = new HSSFWorkbook();
		// create a new sheet
		HSSFSheet s = wb.createSheet();
		
		if(fName!=null)
			wb.setSheetName(0, fName);
		
		else if(dName!=null) {
			wb.setSheetName(0, dName);
		}
		else if(oName!=null) {
			wb.setSheetName(0, oName);
		}
		
		// create 3 cell styles
		HSSFCellStyle headerStyle = wb.createCellStyle();
		//headerStyle.setFillForegroundColor(getColorIndex("79,129,89", wb));
		headerStyle.setFillForegroundColor(HSSFColor.BLUE.index);
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setTopBorderColor(HSSFColor.WHITE.index);
		headerStyle.setBottomBorderColor(HSSFColor.WHITE.index);
		headerStyle.setRightBorderColor(HSSFColor.WHITE.index);
		headerStyle.setLeftBorderColor(HSSFColor.WHITE.index);
		
		HSSFFont headerFont = wb.createFont();
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerStyle.setFont(headerFont);
		headerStyle.setWrapText(true);
		
		HSSFCellStyle oddStyle = wb.createCellStyle();
		//oddStyle.setFillForegroundColor(getColorIndex("184,204,228", wb));
		oddStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
		oddStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		oddStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		oddStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		oddStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		oddStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		oddStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		oddStyle.setTopBorderColor(HSSFColor.WHITE.index);
		oddStyle.setBottomBorderColor(HSSFColor.WHITE.index);
		oddStyle.setRightBorderColor(HSSFColor.WHITE.index);
		oddStyle.setLeftBorderColor(HSSFColor.WHITE.index);
		
		HSSFCellStyle evenStyle = wb.createCellStyle();
		//evenStyle.setFillForegroundColor(getColorIndex("220,230,241", wb));
		evenStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		evenStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		evenStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		evenStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		evenStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		evenStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		evenStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		evenStyle.setTopBorderColor(HSSFColor.WHITE.index);
		evenStyle.setBottomBorderColor(HSSFColor.WHITE.index);
		evenStyle.setRightBorderColor(HSSFColor.WHITE.index);
		evenStyle.setLeftBorderColor(HSSFColor.WHITE.index);
		
		HSSFRow headerRow = s.createRow(0);
		headerRow.setHeight((short) 0x300);
		
		HSSFCell serialHeaderCell = headerRow.createCell((short) 0);
		serialHeaderCell.setCellStyle(headerStyle);
		serialHeaderCell.setCellValue("#");
		
		HSSFCell nameHeaderCell = headerRow.createCell((short) 1);
		nameHeaderCell.setCellStyle(headerStyle);
		nameHeaderCell.setCellValue(Context.getMessageSourceService().getMessage("mdrtb.tb03.name"));
		
		//TxStartDate
		HSSFCell txStartHeaderCell =  headerRow.createCell((short) 2);
		txStartHeaderCell.setCellStyle(headerStyle);
		txStartHeaderCell.setCellValue(Context.getMessageSourceService().getMessage("mdrtb.sldreport..treatmentStartDate"));
		
		
		//Cm
		HSSFCell cmHeaderCell =  headerRow.createCell((short) 3);
		cmHeaderCell.setCellStyle(headerStyle);
		//cmHeaderCell.setEncoding(HSSFHeaderCell.ENCODING_UTF_16);
		cmHeaderCell.setCellValue("Cm 1 g");
		
		//Am
		HSSFCell amHeaderCell =  headerRow.createCell((short) 4);
		amHeaderCell.setCellStyle(headerStyle);
		//amHeaderCell.setEncoding(HSSFHeaderCell.ENCODING_UTF_16);
		amHeaderCell.setCellValue("Am 0.5 g");
		
		//Mfx
		HSSFCell mfxHeaderCell =  headerRow.createCell((short) 5);
		mfxHeaderCell.setCellStyle(headerStyle);
		//mfxHeaderCell.setEncoding(HSSFHeaderCell.ENCODING_UTF_16);
		mfxHeaderCell.setCellValue("Mxf 400 mg");
		
		//Lfx
		HSSFCell lfxHeaderCell =  headerRow.createCell((short) 6);
		lfxHeaderCell.setCellStyle(headerStyle);
		//LfxHeaderCell.setEncoding(HSSFHeaderCell.ENCODING_UTF_16);
		lfxHeaderCell.setCellValue("Lfx 250 mg");
		
		//Pto
		HSSFCell ptoHeaderCell =  headerRow.createCell((short) 7);
		ptoHeaderCell.setCellStyle(headerStyle);
		//ptoHeaderCell.setEncoding(HSSFHeaderCell.ENCODING_UTF_16);
		ptoHeaderCell.setCellValue("Pto 250 mg");
		
		//Cs
		HSSFCell csHeaderCell =  headerRow.createCell((short) 8);
		csHeaderCell.setCellStyle(headerStyle);
		//csHeaderCell.setEncoding(HSSFHeaderCell.ENCODING_UTF_16);
		csHeaderCell.setCellValue("Cs 250 mg");
		
		//Pas
		HSSFCell pasHeaderCell =  headerRow.createCell((short) 9);
		pasHeaderCell.setCellStyle(headerStyle);
		//pasHeaderCell.setEncoding(HSSFHeaderCell.ENCODING_UTF_16);
		pasHeaderCell.setCellValue("PAS 4g");
		
		//Z
		HSSFCell zHeaderCell =  headerRow.createCell((short) 10);
		zHeaderCell.setCellStyle(headerStyle);
		//zHeaderCell.setEncoding(HSSFHeaderCell.ENCODING_UTF_16);
		zHeaderCell.setCellValue("Z 400 mg");
		
		//E
		HSSFCell eHeaderCell =  headerRow.createCell((short) 11);
		eHeaderCell.setCellStyle(headerStyle);
		//eHeaderCell.setEncoding(HSSFHeaderCell.ENCODING_UTF_16);
		eHeaderCell.setCellValue("E 400 mg");
		
		//H
		HSSFCell hHeaderCell =  headerRow.createCell((short) 12);
		hHeaderCell.setCellStyle(headerStyle);
		//hHeaderCell.setEncoding(HSSFHeaderCell.ENCODING_UTF_16);
		hHeaderCell.setCellValue("H 300 mg");
		
		//Lzd
		HSSFCell lzdHeaderCell =  headerRow.createCell((short) 13);
		lzdHeaderCell.setCellStyle(headerStyle);
		//lzdHeaderCell.setEncoding(HSSFHeaderCell.ENCODING_UTF_16);
		lzdHeaderCell.setCellValue("600 mg");
		
		//Cfz
		HSSFCell cfzHeaderCell =  headerRow.createCell((short) 14);
		cfzHeaderCell.setCellStyle(headerStyle);
		//cfzHeaderCell.setEncoding(HSSFHeaderCell.ENCODING_UTF_16);
		cfzHeaderCell.setCellValue("Cfz 100 mg");
		
		//Bdq
		HSSFCell bdqHeaderCell =  headerRow.createCell((short) 15);
		bdqHeaderCell.setCellStyle(headerStyle);
		//bdqHeaderCell.setEncoding(HSSFHeaderCell.ENCODING_UTF_16);
		bdqHeaderCell.setCellValue("Bdq 100 mg");
		
		//HR
		HSSFCell hrHeaderCell =  headerRow.createCell((short) 16);
		hrHeaderCell.setCellStyle(headerStyle);
		//hrHeaderCell.setEncoding(HSSFHeaderCell.ENCODING_UTF_16);
		hrHeaderCell.setCellValue("HR 75/150");
			
		//}
		
		//do header row stuff here
		RegimenReportRow row = null;
		HSSFCellStyle cs = null;
		
    	for(int i=0; i<rows.size(); i++) {
    		
    		row = rows.get(i);
    		Patient pt = row.getPatient();
    		if(i%2==0)
    			cs = evenStyle;
    		else
    			cs = oddStyle;
    		
    		HSSFRow regRow = s.createRow(i+1);
    		
    		//serial number
    		HSSFCell serialCell = regRow.createCell((short) 0);
    		serialCell.setCellStyle(cs);
    		serialCell.setCellValue("" + (i+1));
    		
    		//Name
    		HSSFCell nameCell = regRow.createCell((short) 1);
    		nameCell.setCellStyle(cs);
    		//nameCell.setEncoding(HSSFCell.ENCODING_UTF_16);
    		nameCell.setCellValue(pt.getFamilyName() + "," + pt.getGivenName());
    		
    		//TxStartDate
    		HSSFCell txStartCell = regRow.createCell((short) 2);
    		txStartCell.setCellStyle(cs);
    		txStartCell.setCellValue(row.getFormattedTreatmentStartDate());
    		
    		
    		//Cm
    		HSSFCell cmCell = regRow.createCell((short) 3);
    		cmCell.setCellStyle(cs);
    		//cmCell.setEncoding(HSSFCell.ENCODING_UTF_16);
    		if(row.getCm())
    			cmCell.setCellValue("√");
    		
    		//Am
    		HSSFCell amCell = regRow.createCell((short) 4);
    		amCell.setCellStyle(cs);
    		//amCell.setEncoding(HSSFCell.ENCODING_UTF_16);
    		if(row.getAm())
    			amCell.setCellValue("√");
    		
    		//Mfx
    		HSSFCell mfxCell = regRow.createCell((short) 5);
    		mfxCell.setCellStyle(cs);
    		//mfxCell.setEncoding(HSSFCell.ENCODING_UTF_16);
    		if(row.getMfx())
    			mfxCell.setCellValue("√");
    		
    		//Lfx
    		HSSFCell lfxCell = regRow.createCell((short) 6);
    		lfxCell.setCellStyle(cs);
    		//LfxCell.setEncoding(HSSFCell.ENCODING_UTF_16);
    		if(row.getLfx())
    			lfxCell.setCellValue("√");
    		
    		//Pto
    		HSSFCell ptoCell = regRow.createCell((short) 7);
    		ptoCell.setCellStyle(cs);
    		//ptoCell.setEncoding(HSSFCell.ENCODING_UTF_16);
    		if(row.getPto())
    			ptoCell.setCellValue("√");
    		
    		//Cs
    		HSSFCell csCell = regRow.createCell((short) 8);
    		csCell.setCellStyle(cs);
    		//csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
    		if(row.getCs())
    			csCell.setCellValue("√");
    		
    		//Pas
    		HSSFCell pasCell = regRow.createCell((short) 9);
    		pasCell.setCellStyle(cs);
    		//pasCell.setEncoding(HSSFCell.ENCODING_UTF_16);
    		if(row.getPas())
    			pasCell.setCellValue("√");
    		
    		//Z
    		HSSFCell zCell = regRow.createCell((short) 10);
    		zCell.setCellStyle(cs);
    		//zCell.setEncoding(HSSFCell.ENCODING_UTF_16);
    		if(row.getZ())
    			zCell.setCellValue("√");
    		
    		//E
    		HSSFCell eCell = regRow.createCell((short) 11);
    		eCell.setCellStyle(cs);
    		//eCell.setEncoding(HSSFCell.ENCODING_UTF_16);
    		if(row.getE())
    			eCell.setCellValue("√");
    		
    		//H
    		HSSFCell hCell = regRow.createCell((short) 12);
    		hCell.setCellStyle(cs);
    		//hCell.setEncoding(HSSFCell.ENCODING_UTF_16);
    		if(row.getH())
    			hCell.setCellValue("√");
    		
    		//Lzd
    		HSSFCell lzdCell = regRow.createCell((short) 13);
    		lzdCell.setCellStyle(cs);
    		//lzdCell.setEncoding(HSSFCell.ENCODING_UTF_16);
    		if(row.getLzd())
    			lzdCell.setCellValue("√");
    		
    		//Cfz
    		HSSFCell cfzCell = regRow.createCell((short) 14);
    		cfzCell.setCellStyle(cs);
    		//cfzCell.setEncoding(HSSFCell.ENCODING_UTF_16);
    		if(row.getCfz())
    			cfzCell.setCellValue("√");
    		
    		//Bdq
    		HSSFCell bdqCell = regRow.createCell((short) 15);
    		bdqCell.setCellStyle(cs);
    		//bdqCell.setEncoding(HSSFCell.ENCODING_UTF_16);
    		if(row.getBdq())
    			bdqCell.setCellValue("√");
    		
    		//HR
    		HSSFCell hrCell = regRow.createCell((short) 16);
    		hrCell.setCellStyle(cs);
    		//hrCell.setEncoding(HSSFCell.ENCODING_UTF_16);
    		if(row.getHr())
    			hrCell.setCellValue("√");
    		
    	}
    	HSSFPalette palette  = wb.getCustomPalette();
    	palette.setColorAtIndex(HSSFColor.BLUE.index, (byte) 79, (byte) 129, (byte) 189);
    	palette.setColorAtIndex(HSSFColor.PALE_BLUE.index, (byte) 220, (byte) 230, (byte) 241);
    	palette.setColorAtIndex(HSSFColor.BLUE_GREY.index, (byte) 184, (byte) 204, (byte) 228);
    	
    	
        
        

   	 // write the workbook to the output stream
   	 // close our file (don't blow out our file handles
   	 try {
			wb.write(out);
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   	 
   	File file = new File(System.getProperty("user.home") + File.separator + "regimenReport.xls") ;
   	response.setContentType("application/vnd.ms-excel");
	
	String headerKey = "Content-Disposition";
    String headerValue = String.format("attachment; filename=\"%s\"",
            "regimenReport.xls");
    response.setHeader(headerKey, headerValue);
    response.setContentLength((int)file.length());
    
    FileInputStream inputStream = new FileInputStream(file);
    
	OutputStream outStream = response.getOutputStream();
	byte[] buffer = new byte[4096];
    int bytesRead = -1;

    // write bytes read from the input stream into the output stream
    while ((bytesRead = inputStream.read(buffer)) != -1) {
        outStream.write(buffer, 0, bytesRead);
    }

    inputStream.close();
    outStream.close();
	file.delete();
    
		
    	/*boolean reportStatus;
    	
    	    	
    	reportStatus = Context.getService(MdrtbService.class).readReportStatus(oblastId, districtId, facilityId, year, quarter, month, "TB-03", "DOTSTB");
		
    	System.out.println(reportStatus);
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
    	//model.addAttribute("reportDate", sdf.format(new Date()));
    	model.addAttribute("reportStatus", reportStatus);
    	return "/module/mdrtb/reporting/regimen";*/
    }
    
    private static Short getColorIndex(String colorStr, HSSFWorkbook wb) {
        //in this method string containing RGB component is passed
        //and corresponding color index is obtained and returned. 
        short index = 0;
        String[] rgb = colorStr.split(",");
        System.out.println(colorStr+"--------------");

        Integer red = Integer.parseInt(rgb[0]);
        Integer green = Integer.parseInt(rgb[1]);
        Integer blue = Integer.parseInt(rgb[2]);

        HSSFPalette palette = wb.getCustomPalette();
        HSSFColor color = palette.findColor(red.byteValue(), green.byteValue(), blue.byteValue());
        
        if(color != null){
             index = color.getIndex();
        }
        else{
            index = HSSFColor.WHITE.index;
        }
        return index;
     }
    
    
  
    
}
