package org.openmrs.module.mdrtb.reporting;

import java.util.Date;
import java.util.HashMap;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;


public class TB08uData  {
	
	private Integer newRegisteredShort;
	private Integer newCuredShort;
	private Integer newCompletedShort;
	private Integer newTxSuccessShort;
	private Integer newDiedTBShort;
	private Integer newDiedNotTBShort;
	private Integer newFailedShort;
	private Integer newDefaultedShort;
	private Integer newNotAssessedShort;
	private Integer newTotalShort;
	
	private Integer relapse1RegisteredShort;
	private Integer relapse1CuredShort;
	private Integer relapse1CompletedShort;
	private Integer relapse1TxSuccessShort;
	private Integer relapse1DiedTBShort;
	private Integer relapse1DiedNotTBShort;
	private Integer relapse1FailedShort;
	private Integer relapse1DefaultedShort;
	private Integer relapse1NotAssessedShort;
	private Integer relapse1TotalShort;
	
	private Integer relapse2RegisteredShort;
	private Integer relapse2CuredShort;
	private Integer relapse2CompletedShort;
	private Integer relapse2TxSuccessShort;
	private Integer relapse2DiedTBShort;
	private Integer relapse2DiedNotTBShort;
	private Integer relapse2FailedShort;
	private Integer relapse2DefaultedShort;
	private Integer relapse2NotAssessedShort;
	private Integer relapse2TotalShort;
	
	private Integer default1RegisteredShort;
	private Integer default1CuredShort;
	private Integer default1CompletedShort;
	private Integer default1TxSuccessShort;
	private Integer default1DiedTBShort;
	private Integer default1DiedNotTBShort;
	private Integer default1FailedShort;
	private Integer default1DefaultedShort;
	private Integer default1NotAssessedShort;
	private Integer default1TotalShort;
	
	private Integer default2RegisteredShort;
	private Integer default2CuredShort;
	private Integer default2CompletedShort;
	private Integer default2TxSuccessShort;
	private Integer default2DiedTBShort;
	private Integer default2DiedNotTBShort;
	private Integer default2FailedShort;
	private Integer default2DefaultedShort;
	private Integer default2NotAssessedShort;
	private Integer default2TotalShort;
	
	private Integer failure1RegisteredShort;
	private Integer failure1CuredShort;
	private Integer failure1CompletedShort;
	private Integer failure1TxSuccessShort;
	private Integer failure1DiedTBShort;
	private Integer failure1DiedNotTBShort;
	private Integer failure1FailedShort;
	private Integer failure1DefaultedShort;
	private Integer failure1NotAssessedShort;
	private Integer failure1TotalShort;
	
	private Integer failure2RegisteredShort;
	private Integer failure2CuredShort;
	private Integer failure2CompletedShort;
	private Integer failure2TxSuccessShort;
	private Integer failure2DiedTBShort;
	private Integer failure2DiedNotTBShort;
	private Integer failure2FailedShort;
	private Integer failure2DefaultedShort;
	private Integer failure2NotAssessedShort;
	private Integer failure2TotalShort;
	
	private Integer otherRegisteredShort;
	private Integer otherCuredShort;
	private Integer otherCompletedShort;
	private Integer otherTxSuccessShort;
	private Integer otherDiedTBShort;
	private Integer otherDiedNotTBShort;
	private Integer otherFailedShort;
	private Integer otherDefaultedShort;
	private Integer otherNotAssessedShort;
	private Integer otherTotalShort;
	
	private Integer totalRegisteredShort;
	private Integer totalCuredShort;
	private Integer totalCompletedShort;
	private Integer totalTxSuccessShort;
	private Integer totalDiedTBShort;
	private Integer totalDiedNotTBShort;
	private Integer totalFailedShort;
	private Integer totalDefaultedShort;
	private Integer totalNotAssessedShort;
	private Integer totalTotalShort;
	
	//INDIV
	private Integer newRegisteredIndiv;
	private Integer newCuredIndiv;
	private Integer newCompletedIndiv;
	private Integer newTxSuccessIndiv;
	private Integer newDiedTBIndiv;
	private Integer newDiedNotTBIndiv;
	private Integer newFailedIndiv;
	private Integer newDefaultedIndiv;
	private Integer newNotAssessedIndiv;
	private Integer newTotalIndiv;
	
	private Integer relapse1RegisteredIndiv;
	private Integer relapse1CuredIndiv;
	private Integer relapse1CompletedIndiv;
	private Integer relapse1TxSuccessIndiv;
	private Integer relapse1DiedTBIndiv;
	private Integer relapse1DiedNotTBIndiv;
	private Integer relapse1FailedIndiv;
	private Integer relapse1DefaultedIndiv;
	private Integer relapse1NotAssessedIndiv;
	private Integer relapse1TotalIndiv;
	
	private Integer relapse2RegisteredIndiv;
	private Integer relapse2CuredIndiv;
	private Integer relapse2CompletedIndiv;
	private Integer relapse2TxSuccessIndiv;
	private Integer relapse2DiedTBIndiv;
	private Integer relapse2DiedNotTBIndiv;
	private Integer relapse2FailedIndiv;
	private Integer relapse2DefaultedIndiv;
	private Integer relapse2NotAssessedIndiv;
	private Integer relapse2TotalIndiv;
	
	private Integer default1RegisteredIndiv;
	private Integer default1CuredIndiv;
	private Integer default1CompletedIndiv;
	private Integer default1TxSuccessIndiv;
	private Integer default1DiedTBIndiv;
	private Integer default1DiedNotTBIndiv;
	private Integer default1FailedIndiv;
	private Integer default1DefaultedIndiv;
	private Integer default1NotAssessedIndiv;
	private Integer default1TotalIndiv;
	
	private Integer default2RegisteredIndiv;
	private Integer default2CuredIndiv;
	private Integer default2CompletedIndiv;
	private Integer default2TxSuccessIndiv;
	private Integer default2DiedTBIndiv;
	private Integer default2DiedNotTBIndiv;
	private Integer default2FailedIndiv;
	private Integer default2DefaultedIndiv;
	private Integer default2NotAssessedIndiv;
	private Integer default2TotalIndiv;
	
	private Integer failure1RegisteredIndiv;
	private Integer failure1CuredIndiv;
	private Integer failure1CompletedIndiv;
	private Integer failure1TxSuccessIndiv;
	private Integer failure1DiedTBIndiv;
	private Integer failure1DiedNotTBIndiv;
	private Integer failure1FailedIndiv;
	private Integer failure1DefaultedIndiv;
	private Integer failure1NotAssessedIndiv;
	private Integer failure1TotalIndiv;
	
	private Integer failure2RegisteredIndiv;
	private Integer failure2CuredIndiv;
	private Integer failure2CompletedIndiv;
	private Integer failure2TxSuccessIndiv;
	private Integer failure2DiedTBIndiv;
	private Integer failure2DiedNotTBIndiv;
	private Integer failure2FailedIndiv;
	private Integer failure2DefaultedIndiv;
	private Integer failure2NotAssessedIndiv;
	private Integer failure2TotalIndiv;
	
	private Integer otherRegisteredIndiv;
	private Integer otherCuredIndiv;
	private Integer otherCompletedIndiv;
	private Integer otherTxSuccessIndiv;
	private Integer otherDiedTBIndiv;
	private Integer otherDiedNotTBIndiv;
	private Integer otherFailedIndiv;
	private Integer otherDefaultedIndiv;
	private Integer otherNotAssessedIndiv;
	private Integer otherTotalIndiv;
	
	private Integer totalRegisteredIndiv;
	private Integer totalCuredIndiv;
	private Integer totalCompletedIndiv;
	private Integer totalTxSuccessIndiv;
	private Integer totalDiedTBIndiv;
	private Integer totalDiedNotTBIndiv;
	private Integer totalFailedIndiv;
	private Integer totalDefaultedIndiv;
	private Integer totalNotAssessedIndiv;
	private Integer totalTotalIndiv;
	
	//STANDARD
	private Integer newRegisteredStandard;
	private Integer newCuredStandard;
	private Integer newCompletedStandard;
	private Integer newTxSuccessStandard;
	private Integer newDiedTBStandard;
	private Integer newDiedNotTBStandard;
	private Integer newFailedStandard;
	private Integer newDefaultedStandard;
	private Integer newNotAssessedStandard;
	private Integer newTotalStandard;
	
	private Integer relapse1RegisteredStandard;
	private Integer relapse1CuredStandard;
	private Integer relapse1CompletedStandard;
	private Integer relapse1TxSuccessStandard;
	private Integer relapse1DiedTBStandard;
	private Integer relapse1DiedNotTBStandard;
	private Integer relapse1FailedStandard;
	private Integer relapse1DefaultedStandard;
	private Integer relapse1NotAssessedStandard;
	private Integer relapse1TotalStandard;
	
	private Integer relapse2RegisteredStandard;
	private Integer relapse2CuredStandard;
	private Integer relapse2CompletedStandard;
	private Integer relapse2TxSuccessStandard;
	private Integer relapse2DiedTBStandard;
	private Integer relapse2DiedNotTBStandard;
	private Integer relapse2FailedStandard;
	private Integer relapse2DefaultedStandard;
	private Integer relapse2NotAssessedStandard;
	private Integer relapse2TotalStandard;
	
	private Integer default1RegisteredStandard;
	private Integer default1CuredStandard;
	private Integer default1CompletedStandard;
	private Integer default1TxSuccessStandard;
	private Integer default1DiedTBStandard;
	private Integer default1DiedNotTBStandard;
	private Integer default1FailedStandard;
	private Integer default1DefaultedStandard;
	private Integer default1NotAssessedStandard;
	private Integer default1TotalStandard;
	
	private Integer default2RegisteredStandard;
	private Integer default2CuredStandard;
	private Integer default2CompletedStandard;
	private Integer default2TxSuccessStandard;
	private Integer default2DiedTBStandard;
	private Integer default2DiedNotTBStandard;
	private Integer default2FailedStandard;
	private Integer default2DefaultedStandard;
	private Integer default2NotAssessedStandard;
	private Integer default2TotalStandard;
	
	private Integer failure1RegisteredStandard;
	private Integer failure1CuredStandard;
	private Integer failure1CompletedStandard;
	private Integer failure1TxSuccessStandard;
	private Integer failure1DiedTBStandard;
	private Integer failure1DiedNotTBStandard;
	private Integer failure1FailedStandard;
	private Integer failure1DefaultedStandard;
	private Integer failure1NotAssessedStandard;
	private Integer failure1TotalStandard;
	
	private Integer failure2RegisteredStandard;
	private Integer failure2CuredStandard;
	private Integer failure2CompletedStandard;
	private Integer failure2TxSuccessStandard;
	private Integer failure2DiedTBStandard;
	private Integer failure2DiedNotTBStandard;
	private Integer failure2FailedStandard;
	private Integer failure2DefaultedStandard;
	private Integer failure2NotAssessedStandard;
	private Integer failure2TotalStandard;
	
	private Integer otherRegisteredStandard;
	private Integer otherCuredStandard;
	private Integer otherCompletedStandard;
	private Integer otherTxSuccessStandard;
	private Integer otherDiedTBStandard;
	private Integer otherDiedNotTBStandard;
	private Integer otherFailedStandard;
	private Integer otherDefaultedStandard;
	private Integer otherNotAssessedStandard;
	private Integer otherTotalStandard;
	
	private Integer totalRegisteredStandard;
	private Integer totalCuredStandard;
	private Integer totalCompletedStandard;
	private Integer totalTxSuccessStandard;
	private Integer totalDiedTBStandard;
	private Integer totalDiedNotTBStandard;
	private Integer totalFailedStandard;
	private Integer totalDefaultedStandard;
	private Integer totalNotAssessedStandard;
	private Integer totalTotalStandard;
	
	public TB08uData() {
		 newRegisteredShort = 0;
		 newCuredShort = 0;
		 newCompletedShort = 0;
		 newTxSuccessShort = 0;
		 newDiedTBShort = 0;
		 newDiedNotTBShort = 0;
		 newFailedShort = 0;
		 newDefaultedShort = 0;
		 newNotAssessedShort = 0;
		 newTotalShort = 0;
		
		 relapse1RegisteredShort = 0;
		 relapse1CuredShort = 0;
		 relapse1CompletedShort = 0;
		 relapse1TxSuccessShort = 0;
		 relapse1DiedTBShort = 0;
		 relapse1DiedNotTBShort = 0;
		 relapse1FailedShort = 0;
		 relapse1DefaultedShort = 0;
		 relapse1NotAssessedShort = 0;
		 relapse1TotalShort = 0;
		
		 relapse2RegisteredShort = 0;
		 relapse2CuredShort = 0;
		 relapse2CompletedShort = 0;
		 relapse2TxSuccessShort = 0;
		 relapse2DiedTBShort = 0;
		 relapse2DiedNotTBShort = 0;
		 relapse2FailedShort = 0;
		 relapse2DefaultedShort = 0;
		 relapse2NotAssessedShort = 0;
		 relapse2TotalShort = 0;
		
		 default1RegisteredShort = 0;
		 default1CuredShort = 0;
		 default1CompletedShort = 0;
		 default1TxSuccessShort = 0;
		 default1DiedTBShort = 0;
		 default1DiedNotTBShort = 0;
		 default1FailedShort = 0;
		 default1DefaultedShort = 0;
		 default1NotAssessedShort = 0;
		 default1TotalShort = 0;
		
		 default2RegisteredShort = 0;
		 default2CuredShort = 0;
		 default2CompletedShort = 0;
		 default2TxSuccessShort = 0;
		 default2DiedTBShort = 0;
		 default2DiedNotTBShort = 0;
		 default2FailedShort = 0;
		 default2DefaultedShort = 0;
		 default2NotAssessedShort = 0;
		 default2TotalShort = 0;
		
		 failure1RegisteredShort = 0;
		 failure1CuredShort = 0;
		 failure1CompletedShort = 0;
		 failure1TxSuccessShort = 0;
		 failure1DiedTBShort = 0;
		 failure1DiedNotTBShort = 0;
		 failure1FailedShort = 0;
		 failure1DefaultedShort = 0;
		 failure1NotAssessedShort = 0;
		 failure1TotalShort = 0;
		
		 failure2RegisteredShort = 0;
		 failure2CuredShort = 0;
		 failure2CompletedShort = 0;
		 failure2TxSuccessShort = 0;
		 failure2DiedTBShort = 0;
		 failure2DiedNotTBShort = 0;
		 failure2FailedShort = 0;
		 failure2DefaultedShort = 0;
		 failure2NotAssessedShort = 0;
		 failure2TotalShort = 0;
		
		 otherRegisteredShort = 0;
		 otherCuredShort = 0;
		 otherCompletedShort = 0;
		 otherTxSuccessShort = 0;
		 otherDiedTBShort = 0;
		 otherDiedNotTBShort = 0;
		 otherFailedShort = 0;
		 otherDefaultedShort = 0;
		 otherNotAssessedShort = 0;
		 otherTotalShort = 0;
		
		 totalRegisteredShort = 0;
		 totalCuredShort = 0;
		 totalCompletedShort = 0;
		 totalTxSuccessShort = 0;
		 totalDiedTBShort = 0;
		 totalDiedNotTBShort = 0;
		 totalFailedShort = 0;
		 totalDefaultedShort = 0;
		 totalNotAssessedShort = 0;
		 totalTotalShort = 0;
		 
		 //INDIV
		 newRegisteredIndiv = 0;
		 newCuredIndiv = 0;
		 newCompletedIndiv = 0;
		 newTxSuccessIndiv = 0;
		 newDiedTBIndiv = 0;
		 newDiedNotTBIndiv = 0;
		 newFailedIndiv = 0;
		 newDefaultedIndiv = 0;
		 newNotAssessedIndiv = 0;
		 newTotalIndiv = 0;
		
		 relapse1RegisteredIndiv = 0;
		 relapse1CuredIndiv = 0;
		 relapse1CompletedIndiv = 0;
		 relapse1TxSuccessIndiv = 0;
		 relapse1DiedTBIndiv = 0;
		 relapse1DiedNotTBIndiv = 0;
		 relapse1FailedIndiv = 0;
		 relapse1DefaultedIndiv = 0;
		 relapse1NotAssessedIndiv = 0;
		 relapse1TotalIndiv = 0;
		
		 relapse2RegisteredIndiv = 0;
		 relapse2CuredIndiv = 0;
		 relapse2CompletedIndiv = 0;
		 relapse2TxSuccessIndiv = 0;
		 relapse2DiedTBIndiv = 0;
		 relapse2DiedNotTBIndiv = 0;
		 relapse2FailedIndiv = 0;
		 relapse2DefaultedIndiv = 0;
		 relapse2NotAssessedIndiv = 0;
		 relapse2TotalIndiv = 0;
		
		 default1RegisteredIndiv = 0;
		 default1CuredIndiv = 0;
		 default1CompletedIndiv = 0;
		 default1TxSuccessIndiv = 0;
		 default1DiedTBIndiv = 0;
		 default1DiedNotTBIndiv = 0;
		 default1FailedIndiv = 0;
		 default1DefaultedIndiv = 0;
		 default1NotAssessedIndiv = 0;
		 default1TotalIndiv = 0;
		
		 default2RegisteredIndiv = 0;
		 default2CuredIndiv = 0;
		 default2CompletedIndiv = 0;
		 default2TxSuccessIndiv = 0;
		 default2DiedTBIndiv = 0;
		 default2DiedNotTBIndiv = 0;
		 default2FailedIndiv = 0;
		 default2DefaultedIndiv = 0;
		 default2NotAssessedIndiv = 0;
		 default2TotalIndiv = 0;
		
		 failure1RegisteredIndiv = 0;
		 failure1CuredIndiv = 0;
		 failure1CompletedIndiv = 0;
		 failure1TxSuccessIndiv = 0;
		 failure1DiedTBIndiv = 0;
		 failure1DiedNotTBIndiv = 0;
		 failure1FailedIndiv = 0;
		 failure1DefaultedIndiv = 0;
		 failure1NotAssessedIndiv = 0;
		 failure1TotalIndiv = 0;
		
		 failure2RegisteredIndiv = 0;
		 failure2CuredIndiv = 0;
		 failure2CompletedIndiv = 0;
		 failure2TxSuccessIndiv = 0;
		 failure2DiedTBIndiv = 0;
		 failure2DiedNotTBIndiv = 0;
		 failure2FailedIndiv = 0;
		 failure2DefaultedIndiv = 0;
		 failure2NotAssessedIndiv = 0;
		 failure2TotalIndiv = 0;
		
		 otherRegisteredIndiv = 0;
		 otherCuredIndiv = 0;
		 otherCompletedIndiv = 0;
		 otherTxSuccessIndiv = 0;
		 otherDiedTBIndiv = 0;
		 otherDiedNotTBIndiv = 0;
		 otherFailedIndiv = 0;
		 otherDefaultedIndiv = 0;
		 otherNotAssessedIndiv = 0;
		 otherTotalIndiv = 0;
		
		 totalRegisteredIndiv = 0;
		 totalCuredIndiv = 0;
		 totalCompletedIndiv = 0;
		 totalTxSuccessIndiv = 0;
		 totalDiedTBIndiv = 0;
		 totalDiedNotTBIndiv = 0;
		 totalFailedIndiv = 0;
		 totalDefaultedIndiv = 0;
		 totalNotAssessedIndiv = 0;
		 totalTotalIndiv = 0;
		 
		 //STANDARD
		 
		 newRegisteredStandard = 0;
		 newCuredStandard = 0;
		 newCompletedStandard = 0;
		 newTxSuccessStandard = 0;
		 newDiedTBStandard = 0;
		 newDiedNotTBStandard = 0;
		 newFailedStandard = 0;
		 newDefaultedStandard = 0;
		 newNotAssessedStandard = 0;
		 newTotalStandard = 0;
		
		 relapse1RegisteredStandard = 0;
		 relapse1CuredStandard = 0;
		 relapse1CompletedStandard = 0;
		 relapse1TxSuccessStandard = 0;
		 relapse1DiedTBStandard = 0;
		 relapse1DiedNotTBStandard = 0;
		 relapse1FailedStandard = 0;
		 relapse1DefaultedStandard = 0;
		 relapse1NotAssessedStandard = 0;
		 relapse1TotalStandard = 0;
		
		 relapse2RegisteredStandard = 0;
		 relapse2CuredStandard = 0;
		 relapse2CompletedStandard = 0;
		 relapse2TxSuccessStandard = 0;
		 relapse2DiedTBStandard = 0;
		 relapse2DiedNotTBStandard = 0;
		 relapse2FailedStandard = 0;
		 relapse2DefaultedStandard = 0;
		 relapse2NotAssessedStandard = 0;
		 relapse2TotalStandard = 0;
		
		 default1RegisteredStandard = 0;
		 default1CuredStandard = 0;
		 default1CompletedStandard = 0;
		 default1TxSuccessStandard = 0;
		 default1DiedTBStandard = 0;
		 default1DiedNotTBStandard = 0;
		 default1FailedStandard = 0;
		 default1DefaultedStandard = 0;
		 default1NotAssessedStandard = 0;
		 default1TotalStandard = 0;
		
		 default2RegisteredStandard = 0;
		 default2CuredStandard = 0;
		 default2CompletedStandard = 0;
		 default2TxSuccessStandard = 0;
		 default2DiedTBStandard = 0;
		 default2DiedNotTBStandard = 0;
		 default2FailedStandard = 0;
		 default2DefaultedStandard = 0;
		 default2NotAssessedStandard = 0;
		 default2TotalStandard = 0;
		
		 failure1RegisteredStandard = 0;
		 failure1CuredStandard = 0;
		 failure1CompletedStandard = 0;
		 failure1TxSuccessStandard = 0;
		 failure1DiedTBStandard = 0;
		 failure1DiedNotTBStandard = 0;
		 failure1FailedStandard = 0;
		 failure1DefaultedStandard = 0;
		 failure1NotAssessedStandard = 0;
		 failure1TotalStandard = 0;
		
		 failure2RegisteredStandard = 0;
		 failure2CuredStandard = 0;
		 failure2CompletedStandard = 0;
		 failure2TxSuccessStandard = 0;
		 failure2DiedTBStandard = 0;
		 failure2DiedNotTBStandard = 0;
		 failure2FailedStandard = 0;
		 failure2DefaultedStandard = 0;
		 failure2NotAssessedStandard = 0;
		 failure2TotalStandard = 0;
		
		 otherRegisteredStandard = 0;
		 otherCuredStandard = 0;
		 otherCompletedStandard = 0;
		 otherTxSuccessStandard = 0;
		 otherDiedTBStandard = 0;
		 otherDiedNotTBStandard = 0;
		 otherFailedStandard = 0;
		 otherDefaultedStandard = 0;
		 otherNotAssessedStandard = 0;
		 otherTotalStandard = 0;
		
		 totalRegisteredStandard = 0;
		 totalCuredStandard = 0;
		 totalCompletedStandard = 0;
		 totalTxSuccessStandard = 0;
		 totalDiedTBStandard = 0;
		 totalDiedNotTBStandard = 0;
		 totalFailedStandard = 0;
		 totalDefaultedStandard = 0;
		 totalNotAssessedStandard = 0;
		 totalTotalStandard = 0;
	}

	public Integer getNewRegisteredShort() {
		return newRegisteredShort;
	}

	public void setNewRegisteredShort(Integer newRegisteredShort) {
		this.newRegisteredShort = newRegisteredShort;
	}

	public Integer getNewCuredShort() {
		return newCuredShort;
	}

	public void setNewCuredShort(Integer newCuredShort) {
		this.newCuredShort = newCuredShort;
	}

	public Integer getNewCompletedShort() {
		return newCompletedShort;
	}

	public void setNewCompletedShort(Integer newCompletedShort) {
		this.newCompletedShort = newCompletedShort;
	}

	public Integer getNewTxSuccessShort() {
		return newTxSuccessShort;
	}

	public void setNewTxSuccessShort(Integer newTxSuccessShort) {
		this.newTxSuccessShort = newTxSuccessShort;
	}

	public Integer getNewDiedTBShort() {
		return newDiedTBShort;
	}

	public void setNewDiedTBShort(Integer newDiedTBShort) {
		this.newDiedTBShort = newDiedTBShort;
	}

	public Integer getNewDiedNotTBShort() {
		return newDiedNotTBShort;
	}

	public void setNewDiedNotTBShort(Integer newDiedNotTBShort) {
		this.newDiedNotTBShort = newDiedNotTBShort;
	}

	public Integer getNewFailedShort() {
		return newFailedShort;
	}

	public void setNewFailedShort(Integer newFailedShort) {
		this.newFailedShort = newFailedShort;
	}

	public Integer getNewDefaultedShort() {
		return newDefaultedShort;
	}

	public void setNewDefaultedShort(Integer newDefaultedShort) {
		this.newDefaultedShort = newDefaultedShort;
	}

	public Integer getNewNotAssessedShort() {
		return newNotAssessedShort;
	}

	public void setNewNotAssessedShort(Integer newNotAssessedShort) {
		this.newNotAssessedShort = newNotAssessedShort;
	}

	public Integer getNewTotalShort() {
		return newTotalShort;
	}

	public void setNewTotalShort(Integer newTotalShort) {
		this.newTotalShort = newTotalShort;
	}

	public Integer getRelapse1RegisteredShort() {
		return relapse1RegisteredShort;
	}

	public void setRelapse1RegisteredShort(Integer relapse1RegisteredShort) {
		this.relapse1RegisteredShort = relapse1RegisteredShort;
	}

	public Integer getRelapse1CuredShort() {
		return relapse1CuredShort;
	}

	public void setRelapse1CuredShort(Integer relapse1CuredShort) {
		this.relapse1CuredShort = relapse1CuredShort;
	}

	public Integer getRelapse1CompletedShort() {
		return relapse1CompletedShort;
	}

	public void setRelapse1CompletedShort(Integer relapse1CompletedShort) {
		this.relapse1CompletedShort = relapse1CompletedShort;
	}

	public Integer getRelapse1TxSuccessShort() {
		return relapse1TxSuccessShort;
	}

	public void setRelapse1TxSuccessShort(Integer relapse1TxSuccessShort) {
		this.relapse1TxSuccessShort = relapse1TxSuccessShort;
	}

	public Integer getRelapse1DiedTBShort() {
		return relapse1DiedTBShort;
	}

	public void setRelapse1DiedTBShort(Integer relapse1DiedTBShort) {
		this.relapse1DiedTBShort = relapse1DiedTBShort;
	}

	public Integer getRelapse1DiedNotTBShort() {
		return relapse1DiedNotTBShort;
	}

	public void setRelapse1DiedNotTBShort(Integer relapse1DiedNotTBShort) {
		this.relapse1DiedNotTBShort = relapse1DiedNotTBShort;
	}

	public Integer getRelapse1FailedShort() {
		return relapse1FailedShort;
	}

	public void setRelapse1FailedShort(Integer relapse1FailedShort) {
		this.relapse1FailedShort = relapse1FailedShort;
	}

	public Integer getRelapse1DefaultedShort() {
		return relapse1DefaultedShort;
	}

	public void setRelapse1DefaultedShort(Integer relapse1DefaultedShort) {
		this.relapse1DefaultedShort = relapse1DefaultedShort;
	}

	public Integer getRelapse1NotAssessedShort() {
		return relapse1NotAssessedShort;
	}

	public void setRelapse1NotAssessedShort(Integer relapse1NotAssessedShort) {
		this.relapse1NotAssessedShort = relapse1NotAssessedShort;
	}

	public Integer getRelapse1TotalShort() {
		return relapse1TotalShort;
	}

	public void setRelapse1TotalShort(Integer relapse1TotalShort) {
		this.relapse1TotalShort = relapse1TotalShort;
	}

	public Integer getRelapse2RegisteredShort() {
		return relapse2RegisteredShort;
	}

	public void setRelapse2RegisteredShort(Integer relapse2RegisteredShort) {
		this.relapse2RegisteredShort = relapse2RegisteredShort;
	}

	public Integer getRelapse2CuredShort() {
		return relapse2CuredShort;
	}

	public void setRelapse2CuredShort(Integer relapse2CuredShort) {
		this.relapse2CuredShort = relapse2CuredShort;
	}

	public Integer getRelapse2CompletedShort() {
		return relapse2CompletedShort;
	}

	public void setRelapse2CompletedShort(Integer relapse2CompletedShort) {
		this.relapse2CompletedShort = relapse2CompletedShort;
	}

	public Integer getRelapse2TxSuccessShort() {
		return relapse2TxSuccessShort;
	}

	public void setRelapse2TxSuccessShort(Integer relapse2TxSuccessShort) {
		this.relapse2TxSuccessShort = relapse2TxSuccessShort;
	}

	public Integer getRelapse2DiedTBShort() {
		return relapse2DiedTBShort;
	}

	public void setRelapse2DiedTBShort(Integer relapse2DiedTBShort) {
		this.relapse2DiedTBShort = relapse2DiedTBShort;
	}

	public Integer getRelapse2DiedNotTBShort() {
		return relapse2DiedNotTBShort;
	}

	public void setRelapse2DiedNotTBShort(Integer relapse2DiedNotTBShort) {
		this.relapse2DiedNotTBShort = relapse2DiedNotTBShort;
	}

	public Integer getRelapse2FailedShort() {
		return relapse2FailedShort;
	}

	public void setRelapse2FailedShort(Integer relapse2FailedShort) {
		this.relapse2FailedShort = relapse2FailedShort;
	}

	public Integer getRelapse2DefaultedShort() {
		return relapse2DefaultedShort;
	}

	public void setRelapse2DefaultedShort(Integer relapse2DefaultedShort) {
		this.relapse2DefaultedShort = relapse2DefaultedShort;
	}

	public Integer getRelapse2NotAssessedShort() {
		return relapse2NotAssessedShort;
	}

	public void setRelapse2NotAssessedShort(Integer relapse2NotAssessedShort) {
		this.relapse2NotAssessedShort = relapse2NotAssessedShort;
	}

	public Integer getRelapse2TotalShort() {
		return relapse2TotalShort;
	}

	public void setRelapse2TotalShort(Integer relapse2TotalShort) {
		this.relapse2TotalShort = relapse2TotalShort;
	}

	public Integer getDefault1RegisteredShort() {
		return default1RegisteredShort;
	}

	public void setDefault1RegisteredShort(Integer default1RegisteredShort) {
		this.default1RegisteredShort = default1RegisteredShort;
	}

	public Integer getDefault1CuredShort() {
		return default1CuredShort;
	}

	public void setDefault1CuredShort(Integer default1CuredShort) {
		this.default1CuredShort = default1CuredShort;
	}

	public Integer getDefault1CompletedShort() {
		return default1CompletedShort;
	}

	public void setDefault1CompletedShort(Integer default1CompletedShort) {
		this.default1CompletedShort = default1CompletedShort;
	}

	public Integer getDefault1TxSuccessShort() {
		return default1TxSuccessShort;
	}

	public void setDefault1TxSuccessShort(Integer default1TxSuccessShort) {
		this.default1TxSuccessShort = default1TxSuccessShort;
	}

	public Integer getDefault1DiedTBShort() {
		return default1DiedTBShort;
	}

	public void setDefault1DiedTBShort(Integer default1DiedTBShort) {
		this.default1DiedTBShort = default1DiedTBShort;
	}

	public Integer getDefault1DiedNotTBShort() {
		return default1DiedNotTBShort;
	}

	public void setDefault1DiedNotTBShort(Integer default1DiedNotTBShort) {
		this.default1DiedNotTBShort = default1DiedNotTBShort;
	}

	public Integer getDefault1FailedShort() {
		return default1FailedShort;
	}

	public void setDefault1FailedShort(Integer default1FailedShort) {
		this.default1FailedShort = default1FailedShort;
	}

	public Integer getDefault1DefaultedShort() {
		return default1DefaultedShort;
	}

	public void setDefault1DefaultedShort(Integer default1DefaultedShort) {
		this.default1DefaultedShort = default1DefaultedShort;
	}

	public Integer getDefault1NotAssessedShort() {
		return default1NotAssessedShort;
	}

	public void setDefault1NotAssessedShort(Integer default1NotAssessedShort) {
		this.default1NotAssessedShort = default1NotAssessedShort;
	}

	public Integer getDefault1TotalShort() {
		return default1TotalShort;
	}

	public void setDefault1TotalShort(Integer default1TotalShort) {
		this.default1TotalShort = default1TotalShort;
	}

	public Integer getDefault2RegisteredShort() {
		return default2RegisteredShort;
	}

	public void setDefault2RegisteredShort(Integer default2RegisteredShort) {
		this.default2RegisteredShort = default2RegisteredShort;
	}

	public Integer getDefault2CuredShort() {
		return default2CuredShort;
	}

	public void setDefault2CuredShort(Integer default2CuredShort) {
		this.default2CuredShort = default2CuredShort;
	}

	public Integer getDefault2CompletedShort() {
		return default2CompletedShort;
	}

	public void setDefault2CompletedShort(Integer default2CompletedShort) {
		this.default2CompletedShort = default2CompletedShort;
	}

	public Integer getDefault2TxSuccessShort() {
		return default2TxSuccessShort;
	}

	public void setDefault2TxSuccessShort(Integer default2TxSuccessShort) {
		this.default2TxSuccessShort = default2TxSuccessShort;
	}

	public Integer getDefault2DiedTBShort() {
		return default2DiedTBShort;
	}

	public void setDefault2DiedTBShort(Integer default2DiedTBShort) {
		this.default2DiedTBShort = default2DiedTBShort;
	}

	public Integer getDefault2DiedNotTBShort() {
		return default2DiedNotTBShort;
	}

	public void setDefault2DiedNotTBShort(Integer default2DiedNotTBShort) {
		this.default2DiedNotTBShort = default2DiedNotTBShort;
	}

	public Integer getDefault2FailedShort() {
		return default2FailedShort;
	}

	public void setDefault2FailedShort(Integer default2FailedShort) {
		this.default2FailedShort = default2FailedShort;
	}

	public Integer getDefault2DefaultedShort() {
		return default2DefaultedShort;
	}

	public void setDefault2DefaultedShort(Integer default2DefaultedShort) {
		this.default2DefaultedShort = default2DefaultedShort;
	}

	public Integer getDefault2NotAssessedShort() {
		return default2NotAssessedShort;
	}

	public void setDefault2NotAssessedShort(Integer default2NotAssessedShort) {
		this.default2NotAssessedShort = default2NotAssessedShort;
	}

	public Integer getDefault2TotalShort() {
		return default2TotalShort;
	}

	public void setDefault2TotalShort(Integer default2TotalShort) {
		this.default2TotalShort = default2TotalShort;
	}

	public Integer getFailure1RegisteredShort() {
		return failure1RegisteredShort;
	}

	public void setFailure1RegisteredShort(Integer failure1RegisteredShort) {
		this.failure1RegisteredShort = failure1RegisteredShort;
	}

	public Integer getFailure1CuredShort() {
		return failure1CuredShort;
	}

	public void setFailure1CuredShort(Integer failure1CuredShort) {
		this.failure1CuredShort = failure1CuredShort;
	}

	public Integer getFailure1CompletedShort() {
		return failure1CompletedShort;
	}

	public void setFailure1CompletedShort(Integer failure1CompletedShort) {
		this.failure1CompletedShort = failure1CompletedShort;
	}

	public Integer getFailure1TxSuccessShort() {
		return failure1TxSuccessShort;
	}

	public void setFailure1TxSuccessShort(Integer failure1TxSuccessShort) {
		this.failure1TxSuccessShort = failure1TxSuccessShort;
	}

	public Integer getFailure1DiedTBShort() {
		return failure1DiedTBShort;
	}

	public void setFailure1DiedTBShort(Integer failure1DiedTBShort) {
		this.failure1DiedTBShort = failure1DiedTBShort;
	}

	public Integer getFailure1DiedNotTBShort() {
		return failure1DiedNotTBShort;
	}

	public void setFailure1DiedNotTBShort(Integer failure1DiedNotTBShort) {
		this.failure1DiedNotTBShort = failure1DiedNotTBShort;
	}

	public Integer getFailure1FailedShort() {
		return failure1FailedShort;
	}

	public void setFailure1FailedShort(Integer failure1FailedShort) {
		this.failure1FailedShort = failure1FailedShort;
	}

	public Integer getFailure1DefaultedShort() {
		return failure1DefaultedShort;
	}

	public void setFailure1DefaultedShort(Integer failure1DefaultedShort) {
		this.failure1DefaultedShort = failure1DefaultedShort;
	}

	public Integer getFailure1NotAssessedShort() {
		return failure1NotAssessedShort;
	}

	public void setFailure1NotAssessedShort(Integer failure1NotAssessedShort) {
		this.failure1NotAssessedShort = failure1NotAssessedShort;
	}

	public Integer getFailure1TotalShort() {
		return failure1TotalShort;
	}

	public void setFailure1TotalShort(Integer failure1TotalShort) {
		this.failure1TotalShort = failure1TotalShort;
	}

	public Integer getFailure2RegisteredShort() {
		return failure2RegisteredShort;
	}

	public void setFailure2RegisteredShort(Integer failure2RegisteredShort) {
		this.failure2RegisteredShort = failure2RegisteredShort;
	}

	public Integer getFailure2CuredShort() {
		return failure2CuredShort;
	}

	public void setFailure2CuredShort(Integer failure2CuredShort) {
		this.failure2CuredShort = failure2CuredShort;
	}

	public Integer getFailure2CompletedShort() {
		return failure2CompletedShort;
	}

	public void setFailure2CompletedShort(Integer failure2CompletedShort) {
		this.failure2CompletedShort = failure2CompletedShort;
	}

	public Integer getFailure2TxSuccessShort() {
		return failure2TxSuccessShort;
	}

	public void setFailure2TxSuccessShort(Integer failure2TxSuccessShort) {
		this.failure2TxSuccessShort = failure2TxSuccessShort;
	}

	public Integer getFailure2DiedTBShort() {
		return failure2DiedTBShort;
	}

	public void setFailure2DiedTBShort(Integer failure2DiedTBShort) {
		this.failure2DiedTBShort = failure2DiedTBShort;
	}

	public Integer getFailure2DiedNotTBShort() {
		return failure2DiedNotTBShort;
	}

	public void setFailure2DiedNotTBShort(Integer failure2DiedNotTBShort) {
		this.failure2DiedNotTBShort = failure2DiedNotTBShort;
	}

	public Integer getFailure2FailedShort() {
		return failure2FailedShort;
	}

	public void setFailure2FailedShort(Integer failure2FailedShort) {
		this.failure2FailedShort = failure2FailedShort;
	}

	public Integer getFailure2DefaultedShort() {
		return failure2DefaultedShort;
	}

	public void setFailure2DefaultedShort(Integer failure2DefaultedShort) {
		this.failure2DefaultedShort = failure2DefaultedShort;
	}

	public Integer getFailure2NotAssessedShort() {
		return failure2NotAssessedShort;
	}

	public void setFailure2NotAssessedShort(Integer failure2NotAssessedShort) {
		this.failure2NotAssessedShort = failure2NotAssessedShort;
	}

	public Integer getFailure2TotalShort() {
		return failure2TotalShort;
	}

	public void setFailure2TotalShort(Integer failure2TotalShort) {
		this.failure2TotalShort = failure2TotalShort;
	}

	public Integer getOtherRegisteredShort() {
		return otherRegisteredShort;
	}

	public void setOtherRegisteredShort(Integer otherRegisteredShort) {
		this.otherRegisteredShort = otherRegisteredShort;
	}

	public Integer getOtherCuredShort() {
		return otherCuredShort;
	}

	public void setOtherCuredShort(Integer otherCuredShort) {
		this.otherCuredShort = otherCuredShort;
	}

	public Integer getOtherCompletedShort() {
		return otherCompletedShort;
	}

	public void setOtherCompletedShort(Integer otherCompletedShort) {
		this.otherCompletedShort = otherCompletedShort;
	}

	public Integer getOtherTxSuccessShort() {
		return otherTxSuccessShort;
	}

	public void setOtherTxSuccessShort(Integer otherTxSuccessShort) {
		this.otherTxSuccessShort = otherTxSuccessShort;
	}

	public Integer getOtherDiedTBShort() {
		return otherDiedTBShort;
	}

	public void setOtherDiedTBShort(Integer otherDiedTBShort) {
		this.otherDiedTBShort = otherDiedTBShort;
	}

	public Integer getOtherDiedNotTBShort() {
		return otherDiedNotTBShort;
	}

	public void setOtherDiedNotTBShort(Integer otherDiedNotTBShort) {
		this.otherDiedNotTBShort = otherDiedNotTBShort;
	}

	public Integer getOtherFailedShort() {
		return otherFailedShort;
	}

	public void setOtherFailedShort(Integer otherFailedShort) {
		this.otherFailedShort = otherFailedShort;
	}

	public Integer getOtherDefaultedShort() {
		return otherDefaultedShort;
	}

	public void setOtherDefaultedShort(Integer otherDefaultedShort) {
		this.otherDefaultedShort = otherDefaultedShort;
	}

	public Integer getOtherNotAssessedShort() {
		return otherNotAssessedShort;
	}

	public void setOtherNotAssessedShort(Integer otherNotAssessedShort) {
		this.otherNotAssessedShort = otherNotAssessedShort;
	}

	public Integer getOtherTotalShort() {
		return otherTotalShort;
	}

	public void setOtherTotalShort(Integer otherTotalShort) {
		this.otherTotalShort = otherTotalShort;
	}

	public Integer getTotalRegisteredShort() {
		return totalRegisteredShort;
	}

	public void setTotalRegisteredShort(Integer totalRegisteredShort) {
		this.totalRegisteredShort = totalRegisteredShort;
	}

	public Integer getTotalCuredShort() {
		return totalCuredShort;
	}

	public void setTotalCuredShort(Integer totalCuredShort) {
		this.totalCuredShort = totalCuredShort;
	}

	public Integer getTotalCompletedShort() {
		return totalCompletedShort;
	}

	public void setTotalCompletedShort(Integer totalCompletedShort) {
		this.totalCompletedShort = totalCompletedShort;
	}

	public Integer getTotalTxSuccessShort() {
		return totalTxSuccessShort;
	}

	public void setTotalTxSuccessShort(Integer totalTxSuccessShort) {
		this.totalTxSuccessShort = totalTxSuccessShort;
	}

	public Integer getTotalDiedTBShort() {
		return totalDiedTBShort;
	}

	public void setTotalDiedTBShort(Integer totalDiedTBShort) {
		this.totalDiedTBShort = totalDiedTBShort;
	}

	public Integer getTotalDiedNotTBShort() {
		return totalDiedNotTBShort;
	}

	public void setTotalDiedNotTBShort(Integer totalDiedNotTBShort) {
		this.totalDiedNotTBShort = totalDiedNotTBShort;
	}

	public Integer getTotalFailedShort() {
		return totalFailedShort;
	}

	public void setTotalFailedShort(Integer totalFailedShort) {
		this.totalFailedShort = totalFailedShort;
	}

	public Integer getTotalDefaultedShort() {
		return totalDefaultedShort;
	}

	public void setTotalDefaultedShort(Integer totalDefaultedShort) {
		this.totalDefaultedShort = totalDefaultedShort;
	}

	public Integer getTotalNotAssessedShort() {
		return totalNotAssessedShort;
	}

	public void setTotalNotAssessedShort(Integer totalNotAssessedShort) {
		this.totalNotAssessedShort = totalNotAssessedShort;
	}

	public Integer getTotalTotalShort() {
		return totalTotalShort;
	}

	public void setTotalTotalShort(Integer totalTotalShort) {
		this.totalTotalShort = totalTotalShort;
	}

	public Integer getNewRegisteredIndiv() {
		return newRegisteredIndiv;
	}

	public void setNewRegisteredIndiv(Integer newRegisteredIndiv) {
		this.newRegisteredIndiv = newRegisteredIndiv;
	}

	public Integer getNewCuredIndiv() {
		return newCuredIndiv;
	}

	public void setNewCuredIndiv(Integer newCuredIndiv) {
		this.newCuredIndiv = newCuredIndiv;
	}

	public Integer getNewCompletedIndiv() {
		return newCompletedIndiv;
	}

	public void setNewCompletedIndiv(Integer newCompletedIndiv) {
		this.newCompletedIndiv = newCompletedIndiv;
	}

	public Integer getNewTxSuccessIndiv() {
		return newTxSuccessIndiv;
	}

	public void setNewTxSuccessIndiv(Integer newTxSuccessIndiv) {
		this.newTxSuccessIndiv = newTxSuccessIndiv;
	}

	public Integer getNewDiedTBIndiv() {
		return newDiedTBIndiv;
	}

	public void setNewDiedTBIndiv(Integer newDiedTBIndiv) {
		this.newDiedTBIndiv = newDiedTBIndiv;
	}

	public Integer getNewDiedNotTBIndiv() {
		return newDiedNotTBIndiv;
	}

	public void setNewDiedNotTBIndiv(Integer newDiedNotTBIndiv) {
		this.newDiedNotTBIndiv = newDiedNotTBIndiv;
	}

	public Integer getNewFailedIndiv() {
		return newFailedIndiv;
	}

	public void setNewFailedIndiv(Integer newFailedIndiv) {
		this.newFailedIndiv = newFailedIndiv;
	}

	public Integer getNewDefaultedIndiv() {
		return newDefaultedIndiv;
	}

	public void setNewDefaultedIndiv(Integer newDefaultedIndiv) {
		this.newDefaultedIndiv = newDefaultedIndiv;
	}

	public Integer getNewNotAssessedIndiv() {
		return newNotAssessedIndiv;
	}

	public void setNewNotAssessedIndiv(Integer newNotAssessedIndiv) {
		this.newNotAssessedIndiv = newNotAssessedIndiv;
	}

	public Integer getNewTotalIndiv() {
		return newTotalIndiv;
	}

	public void setNewTotalIndiv(Integer newTotalIndiv) {
		this.newTotalIndiv = newTotalIndiv;
	}

	public Integer getRelapse1RegisteredIndiv() {
		return relapse1RegisteredIndiv;
	}

	public void setRelapse1RegisteredIndiv(Integer relapse1RegisteredIndiv) {
		this.relapse1RegisteredIndiv = relapse1RegisteredIndiv;
	}

	public Integer getRelapse1CuredIndiv() {
		return relapse1CuredIndiv;
	}

	public void setRelapse1CuredIndiv(Integer relapse1CuredIndiv) {
		this.relapse1CuredIndiv = relapse1CuredIndiv;
	}

	public Integer getRelapse1CompletedIndiv() {
		return relapse1CompletedIndiv;
	}

	public void setRelapse1CompletedIndiv(Integer relapse1CompletedIndiv) {
		this.relapse1CompletedIndiv = relapse1CompletedIndiv;
	}

	public Integer getRelapse1TxSuccessIndiv() {
		return relapse1TxSuccessIndiv;
	}

	public void setRelapse1TxSuccessIndiv(Integer relapse1TxSuccessIndiv) {
		this.relapse1TxSuccessIndiv = relapse1TxSuccessIndiv;
	}

	public Integer getRelapse1DiedTBIndiv() {
		return relapse1DiedTBIndiv;
	}

	public void setRelapse1DiedTBIndiv(Integer relapse1DiedTBIndiv) {
		this.relapse1DiedTBIndiv = relapse1DiedTBIndiv;
	}

	public Integer getRelapse1DiedNotTBIndiv() {
		return relapse1DiedNotTBIndiv;
	}

	public void setRelapse1DiedNotTBIndiv(Integer relapse1DiedNotTBIndiv) {
		this.relapse1DiedNotTBIndiv = relapse1DiedNotTBIndiv;
	}

	public Integer getRelapse1FailedIndiv() {
		return relapse1FailedIndiv;
	}

	public void setRelapse1FailedIndiv(Integer relapse1FailedIndiv) {
		this.relapse1FailedIndiv = relapse1FailedIndiv;
	}

	public Integer getRelapse1DefaultedIndiv() {
		return relapse1DefaultedIndiv;
	}

	public void setRelapse1DefaultedIndiv(Integer relapse1DefaultedIndiv) {
		this.relapse1DefaultedIndiv = relapse1DefaultedIndiv;
	}

	public Integer getRelapse1NotAssessedIndiv() {
		return relapse1NotAssessedIndiv;
	}

	public void setRelapse1NotAssessedIndiv(Integer relapse1NotAssessedIndiv) {
		this.relapse1NotAssessedIndiv = relapse1NotAssessedIndiv;
	}

	public Integer getRelapse1TotalIndiv() {
		return relapse1TotalIndiv;
	}

	public void setRelapse1TotalIndiv(Integer relapse1TotalIndiv) {
		this.relapse1TotalIndiv = relapse1TotalIndiv;
	}

	public Integer getRelapse2RegisteredIndiv() {
		return relapse2RegisteredIndiv;
	}

	public void setRelapse2RegisteredIndiv(Integer relapse2RegisteredIndiv) {
		this.relapse2RegisteredIndiv = relapse2RegisteredIndiv;
	}

	public Integer getRelapse2CuredIndiv() {
		return relapse2CuredIndiv;
	}

	public void setRelapse2CuredIndiv(Integer relapse2CuredIndiv) {
		this.relapse2CuredIndiv = relapse2CuredIndiv;
	}

	public Integer getRelapse2CompletedIndiv() {
		return relapse2CompletedIndiv;
	}

	public void setRelapse2CompletedIndiv(Integer relapse2CompletedIndiv) {
		this.relapse2CompletedIndiv = relapse2CompletedIndiv;
	}

	public Integer getRelapse2TxSuccessIndiv() {
		return relapse2TxSuccessIndiv;
	}

	public void setRelapse2TxSuccessIndiv(Integer relapse2TxSuccessIndiv) {
		this.relapse2TxSuccessIndiv = relapse2TxSuccessIndiv;
	}

	public Integer getRelapse2DiedTBIndiv() {
		return relapse2DiedTBIndiv;
	}

	public void setRelapse2DiedTBIndiv(Integer relapse2DiedTBIndiv) {
		this.relapse2DiedTBIndiv = relapse2DiedTBIndiv;
	}

	public Integer getRelapse2DiedNotTBIndiv() {
		return relapse2DiedNotTBIndiv;
	}

	public void setRelapse2DiedNotTBIndiv(Integer relapse2DiedNotTBIndiv) {
		this.relapse2DiedNotTBIndiv = relapse2DiedNotTBIndiv;
	}

	public Integer getRelapse2FailedIndiv() {
		return relapse2FailedIndiv;
	}

	public void setRelapse2FailedIndiv(Integer relapse2FailedIndiv) {
		this.relapse2FailedIndiv = relapse2FailedIndiv;
	}

	public Integer getRelapse2DefaultedIndiv() {
		return relapse2DefaultedIndiv;
	}

	public void setRelapse2DefaultedIndiv(Integer relapse2DefaultedIndiv) {
		this.relapse2DefaultedIndiv = relapse2DefaultedIndiv;
	}

	public Integer getRelapse2NotAssessedIndiv() {
		return relapse2NotAssessedIndiv;
	}

	public void setRelapse2NotAssessedIndiv(Integer relapse2NotAssessedIndiv) {
		this.relapse2NotAssessedIndiv = relapse2NotAssessedIndiv;
	}

	public Integer getRelapse2TotalIndiv() {
		return relapse2TotalIndiv;
	}

	public void setRelapse2TotalIndiv(Integer relapse2TotalIndiv) {
		this.relapse2TotalIndiv = relapse2TotalIndiv;
	}

	public Integer getDefault1RegisteredIndiv() {
		return default1RegisteredIndiv;
	}

	public void setDefault1RegisteredIndiv(Integer default1RegisteredIndiv) {
		this.default1RegisteredIndiv = default1RegisteredIndiv;
	}

	public Integer getDefault1CuredIndiv() {
		return default1CuredIndiv;
	}

	public void setDefault1CuredIndiv(Integer default1CuredIndiv) {
		this.default1CuredIndiv = default1CuredIndiv;
	}

	public Integer getDefault1CompletedIndiv() {
		return default1CompletedIndiv;
	}

	public void setDefault1CompletedIndiv(Integer default1CompletedIndiv) {
		this.default1CompletedIndiv = default1CompletedIndiv;
	}

	public Integer getDefault1TxSuccessIndiv() {
		return default1TxSuccessIndiv;
	}

	public void setDefault1TxSuccessIndiv(Integer default1TxSuccessIndiv) {
		this.default1TxSuccessIndiv = default1TxSuccessIndiv;
	}

	public Integer getDefault1DiedTBIndiv() {
		return default1DiedTBIndiv;
	}

	public void setDefault1DiedTBIndiv(Integer default1DiedTBIndiv) {
		this.default1DiedTBIndiv = default1DiedTBIndiv;
	}

	public Integer getDefault1DiedNotTBIndiv() {
		return default1DiedNotTBIndiv;
	}

	public void setDefault1DiedNotTBIndiv(Integer default1DiedNotTBIndiv) {
		this.default1DiedNotTBIndiv = default1DiedNotTBIndiv;
	}

	public Integer getDefault1FailedIndiv() {
		return default1FailedIndiv;
	}

	public void setDefault1FailedIndiv(Integer default1FailedIndiv) {
		this.default1FailedIndiv = default1FailedIndiv;
	}

	public Integer getDefault1DefaultedIndiv() {
		return default1DefaultedIndiv;
	}

	public void setDefault1DefaultedIndiv(Integer default1DefaultedIndiv) {
		this.default1DefaultedIndiv = default1DefaultedIndiv;
	}

	public Integer getDefault1NotAssessedIndiv() {
		return default1NotAssessedIndiv;
	}

	public void setDefault1NotAssessedIndiv(Integer default1NotAssessedIndiv) {
		this.default1NotAssessedIndiv = default1NotAssessedIndiv;
	}

	public Integer getDefault1TotalIndiv() {
		return default1TotalIndiv;
	}

	public void setDefault1TotalIndiv(Integer default1TotalIndiv) {
		this.default1TotalIndiv = default1TotalIndiv;
	}

	public Integer getDefault2RegisteredIndiv() {
		return default2RegisteredIndiv;
	}

	public void setDefault2RegisteredIndiv(Integer default2RegisteredIndiv) {
		this.default2RegisteredIndiv = default2RegisteredIndiv;
	}

	public Integer getDefault2CuredIndiv() {
		return default2CuredIndiv;
	}

	public void setDefault2CuredIndiv(Integer default2CuredIndiv) {
		this.default2CuredIndiv = default2CuredIndiv;
	}

	public Integer getDefault2CompletedIndiv() {
		return default2CompletedIndiv;
	}

	public void setDefault2CompletedIndiv(Integer default2CompletedIndiv) {
		this.default2CompletedIndiv = default2CompletedIndiv;
	}

	public Integer getDefault2TxSuccessIndiv() {
		return default2TxSuccessIndiv;
	}

	public void setDefault2TxSuccessIndiv(Integer default2TxSuccessIndiv) {
		this.default2TxSuccessIndiv = default2TxSuccessIndiv;
	}

	public Integer getDefault2DiedTBIndiv() {
		return default2DiedTBIndiv;
	}

	public void setDefault2DiedTBIndiv(Integer default2DiedTBIndiv) {
		this.default2DiedTBIndiv = default2DiedTBIndiv;
	}

	public Integer getDefault2DiedNotTBIndiv() {
		return default2DiedNotTBIndiv;
	}

	public void setDefault2DiedNotTBIndiv(Integer default2DiedNotTBIndiv) {
		this.default2DiedNotTBIndiv = default2DiedNotTBIndiv;
	}

	public Integer getDefault2FailedIndiv() {
		return default2FailedIndiv;
	}

	public void setDefault2FailedIndiv(Integer default2FailedIndiv) {
		this.default2FailedIndiv = default2FailedIndiv;
	}

	public Integer getDefault2DefaultedIndiv() {
		return default2DefaultedIndiv;
	}

	public void setDefault2DefaultedIndiv(Integer default2DefaultedIndiv) {
		this.default2DefaultedIndiv = default2DefaultedIndiv;
	}

	public Integer getDefault2NotAssessedIndiv() {
		return default2NotAssessedIndiv;
	}

	public void setDefault2NotAssessedIndiv(Integer default2NotAssessedIndiv) {
		this.default2NotAssessedIndiv = default2NotAssessedIndiv;
	}

	public Integer getDefault2TotalIndiv() {
		return default2TotalIndiv;
	}

	public void setDefault2TotalIndiv(Integer default2TotalIndiv) {
		this.default2TotalIndiv = default2TotalIndiv;
	}

	public Integer getFailure1RegisteredIndiv() {
		return failure1RegisteredIndiv;
	}

	public void setFailure1RegisteredIndiv(Integer failure1RegisteredIndiv) {
		this.failure1RegisteredIndiv = failure1RegisteredIndiv;
	}

	public Integer getFailure1CuredIndiv() {
		return failure1CuredIndiv;
	}

	public void setFailure1CuredIndiv(Integer failure1CuredIndiv) {
		this.failure1CuredIndiv = failure1CuredIndiv;
	}

	public Integer getFailure1CompletedIndiv() {
		return failure1CompletedIndiv;
	}

	public void setFailure1CompletedIndiv(Integer failure1CompletedIndiv) {
		this.failure1CompletedIndiv = failure1CompletedIndiv;
	}

	public Integer getFailure1TxSuccessIndiv() {
		return failure1TxSuccessIndiv;
	}

	public void setFailure1TxSuccessIndiv(Integer failure1TxSuccessIndiv) {
		this.failure1TxSuccessIndiv = failure1TxSuccessIndiv;
	}

	public Integer getFailure1DiedTBIndiv() {
		return failure1DiedTBIndiv;
	}

	public void setFailure1DiedTBIndiv(Integer failure1DiedTBIndiv) {
		this.failure1DiedTBIndiv = failure1DiedTBIndiv;
	}

	public Integer getFailure1DiedNotTBIndiv() {
		return failure1DiedNotTBIndiv;
	}

	public void setFailure1DiedNotTBIndiv(Integer failure1DiedNotTBIndiv) {
		this.failure1DiedNotTBIndiv = failure1DiedNotTBIndiv;
	}

	public Integer getFailure1FailedIndiv() {
		return failure1FailedIndiv;
	}

	public void setFailure1FailedIndiv(Integer failure1FailedIndiv) {
		this.failure1FailedIndiv = failure1FailedIndiv;
	}

	public Integer getFailure1DefaultedIndiv() {
		return failure1DefaultedIndiv;
	}

	public void setFailure1DefaultedIndiv(Integer failure1DefaultedIndiv) {
		this.failure1DefaultedIndiv = failure1DefaultedIndiv;
	}

	public Integer getFailure1NotAssessedIndiv() {
		return failure1NotAssessedIndiv;
	}

	public void setFailure1NotAssessedIndiv(Integer failure1NotAssessedIndiv) {
		this.failure1NotAssessedIndiv = failure1NotAssessedIndiv;
	}

	public Integer getFailure1TotalIndiv() {
		return failure1TotalIndiv;
	}

	public void setFailure1TotalIndiv(Integer failure1TotalIndiv) {
		this.failure1TotalIndiv = failure1TotalIndiv;
	}

	public Integer getFailure2RegisteredIndiv() {
		return failure2RegisteredIndiv;
	}

	public void setFailure2RegisteredIndiv(Integer failure2RegisteredIndiv) {
		this.failure2RegisteredIndiv = failure2RegisteredIndiv;
	}

	public Integer getFailure2CuredIndiv() {
		return failure2CuredIndiv;
	}

	public void setFailure2CuredIndiv(Integer failure2CuredIndiv) {
		this.failure2CuredIndiv = failure2CuredIndiv;
	}

	public Integer getFailure2CompletedIndiv() {
		return failure2CompletedIndiv;
	}

	public void setFailure2CompletedIndiv(Integer failure2CompletedIndiv) {
		this.failure2CompletedIndiv = failure2CompletedIndiv;
	}

	public Integer getFailure2TxSuccessIndiv() {
		return failure2TxSuccessIndiv;
	}

	public void setFailure2TxSuccessIndiv(Integer failure2TxSuccessIndiv) {
		this.failure2TxSuccessIndiv = failure2TxSuccessIndiv;
	}

	public Integer getFailure2DiedTBIndiv() {
		return failure2DiedTBIndiv;
	}

	public void setFailure2DiedTBIndiv(Integer failure2DiedTBIndiv) {
		this.failure2DiedTBIndiv = failure2DiedTBIndiv;
	}

	public Integer getFailure2DiedNotTBIndiv() {
		return failure2DiedNotTBIndiv;
	}

	public void setFailure2DiedNotTBIndiv(Integer failure2DiedNotTBIndiv) {
		this.failure2DiedNotTBIndiv = failure2DiedNotTBIndiv;
	}

	public Integer getFailure2FailedIndiv() {
		return failure2FailedIndiv;
	}

	public void setFailure2FailedIndiv(Integer failure2FailedIndiv) {
		this.failure2FailedIndiv = failure2FailedIndiv;
	}

	public Integer getFailure2DefaultedIndiv() {
		return failure2DefaultedIndiv;
	}

	public void setFailure2DefaultedIndiv(Integer failure2DefaultedIndiv) {
		this.failure2DefaultedIndiv = failure2DefaultedIndiv;
	}

	public Integer getFailure2NotAssessedIndiv() {
		return failure2NotAssessedIndiv;
	}

	public void setFailure2NotAssessedIndiv(Integer failure2NotAssessedIndiv) {
		this.failure2NotAssessedIndiv = failure2NotAssessedIndiv;
	}

	public Integer getFailure2TotalIndiv() {
		return failure2TotalIndiv;
	}

	public void setFailure2TotalIndiv(Integer failure2TotalIndiv) {
		this.failure2TotalIndiv = failure2TotalIndiv;
	}

	public Integer getOtherRegisteredIndiv() {
		return otherRegisteredIndiv;
	}

	public void setOtherRegisteredIndiv(Integer otherRegisteredIndiv) {
		this.otherRegisteredIndiv = otherRegisteredIndiv;
	}

	public Integer getOtherCuredIndiv() {
		return otherCuredIndiv;
	}

	public void setOtherCuredIndiv(Integer otherCuredIndiv) {
		this.otherCuredIndiv = otherCuredIndiv;
	}

	public Integer getOtherCompletedIndiv() {
		return otherCompletedIndiv;
	}

	public void setOtherCompletedIndiv(Integer otherCompletedIndiv) {
		this.otherCompletedIndiv = otherCompletedIndiv;
	}

	public Integer getOtherTxSuccessIndiv() {
		return otherTxSuccessIndiv;
	}

	public void setOtherTxSuccessIndiv(Integer otherTxSuccessIndiv) {
		this.otherTxSuccessIndiv = otherTxSuccessIndiv;
	}

	public Integer getOtherDiedTBIndiv() {
		return otherDiedTBIndiv;
	}

	public void setOtherDiedTBIndiv(Integer otherDiedTBIndiv) {
		this.otherDiedTBIndiv = otherDiedTBIndiv;
	}

	public Integer getOtherDiedNotTBIndiv() {
		return otherDiedNotTBIndiv;
	}

	public void setOtherDiedNotTBIndiv(Integer otherDiedNotTBIndiv) {
		this.otherDiedNotTBIndiv = otherDiedNotTBIndiv;
	}

	public Integer getOtherFailedIndiv() {
		return otherFailedIndiv;
	}

	public void setOtherFailedIndiv(Integer otherFailedIndiv) {
		this.otherFailedIndiv = otherFailedIndiv;
	}

	public Integer getOtherDefaultedIndiv() {
		return otherDefaultedIndiv;
	}

	public void setOtherDefaultedIndiv(Integer otherDefaultedIndiv) {
		this.otherDefaultedIndiv = otherDefaultedIndiv;
	}

	public Integer getOtherNotAssessedIndiv() {
		return otherNotAssessedIndiv;
	}

	public void setOtherNotAssessedIndiv(Integer otherNotAssessedIndiv) {
		this.otherNotAssessedIndiv = otherNotAssessedIndiv;
	}

	public Integer getOtherTotalIndiv() {
		return otherTotalIndiv;
	}

	public void setOtherTotalIndiv(Integer otherTotalIndiv) {
		this.otherTotalIndiv = otherTotalIndiv;
	}

	public Integer getTotalRegisteredIndiv() {
		return totalRegisteredIndiv;
	}

	public void setTotalRegisteredIndiv(Integer totalRegisteredIndiv) {
		this.totalRegisteredIndiv = totalRegisteredIndiv;
	}

	public Integer getTotalCuredIndiv() {
		return totalCuredIndiv;
	}

	public void setTotalCuredIndiv(Integer totalCuredIndiv) {
		this.totalCuredIndiv = totalCuredIndiv;
	}

	public Integer getTotalCompletedIndiv() {
		return totalCompletedIndiv;
	}

	public void setTotalCompletedIndiv(Integer totalCompletedIndiv) {
		this.totalCompletedIndiv = totalCompletedIndiv;
	}

	public Integer getTotalTxSuccessIndiv() {
		return totalTxSuccessIndiv;
	}

	public void setTotalTxSuccessIndiv(Integer totalTxSuccessIndiv) {
		this.totalTxSuccessIndiv = totalTxSuccessIndiv;
	}

	public Integer getTotalDiedTBIndiv() {
		return totalDiedTBIndiv;
	}

	public void setTotalDiedTBIndiv(Integer totalDiedTBIndiv) {
		this.totalDiedTBIndiv = totalDiedTBIndiv;
	}

	public Integer getTotalDiedNotTBIndiv() {
		return totalDiedNotTBIndiv;
	}

	public void setTotalDiedNotTBIndiv(Integer totalDiedNotTBIndiv) {
		this.totalDiedNotTBIndiv = totalDiedNotTBIndiv;
	}

	public Integer getTotalFailedIndiv() {
		return totalFailedIndiv;
	}

	public void setTotalFailedIndiv(Integer totalFailedIndiv) {
		this.totalFailedIndiv = totalFailedIndiv;
	}

	public Integer getTotalDefaultedIndiv() {
		return totalDefaultedIndiv;
	}

	public void setTotalDefaultedIndiv(Integer totalDefaultedIndiv) {
		this.totalDefaultedIndiv = totalDefaultedIndiv;
	}

	public Integer getTotalNotAssessedIndiv() {
		return totalNotAssessedIndiv;
	}

	public void setTotalNotAssessedIndiv(Integer totalNotAssessedIndiv) {
		this.totalNotAssessedIndiv = totalNotAssessedIndiv;
	}

	public Integer getTotalTotalIndiv() {
		return totalTotalIndiv;
	}

	public void setTotalTotalIndiv(Integer totalTotalIndiv) {
		this.totalTotalIndiv = totalTotalIndiv;
	}

	public Integer getNewRegisteredStandard() {
		return newRegisteredStandard;
	}

	public void setNewRegisteredStandard(Integer newRegisteredStandard) {
		this.newRegisteredStandard = newRegisteredStandard;
	}

	public Integer getNewCuredStandard() {
		return newCuredStandard;
	}

	public void setNewCuredStandard(Integer newCuredStandard) {
		this.newCuredStandard = newCuredStandard;
	}

	public Integer getNewCompletedStandard() {
		return newCompletedStandard;
	}

	public void setNewCompletedStandard(Integer newCompletedStandard) {
		this.newCompletedStandard = newCompletedStandard;
	}

	public Integer getNewTxSuccessStandard() {
		return newTxSuccessStandard;
	}

	public void setNewTxSuccessStandard(Integer newTxSuccessStandard) {
		this.newTxSuccessStandard = newTxSuccessStandard;
	}

	public Integer getNewDiedTBStandard() {
		return newDiedTBStandard;
	}

	public void setNewDiedTBStandard(Integer newDiedTBStandard) {
		this.newDiedTBStandard = newDiedTBStandard;
	}

	public Integer getNewDiedNotTBStandard() {
		return newDiedNotTBStandard;
	}

	public void setNewDiedNotTBStandard(Integer newDiedNotTBStandard) {
		this.newDiedNotTBStandard = newDiedNotTBStandard;
	}

	public Integer getNewFailedStandard() {
		return newFailedStandard;
	}

	public void setNewFailedStandard(Integer newFailedStandard) {
		this.newFailedStandard = newFailedStandard;
	}

	public Integer getNewDefaultedStandard() {
		return newDefaultedStandard;
	}

	public void setNewDefaultedStandard(Integer newDefaultedStandard) {
		this.newDefaultedStandard = newDefaultedStandard;
	}

	public Integer getNewNotAssessedStandard() {
		return newNotAssessedStandard;
	}

	public void setNewNotAssessedStandard(Integer newNotAssessedStandard) {
		this.newNotAssessedStandard = newNotAssessedStandard;
	}

	public Integer getNewTotalStandard() {
		return newTotalStandard;
	}

	public void setNewTotalStandard(Integer newTotalStandard) {
		this.newTotalStandard = newTotalStandard;
	}

	public Integer getRelapse1RegisteredStandard() {
		return relapse1RegisteredStandard;
	}

	public void setRelapse1RegisteredStandard(Integer relapse1RegisteredStandard) {
		this.relapse1RegisteredStandard = relapse1RegisteredStandard;
	}

	public Integer getRelapse1CuredStandard() {
		return relapse1CuredStandard;
	}

	public void setRelapse1CuredStandard(Integer relapse1CuredStandard) {
		this.relapse1CuredStandard = relapse1CuredStandard;
	}

	public Integer getRelapse1CompletedStandard() {
		return relapse1CompletedStandard;
	}

	public void setRelapse1CompletedStandard(Integer relapse1CompletedStandard) {
		this.relapse1CompletedStandard = relapse1CompletedStandard;
	}

	public Integer getRelapse1TxSuccessStandard() {
		return relapse1TxSuccessStandard;
	}

	public void setRelapse1TxSuccessStandard(Integer relapse1TxSuccessStandard) {
		this.relapse1TxSuccessStandard = relapse1TxSuccessStandard;
	}

	public Integer getRelapse1DiedTBStandard() {
		return relapse1DiedTBStandard;
	}

	public void setRelapse1DiedTBStandard(Integer relapse1DiedTBStandard) {
		this.relapse1DiedTBStandard = relapse1DiedTBStandard;
	}

	public Integer getRelapse1DiedNotTBStandard() {
		return relapse1DiedNotTBStandard;
	}

	public void setRelapse1DiedNotTBStandard(Integer relapse1DiedNotTBStandard) {
		this.relapse1DiedNotTBStandard = relapse1DiedNotTBStandard;
	}

	public Integer getRelapse1FailedStandard() {
		return relapse1FailedStandard;
	}

	public void setRelapse1FailedStandard(Integer relapse1FailedStandard) {
		this.relapse1FailedStandard = relapse1FailedStandard;
	}

	public Integer getRelapse1DefaultedStandard() {
		return relapse1DefaultedStandard;
	}

	public void setRelapse1DefaultedStandard(Integer relapse1DefaultedStandard) {
		this.relapse1DefaultedStandard = relapse1DefaultedStandard;
	}

	public Integer getRelapse1NotAssessedStandard() {
		return relapse1NotAssessedStandard;
	}

	public void setRelapse1NotAssessedStandard(Integer relapse1NotAssessedStandard) {
		this.relapse1NotAssessedStandard = relapse1NotAssessedStandard;
	}

	public Integer getRelapse1TotalStandard() {
		return relapse1TotalStandard;
	}

	public void setRelapse1TotalStandard(Integer relapse1TotalStandard) {
		this.relapse1TotalStandard = relapse1TotalStandard;
	}

	public Integer getRelapse2RegisteredStandard() {
		return relapse2RegisteredStandard;
	}

	public void setRelapse2RegisteredStandard(Integer relapse2RegisteredStandard) {
		this.relapse2RegisteredStandard = relapse2RegisteredStandard;
	}

	public Integer getRelapse2CuredStandard() {
		return relapse2CuredStandard;
	}

	public void setRelapse2CuredStandard(Integer relapse2CuredStandard) {
		this.relapse2CuredStandard = relapse2CuredStandard;
	}

	public Integer getRelapse2CompletedStandard() {
		return relapse2CompletedStandard;
	}

	public void setRelapse2CompletedStandard(Integer relapse2CompletedStandard) {
		this.relapse2CompletedStandard = relapse2CompletedStandard;
	}

	public Integer getRelapse2TxSuccessStandard() {
		return relapse2TxSuccessStandard;
	}

	public void setRelapse2TxSuccessStandard(Integer relapse2TxSuccessStandard) {
		this.relapse2TxSuccessStandard = relapse2TxSuccessStandard;
	}

	public Integer getRelapse2DiedTBStandard() {
		return relapse2DiedTBStandard;
	}

	public void setRelapse2DiedTBStandard(Integer relapse2DiedTBStandard) {
		this.relapse2DiedTBStandard = relapse2DiedTBStandard;
	}

	public Integer getRelapse2DiedNotTBStandard() {
		return relapse2DiedNotTBStandard;
	}

	public void setRelapse2DiedNotTBStandard(Integer relapse2DiedNotTBStandard) {
		this.relapse2DiedNotTBStandard = relapse2DiedNotTBStandard;
	}

	public Integer getRelapse2FailedStandard() {
		return relapse2FailedStandard;
	}

	public void setRelapse2FailedStandard(Integer relapse2FailedStandard) {
		this.relapse2FailedStandard = relapse2FailedStandard;
	}

	public Integer getRelapse2DefaultedStandard() {
		return relapse2DefaultedStandard;
	}

	public void setRelapse2DefaultedStandard(Integer relapse2DefaultedStandard) {
		this.relapse2DefaultedStandard = relapse2DefaultedStandard;
	}

	public Integer getRelapse2NotAssessedStandard() {
		return relapse2NotAssessedStandard;
	}

	public void setRelapse2NotAssessedStandard(Integer relapse2NotAssessedStandard) {
		this.relapse2NotAssessedStandard = relapse2NotAssessedStandard;
	}

	public Integer getRelapse2TotalStandard() {
		return relapse2TotalStandard;
	}

	public void setRelapse2TotalStandard(Integer relapse2TotalStandard) {
		this.relapse2TotalStandard = relapse2TotalStandard;
	}

	public Integer getDefault1RegisteredStandard() {
		return default1RegisteredStandard;
	}

	public void setDefault1RegisteredStandard(Integer default1RegisteredStandard) {
		this.default1RegisteredStandard = default1RegisteredStandard;
	}

	public Integer getDefault1CuredStandard() {
		return default1CuredStandard;
	}

	public void setDefault1CuredStandard(Integer default1CuredStandard) {
		this.default1CuredStandard = default1CuredStandard;
	}

	public Integer getDefault1CompletedStandard() {
		return default1CompletedStandard;
	}

	public void setDefault1CompletedStandard(Integer default1CompletedStandard) {
		this.default1CompletedStandard = default1CompletedStandard;
	}

	public Integer getDefault1TxSuccessStandard() {
		return default1TxSuccessStandard;
	}

	public void setDefault1TxSuccessStandard(Integer default1TxSuccessStandard) {
		this.default1TxSuccessStandard = default1TxSuccessStandard;
	}

	public Integer getDefault1DiedTBStandard() {
		return default1DiedTBStandard;
	}

	public void setDefault1DiedTBStandard(Integer default1DiedTBStandard) {
		this.default1DiedTBStandard = default1DiedTBStandard;
	}

	public Integer getDefault1DiedNotTBStandard() {
		return default1DiedNotTBStandard;
	}

	public void setDefault1DiedNotTBStandard(Integer default1DiedNotTBStandard) {
		this.default1DiedNotTBStandard = default1DiedNotTBStandard;
	}

	public Integer getDefault1FailedStandard() {
		return default1FailedStandard;
	}

	public void setDefault1FailedStandard(Integer default1FailedStandard) {
		this.default1FailedStandard = default1FailedStandard;
	}

	public Integer getDefault1DefaultedStandard() {
		return default1DefaultedStandard;
	}

	public void setDefault1DefaultedStandard(Integer default1DefaultedStandard) {
		this.default1DefaultedStandard = default1DefaultedStandard;
	}

	public Integer getDefault1NotAssessedStandard() {
		return default1NotAssessedStandard;
	}

	public void setDefault1NotAssessedStandard(Integer default1NotAssessedStandard) {
		this.default1NotAssessedStandard = default1NotAssessedStandard;
	}

	public Integer getDefault1TotalStandard() {
		return default1TotalStandard;
	}

	public void setDefault1TotalStandard(Integer default1TotalStandard) {
		this.default1TotalStandard = default1TotalStandard;
	}

	public Integer getDefault2RegisteredStandard() {
		return default2RegisteredStandard;
	}

	public void setDefault2RegisteredStandard(Integer default2RegisteredStandard) {
		this.default2RegisteredStandard = default2RegisteredStandard;
	}

	public Integer getDefault2CuredStandard() {
		return default2CuredStandard;
	}

	public void setDefault2CuredStandard(Integer default2CuredStandard) {
		this.default2CuredStandard = default2CuredStandard;
	}

	public Integer getDefault2CompletedStandard() {
		return default2CompletedStandard;
	}

	public void setDefault2CompletedStandard(Integer default2CompletedStandard) {
		this.default2CompletedStandard = default2CompletedStandard;
	}

	public Integer getDefault2TxSuccessStandard() {
		return default2TxSuccessStandard;
	}

	public void setDefault2TxSuccessStandard(Integer default2TxSuccessStandard) {
		this.default2TxSuccessStandard = default2TxSuccessStandard;
	}

	public Integer getDefault2DiedTBStandard() {
		return default2DiedTBStandard;
	}

	public void setDefault2DiedTBStandard(Integer default2DiedTBStandard) {
		this.default2DiedTBStandard = default2DiedTBStandard;
	}

	public Integer getDefault2DiedNotTBStandard() {
		return default2DiedNotTBStandard;
	}

	public void setDefault2DiedNotTBStandard(Integer default2DiedNotTBStandard) {
		this.default2DiedNotTBStandard = default2DiedNotTBStandard;
	}

	public Integer getDefault2FailedStandard() {
		return default2FailedStandard;
	}

	public void setDefault2FailedStandard(Integer default2FailedStandard) {
		this.default2FailedStandard = default2FailedStandard;
	}

	public Integer getDefault2DefaultedStandard() {
		return default2DefaultedStandard;
	}

	public void setDefault2DefaultedStandard(Integer default2DefaultedStandard) {
		this.default2DefaultedStandard = default2DefaultedStandard;
	}

	public Integer getDefault2NotAssessedStandard() {
		return default2NotAssessedStandard;
	}

	public void setDefault2NotAssessedStandard(Integer default2NotAssessedStandard) {
		this.default2NotAssessedStandard = default2NotAssessedStandard;
	}

	public Integer getDefault2TotalStandard() {
		return default2TotalStandard;
	}

	public void setDefault2TotalStandard(Integer default2TotalStandard) {
		this.default2TotalStandard = default2TotalStandard;
	}

	public Integer getFailure1RegisteredStandard() {
		return failure1RegisteredStandard;
	}

	public void setFailure1RegisteredStandard(Integer failure1RegisteredStandard) {
		this.failure1RegisteredStandard = failure1RegisteredStandard;
	}

	public Integer getFailure1CuredStandard() {
		return failure1CuredStandard;
	}

	public void setFailure1CuredStandard(Integer failure1CuredStandard) {
		this.failure1CuredStandard = failure1CuredStandard;
	}

	public Integer getFailure1CompletedStandard() {
		return failure1CompletedStandard;
	}

	public void setFailure1CompletedStandard(Integer failure1CompletedStandard) {
		this.failure1CompletedStandard = failure1CompletedStandard;
	}

	public Integer getFailure1TxSuccessStandard() {
		return failure1TxSuccessStandard;
	}

	public void setFailure1TxSuccessStandard(Integer failure1TxSuccessStandard) {
		this.failure1TxSuccessStandard = failure1TxSuccessStandard;
	}

	public Integer getFailure1DiedTBStandard() {
		return failure1DiedTBStandard;
	}

	public void setFailure1DiedTBStandard(Integer failure1DiedTBStandard) {
		this.failure1DiedTBStandard = failure1DiedTBStandard;
	}

	public Integer getFailure1DiedNotTBStandard() {
		return failure1DiedNotTBStandard;
	}

	public void setFailure1DiedNotTBStandard(Integer failure1DiedNotTBStandard) {
		this.failure1DiedNotTBStandard = failure1DiedNotTBStandard;
	}

	public Integer getFailure1FailedStandard() {
		return failure1FailedStandard;
	}

	public void setFailure1FailedStandard(Integer failure1FailedStandard) {
		this.failure1FailedStandard = failure1FailedStandard;
	}

	public Integer getFailure1DefaultedStandard() {
		return failure1DefaultedStandard;
	}

	public void setFailure1DefaultedStandard(Integer failure1DefaultedStandard) {
		this.failure1DefaultedStandard = failure1DefaultedStandard;
	}

	public Integer getFailure1NotAssessedStandard() {
		return failure1NotAssessedStandard;
	}

	public void setFailure1NotAssessedStandard(Integer failure1NotAssessedStandard) {
		this.failure1NotAssessedStandard = failure1NotAssessedStandard;
	}

	public Integer getFailure1TotalStandard() {
		return failure1TotalStandard;
	}

	public void setFailure1TotalStandard(Integer failure1TotalStandard) {
		this.failure1TotalStandard = failure1TotalStandard;
	}

	public Integer getFailure2RegisteredStandard() {
		return failure2RegisteredStandard;
	}

	public void setFailure2RegisteredStandard(Integer failure2RegisteredStandard) {
		this.failure2RegisteredStandard = failure2RegisteredStandard;
	}

	public Integer getFailure2CuredStandard() {
		return failure2CuredStandard;
	}

	public void setFailure2CuredStandard(Integer failure2CuredStandard) {
		this.failure2CuredStandard = failure2CuredStandard;
	}

	public Integer getFailure2CompletedStandard() {
		return failure2CompletedStandard;
	}

	public void setFailure2CompletedStandard(Integer failure2CompletedStandard) {
		this.failure2CompletedStandard = failure2CompletedStandard;
	}

	public Integer getFailure2TxSuccessStandard() {
		return failure2TxSuccessStandard;
	}

	public void setFailure2TxSuccessStandard(Integer failure2TxSuccessStandard) {
		this.failure2TxSuccessStandard = failure2TxSuccessStandard;
	}

	public Integer getFailure2DiedTBStandard() {
		return failure2DiedTBStandard;
	}

	public void setFailure2DiedTBStandard(Integer failure2DiedTBStandard) {
		this.failure2DiedTBStandard = failure2DiedTBStandard;
	}

	public Integer getFailure2DiedNotTBStandard() {
		return failure2DiedNotTBStandard;
	}

	public void setFailure2DiedNotTBStandard(Integer failure2DiedNotTBStandard) {
		this.failure2DiedNotTBStandard = failure2DiedNotTBStandard;
	}

	public Integer getFailure2FailedStandard() {
		return failure2FailedStandard;
	}

	public void setFailure2FailedStandard(Integer failure2FailedStandard) {
		this.failure2FailedStandard = failure2FailedStandard;
	}

	public Integer getFailure2DefaultedStandard() {
		return failure2DefaultedStandard;
	}

	public void setFailure2DefaultedStandard(Integer failure2DefaultedStandard) {
		this.failure2DefaultedStandard = failure2DefaultedStandard;
	}

	public Integer getFailure2NotAssessedStandard() {
		return failure2NotAssessedStandard;
	}

	public void setFailure2NotAssessedStandard(Integer failure2NotAssessedStandard) {
		this.failure2NotAssessedStandard = failure2NotAssessedStandard;
	}

	public Integer getFailure2TotalStandard() {
		return failure2TotalStandard;
	}

	public void setFailure2TotalStandard(Integer failure2TotalStandard) {
		this.failure2TotalStandard = failure2TotalStandard;
	}

	public Integer getOtherRegisteredStandard() {
		return otherRegisteredStandard;
	}

	public void setOtherRegisteredStandard(Integer otherRegisteredStandard) {
		this.otherRegisteredStandard = otherRegisteredStandard;
	}

	public Integer getOtherCuredStandard() {
		return otherCuredStandard;
	}

	public void setOtherCuredStandard(Integer otherCuredStandard) {
		this.otherCuredStandard = otherCuredStandard;
	}

	public Integer getOtherCompletedStandard() {
		return otherCompletedStandard;
	}

	public void setOtherCompletedStandard(Integer otherCompletedStandard) {
		this.otherCompletedStandard = otherCompletedStandard;
	}

	public Integer getOtherTxSuccessStandard() {
		return otherTxSuccessStandard;
	}

	public void setOtherTxSuccessStandard(Integer otherTxSuccessStandard) {
		this.otherTxSuccessStandard = otherTxSuccessStandard;
	}

	public Integer getOtherDiedTBStandard() {
		return otherDiedTBStandard;
	}

	public void setOtherDiedTBStandard(Integer otherDiedTBStandard) {
		this.otherDiedTBStandard = otherDiedTBStandard;
	}

	public Integer getOtherDiedNotTBStandard() {
		return otherDiedNotTBStandard;
	}

	public void setOtherDiedNotTBStandard(Integer otherDiedNotTBStandard) {
		this.otherDiedNotTBStandard = otherDiedNotTBStandard;
	}

	public Integer getOtherFailedStandard() {
		return otherFailedStandard;
	}

	public void setOtherFailedStandard(Integer otherFailedStandard) {
		this.otherFailedStandard = otherFailedStandard;
	}

	public Integer getOtherDefaultedStandard() {
		return otherDefaultedStandard;
	}

	public void setOtherDefaultedStandard(Integer otherDefaultedStandard) {
		this.otherDefaultedStandard = otherDefaultedStandard;
	}

	public Integer getOtherNotAssessedStandard() {
		return otherNotAssessedStandard;
	}

	public void setOtherNotAssessedStandard(Integer otherNotAssessedStandard) {
		this.otherNotAssessedStandard = otherNotAssessedStandard;
	}

	public Integer getOtherTotalStandard() {
		return otherTotalStandard;
	}

	public void setOtherTotalStandard(Integer otherTotalStandard) {
		this.otherTotalStandard = otherTotalStandard;
	}

	public Integer getTotalRegisteredStandard() {
		return totalRegisteredStandard;
	}

	public void setTotalRegisteredStandard(Integer totalRegisteredStandard) {
		this.totalRegisteredStandard = totalRegisteredStandard;
	}

	public Integer getTotalCuredStandard() {
		return totalCuredStandard;
	}

	public void setTotalCuredStandard(Integer totalCuredStandard) {
		this.totalCuredStandard = totalCuredStandard;
	}

	public Integer getTotalCompletedStandard() {
		return totalCompletedStandard;
	}

	public void setTotalCompletedStandard(Integer totalCompletedStandard) {
		this.totalCompletedStandard = totalCompletedStandard;
	}

	public Integer getTotalTxSuccessStandard() {
		return totalTxSuccessStandard;
	}

	public void setTotalTxSuccessStandard(Integer totalTxSuccessStandard) {
		this.totalTxSuccessStandard = totalTxSuccessStandard;
	}

	public Integer getTotalDiedTBStandard() {
		return totalDiedTBStandard;
	}

	public void setTotalDiedTBStandard(Integer totalDiedTBStandard) {
		this.totalDiedTBStandard = totalDiedTBStandard;
	}

	public Integer getTotalDiedNotTBStandard() {
		return totalDiedNotTBStandard;
	}

	public void setTotalDiedNotTBStandard(Integer totalDiedNotTBStandard) {
		this.totalDiedNotTBStandard = totalDiedNotTBStandard;
	}

	public Integer getTotalFailedStandard() {
		return totalFailedStandard;
	}

	public void setTotalFailedStandard(Integer totalFailedStandard) {
		this.totalFailedStandard = totalFailedStandard;
	}

	public Integer getTotalDefaultedStandard() {
		return totalDefaultedStandard;
	}

	public void setTotalDefaultedStandard(Integer totalDefaultedStandard) {
		this.totalDefaultedStandard = totalDefaultedStandard;
	}

	public Integer getTotalNotAssessedStandard() {
		return totalNotAssessedStandard;
	}

	public void setTotalNotAssessedStandard(Integer totalNotAssessedStandard) {
		this.totalNotAssessedStandard = totalNotAssessedStandard;
	}

	public Integer getTotalTotalStandard() {
		return totalTotalStandard;
	}

	public void setTotalTotalStandard(Integer totalTotalStandard) {
		this.totalTotalStandard = totalTotalStandard;
	}
	
	
	
	
	
	
	
}
