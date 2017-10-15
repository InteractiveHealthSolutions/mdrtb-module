package org.openmrs.module.mdrtb.web.controller.form;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.service.MdrtbService;

import org.openmrs.module.mdrtb.exception.MdrtbAPIException;
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
	                            @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId/*,
	                            @RequestParam(required = false, value = "previousProgramId") Integer previousProgramId*/) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		// if no form is specified, create a new one
		if (encounterId == -1) {
			MdrtbPatientProgram tbProgram = Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId);
			
			TB03uForm form = new TB03uForm(tbProgram.getPatient());
			
			// prepopulate the intake form with any program information
			form.setEncounterDatetime(tbProgram.getDateEnrolled());
			form.setLocation(tbProgram.getLocation());
			form.setPatProgId(patientProgramId);
			
			if(tbProgram.getClassificationAccordingToPreviousTreatment()!=null)
				form.setRegistrationGroup(tbProgram.getClassificationAccordingToPreviousTreatment().getConcept());
			if(tbProgram.getClassificationAccordingToPreviousDrugUse()!=null)
				form.setRegistrationGroupByDrug(tbProgram.getClassificationAccordingToPreviousDrugUse().getConcept());
			/*if(previousProgramId!=null)
				form.setPreviousProgramId(previousProgramId);*/
				
			return form;
		}
		else {
			return new TB03uForm(Context.getEncounterService().getEncounter(encounterId));
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showTB03uForm(@RequestParam(required = false, value = "returnUrl") String returnUrl,
			@RequestParam(value="loc", required=false) String district,
			@RequestParam(value="ob", required=false) String oblast,
			@RequestParam(required = true, value = "patientProgramId") Integer patientProgramId,
			  	@RequestParam(required = true, value = "encounterId") Integer encounterId,
			  	@RequestParam(required = false, value = "mode") String mode,
			  ModelMap model) {
		List<Oblast> oblasts;
        List<Facility> facilities;
        List<District> districts;
        
        if(oblast==null)
        {
        	TB03uForm tb03u = null;
        	if(encounterId!=-1) {  //we are editing an existing encounter
        		 tb03u = new TB03uForm(Context.getEncounterService().getEncounter(encounterId));
        	}
        	else {
        		try {
					tb03u = getTB03uForm(-1, patientProgramId);
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
        	
        	//TB03Form tb03 = new TB03Form(Context.getEncounterService().getEncounter(encounterId));
        	Location location  = tb03u.getLocation();
        	System.out.println("show:" + location.getDisplayString());
        	oblasts = Context.getService(MdrtbService.class).getOblasts();
        	model.addAttribute("oblasts", oblasts);
        	for(Oblast o : oblasts) {
        		if(o.getName().equals(location.getStateProvince())) {
        			System.out.println(o.getName() + " Set");
        			model.addAttribute("oblastSelected", o.getId());
        			districts = Context.getService(MdrtbService.class).getRegDistricts(o.getId());
        			model.addAttribute("districts", districts);
        			for(District d : districts) {
        				if(d.getName().equals(location.getCountyDistrict())) {
        					model.addAttribute("districtSelected", d.getId());
        					facilities = Context.getService(MdrtbService.class).getRegFacilities(d.getId());
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

        else if(district==null)
        { 
        	oblasts = Context.getService(MdrtbService.class).getOblasts();
        	districts= Context.getService(MdrtbService.class).getRegDistricts(Integer.parseInt(oblast));
        	model.addAttribute("oblastSelected", oblast);
            model.addAttribute("oblasts", oblasts);
            model.addAttribute("districts", districts);
        }
        else
        {
        	oblasts = Context.getService(MdrtbService.class).getOblasts();
        	districts= Context.getService(MdrtbService.class).getRegDistricts(Integer.parseInt(oblast));
        	facilities = Context.getService(MdrtbService.class).getRegFacilities(Integer.parseInt(district));
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
		
		return new ModelAndView("/module/mdrtb/form/tb03u", model);	
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST)
	public ModelAndView processTB03Form (@ModelAttribute("tb03u") TB03uForm tb03u, BindingResult errors, 
	                                       @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId,
	                                       @RequestParam(required = true, value = "oblast") String oblastId,
	                                       @RequestParam(required = true, value = "district") String districtId,
	                                       @RequestParam(required = false, value = "facility") String facilityId,
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
		
		Location location=null;
    	
    	
    	System.out.println("PARAMS:\nob: " + oblastId + "\ndist: " + districtId + "\nfac: " + facilityId);
    	
    	if(facilityId!=null && facilityId.length()!=0)
    		location = Context.getService(MdrtbService.class).getLocation(Integer.parseInt(oblastId),Integer.parseInt(districtId),Integer.parseInt(facilityId));
    	else
    		location = Context.getService(MdrtbService.class).getLocation(Integer.parseInt(oblastId),Integer.parseInt(districtId),null);
		
    	if(location == null) { // && locations!=null && (locations.size()==0 || locations.size()>1)) {
    		throw new MdrtbAPIException("Invalid Hierarchy Set selected");
    	}
    	
    	
		if(tb03u.getLocation()==null || !location.equals(tb03u.getLocation())) {
			System.out.println("setting loc");
			tb03u.setLocation(location);
		}
		
		// save the actual update
		Context.getEncounterService().saveEncounter(tb03u.getEncounter());
		
		//handle changes in workflows
				Concept outcome = tb03u.getTreatmentOutcome();
				Concept group = tb03u.getRegistrationGroup();
				Concept groupByDrug = tb03u.getRegistrationGroupByDrug();
				
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
				
				if(group!=null) {
					ProgramWorkflow groupFlow = Context.getProgramWorkflowService().getWorkflow(tpp.getPatientProgram().getProgram(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAT_4_CLASSIFICATION_PREVIOUS_TX).getName().getName()); 
					ProgramWorkflowState groupState = Context.getProgramWorkflowService().getState(groupFlow, group.getName().getName());
					tpp.setClassificationAccordingToPreviousTreatment(groupState);
				}
				
				if(groupByDrug!=null) {
					ProgramWorkflow groupByDrugFlow = Context.getProgramWorkflowService().getWorkflow(tpp.getPatientProgram().getProgram(), Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CAT_4_CLASSIFICATION_PREVIOUS_DRUG_USE).getName().getName()); 
					ProgramWorkflowState groupByDrugState = Context.getProgramWorkflowService().getState(groupByDrugFlow, groupByDrug.getName().getName());
					tpp.setClassificationAccordingToPreviousDrugUse(groupByDrugState);
				}
				
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
		//return Context.getService(MdrtbService.class).getPossibleRegimens();
		ArrayList<ConceptAnswer> catArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleRegimens();
		for(int i=0; i< 3; i++) {
			catArray.add(null);
		}
		for(ConceptAnswer c : ca) {
			
			if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REGIMEN_2_STANDARD).getId().intValue()) {
				catArray.set(0, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REGIMEN_2_SHORT).getId().intValue()) {
				catArray.set(1, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.REGIMEN_2_INDIVIDUALIZED).getId().intValue()) {
				catArray.set(2, c);
			}
		}
		
		return catArray;
	}
	
	@ModelAttribute("groups")
	public ArrayList <ProgramWorkflowState> getPossiblePatientGroups() {
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
	
	@ModelAttribute("bydrug")
	public ArrayList<ProgramWorkflowState> getPossibleResultsByDrugs() {
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
	
	@ModelAttribute("hivstatuses")
	public Collection<ConceptAnswer> getPossibleHIVStatuses() {
		return Context.getService(MdrtbService.class).getPossibleHIVStatuses();
	}
	
	@ModelAttribute("resistancetypes")
	public ArrayList<ConceptAnswer> getPossibleResistanceTypes() {
		
		ArrayList<ConceptAnswer> typeArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.RESISTANCE_TYPE);
		for(int i=0; i< 8; i++) {
			typeArray.add(null);
		}
		for(ConceptAnswer c : ca) {
			if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MONO).getId().intValue()) {
				typeArray.set(0, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PDR_TB).getId().intValue()) {
				typeArray.set(1, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RR_TB).getId().intValue()) {
				typeArray.set(2, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TB).getId().intValue()) {
				typeArray.set(3, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PRE_XDR_TB).getId().intValue()) {
				typeArray.set(4, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.XDR_TB).getId().intValue()) {
				typeArray.set(5, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(TbConcepts.NO).getId().intValue()) {
				typeArray.set(6, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.UNKNOWN).getId().intValue()) {
				typeArray.set(7, c);
			}
		}
		
		return typeArray;
		
		/*return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.RESISTANCE_TYPE);*/
	}
	
	@ModelAttribute("outcomes")
	public ArrayList<ProgramWorkflowState> getPossibleTreatmentOutcomes() {
		/*return Context.getService(MdrtbService.class).getPossibleMdrtbProgramOutcomes();*/
		ArrayList<ProgramWorkflowState> stateArray = new ArrayList<ProgramWorkflowState>();
		for(int i=0; i< 5; i++) {
			stateArray.add(null);
		}
		Set<ProgramWorkflowState> states = Context.getService(MdrtbService.class).getPossibleTbProgramOutcomes();
		MdrtbService ms = Context.getService(MdrtbService.class);
		for(ProgramWorkflowState pws : states) {
			if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.CURED).getId().intValue()) {
				stateArray.set(0, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.TREATMENT_COMPLETE).getId().intValue()) {
				stateArray.set(1, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.DIED).getId().intValue()) {
				stateArray.set(2, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.FAILED).getId().intValue()) {
				stateArray.set(3, pws);
			}
			else if(pws.getConcept().getId().intValue() == ms.getConcept(TbConcepts.LOST_TO_FOLLOWUP).getId().intValue()) {
				stateArray.set(4, pws);
			}
			/*else if(pws.getConcept().getId().intValue() == ms.getConcept(MdrtbConcepts).getId().intValue()) {
				stateArray.set(6, pws);
			}*/
		}
		
		return stateArray;
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
