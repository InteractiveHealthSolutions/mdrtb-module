<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%> 
<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="/moduleResources/mdrtb/mdrtb.css"/>

<!-- TODO: clean up above paths so they use dynamic reference -->
<!-- TODO: add privileges? -->

<!-- SPECIALIZED STYLES FOR THIS PAGE -->

<!-- CUSTOM JQUERY  -->
<c:set var="defaultReturnUrl" value="${pageContext.request.contextPath}/module/mdrtb/dashboard/dashboard.form?patientProgramId=${patientProgramId}"/>
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
<div id="viewVisit" <c:if test="${(empty tb03.id) || (tb03.id == -1) || fn:length(errors.allErrors) > 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.tb03Form" text="TB03 Form"/>
<span style="position: absolute; right:30px;"><a id="edit" onmouseover="document.body.style.cursor='pointer'" onmouseout="document.body.style.cursor='default'"><spring:message code="mdrtb.edit" text="edit"/></a>&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/module/mdrtb/visits/delete.form?visitId=${tb03.id}&patientProgramId=${patientProgramId}" class="delete" onclick="return confirm('<spring:message code="mdrtb.confirmDeleteVisit" text="Are you sure you want to delete this visit?"/>')"><spring:message code="mdrtb.delete" text="delete"/></a></span>
</b>
<div class="box">

<table>
 
<tr>
<td><spring:message code="mdrtb.date" text="Date"/>:</td>
<td><openmrs:formatDate date="${tb03.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.provider" text="Provider"/>:</td>
<td>${tb03.provider.personName}</td>
</tr>
 
<tr>
<td><spring:message code="mdrtb.location" text="Location"/>:</td>
<td>${tb03.location.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.gender" text="Gender"/>:</td>
<td>${tb03.gender}</td>
</tr>
 
<tr>
<td><spring:message code="mdrtb.tb03.ageAtRegistration" text="Age at Regisration"/>:</td>
<td>${tb03.ageAtTB03Registration}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.dateOfBirth" text="Date of Birth"/>:</td>
<td><openmrs:formatDate date="${tb03.dateOfBirth}" format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.address" text="Residential Address"/>:</td>
<td>${tb03.address }</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.treatmentSiteIP" text="Tx Site IP"/>:</td>
<td>${tb03.treatmentSiteIP.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.treatmentSiteCP" text="Tx Site CP"/>:</td>
<td>${tb03.treatmentSiteCP.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.patientCategory" text="Tx Regimen"/>:</td>
<td>${tb03.patientCategory.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.treatmentStartDate" text="Treatment Start Date"/>:</td>
<td><openmrs:formatDate date="${tb03.treatmentStartDate}" format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.siteOfDisease" text="Anatomical Type"/>:</td>
<td>${tb03.anatomicalSite.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.registrationGroup" text="Registration Group"/>:</td>
<td>${tb03.registrationGroup.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.hivStatus" text="HIV Status"/>:</td>
<td>${tb03.hivStatus.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.artStartDate" text="ART Start Date"/>:</td>
<td><openmrs:formatDate date="${tb03.artStartDate}" format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.cptStartDate" text="PCT Start Date"/>:</td>
<td><openmrs:formatDate date="${tb03.pctStartDate}" format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.resistanceType" text="Type of Resistance"/>:</td>
<td>${tb03.resistanceType.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.treatmentOutcome" text="Tx Outcome"/>:</td>
<td>${tb03.treatmentOutcome.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.treatmentOutcomeDateOnly" text="Outcome Date"/>:</td>
<td><openmrs:formatDate date="${tb03.treatmentOutcomeDate}" format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.afterOutcomeDeathDate" text="Date of Death after Outcome"/>:</td>
<td><openmrs:formatDate date="${tb03.dateOfDeathAfterOutcome}"  format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.clinicalNotes" text="Clinical Notes"/>:</td>
<td>${tb03.cliniciansNotes}</td>
</tr>

<%-- <tr>
<td><spring:message code="mdrtb.tb03.doseTakenIP" text="Dose taken for IP"/>:</td>
<td>${tb03.doseTakenIP}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.doseMissedIP" text="Dose missed for IP"/>:</td>
<td>${tb03.doseMissedIP}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.doseTakenCP" text="Dose taken for CP"/>:</td>
<td>${tb03.doseTakenCP}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.doseMissedCP" text="Dose missed for CP"/>:</td>
<td>${tb03.doseMissedCP}</td>
</tr> --%>

</table>

</div>
</div>
<!-- END VIEW BOX -->

<!-- EDIT BOX -->
<div id="editVisit" <c:if test="${(!empty tb03.id) && (tb03.id != -1) && fn:length(errors.allErrors) == 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.tb03Form" text="TB03 Form"/></b>
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

<form name="tb03" action="tb03.form?patientId=${patientId}&patientProgramId=${patientProgramId}&encounterId=${!empty tb03.id ? tb03.id : -1}" method="post">
<input type="hidden" name="returnUrl" value="${returnUrl}" />

<table>
 
<tr>
<td><spring:message code="mdrtb.date" text="Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="encounterDatetime" startValue="${tb03.encounterDatetime}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.provider" text="Provider"/>:</td>
<td>
<select name="provider">
<option value=""></option>
<c:forEach var="provider" items="${providers}">
	<option value="${provider.id}" <c:if test="${tb03.provider == provider}">selected</c:if>>${provider.personName}</option>
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
	<option value="${location.id}" <c:if test="${tb03.location == location}">selected</c:if>>${location.displayString}</option>
</c:forEach>
</select>
</td>
</tr>
 
<tr>
<td><spring:message code="mdrtb.gender" text="Gender"/>:</td>
<td>${tb03.gender}</td>
</tr>
 
<tr>
<td valign="top"><spring:message code="mdrtb.tb03.ageAtRegistration" text="Age at Regisration"/>:</td>
<td><input name="ageAtTB03Registration" size="8" value="${tb03.ageAtTB03Registration}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.dateOfBirth" text="Date of Birth"/>:</td>
<td><openmrs:formatDate date="${tb03.dateOfBirth}" format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.address" text="Residential Address"/>:</td>
<td>${tb03.address }</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.treatmentSiteIP" text="Tx Site IP"/>:</td>
<td>
<select name="treatmentSiteIP">
<option value=""></option>
<c:forEach var="iptxsite" items="${iptxsites}">
	<option value="${iptxsite.answerConcept.id}" <c:if test="${tb03.treatmentSiteIP == iptxsite.answerConcept}">selected</c:if> >${iptxsite.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.treatmentSiteCP" text="Tx Site CP"/>:</td>
<td>
<select name="treatmentSiteCP">
<option value=""></option>
<c:forEach var="cptxsite" items="${cptxsites}">
	<option value="${cptxsite.answerConcept.id}" <c:if test="${tb03.treatmentSiteCP == cptxsite.answerConcept}">selected</c:if> >${cptxsite.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.patientCategory" text="Regimen Type"/>:</td>
<td>
<select name="patientCategory">
<option value=""></option>
<c:forEach var="category" items="${categories}">
	<option value="${category.answerConcept.id}" <c:if test="${tb03.patientCategory == category.answerConcept}">selected</c:if> >${category.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.treatmentStartDate" text="Tx Start Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="treatmentStartDate" startValue="${tb03.treatmentStartDate}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.siteOfDisease" text="Anatomical Type"/>:</td>
<td>
<select name="anatomicalSite">
<option value=""></option>
<c:forEach var="site" items="${sites}">
	<option value="${site.answerConcept.id}" <c:if test="${tb03.anatomicalSite == site.answerConcept}">selected</c:if> >${site.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.registrationGroup" text="Registration Group"/>:</td>
<td>
<select name="registrationGroup">
<option value=""></option>
<c:forEach var="group" items="${groups}">
	<option value="${group.concept.id}" <c:if test="${tb03.registrationGroup == group.concept}">selected</c:if> >${group.concept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.hivStatus" text="HIV Status"/>:</td>
<td>
<select name="hivStatus">
<option value=""></option>
<c:forEach var="status" items="${hivstatuses}">
	<option value="${status.answerConcept.id}" <c:if test="${tb03.hivStatus == status.answerConcept}">selected</c:if> >${status.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.artStartDate" text="ART Start Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="artStartDate" startValue="${tb03.artStartDate}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.cptStartDate" text="PCT Start Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="pctStartDate" startValue="${tb03.pctStartDate}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.resistanceType" text="Type of Resistance"/>:</td>
<td>
<select name="resistanceType">
<option value=""></option>
<c:forEach var="type" items="${resistancetypes}">
	<option value="${type.answerConcept.id}" <c:if test="${tb03.resistanceType == type.answerConcept}">selected</c:if> >${type.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.treatmentOutcome" text="Tx Outcome"/>:</td>
<td>
<select name="treatmentOutcome">
<option value=""></option>
<c:forEach var="outcome" items="${outcomes}">
	<option value="${outcome.concept.id}" <c:if test="${tb03.treatmentOutcome == outcome.concept}">selected</c:if> >${outcome.concept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.treatmentOutcomeDateOnly" text="Outcome Dates"/>:</td>
<td><openmrs_tag:dateField formFieldName="treatmentOutcomeDate" startValue="${tb03.treatmentOutcomeDate}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.afterOutcomeDeathDate" text="Date of Death after Outcome"/>:</td>
<td><openmrs_tag:dateField formFieldName="afterOutcomeDeathDate" startValue="${tb03.dateOfDeathAfterOutcome}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.clinicalNotes" text="Clinical Notes"/>:</td>
<td><textarea rows="4" cols="50" name="cliniciansNotes">${tb03.cliniciansNotes}</textarea></td>
</tr>

<%-- <tr>
<td><spring:message code="mdrtb.tb03.doseTakenIP" text="Dose taken for IP"/>:</td>
<td><input name="doseTakenIP" size="8" value="${tb03.doseTakenIP}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.doseMissedIP" text="Dose missed for IP"/>:</td>
<td><input name="doseMissedIP" size="8" value="${tb03.doseMissedIP}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.doseTakenCP" text="Dose taken for CP"/>:</td>
<td><input name="doseTakenCP" size="8" value="${tb03.doseTakenCP}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.doseMissedCP" text="Dose missed for CP"/>:</td>
<td><input name="doseMissedCP" size="8" value="${tb03.doseMissedCP}"/></td>
</tr> --%>


</table>

<button type="submit"><spring:message code="mdrtb.save" text="Save"/></button> <button id="cancel" type="reset"><spring:message code="mdrtb.cancel" text="Cancel"/></button>
	
</form>

</div>
</div>
<!-- END OF EDIT BOX -->

</div> <!-- end of page div -->

<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbFooter.jsp"%>