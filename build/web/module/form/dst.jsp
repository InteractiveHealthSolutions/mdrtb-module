<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%> 
<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="/moduleResources/mdrtb/mdrtb.css"/>

<!-- TODO: clean up above paths so they use dynamic reference -->
<!-- TODO: add privileges? -->

<!-- SPECIALIZED STYLES FOR THIS PAGE -->

<!-- CUSTOM JQUERY  -->
<c:set var="defaultReturnUrl" value="${pageContext.request.contextPath}/module/mdrtb/dashboard/tbdashboard.form?patientProgramId=${patientProgramId}&patientId=${dst.patient.id}"/>
<script type="text/javascript"><!--

	var $j = jQuery.noConflict();	
	
function contains(set, item) {
	for (var i in set) {
		if (set[i] == item) {
			return true;
		}
	}
	return false;
}

// hides all add and edit details boxes
function hideDisplayBoxes(){
	$j('.addBox').hide();
	$j('.editBox').hide();
	$j('.detailBox').hide();
	$j('#details_-1').hide();
}

// hides all edit, add, and delete link elements (used to stop used from navigating away from an edit)
function hideLinks() {
	$j('.edit').fadeOut();  // hide all the edit tests links
	$j('.delete').fadeOut(); // hide all the delete links 
	$j('#editSpecimen').fadeOut(); // hide the edit specimen link
	$j('#add').fadeOut(); // hide the "add a test" selector
}

// shows all edit, add, and delete elements (called when an edit is complete)
function showLinks() {
	$j('.edit').fadeIn();  // show all the edit tests links
	$j('.delete').fadeIn(); // show all the delete links 
	$j('#editSpecimen').fadeIn(); // show the edit specimen link
	$j('#add').fadeIn(); // show the "add a test" selector
}

// resets the default dst results boxes in the "add dst" section
function resetAddDstResults() {		
	$j('.addDstResult').hide().find('input,.dstResult').attr('value',''); // reset all the dst result values except drug type
	$j('.dstColonies').hide()	  // hide the colony input boxes
	
	// now reshow the boxes that contain the default drugs
	for (var i = 1; i <= ${fn:length(defaultDstDrugs)}; i++) {
		$j('#addDstResult_' + i).show();	
	}
	addDstResultCounter = ${fn:length(defaultDstDrugs)} + 1; // reset the add dst result counter
}

function showAddDstResultsWithData() {
	for (var i = 1; i < 31; i++) {
		if ($j('#addDstResult${testId}_' + i).find('.dstResult').val() != ''
			|| $j('#addDstResult${testId}_' + i).find('select').val() != '') {

			$j('#addDstResult${testId}_' + i).show();
			addDstResultCounter = i + 1;
		}
	}
}

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
		
		// set the add dst count to 1
		addDstResultCounter = 1;
		
		if (${fn:length(specimenErrors.allErrors) > 0}) {
			// switch to edit specimen if we are here because of specimen validation error
			hideLinks();
			$j('#details_specimen').hide();  // hide the specimen details box
			$j('#edit_specimen').show();  // show the edit speciment box		
		}
		else if (${fn:length(testErrors.allErrors) > 0}) {
			// switch to edit test if we are here because of a test validation error
			if(${! empty testId}) {
				// handle the "edit" case
				hideLinks();
				$j('#details_${testId}').hide();  // hide the selected details box
				$j('#edit_${testId}').show(); // show the selected edit box
				showAddDstResultsWithData(); // show any dst result rows that may have transitory data
				$j(document).scrollTop($j('#edit_${testId}').offset().top - 50); // jump to the edit box that has the error
			} 
			else {
				// handle the "add" case
				hideDisplayBoxes();
				hideLinks();
				$j('#add_${testType}').show(); // show the proper add a test box
				showAddDstResultsWithData(); // show any dst result rows thay may have transitory data
				$j(document).scrollTop($j('#add_${testType}').offset().top - 50); // jump to the edit box that has the error
			}
		}
		else {
			// show the proper detail windows if it has been specified
			// TODO: this does not work when a test is saved as the test id of a test gets changes whenever it is saved since
			// all the obs get voided and recreated;
			$j('#details_${testId}').show();
		}
	
		// event handlers to display add boxes
		$j('#addButton').click(function(){
			hideDisplayBoxes();
			hideLinks();
			addDstResultCounter = ${fn:length(defaultDstDrugs)} + 1;   // set the dst counter based on how many default drugs we have 
			$j('#add_' + $j('#addSelect').attr('value')).show(); // show the proper add a test box
		});

		//event handler to handle removing dst results
		$j('.removeDstResult').click(function() {
			$j(this).closest('.dstResult').hide();   	// hide the dst result
			$j('#removeDstResult' + $j(this).attr('value')).attr('value',$j(this).attr('value'));  	// set it's hidden input to the id of this dst report
		});

		// event handle to handle adding dst results
		$j('.addDstResultRow').click(function() {
			if(addDstResultCounter < 30) {
				$j('#addDstResult' + $j(this).attr('value') + "_" + addDstResultCounter).show();
				addDstResultCounter++;
			}
		});

		//event handler to handle removing of dst rows that have been added, but not saved
		$j('.removeDstResultRow').click(function() {
			// hide the dst result row and reset the value of all the interior elements
			$j(this).closest('.addDstResult').hide().find('input,select').attr('value','');
		});
		
	});


-->

</script>

<br/>

<div> <!-- start of page div -->

&nbsp;&nbsp;<a href="${!empty returnUrl ? returnUrl : defaultReturnUrl}"><spring:message code="mdrtb.back" text="Back"/></a>
<br/><br/>

<!-- VIEW BOX -->
<div id="viewVisit" <c:if test="${(empty dst.id) || (dst.id == -1) || fn:length(errors.allErrors) > 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.dst" text="DST Form"/>
<openmrs:hasPrivilege privilege="Edit DOTS-MDR Data">
<span style="position: absolute; right:30px;"><a id="edit" onmouseover="document.body.style.cursor='pointer'" onmouseout="document.body.style.cursor='default'"><spring:message code="mdrtb.edit" text="edit"/></a>&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/module/mdrtb/visits/delete.form?visitId=${dst.id}&patientProgramId=${patientProgramId}&patientId=${dst.patient.id }" class="delete" onclick="return confirm('<spring:message code="mdrtb.confirmDeleteVisit" text="Are you sure you want to delete this visit?"/>')"><spring:message code="mdrtb.delete" text="delete"/></a></span>
</openmrs:hasPrivilege>
</b>
<div class="box">

<table>
 
<tr>
<td><spring:message code="mdrtb.dateCollected" text="Date"/>:</td>
<td><openmrs:formatDate date="${dst.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.monthOfTreatment" text="TxMonth"/>:</td>
<td>${dst.monthOfTreatment}</td>
</tr>


<%-- <tr>
<td><spring:message code="mdrtb.provider" text="Provider"/>:</td>
<td>${dst.provider.personName}</td>
</tr> --%>
 
<tr>
<td><spring:message code="mdrtb.location" text="Location"/>:</td>
<td>${dst.location.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.specimenId" text="SpecimenId"/>:</td>
<td>${dst.specimenId }</td>
</tr>

</table>
<c:set var="resultsMap" value="${dst.resultsMap}"/>
<br/>
<table cellpadding="0">
<tr>
<td style="font-weight:bold"><u><spring:message code="mdrtb.drug" text="Drug"/></u></td>
<!--  <td style="font-weight:bold"><u><spring:message code="mdrtb.concentration" text="Concentration"/></u></td> -->
<td style="font-weight:bold"><u><spring:message code="mdrtb.result" text="Result"/></u></td>
<!--  <td style="font-weight:bold"><u><spring:message code="mdrtb.colonies" text="Colonies"/></u></td> -->
</tr>
<c:forEach var="drugType" items="${drugTypes}">
<c:if test="${!empty resultsMap[drugType.id]}">
	<c:forEach var="dstResult" items="${resultsMap[drugType.id]}">
		<tr>
		<td><nobr>${dstResult.drug.displayString}</nobr></td>
		<!-- <td><nobr>${dstResult.concentration}</nobr></td> -->
		<td><nobr>${dstResult.result.displayString}</nobr></td>
		<!-- <td><nobr>${dstResult.colonies}</nobr></td> -->
		</tr>
	</c:forEach>
</c:if>
</c:forEach>
</table>


</div>
</div>
<!-- END VIEW BOX -->

<!-- EDIT BOX -->
<div id="editVisit" <c:if test="${(!empty dst.id) && (dst.id != -1) && fn:length(errors.allErrors) == 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.dst" text="DST"/></b>
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

<form name="dst" action="dst.form?patientId=${patientId}&patientProgramId=${patientProgramId}&encounterId=${!empty dst.id ? dst.id : -1}" method="post">
<input type="hidden" name="returnUrl" value="${returnUrl}" />
<input type="hidden" name="patProgId" value="${patientProgramId}" />
<input type="hidden" name="provider" value="45" />

<table>
 
<tr>
<td><spring:message code="mdrtb.dateCollected" text="Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="encounterDatetime" startValue="${dst.encounterDatetime}"/></td>
</tr>

<tr>
<td valign="top"><spring:message code="mdrtb.monthOfTreatment" text="TxMonth"/>:</td>
<td><input name="monthOfTreatment" size="2" value="${dst.monthOfTreatment}"/></td>
</tr>

<%-- <tr>
<td><spring:message code="mdrtb.provider" text="Provider"/>:</td>
<td>
<select name="provider">
<option value=""></option>
<c:forEach var="provider" items="${providers}">
	<option value="${provider.id}" <c:if test="${dst.provider == provider}">selected</c:if>>${provider.personName}</option>
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
	<option value="${location.id}" <c:if test="${dst.location == location}">selected</c:if>>${location.displayString}</option>
</c:forEach>
</select>
</td>
</tr>


<tr>
<td><spring:message code="mdrtb.specimenId" text="SpecimenId"/>:</td>
<td><input type="text" size="10" name="specimenId" value="${dst.specimenId}"/></td>
</tr>

</table>


<br/>
<table cellpadding="0">

<tr>
<td style="font-weight:bold"><u><spring:message code="mdrtb.drug" text="Drug"/></u></td>
<%-- <td style="font-weight:bold"><u><spring:message code="mdrtb.concentration" text="Concentration"/></u></td> --%>
<td style="font-weight:bold"><u><spring:message code="mdrtb.result" text="Result"/></u></td>
<%-- <td style="font-weight:bold"><u><spring:message code="mdrtb.colonies" text="Colonies"/></u></td> --%>
</tr>
<c:forEach var="drugType" items="${drugTypes}">
	<c:if test="${!empty resultsMap[drugType.id]}">
		<c:forEach var="dstResult" items="${resultsMap[drugType.id]}">
			<tr class="dstResult">
			<td><nobr>${dstResult.drug.displayString}</nobr></td>
			<!-- <td><nobr>${dstResult.concentration}</nobr></td> -->
			<td><nobr>${dstResult.result.displayString}</nobr></td>
			<!-- <td><nobr>${dstResult.colonies}</nobr></td> -->
			<td><button class="removeDstResult" value="${dstResult.id}" type="button"><spring:message code="mdrtb.remove" text="Remove"/></button>
				<input type="hidden" id="removeDstResult${dstResult.id}" name="removeDstResult" value=""/></td>
			</tr>
		</c:forEach>
	</c:if>
</c:forEach>
<c:forEach begin="1" end="30" varStatus="i">
	<tr id="addDstResult${test.id}_${i.count}" class="addDstResult" style="display:none">
	<td><select name="addDstResult${i.count}.drug">
		<option value=""></option>
		<c:forEach var="drug" items="${drugTypes}">
			<option value="${drug.id}" <c:if test="${! empty addDstResultDrug && addDstResultDrug[i.count - 1] == drug.id}">selected</c:if>>${drug.displayString}</option>
		</c:forEach>
		</select>
	</td>
	
	<td><select name="addDstResult${i.count}.result" class="dstResult">
		<option value=""></option>
		<c:forEach var="possibleResult" items="${dstResults}">
			<option value="${possibleResult.id}" <c:if test="${! empty addDstResultResult && addDstResultResult[i.count - 1] == possibleResult.id}">selected</c:if>>${possibleResult.displayString}</option>
		</c:forEach></td>
		</select>
	</td>
	<td><input type="text" size="6" name="addDstResult${i.count}.colonies" value="${! empty addDstResultColonies ? addDstResultColonies[i.count - 1] : ''}" class="dstColonies" style="display:none"/></td>
	<td><button class="removeDstResultRow" value="${i.count}" type="button"><spring:message code="mdrtb.remove" text="Remove"/></button></td>	
	</tr>
</c:forEach>

	<tr>
	<td><button class="addDstResultRow" value="${test.id}" type="button"><spring:message code="mdrtb.addDSTResult" text="Add DST result"/></button></td>
	<!--  set to 2 for TJK. Change to 4 if showing conc and colonies -->
	<td colspan="2"/>
	</tr>
	
</table>
<br/>

<!-- end of the DST table -->

<button type="submit"><spring:message code="mdrtb.save" text="Save"/></button> <button id="cancel" type="reset"><spring:message code="mdrtb.cancel" text="Cancel"/></button>
	
</form>

</div>
</div>
<!-- END OF EDIT BOX -->
</div>

</div> <!-- end of page div -->

<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbFooter.jsp"%>