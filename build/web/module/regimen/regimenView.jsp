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

<a href="${pageContext.request.contextPath}/module/mdrtb/form/regimen.form?encounterId=-1&patientId=${patientId}&patientProgramId=${patientProgramId}"><spring:message code="mdrtb.pv.addRegimen" text="newz"/></a>

<table border="1" width="100%">
<tr>
		<th><spring:message code="mdrtb.pv.serialNumber" text="serialz"/></th>
		<th><spring:message code="mdrtb.pv.cmacDate" text="datez"/></th>
		<th><spring:message code="mdrtb.form89.cmacNumber" text="numberz"/></th>
		<th><spring:message code="mdrtb.form89.placeOfCommission" text="placez"/></th>
		<th><spring:message code="mdrtb.tb03.resistanceType" text="typez"/></th>
		<th><spring:message code="mdrtb.pv.sldRegimenType" text="typez"/></th>
		<th><spring:message code="mdrtb.pv.cmDose" text="cmDose"/></th>
		<th><spring:message code="mdrtb.pv.amDose" text="amDose"/></th>
		<th><spring:message code="mdrtb.pv.mfxDose" text="mfxDose"/></th>
		<th><spring:message code="mdrtb.pv.lfxDose" text="lfxDose"/></th>
		<th><spring:message code="mdrtb.pv.ptoDose" text="ptoDose"/></th>
		<th><spring:message code="mdrtb.pv.csDose" text="csDose"/></th>
		<th><spring:message code="mdrtb.pv.pasDose" text="pasDose"/></th>
		<th><spring:message code="mdrtb.pv.zDose" text="zDose"/></th>
		<th><spring:message code="mdrtb.pv.eDose" text="eDose"/></th>
		<th><spring:message code="mdrtb.pv.hDose" text="hDose"/></th>
		<th><spring:message code="mdrtb.pv.lzdDose" text="lzdDose"/></th>
		<th><spring:message code="mdrtb.pv.cfzDose" text="cfzDose"/></th>
		<th><spring:message code="mdrtb.pv.bdqDose" text="bdqDose"/></th>
		<th><spring:message code="mdrtb.pv.dlmDose" text="dlmDose"/></th>
		<th><spring:message code="mdrtb.pv.impDose" text="impDose"/></th>
		<th><spring:message code="mdrtb.pv.amxDose" text="amxDose"/></th>
		<th><spring:message code="mdrtb.pv.hrDose" text="hrDose"/></th>
		<th><spring:message code="mdrtb.pv.hrzeDose" text="hrzeDose"/></th>
		<th><spring:message code="mdrtb.pv.sDose" text="sDose"/></th>
		<th><spring:message code="mdrtb.pv.otherDrug1Dose" text="otherDrug1Dose"/></th>
		<!-- <th><spring:message code="mdrtb.pv.otherDrug2Dose" text="otherDrug2Dose"/></th> -->
		<th><spring:message code="mdrtb.pv.fundingSource" text="fundingz"/></th>
		
</tr>

<c:forEach var="form" items="${forms}" varStatus="loop">
 <tr>
 <td><a href="${pageContext.request.contextPath}//module/mdrtb/form/regimen.form?encounterId=${form.id}&patientProgramId=${patientProgramId}" target="_blank">${loop.index+1}</a></td>
 <td><openmrs:formatDate date="${form.councilDate}" format="${_dateFormatDisplay}"/></td>
 <td>${form.cmacNumber}</td>
 <td>${form.placeOfCommission.displayString}</td>
 <td>${form.resistanceType.displayString}</td>
 <td>${form.sldRegimenType.displayString}</td>
 <td>${form.cmDose}</td>
 <td>${form.amDose}</td>
 <td>${form.mfxDose}</td>
 <td>${form.lfxDose}</td>
 <td>${form.ptoDose}</td>
 <td>${form.csDose}</td>
 <td>${form.pasDose}</td>
 <td>${form.ZDose}</td>
 <td>${form.EDose}</td>
 <td>${form.HDose}</td>
 <td>${form.lzdDose}</td>
 <td>${form.cfzDose}</td>
 <td>${form.bdqDose}</td>
 <td>${form.dlmDose}</td>
 <td>${form.impDose}</td>
 <td>${form.amxDose}</td>
 <td>${form.hrDose}</td>
 <td>${form.hrzeDose}</td>
 <td>${form.SDose}</td>
 <td>${form.otherDrug1Name}&nbsp;&nbsp;${form.otherDrug1Dose}</td>
 <td>${form.fundingSource.displayString}</td>
 </tr>
  
</c:forEach>

</table>


</div> <!-- end of page div -->

<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbFooter.jsp"%>