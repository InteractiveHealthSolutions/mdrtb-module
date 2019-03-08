<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp" %>

<%@ include file="/WEB-INF/template/headerMinimal.jsp" %>
<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui-1.7.2.custom.min.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/css/redmond/jquery-ui-1.7.2.custom.css" />

<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.dimensions.pack.js"/>
<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.tooltip.js" />
<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.tooltip.css" />
<openmrs:htmlInclude file="/moduleResources/mdrtb/mdrtb.css"/>

<script>
function submitForm(url) {
	var e = document.getElementById("oblast");
	var val1 = e.options[e.selectedIndex].value;
	var e = document.getElementById("district");
	var val2 = e.options[e.selectedIndex].value;
	var e = document.getElementById("facility");
	var val3 = e.options[e.selectedIndex].value;
	var year = document.getElementById("year").value;
	var quarter = document.getElementById("quarter").value;
	var month = document.getElementById("month").value;
	var submitPath = "${pageContext.request.contextPath}/module/mdrtb/reporting/" + url + ".form?oblast="+val1+"&district="+val2+ "&facility="+val3+"&year="+year+"&quarter="+quarter+"&month="+month;
	
	window.location.replace(submitPath);
}

function fun1()
{
	var e = document.getElementById("oblast");
	var val = e.options[e.selectedIndex].value;
	var year = document.getElementById("year").value;
	var quarter =  "\"" + document.getElementById("quarter").value +  "\"";
	var month =  "\"" + document.getElementById("month").value +  "\"";
	if(val!="")
		window.location.replace("${pageContext.request.contextPath}/module/mdrtb/reporting/patientLists.form?ob="+val+"&yearSelected="+year+"&quarterSelected="+quarter+"&monthSelected="+month)
}

function fun2()
{
	var e = document.getElementById("oblast");
	var val1 = e.options[e.selectedIndex].value;
	var e = document.getElementById("district");
	var val2 = e.options[e.selectedIndex].value;
	var year = document.getElementById("year").value;
	
	if(val1==186) {
		
		if(val2!="") {
			document.getElementById("facility").selectedIndex = 0;
		}
		
		return;
	}
	
	
	var quarter = "\"" + document.getElementById("quarter").value +  "\"";
	var month =  "\"" + document.getElementById("month").value +  "\"";
	if(val2!="")
		window.location.replace("${pageContext.request.contextPath}/module/mdrtb/reporting/patientLists.form?loc="+val2+"&ob="+val1+"&yearSelected="+year+"&quarterSelected="+quarter+"&monthSelected="+month)
}

function fun3() {
  	var e = document.getElementById("oblast");
	var val1 = e.options[e.selectedIndex].value;
	var e = document.getElementById("district");
	var val2 = e.options[e.selectedIndex].value;
	var e = document.getElementById("facility");
	var val3 = e.options[e.selectedIndex].value;
	
	
	if(val1==186) {
		
		if(val3!="") {
			document.getElementById("district").selectedIndex = 0;
		}
		
	
	}
	
	return;
  
}
</script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js">
</script>
<script>
$(document).ready(function(){
	$('#oblast').val(${oblastSelected});
	$('#district').val(${districtSelected});
	$('#year').val(${yearSelected});
	$('#quarter').val(${quarterSelected});
	$('#month').val(${monthSelected});

});
</script>

<a href="${pageContext.request.contextPath}/module/mdrtb/mdrtbIndex.form"><spring:message code="mdrtb.back" text="Backu"/></a>
<br/><br/>
<b class="boxHeader" style="margin:0px"><spring:message code="mdrtb.patientLists" text="Lists"/></b>
<div class="box" style="margin:0px;">
<br/>
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
		<tr>
			<td>&nbsp;</td>
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
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr id="facilityDiv">
			<td align="right"><spring:message code="mdrtb.facility" /></td>
			<td><select name="facility" id="facility" onchange="fun3()">
					<option value=""></option>
					<c:forEach var="f" items="${facilities}">
						<option value="${f.id}">${f.name}</option>
					</c:forEach>
			</select></td>
		<tr>
		</table>
			<br/>
			<spring:message code="mdrtb.year" />&nbsp;&nbsp;&nbsp;&nbsp;<input name="year" id="year" type="text" size="4"/><br/>
			<spring:message code="mdrtb.quarter" /><input name="quarter" id="quarter" type="text" size="7"/></td>
			<spring:message code="mdrtb.or" />&nbsp;<spring:message code="mdrtb.month" />&nbsp;<input id="month" name="month" type="text" size="7"/>
		    <br/><br/><br/><br/>
		    
		    <table>
		    <tr>
		    <td><spring:message code="mdrtb.allCasesEnrolled" /></td>
		    <td><button onClick="submitForm('allCasesEnrolled');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.dotsCasesByRegistrationGroup" /></td>
		    <td><button onClick="submitForm('dotsCasesByRegistrationGroup');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.byDrugResistance" /></td>
		    <td><button onClick="submitForm('byDrugResistance');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.dotsCasesByAnatomicalSite" /></td>
		    <td><button onClick="submitForm('dotsCasesByAnatomicalSite');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.dotsPulmonaryCasesByRegisrationGroupAndBacStatus" /></td>
		    <td><button onClick="submitForm('dotsPulmonaryCasesByRegisrationGroupAndBacStatus');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		  <!--   <tr>
		    <td><spring:message code="mdrtb.mdrXdrPatients" /></td>
		    <td><button onClick="submitForm('mdrXdrPatients');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr> -->
		    
		      <tr>
		    <td><spring:message code="mdrtb.drTbPatients" /></td>
		    <td><button onClick="submitForm('drTbPatients');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <!-- <tr>
		    <td><spring:message code="mdrtb.mdrSuccessfulTreatmentOutcome" /></td>
		    <td><button onClick="submitForm('mdrSuccessfulTreatmentOutcome');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr> -->
		    
		     <tr>
		    <td><spring:message code="mdrtb.drTbPatientsSuccessfulTreatment" /></td>
		    <td><button onClick="submitForm('drTbPatientsSuccessfulTreatment');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <!--  <tr>
		    <td><spring:message code="mdrtb.mdrXdrPatientsNoTreatment" /></td>
		    <td><button onClick="submitForm('mdrXdrPatientsNoTreatment');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr> -->
		    
		     <tr>
		    <td><spring:message code="mdrtb.drTbPatientsNoTreatment" /></td>
		    <td><button onClick="submitForm('drTbPatientsNoTreatment');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		     <tr>
		    <td><spring:message code="mdrtb.womenOfChildbearingAge" /></td>
		    <td><button onClick="submitForm('womenOfChildbearingAge');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.menOfConscriptAge" /></td>
		    <td><button onClick="submitForm('menOfConscriptAge');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <!-- <tr>
		    <td><spring:message code="mdrtb.detectedFromContact" /></td>
		    <td><button onClick="submitForm('detectedFromContact');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr> -->
		    
		    <tr>
		    <td><spring:message code="mdrtb.withConcomitantDisease" /></td>
		    <td><button onClick="submitForm('withConcomitantDisease');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		   <!--  <tr>
		    <td><spring:message code="mdrtb.withDiabetes" /></td>
		    <td><button onClick="submitForm('withDiabetes');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.withCancer" /></td>
		    <td><button onClick="submitForm('withCancer');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		     <tr>
		    <td><spring:message code="mdrtb.withUlcer" /></td>
		    <td><button onClick="submitForm('withUlcer');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		     <tr>
		    <td><spring:message code="mdrtb.withHypertension" /></td>
		    <td><button onClick="submitForm('withHypertension');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		     <tr>
		    <td><spring:message code="mdrtb.withCOPD" /></td>
		    <td><button onClick="submitForm('withCOPD');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		     <tr>
		    <td><spring:message code="mdrtb.withMentalDisorder" /></td>
		    <td><button onClick="submitForm('withMentalDisorder');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		     <tr>
		    <td><spring:message code="mdrtb.withHIV" /></td>
		    <td><button onClick="submitForm('withHIV');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		      <tr>
		    <td><spring:message code="mdrtb.withHepatitis" /></td>
		    <td><button onClick="submitForm('withHepatitis');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		       <tr>
		    <td><spring:message code="mdrtb.withKidneyDisease" /></td>
		    <td><button onClick="submitForm('withKidneyDisease');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		     <tr>
		    <td><spring:message code="mdrtb.withOtherDisease" /></td>
		    <td><button onClick="submitForm('withOtherDisease');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr> -->
		    
		     <tr>
		    <td><spring:message code="mdrtb.byDwelling" /></td>
		    <td><button onClick="submitForm('byDwelling');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.bySocProfStatus" /></td>
		    <td><button onClick="submitForm('bySocProfStatus');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.byPopCategory" /></td>
		    <td><button onClick="submitForm('byPopCategory');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.byPlaceOfDetection" /></td>
		    <td><button onClick="submitForm('byPlaceOfDetection');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.byCircumstancesOfDetection" /></td>
		    <td><button onClick="submitForm('byCircumstancesOfDetection');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.byMethodOfDetection" /></td>
		    <td><button onClick="submitForm('byMethodOfDetection');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.byPulmonaryLocation" /></td>
		    <td><button onClick="submitForm('byPulmonaryLocation');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		     <tr>
		    <td><spring:message code="mdrtb.byExtraPulmonaryLocation" /></td>
		    <td><button onClick="submitForm('byExtraPulmonaryLocation');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    </table>
		    
		   
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>
