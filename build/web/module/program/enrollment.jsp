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

<!-- CUSTOM JQUERY  -->
<script>
function addId(ppid)
{
	var e = document.getElementById("id_" + ppid);
	var idToAdd = e.options[e.selectedIndex].value;
	
	window.location.replace("${pageContext.request.contextPath}/module/mdrtb/program/addId.form?ppid="+ppid+"&idToAdd="+idToAdd)
}


</script>

<br/><br/>

<div align="center"> <!-- start of page div -->


<!--  DISPLAY ANY ERROR MESSAGES -->
<c:if test="${fn:length(errors.allErrors) > 0}">
	<c:forEach var="error" items="${errors.allErrors}">
		<span class="error"><spring:message code="${error.code}"/></span><br/><br/>
	</c:forEach>
	<br/>
</c:if>

<c:choose>
<c:when test="${hasPrograms}">
<c:if test="${not empty tbPrograms}">
<b class="boxHeader" style="margin:0px"><spring:message code="mdrtb.enrollment.tbPrograms" text="TB Programs"/>
<span style="position: absolute; right:30px;"><a href="${pageContext.request.contextPath}/module/mdrtb/program/otherEnrollment.form?patientId=${patientId}&patientProgramId=-1&type=tb"><spring:message code="mdrtb.add" text="Addz"/></a></span>
</b>
<div class="box" style="margin:0px">
<table cellspacing="2" cellpadding="2">
<tr>
<th><spring:message code="mdrtb.tb03.dateOfRegistration" text="Date"/></th>
<th><spring:message code="mdrtb.enrollment.location" text="Location"/></th>
<th><spring:message code="mdrtb.tb03.registrationNumber" text="Location"/></th>
<th><spring:message code="mdrtb.enrollment.completionDate" text="Completion Date"/></th>
<th>&nbsp;</th>
<th>&nbsp;</th>
<th><spring:message code="mdrtb.forms" text="Forms"/></th>
</tr>

<c:forEach var="tbProgram" items="${tbPrograms}">
<tr>
<td><openmrs:formatDate date="${tbProgram.dateEnrolled}" format="${_dateFormatDisplay}"/></td>
<td>${tbProgram.location.name }</td>
<td>${tbProgram.patientIdentifier.identifier }</td>
<td><openmrs:formatDate date="${tbProgram.dateCompleted}" format="${_dateFormatDisplay}"/></td>
<td><a href="${pageContext.request.contextPath}/module/mdrtb/dashboard/tbdashboard.form?patientId=${patientId}&patientProgramId=${tbProgram.id }"><spring:message code="mdrtb.edit" text="XXXX"/></a></td>
<td>
<c:if test="${empty tbProgram.patientIdentifier}">
    <c:if test="${!empty unassignedDotsIdentifiers}">
    	<select id="id_${tbProgram.id}">
    		<c:forEach var="pi" items="${unassignedDotsIdentifiers}">
    			<option value="${pi.id}">${pi.identifier}</option>
    		</c:forEach>
    	</select>
    	<button onclick="addId(${tbProgram.id})" text="Link">Add</button>
    </c:if>
</c:if>
</td>
<td>
<c:if test="${!empty tbProgram.tb03}">
<a href="${pageContext.request.contextPath}${tbProgram.tb03.link }"><spring:message code="mdrtb.tb03" text="TB03"/></a>&nbsp;&nbsp;
</c:if>
<c:if test="${!empty tbProgram.form89}">
<a href="${pageContext.request.contextPath}${tbProgram.form89.link }"><spring:message code="mdrtb.form89" text="Form89"/></a>
</c:if>
</td>
</tr>     
</c:forEach>
</table>
</div>
</c:if>
<c:if test="${not empty mdrtbPrograms}">
<b class="boxHeader" style="margin:0px"><spring:message code="mdrtb.enrollment.mdrtbPrograms" text="MDR-TB Programs"/></b>
<div class="box" style="margin:0px">
<table cellspacing="2" cellpadding="2">

<tr>
<th><spring:message code="mdrtb.tb03.dateOfRegistration" text="Date"/></th>
<th><spring:message code="mdrtb.enrollment.location" text="Location"/></th>
<th><spring:message code="mdrtb.tb03.registrationNumber" text="Location"/></th>
<th><spring:message code="mdrtb.enrollment.completionDate" text="Completion Date"/></th>
<th>&nbsp;</th>
<th>&nbsp;</th>
<th><spring:message code="mdrtb.forms" text="Forms"/></th>
</tr>
<c:forEach var="mdrtbProgram" items="${mdrtbPrograms}">
   <tr>
<td><openmrs:formatDate date="${mdrtbProgram.dateEnrolled}" format="${_dateFormatDisplay}"/></td>
<td>${mdrtbProgram.location.name }</td>
<td>${mdrtbProgram.patientIdentifier.identifier }</td>
<td><openmrs:formatDate date="${mdrtbProgram.dateCompleted}"format="${_dateFormatDisplay}"/></td>
<td><a href="${pageContext.request.contextPath}/module/mdrtb/dashboard/dashboard.form?patientId=${patientId}&patientProgramId=${mdrtbProgram.id }"><spring:message code="mdrtb.edit" text="Edit"/></a></td>
<td>
<c:if test="${empty mdrtbProgram.patientIdentifier}">
    <c:if test="${!empty unassignedMdrIdentifiers}">
    	<select id="id_${mdrtbProgram.id}">
    		<c:forEach var="pi" items="${unassignedMdrIdentifiers}">
    			<option value="${pi.id}">${pi.identifier}</option>
    		</c:forEach>
    	</select>
    	<button onclick="addId(${mdrtbProgram.id})" text="Link">Add</button>
    </c:if>
</c:if>
</td>
<td>
<c:if test="${!empty mdrtbProgram.tb03u}">
<a href="${pageContext.request.contextPath}${mdrtbProgram.tb03u.link }"><spring:message code="mdrtb.tb03u" text="TB03u"/></a>
</c:if>
</td>
</tr>    

</c:forEach>
</table>
</div>
</c:if>
</c:when>
<c:otherwise>

<!-- PROGRAM ENROLLMENT BOX-->
<b class="boxHeader" style="margin:0px"><spring:message code="mdrtb.enrollment.enrollInProgram" text="Enroll in MDR-TB Program"/></b>
<div class="box" style="margin:0px">

<form id="enrollment" action="${pageContext.request.contextPath}/module/mdrtb/program/firstEnrollment.form?patientId=${patientId}&patientProgramId=-1&idId=${idId}" method="post" >

<table cellspacing="2" cellpadding="2">
<tr><td>
<spring:message code="mdrtb.enrollment.date" text="Enrollment Date"/>:</td><td><input id="dateEnrolled" type="text" size="14" tabindex="-1" name="dateEnrolled" value="<openmrs:formatDate date='${program.dateEnrolled}'/>" onFocus="showCalendar(this)"/>
</td></tr>

<tr><td>
<spring:message code="mdrtb.enrollment.location" text="Enrollment Location"/>:</td><td>
<select name="location">
<option value=""/>
<c:forEach var="location" items="${locations}">
<option value="${location.locationId}" <c:if test="${location == program.location}">selected</c:if> >${location.displayString}</option>
</c:forEach>
</select>
</td></tr>

<tr><td colspan="2">
<spring:message code="mdrtb.tb03.registrationGroup" text="Registration Group"/>:<br/>
<select name="classificationAccordingToPatientGroups">
<option value=""/>
<c:forEach var="classificationAccordingToPatientGroups" items="${classificationsAccordingToPatientGroups}">
<option value="${classificationAccordingToPatientGroups.id}" <c:if test="${classificationAccordingToPatientGroups == program.classificationAccordingToPatientGroups}">selected</c:if>>${classificationAccordingToPatientGroups.concept.displayString}</option>
</c:forEach>
</select>	
</td></tr>

<tr><td colspan="2">
<spring:message code="mdrtb.previousDrugClassification" text="Registration Group - Previous Drug Use"/>:<br/>
<select name="classificationAccordingToPreviousDrugUse">
<option value=""/>
<c:forEach var="classificationAccordingToPreviousDrugUse" items="${classificationsAccordingToPreviousDrugUseDOTS}">
<option value="${classificationAccordingToPreviousDrugUse.id}" <c:if test="${classificationAccordingToPreviousDrugUse == program.classificationAccordingToPreviousDrugUse}">selected</c:if> >${classificationAccordingToPreviousDrugUse.concept.displayString}</option>
</c:forEach>
</select>	
</td></tr>

</table>
<button type="submit"><spring:message code="mdrtb.enrollment.enroll" text="Enroll in Program"/></button><button type="reset" onclick=window.location='${pageContext.request.contextPath}/module/mdrtb/dashboard/dashboard.form?patientId=${patientId}'><spring:message code="mdrtb.cancel" text="Cancel"/></button>
</form>
</div>
</c:otherwise>
</c:choose>

<!-- END PROGRAM ENROLLMENT BOX -->

</div> <!-- end of page div -->

<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbFooter.jsp"%>