<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%> 
<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="/moduleResources/mdrtb/mdrtb.css"/>

<!-- TODO: clean up above paths so they use dynamic reference -->
<!-- TODO: add privileges? -->

<!-- SPECIALIZED STYLES FOR THIS PAGE -->

<!-- CUSTOM JQUERY  -->
<c:set var="defaultReturnUrl" value="${pageContext.request.contextPath}/module/mdrtb/dashboard/dashboard.form?patientProgramId=${patientProgramId}&patientId=${regimenForm.patient.id}"/>
<script type="text/javascript"><!--

	var $j = jQuery.noConflict();	

	$j(document).ready(function(){

		$j('#edit').click(function(){
			$j('#viewVisit').hide();
			$j('#editVisit').show();
			sldToggle();
			
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
		
		
		
		/* $('#oblast').val(${oblastSelected});
		$('#district').val(${districtSelected});
		$('#facility').val(${facilitySelected}); */
		$('#otherRegimen').prop('disabled',true);
		
		
		
	});
	
	function sldToggle () {
		
		var statusBox = document.getElementById('sldRegimenType');
		var choice  = statusBox.options[statusBox.selectedIndex].value;
		showHideOtherRegimen(choice);
	}
	
	function showHideOtherRegimen(val) {
		
		
       	
       	if(val==653) {
       		
       		document.getElementById('otherRegimen').disabled = false;
       		
       	}
       	else {
       		    $j('#otherRegimen').val("");
       		 	
       		 	document.getElementById('otherRegimen').disabled = true;
       	      
       	   		//set values of dates to ""
       	}
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

	    mywindow.document.write('<html><head><title><spring:message code="mdrtb.pv.regimenForm" text="Regimen"/></title>');
	    mywindow.document.write('</head><body >');
	    mywindow.document.write('<h1><spring:message code="mdrtb.pv.regimenForm" text="Regimen"/></h1>');
	    mywindow.document.write(document.getElementById("regimen").innerHTML);
	    
	    mywindow.document.write('</body></html>');

	    mywindow.document.close(); // necessary for IE >= 10
	    mywindow.focus(); // necessary for IE >= 10*/

	    mywindow.print();
	    mywindow.close();

	    return true;
	}
	
	/* function fun1()
	{
		var e = document.getElementById("oblast");
		var val = e.options[e.selectedIndex].value;
		
		if(val!="")
			window.location.replace("${pageContext.request.contextPath}/module/mdrtb/form/regimen.form?mode=edit&ob="+val+"&patientProgramId="+${patientProgramId}+"&encounterId=" + ${!empty regimenForm.id ? regimenForm.id : -1})
	}

	function fun2()
	{
		var e = document.getElementById("oblast");
		var val1 = e.options[e.selectedIndex].value;
		var e = document.getElementById("district");
		var val2 = e.options[e.selectedIndex].value;
		
		if(val2!="")
			window.location.replace("${pageContext.request.contextPath}/module/mdrtb/form/regimen.form?mode=edit&loc="+val2+"&ob="+val1+"&patientProgramId="+${patientProgramId}+"&encounterId=" + ${!empty regimenForm.id ? regimenForm.id : -1})
	} */

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
<div id="viewVisit" <c:if test="${(empty regimenForm.id) || (regimenForm.id == -1) || fn:length(errors.allErrors) > 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.pv.regimenForm" text="Regimen Formz"/>
<span style="position: absolute; right:30px;"><a id="print" onmouseover="document.body.style.cursor='pointer'" onmouseout="document.body.style.cursor='default'" onclick="printForm()"><spring:message code="mdrtb.print" text="Regimen"/></a>
&nbsp;&nbsp;<a id="export" onmouseover="document.body.style.cursor='pointer'" onmouseout="document.body.style.cursor='default'" onclick="tableToExcel('regimen', 'Regimen')"><spring:message code="mdrtb.exportToExcel" text="Export"/></a>
<openmrs:hasPrivilege privilege="Edit DOTS-MDR Data">
&nbsp;&nbsp;<a id="edit" onmouseover="document.body.style.cursor='pointer'" onmouseout="document.body.style.cursor='default'"><spring:message code="mdrtb.edit" text="edit"/></a>
&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/module/mdrtb/visits/delete.form?visitId=${regimenForm.id}&patientProgramId=${patientProgramId}&patientId=${regimenForm.patient.id }" class="delete" onclick="return confirm('<spring:message code="mdrtb.confirmDeleteVisit" text="Are you sure you want to delete this visit?"/>')"><spring:message code="mdrtb.delete" text="delete"/></a>
</openmrs:hasPrivilege>
</span>

</b>
<div id="regimen" class="box">
<table>
<tr><td>
<table>
 
<tr>
<td><spring:message code="mdrtb.date" text="Date"/>:</td>
<td><openmrs:formatDate date="${regimenForm.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
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
<td>${regimenForm.location.stateProvince}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.district" text="District"/>:</td>
<td>${regimenForm.location.countyDistrict}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.facility" text="District"/>:</td>
<td>${regimenForm.location.region}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.cmacDate" text="CMAC date"/>:</td>
<td><openmrs:formatDate date="${regimenForm.councilDate}" format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.cmacNumber" text="CMAC Number"/>:</td>
<td>${regimenForm.cmacNumber}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.placeOfCommission" text="Tx Site CP"/>:</td>
<td>${regimenForm.placeOfCommission.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.resistanceType" text="resTypesz"/>:</td>
<td>${regimenForm.resistanceType}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.fundingSource" text="Fundingz"/>:</td>
<td>${regimenForm.fundingSource.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.sldRegimenType" text="typez"/>:</td>
<td>${regimenForm.sldRegimenType.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.otherRegimen" text="otherz"/>:</td>
<td>${regimenForm.otherRegimen}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.cmDose" text="cmDose"/>:</td>
<td>${regimenForm.cmDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.amDose" text="amDose"/>:</td>
<td>${regimenForm.amDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.mfxDose" text="mfxDose"/>:</td>
<td>${regimenForm.mfxDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.lfxDose" text="lfxDose"/>:</td>
<td>${regimenForm.lfxDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.ptoDose" text="ptoDose"/>:</td>
<td>${regimenForm.ptoDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.csDose" text="csDose"/>:</td>
<td>${regimenForm.csDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.pasDose" text="pasDose"/>:</td>
<td>${regimenForm.pasDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.zDose" text="zDose"/>:</td>
<td>${regimenForm.ZDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.eDose" text="eDose"/>:</td>
<td>${regimenForm.EDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.hDose" text="hDose"/>:</td>
<td>${regimenForm.HDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.lzdDose" text="lzdDose"/>:</td>
<td>${regimenForm.lzdDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.cfzDose" text="cfzDose"/>:</td>
<td>${regimenForm.cfzDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.bdqDose" text="bdqDose"/>:</td>
<td>${regimenForm.bdqDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.dlmDose" text="dlmDose"/>:</td>
<td>${regimenForm.dlmDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.impDose" text="impDose"/>:</td>
<td>${regimenForm.impDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.amxDose" text="amxDose"/>:</td>
<td>${regimenForm.amxDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.hrDose" text="hrDose"/>:</td>
<td>${regimenForm.hrDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.hrzeDose" text="hrzeDose"/>:</td>
<td>${regimenForm.hrzeDose}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.sDose" text="sDose"/>:</td>
<td>${regimenForm.SDose}</td>
</tr>

<tr>
<td colspan="2" align="left"><spring:message code="mdrtb.pv.other" text="Otherz"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.otherDrug1Name" text="otherDrug1Name"/>:</td>
<td>${regimenForm.otherDrug1Name}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.otherDrug1Dose" text="otherDrug1Dose"/>:</td>
<td>${regimenForm.otherDrug1Dose}</td>
</tr>


</table>
</td></tr>
</table>
</div>
</div>
<!-- END VIEW BOX -->

<!-- EDIT BOX -->
<div id="editVisit" <c:if test="${(!empty regimenForm.id) && (regimenForm.id != -1) && fn:length(errors.allErrors) == 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.regimenForm" text="regimenForm"/></b>
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

<form name="regimenForm" name="regimenForm" action="regimen.form?patientId=${patientId}&patientProgramId=${patientProgramId}&encounterId=${!empty regimenForm.id ? regimenForm.id : -1}" method="post">
<input type="hidden" name="returnUrl" value="${returnUrl}" />
<input type="hidden" name="patProgId" value="${patientProgramId}" />
<input type="hidden" name="provider" value="47" />
<input type="hidden" name="location" value="${regimenForm.location.id }"/>

<table>
 
<tr>
<td><spring:message code="mdrtb.date" text="Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="encounterDatetime" startValue="${regimenForm.encounterDatetime}"/></td>
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

<%-- <table>
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
	 --%>
<table>

<tr>
<td><spring:message code="mdrtb.oblast" text="Oblast"/>:</td>
<td>${regimenForm.location.stateProvince}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.district" text="District"/>:</td>
<td>${regimenForm.location.countyDistrict}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.facility" text="District"/>:</td>
<td>${regimenForm.location.region}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.cmacDate" text="CMAC Datez"/>:</td>
<td><openmrs_tag:dateField formFieldName="councilDate" startValue="${regimenForm.councilDate}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.cmacNumber" text="CMAC Numberz"/>:</td>
<td><input name="cmacNumber" size="8" value="${regimenForm.cmacNumber}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.form89.placeOfCommission" text="Tx Site CP"/>:</td>
<td>
<select name="placeOfCommission">
<option value=""></option>
<c:forEach var="cecOption" items="${cecOptions}">
	<option value="${cecOption.answerConcept.id}" <c:if test="${regimenForm.placeOfCommission == cecOption.answerConcept}">selected</c:if> >${cecOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.tb03.resistanceType" text="Type of Resistance"/>:</td>
<td>
<select name="resistanceType">
<option value=""></option>
<c:forEach var="type" items="${resistancetypes}">
	<option value="${type.answerConcept.id}" <c:if test="${regimenForm.resistanceType == type.answerConcept}">selected</c:if> >${type.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.fundingSource" text="Fundz"/>:</td>
<td>
<select name="fundingSource">
<option value=""></option>
<c:forEach var="source" items="${fundingSources}">
	<option value="${source.answerConcept.id}" <c:if test="${regimenForm.fundingSource == source.answerConcept}">selected</c:if> >${source.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.sldRegimenType" text="typez"/>:</td>
<td>
<select name="sldRegimenType" id="sldRegimenType" onChange="sldToggle()">
<option value=""></option>
<c:forEach var="type" items="${sldregimens}">
	<option value="${type.answerConcept.id}" <c:if test="${regimenForm.sldRegimenType == type.answerConcept}">selected</c:if> >${type.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.otherRegimen" text="otherz"/>:</td>
<td><input name="otherRegimen" id="otherRegimen" size="20" value="${regimenForm.otherRegimen}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.cmDose" text="cmDose"/>:</td>
<td><input name="cmDose" size="4" value="${regimenForm.cmDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.amDose" text="amDose"/>:</td>
<td><input name="amDose" size="4" value="${regimenForm.amDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.mfxDose" text="mfxDose"/>:</td>
<td><input name="mfxDose" size="4" value="${regimenForm.mfxDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.lfxDose" text="lfxDose"/>:</td>
<td><input name="lfxDose" size="4" value="${regimenForm.lfxDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.ptoDose" text="ptoDose"/>:</td>
<td><input name="ptoDose" size="4" value="${regimenForm.ptoDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.csDose" text="csDose"/>:</td>
<td><input name="csDose" size="4" value="${regimenForm.csDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.pasDose" text="pasDose"/>:</td>
<td><input name="pasDose" size="4" value="${regimenForm.pasDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.zDose" text="zDose"/>:</td>
<td><input name="ZDose" size="4" value="${regimenForm.ZDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.eDose" text="eDose"/>:</td>
<td><input name="EDose" size="4" value="${regimenForm.EDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.hDose" text="hDose"/>:</td>
<td><input name="HDose" size="4" value="${regimenForm.HDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.lzdDose" text="lzdDose"/>:</td>
<td><input name="lzdDose" size="4" value="${regimenForm.lzdDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.cfzDose" text="cfzDose"/>:</td>
<td><input name="cfzDose" size="4" value="${regimenForm.cfzDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.bdqDose" text="bdqDose"/>:</td>
<td><input name="bdqDose" size="4" value="${regimenForm.bdqDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.dlmDose" text="dlmDose"/>:</td>
<td><input name="dlmDose" size="4" value="${regimenForm.dlmDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.impDose" text="impDose"/>:</td>
<td><input name="impDose" size="4" value="${regimenForm.impDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.amxDose" text="amxDose"/>:</td>
<td><input name="amxDose" size="4" value="${regimenForm.amxDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.hrDose" text="hrDose"/>:</td>
<td><input name="hrDose" size="4" value="${regimenForm.hrDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.hrzeDose" text="hrzeDose"/>:</td>
<td><input name="hrzeDose" size="4" value="${regimenForm.hrzeDose}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.sDose" text="sDose"/>:</td>
<td><input name="SDose" size="4" value="${regimenForm.SDose}"/></td>
</tr>

<tr>
<td colspan="2" align="left"><spring:message code="mdrtb.pv.other" text="Otherz"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.otherDrug1Name" text="otherDrug1Name"/>:</td>
<td><input name="otherDrug1Name" size="25" value="${regimenForm.otherDrug1Name}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.otherDrug1Dose" text="otherDrug1Dose"/>:</td>
<td><input name="otherDrug1Dose" size="4" value="${regimenForm.otherDrug1Dose}"/></td>
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