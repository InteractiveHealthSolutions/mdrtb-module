package org.openmrs.module.mdrtb.reporting;

import java.util.Date;
import java.util.HashMap;

import org.openmrs.Patient;
import org.openmrs.api.context.Context;


public class TB08uData  {
	
	private Integer newRegistered;
	private Integer newCured;
	private Integer newCompleted;
	private Integer newTxSuccess;
	private Integer newDiedTB;
	private Integer newDiedNotTB;
	private Integer newFailed;
	private Integer newDefaulted;
	private Integer newNotAssessed;
	private Integer newTotal;
	
	private Integer relapse1Registered;
	private Integer relapse1Cured;
	private Integer relapse1Completed;
	private Integer relapse1TxSuccess;
	private Integer relapse1DiedTB;
	private Integer relapse1DiedNotTB;
	private Integer relapse1Failed;
	private Integer relapse1Defaulted;
	private Integer relapse1NotAssessed;
	private Integer relapse1Total;
	
	private Integer relapse2Registered;
	private Integer relapse2Cured;
	private Integer relapse2Completed;
	private Integer relapse2TxSuccess;
	private Integer relapse2DiedTB;
	private Integer relapse2DiedNotTB;
	private Integer relapse2Failed;
	private Integer relapse2Defaulted;
	private Integer relapse2NotAssessed;
	private Integer relapse2Total;
	
	private Integer default1Registered;
	private Integer default1Cured;
	private Integer default1Completed;
	private Integer default1TxSuccess;
	private Integer default1DiedTB;
	private Integer default1DiedNotTB;
	private Integer default1Failed;
	private Integer default1Defaulted;
	private Integer default1NotAssessed;
	private Integer default1Total;
	
	private Integer default2Registered;
	private Integer default2Cured;
	private Integer default2Completed;
	private Integer default2TxSuccess;
	private Integer default2DiedTB;
	private Integer default2DiedNotTB;
	private Integer default2Failed;
	private Integer default2Defaulted;
	private Integer default2NotAssessed;
	private Integer default2Total;
	
	private Integer failure1Registered;
	private Integer failure1Cured;
	private Integer failure1Completed;
	private Integer failure1TxSuccess;
	private Integer failure1DiedTB;
	private Integer failure1DiedNotTB;
	private Integer failure1Failed;
	private Integer failure1Defaulted;
	private Integer failure1NotAssessed;
	private Integer failure1Total;
	
	private Integer failure2Registered;
	private Integer failure2Cured;
	private Integer failure2Completed;
	private Integer failure2TxSuccess;
	private Integer failure2DiedTB;
	private Integer failure2DiedNotTB;
	private Integer failure2Failed;
	private Integer failure2Defaulted;
	private Integer failure2NotAssessed;
	private Integer failure2Total;
	
	private Integer otherRegistered;
	private Integer otherCured;
	private Integer otherCompleted;
	private Integer otherTxSuccess;
	private Integer otherDiedTB;
	private Integer otherDiedNotTB;
	private Integer otherFailed;
	private Integer otherDefaulted;
	private Integer otherNotAssessed;
	private Integer otherTotal;
	
	private Integer totalRegistered;
	private Integer totalCured;
	private Integer totalCompleted;
	private Integer totalTxSuccess;
	private Integer totalDiedTB;
	private Integer totalDiedNotTB;
	private Integer totalFailed;
	private Integer totalDefaulted;
	private Integer totalNotAssessed;
	private Integer totalTotal;
	
	public TB08uData() {
		 newRegistered = 0;
		 newCured = 0;
		 newCompleted = 0;
		 newTxSuccess = 0;
		 newDiedTB = 0;
		 newDiedNotTB = 0;
		 newFailed = 0;
		 newDefaulted = 0;
		 newNotAssessed = 0;
		 newTotal = 0;
		
		 relapse1Registered = 0;
		 relapse1Cured = 0;
		 relapse1Completed = 0;
		 relapse1TxSuccess = 0;
		 relapse1DiedTB = 0;
		 relapse1DiedNotTB = 0;
		 relapse1Failed = 0;
		 relapse1Defaulted = 0;
		 relapse1NotAssessed = 0;
		 relapse1Total = 0;
		
		 relapse2Registered = 0;
		 relapse2Cured = 0;
		 relapse2Completed = 0;
		 relapse2TxSuccess = 0;
		 relapse2DiedTB = 0;
		 relapse2DiedNotTB = 0;
		 relapse2Failed = 0;
		 relapse2Defaulted = 0;
		 relapse2NotAssessed = 0;
		 relapse2Total = 0;
		
		 default1Registered = 0;
		 default1Cured = 0;
		 default1Completed = 0;
		 default1TxSuccess = 0;
		 default1DiedTB = 0;
		 default1DiedNotTB = 0;
		 default1Failed = 0;
		 default1Defaulted = 0;
		 default1NotAssessed = 0;
		 default1Total = 0;
		
		 default2Registered = 0;
		 default2Cured = 0;
		 default2Completed = 0;
		 default2TxSuccess = 0;
		 default2DiedTB = 0;
		 default2DiedNotTB = 0;
		 default2Failed = 0;
		 default2Defaulted = 0;
		 default2NotAssessed = 0;
		 default2Total = 0;
		
		 failure1Registered = 0;
		 failure1Cured = 0;
		 failure1Completed = 0;
		 failure1TxSuccess = 0;
		 failure1DiedTB = 0;
		 failure1DiedNotTB = 0;
		 failure1Failed = 0;
		 failure1Defaulted = 0;
		 failure1NotAssessed = 0;
		 failure1Total = 0;
		
		 failure2Registered = 0;
		 failure2Cured = 0;
		 failure2Completed = 0;
		 failure2TxSuccess = 0;
		 failure2DiedTB = 0;
		 failure2DiedNotTB = 0;
		 failure2Failed = 0;
		 failure2Defaulted = 0;
		 failure2NotAssessed = 0;
		 failure2Total = 0;
		
		 otherRegistered = 0;
		 otherCured = 0;
		 otherCompleted = 0;
		 otherTxSuccess = 0;
		 otherDiedTB = 0;
		 otherDiedNotTB = 0;
		 otherFailed = 0;
		 otherDefaulted = 0;
		 otherNotAssessed = 0;
		 otherTotal = 0;
		
		 totalRegistered = 0;
		 totalCured = 0;
		 totalCompleted = 0;
		 totalTxSuccess = 0;
		 totalDiedTB = 0;
		 totalDiedNotTB = 0;
		 totalFailed = 0;
		 totalDefaulted = 0;
		 totalNotAssessed = 0;
		 totalTotal = 0;
	}
	
	public Integer getNewRegistered() {
		return newRegistered;
	}
	public void setNewRegistered(Integer newRegistered) {
		this.newRegistered = newRegistered;
	}
	public Integer getNewCured() {
		return newCured;
	}
	public void setNewCured(Integer newCured) {
		this.newCured = newCured;
	}
	public Integer getNewCompleted() {
		return newCompleted;
	}
	public void setNewCompleted(Integer newCompleted) {
		this.newCompleted = newCompleted;
	}
	public Integer getNewTxSuccess() {
		return newTxSuccess;
	}
	public void setNewTxSuccess(Integer newTxSuccess) {
		this.newTxSuccess = newTxSuccess;
	}
	public Integer getNewDiedTB() {
		return newDiedTB;
	}
	public void setNewDiedTB(Integer newDiedTB) {
		this.newDiedTB = newDiedTB;
	}
	public Integer getNewDiedNotTB() {
		return newDiedNotTB;
	}
	public void setNewDiedNotTB(Integer newDiedNotTB) {
		this.newDiedNotTB = newDiedNotTB;
	}
	public Integer getNewFailed() {
		return newFailed;
	}
	public void setNewFailed(Integer newFailed) {
		this.newFailed = newFailed;
	}
	public Integer getNewDefaulted() {
		return newDefaulted;
	}
	public void setNewDefaulted(Integer newDefaulted) {
		this.newDefaulted = newDefaulted;
	}
	public Integer getNewNotAssessed() {
		return newNotAssessed;
	}
	public void setNewNotAssessed(Integer newNotAssessed) {
		this.newNotAssessed = newNotAssessed;
	}
	public Integer getNewTotal() {
		return newTotal;
	}
	public void setNewTotal(Integer newTotal) {
		this.newTotal = newTotal;
	}
	public Integer getRelapse1Registered() {
		return relapse1Registered;
	}
	public void setRelapse1Registered(Integer relapse1Registered) {
		this.relapse1Registered = relapse1Registered;
	}
	public Integer getRelapse1Cured() {
		return relapse1Cured;
	}
	public void setRelapse1Cured(Integer relapse1Cured) {
		this.relapse1Cured = relapse1Cured;
	}
	public Integer getRelapse1Completed() {
		return relapse1Completed;
	}
	public void setRelapse1Completed(Integer relapse1Completed) {
		this.relapse1Completed = relapse1Completed;
	}
	public Integer getRelapse1TxSuccess() {
		return relapse1TxSuccess;
	}
	public void setRelapse1TxSuccess(Integer relapse1TxSuccess) {
		this.relapse1TxSuccess = relapse1TxSuccess;
	}
	public Integer getRelapse1DiedTB() {
		return relapse1DiedTB;
	}
	public void setRelapse1DiedTB(Integer relapse1DiedTB) {
		this.relapse1DiedTB = relapse1DiedTB;
	}
	public Integer getRelapse1DiedNotTB() {
		return relapse1DiedNotTB;
	}
	public void setRelapse1DiedNotTB(Integer relapse1DiedNotTB) {
		this.relapse1DiedNotTB = relapse1DiedNotTB;
	}
	public Integer getRelapse1Failed() {
		return relapse1Failed;
	}
	public void setRelapse1Failed(Integer relapse1Failed) {
		this.relapse1Failed = relapse1Failed;
	}
	public Integer getRelapse1Defaulted() {
		return relapse1Defaulted;
	}
	public void setRelapse1Defaulted(Integer relapse1Defaulted) {
		this.relapse1Defaulted = relapse1Defaulted;
	}
	public Integer getRelapse1NotAssessed() {
		return relapse1NotAssessed;
	}
	public void setRelapse1NotAssessed(Integer relapse1NotAssessed) {
		this.relapse1NotAssessed = relapse1NotAssessed;
	}
	public Integer getRelapse1Total() {
		return relapse1Total;
	}
	public void setRelapse1Total(Integer relapse1Total) {
		this.relapse1Total = relapse1Total;
	}
	public Integer getRelapse2Registered() {
		return relapse2Registered;
	}
	public void setRelapse2Registered(Integer relapse2Registered) {
		this.relapse2Registered = relapse2Registered;
	}
	public Integer getRelapse2Cured() {
		return relapse2Cured;
	}
	public void setRelapse2Cured(Integer relapse2Cured) {
		this.relapse2Cured = relapse2Cured;
	}
	public Integer getRelapse2Completed() {
		return relapse2Completed;
	}
	public void setRelapse2Completed(Integer relapse2Completed) {
		this.relapse2Completed = relapse2Completed;
	}
	public Integer getRelapse2TxSuccess() {
		return relapse2TxSuccess;
	}
	public void setRelapse2TxSuccess(Integer relapse2TxSuccess) {
		this.relapse2TxSuccess = relapse2TxSuccess;
	}
	public Integer getRelapse2DiedTB() {
		return relapse2DiedTB;
	}
	public void setRelapse2DiedTB(Integer relapse2DiedTB) {
		this.relapse2DiedTB = relapse2DiedTB;
	}
	public Integer getRelapse2DiedNotTB() {
		return relapse2DiedNotTB;
	}
	public void setRelapse2DiedNotTB(Integer relapse2DiedNotTB) {
		this.relapse2DiedNotTB = relapse2DiedNotTB;
	}
	public Integer getRelapse2Failed() {
		return relapse2Failed;
	}
	public void setRelapse2Failed(Integer relapse2Failed) {
		this.relapse2Failed = relapse2Failed;
	}
	public Integer getRelapse2Defaulted() {
		return relapse2Defaulted;
	}
	public void setRelapse2Defaulted(Integer relapse2Defaulted) {
		this.relapse2Defaulted = relapse2Defaulted;
	}
	public Integer getRelapse2NotAssessed() {
		return relapse2NotAssessed;
	}
	public void setRelapse2NotAssessed(Integer relapse2NotAssessed) {
		this.relapse2NotAssessed = relapse2NotAssessed;
	}
	public Integer getRelapse2Total() {
		return relapse2Total;
	}
	public void setRelapse2Total(Integer relapse2Total) {
		this.relapse2Total = relapse2Total;
	}
	public Integer getFailure1Registered() {
		return failure1Registered;
	}
	public void setFailure1Registered(Integer failure1Registered) {
		this.failure1Registered = failure1Registered;
	}
	public Integer getFailure1Cured() {
		return failure1Cured;
	}
	public void setFailure1Cured(Integer failure1Cured) {
		this.failure1Cured = failure1Cured;
	}
	public Integer getFailure1Completed() {
		return failure1Completed;
	}
	public void setFailure1Completed(Integer failure1Completed) {
		this.failure1Completed = failure1Completed;
	}
	public Integer getFailure1TxSuccess() {
		return failure1TxSuccess;
	}
	public void setFailure1TxSuccess(Integer failure1TxSuccess) {
		this.failure1TxSuccess = failure1TxSuccess;
	}
	public Integer getFailure1DiedTB() {
		return failure1DiedTB;
	}
	public void setFailure1DiedTB(Integer failure1DiedTB) {
		this.failure1DiedTB = failure1DiedTB;
	}
	public Integer getFailure1DiedNotTB() {
		return failure1DiedNotTB;
	}
	public void setFailure1DiedNotTB(Integer failure1DiedNotTB) {
		this.failure1DiedNotTB = failure1DiedNotTB;
	}
	public Integer getFailure1Failed() {
		return failure1Failed;
	}
	public void setFailure1Failed(Integer failure1Failed) {
		this.failure1Failed = failure1Failed;
	}
	public Integer getFailure1Defaulted() {
		return failure1Defaulted;
	}
	public void setFailure1Defaulted(Integer failure1Defaulted) {
		this.failure1Defaulted = failure1Defaulted;
	}
	public Integer getFailure1NotAssessed() {
		return failure1NotAssessed;
	}
	public void setFailure1NotAssessed(Integer failure1NotAssessed) {
		this.failure1NotAssessed = failure1NotAssessed;
	}
	public Integer getFailure1Total() {
		return failure1Total;
	}
	public void setFailure1Total(Integer failure1Total) {
		this.failure1Total = failure1Total;
	}
	public Integer getFailure2Registered() {
		return failure2Registered;
	}
	public void setFailure2Registered(Integer failure2Registered) {
		this.failure2Registered = failure2Registered;
	}
	public Integer getFailure2Cured() {
		return failure2Cured;
	}
	public void setFailure2Cured(Integer failure2Cured) {
		this.failure2Cured = failure2Cured;
	}
	public Integer getFailure2Completed() {
		return failure2Completed;
	}
	public void setFailure2Completed(Integer failure2Completed) {
		this.failure2Completed = failure2Completed;
	}
	public Integer getFailure2TxSuccess() {
		return failure2TxSuccess;
	}
	public void setFailure2TxSuccess(Integer failure2TxSuccess) {
		this.failure2TxSuccess = failure2TxSuccess;
	}
	public Integer getFailure2DiedTB() {
		return failure2DiedTB;
	}
	public void setFailure2DiedTB(Integer failure2DiedTB) {
		this.failure2DiedTB = failure2DiedTB;
	}
	public Integer getFailure2DiedNotTB() {
		return failure2DiedNotTB;
	}
	public void setFailure2DiedNotTB(Integer failure2DiedNotTB) {
		this.failure2DiedNotTB = failure2DiedNotTB;
	}
	public Integer getFailure2Failed() {
		return failure2Failed;
	}
	public void setFailure2Failed(Integer failure2Failed) {
		this.failure2Failed = failure2Failed;
	}
	public Integer getFailure2Defaulted() {
		return failure2Defaulted;
	}
	public void setFailure2Defaulted(Integer failure2Defaulted) {
		this.failure2Defaulted = failure2Defaulted;
	}
	public Integer getFailure2NotAssessed() {
		return failure2NotAssessed;
	}
	public void setFailure2NotAssessed(Integer failure2NotAssessed) {
		this.failure2NotAssessed = failure2NotAssessed;
	}
	public Integer getFailure2Total() {
		return failure2Total;
	}
	public void setFailure2Total(Integer failure2Total) {
		this.failure2Total = failure2Total;
	}
	public Integer getOtherRegistered() {
		return otherRegistered;
	}
	public void setOtherRegistered(Integer otherRegistered) {
		this.otherRegistered = otherRegistered;
	}
	public Integer getOtherCured() {
		return otherCured;
	}
	public void setOtherCured(Integer otherCured) {
		this.otherCured = otherCured;
	}
	public Integer getOtherCompleted() {
		return otherCompleted;
	}
	public void setOtherCompleted(Integer otherCompleted) {
		this.otherCompleted = otherCompleted;
	}
	public Integer getOtherTxSuccess() {
		return otherTxSuccess;
	}
	public void setOtherTxSuccess(Integer otherTxSuccess) {
		this.otherTxSuccess = otherTxSuccess;
	}
	public Integer getOtherDiedTB() {
		return otherDiedTB;
	}
	public void setOtherDiedTB(Integer otherDiedTB) {
		this.otherDiedTB = otherDiedTB;
	}
	public Integer getOtherDiedNotTB() {
		return otherDiedNotTB;
	}
	public void setOtherDiedNotTB(Integer otherDiedNotTB) {
		this.otherDiedNotTB = otherDiedNotTB;
	}
	public Integer getOtherFailed() {
		return otherFailed;
	}
	public void setOtherFailed(Integer otherFailed) {
		this.otherFailed = otherFailed;
	}
	public Integer getOtherDefaulted() {
		return otherDefaulted;
	}
	public void setOtherDefaulted(Integer otherDefaulted) {
		this.otherDefaulted = otherDefaulted;
	}
	public Integer getOtherNotAssessed() {
		return otherNotAssessed;
	}
	public void setOtherNotAssessed(Integer otherNotAssessed) {
		this.otherNotAssessed = otherNotAssessed;
	}
	public Integer getOtherTotal() {
		return otherTotal;
	}
	public void setOtherTotal(Integer otherTotal) {
		this.otherTotal = otherTotal;
	}
	public Integer getTotalRegistered() {
		return totalRegistered;
	}
	public void setTotalRegistered(Integer totalRegistered) {
		this.totalRegistered = totalRegistered;
	}
	public Integer getTotalCured() {
		return totalCured;
	}
	public void setTotalCured(Integer totalCured) {
		this.totalCured = totalCured;
	}
	public Integer getTotalCompleted() {
		return totalCompleted;
	}
	public void setTotalCompleted(Integer totalCompleted) {
		this.totalCompleted = totalCompleted;
	}
	public Integer getTotalTxSuccess() {
		return totalTxSuccess;
	}
	public void setTotalTxSuccess(Integer totalTxSuccess) {
		this.totalTxSuccess = totalTxSuccess;
	}
	public Integer getTotalDiedTB() {
		return totalDiedTB;
	}
	public void setTotalDiedTB(Integer totalDiedTB) {
		this.totalDiedTB = totalDiedTB;
	}
	public Integer getTotalDiedNotTB() {
		return totalDiedNotTB;
	}
	public void setTotalDiedNotTB(Integer totalDiedNotTB) {
		this.totalDiedNotTB = totalDiedNotTB;
	}
	public Integer getTotalFailed() {
		return totalFailed;
	}
	public void setTotalFailed(Integer totalFailed) {
		this.totalFailed = totalFailed;
	}
	public Integer getTotalDefaulted() {
		return totalDefaulted;
	}
	public void setTotalDefaulted(Integer totalDefaulted) {
		this.totalDefaulted = totalDefaulted;
	}
	public Integer getTotalNotAssessed() {
		return totalNotAssessed;
	}
	public void setTotalNotAssessed(Integer totalNotAssessed) {
		this.totalNotAssessed = totalNotAssessed;
	}
	public Integer getTotalTotal() {
		return totalTotal;
	}
	public void setTotalTotal(Integer totalTotal) {
		this.totalTotal = totalTotal;
	}
	public Integer getDefault1Registered() {
		return default1Registered;
	}
	public void setDefault1Registered(Integer default1Registered) {
		this.default1Registered = default1Registered;
	}
	public Integer getDefault1Cured() {
		return default1Cured;
	}
	public void setDefault1Cured(Integer default1Cured) {
		this.default1Cured = default1Cured;
	}
	public Integer getDefault1Completed() {
		return default1Completed;
	}
	public void setDefault1Completed(Integer default1Completed) {
		this.default1Completed = default1Completed;
	}
	public Integer getDefault1TxSuccess() {
		return default1TxSuccess;
	}
	public void setDefault1TxSuccess(Integer default1TxSuccess) {
		this.default1TxSuccess = default1TxSuccess;
	}
	public Integer getDefault1DiedTB() {
		return default1DiedTB;
	}
	public void setDefault1DiedTB(Integer default1DiedTB) {
		this.default1DiedTB = default1DiedTB;
	}
	public Integer getDefault1DiedNotTB() {
		return default1DiedNotTB;
	}
	public void setDefault1DiedNotTB(Integer default1DiedNotTB) {
		this.default1DiedNotTB = default1DiedNotTB;
	}
	public Integer getDefault1Failed() {
		return default1Failed;
	}
	public void setDefault1Failed(Integer default1Failed) {
		this.default1Failed = default1Failed;
	}
	public Integer getDefault1Defaulted() {
		return default1Defaulted;
	}
	public void setDefault1Defaulted(Integer default1Defaulted) {
		this.default1Defaulted = default1Defaulted;
	}
	public Integer getDefault1NotAssessed() {
		return default1NotAssessed;
	}
	public void setDefault1NotAssessed(Integer default1NotAssessed) {
		this.default1NotAssessed = default1NotAssessed;
	}
	public Integer getDefault1Total() {
		return default1Total;
	}
	public void setDefault1Total(Integer default1Total) {
		this.default1Total = default1Total;
	}
	public Integer getDefault2Registered() {
		return default2Registered;
	}
	public void setDefault2Registered(Integer default2Registered) {
		this.default2Registered = default2Registered;
	}
	public Integer getDefault2Cured() {
		return default2Cured;
	}
	public void setDefault2Cured(Integer default2Cured) {
		this.default2Cured = default2Cured;
	}
	public Integer getDefault2Completed() {
		return default2Completed;
	}
	public void setDefault2Completed(Integer default2Completed) {
		this.default2Completed = default2Completed;
	}
	public Integer getDefault2TxSuccess() {
		return default2TxSuccess;
	}
	public void setDefault2TxSuccess(Integer default2TxSuccess) {
		this.default2TxSuccess = default2TxSuccess;
	}
	public Integer getDefault2DiedTB() {
		return default2DiedTB;
	}
	public void setDefault2DiedTB(Integer default2DiedTB) {
		this.default2DiedTB = default2DiedTB;
	}
	public Integer getDefault2DiedNotTB() {
		return default2DiedNotTB;
	}
	public void setDefault2DiedNotTB(Integer default2DiedNotTB) {
		this.default2DiedNotTB = default2DiedNotTB;
	}
	public Integer getDefault2Failed() {
		return default2Failed;
	}
	public void setDefault2Failed(Integer default2Failed) {
		this.default2Failed = default2Failed;
	}
	public Integer getDefault2Defaulted() {
		return default2Defaulted;
	}
	public void setDefault2Defaulted(Integer default2Defaulted) {
		this.default2Defaulted = default2Defaulted;
	}
	public Integer getDefault2NotAssessed() {
		return default2NotAssessed;
	}
	public void setDefault2NotAssessed(Integer default2NotAssessed) {
		this.default2NotAssessed = default2NotAssessed;
	}
	public Integer getDefault2Total() {
		return default2Total;
	}
	public void setDefault2Total(Integer default2Total) {
		this.default2Total = default2Total;
	}
	
	
	
	
	
}
