package org.openmrs.module.mdrtb.reporting;

import java.util.Date;

import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.TbConcepts;
import org.openmrs.module.mdrtb.MdrtbUtil;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.specimen.Dst;
import org.openmrs.module.mdrtb.specimen.HAIN;
import org.openmrs.module.mdrtb.specimen.Smear;
import org.openmrs.module.mdrtb.specimen.Culture;
import org.openmrs.module.mdrtb.specimen.Specimen;
import org.openmrs.module.mdrtb.specimen.Xpert;

public class TB07Util {

	
	/*public static Boolean isBacPositive(Patient patient) {
		Smear diagSmear = TB03Util.getDiagnosticSmear(patient);
		Xpert diagXpert = TB03Util.getFirstXpert(patient);
		HAIN diagHAIN = TB03Util.getFirstHAIN(patient);
		Culture diagCulture = TB03Util.getDiagnosticCulture(patient);
		
		Concept smearResult = null;
		Concept xpertResult = null;
		Concept hainResult = null;
		Concept	cultureResult = null;
		
		if(diagSmear != null)
			smearResult = diagSmear.getResult();
		
		if(diagXpert != null)
			xpertResult = diagXpert.getResult();
		
		if(diagHAIN != null)
			hainResult = diagHAIN.getResult();
		
		if(diagCulture != null)
			cultureResult = diagCulture.getResult();
		
		Integer [] positiveResultConceptIds = TbUtil.getPositiveResultConceptIds();
		
		for(int i=0; i<positiveResultConceptIds.length; i++) {
			if((smearResult!=null && smearResult.getConceptId().intValue()==positiveResultConceptIds[i].intValue()) || (cultureResult!=null && cultureResult.getConceptId().intValue()==positiveResultConceptIds[i].intValue()) || (xpertResult!=null && xpertResult.getConceptId().intValue()==positiveResultConceptIds[i].intValue()) || (hainResult!=null && hainResult.getConceptId().intValue()==positiveResultConceptIds[i].intValue())) {
				return true;
			}
		}
		
		
		return false;
	}*/
	
	/*public static Boolean isBacPositive(Patient patient), Date startDate, Date endDate) {
		Smear diagSmear = TB03Util.getDiagnosticSmear(patient);
		Xpert diagXpert = TB03Util.getFirstXpert(patient);
		HAIN diagHAIN = TB03Util.getFirstHAIN(patient);
		Culture diagCulture = TB03Util.getDiagnosticCulture(patient);
		
		Concept smearResult = null;
		Concept xpertResult = null;
		Concept hainResult = null;
		Concept	cultureResult = null;
		
		if(diagSmear != null)
			smearResult = diagSmear.getResult();
		
		if(diagXpert != null)
			xpertResult = diagXpert.getResult();
		
		if(diagHAIN != null)
			hainResult = diagHAIN.getResult();
		
		if(diagCulture != null)
			cultureResult = diagCulture.getResult();
		
		Integer [] positiveResultConceptIds = TbUtil.getPositiveResultConceptIds();
		
		for(int i=0; i<positiveResultConceptIds.length; i++) {
			if((smearResult!=null && smearResult.getConceptId().intValue()==positiveResultConceptIds[i].intValue()) || (cultureResult!=null && cultureResult.getConceptId().intValue()==positiveResultConceptIds[i].intValue()) || (xpertResult!=null && xpertResult.getConceptId().intValue()==positiveResultConceptIds[i].intValue()) || (hainResult!=null && hainResult.getConceptId().intValue()==positiveResultConceptIds[i].intValue())) {
				return true;
			}
		}
		
		
		return false;
	}*/
	
	
	
	
}
