<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%> 
<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="/moduleResources/mdrtb/mdrtb.css"/>

<!-- TODO: clean up above paths so they use dynamic reference -->
<!-- TODO: add privileges? -->

<!-- SPECIALIZED STYLES FOR THIS PAGE -->

<!-- CUSTOM JQUERY  -->
<c:set var="defaultReturnUrl" value="${pageContext.request.contextPath}/module/mdrtb/dashboard/dashboard.form?patientProgramId=${patientProgramId}&patientId=${tb03.patient.id}"/>
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
		
		if(${mode eq 'edit'}) {
			$j('#viewVisit').hide();
			$j('#editVisit').show();
		}
		
		
		
		$('#oblast').val(${oblastSelected});
		$('#district').val(${districtSelected});
		$('#facility').val(${facilitySelected});
		
		
		
		
	});
	
	
	
    
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

	    mywindow.document.write('<html><head><title><spring:message code="mdrtb.pv.aeForm" text="AE"/></title>');
	    mywindow.document.write('</head><body >');
	    mywindow.document.write('<h1><spring:message code="mdrtb.pv.aeForm" text="AE"/></h1>');
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
			window.location.replace("${pageContext.request.contextPath}/module/mdrtb/form/ae.form?mode=edit&ob="+val+"&patientProgramId="+${patientProgramId}+"&encounterId=" + ${!empty aeForm.id ? aeForm.id : -1})
	}

	function fun2()
	{
		var e = document.getElementById("oblast");
		var val1 = e.options[e.selectedIndex].value;
		var e = document.getElementById("district");
		var val2 = e.options[e.selectedIndex].value;
		
		if(val2!="")
			window.location.replace("${pageContext.request.contextPath}/module/mdrtb/form/ae.form?mode=edit&loc="+val2+"&ob="+val1+"&patientProgramId="+${patientProgramId}+"&encounterId=" + ${!empty aeForm.id ? aeForm.id : -1})
	}
	
	function validate() 
	{
		var encDate = document.getElementById("encounterDatetime").value;
		var errorText = "";
		if(encDate=="") {
			errorText = ""  + '<spring:message code="mdrtb.pv.missingOnsetDate"/>' + "";
			alert(errorText);
			return false;
		}
		
		if(document.getElementById("adverseEvent").value=="") {
			
			
			errorText = ""  + '<spring:message code="mdrtb.pv.missingAdverseEvent"/>' + "";
			alert(errorText);
			return false;
		}
		
		encDate = encDate.replace(/\//g,".");
		
		
		var parts = encDate.split(".");
		var day = parts[0];
		var month = parts[1]-1;
		var year = parts[2];
		
		
		
		var onsetDate = new Date(year,month,day);

		var now = new Date();
		
		if(onsetDate.getTime() > now.getTime()) {
			alert("Onset date cannot be in future");
			return false;
		}
		
		if(document.getElementById("diagnosticInvestigation").value=="") {
			
			alert("Please enter the Diagnostic Investigation");
			return false;
		}
		
		if(document.getElementById("suspectedDrug").value=="") {
			
			alert("Please enter the Suspected Drug");
			return false;
		}
		
		if(document.getElementById("typeOfEvent").value=="") {
			
			alert("Please enter the Type of Event");
			return false;
		}
		
		var ycDateString = document.getElementById("yellowCardDate").value;
		
		if(ycDateString!="") {
			ycDateString = ycDateString.replace(/\//g,".");
			parts = ycDateString.split(".");
			day = parts[0];
			month = parts[1]-1;
			year = parts[2];
			
			var ycDate = new Date(year, month, day);

			if(ycDate.getTime() < onsetDate.getTime()) {
				alert("Yellow Card Submission Date cannot be before Onset Date");
				return false;
			}
		}
		
		if(document.getElementById("causalityDrug1").value!="" && document.getElementById("causalityAssessmentResult1").value=="") {
			
			alert("Please enter the Causality Assessment Result for Drug 1");
			return false;
		}
		
		if(document.getElementById("causalityDrug2").value!="" && document.getElementById("causalityAssessmentResult2").value=="") {
			
			alert("Please enter the Causality Assessment Result for Drug 2");
			return false;
		}
		
		if(document.getElementById("causalityDrug3").value!="" && document.getElementById("causalityAssessmentResult3").value=="") {
			
			alert("Please enter the Causality Assessment Result for Drug 3");
			return false;
		}
		
		if(document.getElementById("actionTaken").value=="") {
			
			alert("Please enter the Action Taken");
			return false;
		}

		var outcomeDateString = document.getElementById("outcomeDate").value;
		
		if(document.getElementById("actionOutcome").value!="" && outcomeDateString=="") {
			
			alert("Please enter the Outcome Date");
			return false;
		}
		
		if(outcomeDateString != "") {
			
			if(document.getElementById("actionOutcome").value=="") {
				
				alert("Please enter the Outcome");
				return false;
			}
			
			
			outcomeDateString = outcomeDateString.replace(/\//g,".");
			parts = outcomeDateString.split(".");
			day = parts[0];
			month = parts[1]-1;
			year = parts[2];
		
			var outcomeDate = new Date(year, month, day);
			if(outcomeDate.getTime() < onsetDate.getTime()) {
				alert("Outcome date cannot be before Onset Date");
				return false;
			}
		}
		
		if(document.getElementById("meddraCode").value=="") {
			
			alert("Please enter the MedDRA code");
			return false;
		}
		
		return true;
	}

-->

</script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js">
</script>

<br/>

<div> <!-- start of page div -->

&nbsp;&nbsp;<a href="${!empty returnUrl ? returnUrl : defaultReturnUrl}"><spring:message code="mdrtb.back" text="Backu"/></a>
<br/><br/>

<!-- VIEW BOX -->
<div id="viewVisit" <c:if test="${(empty aeForm.id) || (aeForm.id == -1) || fn:length(errors.allErrors) > 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.pv.aeForm" text="AE Formz"/>
<span style="position: absolute; right:30px;"><a id="print" onmouseover="document.body.style.cursor='pointer'" onmouseout="document.body.style.cursor='default'" onclick="printForm()"><spring:message code="mdrtb.print" text="AE"/></a>
&nbsp;&nbsp;<a id="export" onmouseover="document.body.style.cursor='pointer'" onmouseout="document.body.style.cursor='default'" onclick="tableToExcel('ae', 'AE')"><spring:message code="mdrtb.exportToExcel" text="Export"/></a>
<openmrs:hasPrivilege privilege="Edit DOTS-MDR Data">
&nbsp;&nbsp;<a id="edit" onmouseover="document.body.style.cursor='pointer'" onmouseout="document.body.style.cursor='default'"><spring:message code="mdrtb.edit" text="edit"/></a>
&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/module/mdrtb/visits/delete.form?visitId=${aeForm.id}&patientProgramId=${patientProgramId}&patientId=${aeForm.patient.id }" class="delete" onclick="return confirm('<spring:message code="mdrtb.confirmDeleteVisit" text="Are you sure you want to delete this visit?"/>')"><spring:message code="mdrtb.delete" text="delete"/></a>
</openmrs:hasPrivilege>
</span>

</b>
<div id="ae" class="box">
<table>
<tr><td>
<table>
 
<tr>
<td><spring:message code="mdrtb.pv.onsetDate" text="Date"/>:</td>
<td><openmrs:formatDate date="${aeForm.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
</tr>


<tr>
<td><spring:message code="mdrtb.oblast" text="Oblast"/>:</td>
<td>${aeForm.location.stateProvince}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.district" text="District"/>:</td>
<td>${aeForm.location.countyDistrict}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.facility" text="District"/>:</td>
<td>${aeForm.location.region}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.adverseEvent" text="AEz"/>:</td>
<td>${aeForm.adverseEvent.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.diagnosticInvestigation" text="AEz"/>:</td>
<td>${aeForm.diagnosticInvestigation.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.suspectedDrug" text="Drugz"/>:</td>
<td>${aeForm.suspectedDrug}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.treatmentRegimenAtOnset" text="regz"/>:</td>
<td>${aeForm.treatmentRegimenAtOnset}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.typeOfEvent" text="Eventz"/>:</td>
<td>${aeForm.typeOfEvent.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.yellowCardDate" text="Yellowz"/>:</td>
<td><openmrs:formatDate date="${aeForm.yellowCardDate}" format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.causalityDrug1" text="CD1z"/>:</td>
<td>${aeForm.causalityDrug1.displayString}</td>
<td><spring:message code="mdrtb.pv.causalityAssessmentResult1" text="CAR1z"/>:</td>
<td>${aeForm.causalityAssessmentResult1.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.causalityDrug2" text="CD2z"/>:</td>
<td>${aeForm.causalityDrug2.displayString}</td>
<td><spring:message code="mdrtb.pv.causalityAssessmentResult2" text="CAR2z"/>:</td>
<td>${aeForm.causalityAssessmentResult2.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.causalityDrug3" text="CD3z"/>:</td>
<td>${aeForm.causalityDrug3.displayString}</td>
<td><spring:message code="mdrtb.pv.causalityAssessmentResult3" text="CAR3z"/>:</td>
<td>${aeForm.causalityAssessmentResult3.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.actionTaken" text="Actionz"/>:</td>
<td>${aeForm.actionTaken.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.actionOutcome" text="Outcomez"/>:</td>
<td>${aeForm.actionOutcome.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.outcomeDate" text="OutcmeDatez"/>:</td>
<td><openmrs:formatDate date="${aeForm.outcomeDate}" format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.meddraCode" text="MeddraCodez"/>:</td>
<td>${aeForm.meddraCode.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.drugRechallenge" text="RCz"/>:</td>
<td>${aeForm.drugRechallenge.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.comments" text="commentz"/>:</td>
<td>${aeForm.comments}</td>
</tr>




</table>
</td></tr>
</table>
</div>
</div>
<!-- END VIEW BOX -->

<!-- EDIT BOX -->
<div id="editVisit" <c:if test="${(!empty aeForm.id) && (aeForm.id != -1) && fn:length(errors.allErrors) == 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.pv.aeForm" text="aeForm"/></b>
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

<form name="aeForm" name="aeForm" action="ae.form?patientId=${patientId}&patientProgramId=${patientProgramId}&encounterId=${!empty aeForm.id ? aeForm.id : -1}" method="post" onSubmit="return validate()">
<input type="hidden" name="returnUrl" value="${returnUrl}" />
<input type="hidden" name="patProgId" value="${patientProgramId}" />
<input type="hidden" name="provider" value="47" />

<table>
 
<tr>
<td><spring:message code="mdrtb.pv.onsetDate" text="Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="encounterDatetime" startValue="${aeForm.encounterDatetime}"/></td>
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
<td><spring:message code="mdrtb.pv.adverseEvent" text="AEz"/>:</td>
<td>
<select name="adverseEvent" id="adverseEvent">
<option value=""></option>
<c:forEach var="aeOption" items="${aeOptions}">
	<option value="${aeOption.answerConcept.id}" <c:if test="${aeForm.adverseEvent == aeOption.answerConcept}">selected</c:if> >${aeOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.diagnosticInvestigation" text="DIz"/>:</td>
<td>
<select name="diagnosticInvestigation" id="diagnosticInvestigation">
<option value=""></option>
<c:forEach var="diOption" items="${diOptions}">
	<option value="${diOption.answerConcept.id}" <c:if test="${aeForm.diagnosticInvestigation == diOption.answerConcept}">selected</c:if> >${diOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.suspectedDrug" text="Drugz"/>:</td>
<td><input name="suspectedDrug" id="suspectedDrug" size="25" value="${aeForm.suspectedDrug}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.treatmentRegimenAtOnset" text="regimenz"/>:</td>
<td>
<select name="treatmentRegimenAtOnset" id="treatmentRegimenAtOnset">
<option value=""></option>
<c:forEach var="regimen" items="${regimens}">
	<option value="${regimen}" <c:if test="${aeForm.treatmentRegimenAtOnset == regimen}">selected</c:if> >${regimen}</option>
</c:forEach>
</select>
</td>
</tr>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.typeOfEvent" text="Eventz"/>:</td>
<td>
<select name="typeOfEvent" id="typeOfEvent">
<option value=""></option>
<c:forEach var="typeOption" items="${typeOptions}">
	<option value="${typeOption.answerConcept.id}" <c:if test="${aeForm.typeOfEvent == typeOption.answerConcept}">selected</c:if> >${typeOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.yellowCardDate" text="Yellowz"/>:</td>
<td><openmrs_tag:dateField formFieldName="yellowCardDate" startValue="${aeForm.yellowCardDate}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.causalityDrug1" text="CD1z"/>:</td>
<td>
<select name="causalityDrug1" id="causalityDrug1">
<option value=""></option>
<c:forEach var="cdOption" items="${cdOptions}">
	<option value="${cdOption.answerConcept.id}" <c:if test="${aeForm.causalityDrug1 == cdOption.answerConcept}">selected</c:if> >${cdOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
<td><spring:message code="mdrtb.pv.causalityAssessmentResult1" text="CAR1z"/>:</td>
<td>
<select name="causalityAssessmentResult1" id="causalityAssessmentResult1">
<option value=""></option>
<c:forEach var="carOption" items="${carOptions}">
	<option value="${carOption.answerConcept.id}" <c:if test="${aeForm.causalityAssessmentResult1 == carOption.answerConcept}">selected</c:if> >${carOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.causalityDrug2" text="CD2z"/>:</td>
<td>
<select name="causalityDrug2" id="causalityDrug2">
<option value=""></option>
<c:forEach var="cdOption" items="${cdOptions}">
	<option value="${cdOption.answerConcept.id}" <c:if test="${aeForm.causalityDrug2 == cdOption.answerConcept}">selected</c:if> >${cdOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
<td><spring:message code="mdrtb.pv.causalityAssessmentResult2" text="CAR2z"/>:</td>
<td>
<select name="causalityAssessmentResult2" id="causalityAssessmentResult2">
<option value=""></option>
<c:forEach var="carOption" items="${carOptions}">
	<option value="${carOption.answerConcept.id}" <c:if test="${aeForm.causalityAssessmentResult2 == carOption.answerConcept}">selected</c:if> >${carOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.causalityDrug3" text="CD3z"/>:</td>
<td>
<select name="causalityDrug3" id="causalityDrug3">
<option value=""></option>
<c:forEach var="cdOption" items="${cdOptions}">
	<option value="${cdOption.answerConcept.id}" <c:if test="${aeForm.causalityDrug3 == cdOption.answerConcept}">selected</c:if> >${cdOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
<td><spring:message code="mdrtb.pv.causalityAssessmentResult3" text="CAR3z"/>:</td>
<td>
<select name="causalityAssessmentResult3" id="causalityAssessmentResult3">
<option value=""></option>
<c:forEach var="carOption" items="${carOptions}">
	<option value="${carOption.answerConcept.id}" <c:if test="${aeForm.causalityAssessmentResult3 == carOption.answerConcept}">selected</c:if> >${carOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.actionTaken" text="Actionz"/>:</td>
<td>
<select name="actionTaken" id="actionTaken">
<option value=""></option>
<c:forEach var="action" items="${actions}">
	<option value="${action.answerConcept.id}" <c:if test="${aeForm.actionTaken == action.answerConcept}">selected</c:if> >${action.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.actionOutcome" text="Actionz"/>:</td>
<td>
<select name="actionOutcome" id="actionOutcome">
<option value=""></option>
<c:forEach var="outcome" items="${outcomes}">
	<option value="${outcome.answerConcept.id}" <c:if test="${aeForm.actionOutcome == outcome.answerConcept}">selected</c:if> >${outcome.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.outcomeDate" text="OutcomeDatez"/>:</td>
<td><openmrs_tag:dateField formFieldName="outcomeDate" startValue="${aeForm.outcomeDate}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.meddraCode" text="medraz"/>:</td>
<td>
<select name="meddraCode" id="meddraCode">
<option value=""></option>
<c:forEach var="code" items="${meddraCodes}">
	<option value="${code.answerConcept.id}" <c:if test="${aeForm.meddraCode == code.answerConcept}">selected</c:if> >${code.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.drugRechallenge" text="RCz"/>:</td>
<td>
<select name="drugRechallenge" id="drugRechallenge">
<option value=""></option>
<c:forEach var="dr" items="${drugRechallenges}">
	<option value="${dr.answerConcept.id}" <c:if test="${aeForm.drugRechallenge == dr.answerConcept}">selected</c:if> >${dr.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>



<tr>
<td><spring:message code="mdrtb.pv.comments" text="commentz"/>:</td>
<td><textarea rows="4" cols="50" name="comments">${aeForm.comments}</textarea></td>
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