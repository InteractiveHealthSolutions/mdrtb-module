package org.openmrs.module.mdrtb.reporting;

import java.util.Date;
import java.util.HashMap;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;


public class TB03uData implements Comparable <TB03uData> {
	
	private Patient patient;
	private String gender;
	private String identifierDOTS;
	private Integer dotsYear;
	private String identifierMDR;
	private String tb03uRegistrationDate;
	private Integer ageAtTB03uRegistration;
	private String dateOfBirth;
	private String reg2Number;
	
	private String siteOfDisease;
	private Integer regGroup;
	
	private String mdrtbStatus;
	private String mdrConfDate;
	private String treatmentRegimen;
	private String tb03uTreatmentStartDate;
	
	private String treatmentLocation;
	
	  private HashMap<String, String> dstResults;
	    private String dstCollectionDate;
	    private String dstResultDate;
	    private String dstR;
	    private String dstH;
	    private String dstZ;
	    private String dstE;
	    private String dstS;
	    private String dstKm;
	    private String dstAm;
	    private String dstCm;
	    private String dstOfx;
	    private String dstMfx;
	    private String dstPto;
	    private String dstCs;
	    private String dstPAS;
	    private String dstLzd;
	    private String dstCfz;
	    private String dstBdq;
	    private String dstDlm;
	    private String drugResistance;
	    private String diagnosticMethod;
	private String hivTestResult;
	private String hivTestDate;
	private String artStartDate;
	private String cpStartDate;
	
	
	private String month0SmearResult;
	private String month1SmearResult;
	private String month2SmearResult;
	private String month3SmearResult;
	private String month4SmearResult;
	private String month5SmearResult;
	private String month6SmearResult;
	private String month7SmearResult;
	private String month8SmearResult;
	private String month9SmearResult;
	private String month10SmearResult;
	private String month11SmearResult;
	private String month12SmearResult;
	private String month15SmearResult;
	private String month18SmearResult;
	private String month21SmearResult;
	private String month24SmearResult;
	private String month27SmearResult;
	private String month30SmearResult;
	private String month33SmearResult;
	private String month36SmearResult;
	private String month0SmearResultDate;
	private String month1SmearResultDate;
	private String month2SmearResultDate;
	private String month3SmearResultDate;
	private String month4SmearResultDate;
	private String month5SmearResultDate;
	private String month6SmearResultDate;
	private String month7SmearResultDate;
	private String month8SmearResultDate;
	private String month9SmearResultDate;
	private String month10SmearResultDate;
	private String month11SmearResultDate;
	private String month12SmearResultDate;
	private String month15SmearResultDate;
	private String month18SmearResultDate;
	private String month21SmearResultDate;
	private String month24SmearResultDate;
	private String month27SmearResultDate;
	private String month30SmearResultDate;
	private String month33SmearResultDate;
	private String month36SmearResultDate;
	private String month0CultureResult;
	private String month1CultureResult;
	private String month2CultureResult;
	private String month3CultureResult;
	private String month4CultureResult;
	private String month5CultureResult;
	private String month6CultureResult;
	private String month7CultureResult;
	private String month8CultureResult;
	private String month9CultureResult;
	private String month10CultureResult;
	private String month11CultureResult;
	private String month12CultureResult;
	private String month15CultureResult;
	private String month18CultureResult;
	private String month21CultureResult;
	private String month24CultureResult;
	private String month27CultureResult;
	private String month30CultureResult;
	private String month33CultureResult;
	private String month36CultureResult;
	private String month0CultureResultDate;
	private String month1CultureResultDate;
	private String month2CultureResultDate;
	private String month3CultureResultDate;
	private String month4CultureResultDate;
	private String month5CultureResultDate;
	private String month6CultureResultDate;
	private String month7CultureResultDate;
	private String month8CultureResultDate;
	private String month9CultureResultDate;
	private String month10CultureResultDate;
	private String month11CultureResultDate;
	private String month12CultureResultDate;
	private String month15CultureResultDate;
	private String month18CultureResultDate;
	private String month21CultureResultDate;
	private String month24CultureResultDate;
	private String month27CultureResultDate;
	private String month30CultureResultDate;
	private String month33CultureResultDate;
	private String month36CultureResultDate;

	
	private Integer tb03uTreatmentOutcome;
	private String tb03uTreatmentOutcomeDate;
	private Boolean diedOfTB;
	

  
    
   private String relapsed;
   private Integer relapseMonth;
    
   private String notes;
    
	public TB03uData() {
		// TODO Auto-generated constructor stub
		dstResults = new HashMap<String,String>();
	}

	

	public void setRegGroup(Integer regGroup) {
		
		if(regGroup == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.new.conceptId")))
			this.regGroup = 0;
		else if(regGroup == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterRelapse1.conceptId")))
			this.regGroup = 1;
		else if(regGroup == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterRelapse2.conceptId")))
			this.regGroup = 2;
		else if(regGroup == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterDefault1.conceptId")))
			this.regGroup = 3;
		else if(regGroup == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterDefault2.conceptId")))
			this.regGroup = 4;
		else if(regGroup == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterFailure1.conceptId")))
			this.regGroup = 5;
		else if(regGroup == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.afterFailure2.conceptId")))
			this.regGroup = 6;
		else if(regGroup == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.other.conceptId")))
			this.regGroup = 7;
		else if(regGroup == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.transferIn.conceptId")))
			this.regGroup = 8;
		
		
	}

	

	public void setTb03uTreatmentOutcome(Integer tb03uTreatmentOutcome) {
		//System.out.println("---->" + tb03uTreatmentOutcome);
		if(tb03uTreatmentOutcome.intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.outcome.cured.conceptId")))
			this.tb03uTreatmentOutcome = 0;
		else if(tb03uTreatmentOutcome.intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.outcome.txCompleted.conceptId")))
			this.tb03uTreatmentOutcome = 1;
		else if(tb03uTreatmentOutcome.intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.outcome.txFailure.conceptId")))
			this.tb03uTreatmentOutcome = 4;
		else if(tb03uTreatmentOutcome.intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.outcome.died.conceptId")))
		{
			if(diedOfTB)
				this.tb03uTreatmentOutcome = 2;
			else
				this.tb03uTreatmentOutcome = 3;
		}
			
		
		else if(tb03uTreatmentOutcome.intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.outcome.ltfu.conceptId")))
			this.tb03uTreatmentOutcome = 5;
		/*else if(tb03uTreatmentOutcome == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.outcome.canceled.conceptId")))
			this.tb03uTreatmentOutcome = 6;
		else if(tb03uTreatmentOutcome == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("dotsreports.outcome.sld2.conceptId")))
			this.tb03uTreatmentOutcome = 7;*/
		else if(tb03uTreatmentOutcome.intValue() == Integer.parseInt(Context.getAdministrationService().getGlobalProperty("mdrtb.outcome.transferout.conceptId")))
			this.tb03uTreatmentOutcome = 6;
		
		//System.out.println("---->" + this.tb03uTreatmentOutcome);
	}

	

	
	public HashMap getDstResults() {
		return dstResults;
	}


	public String getDstCollectionDate() {
		return dstCollectionDate;
	}

	public void setDstCollectionDate(String dstCollectionDate) {
		this.dstCollectionDate = dstCollectionDate;
	}

	public String getDstResultDate() {
		return dstResultDate;
	}

	public void setDstResultDate(String dstResultDate) {
		this.dstResultDate = dstResultDate;
	}

	public String getDstR() {
		return dstResults.get("R");
	}

	public void setDstR(String dstR) {
		this.dstR = dstR;
	}

	public String getDstH() {
		return dstResults.get("H");
	}

	public void setDstH(String dstH) {
		this.dstH = dstH;
	}

	public String getDstZ() {
		return dstResults.get("Z");
	}

	public void setDstZ(String dstZ) {
		this.dstZ = dstZ;
	}

	public String getDstE() {
		return dstResults.get("E");
	}

	public void setDstE(String dstE) {
		this.dstE = dstE;
	}

	public String getDstS() {
		return dstResults.get("S");
	}

	public void setDstS(String dstS) {
		this.dstS = dstS;
	}

	public String getDstKm() {
		return dstResults.get("KM");
	}

	public void setDstKm(String dstKm) {
		this.dstKm = dstKm;
	}

	public String getDstAm() {
		return dstResults.get("AM");
	}

	public void setDstAm(String dstAm) {
		this.dstAm = dstAm;
	}

	public String getDstCm() {
		return dstResults.get("CM");
	}

	public void setDstCm(String dstCm) {
		this.dstCm = dstCm;
	}

	public String getDstOfx() {
		String ofx = null;
		String lfx = null;
		
		String ret = null;
		ofx = dstResults.get("OFX");
		lfx = dstResults.get("LFX");
		
		if(ofx==null)
			return lfx;
		
		if(lfx == null)
			return ofx;
	
		return ret;
	}

	public void setDstOfx(String dstOfx) {
		this.dstOfx = dstOfx;
	}

	public String getDstMfx() {
		return dstResults.get("Moxi");
	}

	public void setDstMfx(String dstMfx) {
		this.dstMfx = dstMfx;
	}

	public String getDstPto() {
		return dstResults.get("Pto");
	}

	public void setDstPto(String dstPto) {
		this.dstPto = dstPto;
	}

	public String getDstCs() {
		return dstResults.get("CS");
	}

	public void setDstCs(String dstCs) {
		this.dstCs = dstCs;
	}

	public String getDstPAS() {
		return dstResults.get("PAS");
	}

	public void setDstPAS(String dstPAS) {
		this.dstPAS = dstPAS;
	}

	public String getDstLzd() {
		return dstResults.get("LZD");
	}

	public void setDstLzd(String lzd) {
		this.dstLzd = lzd;
	}

	public String getDstCfz() {
		return dstResults.get("CFZ");
	}

	public void setDstCfz(String cfz) {
		this.dstCfz = cfz;
	}

	public String getDstBdq() {
		return dstResults.get("BDQ");
	}

	public void setDstBdq(String bdq) {
		this.dstBdq = bdq;
	}

	public String getDstDlm() {
		return dstResults.get("DLM");
	}

	public void setDstDlm(String dlm) {
		dstDlm = dlm;
	}

	public void setDstResults(HashMap<String, String> dstResults) {
		this.dstResults = dstResults;
	}



	public Patient getPatient() {
		return patient;
	}



	public void setPatient(Patient patient) {
		this.patient = patient;
	}



	public String getIdentifierDOTS() {
		return identifierDOTS;
	}



	public void setIdentifierDOTS(String identifierDOTS) {
		this.identifierDOTS = identifierDOTS;
	}



	public String getIdentifierMDR() {
		return identifierMDR;
	}



	public void setIdentifierMDR(String identifierMDR) {
		this.identifierMDR = identifierMDR;
	}



	public String getTb03uRegistrationDate() {
		return tb03uRegistrationDate;
	}



	public void setTb03uRegistrationDate(String tb03uRegistrationDate) {
		this.tb03uRegistrationDate = tb03uRegistrationDate;
	}



	public Integer getAgeAtTB03uRegistration() {
		return ageAtTB03uRegistration;
	}



	public void setAgeAtTB03uRegistration(Integer ageAtTB03uRegistration) {
		this.ageAtTB03uRegistration = ageAtTB03uRegistration;
	}



	public String getDateOfBirth() {
		return dateOfBirth;
	}



	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}



	public String getReg2Number() {
		return reg2Number;
	}



	public void setReg2Number(String reg2Number) {
		this.reg2Number = reg2Number;
	}



	public String getSiteOfDisease() {
		return siteOfDisease;
	}



	public void setSiteOfDisease(String siteOfDisease) {
		this.siteOfDisease = siteOfDisease;
	}



	public String getMdrtbStatus() {
		return mdrtbStatus;
	}



	public void setMdrtbStatus(String mdrtbStatus) {
		this.mdrtbStatus = mdrtbStatus;
	}



	public String getMdrConfDate() {
		return mdrConfDate;
	}



	public void setMdrConfDate(String mdrConfDate) {
		this.mdrConfDate = mdrConfDate;
	}



	public String getTreatmentRegimen() {
		return treatmentRegimen;
	}



	public void setTreatmentRegimen(String treatmentRegimen) {
		this.treatmentRegimen = treatmentRegimen;
	}



	public String getTb03uTreatmentStartDate() {
		return tb03uTreatmentStartDate;
	}



	public void setTb03uTreatmentStartDate(String tb03uTreatmentStartDate) {
		this.tb03uTreatmentStartDate = tb03uTreatmentStartDate;
	}



	public String getTreatmentLocation() {
		return treatmentLocation;
	}



	public void setTreatmentLocation(String treatmentLocation) {
		this.treatmentLocation = treatmentLocation;
	}



	public String getDrugResistance() {
		return drugResistance;
	}



	public void setDrugResistance(String drugResistance) {
		this.drugResistance = drugResistance;
	}



	public String getDiagnosticMethod() {
		return diagnosticMethod;
	}



	public void setDiagnosticMethod(String diagnosticMethod) {
		this.diagnosticMethod = diagnosticMethod;
	}



	public String getHivTestResult() {
		return hivTestResult;
	}



	public void setHivTestResult(String hivTestResult) {
		this.hivTestResult = hivTestResult;
	}



	public String getHivTestDate() {
		return hivTestDate;
	}



	public void setHivTestDate(String hivTestDate) {
		this.hivTestDate = hivTestDate;
	}



	public String getArtStartDate() {
		return artStartDate;
	}



	public void setArtStartDate(String artStartDate) {
		this.artStartDate = artStartDate;
	}



	public String getCpStartDate() {
		return cpStartDate;
	}



	public void setCpStartDate(String cpStartDate) {
		this.cpStartDate = cpStartDate;
	}



	public String getMonth0SmearResult() {
		return month0SmearResult;
	}



	public void setMonth0SmearResult(String month0SmearResult) {
		this.month0SmearResult = month0SmearResult;
	}



	public String getMonth1SmearResult() {
		return month1SmearResult;
	}



	public void setMonth1SmearResult(String month1SmearResult) {
		this.month1SmearResult = month1SmearResult;
	}



	public String getMonth2SmearResult() {
		return month2SmearResult;
	}



	public void setMonth2SmearResult(String month2SmearResult) {
		this.month2SmearResult = month2SmearResult;
	}



	public String getMonth3SmearResult() {
		return month3SmearResult;
	}



	public void setMonth3SmearResult(String month3SmearResult) {
		this.month3SmearResult = month3SmearResult;
	}



	public String getMonth4SmearResult() {
		return month4SmearResult;
	}



	public void setMonth4SmearResult(String month4SmearResult) {
		this.month4SmearResult = month4SmearResult;
	}



	public String getMonth5SmearResult() {
		return month5SmearResult;
	}



	public void setMonth5SmearResult(String month5SmearResult) {
		this.month5SmearResult = month5SmearResult;
	}



	public String getMonth6SmearResult() {
		return month6SmearResult;
	}



	public void setMonth6SmearResult(String month6SmearResult) {
		this.month6SmearResult = month6SmearResult;
	}



	public String getMonth7SmearResult() {
		return month7SmearResult;
	}



	public void setMonth7SmearResult(String month7SmearResult) {
		this.month7SmearResult = month7SmearResult;
	}



	public String getMonth8SmearResult() {
		return month8SmearResult;
	}



	public void setMonth8SmearResult(String month8SmearResult) {
		this.month8SmearResult = month8SmearResult;
	}



	public String getMonth9SmearResult() {
		return month9SmearResult;
	}



	public void setMonth9SmearResult(String month9SmearResult) {
		this.month9SmearResult = month9SmearResult;
	}



	public String getMonth10SmearResult() {
		return month10SmearResult;
	}



	public void setMonth10SmearResult(String month10SmearResult) {
		this.month10SmearResult = month10SmearResult;
	}



	public String getMonth11SmearResult() {
		return month11SmearResult;
	}



	public void setMonth11SmearResult(String month11SmearResult) {
		this.month11SmearResult = month11SmearResult;
	}



	public String getMonth12SmearResult() {
		return month12SmearResult;
	}



	public void setMonth12SmearResult(String month12SmearResult) {
		this.month12SmearResult = month12SmearResult;
	}



	public String getMonth15SmearResult() {
		return month15SmearResult;
	}



	public void setMonth15SmearResult(String month15SmearResult) {
		this.month15SmearResult = month15SmearResult;
	}



	public String getMonth18SmearResult() {
		return month18SmearResult;
	}



	public void setMonth18SmearResult(String month18SmearResult) {
		this.month18SmearResult = month18SmearResult;
	}



	public String getMonth21SmearResult() {
		return month21SmearResult;
	}



	public void setMonth21SmearResult(String month21SmearResult) {
		this.month21SmearResult = month21SmearResult;
	}



	public String getMonth24SmearResult() {
		return month24SmearResult;
	}



	public void setMonth24SmearResult(String month24SmearResult) {
		this.month24SmearResult = month24SmearResult;
	}



	public String getMonth27SmearResult() {
		return month27SmearResult;
	}



	public void setMonth27SmearResult(String month27SmearResult) {
		this.month27SmearResult = month27SmearResult;
	}



	public String getMonth30SmearResult() {
		return month30SmearResult;
	}



	public void setMonth30SmearResult(String month30SmearResult) {
		this.month30SmearResult = month30SmearResult;
	}



	public String getMonth33SmearResult() {
		return month33SmearResult;
	}



	public void setMonth33SmearResult(String month33SmearResult) {
		this.month33SmearResult = month33SmearResult;
	}



	public String getMonth36SmearResult() {
		return month36SmearResult;
	}



	public void setMonth36SmearResult(String month36SmearResult) {
		this.month36SmearResult = month36SmearResult;
	}



	public String getMonth0SmearResultDate() {
		return month0SmearResultDate;
	}



	public void setMonth0SmearResultDate(String month0SmearResultDate) {
		this.month0SmearResultDate = month0SmearResultDate;
	}



	public String getMonth1SmearResultDate() {
		return month1SmearResultDate;
	}



	public void setMonth1SmearResultDate(String month1SmearResultDate) {
		this.month1SmearResultDate = month1SmearResultDate;
	}



	public String getMonth2SmearResultDate() {
		return month2SmearResultDate;
	}



	public void setMonth2SmearResultDate(String month2SmearResultDate) {
		this.month2SmearResultDate = month2SmearResultDate;
	}



	public String getMonth3SmearResultDate() {
		return month3SmearResultDate;
	}



	public void setMonth3SmearResultDate(String month3SmearResultDate) {
		this.month3SmearResultDate = month3SmearResultDate;
	}



	public String getMonth4SmearResultDate() {
		return month4SmearResultDate;
	}



	public void setMonth4SmearResultDate(String month4SmearResultDate) {
		this.month4SmearResultDate = month4SmearResultDate;
	}



	public String getMonth5SmearResultDate() {
		return month5SmearResultDate;
	}



	public void setMonth5SmearResultDate(String month5SmearResultDate) {
		this.month5SmearResultDate = month5SmearResultDate;
	}



	public String getMonth6SmearResultDate() {
		return month6SmearResultDate;
	}



	public void setMonth6SmearResultDate(String month6SmearResultDate) {
		this.month6SmearResultDate = month6SmearResultDate;
	}



	public String getMonth7SmearResultDate() {
		return month7SmearResultDate;
	}



	public void setMonth7SmearResultDate(String month7SmearResultDate) {
		this.month7SmearResultDate = month7SmearResultDate;
	}



	public String getMonth8SmearResultDate() {
		return month8SmearResultDate;
	}



	public void setMonth8SmearResultDate(String month8SmearResultDate) {
		this.month8SmearResultDate = month8SmearResultDate;
	}



	public String getMonth9SmearResultDate() {
		return month9SmearResultDate;
	}



	public void setMonth9SmearResultDate(String month9SmearResultDate) {
		this.month9SmearResultDate = month9SmearResultDate;
	}



	public String getMonth10SmearResultDate() {
		return month10SmearResultDate;
	}



	public void setMonth10SmearResultDate(String month10SmearResultDate) {
		this.month10SmearResultDate = month10SmearResultDate;
	}



	public String getMonth11SmearResultDate() {
		return month11SmearResultDate;
	}



	public void setMonth11SmearResultDate(String month11SmearResultDate) {
		this.month11SmearResultDate = month11SmearResultDate;
	}



	public String getMonth12SmearResultDate() {
		return month12SmearResultDate;
	}



	public void setMonth12SmearResultDate(String month12SmearResultDate) {
		this.month12SmearResultDate = month12SmearResultDate;
	}



	public String getMonth15SmearResultDate() {
		return month15SmearResultDate;
	}



	public void setMonth15SmearResultDate(String month15SmearResultDate) {
		this.month15SmearResultDate = month15SmearResultDate;
	}



	public String getMonth18SmearResultDate() {
		return month18SmearResultDate;
	}



	public void setMonth18SmearResultDate(String month18SmearResultDate) {
		this.month18SmearResultDate = month18SmearResultDate;
	}



	public String getMonth21SmearResultDate() {
		return month21SmearResultDate;
	}



	public void setMonth21SmearResultDate(String month21SmearResultDate) {
		this.month21SmearResultDate = month21SmearResultDate;
	}



	public String getMonth24SmearResultDate() {
		return month24SmearResultDate;
	}



	public void setMonth24SmearResultDate(String month24SmearResultDate) {
		this.month24SmearResultDate = month24SmearResultDate;
	}



	public String getMonth27SmearResultDate() {
		return month27SmearResultDate;
	}



	public void setMonth27SmearResultDate(String month27SmearResultDate) {
		this.month27SmearResultDate = month27SmearResultDate;
	}



	public String getMonth30SmearResultDate() {
		return month30SmearResultDate;
	}



	public void setMonth30SmearResultDate(String month30SmearResultDate) {
		this.month30SmearResultDate = month30SmearResultDate;
	}



	public String getMonth33SmearResultDate() {
		return month33SmearResultDate;
	}



	public void setMonth33SmearResultDate(String month33SmearResultDate) {
		this.month33SmearResultDate = month33SmearResultDate;
	}



	public String getMonth36SmearResultDate() {
		return month36SmearResultDate;
	}



	public void setMonth36SmearResultDate(String month36SmearResultDate) {
		this.month36SmearResultDate = month36SmearResultDate;
	}



	public String getMonth0CultureResult() {
		return month0CultureResult;
	}



	public void setMonth0CultureResult(String month0CultureResult) {
		this.month0CultureResult = month0CultureResult;
	}



	public String getMonth1CultureResult() {
		return month1CultureResult;
	}



	public void setMonth1CultureResult(String month1CultureResult) {
		this.month1CultureResult = month1CultureResult;
	}



	public String getMonth2CultureResult() {
		return month2CultureResult;
	}



	public void setMonth2CultureResult(String month2CultureResult) {
		this.month2CultureResult = month2CultureResult;
	}



	public String getMonth3CultureResult() {
		return month3CultureResult;
	}



	public void setMonth3CultureResult(String month3CultureResult) {
		this.month3CultureResult = month3CultureResult;
	}



	public String getMonth4CultureResult() {
		return month4CultureResult;
	}



	public void setMonth4CultureResult(String month4CultureResult) {
		this.month4CultureResult = month4CultureResult;
	}



	public String getMonth5CultureResult() {
		return month5CultureResult;
	}



	public void setMonth5CultureResult(String month5CultureResult) {
		this.month5CultureResult = month5CultureResult;
	}



	public String getMonth6CultureResult() {
		return month6CultureResult;
	}



	public void setMonth6CultureResult(String month6CultureResult) {
		this.month6CultureResult = month6CultureResult;
	}



	public String getMonth7CultureResult() {
		return month7CultureResult;
	}



	public void setMonth7CultureResult(String month7CultureResult) {
		this.month7CultureResult = month7CultureResult;
	}



	public String getMonth8CultureResult() {
		return month8CultureResult;
	}



	public void setMonth8CultureResult(String month8CultureResult) {
		this.month8CultureResult = month8CultureResult;
	}



	public String getMonth9CultureResult() {
		return month9CultureResult;
	}



	public void setMonth9CultureResult(String month9CultureResult) {
		this.month9CultureResult = month9CultureResult;
	}



	public String getMonth10CultureResult() {
		return month10CultureResult;
	}



	public void setMonth10CultureResult(String month10CultureResult) {
		this.month10CultureResult = month10CultureResult;
	}



	public String getMonth11CultureResult() {
		return month11CultureResult;
	}



	public void setMonth11CultureResult(String month11CultureResult) {
		this.month11CultureResult = month11CultureResult;
	}



	public String getMonth12CultureResult() {
		return month12CultureResult;
	}



	public void setMonth12CultureResult(String month12CultureResult) {
		this.month12CultureResult = month12CultureResult;
	}



	public String getMonth15CultureResult() {
		return month15CultureResult;
	}



	public void setMonth15CultureResult(String month15CultureResult) {
		this.month15CultureResult = month15CultureResult;
	}



	public String getMonth18CultureResult() {
		return month18CultureResult;
	}



	public void setMonth18CultureResult(String month18CultureResult) {
		this.month18CultureResult = month18CultureResult;
	}



	public String getMonth21CultureResult() {
		return month21CultureResult;
	}



	public void setMonth21CultureResult(String month21CultureResult) {
		this.month21CultureResult = month21CultureResult;
	}



	public String getMonth24CultureResult() {
		return month24CultureResult;
	}



	public void setMonth24CultureResult(String month24CultureResult) {
		this.month24CultureResult = month24CultureResult;
	}



	public String getMonth27CultureResult() {
		return month27CultureResult;
	}



	public void setMonth27CultureResult(String month27CultureResult) {
		this.month27CultureResult = month27CultureResult;
	}



	public String getMonth30CultureResult() {
		return month30CultureResult;
	}



	public void setMonth30CultureResult(String month30CultureResult) {
		this.month30CultureResult = month30CultureResult;
	}



	public String getMonth33CultureResult() {
		return month33CultureResult;
	}



	public void setMonth33CultureResult(String month33CultureResult) {
		this.month33CultureResult = month33CultureResult;
	}



	public String getMonth36CultureResult() {
		return month36CultureResult;
	}



	public void setMonth36CultureResult(String month36CultureResult) {
		this.month36CultureResult = month36CultureResult;
	}



	public String getMonth0CultureResultDate() {
		return month0CultureResultDate;
	}



	public void setMonth0CultureResultDate(String month0CultureResultDate) {
		this.month0CultureResultDate = month0CultureResultDate;
	}



	public String getMonth1CultureResultDate() {
		return month1CultureResultDate;
	}



	public void setMonth1CultureResultDate(String month1CultureResultDate) {
		this.month1CultureResultDate = month1CultureResultDate;
	}



	public String getMonth2CultureResultDate() {
		return month2CultureResultDate;
	}



	public void setMonth2CultureResultDate(String month2CultureResultDate) {
		this.month2CultureResultDate = month2CultureResultDate;
	}



	public String getMonth3CultureResultDate() {
		return month3CultureResultDate;
	}



	public void setMonth3CultureResultDate(String month3CultureResultDate) {
		this.month3CultureResultDate = month3CultureResultDate;
	}



	public String getMonth4CultureResultDate() {
		return month4CultureResultDate;
	}



	public void setMonth4CultureResultDate(String month4CultureResultDate) {
		this.month4CultureResultDate = month4CultureResultDate;
	}



	public String getMonth5CultureResultDate() {
		return month5CultureResultDate;
	}



	public void setMonth5CultureResultDate(String month5CultureResultDate) {
		this.month5CultureResultDate = month5CultureResultDate;
	}



	public String getMonth6CultureResultDate() {
		return month6CultureResultDate;
	}



	public void setMonth6CultureResultDate(String month6CultureResultDate) {
		this.month6CultureResultDate = month6CultureResultDate;
	}



	public String getMonth7CultureResultDate() {
		return month7CultureResultDate;
	}



	public void setMonth7CultureResultDate(String month7CultureResultDate) {
		this.month7CultureResultDate = month7CultureResultDate;
	}



	public String getMonth8CultureResultDate() {
		return month8CultureResultDate;
	}



	public void setMonth8CultureResultDate(String month8CultureResultDate) {
		this.month8CultureResultDate = month8CultureResultDate;
	}



	public String getMonth9CultureResultDate() {
		return month9CultureResultDate;
	}



	public void setMonth9CultureResultDate(String month9CultureResultDate) {
		this.month9CultureResultDate = month9CultureResultDate;
	}



	public String getMonth10CultureResultDate() {
		return month10CultureResultDate;
	}



	public void setMonth10CultureResultDate(String month10CultureResultDate) {
		this.month10CultureResultDate = month10CultureResultDate;
	}



	public String getMonth11CultureResultDate() {
		return month11CultureResultDate;
	}



	public void setMonth11CultureResultDate(String month11CultureResultDate) {
		this.month11CultureResultDate = month11CultureResultDate;
	}



	public String getMonth12CultureResultDate() {
		return month12CultureResultDate;
	}



	public void setMonth12CultureResultDate(String month12CultureResultDate) {
		this.month12CultureResultDate = month12CultureResultDate;
	}



	public String getMonth15CultureResultDate() {
		return month15CultureResultDate;
	}



	public void setMonth15CultureResultDate(String month15CultureResultDate) {
		this.month15CultureResultDate = month15CultureResultDate;
	}



	public String getMonth18CultureResultDate() {
		return month18CultureResultDate;
	}



	public void setMonth18CultureResultDate(String month18CultureResultDate) {
		this.month18CultureResultDate = month18CultureResultDate;
	}



	public String getMonth21CultureResultDate() {
		return month21CultureResultDate;
	}



	public void setMonth21CultureResultDate(String month21CultureResultDate) {
		this.month21CultureResultDate = month21CultureResultDate;
	}



	public String getMonth24CultureResultDate() {
		return month24CultureResultDate;
	}



	public void setMonth24CultureResultDate(String month24CultureResultDate) {
		this.month24CultureResultDate = month24CultureResultDate;
	}



	public String getMonth27CultureResultDate() {
		return month27CultureResultDate;
	}



	public void setMonth27CultureResultDate(String month27CultureResultDate) {
		this.month27CultureResultDate = month27CultureResultDate;
	}



	public String getMonth30CultureResultDate() {
		return month30CultureResultDate;
	}



	public void setMonth30CultureResultDate(String month30CultureResultDate) {
		this.month30CultureResultDate = month30CultureResultDate;
	}



	public String getMonth33CultureResultDate() {
		return month33CultureResultDate;
	}



	public void setMonth33CultureResultDate(String month33CultureResultDate) {
		this.month33CultureResultDate = month33CultureResultDate;
	}



	public String getMonth36CultureResultDate() {
		return month36CultureResultDate;
	}



	public void setMonth36CultureResultDate(String month36CultureResultDate) {
		this.month36CultureResultDate = month36CultureResultDate;
	}



	public String getTb03uTreatmentOutcomeDate() {
		return tb03uTreatmentOutcomeDate;
	}



	public void setTb03uTreatmentOutcomeDate(String tb03uTreatmentOutcomeDate) {
		this.tb03uTreatmentOutcomeDate = tb03uTreatmentOutcomeDate;
	}



	public Boolean getDiedOfTB() {
		return diedOfTB;
	}



	public void setDiedOfTB(Boolean diedOfTB) {
		this.diedOfTB = diedOfTB;
	}



	

	public String getRelapsed() {
		return relapsed;
	}



	public void setRelapsed(String relapsed) {
		this.relapsed = relapsed;
	}



	public Integer getRelapseMonth() {
		return relapseMonth;
	}



	public void setRelapseMonth(Integer relapseMonth) {
		this.relapseMonth = relapseMonth;
	}



	public String getNotes() {
		return notes;
	}



	public void setNotes(String notes) {
		this.notes = notes;
	}



	public Integer getRegGroup() {
		return regGroup;
	}



	public Integer getTb03uTreatmentOutcome() {
		return tb03uTreatmentOutcome;
	}



	public Integer getDotsYear() {
		return dotsYear;
	}



	public void setDotsYear(Integer dotsYear) {
		this.dotsYear = dotsYear;
	}
	
		
	@Override
	public int compareTo(TB03uData o) {
		// TODO Auto-generated method stub
		
		if(o.getIdentifierMDR()==null || getIdentifierMDR()==null)
			return 0;
		
		return identifierMDR.compareTo(o.getIdentifierMDR());

	}
	
	
	public String getGender() {
		if(patient.getGender().equals("M"))
			return Context.getMessageSourceService().getMessage("dotsreports.tb03.gender.male");
		else if (patient.getGender().equals("F"))
			return Context.getMessageSourceService().getMessage("dotsreports.tb03.gender.female");
		
		return "";
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
