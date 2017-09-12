<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%> 
<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="/moduleResources/mdrtb/mdrtb.css"/>

<!-- TODO: clean up above paths so they use dynamic reference -->
<!-- TODO: add privileges? -->

<!-- SPECIALIZED STYLES FOR THIS PAGE -->

<!-- CUSTOM JQUERY  -->
<c:set var="defaultReturnUrl" value="${pageContext.request.contextPath}/module/mdrtb/dashboard/tbdashboard.form?patientProgramId=${patientProgramId}&patientId=${form89.patient.id}"/>
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
<div id="viewVisit" <c:if test="${(empty form89.id) || (form89.id == -1) || fn:length(errors.allErrors) > 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.form89" text="Form89"/>
<span style="position: absolute; right:30px;"><a id="edit" onmouseover="document.body.style.cursor='pointer'" onmouseout="document.body.style.cursor='default'"><spring:message code="mdrtb.edit" text="edit"/></a>&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/module/mdrtb/visits/delete.form?visitId=${form89.id}&patientProgramId=${patientProgramId}" class="delete" onclick="return confirm('<spring:message code="mdrtb.confirmDeleteVisit" text="Are you sure you want to delete this visit?"/>')"><spring:message code="mdrtb.delete" text="delete"/></a></span>
</b>
<div class="box">

<table>
 
<tr>
<td><spring:message code="mdrtb.date" text="Date"/>:</td>
<td><openmrs:formatDate date="${form89.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
</tr>

<%-- <tr>
<td><spring:message code="mdrtb.provider" text="Provider"/>:</td>
<td>${form89.provider.personName}</td>
</tr>
  --%>
<tr>
<td><spring:message code="mdrtb.location" text="Location"/>:</td>
<td>${form89.location.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.name" text="Name"/>:</td>
<td>${form89.patientName}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.tb03RegistrationNumber" text="TB03 Reg Num"/>:</td>
<%-- <td>${form89.tb03RegistrationNumber}</td> --%>
<td>${tbProgram.patientIdentifier.identifier }</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.yearOfRegistration" text="TB03 Reg Year"/>:</td>
<td>${form89.yearOfTB03Registration}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.gender" text="Gender"/>:</td>
<td>${form89.gender}</td>
</tr>
 
 <tr>
<td><spring:message code="mdrtb.tb03.dateOfBirth" text="Date of Birth"/>:</td>
<td><openmrs:formatDate date="${form89.dateOfBirth}" format="${_dateFormatDisplay}"/></td>
</tr>
 
<tr>
<td><spring:message code="mdrtb.form89.ageAtRegistration" text="Age at Regisration"/>:</td>
<td>${form89.ageAtRegistration}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.address" text="Residential Address"/>:</td>
<td>${form89.address }</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.locationType" text="Resident"/>:</td>
<td>${form89.locationType.displayString }</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.profession" text="Profession"/>:</td>
<td>${form89.profession.displayString }</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.populationCategory" text="PopulationCategory"/>:</td>
<td>${form89.populationCategory.displayString }</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.placeOfDetection" text="DetPlace"/>:</td>
<td>${form89.placeOfDetection.displayString }</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.dateFirstSeekingHelp" text="dateFirstSeekingHelp"/>:</td>
<td><openmrs:formatDate date="${form89.dateFirstSeekingHelp}" format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.circumstancesOfDetection" text="DetPlace"/>:</td>
<td>${form89.circumstancesOfDetection.displayString }</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.methodOfDetection" text="mDet"/>:</td>
<td>${form89.methodOfDetection.displayString }</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.siteOfDisease" text="Anatomical Type"/>:</td>
<td>${form89.anatomicalSite.displayString}</td>
</tr>

<c:if test="${form89.isPulmonary eq false}">
<tr>
<td><spring:message code="mdrtb.form89.epSite" text="EP Site"/>:</td>
<td>${form89.epSite.displayString}</td>
</tr>
</c:if>

<c:if test="${form89.isPulmonary eq true}">
<tr>
<td><spring:message code="mdrtb.form89.pSite" text="P Site"/>:</td>
<td>${form89.pulSite.displayString}</td>
</tr>
</c:if>

<c:if test="${form89.isPulmonary eq false}">
<tr>
<td><spring:message code="mdrtb.form89.epLocation" text="EP Location"/>:</td>
<td>${form89.epLocation.displayString}</td>
</tr>
</c:if>

<tr>
<td><spring:message code="mdrtb.form89.presenceOfDecay" text="Decay"/>:</td>
<td>${form89.presenceOfDecay.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.dateOfDecaySurvey" text="dateOfDecaySurvey"/>:</td>
<td><openmrs:formatDate date="${form89.dateOfDecaySurvey}" format="${_dateFormatDisplay}"/></td>
</tr>

</table>

<br/>

<spring:message code="mdrtb.smears" text="Smearz"/>
<table border="1">
<tr>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.monthOfTreatment"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.result"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.dateCollected"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.lab"/></td>
</tr>
<c:forEach var="smear" items="${form89.smears}">
<c:if test="${smear.monthOfTreatment eq 0 }">
<tr>
<td>${smear.monthOfTreatment }</td>
<td>${smear.smearResult.displayString }</td>
<td><openmrs:formatDate date="${smear.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
<td>${smear.location.displayString}</td>
</tr>
</c:if>
</c:forEach>

</tr>
</table>

<br/>

<spring:message code="mdrtb.xperts" text="Xpertz"/>
<table border="1">
<tr>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.result"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.dateCollected"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.lab"/></td>
</tr>

<c:forEach var="xpert" items="${form89.xperts}">
<tr>
<td>${xpert.mtbResult.displayString }/RIF: ${xpert.rifResult.displayString }</td>
<td><openmrs:formatDate date="${xpert.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
<td>${xpert.location.displayString}</td>
</c:forEach>
</tr>
</table>

<br/>

<spring:message code="mdrtb.hains" text="Hainz"/>
<table border="1">
<tr>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.result"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.dateCollected"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.lab"/></td>
</tr>

<c:forEach var="hain" items="${form89.hains}">
<tr>
<td>${hain.mtbResult.displayString }/RIF: ${hain.rifResult.displayString }/ INH: ${hain.inhResult.displayString }</td>
<td><openmrs:formatDate date="${hain.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
<td>${hain.location.displayString}</td>
</c:forEach>
</tr>
</table>

<br/>

<spring:message code="mdrtb.form89.comorbidities" text="Comorb"/>
<table>


<tr>
<td><spring:message code="mdrtb.form89.mentalDisorder" text="mentalDisorder"/>:</td>
<td>${form89.mentalDisorder.displayString}</td>
</tr>


<tr>
<td><spring:message code="mdrtb.form89.cnsdl" text="cnsdl"/>:</td>
<td>${form89.cnsdl.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.htHeartDisease" text="htheart"/>:</td>
<td>${form89.htHeartDisease.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.ulcer" text="ulcer"/>:</td>
<td>${form89.ulcer.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.mentalDisorder" text="mentalDisorder"/>:</td>
<td>${form89.mentalDisorder.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.ibc20" text="ibc20"/>:</td>
<td>${form89.ibc20.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.cancer" text="cancer"/>:</td>
<td>${form89.cancer.displayString}</td>
</tr>

<%-- <tr>
<td><spring:message code="mdrtb.form89.noDisease" text="noDisease"/>:</td>
<td>${form89.noDisease.displayString}</td>
</tr> --%>

<tr>
<td><spring:message code="mdrtb.form89.otherDisease" text="otherDisease"/>:</td>
<td>${form89.otherDisease}</td>
</tr>



</table>

<table>

<tr>
<td><spring:message code="mdrtb.form89.cmacDate" text="CMAC Date"/>:</td>
<td><openmrs:formatDate date="${form89.cmacDate}" format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.cmacNumber" text="CMAC Number"/>:</td>
<td>${form89.cmacNumber}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.prescribedTreatment" text="prescribedTreatment"/>:</td>
<td>${form89.prescribedTreatment.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.nameOfDoctor" text="DocName"/>:</td>
<td>${form89.nameOfDoctor}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.form89Date" text="form89Date"/>:</td>
<td><openmrs:formatDate date="${form89.form89Date}" format="${_dateFormatDisplay}"/></td>
</tr>



</table>

</div>
</div>
<!-- END VIEW BOX -->

<!-- EDIT BOX -->
<div id="editVisit" <c:if test="${(!empty form89.id) && (form89.id != -1) && fn:length(errors.allErrors) == 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.form89" text="Form89"/></b>
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

<form name="form89" action="form89.form?patientId=${patientId}&patientProgramId=${patientProgramId}&encounterId=${!empty form89.id ? form89.id : -1}" method="post">
<input type="hidden" name="returnUrl" value="${returnUrl}" />
<input type="hidden" name="patProgId" value="${patientProgramId}" />
<input type="hidden" name="provider" value="47" />

<table>
 
<tr>
<td><spring:message code="mdrtb.date" text="Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="encounterDatetime" startValue="${form89.encounterDatetime}"/></td>
</tr>

<%-- <tr>
<td><spring:message code="mdrtb.provider" text="Provider"/>:</td>
<td>
<select name="provider">
<option value=""></option>
<c:forEach var="provider" items="${providers}">
	<option value="${provider.id}" <c:if test="${form89.provider == provider}">selected</c:if>>${provider.personName}</option>
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
	<option value="${location.id}" <c:if test="${form89.location == location}">selected</c:if>>${location.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.name" text="Name"/>:</td>
<td>${form89.patientName}</td>
</tr>

<tr>
<td valign="top"><spring:message code="mdrtb.tb03.tb03RegistrationNumber" text="TB03RegistrationNumber"/>:</td>
<%-- <td><input name="tb03RegistrationNumber" size="12" value="${form89.tb03RegistrationNumber}"/></td> --%>
<td>${tbProgram.patientIdentifier.identifier }</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.yearOfRegistration" text="TB03 Reg Year"/>:</td>
<td>${form89.yearOfTB03Registration}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.gender" text="Gender"/>:</td>
<td>${form89.gender}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.dateOfBirth" text="Date of Birth"/>:</td>
<td><openmrs:formatDate date="${form89.dateOfBirth}" format="${_dateFormatDisplay}"/></td>
</tr>
 
<tr>
<td valign="top"><spring:message code="mdrtb.form89.ageAtRegistration" text="Age at Registration"/>:</td>
<%-- <td><input name="ageAtRegistration" size="8" value="${form89.ageAtRegistration}"/></td> --%>
<td>${form89.ageAtRegistration}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.address" text="Residential Address"/>:</td>
<td>${form89.address }</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.locationType" text="Resident"/>:</td>
<td><select name="locationType">
<option value=""></option>
<c:forEach var="type" items="${locationtypes}">
	<option value="${type.answerConcept.id}" <c:if test="${form89.locationType == type.answerConcept}">selected</c:if> >${type.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.profession" text="Profession"/>:</td>
<td><select name="profession">
<option value=""></option>
<c:forEach var="prof" items="${professions}">
	<option value="${prof.answerConcept.id}" <c:if test="${form89.profession == prof.answerConcept}">selected</c:if> >${prof.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.populationCategory" text="PopCat"/>:</td>
<td><select name="populationCategory">
<option value=""></option>
<c:forEach var="cat" items="${populationcategories}">
	<option value="${cat.answerConcept.id}" <c:if test="${form89.populationCategory == cat.answerConcept}">selected</c:if> >${cat.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.placeOfDetection" text="DetPlace"/>:</td>
<td><select name="placeOfDetection">
<option value=""></option>
<c:forEach var="p" items="${places}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.placeOfDetection == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.dateFirstSeekingHelp" text="dateFirstSeekingHelp"/>:</td>
<td><openmrs_tag:dateField formFieldName="dateFirstSeekingHelp" startValue="${form89.dateFirstSeekingHelp}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.circumstancesOfDetection" text="CPlace"/>:</td>
<td><select name="circumstancesOfDetection">
<option value=""></option>
<c:forEach var="p" items="${circumstances}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.circumstancesOfDetection == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.methodOfDetection" text="mDet"/>:</td>
<td><select name="methodOfDetection">
<option value=""></option>
<c:forEach var="p" items="${methods}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.methodOfDetection == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.siteOfDisease" text="Anatomical Type"/>:</td>
<td>${form89.anatomicalSite.displayString}</td>
</tr>

<c:if test="${form89.isPulmonary eq false}">
<tr>
<td><spring:message code="mdrtb.form89.epSite" text="EP Site"/>:</td>
<td><select name="epSite">
<option value=""></option>
<c:forEach var="p" items="${epsites}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.epSite == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>
</c:if>

<c:if test="${form89.isPulmonary eq true}">
<tr>
<td><spring:message code="mdrtb.form89.pSite" text="P Site"/>:</td>
<td><select name="pulSite">
<option value=""></option>
<c:forEach var="p" items="${psites}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.pulSite == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>
</c:if>

<c:if test="${form89.isPulmonary eq false}">
<tr>
<td><spring:message code="mdrtb.form89.epLocation" text="EP Location"/>:</td>
<td><select name="epLocation">
<option value=""></option>
<c:forEach var="p" items="${eplocations}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.epLocation == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>
</c:if>

<tr>
<td><spring:message code="mdrtb.form89.presenceOfDecay" text="Decay"/>:</td>
<td>
<select name="presenceOfDecay">
<option value=""></option>
<c:forEach var="p" items="${presences}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.presenceOfDecay == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.dateOfDecaySurvey" text="dateOfDecaySurvey"/>:</td>
<td><openmrs_tag:dateField formFieldName="dateOfDecaySurvey" startValue="${form89.dateOfDecaySurvey}"/></td>
</tr>

</table>

<spring:message code="mdrtb.form89.comorbidities" text="Comorb"/>
<table>

<tr>
<td><spring:message code="mdrtb.form89.diabetes" text="diabetes"/>:</td>
<td><select name="diabetes">
<option value=""></option>
<c:forEach var="p" items="${diabetesOptions}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.diabetes == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.cnsdl" text="cnsdl"/>:</td>
<td><select name="cnsdl">
<option value=""></option>
<c:forEach var="p" items="${cnsdlOptions}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.cnsdl == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.htHeartDisease" text="htHeartDisease"/>:</td>
<td><select name="htHeartDisease">
<option value=""></option>
<c:forEach var="p" items="${htHeartDiseaseOptions}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.htHeartDisease == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.ulcer" text="ulcer"/>:</td>
<td><select name="ulcer">
<option value=""></option>
<c:forEach var="p" items="${ulcerOptions}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.ulcer == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.mentalDisorder" text="mentalDisorder"/>:</td>
<td><select name="mentalDisorder">
<option value=""></option>
<c:forEach var="p" items="${mentalDisorderOptions}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.mentalDisorder == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>



<tr>
<td><spring:message code="mdrtb.form89.ibc20" text="ibc20"/>:</td>
<td>${form89.ibc20.displayString}</td>
<%-- <td><select name="ibc20">
<option value=""></option>
<c:forEach var="p" items="${ibc20Options}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.ibc20 == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td> --%>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.cancer" text="cancer"/>:</td>
<td><select name="cancer">
<option value=""></option>
<c:forEach var="p" items="${cancerOptions}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.cancer == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<%-- <tr>
<td><spring:message code="mdrtb.form89.noDisease" text="noDisease"/>:</td>
<td><select name="noDisease">
<option value=""></option>
<c:forEach var="p" items="${noDiseaseOptions}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.noDisease == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr> --%>

<tr>
<td><spring:message code="mdrtb.form89.otherDisease" text="otherDisease"/>:</td>
<td><input name="otherDisease" size="8" value="${form89.otherDisease}"/></td>
</tr>

</table>



<table>

<tr>
<td><spring:message code="mdrtb.form89.cmacDate" text="CMAC Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="cmacDate" startValue="${form89.cmacDate}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.cmacNumber" text="CMAC Number"/>:</td>
<td><input name="cmacNumber" size="8" value="${form89.cmacNumber}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.prescribedTreatment" text="prescribedTreatment"/>:</td>
<td><select name="prescribedTreatment">
<option value=""></option>
<c:forEach var="p" items="${gptOptions}">
	<option value="${p.answerConcept.id}" <c:if test="${form89.prescribedTreatment == p.answerConcept}">selected</c:if> >${p.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.nameOfDoctor" text="DocName"/>:</td>
<td><input name="nameOfDoctor" size="20" value="${form89.nameOfDoctor}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.form89Date" text="form89Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="form89Date" startValue="${form89.form89Date}"/></td>
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