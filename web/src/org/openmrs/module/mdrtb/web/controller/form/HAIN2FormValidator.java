package org.openmrs.module.mdrtb.web.controller.form;

import org.openmrs.module.mdrtb.form.HAIN2Form;
import org.openmrs.module.mdrtb.form.HAINForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


public class HAIN2FormValidator implements Validator {
	
	@SuppressWarnings("unchecked")
    public boolean supports(Class clazz) {
		return (HAIN2Form.class.isAssignableFrom(clazz) || HAIN2Form.class.isAssignableFrom(clazz));
    }

    public void validate(Object target, Errors errors) {   
	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "encounterDatetime", "mdrtb.form.errors.noEncounterDatetime", "Please specify a visit date.");
	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "mdrtb.form.errors.noLocation", "Please specify a location.");
	    ValidationUtils.rejectIfEmptyOrWhitespace(errors, "provider", "mdrtb.form.errors.noProvider", "Please specify a provider");
	    
	    if (errors.getFieldError("weight") != null) {
	    	errors.reject("mdrtb.form.errors.weight", "Weight must be a numeric value.");
	    }
	    
	    if (errors.getFieldError("temperature") != null) {
	    	errors.reject("mdrtb.form.errors.temperature", "Temperature must be a numeric value.");
	    }
	    
	    if (errors.getFieldError("pulse") != null) {
	    	errors.reject("mdrtb.form.errors.pulse", "Pulse must be a numeric value.");
	    }
	    
	    if (errors.getFieldError("systolicBloodPressure") != null) {
	    	errors.reject("mdrtb.form.errors.systolicBloodPressure", "Blood pressure must be a numeric value.");
	    }
	    
	    if (errors.getFieldError("respiratoryRate") != null) {
	    	errors.reject("mdrtb.form.errors.respiratoryRate", "Respiratory rate must be a numeric value.");
	    }
    }
}
