package org.openmrs.module.mdrtb.web.controller.program;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
		return Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPreviousDrugUse();
	}
	
	@ModelAttribute("classificationsAccordingToPreviousTreatment")
	public Collection<ProgramWorkflowState> getClassificationsAccordingToPreviousTreatment() {		
		return Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPreviousTreatment();
	}
	
	@ModelAttribute("classificationsAccordingToPatientGroups")
	public Collection<ProgramWorkflowState> getClassificationsAccordingToPatientGroups() {		
		System.out.println("called");
		return Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPatientGroups();
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
			System.out.println("Prog:"+ map.get("hasPrograms"));
			
			map.put("mdrtbPrograms", mdrtbPrograms);
			map.put("tbPrograms", tbPrograms);
			
			map.put("unassignedMdrIdentifiers",getUnassignedMdrIdentifiers(patient));
			map.put("unassignedDotsIdentifiers",getUnassignedDotsIdentifiers(patient));
			
			return new ModelAndView("/module/mdrtb/program/enrollment", map);
			
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping(value = "/module/mdrtb/program/firstEnrollment.form", method = RequestMethod.POST)
	public ModelAndView processFirstEnroll(@ModelAttribute("program") TbPatientProgram program, BindingResult errors, 
	                                  @RequestParam(required = true, value = "patientId") Integer patientId,
	                                  @RequestParam(required = false, value="idId") Integer idId,
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
	                                         ModelMap map) {

			Patient patient = Context.getPatientService().getPatient(patientId);
			if (patient == null) {
				throw new RuntimeException ("Show enroll called with invalid patient id " + patientId);
			}
		
			
			if(type==null || type.length()==0) {
				throw new RuntimeException ("No program type specified");
			}
			
			map.put("patientId", patientId);
			
			map.put("type", type);
			
			return new ModelAndView("/module/mdrtb/program/otherEnrollment", map);
			
	}
	
	
	
	@SuppressWarnings("unchecked")
    @RequestMapping(value = "/module/mdrtb/program/otherEnrollmentMdrtb.form", method = RequestMethod.POST)
	public ModelAndView processOtherEnrollMdrtb(@ModelAttribute("program") MdrtbPatientProgram program, BindingResult errors, 
			@RequestParam(required = true, value = "patientId") Integer patientId,
           
            
            @RequestParam(required = true, value = "identifierValue") String identifierValue,
	                                  SessionStatus status, HttpServletRequest request, ModelMap map) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		System.out.println("ProgramCont:processEnroll -= Other MDRTB");
		Patient patient = Context.getPatientService().getPatient(patientId);
		
		if (patient == null) {
			throw new RuntimeException ("Process enroll called with invalid patient id " + patientId);
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
			MdrtbPatientProgram mostRecentProgram = Context.getService(MdrtbService.class).getMostRecentMdrtbPatientProgram(patient);
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
		
		
		
		return new ModelAndView("redirect:/module/mdrtb/form/tb03u.form?patientProgramId=" + program.getId() + "&encounterId=-1");
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping(value = "/module/mdrtb/program/otherEnrollmentTb.form", method = RequestMethod.POST)
	public ModelAndView processOtherEnrollTb(@ModelAttribute("program") TbPatientProgram program, BindingResult errors, 
	                                  @RequestParam(required = true, value = "patientId") Integer patientId,
	                                  @RequestParam(required = true, value = "identifierValue") String identifierValue,
	                                  SessionStatus status, HttpServletRequest request, ModelMap map) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		System.out.println("ProgramCont:processEnroll -= OtherTB");
		Patient patient = Context.getPatientService().getPatient(patientId);
		
		if (patient == null) {
			throw new RuntimeException ("Process enroll called with invalid patient id " + patientId);
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
				if(temp!=null){
					System.out.println("temp ID=" + temp.getId().intValue());
				}
				else {
					System.out.println("temp ID=null");
				}
				System.out.println("PI:" + pi.getId().intValue());
				
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
	
	

