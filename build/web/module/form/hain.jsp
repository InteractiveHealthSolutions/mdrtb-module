<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%> 
<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="/moduleResources/mdrtb/mdrtb.css"/>

<!-- TODO: clean up above paths so they use dynamic reference -->
<!-- TODO: add privileges? -->

<!-- SPECIALIZED STYLES FOR THIS PAGE -->

<!-- CUSTOM JQUERY  -->
<c:set var="defaultReturnUrl" value="${pageContext.request.contextPath}/module/mdrtb/dashboard/tbdashboard.form?patientProgramId=${patientProgramId}"/>
<script type="text/javascript"><!--

	var $j = jQuery.noConflict();	

	$j(document).ready(function(){

		$j('#edit').click(function(){
			$j('#viewVisit').hide();
			$j('#editVisit').show();
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


-->

</script>

<br/>

<div> <!-- start of page div -->

&nbsp;&nbsp;<a href="${!empty returnUrl ? returnUrl : defaultReturnUrl}"><spring:message code="mdrtb.back" text="Back"/></a>
<br/><br/>

<!-- VIEW BOX -->
<div id="viewVisit" <c:if test="${(empty hain.id) || (hain.id == -1) || fn:length(errors.allErrors) > 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.hain" text="HAIN Form"/>
<span style="position: absolute; right:30px;"><a id="edit" onmouseover="document.body.style.cursor='pointer'" onmouseout="document.body.style.cursor='default'"><spring:message code="mdrtb.edit" text="edit"/></a>&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/module/mdrtb/visits/delete.form?visitId=${hain.id}&patientProgramId=${patientProgramId}" class="delete" onclick="return confirm('<spring:message code="mdrtb.confirmDeleteVisit" text="Are you sure you want to delete this visit?"/>')"><spring:message code="mdrtb.delete" text="delete"/></a></span>
</b>
<div class="box">

<table>
 
<tr>
<td><spring:message code="mdrtb.dateCollected" text="Date"/>:</td>
<td><openmrs:formatDate date="${hain.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.provider" text="Provider"/>:</td>
<td>${hain.provider.personName}</td>
</tr>
 
<tr>
<td><spring:message code="mdrtb.location" text="Location"/>:</td>
<td>${hain.location.displayString}</td>
</tr>



<tr>
<td><spring:message code="mdrtb.specimenId" text="SpecimenId"/>:</td>
<td>${hain.specimenId }</td>
</tr>

<tr>
<td><spring:message code="mdrtb.mtbResult" text="MtbResult"/>:</td>
<td>${hain.mtbResult.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.inhResult" text="inhResult"/>:</td>
<td>${hain.inhResult.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.rifResult" text="rifResult"/>:</td>
<td>${hain.rifResult.displayString}</td>
</tr>


</table>

</div>
</div>
<!-- END VIEW BOX -->

<!-- EDIT BOX -->
<div id="editVisit" <c:if test="${(!empty hain.id) && (hain.id != -1) && fn:length(errors.allErrors) == 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.hain" text="HAIN"/></b>
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

<form name="hain" action="hain.form?patientId=${patientId}&patientProgramId=${patientProgramId}&encounterId=${!empty hain.id ? hain.id : -1}" method="post">
<input type="hidden" name="returnUrl" value="${returnUrl}" />
<input type="hidden" name="patProgId" value="${patientProgramId}" />

<table>
 
<tr>
<td><spring:message code="mdrtb.dateCollected" text="Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="encounterDatetime" startValue="${hain.encounterDatetime}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.provider" text="Provider"/>:</td>
<td>
<select name="provider">
<option value=""></option>
<c:forEach var="provider" items="${providers}">
	<option value="${provider.id}" <c:if test="${hain.provider == provider}">selected</c:if>>${provider.personName}</option>
</c:forEach>
</select>
</td>
</tr>
 
<tr>
<td><spring:message code="mdrtb.location" text="Location"/>:</td>
<td>
<select name="location">
<option value=""></option>
<c:forEach var="location" items="${locations}">
	<option value="${location.id}" <c:if test="${hain.location == location}">selected</c:if>>${location.displayString}</option>
</c:forEach>
</select>
</td>
</tr>


<tr>
<td><spring:message code="mdrtb.specimenId" text="SpecimenId"/>:</td>
<td><input type="text" size="10" name="specimenId" value="${hain.specimenId}"/></td>
</tr>


<tr>
<td><spring:message code="mdrtb.mtbResult" text="mtbResult"/>:</td>
<td>
<select name="mtbResult">
<option value=""></option>
<c:forEach var="result" items="${mtbresults}">
	<option value="${result.answerConcept.id}" <c:if test="${hain.mtbResult == result.answerConcept}">selected</c:if> >${result.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.inhResult" text="inhResult"/>:</td>
<td>
<select name="inhResult">
<option value=""></option>
<c:forEach var="result" items="${inhresults}">
	<option value="${result.answerConcept.id}" <c:if test="${hain.inhResult == result.answerConcept}">selected</c:if> >${result.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>


<tr>
<td><spring:message code="mdrtb.rifResult" text="rifResult"/>:</td>
<td>
<select name="rifResult">
<option value=""></option>
<c:forEach var="result" items="${rifresults}">
	<option value="${result.answerConcept.id}" <c:if test="${hain.rifResult == result.answerConcept}">selected</c:if> >${result.answerConcept.displayString}</option>
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