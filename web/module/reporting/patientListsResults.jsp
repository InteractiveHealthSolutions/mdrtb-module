<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp" %>

<%@ include file="/WEB-INF/template/headerMinimal.jsp" %>
<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui-1.7.2.custom.min.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/css/redmond/jquery-ui-1.7.2.custom.css" />

<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.dimensions.pack.js"/>
<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.tooltip.js" />
<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.tooltip.css" />
<openmrs:htmlInclude file="/moduleResources/mdrtb/mdrtb.css"/>
<script type="text/javascript">
function printForm() {
	var mywindow = window.open('', 'PRINT', 'height=400,width=600');
	var listName = "${listName}";
    mywindow.document.write('<html><head><title>' + listName + '</title>');
    mywindow.document.write('</head><body >');
   // mywindow.document.write('<h1><spring:message code="mdrtb.pv.aeForm" text="AE"/></h1>');
    mywindow.document.write(document.getElementById("list").innerHTML);
    
    mywindow.document.write('</body></html>');

    mywindow.document.close(); // necessary for IE >= 10
    mywindow.focus(); // necessary for IE >= 10*/

    mywindow.print();
    mywindow.close();

    return true;
}
var tableToExcel = (function() {
  var uri = 'data:application/vnd.ms-excel;base64,'
    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>${listName}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta http-equiv="content-type" content="text/plain; charset=UTF-8"/></head><body><table>{table}</table></body></html>'
    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
  return function(table, name) {
    if (!table.nodeType) table = document.getElementById(table)
    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
    window.location.href = uri + base64(format(template, ctx))
  }
})()
</script>
<b class="boxHeader" style="margin:0px"><spring:message code="mdrtb.patientLists" text="Lists"/></b>

<a href="${pageContext.request.contextPath}/module/mdrtb/reporting/patientLists.form"><spring:message code="mdrtb.back" text="Back"/></a><br/><br/>
<div id="list">
<spring:message code="mdrtb.oblast" text="Oblast"/>: ${oblast }<br/>
<spring:message code="mdrtb.district" text="District"/>: ${district}<br/>
<spring:message code="mdrtb.facility" text="Facility"/>: ${facility}<br/>
<spring:message code="mdrtb.year" text="Year"/>: ${year }<br/>
<spring:message code="mdrtb.quarter" text="Quarter"/>: ${quarter }<br/>
<spring:message code="mdrtb.month" text="Month"/>: ${month }<br/>
<spring:message code="mdrtb.list" text="List"/>: ${listName }<br/>
<br/>
${report}

<br/>
</div>
<input type="button" onclick="tableToExcel('list', 'list')" value="<spring:message code='dotsreports.exportToExcelBtn' />" />
<input type="button" onclick="printForm()" value="<spring:message code='mdrtb.print' />" />
<br><br>
<a href="${pageContext.request.contextPath}/module/mdrtb/reporting/patientLists.form"><spring:message code="mdrtb.back" text="Back"/></a><br/><br/>

<%@ include file="/WEB-INF/template/footer.jsp" %>
