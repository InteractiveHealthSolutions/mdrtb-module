<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%>
<%@ include file="../mdrtbHeader.jsp"%>
<meta http-equiv="content-type" content="text/plain; charset=UTF-8"/>
<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/tableExport/js/tableExport.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/tableExport/js/jquery.base64.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/tableExport/js/jspdf/libs/sprintf.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/tableExport/js/jspdf/jspdf.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/tableExport/js/jspdf/libs/base64.js"></script>

<script type="text/javascript">
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
function savePdf(action, reportName, formPath) {
	var tableData = (document.getElementById("dq")).innerHTML.toString();
	var oblast = "${oblast}";
	var district = "${district}";
	var facility = "${facility}";
	var year = "${year}";
	<c:choose>
	<c:when test="${! empty quarter}">
		var quarter =  "\"" + ${quarter} + "\"";
	</c:when>
	<c:otherwise>
		var quarter =  "";
	</c:otherwise>
	</c:choose>
	
	<c:choose>
	<c:when test="${! empty month}" >
		var month =  "\"" + ${month} + "\"";
	</c:when>
	<c:otherwise>
		var month =  "";
	</c:otherwise>
    </c:choose>
	var reportDate = "${reportDate}";
	
	var form = document.createElement("FORM");

	form.setAttribute("id", "closeReportForm");
    form.setAttribute("name", "closeReportForm");
    form.setAttribute("method", "post");
    form.setAttribute("action", action);
    
    document.body.appendChild(form);
    
    var input = document.createElement("INPUT");
    input.setAttribute("type", "hidden");
    input.setAttribute("id", "oblast");
    input.setAttribute("name", "oblast");
    input.setAttribute("value", oblast);
    form.appendChild(input);

    var input = document.createElement("INPUT");
    input.setAttribute("type", "hidden");
    input.setAttribute("id", "district");
    input.setAttribute("name", "district");
    input.setAttribute("value", district);
    form.appendChild(input);
    
    var input = document.createElement("INPUT");
    input.setAttribute("type", "hidden");
    input.setAttribute("id", "facility");
    input.setAttribute("name", "facility");
    input.setAttribute("value", facility);
    form.appendChild(input);
    
    var input = document.createElement("INPUT");
    input.setAttribute("type", "hidden");
    input.setAttribute("id", "year");
    input.setAttribute("name", "year");
    input.setAttribute("value", year);
    form.appendChild(input);

    var input = document.createElement("INPUT");
    input.setAttribute("type", "hidden");
    input.setAttribute("id", "quarter");
    input.setAttribute("name", "quarter");
    input.setAttribute("value", quarter);
    form.appendChild(input);

    var input = document.createElement("INPUT");
    input.setAttribute("type", "hidden");
    input.setAttribute("id", "month");
    input.setAttribute("name", "month");
    input.setAttribute("value", month);
    form.appendChild(input);
    
    var input = document.createElement("INPUT");
    input.setAttribute("type", "hidden");
    input.setAttribute("id", "reportDate");
    input.setAttribute("name", "reportDate");
    input.setAttribute("value", reportDate);
    form.appendChild(input);

    var input = document.createElement("INPUT");
    input.setAttribute("type", "hidden");
    input.setAttribute("id", "table");
    input.setAttribute("name", "table");
    input.setAttribute("value", tableData);
    form.appendChild(input);

    var input = document.createElement("INPUT");
    input.setAttribute("type", "hidden");
    input.setAttribute("id", "formPath");
    input.setAttribute("name", "formPath");
    input.setAttribute("value", formPath);
    form.appendChild(input);

    var input = document.createElement("INPUT");
    input.setAttribute("type", "hidden");
    input.setAttribute("id", "reportName");
    input.setAttribute("name", "reportName");
    input.setAttribute("value", reportName);
    form.appendChild(input);
    
    form.submit();
}
$(document).ready(function(){
	$("#tableToSql").bind("click", function() {
		if(confirm('<spring:message code="dotsreports.closeReportMessage" />') ) {
			savePdf("closeReport.form", "DOTSDQ", "dqResults");
		}
	});
	/* $("#tableToPdf").click(function(){
		savePdf("exportReport.form", "DQ", "dqResults");
	}); */
});
</script>
</head>
<body>

<div id="dq">
	<c:if test="${locale == 'tj'}">
		<style> html * {
		   font-family: Times New Roman Tj !important;
		}
		</style>
	</c:if>
	
	<style>
		th.rotate {
		  /* Something you can count on */
		  height: 350px;
		  white-space: nowrap;
		  valign: middle;
		}
		
		th.rotate > div {
		  transform: 
		    /* Magic Numbers */
		    translate(0px, 120px)
		    /* 45 is really 360 - 45 */
		    rotate(270deg);
		  width: 30px;
		  align: centre;
		}
		
		td.rotate {
		  /* Something you can count on */
		  height: 150px;
		  white-space: nowrap;
		  valign: middle;
		}
		
		td.rotate > div {
		  transform: 
		    /* Magic Numbers */
		    translate(0px, 100px)
		    /* 45 is really 360 - 45 */
		    rotate(270deg);
		  width: 30px;
		  align: centre;
		}
		
		th.subrotate {
		  /* Something you can count on */
		  white-space: nowrap;
		  valign: middle;
		}
		
		th.subrotate > div {
		  transform: 
		    /* Magic Numbers */
		    translate(0px, 65px)
		    /* 45 is really 360 - 45 */
		    rotate(270deg);
		  width: 50px;
		  align: centre;
		}
		
		th.dst {
		  valign: middle;
		}
		
		th.dst > div {
		  width: 30px;
		}
		
		th.widedst {
		  valign: middle;
		}
		
		th.widedst > div {
		  width: 55px;
		}
		
		th.normal {
		  /* Something you can count on */
		  white-space: nowrap;
		  valign: middle;
		}
		
		th.reggroup {
		  /* Something you can count on */
		  height: 50px;
		  white-space: nowrap;
		  valign: middle;
		}
		
		
		table.resultsTable {
			border-collapse: collapse;
		}
		
		table.resultsTable td, table.resultsTable th {
			border-top: 1px black solid;
			border-bottom: 1px black solid;
			border-right: 1px black solid;
			border-left: 1px black solid;
		}
	</style>

	<table class="resultsTable">
	   	<tr>
	   		<th class=normal colspan="3"><spring:message code="dotsreports.dq.title" /></th>
	  	</tr>
		<tr>
			<th colspan="2"><spring:message code="dotsreports.oblast" /></td>
			<td>${oblast}</td>
		</tr>
		<tr>
			<th colspan="2"><spring:message code="dotsreports.district" /></td>
			<td>${location}</td>
		</tr>
	  	<tr>
	     	<th colspan="2"><spring:message code="dotsreports.year" /></td>
	     	<td align="center">${year}</td>
	  	</tr>
	  	<tr>
	     	<th colspan="2"><spring:message code="dotsreports.quarter" /></td>
	     	<td align="center">${quarter}</td>
	  	</tr>
	   	<tr>
	     	<th colspan="2"><spring:message code="dotsreports.month" /></td>
	     	<td align="center">${month}</td>
	  	</tr>
	  	<tr>
	     	<th colspan="2"><spring:message code="dotsreports.dq.numberOfPatients" /></td>
	     	<td align="right">${num}</td>
	  	</tr>
	  	<tr>
	     	<th colspan="2"><spring:message code="dotsreports.dq.numberWithErrors" /></td>
	     	<td align="right">${errorCount}</td>
	  	</tr>
	  	<tr>
	     	<th colspan="2"><spring:message code="dotsreports.dq.errorPercentage" /></td>
	     	<td align="right">${errorPercentage}</td>
	  	</tr>
	  	<tr><td colspan="3">&nbsp;</td></tr>
	    <tr><td colspan="3">&nbsp;</td></tr>
	  	<tr>
	   		<th class=normal colspan="3"><spring:message code="dotsreports.dq.missingtb03" /></th>
	  	</tr>
	  	<tr>
	     	<td><spring:message code="dotsreports.dq.fullName" /></td>
	     	<td><spring:message code="dotsreports.dq.dob" /></td>
	     	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${missingTB03}">
	    	<tr>
	        
	        	<td><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
	        	<td>${row.dateOfBirth}</td>
	         	<td align="center">${row.patient.gender}</td>
	    	</tr>  
	   	</c:forEach>
	    <tr><td colspan="3">&nbsp;</td></tr>
	    <tr><td colspan="3">&nbsp;</td></tr>
	    <tr>
	   		<th class=normal colspan="3"><spring:message code="dotsreports.dq.missingAge" /></th>
	  	</tr>
	  	<tr>
	     	<td><spring:message code="dotsreports.dq.fullName" /></td>
	     	<td><spring:message code="dotsreports.dq.dob" /></td>
	     	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${missingAge}">
	    	<tr>
	        
	        	<td><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
	        	<td>${row.dateOfBirth}</td>
	         	<td align="center">${row.patient.gender}</td>
	    	</tr>  
	  
	  	</c:forEach>
	  	<tr><td colspan="3">&nbsp;</td></tr>
	  	<tr><td colspan="3">&nbsp;</td></tr>
	  	<tr>
	   		<th class=normal colspan="3"><spring:message code="dotsreports.dq.missingPatientGroup" /></th>
	  	</tr>
	  	<tr>
	     	<td><spring:message code="dotsreports.dq.fullName" /></td>
	     	<td><spring:message code="dotsreports.dq.dob" /></td>
	     	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${missingPatientGroup}">
	    	<tr>
	        
	        	<td><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
	        	<td>${row.dateOfBirth}</td>
	         	<td align="center">${row.patient.gender}</td>
	    	</tr>  
	  
	  	</c:forEach>
	   	<tr><td colspan="3">&nbsp;</td></tr>
	  	<tr><td colspan="3">&nbsp;</td></tr>
	  	<tr>
	   		<th class=normal colspan="3"><spring:message code="dotsreports.dq.missingDiagnosticTests" /></th>
	  	</tr>
	  	<tr>
	     	<td><spring:message code="dotsreports.dq.fullName" /></td>
	    	<td><spring:message code="dotsreports.dq.dob" /></td>
	    	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${missingDiagnosticTests}">
		   	<tr>
		        
		        <td><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
		        <td>${row.dateOfBirth}</td>
				<td align="center">${row.patient.gender}</td>
		    </tr>  
	  
	  	</c:forEach>
	  	<tr><td colspan="3">&nbsp;</td></tr>
	  	<tr><td colspan="3">&nbsp;</td></tr>
	  	<tr>
	   		<th class=normal colspan="3"><spring:message code="dotsreports.dq.notStartedTreatment" /></th>
	  	</tr>
	  	<tr>
	     	<td><spring:message code="dotsreports.dq.fullName" /></td>
	     	<td><spring:message code="dotsreports.dq.dob" /></td>
	     	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${notStartedTreatment}">
		    <tr>
		        
		        <td><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
		        <td>${row.dateOfBirth}</td>
		        <td align="center">${row.patient.gender}</td>
		    </tr>  
	  	
	  	</c:forEach>
	   	<tr><td colspan="3">&nbsp;</td></tr>
	  	<tr><td colspan="3">&nbsp;</td></tr>
	  	<tr>
	   		<th class=normal colspan="3"><spring:message code="dotsreports.dq.missingOutcomes" /></th>
	  	</tr>
	  	<tr>
	     	<td><spring:message code="dotsreports.dq.fullName" /></td>
	     	<td><spring:message code="dotsreports.dq.dob" /></td>
	     	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${missingOutcomes}">
	    	<tr>
				
	        	<td><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
	        	<td>${row.dateOfBirth}</td>
	        	<td align="center">${row.patient.gender}</td>
	    	</tr>  
	  
	  	</c:forEach>
	   	<tr><td colspan="3">&nbsp;</td></tr>
	  	<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
			<th class=normal colspan="3"><spring:message code="dotsreports.dq.noDOTSId" /></th>
		</tr>
		<tr>
			<td><spring:message code="dotsreports.dq.fullName" /></td>
			<td><spring:message code="dotsreports.dq.dob" /></td>
			<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${noDOTSId}">
	    	<tr>
	        
	        	<td><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
	        	<td>${row.dateOfBirth}</td>
	         	<td align="center">${row.patient.gender}</td>
	    	</tr>  
	  
	  	</c:forEach>
	   	<tr><td colspan="3">&nbsp;</td></tr>
	  	<tr><td colspan="3">&nbsp;</td></tr>
		<tr>
			<th class=normal colspan="3"><spring:message code="dotsreports.dq.noSite" /></th>
		</tr>
		<tr>
			<td><spring:message code="dotsreports.dq.fullName" /></td>
			<td><spring:message code="dotsreports.dq.dob" /></td>
			<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
		</tr>
		<c:forEach var="row" items="${noSite}">
			<tr>
		        
				<td><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
				<td>${row.dateOfBirth}</td>
				<td align="center">${row.patient.gender}</td>
			</tr>  
		</c:forEach>
		<tr><td colspan="3">&nbsp;</td></tr>
	  	<tr><td colspan="3">&nbsp;</td></tr>
	  	
	  	
		<tr>
			<th class=normal colspan="3"><spring:message code="mdrtb.dq.transferOutError" /></th>
		</tr>
		<tr>
			<td><spring:message code="dotsreports.dq.fullName" /></td>
			<td><spring:message code="dotsreports.dq.dob" /></td>
			<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
		</tr>
		<c:forEach var="row" items="${transferOutError}">
			<tr>
		        
				<td><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
				<td>${row.dateOfBirth}</td>
				<td align="center">${row.patient.gender}</td>
			</tr>  
		</c:forEach>
	</table>
</div>

<c:if test="${locale == 'tj' }"></font></c:if>

<input type="button" onclick="tableToExcel('dq', 'DQ')" value="<spring:message code='dotsreports.exportToExcelBtn' />" />
<!-- <input type="button" id="tableToPdf" name="tableToPdf" value="<spring:message code='dotsreports.exportToPdfBtn' />" /> -->
<openmrs:hasPrivilege privilege="Manage Report Closing">
<input type="button" id="tableToSql" name="tableToSql" value="<spring:message code='dotsreports.closeReportBtn' />" />
</openmrs:hasPrivilege>
<input type="button" id="back" name="back" value="<spring:message code='mdrtb.back' />" onclick="document.location.href='${pageContext.request.contextPath}/module/mdrtb/mdrtbIndex.form';" />

<script> 
	if("${reportStatus}" === "true") { 
		document.getElementById("tableToSql").disabled = true; 
	} else { 
		document.getElementById("tableToSql").disabled = false; 
	}
</script>

<%@ include file="../mdrtbFooter.jsp"%>
