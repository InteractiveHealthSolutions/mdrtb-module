<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%> 
<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="/moduleResources/mdrtb/mdrtb.css"/>

<!-- TODO: clean up above paths so they use dynamic reference -->
<!-- TODO: add privileges? -->

<!-- SPECIALIZED STYLES FOR THIS PAGE -->

<!-- CUSTOM JQUERY  -->
<c:set var="defaultReturnUrl" value="${pageContext.request.contextPath}/module/mdrtb/dashboard/tbdashboard.form?patientProgramId=${patientProgramId}&patientId=${tb03.patient.id}"/>
<script type="text/javascript"><!--

	var $j = jQuery.noConflict();	

	$j(document).ready(function(){

		$j('#edit').click(function(){
			$j('#viewVisit').hide();
			$j('#editVisit').show();
			hivToggle();
			codToggle()
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
		
		if(${mode eq 'edit'}) {
			$j('#viewVisit').hide();
			$j('#editVisit').show();
		}
		
		
		
		$('#oblast').val(${oblastSelected});
		$('#district').val(${districtSelected});
		$('#facility').val(${facilitySelected});
		$('#oblast').prop('disabled',true);
		$('#district').prop('disabled',true);
		$('#facility').prop('disabled',true);
		$('#encounterDatetime').prop('disabled',true);
		
		
	});
	
	function hivToggle () {
		
		var statusBox = document.getElementById('hivStatus');
		var choice  = statusBox.options[statusBox.selectedIndex].value;
		showHideHivDates(choice);
	}
	
	function showHideHivDates(val) {
		
		var e = document.getElementById('hivDateDiv');
       	
       	if(val==48) {
       		
       		document.getElementById('artStartDate').disabled = false;
       		document.getElementById('pctStartDate').disabled = false;
       		
       	}
       	else {
       		    $j('#artStartDate').val("");
       		 	$j('#pctStartDate').val("");
       		 	document.getElementById('artStartDate').disabled = true;
        		document.getElementById('pctStartDate').disabled = true;
       	      
       	   		//set values of dates to ""
       	}
     }
	
	function codToggle () {
		
		var statusBox = document.getElementById('causeOfDeath');
		var choice  = statusBox.options[statusBox.selectedIndex].value;
		showHideOtherCod(choice);
	}
	
	function showHideOtherCod(val) {
		
		
       	
       	if(val==291) {
       		
       		document.getElementById('otherCauseOfDeath').disabled = false;
       		
       	}
       	else {
       		    $j('#otherCauseOfDeath').val("");
       		 	
       		 	document.getElementById('otherCauseOfDeath').disabled = true;
       	      
       	   		//set values of dates to ""
       	}
     }
	
	function enable() {
		
			
			$('#oblast').prop('disabled',false);
			$('#district').prop('disabled',false);
			$('#facility').prop('disabled',false);
			$('#encounterDatetime').prop('disabled',false);
	
		
	}
    
	var tableToExcel = (function() {
		  var uri = 'data:application/vnd.ms-excel;base64,'
		    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>TB03</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta http-equiv="content-type" content="text/plain; charset=UTF-8"/></head><body><table>{table}</table></body></html>'
		    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
		    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
		  return function(table, name) {
		    if (!table.nodeType) table = document.getElementById(table)
		    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
		    window.location.href = uri + base64(format(template, ctx))
		  }
		})()
		
	function printForm() {
		var mywindow = window.open('', 'PRINT', 'height=400,width=600');

	    mywindow.document.write('<html><head><title><spring:message code="mdrtb.tb03Form" text="TB03"/></title>');
	    mywindow.document.write('</head><body >');
	    mywindow.document.write('<h1><spring:message code="mdrtb.tb03Form" text="TB03"/></h1>');
	    mywindow.document.write(document.getElementById("tb03").innerHTML);
	    
	    mywindow.document.write('</body></html>');

	    mywindow.document.close(); // necessary for IE >= 10
	    mywindow.focus(); // necessary for IE >= 10*/

	    mywindow.print();
	    mywindow.close();

	    return true;
	}
	
	function fun1()
	{
		var e = document.getElementById("oblast");
		var val = e.options[e.selectedIndex].value;
		
		if(val!="")
			window.location.replace("${pageContext.request.contextPath}/module/mdrtb/form/tb03.form?mode=edit&ob="+val+"&patientProgramId="+${patientProgramId}+"&encounterId=" + ${!empty tb03.id ? tb03.id : -1})
	}

	function fun2()
	{
		var e = document.getElementById("oblast");
		var val1 = e.options[e.selectedIndex].value;
		var e = document.getElementById("district");
		var val2 = e.options[e.selectedIndex].value;
		
		if(val2!="")
			window.location.replace("${pageContext.request.contextPath}/module/mdrtb/form/tb03.form?mode=edit&loc="+val2+"&ob="+val1+"&patientProgramId="+${patientProgramId}+"&encounterId=" + ${!empty tb03.id ? tb03.id : -1})
	}

-->

</script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</script>
<br/>

<div> <!-- start of page div -->

&nbsp;&nbsp;<a href="${!empty returnUrl ? returnUrl : defaultReturnUrl}"><spring:message code="mdrtb.back" text="Back"/></a>
<br/><br/>

<!-- VIEW BOX -->
<div id="viewVisit" <c:if test="${(empty tb03.id) || (tb03.id == -1) || fn:length(errors.allErrors) > 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.tb03" text="TB03 Form"/>
<span style="position: absolute; right:30px;"><a id="print" onmouseover="document.body.style.cursor='pointer'" onmouseout="document.body.style.cursor='default'" onclick="printForm()"><spring:message code="mdrtb.print" text="TB03"/></a>
&nbsp;&nbsp;<a id="export" onmouseover="document.body.style.cursor='pointer'" onmouseout="document.body.style.cursor='default'" onclick="tableToExcel('tb03', 'TB03')"><spring:message code="mdrtb.exportToExcel" text="TB03"/></a>
<openmrs:hasPrivilege privilege="Edit DOTS-MDR Data">
&nbsp;&nbsp;<a id="edit" onmouseover="document.body.style.cursor='pointer'" onmouseout="document.body.style.cursor='default'"><spring:message code="mdrtb.edit" text="edit"/></a>
&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/module/mdrtb/visits/delete.form?visitId=${tb03.id}&patientProgramId=${patientProgramId}" class="delete" onclick="return confirm('<spring:message code="mdrtb.confirmDeleteVisit" text="Are you sure you want to delete this visit?"/>')"><spring:message code="mdrtb.delete" text="delete"/></a>
</openmrs:hasPrivilege>
</span>

</b>
<div id="tb03" class="box">
<table>
<tr><td>
<table>
 
<tr>
<td><spring:message code="mdrtb.registrationDate" text="Date"/>:</td>
<td><openmrs:formatDate date="${tb03.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
</tr>

<%-- <tr>
<td><spring:message code="mdrtb.provider" text="Provider"/>:</td>
<td>${tb03.provider.personName}</td>
</tr> --%>
 
<%-- <tr>
<td><spring:message code="mdrtb.location" text="Location"/>:</td>
<td>${tb03.location.displayString}</td>
</tr> --%>

<tr>
<td><spring:message code="mdrtb.oblast" text="Oblast"/>:</td>
<td>${tb03.location.stateProvince}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.district" text="District"/>:</td>
<td>${tb03.location.countyDistrict}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.facility" text="District"/>:</td>
<td>${tb03.location.region}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.name" text="Name"/>:</td>
<td>${tb03.patientName}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.tb03RegistrationNumber" text="TB03 Reg Num"/>:</td>
<td>${tbProgram.patientIdentifier.identifier }</td>
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
<td><spring:message code="mdrtb.tb03.nameOfIPFacility" text="IP_FAC_NAME"/>:</td>
<td>${tb03.nameOfIPFacility}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.treatmentSiteCP" text="Tx Site CP"/>:</td>
<td>${tb03.treatmentSiteCP.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.nameOfCPFacility" text="CP_FAC_NAME"/>:</td>
<td>${tb03.nameOfCPFacility}</td>
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
<td><spring:message code="mdrtb.previousDrugClassification" text="Registration Group By Drug"/>:</td>
<td>${tb03.registrationGroupByDrug.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.hivStatus" text="HIV Status"/>:</td>
<td>${tb03.hivStatus.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.hivTestDate" text="hivTestDate"/>:</td>
<td><openmrs:formatDate date="${tb03.hivTestDate}" format="${_dateFormatDisplay}"/></td>
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
<td><spring:message code="mdrtb.tb03.xrayDate" text="X-raydate"/>:</td>
<td><openmrs:formatDate date="${tb03.xrayDate}" format="${_dateFormatDisplay}"/></td>
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
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.specimenId"/></td>
</tr>
<c:forEach var="smear" items="${tb03.smears}">
<tr>
<td>${smear.monthOfTreatment }</td>
<td>${smear.smearResult.displayString }</td>
<td><openmrs:formatDate date="${smear.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
<td>${smear.location.displayString}
<td>${smear.specimenId}
</c:forEach>
</tr>
</table>

<br/>

<spring:message code="mdrtb.xperts" text="Xpertz"/>
<table border="1">
<tr>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.monthOfTreatment"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.result"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.dateCollected"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.lab"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.specimenId"/></td>
</tr>

<c:forEach var="xpert" items="${tb03.xperts}">
<tr>
<td>${xpert.monthOfTreatment }</td>
<td>${xpert.mtbResult.displayString }/RIF: ${xpert.rifResult.displayString }</td>
<td><openmrs:formatDate date="${xpert.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
<td>${xpert.location.displayString}
<td>${xpert.specimenId}
</c:forEach>
</tr>
</table>

<br/>

<spring:message code="mdrtb.hains" text="Hainz"/>
<table border="1">
<tr>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.monthOfTreatment"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.result"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.dateCollected"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.lab"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.specimenId"/></td>
</tr>

<c:forEach var="hain" items="${tb03.hains}">
<tr>
<td>${hain.monthOfTreatment }</td>
<td>${hain.mtbResult.displayString }/RIF: ${hain.rifResult.displayString }/ INH: ${hain.inhResult.displayString }</td>
<td><openmrs:formatDate date="${hain.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
<td>${hain.location.displayString}
<td>${hain.specimenId}
</c:forEach>
</tr>
</table>

<br/>

<spring:message code="mdrtb.hain2s" text="Hainz"/>
<table border="1">
<tr>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.monthOfTreatment"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.result"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.dateCollected"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.lab"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.specimenId"/></td>
</tr>

<c:forEach var="hain2" items="${tb03.hain2s}">
<tr>
<td>${hain2.monthOfTreatment }</td>
<td>${hain2.mtbResult.displayString }/FQ: ${hain2.fqResult.displayString }/ INJ: ${hain2.injResult.displayString }</td>
<td><openmrs:formatDate date="${hain2.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
<td>${hain2.location.displayString}
<td>${hain2.specimenId}
</c:forEach>
</tr>
</table>

<br/>

<spring:message code="mdrtb.cultures" text="Culturez"/>
<table border="1">
<tr>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.monthOfTreatment"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.result"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.dateCollected"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.lab"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.specimenId"/></td>
</tr>

<c:forEach var="culture" items="${tb03.cultures}">
<tr>
<td>${culture.monthOfTreatment }</td>
<td>${culture.cultureResult.displayString }</td>
<td><openmrs:formatDate date="${culture.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
<td>${culture.location.displayString}
<td>${culture.specimenId}
</c:forEach>
</tr>
</table>

<br/>

<spring:message code="mdrtb.dsts" text="DSTz"/>
<table border="1">
<tr>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.monthOfTreatment"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.result"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.dateCollected"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.lab"/></td>
<td style="font-weight:bold"><nobr><spring:message code="mdrtb.specimenId"/></td>
</tr>

<c:forEach var="dst" items="${tb03.dsts}">
<tr>
<td>${dst.monthOfTreatment }</td>
<td>${dst.di.resultsString }</td>
<td><openmrs:formatDate date="${dst.encounter.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
<td>${dst.encounter.location.displayString}
<td>${dst.specimenId}
</c:forEach>
</tr>
</table>

<table>
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
<td><spring:message code="mdrtb.causeOfDeath" text="Cause of Death"/>:</td>
<td>${tb03.causeOfDeath.displayString}</td>
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
</td></tr>
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

<form name="tb03Form" name="tb03Form" action="tb03.form?patientId=${patientId}&patientProgramId=${patientProgramId}&encounterId=${!empty tb03.id ? tb03.id : -1}" method="post" onSubmit="enable()">
<input type="hidden" name="returnUrl" value="${returnUrl}" />
<input type="hidden" name="patProgId" value="${patientProgramId}" />
<input type="hidden" name="provider" value="47" />

<table>
 
<tr>
<td><spring:message code="mdrtb.registrationDate" text="Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="encounterDatetime" startValue="${tb03.encounterDatetime}"/></td>
</tr>

<%-- <tr>
<td><spring:message code="mdrtb.provider" text="Provider"/>:</td>
<td>
<select name="provider">
<option value=""></option>
<c:forEach var="provider" items="${providers}">
	<option value="${provider.id}" <c:if test="${tb03.provider == provider}">selected</c:if>>${provider.personName}</option>
</c:forEach>
</select>
</td>
</tr> --%>
 
<%-- <tr>
<td><spring:message code="mdrtb.location" text="Location"/>:</td>
<td>
<select name="location">
<option value=""></option>
<c:forEach var="location" items="${locations}">
	<option value="${location.id}" <c:if test="${tb03.location == location}">selected</c:if>>${location.displayString}</option>
</c:forEach>
</select>
</td>
</tr> --%>
</table>

<table>
<tr id="oblastDiv">
			<td align="right"><spring:message code="mdrtb.oblast" /></td>
			<td><select name="oblast" id="oblast" onchange="fun1()" >
					<option value=""></option>
					<c:forEach var="o" items="${oblasts}">
						<option value="${o.id}">${o.name}</option>
					</c:forEach>
			</select></td>
		</tr>
		
		<tr id="districtDiv">
			<td align="right"><spring:message code="mdrtb.district" /></td>
			<td><select name="district" id="district" onchange="fun2()" >
					<option value=""></option>
					<c:forEach var="dist" items="${districts}">
						<option value="${dist.id}">${dist.name}</option>
					</c:forEach>
			</select></td>
		</tr>
		
		<tr id="facilityDiv">
			<td align="right"><spring:message code="mdrtb.facility" /></td>
			<td><select name="facility" id="facility" >
					<option value=""></option>
					<c:forEach var="f" items="${facilities}">
						<option value="${f.id}">${f.name}</option>
					</c:forEach>
			</select>
			</td>
		</tr>
	</table>
	
<table>

<tr>
<td><spring:message code="mdrtb.tb03.name" text="Name"/>:</td>
<td>${tb03.patientName}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.tb03RegistrationNumber" text="TB03 Reg Num"/>:</td>
<td>${tbProgram.patientIdentifier.identifier }</td>
</tr>
 
<tr>
<td><spring:message code="mdrtb.gender" text="Gender"/>:</td>
<td>${tb03.gender}</td>
</tr>
 
<tr>
<td valign="top"><spring:message code="mdrtb.tb03.ageAtRegistration" text="Age at Regisration"/>:</td>
<%-- <td><input name="ageAtTB03Registration" size="8" value="${tb03.ageAtTB03Registration}"/></td> --%>
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
<td><spring:message code="mdrtb.tb03.nameOfIPFacility" text="IP_FAC_NAME"/>:</td>
<td><input name="nameOfIPFacility" size="15" value="${tb03.nameOfIPFacility}"/></td>
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
<td><spring:message code="mdrtb.tb03.nameOfCPFacility" text="CP_FAC_NAME"/>:</td>
<td><input name="nameOfCPFacility" size="15" value="${tb03.nameOfCPFacility}"/></td>
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
<td>${tb03.registrationGroup.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.previousDrugClassification" text="Registration Group By Drug"/>:</td>
<td>${tb03.registrationGroupByDrug.displayString}</td>
</tr>

<%-- <tr>
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
<td><spring:message code="mdrtb.previousDrugClassification" text="Registration Group By Drug"/>:</td>
<td>
<select name="registrationGroupByDrug">
<option value=""></option>
<c:forEach var="group" items="${bydrug}">
	<option value="${group.concept.id}" <c:if test="${tb03.registrationGroupByDrug == group.concept}">selected</c:if> >${group.concept.displayString}</option>
</c:forEach>
</select>
</td>
</tr> --%>

<tr>
<td><spring:message code="mdrtb.tb03.hivTestDate" text="HIV Test Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="hivTestDate" startValue="${tb03.hivTestDate}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.hivStatus" text="HIV Status" />:</td>
<td>
<select name="hivStatus" id="hivStatus"  onChange="hivToggle()">
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
<td><spring:message code="mdrtb.tb03.xrayDate" text="X-ray Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="xrayDate" startValue="${tb03.xrayDate}"/></td>
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
<td> <openmrs_tag:dateField  formFieldName="treatmentOutcomeDate" startValue="${tb03.treatmentOutcomeDate}"/></td>
<td>&nbsp;</td>
</tr>

<tr>
<td><spring:message code="mdrtb.causeOfDeath" text="Cause of Death"/>:</td>
<td>
<select name="causeOfDeath" id="causeOfDeath" onChange="codToggle()">
<option value=""></option>
<c:forEach var="type" items="${causes}">
	<option value="${type.answerConcept.id}" <c:if test="${tb03.causeOfDeath == type.answerConcept}">selected</c:if> >${type.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.otherCauseOfDeath" text="Other Cause of Death"/>:</td>
<td><input name="otherCauseOfDeath" id="otherCauseOfDeath" size="15" value="${tb03.otherCauseOfDeath}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.afterOutcomeDeathDate" text="Date of Death after Outcome"/>:</td>
<td><openmrs_tag:dateField formFieldName="dateOfDeathAfterOutcome" startValue="${tb03.dateOfDeathAfterOutcome}"/></td>
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
</div>

</div> <!-- end of page div -->

<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbFooter.jsp"%>