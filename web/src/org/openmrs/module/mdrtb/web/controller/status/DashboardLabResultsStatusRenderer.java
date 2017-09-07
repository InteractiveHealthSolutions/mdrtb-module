package org.openmrs.module.mdrtb.web.controller.status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.MdrtbConstants;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtb.MdrtbConstants.TbClassification;
import org.openmrs.module.mdrtb.specimen.Culture;
import org.openmrs.module.mdrtb.specimen.Dst;
import org.openmrs.module.mdrtb.specimen.DstResult;
import org.openmrs.module.mdrtb.specimen.HAIN;
import org.openmrs.module.mdrtb.specimen.Smear;
import org.openmrs.module.mdrtb.specimen.Test;
import org.openmrs.module.mdrtb.specimen.SpecimenConstants.TestStatus;
import org.openmrs.module.mdrtb.specimen.Xpert;
import org.openmrs.module.mdrtb.status.LabResultsStatus;
import org.openmrs.module.mdrtb.status.LabResultsStatusRenderer;
import org.openmrs.module.mdrtb.status.StatusFlag;
import org.openmrs.module.mdrtb.status.StatusItem;

public class DashboardLabResultsStatusRenderer implements LabResultsStatusRenderer {
	
	private DateFormat df = new SimpleDateFormat(MdrtbConstants.DATE_FORMAT_DISPLAY, Context.getLocale());
	
	public void renderSmear(StatusItem item, LabResultsStatus status) {
		
		Smear smear = (Smear) item.getValue();
		
		if (smear != null) {
			String[] params = { smear.getResult().getBestShortName(Context.getLocale()).toString(),
			        smear.getDateCollected() != null ? df.format(smear.getDateCollected()) : "(N/A)",
			        smear.getLab() != null ? smear.getLab().getDisplayString() : "(N/A)" };
			
			if(status.getPatientProgram()!=null) {
			
				item.setLink("/module/mdrtb/form/smear.form?encounterId=" + smear.getSpecimenId() + "&patientProgramId=" + status.getPatientProgram().getId());
			}
			
			else {
				item.setLink("/module/mdrtb/form/smear.form?encounterId=" + smear.getSpecimenId() + "&patientProgramId=" + status.getPatientTbProgram().getId());
			}
			
			item.setDisplayString(Context.getMessageSourceService().getMessage("mdrtb.smearFormatter", params, "{0} on {1}, tested at {2}", Context.getLocale()));
			
			
		} else {
			item.setDisplayString(Context.getMessageSourceService().getMessage("mdrtb.none"));
			item.setLink(null);
		}
	}
	
	public void renderCulture(StatusItem item, LabResultsStatus status) {
		
		Culture culture = (Culture) item.getValue();
		
		if (culture != null) {
			String[] params = {
			        culture.getResult().getBestShortName(Context.getLocale()).toString(),
			        culture.getDateCollected() != null ? df.format(culture.getDateCollected()) : "(N/A)",
			        culture.getLab() != null ? culture.getLab().getDisplayString() : "(N/A)" };
			if(status.getPatientProgram()!=null) {
				item.setLink("/module/mdrtb/form/culture.form?encounterId=" + culture.getSpecimenId() + "&patientProgramId=" + status.getPatientProgram().getId());
			}
			
			else {
				item.setLink("/module/mdrtb/form/culture.form?encounterId=" + culture.getSpecimenId() + "&patientProgramId=" + status.getPatientTbProgram().getId());
			}
			item.setDisplayString(Context.getMessageSourceService().getMessage("mdrtb.cultureFormatter", params, "{0} on {1}, tested at {2}", Context.getLocale()));
		} else {
			item.setDisplayString(Context.getMessageSourceService().getMessage("mdrtb.none"));
			item.setLink(null);
		}
	}
	
	public void renderXpert(StatusItem item, LabResultsStatus status) {
		
		Xpert xpert = (Xpert) item.getValue();
		
		if (xpert != null) {
			String[] params = {
			        xpert.getResult().getBestShortName(Context.getLocale()).toString(),
			        xpert.getDateCollected() != null ? df.format(xpert.getDateCollected()) : "(N/A)",
			        xpert.getLab() != null ? xpert.getLab().getDisplayString() : "(N/A)" };
			
			if(status.getPatientProgram()!=null) {
				item.setLink("/module/mdrtb/form/xpert.form?encounterId=" + xpert.getSpecimenId() + "&patientProgramId=" + status.getPatientProgram().getId());
			}
			
			else {
				item.setLink("/module/mdrtb/form/xpert.form?encounterId=" + xpert.getSpecimenId() + "&patientProgramId=" + status.getPatientTbProgram().getId());
			}
			     
			item.setDisplayString(Context.getMessageSourceService().getMessage("mdrtb.xpertFormatter", params, "{0} on {1}, tested at {2}", Context.getLocale()));
		} else {
			item.setDisplayString(Context.getMessageSourceService().getMessage("mdrtb.none"));
			item.setLink(null);
		}
	}
	
	public void renderHAIN(StatusItem item, LabResultsStatus status) {
		
		HAIN hain = (HAIN) item.getValue();
		
		if (hain != null) {
			String[] params = {
					hain.getResult().getBestShortName(Context.getLocale()).toString(),
					hain.getDateCollected() != null ? df.format(hain.getDateCollected()) : "(N/A)",
							hain.getLab() != null ? hain.getLab().getDisplayString() : "(N/A)" };
			if(status.getPatientProgram()!=null) {
				item.setLink("/module/mdrtb/form/hain.form?encounterId=" + hain.getSpecimenId() + "&patientProgramId=" + status.getPatientProgram().getId());
			}
			
			else {
				item.setLink("/module/mdrtb/form/hain.form?encounterId=" + hain.getSpecimenId() + "&patientProgramId=" + status.getPatientTbProgram().getId());
			}
			     
			item.setDisplayString(Context.getMessageSourceService().getMessage("mdrtb.hainFormatter", params, "{0} on {1}, tested at {2}", Context.getLocale()));
		} else {
			item.setDisplayString(Context.getMessageSourceService().getMessage("mdrtb.none"));
			item.setLink(null);
		}
	
	}
	
	public void renderDst(StatusItem item, LabResultsStatus status) {
		
		Dst dst = (Dst) item.getValue();
		
		
		if (dst != null) {
			String[] params = {
					getDstResultString(dst),
					dst.getDateCollected() != null ? df.format(dst.getDateCollected()) : "(N/A)",
							dst.getLab() != null ? dst.getLab().getDisplayString() : "(N/A)" };
			if(status.getPatientProgram()!=null) {
				item.setLink("/module/mdrtb/form/dst.form?encounterId=" + dst.getSpecimenId() + "&patientProgramId=" + status.getPatientProgram().getId());
			}
			
			else {
				item.setLink("/module/mdrtb/form/dst.form?encounterId=" + dst.getSpecimenId() + "&patientProgramId=" + status.getPatientTbProgram().getId());
			}
			     
			item.setDisplayString(Context.getMessageSourceService().getMessage("mdrtb.dstFormatter", params, "{0} on {1}, tested at {2}", Context.getLocale()));
		} else {
			item.setDisplayString(Context.getMessageSourceService().getMessage("mdrtb.none"));
			item.setLink(null);
		}
	
	}
	
	
    public String renderDrugResistanceProfile(List<Concept> drugs) {
    	String drugList = DashboardStatusRendererUtil.renderDrugList(drugs);
    	
    	if (StringUtils.isBlank(drugList)) {
    		drugList = Context.getMessageSourceService().getMessage("mdrtb.unknown");
    	}
    	
    	return drugList;
    }
   
    
    
	
	/*@SuppressWarnings("unchecked")
    public void renderPendingLabResults(StatusItem pendingLabResults, LabResultsStatus status) {		
		List<StatusItem> tests = (List<StatusItem>) pendingLabResults.getValue();
		
		for (StatusItem item : tests) {
			Test test = (Test) item.getValue();
			TestStatus testStatus = test.getStatus();
			
			item.setLink("/module/mdrtb/specimen/specimen.form?specimenId=" 
						+ test.getSpecimenId() + "&testId=" + test.getId() + "&patientProgramId=" + status.getPatientProgram().getId());
			 
			// get the test type and capitalize the first character
			String testType = Character.toUpperCase(test.getTestType().charAt(0)) + test.getTestType().substring(1);
			
			if (testStatus == TestStatus.STARTED) {
				String[] params = { test.getLab().getDisplayString(), df.format(test.getStartDate()), testType };
				item.setDisplayString(Context.getMessageSourceService().getMessage("mdrtb.labResultsStatus.started", params,
				    "{2} started on {1} at {0}", Context.getLocale()));
			} else if (testStatus == TestStatus.RECEIVED) {
				String[] params = { test.getLab().getDisplayString(), df.format(test.getDateReceived()), testType };
				item.setDisplayString(Context.getMessageSourceService().getMessage("mdrtb.labResultsStatus.received", params,
				    "{2} received by {0} at {1}", Context.getLocale()));
			} else if (testStatus == TestStatus.ORDERED) {
				String[] params = { test.getLab().getDisplayString(), df.format(test.getDateOrdered()), testType };
				item.setDisplayString(Context.getMessageSourceService().getMessage("mdrtb.labResultsStatus.ordered", params,
				    "{2} ordered on {1} from {0}", Context.getLocale()));
			} else {
				String[] params = { testType };
				item.setDisplayString(Context.getMessageSourceService().getMessage("mdrtb.labResultsStatus.unknown", params,
				    "{0} with status unknown", Context.getLocale()));
			}
		}
	}*/

    public String renderTbClassification(TbClassification classification) {
	   
    	StringBuffer displayString = new StringBuffer();
    	displayString.append(Context.getMessageSourceService().getMessage("mdrtb.confirmed") + " ");
	    
	    if (classification == TbClassification.MONO_RESISTANT_TB) {
	    	displayString.append(Context.getMessageSourceService().getMessage("mdrtb.monoResistantTb"));
	    } 
	    else if (classification == TbClassification.POLY_RESISTANT_TB){
	    	displayString.append(Context.getMessageSourceService().getMessage("mdrtb.polyResistantTb"));
	    } 
	    else if (classification == TbClassification.MDR_TB) {
	    	displayString.append(Context.getMessageSourceService().getMessage("mdrtb.mdrtb"));
	    }
	    else if (classification == TbClassification.XDR_TB) {
	    	displayString.append(Context.getMessageSourceService().getMessage("mdrtb.xdrtb"));
	    }
	    else {
	    	displayString = new StringBuffer();
	    	displayString.append(Context.getMessageSourceService().getMessage("mdrtb.unknown"));
	    }
	    
	    return displayString.toString();
    }
	
    public String renderAnatomicalSite(StatusItem anatomicalStatus) {
    	Concept anatomicalSite = (Concept) anatomicalStatus.getValue();
    	
    	if (anatomicalSite == null) {
    		return Context.getMessageSourceService().getMessage("mdrtb.unknown");
    	}
    	else if (anatomicalSite.equals(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.PULMONARY_TB))) {
    		return Context.getMessageSourceService().getMessage("mdrtb.pulmonary");
    	}
    	else if (anatomicalSite.equals(Context.getService(MdrtbService.class).getConcept(MdrtbConcepts.EXTRA_PULMONARY_TB))) {
    		return Context.getMessageSourceService().getMessage("mdrtb.extrapulmonary");
    	}
    	else {
    		return Context.getMessageSourceService().getMessage("mdrtb.unknown");
    	}
    }
    
    public String renderConversion(StatusItem cultureConversion) {
	   if ( ((Boolean) cultureConversion.getValue()) == false) {
		   return Context.getMessageSourceService().getMessage("mdrtb.notConverted");
	   }
	   else {
		   String[] params = {df.format(cultureConversion.getDate()) };
		   return Context.getMessageSourceService().getMessage("mdrtb.converted", params,
			    "Converted on {0}", Context.getLocale());
	   }
    }
    
	public StatusFlag createNoSmearsFlag() {
		StatusFlag flag = new StatusFlag();
		flag.setMessage(Context.getMessageSourceService().getMessage("mdrtb.noSmearResults"));
		return flag;
	}
	
	public StatusFlag createNoCulturesFlag() {
		StatusFlag flag = new StatusFlag();
		flag.setMessage(Context.getMessageSourceService().getMessage("mdrtb.noCultureResults"));
		return flag;
	}
	
	public String getDstResultString(Dst dst) {
		
		
		String results = "";
		Map<Integer, List<DstResult>> dstResultsMap = dst.getResultsMap();
		Collection<Concept> drugs = getPossibleDrugTypes();
		
		for(Concept drug : drugs) {
			if(dstResultsMap.get(drug.getId())!=null) {
				for(DstResult result : dstResultsMap.get(drug.getId())) {
					results += result.getDrug().getDisplayString() + ": " + result.getResult().getShortNameInLocale(Context.getLocale()) + "<br/>";
					
				}
			}
		}
		
		if(results.length()==0) {
			results = "N/A";
		}
		
		return results;
	}
	
	public Collection<Concept> getPossibleDrugTypes() {
		return Context.getService(MdrtbService.class).getMdrtbDrugs();
	}

}
