<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%> 
<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="/moduleResources/mdrtb/mdrtb.css"/>

<!-- TODO: clean up above paths so they use dynamic reference -->
<!-- TODO: add privileges? -->

<!-- SPECIALIZED STYLES FOR THIS PAGE -->

<!-- CUSTOM JQUERY  -->
<c:set var="defaultReturnUrl" value="${pageContext.request.contextPath}/module/mdrtb/dashboard/tbdashboard.form?patientProgramId=${patientProgramId}"/>
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
		
		// event handlers to hide and show specimen edit box
		$j('#editSpecimen').click(function(){
			hideLinks();
			$j('#details_specimen').hide();  // hide the specimen details box
			$j('#edit_specimen').show();  // show the edit speciment box
		});

		$j('#cancelSpecimen').click(function(){
			// if this a cancel during a reload due to validation error, we need to reload
			// the entire page to "reset" the specimen model attribute, which is in a transient state
			if (${fn:length(specimenErrors.allErrors) > 0}) {
				window.location="specimen.form?patientId=${specimen.patient.patientId}&patientProgramId=${patientProgramId}";
			}
			else {
				// otherwise, just do a standard "cancel"		
				showLinks();
				$j('#edit_specimen').hide();  // hide the edit specimen box
				$j('#details_specimen').show();  // show the specimen details box
				$j('.scannedLabReport').show(); // show any scanned lab reports that may have been deleted
			}
		});

		// event handlers to display add boxes
		$j('#addButton').click(function(){
			hideDisplayBoxes();
			hideLinks();
			addDstResultCounter = ${fn:length(defaultDstDrugs)} + 1;   // set the dst counter based on how many default drugs we have 
			$j('#add_' + $j('#addSelect').attr('value')).show(); // show the proper add a test box
		});

		// event handler to handle the "quick test entry" add button at the top of the page
		$j('#quickEntryAddButton').click(function(){
			if($j('#quickEntryAddSelect').attr('value').indexOf("Set") != -1) {
				window.location='quickEntry.form?patientId=${! empty patientId ? patientId : specimen.patient.patientId}&patientProgramId=${patientProgramId}&testType=' + $j('#quickEntryAddSelect').attr('value') + "&numberOfTests=3";
			}
			else {
				window.location='quickEntry.form?patientId=${! empty patientId ? patientId : specimen.patient.patientId}&patientProgramId=${patientProgramId}&testType=' + $j('#quickEntryAddSelect').attr('value') + "&numberOfTests=1";
			}
		});
	
		// event handler to display edit detail boxes
		$j('.edit').click(function(){
			hideLinks();
			$j('#details_' + this.id).hide();  // hide the selected details box
			$j('#edit_' + this.id).show();  // show the selected edit box
		});

		// event handler to cancel an edit or add
		$j('.cancel').click(function(){	
			// if this a cancel during a reload due to validation error, we need to reload
			// the entire page to "reset" the specimen model attribute, which is in a transient state
			if (${fn:length(testErrors.allErrors) > 0}) {
				window.location="dst.form?patientId=${specimen.patient.patientId}&patientProgramId=${patientProgramId}";
			}
			else {			
				hideDisplayBoxes();
				$j('.detailBox').show();  // show all the detail boxes
				showLinks();
				$j('.dstResult').show(); // show any dst results that may have been deleted
				resetAddDstResults();
			}
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
<div id="viewVisit" <c:if test="${(empty hain.id) || (hain.id == -1) || fn:length(errors.allErrors) > 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.hain" text="HAIN Form"/>
<span style="position: absolute; right:30px;"><a id="edit" onmouseover="document.body.style.cursor='pointer'" onmouseout="document.body.style.cursor='default'"><spring:message code="mdrtb.edit" text="edit"/></a>&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/module/mdrtb/visits/delete.form?visitId=${hain.id}&patientProgramId=${patientProgramId}" class="delete" onclick="return confirm('<spring:message code="mdrtb.confirmDeleteVisit" text="Are you sure you want to delete this visit?"/>')"><spring:message code="mdrtb.delete" text="delete"/></a></span>
</b>
<div class="box">

<table>
 
<tr>
<td><spring:message code="mdrtb.dateCollected" text="Date"/>:</td>
<td><openmrs:formatDate date="${hain.encounterDatetime}" format="${_dateFormatDisplay}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.provider" text="Provider"/>:</td>
<td>${hain.provider.personName}</td>
</tr>
 
<tr>
<td><spring:message code="mdrtb.location" text="Location"/>:</td>
<td>${hain.location.displayString}</td>
</tr>



<tr>
<td><spring:message code="mdrtb.specimenId" text="SpecimenId"/>:</td>
<td>${hain.specimenId }</td>
</tr>

<tr>
<td><spring:message code="mdrtb.mtbResult" text="MtbResult"/>:</td>
<td>${hain.mtbResult.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.inhResult" text="inhResult"/>:</td>
<td>${hain.inhResult.displayString}</td>
</tr>

<tr>
<td><spring:message code="mdrtb.rifResult" text="rifResult"/>:</td>
<td>${hain.rifResult.displayString}</td>
</tr>


</table>

</div>
</div>
<!-- END VIEW BOX -->

<!-- EDIT BOX -->
<div id="editVisit" <c:if test="${(!empty hain.id) && (hain.id != -1) && fn:length(errors.allErrors) == 0}"> style="display:none" </c:if>>
<b class="boxHeader"><spring:message code="mdrtb.hain" text="HAIN"/></b>
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

<form name="hain" action="hain.form?patientId=${patientId}&patientProgramId=${patientProgramId}&encounterId=${!empty hain.id ? hain.id : -1}" method="post">
<input type="hidden" name="returnUrl" value="${returnUrl}" />
<input type="hidden" name="patProgId" value="${patientProgramId}" />

<table>
 
<tr>
<td><spring:message code="mdrtb.dateCollected" text="Date"/>:</td>
<td><openmrs_tag:dateField formFieldName="encounterDatetime" startValue="${hain.encounterDatetime}"/></td>
</tr>

<tr>
<td><spring:message code="mdrtb.provider" text="Provider"/>:</td>
<td>
<select name="provider">
<option value=""></option>
<c:forEach var="provider" items="${providers}">
	<option value="${provider.id}" <c:if test="${hain.provider == provider}">selected</c:if>>${provider.personName}</option>
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
	<option value="${location.id}" <c:if test="${hain.location == location}">selected</c:if>>${location.displayString}</option>
</c:forEach>
</select>
</td>
</tr>


<tr>
<td><spring:message code="mdrtb.specimenId" text="SpecimenId"/>:</td>
<td><input type="text" size="10" name="specimenId" value="${hain.specimenId}"/></td>
</tr>


<tr>
<td><spring:message code="mdrtb.mtbResult" text="mtbResult"/>:</td>
<td>
<select name="mtbResult">
<option value=""></option>
<c:forEach var="result" items="${mtbresults}">
	<option value="${result.answerConcept.id}" <c:if test="${hain.mtbResult == result.answerConcept}">selected</c:if> >${result.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>

<tr>
<td><spring:message code="mdrtb.inhResult" text="inhResult"/>:</td>
<td>
<select name="inhResult">
<option value=""></option>
<c:forEach var="result" items="${inhresults}">
	<option value="${result.answerConcept.id}" <c:if test="${hain.inhResult == result.answerConcept}">selected</c:if> >${result.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
</tr>


<tr>
<td><spring:message code="mdrtb.rifResult" text="rifResult"/>:</td>
<td>
<select name="rifResult">
<option value=""></option>
<c:forEach var="result" items="${rifresults}">
	<option value="${result.answerConcept.id}" <c:if test="${hain.rifResult == result.answerConcept}">selected</c:if> >${result.answerConcept.displayString}</option>
</c:forEach>
</select>
</td>
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