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

    mywindow.document.write('<html><head><title><spring:message code="mdrtb.tb03" text="TB03"/></title>');
    mywindow.document.write('</head><body >');
   // mywindow.document.write('<h1><spring:message code="mdrtb.pv.aeForm" text="AE"/></h1>');
    mywindow.document.write(document.getElementById("tb03").innerHTML);
    
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
	var tableData = (document.getElementById("tb03")).innerHTML.toString();
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
    
    var input = document.createElement("INPUT");
    input.setAttribute("type", "hidden");
    input.setAttribute("id", "reportType");
    input.setAttribute("name", "reportType");
    input.setAttribute("value", "DOTSTB");
    form.appendChild(input);
    
    form.submit();
}
$(document).ready(function(){
	$("#tableToSql").bind("click", function() {
		if(confirm('<spring:message code="dotsreports.closeReportMessage" />') ) {
			savePdf("closeReport.form", "TB-03", "tb03Results");
		}
	});
	/* $("#tableToPdf").click(function(){
		savePdf("exportReport.form", "TB 03", "tb03Results");
	}); */
});
</script>
</head>
<body>
<div id="tb03">
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

	<table class="resultsTable" border="1" cellspacing="0">
	   <tr>
	     <th class="rotate" rowspan="4"><div><span><spring:message code="dotsreports.tb03.registrationNumber"/></span></div></th>
	     <th class="rotate" rowspan="4"><div><span><spring:message code="dotsreports.tb03.dateOfRegistration"/></span></div></th>
	     <th rowspan="4"><spring:message code="dotsreports.tb03.fullName"/></th>
	     <th class="rotate" rowspan="4"><div><span><spring:message code="dotsreports.tb03.gender"/></span></div></th>
	     <th class="normal" rowspan="2"><spring:message code="dotsreports.tb03.age"/></th>
	     <th rowspan="4"><spring:message code="dotsreports.tb03.address"/></th>
	     <th class="normal" rowspan="2"><spring:message code="dotsreports.tb03.mfForIP"/></th>
		 <th class="normal" rowspan="2"><spring:message code="dotsreports.tb03.treatmentRegimen"/></th>
		 <th class="rotate" rowspan="4"><div><span><spring:message code="dotsreports.tb03.tbLocalization"/></span></div></th>
		 <th class="reggroup" colspan="8" ><spring:message code="dotsreports.tb03.registrationGroup"/></th>
		 <th class="rotate" rowspan="4"><div><span><spring:message code="dotsreports.tb03.transferFrom"/></span></div></th>
		 <th class="reggroup" colspan="3" ><spring:message code="dotsreports.tb03.tbHivActivities"/></th>
		 <th class="reggroup" colspan="17" ><spring:message code="dotsreports.tb03.diagnosticTestResults"/></th>
		 <th class="normal" rowspan="2" rowspan="2"><spring:message code="dotsreports.tb03.dstSampleCollectionDate"/></th>
		 <th class="reggroup" colspan="17" rowspan="1"><spring:message code="dotsreports.tb03.dst"/></th>
		 
		 <th class="rotate" rowspan="4"><div><span><spring:message code="dotsreports.tb03.drugResistance"/></span></div></th>
		 <th class="reggroup" colspan="9" ><spring:message code="dotsreports.tb03.smearMonitoring"/></th>
		 <th class="reggroup" colspan="6" ><spring:message code="dotsreports.tb03.treatmentOutcome"/></th>
		 <th class="rotate" rowspan="4"><div><span><spring:message code="dotsreports.tb03.canceled"/></span></div></th>
		 <th class="rotate" rowspan="4"><div><span><spring:message code="dotsreports.tb03.startedRegimen2"/></span></div></th>
		 <th class="rotate" rowspan="4"><div><span><spring:message code="dotsreports.tb03.transferOut"/></span></div></th>
		 <th class="rotate" rowspan="4"><div><span><spring:message code="dotsreports.tb03.notes"/></span></div></th>
	  </tr>
	   <tr>
	   	 
	   	  <th class="subrotate" rowspan="3"><div><span><spring:message code="dotsreports.tb03.new"/></span></div></th>
	   	 <th class="subrotate" rowspan="2"><div><span><spring:message code="dotsreports.tb03.relapseAfter"/></span></div></th>
	   	 <th class="subrotate" rowspan="2"><div><span><spring:message code="dotsreports.tb03.relapseAfter"/></span></div></th>
	   	
	   	 <th class="subrotate" rowspan="2"><div><span><spring:message code="dotsreports.tb03.defaultAfter"/></span></div></th>
	   	 <th class="subrotate" rowspan="2"><div><span><spring:message code="dotsreports.tb03.defaultAfter"/> </span></div></th>
	   	 <th class="subrotate" rowspan="2"><div><span><spring:message code="dotsreports.tb03.failureAfter"/></span></div></th>
	   	 <th class="subrotate" rowspan="2"><div><span><spring:message code="dotsreports.tb03.failureAfter"/></span></div></th>
	   	 <th class="subrotate" rowspan="3"><div><span><spring:message code="dotsreports.tb03.other"/></span></div></th>
	   	 <th class="subrotate" rowspan="2"><div><span><spring:message code="dotsreports.tb03.hivTest"/></span></div></th>
	   	 <th class="subrotate" rowspan="2"><div><span><spring:message code="dotsreports.tb03.artTest"/></span></div></th>
	   	 <th class="subrotate" rowspan="2"><div><span><spring:message code="dotsreports.tb03.cpTest"/></span></div></th>
	   	 <th class="normal" colspan="3"><spring:message code="dotsreports.tb03.microscopy"/></th>
	   	 <th class="normal" colspan="3"><spring:message code="dotsreports.tb03.genexpert"/></th>
	   	 <th class="normal" colspan="4"><spring:message code="dotsreports.tb03.hain"/></th>
	   	 <th class="normal" colspan="4"><spring:message code="mdrtb.tb03.hain2"/></th>
	   	 <th class="normal" colspan="3"><spring:message code="dotsreports.tb03.culture"/></th>
	   	 <th class="dst" rowspan="3"><div>R</div></th>
	   	 <th class="dst" rowspan="3"><div>H</div></th>
	   	 <th class="dst" rowspan="3"><div>E</div></th>
	   	 <th class="dst" rowspan="3"><div>S</div></th>
	   	 <th class="dst" rowspan="3"><div>Z</div></th>
	   	 <th class="dst" rowspan="3"><div>Km</div></th>
	   	 <th class="dst" rowspan="3"><div>Am</div></th>
	   	 <th class="dst" rowspan="3"><div>Cm</div></th>
	   	 <th class="widedst" rowspan="3"><div>Ofx/Lfx</div></th>
	   	 <th class="dst" rowspan="3"><div>Mfx</div></th>
	   	 <th class="dst" rowspan="3"><div>Pto</div></th>
	   	 <th class="dst" rowspan="3"><div>Cs</div></th>
	   	 <th class="dst" rowspan="3"><div>PAS</div></th>
	   	 <th class="dst" rowspan="3"><div>Lzd</div></th>
	   	 <th class="dst" rowspan="3"><div>Cfz</div></th>
	   	 <th class="dst" rowspan="3"><div>Bdq</div></th>
	   	 <th class="dst" rowspan="3"><div>Dlm</div></th>
	   	 <th class="normal" colspan="3"><spring:message code="dotsreports.tb03.m234"/><br style="mso-data-placement:same-cell;" /><spring:message code="dotsreports.tb03.month"/></th>
	   	 <th class="normal" colspan="3"><spring:message code="dotsreports.tb03.five"/> <spring:message code="dotsreports.tb03.month"/></th>
	   	 <th class="normal" colspan="3"><spring:message code="dotsreports.tb03.m68"/> <spring:message code="dotsreports.tb03.month"/></th>
	   	  <th class="subrotate" rowspan="3"><div><span><spring:message code="dotsreports.tb03.cured"/></span></div></th>
	   	   <th class="subrotate" rowspan="3"><div><span><spring:message code="dotsreports.tb03.txCompleted"/></span></div></th>
	   	   <th class="subrotate" rowspan="3"><div><span><spring:message code="dotsreports.tb03.failure"/></span></div></th>
	   	 <th class="normal" colspan="2"><spring:message code="dotsreports.tb03.died"/></th>
	   	  <th class="subrotate" rowspan="3"><div><span><spring:message code="dotsreports.tb03.ltfu"/></span></div></th>
	   	 
	   </tr>
	   <tr>
	       <th class="normal" rowspan="2"><spring:message code="dotsreports.tb03.dateOfBirth"/></th>
	   	 <th class="normal" rowspan="2"><spring:message code="dotsreports.tb03.mfForFP"/></th>
	   	 <th class="normal" rowspan="2"><spring:message code="dotsreports.tb03.treatmentStartDate"/></th>
	   	 <!-- <th class="normal" rowspan="1">I</th>
	   	 <th class="normal" rowspan="1">II</th>
	   	 <th class="normal" rowspan="1">I</th>
	   	 <th class="normal" rowspan="1">II</th>
	   	 <th class="normal" rowspan="1">I</th>
	   	 <th class="normal" rowspan="1">II</th> -->
	   	
	       <th class="normal" colspan="3"><spring:message code="dotsreports.tb03.microscopyResult"/></th>
	   	   <th class="normal" colspan="3"><spring:message code="dotsreports.tb03.xpertResult"/></th>
	   	   <th class="normal"><spring:message code="dotsreports.tb03.date"/></th>
	   	   <th class="normal" colspan="3"><spring:message code="dotsreports.tb03.hainCultureResult"/></th>
	   	   <th class="normal"><spring:message code="dotsreports.tb03.date"/></th>
	   	   <th class="normal" colspan="3"><spring:message code="dotsreports.tb03.hainCultureResult"/></th>
	   	   <th class="normal" colspan="3"><spring:message code="dotsreports.tb03.hainCultureResult"/></th>
	   	   <th class="normal" rowspan="2"><spring:message code="dotsreports.tb03.dstResultDate"/></th>
	   	    
	   	   <th class="normal" colspan="3"><spring:message code="dotsreports.tb03.result"/></th>
	   	   <th class="normal" colspan="3"><spring:message code="dotsreports.tb03.result"/></th>
	   	   <th class="normal" colspan="3"><spring:message code="dotsreports.tb03.result"/></th>
	   	
	   	   <th class="subrotate" rowspan="2"><div><span><spring:message code="dotsreports.tb03.ofTb"/></span></div></th>
	   	   <th class="subrotate" rowspan="2"><div><span><spring:message code="dotsreports.tb03.ofOther"/></span></div></th>
	   	   
	   	    
	   	 
	   	   
	   	   
	   </tr>
	   <tr>
	        <th class="normal" rowspan="1">I</th>
	   	 <th class="normal" rowspan="1">II</th>
	   	 <th class="normal" rowspan="1">I</th>
	   	 <th class="normal" rowspan="1">II</th>
	   	 <th class="normal" rowspan="1">I</th>
	   	 <th class="normal" rowspan="1">II</th>
	        <th class="normal"><spring:message code="dotsreports.tb03.date"/></th>
	       <th class="normal"><spring:message code="dotsreports.tb03.startDate"/></th>
	       <th class="normal"><spring:message code="dotsreports.tb03.startDate"/></th>
	       
	        <th><spring:message code="dotsreports.tb03.date"/></th>
	   		<th><spring:message code="dotsreports.tb03.testNumber"/></th>
	   		<th><spring:message code="mdrtb.lab"/></th>
	   		<th><spring:message code="dotsreports.tb03.date"/></th>
	   		<th><spring:message code="dotsreports.tb03.testNumber"/></th>
	   		<th><spring:message code="mdrtb.lab"/></th>
	   		<th><spring:message code="dotsreports.tb03.testNumber"/></th>
	   		<th><spring:message code="dotsreports.tb03.hResult"/></th>
	   		<th><spring:message code="dotsreports.tb03.rResult"/></th>
	   		<th><spring:message code="mdrtb.lab"/></th>
	   		<th><spring:message code="mdrtb.tb03.testNumber"/></th>
	   		<th><spring:message code="mdrtb.tb03.iResult"/></th>
	   		<th><spring:message code="mdrtb.tb03.fResult"/></th>
	   		<th><spring:message code="mdrtb.lab"/></th>
	   		<th><spring:message code="dotsreports.tb03.date"/></th>
	   		<th><spring:message code="dotsreports.tb03.testNumber"/></th>
	   		<th><spring:message code="mdrtb.lab"/></th>
	   		   
	   	  <!--  <th class="normal">Date</th>
	   	   <th class="normal">Date</th>
	   	   <th class="normal">Date</th>
	   	   <th class="normal">Date</th>
	   	   <th class="normal">Date</th>
	   	   <th class="normal">Date</th>
	   	   <th class="normal">Date</th>
	   	   <th class="normal">Date</th>
	   	   <th class="normal">Date</th>
	   	   <th class="normal">Date</th>
	   	   <th class="normal">Date</th>
	   	   <th class="normal">Date</th>
	   	   <th class="normal">Date</th>
	   	   <th class="normal">Date</th>
	   	    -->
	   		<th><spring:message code="dotsreports.tb03.date"/></th>
	   		<th><spring:message code="dotsreports.tb03.testNumber"/></th>
	   		<th><spring:message code="mdrtb.lab"/></th>
	   		<th><spring:message code="dotsreports.tb03.date"/></th>
	   		<th><spring:message code="dotsreports.tb03.testNumber"/></th>
	   		<th><spring:message code="mdrtb.lab"/></th>
	   		<th><spring:message code="dotsreports.tb03.date"/></th>
	   		<th><spring:message code="dotsreports.tb03.testNumber"/></th>
	   		<th><spring:message code="mdrtb.lab"/></th>
	   </tr>
	<c:forEach var="row" items="${patientSet}" varStatus="loopVar" >
	 <tr <c:if test="${loopVar.index%2==0}"> bgcolor="#D3D3D3" </c:if>>
	 <td rowspan="2"><div><span>${row.identifier}</span></div></td>
	 <td rowspan="2">${row.tb03RegistrationDate}</td>
	 <td rowspan="2">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</td>
	 <td rowspan="2" align="center">${row.gender}</td>
	 <td align="center">${row.ageAtTB03Registration }</td>
	 <td rowspan="2">${row.patient.personAddress.stateProvince},<br/>${row.patient.personAddress.countyDistrict},${row.patient.personAddress.address1}</td> 
	 <td>${row.intensivePhaseFacility }</td>
	 <td>${row.treatmentRegimen }</td>
	 <td align="center" rowspan="2">${row.siteOfDisease}</td>
	 <c:forEach begin="0" end="8" varStatus="loop">
	    <td align="center" rowspan="2">
	      <c:if test="${row.regGroup == loop.index }">&#10004;</c:if>
	    </td>
	</c:forEach>
	 <td>${row.hivTestResult }</td>
	 <td rowspan="2">${row.artStartDate }</td>
	 <td rowspan="2">${row.cpStartDate }</td>
	 
	 <td colspan="3" align="center">${row.diagnosticSmearResult }</td>
	 <td colspan="3" align="center">${row.xpertMTBResult } ${row.xpertRIFResult } </td>
	 <td align="center">${row.hainTestDate } </td>
	 <td colspan="3" align="center">${row.hainMTBResult }</td>
	  <td align="center">${row.hain2TestDate } </td>
	 <td colspan="3" align="center">${row.hain2MTBResult }</td>
	  <td colspan="3" align="center">${row.cultureResult }</td>
	  <td>${ row.dstCollectionDate}</td>
	  <td rowspan="2" align="center">${ row.dstR}</td>
	  <td rowspan="2" align="center">${ row.dstH }</td>
	 <td rowspan="2" align="center">${ row.dstE }</td>
	  <td rowspan="2" align="center">${ row.dstS }</td>
	  <td rowspan="2" align="center">${ row.dstZ }</td>
	  <td rowspan="2" align="center">${ row.dstKm }</td>
	 <td rowspan="2" align="center">${ row.dstAm }</td>
	 <td rowspan="2" align="center">${ row.dstCm }</td>
	  <td rowspan="2" align="center">${ row.dstOfx }</td>
	  <td rowspan="2" align="center">${ row.dstMfx }</td>
	 <td rowspan="2" align="center">${ row.dstPto }</td>
	 <td rowspan="2" align="center">${ row.dstCs }</td>
	  <td rowspan="2" align="center">${ row.dstPAS }</td>
	  <td rowspan="2" align="center">${ row.dstLzd }</td>
	 <td rowspan="2" align="center">${ row.dstCfz }</td>
	 <td rowspan="2" align="center">${ row.dstBdq}</td>
	 <td rowspan="2" align="center">${ row.dstDlm }</td>
	  
	  <td rowspan="2">${row.drugResistance }</td>
	  <c:choose>
	 	 <c:when test="${row.reg1New}">
	 	    <c:choose>
	 	    	<c:when test="${row.month2SmearResult != null || row.month3SmearResult!=null }">
	  				<td colspan="3" align="center">${row.month2SmearResult} / ${row.month3SmearResult}</td>
	  			</c:when>
	  			<c:otherwise>
	  			   <td colspan="3">&nbsp;</td>
	  			</c:otherwise>
	  		</c:choose>
	  		<td colspan="3" align="center">${row.month5SmearResult}</td>
	  		<td colspan="3" align="center">${row.month6SmearResult}</td>
	  	</c:when>
	  	<c:when test="${row.reg1Rtx}">
	  		<c:choose>
	  			<c:when test="${row.month3SmearResult != null || row.month4SmearResult!=null }">
	  				<td colspan="3" align="center">${row.month3SmearResult} / ${row.month4SmearResult}</td>
	  			</c:when>
	  			<c:otherwise>
	  			   <td colspan="3">&nbsp;</td>
	  			</c:otherwise>
	  		</c:choose>
	  		<td colspan="3" align="center">${row.month5SmearResult}</td>
	  		<td colspan="3" align="center">${row.month8SmearResult}</td>
	  	</c:when>
	  	<c:otherwise>
	  	<td colspan="3">&nbsp;</td>
	  	<td colspan="3">&nbsp;</td>
	  	<td colspan="3">&nbsp;</td>
	  	</c:otherwise>
	  </c:choose>
	  <c:forEach begin="0" end="8" varStatus="loop">
	    <td align="center" rowspan="2">
	      <c:if test="${row.tb03TreatmentOutcome == loop.index }">${row.tb03TreatmentOutcomeDate}</c:if>
	    </td>
	</c:forEach>
	  <td rowspan="2">${row.notes }</td>
	 </tr>
	  <tr <c:if test="${loopVar.index%2==0}"> bgcolor="#D3D3D3" </c:if>>
	 	<td>${row.dateOfBirth}</td>
	 	<td>${row.continuationPhaseFacility }</td>
	 	<td>${row.tb03TreatmentStartDate }</td>
	 	<td>${row.hivTestDate }</td>
	 	<td>${row.diagnosticSmearDate }</td>
	 	<td>${row.diagnosticSmearTestNumber }</td>
	 	<td>${row.diagnosticSmearLab }</td>
	 	<td>${row.xpertTestDate }</td>
	 	<td>${row.xpertTestNumber }</td>
	 	<td>${row.xpertLab }</td>
	 	<td>${row.hainTestNumber }</td>
	 	<td align="center">${row.hainINHResult }</td>
	 	<td align="center">${row.hainRIFResult }</td>
	 	<td>${row.hainLab }</td>
	 	<td>${row.hain2TestNumber }</td>
	 	<td align="center">${row.hain2InjResult }</td>
	 	<td align="center">${row.hain2FqResult }</td>
	 	<td>${row.hain2Lab }</td>
	 	<td>${row.cultureTestDate }</td>
	 	<td>${row.cultureTestNumber }</td>
	 	<td>${row.cultureLab }</td>
	 	<td>${ row.dstResultDate}</td>
	 	 <c:choose>
	 	 <c:when test="${row.reg1New}">
	 	 	<c:choose>
	 	 		<c:when test="${row.month2SmearDate != null || row.month3SmearDate != null }">
	  				<td align="center">${row.month2SmearDate} / ${row.month3SmearDate}</td>
	  				<td align="center">${row.month2TestNumber} / ${row.month3TestNumber}</td>
	  				<td align="center">${row.month2TestLab} / ${row.month3TestLab}</td>
	  			</c:when>
	  			<c:otherwise>
	  				<td>&nbsp;</td>
	  				<td>&nbsp;</td>
	  				<td>&nbsp;</td>
	  			</c:otherwise>
	  		</c:choose>
	  		<td>${row.month5SmearDate}</td>
	  		<td>${row.month5TestNumber}</td>
	  		<td>${row.month5TestLab}</td>
	  		<td>${row.month6SmearDate}</td>
	  		<td>${row.month6TestNumber}</td>
	  		<td>${row.month6TestLab}</td>
	  	</c:when>
	  	<c:when test="${row.reg1Rtx}">
	  		<c:choose>
	  			<c:when test="${row.month3SmearDate!=null || row.month4SmearDate!=null}">
	  				<td align="center">${row.month3SmearDate} / ${row.month4SmearDate}</td>
	  				<td align="center">${row.month3TestNumber} / ${row.month4TestNumber}</td>
	  				<td align="center">${row.month3TestLab} / ${row.month4TestLab}</td>
	  			</c:when>
	  			<c:otherwise>
	  				<td>&nbsp;</td>
	  				<td>&nbsp;</td>
	  				<td>&nbsp;</td>
	  			</c:otherwise>
	  		</c:choose>
	  		<td>${row.month5SmearDate}</td>
	  		<td>${row.month5TestNumber}</td>
	  		<td>${row.month5TestLab}</td>
	  		<td>${row.month8SmearDate}</td>
	  		<td>${row.month8TestNumber}</td>
	  		<td>${row.month8TestLab}</td>
	  	</c:when>
	  	<c:otherwise>
	  	<td>&nbsp;</td>
	  	<td>&nbsp;</td>
	  	<td>&nbsp;</td>
	  	<td>&nbsp;</td>
	  	<td>&nbsp;</td>
	  	<td>&nbsp;</td>
	  	<td>&nbsp;</td>
	  	<td>&nbsp;</td>
	  	<td>&nbsp;</td>
	  	</c:otherwise>
	  </c:choose>
	 </tr>
	</c:forEach>
	
	</table>

	<c:if test="${locale == 'tj' }"></font></c:if>
</div>

<input type="button" onclick="tableToExcel('tb03', 'TB03')" value="<spring:message code='dotsreports.exportToExcelBtn' />" />
<!-- <input type="button" id="tableToPdf" name="tableToPdf" value="<spring:message code='dotsreports.exportToPdfBtn' />" /> -->
<openmrs:hasPrivilege privilege="Manage Report Closing">
<input type="button" id="tableToSql" name="tableToSql" value="<spring:message code='dotsreports.closeReportBtn' />" />
</openmrs:hasPrivilege>
<input type="button" id="back" name="back" value="<spring:message code='mdrtb.back' />" onclick="document.location.href='${pageContext.request.contextPath}/module/mdrtb/mdrtbIndex.form';" />
<input type="button" onclick="printForm()" value="<spring:message code='mdrtb.print' />" />

<script> 
	console.log("${reportStatus}");
	if("${reportStatus}" === "true") { 
		document.getElementById("tableToSql").disabled = true; 
	} else { 
		document.getElementById("tableToSql").disabled = false; 
	}
</script>
<%@ include file="../mdrtbFooter.jsp"%>
