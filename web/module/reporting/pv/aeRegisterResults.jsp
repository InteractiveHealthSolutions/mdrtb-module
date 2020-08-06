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
	<title><spring:message code="mdrtb.pv.register.title"/></title>
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

		    mywindow.document.write('<html><head><title><spring:message code="mdrtb.pv.register.title" text="AE Register"/></title>');
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
			<td align="center" style="font-size:14px; font-weight:bold;border:0px" width="100%"><spring:message code="mdrtb.pv.register.title"/></td>
			<!-- <td align="right" style="font-size:14px; font-weight:bold;border:0px" valign="top" width="10%">AE</td> -->
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
<spring:message code="mdrtb.pv.quarter" />,<spring:message code="mdrtb.pv.year" />: <u>${quarter}, ${year}</u></br>
<spring:message code="mdrtb.pv.reportDate" />:<u>&nbsp; ${reportDate}</u>
</td>
</tr>


</table>


	<table border="1" cellpadding="1" cellspacing="1" dir="ltr"
			style="width: 980px;">
			<tbody>
				<tr>
					<th rowspan="2"><spring:message code="mdrtb.pv.serialNumber"
							text="serialz" /></th>
					<th rowspan="2"><spring:message code="mdrtb.pv.register.patientId" text="patz" /></th>
					<th rowspan="2"><spring:message code="mdrtb.pv.register.patientName" text="namez" /></th>
					<th rowspan="2"><spring:message code="mdrtb.pv.register.patientBirthdate"
							text="dobz" /></th>
					<th rowspan="2"><spring:message code="mdrtb.pv.register.onsetDate" text="datez" /></th>
					<th rowspan="2"><spring:message code="mdrtb.pv.register.adverseEvent" text="aez" /></th>
					<th rowspan="2"><spring:message code="mdrtb.pv.register.diagnosticInvestigation"
							text="diz" /></th>
					<th colspan="2"><spring:message code="mdrtb.pv.register.typeOfEvent" text="typez" /></th>
					
					<th rowspan="2"><spring:message code="mdrtb.pv.register.requiringAncillary"
							text="ancz" /></th>
					<th rowspan="2"><spring:message code="mdrtb.pv.register.requiringChanges"
							text="ancz" /></th>
					<th rowspan="2"><spring:message code="mdrtb.pv.register.suspectedDrug" text="drugz" /></th>
					<th rowspan="2"><spring:message code="mdrtb.pv.register.suspectedDrugTxStartDate"
							text="drugz" /></th>
					
					<th rowspan="2"><spring:message code="mdrtb.pv.register.actionTaken" text="actionz" /></th>
					<th rowspan="2"><spring:message code="mdrtb.pv.register.actionOutcome"
							text="outcomez" /></th>
					<th rowspan="2"><spring:message code="mdrtb.pv.register.treatmentRegimenAtOnset"
							text="regimenz" /></th>

					<th rowspan="2"><spring:message code="mdrtb.pv.register.drugRechallenge" text="rcz" /></th>
					<th rowspan="2"><spring:message code="mdrtb.pv.register.causalityAssessment" text="car" /></th>
					<th rowspan="2"><spring:message code="mdrtb.pv.register.yellowCardDate"
							text="Yellowz" /></th>

					<!-- <th rowspan="2"><spring:message code="mdrtb.pv.outcomeDate" text="datez"/></th>
		<th rowspan="2"><spring:message code="mdrtb.pv.eventOnsetLocation" text="placez"/></th>
		<th rowspan="2"><spring:message code="mdrtb.pv.meddraCode" text="codez"/></th>-->

					<th rowspan="2"><spring:message code="mdrtb.pv.register.comments" text="Commentz" /></th>
				</tr>
				
				<tr>
					<th><spring:message code="mdrtb.pv.register.serious" text="seriousz" /></th>
					<th><spring:message code="mdrtb.pv.register.ofSpecialInterest" text="specialz" /></th>
				</tr>
				
				<tr>
					<td align="center">1</td>
					<td align="center">2</td>
					<td align="center">3</td>
					<td align="center">4</td>
					<td align="center">5</td>
					<td align="center">6</td>
					<td align="center">7</td>
					<td align="center">8a</td>
					<td align="center">8b</td>
					<td align="center">9</td>
					<td align="center">10</td>
					<td align="center">11</td>
					<td align="center">12</td>
					<td align="center">13</td>
					<td align="center">14</td>
					<td align="center">15</td>
					<td align="center">16</td>
					<td align="center">17</td>
					<td align="center">18</td>
					<td align="center">19</td>
					
				</tr>

				<c:forEach var="form" items="${forms}" varStatus="loop">
					<tr>
						<td><a
							href="${pageContext.request.contextPath}/module/mdrtb/form/ae.form?encounterId=${form.AEForm.encounter.id}&patientProgramId=${form.AEForm.patProgId}"
							target="_blank">${loop.index+1}</a></td>
						<td align="left">${form.identifier}</td>
						<td align="left">${form.patientName}</td>
						<td align="center">${form.birthDate}</td>
						<td align="center">${form.onsetDate}</td>
						<td align="left">${form.AEDescription}</td>
						<td align="left">${form.diagnosticInvestigation}</td>
						<td align="center">${form.serious}</td>
						<td align="center">${form.ofSpecialInterest}</td>
						<td align="center">${form.ancillaryDrugs}</td>
						<td align="center">${form.doseChanged}</td>
						<td align="center">${form.suspectedDrug}</td>
						<td>&nbsp;</td>
						<td align="left">${form.actionTaken}</td>
						<td align="left">${form.actionOutcome}</td>
						<td align="left">${form.txRegimen}</td>
						<td  align="left">${form.drugRechallenge}</td>
						<td  align="left">${form.causalityAssessment}</td>
						<td  align="center">${form.yellowCardDate}</td>
						<td align=left">${form.comments}</td>
					</tr>

				</c:forEach>

			</tbody>
	</table>

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
