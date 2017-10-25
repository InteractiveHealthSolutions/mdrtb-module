package org.openmrs.module.mdrtb.web.controller.program;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.Program;
import org.openmrs.ProgramWorkflowState;
import org.openmrs.api.ProgramWorkflowService;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.exception.MdrtbAPIException;
import org.openmrs.module.mdrtb.form.HAINForm;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgramValidator;
import org.openmrs.module.mdrtb.program.TbPatientProgram;
import org.openmrs.module.mdrtb.program.TbPatientProgramValidator;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.status.VisitStatus;
import org.openmrs.module.mdrtb.status.VisitStatusCalculator;
import org.openmrs.module.mdrtb.web.controller.status.DashboardVisitStatusRenderer;
import org.openmrs.module.programlocation.PatientProgram;
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
public class ProgramController {
	
	@ModelAttribute("locations")
	public Collection<Location> getPossibleLocations() {
		return Context.getLocationService().getAllLocations(false);
	}
	
	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		
		//bind dates
		SimpleDateFormat dateFormat = Context.getDateFormat();
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true, 10));
		
		// register binders for location and program workflow state
		binder.registerCustomEditor(Location.class, new LocationEditor());
		binder.registerCustomEditor(ProgramWorkflowState.class, new ProgramWorkflowStateEditor());
		
	}

	@ModelAttribute("classificationsAccordingToPreviousDrugUse")
	public Collection<ProgramWorkflowState> getClassificationsAccordingToPreviousDrugUse() {		
		/*return Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPreviousDrugUse();*/
		ArrayList<ProgramWorkflowState> stateArray = new ArrayList<ProgramWorkflowState>();
		for(int i=0; i< 3; i++) {
			stateArray.add(null);
		}
		Set<ProgramWorkflowState> states = Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPreviousDrugUse();
		MdrtbService ms = Context.getService(MdrtbService.class);
		for(ProgramWorkflowState pws : states) {
			if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.NEW).getId().intValue()) {
				stateArray.set(0, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.PREVIOUSLY_TREATED_FIRST_LINE_DRUGS_ONLY).getId().intValue()) {
				stateArray.set(1, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.PREVIOUSLY_TREATED_SECOND_LINE_DRUGS).getId().intValue()) {
				stateArray.set(2, pws);
			}
		}
		
		return stateArray;
	}
	
	@ModelAttribute("classificationsAccordingToPreviousDrugUseDOTS")
	public Collection<ProgramWorkflowState> getDOTSClassificationsAccordingToPreviousDrugUse() {		
		/*return Context.getService(MdrtbService.class).getPossibleDOTSClassificationsAccordingToPreviousDrugUse();*/
		ArrayList<ProgramWorkflowState> stateArray = new ArrayList<ProgramWorkflowState>();
		for(int i=0; i< 3; i++) {
			stateArray.add(null);
		}
		Set<ProgramWorkflowState> states = Context.getService(MdrtbService.class).getPossibleDOTSClassificationsAccordingToPreviousDrugUse();
		MdrtbService ms = Context.getService(MdrtbService.class);
		for(ProgramWorkflowState pws : states) {
			if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.NEW).getId().intValue()) {
				stateArray.set(0, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.PREVIOUSLY_TREATED_FIRST_LINE_DRUGS_ONLY).getId().intValue()) {
				stateArray.set(1, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.PREVIOUSLY_TREATED_SECOND_LINE_DRUGS).getId().intValue()) {
				stateArray.set(2, pws);
			}
		}
		
		return stateArray;
	}
	
	@ModelAttribute("classificationsAccordingToPreviousTreatment")
	public Collection<ProgramWorkflowState> getClassificationsAccordingToPreviousTreatment() {		
		//return Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPreviousTreatment();
		ArrayList<ProgramWorkflowState> stateArray = new ArrayList<ProgramWorkflowState>();
		for(int i=0; i< 8; i++) {
			stateArray.add(null);
		}
		Set<ProgramWorkflowState> states = Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPreviousTreatment();
		MdrtbService ms = Context.getService(MdrtbService.class);
		for(ProgramWorkflowState pws : states) {
			if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.NEW).getId().intValue()) {
				stateArray.set(0, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.RELAPSE_AFTER_REGIMEN_1).getId().intValue()) {
				stateArray.set(1, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.RELAPSE_AFTER_REGIMEN_2).getId().intValue()) {
				stateArray.set(2, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.DEFAULT_AFTER_REGIMEN_1).getId().intValue()) {
				stateArray.set(3, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.DEFAULT_AFTER_REGIMEN_2).getId().intValue()) {
				stateArray.set(4, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.AFTER_FAILURE_REGIMEN_1).getId().intValue()) {
				stateArray.set(5, pws);
			}
			
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.AFTER_FAILURE_REGIMEN_2).getId().intValue()) {
				stateArray.set(6, pws);
			}
			
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.OTHER).getId().intValue()) {
				stateArray.set(7, pws);
			}
			
		}
		
		return stateArray;
	}
	
	@ModelAttribute("classificationsAccordingToPatientGroups")
	public ArrayList<ProgramWorkflowState> getClassificationsAccordingToPatientGroups() {		
		/*return Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPatientGroups();*/
		ArrayList<ProgramWorkflowState> stateArray = new ArrayList<ProgramWorkflowState>();
		for(int i=0; i< 8; i++) {
			stateArray.add(null);
		}
		Set<ProgramWorkflowState> states = Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPatientGroups();
		MdrtbService ms = Context.getService(MdrtbService.class);
		for(ProgramWorkflowState pws : states) {
			if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.NEW).getId().intValue()) {
				stateArray.set(0, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.RELAPSE_AFTER_REGIMEN_1).getId().intValue()) {
				stateArray.set(1, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.RELAPSE_AFTER_REGIMEN_2).getId().intValue()) {
				stateArray.set(2, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.DEFAULT_AFTER_REGIMEN_1).getId().intValue()) {
				stateArray.set(3, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.DEFAULT_AFTER_REGIMEN_2).getId().intValue()) {
				stateArray.set(4, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.AFTER_FAILURE_REGIMEN_1).getId().intValue()) {
				stateArray.set(5, pws);
			}
			
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.AFTER_FAILURE_REGIMEN_2).getId().intValue()) {
				stateArray.set(6, pws);
			}
			
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.OTHER).getId().intValue()) {
				stateArray.set(7, pws);
			}
			
		}
		
		return stateArray;
	}
	
	@ModelAttribute("outcomes")
	Collection<ProgramWorkflowState> getOutcomes() {		
		return Context.getService(MdrtbService.class).getPossibleMdrtbProgramOutcomes();
	}
	
	@ModelAttribute("dotsIdentifier")
	public PatientIdentifierType getDotsIdentifier() {
		
		return Context.getPatientService().getPatientIdentifierTypeByName(Context.getAdministrationService().getGlobalProperty("mdrtb.primaryPatientIdentifierType"));
	}
	
	@ModelAttribute("mdrIdentifier")
	public PatientIdentifierType getMdrIdentifier() {
		
		return Context.getPatientService().getPatientIdentifierTypeByName(Context.getAdministrationService().getGlobalProperty("mdrtb.mdrIdentifierType"));
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping("/module/mdrtb/program/showEnroll.form")
	public ModelAndView showEnrollInPrograms(@RequestParam(required = false, value = "patientId") Integer patientId,
	                                         ModelMap map) {
		
		
			Patient patient = Context.getPatientService().getPatient(patientId);
			if (patient == null) {
				throw new RuntimeException ("Show enroll called with invalid patient id " + patientId);
			}
		
			// we need to determine if this patient currently in active in an mdr-tb program to determine what fields to display
			//MdrtbPatientProgram mostRecentMdrtbProgram = Context.getService(MdrtbService.class).getMostRecentMdrtbPatientProgram(patient);
			//TbPatientProgram mostRecentTbProgram = Context.getService(MdrtbService.class).getMostRecentTbPatientProgram(patient);
			//map.put("hasActiveProgram", ((mostRecentMdrtbProgram != null && mostRecentMdrtbProgram.getActive()) || (mostRecentTbProgram != null && mostRecentTbProgram.getActive())) ? true : false);
			map.put("patientId", patientId);
			
			List<MdrtbPatientProgram> mdrtbPrograms = Context.getService(MdrtbService.class).getMdrtbPatientPrograms(patient);
			List<TbPatientProgram> tbPrograms = Context.getService(MdrtbService.class).getTbPatientPrograms(patient);
			
			map.put("hasPrograms", ((mdrtbPrograms != null && mdrtbPrograms.size()!=0) || (tbPrograms != null && tbPrograms.size() != 0)) ? true : false);
			System.out.println("Prog:"+ map.get("hasPrograms"));
			
			map.put("mdrtbPrograms", mdrtbPrograms);
			map.put("tbPrograms", tbPrograms);
			
			return new ModelAndView("/module/mdrtb/program/showEnroll", map);
			
	}

	@SuppressWarnings("unchecked")
    @RequestMapping(value = "/module/mdrtb/program/programEnroll.form", method = RequestMethod.POST)
	public ModelAndView processEnroll(@ModelAttribute("program") MdrtbPatientProgram program, BindingResult errors, 
	                                  @RequestParam(required = true, value = "patientId") Integer patientId,
	                                  SessionStatus status, HttpServletRequest request, ModelMap map) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		System.out.println("ProgramCont:processEnroll");
		Patient patient = Context.getPatientService().getPatient(patientId);
		
		if (patient == null) {
			throw new RuntimeException ("Process enroll called with invalid patient id " + patientId);
		}
		
		// set the patient
		program.setPatient(patient);
		
		// perform validation (validation needs to happen after patient is set since patient is used to pull up patient's previous programs)
		if (program != null) {
    		new MdrtbPatientProgramValidator().validate(program, errors);
    	}
		
		if (errors.hasErrors()) {
			MdrtbPatientProgram mostRecentProgram = Context.getService(MdrtbService.class).getMostRecentMdrtbPatientProgram(patient);
			map.put("hasActiveProgram", mostRecentProgram != null && mostRecentProgram.getActive() ? true : false);
			map.put("patientId", patientId);
			map.put("errors", errors);
			return new ModelAndView("/module/mdrtb/program/showEnroll", map);
		}
		
		// save the actual update
		Context.getProgramWorkflowService().savePatientProgram(program.getPatientProgram());

		// clears the command object from the session
		status.setComplete();
		map.clear();
			
		// when we enroll in a program, we want to jump immediately to the intake for this patient
		// TODO: hacky to have to create a whole new visit status here just to determine the proper link?
		// TODO: modeling visit as a status probably wasn't the best way to go on my part
		VisitStatus visitStatus = (VisitStatus) new VisitStatusCalculator(new DashboardVisitStatusRenderer()).calculate(program);
		
		return new ModelAndView("redirect:" + visitStatus.getNewIntakeVisit().getLink() + "&returnUrl=" + request.getContextPath() + "/module/mdrtb/dashboard/dashboard.form%3FpatientProgramId=" + program.getId());		
	}
	
	@RequestMapping("/module/mdrtb/program/programDelete.form")
	public ModelAndView processDelete(@RequestParam(required = true, value = "patientProgramId") Integer patientProgramId,
	                                  SessionStatus status, ModelMap map){

		MdrtbPatientProgram program = Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId);
		
		// we need to save the patient id so that we know where to redirect to
		Integer patientId = program.getPatient().getId();
		
		// now void the program
		Context.getProgramWorkflowService().voidPatientProgram(program.getPatientProgram(), "voided by mdr-tb module");

		// clear the command object
		status.setComplete();
		map.clear();
		
		return new ModelAndView("redirect:/module/mdrtb/dashboard/dashboard.form?patientId=" + patientId);
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping("/module/mdrtb/program/enrollment.form")
	public ModelAndView showEnrollment(@RequestParam(required = false, value = "patientId") Integer patientId,
									   @RequestParam(required = false, value = "idId") Integer idId,
									   @RequestParam(value="loc", required=false) String district,
										  @RequestParam(value="ob", required=false) String oblast,
										  @RequestParam(value="dateEnrolled", required=false) String dateEnrolled,
										  @RequestParam(value="patGroup", required=false) Integer patGroup,
										  @RequestParam(value="drugGroup", required=false) Integer drugGroup,
	                                         ModelMap map) {
		
		
			Patient patient = Context.getPatientService().getPatient(patientId);
			if (patient == null) {
				throw new RuntimeException ("Show enroll called with invalid patient id " + patientId);
			}
		
			// we need to determine if this patient currently in active in an mdr-tb program to determine what fields to display
			/*MdrtbPatientProgram mostRecentMdrtbProgram = Context.getService(MdrtbService.class).getMostRecentMdrtbPatientProgram(patient);
			TbPatientProgram mostRecentTbProgram = Context.getService(MdrtbService.class).getMostRecentTbPatientProgram(patient);
			map.put("hasActiveProgram", ((mostRecentMdrtbProgram != null && mostRecentMdrtbProgram.getActive()) || (mostRecentTbProgram != null && mostRecentTbProgram.getActive())) ? true : false);
			map.put("patientId", patientId);
			map.put("idId", idId);
			return new ModelAndView("/module/mdrtb/program/enrollment", map);*/
			
			List<MdrtbPatientProgram> mdrtbPrograms = Context.getService(MdrtbService.class).getMdrtbPatientPrograms(patient);
			List<TbPatientProgram> tbPrograms = Context.getService(MdrtbService.class).getTbPatientPrograms(patient);
			map.put("patientId", patientId);
			map.put("hasPrograms", ((mdrtbPrograms != null && mdrtbPrograms.size()!=0) || (tbPrograms != null && tbPrograms.size() != 0)) ? true : false);
			
			
			map.put("mdrtbPrograms", mdrtbPrograms);
			map.put("tbPrograms", tbPrograms);
			
			map.put("unassignedMdrIdentifiers",getUnassignedMdrIdentifiers(patient));
			map.put("unassignedDotsIdentifiers",getUnassignedDotsIdentifiers(patient));
			map.put("idId",idId);
			
			List<Oblast> oblasts;
	        List<Facility> facilities;
	        List<District> districts;
	        
	        String prefix = "";
	        
	        if(oblast==null)
	        {
	        	if(idId!=null) {
	        		Oblast locOb = null;// = new Oblast();
	        		District locDist = null; //= new District();
	        		Facility locFac = null;// = new Facility();
	        		PatientIdentifier pi = Context.getService(MdrtbService.class).getPatientIdentifierById(idId);
	        		if(pi!=null) {
	        			prefix = pi.getIdentifier().substring(0,2);
	        			prefix = "(" + prefix + ")";
	        			Location idLoc = null;
	        			List<Location> locList = Context.getLocationService().getAllLocations(false);
	        			for(Location l : locList) {
	        				if(l.getName().trim().endsWith(prefix)) {
	        					idLoc = l;
	        					break;
	        				}
	        			}
	        			
	        			if(idLoc!=null) {
	        				String obName = idLoc.getStateProvince();
	        				List<Oblast> obList = Context.getService(MdrtbService.class).getOblasts();
	        				for(Oblast o : obList) {
	        					if(obName!=null && o.getName()!=null && o.getName().equals(obName)) {
	        						locOb = o;
	        						break;
	        					}
	        				}
	        				
	        				if(locOb!=null && idLoc.getCountyDistrict()!=null) {
	        					List<District> distList = Context.getService(MdrtbService.class).getDistricts(locOb.getId());
	        					for(District d : distList) {
	        						if(idLoc.getCountyDistrict().equals(d.getName())) {
	        							locDist = d;
	        							break;
	        						}
	        					}
	        				}
	        				
	        				if(locDist!=null && idLoc.getRegion()!=null) {
	        					List<Facility> facList = Context.getService(MdrtbService.class).getFacilities(locDist.getId());
	        					for(Facility f  : facList) {
	        						if(idLoc.getRegion().equals(f.getName())) {
	        							locFac = f;
	        							break;
	        						}
	        					}
	        				}
	        				
	        				
	        				
	        			}
	        		}
	        		
	        		if(locOb!=null) {
	        			oblasts = new ArrayList<Oblast>();
	        			oblasts.add(locOb);
	        			map.addAttribute("oblasts", oblasts);
	        			
	        			if(locDist!=null) {
		        			districts= new ArrayList<District>();
		        			districts.add(locDist);
		        			map.addAttribute("districts", districts);
		        			
		        			if(locFac!=null) {
		        				facilities = new ArrayList<Facility>();
		        				facilities.add(locFac);
		        				map.addAttribute("facilities", facilities);
		        			}
		        		}
	        			
	        			else {
	        				map.addAttribute("districts", Context.getService(MdrtbService.class).getDistrict(locOb.getId()));
	        			}
	        		}
	        		
	        		else {
	        			oblasts = Context.getService(MdrtbService.class).getOblasts();
		        		map.addAttribute("oblasts", oblasts);
	        		}

	        	}
	        	
	        	else {
	        		oblasts = Context.getService(MdrtbService.class).getOblasts();
	        		map.addAttribute("oblasts", oblasts);
	        	}
	        	
	        }
	        
	       
	        else if(district==null)
	        { 
	        	oblasts = Context.getService(MdrtbService.class).getOblasts();
	        	districts= Context.getService(MdrtbService.class).getRegDistricts(Integer.parseInt(oblast));
	        	map.addAttribute("oblastSelected", oblast);
	            map.addAttribute("oblasts", oblasts);
	            map.addAttribute("districts", districts);
	            
	        }
	        else
	        {
	        	oblasts = Context.getService(MdrtbService.class).getOblasts();
	        	districts= Context.getService(MdrtbService.class).getRegDistricts(Integer.parseInt(oblast));
	        	facilities = Context.getService(MdrtbService.class).getRegFacilities(Integer.parseInt(district));
	            map.addAttribute("oblastSelected", oblast);
	            map.addAttribute("oblasts", oblasts);
	            map.addAttribute("districts", districts);
	            map.addAttribute("districtSelected", district);
	            map.addAttribute("facilities", facilities);
	        }

	        map.addAttribute("dateEnrolled", dateEnrolled);
        	map.addAttribute("patientGroup", patGroup);
        	map.addAttribute("previousDrugUse", drugGroup);
        	
			return new ModelAndView("/module/mdrtb/program/enrollment", map);
			
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping(value = "/module/mdrtb/program/firstEnrollment.form", method = RequestMethod.POST)
	public ModelAndView processFirstEnroll(@ModelAttribute("program") TbPatientProgram program, BindingResult errors, 
										@RequestParam(required = true, value = "oblast") String oblastId,
										@RequestParam(required = true, value = "district") String districtId,
										@RequestParam(required = false, value = "facility") String facilityId,
										@RequestParam(required = true, value = "patientId") Integer patientId,
										@RequestParam(required = false, value="idId") Integer idId,
		
										SessionStatus status, HttpServletRequest request, ModelMap map) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		System.out.println("ProgramCont:processEnroll");
		Patient patient = Context.getPatientService().getPatient(patientId);
		
		if (patient == null) {
			throw new RuntimeException ("Process enroll called with invalid patient id " + patientId);
		}
		
		Location location=null;
    	
    	
    	System.out.println("PARAMS:\nob: " + oblastId + "\ndist: " + districtId + "\nfac: " + facilityId);
    	
    	if(facilityId!=null && facilityId.length()!=0)
    		location = Context.getService(MdrtbService.class).getLocation(Integer.parseInt(oblastId),Integer.parseInt(districtId),Integer.parseInt(facilityId));
    	else
    		location = Context.getService(MdrtbService.class).getLocation(Integer.parseInt(oblastId),Integer.parseInt(districtId),null);
    	
    	
    	if(location == null) { // && locations!=null && (locations.size()==0 || locations.size()>1)) {
    		throw new MdrtbAPIException("Invalid Hierarchy Set selected");
    	}
    	
    	
		if(program.getLocation()==null || !location.equals(program.getLocation())) {
			System.out.println("setting loc");
			program.setLocation(location);
		}
		
		// set the patient
		program.setPatient(patient);
		
		// perform validation (validation needs to happen after patient is set since patient is used to pull up patient's previous programs)
		if (program != null) {
    		new TbPatientProgramValidator().validate(program, errors);
    	}
		
		if (errors.hasErrors()) {
			TbPatientProgram mostRecentProgram = Context.getService(MdrtbService.class).getMostRecentTbPatientProgram(patient);
			map.put("hasActiveProgram", mostRecentProgram != null && mostRecentProgram.getActive() ? true : false);
			map.put("patientId", patientId);
			map.put("errors", errors);
			return new ModelAndView("/module/mdrtb/program/enrollment", map);
		}
		
		// save the actual update
		Context.getProgramWorkflowService().savePatientProgram(program.getPatientProgram());
		Context.getService(MdrtbService.class).addIdentifierToProgram(idId, program.getPatientProgram().getPatientProgramId());
		// clears the command object from the session
		status.setComplete();
		map.clear();
			
		// when we enroll in a program, we want to jump immediately to the intake for this patient
		// TODO: hacky to have to create a whole new visit status here just to determine the proper link?
		// TODO: modeling visit as a status probably wasn't the best way to go on my part
	   /* VisitStatus visitStatus = (VisitStatus) new VisitStatusCalculator(new DashboardVisitStatusRenderer()).calculateTb(program);
		
		return new ModelAndView("redirect:" + visitStatus.getNewIntakeVisit().getLink() + "&returnUrl=" + request.getContextPath() + "/module/mdrtb/dashboard/dashboard.form%3FpatientProgramId=" + program.getId());*/
		
		return new ModelAndView("redirect:/module/mdrtb/form/tb03.form?patientProgramId=" + program.getId() + "&encounterId=-1");
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping(value="/module/mdrtb/program/otherEnrollment.form", method = RequestMethod.GET)
	public ModelAndView showOtherEnrollment(@RequestParam(required = true, value = "patientId") Integer patientId,
									   @RequestParam(required = true, value = "type") String type,
									   @RequestParam(required = false, value = "mdrLocation") Integer locationId,
									   @RequestParam(value="loc", required=false) String district,
									   @RequestParam(value="ob", required=false) String oblast,
										  @RequestParam(value="dateEnrolled", required=false) String dateEnrolled,
										  @RequestParam(value="patGroup", required=false) Integer patGroup,
										  @RequestParam(value="drugGroup", required=false) Integer drugGroup,
										  @RequestParam(value="idSelected", required=false) Integer idSelected,
									   @RequestParam(required = false, value = "programStartDate") String programStartDate,
									  /* @RequestParam(required = false, value = "previousProgramId") Integer previousProgramId,*/
	                                         ModelMap map) {

		    System.out.println("SOE: " + patientId + ":" + type);
			Patient patient = Context.getPatientService().getPatient(patientId);
			if (patient == null) {
				System.out.println("bad patient");
				throw new RuntimeException ("Show enroll called with invalid patient id " + patientId);
			}
		
			
			if(type==null || type.length()==0) {
				System.out.println("bad type");
				throw new RuntimeException ("No program type specified");
			}
			
			map.put("patientId", patientId);
			
			map.put("type", type);
			if(locationId!=null) {
				map.put("initLocation", Context.getLocationService().getLocation(locationId));
			}
			Date parsedDate = null;
			if(programStartDate!=null) {
				String[] splits = programStartDate.split("\\.");
				if(splits==null || splits.length!=3) {
					parsedDate = null;
				}
				
				else {
					int year = Integer.parseInt(splits[2]);
					int month = Integer.parseInt(splits[1]) - 1;
					int date = Integer.parseInt(splits[0]);
					
					GregorianCalendar gc = new GregorianCalendar();
					gc.set(gc.YEAR, year);
					gc.set(gc.MONTH, month);
					gc.set(gc.DATE, date);
					parsedDate = gc.getTime();
				}
				
				/*SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
				try {
					parsedDate = formatter.parse(programStartDate);
				} catch (ParseException e) {
					parsedDate = null;
					}*/
				System.out.println(parsedDate);
				map.put("programStartDate", parsedDate);
				
			
			}
			
			System.out.println("OBLAST: " + oblast);
			List<Oblast> oblasts;
	        List<Facility> facilities;
	        List<District> districts;
	        
	        if(oblast==null)
	        {
	        	oblasts = Context.getService(MdrtbService.class).getOblasts();
	        	map.addAttribute("oblasts", oblasts);
	        	
	        }
	        
	       
	        else if(district==null)
	        { 
	        	oblasts = Context.getService(MdrtbService.class).getOblasts();
	        	districts= Context.getService(MdrtbService.class).getRegDistricts(Integer.parseInt(oblast));
	        	map.addAttribute("oblastSelected", oblast);
	            map.addAttribute("oblasts", oblasts);
	            map.addAttribute("districts", districts);
	            
	        }
	        else
	        {
	        	oblasts = Context.getService(MdrtbService.class).getOblasts();
	        	districts= Context.getService(MdrtbService.class).getRegDistricts(Integer.parseInt(oblast));
	        	facilities = Context.getService(MdrtbService.class).getRegFacilities(Integer.parseInt(district));
	            map.addAttribute("oblastSelected", oblast);
	            map.addAttribute("oblasts", oblasts);
	            map.addAttribute("districts", districts);
	            map.addAttribute("districtSelected", district);
	            map.addAttribute("facilities", facilities);
	        }

	        map.addAttribute("dateEnrolled", dateEnrolled);
        	map.addAttribute("patientGroup", patGroup);
        	map.addAttribute("previousDrugUse", drugGroup);
        	map.addAttribute("idSelected", idSelected);
		/*	if(previousProgramId!=null) {
				map.put("previousProgramId", previousProgramId);
			}*/
			
			return new ModelAndView("/module/mdrtb/program/otherEnrollment", map);
			
	}
	
	
	
	@SuppressWarnings("unchecked")
    @RequestMapping(value = "/module/mdrtb/program/otherEnrollmentMdrtb.form", method = RequestMethod.POST)
	public ModelAndView processOtherEnrollMdrtb(@ModelAttribute("program") MdrtbPatientProgram program, BindingResult errors, 
			@RequestParam(required = true, value = "patientId") Integer patientId,
			@RequestParam(required = true, value = "oblast") String oblastId,
			@RequestParam(required = true, value = "district") String districtId,
			@RequestParam(required = false, value = "facility") String facilityId,
            
            @RequestParam(required = true, value = "identifierValue") String identifierValue,
	                                  SessionStatus status, HttpServletRequest request, ModelMap map) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		System.out.println("ProgramCont:processEnroll -= Other MDRTB");
		Patient patient = Context.getPatientService().getPatient(patientId);
		
		if (patient == null) {
			throw new RuntimeException ("Process enroll called with invalid patient id " + patientId);
		}
		
		Location location=null;
    	
    	
    	System.out.println("PARAMS:\nob: " + oblastId + "\ndist: " + districtId + "\nfac: " + facilityId);
    	
    	if(facilityId!=null && facilityId.length()!=0)
    		location = Context.getService(MdrtbService.class).getLocation(Integer.parseInt(oblastId),Integer.parseInt(districtId),Integer.parseInt(facilityId));
    	else
    		location = Context.getService(MdrtbService.class).getLocation(Integer.parseInt(oblastId),Integer.parseInt(districtId),null);
    	
    	
    	if(location == null) { // && locations!=null && (locations.size()==0 || locations.size()>1)) {
    		throw new MdrtbAPIException("Invalid Hierarchy Set selected");
    	}
    	
    	
		if(program.getLocation()==null || !location.equals(program.getLocation())) {
			System.out.println("setting loc");
			program.setLocation(location);
		}
		
		PatientIdentifier identifier = new PatientIdentifier(identifierValue, getMdrIdentifier(), program.getLocation());

		patient.addIdentifier(identifier);	
		
		Context.getPatientService().savePatient(patient);
		
		Integer idId = null;
		Set<PatientIdentifier> identifiers = patient.getIdentifiers();
		Iterator<PatientIdentifier> idIterator = identifiers.iterator();
		PatientIdentifier temp = null;
		while(idIterator.hasNext()) {
			temp = idIterator.next();
			if(temp.getIdentifier().equals(identifierValue)) {
				idId = temp.getId();
				break;
			}
		}
		
		// set the patient
		program.setPatient(patient);
		
		// perform validation (validation needs to happen after patient is set since patient is used to pull up patient's previous programs)
		if (program != null) {
    		new MdrtbPatientProgramValidator().validate(program, errors);
    	}
		
		if (errors.hasErrors()) {
			System.out.println("errors");
			MdrtbPatientProgram mostRecentProgram = Context.getService(MdrtbService.class).getMostRecentMdrtbPatientProgram(patient);
			map.put("hasActiveProgram", mostRecentProgram != null && mostRecentProgram.getActive() ? true : false);
			map.put("patientId", patientId);
			map.put("errors", errors);
			map.put("type", "mdr");
			for(Object err  : errors.getAllErrors())
				System.out.println(err.toString());
			return new ModelAndView("/module/mdrtb/program/otherEnrollmentMdrtb.form?patientId=" + patientId + "&type=mdr", map);
		}
		
		
		
		// save the actual update
		Context.getProgramWorkflowService().savePatientProgram(program.getPatientProgram());
		Context.getService(MdrtbService.class).addIdentifierToProgram(idId, program.getPatientProgram().getPatientProgramId());
		// clears the command object from the session
		status.setComplete();
		map.clear();
			
		// when we enroll in a program, we want to jump immediately to the intake for this patient
		// TODO: hacky to have to create a whole new visit status here just to determine the proper link?
		// TODO: modeling visit as a status probably wasn't the best way to go on my part
	   /* VisitStatus visitStatus = (VisitStatus) new VisitStatusCalculator(new DashboardVisitStatusRenderer()).calculateTb(program);
		
		return new ModelAndView("redirect:" + visitStatus.getNewIntakeVisit().getLink() + "&returnUrl=" + request.getContextPath() + "/module/mdrtb/dashboard/dashboard.form%3FpatientProgramId=" + program.getId());*/
		
		
		
		return new ModelAndView("redirect:/module/mdrtb/form/tb03u.form?patientProgramId=" + program.getId() + "&encounterId=-1");
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping(value = "/module/mdrtb/program/otherEnrollmentTb.form", method = RequestMethod.POST)
	public ModelAndView processOtherEnrollTb(@ModelAttribute("program") TbPatientProgram program, BindingResult errors, 
	                                  @RequestParam(required = true, value = "patientId") Integer patientId,
	                                  @RequestParam(required = true, value = "oblast") String oblastId,
	                                  @RequestParam(required = true, value = "district") String districtId,
	                      			  @RequestParam(required = false, value = "facility") String facilityId,
	                                  @RequestParam(required = true, value = "identifierValue") String identifierValue,
	                                  SessionStatus status, HttpServletRequest request, ModelMap map) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		System.out.println("ProgramCont:processEnroll -= OtherTB");
		Patient patient = Context.getPatientService().getPatient(patientId);
		
		if (patient == null) {
			throw new RuntimeException ("Process enroll called with invalid patient id " + patientId);
		}
		
		Location location=null;
    	
    	
    	System.out.println("PARAMS:\nob: " + oblastId + "\ndist: " + districtId + "\nfac: " + facilityId);
    	
    	if(facilityId!=null && facilityId.length()!=0)
    		location = Context.getService(MdrtbService.class).getLocation(Integer.parseInt(oblastId),Integer.parseInt(districtId),Integer.parseInt(facilityId));
    	else
    		location = Context.getService(MdrtbService.class).getLocation(Integer.parseInt(oblastId),Integer.parseInt(districtId),null);
    	
    	
    	if(location == null) { // && locations!=null && (locations.size()==0 || locations.size()>1)) {
    		throw new MdrtbAPIException("Invalid Hierarchy Set selected");
    	}
    	
    	
		if(program.getLocation()==null || !location.equals(program.getLocation())) {
			System.out.println("setting loc");
			program.setLocation(location);
		}
		
		PatientIdentifier identifier = new PatientIdentifier(identifierValue, getDotsIdentifier(), program.getLocation());

		patient.addIdentifier(identifier);	
		
		Context.getPatientService().savePatient(patient);
		
		Integer idId = null;
		Set<PatientIdentifier> identifiers = patient.getIdentifiers();
		Iterator<PatientIdentifier> idIterator = identifiers.iterator();
		PatientIdentifier temp = null;
		while(idIterator.hasNext()) {
			temp = idIterator.next();
			if(temp.getIdentifier().equals(identifierValue)) {
				idId = temp.getId();
				break;
			}
		}
		
		// set the patient
		program.setPatient(patient);
		
		// perform validation (validation needs to happen after patient is set since patient is used to pull up patient's previous programs)
		if (program != null) {
    		new TbPatientProgramValidator().validate(program, errors);
    	}
		
		if (errors.hasErrors()) {
			TbPatientProgram mostRecentProgram = Context.getService(MdrtbService.class).getMostRecentTbPatientProgram(patient);
			map.put("hasActiveProgram", mostRecentProgram != null && mostRecentProgram.getActive() ? true : false);
			map.put("patientId", patientId);
			map.put("errors", errors);
			return new ModelAndView("/module/mdrtb/program/enrollment", map);
		}
		
		
		
		// save the actual update
		Context.getProgramWorkflowService().savePatientProgram(program.getPatientProgram());
		Context.getService(MdrtbService.class).addIdentifierToProgram(idId, program.getPatientProgram().getPatientProgramId());
		// clears the command object from the session
		status.setComplete();
		map.clear();
			
		// when we enroll in a program, we want to jump immediately to the intake for this patient
		// TODO: hacky to have to create a whole new visit status here just to determine the proper link?
		// TODO: modeling visit as a status probably wasn't the best way to go on my part
	   /* VisitStatus visitStatus = (VisitStatus) new VisitStatusCalculator(new DashboardVisitStatusRenderer()).calculateTb(program);
		
		return new ModelAndView("redirect:" + visitStatus.getNewIntakeVisit().getLink() + "&returnUrl=" + request.getContextPath() + "/module/mdrtb/dashboard/dashboard.form%3FpatientProgramId=" + program.getId());*/
		
		return new ModelAndView("redirect:/module/mdrtb/form/tb03.form?patientProgramId=" + program.getId() + "&encounterId=-1");
	}
	
	
	public List<PatientIdentifier> getUnassignedDotsIdentifiers(Patient p) {
		List<PatientIdentifier> ids = null;
		List<PatientIdentifier> ret = new ArrayList<PatientIdentifier>();
		PatientIdentifierType pit = Context.getPatientService().getPatientIdentifierTypeByName(Context.getAdministrationService().getGlobalProperty("mdrtb.primaryPatientIdentifierType"));
		List<PatientIdentifierType> typeList = new ArrayList<PatientIdentifierType>();
		typeList.add(pit);
		List<Patient> patList = new ArrayList<Patient>();
		patList.add(p);
 		
		ids = Context.getPatientService().getPatientIdentifiers(null, typeList, null, patList, null);
		for(PatientIdentifier pi : ids) {
			if(!isIdentifierAssigned(pi, false))
				ret.add(pi);
		}
		
		
		return ret;
	}
	
	
	public List<PatientIdentifier> getUnassignedMdrIdentifiers(Patient p) {
		List<PatientIdentifier> ids = null;
		List<PatientIdentifier> ret = new ArrayList<PatientIdentifier>();
		PatientIdentifierType pit = Context.getPatientService().getPatientIdentifierTypeByName(Context.getAdministrationService().getGlobalProperty("mdrtb.mdrIdentifierType"));
		
		List<PatientIdentifierType> typeList = new ArrayList<PatientIdentifierType>();
		typeList.add(pit);
		List<Patient> patList = new ArrayList<Patient>();
		patList.add(p);
 		
		ids = Context.getPatientService().getPatientIdentifiers(null, typeList, null, patList, null);
		for(PatientIdentifier pi : ids) {
			if(!isIdentifierAssigned(pi, true))
				ret.add(pi);
		}
		
		
		return ids;
	}
	
	public Boolean isIdentifierAssigned(PatientIdentifier pi, boolean mdr) {
		
		Collection<org.openmrs.PatientProgram> ppList = Context.getProgramWorkflowService().getPatientPrograms(pi.getPatient());
		PatientIdentifier temp = null;
		for(org.openmrs.PatientProgram pp : ppList) {
			/*if(mdr) {*/
				temp = Context.getService(MdrtbService.class).getGenPatientProgramIdentifier(Context.getProgramWorkflowService().getPatientProgram(pp.getId()));
				/*if(temp!=null){
					System.out.println("temp ID=" + temp.getId().intValue());
				}
				else {
					System.out.println("temp ID=null");
				}*/
				//System.out.println("PI:" + pi.getId().intValue());
				
				if(temp!=null && temp.getId().intValue()==pi.getId().intValue())
					return true;
			/*}
			
			else {
				temp = Context.getService(MdrtbService.class).getTbPatientProgram(pp.getId()).getPatientIdentifier();
				if(temp!=null && temp.getId().intValue()==pi.getId().intValue())
					return false;
			}*/
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping("/module/mdrtb/program/addId.form")
	public ModelAndView addIdToProgram(@RequestParam(required = true, value = "ppid") Integer patientProgramId,
											 @RequestParam(required = true, value = "idToAdd") Integer patientIdentifierId,
	                                         ModelMap map) {
		
		
			
			Context.getService(MdrtbService.class).addIdentifierToProgram(patientIdentifierId, patientProgramId);
			
			//map.put("patientId", Context.getProgramWorkflowService().getPatientProgram(patientProgramId).getPatient().getId());
			
			
			return new ModelAndView("redirect:/module/mdrtb/program/enrollment.form?patientProgramId="+ patientProgramId +"&patientId="+Context.getProgramWorkflowService().getPatientProgram(patientProgramId).getPatient().getId());
			
	}
	
}
	
	

