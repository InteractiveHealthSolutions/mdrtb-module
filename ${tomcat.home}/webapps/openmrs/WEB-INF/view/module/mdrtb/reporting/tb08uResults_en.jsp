<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		<style>
			th {vertical-align:middle; text-align:center;}
			th, td {font-size:smaller;}
		</style>


<!-- <openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.tooltip.js" />
<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.tooltip.js" />
<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.tooltip.js" />
<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.tooltip.js" />
<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.tooltip.js" /> -->
		
		<%-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/jspdf/tableExport.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/jspdf/jquery.base64.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/jspdf/sprintf.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/jspdf/jspdf.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/jspdf/base64.js"></script>


<script type="text/javascript" src="https://code.jquery.com/jquery-2.1.3.js"></script>
    <script type="text/javascript" src="js/jspdf.js"></script>
    <script type="text/javascript" src="js/from_html.js"></script>
    <script type="text/javascript" src="js/split_text_to_size.js"></script>
    <script type="text/javascript" src="js/standard_fonts_metrics.js"></script>
    <script type="text/javascript" src="js/cell.js"></script>
    <script type="text/javascript" src="js/FileSaver.js"></script> --%>


		<link rel="stylesheet" href="<%= request.getContextPath() %>/moduleResources/mdrtb/tableExport/css/jquery-ui.css" />
		<link rel="stylesheet" href="<%= request.getContextPath() %>/moduleResources/mdrtb/tableExport/css/jquery.dataTables.min.css" />
	
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/tableExport/js/jquery-1.12.4.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/tableExport/js/jquery-ui.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/tableExport/js/jquery.dataTables.min.js"></script>




		<script type="text/javascript">
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
			$(document).ready(function(){
				$("#tableToPdf").click(function(){
					$('#tb08u').tableExport({type:'pdf',pdfFontSize:'7',escape:'false'});
				});
			});
		</script>
		<div style="font-size:smaller; width:980px;">
			<table width="90%"><tr>
				<td width="90" align="left" style="font-size:14px; font-weight:bold;">
					Quarterly report on DR TB cases treatment outcomes<br/>
					(to be filled after 24-36 months of the last date of quarter or year of treatment start) 
				</td>
				<td width="10%" align="right" style="font-size:14px; font-weight:bold;">TB 08y</td>
			</tr></table>
			<br/><br/>
			<center>
			<table width="100%" border="1">
				<tr>
				    <td>Name of facility: ________________  <br/>  
				    Region/City/District  ${location} }<br/>
					Name of coordinator:  _________________<br/>
					Signature: ____________________
				</td>
				
				<td valign="top">Cases detected in quarter ${quarter } of ${year } <br/>
					Date of Report: ${reportDate }
				</tr>
			</table>
			</center>
			<br/><br/>
			
			
			
			<input type="button" onclick="tableToExcel('tb08u', 'TB08u')" value="Export to Excel" />
			<input type="button" id="tableToPdf" name="tableToPdf" value="Export to Pdf" />
			<table border="1" id="tb08u" cellpadding="5" width="100%">
				<tr>
					<th rowspan="2" colspan="2" align="center">Registration group</th>
					<th rowspan="2" align="center">Registered</th>
					<th rowspan="2" align="center">Cured</th>
					<th rowspan="2" align="center">Treatment<br/>Completed</th>
					<th rowspan="2" align="center">TxSuccessful Treatment</br>(Cured+ Treatment completed)</th>
					<th colspan="2" align="center">Died</th>
					<th rowspan="2" align="center">Treatment<br/>Failure</th>
					<th rowspan="2" align="center">Lost to Follow-<br/>up</th>
					
					
					<th rowspan="2" align="center">Result Not<br/>Assessed<br/>(still on treatment<br/>or transferred out)</th>
					<th rowspan="2" align="center">Total</th>
				</tr>
				<tr>
					<th align="center">TB</th>
					<th align="center">Non-TB</th>
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
					<td colspan="2">New Cases</td>
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
					<td rowspan="2" align="center">Relapse after treatment<br/>on regimen</td>
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
					<td rowspan="2" align="center">Default after treatment <br/>on regimen</td>
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
					<td rowspan="2" align="center">Failure after treatment <br/>on regimen</td>
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
					<td colspan="2">Other</td>
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
					<td colspan="2">Total</td>
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
	</body>
</html>
