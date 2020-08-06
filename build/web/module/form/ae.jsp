<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%> 
<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="/moduleResources/mdrtb/mdrtb.css"/>
<script src='<%= request.getContextPath() %>/dwr/interface/MdrtbAEDrugsDWR.js'></script>
<!-- TODO: clean up above paths so they use dynamic reference -->
<!-- TODO: add privileges? -->

<!-- SPECIALIZED STYLES FOR THIS PAGE -->

<!-- CUSTOM JQUERY  -->
<c:set var="defaultReturnUrl" value="${pageContext.request.contextPath}/module/mdrtb/dashboard/dashboard.form?patientProgramId=${patientProgramId}&patientId=${aeForm.patient.id}"/>
<script type="text/javascript"><!--

	var $j = jQuery.noConflict();	

	$j(document).ready(function(){

		$j('#edit').click(function(){
			$j('#viewVisit').hide();
			$j('#editVisit').show();
			typeToggle();
			dateToggle();
			car1Toggle();
			car2Toggle();
			car3Toggle();
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
		
		
	/* 	
		$('#oblast').val(${oblastSelected});
		$('#district').val(${districtSelected});
		$('#facility').val(${facilitySelected}); */
		
		
		
		
	});
	
	function typeToggle () {
		
		var statusBox = document.getElementById('typeOfEvent');
		var choice  = statusBox.options[statusBox.selectedIndex].value;
		showHideTypes(choice);
	}
	
	function showHideTypes(val) {
	
       	if(val==710) {
       		
       		document.getElementById('typeOfSpecialEvent').disabled = true;
   			document.getElementById('typeOfSpecialEvent').selectedIndex = 0;
   			document.getElementById('typeOfSAE').disabled = false;
       		
       	}
       	else if(val==711) {
       		document.getElementById('typeOfSAE').disabled = true;
   			document.getElementById('typeOfSAE').selectedIndex = 0;
   			document.getElementById('typeOfSpecialEvent').disabled = false;
       	}
       	
       	else {
       		document.getElementById('typeOfSpecialEvent').disabled = true;
   			document.getElementById('typeOfSpecialEvent').selectedIndex = 0;
   			document.getElementById('typeOfSAE').disabled = true;
   			document.getElementById('typeOfSAE').selectedIndex = 0;
       		//hide both
       	}
     }
	
	function dateToggle () {
		
		var statusBox = document.getElementById('actionOutcome');
		var choice  = statusBox.options[statusBox.selectedIndex].value;
		showHideDate(choice);
	}
	
	function showHideDate(val) {
	
       	if(val==731 || val==730) {
       		
       		document.getElementById('outcomeDate').disabled = true;
       		document.getElementById('outcomeDate').value = "";
   			
       		
       	}
       	else {
       		
       		document.getElementById('outcomeDate').disabled = false;
       	}
       	
      
     }
	
     function car1Toggle () {
		
		var statusBox = document.getElementById('causalityDrug1');
		var choice  = statusBox.options[statusBox.selectedIndex].value;
		showHideCar1(choice);
	}
	
	function showHideCar1(val) {
	
       	if(val=="") {
       		
       		document.getElementById('causalityAssessmentResult1').disabled = true;
       		document.getElementById('causalityAssessmentResult1').selectedIndex = 0;
   			
       		
       	}
       	else {
       		
       		document.getElementById('causalityAssessmentResult1').disabled = false;
       	}
       	
      
     }	
	
	 function car2Toggle () {
			
			var statusBox = document.getElementById('causalityDrug2');
			var choice  = statusBox.options[statusBox.selectedIndex].value;
			showHideCar2(choice);
		}
		
	function showHideCar2(val) {
		
	       	if(val=="") {
	       		
	       		document.getElementById('causalityAssessmentResult2').disabled = true;
	       		document.getElementById('causalityAssessmentResult2').selectedIndex = 0;
	   			
	       		
	       	}
	       	else {
	       		
	       		document.getElementById('causalityAssessmentResult2').disabled = false;
	       	}
	       	
	      
	     }	
	
	 function car3Toggle () {
			
			var statusBox = document.getElementById('causalityDrug3');
			var choice  = statusBox.options[statusBox.selectedIndex].value;
			showHideCar3(choice);
		}
		
		function showHideCar3(val) {
		
	       	if(val=="") {
	       		
	       		document.getElementById('causalityAssessmentResult3').disabled = true;
	       		document.getElementById('causalityAssessmentResult3').selectedIndex = 0;
	   			
	       		
	       	}
	       	else {
	       		
	       		document.getElementById('causalityAssessmentResult3').disabled = false;
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

	    mywindow.document.write('<html><head><title><spring:message code="mdrtb.pv.aeForm" text="AE"/></title>');
	    mywindow.document.write('</head><body >');
	    mywindow.document.write('<h1><spring:message code="mdrtb.pv.aeForm" text="AE"/></h1>');
	    mywindow.document.write(document.getElementById("ae").innerHTML);
	    
	    mywindow.document.write('</body></html>');

	    mywindow.document.close(); // necessary for IE >= 10
	    mywindow.focus(); // necessary for IE >= 10*/

	    mywindow.print();
	    mywindow.close();

	    return true;
	}
	
	function drugToggle() {
		var statusBox = document.getElementById('treatmentRegimenAtOnset');
		var regimen  = statusBox.options[statusBox.selectedIndex].value;
		
		var drug1 = document.getElementById('causalityDrug1');
		var drug2 = document.getElementById('causalityDrug2');
		var drug3 = document.getElementById('causalityDrug3');
		
		var car1 = document.getElementById('causalityAssessmentResult1');
		var car2 = document.getElementById('causalityAssessmentResult2');
		var car3 = document.getElementById('causalityAssessmentResult3');
		
		car1.disabled = true;
		car1.selectedIndex = 0;
		car2.disabled = true;
		car2.selectedIndex = 0;
		car3.disabled = true;
		car3.selectedIndex = 0;
		
		
		drug1.options.length = 0;
		drug2.options.length = 0;
		drug3.options.length = 0;
		
		var drugs=[];
		var optionsHTML=[];
		   MdrtbAEDrugsDWR.getRelevantDrugsString(regimen, {
			   callback:function(data) {
				   drugs = data;
				},
			   async:false
			 } );
		 //alert(drugs); 
		 for(var i=0; i<drugs.length; i++) {
			 optionsHTML.push(drugs[i]);    
		 }
		 
		 drug1.innerHTML = optionsHTML.join('\n');
		 drug2.innerHTML = optionsHTML.join('\n');
		 drug3.innerHTML = optionsHTML.join('\n');
		   
		   
	
	}
	
	/* function fun1()
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
	} */
	
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
			errorText = ""  + '<spring:message code="mdrtb.pv.onsetDateInFuture"/>' + "";
			alert(errorText);
			return false;
		}
		
		if(document.getElementById("diagnosticInvestigation").value=="") {
			
			errorText = ""  + '<spring:message code="mdrtb.pv.missingDiagnosticInvestigation"/>' + "";
			alert(errorText);
			return false;
		}
		
		if(document.getElementById("suspectedDrug").value=="") {
			
			errorText = ""  + '<spring:message code="mdrtb.pv.missingSuspectedDrug"/>' + "";
			alert(errorText);
			return false;
		}
		
		if(document.getElementById("typeOfEvent").value=="") {
			
			errorText = ""  + '<spring:message code="mdrtb.pv.missingTypeOfEvent"/>' + "";
			alert(errorText);
			return false;
		}
		
		if(document.getElementById("typeOfEvent").value==710 && document.getElementById("typeOfSAE").value=="") {
			
			errorText = ""  + '<spring:message code="mdrtb.pv.missingSAEType"/>' + "";
			alert(errorText);
			return false;
		}
		
		if(document.getElementById("typeOfEvent").value==711 && document.getElementById("typeOfSpecialEvent").value=="") {
			
			errorText = ""  + '<spring:message code="mdrtb.pv.missingSpecialEventType"/>' + "";
			alert(errorText);
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
				errorText = ""  + '<spring:message code="mdrtb.pv.yellowCardDateBeforeOnsetDate"/>' + "";
				alert(errorText);
				return false;
			}
		}
		
		if(document.getElementById("causalityDrug1").value!="" && document.getElementById("causalityAssessmentResult1").value=="") {
			
			errorText = ""  + '<spring:message code="mdrtb.pv.missingCAR1"/>' + "";
			alert(errorText);
			return false;
		}
		
		if(document.getElementById("causalityDrug2").value!="" && document.getElementById("causalityAssessmentResult2").value=="") {
			
			errorText = ""  + '<spring:message code="mdrtb.pv.missingCAR2"/>' + "";
			alert(errorText);
			return false;
		}
		
		if(document.getElementById("causalityDrug3").value!="" && document.getElementById("causalityAssessmentResult3").value=="") {
			
			errorText = ""  + '<spring:message code="mdrtb.pv.missingCAR3"/>' + "";
			alert(errorText);
			return false;
		}
		
		if(document.getElementById("actionTaken").value=="") {
			
			errorText = ""  + '<spring:message code="mdrtb.pv.actionTaken"/>' + "";
			alert(errorText);
			return false;
		}

		var outcomeDateString = document.getElementById("outcomeDate").value;
		
		if(document.getElementById("actionOutcome").value!="" && document.getElementById("actionOutcome").value!=731 && document.getElementById("actionOutcome").value!=730 && outcomeDateString=="") {
			
			errorText = ""  + '<spring:message code="mdrtb.pv.missingOutcomeDate"/>' + "";
			alert(errorText);
			return false;
		}
		
		if(outcomeDateString != "") {
			
			if(document.getElementById("actionOutcome").value=="") {
				
				errorText = ""  + '<spring:message code="mdrtb.pv.missingOutcome"/>' + "";
				alert(errorText);
				return false;
			}
			
			
			outcomeDateString = outcomeDateString.replace(/\//g,".");
			parts = outcomeDateString.split(".");
			day = parts[0];
			month = parts[1]-1;
			year = parts[2];
		
			var outcomeDate = new Date(year, month, day);
			if(outcomeDate.getTime() < onsetDate.getTime()) {
				errorText = ""  + '<spring:message code="mdrtb.pv.outcomeDateBeforeOnsetDate"/>' + "";
				alert(errorText);
				return false;
			}
		}
		
		if(document.getElementById("meddraCode").value=="") {
			
			errorText = ""  + '<spring:message code="mdrtb.pv.missingMeddraCode"/>' + "";
			alert(errorText);
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

</table>

<table>
<tr>
<td colspan="2"><spring:message code="mdrtb.pv.diagnosticInvestigation" text="AEz"/>:</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.clinicalScreen" text="Csz"/>:</td>
<td>${aeForm.clinicalScreenDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.visualAcuity" text="Vaz"/>:</td>
<td>${aeForm.visualAcuityDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.hearingTest" text="Htz"/>:</td>
<td>${aeForm.simpleHearingTestDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.audiogram" text="Aud"/>:</td>
<td>${aeForm.audiogramDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.neuroInvestigations" text="Niz"/>:</td>
<td>${aeForm.neuroInvestigationDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.serumCreatnine" text="Scz"/>:</td>
<td>${aeForm.serumCreatnineDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.alt" text="altz"/>:</td>
<td>${aeForm.altDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.ast" text="astz"/>:</td>
<td>${aeForm.astDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.bilirubin" text="Brb"/>:</td>
<td>${aeForm.bilirubinDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.alkalinePhosphatase" text="akpz"/>:</td>
<td>${aeForm.alkalinePhosphataseDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.ygt" text="ygtz"/>:</td>
<td>${aeForm.ygtDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.ecg" text="ecgz"/>:</td>
<td>${aeForm.ecgDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.lipase" text="lps"/>:</td>
<td>${aeForm.lipaseDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.amylase" text="amz"/>:</td>
<td>${aeForm.amylaseDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.potassium" text="pz"/>:</td>
<td>${aeForm.potassiumDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.magnesium" text="mgz"/>:</td>
<td>${aeForm.magnesiumDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.calcium" text="cz"/>:</td>
<td>${aeForm.calciumDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.albumin" text="alb"/>:</td>
<td>${aeForm.albuminDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.cbc" text="cbcz"/>:</td>
<td>${aeForm.cbcDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.bloodGlucose" text="bgz"/>:</td>
<td>${aeForm.bloodGlucoseDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.thyroidTest" text="ttz"/>:</td>
<td>${aeForm.thyroidTestDone.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.other" text="otz"/>:</td>
<td>${aeForm.otherTestDone.displayString}</td>
</tr>

</table>
<table>

<tr>
<td><spring:message code="mdrtb.pv.treatmentRegimenAtOnset" text="regz"/>:</td>
<td>${aeForm.treatmentRegimenAtOnset}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.suspectedDrug" text="Drugz"/>:</td>
<td>${aeForm.suspectedDrug}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.typeOfEvent" text="Eventz"/>:</td>
<td>${aeForm.typeOfEvent.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.typeOfSAE" text="SAEz"/>:</td>
<td>${aeForm.typeOfSAE.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.typeOfSpecialEvent" text="Specialz"/>:</td>
<td>${aeForm.typeOfSpecialEvent.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.requiresAncillaryDrugs" text="Ancz"/>:</td>
<td>${aeForm.requiresAncillaryDrugs.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.requiresDoseChange" text="dosez"/>:</td>
<td>${aeForm.requiresDoseChange.displayString}</td>
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
<td>${aeForm.actionTakenSummary}</td>
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
<input type="hidden" name="location" value="${aeForm.location.id }"/>

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
	</table> --%>
	
<table>

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
<td>
<select name="adverseEvent" id="adverseEvent">
<option value=""></option>
<c:forEach var="aeOption" items="${aeOptions}" varStatus="loop">
	<option value="${aeOption.answerConcept.id}" <c:if test="${aeForm.adverseEvent == aeOption.answerConcept}">selected</c:if> >${loop.index + 1}. ${aeOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>
</tr>

</table>

<table>
<tr>
<td colspan="2"><spring:message code="mdrtb.pv.diagnosticInvestigation" text="AEz"/>:</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.clinicalScreen" text="Csz"/>:</td>
<td>
<select name="clinicalScreenDone" id="clinicalScreenDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.clinicalScreenDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.visualAcuity" text="Vaz"/>:</td>
<td>
<select name="visualAcuityDone" id="visualAcuityDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.visualAcuityDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.hearingTest" text="Htz"/>:</td>
<td>
<select name="simpleHearingTestDone" id="simpleHearingTestDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.simpleHearingTestDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.audiogram" text="Aud"/>:</td>
<td>
<select name="audiogramDone" id="audiogramDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.audiogramDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.neuroInvestigations" text="Niz"/>:</td>
<td>
<select name="neuroInvestigationDone" id="neuroInvestigationDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.neuroInvestigationDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.serumCreatnine" text="Scz"/>:</td>
<td>
<select name="serumCreatnineDone" id="serumCreatnineDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.serumCreatnineDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.alt" text="altz"/>:</td>
<td>
<select name="altDone" id="altDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.altDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.ast" text="astz"/>:</td>
<td>
<select name="astDone" id="astDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.astDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.bilirubin" text="Brb"/>:</td>
<td>
<select name="bilirubinDone" id="bilirubinDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.bilirubinDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.alkalinePhosphatase" text="akpz"/>:</td>
<td>
<select name="alkalinePhosphataseDone" id="alkalinePhosphataseDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.alkalinePhosphataseDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.ygt" text="ygtz"/>:</td>
<td>
<select name="ygtDone" id="ygtDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.ygtDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.ecg" text="ecgz"/>:</td>
<td>
<select name="ecgDone" id="ecgDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.ecgDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.lipase" text="lps"/>:</td>
<td>
<select name="lipaseDone" id="lipaseDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.lipaseDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.amylase" text="amz"/>:</td>
<td>
<select name="amylaseDone" id="amylaseDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.amylaseDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.potassium" text="pz"/>:</td>
<td>
<select name="potassiumDone" id="potassiumDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.potassiumDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.magnesium" text="mgz"/>:</td>
<td>
<select name="magnesiumDone" id="magnesiumDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.magnesiumDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.calcium" text="cz"/>:</td>
<td>
<select name="calciumDone" id="calciumDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.calciumDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.albumin" text="alb"/>:</td>
<td>
<select name="albuminDone" id="albuminDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.albuminDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.cbc" text="cbcz"/>:</td>
<td>
<select name="cbcDone" id="cbcDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.cbcDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.bloodGlucose" text="bgz"/>:</td>
<td>
<select name="bloodGlucoseDone" id="bloodGlucoseDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.bloodGlucoseDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.thyroidTest" text="ttz"/>:</td>
<td>
<select name="thyroidTestDone" id="thyroidTestDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.thyroidTestDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.other" text="otz"/>:</td>
<td>
<select name="otherTestDone" id="otherTestDone">
<option value=""></option>
<c:forEach var="option" items="${yesno}">
	<option value="${option.answerConcept.id}" <c:if test="${aeForm.otherTestDone == option.answerConcept}">selected</c:if> >${option.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

</table>

<table>

<tr>
<td><spring:message code="mdrtb.pv.treatmentRegimenAtOnset" text="regimenz"/>:</td>
<td>
<select name="treatmentRegimenAtOnset" id="treatmentRegimenAtOnset" onChange="drugToggle()">
<option value=""></option>
<c:forEach var="regimen" items="${regimens}">
	<option value="${regimen}" <c:if test="${aeForm.treatmentRegimenAtOnset == regimen}">selected</c:if> >${regimen}</option>
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
<td><spring:message code="mdrtb.pv.typeOfEvent" text="Eventz" />:</td>
<td>
<select name="typeOfEvent" id="typeOfEvent" onChange="typeToggle()">
<option value=""></option>
<c:forEach var="typeOption" items="${typeOptions}" varStatus="loop">
	<option value="${typeOption.answerConcept.id}" <c:if test="${aeForm.typeOfEvent == typeOption.answerConcept}">selected</c:if> >${loop.index + 1}. ${typeOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.typeOfSAE" text="SAEz"/>:</td>
<td>
<select name="typeOfSAE" id="typeOfSAE">
<option value=""></option>
<c:forEach var="saeOption" items="${saeOptions}" varStatus="loop">
	<option value="${saeOption.answerConcept.id}" <c:if test="${aeForm.typeOfSAE == saeOption.answerConcept}">selected</c:if> >${loop.index + 1}. ${saeOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.typeOfSpecialEvent" text="Specialz"/>:</td>
<td>
<select name="typeOfSpecialEvent" id="typeOfSpecialEvent">
<option value=""></option>
<c:forEach var="specialOption" items="${specialOptions}" varStatus="loop">
	<option value="${specialOption.answerConcept.id}" <c:if test="${aeForm.typeOfSpecialEvent == specialOption.answerConcept}">selected</c:if> >${loop.index + 1}. ${specialOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.requiresAncillaryDrugs" text="Ancez"/>:</td>
<td>
<select name="requiresAncillaryDrugs" id="requiresAncillaryDrugs">
<option value=""></option>
<c:forEach var="ancOption" items="${yesno}">
	<option value="${ancOption.answerConcept.id}" <c:if test="${aeForm.requiresAncillaryDrugs == ancOption.answerConcept}">selected</c:if> >${ancOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.requiresDoseChange" text="Dosez"/>:</td>
<td>
<select name="requiresDoseChange" id="requiresDoseChange">
<option value=""></option>
<c:forEach var="changeOption" items="${yesno}" varStatus="loop">
	<option value="${changeOption.answerConcept.id}" <c:if test="${aeForm.requiresDoseChange == changeOption.answerConcept}">selected</c:if> >${changeOption.answerConcept.displayString}</option>
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
<select name="causalityDrug1" id="causalityDrug1" onChange="car1Toggle()">
<option value=""></option>
<c:forEach var="cdOption" items="${cdOptions}">
	<option value="${cdOption.answerConcept.id}" <c:if test="${aeForm.causalityDrug1 == cdOption.answerConcept}">selected</c:if> >${cdOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
<td><spring:message code="mdrtb.pv.causalityAssessmentResult1" text="CAR1z"/>:</td>
<td>
<select name="causalityAssessmentResult1" id="causalityAssessmentResult1" >
<option value=""></option>
<c:forEach var="carOption" items="${carOptions}" varStatus="loop">
	<option value="${carOption.answerConcept.id}" <c:if test="${aeForm.causalityAssessmentResult1 == carOption.answerConcept}">selected</c:if> >${loop.index + 1}. ${carOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.causalityDrug2" text="CD2z"/>:</td>
<td>
<select name="causalityDrug2" id="causalityDrug2" onChange="car2Toggle()">
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
<c:forEach var="carOption" items="${carOptions}" varStatus="loop">
	<option value="${carOption.answerConcept.id}" <c:if test="${aeForm.causalityAssessmentResult2 == carOption.answerConcept}">selected</c:if> >${loop.index + 1}. ${carOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.causalityDrug3" text="CD3z"/>:</td>
<td>
<select name="causalityDrug3" id="causalityDrug3" onChange="car3Toggle()">
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
<c:forEach var="carOption" items="${carOptions}" varStatus="loop">
	<option value="${carOption.answerConcept.id}" <c:if test="${aeForm.causalityAssessmentResult3 == carOption.answerConcept}">selected</c:if> >${loop.index + 1}. ${carOption.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.actionTaken" text="Actionz"/>:</td>
<td>
<select name="actionTaken" id="actionTaken">
<option value=""></option>
<c:forEach var="action" items="${actions}" varStatus="loop">
	<option value="${action.answerConcept.id}" <c:if test="${aeForm.actionTaken == action.answerConcept}">selected</c:if> >${loop.index + 1}. ${action.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.actionTaken" text="Actionz"/>:</td>
<td>
<select name="actionTaken2" id="actionTaken2">
<option value=""></option>
<c:forEach var="action" items="${actions}" varStatus="loop">
	<option value="${action.answerConcept.id}" <c:if test="${aeForm.actionTaken2 == action.answerConcept}">selected</c:if> >${loop.index + 1}. ${action.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.actionTaken" text="Actionz"/>:</td>
<td>
<select name="actionTaken3" id="actionTaken3">
<option value=""></option>
<c:forEach var="action" items="${actions}" varStatus="loop">
	<option value="${action.answerConcept.id}" <c:if test="${aeForm.actionTaken3 == action.answerConcept}">selected</c:if> >${loop.index + 1}. ${action.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.actionTaken" text="Actionz"/>:</td>
<td>
<select name="actionTaken4" id="actionTaken4">
<option value=""></option>
<c:forEach var="action" items="${actions}" varStatus="loop">
	<option value="${action.answerConcept.id}" <c:if test="${aeForm.actionTaken4 == action.answerConcept}">selected</c:if> >${loop.index + 1}. ${action.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.actionTaken" text="Actionz"/>:</td>
<td>
<select name="actionTaken5" id="actionTaken5">
<option value=""></option>
<c:forEach var="action" items="${actions}" varStatus="loop">
	<option value="${action.answerConcept.id}" <c:if test="${aeForm.actionTaken5 == action.answerConcept}">selected</c:if> >${loop.index + 1}. ${action.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>


<tr>
<td><spring:message code="mdrtb.pv.actionOutcome" text="Actionz"/>:</td>
<td>
<select name="actionOutcome" id="actionOutcome" onChange="dateToggle()">
<option value=""></option>
<c:forEach var="outcome" items="${outcomes}" varStatus="loop">
	<option value="${outcome.answerConcept.id}" <c:if test="${aeForm.actionOutcome == outcome.answerConcept}">selected</c:if> >${loop.index + 1}. ${outcome.answerConcept.displayString}</option>
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
<c:forEach var="code" items="${meddraCodes}" varStatus="loop">
	<option value="${code.answerConcept.id}" <c:if test="${aeForm.meddraCode == code.answerConcept}">selected</c:if> >${loop.index + 1}. ${code.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.pv.drugRechallenge" text="RCz"/>:</td>
<td>
<select name="drugRechallenge" id="drugRechallenge">
<option value=""></option>
<c:forEach var="dr" items="${drugRechallenges}" varStatus="loop">
	<option value="${dr.answerConcept.id}" <c:if test="${aeForm.drugRechallenge == dr.answerConcept}">selected</c:if> >${loop.index + 1}. ${dr.answerConcept.displayString}</option>
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