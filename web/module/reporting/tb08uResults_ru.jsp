<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%>

<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>
<html>
	<head>
		<title>TB-08u</title>
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

		    mywindow.document.write('<html><head><title><spring:message code="mdrtb.tb08" text="TB08u"/></title>');
		    mywindow.document.write('</head><body >');
		   // mywindow.document.write('<h1><spring:message code="mdrtb.pv.aeForm" text="AE"/></h1>');
		    mywindow.document.write(document.getElementById("tb08u").innerHTML);
		    
		    mywindow.document.write('</body></html>');

		    mywindow.document.close(); // necessary for IE >= 10
		    mywindow.focus(); // necessary for IE >= 10*/

		    mywindow.print();
		    mywindow.close();

		    return true;
		}
			var tableToExcel = (function() {
			  var uri = 'data:application/vnd.ms-excel;base64,'
			    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>TB08u</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta http-equiv="content-type" content="text/plain; charset=UTF-8"/></head><body><table>{table}</table></body></html>'
			    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
			    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
			  return function(table, name) {
			    if (!table.nodeType) table = document.getElementById(table)
			    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
			    window.location.href = uri + base64(format(template, ctx))
			  }
			})()
			function savePdf(action, reportName, formPath) {
				var tableData = (document.getElementById("tb08u")).innerHTML.toString();
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
						savePdf("closeReport.form", "TB-08u", "tb08uResults");
					}
				});
				/* $("#tableToPdf").click(function(){
					savePdf("exportReport.form", "TB-08u", "tb08uResults");
				}); */
			});
		</script>

		<div id="tb08u" style="font-size:smaller; width:980px;">
			<style>
				th {vertical-align:middle; text-align:center;}
				th, td {font-size:smaller;}
			</style>
			<table width="100%"><tr>
				<td width="90%" align="left" style="font-size:14px; font-weight:bold;">
					Квартальный отчет о результатах лечения больных ЛУ ТБ <br/>
					(заполняется после 24 - 36 месяцев от последней даты квартала или года начала лечения)
				</td>
				<td width="10%" align="right" style="font-size:14px; font-weight:bold;">ТБ 08y</td>
			</tr></table>
			<br/><br/>
			<center>
			<table width="100%" border="1">
				<tr>
				    <td>Наименование учреждения: <u>&nbsp; ${fName} &nbsp;</u> <br/>  
				    Область/район:   <u> ${oName}/${dName} </u><br/>
					ФИО координатора по ТБ:   _________________<br/>
					
				</td>
				
				<td valign="top">Случаи выявленные за ${quarter} квартал ${year} года<br/>
					Дата отчета: ${reportDate}
				</tr>
			</table>
			</center>
			<br/><br/>
			<table border="1" cellpadding="5" width="100%">
				<tr>
					<th rowspan="2" colspan="2" align="center">Регистрационная группа</th>
					<th rowspan="2" align="center">Было<br/>зарегист-<br/>рировано</th>
					<th rowspan="2" align="center">Излечен</th>
					<th rowspan="2" align="center">Лечение<br/>завершено</th>
					<th rowspan="2" align="center">Успешное<br/>лечение<br/>(излечен +<br/>лечение<br/>завершено)</th>
					<th colspan="2" align="center">Умер</th>
					<th rowspan="2" align="center">Неэффективное<br/>лечение</th>
					<th rowspan="2" align="center">Потерян для<br/>последующего<br/>наблюдения</th>
					
					
					<th rowspan="2" align="center">Результат не<br/>оценен<br/>(продолжают<br/>лечение и<br/>переведены)</th>
					<th rowspan="2" align="center">Всего</th>
				</tr>
				<tr>
					<th align="center">от ТБ</th>
					<th align="center">от других причин</th>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>1</td>
					<td>2</td>
					<td>3</td>
					<td>4</td>
					<td>5</td>
					<td>6</td>
					<td>7</td>
					<td>8</td>
					<td>9</td>
					<td>10</td>
				</tr>
				
				<tr>
					<td colspan="2">Новый случай</td>
					<td>${table1.newRegistered }</td>
					
					<td>${table1.newCured }</td>
					
					<td>${table1.newCompleted }</td>
					
					<td>${table1.newTxSuccess }</td>
					
					<td>${table1.newDiedTB }</td>
					
					<td>${table1.newDiedNotTB }</td>
					
					<td>${table1.newFailed }</td>
					
					<td>${table1.newDefaulted }</td>

					<td>${table1.newNotAssessed }</td>
					
					<td>${table1.newTotal }</td>
				</tr>
				<tr>
					<td rowspan="2" align="center">Рецидив после лечения по<br/>режиму</td>
					<td align="center">I</td>
					<td>${table1.relapse1Registered }</td>
					
					<td>${table1.relapse1Cured }</td>
					
					<td>${table1.relapse1Completed }</td>
					
					<td>${table1.relapse1TxSuccess }</td>
					
					<td>${table1.relapse1DiedTB }</td>
					
					<td>${table1.relapse1DiedNotTB }</td>
					
					<td>${table1.relapse1Failed }</td>
					
					<td>${table1.relapse1Defaulted }</td>

					<td>${table1.relapse1NotAssessed }</td>
					
					<td>${table1.relapse1Total }</td>
				</tr>
				<tr>
					<td align="center">II</td>
					<td>${table1.relapse2Registered }</td>
					
					<td>${table1.relapse2Cured }</td>
					
					<td>${table1.relapse2Completed }</td>
					
					<td>${table1.relapse2TxSuccess }</td>
					
					<td>${table1.relapse2DiedTB }</td>
					
					<td>${table1.relapse2DiedNotTB }</td>
					
					<td>${table1.relapse2Failed }</td>
					
					<td>${table1.relapse2Defaulted }</td>

					<td>${table1.relapse2NotAssessed }</td>
					
					<td>${table1.relapse2Total }</td>
				</tr>
				<tr>
					<td rowspan="2" align="center">После отрыва от режима</td>
					<td align="center">I</td>
					<td>${table1.default1Registered }</td>
					
					<td>${table1.default1Cured }</td>
					
					<td>${table1.default1Completed }</td>
					
					<td>${table1.default1TxSuccess }</td>
					
					<td>${table1.default1DiedTB }</td>
					
					<td>${table1.default1DiedNotTB }</td>
					
					<td>${table1.default1Failed }</td>
					
					<td>${table1.default1Defaulted }</td>

					<td>${table1.default1NotAssessed }</td>
					
					<td>${table1.default1Total }</td>
				</tr>
				<tr>
					<td align="center">II</td>
					<td>${table1.default2Registered }</td>
					
					<td>${table1.default2Cured }</td>
					
					<td>${table1.default2Completed }</td>
					
					<td>${table1.default2TxSuccess }</td>
					
					<td>${table1.default2DiedTB }</td>
					
					<td>${table1.default2DiedNotTB }</td>
					
					<td>${table1.default2Failed }</td>
					
					<td>${table1.default2Defaulted }</td>

					<td>${table1.default2NotAssessed }</td>
					
					<td>${table1.default2Total }</td>
				</tr>
				<tr>
					<td rowspan="2" align="center">После неэффективного<br/>лечения по режиму</td>
					<td align="center">I</td>
					
					<td>${table1.failure1Registered }</td>
					
					<td>${table1.failure1Cured }</td>
					
					<td>${table1.failure1Completed }</td>
					
					<td>${table1.failure1TxSuccess }</td>
					
					<td>${table1.failure1DiedTB }</td>
					
					<td>${table1.failure1DiedNotTB }</td>
					
					<td>${table1.failure1Failed }</td>
					
					<td>${table1.failure1Defaulted }</td>

					<td>${table1.failure1NotAssessed }</td>
					
					<td>${table1.failure1Total }</td>
				</tr>
				<tr>
					<td align="center">II</td>
					<td>${table1.failure2Registered }</td>
					
					<td>${table1.failure2Cured }</td>
					
					<td>${table1.failure2Completed }</td>
					
					<td>${table1.failure2TxSuccess }</td>
					
					<td>${table1.failure2DiedTB }</td>
					
					<td>${table1.failure2DiedNotTB }</td>
					
					<td>${table1.failure2Failed }</td>
					
					<td>${table1.failure2Defaulted }</td>

					<td>${table1.failure2NotAssessed }</td>
					
					<td>${table1.failure2Total }</td>
				</tr>
				
				<tr>
					<td colspan="2">Другие</td>
					<td>${table1.otherRegistered }</td>
					
					<td>${table1.otherCured }</td>
					
					<td>${table1.otherCompleted }</td>
					
					<td>${table1.otherTxSuccess }</td>
					
					<td>${table1.otherDiedTB }</td>
					
					<td>${table1.otherDiedNotTB }</td>
					
					<td>${table1.otherFailed }</td>
					
					<td>${table1.otherDefaulted }</td>

					<td>${table1.otherNotAssessed }</td>
					
					<td>${table1.otherTotal }</td>
				</tr>
				
				<tr>
					<td colspan="2">Итого</td>
					<td>${table1.totalRegistered }</td>
					
					<td>${table1.totalCured }</td>
					
					<td>${table1.totalCompleted }</td>
					
					<td>${table1.totalTxSuccess }</td>
					
					<td>${table1.totalDiedTB }</td>
					
					<td>${table1.totalDiedNotTB }</td>
					
					<td>${table1.totalFailed }</td>
					
					<td>${table1.totalDefaulted }</td>

					<td>${table1.totalNotAssessed }</td>
					
					<td>${table1.totalRegistered }</td>
				</tr>
				
				
					
				
				
			</table>
		</div>
		
		<input type="button" onclick="tableToExcel('tb08u', 'TB08u')" value="<spring:message code='mdrtb.exportToExcelBtn' />" />
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
	</body>
</html>
