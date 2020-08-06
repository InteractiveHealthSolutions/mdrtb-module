<%@page import="org.openmrs.module.mdrtb.service.MdrtbService"%>
<%@page import="org.openmrs.api.context.Context"%>
<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%>
<html>
	<head>
		<title>TB-07y</title>
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
		    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>TB07u</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta http-equiv="content-type" content="text/plain; charset=UTF-8"/></head><body><table>{table}</table></body></html>'
		    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
		    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
		  return function(table, name) {
		    if (!table.nodeType) table = document.getElementById(table)
		    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
		    window.location.href = uri + base64(format(template, ctx))
		  }
		})()
		function savePdf(action, reportName, formPath) {
			var tableData = (document.getElementById("tb07u")).innerHTML.toString();
			var oblast = "${oblast}";
			var district = "${location.locationId}";
			var year = "${year}";
			var quarter = "${quarter}";
			var month = "${month}";
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
		    input.setAttribute("id", "location");
		    input.setAttribute("name", "location");
		    input.setAttribute("value", district);
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
					savePdf("closeReport.form", "TB-07u", "tb07uResults");
				}
			});
			/* $("#tableToPdf").click(function(){
				savePdf("exportReport.form", "TB-07u", "tb07uResults");
			}); */
		});
		</script>
		<div id="tb07u" style="font-size:smaller; width:980px;">
			<style>
				th {vertical-align:top; text-align:left;}
				th, td {font-size:smaller;}
				.vertical-text {
				transform: rotate(270deg);
				transform-origin: left top 0;
				float: left;
			}
			</style>
			<table width="100%"><tr>
				<td width="10%">&nbsp;</td>
				<td width="50%" align="center" style="font-size:14px; font-weight:bold;">Квартальный отчет о выявлении и начале лечения случаев ЛУ ТБ </td>
				<td width="10%" align="right">ТБ 07y</td>
			</tr></table>
			<br/><br/>
			
			<center>
			<table width="100%" border="1">
				<tr>
				    <td>Наименование учреждения:_________________  ________________<br/>
					Область/район  #location#<br/>
					ФИО координатора по ТБ:   ________________<br/>
					Подпись:  ___________________________
				</td>
				
				<td valign="top">Случаи выявленные за #quarter# квартал  #year# года (#startDate|dd/MMM/yyyy# - #endDate|dd/MMM/yyyy#)<br/>
					Дата отчета: #generationDate|dd/MMM/yyyy#</td>
				</tr>
			</table>
			</center>
			<br/>
			<br/>
			<span style="font-weight:bold;">
				Таблица 1. Число выявленных случаев ЛУ ТБ в течении отчетного периода: 
			</span>
			<span style="font-weight:bold;">
				<br/><br/>
				<center><table width="100%" border="1">
					<tr>
					    <td rowspan="2" align="center">Число выявленных>br/>случаев ЛУ ТБ, всего  </td>
					    <td colspan="4" align="center">из них</td>
					</tr>
					<tr>
						<td align="center">РУ/МЛУ ТБ</td>
						<td align="center">ПЛУ ТБ</td>
						<td align="center">пре-ШЛУ ТБ</td>
						<td align="center">ШЛУ ТБ</td>
					</tr>
					<tr>
					    <td style="border: 1px solid black; width: 50px; height: 20px; text-align:center;">
							#labDetections.total#
						</td>
						<td style="border: 1px solid black; width: 50px; height: 20px; text-align:center;">
							#labDetections.mdr#
						</td>
						<td style="border: 1px solid black; width: 50px; height: 20px; text-align:center;">
							#labDetections.pdr#
						</td>
						<td style="border: 1px solid black; width: 50px; height: 20px; text-align:center;">
							#labDetections.prexdr#
						</td>
						<td style="border: 1px solid black; width: 50px; height: 20px; text-align:center;">
							#labDetections.xdr#
						</td>
					</tr>
				</table></center>
			</span>
			<br/><br/>
			
			<span style="font-weight:bold;">
				Таблица 2. Число случаев ЛУ ТБ, начавших лечение в течении отчетного периода
			</span>
			<br/><br/>
			<table border="1" cellpadding="5" width="100%">
				<tr align="center">
				 	<td colspan="2" rowspan="3">&nbsp;</td>
					<td colspan="7">Регистрационная группа</td>
					<td rowspan="3">Другие</td>
					<td rowspan="3">Итого</td>
				</tr>
				<tr align="center">
					<td rowspan="2">Новый<br/случай</td>
					<td colspan="2">Рецидив после<br/>лечения по<br/>режиму</td>
					<td colspan="2">После отрыва<br/>от лечения по<br/>режиму
					<td colspan="2">После неэффективного<br/>лечения по режиму</td>
				</tr>
				<tr align="center">
					<td>I</td> <!-- relapse -->
					<td>II</td> <!-- relapse -->
					<td>I</td> <!-- after default -->
					<td>II</td> <!-- after default -->
					<td>I</td> <!-- previously treated with Cat 1 -->
					<td>II</td>  <!-- previously treated with Cat 2 -->
				</tr>
				<tr>
				    <td colspan="2">РУ/МЛУ ТБ</td>
				   <td>#startedTreatment.mdr.New#</td>
					 <td>#startedTreatment.mdr.Relapse1#</td>
					 <td>#startedTreatment.mdr.Relapse2#</td>
					 <td>#startedTreatment.mdr.AfterDefault1#</td>
					 <td>#startedTreatment.mdr.AfterDefault2#</td>
					 <td>#startedTreatment.mdr.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdr.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdr.Other#</td>
					 <td>#startedTreatment.mdr.newTotal#</td> 
				</tr>
				<tr>
				     <td rowspan=3">из них<br/>дети</td>
				     <td>0-4</td>
				     <td>#startedTreatment.mdr04.New#</td>
					 <td>#startedTreatment.mdr04.Relapse1#</td>
					 <td>#startedTreatment.mdr04.Relapse2#</td>
					 <td>#startedTreatment.mdr04.AfterDefault1#</td>
					 <td>#startedTreatment.mdr04.AfterDefault2#</td>
					 <td>#startedTreatment.mdr04.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdr04.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdr04.Other#</td>
					 <td>#startedTreatment.mdr04.newTotal#</td>
				</tr>
				<tr>
				     <td>5-14</td>
				     <td>#startedTreatment.mdr0514.New#</td>
					 <td>#startedTreatment.mdr0514.Relapse1#</td>
					 <td>#startedTreatment.mdr0514.Relapse2#</td>
					 <td>#startedTreatment.mdr0514.AfterDefault1#</td>
					 <td>#startedTreatment.mdr0514.AfterDefault2#</td>
					 <td>#startedTreatment.mdr0514.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdr0514.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdr0514.Other#</td>
					 <td>#startedTreatment.mdr0514.newTotal#</td>
				</tr>
				<tr>
				     <td>15-17</td>
				     <td>#startedTreatment.mdr1517.New#</td>
					 <td>#startedTreatment.mdr1517.Relapse1#</td>
					 <td>#startedTreatment.mdr1517.Relapse2#</td>
					 <td>#startedTreatment.mdr1517.AfterDefault1#</td>
					 <td>#startedTreatment.mdr1517.AfterDefault2#</td>
					 <td>#startedTreatment.mdr1517.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdr1517.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdr1517.Other#</td>
					 <td>#startedTreatment.mdr1517.newTotal#</td>
				</tr>
				<tr>
				     <td colspan=2">из них с ВИЧ</td>
				     <td>#startedTreatment.mdrhiv.New#</td>
					 <td>#startedTreatment.mdrhiv.Relapse1#</td>
					 <td>#startedTreatment.mdrhiv.Relapse2#</td>
					 <td>#startedTreatment.mdrhiv.AfterDefault1#</td>
					 <td>#startedTreatment.mdrhiv.AfterDefault2#</td>
					 <td>#startedTreatment.mdrhiv.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrhiv.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrhiv.Other#</td>
					 <td>#startedTreatment.mdrhiv.newTotal#</td>
				</tr>
				<tr>
				    <td colspan="2">ПЛУ ТБ</td>
				    <td>#startedTreatment.pdr.New#</td>
					 <td>#startedTreatment.pdr.Relapse1#</td>
					 <td>#startedTreatment.pdr.Relapse2#</td>
					 <td>#startedTreatment.pdr.AfterDefault1#</td>
					 <td>#startedTreatment.pdr.AfterDefault2#</td>
					 <td>#startedTreatment.pdr.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.pdr.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.pdr.Other#</td>
					 <td>#startedTreatment.pdr.newTotal#</td> 
				</tr>
				<tr>
				     <td rowspan=3">из них<br/>дети</td>
				     <td>0-4</td>
				     <td>#startedTreatment.pdr04.New#</td>
					 <td>#startedTreatment.pdr04.Relapse1#</td>
					 <td>#startedTreatment.pdr04.Relapse2#</td>
					 <td>#startedTreatment.pdr04.AfterDefault1#</td>
					 <td>#startedTreatment.pdr04.AfterDefault2#</td>
					 <td>#startedTreatment.pdr04.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.pdr04.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.pdr04.Other#</td>
					 <td>#startedTreatment.pdr04.newTotal#</td>
				</tr>
				<tr>
				     <td>5-14</td>
				     <td>#startedTreatment.pdr0514.New#</td>
					 <td>#startedTreatment.pdr0514.Relapse1#</td>
					 <td>#startedTreatment.pdr0514.Relapse2#</td>
					 <td>#startedTreatment.pdr0514.AfterDefault1#</td>
					 <td>#startedTreatment.pdr0514.AfterDefault2#</td>
					 <td>#startedTreatment.pdr0514.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.pdr0514.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.pdr0514.Other#</td>
					 <td>#startedTreatment.pdr0514.newTotal#</td>
				</tr>
				<tr>
				     <td>15-17</td>
				     <td>#startedTreatment.pdr1517.New#</td>
					 <td>#startedTreatment.pdr1517.Relapse1#</td>
					 <td>#startedTreatment.pdr1517.Relapse2#</td>
					 <td>#startedTreatment.pdr1517.AfterDefault1#</td>
					 <td>#startedTreatment.pdr1517.AfterDefault2#</td>
					 <td>#startedTreatment.pdr1517.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.pdr1517.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.pdr1517.Other#</td>
					 <td>#startedTreatment.pdr1517.newTotal#</td>
				</tr>
				<tr>
				     <td colspan=2">из них с ВИЧ</td>
				     <td>#startedTreatment.pdrhiv.New#</td>
					 <td>#startedTreatment.pdrhiv.Relapse1#</td>
					 <td>#startedTreatment.pdrhiv.Relapse2#</td>
					 <td>#startedTreatment.pdrhiv.AfterDefault1#</td>
					 <td>#startedTreatment.pdrhiv.AfterDefault2#</td>
					 <td>#startedTreatment.pdrhiv.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.pdrhiv.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.pdrhiv.Other#</td>
					 <td>#startedTreatment.pdrhiv.newTotal#</td>
				</tr>
				<tr>
				    <td colspan="2">пре-ШЛУ ТБ</td>
				    <td>#startedTreatment.prexdr.New#</td>
					 <td>#startedTreatment.prexdr.Relapse1#</td>
					 <td>#startedTreatment.prexdr.Relapse2#</td>
					 <td>#startedTreatment.prexdr.AfterDefault1#</td>
					 <td>#startedTreatment.prexdr.AfterDefault2#</td>
					 <td>#startedTreatment.prexdr.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.prexdr.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.prexdr.Other#</td>
					 <td>#startedTreatment.prexdr.newTotal#</td>  
				</tr>
				<tr>
				     <td rowspan=3">из них<br/>дети</td>
				     <td>0-4</td>
				     <td>#startedTreatment.prexdr04.New#</td>
					 <td>#startedTreatment.prexdr04.Relapse1#</td>
					 <td>#startedTreatment.prexdr04.Relapse2#</td>
					 <td>#startedTreatment.prexdr04.AfterDefault1#</td>
					 <td>#startedTreatment.prexdr04.AfterDefault2#</td>
					 <td>#startedTreatment.prexdr04.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.prexdr04.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.prexdr04.Other#</td>
					 <td>#startedTreatment.prexdr04.newTotal#</td>
				</tr>
				<tr>
				     <td>5-14</td>
				     <td>#startedTreatment.prexdr0514.New#</td>
					 <td>#startedTreatment.prexdr0514.Relapse1#</td>
					 <td>#startedTreatment.prexdr0514.Relapse2#</td>
					 <td>#startedTreatment.prexdr0514.AfterDefault1#</td>
					 <td>#startedTreatment.prexdr0514.AfterDefault2#</td>
					 <td>#startedTreatment.prexdr0514.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.prexdr0514.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.prexdr0514.Other#</td>
					 <td>#startedTreatment.prexdr0514.newTotal#</td>
				</tr>
				<tr>
				     <td>15-17</td>
				     <td>#startedTreatment.prexdr1517.New#</td>
					 <td>#startedTreatment.prexdr1517.Relapse1#</td>
					 <td>#startedTreatment.prexdr1517.Relapse2#</td>
					 <td>#startedTreatment.prexdr1517.AfterDefault1#</td>
					 <td>#startedTreatment.prexdr1517.AfterDefault2#</td>
					 <td>#startedTreatment.prexdr1517.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.prexdr1517.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.prexdr1517.Other#</td>
					 <td>#startedTreatment.prexdr1517.newTotal#</td>
				</tr>
				<tr>
				     <td colspan=2">из них с ВИЧ</td>
				     <td>#startedTreatment.prexdrhiv.New#</td>
					 <td>#startedTreatment.prexdrhiv.Relapse1#</td>
					 <td>#startedTreatment.prexdrhiv.Relapse2#</td>
					 <td>#startedTreatment.prexdrhiv.AfterDefault1#</td>
					 <td>#startedTreatment.prexdrhiv.AfterDefault2#</td>
					 <td>#startedTreatment.prexdrhiv.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.prexdrhiv.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.prexdrhiv.Other#</td>
					 <td>#startedTreatment.prexdrhiv.newTotal#</td>
				</tr>
				<tr>
				    <td colspan="2">ШЛУ ТБ</td>
				    <td>#startedTreatment.xdr.New#</td>
					 <td>#startedTreatment.xdr.Relapse1#</td>
					 <td>#startedTreatment.xdr.Relapse2#</td>
					 <td>#startedTreatment.xdr.AfterDefault1#</td>
					 <td>#startedTreatment.xdr.AfterDefault2#</td>
					 <td>#startedTreatment.xdr.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdr.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdr.Other#</td>
					 <td>#startedTreatment.xdr.newTotal#</td>  
				</tr>
				<tr>
				     <td rowspan=3">из них<br/>дети</td>
				     <td>0-4</td>
				     <td>#startedTreatment.xdr04.New#</td>
					 <td>#startedTreatment.xdr04.Relapse1#</td>
					 <td>#startedTreatment.xdr04.Relapse2#</td>
					 <td>#startedTreatment.xdr04.AfterDefault1#</td>
					 <td>#startedTreatment.xdr04.AfterDefault2#</td>
					 <td>#startedTreatment.xdr04.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdr04.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdr04.Other#</td>
					 <td>#startedTreatment.xdr04.newTotal#</td>
				</tr>
				<tr>
				     <td>5-14</td>
				     <td>#startedTreatment.xdr0514.New#</td>
					 <td>#startedTreatment.xdr0514.Relapse1#</td>
					 <td>#startedTreatment.xdr0514.Relapse2#</td>
					 <td>#startedTreatment.xdr0514.AfterDefault1#</td>
					 <td>#startedTreatment.xdr0514.AfterDefault2#</td>
					 <td>#startedTreatment.xdr0514.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdr0514.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdr0514.Other#</td>
					 <td>#startedTreatment.xdr0514.newTotal#</td>
				</tr>
				<tr>
				     <td>15-17</td>
				     <td>#startedTreatment.xdr1517.New#</td>
					 <td>#startedTreatment.xdr1517.Relapse1#</td>
					 <td>#startedTreatment.xdr1517.Relapse2#</td>
					 <td>#startedTreatment.xdr1517.AfterDefault1#</td>
					 <td>#startedTreatment.xdr1517.AfterDefault2#</td>
					 <td>#startedTreatment.xdr1517.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdr1517.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdr1517.Other#</td>
					 <td>#startedTreatment.xdr1517.newTotal#</td>
				</tr>
				<tr>
				     <td colspan=2">из них с ВИЧ</td>
				     <td>#startedTreatment.xdrhiv.New#</td>
					 <td>#startedTreatment.xdrhiv.Relapse1#</td>
					 <td>#startedTreatment.xdrhiv.Relapse2#</td>
					 <td>#startedTreatment.xdrhiv.AfterDefault1#</td>
					 <td>#startedTreatment.xdrhiv.AfterDefault2#</td>
					 <td>#startedTreatment.xdrhiv.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrhiv.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrhiv.Other#</td>
					 <td>#startedTreatment.xdrhiv.newTotal#</td>
				</tr>
				<tr>
				    <td colspan="2">Итого</td>
				    <td>#startedTreatment.total.New#</td>
					 <td>#startedTreatment.total.Relapse1#</td>
					 <td>#startedTreatment.total.Relapse2#</td>
					 <td>#startedTreatment.total.AfterDefault1#</td>
					 <td>#startedTreatment.total.AfterDefault2#</td>
					 <td>#startedTreatment.total.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.total.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.total.Other#</td>
					 <td>#startedTreatment.total.newTotal#</td> 
				</tr>
				<tr>
				     <td rowspan=3">из них<br/>дети</td>
				     <td>0-4</td>
				     <td>#startedTreatment.total04.New#</td>
					 <td>#startedTreatment.total04.Relapse1#</td>
					 <td>#startedTreatment.total04.Relapse2#</td>
					 <td>#startedTreatment.total04.AfterDefault1#</td>
					 <td>#startedTreatment.total04.AfterDefault2#</td>
					 <td>#startedTreatment.total04.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.total04.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.total04.Other#</td>
					 <td>#startedTreatment.total04.newTotal#</td>
				</tr>
				<tr>
				     <td>5-14</td>
				     <td>#startedTreatment.total0514.New#</td>
					 <td>#startedTreatment.total0514.Relapse1#</td>
					 <td>#startedTreatment.total0514.Relapse2#</td>
					 <td>#startedTreatment.total0514.AfterDefault1#</td>
					 <td>#startedTreatment.total0514.AfterDefault2#</td>
					 <td>#startedTreatment.total0514.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.total0514.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.total0514.Other#</td>
					 <td>#startedTreatment.total0514.newTotal#</td>
				</tr>
				<tr>
				     <td>15-17</td>
				     <td>#startedTreatment.total1517.New#</td>
					 <td>#startedTreatment.total1517.Relapse1#</td>
					 <td>#startedTreatment.total1517.Relapse2#</td>
					 <td>#startedTreatment.total1517.AfterDefault1#</td>
					 <td>#startedTreatment.total1517.AfterDefault2#</td>
					 <td>#startedTreatment.total1517.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.total1517.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.total1517.Other#</td>
					 <td>#startedTreatment.total1517.newTotal#</td>
				</tr>
				<tr>
				     <td colspan=2">из них с ВИЧ</td>
				     <td>#startedTreatment.totalhiv.New#</td>
					 <td>#startedTreatment.totalhiv.Relapse1#</td>
					 <td>#startedTreatment.totalhiv.Relapse2#</td>
					 <td>#startedTreatment.totalhiv.AfterDefault1#</td>
					 <td>#startedTreatment.totalhiv.AfterDefault2#</td>
					 <td>#startedTreatment.totalhiv.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.totalhiv.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.totalhiv.Other#</td>
					 <td>#startedTreatment.totalhiv.newTotal#</td>
				</tr>
				
				</table>
			<br/><br/>	
			<span style="font-weight:bold;">
				Таблица 3. Число случаев ЛУ ТБ, начавших лечение в течении квартала отчетного периода, по режиму лечения
			</span>
			<br/><br/>
			<table border="1" cellpadding="5" width="100%">
				<tr align="center">
				 	<td colspan="2" rowspan="3">&nbsp;</td>
					<td colspan="7">Регистрационная группа</td>
					<td rowspan="3">Другие</td>
					<td rowspan="3">Итого</td>
				</tr>
				<tr align="center">
					<td rowspan="2">Новый<br/случай</td>
					<td colspan="2">Рецидив после<br/>лечения по<br/>режиму</td>
					<td colspan="2">После отрыва<br/>от лечения по<br/>режиму
					<td colspan="2">После неэффективного<br/>лечения по режиму</td>
				</tr>
				<tr align="center">
					<td>I</td> <!-- relapse -->
					<td>II</td> <!-- relapse -->
					<td>I</td> <!-- after default -->
					<td>II</td> <!-- after default -->
					<td>I</td> <!-- previously treated with Cat 1 -->
					<td>II</td>  <!-- previously treated with Cat 2 -->
				</tr>
				<tr>
				    <td colspan="11" align="center">РУ/МЛУ ТБ</td> 
				</tr>
				<tr>
				   <td colspan="2">Краткосрочный<br/>режим лечения</td>
				     <td>#startedTreatment.mdrshr.New#</td>
					 <td>#startedTreatment.mdrshr.Relapse1#</td>
					 <td>#startedTreatment.mdrshr.Relapse2#</td>
					 <td>#startedTreatment.mdrshr.AfterDefault1#</td>
					 <td>#startedTreatment.mdrshr.AfterDefault2#</td>
					 <td>#startedTreatment.mdrshr.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrshr.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrshr.Other#</td>
					 <td>#startedTreatment.mdrshr.newTotal#</td>
				</tr>
				
				<tr>
				     <td rowspan=3">из них<br/>дети</td>
				     <td>0-4</td>
				     <td>#startedTreatment.mdrshr04.New#</td>
					 <td>#startedTreatment.mdrshr04.Relapse1#</td>
					 <td>#startedTreatment.mdrshr04.Relapse2#</td>
					 <td>#startedTreatment.mdrshr04.AfterDefault1#</td>
					 <td>#startedTreatment.mdrshr04.AfterDefault2#</td>
					 <td>#startedTreatment.mdrshr04.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrshr04.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrshr04.Other#</td>
					 <td>#startedTreatment.mdrshr04.newTotal#</td>
				</tr>
				<tr>
				     <td>5-14</td>
				     <td>#startedTreatment.mdrshr0514.New#</td>
					 <td>#startedTreatment.mdrshr0514.Relapse1#</td>
					 <td>#startedTreatment.mdrshr0514.Relapse2#</td>
					 <td>#startedTreatment.mdrshr0514.AfterDefault1#</td>
					 <td>#startedTreatment.mdrshr0514.AfterDefault2#</td>
					 <td>#startedTreatment.mdrshr0514.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrshr0514.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrshr0514.Other#</td>
					 <td>#startedTreatment.mdrshr0514.newTotal#</td>
				</tr>
				<tr>
				     <td>15-17</td>
				     <td>#startedTreatment.mdrshr1517.New#</td>
					 <td>#startedTreatment.mdrshr1517.Relapse1#</td>
					 <td>#startedTreatment.mdrshr1517.Relapse2#</td>
					 <td>#startedTreatment.mdrshr1517.AfterDefault1#</td>
					 <td>#startedTreatment.mdrshr1517.AfterDefault2#</td>
					 <td>#startedTreatment.mdrshr1517.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrshr1517.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrshr1517.Other#</td>
					 <td>#startedTreatment.mdrshr1517.newTotal#</td>
				</tr>
				<tr>
				     <td colspan=2">из них с ВИЧ</td>
				     <td>#startedTreatment.mdrshrhiv.New#</td>
					 <td>#startedTreatment.mdrshrhiv.Relapse1#</td>
					 <td>#startedTreatment.mdrshrhiv.Relapse2#</td>
					 <td>#startedTreatment.mdrshrhiv.AfterDefault1#</td>
					 <td>#startedTreatment.mdrshrhiv.AfterDefault2#</td>
					 <td>#startedTreatment.mdrshrhiv.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrshrhiv.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrshrhiv.Other#</td>
					 <td>#startedTreatment.mdrshrhiv.newTotal#</td>
					 
				</tr>
				
				<tr>
				    <td colspan="2">Стандартный<br/>режим лечения</td>
				    <td>#startedTreatment.mdrstr.New#</td>
					 <td>#startedTreatment.mdrstr.Relapse1#</td>
					 <td>#startedTreatment.mdrstr.Relapse2#</td>
					 <td>#startedTreatment.mdrstr.AfterDefault1#</td>
					 <td>#startedTreatment.mdrstr.AfterDefault2#</td>
					 <td>#startedTreatment.mdrstr.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrstr.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrstr.Other#</td>
					 <td>#startedTreatment.mdrstr.newTotal#</td> 
				</tr>
				<tr>
				     <td rowspan=3">из них<br/>дети</td>
				     <td>0-4</td>
				     <td>#startedTreatment.mdrstr04.New#</td>
					 <td>#startedTreatment.mdrstr04.Relapse1#</td>
					 <td>#startedTreatment.mdrstr04.Relapse2#</td>
					 <td>#startedTreatment.mdrstr04.AfterDefault1#</td>
					 <td>#startedTreatment.mdrstr04.AfterDefault2#</td>
					 <td>#startedTreatment.mdrstr04.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrstr04.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrstr04.Other#</td>
					 <td>#startedTreatment.mdrstr04.newTotal#</td>
				</tr>
				<tr>
				     <td>5-14</td>
				     <td>#startedTreatment.mdrstr0514.New#</td>
					 <td>#startedTreatment.mdrstr0514.Relapse1#</td>
					 <td>#startedTreatment.mdrstr0514.Relapse2#</td>
					 <td>#startedTreatment.mdrstr0514.AfterDefault1#</td>
					 <td>#startedTreatment.mdrstr0514.AfterDefault2#</td>
					 <td>#startedTreatment.mdrstr0514.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrstr0514.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrstr0514.Other#</td>
					 <td>#startedTreatment.mdrstr0514.newTotal#</td>
				</tr>
				<tr>
				     <td>15-17</td>
				     <td>#startedTreatment.mdrstr1517.New#</td>
					 <td>#startedTreatment.mdrstr1517.Relapse1#</td>
					 <td>#startedTreatment.mdrstr1517.Relapse2#</td>
					 <td>#startedTreatment.mdrstr1517.AfterDefault1#</td>
					 <td>#startedTreatment.mdrstr1517.AfterDefault2#</td>
					 <td>#startedTreatment.mdrstr1517.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrstr1517.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrstr1517.Other#</td>
					 <td>#startedTreatment.mdrstr1517.newTotal#</td>
				</tr>
				<tr>
				     <td colspan=2">из них с ВИЧ</td>
				     <td>#startedTreatment.mdrstrhiv.New#</td>
					 <td>#startedTreatment.mdrstrhiv.Relapse1#</td>
					 <td>#startedTreatment.mdrstrhiv.Relapse2#</td>
					 <td>#startedTreatment.mdrstrhiv.AfterDefault1#</td>
					 <td>#startedTreatment.mdrstrhiv.AfterDefault2#</td>
					 <td>#startedTreatment.mdrstrhiv.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrstrhiv.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrstrhiv.Other#</td>
					 <td>#startedTreatment.mdrstrhiv.newTotal#</td>
				</tr>
				<tr>
				    <td colspan="2">Total RR/MDR TB</td>
				     <td>#startedTreatment.mdrtotal.New#</td>
					 <td>#startedTreatment.mdrtotal.Relapse1#</td>
					 <td>#startedTreatment.mdrtotal.Relapse2#</td>
					 <td>#startedTreatment.mdrtotal.AfterDefault1#</td>
					 <td>#startedTreatment.mdrtotal.AfterDefault2#</td>
					 <td>#startedTreatment.mdrtotal.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrtotal.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrtotal.Other#</td>
					 <td>#startedTreatment.mdrtotal.newTotal#</td> 
				</tr>
				<tr>
				     <td rowspan=3">из них<br/>дети</td>
				     <td>0-4</td>
				     <td>#startedTreatment.mdrtotal04.New#</td>
					 <td>#startedTreatment.mdrtotal04.Relapse1#</td>
					 <td>#startedTreatment.mdrtotal04.Relapse2#</td>
					 <td>#startedTreatment.mdrtotal04.AfterDefault1#</td>
					 <td>#startedTreatment.mdrtotal04.AfterDefault2#</td>
					 <td>#startedTreatment.mdrtotal04.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrtotal04.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrtotal04.Other#</td>
					 <td>#startedTreatment.mdrtotal04.newTotal#</td>
				</tr>
				<tr>
				     <td>5-14</td>
				     <td>#startedTreatment.mdrtotal0514.New#</td>
					 <td>#startedTreatment.mdrtotal0514.Relapse1#</td>
					 <td>#startedTreatment.mdrtotal0514.Relapse2#</td>
					 <td>#startedTreatment.mdrtotal0514.AfterDefault1#</td>
					 <td>#startedTreatment.mdrtotal0514.AfterDefault2#</td>
					 <td>#startedTreatment.mdrtotal0514.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrtotal0514.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrtotal0514.Other#</td>
					 <td>#startedTreatment.mdrtotal0514.newTotal#</td>
				</tr>
				<tr>
				     <td>15-17</td>
				     <td>#startedTreatment.mdrtotal1517.New#</td>
					 <td>#startedTreatment.mdrtotal1517.Relapse1#</td>
					 <td>#startedTreatment.mdrtotal1517.Relapse2#</td>
					 <td>#startedTreatment.mdrtotal1517.AfterDefault1#</td>
					 <td>#startedTreatment.mdrtotal1517.AfterDefault2#</td>
					 <td>#startedTreatment.mdrtotal1517.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrtotal1517.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrtotal1517.Other#</td>
					 <td>#startedTreatment.mdrtotal1517.newTotal#</td>
				</tr>
				<tr>
				     <td colspan=2">из них с ВИЧ</td>
				     <td>#startedTreatment.mdrtotalhiv.New#</td>
					 <td>#startedTreatment.mdrtotalhiv.Relapse1#</td>
					 <td>#startedTreatment.mdrtotalhiv.Relapse2#</td>
					 <td>#startedTreatment.mdrtotalhiv.AfterDefault1#</td>
					 <td>#startedTreatment.mdrtotalhiv.AfterDefault2#</td>
					 <td>#startedTreatment.mdrtotalhiv.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.mdrtotalhiv.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.mdrtotalhiv.Other#</td>
					 <td>#startedTreatment.mdrtotalhiv.newTotal#</td>
				</tr>
				
				
				<tr>
				    <td colspan="11" align="center">Пре-ШЛУ/ШЛУ ТБ</td> 
				</tr>
				<tr>
				   <td colspan="2">Индивидуальный<br/>режим лечения</td>
				   <td>#startedTreatment.xdrind.New#</td>
					 <td>#startedTreatment.xdrind.Relapse1#</td>
					 <td>#startedTreatment.xdrind.Relapse2#</td>
					 <td>#startedTreatment.xdrind.AfterDefault1#</td>
					 <td>#startedTreatment.xdrind.AfterDefault2#</td>
					 <td>#startedTreatment.xdrind.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrind.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrind.Other#</td>
					 <td>#startedTreatment.xdrind.newTotal#</td>
				</tr>
				
				<tr>
				     <td rowspan=3">из них<br/>дети</td>
				     <td>0-4</td>
				     <td>#startedTreatment.xdrind04.New#</td>
					 <td>#startedTreatment.xdrind04.Relapse1#</td>
					 <td>#startedTreatment.xdrind04.Relapse2#</td>
					 <td>#startedTreatment.xdrind04.AfterDefault1#</td>
					 <td>#startedTreatment.xdrind04.AfterDefault2#</td>
					 <td>#startedTreatment.xdrind04.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrind04.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrind04.Other#</td>
					 <td>#startedTreatment.xdrind04.newTotal#</td>
				</tr>
				<tr>
				     <td>5-14</td>
				     <td>#startedTreatment.xdrind0514.New#</td>
					 <td>#startedTreatment.xdrind0514.Relapse1#</td>
					 <td>#startedTreatment.xdrind0514.Relapse2#</td>
					 <td>#startedTreatment.xdrind0514.AfterDefault1#</td>
					 <td>#startedTreatment.xdrind0514.AfterDefault2#</td>
					 <td>#startedTreatment.xdrind0514.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrind0514.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrind0514.Other#</td>
					 <td>#startedTreatment.xdrind0514.newTotal#</td>
				</tr>
				<tr>
				     <td>15-17</td>
				     <td>#startedTreatment.xdrind1517.New#</td>
					 <td>#startedTreatment.xdrind1517.Relapse1#</td>
					 <td>#startedTreatment.xdrind1517.Relapse2#</td>
					 <td>#startedTreatment.xdrind1517.AfterDefault1#</td>
					 <td>#startedTreatment.xdrind1517.AfterDefault2#</td>
					 <td>#startedTreatment.xdrind1517.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrind1517.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrind1517.Other#</td>
					 <td>#startedTreatment.xdrind1517.newTotal#</td>
				</tr>
				<tr>
				     <td colspan=2">из них с ВИЧ</td>
				     <td>#startedTreatment.xdrindhiv.New#</td>
					 <td>#startedTreatment.xdrindhiv.Relapse1#</td>
					 <td>#startedTreatment.xdrindhiv.Relapse2#</td>
					 <td>#startedTreatment.xdrindhiv.AfterDefault1#</td>
					 <td>#startedTreatment.xdrindhiv.AfterDefault2#</td>
					 <td>#startedTreatment.xdrindhiv.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrindhiv.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrindhiv.Other#</td>
					 <td>#startedTreatment.xdrindhiv.newTotal#</td>
					 
				</tr>
				
				<tr>
				    <td colspan="2">Стандартный<br/>режим лечения</td>
				    <td>#startedTreatment.xdrstr.New#</td>
					 <td>#startedTreatment.xdrstr.Relapse1#</td>
					 <td>#startedTreatment.xdrstr.Relapse2#</td>
					 <td>#startedTreatment.xdrstr.AfterDefault1#</td>
					 <td>#startedTreatment.xdrstr.AfterDefault2#</td>
					 <td>#startedTreatment.xdrstr.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrstr.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrstr.Other#</td>
					 <td>#startedTreatment.xdrstr.newTotal#</td> 
				</tr>
				<tr>
				     <td rowspan=3">из них<br/>дети</td>
				     <td>0-4</td>
				     <td>#startedTreatment.xdrstr04.New#</td>
					 <td>#startedTreatment.xdrstr04.Relapse1#</td>
					 <td>#startedTreatment.xdrstr04.Relapse2#</td>
					 <td>#startedTreatment.xdrstr04.AfterDefault1#</td>
					 <td>#startedTreatment.xdrstr04.AfterDefault2#</td>
					 <td>#startedTreatment.xdrstr04.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrstr04.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrstr04.Other#</td>
					 <td>#startedTreatment.xdrstr04.newTotal#</td>
				</tr>
				<tr>
				     <td>5-14</td>
				     <td>#startedTreatment.xdrstr0514.New#</td>
					 <td>#startedTreatment.xdrstr0514.Relapse1#</td>
					 <td>#startedTreatment.xdrstr0514.Relapse2#</td>
					 <td>#startedTreatment.xdrstr0514.AfterDefault1#</td>
					 <td>#startedTreatment.xdrstr0514.AfterDefault2#</td>
					 <td>#startedTreatment.xdrstr0514.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrstr0514.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrstr0514.Other#</td>
					 <td>#startedTreatment.xdrstr0514.newTotal#</td>
				</tr>
				<tr>
				     <td>15-17</td>
				     <td>#startedTreatment.xdrstr1517.New#</td>
					 <td>#startedTreatment.xdrstr1517.Relapse1#</td>
					 <td>#startedTreatment.xdrstr1517.Relapse2#</td>
					 <td>#startedTreatment.xdrstr1517.AfterDefault1#</td>
					 <td>#startedTreatment.xdrstr1517.AfterDefault2#</td>
					 <td>#startedTreatment.xdrstr1517.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrstr1517.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrstr1517.Other#</td>
					 <td>#startedTreatment.xdrstr1517.newTotal#</td>
				</tr>
				<tr>
				     <td colspan=2">из них с ВИЧ</td>
				     <td>#startedTreatment.xdrstrhiv.New#</td>
					 <td>#startedTreatment.xdrstrhiv.Relapse1#</td>
					 <td>#startedTreatment.xdrstrhiv.Relapse2#</td>
					 <td>#startedTreatment.xdrstrhiv.AfterDefault1#</td>
					 <td>#startedTreatment.xdrstrhiv.AfterDefault2#</td>
					 <td>#startedTreatment.xdrstrhiv.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrstrhiv.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrstrhiv.Other#</td>
					 <td>#startedTreatment.xdrstrhiv.newTotal#</td>
				</tr>
				<tr>
				    <td colspan="2">Total Пре-ШЛУ/ШЛУ ТБ</td>
				    <td>#startedTreatment.xdrtotal.New#</td>
					 <td>#startedTreatment.xdrtotal.Relapse1#</td>
					 <td>#startedTreatment.xdrtotal.Relapse2#</td>
					 <td>#startedTreatment.xdrtotal.AfterDefault1#</td>
					 <td>#startedTreatment.xdrtotal.AfterDefault2#</td>
					 <td>#startedTreatment.xdrtotal.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrtotal.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrtotal.Other#</td>
					 <td>#startedTreatment.xdrtotal.newTotal#</td>
				</tr>
				<tr>
				     <td rowspan=3">из них<br/>дети</td>
				     <td>0-4</td>
				     <td>#startedTreatment.xdrtotal04.New#</td>
					 <td>#startedTreatment.xdrtotal04.Relapse1#</td>
					 <td>#startedTreatment.xdrtotal04.Relapse2#</td>
					 <td>#startedTreatment.xdrtotal04.AfterDefault1#</td>
					 <td>#startedTreatment.xdrtotal04.AfterDefault2#</td>
					 <td>#startedTreatment.xdrtotal04.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrtotal04.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrtotal04.Other#</td>
					 <td>#startedTreatment.xdrtotal04.newTotal#</td>
				</tr>
				<tr>
				     <td>5-14</td>
				     <td>#startedTreatment.xdrtotal0514.New#</td>
					 <td>#startedTreatment.xdrtotal0514.Relapse1#</td>
					 <td>#startedTreatment.xdrtotal0514.Relapse2#</td>
					 <td>#startedTreatment.xdrtotal0514.AfterDefault1#</td>
					 <td>#startedTreatment.xdrtotal0514.AfterDefault2#</td>
					 <td>#startedTreatment.xdrtotal0514.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrtotal0514.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrtotal0514.Other#</td>
					 <td>#startedTreatment.xdrtotal0514.newTotal#</td>
				</tr>
				<tr>
				     <td>15-17</td>
				     <td>#startedTreatment.xdrtotal1517.New#</td>
					 <td>#startedTreatment.xdrtotal1517.Relapse1#</td>
					 <td>#startedTreatment.xdrtotal1517.Relapse2#</td>
					 <td>#startedTreatment.xdrtotal1517.AfterDefault1#</td>
					 <td>#startedTreatment.xdrtotal1517.AfterDefault2#</td>
					 <td>#startedTreatment.xdrtotal1517.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrtotal1517.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrtotal1517.Other#</td>
					 <td>#startedTreatment.xdrtotal1517.newTotal#</td>
				</tr>
				<tr>
				     <td colspan=2">из них с ВИЧ</td>
				     <td>#startedTreatment.xdrtotalhiv.New#</td>
					 <td>#startedTreatment.xdrtotalhiv.Relapse1#</td>
					 <td>#startedTreatment.xdrtotalhiv.Relapse2#</td>
					 <td>#startedTreatment.xdrtotalhiv.AfterDefault1#</td>
					 <td>#startedTreatment.xdrtotalhiv.AfterDefault2#</td>
					 <td>#startedTreatment.xdrtotalhiv.AfterFailureCategoryI#</td>
					 <td>#startedTreatment.xdrtotalhiv.AfterFailureCategoryII#</td>
					 <td>#startedTreatment.xdrtotalhiv.Other#</td>
					 <td>#startedTreatment.xdrtotalhiv.newTotal#</td>
				</tr>
				
			</table>
		</div>
		<input type="button" onclick="tableToExcel('tb07u', 'TB07u')" value="<spring:message code='mdrtb.exportToExcelBtn' />" />
		<!-- <input type="button" id="tableToPdf" name="tableToPdf" value="<spring:message code='mdrtb.exportToPdfBtn' />" /> -->
		<openmrs:hasPrivilege privilege="Manage Report Closing">
		<input type="button" id="tableToSql" name="tableToSql" value="<spring:message code='mdrtb.closeReportBtn' />" />
		</openmrs:hasPrivilege>
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
