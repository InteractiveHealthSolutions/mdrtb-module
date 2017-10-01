package org.openmrs.module.mdrtb.status;

import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.program.TbPatientProgram;


public class VisitStatus extends Status {

	public VisitStatus(MdrtbPatientProgram program) {
	    super(program);
    }
	
	public VisitStatus(TbPatientProgram program) {
	    super(program);
    }

	public StatusItem getIntakeVisits() {
		return getItem("intakeVisits");
	}
	
	public StatusItem getFollowUpVisits() {
		return getItem("followUpVisits");
	}
	
	public StatusItem getScheduledFollowUpVisits() {
		return getItem("scheduledFollowUpVisits");
	}
	
	public StatusItem getSpecimenCollectionVisits() {
		return getItem("specimenCollectionVisits");
	}
	
	public StatusItem getTransferOutVisits() {
		return getItem("transferOutVisits");
	}
	
	public StatusItem getTransferInVisits() {
		return getItem("transferInVisits");
	}
	
	// used to store the link that should be used for a new intake visit
	public StatusItem getNewIntakeVisit() {
		return getItem("newIntakeVisit");
	}
	
	// used to store the link that should be used for the a new follow-up visit
	public StatusItem getNewFollowUpVisit() {
		return getItem("newFollowUpVisit");
	}
	
	// used to store the link that should be used for the a new follow-up visit
	public StatusItem getNewTransferOutVisit() {
			return getItem("newTransferOutVisit");
	}
	
	// used to store the link that should be used for the a new follow-up visit
	public StatusItem getNewTransferInVisit() {
			return getItem("newTransferInVisit");
	}
}
