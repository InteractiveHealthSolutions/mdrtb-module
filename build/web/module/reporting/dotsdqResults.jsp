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
function printForm() {
	var mywindow = window.open('', 'PRINT', 'height=400,width=600');

    mywindow.document.write('<html><head><title><spring:message code="dotsreports.dq.title" text="dq"/></title>');
    mywindow.document.write('</head><body >');
   // mywindow.document.write('<h1><spring:message code="mdrtb.pv.aeForm" text="AE"/></h1>');
    mywindow.document.write(document.getElementById("dq").innerHTML);
    
    mywindow.document.write('</body></html>');

    mywindow.document.close(); // necessary for IE >= 10
    mywindow.focus(); // necessary for IE >= 10*/

    mywindow.print();
    mywindow.close();

    return true;
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
	   		<th class=normal colspan="4"><spring:message code="dotsreports.dq.title" /></th>
	  	</tr>
		<tr>
			<th colspan="2"><spring:message code="mdrtb.oblast" /></td>
			<td colspan="2">${oName}</td>
		</tr>
		<tr>
			<th colspan="2"><spring:message code="mdrtb.district" /></td>
			<td colspan="2">${dName}</td>
		</tr>
		<tr>
			<th colspan="2"><spring:message code="mdrtb.facility" /></td>
			<td colspan="2">${fName}</td>
		</tr>
	  	<tr>
	     	<th colspan="2"><spring:message code="dotsreports.year" /></td>
	     	<td  colspan="2" align="right">${year}</td>
	  	</tr>
	  	<tr>
	     	<th colspan="2"><spring:message code="dotsreports.quarter" /></td>
	     	<td align="right"  colspan="2">${quarter}</td>
	  	</tr>
	   	<tr>
	     	<th colspan="2"><spring:message code="dotsreports.month" /></td>
	     	<td align="right"  colspan="2">${month}</td>
	  	</tr>
	  	<tr>
	     	<th colspan="2"><spring:message code="dotsreports.dq.numberOfPatients" /></td>
	     	<td align="right"  colspan="2">${num}</td>
	  	</tr>
	  	<tr>
	     	<th colspan="2"><spring:message code="dotsreports.dq.numberWithErrors" /></td>
	     	<td align="right"  colspan="2">${errorCount}</td>
	  	</tr>
	  	<tr>
	     	<th colspan="2"><spring:message code="dotsreports.dq.errorPercentage" /></td>
	     	<td align="right"  colspan="2">${errorPercentage}</td>
	  	</tr>
	  	<tr><td colspan="4">&nbsp;</td></tr>
	    <tr><td colspan="4">&nbsp;</td></tr>
	  	<%-- <tr>
	   		<th class=normal colspan="4"><spring:message code="dotsreports.dq.missingtb03" /></th>
	  	</tr>
	  	<tr>
	     	<td colspan="2"><spring:message code="dotsreports.dq.fullName" /></td>
	     	<td><spring:message code="dotsreports.dq.dob" /></td>
	     	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${missingTB03}">
	    	<tr>
	        
	        	<td colspan="2"><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
	        	<td>${row.dateOfBirth}</td>
	         	<td align="center">${row.gender}</td>
	    	</tr>  
	   	</c:forEach>
	   	
	   	<tr><td colspan="4">&nbsp;</td></tr>
	    <tr><td colspan="4">&nbsp;</td></tr> --%>
	    
	    <tr>
	   		<th class=normal colspan="4"><spring:message code="mdrtb.dq.duplicateForm89" /></th>
	  	</tr>
	  	<tr>
	     	<td><spring:message code="dotsreports.dq.fullName" /></td>
	     	<td><spring:message code="dotsreports.dq.dob" /></td>
	     	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	     	<td align="center"><spring:message code="mdrtb.dq.links" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${duplicateForm89}">
	    	<tr>
	        
	        	<td><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
	        	<td>${row.dateOfBirth}</td>
	         	<td align="center">${row.gender}</td>
	         	<td>
	         	<c:forEach var="link" items="${row.form89Links }">
	         	    <a href="${pageContext.request.contextPath}${link}" target="_blank"><spring:message code="mdrtb.form89" /> </a>&nbsp;
	         	</c:forEach>
	         	</td>
	    	</tr>  
	   	</c:forEach>
	   	
	   	<tr><td colspan="4">&nbsp;</td></tr>
	    <tr><td colspan="4">&nbsp;</td></tr>
	    
	     <tr>
	   		<th class=normal colspan="4"><spring:message code="mdrtb.dq.duplicateTB03" /></th>
	  	</tr>
	  	<tr>
	     	<td><spring:message code="dotsreports.dq.fullName" /></td>
	     	<td><spring:message code="dotsreports.dq.dob" /></td>
	     	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	     	<td align="center"><spring:message code="mdrtb.dq.links" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${duplicateTB03}">
	    	<tr>
	        
	        	<td><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
	        	<td>${row.dateOfBirth}</td>
	         	<td align="center">${row.gender}</td>
	         	<td>
	         	<c:forEach var="link" items="${row.tb03Links }">
	         	    <a href="${pageContext.request.contextPath}${link}" target="_blank"><spring:message code="mdrtb.tb03" /> </a>&nbsp;
	         	</c:forEach>
	         	</td>
	    	</tr>  
	   	</c:forEach>
	   	
	   	<tr><td colspan="4">&nbsp;</td></tr>
	    <tr><td colspan="4">&nbsp;</td></tr>
	    
	  	<tr>
	   		<th class=normal colspan="4"><spring:message code="mdrtb.dq.missingForm89" /></th>
	  	</tr>
	  	<tr>
	     	<td colspan="2"><spring:message code="dotsreports.dq.fullName" /></td>
	     	<td><spring:message code="dotsreports.dq.dob" /></td>
	     	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${missingForm89}">
	    	<tr>
	        
	        	<td colspan="2"><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
	        	<td>${row.dateOfBirth}</td>
	         	<td align="center">${row.gender}</td>
	    	</tr>  
	   	</c:forEach>
	   	
	   	 <tr><td colspan="4">&nbsp;</td></tr>
	    <tr><td colspan="4">&nbsp;</td></tr>
	    <tr>
	   		<th class=normal colspan="4"><spring:message code="mdrtb.dq.invalidForm89" /></th>
	  	</tr>
	  	<tr>
	     	<td colspan="2"><spring:message code="dotsreports.dq.fullName" /></td>
	     	<td><spring:message code="dotsreports.dq.dob" /></td>
	     	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${invalidForm89}">
	    	<tr>
	        
	        	<td colspan="2"><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
	        	<td>${row.dateOfBirth}</td>
	         	<td align="center">${row.gender}</td>
	    	</tr>  
	  
	  	</c:forEach>
	   	
	    <tr><td colspan="4">&nbsp;</td></tr>
	    <tr><td colspan="4">&nbsp;</td></tr>
	    <tr>
	   		<th class=normal colspan="4"><spring:message code="dotsreports.dq.missingAge" /></th>
	  	</tr>
	  	<tr>
	     	<td colspan="2"><spring:message code="dotsreports.dq.fullName" /></td>
	     	<td><spring:message code="dotsreports.dq.dob" /></td>
	     	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${missingAge}">
	    	<tr>
	        
	        	<td colspan="2"><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
	        	<td>${row.dateOfBirth}</td>
	         	<td align="center">${row.gender}</td>
	    	</tr>  
	  
	  	</c:forEach>
	  	<tr><td colspan="4">&nbsp;</td></tr>
	  	<tr><td colspan="4">&nbsp;</td></tr>
	  	<tr>
	   		<th class=normal colspan="4"><spring:message code="dotsreports.dq.missingPatientGroup" /></th>
	  	</tr>
	  	<tr>
	     	<td colspan="2"><spring:message code="dotsreports.dq.fullName" /></td>
	     	<td><spring:message code="dotsreports.dq.dob" /></td>
	     	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${missingPatientGroup}">
	    	<tr>
	        
	        	<td colspan="2"><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
	        	<td>${row.dateOfBirth}</td>
	         	<td align="center">${row.gender}</td>
	    	</tr>  
	  
	  	</c:forEach>
	   	<tr><td colspan="4">&nbsp;</td></tr>
	  	<tr><td colspan="4">&nbsp;</td></tr>
	  	<tr>
	   		<th class=normal colspan="4"><spring:message code="dotsreports.dq.missingDiagnosticTests" /></th>
	  	</tr>
	  	<tr>
	     	<td colspan="2"><spring:message code="dotsreports.dq.fullName" /></td>
	    	<td><spring:message code="dotsreports.dq.dob" /></td>
	    	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${missingDiagnosticTests}">
		   	<tr>
		        
		        <td colspan="2"><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
		        <td>${row.dateOfBirth}</td>
				<td align="center">${row.gender}</td>
		    </tr>  
	  
	  	</c:forEach>
	  	<tr><td colspan="4">&nbsp;</td></tr>
	  	<tr><td colspan="4">&nbsp;</td></tr>
	  	<tr>
	   		<th class=normal colspan="4"><spring:message code="dotsreports.dq.notStartedTreatment" /></th>
	  	</tr>
	  	<tr>
	     	<td colspan="2"><spring:message code="dotsreports.dq.fullName" /></td>
	     	<td><spring:message code="dotsreports.dq.dob" /></td>
	     	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${notStartedTreatment}">
		    <tr>
		        
		        <td  colspan="2"><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
		        <td>${row.dateOfBirth}</td>
		        <td align="center">${row.gender}</td>
		    </tr>  
	  	
	  	</c:forEach>
	   	<tr><td colspan="4">&nbsp;</td></tr>
	  	<tr><td colspan="4">&nbsp;</td></tr>
	  	<tr>
	   		<th class=normal colspan="4"><spring:message code="dotsreports.dq.missingOutcomes" /></th>
	  	</tr>
	  	<tr>
	     	<td colspan="2"><spring:message code="dotsreports.dq.fullName" /></td>
	     	<td><spring:message code="dotsreports.dq.dob" /></td>
	     	<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${missingOutcomes}">
	    	<tr>
				
	        	<td colspan="2"><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
	        	<td>${row.dateOfBirth}</td>
	        	<td align="center">${row.gender}</td>
	    	</tr>  
	  
	  	</c:forEach>
	   	<tr><td colspan="4">&nbsp;</td></tr>
	  	<tr><td colspan="4">&nbsp;</td></tr>
		<tr>
			<th class=normal colspan="4"><spring:message code="dotsreports.dq.noDOTSId" /></th>
		</tr>
		<tr>
			<td colspan="2"><spring:message code="dotsreports.dq.fullName" /></td>
			<td><spring:message code="dotsreports.dq.dob" /></td>
			<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
	  	</tr>
	  	<c:forEach var="row" items="${noDOTSId}">
	    	<tr>
	        
	        	<td colspan="2"><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
	        	<td>${row.dateOfBirth}</td>
	         	<td align="center">${row.gender}</td>
	    	</tr>  
	  
	  	</c:forEach>
	   	<tr><td colspan="4">&nbsp;</td></tr>
	  	<tr><td colspan="4">&nbsp;</td></tr>
		<tr>
			<th class=normal colspan="4"><spring:message code="dotsreports.dq.noSite" /></th>
		</tr>
		<tr>
			<td colspan="2"><spring:message code="dotsreports.dq.fullName" /></td>
			<td><spring:message code="dotsreports.dq.dob" /></td>
			<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
		</tr>
		<c:forEach var="row" items="${noSite}">
			<tr>
		        
				<td colspan="2"><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
				<td>${row.dateOfBirth}</td>
				<td align="center">${row.gender}</td>
			</tr>  
		</c:forEach>
		<tr><td colspan="4">&nbsp;</td></tr>
	  	<tr><td colspan="4">&nbsp;</td></tr>
	  	
	  	
		<tr>
			<th class=normal colspan="4"><spring:message code="mdrtb.dq.noTransferIn" /></th>
		</tr>
		<tr>
			<td><spring:message code="dotsreports.dq.fullName" /></td>
			<td><spring:message code="mdrtb.location" /></td>
			<td><spring:message code="dotsreports.dq.dob" /></td>
			<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
		</tr>
		<c:forEach var="row" items="${noTransferIn}">
			<tr>
		        
				<td><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
				<td>${row.locName}</td>
				<td>${row.dateOfBirth}</td>
				<td align="center">${row.gender}</td>
			</tr>  
		</c:forEach>
		
		<tr><td colspan="4">&nbsp;</td></tr>
	  	<tr><td colspan="4">&nbsp;</td></tr>
	  	
	  	
		<tr>
			<th class=normal colspan="4"><spring:message code="mdrtb.dq.noTransferOut" /></th>
		</tr>
		<tr>
			<td><spring:message code="dotsreports.dq.fullName" /></td>
			<td><spring:message code="mdrtb.location" /></td>
			<td><spring:message code="dotsreports.dq.dob" /></td>
			<td align="center"><spring:message code="dotsreports.dq.gender" /></td>
		</tr>
		<c:forEach var="row" items="${noTransferOut}">
			<tr>
		        
				<td><a href="${pageContext.request.contextPath}/module/mdrtb/program/enrollment.form?patientId=${row.patient.id}" target="_blank">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</a></td>
				<td>${row.locName}</td>
				<td>${row.dateOfBirth}</td>
				<td align="center">${row.gender}</td>
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
	<input type="button" onclick="printForm()" value="<spring:message code='mdrtb.print' />" />
<script> 
	if("${reportStatus}" === "true") { 
		document.getElementById("tableToSql").disabled = true; 
	} else { 
		document.getElementById("tableToSql").disabled = false; 
	}
</script>

<%@ include file="../mdrtbFooter.jsp"%>
