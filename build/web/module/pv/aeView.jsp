<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%> 
<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbHeader.jsp"%>

<%@ taglib prefix="wgt" uri="/WEB-INF/view/module/htmlwidgets/resources/htmlwidgets.tld" %>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui-1.7.2.custom.min.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/css/redmond/jquery-ui-1.7.2.custom.css" />

<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.dimensions.pack.js"/>
<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.tooltip.js" />
<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.tooltip.css" />
<openmrs:htmlInclude file="/moduleResources/mdrtb/mdrtb.css"/>

<c:if test="${! empty patientId && patientId != -1}">
	<openmrs:portlet url="mdrtbPatientHeader" id="mdrtbPatientHeader" moduleId="mdrtb" patientId="${!empty patientId ? patientId : program.patient.id}"/>
	
</c:if>
<!-- TODO: clean up above paths so they use dynamic reference -->
<!-- TODO: add privileges? -->


<!-- JQUERY FOR THIS PAGE -->



<div> <!-- start of page div -->

<a href="${pageContext.request.contextPath}/module/mdrtb/form/ae.form?encounterId=-1&patientId=${patientId}&patientProgramId=${patientProgramId}"><spring:message code="mdrtb.pv.addAE" text="newz"/></a>

<table border="1" width="100%">
<tr>
		<th><spring:message code="mdrtb.pv.serialNumber" text="serialz"/></th>
		<th><spring:message code="mdrtb.pv.adverseEvent" text="aez"/></th>
		<th><spring:message code="mdrtb.pv.onsetDate" text="datez"/></th>
		<th><spring:message code="mdrtb.pv.diagnosticInvestigation" text="diz"/></th>
		<th><spring:message code="mdrtb.pv.suspectedDrug" text="drugz"/></th>
		<th><spring:message code="mdrtb.pv.treatmentRegimenAtOnset" text="regimenz"/></th>
		<th><spring:message code="mdrtb.pv.typeOfEvent" text="typez"/></th>
		<th><spring:message code="mdrtb.pv.yellowCardDate" text="Yellowz"/></th>
		<th><spring:message code="mdrtb.pv.causalityDrug1" text="cd1"/></th>
		<th><spring:message code="mdrtb.pv.causalityAssessmentResult1" text="car1"/></th>
		<th><spring:message code="mdrtb.pv.causalityDrug2" text="cd2"/></th>
		<th><spring:message code="mdrtb.pv.causalityAssessmentResult2" text="car2"/></th>
		<th><spring:message code="mdrtb.pv.causalityDrug3" text="cd3"/></th>
		<th><spring:message code="mdrtb.pv.causalityAssessmentResult3" text="car3"/></th>
		<th><spring:message code="mdrtb.pv.actionTaken" text="actionz"/></th>
		<th><spring:message code="mdrtb.pv.actionOutcome" text="outcomez"/></th>
		<th><spring:message code="mdrtb.pv.outcomeDate" text="datez"/></th>
		<th><spring:message code="mdrtb.pv.eventOnsetLocation" text="placez"/></th>
		<th><spring:message code="mdrtb.pv.meddraCode" text="codez"/></th>
		<th><spring:message code="mdrtb.pv.drugRechallenge" text="rcz"/></th>
		<th><spring:message code="mdrtb.pv.comments" text="Commentz"/></th>
		
		
</tr>

<c:forEach var="form" items="${forms}" varStatus="loop">
 <tr>
 <td><a href="${pageContext.request.contextPath}//module/mdrtb/form/ae.form?encounterId=${form.id}&patientProgramId=${patientProgramId}" target="_blank">${loop.index+1}</a></td>
 <td>${form.adverseEvent.displayString}</td>
 <td><openmrs:formatDate date="${form.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
 <td>${form.diagnosticInvestigation.displayString}</td>
 <td>${form.suspectedDrug}</td>
 <td>${form.treatmentRegimenAtOnset}</td>
 <td>${form.typeOfEvent.displayString}</td>
 <td><openmrs:formatDate date="${form.yellowCardDate}" format="${_dateFormatDisplay}"/></td>
 <td>${form.causalityDrug1.displayString}</td>
 <td>${form.causalityAssessmentResult1.displayString}</td>
 <td>${form.causalityDrug2.displayString}</td>
 <td>${form.causalityAssessmentResult2.displayString}</td>
 <td>${form.causalityDrug3.displayString}</td>
 <td>${form.causalityAssessmentResult3.displayString}</td>
 <td>${form.actionTaken.displayString}</td>
 <td>${form.actionOutcome.displayString}</td>
 <td><openmrs:formatDate date="${form.outcomeDate}" format="${_dateFormatDisplay}"/></td>
 <td>${form.facility}</td>
 <td>${form.meddraCode.displayString}</td>
 <td>${form.drugRechallenge.displayString}</td>
 <td>${form.comments}</td>

 </tr>
  
</c:forEach>

</table>


</div> <!-- end of page div -->

<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbFooter.jsp"%>