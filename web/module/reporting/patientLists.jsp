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
	var e = document.getElementById("location");
	var val2 = e.options[e.selectedIndex].value;
	var year = document.getElementById("year").value;
	var quarter = document.getElementById("quarter").value;
	var month = document.getElementById("month").value;
	var submitPath = "${pageContext.request.contextPath}/module/mdrtb/reporting/" + url + ".form?oblast="+val1+"&location="+val2+"&year="+year+"&quarter="+quarter+"&month="+month;
	
	window.location.replace(submitPath);
}


</script>

<b class="boxHeader" style="margin:0px"><spring:message code="mdrtb.patientLists" text="Lists"/></b>
<div class="box" style="margin:0px;">
<br/>
	
	<spring:message code="mdrtb.oblast" />
	<select name="oblast" id="oblast">
				    <option value=""></option>
					<c:forEach var="o" items="${oblasts}">
						<option value="${o.id}">${o.name}</option>
					</c:forEach>
				</select>
			
		    <spring:message code="mdrtb.or" />
		    <spring:message code="mdrtb.district" />
				<select name="location" id="location">
				    <option value=""></option>
					<c:forEach var="loc" items="${locations}">
						<option value="${loc.id}">${loc.name}</option>
					</c:forEach>
				</select>
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
		    <td><spring:message code="mdrtb.dotsCasesByAnatomicalSite" /></td>
		    <td><button onClick="submitForm('dotsCasesByAnatomicalSite');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.dotsPulmonaryCasesByRegisrationGroupAndBacStatus" /></td>
		    <td><button onClick="submitForm('dotsPulmonaryCasesByRegisrationGroupAndBacStatus');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.mdrXdrPatients" /></td>
		    <td><button onClick="submitForm('mdrXdrPatients');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.mdrSuccessfulTreatmentOutcome" /></td>
		    <td><button onClick="submitForm('mdrSuccessfulTreatmentOutcome');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		     <tr>
		    <td><spring:message code="mdrtb.mdrXdrPatientsNoTreatment" /></td>
		    <td><button onClick="submitForm('mdrXdrPatientsNoTreatment');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		     <tr>
		    <td><spring:message code="mdrtb.womenOfChildbearingAge" /></td>
		    <td><button onClick="submitForm('womenOfChildbearingAge');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.menOfConscriptAge" /></td>
		    <td><button onClick="submitForm('menOfConscriptAge');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    <tr>
		    <td><spring:message code="mdrtb.detectedFromContact" /></td>
		    <td><button onClick="submitForm('detectedFromContact');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		     <tr>
		    <td><spring:message code="mdrtb.withDiabetes" /></td>
		    <td><button onClick="submitForm('withDiabetes');"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    
		    </table>
		    
		   
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>
