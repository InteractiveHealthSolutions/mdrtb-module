package org.openmrs.module.mdrtb.reporting;

import java.util.HashMap;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;

public class TB07Table1Data {
	
	//NEW CASES
	private Integer newMalePulmonaryBC04;
	private Integer newFemalePulmonaryBC04;	
	private Integer newMalePulmonaryBC0514;
	private Integer newFemalePulmonaryBC0514;	
	private Integer newMalePulmonaryBC1517;
	private Integer newFemalePulmonaryBC1517;
	private Integer newMalePulmonaryBC1819;
	private Integer newFemalePulmonaryBC1819;
	private Integer newMalePulmonaryBC2024;
	private Integer newFemalePulmonaryBC2024;
	private Integer newMalePulmonaryBC2534;
	private Integer newFemalePulmonaryBC2534;
	private Integer newMalePulmonaryBC3544;
	private Integer newFemalePulmonaryBC3544;
	private Integer newMalePulmonaryBC4554;
	private Integer newFemalePulmonaryBC4554;
	private Integer newMalePulmonaryBC5564;
	private Integer newFemalePulmonaryBC5564;
	private Integer newMalePulmonaryBC65;
	private Integer newFemalePulmonaryBC65;
	private Integer newMalePulmonaryBC;
	private Integer newFemalePulmonaryBC;
	private Integer newPulmonaryBC;
	
	private Integer newMalePulmonaryBCHIV04;
	private Integer newFemalePulmonaryBCHIV04;	
	private Integer newMalePulmonaryBCHIV0514;
	private Integer newFemalePulmonaryBCHIV0514;	
	private Integer newMalePulmonaryBCHIV1517;
	private Integer newFemalePulmonaryBCHIV1517;
	private Integer newMalePulmonaryBCHIV1819;
	private Integer newFemalePulmonaryBCHIV1819;
	private Integer newMalePulmonaryBCHIV2024;
	private Integer newFemalePulmonaryBCHIV2024;
	private Integer newMalePulmonaryBCHIV2534;
	private Integer newFemalePulmonaryBCHIV2534;
	private Integer newMalePulmonaryBCHIV3544;
	private Integer newFemalePulmonaryBCHIV3544;
	private Integer newMalePulmonaryBCHIV4554;
	private Integer newFemalePulmonaryBCHIV4554;
	private Integer newMalePulmonaryBCHIV5564;
	private Integer newFemalePulmonaryBCHIV5564;
	private Integer newMalePulmonaryBCHIV65;
	private Integer newFemalePulmonaryBCHIV65;
	private Integer newMalePulmonaryBCHIV;
	private Integer newFemalePulmonaryBCHIV;
	private Integer newPulmonaryBCHIV;
	
	private Integer newMalePulmonaryCD04;
	private Integer newFemalePulmonaryCD04;	
	private Integer newMalePulmonaryCD0514;
	private Integer newFemalePulmonaryCD0514;	
	private Integer newMalePulmonaryCD1517;
	private Integer newFemalePulmonaryCD1517;
	private Integer newMalePulmonaryCD1819;
	private Integer newFemalePulmonaryCD1819;
	private Integer newMalePulmonaryCD2024;
	private Integer newFemalePulmonaryCD2024;
	private Integer newMalePulmonaryCD2534;
	private Integer newFemalePulmonaryCD2534;
	private Integer newMalePulmonaryCD3544;
	private Integer newFemalePulmonaryCD3544;
	private Integer newMalePulmonaryCD4554;
	private Integer newFemalePulmonaryCD4554;
	private Integer newMalePulmonaryCD5564;
	private Integer newFemalePulmonaryCD5564;
	private Integer newMalePulmonaryCD65;
	private Integer newFemalePulmonaryCD65;
	private Integer newMalePulmonaryCD;
	private Integer newFemalePulmonaryCD;
	private Integer newPulmonaryCD;
	
	private Integer newMalePulmonaryCDHIV04;
	private Integer newFemalePulmonaryCDHIV04;	
	private Integer newMalePulmonaryCDHIV0514;
	private Integer newFemalePulmonaryCDHIV0514;	
	private Integer newMalePulmonaryCDHIV1517;
	private Integer newFemalePulmonaryCDHIV1517;
	private Integer newMalePulmonaryCDHIV1819;
	private Integer newFemalePulmonaryCDHIV1819;
	private Integer newMalePulmonaryCDHIV2024;
	private Integer newFemalePulmonaryCDHIV2024;
	private Integer newMalePulmonaryCDHIV2534;
	private Integer newFemalePulmonaryCDHIV2534;
	private Integer newMalePulmonaryCDHIV3544;
	private Integer newFemalePulmonaryCDHIV3544;
	private Integer newMalePulmonaryCDHIV4554;
	private Integer newFemalePulmonaryCDHIV4554;
	private Integer newMalePulmonaryCDHIV5564;
	private Integer newFemalePulmonaryCDHIV5564;
	private Integer newMalePulmonaryCDHIV65;
	private Integer newFemalePulmonaryCDHIV65;
	private Integer newMalePulmonaryCDHIV;
	private Integer newFemalePulmonaryCDHIV;
	private Integer newPulmonaryCDHIV;
	
	private Integer newMaleExtrapulmonary04;
	private Integer newFemaleExtrapulmonary04;	
	private Integer newMaleExtrapulmonary0514;
	private Integer newFemaleExtrapulmonary0514;	
	private Integer newMaleExtrapulmonary1517;
	private Integer newFemaleExtrapulmonary1517;
	private Integer newMaleExtrapulmonary1819;
	private Integer newFemaleExtrapulmonary1819;
	private Integer newMaleExtrapulmonary2024;
	private Integer newFemaleExtrapulmonary2024;
	private Integer newMaleExtrapulmonary2534;
	private Integer newFemaleExtrapulmonary2534;
	private Integer newMaleExtrapulmonary3544;
	private Integer newFemaleExtrapulmonary3544;
	private Integer newMaleExtrapulmonary4554;
	private Integer newFemaleExtrapulmonary4554;
	private Integer newMaleExtrapulmonary5564;
	private Integer newFemaleExtrapulmonary5564;
	private Integer newMaleExtrapulmonary65;
	private Integer newFemaleExtrapulmonary65;
	private Integer newMaleExtrapulmonary;
	private Integer newFemaleExtrapulmonary;
	private Integer newExtrapulmonary;
	
	private Integer newMaleExtrapulmonaryHIV04;
	private Integer newFemaleExtrapulmonaryHIV04;	
	private Integer newMaleExtrapulmonaryHIV0514;
	private Integer newFemaleExtrapulmonaryHIV0514;	
	private Integer newMaleExtrapulmonaryHIV1517;
	private Integer newFemaleExtrapulmonaryHIV1517;
	private Integer newMaleExtrapulmonaryHIV1819;
	private Integer newFemaleExtrapulmonaryHIV1819;
	private Integer newMaleExtrapulmonaryHIV2024;
	private Integer newFemaleExtrapulmonaryHIV2024;
	private Integer newMaleExtrapulmonaryHIV2534;
	private Integer newFemaleExtrapulmonaryHIV2534;
	private Integer newMaleExtrapulmonaryHIV3544;
	private Integer newFemaleExtrapulmonaryHIV3544;
	private Integer newMaleExtrapulmonaryHIV4554;
	private Integer newFemaleExtrapulmonaryHIV4554;
	private Integer newMaleExtrapulmonaryHIV5564;
	private Integer newFemaleExtrapulmonaryHIV5564;
	private Integer newMaleExtrapulmonaryHIV65;
	private Integer newFemaleExtrapulmonaryHIV65;
	private Integer newMaleExtrapulmonaryHIV;
	private Integer newFemaleExtrapulmonaryHIV;
	private Integer newExtrapulmonaryHIV;
	
	private Integer newMale04;
	private Integer newFemale04;	
	private Integer newMale0514;
	private Integer newFemale0514;	
	private Integer newMale1517;
	private Integer newFemale1517;
	private Integer newMale1819;
	private Integer newFemale1819;
	private Integer newMale2024;
	private Integer newFemale2024;
	private Integer newMale2534;
	private Integer newFemale2534;
	private Integer newMale3544;
	private Integer newFemale3544;
	private Integer newMale4554;
	private Integer newFemale4554;
	private Integer newMale5564;
	private Integer newFemale5564;
	private Integer newMale65;
	private Integer newFemale65;
	private Integer newMale;
	private Integer newFemale;
	private Integer newAll;
	
	private Integer newMaleHIV04;
	private Integer newFemaleHIV04;	
	private Integer newMaleHIV0514;
	private Integer newFemaleHIV0514;	
	private Integer newMaleHIV1517;
	private Integer newFemaleHIV1517;
	private Integer newMaleHIV1819;
	private Integer newFemaleHIV1819;
	private Integer newMaleHIV2024;
	private Integer newFemaleHIV2024;
	private Integer newMaleHIV2534;
	private Integer newFemaleHIV2534;
	private Integer newMaleHIV3544;
	private Integer newFemaleHIV3544;
	private Integer newMaleHIV4554;
	private Integer newFemaleHIV4554;
	private Integer newMaleHIV5564;
	private Integer newFemaleHIV5564;
	private Integer newMaleHIV65;
	private Integer newFemaleHIV65;
	private Integer newMaleHIV;
	private Integer newFemaleHIV;
	private Integer newAllHIV;
	
	//RELAPSES
	private Integer relapseMalePulmonaryBC04;
	private Integer relapseFemalePulmonaryBC04;	
	private Integer relapseMalePulmonaryBC0514;
	private Integer relapseFemalePulmonaryBC0514;	
	private Integer relapseMalePulmonaryBC1517;
	private Integer relapseFemalePulmonaryBC1517;
	private Integer relapseMalePulmonaryBC1819;
	private Integer relapseFemalePulmonaryBC1819;
	private Integer relapseMalePulmonaryBC2024;
	private Integer relapseFemalePulmonaryBC2024;
	private Integer relapseMalePulmonaryBC2534;
	private Integer relapseFemalePulmonaryBC2534;
	private Integer relapseMalePulmonaryBC3544;
	private Integer relapseFemalePulmonaryBC3544;
	private Integer relapseMalePulmonaryBC4554;
	private Integer relapseFemalePulmonaryBC4554;
	private Integer relapseMalePulmonaryBC5564;
	private Integer relapseFemalePulmonaryBC5564;
	private Integer relapseMalePulmonaryBC65;
	private Integer relapseFemalePulmonaryBC65;
	private Integer relapseMalePulmonaryBC;
	private Integer relapseFemalePulmonaryBC;
	private Integer relapsePulmonaryBC;
	
	private Integer relapseMalePulmonaryBCHIV04;
	private Integer relapseFemalePulmonaryBCHIV04;	
	private Integer relapseMalePulmonaryBCHIV0514;
	private Integer relapseFemalePulmonaryBCHIV0514;	
	private Integer relapseMalePulmonaryBCHIV1517;
	private Integer relapseFemalePulmonaryBCHIV1517;
	private Integer relapseMalePulmonaryBCHIV1819;
	private Integer relapseFemalePulmonaryBCHIV1819;
	private Integer relapseMalePulmonaryBCHIV2024;
	private Integer relapseFemalePulmonaryBCHIV2024;
	private Integer relapseMalePulmonaryBCHIV2534;
	private Integer relapseFemalePulmonaryBCHIV2534;
	private Integer relapseMalePulmonaryBCHIV3544;
	private Integer relapseFemalePulmonaryBCHIV3544;
	private Integer relapseMalePulmonaryBCHIV4554;
	private Integer relapseFemalePulmonaryBCHIV4554;
	private Integer relapseMalePulmonaryBCHIV5564;
	private Integer relapseFemalePulmonaryBCHIV5564;
	private Integer relapseMalePulmonaryBCHIV65;
	private Integer relapseFemalePulmonaryBCHIV65;
	private Integer relapseMalePulmonaryBCHIV;
	private Integer relapseFemalePulmonaryBCHIV;
	private Integer relapsePulmonaryBCHIV;
	
	private Integer relapseMalePulmonaryCD04;
	private Integer relapseFemalePulmonaryCD04;	
	private Integer relapseMalePulmonaryCD0514;
	private Integer relapseFemalePulmonaryCD0514;	
	private Integer relapseMalePulmonaryCD1517;
	private Integer relapseFemalePulmonaryCD1517;
	private Integer relapseMalePulmonaryCD1819;
	private Integer relapseFemalePulmonaryCD1819;
	private Integer relapseMalePulmonaryCD2024;
	private Integer relapseFemalePulmonaryCD2024;
	private Integer relapseMalePulmonaryCD2534;
	private Integer relapseFemalePulmonaryCD2534;
	private Integer relapseMalePulmonaryCD3544;
	private Integer relapseFemalePulmonaryCD3544;
	private Integer relapseMalePulmonaryCD4554;
	private Integer relapseFemalePulmonaryCD4554;
	private Integer relapseMalePulmonaryCD5564;
	private Integer relapseFemalePulmonaryCD5564;
	private Integer relapseMalePulmonaryCD65;
	private Integer relapseFemalePulmonaryCD65;
	private Integer relapseMalePulmonaryCD;
	private Integer relapseFemalePulmonaryCD;
	private Integer relapsePulmonaryCD;
	
	private Integer relapseMalePulmonaryCDHIV04;
	private Integer relapseFemalePulmonaryCDHIV04;	
	private Integer relapseMalePulmonaryCDHIV0514;
	private Integer relapseFemalePulmonaryCDHIV0514;	
	private Integer relapseMalePulmonaryCDHIV1517;
	private Integer relapseFemalePulmonaryCDHIV1517;
	private Integer relapseMalePulmonaryCDHIV1819;
	private Integer relapseFemalePulmonaryCDHIV1819;
	private Integer relapseMalePulmonaryCDHIV2024;
	private Integer relapseFemalePulmonaryCDHIV2024;
	private Integer relapseMalePulmonaryCDHIV2534;
	private Integer relapseFemalePulmonaryCDHIV2534;
	private Integer relapseMalePulmonaryCDHIV3544;
	private Integer relapseFemalePulmonaryCDHIV3544;
	private Integer relapseMalePulmonaryCDHIV4554;
	private Integer relapseFemalePulmonaryCDHIV4554;
	private Integer relapseMalePulmonaryCDHIV5564;
	private Integer relapseFemalePulmonaryCDHIV5564;
	private Integer relapseMalePulmonaryCDHIV65;
	private Integer relapseFemalePulmonaryCDHIV65;
	private Integer relapseMalePulmonaryCDHIV;
	private Integer relapseFemalePulmonaryCDHIV;
	private Integer relapsePulmonaryCDHIV;
	
	private Integer relapseMaleExtrapulmonary04;
	private Integer relapseFemaleExtrapulmonary04;	
	private Integer relapseMaleExtrapulmonary0514;
	private Integer relapseFemaleExtrapulmonary0514;	
	private Integer relapseMaleExtrapulmonary1517;
	private Integer relapseFemaleExtrapulmonary1517;
	private Integer relapseMaleExtrapulmonary1819;
	private Integer relapseFemaleExtrapulmonary1819;
	private Integer relapseMaleExtrapulmonary2024;
	private Integer relapseFemaleExtrapulmonary2024;
	private Integer relapseMaleExtrapulmonary2534;
	private Integer relapseFemaleExtrapulmonary2534;
	private Integer relapseMaleExtrapulmonary3544;
	private Integer relapseFemaleExtrapulmonary3544;
	private Integer relapseMaleExtrapulmonary4554;
	private Integer relapseFemaleExtrapulmonary4554;
	private Integer relapseMaleExtrapulmonary5564;
	private Integer relapseFemaleExtrapulmonary5564;
	private Integer relapseMaleExtrapulmonary65;
	private Integer relapseFemaleExtrapulmonary65;
	private Integer relapseMaleExtrapulmonary;
	private Integer relapseFemaleExtrapulmonary;
	private Integer relapseExtrapulmonary;
	
	private Integer relapseMaleExtrapulmonaryHIV04;
	private Integer relapseFemaleExtrapulmonaryHIV04;	
	private Integer relapseMaleExtrapulmonaryHIV0514;
	private Integer relapseFemaleExtrapulmonaryHIV0514;	
	private Integer relapseMaleExtrapulmonaryHIV1517;
	private Integer relapseFemaleExtrapulmonaryHIV1517;
	private Integer relapseMaleExtrapulmonaryHIV1819;
	private Integer relapseFemaleExtrapulmonaryHIV1819;
	private Integer relapseMaleExtrapulmonaryHIV2024;
	private Integer relapseFemaleExtrapulmonaryHIV2024;
	private Integer relapseMaleExtrapulmonaryHIV2534;
	private Integer relapseFemaleExtrapulmonaryHIV2534;
	private Integer relapseMaleExtrapulmonaryHIV3544;
	private Integer relapseFemaleExtrapulmonaryHIV3544;
	private Integer relapseMaleExtrapulmonaryHIV4554;
	private Integer relapseFemaleExtrapulmonaryHIV4554;
	private Integer relapseMaleExtrapulmonaryHIV5564;
	private Integer relapseFemaleExtrapulmonaryHIV5564;
	private Integer relapseMaleExtrapulmonaryHIV65;
	private Integer relapseFemaleExtrapulmonaryHIV65;
	private Integer relapseMaleExtrapulmonaryHIV;
	private Integer relapseFemaleExtrapulmonaryHIV;
	private Integer relapseExtrapulmonaryHIV;
	
	private Integer relapseMale04;
	private Integer relapseFemale04;	
	private Integer relapseMale0514;
	private Integer relapseFemale0514;	
	private Integer relapseMale1517;
	private Integer relapseFemale1517;
	private Integer relapseMale1819;
	private Integer relapseFemale1819;
	private Integer relapseMale2024;
	private Integer relapseFemale2024;
	private Integer relapseMale2534;
	private Integer relapseFemale2534;
	private Integer relapseMale3544;
	private Integer relapseFemale3544;
	private Integer relapseMale4554;
	private Integer relapseFemale4554;
	private Integer relapseMale5564;
	private Integer relapseFemale5564;
	private Integer relapseMale65;
	private Integer relapseFemale65;
	private Integer relapseMale;
	private Integer relapseFemale;
	private Integer relapseAll;
	
	private Integer relapseMaleHIV04;
	private Integer relapseFemaleHIV04;	
	private Integer relapseMaleHIV0514;
	private Integer relapseFemaleHIV0514;	
	private Integer relapseMaleHIV1517;
	private Integer relapseFemaleHIV1517;
	private Integer relapseMaleHIV1819;
	private Integer relapseFemaleHIV1819;
	private Integer relapseMaleHIV2024;
	private Integer relapseFemaleHIV2024;
	private Integer relapseMaleHIV2534;
	private Integer relapseFemaleHIV2534;
	private Integer relapseMaleHIV3544;
	private Integer relapseFemaleHIV3544;
	private Integer relapseMaleHIV4554;
	private Integer relapseFemaleHIV4554;
	private Integer relapseMaleHIV5564;
	private Integer relapseFemaleHIV5564;
	private Integer relapseMaleHIV65;
	private Integer relapseFemaleHIV65;
	private Integer relapseMaleHIV;
	private Integer relapseFemaleHIV;
	private Integer relapseAllHIV;
	
	private Integer hivPositive;
	private Integer hivTested;
	private Integer artStarted;
	private Integer pctStarted;
	
	private Integer failureMalePulmonaryBC;
	private Integer failureFemalePulmonaryBC;
	private Integer failurePulmonaryBC;
	private Integer failureMalePulmonaryBCHIV;
	private Integer failureFemalePulmonaryBCHIV;
	private Integer failurePulmonaryBCHIV;
	private Integer failureMalePulmonaryCD;
	private Integer failureFemalePulmonaryCD;
	private Integer failurePulmonaryCD;
	private Integer failureMalePulmonaryCDHIV;
	private Integer failureFemalePulmonaryCDHIV;
	private Integer failurePulmonaryCDHIV;
	private Integer failureMaleExtrapulmonary;
	private Integer failureFemaleExtrapulmonary;
	private Integer failureExtrapulmonary;
	private Integer failureMaleExtrapulmonaryHIV;
	private Integer failureFemaleExtrapulmonaryHIV;
	private Integer failureExtrapulmonaryHIV;
	private Integer failureMale;
	private Integer failureFemale;
	private Integer failureAll;
	private Integer failureMaleHIV;
	private Integer failureFemaleHIV;
	private Integer failureAllHIV;
	
	private Integer defaultMalePulmonaryBC;
	private Integer defaultFemalePulmonaryBC;
	private Integer defaultPulmonaryBC;
	private Integer defaultMalePulmonaryBCHIV;
	private Integer defaultFemalePulmonaryBCHIV;
	private Integer defaultPulmonaryBCHIV;
	private Integer defaultMalePulmonaryCD;
	private Integer defaultFemalePulmonaryCD;
	private Integer defaultPulmonaryCD;
	private Integer defaultMalePulmonaryCDHIV;
	private Integer defaultFemalePulmonaryCDHIV;
	private Integer defaultPulmonaryCDHIV;
	private Integer defaultMaleExtrapulmonary;
	private Integer defaultFemaleExtrapulmonary;
	private Integer defaultExtrapulmonary;
	private Integer defaultMaleExtrapulmonaryHIV;
	private Integer defaultFemaleExtrapulmonaryHIV;
	private Integer defaultExtrapulmonaryHIV;
	private Integer defaultMale;
	private Integer defaultFemale;
	private Integer defaultAll;
	private Integer defaultMaleHIV;
	private Integer defaultFemaleHIV;
	private Integer defaultAllHIV;
	
	private Integer otherMalePulmonaryBC;
	private Integer otherFemalePulmonaryBC;
	private Integer otherPulmonaryBC;
	private Integer otherMalePulmonaryBCHIV;
	private Integer otherFemalePulmonaryBCHIV;
	private Integer otherPulmonaryBCHIV;
	private Integer otherMalePulmonaryCD;
	private Integer otherFemalePulmonaryCD;
	private Integer otherPulmonaryCD;
	private Integer otherMalePulmonaryCDHIV;
	private Integer otherFemalePulmonaryCDHIV;
	private Integer otherPulmonaryCDHIV;
	private Integer otherMaleExtrapulmonary;
	private Integer otherFemaleExtrapulmonary;
	private Integer otherExtrapulmonary;
	private Integer otherMaleExtrapulmonaryHIV;
	private Integer otherFemaleExtrapulmonaryHIV;
	private Integer otherExtrapulmonaryHIV;
	private Integer otherMale;
	private Integer otherFemale;
	private Integer otherAll;
	private Integer otherMaleHIV;
	private Integer otherFemaleHIV;
	private Integer otherAllHIV;
	
	private Integer retreatmentMalePulmonaryBC;
	private Integer retreatmentFemalePulmonaryBC;
	private Integer retreatmentPulmonaryBC;
	private Integer retreatmentMalePulmonaryBCHIV;
	private Integer retreatmentFemalePulmonaryBCHIV;
	private Integer retreatmentPulmonaryBCHIV;
	private Integer retreatmentMalePulmonaryCD;
	private Integer retreatmentFemalePulmonaryCD;
	private Integer retreatmentPulmonaryCD;
	private Integer retreatmentMalePulmonaryCDHIV;
	private Integer retreatmentFemalePulmonaryCDHIV;
	private Integer retreatmentPulmonaryCDHIV;
	private Integer retreatmentMaleExtrapulmonary;
	private Integer retreatmentFemaleExtrapulmonary;
	private Integer retreatmentExtrapulmonary;
	private Integer retreatmentMaleExtrapulmonaryHIV;
	private Integer retreatmentFemaleExtrapulmonaryHIV;
	private Integer retreatmentExtrapulmonaryHIV;
	private Integer retreatmentMale;
	private Integer retreatmentFemale;
	private Integer retreatmentAll;
	private Integer retreatmentMaleHIV;
	private Integer retreatmentFemaleHIV;
	private Integer retreatmentAllHIV;
	
	private Integer totalMalePulmonaryBC;
	private Integer totalFemalePulmonaryBC;
	private Integer totalPulmonaryBC;
	private Integer totalMalePulmonaryBCHIV;
	private Integer totalFemalePulmonaryBCHIV;
	private Integer totalPulmonaryBCHIV;
	private Integer totalMalePulmonaryCD;
	private Integer totalFemalePulmonaryCD;
	private Integer totalPulmonaryCD;
	private Integer totalMalePulmonaryCDHIV;
	private Integer totalFemalePulmonaryCDHIV;
	private Integer totalPulmonaryCDHIV;
	private Integer totalMaleExtrapulmonary;
	private Integer totalFemaleExtrapulmonary;
	private Integer totalExtrapulmonary;
	private Integer totalMaleExtrapulmonaryHIV;
	private Integer totalFemaleExtrapulmonaryHIV;
	private Integer totalExtrapulmonaryHIV;
	private Integer totalMale;
	private Integer totalFemale;
	private Integer totalAll;
	private Integer totalMaleHIV;
	private Integer totalFemaleHIV;
	private Integer totalAllHIV;
	
	
	private Integer womenOfChildBearingAge;
	private Integer pregnant;
	private Integer contacts;
	private Integer migrants;
	private Integer phcWorkers;
	private Integer tbServicesWorkers;
	private Integer died;
	private Integer diedChildren;
	private Integer diedWomenOfChildBearingAge;
	
	public TB07Table1Data() {
		newMalePulmonaryBC04 = 0;
		newFemalePulmonaryBC04 = 0;
		newMalePulmonaryBC0514 = 0;
		newFemalePulmonaryBC0514 = 0;
		newMalePulmonaryBC1517 = 0;
		newFemalePulmonaryBC1517 = 0;
		newMalePulmonaryBC1819 = 0;
		newFemalePulmonaryBC1819 = 0;
		newMalePulmonaryBC2024 = 0;
		newFemalePulmonaryBC2024 = 0;
		newMalePulmonaryBC2534 = 0;
		newFemalePulmonaryBC2534 = 0;
		newMalePulmonaryBC3544 = 0;
		newFemalePulmonaryBC3544 = 0;
		newMalePulmonaryBC4554 = 0;
		newFemalePulmonaryBC4554 = 0;
		newMalePulmonaryBC5564 = 0;
		newFemalePulmonaryBC5564 = 0;
		newMalePulmonaryBC65 = 0;
		newFemalePulmonaryBC65 = 0;
		newMalePulmonaryBC = 0;
		newFemalePulmonaryBC = 0;
		newPulmonaryBC = 0;

		newMalePulmonaryBCHIV04 = 0;
		newFemalePulmonaryBCHIV04 = 0;
		newMalePulmonaryBCHIV0514 = 0;
		newFemalePulmonaryBCHIV0514 = 0;
		newMalePulmonaryBCHIV1517 = 0;
		newFemalePulmonaryBCHIV1517 = 0;
		newMalePulmonaryBCHIV1819 = 0;
		newFemalePulmonaryBCHIV1819 = 0;
		newMalePulmonaryBCHIV2024 = 0;
		newFemalePulmonaryBCHIV2024 = 0;
		newMalePulmonaryBCHIV2534 = 0;
		newFemalePulmonaryBCHIV2534 = 0;
		newMalePulmonaryBCHIV3544 = 0;
		newFemalePulmonaryBCHIV3544 = 0;
		newMalePulmonaryBCHIV4554 = 0;
		newFemalePulmonaryBCHIV4554 = 0;
		newMalePulmonaryBCHIV5564 = 0;
		newFemalePulmonaryBCHIV5564 = 0;
		newMalePulmonaryBCHIV65 = 0;
		newFemalePulmonaryBCHIV65 = 0;
		newMalePulmonaryBCHIV = 0;
		newFemalePulmonaryBCHIV = 0;
		newPulmonaryBCHIV = 0;

		newMalePulmonaryCD04 = 0;
		newFemalePulmonaryCD04 = 0;
		newMalePulmonaryCD0514 = 0;
		newFemalePulmonaryCD0514 = 0;
		newMalePulmonaryCD1517 = 0;
		newFemalePulmonaryCD1517 = 0;
		newMalePulmonaryCD1819 = 0;
		newFemalePulmonaryCD1819 = 0;
		newMalePulmonaryCD2024 = 0;
		newFemalePulmonaryCD2024 = 0;
		newMalePulmonaryCD2534 = 0;
		newFemalePulmonaryCD2534 = 0;
		newMalePulmonaryCD3544 = 0;
		newFemalePulmonaryCD3544 = 0;
		newMalePulmonaryCD4554 = 0;
		newFemalePulmonaryCD4554 = 0;
		newMalePulmonaryCD5564 = 0;
		newFemalePulmonaryCD5564 = 0;
		newMalePulmonaryCD65 = 0;
		newFemalePulmonaryCD65 = 0;
		newMalePulmonaryCD = 0;
		newFemalePulmonaryCD = 0;
		newPulmonaryCD = 0;

		newMalePulmonaryCDHIV04 = 0;
		newFemalePulmonaryCDHIV04 = 0;
		newMalePulmonaryCDHIV0514 = 0;
		newFemalePulmonaryCDHIV0514 = 0;
		newMalePulmonaryCDHIV1517 = 0;
		newFemalePulmonaryCDHIV1517 = 0;
		newMalePulmonaryCDHIV1819 = 0;
		newFemalePulmonaryCDHIV1819 = 0;
		newMalePulmonaryCDHIV2024 = 0;
		newFemalePulmonaryCDHIV2024 = 0;
		newMalePulmonaryCDHIV2534 = 0;
		newFemalePulmonaryCDHIV2534 = 0;
		newMalePulmonaryCDHIV3544 = 0;
		newFemalePulmonaryCDHIV3544 = 0;
		newMalePulmonaryCDHIV4554 = 0;
		newFemalePulmonaryCDHIV4554 = 0;
		newMalePulmonaryCDHIV5564 = 0;
		newFemalePulmonaryCDHIV5564 = 0;
		newMalePulmonaryCDHIV65 = 0;
		newFemalePulmonaryCDHIV65 = 0;
		newMalePulmonaryCDHIV = 0;
		newFemalePulmonaryCDHIV = 0;
		newPulmonaryCDHIV = 0;

		newMaleExtrapulmonary04 = 0;
		newFemaleExtrapulmonary04 = 0;
		newMaleExtrapulmonary0514 = 0;
		newFemaleExtrapulmonary0514 = 0;
		newMaleExtrapulmonary1517 = 0;
		newFemaleExtrapulmonary1517 = 0;
		newMaleExtrapulmonary1819 = 0;
		newFemaleExtrapulmonary1819 = 0;
		newMaleExtrapulmonary2024 = 0;
		newFemaleExtrapulmonary2024 = 0;
		newMaleExtrapulmonary2534 = 0;
		newFemaleExtrapulmonary2534 = 0;
		newMaleExtrapulmonary3544 = 0;
		newFemaleExtrapulmonary3544 = 0;
		newMaleExtrapulmonary4554 = 0;
		newFemaleExtrapulmonary4554 = 0;
		newMaleExtrapulmonary5564 = 0;
		newFemaleExtrapulmonary5564 = 0;
		newMaleExtrapulmonary65 = 0;
		newFemaleExtrapulmonary65 = 0;
		newMaleExtrapulmonary = 0;
		newFemaleExtrapulmonary = 0;
		newExtrapulmonary = 0;

		newMaleExtrapulmonaryHIV04 = 0;
		newFemaleExtrapulmonaryHIV04 = 0;
		newMaleExtrapulmonaryHIV0514 = 0;
		newFemaleExtrapulmonaryHIV0514 = 0;
		newMaleExtrapulmonaryHIV1517 = 0;
		newFemaleExtrapulmonaryHIV1517 = 0;
		newMaleExtrapulmonaryHIV1819 = 0;
		newFemaleExtrapulmonaryHIV1819 = 0;
		newMaleExtrapulmonaryHIV2024 = 0;
		newFemaleExtrapulmonaryHIV2024 = 0;
		newMaleExtrapulmonaryHIV2534 = 0;
		newFemaleExtrapulmonaryHIV2534 = 0;
		newMaleExtrapulmonaryHIV3544 = 0;
		newFemaleExtrapulmonaryHIV3544 = 0;
		newMaleExtrapulmonaryHIV4554 = 0;
		newFemaleExtrapulmonaryHIV4554 = 0;
		newMaleExtrapulmonaryHIV5564 = 0;
		newFemaleExtrapulmonaryHIV5564 = 0;
		newMaleExtrapulmonaryHIV65 = 0;
		newFemaleExtrapulmonaryHIV65 = 0;
		newMaleExtrapulmonaryHIV = 0;
		newFemaleExtrapulmonaryHIV = 0;
		newExtrapulmonaryHIV = 0;

		newMale04 = 0;
		newFemale04 = 0;
		newMale0514 = 0;
		newFemale0514 = 0;
		newMale1517 = 0;
		newFemale1517 = 0;
		newMale1819 = 0;
		newFemale1819 = 0;
		newMale2024 = 0;
		newFemale2024 = 0;
		newMale2534 = 0;
		newFemale2534 = 0;
		newMale3544 = 0;
		newFemale3544 = 0;
		newMale4554 = 0;
		newFemale4554 = 0;
		newMale5564 = 0;
		newFemale5564 = 0;
		newMale65 = 0;
		newFemale65 = 0;
		newMale = 0;
		newFemale = 0;
		newAll = 0;

		newMaleHIV04 = 0;
		newFemaleHIV04 = 0;
		newMaleHIV0514 = 0;
		newFemaleHIV0514 = 0;
		newMaleHIV1517 = 0;
		newFemaleHIV1517 = 0;
		newMaleHIV1819 = 0;
		newFemaleHIV1819 = 0;
		newMaleHIV2024 = 0;
		newFemaleHIV2024 = 0;
		newMaleHIV2534 = 0;
		newFemaleHIV2534 = 0;
		newMaleHIV3544 = 0;
		newFemaleHIV3544 = 0;
		newMaleHIV4554 = 0;
		newFemaleHIV4554 = 0;
		newMaleHIV5564 = 0;
		newFemaleHIV5564 = 0;
		newMaleHIV65 = 0;
		newFemaleHIV65 = 0;
		newMaleHIV = 0;
		newFemaleHIV = 0;
		newAllHIV = 0;

		// RELAPSES
		relapseMalePulmonaryBC04 = 0;
		relapseFemalePulmonaryBC04 = 0;
		relapseMalePulmonaryBC0514 = 0;
		relapseFemalePulmonaryBC0514 = 0;
		relapseMalePulmonaryBC1517 = 0;
		relapseFemalePulmonaryBC1517 = 0;
		relapseMalePulmonaryBC1819 = 0;
		relapseFemalePulmonaryBC1819 = 0;
		relapseMalePulmonaryBC2024 = 0;
		relapseFemalePulmonaryBC2024 = 0;
		relapseMalePulmonaryBC2534 = 0;
		relapseFemalePulmonaryBC2534 = 0;
		relapseMalePulmonaryBC3544 = 0;
		relapseFemalePulmonaryBC3544 = 0;
		relapseMalePulmonaryBC4554 = 0;
		relapseFemalePulmonaryBC4554 = 0;
		relapseMalePulmonaryBC5564 = 0;
		relapseFemalePulmonaryBC5564 = 0;
		relapseMalePulmonaryBC65 = 0;
		relapseFemalePulmonaryBC65 = 0;
		relapseMalePulmonaryBC = 0;
		relapseFemalePulmonaryBC = 0;
		relapsePulmonaryBC = 0;

		relapseMalePulmonaryBCHIV04 = 0;
		relapseFemalePulmonaryBCHIV04 = 0;
		relapseMalePulmonaryBCHIV0514 = 0;
		relapseFemalePulmonaryBCHIV0514 = 0;
		relapseMalePulmonaryBCHIV1517 = 0;
		relapseFemalePulmonaryBCHIV1517 = 0;
		relapseMalePulmonaryBCHIV1819 = 0;
		relapseFemalePulmonaryBCHIV1819 = 0;
		relapseMalePulmonaryBCHIV2024 = 0;
		relapseFemalePulmonaryBCHIV2024 = 0;
		relapseMalePulmonaryBCHIV2534 = 0;
		relapseFemalePulmonaryBCHIV2534 = 0;
		relapseMalePulmonaryBCHIV3544 = 0;
		relapseFemalePulmonaryBCHIV3544 = 0;
		relapseMalePulmonaryBCHIV4554 = 0;
		relapseFemalePulmonaryBCHIV4554 = 0;
		relapseMalePulmonaryBCHIV5564 = 0;
		relapseFemalePulmonaryBCHIV5564 = 0;
		relapseMalePulmonaryBCHIV65 = 0;
		relapseFemalePulmonaryBCHIV65 = 0;
		relapseMalePulmonaryBCHIV = 0;
		relapseFemalePulmonaryBCHIV = 0;
		relapsePulmonaryBCHIV = 0;

		relapseMalePulmonaryCD04 = 0;
		relapseFemalePulmonaryCD04 = 0;
		relapseMalePulmonaryCD0514 = 0;
		relapseFemalePulmonaryCD0514 = 0;
		relapseMalePulmonaryCD1517 = 0;
		relapseFemalePulmonaryCD1517 = 0;
		relapseMalePulmonaryCD1819 = 0;
		relapseFemalePulmonaryCD1819 = 0;
		relapseMalePulmonaryCD2024 = 0;
		relapseFemalePulmonaryCD2024 = 0;
		relapseMalePulmonaryCD2534 = 0;
		relapseFemalePulmonaryCD2534 = 0;
		relapseMalePulmonaryCD3544 = 0;
		relapseFemalePulmonaryCD3544 = 0;
		relapseMalePulmonaryCD4554 = 0;
		relapseFemalePulmonaryCD4554 = 0;
		relapseMalePulmonaryCD5564 = 0;
		relapseFemalePulmonaryCD5564 = 0;
		relapseMalePulmonaryCD65 = 0;
		relapseFemalePulmonaryCD65 = 0;
		relapseMalePulmonaryCD = 0;
		relapseFemalePulmonaryCD = 0;
		relapsePulmonaryCD = 0;

		relapseMalePulmonaryCDHIV04 = 0;
		relapseFemalePulmonaryCDHIV04 = 0;
		relapseMalePulmonaryCDHIV0514 = 0;
		relapseFemalePulmonaryCDHIV0514 = 0;
		relapseMalePulmonaryCDHIV1517 = 0;
		relapseFemalePulmonaryCDHIV1517 = 0;
		relapseMalePulmonaryCDHIV1819 = 0;
		relapseFemalePulmonaryCDHIV1819 = 0;
		relapseMalePulmonaryCDHIV2024 = 0;
		relapseFemalePulmonaryCDHIV2024 = 0;
		relapseMalePulmonaryCDHIV2534 = 0;
		relapseFemalePulmonaryCDHIV2534 = 0;
		relapseMalePulmonaryCDHIV3544 = 0;
		relapseFemalePulmonaryCDHIV3544 = 0;
		relapseMalePulmonaryCDHIV4554 = 0;
		relapseFemalePulmonaryCDHIV4554 = 0;
		relapseMalePulmonaryCDHIV5564 = 0;
		relapseFemalePulmonaryCDHIV5564 = 0;
		relapseMalePulmonaryCDHIV65 = 0;
		relapseFemalePulmonaryCDHIV65 = 0;
		relapseMalePulmonaryCDHIV = 0;
		relapseFemalePulmonaryCDHIV = 0;
		relapsePulmonaryCDHIV = 0;

		relapseMaleExtrapulmonary04 = 0;
		relapseFemaleExtrapulmonary04 = 0;
		relapseMaleExtrapulmonary0514 = 0;
		relapseFemaleExtrapulmonary0514 = 0;
		relapseMaleExtrapulmonary1517 = 0;
		relapseFemaleExtrapulmonary1517 = 0;
		relapseMaleExtrapulmonary1819 = 0;
		relapseFemaleExtrapulmonary1819 = 0;
		relapseMaleExtrapulmonary2024 = 0;
		relapseFemaleExtrapulmonary2024 = 0;
		relapseMaleExtrapulmonary2534 = 0;
		relapseFemaleExtrapulmonary2534 = 0;
		relapseMaleExtrapulmonary3544 = 0;
		relapseFemaleExtrapulmonary3544 = 0;
		relapseMaleExtrapulmonary4554 = 0;
		relapseFemaleExtrapulmonary4554 = 0;
		relapseMaleExtrapulmonary5564 = 0;
		relapseFemaleExtrapulmonary5564 = 0;
		relapseMaleExtrapulmonary65 = 0;
		relapseFemaleExtrapulmonary65 = 0;
		relapseMaleExtrapulmonary = 0;
		relapseFemaleExtrapulmonary = 0;
		relapseExtrapulmonary = 0;

		relapseMaleExtrapulmonaryHIV04 = 0;
		relapseFemaleExtrapulmonaryHIV04 = 0;
		relapseMaleExtrapulmonaryHIV0514 = 0;
		relapseFemaleExtrapulmonaryHIV0514 = 0;
		relapseMaleExtrapulmonaryHIV1517 = 0;
		relapseFemaleExtrapulmonaryHIV1517 = 0;
		relapseMaleExtrapulmonaryHIV1819 = 0;
		relapseFemaleExtrapulmonaryHIV1819 = 0;
		relapseMaleExtrapulmonaryHIV2024 = 0;
		relapseFemaleExtrapulmonaryHIV2024 = 0;
		relapseMaleExtrapulmonaryHIV2534 = 0;
		relapseFemaleExtrapulmonaryHIV2534 = 0;
		relapseMaleExtrapulmonaryHIV3544 = 0;
		relapseFemaleExtrapulmonaryHIV3544 = 0;
		relapseMaleExtrapulmonaryHIV4554 = 0;
		relapseFemaleExtrapulmonaryHIV4554 = 0;
		relapseMaleExtrapulmonaryHIV5564 = 0;
		relapseFemaleExtrapulmonaryHIV5564 = 0;
		relapseMaleExtrapulmonaryHIV65 = 0;
		relapseFemaleExtrapulmonaryHIV65 = 0;
		relapseMaleExtrapulmonaryHIV = 0;
		relapseFemaleExtrapulmonaryHIV = 0;
		relapseExtrapulmonaryHIV = 0;

		relapseMale04 = 0;
		relapseFemale04 = 0;
		relapseMale0514 = 0;
		relapseFemale0514 = 0;
		relapseMale1517 = 0;
		relapseFemale1517 = 0;
		relapseMale1819 = 0;
		relapseFemale1819 = 0;
		relapseMale2024 = 0;
		relapseFemale2024 = 0;
		relapseMale2534 = 0;
		relapseFemale2534 = 0;
		relapseMale3544 = 0;
		relapseFemale3544 = 0;
		relapseMale4554 = 0;
		relapseFemale4554 = 0;
		relapseMale5564 = 0;
		relapseFemale5564 = 0;
		relapseMale65 = 0;
		relapseFemale65 = 0;
		relapseMale = 0;
		relapseFemale = 0;
		relapseAll = 0;

		relapseMaleHIV04 = 0;
		relapseFemaleHIV04 = 0;
		relapseMaleHIV0514 = 0;
		relapseFemaleHIV0514 = 0;
		relapseMaleHIV1517 = 0;
		relapseFemaleHIV1517 = 0;
		relapseMaleHIV1819 = 0;
		relapseFemaleHIV1819 = 0;
		relapseMaleHIV2024 = 0;
		relapseFemaleHIV2024 = 0;
		relapseMaleHIV2534 = 0;
		relapseFemaleHIV2534 = 0;
		relapseMaleHIV3544 = 0;
		relapseFemaleHIV3544 = 0;
		relapseMaleHIV4554 = 0;
		relapseFemaleHIV4554 = 0;
		relapseMaleHIV5564 = 0;
		relapseFemaleHIV5564 = 0;
		relapseMaleHIV65 = 0;
		relapseFemaleHIV65 = 0;
		relapseMaleHIV = 0;
		relapseFemaleHIV = 0;
		relapseAllHIV = 0;

		//HIV
		hivPositive = 0;
		hivTested = 0;
		artStarted = 0;
		pctStarted = 0;

		//FAILURE
		failureMalePulmonaryBC = 0;
		failureFemalePulmonaryBC = 0;
		failurePulmonaryBC = 0;
		failureMalePulmonaryBCHIV = 0;
		failureFemalePulmonaryBCHIV = 0;
		failurePulmonaryBCHIV = 0;
		failureMalePulmonaryCD = 0;
		failureFemalePulmonaryCD = 0;
		failurePulmonaryCD = 0;
		failureMalePulmonaryCDHIV = 0;
		failureFemalePulmonaryCDHIV = 0;
		failurePulmonaryCDHIV = 0;
		failureMaleExtrapulmonary = 0;
		failureFemaleExtrapulmonary = 0;
		failureExtrapulmonary = 0;
		failureMaleExtrapulmonaryHIV = 0;
		failureFemaleExtrapulmonaryHIV = 0;
		failureExtrapulmonaryHIV = 0;
		failureMale = 0;
		failureFemale = 0;
		failureAll = 0;
		failureMaleHIV = 0;
		failureFemaleHIV = 0;
		failureAllHIV = 0;

		//DEFAULT
		defaultMalePulmonaryBC = 0;
		defaultFemalePulmonaryBC = 0;
		defaultPulmonaryBC = 0;
		defaultMalePulmonaryBCHIV = 0;
		defaultFemalePulmonaryBCHIV = 0;
		defaultPulmonaryBCHIV = 0;
		defaultMalePulmonaryCD = 0;
		defaultFemalePulmonaryCD = 0;
		defaultPulmonaryCD = 0;
		defaultMalePulmonaryCDHIV = 0;
		defaultFemalePulmonaryCDHIV = 0;
		defaultPulmonaryCDHIV = 0;
		defaultMaleExtrapulmonary = 0;
		defaultFemaleExtrapulmonary = 0;
		defaultExtrapulmonary = 0;
		defaultMaleExtrapulmonaryHIV = 0;
		defaultFemaleExtrapulmonaryHIV = 0;
		defaultExtrapulmonaryHIV = 0;
		defaultMale = 0;
		defaultFemale = 0;
		defaultAll = 0;
		defaultMaleHIV = 0;
		defaultFemaleHIV = 0;
		defaultAllHIV = 0;

		//OTHER
		otherMalePulmonaryBC = 0;
		otherFemalePulmonaryBC = 0;
		otherPulmonaryBC = 0;
		otherMalePulmonaryBCHIV = 0;
		otherFemalePulmonaryBCHIV = 0;
		otherPulmonaryBCHIV = 0;
		otherMalePulmonaryCD = 0;
		otherFemalePulmonaryCD = 0;
		otherPulmonaryCD = 0;
		otherMalePulmonaryCDHIV = 0;
		otherFemalePulmonaryCDHIV = 0;
		otherPulmonaryCDHIV = 0;
		otherMaleExtrapulmonary = 0;
		otherFemaleExtrapulmonary = 0;
		otherExtrapulmonary = 0;
		otherMaleExtrapulmonaryHIV = 0;
		otherFemaleExtrapulmonaryHIV = 0;
		otherExtrapulmonaryHIV = 0;
		otherMale = 0;
		otherFemale = 0;
		otherAll = 0;
		otherMaleHIV = 0;
		otherFemaleHIV = 0;
		otherAllHIV = 0;

		//ALLRTX
		retreatmentMalePulmonaryBC = 0;
		retreatmentFemalePulmonaryBC = 0;
		retreatmentPulmonaryBC = 0;
		retreatmentMalePulmonaryBCHIV = 0;
		retreatmentFemalePulmonaryBCHIV = 0;
		retreatmentPulmonaryBCHIV = 0;
		retreatmentMalePulmonaryCD = 0;
		retreatmentFemalePulmonaryCD = 0;
		retreatmentPulmonaryCD = 0;
		retreatmentMalePulmonaryCDHIV = 0;
		retreatmentFemalePulmonaryCDHIV = 0;
		retreatmentPulmonaryCDHIV = 0;
		retreatmentMaleExtrapulmonary = 0;
		retreatmentFemaleExtrapulmonary = 0;
		retreatmentExtrapulmonary = 0;
		retreatmentMaleExtrapulmonaryHIV = 0;
		retreatmentFemaleExtrapulmonaryHIV = 0;
		retreatmentExtrapulmonaryHIV = 0;
		retreatmentMale = 0;
		retreatmentFemale = 0;
		retreatmentAll = 0;
		retreatmentMaleHIV = 0;
		retreatmentFemaleHIV = 0;
		retreatmentAllHIV = 0;

		//GRAND TOTAL
		totalMalePulmonaryBC = 0;
		totalFemalePulmonaryBC = 0;
		totalPulmonaryBC = 0;
		totalMalePulmonaryBCHIV = 0;
		totalFemalePulmonaryBCHIV = 0;
		totalPulmonaryBCHIV = 0;
		totalMalePulmonaryCD = 0;
		totalFemalePulmonaryCD = 0;
		totalPulmonaryCD = 0;
		totalMalePulmonaryCDHIV = 0;
		totalFemalePulmonaryCDHIV = 0;
		totalPulmonaryCDHIV = 0;
		totalMaleExtrapulmonary = 0;
		totalFemaleExtrapulmonary = 0;
		totalExtrapulmonary = 0;
		totalMaleExtrapulmonaryHIV = 0;
		totalFemaleExtrapulmonaryHIV = 0;
		totalExtrapulmonaryHIV = 0;
		totalMale = 0;
		totalFemale = 0;
		totalAll = 0;
		totalMaleHIV = 0;
		totalFemaleHIV = 0;
		totalAllHIV = 0;
		
		womenOfChildBearingAge = 0;
		pregnant = 0;
		contacts = 0;
		migrants = 0;
		phcWorkers = 0;
		tbServicesWorkers = 0;
		died = 0;
		diedChildren = 0;
		diedWomenOfChildBearingAge = 0;

	}

	public Integer getNewMalePulmonaryBC04() {
		return newMalePulmonaryBC04;
	}
	public void setNewMalePulmonaryBC04(Integer newMalePulmonaryBC04) {
		this.newMalePulmonaryBC04 = newMalePulmonaryBC04;
	}
	public Integer getNewFemalePulmonaryBC04() {
		return newFemalePulmonaryBC04;
	}
	public void setNewFemalePulmonaryBC04(Integer newFemalePulmonaryBC04) {
		this.newFemalePulmonaryBC04 = newFemalePulmonaryBC04;
	}
	public Integer getNewMalePulmonaryBC0514() {
		return newMalePulmonaryBC0514;
	}
	public void setNewMalePulmonaryBC0514(Integer newMalePulmonaryBC0514) {
		this.newMalePulmonaryBC0514 = newMalePulmonaryBC0514;
	}
	public Integer getNewFemalePulmonaryBC0514() {
		return newFemalePulmonaryBC0514;
	}
	public void setNewFemalePulmonaryBC0514(Integer newFemalePulmonaryBC0514) {
		this.newFemalePulmonaryBC0514 = newFemalePulmonaryBC0514;
	}
	public Integer getNewMalePulmonaryBC1517() {
		return newMalePulmonaryBC1517;
	}
	public void setNewMalePulmonaryBC1517(Integer newMalePulmonaryBC1517) {
		this.newMalePulmonaryBC1517 = newMalePulmonaryBC1517;
	}
	public Integer getNewFemalePulmonaryBC1517() {
		return newFemalePulmonaryBC1517;
	}
	public void setNewFemalePulmonaryBC1517(Integer newFemalePulmonaryBC1517) {
		this.newFemalePulmonaryBC1517 = newFemalePulmonaryBC1517;
	}
	public Integer getNewMalePulmonaryBC1819() {
		return newMalePulmonaryBC1819;
	}
	public void setNewMalePulmonaryBC1819(Integer newMalePulmonaryBC1819) {
		this.newMalePulmonaryBC1819 = newMalePulmonaryBC1819;
	}
	public Integer getNewFemalePulmonaryBC1819() {
		return newFemalePulmonaryBC1819;
	}
	public void setNewFemalePulmonaryBC1819(Integer newFemalePulmonaryBC1819) {
		this.newFemalePulmonaryBC1819 = newFemalePulmonaryBC1819;
	}
	public Integer getNewMalePulmonaryBC2024() {
		return newMalePulmonaryBC2024;
	}
	public void setNewMalePulmonaryBC2024(Integer newMalePulmonaryBC2024) {
		this.newMalePulmonaryBC2024 = newMalePulmonaryBC2024;
	}
	public Integer getNewFemalePulmonaryBC2024() {
		return newFemalePulmonaryBC2024;
	}
	public void setNewFemalePulmonaryBC2024(Integer newFemalePulmonaryBC2024) {
		this.newFemalePulmonaryBC2024 = newFemalePulmonaryBC2024;
	}
	public Integer getNewMalePulmonaryBC2534() {
		return newMalePulmonaryBC2534;
	}
	public void setNewMalePulmonaryBC2534(Integer newMalePulmonaryBC2534) {
		this.newMalePulmonaryBC2534 = newMalePulmonaryBC2534;
	}
	public Integer getNewFemalePulmonaryBC2534() {
		return newFemalePulmonaryBC2534;
	}
	public void setNewFemalePulmonaryBC2534(Integer newFemalePulmonaryBC2534) {
		this.newFemalePulmonaryBC2534 = newFemalePulmonaryBC2534;
	}
	public Integer getNewMalePulmonaryBC3544() {
		return newMalePulmonaryBC3544;
	}
	public void setNewMalePulmonaryBC3544(Integer newMalePulmonaryBC3544) {
		this.newMalePulmonaryBC3544 = newMalePulmonaryBC3544;
	}
	public Integer getNewFemalePulmonaryBC3544() {
		return newFemalePulmonaryBC3544;
	}
	public void setNewFemalePulmonaryBC3544(Integer newFemalePulmonaryBC3544) {
		this.newFemalePulmonaryBC3544 = newFemalePulmonaryBC3544;
	}
	public Integer getNewMalePulmonaryBC4554() {
		return newMalePulmonaryBC4554;
	}
	public void setNewMalePulmonaryBC4554(Integer newMalePulmonaryBC4554) {
		this.newMalePulmonaryBC4554 = newMalePulmonaryBC4554;
	}
	public Integer getNewFemalePulmonaryBC4554() {
		return newFemalePulmonaryBC4554;
	}
	public void setNewFemalePulmonaryBC4554(Integer newFemalePulmonaryBC4554) {
		this.newFemalePulmonaryBC4554 = newFemalePulmonaryBC4554;
	}
	public Integer getNewMalePulmonaryBC5564() {
		return newMalePulmonaryBC5564;
	}
	public void setNewMalePulmonaryBC5564(Integer newMalePulmonaryBC5564) {
		this.newMalePulmonaryBC5564 = newMalePulmonaryBC5564;
	}
	public Integer getNewFemalePulmonaryBC5564() {
		return newFemalePulmonaryBC5564;
	}
	public void setNewFemalePulmonaryBC5564(Integer newFemalePulmonaryBC5564) {
		this.newFemalePulmonaryBC5564 = newFemalePulmonaryBC5564;
	}
	public Integer getNewMalePulmonaryBC65() {
		return newMalePulmonaryBC65;
	}
	public void setNewMalePulmonaryBC65(Integer newMalePulmonaryBC65) {
		this.newMalePulmonaryBC65 = newMalePulmonaryBC65;
	}
	public Integer getNewFemalePulmonaryBC65() {
		return newFemalePulmonaryBC65;
	}
	public void setNewFemalePulmonaryBC65(Integer newFemalePulmonaryBC65) {
		this.newFemalePulmonaryBC65 = newFemalePulmonaryBC65;
	}
	public Integer getNewMalePulmonaryBC() {
		return newMalePulmonaryBC;
	}
	public void setNewMalePulmonaryBC(Integer newMalePulmonaryBC) {
		this.newMalePulmonaryBC = newMalePulmonaryBC;
	}
	public Integer getNewFemalePulmonaryBC() {
		return newFemalePulmonaryBC;
	}
	public void setNewFemalePulmonaryBC(Integer newFemalePulmonaryBC) {
		this.newFemalePulmonaryBC = newFemalePulmonaryBC;
	}
	public Integer getNewPulmonaryBC() {
		return newPulmonaryBC;
	}
	public void setNewPulmonaryBC(Integer newPulmonaryBC) {
		this.newPulmonaryBC = newPulmonaryBC;
	}
	public Integer getNewMalePulmonaryBCHIV04() {
		return newMalePulmonaryBCHIV04;
	}
	public void setNewMalePulmonaryBCHIV04(Integer newMalePulmonaryBCHIV04) {
		this.newMalePulmonaryBCHIV04 = newMalePulmonaryBCHIV04;
	}
	public Integer getNewFemalePulmonaryBCHIV04() {
		return newFemalePulmonaryBCHIV04;
	}
	public void setNewFemalePulmonaryBCHIV04(Integer newFemalePulmonaryBCHIV04) {
		this.newFemalePulmonaryBCHIV04 = newFemalePulmonaryBCHIV04;
	}
	public Integer getNewMalePulmonaryBCHIV0514() {
		return newMalePulmonaryBCHIV0514;
	}
	public void setNewMalePulmonaryBCHIV0514(Integer newMalePulmonaryBCHIV0514) {
		this.newMalePulmonaryBCHIV0514 = newMalePulmonaryBCHIV0514;
	}
	public Integer getNewFemalePulmonaryBCHIV0514() {
		return newFemalePulmonaryBCHIV0514;
	}
	public void setNewFemalePulmonaryBCHIV0514(Integer newFemalePulmonaryBCHIV0514) {
		this.newFemalePulmonaryBCHIV0514 = newFemalePulmonaryBCHIV0514;
	}
	public Integer getNewMalePulmonaryBCHIV1517() {
		return newMalePulmonaryBCHIV1517;
	}
	public void setNewMalePulmonaryBCHIV1517(Integer newMalePulmonaryBCHIV1517) {
		this.newMalePulmonaryBCHIV1517 = newMalePulmonaryBCHIV1517;
	}
	public Integer getNewFemalePulmonaryBCHIV1517() {
		return newFemalePulmonaryBCHIV1517;
	}
	public void setNewFemalePulmonaryBCHIV1517(Integer newFemalePulmonaryBCHIV1517) {
		this.newFemalePulmonaryBCHIV1517 = newFemalePulmonaryBCHIV1517;
	}
	public Integer getNewMalePulmonaryBCHIV1819() {
		return newMalePulmonaryBCHIV1819;
	}
	public void setNewMalePulmonaryBCHIV1819(Integer newMalePulmonaryBCHIV1819) {
		this.newMalePulmonaryBCHIV1819 = newMalePulmonaryBCHIV1819;
	}
	public Integer getNewFemalePulmonaryBCHIV1819() {
		return newFemalePulmonaryBCHIV1819;
	}
	public void setNewFemalePulmonaryBCHIV1819(Integer newFemalePulmonaryBCHIV1819) {
		this.newFemalePulmonaryBCHIV1819 = newFemalePulmonaryBCHIV1819;
	}
	public Integer getNewMalePulmonaryBCHIV2024() {
		return newMalePulmonaryBCHIV2024;
	}
	public void setNewMalePulmonaryBCHIV2024(Integer newMalePulmonaryBCHIV2024) {
		this.newMalePulmonaryBCHIV2024 = newMalePulmonaryBCHIV2024;
	}
	public Integer getNewFemalePulmonaryBCHIV2024() {
		return newFemalePulmonaryBCHIV2024;
	}
	public void setNewFemalePulmonaryBCHIV2024(Integer newFemalePulmonaryBCHIV2024) {
		this.newFemalePulmonaryBCHIV2024 = newFemalePulmonaryBCHIV2024;
	}
	public Integer getNewMalePulmonaryBCHIV2534() {
		return newMalePulmonaryBCHIV2534;
	}
	public void setNewMalePulmonaryBCHIV2534(Integer newMalePulmonaryBCHIV2534) {
		this.newMalePulmonaryBCHIV2534 = newMalePulmonaryBCHIV2534;
	}
	public Integer getNewFemalePulmonaryBCHIV2534() {
		return newFemalePulmonaryBCHIV2534;
	}
	public void setNewFemalePulmonaryBCHIV2534(Integer newFemalePulmonaryBCHIV2534) {
		this.newFemalePulmonaryBCHIV2534 = newFemalePulmonaryBCHIV2534;
	}
	public Integer getNewMalePulmonaryBCHIV3544() {
		return newMalePulmonaryBCHIV3544;
	}
	public void setNewMalePulmonaryBCHIV3544(Integer newMalePulmonaryBCHIV3544) {
		this.newMalePulmonaryBCHIV3544 = newMalePulmonaryBCHIV3544;
	}
	public Integer getNewFemalePulmonaryBCHIV3544() {
		return newFemalePulmonaryBCHIV3544;
	}
	public void setNewFemalePulmonaryBCHIV3544(Integer newFemalePulmonaryBCHIV3544) {
		this.newFemalePulmonaryBCHIV3544 = newFemalePulmonaryBCHIV3544;
	}
	public Integer getNewMalePulmonaryBCHIV4554() {
		return newMalePulmonaryBCHIV4554;
	}
	public void setNewMalePulmonaryBCHIV4554(Integer newMalePulmonaryBCHIV4554) {
		this.newMalePulmonaryBCHIV4554 = newMalePulmonaryBCHIV4554;
	}
	public Integer getNewFemalePulmonaryBCHIV4554() {
		return newFemalePulmonaryBCHIV4554;
	}
	public void setNewFemalePulmonaryBCHIV4554(Integer newFemalePulmonaryBCHIV4554) {
		this.newFemalePulmonaryBCHIV4554 = newFemalePulmonaryBCHIV4554;
	}
	public Integer getNewMalePulmonaryBCHIV5564() {
		return newMalePulmonaryBCHIV5564;
	}
	public void setNewMalePulmonaryBCHIV5564(Integer newMalePulmonaryBCHIV5564) {
		this.newMalePulmonaryBCHIV5564 = newMalePulmonaryBCHIV5564;
	}
	public Integer getNewFemalePulmonaryBCHIV5564() {
		return newFemalePulmonaryBCHIV5564;
	}
	public void setNewFemalePulmonaryBCHIV5564(Integer newFemalePulmonaryBCHIV5564) {
		this.newFemalePulmonaryBCHIV5564 = newFemalePulmonaryBCHIV5564;
	}
	public Integer getNewMalePulmonaryBCHIV65() {
		return newMalePulmonaryBCHIV65;
	}
	public void setNewMalePulmonaryBCHIV65(Integer newMalePulmonaryBCHIV65) {
		this.newMalePulmonaryBCHIV65 = newMalePulmonaryBCHIV65;
	}
	public Integer getNewFemalePulmonaryBCHIV65() {
		return newFemalePulmonaryBCHIV65;
	}
	public void setNewFemalePulmonaryBCHIV65(Integer newFemalePulmonaryBCHIV65) {
		this.newFemalePulmonaryBCHIV65 = newFemalePulmonaryBCHIV65;
	}
	public Integer getNewMalePulmonaryBCHIV() {
		return newMalePulmonaryBCHIV;
	}
	public void setNewMalePulmonaryBCHIV(Integer newMalePulmonaryBCHIV) {
		this.newMalePulmonaryBCHIV = newMalePulmonaryBCHIV;
	}
	public Integer getNewFemalePulmonaryBCHIV() {
		return newFemalePulmonaryBCHIV;
	}
	public void setNewFemalePulmonaryBCHIV(Integer newFemalePulmonaryBCHIV) {
		this.newFemalePulmonaryBCHIV = newFemalePulmonaryBCHIV;
	}
	public Integer getNewPulmonaryBCHIV() {
		return newPulmonaryBCHIV;
	}
	public void setNewPulmonaryBCHIV(Integer newPulmonaryBCHIV) {
		this.newPulmonaryBCHIV = newPulmonaryBCHIV;
	}
	public Integer getNewMalePulmonaryCD04() {
		return newMalePulmonaryCD04;
	}
	public void setNewMalePulmonaryCD04(Integer newMalePulmonaryCD04) {
		this.newMalePulmonaryCD04 = newMalePulmonaryCD04;
	}
	public Integer getNewFemalePulmonaryCD04() {
		return newFemalePulmonaryCD04;
	}
	public void setNewFemalePulmonaryCD04(Integer newFemalePulmonaryCD04) {
		this.newFemalePulmonaryCD04 = newFemalePulmonaryCD04;
	}
	public Integer getNewMalePulmonaryCD0514() {
		return newMalePulmonaryCD0514;
	}
	public void setNewMalePulmonaryCD0514(Integer newMalePulmonaryCD0514) {
		this.newMalePulmonaryCD0514 = newMalePulmonaryCD0514;
	}
	public Integer getNewFemalePulmonaryCD0514() {
		return newFemalePulmonaryCD0514;
	}
	public void setNewFemalePulmonaryCD0514(Integer newFemalePulmonaryCD0514) {
		this.newFemalePulmonaryCD0514 = newFemalePulmonaryCD0514;
	}
	public Integer getNewMalePulmonaryCD1517() {
		return newMalePulmonaryCD1517;
	}
	public void setNewMalePulmonaryCD1517(Integer newMalePulmonaryCD1517) {
		this.newMalePulmonaryCD1517 = newMalePulmonaryCD1517;
	}
	public Integer getNewFemalePulmonaryCD1517() {
		return newFemalePulmonaryCD1517;
	}
	public void setNewFemalePulmonaryCD1517(Integer newFemalePulmonaryCD1517) {
		this.newFemalePulmonaryCD1517 = newFemalePulmonaryCD1517;
	}
	public Integer getNewMalePulmonaryCD1819() {
		return newMalePulmonaryCD1819;
	}
	public void setNewMalePulmonaryCD1819(Integer newMalePulmonaryCD1819) {
		this.newMalePulmonaryCD1819 = newMalePulmonaryCD1819;
	}
	public Integer getNewFemalePulmonaryCD1819() {
		return newFemalePulmonaryCD1819;
	}
	public void setNewFemalePulmonaryCD1819(Integer newFemalePulmonaryCD1819) {
		this.newFemalePulmonaryCD1819 = newFemalePulmonaryCD1819;
	}
	public Integer getNewMalePulmonaryCD2024() {
		return newMalePulmonaryCD2024;
	}
	public void setNewMalePulmonaryCD2024(Integer newMalePulmonaryCD2024) {
		this.newMalePulmonaryCD2024 = newMalePulmonaryCD2024;
	}
	public Integer getNewFemalePulmonaryCD2024() {
		return newFemalePulmonaryCD2024;
	}
	public void setNewFemalePulmonaryCD2024(Integer newFemalePulmonaryCD2024) {
		this.newFemalePulmonaryCD2024 = newFemalePulmonaryCD2024;
	}
	public Integer getNewMalePulmonaryCD2534() {
		return newMalePulmonaryCD2534;
	}
	public void setNewMalePulmonaryCD2534(Integer newMalePulmonaryCD2534) {
		this.newMalePulmonaryCD2534 = newMalePulmonaryCD2534;
	}
	public Integer getNewFemalePulmonaryCD2534() {
		return newFemalePulmonaryCD2534;
	}
	public void setNewFemalePulmonaryCD2534(Integer newFemalePulmonaryCD2534) {
		this.newFemalePulmonaryCD2534 = newFemalePulmonaryCD2534;
	}
	public Integer getNewMalePulmonaryCD3544() {
		return newMalePulmonaryCD3544;
	}
	public void setNewMalePulmonaryCD3544(Integer newMalePulmonaryCD3544) {
		this.newMalePulmonaryCD3544 = newMalePulmonaryCD3544;
	}
	public Integer getNewFemalePulmonaryCD3544() {
		return newFemalePulmonaryCD3544;
	}
	public void setNewFemalePulmonaryCD3544(Integer newFemalePulmonaryCD3544) {
		this.newFemalePulmonaryCD3544 = newFemalePulmonaryCD3544;
	}
	public Integer getNewMalePulmonaryCD4554() {
		return newMalePulmonaryCD4554;
	}
	public void setNewMalePulmonaryCD4554(Integer newMalePulmonaryCD4554) {
		this.newMalePulmonaryCD4554 = newMalePulmonaryCD4554;
	}
	public Integer getNewFemalePulmonaryCD4554() {
		return newFemalePulmonaryCD4554;
	}
	public void setNewFemalePulmonaryCD4554(Integer newFemalePulmonaryCD4554) {
		this.newFemalePulmonaryCD4554 = newFemalePulmonaryCD4554;
	}
	public Integer getNewMalePulmonaryCD5564() {
		return newMalePulmonaryCD5564;
	}
	public void setNewMalePulmonaryCD5564(Integer newMalePulmonaryCD5564) {
		this.newMalePulmonaryCD5564 = newMalePulmonaryCD5564;
	}
	public Integer getNewFemalePulmonaryCD5564() {
		return newFemalePulmonaryCD5564;
	}
	public void setNewFemalePulmonaryCD5564(Integer newFemalePulmonaryCD5564) {
		this.newFemalePulmonaryCD5564 = newFemalePulmonaryCD5564;
	}
	public Integer getNewMalePulmonaryCD65() {
		return newMalePulmonaryCD65;
	}
	public void setNewMalePulmonaryCD65(Integer newMalePulmonaryCD65) {
		this.newMalePulmonaryCD65 = newMalePulmonaryCD65;
	}
	public Integer getNewFemalePulmonaryCD65() {
		return newFemalePulmonaryCD65;
	}
	public void setNewFemalePulmonaryCD65(Integer newFemalePulmonaryCD65) {
		this.newFemalePulmonaryCD65 = newFemalePulmonaryCD65;
	}
	public Integer getNewMalePulmonaryCD() {
		return newMalePulmonaryCD;
	}
	public void setNewMalePulmonaryCD(Integer newMalePulmonaryCD) {
		this.newMalePulmonaryCD = newMalePulmonaryCD;
	}
	public Integer getNewFemalePulmonaryCD() {
		return newFemalePulmonaryCD;
	}
	public void setNewFemalePulmonaryCD(Integer newFemalePulmonaryCD) {
		this.newFemalePulmonaryCD = newFemalePulmonaryCD;
	}
	public Integer getNewPulmonaryCD() {
		return newPulmonaryCD;
	}
	public void setNewPulmonaryCD(Integer newPulmonaryCD) {
		this.newPulmonaryCD = newPulmonaryCD;
	}
	public Integer getNewMalePulmonaryCDHIV04() {
		return newMalePulmonaryCDHIV04;
	}
	public void setNewMalePulmonaryCDHIV04(Integer newMalePulmonaryCDHIV04) {
		this.newMalePulmonaryCDHIV04 = newMalePulmonaryCDHIV04;
	}
	public Integer getNewFemalePulmonaryCDHIV04() {
		return newFemalePulmonaryCDHIV04;
	}
	public void setNewFemalePulmonaryCDHIV04(Integer newFemalePulmonaryCDHIV04) {
		this.newFemalePulmonaryCDHIV04 = newFemalePulmonaryCDHIV04;
	}
	public Integer getNewMalePulmonaryCDHIV0514() {
		return newMalePulmonaryCDHIV0514;
	}
	public void setNewMalePulmonaryCDHIV0514(Integer newMalePulmonaryCDHIV0514) {
		this.newMalePulmonaryCDHIV0514 = newMalePulmonaryCDHIV0514;
	}
	public Integer getNewFemalePulmonaryCDHIV0514() {
		return newFemalePulmonaryCDHIV0514;
	}
	public void setNewFemalePulmonaryCDHIV0514(Integer newFemalePulmonaryCDHIV0514) {
		this.newFemalePulmonaryCDHIV0514 = newFemalePulmonaryCDHIV0514;
	}
	public Integer getNewMalePulmonaryCDHIV1517() {
		return newMalePulmonaryCDHIV1517;
	}
	public void setNewMalePulmonaryCDHIV1517(Integer newMalePulmonaryCDHIV1517) {
		this.newMalePulmonaryCDHIV1517 = newMalePulmonaryCDHIV1517;
	}
	public Integer getNewFemalePulmonaryCDHIV1517() {
		return newFemalePulmonaryCDHIV1517;
	}
	public void setNewFemalePulmonaryCDHIV1517(Integer newFemalePulmonaryCDHIV1517) {
		this.newFemalePulmonaryCDHIV1517 = newFemalePulmonaryCDHIV1517;
	}
	public Integer getNewMalePulmonaryCDHIV1819() {
		return newMalePulmonaryCDHIV1819;
	}
	public void setNewMalePulmonaryCDHIV1819(Integer newMalePulmonaryCDHIV1819) {
		this.newMalePulmonaryCDHIV1819 = newMalePulmonaryCDHIV1819;
	}
	public Integer getNewFemalePulmonaryCDHIV1819() {
		return newFemalePulmonaryCDHIV1819;
	}
	public void setNewFemalePulmonaryCDHIV1819(Integer newFemalePulmonaryCDHIV1819) {
		this.newFemalePulmonaryCDHIV1819 = newFemalePulmonaryCDHIV1819;
	}
	public Integer getNewMalePulmonaryCDHIV2024() {
		return newMalePulmonaryCDHIV2024;
	}
	public void setNewMalePulmonaryCDHIV2024(Integer newMalePulmonaryCDHIV2024) {
		this.newMalePulmonaryCDHIV2024 = newMalePulmonaryCDHIV2024;
	}
	public Integer getNewFemalePulmonaryCDHIV2024() {
		return newFemalePulmonaryCDHIV2024;
	}
	public void setNewFemalePulmonaryCDHIV2024(Integer newFemalePulmonaryCDHIV2024) {
		this.newFemalePulmonaryCDHIV2024 = newFemalePulmonaryCDHIV2024;
	}
	public Integer getNewMalePulmonaryCDHIV2534() {
		return newMalePulmonaryCDHIV2534;
	}
	public void setNewMalePulmonaryCDHIV2534(Integer newMalePulmonaryCDHIV2534) {
		this.newMalePulmonaryCDHIV2534 = newMalePulmonaryCDHIV2534;
	}
	public Integer getNewFemalePulmonaryCDHIV2534() {
		return newFemalePulmonaryCDHIV2534;
	}
	public void setNewFemalePulmonaryCDHIV2534(Integer newFemalePulmonaryCDHIV2534) {
		this.newFemalePulmonaryCDHIV2534 = newFemalePulmonaryCDHIV2534;
	}
	public Integer getNewMalePulmonaryCDHIV3544() {
		return newMalePulmonaryCDHIV3544;
	}
	public void setNewMalePulmonaryCDHIV3544(Integer newMalePulmonaryCDHIV3544) {
		this.newMalePulmonaryCDHIV3544 = newMalePulmonaryCDHIV3544;
	}
	public Integer getNewFemalePulmonaryCDHIV3544() {
		return newFemalePulmonaryCDHIV3544;
	}
	public void setNewFemalePulmonaryCDHIV3544(Integer newFemalePulmonaryCDHIV3544) {
		this.newFemalePulmonaryCDHIV3544 = newFemalePulmonaryCDHIV3544;
	}
	public Integer getNewMalePulmonaryCDHIV4554() {
		return newMalePulmonaryCDHIV4554;
	}
	public void setNewMalePulmonaryCDHIV4554(Integer newMalePulmonaryCDHIV4554) {
		this.newMalePulmonaryCDHIV4554 = newMalePulmonaryCDHIV4554;
	}
	public Integer getNewFemalePulmonaryCDHIV4554() {
		return newFemalePulmonaryCDHIV4554;
	}
	public void setNewFemalePulmonaryCDHIV4554(Integer newFemalePulmonaryCDHIV4554) {
		this.newFemalePulmonaryCDHIV4554 = newFemalePulmonaryCDHIV4554;
	}
	public Integer getNewMalePulmonaryCDHIV5564() {
		return newMalePulmonaryCDHIV5564;
	}
	public void setNewMalePulmonaryCDHIV5564(Integer newMalePulmonaryCDHIV5564) {
		this.newMalePulmonaryCDHIV5564 = newMalePulmonaryCDHIV5564;
	}
	public Integer getNewFemalePulmonaryCDHIV5564() {
		return newFemalePulmonaryCDHIV5564;
	}
	public void setNewFemalePulmonaryCDHIV5564(Integer newFemalePulmonaryCDHIV5564) {
		this.newFemalePulmonaryCDHIV5564 = newFemalePulmonaryCDHIV5564;
	}
	public Integer getNewMalePulmonaryCDHIV65() {
		return newMalePulmonaryCDHIV65;
	}
	public void setNewMalePulmonaryCDHIV65(Integer newMalePulmonaryCDHIV65) {
		this.newMalePulmonaryCDHIV65 = newMalePulmonaryCDHIV65;
	}
	public Integer getNewFemalePulmonaryCDHIV65() {
		return newFemalePulmonaryCDHIV65;
	}
	public void setNewFemalePulmonaryCDHIV65(Integer newFemalePulmonaryCDHIV65) {
		this.newFemalePulmonaryCDHIV65 = newFemalePulmonaryCDHIV65;
	}
	public Integer getNewMalePulmonaryCDHIV() {
		return newMalePulmonaryCDHIV;
	}
	public void setNewMalePulmonaryCDHIV(Integer newMalePulmonaryCDHIV) {
		this.newMalePulmonaryCDHIV = newMalePulmonaryCDHIV;
	}
	public Integer getNewFemalePulmonaryCDHIV() {
		return newFemalePulmonaryCDHIV;
	}
	public void setNewFemalePulmonaryCDHIV(Integer newFemalePulmonaryCDHIV) {
		this.newFemalePulmonaryCDHIV = newFemalePulmonaryCDHIV;
	}
	public Integer getNewPulmonaryCDHIV() {
		return newPulmonaryCDHIV;
	}
	public void setNewPulmonaryCDHIV(Integer newPulmonaryCDHIV) {
		this.newPulmonaryCDHIV = newPulmonaryCDHIV;
	}
	public Integer getNewMaleExtrapulmonary04() {
		return newMaleExtrapulmonary04;
	}
	public void setNewMaleExtrapulmonary04(Integer newMaleExtrapulmonary04) {
		this.newMaleExtrapulmonary04 = newMaleExtrapulmonary04;
	}
	public Integer getNewFemaleExtrapulmonary04() {
		return newFemaleExtrapulmonary04;
	}
	public void setNewFemaleExtrapulmonary04(Integer newFemaleExtrapulmonary04) {
		this.newFemaleExtrapulmonary04 = newFemaleExtrapulmonary04;
	}
	public Integer getNewMaleExtrapulmonary0514() {
		return newMaleExtrapulmonary0514;
	}
	public void setNewMaleExtrapulmonary0514(Integer newMaleExtrapulmonary0514) {
		this.newMaleExtrapulmonary0514 = newMaleExtrapulmonary0514;
	}
	public Integer getNewFemaleExtrapulmonary0514() {
		return newFemaleExtrapulmonary0514;
	}
	public void setNewFemaleExtrapulmonary0514(Integer newFemaleExtrapulmonary0514) {
		this.newFemaleExtrapulmonary0514 = newFemaleExtrapulmonary0514;
	}
	public Integer getNewMaleExtrapulmonary1517() {
		return newMaleExtrapulmonary1517;
	}
	public void setNewMaleExtrapulmonary1517(Integer newMaleExtrapulmonary1517) {
		this.newMaleExtrapulmonary1517 = newMaleExtrapulmonary1517;
	}
	public Integer getNewFemaleExtrapulmonary1517() {
		return newFemaleExtrapulmonary1517;
	}
	public void setNewFemaleExtrapulmonary1517(Integer newFemaleExtrapulmonary1517) {
		this.newFemaleExtrapulmonary1517 = newFemaleExtrapulmonary1517;
	}
	public Integer getNewMaleExtrapulmonary1819() {
		return newMaleExtrapulmonary1819;
	}
	public void setNewMaleExtrapulmonary1819(Integer newMaleExtrapulmonary1819) {
		this.newMaleExtrapulmonary1819 = newMaleExtrapulmonary1819;
	}
	public Integer getNewFemaleExtrapulmonary1819() {
		return newFemaleExtrapulmonary1819;
	}
	public void setNewFemaleExtrapulmonary1819(Integer newFemaleExtrapulmonary1819) {
		this.newFemaleExtrapulmonary1819 = newFemaleExtrapulmonary1819;
	}
	public Integer getNewMaleExtrapulmonary2024() {
		return newMaleExtrapulmonary2024;
	}
	public void setNewMaleExtrapulmonary2024(Integer newMaleExtrapulmonary2024) {
		this.newMaleExtrapulmonary2024 = newMaleExtrapulmonary2024;
	}
	public Integer getNewFemaleExtrapulmonary2024() {
		return newFemaleExtrapulmonary2024;
	}
	public void setNewFemaleExtrapulmonary2024(Integer newFemaleExtrapulmonary2024) {
		this.newFemaleExtrapulmonary2024 = newFemaleExtrapulmonary2024;
	}
	public Integer getNewMaleExtrapulmonary2534() {
		return newMaleExtrapulmonary2534;
	}
	public void setNewMaleExtrapulmonary2534(Integer newMaleExtrapulmonary2534) {
		this.newMaleExtrapulmonary2534 = newMaleExtrapulmonary2534;
	}
	public Integer getNewFemaleExtrapulmonary2534() {
		return newFemaleExtrapulmonary2534;
	}
	public void setNewFemaleExtrapulmonary2534(Integer newFemaleExtrapulmonary2534) {
		this.newFemaleExtrapulmonary2534 = newFemaleExtrapulmonary2534;
	}
	public Integer getNewMaleExtrapulmonary3544() {
		return newMaleExtrapulmonary3544;
	}
	public void setNewMaleExtrapulmonary3544(Integer newMaleExtrapulmonary3544) {
		this.newMaleExtrapulmonary3544 = newMaleExtrapulmonary3544;
	}
	public Integer getNewFemaleExtrapulmonary3544() {
		return newFemaleExtrapulmonary3544;
	}
	public void setNewFemaleExtrapulmonary3544(Integer newFemaleExtrapulmonary3544) {
		this.newFemaleExtrapulmonary3544 = newFemaleExtrapulmonary3544;
	}
	public Integer getNewMaleExtrapulmonary4554() {
		return newMaleExtrapulmonary4554;
	}
	public void setNewMaleExtrapulmonary4554(Integer newMaleExtrapulmonary4554) {
		this.newMaleExtrapulmonary4554 = newMaleExtrapulmonary4554;
	}
	public Integer getNewFemaleExtrapulmonary4554() {
		return newFemaleExtrapulmonary4554;
	}
	public void setNewFemaleExtrapulmonary4554(Integer newFemaleExtrapulmonary4554) {
		this.newFemaleExtrapulmonary4554 = newFemaleExtrapulmonary4554;
	}
	public Integer getNewMaleExtrapulmonary5564() {
		return newMaleExtrapulmonary5564;
	}
	public void setNewMaleExtrapulmonary5564(Integer newMaleExtrapulmonary5564) {
		this.newMaleExtrapulmonary5564 = newMaleExtrapulmonary5564;
	}
	public Integer getNewFemaleExtrapulmonary5564() {
		return newFemaleExtrapulmonary5564;
	}
	public void setNewFemaleExtrapulmonary5564(Integer newFemaleExtrapulmonary5564) {
		this.newFemaleExtrapulmonary5564 = newFemaleExtrapulmonary5564;
	}
	public Integer getNewMaleExtrapulmonary65() {
		return newMaleExtrapulmonary65;
	}
	public void setNewMaleExtrapulmonary65(Integer newMaleExtrapulmonary65) {
		this.newMaleExtrapulmonary65 = newMaleExtrapulmonary65;
	}
	public Integer getNewFemaleExtrapulmonary65() {
		return newFemaleExtrapulmonary65;
	}
	public void setNewFemaleExtrapulmonary65(Integer newFemaleExtrapulmonary65) {
		this.newFemaleExtrapulmonary65 = newFemaleExtrapulmonary65;
	}
	public Integer getNewMaleExtrapulmonary() {
		return newMaleExtrapulmonary;
	}
	public void setNewMaleExtrapulmonary(Integer newMaleExtrapulmonary) {
		this.newMaleExtrapulmonary = newMaleExtrapulmonary;
	}
	public Integer getNewFemaleExtrapulmonary() {
		return newFemaleExtrapulmonary;
	}
	public void setNewFemaleExtrapulmonary(Integer newFemaleExtrapulmonary) {
		this.newFemaleExtrapulmonary = newFemaleExtrapulmonary;
	}
	public Integer getNewExtrapulmonary() {
		return newExtrapulmonary;
	}
	public void setNewExtrapulmonary(Integer newExtrapulmonary) {
		this.newExtrapulmonary = newExtrapulmonary;
	}
	public Integer getNewMaleExtrapulmonaryHIV04() {
		return newMaleExtrapulmonaryHIV04;
	}
	public void setNewMaleExtrapulmonaryHIV04(Integer newMaleExtrapulmonaryHIV04) {
		this.newMaleExtrapulmonaryHIV04 = newMaleExtrapulmonaryHIV04;
	}
	public Integer getNewFemaleExtrapulmonaryHIV04() {
		return newFemaleExtrapulmonaryHIV04;
	}
	public void setNewFemaleExtrapulmonaryHIV04(Integer newFemaleExtrapulmonaryHIV04) {
		this.newFemaleExtrapulmonaryHIV04 = newFemaleExtrapulmonaryHIV04;
	}
	public Integer getNewMaleExtrapulmonaryHIV0514() {
		return newMaleExtrapulmonaryHIV0514;
	}
	public void setNewMaleExtrapulmonaryHIV0514(Integer newMaleExtrapulmonaryHIV0514) {
		this.newMaleExtrapulmonaryHIV0514 = newMaleExtrapulmonaryHIV0514;
	}
	public Integer getNewFemaleExtrapulmonaryHIV0514() {
		return newFemaleExtrapulmonaryHIV0514;
	}
	public void setNewFemaleExtrapulmonaryHIV0514(
			Integer newFemaleExtrapulmonaryHIV0514) {
		this.newFemaleExtrapulmonaryHIV0514 = newFemaleExtrapulmonaryHIV0514;
	}
	public Integer getNewMaleExtrapulmonaryHIV1517() {
		return newMaleExtrapulmonaryHIV1517;
	}
	public void setNewMaleExtrapulmonaryHIV1517(Integer newMaleExtrapulmonaryHIV1517) {
		this.newMaleExtrapulmonaryHIV1517 = newMaleExtrapulmonaryHIV1517;
	}
	public Integer getNewFemaleExtrapulmonaryHIV1517() {
		return newFemaleExtrapulmonaryHIV1517;
	}
	public void setNewFemaleExtrapulmonaryHIV1517(
			Integer newFemaleExtrapulmonaryHIV1517) {
		this.newFemaleExtrapulmonaryHIV1517 = newFemaleExtrapulmonaryHIV1517;
	}
	public Integer getNewMaleExtrapulmonaryHIV1819() {
		return newMaleExtrapulmonaryHIV1819;
	}
	public void setNewMaleExtrapulmonaryHIV1819(Integer newMaleExtrapulmonaryHIV1819) {
		this.newMaleExtrapulmonaryHIV1819 = newMaleExtrapulmonaryHIV1819;
	}
	public Integer getNewFemaleExtrapulmonaryHIV1819() {
		return newFemaleExtrapulmonaryHIV1819;
	}
	public void setNewFemaleExtrapulmonaryHIV1819(
			Integer newFemaleExtrapulmonaryHIV1819) {
		this.newFemaleExtrapulmonaryHIV1819 = newFemaleExtrapulmonaryHIV1819;
	}
	public Integer getNewMaleExtrapulmonaryHIV2024() {
		return newMaleExtrapulmonaryHIV2024;
	}
	public void setNewMaleExtrapulmonaryHIV2024(Integer newMaleExtrapulmonaryHIV2024) {
		this.newMaleExtrapulmonaryHIV2024 = newMaleExtrapulmonaryHIV2024;
	}
	public Integer getNewFemaleExtrapulmonaryHIV2024() {
		return newFemaleExtrapulmonaryHIV2024;
	}
	public void setNewFemaleExtrapulmonaryHIV2024(
			Integer newFemaleExtrapulmonaryHIV2024) {
		this.newFemaleExtrapulmonaryHIV2024 = newFemaleExtrapulmonaryHIV2024;
	}
	public Integer getNewMaleExtrapulmonaryHIV2534() {
		return newMaleExtrapulmonaryHIV2534;
	}
	public void setNewMaleExtrapulmonaryHIV2534(Integer newMaleExtrapulmonaryHIV2534) {
		this.newMaleExtrapulmonaryHIV2534 = newMaleExtrapulmonaryHIV2534;
	}
	public Integer getNewFemaleExtrapulmonaryHIV2534() {
		return newFemaleExtrapulmonaryHIV2534;
	}
	public void setNewFemaleExtrapulmonaryHIV2534(
			Integer newFemaleExtrapulmonaryHIV2534) {
		this.newFemaleExtrapulmonaryHIV2534 = newFemaleExtrapulmonaryHIV2534;
	}
	public Integer getNewMaleExtrapulmonaryHIV3544() {
		return newMaleExtrapulmonaryHIV3544;
	}
	public void setNewMaleExtrapulmonaryHIV3544(Integer newMaleExtrapulmonaryHIV3544) {
		this.newMaleExtrapulmonaryHIV3544 = newMaleExtrapulmonaryHIV3544;
	}
	public Integer getNewFemaleExtrapulmonaryHIV3544() {
		return newFemaleExtrapulmonaryHIV3544;
	}
	public void setNewFemaleExtrapulmonaryHIV3544(
			Integer newFemaleExtrapulmonaryHIV3544) {
		this.newFemaleExtrapulmonaryHIV3544 = newFemaleExtrapulmonaryHIV3544;
	}
	public Integer getNewMaleExtrapulmonaryHIV4554() {
		return newMaleExtrapulmonaryHIV4554;
	}
	public void setNewMaleExtrapulmonaryHIV4554(Integer newMaleExtrapulmonaryHIV4554) {
		this.newMaleExtrapulmonaryHIV4554 = newMaleExtrapulmonaryHIV4554;
	}
	public Integer getNewFemaleExtrapulmonaryHIV4554() {
		return newFemaleExtrapulmonaryHIV4554;
	}
	public void setNewFemaleExtrapulmonaryHIV4554(
			Integer newFemaleExtrapulmonaryHIV4554) {
		this.newFemaleExtrapulmonaryHIV4554 = newFemaleExtrapulmonaryHIV4554;
	}
	public Integer getNewMaleExtrapulmonaryHIV5564() {
		return newMaleExtrapulmonaryHIV5564;
	}
	public void setNewMaleExtrapulmonaryHIV5564(Integer newMaleExtrapulmonaryHIV5564) {
		this.newMaleExtrapulmonaryHIV5564 = newMaleExtrapulmonaryHIV5564;
	}
	public Integer getNewFemaleExtrapulmonaryHIV5564() {
		return newFemaleExtrapulmonaryHIV5564;
	}
	public void setNewFemaleExtrapulmonaryHIV5564(
			Integer newFemaleExtrapulmonaryHIV5564) {
		this.newFemaleExtrapulmonaryHIV5564 = newFemaleExtrapulmonaryHIV5564;
	}
	public Integer getNewMaleExtrapulmonaryHIV65() {
		return newMaleExtrapulmonaryHIV65;
	}
	public void setNewMaleExtrapulmonaryHIV65(Integer newMaleExtrapulmonaryHIV65) {
		this.newMaleExtrapulmonaryHIV65 = newMaleExtrapulmonaryHIV65;
	}
	public Integer getNewFemaleExtrapulmonaryHIV65() {
		return newFemaleExtrapulmonaryHIV65;
	}
	public void setNewFemaleExtrapulmonaryHIV65(Integer newFemaleExtrapulmonaryHIV65) {
		this.newFemaleExtrapulmonaryHIV65 = newFemaleExtrapulmonaryHIV65;
	}
	public Integer getNewMaleExtrapulmonaryHIV() {
		return newMaleExtrapulmonaryHIV;
	}
	public void setNewMaleExtrapulmonaryHIV(Integer newMaleExtrapulmonaryHIV) {
		this.newMaleExtrapulmonaryHIV = newMaleExtrapulmonaryHIV;
	}
	public Integer getNewFemaleExtrapulmonaryHIV() {
		return newFemaleExtrapulmonaryHIV;
	}
	public void setNewFemaleExtrapulmonaryHIV(Integer newFemaleExtrapulmonaryHIV) {
		this.newFemaleExtrapulmonaryHIV = newFemaleExtrapulmonaryHIV;
	}
	public Integer getNewExtrapulmonaryHIV() {
		return newExtrapulmonaryHIV;
	}
	public void setNewExtrapulmonaryHIV(Integer newExtrapulmonaryHIV) {
		this.newExtrapulmonaryHIV = newExtrapulmonaryHIV;
	}
	public Integer getNewMale04() {
		return newMale04;
	}
	public void setNewMale04(Integer newMale04) {
		this.newMale04 = newMale04;
	}
	public Integer getNewFemale04() {
		return newFemale04;
	}
	public void setNewFemale04(Integer newFemale04) {
		this.newFemale04 = newFemale04;
	}
	public Integer getNewMale0514() {
		return newMale0514;
	}
	public void setNewMale0514(Integer newMale0514) {
		this.newMale0514 = newMale0514;
	}
	public Integer getNewFemale0514() {
		return newFemale0514;
	}
	public void setNewFemale0514(Integer newFemale0514) {
		this.newFemale0514 = newFemale0514;
	}
	public Integer getNewMale1517() {
		return newMale1517;
	}
	public void setNewMale1517(Integer newMale1517) {
		this.newMale1517 = newMale1517;
	}
	public Integer getNewFemale1517() {
		return newFemale1517;
	}
	public void setNewFemale1517(Integer newFemale1517) {
		this.newFemale1517 = newFemale1517;
	}
	public Integer getNewMale1819() {
		return newMale1819;
	}
	public void setNewMale1819(Integer newMale1819) {
		this.newMale1819 = newMale1819;
	}
	public Integer getNewFemale1819() {
		return newFemale1819;
	}
	public void setNewFemale1819(Integer newFemale1819) {
		this.newFemale1819 = newFemale1819;
	}
	public Integer getNewMale2024() {
		return newMale2024;
	}
	public void setNewMale2024(Integer newMale2024) {
		this.newMale2024 = newMale2024;
	}
	public Integer getNewFemale2024() {
		return newFemale2024;
	}
	public void setNewFemale2024(Integer newFemale2024) {
		this.newFemale2024 = newFemale2024;
	}
	public Integer getNewMale2534() {
		return newMale2534;
	}
	public void setNewMale2534(Integer newMale2534) {
		this.newMale2534 = newMale2534;
	}
	public Integer getNewFemale2534() {
		return newFemale2534;
	}
	public void setNewFemale2534(Integer newFemale2534) {
		this.newFemale2534 = newFemale2534;
	}
	public Integer getNewMale3544() {
		return newMale3544;
	}
	public void setNewMale3544(Integer newMale3544) {
		this.newMale3544 = newMale3544;
	}
	public Integer getNewFemale3544() {
		return newFemale3544;
	}
	public void setNewFemale3544(Integer newFemale3544) {
		this.newFemale3544 = newFemale3544;
	}
	public Integer getNewMale4554() {
		return newMale4554;
	}
	public void setNewMale4554(Integer newMale4554) {
		this.newMale4554 = newMale4554;
	}
	public Integer getNewFemale4554() {
		return newFemale4554;
	}
	public void setNewFemale4554(Integer newFemale4554) {
		this.newFemale4554 = newFemale4554;
	}
	public Integer getNewMale5564() {
		return newMale5564;
	}
	public void setNewMale5564(Integer newMale5564) {
		this.newMale5564 = newMale5564;
	}
	public Integer getNewFemale5564() {
		return newFemale5564;
	}
	public void setNewFemale5564(Integer newFemale5564) {
		this.newFemale5564 = newFemale5564;
	}
	public Integer getNewMale65() {
		return newMale65;
	}
	public void setNewMale65(Integer newMale65) {
		this.newMale65 = newMale65;
	}
	public Integer getNewFemale65() {
		return newFemale65;
	}
	public void setNewFemale65(Integer newFemale65) {
		this.newFemale65 = newFemale65;
	}
	public Integer getNewMale() {
		return newMale;
	}
	public void setNewMale(Integer newMale) {
		this.newMale = newMale;
	}
	public Integer getNewFemale() {
		return newFemale;
	}
	public void setNewFemale(Integer newFemale) {
		this.newFemale = newFemale;
	}
	public Integer getNewAll() {
		return newAll;
	}
	public void setNewAll(Integer newAll) {
		this.newAll = newAll;
	}
	public Integer getNewMaleHIV04() {
		return newMaleHIV04;
	}
	public void setNewMaleHIV04(Integer newMaleHIV04) {
		this.newMaleHIV04 = newMaleHIV04;
	}
	public Integer getNewFemaleHIV04() {
		return newFemaleHIV04;
	}
	public void setNewFemaleHIV04(Integer newFemaleHIV04) {
		this.newFemaleHIV04 = newFemaleHIV04;
	}
	public Integer getNewMaleHIV0514() {
		return newMaleHIV0514;
	}
	public void setNewMaleHIV0514(Integer newMaleHIV0514) {
		this.newMaleHIV0514 = newMaleHIV0514;
	}
	public Integer getNewFemaleHIV0514() {
		return newFemaleHIV0514;
	}
	public void setNewFemaleHIV0514(Integer newFemaleHIV0514) {
		this.newFemaleHIV0514 = newFemaleHIV0514;
	}
	public Integer getNewMaleHIV1517() {
		return newMaleHIV1517;
	}
	public void setNewMaleHIV1517(Integer newMaleHIV1517) {
		this.newMaleHIV1517 = newMaleHIV1517;
	}
	public Integer getNewFemaleHIV1517() {
		return newFemaleHIV1517;
	}
	public void setNewFemaleHIV1517(Integer newFemaleHIV1517) {
		this.newFemaleHIV1517 = newFemaleHIV1517;
	}
	public Integer getNewMaleHIV1819() {
		return newMaleHIV1819;
	}
	public void setNewMaleHIV1819(Integer newMaleHIV1819) {
		this.newMaleHIV1819 = newMaleHIV1819;
	}
	public Integer getNewFemaleHIV1819() {
		return newFemaleHIV1819;
	}
	public void setNewFemaleHIV1819(Integer newFemaleHIV1819) {
		this.newFemaleHIV1819 = newFemaleHIV1819;
	}
	public Integer getNewMaleHIV2024() {
		return newMaleHIV2024;
	}
	public void setNewMaleHIV2024(Integer newMaleHIV2024) {
		this.newMaleHIV2024 = newMaleHIV2024;
	}
	public Integer getNewFemaleHIV2024() {
		return newFemaleHIV2024;
	}
	public void setNewFemaleHIV2024(Integer newFemaleHIV2024) {
		this.newFemaleHIV2024 = newFemaleHIV2024;
	}
	public Integer getNewMaleHIV2534() {
		return newMaleHIV2534;
	}
	public void setNewMaleHIV2534(Integer newMaleHIV2534) {
		this.newMaleHIV2534 = newMaleHIV2534;
	}
	public Integer getNewFemaleHIV2534() {
		return newFemaleHIV2534;
	}
	public void setNewFemaleHIV2534(Integer newFemaleHIV2534) {
		this.newFemaleHIV2534 = newFemaleHIV2534;
	}
	public Integer getNewMaleHIV3544() {
		return newMaleHIV3544;
	}
	public void setNewMaleHIV3544(Integer newMaleHIV3544) {
		this.newMaleHIV3544 = newMaleHIV3544;
	}
	public Integer getNewFemaleHIV3544() {
		return newFemaleHIV3544;
	}
	public void setNewFemaleHIV3544(Integer newFemaleHIV3544) {
		this.newFemaleHIV3544 = newFemaleHIV3544;
	}
	public Integer getNewMaleHIV4554() {
		return newMaleHIV4554;
	}
	public void setNewMaleHIV4554(Integer newMaleHIV4554) {
		this.newMaleHIV4554 = newMaleHIV4554;
	}
	public Integer getNewFemaleHIV4554() {
		return newFemaleHIV4554;
	}
	public void setNewFemaleHIV4554(Integer newFemaleHIV4554) {
		this.newFemaleHIV4554 = newFemaleHIV4554;
	}
	public Integer getNewMaleHIV5564() {
		return newMaleHIV5564;
	}
	public void setNewMaleHIV5564(Integer newMaleHIV5564) {
		this.newMaleHIV5564 = newMaleHIV5564;
	}
	public Integer getNewFemaleHIV5564() {
		return newFemaleHIV5564;
	}
	public void setNewFemaleHIV5564(Integer newFemaleHIV5564) {
		this.newFemaleHIV5564 = newFemaleHIV5564;
	}
	public Integer getNewMaleHIV65() {
		return newMaleHIV65;
	}
	public void setNewMaleHIV65(Integer newMaleHIV65) {
		this.newMaleHIV65 = newMaleHIV65;
	}
	public Integer getNewFemaleHIV65() {
		return newFemaleHIV65;
	}
	public void setNewFemaleHIV65(Integer newFemaleHIV65) {
		this.newFemaleHIV65 = newFemaleHIV65;
	}
	public Integer getNewMaleHIV() {
		return newMaleHIV;
	}
	public void setNewMaleHIV(Integer newMaleHIV) {
		this.newMaleHIV = newMaleHIV;
	}
	public Integer getNewFemaleHIV() {
		return newFemaleHIV;
	}
	public void setNewFemaleHIV(Integer newFemaleHIV) {
		this.newFemaleHIV = newFemaleHIV;
	}
	public Integer getNewAllHIV() {
		return newAllHIV;
	}
	public void setNewAllHIV(Integer newAllHIV) {
		this.newAllHIV = newAllHIV;
	}
	public Integer getRelapseMalePulmonaryBC04() {
		return relapseMalePulmonaryBC04;
	}
	public void setRelapseMalePulmonaryBC04(Integer relapseMalePulmonaryBC04) {
		this.relapseMalePulmonaryBC04 = relapseMalePulmonaryBC04;
	}
	public Integer getRelapseFemalePulmonaryBC04() {
		return relapseFemalePulmonaryBC04;
	}
	public void setRelapseFemalePulmonaryBC04(Integer relapseFemalePulmonaryBC04) {
		this.relapseFemalePulmonaryBC04 = relapseFemalePulmonaryBC04;
	}
	public Integer getRelapseMalePulmonaryBC0514() {
		return relapseMalePulmonaryBC0514;
	}
	public void setRelapseMalePulmonaryBC0514(Integer relapseMalePulmonaryBC0514) {
		this.relapseMalePulmonaryBC0514 = relapseMalePulmonaryBC0514;
	}
	public Integer getRelapseFemalePulmonaryBC0514() {
		return relapseFemalePulmonaryBC0514;
	}
	public void setRelapseFemalePulmonaryBC0514(Integer relapseFemalePulmonaryBC0514) {
		this.relapseFemalePulmonaryBC0514 = relapseFemalePulmonaryBC0514;
	}
	public Integer getRelapseMalePulmonaryBC1517() {
		return relapseMalePulmonaryBC1517;
	}
	public void setRelapseMalePulmonaryBC1517(Integer relapseMalePulmonaryBC1517) {
		this.relapseMalePulmonaryBC1517 = relapseMalePulmonaryBC1517;
	}
	public Integer getRelapseFemalePulmonaryBC1517() {
		return relapseFemalePulmonaryBC1517;
	}
	public void setRelapseFemalePulmonaryBC1517(Integer relapseFemalePulmonaryBC1517) {
		this.relapseFemalePulmonaryBC1517 = relapseFemalePulmonaryBC1517;
	}
	public Integer getRelapseMalePulmonaryBC1819() {
		return relapseMalePulmonaryBC1819;
	}
	public void setRelapseMalePulmonaryBC1819(Integer relapseMalePulmonaryBC1819) {
		this.relapseMalePulmonaryBC1819 = relapseMalePulmonaryBC1819;
	}
	public Integer getRelapseFemalePulmonaryBC1819() {
		return relapseFemalePulmonaryBC1819;
	}
	public void setRelapseFemalePulmonaryBC1819(Integer relapseFemalePulmonaryBC1819) {
		this.relapseFemalePulmonaryBC1819 = relapseFemalePulmonaryBC1819;
	}
	public Integer getRelapseMalePulmonaryBC2024() {
		return relapseMalePulmonaryBC2024;
	}
	public void setRelapseMalePulmonaryBC2024(Integer relapseMalePulmonaryBC2024) {
		this.relapseMalePulmonaryBC2024 = relapseMalePulmonaryBC2024;
	}
	public Integer getRelapseFemalePulmonaryBC2024() {
		return relapseFemalePulmonaryBC2024;
	}
	public void setRelapseFemalePulmonaryBC2024(Integer relapseFemalePulmonaryBC2024) {
		this.relapseFemalePulmonaryBC2024 = relapseFemalePulmonaryBC2024;
	}
	public Integer getRelapseMalePulmonaryBC2534() {
		return relapseMalePulmonaryBC2534;
	}
	public void setRelapseMalePulmonaryBC2534(Integer relapseMalePulmonaryBC2534) {
		this.relapseMalePulmonaryBC2534 = relapseMalePulmonaryBC2534;
	}
	public Integer getRelapseFemalePulmonaryBC2534() {
		return relapseFemalePulmonaryBC2534;
	}
	public void setRelapseFemalePulmonaryBC2534(Integer relapseFemalePulmonaryBC2534) {
		this.relapseFemalePulmonaryBC2534 = relapseFemalePulmonaryBC2534;
	}
	public Integer getRelapseMalePulmonaryBC3544() {
		return relapseMalePulmonaryBC3544;
	}
	public void setRelapseMalePulmonaryBC3544(Integer relapseMalePulmonaryBC3544) {
		this.relapseMalePulmonaryBC3544 = relapseMalePulmonaryBC3544;
	}
	public Integer getRelapseFemalePulmonaryBC3544() {
		return relapseFemalePulmonaryBC3544;
	}
	public void setRelapseFemalePulmonaryBC3544(Integer relapseFemalePulmonaryBC3544) {
		this.relapseFemalePulmonaryBC3544 = relapseFemalePulmonaryBC3544;
	}
	public Integer getRelapseMalePulmonaryBC4554() {
		return relapseMalePulmonaryBC4554;
	}
	public void setRelapseMalePulmonaryBC4554(Integer relapseMalePulmonaryBC4554) {
		this.relapseMalePulmonaryBC4554 = relapseMalePulmonaryBC4554;
	}
	public Integer getRelapseFemalePulmonaryBC4554() {
		return relapseFemalePulmonaryBC4554;
	}
	public void setRelapseFemalePulmonaryBC4554(Integer relapseFemalePulmonaryBC4554) {
		this.relapseFemalePulmonaryBC4554 = relapseFemalePulmonaryBC4554;
	}
	public Integer getRelapseMalePulmonaryBC5564() {
		return relapseMalePulmonaryBC5564;
	}
	public void setRelapseMalePulmonaryBC5564(Integer relapseMalePulmonaryBC5564) {
		this.relapseMalePulmonaryBC5564 = relapseMalePulmonaryBC5564;
	}
	public Integer getRelapseFemalePulmonaryBC5564() {
		return relapseFemalePulmonaryBC5564;
	}
	public void setRelapseFemalePulmonaryBC5564(Integer relapseFemalePulmonaryBC5564) {
		this.relapseFemalePulmonaryBC5564 = relapseFemalePulmonaryBC5564;
	}
	public Integer getRelapseMalePulmonaryBC65() {
		return relapseMalePulmonaryBC65;
	}
	public void setRelapseMalePulmonaryBC65(Integer relapseMalePulmonaryBC65) {
		this.relapseMalePulmonaryBC65 = relapseMalePulmonaryBC65;
	}
	public Integer getRelapseFemalePulmonaryBC65() {
		return relapseFemalePulmonaryBC65;
	}
	public void setRelapseFemalePulmonaryBC65(Integer relapseFemalePulmonaryBC65) {
		this.relapseFemalePulmonaryBC65 = relapseFemalePulmonaryBC65;
	}
	public Integer getRelapseMalePulmonaryBC() {
		return relapseMalePulmonaryBC;
	}
	public void setRelapseMalePulmonaryBC(Integer relapseMalePulmonaryBC) {
		this.relapseMalePulmonaryBC = relapseMalePulmonaryBC;
	}
	public Integer getRelapseFemalePulmonaryBC() {
		return relapseFemalePulmonaryBC;
	}
	public void setRelapseFemalePulmonaryBC(Integer relapseFemalePulmonaryBC) {
		this.relapseFemalePulmonaryBC = relapseFemalePulmonaryBC;
	}
	public Integer getRelapsePulmonaryBC() {
		return relapsePulmonaryBC;
	}
	public void setRelapsePulmonaryBC(Integer relapsePulmonaryBC) {
		this.relapsePulmonaryBC = relapsePulmonaryBC;
	}
	public Integer getRelapseMalePulmonaryBCHIV04() {
		return relapseMalePulmonaryBCHIV04;
	}
	public void setRelapseMalePulmonaryBCHIV04(Integer relapseMalePulmonaryBCHIV04) {
		this.relapseMalePulmonaryBCHIV04 = relapseMalePulmonaryBCHIV04;
	}
	public Integer getRelapseFemalePulmonaryBCHIV04() {
		return relapseFemalePulmonaryBCHIV04;
	}
	public void setRelapseFemalePulmonaryBCHIV04(
			Integer relapseFemalePulmonaryBCHIV04) {
		this.relapseFemalePulmonaryBCHIV04 = relapseFemalePulmonaryBCHIV04;
	}
	public Integer getRelapseMalePulmonaryBCHIV0514() {
		return relapseMalePulmonaryBCHIV0514;
	}
	public void setRelapseMalePulmonaryBCHIV0514(
			Integer relapseMalePulmonaryBCHIV0514) {
		this.relapseMalePulmonaryBCHIV0514 = relapseMalePulmonaryBCHIV0514;
	}
	public Integer getRelapseFemalePulmonaryBCHIV0514() {
		return relapseFemalePulmonaryBCHIV0514;
	}
	public void setRelapseFemalePulmonaryBCHIV0514(
			Integer relapseFemalePulmonaryBCHIV0514) {
		this.relapseFemalePulmonaryBCHIV0514 = relapseFemalePulmonaryBCHIV0514;
	}
	public Integer getRelapseMalePulmonaryBCHIV1517() {
		return relapseMalePulmonaryBCHIV1517;
	}
	public void setRelapseMalePulmonaryBCHIV1517(
			Integer relapseMalePulmonaryBCHIV1517) {
		this.relapseMalePulmonaryBCHIV1517 = relapseMalePulmonaryBCHIV1517;
	}
	public Integer getRelapseFemalePulmonaryBCHIV1517() {
		return relapseFemalePulmonaryBCHIV1517;
	}
	public void setRelapseFemalePulmonaryBCHIV1517(
			Integer relapseFemalePulmonaryBCHIV1517) {
		this.relapseFemalePulmonaryBCHIV1517 = relapseFemalePulmonaryBCHIV1517;
	}
	public Integer getRelapseMalePulmonaryBCHIV1819() {
		return relapseMalePulmonaryBCHIV1819;
	}
	public void setRelapseMalePulmonaryBCHIV1819(
			Integer relapseMalePulmonaryBCHIV1819) {
		this.relapseMalePulmonaryBCHIV1819 = relapseMalePulmonaryBCHIV1819;
	}
	public Integer getRelapseFemalePulmonaryBCHIV1819() {
		return relapseFemalePulmonaryBCHIV1819;
	}
	public void setRelapseFemalePulmonaryBCHIV1819(
			Integer relapseFemalePulmonaryBCHIV1819) {
		this.relapseFemalePulmonaryBCHIV1819 = relapseFemalePulmonaryBCHIV1819;
	}
	public Integer getRelapseMalePulmonaryBCHIV2024() {
		return relapseMalePulmonaryBCHIV2024;
	}
	public void setRelapseMalePulmonaryBCHIV2024(
			Integer relapseMalePulmonaryBCHIV2024) {
		this.relapseMalePulmonaryBCHIV2024 = relapseMalePulmonaryBCHIV2024;
	}
	public Integer getRelapseFemalePulmonaryBCHIV2024() {
		return relapseFemalePulmonaryBCHIV2024;
	}
	public void setRelapseFemalePulmonaryBCHIV2024(
			Integer relapseFemalePulmonaryBCHIV2024) {
		this.relapseFemalePulmonaryBCHIV2024 = relapseFemalePulmonaryBCHIV2024;
	}
	public Integer getRelapseMalePulmonaryBCHIV2534() {
		return relapseMalePulmonaryBCHIV2534;
	}
	public void setRelapseMalePulmonaryBCHIV2534(
			Integer relapseMalePulmonaryBCHIV2534) {
		this.relapseMalePulmonaryBCHIV2534 = relapseMalePulmonaryBCHIV2534;
	}
	public Integer getRelapseFemalePulmonaryBCHIV2534() {
		return relapseFemalePulmonaryBCHIV2534;
	}
	public void setRelapseFemalePulmonaryBCHIV2534(
			Integer relapseFemalePulmonaryBCHIV2534) {
		this.relapseFemalePulmonaryBCHIV2534 = relapseFemalePulmonaryBCHIV2534;
	}
	public Integer getRelapseMalePulmonaryBCHIV3544() {
		return relapseMalePulmonaryBCHIV3544;
	}
	public void setRelapseMalePulmonaryBCHIV3544(
			Integer relapseMalePulmonaryBCHIV3544) {
		this.relapseMalePulmonaryBCHIV3544 = relapseMalePulmonaryBCHIV3544;
	}
	public Integer getRelapseFemalePulmonaryBCHIV3544() {
		return relapseFemalePulmonaryBCHIV3544;
	}
	public void setRelapseFemalePulmonaryBCHIV3544(
			Integer relapseFemalePulmonaryBCHIV3544) {
		this.relapseFemalePulmonaryBCHIV3544 = relapseFemalePulmonaryBCHIV3544;
	}
	public Integer getRelapseMalePulmonaryBCHIV4554() {
		return relapseMalePulmonaryBCHIV4554;
	}
	public void setRelapseMalePulmonaryBCHIV4554(
			Integer relapseMalePulmonaryBCHIV4554) {
		this.relapseMalePulmonaryBCHIV4554 = relapseMalePulmonaryBCHIV4554;
	}
	public Integer getRelapseFemalePulmonaryBCHIV4554() {
		return relapseFemalePulmonaryBCHIV4554;
	}
	public void setRelapseFemalePulmonaryBCHIV4554(
			Integer relapseFemalePulmonaryBCHIV4554) {
		this.relapseFemalePulmonaryBCHIV4554 = relapseFemalePulmonaryBCHIV4554;
	}
	public Integer getRelapseMalePulmonaryBCHIV5564() {
		return relapseMalePulmonaryBCHIV5564;
	}
	public void setRelapseMalePulmonaryBCHIV5564(
			Integer relapseMalePulmonaryBCHIV5564) {
		this.relapseMalePulmonaryBCHIV5564 = relapseMalePulmonaryBCHIV5564;
	}
	public Integer getRelapseFemalePulmonaryBCHIV5564() {
		return relapseFemalePulmonaryBCHIV5564;
	}
	public void setRelapseFemalePulmonaryBCHIV5564(
			Integer relapseFemalePulmonaryBCHIV5564) {
		this.relapseFemalePulmonaryBCHIV5564 = relapseFemalePulmonaryBCHIV5564;
	}
	public Integer getRelapseMalePulmonaryBCHIV65() {
		return relapseMalePulmonaryBCHIV65;
	}
	public void setRelapseMalePulmonaryBCHIV65(Integer relapseMalePulmonaryBCHIV65) {
		this.relapseMalePulmonaryBCHIV65 = relapseMalePulmonaryBCHIV65;
	}
	public Integer getRelapseFemalePulmonaryBCHIV65() {
		return relapseFemalePulmonaryBCHIV65;
	}
	public void setRelapseFemalePulmonaryBCHIV65(
			Integer relapseFemalePulmonaryBCHIV65) {
		this.relapseFemalePulmonaryBCHIV65 = relapseFemalePulmonaryBCHIV65;
	}
	public Integer getRelapseMalePulmonaryBCHIV() {
		return relapseMalePulmonaryBCHIV;
	}
	public void setRelapseMalePulmonaryBCHIV(Integer relapseMalePulmonaryBCHIV) {
		this.relapseMalePulmonaryBCHIV = relapseMalePulmonaryBCHIV;
	}
	public Integer getRelapseFemalePulmonaryBCHIV() {
		return relapseFemalePulmonaryBCHIV;
	}
	public void setRelapseFemalePulmonaryBCHIV(Integer relapseFemalePulmonaryBCHIV) {
		this.relapseFemalePulmonaryBCHIV = relapseFemalePulmonaryBCHIV;
	}
	public Integer getRelapsePulmonaryBCHIV() {
		return relapsePulmonaryBCHIV;
	}
	public void setRelapsePulmonaryBCHIV(Integer relapsePulmonaryBCHIV) {
		this.relapsePulmonaryBCHIV = relapsePulmonaryBCHIV;
	}
	public Integer getRelapseMalePulmonaryCD04() {
		return relapseMalePulmonaryCD04;
	}
	public void setRelapseMalePulmonaryCD04(Integer relapseMalePulmonaryCD04) {
		this.relapseMalePulmonaryCD04 = relapseMalePulmonaryCD04;
	}
	public Integer getRelapseFemalePulmonaryCD04() {
		return relapseFemalePulmonaryCD04;
	}
	public void setRelapseFemalePulmonaryCD04(Integer relapseFemalePulmonaryCD04) {
		this.relapseFemalePulmonaryCD04 = relapseFemalePulmonaryCD04;
	}
	public Integer getRelapseMalePulmonaryCD0514() {
		return relapseMalePulmonaryCD0514;
	}
	public void setRelapseMalePulmonaryCD0514(Integer relapseMalePulmonaryCD0514) {
		this.relapseMalePulmonaryCD0514 = relapseMalePulmonaryCD0514;
	}
	public Integer getRelapseFemalePulmonaryCD0514() {
		return relapseFemalePulmonaryCD0514;
	}
	public void setRelapseFemalePulmonaryCD0514(Integer relapseFemalePulmonaryCD0514) {
		this.relapseFemalePulmonaryCD0514 = relapseFemalePulmonaryCD0514;
	}
	public Integer getRelapseMalePulmonaryCD1517() {
		return relapseMalePulmonaryCD1517;
	}
	public void setRelapseMalePulmonaryCD1517(Integer relapseMalePulmonaryCD1517) {
		this.relapseMalePulmonaryCD1517 = relapseMalePulmonaryCD1517;
	}
	public Integer getRelapseFemalePulmonaryCD1517() {
		return relapseFemalePulmonaryCD1517;
	}
	public void setRelapseFemalePulmonaryCD1517(Integer relapseFemalePulmonaryCD1517) {
		this.relapseFemalePulmonaryCD1517 = relapseFemalePulmonaryCD1517;
	}
	public Integer getRelapseMalePulmonaryCD1819() {
		return relapseMalePulmonaryCD1819;
	}
	public void setRelapseMalePulmonaryCD1819(Integer relapseMalePulmonaryCD1819) {
		this.relapseMalePulmonaryCD1819 = relapseMalePulmonaryCD1819;
	}
	public Integer getRelapseFemalePulmonaryCD1819() {
		return relapseFemalePulmonaryCD1819;
	}
	public void setRelapseFemalePulmonaryCD1819(Integer relapseFemalePulmonaryCD1819) {
		this.relapseFemalePulmonaryCD1819 = relapseFemalePulmonaryCD1819;
	}
	public Integer getRelapseMalePulmonaryCD2024() {
		return relapseMalePulmonaryCD2024;
	}
	public void setRelapseMalePulmonaryCD2024(Integer relapseMalePulmonaryCD2024) {
		this.relapseMalePulmonaryCD2024 = relapseMalePulmonaryCD2024;
	}
	public Integer getRelapseFemalePulmonaryCD2024() {
		return relapseFemalePulmonaryCD2024;
	}
	public void setRelapseFemalePulmonaryCD2024(Integer relapseFemalePulmonaryCD2024) {
		this.relapseFemalePulmonaryCD2024 = relapseFemalePulmonaryCD2024;
	}
	public Integer getRelapseMalePulmonaryCD2534() {
		return relapseMalePulmonaryCD2534;
	}
	public void setRelapseMalePulmonaryCD2534(Integer relapseMalePulmonaryCD2534) {
		this.relapseMalePulmonaryCD2534 = relapseMalePulmonaryCD2534;
	}
	public Integer getRelapseFemalePulmonaryCD2534() {
		return relapseFemalePulmonaryCD2534;
	}
	public void setRelapseFemalePulmonaryCD2534(Integer relapseFemalePulmonaryCD2534) {
		this.relapseFemalePulmonaryCD2534 = relapseFemalePulmonaryCD2534;
	}
	public Integer getRelapseMalePulmonaryCD3544() {
		return relapseMalePulmonaryCD3544;
	}
	public void setRelapseMalePulmonaryCD3544(Integer relapseMalePulmonaryCD3544) {
		this.relapseMalePulmonaryCD3544 = relapseMalePulmonaryCD3544;
	}
	public Integer getRelapseFemalePulmonaryCD3544() {
		return relapseFemalePulmonaryCD3544;
	}
	public void setRelapseFemalePulmonaryCD3544(Integer relapseFemalePulmonaryCD3544) {
		this.relapseFemalePulmonaryCD3544 = relapseFemalePulmonaryCD3544;
	}
	public Integer getRelapseMalePulmonaryCD4554() {
		return relapseMalePulmonaryCD4554;
	}
	public void setRelapseMalePulmonaryCD4554(Integer relapseMalePulmonaryCD4554) {
		this.relapseMalePulmonaryCD4554 = relapseMalePulmonaryCD4554;
	}
	public Integer getRelapseFemalePulmonaryCD4554() {
		return relapseFemalePulmonaryCD4554;
	}
	public void setRelapseFemalePulmonaryCD4554(Integer relapseFemalePulmonaryCD4554) {
		this.relapseFemalePulmonaryCD4554 = relapseFemalePulmonaryCD4554;
	}
	public Integer getRelapseMalePulmonaryCD5564() {
		return relapseMalePulmonaryCD5564;
	}
	public void setRelapseMalePulmonaryCD5564(Integer relapseMalePulmonaryCD5564) {
		this.relapseMalePulmonaryCD5564 = relapseMalePulmonaryCD5564;
	}
	public Integer getRelapseFemalePulmonaryCD5564() {
		return relapseFemalePulmonaryCD5564;
	}
	public void setRelapseFemalePulmonaryCD5564(Integer relapseFemalePulmonaryCD5564) {
		this.relapseFemalePulmonaryCD5564 = relapseFemalePulmonaryCD5564;
	}
	public Integer getRelapseMalePulmonaryCD65() {
		return relapseMalePulmonaryCD65;
	}
	public void setRelapseMalePulmonaryCD65(Integer relapseMalePulmonaryCD65) {
		this.relapseMalePulmonaryCD65 = relapseMalePulmonaryCD65;
	}
	public Integer getRelapseFemalePulmonaryCD65() {
		return relapseFemalePulmonaryCD65;
	}
	public void setRelapseFemalePulmonaryCD65(Integer relapseFemalePulmonaryCD65) {
		this.relapseFemalePulmonaryCD65 = relapseFemalePulmonaryCD65;
	}
	public Integer getRelapseMalePulmonaryCD() {
		return relapseMalePulmonaryCD;
	}
	public void setRelapseMalePulmonaryCD(Integer relapseMalePulmonaryCD) {
		this.relapseMalePulmonaryCD = relapseMalePulmonaryCD;
	}
	public Integer getRelapseFemalePulmonaryCD() {
		return relapseFemalePulmonaryCD;
	}
	public void setRelapseFemalePulmonaryCD(Integer relapseFemalePulmonaryCD) {
		this.relapseFemalePulmonaryCD = relapseFemalePulmonaryCD;
	}
	public Integer getRelapsePulmonaryCD() {
		return relapsePulmonaryCD;
	}
	public void setRelapsePulmonaryCD(Integer relapsePulmonaryCD) {
		this.relapsePulmonaryCD = relapsePulmonaryCD;
	}
	public Integer getRelapseMalePulmonaryCDHIV04() {
		return relapseMalePulmonaryCDHIV04;
	}
	public void setRelapseMalePulmonaryCDHIV04(Integer relapseMalePulmonaryCDHIV04) {
		this.relapseMalePulmonaryCDHIV04 = relapseMalePulmonaryCDHIV04;
	}
	public Integer getRelapseFemalePulmonaryCDHIV04() {
		return relapseFemalePulmonaryCDHIV04;
	}
	public void setRelapseFemalePulmonaryCDHIV04(
			Integer relapseFemalePulmonaryCDHIV04) {
		this.relapseFemalePulmonaryCDHIV04 = relapseFemalePulmonaryCDHIV04;
	}
	public Integer getRelapseMalePulmonaryCDHIV0514() {
		return relapseMalePulmonaryCDHIV0514;
	}
	public void setRelapseMalePulmonaryCDHIV0514(
			Integer relapseMalePulmonaryCDHIV0514) {
		this.relapseMalePulmonaryCDHIV0514 = relapseMalePulmonaryCDHIV0514;
	}
	public Integer getRelapseFemalePulmonaryCDHIV0514() {
		return relapseFemalePulmonaryCDHIV0514;
	}
	public void setRelapseFemalePulmonaryCDHIV0514(
			Integer relapseFemalePulmonaryCDHIV0514) {
		this.relapseFemalePulmonaryCDHIV0514 = relapseFemalePulmonaryCDHIV0514;
	}
	public Integer getRelapseMalePulmonaryCDHIV1517() {
		return relapseMalePulmonaryCDHIV1517;
	}
	public void setRelapseMalePulmonaryCDHIV1517(
			Integer relapseMalePulmonaryCDHIV1517) {
		this.relapseMalePulmonaryCDHIV1517 = relapseMalePulmonaryCDHIV1517;
	}
	public Integer getRelapseFemalePulmonaryCDHIV1517() {
		return relapseFemalePulmonaryCDHIV1517;
	}
	public void setRelapseFemalePulmonaryCDHIV1517(
			Integer relapseFemalePulmonaryCDHIV1517) {
		this.relapseFemalePulmonaryCDHIV1517 = relapseFemalePulmonaryCDHIV1517;
	}
	public Integer getRelapseMalePulmonaryCDHIV1819() {
		return relapseMalePulmonaryCDHIV1819;
	}
	public void setRelapseMalePulmonaryCDHIV1819(
			Integer relapseMalePulmonaryCDHIV1819) {
		this.relapseMalePulmonaryCDHIV1819 = relapseMalePulmonaryCDHIV1819;
	}
	public Integer getRelapseFemalePulmonaryCDHIV1819() {
		return relapseFemalePulmonaryCDHIV1819;
	}
	public void setRelapseFemalePulmonaryCDHIV1819(
			Integer relapseFemalePulmonaryCDHIV1819) {
		this.relapseFemalePulmonaryCDHIV1819 = relapseFemalePulmonaryCDHIV1819;
	}
	public Integer getRelapseMalePulmonaryCDHIV2024() {
		return relapseMalePulmonaryCDHIV2024;
	}
	public void setRelapseMalePulmonaryCDHIV2024(
			Integer relapseMalePulmonaryCDHIV2024) {
		this.relapseMalePulmonaryCDHIV2024 = relapseMalePulmonaryCDHIV2024;
	}
	public Integer getRelapseFemalePulmonaryCDHIV2024() {
		return relapseFemalePulmonaryCDHIV2024;
	}
	public void setRelapseFemalePulmonaryCDHIV2024(
			Integer relapseFemalePulmonaryCDHIV2024) {
		this.relapseFemalePulmonaryCDHIV2024 = relapseFemalePulmonaryCDHIV2024;
	}
	public Integer getRelapseMalePulmonaryCDHIV2534() {
		return relapseMalePulmonaryCDHIV2534;
	}
	public void setRelapseMalePulmonaryCDHIV2534(
			Integer relapseMalePulmonaryCDHIV2534) {
		this.relapseMalePulmonaryCDHIV2534 = relapseMalePulmonaryCDHIV2534;
	}
	public Integer getRelapseFemalePulmonaryCDHIV2534() {
		return relapseFemalePulmonaryCDHIV2534;
	}
	public void setRelapseFemalePulmonaryCDHIV2534(
			Integer relapseFemalePulmonaryCDHIV2534) {
		this.relapseFemalePulmonaryCDHIV2534 = relapseFemalePulmonaryCDHIV2534;
	}
	public Integer getRelapseMalePulmonaryCDHIV3544() {
		return relapseMalePulmonaryCDHIV3544;
	}
	public void setRelapseMalePulmonaryCDHIV3544(
			Integer relapseMalePulmonaryCDHIV3544) {
		this.relapseMalePulmonaryCDHIV3544 = relapseMalePulmonaryCDHIV3544;
	}
	public Integer getRelapseFemalePulmonaryCDHIV3544() {
		return relapseFemalePulmonaryCDHIV3544;
	}
	public void setRelapseFemalePulmonaryCDHIV3544(
			Integer relapseFemalePulmonaryCDHIV3544) {
		this.relapseFemalePulmonaryCDHIV3544 = relapseFemalePulmonaryCDHIV3544;
	}
	public Integer getRelapseMalePulmonaryCDHIV4554() {
		return relapseMalePulmonaryCDHIV4554;
	}
	public void setRelapseMalePulmonaryCDHIV4554(
			Integer relapseMalePulmonaryCDHIV4554) {
		this.relapseMalePulmonaryCDHIV4554 = relapseMalePulmonaryCDHIV4554;
	}
	public Integer getRelapseFemalePulmonaryCDHIV4554() {
		return relapseFemalePulmonaryCDHIV4554;
	}
	public void setRelapseFemalePulmonaryCDHIV4554(
			Integer relapseFemalePulmonaryCDHIV4554) {
		this.relapseFemalePulmonaryCDHIV4554 = relapseFemalePulmonaryCDHIV4554;
	}
	public Integer getRelapseMalePulmonaryCDHIV5564() {
		return relapseMalePulmonaryCDHIV5564;
	}
	public void setRelapseMalePulmonaryCDHIV5564(
			Integer relapseMalePulmonaryCDHIV5564) {
		this.relapseMalePulmonaryCDHIV5564 = relapseMalePulmonaryCDHIV5564;
	}
	public Integer getRelapseFemalePulmonaryCDHIV5564() {
		return relapseFemalePulmonaryCDHIV5564;
	}
	public void setRelapseFemalePulmonaryCDHIV5564(
			Integer relapseFemalePulmonaryCDHIV5564) {
		this.relapseFemalePulmonaryCDHIV5564 = relapseFemalePulmonaryCDHIV5564;
	}
	public Integer getRelapseMalePulmonaryCDHIV65() {
		return relapseMalePulmonaryCDHIV65;
	}
	public void setRelapseMalePulmonaryCDHIV65(Integer relapseMalePulmonaryCDHIV65) {
		this.relapseMalePulmonaryCDHIV65 = relapseMalePulmonaryCDHIV65;
	}
	public Integer getRelapseFemalePulmonaryCDHIV65() {
		return relapseFemalePulmonaryCDHIV65;
	}
	public void setRelapseFemalePulmonaryCDHIV65(
			Integer relapseFemalePulmonaryCDHIV65) {
		this.relapseFemalePulmonaryCDHIV65 = relapseFemalePulmonaryCDHIV65;
	}
	public Integer getRelapseMalePulmonaryCDHIV() {
		return relapseMalePulmonaryCDHIV;
	}
	public void setRelapseMalePulmonaryCDHIV(Integer relapseMalePulmonaryCDHIV) {
		this.relapseMalePulmonaryCDHIV = relapseMalePulmonaryCDHIV;
	}
	public Integer getRelapseFemalePulmonaryCDHIV() {
		return relapseFemalePulmonaryCDHIV;
	}
	public void setRelapseFemalePulmonaryCDHIV(Integer relapseFemalePulmonaryCDHIV) {
		this.relapseFemalePulmonaryCDHIV = relapseFemalePulmonaryCDHIV;
	}
	public Integer getRelapsePulmonaryCDHIV() {
		return relapsePulmonaryCDHIV;
	}
	public void setRelapsePulmonaryCDHIV(Integer relapsePulmonaryCDHIV) {
		this.relapsePulmonaryCDHIV = relapsePulmonaryCDHIV;
	}
	public Integer getRelapseMaleExtrapulmonary04() {
		return relapseMaleExtrapulmonary04;
	}
	public void setRelapseMaleExtrapulmonary04(Integer relapseMaleExtrapulmonary04) {
		this.relapseMaleExtrapulmonary04 = relapseMaleExtrapulmonary04;
	}
	public Integer getRelapseFemaleExtrapulmonary04() {
		return relapseFemaleExtrapulmonary04;
	}
	public void setRelapseFemaleExtrapulmonary04(
			Integer relapseFemaleExtrapulmonary04) {
		this.relapseFemaleExtrapulmonary04 = relapseFemaleExtrapulmonary04;
	}
	public Integer getRelapseMaleExtrapulmonary0514() {
		return relapseMaleExtrapulmonary0514;
	}
	public void setRelapseMaleExtrapulmonary0514(
			Integer relapseMaleExtrapulmonary0514) {
		this.relapseMaleExtrapulmonary0514 = relapseMaleExtrapulmonary0514;
	}
	public Integer getRelapseFemaleExtrapulmonary0514() {
		return relapseFemaleExtrapulmonary0514;
	}
	public void setRelapseFemaleExtrapulmonary0514(
			Integer relapseFemaleExtrapulmonary0514) {
		this.relapseFemaleExtrapulmonary0514 = relapseFemaleExtrapulmonary0514;
	}
	public Integer getRelapseMaleExtrapulmonary1517() {
		return relapseMaleExtrapulmonary1517;
	}
	public void setRelapseMaleExtrapulmonary1517(
			Integer relapseMaleExtrapulmonary1517) {
		this.relapseMaleExtrapulmonary1517 = relapseMaleExtrapulmonary1517;
	}
	public Integer getRelapseFemaleExtrapulmonary1517() {
		return relapseFemaleExtrapulmonary1517;
	}
	public void setRelapseFemaleExtrapulmonary1517(
			Integer relapseFemaleExtrapulmonary1517) {
		this.relapseFemaleExtrapulmonary1517 = relapseFemaleExtrapulmonary1517;
	}
	public Integer getRelapseMaleExtrapulmonary1819() {
		return relapseMaleExtrapulmonary1819;
	}
	public void setRelapseMaleExtrapulmonary1819(
			Integer relapseMaleExtrapulmonary1819) {
		this.relapseMaleExtrapulmonary1819 = relapseMaleExtrapulmonary1819;
	}
	public Integer getRelapseFemaleExtrapulmonary1819() {
		return relapseFemaleExtrapulmonary1819;
	}
	public void setRelapseFemaleExtrapulmonary1819(
			Integer relapseFemaleExtrapulmonary1819) {
		this.relapseFemaleExtrapulmonary1819 = relapseFemaleExtrapulmonary1819;
	}
	public Integer getRelapseMaleExtrapulmonary2024() {
		return relapseMaleExtrapulmonary2024;
	}
	public void setRelapseMaleExtrapulmonary2024(
			Integer relapseMaleExtrapulmonary2024) {
		this.relapseMaleExtrapulmonary2024 = relapseMaleExtrapulmonary2024;
	}
	public Integer getRelapseFemaleExtrapulmonary2024() {
		return relapseFemaleExtrapulmonary2024;
	}
	public void setRelapseFemaleExtrapulmonary2024(
			Integer relapseFemaleExtrapulmonary2024) {
		this.relapseFemaleExtrapulmonary2024 = relapseFemaleExtrapulmonary2024;
	}
	public Integer getRelapseMaleExtrapulmonary2534() {
		return relapseMaleExtrapulmonary2534;
	}
	public void setRelapseMaleExtrapulmonary2534(
			Integer relapseMaleExtrapulmonary2534) {
		this.relapseMaleExtrapulmonary2534 = relapseMaleExtrapulmonary2534;
	}
	public Integer getRelapseFemaleExtrapulmonary2534() {
		return relapseFemaleExtrapulmonary2534;
	}
	public void setRelapseFemaleExtrapulmonary2534(
			Integer relapseFemaleExtrapulmonary2534) {
		this.relapseFemaleExtrapulmonary2534 = relapseFemaleExtrapulmonary2534;
	}
	public Integer getRelapseMaleExtrapulmonary3544() {
		return relapseMaleExtrapulmonary3544;
	}
	public void setRelapseMaleExtrapulmonary3544(
			Integer relapseMaleExtrapulmonary3544) {
		this.relapseMaleExtrapulmonary3544 = relapseMaleExtrapulmonary3544;
	}
	public Integer getRelapseFemaleExtrapulmonary3544() {
		return relapseFemaleExtrapulmonary3544;
	}
	public void setRelapseFemaleExtrapulmonary3544(
			Integer relapseFemaleExtrapulmonary3544) {
		this.relapseFemaleExtrapulmonary3544 = relapseFemaleExtrapulmonary3544;
	}
	public Integer getRelapseMaleExtrapulmonary4554() {
		return relapseMaleExtrapulmonary4554;
	}
	public void setRelapseMaleExtrapulmonary4554(
			Integer relapseMaleExtrapulmonary4554) {
		this.relapseMaleExtrapulmonary4554 = relapseMaleExtrapulmonary4554;
	}
	public Integer getRelapseFemaleExtrapulmonary4554() {
		return relapseFemaleExtrapulmonary4554;
	}
	public void setRelapseFemaleExtrapulmonary4554(
			Integer relapseFemaleExtrapulmonary4554) {
		this.relapseFemaleExtrapulmonary4554 = relapseFemaleExtrapulmonary4554;
	}
	public Integer getRelapseMaleExtrapulmonary5564() {
		return relapseMaleExtrapulmonary5564;
	}
	public void setRelapseMaleExtrapulmonary5564(
			Integer relapseMaleExtrapulmonary5564) {
		this.relapseMaleExtrapulmonary5564 = relapseMaleExtrapulmonary5564;
	}
	public Integer getRelapseFemaleExtrapulmonary5564() {
		return relapseFemaleExtrapulmonary5564;
	}
	public void setRelapseFemaleExtrapulmonary5564(
			Integer relapseFemaleExtrapulmonary5564) {
		this.relapseFemaleExtrapulmonary5564 = relapseFemaleExtrapulmonary5564;
	}
	public Integer getRelapseMaleExtrapulmonary65() {
		return relapseMaleExtrapulmonary65;
	}
	public void setRelapseMaleExtrapulmonary65(Integer relapseMaleExtrapulmonary65) {
		this.relapseMaleExtrapulmonary65 = relapseMaleExtrapulmonary65;
	}
	public Integer getRelapseFemaleExtrapulmonary65() {
		return relapseFemaleExtrapulmonary65;
	}
	public void setRelapseFemaleExtrapulmonary65(
			Integer relapseFemaleExtrapulmonary65) {
		this.relapseFemaleExtrapulmonary65 = relapseFemaleExtrapulmonary65;
	}
	public Integer getRelapseMaleExtrapulmonary() {
		return relapseMaleExtrapulmonary;
	}
	public void setRelapseMaleExtrapulmonary(Integer relapseMaleExtrapulmonary) {
		this.relapseMaleExtrapulmonary = relapseMaleExtrapulmonary;
	}
	public Integer getRelapseFemaleExtrapulmonary() {
		return relapseFemaleExtrapulmonary;
	}
	public void setRelapseFemaleExtrapulmonary(Integer relapseFemaleExtrapulmonary) {
		this.relapseFemaleExtrapulmonary = relapseFemaleExtrapulmonary;
	}
	public Integer getRelapseExtrapulmonary() {
		return relapseExtrapulmonary;
	}
	public void setRelapseExtrapulmonary(Integer relapseExtrapulmonary) {
		this.relapseExtrapulmonary = relapseExtrapulmonary;
	}
	public Integer getRelapseMaleExtrapulmonaryHIV04() {
		return relapseMaleExtrapulmonaryHIV04;
	}
	public void setRelapseMaleExtrapulmonaryHIV04(
			Integer relapseMaleExtrapulmonaryHIV04) {
		this.relapseMaleExtrapulmonaryHIV04 = relapseMaleExtrapulmonaryHIV04;
	}
	public Integer getRelapseFemaleExtrapulmonaryHIV04() {
		return relapseFemaleExtrapulmonaryHIV04;
	}
	public void setRelapseFemaleExtrapulmonaryHIV04(
			Integer relapseFemaleExtrapulmonaryHIV04) {
		this.relapseFemaleExtrapulmonaryHIV04 = relapseFemaleExtrapulmonaryHIV04;
	}
	public Integer getRelapseMaleExtrapulmonaryHIV0514() {
		return relapseMaleExtrapulmonaryHIV0514;
	}
	public void setRelapseMaleExtrapulmonaryHIV0514(
			Integer relapseMaleExtrapulmonaryHIV0514) {
		this.relapseMaleExtrapulmonaryHIV0514 = relapseMaleExtrapulmonaryHIV0514;
	}
	public Integer getRelapseFemaleExtrapulmonaryHIV0514() {
		return relapseFemaleExtrapulmonaryHIV0514;
	}
	public void setRelapseFemaleExtrapulmonaryHIV0514(
			Integer relapseFemaleExtrapulmonaryHIV0514) {
		this.relapseFemaleExtrapulmonaryHIV0514 = relapseFemaleExtrapulmonaryHIV0514;
	}
	public Integer getRelapseMaleExtrapulmonaryHIV1517() {
		return relapseMaleExtrapulmonaryHIV1517;
	}
	public void setRelapseMaleExtrapulmonaryHIV1517(
			Integer relapseMaleExtrapulmonaryHIV1517) {
		this.relapseMaleExtrapulmonaryHIV1517 = relapseMaleExtrapulmonaryHIV1517;
	}
	public Integer getRelapseFemaleExtrapulmonaryHIV1517() {
		return relapseFemaleExtrapulmonaryHIV1517;
	}
	public void setRelapseFemaleExtrapulmonaryHIV1517(
			Integer relapseFemaleExtrapulmonaryHIV1517) {
		this.relapseFemaleExtrapulmonaryHIV1517 = relapseFemaleExtrapulmonaryHIV1517;
	}
	public Integer getRelapseMaleExtrapulmonaryHIV1819() {
		return relapseMaleExtrapulmonaryHIV1819;
	}
	public void setRelapseMaleExtrapulmonaryHIV1819(
			Integer relapseMaleExtrapulmonaryHIV1819) {
		this.relapseMaleExtrapulmonaryHIV1819 = relapseMaleExtrapulmonaryHIV1819;
	}
	public Integer getRelapseFemaleExtrapulmonaryHIV1819() {
		return relapseFemaleExtrapulmonaryHIV1819;
	}
	public void setRelapseFemaleExtrapulmonaryHIV1819(
			Integer relapseFemaleExtrapulmonaryHIV1819) {
		this.relapseFemaleExtrapulmonaryHIV1819 = relapseFemaleExtrapulmonaryHIV1819;
	}
	public Integer getRelapseMaleExtrapulmonaryHIV2024() {
		return relapseMaleExtrapulmonaryHIV2024;
	}
	public void setRelapseMaleExtrapulmonaryHIV2024(
			Integer relapseMaleExtrapulmonaryHIV2024) {
		this.relapseMaleExtrapulmonaryHIV2024 = relapseMaleExtrapulmonaryHIV2024;
	}
	public Integer getRelapseFemaleExtrapulmonaryHIV2024() {
		return relapseFemaleExtrapulmonaryHIV2024;
	}
	public void setRelapseFemaleExtrapulmonaryHIV2024(
			Integer relapseFemaleExtrapulmonaryHIV2024) {
		this.relapseFemaleExtrapulmonaryHIV2024 = relapseFemaleExtrapulmonaryHIV2024;
	}
	public Integer getRelapseMaleExtrapulmonaryHIV2534() {
		return relapseMaleExtrapulmonaryHIV2534;
	}
	public void setRelapseMaleExtrapulmonaryHIV2534(
			Integer relapseMaleExtrapulmonaryHIV2534) {
		this.relapseMaleExtrapulmonaryHIV2534 = relapseMaleExtrapulmonaryHIV2534;
	}
	public Integer getRelapseFemaleExtrapulmonaryHIV2534() {
		return relapseFemaleExtrapulmonaryHIV2534;
	}
	public void setRelapseFemaleExtrapulmonaryHIV2534(
			Integer relapseFemaleExtrapulmonaryHIV2534) {
		this.relapseFemaleExtrapulmonaryHIV2534 = relapseFemaleExtrapulmonaryHIV2534;
	}
	public Integer getRelapseMaleExtrapulmonaryHIV3544() {
		return relapseMaleExtrapulmonaryHIV3544;
	}
	public void setRelapseMaleExtrapulmonaryHIV3544(
			Integer relapseMaleExtrapulmonaryHIV3544) {
		this.relapseMaleExtrapulmonaryHIV3544 = relapseMaleExtrapulmonaryHIV3544;
	}
	public Integer getRelapseFemaleExtrapulmonaryHIV3544() {
		return relapseFemaleExtrapulmonaryHIV3544;
	}
	public void setRelapseFemaleExtrapulmonaryHIV3544(
			Integer relapseFemaleExtrapulmonaryHIV3544) {
		this.relapseFemaleExtrapulmonaryHIV3544 = relapseFemaleExtrapulmonaryHIV3544;
	}
	public Integer getRelapseMaleExtrapulmonaryHIV4554() {
		return relapseMaleExtrapulmonaryHIV4554;
	}
	public void setRelapseMaleExtrapulmonaryHIV4554(
			Integer relapseMaleExtrapulmonaryHIV4554) {
		this.relapseMaleExtrapulmonaryHIV4554 = relapseMaleExtrapulmonaryHIV4554;
	}
	public Integer getRelapseFemaleExtrapulmonaryHIV4554() {
		return relapseFemaleExtrapulmonaryHIV4554;
	}
	public void setRelapseFemaleExtrapulmonaryHIV4554(
			Integer relapseFemaleExtrapulmonaryHIV4554) {
		this.relapseFemaleExtrapulmonaryHIV4554 = relapseFemaleExtrapulmonaryHIV4554;
	}
	public Integer getRelapseMaleExtrapulmonaryHIV5564() {
		return relapseMaleExtrapulmonaryHIV5564;
	}
	public void setRelapseMaleExtrapulmonaryHIV5564(
			Integer relapseMaleExtrapulmonaryHIV5564) {
		this.relapseMaleExtrapulmonaryHIV5564 = relapseMaleExtrapulmonaryHIV5564;
	}
	public Integer getRelapseFemaleExtrapulmonaryHIV5564() {
		return relapseFemaleExtrapulmonaryHIV5564;
	}
	public void setRelapseFemaleExtrapulmonaryHIV5564(
			Integer relapseFemaleExtrapulmonaryHIV5564) {
		this.relapseFemaleExtrapulmonaryHIV5564 = relapseFemaleExtrapulmonaryHIV5564;
	}
	public Integer getRelapseMaleExtrapulmonaryHIV65() {
		return relapseMaleExtrapulmonaryHIV65;
	}
	public void setRelapseMaleExtrapulmonaryHIV65(
			Integer relapseMaleExtrapulmonaryHIV65) {
		this.relapseMaleExtrapulmonaryHIV65 = relapseMaleExtrapulmonaryHIV65;
	}
	public Integer getRelapseFemaleExtrapulmonaryHIV65() {
		return relapseFemaleExtrapulmonaryHIV65;
	}
	public void setRelapseFemaleExtrapulmonaryHIV65(
			Integer relapseFemaleExtrapulmonaryHIV65) {
		this.relapseFemaleExtrapulmonaryHIV65 = relapseFemaleExtrapulmonaryHIV65;
	}
	public Integer getRelapseMaleExtrapulmonaryHIV() {
		return relapseMaleExtrapulmonaryHIV;
	}
	public void setRelapseMaleExtrapulmonaryHIV(Integer relapseMaleExtrapulmonaryHIV) {
		this.relapseMaleExtrapulmonaryHIV = relapseMaleExtrapulmonaryHIV;
	}
	public Integer getRelapseFemaleExtrapulmonaryHIV() {
		return relapseFemaleExtrapulmonaryHIV;
	}
	public void setRelapseFemaleExtrapulmonaryHIV(
			Integer relapseFemaleExtrapulmonaryHIV) {
		this.relapseFemaleExtrapulmonaryHIV = relapseFemaleExtrapulmonaryHIV;
	}
	public Integer getRelapseExtrapulmonaryHIV() {
		return relapseExtrapulmonaryHIV;
	}
	public void setRelapseExtrapulmonaryHIV(Integer relapseExtrapulmonaryHIV) {
		this.relapseExtrapulmonaryHIV = relapseExtrapulmonaryHIV;
	}
	public Integer getRelapseMale04() {
		return relapseMale04;
	}
	public void setRelapseMale04(Integer relapseMale04) {
		this.relapseMale04 = relapseMale04;
	}
	public Integer getRelapseFemale04() {
		return relapseFemale04;
	}
	public void setRelapseFemale04(Integer relapseFemale04) {
		this.relapseFemale04 = relapseFemale04;
	}
	public Integer getRelapseMale0514() {
		return relapseMale0514;
	}
	public void setRelapseMale0514(Integer relapseMale0514) {
		this.relapseMale0514 = relapseMale0514;
	}
	public Integer getRelapseFemale0514() {
		return relapseFemale0514;
	}
	public void setRelapseFemale0514(Integer relapseFemale0514) {
		this.relapseFemale0514 = relapseFemale0514;
	}
	public Integer getRelapseMale1517() {
		return relapseMale1517;
	}
	public void setRelapseMale1517(Integer relapseMale1517) {
		this.relapseMale1517 = relapseMale1517;
	}
	public Integer getRelapseFemale1517() {
		return relapseFemale1517;
	}
	public void setRelapseFemale1517(Integer relapseFemale1517) {
		this.relapseFemale1517 = relapseFemale1517;
	}
	public Integer getRelapseMale1819() {
		return relapseMale1819;
	}
	public void setRelapseMale1819(Integer relapseMale1819) {
		this.relapseMale1819 = relapseMale1819;
	}
	public Integer getRelapseFemale1819() {
		return relapseFemale1819;
	}
	public void setRelapseFemale1819(Integer relapseFemale1819) {
		this.relapseFemale1819 = relapseFemale1819;
	}
	public Integer getRelapseMale2024() {
		return relapseMale2024;
	}
	public void setRelapseMale2024(Integer relapseMale2024) {
		this.relapseMale2024 = relapseMale2024;
	}
	public Integer getRelapseFemale2024() {
		return relapseFemale2024;
	}
	public void setRelapseFemale2024(Integer relapseFemale2024) {
		this.relapseFemale2024 = relapseFemale2024;
	}
	public Integer getRelapseMale2534() {
		return relapseMale2534;
	}
	public void setRelapseMale2534(Integer relapseMale2534) {
		this.relapseMale2534 = relapseMale2534;
	}
	public Integer getRelapseFemale2534() {
		return relapseFemale2534;
	}
	public void setRelapseFemale2534(Integer relapseFemale2534) {
		this.relapseFemale2534 = relapseFemale2534;
	}
	public Integer getRelapseMale3544() {
		return relapseMale3544;
	}
	public void setRelapseMale3544(Integer relapseMale3544) {
		this.relapseMale3544 = relapseMale3544;
	}
	public Integer getRelapseFemale3544() {
		return relapseFemale3544;
	}
	public void setRelapseFemale3544(Integer relapseFemale3544) {
		this.relapseFemale3544 = relapseFemale3544;
	}
	public Integer getRelapseMale4554() {
		return relapseMale4554;
	}
	public void setRelapseMale4554(Integer relapseMale4554) {
		this.relapseMale4554 = relapseMale4554;
	}
	public Integer getRelapseFemale4554() {
		return relapseFemale4554;
	}
	public void setRelapseFemale4554(Integer relapseFemale4554) {
		this.relapseFemale4554 = relapseFemale4554;
	}
	public Integer getRelapseMale5564() {
		return relapseMale5564;
	}
	public void setRelapseMale5564(Integer relapseMale5564) {
		this.relapseMale5564 = relapseMale5564;
	}
	public Integer getRelapseFemale5564() {
		return relapseFemale5564;
	}
	public void setRelapseFemale5564(Integer relapseFemale5564) {
		this.relapseFemale5564 = relapseFemale5564;
	}
	public Integer getRelapseMale65() {
		return relapseMale65;
	}
	public void setRelapseMale65(Integer relapseMale65) {
		this.relapseMale65 = relapseMale65;
	}
	public Integer getRelapseFemale65() {
		return relapseFemale65;
	}
	public void setRelapseFemale65(Integer relapseFemale65) {
		this.relapseFemale65 = relapseFemale65;
	}
	public Integer getRelapseMale() {
		return relapseMale;
	}
	public void setRelapseMale(Integer relapseMale) {
		this.relapseMale = relapseMale;
	}
	public Integer getRelapseFemale() {
		return relapseFemale;
	}
	public void setRelapseFemale(Integer relapseFemale) {
		this.relapseFemale = relapseFemale;
	}
	public Integer getRelapseAll() {
		return relapseAll;
	}
	public void setRelapseAll(Integer relapseAll) {
		this.relapseAll = relapseAll;
	}
	public Integer getRelapseMaleHIV04() {
		return relapseMaleHIV04;
	}
	public void setRelapseMaleHIV04(Integer relapseMaleHIV04) {
		this.relapseMaleHIV04 = relapseMaleHIV04;
	}
	public Integer getRelapseFemaleHIV04() {
		return relapseFemaleHIV04;
	}
	public void setRelapseFemaleHIV04(Integer relapseFemaleHIV04) {
		this.relapseFemaleHIV04 = relapseFemaleHIV04;
	}
	public Integer getRelapseMaleHIV0514() {
		return relapseMaleHIV0514;
	}
	public void setRelapseMaleHIV0514(Integer relapseMaleHIV0514) {
		this.relapseMaleHIV0514 = relapseMaleHIV0514;
	}
	public Integer getRelapseFemaleHIV0514() {
		return relapseFemaleHIV0514;
	}
	public void setRelapseFemaleHIV0514(Integer relapseFemaleHIV0514) {
		this.relapseFemaleHIV0514 = relapseFemaleHIV0514;
	}
	public Integer getRelapseMaleHIV1517() {
		return relapseMaleHIV1517;
	}
	public void setRelapseMaleHIV1517(Integer relapseMaleHIV1517) {
		this.relapseMaleHIV1517 = relapseMaleHIV1517;
	}
	public Integer getRelapseFemaleHIV1517() {
		return relapseFemaleHIV1517;
	}
	public void setRelapseFemaleHIV1517(Integer relapseFemaleHIV1517) {
		this.relapseFemaleHIV1517 = relapseFemaleHIV1517;
	}
	public Integer getRelapseMaleHIV1819() {
		return relapseMaleHIV1819;
	}
	public void setRelapseMaleHIV1819(Integer relapseMaleHIV1819) {
		this.relapseMaleHIV1819 = relapseMaleHIV1819;
	}
	public Integer getRelapseFemaleHIV1819() {
		return relapseFemaleHIV1819;
	}
	public void setRelapseFemaleHIV1819(Integer relapseFemaleHIV1819) {
		this.relapseFemaleHIV1819 = relapseFemaleHIV1819;
	}
	public Integer getRelapseMaleHIV2024() {
		return relapseMaleHIV2024;
	}
	public void setRelapseMaleHIV2024(Integer relapseMaleHIV2024) {
		this.relapseMaleHIV2024 = relapseMaleHIV2024;
	}
	public Integer getRelapseFemaleHIV2024() {
		return relapseFemaleHIV2024;
	}
	public void setRelapseFemaleHIV2024(Integer relapseFemaleHIV2024) {
		this.relapseFemaleHIV2024 = relapseFemaleHIV2024;
	}
	public Integer getRelapseMaleHIV2534() {
		return relapseMaleHIV2534;
	}
	public void setRelapseMaleHIV2534(Integer relapseMaleHIV2534) {
		this.relapseMaleHIV2534 = relapseMaleHIV2534;
	}
	public Integer getRelapseFemaleHIV2534() {
		return relapseFemaleHIV2534;
	}
	public void setRelapseFemaleHIV2534(Integer relapseFemaleHIV2534) {
		this.relapseFemaleHIV2534 = relapseFemaleHIV2534;
	}
	public Integer getRelapseMaleHIV3544() {
		return relapseMaleHIV3544;
	}
	public void setRelapseMaleHIV3544(Integer relapseMaleHIV3544) {
		this.relapseMaleHIV3544 = relapseMaleHIV3544;
	}
	public Integer getRelapseFemaleHIV3544() {
		return relapseFemaleHIV3544;
	}
	public void setRelapseFemaleHIV3544(Integer relapseFemaleHIV3544) {
		this.relapseFemaleHIV3544 = relapseFemaleHIV3544;
	}
	public Integer getRelapseMaleHIV4554() {
		return relapseMaleHIV4554;
	}
	public void setRelapseMaleHIV4554(Integer relapseMaleHIV4554) {
		this.relapseMaleHIV4554 = relapseMaleHIV4554;
	}
	public Integer getRelapseFemaleHIV4554() {
		return relapseFemaleHIV4554;
	}
	public void setRelapseFemaleHIV4554(Integer relapseFemaleHIV4554) {
		this.relapseFemaleHIV4554 = relapseFemaleHIV4554;
	}
	public Integer getRelapseMaleHIV5564() {
		return relapseMaleHIV5564;
	}
	public void setRelapseMaleHIV5564(Integer relapseMaleHIV5564) {
		this.relapseMaleHIV5564 = relapseMaleHIV5564;
	}
	public Integer getRelapseFemaleHIV5564() {
		return relapseFemaleHIV5564;
	}
	public void setRelapseFemaleHIV5564(Integer relapseFemaleHIV5564) {
		this.relapseFemaleHIV5564 = relapseFemaleHIV5564;
	}
	public Integer getRelapseMaleHIV65() {
		return relapseMaleHIV65;
	}
	public void setRelapseMaleHIV65(Integer relapseMaleHIV65) {
		this.relapseMaleHIV65 = relapseMaleHIV65;
	}
	public Integer getRelapseFemaleHIV65() {
		return relapseFemaleHIV65;
	}
	public void setRelapseFemaleHIV65(Integer relapseFemaleHIV65) {
		this.relapseFemaleHIV65 = relapseFemaleHIV65;
	}
	public Integer getRelapseMaleHIV() {
		return relapseMaleHIV;
	}
	public void setRelapseMaleHIV(Integer relapseMaleHIV) {
		this.relapseMaleHIV = relapseMaleHIV;
	}
	public Integer getRelapseFemaleHIV() {
		return relapseFemaleHIV;
	}
	public void setRelapseFemaleHIV(Integer relapseFemaleHIV) {
		this.relapseFemaleHIV = relapseFemaleHIV;
	}
	public Integer getRelapseAllHIV() {
		return relapseAllHIV;
	}
	public void setRelapseAllHIV(Integer relapseAllHIV) {
		this.relapseAllHIV = relapseAllHIV;
	}

	public Integer getHivPositive() {
		return hivPositive;
	}

	public void setHivPositive(Integer hivPositive) {
		this.hivPositive = hivPositive;
	}

	public Integer getHivTested() {
		return hivTested;
	}

	public void setHivTested(Integer hivTested) {
		this.hivTested = hivTested;
	}

	public Integer getArtStarted() {
		return artStarted;
	}

	public void setArtStarted(Integer artStarted) {
		this.artStarted = artStarted;
	}

	public Integer getPctStarted() {
		return pctStarted;
	}

	public void setPctStarted(Integer pctStarted) {
		this.pctStarted = pctStarted;
	}

	public Integer getFailureMalePulmonaryBC() {
		return failureMalePulmonaryBC;
	}

	public void setFailureMalePulmonaryBC(Integer failureMalePulmonaryBC) {
		this.failureMalePulmonaryBC = failureMalePulmonaryBC;
	}

	public Integer getFailureFemalePulmonaryBC() {
		return failureFemalePulmonaryBC;
	}

	public void setFailureFemalePulmonaryBC(Integer failureFemalePulmonaryBC) {
		this.failureFemalePulmonaryBC = failureFemalePulmonaryBC;
	}

	public Integer getFailurePulmonaryBC() {
		return failurePulmonaryBC;
	}

	public void setFailurePulmonaryBC(Integer failurePulmonaryBC) {
		this.failurePulmonaryBC = failurePulmonaryBC;
	}

	public Integer getFailureMalePulmonaryBCHIV() {
		return failureMalePulmonaryBCHIV;
	}

	public void setFailureMalePulmonaryBCHIV(Integer failureMalePulmonaryBCHIV) {
		this.failureMalePulmonaryBCHIV = failureMalePulmonaryBCHIV;
	}

	public Integer getFailureFemalePulmonaryBCHIV() {
		return failureFemalePulmonaryBCHIV;
	}

	public void setFailureFemalePulmonaryBCHIV(Integer failureFemalePulmonaryBCHIV) {
		this.failureFemalePulmonaryBCHIV = failureFemalePulmonaryBCHIV;
	}

	public Integer getFailurePulmonaryBCHIV() {
		return failurePulmonaryBCHIV;
	}

	public void setFailurePulmonaryBCHIV(Integer failurePulmonaryBCHIV) {
		this.failurePulmonaryBCHIV = failurePulmonaryBCHIV;
	}

	public Integer getFailureMalePulmonaryCD() {
		return failureMalePulmonaryCD;
	}

	public void setFailureMalePulmonaryCD(Integer failureMalePulmonaryCD) {
		this.failureMalePulmonaryCD = failureMalePulmonaryCD;
	}

	public Integer getFailureFemalePulmonaryCD() {
		return failureFemalePulmonaryCD;
	}

	public void setFailureFemalePulmonaryCD(Integer failureFemalePulmonaryCD) {
		this.failureFemalePulmonaryCD = failureFemalePulmonaryCD;
	}

	public Integer getFailurePulmonaryCD() {
		return failurePulmonaryCD;
	}

	public void setFailurePulmonaryCD(Integer failurePulmonaryCD) {
		this.failurePulmonaryCD = failurePulmonaryCD;
	}

	public Integer getFailureMalePulmonaryCDHIV() {
		return failureMalePulmonaryCDHIV;
	}

	public void setFailureMalePulmonaryCDHIV(Integer failureMalePulmonaryCDHIV) {
		this.failureMalePulmonaryCDHIV = failureMalePulmonaryCDHIV;
	}

	public Integer getFailureFemalePulmonaryCDHIV() {
		return failureFemalePulmonaryCDHIV;
	}

	public void setFailureFemalePulmonaryCDHIV(Integer failureFemalePulmonaryCDHIV) {
		this.failureFemalePulmonaryCDHIV = failureFemalePulmonaryCDHIV;
	}

	public Integer getFailurePulmonaryCDHIV() {
		return failurePulmonaryCDHIV;
	}

	public void setFailurePulmonaryCDHIV(Integer failurePulmonaryCDHIV) {
		this.failurePulmonaryCDHIV = failurePulmonaryCDHIV;
	}

	public Integer getFailureMaleExtrapulmonary() {
		return failureMaleExtrapulmonary;
	}

	public void setFailureMaleExtrapulmonary(Integer failureMaleExtrapulmonary) {
		this.failureMaleExtrapulmonary = failureMaleExtrapulmonary;
	}

	public Integer getFailureFemaleExtrapulmonary() {
		return failureFemaleExtrapulmonary;
	}

	public void setFailureFemaleExtrapulmonary(Integer failureFemaleExtrapulmonary) {
		this.failureFemaleExtrapulmonary = failureFemaleExtrapulmonary;
	}

	public Integer getFailureExtrapulmonary() {
		return failureExtrapulmonary;
	}

	public void setFailureExtrapulmonary(Integer failureExtrapulmonary) {
		this.failureExtrapulmonary = failureExtrapulmonary;
	}

	public Integer getFailureMaleExtrapulmonaryHIV() {
		return failureMaleExtrapulmonaryHIV;
	}

	public void setFailureMaleExtrapulmonaryHIV(Integer failureMaleExtrapulmonaryHIV) {
		this.failureMaleExtrapulmonaryHIV = failureMaleExtrapulmonaryHIV;
	}

	public Integer getFailureFemaleExtrapulmonaryHIV() {
		return failureFemaleExtrapulmonaryHIV;
	}

	public void setFailureFemaleExtrapulmonaryHIV(
			Integer failureFemaleExtrapulmonaryHIV) {
		this.failureFemaleExtrapulmonaryHIV = failureFemaleExtrapulmonaryHIV;
	}

	public Integer getFailureExtrapulmonaryHIV() {
		return failureExtrapulmonaryHIV;
	}

	public void setFailureExtrapulmonaryHIV(Integer failureExtrapulmonaryHIV) {
		this.failureExtrapulmonaryHIV = failureExtrapulmonaryHIV;
	}

	public Integer getFailureMale() {
		return failureMale;
	}

	public void setFailureMale(Integer failureMale) {
		this.failureMale = failureMale;
	}

	public Integer getFailureFemale() {
		return failureFemale;
	}

	public void setFailureFemale(Integer failureFemale) {
		this.failureFemale = failureFemale;
	}

	public Integer getFailureAll() {
		return failureAll;
	}

	public void setFailureAll(Integer failureAll) {
		this.failureAll = failureAll;
	}

	public Integer getFailureMaleHIV() {
		return failureMaleHIV;
	}

	public void setFailureMaleHIV(Integer failureMaleHIV) {
		this.failureMaleHIV = failureMaleHIV;
	}

	public Integer getFailureFemaleHIV() {
		return failureFemaleHIV;
	}

	public void setFailureFemaleHIV(Integer failureFemaleHIV) {
		this.failureFemaleHIV = failureFemaleHIV;
	}

	public Integer getFailureAllHIV() {
		return failureAllHIV;
	}

	public void setFailureAllHIV(Integer failureAllHIV) {
		this.failureAllHIV = failureAllHIV;
	}

	public Integer getDefaultMalePulmonaryBC() {
		return defaultMalePulmonaryBC;
	}

	public void setDefaultMalePulmonaryBC(Integer defaultMalePulmonaryBC) {
		this.defaultMalePulmonaryBC = defaultMalePulmonaryBC;
	}

	public Integer getDefaultFemalePulmonaryBC() {
		return defaultFemalePulmonaryBC;
	}

	public void setDefaultFemalePulmonaryBC(Integer defaultFemalePulmonaryBC) {
		this.defaultFemalePulmonaryBC = defaultFemalePulmonaryBC;
	}

	public Integer getDefaultPulmonaryBC() {
		return defaultPulmonaryBC;
	}

	public void setDefaultPulmonaryBC(Integer defaultPulmonaryBC) {
		this.defaultPulmonaryBC = defaultPulmonaryBC;
	}

	public Integer getDefaultMalePulmonaryBCHIV() {
		return defaultMalePulmonaryBCHIV;
	}

	public void setDefaultMalePulmonaryBCHIV(Integer defaultMalePulmonaryBCHIV) {
		this.defaultMalePulmonaryBCHIV = defaultMalePulmonaryBCHIV;
	}

	public Integer getDefaultFemalePulmonaryBCHIV() {
		return defaultFemalePulmonaryBCHIV;
	}

	public void setDefaultFemalePulmonaryBCHIV(Integer defaultFemalePulmonaryBCHIV) {
		this.defaultFemalePulmonaryBCHIV = defaultFemalePulmonaryBCHIV;
	}

	public Integer getDefaultPulmonaryBCHIV() {
		return defaultPulmonaryBCHIV;
	}

	public void setDefaultPulmonaryBCHIV(Integer defaultPulmonaryBCHIV) {
		this.defaultPulmonaryBCHIV = defaultPulmonaryBCHIV;
	}

	public Integer getDefaultMalePulmonaryCD() {
		return defaultMalePulmonaryCD;
	}

	public void setDefaultMalePulmonaryCD(Integer defaultMalePulmonaryCD) {
		this.defaultMalePulmonaryCD = defaultMalePulmonaryCD;
	}

	public Integer getDefaultFemalePulmonaryCD() {
		return defaultFemalePulmonaryCD;
	}

	public void setDefaultFemalePulmonaryCD(Integer defaultFemalePulmonaryCD) {
		this.defaultFemalePulmonaryCD = defaultFemalePulmonaryCD;
	}

	public Integer getDefaultPulmonaryCD() {
		return defaultPulmonaryCD;
	}

	public void setDefaultPulmonaryCD(Integer defaultPulmonaryCD) {
		this.defaultPulmonaryCD = defaultPulmonaryCD;
	}

	public Integer getDefaultMalePulmonaryCDHIV() {
		return defaultMalePulmonaryCDHIV;
	}

	public void setDefaultMalePulmonaryCDHIV(Integer defaultMalePulmonaryCDHIV) {
		this.defaultMalePulmonaryCDHIV = defaultMalePulmonaryCDHIV;
	}

	public Integer getDefaultFemalePulmonaryCDHIV() {
		return defaultFemalePulmonaryCDHIV;
	}

	public void setDefaultFemalePulmonaryCDHIV(Integer defaultFemalePulmonaryCDHIV) {
		this.defaultFemalePulmonaryCDHIV = defaultFemalePulmonaryCDHIV;
	}

	public Integer getDefaultPulmonaryCDHIV() {
		return defaultPulmonaryCDHIV;
	}

	public void setDefaultPulmonaryCDHIV(Integer defaultPulmonaryCDHIV) {
		this.defaultPulmonaryCDHIV = defaultPulmonaryCDHIV;
	}

	public Integer getDefaultMaleExtrapulmonary() {
		return defaultMaleExtrapulmonary;
	}

	public void setDefaultMaleExtrapulmonary(Integer defaultMaleExtrapulmonary) {
		this.defaultMaleExtrapulmonary = defaultMaleExtrapulmonary;
	}

	public Integer getDefaultFemaleExtrapulmonary() {
		return defaultFemaleExtrapulmonary;
	}

	public void setDefaultFemaleExtrapulmonary(Integer defaultFemaleExtrapulmonary) {
		this.defaultFemaleExtrapulmonary = defaultFemaleExtrapulmonary;
	}

	public Integer getDefaultExtrapulmonary() {
		return defaultExtrapulmonary;
	}

	public void setDefaultExtrapulmonary(Integer defaultExtrapulmonary) {
		this.defaultExtrapulmonary = defaultExtrapulmonary;
	}

	public Integer getDefaultMaleExtrapulmonaryHIV() {
		return defaultMaleExtrapulmonaryHIV;
	}

	public void setDefaultMaleExtrapulmonaryHIV(Integer defaultMaleExtrapulmonaryHIV) {
		this.defaultMaleExtrapulmonaryHIV = defaultMaleExtrapulmonaryHIV;
	}

	public Integer getDefaultFemaleExtrapulmonaryHIV() {
		return defaultFemaleExtrapulmonaryHIV;
	}

	public void setDefaultFemaleExtrapulmonaryHIV(
			Integer defaultFemaleExtrapulmonaryHIV) {
		this.defaultFemaleExtrapulmonaryHIV = defaultFemaleExtrapulmonaryHIV;
	}

	public Integer getDefaultExtrapulmonaryHIV() {
		return defaultExtrapulmonaryHIV;
	}

	public void setDefaultExtrapulmonaryHIV(Integer defaultExtrapulmonaryHIV) {
		this.defaultExtrapulmonaryHIV = defaultExtrapulmonaryHIV;
	}

	public Integer getDefaultMale() {
		return defaultMale;
	}

	public void setDefaultMale(Integer defaultMale) {
		this.defaultMale = defaultMale;
	}

	public Integer getDefaultFemale() {
		return defaultFemale;
	}

	public void setDefaultFemale(Integer defaultFemale) {
		this.defaultFemale = defaultFemale;
	}

	public Integer getDefaultAll() {
		return defaultAll;
	}

	public void setDefaultAll(Integer defaultAll) {
		this.defaultAll = defaultAll;
	}

	public Integer getDefaultMaleHIV() {
		return defaultMaleHIV;
	}

	public void setDefaultMaleHIV(Integer defaultMaleHIV) {
		this.defaultMaleHIV = defaultMaleHIV;
	}

	public Integer getDefaultFemaleHIV() {
		return defaultFemaleHIV;
	}

	public void setDefaultFemaleHIV(Integer defaultFemaleHIV) {
		this.defaultFemaleHIV = defaultFemaleHIV;
	}

	public Integer getDefaultAllHIV() {
		return defaultAllHIV;
	}

	public void setDefaultAllHIV(Integer defaultAllHIV) {
		this.defaultAllHIV = defaultAllHIV;
	}

	public Integer getOtherMalePulmonaryBC() {
		return otherMalePulmonaryBC;
	}

	public void setOtherMalePulmonaryBC(Integer otherMalePulmonaryBC) {
		this.otherMalePulmonaryBC = otherMalePulmonaryBC;
	}

	public Integer getOtherFemalePulmonaryBC() {
		return otherFemalePulmonaryBC;
	}

	public void setOtherFemalePulmonaryBC(Integer otherFemalePulmonaryBC) {
		this.otherFemalePulmonaryBC = otherFemalePulmonaryBC;
	}

	public Integer getOtherPulmonaryBC() {
		return otherPulmonaryBC;
	}

	public void setOtherPulmonaryBC(Integer otherPulmonaryBC) {
		this.otherPulmonaryBC = otherPulmonaryBC;
	}

	public Integer getOtherMalePulmonaryBCHIV() {
		return otherMalePulmonaryBCHIV;
	}

	public void setOtherMalePulmonaryBCHIV(Integer otherMalePulmonaryBCHIV) {
		this.otherMalePulmonaryBCHIV = otherMalePulmonaryBCHIV;
	}

	public Integer getOtherFemalePulmonaryBCHIV() {
		return otherFemalePulmonaryBCHIV;
	}

	public void setOtherFemalePulmonaryBCHIV(Integer otherFemalePulmonaryBCHIV) {
		this.otherFemalePulmonaryBCHIV = otherFemalePulmonaryBCHIV;
	}

	public Integer getOtherPulmonaryBCHIV() {
		return otherPulmonaryBCHIV;
	}

	public void setOtherPulmonaryBCHIV(Integer otherPulmonaryBCHIV) {
		this.otherPulmonaryBCHIV = otherPulmonaryBCHIV;
	}

	public Integer getOtherMalePulmonaryCD() {
		return otherMalePulmonaryCD;
	}

	public void setOtherMalePulmonaryCD(Integer otherMalePulmonaryCD) {
		this.otherMalePulmonaryCD = otherMalePulmonaryCD;
	}

	public Integer getOtherFemalePulmonaryCD() {
		return otherFemalePulmonaryCD;
	}

	public void setOtherFemalePulmonaryCD(Integer otherFemalePulmonaryCD) {
		this.otherFemalePulmonaryCD = otherFemalePulmonaryCD;
	}

	public Integer getOtherPulmonaryCD() {
		return otherPulmonaryCD;
	}

	public void setOtherPulmonaryCD(Integer otherPulmonaryCD) {
		this.otherPulmonaryCD = otherPulmonaryCD;
	}

	public Integer getOtherMalePulmonaryCDHIV() {
		return otherMalePulmonaryCDHIV;
	}

	public void setOtherMalePulmonaryCDHIV(Integer otherMalePulmonaryCDHIV) {
		this.otherMalePulmonaryCDHIV = otherMalePulmonaryCDHIV;
	}

	public Integer getOtherFemalePulmonaryCDHIV() {
		return otherFemalePulmonaryCDHIV;
	}

	public void setOtherFemalePulmonaryCDHIV(Integer otherFemalePulmonaryCDHIV) {
		this.otherFemalePulmonaryCDHIV = otherFemalePulmonaryCDHIV;
	}

	public Integer getOtherPulmonaryCDHIV() {
		return otherPulmonaryCDHIV;
	}

	public void setOtherPulmonaryCDHIV(Integer otherPulmonaryCDHIV) {
		this.otherPulmonaryCDHIV = otherPulmonaryCDHIV;
	}

	public Integer getOtherMaleExtrapulmonary() {
		return otherMaleExtrapulmonary;
	}

	public void setOtherMaleExtrapulmonary(Integer otherMaleExtrapulmonary) {
		this.otherMaleExtrapulmonary = otherMaleExtrapulmonary;
	}

	public Integer getOtherFemaleExtrapulmonary() {
		return otherFemaleExtrapulmonary;
	}

	public void setOtherFemaleExtrapulmonary(Integer otherFemaleExtrapulmonary) {
		this.otherFemaleExtrapulmonary = otherFemaleExtrapulmonary;
	}

	public Integer getOtherExtrapulmonary() {
		return otherExtrapulmonary;
	}

	public void setOtherExtrapulmonary(Integer otherExtrapulmonary) {
		this.otherExtrapulmonary = otherExtrapulmonary;
	}

	public Integer getOtherMaleExtrapulmonaryHIV() {
		return otherMaleExtrapulmonaryHIV;
	}

	public void setOtherMaleExtrapulmonaryHIV(Integer otherMaleExtrapulmonaryHIV) {
		this.otherMaleExtrapulmonaryHIV = otherMaleExtrapulmonaryHIV;
	}

	public Integer getOtherFemaleExtrapulmonaryHIV() {
		return otherFemaleExtrapulmonaryHIV;
	}

	public void setOtherFemaleExtrapulmonaryHIV(Integer otherFemaleExtrapulmonaryHIV) {
		this.otherFemaleExtrapulmonaryHIV = otherFemaleExtrapulmonaryHIV;
	}

	public Integer getOtherExtrapulmonaryHIV() {
		return otherExtrapulmonaryHIV;
	}

	public void setOtherExtrapulmonaryHIV(Integer otherExtrapulmonaryHIV) {
		this.otherExtrapulmonaryHIV = otherExtrapulmonaryHIV;
	}

	public Integer getOtherMale() {
		return otherMale;
	}

	public void setOtherMale(Integer otherMale) {
		this.otherMale = otherMale;
	}

	public Integer getOtherFemale() {
		return otherFemale;
	}

	public void setOtherFemale(Integer otherFemale) {
		this.otherFemale = otherFemale;
	}

	public Integer getOtherAll() {
		return otherAll;
	}

	public void setOtherAll(Integer otherAll) {
		this.otherAll = otherAll;
	}

	public Integer getOtherMaleHIV() {
		return otherMaleHIV;
	}

	public void setOtherMaleHIV(Integer otherMaleHIV) {
		this.otherMaleHIV = otherMaleHIV;
	}

	public Integer getOtherFemaleHIV() {
		return otherFemaleHIV;
	}

	public void setOtherFemaleHIV(Integer otherFemaleHIV) {
		this.otherFemaleHIV = otherFemaleHIV;
	}

	public Integer getOtherAllHIV() {
		return otherAllHIV;
	}

	public void setOtherAllHIV(Integer otherAllHIV) {
		this.otherAllHIV = otherAllHIV;
	}

	public Integer getRetreatmentMalePulmonaryBC() {
		return retreatmentMalePulmonaryBC;
	}

	public void setRetreatmentMalePulmonaryBC(Integer retreatmentMalePulmonaryBC) {
		this.retreatmentMalePulmonaryBC = retreatmentMalePulmonaryBC;
	}

	public Integer getRetreatmentFemalePulmonaryBC() {
		return retreatmentFemalePulmonaryBC;
	}

	public void setRetreatmentFemalePulmonaryBC(Integer retreatmentFemalePulmonaryBC) {
		this.retreatmentFemalePulmonaryBC = retreatmentFemalePulmonaryBC;
	}

	public Integer getRetreatmentPulmonaryBC() {
		return retreatmentPulmonaryBC;
	}

	public void setRetreatmentPulmonaryBC(Integer retreatmentPulmonaryBC) {
		this.retreatmentPulmonaryBC = retreatmentPulmonaryBC;
	}

	public Integer getRetreatmentMalePulmonaryBCHIV() {
		return retreatmentMalePulmonaryBCHIV;
	}

	public void setRetreatmentMalePulmonaryBCHIV(
			Integer retreatmentMalePulmonaryBCHIV) {
		this.retreatmentMalePulmonaryBCHIV = retreatmentMalePulmonaryBCHIV;
	}

	public Integer getRetreatmentFemalePulmonaryBCHIV() {
		return retreatmentFemalePulmonaryBCHIV;
	}

	public void setRetreatmentFemalePulmonaryBCHIV(
			Integer retreatmentFemalePulmonaryBCHIV) {
		this.retreatmentFemalePulmonaryBCHIV = retreatmentFemalePulmonaryBCHIV;
	}

	public Integer getRetreatmentPulmonaryBCHIV() {
		return retreatmentPulmonaryBCHIV;
	}

	public void setRetreatmentPulmonaryBCHIV(Integer retreatmentPulmonaryBCHIV) {
		this.retreatmentPulmonaryBCHIV = retreatmentPulmonaryBCHIV;
	}

	public Integer getRetreatmentMalePulmonaryCD() {
		return retreatmentMalePulmonaryCD;
	}

	public void setRetreatmentMalePulmonaryCD(Integer retreatmentMalePulmonaryCD) {
		this.retreatmentMalePulmonaryCD = retreatmentMalePulmonaryCD;
	}

	public Integer getRetreatmentFemalePulmonaryCD() {
		return retreatmentFemalePulmonaryCD;
	}

	public void setRetreatmentFemalePulmonaryCD(Integer retreatmentFemalePulmonaryCD) {
		this.retreatmentFemalePulmonaryCD = retreatmentFemalePulmonaryCD;
	}

	public Integer getRetreatmentPulmonaryCD() {
		return retreatmentPulmonaryCD;
	}

	public void setRetreatmentPulmonaryCD(Integer retreatmentPulmonaryCD) {
		this.retreatmentPulmonaryCD = retreatmentPulmonaryCD;
	}

	public Integer getRetreatmentMalePulmonaryCDHIV() {
		return retreatmentMalePulmonaryCDHIV;
	}

	public void setRetreatmentMalePulmonaryCDHIV(
			Integer retreatmentMalePulmonaryCDHIV) {
		this.retreatmentMalePulmonaryCDHIV = retreatmentMalePulmonaryCDHIV;
	}

	public Integer getRetreatmentFemalePulmonaryCDHIV() {
		return retreatmentFemalePulmonaryCDHIV;
	}

	public void setRetreatmentFemalePulmonaryCDHIV(
			Integer retreatmentFemalePulmonaryCDHIV) {
		this.retreatmentFemalePulmonaryCDHIV = retreatmentFemalePulmonaryCDHIV;
	}

	public Integer getRetreatmentPulmonaryCDHIV() {
		return retreatmentPulmonaryCDHIV;
	}

	public void setRetreatmentPulmonaryCDHIV(Integer retreatmentPulmonaryCDHIV) {
		this.retreatmentPulmonaryCDHIV = retreatmentPulmonaryCDHIV;
	}

	public Integer getRetreatmentMaleExtrapulmonary() {
		return retreatmentMaleExtrapulmonary;
	}

	public void setRetreatmentMaleExtrapulmonary(
			Integer retreatmentMaleExtrapulmonary) {
		this.retreatmentMaleExtrapulmonary = retreatmentMaleExtrapulmonary;
	}

	public Integer getRetreatmentFemaleExtrapulmonary() {
		return retreatmentFemaleExtrapulmonary;
	}

	public void setRetreatmentFemaleExtrapulmonary(
			Integer retreatmentFemaleExtrapulmonary) {
		this.retreatmentFemaleExtrapulmonary = retreatmentFemaleExtrapulmonary;
	}

	public Integer getRetreatmentExtrapulmonary() {
		return retreatmentExtrapulmonary;
	}

	public void setRetreatmentExtrapulmonary(Integer retreatmentExtrapulmonary) {
		this.retreatmentExtrapulmonary = retreatmentExtrapulmonary;
	}

	public Integer getRetreatmentMaleExtrapulmonaryHIV() {
		return retreatmentMaleExtrapulmonaryHIV;
	}

	public void setRetreatmentMaleExtrapulmonaryHIV(
			Integer retreatmentMaleExtrapulmonaryHIV) {
		this.retreatmentMaleExtrapulmonaryHIV = retreatmentMaleExtrapulmonaryHIV;
	}

	public Integer getRetreatmentFemaleExtrapulmonaryHIV() {
		return retreatmentFemaleExtrapulmonaryHIV;
	}

	public void setRetreatmentFemaleExtrapulmonaryHIV(
			Integer retreatmentFemaleExtrapulmonaryHIV) {
		this.retreatmentFemaleExtrapulmonaryHIV = retreatmentFemaleExtrapulmonaryHIV;
	}

	public Integer getRetreatmentExtrapulmonaryHIV() {
		return retreatmentExtrapulmonaryHIV;
	}

	public void setRetreatmentExtrapulmonaryHIV(Integer retreatmentExtrapulmonaryHIV) {
		this.retreatmentExtrapulmonaryHIV = retreatmentExtrapulmonaryHIV;
	}

	public Integer getRetreatmentMale() {
		return retreatmentMale;
	}

	public void setRetreatmentMale(Integer retreatmentMale) {
		this.retreatmentMale = retreatmentMale;
	}

	public Integer getRetreatmentFemale() {
		return retreatmentFemale;
	}

	public void setRetreatmentFemale(Integer retreatmentFemale) {
		this.retreatmentFemale = retreatmentFemale;
	}

	public Integer getRetreatmentAll() {
		return retreatmentAll;
	}

	public void setRetreatmentAll(Integer retreatmentAll) {
		this.retreatmentAll = retreatmentAll;
	}

	public Integer getRetreatmentMaleHIV() {
		return retreatmentMaleHIV;
	}

	public void setRetreatmentMaleHIV(Integer retreatmentMaleHIV) {
		this.retreatmentMaleHIV = retreatmentMaleHIV;
	}

	public Integer getRetreatmentFemaleHIV() {
		return retreatmentFemaleHIV;
	}

	public void setRetreatmentFemaleHIV(Integer retreatmentFemaleHIV) {
		this.retreatmentFemaleHIV = retreatmentFemaleHIV;
	}

	public Integer getRetreatmentAllHIV() {
		return retreatmentAllHIV;
	}

	public void setRetreatmentAllHIV(Integer retreatmentAllHIV) {
		this.retreatmentAllHIV = retreatmentAllHIV;
	}

	public Integer getTotalMalePulmonaryBC() {
		return totalMalePulmonaryBC;
	}

	public void setTotalMalePulmonaryBC(Integer totalMalePulmonaryBC) {
		this.totalMalePulmonaryBC = totalMalePulmonaryBC;
	}

	public Integer getTotalFemalePulmonaryBC() {
		return totalFemalePulmonaryBC;
	}

	public void setTotalFemalePulmonaryBC(Integer totalFemalePulmonaryBC) {
		this.totalFemalePulmonaryBC = totalFemalePulmonaryBC;
	}

	public Integer getTotalPulmonaryBC() {
		return totalPulmonaryBC;
	}

	public void setTotalPulmonaryBC(Integer totalPulmonaryBC) {
		this.totalPulmonaryBC = totalPulmonaryBC;
	}

	public Integer getTotalMalePulmonaryBCHIV() {
		return totalMalePulmonaryBCHIV;
	}

	public void setTotalMalePulmonaryBCHIV(Integer totalMalePulmonaryBCHIV) {
		this.totalMalePulmonaryBCHIV = totalMalePulmonaryBCHIV;
	}

	public Integer getTotalFemalePulmonaryBCHIV() {
		return totalFemalePulmonaryBCHIV;
	}

	public void setTotalFemalePulmonaryBCHIV(Integer totalFemalePulmonaryBCHIV) {
		this.totalFemalePulmonaryBCHIV = totalFemalePulmonaryBCHIV;
	}

	public Integer getTotalPulmonaryBCHIV() {
		return totalPulmonaryBCHIV;
	}

	public void setTotalPulmonaryBCHIV(Integer totalPulmonaryBCHIV) {
		this.totalPulmonaryBCHIV = totalPulmonaryBCHIV;
	}

	public Integer getTotalMalePulmonaryCD() {
		return totalMalePulmonaryCD;
	}

	public void setTotalMalePulmonaryCD(Integer totalMalePulmonaryCD) {
		this.totalMalePulmonaryCD = totalMalePulmonaryCD;
	}

	public Integer getTotalFemalePulmonaryCD() {
		return totalFemalePulmonaryCD;
	}

	public void setTotalFemalePulmonaryCD(Integer totalFemalePulmonaryCD) {
		this.totalFemalePulmonaryCD = totalFemalePulmonaryCD;
	}

	public Integer getTotalPulmonaryCD() {
		return totalPulmonaryCD;
	}

	public void setTotalPulmonaryCD(Integer totalPulmonaryCD) {
		this.totalPulmonaryCD = totalPulmonaryCD;
	}

	public Integer getTotalMalePulmonaryCDHIV() {
		return totalMalePulmonaryCDHIV;
	}

	public void setTotalMalePulmonaryCDHIV(Integer totalMalePulmonaryCDHIV) {
		this.totalMalePulmonaryCDHIV = totalMalePulmonaryCDHIV;
	}

	public Integer getTotalFemalePulmonaryCDHIV() {
		return totalFemalePulmonaryCDHIV;
	}

	public void setTotalFemalePulmonaryCDHIV(Integer totalFemalePulmonaryCDHIV) {
		this.totalFemalePulmonaryCDHIV = totalFemalePulmonaryCDHIV;
	}

	public Integer getTotalPulmonaryCDHIV() {
		return totalPulmonaryCDHIV;
	}

	public void setTotalPulmonaryCDHIV(Integer totalPulmonaryCDHIV) {
		this.totalPulmonaryCDHIV = totalPulmonaryCDHIV;
	}

	public Integer getTotalMaleExtrapulmonary() {
		return totalMaleExtrapulmonary;
	}

	public void setTotalMaleExtrapulmonary(Integer totalMaleExtrapulmonary) {
		this.totalMaleExtrapulmonary = totalMaleExtrapulmonary;
	}

	public Integer getTotalFemaleExtrapulmonary() {
		return totalFemaleExtrapulmonary;
	}

	public void setTotalFemaleExtrapulmonary(Integer totalFemaleExtrapulmonary) {
		this.totalFemaleExtrapulmonary = totalFemaleExtrapulmonary;
	}

	public Integer getTotalExtrapulmonary() {
		return totalExtrapulmonary;
	}

	public void setTotalExtrapulmonary(Integer totalExtrapulmonary) {
		this.totalExtrapulmonary = totalExtrapulmonary;
	}

	public Integer getTotalMaleExtrapulmonaryHIV() {
		return totalMaleExtrapulmonaryHIV;
	}

	public void setTotalMaleExtrapulmonaryHIV(Integer totalMaleExtrapulmonaryHIV) {
		this.totalMaleExtrapulmonaryHIV = totalMaleExtrapulmonaryHIV;
	}

	public Integer getTotalFemaleExtrapulmonaryHIV() {
		return totalFemaleExtrapulmonaryHIV;
	}

	public void setTotalFemaleExtrapulmonaryHIV(Integer totalFemaleExtrapulmonaryHIV) {
		this.totalFemaleExtrapulmonaryHIV = totalFemaleExtrapulmonaryHIV;
	}

	public Integer getTotalExtrapulmonaryHIV() {
		return totalExtrapulmonaryHIV;
	}

	public void setTotalExtrapulmonaryHIV(Integer totalExtrapulmonaryHIV) {
		this.totalExtrapulmonaryHIV = totalExtrapulmonaryHIV;
	}

	public Integer getTotalMale() {
		return totalMale;
	}

	public void setTotalMale(Integer totalMale) {
		this.totalMale = totalMale;
	}

	public Integer getTotalFemale() {
		return totalFemale;
	}

	public void setTotalFemale(Integer totalFemale) {
		this.totalFemale = totalFemale;
	}

	public Integer getTotalAll() {
		return totalAll;
	}

	public void setTotalAll(Integer totalAll) {
		this.totalAll = totalAll;
	}

	public Integer getTotalMaleHIV() {
		return totalMaleHIV;
	}

	public void setTotalMaleHIV(Integer totalMaleHIV) {
		this.totalMaleHIV = totalMaleHIV;
	}

	public Integer getTotalFemaleHIV() {
		return totalFemaleHIV;
	}

	public void setTotalFemaleHIV(Integer totalFemaleHIV) {
		this.totalFemaleHIV = totalFemaleHIV;
	}

	public Integer getTotalAllHIV() {
		return totalAllHIV;
	}

	public void setTotalAllHIV(Integer totalAllHIV) {
		this.totalAllHIV = totalAllHIV;
	}
	
	public void add(TB07Table1Data table) {
		if(table==null) {
			return;
		}
		
		this.setNewMalePulmonaryBC04(this.getNewMalePulmonaryBC04() + table.getNewMalePulmonaryBC04());
		this.setNewFemalePulmonaryBC04(this.getNewFemalePulmonaryBC04() + table.getNewFemalePulmonaryBC04());
		this.setNewMalePulmonaryBC0514(this.getNewMalePulmonaryBC0514() + table.getNewMalePulmonaryBC0514());
		this.setNewFemalePulmonaryBC0514(this.getNewFemalePulmonaryBC0514() + table.getNewFemalePulmonaryBC0514());
		this.setNewMalePulmonaryBC1517(this.getNewMalePulmonaryBC1517() + table.getNewMalePulmonaryBC1517());
		this.setNewFemalePulmonaryBC1517(this.getNewFemalePulmonaryBC1517() + table.getNewFemalePulmonaryBC1517());
		this.setNewMalePulmonaryBC1819(this.getNewMalePulmonaryBC1819() + table.getNewMalePulmonaryBC1819());
		this.setNewFemalePulmonaryBC1819(this.getNewFemalePulmonaryBC1819() + table.getNewFemalePulmonaryBC1819());
		this.setNewMalePulmonaryBC2024(this.getNewMalePulmonaryBC2024() + table.getNewMalePulmonaryBC2024());
		this.setNewFemalePulmonaryBC2024(this.getNewFemalePulmonaryBC2024() + table.getNewFemalePulmonaryBC2024());
		this.setNewMalePulmonaryBC2534(this.getNewMalePulmonaryBC2534() + table.getNewMalePulmonaryBC2534());
		this.setNewFemalePulmonaryBC2534(this.getNewFemalePulmonaryBC2534() + table.getNewFemalePulmonaryBC2534());
		this.setNewMalePulmonaryBC3544(this.getNewMalePulmonaryBC3544() + table.getNewMalePulmonaryBC3544());
		this.setNewFemalePulmonaryBC3544(this.getNewFemalePulmonaryBC3544() + table.getNewFemalePulmonaryBC3544());
		this.setNewMalePulmonaryBC4554(this.getNewMalePulmonaryBC4554() + table.getNewMalePulmonaryBC4554());
		this.setNewFemalePulmonaryBC4554(this.getNewFemalePulmonaryBC4554() + table.getNewFemalePulmonaryBC4554());
		this.setNewMalePulmonaryBC5564(this.getNewMalePulmonaryBC5564() + table.getNewMalePulmonaryBC5564());
		this.setNewFemalePulmonaryBC5564(this.getNewFemalePulmonaryBC5564() + table.getNewFemalePulmonaryBC5564());
		this.setNewMalePulmonaryBC65(this.getNewMalePulmonaryBC65() + table.getNewMalePulmonaryBC65());
		this.setNewFemalePulmonaryBC65(this.getNewFemalePulmonaryBC65() + table.getNewFemalePulmonaryBC65());
		this.setNewMalePulmonaryBC(this.getNewMalePulmonaryBC() + table.getNewMalePulmonaryBC());
		this.setNewFemalePulmonaryBC(this.getNewFemalePulmonaryBC() + table.getNewFemalePulmonaryBC());
		this.setNewPulmonaryBC(this.getNewPulmonaryBC() + table.getNewPulmonaryBC());
		this.setNewMalePulmonaryBCHIV04(this.getNewMalePulmonaryBCHIV04() + table.getNewMalePulmonaryBCHIV04());
		this.setNewFemalePulmonaryBCHIV04(this.getNewFemalePulmonaryBCHIV04() + table.getNewFemalePulmonaryBCHIV04());
		this.setNewMalePulmonaryBCHIV0514(this.getNewMalePulmonaryBCHIV0514() + table.getNewMalePulmonaryBCHIV0514());
		this.setNewFemalePulmonaryBCHIV0514(this.getNewFemalePulmonaryBCHIV0514() + table.getNewFemalePulmonaryBCHIV0514());
		this.setNewMalePulmonaryBCHIV1517(this.getNewMalePulmonaryBCHIV1517() + table.getNewMalePulmonaryBCHIV1517());
		this.setNewFemalePulmonaryBCHIV1517(this.getNewFemalePulmonaryBCHIV1517() + table.getNewFemalePulmonaryBCHIV1517());
		this.setNewMalePulmonaryBCHIV1819(this.getNewMalePulmonaryBCHIV1819() + table.getNewMalePulmonaryBCHIV1819());
		this.setNewFemalePulmonaryBCHIV1819(this.getNewFemalePulmonaryBCHIV1819() + table.getNewFemalePulmonaryBCHIV1819());
		this.setNewMalePulmonaryBCHIV2024(this.getNewMalePulmonaryBCHIV2024() + table.getNewMalePulmonaryBCHIV2024());
		this.setNewFemalePulmonaryBCHIV2024(this.getNewFemalePulmonaryBCHIV2024() + table.getNewFemalePulmonaryBCHIV2024());
		this.setNewMalePulmonaryBCHIV2534(this.getNewMalePulmonaryBCHIV2534() + table.getNewMalePulmonaryBCHIV2534());
		this.setNewFemalePulmonaryBCHIV2534(this.getNewFemalePulmonaryBCHIV2534() + table.getNewFemalePulmonaryBCHIV2534());
		this.setNewMalePulmonaryBCHIV3544(this.getNewMalePulmonaryBCHIV3544() + table.getNewMalePulmonaryBCHIV3544());
		this.setNewFemalePulmonaryBCHIV3544(this.getNewFemalePulmonaryBCHIV3544() + table.getNewFemalePulmonaryBCHIV3544());
		this.setNewMalePulmonaryBCHIV4554(this.getNewMalePulmonaryBCHIV4554() + table.getNewMalePulmonaryBCHIV4554());
		this.setNewFemalePulmonaryBCHIV4554(this.getNewFemalePulmonaryBCHIV4554() + table.getNewFemalePulmonaryBCHIV4554());
		this.setNewMalePulmonaryBCHIV5564(this.getNewMalePulmonaryBCHIV5564() + table.getNewMalePulmonaryBCHIV5564());
		this.setNewFemalePulmonaryBCHIV5564(this.getNewFemalePulmonaryBCHIV5564() + table.getNewFemalePulmonaryBCHIV5564());
		this.setNewMalePulmonaryBCHIV65(this.getNewMalePulmonaryBCHIV65() + table.getNewMalePulmonaryBCHIV65());
		this.setNewFemalePulmonaryBCHIV65(this.getNewFemalePulmonaryBCHIV65() + table.getNewFemalePulmonaryBCHIV65());
		this.setNewMalePulmonaryBCHIV(this.getNewMalePulmonaryBCHIV() + table.getNewMalePulmonaryBCHIV());
		this.setNewFemalePulmonaryBCHIV(this.getNewFemalePulmonaryBCHIV() + table.getNewFemalePulmonaryBCHIV());
		this.setNewPulmonaryBCHIV(this.getNewPulmonaryBCHIV() + table.getNewPulmonaryBCHIV());
		this.setNewMalePulmonaryCD04(this.getNewMalePulmonaryCD04() + table.getNewMalePulmonaryCD04());
		this.setNewFemalePulmonaryCD04(this.getNewFemalePulmonaryCD04() + table.getNewFemalePulmonaryCD04());
		this.setNewMalePulmonaryCD0514(this.getNewMalePulmonaryCD0514() + table.getNewMalePulmonaryCD0514());
		this.setNewFemalePulmonaryCD0514(this.getNewFemalePulmonaryCD0514() + table.getNewFemalePulmonaryCD0514());
		this.setNewMalePulmonaryCD1517(this.getNewMalePulmonaryCD1517() + table.getNewMalePulmonaryCD1517());
		this.setNewFemalePulmonaryCD1517(this.getNewFemalePulmonaryCD1517() + table.getNewFemalePulmonaryCD1517());
		this.setNewMalePulmonaryCD1819(this.getNewMalePulmonaryCD1819() + table.getNewMalePulmonaryCD1819());
		this.setNewFemalePulmonaryCD1819(this.getNewFemalePulmonaryCD1819() + table.getNewFemalePulmonaryCD1819());
		this.setNewMalePulmonaryCD2024(this.getNewMalePulmonaryCD2024() + table.getNewMalePulmonaryCD2024());
		this.setNewFemalePulmonaryCD2024(this.getNewFemalePulmonaryCD2024() + table.getNewFemalePulmonaryCD2024());
		this.setNewMalePulmonaryCD2534(this.getNewMalePulmonaryCD2534() + table.getNewMalePulmonaryCD2534());
		this.setNewFemalePulmonaryCD2534(this.getNewFemalePulmonaryCD2534() + table.getNewFemalePulmonaryCD2534());
		this.setNewMalePulmonaryCD3544(this.getNewMalePulmonaryCD3544() + table.getNewMalePulmonaryCD3544());
		this.setNewFemalePulmonaryCD3544(this.getNewFemalePulmonaryCD3544() + table.getNewFemalePulmonaryCD3544());
		this.setNewMalePulmonaryCD4554(this.getNewMalePulmonaryCD4554() + table.getNewMalePulmonaryCD4554());
		this.setNewFemalePulmonaryCD4554(this.getNewFemalePulmonaryCD4554() + table.getNewFemalePulmonaryCD4554());
		this.setNewMalePulmonaryCD5564(this.getNewMalePulmonaryCD5564() + table.getNewMalePulmonaryCD5564());
		this.setNewFemalePulmonaryCD5564(this.getNewFemalePulmonaryCD5564() + table.getNewFemalePulmonaryCD5564());
		this.setNewMalePulmonaryCD65(this.getNewMalePulmonaryCD65() + table.getNewMalePulmonaryCD65());
		this.setNewFemalePulmonaryCD65(this.getNewFemalePulmonaryCD65() + table.getNewFemalePulmonaryCD65());
		this.setNewMalePulmonaryCD(this.getNewMalePulmonaryCD() + table.getNewMalePulmonaryCD());
		this.setNewFemalePulmonaryCD(this.getNewFemalePulmonaryCD() + table.getNewFemalePulmonaryCD());
		this.setNewPulmonaryCD(this.getNewPulmonaryCD() + table.getNewPulmonaryCD());
		this.setNewMalePulmonaryCDHIV04(this.getNewMalePulmonaryCDHIV04() + table.getNewMalePulmonaryCDHIV04());
		this.setNewFemalePulmonaryCDHIV04(this.getNewFemalePulmonaryCDHIV04() + table.getNewFemalePulmonaryCDHIV04());
		this.setNewMalePulmonaryCDHIV0514(this.getNewMalePulmonaryCDHIV0514() + table.getNewMalePulmonaryCDHIV0514());
		this.setNewFemalePulmonaryCDHIV0514(this.getNewFemalePulmonaryCDHIV0514() + table.getNewFemalePulmonaryCDHIV0514());
		this.setNewMalePulmonaryCDHIV1517(this.getNewMalePulmonaryCDHIV1517() + table.getNewMalePulmonaryCDHIV1517());
		this.setNewFemalePulmonaryCDHIV1517(this.getNewFemalePulmonaryCDHIV1517() + table.getNewFemalePulmonaryCDHIV1517());
		this.setNewMalePulmonaryCDHIV1819(this.getNewMalePulmonaryCDHIV1819() + table.getNewMalePulmonaryCDHIV1819());
		this.setNewFemalePulmonaryCDHIV1819(this.getNewFemalePulmonaryCDHIV1819() + table.getNewFemalePulmonaryCDHIV1819());
		this.setNewMalePulmonaryCDHIV2024(this.getNewMalePulmonaryCDHIV2024() + table.getNewMalePulmonaryCDHIV2024());
		this.setNewFemalePulmonaryCDHIV2024(this.getNewFemalePulmonaryCDHIV2024() + table.getNewFemalePulmonaryCDHIV2024());
		this.setNewMalePulmonaryCDHIV2534(this.getNewMalePulmonaryCDHIV2534() + table.getNewMalePulmonaryCDHIV2534());
		this.setNewFemalePulmonaryCDHIV2534(this.getNewFemalePulmonaryCDHIV2534() + table.getNewFemalePulmonaryCDHIV2534());
		this.setNewMalePulmonaryCDHIV3544(this.getNewMalePulmonaryCDHIV3544() + table.getNewMalePulmonaryCDHIV3544());
		this.setNewFemalePulmonaryCDHIV3544(this.getNewFemalePulmonaryCDHIV3544() + table.getNewFemalePulmonaryCDHIV3544());
		this.setNewMalePulmonaryCDHIV4554(this.getNewMalePulmonaryCDHIV4554() + table.getNewMalePulmonaryCDHIV4554());
		this.setNewFemalePulmonaryCDHIV4554(this.getNewFemalePulmonaryCDHIV4554() + table.getNewFemalePulmonaryCDHIV4554());
		this.setNewMalePulmonaryCDHIV5564(this.getNewMalePulmonaryCDHIV5564() + table.getNewMalePulmonaryCDHIV5564());
		this.setNewFemalePulmonaryCDHIV5564(this.getNewFemalePulmonaryCDHIV5564() + table.getNewFemalePulmonaryCDHIV5564());
		this.setNewMalePulmonaryCDHIV65(this.getNewMalePulmonaryCDHIV65() + table.getNewMalePulmonaryCDHIV65());
		this.setNewFemalePulmonaryCDHIV65(this.getNewFemalePulmonaryCDHIV65() + table.getNewFemalePulmonaryCDHIV65());
		this.setNewMalePulmonaryCDHIV(this.getNewMalePulmonaryCDHIV() + table.getNewMalePulmonaryCDHIV());
		this.setNewFemalePulmonaryCDHIV(this.getNewFemalePulmonaryCDHIV() + table.getNewFemalePulmonaryCDHIV());
		this.setNewPulmonaryCDHIV(this.getNewPulmonaryCDHIV() + table.getNewPulmonaryCDHIV());
		this.setNewMaleExtrapulmonary04(this.getNewMaleExtrapulmonary04() + table.getNewMaleExtrapulmonary04());
		this.setNewFemaleExtrapulmonary04(this.getNewFemaleExtrapulmonary04() + table.getNewFemaleExtrapulmonary04());
		this.setNewMaleExtrapulmonary0514(this.getNewMaleExtrapulmonary0514() + table.getNewMaleExtrapulmonary0514());
		this.setNewFemaleExtrapulmonary0514(this.getNewFemaleExtrapulmonary0514() + table.getNewFemaleExtrapulmonary0514());
		this.setNewMaleExtrapulmonary1517(this.getNewMaleExtrapulmonary1517() + table.getNewMaleExtrapulmonary1517());
		this.setNewFemaleExtrapulmonary1517(this.getNewFemaleExtrapulmonary1517() + table.getNewFemaleExtrapulmonary1517());
		this.setNewMaleExtrapulmonary1819(this.getNewMaleExtrapulmonary1819() + table.getNewMaleExtrapulmonary1819());
		this.setNewFemaleExtrapulmonary1819(this.getNewFemaleExtrapulmonary1819() + table.getNewFemaleExtrapulmonary1819());
		this.setNewMaleExtrapulmonary2024(this.getNewMaleExtrapulmonary2024() + table.getNewMaleExtrapulmonary2024());
		this.setNewFemaleExtrapulmonary2024(this.getNewFemaleExtrapulmonary2024() + table.getNewFemaleExtrapulmonary2024());
		this.setNewMaleExtrapulmonary2534(this.getNewMaleExtrapulmonary2534() + table.getNewMaleExtrapulmonary2534());
		this.setNewFemaleExtrapulmonary2534(this.getNewFemaleExtrapulmonary2534() + table.getNewFemaleExtrapulmonary2534());
		this.setNewMaleExtrapulmonary3544(this.getNewMaleExtrapulmonary3544() + table.getNewMaleExtrapulmonary3544());
		this.setNewFemaleExtrapulmonary3544(this.getNewFemaleExtrapulmonary3544() + table.getNewFemaleExtrapulmonary3544());
		this.setNewMaleExtrapulmonary4554(this.getNewMaleExtrapulmonary4554() + table.getNewMaleExtrapulmonary4554());
		this.setNewFemaleExtrapulmonary4554(this.getNewFemaleExtrapulmonary4554() + table.getNewFemaleExtrapulmonary4554());
		this.setNewMaleExtrapulmonary5564(this.getNewMaleExtrapulmonary5564() + table.getNewMaleExtrapulmonary5564());
		this.setNewFemaleExtrapulmonary5564(this.getNewFemaleExtrapulmonary5564() + table.getNewFemaleExtrapulmonary5564());
		this.setNewMaleExtrapulmonary65(this.getNewMaleExtrapulmonary65() + table.getNewMaleExtrapulmonary65());
		this.setNewFemaleExtrapulmonary65(this.getNewFemaleExtrapulmonary65() + table.getNewFemaleExtrapulmonary65());
		this.setNewMaleExtrapulmonary(this.getNewMaleExtrapulmonary() + table.getNewMaleExtrapulmonary());
		this.setNewFemaleExtrapulmonary(this.getNewFemaleExtrapulmonary() + table.getNewFemaleExtrapulmonary());
		this.setNewExtrapulmonary(this.getNewExtrapulmonary() + table.getNewExtrapulmonary());
		this.setNewMaleExtrapulmonaryHIV04(this.getNewMaleExtrapulmonaryHIV04() + table.getNewMaleExtrapulmonaryHIV04());
		this.setNewFemaleExtrapulmonaryHIV04(this.getNewFemaleExtrapulmonaryHIV04() + table.getNewFemaleExtrapulmonaryHIV04());
		this.setNewMaleExtrapulmonaryHIV0514(this.getNewMaleExtrapulmonaryHIV0514() + table.getNewMaleExtrapulmonaryHIV0514());
		this.setNewFemaleExtrapulmonaryHIV0514(this.getNewFemaleExtrapulmonaryHIV0514() + table.getNewFemaleExtrapulmonaryHIV0514());
		this.setNewMaleExtrapulmonaryHIV1517(this.getNewMaleExtrapulmonaryHIV1517() + table.getNewMaleExtrapulmonaryHIV1517());
		this.setNewFemaleExtrapulmonaryHIV1517(this.getNewFemaleExtrapulmonaryHIV1517() + table.getNewFemaleExtrapulmonaryHIV1517());
		this.setNewMaleExtrapulmonaryHIV1819(this.getNewMaleExtrapulmonaryHIV1819() + table.getNewMaleExtrapulmonaryHIV1819());
		this.setNewFemaleExtrapulmonaryHIV1819(this.getNewFemaleExtrapulmonaryHIV1819() + table.getNewFemaleExtrapulmonaryHIV1819());
		this.setNewMaleExtrapulmonaryHIV2024(this.getNewMaleExtrapulmonaryHIV2024() + table.getNewMaleExtrapulmonaryHIV2024());
		this.setNewFemaleExtrapulmonaryHIV2024(this.getNewFemaleExtrapulmonaryHIV2024() + table.getNewFemaleExtrapulmonaryHIV2024());
		this.setNewMaleExtrapulmonaryHIV2534(this.getNewMaleExtrapulmonaryHIV2534() + table.getNewMaleExtrapulmonaryHIV2534());
		this.setNewFemaleExtrapulmonaryHIV2534(this.getNewFemaleExtrapulmonaryHIV2534() + table.getNewFemaleExtrapulmonaryHIV2534());
		this.setNewMaleExtrapulmonaryHIV3544(this.getNewMaleExtrapulmonaryHIV3544() + table.getNewMaleExtrapulmonaryHIV3544());
		this.setNewFemaleExtrapulmonaryHIV3544(this.getNewFemaleExtrapulmonaryHIV3544() + table.getNewFemaleExtrapulmonaryHIV3544());
		this.setNewMaleExtrapulmonaryHIV4554(this.getNewMaleExtrapulmonaryHIV4554() + table.getNewMaleExtrapulmonaryHIV4554());
		this.setNewFemaleExtrapulmonaryHIV4554(this.getNewFemaleExtrapulmonaryHIV4554() + table.getNewFemaleExtrapulmonaryHIV4554());
		this.setNewMaleExtrapulmonaryHIV5564(this.getNewMaleExtrapulmonaryHIV5564() + table.getNewMaleExtrapulmonaryHIV5564());
		this.setNewFemaleExtrapulmonaryHIV5564(this.getNewFemaleExtrapulmonaryHIV5564() + table.getNewFemaleExtrapulmonaryHIV5564());
		this.setNewMaleExtrapulmonaryHIV65(this.getNewMaleExtrapulmonaryHIV65() + table.getNewMaleExtrapulmonaryHIV65());
		this.setNewFemaleExtrapulmonaryHIV65(this.getNewFemaleExtrapulmonaryHIV65() + table.getNewFemaleExtrapulmonaryHIV65());
		this.setNewMaleExtrapulmonaryHIV(this.getNewMaleExtrapulmonaryHIV() + table.getNewMaleExtrapulmonaryHIV());
		this.setNewFemaleExtrapulmonaryHIV(this.getNewFemaleExtrapulmonaryHIV() + table.getNewFemaleExtrapulmonaryHIV());
		this.setNewExtrapulmonaryHIV(this.getNewExtrapulmonaryHIV() + table.getNewExtrapulmonaryHIV());
		this.setNewMale04(this.getNewMale04() + table.getNewMale04());
		this.setNewFemale04(this.getNewFemale04() + table.getNewFemale04());
		this.setNewMale0514(this.getNewMale0514() + table.getNewMale0514());
		this.setNewFemale0514(this.getNewFemale0514() + table.getNewFemale0514());
		this.setNewMale1517(this.getNewMale1517() + table.getNewMale1517());
		this.setNewFemale1517(this.getNewFemale1517() + table.getNewFemale1517());
		this.setNewMale1819(this.getNewMale1819() + table.getNewMale1819());
		this.setNewFemale1819(this.getNewFemale1819() + table.getNewFemale1819());
		this.setNewMale2024(this.getNewMale2024() + table.getNewMale2024());
		this.setNewFemale2024(this.getNewFemale2024() + table.getNewFemale2024());
		this.setNewMale2534(this.getNewMale2534() + table.getNewMale2534());
		this.setNewFemale2534(this.getNewFemale2534() + table.getNewFemale2534());
		this.setNewMale3544(this.getNewMale3544() + table.getNewMale3544());
		this.setNewFemale3544(this.getNewFemale3544() + table.getNewFemale3544());
		this.setNewMale4554(this.getNewMale4554() + table.getNewMale4554());
		this.setNewFemale4554(this.getNewFemale4554() + table.getNewFemale4554());
		this.setNewMale5564(this.getNewMale5564() + table.getNewMale5564());
		this.setNewFemale5564(this.getNewFemale5564() + table.getNewFemale5564());
		this.setNewMale65(this.getNewMale65() + table.getNewMale65());
		this.setNewFemale65(this.getNewFemale65() + table.getNewFemale65());
		this.setNewMale(this.getNewMale() + table.getNewMale());
		this.setNewFemale(this.getNewFemale() + table.getNewFemale());
		this.setNewAll(this.getNewAll() + table.getNewAll());
		this.setNewMaleHIV04(this.getNewMaleHIV04() + table.getNewMaleHIV04());
		this.setNewFemaleHIV04(this.getNewFemaleHIV04() + table.getNewFemaleHIV04());
		this.setNewMaleHIV0514(this.getNewMaleHIV0514() + table.getNewMaleHIV0514());
		this.setNewFemaleHIV0514(this.getNewFemaleHIV0514() + table.getNewFemaleHIV0514());
		this.setNewMaleHIV1517(this.getNewMaleHIV1517() + table.getNewMaleHIV1517());
		this.setNewFemaleHIV1517(this.getNewFemaleHIV1517() + table.getNewFemaleHIV1517());
		this.setNewMaleHIV1819(this.getNewMaleHIV1819() + table.getNewMaleHIV1819());
		this.setNewFemaleHIV1819(this.getNewFemaleHIV1819() + table.getNewFemaleHIV1819());
		this.setNewMaleHIV2024(this.getNewMaleHIV2024() + table.getNewMaleHIV2024());
		this.setNewFemaleHIV2024(this.getNewFemaleHIV2024() + table.getNewFemaleHIV2024());
		this.setNewMaleHIV2534(this.getNewMaleHIV2534() + table.getNewMaleHIV2534());
		this.setNewFemaleHIV2534(this.getNewFemaleHIV2534() + table.getNewFemaleHIV2534());
		this.setNewMaleHIV3544(this.getNewMaleHIV3544() + table.getNewMaleHIV3544());
		this.setNewFemaleHIV3544(this.getNewFemaleHIV3544() + table.getNewFemaleHIV3544());
		this.setNewMaleHIV4554(this.getNewMaleHIV4554() + table.getNewMaleHIV4554());
		this.setNewFemaleHIV4554(this.getNewFemaleHIV4554() + table.getNewFemaleHIV4554());
		this.setNewMaleHIV5564(this.getNewMaleHIV5564() + table.getNewMaleHIV5564());
		this.setNewFemaleHIV5564(this.getNewFemaleHIV5564() + table.getNewFemaleHIV5564());
		this.setNewMaleHIV65(this.getNewMaleHIV65() + table.getNewMaleHIV65());
		this.setNewFemaleHIV65(this.getNewFemaleHIV65() + table.getNewFemaleHIV65());
		this.setNewMaleHIV(this.getNewMaleHIV() + table.getNewMaleHIV());
		this.setNewFemaleHIV(this.getNewFemaleHIV() + table.getNewFemaleHIV());
		this.setNewAllHIV(this.getNewAllHIV() + table.getNewAllHIV());
		this.setRelapseMalePulmonaryBC04(this.getRelapseMalePulmonaryBC04() + table.getRelapseMalePulmonaryBC04());
		this.setRelapseFemalePulmonaryBC04(this.getRelapseFemalePulmonaryBC04() + table.getRelapseFemalePulmonaryBC04());
		this.setRelapseMalePulmonaryBC0514(this.getRelapseMalePulmonaryBC0514() + table.getRelapseMalePulmonaryBC0514());
		this.setRelapseFemalePulmonaryBC0514(this.getRelapseFemalePulmonaryBC0514() + table.getRelapseFemalePulmonaryBC0514());
		this.setRelapseMalePulmonaryBC1517(this.getRelapseMalePulmonaryBC1517() + table.getRelapseMalePulmonaryBC1517());
		this.setRelapseFemalePulmonaryBC1517(this.getRelapseFemalePulmonaryBC1517() + table.getRelapseFemalePulmonaryBC1517());
		this.setRelapseMalePulmonaryBC1819(this.getRelapseMalePulmonaryBC1819() + table.getRelapseMalePulmonaryBC1819());
		this.setRelapseFemalePulmonaryBC1819(this.getRelapseFemalePulmonaryBC1819() + table.getRelapseFemalePulmonaryBC1819());
		this.setRelapseMalePulmonaryBC2024(this.getRelapseMalePulmonaryBC2024() + table.getRelapseMalePulmonaryBC2024());
		this.setRelapseFemalePulmonaryBC2024(this.getRelapseFemalePulmonaryBC2024() + table.getRelapseFemalePulmonaryBC2024());
		this.setRelapseMalePulmonaryBC2534(this.getRelapseMalePulmonaryBC2534() + table.getRelapseMalePulmonaryBC2534());
		this.setRelapseFemalePulmonaryBC2534(this.getRelapseFemalePulmonaryBC2534() + table.getRelapseFemalePulmonaryBC2534());
		this.setRelapseMalePulmonaryBC3544(this.getRelapseMalePulmonaryBC3544() + table.getRelapseMalePulmonaryBC3544());
		this.setRelapseFemalePulmonaryBC3544(this.getRelapseFemalePulmonaryBC3544() + table.getRelapseFemalePulmonaryBC3544());
		this.setRelapseMalePulmonaryBC4554(this.getRelapseMalePulmonaryBC4554() + table.getRelapseMalePulmonaryBC4554());
		this.setRelapseFemalePulmonaryBC4554(this.getRelapseFemalePulmonaryBC4554() + table.getRelapseFemalePulmonaryBC4554());
		this.setRelapseMalePulmonaryBC5564(this.getRelapseMalePulmonaryBC5564() + table.getRelapseMalePulmonaryBC5564());
		this.setRelapseFemalePulmonaryBC5564(this.getRelapseFemalePulmonaryBC5564() + table.getRelapseFemalePulmonaryBC5564());
		this.setRelapseMalePulmonaryBC65(this.getRelapseMalePulmonaryBC65() + table.getRelapseMalePulmonaryBC65());
		this.setRelapseFemalePulmonaryBC65(this.getRelapseFemalePulmonaryBC65() + table.getRelapseFemalePulmonaryBC65());
		this.setRelapseMalePulmonaryBC(this.getRelapseMalePulmonaryBC() + table.getRelapseMalePulmonaryBC());
		this.setRelapseFemalePulmonaryBC(this.getRelapseFemalePulmonaryBC() + table.getRelapseFemalePulmonaryBC());
		this.setRelapsePulmonaryBC(this.getRelapsePulmonaryBC() + table.getRelapsePulmonaryBC());
		this.setRelapseMalePulmonaryBCHIV04(this.getRelapseMalePulmonaryBCHIV04() + table.getRelapseMalePulmonaryBCHIV04());
		this.setRelapseFemalePulmonaryBCHIV04(this.getRelapseFemalePulmonaryBCHIV04() + table.getRelapseFemalePulmonaryBCHIV04());
		this.setRelapseMalePulmonaryBCHIV0514(this.getRelapseMalePulmonaryBCHIV0514() + table.getRelapseMalePulmonaryBCHIV0514());
		this.setRelapseFemalePulmonaryBCHIV0514(this.getRelapseFemalePulmonaryBCHIV0514() + table.getRelapseFemalePulmonaryBCHIV0514());
		this.setRelapseMalePulmonaryBCHIV1517(this.getRelapseMalePulmonaryBCHIV1517() + table.getRelapseMalePulmonaryBCHIV1517());
		this.setRelapseFemalePulmonaryBCHIV1517(this.getRelapseFemalePulmonaryBCHIV1517() + table.getRelapseFemalePulmonaryBCHIV1517());
		this.setRelapseMalePulmonaryBCHIV1819(this.getRelapseMalePulmonaryBCHIV1819() + table.getRelapseMalePulmonaryBCHIV1819());
		this.setRelapseFemalePulmonaryBCHIV1819(this.getRelapseFemalePulmonaryBCHIV1819() + table.getRelapseFemalePulmonaryBCHIV1819());
		this.setRelapseMalePulmonaryBCHIV2024(this.getRelapseMalePulmonaryBCHIV2024() + table.getRelapseMalePulmonaryBCHIV2024());
		this.setRelapseFemalePulmonaryBCHIV2024(this.getRelapseFemalePulmonaryBCHIV2024() + table.getRelapseFemalePulmonaryBCHIV2024());
		this.setRelapseMalePulmonaryBCHIV2534(this.getRelapseMalePulmonaryBCHIV2534() + table.getRelapseMalePulmonaryBCHIV2534());
		this.setRelapseFemalePulmonaryBCHIV2534(this.getRelapseFemalePulmonaryBCHIV2534() + table.getRelapseFemalePulmonaryBCHIV2534());
		this.setRelapseMalePulmonaryBCHIV3544(this.getRelapseMalePulmonaryBCHIV3544() + table.getRelapseMalePulmonaryBCHIV3544());
		this.setRelapseFemalePulmonaryBCHIV3544(this.getRelapseFemalePulmonaryBCHIV3544() + table.getRelapseFemalePulmonaryBCHIV3544());
		this.setRelapseMalePulmonaryBCHIV4554(this.getRelapseMalePulmonaryBCHIV4554() + table.getRelapseMalePulmonaryBCHIV4554());
		this.setRelapseFemalePulmonaryBCHIV4554(this.getRelapseFemalePulmonaryBCHIV4554() + table.getRelapseFemalePulmonaryBCHIV4554());
		this.setRelapseMalePulmonaryBCHIV5564(this.getRelapseMalePulmonaryBCHIV5564() + table.getRelapseMalePulmonaryBCHIV5564());
		this.setRelapseFemalePulmonaryBCHIV5564(this.getRelapseFemalePulmonaryBCHIV5564() + table.getRelapseFemalePulmonaryBCHIV5564());
		this.setRelapseMalePulmonaryBCHIV65(this.getRelapseMalePulmonaryBCHIV65() + table.getRelapseMalePulmonaryBCHIV65());
		this.setRelapseFemalePulmonaryBCHIV65(this.getRelapseFemalePulmonaryBCHIV65() + table.getRelapseFemalePulmonaryBCHIV65());
		this.setRelapseMalePulmonaryBCHIV(this.getRelapseMalePulmonaryBCHIV() + table.getRelapseMalePulmonaryBCHIV());
		this.setRelapseFemalePulmonaryBCHIV(this.getRelapseFemalePulmonaryBCHIV() + table.getRelapseFemalePulmonaryBCHIV());
		this.setRelapsePulmonaryBCHIV(this.getRelapsePulmonaryBCHIV() + table.getRelapsePulmonaryBCHIV());
		this.setRelapseMalePulmonaryCD04(this.getRelapseMalePulmonaryCD04() + table.getRelapseMalePulmonaryCD04());
		this.setRelapseFemalePulmonaryCD04(this.getRelapseFemalePulmonaryCD04() + table.getRelapseFemalePulmonaryCD04());
		this.setRelapseMalePulmonaryCD0514(this.getRelapseMalePulmonaryCD0514() + table.getRelapseMalePulmonaryCD0514());
		this.setRelapseFemalePulmonaryCD0514(this.getRelapseFemalePulmonaryCD0514() + table.getRelapseFemalePulmonaryCD0514());
		this.setRelapseMalePulmonaryCD1517(this.getRelapseMalePulmonaryCD1517() + table.getRelapseMalePulmonaryCD1517());
		this.setRelapseFemalePulmonaryCD1517(this.getRelapseFemalePulmonaryCD1517() + table.getRelapseFemalePulmonaryCD1517());
		this.setRelapseMalePulmonaryCD1819(this.getRelapseMalePulmonaryCD1819() + table.getRelapseMalePulmonaryCD1819());
		this.setRelapseFemalePulmonaryCD1819(this.getRelapseFemalePulmonaryCD1819() + table.getRelapseFemalePulmonaryCD1819());
		this.setRelapseMalePulmonaryCD2024(this.getRelapseMalePulmonaryCD2024() + table.getRelapseMalePulmonaryCD2024());
		this.setRelapseFemalePulmonaryCD2024(this.getRelapseFemalePulmonaryCD2024() + table.getRelapseFemalePulmonaryCD2024());
		this.setRelapseMalePulmonaryCD2534(this.getRelapseMalePulmonaryCD2534() + table.getRelapseMalePulmonaryCD2534());
		this.setRelapseFemalePulmonaryCD2534(this.getRelapseFemalePulmonaryCD2534() + table.getRelapseFemalePulmonaryCD2534());
		this.setRelapseMalePulmonaryCD3544(this.getRelapseMalePulmonaryCD3544() + table.getRelapseMalePulmonaryCD3544());
		this.setRelapseFemalePulmonaryCD3544(this.getRelapseFemalePulmonaryCD3544() + table.getRelapseFemalePulmonaryCD3544());
		this.setRelapseMalePulmonaryCD4554(this.getRelapseMalePulmonaryCD4554() + table.getRelapseMalePulmonaryCD4554());
		this.setRelapseFemalePulmonaryCD4554(this.getRelapseFemalePulmonaryCD4554() + table.getRelapseFemalePulmonaryCD4554());
		this.setRelapseMalePulmonaryCD5564(this.getRelapseMalePulmonaryCD5564() + table.getRelapseMalePulmonaryCD5564());
		this.setRelapseFemalePulmonaryCD5564(this.getRelapseFemalePulmonaryCD5564() + table.getRelapseFemalePulmonaryCD5564());
		this.setRelapseMalePulmonaryCD65(this.getRelapseMalePulmonaryCD65() + table.getRelapseMalePulmonaryCD65());
		this.setRelapseFemalePulmonaryCD65(this.getRelapseFemalePulmonaryCD65() + table.getRelapseFemalePulmonaryCD65());
		this.setRelapseMalePulmonaryCD(this.getRelapseMalePulmonaryCD() + table.getRelapseMalePulmonaryCD());
		this.setRelapseFemalePulmonaryCD(this.getRelapseFemalePulmonaryCD() + table.getRelapseFemalePulmonaryCD());
		this.setRelapsePulmonaryCD(this.getRelapsePulmonaryCD() + table.getRelapsePulmonaryCD());
		this.setRelapseMalePulmonaryCDHIV04(this.getRelapseMalePulmonaryCDHIV04() + table.getRelapseMalePulmonaryCDHIV04());
		this.setRelapseFemalePulmonaryCDHIV04(this.getRelapseFemalePulmonaryCDHIV04() + table.getRelapseFemalePulmonaryCDHIV04());
		this.setRelapseMalePulmonaryCDHIV0514(this.getRelapseMalePulmonaryCDHIV0514() + table.getRelapseMalePulmonaryCDHIV0514());
		this.setRelapseFemalePulmonaryCDHIV0514(this.getRelapseFemalePulmonaryCDHIV0514() + table.getRelapseFemalePulmonaryCDHIV0514());
		this.setRelapseMalePulmonaryCDHIV1517(this.getRelapseMalePulmonaryCDHIV1517() + table.getRelapseMalePulmonaryCDHIV1517());
		this.setRelapseFemalePulmonaryCDHIV1517(this.getRelapseFemalePulmonaryCDHIV1517() + table.getRelapseFemalePulmonaryCDHIV1517());
		this.setRelapseMalePulmonaryCDHIV1819(this.getRelapseMalePulmonaryCDHIV1819() + table.getRelapseMalePulmonaryCDHIV1819());
		this.setRelapseFemalePulmonaryCDHIV1819(this.getRelapseFemalePulmonaryCDHIV1819() + table.getRelapseFemalePulmonaryCDHIV1819());
		this.setRelapseMalePulmonaryCDHIV2024(this.getRelapseMalePulmonaryCDHIV2024() + table.getRelapseMalePulmonaryCDHIV2024());
		this.setRelapseFemalePulmonaryCDHIV2024(this.getRelapseFemalePulmonaryCDHIV2024() + table.getRelapseFemalePulmonaryCDHIV2024());
		this.setRelapseMalePulmonaryCDHIV2534(this.getRelapseMalePulmonaryCDHIV2534() + table.getRelapseMalePulmonaryCDHIV2534());
		this.setRelapseFemalePulmonaryCDHIV2534(this.getRelapseFemalePulmonaryCDHIV2534() + table.getRelapseFemalePulmonaryCDHIV2534());
		this.setRelapseMalePulmonaryCDHIV3544(this.getRelapseMalePulmonaryCDHIV3544() + table.getRelapseMalePulmonaryCDHIV3544());
		this.setRelapseFemalePulmonaryCDHIV3544(this.getRelapseFemalePulmonaryCDHIV3544() + table.getRelapseFemalePulmonaryCDHIV3544());
		this.setRelapseMalePulmonaryCDHIV4554(this.getRelapseMalePulmonaryCDHIV4554() + table.getRelapseMalePulmonaryCDHIV4554());
		this.setRelapseFemalePulmonaryCDHIV4554(this.getRelapseFemalePulmonaryCDHIV4554() + table.getRelapseFemalePulmonaryCDHIV4554());
		this.setRelapseMalePulmonaryCDHIV5564(this.getRelapseMalePulmonaryCDHIV5564() + table.getRelapseMalePulmonaryCDHIV5564());
		this.setRelapseFemalePulmonaryCDHIV5564(this.getRelapseFemalePulmonaryCDHIV5564() + table.getRelapseFemalePulmonaryCDHIV5564());
		this.setRelapseMalePulmonaryCDHIV65(this.getRelapseMalePulmonaryCDHIV65() + table.getRelapseMalePulmonaryCDHIV65());
		this.setRelapseFemalePulmonaryCDHIV65(this.getRelapseFemalePulmonaryCDHIV65() + table.getRelapseFemalePulmonaryCDHIV65());
		this.setRelapseMalePulmonaryCDHIV(this.getRelapseMalePulmonaryCDHIV() + table.getRelapseMalePulmonaryCDHIV());
		this.setRelapseFemalePulmonaryCDHIV(this.getRelapseFemalePulmonaryCDHIV() + table.getRelapseFemalePulmonaryCDHIV());
		this.setRelapsePulmonaryCDHIV(this.getRelapsePulmonaryCDHIV() + table.getRelapsePulmonaryCDHIV());
		this.setRelapseMaleExtrapulmonary04(this.getRelapseMaleExtrapulmonary04() + table.getRelapseMaleExtrapulmonary04());
		this.setRelapseFemaleExtrapulmonary04(this.getRelapseFemaleExtrapulmonary04() + table.getRelapseFemaleExtrapulmonary04());
		this.setRelapseMaleExtrapulmonary0514(this.getRelapseMaleExtrapulmonary0514() + table.getRelapseMaleExtrapulmonary0514());
		this.setRelapseFemaleExtrapulmonary0514(this.getRelapseFemaleExtrapulmonary0514() + table.getRelapseFemaleExtrapulmonary0514());
		this.setRelapseMaleExtrapulmonary1517(this.getRelapseMaleExtrapulmonary1517() + table.getRelapseMaleExtrapulmonary1517());
		this.setRelapseFemaleExtrapulmonary1517(this.getRelapseFemaleExtrapulmonary1517() + table.getRelapseFemaleExtrapulmonary1517());
		this.setRelapseMaleExtrapulmonary1819(this.getRelapseMaleExtrapulmonary1819() + table.getRelapseMaleExtrapulmonary1819());
		this.setRelapseFemaleExtrapulmonary1819(this.getRelapseFemaleExtrapulmonary1819() + table.getRelapseFemaleExtrapulmonary1819());
		this.setRelapseMaleExtrapulmonary2024(this.getRelapseMaleExtrapulmonary2024() + table.getRelapseMaleExtrapulmonary2024());
		this.setRelapseFemaleExtrapulmonary2024(this.getRelapseFemaleExtrapulmonary2024() + table.getRelapseFemaleExtrapulmonary2024());
		this.setRelapseMaleExtrapulmonary2534(this.getRelapseMaleExtrapulmonary2534() + table.getRelapseMaleExtrapulmonary2534());
		this.setRelapseFemaleExtrapulmonary2534(this.getRelapseFemaleExtrapulmonary2534() + table.getRelapseFemaleExtrapulmonary2534());
		this.setRelapseMaleExtrapulmonary3544(this.getRelapseMaleExtrapulmonary3544() + table.getRelapseMaleExtrapulmonary3544());
		this.setRelapseFemaleExtrapulmonary3544(this.getRelapseFemaleExtrapulmonary3544() + table.getRelapseFemaleExtrapulmonary3544());
		this.setRelapseMaleExtrapulmonary4554(this.getRelapseMaleExtrapulmonary4554() + table.getRelapseMaleExtrapulmonary4554());
		this.setRelapseFemaleExtrapulmonary4554(this.getRelapseFemaleExtrapulmonary4554() + table.getRelapseFemaleExtrapulmonary4554());
		this.setRelapseMaleExtrapulmonary5564(this.getRelapseMaleExtrapulmonary5564() + table.getRelapseMaleExtrapulmonary5564());
		this.setRelapseFemaleExtrapulmonary5564(this.getRelapseFemaleExtrapulmonary5564() + table.getRelapseFemaleExtrapulmonary5564());
		this.setRelapseMaleExtrapulmonary65(this.getRelapseMaleExtrapulmonary65() + table.getRelapseMaleExtrapulmonary65());
		this.setRelapseFemaleExtrapulmonary65(this.getRelapseFemaleExtrapulmonary65() + table.getRelapseFemaleExtrapulmonary65());
		this.setRelapseMaleExtrapulmonary(this.getRelapseMaleExtrapulmonary() + table.getRelapseMaleExtrapulmonary());
		this.setRelapseFemaleExtrapulmonary(this.getRelapseFemaleExtrapulmonary() + table.getRelapseFemaleExtrapulmonary());
		this.setRelapseExtrapulmonary(this.getRelapseExtrapulmonary() + table.getRelapseExtrapulmonary());
		this.setRelapseMaleExtrapulmonaryHIV04(this.getRelapseMaleExtrapulmonaryHIV04() + table.getRelapseMaleExtrapulmonaryHIV04());
		this.setRelapseFemaleExtrapulmonaryHIV04(this.getRelapseFemaleExtrapulmonaryHIV04() + table.getRelapseFemaleExtrapulmonaryHIV04());
		this.setRelapseMaleExtrapulmonaryHIV0514(this.getRelapseMaleExtrapulmonaryHIV0514() + table.getRelapseMaleExtrapulmonaryHIV0514());
		this.setRelapseFemaleExtrapulmonaryHIV0514(this.getRelapseFemaleExtrapulmonaryHIV0514() + table.getRelapseFemaleExtrapulmonaryHIV0514());
		this.setRelapseMaleExtrapulmonaryHIV1517(this.getRelapseMaleExtrapulmonaryHIV1517() + table.getRelapseMaleExtrapulmonaryHIV1517());
		this.setRelapseFemaleExtrapulmonaryHIV1517(this.getRelapseFemaleExtrapulmonaryHIV1517() + table.getRelapseFemaleExtrapulmonaryHIV1517());
		this.setRelapseMaleExtrapulmonaryHIV1819(this.getRelapseMaleExtrapulmonaryHIV1819() + table.getRelapseMaleExtrapulmonaryHIV1819());
		this.setRelapseFemaleExtrapulmonaryHIV1819(this.getRelapseFemaleExtrapulmonaryHIV1819() + table.getRelapseFemaleExtrapulmonaryHIV1819());
		this.setRelapseMaleExtrapulmonaryHIV2024(this.getRelapseMaleExtrapulmonaryHIV2024() + table.getRelapseMaleExtrapulmonaryHIV2024());
		this.setRelapseFemaleExtrapulmonaryHIV2024(this.getRelapseFemaleExtrapulmonaryHIV2024() + table.getRelapseFemaleExtrapulmonaryHIV2024());
		this.setRelapseMaleExtrapulmonaryHIV2534(this.getRelapseMaleExtrapulmonaryHIV2534() + table.getRelapseMaleExtrapulmonaryHIV2534());
		this.setRelapseFemaleExtrapulmonaryHIV2534(this.getRelapseFemaleExtrapulmonaryHIV2534() + table.getRelapseFemaleExtrapulmonaryHIV2534());
		this.setRelapseMaleExtrapulmonaryHIV3544(this.getRelapseMaleExtrapulmonaryHIV3544() + table.getRelapseMaleExtrapulmonaryHIV3544());
		this.setRelapseFemaleExtrapulmonaryHIV3544(this.getRelapseFemaleExtrapulmonaryHIV3544() + table.getRelapseFemaleExtrapulmonaryHIV3544());
		this.setRelapseMaleExtrapulmonaryHIV4554(this.getRelapseMaleExtrapulmonaryHIV4554() + table.getRelapseMaleExtrapulmonaryHIV4554());
		this.setRelapseFemaleExtrapulmonaryHIV4554(this.getRelapseFemaleExtrapulmonaryHIV4554() + table.getRelapseFemaleExtrapulmonaryHIV4554());
		this.setRelapseMaleExtrapulmonaryHIV5564(this.getRelapseMaleExtrapulmonaryHIV5564() + table.getRelapseMaleExtrapulmonaryHIV5564());
		this.setRelapseFemaleExtrapulmonaryHIV5564(this.getRelapseFemaleExtrapulmonaryHIV5564() + table.getRelapseFemaleExtrapulmonaryHIV5564());
		this.setRelapseMaleExtrapulmonaryHIV65(this.getRelapseMaleExtrapulmonaryHIV65() + table.getRelapseMaleExtrapulmonaryHIV65());
		this.setRelapseFemaleExtrapulmonaryHIV65(this.getRelapseFemaleExtrapulmonaryHIV65() + table.getRelapseFemaleExtrapulmonaryHIV65());
		this.setRelapseMaleExtrapulmonaryHIV(this.getRelapseMaleExtrapulmonaryHIV() + table.getRelapseMaleExtrapulmonaryHIV());
		this.setRelapseFemaleExtrapulmonaryHIV(this.getRelapseFemaleExtrapulmonaryHIV() + table.getRelapseFemaleExtrapulmonaryHIV());
		this.setRelapseExtrapulmonaryHIV(this.getRelapseExtrapulmonaryHIV() + table.getRelapseExtrapulmonaryHIV());
		this.setRelapseMale04(this.getRelapseMale04() + table.getRelapseMale04());
		this.setRelapseFemale04(this.getRelapseFemale04() + table.getRelapseFemale04());
		this.setRelapseMale0514(this.getRelapseMale0514() + table.getRelapseMale0514());
		this.setRelapseFemale0514(this.getRelapseFemale0514() + table.getRelapseFemale0514());
		this.setRelapseMale1517(this.getRelapseMale1517() + table.getRelapseMale1517());
		this.setRelapseFemale1517(this.getRelapseFemale1517() + table.getRelapseFemale1517());
		this.setRelapseMale1819(this.getRelapseMale1819() + table.getRelapseMale1819());
		this.setRelapseFemale1819(this.getRelapseFemale1819() + table.getRelapseFemale1819());
		this.setRelapseMale2024(this.getRelapseMale2024() + table.getRelapseMale2024());
		this.setRelapseFemale2024(this.getRelapseFemale2024() + table.getRelapseFemale2024());
		this.setRelapseMale2534(this.getRelapseMale2534() + table.getRelapseMale2534());
		this.setRelapseFemale2534(this.getRelapseFemale2534() + table.getRelapseFemale2534());
		this.setRelapseMale3544(this.getRelapseMale3544() + table.getRelapseMale3544());
		this.setRelapseFemale3544(this.getRelapseFemale3544() + table.getRelapseFemale3544());
		this.setRelapseMale4554(this.getRelapseMale4554() + table.getRelapseMale4554());
		this.setRelapseFemale4554(this.getRelapseFemale4554() + table.getRelapseFemale4554());
		this.setRelapseMale5564(this.getRelapseMale5564() + table.getRelapseMale5564());
		this.setRelapseFemale5564(this.getRelapseFemale5564() + table.getRelapseFemale5564());
		this.setRelapseMale65(this.getRelapseMale65() + table.getRelapseMale65());
		this.setRelapseFemale65(this.getRelapseFemale65() + table.getRelapseFemale65());
		this.setRelapseMale(this.getRelapseMale() + table.getRelapseMale());
		this.setRelapseFemale(this.getRelapseFemale() + table.getRelapseFemale());
		this.setRelapseAll(this.getRelapseAll() + table.getRelapseAll());
		this.setRelapseMaleHIV04(this.getRelapseMaleHIV04() + table.getRelapseMaleHIV04());
		this.setRelapseFemaleHIV04(this.getRelapseFemaleHIV04() + table.getRelapseFemaleHIV04());
		this.setRelapseMaleHIV0514(this.getRelapseMaleHIV0514() + table.getRelapseMaleHIV0514());
		this.setRelapseFemaleHIV0514(this.getRelapseFemaleHIV0514() + table.getRelapseFemaleHIV0514());
		this.setRelapseMaleHIV1517(this.getRelapseMaleHIV1517() + table.getRelapseMaleHIV1517());
		this.setRelapseFemaleHIV1517(this.getRelapseFemaleHIV1517() + table.getRelapseFemaleHIV1517());
		this.setRelapseMaleHIV1819(this.getRelapseMaleHIV1819() + table.getRelapseMaleHIV1819());
		this.setRelapseFemaleHIV1819(this.getRelapseFemaleHIV1819() + table.getRelapseFemaleHIV1819());
		this.setRelapseMaleHIV2024(this.getRelapseMaleHIV2024() + table.getRelapseMaleHIV2024());
		this.setRelapseFemaleHIV2024(this.getRelapseFemaleHIV2024() + table.getRelapseFemaleHIV2024());
		this.setRelapseMaleHIV2534(this.getRelapseMaleHIV2534() + table.getRelapseMaleHIV2534());
		this.setRelapseFemaleHIV2534(this.getRelapseFemaleHIV2534() + table.getRelapseFemaleHIV2534());
		this.setRelapseMaleHIV3544(this.getRelapseMaleHIV3544() + table.getRelapseMaleHIV3544());
		this.setRelapseFemaleHIV3544(this.getRelapseFemaleHIV3544() + table.getRelapseFemaleHIV3544());
		this.setRelapseMaleHIV4554(this.getRelapseMaleHIV4554() + table.getRelapseMaleHIV4554());
		this.setRelapseFemaleHIV4554(this.getRelapseFemaleHIV4554() + table.getRelapseFemaleHIV4554());
		this.setRelapseMaleHIV5564(this.getRelapseMaleHIV5564() + table.getRelapseMaleHIV5564());
		this.setRelapseFemaleHIV5564(this.getRelapseFemaleHIV5564() + table.getRelapseFemaleHIV5564());
		this.setRelapseMaleHIV65(this.getRelapseMaleHIV65() + table.getRelapseMaleHIV65());
		this.setRelapseFemaleHIV65(this.getRelapseFemaleHIV65() + table.getRelapseFemaleHIV65());
		this.setRelapseMaleHIV(this.getRelapseMaleHIV() + table.getRelapseMaleHIV());
		this.setRelapseFemaleHIV(this.getRelapseFemaleHIV() + table.getRelapseFemaleHIV());
		this.setRelapseAllHIV(this.getRelapseAllHIV() + table.getRelapseAllHIV());
		this.setHivPositive(this.getHivPositive() + table.getHivPositive());
		this.setHivTested(this.getHivTested() + table.getHivTested());
		this.setArtStarted(this.getArtStarted() + table.getArtStarted());
		this.setPctStarted(this.getPctStarted() + table.getPctStarted());
		this.setFailureMalePulmonaryBC(this.getFailureMalePulmonaryBC() + table.getFailureMalePulmonaryBC());
		this.setFailureFemalePulmonaryBC(this.getFailureFemalePulmonaryBC() + table.getFailureFemalePulmonaryBC());
		this.setFailurePulmonaryBC(this.getFailurePulmonaryBC() + table.getFailurePulmonaryBC());
		this.setFailureMalePulmonaryBCHIV(this.getFailureMalePulmonaryBCHIV() + table.getFailureMalePulmonaryBCHIV());
		this.setFailureFemalePulmonaryBCHIV(this.getFailureFemalePulmonaryBCHIV() + table.getFailureFemalePulmonaryBCHIV());
		this.setFailurePulmonaryBCHIV(this.getFailurePulmonaryBCHIV() + table.getFailurePulmonaryBCHIV());
		this.setFailureMalePulmonaryCD(this.getFailureMalePulmonaryCD() + table.getFailureMalePulmonaryCD());
		this.setFailureFemalePulmonaryCD(this.getFailureFemalePulmonaryCD() + table.getFailureFemalePulmonaryCD());
		this.setFailurePulmonaryCD(this.getFailurePulmonaryCD() + table.getFailurePulmonaryCD());
		this.setFailureMalePulmonaryCDHIV(this.getFailureMalePulmonaryCDHIV() + table.getFailureMalePulmonaryCDHIV());
		this.setFailureFemalePulmonaryCDHIV(this.getFailureFemalePulmonaryCDHIV() + table.getFailureFemalePulmonaryCDHIV());
		this.setFailurePulmonaryCDHIV(this.getFailurePulmonaryCDHIV() + table.getFailurePulmonaryCDHIV());
		this.setFailureMaleExtrapulmonary(this.getFailureMaleExtrapulmonary() + table.getFailureMaleExtrapulmonary());
		this.setFailureFemaleExtrapulmonary(this.getFailureFemaleExtrapulmonary() + table.getFailureFemaleExtrapulmonary());
		this.setFailureExtrapulmonary(this.getFailureExtrapulmonary() + table.getFailureExtrapulmonary());
		this.setFailureMaleExtrapulmonaryHIV(this.getFailureMaleExtrapulmonaryHIV() + table.getFailureMaleExtrapulmonaryHIV());
		this.setFailureFemaleExtrapulmonaryHIV(this.getFailureFemaleExtrapulmonaryHIV() + table.getFailureFemaleExtrapulmonaryHIV());
		this.setFailureExtrapulmonaryHIV(this.getFailureExtrapulmonaryHIV() + table.getFailureExtrapulmonaryHIV());
		this.setFailureMale(this.getFailureMale() + table.getFailureMale());
		this.setFailureFemale(this.getFailureFemale() + table.getFailureFemale());
		this.setFailureAll(this.getFailureAll() + table.getFailureAll());
		this.setFailureMaleHIV(this.getFailureMaleHIV() + table.getFailureMaleHIV());
		this.setFailureFemaleHIV(this.getFailureFemaleHIV() + table.getFailureFemaleHIV());
		this.setFailureAllHIV(this.getFailureAllHIV() + table.getFailureAllHIV());
		this.setDefaultMalePulmonaryBC(this.getDefaultMalePulmonaryBC() + table.getDefaultMalePulmonaryBC());
		this.setDefaultFemalePulmonaryBC(this.getDefaultFemalePulmonaryBC() + table.getDefaultFemalePulmonaryBC());
		this.setDefaultPulmonaryBC(this.getDefaultPulmonaryBC() + table.getDefaultPulmonaryBC());
		this.setDefaultMalePulmonaryBCHIV(this.getDefaultMalePulmonaryBCHIV() + table.getDefaultMalePulmonaryBCHIV());
		this.setDefaultFemalePulmonaryBCHIV(this.getDefaultFemalePulmonaryBCHIV() + table.getDefaultFemalePulmonaryBCHIV());
		this.setDefaultPulmonaryBCHIV(this.getDefaultPulmonaryBCHIV() + table.getDefaultPulmonaryBCHIV());
		this.setDefaultMalePulmonaryCD(this.getDefaultMalePulmonaryCD() + table.getDefaultMalePulmonaryCD());
		this.setDefaultFemalePulmonaryCD(this.getDefaultFemalePulmonaryCD() + table.getDefaultFemalePulmonaryCD());
		this.setDefaultPulmonaryCD(this.getDefaultPulmonaryCD() + table.getDefaultPulmonaryCD());
		this.setDefaultMalePulmonaryCDHIV(this.getDefaultMalePulmonaryCDHIV() + table.getDefaultMalePulmonaryCDHIV());
		this.setDefaultFemalePulmonaryCDHIV(this.getDefaultFemalePulmonaryCDHIV() + table.getDefaultFemalePulmonaryCDHIV());
		this.setDefaultPulmonaryCDHIV(this.getDefaultPulmonaryCDHIV() + table.getDefaultPulmonaryCDHIV());
		this.setDefaultMaleExtrapulmonary(this.getDefaultMaleExtrapulmonary() + table.getDefaultMaleExtrapulmonary());
		this.setDefaultFemaleExtrapulmonary(this.getDefaultFemaleExtrapulmonary() + table.getDefaultFemaleExtrapulmonary());
		this.setDefaultExtrapulmonary(this.getDefaultExtrapulmonary() + table.getDefaultExtrapulmonary());
		this.setDefaultMaleExtrapulmonaryHIV(this.getDefaultMaleExtrapulmonaryHIV() + table.getDefaultMaleExtrapulmonaryHIV());
		this.setDefaultFemaleExtrapulmonaryHIV(this.getDefaultFemaleExtrapulmonaryHIV() + table.getDefaultFemaleExtrapulmonaryHIV());
		this.setDefaultExtrapulmonaryHIV(this.getDefaultExtrapulmonaryHIV() + table.getDefaultExtrapulmonaryHIV());
		this.setDefaultMale(this.getDefaultMale() + table.getDefaultMale());
		this.setDefaultFemale(this.getDefaultFemale() + table.getDefaultFemale());
		this.setDefaultAll(this.getDefaultAll() + table.getDefaultAll());
		this.setDefaultMaleHIV(this.getDefaultMaleHIV() + table.getDefaultMaleHIV());
		this.setDefaultFemaleHIV(this.getDefaultFemaleHIV() + table.getDefaultFemaleHIV());
		this.setDefaultAllHIV(this.getDefaultAllHIV() + table.getDefaultAllHIV());
		this.setOtherMalePulmonaryBC(this.getOtherMalePulmonaryBC() + table.getOtherMalePulmonaryBC());
		this.setOtherFemalePulmonaryBC(this.getOtherFemalePulmonaryBC() + table.getOtherFemalePulmonaryBC());
		this.setOtherPulmonaryBC(this.getOtherPulmonaryBC() + table.getOtherPulmonaryBC());
		this.setOtherMalePulmonaryBCHIV(this.getOtherMalePulmonaryBCHIV() + table.getOtherMalePulmonaryBCHIV());
		this.setOtherFemalePulmonaryBCHIV(this.getOtherFemalePulmonaryBCHIV() + table.getOtherFemalePulmonaryBCHIV());
		this.setOtherPulmonaryBCHIV(this.getOtherPulmonaryBCHIV() + table.getOtherPulmonaryBCHIV());
		this.setOtherMalePulmonaryCD(this.getOtherMalePulmonaryCD() + table.getOtherMalePulmonaryCD());
		this.setOtherFemalePulmonaryCD(this.getOtherFemalePulmonaryCD() + table.getOtherFemalePulmonaryCD());
		this.setOtherPulmonaryCD(this.getOtherPulmonaryCD() + table.getOtherPulmonaryCD());
		this.setOtherMalePulmonaryCDHIV(this.getOtherMalePulmonaryCDHIV() + table.getOtherMalePulmonaryCDHIV());
		this.setOtherFemalePulmonaryCDHIV(this.getOtherFemalePulmonaryCDHIV() + table.getOtherFemalePulmonaryCDHIV());
		this.setOtherPulmonaryCDHIV(this.getOtherPulmonaryCDHIV() + table.getOtherPulmonaryCDHIV());
		this.setOtherMaleExtrapulmonary(this.getOtherMaleExtrapulmonary() + table.getOtherMaleExtrapulmonary());
		this.setOtherFemaleExtrapulmonary(this.getOtherFemaleExtrapulmonary() + table.getOtherFemaleExtrapulmonary());
		this.setOtherExtrapulmonary(this.getOtherExtrapulmonary() + table.getOtherExtrapulmonary());
		this.setOtherMaleExtrapulmonaryHIV(this.getOtherMaleExtrapulmonaryHIV() + table.getOtherMaleExtrapulmonaryHIV());
		this.setOtherFemaleExtrapulmonaryHIV(this.getOtherFemaleExtrapulmonaryHIV() + table.getOtherFemaleExtrapulmonaryHIV());
		this.setOtherExtrapulmonaryHIV(this.getOtherExtrapulmonaryHIV() + table.getOtherExtrapulmonaryHIV());
		this.setOtherMale(this.getOtherMale() + table.getOtherMale());
		this.setOtherFemale(this.getOtherFemale() + table.getOtherFemale());
		this.setOtherAll(this.getOtherAll() + table.getOtherAll());
		this.setOtherMaleHIV(this.getOtherMaleHIV() + table.getOtherMaleHIV());
		this.setOtherFemaleHIV(this.getOtherFemaleHIV() + table.getOtherFemaleHIV());
		this.setOtherAllHIV(this.getOtherAllHIV() + table.getOtherAllHIV());
		this.setRetreatmentMalePulmonaryBC(this.getRetreatmentMalePulmonaryBC() + table.getRetreatmentMalePulmonaryBC());
		this.setRetreatmentFemalePulmonaryBC(this.getRetreatmentFemalePulmonaryBC() + table.getRetreatmentFemalePulmonaryBC());
		this.setRetreatmentPulmonaryBC(this.getRetreatmentPulmonaryBC() + table.getRetreatmentPulmonaryBC());
		this.setRetreatmentMalePulmonaryBCHIV(this.getRetreatmentMalePulmonaryBCHIV() + table.getRetreatmentMalePulmonaryBCHIV());
		this.setRetreatmentFemalePulmonaryBCHIV(this.getRetreatmentFemalePulmonaryBCHIV() + table.getRetreatmentFemalePulmonaryBCHIV());
		this.setRetreatmentPulmonaryBCHIV(this.getRetreatmentPulmonaryBCHIV() + table.getRetreatmentPulmonaryBCHIV());
		this.setRetreatmentMalePulmonaryCD(this.getRetreatmentMalePulmonaryCD() + table.getRetreatmentMalePulmonaryCD());
		this.setRetreatmentFemalePulmonaryCD(this.getRetreatmentFemalePulmonaryCD() + table.getRetreatmentFemalePulmonaryCD());
		this.setRetreatmentPulmonaryCD(this.getRetreatmentPulmonaryCD() + table.getRetreatmentPulmonaryCD());
		this.setRetreatmentMalePulmonaryCDHIV(this.getRetreatmentMalePulmonaryCDHIV() + table.getRetreatmentMalePulmonaryCDHIV());
		this.setRetreatmentFemalePulmonaryCDHIV(this.getRetreatmentFemalePulmonaryCDHIV() + table.getRetreatmentFemalePulmonaryCDHIV());
		this.setRetreatmentPulmonaryCDHIV(this.getRetreatmentPulmonaryCDHIV() + table.getRetreatmentPulmonaryCDHIV());
		this.setRetreatmentMaleExtrapulmonary(this.getRetreatmentMaleExtrapulmonary() + table.getRetreatmentMaleExtrapulmonary());
		this.setRetreatmentFemaleExtrapulmonary(this.getRetreatmentFemaleExtrapulmonary() + table.getRetreatmentFemaleExtrapulmonary());
		this.setRetreatmentExtrapulmonary(this.getRetreatmentExtrapulmonary() + table.getRetreatmentExtrapulmonary());
		this.setRetreatmentMaleExtrapulmonaryHIV(this.getRetreatmentMaleExtrapulmonaryHIV() + table.getRetreatmentMaleExtrapulmonaryHIV());
		this.setRetreatmentFemaleExtrapulmonaryHIV(this.getRetreatmentFemaleExtrapulmonaryHIV() + table.getRetreatmentFemaleExtrapulmonaryHIV());
		this.setRetreatmentExtrapulmonaryHIV(this.getRetreatmentExtrapulmonaryHIV() + table.getRetreatmentExtrapulmonaryHIV());
		this.setRetreatmentMale(this.getRetreatmentMale() + table.getRetreatmentMale());
		this.setRetreatmentFemale(this.getRetreatmentFemale() + table.getRetreatmentFemale());
		this.setRetreatmentAll(this.getRetreatmentAll() + table.getRetreatmentAll());
		this.setRetreatmentMaleHIV(this.getRetreatmentMaleHIV() + table.getRetreatmentMaleHIV());
		this.setRetreatmentFemaleHIV(this.getRetreatmentFemaleHIV() + table.getRetreatmentFemaleHIV());
		this.setRetreatmentAllHIV(this.getRetreatmentAllHIV() + table.getRetreatmentAllHIV());
		this.setTotalMalePulmonaryBC(this.getTotalMalePulmonaryBC() + table.getTotalMalePulmonaryBC());
		this.setTotalFemalePulmonaryBC(this.getTotalFemalePulmonaryBC() + table.getTotalFemalePulmonaryBC());
		this.setTotalPulmonaryBC(this.getTotalPulmonaryBC() + table.getTotalPulmonaryBC());
		this.setTotalMalePulmonaryBCHIV(this.getTotalMalePulmonaryBCHIV() + table.getTotalMalePulmonaryBCHIV());
		this.setTotalFemalePulmonaryBCHIV(this.getTotalFemalePulmonaryBCHIV() + table.getTotalFemalePulmonaryBCHIV());
		this.setTotalPulmonaryBCHIV(this.getTotalPulmonaryBCHIV() + table.getTotalPulmonaryBCHIV());
		this.setTotalMalePulmonaryCD(this.getTotalMalePulmonaryCD() + table.getTotalMalePulmonaryCD());
		this.setTotalFemalePulmonaryCD(this.getTotalFemalePulmonaryCD() + table.getTotalFemalePulmonaryCD());
		this.setTotalPulmonaryCD(this.getTotalPulmonaryCD() + table.getTotalPulmonaryCD());
		this.setTotalMalePulmonaryCDHIV(this.getTotalMalePulmonaryCDHIV() + table.getTotalMalePulmonaryCDHIV());
		this.setTotalFemalePulmonaryCDHIV(this.getTotalFemalePulmonaryCDHIV() + table.getTotalFemalePulmonaryCDHIV());
		this.setTotalPulmonaryCDHIV(this.getTotalPulmonaryCDHIV() + table.getTotalPulmonaryCDHIV());
		this.setTotalMaleExtrapulmonary(this.getTotalMaleExtrapulmonary() + table.getTotalMaleExtrapulmonary());
		this.setTotalFemaleExtrapulmonary(this.getTotalFemaleExtrapulmonary() + table.getTotalFemaleExtrapulmonary());
		this.setTotalExtrapulmonary(this.getTotalExtrapulmonary() + table.getTotalExtrapulmonary());
		this.setTotalMaleExtrapulmonaryHIV(this.getTotalMaleExtrapulmonaryHIV() + table.getTotalMaleExtrapulmonaryHIV());
		this.setTotalFemaleExtrapulmonaryHIV(this.getTotalFemaleExtrapulmonaryHIV() + table.getTotalFemaleExtrapulmonaryHIV());
		this.setTotalExtrapulmonaryHIV(this.getTotalExtrapulmonaryHIV() + table.getTotalExtrapulmonaryHIV());
		this.setTotalMale(this.getTotalMale() + table.getTotalMale());
		this.setTotalFemale(this.getTotalFemale() + table.getTotalFemale());
		this.setTotalAll(this.getTotalAll() + table.getTotalAll());
		this.setTotalMaleHIV(this.getTotalMaleHIV() + table.getTotalMaleHIV());
		this.setTotalFemaleHIV(this.getTotalFemaleHIV() + table.getTotalFemaleHIV());
		this.setTotalAllHIV(this.getTotalAllHIV() + table.getTotalAllHIV());

		
	}

	public Integer getWomenOfChildBearingAge() {
		return womenOfChildBearingAge;
	}

	public void setWomenOfChildBearingAge(Integer womenOfChildBearingAge) {
		this.womenOfChildBearingAge = womenOfChildBearingAge;
	}

	public Integer getPregnant() {
		return pregnant;
	}

	public void setPregnant(Integer pregnant) {
		this.pregnant = pregnant;
	}

	public Integer getContacts() {
		return contacts;
	}

	public void setContacts(Integer contacts) {
		this.contacts = contacts;
	}

	public Integer getMigrants() {
		return migrants;
	}

	public void setMigrants(Integer migrants) {
		this.migrants = migrants;
	}

	public Integer getPhcWorkers() {
		return phcWorkers;
	}

	public void setPhcWorkers(Integer phcWorkers) {
		this.phcWorkers = phcWorkers;
	}

	public Integer getTbServicesWorkers() {
		return tbServicesWorkers;
	}

	public void setTbServicesWorkers(Integer tbServicesWorkers) {
		this.tbServicesWorkers = tbServicesWorkers;
	}

	public Integer getDied() {
		return died;
	}

	public void setDied(Integer died) {
		this.died = died;
	}

	public Integer getDiedChildren() {
		return diedChildren;
	}

	public void setDiedChildren(Integer diedChildren) {
		this.diedChildren = diedChildren;
	}

	public Integer getDiedWomenOfChildBearingAge() {
		return diedWomenOfChildBearingAge;
	}

	public void setDiedWomenOfChildBearingAge(Integer diedWomenOfChildBearingAge) {
		this.diedWomenOfChildBearingAge = diedWomenOfChildBearingAge;
	}
	
}
