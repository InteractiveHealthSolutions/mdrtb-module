package org.openmrs.module.mdrtb.web.dwr;

import java.util.ArrayList;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.springframework.web.bind.annotation.RequestParam;

public class MdrtbAEDrugsDWR {

	
	public ArrayList<Concept> getRelevantDrugs(String regimen) {
		ArrayList<Concept> drugs = new ArrayList<Concept>();
		MdrtbService ms = Context.getService(MdrtbService.class);
		if(regimen == null || regimen.length()==0)
			return drugs;
		
		String drugArray[] = regimen.split("-");
		
		for(int i=0; i < drugArray.length; i++) {
			if(drugArray[i].equals("Cm")) {
				drugs.add(ms.getConcept(MdrtbConcepts.CAPREOMYCIN));
			}
			
			else if(drugArray[i].equals("Am")) {
				drugs.add(ms.getConcept(MdrtbConcepts.AMIKACIN));
			}
			
			else if(drugArray[i].equals("Mfx")) {
				drugs.add(ms.getConcept(MdrtbConcepts.MOXIFLOXACIN));
			}
			
			else if(drugArray[i].equals("Pto")) {
				drugs.add(ms.getConcept(MdrtbConcepts.PROTHIONAMIDE));
			}
			
			else if(drugArray[i].equals("Cs")) {
				drugs.add(ms.getConcept(MdrtbConcepts.CYCLOSERINE));
			}
			
			else if(drugArray[i].equals("PAS")) {
				drugs.add(ms.getConcept(MdrtbConcepts.P_AMINOSALICYLIC_ACID));
			}
			
			else if(drugArray[i].equals("Z")) {
				drugs.add(ms.getConcept(MdrtbConcepts.PYRAZINAMIDE));
			}
			
			else if(drugArray[i].equals("E")) {
				drugs.add(ms.getConcept(MdrtbConcepts.ETHAMBUTOL));
			}
			
			else if(drugArray[i].equals("H")) {
				if(!drugs.contains(MdrtbConcepts.ISONIAZID)) {
					drugs.add(ms.getConcept(MdrtbConcepts.ISONIAZID));
				}
			}
			
			else if(drugArray[i].equals("Lzd")) {
				drugs.add(ms.getConcept(MdrtbConcepts.LINEZOLID));
			}
			
			else if(drugArray[i].equals("Cfz")) {
				drugs.add(ms.getConcept(MdrtbConcepts.CLOFAZIMINE));
			}
			
			else if(drugArray[i].equals("Bdq")) {
				drugs.add(ms.getConcept(MdrtbConcepts.BEDAQUILINE));
			}
			
			else if(drugArray[i].equals("Dlm")) {
				drugs.add(ms.getConcept(MdrtbConcepts.DELAMANID));
			}
			
			else if(drugArray[i].equals("Imp/Clm")) {
				drugs.add(ms.getConcept(MdrtbConcepts.IMIPENEM));
			}
			
			else if(drugArray[i].equals("HR")) {
				if(!drugs.contains(MdrtbConcepts.ISONIAZID)) {
					drugs.add(ms.getConcept(MdrtbConcepts.ISONIAZID));
				}
				if(!drugs.contains(MdrtbConcepts.RIFAMPICIN)) {
					drugs.add(ms.getConcept(MdrtbConcepts.RIFAMPICIN));
				}
			}
			
			else if(drugArray[i].equals("HRZE")) {
				if(!drugs.contains(MdrtbConcepts.ISONIAZID)) {
					drugs.add(ms.getConcept(MdrtbConcepts.ISONIAZID));
				}
				if(!drugs.contains(MdrtbConcepts.RIFAMPICIN)) {
					drugs.add(ms.getConcept(MdrtbConcepts.RIFAMPICIN));
				}
				if(!drugs.contains(MdrtbConcepts.PYRAZINAMIDE)) {
					drugs.add(ms.getConcept(MdrtbConcepts.RIFAMPICIN));
				}
				if(!drugs.contains(MdrtbConcepts.ETHAMBUTOL)) {
					drugs.add(ms.getConcept(MdrtbConcepts.ETHAMBUTOL));
				}
			}
			
			else if(drugArray[i].equals("S")) {
				drugs.add(ms.getConcept(MdrtbConcepts.STREPTOMYCIN));
			}
			
			else if(drugArray[i].equals("Amx/Clv")) {
				drugs.add(ms.getConcept(MdrtbConcepts.AMOXICILLIN_AND_LAVULANIC_ACID));
			}
			
			
		}
		
		
		return drugs;
	}
	
	public ArrayList<String> getRelevantDrugsString(String regimen) {
		ArrayList<String> drugs = new ArrayList<String>();
		MdrtbService ms = Context.getService(MdrtbService.class);
		drugs.add(getOptionTag(null));
		if(regimen == null || regimen.length()==0)
			return drugs;
		
		String drugArray[] = regimen.split("-");
		
		
		
		
		for(int i=0; i < drugArray.length; i++) {
			if(drugArray[i].equals("Cm")) {
				drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.CAPREOMYCIN)));
			}
			
			else if(drugArray[i].equals("Am")) {
				drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.AMIKACIN)));
			}
			
			else if(drugArray[i].equals("Mfx")) {
				drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.MOXIFLOXACIN)));
			}
			
			else if(drugArray[i].equals("Pto")) {
				drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.PROTHIONAMIDE)));
			}
			
			else if(drugArray[i].equals("Cs")) {
				drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.CYCLOSERINE)));
			}
			
			else if(drugArray[i].equals("PAS")) {
				drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.P_AMINOSALICYLIC_ACID)));
			}
			
			else if(drugArray[i].equals("Z")) {
				drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.PYRAZINAMIDE)));
			}
			
			else if(drugArray[i].equals("E")) {
				drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.ETHAMBUTOL)));
			}
			
			else if(drugArray[i].equals("H")) {
				if(!drugs.contains(getOptionTag(ms.getConcept(MdrtbConcepts.ISONIAZID)))) {
					drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.ISONIAZID)));
				}
			}
			
			else if(drugArray[i].equals("Lzd")) {
				drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.LINEZOLID)));
			}
			
			else if(drugArray[i].equals("Cfz")) {
				drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.CLOFAZIMINE)));
			}
			
			else if(drugArray[i].equals("Bdq")) {
				drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.BEDAQUILINE)));
			}
			
			else if(drugArray[i].equals("Dlm")) {
				drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.DELAMANID)));
			}
			
			else if(drugArray[i].equals("Imp/Clm")) {
				drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.IMIPENEM)));
			}
			
			else if(drugArray[i].equals("HR")) {
				if(!drugs.contains(getOptionTag(ms.getConcept(MdrtbConcepts.ISONIAZID)))) {
					drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.ISONIAZID)));
				}
				if(!drugs.contains(getOptionTag(ms.getConcept(MdrtbConcepts.RIFAMPICIN)))) {
					drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.RIFAMPICIN)));
				}
			}
			
			else if(drugArray[i].equals("HRZE")) {
				if(!drugs.contains(getOptionTag(ms.getConcept(MdrtbConcepts.ISONIAZID)))) {
					drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.ISONIAZID)));
				}
				if(!drugs.contains(getOptionTag(ms.getConcept(MdrtbConcepts.RIFAMPICIN)))) {
					drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.RIFAMPICIN)));
				}
				if(!drugs.contains(getOptionTag(ms.getConcept(MdrtbConcepts.PYRAZINAMIDE)))) {
					drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.RIFAMPICIN)));
				}
				if(!drugs.contains(getOptionTag(ms.getConcept(MdrtbConcepts.ETHAMBUTOL)))) {
					drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.ETHAMBUTOL)));
				}
			}
			
			else if(drugArray[i].equals("S")) {
				drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.STREPTOMYCIN)));
			}
			
			else if(drugArray[i].equals("Amx/Clv")) {
				drugs.add(getOptionTag(ms.getConcept(MdrtbConcepts.AMOXICILLIN_AND_LAVULANIC_ACID)));
			}
			
			
		}
		
		
		return drugs;
	}
	
	private String getOptionTag(Concept c) {
		String tag = "";
		if(c==null) {
			return "<option value=\"\"></option>";
		}
		tag = "<option value=\"" + c.getId() + "\">" + c.getName().getName() + "</option>";
		
		return tag;
	}
}


