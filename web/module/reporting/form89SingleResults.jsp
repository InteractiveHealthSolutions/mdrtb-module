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

    mywindow.document.write('<html><head><title><spring:message code="mdrtb.form89" text="Form89"/></title>');
    mywindow.document.write('</head><body >');
   // mywindow.document.write('<h1><spring:message code="mdrtb.pv.aeForm" text="AE"/></h1>');
    mywindow.document.write(document.getElementById("f89").innerHTML);
    
    mywindow.document.write('</body></html>');

    mywindow.document.close(); // necessary for IE >= 10
    mywindow.focus(); // necessary for IE >= 10*/

    mywindow.print();
    mywindow.close();

    return true;
}
var tableToExcel = (function() {
  var uri = 'data:application/vnd.ms-excel;base64,'
    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>Form 89</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta http-equiv="content-type" content="text/plain; charset=UTF-8"/></head><body><table>{table}</table></body></html>'
    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
  return function(table, name) {
    if (!table.nodeType) table = document.getElementById(table)
    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
    window.location.href = uri + base64(format(template, ctx))
  }
})()
function savePdf(action, reportName, formPath) {
	var tableData = (document.getElementById("f89")).innerHTML.toString();
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
			savePdf("closeReport.form", "form89", "form89results");
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

	<table border="1" cellspacing="0">
	   <tr>
	     <th class="normal" rowspan="2"><div><span><spring:message code="mdrtb.tb03.tb03RegistrationNumber"/></span></div></th>
	     <th class="normal" rowspan="2"><div><span><spring:message code="mdrtb.form89.form89Date"/></span></div></th>
	     <th class="normal" rowspan="2"><spring:message code="mdrtb.tb03.name"/></th>
	     <th class="normal" rowspan="2"><div><span><spring:message code="mdrtb.gender"/></span></div></th>
	     <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.ageAtRegistration"/></th>
	     <th class="normal" rowspan="2"><spring:message code="mdrtb.tb03.dateOfBirth"/></th>
	      <th class="normal" rowspan="2"><spring:message code="mdrtb.oblast"/></th>
	      <th class="normal" rowspan="2"><spring:message code="mdrtb.district"/></th>
	      <th class="normal" rowspan="2"><spring:message code="mdrtb.facility"/></th>
	     <th class="normal" rowspan="2"><spring:message code="mdrtb.tb03.address"/></th>
	     
		 <th class="normal" rowspan="2"><div><span><spring:message code="mdrtb.pregnant"/></span></div></th>
		 <th class="normal" rowspan="2" ><spring:message code="mdrtb.form89.locationType"/></th>
		 <th class="normal" rowspan="2"><div><span><spring:message code="mdrtb.form89.profession"/></span></div></th>
		 <th class="normal" rowspan="2" rowspan="2"><spring:message code="mdrtb.form89.populationCategory"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.countryOfOrigin"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.placeOfDetection"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.dateFirstSeekingHelp"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.circumstancesOfDetection"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.cityOfOrigin"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.dateOfReturn"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.methodOfDetection"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.otherMethodOfDetection"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.tb03.siteOfDisease"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.epSite"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.epLocation"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.pSite"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.presenceOfDecay"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.dateOfDecaySurvey"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.complication"/></th>
		 <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.smear"/></th>
		 <th class="normal" rowspan="1" colspan="4"><spring:message code="dotsreports.tb03.genexpert"/></th>
		 <th class="normal" rowspan="1" colspan="6"><spring:message code="mdrtb.hain"/></th>
		 <th class="normal" rowspan="1" colspan="6"><spring:message code="mdrtb.hain2"/></th>
		 <th class="normal" rowspan="1" colspan="10"><spring:message code="mdrtb.form89.comorbidities"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.cmacDate"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.cmacNumber"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.prescribedTreatment"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.placeOfCommission"/></th>
		 <th class="normal" rowspan="2"><spring:message code="mdrtb.form89.nameOfDoctor"/></th>
		 <th class="normal" rowspan="2">&nbsp;</th>

	  </tr>
	   <tr>
	   	 <th class="normal" rowspan="1"><spring:message code="mdrtb.result"/></th>
		 <th class="normal" rowspan="1"><spring:message code="mdrtb.date"/></th>
		 <th class="normal" rowspan="1"><spring:message code="mdrtb.lab"/></th>
		 <th class="normal" rowspan="1"><spring:message code="dotsreports.tb03.testNumber"/></th>
		 
	   	 <th class="normal" rowspan="1"><spring:message code="dotsreports.tb03.xpertResult"/></th>
		 <th class="normal" rowspan="1"><spring:message code="mdrtb.date"/></th>
		 <th class="normal" rowspan="1"><spring:message code="mdrtb.lab"/></th>
		 <th class="normal" rowspan="1"><spring:message code="dotsreports.tb03.testNumber"/></th>
		 
		  <th class="normal" rowspan="1"><spring:message code="dotsreports.tb03.hainCultureResult"/></th>
		  <th rowspan="1"><spring:message code="dotsreports.tb03.hResult"/></th>
	   	  <th rowspan="1"><spring:message code="dotsreports.tb03.rResult"/></th>
		  <th class="normal" rowspan="1"><spring:message code="mdrtb.date"/></th>
		  <th class="normal" rowspan="1"><spring:message code="mdrtb.lab"/></th>
		  <th class="normal" rowspan="1"><spring:message code="dotsreports.tb03.testNumber"/></th>
		  
		  <th class="normal" rowspan="1"><spring:message code="dotsreports.tb03.hainCultureResult"/></th>
		  <th class="normal" rowspan="1"><spring:message code="mdrtb.tb03.iResult"/></th>
	   	  <th class="normal" rowspan="1"><spring:message code="mdrtb.tb03.fResult"/></th>
		  <th class="normal" rowspan="1"><spring:message code="mdrtb.date"/></th>
		  <th class="normal" rowspan="1"><spring:message code="mdrtb.lab"/></th>
		  <th class="normal" rowspan="1"><spring:message code="dotsreports.tb03.testNumber"/></th>
		  
		  <th class="normal" rowspan="1"><spring:message code="mdrtb.form89.diabetes"/></th>
		  <th class="normal" rowspan="1"><spring:message code="mdrtb.form89.cnsdl"/></th>
		  <th class="normal" rowspan="1"><spring:message code="mdrtb.form89.htHeartDisease"/></th>
		  <th class="normal" rowspan="1"><spring:message code="mdrtb.form89.ulcer"/></th>
		  <th class="normal" rowspan="1"><spring:message code="mdrtb.form89.mentalDisorder"/></th>
		  <th class="normal" rowspan="1"><spring:message code="mdrtb.form89.ibc20"/></th>
		  <th class="normal" rowspan="1"><spring:message code="mdrtb.form89.cancer"/></th>
		  <th class="normal" rowspan="1"><spring:message code="mdrtb.form89.hepatitis"/></th>
		  <th class="normal" rowspan="1"><spring:message code="mdrtb.form89.kidneyDisease"/></th>
		  <th class="normal" rowspan="1"><spring:message code="mdrtb.form89.otherDisease"/></th>
	   	 
	   </tr>
	  

	
	
<c:forEach var="row" items="${patientSet}" varStatus="loopVar">
	 <tr <c:if test="${loopVar.index%2==0}"> bgcolor="#D3D3D3" </c:if>>
	 <td><div><span>${row.identifier}</span></div></td>
	 <td>${row.tb03RegistrationDate}</td>
	 <td>${row.patient.personName.familyName}, ${row.patient.personName.givenName}</td>
	 <td align="center">${row.gender}</td>
	 <td align="center">${row.ageAtTB03Registration }</td>
	 <td>${row.dateOfBirth}</td>
	 <td>${row.patient.personAddress.stateProvince}</td>
	 <td>${row.patient.personAddress.countyDistrict}</td>
	  <td>${row.patient.personAddress.region}</td>
	 <td>${row.patient.personAddress.address1}</td>
	 <td>${row.form89.pregnant.displayString}</td>
	 <td>${row.form89.locationType.displayString}</td>
	  <td>${row.form89.profession.displayString}</td>
	  <td>${row.form89.populationCategory.displayString}</td>
	  <td>${row.form89.countryOfOrigin}</td>
	  <td>${row.form89.placeOfDetection.displayString}</td>
	  <td>${row.dateFirstSeekingHelp}</td>
	  <td>${row.form89.circumstancesOfDetection.displayString}</td>
	  <td>${row.form89.cityOfOrigin}</td>
	  <td>${row.dateOfReturn}</td>
	  <td>${row.form89.methodOfDetection.displayString}</td>
	  <td>${row.form89.otherMethodOfDetection}</td>
	  <td>${row.form89.anatomicalSite.displayString}</td>
	  <td>${row.form89.epSite.displayString}</td>
	  <td>${row.form89.epLocation.displayString}</td>
	  <td>${row.form89.pulSite.displayString}</td>
	  <td>${row.form89.presenceOfDecay.displayString}</td>
	  <td>${row.dateOfDecaySurvey}</td>
	  <td>${row.form89.complication}</td>
	 
	 
	 <td align="center">${row.diagnosticSmearResult }</td>
	 <td>${row.diagnosticSmearDate }</td>
	 <td>${row.diagnosticSmearLab }</td>
	 <td>${row.diagnosticSmearTestNumber }</td>
	 
	 <td align="center">${row.xpertMTBResult } ${row.xpertRIFResult } </td>
	 <td>${row.xpertTestDate }</td>
	 <td>${row.xpertLab }</td>
	 <td>${row.xpertTestNumber }</td>
	 
	 <td align="center">${row.hainMTBResult }</td>
	 <td align="center">${row.hainINHResult }</td>
	 <td align="center">${row.hainRIFResult }</td>
	 <td align="center">${row.hainTestDate } </td>
	 <td>${row.hainLab }</td>
	 <td>${row.hainTestNumber }</td>
	 
	 <td align="center">${row.hain2MTBResult }</td>
	 <td align="center">${row.hain2InjResult }</td>
	 <td align="center">${row.hain2FqResult }</td>
	 <td align="center">${row.hain2TestDate } </td>
	 <td>${row.hain2Lab }</td>
	 <td>${row.hain2TestNumber }</td>
	 
	  <td>${row.form89.diabetes.displayString}</td>
	  <td>${row.form89.cnsdl.displayString}</td>
	  <td>${row.form89.htHeartDisease.displayString}</td>
	  <td>${row.form89.ulcer.displayString}</td>
	  <td>${row.form89.mentalDisorder.displayString}</td>
	  <td>${row.form89.ibc20.displayString}</td>
	  <td>${row.form89.cancer.displayString}</td>
	  <td>${row.form89.hepatitis.displayString}</td>
	  <td>${row.form89.kidneyDisease.displayString}</td>
	  <td>${row.form89.otherDisease}</td>
	  <td>${row.cmacDate } </td>
	  <td>${row.form89.cmacNumber } </td>
	  <td>${row.form89.prescribedTreatment.displayString } </td>
	  <td>${row.form89.placeOfCommission.displayString } </td>
	  <td>${row.form89.nameOfDoctor } </td>
	  <td><a href="${pageContext.request.contextPath}/module/mdrtb/form/form89.form?patientProgramId=${row.form89.patProgId}&encounterId=${row.form89.encounter.id}"><spring:message code="mdrtb.view"/></a></td>
	 </tr>
	 
	 	
	
	</c:forEach>
	</table>

	<c:if test="${locale == 'tj' }"></font></c:if>
</div>
<input type="button" onclick="tableToExcel('tb03', 'TB03')" value="<spring:message code='dotsreports.exportToExcelBtn' />" />

<input type="button" id="back" name="back" value="<spring:message code='mdrtb.back' />" onclick="document.location.href='${pageContext.request.contextPath}/module/mdrtb/mdrtbIndex.form';" />
<input type="button" onclick="printForm()" value="<spring:message code='mdrtb.print' />" />
<%@ include file="../mdrtbFooter.jsp"%>
