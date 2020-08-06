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
		function printForm() {
			var mywindow = window.open('', 'PRINT', 'height=400,width=600');

		    mywindow.document.write('<html><head><title><spring:message code="mdrtb.pv.qtrReportTitle" text="AE Report"/></title>');
		    mywindow.document.write('</head><body >');
		   // mywindow.document.write('<h1><spring:message code="mdrtb.pv.aeForm" text="AE"/></h1>');
		    mywindow.document.write(document.getElementById("ae").innerHTML);
		    
		    mywindow.document.write('</body></html>');

		    mywindow.document.close(); // necessary for IE >= 10
		    mywindow.focus(); // necessary for IE >= 10*/

		    mywindow.print();
		    mywindow.close();

		    return true;
		}
		
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
<spring:message code="dotsreports.tb07.nameOfFacility"/>: <u>${fName}</u><br/>
<spring:message code="dotsreports.tb07.regionCityDistrict"/>: <u> ${oName}/${dName} </u><br/>
<spring:message code="dotsreports.tb07.tbCoordinatorName"/>: ___________________ </br>
<spring:message code="dotsreports.tb07.signature"/>: _________________
</td>

<td>
<spring:message code="mdrtb.pv.quarter" />,<spring:message code="mdrtb.pv.year" />: <u> ${quarter}, ${year}</u></br>
<spring:message code="mdrtb.pv.reportDate" />:<u>&nbsp; ${reportDate}</u>
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
					<td align="center">${table1.standardRegimenStarting}</td>
					<td align="center">${table1.standardRegimenEver}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.shortRegimen"/></td>
					<td align="center">${table1.shortRegimenStarting}</td>
					<td align="center">${table1.shortRegimenEver}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.regimenWithBdq"/></td>
					<td align="center">${table1.regimenWithBdqStarting}</td>
					<td align="center">${table1.regimenWithBdqEver}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.regimenWithDlm"/></td>
					<td align="center">${table1.regimenWithDlmStarting}</td>
					<td align="center">${table1.regimenWithDlmEver}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.regimenWithBdqAndDlm"/></td>
					<td align="center">${table1.regimenWithBdqDlmStarting}</td>
					<td align="center">${table1.regimenWithBdqDlmEver}</td>
				</tr>
				<tr>
					<td><spring:message code="mdrtb.pv.regimenWithCfzLzd"/></td>
					<td align="center">${table1.regimenWithCfzLzdStarting}</td>
					<td align="center">${table1.regimenWithCfzLzdEver}</td>
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
			  <tr>
			    <th align="center"><spring:message code="mdrtb.pv.adverseEvent"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.standardRegimen"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.shortRegimen"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.regimenWithBdq"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.regimenWithDlm"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.regimenWithBdqAndDlm"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.regimenWithCfzLzd"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.other"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.total"/></th>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.saes"/></td>
			   	<td align="center">${table2.saeStandard}</td>
			   	<td align="center">${table2.saeShort}</td>
			   	<td align="center">${table2.saeBdq}</td>
			   	<td align="center">${table2.saeDlm}</td>
			   	<td align="center">${table2.saeBdqAndDlm}</td>
			   	<td align="center">${table2.saeCfzLzd}</td>
			   	<td align="center">${table2.saeOther}</td>
			   	<td align="center">${table2.saeTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.death"/></td>
			   	<td align="center">${table2.deathStandard}</td>
			   	<td align="center">${table2.deathShort}</td>
			   	<td align="center">${table2.deathBdq}</td>
			   	<td align="center">${table2.deathDlm}</td>
			   	<td align="center">${table2.deathBdqAndDlm}</td>
			   	<td align="center">${table2.deathCfzLzd}</td>
			   	<td align="center">${table2.deathOther}</td>
			   	<td align="center">${table2.deathTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.hospitalization"/></td>
			   	<td align="center">${table2.hospitalizationStandard}</td>
			   	<td align="center">${table2.hospitalizationShort}</td>
			   	<td align="center">${table2.hospitalizationBdq}</td>
			   	<td align="center">${table2.hospitalizationDlm}</td>
			   	<td align="center">${table2.hospitalizationBdqAndDlm}</td>
			   	<td align="center">${table2.hospitalizationCfzLzd}</td>
			   	<td align="center">${table2.hospitalizationOther}</td>
			   	<td align="center">${table2.hospitalizationTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.disability"/></td>
			   	<td align="center">${table2.disabilityStandard}</td>
			   	<td align="center">${table2.disabilityShort}</td>
			   	<td align="center">${table2.disabilityBdq}</td>
			   	<td align="center">${table2.disabilityDlm}</td>
			   	<td align="center">${table2.disabilityBdqAndDlm}</td>
			   	<td align="center">${table2.disabilityCfzLzd}</td>
			   	<td align="center">${table2.disabilityOther}</td>
			   	<td align="center">${table2.disabilityTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.congenitalAbnormality"/></td>
			   	<td align="center">${table2.congenitalAbnormalityStandard}</td>
			   	<td align="center">${table2.congenitalAbnormalityShort}</td>
			   	<td align="center">${table2.congenitalAbnormalityBdq}</td>
			   	<td align="center">${table2.congenitalAbnormalityDlm}</td>
			   	<td align="center">${table2.congenitalAbnormalityBdqAndDlm}</td>
			   	<td align="center">${table2.congenitalAbnormalityCfzLzd}</td>
			   	<td align="center">${table2.congenitalAbnormalityOther}</td>
			   	<td align="center">${table2.congenitalAbnormalityTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.lifeThreateningExperience"/></td>
			   	<td align="center">${table2.lifeThreateningExperienceStandard}</td>
			   	<td align="center">${table2.lifeThreateningExperienceShort}</td>
			   	<td align="center">${table2.lifeThreateningExperienceBdq}</td>
			   	<td align="center">${table2.lifeThreateningExperienceDlm}</td>
			   	<td align="center">${table2.lifeThreateningExperienceBdqAndDlm}</td>
			   	<td align="center">${table2.lifeThreateningExperienceCfzLzd}</td>
			   	<td align="center">${table2.lifeThreateningExperienceOther}</td>
			   	<td align="center">${table2.lifeThreateningExperienceTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.specialInterest"/></td>
			   	<td align="center">${table2.specialInterestStandard}</td>
			   	<td align="center">${table2.specialInterestShort}</td>
			   	<td align="center">${table2.specialInterestBdq}</td>
			   	<td align="center">${table2.specialInterestDlm}</td>
			   	<td align="center">${table2.specialInterestBdqAndDlm}</td>
			   	<td align="center">${table2.specialInterestCfzLzd}</td>
			   	<td align="center">${table2.specialInterestOther}</td>
			   	<td align="center">${table2.specialInterestTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.peripheralNeuropathy"/></td>
			   	<td align="center">${table2.peripheralNeuropathyStandard}</td>
			   	<td align="center">${table2.peripheralNeuropathyShort}</td>
			   	<td align="center">${table2.peripheralNeuropathyBdq}</td>
			   	<td align="center">${table2.peripheralNeuropathyDlm}</td>
			   	<td align="center">${table2.peripheralNeuropathyBdqAndDlm}</td>
			   	<td align="center">${table2.peripheralNeuropathyCfzLzd}</td>
			   	<td align="center">${table2.peripheralNeuropathyOther}</td>
			   	<td align="center">${table2.peripheralNeuropathyTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.psychiatricDisorders"/></td>
			   	<td align="center">${table2.psychiatricDisorderStandard}</td>
			   	<td align="center">${table2.psychiatricDisorderShort}</td>
			   	<td align="center">${table2.psychiatricDisorderBdq}</td>
			   	<td align="center">${table2.psychiatricDisorderDlm}</td>
			   	<td align="center">${table2.psychiatricDisorderBdqAndDlm}</td>
			   	<td align="center">${table2.psychiatricDisorderCfzLzd}</td>
			   	<td align="center">${table2.psychiatricDisorderOther}</td>
			   	<td align="center">${table2.psychiatricDisorderTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.opticNerveDisorders"/></td>
			   	<td align="center">${table2.opticNerveDisorderStandard}</td>
			   	<td align="center">${table2.opticNerveDisorderShort}</td>
			   	<td align="center">${table2.opticNerveDisorderBdq}</td>
			   	<td align="center">${table2.opticNerveDisorderDlm}</td>
			   	<td align="center">${table2.opticNerveDisorderBdqAndDlm}</td>
			   	<td align="center">${table2.opticNerveDisorderCfzLzd}</td>
			   	<td align="center">${table2.opticNerveDisorderOther}</td>
			   	<td align="center">${table2.opticNerveDisorderTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.ototoxicity"/></td>
			   	<td align="center">${table2.ototoxicityStandard}</td>
			   	<td align="center">${table2.ototoxicityShort}</td>
			   	<td align="center">${table2.ototoxicityBdq}</td>
			   	<td align="center">${table2.ototoxicityDlm}</td>
			   	<td align="center">${table2.ototoxicityBdqAndDlm}</td>
			   	<td align="center">${table2.ototoxicityCfzLzd}</td>
			   	<td align="center">${table2.ototoxicityOther}</td>
			   	<td align="center">${table2.ototoxicityTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.myelosuppression"/></td>
			   	<td align="center">${table2.myelosuppressionStandard}</td>
			   	<td align="center">${table2.myelosuppressionShort}</td>
			   	<td align="center">${table2.myelosuppressionBdq}</td>
			   	<td align="center">${table2.myelosuppressionDlm}</td>
			   	<td align="center">${table2.myelosuppressionBdqAndDlm}</td>
			   	<td align="center">${table2.myelosuppressionCfzLzd}</td>
			   	<td align="center">${table2.myelosuppressionOther}</td>
			   	<td align="center">${table2.myelosuppressionTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.qtProlongation"/></td>
			   	<td align="center">${table2.qtProlongationStandard}</td>
			   	<td align="center">${table2.qtProlongationShort}</td>
			   	<td align="center">${table2.qtProlongationBdq}</td>
			   	<td align="center">${table2.qtProlongationDlm}</td>
			   	<td align="center">${table2.qtProlongationBdqAndDlm}</td>
			   	<td align="center">${table2.qtProlongationCfzLzd}</td>
			   	<td align="center">${table2.qtProlongationOther}</td>
			   	<td align="center">${table2.qtProlongationTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.lacticAcidosis"/></td>
			   	<td align="center">${table2.lacticAcidosisStandard}</td>
			   	<td align="center">${table2.lacticAcidosisShort}</td>
			   	<td align="center">${table2.lacticAcidosisBdq}</td>
			   	<td align="center">${table2.lacticAcidosisDlm}</td>
			   	<td align="center">${table2.lacticAcidosisBdqAndDlm}</td>
			   	<td align="center">${table2.lacticAcidosisCfzLzd}</td>
			   	<td align="center">${table2.lacticAcidosisOther}</td>
			   	<td align="center">${table2.lacticAcidosisTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.hepatitis"/></td>
			   	<td align="center">${table2.hepatitisStandard}</td>
			   	<td align="center">${table2.hepatitisShort}</td>
			   	<td align="center">${table2.hepatitisBdq}</td>
			   	<td align="center">${table2.hepatitisDlm}</td>
			   	<td align="center">${table2.hepatitisBdqAndDlm}</td>
			   	<td align="center">${table2.hepatitisCfzLzd}</td>
			   	<td align="center">${table2.hepatitisOther}</td>
			   	<td align="center">${table2.hepatitisTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.hypothyroidism"/></td>
			   	<td align="center">${table2.hypothyroidismStandard}</td>
			   	<td align="center">${table2.hypothyroidismShort}</td>
			   	<td align="center">${table2.hypothyroidismBdq}</td>
			   	<td align="center">${table2.hypothyroidismDlm}</td>
			   	<td align="center">${table2.hypothyroidismBdqAndDlm}</td>
			   	<td align="center">${table2.hypothyroidismCfzLzd}</td>
			   	<td align="center">${table2.hypothyroidismOther}</td>
			   	<td align="center">${table2.hypothyroidismTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.hypokalemia"/></td>
			   	<td align="center">${table2.hypokalemiaStandard}</td>
			   	<td align="center">${table2.hypokalemiaShort}</td>
			   	<td align="center">${table2.hypokalemiaBdq}</td>
			   	<td align="center">${table2.hypokalemiaDlm}</td>
			   	<td align="center">${table2.hypokalemiaBdqAndDlm}</td>
			   	<td align="center">${table2.hypokalemiaCfzLzd}</td>
			   	<td align="center">${table2.hypokalemiaOther}</td>
			   	<td align="center">${table2.hypokalemiaTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.pancreatitis"/></td>
			   	<td align="center">${table2.pancreatitisStandard}</td>
			   	<td align="center">${table2.pancreatitisShort}</td>
			   	<td align="center">${table2.pancreatitisBdq}</td>
			   	<td align="center">${table2.pancreatitisDlm}</td>
			   	<td align="center">${table2.pancreatitisBdqAndDlm}</td>
			   	<td align="center">${table2.pancreatitisCfzLzd}</td>
			   	<td align="center">${table2.pancreatitisOther}</td>
			   	<td align="center">${table2.pancreatitisTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.phospholipidosis"/></td>
			   	<td align="center">${table2.phospholipidosisStandard}</td>
			   	<td align="center">${table2.phospholipidosisShort}</td>
			   	<td align="center">${table2.phospholipidosisBdq}</td>
			   	<td align="center">${table2.phospholipidosisDlm}</td>
			   	<td align="center">${table2.phospholipidosisBdqAndDlm}</td>
			   	<td align="center">${table2.phospholipidosisCfzLzd}</td>
			   	<td align="center">${table2.phospholipidosisOther}</td>
			   	<td align="center">${table2.phospholipidosisTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.renalFailure"/></td>
			   	<td align="center">${table2.renalFailureStandard}</td>
			   	<td align="center">${table2.renalFailureShort}</td>
			   	<td align="center">${table2.renalFailureBdq}</td>
			   	<td align="center">${table2.renalFailureDlm}</td>
			   	<td align="center">${table2.renalFailureBdqAndDlm}</td>
			   	<td align="center">${table2.renalFailureCfzLzd}</td>
			   	<td align="center">${table2.renalFailureOther}</td>
			   	<td align="center">${table2.renalFailureTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.total"/></td>
			   	<td align="center">${table2.totalStandard}</td>
			   	<td align="center">${table2.totalShort}</td>
			   	<td align="center">${table2.totalBdq}</td>
			   	<td align="center">${table2.totalDlm}</td>
			   	<td align="center">${table2.totalBdqAndDlm}</td>
			   	<td align="center">${table2.totalCfzLzd}</td>
			   	<td align="center">${table2.totalOther}</td>
			   	<td align="center">${table2.totalTotal}</td>
			  </tr>
			</tbody>
	     </table>
	    
	     <br/><br/>
		<h5><spring:message code="mdrtb.pv.table3.title"/></h5>
		<table border="1" cellpadding="1" cellspacing="1" dir="ltr"
			style="width: 980px;">
			<tbody>
			<tr>
			    <th align="center"><spring:message code="mdrtb.pv.adverseEvent"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.bdq"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.dlm"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.cfz"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.lzd"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.am"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.cm"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.mfx"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.lfx"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.cyc"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.pas"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.pto"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.z"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.e"/></th>
			    <th align="center"><spring:message code="mdrtb.pv.total"/></th>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.saes"/></td>
			   	<td align="center">${table3.saeBdq}</td>
			   	<td align="center">${table3.saeDlm}</td>
			   	<td align="center">${table3.saeCfz}</td>
			   	<td align="center">${table3.saeLzd}</td>
			   	<td align="center">${table3.saeAm}</td>
			   	<td align="center">${table3.saeCm}</td>
			   	<td align="center">${table3.saeMfx}</td>
			   	<td align="center">${table3.saeLfx}</td>
			   	<td align="center">${table3.saeCyc}</td>
			   	<td align="center">${table3.saePas}</td>
			   	<td align="center">${table3.saePto}</td>
			   	<td align="center">${table3.saeZ}</td>
			   	<td align="center">${table3.saeE}</td>
			   	<td align="center">${table3.saeTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.death"/></td>
			   	<td align="center">${table3.deathBdq}</td>
			   	<td align="center">${table3.deathDlm}</td>
			   	<td align="center">${table3.deathCfz}</td>
			   	<td align="center">${table3.deathLzd}</td>
			   	<td align="center">${table3.deathAm}</td>
			   	<td align="center">${table3.deathCm}</td>
			   	<td align="center">${table3.deathMfx}</td>
			   	<td align="center">${table3.deathLfx}</td>
			   	<td align="center">${table3.deathCyc}</td>
			   	<td align="center">${table3.deathPas}</td>
			   	<td align="center">${table3.deathPto}</td>
			   	<td align="center">${table3.deathZ}</td>
			   	<td align="center">${table3.deathE}</td>
			   	<td align="center">${table3.deathTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.hospitalization"/></td>
			   	<td align="center">${table3.hospitalizationBdq}</td>
			   	<td align="center">${table3.hospitalizationDlm}</td>
			   	<td align="center">${table3.hospitalizationCfz}</td>
			   	<td align="center">${table3.hospitalizationLzd}</td>
			   	<td align="center">${table3.hospitalizationAm}</td>
			   	<td align="center">${table3.hospitalizationCm}</td>
			   	<td align="center">${table3.hospitalizationMfx}</td>
			   	<td align="center">${table3.hospitalizationLfx}</td>
			   	<td align="center">${table3.hospitalizationCyc}</td>
			   	<td align="center">${table3.hospitalizationPas}</td>
			   	<td align="center">${table3.hospitalizationPto}</td>
			   	<td align="center">${table3.hospitalizationZ}</td>
			   	<td align="center">${table3.hospitalizationE}</td>
			   	<td align="center">${table3.hospitalizationTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.disability"/></td>
			   	<td align="center">${table3.disabilityBdq}</td>
			   	<td align="center">${table3.disabilityDlm}</td>
			   	<td align="center">${table3.disabilityCfz}</td>
			   	<td align="center">${table3.disabilityLzd}</td>
			   	<td align="center">${table3.disabilityAm}</td>
			   	<td align="center">${table3.disabilityCm}</td>
			   	<td align="center">${table3.disabilityMfx}</td>
			   	<td align="center">${table3.disabilityLfx}</td>
			   	<td align="center">${table3.disabilityCyc}</td>
			   	<td align="center">${table3.disabilityPas}</td>
			   	<td align="center">${table3.disabilityPto}</td>
			   	<td align="center">${table3.disabilityZ}</td>
			   	<td align="center">${table3.disabilityE}</td>
			   	<td align="center">${table3.disabilityTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.congenitalAbnormality"/></td>
			   	<td align="center">${table3.congenitalAbnormalityBdq}</td>
			   	<td align="center">${table3.congenitalAbnormalityDlm}</td>
			   	<td align="center">${table3.congenitalAbnormalityCfz}</td>
			   	<td align="center">${table3.congenitalAbnormalityLzd}</td>
			   	<td align="center">${table3.congenitalAbnormalityAm}</td>
			   	<td align="center">${table3.congenitalAbnormalityCm}</td>
			   	<td align="center">${table3.congenitalAbnormalityMfx}</td>
			   	<td align="center">${table3.congenitalAbnormalityLfx}</td>
			   	<td align="center">${table3.congenitalAbnormalityCyc}</td>
			   	<td align="center">${table3.congenitalAbnormalityPas}</td>
			   	<td align="center">${table3.congenitalAbnormalityPto}</td>
			   	<td align="center">${table3.congenitalAbnormalityZ}</td>
			   	<td align="center">${table3.congenitalAbnormalityE}</td>
			   	<td align="center">${table3.congenitalAbnormalityTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.lifeThreateningExperience"/></td>
			   	<td align="center">${table3.lifeThreateningExperienceBdq}</td>
			   	<td align="center">${table3.lifeThreateningExperienceDlm}</td>
			   	<td align="center">${table3.lifeThreateningExperienceCfz}</td>
			   	<td align="center">${table3.lifeThreateningExperienceLzd}</td>
			   	<td align="center">${table3.lifeThreateningExperienceAm}</td>
			   	<td align="center">${table3.lifeThreateningExperienceCm}</td>
			   	<td align="center">${table3.lifeThreateningExperienceMfx}</td>
			   	<td align="center">${table3.lifeThreateningExperienceLfx}</td>
			   	<td align="center">${table3.lifeThreateningExperienceCyc}</td>
			   	<td align="center">${table3.lifeThreateningExperiencePas}</td>
			   	<td align="center">${table3.lifeThreateningExperiencePto}</td>
			   	<td align="center">${table3.lifeThreateningExperienceZ}</td>
			   	<td align="center">${table3.lifeThreateningExperienceE}</td>
			   	<td align="center">${table3.lifeThreateningExperienceTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.specialInterest"/></td>
			   	<td align="center">${table3.specialInterestBdq}</td>
			   	<td align="center">${table3.specialInterestDlm}</td>
			   	<td align="center">${table3.specialInterestCfz}</td>
			   	<td align="center">${table3.specialInterestLzd}</td>
			   	<td align="center">${table3.specialInterestAm}</td>
			   	<td align="center">${table3.specialInterestCm}</td>
			   	<td align="center">${table3.specialInterestMfx}</td>
			   	<td align="center">${table3.specialInterestLfx}</td>
			   	<td align="center">${table3.specialInterestCyc}</td>
			   	<td align="center">${table3.specialInterestPas}</td>
			   	<td align="center">${table3.specialInterestPto}</td>
			   	<td align="center">${table3.specialInterestZ}</td>
			   	<td align="center">${table3.specialInterestE}</td>
			   	<td align="center">${table3.specialInterestTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.peripheralNeuropathy"/></td>
			   	<td align="center">${table3.peripheralNeuropathyBdq}</td>
			   	<td align="center">${table3.peripheralNeuropathyDlm}</td>
			   	<td align="center">${table3.peripheralNeuropathyCfz}</td>
			   	<td align="center">${table3.peripheralNeuropathyLzd}</td>
			   	<td align="center">${table3.peripheralNeuropathyAm}</td>
			   	<td align="center">${table3.peripheralNeuropathyCm}</td>
			   	<td align="center">${table3.peripheralNeuropathyMfx}</td>
			   	<td align="center">${table3.peripheralNeuropathyLfx}</td>
			   	<td align="center">${table3.peripheralNeuropathyCyc}</td>
			   	<td align="center">${table3.peripheralNeuropathyPas}</td>
			   	<td align="center">${table3.peripheralNeuropathyPto}</td>
			   	<td align="center">${table3.peripheralNeuropathyZ}</td>
			   	<td align="center">${table3.peripheralNeuropathyE}</td>
			   	<td align="center">${table3.peripheralNeuropathyTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.psychiatricDisorders"/></td>
			   	<td align="center">${table3.psychiatricDisorderBdq}</td>
			   	<td align="center">${table3.psychiatricDisorderDlm}</td>
			   	<td align="center">${table3.psychiatricDisorderCfz}</td>
			   	<td align="center">${table3.psychiatricDisorderLzd}</td>
			   	<td align="center">${table3.psychiatricDisorderAm}</td>
			   	<td align="center">${table3.psychiatricDisorderCm}</td>
			   	<td align="center">${table3.psychiatricDisorderMfx}</td>
			   	<td align="center">${table3.psychiatricDisorderLfx}</td>
			   	<td align="center">${table3.psychiatricDisorderCyc}</td>
			   	<td align="center">${table3.psychiatricDisorderPas}</td>
			   	<td align="center">${table3.psychiatricDisorderPto}</td>
			   	<td align="center">${table3.psychiatricDisorderZ}</td>
			   	<td align="center">${table3.psychiatricDisorderE}</td>
			   	<td align="center">${table3.psychiatricDisorderTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.opticNerveDisorders"/></td>
			   	<td align="center">${table3.opticNerveDisorderBdq}</td>
			   	<td align="center">${table3.opticNerveDisorderDlm}</td>
			   	<td align="center">${table3.opticNerveDisorderCfz}</td>
			   	<td align="center">${table3.opticNerveDisorderLzd}</td>
			   	<td align="center">${table3.opticNerveDisorderAm}</td>
			   	<td align="center">${table3.opticNerveDisorderCm}</td>
			   	<td align="center">${table3.opticNerveDisorderMfx}</td>
			   	<td align="center">${table3.opticNerveDisorderLfx}</td>
			   	<td align="center">${table3.opticNerveDisorderCyc}</td>
			   	<td align="center">${table3.opticNerveDisorderPas}</td>
			   	<td align="center">${table3.opticNerveDisorderPto}</td>
			   	<td align="center">${table3.opticNerveDisorderZ}</td>
			   	<td align="center">${table3.opticNerveDisorderE}</td>
			   	<td align="center">${table3.opticNerveDisorderTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.ototoxicity"/></td>
			   	<td align="center">${table3.ototoxicityBdq}</td>
			   	<td align="center">${table3.ototoxicityDlm}</td>
			   	<td align="center">${table3.ototoxicityCfz}</td>
			   	<td align="center">${table3.ototoxicityLzd}</td>
			   	<td align="center">${table3.ototoxicityAm}</td>
			   	<td align="center">${table3.ototoxicityCm}</td>
			   	<td align="center">${table3.ototoxicityMfx}</td>
			   	<td align="center">${table3.ototoxicityLfx}</td>
			   	<td align="center">${table3.ototoxicityCyc}</td>
			   	<td align="center">${table3.ototoxicityPas}</td>
			   	<td align="center">${table3.ototoxicityPto}</td>
			   	<td align="center">${table3.ototoxicityZ}</td>
			   	<td align="center">${table3.ototoxicityE}</td>
			   	<td align="center">${table3.ototoxicityTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.myelosuppression"/></td>
			   	<td align="center">${table3.myelosuppressionBdq}</td>
			   	<td align="center">${table3.myelosuppressionDlm}</td>
			   	<td align="center">${table3.myelosuppressionCfz}</td>
			   	<td align="center">${table3.myelosuppressionLzd}</td>
			   	<td align="center">${table3.myelosuppressionAm}</td>
			   	<td align="center">${table3.myelosuppressionCm}</td>
			   	<td align="center">${table3.myelosuppressionMfx}</td>
			   	<td align="center">${table3.myelosuppressionLfx}</td>
			   	<td align="center">${table3.myelosuppressionCyc}</td>
			   	<td align="center">${table3.myelosuppressionPas}</td>
			   	<td align="center">${table3.myelosuppressionPto}</td>
			   	<td align="center">${table3.myelosuppressionZ}</td>
			   	<td align="center">${table3.myelosuppressionE}</td>
			   	<td align="center">${table3.myelosuppressionTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.qtProlongation"/></td>
			   	<td align="center">${table3.qtProlongationBdq}</td>
			   	<td align="center">${table3.qtProlongationDlm}</td>
			   	<td align="center">${table3.qtProlongationCfz}</td>
			   	<td align="center">${table3.qtProlongationLzd}</td>
			   	<td align="center">${table3.qtProlongationAm}</td>
			   	<td align="center">${table3.qtProlongationCm}</td>
			   	<td align="center">${table3.qtProlongationMfx}</td>
			   	<td align="center">${table3.qtProlongationLfx}</td>
			   	<td align="center">${table3.qtProlongationCyc}</td>
			   	<td align="center">${table3.qtProlongationPas}</td>
			   	<td align="center">${table3.qtProlongationPto}</td>
			   	<td align="center">${table3.qtProlongationZ}</td>
			   	<td align="center">${table3.qtProlongationE}</td>
			   	<td align="center">${table3.qtProlongationTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.lacticAcidosis"/></td>
			   	<td align="center">${table3.lacticAcidosisBdq}</td>
			   	<td align="center">${table3.lacticAcidosisDlm}</td>
			   	<td align="center">${table3.lacticAcidosisCfz}</td>
			   	<td align="center">${table3.lacticAcidosisLzd}</td>
			   	<td align="center">${table3.lacticAcidosisAm}</td>
			   	<td align="center">${table3.lacticAcidosisCm}</td>
			   	<td align="center">${table3.lacticAcidosisMfx}</td>
			   	<td align="center">${table3.lacticAcidosisLfx}</td>
			   	<td align="center">${table3.lacticAcidosisCyc}</td>
			   	<td align="center">${table3.lacticAcidosisPas}</td>
			   	<td align="center">${table3.lacticAcidosisPto}</td>
			   	<td align="center">${table3.lacticAcidosisZ}</td>
			   	<td align="center">${table3.lacticAcidosisE}</td>
			   	<td align="center">${table3.lacticAcidosisTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.hepatitis"/></td>
			   	<td align="center">${table3.hepatitisBdq}</td>
			   	<td align="center">${table3.hepatitisDlm}</td>
			   	<td align="center">${table3.hepatitisCfz}</td>
			   	<td align="center">${table3.hepatitisLzd}</td>
			   	<td align="center">${table3.hepatitisAm}</td>
			   	<td align="center">${table3.hepatitisCm}</td>
			   	<td align="center">${table3.hepatitisMfx}</td>
			   	<td align="center">${table3.hepatitisLfx}</td>
			   	<td align="center">${table3.hepatitisCyc}</td>
			   	<td align="center">${table3.hepatitisPas}</td>
			   	<td align="center">${table3.hepatitisPto}</td>
			   	<td align="center">${table3.hepatitisZ}</td>
			   	<td align="center">${table3.hepatitisE}</td>
			   	<td align="center">${table3.hepatitisTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.hypothyroidism"/></td>
			   	<td align="center">${table3.hypothyroidismBdq}</td>
			   	<td align="center">${table3.hypothyroidismDlm}</td>
			   	<td align="center">${table3.hypothyroidismCfz}</td>
			   	<td align="center">${table3.hypothyroidismLzd}</td>
			   	<td align="center">${table3.hypothyroidismAm}</td>
			   	<td align="center">${table3.hypothyroidismCm}</td>
			   	<td align="center">${table3.hypothyroidismMfx}</td>
			   	<td align="center">${table3.hypothyroidismLfx}</td>
			   	<td align="center">${table3.hypothyroidismCyc}</td>
			   	<td align="center">${table3.hypothyroidismPas}</td>
			   	<td align="center">${table3.hypothyroidismPto}</td>
			   	<td align="center">${table3.hypothyroidismZ}</td>
			   	<td align="center">${table3.hypothyroidismE}</td>
			   	<td align="center">${table3.hypothyroidismTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.hypokalemia"/></td>
			   	<td align="center">${table3.hypokalemiaBdq}</td>
			   	<td align="center">${table3.hypokalemiaDlm}</td>
			   	<td align="center">${table3.hypokalemiaCfz}</td>
			   	<td align="center">${table3.hypokalemiaLzd}</td>
			   	<td align="center">${table3.hypokalemiaAm}</td>
			   	<td align="center">${table3.hypokalemiaCm}</td>
			   	<td align="center">${table3.hypokalemiaMfx}</td>
			   	<td align="center">${table3.hypokalemiaLfx}</td>
			   	<td align="center">${table3.hypokalemiaCyc}</td>
			   	<td align="center">${table3.hypokalemiaPas}</td>
			   	<td align="center">${table3.hypokalemiaPto}</td>
			   	<td align="center">${table3.hypokalemiaZ}</td>
			   	<td align="center">${table3.hypokalemiaE}</td>
			   	<td align="center">${table3.hypokalemiaTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.pancreatitis"/></td>
			   	<td align="center">${table3.pancreatitisBdq}</td>
			   	<td align="center">${table3.pancreatitisDlm}</td>
			   	<td align="center">${table3.pancreatitisCfz}</td>
			   	<td align="center">${table3.pancreatitisLzd}</td>
			   	<td align="center">${table3.pancreatitisAm}</td>
			   	<td align="center">${table3.pancreatitisCm}</td>
			   	<td align="center">${table3.pancreatitisMfx}</td>
			   	<td align="center">${table3.pancreatitisLfx}</td>
			   	<td align="center">${table3.pancreatitisCyc}</td>
			   	<td align="center">${table3.pancreatitisPas}</td>
			   	<td align="center">${table3.pancreatitisPto}</td>
			   	<td align="center">${table3.pancreatitisZ}</td>
			   	<td align="center">${table3.pancreatitisE}</td>
			   	<td align="center">${table3.pancreatitisTotal}</td>
			  </tr>
			   <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.phospholipidosis"/></td>
			   	<td align="center">${table3.phospholipidosisBdq}</td>
			   	<td align="center">${table3.phospholipidosisDlm}</td>
			   	<td align="center">${table3.phospholipidosisCfz}</td>
			   	<td align="center">${table3.phospholipidosisLzd}</td>
			   	<td align="center">${table3.phospholipidosisAm}</td>
			   	<td align="center">${table3.phospholipidosisCm}</td>
			   	<td align="center">${table3.phospholipidosisMfx}</td>
			   	<td align="center">${table3.phospholipidosisLfx}</td>
			   	<td align="center">${table3.phospholipidosisCyc}</td>
			   	<td align="center">${table3.phospholipidosisPas}</td>
			   	<td align="center">${table3.phospholipidosisPto}</td>
			   	<td align="center">${table3.phospholipidosisZ}</td>
			   	<td align="center">${table3.phospholipidosisE}</td>
			   	<td align="center">${table3.phospholipidosisTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.report.renalFailure"/></td>
			   	<td align="center">${table3.renalFailureBdq}</td>
			   	<td align="center">${table3.renalFailureDlm}</td>
			   	<td align="center">${table3.renalFailureCfz}</td>
			   	<td align="center">${table3.renalFailureLzd}</td>
			   	<td align="center">${table3.renalFailureAm}</td>
			   	<td align="center">${table3.renalFailureCm}</td>
			   	<td align="center">${table3.renalFailureMfx}</td>
			   	<td align="center">${table3.renalFailureLfx}</td>
			   	<td align="center">${table3.renalFailureCyc}</td>
			   	<td align="center">${table3.renalFailurePas}</td>
			   	<td align="center">${table3.renalFailurePto}</td>
			   	<td align="center">${table3.renalFailureZ}</td>
			   	<td align="center">${table3.renalFailureE}</td>
			   	<td align="center">${table3.renalFailureTotal}</td>
			  </tr>
			  <tr>
			   	<td align="left"><spring:message code="mdrtb.pv.total"/></td>
			   	<td align="center">${table3.totalBdq}</td>
			   	<td align="center">${table3.totalDlm}</td>
			   	<td align="center">${table3.totalCfz}</td>
			   	<td align="center">${table3.totalLzd}</td>
			   	<td align="center">${table3.totalAm}</td>
			   	<td align="center">${table3.totalCm}</td>
			   	<td align="center">${table3.totalMfx}</td>
			   	<td align="center">${table3.totalLfx}</td>
			   	<td align="center">${table3.totalCyc}</td>
			   	<td align="center">${table3.totalPas}</td>
			   	<td align="center">${table3.totalPto}</td>
			   	<td align="center">${table3.totalZ}</td>
			   	<td align="center">${table3.totalE}</td>
			   	<td align="center">${table3.totalTotal}</td>
			  </tr>
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
		

</div>
		
		<input type="button" onclick="tableToExcel('ae', 'AE')" value="<spring:message code='dotsreports.exportToExcelBtn' />" />
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

</body>
</html>
