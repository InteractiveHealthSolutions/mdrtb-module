<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%>
<%@ include file="../mdrtbHeader.jsp"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>
<html>
	<head>
		<meta http-equiv="content-type" content="text/plain; charset=UTF-8"/>

	
	
		<title>TB 08</title>
		
	
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

		    mywindow.document.write('<html><head><title><spring:message code="mdrtb.form8.title" text="Form8"/></title>');
		    mywindow.document.write('</head><body >');
		   // mywindow.document.write('<h1><spring:message code="mdrtb.pv.aeForm" text="AE"/></h1>');
		    mywindow.document.write(document.getElementById("form8").innerHTML);
		    
		    mywindow.document.write('</body></html>');

		    mywindow.document.close(); // necessary for IE >= 10
		    mywindow.focus(); // necessary for IE >= 10*/

		    mywindow.print();
		    mywindow.close();

		    return true;
		}
			var tableToExcel = (function() {
			  var uri = 'data:application/vnd.ms-excel;base64,'
			    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>Form8</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta http-equiv="content-type" content="text/plain; charset=UTF-8"/></head><body><table>{table}</table></body></html>'
			    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
			    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
			  return function(table, name) {
			    if (!table.nodeType) table = document.getElementById(table)
			    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
			    window.location.href = uri + base64(format(template, ctx))
			  }
			})()
			function savePdf(action, reportName, formPath) {
				var tableData = (document.getElementById("form8")).innerHTML.toString();
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
						savePdf("closeReport.form", "Form8", "form8Results");
					}
				});
				/* $("#tableToPdf").click(function(){
					savePdf("exportReport.form", "TB 08", "tb08Results");
				}); */
			});
		</script>
		
		<div id="form8" style="font-size:smaller; width:980px;">
		<!-- <style>
			th {vertical-align:top; text-align:center;}
			th, td {font-size:smaller;border: 1px solid #000000}
			border {border: 1px solid #000000}

		</style> -->
		
		
			<!-- <table width="100%" border="0">
			<tr>
				<td align="left" width="90%" style="font-size:14px; font-weight:bold;border:0px">&nbsp;   
				</td>
				<td align="right" width="10%" style="font-size:14px; font-weight:bold;border:0px">Form8</td>
			</tr>
			<tr><td style="font-size:14px; font-weight:bold;border:0px">&nbsp;</td><td style="font-size:14px; font-weight:bold;border:0px">&nbsp;</td></tr>
			<tr>
				
				<td width="90%" align="center" style="font-size:14px; font-weight:bold;border:0px">
						<spring:message code="mdrtb.form8.title"/>
				</td>
				<td width="10%" align="right" style="font-size:14px; font-weight:bold;border:0px;" valign="top" border="0">&nbsp;</td>
			</tr></table> -->
			<br/><br/>
		<%-- <table border="1" width="100%">
		<tr>
		<td>
		<spring:message code="dotsreports.tb08.nameOfFacility"/> <u>&nbsp; ${fName} &nbsp;</u> <br/>
		<spring:message code="dotsreports.tb08.regionCityDistrict"/>  <u> ${oName}/${dName} </u><br/>
		<spring:message code="dotsreports.tb08.tbCoordinator"/> ____________________<spring:message code="dotsreports.tb08.signature"/> ____________<br/>
		</td>
		
		<td>
		<spring:message code="dotsreports.tb08.tbCasesDetectedDuringQuarterYear" arguments="${quarter},${year}"/> <br/>
		<spring:message code="dotsreports.tb08.dateOfReport"/> ${reportDate }
		</td>
		</tr>
		</table>	 --%>
		
		<h3 align="center"><spring:message code="mdrtb.form8.main"/></h3>
		
		<table border = "1" cellspacing="0" cellpadding="0" style="width: 100%;" >
		<tr>
			<td colspan="8" align="center"><spring:message code="mdrtb.form8.main.codes"/></td>
		</tr>
		<tr>
			<td align="center"><spring:message code="mdrtb.form8.main.column1"/></td>
			<td align="center"><spring:message code="mdrtb.form8.main.column2"/></td>
			<td align="center"><spring:message code="mdrtb.form8.main.column3"/></td>
			<td align="center"><spring:message code="mdrtb.form8.main.column4"/></td>
			<td align="center"><spring:message code="mdrtb.form8.main.column5"/></td>
			<td align="center"><spring:message code="mdrtb.form8.main.column6"/></td>
			<td align="center"><spring:message code="mdrtb.form8.main.column7"/></td>
			<td align="center"><spring:message code="mdrtb.form8.main.column8"/></td>
		</tr>
		<tr>
			<td align="center">1</td>
			<td align="center">2</td>
			<td align="center">3</td>
			<td align="center">4</td>
			<td align="center">5</td>
			<td align="center">6</td>
			<td align="center">7</td>
			<td align="center">8</td>
		</tr>
		<tr>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
		</tr>
		</table>
		<br/></br>
		<table style="width: 100%; border: 0px" >
		<tr>
			<td>&nbsp;</td>
			<td align="right"><b><spring:message code="mdrtb.form8.title"/></b></td>
		<tr>
		<tr>
			<td style="width: 50%">&nbsp;</td>
			<td style="width: 50%"><spring:message code="mdrtb.form8.main.statCommittee"/><br/>
				<spring:message code="mdrtb.form8.main.repTajikistan"/><br/><br/>
				<center><b><spring:message code="mdrtb.form8.main.postageAnnual"/></b></center><br/><br/>
				<spring:message code="mdrtb.form8.main.legal"/></td>
		</tr>
		
		</table>
		<br/><br/>	
		
		<spring:message code="mdrtb.oblast"/> <u>${oName }</u>____ <spring:message code="mdrtb.district"/> <u>${dName }</u>____  <spring:message code="mdrtb.form8.main.city"/> ____________________<br/>
		<spring:message code="mdrtb.facility"/> <u>${fName }</u>________
		<spring:message code="mdrtb.form8.main.institutionAddress"/> ____________________________________________________________________________________________________<br/>
		<spring:message code="mdrtb.form8.main.ownership"/><br/>
		
		<br/><br/>	
		<h4 align="center"><spring:message code="mdrtb.form8.main.sub"/></h4>
		<h4 align="center"><spring:message code="mdrtb.form8.main.forTheYear" arguments="${year }"/></h4>
		
		
		<h5 align="center"><spring:message code="mdrtb.form8.table1.title"/></h5>		
		
		<table  border="1" cellpadding="1" cellspacing="1" style="width: 100%;">
			<tr align="center">
				<td rowspan="2"><spring:message code="mdrtb.form8.item"/></td>
				<td rowspan="2"><spring:message code="mdrtb.form8.sex"/></td>
				<td rowspan="2"><spring:message code="mdrtb.form8.icd"/></td>
				<td rowspan="2"><spring:message code="mdrtb.form8.number"/></td>
				<td rowspan="2"><spring:message code="mdrtb.form8.total"/></td>
				<td colspan="10" align="center"><spring:message code="mdrtb.form8.including"/></td>
				<td rowspan="2" align="center"><spring:message code="mdrtb.form8.includingRural"/></td>
			</tr>
			<tr align="center">
				<td>0-4 <spring:message code="mdrtb.form8.years"/></td>	
				<td>5-14 <spring:message code="mdrtb.form8.years"/></td>
				<td>15-17 <spring:message code="mdrtb.form8.years"/></td>	
				<td>18-19 <spring:message code="mdrtb.form8.years"/></td>
				<td>20-24 <spring:message code="mdrtb.form8.years"/></td>	
				<td>25-34 <spring:message code="mdrtb.form8.years"/></td>
				<td>35-44 <spring:message code="mdrtb.form8.years"/></td>	
				<td>45-54 <spring:message code="mdrtb.form8.years"/></td>
				<td>55-64 <spring:message code="mdrtb.form8.years"/></td>	
				<td>65+ <spring:message code="mdrtb.form8.yearsAndOlder"/></td>
			</tr>
			<tr align="center">
				<td>A</td>	
				<td>B</td>
				<td>C</td>	
				<td>D</td>
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
				<td>11</td>
				<td>12</td>
			</tr>
			<tr align="center">
				<td rowspan="2" align="left"><b><spring:message code="mdrtb.form8.activeTotal"/></b></td>	
				<td>M</td>
				<td rowspan="2">A15-19</td>	
				<td>01</td>
				<td>${table1.activeTBTotalMale}</td>	
				<td>${table1.activeTB04Male}</td>	
				<td>${table1.activeTB0514Male}</td>
				<td>${table1.activeTB1517Male}</td>
				<td>${table1.activeTB1819Male}</td>
				<td>${table1.activeTB2024Male}</td>
				<td>${table1.activeTB2534Male}</td>
				<td>${table1.activeTB3544Male}</td>
				<td>${table1.activeTB4554Male}</td>
				<td>${table1.activeTB5564Male}</td>
				<td>${table1.activeTB65Male}</td>
				<td>${table1.activeTBRuralMale}</td>				
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>02</td>
				<td>${table1.activeTBTotalFemale}</td>	
				<td>${table1.activeTB04Female}</td>	
				<td>${table1.activeTB0514Female}</td>
				<td>${table1.activeTB1517Female}</td>
				<td>${table1.activeTB1819Female}</td>
				<td>${table1.activeTB2024Female}</td>
				<td>${table1.activeTB2534Female}</td>
				<td>${table1.activeTB3544Female}</td>
				<td>${table1.activeTB4554Female}</td>
				<td>${table1.activeTB5564Female}</td>
				<td>${table1.activeTB65Female}</td>
				<td>${table1.activeTBRuralFemale}</td>		
				
			</tr>
			<tr align="center">
				<td rowspan="2" align="left"><spring:message code="mdrtb.form8.includingRespiratory"/></td>	
				<td>M</td>
				<td rowspan="2">A15-16</td>	
				<td>03</td>
				<td>${table1.respiratoryTBTotalMale}</td>	
				<td>${table1.respiratoryTB04Male}</td>	
				<td>${table1.respiratoryTB0514Male}</td>
				<td>${table1.respiratoryTB1517Male}</td>
				<td>${table1.respiratoryTB1819Male}</td>
				<td>${table1.respiratoryTB2024Male}</td>
				<td>${table1.respiratoryTB2534Male}</td>
				<td>${table1.respiratoryTB3544Male}</td>
				<td>${table1.respiratoryTB4554Male}</td>
				<td>${table1.respiratoryTB5564Male}</td>
				<td>${table1.respiratoryTB65Male}</td>
				<td>${table1.respiratoryTBRuralMale}</td>	
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>04</td>
				<td>${table1.respiratoryTBTotalFemale}</td>	
				<td>${table1.respiratoryTB04Female}</td>	
				<td>${table1.respiratoryTB0514Female}</td>
				<td>${table1.respiratoryTB1517Female}</td>
				<td>${table1.respiratoryTB1819Female}</td>
				<td>${table1.respiratoryTB2024Female}</td>
				<td>${table1.respiratoryTB2534Female}</td>
				<td>${table1.respiratoryTB3544Female}</td>
				<td>${table1.respiratoryTB4554Female}</td>
				<td>${table1.respiratoryTB5564Female}</td>
				<td>${table1.respiratoryTB65Female}</td>
				<td>${table1.respiratoryTBRuralFemale}</td>
			</tr>
			<tr align="center">
				<td rowspan="2" align="left"><spring:message code="mdrtb.form8.pulmonary"/></td>	
				<td>M</td>
				<td rowspan="2">A15.0</td>	
				<td>05</td>
				<td>${table1.pulmonaryTBTotalMale}</td>	
				<td>${table1.pulmonaryTB04Male}</td>	
				<td>${table1.pulmonaryTB0514Male}</td>
				<td>${table1.pulmonaryTB1517Male}</td>
				<td>${table1.pulmonaryTB1819Male}</td>
				<td>${table1.pulmonaryTB2024Male}</td>
				<td>${table1.pulmonaryTB2534Male}</td>
				<td>${table1.pulmonaryTB3544Male}</td>
				<td>${table1.pulmonaryTB4554Male}</td>
				<td>${table1.pulmonaryTB5564Male}</td>
				<td>${table1.pulmonaryTB65Male}</td>
				<td>${table1.pulmonaryTBRuralMale}</td>
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>06</td>
				<td>${table1.pulmonaryTBTotalFemale}</td>	
				<td>${table1.pulmonaryTB04Female}</td>	
				<td>${table1.pulmonaryTB0514Female}</td>
				<td>${table1.pulmonaryTB1517Female}</td>
				<td>${table1.pulmonaryTB1819Female}</td>
				<td>${table1.pulmonaryTB2024Female}</td>
				<td>${table1.pulmonaryTB2534Female}</td>
				<td>${table1.pulmonaryTB3544Female}</td>
				<td>${table1.pulmonaryTB4554Female}</td>
				<td>${table1.pulmonaryTB5564Female}</td>
				<td>${table1.pulmonaryTB65Female}</td>
				<td>${table1.pulmonaryTBRuralFemale}</td>
			</tr>
			<tr align="center">
				<td rowspan="2" align="left"><spring:message code="mdrtb.form8.withBacterioExcretion"/></td>	
				<td>M</td>
				<td rowspan="2">A15.0</td>	
				<td>07</td>
				<td>${table1.bacExTBTotalMale}</td>	
				<td>${table1.bacExTB04Male}</td>	
				<td>${table1.bacExTB0514Male}</td>
				<td>${table1.bacExTB1517Male}</td>
				<td>${table1.bacExTB1819Male}</td>
				<td>${table1.bacExTB2024Male}</td>
				<td>${table1.bacExTB2534Male}</td>
				<td>${table1.bacExTB3544Male}</td>
				<td>${table1.bacExTB4554Male}</td>
				<td>${table1.bacExTB5564Male}</td>
				<td>${table1.bacExTB65Male}</td>
				<td>${table1.bacExTBRuralMale}</td>
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>08</td>
				<td>${table1.bacExTBTotalFemale}</td>	
				<td>${table1.bacExTB04Female}</td>	
				<td>${table1.bacExTB0514Female}</td>
				<td>${table1.bacExTB1517Female}</td>
				<td>${table1.bacExTB1819Female}</td>
				<td>${table1.bacExTB2024Female}</td>
				<td>${table1.bacExTB2534Female}</td>
				<td>${table1.bacExTB3544Female}</td>
				<td>${table1.bacExTB4554Female}</td>
				<td>${table1.bacExTB5564Female}</td>
				<td>${table1.bacExTB65Female}</td>
				<td>${table1.bacExTBRuralFemale}</td>
			</tr>
			<tr align="center">
				<td rowspan="2" align="left"><spring:message code="mdrtb.form8.fibroCavernous"/></td>	
				<td>M</td>
				<td rowspan="2">A15.0<br/>A16.0</td>	
				<td>09</td>
				<td>${table1.fibCavTBTotalMale}</td>	
				<td>${table1.fibCavTB04Male}</td>	
				<td>${table1.fibCavTB0514Male}</td>
				<td>${table1.fibCavTB1517Male}</td>
				<td>${table1.fibCavTB1819Male}</td>
				<td>${table1.fibCavTB2024Male}</td>
				<td>${table1.fibCavTB2534Male}</td>
				<td>${table1.fibCavTB3544Male}</td>
				<td>${table1.fibCavTB4554Male}</td>
				<td>${table1.fibCavTB5564Male}</td>
				<td>${table1.fibCavTB65Male}</td>
				<td>${table1.fibCavTBRuralMale}</td>
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>10</td>
				<td>${table1.fibCavTBTotalFemale}</td>	
				<td>${table1.fibCavTB04Female}</td>	
				<td>${table1.fibCavTB0514Female}</td>
				<td>${table1.fibCavTB1517Female}</td>
				<td>${table1.fibCavTB1819Female}</td>
				<td>${table1.fibCavTB2024Female}</td>
				<td>${table1.fibCavTB2534Female}</td>
				<td>${table1.fibCavTB3544Female}</td>
				<td>${table1.fibCavTB4554Female}</td>
				<td>${table1.fibCavTB5564Female}</td>
				<td>${table1.fibCavTB65Female}</td>
				<td>${table1.fibCavTBRuralFemale}</td>
			</tr>
		
		<tr align="center">
				<td rowspan="2" align="left"><b><spring:message code="mdrtb.form8.nervousSystem"/></b></td>	
				<td>M</td>
				<td rowspan="2">A17</td>	
				<td>11</td>
				<td>${table1.nervousSystemTBTotalMale}</td>	
				<td>${table1.nervousSystemTB04Male}</td>	
				<td>${table1.nervousSystemTB0514Male}</td>
				<td>${table1.nervousSystemTB1517Male}</td>
				<td>${table1.nervousSystemTB1819Male}</td>
				<td>${table1.nervousSystemTB2024Male}</td>
				<td>${table1.nervousSystemTB2534Male}</td>
				<td>${table1.nervousSystemTB3544Male}</td>
				<td>${table1.nervousSystemTB4554Male}</td>
				<td>${table1.nervousSystemTB5564Male}</td>
				<td>${table1.nervousSystemTB65Male}</td>
				<td>${table1.nervousSystemTBRuralMale}</td>
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>12</td>
				<td>${table1.nervousSystemTBTotalFemale}</td>	
				<td>${table1.nervousSystemTB04Female}</td>	
				<td>${table1.nervousSystemTB0514Female}</td>
				<td>${table1.nervousSystemTB1517Female}</td>
				<td>${table1.nervousSystemTB1819Female}</td>
				<td>${table1.nervousSystemTB2024Female}</td>
				<td>${table1.nervousSystemTB2534Female}</td>
				<td>${table1.nervousSystemTB3544Female}</td>
				<td>${table1.nervousSystemTB4554Female}</td>
				<td>${table1.nervousSystemTB5564Female}</td>
				<td>${table1.nervousSystemTB65Female}</td>
				<td>${table1.nervousSystemTBRuralFemale}</td>
			</tr>
			<tr align="center">
				<td rowspan="2" align="left"><b><spring:message code="mdrtb.form8.otherOrgans"/></b></td>	
				<td>M</td>
				<td rowspan="2">A18-18.5</td>	
				<td>13</td>
				<td>${table1.otherOrgansTBTotalMale}</td>	
				<td>${table1.otherOrgansTB04Male}</td>	
				<td>${table1.otherOrgansTB0514Male}</td>
				<td>${table1.otherOrgansTB1517Male}</td>
				<td>${table1.otherOrgansTB1819Male}</td>
				<td>${table1.otherOrgansTB2024Male}</td>
				<td>${table1.otherOrgansTB2534Male}</td>
				<td>${table1.otherOrgansTB3544Male}</td>
				<td>${table1.otherOrgansTB4554Male}</td>
				<td>${table1.otherOrgansTB5564Male}</td>
				<td>${table1.otherOrgansTB65Male}</td>
				<td>${table1.otherOrgansTBRuralMale}</td>
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>14</td>
				<td>${table1.otherOrgansTBTotalFemale}</td>	
				<td>${table1.otherOrgansTB04Female}</td>	
				<td>${table1.otherOrgansTB0514Female}</td>
				<td>${table1.otherOrgansTB1517Female}</td>
				<td>${table1.otherOrgansTB1819Female}</td>
				<td>${table1.otherOrgansTB2024Female}</td>
				<td>${table1.otherOrgansTB2534Female}</td>
				<td>${table1.otherOrgansTB3544Female}</td>
				<td>${table1.otherOrgansTB4554Female}</td>
				<td>${table1.otherOrgansTB5564Female}</td>
				<td>${table1.otherOrgansTB65Female}</td>
				<td>${table1.otherOrgansTBRuralFemale}</td>
			</tr>
			<tr align="center">
				<td rowspan="2" align="left"><spring:message code="mdrtb.form8.osteoarticular"/></td>	
				<td>M</td>
				<td rowspan="2">A18.0</td>	
				<td>15</td>
				<td>${table1.osteoarticularTBTotalMale}</td>	
				<td>${table1.osteoarticularTB04Male}</td>	
				<td>${table1.osteoarticularTB0514Male}</td>
				<td>${table1.osteoarticularTB1517Male}</td>
				<td>${table1.osteoarticularTB1819Male}</td>
				<td>${table1.osteoarticularTB2024Male}</td>
				<td>${table1.osteoarticularTB2534Male}</td>
				<td>${table1.osteoarticularTB3544Male}</td>
				<td>${table1.osteoarticularTB4554Male}</td>
				<td>${table1.osteoarticularTB5564Male}</td>
				<td>${table1.osteoarticularTB65Male}</td>
				<td>${table1.osteoarticularTBRuralMale}</td>
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>16</td>
				<td>${table1.osteoarticularTBTotalFemale}</td>	
				<td>${table1.osteoarticularTB04Female}</td>	
				<td>${table1.osteoarticularTB0514Female}</td>
				<td>${table1.osteoarticularTB1517Female}</td>
				<td>${table1.osteoarticularTB1819Female}</td>
				<td>${table1.osteoarticularTB2024Female}</td>
				<td>${table1.osteoarticularTB2534Female}</td>
				<td>${table1.osteoarticularTB3544Female}</td>
				<td>${table1.osteoarticularTB4554Female}</td>
				<td>${table1.osteoarticularTB5564Female}</td>
				<td>${table1.osteoarticularTB65Female}</td>
				<td>${table1.osteoarticularTBRuralFemale}</td>
			</tr>
			<tr align="center">
				<td rowspan="2" align="left"><spring:message code="mdrtb.form8.urogenital"/></td>	
				<td>M</td>
				<td rowspan="2">A18.1</td>	
				<td>17</td>
				<td>${table1.urogenitalTBTotalMale}</td>	
				<td>${table1.urogenitalTB04Male}</td>	
				<td>${table1.urogenitalTB0514Male}</td>
				<td>${table1.urogenitalTB1517Male}</td>
				<td>${table1.urogenitalTB1819Male}</td>
				<td>${table1.urogenitalTB2024Male}</td>
				<td>${table1.urogenitalTB2534Male}</td>
				<td>${table1.urogenitalTB3544Male}</td>
				<td>${table1.urogenitalTB4554Male}</td>
				<td>${table1.urogenitalTB5564Male}</td>
				<td>${table1.urogenitalTB65Male}</td>
				<td>${table1.urogenitalTBRuralMale}</td>
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>18</td>
				<td>${table1.urogenitalTBTotalFemale}</td>	
				<td>${table1.urogenitalTB04Female}</td>	
				<td>${table1.urogenitalTB0514Female}</td>
				<td>${table1.urogenitalTB1517Female}</td>
				<td>${table1.urogenitalTB1819Female}</td>
				<td>${table1.urogenitalTB2024Female}</td>
				<td>${table1.urogenitalTB2534Female}</td>
				<td>${table1.urogenitalTB3544Female}</td>
				<td>${table1.urogenitalTB4554Female}</td>
				<td>${table1.urogenitalTB5564Female}</td>
				<td>${table1.urogenitalTB65Female}</td>
				<td>${table1.urogenitalTBRuralFemale}</td>
			</tr>
			<tr align="center">
				<td rowspan="2" align="left"><spring:message code="mdrtb.form8.lymphNodes"/></td>	
				<td>M</td>
				<td rowspan="2">A18.2</td>	
				<td>19</td>
				<td>${table1.lymphNodesTBTotalMale}</td>	
				<td>${table1.lymphNodesTB04Male}</td>	
				<td>${table1.lymphNodesTB0514Male}</td>
				<td>${table1.lymphNodesTB1517Male}</td>
				<td>${table1.lymphNodesTB1819Male}</td>
				<td>${table1.lymphNodesTB2024Male}</td>
				<td>${table1.lymphNodesTB2534Male}</td>
				<td>${table1.lymphNodesTB3544Male}</td>
				<td>${table1.lymphNodesTB4554Male}</td>
				<td>${table1.lymphNodesTB5564Male}</td>
				<td>${table1.lymphNodesTB65Male}</td>
				<td>${table1.lymphNodesTBRuralMale}</td>
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>20</td>
				<td>${table1.lymphNodesTBTotalFemale}</td>	
				<td>${table1.lymphNodesTB04Female}</td>	
				<td>${table1.lymphNodesTB0514Female}</td>
				<td>${table1.lymphNodesTB1517Female}</td>
				<td>${table1.lymphNodesTB1819Female}</td>
				<td>${table1.lymphNodesTB2024Female}</td>
				<td>${table1.lymphNodesTB2534Female}</td>
				<td>${table1.lymphNodesTB3544Female}</td>
				<td>${table1.lymphNodesTB4554Female}</td>
				<td>${table1.lymphNodesTB5564Female}</td>
				<td>${table1.lymphNodesTB65Female}</td>
				<td>${table1.lymphNodesTBRuralFemale}</td>
			</tr>
			<tr align="center">
				<td rowspan="2" align="left"><spring:message code="mdrtb.form8.abdominal"/>
</td>	
				<td>M</td>
				<td rowspan="2">A18.3</td>	
				<td>21</td>
				<td>${table1.abdominalTBTotalMale}</td>	
				<td>${table1.abdominalTB04Male}</td>	
				<td>${table1.abdominalTB0514Male}</td>
				<td>${table1.abdominalTB1517Male}</td>
				<td>${table1.abdominalTB1819Male}</td>
				<td>${table1.abdominalTB2024Male}</td>
				<td>${table1.abdominalTB2534Male}</td>
				<td>${table1.abdominalTB3544Male}</td>
				<td>${table1.abdominalTB4554Male}</td>
				<td>${table1.abdominalTB5564Male}</td>
				<td>${table1.abdominalTB65Male}</td>
				<td>${table1.abdominalTBRuralMale}</td>
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>22</td>
				<td>${table1.abdominalTBTotalFemale}</td>	
				<td>${table1.abdominalTB04Female}</td>	
				<td>${table1.abdominalTB0514Female}</td>
				<td>${table1.abdominalTB1517Female}</td>
				<td>${table1.abdominalTB1819Female}</td>
				<td>${table1.abdominalTB2024Female}</td>
				<td>${table1.abdominalTB2534Female}</td>
				<td>${table1.abdominalTB3544Female}</td>
				<td>${table1.abdominalTB4554Female}</td>
				<td>${table1.abdominalTB5564Female}</td>
				<td>${table1.abdominalTB65Female}</td>
				<td>${table1.abdominalTBRuralFemale}</td>
			</tr>
			<tr align="center">
				<td rowspan="2" align="left"><spring:message code="mdrtb.form8.eye"/></td>	
				<td>M</td>
				<td rowspan="2">A18.5</td>	
				<td>23</td>
				<td>${table1.eyeTBTotalMale}</td>	
				<td>${table1.eyeTB04Male}</td>	
				<td>${table1.eyeTB0514Male}</td>
				<td>${table1.eyeTB1517Male}</td>
				<td>${table1.eyeTB1819Male}</td>
				<td>${table1.eyeTB2024Male}</td>
				<td>${table1.eyeTB2534Male}</td>
				<td>${table1.eyeTB3544Male}</td>
				<td>${table1.eyeTB4554Male}</td>
				<td>${table1.eyeTB5564Male}</td>
				<td>${table1.eyeTB65Male}</td>
				<td>${table1.eyeTBRuralMale}</td>
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>24</td>
				<td>${table1.eyeTBTotalFemale}</td>	
				<td>${table1.eyeTB04Female}</td>	
				<td>${table1.eyeTB0514Female}</td>
				<td>${table1.eyeTB1517Female}</td>
				<td>${table1.eyeTB1819Female}</td>
				<td>${table1.eyeTB2024Female}</td>
				<td>${table1.eyeTB2534Female}</td>
				<td>${table1.eyeTB3544Female}</td>
				<td>${table1.eyeTB4554Female}</td>
				<td>${table1.eyeTB5564Female}</td>
				<td>${table1.eyeTB65Female}</td>
				<td>${table1.eyeTBRuralFemale}</td>
			</tr>
			<tr align="center">
				<td rowspan="2" align="left"><b><spring:message code="mdrtb.form8.miliary"/></b></td>	
				<td>M</td>
				<td rowspan="2">A19</td>	
				<td>25</td>
				<td>${table1.miliaryTBTotalMale}</td>	
				<td>${table1.miliaryTB04Male}</td>	
				<td>${table1.miliaryTB0514Male}</td>
				<td>${table1.miliaryTB1517Male}</td>
				<td>${table1.miliaryTB1819Male}</td>
				<td>${table1.miliaryTB2024Male}</td>
				<td>${table1.miliaryTB2534Male}</td>
				<td>${table1.miliaryTB3544Male}</td>
				<td>${table1.miliaryTB4554Male}</td>
				<td>${table1.miliaryTB5564Male}</td>
				<td>${table1.miliaryTB65Male}</td>
				<td>${table1.miliaryTBRuralMale}</td>
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>26</td>
				<td>${table1.miliaryTBTotalFemale}</td>	
				<td>${table1.miliaryTB04Female}</td>	
				<td>${table1.miliaryTB0514Female}</td>
				<td>${table1.miliaryTB1517Female}</td>
				<td>${table1.miliaryTB1819Female}</td>
				<td>${table1.miliaryTB2024Female}</td>
				<td>${table1.miliaryTB2534Female}</td>
				<td>${table1.miliaryTB3544Female}</td>
				<td>${table1.miliaryTB4554Female}</td>
				<td>${table1.miliaryTB5564Female}</td>
				<td>${table1.miliaryTB65Female}</td>
				<td>${table1.miliaryTBRuralFemale}</td>
			</tr>
			<tr align="center">
				<td rowspan="2" align="left"><spring:message code="mdrtb.form8.includingResistant"/></td>	
				<td>M</td>
				<td rowspan="2">A15-19</td>	
				<td>27</td>
				<td>${table1.resistantTBTotalMale}</td>	
				<td>${table1.resistantTB04Male}</td>	
				<td>${table1.resistantTB0514Male}</td>
				<td>${table1.resistantTB1517Male}</td>
				<td>${table1.resistantTB1819Male}</td>
				<td>${table1.resistantTB2024Male}</td>
				<td>${table1.resistantTB2534Male}</td>
				<td>${table1.resistantTB3544Male}</td>
				<td>${table1.resistantTB4554Male}</td>
				<td>${table1.resistantTB5564Male}</td>
				<td>${table1.resistantTB65Male}</td>
				<td>${table1.resistantTBRuralMale}</td>
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>28</td>
				<td>${table1.resistantTBTotalFemale}</td>	
				<td>${table1.resistantTB04Female}</td>	
				<td>${table1.resistantTB0514Female}</td>
				<td>${table1.resistantTB1517Female}</td>
				<td>${table1.resistantTB1819Female}</td>
				<td>${table1.resistantTB2024Female}</td>
				<td>${table1.resistantTB2534Female}</td>
				<td>${table1.resistantTB3544Female}</td>
				<td>${table1.resistantTB4554Female}</td>
				<td>${table1.resistantTB5564Female}</td>
				<td>${table1.resistantTB65Female}</td>
				<td>${table1.resistantTBRuralFemale}</td>
			</tr>
			<tr align="center">
				<td rowspan="2" align="left"><spring:message code="mdrtb.form8.tbhiv"/></td>	
				<td>M</td>
				<td rowspan="2">A15-19</td>	
				<td>29</td>
				<td>${table1.tbhivTBTotalMale}</td>	
				<td>${table1.tbhivTB04Male}</td>	
				<td>${table1.tbhivTB0514Male}</td>
				<td>${table1.tbhivTB1517Male}</td>
				<td>${table1.tbhivTB1819Male}</td>
				<td>${table1.tbhivTB2024Male}</td>
				<td>${table1.tbhivTB2534Male}</td>
				<td>${table1.tbhivTB3544Male}</td>
				<td>${table1.tbhivTB4554Male}</td>
				<td>${table1.tbhivTB5564Male}</td>
				<td>${table1.tbhivTB65Male}</td>
				<td>${table1.tbhivTBRuralMale}</td>
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>30</td>
				<td>${table1.tbhivTBTotalFemale}</td>	
				<td>${table1.tbhivTB04Female}</td>	
				<td>${table1.tbhivTB0514Female}</td>
				<td>${table1.tbhivTB1517Female}</td>
				<td>${table1.tbhivTB1819Female}</td>
				<td>${table1.tbhivTB2024Female}</td>
				<td>${table1.tbhivTB2534Female}</td>
				<td>${table1.tbhivTB3544Female}</td>
				<td>${table1.tbhivTB4554Female}</td>
				<td>${table1.tbhivTB5564Female}</td>
				<td>${table1.tbhivTB65Female}</td>
				<td>${table1.tbhivTBRuralFemale}</td>
			</tr>
			<tr align="center">
				<td rowspan="2" align="left"><spring:message code="mdrtb.form8.rural"/></td>	
				<td>M</td>
				<td rowspan="2">A15-19</td>	
				<td>31</td>
				<td>${table1.ruralTBTotalMale}</td>	
				<td>${table1.ruralTB04Male}</td>	
				<td>${table1.ruralTB0514Male}</td>
				<td>${table1.ruralTB1517Male}</td>
				<td>${table1.ruralTB1819Male}</td>
				<td>${table1.ruralTB2024Male}</td>
				<td>${table1.ruralTB2534Male}</td>
				<td>${table1.ruralTB3544Male}</td>
				<td>${table1.ruralTB4554Male}</td>
				<td>${table1.ruralTB5564Male}</td>
				<td>${table1.ruralTB65Male}</td>
				<td>X</td>
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.tb03.gender.female"/></td>
				<td>32</td>
				<td>${table1.ruralTBTotalFemale}</td>	
				<td>${table1.ruralTB04Female}</td>	
				<td>${table1.ruralTB0514Female}</td>
				<td>${table1.ruralTB1517Female}</td>
				<td>${table1.ruralTB1819Female}</td>
				<td>${table1.ruralTB2024Female}</td>
				<td>${table1.ruralTB2534Female}</td>
				<td>${table1.ruralTB3544Female}</td>
				<td>${table1.ruralTB4554Female}</td>
				<td>${table1.ruralTB5564Female}</td>
				<td>${table1.ruralTB65Female}</td>
				<td>X</td>
			</tr>
		</table>	
		<br/><br/>
		
		<h5 align="center"><spring:message code="mdrtb.form8.table2.title"/></h5>
		
		<table width="100%" border="1">
		<tr align="center">
			<td rowspan="3"><spring:message code="mdrtb.form8.item"/></td>
			<td rowspan="3"><spring:message code="mdrtb.form8.number"/></td>
			<td rowspan="3"><spring:message code="mdrtb.form8.icd"/></td>
			<td colspan="4"><spring:message code="mdrtb.form8.patientsRegistered"/></td>
			<td colspan="5"><spring:message code="mdrtb.form8.patientsOnRecord"/></td>
			<td colspan="4"><spring:message code="mdrtb.form8.patientsDeregistered"/></td>
			<td colspan="4"><spring:message code="mdrtb.form8.patientsAtEndOfYear"/></td>
		</tr>
		<tr align="center">
			<td rowspan="2"><spring:message code="mdrtb.form8.total"/>:<br/>
			<td colspan="3"><spring:message code="mdrtb.form8.including"/>:<br/>
			<td rowspan="2"><spring:message code="mdrtb.form8.total"/></td>
			<td rowspan="2"><spring:message code="mdrtb.form8.includingPHCDiagnosed"/>:<br/>
			<td colspan="3"><spring:message code="mdrtb.form8.including"/>:<br/>
			<td rowspan="2"><spring:message code="mdrtb.form8.total"/>:<br/>
			<td colspan="3"><spring:message code="mdrtb.form8.including"/>:<br/>
			<td rowspan="2"><spring:message code="mdrtb.form8.total"/>:<br/>
			<td colspan="3"><spring:message code="mdrtb.form8.including"/>:<br/>
		</tr>
		<tr align="center">
			<td>0-14 <spring:message code="mdrtb.form8.years"/></td>
			<td>15-17 <spring:message code="mdrtb.form8.years"/></td>
			<td>18-19 <spring:message code="mdrtb.form8.years"/></td>
			<td>0-14 <spring:message code="mdrtb.form8.years"/></td>
			<td>15-17 <spring:message code="mdrtb.form8.years"/></td>
			<td>18-19 <spring:message code="mdrtb.form8.years"/></td>
			<td>0-14 <spring:message code="mdrtb.form8.years"/></td>
			<td>15-17 <spring:message code="mdrtb.form8.years"/></td>
			<td>18-19 <spring:message code="mdrtb.form8.years"/></td>
			<td>0-14 <spring:message code="mdrtb.form8.years"/></td>
			<td>15-17 <spring:message code="mdrtb.form8.years"/></td>
			<td>18-19 <spring:message code="mdrtb.form8.years"/></td>
		</tr>
		<tr align="center">
			<td>A</td>
			<td>&nbsp;</td>
			<td>B</td>
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
			<td>11</td>
			<td>12</td>
			<td>13</td>
			<td>14</td>
			<td>15</td>
			<td>16</td>
			<td>17</td>
		</tr>
		<tr align="center">
			<td align="left"><b><spring:message code="mdrtb.form8.activeTotal"/></b></td>
			<td>01</td>
			<td>A15-19</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>${table1.activeTBTotalMale + table1.activeTBTotalFemale }</td>
			<td>${table2.activePHCTotal }</td>
			<td>${table1.activeTB04Male + table1.activeTB04Female + table1.activeTB0514Male + table1.activeTB0514Female}</td>
			<td>${table1.activeTB1517Male + table1.activeTB1517Female}</td>
			<td>${table1.activeTB1819Male + table1.activeTB1819Female}</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="left"><spring:message code="mdrtb.form8.includingRespiratory"/></td>
			<td>02</td>
			<td>A15-16</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>${table1.respiratoryTBTotalMale + table1.respiratoryTBTotalFemale }</td>
			<td>${table2.respiratoryPHCTotal }</td>
			<td>${table1.respiratoryTB04Male + table1.respiratoryTB04Female + table1.respiratoryTB0514Male + table1.respiratoryTB0514Female}</td>
			<td>${table1.respiratoryTB1517Male + table1.respiratoryTB1517Female}</td>
			<td>${table1.respiratoryTB1819Male + table1.respiratoryTB1819Female}</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td><spring:message code="mdrtb.form8.pulmonary"/></td>
			<td>03</td>
			<td>A15.0<br/>A15.1-15.3<br/>A16.0-16.2</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>${table1.pulmonaryTBTotalMale + table1.pulmonaryTBTotalFemale }</td>
			<td>${table2.pulmonaryPHCTotal }</td>
			<td>${table1.pulmonaryTB04Male + table1.pulmonaryTB04Female + table1.pulmonaryTB0514Male + table1.pulmonaryTB0514Female}</td>
			<td>${table1.pulmonaryTB1517Male + table1.pulmonaryTB1517Female}</td>
			<td>${table1.pulmonaryTB1819Male + table1.pulmonaryTB1819Female}</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td><spring:message code="mdrtb.form8.withBacterioExcretion"/></td>
			<td>04</td>
			<td>A15.0-15.1<br/>A15.5-15.9</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>${table1.bacExTBTotalMale + table1.bacExTBTotalFemale }</td>
			<td>${table2.bacExPHCTotal }</td>
			<td>${table1.bacExTB04Male + table1.bacExTB04Female + table1.bacExTB0514Male + table1.bacExTB0514Female}</td>
			<td>${table1.bacExTB1517Male + table1.bacExTB1517Female}</td>
			<td>${table1.bacExTB1819Male + table1.bacExTB1819Female}</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td><spring:message code="mdrtb.form8.inDecayPhase"/></td>
			<td>05</td>
			<td>A15.0<br/>A15.1-15.3<br/>A16.0-16.2</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>${table2.decayPhaseTotal }</td>
			<td>${table2.decayPhasePHCTotal }</td>
			<td>${table2.decayPhase014 }</td>
			<td>${table2.decayPhase1517 }</td>
			<td>${table2.decayPhase1819 }</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align=left><b><spring:message code="mdrtb.form8.nervousSystem"/></b></td>
			<td>06</td>
			<td>A17</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>${table1.nervousSystemTBTotalMale + table1.nervousSystemTBTotalFemale }</td>
			<td>${table2.nervousSystemPHCTotal }</td>
			<td>${table1.nervousSystemTB04Male + table1.nervousSystemTB04Female + table1.nervousSystemTB0514Male + table1.nervousSystemTB0514Female}</td>
			<td>${table1.nervousSystemTB1517Male + table1.nervousSystemTB1517Female}</td>
			<td>${table1.nervousSystemTB1819Male + table1.nervousSystemTB1819Female}</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align=left><b><spring:message code="mdrtb.form8.otherOrgans"/></b></td>
			<td>07</td>
			<td>A18</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>${table1.otherOrgansTBTotalMale + table1.otherOrgansTBTotalFemale }</td>
			<td>${table2.otherOrgansPHCTotal }</td>
			<td>${table1.otherOrgansTB04Male + table1.otherOrgansTB04Female + table1.otherOrgansTB0514Male + table1.otherOrgansTB0514Female}</td>
			<td>${table1.otherOrgansTB1517Male + table1.otherOrgansTB1517Female}</td>
			<td>${table1.otherOrgansTB1819Male + table1.otherOrgansTB1819Female}</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align=left><b><spring:message code="mdrtb.form8.miliary"/></b></td>
			<td>09</td>
			<td>A19</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>${table1.miliaryTBTotalMale + table1.miliaryTBTotalFemale }</td>
			<td>${table2.miliaryPHCTotal }</td>
			<td>${table1.miliaryTB04Male + table1.miliaryTB04Female + table1.miliaryTB0514Male + table1.miliaryTB0514Female}</td>
			<td>${table1.miliaryTB1517Male + table1.miliaryTB1517Female}</td>
			<td>${table1.miliaryTB1819Male + table1.miliaryTB1819Female}</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		</table>
		<br/><br/>
		<spring:message code="mdrtb.form8.t2f1" arguments="${table2.detectedByRoutineCheckups},${table2.routine014},${table2.routine1517},${table2.routine1819 },${table2.detectedBySpecialistsTotal },${table2.detectedByTBDoctors },${table2.detectedByOtherSpecialists }  "/><br/>
		<spring:message code="mdrtb.form8.t2f2"/><br/>
		<spring:message code="mdrtb.form8.t2f3" arguments="${table2.focalTotal },${table2.infiltrativeTotal },${table2.disseminatedTotal },${table2.cavernousTotal },${table2.fibrousTotal },${table2.cirrhoticTotal },${table2.tbComplexTotal },${table2.miliaryTotal },${table2.tuberculomaTotal },${table2.bronchiTotal },${table2.pleurisyTotal },${table2.hilarLymphNodesTotal },${table2.osteoarticularTotal },${table2.urogenitalTotal },${table2.peripheralLymphNodesTotal },${table2.abdominalTotal },${table2.skinTotal },${table2.eyeTotal },${table2.nervousSystemTotal },${table2.liverTotal }"/><br/>
		<spring:message code="mdrtb.form8.t2f4" arguments="${table2.childbearing },${table2.pregnant },${table2.contact},${table2.migrants },${table2.phcWorkers },${table2.tbServiceWorkers }"/><br/>
		<spring:message code="mdrtb.form8.t2f5" arguments="${table2.relapseCount },${table2.failCount },${table2.ltfuCount },${table2.otherCount }"/><br/>
		<br/><br/>
		<h5 align="center"><spring:message code="mdrtb.form8.table3.title"/> </h5>
		
		<table width="100%" border="1">
		<tr align="center">
			<td width="75%"><spring:message code="mdrtb.form8.item"/></td>
			<td><spring:message code="mdrtb.form8.number"/></td>
			<td><spring:message code="mdrtb.form8.total"/></td>
		</tr>
		<tr align="center">
			<td>A</td>
			<td>B</td>
			<td>1</td>
		</tr>
		<tr align="center">
			<td align="left"><spring:message code="mdrtb.form8.group1To2"/></td>
			<td>01</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="left"><spring:message code="mdrtb.form8.group2To1"/></td>
			<td>02</td>
			<td>${table3.group2To1 }</td>
		</tr>
		<tr align="center">
			<td align="left"><spring:message code="mdrtb.form8.relapses"/></td>
			<td>03</td>
			<td>${table3.relapse }</td>
		</tr>
		<tr align="center">
			<td align="left"><spring:message code="mdrtb.form8.translateFrom"/></td>
			<td>04</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="left"><spring:message code="mdrtb.form8.translateTo"/></td>
			<td>05</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="left"><spring:message code="mdrtb.form8.nonDiagnosis"/></td>
			<td>06</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="left"><spring:message code="mdrtb.form8.diedOfTB"/></td>
			<td>07</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="left"><spring:message code="mdrtb.form8.includingActive"/></td>
			<td>08</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="mdrtb.form8.fromNew"/></td>
			<td>09</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="left"><spring:message code="mdrtb.form8.diedInHospital"/> </td>
			<td>10</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="left"><spring:message code="mdrtb.form8.children017"/></td>
			<td>11</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="left"> - <spring:message code="mdrtb.womenOfChildbearingAge"/> </td>
			<td>12</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="left"><spring:message code="mdrtb.form8.notRegistered"/></td>
			<td>13</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="left"><spring:message code="mdrtb.form8.tbhivCoinfection"/></td>
			<td>14</td>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
			<td align="left"><spring:message code="mdrtb.form8.fromOtherDiseases"/></td>
			<td>15</td>
			<td>&nbsp;</td>
		</tr>
		
		</table>
		<br/><br/>
		
		<h5 align="center"><spring:message code="mdrtb.form8.table4.title"/></h5>
		
		<table width="100%" border="1">
			<tr align="center">
			 <td width="60%"><spring:message code="mdrtb.form8.item"/></td>
			 <td><spring:message code="mdrtb.form8.number"/></td>
			 <td><spring:message code="mdrtb.form8.total"/></td>
			 <td>0-14 <spring:message code="mdrtb.form8.years"/></td>
			 <td>15-17 <spring:message code="mdrtb.form8.years"/></td>
			 <td>18-19 <spring:message code="mdrtb.form8.years"/></td>
			</tr>
			<tr align="center">
			 <td width="50%">A</td>
			 <td>B</td>
			 <td>1</td>
			 <td>2</td>
			 <td>3</td>
			 <td>4</td>
			</tr>
			<tr align="center">
			 <td width="50%" align="left"><spring:message code="mdrtb.form8.activeHospitalized"/></td>
			 <td>01</td>
			 <td>${table4.hospitalised }</td>
			 <td>${table4.hospitalised014 }</td>
			 <td>${table4.hospitalised1517 }</td>
			 <td>${table4.hospitalised1819 }</td>
			</tr>
			<tr align="center">
			 <td width="50%" align="left"><spring:message code="mdrtb.form8.including"/>&nbsp;&nbsp;&nbsp;&nbsp;- <spring:message code="mdrtb.form8.inHospital"/> </td>
			 <td>02</td>
			<td>${table4.inHospital }</td>
			 <td>${table4.inHospital014 }</td>
			 <td>${table4.inHospital1517 }</td>
			 <td>${table4.inHospital1819 }</td>
			</tr>
			<tr align="center">
			 <td width="50%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <spring:message code="mdrtb.form8.inSanatorium"/> </td>
			 <td>03</td>
			 <td>&nbsp;</td>
			 <td>&nbsp;</td>
			 <td>&nbsp;</td>
			 <td>&nbsp;</td>
			</tr>
			<tr align="center">
			 <td width="50%" align="left"><spring:message code="mdrtb.form8.firstIdentifiedTotal"/></td>
			 <td>04</td>
			<td>${table4.firstNew }</td>
			 <td>${table4.firstNew014 }</td>
			 <td>${table4.firstNew1517 }</td>
			 <td>${table4.firstNew1819 }</td>
			</tr>
			<tr align="center">
			 <td width="50%" align="left"><spring:message code="mdrtb.form8.including"/>&nbsp;&nbsp;&nbsp;&nbsp;- <spring:message code="mdrtb.form8.mtbPositive"/> </td>
			 <td>05</td>
			 <td>${table4.newBac }</td>
			 <td>${table4.newBac014 }</td>
			 <td>${table4.newBac1517 }</td>
			 <td>${table4.newBac1819 }</td>
			</tr>
			<tr align="center">
			 <td width="50%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <spring:message code="mdrtb.form8.others"/> </td>
			 <td>06</td>
			 <td>${table4.newOther }</td>
			 <td>${table4.newOther014 }</td>
			 <td>${table4.newOther1517 }</td>
			 <td>${table4.newOther1819 }</td>
			</tr>
		</table>
		<br/><br/>
		<spring:message code="mdrtb.form8.t4f1"/>
		<br/><br/>
		<h5 align="center"><spring:message code="mdrtb.form8.table5.title"/><br/><spring:message code="mdrtb.form8.table5a.title"/></h5>
		
		<table width="100%" border="1">
			<tr align="center">
				<td rowspan="2"><spring:message code="mdrtb.form8.item"/></td>
				<td rowspan="2"><spring:message code="mdrtb.form8.number"/></td>
				<td rowspan="2"><spring:message code="mdrtb.form8.atBeginning"/></td>
				<td colspan="2"><spring:message code="mdrtb.form8.newlyIdentified"/></td>
				<td rowspan="2"><spring:message code="mdrtb.form8.transferMtb"/></td>
				<td rowspan="2"><spring:message code="mdrtb.form8.ceasedMtb"/></td>
				<td rowspan="2"><spring:message code="mdrtb.form8.outOfArea"/></td>
				<td rowspan="2"><spring:message code="mdrtb.form8.diedTb"/></td>
				<td rowspan="2"><spring:message code="mdrtb.form8.atEnd"/></td>
				
			</tr>
			<tr align="center">
				<td><spring:message code="mdrtb.form8.newActive"/></td>
				<td><spring:message code="mdrtb.form8.repeated"/></td>
				
			 
			</tr>
			<tr align="center">
				
				<td>A</td>
				<td><br/>
			 	<td>1</td>
			 	<td>2</td>
			 	<td>3</td>
			 	<td>4</td>
			 	<td>5</td>
			 	<td>6</td>
			 	<td>7</td>
			 	<td>8</td>
			 
			</tr>
			<tr align="center">
				
				<td><spring:message code="mdrtb.form8.totalBacPositiveRespiratory"/></td>
				<td>01<br/>
			 	<td>&nbsp;</td>
			 	<td>${table5a.respBacNew }</td>
			 	<td>${table5a.respBacOther }</td>
			 	<td>&nbsp;</td>
			 	<td>&nbsp;</td>
			 	<td>&nbsp;</td>
			 	<td>&nbsp;</td>
			 	<td>&nbsp;</td>
			 
			</tr>
			<tr align="center">
				
				<td>-<spring:message code="mdrtb.form8.includingVillagers"/></td>
				<td>02<br/>
			 	<td>&nbsp;</td>
			 	<td>${table5a.respBacNewVillager }</td>
			 	<td>${table5a.respBacOtherVillager }</td>
			 	<td>&nbsp;</td>
			 	<td>&nbsp;</td>
			 	<td>&nbsp;</td>
			 	<td>&nbsp;</td>
			 	<td>&nbsp;</td>
			 
			</tr>
		</table>
		<br/><br/>
		<h5 align="center"><spring:message code="mdrtb.form8.table5b.title"/></h5>
		
		<table width="100%", border="1">
		<tr align="center">
		<td rowspan="4"><spring:message code="mdrtb.form8.item"/></td>
		<td rowspan="4"><spring:message code="mdrtb.form8.number"/></td>
		<td rowspan="4"><spring:message code="mdrtb.form8.atBeginning"/></td>
		<td colspan="6"><spring:message code="mdrtb.form8.registered"/></td>
		<td rowspan="4"><spring:message code="mdrtb.form8.removed"/></td>
		<td rowspan="4"><spring:message code="mdrtb.form8.atEnd"/></td>
		
		
		
		</tr>
		<tr align="center">
			<td rowspan="3">
				<spring:message code="mdrtb.form8.total"/>
			</td>
			<td rowspan="3">
				<spring:message code="mdrtb.form8.surveyedThisYear"/>
			</td>
			<td rowspan="3">
				<spring:message code="mdrtb.form8.includingPHC"/>
			</td>
			<td colspan="3">
				<spring:message code="mdrtb.form8.gotChemo"/>
			</td>
		</tr>
		<tr align="center">
			<td colspan="2"><spring:message code="mdrtb.form8.tb"/></td>
			<td><spring:message code="mdrtb.form8.hiv"/></td>
		
		</tr>
		<tr align=center>
			<td><spring:message code="mdrtb.form8.total"/></td>
			<td><spring:message code="mdrtb.form8.includingInPHC"/></td>
		</tr>
		<tr align="center">
				
				<td>A</td>
				<td><br/>
			 	<td>1</td>
			 	<td>2</td>
			 	<td>3</td>
			 	<td>4</td>
			 	<td>5</td>
			 	<td>6</td>
			 	<td>7</td>
			 	<td>8</td>
			 	<td>9</td>
			</tr>
			<tr align="center">
				<td align="left"><spring:message code="mdrtb.form8.numberLiving"/></td>
				<td>01</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr align="center">
				<td align="left">&nbsp;&nbsp;&nbsp;<spring:message code="mdrtb.form8.including"/> - 0-14 <spring:message code="mdrtb.form8.years"/><br/></td>
				<td>02</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr align="center">
				<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 15-17 <spring:message code="mdrtb.form8.years"/></td>
				<td>03</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr align="center">
				<td align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 18-19 <spring:message code="mdrtb.form8.years"/></td>
				<td>04</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<br/><br/>
		<center><h5><spring:message code="mdrtb.form8.table6.title"/></h5></center>
		<table border="1" cellpadding="1" cellspacing="1" style="width: 100%;">
			<tbody>
				<tr>
					<td rowspan="2">
						&nbsp;</td>
					<td rowspan="2">
						&nbsp;</td>
					<td rowspan="2" style="text-align: center;">
						<spring:message code="dotsreports.tb08.totatTBCasesDetected"/></td>
					<td rowspan="2">
						<div style="text-align: center;">
							<spring:message code="dotsreports.tb08.eligibleCohort"/> &nbsp;</div>
						
					</td>
					<td rowspan="2" style="text-align: center;">
						<spring:message code="dotsreports.tb08.cured"/></td>
					<td rowspan="2">
						<div style="text-align: center;">
							<spring:message code="dotsreports.tb08.txCompleted"/></div>
					</td>
					<td colspan="2" rowspan="1">
						<div style="text-align: center;">
							<spring:message code="dotsreports.tb08.died"/> &nbsp;</div>
						
					</td>
					<td rowspan="2" style="text-align: center;">
						<spring:message code="dotsreports.tb08.txFailure"/></td>
					<td rowspan="2" style="text-align: center;">
						<spring:message code="dotsreports.tb08.ltfu"/></td>
					
					<td rowspan="2">
						<div style="text-align: center;">
							<spring:message code="dotsreports.tb08.totalEvaluated"/>&nbsp;</div>
						
					</td>
					<td rowspan="2">
						<div style="text-align: center;">
							<spring:message code="dotsreports.tb08.startedSLD"/>&nbsp;</div>
					</td>
					
					<td rowspan="2">
						<div style="text-align: center;">
							<spring:message code="dotsreports.tb08.canceled"/> &nbsp;</div>
					</td>
					
					<td rowspan="2">
						<div style="text-align: center;">
							<spring:message code="dotsreports.tb08.notEvaluated"/>&nbsp;</div>
						
					</td>
					
				</tr>
				<tr align="center">
					<td>
						<spring:message code="dotsreports.tb08.tb"/></td>
					<td>
						<spring:message code="dotsreports.tb08.nontb"/></td>
					
				</tr>
				<tr align="center">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>
						1</td>
					<td>
						2</td>
					<td>
						3</td>
					<td>
						4</td>
					<td>
						5</td>
					<td>
						6</td>
					<td>
						7</td>
					<td>
						8</td>
					<td>
						9</td>
					<td>
						10</td>
					<td>
						11</td>
					<td>
						12</td>
					
				</tr>
				<tr>
					<td style="font: bold;">
						1</td>
					<td style="font: bold;">
						<spring:message code="dotsreports.tb08.newCases"/></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				
					
					
				</tr>
				<tr>
					<td>
						1.1</td>
					<td>
						<spring:message code="dotsreports.tb08.pulmonaryBC"/>&nbsp;</td>
					<td>
						${table6.newPulmonaryBCDetected }</td>
					<td>
						${table6.newPulmonaryBCEligible }</td>
					<td>
						${table6.newPulmonaryBCCured }</td>
					<td>
						${table6.newPulmonaryBCCompleted }</td>
					<td>
						${table6.newPulmonaryBCDiedTB }</td>
					<td>
						${table6.newPulmonaryBCDiedNotTB}</td>
					<td>
						${table6.newPulmonaryBCFailed }</td>
					<td>
						${table6.newPulmonaryBCDefaulted }</td>
					<td>
						${table6.newPulmonaryBCEligible }</td>
					
					<td>
						${table6.newPulmonaryBCSLD }</td>
					<td>
						${table6.newPulmonaryBCCanceled }</td>
					<td>
						${table6.newPulmonaryBCDetected - (table6.newPulmonaryBCEligible + table6.newPulmonaryBCSLD + table6.newPulmonaryBCCanceled) }</td>
				</tr>
				<tr>
					<td>
						1.1.1</td>
					<td>
						0-4&nbsp;</td>
					<td>
						${table6.newPulmonaryBCDetected04 }</td>
					<td>
						${table6.newPulmonaryBCEligible04 }</td>
					<td>
						${table6.newPulmonaryBCCured04 }</td>
					<td>
						${table6.newPulmonaryBCCompleted04 }</td>
					<td>
						${table6.newPulmonaryBCDiedTB04 }</td>
					<td>
						${table6.newPulmonaryBCDiedNotTB04}</td>
					<td>
						${table6.newPulmonaryBCFailed04 }</td>
					<td>
						${table6.newPulmonaryBCDefaulted04 }</td>
					<td>
						${table6.newPulmonaryBCEligible04 }</td>
					<td>
						${table6.newPulmonaryBCSLD04 }</td>
					<td>
						${table6.newPulmonaryBCCanceled04 }</td>
					
					<td>
						${table6.newPulmonaryBCDetected04 - (table6.newPulmonaryBCEligible04 + table6.newPulmonaryBCSLD04 + table6.newPulmonaryBCCanceled04) }</td>
				</tr>
				<tr>
					<td>
						1.1.2</td>
					<td>
						5-14&nbsp;</td>
					<td>
						${table6.newPulmonaryBCDetected0514 }</td>
					<td>
						${table6.newPulmonaryBCEligible0514 }</td>
					<td>
						${table6.newPulmonaryBCCured0514 }</td>
					<td>
						${table6.newPulmonaryBCCompleted0514 }</td>
					<td>
						${table6.newPulmonaryBCDiedTB0514 }</td>
					<td>
						${table6.newPulmonaryBCDiedNotTB0514}</td>
					<td>
						${table6.newPulmonaryBCFailed0514 }</td>
					<td>
						${table6.newPulmonaryBCDefaulted0514 }</td>
					<td>
						${table6.newPulmonaryBCEligible0514 }</td>
					
					<td>
						${table6.newPulmonaryBCSLD0514 }</td>
					<td>
						${table6.newPulmonaryBCCanceled0514 }</td>
					<td>
						${table6.newPulmonaryBCDetected0514 - (table6.newPulmonaryBCEligible0514 + table6.newPulmonaryBCSLD0514 + table6.newPulmonaryBCCanceled0514) }</td>
					
				</tr>
				<tr>
					<td>
						1.1.3</td>
					<td>
						15-17&nbsp;</td>
					<td>
						${table6.newPulmonaryBCDetected1517 }</td>
					<td>
						${table6.newPulmonaryBCEligible1517 }</td>
					<td>
						${table6.newPulmonaryBCCured1517 }</td>
					<td>
						${table6.newPulmonaryBCCompleted1517 }</td>
					<td>
						${table6.newPulmonaryBCDiedTB1517 }</td>
					<td>
						${table6.newPulmonaryBCDiedNotTB1517}</td>
					<td>
						${table6.newPulmonaryBCFailed1517 }</td>
					<td>
						${table6.newPulmonaryBCDefaulted1517 }</td>
					<td>
						${table6.newPulmonaryBCEligible1517 }</td>
					<td>
						${table6.newPulmonaryBCSLD1517 }</td>
					<td>
						${table6.newPulmonaryBCCanceled1517 }</td>
					
					<td>
						${table6.newPulmonaryBCDetected1517 - (table6.newPulmonaryBCEligible1517 + table6.newPulmonaryBCSLD1517 + table6.newPulmonaryBCCanceled1517) }</td>
					
				</tr>
				<tr>
					<td>
						1.2</td>
					<td>
						<spring:message code="dotsreports.tb08.pulmonaryCD"/> </td>
					<td>
						${table6.newPulmonaryCDDetected }</td>
					<td>
						${table6.newPulmonaryCDEligible }</td>
					<td>
						${table6.newPulmonaryCDCured }</td>
					<td>
						${table6.newPulmonaryCDCompleted }</td>
					<td>
						${table6.newPulmonaryCDDiedTB }</td>
					<td>
						${table6.newPulmonaryCDDiedNotTB}</td>
					<td>
						${table6.newPulmonaryCDFailed }</td>
					<td>
						${table6.newPulmonaryCDDefaulted }</td>
					<td>
						${table6.newPulmonaryCDEligible }</td>
					
					<td>
						${table6.newPulmonaryCDSLD }</td>
					<td>
						${table6.newPulmonaryCDCanceled }</td>
					<td>
						${table6.newPulmonaryCDDetected - (table6.newPulmonaryCDEligible + table6.newPulmonaryCDSLD + table6.newPulmonaryCDCanceled ) }</td>
					
				</tr>
				<tr>
					<td>
						1.2.1</td>
					<td>
						0-4</td>
					<td>
						${table6.newPulmonaryCDDetected04 }</td>
					<td>
						${table6.newPulmonaryCDEligible04 }</td>
					<td>
						${table6.newPulmonaryCDCured04 }</td>
					<td>
						${table6.newPulmonaryCDCompleted04 }</td>
					<td>
						${table6.newPulmonaryCDDiedTB04 }</td>
					<td>
						${table6.newPulmonaryCDDiedNotTB04}</td>
					<td>
						${table6.newPulmonaryCDFailed04 }</td>
					<td>
						${table6.newPulmonaryCDDefaulted04 }</td>
					<td>
						${table6.newPulmonaryCDEligible04 }</td>
					<td>
						${table6.newPulmonaryCDSLD04 }</td>
					<td>
						${table6.newPulmonaryCDCanceled04 }</td>
					<td>
						${table6.newPulmonaryCDDetected04 - (table6.newPulmonaryCDEligible04 + table6.newPulmonaryCDSLD04 + table6.newPulmonaryCDCanceled04 ) }</td>
				</tr>
				<tr>
					<td>
						1.2.2</td>
					<td>
						5-14</td>
					<td>
						${table6.newPulmonaryCDDetected0514 }</td>
					<td>
						${table6.newPulmonaryCDEligible0514 }</td>
					<td>
						${table6.newPulmonaryCDCured0514 }</td>
					<td>
						${table6.newPulmonaryCDCompleted0514 }</td>
					<td>
						${table6.newPulmonaryCDDiedTB0514 }</td>
					<td>
						${table6.newPulmonaryCDDiedNotTB0514}</td>
					<td>
						${table6.newPulmonaryCDFailed0514 }</td>
					<td>
						${table6.newPulmonaryCDDefaulted0514 }</td>
					<td>
						${table6.newPulmonaryCDEligible0514 }</td>
					<td>
						${table6.newPulmonaryCDSLD0514 }</td>
					<td>
						${table6.newPulmonaryCDCanceled0514 }</td>
					<td>
						${table6.newPulmonaryCDDetected0514 - (table6.newPulmonaryCDEligible0514 + table6.newPulmonaryCDSLD0514 + table6.newPulmonaryCDCanceled0514 ) }</td>
					
				</tr>
				<tr>
					<td>
						1.2.3</td>
					<td>
						15-17</td>
					<td>
						${table6.newPulmonaryCDDetected1517 }</td>
					<td>
						${table6.newPulmonaryCDEligible1517 }</td>
					<td>
						${table6.newPulmonaryCDCured1517 }</td>
					<td>
						${table6.newPulmonaryCDCompleted1517 }</td>
					<td>
						${table6.newPulmonaryCDDiedTB1517 }</td>
					<td>
						${table6.newPulmonaryCDDiedNotTB1517}</td>
					<td>
						${table6.newPulmonaryCDFailed1517 }</td>
					<td>
						${table6.newPulmonaryCDDefaulted1517 }</td>
					<td>
						${table6.newPulmonaryCDEligible1517 }</td>
					<td>
						${table6.newPulmonaryCDSLD1517 }</td>
					<td>
						${table6.newPulmonaryCDCanceled1517 }</td>
					<td>
						${table6.newPulmonaryCDDetected0514 - (table6.newPulmonaryCDEligible0514 + table6.newPulmonaryCDSLD0514 + table6.newPulmonaryCDCanceled0514 ) }</td>
					
						
				</tr>
				<tr>
					<td>
						&nbsp;1.3</td>
					<td>
						<spring:message code="dotsreports.tb08.eptb"/></td>
					<td>
						${table6.newExtrapulmonaryDetected }</td>
					<td>
						${table6.newExtrapulmonaryEligible }</td>
					<td>
						${table6.newExtrapulmonaryCured }</td>
					<td>
						${table6.newExtrapulmonaryCompleted }</td>
					<td>
						${table6.newExtrapulmonaryDiedTB }</td>
					<td>
						${table6.newExtrapulmonaryDiedNotTB}</td>
					<td>
						${table6.newExtrapulmonaryFailed }</td>
					<td>
						${table6.newExtrapulmonaryDefaulted }</td>
					<td>
						${table6.newExtrapulmonaryEligible }</td>
					<td>
						${table6.newExtrapulmonarySLD }</td>
					<td>
						${table6.newExtrapulmonaryCanceled }</td>
					<td>
						${table6.newExtrapulmonaryDetected - (table6.newExtrapulmonaryEligible + table6.newExtrapulmonarySLD + table6.newExtrapulmonaryCanceled ) }</td>
				</tr>
				<tr>
					<td>
						1.3.1</td>
					<td>
						0-4</td>
					<td>
						${table6.newExtrapulmonaryDetected04 }</td>
					<td>
						${table6.newExtrapulmonaryEligible04 }</td>
					<td>
						${table6.newExtrapulmonaryCured04 }</td>
					<td>
						${table6.newExtrapulmonaryCompleted04 }</td>
					<td>
						${table6.newExtrapulmonaryDiedTB04 }</td>
					<td>
						${table6.newExtrapulmonaryDiedNotTB04}</td>
					<td>
						${table6.newExtrapulmonaryFailed04 }</td>
					<td>
						${table6.newExtrapulmonaryDefaulted04 }</td>
					<td>
						${table6.newExtrapulmonaryEligible04 }</td>
					<td>
						${table6.newExtrapulmonarySLD04 }</td>
					<td>
						${table6.newExtrapulmonaryCanceled04 }</td>
					<td>
						${table6.newExtrapulmonaryDetected04 - (table6.newExtrapulmonaryEligible04 + table6.newExtrapulmonarySLD04 + table6.newExtrapulmonaryCanceled04 ) }</td>
				</tr>
				<tr>
					<td>
						1.3.2</td>
					<td>
						5-14</td>
					<td>
						${table6.newExtrapulmonaryDetected0514 }</td>
					<td>
						${table6.newExtrapulmonaryEligible0514 }</td>
					<td>
						${table6.newExtrapulmonaryCured0514 }</td>
					<td>
						${table6.newExtrapulmonaryCompleted0514 }</td>
					<td>
						${table6.newExtrapulmonaryDiedTB0514 }</td>
					<td>
						${table6.newExtrapulmonaryDiedNotTB0514}</td>
					<td>
						${table6.newExtrapulmonaryFailed0514 }</td>
					<td>
						${table6.newExtrapulmonaryDefaulted0514 }</td>
					<td>
						${table6.newExtrapulmonaryEligible0514 }</td>
					
					<td>
						${table6.newExtrapulmonarySLD0514 }</td>
					<td>
						${table6.newExtrapulmonaryCanceled0514 }</td>
					<td>
						${table6.newExtrapulmonaryDetected0514 - (table6.newExtrapulmonaryEligible0514 + table6.newExtrapulmonarySLD0514 + table6.newExtrapulmonaryCanceled0514 ) }</td>
					
				</tr>
				<tr>
					<td>
						1.3.3</td>
					<td>
						15-17</td>
					<td>
						${table6.newExtrapulmonaryDetected1517 }</td>
					<td>
						${table6.newExtrapulmonaryEligible1517 }</td>
					<td>
						${table6.newExtrapulmonaryCured1517 }</td>
					<td>
						${table6.newExtrapulmonaryCompleted1517 }</td>
					<td>
						${table6.newExtrapulmonaryDiedTB1517 }</td>
					<td>
						${table6.newExtrapulmonaryDiedNotTB1517}</td>
					<td>
						${table6.newExtrapulmonaryFailed1517 }</td>
					<td>
						${table6.newExtrapulmonaryDefaulted1517 }</td>
					<td>
						${table6.newExtrapulmonaryEligible1517 }</td>
					<td>
						${table6.newExtrapulmonarySLD1517 }</td>
					<td>
						${table6.newExtrapulmonaryCanceled1517 }</td>
					<td>
						${table6.newExtrapulmonaryDetected1517 - (table6.newExtrapulmonaryEligible1517 + table6.newExtrapulmonarySLD1517 + table6.newExtrapulmonaryCanceled1517 ) }</td>
				</tr>
				<tr>
					<td>
						&nbsp;</td>
					<td style="font: bold;">
						<spring:message code="dotsreports.tb08.total1" /></td>
					<td>
						${ table6.newPulmonaryBCDetected + table6.newPulmonaryCDDetected + table6.newExtrapulmonaryDetected }</td>
					<td>
						${ table6.newPulmonaryBCEligible + table6.newPulmonaryCDEligible + table6.newExtrapulmonaryEligible }</td>
					<td>
						${ table6.newPulmonaryBCCured + table6.newPulmonaryCDCured + table6.newExtrapulmonaryCured }</td></td>
					<td>
						${ table6.newPulmonaryBCCompleted + table6.newPulmonaryCDCompleted + table6.newExtrapulmonaryCompleted }</td>
					<td>
						${ table6.newPulmonaryBCDiedTB + table6.newPulmonaryCDDiedTB + table6.newExtrapulmonaryDiedTB }</td>
					<td>
						${ table6.newPulmonaryBCDiedNotTB + table6.newPulmonaryCDDiedNotTB + table6.newExtrapulmonaryDiedNotTB }</td>
					<td>
						${ table6.newPulmonaryBCFailed + table6.newPulmonaryCDFailed + table6.newExtrapulmonaryFailed }</td>
					<td>
						${ table6.newPulmonaryBCDefaulted + table6.newPulmonaryCDDefaulted + table6.newExtrapulmonaryDefaulted }</td>
					<td>
						${ table6.newPulmonaryBCEligible + table6.newPulmonaryCDEligible + table6.newExtrapulmonaryEligible }</td>
					<td>
						${ table6.newPulmonaryBCSLD + table6.newPulmonaryCDSLD + table6.newExtrapulmonarySLD }</td>
					<td>
						${ table6.newPulmonaryBCCanceled + table6.newPulmonaryCDCanceled + table6.newExtrapulmonaryCanceled }</td>
					<td>
						${ table6.newPulmonaryBCDetected + table6.newPulmonaryCDDetected + table6.newExtrapulmonaryDetected - (table6.newPulmonaryBCEligible + table6.newPulmonaryCDEligible + table6.newExtrapulmonaryEligible + table6.newPulmonaryBCSLD + table6.newPulmonaryCDSLD + table6.newExtrapulmonarySLD + table6.newPulmonaryBCCanceled + table6.newPulmonaryCDCanceled + table6.newExtrapulmonaryCanceled) }</td>
					
				</tr>
				<tr>
					<td>
						&nbsp;</td>
					<td>
						0-4</td>
					<td>
						${ table6.newPulmonaryBCDetected04 + table6.newPulmonaryCDDetected04 + table6.newExtrapulmonaryDetected04 }</td>
					<td>
						${ table6.newPulmonaryBCEligible04 + table6.newPulmonaryCDEligible04 + table6.newExtrapulmonaryEligible04 }</td>
					<td>
						${ table6.newPulmonaryBCCured04 + table6.newPulmonaryCDCured04 + table6.newExtrapulmonaryCured04 }</td></td>
					<td>
						${ table6.newPulmonaryBCCompleted04 + table6.newPulmonaryCDCompleted04 + table6.newExtrapulmonaryCompleted04 }</td>
					<td>
						${ table6.newPulmonaryBCDiedTB04 + table6.newPulmonaryCDDiedTB04 + table6.newExtrapulmonaryDiedTB04 }</td>
					<td>
						${ table6.newPulmonaryBCDiedNotTB04 + table6.newPulmonaryCDDiedNotTB04 + table6.newExtrapulmonaryDiedNotTB04 }</td>
					<td>
						${ table6.newPulmonaryBCFailed04 + table6.newPulmonaryCDFailed04 + table6.newExtrapulmonaryFailed04 }</td>
					<td>
						${ table6.newPulmonaryBCDefaulted04 + table6.newPulmonaryCDDefaulted04 + table6.newExtrapulmonaryDefaulted04 }</td>
					<td>
						${ table6.newPulmonaryBCEligible04 + table6.newPulmonaryCDEligible04 + table6.newExtrapulmonaryEligible04 }</td>
					
					<td>
						${ table6.newPulmonaryBCSLD04 + table6.newPulmonaryCDSLD04 + table6.newExtrapulmonarySLD04 }</td>
					<td>
						${ table6.newPulmonaryBCCanceled04 + table6.newPulmonaryCDCanceled04 + table6.newExtrapulmonaryCanceled04 }</td>
					
					<td>
						${ table6.newPulmonaryBCDetected04 + table6.newPulmonaryCDDetected04 + table6.newExtrapulmonaryDetected04 - (table6.newPulmonaryBCEligible04 + table6.newPulmonaryCDEligible04 + table6.newExtrapulmonaryEligible04 + table6.newPulmonaryBCSLD04 + table6.newPulmonaryCDSLD04 + table6.newExtrapulmonarySLD04 + table6.newPulmonaryBCCanceled04 + table6.newPulmonaryCDCanceled04 + table6.newExtrapulmonaryCanceled04) }</td>
				</tr>
				<tr>
					<td>
						&nbsp;</td>
					<td>
						5-14</td>
					<td>
						${ table6.newPulmonaryBCDetected0514 + table6.newPulmonaryCDDetected0514 + table6.newExtrapulmonaryDetected0514 }</td>
					<td>
						${ table6.newPulmonaryBCEligible0514 + table6.newPulmonaryCDEligible0514 + table6.newExtrapulmonaryEligible0514 }</td>
					<td>
						${ table6.newPulmonaryBCCured0514 + table6.newPulmonaryCDCured0514 + table6.newExtrapulmonaryCured0514 }</td></td>
					<td>
						${ table6.newPulmonaryBCCompleted0514 + table6.newPulmonaryCDCompleted0514 + table6.newExtrapulmonaryCompleted0514 }</td>
					<td>
						${ table6.newPulmonaryBCDiedTB0514 + table6.newPulmonaryCDDiedTB0514 + table6.newExtrapulmonaryDiedTB0514 }</td>
					<td>
						${ table6.newPulmonaryBCDiedNotTB0514 + table6.newPulmonaryCDDiedNotTB0514 + table6.newExtrapulmonaryDiedNotTB0514 }</td>
					<td>
						${ table6.newPulmonaryBCFailed0514 + table6.newPulmonaryCDFailed0514 + table6.newExtrapulmonaryFailed0514 }</td>
					<td>
						${ table6.newPulmonaryBCDefaulted0514 + table6.newPulmonaryCDDefaulted0514 + table6.newExtrapulmonaryDefaulted0514 }</td>
					<td>
						${ table6.newPulmonaryBCEligible0514 + table6.newPulmonaryCDEligible0514 + table6.newExtrapulmonaryEligible0514 }</td>
					<td>
						${ table6.newPulmonaryBCSLD0514 + table6.newPulmonaryCDSLD0514 + table6.newExtrapulmonarySLD0514 }</td>
					<td>
						${ table6.newPulmonaryBCCanceled0514 + table6.newPulmonaryCDCanceled0514 + table6.newExtrapulmonaryCanceled0514 }</td>
					<td>
						${ table6.newPulmonaryBCDetected0514 + table6.newPulmonaryCDDetected0514 + table6.newExtrapulmonaryDetected0514 - (table6.newPulmonaryBCEligible0514 + table6.newPulmonaryCDEligible0514 + table6.newExtrapulmonaryEligible0514 + table6.newPulmonaryBCSLD0514 + table6.newPulmonaryCDSLD0514 + table6.newExtrapulmonarySLD0514 + table6.newPulmonaryBCCanceled0514 + table6.newPulmonaryCDCanceled0514 + table6.newExtrapulmonaryCanceled0514) }</td>
				</tr>
				<tr>
					<td>
						&nbsp;</td>
					<td>
						15-17</td>
					<td>
						${ table6.newPulmonaryBCDetected1517 + table6.newPulmonaryCDDetected1517 + table6.newExtrapulmonaryDetected1517 }</td>
					<td>
						${ table6.newPulmonaryBCEligible1517 + table6.newPulmonaryCDEligible1517 + table6.newExtrapulmonaryEligible1517 }</td>
					<td>
						${ table6.newPulmonaryBCCured1517 + table6.newPulmonaryCDCured1517 + table6.newExtrapulmonaryCured1517 }</td></td>
					<td>
						${ table6.newPulmonaryBCCompleted1517 + table6.newPulmonaryCDCompleted1517 + table6.newExtrapulmonaryCompleted1517 }</td>
					<td>
						${ table6.newPulmonaryBCDiedTB1517 + table6.newPulmonaryCDDiedTB1517 + table6.newExtrapulmonaryDiedTB1517 }</td>
					<td>
						${ table6.newPulmonaryBCDiedNotTB1517 + table6.newPulmonaryCDDiedNotTB1517 + table6.newExtrapulmonaryDiedNotTB1517 }</td>
					<td>
						${ table6.newPulmonaryBCFailed1517 + table6.newPulmonaryCDFailed1517 + table6.newExtrapulmonaryFailed1517 }</td>
					<td>
						${ table6.newPulmonaryBCDefaulted1517 + table6.newPulmonaryCDDefaulted1517 + table6.newExtrapulmonaryDefaulted1517 }</td>
					<td>
						${ table6.newPulmonaryBCEligible1517 + table6.newPulmonaryCDEligible1517 + table6.newExtrapulmonaryEligible1517 }</td>
					<td>
						${ table6.newPulmonaryBCSLD1517 + table6.newPulmonaryCDSLD1517 + table6.newExtrapulmonarySLD1517 }</td>
					<td>
						${ table6.newPulmonaryBCCanceled1517 + table6.newPulmonaryCDCanceled1517 + table6.newExtrapulmonaryCanceled1517 }</td>
					<td>
						${ table6.newPulmonaryBCDetected1517 + table6.newPulmonaryCDDetected1517 + table6.newExtrapulmonaryDetected1517 - (table6.newPulmonaryBCEligible1517 + table6.newPulmonaryCDEligible1517 + table6.newExtrapulmonaryEligible1517 + table6.newPulmonaryBCSLD1517 + table6.newPulmonaryCDSLD1517 + table6.newExtrapulmonarySLD1517 + table6.newPulmonaryBCCanceled1517 + table6.newPulmonaryCDCanceled1517 + table6.newExtrapulmonaryCanceled1517) }</td>
				</tr>
				<tr>
					<td style="font: bold;">
						2</td>
					<td style="font: bold;">
						<spring:message code="dotsreports.tb08.relapses"/></td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
				</tr>
				<tr>
					<td>
						2.1</td>
					<td>
						<spring:message code="dotsreports.tb08.pulmonaryBC"/></td>
					<td>
						${table6.relapsePulmonaryBCDetected }</td>
					<td>
						${table6.relapsePulmonaryBCEligible }</td>
					<td>
						${table6.relapsePulmonaryBCCured }</td>
					<td>
						${table6.relapsePulmonaryBCCompleted }</td>
					<td>
						${table6.relapsePulmonaryBCDiedTB }</td>
					<td>
						${table6.relapsePulmonaryBCDiedNotTB}</td>
					<td>
						${table6.relapsePulmonaryBCFailed }</td>
					<td>
						${table6.relapsePulmonaryBCDefaulted }</td>
					<td>
						${table6.relapsePulmonaryBCEligible }</td>
					<td>
						${table6.relapsePulmonaryBCSLD }</td>
					<td>
						${table6.relapsePulmonaryBCCanceled }</td>
					<td>
						${table6.relapsePulmonaryBCDetected - (table6.relapsePulmonaryBCEligible + table6.relapsePulmonaryBCSLD + table6.relapsePulmonaryBCCanceled) }</td>
				</tr>
				<tr>
					<td>2.1.1</td>
					<td>
						0-4</td>
					<td>
						${table6.relapsePulmonaryBCDetected04 }</td>
					<td>
						${table6.relapsePulmonaryBCEligible04 }</td>
					<td>
						${table6.relapsePulmonaryBCCured04 }</td>
					<td>
						${table6.relapsePulmonaryBCCompleted04 }</td>
					<td>
						${table6.relapsePulmonaryBCDiedTB04 }</td>
					<td>
						${table6.relapsePulmonaryBCDiedNotTB04}</td>
					<td>
						${table6.relapsePulmonaryBCFailed04 }</td>
					<td>
						${table6.relapsePulmonaryBCDefaulted04 }</td>
					<td>
						${table6.relapsePulmonaryBCEligible04 }</td>
					<td>
						${table6.relapsePulmonaryBCSLD04 }</td>
					<td>
						${table6.relapsePulmonaryBCCanceled04 }</td>
					<td>
						${table6.relapsePulmonaryBCDetected04 - (table6.relapsePulmonaryBCEligible04 + table6.relapsePulmonaryBCSLD04 + table6.relapsePulmonaryBCCanceled04) }</td>
				</tr>
				<tr>
					<td>
						2.1.2</td>
					<td>
						5-14</td>
					<td>
						${table6.relapsePulmonaryBCDetected0514 }</td>
					<td>
						${table6.relapsePulmonaryBCEligible0514 }</td>
					<td>
						${table6.relapsePulmonaryBCCured0514 }</td>
					<td>
						${table6.relapsePulmonaryBCCompleted0514 }</td>
					<td>
						${table6.relapsePulmonaryBCDiedTB0514 }</td>
					<td>
						${table6.relapsePulmonaryBCDiedNotTB0514}</td>
					<td>
						${table6.relapsePulmonaryBCFailed0514 }</td>
					<td>
						${table6.relapsePulmonaryBCDefaulted0514 }</td>
					<td>
						${table6.relapsePulmonaryBCEligible0514 }</td>
					<td>
						${table6.relapsePulmonaryBCSLD0514 }</td>
					<td>
						${table6.relapsePulmonaryBCCanceled0514 }</td>
					<td>
						${table6.relapsePulmonaryBCDetected0514 - (table6.relapsePulmonaryBCEligible0514 + table6.relapsePulmonaryBCSLD0514 + table6.relapsePulmonaryBCCanceled0514) }</td>
				</tr>
				<tr>
					<td>
						2.1.3</td>
					<td>
						15-17</td>
					<td>
						${table6.relapsePulmonaryBCDetected1517 }</td>
					<td>
						${table6.relapsePulmonaryBCEligible1517 }</td>
					<td>
						${table6.relapsePulmonaryBCCured1517 }</td>
					<td>
						${table6.relapsePulmonaryBCCompleted1517 }</td>
					<td>
						${table6.relapsePulmonaryBCDiedTB1517 }</td>
					<td>
						${table6.relapsePulmonaryBCDiedNotTB1517}</td>
					<td>
						${table6.relapsePulmonaryBCFailed1517 }</td>
					<td>
						${table6.relapsePulmonaryBCDefaulted1517 }</td>
					<td>
						${table6.relapsePulmonaryBCEligible1517 }</td>
					<td>
						${table6.relapsePulmonaryBCSLD1517 }</td>
					<td>
						${table6.relapsePulmonaryBCCanceled1517 }</td>
					<td>
						${table6.relapsePulmonaryBCDetected1517 - (table6.relapsePulmonaryBCEligible1517 + table6.relapsePulmonaryBCSLD1517 + table6.relapsePulmonaryBCCanceled1517) }</td>
				</tr>
				<tr>
					<td>
						2.2</td>
					<td>
						<spring:message code="dotsreports.tb08.pulmonaryCD"/> </td>
					<td>
						${table6.relapsePulmonaryCDDetected }</td>
					<td>
						${table6.relapsePulmonaryCDEligible }</td>
					<td>
						${table6.relapsePulmonaryCDCured }</td>
					<td>
						${table6.relapsePulmonaryCDCompleted }</td>
					<td>
						${table6.relapsePulmonaryCDDiedTB }</td>
					<td>
						${table6.relapsePulmonaryCDDiedNotTB}</td>
					<td>
						${table6.relapsePulmonaryCDFailed }</td>
					<td>
						${table6.relapsePulmonaryCDDefaulted }</td>
					<td>
						${table6.relapsePulmonaryCDEligible }</td>
					<td>
						${table6.relapsePulmonaryCDSLD }</td>
					<td>
						${table6.relapsePulmonaryCDCanceled }</td>

					<td>
						${table6.relapsePulmonaryCDDetected - (table6.relapsePulmonaryCDEligible + table6.relapsePulmonaryCDSLD + table6.relapsePulmonaryCDCanceled) }</td>
				</tr>
				<tr>
					<td>
						2.2.1</td>
					<td>
						0-4</td>
					<td>
						${table6.relapsePulmonaryCDDetected04 }</td>
					<td>
						${table6.relapsePulmonaryCDEligible04 }</td>
					<td>
						${table6.relapsePulmonaryCDCured04 }</td>
					<td>
						${table6.relapsePulmonaryCDCompleted04 }</td>
					<td>
						${table6.relapsePulmonaryCDDiedTB04 }</td>
					<td>
						${table6.relapsePulmonaryCDDiedNotTB04}</td>
					<td>
						${table6.relapsePulmonaryCDFailed04 }</td>
					<td>
						${table6.relapsePulmonaryCDDefaulted04 }</td>
					<td>
						${table6.relapsePulmonaryCDEligible04 }</td>
					<td>
						${table6.relapsePulmonaryCDSLD04 }</td>
					<td>
						${table6.relapsePulmonaryCDCanceled04 }</td>
					<td>
						${table6.relapsePulmonaryCDDetected04 - (table6.relapsePulmonaryCDEligible04 + table6.relapsePulmonaryCDSLD04 + table6.relapsePulmonaryCDCanceled04) }</td>
				</tr>
				<tr>
					<td>
						2.2.2</td>
					<td>
						5-14</td>
					<td>
						${table6.relapsePulmonaryCDDetected0514 }</td>
					<td>
						${table6.relapsePulmonaryCDEligible0514 }</td>
					<td>
						${table6.relapsePulmonaryCDCured0514 }</td>
					<td>
						${table6.relapsePulmonaryCDCompleted0514 }</td>
					<td>
						${table6.relapsePulmonaryCDDiedTB0514 }</td>
					<td>
						${table6.relapsePulmonaryCDDiedNotTB0514}</td>
					<td>
						${table6.relapsePulmonaryCDFailed0514 }</td>
					<td>
						${table6.relapsePulmonaryCDDefaulted0514 }</td>
					<td>
						${table6.relapsePulmonaryCDEligible0514 }</td>
					<td>
						${table6.relapsePulmonaryCDSLD0514 }</td>
					<td>
						${table6.relapsePulmonaryCDCanceled0514 }</td>
					<td>
						${table6.relapsePulmonaryCDDetected0514 - (table6.relapsePulmonaryCDEligible0514 + table6.relapsePulmonaryCDSLD0514 + table6.relapsePulmonaryCDCanceled0514) }</td>
				</tr>
				<tr>
					<td>
						2.2.3</td>
					<td>
						15-17</td>
					<td>
						${table6.relapsePulmonaryCDDetected1517 }</td>
					<td>
						${table6.relapsePulmonaryCDEligible1517 }</td>
					<td>
						${table6.relapsePulmonaryCDCured1517 }</td>
					<td>
						${table6.relapsePulmonaryCDCompleted1517 }</td>
					<td>
						${table6.relapsePulmonaryCDDiedTB1517 }</td>
					<td>
						${table6.relapsePulmonaryCDDiedNotTB1517}</td>
					<td>
						${table6.relapsePulmonaryCDFailed1517 }</td>
					<td>
						${table6.relapsePulmonaryCDDefaulted1517 }</td>
					<td>
						${table6.relapsePulmonaryCDEligible1517 }</td>
					
					<td>
						${table6.relapsePulmonaryCDSLD1517 }</td>
					<td>
						${table6.relapsePulmonaryCDCanceled1517 }</td>
					<td>
						${table6.relapsePulmonaryCDDetected1517 - (table6.relapsePulmonaryCDEligible1517 + table6.relapsePulmonaryCDSLD1517 + table6.relapsePulmonaryCDCanceled1517) }</td>
					
				</tr>
				<tr>
					<td>
						2.3</td>
					<td>
						<spring:message code="dotsreports.tb08.eptb"/> </td>
					<td>
						${table6.relapseExtrapulmonaryDetected }</td>
					<td>
						${table6.relapseExtrapulmonaryEligible }</td>
					<td>
						${table6.relapseExtrapulmonaryCured }</td>
					<td>
						${table6.relapseExtrapulmonaryCompleted }</td>
					<td>
						${table6.relapseExtrapulmonaryDiedTB }</td>
					<td>
						${table6.relapseExtrapulmonaryDiedNotTB}</td>
					<td>
						${table6.relapseExtrapulmonaryFailed }</td>
					<td>
						${table6.relapseExtrapulmonaryDefaulted }</td>
					<td>
						${table6.relapseExtrapulmonaryEligible }</td>
					<td>
						${table6.relapseExtrapulmonarySLD }</td>
					<td>
						${table6.relapseExtrapulmonaryCanceled }</td>
					<td>
						${table6.relapseExtrapulmonaryDetected - (table6.relapseExtrapulmonaryEligible + table6.relapseExtrapulmonarySLD + table6.relapseExtrapulmonaryCanceled) }</td>
				</tr>
				<tr>
					<td>
						2.3.1</td>
					<td>
						0-4</td>
					<td>
						${table6.relapseExtrapulmonaryDetected04 }</td>
					<td>
						${table6.relapseExtrapulmonaryEligible04 }</td>
					<td>
						${table6.relapseExtrapulmonaryCured04 }</td>
					<td>
						${table6.relapseExtrapulmonaryCompleted04 }</td>
					<td>
						${table6.relapseExtrapulmonaryDiedTB04 }</td>
					<td>
						${table6.relapseExtrapulmonaryDiedNotTB04}</td>
					<td>
						${table6.relapseExtrapulmonaryFailed04 }</td>
					<td>
						${table6.relapseExtrapulmonaryDefaulted04 }</td>
					<td>
						${table6.relapseExtrapulmonaryEligible04 }</td>
					<td>
						${table6.relapseExtrapulmonarySLD04 }</td>
					<td>
						${table6.relapseExtrapulmonaryCanceled04 }</td>
					<td>
						${table6.relapseExtrapulmonaryDetected04 - (table6.relapseExtrapulmonaryEligible04 + table6.relapseExtrapulmonarySLD04 + table6.relapseExtrapulmonaryCanceled04) }</td>
				</tr>
				<tr>
					<td>
						2.3.2</td>
					<td>
						5-14</td>
					<td>
						${table6.relapseExtrapulmonaryDetected0514 }</td>
					<td>
						${table6.relapseExtrapulmonaryEligible0514 }</td>
					<td>
						${table6.relapseExtrapulmonaryCured0514 }</td>
					<td>
						${table6.relapseExtrapulmonaryCompleted0514 }</td>
					<td>
						${table6.relapseExtrapulmonaryDiedTB0514 }</td>
					<td>
						${table6.relapseExtrapulmonaryDiedNotTB0514}</td>
					<td>
						${table6.relapseExtrapulmonaryFailed0514 }</td>
					<td>
						${table6.relapseExtrapulmonaryDefaulted0514 }</td>
					<td>
						${table6.relapseExtrapulmonaryEligible0514 }</td>
					
					<td>
						${table6.relapseExtrapulmonarySLD0514 }</td>
					<td>
						${table6.relapseExtrapulmonaryCanceled0514 }</td>
					<td>
						${table6.relapseExtrapulmonaryDetected0514 - (table6.relapseExtrapulmonaryEligible0514 + table6.relapseExtrapulmonarySLD0514 + table6.relapseExtrapulmonaryCanceled0514) }</td>
					
				</tr>
				<tr>
					<td>
						2.3.3</td>
					<td>
						15-17</td>
					<td>
						${table6.relapseExtrapulmonaryDetected1517 }</td>
					<td>
						${table6.relapseExtrapulmonaryEligible1517 }</td>
					<td>
						${table6.relapseExtrapulmonaryCured1517 }</td>
					<td>
						${table6.relapseExtrapulmonaryCompleted1517 }</td>
					<td>
						${table6.relapseExtrapulmonaryDiedTB1517 }</td>
					<td>
						${table6.relapseExtrapulmonaryDiedNotTB1517}</td>
					<td>
						${table6.relapseExtrapulmonaryFailed1517 }</td>
					<td>
						${table6.relapseExtrapulmonaryDefaulted1517 }</td>
					<td>
						${table6.relapseExtrapulmonaryEligible1517 }</td>
					<td>
						${table6.relapseExtrapulmonarySLD1517 }</td>
					<td>
						${table6.relapseExtrapulmonaryCanceled1517 }</td>
					<td>
						${table6.relapseExtrapulmonaryDetected1517 - (table6.relapseExtrapulmonaryEligible1517 + table6.relapseExtrapulmonarySLD1517 + table6.relapseExtrapulmonaryCanceled1517) }</td>
				</tr>
				<tr>
					<td>
						&nbsp;</td>
					<td style="font:bold;">
					<spring:message code="dotsreports.tb08.total2"/></td>
					<td>
						${ table6.relapsePulmonaryBCDetected + table6.relapsePulmonaryCDDetected + table6.relapseExtrapulmonaryDetected }</td>
					<td>
						${ table6.relapsePulmonaryBCEligible + table6.relapsePulmonaryCDEligible + table6.relapseExtrapulmonaryEligible }</td>
					<td>
						${ table6.relapsePulmonaryBCCured + table6.relapsePulmonaryCDCured + table6.relapseExtrapulmonaryCured }</td></td>
					<td>
						${ table6.relapsePulmonaryBCCompleted + table6.relapsePulmonaryCDCompleted + table6.relapseExtrapulmonaryCompleted }</td>
					<td>
						${ table6.relapsePulmonaryBCDiedTB + table6.relapsePulmonaryCDDiedTB + table6.relapseExtrapulmonaryDiedTB }</td>
					<td>
						${ table6.relapsePulmonaryBCDiedNotTB + table6.relapsePulmonaryCDDiedNotTB + table6.relapseExtrapulmonaryDiedNotTB }</td>
					<td>
						${ table6.relapsePulmonaryBCFailed + table6.relapsePulmonaryCDFailed + table6.relapseExtrapulmonaryFailed }</td>
					<td>
						${ table6.relapsePulmonaryBCDefaulted + table6.relapsePulmonaryCDDefaulted + table6.relapseExtrapulmonaryDefaulted }</td>
					<td>
						${ table6.relapsePulmonaryBCEligible + table6.relapsePulmonaryCDEligible + table6.relapseExtrapulmonaryEligible }</td>
					
					<td>
						${ table6.relapsePulmonaryBCSLD + table6.relapsePulmonaryCDSLD + table6.relapseExtrapulmonarySLD }</td>
					<td>
						${ table6.relapsePulmonaryBCCanceled + table6.relapsePulmonaryCDCanceled + table6.relapseExtrapulmonaryCanceled }</td>
					<td>
						${ table6.relapsePulmonaryBCDetected + table6.relapsePulmonaryCDDetected + table6.relapseExtrapulmonaryDetected - (table6.relapsePulmonaryBCEligible + table6.relapsePulmonaryCDEligible + table6.relapseExtrapulmonaryEligible + table6.relapsePulmonaryBCSLD + table6.relapsePulmonaryCDSLD + table6.relapseExtrapulmonarySLD + table6.relapsePulmonaryBCCanceled + table6.relapsePulmonaryCDCanceled + table6.relapseExtrapulmonaryCanceled) }</td>
					
				</tr>
				<tr>
					<td>
						&nbsp;</td>
					<td>
						0-4</td>
					<td>
						${ table6.relapsePulmonaryBCDetected04 + table6.relapsePulmonaryCDDetected04 + table6.relapseExtrapulmonaryDetected04 }</td>
					<td>
						${ table6.relapsePulmonaryBCEligible04 + table6.relapsePulmonaryCDEligible04 + table6.relapseExtrapulmonaryEligible04 }</td>
					<td>
						${ table6.relapsePulmonaryBCCured04 + table6.relapsePulmonaryCDCured04 + table6.relapseExtrapulmonaryCured04 }</td></td>
					<td>
						${ table6.relapsePulmonaryBCCompleted04 + table6.relapsePulmonaryCDCompleted04 + table6.relapseExtrapulmonaryCompleted04 }</td>
					<td>
						${ table6.relapsePulmonaryBCDiedTB04 + table6.relapsePulmonaryCDDiedTB04 + table6.relapseExtrapulmonaryDiedTB04 }</td>
					<td>
						${ table6.relapsePulmonaryBCDiedNotTB04 + table6.relapsePulmonaryCDDiedNotTB04 + table6.relapseExtrapulmonaryDiedNotTB04 }</td>
					<td>
						${ table6.relapsePulmonaryBCFailed04 + table6.relapsePulmonaryCDFailed04 + table6.relapseExtrapulmonaryFailed04 }</td>
					<td>
						${ table6.relapsePulmonaryBCDefaulted04 + table6.relapsePulmonaryCDDefaulted04 + table6.relapseExtrapulmonaryDefaulted04 }</td>
					<td>
						${ table6.relapsePulmonaryBCEligible04 + table6.relapsePulmonaryCDEligible04 + table6.relapseExtrapulmonaryEligible04 }</td>
					<td>
						${ table6.relapsePulmonaryBCSLD04 + table6.relapsePulmonaryCDSLD04 + table6.relapseExtrapulmonarySLD04 }</td>
					<td>
						${ table6.relapsePulmonaryBCCanceled04 + table6.relapsePulmonaryCDCanceled04 + table6.relapseExtrapulmonaryCanceled04 }</td>
					<td>
						${ table6.relapsePulmonaryBCDetected04 + table6.relapsePulmonaryCDDetected04 + table6.relapseExtrapulmonaryDetected04 - (table6.relapsePulmonaryBCEligible04 + table6.relapsePulmonaryCDEligible04 + table6.relapseExtrapulmonaryEligible04 + table6.relapsePulmonaryBCSLD04 + table6.relapsePulmonaryCDSLD04 + table6.relapseExtrapulmonarySLD04 + table6.relapsePulmonaryBCCanceled04 + table6.relapsePulmonaryCDCanceled04 + table6.relapseExtrapulmonaryCanceled04) }</td>
				</tr>
				<tr>
					<td>
						&nbsp;</td>
					<td>
						5-14</td>
					<td>
						${ table6.relapsePulmonaryBCDetected0514 + table6.relapsePulmonaryCDDetected0514 + table6.relapseExtrapulmonaryDetected0514 }</td>
					<td>
						${ table6.relapsePulmonaryBCEligible0514 + table6.relapsePulmonaryCDEligible0514 + table6.relapseExtrapulmonaryEligible0514 }</td>
					<td>
						${ table6.relapsePulmonaryBCCured0514 + table6.relapsePulmonaryCDCured0514 + table6.relapseExtrapulmonaryCured0514 }</td></td>
					<td>
						${ table6.relapsePulmonaryBCCompleted0514 + table6.relapsePulmonaryCDCompleted0514 + table6.relapseExtrapulmonaryCompleted0514 }</td>
					<td>
						${ table6.relapsePulmonaryBCDiedTB0514 + table6.relapsePulmonaryCDDiedTB0514 + table6.relapseExtrapulmonaryDiedTB0514 }</td>
					<td>
						${ table6.relapsePulmonaryBCDiedNotTB0514 + table6.relapsePulmonaryCDDiedNotTB0514 + table6.relapseExtrapulmonaryDiedNotTB0514 }</td>
					<td>
						${ table6.relapsePulmonaryBCFailed0514 + table6.relapsePulmonaryCDFailed0514 + table6.relapseExtrapulmonaryFailed0514 }</td>
					<td>
						${ table6.relapsePulmonaryBCDefaulted0514 + table6.relapsePulmonaryCDDefaulted0514 + table6.relapseExtrapulmonaryDefaulted0514 }</td>
					<td>
						${ table6.relapsePulmonaryBCEligible0514 + table6.relapsePulmonaryCDEligible0514 + table6.relapseExtrapulmonaryEligible0514 }</td>
					<td>
						${ table6.relapsePulmonaryBCSLD0514 + table6.relapsePulmonaryCDSLD0514 + table6.relapseExtrapulmonarySLD0514 }</td>
					<td>
						${ table6.relapsePulmonaryBCCanceled0514 + table6.relapsePulmonaryCDCanceled0514 + table6.relapseExtrapulmonaryCanceled0514 }</td>
					<td>
						${ table6.relapsePulmonaryBCDetected0514 + table6.relapsePulmonaryCDDetected0514 + table6.relapseExtrapulmonaryDetected0514 - (table6.relapsePulmonaryBCEligible0514 + table6.relapsePulmonaryCDEligible0514 + table6.relapseExtrapulmonaryEligible0514 + table6.relapsePulmonaryBCSLD0514 + table6.relapsePulmonaryCDSLD0514 + table6.relapseExtrapulmonarySLD0514 + table6.relapsePulmonaryBCCanceled0514 + table6.relapsePulmonaryCDCanceled0514 + table6.relapseExtrapulmonaryCanceled0514) }</td>
					
				</tr>
				<tr>
					<td>
						&nbsp;</td>
					<td>
						15-17</td>
					<td>
						${ table6.relapsePulmonaryBCDetected1517 + table6.relapsePulmonaryCDDetected1517 + table6.relapseExtrapulmonaryDetected1517 }</td>
					<td>
						${ table6.relapsePulmonaryBCEligible1517 + table6.relapsePulmonaryCDEligible1517 + table6.relapseExtrapulmonaryEligible1517 }</td>
					<td>
						${ table6.relapsePulmonaryBCCured1517 + table6.relapsePulmonaryCDCured1517 + table6.relapseExtrapulmonaryCured1517 }</td></td>
					<td>
						${ table6.relapsePulmonaryBCCompleted1517 + table6.relapsePulmonaryCDCompleted1517 + table6.relapseExtrapulmonaryCompleted1517 }</td>
					<td>
						${ table6.relapsePulmonaryBCDiedTB1517 + table6.relapsePulmonaryCDDiedTB1517 + table6.relapseExtrapulmonaryDiedTB1517 }</td>
					<td>
						${ table6.relapsePulmonaryBCDiedNotTB1517 + table6.relapsePulmonaryCDDiedNotTB1517 + table6.relapseExtrapulmonaryDiedNotTB1517 }</td>
					<td>
						${ table6.relapsePulmonaryBCFailed1517 + table6.relapsePulmonaryCDFailed1517 + table6.relapseExtrapulmonaryFailed1517 }</td>
					<td>
						${ table6.relapsePulmonaryBCDefaulted1517 + table6.relapsePulmonaryCDDefaulted1517 + table6.relapseExtrapulmonaryDefaulted1517 }</td>
					<td>
						${ table6.relapsePulmonaryBCEligible1517 + table6.relapsePulmonaryCDEligible1517 + table6.relapseExtrapulmonaryEligible1517 }</td>
					
					<td>
						${ table6.relapsePulmonaryBCSLD1517 + table6.relapsePulmonaryCDSLD1517 + table6.relapseExtrapulmonarySLD1517 }</td>
					<td>
						${ table6.relapsePulmonaryBCCanceled1517 + table6.relapsePulmonaryCDCanceled1517 + table6.relapseExtrapulmonaryCanceled1517 }</td>
					<td>
						${ table6.relapsePulmonaryBCDetected1517 + table6.relapsePulmonaryCDDetected1517 + table6.relapseExtrapulmonaryDetected1517 - (table6.relapsePulmonaryBCEligible1517 + table6.relapsePulmonaryCDEligible1517 + table6.relapseExtrapulmonaryEligible1517 + table6.relapsePulmonaryBCSLD1517 + table6.relapsePulmonaryCDSLD1517 + table6.relapseExtrapulmonarySLD1517 + table6.relapsePulmonaryBCCanceled1517 + table6.relapsePulmonaryCDCanceled1517 + table6.relapseExtrapulmonaryCanceled1517) }</td>
					
				</tr>
				<tr>
					<td style="font: bold;">
						3</td>
					<td style="font: bold;">
						<spring:message code="dotsreports.tb08.afterFailure"/></td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					
				</tr>
				<tr>
					<td>
						3.1</td>
					<td>
						<spring:message code="dotsreports.tb08.pulmonaryBC"/> </td>
					<td>
						${table6.failurePulmonaryBCDetected }</td>
					<td>
						${table6.failurePulmonaryBCEligible }</td>
					<td>
						${table6.failurePulmonaryBCCured }</td>
					<td>
						${table6.failurePulmonaryBCCompleted }</td>
					<td>
						${table6.failurePulmonaryBCDiedTB }</td>
					<td>
						${table6.failurePulmonaryBCDiedNotTB }</td>
					<td>
						${table6.failurePulmonaryBCFailed }</td>
					<td>
						${table6.failurePulmonaryBCDefaulted}</td>

					<td>
						${table6.failurePulmonaryBCEligible }</td>
					
					<td>
						${table6.failurePulmonaryBCSLD}</td>
					<td>
						${table6.failurePulmonaryBCCanceled }</td>
					<td>
						${table6.failurePulmonaryBCDetected  - (table6.failurePulmonaryBCEligible + table6.failurePulmonaryBCSLD + table6.failurePulmonaryBCCanceled ) }</td>
						
				</tr>
				<tr>
					<td>
						3.2</td>
					<td>
						<spring:message code="dotsreports.tb08.pulmonaryCD"/></td>
					<td>
						${table6.failurePulmonaryCDDetected }</td>
					<td>
						${table6.failurePulmonaryCDEligible }</td>
					<td>
						${table6.failurePulmonaryCDCured }</td>
					<td>
						${table6.failurePulmonaryCDCompleted }</td>
					<td>
						${table6.failurePulmonaryCDDiedTB }</td>
					<td>
						${table6.failurePulmonaryCDDiedNotTB }</td>
					<td>
						${table6.failurePulmonaryCDFailed }</td>
					<td>
						${table6.failurePulmonaryCDDefaulted}</td>

					<td>
						${table6.failurePulmonaryCDEligible }</td>
					<td>
						${table6.failurePulmonaryCDSLD}</td>
					<td>
						${table6.failurePulmonaryCDCanceled }</td>
					<td>
						${table6.failurePulmonaryCDDetected  - (table6.failurePulmonaryCDEligible + table6.failurePulmonaryCDSLD + table6.failurePulmonaryCDCanceled ) }</td>
				</tr>
				<tr>
					<td>
						3.3</td>
					<td>
						<spring:message code="dotsreports.tb08.eptb"/></td>
					<td>
						${table6.failureExtrapulmonaryDetected }</td>
					<td>
						${table6.failureExtrapulmonaryEligible }</td>
					<td>
						${table6.failureExtrapulmonaryCured }</td>
					<td>
						${table6.failureExtrapulmonaryCompleted }</td>
					<td>
						${table6.failureExtrapulmonaryDiedTB }</td>
					<td>
						${table6.failureExtrapulmonaryDiedNotTB }</td>
					<td>
						${table6.failureExtrapulmonaryFailed }</td>
					<td>
						${table6.failureExtrapulmonaryDefaulted}</td>

					<td>
						${table6.failureExtrapulmonaryEligible }</td>
					<td>
						${table6.failureExtrapulmonarySLD}</td>	
					<td>
						${table6.failureExtrapulmonaryCanceled }</td>
					<td>
						${table6.failureExtrapulmonaryDetected  - (table6.failureExtrapulmonaryEligible + table6.failureExtrapulmonarySLD + table6.failureExtrapulmonaryCanceled ) }</td>
				</tr>
				<tr>
					<td>
						&nbsp;</td>
					<td style="font: bold;">
						<b><spring:message code="dotsreports.tb08.total"/></b></td>
					<td>
						${ table6.failurePulmonaryBCDetected + table6.failurePulmonaryCDDetected + table6.failureExtrapulmonaryDetected }</td>
					<td>
						${ table6.failurePulmonaryBCEligible + table6.failurePulmonaryCDEligible + table6.failureExtrapulmonaryEligible }</td>
					<td>
						${ table6.failurePulmonaryBCCured + table6.failurePulmonaryCDCured + table6.failureExtrapulmonaryCured }</td></td>
					<td>
						${ table6.failurePulmonaryBCCompleted + table6.failurePulmonaryCDCompleted + table6.failureExtrapulmonaryCompleted }</td>
					<td>
						${ table6.failurePulmonaryBCDiedTB + table6.failurePulmonaryCDDiedTB + table6.failureExtrapulmonaryDiedTB }</td>
					<td>
						${ table6.failurePulmonaryBCDiedNotTB + table6.failurePulmonaryCDDiedNotTB + table6.failureExtrapulmonaryDiedNotTB }</td>
					<td>
						${ table6.failurePulmonaryBCFailed + table6.failurePulmonaryCDFailed + table6.failureExtrapulmonaryFailed }</td>
					<td>
						${ table6.failurePulmonaryBCDefaulted + table6.failurePulmonaryCDDefaulted + table6.failureExtrapulmonaryDefaulted }</td>
					<td>
						${ table6.failurePulmonaryBCEligible + table6.failurePulmonaryCDEligible + table6.failureExtrapulmonaryEligible }</td>
					<td>
						${ table6.failurePulmonaryBCSLD + table6.failurePulmonaryCDSLD + table6.failureExtrapulmonarySLD }</td>
					<td>
						${ table6.failurePulmonaryBCCanceled + table6.failurePulmonaryCDCanceled + table6.failureExtrapulmonaryCanceled }</td>
					<td>
						${  table6.failurePulmonaryBCDetected + table6.failurePulmonaryCDDetected + table6.failureExtrapulmonaryDetected - (table6.failurePulmonaryBCEligible + table6.failurePulmonaryCDEligible + table6.failureExtrapulmonaryEligible  + table6.failurePulmonaryBCSLD + table6.failurePulmonaryCDSLD + table6.failureExtrapulmonarySLD + table6.failurePulmonaryBCCanceled + table6.failurePulmonaryCDCanceled + table6.failureExtrapulmonaryCanceled) }</td>
					
					
				</tr>
				<tr>
					<td style="font: bold;">
						4</td>
					<td style="font: bold;">
						<spring:message code="dotsreports.tb08.afterDefault"/>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
				</tr>
				<tr>
					<td>
						4.1</td>
					<td>
						<spring:message code="dotsreports.tb08.pulmonaryBC"/> </td>
					<td>
						${table6.defaultPulmonaryBCDetected }</td>
					<td>
						${table6.defaultPulmonaryBCEligible }</td>
					<td>
						${table6.defaultPulmonaryBCCured }</td>
					<td>
						${table6.defaultPulmonaryBCCompleted }</td>
					<td>
						${table6.defaultPulmonaryBCDiedTB }</td>
					<td>
						${table6.defaultPulmonaryBCDiedNotTB }</td>
					<td>
						${table6.defaultPulmonaryBCFailed }</td>
					<td>
						${table6.defaultPulmonaryBCDefaulted}</td>

					<td>
						${table6.defaultPulmonaryBCEligible }</td>
					<td>
						${table6.defaultPulmonaryBCSLD}</td>
					<td>
						${table6.defaultPulmonaryBCCanceled }</td>
					
					<td>
						${table6.defaultPulmonaryBCDetected - (table6.defaultPulmonaryBCEligible + table6.defaultPulmonaryBCSLD + table6.defaultPulmonaryBCCanceled) }</td>
				</tr>
				<tr>
					<td>
						4.2</td>
					<td>
						<spring:message code="dotsreports.tb08.pulmonaryCD"/></td>
					<td>
						${table6.defaultPulmonaryCDDetected }</td>
					<td>
						${table6.defaultPulmonaryCDEligible }</td>
					<td>
						${table6.defaultPulmonaryCDCured }</td>
					<td>
						${table6.defaultPulmonaryCDCompleted }</td>
					<td>
						${table6.defaultPulmonaryCDDiedTB }</td>
					<td>
						${table6.defaultPulmonaryCDDiedNotTB }</td>
					<td>
						${table6.defaultPulmonaryCDFailed }</td>
					<td>
						${table6.defaultPulmonaryCDDefaulted}</td>

					<td>
						${table6.defaultPulmonaryCDEligible }</td>
					
					<td>
						${table6.defaultPulmonaryCDSLD}</td>
					<td>
						${table6.defaultPulmonaryCDCanceled }</td>
					
					<td>
						${table6.defaultPulmonaryCDDetected - (table6.defaultPulmonaryCDEligible + table6.defaultPulmonaryCDSLD + table6.defaultPulmonaryCDCanceled) }</td>
						
				</tr>
				<tr>
					<td>
						4.3</td>
					<td>
						<spring:message code="dotsreports.tb08.eptb"/></td>
					<td>
						${table6.defaultExtrapulmonaryDetected }</td>
					<td>
						${table6.defaultExtrapulmonaryEligible }</td>
					<td>
						${table6.defaultExtrapulmonaryCured }</td>
					<td>
						${table6.defaultExtrapulmonaryCompleted }</td>
					<td>
						${table6.defaultExtrapulmonaryDiedTB }</td>
					<td>
						${table6.defaultExtrapulmonaryDiedNotTB }</td>
					<td>
						${table6.defaultExtrapulmonaryFailed }</td>
					<td>
						${table6.defaultExtrapulmonaryDefaulted}</td>

					<td>
						${table6.defaultExtrapulmonaryEligible }</td>
					
					<td>
						${table6.defaultExtrapulmonarySLD}</td>	
					<td>
						${table6.defaultExtrapulmonaryCanceled }</td>
					<td>
						${table6.defaultExtrapulmonaryDetected - (table6.defaultExtrapulmonaryEligible + table6.defaultExtrapulmonarySLD + table6.defaultExtrapulmonaryCanceled) }</td>
					
				</tr>
				<tr>
					<td>
						&nbsp;</td>
					<td style="font: bold;">
						<b><spring:message code="dotsreports.tb08.total"/></b></td>
					<td>
						${ table6.defaultPulmonaryBCDetected + table6.defaultPulmonaryCDDetected + table6.defaultExtrapulmonaryDetected }</td>
					<td>
						${ table6.defaultPulmonaryBCEligible + table6.defaultPulmonaryCDEligible + table6.defaultExtrapulmonaryEligible }</td>
					<td>
						${ table6.defaultPulmonaryBCCured + table6.defaultPulmonaryCDCured + table6.defaultExtrapulmonaryCured }</td></td>
					<td>
						${ table6.defaultPulmonaryBCCompleted + table6.defaultPulmonaryCDCompleted + table6.defaultExtrapulmonaryCompleted }</td>
					<td>
						${ table6.defaultPulmonaryBCDiedTB + table6.defaultPulmonaryCDDiedTB + table6.defaultExtrapulmonaryDiedTB }</td>
					<td>
						${ table6.defaultPulmonaryBCDiedNotTB + table6.defaultPulmonaryCDDiedNotTB + table6.defaultExtrapulmonaryDiedNotTB }</td>
					<td>
						${ table6.defaultPulmonaryBCFailed + table6.defaultPulmonaryCDFailed + table6.defaultExtrapulmonaryFailed }</td>
					<td>
						${ table6.defaultPulmonaryBCDefaulted + table6.defaultPulmonaryCDDefaulted + table6.defaultExtrapulmonaryDefaulted }</td>
					<td>
						${ table6.defaultPulmonaryBCEligible + table6.defaultPulmonaryCDEligible + table6.defaultExtrapulmonaryEligible }</td>
					<td>
						${ table6.defaultPulmonaryBCSLD + table6.defaultPulmonaryCDSLD + table6.defaultExtrapulmonarySLD }</td>
					
					<td>
						${ table6.defaultPulmonaryBCCanceled + table6.defaultPulmonaryCDCanceled + table6.defaultExtrapulmonaryCanceled }</td>
					
					<td> 
						${table6.defaultPulmonaryBCDetected + table6.defaultPulmonaryCDDetected + table6.defaultExtrapulmonaryDetected - (table6.defaultPulmonaryBCEligible + table6.defaultPulmonaryCDEligible + table6.defaultExtrapulmonaryEligible  + table6.defaultPulmonaryBCSLD + table6.defaultPulmonaryCDSLD + table6.defaultExtrapulmonarySLD + table6.defaultPulmonaryBCCanceled + table6.defaultPulmonaryCDCanceled + table6.defaultExtrapulmonaryCanceled) } </td>
					
				</tr>
				<tr>
					<td style="font: bold;">
						5</td>
					<td style="font: bold;">
						<spring:message code="dotsreports.tb08.other"/></td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					
				</tr>
				<tr>
					<td>
						5.1</td>
					<td>
						<spring:message code="dotsreports.tb08.pulmonaryBC"/> </td>
					<td>
						${table6.otherPulmonaryBCDetected }</td>
					<td>
						${table6.otherPulmonaryBCEligible }</td>
					<td>
						${table6.otherPulmonaryBCCured }</td>
					<td>
						${table6.otherPulmonaryBCCompleted }</td>
					<td>
						${table6.otherPulmonaryBCDiedTB }</td>
					<td>
						${table6.otherPulmonaryBCDiedNotTB }</td>
					<td>
						${table6.otherPulmonaryBCFailed }</td>
					<td>
						${table6.otherPulmonaryBCDefaulted}</td>

					<td>
						${table6.otherPulmonaryBCEligible }</td>
					<td>
						${table6.otherPulmonaryBCSLD}</td>
					<td>
						${table6.otherPulmonaryBCCanceled }</td>
					
					<td>
						${table6.otherPulmonaryBCDetected - (table6.otherPulmonaryBCEligible + table6.otherPulmonaryBCSLD + table6.otherPulmonaryBCCanceled ) }</td>
				</tr>
				<tr>
					<td>
						5.2</td>
					<td>
						<spring:message code="dotsreports.tb08.pulmonaryCD"/></td>
					<td>
						${table6.otherPulmonaryCDDetected }</td>
					<td>
						${table6.otherPulmonaryCDEligible }</td>
					<td>
						${table6.otherPulmonaryCDCured }</td>
					<td>
						${table6.otherPulmonaryCDCompleted }</td>
					<td>
						${table6.otherPulmonaryCDDiedTB }</td>
					<td>
						${table6.otherPulmonaryCDDiedNotTB }</td>
					<td>
						${table6.otherPulmonaryCDFailed }</td>
					<td>
						${table6.otherPulmonaryCDDefaulted}</td>

					<td>
						${table6.otherPulmonaryCDEligible }</td>
					<td>
						${table6.otherPulmonaryCDSLD}</td>	
					<td>
						${table6.otherPulmonaryCDCanceled }</td>
					<td>
						${table6.otherPulmonaryCDDetected - (table6.otherPulmonaryCDEligible + table6.otherPulmonaryCDSLD + table6.otherPulmonaryCDCanceled ) }</td>
				
				</tr>
				<tr>
					<td>
						5.3</td>
					<td>
						<spring:message code="dotsreports.tb08.eptb"/></td>
					<td>
						${table6.otherExtrapulmonaryDetected }</td>
					<td>
						${table6.otherExtrapulmonaryEligible }</td>
					<td>
						${table6.otherExtrapulmonaryCured }</td>
					<td>
						${table6.otherExtrapulmonaryCompleted }</td>
					<td>
						${table6.otherExtrapulmonaryDiedTB }</td>
					<td>
						${table6.otherExtrapulmonaryDiedNotTB }</td>
					<td>
						${table6.otherExtrapulmonaryFailed }</td>
					<td>
						${table6.otherExtrapulmonaryDefaulted}</td>

					<td>
						${table6.otherExtrapulmonaryEligible }</td>
					<td>
						${table6.otherExtrapulmonarySLD}</td>	
					<td>
						${table6.otherExtrapulmonaryCanceled }</td>
					<td>
						${table6.otherExtrapulmonaryDetected - (table6.otherExtrapulmonaryEligible + table6.otherExtrapulmonarySLD + table6.otherExtrapulmonaryCanceled ) }</td>
				</tr>
				<tr>
					<td>
						&nbsp;</td>
					<td style="font: bold;">
						<b><spring:message code="dotsreports.tb08.total"/></b></td>
					<td>
						${ table6.otherPulmonaryBCDetected + table6.otherPulmonaryCDDetected + table6.otherExtrapulmonaryDetected }</td>
					<td>
						${ table6.otherPulmonaryBCEligible + table6.otherPulmonaryCDEligible + table6.otherExtrapulmonaryEligible }</td>
					<td>
						${ table6.otherPulmonaryBCCured + table6.otherPulmonaryCDCured + table6.otherExtrapulmonaryCured }</td></td>
					<td>
						${ table6.otherPulmonaryBCCompleted + table6.otherPulmonaryCDCompleted + table6.otherExtrapulmonaryCompleted }</td>
					<td>
						${ table6.otherPulmonaryBCDiedTB + table6.otherPulmonaryCDDiedTB + table6.otherExtrapulmonaryDiedTB }</td>
					<td>
						${ table6.otherPulmonaryBCDiedNotTB + table6.otherPulmonaryCDDiedNotTB + table6.otherExtrapulmonaryDiedNotTB }</td>
					<td>
						${ table6.otherPulmonaryBCFailed + table6.otherPulmonaryCDFailed + table6.otherExtrapulmonaryFailed }</td>
					<td>
						${ table6.otherPulmonaryBCDefaulted + table6.otherPulmonaryCDDefaulted + table6.otherExtrapulmonaryDefaulted }</td>
					<td>
						${ table6.otherPulmonaryBCEligible + table6.otherPulmonaryCDEligible + table6.otherExtrapulmonaryEligible }</td>
					
					<td>
						${ table6.otherPulmonaryBCSLD + table6.otherPulmonaryCDSLD + table6.otherExtrapulmonarySLD }</td>
					<td>
						${ table6.otherPulmonaryBCCanceled + table6.otherPulmonaryCDCanceled + table6.otherExtrapulmonaryCanceled }</td>
					<td> 
						${table6.otherPulmonaryBCDetected + table6.otherPulmonaryCDDetected + table6.otherExtrapulmonaryDetected - (table6.otherPulmonaryBCEligible + table6.otherPulmonaryCDEligible + table6.otherExtrapulmonaryEligible  + table6.otherPulmonaryBCSLD + table6.otherPulmonaryCDSLD + table6.otherExtrapulmonarySLD + table6.otherPulmonaryBCCanceled + table6.otherPulmonaryCDCanceled + table6.otherExtrapulmonaryCanceled) } </td>
					
					
				</tr>
				
				<tr>
				     <td>&nbsp;
						</td>
					<td style="font: bold;">
						<spring:message code="dotsreports.tb08.totalRetreatments"/></td>
					
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					<td>
						&nbsp;</td>
					
				</tr>
				<tr>
					<td>
						&nbsp;</td>
					<td>
						<spring:message code="dotsreports.tb08.pulmonaryBC"/> </td>
					<td>
						${table6.failurePulmonaryBCDetected + table6.defaultPulmonaryBCDetected + table6.otherPulmonaryBCDetected }</td>
					<td>
						${table6.failurePulmonaryBCEligible + table6.defaultPulmonaryBCEligible + table6.otherPulmonaryBCEligible }</td>
					<td>
						${table6.failurePulmonaryBCCured + table6.defaultPulmonaryBCCured + table6.otherPulmonaryBCCured }</td>
					<td>
						${table6.failurePulmonaryBCCompleted + table6.defaultPulmonaryBCCompleted + table6.otherPulmonaryBCCompleted }</td>
					<td>
						${table6.failurePulmonaryBCDiedTB + table6.defaultPulmonaryBCDiedTB + table6.otherPulmonaryBCDiedTB }</td>
					<td>
						${table6.failurePulmonaryBCDiedNotTB + table6.defaultPulmonaryBCDiedNotTB + table6.otherPulmonaryBCDiedNotTB }</td>
					<td>
						${table6.failurePulmonaryBCFailed + table6.defaultPulmonaryBCFailed + table6.otherPulmonaryBCFailed }</td>
					<td>
						${table6.failurePulmonaryBCDefaulted + table6.defaultPulmonaryBCDefaulted + table6.otherPulmonaryBCDefaulted }</td>
					
					<td>
						${table6.failurePulmonaryBCEligible + table6.defaultPulmonaryBCEligible + table6.otherPulmonaryBCEligible }</td>
					
					<td>
						${table6.failurePulmonaryBCSLD + table6.defaultPulmonaryBCSLD + table6.otherPulmonaryBCSLD }</td>
					<td>
						${table6.failurePulmonaryBCCanceled + table6.defaultPulmonaryBCCanceled + table6.otherPulmonaryBCCanceled }</td>
					<td>
						${table6.failurePulmonaryBCDetected + table6.defaultPulmonaryBCDetected + table6.otherPulmonaryBCDetected - (table6.failurePulmonaryBCEligible + table6.defaultPulmonaryBCEligible + table6.otherPulmonaryBCEligible + table6.failurePulmonaryBCSLD + table6.defaultPulmonaryBCSLD + table6.otherPulmonaryBCSLD + table6.failurePulmonaryBCCanceled + table6.defaultPulmonaryBCCanceled + table6.otherPulmonaryBCCanceled ) }</td>
					
				</tr>
				<tr>
					<td>
						&nbsp;</td>
					<td>
						<spring:message code="dotsreports.tb08.pulmonaryCD"/> </td>
					<td>
						${table6.failurePulmonaryCDDetected + table6.defaultPulmonaryCDDetected + table6.otherPulmonaryCDDetected }</td>
					<td>
						${table6.failurePulmonaryCDEligible + table6.defaultPulmonaryCDEligible + table6.otherPulmonaryCDEligible }</td>
					<td>
						${table6.failurePulmonaryCDCured + table6.defaultPulmonaryCDCured + table6.otherPulmonaryCDCured }</td>
					<td>
						${table6.failurePulmonaryCDCompleted + table6.defaultPulmonaryCDCompleted + table6.otherPulmonaryCDCompleted }</td>
					<td>
						${table6.failurePulmonaryCDDiedTB + table6.defaultPulmonaryCDDiedTB + table6.otherPulmonaryCDDiedTB }</td>
					<td>
						${table6.failurePulmonaryCDDiedNotTB + table6.defaultPulmonaryCDDiedNotTB + table6.otherPulmonaryCDDiedNotTB }</td>
					<td>
						${table6.failurePulmonaryCDFailed + table6.defaultPulmonaryCDFailed + table6.otherPulmonaryCDFailed }</td>
					<td>
						${table6.failurePulmonaryCDDefaulted + table6.defaultPulmonaryCDDefaulted + table6.otherPulmonaryCDDefaulted }</td>
					
					<td>
						${table6.failurePulmonaryCDEligible + table6.defaultPulmonaryCDEligible + table6.otherPulmonaryCDEligible }</td>
					<td>
						${table6.failurePulmonaryCDSLD + table6.defaultPulmonaryCDSLD + table6.otherPulmonaryCDSLD }</td>
					<td>
						${table6.failurePulmonaryCDCanceled + table6.defaultPulmonaryCDCanceled + table6.otherPulmonaryCDCanceled }</td>
					<td>
						${table6.failurePulmonaryCDDetected + table6.defaultPulmonaryCDDetected + table6.otherPulmonaryCDDetected - (table6.failurePulmonaryCDEligible + table6.defaultPulmonaryCDEligible + table6.otherPulmonaryCDEligible + table6.failurePulmonaryCDSLD + table6.defaultPulmonaryCDSLD + table6.otherPulmonaryCDSLD + table6.failurePulmonaryCDCanceled + table6.defaultPulmonaryCDCanceled + table6.otherPulmonaryCDCanceled ) }</td>
					
				</tr>
				<tr>
					<td>
						&nbsp;</td>
					<td>
						<spring:message code="dotsreports.tb08.eptb"/></td>
					<td>
						${table6.failureExtrapulmonaryDetected + table6.defaultExtrapulmonaryDetected + table6.otherExtrapulmonaryDetected }</td>
					<td>
						${table6.failureExtrapulmonaryEligible + table6.defaultExtrapulmonaryEligible + table6.otherExtrapulmonaryEligible }</td>
					<td>
						${table6.failureExtrapulmonaryCured + table6.defaultExtrapulmonaryCured + table6.otherExtrapulmonaryCured }</td>
					<td>
						${table6.failureExtrapulmonaryCompleted + table6.defaultExtrapulmonaryCompleted + table6.otherExtrapulmonaryCompleted }</td>
					<td>
						${table6.failureExtrapulmonaryDiedTB + table6.defaultExtrapulmonaryDiedTB + table6.otherExtrapulmonaryDiedTB }</td>
					<td>
						${table6.failureExtrapulmonaryDiedNotTB + table6.defaultExtrapulmonaryDiedNotTB + table6.otherExtrapulmonaryDiedNotTB }</td>
					<td>
						${table6.failureExtrapulmonaryFailed + table6.defaultExtrapulmonaryFailed + table6.otherExtrapulmonaryFailed }</td>
					<td>
						${table6.failureExtrapulmonaryDefaulted + table6.defaultExtrapulmonaryDefaulted + table6.otherExtrapulmonaryDefaulted }</td>
					
					<td>
						${table6.failureExtrapulmonaryEligible + table6.defaultExtrapulmonaryEligible + table6.otherExtrapulmonaryEligible }</td>
					<td>
						${table6.failureExtrapulmonarySLD + table6.defaultExtrapulmonarySLD + table6.otherExtrapulmonarySLD }</td>
					
					<td>
						${table6.failureExtrapulmonaryCanceled + table6.defaultExtrapulmonaryCanceled + table6.otherExtrapulmonaryCanceled }</td>
					<td>
						${table6.failurePulmonaryCDDetected + table6.defaultPulmonaryCDDetected + table6.otherPulmonaryCDDetected - (table6.failurePulmonaryCDEligible + table6.defaultPulmonaryCDEligible + table6.otherPulmonaryCDEligible + table6.failurePulmonaryCDSLD + table6.defaultPulmonaryCDSLD + table6.otherPulmonaryCDSLD + table6.failurePulmonaryCDCanceled + table6.defaultPulmonaryCDCanceled + table6.otherPulmonaryCDCanceled ) }</td>
					
				</tr>
				<tr>
					<td>&nbsp;
						</td>
					<td style="font: bold;">
						<spring:message code="dotsreports.tb08.retreatmentTotalBreakdown" /></td>
					
					<td>
						${table6.failureAllDetected + table6.defaultAllDetected + table6.otherAllDetected }</td>
					<td>
						${table6.failureAllEligible + table6.defaultAllEligible + table6.otherAllEligible }</td>
					<td>
						${table6.failureAllCured + table6.defaultAllCured + table6.otherAllCured }</td>
					<td>
						${table6.failureAllCompleted + table6.defaultAllCompleted + table6.otherAllCompleted }</td>
					<td>
						${table6.failureAllDiedTB + table6.defaultAllDiedTB + table6.otherAllDiedTB }</td>
					<td>
						${table6.failureAllDiedNotTB + table6.defaultAllDiedNotTB + table6.otherAllDiedNotTB }</td>
					<td>
						${table6.failureAllFailed + table6.defaultAllFailed + table6.otherAllFailed }</td>
					<td>
						${table6.failureAllDefaulted + table6.defaultAllDefaulted + table6.otherAllDefaulted }</td>
					<td>
						${table6.failureAllEligible + table6.defaultAllEligible + table6.otherAllEligible }</td>
					<td>
						${table6.failureAllSLD + table6.defaultAllSLD + table6.otherAllSLD }</td>
					<td>
						${table6.failureAllCanceled + table6.defaultAllCanceled + table6.otherAllCanceled }</td>
					
					<td>
						${table6.failureAllDetected + table6.defaultAllDetected + table6.otherAllDetected - (table6.failureAllEligible + table6.defaultAllEligible + table6.otherAllEligible + table6.failureAllSLD + table6.defaultAllSLD + table6.otherAllSLD + table6.failureAllCanceled + table6.defaultAllCanceled + table6.otherAllCanceled ) }</td>
					
				</tr>
				<%-- <tr>
					<td>&nbsp;
						</td>
					<td style="font: bold;">
						Total (1+2+3+4+5) </td>
					
					<td>
						${table6.allDetected }</td>
					<td>
						${table6.allEligible }</td>
					<td>
						${table6.allCured }</td>
					<td>
						${table6.allCompleted }</td>
					<td>
						${table6.allDiedTB }</td>
					<td>
						${table6.allDiedNotTB }</td>
					<td>
						${table6.allFailed }</td>
					<td>
						${table6.allDefaulted }</td>
					<td>
						${table6.allEligible }</td>
					<td>
						${table6.allSLD }</td>
					<td>
						${table6.allCanceled }</td>
					
					<td>
						${table6.allDetected - (table6.allEligible + table6.allSLD + table6.allCanceled) }</td>
					
				</tr> --%>
				
			</tbody>
		</table>
		</div>
		
		<input type="button" onclick="tableToExcel('form8', 'Form8')" value="<spring:message code='dotsreports.exportToExcelBtn' />" />
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
			
