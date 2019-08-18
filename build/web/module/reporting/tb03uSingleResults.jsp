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

    mywindow.document.write('<html><head><title><spring:message code="mdrtb.tb03u" text="TB03y"/></title>');
    mywindow.document.write('</head><body >');
   // mywindow.document.write('<h1><spring:message code="mdrtb.pv.aeForm" text="AE"/></h1>');
    mywindow.document.write(document.getElementById("tb03u").innerHTML);
    
    mywindow.document.write('</body></html>');

    mywindow.document.close(); // necessary for IE >= 10
    mywindow.focus(); // necessary for IE >= 10*/

    mywindow.print();
    mywindow.close();

    return true;
}
var tableToExcel = (function() {
  var uri = 'data:application/vnd.ms-excel;base64,'
    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>TB03u</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta http-equiv="content-type" content="text/plain; charset=UTF-8"/></head><body><table>{table}</table></body></html>'
    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
  return function(table, name) {
    if (!table.nodeType) table = document.getElementById(table)
    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
    window.location.href = uri + base64(format(template, ctx))
  }
})()
function savePdf(action, reportName, formPath) {
	var tableData = (document.getElementById("tb03u")).innerHTML.toString();
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
    input.setAttribute("value", "MDRTB");
    form.appendChild(input);
    
    form.submit();
}
$(document).ready(function(){
	$("#tableToSql").bind("click", function() {
		if(confirm('<spring:message code="mdrtb.closeReportMessage" />') ) {
			savePdf("closeReport.form", "TB-03u", "tb03uResults");
		}
	});
	/* $("#tableToPdf").click(function(){
		savePdf("exportReport.form", "TB-03u", "tb03uResults");
	}); */
});
</script>
</head>
<body>
<div id="tb03u">
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
		    translate(0px, 100px)
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
		  height: 200px;
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
	<table class="resultsTable" >
	   <tr>
	     <th class="rotate" rowspan="3"><div><span><spring:message code="mdrtb.tb03.registrationNumber"/></span></div></th>
	     <th class="rotate" rowspan="3"><div><span><spring:message code="mdrtb.tb03.dateOfRegistration"/></span></div></th>
	     <th class="rotate" rowspan="3"><div><span><spring:message code="mdrtb.tb03.tb03RegistrationYear"/></span></div></th>
	     <th class="rotate" rowspan="3"><div><span><spring:message code="mdrtb.tb03.tb03registration"/></span></div></th>
	     <th class="rotate" rowspan="3"><div><span><spring:message code="mdrtb.tb03.fullName"/></span></div></th>
	     <th class="rotate" rowspan="3"><div><span><spring:message code="mdrtb.tb03.gender"/></span></div></th>
	     <th class="rotate" rowspan="3"><div><span><spring:message code="mdrtb.tb03.ageAtRegistration"/></span></div></th>
	     <th class="rotate" rowspan="3"><div><span><spring:message code="mdrtb.tb03.dateOfBirth"/></span></div></th>
	     <th class="rotate" rowspan="3"><div><span><spring:message code="mdrtb.tb03.address"/></span></div></th>
	     <th class="rotate" rowspan="3"><div><span><spring:message code="mdrtb.tb03.addressRegII"/></span></div></th>
		 <th class="rotate" rowspan="3"><div><span><spring:message code="mdrtb.tb03.tbLocalization"/></span></div></th>
		
		 <th class="reggroup" colspan="8" ><spring:message code="mdrtb.tb03.registrationGroup"/></th>
		 <th class="rotate" rowspan="3"><div><span><spring:message code="mdrtb.tb03.transferFrom"/></span></div></th>
		 <th class="normal" rowspan="3"><spring:message code="mdrtb.tb03.confirmedOrSuspect"/></th>
		 <th class="normal" rowspan="3"><spring:message code="mdrtb.tb03.dateOfConfirmation"/></th>
		 <th class="normal" rowspan="3"><spring:message code="mdrtb.tb03.treatmentRegimen"/></th>
		 <th class="normal" rowspan="3"><spring:message code="mdrtb.tb03.treatmentStartDate"/></th>
		 <th class="rotate" rowspan="3"><div><span><spring:message code="mdrtb.tb03.ambulatoryHosp"/></span></div></th>
		 <th class="normal" rowspan="3" rowspan="2"><spring:message code="mdrtb.tb03.dstSampleCollectionDate"/></th>
		 <th class="normal" rowspan="3"><spring:message code="mdrtb.tb03.dstResultDate"/></th>
		 <th class="reggroup" colspan="17" rowspan="2"><spring:message code="mdrtb.tb03.dst"/></th>
		 <th class="normal" colspan="4"><spring:message code="dotsreports.tb03.genexpert"/></th>
	   	 <th class="normal" colspan="6" ><spring:message code="dotsreports.tb03.hain"/></th>
	   	 <th class="normal" colspan="6" ><spring:message code="mdrtb.tb03.hain2"/></th>
		 <th class="normal" rowspan="3"><spring:message code="mdrtb.tb03.drugResistance"/></th>
	     <th class="normal" rowspan="3"><spring:message code="mdrtb.tb03.diagnosticMethod"/></th>
		 <th class="reggroup" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.tbHivActivities"/></th>
		 <th class="normal" colspan="84"><spring:message code="mdrtb.tb03.bacCultureResults"/></th>
		  <th class="reggroup" rowspan="1" colspan="6"><spring:message code="mdrtb.tb03.treatmentOutcomeDate"/></th> 
		 <th class="rotate" rowspan="3"><div><span><spring:message code="mdrtb.tb03.transferOut"/></span></div></th>
		 <th class="normal" rowspan="3"><spring:message code="mdrtb.tb03.relapseYesNo"/></th>
		  <th class="normal" rowspan="3"><div><span><spring:message code="mdrtb.tb03.relapseTxMonth"/></span></div></th>
		 <th class="normal" rowspan="3"><spring:message code="mdrtb.tb03.notes"/></th>
	  </tr>
	   <tr>
	   	 
	   	  <th class="subrotate" rowspan="2"><div><span><spring:message code="mdrtb.tb03.new"/></span></div></th>
	   	 <th class="subrotate" rowspan="1" colspan="2"><div><span><spring:message code="mdrtb.tb03.relapseAfter"/></span></div></th>
	   	 
	   	
	   	 <th class="subrotate" rowspan="1" colspan="2"><div><span><spring:message code="mdrtb.tb03.defaultAfter"/></span></div></th>
	   	<!-- <th class="subrotate" rowspan="1"><div><span><spring:message code="mdrtb.tb03.defaultAfter"/>Default After Treatment<br/>on Regimen </span></div></th> --> 
	   	 <th class="subrotate" rowspan="1" colspan="2"><div><span><spring:message code="mdrtb.tb03.failureAfter"/></span></div></th>
	   	 <!-- <th class="subrotate" rowspan="1"><div><span><spring:message code="mdrtb.tb03.failureAfter"/>Failure After Treatment<br/>on Regimen</span></div></th> -->
	   	 <th class="subrotate" rowspan="2"><div><span><spring:message code="mdrtb.tb03.other"/></span></div></th>
	    
	   	 
	   	 
	   	 <th class="normal" rowspan="2"><spring:message code="dotsreports.tb03.xpertResult"/></th>
	   	  <th rowspan="2"><spring:message code="dotsreports.tb03.date"/></th>
	   		<th rowspan="2"><spring:message code="dotsreports.tb03.testNumber"/></th>
	   		<th rowspan="2"><spring:message code="mdrtb.lab"/></th>
	   		
	   	   <th class="normal" rowspan="2"><spring:message code="dotsreports.tb03.date"/></th>
	   	   <th rowspan="2"><spring:message code="mdrtb.tb03.testNumber"/></th>
	   	   <th class="normal" rowspan="2"><spring:message code="dotsreports.tb03.hainCultureResult"/></th>
	   	   <th rowspan="2"><spring:message code="dotsreports.tb03.hResult"/></th>
	   		<th rowspan="2"><spring:message code="dotsreports.tb03.rResult"/></th>
	   		<th rowspan="2"><spring:message code="mdrtb.lab"/></th>
	   		
	   	   <th class="normal" rowspan="2"><spring:message code="dotsreports.tb03.date"/></th>
	   	   <th rowspan="2"><spring:message code="dotsreports.tb03.testNumber"/></th>
	   	   <th class="normal" rowspan="2" ><spring:message code="dotsreports.tb03.hainCultureResult"/></th>
	   		<th rowspan="2"><spring:message code="mdrtb.tb03.iResult"/></th>
	   		<th rowspan="2"><spring:message code="mdrtb.tb03.fResult"/></th>
	   		<th rowspan="2"><spring:message code="mdrtb.lab"/></th>
	   	 
	   	  <th class="normal" rowspan="1" colspan="2"><spring:message code="mdrtb.tb03.hivTest"/></th>
	   	  <th class="normal" rowspan="2"><spring:message code="mdrtb.tb03.artTest"/></th>
	   	  <th class="normal" rowspan="2"><spring:message code="mdrtb.tb03.cpTest"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month0"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month1"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month2"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month3"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month4"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month5"/></th>
	   	   <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month6"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month7"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month8"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month9"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month10"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month11"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month12"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month15"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month18"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month21"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month24"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month27"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month30"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month33"/></th>
	   	  <th class="normal" rowspan="1" colspan="4"><spring:message code="mdrtb.tb03.month36"/></th>
	   	  <th class="normal" rowspan="2"><div><span><spring:message code="mdrtb.tb03.cured"/></span></div></th>
	   	   <th class="normal" rowspan="2"><div><span><spring:message code="mdrtb.tb03.txCompleted"/></span></div></th>
	   	    <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.died"/></th>
	   	   <th class="normal" rowspan="2"><div><span><spring:message code="mdrtb.tb03.failure"/></span></div></th>
	   	
	   	  <th class="normal" rowspan="2"><div><span><spring:message code="mdrtb.tb03.ltfu"/></span></div></th>
	   	  
	   	   
	   </tr>
	   
	   <tr>
	        <th class="normal" rowspan="1">I</th>
	   	 <th class="normal" rowspan="1">II</th>
	   	 <th class="normal" rowspan="1">I</th>
	   	 <th class="normal" rowspan="1">II</th>
	   	 <th class="normal" rowspan="1">I</th>
	   	 <th class="normal" rowspan="1">II</th>
	   	
	   	 
	   
	   	 
	   	 <th class="dst" rowspan="1"><div>R</div></th>
	   	 <th class="dst" rowspan="1"><div>H</div></th>
	   	 <th class="dst" rowspan="1"><div>E</div></th>
	   	 <th class="dst" rowspan="1"><div>S</div></th>
	   	 <th class="dst" rowspan="1"><div>Z</div></th>
	   	 <th class="dst" rowspan="1"><div>Km</div></th>
	   	 <th class="dst" rowspan="1"><div>Am</div></th>
	   	 <th class="dst" rowspan="1"><div>Cm</div></th>
	   	 <th class="dst" rowspan="1"><div>Ofx/<br/>Lfx</div></th>
	   	 <th class="dst" rowspan="1"><div>Mfx</div></th>
	   	 <th class="dst" rowspan="1"><div>Pto</div></th>
	   	 <th class="dst" rowspan="1"><div>Cs</div></th>
	   	 <th class="dst" rowspan="1"><div>PAS</div></th>
	   	 <th class="dst" rowspan="1"><div>Lzd</div></th>
	   	 <th class="dst" rowspan="1"><div>Cfz</div></th>
	   	 <th class="dst" rowspan="1"><div>Bdq</div></th>
	   	 <th class="dst" rowspan="1"><div>Dlm</div></th>
	   	
	   		 <th class="normal"><spring:message code="mdrtb.tb03.yesNoDate"/></th>
	   	 <th class="normal"><spring:message code="mdrtb.tb03.resultPosNeg"/></th>	
	   		
	   
	   	<th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.smearShort"/></th>
	        <th class="normal" colspan="2"><spring:message code="mdrtb.tb03.cultureShort"/></th>
	        <th class="normal"><div><span><spring:message code="mdrtb.tb03.ofTb"/></span></div></th>
	   	   <th class="normal"><div><span><spring:message code="mdrtb.tb03.ofOther"/></span></div></th>
	   
	   </tr>
	   

	<c:forEach var="row" items="${patientSet}" varStatus="loopVar">
	 <tr <c:if test="${loopVar.index%2==0}"> bgcolor="#D3D3D3" </c:if>>
	 <td rowspan="1"><div><span>${row.identifierMDR}</span></div></td>
	 <td rowspan="1">${row.tb03uRegistrationDate}</td>
	 <td rowspan="1">${row.dotsYear}</td>
	 <td rowspan="1">${row.identifierDOTS}</td>
	    
	 <td rowspan="1">${row.patient.personName.familyName}, ${row.patient.personName.givenName}</td>
	  
	     
	     
	 <td rowspan="1" align="center">${row.gender}</td>
	 <td align="center" rowspan="1">${row.ageAtTB03uRegistration }</td>
	 <td rowspan="1">${row.dateOfBirth}</td>
	 <td rowspan="1">${row.patient.personAddress.stateProvince},${row.patient.personAddress.countyDistrict},${row.patient.personAddress.address1}</td> 
	 <td rowspan="1">${row.reg2Number}</td>
	 
	 <td align="center" rowspan="1">${row.siteOfDisease}</td>
	 <c:forEach begin="0" end="8" varStatus="loop">
	    <td align="center" rowspan="1">
	      <c:if test="${row.regGroup == loop.index }">&#10004;</c:if>
	    </td>
	</c:forEach>
	<td rowspan="1">${row.mdrtbStatus }
	<td rowspan="1">${row.mdrConfDate}</td>
	<td rowspan="1">${row.treatmentRegimen }</td>
	<td rowspan="1">${row.tb03uTreatmentStartDate }</td>
	<td rowspan="1">${row.treatmentLocation}</td>
	
	  <td>${ row.dstCollectionDate}</td>
	  <td rowspan="1">${ row.dstResultDate}</td>
	  <td rowspan="1" align="center">${ row.dstR}</td>
	  <td rowspan="1" align="center">${ row.dstH }</td>
	 <td rowspan="1" align="center">${ row.dstE }</td>
	  <td rowspan="1" align="center">${ row.dstS }</td>
	  <td rowspan="1" align="center">${ row.dstZ }</td>
	  <td rowspan="1" align="center">${ row.dstKm }</td>
	 <td rowspan="1" align="center">${ row.dstAm }</td>
	 <td rowspan="1" align="center">${ row.dstCm }</td>
	  <td rowspan="1" align="center">${ row.dstOfx }</td>
	  <td rowspan="1" align="center">${ row.dstMfx }</td>
	 <td rowspan="1" align="center">${ row.dstPto }</td>
	 <td rowspan="1" align="center">${ row.dstCs }</td>
	  <td rowspan="1" align="center">${ row.dstPAS }</td>
	  <td rowspan="1" align="center">${ row.dstLzd }</td>
	 <td rowspan="1" align="center">${ row.dstCfz }</td>
	 <td rowspan="1" align="center">${ row.dstBdq}</td>
	 <td rowspan="1"align="center">${ row.dstDlm }</td>
	   <td colspan="1" align="center">${row.xpertMTBResult } ${row.xpertRIFResult } </td>
	      <td>${row.xpertTestDate }</td>
	 	<td>${row.xpertTestNumber }</td>
	 	<td>${row.xpertLab }</td>
	   
	 <td align="center">${row.hainTestDate } </td>
	 <td>${row.hainTestNumber }</td>
	 <td colspan="1" align="center">${row.hainMTBResult }</td>
	 	<td align="center">${row.hainINHResult }</td>
	 	<td align="center">${row.hainRIFResult }</td>
	 	<td>${row.hainLab }</td>
	 	
	 
	 
	  <td align="center">${row.hain2TestDate } </td>
	
	 <td>${row.hain2TestNumber }</td>
	  <td colspan="1" align="center">${row.hain2MTBResult }</td>
	 	<td align="center">${row.hain2InjResult }</td>
	 	<td align="center">${row.hain2FqResult }</td>
	 	<td>${row.hain2Lab }</td>
	 
	 
	 
	 
	  <td rowspan="1">${row.drugResistance }</td>
	    <td rowspan="1">${ row.diagnosticMethod}</td>
	     
	   <td rowspan="1">${row.hivTestDate }</td>
	   <td rowspan="1">${row.hivTestResult }</td>
	  <td rowspan="1">${row.artStartDate }</td>
	 <td rowspan="1">${row.cpStartDate }</td>
	  <td rowspan="1">${row.month0SmearResult }</td>
	    <td rowspan="1">${row.month0SmearResultDate }</td>
	 <td rowspan="1">${row.month0CultureResult }</td>
	 <td rowspan="1">${row.month0CultureResultDate }</td>
	 <td rowspan="1">${row.month1SmearResult }</td>
	 <td rowspan="1">${row.month1SmearResultDate }</td>
	 <td rowspan="1">${row.month1CultureResult }</td>
	  <td rowspan="1">${row.month1CultureResultDate }</td>
	 <td rowspan="1">${row.month2SmearResult }</td>
	 <td rowspan="1">${row.month2SmearResultDate }</td>
	 <td rowspan="1">${row.month2CultureResult }</td>
	 <td rowspan="1">${row.month2CultureResultDate }</td>
	  <td rowspan="1">${row.month3SmearResult }</td>
	   <td rowspan="1">${row.month3SmearResultDate }</td>
	 <td rowspan="1">${row.month3CultureResult }</td>
	  <td rowspan="1">${row.month3CultureResultDate }</td>
	 <td rowspan="1">${row.month4SmearResult }</td>
	  <td rowspan="1">${row.month4SmearResultDate }</td>
	 <td rowspan="1">${row.month4CultureResult }</td>
	  <td rowspan="1">${row.month4CultureResultDate }</td>
	 <td rowspan="1">${row.month5SmearResult }</td>
	 <td rowspan="1">${row.month5SmearResultDate }</td>
	 <td rowspan="1">${row.month5CultureResult }</td>
	 <td rowspan="1">${row.month5CultureResultDate }</td>
	 <td rowspan="1">${row.month6SmearResult }</td>
	  <td rowspan="1">${row.month6SmearResultDate }</td>
	 <td rowspan="1">${row.month6CultureResult }</td>
	  <td rowspan="1">${row.month6CultureResultDate }</td>
	 <td rowspan="1">${row.month7SmearResult }</td>
	  <td rowspan="1">${row.month7SmearResultDate }</td>
	 <td rowspan="1">${row.month7CultureResult }</td>
	  <td rowspan="1">${row.month7CultureResultDate }</td>
	 <td rowspan="1">${row.month8SmearResult }</td>
	  <td rowspan="1">${row.month8SmearResultDate }</td>
	 <td rowspan="1">${row.month8CultureResult }</td>
	  <td rowspan="1">${row.month8CultureResultDate }</td>
	 <td rowspan="1">${row.month9SmearResult }</td>
	  <td rowspan="1">${row.month9SmearResultDate }</td>
	 <td rowspan="1">${row.month9CultureResult }</td>
	 <td rowspan="1">${row.month9CultureResultDate }</td>
	 <td rowspan="1">${row.month10SmearResult }</td>
	 <td rowspan="1">${row.month10SmearResultDate }</td>
	 <td rowspan="1">${row.month10CultureResult }</td>
	  <td rowspan="1">${row.month10CultureResultDate }</td>
	 <td rowspan="1">${row.month11SmearResult }</td>
	  <td rowspan="1">${row.month11SmearResultDate }</td>
	 <td rowspan="1">${row.month11CultureResult }</td>
	 <td rowspan="1">${row.month11CultureResultDate }</td>
	 <td rowspan="1">${row.month12SmearResult }</td>
	 <td rowspan="1">${row.month12SmearResultDate }</td>
	 <td rowspan="1">${row.month12CultureResult }</td>
	 <td rowspan="1">${row.month12CultureResultDate }</td>
	 <td rowspan="1">${row.month15SmearResult }</td>
	 <td rowspan="1">${row.month15SmearResultDate }</td>
	 <td rowspan="1">${row.month15CultureResult }</td>
	  <td rowspan="1">${row.month15CultureResultDate }</td>
	 <td rowspan="1">${row.month18SmearResult }</td>
	 <td rowspan="1">${row.month18SmearResultDate }</td>
	 <td rowspan="1">${row.month18CultureResult }</td>
	 <td rowspan="1">${row.month18CultureResultDate }</td>
	 <td rowspan="1">${row.month21SmearResult }</td>
	  <td rowspan="1">${row.month21SmearResultDate }</td>
	 <td rowspan="1">${row.month21CultureResult }</td>
	  <td rowspan="1">${row.month21CultureResultDate }</td>
	 <td rowspan="1">${row.month24SmearResult }</td>
	 <td rowspan="1">${row.month24SmearResultDate }</td>
	 <td rowspan="1">${row.month24CultureResult }</td>
	 <td rowspan="1">${row.month24CultureResultDate }</td>
	 <td rowspan="1">${row.month27SmearResult }</td>
	  <td rowspan="1">${row.month27SmearResultDate }</td>
	 <td rowspan="1">${row.month27CultureResult }</td>
	  <td rowspan="1">${row.month27CultureResultDate }</td>
	  <td rowspan="1">${row.month30SmearResult }</td>
	   <td rowspan="1">${row.month30SmearResultDate }</td>
	 <td rowspan="1">${row.month30CultureResult }</td>
	 <td rowspan="1">${row.month30CultureResultDate }</td>
	  <td rowspan="1">${row.month33SmearResult }</td>
	   <td rowspan="1">${row.month33SmearResultDate }</td>
	 <td rowspan="1">${row.month33CultureResult }</td>
	  <td rowspan="1">${row.month33CultureResultDate }</td>
	  <td rowspan="1">${row.month36SmearResult }</td>
	  <td rowspan="1">${row.month36SmearResultDate }</td>
	 <td rowspan="1">${row.month36CultureResult }</td>
	 <td rowspan="1">${row.month36CultureResultDate }</td>
	 
	
	 <c:forEach begin="0" end="6" varStatus="loop">
	    <td align="center" rowspan="1">
	      <c:if test="${row.tb03uTreatmentOutcome == loop.index }">${row.tb03uTreatmentOutcomeDate }</c:if>
	    </td>
	</c:forEach>
	<td rowspan="1">${row.relapsed }</td>
	 <td rowspan="1">${row.relapseMonth }</td>
	<td rowspan="1">${row.notes }</td>
	  
	
	
	
	     
	     
	     
	  
	 	
	   
	    
	
	 	
	 </tr>
	</c:forEach>
	 
	</table>
</div>
<input type="button" onclick="tableToExcel('tb03u', 'TB03u')" value="<spring:message code='mdrtb.exportToExcelBtn' />" />
<!-- <input type="button" id="tableToPdf" name="tableToPdf" value="<spring:message code='mdrtb.exportToPdfBtn' />" /> -->
<openmrs:hasPrivilege privilege="Manage Report Closing">
<input type="button" id="tableToSql" name="tableToSql" value="<spring:message code='mdrtb.closeReportBtn' />" />
<input type="button" onclick="printForm()" value="<spring:message code='mdrtb.print' />" />
</openmrs:hasPrivilege>
<script> 
	console.log("${reportStatus}");
	if("${reportStatus}" === "true") { 
		document.getElementById("tableToSql").disabled = true; 
	} else { 
		document.getElementById("tableToSql").disabled = false; 
	}
</script>
<%@ include file="../mdrtbFooter.jsp"%>
