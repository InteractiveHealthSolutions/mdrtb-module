<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%>
<%@ include file="../mdrtbHeader.jsp"%>

<html>
	<head>
		<title>
			Closed Reports
		</title>

		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/moduleResources/mdrtb/css/datatables.min.css"/>
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/jquery/jquery.dataTables.min.js"></script>
	</head>
	<body>
		<h2>
			<center><spring:message code="mdrtb.viewClosedReports" /></center>
		</h2>
		<br/>
		<table class="display" id="myTable" border="1">
			<thead>
				<tr>
					<th><spring:message code="mdrtb.viewClosedReports.oblast" /></th>
					<th><spring:message code="mdrtb.viewClosedReports.district" /></th>
					<th><spring:message code="mdrtb.viewClosedReports.year" /></th>
					<th><spring:message code="mdrtb.viewClosedReports.quarter" /></th>
					<th><spring:message code="mdrtb.viewClosedReports.month" /></th>
					<th><spring:message code="mdrtb.viewClosedReports.reportName" /></th>
					<th><spring:message code="mdrtb.viewClosedReports.reportDate" /></th>
					<th colspan="2"></th>
				</tr>
			</thead>
			<tbody id="tbody"></tbody>
		</table>

		<form id="viewReport" name="viewReport" method="post">
			<input type="hidden" id="oblast" name="oblast" />
			<input type="hidden" id="location" name="location" />
			<input type="hidden" id="year" name="year" />
			<input type="hidden" id="quarter" name="quarter" />
			<input type="hidden" id="month" name="month" />
			<input type="hidden" id="reportName" name="reportName" />
			<input type="hidden" id="reportDate" name="reportDate" />
			<input type="hidden" id="formAction" name="formAction" />
		</form>

		<script>
			$(document).ready(function() {
				$('#myTable').DataTable({
					"bFilter": false,
					"ordering": false,
					"paging" : false,
					"lengthChange" : false,
					"searching" : false,
					"ordering" : false,
					"info" : false,
					"autoWidth" : true,
				});
			});
			
			var viewBtnTxt = '<spring:message code="mdrtb.viewClosedReports.viewBtn" />';
			var unlockBtnTxt = '<spring:message code="mdrtb.viewClosedReports.unlockBtn" />';
			var tbody = document.getElementById("tbody");
			var oblastIds = []; var oblastNames = [];
			var locationIds = []; var locationNames = [];
			
			if("${reportIds}"=== "[]") {
				var row = tbody.insertRow(-1);
				var cell = row.insertCell(-1);
				cell.colSpan = document.getElementById("myTable").rows[0].cells.length;
				cell.innerHTML = "<center>No Data Found</center>";
			}
			else {
				<c:forEach var="reportId" items="${reportIds}" varStatus="reportIdLoop">
					oblastIds.push("${reportOblasts[reportIdLoop.index].id}"); 
					oblastNames.push("${reportOblasts[reportIdLoop.index].name}");
					locationIds.push("${reportLocations[reportIdLoop.index].id}"); 
					locationNames.push("${reportLocations[reportIdLoop.index].name}");
					
					var row = tbody.insertRow(-1);
					
					//OBLAST
					var cell = row.insertCell(-1);
					cell.id="oblast_${reportIdLoop.index}";
					cell.innerHTML = "${reportOblasts[reportIdLoop.index].name}";
					
					//LOCATION
					var cell = row.insertCell(-1);
					cell.id="location_${reportIdLoop.index}";
					cell.innerHTML = "${reportLocations[reportIdLoop.index].name}";
	
					//YEAR
					var cell = row.insertCell(-1);
					cell.id="year_${reportIdLoop.index}";
					cell.innerHTML = "${years[reportIdLoop.index]}";
					
					//QUARTER
					var cell = row.insertCell(-1);
					cell.id="quarter_${reportIdLoop.index}";
					cell.innerHTML = "${quarters[reportIdLoop.index]}";
					
					//MONTH
					var cell = row.insertCell(-1);
					cell.id="month_${reportIdLoop.index}";
					cell.innerHTML = "${months[reportIdLoop.index]}";
					
					//REPORT_NAME
					var cell = row.insertCell(-1);
					cell.id="reportName_${reportIdLoop.index}";
					cell.innerHTML = ("${reportNames[reportIdLoop.index]}").replace("_", " ").toUpperCase();
					
					//REPORT_DATE
					var cell = row.insertCell(-1);
					cell.id="reportDate_${reportIdLoop.index}";
					cell.innerHTML = "${reportDates[reportIdLoop.index]}";
					
					//VIEW
					var cell = row.insertCell(-1);
					cell.id="view_${reportIdLoop.index}";
					cell.innerHTML = "<button id='viewBtn_${reportIdLoop.index}' name='viewBtn_${reportIdLoop.index}' onclick='view(\"${reportIdLoop.index}\");'>"+viewBtnTxt+"</button>";
	
					//UNLOCK
					var cell = row.insertCell(-1);
					cell.id="unlock_${reportIdLoop.index}";
					cell.innerHTML = "<button id='unlockBtn_${reportIdLoop.index}' name='unlockBtn_${reportIdLoop.index}' onclick='unlock(\"${reportIdLoop.index}\");'>"+unlockBtnTxt+"</button>";
				</c:forEach>
			}


			function maxLengthCheck(year) { if (year.value.length > 4) { year.value = year.value.slice(0,4); } }

			function submitForm(id, formAction) {
				document.getElementById("oblast").value = oblastIds[oblastNames.indexOf(document.getElementById("oblast_"+id).innerHTML)];
				document.getElementById("location").value = locationIds[locationNames.indexOf(document.getElementById("location_"+id).innerHTML)];
				document.getElementById("year").value = document.getElementById("year_"+id).innerHTML;
				document.getElementById("quarter").value = document.getElementById("quarter_"+id).innerHTML;
				document.getElementById("month").value = document.getElementById("month_"+id).innerHTML;
				document.getElementById("reportName").value = document.getElementById("reportName_"+id).innerHTML;
				document.getElementById("reportDate").value = document.getElementById("reportDate_"+id).innerHTML;
				document.getElementById("formAction").value = formAction; 
				document.getElementById("viewReport").submit();
			}
			
			function view(id) { 
				submitForm(id, "view");
			}
			function unlock(id) { 
				var confirm = confirm('<spring:message code="mdrtb.unlockClosedReportMessage" />'); 
				if(confirm) {
					submitForm(id, "unlock");
				}
			}
		</script>
	</body>
</html>
<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbFooter.jsp"%>