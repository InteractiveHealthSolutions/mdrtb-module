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

$j(document).ready(function(){
	
	$('#oblast').val(${oblastSelected});
	$('#district').val(${districtSelected});
	$('#facility').val(${facilitySelected});
	$('#identifierValue').val(${idSelected});
	<c:if test="${! empty dateEnrolled}">
		$('#dateEnrolled').val("" + ${dateEnrolled});
	</c:if>
	<c:if test="${type eq 'tb'}" >
		$('#classificationAccordingToPatientGroups').val(${patientGroup});
	</c:if>
	$('#classificationAccordingToPreviousDrugUse').val(${previousDrugUse});
	<c:if test="${type eq 'mdr'}" >
		$('#classificationAccordingToPreviousTreatment').val(${patientGroup});
	</c:if>
	
});

function fun1()
{
	var e = document.getElementById("oblast");
	var val = e.options[e.selectedIndex].value;
	var dt = "\"" + document.getElementById("dateEnrolled").value + "\"";
	var idSelected = document.getElementById("identifierValue").value;
	var patGroup;
	<c:if test="${type eq 'tb'}" >
		patGroup = document.getElementById("classificationAccordingToPatientGroups");
	</c:if>
	
	<c:if test="${type eq 'mdr'}" >
		patGroup = document.getElementById("classificationAccordingToPreviousTreatment");
	</c:if>
	var patGroupChoice = patGroup.options[patGroup.selectedIndex].value;
	var drugGroup = document.getElementById("classificationAccordingToPreviousDrugUse");
	var drugGroupChoice = drugGroup.options[drugGroup.selectedIndex].value;
	
	
	if(val!="")
		window.location.replace("${pageContext.request.contextPath}/module/mdrtb/program/otherEnrollment.form?ob="+val+ "&dateEnrolled=" + dt + "&patGroup=" + patGroupChoice + "&drugGroup=" + drugGroupChoice + "&patientId="+${patientId} + "&idSelected=" + idSelected + "&type=${type}")
}

function fun2()
{
	var e = document.getElementById("oblast");
	var val1 = e.options[e.selectedIndex].value;
	var e = document.getElementById("district");
	var val2 = e.options[e.selectedIndex].value;
	var dt = "\"" + document.getElementById("dateEnrolled").value + "\"";
	var idSelected = document.getElementById("identifierValue").value;
	var patGroup;
	<c:if test="${type eq 'tb'}" >
		patGroup = document.getElementById("classificationAccordingToPatientGroups");
	</c:if>
	
	<c:if test="${type eq 'mdr'}" >
		patGroup = document.getElementById("classificationAccordingToPreviousTreatment");
	</c:if>
	var patGroupChoice = patGroup.options[patGroup.selectedIndex].value;
	var drugGroup = document.getElementById("classificationAccordingToPreviousDrugUse");
	var drugGroupChoice = drugGroup.options[drugGroup.selectedIndex].value;
	
	if(val2!="")
		window.location.replace("${pageContext.request.contextPath}/module/mdrtb/program/otherEnrollment.form?loc="+val2+"&ob="+val1+ "&dateEnrolled=" + dt + "&patGroup=" + patGroupChoice + "&drugGroup=" + drugGroupChoice + "&patientId="+${patientId} + "&idSelected=" + idSelected + "&type=${type}")
}

</script>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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


<!-- PROGRAM ENROLLMENT BOX-->
<b class="boxHeader" style="margin:0px"><spring:message code="mdrtb.enrollment.enroll" text="Enroll in Program"/></b>
<div class="box" style="margin:0px">

<c:choose>
<c:when test="${type eq 'tb' }">
<form id="enrollment" action="${pageContext.request.contextPath}/module/mdrtb/program/otherEnrollmentTb.form?patientId=${patientId}&patientProgramId=-1" method="post" >

<table cellspacing="2" cellpadding="2">

<tr>
<td>${dotsIdentifier.name}</td>
<td><input name="identifierValue" id="identifierValue" type="text" value="${identifierValue}"></td>
</tr>

<tr><td>
<spring:message code="mdrtb.enrollment.date" text="Enrollment Date"/>:</td><td><input id="dateEnrolled" type="text" size="14" tabindex="-1" name="dateEnrolled" value="<openmrs:formatDate date='${program.dateEnrolled}'/>" onFocus="showCalendar(this)"/>
</td></tr>

<%-- <tr><td>
<spring:message code="mdrtb.enrollment.location" text="Enrollment Location"/>:</td><td>
<select name="location">
<option value=""/>
<c:forEach var="location" items="${locations}">
<option value="${location.locationId}" <c:if test="${location == program.location}">selected</c:if> >${location.displayString}</option>
</c:forEach>
</select>
</td></tr> --%>

</table>

<table>
<tr id="oblastDiv">
			<td align="right"><spring:message code="mdrtb.oblast" /></td>
			<td><select name="oblast" id="oblast" onchange="fun1()">
					<option value=""></option>
					<c:forEach var="o" items="${oblasts}">
						<option value="${o.id}">${o.name}</option>
					</c:forEach>
			</select></td>
		</tr>
		
		<tr id="districtDiv">
			<td align="right"><spring:message code="mdrtb.district" /></td>
			<td><select name="district" id="district" onchange="fun2()">
					<option value=""></option>
					<c:forEach var="dist" items="${districts}">
						<option value="${dist.id}">${dist.name}</option>
					</c:forEach>
			</select></td>
		</tr>
		
		<tr id="facilityDiv">
			<td align="right"><spring:message code="mdrtb.facility" /></td>
			<td><select name="facility" id="facility">
					<option value=""></option>
					<c:forEach var="f" items="${facilities}">
						<option value="${f.id}">${f.name}</option>
					</c:forEach>
			</select>
			</td>
		</tr>
	</table>

<table>

<tr><td colspan="2">
<spring:message code="mdrtb.tb03.registrationGroup" text="Registration Group"/>:<br/>
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
<button type="submit"><spring:message code="mdrtb.enrollment.enroll" text="Enroll in Program"/>
</form>
</c:when>

<c:otherwise>
<form id="enrollment" action="${pageContext.request.contextPath}/module/mdrtb/program/otherEnrollmentMdrtb.form?patientId=${patientId}&patientProgramId=-1" method="post" >
<%-- <input type="hidden" name="previousProgramId" value="${previousProgramId}"/> --%>
<table cellspacing="2" cellpadding="2">

<tr>
<td>${mdrIdentifier.name}</td>
<td><input name="identifierValue" id="identifierValue" type="text"></td>
</tr>

<tr><td>
<spring:message code="mdrtb.enrollment.date" text="Enrollment Date"/>:</td><td><input id="dateEnrolled" type="text" size="14" tabindex="-1" name="dateEnrolled" value="<openmrs:formatDate date='${programStartDate}'/>" onFocus="showCalendar(this)"/>
</td></tr>

<%-- <tr><td>
<spring:message code="mdrtb.enrollment.location" text="Enrollment Location"/>:</td><td>
<select name="location">
<option value=""/>
<c:forEach var="location" items="${locations}">
<option value="${location.locationId}" <c:if test="${location == initLocation}">selected</c:if> >${location.displayString}</option>
</c:forEach>
</select>
</td></tr> --%>

</table>

<table>
<tr id="oblastDiv">
			<td align="right"><spring:message code="mdrtb.oblast" /></td>
			<td><select name="oblast" id="oblast" onchange="fun1()">
					<option value=""></option>
					<c:forEach var="o" items="${oblasts}">
						<option value="${o.id}">${o.name}</option>
					</c:forEach>
			</select></td>
		</tr>
		
		<tr id="districtDiv">
			<td align="right"><spring:message code="mdrtb.district" /></td>
			<td><select name="district" id="district" onchange="fun2()">
					<option value=""></option>
					<c:forEach var="dist" items="${districts}">
						<option value="${dist.id}">${dist.name}</option>
					</c:forEach>
			</select></td>
		</tr>
		
		<tr id="facilityDiv">
			<td align="right"><spring:message code="mdrtb.facility" /></td>
			<td><select name="facility" id="facility">
					<option value=""></option>
					<c:forEach var="f" items="${facilities}">
						<option value="${f.id}">${f.name}</option>
					</c:forEach>
			</select>
			</td>
		</tr>
	</table>

<table>

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
<button type="submit"><spring:message code="mdrtb.enrollment.enroll" text="Enroll in Program"/>
</form>

</c:otherwise>
</c:choose>
</div>


<!-- END PROGRAM ENROLLMENT BOX -->

</div> <!-- end of page div -->

<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbFooter.jsp"%>