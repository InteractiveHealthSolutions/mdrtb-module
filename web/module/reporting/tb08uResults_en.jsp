<%@page import="org.openmrs.module.mdrtb.service.MdrtbService"%>
<%@page import="org.openmrs.api.context.Context"%>
<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%>
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
		  			return function(table, name) { if (!table.nodeType) table = document.getElementById(table)
		    			var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
		    			window.location.href = uri + base64(format(template, ctx))
	  				}
				}
			)()
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
			<table width="90%"><tr>
				<td width="90" align="left" style="font-size:14px; font-weight:bold;">
					<spring:message code="mdrtb.tb08u.titleShort"/>
				</td>
				<td width="10%" align="right" style="font-size:14px; font-weight:bold;">TB 08y</td>
			</tr></table>
			<br/><br/>
			<table border="1" width="100%">
			
			<td>
			<spring:message code="mdrtb.tb08u.nameOfFacility"/> <u>&nbsp; ${fName} &nbsp;</u> <br/>
			<spring:message code="mdrtb.tb08u.regionCityDistrict"/>  <u> ${oName}/${dName} </u><br/>
			<spring:message code="mdrtb.tb08u.tbCoordinator"/> ____________________<spring:message code="mdrtb.tb08u.signature"/> ____________<br/>
			</td>
		
			<td>
			<spring:message code="mdrtb.tb08u.tbCasesDetectedDuringQuarterYear" arguments="${quarter},${year}"/> <br/>
			<spring:message code="mdrtb.tb08u.dateOfReport"/> ${reportDate }
			</td>
			</tr>
			</table>	
			<br/><br/>
		
			
			
			<table cellpadding="5" width="100%" border="1" >
				<tr>
					<th rowspan="2" colspan="2" align="center"><spring:message code="mdrtb.tb08u.registrationGroup"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.registered"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.cured"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.txCompleted"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.successfullyTreated"/></th>
					<th colspan="2" align="center"><spring:message code="mdrtb.tb08u.died"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.failure"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.ltfu"/></th>
					
					
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.notAssessed"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.total"/></th>
				</tr>
				<tr>
					<th align="center"><spring:message code="mdrtb.tb08u.tb"/></th>
					<th align="center"><spring:message code="mdrtb.tb08u.notTb"/></th>
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
					<td colspan="2"><spring:message code="mdrtb.tb08u.newCases"/></td>
					<td>${table1.newRegisteredShort }</td>
					
					<td>${table1.newCuredShort }</td>
					
					<td>${table1.newCompletedShort }</td>
					
					<td>${table1.newTxSuccessShort }</td>
					
					<td>${table1.newDiedTBShort }</td>
					
					<td>${table1.newDiedNotTBShort }</td>
					
					<td>${table1.newFailedShort }</td>
					
					<td>${table1.newDefaultedShort }</td>

					<td>${table1.newNotAssessedShort }</td>
					
					<td>${table1.newTotalShort }</td>
				</tr>
				<tr>
					<td rowspan="2" align="center"><spring:message code="mdrtb.tb08u.relapse"/></td>
					<td align="center">I</td>
					<td>${table1.relapse1RegisteredShort }</td>
					
					<td>${table1.relapse1CuredShort }</td>
					
					<td>${table1.relapse1CompletedShort }</td>
					
					<td>${table1.relapse1TxSuccessShort }</td>
					
					<td>${table1.relapse1DiedTBShort }</td>
					
					<td>${table1.relapse1DiedNotTBShort }</td>
					
					<td>${table1.relapse1FailedShort }</td>
					
					<td>${table1.relapse1DefaultedShort }</td>

					<td>${table1.relapse1NotAssessedShort }</td>
					
					<td>${table1.relapse1TotalShort }</td>
				</tr>
				<tr>
					<td align="center">II</td>
					<td>${table1.relapse2RegisteredShort }</td>
					
					<td>${table1.relapse2CuredShort }</td>
					
					<td>${table1.relapse2CompletedShort }</td>
					
					<td>${table1.relapse2TxSuccessShort }</td>
					
					<td>${table1.relapse2DiedTBShort }</td>
					
					<td>${table1.relapse2DiedNotTBShort }</td>
					
					<td>${table1.relapse2FailedShort }</td>
					
					<td>${table1.relapse2DefaultedShort }</td>

					<td>${table1.relapse2NotAssessedShort }</td>
					
					<td>${table1.relapse2TotalShort }</td>
				</tr>
				<tr>
					<td rowspan="2" align="center"><spring:message code="mdrtb.tb08u.afterDefault"/></td>
					<td align="center">I</td>
					<td>${table1.default1RegisteredShort }</td>
					
					<td>${table1.default1CuredShort }</td>
					
					<td>${table1.default1CompletedShort }</td>
					
					<td>${table1.default1TxSuccessShort }</td>
					
					<td>${table1.default1DiedTBShort }</td>
					
					<td>${table1.default1DiedNotTBShort }</td>
					
					<td>${table1.default1FailedShort }</td>
					
					<td>${table1.default1DefaultedShort }</td>

					<td>${table1.default1NotAssessedShort }</td>
					
					<td>${table1.default1TotalShort }</td>
				</tr>
				<tr>
					<td align="center">II</td>
					<td>${table1.default2RegisteredShort }</td>
					
					<td>${table1.default2CuredShort }</td>
					
					<td>${table1.default2CompletedShort }</td>
					
					<td>${table1.default2TxSuccessShort }</td>
					
					<td>${table1.default2DiedTBShort }</td>
					
					<td>${table1.default2DiedNotTBShort }</td>
					
					<td>${table1.default2FailedShort }</td>
					
					<td>${table1.default2DefaultedShort }</td>

					<td>${table1.default2NotAssessedShort }</td>
					
					<td>${table1.default2TotalShort }</td>
				</tr>
				<tr>
					<td rowspan="2" align="center"><spring:message code="mdrtb.tb08u.afterFailure"/></td>
					<td align="center">I</td>
					<td>${table1.failure1RegisteredShort }</td>
					
					<td>${table1.failure1CuredShort }</td>
					
					<td>${table1.failure1CompletedShort }</td>
					
					<td>${table1.failure1TxSuccessShort }</td>
					
					<td>${table1.failure1DiedTBShort }</td>
					
					<td>${table1.failure1DiedNotTBShort }</td>
					
					<td>${table1.failure1FailedShort }</td>
					
					<td>${table1.failure1DefaultedShort }</td>

					<td>${table1.failure1NotAssessedShort }</td>
					
					<td>${table1.failure1TotalShort }</td>
				</tr>
				<tr>
					<td align="center">II</td>
					<td>${table1.failure2RegisteredShort }</td>
					
					<td>${table1.failure2CuredShort }</td>
					
					<td>${table1.failure2CompletedShort }</td>
					
					<td>${table1.failure2TxSuccessShort }</td>
					
					<td>${table1.failure2DiedTBShort }</td>
					
					<td>${table1.failure2DiedNotTBShort }</td>
					
					<td>${table1.failure2FailedShort }</td>
					
					<td>${table1.failure2DefaultedShort }</td>

					<td>${table1.failure2NotAssessedShort }</td>
					
					<td>${table1.failure2TotalShort }</td>
				</tr>
				
				<tr>
					<td colspan="2"><spring:message code="mdrtb.tb08u.other"/></td>
					<td>${table1.otherRegisteredShort }</td>
					
					<td>${table1.otherCuredShort }</td>
					
					<td>${table1.otherCompletedShort }</td>
					
					<td>${table1.otherTxSuccessShort }</td>
					
					<td>${table1.otherDiedTBShort }</td>
					
					<td>${table1.otherDiedNotTBShort }</td>
					
					<td>${table1.otherFailedShort }</td>
					
					<td>${table1.otherDefaultedShort }</td>

					<td>${table1.otherNotAssessedShort }</td>
					
					<td>${table1.otherTotalShort }</td>
				</tr>
				
				<tr>
					<td colspan="2">mdrtb.tb08u.total</td>
					<td>${table1.totalRegisteredShort }</td>
					
					<td>${table1.totalCuredShort }</td>
					
					<td>${table1.totalCompletedShort }</td>
					
					<td>${table1.totalTxSuccessShort }</td>
					
					<td>${table1.totalDiedTBShort }</td>
					
					<td>${table1.totalDiedNotTBShort }</td>
					
					<td>${table1.totalFailedShort }</td>
					
					<td>${table1.totalDefaultedShort }</td>

					<td>${table1.totalNotAssessedShort }</td>
					
					<td>${table1.totalRegisteredShort }</td>
				</tr>
			</table>
			
			<!-- INDIV -->
			<table width="90%"><tr>
				<td width="90" align="left" style="font-size:14px; font-weight:bold;">
					<spring:message code="mdrtb.tb08u.titleIndiv"/> 
				</td>
				<td width="10%" align="right" style="font-size:14px; font-weight:bold;">TB 08y</td>
			</tr></table>
			<br/><br/>
			<table border="1" width="100%">
			
			<td>
			<spring:message code="mdrtb.tb08u.nameOfFacility"/> <u>&nbsp; ${fName} &nbsp;</u> <br/>
			<spring:message code="mdrtb.tb08u.regionCityDistrict"/>  <u> ${oName}/${dName} </u><br/>
			<spring:message code="mdrtb.tb08u.tbCoordinator"/> ____________________<spring:message code="mdrtb.tb08u.signature"/> ____________<br/>
			</td>
		
			<td>
			<spring:message code="mdrtb.tb08u.tbCasesDetectedDuringQuarterYear" arguments="${quarter},${year}"/> <br/>
			<spring:message code="mdrtb.tb08u.dateOfReport"/> ${reportDate }
			</td>
			</tr>
			</table>	
			<br/><br/>
			
			
			<table cellpadding="5" width="100%" border="1" >
				<tr>
					<th rowspan="2" colspan="2" align="center"><spring:message code="mdrtb.tb08u.registrationGroup"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.registered"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.cured"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.txCompleted"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.successfullyTreated"/></th>
					<th colspan="2" align="center"><spring:message code="mdrtb.tb08u.died"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.failure"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.ltfu"/></th>
					
					
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.notAssessed"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.total"/></th>
				</tr>
				<tr>
					<th align="center"><spring:message code="mdrtb.tb08u.tb"/></th>
					<th align="center"><spring:message code="mdrtb.tb08u.notTb"/></th>
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
					<td colspan="2"><spring:message code="mdrtb.tb08u.newCases"/></td>
					<td>${table1.newRegisteredIndiv }</td>
					
					<td>${table1.newCuredIndiv }</td>
					
					<td>${table1.newCompletedIndiv }</td>
					
					<td>${table1.newTxSuccessIndiv }</td>
					
					<td>${table1.newDiedTBIndiv }</td>
					
					<td>${table1.newDiedNotTBIndiv }</td>
					
					<td>${table1.newFailedIndiv }</td>
					
					<td>${table1.newDefaultedIndiv }</td>

					<td>${table1.newNotAssessedIndiv }</td>
					
					<td>${table1.newTotalIndiv }</td>
				</tr>
				<tr>
					<td rowspan="2" align="center"><spring:message code="mdrtb.tb08u.relapse"/></td>
					<td align="center">I</td>
					<td>${table1.relapse1RegisteredIndiv }</td>
					
					<td>${table1.relapse1CuredIndiv }</td>
					
					<td>${table1.relapse1CompletedIndiv }</td>
					
					<td>${table1.relapse1TxSuccessIndiv }</td>
					
					<td>${table1.relapse1DiedTBIndiv }</td>
					
					<td>${table1.relapse1DiedNotTBIndiv }</td>
					
					<td>${table1.relapse1FailedIndiv }</td>
					
					<td>${table1.relapse1DefaultedIndiv }</td>

					<td>${table1.relapse1NotAssessedIndiv }</td>
					
					<td>${table1.relapse1TotalIndiv }</td>
				</tr>
				<tr>
					<td align="center">II</td>
					<td>${table1.relapse2RegisteredIndiv }</td>
					
					<td>${table1.relapse2CuredIndiv }</td>
					
					<td>${table1.relapse2CompletedIndiv }</td>
					
					<td>${table1.relapse2TxSuccessIndiv }</td>
					
					<td>${table1.relapse2DiedTBIndiv }</td>
					
					<td>${table1.relapse2DiedNotTBIndiv }</td>
					
					<td>${table1.relapse2FailedIndiv }</td>
					
					<td>${table1.relapse2DefaultedIndiv }</td>

					<td>${table1.relapse2NotAssessedIndiv }</td>
					
					<td>${table1.relapse2TotalIndiv }</td>
				</tr>
				<tr>
					<td rowspan="2" align="center"><spring:message code="mdrtb.tb08u.afterDefault"/></td>
					<td align="center">I</td>
					<td>${table1.default1RegisteredIndiv }</td>
					
					<td>${table1.default1CuredIndiv }</td>
					
					<td>${table1.default1CompletedIndiv }</td>
					
					<td>${table1.default1TxSuccessIndiv }</td>
					
					<td>${table1.default1DiedTBIndiv }</td>
					
					<td>${table1.default1DiedNotTBIndiv }</td>
					
					<td>${table1.default1FailedIndiv }</td>
					
					<td>${table1.default1DefaultedIndiv }</td>

					<td>${table1.default1NotAssessedIndiv }</td>
					
					<td>${table1.default1TotalIndiv }</td>
				</tr>
				<tr>
					<td align="center">II</td>
					<td>${table1.default2RegisteredIndiv }</td>
					
					<td>${table1.default2CuredIndiv }</td>
					
					<td>${table1.default2CompletedIndiv }</td>
					
					<td>${table1.default2TxSuccessIndiv }</td>
					
					<td>${table1.default2DiedTBIndiv }</td>
					
					<td>${table1.default2DiedNotTBIndiv }</td>
					
					<td>${table1.default2FailedIndiv }</td>
					
					<td>${table1.default2DefaultedIndiv }</td>

					<td>${table1.default2NotAssessedIndiv }</td>
					
					<td>${table1.default2TotalIndiv }</td>
				</tr>
				<tr>
					<td rowspan="2" align="center"><spring:message code="mdrtb.tb08u.afterFailure"/></td>
					<td align="center">I</td>
					<td>${table1.failure1RegisteredIndiv }</td>
					
					<td>${table1.failure1CuredIndiv }</td>
					
					<td>${table1.failure1CompletedIndiv }</td>
					
					<td>${table1.failure1TxSuccessIndiv }</td>
					
					<td>${table1.failure1DiedTBIndiv }</td>
					
					<td>${table1.failure1DiedNotTBIndiv }</td>
					
					<td>${table1.failure1FailedIndiv }</td>
					
					<td>${table1.failure1DefaultedIndiv }</td>

					<td>${table1.failure1NotAssessedIndiv }</td>
					
					<td>${table1.failure1TotalIndiv }</td>
				</tr>
				<tr>
					<td align="center">II</td>
					<td>${table1.failure2RegisteredIndiv }</td>
					
					<td>${table1.failure2CuredIndiv }</td>
					
					<td>${table1.failure2CompletedIndiv }</td>
					
					<td>${table1.failure2TxSuccessIndiv }</td>
					
					<td>${table1.failure2DiedTBIndiv }</td>
					
					<td>${table1.failure2DiedNotTBIndiv }</td>
					
					<td>${table1.failure2FailedIndiv }</td>
					
					<td>${table1.failure2DefaultedIndiv }</td>

					<td>${table1.failure2NotAssessedIndiv }</td>
					
					<td>${table1.failure2TotalIndiv }</td>
				</tr>
				
				<tr>
					<td colspan="2"><spring:message code="mdrtb.tb08u.other"/></td>
					<td>${table1.otherRegisteredIndiv }</td>
					
					<td>${table1.otherCuredIndiv }</td>
					
					<td>${table1.otherCompletedIndiv }</td>
					
					<td>${table1.otherTxSuccessIndiv }</td>
					
					<td>${table1.otherDiedTBIndiv }</td>
					
					<td>${table1.otherDiedNotTBIndiv }</td>
					
					<td>${table1.otherFailedIndiv }</td>
					
					<td>${table1.otherDefaultedIndiv }</td>

					<td>${table1.otherNotAssessedIndiv }</td>
					
					<td>${table1.otherTotalIndiv }</td>
				</tr>
				
				<tr>
					<td colspan="2">mdrtb.tb08u.total</td>
					<td>${table1.totalRegisteredIndiv }</td>
					
					<td>${table1.totalCuredIndiv }</td>
					
					<td>${table1.totalCompletedIndiv }</td>
					
					<td>${table1.totalTxSuccessIndiv }</td>
					
					<td>${table1.totalDiedTBIndiv }</td>
					
					<td>${table1.totalDiedNotTBIndiv }</td>
					
					<td>${table1.totalFailedIndiv }</td>
					
					<td>${table1.totalDefaultedIndiv }</td>

					<td>${table1.totalNotAssessedIndiv }</td>
					
					<td>${table1.totalRegisteredIndiv }</td>
				</tr>
			</table>
			
			<!-- STANDARD -->
			<table width="90%"><tr>
				<td width="90" align="left" style="font-size:14px; font-weight:bold;">
					<spring:message code="mdrtb.tb08u.titleStandard"/>
				</td>
				<td width="10%" align="right" style="font-size:14px; font-weight:bold;">TB 08y</td>
			</tr></table>
			<br/><br/>
			<table border="1" width="100%">
			
			<td>
			<spring:message code="mdrtb.tb08u.nameOfFacility"/> <u>&nbsp; ${fName} &nbsp;</u> <br/>
			<spring:message code="mdrtb.tb08u.regionCityDistrict"/>  <u> ${oName}/${dName} </u><br/>
			<spring:message code="mdrtb.tb08u.tbCoordinator"/> ____________________<spring:message code="mdrtb.tb08u.signature"/> ____________<br/>
			</td>
		
			<td>
			<spring:message code="mdrtb.tb08u.tbCasesDetectedDuringQuarterYear" arguments="${quarter},${year}"/> <br/>
			<spring:message code="mdrtb.tb08u.dateOfReport"/> ${reportDate }
			</td>
			</tr>
			</table>	
			<br/><br/>
		
			
			
			<table cellpadding="5" width="100%" border="1" >
				<tr>
					<th rowspan="2" colspan="2" align="center"><spring:message code="mdrtb.tb08u.registrationGroup"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.registered"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.cured"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.txCompleted"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.successfullyTreated"/></th>
					<th colspan="2" align="center"><spring:message code="mdrtb.tb08u.died"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.failure"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.ltfu"/></th>
					
					
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.notAssessed"/></th>
					<th rowspan="2" align="center"><spring:message code="mdrtb.tb08u.total"/></th>
				</tr>
				<tr>
					<th align="center"><spring:message code="mdrtb.tb08u.tb"/></th>
					<th align="center"><spring:message code="mdrtb.tb08u.notTb"/></th>
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
					<td colspan="2"><spring:message code="mdrtb.tb08u.newCases"/></td>
					<td>${table1.newRegisteredStandard }</td>
					
					<td>${table1.newCuredStandard }</td>
					
					<td>${table1.newCompletedStandard }</td>
					
					<td>${table1.newTxSuccessStandard }</td>
					
					<td>${table1.newDiedTBStandard }</td>
					
					<td>${table1.newDiedNotTBStandard }</td>
					
					<td>${table1.newFailedStandard }</td>
					
					<td>${table1.newDefaultedStandard }</td>

					<td>${table1.newNotAssessedStandard }</td>
					
					<td>${table1.newTotalStandard }</td>
				</tr>
				<tr>
					<td rowspan="2" align="center"><spring:message code="mdrtb.tb08u.relapse"/></td>
					<td align="center">I</td>
					<td>${table1.relapse1RegisteredStandard }</td>
					
					<td>${table1.relapse1CuredStandard }</td>
					
					<td>${table1.relapse1CompletedStandard }</td>
					
					<td>${table1.relapse1TxSuccessStandard }</td>
					
					<td>${table1.relapse1DiedTBStandard }</td>
					
					<td>${table1.relapse1DiedNotTBStandard }</td>
					
					<td>${table1.relapse1FailedStandard }</td>
					
					<td>${table1.relapse1DefaultedStandard }</td>

					<td>${table1.relapse1NotAssessedStandard }</td>
					
					<td>${table1.relapse1TotalStandard }</td>
				</tr>
				<tr>
					<td align="center">II</td>
					<td>${table1.relapse2RegisteredStandard }</td>
					
					<td>${table1.relapse2CuredStandard }</td>
					
					<td>${table1.relapse2CompletedStandard }</td>
					
					<td>${table1.relapse2TxSuccessStandard }</td>
					
					<td>${table1.relapse2DiedTBStandard }</td>
					
					<td>${table1.relapse2DiedNotTBStandard }</td>
					
					<td>${table1.relapse2FailedStandard }</td>
					
					<td>${table1.relapse2DefaultedStandard }</td>

					<td>${table1.relapse2NotAssessedStandard }</td>
					
					<td>${table1.relapse2TotalStandard }</td>
				</tr>
				<tr>
					<td rowspan="2" align="center"><spring:message code="mdrtb.tb08u.afterDefault"/></td>
					<td align="center">I</td>
					<td>${table1.default1RegisteredStandard }</td>
					
					<td>${table1.default1CuredStandard }</td>
					
					<td>${table1.default1CompletedStandard }</td>
					
					<td>${table1.default1TxSuccessStandard }</td>
					
					<td>${table1.default1DiedTBStandard }</td>
					
					<td>${table1.default1DiedNotTBStandard }</td>
					
					<td>${table1.default1FailedStandard }</td>
					
					<td>${table1.default1DefaultedStandard }</td>

					<td>${table1.default1NotAssessedStandard }</td>
					
					<td>${table1.default1TotalStandard }</td>
				</tr>
				<tr>
					<td align="center">II</td>
					<td>${table1.default2RegisteredStandard }</td>
					
					<td>${table1.default2CuredStandard }</td>
					
					<td>${table1.default2CompletedStandard }</td>
					
					<td>${table1.default2TxSuccessStandard }</td>
					
					<td>${table1.default2DiedTBStandard }</td>
					
					<td>${table1.default2DiedNotTBStandard }</td>
					
					<td>${table1.default2FailedStandard }</td>
					
					<td>${table1.default2DefaultedStandard }</td>

					<td>${table1.default2NotAssessedStandard }</td>
					
					<td>${table1.default2TotalStandard }</td>
				</tr>
				<tr>
					<td rowspan="2" align="center"><spring:message code="mdrtb.tb08u.afterFailure"/></td>
					<td align="center">I</td>
					<td>${table1.failure1RegisteredStandard }</td>
					
					<td>${table1.failure1CuredStandard }</td>
					
					<td>${table1.failure1CompletedStandard }</td>
					
					<td>${table1.failure1TxSuccessStandard }</td>
					
					<td>${table1.failure1DiedTBStandard }</td>
					
					<td>${table1.failure1DiedNotTBStandard }</td>
					
					<td>${table1.failure1FailedStandard }</td>
					
					<td>${table1.failure1DefaultedStandard }</td>

					<td>${table1.failure1NotAssessedStandard }</td>
					
					<td>${table1.failure1TotalStandard }</td>
				</tr>
				<tr>
					<td align="center">II</td>
					<td>${table1.failure2RegisteredStandard }</td>
					
					<td>${table1.failure2CuredStandard }</td>
					
					<td>${table1.failure2CompletedStandard }</td>
					
					<td>${table1.failure2TxSuccessStandard }</td>
					
					<td>${table1.failure2DiedTBStandard }</td>
					
					<td>${table1.failure2DiedNotTBStandard }</td>
					
					<td>${table1.failure2FailedStandard }</td>
					
					<td>${table1.failure2DefaultedStandard }</td>

					<td>${table1.failure2NotAssessedStandard }</td>
					
					<td>${table1.failure2TotalStandard }</td>
				</tr>
				
				<tr>
					<td colspan="2"><spring:message code="mdrtb.tb08u.other"/></td>
					<td>${table1.otherRegisteredStandard }</td>
					
					<td>${table1.otherCuredStandard }</td>
					
					<td>${table1.otherCompletedStandard }</td>
					
					<td>${table1.otherTxSuccessStandard }</td>
					
					<td>${table1.otherDiedTBStandard }</td>
					
					<td>${table1.otherDiedNotTBStandard }</td>
					
					<td>${table1.otherFailedStandard }</td>
					
					<td>${table1.otherDefaultedStandard }</td>

					<td>${table1.otherNotAssessedStandard }</td>
					
					<td>${table1.otherTotalStandard }</td>
				</tr>
				
				<tr>
					<td colspan="2">mdrtb.tb08u.total</td>
					<td>${table1.totalRegisteredStandard }</td>
					
					<td>${table1.totalCuredStandard }</td>
					
					<td>${table1.totalCompletedStandard }</td>
					
					<td>${table1.totalTxSuccessStandard }</td>
					
					<td>${table1.totalDiedTBStandard }</td>
					
					<td>${table1.totalDiedNotTBStandard }</td>
					
					<td>${table1.totalFailedStandard }</td>
					
					<td>${table1.totalDefaultedStandard }</td>

					<td>${table1.totalNotAssessedStandard }</td>
					
					<td>${table1.totalRegisteredStandard }</td>
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
