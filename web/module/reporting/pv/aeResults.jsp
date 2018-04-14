<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%>
<%@ include file="../../mdrtbHeader.jsp"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>
<html>
<head>
	<title><spring:message code="mdrtb.pv.qtrReportTitle"/></title>
	<meta http-equiv="content-type" content="text/plain; charset=UTF-8"/>

</head>
<body>
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/tableExport/js/tableExport.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/tableExport/js/jquery.base64.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/tableExport/js/jspdf/libs/sprintf.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/tableExport/js/jspdf/jspdf.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/tableExport/js/jspdf/libs/base64.js"></script>
		
		<script type="text/javascript">
			var tableToExcel = (function() {
			  var uri = 'data:application/vnd.ms-excel;base64,'
			    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>TB07</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta http-equiv="content-type" content="text/plain; charset=UTF-8"/></head><body><table>{table}</table></body></html>'
			    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
			    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
			  return function(table, name) {
			    if (!table.nodeType) table = document.getElementById(table)
			    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
			    window.location.href = uri + base64(format(template, ctx))
			  }
			})()
			function savePdf(action, reportName, formPath) {
				var tableData = (document.getElementById("ae")).innerHTML.toString();
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
					if(confirm('<spring:message code="dotsreports.closeReportMessage" />') ) {
						savePdf("closeReport.form", "AE", "aeResults");
					}
				});
				/* $("#tableToPdf").click(function(){
					savePdf("exportReport.form", "TB 07", "tb07Results");
				}); */
			});
		</script>
		
		<div id="ae" style="font-size:smaller; width:980px;">	
<style type="text/css">th {vertical-align:top; text-align:left;}
			th, td {font-size:smaller; border: 1px solid #000000}
			border {border: 1px solid #000000}
</style>

<table border="0" width="100%">
	<tbody>
		<tr>
			<td align="center" style="font-size:14px; font-weight:bold;border:0px" width="90%"><spring:message code="mdrtb.pv.qtrReportTitle"/></td>
			<td align="right" style="font-size:14px; font-weight:bold;border:0px" valign="top" width="10%">AE</td>
		</tr>
	</tbody>
</table>
&nbsp;
<table width="100%" border="1">
<tr>
<td>
<spring:message code="dotsreports.tb07.nameOfFacility"/> <u>&nbsp; ${fName} &nbsp;</u><br/>
<spring:message code="dotsreports.tb07.regionCityDistrict"/> <u> ${oName}/${dName} </u><br/>
</td>

<td>
<spring:message code="mdrtb.pv.year" /> <u>&nbsp;${year}</u>
<spring:message code="mdrtb.pv.quarter" /><u>&nbsp; ${quarter}</u>
</td>
</tr>


</table>

<h5><spring:message code="mdrtb.pv.table1.title"/></h5>
	<table border="1" cellpadding="1" cellspacing="1" dir="ltr"
			style="width: 980px;">
			<tbody>
				<tr>
					<th><spring:message code="mdrtb.pv.regimen"/></th>
					<th><spring:message code="mdrtb.pv.table1.startingThisQuarter"/></th>
					<th><spring:message code="mdrtb.pv.table1.onRegimen"/></th>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.standardRegimen"/></td>
					<td>${table1.standardRegimenStarting}</td>
					<td>${table1.standardRegimenEver}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.shortRegimen"/></td>
					<td>${table1.shortRegimenStarting}</td>
					<td>${table1.shortRegimenEver}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.regimenWithBdq"/></td>
					<td>${table1.regimenWithBdqStarting}</td>
					<td>${table1.regimenWithBdqEver}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.regimenWithDlm"/></td>
					<td>${table1.regimenWithDlmStarting}</td>
					<td>${table1.regimenWithDlmEver}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.regimenWithBdqAndDlm"/></td>
					<td>${table1.regimenWithBdqDlmStarting}</td>
					<td>${table1.regimenWithBdqDlmEver}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.regimenWithCfzLzd"/></td>
					<td>${table1.regimenWithCfzLzdStarting}</td>
					<td>${table1.regimenWithCfzLzdEver}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.table1.otherRegimenSpecify"/></td>
					<td></td>
					<td></td>
				</tr>

			</tbody>
		</table>
		
		<br/><br/>
		<h5><spring:message code="mdrtb.pv.table2.title"/></h5>
		<table border="1" cellpadding="1" cellspacing="1" dir="ltr"
			style="width: 980px;">
			<tbody>
			</tbody>
	     </table>
	    
	     <br/><br/>
		<h5><spring:message code="mdrtb.pv.table3.title"/></h5>
		<table border="1" cellpadding="1" cellspacing="1" dir="ltr"
			style="width: 980px;">
			<tbody>
			</tbody>
	     </table>
	     
	     <br/><br/>
		<h5><spring:message code="mdrtb.pv.table4.title"/></h5>
		<table border="1" cellpadding="1" cellspacing="1" dir="ltr"
			style="width: 980px;">
			<tbody>
				<tr>
					<th><spring:message code="mdrtb.pv.table4.ae"/></th>
					<th><spring:message code="mdrtb.pv.table4.numberOfPatients"/></th>
				</tr/>
				<tr>
					<td><spring:message code="mdrtb.pv.nausea"/></td>
					<td/>${table4.nausea }</td>
				</tr/>
				<tr>
					<td><spring:message code="mdrtb.pv.diarrhoea"/></td>
					<td>${table4.diarrhoea}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.arthalgia"/></td>
					<td>${table4.arthalgia}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.dizziness"/></td>
					<td>${table4.dizziness}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.hearingDisturbances"/></td>
					<td>${table4.hearingDisturbances}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.headaches"/></td>
					<td>${table4.headaches}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.sleepDisturbances"/></td>
					<td>${table4.sleepDisturbances}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.electrolyteDisturbances"/></td>
					<td>${table4.electrolyteDisturbance}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.abdominalPain"/></td>
					<td>${table4.abdominalPain}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.anorexia"/></td>
					<td>${table4.anorexia}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.gastritis"/></td>
					<td>${table4.gastritis}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.peripheralNeuropathy"/></td>
					<td>${table4.peripheralNeuropathy}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.depression"/></td>
					<td>${table4.depression}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.tinnitus"/></td>
					<td>${table4.tinnitus}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.allergicReaction"/></td>
					<td>${table4.allergicReaction}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.rash"/></td>
					<td>${table4.rash}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.visualDisturbances"/></td>
					<td>${table4.visualDisturbances}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.seizures"/></td>
					<td>${table4.seizures}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.hypothyroidism"/></td>
					<td>${table4.hypothyroidism}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.psychosis"/></td>
					<td>${table4.psychosis}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.suicidalIdeation"/></td>
					<td>${table4.suicidalIdeation}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.hepatitis"/></td>
					<td>${table4.hepatitis}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.renalFailure"/></td>
					<td>${table4.renalFailure}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.qtProlongation"/></td>
					<td>${table4.qtProlongation}</td>
				</tr>
				
			</tbody>
	     </table>
	     
	     <br/><br/>
		<h5><spring:message code="mdrtb.pv.table5.title"/></h5>
		<table border="1" cellpadding="1" cellspacing="1" dir="ltr"
			style="width: 980px;">
			<tbody>
			</tbody>
	     </table>

</div>
		
		<input type="button" onclick="tableToExcel('ae', 'AE')" value="<spring:message code='dotsreports.exportToExcelBtn' />" />
		<!-- <input type="button" id="tableToPdf" name="tableToPdf" value="<spring:message code='dotsreports.exportToPdfBtn' />" /> -->
		<openmrs:hasPrivilege privilege="Manage Report Closing">
		<input type="button" id="tableToSql" name="tableToSql" value="<spring:message code='dotsreports.closeReportBtn' />" />
		</openmrs:hasPrivilege>
		<input type="button" id="back" name="back" value="<spring:message code='dotsreports.back' />" onclick="document.location.href='${pageContext.request.contextPath}/module/mdrtb/mdrtbIndex.form';" />

		
		<script> 
			console.log("${reportStatus}");
			if("${reportStatus}" === "true") { 
				document.getElementById("tableToSql").disabled = true; 
			} else { 
				document.getElementById("tableToSql").disabled = false; 
			}
		</script>

</body>
</html>
