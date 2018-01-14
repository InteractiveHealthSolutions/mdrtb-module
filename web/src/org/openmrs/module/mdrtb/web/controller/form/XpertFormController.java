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
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.service.MdrtbService;

import org.openmrs.module.mdrtb.exception.MdrtbAPIException;
import org.openmrs.module.mdrtb.form.SmearForm;
import org.openmrs.module.mdrtb.form.XpertForm;


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
@RequestMapping("/module/mdrtb/form/xpert.form")
public class XpertFormController {
	
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
	
	@ModelAttribute("xpert")
	public XpertForm getXpertForm(@RequestParam(required = true, value = "encounterId") Integer encounterId,
	                            @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		boolean mdr = false;
		PatientProgram pp = Context.getProgramWorkflowService().getPatientProgram(patientProgramId);
		if(pp.getProgram().getConcept().getId().intValue() == Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TB_PROGRAM).getId().intValue()) {
		//if(pp.getProgram().getConcept().getId().intValue() == Context.getConceptService().getConceptByName(Context.getAdministrationService().getGlobalProperty("mdrtb.program_name")).getId().intValue()) {
			mdr=true;
			System.out.println("mdr");
		}
		
		else {
			mdr = false;
			System.out.println("not mdr");
		}
		// if no form is specified, create a new one
		if (encounterId == -1) {
			XpertForm form = null;
			if(!mdr) {
				TbPatientProgram tbProgram = Context.getService(MdrtbService.class).getTbPatientProgram(patientProgramId);
			
				form = new XpertForm(tbProgram.getPatient());
			
				// prepopulate the intake form with any program information
				//form.setEncounterDatetime(tbProgram.getDateEnrolled());
				//form.setLocation(tbProgram.getLocation());
			}
			
			else {
				MdrtbPatientProgram mdrtbProgram = Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId);
				
				form = new XpertForm(mdrtbProgram.getPatient());
			
				// prepopulate the intake form with any program information
				//form.setEncounterDatetime(mdrtbProgram.getDateEnrolled());
				form.setLocation(mdrtbProgram.getLocation());
			}
			return form;
		}
		else {
			return new XpertForm(Context.getEncounterService().getEncounter(encounterId));
		}
	}
	
	/*@ModelAttribute("xpertmdr")
	public XpertForm getMdrXpertForm(@RequestParam(required = true, value = "encounterId") Integer encounterId,
	                            @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		// if no form is specified, create a new one
		if (encounterId == -1) {
			MdrtbPatientProgram mdrtbProgram = Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId);
			
			XpertForm form = new XpertForm(mdrtbProgram.getPatient());
			
			// prepopulate the intake form with any program information
			form.setEncounterDatetime(mdrtbProgram.getDateEnrolled());
			form.setLocation(mdrtbProgram.getLocation());
				
			return form;
		}
		else {
			return new XpertForm(Context.getEncounterService().getEncounter(encounterId));
		}
	}*/
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showXpertForm(@RequestParam(value="loc", required=false) String district,
			  @RequestParam(value="ob", required=false) String oblast,
			  @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId,
			  @RequestParam(required = false, value = "encounterId") Integer encounterId,
			  @RequestParam(required = false, value = "mode") String mode,
			  ModelMap model) {
		List<Oblast> oblasts;
        List<Facility> facilities;
        List<District> districts;
        
        if(oblast==null)
        {
        	XpertForm xpert = null;
        	if(encounterId!=-1) {  //we are editing an existing encounter
        		xpert = new XpertForm(Context.getEncounterService().getEncounter(encounterId));
        	}
        	else {
        		try {
        			xpert = getXpertForm(-1, patientProgramId);
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	
        	Location location  = xpert.getLocation();
        	
        	if(location!=null) {
        	oblasts = Context.getService(MdrtbService.class).getOblasts();
        	model.addAttribute("oblasts", oblasts);
        	for(Oblast o : oblasts) {
        		if(o.getName().equals(location.getStateProvince())) {
        			model.addAttribute("oblastSelected", o.getId());
        			districts = Context.getService(MdrtbService.class).getDistricts(o.getId());
        			model.addAttribute("districts", districts);
        			for(District d : districts) {
        				if(d.getName().equals(location.getCountyDistrict())) {
        					model.addAttribute("districtSelected", d.getId());
        					facilities = Context.getService(MdrtbService.class).getFacilities(d.getId());
        					if(facilities != null ) {
        						model.addAttribute("facilities", facilities);
        						for(Facility f : facilities) {
        							if(f.getName().equals(location.getRegion())) {
        								System.out.println("setting");
        								model.addAttribute("facilitySelected", f.getId());
        								break;
        							}
        						}
        					}
        					break;
        				}
        			}
        			
        			break;
        		}
        	}
        }
        	
        	else {
        		oblasts = Context.getService(MdrtbService.class).getOblasts();
        		 model.addAttribute("oblasts", oblasts);
        	}
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
        model.addAttribute("encounterId", encounterId);
        if(mode!=null && mode.length()!=0) {
        	model.addAttribute("mode", mode);
        }
		
		
		return new ModelAndView("/module/mdrtb/form/xpert", model);	
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST)
	public ModelAndView processXpertForm (@ModelAttribute("xpert") XpertForm xpert, BindingResult errors, 
	                                       @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId,
	                                       @RequestParam(required = true, value = "oblast") String oblastId,
	                                       @RequestParam(required = true, value = "district") String districtId,
	                                       @RequestParam(required = false, value = "facility") String facilityId,
	                                       @RequestParam(required = false, value = "returnUrl") String returnUrl,
	                                       SessionStatus status, HttpServletRequest request, ModelMap map) {
		Location location=null;
    	
    	
    	System.out.println("PARAMS:\nob: " + oblastId + "\ndist: " + districtId + "\nfac: " + facilityId);
    	
    	if(facilityId!=null && facilityId.length()!=0)
    		location = Context.getService(MdrtbService.class).getLocation(Integer.parseInt(oblastId),Integer.parseInt(districtId),Integer.parseInt(facilityId));
    	else
    		location = Context.getService(MdrtbService.class).getLocation(Integer.parseInt(oblastId),Integer.parseInt(districtId),null);
    	
    	
    	if(location == null) { // && locations!=null && (locations.size()==0 || locations.size()>1)) {
    		throw new MdrtbAPIException("Invalid Hierarchy Set selected");
    	}
    	
    	
		if(xpert.getLocation()==null || !location.equals(xpert.getLocation())) {
			System.out.println("setting loc");
			xpert.setLocation(location);
		}
		
		boolean mdr = false;
		PatientProgram pp = Context.getProgramWorkflowService().getPatientProgram(patientProgramId);
		//if(pp.getProgram().getConcept().getId().intValue() == Context.getConceptService().getConceptByName(Context.getAdministrationService().getGlobalProperty("mdrtb.program_name")).getId().intValue()) {
		if(pp.getProgram().getConcept().getId().intValue() == Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TB_PROGRAM).getId().intValue()) {
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
		
		
		
		if(xpert.getMtbResult()!=null && xpert.getMtbResult().getId().intValue()!=Context.getService(MdrtbService.class).getConcept(TbConcepts.MTB_POSITIVE).getId().intValue()) {
			
			System.out.println("Setting null");
			xpert.setRifResult(null);
		}
		
		// save the actual update
		Context.getEncounterService().saveEncounter(xpert.getEncounter());
		
		boolean programModified = false;
		//handle changes in workflows
		/*Concept outcome = tb03.getTreatmentOutcome();
		Concept group = tb03.getRegistrationGroup();
		
		PatientProgram pp = Context.getProgramWorkflowService().getPatientProgram(patientProgramId);
		
		ProgramWorkflow outcomeFlow = new ProgramWorkflow();
		outcomeFlow.setConcept(outcome);
		PatientState outcomePatientState = pp.getCurrentState(outcomeFlow);
		//ProgramWorkflowState pwfs = null;
		Concept currentOutcomeConcept = null;
		//outcome entered previously but now removed
		if(outcomePatientState != null && outcome == null) {
			System.out.println("outcome removed");
			HashSet<PatientState> states = new HashSet<PatientState>();
			outcomePatientState = null;
			states.add(outcomePatientState);
		
			pp.setStates(states);	
			programModified = true;
		}

		//outcome has been added	
		else if(outcomePatientState == null && outcome != null) {
			System.out.println("outcome added");
			HashSet<PatientState> states = new HashSet<PatientState>();
			PatientState newState = new PatientState();
			ProgramWorkflowState pwfs = new ProgramWorkflowState();
			pwfs.setConcept(Context.getService(MdrtbService.class).getConcept(TbConcepts.TB_TX_OUTCOME));
			newState.setState(pwfs);
			states.add(newState);
			pp.setStates(states);	
			programModified = true;
		}
		
		//outcome entered previously and may have been modified now
		else if(outcomePatientState!=null && outcome !=null) {
			
		
		}
		
		
		
		
		//TX OUTCOME
		//PATIENT GROUP
		//PATIENT DEATH AND CAUSE OF DEATH
*/
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
	
	@ModelAttribute("mtbresults")
	public Collection<ConceptAnswer> getMtbResults() {
		return Context.getService(MdrtbService.class).getPossibleMtbResults();
	}
	
	@ModelAttribute("rifresults")
	public Collection<ConceptAnswer> getRifResults() {
		return Context.getService(MdrtbService.class).getPossibleRifResistanceResults();
	}
	
	

		
}
