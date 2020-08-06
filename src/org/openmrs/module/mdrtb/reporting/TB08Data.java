package org.openmrs.module.mdrtb.reporting;

import java.util.HashMap;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;

public class TB08Data {
	
	private Integer newPulmonaryBCDetected;
	private Integer newPulmonaryBCDetected04;
	private Integer newPulmonaryBCDetected0514;
	private Integer newPulmonaryBCDetected1517;
	
	private Integer newPulmonaryCDDetected;
	private Integer newPulmonaryCDDetected04;
	private Integer newPulmonaryCDDetected0514;
	private Integer newPulmonaryCDDetected1517;
	
	private Integer newExtrapulmonaryDetected;
	private Integer newExtrapulmonaryDetected04;
	private Integer newExtrapulmonaryDetected0514;
	private Integer newExtrapulmonaryDetected1517;
	
	private Integer newAllDetected;
	private Integer newAllDetected04;
	private Integer newAllDetected0514;
	private Integer newAllDetected1517;
	
	private Integer newPulmonaryBCEligible;
	private Integer newPulmonaryBCEligible04;
	private Integer newPulmonaryBCEligible0514;
	private Integer newPulmonaryBCEligible1517;
	
	private Integer newPulmonaryCDEligible;
	private Integer newPulmonaryCDEligible04;
	private Integer newPulmonaryCDEligible0514;
	private Integer newPulmonaryCDEligible1517;
	
	private Integer newExtrapulmonaryEligible;
	private Integer newExtrapulmonaryEligible04;
	private Integer newExtrapulmonaryEligible0514;
	private Integer newExtrapulmonaryEligible1517;
	
	private Integer newAllEligible;
	private Integer newAllEligible04;
	private Integer newAllEligible0514;
	private Integer newAllEligible1517;
	
	private Integer newPulmonaryBCCured;
	private Integer newPulmonaryBCCured04;
	private Integer newPulmonaryBCCured0514;
	private Integer newPulmonaryBCCured1517;
	
	private Integer newPulmonaryCDCured;
	private Integer newPulmonaryCDCured04;
	private Integer newPulmonaryCDCured0514;
	private Integer newPulmonaryCDCured1517;
	
	private Integer newExtrapulmonaryCured;
	private Integer newExtrapulmonaryCured04;
	private Integer newExtrapulmonaryCured0514;
	private Integer newExtrapulmonaryCured1517;
	
	private Integer newAllCured;
	private Integer newAllCured04;
	private Integer newAllCured0514;
	private Integer newAllCured1517;
	
	private Integer newPulmonaryBCCompleted;
	private Integer newPulmonaryBCCompleted04;
	private Integer newPulmonaryBCCompleted0514;
	private Integer newPulmonaryBCCompleted1517;
	
	private Integer newPulmonaryCDCompleted;
	private Integer newPulmonaryCDCompleted04;
	private Integer newPulmonaryCDCompleted0514;
	private Integer newPulmonaryCDCompleted1517;
	
	private Integer newExtrapulmonaryCompleted;
	private Integer newExtrapulmonaryCompleted04;
	private Integer newExtrapulmonaryCompleted0514;
	private Integer newExtrapulmonaryCompleted1517;
	
	private Integer newAllCompleted;
	private Integer newAllCompleted04;
	private Integer newAllCompleted0514;
	private Integer newAllCompleted1517;
	
	private Integer newPulmonaryBCDiedTB;
	private Integer newPulmonaryBCDiedTB04;
	private Integer newPulmonaryBCDiedTB0514;
	private Integer newPulmonaryBCDiedTB1517;
	
	private Integer newPulmonaryCDDiedTB;
	private Integer newPulmonaryCDDiedTB04;
	private Integer newPulmonaryCDDiedTB0514;
	private Integer newPulmonaryCDDiedTB1517;
	
	private Integer newExtrapulmonaryDiedTB;
	private Integer newExtrapulmonaryDiedTB04;
	private Integer newExtrapulmonaryDiedTB0514;
	private Integer newExtrapulmonaryDiedTB1517;
	
	private Integer newAllDiedTB;
	private Integer newAllDiedTB04;
	private Integer newAllDiedTB0514;
	private Integer newAllDiedTB1517;
	
	private Integer newPulmonaryBCDiedNotTB;
	private Integer newPulmonaryBCDiedNotTB04;
	private Integer newPulmonaryBCDiedNotTB0514;
	private Integer newPulmonaryBCDiedNotTB1517;
	
	private Integer newPulmonaryCDDiedNotTB;
	private Integer newPulmonaryCDDiedNotTB04;
	private Integer newPulmonaryCDDiedNotTB0514;
	private Integer newPulmonaryCDDiedNotTB1517;
	
	private Integer newExtrapulmonaryDiedNotTB;
	private Integer newExtrapulmonaryDiedNotTB04;
	private Integer newExtrapulmonaryDiedNotTB0514;
	private Integer newExtrapulmonaryDiedNotTB1517;
	
	private Integer newAllDiedNotTB;
	private Integer newAllDiedNotTB04;
	private Integer newAllDiedNotTB0514;
	private Integer newAllDiedNotTB1517;
	
	private Integer newPulmonaryBCFailed;
	private Integer newPulmonaryBCFailed04;
	private Integer newPulmonaryBCFailed0514;
	private Integer newPulmonaryBCFailed1517;
	
	private Integer newPulmonaryCDFailed;
	private Integer newPulmonaryCDFailed04;
	private Integer newPulmonaryCDFailed0514;
	private Integer newPulmonaryCDFailed1517;
	
	private Integer newExtrapulmonaryFailed;
	private Integer newExtrapulmonaryFailed04;
	private Integer newExtrapulmonaryFailed0514;
	private Integer newExtrapulmonaryFailed1517;
	
	private Integer newAllFailed;
	private Integer newAllFailed04;
	private Integer newAllFailed0514;
	private Integer newAllFailed1517;
	
	private Integer newPulmonaryBCDefaulted;
	private Integer newPulmonaryBCDefaulted04;
	private Integer newPulmonaryBCDefaulted0514;
	private Integer newPulmonaryBCDefaulted1517;
	
	private Integer newPulmonaryCDDefaulted;
	private Integer newPulmonaryCDDefaulted04;
	private Integer newPulmonaryCDDefaulted0514;
	private Integer newPulmonaryCDDefaulted1517;
	
	private Integer newExtrapulmonaryDefaulted;
	private Integer newExtrapulmonaryDefaulted04;
	private Integer newExtrapulmonaryDefaulted0514;
	private Integer newExtrapulmonaryDefaulted1517;
	
	private Integer newAllDefaulted;
	private Integer newAllDefaulted04;
	private Integer newAllDefaulted0514;
	private Integer newAllDefaulted1517;
	
	private Integer newPulmonaryBCTransferOut;
	private Integer newPulmonaryBCTransferOut04;
	private Integer newPulmonaryBCTransferOut0514;
	private Integer newPulmonaryBCTransferOut1517;
	
	private Integer newPulmonaryCDTransferOut;
	private Integer newPulmonaryCDTransferOut04;
	private Integer newPulmonaryCDTransferOut0514;
	private Integer newPulmonaryCDTransferOut1517;
	
	private Integer newExtrapulmonaryTransferOut;
	private Integer newExtrapulmonaryTransferOut04;
	private Integer newExtrapulmonaryTransferOut0514;
	private Integer newExtrapulmonaryTransferOut1517;
	
	private Integer newAllTransferOut;
	private Integer newAllTransferOut04;
	private Integer newAllTransferOut0514;
	private Integer newAllTransferOut1517;
	
	private Integer newPulmonaryBCCanceled;
	private Integer newPulmonaryBCCanceled04;
	private Integer newPulmonaryBCCanceled0514;
	private Integer newPulmonaryBCCanceled1517;
	
	private Integer newPulmonaryCDCanceled;
	private Integer newPulmonaryCDCanceled04;
	private Integer newPulmonaryCDCanceled0514;
	private Integer newPulmonaryCDCanceled1517;
	
	private Integer newExtrapulmonaryCanceled;
	private Integer newExtrapulmonaryCanceled04;
	private Integer newExtrapulmonaryCanceled0514;
	private Integer newExtrapulmonaryCanceled1517;
	
	private Integer newAllCanceled;
	private Integer newAllCanceled04;
	private Integer newAllCanceled0514;
	private Integer newAllCanceled1517;
	
	private Integer newPulmonaryBCSLD;
	private Integer newPulmonaryBCSLD04;
	private Integer newPulmonaryBCSLD0514;
	private Integer newPulmonaryBCSLD1517;
	
	private Integer newPulmonaryCDSLD;
	private Integer newPulmonaryCDSLD04;
	private Integer newPulmonaryCDSLD0514;
	private Integer newPulmonaryCDSLD1517;
	
	private Integer newExtrapulmonarySLD;
	private Integer newExtrapulmonarySLD04;
	private Integer newExtrapulmonarySLD0514;
	private Integer newExtrapulmonarySLD1517;
	
	private Integer newAllSLD;
	private Integer newAllSLD04;
	private Integer newAllSLD0514;
	private Integer newAllSLD1517;
	
	//RELAPSE
	private Integer relapsePulmonaryBCDetected;
	private Integer relapsePulmonaryBCDetected04;
	private Integer relapsePulmonaryBCDetected0514;
	private Integer relapsePulmonaryBCDetected1517;
	
	private Integer relapsePulmonaryCDDetected;
	private Integer relapsePulmonaryCDDetected04;
	private Integer relapsePulmonaryCDDetected0514;
	private Integer relapsePulmonaryCDDetected1517;
	
	private Integer relapseExtrapulmonaryDetected;
	private Integer relapseExtrapulmonaryDetected04;
	private Integer relapseExtrapulmonaryDetected0514;
	private Integer relapseExtrapulmonaryDetected1517;
	
	private Integer relapseAllDetected;
	private Integer relapseAllDetected04;
	private Integer relapseAllDetected0514;
	private Integer relapseAllDetected1517;
	
	private Integer relapsePulmonaryBCEligible;
	private Integer relapsePulmonaryBCEligible04;
	private Integer relapsePulmonaryBCEligible0514;
	private Integer relapsePulmonaryBCEligible1517;
	
	private Integer relapsePulmonaryCDEligible;
	private Integer relapsePulmonaryCDEligible04;
	private Integer relapsePulmonaryCDEligible0514;
	private Integer relapsePulmonaryCDEligible1517;
	
	private Integer relapseExtrapulmonaryEligible;
	private Integer relapseExtrapulmonaryEligible04;
	private Integer relapseExtrapulmonaryEligible0514;
	private Integer relapseExtrapulmonaryEligible1517;
	
	private Integer relapseAllEligible;
	private Integer relapseAllEligible04;
	private Integer relapseAllEligible0514;
	private Integer relapseAllEligible1517;
	
	private Integer relapsePulmonaryBCCured;
	private Integer relapsePulmonaryBCCured04;
	private Integer relapsePulmonaryBCCured0514;
	private Integer relapsePulmonaryBCCured1517;
	
	private Integer relapsePulmonaryCDCured;
	private Integer relapsePulmonaryCDCured04;
	private Integer relapsePulmonaryCDCured0514;
	private Integer relapsePulmonaryCDCured1517;
	
	private Integer relapseExtrapulmonaryCured;
	private Integer relapseExtrapulmonaryCured04;
	private Integer relapseExtrapulmonaryCured0514;
	private Integer relapseExtrapulmonaryCured1517;
	
	private Integer relapseAllCured;
	private Integer relapseAllCured04;
	private Integer relapseAllCured0514;
	private Integer relapseAllCured1517;
	
	private Integer relapsePulmonaryBCCompleted;
	private Integer relapsePulmonaryBCCompleted04;
	private Integer relapsePulmonaryBCCompleted0514;
	private Integer relapsePulmonaryBCCompleted1517;
	
	private Integer relapsePulmonaryCDCompleted;
	private Integer relapsePulmonaryCDCompleted04;
	private Integer relapsePulmonaryCDCompleted0514;
	private Integer relapsePulmonaryCDCompleted1517;
	
	private Integer relapseExtrapulmonaryCompleted;
	private Integer relapseExtrapulmonaryCompleted04;
	private Integer relapseExtrapulmonaryCompleted0514;
	private Integer relapseExtrapulmonaryCompleted1517;
	
	private Integer relapseAllCompleted;
	private Integer relapseAllCompleted04;
	private Integer relapseAllCompleted0514;
	private Integer relapseAllCompleted1517;
	
	private Integer relapsePulmonaryBCDiedTB;
	private Integer relapsePulmonaryBCDiedTB04;
	private Integer relapsePulmonaryBCDiedTB0514;
	private Integer relapsePulmonaryBCDiedTB1517;
	
	private Integer relapsePulmonaryCDDiedTB;
	private Integer relapsePulmonaryCDDiedTB04;
	private Integer relapsePulmonaryCDDiedTB0514;
	private Integer relapsePulmonaryCDDiedTB1517;
	
	private Integer relapseExtrapulmonaryDiedTB;
	private Integer relapseExtrapulmonaryDiedTB04;
	private Integer relapseExtrapulmonaryDiedTB0514;
	private Integer relapseExtrapulmonaryDiedTB1517;
	
	private Integer relapseAllDiedTB;
	private Integer relapseAllDiedTB04;
	private Integer relapseAllDiedTB0514;
	private Integer relapseAllDiedTB1517;
	
	private Integer relapsePulmonaryBCDiedNotTB;
	private Integer relapsePulmonaryBCDiedNotTB04;
	private Integer relapsePulmonaryBCDiedNotTB0514;
	private Integer relapsePulmonaryBCDiedNotTB1517;
	
	private Integer relapsePulmonaryCDDiedNotTB;
	private Integer relapsePulmonaryCDDiedNotTB04;
	private Integer relapsePulmonaryCDDiedNotTB0514;
	private Integer relapsePulmonaryCDDiedNotTB1517;
	
	private Integer relapseExtrapulmonaryDiedNotTB;
	private Integer relapseExtrapulmonaryDiedNotTB04;
	private Integer relapseExtrapulmonaryDiedNotTB0514;
	private Integer relapseExtrapulmonaryDiedNotTB1517;
	
	private Integer relapseAllDiedNotTB;
	private Integer relapseAllDiedNotTB04;
	private Integer relapseAllDiedNotTB0514;
	private Integer relapseAllDiedNotTB1517;
	
	private Integer relapsePulmonaryBCFailed;
	private Integer relapsePulmonaryBCFailed04;
	private Integer relapsePulmonaryBCFailed0514;
	private Integer relapsePulmonaryBCFailed1517;
	
	private Integer relapsePulmonaryCDFailed;
	private Integer relapsePulmonaryCDFailed04;
	private Integer relapsePulmonaryCDFailed0514;
	private Integer relapsePulmonaryCDFailed1517;
	
	private Integer relapseExtrapulmonaryFailed;
	private Integer relapseExtrapulmonaryFailed04;
	private Integer relapseExtrapulmonaryFailed0514;
	private Integer relapseExtrapulmonaryFailed1517;
	
	private Integer relapseAllFailed;
	private Integer relapseAllFailed04;
	private Integer relapseAllFailed0514;
	private Integer relapseAllFailed1517;
	
	private Integer relapsePulmonaryBCDefaulted;
	private Integer relapsePulmonaryBCDefaulted04;
	private Integer relapsePulmonaryBCDefaulted0514;
	private Integer relapsePulmonaryBCDefaulted1517;
	
	private Integer relapsePulmonaryCDDefaulted;
	private Integer relapsePulmonaryCDDefaulted04;
	private Integer relapsePulmonaryCDDefaulted0514;
	private Integer relapsePulmonaryCDDefaulted1517;
	
	private Integer relapseExtrapulmonaryDefaulted;
	private Integer relapseExtrapulmonaryDefaulted04;
	private Integer relapseExtrapulmonaryDefaulted0514;
	private Integer relapseExtrapulmonaryDefaulted1517;
	
	private Integer relapseAllDefaulted;
	private Integer relapseAllDefaulted04;
	private Integer relapseAllDefaulted0514;
	private Integer relapseAllDefaulted1517;
	
	private Integer relapsePulmonaryBCTransferOut;
	private Integer relapsePulmonaryBCTransferOut04;
	private Integer relapsePulmonaryBCTransferOut0514;
	private Integer relapsePulmonaryBCTransferOut1517;
	
	private Integer relapsePulmonaryCDTransferOut;
	private Integer relapsePulmonaryCDTransferOut04;
	private Integer relapsePulmonaryCDTransferOut0514;
	private Integer relapsePulmonaryCDTransferOut1517;
	
	private Integer relapseExtrapulmonaryTransferOut;
	private Integer relapseExtrapulmonaryTransferOut04;
	private Integer relapseExtrapulmonaryTransferOut0514;
	private Integer relapseExtrapulmonaryTransferOut1517;
	
	private Integer relapseAllTransferOut;
	private Integer relapseAllTransferOut04;
	private Integer relapseAllTransferOut0514;
	private Integer relapseAllTransferOut1517;
	
	private Integer relapsePulmonaryBCCanceled;
	private Integer relapsePulmonaryBCCanceled04;
	private Integer relapsePulmonaryBCCanceled0514;
	private Integer relapsePulmonaryBCCanceled1517;
	
	private Integer relapsePulmonaryCDCanceled;
	private Integer relapsePulmonaryCDCanceled04;
	private Integer relapsePulmonaryCDCanceled0514;
	private Integer relapsePulmonaryCDCanceled1517;
	
	private Integer relapseExtrapulmonaryCanceled;
	private Integer relapseExtrapulmonaryCanceled04;
	private Integer relapseExtrapulmonaryCanceled0514;
	private Integer relapseExtrapulmonaryCanceled1517;
	
	private Integer relapseAllCanceled;
	private Integer relapseAllCanceled04;
	private Integer relapseAllCanceled0514;
	private Integer relapseAllCanceled1517;
	
	private Integer relapsePulmonaryBCSLD;
	private Integer relapsePulmonaryBCSLD04;
	private Integer relapsePulmonaryBCSLD0514;
	private Integer relapsePulmonaryBCSLD1517;
	
	private Integer relapsePulmonaryCDSLD;
	private Integer relapsePulmonaryCDSLD04;
	private Integer relapsePulmonaryCDSLD0514;
	private Integer relapsePulmonaryCDSLD1517;
	
	private Integer relapseExtrapulmonarySLD;
	private Integer relapseExtrapulmonarySLD04;
	private Integer relapseExtrapulmonarySLD0514;
	private Integer relapseExtrapulmonarySLD1517;
	
	private Integer relapseAllSLD;
	private Integer relapseAllSLD04;
	private Integer relapseAllSLD0514;
	private Integer relapseAllSLD1517;
	
	//FAILURE
	private Integer failurePulmonaryBCDetected;
	private Integer failurePulmonaryCDDetected;
	private Integer failureExtrapulmonaryDetected;
	private Integer failureAllDetected;
	private Integer failurePulmonaryBCEligible;
	private Integer failurePulmonaryCDEligible;
	private Integer failureExtrapulmonaryEligible;
	private Integer failureAllEligible;
	private Integer failurePulmonaryBCCured;
	private Integer failurePulmonaryCDCured;
	private Integer failureExtrapulmonaryCured;
	private Integer failureAllCured;
	private Integer failurePulmonaryBCCompleted;
	private Integer failurePulmonaryCDCompleted;
	private Integer failureExtrapulmonaryCompleted;
	private Integer failureAllCompleted;
	private Integer failurePulmonaryBCDiedTB;
	private Integer failurePulmonaryCDDiedTB;
	private Integer failureExtrapulmonaryDiedTB;
	private Integer failureAllDiedTB;
	private Integer failurePulmonaryBCDiedNotTB;
	private Integer failurePulmonaryCDDiedNotTB;
	private Integer failureExtrapulmonaryDiedNotTB;
	private Integer failureAllDiedNotTB;
	private Integer failurePulmonaryBCFailed;
	private Integer failurePulmonaryCDFailed;
	private Integer failureExtrapulmonaryFailed;
	private Integer failureAllFailed;
	private Integer failurePulmonaryBCDefaulted;
	private Integer failurePulmonaryCDDefaulted;
	private Integer failureExtrapulmonaryDefaulted;
	private Integer failureAllDefaulted;
	private Integer failurePulmonaryBCTransferOut;
	private Integer failurePulmonaryCDTransferOut;
	private Integer failureExtrapulmonaryTransferOut;
	private Integer failureAllTransferOut;
	private Integer failurePulmonaryBCCanceled;
	private Integer failurePulmonaryCDCanceled;
	private Integer failureExtrapulmonaryCanceled;
	private Integer failureAllCanceled;
	private Integer failurePulmonaryBCSLD;
	private Integer failurePulmonaryCDSLD;
	private Integer failureExtrapulmonarySLD;
	private Integer failureAllSLD;
	
	//DEFAULT
	private Integer defaultPulmonaryBCDetected;
	private Integer defaultPulmonaryCDDetected;
	private Integer defaultExtrapulmonaryDetected;
	private Integer defaultAllDetected;
	private Integer defaultPulmonaryBCEligible;
	private Integer defaultPulmonaryCDEligible;
	private Integer defaultExtrapulmonaryEligible;
	private Integer defaultAllEligible;
	private Integer defaultPulmonaryBCCured;
	private Integer defaultPulmonaryCDCured;
	private Integer defaultExtrapulmonaryCured;
	private Integer defaultAllCured;
	private Integer defaultPulmonaryBCCompleted;
	private Integer defaultPulmonaryCDCompleted;
	private Integer defaultExtrapulmonaryCompleted;
	private Integer defaultAllCompleted;
	private Integer defaultPulmonaryBCDiedTB;
	private Integer defaultPulmonaryCDDiedTB;
	private Integer defaultExtrapulmonaryDiedTB;
	private Integer defaultAllDiedTB;
	private Integer defaultPulmonaryBCDiedNotTB;
	private Integer defaultPulmonaryCDDiedNotTB;
	private Integer defaultExtrapulmonaryDiedNotTB;
	private Integer defaultAllDiedNotTB;
	private Integer defaultPulmonaryBCFailed;
	private Integer defaultPulmonaryCDFailed;
	private Integer defaultExtrapulmonaryFailed;
	private Integer defaultAllFailed;
	private Integer defaultPulmonaryBCDefaulted;
	private Integer defaultPulmonaryCDDefaulted;
	private Integer defaultExtrapulmonaryDefaulted;
	private Integer defaultAllDefaulted;
	private Integer defaultPulmonaryBCTransferOut;
	private Integer defaultPulmonaryCDTransferOut;
	private Integer defaultExtrapulmonaryTransferOut;
	private Integer defaultAllTransferOut;
	private Integer defaultPulmonaryBCCanceled;
	private Integer defaultPulmonaryCDCanceled;
	private Integer defaultExtrapulmonaryCanceled;
	private Integer defaultAllCanceled;
	private Integer defaultPulmonaryBCSLD;
	private Integer defaultPulmonaryCDSLD;
	private Integer defaultExtrapulmonarySLD;
	private Integer defaultAllSLD;
	
	//OTHER
	private Integer otherPulmonaryBCDetected;
	private Integer otherPulmonaryCDDetected;
	private Integer otherExtrapulmonaryDetected;
	private Integer otherAllDetected;
	private Integer otherPulmonaryBCEligible;
	private Integer otherPulmonaryCDEligible;
	private Integer otherExtrapulmonaryEligible;
	private Integer otherAllEligible;
	private Integer otherPulmonaryBCCured;
	private Integer otherPulmonaryCDCured;
	private Integer otherExtrapulmonaryCured;
	private Integer otherAllCured;
	private Integer otherPulmonaryBCCompleted;
	private Integer otherPulmonaryCDCompleted;
	private Integer otherExtrapulmonaryCompleted;
	private Integer otherAllCompleted;
	private Integer otherPulmonaryBCDiedTB;
	private Integer otherPulmonaryCDDiedTB;
	private Integer otherExtrapulmonaryDiedTB;
	private Integer otherAllDiedTB;
	private Integer otherPulmonaryBCDiedNotTB;
	private Integer otherPulmonaryCDDiedNotTB;
	private Integer otherExtrapulmonaryDiedNotTB;
	private Integer otherAllDiedNotTB;
	private Integer otherPulmonaryBCFailed;
	private Integer otherPulmonaryCDFailed;
	private Integer otherExtrapulmonaryFailed;
	private Integer otherAllFailed;
	private Integer otherPulmonaryBCDefaulted;
	private Integer otherPulmonaryCDDefaulted;
	private Integer otherExtrapulmonaryDefaulted;
	private Integer otherAllDefaulted;
	private Integer otherPulmonaryBCTransferOut;
	private Integer otherPulmonaryCDTransferOut;
	private Integer otherExtrapulmonaryTransferOut;
	private Integer otherAllTransferOut;
	private Integer otherPulmonaryBCCanceled;
	private Integer otherPulmonaryCDCanceled;
	private Integer otherExtrapulmonaryCanceled;
	private Integer otherAllCanceled;
	private Integer otherPulmonaryBCSLD;
	private Integer otherPulmonaryCDSLD;
	private Integer otherExtrapulmonarySLD;
	private Integer otherAllSLD;
	
	//RE-TREATMENT
	private Integer rtxPulmonaryBCDetected;
	private Integer rtxPulmonaryCDDetected;
	private Integer rtxExtrapulmonaryDetected;
	private Integer rtxAllDetected;
	private Integer rtxPulmonaryBCEligible;
	private Integer rtxPulmonaryCDEligible;
	private Integer rtxExtrapulmonaryEligible;
	private Integer rtxAllEligible;
	private Integer rtxPulmonaryBCCured;
	private Integer rtxPulmonaryCDCured;
	private Integer rtxExtrapulmonaryCured;
	private Integer rtxAllCured;
	private Integer rtxPulmonaryBCCompleted;
	private Integer rtxPulmonaryCDCompleted;
	private Integer rtxExtrapulmonaryCompleted;
	private Integer rtxAllCompleted;
	private Integer rtxPulmonaryBCDiedTB;
	private Integer rtxPulmonaryCDDiedTB;
	private Integer rtxExtrapulmonaryDiedTB;
	private Integer rtxAllDiedTB;
	private Integer rtxPulmonaryBCDiedNotTB;
	private Integer rtxPulmonaryCDDiedNotTB;
	private Integer rtxExtrapulmonaryDiedNotTB;
	private Integer rtxAllDiedNotTB;
	private Integer rtxPulmonaryBCFailed;
	private Integer rtxPulmonaryCDFailed;
	private Integer rtxExtrapulmonaryFailed;
	private Integer rtxAllFailed;
	private Integer rtxPulmonaryBCDefaulted;
	private Integer rtxPulmonaryCDDefaulted;
	private Integer rtxExtrapulmonaryDefaulted;
	private Integer rtxAllDefaulted;
	private Integer rtxPulmonaryBCTransferOut;
	private Integer rtxPulmonaryCDTransferOut;
	private Integer rtxExtrapulmonaryTransferOut;
	private Integer rtxAllTransferOut;
	private Integer rtxPulmonaryBCCanceled;
	private Integer rtxPulmonaryCDCanceled;
	private Integer rtxExtrapulmonaryCanceled;
	private Integer rtxAllCanceled;
	private Integer rtxPulmonaryBCSLD;
	private Integer rtxPulmonaryCDSLD;
	private Integer rtxExtrapulmonarySLD;
	private Integer rtxAllSLD;
	
	private Integer allDetected;
	private Integer allEligible;
	private Integer allCured;
	private Integer allCompleted;
	private Integer allDiedTB;
	private Integer allDiedNotTB;
	private Integer allFailed;
	private Integer allDefaulted;
	private Integer allTransferOut;
	private Integer allCanceled;
	private Integer allSLD;
	
	public TB08Data() {
		 newPulmonaryBCDetected = 0;
		 newPulmonaryBCDetected04 = 0;
		 newPulmonaryBCDetected0514 = 0;
		 newPulmonaryBCDetected1517 = 0;
		
		 newPulmonaryCDDetected = 0;
		 newPulmonaryCDDetected04 = 0;
		 newPulmonaryCDDetected0514 = 0;
		 newPulmonaryCDDetected1517 = 0;
		
		 newExtrapulmonaryDetected = 0;
		 newExtrapulmonaryDetected04 = 0;
		 newExtrapulmonaryDetected0514 = 0;
		 newExtrapulmonaryDetected1517 = 0;
		
		 newAllDetected = 0;
		 newAllDetected04 = 0;
		 newAllDetected0514 = 0;
		 newAllDetected1517 = 0;
		
		 newPulmonaryBCEligible = 0;
		 newPulmonaryBCEligible04 = 0;
		 newPulmonaryBCEligible0514 = 0;
		 newPulmonaryBCEligible1517 = 0;
		
		 newPulmonaryCDEligible = 0;
		 newPulmonaryCDEligible04 = 0;
		 newPulmonaryCDEligible0514 = 0;
		 newPulmonaryCDEligible1517 = 0;
		
		 newExtrapulmonaryEligible = 0;
		 newExtrapulmonaryEligible04 = 0;
		 newExtrapulmonaryEligible0514 = 0;
		 newExtrapulmonaryEligible1517 = 0;
		
		 newAllEligible = 0;
		 newAllEligible04 = 0;
		 newAllEligible0514 = 0;
		 newAllEligible1517 = 0;
		
		 newPulmonaryBCCured = 0;
		 newPulmonaryBCCured04 = 0;
		 newPulmonaryBCCured0514 = 0;
		 newPulmonaryBCCured1517 = 0;
		
		 newPulmonaryCDCured = 0;
		 newPulmonaryCDCured04 = 0;
		 newPulmonaryCDCured0514 = 0;
		 newPulmonaryCDCured1517 = 0;
		
		 newExtrapulmonaryCured = 0;
		 newExtrapulmonaryCured04 = 0;
		 newExtrapulmonaryCured0514 = 0;
		 newExtrapulmonaryCured1517 = 0;
		
		 newAllCured = 0;
		 newAllCured04 = 0;
		 newAllCured0514 = 0;
		 newAllCured1517 = 0;
		
		 newPulmonaryBCCompleted = 0;
		 newPulmonaryBCCompleted04 = 0;
		 newPulmonaryBCCompleted0514 = 0;
		 newPulmonaryBCCompleted1517 = 0;
		
		 newPulmonaryCDCompleted = 0;
		 newPulmonaryCDCompleted04 = 0;
		 newPulmonaryCDCompleted0514 = 0;
		 newPulmonaryCDCompleted1517 = 0;
		
		 newExtrapulmonaryCompleted = 0;
		 newExtrapulmonaryCompleted04 = 0;
		 newExtrapulmonaryCompleted0514 = 0;
		 newExtrapulmonaryCompleted1517 = 0;
		
		 newAllCompleted = 0;
		 newAllCompleted04 = 0;
		 newAllCompleted0514 = 0;
		 newAllCompleted1517 = 0;
		
		 newPulmonaryBCDiedTB = 0;
		 newPulmonaryBCDiedTB04 = 0;
		 newPulmonaryBCDiedTB0514 = 0;
		 newPulmonaryBCDiedTB1517 = 0;
		
		 newPulmonaryCDDiedTB = 0;
		 newPulmonaryCDDiedTB04 = 0;
		 newPulmonaryCDDiedTB0514 = 0;
		 newPulmonaryCDDiedTB1517 = 0;
		
		 newExtrapulmonaryDiedTB = 0;
		 newExtrapulmonaryDiedTB04 = 0;
		 newExtrapulmonaryDiedTB0514 = 0;
		 newExtrapulmonaryDiedTB1517 = 0;
		
		 newAllDiedTB = 0;
		 newAllDiedTB04 = 0;
		 newAllDiedTB0514 = 0;
		 newAllDiedTB1517 = 0;
		
		 newPulmonaryBCDiedNotTB = 0;
		 newPulmonaryBCDiedNotTB04 = 0;
		 newPulmonaryBCDiedNotTB0514 = 0;
		 newPulmonaryBCDiedNotTB1517 = 0;
		
		 newPulmonaryCDDiedNotTB = 0;
		 newPulmonaryCDDiedNotTB04 = 0;
		 newPulmonaryCDDiedNotTB0514 = 0;
		 newPulmonaryCDDiedNotTB1517 = 0;
		
		 newExtrapulmonaryDiedNotTB = 0;
		 newExtrapulmonaryDiedNotTB04 = 0;
		 newExtrapulmonaryDiedNotTB0514 = 0;
		 newExtrapulmonaryDiedNotTB1517 = 0;
		
		 newAllDiedNotTB = 0;
		 newAllDiedNotTB04 = 0;
		 newAllDiedNotTB0514 = 0;
		 newAllDiedNotTB1517 = 0;
		
		 newPulmonaryBCFailed = 0;
		 newPulmonaryBCFailed04 = 0;
		 newPulmonaryBCFailed0514 = 0;
		 newPulmonaryBCFailed1517 = 0;
		
		 newPulmonaryCDFailed = 0;
		 newPulmonaryCDFailed04 = 0;
		 newPulmonaryCDFailed0514 = 0;
		 newPulmonaryCDFailed1517 = 0;
		
		 newExtrapulmonaryFailed = 0;
		 newExtrapulmonaryFailed04 = 0;
		 newExtrapulmonaryFailed0514 = 0;
		 newExtrapulmonaryFailed1517 = 0;
		
		 newAllFailed = 0;
		 newAllFailed04 = 0;
		 newAllFailed0514 = 0;
		 newAllFailed1517 = 0;
		
		 newPulmonaryBCDefaulted = 0;
		 newPulmonaryBCDefaulted04 = 0;
		 newPulmonaryBCDefaulted0514 = 0;
		 newPulmonaryBCDefaulted1517 = 0;
		
		 newPulmonaryCDDefaulted = 0;
		 newPulmonaryCDDefaulted04 = 0;
		 newPulmonaryCDDefaulted0514 = 0;
		 newPulmonaryCDDefaulted1517 = 0;
		
		 newExtrapulmonaryDefaulted = 0;
		 newExtrapulmonaryDefaulted04 = 0;
		 newExtrapulmonaryDefaulted0514 = 0;
		 newExtrapulmonaryDefaulted1517 = 0;
		
		 newAllDefaulted = 0;
		 newAllDefaulted04 = 0;
		 newAllDefaulted0514 = 0;
		 newAllDefaulted1517 = 0;
		
		 newPulmonaryBCTransferOut = 0;
		 newPulmonaryBCTransferOut04 = 0;
		 newPulmonaryBCTransferOut0514 = 0;
		 newPulmonaryBCTransferOut1517 = 0;
		
		 newPulmonaryCDTransferOut = 0;
		 newPulmonaryCDTransferOut04 = 0;
		 newPulmonaryCDTransferOut0514 = 0;
		 newPulmonaryCDTransferOut1517 = 0;
		
		 newExtrapulmonaryTransferOut = 0;
		 newExtrapulmonaryTransferOut04 = 0;
		 newExtrapulmonaryTransferOut0514 = 0;
		 newExtrapulmonaryTransferOut1517 = 0;
		
		 newAllTransferOut = 0;
		 newAllTransferOut04 = 0;
		 newAllTransferOut0514 = 0;
		 newAllTransferOut1517 = 0;
		
		 newPulmonaryBCCanceled = 0;
		 newPulmonaryBCCanceled04 = 0;
		 newPulmonaryBCCanceled0514 = 0;
		 newPulmonaryBCCanceled1517 = 0;
		
		 newPulmonaryCDCanceled = 0;
		 newPulmonaryCDCanceled04 = 0;
		 newPulmonaryCDCanceled0514 = 0;
		 newPulmonaryCDCanceled1517 = 0;
		
		 newExtrapulmonaryCanceled = 0;
		 newExtrapulmonaryCanceled04 = 0;
		 newExtrapulmonaryCanceled0514 = 0;
		 newExtrapulmonaryCanceled1517 = 0;
		
		 newAllCanceled = 0;
		 newAllCanceled04 = 0;
		 newAllCanceled0514 = 0;
		 newAllCanceled1517 = 0;
		
		 newPulmonaryBCSLD = 0;
		 newPulmonaryBCSLD04 = 0;
		 newPulmonaryBCSLD0514 = 0;
		 newPulmonaryBCSLD1517 = 0;
		
		 newPulmonaryCDSLD = 0;
		 newPulmonaryCDSLD04 = 0;
		 newPulmonaryCDSLD0514 = 0;
		 newPulmonaryCDSLD1517 = 0;
		
		 newExtrapulmonarySLD = 0;
		 newExtrapulmonarySLD04 = 0;
		 newExtrapulmonarySLD0514 = 0;
		 newExtrapulmonarySLD1517 = 0;
		
		 newAllSLD = 0;
		 newAllSLD04 = 0;
		 newAllSLD0514 = 0;
		 newAllSLD1517 = 0;
		
		//RELAPSE
		 relapsePulmonaryBCDetected = 0;
		 relapsePulmonaryBCDetected04 = 0;
		 relapsePulmonaryBCDetected0514 = 0;
		 relapsePulmonaryBCDetected1517 = 0;
		
		 relapsePulmonaryCDDetected = 0;
		 relapsePulmonaryCDDetected04 = 0;
		 relapsePulmonaryCDDetected0514 = 0;
		 relapsePulmonaryCDDetected1517 = 0;
		
		 relapseExtrapulmonaryDetected = 0;
		 relapseExtrapulmonaryDetected04 = 0;
		 relapseExtrapulmonaryDetected0514 = 0;
		 relapseExtrapulmonaryDetected1517 = 0;
		
		 relapseAllDetected = 0;
		 relapseAllDetected04 = 0;
		 relapseAllDetected0514 = 0;
		 relapseAllDetected1517 = 0;
		
		 relapsePulmonaryBCEligible = 0;
		 relapsePulmonaryBCEligible04 = 0;
		 relapsePulmonaryBCEligible0514 = 0;
		 relapsePulmonaryBCEligible1517 = 0;
		
		 relapsePulmonaryCDEligible = 0;
		 relapsePulmonaryCDEligible04 = 0;
		 relapsePulmonaryCDEligible0514 = 0;
		 relapsePulmonaryCDEligible1517 = 0;
		
		 relapseExtrapulmonaryEligible = 0;
		 relapseExtrapulmonaryEligible04 = 0;
		 relapseExtrapulmonaryEligible0514 = 0;
		 relapseExtrapulmonaryEligible1517 = 0;
		
		 relapseAllEligible = 0;
		 relapseAllEligible04 = 0;
		 relapseAllEligible0514 = 0;
		 relapseAllEligible1517 = 0;
		
		 relapsePulmonaryBCCured = 0;
		 relapsePulmonaryBCCured04 = 0;
		 relapsePulmonaryBCCured0514 = 0;
		 relapsePulmonaryBCCured1517 = 0;
		
		 relapsePulmonaryCDCured = 0;
		 relapsePulmonaryCDCured04 = 0;
		 relapsePulmonaryCDCured0514 = 0;
		 relapsePulmonaryCDCured1517 = 0;
		
		 relapseExtrapulmonaryCured = 0;
		 relapseExtrapulmonaryCured04 = 0;
		 relapseExtrapulmonaryCured0514 = 0;
		 relapseExtrapulmonaryCured1517 = 0;
		
		 relapseAllCured = 0;
		 relapseAllCured04 = 0;
		 relapseAllCured0514 = 0;
		 relapseAllCured1517 = 0;
		
		 relapsePulmonaryBCCompleted = 0;
		 relapsePulmonaryBCCompleted04 = 0;
		 relapsePulmonaryBCCompleted0514 = 0;
		 relapsePulmonaryBCCompleted1517 = 0;
		
		 relapsePulmonaryCDCompleted = 0;
		 relapsePulmonaryCDCompleted04 = 0;
		 relapsePulmonaryCDCompleted0514 = 0;
		 relapsePulmonaryCDCompleted1517 = 0;
		
		 relapseExtrapulmonaryCompleted = 0;
		 relapseExtrapulmonaryCompleted04 = 0;
		 relapseExtrapulmonaryCompleted0514 = 0;
		 relapseExtrapulmonaryCompleted1517 = 0;
		
		 relapseAllCompleted = 0;
		 relapseAllCompleted04 = 0;
		 relapseAllCompleted0514 = 0;
		 relapseAllCompleted1517 = 0;
		
		 relapsePulmonaryBCDiedTB = 0;
		 relapsePulmonaryBCDiedTB04 = 0;
		 relapsePulmonaryBCDiedTB0514 = 0;
		 relapsePulmonaryBCDiedTB1517 = 0;
		
		 relapsePulmonaryCDDiedTB = 0;
		 relapsePulmonaryCDDiedTB04 = 0;
		 relapsePulmonaryCDDiedTB0514 = 0;
		 relapsePulmonaryCDDiedTB1517 = 0;
		
		 relapseExtrapulmonaryDiedTB = 0;
		 relapseExtrapulmonaryDiedTB04 = 0;
		 relapseExtrapulmonaryDiedTB0514 = 0;
		 relapseExtrapulmonaryDiedTB1517 = 0;
		
		 relapseAllDiedTB = 0;
		 relapseAllDiedTB04 = 0;
		 relapseAllDiedTB0514 = 0;
		 relapseAllDiedTB1517 = 0;
		
		 relapsePulmonaryBCDiedNotTB = 0;
		 relapsePulmonaryBCDiedNotTB04 = 0;
		 relapsePulmonaryBCDiedNotTB0514 = 0;
		 relapsePulmonaryBCDiedNotTB1517 = 0;
		
		 relapsePulmonaryCDDiedNotTB = 0;
		 relapsePulmonaryCDDiedNotTB04 = 0;
		 relapsePulmonaryCDDiedNotTB0514 = 0;
		 relapsePulmonaryCDDiedNotTB1517 = 0;
		
		 relapseExtrapulmonaryDiedNotTB = 0;
		 relapseExtrapulmonaryDiedNotTB04 = 0;
		 relapseExtrapulmonaryDiedNotTB0514 = 0;
		 relapseExtrapulmonaryDiedNotTB1517 = 0;
		
		 relapseAllDiedNotTB = 0;
		 relapseAllDiedNotTB04 = 0;
		 relapseAllDiedNotTB0514 = 0;
		 relapseAllDiedNotTB1517 = 0;
		
		 relapsePulmonaryBCFailed = 0;
		 relapsePulmonaryBCFailed04 = 0;
		 relapsePulmonaryBCFailed0514 = 0;
		 relapsePulmonaryBCFailed1517 = 0;
		
		 relapsePulmonaryCDFailed = 0;
		 relapsePulmonaryCDFailed04 = 0;
		 relapsePulmonaryCDFailed0514 = 0;
		 relapsePulmonaryCDFailed1517 = 0;
		
		 relapseExtrapulmonaryFailed = 0;
		 relapseExtrapulmonaryFailed04 = 0;
		 relapseExtrapulmonaryFailed0514 = 0;
		 relapseExtrapulmonaryFailed1517 = 0;
		
		 relapseAllFailed = 0;
		 relapseAllFailed04 = 0;
		 relapseAllFailed0514 = 0;
		 relapseAllFailed1517 = 0;
		
		 relapsePulmonaryBCDefaulted = 0;
		 relapsePulmonaryBCDefaulted04 = 0;
		 relapsePulmonaryBCDefaulted0514 = 0;
		 relapsePulmonaryBCDefaulted1517 = 0;
		
		 relapsePulmonaryCDDefaulted = 0;
		 relapsePulmonaryCDDefaulted04 = 0;
		 relapsePulmonaryCDDefaulted0514 = 0;
		 relapsePulmonaryCDDefaulted1517 = 0;
		
		 relapseExtrapulmonaryDefaulted = 0;
		 relapseExtrapulmonaryDefaulted04 = 0;
		 relapseExtrapulmonaryDefaulted0514 = 0;
		 relapseExtrapulmonaryDefaulted1517 = 0;
		
		 relapseAllDefaulted = 0;
		 relapseAllDefaulted04 = 0;
		 relapseAllDefaulted0514 = 0;
		 relapseAllDefaulted1517 = 0;
		
		 relapsePulmonaryBCTransferOut = 0;
		 relapsePulmonaryBCTransferOut04 = 0;
		 relapsePulmonaryBCTransferOut0514 = 0;
		 relapsePulmonaryBCTransferOut1517 = 0;
		
		 relapsePulmonaryCDTransferOut = 0;
		 relapsePulmonaryCDTransferOut04 = 0;
		 relapsePulmonaryCDTransferOut0514 = 0;
		 relapsePulmonaryCDTransferOut1517 = 0;
		
		 relapseExtrapulmonaryTransferOut = 0;
		 relapseExtrapulmonaryTransferOut04 = 0;
		 relapseExtrapulmonaryTransferOut0514 = 0;
		 relapseExtrapulmonaryTransferOut1517 = 0;
		
		 relapseAllTransferOut = 0;
		 relapseAllTransferOut04 = 0;
		 relapseAllTransferOut0514 = 0;
		 relapseAllTransferOut1517 = 0;
		
		 relapsePulmonaryBCCanceled = 0;
		 relapsePulmonaryBCCanceled04 = 0;
		 relapsePulmonaryBCCanceled0514 = 0;
		 relapsePulmonaryBCCanceled1517 = 0;
		
		 relapsePulmonaryCDCanceled = 0;
		 relapsePulmonaryCDCanceled04 = 0;
		 relapsePulmonaryCDCanceled0514 = 0;
		 relapsePulmonaryCDCanceled1517 = 0;
		
		 relapseExtrapulmonaryCanceled = 0;
		 relapseExtrapulmonaryCanceled04 = 0;
		 relapseExtrapulmonaryCanceled0514 = 0;
		 relapseExtrapulmonaryCanceled1517 = 0;
		
		 relapseAllCanceled = 0;
		 relapseAllCanceled04 = 0;
		 relapseAllCanceled0514 = 0;
		 relapseAllCanceled1517 = 0;
		
		 relapsePulmonaryBCSLD = 0;
		 relapsePulmonaryBCSLD04 = 0;
		 relapsePulmonaryBCSLD0514 = 0;
		 relapsePulmonaryBCSLD1517 = 0;
		
		 relapsePulmonaryCDSLD = 0;
		 relapsePulmonaryCDSLD04 = 0;
		 relapsePulmonaryCDSLD0514 = 0;
		 relapsePulmonaryCDSLD1517 = 0;
		
		 relapseExtrapulmonarySLD = 0;
		 relapseExtrapulmonarySLD04 = 0;
		 relapseExtrapulmonarySLD0514 = 0;
		 relapseExtrapulmonarySLD1517 = 0;
		
		 relapseAllSLD = 0;
		 relapseAllSLD04 = 0;
		 relapseAllSLD0514 = 0;
		 relapseAllSLD1517 = 0;
		
		//FAILURE
		 failurePulmonaryBCDetected = 0;
		 failurePulmonaryCDDetected = 0;
		 failureExtrapulmonaryDetected = 0;
		 failureAllDetected = 0;
		 failurePulmonaryBCEligible = 0;
		 failurePulmonaryCDEligible = 0;
		 failureExtrapulmonaryEligible = 0;
		 failureAllEligible = 0;
		 failurePulmonaryBCCured = 0;
		 failurePulmonaryCDCured = 0;
		 failureExtrapulmonaryCured = 0;
		 failureAllCured = 0;
		 failurePulmonaryBCCompleted = 0;
		 failurePulmonaryCDCompleted = 0;
		 failureExtrapulmonaryCompleted = 0;
		 failureAllCompleted = 0;
		 failurePulmonaryBCDiedTB = 0;
		 failurePulmonaryCDDiedTB = 0;
		 failureExtrapulmonaryDiedTB = 0;
		 failureAllDiedTB = 0;
		 failurePulmonaryBCDiedNotTB = 0;
		 failurePulmonaryCDDiedNotTB = 0;
		 failureExtrapulmonaryDiedNotTB = 0;
		 failureAllDiedNotTB = 0;
		 failurePulmonaryBCFailed = 0;
		 failurePulmonaryCDFailed = 0;
		 failureExtrapulmonaryFailed = 0;
		 failureAllFailed = 0;
		 failurePulmonaryBCDefaulted = 0;
		 failurePulmonaryCDDefaulted = 0;
		 failureExtrapulmonaryDefaulted = 0;
		 failureAllDefaulted = 0;
		 failurePulmonaryBCTransferOut = 0;
		 failurePulmonaryCDTransferOut = 0;
		 failureExtrapulmonaryTransferOut = 0;
		 failureAllTransferOut = 0;
		 failurePulmonaryBCCanceled = 0;
		 failurePulmonaryCDCanceled = 0;
		 failureExtrapulmonaryCanceled = 0;
		 failureAllCanceled = 0;
		 failurePulmonaryBCSLD = 0;
		 failurePulmonaryCDSLD = 0;
		 failureExtrapulmonarySLD = 0;
		 failureAllSLD = 0;
		
		//DEFAULT
		 defaultPulmonaryBCDetected = 0;
		 defaultPulmonaryCDDetected = 0;
		 defaultExtrapulmonaryDetected = 0;
		 defaultAllDetected = 0;
		 defaultPulmonaryBCEligible = 0;
		 defaultPulmonaryCDEligible = 0;
		 defaultExtrapulmonaryEligible = 0;
		 defaultAllEligible = 0;
		 defaultPulmonaryBCCured = 0;
		 defaultPulmonaryCDCured = 0;
		 defaultExtrapulmonaryCured = 0;
		 defaultAllCured = 0;
		 defaultPulmonaryBCCompleted = 0;
		 defaultPulmonaryCDCompleted = 0;
		 defaultExtrapulmonaryCompleted = 0;
		 defaultAllCompleted = 0;
		 defaultPulmonaryBCDiedTB = 0;
		 defaultPulmonaryCDDiedTB = 0;
		 defaultExtrapulmonaryDiedTB = 0;
		 defaultAllDiedTB = 0;
		 defaultPulmonaryBCDiedNotTB = 0;
		 defaultPulmonaryCDDiedNotTB = 0;
		 defaultExtrapulmonaryDiedNotTB = 0;
		 defaultAllDiedNotTB = 0;
		 defaultPulmonaryBCFailed = 0;
		 defaultPulmonaryCDFailed = 0;
		 defaultExtrapulmonaryFailed = 0;
		 defaultAllFailed = 0;
		 defaultPulmonaryBCDefaulted = 0;
		 defaultPulmonaryCDDefaulted = 0;
		 defaultExtrapulmonaryDefaulted = 0;
		 defaultAllDefaulted = 0;
		 defaultPulmonaryBCTransferOut = 0;
		 defaultPulmonaryCDTransferOut = 0;
		 defaultExtrapulmonaryTransferOut = 0;
		 defaultAllTransferOut = 0;
		 defaultPulmonaryBCCanceled = 0;
		 defaultPulmonaryCDCanceled = 0;
		 defaultExtrapulmonaryCanceled = 0;
		 defaultAllCanceled = 0;
		 defaultPulmonaryBCSLD = 0;
		 defaultPulmonaryCDSLD = 0;
		 defaultExtrapulmonarySLD = 0;
		 defaultAllSLD = 0;
		
		//OTHER
		 otherPulmonaryBCDetected = 0;
		 otherPulmonaryCDDetected = 0;
		 otherExtrapulmonaryDetected = 0;
		 otherAllDetected = 0;
		 otherPulmonaryBCEligible = 0;
		 otherPulmonaryCDEligible = 0;
		 otherExtrapulmonaryEligible = 0;
		 otherAllEligible = 0;
		 otherPulmonaryBCCured = 0;
		 otherPulmonaryCDCured = 0;
		 otherExtrapulmonaryCured = 0;
		 otherAllCured = 0;
		 otherPulmonaryBCCompleted = 0;
		 otherPulmonaryCDCompleted = 0;
		 otherExtrapulmonaryCompleted = 0;
		 otherAllCompleted = 0;
		 otherPulmonaryBCDiedTB = 0;
		 otherPulmonaryCDDiedTB = 0;
		 otherExtrapulmonaryDiedTB = 0;
		 otherAllDiedTB = 0;
		 otherPulmonaryBCDiedNotTB = 0;
		 otherPulmonaryCDDiedNotTB = 0;
		 otherExtrapulmonaryDiedNotTB = 0;
		 otherAllDiedNotTB = 0;
		 otherPulmonaryBCFailed = 0;
		 otherPulmonaryCDFailed = 0;
		 otherExtrapulmonaryFailed = 0;
		 otherAllFailed = 0;
		 otherPulmonaryBCDefaulted = 0;
		 otherPulmonaryCDDefaulted = 0;
		 otherExtrapulmonaryDefaulted = 0;
		 otherAllDefaulted = 0;
		 otherPulmonaryBCTransferOut = 0;
		 otherPulmonaryCDTransferOut = 0;
		 otherExtrapulmonaryTransferOut = 0;
		 otherAllTransferOut = 0;
		 otherPulmonaryBCCanceled = 0;
		 otherPulmonaryCDCanceled = 0;
		 otherExtrapulmonaryCanceled = 0;
		 otherAllCanceled = 0;
		 otherPulmonaryBCSLD = 0;
		 otherPulmonaryCDSLD = 0;
		 otherExtrapulmonarySLD = 0;
		 otherAllSLD = 0;
		
		//RE-TREATMENT
		 rtxPulmonaryBCDetected = 0;
		 rtxPulmonaryCDDetected = 0;
		 rtxExtrapulmonaryDetected = 0;
		 rtxAllDetected = 0;
		 rtxPulmonaryBCEligible = 0;
		 rtxPulmonaryCDEligible = 0;
		 rtxExtrapulmonaryEligible = 0;
		 rtxAllEligible = 0;
		 rtxPulmonaryBCCured = 0;
		 rtxPulmonaryCDCured = 0;
		 rtxExtrapulmonaryCured = 0;
		 rtxAllCured = 0;
		 rtxPulmonaryBCCompleted = 0;
		 rtxPulmonaryCDCompleted = 0;
		 rtxExtrapulmonaryCompleted = 0;
		 rtxAllCompleted = 0;
		 rtxPulmonaryBCDiedTB = 0;
		 rtxPulmonaryCDDiedTB = 0;
		 rtxExtrapulmonaryDiedTB = 0;
		 rtxAllDiedTB = 0;
		 rtxPulmonaryBCDiedNotTB = 0;
		 rtxPulmonaryCDDiedNotTB = 0;
		 rtxExtrapulmonaryDiedNotTB = 0;
		 rtxAllDiedNotTB = 0;
		 rtxPulmonaryBCFailed = 0;
		 rtxPulmonaryCDFailed = 0;
		 rtxExtrapulmonaryFailed = 0;
		 rtxAllFailed = 0;
		 rtxPulmonaryBCDefaulted = 0;
		 rtxPulmonaryCDDefaulted = 0;
		 rtxExtrapulmonaryDefaulted = 0;
		 rtxAllDefaulted = 0;
		 rtxPulmonaryBCTransferOut = 0;
		 rtxPulmonaryCDTransferOut = 0;
		 rtxExtrapulmonaryTransferOut = 0;
		 rtxAllTransferOut = 0;
		 rtxPulmonaryBCCanceled = 0;
		 rtxPulmonaryCDCanceled = 0;
		 rtxExtrapulmonaryCanceled = 0;
		 rtxAllCanceled = 0;
		 rtxPulmonaryBCSLD = 0;
		 rtxPulmonaryCDSLD = 0;
		 rtxExtrapulmonarySLD = 0;
		 rtxAllSLD = 0;
		 
		 allDetected = 0;
		 allEligible = 0;
		 allCured = 0;
		 allCompleted = 0;
		 allDiedTB = 0;
		 allDiedNotTB = 0;
		 allFailed = 0;
		 allDefaulted = 0;
		 allTransferOut = 0;
		 allCanceled = 0;
		 allSLD = 0;
		 
	}
	
	public Integer getNewPulmonaryBCDetected() {
		return newPulmonaryBCDetected;
	}
	public void setNewPulmonaryBCDetected(Integer newPulmonaryBCDetected) {
		this.newPulmonaryBCDetected = newPulmonaryBCDetected;
	}
	public Integer getNewPulmonaryBCDetected04() {
		return newPulmonaryBCDetected04;
	}
	public void setNewPulmonaryBCDetected04(Integer newPulmonaryBCDetected04) {
		this.newPulmonaryBCDetected04 = newPulmonaryBCDetected04;
	}
	public Integer getNewPulmonaryBCDetected0514() {
		return newPulmonaryBCDetected0514;
	}
	public void setNewPulmonaryBCDetected0514(Integer newPulmonaryBCDetected0514) {
		this.newPulmonaryBCDetected0514 = newPulmonaryBCDetected0514;
	}
	public Integer getNewPulmonaryBCDetected1517() {
		return newPulmonaryBCDetected1517;
	}
	public void setNewPulmonaryBCDetected1517(Integer newPulmonaryBCDetected1517) {
		this.newPulmonaryBCDetected1517 = newPulmonaryBCDetected1517;
	}
	public Integer getNewPulmonaryCDDetected() {
		return newPulmonaryCDDetected;
	}
	public void setNewPulmonaryCDDetected(Integer newPulmonaryCDDetected) {
		this.newPulmonaryCDDetected = newPulmonaryCDDetected;
	}
	public Integer getNewPulmonaryCDDetected04() {
		return newPulmonaryCDDetected04;
	}
	public void setNewPulmonaryCDDetected04(Integer newPulmonaryCDDetected04) {
		this.newPulmonaryCDDetected04 = newPulmonaryCDDetected04;
	}
	public Integer getNewPulmonaryCDDetected0514() {
		return newPulmonaryCDDetected0514;
	}
	public void setNewPulmonaryCDDetected0514(Integer newPulmonaryCDDetected0514) {
		this.newPulmonaryCDDetected0514 = newPulmonaryCDDetected0514;
	}
	public Integer getNewPulmonaryCDDetected1517() {
		return newPulmonaryCDDetected1517;
	}
	public void setNewPulmonaryCDDetected1517(Integer newPulmonaryCDDetected1517) {
		this.newPulmonaryCDDetected1517 = newPulmonaryCDDetected1517;
	}
	public Integer getNewExtrapulmonaryDetected() {
		return newExtrapulmonaryDetected;
	}
	public void setNewExtrapulmonaryDetected(Integer newExtrapulmonaryDetected) {
		this.newExtrapulmonaryDetected = newExtrapulmonaryDetected;
	}
	public Integer getNewExtrapulmonaryDetected04() {
		return newExtrapulmonaryDetected04;
	}
	public void setNewExtrapulmonaryDetected04(Integer newExtrapulmonaryDetected04) {
		this.newExtrapulmonaryDetected04 = newExtrapulmonaryDetected04;
	}
	public Integer getNewExtrapulmonaryDetected0514() {
		return newExtrapulmonaryDetected0514;
	}
	public void setNewExtrapulmonaryDetected0514(
			Integer newExtrapulmonaryDetected0514) {
		this.newExtrapulmonaryDetected0514 = newExtrapulmonaryDetected0514;
	}
	public Integer getNewExtrapulmonaryDetected1517() {
		return newExtrapulmonaryDetected1517;
	}
	public void setNewExtrapulmonaryDetected1517(
			Integer newExtrapulmonaryDetected1517) {
		this.newExtrapulmonaryDetected1517 = newExtrapulmonaryDetected1517;
	}
	public Integer getNewAllDetected() {
		return newAllDetected;
	}
	public void setNewAllDetected(Integer newAllDetected) {
		this.newAllDetected = newAllDetected;
	}
	public Integer getNewAllDetected04() {
		return newAllDetected04;
	}
	public void setNewAllDetected04(Integer newAllDetected04) {
		this.newAllDetected04 = newAllDetected04;
	}
	public Integer getNewAllDetected0514() {
		return newAllDetected0514;
	}
	public void setNewAllDetected0514(Integer newAllDetected0514) {
		this.newAllDetected0514 = newAllDetected0514;
	}
	public Integer getNewAllDetected1517() {
		return newAllDetected1517;
	}
	public void setNewAllDetected1517(Integer newAllDetected1517) {
		this.newAllDetected1517 = newAllDetected1517;
	}
	public Integer getNewPulmonaryBCEligible() {
		return newPulmonaryBCEligible;
	}
	public void setNewPulmonaryBCEligible(Integer newPulmonaryBCEligible) {
		this.newPulmonaryBCEligible = newPulmonaryBCEligible;
	}
	public Integer getNewPulmonaryBCEligible04() {
		return newPulmonaryBCEligible04;
	}
	public void setNewPulmonaryBCEligible04(Integer newPulmonaryBCEligible04) {
		this.newPulmonaryBCEligible04 = newPulmonaryBCEligible04;
	}
	public Integer getNewPulmonaryBCEligible0514() {
		return newPulmonaryBCEligible0514;
	}
	public void setNewPulmonaryBCEligible0514(Integer newPulmonaryBCEligible0514) {
		this.newPulmonaryBCEligible0514 = newPulmonaryBCEligible0514;
	}
	public Integer getNewPulmonaryBCEligible1517() {
		return newPulmonaryBCEligible1517;
	}
	public void setNewPulmonaryBCEligible1517(Integer newPulmonaryBCEligible1517) {
		this.newPulmonaryBCEligible1517 = newPulmonaryBCEligible1517;
	}
	public Integer getNewPulmonaryCDEligible() {
		return newPulmonaryCDEligible;
	}
	public void setNewPulmonaryCDEligible(Integer newPulmonaryCDEligible) {
		this.newPulmonaryCDEligible = newPulmonaryCDEligible;
	}
	public Integer getNewPulmonaryCDEligible04() {
		return newPulmonaryCDEligible04;
	}
	public void setNewPulmonaryCDEligible04(Integer newPulmonaryCDEligible04) {
		this.newPulmonaryCDEligible04 = newPulmonaryCDEligible04;
	}
	public Integer getNewPulmonaryCDEligible0514() {
		return newPulmonaryCDEligible0514;
	}
	public void setNewPulmonaryCDEligible0514(Integer newPulmonaryCDEligible0514) {
		this.newPulmonaryCDEligible0514 = newPulmonaryCDEligible0514;
	}
	public Integer getNewPulmonaryCDEligible1517() {
		return newPulmonaryCDEligible1517;
	}
	public void setNewPulmonaryCDEligible1517(Integer newPulmonaryCDEligible1517) {
		this.newPulmonaryCDEligible1517 = newPulmonaryCDEligible1517;
	}
	public Integer getNewExtrapulmonaryEligible() {
		return newExtrapulmonaryEligible;
	}
	public void setNewExtrapulmonaryEligible(Integer newExtrapulmonaryEligible) {
		this.newExtrapulmonaryEligible = newExtrapulmonaryEligible;
	}
	public Integer getNewExtrapulmonaryEligible04() {
		return newExtrapulmonaryEligible04;
	}
	public void setNewExtrapulmonaryEligible04(Integer newExtrapulmonaryEligible04) {
		this.newExtrapulmonaryEligible04 = newExtrapulmonaryEligible04;
	}
	public Integer getNewExtrapulmonaryEligible0514() {
		return newExtrapulmonaryEligible0514;
	}
	public void setNewExtrapulmonaryEligible0514(
			Integer newExtrapulmonaryEligible0514) {
		this.newExtrapulmonaryEligible0514 = newExtrapulmonaryEligible0514;
	}
	public Integer getNewExtrapulmonaryEligible1517() {
		return newExtrapulmonaryEligible1517;
	}
	public void setNewExtrapulmonaryEligible1517(
			Integer newExtrapulmonaryEligible1517) {
		this.newExtrapulmonaryEligible1517 = newExtrapulmonaryEligible1517;
	}
	public Integer getNewAllEligible() {
		return newAllEligible;
	}
	public void setNewAllEligible(Integer newAllEligible) {
		this.newAllEligible = newAllEligible;
	}
	public Integer getNewAllEligible04() {
		return newAllEligible04;
	}
	public void setNewAllEligible04(Integer newAllEligible04) {
		this.newAllEligible04 = newAllEligible04;
	}
	public Integer getNewAllEligible0514() {
		return newAllEligible0514;
	}
	public void setNewAllEligible0514(Integer newAllEligible0514) {
		this.newAllEligible0514 = newAllEligible0514;
	}
	public Integer getNewAllEligible1517() {
		return newAllEligible1517;
	}
	public void setNewAllEligible1517(Integer newAllEligible1517) {
		this.newAllEligible1517 = newAllEligible1517;
	}
	public Integer getNewPulmonaryBCCured() {
		return newPulmonaryBCCured;
	}
	public void setNewPulmonaryBCCured(Integer newPulmonaryBCCured) {
		this.newPulmonaryBCCured = newPulmonaryBCCured;
	}
	public Integer getNewPulmonaryBCCured04() {
		return newPulmonaryBCCured04;
	}
	public void setNewPulmonaryBCCured04(Integer newPulmonaryBCCured04) {
		this.newPulmonaryBCCured04 = newPulmonaryBCCured04;
	}
	public Integer getNewPulmonaryBCCured0514() {
		return newPulmonaryBCCured0514;
	}
	public void setNewPulmonaryBCCured0514(Integer newPulmonaryBCCured0514) {
		this.newPulmonaryBCCured0514 = newPulmonaryBCCured0514;
	}
	public Integer getNewPulmonaryBCCured1517() {
		return newPulmonaryBCCured1517;
	}
	public void setNewPulmonaryBCCured1517(Integer newPulmonaryBCCured1517) {
		this.newPulmonaryBCCured1517 = newPulmonaryBCCured1517;
	}
	public Integer getNewPulmonaryCDCured() {
		return newPulmonaryCDCured;
	}
	public void setNewPulmonaryCDCured(Integer newPulmonaryCDCured) {
		this.newPulmonaryCDCured = newPulmonaryCDCured;
	}
	public Integer getNewPulmonaryCDCured04() {
		return newPulmonaryCDCured04;
	}
	public void setNewPulmonaryCDCured04(Integer newPulmonaryCDCured04) {
		this.newPulmonaryCDCured04 = newPulmonaryCDCured04;
	}
	public Integer getNewPulmonaryCDCured0514() {
		return newPulmonaryCDCured0514;
	}
	public void setNewPulmonaryCDCured0514(Integer newPulmonaryCDCured0514) {
		this.newPulmonaryCDCured0514 = newPulmonaryCDCured0514;
	}
	public Integer getNewPulmonaryCDCured1517() {
		return newPulmonaryCDCured1517;
	}
	public void setNewPulmonaryCDCured1517(Integer newPulmonaryCDCured1517) {
		this.newPulmonaryCDCured1517 = newPulmonaryCDCured1517;
	}
	public Integer getNewExtrapulmonaryCured() {
		return newExtrapulmonaryCured;
	}
	public void setNewExtrapulmonaryCured(Integer newExtrapulmonaryCured) {
		this.newExtrapulmonaryCured = newExtrapulmonaryCured;
	}
	public Integer getNewExtrapulmonaryCured04() {
		return newExtrapulmonaryCured04;
	}
	public void setNewExtrapulmonaryCured04(Integer newExtrapulmonaryCured04) {
		this.newExtrapulmonaryCured04 = newExtrapulmonaryCured04;
	}
	public Integer getNewExtrapulmonaryCured0514() {
		return newExtrapulmonaryCured0514;
	}
	public void setNewExtrapulmonaryCured0514(Integer newExtrapulmonaryCured0514) {
		this.newExtrapulmonaryCured0514 = newExtrapulmonaryCured0514;
	}
	public Integer getNewExtrapulmonaryCured1517() {
		return newExtrapulmonaryCured1517;
	}
	public void setNewExtrapulmonaryCured1517(Integer newExtrapulmonaryCured1517) {
		this.newExtrapulmonaryCured1517 = newExtrapulmonaryCured1517;
	}
	public Integer getNewAllCured() {
		return newAllCured;
	}
	public void setNewAllCured(Integer newAllCured) {
		this.newAllCured = newAllCured;
	}
	public Integer getNewAllCured04() {
		return newAllCured04;
	}
	public void setNewAllCured04(Integer newAllCured04) {
		this.newAllCured04 = newAllCured04;
	}
	public Integer getNewAllCured0514() {
		return newAllCured0514;
	}
	public void setNewAllCured0514(Integer newAllCured0514) {
		this.newAllCured0514 = newAllCured0514;
	}
	public Integer getNewAllCured1517() {
		return newAllCured1517;
	}
	public void setNewAllCured1517(Integer newAllCured1517) {
		this.newAllCured1517 = newAllCured1517;
	}
	public Integer getNewPulmonaryBCCompleted() {
		return newPulmonaryBCCompleted;
	}
	public void setNewPulmonaryBCCompleted(Integer newPulmonaryBCCompleted) {
		this.newPulmonaryBCCompleted = newPulmonaryBCCompleted;
	}
	public Integer getNewPulmonaryBCCompleted04() {
		return newPulmonaryBCCompleted04;
	}
	public void setNewPulmonaryBCCompleted04(Integer newPulmonaryBCCompleted04) {
		this.newPulmonaryBCCompleted04 = newPulmonaryBCCompleted04;
	}
	public Integer getNewPulmonaryBCCompleted0514() {
		return newPulmonaryBCCompleted0514;
	}
	public void setNewPulmonaryBCCompleted0514(Integer newPulmonaryBCCompleted0514) {
		this.newPulmonaryBCCompleted0514 = newPulmonaryBCCompleted0514;
	}
	public Integer getNewPulmonaryBCCompleted1517() {
		return newPulmonaryBCCompleted1517;
	}
	public void setNewPulmonaryBCCompleted1517(Integer newPulmonaryBCCompleted1517) {
		this.newPulmonaryBCCompleted1517 = newPulmonaryBCCompleted1517;
	}
	public Integer getNewPulmonaryCDCompleted() {
		return newPulmonaryCDCompleted;
	}
	public void setNewPulmonaryCDCompleted(Integer newPulmonaryCDCompleted) {
		this.newPulmonaryCDCompleted = newPulmonaryCDCompleted;
	}
	public Integer getNewPulmonaryCDCompleted04() {
		return newPulmonaryCDCompleted04;
	}
	public void setNewPulmonaryCDCompleted04(Integer newPulmonaryCDCompleted04) {
		this.newPulmonaryCDCompleted04 = newPulmonaryCDCompleted04;
	}
	public Integer getNewPulmonaryCDCompleted0514() {
		return newPulmonaryCDCompleted0514;
	}
	public void setNewPulmonaryCDCompleted0514(Integer newPulmonaryCDCompleted0514) {
		this.newPulmonaryCDCompleted0514 = newPulmonaryCDCompleted0514;
	}
	public Integer getNewPulmonaryCDCompleted1517() {
		return newPulmonaryCDCompleted1517;
	}
	public void setNewPulmonaryCDCompleted1517(Integer newPulmonaryCDCompleted1517) {
		this.newPulmonaryCDCompleted1517 = newPulmonaryCDCompleted1517;
	}
	public Integer getNewExtrapulmonaryCompleted() {
		return newExtrapulmonaryCompleted;
	}
	public void setNewExtrapulmonaryCompleted(Integer newExtrapulmonaryCompleted) {
		this.newExtrapulmonaryCompleted = newExtrapulmonaryCompleted;
	}
	public Integer getNewExtrapulmonaryCompleted04() {
		return newExtrapulmonaryCompleted04;
	}
	public void setNewExtrapulmonaryCompleted04(Integer newExtrapulmonaryCompleted04) {
		this.newExtrapulmonaryCompleted04 = newExtrapulmonaryCompleted04;
	}
	public Integer getNewExtrapulmonaryCompleted0514() {
		return newExtrapulmonaryCompleted0514;
	}
	public void setNewExtrapulmonaryCompleted0514(
			Integer newExtrapulmonaryCompleted0514) {
		this.newExtrapulmonaryCompleted0514 = newExtrapulmonaryCompleted0514;
	}
	public Integer getNewExtrapulmonaryCompleted1517() {
		return newExtrapulmonaryCompleted1517;
	}
	public void setNewExtrapulmonaryCompleted1517(
			Integer newExtrapulmonaryCompleted1517) {
		this.newExtrapulmonaryCompleted1517 = newExtrapulmonaryCompleted1517;
	}
	public Integer getNewAllCompleted() {
		return newAllCompleted;
	}
	public void setNewAllCompleted(Integer newAllCompleted) {
		this.newAllCompleted = newAllCompleted;
	}
	public Integer getNewAllCompleted04() {
		return newAllCompleted04;
	}
	public void setNewAllCompleted04(Integer newAllCompleted04) {
		this.newAllCompleted04 = newAllCompleted04;
	}
	public Integer getNewAllCompleted0514() {
		return newAllCompleted0514;
	}
	public void setNewAllCompleted0514(Integer newAllCompleted0514) {
		this.newAllCompleted0514 = newAllCompleted0514;
	}
	public Integer getNewAllCompleted1517() {
		return newAllCompleted1517;
	}
	public void setNewAllCompleted1517(Integer newAllCompleted1517) {
		this.newAllCompleted1517 = newAllCompleted1517;
	}
	public Integer getNewPulmonaryBCDiedTB() {
		return newPulmonaryBCDiedTB;
	}
	public void setNewPulmonaryBCDiedTB(Integer newPulmonaryBCDiedTB) {
		this.newPulmonaryBCDiedTB = newPulmonaryBCDiedTB;
	}
	public Integer getNewPulmonaryBCDiedTB04() {
		return newPulmonaryBCDiedTB04;
	}
	public void setNewPulmonaryBCDiedTB04(Integer newPulmonaryBCDiedTB04) {
		this.newPulmonaryBCDiedTB04 = newPulmonaryBCDiedTB04;
	}
	public Integer getNewPulmonaryBCDiedTB0514() {
		return newPulmonaryBCDiedTB0514;
	}
	public void setNewPulmonaryBCDiedTB0514(Integer newPulmonaryBCDiedTB0514) {
		this.newPulmonaryBCDiedTB0514 = newPulmonaryBCDiedTB0514;
	}
	public Integer getNewPulmonaryBCDiedTB1517() {
		return newPulmonaryBCDiedTB1517;
	}
	public void setNewPulmonaryBCDiedTB1517(Integer newPulmonaryBCDiedTB1517) {
		this.newPulmonaryBCDiedTB1517 = newPulmonaryBCDiedTB1517;
	}
	public Integer getNewPulmonaryCDDiedTB() {
		return newPulmonaryCDDiedTB;
	}
	public void setNewPulmonaryCDDiedTB(Integer newPulmonaryCDDiedTB) {
		this.newPulmonaryCDDiedTB = newPulmonaryCDDiedTB;
	}
	public Integer getNewPulmonaryCDDiedTB04() {
		return newPulmonaryCDDiedTB04;
	}
	public void setNewPulmonaryCDDiedTB04(Integer newPulmonaryCDDiedTB04) {
		this.newPulmonaryCDDiedTB04 = newPulmonaryCDDiedTB04;
	}
	public Integer getNewPulmonaryCDDiedTB0514() {
		return newPulmonaryCDDiedTB0514;
	}
	public void setNewPulmonaryCDDiedTB0514(Integer newPulmonaryCDDiedTB0514) {
		this.newPulmonaryCDDiedTB0514 = newPulmonaryCDDiedTB0514;
	}
	public Integer getNewPulmonaryCDDiedTB1517() {
		return newPulmonaryCDDiedTB1517;
	}
	public void setNewPulmonaryCDDiedTB1517(Integer newPulmonaryCDDiedTB1517) {
		this.newPulmonaryCDDiedTB1517 = newPulmonaryCDDiedTB1517;
	}
	public Integer getNewExtrapulmonaryDiedTB() {
		return newExtrapulmonaryDiedTB;
	}
	public void setNewExtrapulmonaryDiedTB(Integer newExtrapulmonaryDiedTB) {
		this.newExtrapulmonaryDiedTB = newExtrapulmonaryDiedTB;
	}
	public Integer getNewExtrapulmonaryDiedTB04() {
		return newExtrapulmonaryDiedTB04;
	}
	public void setNewExtrapulmonaryDiedTB04(Integer newExtrapulmonaryDiedTB04) {
		this.newExtrapulmonaryDiedTB04 = newExtrapulmonaryDiedTB04;
	}
	public Integer getNewExtrapulmonaryDiedTB0514() {
		return newExtrapulmonaryDiedTB0514;
	}
	public void setNewExtrapulmonaryDiedTB0514(Integer newExtrapulmonaryDiedTB0514) {
		this.newExtrapulmonaryDiedTB0514 = newExtrapulmonaryDiedTB0514;
	}
	public Integer getNewExtrapulmonaryDiedTB1517() {
		return newExtrapulmonaryDiedTB1517;
	}
	public void setNewExtrapulmonaryDiedTB1517(Integer newExtrapulmonaryDiedTB1517) {
		this.newExtrapulmonaryDiedTB1517 = newExtrapulmonaryDiedTB1517;
	}
	public Integer getNewAllDiedTB() {
		return newAllDiedTB;
	}
	public void setNewAllDiedTB(Integer newAllDiedTB) {
		this.newAllDiedTB = newAllDiedTB;
	}
	public Integer getNewAllDiedTB04() {
		return newAllDiedTB04;
	}
	public void setNewAllDiedTB04(Integer newAllDiedTB04) {
		this.newAllDiedTB04 = newAllDiedTB04;
	}
	public Integer getNewAllDiedTB0514() {
		return newAllDiedTB0514;
	}
	public void setNewAllDiedTB0514(Integer newAllDiedTB0514) {
		this.newAllDiedTB0514 = newAllDiedTB0514;
	}
	public Integer getNewAllDiedTB1517() {
		return newAllDiedTB1517;
	}
	public void setNewAllDiedTB1517(Integer newAllDiedTB1517) {
		this.newAllDiedTB1517 = newAllDiedTB1517;
	}
	public Integer getNewPulmonaryBCDiedNotTB() {
		return newPulmonaryBCDiedNotTB;
	}
	public void setNewPulmonaryBCDiedNotTB(Integer newPulmonaryBCDiedNotTB) {
		this.newPulmonaryBCDiedNotTB = newPulmonaryBCDiedNotTB;
	}
	public Integer getNewPulmonaryBCDiedNotTB04() {
		return newPulmonaryBCDiedNotTB04;
	}
	public void setNewPulmonaryBCDiedNotTB04(Integer newPulmonaryBCDiedNotTB04) {
		this.newPulmonaryBCDiedNotTB04 = newPulmonaryBCDiedNotTB04;
	}
	public Integer getNewPulmonaryBCDiedNotTB0514() {
		return newPulmonaryBCDiedNotTB0514;
	}
	public void setNewPulmonaryBCDiedNotTB0514(Integer newPulmonaryBCDiedNotTB0514) {
		this.newPulmonaryBCDiedNotTB0514 = newPulmonaryBCDiedNotTB0514;
	}
	public Integer getNewPulmonaryBCDiedNotTB1517() {
		return newPulmonaryBCDiedNotTB1517;
	}
	public void setNewPulmonaryBCDiedNotTB1517(Integer newPulmonaryBCDiedNotTB1517) {
		this.newPulmonaryBCDiedNotTB1517 = newPulmonaryBCDiedNotTB1517;
	}
	public Integer getNewPulmonaryCDDiedNotTB() {
		return newPulmonaryCDDiedNotTB;
	}
	public void setNewPulmonaryCDDiedNotTB(Integer newPulmonaryCDDiedNotTB) {
		this.newPulmonaryCDDiedNotTB = newPulmonaryCDDiedNotTB;
	}
	public Integer getNewPulmonaryCDDiedNotTB04() {
		return newPulmonaryCDDiedNotTB04;
	}
	public void setNewPulmonaryCDDiedNotTB04(Integer newPulmonaryCDDiedNotTB04) {
		this.newPulmonaryCDDiedNotTB04 = newPulmonaryCDDiedNotTB04;
	}
	public Integer getNewPulmonaryCDDiedNotTB0514() {
		return newPulmonaryCDDiedNotTB0514;
	}
	public void setNewPulmonaryCDDiedNotTB0514(Integer newPulmonaryCDDiedNotTB0514) {
		this.newPulmonaryCDDiedNotTB0514 = newPulmonaryCDDiedNotTB0514;
	}
	public Integer getNewPulmonaryCDDiedNotTB1517() {
		return newPulmonaryCDDiedNotTB1517;
	}
	public void setNewPulmonaryCDDiedNotTB1517(Integer newPulmonaryCDDiedNotTB1517) {
		this.newPulmonaryCDDiedNotTB1517 = newPulmonaryCDDiedNotTB1517;
	}
	public Integer getNewExtrapulmonaryDiedNotTB() {
		return newExtrapulmonaryDiedNotTB;
	}
	public void setNewExtrapulmonaryDiedNotTB(Integer newExtrapulmonaryDiedNotTB) {
		this.newExtrapulmonaryDiedNotTB = newExtrapulmonaryDiedNotTB;
	}
	public Integer getNewExtrapulmonaryDiedNotTB04() {
		return newExtrapulmonaryDiedNotTB04;
	}
	public void setNewExtrapulmonaryDiedNotTB04(Integer newExtrapulmonaryDiedNotTB04) {
		this.newExtrapulmonaryDiedNotTB04 = newExtrapulmonaryDiedNotTB04;
	}
	public Integer getNewExtrapulmonaryDiedNotTB0514() {
		return newExtrapulmonaryDiedNotTB0514;
	}
	public void setNewExtrapulmonaryDiedNotTB0514(
			Integer newExtrapulmonaryDiedNotTB0514) {
		this.newExtrapulmonaryDiedNotTB0514 = newExtrapulmonaryDiedNotTB0514;
	}
	public Integer getNewExtrapulmonaryDiedNotTB1517() {
		return newExtrapulmonaryDiedNotTB1517;
	}
	public void setNewExtrapulmonaryDiedNotTB1517(
			Integer newExtrapulmonaryDiedNotTB1517) {
		this.newExtrapulmonaryDiedNotTB1517 = newExtrapulmonaryDiedNotTB1517;
	}
	public Integer getNewAllDiedNotTB() {
		return newAllDiedNotTB;
	}
	public void setNewAllDiedNotTB(Integer newAllDiedNotTB) {
		this.newAllDiedNotTB = newAllDiedNotTB;
	}
	public Integer getNewAllDiedNotTB04() {
		return newAllDiedNotTB04;
	}
	public void setNewAllDiedNotTB04(Integer newAllDiedNotTB04) {
		this.newAllDiedNotTB04 = newAllDiedNotTB04;
	}
	public Integer getNewAllDiedNotTB0514() {
		return newAllDiedNotTB0514;
	}
	public void setNewAllDiedNotTB0514(Integer newAllDiedNotTB0514) {
		this.newAllDiedNotTB0514 = newAllDiedNotTB0514;
	}
	public Integer getNewAllDiedNotTB1517() {
		return newAllDiedNotTB1517;
	}
	public void setNewAllDiedNotTB1517(Integer newAllDiedNotTB1517) {
		this.newAllDiedNotTB1517 = newAllDiedNotTB1517;
	}
	public Integer getNewPulmonaryBCFailed() {
		return newPulmonaryBCFailed;
	}
	public void setNewPulmonaryBCFailed(Integer newPulmonaryBCFailed) {
		this.newPulmonaryBCFailed = newPulmonaryBCFailed;
	}
	public Integer getNewPulmonaryBCFailed04() {
		return newPulmonaryBCFailed04;
	}
	public void setNewPulmonaryBCFailed04(Integer newPulmonaryBCFailed04) {
		this.newPulmonaryBCFailed04 = newPulmonaryBCFailed04;
	}
	public Integer getNewPulmonaryBCFailed0514() {
		return newPulmonaryBCFailed0514;
	}
	public void setNewPulmonaryBCFailed0514(Integer newPulmonaryBCFailed0514) {
		this.newPulmonaryBCFailed0514 = newPulmonaryBCFailed0514;
	}
	public Integer getNewPulmonaryBCFailed1517() {
		return newPulmonaryBCFailed1517;
	}
	public void setNewPulmonaryBCFailed1517(Integer newPulmonaryBCFailed1517) {
		this.newPulmonaryBCFailed1517 = newPulmonaryBCFailed1517;
	}
	public Integer getNewPulmonaryCDFailed() {
		return newPulmonaryCDFailed;
	}
	public void setNewPulmonaryCDFailed(Integer newPulmonaryCDFailed) {
		this.newPulmonaryCDFailed = newPulmonaryCDFailed;
	}
	public Integer getNewPulmonaryCDFailed04() {
		return newPulmonaryCDFailed04;
	}
	public void setNewPulmonaryCDFailed04(Integer newPulmonaryCDFailed04) {
		this.newPulmonaryCDFailed04 = newPulmonaryCDFailed04;
	}
	public Integer getNewPulmonaryCDFailed0514() {
		return newPulmonaryCDFailed0514;
	}
	public void setNewPulmonaryCDFailed0514(Integer newPulmonaryCDFailed0514) {
		this.newPulmonaryCDFailed0514 = newPulmonaryCDFailed0514;
	}
	public Integer getNewPulmonaryCDFailed1517() {
		return newPulmonaryCDFailed1517;
	}
	public void setNewPulmonaryCDFailed1517(Integer newPulmonaryCDFailed1517) {
		this.newPulmonaryCDFailed1517 = newPulmonaryCDFailed1517;
	}
	public Integer getNewExtrapulmonaryFailed() {
		return newExtrapulmonaryFailed;
	}
	public void setNewExtrapulmonaryFailed(Integer newExtrapulmonaryFailed) {
		this.newExtrapulmonaryFailed = newExtrapulmonaryFailed;
	}
	public Integer getNewExtrapulmonaryFailed04() {
		return newExtrapulmonaryFailed04;
	}
	public void setNewExtrapulmonaryFailed04(Integer newExtrapulmonaryFailed04) {
		this.newExtrapulmonaryFailed04 = newExtrapulmonaryFailed04;
	}
	public Integer getNewExtrapulmonaryFailed0514() {
		return newExtrapulmonaryFailed0514;
	}
	public void setNewExtrapulmonaryFailed0514(Integer newExtrapulmonaryFailed0514) {
		this.newExtrapulmonaryFailed0514 = newExtrapulmonaryFailed0514;
	}
	public Integer getNewExtrapulmonaryFailed1517() {
		return newExtrapulmonaryFailed1517;
	}
	public void setNewExtrapulmonaryFailed1517(Integer newExtrapulmonaryFailed1517) {
		this.newExtrapulmonaryFailed1517 = newExtrapulmonaryFailed1517;
	}
	public Integer getNewAllFailed() {
		return newAllFailed;
	}
	public void setNewAllFailed(Integer newAllFailed) {
		this.newAllFailed = newAllFailed;
	}
	public Integer getNewAllFailed04() {
		return newAllFailed04;
	}
	public void setNewAllFailed04(Integer newAllFailed04) {
		this.newAllFailed04 = newAllFailed04;
	}
	public Integer getNewAllFailed0514() {
		return newAllFailed0514;
	}
	public void setNewAllFailed0514(Integer newAllFailed0514) {
		this.newAllFailed0514 = newAllFailed0514;
	}
	public Integer getNewAllFailed1517() {
		return newAllFailed1517;
	}
	public void setNewAllFailed1517(Integer newAllFailed1517) {
		this.newAllFailed1517 = newAllFailed1517;
	}
	public Integer getNewPulmonaryBCDefaulted() {
		return newPulmonaryBCDefaulted;
	}
	public void setNewPulmonaryBCDefaulted(Integer newPulmonaryBCDefaulted) {
		this.newPulmonaryBCDefaulted = newPulmonaryBCDefaulted;
	}
	public Integer getNewPulmonaryBCDefaulted04() {
		return newPulmonaryBCDefaulted04;
	}
	public void setNewPulmonaryBCDefaulted04(Integer newPulmonaryBCDefaulted04) {
		this.newPulmonaryBCDefaulted04 = newPulmonaryBCDefaulted04;
	}
	public Integer getNewPulmonaryBCDefaulted0514() {
		return newPulmonaryBCDefaulted0514;
	}
	public void setNewPulmonaryBCDefaulted0514(Integer newPulmonaryBCDefaulted0514) {
		this.newPulmonaryBCDefaulted0514 = newPulmonaryBCDefaulted0514;
	}
	public Integer getNewPulmonaryBCDefaulted1517() {
		return newPulmonaryBCDefaulted1517;
	}
	public void setNewPulmonaryBCDefaulted1517(Integer newPulmonaryBCDefaulted1517) {
		this.newPulmonaryBCDefaulted1517 = newPulmonaryBCDefaulted1517;
	}
	public Integer getNewPulmonaryCDDefaulted() {
		return newPulmonaryCDDefaulted;
	}
	public void setNewPulmonaryCDDefaulted(Integer newPulmonaryCDDefaulted) {
		this.newPulmonaryCDDefaulted = newPulmonaryCDDefaulted;
	}
	public Integer getNewPulmonaryCDDefaulted04() {
		return newPulmonaryCDDefaulted04;
	}
	public void setNewPulmonaryCDDefaulted04(Integer newPulmonaryCDDefaulted04) {
		this.newPulmonaryCDDefaulted04 = newPulmonaryCDDefaulted04;
	}
	public Integer getNewPulmonaryCDDefaulted0514() {
		return newPulmonaryCDDefaulted0514;
	}
	public void setNewPulmonaryCDDefaulted0514(Integer newPulmonaryCDDefaulted0514) {
		this.newPulmonaryCDDefaulted0514 = newPulmonaryCDDefaulted0514;
	}
	public Integer getNewPulmonaryCDDefaulted1517() {
		return newPulmonaryCDDefaulted1517;
	}
	public void setNewPulmonaryCDDefaulted1517(Integer newPulmonaryCDDefaulted1517) {
		this.newPulmonaryCDDefaulted1517 = newPulmonaryCDDefaulted1517;
	}
	public Integer getNewExtrapulmonaryDefaulted() {
		return newExtrapulmonaryDefaulted;
	}
	public void setNewExtrapulmonaryDefaulted(Integer newExtrapulmonaryDefaulted) {
		this.newExtrapulmonaryDefaulted = newExtrapulmonaryDefaulted;
	}
	public Integer getNewExtrapulmonaryDefaulted04() {
		return newExtrapulmonaryDefaulted04;
	}
	public void setNewExtrapulmonaryDefaulted04(Integer newExtrapulmonaryDefaulted04) {
		this.newExtrapulmonaryDefaulted04 = newExtrapulmonaryDefaulted04;
	}
	public Integer getNewExtrapulmonaryDefaulted0514() {
		return newExtrapulmonaryDefaulted0514;
	}
	public void setNewExtrapulmonaryDefaulted0514(
			Integer newExtrapulmonaryDefaulted0514) {
		this.newExtrapulmonaryDefaulted0514 = newExtrapulmonaryDefaulted0514;
	}
	public Integer getNewExtrapulmonaryDefaulted1517() {
		return newExtrapulmonaryDefaulted1517;
	}
	public void setNewExtrapulmonaryDefaulted1517(
			Integer newExtrapulmonaryDefaulted1517) {
		this.newExtrapulmonaryDefaulted1517 = newExtrapulmonaryDefaulted1517;
	}
	public Integer getNewAllDefaulted() {
		return newAllDefaulted;
	}
	public void setNewAllDefaulted(Integer newAllDefaulted) {
		this.newAllDefaulted = newAllDefaulted;
	}
	public Integer getNewAllDefaulted04() {
		return newAllDefaulted04;
	}
	public void setNewAllDefaulted04(Integer newAllDefaulted04) {
		this.newAllDefaulted04 = newAllDefaulted04;
	}
	public Integer getNewAllDefaulted0514() {
		return newAllDefaulted0514;
	}
	public void setNewAllDefaulted0514(Integer newAllDefaulted0514) {
		this.newAllDefaulted0514 = newAllDefaulted0514;
	}
	public Integer getNewAllDefaulted1517() {
		return newAllDefaulted1517;
	}
	public void setNewAllDefaulted1517(Integer newAllDefaulted1517) {
		this.newAllDefaulted1517 = newAllDefaulted1517;
	}
	public Integer getNewPulmonaryBCTransferOut() {
		return newPulmonaryBCTransferOut;
	}
	public void setNewPulmonaryBCTransferOut(Integer newPulmonaryBCTransferOut) {
		this.newPulmonaryBCTransferOut = newPulmonaryBCTransferOut;
	}
	public Integer getNewPulmonaryBCTransferOut04() {
		return newPulmonaryBCTransferOut04;
	}
	public void setNewPulmonaryBCTransferOut04(Integer newPulmonaryBCTransferOut04) {
		this.newPulmonaryBCTransferOut04 = newPulmonaryBCTransferOut04;
	}
	public Integer getNewPulmonaryBCTransferOut0514() {
		return newPulmonaryBCTransferOut0514;
	}
	public void setNewPulmonaryBCTransferOut0514(
			Integer newPulmonaryBCTransferOut0514) {
		this.newPulmonaryBCTransferOut0514 = newPulmonaryBCTransferOut0514;
	}
	public Integer getNewPulmonaryBCTransferOut1517() {
		return newPulmonaryBCTransferOut1517;
	}
	public void setNewPulmonaryBCTransferOut1517(
			Integer newPulmonaryBCTransferOut1517) {
		this.newPulmonaryBCTransferOut1517 = newPulmonaryBCTransferOut1517;
	}
	public Integer getNewPulmonaryCDTransferOut() {
		return newPulmonaryCDTransferOut;
	}
	public void setNewPulmonaryCDTransferOut(Integer newPulmonaryCDTransferOut) {
		this.newPulmonaryCDTransferOut = newPulmonaryCDTransferOut;
	}
	public Integer getNewPulmonaryCDTransferOut04() {
		return newPulmonaryCDTransferOut04;
	}
	public void setNewPulmonaryCDTransferOut04(Integer newPulmonaryCDTransferOut04) {
		this.newPulmonaryCDTransferOut04 = newPulmonaryCDTransferOut04;
	}
	public Integer getNewPulmonaryCDTransferOut0514() {
		return newPulmonaryCDTransferOut0514;
	}
	public void setNewPulmonaryCDTransferOut0514(
			Integer newPulmonaryCDTransferOut0514) {
		this.newPulmonaryCDTransferOut0514 = newPulmonaryCDTransferOut0514;
	}
	public Integer getNewPulmonaryCDTransferOut1517() {
		return newPulmonaryCDTransferOut1517;
	}
	public void setNewPulmonaryCDTransferOut1517(
			Integer newPulmonaryCDTransferOut1517) {
		this.newPulmonaryCDTransferOut1517 = newPulmonaryCDTransferOut1517;
	}
	public Integer getNewExtrapulmonaryTransferOut() {
		return newExtrapulmonaryTransferOut;
	}
	public void setNewExtrapulmonaryTransferOut(Integer newExtrapulmonaryTransferOut) {
		this.newExtrapulmonaryTransferOut = newExtrapulmonaryTransferOut;
	}
	public Integer getNewExtrapulmonaryTransferOut04() {
		return newExtrapulmonaryTransferOut04;
	}
	public void setNewExtrapulmonaryTransferOut04(
			Integer newExtrapulmonaryTransferOut04) {
		this.newExtrapulmonaryTransferOut04 = newExtrapulmonaryTransferOut04;
	}
	public Integer getNewExtrapulmonaryTransferOut0514() {
		return newExtrapulmonaryTransferOut0514;
	}
	public void setNewExtrapulmonaryTransferOut0514(
			Integer newExtrapulmonaryTransferOut0514) {
		this.newExtrapulmonaryTransferOut0514 = newExtrapulmonaryTransferOut0514;
	}
	public Integer getNewExtrapulmonaryTransferOut1517() {
		return newExtrapulmonaryTransferOut1517;
	}
	public void setNewExtrapulmonaryTransferOut1517(
			Integer newExtrapulmonaryTransferOut1517) {
		this.newExtrapulmonaryTransferOut1517 = newExtrapulmonaryTransferOut1517;
	}
	public Integer getNewAllTransferOut() {
		return newAllTransferOut;
	}
	public void setNewAllTransferOut(Integer newAllTransferOut) {
		this.newAllTransferOut = newAllTransferOut;
	}
	public Integer getNewAllTransferOut04() {
		return newAllTransferOut04;
	}
	public void setNewAllTransferOut04(Integer newAllTransferOut04) {
		this.newAllTransferOut04 = newAllTransferOut04;
	}
	public Integer getNewAllTransferOut0514() {
		return newAllTransferOut0514;
	}
	public void setNewAllTransferOut0514(Integer newAllTransferOut0514) {
		this.newAllTransferOut0514 = newAllTransferOut0514;
	}
	public Integer getNewAllTransferOut1517() {
		return newAllTransferOut1517;
	}
	public void setNewAllTransferOut1517(Integer newAllTransferOut1517) {
		this.newAllTransferOut1517 = newAllTransferOut1517;
	}
	public Integer getNewPulmonaryBCCanceled() {
		return newPulmonaryBCCanceled;
	}
	public void setNewPulmonaryBCCanceled(Integer newPulmonaryBCCanceled) {
		this.newPulmonaryBCCanceled = newPulmonaryBCCanceled;
	}
	public Integer getNewPulmonaryBCCanceled04() {
		return newPulmonaryBCCanceled04;
	}
	public void setNewPulmonaryBCCanceled04(Integer newPulmonaryBCCanceled04) {
		this.newPulmonaryBCCanceled04 = newPulmonaryBCCanceled04;
	}
	public Integer getNewPulmonaryBCCanceled0514() {
		return newPulmonaryBCCanceled0514;
	}
	public void setNewPulmonaryBCCanceled0514(Integer newPulmonaryBCCanceled0514) {
		this.newPulmonaryBCCanceled0514 = newPulmonaryBCCanceled0514;
	}
	public Integer getNewPulmonaryBCCanceled1517() {
		return newPulmonaryBCCanceled1517;
	}
	public void setNewPulmonaryBCCanceled1517(Integer newPulmonaryBCCanceled1517) {
		this.newPulmonaryBCCanceled1517 = newPulmonaryBCCanceled1517;
	}
	public Integer getNewPulmonaryCDCanceled() {
		return newPulmonaryCDCanceled;
	}
	public void setNewPulmonaryCDCanceled(Integer newPulmonaryCDCanceled) {
		this.newPulmonaryCDCanceled = newPulmonaryCDCanceled;
	}
	public Integer getNewPulmonaryCDCanceled04() {
		return newPulmonaryCDCanceled04;
	}
	public void setNewPulmonaryCDCanceled04(Integer newPulmonaryCDCanceled04) {
		this.newPulmonaryCDCanceled04 = newPulmonaryCDCanceled04;
	}
	public Integer getNewPulmonaryCDCanceled0514() {
		return newPulmonaryCDCanceled0514;
	}
	public void setNewPulmonaryCDCanceled0514(Integer newPulmonaryCDCanceled0514) {
		this.newPulmonaryCDCanceled0514 = newPulmonaryCDCanceled0514;
	}
	public Integer getNewPulmonaryCDCanceled1517() {
		return newPulmonaryCDCanceled1517;
	}
	public void setNewPulmonaryCDCanceled1517(Integer newPulmonaryCDCanceled1517) {
		this.newPulmonaryCDCanceled1517 = newPulmonaryCDCanceled1517;
	}
	public Integer getNewExtrapulmonaryCanceled() {
		return newExtrapulmonaryCanceled;
	}
	public void setNewExtrapulmonaryCanceled(Integer newExtrapulmonaryCanceled) {
		this.newExtrapulmonaryCanceled = newExtrapulmonaryCanceled;
	}
	public Integer getNewExtrapulmonaryCanceled04() {
		return newExtrapulmonaryCanceled04;
	}
	public void setNewExtrapulmonaryCanceled04(Integer newExtrapulmonaryCanceled04) {
		this.newExtrapulmonaryCanceled04 = newExtrapulmonaryCanceled04;
	}
	public Integer getNewExtrapulmonaryCanceled0514() {
		return newExtrapulmonaryCanceled0514;
	}
	public void setNewExtrapulmonaryCanceled0514(
			Integer newExtrapulmonaryCanceled0514) {
		this.newExtrapulmonaryCanceled0514 = newExtrapulmonaryCanceled0514;
	}
	public Integer getNewExtrapulmonaryCanceled1517() {
		return newExtrapulmonaryCanceled1517;
	}
	public void setNewExtrapulmonaryCanceled1517(
			Integer newExtrapulmonaryCanceled1517) {
		this.newExtrapulmonaryCanceled1517 = newExtrapulmonaryCanceled1517;
	}
	public Integer getNewAllCanceled() {
		return newAllCanceled;
	}
	public void setNewAllCanceled(Integer newAllCanceled) {
		this.newAllCanceled = newAllCanceled;
	}
	public Integer getNewAllCanceled04() {
		return newAllCanceled04;
	}
	public void setNewAllCanceled04(Integer newAllCanceled04) {
		this.newAllCanceled04 = newAllCanceled04;
	}
	public Integer getNewAllCanceled0514() {
		return newAllCanceled0514;
	}
	public void setNewAllCanceled0514(Integer newAllCanceled0514) {
		this.newAllCanceled0514 = newAllCanceled0514;
	}
	public Integer getNewAllCanceled1517() {
		return newAllCanceled1517;
	}
	public void setNewAllCanceled1517(Integer newAllCanceled1517) {
		this.newAllCanceled1517 = newAllCanceled1517;
	}
	public Integer getNewPulmonaryBCSLD() {
		return newPulmonaryBCSLD;
	}
	public void setNewPulmonaryBCSLD(Integer newPulmonaryBCSLD) {
		this.newPulmonaryBCSLD = newPulmonaryBCSLD;
	}
	public Integer getNewPulmonaryBCSLD04() {
		return newPulmonaryBCSLD04;
	}
	public void setNewPulmonaryBCSLD04(Integer newPulmonaryBCSLD04) {
		this.newPulmonaryBCSLD04 = newPulmonaryBCSLD04;
	}
	public Integer getNewPulmonaryBCSLD0514() {
		return newPulmonaryBCSLD0514;
	}
	public void setNewPulmonaryBCSLD0514(Integer newPulmonaryBCSLD0514) {
		this.newPulmonaryBCSLD0514 = newPulmonaryBCSLD0514;
	}
	public Integer getNewPulmonaryBCSLD1517() {
		return newPulmonaryBCSLD1517;
	}
	public void setNewPulmonaryBCSLD1517(Integer newPulmonaryBCSLD1517) {
		this.newPulmonaryBCSLD1517 = newPulmonaryBCSLD1517;
	}
	public Integer getNewPulmonaryCDSLD() {
		return newPulmonaryCDSLD;
	}
	public void setNewPulmonaryCDSLD(Integer newPulmonaryCDSLD) {
		this.newPulmonaryCDSLD = newPulmonaryCDSLD;
	}
	public Integer getNewPulmonaryCDSLD04() {
		return newPulmonaryCDSLD04;
	}
	public void setNewPulmonaryCDSLD04(Integer newPulmonaryCDSLD04) {
		this.newPulmonaryCDSLD04 = newPulmonaryCDSLD04;
	}
	public Integer getNewPulmonaryCDSLD0514() {
		return newPulmonaryCDSLD0514;
	}
	public void setNewPulmonaryCDSLD0514(Integer newPulmonaryCDSLD0514) {
		this.newPulmonaryCDSLD0514 = newPulmonaryCDSLD0514;
	}
	public Integer getNewPulmonaryCDSLD1517() {
		return newPulmonaryCDSLD1517;
	}
	public void setNewPulmonaryCDSLD1517(Integer newPulmonaryCDSLD1517) {
		this.newPulmonaryCDSLD1517 = newPulmonaryCDSLD1517;
	}
	public Integer getNewExtrapulmonarySLD() {
		return newExtrapulmonarySLD;
	}
	public void setNewExtrapulmonarySLD(Integer newExtrapulmonarySLD) {
		this.newExtrapulmonarySLD = newExtrapulmonarySLD;
	}
	public Integer getNewExtrapulmonarySLD04() {
		return newExtrapulmonarySLD04;
	}
	public void setNewExtrapulmonarySLD04(Integer newExtrapulmonarySLD04) {
		this.newExtrapulmonarySLD04 = newExtrapulmonarySLD04;
	}
	public Integer getNewExtrapulmonarySLD0514() {
		return newExtrapulmonarySLD0514;
	}
	public void setNewExtrapulmonarySLD0514(Integer newExtrapulmonarySLD0514) {
		this.newExtrapulmonarySLD0514 = newExtrapulmonarySLD0514;
	}
	public Integer getNewExtrapulmonarySLD1517() {
		return newExtrapulmonarySLD1517;
	}
	public void setNewExtrapulmonarySLD1517(Integer newExtrapulmonarySLD1517) {
		this.newExtrapulmonarySLD1517 = newExtrapulmonarySLD1517;
	}
	public Integer getNewAllSLD() {
		return newAllSLD;
	}
	public void setNewAllSLD(Integer newAllSLD) {
		this.newAllSLD = newAllSLD;
	}
	public Integer getNewAllSLD04() {
		return newAllSLD04;
	}
	public void setNewAllSLD04(Integer newAllSLD04) {
		this.newAllSLD04 = newAllSLD04;
	}
	public Integer getNewAllSLD0514() {
		return newAllSLD0514;
	}
	public void setNewAllSLD0514(Integer newAllSLD0514) {
		this.newAllSLD0514 = newAllSLD0514;
	}
	public Integer getNewAllSLD1517() {
		return newAllSLD1517;
	}
	public void setNewAllSLD1517(Integer newAllSLD1517) {
		this.newAllSLD1517 = newAllSLD1517;
	}
	public Integer getRelapsePulmonaryBCDetected() {
		return relapsePulmonaryBCDetected;
	}
	public void setRelapsePulmonaryBCDetected(Integer relapsePulmonaryBCDetected) {
		this.relapsePulmonaryBCDetected = relapsePulmonaryBCDetected;
	}
	public Integer getRelapsePulmonaryBCDetected04() {
		return relapsePulmonaryBCDetected04;
	}
	public void setRelapsePulmonaryBCDetected04(Integer relapsePulmonaryBCDetected04) {
		this.relapsePulmonaryBCDetected04 = relapsePulmonaryBCDetected04;
	}
	public Integer getRelapsePulmonaryBCDetected0514() {
		return relapsePulmonaryBCDetected0514;
	}
	public void setRelapsePulmonaryBCDetected0514(
			Integer relapsePulmonaryBCDetected0514) {
		this.relapsePulmonaryBCDetected0514 = relapsePulmonaryBCDetected0514;
	}
	public Integer getRelapsePulmonaryBCDetected1517() {
		return relapsePulmonaryBCDetected1517;
	}
	public void setRelapsePulmonaryBCDetected1517(
			Integer relapsePulmonaryBCDetected1517) {
		this.relapsePulmonaryBCDetected1517 = relapsePulmonaryBCDetected1517;
	}
	public Integer getRelapsePulmonaryCDDetected() {
		return relapsePulmonaryCDDetected;
	}
	public void setRelapsePulmonaryCDDetected(Integer relapsePulmonaryCDDetected) {
		this.relapsePulmonaryCDDetected = relapsePulmonaryCDDetected;
	}
	public Integer getRelapsePulmonaryCDDetected04() {
		return relapsePulmonaryCDDetected04;
	}
	public void setRelapsePulmonaryCDDetected04(Integer relapsePulmonaryCDDetected04) {
		this.relapsePulmonaryCDDetected04 = relapsePulmonaryCDDetected04;
	}
	public Integer getRelapsePulmonaryCDDetected0514() {
		return relapsePulmonaryCDDetected0514;
	}
	public void setRelapsePulmonaryCDDetected0514(
			Integer relapsePulmonaryCDDetected0514) {
		this.relapsePulmonaryCDDetected0514 = relapsePulmonaryCDDetected0514;
	}
	public Integer getRelapsePulmonaryCDDetected1517() {
		return relapsePulmonaryCDDetected1517;
	}
	public void setRelapsePulmonaryCDDetected1517(
			Integer relapsePulmonaryCDDetected1517) {
		this.relapsePulmonaryCDDetected1517 = relapsePulmonaryCDDetected1517;
	}
	public Integer getRelapseExtrapulmonaryDetected() {
		return relapseExtrapulmonaryDetected;
	}
	public void setRelapseExtrapulmonaryDetected(
			Integer relapseExtrapulmonaryDetected) {
		this.relapseExtrapulmonaryDetected = relapseExtrapulmonaryDetected;
	}
	public Integer getRelapseExtrapulmonaryDetected04() {
		return relapseExtrapulmonaryDetected04;
	}
	public void setRelapseExtrapulmonaryDetected04(
			Integer relapseExtrapulmonaryDetected04) {
		this.relapseExtrapulmonaryDetected04 = relapseExtrapulmonaryDetected04;
	}
	public Integer getRelapseExtrapulmonaryDetected0514() {
		return relapseExtrapulmonaryDetected0514;
	}
	public void setRelapseExtrapulmonaryDetected0514(
			Integer relapseExtrapulmonaryDetected0514) {
		this.relapseExtrapulmonaryDetected0514 = relapseExtrapulmonaryDetected0514;
	}
	public Integer getRelapseExtrapulmonaryDetected1517() {
		return relapseExtrapulmonaryDetected1517;
	}
	public void setRelapseExtrapulmonaryDetected1517(
			Integer relapseExtrapulmonaryDetected1517) {
		this.relapseExtrapulmonaryDetected1517 = relapseExtrapulmonaryDetected1517;
	}
	public Integer getRelapseAllDetected() {
		return relapseAllDetected;
	}
	public void setRelapseAllDetected(Integer relapseAllDetected) {
		this.relapseAllDetected = relapseAllDetected;
	}
	public Integer getRelapseAllDetected04() {
		return relapseAllDetected04;
	}
	public void setRelapseAllDetected04(Integer relapseAllDetected04) {
		this.relapseAllDetected04 = relapseAllDetected04;
	}
	public Integer getRelapseAllDetected0514() {
		return relapseAllDetected0514;
	}
	public void setRelapseAllDetected0514(Integer relapseAllDetected0514) {
		this.relapseAllDetected0514 = relapseAllDetected0514;
	}
	public Integer getRelapseAllDetected1517() {
		return relapseAllDetected1517;
	}
	public void setRelapseAllDetected1517(Integer relapseAllDetected1517) {
		this.relapseAllDetected1517 = relapseAllDetected1517;
	}
	public Integer getRelapsePulmonaryBCEligible() {
		return relapsePulmonaryBCEligible;
	}
	public void setRelapsePulmonaryBCEligible(Integer relapsePulmonaryBCEligible) {
		this.relapsePulmonaryBCEligible = relapsePulmonaryBCEligible;
	}
	public Integer getRelapsePulmonaryBCEligible04() {
		return relapsePulmonaryBCEligible04;
	}
	public void setRelapsePulmonaryBCEligible04(Integer relapsePulmonaryBCEligible04) {
		this.relapsePulmonaryBCEligible04 = relapsePulmonaryBCEligible04;
	}
	public Integer getRelapsePulmonaryBCEligible0514() {
		return relapsePulmonaryBCEligible0514;
	}
	public void setRelapsePulmonaryBCEligible0514(
			Integer relapsePulmonaryBCEligible0514) {
		this.relapsePulmonaryBCEligible0514 = relapsePulmonaryBCEligible0514;
	}
	public Integer getRelapsePulmonaryBCEligible1517() {
		return relapsePulmonaryBCEligible1517;
	}
	public void setRelapsePulmonaryBCEligible1517(
			Integer relapsePulmonaryBCEligible1517) {
		this.relapsePulmonaryBCEligible1517 = relapsePulmonaryBCEligible1517;
	}
	public Integer getRelapsePulmonaryCDEligible() {
		return relapsePulmonaryCDEligible;
	}
	public void setRelapsePulmonaryCDEligible(Integer relapsePulmonaryCDEligible) {
		this.relapsePulmonaryCDEligible = relapsePulmonaryCDEligible;
	}
	public Integer getRelapsePulmonaryCDEligible04() {
		return relapsePulmonaryCDEligible04;
	}
	public void setRelapsePulmonaryCDEligible04(Integer relapsePulmonaryCDEligible04) {
		this.relapsePulmonaryCDEligible04 = relapsePulmonaryCDEligible04;
	}
	public Integer getRelapsePulmonaryCDEligible0514() {
		return relapsePulmonaryCDEligible0514;
	}
	public void setRelapsePulmonaryCDEligible0514(
			Integer relapsePulmonaryCDEligible0514) {
		this.relapsePulmonaryCDEligible0514 = relapsePulmonaryCDEligible0514;
	}
	public Integer getRelapsePulmonaryCDEligible1517() {
		return relapsePulmonaryCDEligible1517;
	}
	public void setRelapsePulmonaryCDEligible1517(
			Integer relapsePulmonaryCDEligible1517) {
		this.relapsePulmonaryCDEligible1517 = relapsePulmonaryCDEligible1517;
	}
	public Integer getRelapseExtrapulmonaryEligible() {
		return relapseExtrapulmonaryEligible;
	}
	public void setRelapseExtrapulmonaryEligible(
			Integer relapseExtrapulmonaryEligible) {
		this.relapseExtrapulmonaryEligible = relapseExtrapulmonaryEligible;
	}
	public Integer getRelapseExtrapulmonaryEligible04() {
		return relapseExtrapulmonaryEligible04;
	}
	public void setRelapseExtrapulmonaryEligible04(
			Integer relapseExtrapulmonaryEligible04) {
		this.relapseExtrapulmonaryEligible04 = relapseExtrapulmonaryEligible04;
	}
	public Integer getRelapseExtrapulmonaryEligible0514() {
		return relapseExtrapulmonaryEligible0514;
	}
	public void setRelapseExtrapulmonaryEligible0514(
			Integer relapseExtrapulmonaryEligible0514) {
		this.relapseExtrapulmonaryEligible0514 = relapseExtrapulmonaryEligible0514;
	}
	public Integer getRelapseExtrapulmonaryEligible1517() {
		return relapseExtrapulmonaryEligible1517;
	}
	public void setRelapseExtrapulmonaryEligible1517(
			Integer relapseExtrapulmonaryEligible1517) {
		this.relapseExtrapulmonaryEligible1517 = relapseExtrapulmonaryEligible1517;
	}
	public Integer getRelapseAllEligible() {
		return relapseAllEligible;
	}
	public void setRelapseAllEligible(Integer relapseAllEligible) {
		this.relapseAllEligible = relapseAllEligible;
	}
	public Integer getRelapseAllEligible04() {
		return relapseAllEligible04;
	}
	public void setRelapseAllEligible04(Integer relapseAllEligible04) {
		this.relapseAllEligible04 = relapseAllEligible04;
	}
	public Integer getRelapseAllEligible0514() {
		return relapseAllEligible0514;
	}
	public void setRelapseAllEligible0514(Integer relapseAllEligible0514) {
		this.relapseAllEligible0514 = relapseAllEligible0514;
	}
	public Integer getRelapseAllEligible1517() {
		return relapseAllEligible1517;
	}
	public void setRelapseAllEligible1517(Integer relapseAllEligible1517) {
		this.relapseAllEligible1517 = relapseAllEligible1517;
	}
	public Integer getRelapsePulmonaryBCCured() {
		return relapsePulmonaryBCCured;
	}
	public void setRelapsePulmonaryBCCured(Integer relapsePulmonaryBCCured) {
		this.relapsePulmonaryBCCured = relapsePulmonaryBCCured;
	}
	public Integer getRelapsePulmonaryBCCured04() {
		return relapsePulmonaryBCCured04;
	}
	public void setRelapsePulmonaryBCCured04(Integer relapsePulmonaryBCCured04) {
		this.relapsePulmonaryBCCured04 = relapsePulmonaryBCCured04;
	}
	public Integer getRelapsePulmonaryBCCured0514() {
		return relapsePulmonaryBCCured0514;
	}
	public void setRelapsePulmonaryBCCured0514(Integer relapsePulmonaryBCCured0514) {
		this.relapsePulmonaryBCCured0514 = relapsePulmonaryBCCured0514;
	}
	public Integer getRelapsePulmonaryBCCured1517() {
		return relapsePulmonaryBCCured1517;
	}
	public void setRelapsePulmonaryBCCured1517(Integer relapsePulmonaryBCCured1517) {
		this.relapsePulmonaryBCCured1517 = relapsePulmonaryBCCured1517;
	}
	public Integer getRelapsePulmonaryCDCured() {
		return relapsePulmonaryCDCured;
	}
	public void setRelapsePulmonaryCDCured(Integer relapsePulmonaryCDCured) {
		this.relapsePulmonaryCDCured = relapsePulmonaryCDCured;
	}
	public Integer getRelapsePulmonaryCDCured04() {
		return relapsePulmonaryCDCured04;
	}
	public void setRelapsePulmonaryCDCured04(Integer relapsePulmonaryCDCured04) {
		this.relapsePulmonaryCDCured04 = relapsePulmonaryCDCured04;
	}
	public Integer getRelapsePulmonaryCDCured0514() {
		return relapsePulmonaryCDCured0514;
	}
	public void setRelapsePulmonaryCDCured0514(Integer relapsePulmonaryCDCured0514) {
		this.relapsePulmonaryCDCured0514 = relapsePulmonaryCDCured0514;
	}
	public Integer getRelapsePulmonaryCDCured1517() {
		return relapsePulmonaryCDCured1517;
	}
	public void setRelapsePulmonaryCDCured1517(Integer relapsePulmonaryCDCured1517) {
		this.relapsePulmonaryCDCured1517 = relapsePulmonaryCDCured1517;
	}
	public Integer getRelapseExtrapulmonaryCured() {
		return relapseExtrapulmonaryCured;
	}
	public void setRelapseExtrapulmonaryCured(Integer relapseExtrapulmonaryCured) {
		this.relapseExtrapulmonaryCured = relapseExtrapulmonaryCured;
	}
	public Integer getRelapseExtrapulmonaryCured04() {
		return relapseExtrapulmonaryCured04;
	}
	public void setRelapseExtrapulmonaryCured04(Integer relapseExtrapulmonaryCured04) {
		this.relapseExtrapulmonaryCured04 = relapseExtrapulmonaryCured04;
	}
	public Integer getRelapseExtrapulmonaryCured0514() {
		return relapseExtrapulmonaryCured0514;
	}
	public void setRelapseExtrapulmonaryCured0514(
			Integer relapseExtrapulmonaryCured0514) {
		this.relapseExtrapulmonaryCured0514 = relapseExtrapulmonaryCured0514;
	}
	public Integer getRelapseExtrapulmonaryCured1517() {
		return relapseExtrapulmonaryCured1517;
	}
	public void setRelapseExtrapulmonaryCured1517(
			Integer relapseExtrapulmonaryCured1517) {
		this.relapseExtrapulmonaryCured1517 = relapseExtrapulmonaryCured1517;
	}
	public Integer getRelapseAllCured() {
		return relapseAllCured;
	}
	public void setRelapseAllCured(Integer relapseAllCured) {
		this.relapseAllCured = relapseAllCured;
	}
	public Integer getRelapseAllCured04() {
		return relapseAllCured04;
	}
	public void setRelapseAllCured04(Integer relapseAllCured04) {
		this.relapseAllCured04 = relapseAllCured04;
	}
	public Integer getRelapseAllCured0514() {
		return relapseAllCured0514;
	}
	public void setRelapseAllCured0514(Integer relapseAllCured0514) {
		this.relapseAllCured0514 = relapseAllCured0514;
	}
	public Integer getRelapseAllCured1517() {
		return relapseAllCured1517;
	}
	public void setRelapseAllCured1517(Integer relapseAllCured1517) {
		this.relapseAllCured1517 = relapseAllCured1517;
	}
	public Integer getRelapsePulmonaryBCCompleted() {
		return relapsePulmonaryBCCompleted;
	}
	public void setRelapsePulmonaryBCCompleted(Integer relapsePulmonaryBCCompleted) {
		this.relapsePulmonaryBCCompleted = relapsePulmonaryBCCompleted;
	}
	public Integer getRelapsePulmonaryBCCompleted04() {
		return relapsePulmonaryBCCompleted04;
	}
	public void setRelapsePulmonaryBCCompleted04(
			Integer relapsePulmonaryBCCompleted04) {
		this.relapsePulmonaryBCCompleted04 = relapsePulmonaryBCCompleted04;
	}
	public Integer getRelapsePulmonaryBCCompleted0514() {
		return relapsePulmonaryBCCompleted0514;
	}
	public void setRelapsePulmonaryBCCompleted0514(
			Integer relapsePulmonaryBCCompleted0514) {
		this.relapsePulmonaryBCCompleted0514 = relapsePulmonaryBCCompleted0514;
	}
	public Integer getRelapsePulmonaryBCCompleted1517() {
		return relapsePulmonaryBCCompleted1517;
	}
	public void setRelapsePulmonaryBCCompleted1517(
			Integer relapsePulmonaryBCCompleted1517) {
		this.relapsePulmonaryBCCompleted1517 = relapsePulmonaryBCCompleted1517;
	}
	public Integer getRelapsePulmonaryCDCompleted() {
		return relapsePulmonaryCDCompleted;
	}
	public void setRelapsePulmonaryCDCompleted(Integer relapsePulmonaryCDCompleted) {
		this.relapsePulmonaryCDCompleted = relapsePulmonaryCDCompleted;
	}
	public Integer getRelapsePulmonaryCDCompleted04() {
		return relapsePulmonaryCDCompleted04;
	}
	public void setRelapsePulmonaryCDCompleted04(
			Integer relapsePulmonaryCDCompleted04) {
		this.relapsePulmonaryCDCompleted04 = relapsePulmonaryCDCompleted04;
	}
	public Integer getRelapsePulmonaryCDCompleted0514() {
		return relapsePulmonaryCDCompleted0514;
	}
	public void setRelapsePulmonaryCDCompleted0514(
			Integer relapsePulmonaryCDCompleted0514) {
		this.relapsePulmonaryCDCompleted0514 = relapsePulmonaryCDCompleted0514;
	}
	public Integer getRelapsePulmonaryCDCompleted1517() {
		return relapsePulmonaryCDCompleted1517;
	}
	public void setRelapsePulmonaryCDCompleted1517(
			Integer relapsePulmonaryCDCompleted1517) {
		this.relapsePulmonaryCDCompleted1517 = relapsePulmonaryCDCompleted1517;
	}
	public Integer getRelapseExtrapulmonaryCompleted() {
		return relapseExtrapulmonaryCompleted;
	}
	public void setRelapseExtrapulmonaryCompleted(
			Integer relapseExtrapulmonaryCompleted) {
		this.relapseExtrapulmonaryCompleted = relapseExtrapulmonaryCompleted;
	}
	public Integer getRelapseExtrapulmonaryCompleted04() {
		return relapseExtrapulmonaryCompleted04;
	}
	public void setRelapseExtrapulmonaryCompleted04(
			Integer relapseExtrapulmonaryCompleted04) {
		this.relapseExtrapulmonaryCompleted04 = relapseExtrapulmonaryCompleted04;
	}
	public Integer getRelapseExtrapulmonaryCompleted0514() {
		return relapseExtrapulmonaryCompleted0514;
	}
	public void setRelapseExtrapulmonaryCompleted0514(
			Integer relapseExtrapulmonaryCompleted0514) {
		this.relapseExtrapulmonaryCompleted0514 = relapseExtrapulmonaryCompleted0514;
	}
	public Integer getRelapseExtrapulmonaryCompleted1517() {
		return relapseExtrapulmonaryCompleted1517;
	}
	public void setRelapseExtrapulmonaryCompleted1517(
			Integer relapseExtrapulmonaryCompleted1517) {
		this.relapseExtrapulmonaryCompleted1517 = relapseExtrapulmonaryCompleted1517;
	}
	public Integer getRelapseAllCompleted() {
		return relapseAllCompleted;
	}
	public void setRelapseAllCompleted(Integer relapseAllCompleted) {
		this.relapseAllCompleted = relapseAllCompleted;
	}
	public Integer getRelapseAllCompleted04() {
		return relapseAllCompleted04;
	}
	public void setRelapseAllCompleted04(Integer relapseAllCompleted04) {
		this.relapseAllCompleted04 = relapseAllCompleted04;
	}
	public Integer getRelapseAllCompleted0514() {
		return relapseAllCompleted0514;
	}
	public void setRelapseAllCompleted0514(Integer relapseAllCompleted0514) {
		this.relapseAllCompleted0514 = relapseAllCompleted0514;
	}
	public Integer getRelapseAllCompleted1517() {
		return relapseAllCompleted1517;
	}
	public void setRelapseAllCompleted1517(Integer relapseAllCompleted1517) {
		this.relapseAllCompleted1517 = relapseAllCompleted1517;
	}
	public Integer getRelapsePulmonaryBCDiedTB() {
		return relapsePulmonaryBCDiedTB;
	}
	public void setRelapsePulmonaryBCDiedTB(Integer relapsePulmonaryBCDiedTB) {
		this.relapsePulmonaryBCDiedTB = relapsePulmonaryBCDiedTB;
	}
	public Integer getRelapsePulmonaryBCDiedTB04() {
		return relapsePulmonaryBCDiedTB04;
	}
	public void setRelapsePulmonaryBCDiedTB04(Integer relapsePulmonaryBCDiedTB04) {
		this.relapsePulmonaryBCDiedTB04 = relapsePulmonaryBCDiedTB04;
	}
	public Integer getRelapsePulmonaryBCDiedTB0514() {
		return relapsePulmonaryBCDiedTB0514;
	}
	public void setRelapsePulmonaryBCDiedTB0514(Integer relapsePulmonaryBCDiedTB0514) {
		this.relapsePulmonaryBCDiedTB0514 = relapsePulmonaryBCDiedTB0514;
	}
	public Integer getRelapsePulmonaryBCDiedTB1517() {
		return relapsePulmonaryBCDiedTB1517;
	}
	public void setRelapsePulmonaryBCDiedTB1517(Integer relapsePulmonaryBCDiedTB1517) {
		this.relapsePulmonaryBCDiedTB1517 = relapsePulmonaryBCDiedTB1517;
	}
	public Integer getRelapsePulmonaryCDDiedTB() {
		return relapsePulmonaryCDDiedTB;
	}
	public void setRelapsePulmonaryCDDiedTB(Integer relapsePulmonaryCDDiedTB) {
		this.relapsePulmonaryCDDiedTB = relapsePulmonaryCDDiedTB;
	}
	public Integer getRelapsePulmonaryCDDiedTB04() {
		return relapsePulmonaryCDDiedTB04;
	}
	public void setRelapsePulmonaryCDDiedTB04(Integer relapsePulmonaryCDDiedTB04) {
		this.relapsePulmonaryCDDiedTB04 = relapsePulmonaryCDDiedTB04;
	}
	public Integer getRelapsePulmonaryCDDiedTB0514() {
		return relapsePulmonaryCDDiedTB0514;
	}
	public void setRelapsePulmonaryCDDiedTB0514(Integer relapsePulmonaryCDDiedTB0514) {
		this.relapsePulmonaryCDDiedTB0514 = relapsePulmonaryCDDiedTB0514;
	}
	public Integer getRelapsePulmonaryCDDiedTB1517() {
		return relapsePulmonaryCDDiedTB1517;
	}
	public void setRelapsePulmonaryCDDiedTB1517(Integer relapsePulmonaryCDDiedTB1517) {
		this.relapsePulmonaryCDDiedTB1517 = relapsePulmonaryCDDiedTB1517;
	}
	public Integer getRelapseExtrapulmonaryDiedTB() {
		return relapseExtrapulmonaryDiedTB;
	}
	public void setRelapseExtrapulmonaryDiedTB(Integer relapseExtrapulmonaryDiedTB) {
		this.relapseExtrapulmonaryDiedTB = relapseExtrapulmonaryDiedTB;
	}
	public Integer getRelapseExtrapulmonaryDiedTB04() {
		return relapseExtrapulmonaryDiedTB04;
	}
	public void setRelapseExtrapulmonaryDiedTB04(
			Integer relapseExtrapulmonaryDiedTB04) {
		this.relapseExtrapulmonaryDiedTB04 = relapseExtrapulmonaryDiedTB04;
	}
	public Integer getRelapseExtrapulmonaryDiedTB0514() {
		return relapseExtrapulmonaryDiedTB0514;
	}
	public void setRelapseExtrapulmonaryDiedTB0514(
			Integer relapseExtrapulmonaryDiedTB0514) {
		this.relapseExtrapulmonaryDiedTB0514 = relapseExtrapulmonaryDiedTB0514;
	}
	public Integer getRelapseExtrapulmonaryDiedTB1517() {
		return relapseExtrapulmonaryDiedTB1517;
	}
	public void setRelapseExtrapulmonaryDiedTB1517(
			Integer relapseExtrapulmonaryDiedTB1517) {
		this.relapseExtrapulmonaryDiedTB1517 = relapseExtrapulmonaryDiedTB1517;
	}
	public Integer getRelapseAllDiedTB() {
		return relapseAllDiedTB;
	}
	public void setRelapseAllDiedTB(Integer relapseAllDiedTB) {
		this.relapseAllDiedTB = relapseAllDiedTB;
	}
	public Integer getRelapseAllDiedTB04() {
		return relapseAllDiedTB04;
	}
	public void setRelapseAllDiedTB04(Integer relapseAllDiedTB04) {
		this.relapseAllDiedTB04 = relapseAllDiedTB04;
	}
	public Integer getRelapseAllDiedTB0514() {
		return relapseAllDiedTB0514;
	}
	public void setRelapseAllDiedTB0514(Integer relapseAllDiedTB0514) {
		this.relapseAllDiedTB0514 = relapseAllDiedTB0514;
	}
	public Integer getRelapseAllDiedTB1517() {
		return relapseAllDiedTB1517;
	}
	public void setRelapseAllDiedTB1517(Integer relapseAllDiedTB1517) {
		this.relapseAllDiedTB1517 = relapseAllDiedTB1517;
	}
	public Integer getRelapsePulmonaryBCDiedNotTB() {
		return relapsePulmonaryBCDiedNotTB;
	}
	public void setRelapsePulmonaryBCDiedNotTB(Integer relapsePulmonaryBCDiedNotTB) {
		this.relapsePulmonaryBCDiedNotTB = relapsePulmonaryBCDiedNotTB;
	}
	public Integer getRelapsePulmonaryBCDiedNotTB04() {
		return relapsePulmonaryBCDiedNotTB04;
	}
	public void setRelapsePulmonaryBCDiedNotTB04(
			Integer relapsePulmonaryBCDiedNotTB04) {
		this.relapsePulmonaryBCDiedNotTB04 = relapsePulmonaryBCDiedNotTB04;
	}
	public Integer getRelapsePulmonaryBCDiedNotTB0514() {
		return relapsePulmonaryBCDiedNotTB0514;
	}
	public void setRelapsePulmonaryBCDiedNotTB0514(
			Integer relapsePulmonaryBCDiedNotTB0514) {
		this.relapsePulmonaryBCDiedNotTB0514 = relapsePulmonaryBCDiedNotTB0514;
	}
	public Integer getRelapsePulmonaryBCDiedNotTB1517() {
		return relapsePulmonaryBCDiedNotTB1517;
	}
	public void setRelapsePulmonaryBCDiedNotTB1517(
			Integer relapsePulmonaryBCDiedNotTB1517) {
		this.relapsePulmonaryBCDiedNotTB1517 = relapsePulmonaryBCDiedNotTB1517;
	}
	public Integer getRelapsePulmonaryCDDiedNotTB() {
		return relapsePulmonaryCDDiedNotTB;
	}
	public void setRelapsePulmonaryCDDiedNotTB(Integer relapsePulmonaryCDDiedNotTB) {
		this.relapsePulmonaryCDDiedNotTB = relapsePulmonaryCDDiedNotTB;
	}
	public Integer getRelapsePulmonaryCDDiedNotTB04() {
		return relapsePulmonaryCDDiedNotTB04;
	}
	public void setRelapsePulmonaryCDDiedNotTB04(
			Integer relapsePulmonaryCDDiedNotTB04) {
		this.relapsePulmonaryCDDiedNotTB04 = relapsePulmonaryCDDiedNotTB04;
	}
	public Integer getRelapsePulmonaryCDDiedNotTB0514() {
		return relapsePulmonaryCDDiedNotTB0514;
	}
	public void setRelapsePulmonaryCDDiedNotTB0514(
			Integer relapsePulmonaryCDDiedNotTB0514) {
		this.relapsePulmonaryCDDiedNotTB0514 = relapsePulmonaryCDDiedNotTB0514;
	}
	public Integer getRelapsePulmonaryCDDiedNotTB1517() {
		return relapsePulmonaryCDDiedNotTB1517;
	}
	public void setRelapsePulmonaryCDDiedNotTB1517(
			Integer relapsePulmonaryCDDiedNotTB1517) {
		this.relapsePulmonaryCDDiedNotTB1517 = relapsePulmonaryCDDiedNotTB1517;
	}
	public Integer getRelapseExtrapulmonaryDiedNotTB() {
		return relapseExtrapulmonaryDiedNotTB;
	}
	public void setRelapseExtrapulmonaryDiedNotTB(
			Integer relapseExtrapulmonaryDiedNotTB) {
		this.relapseExtrapulmonaryDiedNotTB = relapseExtrapulmonaryDiedNotTB;
	}
	public Integer getRelapseExtrapulmonaryDiedNotTB04() {
		return relapseExtrapulmonaryDiedNotTB04;
	}
	public void setRelapseExtrapulmonaryDiedNotTB04(
			Integer relapseExtrapulmonaryDiedNotTB04) {
		this.relapseExtrapulmonaryDiedNotTB04 = relapseExtrapulmonaryDiedNotTB04;
	}
	public Integer getRelapseExtrapulmonaryDiedNotTB0514() {
		return relapseExtrapulmonaryDiedNotTB0514;
	}
	public void setRelapseExtrapulmonaryDiedNotTB0514(
			Integer relapseExtrapulmonaryDiedNotTB0514) {
		this.relapseExtrapulmonaryDiedNotTB0514 = relapseExtrapulmonaryDiedNotTB0514;
	}
	public Integer getRelapseExtrapulmonaryDiedNotTB1517() {
		return relapseExtrapulmonaryDiedNotTB1517;
	}
	public void setRelapseExtrapulmonaryDiedNotTB1517(
			Integer relapseExtrapulmonaryDiedNotTB1517) {
		this.relapseExtrapulmonaryDiedNotTB1517 = relapseExtrapulmonaryDiedNotTB1517;
	}
	public Integer getRelapseAllDiedNotTB() {
		return relapseAllDiedNotTB;
	}
	public void setRelapseAllDiedNotTB(Integer relapseAllDiedNotTB) {
		this.relapseAllDiedNotTB = relapseAllDiedNotTB;
	}
	public Integer getRelapseAllDiedNotTB04() {
		return relapseAllDiedNotTB04;
	}
	public void setRelapseAllDiedNotTB04(Integer relapseAllDiedNotTB04) {
		this.relapseAllDiedNotTB04 = relapseAllDiedNotTB04;
	}
	public Integer getRelapseAllDiedNotTB0514() {
		return relapseAllDiedNotTB0514;
	}
	public void setRelapseAllDiedNotTB0514(Integer relapseAllDiedNotTB0514) {
		this.relapseAllDiedNotTB0514 = relapseAllDiedNotTB0514;
	}
	public Integer getRelapseAllDiedNotTB1517() {
		return relapseAllDiedNotTB1517;
	}
	public void setRelapseAllDiedNotTB1517(Integer relapseAllDiedNotTB1517) {
		this.relapseAllDiedNotTB1517 = relapseAllDiedNotTB1517;
	}
	public Integer getRelapsePulmonaryBCFailed() {
		return relapsePulmonaryBCFailed;
	}
	public void setRelapsePulmonaryBCFailed(Integer relapsePulmonaryBCFailed) {
		this.relapsePulmonaryBCFailed = relapsePulmonaryBCFailed;
	}
	public Integer getRelapsePulmonaryBCFailed04() {
		return relapsePulmonaryBCFailed04;
	}
	public void setRelapsePulmonaryBCFailed04(Integer relapsePulmonaryBCFailed04) {
		this.relapsePulmonaryBCFailed04 = relapsePulmonaryBCFailed04;
	}
	public Integer getRelapsePulmonaryBCFailed0514() {
		return relapsePulmonaryBCFailed0514;
	}
	public void setRelapsePulmonaryBCFailed0514(Integer relapsePulmonaryBCFailed0514) {
		this.relapsePulmonaryBCFailed0514 = relapsePulmonaryBCFailed0514;
	}
	public Integer getRelapsePulmonaryBCFailed1517() {
		return relapsePulmonaryBCFailed1517;
	}
	public void setRelapsePulmonaryBCFailed1517(Integer relapsePulmonaryBCFailed1517) {
		this.relapsePulmonaryBCFailed1517 = relapsePulmonaryBCFailed1517;
	}
	public Integer getRelapsePulmonaryCDFailed() {
		return relapsePulmonaryCDFailed;
	}
	public void setRelapsePulmonaryCDFailed(Integer relapsePulmonaryCDFailed) {
		this.relapsePulmonaryCDFailed = relapsePulmonaryCDFailed;
	}
	public Integer getRelapsePulmonaryCDFailed04() {
		return relapsePulmonaryCDFailed04;
	}
	public void setRelapsePulmonaryCDFailed04(Integer relapsePulmonaryCDFailed04) {
		this.relapsePulmonaryCDFailed04 = relapsePulmonaryCDFailed04;
	}
	public Integer getRelapsePulmonaryCDFailed0514() {
		return relapsePulmonaryCDFailed0514;
	}
	public void setRelapsePulmonaryCDFailed0514(Integer relapsePulmonaryCDFailed0514) {
		this.relapsePulmonaryCDFailed0514 = relapsePulmonaryCDFailed0514;
	}
	public Integer getRelapsePulmonaryCDFailed1517() {
		return relapsePulmonaryCDFailed1517;
	}
	public void setRelapsePulmonaryCDFailed1517(Integer relapsePulmonaryCDFailed1517) {
		this.relapsePulmonaryCDFailed1517 = relapsePulmonaryCDFailed1517;
	}
	public Integer getRelapseExtrapulmonaryFailed() {
		return relapseExtrapulmonaryFailed;
	}
	public void setRelapseExtrapulmonaryFailed(Integer relapseExtrapulmonaryFailed) {
		this.relapseExtrapulmonaryFailed = relapseExtrapulmonaryFailed;
	}
	public Integer getRelapseExtrapulmonaryFailed04() {
		return relapseExtrapulmonaryFailed04;
	}
	public void setRelapseExtrapulmonaryFailed04(
			Integer relapseExtrapulmonaryFailed04) {
		this.relapseExtrapulmonaryFailed04 = relapseExtrapulmonaryFailed04;
	}
	public Integer getRelapseExtrapulmonaryFailed0514() {
		return relapseExtrapulmonaryFailed0514;
	}
	public void setRelapseExtrapulmonaryFailed0514(
			Integer relapseExtrapulmonaryFailed0514) {
		this.relapseExtrapulmonaryFailed0514 = relapseExtrapulmonaryFailed0514;
	}
	public Integer getRelapseExtrapulmonaryFailed1517() {
		return relapseExtrapulmonaryFailed1517;
	}
	public void setRelapseExtrapulmonaryFailed1517(
			Integer relapseExtrapulmonaryFailed1517) {
		this.relapseExtrapulmonaryFailed1517 = relapseExtrapulmonaryFailed1517;
	}
	public Integer getRelapseAllFailed() {
		return relapseAllFailed;
	}
	public void setRelapseAllFailed(Integer relapseAllFailed) {
		this.relapseAllFailed = relapseAllFailed;
	}
	public Integer getRelapseAllFailed04() {
		return relapseAllFailed04;
	}
	public void setRelapseAllFailed04(Integer relapseAllFailed04) {
		this.relapseAllFailed04 = relapseAllFailed04;
	}
	public Integer getRelapseAllFailed0514() {
		return relapseAllFailed0514;
	}
	public void setRelapseAllFailed0514(Integer relapseAllFailed0514) {
		this.relapseAllFailed0514 = relapseAllFailed0514;
	}
	public Integer getRelapseAllFailed1517() {
		return relapseAllFailed1517;
	}
	public void setRelapseAllFailed1517(Integer relapseAllFailed1517) {
		this.relapseAllFailed1517 = relapseAllFailed1517;
	}
	public Integer getRelapsePulmonaryBCDefaulted() {
		return relapsePulmonaryBCDefaulted;
	}
	public void setRelapsePulmonaryBCDefaulted(Integer relapsePulmonaryBCDefaulted) {
		this.relapsePulmonaryBCDefaulted = relapsePulmonaryBCDefaulted;
	}
	public Integer getRelapsePulmonaryBCDefaulted04() {
		return relapsePulmonaryBCDefaulted04;
	}
	public void setRelapsePulmonaryBCDefaulted04(
			Integer relapsePulmonaryBCDefaulted04) {
		this.relapsePulmonaryBCDefaulted04 = relapsePulmonaryBCDefaulted04;
	}
	public Integer getRelapsePulmonaryBCDefaulted0514() {
		return relapsePulmonaryBCDefaulted0514;
	}
	public void setRelapsePulmonaryBCDefaulted0514(
			Integer relapsePulmonaryBCDefaulted0514) {
		this.relapsePulmonaryBCDefaulted0514 = relapsePulmonaryBCDefaulted0514;
	}
	public Integer getRelapsePulmonaryBCDefaulted1517() {
		return relapsePulmonaryBCDefaulted1517;
	}
	public void setRelapsePulmonaryBCDefaulted1517(
			Integer relapsePulmonaryBCDefaulted1517) {
		this.relapsePulmonaryBCDefaulted1517 = relapsePulmonaryBCDefaulted1517;
	}
	public Integer getRelapsePulmonaryCDDefaulted() {
		return relapsePulmonaryCDDefaulted;
	}
	public void setRelapsePulmonaryCDDefaulted(Integer relapsePulmonaryCDDefaulted) {
		this.relapsePulmonaryCDDefaulted = relapsePulmonaryCDDefaulted;
	}
	public Integer getRelapsePulmonaryCDDefaulted04() {
		return relapsePulmonaryCDDefaulted04;
	}
	public void setRelapsePulmonaryCDDefaulted04(
			Integer relapsePulmonaryCDDefaulted04) {
		this.relapsePulmonaryCDDefaulted04 = relapsePulmonaryCDDefaulted04;
	}
	public Integer getRelapsePulmonaryCDDefaulted0514() {
		return relapsePulmonaryCDDefaulted0514;
	}
	public void setRelapsePulmonaryCDDefaulted0514(
			Integer relapsePulmonaryCDDefaulted0514) {
		this.relapsePulmonaryCDDefaulted0514 = relapsePulmonaryCDDefaulted0514;
	}
	public Integer getRelapsePulmonaryCDDefaulted1517() {
		return relapsePulmonaryCDDefaulted1517;
	}
	public void setRelapsePulmonaryCDDefaulted1517(
			Integer relapsePulmonaryCDDefaulted1517) {
		this.relapsePulmonaryCDDefaulted1517 = relapsePulmonaryCDDefaulted1517;
	}
	public Integer getRelapseExtrapulmonaryDefaulted() {
		return relapseExtrapulmonaryDefaulted;
	}
	public void setRelapseExtrapulmonaryDefaulted(
			Integer relapseExtrapulmonaryDefaulted) {
		this.relapseExtrapulmonaryDefaulted = relapseExtrapulmonaryDefaulted;
	}
	public Integer getRelapseExtrapulmonaryDefaulted04() {
		return relapseExtrapulmonaryDefaulted04;
	}
	public void setRelapseExtrapulmonaryDefaulted04(
			Integer relapseExtrapulmonaryDefaulted04) {
		this.relapseExtrapulmonaryDefaulted04 = relapseExtrapulmonaryDefaulted04;
	}
	public Integer getRelapseExtrapulmonaryDefaulted0514() {
		return relapseExtrapulmonaryDefaulted0514;
	}
	public void setRelapseExtrapulmonaryDefaulted0514(
			Integer relapseExtrapulmonaryDefaulted0514) {
		this.relapseExtrapulmonaryDefaulted0514 = relapseExtrapulmonaryDefaulted0514;
	}
	public Integer getRelapseExtrapulmonaryDefaulted1517() {
		return relapseExtrapulmonaryDefaulted1517;
	}
	public void setRelapseExtrapulmonaryDefaulted1517(
			Integer relapseExtrapulmonaryDefaulted1517) {
		this.relapseExtrapulmonaryDefaulted1517 = relapseExtrapulmonaryDefaulted1517;
	}
	public Integer getRelapseAllDefaulted() {
		return relapseAllDefaulted;
	}
	public void setRelapseAllDefaulted(Integer relapseAllDefaulted) {
		this.relapseAllDefaulted = relapseAllDefaulted;
	}
	public Integer getRelapseAllDefaulted04() {
		return relapseAllDefaulted04;
	}
	public void setRelapseAllDefaulted04(Integer relapseAllDefaulted04) {
		this.relapseAllDefaulted04 = relapseAllDefaulted04;
	}
	public Integer getRelapseAllDefaulted0514() {
		return relapseAllDefaulted0514;
	}
	public void setRelapseAllDefaulted0514(Integer relapseAllDefaulted0514) {
		this.relapseAllDefaulted0514 = relapseAllDefaulted0514;
	}
	public Integer getRelapseAllDefaulted1517() {
		return relapseAllDefaulted1517;
	}
	public void setRelapseAllDefaulted1517(Integer relapseAllDefaulted1517) {
		this.relapseAllDefaulted1517 = relapseAllDefaulted1517;
	}
	public Integer getRelapsePulmonaryBCTransferOut() {
		return relapsePulmonaryBCTransferOut;
	}
	public void setRelapsePulmonaryBCTransferOut(
			Integer relapsePulmonaryBCTransferOut) {
		this.relapsePulmonaryBCTransferOut = relapsePulmonaryBCTransferOut;
	}
	public Integer getRelapsePulmonaryBCTransferOut04() {
		return relapsePulmonaryBCTransferOut04;
	}
	public void setRelapsePulmonaryBCTransferOut04(
			Integer relapsePulmonaryBCTransferOut04) {
		this.relapsePulmonaryBCTransferOut04 = relapsePulmonaryBCTransferOut04;
	}
	public Integer getRelapsePulmonaryBCTransferOut0514() {
		return relapsePulmonaryBCTransferOut0514;
	}
	public void setRelapsePulmonaryBCTransferOut0514(
			Integer relapsePulmonaryBCTransferOut0514) {
		this.relapsePulmonaryBCTransferOut0514 = relapsePulmonaryBCTransferOut0514;
	}
	public Integer getRelapsePulmonaryBCTransferOut1517() {
		return relapsePulmonaryBCTransferOut1517;
	}
	public void setRelapsePulmonaryBCTransferOut1517(
			Integer relapsePulmonaryBCTransferOut1517) {
		this.relapsePulmonaryBCTransferOut1517 = relapsePulmonaryBCTransferOut1517;
	}
	public Integer getRelapsePulmonaryCDTransferOut() {
		return relapsePulmonaryCDTransferOut;
	}
	public void setRelapsePulmonaryCDTransferOut(
			Integer relapsePulmonaryCDTransferOut) {
		this.relapsePulmonaryCDTransferOut = relapsePulmonaryCDTransferOut;
	}
	public Integer getRelapsePulmonaryCDTransferOut04() {
		return relapsePulmonaryCDTransferOut04;
	}
	public void setRelapsePulmonaryCDTransferOut04(
			Integer relapsePulmonaryCDTransferOut04) {
		this.relapsePulmonaryCDTransferOut04 = relapsePulmonaryCDTransferOut04;
	}
	public Integer getRelapsePulmonaryCDTransferOut0514() {
		return relapsePulmonaryCDTransferOut0514;
	}
	public void setRelapsePulmonaryCDTransferOut0514(
			Integer relapsePulmonaryCDTransferOut0514) {
		this.relapsePulmonaryCDTransferOut0514 = relapsePulmonaryCDTransferOut0514;
	}
	public Integer getRelapsePulmonaryCDTransferOut1517() {
		return relapsePulmonaryCDTransferOut1517;
	}
	public void setRelapsePulmonaryCDTransferOut1517(
			Integer relapsePulmonaryCDTransferOut1517) {
		this.relapsePulmonaryCDTransferOut1517 = relapsePulmonaryCDTransferOut1517;
	}
	public Integer getRelapseExtrapulmonaryTransferOut() {
		return relapseExtrapulmonaryTransferOut;
	}
	public void setRelapseExtrapulmonaryTransferOut(
			Integer relapseExtrapulmonaryTransferOut) {
		this.relapseExtrapulmonaryTransferOut = relapseExtrapulmonaryTransferOut;
	}
	public Integer getRelapseExtrapulmonaryTransferOut04() {
		return relapseExtrapulmonaryTransferOut04;
	}
	public void setRelapseExtrapulmonaryTransferOut04(
			Integer relapseExtrapulmonaryTransferOut04) {
		this.relapseExtrapulmonaryTransferOut04 = relapseExtrapulmonaryTransferOut04;
	}
	public Integer getRelapseExtrapulmonaryTransferOut0514() {
		return relapseExtrapulmonaryTransferOut0514;
	}
	public void setRelapseExtrapulmonaryTransferOut0514(
			Integer relapseExtrapulmonaryTransferOut0514) {
		this.relapseExtrapulmonaryTransferOut0514 = relapseExtrapulmonaryTransferOut0514;
	}
	public Integer getRelapseExtrapulmonaryTransferOut1517() {
		return relapseExtrapulmonaryTransferOut1517;
	}
	public void setRelapseExtrapulmonaryTransferOut1517(
			Integer relapseExtrapulmonaryTransferOut1517) {
		this.relapseExtrapulmonaryTransferOut1517 = relapseExtrapulmonaryTransferOut1517;
	}
	public Integer getRelapseAllTransferOut() {
		return relapseAllTransferOut;
	}
	public void setRelapseAllTransferOut(Integer relapseAllTransferOut) {
		this.relapseAllTransferOut = relapseAllTransferOut;
	}
	public Integer getRelapseAllTransferOut04() {
		return relapseAllTransferOut04;
	}
	public void setRelapseAllTransferOut04(Integer relapseAllTransferOut04) {
		this.relapseAllTransferOut04 = relapseAllTransferOut04;
	}
	public Integer getRelapseAllTransferOut0514() {
		return relapseAllTransferOut0514;
	}
	public void setRelapseAllTransferOut0514(Integer relapseAllTransferOut0514) {
		this.relapseAllTransferOut0514 = relapseAllTransferOut0514;
	}
	public Integer getRelapseAllTransferOut1517() {
		return relapseAllTransferOut1517;
	}
	public void setRelapseAllTransferOut1517(Integer relapseAllTransferOut1517) {
		this.relapseAllTransferOut1517 = relapseAllTransferOut1517;
	}
	public Integer getRelapsePulmonaryBCCanceled() {
		return relapsePulmonaryBCCanceled;
	}
	public void setRelapsePulmonaryBCCanceled(Integer relapsePulmonaryBCCanceled) {
		this.relapsePulmonaryBCCanceled = relapsePulmonaryBCCanceled;
	}
	public Integer getRelapsePulmonaryBCCanceled04() {
		return relapsePulmonaryBCCanceled04;
	}
	public void setRelapsePulmonaryBCCanceled04(Integer relapsePulmonaryBCCanceled04) {
		this.relapsePulmonaryBCCanceled04 = relapsePulmonaryBCCanceled04;
	}
	public Integer getRelapsePulmonaryBCCanceled0514() {
		return relapsePulmonaryBCCanceled0514;
	}
	public void setRelapsePulmonaryBCCanceled0514(
			Integer relapsePulmonaryBCCanceled0514) {
		this.relapsePulmonaryBCCanceled0514 = relapsePulmonaryBCCanceled0514;
	}
	public Integer getRelapsePulmonaryBCCanceled1517() {
		return relapsePulmonaryBCCanceled1517;
	}
	public void setRelapsePulmonaryBCCanceled1517(
			Integer relapsePulmonaryBCCanceled1517) {
		this.relapsePulmonaryBCCanceled1517 = relapsePulmonaryBCCanceled1517;
	}
	public Integer getRelapsePulmonaryCDCanceled() {
		return relapsePulmonaryCDCanceled;
	}
	public void setRelapsePulmonaryCDCanceled(Integer relapsePulmonaryCDCanceled) {
		this.relapsePulmonaryCDCanceled = relapsePulmonaryCDCanceled;
	}
	public Integer getRelapsePulmonaryCDCanceled04() {
		return relapsePulmonaryCDCanceled04;
	}
	public void setRelapsePulmonaryCDCanceled04(Integer relapsePulmonaryCDCanceled04) {
		this.relapsePulmonaryCDCanceled04 = relapsePulmonaryCDCanceled04;
	}
	public Integer getRelapsePulmonaryCDCanceled0514() {
		return relapsePulmonaryCDCanceled0514;
	}
	public void setRelapsePulmonaryCDCanceled0514(
			Integer relapsePulmonaryCDCanceled0514) {
		this.relapsePulmonaryCDCanceled0514 = relapsePulmonaryCDCanceled0514;
	}
	public Integer getRelapsePulmonaryCDCanceled1517() {
		return relapsePulmonaryCDCanceled1517;
	}
	public void setRelapsePulmonaryCDCanceled1517(
			Integer relapsePulmonaryCDCanceled1517) {
		this.relapsePulmonaryCDCanceled1517 = relapsePulmonaryCDCanceled1517;
	}
	public Integer getRelapseExtrapulmonaryCanceled() {
		return relapseExtrapulmonaryCanceled;
	}
	public void setRelapseExtrapulmonaryCanceled(
			Integer relapseExtrapulmonaryCanceled) {
		this.relapseExtrapulmonaryCanceled = relapseExtrapulmonaryCanceled;
	}
	public Integer getRelapseExtrapulmonaryCanceled04() {
		return relapseExtrapulmonaryCanceled04;
	}
	public void setRelapseExtrapulmonaryCanceled04(
			Integer relapseExtrapulmonaryCanceled04) {
		this.relapseExtrapulmonaryCanceled04 = relapseExtrapulmonaryCanceled04;
	}
	public Integer getRelapseExtrapulmonaryCanceled0514() {
		return relapseExtrapulmonaryCanceled0514;
	}
	public void setRelapseExtrapulmonaryCanceled0514(
			Integer relapseExtrapulmonaryCanceled0514) {
		this.relapseExtrapulmonaryCanceled0514 = relapseExtrapulmonaryCanceled0514;
	}
	public Integer getRelapseExtrapulmonaryCanceled1517() {
		return relapseExtrapulmonaryCanceled1517;
	}
	public void setRelapseExtrapulmonaryCanceled1517(
			Integer relapseExtrapulmonaryCanceled1517) {
		this.relapseExtrapulmonaryCanceled1517 = relapseExtrapulmonaryCanceled1517;
	}
	public Integer getRelapseAllCanceled() {
		return relapseAllCanceled;
	}
	public void setRelapseAllCanceled(Integer relapseAllCanceled) {
		this.relapseAllCanceled = relapseAllCanceled;
	}
	public Integer getRelapseAllCanceled04() {
		return relapseAllCanceled04;
	}
	public void setRelapseAllCanceled04(Integer relapseAllCanceled04) {
		this.relapseAllCanceled04 = relapseAllCanceled04;
	}
	public Integer getRelapseAllCanceled0514() {
		return relapseAllCanceled0514;
	}
	public void setRelapseAllCanceled0514(Integer relapseAllCanceled0514) {
		this.relapseAllCanceled0514 = relapseAllCanceled0514;
	}
	public Integer getRelapseAllCanceled1517() {
		return relapseAllCanceled1517;
	}
	public void setRelapseAllCanceled1517(Integer relapseAllCanceled1517) {
		this.relapseAllCanceled1517 = relapseAllCanceled1517;
	}
	public Integer getRelapsePulmonaryBCSLD() {
		return relapsePulmonaryBCSLD;
	}
	public void setRelapsePulmonaryBCSLD(Integer relapsePulmonaryBCSLD) {
		this.relapsePulmonaryBCSLD = relapsePulmonaryBCSLD;
	}
	public Integer getRelapsePulmonaryBCSLD04() {
		return relapsePulmonaryBCSLD04;
	}
	public void setRelapsePulmonaryBCSLD04(Integer relapsePulmonaryBCSLD04) {
		this.relapsePulmonaryBCSLD04 = relapsePulmonaryBCSLD04;
	}
	public Integer getRelapsePulmonaryBCSLD0514() {
		return relapsePulmonaryBCSLD0514;
	}
	public void setRelapsePulmonaryBCSLD0514(Integer relapsePulmonaryBCSLD0514) {
		this.relapsePulmonaryBCSLD0514 = relapsePulmonaryBCSLD0514;
	}
	public Integer getRelapsePulmonaryBCSLD1517() {
		return relapsePulmonaryBCSLD1517;
	}
	public void setRelapsePulmonaryBCSLD1517(Integer relapsePulmonaryBCSLD1517) {
		this.relapsePulmonaryBCSLD1517 = relapsePulmonaryBCSLD1517;
	}
	public Integer getRelapsePulmonaryCDSLD() {
		return relapsePulmonaryCDSLD;
	}
	public void setRelapsePulmonaryCDSLD(Integer relapsePulmonaryCDSLD) {
		this.relapsePulmonaryCDSLD = relapsePulmonaryCDSLD;
	}
	public Integer getRelapsePulmonaryCDSLD04() {
		return relapsePulmonaryCDSLD04;
	}
	public void setRelapsePulmonaryCDSLD04(Integer relapsePulmonaryCDSLD04) {
		this.relapsePulmonaryCDSLD04 = relapsePulmonaryCDSLD04;
	}
	public Integer getRelapsePulmonaryCDSLD0514() {
		return relapsePulmonaryCDSLD0514;
	}
	public void setRelapsePulmonaryCDSLD0514(Integer relapsePulmonaryCDSLD0514) {
		this.relapsePulmonaryCDSLD0514 = relapsePulmonaryCDSLD0514;
	}
	public Integer getRelapsePulmonaryCDSLD1517() {
		return relapsePulmonaryCDSLD1517;
	}
	public void setRelapsePulmonaryCDSLD1517(Integer relapsePulmonaryCDSLD1517) {
		this.relapsePulmonaryCDSLD1517 = relapsePulmonaryCDSLD1517;
	}
	public Integer getRelapseExtrapulmonarySLD() {
		return relapseExtrapulmonarySLD;
	}
	public void setRelapseExtrapulmonarySLD(Integer relapseExtrapulmonarySLD) {
		this.relapseExtrapulmonarySLD = relapseExtrapulmonarySLD;
	}
	public Integer getRelapseExtrapulmonarySLD04() {
		return relapseExtrapulmonarySLD04;
	}
	public void setRelapseExtrapulmonarySLD04(Integer relapseExtrapulmonarySLD04) {
		this.relapseExtrapulmonarySLD04 = relapseExtrapulmonarySLD04;
	}
	public Integer getRelapseExtrapulmonarySLD0514() {
		return relapseExtrapulmonarySLD0514;
	}
	public void setRelapseExtrapulmonarySLD0514(Integer relapseExtrapulmonarySLD0514) {
		this.relapseExtrapulmonarySLD0514 = relapseExtrapulmonarySLD0514;
	}
	public Integer getRelapseExtrapulmonarySLD1517() {
		return relapseExtrapulmonarySLD1517;
	}
	public void setRelapseExtrapulmonarySLD1517(Integer relapseExtrapulmonarySLD1517) {
		this.relapseExtrapulmonarySLD1517 = relapseExtrapulmonarySLD1517;
	}
	public Integer getRelapseAllSLD() {
		return relapseAllSLD;
	}
	public void setRelapseAllSLD(Integer relapseAllSLD) {
		this.relapseAllSLD = relapseAllSLD;
	}
	public Integer getRelapseAllSLD04() {
		return relapseAllSLD04;
	}
	public void setRelapseAllSLD04(Integer relapseAllSLD04) {
		this.relapseAllSLD04 = relapseAllSLD04;
	}
	public Integer getRelapseAllSLD0514() {
		return relapseAllSLD0514;
	}
	public void setRelapseAllSLD0514(Integer relapseAllSLD0514) {
		this.relapseAllSLD0514 = relapseAllSLD0514;
	}
	public Integer getRelapseAllSLD1517() {
		return relapseAllSLD1517;
	}
	public void setRelapseAllSLD1517(Integer relapseAllSLD1517) {
		this.relapseAllSLD1517 = relapseAllSLD1517;
	}
	public Integer getFailurePulmonaryBCDetected() {
		return failurePulmonaryBCDetected;
	}
	public void setFailurePulmonaryBCDetected(Integer failurePulmonaryBCDetected) {
		this.failurePulmonaryBCDetected = failurePulmonaryBCDetected;
	}
	public Integer getFailurePulmonaryCDDetected() {
		return failurePulmonaryCDDetected;
	}
	public void setFailurePulmonaryCDDetected(Integer failurePulmonaryCDDetected) {
		this.failurePulmonaryCDDetected = failurePulmonaryCDDetected;
	}
	public Integer getFailureExtrapulmonaryDetected() {
		return failureExtrapulmonaryDetected;
	}
	public void setFailureExtrapulmonaryDetected(
			Integer failureExtrapulmonaryDetected) {
		this.failureExtrapulmonaryDetected = failureExtrapulmonaryDetected;
	}
	public Integer getFailureAllDetected() {
		return failureAllDetected;
	}
	public void setFailureAllDetected(Integer failureAllDetected) {
		this.failureAllDetected = failureAllDetected;
	}
	public Integer getFailurePulmonaryBCEligible() {
		return failurePulmonaryBCEligible;
	}
	public void setFailurePulmonaryBCEligible(Integer failurePulmonaryBCEligible) {
		this.failurePulmonaryBCEligible = failurePulmonaryBCEligible;
	}
	public Integer getFailurePulmonaryCDEligible() {
		return failurePulmonaryCDEligible;
	}
	public void setFailurePulmonaryCDEligible(Integer failurePulmonaryCDEligible) {
		this.failurePulmonaryCDEligible = failurePulmonaryCDEligible;
	}
	public Integer getFailureExtrapulmonaryEligible() {
		return failureExtrapulmonaryEligible;
	}
	public void setFailureExtrapulmonaryEligible(
			Integer failureExtrapulmonaryEligible) {
		this.failureExtrapulmonaryEligible = failureExtrapulmonaryEligible;
	}
	public Integer getFailureAllEligible() {
		return failureAllEligible;
	}
	public void setFailureAllEligible(Integer failureAllEligible) {
		this.failureAllEligible = failureAllEligible;
	}
	public Integer getFailurePulmonaryBCCured() {
		return failurePulmonaryBCCured;
	}
	public void setFailurePulmonaryBCCured(Integer failurePulmonaryBCCured) {
		this.failurePulmonaryBCCured = failurePulmonaryBCCured;
	}
	public Integer getFailurePulmonaryCDCured() {
		return failurePulmonaryCDCured;
	}
	public void setFailurePulmonaryCDCured(Integer failurePulmonaryCDCured) {
		this.failurePulmonaryCDCured = failurePulmonaryCDCured;
	}
	public Integer getFailureExtrapulmonaryCured() {
		return failureExtrapulmonaryCured;
	}
	public void setFailureExtrapulmonaryCured(Integer failureExtrapulmonaryCured) {
		this.failureExtrapulmonaryCured = failureExtrapulmonaryCured;
	}
	public Integer getFailureAllCured() {
		return failureAllCured;
	}
	public void setFailureAllCured(Integer failureAllCured) {
		this.failureAllCured = failureAllCured;
	}
	public Integer getFailurePulmonaryBCCompleted() {
		return failurePulmonaryBCCompleted;
	}
	public void setFailurePulmonaryBCCompleted(Integer failurePulmonaryBCCompleted) {
		this.failurePulmonaryBCCompleted = failurePulmonaryBCCompleted;
	}
	public Integer getFailurePulmonaryCDCompleted() {
		return failurePulmonaryCDCompleted;
	}
	public void setFailurePulmonaryCDCompleted(Integer failurePulmonaryCDCompleted) {
		this.failurePulmonaryCDCompleted = failurePulmonaryCDCompleted;
	}
	public Integer getFailureExtrapulmonaryCompleted() {
		return failureExtrapulmonaryCompleted;
	}
	public void setFailureExtrapulmonaryCompleted(
			Integer failureExtrapulmonaryCompleted) {
		this.failureExtrapulmonaryCompleted = failureExtrapulmonaryCompleted;
	}
	public Integer getFailureAllCompleted() {
		return failureAllCompleted;
	}
	public void setFailureAllCompleted(Integer failureAllCompleted) {
		this.failureAllCompleted = failureAllCompleted;
	}
	public Integer getFailurePulmonaryBCDiedTB() {
		return failurePulmonaryBCDiedTB;
	}
	public void setFailurePulmonaryBCDiedTB(Integer failurePulmonaryBCDiedTB) {
		this.failurePulmonaryBCDiedTB = failurePulmonaryBCDiedTB;
	}
	public Integer getFailurePulmonaryCDDiedTB() {
		return failurePulmonaryCDDiedTB;
	}
	public void setFailurePulmonaryCDDiedTB(Integer failurePulmonaryCDDiedTB) {
		this.failurePulmonaryCDDiedTB = failurePulmonaryCDDiedTB;
	}
	public Integer getFailureExtrapulmonaryDiedTB() {
		return failureExtrapulmonaryDiedTB;
	}
	public void setFailureExtrapulmonaryDiedTB(Integer failureExtrapulmonaryDiedTB) {
		this.failureExtrapulmonaryDiedTB = failureExtrapulmonaryDiedTB;
	}
	public Integer getFailureAllDiedTB() {
		return failureAllDiedTB;
	}
	public void setFailureAllDiedTB(Integer failureAllDiedTB) {
		this.failureAllDiedTB = failureAllDiedTB;
	}
	public Integer getFailurePulmonaryBCDiedNotTB() {
		return failurePulmonaryBCDiedNotTB;
	}
	public void setFailurePulmonaryBCDiedNotTB(Integer failurePulmonaryBCDiedNotTB) {
		this.failurePulmonaryBCDiedNotTB = failurePulmonaryBCDiedNotTB;
	}
	public Integer getFailurePulmonaryCDDiedNotTB() {
		return failurePulmonaryCDDiedNotTB;
	}
	public void setFailurePulmonaryCDDiedNotTB(Integer failurePulmonaryCDDiedNotTB) {
		this.failurePulmonaryCDDiedNotTB = failurePulmonaryCDDiedNotTB;
	}
	public Integer getFailureExtrapulmonaryDiedNotTB() {
		return failureExtrapulmonaryDiedNotTB;
	}
	public void setFailureExtrapulmonaryDiedNotTB(
			Integer failureExtrapulmonaryDiedNotTB) {
		this.failureExtrapulmonaryDiedNotTB = failureExtrapulmonaryDiedNotTB;
	}
	public Integer getFailureAllDiedNotTB() {
		return failureAllDiedNotTB;
	}
	public void setFailureAllDiedNotTB(Integer failureAllDiedNotTB) {
		this.failureAllDiedNotTB = failureAllDiedNotTB;
	}
	public Integer getFailurePulmonaryBCFailed() {
		return failurePulmonaryBCFailed;
	}
	public void setFailurePulmonaryBCFailed(Integer failurePulmonaryBCFailed) {
		this.failurePulmonaryBCFailed = failurePulmonaryBCFailed;
	}
	public Integer getFailurePulmonaryCDFailed() {
		return failurePulmonaryCDFailed;
	}
	public void setFailurePulmonaryCDFailed(Integer failurePulmonaryCDFailed) {
		this.failurePulmonaryCDFailed = failurePulmonaryCDFailed;
	}
	public Integer getFailureExtrapulmonaryFailed() {
		return failureExtrapulmonaryFailed;
	}
	public void setFailureExtrapulmonaryFailed(Integer failureExtrapulmonaryFailed) {
		this.failureExtrapulmonaryFailed = failureExtrapulmonaryFailed;
	}
	public Integer getFailureAllFailed() {
		return failureAllFailed;
	}
	public void setFailureAllFailed(Integer failureAllFailed) {
		this.failureAllFailed = failureAllFailed;
	}
	public Integer getFailurePulmonaryBCDefaulted() {
		return failurePulmonaryBCDefaulted;
	}
	public void setFailurePulmonaryBCDefaulted(Integer failurePulmonaryBCDefaulted) {
		this.failurePulmonaryBCDefaulted = failurePulmonaryBCDefaulted;
	}
	public Integer getFailurePulmonaryCDDefaulted() {
		return failurePulmonaryCDDefaulted;
	}
	public void setFailurePulmonaryCDDefaulted(Integer failurePulmonaryCDDefaulted) {
		this.failurePulmonaryCDDefaulted = failurePulmonaryCDDefaulted;
	}
	public Integer getFailureExtrapulmonaryDefaulted() {
		return failureExtrapulmonaryDefaulted;
	}
	public void setFailureExtrapulmonaryDefaulted(
			Integer failureExtrapulmonaryDefaulted) {
		this.failureExtrapulmonaryDefaulted = failureExtrapulmonaryDefaulted;
	}
	public Integer getFailureAllDefaulted() {
		return failureAllDefaulted;
	}
	public void setFailureAllDefaulted(Integer failureAllDefaulted) {
		this.failureAllDefaulted = failureAllDefaulted;
	}
	public Integer getFailurePulmonaryBCTransferOut() {
		return failurePulmonaryBCTransferOut;
	}
	public void setFailurePulmonaryBCTransferOut(
			Integer failurePulmonaryBCTransferOut) {
		this.failurePulmonaryBCTransferOut = failurePulmonaryBCTransferOut;
	}
	public Integer getFailurePulmonaryCDTransferOut() {
		return failurePulmonaryCDTransferOut;
	}
	public void setFailurePulmonaryCDTransferOut(
			Integer failurePulmonaryCDTransferOut) {
		this.failurePulmonaryCDTransferOut = failurePulmonaryCDTransferOut;
	}
	public Integer getFailureExtrapulmonaryTransferOut() {
		return failureExtrapulmonaryTransferOut;
	}
	public void setFailureExtrapulmonaryTransferOut(
			Integer failureExtrapulmonaryTransferOut) {
		this.failureExtrapulmonaryTransferOut = failureExtrapulmonaryTransferOut;
	}
	public Integer getFailureAllTransferOut() {
		return failureAllTransferOut;
	}
	public void setFailureAllTransferOut(Integer failureAllTransferOut) {
		this.failureAllTransferOut = failureAllTransferOut;
	}
	public Integer getFailurePulmonaryBCCanceled() {
		return failurePulmonaryBCCanceled;
	}
	public void setFailurePulmonaryBCCanceled(Integer failurePulmonaryBCCanceled) {
		this.failurePulmonaryBCCanceled = failurePulmonaryBCCanceled;
	}
	public Integer getFailurePulmonaryCDCanceled() {
		return failurePulmonaryCDCanceled;
	}
	public void setFailurePulmonaryCDCanceled(Integer failurePulmonaryCDCanceled) {
		this.failurePulmonaryCDCanceled = failurePulmonaryCDCanceled;
	}
	public Integer getFailureExtrapulmonaryCanceled() {
		return failureExtrapulmonaryCanceled;
	}
	public void setFailureExtrapulmonaryCanceled(
			Integer failureExtrapulmonaryCanceled) {
		this.failureExtrapulmonaryCanceled = failureExtrapulmonaryCanceled;
	}
	public Integer getFailureAllCanceled() {
		return failureAllCanceled;
	}
	public void setFailureAllCanceled(Integer failureAllCanceled) {
		this.failureAllCanceled = failureAllCanceled;
	}
	public Integer getFailurePulmonaryBCSLD() {
		return failurePulmonaryBCSLD;
	}
	public void setFailurePulmonaryBCSLD(Integer failurePulmonaryBCSLD) {
		this.failurePulmonaryBCSLD = failurePulmonaryBCSLD;
	}
	public Integer getFailurePulmonaryCDSLD() {
		return failurePulmonaryCDSLD;
	}
	public void setFailurePulmonaryCDSLD(Integer failurePulmonaryCDSLD) {
		this.failurePulmonaryCDSLD = failurePulmonaryCDSLD;
	}
	public Integer getFailureExtrapulmonarySLD() {
		return failureExtrapulmonarySLD;
	}
	public void setFailureExtrapulmonarySLD(Integer failureExtrapulmonarySLD) {
		this.failureExtrapulmonarySLD = failureExtrapulmonarySLD;
	}
	public Integer getFailureAllSLD() {
		return failureAllSLD;
	}
	public void setFailureAllSLD(Integer failureAllSLD) {
		this.failureAllSLD = failureAllSLD;
	}
	public Integer getDefaultPulmonaryBCDetected() {
		return defaultPulmonaryBCDetected;
	}
	public void setDefaultPulmonaryBCDetected(Integer defaultPulmonaryBCDetected) {
		this.defaultPulmonaryBCDetected = defaultPulmonaryBCDetected;
	}
	public Integer getDefaultPulmonaryCDDetected() {
		return defaultPulmonaryCDDetected;
	}
	public void setDefaultPulmonaryCDDetected(Integer defaultPulmonaryCDDetected) {
		this.defaultPulmonaryCDDetected = defaultPulmonaryCDDetected;
	}
	public Integer getDefaultExtrapulmonaryDetected() {
		return defaultExtrapulmonaryDetected;
	}
	public void setDefaultExtrapulmonaryDetected(
			Integer defaultExtrapulmonaryDetected) {
		this.defaultExtrapulmonaryDetected = defaultExtrapulmonaryDetected;
	}
	public Integer getDefaultAllDetected() {
		return defaultAllDetected;
	}
	public void setDefaultAllDetected(Integer defaultAllDetected) {
		this.defaultAllDetected = defaultAllDetected;
	}
	public Integer getDefaultPulmonaryBCEligible() {
		return defaultPulmonaryBCEligible;
	}
	public void setDefaultPulmonaryBCEligible(Integer defaultPulmonaryBCEligible) {
		this.defaultPulmonaryBCEligible = defaultPulmonaryBCEligible;
	}
	public Integer getDefaultPulmonaryCDEligible() {
		return defaultPulmonaryCDEligible;
	}
	public void setDefaultPulmonaryCDEligible(Integer defaultPulmonaryCDEligible) {
		this.defaultPulmonaryCDEligible = defaultPulmonaryCDEligible;
	}
	public Integer getDefaultExtrapulmonaryEligible() {
		return defaultExtrapulmonaryEligible;
	}
	public void setDefaultExtrapulmonaryEligible(
			Integer defaultExtrapulmonaryEligible) {
		this.defaultExtrapulmonaryEligible = defaultExtrapulmonaryEligible;
	}
	public Integer getDefaultAllEligible() {
		return defaultAllEligible;
	}
	public void setDefaultAllEligible(Integer defaultAllEligible) {
		this.defaultAllEligible = defaultAllEligible;
	}
	public Integer getDefaultPulmonaryBCCured() {
		return defaultPulmonaryBCCured;
	}
	public void setDefaultPulmonaryBCCured(Integer defaultPulmonaryBCCured) {
		this.defaultPulmonaryBCCured = defaultPulmonaryBCCured;
	}
	public Integer getDefaultPulmonaryCDCured() {
		return defaultPulmonaryCDCured;
	}
	public void setDefaultPulmonaryCDCured(Integer defaultPulmonaryCDCured) {
		this.defaultPulmonaryCDCured = defaultPulmonaryCDCured;
	}
	public Integer getDefaultExtrapulmonaryCured() {
		return defaultExtrapulmonaryCured;
	}
	public void setDefaultExtrapulmonaryCured(Integer defaultExtrapulmonaryCured) {
		this.defaultExtrapulmonaryCured = defaultExtrapulmonaryCured;
	}
	public Integer getDefaultAllCured() {
		return defaultAllCured;
	}
	public void setDefaultAllCured(Integer defaultAllCured) {
		this.defaultAllCured = defaultAllCured;
	}
	public Integer getDefaultPulmonaryBCCompleted() {
		return defaultPulmonaryBCCompleted;
	}
	public void setDefaultPulmonaryBCCompleted(Integer defaultPulmonaryBCCompleted) {
		this.defaultPulmonaryBCCompleted = defaultPulmonaryBCCompleted;
	}
	public Integer getDefaultPulmonaryCDCompleted() {
		return defaultPulmonaryCDCompleted;
	}
	public void setDefaultPulmonaryCDCompleted(Integer defaultPulmonaryCDCompleted) {
		this.defaultPulmonaryCDCompleted = defaultPulmonaryCDCompleted;
	}
	public Integer getDefaultExtrapulmonaryCompleted() {
		return defaultExtrapulmonaryCompleted;
	}
	public void setDefaultExtrapulmonaryCompleted(
			Integer defaultExtrapulmonaryCompleted) {
		this.defaultExtrapulmonaryCompleted = defaultExtrapulmonaryCompleted;
	}
	public Integer getDefaultAllCompleted() {
		return defaultAllCompleted;
	}
	public void setDefaultAllCompleted(Integer defaultAllCompleted) {
		this.defaultAllCompleted = defaultAllCompleted;
	}
	public Integer getDefaultPulmonaryBCDiedTB() {
		return defaultPulmonaryBCDiedTB;
	}
	public void setDefaultPulmonaryBCDiedTB(Integer defaultPulmonaryBCDiedTB) {
		this.defaultPulmonaryBCDiedTB = defaultPulmonaryBCDiedTB;
	}
	public Integer getDefaultPulmonaryCDDiedTB() {
		return defaultPulmonaryCDDiedTB;
	}
	public void setDefaultPulmonaryCDDiedTB(Integer defaultPulmonaryCDDiedTB) {
		this.defaultPulmonaryCDDiedTB = defaultPulmonaryCDDiedTB;
	}
	public Integer getDefaultExtrapulmonaryDiedTB() {
		return defaultExtrapulmonaryDiedTB;
	}
	public void setDefaultExtrapulmonaryDiedTB(Integer defaultExtrapulmonaryDiedTB) {
		this.defaultExtrapulmonaryDiedTB = defaultExtrapulmonaryDiedTB;
	}
	public Integer getDefaultAllDiedTB() {
		return defaultAllDiedTB;
	}
	public void setDefaultAllDiedTB(Integer defaultAllDiedTB) {
		this.defaultAllDiedTB = defaultAllDiedTB;
	}
	public Integer getDefaultPulmonaryBCDiedNotTB() {
		return defaultPulmonaryBCDiedNotTB;
	}
	public void setDefaultPulmonaryBCDiedNotTB(Integer defaultPulmonaryBCDiedNotTB) {
		this.defaultPulmonaryBCDiedNotTB = defaultPulmonaryBCDiedNotTB;
	}
	public Integer getDefaultPulmonaryCDDiedNotTB() {
		return defaultPulmonaryCDDiedNotTB;
	}
	public void setDefaultPulmonaryCDDiedNotTB(Integer defaultPulmonaryCDDiedNotTB) {
		this.defaultPulmonaryCDDiedNotTB = defaultPulmonaryCDDiedNotTB;
	}
	public Integer getDefaultExtrapulmonaryDiedNotTB() {
		return defaultExtrapulmonaryDiedNotTB;
	}
	public void setDefaultExtrapulmonaryDiedNotTB(
			Integer defaultExtrapulmonaryDiedNotTB) {
		this.defaultExtrapulmonaryDiedNotTB = defaultExtrapulmonaryDiedNotTB;
	}
	public Integer getDefaultAllDiedNotTB() {
		return defaultAllDiedNotTB;
	}
	public void setDefaultAllDiedNotTB(Integer defaultAllDiedNotTB) {
		this.defaultAllDiedNotTB = defaultAllDiedNotTB;
	}
	public Integer getDefaultPulmonaryBCFailed() {
		return defaultPulmonaryBCFailed;
	}
	public void setDefaultPulmonaryBCFailed(Integer defaultPulmonaryBCFailed) {
		this.defaultPulmonaryBCFailed = defaultPulmonaryBCFailed;
	}
	public Integer getDefaultPulmonaryCDFailed() {
		return defaultPulmonaryCDFailed;
	}
	public void setDefaultPulmonaryCDFailed(Integer defaultPulmonaryCDFailed) {
		this.defaultPulmonaryCDFailed = defaultPulmonaryCDFailed;
	}
	public Integer getDefaultExtrapulmonaryFailed() {
		return defaultExtrapulmonaryFailed;
	}
	public void setDefaultExtrapulmonaryFailed(Integer defaultExtrapulmonaryFailed) {
		this.defaultExtrapulmonaryFailed = defaultExtrapulmonaryFailed;
	}
	public Integer getDefaultAllFailed() {
		return defaultAllFailed;
	}
	public void setDefaultAllFailed(Integer defaultAllFailed) {
		this.defaultAllFailed = defaultAllFailed;
	}
	public Integer getDefaultPulmonaryBCDefaulted() {
		return defaultPulmonaryBCDefaulted;
	}
	public void setDefaultPulmonaryBCDefaulted(Integer defaultPulmonaryBCDefaulted) {
		this.defaultPulmonaryBCDefaulted = defaultPulmonaryBCDefaulted;
	}
	public Integer getDefaultPulmonaryCDDefaulted() {
		return defaultPulmonaryCDDefaulted;
	}
	public void setDefaultPulmonaryCDDefaulted(Integer defaultPulmonaryCDDefaulted) {
		this.defaultPulmonaryCDDefaulted = defaultPulmonaryCDDefaulted;
	}
	public Integer getDefaultExtrapulmonaryDefaulted() {
		return defaultExtrapulmonaryDefaulted;
	}
	public void setDefaultExtrapulmonaryDefaulted(
			Integer defaultExtrapulmonaryDefaulted) {
		this.defaultExtrapulmonaryDefaulted = defaultExtrapulmonaryDefaulted;
	}
	public Integer getDefaultAllDefaulted() {
		return defaultAllDefaulted;
	}
	public void setDefaultAllDefaulted(Integer defaultAllDefaulted) {
		this.defaultAllDefaulted = defaultAllDefaulted;
	}
	public Integer getDefaultPulmonaryBCTransferOut() {
		return defaultPulmonaryBCTransferOut;
	}
	public void setDefaultPulmonaryBCTransferOut(
			Integer defaultPulmonaryBCTransferOut) {
		this.defaultPulmonaryBCTransferOut = defaultPulmonaryBCTransferOut;
	}
	public Integer getDefaultPulmonaryCDTransferOut() {
		return defaultPulmonaryCDTransferOut;
	}
	public void setDefaultPulmonaryCDTransferOut(
			Integer defaultPulmonaryCDTransferOut) {
		this.defaultPulmonaryCDTransferOut = defaultPulmonaryCDTransferOut;
	}
	public Integer getDefaultExtrapulmonaryTransferOut() {
		return defaultExtrapulmonaryTransferOut;
	}
	public void setDefaultExtrapulmonaryTransferOut(
			Integer defaultExtrapulmonaryTransferOut) {
		this.defaultExtrapulmonaryTransferOut = defaultExtrapulmonaryTransferOut;
	}
	public Integer getDefaultAllTransferOut() {
		return defaultAllTransferOut;
	}
	public void setDefaultAllTransferOut(Integer defaultAllTransferOut) {
		this.defaultAllTransferOut = defaultAllTransferOut;
	}
	public Integer getDefaultPulmonaryBCCanceled() {
		return defaultPulmonaryBCCanceled;
	}
	public void setDefaultPulmonaryBCCanceled(Integer defaultPulmonaryBCCanceled) {
		this.defaultPulmonaryBCCanceled = defaultPulmonaryBCCanceled;
	}
	public Integer getDefaultPulmonaryCDCanceled() {
		return defaultPulmonaryCDCanceled;
	}
	public void setDefaultPulmonaryCDCanceled(Integer defaultPulmonaryCDCanceled) {
		this.defaultPulmonaryCDCanceled = defaultPulmonaryCDCanceled;
	}
	public Integer getDefaultExtrapulmonaryCanceled() {
		return defaultExtrapulmonaryCanceled;
	}
	public void setDefaultExtrapulmonaryCanceled(
			Integer defaultExtrapulmonaryCanceled) {
		this.defaultExtrapulmonaryCanceled = defaultExtrapulmonaryCanceled;
	}
	public Integer getDefaultAllCanceled() {
		return defaultAllCanceled;
	}
	public void setDefaultAllCanceled(Integer defaultAllCanceled) {
		this.defaultAllCanceled = defaultAllCanceled;
	}
	public Integer getDefaultPulmonaryBCSLD() {
		return defaultPulmonaryBCSLD;
	}
	public void setDefaultPulmonaryBCSLD(Integer defaultPulmonaryBCSLD) {
		this.defaultPulmonaryBCSLD = defaultPulmonaryBCSLD;
	}
	public Integer getDefaultPulmonaryCDSLD() {
		return defaultPulmonaryCDSLD;
	}
	public void setDefaultPulmonaryCDSLD(Integer defaultPulmonaryCDSLD) {
		this.defaultPulmonaryCDSLD = defaultPulmonaryCDSLD;
	}
	public Integer getDefaultExtrapulmonarySLD() {
		return defaultExtrapulmonarySLD;
	}
	public void setDefaultExtrapulmonarySLD(Integer defaultExtrapulmonarySLD) {
		this.defaultExtrapulmonarySLD = defaultExtrapulmonarySLD;
	}
	public Integer getDefaultAllSLD() {
		return defaultAllSLD;
	}
	public void setDefaultAllSLD(Integer defaultAllSLD) {
		this.defaultAllSLD = defaultAllSLD;
	}
	public Integer getOtherPulmonaryBCDetected() {
		return otherPulmonaryBCDetected;
	}
	public void setOtherPulmonaryBCDetected(Integer otherPulmonaryBCDetected) {
		this.otherPulmonaryBCDetected = otherPulmonaryBCDetected;
	}
	public Integer getOtherPulmonaryCDDetected() {
		return otherPulmonaryCDDetected;
	}
	public void setOtherPulmonaryCDDetected(Integer otherPulmonaryCDDetected) {
		this.otherPulmonaryCDDetected = otherPulmonaryCDDetected;
	}
	public Integer getOtherExtrapulmonaryDetected() {
		return otherExtrapulmonaryDetected;
	}
	public void setOtherExtrapulmonaryDetected(Integer otherExtrapulmonaryDetected) {
		this.otherExtrapulmonaryDetected = otherExtrapulmonaryDetected;
	}
	public Integer getOtherAllDetected() {
		return otherAllDetected;
	}
	public void setOtherAllDetected(Integer otherAllDetected) {
		this.otherAllDetected = otherAllDetected;
	}
	public Integer getOtherPulmonaryBCEligible() {
		return otherPulmonaryBCEligible;
	}
	public void setOtherPulmonaryBCEligible(Integer otherPulmonaryBCEligible) {
		this.otherPulmonaryBCEligible = otherPulmonaryBCEligible;
	}
	public Integer getOtherPulmonaryCDEligible() {
		return otherPulmonaryCDEligible;
	}
	public void setOtherPulmonaryCDEligible(Integer otherPulmonaryCDEligible) {
		this.otherPulmonaryCDEligible = otherPulmonaryCDEligible;
	}
	public Integer getOtherExtrapulmonaryEligible() {
		return otherExtrapulmonaryEligible;
	}
	public void setOtherExtrapulmonaryEligible(Integer otherExtrapulmonaryEligible) {
		this.otherExtrapulmonaryEligible = otherExtrapulmonaryEligible;
	}
	public Integer getOtherAllEligible() {
		return otherAllEligible;
	}
	public void setOtherAllEligible(Integer otherAllEligible) {
		this.otherAllEligible = otherAllEligible;
	}
	public Integer getOtherPulmonaryBCCured() {
		return otherPulmonaryBCCured;
	}
	public void setOtherPulmonaryBCCured(Integer otherPulmonaryBCCured) {
		this.otherPulmonaryBCCured = otherPulmonaryBCCured;
	}
	public Integer getOtherPulmonaryCDCured() {
		return otherPulmonaryCDCured;
	}
	public void setOtherPulmonaryCDCured(Integer otherPulmonaryCDCured) {
		this.otherPulmonaryCDCured = otherPulmonaryCDCured;
	}
	public Integer getOtherExtrapulmonaryCured() {
		return otherExtrapulmonaryCured;
	}
	public void setOtherExtrapulmonaryCured(Integer otherExtrapulmonaryCured) {
		this.otherExtrapulmonaryCured = otherExtrapulmonaryCured;
	}
	public Integer getOtherAllCured() {
		return otherAllCured;
	}
	public void setOtherAllCured(Integer otherAllCured) {
		this.otherAllCured = otherAllCured;
	}
	public Integer getOtherPulmonaryBCCompleted() {
		return otherPulmonaryBCCompleted;
	}
	public void setOtherPulmonaryBCCompleted(Integer otherPulmonaryBCCompleted) {
		this.otherPulmonaryBCCompleted = otherPulmonaryBCCompleted;
	}
	public Integer getOtherPulmonaryCDCompleted() {
		return otherPulmonaryCDCompleted;
	}
	public void setOtherPulmonaryCDCompleted(Integer otherPulmonaryCDCompleted) {
		this.otherPulmonaryCDCompleted = otherPulmonaryCDCompleted;
	}
	public Integer getOtherExtrapulmonaryCompleted() {
		return otherExtrapulmonaryCompleted;
	}
	public void setOtherExtrapulmonaryCompleted(Integer otherExtrapulmonaryCompleted) {
		this.otherExtrapulmonaryCompleted = otherExtrapulmonaryCompleted;
	}
	public Integer getOtherAllCompleted() {
		return otherAllCompleted;
	}
	public void setOtherAllCompleted(Integer otherAllCompleted) {
		this.otherAllCompleted = otherAllCompleted;
	}
	public Integer getOtherPulmonaryBCDiedTB() {
		return otherPulmonaryBCDiedTB;
	}
	public void setOtherPulmonaryBCDiedTB(Integer otherPulmonaryBCDiedTB) {
		this.otherPulmonaryBCDiedTB = otherPulmonaryBCDiedTB;
	}
	public Integer getOtherPulmonaryCDDiedTB() {
		return otherPulmonaryCDDiedTB;
	}
	public void setOtherPulmonaryCDDiedTB(Integer otherPulmonaryCDDiedTB) {
		this.otherPulmonaryCDDiedTB = otherPulmonaryCDDiedTB;
	}
	public Integer getOtherExtrapulmonaryDiedTB() {
		return otherExtrapulmonaryDiedTB;
	}
	public void setOtherExtrapulmonaryDiedTB(Integer otherExtrapulmonaryDiedTB) {
		this.otherExtrapulmonaryDiedTB = otherExtrapulmonaryDiedTB;
	}
	public Integer getOtherAllDiedTB() {
		return otherAllDiedTB;
	}
	public void setOtherAllDiedTB(Integer otherAllDiedTB) {
		this.otherAllDiedTB = otherAllDiedTB;
	}
	public Integer getOtherPulmonaryBCDiedNotTB() {
		return otherPulmonaryBCDiedNotTB;
	}
	public void setOtherPulmonaryBCDiedNotTB(Integer otherPulmonaryBCDiedNotTB) {
		this.otherPulmonaryBCDiedNotTB = otherPulmonaryBCDiedNotTB;
	}
	public Integer getOtherPulmonaryCDDiedNotTB() {
		return otherPulmonaryCDDiedNotTB;
	}
	public void setOtherPulmonaryCDDiedNotTB(Integer otherPulmonaryCDDiedNotTB) {
		this.otherPulmonaryCDDiedNotTB = otherPulmonaryCDDiedNotTB;
	}
	public Integer getOtherExtrapulmonaryDiedNotTB() {
		return otherExtrapulmonaryDiedNotTB;
	}
	public void setOtherExtrapulmonaryDiedNotTB(Integer otherExtrapulmonaryDiedNotTB) {
		this.otherExtrapulmonaryDiedNotTB = otherExtrapulmonaryDiedNotTB;
	}
	public Integer getOtherAllDiedNotTB() {
		return otherAllDiedNotTB;
	}
	public void setOtherAllDiedNotTB(Integer otherAllDiedNotTB) {
		this.otherAllDiedNotTB = otherAllDiedNotTB;
	}
	public Integer getOtherPulmonaryBCFailed() {
		return otherPulmonaryBCFailed;
	}
	public void setOtherPulmonaryBCFailed(Integer otherPulmonaryBCFailed) {
		this.otherPulmonaryBCFailed = otherPulmonaryBCFailed;
	}
	public Integer getOtherPulmonaryCDFailed() {
		return otherPulmonaryCDFailed;
	}
	public void setOtherPulmonaryCDFailed(Integer otherPulmonaryCDFailed) {
		this.otherPulmonaryCDFailed = otherPulmonaryCDFailed;
	}
	public Integer getOtherExtrapulmonaryFailed() {
		return otherExtrapulmonaryFailed;
	}
	public void setOtherExtrapulmonaryFailed(Integer otherExtrapulmonaryFailed) {
		this.otherExtrapulmonaryFailed = otherExtrapulmonaryFailed;
	}
	public Integer getOtherAllFailed() {
		return otherAllFailed;
	}
	public void setOtherAllFailed(Integer otherAllFailed) {
		this.otherAllFailed = otherAllFailed;
	}
	public Integer getOtherPulmonaryBCDefaulted() {
		return otherPulmonaryBCDefaulted;
	}
	public void setOtherPulmonaryBCDefaulted(Integer otherPulmonaryBCDefaulted) {
		this.otherPulmonaryBCDefaulted = otherPulmonaryBCDefaulted;
	}
	public Integer getOtherPulmonaryCDDefaulted() {
		return otherPulmonaryCDDefaulted;
	}
	public void setOtherPulmonaryCDDefaulted(Integer otherPulmonaryCDDefaulted) {
		this.otherPulmonaryCDDefaulted = otherPulmonaryCDDefaulted;
	}
	public Integer getOtherExtrapulmonaryDefaulted() {
		return otherExtrapulmonaryDefaulted;
	}
	public void setOtherExtrapulmonaryDefaulted(Integer otherExtrapulmonaryDefaulted) {
		this.otherExtrapulmonaryDefaulted = otherExtrapulmonaryDefaulted;
	}
	public Integer getOtherAllDefaulted() {
		return otherAllDefaulted;
	}
	public void setOtherAllDefaulted(Integer otherAllDefaulted) {
		this.otherAllDefaulted = otherAllDefaulted;
	}
	public Integer getOtherPulmonaryBCTransferOut() {
		return otherPulmonaryBCTransferOut;
	}
	public void setOtherPulmonaryBCTransferOut(Integer otherPulmonaryBCTransferOut) {
		this.otherPulmonaryBCTransferOut = otherPulmonaryBCTransferOut;
	}
	public Integer getOtherPulmonaryCDTransferOut() {
		return otherPulmonaryCDTransferOut;
	}
	public void setOtherPulmonaryCDTransferOut(Integer otherPulmonaryCDTransferOut) {
		this.otherPulmonaryCDTransferOut = otherPulmonaryCDTransferOut;
	}
	public Integer getOtherExtrapulmonaryTransferOut() {
		return otherExtrapulmonaryTransferOut;
	}
	public void setOtherExtrapulmonaryTransferOut(
			Integer otherExtrapulmonaryTransferOut) {
		this.otherExtrapulmonaryTransferOut = otherExtrapulmonaryTransferOut;
	}
	public Integer getOtherAllTransferOut() {
		return otherAllTransferOut;
	}
	public void setOtherAllTransferOut(Integer otherAllTransferOut) {
		this.otherAllTransferOut = otherAllTransferOut;
	}
	public Integer getOtherPulmonaryBCCanceled() {
		return otherPulmonaryBCCanceled;
	}
	public void setOtherPulmonaryBCCanceled(Integer otherPulmonaryBCCanceled) {
		this.otherPulmonaryBCCanceled = otherPulmonaryBCCanceled;
	}
	public Integer getOtherPulmonaryCDCanceled() {
		return otherPulmonaryCDCanceled;
	}
	public void setOtherPulmonaryCDCanceled(Integer otherPulmonaryCDCanceled) {
		this.otherPulmonaryCDCanceled = otherPulmonaryCDCanceled;
	}
	public Integer getOtherExtrapulmonaryCanceled() {
		return otherExtrapulmonaryCanceled;
	}
	public void setOtherExtrapulmonaryCanceled(Integer otherExtrapulmonaryCanceled) {
		this.otherExtrapulmonaryCanceled = otherExtrapulmonaryCanceled;
	}
	public Integer getOtherAllCanceled() {
		return otherAllCanceled;
	}
	public void setOtherAllCanceled(Integer otherAllCanceled) {
		this.otherAllCanceled = otherAllCanceled;
	}
	public Integer getOtherPulmonaryBCSLD() {
		return otherPulmonaryBCSLD;
	}
	public void setOtherPulmonaryBCSLD(Integer otherPulmonaryBCSLD) {
		this.otherPulmonaryBCSLD = otherPulmonaryBCSLD;
	}
	public Integer getOtherPulmonaryCDSLD() {
		return otherPulmonaryCDSLD;
	}
	public void setOtherPulmonaryCDSLD(Integer otherPulmonaryCDSLD) {
		this.otherPulmonaryCDSLD = otherPulmonaryCDSLD;
	}
	public Integer getOtherExtrapulmonarySLD() {
		return otherExtrapulmonarySLD;
	}
	public void setOtherExtrapulmonarySLD(Integer otherExtrapulmonarySLD) {
		this.otherExtrapulmonarySLD = otherExtrapulmonarySLD;
	}
	public Integer getOtherAllSLD() {
		return otherAllSLD;
	}
	public void setOtherAllSLD(Integer otherAllSLD) {
		this.otherAllSLD = otherAllSLD;
	}
	public Integer getRtxPulmonaryBCDetected() {
		return rtxPulmonaryBCDetected;
	}
	public void setRtxPulmonaryBCDetected(Integer rtxPulmonaryBCDetected) {
		this.rtxPulmonaryBCDetected = rtxPulmonaryBCDetected;
	}
	public Integer getRtxPulmonaryCDDetected() {
		return rtxPulmonaryCDDetected;
	}
	public void setRtxPulmonaryCDDetected(Integer rtxPulmonaryCDDetected) {
		this.rtxPulmonaryCDDetected = rtxPulmonaryCDDetected;
	}
	public Integer getRtxExtrapulmonaryDetected() {
		return rtxExtrapulmonaryDetected;
	}
	public void setRtxExtrapulmonaryDetected(Integer rtxExtrapulmonaryDetected) {
		this.rtxExtrapulmonaryDetected = rtxExtrapulmonaryDetected;
	}
	public Integer getRtxAllDetected() {
		return rtxAllDetected;
	}
	public void setRtxAllDetected(Integer rtxAllDetected) {
		this.rtxAllDetected = rtxAllDetected;
	}
	public Integer getRtxPulmonaryBCEligible() {
		return rtxPulmonaryBCEligible;
	}
	public void setRtxPulmonaryBCEligible(Integer rtxPulmonaryBCEligible) {
		this.rtxPulmonaryBCEligible = rtxPulmonaryBCEligible;
	}
	public Integer getRtxPulmonaryCDEligible() {
		return rtxPulmonaryCDEligible;
	}
	public void setRtxPulmonaryCDEligible(Integer rtxPulmonaryCDEligible) {
		this.rtxPulmonaryCDEligible = rtxPulmonaryCDEligible;
	}
	public Integer getRtxExtrapulmonaryEligible() {
		return rtxExtrapulmonaryEligible;
	}
	public void setRtxExtrapulmonaryEligible(Integer rtxExtrapulmonaryEligible) {
		this.rtxExtrapulmonaryEligible = rtxExtrapulmonaryEligible;
	}
	public Integer getRtxAllEligible() {
		return rtxAllEligible;
	}
	public void setRtxAllEligible(Integer rtxAllEligible) {
		this.rtxAllEligible = rtxAllEligible;
	}
	public Integer getRtxPulmonaryBCCured() {
		return rtxPulmonaryBCCured;
	}
	public void setRtxPulmonaryBCCured(Integer rtxPulmonaryBCCured) {
		this.rtxPulmonaryBCCured = rtxPulmonaryBCCured;
	}
	public Integer getRtxPulmonaryCDCured() {
		return rtxPulmonaryCDCured;
	}
	public void setRtxPulmonaryCDCured(Integer rtxPulmonaryCDCured) {
		this.rtxPulmonaryCDCured = rtxPulmonaryCDCured;
	}
	public Integer getRtxExtrapulmonaryCured() {
		return rtxExtrapulmonaryCured;
	}
	public void setRtxExtrapulmonaryCured(Integer rtxExtrapulmonaryCured) {
		this.rtxExtrapulmonaryCured = rtxExtrapulmonaryCured;
	}
	public Integer getRtxAllCured() {
		return rtxAllCured;
	}
	public void setRtxAllCured(Integer rtxAllCured) {
		this.rtxAllCured = rtxAllCured;
	}
	public Integer getRtxPulmonaryBCCompleted() {
		return rtxPulmonaryBCCompleted;
	}
	public void setRtxPulmonaryBCCompleted(Integer rtxPulmonaryBCCompleted) {
		this.rtxPulmonaryBCCompleted = rtxPulmonaryBCCompleted;
	}
	public Integer getRtxPulmonaryCDCompleted() {
		return rtxPulmonaryCDCompleted;
	}
	public void setRtxPulmonaryCDCompleted(Integer rtxPulmonaryCDCompleted) {
		this.rtxPulmonaryCDCompleted = rtxPulmonaryCDCompleted;
	}
	public Integer getRtxExtrapulmonaryCompleted() {
		return rtxExtrapulmonaryCompleted;
	}
	public void setRtxExtrapulmonaryCompleted(Integer rtxExtrapulmonaryCompleted) {
		this.rtxExtrapulmonaryCompleted = rtxExtrapulmonaryCompleted;
	}
	public Integer getRtxAllCompleted() {
		return rtxAllCompleted;
	}
	public void setRtxAllCompleted(Integer rtxAllCompleted) {
		this.rtxAllCompleted = rtxAllCompleted;
	}
	public Integer getRtxPulmonaryBCDiedTB() {
		return rtxPulmonaryBCDiedTB;
	}
	public void setRtxPulmonaryBCDiedTB(Integer rtxPulmonaryBCDiedTB) {
		this.rtxPulmonaryBCDiedTB = rtxPulmonaryBCDiedTB;
	}
	public Integer getRtxPulmonaryCDDiedTB() {
		return rtxPulmonaryCDDiedTB;
	}
	public void setRtxPulmonaryCDDiedTB(Integer rtxPulmonaryCDDiedTB) {
		this.rtxPulmonaryCDDiedTB = rtxPulmonaryCDDiedTB;
	}
	public Integer getRtxExtrapulmonaryDiedTB() {
		return rtxExtrapulmonaryDiedTB;
	}
	public void setRtxExtrapulmonaryDiedTB(Integer rtxExtrapulmonaryDiedTB) {
		this.rtxExtrapulmonaryDiedTB = rtxExtrapulmonaryDiedTB;
	}
	public Integer getRtxAllDiedTB() {
		return rtxAllDiedTB;
	}
	public void setRtxAllDiedTB(Integer rtxAllDiedTB) {
		this.rtxAllDiedTB = rtxAllDiedTB;
	}
	public Integer getRtxPulmonaryBCDiedNotTB() {
		return rtxPulmonaryBCDiedNotTB;
	}
	public void setRtxPulmonaryBCDiedNotTB(Integer rtxPulmonaryBCDiedNotTB) {
		this.rtxPulmonaryBCDiedNotTB = rtxPulmonaryBCDiedNotTB;
	}
	public Integer getRtxPulmonaryCDDiedNotTB() {
		return rtxPulmonaryCDDiedNotTB;
	}
	public void setRtxPulmonaryCDDiedNotTB(Integer rtxPulmonaryCDDiedNotTB) {
		this.rtxPulmonaryCDDiedNotTB = rtxPulmonaryCDDiedNotTB;
	}
	public Integer getRtxExtrapulmonaryDiedNotTB() {
		return rtxExtrapulmonaryDiedNotTB;
	}
	public void setRtxExtrapulmonaryDiedNotTB(Integer rtxExtrapulmonaryDiedNotTB) {
		this.rtxExtrapulmonaryDiedNotTB = rtxExtrapulmonaryDiedNotTB;
	}
	public Integer getRtxAllDiedNotTB() {
		return rtxAllDiedNotTB;
	}
	public void setRtxAllDiedNotTB(Integer rtxAllDiedNotTB) {
		this.rtxAllDiedNotTB = rtxAllDiedNotTB;
	}
	public Integer getRtxPulmonaryBCFailed() {
		return rtxPulmonaryBCFailed;
	}
	public void setRtxPulmonaryBCFailed(Integer rtxPulmonaryBCFailed) {
		this.rtxPulmonaryBCFailed = rtxPulmonaryBCFailed;
	}
	public Integer getRtxPulmonaryCDFailed() {
		return rtxPulmonaryCDFailed;
	}
	public void setRtxPulmonaryCDFailed(Integer rtxPulmonaryCDFailed) {
		this.rtxPulmonaryCDFailed = rtxPulmonaryCDFailed;
	}
	public Integer getRtxExtrapulmonaryFailed() {
		return rtxExtrapulmonaryFailed;
	}
	public void setRtxExtrapulmonaryFailed(Integer rtxExtrapulmonaryFailed) {
		this.rtxExtrapulmonaryFailed = rtxExtrapulmonaryFailed;
	}
	public Integer getRtxAllFailed() {
		return rtxAllFailed;
	}
	public void setRtxAllFailed(Integer rtxAllFailed) {
		this.rtxAllFailed = rtxAllFailed;
	}
	public Integer getRtxPulmonaryBCDefaulted() {
		return rtxPulmonaryBCDefaulted;
	}
	public void setRtxPulmonaryBCDefaulted(Integer rtxPulmonaryBCDefaulted) {
		this.rtxPulmonaryBCDefaulted = rtxPulmonaryBCDefaulted;
	}
	public Integer getRtxPulmonaryCDDefaulted() {
		return rtxPulmonaryCDDefaulted;
	}
	public void setRtxPulmonaryCDDefaulted(Integer rtxPulmonaryCDDefaulted) {
		this.rtxPulmonaryCDDefaulted = rtxPulmonaryCDDefaulted;
	}
	public Integer getRtxExtrapulmonaryDefaulted() {
		return rtxExtrapulmonaryDefaulted;
	}
	public void setRtxExtrapulmonaryDefaulted(Integer rtxExtrapulmonaryDefaulted) {
		this.rtxExtrapulmonaryDefaulted = rtxExtrapulmonaryDefaulted;
	}
	public Integer getRtxAllDefaulted() {
		return rtxAllDefaulted;
	}
	public void setRtxAllDefaulted(Integer rtxAllDefaulted) {
		this.rtxAllDefaulted = rtxAllDefaulted;
	}
	public Integer getRtxPulmonaryBCTransferOut() {
		return rtxPulmonaryBCTransferOut;
	}
	public void setRtxPulmonaryBCTransferOut(Integer rtxPulmonaryBCTransferOut) {
		this.rtxPulmonaryBCTransferOut = rtxPulmonaryBCTransferOut;
	}
	public Integer getRtxPulmonaryCDTransferOut() {
		return rtxPulmonaryCDTransferOut;
	}
	public void setRtxPulmonaryCDTransferOut(Integer rtxPulmonaryCDTransferOut) {
		this.rtxPulmonaryCDTransferOut = rtxPulmonaryCDTransferOut;
	}
	public Integer getRtxExtrapulmonaryTransferOut() {
		return rtxExtrapulmonaryTransferOut;
	}
	public void setRtxExtrapulmonaryTransferOut(Integer rtxExtrapulmonaryTransferOut) {
		this.rtxExtrapulmonaryTransferOut = rtxExtrapulmonaryTransferOut;
	}
	public Integer getRtxAllTransferOut() {
		return rtxAllTransferOut;
	}
	public void setRtxAllTransferOut(Integer rtxAllTransferOut) {
		this.rtxAllTransferOut = rtxAllTransferOut;
	}
	public Integer getRtxPulmonaryBCCanceled() {
		return rtxPulmonaryBCCanceled;
	}
	public void setRtxPulmonaryBCCanceled(Integer rtxPulmonaryBCCanceled) {
		this.rtxPulmonaryBCCanceled = rtxPulmonaryBCCanceled;
	}
	public Integer getRtxPulmonaryCDCanceled() {
		return rtxPulmonaryCDCanceled;
	}
	public void setRtxPulmonaryCDCanceled(Integer rtxPulmonaryCDCanceled) {
		this.rtxPulmonaryCDCanceled = rtxPulmonaryCDCanceled;
	}
	public Integer getRtxExtrapulmonaryCanceled() {
		return rtxExtrapulmonaryCanceled;
	}
	public void setRtxExtrapulmonaryCanceled(Integer rtxExtrapulmonaryCanceled) {
		this.rtxExtrapulmonaryCanceled = rtxExtrapulmonaryCanceled;
	}
	public Integer getRtxAllCanceled() {
		return rtxAllCanceled;
	}
	public void setRtxAllCanceled(Integer rtxAllCanceled) {
		this.rtxAllCanceled = rtxAllCanceled;
	}
	public Integer getRtxPulmonaryBCSLD() {
		return rtxPulmonaryBCSLD;
	}
	public void setRtxPulmonaryBCSLD(Integer rtxPulmonaryBCSLD) {
		this.rtxPulmonaryBCSLD = rtxPulmonaryBCSLD;
	}
	public Integer getRtxPulmonaryCDSLD() {
		return rtxPulmonaryCDSLD;
	}
	public void setRtxPulmonaryCDSLD(Integer rtxPulmonaryCDSLD) {
		this.rtxPulmonaryCDSLD = rtxPulmonaryCDSLD;
	}
	public Integer getRtxExtrapulmonarySLD() {
		return rtxExtrapulmonarySLD;
	}
	public void setRtxExtrapulmonarySLD(Integer rtxExtrapulmonarySLD) {
		this.rtxExtrapulmonarySLD = rtxExtrapulmonarySLD;
	}
	public Integer getRtxAllSLD() {
		return rtxAllSLD;
	}
	public void setRtxAllSLD(Integer rtxAllSLD) {
		this.rtxAllSLD = rtxAllSLD;
	}

	public Integer getAllDetected() {
		return allDetected;
	}

	public void setAllDetected(Integer allDetected) {
		this.allDetected = allDetected;
	}

	public Integer getAllEligible() {
		return allEligible;
	}

	public void setAllEligible(Integer allEligible) {
		this.allEligible = allEligible;
	}

	public Integer getAllCured() {
		return allCured;
	}

	public void setAllCured(Integer allCured) {
		this.allCured = allCured;
	}

	public Integer getAllCompleted() {
		return allCompleted;
	}

	public void setAllCompleted(Integer allCompleted) {
		this.allCompleted = allCompleted;
	}

	public Integer getAllDiedTB() {
		return allDiedTB;
	}

	public void setAllDiedTB(Integer allDiedTB) {
		this.allDiedTB = allDiedTB;
	}

	public Integer getAllDiedNotTB() {
		return allDiedNotTB;
	}

	public void setAllDiedNotTB(Integer allDiedNotTB) {
		this.allDiedNotTB = allDiedNotTB;
	}

	public Integer getAllFailed() {
		return allFailed;
	}

	public void setAllFailed(Integer allFailed) {
		this.allFailed = allFailed;
	}

	public Integer getAllDefaulted() {
		return allDefaulted;
	}

	public void setAllDefaulted(Integer allDefaulted) {
		this.allDefaulted = allDefaulted;
	}

	public Integer getAllTransferOut() {
		return allTransferOut;
	}

	public void setAllTransferOut(Integer allTransferOut) {
		this.allTransferOut = allTransferOut;
	}

	public Integer getAllCanceled() {
		return allCanceled;
	}

	public void setAllCanceled(Integer allCanceled) {
		this.allCanceled = allCanceled;
	}

	public Integer getAllSLD() {
		return allSLD;
	}

	public void setAllSLD(Integer allSLD) {
		this.allSLD = allSLD;
	}
	
	public void add(TB08Data table) {
		if(table==null)
			return;
		this.setNewPulmonaryBCDetected(this.getNewPulmonaryBCDetected() + table.getNewPulmonaryBCDetected());
		this.setNewPulmonaryBCDetected04(this.getNewPulmonaryBCDetected04() + table.getNewPulmonaryBCDetected04());
		this.setNewPulmonaryBCDetected0514(this.getNewPulmonaryBCDetected0514() + table.getNewPulmonaryBCDetected0514());
		this.setNewPulmonaryBCDetected1517(this.getNewPulmonaryBCDetected1517() + table.getNewPulmonaryBCDetected1517());
		this.setNewPulmonaryCDDetected(this.getNewPulmonaryCDDetected() + table.getNewPulmonaryCDDetected());
		this.setNewPulmonaryCDDetected04(this.getNewPulmonaryCDDetected04() + table.getNewPulmonaryCDDetected04());
		this.setNewPulmonaryCDDetected0514(this.getNewPulmonaryCDDetected0514() + table.getNewPulmonaryCDDetected0514());
		this.setNewPulmonaryCDDetected1517(this.getNewPulmonaryCDDetected1517() + table.getNewPulmonaryCDDetected1517());
		this.setNewExtrapulmonaryDetected(this.getNewExtrapulmonaryDetected() + table.getNewExtrapulmonaryDetected());
		this.setNewExtrapulmonaryDetected04(this.getNewExtrapulmonaryDetected04() + table.getNewExtrapulmonaryDetected04());
		this.setNewExtrapulmonaryDetected0514(this.getNewExtrapulmonaryDetected0514() + table.getNewExtrapulmonaryDetected0514());
		this.setNewExtrapulmonaryDetected1517(this.getNewExtrapulmonaryDetected1517() + table.getNewExtrapulmonaryDetected1517());
		this.setNewAllDetected(this.getNewAllDetected() + table.getNewAllDetected());
		this.setNewAllDetected04(this.getNewAllDetected04() + table.getNewAllDetected04());
		this.setNewAllDetected0514(this.getNewAllDetected0514() + table.getNewAllDetected0514());
		this.setNewAllDetected1517(this.getNewAllDetected1517() + table.getNewAllDetected1517());
		this.setNewPulmonaryBCEligible(this.getNewPulmonaryBCEligible() + table.getNewPulmonaryBCEligible());
		this.setNewPulmonaryBCEligible04(this.getNewPulmonaryBCEligible04() + table.getNewPulmonaryBCEligible04());
		this.setNewPulmonaryBCEligible0514(this.getNewPulmonaryBCEligible0514() + table.getNewPulmonaryBCEligible0514());
		this.setNewPulmonaryBCEligible1517(this.getNewPulmonaryBCEligible1517() + table.getNewPulmonaryBCEligible1517());
		this.setNewPulmonaryCDEligible(this.getNewPulmonaryCDEligible() + table.getNewPulmonaryCDEligible());
		this.setNewPulmonaryCDEligible04(this.getNewPulmonaryCDEligible04() + table.getNewPulmonaryCDEligible04());
		this.setNewPulmonaryCDEligible0514(this.getNewPulmonaryCDEligible0514() + table.getNewPulmonaryCDEligible0514());
		this.setNewPulmonaryCDEligible1517(this.getNewPulmonaryCDEligible1517() + table.getNewPulmonaryCDEligible1517());
		this.setNewExtrapulmonaryEligible(this.getNewExtrapulmonaryEligible() + table.getNewExtrapulmonaryEligible());
		this.setNewExtrapulmonaryEligible04(this.getNewExtrapulmonaryEligible04() + table.getNewExtrapulmonaryEligible04());
		this.setNewExtrapulmonaryEligible0514(this.getNewExtrapulmonaryEligible0514() + table.getNewExtrapulmonaryEligible0514());
		this.setNewExtrapulmonaryEligible1517(this.getNewExtrapulmonaryEligible1517() + table.getNewExtrapulmonaryEligible1517());
		this.setNewAllEligible(this.getNewAllEligible() + table.getNewAllEligible());
		this.setNewAllEligible04(this.getNewAllEligible04() + table.getNewAllEligible04());
		this.setNewAllEligible0514(this.getNewAllEligible0514() + table.getNewAllEligible0514());
		this.setNewAllEligible1517(this.getNewAllEligible1517() + table.getNewAllEligible1517());
		this.setNewPulmonaryBCCured(this.getNewPulmonaryBCCured() + table.getNewPulmonaryBCCured());
		this.setNewPulmonaryBCCured04(this.getNewPulmonaryBCCured04() + table.getNewPulmonaryBCCured04());
		this.setNewPulmonaryBCCured0514(this.getNewPulmonaryBCCured0514() + table.getNewPulmonaryBCCured0514());
		this.setNewPulmonaryBCCured1517(this.getNewPulmonaryBCCured1517() + table.getNewPulmonaryBCCured1517());
		this.setNewPulmonaryCDCured(this.getNewPulmonaryCDCured() + table.getNewPulmonaryCDCured());
		this.setNewPulmonaryCDCured04(this.getNewPulmonaryCDCured04() + table.getNewPulmonaryCDCured04());
		this.setNewPulmonaryCDCured0514(this.getNewPulmonaryCDCured0514() + table.getNewPulmonaryCDCured0514());
		this.setNewPulmonaryCDCured1517(this.getNewPulmonaryCDCured1517() + table.getNewPulmonaryCDCured1517());
		this.setNewExtrapulmonaryCured(this.getNewExtrapulmonaryCured() + table.getNewExtrapulmonaryCured());
		this.setNewExtrapulmonaryCured04(this.getNewExtrapulmonaryCured04() + table.getNewExtrapulmonaryCured04());
		this.setNewExtrapulmonaryCured0514(this.getNewExtrapulmonaryCured0514() + table.getNewExtrapulmonaryCured0514());
		this.setNewExtrapulmonaryCured1517(this.getNewExtrapulmonaryCured1517() + table.getNewExtrapulmonaryCured1517());
		this.setNewAllCured(this.getNewAllCured() + table.getNewAllCured());
		this.setNewAllCured04(this.getNewAllCured04() + table.getNewAllCured04());
		this.setNewAllCured0514(this.getNewAllCured0514() + table.getNewAllCured0514());
		this.setNewAllCured1517(this.getNewAllCured1517() + table.getNewAllCured1517());
		this.setNewPulmonaryBCCompleted(this.getNewPulmonaryBCCompleted() + table.getNewPulmonaryBCCompleted());
		this.setNewPulmonaryBCCompleted04(this.getNewPulmonaryBCCompleted04() + table.getNewPulmonaryBCCompleted04());
		this.setNewPulmonaryBCCompleted0514(this.getNewPulmonaryBCCompleted0514() + table.getNewPulmonaryBCCompleted0514());
		this.setNewPulmonaryBCCompleted1517(this.getNewPulmonaryBCCompleted1517() + table.getNewPulmonaryBCCompleted1517());
		this.setNewPulmonaryCDCompleted(this.getNewPulmonaryCDCompleted() + table.getNewPulmonaryCDCompleted());
		this.setNewPulmonaryCDCompleted04(this.getNewPulmonaryCDCompleted04() + table.getNewPulmonaryCDCompleted04());
		this.setNewPulmonaryCDCompleted0514(this.getNewPulmonaryCDCompleted0514() + table.getNewPulmonaryCDCompleted0514());
		this.setNewPulmonaryCDCompleted1517(this.getNewPulmonaryCDCompleted1517() + table.getNewPulmonaryCDCompleted1517());
		this.setNewExtrapulmonaryCompleted(this.getNewExtrapulmonaryCompleted() + table.getNewExtrapulmonaryCompleted());
		this.setNewExtrapulmonaryCompleted04(this.getNewExtrapulmonaryCompleted04() + table.getNewExtrapulmonaryCompleted04());
		this.setNewExtrapulmonaryCompleted0514(this.getNewExtrapulmonaryCompleted0514() + table.getNewExtrapulmonaryCompleted0514());
		this.setNewExtrapulmonaryCompleted1517(this.getNewExtrapulmonaryCompleted1517() + table.getNewExtrapulmonaryCompleted1517());
		this.setNewAllCompleted(this.getNewAllCompleted() + table.getNewAllCompleted());
		this.setNewAllCompleted04(this.getNewAllCompleted04() + table.getNewAllCompleted04());
		this.setNewAllCompleted0514(this.getNewAllCompleted0514() + table.getNewAllCompleted0514());
		this.setNewAllCompleted1517(this.getNewAllCompleted1517() + table.getNewAllCompleted1517());
		this.setNewPulmonaryBCDiedTB(this.getNewPulmonaryBCDiedTB() + table.getNewPulmonaryBCDiedTB());
		this.setNewPulmonaryBCDiedTB04(this.getNewPulmonaryBCDiedTB04() + table.getNewPulmonaryBCDiedTB04());
		this.setNewPulmonaryBCDiedTB0514(this.getNewPulmonaryBCDiedTB0514() + table.getNewPulmonaryBCDiedTB0514());
		this.setNewPulmonaryBCDiedTB1517(this.getNewPulmonaryBCDiedTB1517() + table.getNewPulmonaryBCDiedTB1517());
		this.setNewPulmonaryCDDiedTB(this.getNewPulmonaryCDDiedTB() + table.getNewPulmonaryCDDiedTB());
		this.setNewPulmonaryCDDiedTB04(this.getNewPulmonaryCDDiedTB04() + table.getNewPulmonaryCDDiedTB04());
		this.setNewPulmonaryCDDiedTB0514(this.getNewPulmonaryCDDiedTB0514() + table.getNewPulmonaryCDDiedTB0514());
		this.setNewPulmonaryCDDiedTB1517(this.getNewPulmonaryCDDiedTB1517() + table.getNewPulmonaryCDDiedTB1517());
		this.setNewExtrapulmonaryDiedTB(this.getNewExtrapulmonaryDiedTB() + table.getNewExtrapulmonaryDiedTB());
		this.setNewExtrapulmonaryDiedTB04(this.getNewExtrapulmonaryDiedTB04() + table.getNewExtrapulmonaryDiedTB04());
		this.setNewExtrapulmonaryDiedTB0514(this.getNewExtrapulmonaryDiedTB0514() + table.getNewExtrapulmonaryDiedTB0514());
		this.setNewExtrapulmonaryDiedTB1517(this.getNewExtrapulmonaryDiedTB1517() + table.getNewExtrapulmonaryDiedTB1517());
		this.setNewAllDiedTB(this.getNewAllDiedTB() + table.getNewAllDiedTB());
		this.setNewAllDiedTB04(this.getNewAllDiedTB04() + table.getNewAllDiedTB04());
		this.setNewAllDiedTB0514(this.getNewAllDiedTB0514() + table.getNewAllDiedTB0514());
		this.setNewAllDiedTB1517(this.getNewAllDiedTB1517() + table.getNewAllDiedTB1517());
		this.setNewPulmonaryBCDiedNotTB(this.getNewPulmonaryBCDiedNotTB() + table.getNewPulmonaryBCDiedNotTB());
		this.setNewPulmonaryBCDiedNotTB04(this.getNewPulmonaryBCDiedNotTB04() + table.getNewPulmonaryBCDiedNotTB04());
		this.setNewPulmonaryBCDiedNotTB0514(this.getNewPulmonaryBCDiedNotTB0514() + table.getNewPulmonaryBCDiedNotTB0514());
		this.setNewPulmonaryBCDiedNotTB1517(this.getNewPulmonaryBCDiedNotTB1517() + table.getNewPulmonaryBCDiedNotTB1517());
		this.setNewPulmonaryCDDiedNotTB(this.getNewPulmonaryCDDiedNotTB() + table.getNewPulmonaryCDDiedNotTB());
		this.setNewPulmonaryCDDiedNotTB04(this.getNewPulmonaryCDDiedNotTB04() + table.getNewPulmonaryCDDiedNotTB04());
		this.setNewPulmonaryCDDiedNotTB0514(this.getNewPulmonaryCDDiedNotTB0514() + table.getNewPulmonaryCDDiedNotTB0514());
		this.setNewPulmonaryCDDiedNotTB1517(this.getNewPulmonaryCDDiedNotTB1517() + table.getNewPulmonaryCDDiedNotTB1517());
		this.setNewExtrapulmonaryDiedNotTB(this.getNewExtrapulmonaryDiedNotTB() + table.getNewExtrapulmonaryDiedNotTB());
		this.setNewExtrapulmonaryDiedNotTB04(this.getNewExtrapulmonaryDiedNotTB04() + table.getNewExtrapulmonaryDiedNotTB04());
		this.setNewExtrapulmonaryDiedNotTB0514(this.getNewExtrapulmonaryDiedNotTB0514() + table.getNewExtrapulmonaryDiedNotTB0514());
		this.setNewExtrapulmonaryDiedNotTB1517(this.getNewExtrapulmonaryDiedNotTB1517() + table.getNewExtrapulmonaryDiedNotTB1517());
		this.setNewAllDiedNotTB(this.getNewAllDiedNotTB() + table.getNewAllDiedNotTB());
		this.setNewAllDiedNotTB04(this.getNewAllDiedNotTB04() + table.getNewAllDiedNotTB04());
		this.setNewAllDiedNotTB0514(this.getNewAllDiedNotTB0514() + table.getNewAllDiedNotTB0514());
		this.setNewAllDiedNotTB1517(this.getNewAllDiedNotTB1517() + table.getNewAllDiedNotTB1517());
		this.setNewPulmonaryBCFailed(this.getNewPulmonaryBCFailed() + table.getNewPulmonaryBCFailed());
		this.setNewPulmonaryBCFailed04(this.getNewPulmonaryBCFailed04() + table.getNewPulmonaryBCFailed04());
		this.setNewPulmonaryBCFailed0514(this.getNewPulmonaryBCFailed0514() + table.getNewPulmonaryBCFailed0514());
		this.setNewPulmonaryBCFailed1517(this.getNewPulmonaryBCFailed1517() + table.getNewPulmonaryBCFailed1517());
		this.setNewPulmonaryCDFailed(this.getNewPulmonaryCDFailed() + table.getNewPulmonaryCDFailed());
		this.setNewPulmonaryCDFailed04(this.getNewPulmonaryCDFailed04() + table.getNewPulmonaryCDFailed04());
		this.setNewPulmonaryCDFailed0514(this.getNewPulmonaryCDFailed0514() + table.getNewPulmonaryCDFailed0514());
		this.setNewPulmonaryCDFailed1517(this.getNewPulmonaryCDFailed1517() + table.getNewPulmonaryCDFailed1517());
		this.setNewExtrapulmonaryFailed(this.getNewExtrapulmonaryFailed() + table.getNewExtrapulmonaryFailed());
		this.setNewExtrapulmonaryFailed04(this.getNewExtrapulmonaryFailed04() + table.getNewExtrapulmonaryFailed04());
		this.setNewExtrapulmonaryFailed0514(this.getNewExtrapulmonaryFailed0514() + table.getNewExtrapulmonaryFailed0514());
		this.setNewExtrapulmonaryFailed1517(this.getNewExtrapulmonaryFailed1517() + table.getNewExtrapulmonaryFailed1517());
		this.setNewAllFailed(this.getNewAllFailed() + table.getNewAllFailed());
		this.setNewAllFailed04(this.getNewAllFailed04() + table.getNewAllFailed04());
		this.setNewAllFailed0514(this.getNewAllFailed0514() + table.getNewAllFailed0514());
		this.setNewAllFailed1517(this.getNewAllFailed1517() + table.getNewAllFailed1517());
		this.setNewPulmonaryBCDefaulted(this.getNewPulmonaryBCDefaulted() + table.getNewPulmonaryBCDefaulted());
		this.setNewPulmonaryBCDefaulted04(this.getNewPulmonaryBCDefaulted04() + table.getNewPulmonaryBCDefaulted04());
		this.setNewPulmonaryBCDefaulted0514(this.getNewPulmonaryBCDefaulted0514() + table.getNewPulmonaryBCDefaulted0514());
		this.setNewPulmonaryBCDefaulted1517(this.getNewPulmonaryBCDefaulted1517() + table.getNewPulmonaryBCDefaulted1517());
		this.setNewPulmonaryCDDefaulted(this.getNewPulmonaryCDDefaulted() + table.getNewPulmonaryCDDefaulted());
		this.setNewPulmonaryCDDefaulted04(this.getNewPulmonaryCDDefaulted04() + table.getNewPulmonaryCDDefaulted04());
		this.setNewPulmonaryCDDefaulted0514(this.getNewPulmonaryCDDefaulted0514() + table.getNewPulmonaryCDDefaulted0514());
		this.setNewPulmonaryCDDefaulted1517(this.getNewPulmonaryCDDefaulted1517() + table.getNewPulmonaryCDDefaulted1517());
		this.setNewExtrapulmonaryDefaulted(this.getNewExtrapulmonaryDefaulted() + table.getNewExtrapulmonaryDefaulted());
		this.setNewExtrapulmonaryDefaulted04(this.getNewExtrapulmonaryDefaulted04() + table.getNewExtrapulmonaryDefaulted04());
		this.setNewExtrapulmonaryDefaulted0514(this.getNewExtrapulmonaryDefaulted0514() + table.getNewExtrapulmonaryDefaulted0514());
		this.setNewExtrapulmonaryDefaulted1517(this.getNewExtrapulmonaryDefaulted1517() + table.getNewExtrapulmonaryDefaulted1517());
		this.setNewAllDefaulted(this.getNewAllDefaulted() + table.getNewAllDefaulted());
		this.setNewAllDefaulted04(this.getNewAllDefaulted04() + table.getNewAllDefaulted04());
		this.setNewAllDefaulted0514(this.getNewAllDefaulted0514() + table.getNewAllDefaulted0514());
		this.setNewAllDefaulted1517(this.getNewAllDefaulted1517() + table.getNewAllDefaulted1517());
		this.setNewPulmonaryBCTransferOut(this.getNewPulmonaryBCTransferOut() + table.getNewPulmonaryBCTransferOut());
		this.setNewPulmonaryBCTransferOut04(this.getNewPulmonaryBCTransferOut04() + table.getNewPulmonaryBCTransferOut04());
		this.setNewPulmonaryBCTransferOut0514(this.getNewPulmonaryBCTransferOut0514() + table.getNewPulmonaryBCTransferOut0514());
		this.setNewPulmonaryBCTransferOut1517(this.getNewPulmonaryBCTransferOut1517() + table.getNewPulmonaryBCTransferOut1517());
		this.setNewPulmonaryCDTransferOut(this.getNewPulmonaryCDTransferOut() + table.getNewPulmonaryCDTransferOut());
		this.setNewPulmonaryCDTransferOut04(this.getNewPulmonaryCDTransferOut04() + table.getNewPulmonaryCDTransferOut04());
		this.setNewPulmonaryCDTransferOut0514(this.getNewPulmonaryCDTransferOut0514() + table.getNewPulmonaryCDTransferOut0514());
		this.setNewPulmonaryCDTransferOut1517(this.getNewPulmonaryCDTransferOut1517() + table.getNewPulmonaryCDTransferOut1517());
		this.setNewExtrapulmonaryTransferOut(this.getNewExtrapulmonaryTransferOut() + table.getNewExtrapulmonaryTransferOut());
		this.setNewExtrapulmonaryTransferOut04(this.getNewExtrapulmonaryTransferOut04() + table.getNewExtrapulmonaryTransferOut04());
		this.setNewExtrapulmonaryTransferOut0514(this.getNewExtrapulmonaryTransferOut0514() + table.getNewExtrapulmonaryTransferOut0514());
		this.setNewExtrapulmonaryTransferOut1517(this.getNewExtrapulmonaryTransferOut1517() + table.getNewExtrapulmonaryTransferOut1517());
		this.setNewAllTransferOut(this.getNewAllTransferOut() + table.getNewAllTransferOut());
		this.setNewAllTransferOut04(this.getNewAllTransferOut04() + table.getNewAllTransferOut04());
		this.setNewAllTransferOut0514(this.getNewAllTransferOut0514() + table.getNewAllTransferOut0514());
		this.setNewAllTransferOut1517(this.getNewAllTransferOut1517() + table.getNewAllTransferOut1517());
		this.setNewPulmonaryBCCanceled(this.getNewPulmonaryBCCanceled() + table.getNewPulmonaryBCCanceled());
		this.setNewPulmonaryBCCanceled04(this.getNewPulmonaryBCCanceled04() + table.getNewPulmonaryBCCanceled04());
		this.setNewPulmonaryBCCanceled0514(this.getNewPulmonaryBCCanceled0514() + table.getNewPulmonaryBCCanceled0514());
		this.setNewPulmonaryBCCanceled1517(this.getNewPulmonaryBCCanceled1517() + table.getNewPulmonaryBCCanceled1517());
		this.setNewPulmonaryCDCanceled(this.getNewPulmonaryCDCanceled() + table.getNewPulmonaryCDCanceled());
		this.setNewPulmonaryCDCanceled04(this.getNewPulmonaryCDCanceled04() + table.getNewPulmonaryCDCanceled04());
		this.setNewPulmonaryCDCanceled0514(this.getNewPulmonaryCDCanceled0514() + table.getNewPulmonaryCDCanceled0514());
		this.setNewPulmonaryCDCanceled1517(this.getNewPulmonaryCDCanceled1517() + table.getNewPulmonaryCDCanceled1517());
		this.setNewExtrapulmonaryCanceled(this.getNewExtrapulmonaryCanceled() + table.getNewExtrapulmonaryCanceled());
		this.setNewExtrapulmonaryCanceled04(this.getNewExtrapulmonaryCanceled04() + table.getNewExtrapulmonaryCanceled04());
		this.setNewExtrapulmonaryCanceled0514(this.getNewExtrapulmonaryCanceled0514() + table.getNewExtrapulmonaryCanceled0514());
		this.setNewExtrapulmonaryCanceled1517(this.getNewExtrapulmonaryCanceled1517() + table.getNewExtrapulmonaryCanceled1517());
		this.setNewAllCanceled(this.getNewAllCanceled() + table.getNewAllCanceled());
		this.setNewAllCanceled04(this.getNewAllCanceled04() + table.getNewAllCanceled04());
		this.setNewAllCanceled0514(this.getNewAllCanceled0514() + table.getNewAllCanceled0514());
		this.setNewAllCanceled1517(this.getNewAllCanceled1517() + table.getNewAllCanceled1517());
		this.setNewPulmonaryBCSLD(this.getNewPulmonaryBCSLD() + table.getNewPulmonaryBCSLD());
		this.setNewPulmonaryBCSLD04(this.getNewPulmonaryBCSLD04() + table.getNewPulmonaryBCSLD04());
		this.setNewPulmonaryBCSLD0514(this.getNewPulmonaryBCSLD0514() + table.getNewPulmonaryBCSLD0514());
		this.setNewPulmonaryBCSLD1517(this.getNewPulmonaryBCSLD1517() + table.getNewPulmonaryBCSLD1517());
		this.setNewPulmonaryCDSLD(this.getNewPulmonaryCDSLD() + table.getNewPulmonaryCDSLD());
		this.setNewPulmonaryCDSLD04(this.getNewPulmonaryCDSLD04() + table.getNewPulmonaryCDSLD04());
		this.setNewPulmonaryCDSLD0514(this.getNewPulmonaryCDSLD0514() + table.getNewPulmonaryCDSLD0514());
		this.setNewPulmonaryCDSLD1517(this.getNewPulmonaryCDSLD1517() + table.getNewPulmonaryCDSLD1517());
		this.setNewExtrapulmonarySLD(this.getNewExtrapulmonarySLD() + table.getNewExtrapulmonarySLD());
		this.setNewExtrapulmonarySLD04(this.getNewExtrapulmonarySLD04() + table.getNewExtrapulmonarySLD04());
		this.setNewExtrapulmonarySLD0514(this.getNewExtrapulmonarySLD0514() + table.getNewExtrapulmonarySLD0514());
		this.setNewExtrapulmonarySLD1517(this.getNewExtrapulmonarySLD1517() + table.getNewExtrapulmonarySLD1517());
		this.setNewAllSLD(this.getNewAllSLD() + table.getNewAllSLD());
		this.setNewAllSLD04(this.getNewAllSLD04() + table.getNewAllSLD04());
		this.setNewAllSLD0514(this.getNewAllSLD0514() + table.getNewAllSLD0514());
		this.setNewAllSLD1517(this.getNewAllSLD1517() + table.getNewAllSLD1517());
		this.setRelapsePulmonaryBCDetected(this.getRelapsePulmonaryBCDetected() + table.getRelapsePulmonaryBCDetected());
		this.setRelapsePulmonaryBCDetected04(this.getRelapsePulmonaryBCDetected04() + table.getRelapsePulmonaryBCDetected04());
		this.setRelapsePulmonaryBCDetected0514(this.getRelapsePulmonaryBCDetected0514() + table.getRelapsePulmonaryBCDetected0514());
		this.setRelapsePulmonaryBCDetected1517(this.getRelapsePulmonaryBCDetected1517() + table.getRelapsePulmonaryBCDetected1517());
		this.setRelapsePulmonaryCDDetected(this.getRelapsePulmonaryCDDetected() + table.getRelapsePulmonaryCDDetected());
		this.setRelapsePulmonaryCDDetected04(this.getRelapsePulmonaryCDDetected04() + table.getRelapsePulmonaryCDDetected04());
		this.setRelapsePulmonaryCDDetected0514(this.getRelapsePulmonaryCDDetected0514() + table.getRelapsePulmonaryCDDetected0514());
		this.setRelapsePulmonaryCDDetected1517(this.getRelapsePulmonaryCDDetected1517() + table.getRelapsePulmonaryCDDetected1517());
		this.setRelapseExtrapulmonaryDetected(this.getRelapseExtrapulmonaryDetected() + table.getRelapseExtrapulmonaryDetected());
		this.setRelapseExtrapulmonaryDetected04(this.getRelapseExtrapulmonaryDetected04() + table.getRelapseExtrapulmonaryDetected04());
		this.setRelapseExtrapulmonaryDetected0514(this.getRelapseExtrapulmonaryDetected0514() + table.getRelapseExtrapulmonaryDetected0514());
		this.setRelapseExtrapulmonaryDetected1517(this.getRelapseExtrapulmonaryDetected1517() + table.getRelapseExtrapulmonaryDetected1517());
		this.setRelapseAllDetected(this.getRelapseAllDetected() + table.getRelapseAllDetected());
		this.setRelapseAllDetected04(this.getRelapseAllDetected04() + table.getRelapseAllDetected04());
		this.setRelapseAllDetected0514(this.getRelapseAllDetected0514() + table.getRelapseAllDetected0514());
		this.setRelapseAllDetected1517(this.getRelapseAllDetected1517() + table.getRelapseAllDetected1517());
		this.setRelapsePulmonaryBCEligible(this.getRelapsePulmonaryBCEligible() + table.getRelapsePulmonaryBCEligible());
		this.setRelapsePulmonaryBCEligible04(this.getRelapsePulmonaryBCEligible04() + table.getRelapsePulmonaryBCEligible04());
		this.setRelapsePulmonaryBCEligible0514(this.getRelapsePulmonaryBCEligible0514() + table.getRelapsePulmonaryBCEligible0514());
		this.setRelapsePulmonaryBCEligible1517(this.getRelapsePulmonaryBCEligible1517() + table.getRelapsePulmonaryBCEligible1517());
		this.setRelapsePulmonaryCDEligible(this.getRelapsePulmonaryCDEligible() + table.getRelapsePulmonaryCDEligible());
		this.setRelapsePulmonaryCDEligible04(this.getRelapsePulmonaryCDEligible04() + table.getRelapsePulmonaryCDEligible04());
		this.setRelapsePulmonaryCDEligible0514(this.getRelapsePulmonaryCDEligible0514() + table.getRelapsePulmonaryCDEligible0514());
		this.setRelapsePulmonaryCDEligible1517(this.getRelapsePulmonaryCDEligible1517() + table.getRelapsePulmonaryCDEligible1517());
		this.setRelapseExtrapulmonaryEligible(this.getRelapseExtrapulmonaryEligible() + table.getRelapseExtrapulmonaryEligible());
		this.setRelapseExtrapulmonaryEligible04(this.getRelapseExtrapulmonaryEligible04() + table.getRelapseExtrapulmonaryEligible04());
		this.setRelapseExtrapulmonaryEligible0514(this.getRelapseExtrapulmonaryEligible0514() + table.getRelapseExtrapulmonaryEligible0514());
		this.setRelapseExtrapulmonaryEligible1517(this.getRelapseExtrapulmonaryEligible1517() + table.getRelapseExtrapulmonaryEligible1517());
		this.setRelapseAllEligible(this.getRelapseAllEligible() + table.getRelapseAllEligible());
		this.setRelapseAllEligible04(this.getRelapseAllEligible04() + table.getRelapseAllEligible04());
		this.setRelapseAllEligible0514(this.getRelapseAllEligible0514() + table.getRelapseAllEligible0514());
		this.setRelapseAllEligible1517(this.getRelapseAllEligible1517() + table.getRelapseAllEligible1517());
		this.setRelapsePulmonaryBCCured(this.getRelapsePulmonaryBCCured() + table.getRelapsePulmonaryBCCured());
		this.setRelapsePulmonaryBCCured04(this.getRelapsePulmonaryBCCured04() + table.getRelapsePulmonaryBCCured04());
		this.setRelapsePulmonaryBCCured0514(this.getRelapsePulmonaryBCCured0514() + table.getRelapsePulmonaryBCCured0514());
		this.setRelapsePulmonaryBCCured1517(this.getRelapsePulmonaryBCCured1517() + table.getRelapsePulmonaryBCCured1517());
		this.setRelapsePulmonaryCDCured(this.getRelapsePulmonaryCDCured() + table.getRelapsePulmonaryCDCured());
		this.setRelapsePulmonaryCDCured04(this.getRelapsePulmonaryCDCured04() + table.getRelapsePulmonaryCDCured04());
		this.setRelapsePulmonaryCDCured0514(this.getRelapsePulmonaryCDCured0514() + table.getRelapsePulmonaryCDCured0514());
		this.setRelapsePulmonaryCDCured1517(this.getRelapsePulmonaryCDCured1517() + table.getRelapsePulmonaryCDCured1517());
		this.setRelapseExtrapulmonaryCured(this.getRelapseExtrapulmonaryCured() + table.getRelapseExtrapulmonaryCured());
		this.setRelapseExtrapulmonaryCured04(this.getRelapseExtrapulmonaryCured04() + table.getRelapseExtrapulmonaryCured04());
		this.setRelapseExtrapulmonaryCured0514(this.getRelapseExtrapulmonaryCured0514() + table.getRelapseExtrapulmonaryCured0514());
		this.setRelapseExtrapulmonaryCured1517(this.getRelapseExtrapulmonaryCured1517() + table.getRelapseExtrapulmonaryCured1517());
		this.setRelapseAllCured(this.getRelapseAllCured() + table.getRelapseAllCured());
		this.setRelapseAllCured04(this.getRelapseAllCured04() + table.getRelapseAllCured04());
		this.setRelapseAllCured0514(this.getRelapseAllCured0514() + table.getRelapseAllCured0514());
		this.setRelapseAllCured1517(this.getRelapseAllCured1517() + table.getRelapseAllCured1517());
		this.setRelapsePulmonaryBCCompleted(this.getRelapsePulmonaryBCCompleted() + table.getRelapsePulmonaryBCCompleted());
		this.setRelapsePulmonaryBCCompleted04(this.getRelapsePulmonaryBCCompleted04() + table.getRelapsePulmonaryBCCompleted04());
		this.setRelapsePulmonaryBCCompleted0514(this.getRelapsePulmonaryBCCompleted0514() + table.getRelapsePulmonaryBCCompleted0514());
		this.setRelapsePulmonaryBCCompleted1517(this.getRelapsePulmonaryBCCompleted1517() + table.getRelapsePulmonaryBCCompleted1517());
		this.setRelapsePulmonaryCDCompleted(this.getRelapsePulmonaryCDCompleted() + table.getRelapsePulmonaryCDCompleted());
		this.setRelapsePulmonaryCDCompleted04(this.getRelapsePulmonaryCDCompleted04() + table.getRelapsePulmonaryCDCompleted04());
		this.setRelapsePulmonaryCDCompleted0514(this.getRelapsePulmonaryCDCompleted0514() + table.getRelapsePulmonaryCDCompleted0514());
		this.setRelapsePulmonaryCDCompleted1517(this.getRelapsePulmonaryCDCompleted1517() + table.getRelapsePulmonaryCDCompleted1517());
		this.setRelapseExtrapulmonaryCompleted(this.getRelapseExtrapulmonaryCompleted() + table.getRelapseExtrapulmonaryCompleted());
		this.setRelapseExtrapulmonaryCompleted04(this.getRelapseExtrapulmonaryCompleted04() + table.getRelapseExtrapulmonaryCompleted04());
		this.setRelapseExtrapulmonaryCompleted0514(this.getRelapseExtrapulmonaryCompleted0514() + table.getRelapseExtrapulmonaryCompleted0514());
		this.setRelapseExtrapulmonaryCompleted1517(this.getRelapseExtrapulmonaryCompleted1517() + table.getRelapseExtrapulmonaryCompleted1517());
		this.setRelapseAllCompleted(this.getRelapseAllCompleted() + table.getRelapseAllCompleted());
		this.setRelapseAllCompleted04(this.getRelapseAllCompleted04() + table.getRelapseAllCompleted04());
		this.setRelapseAllCompleted0514(this.getRelapseAllCompleted0514() + table.getRelapseAllCompleted0514());
		this.setRelapseAllCompleted1517(this.getRelapseAllCompleted1517() + table.getRelapseAllCompleted1517());
		this.setRelapsePulmonaryBCDiedTB(this.getRelapsePulmonaryBCDiedTB() + table.getRelapsePulmonaryBCDiedTB());
		this.setRelapsePulmonaryBCDiedTB04(this.getRelapsePulmonaryBCDiedTB04() + table.getRelapsePulmonaryBCDiedTB04());
		this.setRelapsePulmonaryBCDiedTB0514(this.getRelapsePulmonaryBCDiedTB0514() + table.getRelapsePulmonaryBCDiedTB0514());
		this.setRelapsePulmonaryBCDiedTB1517(this.getRelapsePulmonaryBCDiedTB1517() + table.getRelapsePulmonaryBCDiedTB1517());
		this.setRelapsePulmonaryCDDiedTB(this.getRelapsePulmonaryCDDiedTB() + table.getRelapsePulmonaryCDDiedTB());
		this.setRelapsePulmonaryCDDiedTB04(this.getRelapsePulmonaryCDDiedTB04() + table.getRelapsePulmonaryCDDiedTB04());
		this.setRelapsePulmonaryCDDiedTB0514(this.getRelapsePulmonaryCDDiedTB0514() + table.getRelapsePulmonaryCDDiedTB0514());
		this.setRelapsePulmonaryCDDiedTB1517(this.getRelapsePulmonaryCDDiedTB1517() + table.getRelapsePulmonaryCDDiedTB1517());
		this.setRelapseExtrapulmonaryDiedTB(this.getRelapseExtrapulmonaryDiedTB() + table.getRelapseExtrapulmonaryDiedTB());
		this.setRelapseExtrapulmonaryDiedTB04(this.getRelapseExtrapulmonaryDiedTB04() + table.getRelapseExtrapulmonaryDiedTB04());
		this.setRelapseExtrapulmonaryDiedTB0514(this.getRelapseExtrapulmonaryDiedTB0514() + table.getRelapseExtrapulmonaryDiedTB0514());
		this.setRelapseExtrapulmonaryDiedTB1517(this.getRelapseExtrapulmonaryDiedTB1517() + table.getRelapseExtrapulmonaryDiedTB1517());
		this.setRelapseAllDiedTB(this.getRelapseAllDiedTB() + table.getRelapseAllDiedTB());
		this.setRelapseAllDiedTB04(this.getRelapseAllDiedTB04() + table.getRelapseAllDiedTB04());
		this.setRelapseAllDiedTB0514(this.getRelapseAllDiedTB0514() + table.getRelapseAllDiedTB0514());
		this.setRelapseAllDiedTB1517(this.getRelapseAllDiedTB1517() + table.getRelapseAllDiedTB1517());
		this.setRelapsePulmonaryBCDiedNotTB(this.getRelapsePulmonaryBCDiedNotTB() + table.getRelapsePulmonaryBCDiedNotTB());
		this.setRelapsePulmonaryBCDiedNotTB04(this.getRelapsePulmonaryBCDiedNotTB04() + table.getRelapsePulmonaryBCDiedNotTB04());
		this.setRelapsePulmonaryBCDiedNotTB0514(this.getRelapsePulmonaryBCDiedNotTB0514() + table.getRelapsePulmonaryBCDiedNotTB0514());
		this.setRelapsePulmonaryBCDiedNotTB1517(this.getRelapsePulmonaryBCDiedNotTB1517() + table.getRelapsePulmonaryBCDiedNotTB1517());
		this.setRelapsePulmonaryCDDiedNotTB(this.getRelapsePulmonaryCDDiedNotTB() + table.getRelapsePulmonaryCDDiedNotTB());
		this.setRelapsePulmonaryCDDiedNotTB04(this.getRelapsePulmonaryCDDiedNotTB04() + table.getRelapsePulmonaryCDDiedNotTB04());
		this.setRelapsePulmonaryCDDiedNotTB0514(this.getRelapsePulmonaryCDDiedNotTB0514() + table.getRelapsePulmonaryCDDiedNotTB0514());
		this.setRelapsePulmonaryCDDiedNotTB1517(this.getRelapsePulmonaryCDDiedNotTB1517() + table.getRelapsePulmonaryCDDiedNotTB1517());
		this.setRelapseExtrapulmonaryDiedNotTB(this.getRelapseExtrapulmonaryDiedNotTB() + table.getRelapseExtrapulmonaryDiedNotTB());
		this.setRelapseExtrapulmonaryDiedNotTB04(this.getRelapseExtrapulmonaryDiedNotTB04() + table.getRelapseExtrapulmonaryDiedNotTB04());
		this.setRelapseExtrapulmonaryDiedNotTB0514(this.getRelapseExtrapulmonaryDiedNotTB0514() + table.getRelapseExtrapulmonaryDiedNotTB0514());
		this.setRelapseExtrapulmonaryDiedNotTB1517(this.getRelapseExtrapulmonaryDiedNotTB1517() + table.getRelapseExtrapulmonaryDiedNotTB1517());
		this.setRelapseAllDiedNotTB(this.getRelapseAllDiedNotTB() + table.getRelapseAllDiedNotTB());
		this.setRelapseAllDiedNotTB04(this.getRelapseAllDiedNotTB04() + table.getRelapseAllDiedNotTB04());
		this.setRelapseAllDiedNotTB0514(this.getRelapseAllDiedNotTB0514() + table.getRelapseAllDiedNotTB0514());
		this.setRelapseAllDiedNotTB1517(this.getRelapseAllDiedNotTB1517() + table.getRelapseAllDiedNotTB1517());
		this.setRelapsePulmonaryBCFailed(this.getRelapsePulmonaryBCFailed() + table.getRelapsePulmonaryBCFailed());
		this.setRelapsePulmonaryBCFailed04(this.getRelapsePulmonaryBCFailed04() + table.getRelapsePulmonaryBCFailed04());
		this.setRelapsePulmonaryBCFailed0514(this.getRelapsePulmonaryBCFailed0514() + table.getRelapsePulmonaryBCFailed0514());
		this.setRelapsePulmonaryBCFailed1517(this.getRelapsePulmonaryBCFailed1517() + table.getRelapsePulmonaryBCFailed1517());
		this.setRelapsePulmonaryCDFailed(this.getRelapsePulmonaryCDFailed() + table.getRelapsePulmonaryCDFailed());
		this.setRelapsePulmonaryCDFailed04(this.getRelapsePulmonaryCDFailed04() + table.getRelapsePulmonaryCDFailed04());
		this.setRelapsePulmonaryCDFailed0514(this.getRelapsePulmonaryCDFailed0514() + table.getRelapsePulmonaryCDFailed0514());
		this.setRelapsePulmonaryCDFailed1517(this.getRelapsePulmonaryCDFailed1517() + table.getRelapsePulmonaryCDFailed1517());
		this.setRelapseExtrapulmonaryFailed(this.getRelapseExtrapulmonaryFailed() + table.getRelapseExtrapulmonaryFailed());
		this.setRelapseExtrapulmonaryFailed04(this.getRelapseExtrapulmonaryFailed04() + table.getRelapseExtrapulmonaryFailed04());
		this.setRelapseExtrapulmonaryFailed0514(this.getRelapseExtrapulmonaryFailed0514() + table.getRelapseExtrapulmonaryFailed0514());
		this.setRelapseExtrapulmonaryFailed1517(this.getRelapseExtrapulmonaryFailed1517() + table.getRelapseExtrapulmonaryFailed1517());
		this.setRelapseAllFailed(this.getRelapseAllFailed() + table.getRelapseAllFailed());
		this.setRelapseAllFailed04(this.getRelapseAllFailed04() + table.getRelapseAllFailed04());
		this.setRelapseAllFailed0514(this.getRelapseAllFailed0514() + table.getRelapseAllFailed0514());
		this.setRelapseAllFailed1517(this.getRelapseAllFailed1517() + table.getRelapseAllFailed1517());
		this.setRelapsePulmonaryBCDefaulted(this.getRelapsePulmonaryBCDefaulted() + table.getRelapsePulmonaryBCDefaulted());
		this.setRelapsePulmonaryBCDefaulted04(this.getRelapsePulmonaryBCDefaulted04() + table.getRelapsePulmonaryBCDefaulted04());
		this.setRelapsePulmonaryBCDefaulted0514(this.getRelapsePulmonaryBCDefaulted0514() + table.getRelapsePulmonaryBCDefaulted0514());
		this.setRelapsePulmonaryBCDefaulted1517(this.getRelapsePulmonaryBCDefaulted1517() + table.getRelapsePulmonaryBCDefaulted1517());
		this.setRelapsePulmonaryCDDefaulted(this.getRelapsePulmonaryCDDefaulted() + table.getRelapsePulmonaryCDDefaulted());
		this.setRelapsePulmonaryCDDefaulted04(this.getRelapsePulmonaryCDDefaulted04() + table.getRelapsePulmonaryCDDefaulted04());
		this.setRelapsePulmonaryCDDefaulted0514(this.getRelapsePulmonaryCDDefaulted0514() + table.getRelapsePulmonaryCDDefaulted0514());
		this.setRelapsePulmonaryCDDefaulted1517(this.getRelapsePulmonaryCDDefaulted1517() + table.getRelapsePulmonaryCDDefaulted1517());
		this.setRelapseExtrapulmonaryDefaulted(this.getRelapseExtrapulmonaryDefaulted() + table.getRelapseExtrapulmonaryDefaulted());
		this.setRelapseExtrapulmonaryDefaulted04(this.getRelapseExtrapulmonaryDefaulted04() + table.getRelapseExtrapulmonaryDefaulted04());
		this.setRelapseExtrapulmonaryDefaulted0514(this.getRelapseExtrapulmonaryDefaulted0514() + table.getRelapseExtrapulmonaryDefaulted0514());
		this.setRelapseExtrapulmonaryDefaulted1517(this.getRelapseExtrapulmonaryDefaulted1517() + table.getRelapseExtrapulmonaryDefaulted1517());
		this.setRelapseAllDefaulted(this.getRelapseAllDefaulted() + table.getRelapseAllDefaulted());
		this.setRelapseAllDefaulted04(this.getRelapseAllDefaulted04() + table.getRelapseAllDefaulted04());
		this.setRelapseAllDefaulted0514(this.getRelapseAllDefaulted0514() + table.getRelapseAllDefaulted0514());
		this.setRelapseAllDefaulted1517(this.getRelapseAllDefaulted1517() + table.getRelapseAllDefaulted1517());
		this.setRelapsePulmonaryBCTransferOut(this.getRelapsePulmonaryBCTransferOut() + table.getRelapsePulmonaryBCTransferOut());
		this.setRelapsePulmonaryBCTransferOut04(this.getRelapsePulmonaryBCTransferOut04() + table.getRelapsePulmonaryBCTransferOut04());
		this.setRelapsePulmonaryBCTransferOut0514(this.getRelapsePulmonaryBCTransferOut0514() + table.getRelapsePulmonaryBCTransferOut0514());
		this.setRelapsePulmonaryBCTransferOut1517(this.getRelapsePulmonaryBCTransferOut1517() + table.getRelapsePulmonaryBCTransferOut1517());
		this.setRelapsePulmonaryCDTransferOut(this.getRelapsePulmonaryCDTransferOut() + table.getRelapsePulmonaryCDTransferOut());
		this.setRelapsePulmonaryCDTransferOut04(this.getRelapsePulmonaryCDTransferOut04() + table.getRelapsePulmonaryCDTransferOut04());
		this.setRelapsePulmonaryCDTransferOut0514(this.getRelapsePulmonaryCDTransferOut0514() + table.getRelapsePulmonaryCDTransferOut0514());
		this.setRelapsePulmonaryCDTransferOut1517(this.getRelapsePulmonaryCDTransferOut1517() + table.getRelapsePulmonaryCDTransferOut1517());
		this.setRelapseExtrapulmonaryTransferOut(this.getRelapseExtrapulmonaryTransferOut() + table.getRelapseExtrapulmonaryTransferOut());
		this.setRelapseExtrapulmonaryTransferOut04(this.getRelapseExtrapulmonaryTransferOut04() + table.getRelapseExtrapulmonaryTransferOut04());
		this.setRelapseExtrapulmonaryTransferOut0514(this.getRelapseExtrapulmonaryTransferOut0514() + table.getRelapseExtrapulmonaryTransferOut0514());
		this.setRelapseExtrapulmonaryTransferOut1517(this.getRelapseExtrapulmonaryTransferOut1517() + table.getRelapseExtrapulmonaryTransferOut1517());
		this.setRelapseAllTransferOut(this.getRelapseAllTransferOut() + table.getRelapseAllTransferOut());
		this.setRelapseAllTransferOut04(this.getRelapseAllTransferOut04() + table.getRelapseAllTransferOut04());
		this.setRelapseAllTransferOut0514(this.getRelapseAllTransferOut0514() + table.getRelapseAllTransferOut0514());
		this.setRelapseAllTransferOut1517(this.getRelapseAllTransferOut1517() + table.getRelapseAllTransferOut1517());
		this.setRelapsePulmonaryBCCanceled(this.getRelapsePulmonaryBCCanceled() + table.getRelapsePulmonaryBCCanceled());
		this.setRelapsePulmonaryBCCanceled04(this.getRelapsePulmonaryBCCanceled04() + table.getRelapsePulmonaryBCCanceled04());
		this.setRelapsePulmonaryBCCanceled0514(this.getRelapsePulmonaryBCCanceled0514() + table.getRelapsePulmonaryBCCanceled0514());
		this.setRelapsePulmonaryBCCanceled1517(this.getRelapsePulmonaryBCCanceled1517() + table.getRelapsePulmonaryBCCanceled1517());
		this.setRelapsePulmonaryCDCanceled(this.getRelapsePulmonaryCDCanceled() + table.getRelapsePulmonaryCDCanceled());
		this.setRelapsePulmonaryCDCanceled04(this.getRelapsePulmonaryCDCanceled04() + table.getRelapsePulmonaryCDCanceled04());
		this.setRelapsePulmonaryCDCanceled0514(this.getRelapsePulmonaryCDCanceled0514() + table.getRelapsePulmonaryCDCanceled0514());
		this.setRelapsePulmonaryCDCanceled1517(this.getRelapsePulmonaryCDCanceled1517() + table.getRelapsePulmonaryCDCanceled1517());
		this.setRelapseExtrapulmonaryCanceled(this.getRelapseExtrapulmonaryCanceled() + table.getRelapseExtrapulmonaryCanceled());
		this.setRelapseExtrapulmonaryCanceled04(this.getRelapseExtrapulmonaryCanceled04() + table.getRelapseExtrapulmonaryCanceled04());
		this.setRelapseExtrapulmonaryCanceled0514(this.getRelapseExtrapulmonaryCanceled0514() + table.getRelapseExtrapulmonaryCanceled0514());
		this.setRelapseExtrapulmonaryCanceled1517(this.getRelapseExtrapulmonaryCanceled1517() + table.getRelapseExtrapulmonaryCanceled1517());
		this.setRelapseAllCanceled(this.getRelapseAllCanceled() + table.getRelapseAllCanceled());
		this.setRelapseAllCanceled04(this.getRelapseAllCanceled04() + table.getRelapseAllCanceled04());
		this.setRelapseAllCanceled0514(this.getRelapseAllCanceled0514() + table.getRelapseAllCanceled0514());
		this.setRelapseAllCanceled1517(this.getRelapseAllCanceled1517() + table.getRelapseAllCanceled1517());
		this.setRelapsePulmonaryBCSLD(this.getRelapsePulmonaryBCSLD() + table.getRelapsePulmonaryBCSLD());
		this.setRelapsePulmonaryBCSLD04(this.getRelapsePulmonaryBCSLD04() + table.getRelapsePulmonaryBCSLD04());
		this.setRelapsePulmonaryBCSLD0514(this.getRelapsePulmonaryBCSLD0514() + table.getRelapsePulmonaryBCSLD0514());
		this.setRelapsePulmonaryBCSLD1517(this.getRelapsePulmonaryBCSLD1517() + table.getRelapsePulmonaryBCSLD1517());
		this.setRelapsePulmonaryCDSLD(this.getRelapsePulmonaryCDSLD() + table.getRelapsePulmonaryCDSLD());
		this.setRelapsePulmonaryCDSLD04(this.getRelapsePulmonaryCDSLD04() + table.getRelapsePulmonaryCDSLD04());
		this.setRelapsePulmonaryCDSLD0514(this.getRelapsePulmonaryCDSLD0514() + table.getRelapsePulmonaryCDSLD0514());
		this.setRelapsePulmonaryCDSLD1517(this.getRelapsePulmonaryCDSLD1517() + table.getRelapsePulmonaryCDSLD1517());
		this.setRelapseExtrapulmonarySLD(this.getRelapseExtrapulmonarySLD() + table.getRelapseExtrapulmonarySLD());
		this.setRelapseExtrapulmonarySLD04(this.getRelapseExtrapulmonarySLD04() + table.getRelapseExtrapulmonarySLD04());
		this.setRelapseExtrapulmonarySLD0514(this.getRelapseExtrapulmonarySLD0514() + table.getRelapseExtrapulmonarySLD0514());
		this.setRelapseExtrapulmonarySLD1517(this.getRelapseExtrapulmonarySLD1517() + table.getRelapseExtrapulmonarySLD1517());
		this.setRelapseAllSLD(this.getRelapseAllSLD() + table.getRelapseAllSLD());
		this.setRelapseAllSLD04(this.getRelapseAllSLD04() + table.getRelapseAllSLD04());
		this.setRelapseAllSLD0514(this.getRelapseAllSLD0514() + table.getRelapseAllSLD0514());
		this.setRelapseAllSLD1517(this.getRelapseAllSLD1517() + table.getRelapseAllSLD1517());
		this.setFailurePulmonaryBCDetected(this.getFailurePulmonaryBCDetected() + table.getFailurePulmonaryBCDetected());
		this.setFailurePulmonaryCDDetected(this.getFailurePulmonaryCDDetected() + table.getFailurePulmonaryCDDetected());
		this.setFailureExtrapulmonaryDetected(this.getFailureExtrapulmonaryDetected() + table.getFailureExtrapulmonaryDetected());
		this.setFailureAllDetected(this.getFailureAllDetected() + table.getFailureAllDetected());
		this.setFailurePulmonaryBCEligible(this.getFailurePulmonaryBCEligible() + table.getFailurePulmonaryBCEligible());
		this.setFailurePulmonaryCDEligible(this.getFailurePulmonaryCDEligible() + table.getFailurePulmonaryCDEligible());
		this.setFailureExtrapulmonaryEligible(this.getFailureExtrapulmonaryEligible() + table.getFailureExtrapulmonaryEligible());
		this.setFailureAllEligible(this.getFailureAllEligible() + table.getFailureAllEligible());
		this.setFailurePulmonaryBCCured(this.getFailurePulmonaryBCCured() + table.getFailurePulmonaryBCCured());
		this.setFailurePulmonaryCDCured(this.getFailurePulmonaryCDCured() + table.getFailurePulmonaryCDCured());
		this.setFailureExtrapulmonaryCured(this.getFailureExtrapulmonaryCured() + table.getFailureExtrapulmonaryCured());
		this.setFailureAllCured(this.getFailureAllCured() + table.getFailureAllCured());
		this.setFailurePulmonaryBCCompleted(this.getFailurePulmonaryBCCompleted() + table.getFailurePulmonaryBCCompleted());
		this.setFailurePulmonaryCDCompleted(this.getFailurePulmonaryCDCompleted() + table.getFailurePulmonaryCDCompleted());
		this.setFailureExtrapulmonaryCompleted(this.getFailureExtrapulmonaryCompleted() + table.getFailureExtrapulmonaryCompleted());
		this.setFailureAllCompleted(this.getFailureAllCompleted() + table.getFailureAllCompleted());
		this.setFailurePulmonaryBCDiedTB(this.getFailurePulmonaryBCDiedTB() + table.getFailurePulmonaryBCDiedTB());
		this.setFailurePulmonaryCDDiedTB(this.getFailurePulmonaryCDDiedTB() + table.getFailurePulmonaryCDDiedTB());
		this.setFailureExtrapulmonaryDiedTB(this.getFailureExtrapulmonaryDiedTB() + table.getFailureExtrapulmonaryDiedTB());
		this.setFailureAllDiedTB(this.getFailureAllDiedTB() + table.getFailureAllDiedTB());
		this.setFailurePulmonaryBCDiedNotTB(this.getFailurePulmonaryBCDiedNotTB() + table.getFailurePulmonaryBCDiedNotTB());
		this.setFailurePulmonaryCDDiedNotTB(this.getFailurePulmonaryCDDiedNotTB() + table.getFailurePulmonaryCDDiedNotTB());
		this.setFailureExtrapulmonaryDiedNotTB(this.getFailureExtrapulmonaryDiedNotTB() + table.getFailureExtrapulmonaryDiedNotTB());
		this.setFailureAllDiedNotTB(this.getFailureAllDiedNotTB() + table.getFailureAllDiedNotTB());
		this.setFailurePulmonaryBCFailed(this.getFailurePulmonaryBCFailed() + table.getFailurePulmonaryBCFailed());
		this.setFailurePulmonaryCDFailed(this.getFailurePulmonaryCDFailed() + table.getFailurePulmonaryCDFailed());
		this.setFailureExtrapulmonaryFailed(this.getFailureExtrapulmonaryFailed() + table.getFailureExtrapulmonaryFailed());
		this.setFailureAllFailed(this.getFailureAllFailed() + table.getFailureAllFailed());
		this.setFailurePulmonaryBCDefaulted(this.getFailurePulmonaryBCDefaulted() + table.getFailurePulmonaryBCDefaulted());
		this.setFailurePulmonaryCDDefaulted(this.getFailurePulmonaryCDDefaulted() + table.getFailurePulmonaryCDDefaulted());
		this.setFailureExtrapulmonaryDefaulted(this.getFailureExtrapulmonaryDefaulted() + table.getFailureExtrapulmonaryDefaulted());
		this.setFailureAllDefaulted(this.getFailureAllDefaulted() + table.getFailureAllDefaulted());
		this.setFailurePulmonaryBCTransferOut(this.getFailurePulmonaryBCTransferOut() + table.getFailurePulmonaryBCTransferOut());
		this.setFailurePulmonaryCDTransferOut(this.getFailurePulmonaryCDTransferOut() + table.getFailurePulmonaryCDTransferOut());
		this.setFailureExtrapulmonaryTransferOut(this.getFailureExtrapulmonaryTransferOut() + table.getFailureExtrapulmonaryTransferOut());
		this.setFailureAllTransferOut(this.getFailureAllTransferOut() + table.getFailureAllTransferOut());
		this.setFailurePulmonaryBCCanceled(this.getFailurePulmonaryBCCanceled() + table.getFailurePulmonaryBCCanceled());
		this.setFailurePulmonaryCDCanceled(this.getFailurePulmonaryCDCanceled() + table.getFailurePulmonaryCDCanceled());
		this.setFailureExtrapulmonaryCanceled(this.getFailureExtrapulmonaryCanceled() + table.getFailureExtrapulmonaryCanceled());
		this.setFailureAllCanceled(this.getFailureAllCanceled() + table.getFailureAllCanceled());
		this.setFailurePulmonaryBCSLD(this.getFailurePulmonaryBCSLD() + table.getFailurePulmonaryBCSLD());
		this.setFailurePulmonaryCDSLD(this.getFailurePulmonaryCDSLD() + table.getFailurePulmonaryCDSLD());
		this.setFailureExtrapulmonarySLD(this.getFailureExtrapulmonarySLD() + table.getFailureExtrapulmonarySLD());
		this.setFailureAllSLD(this.getFailureAllSLD() + table.getFailureAllSLD());
		this.setDefaultPulmonaryBCDetected(this.getDefaultPulmonaryBCDetected() + table.getDefaultPulmonaryBCDetected());
		this.setDefaultPulmonaryCDDetected(this.getDefaultPulmonaryCDDetected() + table.getDefaultPulmonaryCDDetected());
		this.setDefaultExtrapulmonaryDetected(this.getDefaultExtrapulmonaryDetected() + table.getDefaultExtrapulmonaryDetected());
		this.setDefaultAllDetected(this.getDefaultAllDetected() + table.getDefaultAllDetected());
		this.setDefaultPulmonaryBCEligible(this.getDefaultPulmonaryBCEligible() + table.getDefaultPulmonaryBCEligible());
		this.setDefaultPulmonaryCDEligible(this.getDefaultPulmonaryCDEligible() + table.getDefaultPulmonaryCDEligible());
		this.setDefaultExtrapulmonaryEligible(this.getDefaultExtrapulmonaryEligible() + table.getDefaultExtrapulmonaryEligible());
		this.setDefaultAllEligible(this.getDefaultAllEligible() + table.getDefaultAllEligible());
		this.setDefaultPulmonaryBCCured(this.getDefaultPulmonaryBCCured() + table.getDefaultPulmonaryBCCured());
		this.setDefaultPulmonaryCDCured(this.getDefaultPulmonaryCDCured() + table.getDefaultPulmonaryCDCured());
		this.setDefaultExtrapulmonaryCured(this.getDefaultExtrapulmonaryCured() + table.getDefaultExtrapulmonaryCured());
		this.setDefaultAllCured(this.getDefaultAllCured() + table.getDefaultAllCured());
		this.setDefaultPulmonaryBCCompleted(this.getDefaultPulmonaryBCCompleted() + table.getDefaultPulmonaryBCCompleted());
		this.setDefaultPulmonaryCDCompleted(this.getDefaultPulmonaryCDCompleted() + table.getDefaultPulmonaryCDCompleted());
		this.setDefaultExtrapulmonaryCompleted(this.getDefaultExtrapulmonaryCompleted() + table.getDefaultExtrapulmonaryCompleted());
		this.setDefaultAllCompleted(this.getDefaultAllCompleted() + table.getDefaultAllCompleted());
		this.setDefaultPulmonaryBCDiedTB(this.getDefaultPulmonaryBCDiedTB() + table.getDefaultPulmonaryBCDiedTB());
		this.setDefaultPulmonaryCDDiedTB(this.getDefaultPulmonaryCDDiedTB() + table.getDefaultPulmonaryCDDiedTB());
		this.setDefaultExtrapulmonaryDiedTB(this.getDefaultExtrapulmonaryDiedTB() + table.getDefaultExtrapulmonaryDiedTB());
		this.setDefaultAllDiedTB(this.getDefaultAllDiedTB() + table.getDefaultAllDiedTB());
		this.setDefaultPulmonaryBCDiedNotTB(this.getDefaultPulmonaryBCDiedNotTB() + table.getDefaultPulmonaryBCDiedNotTB());
		this.setDefaultPulmonaryCDDiedNotTB(this.getDefaultPulmonaryCDDiedNotTB() + table.getDefaultPulmonaryCDDiedNotTB());
		this.setDefaultExtrapulmonaryDiedNotTB(this.getDefaultExtrapulmonaryDiedNotTB() + table.getDefaultExtrapulmonaryDiedNotTB());
		this.setDefaultAllDiedNotTB(this.getDefaultAllDiedNotTB() + table.getDefaultAllDiedNotTB());
		this.setDefaultPulmonaryBCFailed(this.getDefaultPulmonaryBCFailed() + table.getDefaultPulmonaryBCFailed());
		this.setDefaultPulmonaryCDFailed(this.getDefaultPulmonaryCDFailed() + table.getDefaultPulmonaryCDFailed());
		this.setDefaultExtrapulmonaryFailed(this.getDefaultExtrapulmonaryFailed() + table.getDefaultExtrapulmonaryFailed());
		this.setDefaultAllFailed(this.getDefaultAllFailed() + table.getDefaultAllFailed());
		this.setDefaultPulmonaryBCDefaulted(this.getDefaultPulmonaryBCDefaulted() + table.getDefaultPulmonaryBCDefaulted());
		this.setDefaultPulmonaryCDDefaulted(this.getDefaultPulmonaryCDDefaulted() + table.getDefaultPulmonaryCDDefaulted());
		this.setDefaultExtrapulmonaryDefaulted(this.getDefaultExtrapulmonaryDefaulted() + table.getDefaultExtrapulmonaryDefaulted());
		this.setDefaultAllDefaulted(this.getDefaultAllDefaulted() + table.getDefaultAllDefaulted());
		this.setDefaultPulmonaryBCTransferOut(this.getDefaultPulmonaryBCTransferOut() + table.getDefaultPulmonaryBCTransferOut());
		this.setDefaultPulmonaryCDTransferOut(this.getDefaultPulmonaryCDTransferOut() + table.getDefaultPulmonaryCDTransferOut());
		this.setDefaultExtrapulmonaryTransferOut(this.getDefaultExtrapulmonaryTransferOut() + table.getDefaultExtrapulmonaryTransferOut());
		this.setDefaultAllTransferOut(this.getDefaultAllTransferOut() + table.getDefaultAllTransferOut());
		this.setDefaultPulmonaryBCCanceled(this.getDefaultPulmonaryBCCanceled() + table.getDefaultPulmonaryBCCanceled());
		this.setDefaultPulmonaryCDCanceled(this.getDefaultPulmonaryCDCanceled() + table.getDefaultPulmonaryCDCanceled());
		this.setDefaultExtrapulmonaryCanceled(this.getDefaultExtrapulmonaryCanceled() + table.getDefaultExtrapulmonaryCanceled());
		this.setDefaultAllCanceled(this.getDefaultAllCanceled() + table.getDefaultAllCanceled());
		this.setDefaultPulmonaryBCSLD(this.getDefaultPulmonaryBCSLD() + table.getDefaultPulmonaryBCSLD());
		this.setDefaultPulmonaryCDSLD(this.getDefaultPulmonaryCDSLD() + table.getDefaultPulmonaryCDSLD());
		this.setDefaultExtrapulmonarySLD(this.getDefaultExtrapulmonarySLD() + table.getDefaultExtrapulmonarySLD());
		this.setDefaultAllSLD(this.getDefaultAllSLD() + table.getDefaultAllSLD());
		this.setOtherPulmonaryBCDetected(this.getOtherPulmonaryBCDetected() + table.getOtherPulmonaryBCDetected());
		this.setOtherPulmonaryCDDetected(this.getOtherPulmonaryCDDetected() + table.getOtherPulmonaryCDDetected());
		this.setOtherExtrapulmonaryDetected(this.getOtherExtrapulmonaryDetected() + table.getOtherExtrapulmonaryDetected());
		this.setOtherAllDetected(this.getOtherAllDetected() + table.getOtherAllDetected());
		this.setOtherPulmonaryBCEligible(this.getOtherPulmonaryBCEligible() + table.getOtherPulmonaryBCEligible());
		this.setOtherPulmonaryCDEligible(this.getOtherPulmonaryCDEligible() + table.getOtherPulmonaryCDEligible());
		this.setOtherExtrapulmonaryEligible(this.getOtherExtrapulmonaryEligible() + table.getOtherExtrapulmonaryEligible());
		this.setOtherAllEligible(this.getOtherAllEligible() + table.getOtherAllEligible());
		this.setOtherPulmonaryBCCured(this.getOtherPulmonaryBCCured() + table.getOtherPulmonaryBCCured());
		this.setOtherPulmonaryCDCured(this.getOtherPulmonaryCDCured() + table.getOtherPulmonaryCDCured());
		this.setOtherExtrapulmonaryCured(this.getOtherExtrapulmonaryCured() + table.getOtherExtrapulmonaryCured());
		this.setOtherAllCured(this.getOtherAllCured() + table.getOtherAllCured());
		this.setOtherPulmonaryBCCompleted(this.getOtherPulmonaryBCCompleted() + table.getOtherPulmonaryBCCompleted());
		this.setOtherPulmonaryCDCompleted(this.getOtherPulmonaryCDCompleted() + table.getOtherPulmonaryCDCompleted());
		this.setOtherExtrapulmonaryCompleted(this.getOtherExtrapulmonaryCompleted() + table.getOtherExtrapulmonaryCompleted());
		this.setOtherAllCompleted(this.getOtherAllCompleted() + table.getOtherAllCompleted());
		this.setOtherPulmonaryBCDiedTB(this.getOtherPulmonaryBCDiedTB() + table.getOtherPulmonaryBCDiedTB());
		this.setOtherPulmonaryCDDiedTB(this.getOtherPulmonaryCDDiedTB() + table.getOtherPulmonaryCDDiedTB());
		this.setOtherExtrapulmonaryDiedTB(this.getOtherExtrapulmonaryDiedTB() + table.getOtherExtrapulmonaryDiedTB());
		this.setOtherAllDiedTB(this.getOtherAllDiedTB() + table.getOtherAllDiedTB());
		this.setOtherPulmonaryBCDiedNotTB(this.getOtherPulmonaryBCDiedNotTB() + table.getOtherPulmonaryBCDiedNotTB());
		this.setOtherPulmonaryCDDiedNotTB(this.getOtherPulmonaryCDDiedNotTB() + table.getOtherPulmonaryCDDiedNotTB());
		this.setOtherExtrapulmonaryDiedNotTB(this.getOtherExtrapulmonaryDiedNotTB() + table.getOtherExtrapulmonaryDiedNotTB());
		this.setOtherAllDiedNotTB(this.getOtherAllDiedNotTB() + table.getOtherAllDiedNotTB());
		this.setOtherPulmonaryBCFailed(this.getOtherPulmonaryBCFailed() + table.getOtherPulmonaryBCFailed());
		this.setOtherPulmonaryCDFailed(this.getOtherPulmonaryCDFailed() + table.getOtherPulmonaryCDFailed());
		this.setOtherExtrapulmonaryFailed(this.getOtherExtrapulmonaryFailed() + table.getOtherExtrapulmonaryFailed());
		this.setOtherAllFailed(this.getOtherAllFailed() + table.getOtherAllFailed());
		this.setOtherPulmonaryBCDefaulted(this.getOtherPulmonaryBCDefaulted() + table.getOtherPulmonaryBCDefaulted());
		this.setOtherPulmonaryCDDefaulted(this.getOtherPulmonaryCDDefaulted() + table.getOtherPulmonaryCDDefaulted());
		this.setOtherExtrapulmonaryDefaulted(this.getOtherExtrapulmonaryDefaulted() + table.getOtherExtrapulmonaryDefaulted());
		this.setOtherAllDefaulted(this.getOtherAllDefaulted() + table.getOtherAllDefaulted());
		this.setOtherPulmonaryBCTransferOut(this.getOtherPulmonaryBCTransferOut() + table.getOtherPulmonaryBCTransferOut());
		this.setOtherPulmonaryCDTransferOut(this.getOtherPulmonaryCDTransferOut() + table.getOtherPulmonaryCDTransferOut());
		this.setOtherExtrapulmonaryTransferOut(this.getOtherExtrapulmonaryTransferOut() + table.getOtherExtrapulmonaryTransferOut());
		this.setOtherAllTransferOut(this.getOtherAllTransferOut() + table.getOtherAllTransferOut());
		this.setOtherPulmonaryBCCanceled(this.getOtherPulmonaryBCCanceled() + table.getOtherPulmonaryBCCanceled());
		this.setOtherPulmonaryCDCanceled(this.getOtherPulmonaryCDCanceled() + table.getOtherPulmonaryCDCanceled());
		this.setOtherExtrapulmonaryCanceled(this.getOtherExtrapulmonaryCanceled() + table.getOtherExtrapulmonaryCanceled());
		this.setOtherAllCanceled(this.getOtherAllCanceled() + table.getOtherAllCanceled());
		this.setOtherPulmonaryBCSLD(this.getOtherPulmonaryBCSLD() + table.getOtherPulmonaryBCSLD());
		this.setOtherPulmonaryCDSLD(this.getOtherPulmonaryCDSLD() + table.getOtherPulmonaryCDSLD());
		this.setOtherExtrapulmonarySLD(this.getOtherExtrapulmonarySLD() + table.getOtherExtrapulmonarySLD());
		this.setOtherAllSLD(this.getOtherAllSLD() + table.getOtherAllSLD());
		this.setRtxPulmonaryBCDetected(this.getRtxPulmonaryBCDetected() + table.getRtxPulmonaryBCDetected());
		this.setRtxPulmonaryCDDetected(this.getRtxPulmonaryCDDetected() + table.getRtxPulmonaryCDDetected());
		this.setRtxExtrapulmonaryDetected(this.getRtxExtrapulmonaryDetected() + table.getRtxExtrapulmonaryDetected());
		this.setRtxAllDetected(this.getRtxAllDetected() + table.getRtxAllDetected());
		this.setRtxPulmonaryBCEligible(this.getRtxPulmonaryBCEligible() + table.getRtxPulmonaryBCEligible());
		this.setRtxPulmonaryCDEligible(this.getRtxPulmonaryCDEligible() + table.getRtxPulmonaryCDEligible());
		this.setRtxExtrapulmonaryEligible(this.getRtxExtrapulmonaryEligible() + table.getRtxExtrapulmonaryEligible());
		this.setRtxAllEligible(this.getRtxAllEligible() + table.getRtxAllEligible());
		this.setRtxPulmonaryBCCured(this.getRtxPulmonaryBCCured() + table.getRtxPulmonaryBCCured());
		this.setRtxPulmonaryCDCured(this.getRtxPulmonaryCDCured() + table.getRtxPulmonaryCDCured());
		this.setRtxExtrapulmonaryCured(this.getRtxExtrapulmonaryCured() + table.getRtxExtrapulmonaryCured());
		this.setRtxAllCured(this.getRtxAllCured() + table.getRtxAllCured());
		this.setRtxPulmonaryBCCompleted(this.getRtxPulmonaryBCCompleted() + table.getRtxPulmonaryBCCompleted());
		this.setRtxPulmonaryCDCompleted(this.getRtxPulmonaryCDCompleted() + table.getRtxPulmonaryCDCompleted());
		this.setRtxExtrapulmonaryCompleted(this.getRtxExtrapulmonaryCompleted() + table.getRtxExtrapulmonaryCompleted());
		this.setRtxAllCompleted(this.getRtxAllCompleted() + table.getRtxAllCompleted());
		this.setRtxPulmonaryBCDiedTB(this.getRtxPulmonaryBCDiedTB() + table.getRtxPulmonaryBCDiedTB());
		this.setRtxPulmonaryCDDiedTB(this.getRtxPulmonaryCDDiedTB() + table.getRtxPulmonaryCDDiedTB());
		this.setRtxExtrapulmonaryDiedTB(this.getRtxExtrapulmonaryDiedTB() + table.getRtxExtrapulmonaryDiedTB());
		this.setRtxAllDiedTB(this.getRtxAllDiedTB() + table.getRtxAllDiedTB());
		this.setRtxPulmonaryBCDiedNotTB(this.getRtxPulmonaryBCDiedNotTB() + table.getRtxPulmonaryBCDiedNotTB());
		this.setRtxPulmonaryCDDiedNotTB(this.getRtxPulmonaryCDDiedNotTB() + table.getRtxPulmonaryCDDiedNotTB());
		this.setRtxExtrapulmonaryDiedNotTB(this.getRtxExtrapulmonaryDiedNotTB() + table.getRtxExtrapulmonaryDiedNotTB());
		this.setRtxAllDiedNotTB(this.getRtxAllDiedNotTB() + table.getRtxAllDiedNotTB());
		this.setRtxPulmonaryBCFailed(this.getRtxPulmonaryBCFailed() + table.getRtxPulmonaryBCFailed());
		this.setRtxPulmonaryCDFailed(this.getRtxPulmonaryCDFailed() + table.getRtxPulmonaryCDFailed());
		this.setRtxExtrapulmonaryFailed(this.getRtxExtrapulmonaryFailed() + table.getRtxExtrapulmonaryFailed());
		this.setRtxAllFailed(this.getRtxAllFailed() + table.getRtxAllFailed());
		this.setRtxPulmonaryBCDefaulted(this.getRtxPulmonaryBCDefaulted() + table.getRtxPulmonaryBCDefaulted());
		this.setRtxPulmonaryCDDefaulted(this.getRtxPulmonaryCDDefaulted() + table.getRtxPulmonaryCDDefaulted());
		this.setRtxExtrapulmonaryDefaulted(this.getRtxExtrapulmonaryDefaulted() + table.getRtxExtrapulmonaryDefaulted());
		this.setRtxAllDefaulted(this.getRtxAllDefaulted() + table.getRtxAllDefaulted());
		this.setRtxPulmonaryBCTransferOut(this.getRtxPulmonaryBCTransferOut() + table.getRtxPulmonaryBCTransferOut());
		this.setRtxPulmonaryCDTransferOut(this.getRtxPulmonaryCDTransferOut() + table.getRtxPulmonaryCDTransferOut());
		this.setRtxExtrapulmonaryTransferOut(this.getRtxExtrapulmonaryTransferOut() + table.getRtxExtrapulmonaryTransferOut());
		this.setRtxAllTransferOut(this.getRtxAllTransferOut() + table.getRtxAllTransferOut());
		this.setRtxPulmonaryBCCanceled(this.getRtxPulmonaryBCCanceled() + table.getRtxPulmonaryBCCanceled());
		this.setRtxPulmonaryCDCanceled(this.getRtxPulmonaryCDCanceled() + table.getRtxPulmonaryCDCanceled());
		this.setRtxExtrapulmonaryCanceled(this.getRtxExtrapulmonaryCanceled() + table.getRtxExtrapulmonaryCanceled());
		this.setRtxAllCanceled(this.getRtxAllCanceled() + table.getRtxAllCanceled());
		this.setRtxPulmonaryBCSLD(this.getRtxPulmonaryBCSLD() + table.getRtxPulmonaryBCSLD());
		this.setRtxPulmonaryCDSLD(this.getRtxPulmonaryCDSLD() + table.getRtxPulmonaryCDSLD());
		this.setRtxExtrapulmonarySLD(this.getRtxExtrapulmonarySLD() + table.getRtxExtrapulmonarySLD());
		this.setRtxAllSLD(this.getRtxAllSLD() + table.getRtxAllSLD());
		this.setAllDetected(this.getAllDetected() + table.getAllDetected());
		this.setAllEligible(this.getAllEligible() + table.getAllEligible());
		this.setAllCured(this.getAllCured() + table.getAllCured());
		this.setAllCompleted(this.getAllCompleted() + table.getAllCompleted());
		this.setAllDiedTB(this.getAllDiedTB() + table.getAllDiedTB());
		this.setAllDiedNotTB(this.getAllDiedNotTB() + table.getAllDiedNotTB());
		this.setAllFailed(this.getAllFailed() + table.getAllFailed());
		this.setAllDefaulted(this.getAllDefaulted() + table.getAllDefaulted());
		this.setAllTransferOut(this.getAllTransferOut() + table.getAllTransferOut());
		this.setAllCanceled(this.getAllCanceled() + table.getAllCanceled());
		this.setAllSLD(this.getAllSLD() + table.getAllSLD());

	}
	
}