package org.openmrs.module.mdrtb.web.controller.form;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Location;
import org.openmrs.Obs;

import org.openmrs.Person;

import org.openmrs.api.context.Context;

import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.specimen.Dst;
import org.openmrs.module.mdrtb.specimen.DstImpl;
import org.openmrs.module.mdrtb.specimen.DstResult;
import org.openmrs.module.mdrtb.specimen.TestValidator;

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
	
	/*@SuppressWarnings("unchecked")
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
		if (tb03 != null) {
    		new SimpleFormValidator().validate(tb03, errors);
    	}
		
		if (errors.hasErrors()) {
			map.put("errors", errors);
			return new ModelAndView("/module/mdrtb/form/intake", map);
		}
		
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
	}*/
	
		@SuppressWarnings("unchecked")
		@RequestMapping(method = RequestMethod.POST)
		public ModelAndView processSubmit(@ModelAttribute("dst") DSTForm dst, BindingResult errors, 
		                                  @RequestParam(required = false, value = "patientProgramId") Integer patientProgramId, 
		                                  @RequestParam(required = false, value = "removeDstResult") String [] removeDstResults,
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
			
			Context.getEncounterService().saveEncounter(dst.getEncounter());
			
			dst.setDi(new DstImpl(dst.getEncounter()));
		 /*// validate
	    	if(dst != null) {
	    		new TestValidator().validate(dst, errors);
	    	}
			
	    	// if validation fails
			if (errors.hasErrors()) {
				map.put("testId", dst.getId());
				map.put("testType", dst.getTestType());
				map.put("test", dst);
				map.put("testErrors", errors);
				
				// override the testTypes parameter; we only want to create the add box for a dst in this case
				Collection<String> testTypes = new LinkedList<String>();
				testTypes.add("dst");
				map.put("testTypes", testTypes);					
				
				// hacky way to populate any add data, so that we save it and can redisplay it
				List<String> addDstResultResult = new ArrayList<String>();
				List<String> addDstResultConcentration = new ArrayList<String>();
				List<String> addDstResultColonies = new ArrayList<String>();
				List<String> addDstResultDrug = new ArrayList<String>();
				
				int i = 1;
				while (i<30) {
					addDstResultColonies.add(request.getParameter("addDstResult" + i + ".colonies"));
					addDstResultConcentration.add(request.getParameter("addDstResult" + i + ".concentration"));
					addDstResultResult.add(request.getParameter("addDstResult" + i + ".result"));
					addDstResultDrug.add(request.getParameter("addDstResult" + i + ".drug"));

					i++;
				}

				map.put("addDstResultColonies", addDstResultColonies);
				map.put("addDstResultConcentration", addDstResultConcentration);
				map.put("addDstResultResult", addDstResultResult);
				map.put("addDstResultDrug", addDstResultDrug);
				
				return new ModelAndView("/module/mdrtb/specimen/specimen", map);
			}*/
	    	
			// hacky way to manually handle the addition of new dsts
	    	// note that we only add dsts that have a result and drug specified
			int i = 1;
			while(i<=30) {
				if(StringUtils.isNotEmpty(request.getParameter("addDstResult" + i + ".result")) 
					&& StringUtils.isNotEmpty(request.getParameter("addDstResult" + i + ".drug")) )  {
					// create the new result
					DstResult dstResult = dst.addResult();
				
					// pull the values from the request
					
					String resultType = request.getParameter("addDstResult" + i + ".result");
					String drug = request.getParameter("addDstResult" + i + ".drug");
					
					// although the DstResult obj should handle it, still a good idea to set the result before the drug because of the wonky way result/drugs are stored
					if (StringUtils.isNotBlank(resultType)) {
						dstResult.setResult(Context.getConceptService().getConcept(Integer.valueOf(resultType)));
					}
					if (StringUtils.isNotBlank(drug)) {
						dstResult.setDrug(Context.getConceptService().getConcept(Integer.valueOf(drug)));
					}
				}
				i++;
			} 
			 
			// remove dst results
			if(removeDstResults != null) {
				Set<String> removeDstResultSet = new HashSet<String>(Arrays.asList(removeDstResults));
				
				for(DstResult result : dst.getResults()) {
					if(result.getId() != null && removeDstResultSet.contains(result.getId())) {
						dst.removeResult(result);
					}
				}
			}
	    	
			// save the actual update
			
			
			//Context.getService(MdrtbService.class).saveDst(dst.getDi());
			
			Context.getService(MdrtbService.class).saveDst(dst.getDi());
			status.setComplete();
			
			// clears the command object from the session
			
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
	
	@ModelAttribute("dstResults")
	Collection<Concept>getPossibleDSTResults() {
		return Context.getService(MdrtbService.class).getPossibleDstResults();
	}
	
	@ModelAttribute("defaultDstDrugs")
	public List<List<Object>> getDefaultDstDrugs() {
		return MdrtbUtil.getDefaultDstDrugs();
	}
	
	@ModelAttribute("drugTypes")
	public Collection<Concept> getPossibleDrugTypes() {
		return Context.getService(MdrtbService.class).getMdrtbDrugs();
	}
	
		
}
