package org.openmrs.module.mdrtb.status;



public interface VisitStatusRenderer {

	public void renderVisit(StatusItem visit, VisitStatus status);

	public void renderNewIntakeVisit(StatusItem newIntakeVisit, VisitStatus status);

	public void renderNewFollowUpVisit(StatusItem newFollowUpVisit, VisitStatus status);
	
	public void renderNewTransferOutVisit(StatusItem newFollowUpVisit, VisitStatus status);
	
	public void renderNewTransferInVisit(StatusItem newFollowUpVisit, VisitStatus status);
	
	public void renderTbVisit(StatusItem visit, VisitStatus status);

	public void renderNewTbIntakeVisit(StatusItem newIntakeVisit, VisitStatus status);

	public void renderNewTbFollowUpVisit(StatusItem newFollowUpVisit, VisitStatus status);
	
	public void renderNewTbTransferOutVisit(StatusItem newFollowUpVisit, VisitStatus status);
	
	public void renderNewTbTransferInVisit(StatusItem newFollowUpVisit, VisitStatus status);

}
