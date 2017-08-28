package org.openmrs.module.mdrtb.web.controller.form;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Location;

import org.openmrs.Person;

import org.openmrs.api.context.Context;

import org.openmrs.module.mdrtb.service.MdrtbService;

import org.openmrs.module.mdrtb.form.DSTForm;
import org.openmrs.module.mdrtb.form.HAINForm;


import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.program.TbPatientProgram;
import org.openmrs.module.mdrtb.web.util.MdrtbWebUtil;
import org.openmrs.PatientProgram;
import org.openmrs.propertyeditor.ConceptEditor;
import org.openmrs.propertyeditor.LocationEditor;
import org.openmrs.propertyeditor.PersonEditor;
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
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/module/mdrtb/form/dst.form")
public class DSTFormController {
	
	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		
		//bind dates
		SimpleDateFormat dateFormat = Context.getDateFormat();
    	dateFormat.setLenient(false);
    	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true, 10));
		
		// register binders 
		binder.registerCustomEditor(Location.class, new LocationEditor());
		binder.registerCustomEditor(Person.class, new PersonEditor());
		binder.registerCustomEditor(Concept.class, new ConceptEditor());
		
	}
	
	@ModelAttribute("dst")
	public DSTForm getDSTForm(@RequestParam(required = true, value = "encounterId") Integer encounterId,
	                            @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		boolean mdr = false;
		PatientProgram pp = Context.getProgramWorkflowService().getPatientProgram(patientProgramId);
		if(pp.getProgram().getConcept().getId().intValue() == Context.getConceptService().getConceptByName(Context.getAdministrationService().getGlobalProperty("mdrtb.program_name")).getId().intValue()) {
			mdr=true;
			System.out.println("mdr");
		}
		
		else {
			mdr = false;
			System.out.println("not mdr");
		}
		// if no form is specified, create a new one
		if (encounterId == -1) {
			DSTForm form = null;
			if(!mdr) {
				TbPatientProgram tbProgram = Context.getService(MdrtbService.class).getTbPatientProgram(patientProgramId);
			
				form = new DSTForm(tbProgram.getPatient());
			
				// prepopulate the intake form with any program information
				form.setEncounterDatetime(tbProgram.getDateEnrolled());
				form.setLocation(tbProgram.getLocation());
			}
			
			else {
				MdrtbPatientProgram mdrtbProgram = Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId);
				
				form = new DSTForm(mdrtbProgram.getPatient());
			
				// prepopulate the intake form with any program information
				form.setEncounterDatetime(mdrtbProgram.getDateEnrolled());
				form.setLocation(mdrtbProgram.getLocation());
			}
			return form;
		}
		else {
			return new DSTForm(Context.getEncounterService().getEncounter(encounterId));
		}
	}
	
	/*@ModelAttribute("hainmdr")
	public HAINForm getMdrHAINForm(@RequestParam(required = true, value = "encounterId") Integer encounterId,
	                            @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		// if no form is specified, create a new one
		if (encounterId == -1) {
			MdrtbPatientProgram mdrtbProgram = Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId);
			
			HAINForm form = new HAINForm(mdrtbProgram.getPatient());
			
			// prepopulate the intake form with any program information
			form.setEncounterDatetime(mdrtbProgram.getDateEnrolled());
			form.setLocation(mdrtbProgram.getLocation());
				
			return form;
		}
		else {
			return new HAINForm(Context.getEncounterService().getEncounter(encounterId));
		}
	}*/
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showDSTForm() {
		ModelMap map = new ModelMap();
		
		
		return new ModelAndView("/module/mdrtb/form/dst", map);	
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST)
	public ModelAndView processDSTForm (@ModelAttribute("dst") DSTForm dst, BindingResult errors, 
	                                       @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId,
	                                       @RequestParam(required = false, value = "returnUrl") String returnUrl,
	                                       SessionStatus status, HttpServletRequest request, ModelMap map) {
		
		boolean mdr = false;
		PatientProgram pp = Context.getProgramWorkflowService().getPatientProgram(patientProgramId);
		if(pp.getProgram().getConcept().getId().intValue() == Context.getConceptService().getConceptByName(Context.getAdministrationService().getGlobalProperty("mdrtb.program_name")).getId().intValue()) {
			mdr=true;
		}
		
		else {
			mdr = false;
		}
		
		// perform validation and check for errors
		/*if (tb03 != null) {
    		new SimpleFormValidator().validate(tb03, errors);
    	}*/
		
		/*if (errors.hasErrors()) {
			map.put("errors", errors);
			return new ModelAndView("/module/mdrtb/form/intake", map);
		}*/
		
		// save the actual update
		Context.getEncounterService().saveEncounter(dst.getEncounter());
		
		// clears the command object from the session
		status.setComplete();
		

		map.clear();

		// if there is no return URL, default to the patient dashboard
		if (returnUrl == null || StringUtils.isEmpty(returnUrl)) {
			if(!mdr) {
				returnUrl = request.getContextPath() + "/module/mdrtb/dashboard/tbdashboard.form";
			}
			
			else {
				returnUrl = request.getContextPath() + "/module/mdrtb/dashboard/dashboard.form";
			}
		}
		
		returnUrl = MdrtbWebUtil.appendParameters(returnUrl, Context.getService(MdrtbService.class).getTbPatientProgram(patientProgramId).getPatient().getId(), patientProgramId);
		
		return new ModelAndView(new RedirectView(returnUrl));
	}
	
	@ModelAttribute("patientProgramId")
	public Integer getPatientProgramId(@RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) {
		return patientProgramId;
	}
	
	
	/*@ModelAttribute("tbProgram")
	public TbPatientProgram getTbPatientProgram(@RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) {
		return Context.getService(MdrtbService.class).getTbPatientProgram(patientProgramId);
	}
	
	@ModelAttribute("mdrtbProgram")
	public MdrtbPatientProgram getMdrtbPatientProgram(@RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) {
		return Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId);
	}*/
	
	@ModelAttribute("returnUrl")
	public String getReturnUrl(@RequestParam(required = false, value = "returnUrl") String returnUrl) {
		return returnUrl;
	}
	
	@ModelAttribute("providers")
	public Collection<Person> getProviders() {
		return Context.getService(MdrtbService.class).getProviders();
	}
	
	@ModelAttribute("locations")
	Collection<Location> getPossibleLocations() {
		return Context.getLocationService().getAllLocations(false);
	}
	

	
		
}
