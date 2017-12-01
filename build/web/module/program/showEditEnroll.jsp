<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%> 
<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui-1.7.2.custom.min.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/css/redmond/jquery-ui-1.7.2.custom.css" />

<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.dimensions.pack.js"/>
<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.tooltip.js" />
<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.tooltip.css" />
<openmrs:htmlInclude file="/moduleResources/mdrtb/mdrtb.css"/>

<openmrs:portlet url="mdrtbPatientHeader" id="mdrtbPatientHeader" moduleId="mdrtb" patientId="${!empty patientId ? patientId : program.patient.id}"/>


<!-- TODO: clean up above paths so they use dynamic reference -->
<!-- TODO: add privileges? -->

<!-- SPECIALIZED STYLES FOR THIS PAGE -->
<!--  these are to make sure that the datepicker appears above the popup -->
<style type="text/css">
    td {padding-left:4px; padding-right:4px; padding-top:2px; padding-bottom:2px; vertical-align:top}
</style>





<br/><br/>

<div align="center"> <!-- start of page div -->


<!--  DISPLAY ANY ERROR MESSAGES -->
<c:if test="${fn:length(errors.allErrors) > 0}">
	<c:forEach var="error" items="${errors.allErrors}">
		<span class="error"><spring:message code="${error.code}"/></span><br/><br/>
	</c:forEach>
	<br/>
</c:if>


<!-- PROGRAM ENROLLMENT BOX-->
<b class="boxHeader" style="margin:0px"><spring:message code="mdrtb.editProgram" text="Enroll in Program"/></b>
<div class="box" style="margin:0px">

<c:choose>
<c:when test="${type eq 'tb' }">
<form id="enrollment" action="${pageContext.request.contextPath}/module/mdrtb/program/editProgramTb.form" method="post" >
<input type="hidden" name="programId" id="programId" value="${program.id }"/>
<table cellspacing="2" cellpadding="2">


<tr><td>
<spring:message code="mdrtb.enrollment.date" text="Enrollment Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="dateEnrolled" startValue="${program.dateEnrolled}"/></td>
</td></tr>

<tr><td colspan="2"><spring:message code="mdrtb.enrollment.location" text="Enrollment Location"/>:</td></tr>
<tr><td><spring:message code="mdrtb.oblast" text="Oblast"/>:</td><td>${program.location.stateProvince}</td></tr>
<tr><td><spring:message code="mdrtb.district" text="District"/>:</td><td>${program.location.countyDistrict}</td></tr>
<tr><td><spring:message code="mdrtb.facility" text="Facility"/>:</td><td>${program.location.region}</td></tr>


<tr><td colspan="2">
<spring:message code="mdrtb.patientGroup" text="Registration Group"/>:<br/>
<select name="classificationAccordingToPatientGroups" id="classificationAccordingToPatientGroups">
<option value=""/>
<c:forEach var="classificationAccordingToPatientGroups" items="${classificationsAccordingToPatientGroups}">
<option value="${classificationAccordingToPatientGroups.id}" <c:if test="${classificationAccordingToPatientGroups == program.classificationAccordingToPatientGroups}">selected</c:if>>${classificationAccordingToPatientGroups.concept.displayString}</option>
</c:forEach>
</select>	
</td></tr>

<tr><td colspan="2">
<spring:message code="mdrtb.previousDrugClassification" text="Registration Group - Previous Drug Use"/>:<br/>
<select name="classificationAccordingToPreviousDrugUse" id="classificationAccordingToPreviousDrugUse">
<option value=""/>
<c:forEach var="classificationAccordingToPreviousDrugUse" items="${classificationsAccordingToPreviousDrugUseDOTS}">
<option value="${classificationAccordingToPreviousDrugUse.id}" <c:if test="${classificationAccordingToPreviousDrugUse == program.classificationAccordingToPreviousDrugUse}">selected</c:if> >${classificationAccordingToPreviousDrugUse.concept.displayString}</option>
</c:forEach>
</select>	
</td></tr>

</table>
<button type="submit"><spring:message code="mdrtb.submit" text="Submitz"/>
</form>
</c:when>

<c:otherwise>
<form id="enrollment" action="${pageContext.request.contextPath}/module/mdrtb/program/editProgramMdrtb.form" method="post" >
<input type="hidden" name="programId" id="programIUd" value="${program.id}"/>
<table cellspacing="2" cellpadding="2">

<tr><td>
<spring:message code="mdrtb.enrollment.date" text="Enrollment Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="dateEnrolled" startValue="${program.dateEnrolled}"/></td>
</tr>

<tr><td colspan="2"><spring:message code="mdrtb.enrollment.location" text="Enrollment Location"/>:</td></tr>
<tr><td><spring:message code="mdrtb.oblast" text="Oblast"/>:</td><td>${program.location.stateProvince}</td></tr>
<tr><td><spring:message code="mdrtb.district" text="District"/>:</td><td>${program.location.countyDistrict}</td></tr>
<tr><td><spring:message code="mdrtb.facility" text="Facility"/>:</td><td>${program.location.region}</td></tr>

<%-- <tr><td>
<spring:message code="mdrtb.enrollment.location" text="Enrollment Location"/>:</td><td>
<select name="location">
<option value=""/>
<c:forEach var="location" items="${locations}">
<option value="${location.locationId}" <c:if test="${location == initLocation}">selected</c:if> >${location.displayString}</option>
</c:forEach>
</select>
</td></tr> --%>

<tr><td colspan="2">
<spring:message code="mdrtb.previousDrugClassification" text="Registration Group - Previous Drug Use"/>:<br/>
<select name="classificationAccordingToPreviousDrugUse" id="classificationAccordingToPreviousDrugUse">
<option value=""/>
<c:forEach var="classificationAccordingToPreviousDrugUse" items="${classificationsAccordingToPreviousDrugUse}">
<option value="${classificationAccordingToPreviousDrugUse.id}" <c:if test="${classificationAccordingToPreviousDrugUse == program.classificationAccordingToPreviousDrugUse}">selected</c:if> >${classificationAccordingToPreviousDrugUse.concept.displayString}</option>
</c:forEach>
</select>	
</td></tr>

<tr><td colspan="2">
<spring:message code="mdrtb.tb03.registrationGroup" text="Registration Group - Previous Treatment"/>:<br/>
<select name="classificationAccordingToPreviousTreatment" id="classificationAccordingToPreviousTreatment">
<option value=""/>
<c:forEach var="classificationAccordingToPreviousTreatment" items="${classificationsAccordingToPreviousTreatment}">
<option value="${classificationAccordingToPreviousTreatment.id}" <c:if test="${classificationAccordingToPreviousTreatment == program.classificationAccordingToPreviousTreatment}">selected</c:if> >${classificationAccordingToPreviousTreatment.concept.displayString}</option>
</c:forEach>
</select>	
</td></tr>

</table>
<button type="submit"><spring:message code="mdrtb.submit" text="Submitz"/>
</form>

</c:otherwise>
</c:choose>
</div>


<!-- END PROGRAM ENROLLMENT BOX -->

</div> <!-- end of page div -->

<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbFooter.jsp"%>