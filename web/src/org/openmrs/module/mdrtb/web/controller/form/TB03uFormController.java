package org.openmrs.module.mdrtb.web.controller.form;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientState;
import org.openmrs.Person;
import org.openmrs.ProgramWorkflow;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.service.MdrtbService;

import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.form.TB03uForm;

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
import org.openmrs.ProgramWorkflowState;

@Controller
@RequestMapping("/module/mdrtb/form/tb03u.form")
public class TB03uFormController {
	
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
	
	@ModelAttribute("tb03u")
	public TB03uForm getTB03uForm(@RequestParam(required = true, value = "encounterId") Integer encounterId,
	                            @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		// if no form is specified, create a new one
		if (encounterId == -1) {
			MdrtbPatientProgram tbProgram = Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId);
			
			TB03uForm form = new TB03uForm(tbProgram.getPatient());
			
			// prepopulate the intake form with any program information
			form.setEncounterDatetime(tbProgram.getDateEnrolled());
			form.setLocation(tbProgram.getLocation());
				
			return form;
		}
		else {
			return new TB03uForm(Context.getEncounterService().getEncounter(encounterId));
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showTB03uForm() {
		ModelMap map = new ModelMap();
		return new ModelAndView("/module/mdrtb/form/tb03u", map);	
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST)
	public ModelAndView processTB03Form (@ModelAttribute("tb03u") TB03uForm tb03u, BindingResult errors, 
	                                       @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId,
	                                       @RequestParam(required = false, value = "returnUrl") String returnUrl,
	                                       SessionStatus status, HttpServletRequest request, ModelMap map) {
		
		
		// perform validation and check for errors
		/*if (tb03 != null) {
    		new SimpleFormValidator().validate(tb03, errors);
    	}*/
		
		/*if (errors.hasErrors()) {
			map.put("errors", errors);
			return new ModelAndView("/module/mdrtb/form/intake", map);
		}*/
		
		// save the actual update
		Context.getEncounterService().saveEncounter(tb03u.getEncounter());
		
		//handle changes in workflows
				Concept outcome = tb03u.getTreatmentOutcome();
				Concept group = tb03u.getRegistrationGroup();
				
				MdrtbPatientProgram tpp = getMdrtbPatientProgram(patientProgramId);
				
				if(outcome!=null) {
					ProgramWorkflow outcomeFlow = Context.getProgramWorkflowService().getWorkflow(tpp.getPatientProgram().getProgram(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TB_TX_OUTCOME).getName().toString()); 
					ProgramWorkflowState outcomeState = Context.getProgramWorkflowService().getState(outcomeFlow, outcome.getName().toString());
					tpp.setOutcome(outcomeState);
					tpp.setDateCompleted(tb03u.getTreatmentOutcomeDate());
				}
				
				else {
					tpp.setDateCompleted(null);
				}
				
				
				ProgramWorkflow groupFlow = Context.getProgramWorkflowService().getWorkflow(tpp.getPatientProgram().getProgram(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAT_4_CLASSIFICATION_PREVIOUS_TX).getName().toString()); 
				ProgramWorkflowState groupState = Context.getProgramWorkflowService().getState(groupFlow, group.getName().toString());
				tpp.setClassificationAccordingToPreviousTreatment(groupState);
				
				Context.getProgramWorkflowService().savePatientProgram(tpp.getPatientProgram());

				//TX OUTCOME
				//PATIENT GROUP
				//PATIENT DEATH AND CAUSE OF DEATH
				if(outcome!=null && outcome.getId()==Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.outcome.died.conceptId")));
				{
					Patient patient = tpp.getPatient();
					if(!patient.getDead())
						patient.setDead(new Boolean(true));
					
					Context.getPatientService().savePatient(patient);
					//	patient.setC
					
				}		// clears the command object from the session
		status.setComplete();
		
		/*if(programModified) {
			System.out.println("saving program");
			Context.getProgramWorkflowService().savePatientProgram(pp);
		}*/
		
		map.clear();

		// if there is no return URL, default to the patient dashboard
		if (returnUrl == null || StringUtils.isEmpty(returnUrl)) {
			returnUrl = request.getContextPath() + "/module/mdrtb/dashboard/dashboard.form";
		}
		
		returnUrl = MdrtbWebUtil.appendParameters(returnUrl, Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId).getPatient().getId(), patientProgramId);
		
		return new ModelAndView(new RedirectView(returnUrl));
	}
	
	@ModelAttribute("patientProgramId")
	public Integer getPatientProgramId(@RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) {
		return patientProgramId;
	}
	
	
	@ModelAttribute("tbProgram")
	public MdrtbPatientProgram getMdrtbPatientProgram(@RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) {
		return Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId);
	}
	
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
	
	@ModelAttribute("sites")
	public Collection<ConceptAnswer> getAnatomicalSites() {
		return Context.getService(MdrtbService.class).getPossibleAnatomicalSites();
	}
	
	@ModelAttribute("iptxsites")
	public Collection<ConceptAnswer> getPossibleIPTreatmentSites() {
		return Context.getService(MdrtbService.class).getPossibleIPTreatmentSites();
	}
	
	@ModelAttribute("cptxsites")
	public Collection<ConceptAnswer> getPossibleCPTreatmentSites() {
		return Context.getService(MdrtbService.class).getPossibleCPTreatmentSites();
	}
	
	
	@ModelAttribute("categories")
	public Collection<ConceptAnswer> getPossiblePatientCategories() {
		return Context.getService(MdrtbService.class).getPossibleRegimens();
	}
	
	@ModelAttribute("groups")
	public Set<ProgramWorkflowState> getPossiblePatientGroups() {
		return Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPatientGroups();
	}
	
	@ModelAttribute("hivstatuses")
	public Collection<ConceptAnswer> getPossibleHIVStatuses() {
		return Context.getService(MdrtbService.class).getPossibleHIVStatuses();
	}
	
	@ModelAttribute("resistancetypes")
	public Collection<ConceptAnswer> getPossibleResistanceTypes() {
		
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.RESISTANCE_TYPE);
	}
	
	@ModelAttribute("outcomes")
	public Set<ProgramWorkflowState> getPossibleTreatmentOutcomes() {
		return Context.getService(MdrtbService.class).getPossibleMdrtbProgramOutcomes();
	}
	
	@ModelAttribute("mdrstatuses")
	public Collection<ConceptAnswer> getPossibleMDRStatuses() {
		
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(MdrtbConcepts.MDR_STATUS);
	}
	
	@ModelAttribute("txlocations")
	public Collection<ConceptAnswer> getPossibleTxLocations() {
		
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.TX_LOCATION);
	}
	
	@ModelAttribute("basesfordiagnosis")
	public Collection<ConceptAnswer> getPossibleBasesForDiagnosis() {
	
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.BASIS_FOR_TB_DIAGNOSIS);
	}

	@ModelAttribute("hivstatuses")
	public Collection<ConceptAnswer> getPossibleHivStatuses() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.RESULT_OF_HIV_TEST);
	}
	
	@ModelAttribute("relapses")
	public Collection<ConceptAnswer> getPossibleRelapses() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.RELAPSED);
	}
		
}
