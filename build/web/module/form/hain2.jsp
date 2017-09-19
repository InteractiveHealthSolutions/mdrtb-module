<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%> 
<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="/moduleResources/mdrtb/mdrtb.css"/>

<!-- TODO: clean up above paths so they use dynamic reference -->
<!-- TODO: add privileges? -->

<!-- SPECIALIZED STYLES FOR THIS PAGE -->

<!-- CUSTOM JQUERY  -->
<c:set var="defaultReturnUrl" value="${pageContext.request.contextPath}/module/mdrtb/dashboard/tbdashboard.form?patientProgramId=${patientProgramId}&patientId=${hain2.patient.id}"/>
<script type="text/javascript"><!--

	var $j = jQuery.noConflict();	

	$j(document).ready(function(){

		$j('#edit').click(function(){
			$j('#viewVisit').hide();
			$j('#editVisit').show();
			resToggle();
		});

		$j('#cancel').click(function(){
			if (${(empty intake.id) || (intake.id == -1) || fn:length(errors.allErrors) > 0}) {
				// if we are in the middle of a validation error, or doing an "add" we need to do a page reload on cancel
				window.location="${!empty returnUrl ? returnUrl : defaultReturnUrl}";
			} 
			else {
				// otherwise, just hide the edit popup and show the view one	
				$j('#editVisit').hide();
				$j('#viewVisit').show();
			}
		});
		
	});
	
function resToggle () {
		
		var statusBox = document.getElementById('mtbResult');
		var choice  = statusBox.options[statusBox.selectedIndex].value;
		showHideRes(choice);
	}
	
	function showHideRes(val) {
       	
       	if(val==449) {
       		
       		document.getElementById('fqResult').disabled = false;
       		document.getElementById('injResult').disabled = false;
       		
       		
       	}
       	else {
       		 	
       		 	document.getElementById('fqResult').disabled = true;
       			document.getElementById('fqResult').selectedIndex = 0;
       			document.getElementById('injResult').disabled = true;
       			document.getElementById('injResult').selectedIndex = 0;
       	}
     }


-->

</script>

<br/>

<div> <!-- start of page div -->

&nbsp;&nbsp;<a href="${!empty returnUrl ? returnUrl : defaultReturnUrl}"><spring:message code="mdrtb.back" text="Back"/></a>
<br/><br/>

<!-- VIEW BOX -->
<div id="viewVisit" <c:if test="${(empty hain2.id) || (hain2.id == -1) || fn:length(errors.allErrors) > 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.hain2" text="HAIN2 Form"/>
<span style="position: absolute; right:30px;"><a id="edit" onmouseover="document.body.style.cursor='pointer'" onmouseout="document.body.style.cursor='default'"><spring:message code="mdrtb.edit" text="edit"/></a>&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/module/mdrtb/visits/delete.form?visitId=${hain2.id}&patientProgramId=${patientProgramId}" class="delete" onclick="return confirm('<spring:message code="mdrtb.confirmDeleteVisit" text="Are you sure you want to delete this visit?"/>')"><spring:message code="mdrtb.delete" text="delete"/></a></span>
</b>
<div class="box">

<table>
 
<tr>
<td><spring:message code="mdrtb.dateCollected" text="Date"/>:</td>
<td><openmrs:formatDate date="${hain2.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
</tr>

<%-- <tr>
<td><spring:message code="mdrtb.provider" text="Provider"/>:</td>
<td>${hain2.provider.personName}</td>
</tr> --%>
 
<tr>
<td><spring:message code="mdrtb.location" text="Location"/>:</td>
<td>${hain2.location.displayString}</td>
</tr>



<tr>
<td><spring:message code="mdrtb.specimenId" text="SpecimenId"/>:</td>
<td>${hain2.specimenId }</td>
</tr>

<tr>
<td><spring:message code="mdrtb.mtbResult" text="MtbResult"/>:</td>
<td>${hain2.mtbResult.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.injResult" text="injResult"/>:</td>
<td>${hain2.injResult.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.fqResult" text="fqResult"/>:</td>
<td>${hain2.fqResult.displayString}</td>
</tr>


</table>

</div>
</div>
<!-- END VIEW BOX -->

<!-- EDIT BOX -->
<div id="editVisit" <c:if test="${(!empty hain2.id) && (hain2.id != -1) && fn:length(errors.allErrors) == 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.hain2" text="HAIN"/></b>
<div class="box">

<!--  DISPLAY ANY ERROR MESSAGES -->
<c:if test="${fn:length(errors.allErrors) > 0}">
	<c:forEach var="error" items="${errors.allErrors}">
		<c:if test="${error.code != 'methodInvocation'}">
			<span class="error"><spring:message code="${error.code}"/></span><br/><br/>
		</c:if>	
	</c:forEach>
	<br/>
</c:if>

<form name="hain2" action="hain2.form?patientId=${patientId}&patientProgramId=${patientProgramId}&encounterId=${!empty hain2.id ? hain2.id : -1}" method="post">
<input type="hidden" name="returnUrl" value="${returnUrl}" />
<input type="hidden" name="patProgId" value="${patientProgramId}" />
<input type="hidden" name="provider" value="45" />

<table>
 
<tr>
<td><spring:message code="mdrtb.dateCollected" text="Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="encounterDatetime" startValue="${hain2.encounterDatetime}"/></td>
</tr>

<%-- <tr>
<td><spring:message code="mdrtb.provider" text="Provider"/>:</td>
<td>
<select name="provider">
<option value=""></option>
<c:forEach var="provider" items="${providers}">
	<option value="${provider.id}" <c:if test="${hain2.provider == provider}">selected</c:if>>${provider.personName}</option>
</c:forEach>
</select>
</td>
</tr> --%>
 
<tr>
<td><spring:message code="mdrtb.location" text="Location"/>:</td>
<td>
<select name="location">
<option value=""></option>
<c:forEach var="location" items="${locations}">
	<option value="${location.id}" <c:if test="${hain2.location == location}">selected</c:if>>${location.displayString}</option>
</c:forEach>
</select>
</td>
</tr>


<tr>
<td><spring:message code="mdrtb.specimenId" text="SpecimenId"/>:</td>
<td><input type="text" size="10" name="specimenId" value="${hain2.specimenId}"/></td>
</tr>


<tr>
<td><spring:message code="mdrtb.mtbResult" text="mtbResult" />:</td>
<td>
<select name="mtbResult" id="mtbResult" onChange="resToggle()">
<option value=""></option>
<c:forEach var="result" items="${mtbresults}">
	<option value="${result.answerConcept.id}" <c:if test="${hain2.mtbResult == result.answerConcept}">selected</c:if> >${result.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.injResult" text="injResult"/>:</td>
<td>
<select name="injResult" id="injResult">
<option value=""></option>
<c:forEach var="result" items="${injresults}">
	<option value="${result.answerConcept.id}" <c:if test="${hain2.injResult == result.answerConcept}">selected</c:if> >${result.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>


<tr>
<td><spring:message code="mdrtb.fqResult" text="fqResult"/>:</td>
<td>
<select name="fqResult" id="fqResult">
<option value=""></option>
<c:forEach var="result" items="${fqresults}">
	<option value="${result.answerConcept.id}" <c:if test="${hain2.fqResult == result.answerConcept}">selected</c:if> >${result.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>



</table>

<button type="submit"><spring:message code="mdrtb.save" text="Save"/></button> <button id="cancel" type="reset"><spring:message code="mdrtb.cancel" text="Cancel"/></button>
	
</form>

</div>
</div>
<!-- END OF EDIT BOX -->
</div>

</div> <!-- end of page div -->

<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbFooter.jsp"%>