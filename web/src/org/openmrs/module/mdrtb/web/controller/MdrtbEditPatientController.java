package org.openmrs.module.mdrtb.web.controller;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.Person;
import org.openmrs.PersonAddress;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.PersonName;
import org.openmrs.api.APIException;
import org.openmrs.api.PersonService.ATTR_VIEW_TYPE;
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.mdrtb.Country;
import org.openmrs.module.mdrtb.District;
import org.openmrs.module.mdrtb.Facility;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.Oblast;
import org.openmrs.module.mdrtb.exception.MdrtbAPIException;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.validator.PatientValidator;
import org.openmrs.propertyeditor.ConceptEditor;
import org.openmrs.propertyeditor.LocationEditor;
import org.openmrs.propertyeditor.PatientIdentifierTypeEditor;
import org.openmrs.util.OpenmrsConstants.PERSON_TYPE;
import org.openmrs.web.controller.person.PersonFormController;
import org.openmrs.web.dwr.PatientListItem;
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
@RequestMapping("/module/mdrtb/mdrtbEditPatient.form")
public class MdrtbEditPatientController {

	protected final Log log = LogFactory.getLog(getClass());
	
	PatientValidator validator = new PatientValidator();
	
	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		//bind dates
		SimpleDateFormat dateFormat = Context.getDateFormat();
    	dateFormat.setLenient(false);
    	binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true, 10));
    	
		// register other custom binders
    	binder.registerCustomEditor(Concept.class, new ConceptEditor());
		binder.registerCustomEditor(Location.class, new LocationEditor());
		binder.registerCustomEditor(PatientIdentifierType.class, new PatientIdentifierTypeEditor());
	}
	
	@ModelAttribute("patientId")
	public Integer getPatientId(@RequestParam(required = false, value = "patientId") Integer patientId) {
		return patientId;
	}
	
	@ModelAttribute("patientProgramId")
	public Integer getPatientProgramId(@RequestParam(required = false, value = "patientProgramId") Integer patientProgramId) {
		return patientProgramId;
	}
	
	@ModelAttribute("successURL")
	public String getSuccessUrl(@RequestParam(required=false, value="successURL") String successUrl) {
		// as a default, just reload the same page
		if (StringUtils.isBlank(successUrl)) {
			successUrl="mdrtbEditPatient.form";
		}
		
		return successUrl;
	}
	
	@ModelAttribute("locations")
	public Collection<Location> getPossibleLocations() {
		
		List<Location> list = new ArrayList<Location>();
		list.add(Context.getLocationService().getLocation(1));
		return list;
		
		//return Context.getLocationService().getAllLocations(false);
	}

	// checks to see if the "fixedIdentifierLocation" global prop has been specified, which is used to determine if we
	// should show the location selector for identifiers
	@ModelAttribute("showIdentifierLocationSelector")
	public boolean getShowIdentifierLocationSelector() {
		return StringUtils.isBlank(Context.getAdministrationService().getGlobalProperty("mdrtb.fixedIdentifierLocation"));
	}
	
	@ModelAttribute("patientIdentifierMap")
	public Map<Integer, PatientIdentifier> getPatientIdentifierMap(@RequestParam(required = false, value="patientId") Integer patientId) {
		
		final Map<Integer,PatientIdentifier> map = new HashMap<Integer,PatientIdentifier>();
		
		if (patientId !=null && patientId != -1) {
			Patient patient = Context.getPatientService().getPatient(patientId);
		
			if (patient != null) {
				for (PatientIdentifierType type : Context.getPatientService().getAllPatientIdentifierTypes()) {			
					map.put(type.getId(), patient.getPatientIdentifier(type));
				}
			}
		}
		
		return map;
	}	
	
	@ModelAttribute("dotsIdentifier")
	public PatientIdentifierType getDotsIdentifier() {
		
		return Context.getPatientService().getPatientIdentifierTypeByName(Context.getAdministrationService().getGlobalProperty("mdrtb.primaryPatientIdentifierType"));
	}
	
	@SuppressWarnings("unchecked")
    @ModelAttribute("patientIdentifierTypesAutoAssigned")
	public List<PatientIdentifierType> getPatientIdentifierTypesAutoAssigned() {
		// this is only relevant if we are using the idgen module
		if(!ModuleFactory.getStartedModulesMap().containsKey("idgen")) {
			return new LinkedList<PatientIdentifierType>();  // return an empty list
		}
		else {
			// access the idgen module via reflection
			try {
				Class identifierSourceServiceClass = Context.loadClass("org.openmrs.module.idgen.service.IdentifierSourceService");
				Object idgen = Context.getService(identifierSourceServiceClass);
				Method getPatientIdentifierTypesByAutoGenerationOption = identifierSourceServiceClass.getMethod("getPatientIdentifierTypesByAutoGenerationOption", Boolean.class, Boolean.class);
				
				return (List<PatientIdentifierType>) getPatientIdentifierTypesByAutoGenerationOption.invoke(idgen, false, true);
			}
			catch(Exception e) {
				log.error("Unable to access IdentifierSourceService for automatic id generation.  Is the Idgen module installed and up-to-date?", e);
				return new LinkedList<PatientIdentifierType>();  // return an empty list
			}
		}
	}
	
    @SuppressWarnings("unchecked")
    @ModelAttribute("patientIdentifierTypes")
	public List<PatientIdentifierType> getPatientIdentifierTypes() {
		return ListUtils.subtract(Context.getPatientService().getAllPatientIdentifierTypes(),  getPatientIdentifierTypesAutoAssigned());	
	}
	    
	@ModelAttribute("patient")
	public Patient getPatient(@RequestParam(required = false, value="patientId") Integer patientId,
	                          @RequestParam(required = false, value="addName") String addName,
	                          @RequestParam(required = false, value="addBirthdate") String addBirthdate,
	                          @RequestParam(required = false, value="addAge") String addAge,
	                          @RequestParam(required = false, value="addGender") String addGender,
	                          HttpServletRequest request){
		
		Patient patient = null;
		
		// see if we have a patient id (-1 signifies that we are looking to add a new patient)
		if (patientId != null && patientId != -1) {  
			patient = Context.getPatientService().getPatient(patientId);
			
			if (patient == null) {
				throw new APIException("Invalid patient id passed to edit patient controller");
			}
		}
		else {
			// handle a new patient
			patient = new Patient();
			
			// initialize with any request parameters that may have been passed
			if (addName != null) {
				PersonFormController.getMiniPerson(patient, addName, addGender, addBirthdate, addAge);
			}
		}
		
		// if there is no default address for this patient, create one
		if (patient.getPersonAddress() == null) {
			PersonAddress address = new PersonAddress();
			address.setPreferred(true);
			patient.addAddress(address);
		}
		
		// if there is no default name for this patient, create one
		if (patient.getPersonName() == null) {
			PersonName name = new PersonName();
			name.setPreferred(true);
			patient.addName(name);
		}
		
		// if all the standard attributes haven't been configured, configure them
		for (PersonAttributeType attr : Context.getPersonService().getPersonAttributeTypes(PERSON_TYPE.PATIENT, ATTR_VIEW_TYPE.VIEWING)) {
			if (attr != null && patient.getAttribute(attr) == null) {
				patient.addAttribute(new PersonAttribute(attr, null));
			}
		}
		
		return patient;
	}

	@SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.GET)
	public ModelAndView showForm(@RequestParam(required = false, value="patientId") Integer patientId,
	                             @RequestParam(required = false, value="addName") String addName,
	                             @RequestParam(required = false, value="addBirthdate") Date addBirthdate,
	                             @RequestParam(required = false, value="addAge") String addAge,
	                             @RequestParam(required = false, value="addGender") String addGender,
	                             @RequestParam(required = false, value="add") String add,
	                             /*@RequestParam(required = false, value="ob") String oblast,
	                             @RequestParam(required = false, value="c") String country,
	                             @RequestParam(required = false, value="loc") String district,
	                             */
	                             @RequestParam(required = false, value="skipSimilarCheck") Boolean skipSimilarCheck,
	                             ModelMap map) throws ParseException {
		
		System.out.println("Edit:show:add:" + add);
		
		/*List<Country> countries;
		List<Oblast> oblasts;
        List<Facility> facilities;
        List<District> districts;
		
		 if(country==null)
	      {
			 countries = Context.getService(MdrtbService.class).getCountries();
			 map.addAttribute("countries", countries);*/
			
			/*Oblast locOb = null;// = new Oblast();
			District locDist = null; // = new District();
			Facility locFac = null;// = new Facility();
			PatientIdentifier pi = Context.getService(MdrtbService.class)
					.getPatientIdentifierById(idId);
			if (pi != null) {
				prefix = pi.getIdentifier().substring(0, 2);
				prefix = "(" + prefix + ")";
				Location idLoc = null;
				List<Location> locList = Context.getLocationService()
						.getAllLocations(false);
				for (Location l : locList) {
					if (l.getName().trim().endsWith(prefix)) {
						idLoc = l;
						break;
					}
				}

				if (idLoc != null) {
					String obName = idLoc.getStateProvince();
					List<Oblast> obList = Context
							.getService(MdrtbService.class).getOblasts();
					for (Oblast o : obList) {
						if (obName != null && o.getName() != null
								&& o.getName().equals(obName)) {
							locOb = o;
							break;
						}
					}

					if (locOb != null && idLoc.getCountyDistrict() != null) {
						List<District> distList = Context.getService(
								MdrtbService.class).getDistricts(locOb.getId());
						for (District d : distList) {
							if (idLoc.getCountyDistrict().equals(d.getName())) {
								locDist = d;
								break;
							}
						}
					}

					if (locDist != null && idLoc.getRegion() != null) {
						List<Facility> facList = Context.getService(
								MdrtbService.class).getFacilities(
								locDist.getId());
						for (Facility f : facList) {
							if (idLoc.getRegion().equals(f.getName())) {
								locFac = f;
								break;
							}
						}
					}

				}
			}

			if (locOb != null) {
				oblasts = new ArrayList<Oblast>();
				oblasts.add(locOb);
				map.addAttribute("oblasts", oblasts);

				if (locDist != null) {
					districts = new ArrayList<District>();
					districts.add(locDist);
					map.addAttribute("districts", districts);

					if (locFac != null) {
						facilities = new ArrayList<Facility>();
						facilities.add(locFac);
						map.addAttribute("facilities", facilities);
					}
				}

				else {
					map.addAttribute(
							"districts",
							Context.getService(MdrtbService.class).getDistrict(
									locOb.getId()));
				}
			}

			else {
				oblasts = Context.getService(MdrtbService.class).getOblasts();
				map.addAttribute("oblasts", oblasts);
			}

		}*/
	        	
	        	
	        	
	        
		 
		 /*	else if(oblast==null) {
		 		countries = Context.getService(MdrtbService.class).getCountries();
		 		map.addAttribute("countrySelected", country);
		 		oblasts = Context.getService(MdrtbService.class).getOblasts(Integer.parseInt(country));
		 		
	            map.addAttribute("oblasts", oblasts);
	            map.addAttribute("countries", countries);
		 	}
	        
	       
	        else if(district==null)
	        { 
	        	countries = Context.getService(MdrtbService.class).getCountries();
		 		map.addAttribute("countrySelected", country);
		 		oblasts = Context.getService(MdrtbService.class).getOblasts(Integer.parseInt(country));
	        	districts= Context.getService(MdrtbService.class).getRegDistricts(Integer.parseInt(oblast));
	        	map.addAttribute("oblastSelected", oblast);
	        	
	            map.addAttribute("oblasts", oblasts);
	            map.addAttribute("districts", districts);
	            map.addAttribute("countries", countries);
	            
	        }
	        else
	        {
	        	countries = Context.getService(MdrtbService.class).getCountries();
		 		map.addAttribute("countrySelected", country);
		 		oblasts = Context.getService(MdrtbService.class).getOblasts(Integer.parseInt(country));
	        	districts= Context.getService(MdrtbService.class).getRegDistricts(Integer.parseInt(oblast));
	        	facilities = Context.getService(MdrtbService.class).getRegFacilities(Integer.parseInt(district));
	            map.addAttribute("oblastSelected", oblast);
	           
	            map.addAttribute("oblasts", oblasts);
	            map.addAttribute("districts", districts);
	            map.addAttribute("districtSelected", district);
	            map.addAttribute("facilities", facilities);
	            map.addAttribute("countries", countries);
	        }*/
		
		
		// if we are dealing with a new patient (one with no id, or id=-1) we need to check for similar patients first
	/*	if ((skipSimilarCheck == null || !skipSimilarCheck) && (patientId == null || patientId == -1)) {
			
			Integer birthYear = null;
			
			if (addBirthdate != null) {				
				Calendar birthDate = Calendar.getInstance();
				birthDate.setTime(addBirthdate);
				birthYear = birthDate.get(Calendar.YEAR);
			}
			else if (StringUtils.isNotBlank(addAge)) {
				Calendar currentDate = Calendar.getInstance();
				currentDate.setTime(new Date());
				birthYear = currentDate.get(Calendar.YEAR) - Integer.valueOf(addAge);
			}
			
			Set<Person> similarPersons = Context.getPersonService().getSimilarPeople(addName, birthYear, addGender);
			Set<PatientListItem> similarPatients = new HashSet<PatientListItem>();
	        String primaryIdentifier = Context.getAdministrationService().getGlobalProperty("mdrtb.primaryPatientIdentifierType");
			
			// we only want to pass on similar persons who are patients in this case
			for (Person person : similarPersons) {
				if (person instanceof Patient) {
					PatientListItem  patientListItem = new PatientListItem((Patient) person);
					
					// make sure the correct patient identifier is set on the patient list item
					if (StringUtils.isNotBlank(primaryIdentifier)) {
						if(((Patient) person).getPatientIdentifier(primaryIdentifier)!=null)
	                		patientListItem.setIdentifier(((Patient) person).getPatientIdentifier(primaryIdentifier).getIdentifier());
	                }
					
					
					similarPatients.add(patientListItem);
				}
			}
			
			if (similarPatients.size() > 0) {
				map.put("patients", similarPatients);
				
				// add the request params to the map so that we can pass them on
				map.put("addName", addName);
				map.put("addBirthdate", addBirthdate);
				map.put("addAge", addAge);
				map.put("addGender", addGender);
				map.put("add", add);
				
				return new ModelAndView("/module/mdrtb/similarPatients");
			}
		}*/
		 	map.put("addName", addName);
			map.put("addBirthdate", addBirthdate);
			map.put("addAge", addAge);
			map.put("addGender", addGender);
			map.put("add", add);
		
		// if no similar patients, show the edit page
		map.put("add", add);
		return new ModelAndView("/module/mdrtb/mdrtbEditPatient");
	}

	@SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST)
	public ModelAndView submitForm(@ModelAttribute("patient") Patient patient, BindingResult result,
	                               @RequestParam(required = false, value="identifierValue") String [] identifierValue,
	                               @RequestParam(required = false, value="identifierId") String [] identifierId, 
	                               @RequestParam(required = false, value = "identifierLocation") Location [] identifierLocation,
	                               @RequestParam(required = false, value = "identifierType") PatientIdentifierType [] identifierType,
	                               @RequestParam(required = false, value ="patientProgramId") Integer patientProgramId,
	                               /*@RequestParam(required = false, value ="givenName") String givenName,
	                               @RequestParam(required = false, value ="familyName") String familyName,
	                               @RequestParam(required = false, value="country") Integer countryId,
	                               @RequestParam(required = false, value="oblast") Integer oblastId,
	                               @RequestParam(required = true, value="district") Integer district,
	                               @RequestParam(required = false, value="facility") Integer facilityId,
	                               @RequestParam(required = false, value="address1") String address1,
	                               @RequestParam(required = false, value="address2") String address2,
	                               @RequestParam(required = false, value="otherCountry") String otherCountry,
	                               @RequestParam(required = false, value="otherOblast") String otherOblast,
	                               @RequestParam(required = true, value="otherDistrict") String otherdistrict,
	                               @RequestParam(required = false, value="otherFacility") String otherFacility,*/
	                               @RequestParam("successURL") String successUrl,
	                               @RequestParam("add") String add,
	                               SessionStatus status, ModelMap map) {
		

		// first, we need to set the patient id to null if it's been set to -1
		if (patient.getId() != null && patient.getId() == -1) {
			patient.setId(null);
		}
		
		// if a fixed patient identifier location has been set, get it
		Location fixedLocation = null;
		String fixedLocationName = Context.getAdministrationService().getGlobalProperty("mdrtb.fixedIdentifierLocation");
		if (StringUtils.isNotBlank(fixedLocationName)) {
			fixedLocation = Context.getLocationService().getLocation(fixedLocationName);
			if (fixedLocation == null) {
				throw new MdrtbAPIException("Location referenced by mdrtb.fixedIdentifierLocation global prop does not exist.");
			}
		}
		
		// handle patient identifiers
		if(identifierValue!=null) {
		for (Integer i=0; i<identifierValue.length; i++) {
			
			//  if this identifier is blank and the idgen module is installed, see if we need to auto-generate this identifier
			if (StringUtils.isBlank(identifierValue[i]) && ModuleFactory.getStartedModulesMap().containsKey("idgen")) {
				identifierValue[i] = MdrtbUtil.assignIdentifier(identifierType[i]);
			}
			
			// update any existing identifiers (ones with ids)
			if (StringUtils.isNotBlank(identifierId[i])) {
				PatientIdentifier identifier = getIdentifierById(Integer.valueOf(identifierId[i]), patient);
				
				// if there is a value, update it
				if (StringUtils.isNotBlank(identifierValue[i])) {
					identifier.setIdentifier(identifierValue[i]);
					
					// only update the location if it hasn't been set to be fixed
					if (fixedLocation == null) {
						identifier.setLocation(identifierLocation[i]);
					}
				}
				else {
					// otherwise, remove it
					patient.removeIdentifier(identifier);
				}
			}
			// now add any identifiers that have a value, but no id
			else if (StringUtils.isNotBlank(identifierValue[i])) {
				PatientIdentifier identifier = new PatientIdentifier(identifierValue[i], identifierType[i], (fixedLocation == null ? identifierLocation[i] : fixedLocation));
				
				// set this identifier as preferred if it is of the preferred tyoe
				String preferredIdentifierTypeName = Context.getAdministrationService().getGlobalProperty("mdrtb.primaryPatientIdentifierType");
				if (StringUtils.isNotBlank(preferredIdentifierTypeName)) {
					PatientIdentifierType preferredIdentifierType = Context.getPatientService().getPatientIdentifierTypeByName(preferredIdentifierTypeName);
					if (preferredIdentifierType != null && preferredIdentifierType == identifierType[i]) {
						identifier.setPreferred(true);
					}
				}
				
				patient.addIdentifier(identifier);	
			}
		}
		
		}
	
		
		
		// perform validation
		validator.validate(patient, result);
		if (result.hasErrors()) {
			map.put("errors", result);
			map.put("add", add);
			return new ModelAndView("/module/mdrtb/mdrtbEditPatient", map);
		}
		
		// sync up the patient and person voided attributes
		// TODO: is this correct... do we ever want to void a patient but keep the person (for instance, if the person is also a treatment supporter?)
		patient.setPersonVoided(patient.getVoided());
		patient.setPersonVoidReason(patient.getVoidReason());
		
		// remove the address if it is blank
		if (MdrtbUtil.isBlank(patient.getPersonAddress())) {
			patient.removeAddress(patient.getPersonAddress());
		}
		
		// remove any attributes that are blank
		for (PersonAttributeType attr : Context.getPersonService().getPersonAttributeTypes(PERSON_TYPE.PATIENT, ATTR_VIEW_TYPE.VIEWING)) {
			if (patient.getAttribute(attr) != null  && StringUtils.isBlank(patient.getAttribute(attr).getValue())) {
				patient.removeAttribute(patient.getAttribute(attr));
			}
		}
		
		// save the patient
		Context.getPatientService().savePatient(patient);
		
		// if the patient has been set to dead, exit him/her from care
		if (patient.getDead()) {
			Context.getService(MdrtbService.class).processDeath(patient, patient.getDeathDate(), 
				patient.getCauseOfDeath());
		}
		
		Integer idId = null;
		if(add!=null && add.equals("1"))
		{
			ArrayList<Patient> pats = new ArrayList<Patient>();
			pats.add(patient);
			idId = Context.getPatientService().getPatientIdentifiers(identifierValue[0], null, null, pats, null).get(0).getId();
		}
		
		// clears the command object from the session
		status.setComplete();
		map.clear();
		
		String returnUrl;
		
		if(add==null || add.length()==0) {
			returnUrl = "redirect:/module/mdrtb/program/enrollment.form?patientId="+patient.getId();
		}
		
		else {
			returnUrl = "redirect:" + successUrl + (successUrl.contains("?") ? "&" : "?") + "patientId=" + patient.getId() + 
			(patientProgramId != null ? "&patientProgramId=" + patientProgramId : "") + (idId != null ? "&idId=" + idId : "");
		}

		return new ModelAndView(returnUrl);
	}
	
	
	/**
	 * Utility methods
	 */
	
	private PatientIdentifier getIdentifierById(Integer id, Patient patient) {
		for (PatientIdentifier identifier : patient.getIdentifiers()) {
			if (identifier != null && identifier.getId() != null && identifier.getId().equals(id)) {
				return identifier;
			}
		}
		return null;
	}
}
