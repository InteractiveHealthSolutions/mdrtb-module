package org.openmrs.module.mdrtb.web.controller.pv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientState;
import org.openmrs.ProgramWorkflowState;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.exception.MdrtbAPIException;
import org.openmrs.module.mdrtb.form.DrugResistanceDuringTreatmentForm;
import org.openmrs.module.mdrtb.form.RegimenForm;
import org.openmrs.module.mdrtb.form.TB03uXDRForm;
import org.openmrs.module.mdrtb.form.pv.AEForm;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgramHospitalizationValidator;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgramValidator;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.status.HivStatusCalculator;
import org.openmrs.module.mdrtb.status.LabResultsStatusCalculator;
import org.openmrs.module.mdrtb.status.Status;
import org.openmrs.module.mdrtb.status.StatusFlag;
import org.openmrs.module.mdrtb.status.TreatmentStatusCalculator;
import org.openmrs.module.mdrtb.status.VisitStatusCalculator;
import org.openmrs.module.mdrtb.web.controller.status.DashboardHivStatusRenderer;
import org.openmrs.module.mdrtb.web.controller.status.DashboardLabResultsStatusRenderer;
import org.openmrs.module.mdrtb.web.controller.status.DashboardTreatmentStatusRenderer;
import org.openmrs.module.mdrtb.web.controller.status.DashboardVisitStatusRenderer;
import org.openmrs.module.programlocation.PatientProgram;
import org.openmrs.propertyeditor.ConceptEditor;
import org.openmrs.propertyeditor.LocationEditor;
import org.openmrs.propertyeditor.ProgramWorkflowStateEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AEViewController {
	
	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		
		//bind dates
		SimpleDateFormat dateFormat = Context.getDateFormat();
    	dateFormat.setLenient(false);
    	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true, 10));
		
		// register binders for location and program workflow state
		binder.registerCustomEditor(Location.class, new LocationEditor());
		binder.registerCustomEditor(Concept.class, new ConceptEditor());
		binder.registerCustomEditor(ProgramWorkflowState.class, new ProgramWorkflowStateEditor());
		
	}
	
    @SuppressWarnings("unchecked")
    @RequestMapping("/module/mdrtb/pv/aeView.form")
	public ModelAndView showRegimens(
	                               @RequestParam(required = true, value = "patientId") Integer patientId,
	                               @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId,
	                               
	                               ModelMap map) {
  
    	Patient p = Context.getPatientService().getPatient(patientId);
    	MdrtbPatientProgram program = Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId);
    	
    	map.put("patientProgramId", program.getId());
    	
    	// add the patientId
    	map.put("patientId", program.getPatient().getId());
    	
    	ArrayList<AEForm> forms = Context.getService(MdrtbService.class).getAEFormsForProgram(program.getPatient(), program.getId());
		
		map.put("forms", forms);
		
		return new ModelAndView("/module/mdrtb/pv/aeView", map);

	}
    
    
   
}
