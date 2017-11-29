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
import org.openmrs.PatientState;
import org.openmrs.Person;
import org.openmrs.ProgramWorkflow;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.service.MdrtbService;

import org.openmrs.module.mdrtb.exception.MdrtbAPIException;
import org.openmrs.module.mdrtb.form.Form89;
import org.openmrs.module.mdrtb.form.TB03Form;

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
@RequestMapping("/module/mdrtb/form/form89.form")
public class Form89Controller {
	
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
	
	@ModelAttribute("form89")
	public Form89 getForm89(@RequestParam(required = true, value = "encounterId") Integer encounterId,
	                            @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		// if no form is specified, create a new one
		if (encounterId == -1) {
			TbPatientProgram tbProgram = Context.getService(MdrtbService.class).getTbPatientProgram(patientProgramId);
			
			Form89 form = new Form89(tbProgram.getPatient());
			
			// prepopulate the intake form with any program information
			form.setEncounterDatetime(tbProgram.getDateEnrolled());
			
			form.initTB03(patientProgramId);
			if(form.getTB03()!=null)
					form.setLocation(form.getTB03().getLocation());
			return form;
		}
		else {
			Form89 ret = new Form89(Context.getEncounterService().getEncounter(encounterId));
			ret.initTB03(patientProgramId);
			return ret;
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showForm89(@RequestParam(required = false, value = "returnUrl") String returnUrl,
			@RequestParam(value="loc", required=false) String district,
			@RequestParam(value="ob", required=false) String oblast,
			@RequestParam(required = true, value = "patientProgramId") Integer patientProgramId,
			  	@RequestParam(required = true, value = "encounterId") Integer encounterId,
			  	@RequestParam(required = false, value = "mode") String mode,
			  ModelMap model) {
		//ModelMap map = new ModelMap();
		List<Oblast> oblasts;
        List<Facility> facilities;
        List<District> districts;
        
        if(oblast==null)
        {
        	Form89 form89 = null;
        	if(encounterId!=-1) {  //we are editing an existing encounter
        		 form89 = new Form89(Context.getEncounterService().getEncounter(encounterId));
        	}
        	else {
        		try {
					form89 = getForm89(-1, patientProgramId);
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
        	Location location  = form89.getLocation();
        	//System.out.println("show:" + location.getDisplayString());
        	if(location!=null) {
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
        	else {
        		oblasts  = Context.getService(MdrtbService.class).getOblasts();
        		model.addAttribute("oblasts", oblasts);
        	}
        }
        
//        else if(oblast==null) {
//        	oblasts = Context.getService(MdrtbService.class).getOblasts();
//        	model.addAttribute("oblasts", oblasts);
//        	
//        }
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
		
		return new ModelAndView("/module/mdrtb/form/form89", model);	
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST)
	public ModelAndView processForm89 (@ModelAttribute("form89") Form89 form89, BindingResult errors, 
	                                       @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId,
	                                       @RequestParam(required = true, value = "oblast") String oblastId,
	                                       @RequestParam(required = true, value = "district") String districtId,
	                                       @RequestParam(required = false, value = "facility") String facilityId,
	                                       @RequestParam(required = false, value = "returnUrl") String returnUrl,
	                                       SessionStatus status, HttpServletRequest request, ModelMap map) {
		
		
		// perform validation and check for errors
		/*if (form89 != null) {
    		new SimpleFormValidator().validate(form89, errors);
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
    	
    	
		if(form89.getLocation()==null || !location.equals(form89.getLocation())) {
			System.out.println("setting loc");
			form89.setLocation(location);
		}
		
		if(form89.getPopulationCategory()!=null && form89.getPopulationCategory().getId().intValue()!=Context.getService(MdrtbService.class).getConcept(TbConcepts.FOREIGNER).getId().intValue()) {
			
			System.out.println("Setting null");
			form89.setCountryOfOrigin(null);
		}
		
		if(form89.getCircumstancesOfDetection()!=null && form89.getCircumstancesOfDetection().getId().intValue()!=Context.getService(MdrtbService.class).getConcept(TbConcepts.MIGRANT).getId().intValue()) {
			
			System.out.println("Setting null");
			form89.setCityOfOrigin(null);
			form89.setDateOfReturn(null);
		}
		
		if(form89.getMethodOfDetection()!=null && form89.getMethodOfDetection().getId().intValue()!=Context.getService(MdrtbService.class).getConcept(TbConcepts.OTHER).getId().intValue()) {
			
			System.out.println("Setting null");
			form89.setOtherMethodOfDetection(null);
			
		}
		
		// save the actual update
		Context.getEncounterService().saveEncounter(form89.getEncounter());
		
		boolean programModified = false;
		//handle changes in workflows
		/*Concept outcome = form89.getTreatmentOutcome();
		Concept group = form89.getRegistrationGroup();
		
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
			returnUrl = request.getContextPath() + "/module/mdrtb/dashboard/tbdashboard.form";
		}
		
		returnUrl = MdrtbWebUtil.appendParameters(returnUrl, Context.getService(MdrtbService.class).getTbPatientProgram(patientProgramId).getPatient().getId(), patientProgramId);
		
		return new ModelAndView(new RedirectView(returnUrl));
	}
	
	@ModelAttribute("patientProgramId")
	public Integer getPatientProgramId(@RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) {
		return patientProgramId;
	}
	
	
	@ModelAttribute("tbProgram")
	public TbPatientProgram getTbPatientProgram(@RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) {
		return Context.getService(MdrtbService.class).getTbPatientProgram(patientProgramId);
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
	
	@ModelAttribute("locationtypes")
	public Collection<ConceptAnswer> getPossibleLocationTypes() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.LOCATION_TYPE);
	}
	
	@ModelAttribute("populationcategories")
	public Collection<ConceptAnswer> getPossiblePopulationCategories() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.POPULATION_CATEGORY);
	}
	
	@ModelAttribute("professions")
	public Collection<ConceptAnswer> getPossibleProfessions() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.PROFESSION);
	}
	
	@ModelAttribute("places")
	public Collection<ConceptAnswer> getPossiblePlacesOfDetection() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.PLACE_OF_DETECTION);
	}
	
	@ModelAttribute("circumstances")
	public Collection<ConceptAnswer> getPossibleCircumstancesOfDetection() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.CIRCUMSTANCES_OF_DETECTION);
	}
	
	@ModelAttribute("methods")
	public Collection<ConceptAnswer> getPossibleMethodsOfDetection() {
		//return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.METHOD_OF_DETECTION);
		ArrayList<ConceptAnswer> stateArray = new ArrayList<ConceptAnswer>();
		for(int i=0; i< 9; i++) {
			stateArray.add(null);
		}
		Collection<ConceptAnswer> bases = Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.METHOD_OF_DETECTION);
		MdrtbService ms = Context.getService(MdrtbService.class);
		for(ConceptAnswer ca : bases) {
			if(ca.getAnswerConcept().getId().intValue() == ms.getConcept(TbConcepts.HISTOLOGY).getId().intValue()) {
				stateArray.set(0, ca);
			}
			else if(ca.getAnswerConcept().getId().intValue() == ms.getConcept(TbConcepts.GENEXPERT).getId().intValue()) {
				stateArray.set(1, ca);
			}
			else if(ca.getAnswerConcept().getId().intValue() == ms.getConcept(TbConcepts.FLURORESCENT_MICROSCOPY).getId().intValue()) {
				stateArray.set(2, ca);
			}
			else if(ca.getAnswerConcept().getId().intValue() == ms.getConcept(TbConcepts.CXR_RESULT).getId().intValue()) {
				stateArray.set(3, ca);
			}
			else if(ca.getAnswerConcept().getId().intValue() == ms.getConcept(TbConcepts.FLUOROGRAPHY).getId().intValue()) {
				stateArray.set(4, ca);
			}
			else if(ca.getAnswerConcept().getId().intValue() == ms.getConcept(TbConcepts.HAIN_TEST).getId().intValue()) {
				stateArray.set(5, ca);
			}
			else if(ca.getAnswerConcept().getId().intValue() == ms.getConcept(TbConcepts.CULTURE_DETECTION).getId().intValue()) {
				stateArray.set(6, ca);
			}
			else if(ca.getAnswerConcept().getId().intValue() == ms.getConcept(TbConcepts.TUBERCULIN_TEST).getId().intValue()) {
				stateArray.set(7, ca);
			}
			else if(ca.getAnswerConcept().getId().intValue() == ms.getConcept(TbConcepts.OTHER).getId().intValue()) {
				stateArray.set(8, ca);
			}
		}
		return stateArray;
	}
	
	@ModelAttribute("epsites")
	public Collection<ConceptAnswer> getPossibleEpSites() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.SITE_OF_EPTB);
	}
	
	@ModelAttribute("psites")
	public Collection<ConceptAnswer> getPossiblePSites() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.PTB_SITE);
	}
	
	@ModelAttribute("eplocations")
	public Collection<ConceptAnswer> getPossibleEPLocations() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.EPTB_SITE);
	}	
	
	@ModelAttribute("diabetesOptions")
	public Collection<ConceptAnswer> getPossibleDiabetes() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.DIABETES);
	}
	
	@ModelAttribute("cnsdlOptions")
	public Collection<ConceptAnswer> getPossibleCNSDL() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.CNSDL);
	}
	
	@ModelAttribute("htHeartDiseaseOptions")
	public Collection<ConceptAnswer> getPossibleHeartDisease() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.HYPERTENSION_OR_HEART_DISEASE);
	}

	@ModelAttribute("ulcerOptions")
	public Collection<ConceptAnswer> getPossibleUlcers() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.ULCER);
	}
	
	@ModelAttribute("presences")
	public Collection<ConceptAnswer> getPossibleDecay() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.PRESENCE_OF_DECAY);
	}
		
	@ModelAttribute("mentalDisorderOptions")
	public Collection<ConceptAnswer> mentalDisorderOptions() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.MENTAL_DISORDER);
	}
	
	@ModelAttribute("ibc20Options")
	public Collection<ConceptAnswer> getPossibleIbc20() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.ICD20);
	}
	
	@ModelAttribute("cancerOptions")
	public Collection<ConceptAnswer> getPossibleCancer() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.CANCER);
	}
	
	@ModelAttribute("hepatitisOptions")
	public Collection<ConceptAnswer> getPossibleHepatitis() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.COMORBID_HEPATITIS);
	}
	
	@ModelAttribute("kidneyDiseaseOptions")
	public Collection<ConceptAnswer> getPossibleKidneyDisease() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.KIDNEY_DISEASE);
	}
	
	@ModelAttribute("noDiseaseOptions")
	public Collection<ConceptAnswer> getPossibleND() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.NO_DISEASE);
	}
	
	
	
	@ModelAttribute("gptOptions")
	public Collection<ConceptAnswer> getPossibleGPT() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.GPT);
	}
	
	@ModelAttribute("cecOptions")
	public Collection<ConceptAnswer> getPossibleCMACPlace() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.PLACE_OF_ELECTORAL_COMMISSION);
	}
}
