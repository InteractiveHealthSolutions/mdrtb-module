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
import org.openmrs.module.mdrtb.form.RegimenForm;
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
@RequestMapping("/module/mdrtb/form/regimen.form")
public class RegimenFormController {
	
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
	
	@ModelAttribute("regimenForm")
	public RegimenForm getRegimenForm(@RequestParam(required = true, value = "encounterId") Integer encounterId,
	                            @RequestParam(required = true, value = "patientProgramId") Integer patientProgramId/*,
	                            @RequestParam(required = false, value = "previousProgramId") Integer previousProgramId*/) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

		// if no form is specified, create a new one
		if (encounterId == -1) {
			MdrtbPatientProgram tbProgram = Context.getService(MdrtbService.class).getMdrtbPatientProgram(patientProgramId);
			
			RegimenForm form = new RegimenForm(tbProgram.getPatient());
			
			// prepopulate the intake form with any program information
			//form.setEncounterDatetime(tbProgram.getDateEnrolled());
			form.setLocation(tbProgram.getLocation());
			form.setPatProgId(patientProgramId);
			
			
				
			return form;
		}
		else {
			return new RegimenForm(Context.getEncounterService().getEncounter(encounterId));
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showRegimenForm(@RequestParam(required = false, value = "returnUrl") String returnUrl,
			/*@RequestParam(value="loc", required=false) String district,
			@RequestParam(value="ob", required=false) String oblast,*/
			@RequestParam(required = true, value = "patientProgramId") Integer patientProgramId,
			  	@RequestParam(required = true, value = "encounterId") Integer encounterId,
			  	@RequestParam(required = false, value = "mode") String mode,
			  ModelMap model) {
		List<Oblast> oblasts;
        List<Facility> facilities;
        List<District> districts;
        
     /*   if(oblast==null)
        {*/
        	RegimenForm regimenForm = null;
        	if(encounterId!=-1) {  //we are editing an existing encounter
        		 regimenForm = new RegimenForm(Context.getEncounterService().getEncounter(encounterId));
        	}
        	else {
        		try {
					regimenForm = getRegimenForm(-1, patientProgramId);
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
        	/*Location location  = regimenForm.getLocation();
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
		
		return new ModelAndView("/module/mdrtb/form/regimen", model);	
	}
	
	@SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST)
	public ModelAndView processRegimenForm (@ModelAttribute("regimenForm") RegimenForm regimenForm, BindingResult errors, 
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
		
		/*Location location=null;
    	
    	
    	System.out.println("PARAMS:\nob: " + oblastId + "\ndist: " + districtId + "\nfac: " + facilityId);
    	
    	if(facilityId!=null && facilityId.length()!=0)
    		location = Context.getService(MdrtbService.class).getLocation(Integer.parseInt(oblastId),Integer.parseInt(districtId),Integer.parseInt(facilityId));
    	else
    		location = Context.getService(MdrtbService.class).getLocation(Integer.parseInt(oblastId),Integer.parseInt(districtId),null);
		
    	if(location == null) { // && locations!=null && (locations.size()==0 || locations.size()>1)) {
    		throw new MdrtbAPIException("Invalid Hierarchy Set selected");
    	}
    	
    	
		if(regimenForm.getLocation()==null || !location.equals(regimenForm.getLocation())) {
			System.out.println("setting loc");
			regimenForm.setLocation(location);
		}*/
		
		System.out.println(regimenForm.getLocation());
		System.out.println(regimenForm.getProvider());
		System.out.println(regimenForm.getEncounterDatetime());
		
		if(regimenForm.getSldRegimenType()!=null && regimenForm.getSldRegimenType().getId().intValue()!=Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OTHER_MDRTB_REGIMEN).getId().intValue()) {
			
			System.out.println("Setting null");
			regimenForm.setOtherRegimen(null);
		}
		
		// save the actual update
		Context.getEncounterService().saveEncounter(regimenForm.getEncounter());
		
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
	
	
	
	@ModelAttribute("resistancetypes")
	public ArrayList<ConceptAnswer> getPossibleResistanceTypes() {
		
		ArrayList<ConceptAnswer> typeArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.RESISTANCE_TYPE);
		for(int i=0; i< 8; i++) {
			typeArray.add(null);
		}
		for(ConceptAnswer c : ca) {
			/*if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MONO).getId().intValue()) {
				typeArray.set(0, c);
			}*/
			if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PDR_TB).getId().intValue()) {
				typeArray.set(0, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.RR_TB).getId().intValue()) {
				typeArray.set(1, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.MDR_TB).getId().intValue()) {
				typeArray.set(2, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PRE_XDR_TB).getId().intValue()) {
				typeArray.set(3, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.XDR_TB).getId().intValue()) {
				typeArray.set(4, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.TDR_TB).getId().intValue()) {
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
	
	@ModelAttribute("cecOptions")
	public Collection<ConceptAnswer> getPossibleCMACPlace() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(TbConcepts.PLACE_OF_ELECTORAL_COMMISSION);
	}
	
	@ModelAttribute("fundingSources")
	public Collection<ConceptAnswer> getPossibleFundingSources() {
		return Context.getService(MdrtbService.class).getPossibleConceptAnswers(MdrtbConcepts.FUNDING_SOURCE);
	}
	
	@ModelAttribute("sldregimens")
	public Collection<ConceptAnswer> getPossibleSLDRegimenrs() {
		ArrayList<ConceptAnswer> typeArray = new ArrayList<ConceptAnswer>();
		Collection<ConceptAnswer> ca= Context.getService(MdrtbService.class).getPossibleConceptAnswers(MdrtbConcepts.SLD_REGIMEN_TYPE);
		for(int i=0; i< 7; i++) {
			typeArray.add(null);
		}
		for(ConceptAnswer c : ca) {
			
			if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.SHORT_MDR_REGIMEN).getId().intValue()) {
				typeArray.set(0, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.STANDARD_MDR_REGIMEN).getId().intValue()) {
				typeArray.set(1, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.INDIVIDUAL_WITH_BDQ).getId().intValue()) {
				typeArray.set(2, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.INDIVIDUAL_WITH_DLM).getId().intValue()) {
				typeArray.set(3, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.INDIVIDUAL_WITH_BDQ_AND_DLM).getId().intValue()) {
				typeArray.set(4, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.INDIVIDUAL_WITH_CFZ_LZD).getId().intValue()) {
				typeArray.set(5, c);
			}
			else if(c.getAnswerConcept().getId().intValue()==Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.OTHER_MDRTB_REGIMEN).getId().intValue()) {
				typeArray.set(6, c);
			}
		}
		
		return typeArray;
		
	}
	
	
		
}
