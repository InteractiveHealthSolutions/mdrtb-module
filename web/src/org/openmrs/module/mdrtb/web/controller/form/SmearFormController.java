package org.openmrs.module.mdrtb.web.controller.form;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Location;

import org.openmrs.Person;

import org.openmrs.api.context.Context;

import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.service.MdrtbService;

import org.openmrs.module.mdrtb.exception.MdrtbAPIException;
import org.openmrs.module.mdrtb.form.SmearForm;


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
@RequestMapping("/module/mdrtb/form/smear.form")
public class SmearFormController {
	
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
	
	@ModelAttribute("smear")
	public SmearForm getSmearForm(@RequestParam(required = true, value = "encounterId") Integer encounterId,
	                            @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		boolean mdr = false;
		PatientProgram pp = Context.getProgramWorkflowService().getPatientProgram(patientProgramId);
		//if(pp.getProgram().getConcept().getId().intValue() == Context.getConceptService().getConceptByName(Context.getAdministrationService().getGlobalProperty("mdrtb.program_name")).getId().intValue()) {
		if(pp.getProgram().getConcept().getId().intValue() == Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TB_PROGRAM).getId().intValue()) {
			mdr=true;
			System.out.println("mdr");
		}
		
		else {
			mdr = false;
			System.out.println("not mdr");
		}
		// if no form is specified, create a new one
		if (encounterId == -1) {
			SmearForm form = null;
			if(!mdr) {
				TbPatientProgram tbProgram = Context.getService(MdrtbService.class).getTbPatientProgram(patientProgramId);
			
				form = new SmearForm(tbProgram.getPatient());
			
				// prepopulate the intake form with any program information
				//form.setEncounterDatetime(tbProgram.getDateEnrolled());
				form.setLocation(tbProgram.getLocation());
			}
			
			else {
				MdrtbPatientProgram mdrtbProgram = Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId);
				
				form = new SmearForm(mdrtbProgram.getPatient());
			
				// prepopulate the intake form with any program information
				form.setEncounterDatetime(mdrtbProgram.getDateEnrolled());
				form.setLocation(mdrtbProgram.getLocation());
			}
			return form;
		}
		else {
			return new SmearForm(Context.getEncounterService().getEncounter(encounterId));
		}
	}
	
	/*@ModelAttribute("smearmdr")
	public SmearForm getMdrSmearForm(@RequestParam(required = true, value = "encounterId") Integer encounterId,
	                            @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		// if no form is specified, create a new one
		if (encounterId == -1) {
			MdrtbPatientProgram mdrtbProgram = Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId);
			
			SmearForm form = new SmearForm(mdrtbProgram.getPatient());
			
			// prepopulate the intake form with any program information
			form.setEncounterDatetime(mdrtbProgram.getDateEnrolled());
			form.setLocation(mdrtbProgram.getLocation());
				
			return form;
		}
		else {
			return new SmearForm(Context.getEncounterService().getEncounter(encounterId));
		}
	}*/
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showSmearForm(/*@RequestParam(value="loc", required=false) String district,
    								  @RequestParam(value="ob", required=false) String oblast,*/
    								  @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId,
    								  @RequestParam(required = false, value = "encounterId") Integer encounterId,
    								  ModelMap model){
		//ModelMap map = new ModelMap();
		
		/*List<Oblast> oblasts;
        List<Facility> facilities;
        List<District> districts;
        
        if(oblast==null && encounterId!=-1) //we are editing an existing encounter
        {
        	oblasts = Context.getService(MdrtbService.class).getOblasts();
        	model.addAttribute("oblasts", oblasts);
        	districts = Context.getService(MdrtbService.class).getDistricts();
        	model.addAttribute("districts", districts);
        	facilities = Context.getService(MdrtbService.class).getFacilities();
        	model.addAttribute("facilities", facilities);
        }
        else if(oblast==null) {
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
        model.addAttribute("encounterId", encounterId);*/
		return new ModelAndView("/module/mdrtb/form/smear", model);	
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSmearForm (@ModelAttribute("smear") SmearForm smear, BindingResult errors, 
	                                       @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId,
	                                       /*@RequestParam(required = true, value = "oblast") String oblastId,
	                                       @RequestParam(required = true, value = "district") String districtId,
	                                       @RequestParam(required = false, value = "facility") String facilityId,*/
	                                       @RequestParam(required = false, value = "returnUrl") String returnUrl,
	                                       SessionStatus status, HttpServletRequest request, ModelMap map) {
		
	/*	Location location=null;
    	List<Location> locations = null;// new ArrayList<Location>();
    	
    	
    	if(facilityId!=null && facilityId.length()!=0) {
    		//all fields selected
    		Facility fac = Context.getService(MdrtbService.class).getFacility(Integer.parseInt(facilityId));
    		District dist = Context.getService(MdrtbService.class).getDistrict(Integer.parseInt(districtId));
    		Oblast obl = Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblastId));
    		location = Context.getService(MdrtbService.class).getLocation(obl,dist,fac);
    		
    	}
    	
    	else if(districtId!=null && districtId.length()!=0) {
    		//district and oblast selected
    		District dist = Context.getService(MdrtbService.class).getDistrict(Integer.parseInt(districtId));
    		locations = Context.getService(MdrtbService.class).getLocationsFromDistrictName(dist);
    		
    	}
    	
    	else if(oblastId!=null && oblastId.length()!=0) {
    		Oblast obl = Context.getService(MdrtbService.class).getOblast(Integer.parseInt(oblastId));
    		locations = Context.getService(MdrtbService.class).getLocationsFromOblastName(obl);
    	}
    	
    	if(location == null && locations!=null && (locations.size()==0 || locations.size()>1)) {
    		throw new MdrtbAPIException("Invalid Hierarchy Set selected");
    	}
    	
    	else if(location==null && locations!=null && locations.size()==1) {
    		location = locations.get(0);
    	}
		if(smear.getLocation()==null || !location.equals(smear.getLocation())) {
			System.out.println("setting loc");
			smear.setLocation(location);
		}*/
    	//smear.getEncounter().setLocation(location);
		
		boolean mdr = false;
		PatientProgram pp = Context.getProgramWorkflowService().getPatientProgram(patientProgramId);
		if(pp.getProgram().getConcept().getId().intValue() == Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TB_PROGRAM).getId().intValue()) {
		//if(pp.getProgram().getConcept().getId().intValue() == Context.getConceptService().getConceptByName(Context.getAdministrationService().getGlobalProperty("mdrtb.program_name")).getId().intValue()) {
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
		Context.getEncounterService().saveEncounter(smear.getEncounter());
		
		boolean programModified = false;
		

		// clears the command object from the session
		status.setComplete();
		
		/*if(programModified) {
			System.out.println("saving program");
			Context.getProgramWorkflowService().savePatientProgram(pp);
		}*/
		
		map.clear();

		// if there is no return URL, default to the patient dashboard
		if (returnUrl == null || StringUtils.isEmpty(returnUrl)) {
			if(!mdr) {
				returnUrl = request.getContextPath() + "/module/mdrtb/dashboard/tbdashboard.form";
				returnUrl = MdrtbWebUtil.appendParameters(returnUrl, Context.getService(MdrtbService.class).getTbPatientProgram(patientProgramId).getPatient().getId(), patientProgramId);
			}
			
			else {
				returnUrl = request.getContextPath() + "/module/mdrtb/dashboard/dashboard.form";
				returnUrl = MdrtbWebUtil.appendParameters(returnUrl, Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId).getPatient().getId(), patientProgramId);
			}
		}
		
		
		
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
	
	@ModelAttribute("smearresults")
	public Collection<ConceptAnswer> getSmearResults() {
		return Context.getService(MdrtbService.class).getPossibleSmearResults();
	}
	
	

		
}
