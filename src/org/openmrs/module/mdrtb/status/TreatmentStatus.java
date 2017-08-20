package org.openmrs.module.mdrtb.status;

import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.program.TbPatientProgram;


public class TreatmentStatus extends Status {

	public TreatmentStatus(MdrtbPatientProgram program) {
	    super(program);
    }
	
	public TreatmentStatus(TbPatientProgram program) {
	    super(program);
    }

	public StatusItem getTreatmentState() {
		return getItem("treatmentState");
	}
	
	public StatusItem getRegimens() {
		return getItem("regimens");
	}
	
}
