package org.openmrs.module.mdrtb.web.controller.form.pv;

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
import org.openmrs.module.mdrtb.form.RegimenForm;
import org.openmrs.module.mdrtb.form.TB03Form;
import org.openmrs.module.mdrtb.form.TB03uForm;
import org.openmrs.module.mdrtb.form.pv.AEForm;

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
@RequestMapping("/module/mdrtb/form/ae.form")
public class AEFormController {
	
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
	
	@ModelAttribute("aeForm")
	public AEForm getAEForm(@RequestParam(required = true, value = "encounterId") Integer encounterId,
	                            @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId/*,
	                            @RequestParam(required = false, value = "previousProgramId") Integer previousProgramId*/) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		// if no form is specified, create a new one
		if (encounterId == -1) {
			MdrtbPatientProgram tbProgram = Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId);
			
			AEForm form = new AEForm(tbProgram.getPatient());
			
			// prepopulate the intake form with any program information
			//form.setEncounterDatetime(tbProgram.getDateEnrolled());
			form.setLocation(tbProgram.getLocation());
			form.setPatProgId(patientProgramId);
			
			
				
			return form;
		}
		else {
			return new AEForm(Context.getEncounterService().getEncounter(encounterId));
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showAEForm(@RequestParam(required = false, value = "returnUrl") String returnUrl,
			/*@RequestParam(value="loc", required=false) String district,
			@RequestParam(value="ob", required=false) String oblast,*/
			@RequestParam(required = true, value = "patientProgramId") Integer patientProgramId,
			  	@RequestParam(required = true, value = "encounterId") Integer encounterId,
			  	@RequestParam(required = false, value = "mode") String mode,
			  ModelMap model) {
		/*List<Oblast> oblasts;
        List<Facility> facilities;
        List<District> districts;
        
        if(oblast==null)
        {*/
        	AEForm aeForm = null;
        	if(encounterId!=-1) {  //we are editing an existing encounter
        		 aeForm = new AEForm(Context.getEncounterService().getEncounter(encounterId));
        	}
        	else {
        		try {
					aeForm = getAEForm(-1, patientProgramId);
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
        	
        	/*//TB03Form tb03 = new TB03Form(Context.getEncounterService().getEncounter(encounterId));
        	Location location  = aeForm.getLocation();
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
        }*/
        model.addAttribute("encounterId", encounterId);
        if(mode!=null && mode.length()!=0) {
        	model.addAttribute("mode", mode);
        }
		
		return new ModelAndView("/module/mdrtb/form/ae", model);	
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST)
	public ModelAndView processAEForm (@ModelAttribute("aeForm") AEForm aeForm, BindingResult errors, 
	                                       @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId,
	                                      /* @RequestParam(required = true, value = "oblast") String oblastId,
	                                       @RequestParam(required = true, value = "district") String districtId,
	                                       @RequestParam(required = false, value = "facility") String facilityId,*/
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
		
		//Location location=null;
    	
    	
    	/*System.out.println("PARAMS:\nob: " + oblastId + "\ndist: " + districtId + "\nfac: " + facilityId);
    	
    	if(facilityId!=null && facilityId.length()!=0)
    		location = Context.getService(MdrtbService.class).getLocation(Integer.parseInt(oblastId),Integer.parseInt(districtId),Integer.parseInt(facilityId));
    	else
    		location = Context.getService(MdrtbService.class).getLocation(Integer.parseInt(oblastId),Integer.parseInt(districtId),null);
		
    	if(location == null) { // && locations!=null && (locations.size()==0 || locations.size()>1)) {
    		throw new MdrtbAPIException("Invalid Hierarchy Set selected");
    	}*/
    	
    	
		/*if(aeForm.getLocation()==null || !location.equals(aeForm.getLocation())) {
			System.out.println("setting loc");
			aeForm.setLocation(location);
		}*/
		
		System.out.println(aeForm.getLocation());
		System.out.println(aeForm.getProvider());
		System.out.println(aeForm.getEncounterDatetime());
		/*
		if(aeForm.getSldRegimenType()!=null && regimenForm.getSldRegimenType().getId().intValue()!=Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OTHER_MDRTB_REGIMEN).getId().intValue()) {
			
			System.out.println("Setting null");
			regimenForm.setOtherRegimen(null);
		}*/
		
//		System.out.println("*********************************************");
//		System.out.println("TYPE: " + aeForm.getTypeOfEvent());
//		System.out.println("SERIOUS" + Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SERIOUS));
//		System.out.println("SI" + Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OF_SPECIAL_INTEREST));
//		System.out.println("SAE TYPE" + aeForm.getTypeOfSAE());
//		System.out.println("SIE TYPE" + aeForm.getTypeOfSpecialEvent());
//		System.out.println("*********************************************");
		
		if(aeForm.getTypeOfEvent()!=null && aeForm.getTypeOfEvent().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SERIOUS).getId().intValue()) {
			
			System.out.println(">>>>>>>>>> Setting special null");
			aeForm.setTypeOfSpecialEvent(null);
		}
		
		else if(aeForm.getTypeOfEvent()!=null && aeForm.getTypeOfEvent().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OF_SPECIAL_INTEREST).getId().intValue()) {
			
			System.out.println(">>>>>>>>>> Setting serious null");
			aeForm.setTypeOfSAE(null);
		}
		
		else if(aeForm.getTypeOfEvent()==null) {
			System.out.println(">>>>>>>>>> Setting both null");
			aeForm.setTypeOfSpecialEvent(null);
			aeForm.setTypeOfSAE(null);
		}
		
		if(aeForm.getCausalityDrug1()==null) {
			aeForm.setCausalityAssessmentResult1(null);
		}
		
		if(aeForm.getCausalityDrug2()==null) {
			aeForm.setCausalityAssessmentResult2(null);
		}
		
		if(aeForm.getCausalityDrug3()==null) {
			aeForm.setCausalityAssessmentResult3(null);
		}
		
		if(aeForm.getActionOutcome()!=null && aeForm.getActionOutcome().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NOT_RESOLVED).getId().intValue())
		{
			aeForm.setOutcomeDate(null);
		}
		// save the actual update
		Context.getEncounterService().saveEncounter(aeForm.getEncounter());
		
		//handle changes in workflows
				
		status.setComplete();
		
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
	
	@ModelAttribute("aeOptions")
	public ArrayList<ConceptAnswer> getPossibleAdverseEvents() {
		
		ArrayList<ConceptAnswer> typeArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleConceptAnswers(MdrtbConcepts.ADVERSE_EVENT);
		for(int i=0; i< 24; i++) {
			typeArray.add(null);
		}
		for(ConceptAnswer c : ca) {
			
			if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NAUSEA).getId().intValue()) {
				typeArray.set(0, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DIARRHOEA).getId().intValue()) {
				typeArray.set(1, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ARTHALGIA).getId().intValue()) {
				typeArray.set(2, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DIZZINESS).getId().intValue()) {
				typeArray.set(3, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HEARING_DISTURBANCES).getId().intValue()) {
				typeArray.set(4, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HEADACHE).getId().intValue()) {
				typeArray.set(5, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SLEEP_DISTURBANCES).getId().intValue()) {
				typeArray.set(6, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ELECTROLYTE_DISTURBANCES).getId().intValue()) {
				typeArray.set(7, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ABDOMINAL_PAIN).getId().intValue()) {
				typeArray.set(8, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANOREXIA).getId().intValue()) {
				typeArray.set(9, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.GASTRITIS).getId().intValue()) {
				typeArray.set(10, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PERIPHERAL_NEUROPATHY).getId().intValue()) {
				typeArray.set(11, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DEPRESSION).getId().intValue()) {
				typeArray.set(12, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.TINNITUS).getId().intValue()) {
				typeArray.set(13, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ALLERGIC_REACTION).getId().intValue()) {
				typeArray.set(14, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RASH).getId().intValue()) {
				typeArray.set(15, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.VISUAL_DISTURBANCES).getId().intValue()) {
				typeArray.set(16, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SEIZURES).getId().intValue()) {
				typeArray.set(17, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HYPOTHYROIDISM).getId().intValue()) {
				typeArray.set(18, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PSYCHOSIS).getId().intValue()) {
				typeArray.set(19, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SUICIDAL_IDEATION).getId().intValue()) {
				typeArray.set(20, c);
			}
			
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HEPATITIS_AE).getId().intValue()) {
				typeArray.set(21, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RENAL_FAILURE).getId().intValue()) {
				typeArray.set(22, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.QT_PROLONGATION).getId().intValue()) {
				typeArray.set(23, c);
			}
			
		}
		
		return typeArray;
	
	}
	
	@ModelAttribute("diOptions")
	public ArrayList<ConceptAnswer> getPossibleDiagnosticInvestigations() {
		
		ArrayList<ConceptAnswer> typeArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleConceptAnswers(MdrtbConcepts.LAB_TEST_CONFIRMING_AE);
		for(int i=0; i< 22; i++) {
			typeArray.add(null);
		}
		for(ConceptAnswer c : ca) {
			
			if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CLINICAL_SCREEN).getId().intValue()) {
				typeArray.set(0, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.VISUAL_ACUITY).getId().intValue()) {
				typeArray.set(1, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SIMPLE_HEARING_TEST).getId().intValue()) {
				typeArray.set(2, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AUDIOGRAM).getId().intValue()) {
				typeArray.set(3, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NEURO_INVESTIGATION).getId().intValue()) {
				typeArray.set(4, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CREATNINE).getId().intValue()) {
				typeArray.set(5, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ALT).getId().intValue()) {
				typeArray.set(6, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AST).getId().intValue()) {
				typeArray.set(7, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.BILIRUBIN).getId().intValue()) {
				typeArray.set(8, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ALKALINE_PHOSPHATASE).getId().intValue()) {
				typeArray.set(9, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.YGT).getId().intValue()) {
				typeArray.set(10, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ECG).getId().intValue()) {
				typeArray.set(11, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LIPASE).getId().intValue()) {
				typeArray.set(12, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.AMYLASE).getId().intValue()) {
				typeArray.set(13, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.POTASSIUM).getId().intValue()) {
				typeArray.set(14, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MAGNESIUM).getId().intValue()) {
				typeArray.set(15, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CALCIUM).getId().intValue()) {
				typeArray.set(16, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ALBUMIN).getId().intValue()) {
				typeArray.set(17, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CBC).getId().intValue()) {
				typeArray.set(18, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.BLOOD_GLUCOSE).getId().intValue()) {
				typeArray.set(19, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.THYROID_TEST).getId().intValue()) {
				typeArray.set(20, c);
			}
			
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OTHER).getId().intValue()) {
				typeArray.set(21, c);
			}
			
		}
		
		return typeArray;
	
	}
	
	@ModelAttribute("typeOptions")
	public Collection<ConceptAnswer> getPossibleEventType() {
		ArrayList<ConceptAnswer> typeArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleConceptAnswers(MdrtbConcepts.AE_TYPE);
		for(int i=0; i< 2; i++) {
			typeArray.add(null);
		}
		for(ConceptAnswer c : ca) {
			
			if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SERIOUS).getId().intValue()) {
				typeArray.set(0, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OF_SPECIAL_INTEREST).getId().intValue()) {
				typeArray.set(1, c);
			}
			
		}
		
		return typeArray;
		
	}
	
	@ModelAttribute("saeOptions")
	public Collection<ConceptAnswer> getPossibleSAEType() {
		ArrayList<ConceptAnswer> typeArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleConceptAnswers(MdrtbConcepts.SAE_TYPE);
		for(int i=0; i< 5; i++) {
			typeArray.add(null);
		}
		for(ConceptAnswer c : ca) {
			
			if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DEATH).getId().intValue()) {
				typeArray.set(0, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HOSPITALIZATION).getId().intValue()) {
				typeArray.set(1, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DISABILITY).getId().intValue()) {
				typeArray.set(2, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CONGENITAL_ANOMALY).getId().intValue()) {
				typeArray.set(3, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LIFE_THREATENING_EXPERIENCE).getId().intValue()) {
				typeArray.set(4, c);
			}
			
		}
		
		return typeArray;
		
	}
	
	@ModelAttribute("specialOptions")
	public Collection<ConceptAnswer> getPossibleSpecialType() {
		ArrayList<ConceptAnswer> typeArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleConceptAnswers(MdrtbConcepts.SPECIAL_INTEREST_EVENT_TYPE);
		for(int i=0; i< 13; i++) {
			typeArray.add(null);
		}
		for(ConceptAnswer c : ca) {
			
			if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PERIPHERAL_NEUROPATHY).getId().intValue()) {
				typeArray.set(0, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PSYCHIATRIC_DISORDER).getId().intValue()) {
				typeArray.set(1, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.VISUAL_DISTURBANCES).getId().intValue()) {
				typeArray.set(2, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HEARING_DISTURBANCES).getId().intValue()) {
				typeArray.set(3, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MYELOSUPPRESSION).getId().intValue()) {
				typeArray.set(4, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.QT_PROLONGATION).getId().intValue()) {
				typeArray.set(5, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LACTIC_ACIDOSIS).getId().intValue()) {
				typeArray.set(6, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HEPATITIS_AE).getId().intValue()) {
				typeArray.set(7, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HYPOTHYROIDISM).getId().intValue()) {
				typeArray.set(8, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HYPOKALEMIA).getId().intValue()) {
				typeArray.set(9, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PANCREATITIS).getId().intValue()) {
				typeArray.set(10, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PHOSPHOLIPIDOSIS).getId().intValue()) {
				typeArray.set(11, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RENAL_FAILURE).getId().intValue()) {
				typeArray.set(12, c);
			}
			
		}
		
		return typeArray;
		
	}
	
	@ModelAttribute("cdOptions")
	public Collection<ConceptAnswer> getCausalityDrugOptions() {
		ArrayList<ConceptAnswer> typeArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleConceptAnswers(MdrtbConcepts.CAUSALITY_DRUG_1);
		
		
		return ca;
		
	}
	
	@ModelAttribute("carOptions")
	public Collection<ConceptAnswer> getCausalityAssessmentOptions() {
		ArrayList<ConceptAnswer> typeArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleConceptAnswers(MdrtbConcepts.CAUSALITY_ASSESSMENT_RESULT_1);
		for(int i=0; i< 5; i++) {
			typeArray.add(null);
		}
		for(ConceptAnswer c : ca) {
			
			if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DEFINITE).getId().intValue()) {
				typeArray.set(0, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PROBABLE).getId().intValue()) {
				typeArray.set(1, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.POSSIBLE).getId().intValue()) {
				typeArray.set(2, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SUSPECTED_CA).getId().intValue()) {
				typeArray.set(3, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NOT_CLASSIFIED).getId().intValue()) {
				typeArray.set(4, c);
			}
			
		}
	
		
		return typeArray;
		
	}
	
	@ModelAttribute("actions")
	public Collection<ConceptAnswer> getActionOptions() {
		ArrayList<ConceptAnswer> typeArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleConceptAnswers(MdrtbConcepts.AE_ACTION);
		for(int i=0; i< 6; i++) {
			typeArray.add(null);
		}
		for(ConceptAnswer c : ca) {
			
			if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DOSE_NOT_CHANGED).getId().intValue()) {
				typeArray.set(0, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DOSE_REDUCED).getId().intValue()) {
				typeArray.set(1, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DRUG_INTERRUPTED).getId().intValue()) {
				typeArray.set(2, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.DRUG_WITHDRAWN).getId().intValue()) {
				typeArray.set(3, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ANCILLARY_DRUG_GIVEN).getId().intValue()) {
				typeArray.set(4, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ADDITIONAL_EXAMINATION).getId().intValue()) {
				typeArray.set(5, c);
			}
			
		}
	
		
		return typeArray;
		
	}
	
	@ModelAttribute("outcomes")
	public Collection<ConceptAnswer> getActionOutcomes() {
		ArrayList<ConceptAnswer> typeArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleConceptAnswers(MdrtbConcepts.AE_OUTCOME);
		for(int i=0; i< 5; i++) {
			typeArray.add(null);
		}
		for(ConceptAnswer c : ca) {
			
			if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RESOLVED).getId().intValue()) {
				typeArray.set(0, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RESOLVED_WITH_SEQUELAE).getId().intValue()) {
				typeArray.set(1, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.FATAL).getId().intValue()) {
				typeArray.set(2, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RESOLVING).getId().intValue()) {
				typeArray.set(3, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NOT_RESOLVED).getId().intValue()) {
				typeArray.set(4, c);
			}
			
		}
	
		return typeArray;
		
	}
	
	@ModelAttribute("meddraCodes")
	public ArrayList<ConceptAnswer> getMeddraCodeOptions() {
		
		ArrayList<ConceptAnswer> typeArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleConceptAnswers(MdrtbConcepts.MEDDRA_CODE);
		for(int i=0; i< 12; i++) {
			typeArray.add(null);
		}
		for(ConceptAnswer c : ca) {
			
			if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SKIN_DISORDER).getId().intValue()) {
				typeArray.set(0, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MUSCULOSKELETAL_DISORDER).getId().intValue()) {
				typeArray.set(1, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NEUROLOGICAL_DISORDER).getId().intValue()) {
				typeArray.set(2, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.VISION_DISORDER).getId().intValue()) {
				typeArray.set(3, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.HEARING_DISORDER).getId().intValue()) {
				typeArray.set(4, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PSYCHIATRIC_DISORDER).getId().intValue()) {
				typeArray.set(5, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.GASTROINTESTINAL_DISORDER).getId().intValue()) {
				typeArray.set(6, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.LIVER_DISORDER).getId().intValue()) {
				typeArray.set(7, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.METABOLIC_DISORDER).getId().intValue()) {
				typeArray.set(8, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.ENDOCRINE_DISORDER).getId().intValue()) {
				typeArray.set(9, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.CARDIAC_DISORDER).getId().intValue()) {
				typeArray.set(10, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OTHER).getId().intValue()) {
				typeArray.set(11, c);
			}
		}
		
		return typeArray;
	
	}
	
	@ModelAttribute("drugRechallenges")
	public ArrayList<ConceptAnswer> getRechallengeOptions() {
		
		ArrayList<ConceptAnswer> typeArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleConceptAnswers(MdrtbConcepts.DRUG_RECHALLENGE);
		for(int i=0; i< 4; i++) {
			typeArray.add(null);
		}
		for(ConceptAnswer c : ca) {
			
			if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NO_RECHALLENGE).getId().intValue()) {
				typeArray.set(0, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RECURRENCE_OF_EVENT).getId().intValue()) {
				typeArray.set(1, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NO_RECURRENCE).getId().intValue()) {
				typeArray.set(2, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.UNKNOWN_RESULT).getId().intValue()) {
				typeArray.set(3, c);
			}
			
		}
		
		return typeArray;
	}
	
	@ModelAttribute("regimens")
	public ArrayList<String> getRegimens(@RequestParam(required = true, value = "patientProgramId") Integer patientProgramId) {
		ArrayList<String> regimens = new ArrayList<String>();
		MdrtbPatientProgram pp = Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId);
		ArrayList<RegimenForm> regimenList = Context.getService(MdrtbService.class).getRegimenFormsForProgram(pp.getPatient(), patientProgramId);
		
		for(RegimenForm form : regimenList) {
			String s = form.getRegimenSummary();
			if(s!=null) {
				regimens.add(s);
			}
		}
		
		return regimens;
	}
	
	@ModelAttribute("yesno")
	public ArrayList<ConceptAnswer> getYesno() {
		
		ArrayList<ConceptAnswer> typeArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleConceptAnswers(MdrtbConcepts.REQUIRES_ANCILLARY_DRUGS);
		for(int i=0; i< 2; i++) {
			typeArray.add(null);
		}
		for(ConceptAnswer c : ca) {
			
			if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.NO).getId().intValue()) {
				typeArray.set(0, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.YES).getId().intValue()) {
				typeArray.set(1, c);
			}
			
		}
		
		return typeArray;
	}
	
	
	
	
}