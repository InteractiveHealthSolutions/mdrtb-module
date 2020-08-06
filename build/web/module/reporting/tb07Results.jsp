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
	<title>TB 07</title>
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

		    mywindow.document.write('<html><head><title><spring:message code="dotsreports.tb07.title" text="TB07"/></title>');
		    mywindow.document.write('</head><body >');
		   // mywindow.document.write('<h1><spring:message code="mdrtb.pv.aeForm" text="AE"/></h1>');
		    mywindow.document.write(document.getElementById("tb07").innerHTML);
		    
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
				var tableData = (document.getElementById("tb07")).innerHTML.toString();
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
						savePdf("closeReport.form", "TB-07", "tb07Results");
					}
				});
				/* $("#tableToPdf").click(function(){
					savePdf("exportReport.form", "TB 07", "tb07Results");
				}); */
			});
		</script>
		
		<div id="tb07" style="font-size:smaller; width:980px;">	
<style type="text/css">th {vertical-align:top; text-align:left;}
			th, td {font-size:smaller; border: 1px solid #000000}
			border {border: 1px solid #000000}
</style>

<table border="0" width="100%">
	<tbody>
		<tr>
			<td align="center" style="font-size:14px; font-weight:bold;border:0px" width="90%"><spring:message code="dotsreports.tb07.title"/></td>
			<td align="right" style="font-size:14px; font-weight:bold;border:0px" valign="top" width="10%">TB 07</td>
		</tr>
	</tbody>
</table>
&nbsp;
<table width="100%" border="1">
<tr>
<td>
<spring:message code="dotsreports.tb07.nameOfFacility"/> <u>&nbsp; ${fName} &nbsp;</u></br>
<spring:message code="dotsreports.tb07.regionCityDistrict"/> <u> ${oName}/${dName} </u></br>
<spring:message code="dotsreports.tb07.tbCoordinatorName"/> ___________________ </br>
<spring:message code="dotsreports.tb07.signature"/>_________________
</td>

<td>
<spring:message code="dotsreports.tb07.tbCasesDetectedDuringQuarterYear" arguments="${quarter},${year}" /></br>
<spring:message code="dotsreports.tb07.dateOfReport"/> ${reportDate }
</td>
</tr>


</table>



<h5><spring:message code="dotsreports.tb07.table1.title"/></h5>
	<table border="1" cellpadding="1" cellspacing="1" dir="ltr"
			style="width: 980px;">
			<tbody>
				<tr>
					<td colspan="2" rowspan="1"><spring:message code="dotsreports.tb07.type"/></td>
					<td colspan="2" rowspan="1" style="text-align: center;">0-4</td>
					<td colspan="2" rowspan="1" style="text-align: center;">5-14</td>
					<td colspan="2" rowspan="1" style="text-align: center;">15-17</td>
					<td colspan="2" rowspan="1" style="text-align: center;">18-19</td>
					<td colspan="2" rowspan="1" style="text-align: center;">20-24</td>
					<td colspan="2" rowspan="1" style="text-align: center;">25-34</td>
					<td colspan="2" rowspan="1" style="text-align: center;">35-44</td>
					<td colspan="2" rowspan="1" style="text-align: center;">45-54</td>
					<td colspan="2" rowspan="1" style="text-align: center;">55-64</td>
					<td colspan="2" rowspan="1" style="text-align: center;">&gt;65</td>
					<td colspan="3" rowspan="1" style="text-align: center;"><spring:message code="dotsreports.tb07.total"/></td>
				</tr>
				<tr>

					<td style="text-align: center;">&nbsp;</td>
					<td style="text-align: center;">&nbsp;</td>
					<td style="text-align: center;width: 3%"><spring:message code="dotsreports.tb07.maleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.femaleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.maleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.femaleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.maleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.femaleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.maleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.femaleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.maleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.femaleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.maleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.femaleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.maleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.femaleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.maleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.femaleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.maleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.femaleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.maleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.femaleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.maleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.femaleShort"/></td>
					<td style="text-align: center;"><spring:message code="dotsreports.tb07.total"/></td>
					
				</tr>
				<tr>
					<td rowspan="6">
						<spring:message code="dotsreports.tb07.new" />
					</td>
					<td><spring:message code="dotsreports.tb07.pulmonaryBC" /></td>
					
					<td>${table1.newMalePulmonaryBC04}</td>
					<td>${table1.newFemalePulmonaryBC04}</td>
					
					<td>${table1.newMalePulmonaryBC0514}</td>
					<td>${table1.newFemalePulmonaryBC0514}</td>
					
					<td>${table1.newMalePulmonaryBC1517}</td>
					<td>${table1.newFemalePulmonaryBC1517}</td>
					
					
					<td>${table1.newMalePulmonaryBC1819}</td>
					<td>${table1.newFemalePulmonaryBC1819}</td>
					
					
					<td>${table1.newMalePulmonaryBC2024}</td>
					<td>${table1.newFemalePulmonaryBC2024}</td>
					
					
					<td>${table1.newMalePulmonaryBC2534}</td>
					<td>${table1.newFemalePulmonaryBC2534}</td>
					
					
					<td>${table1.newMalePulmonaryBC3544}</td>
					<td>${table1.newFemalePulmonaryBC3544}</td>
					
					
					<td>${table1.newMalePulmonaryBC4554}</td>
					<td>${table1.newFemalePulmonaryBC4554}</td>
					
					
					<td>${table1.newMalePulmonaryBC5564}</td>
					<td>${table1.newFemalePulmonaryBC5564}</td>
					
					<td>${table1.newMalePulmonaryBC65}</td>
					<td>${table1.newFemalePulmonaryBC65}</td>
					
					<td>${table1.newMalePulmonaryBC}</td>
					<td>${table1.newFemalePulmonaryBC}</td>
					<td>${table1.newPulmonaryBC}</td>                       
				</tr>
				<tr bgcolor="yellow">
					<td><spring:message code="dotsreports.tb07.ofTheseHIV" /> </td>
					
					<td>${table1.newMalePulmonaryBCHIV04}</td>
					<td>${table1.newFemalePulmonaryBCHIV04}</td>
					
					<td>${table1.newMalePulmonaryBCHIV0514}</td>
					<td>${table1.newFemalePulmonaryBCHIV0514}</td>
					
					<td>${table1.newMalePulmonaryBCHIV1517}</td>
					<td>${table1.newFemalePulmonaryBCHIV1517}</td>
					
					
					<td>${table1.newMalePulmonaryBCHIV1819}</td>
					<td>${table1.newFemalePulmonaryBCHIV1819}</td>
					
					
					<td>${table1.newMalePulmonaryBCHIV2024}</td>
					<td>${table1.newFemalePulmonaryBCHIV2024}</td>
					
					
					<td>${table1.newMalePulmonaryBCHIV2534}</td>
					<td>${table1.newFemalePulmonaryBCHIV2534}</td>
					
					
					<td>${table1.newMalePulmonaryBCHIV3544}</td>
					<td>${table1.newFemalePulmonaryBCHIV3544}</td>
					
					
					<td>${table1.newMalePulmonaryBCHIV4554}</td>
					<td>${table1.newFemalePulmonaryBCHIV4554}</td>
					
					
					<td>${table1.newMalePulmonaryBCHIV5564}</td>
					<td>${table1.newFemalePulmonaryBCHIV5564}</td>
					
					<td>${table1.newMalePulmonaryBCHIV65}</td>
					<td>${table1.newFemalePulmonaryBCHIV65}</td>
					
					<td>${table1.newMalePulmonaryBCHIV}</td>
					<td>${table1.newFemalePulmonaryBCHIV}</td>
					<td>${table1.newPulmonaryBCHIV}</td>                
					                   
				</tr>
				<tr>
					<td><spring:message code="dotsreports.tb07.pulmonaryCD" /> </td>
					<td>${table1.newMalePulmonaryCD04}</td>
					<td>${table1.newFemalePulmonaryCD04}</td>
					
					<td>${table1.newMalePulmonaryCD0514}</td>
					<td>${table1.newFemalePulmonaryCD0514}</td>
					
					<td>${table1.newMalePulmonaryCD1517}</td>
					<td>${table1.newFemalePulmonaryCD1517}</td>
					
					
					<td>${table1.newMalePulmonaryCD1819}</td>
					<td>${table1.newFemalePulmonaryCD1819}</td>
					
					
					<td>${table1.newMalePulmonaryCD2024}</td>
					<td>${table1.newFemalePulmonaryCD2024}</td>
					
					
					<td>${table1.newMalePulmonaryCD2534}</td>
					<td>${table1.newFemalePulmonaryCD2534}</td>
					
					
					<td>${table1.newMalePulmonaryCD3544}</td>
					<td>${table1.newFemalePulmonaryCD3544}</td>
					
					
					<td>${table1.newMalePulmonaryCD4554}</td>
					<td>${table1.newFemalePulmonaryCD4554}</td>
					
					
					<td>${table1.newMalePulmonaryCD5564}</td>
					<td>${table1.newFemalePulmonaryCD5564}</td>
					
					<td>${table1.newMalePulmonaryCD65}</td>
					<td>${table1.newFemalePulmonaryCD65}</td>
					
					<td>${table1.newMalePulmonaryCD}</td>
					<td>${table1.newFemalePulmonaryCD}</td>
					<td>${table1.newPulmonaryCD}</td>                         
				</tr>
				<tr bgcolor="yellow">
					<td><spring:message code="dotsreports.tb07.ofTheseHIV" /></td>
					<td>${table1.newMalePulmonaryCDHIV04}</td>
					<td>${table1.newFemalePulmonaryCDHIV04}</td>
					
					<td>${table1.newMalePulmonaryCDHIV0514}</td>
					<td>${table1.newFemalePulmonaryCDHIV0514}</td>
					
					<td>${table1.newMalePulmonaryCDHIV1517}</td>
					<td>${table1.newFemalePulmonaryCDHIV1517}</td>
					
					
					<td>${table1.newMalePulmonaryCDHIV1819}</td>
					<td>${table1.newFemalePulmonaryCDHIV1819}</td>
					
					
					<td>${table1.newMalePulmonaryCDHIV2024}</td>
					<td>${table1.newFemalePulmonaryCDHIV2024}</td>
					
					
					<td>${table1.newMalePulmonaryCDHIV2534}</td>
					<td>${table1.newFemalePulmonaryCDHIV2534}</td>
					
					
					<td>${table1.newMalePulmonaryCDHIV3544}</td>
					<td>${table1.newFemalePulmonaryCDHIV3544}</td>
					
					
					<td>${table1.newMalePulmonaryCDHIV4554}</td>
					<td>${table1.newFemalePulmonaryCDHIV4554}</td>
					
					
					<td>${table1.newMalePulmonaryCDHIV5564}</td>
					<td>${table1.newFemalePulmonaryCDHIV5564}</td>
					
					<td>${table1.newMalePulmonaryCDHIV65}</td>
					<td>${table1.newFemalePulmonaryCDHIV65}</td>
					
					<td>${table1.newMalePulmonaryCDHIV}</td>
					<td>${table1.newFemalePulmonaryCDHIV}</td>
					<td>${table1.newPulmonaryCDHIV}</td>                      
				</tr>
				<tr>
					<td><spring:message code="dotsreports.tb07.eptb" /></td>
					<td>${table1.newMaleExtrapulmonary04}</td>
					<td>${table1.newFemaleExtrapulmonary04}</td>
					
					<td>${table1.newMaleExtrapulmonary0514}</td>
					<td>${table1.newFemaleExtrapulmonary0514}</td>
					
					<td>${table1.newMaleExtrapulmonary1517}</td>
					<td>${table1.newFemaleExtrapulmonary1517}</td>
					
					
					<td>${table1.newMaleExtrapulmonary1819}</td>
					<td>${table1.newFemaleExtrapulmonary1819}</td>
					
					
					<td>${table1.newMaleExtrapulmonary2024}</td>
					<td>${table1.newFemaleExtrapulmonary2024}</td>
					
					
					<td>${table1.newMaleExtrapulmonary2534}</td>
					<td>${table1.newFemaleExtrapulmonary2534}</td>
					
					
					<td>${table1.newMaleExtrapulmonary3544}</td>
					<td>${table1.newFemaleExtrapulmonary3544}</td>
					
					
					<td>${table1.newMaleExtrapulmonary4554}</td>
					<td>${table1.newFemaleExtrapulmonary4554}</td>
					
					
					<td>${table1.newMaleExtrapulmonary5564}</td>
					<td>${table1.newFemaleExtrapulmonary5564}</td>
					
					<td>${table1.newMaleExtrapulmonary65}</td>
					<td>${table1.newFemaleExtrapulmonary65}</td>
					
					<td>${table1.newMaleExtrapulmonary}</td>
					<td>${table1.newFemaleExtrapulmonary}</td>
					<td>${table1.newExtrapulmonary}</td>                   
				</tr>
				<tr bgcolor="yellow">
					<td><spring:message code="dotsreports.tb07.ofTheseHIV" /></td>
					<td>${table1.newMaleExtrapulmonaryHIV04}</td>
					<td>${table1.newFemaleExtrapulmonaryHIV04}</td>
					
					<td>${table1.newMaleExtrapulmonaryHIV0514}</td>
					<td>${table1.newFemaleExtrapulmonaryHIV0514}</td>
					
					<td>${table1.newMaleExtrapulmonaryHIV1517}</td>
					<td>${table1.newFemaleExtrapulmonaryHIV1517}</td>
					
					
					<td>${table1.newMaleExtrapulmonaryHIV1819}</td>
					<td>${table1.newFemaleExtrapulmonaryHIV1819}</td>
					
					
					<td>${table1.newMaleExtrapulmonaryHIV2024}</td>
					<td>${table1.newFemaleExtrapulmonaryHIV2024}</td>
					
					
					<td>${table1.newMaleExtrapulmonaryHIV2534}</td>
					<td>${table1.newFemaleExtrapulmonaryHIV2534}</td>
					
					
					<td>${table1.newMaleExtrapulmonaryHIV3544}</td>
					<td>${table1.newFemaleExtrapulmonaryHIV3544}</td>
					
					
					<td>${table1.newMaleExtrapulmonaryHIV4554}</td>
					<td>${table1.newFemaleExtrapulmonaryHIV4554}</td>
					
					
					<td>${table1.newMaleExtrapulmonaryHIV5564}</td>
					<td>${table1.newFemaleExtrapulmonaryHIV5564}</td>
					
					<td>${table1.newMaleExtrapulmonaryHIV65}</td>
					<td>${table1.newFemaleExtrapulmonaryHIV65}</td>
					
					<td>${table1.newMaleExtrapulmonaryHIV}</td>
					<td>${table1.newFemaleExtrapulmonaryHIV}</td>
					<td>${table1.newExtrapulmonaryHIV}</td>  
				</tr>
				<tr>
					<td colspan="2" align="left"><spring:message code="dotsreports.tb07.total"/></td>

					<td>${table1.newMale04}</td>
					<td>${table1.newFemale04}</td>
					
					<td>${table1.newMale0514}</td>
					<td>${table1.newFemale0514}</td>
					
					<td>${table1.newMale1517}</td>
					<td>${table1.newFemale1517}</td>
					
					
					<td>${table1.newMale1819}</td>
					<td>${table1.newFemale1819}</td>
					
					
					<td>${table1.newMale2024}</td>
					<td>${table1.newFemale2024}</td>
					
					
					<td>${table1.newMale2534}</td>
					<td>${table1.newFemale2534}</td>
					
					
					<td>${table1.newMale3544}</td>
					<td>${table1.newFemale3544}</td>
					
					
					<td>${table1.newMale4554}</td>
					<td>${table1.newFemale4554}</td>
					
					
					<td>${table1.newMale5564}</td>
					<td>${table1.newFemale5564}</td>
					
					<td>${table1.newMale65}</td>
					<td>${table1.newFemale65}</td>
					
					<td>${table1.newMale}</td>
					<td>${table1.newFemale}</td>  
					<td>${table1.newAll}</td> 
				</tr>
				<tr bgcolor="yellow">
					<td colspan="2"><spring:message code="dotsreports.tb07.ofTheseHIV" /></td>

					<td>${table1.newMaleHIV04}</td>
					<td>${table1.newFemaleHIV04}</td>
					
					<td>${table1.newMaleHIV0514}</td>
					<td>${table1.newFemaleHIV0514}</td>
					
					<td>${table1.newMaleHIV1517}</td>
					<td>${table1.newFemaleHIV1517}</td>
					
					
					<td>${table1.newMaleHIV1819}</td>
					<td>${table1.newFemaleHIV1819}</td>
					
					
					<td>${table1.newMaleHIV2024}</td>
					<td>${table1.newFemaleHIV2024}</td>
					
					
					<td>${table1.newMaleHIV2534}</td>
					<td>${table1.newFemaleHIV2534}</td>
					
					
					<td>${table1.newMaleHIV3544}</td>
					<td>${table1.newFemaleHIV3544}</td>
					
					
					<td>${table1.newMaleHIV4554}</td>
					<td>${table1.newFemaleHIV4554}</td>
					
					
					<td>${table1.newMaleHIV5564}</td>
					<td>${table1.newFemaleHIV5564}</td>
					
					<td>${table1.newMaleHIV65}</td>
					<td>${table1.newFemaleHIV65}</td>
					
					<td>${table1.newMaleHIV}</td>
					<td>${table1.newFemaleHIV}</td>  
					<td>${table1.newAllHIV}</td> 
				</tr>

				<tr>
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
					<td rowspan="6">
						<spring:message code="dotsreports.tb07.relapses" />
					</td>
						<td><spring:message code="dotsreports.tb07.pulmonaryBC" /></td>
					<td>${table1.relapseMalePulmonaryBC04}</td>
					<td>${table1.relapseFemalePulmonaryBC04}</td>
					
					<td>${table1.relapseMalePulmonaryBC0514}</td>
					<td>${table1.relapseFemalePulmonaryBC0514}</td>
					
					<td>${table1.relapseMalePulmonaryBC1517}</td>
					<td>${table1.relapseFemalePulmonaryBC1517}</td>
					
					
					<td>${table1.relapseMalePulmonaryBC1819}</td>
					<td>${table1.relapseFemalePulmonaryBC1819}</td>
					
					
					<td>${table1.relapseMalePulmonaryBC2024}</td>
					<td>${table1.relapseFemalePulmonaryBC2024}</td>
					
					
					<td>${table1.relapseMalePulmonaryBC2534}</td>
					<td>${table1.relapseFemalePulmonaryBC2534}</td>
					
					
					<td>${table1.relapseMalePulmonaryBC3544}</td>
					<td>${table1.relapseFemalePulmonaryBC3544}</td>
					
					
					<td>${table1.relapseMalePulmonaryBC4554}</td>
					<td>${table1.relapseFemalePulmonaryBC4554}</td>
					
					
					<td>${table1.relapseMalePulmonaryBC5564}</td>
					<td>${table1.relapseFemalePulmonaryBC5564}</td>
					
					<td>${table1.relapseMalePulmonaryBC65}</td>
					<td>${table1.relapseFemalePulmonaryBC65}</td>
					
					<td>${table1.relapseMalePulmonaryBC}</td>
					<td>${table1.relapseFemalePulmonaryBC}</td>
					<td>${table1.relapsePulmonaryBC}</td>                       
				</tr>
				<tr bgcolor="yellow">
					<td><spring:message code="dotsreports.tb07.ofTheseHIV" /> </td>
					
					<td>${table1.relapseMalePulmonaryBCHIV04}</td>
					<td>${table1.relapseFemalePulmonaryBCHIV04}</td>
					
					<td>${table1.relapseMalePulmonaryBCHIV0514}</td>
					<td>${table1.relapseFemalePulmonaryBCHIV0514}</td>
					
					<td>${table1.relapseMalePulmonaryBCHIV1517}</td>
					<td>${table1.relapseFemalePulmonaryBCHIV1517}</td>
					
					
					<td>${table1.relapseMalePulmonaryBCHIV1819}</td>
					<td>${table1.relapseFemalePulmonaryBCHIV1819}</td>
					
					
					<td>${table1.relapseMalePulmonaryBCHIV2024}</td>
					<td>${table1.relapseFemalePulmonaryBCHIV2024}</td>
					
					
					<td>${table1.relapseMalePulmonaryBCHIV2534}</td>
					<td>${table1.relapseFemalePulmonaryBCHIV2534}</td>
					
					
					<td>${table1.relapseMalePulmonaryBCHIV3544}</td>
					<td>${table1.relapseFemalePulmonaryBCHIV3544}</td>
					
					
					<td>${table1.relapseMalePulmonaryBCHIV4554}</td>
					<td>${table1.relapseFemalePulmonaryBCHIV4554}</td>
					
					
					<td>${table1.relapseMalePulmonaryBCHIV5564}</td>
					<td>${table1.relapseFemalePulmonaryBCHIV5564}</td>
					
					<td>${table1.relapseMalePulmonaryBCHIV65}</td>
					<td>${table1.relapseFemalePulmonaryBCHIV65}</td>
					
					<td>${table1.relapseMalePulmonaryBCHIV}</td>
					<td>${table1.relapseFemalePulmonaryBCHIV}</td>
					<td>${table1.relapsePulmonaryBCHIV}</td>          
					                   
				</tr>
				<tr>
					<td><spring:message code="dotsreports.tb07.pulmonaryCD" /> </td>
					<td>${table1.relapseMalePulmonaryCD04}</td>
					<td>${table1.relapseFemalePulmonaryCD04}</td>
					
					<td>${table1.relapseMalePulmonaryCD0514}</td>
					<td>${table1.relapseFemalePulmonaryCD0514}</td>
					
					<td>${table1.relapseMalePulmonaryCD1517}</td>
					<td>${table1.relapseFemalePulmonaryCD1517}</td>
					
					
					<td>${table1.relapseMalePulmonaryCD1819}</td>
					<td>${table1.relapseFemalePulmonaryCD1819}</td>
					
					
					<td>${table1.relapseMalePulmonaryCD2024}</td>
					<td>${table1.relapseFemalePulmonaryCD2024}</td>
					
					
					<td>${table1.relapseMalePulmonaryCD2534}</td>
					<td>${table1.relapseFemalePulmonaryCD2534}</td>
					
					
					<td>${table1.relapseMalePulmonaryCD3544}</td>
					<td>${table1.relapseFemalePulmonaryCD3544}</td>
					
					
					<td>${table1.relapseMalePulmonaryCD4554}</td>
					<td>${table1.relapseFemalePulmonaryCD4554}</td>
					
					
					<td>${table1.relapseMalePulmonaryCD5564}</td>
					<td>${table1.relapseFemalePulmonaryCD5564}</td>
					
					<td>${table1.relapseMalePulmonaryCD65}</td>
					<td>${table1.relapseFemalePulmonaryCD65}</td>
					
					<td>${table1.relapseMalePulmonaryCD}</td>
					<td>${table1.relapseFemalePulmonaryCD}</td>
					<td>${table1.relapsePulmonaryCD}</td>                         
				</tr>
				<tr bgcolor="yellow">
					<td><spring:message code="dotsreports.tb07.ofTheseHIV" /></td>
					<td>${table1.relapseMalePulmonaryCDHIV04}</td>
					<td>${table1.relapseFemalePulmonaryCDHIV04}</td>
					
					<td>${table1.relapseMalePulmonaryCDHIV0514}</td>
					<td>${table1.relapseFemalePulmonaryCDHIV0514}</td>
					
					<td>${table1.relapseMalePulmonaryCDHIV1517}</td>
					<td>${table1.relapseFemalePulmonaryCDHIV1517}</td>
					
					
					<td>${table1.relapseMalePulmonaryCDHIV1819}</td>
					<td>${table1.relapseFemalePulmonaryCDHIV1819}</td>
					
					
					<td>${table1.relapseMalePulmonaryCDHIV2024}</td>
					<td>${table1.relapseFemalePulmonaryCDHIV2024}</td>
					
					
					<td>${table1.relapseMalePulmonaryCDHIV2534}</td>
					<td>${table1.relapseFemalePulmonaryCDHIV2534}</td>
					
					
					<td>${table1.relapseMalePulmonaryCDHIV3544}</td>
					<td>${table1.relapseFemalePulmonaryCDHIV3544}</td>
					
					
					<td>${table1.relapseMalePulmonaryCDHIV4554}</td>
					<td>${table1.relapseFemalePulmonaryCDHIV4554}</td>
					
					
					<td>${table1.relapseMalePulmonaryCDHIV5564}</td>
					<td>${table1.relapseFemalePulmonaryCDHIV5564}</td>
					
					<td>${table1.relapseMalePulmonaryCDHIV65}</td>
					<td>${table1.relapseFemalePulmonaryCDHIV65}</td>
					
					<td>${table1.relapseMalePulmonaryCDHIV}</td>
					<td>${table1.relapseFemalePulmonaryCDHIV}</td>
					<td>${table1.relapsePulmonaryCDHIV}</td>  
					                   
				</tr>
				</tr>
				<tr>
					<td><spring:message code="dotsreports.tb07.eptb" /></td>
					<td>${table1.relapseMaleExtrapulmonary04}</td>
					<td>${table1.relapseFemaleExtrapulmonary04}</td>
					
					<td>${table1.relapseMaleExtrapulmonary0514}</td>
					<td>${table1.relapseFemaleExtrapulmonary0514}</td>
					
					<td>${table1.relapseMaleExtrapulmonary1517}</td>
					<td>${table1.relapseFemaleExtrapulmonary1517}</td>
					
					
					<td>${table1.relapseMaleExtrapulmonary1819}</td>
					<td>${table1.relapseFemaleExtrapulmonary1819}</td>
					
					
					<td>${table1.relapseMaleExtrapulmonary2024}</td>
					<td>${table1.relapseFemaleExtrapulmonary2024}</td>
					
					
					<td>${table1.relapseMaleExtrapulmonary2534}</td>
					<td>${table1.relapseFemaleExtrapulmonary2534}</td>
					
					
					<td>${table1.relapseMaleExtrapulmonary3544}</td>
					<td>${table1.relapseFemaleExtrapulmonary3544}</td>
					
					
					<td>${table1.relapseMaleExtrapulmonary4554}</td>
					<td>${table1.relapseFemaleExtrapulmonary4554}</td>
					
					
					<td>${table1.relapseMaleExtrapulmonary5564}</td>
					<td>${table1.relapseFemaleExtrapulmonary5564}</td>
					
					<td>${table1.relapseMaleExtrapulmonary65}</td>
					<td>${table1.relapseFemaleExtrapulmonary65}</td>
					
					<td>${table1.relapseMaleExtrapulmonary}</td>
					<td>${table1.relapseFemaleExtrapulmonary}</td>
					<td>${table1.relapseExtrapulmonary}</td>                   
				</tr>
				<tr bgcolor="yellow">
					<td><spring:message code="dotsreports.tb07.ofTheseHIV" /></td>
					<td>${table1.relapseMaleExtrapulmonaryHIV04}</td>
					<td>${table1.relapseFemaleExtrapulmonaryHIV04}</td>
					
					<td>${table1.relapseMaleExtrapulmonaryHIV0514}</td>
					<td>${table1.relapseFemaleExtrapulmonaryHIV0514}</td>
					
					<td>${table1.relapseMaleExtrapulmonaryHIV1517}</td>
					<td>${table1.relapseFemaleExtrapulmonaryHIV1517}</td>
					
					
					<td>${table1.relapseMaleExtrapulmonaryHIV1819}</td>
					<td>${table1.relapseFemaleExtrapulmonaryHIV1819}</td>
					
					
					<td>${table1.relapseMaleExtrapulmonaryHIV2024}</td>
					<td>${table1.relapseFemaleExtrapulmonaryHIV2024}</td>
					
					
					<td>${table1.relapseMaleExtrapulmonaryHIV2534}</td>
					<td>${table1.relapseFemaleExtrapulmonaryHIV2534}</td>
					
					
					<td>${table1.relapseMaleExtrapulmonaryHIV3544}</td>
					<td>${table1.relapseFemaleExtrapulmonaryHIV3544}</td>
					
					
					<td>${table1.relapseMaleExtrapulmonaryHIV4554}</td>
					<td>${table1.relapseFemaleExtrapulmonaryHIV4554}</td>
					
					
					<td>${table1.relapseMaleExtrapulmonaryHIV5564}</td>
					<td>${table1.relapseFemaleExtrapulmonaryHIV5564}</td>
					
					<td>${table1.relapseMaleExtrapulmonaryHIV65}</td>
					<td>${table1.relapseFemaleExtrapulmonaryHIV65}</td>
					
					<td>${table1.relapseMaleExtrapulmonaryHIV}</td>
					<td>${table1.relapseFemaleExtrapulmonaryHIV}</td>
					<td>${table1.relapseExtrapulmonaryHIV}</td> 
					                   
				</tr>
				<tr>
					<td colspan="2" align="left"><spring:message code="dotsreports.tb07.total"/></td>
					<td>${table1.relapseMale04}</td>
					<td>${table1.relapseFemale04}</td>
					
					<td>${table1.relapseMale0514}</td>
					<td>${table1.relapseFemale0514}</td>
					
					<td>${table1.relapseMale1517}</td>
					<td>${table1.relapseFemale1517}</td>
					
					
					<td>${table1.relapseMale1819}</td>
					<td>${table1.relapseFemale1819}</td>
					
					
					<td>${table1.relapseMale2024}</td>
					<td>${table1.relapseFemale2024}</td>
					
					
					<td>${table1.relapseMale2534}</td>
					<td>${table1.relapseFemale2534}</td>
					
					
					<td>${table1.relapseMale3544}</td>
					<td>${table1.relapseFemale3544}</td>
					
					
					<td>${table1.relapseMale4554}</td>
					<td>${table1.relapseFemale4554}</td>
					
					
					<td>${table1.relapseMale5564}</td>
					<td>${table1.relapseFemale5564}</td>
					
					<td>${table1.relapseMale65}</td>
					<td>${table1.relapseFemale65}</td>
					
					<td>${table1.relapseMale}</td>
					<td>${table1.relapseFemale}</td>  
					<td>${table1.relapseAll}</td> 
				</tr>
				<tr bgcolor="yellow">
					<td colspan="2"><spring:message code="dotsreports.tb07.ofTheseHIV" /></td>

					<td>${table1.relapseMaleHIV04}</td>
					<td>${table1.relapseFemaleHIV04}</td>
					
					<td>${table1.relapseMaleHIV0514}</td>
					<td>${table1.relapseFemaleHIV0514}</td>
					
					<td>${table1.relapseMaleHIV1517}</td>
					<td>${table1.relapseFemaleHIV1517}</td>
					
					
					<td>${table1.relapseMaleHIV1819}</td>
					<td>${table1.relapseFemaleHIV1819}</td>
					
					
					<td>${table1.relapseMaleHIV2024}</td>
					<td>${table1.relapseFemaleHIV2024}</td>
					
					
					<td>${table1.relapseMaleHIV2534}</td>
					<td>${table1.relapseFemaleHIV2534}</td>
					
					
					<td>${table1.relapseMaleHIV3544}</td>
					<td>${table1.relapseFemaleHIV3544}</td>
					
					
					<td>${table1.relapseMaleHIV4554}</td>
					<td>${table1.relapseFemaleHIV4554}</td>
					
					
					<td>${table1.relapseMaleHIV5564}</td>
					<td>${table1.relapseFemaleHIV5564}</td>
					
					<td>${table1.relapseMaleHIV65}</td>
					<td>${table1.relapseFemaleHIV65}</td>
					
					<td>${table1.relapseMaleHIV}</td>
					<td>${table1.relapseFemaleHIV}</td>  
					<td>${table1.relapseAllHIV}</td> 
				</tr>
				<tr>
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
					<td rowspan="6">
						<spring:message code="dotsreports.tb07.totalNewRelapse" />
					</td>
					<td><spring:message code="dotsreports.tb07.pulmonaryBC" /></td>
					<td>${table1.newMalePulmonaryBC04 + table1.relapseMalePulmonaryBC04 }</td>
					<td>${table1.newFemalePulmonaryBC04 + table1.relapseFemalePulmonaryBC04 }</td>
					<td>${table1.newMalePulmonaryBC0514 + table1.relapseMalePulmonaryBC0514 }</td>
					<td>${table1.newFemalePulmonaryBC0514 + table1.relapseFemalePulmonaryBC0514 }</td>
					<td>${table1.newMalePulmonaryBC1517 + table1.relapseMalePulmonaryBC1517 }</td>
					<td>${table1.newFemalePulmonaryBC1517 + table1.relapseFemalePulmonaryBC1517 }</td>
					<td>${table1.newMalePulmonaryBC1819 + table1.relapseMalePulmonaryBC1819 }</td>
					<td>${table1.newFemalePulmonaryBC1819 + table1.relapseFemalePulmonaryBC1819 }</td>
					<td>${table1.newMalePulmonaryBC2024 + table1.relapseMalePulmonaryBC2024 }</td>
					<td>${table1.newFemalePulmonaryBC2024 + table1.relapseFemalePulmonaryBC2024 }</td>
					<td>${table1.newMalePulmonaryBC2534 + table1.relapseMalePulmonaryBC2534 }</td>
					<td>${table1.newFemalePulmonaryBC2534 + table1.relapseFemalePulmonaryBC2534 }</td>
					<td>${table1.newMalePulmonaryBC3544 + table1.relapseMalePulmonaryBC3544 }</td>
					<td>${table1.newFemalePulmonaryBC3544 + table1.relapseFemalePulmonaryBC3544 }</td>
					<td>${table1.newMalePulmonaryBC4554 + table1.relapseMalePulmonaryBC4554 }</td>
					<td>${table1.newFemalePulmonaryBC4554 + table1.relapseFemalePulmonaryBC4554 }</td>
					<td>${table1.newMalePulmonaryBC5564 + table1.relapseMalePulmonaryBC5564 }</td>
					<td>${table1.newFemalePulmonaryBC5564 + table1.relapseFemalePulmonaryBC5564 }</td>
					<td>${table1.newMalePulmonaryBC65 + table1.relapseMalePulmonaryBC65 }</td>
					<td>${table1.newFemalePulmonaryBC65 + table1.relapseFemalePulmonaryBC65 }</td>
					<td>${table1.newMalePulmonaryBC + table1.relapseMalePulmonaryBC }</td>
					<td>${table1.newFemalePulmonaryBC + table1.relapseFemalePulmonaryBC }</td>
					<td>${table1.newPulmonaryBC + table1.relapsePulmonaryBC }</td>
					
				</tr>
				<tr bgcolor="yellow">
					<td><spring:message code="dotsreports.tb07.ofTheseHIV" /></td>
					<td>${table1.newMalePulmonaryBCHIV04 + table1.relapseMalePulmonaryBCHIV04 }</td>
					<td>${table1.newFemalePulmonaryBCHIV04 + table1.relapseFemalePulmonaryBCHIV04 }</td>
					<td>${table1.newMalePulmonaryBCHIV0514 + table1.relapseMalePulmonaryBCHIV0514 }</td>
					<td>${table1.newFemalePulmonaryBCHIV0514 + table1.relapseFemalePulmonaryBCHIV0514 }</td>
					<td>${table1.newMalePulmonaryBCHIV1517 + table1.relapseMalePulmonaryBCHIV1517 }</td>
					<td>${table1.newFemalePulmonaryBCHIV1517 + table1.relapseFemalePulmonaryBCHIV1517 }</td>
					<td>${table1.newMalePulmonaryBCHIV1819 + table1.relapseMalePulmonaryBCHIV1819 }</td>
					<td>${table1.newFemalePulmonaryBCHIV1819 + table1.relapseFemalePulmonaryBCHIV1819 }</td>
					<td>${table1.newMalePulmonaryBCHIV2024 + table1.relapseMalePulmonaryBCHIV2024 }</td>
					<td>${table1.newFemalePulmonaryBCHIV2024 + table1.relapseFemalePulmonaryBCHIV2024 }</td>
					<td>${table1.newMalePulmonaryBCHIV2534 + table1.relapseMalePulmonaryBCHIV2534 }</td>
					<td>${table1.newFemalePulmonaryBCHIV2534 + table1.relapseFemalePulmonaryBCHIV2534 }</td>
					<td>${table1.newMalePulmonaryBCHIV3544 + table1.relapseMalePulmonaryBCHIV3544 }</td>
					<td>${table1.newFemalePulmonaryBCHIV3544 + table1.relapseFemalePulmonaryBCHIV3544 }</td>
					<td>${table1.newMalePulmonaryBCHIV4554 + table1.relapseMalePulmonaryBCHIV4554 }</td>
					<td>${table1.newFemalePulmonaryBCHIV4554 + table1.relapseFemalePulmonaryBCHIV4554 }</td>
					<td>${table1.newMalePulmonaryBCHIV5564 + table1.relapseMalePulmonaryBCHIV5564 }</td>
					<td>${table1.newFemalePulmonaryBCHIV5564 + table1.relapseFemalePulmonaryBCHIV5564 }</td>
					<td>${table1.newMalePulmonaryBCHIV65 + table1.relapseMalePulmonaryBCHIV65 }</td>
					<td>${table1.newFemalePulmonaryBCHIV65 + table1.relapseFemalePulmonaryBCHIV65 }</td>
					<td>${table1.newMalePulmonaryBCHIV + table1.relapseMalePulmonaryBCHIV }</td>
					<td>${table1.newFemalePulmonaryBCHIV + table1.relapseFemalePulmonaryBCHIV }</td>
					<td>${table1.newPulmonaryBCHIV + table1.relapsePulmonaryBCHIV }</td>
					                   
				</tr>
				<tr>
					<td><spring:message code="dotsreports.tb07.pulmonaryCD" /> </td>
					<td>${table1.newMalePulmonaryCD04 + table1.relapseMalePulmonaryCD04 }</td>
					<td>${table1.newFemalePulmonaryCD04 + table1.relapseFemalePulmonaryCD04 }</td>
					<td>${table1.newMalePulmonaryCD0514 + table1.relapseMalePulmonaryCD0514 }</td>
					<td>${table1.newFemalePulmonaryCD0514 + table1.relapseFemalePulmonaryCD0514 }</td>
					<td>${table1.newMalePulmonaryCD1517 + table1.relapseMalePulmonaryCD1517 }</td>
					<td>${table1.newFemalePulmonaryCD1517 + table1.relapseFemalePulmonaryCD1517 }</td>
					<td>${table1.newMalePulmonaryCD1819 + table1.relapseMalePulmonaryCD1819 }</td>
					<td>${table1.newFemalePulmonaryCD1819 + table1.relapseFemalePulmonaryCD1819 }</td>
					<td>${table1.newMalePulmonaryCD2024 + table1.relapseMalePulmonaryCD2024 }</td>
					<td>${table1.newFemalePulmonaryCD2024 + table1.relapseFemalePulmonaryCD2024 }</td>
					<td>${table1.newMalePulmonaryCD2534 + table1.relapseMalePulmonaryCD2534 }</td>
					<td>${table1.newFemalePulmonaryCD2534 + table1.relapseFemalePulmonaryCD2534 }</td>
					<td>${table1.newMalePulmonaryCD3544 + table1.relapseMalePulmonaryCD3544 }</td>
					<td>${table1.newFemalePulmonaryCD3544 + table1.relapseFemalePulmonaryCD3544 }</td>
					<td>${table1.newMalePulmonaryCD4554 + table1.relapseMalePulmonaryCD4554 }</td>
					<td>${table1.newFemalePulmonaryCD4554 + table1.relapseFemalePulmonaryCD4554 }</td>
					<td>${table1.newMalePulmonaryCD5564 + table1.relapseMalePulmonaryCD5564 }</td>
					<td>${table1.newFemalePulmonaryCD5564 + table1.relapseFemalePulmonaryCD5564 }</td>
					<td>${table1.newMalePulmonaryCD65 + table1.relapseMalePulmonaryCD65 }</td>
					<td>${table1.newFemalePulmonaryCD65 + table1.relapseFemalePulmonaryCD65 }</td>
					<td>${table1.newMalePulmonaryCD + table1.relapseMalePulmonaryCD }</td>
					<td>${table1.newFemalePulmonaryCD + table1.relapseFemalePulmonaryCD }</td>
					<td>${table1.newPulmonaryCD + table1.relapsePulmonaryCD }</td>
					
				</tr>
				<tr bgcolor="yellow">
					<td><spring:message code="dotsreports.tb07.ofTheseHIV" /></td>
					<td>${table1.newMalePulmonaryCDHIV04 + table1.relapseMalePulmonaryCDHIV04 }</td>
					<td>${table1.newFemalePulmonaryCDHIV04 + table1.relapseFemalePulmonaryCDHIV04 }</td>
					<td>${table1.newMalePulmonaryCDHIV0514 + table1.relapseMalePulmonaryCDHIV0514 }</td>
					<td>${table1.newFemalePulmonaryCDHIV0514 + table1.relapseFemalePulmonaryCDHIV0514 }</td>
					<td>${table1.newMalePulmonaryCDHIV1517 + table1.relapseMalePulmonaryCDHIV1517 }</td>
					<td>${table1.newFemalePulmonaryCDHIV1517 + table1.relapseFemalePulmonaryCDHIV1517 }</td>
					<td>${table1.newMalePulmonaryCDHIV1819 + table1.relapseMalePulmonaryCDHIV1819 }</td>
					<td>${table1.newFemalePulmonaryCDHIV1819 + table1.relapseFemalePulmonaryCDHIV1819 }</td>
					<td>${table1.newMalePulmonaryCDHIV2024 + table1.relapseMalePulmonaryCDHIV2024 }</td>
					<td>${table1.newFemalePulmonaryCDHIV2024 + table1.relapseFemalePulmonaryCDHIV2024 }</td>
					<td>${table1.newMalePulmonaryCDHIV2534 + table1.relapseMalePulmonaryCDHIV2534 }</td>
					<td>${table1.newFemalePulmonaryCDHIV2534 + table1.relapseFemalePulmonaryCDHIV2534 }</td>
					<td>${table1.newMalePulmonaryCDHIV3544 + table1.relapseMalePulmonaryCDHIV3544 }</td>
					<td>${table1.newFemalePulmonaryCDHIV3544 + table1.relapseFemalePulmonaryCDHIV3544 }</td>
					<td>${table1.newMalePulmonaryCDHIV4554 + table1.relapseMalePulmonaryCDHIV4554 }</td>
					<td>${table1.newFemalePulmonaryCDHIV4554 + table1.relapseFemalePulmonaryCDHIV4554 }</td>
					<td>${table1.newMalePulmonaryCDHIV5564 + table1.relapseMalePulmonaryCDHIV5564 }</td>
					<td>${table1.newFemalePulmonaryCDHIV5564 + table1.relapseFemalePulmonaryCDHIV5564 }</td>
					<td>${table1.newMalePulmonaryCDHIV65 + table1.relapseMalePulmonaryCDHIV65 }</td>
					<td>${table1.newFemalePulmonaryCDHIV65 + table1.relapseFemalePulmonaryCDHIV65 }</td>
					<td>${table1.newMalePulmonaryCDHIV + table1.relapseMalePulmonaryCDHIV }</td>
					<td>${table1.newFemalePulmonaryCDHIV + table1.relapseFemalePulmonaryCDHIV }</td>
					<td>${table1.newPulmonaryCDHIV + table1.relapsePulmonaryCDHIV }
				</tr>
				<tr>
					<td><spring:message code="dotsreports.tb07.eptb" /></td>
					<td>${table1.newMaleExtrapulmonary04 + table1.relapseMaleExtrapulmonary04 }</td>
					<td>${table1.newFemaleExtrapulmonary04 + table1.relapseFemaleExtrapulmonary04 }</td>
					<td>${table1.newMaleExtrapulmonary0514 + table1.relapseMaleExtrapulmonary0514 }</td>
					<td>${table1.newFemaleExtrapulmonary0514 + table1.relapseFemaleExtrapulmonary0514 }</td>
					<td>${table1.newMaleExtrapulmonary1517 + table1.relapseMaleExtrapulmonary1517 }</td>
					<td>${table1.newFemaleExtrapulmonary1517 + table1.relapseFemaleExtrapulmonary1517 }</td>
					<td>${table1.newMaleExtrapulmonary1819 + table1.relapseMaleExtrapulmonary1819 }</td>
					<td>${table1.newFemaleExtrapulmonary1819 + table1.relapseFemaleExtrapulmonary1819 }</td>
					<td>${table1.newMaleExtrapulmonary2024 + table1.relapseMaleExtrapulmonary2024 }</td>
					<td>${table1.newFemaleExtrapulmonary2024 + table1.relapseFemaleExtrapulmonary2024 }</td>
					<td>${table1.newMaleExtrapulmonary2534 + table1.relapseMaleExtrapulmonary2534 }</td>
					<td>${table1.newFemaleExtrapulmonary2534 + table1.relapseFemaleExtrapulmonary2534 }</td>
					<td>${table1.newMaleExtrapulmonary3544 + table1.relapseMaleExtrapulmonary3544 }</td>
					<td>${table1.newFemaleExtrapulmonary3544 + table1.relapseFemaleExtrapulmonary3544 }</td>
					<td>${table1.newMaleExtrapulmonary4554 + table1.relapseMaleExtrapulmonary4554 }</td>
					<td>${table1.newFemaleExtrapulmonary4554 + table1.relapseFemaleExtrapulmonary4554 }</td>
					<td>${table1.newMaleExtrapulmonary5564 + table1.relapseMaleExtrapulmonary5564 }</td>
					<td>${table1.newFemaleExtrapulmonary5564 + table1.relapseFemaleExtrapulmonary5564 }</td>
					<td>${table1.newMaleExtrapulmonary65 + table1.relapseMaleExtrapulmonary65 }</td>
					<td>${table1.newFemaleExtrapulmonary65 + table1.relapseFemaleExtrapulmonary65 }</td>
					<td>${table1.newMaleExtrapulmonary + table1.relapseMaleExtrapulmonary }</td>
					<td>${table1.newFemaleExtrapulmonary + table1.relapseFemaleExtrapulmonary }</td>
					<td>${table1.newExtrapulmonary + table1.relapseExtrapulmonary }</td>
					
				</tr>
				<tr bgcolor="yellow">
					<td><spring:message code="dotsreports.tb07.ofTheseHIV" /></td>
					<td>${table1.newMaleExtrapulmonaryHIV04 + table1.relapseMaleExtrapulmonaryHIV04 }</td>
					<td>${table1.newFemaleExtrapulmonaryHIV04 + table1.relapseFemaleExtrapulmonaryHIV04 }</td>
					<td>${table1.newMaleExtrapulmonaryHIV0514 + table1.relapseMaleExtrapulmonaryHIV0514 }</td>
					<td>${table1.newFemaleExtrapulmonaryHIV0514 + table1.relapseFemaleExtrapulmonaryHIV0514 }</td>
					<td>${table1.newMaleExtrapulmonaryHIV1517 + table1.relapseMaleExtrapulmonaryHIV1517 }</td>
					<td>${table1.newFemaleExtrapulmonaryHIV1517 + table1.relapseFemaleExtrapulmonaryHIV1517 }</td>
					<td>${table1.newMaleExtrapulmonaryHIV1819 + table1.relapseMaleExtrapulmonaryHIV1819 }</td>
					<td>${table1.newFemaleExtrapulmonaryHIV1819 + table1.relapseFemaleExtrapulmonaryHIV1819 }</td>
					<td>${table1.newMaleExtrapulmonaryHIV2024 + table1.relapseMaleExtrapulmonaryHIV2024 }</td>
					<td>${table1.newFemaleExtrapulmonaryHIV2024 + table1.relapseFemaleExtrapulmonaryHIV2024 }</td>
					<td>${table1.newMaleExtrapulmonaryHIV2534 + table1.relapseMaleExtrapulmonaryHIV2534 }</td>
					<td>${table1.newFemaleExtrapulmonaryHIV2534 + table1.relapseFemaleExtrapulmonaryHIV2534 }</td>
					<td>${table1.newMaleExtrapulmonaryHIV3544 + table1.relapseMaleExtrapulmonaryHIV3544 }</td>
					<td>${table1.newFemaleExtrapulmonaryHIV3544 + table1.relapseFemaleExtrapulmonaryHIV3544 }</td>
					<td>${table1.newMaleExtrapulmonaryHIV4554 + table1.relapseMaleExtrapulmonaryHIV4554 }</td>
					<td>${table1.newFemaleExtrapulmonaryHIV4554 + table1.relapseFemaleExtrapulmonaryHIV4554 }</td>
					<td>${table1.newMaleExtrapulmonaryHIV5564 + table1.relapseMaleExtrapulmonaryHIV5564 }</td>
					<td>${table1.newFemaleExtrapulmonaryHIV5564 + table1.relapseFemaleExtrapulmonaryHIV5564 }</td>
					<td>${table1.newMaleExtrapulmonaryHIV65 + table1.relapseMaleExtrapulmonaryHIV65 }</td>
					<td>${table1.newFemaleExtrapulmonaryHIV65 + table1.relapseFemaleExtrapulmonaryHIV65 }</td>
					<td>${table1.newMaleExtrapulmonaryHIV + table1.relapseMaleExtrapulmonaryHIV }</td>
					<td>${table1.newFemaleExtrapulmonaryHIV + table1.relapseFemaleExtrapulmonaryHIV }</td>
					<td>${table1.newExtrapulmonaryHIV + table1.relapseExtrapulmonaryHIV }
				</tr>
				<tr>
					<td colspan="2" align="left"><spring:message code="dotsreports.tb07.total"/></td>

					<td>${table1.newMale04 + table1.relapseMale04 }</td>
					<td>${table1.newFemale04 + table1.relapseFemale04 }</td>
					<td>${table1.newMale0514 + table1.relapseMale0514 }</td>
					<td>${table1.newFemale0514 + table1.relapseFemale0514 }</td>
					<td>${table1.newMale1517 + table1.relapseMale1517 }</td>
					<td>${table1.newFemale1517 + table1.relapseFemale1517 }</td>
					<td>${table1.newMale1819 + table1.relapseMale1819 }</td>
					<td>${table1.newFemale1819 + table1.relapseFemale1819 }</td>
					<td>${table1.newMale2024 + table1.relapseMale2024 }</td>
					<td>${table1.newFemale2024 + table1.relapseFemale2024 }</td>
					<td>${table1.newMale2534 + table1.relapseMale2534 }</td>
					<td>${table1.newFemale2534 + table1.relapseFemale2534 }</td>
					<td>${table1.newMale3544 + table1.relapseMale3544 }</td>
					<td>${table1.newFemale3544 + table1.relapseFemale3544 }</td>
					<td>${table1.newMale4554 + table1.relapseMale4554 }</td>
					<td>${table1.newFemale4554 + table1.relapseFemale4554 }</td>
					<td>${table1.newMale5564 + table1.relapseMale5564 }</td>
					<td>${table1.newFemale5564 + table1.relapseFemale5564 }</td>
					<td>${table1.newMale65 + table1.relapseMale65 }</td>
					<td>${table1.newFemale65 + table1.relapseFemale65 }</td>
					<td>${table1.newMale + table1.relapseMale }</td>
					<td>${table1.newFemale + table1.relapseFemale }</td>		
					<td>${table1.newAll + table1.relapseAll }</td>
				</tr>
				<tr bgcolor="yellow">
					<td colspan="2"><spring:message code="dotsreports.tb07.ofTheseHIV" /></td>

					<td>${table1.newMaleHIV04 + table1.relapseMaleHIV04 }</td>
					<td>${table1.newFemaleHIV04 + table1.relapseFemaleHIV04 }</td>
					<td>${table1.newMaleHIV0514 + table1.relapseMaleHIV0514 }</td>
					<td>${table1.newFemaleHIV0514 + table1.relapseFemaleHIV0514 }</td>
					<td>${table1.newMaleHIV1517 + table1.relapseMaleHIV1517 }</td>
					<td>${table1.newFemaleHIV1517 + table1.relapseFemaleHIV1517 }</td>
					<td>${table1.newMaleHIV1819 + table1.relapseMaleHIV1819 }</td>
					<td>${table1.newFemaleHIV1819 + table1.relapseFemaleHIV1819 }</td>
					<td>${table1.newMaleHIV2024 + table1.relapseMaleHIV2024 }</td>
					<td>${table1.newFemaleHIV2024 + table1.relapseFemaleHIV2024 }</td>
					<td>${table1.newMaleHIV2534 + table1.relapseMaleHIV2534 }</td>
					<td>${table1.newFemaleHIV2534 + table1.relapseFemaleHIV2534 }</td>
					<td>${table1.newMaleHIV3544 + table1.relapseMaleHIV3544 }</td>
					<td>${table1.newFemaleHIV3544 + table1.relapseFemaleHIV3544 }</td>
					<td>${table1.newMaleHIV4554 + table1.relapseMaleHIV4554 }</td>
					<td>${table1.newFemaleHIV4554 + table1.relapseFemaleHIV4554 }</td>
					<td>${table1.newMaleHIV5564 + table1.relapseMaleHIV5564 }</td>
					<td>${table1.newFemaleHIV5564 + table1.relapseFemaleHIV5564 }</td>
					<td>${table1.newMaleHIV65 + table1.relapseMaleHIV65 }</td>
					<td>${table1.newFemaleHIV65 + table1.relapseFemaleHIV65 }</td>
					<td>${table1.newMaleHIV + table1.relapseMaleHIV }</td>
					<td>${table1.newFemaleHIV + table1.relapseFemaleHIV }</td>		
					<td>${table1.newAllHIV + table1.relapseAllHIV }</td>
				</tr>


			</tbody>
		</table>
<p><spring:message code="mdrtb.tb07.table1.footnote" arguments="${table1.womenOfChildBearingAge},${table1.pregnant},${table1.contacts},${table1.migrants},${table1.phcWorkers},${table1.tbServicesWorkers},${table1.died},${table1.diedChildren},${table1.diedWomenOfChildBearingAge }" /> </p>
		


<h5><spring:message code="dotsreports.tb07.table2.title" /></h5>
<table style="width: 900px;" border="1">
<tbody>
<tr>
<td>&nbsp;</td>
<td style="text-align: center;" colspan="3"><spring:message code="dotsreports.tb07.afterFailure"/></td>
<td style="text-align: center;" colspan="3"><spring:message code="dotsreports.tb07.afterDefault"/></td>
<td style="text-align: center;" colspan="3"><spring:message code="mdrtb.tb07.others"/></td>
<td style="text-align: center;" colspan="3"><spring:message code="dotsreports.tb07.total"/></td>
<td colspan="3">
<p style="text-align: center;"><spring:message code="dotsreports.tb07.total"/></p>
<p style="text-align: center;"><spring:message code="dotsreports.tb07.t1t2"/>)</p>
</td>
</tr>
<tr>
<td>&nbsp;</td>
<td align="center"><spring:message code="dotsreports.tb07.maleShort"/></td>
<td align="center"><spring:message code="dotsreports.tb07.femaleShort"/></td>
<td align="center"><spring:message code="dotsreports.tb07.total"/></td>
<td align="center"><spring:message code="dotsreports.tb07.maleShort"/></td>
<td align="center"><spring:message code="dotsreports.tb07.femaleShort"/></td>
<td align="center"><spring:message code="dotsreports.tb07.total"/></td>
<td align="center"><spring:message code="dotsreports.tb07.maleShort"/></td>
<td align="center"><spring:message code="dotsreports.tb07.femaleShort"/></td>
<td align="center"><spring:message code="dotsreports.tb07.total"/></td>
<td align="center"><spring:message code="dotsreports.tb07.maleShort"/></td>
<td align="center"><spring:message code="dotsreports.tb07.femaleShort"/></td>
<td align="center"><spring:message code="dotsreports.tb07.total"/></td>
<td align="center"><spring:message code="dotsreports.tb07.maleShort"/></td>
<td align="center"><spring:message code="dotsreports.tb07.femaleShort"/></td>
<td align="center"><spring:message code="dotsreports.tb07.total"/></td>
</tr>
<tr>
<td>
<p><spring:message code="dotsreports.tb07.pulmonaryBC" /></p>
</td>
<td>${table1.failureMalePulmonaryBC}</td>
<td>${table1.failureFemalePulmonaryBC}</td>
<td>${table1.failurePulmonaryBC}</td>
<td>${table1.defaultMalePulmonaryBC}</td>
<td>${table1.defaultFemalePulmonaryBC}</td>
<td>${table1.defaultPulmonaryBC}</td>
<td>${table1.otherMalePulmonaryBC}</td>
<td>${table1.otherFemalePulmonaryBC}</td>
<td>${table1.otherPulmonaryBC}</td>
<td>${table1.retreatmentMalePulmonaryBC}</td>
<td>${table1.retreatmentFemalePulmonaryBC}</td>
<td>${table1.retreatmentPulmonaryBC}</td>
<td>${table1.totalMalePulmonaryBC}</td>
<td>${table1.totalFemalePulmonaryBC}</td>
<td>${table1.totalPulmonaryBC}</td>
                      
</tr>
<tr bgcolor="yellow">
<td><spring:message code="dotsreports.tb07.ofTheseHIV" /></td>
<td>${table1.failureMalePulmonaryBCHIV}</td>
<td>${table1.failureFemalePulmonaryBCHIV}</td>
<td>${table1.failurePulmonaryBCHIV}</td>
<td>${table1.defaultMalePulmonaryBCHIV}</td>
<td>${table1.defaultFemalePulmonaryBCHIV}</td>
<td>${table1.defaultPulmonaryBCHIV}</td>
<td>${table1.otherMalePulmonaryBCHIV}</td>
<td>${table1.otherFemalePulmonaryBCHIV}</td>
<td>${table1.otherPulmonaryBCHIV}</td>
<td>${table1.retreatmentMalePulmonaryBCHIV}</td>
<td>${table1.retreatmentFemalePulmonaryBCHIV}</td>
<td>${table1.retreatmentPulmonaryBCHIV}</td>
<td>${table1.totalMalePulmonaryBCHIV}</td>
<td>${table1.totalFemalePulmonaryBCHIV}</td>
<td>${table1.totalPulmonaryBCHIV}</td>
</tr>
<tr>
<td><spring:message code="dotsreports.tb07.pulmonaryCD" /> </td>
<td>${table1.failureMalePulmonaryCD}</td>
<td>${table1.failureFemalePulmonaryCD}</td>
<td>${table1.failurePulmonaryCD}</td>
<td>${table1.defaultMalePulmonaryCD}</td>
<td>${table1.defaultFemalePulmonaryCD}</td>
<td>${table1.defaultPulmonaryCD}</td>
<td>${table1.otherMalePulmonaryCD}</td>
<td>${table1.otherFemalePulmonaryCD}</td>
<td>${table1.otherPulmonaryCD}</td>
<td>${table1.retreatmentMalePulmonaryCD}</td>
<td>${table1.retreatmentFemalePulmonaryCD}</td>
<td>${table1.retreatmentPulmonaryCD}</td>
<td>${table1.totalMalePulmonaryCD}</td>
<td>${table1.totalFemalePulmonaryCD}</td>
<td>${table1.totalPulmonaryCD}</td>
</tr>
<tr bgcolor="yellow">
<td><spring:message code="dotsreports.tb07.ofTheseHIV" />&nbsp;</td>
<td>${table1.failureMalePulmonaryCDHIV}</td>
<td>${table1.failureFemalePulmonaryCDHIV}</td>
<td>${table1.failurePulmonaryCDHIV}</td>
<td>${table1.defaultMalePulmonaryCDHIV}</td>
<td>${table1.defaultFemalePulmonaryCDHIV}</td>
<td>${table1.defaultPulmonaryCDHIV}</td>
<td>${table1.otherMalePulmonaryCDHIV}</td>
<td>${table1.otherFemalePulmonaryCDHIV}</td>
<td>${table1.otherPulmonaryCDHIV}</td>
<td>${table1.retreatmentMalePulmonaryCDHIV}</td>
<td>${table1.retreatmentFemalePulmonaryCDHIV}</td>
<td>${table1.retreatmentPulmonaryCDHIV}</td>
<td>${table1.totalMalePulmonaryCDHIV}</td>
<td>${table1.totalFemalePulmonaryCDHIV}</td>
<td>${table1.totalPulmonaryCDHIV}</td>
</tr>
<tr>
<td><spring:message code="dotsreports.tb07.eptb" /></td>
<td>${table1.failureMaleExtrapulmonary}</td>
<td>${table1.failureFemaleExtrapulmonary}</td>
<td>${table1.failureExtrapulmonary}</td>
<td>${table1.defaultMaleExtrapulmonary}</td>
<td>${table1.defaultFemaleExtrapulmonary}</td>
<td>${table1.defaultExtrapulmonary}</td>
<td>${table1.otherMaleExtrapulmonary}</td>
<td>${table1.otherFemaleExtrapulmonary}</td>
<td>${table1.otherExtrapulmonary}</td>
<td>${table1.retreatmentMaleExtrapulmonary}</td>
<td>${table1.retreatmentFemaleExtrapulmonary}</td>
<td>${table1.retreatmentExtrapulmonary}</td>
<td>${table1.totalMaleExtrapulmonary}</td>
<td>${table1.totalFemaleExtrapulmonary}</td>
<td>${table1.totalExtrapulmonary}</td>
</tr>
<tr bgcolor="yellow">
<td><spring:message code="dotsreports.tb07.ofTheseHIV" /></td>
<td>${table1.failureMaleExtrapulmonaryHIV}</td>
<td>${table1.failureFemaleExtrapulmonaryHIV}</td>
<td>${table1.failureExtrapulmonaryHIV}</td>
<td>${table1.defaultMaleExtrapulmonaryHIV}</td>
<td>${table1.defaultFemaleExtrapulmonaryHIV}</td>
<td>${table1.defaultExtrapulmonaryHIV}</td>
<td>${table1.otherMaleExtrapulmonaryHIV}</td>
<td>${table1.otherFemaleExtrapulmonaryHIV}</td>
<td>${table1.otherExtrapulmonaryHIV}</td>
<td>${table1.retreatmentMaleExtrapulmonaryHIV}</td>
<td>${table1.retreatmentFemaleExtrapulmonaryHIV}</td>
<td>${table1.retreatmentExtrapulmonaryHIV}</td>
<td>${table1.totalMaleExtrapulmonaryHIV}</td>
<td>${table1.totalFemaleExtrapulmonaryHIV}</td>
<td>${table1.totalExtrapulmonaryHIV}</td>
<tr>
<td style="margin-left: 30px; text-align: center;"><strong><spring:message code="dotsreports.tb07.total"/></strong></td>
<td>${table1.failureMale}</td>
<td>${table1.failureFemale}</td>
<td>${table1.failureAll}</td>
<td>${table1.defaultMale}</td>
<td>${table1.defaultFemale}</td>
<td>${table1.defaultAll}</td>
<td>${table1.otherMale}</td>
<td>${table1.otherFemale}</td>
<td>${table1.otherAll}</td>
<td>${table1.retreatmentMale}</td>
<td>${table1.retreatmentFemale}</td>
<td>${table1.retreatmentAll}</td>
<td>${table1.totalMale}</td>
<td>${table1.totalFemale}</td>
<td>${table1.totalAll}</td>
</tr>

<tr bgcolor="yellow">
<td style="text-align: center;"><strong><spring:message code="dotsreports.tb07.ofTheseHIV" /></strong></td>
<td>${table1.failureMaleHIV}</td>
<td>${table1.failureFemaleHIV}</td>
<td>${table1.failureAllHIV}</td>
<td>${table1.defaultMaleHIV}</td>
<td>${table1.defaultFemaleHIV}</td>
<td>${table1.defaultAllHIV}</td>
<td>${table1.otherMaleHIV}</td>
<td>${table1.otherFemaleHIV}</td>
<td>${table1.otherAllHIV}</td>
<td>${table1.retreatmentMaleHIV}</td>
<td>${table1.retreatmentFemaleHIV}</td>
<td>${table1.retreatmentAllHIV}</td>
<td>${table1.totalMaleHIV}</td>
<td>${table1.totalFemaleHIV}</td>
<td>${table1.totalAllHIV}</td>
</tr>




</tbody>
</table>

<br/><br/>
<h5><spring:message code="dotsreports.tb07.table3.title" /></h5>
<table style="width: 900px;" border="1">
<tbody>	
<tr>
<td align="center"><spring:message code="dotsreports.tb07.testedForHIV"/></td>
<td align="center"><spring:message code="dotsreports.tb07.confirmedHIV"/></td>
<td align="center"><spring:message code="dotsreports.tb07.artStarted"/></td>
<td align="center"><spring:message code="dotsreports.tb07.ptcStarted"/></td>
</tr>
<tr>
<td align="center">${table1.hivTested }</td>
<td align="center">${table1.hivPositive }</td>
<td align="center">${table1.artStarted }</td>
<td align="center">${table1.pctStarted }</td>
</tr>
</tbody>
</table>

</div>
		
		<input type="button" onclick="tableToExcel('tb07', 'TB07')" value="<spring:message code='dotsreports.exportToExcelBtn' />" />
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
