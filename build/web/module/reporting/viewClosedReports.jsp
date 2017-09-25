<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%>
<%@ include file="../mdrtbHeader.jsp"%>

<html>
	<head>
		<title>Closed Reports</title>

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
					<th><spring:message code="mdrtb.viewClosedReports.facility" /></th>
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
			<input type="hidden" id="district" name="district" />
			<input type="hidden" id="facility" name="facility" />
			<input type="hidden" id="year" name="year" />
			<input type="hidden" id="quarter" name="quarter" />
			<input type="hidden" id="month" name="month" />
			<input type="hidden" id="reportName" name="reportName" />
			<input type="hidden" id="reportDate" name="reportDate" />
			<input type="hidden" id="formAction" name="formAction" />
			<input type="hidden" id="reportType" name="reportType" />
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
			
			var tbody = document.getElementById("tbody");
			var oblastIds = []; var oblastNames = [];
			var districtIds = []; var districtNames = [];
			var facilityIds = []; var facilityNames = [];
			
			<c:choose>
			<c:when test="${empty reportIds}">
			
				var row = tbody.insertRow(-1);
				var cell = row.insertCell(-1);
				cell.colSpan = document.getElementById("myTable").rows[0].cells.length;
				cell.innerHTML = "<center>No Data Found</center>";
			</c:when>
			<c:otherwise>
			
				<c:forEach var="reportId" items="${reportIds}" varStatus="reportIdLoop">
					oblastIds.push("${reportOblasts[reportIdLoop.index].id}"); 
					oblastNames.push("${reportOblasts[reportIdLoop.index].name}");
					districtIds.push("${reportDistricts[reportIdLoop.index].id}"); 
					districtNames.push("${reportDistricts[reportIdLoop.index].name}");
					facilityIds.push("${reportFacilities[reportIdLoop.index].id}"); 
					facilityNames.push("${reportFacilities[reportIdLoop.index].name}");
					
					var row = tbody.insertRow(-1);
					
					//OBLAST
					var cell = row.insertCell(-1);
					cell.id="oblast_${reportIdLoop.index}";
					cell.innerHTML = "${reportOblasts[reportIdLoop.index].name}";
					
					//DISTRICT
					var cell = row.insertCell(-1);
					cell.id="district_${reportIdLoop.index}";
					cell.innerHTML = "${reportDistricts[reportIdLoop.index].name}";
					
					//FACILITY
					var cell = row.insertCell(-1);
					cell.id="facility_${reportIdLoop.index}";
					cell.innerHTML = "${reportFacilities[reportIdLoop.index].name}";
	
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
					
					var viewBtnTxt = "<spring:message code='mdrtb.viewClosedReports.viewBtn' />";
					var unlockBtnTxt = "<spring:message code='mdrtb.viewClosedReports.unlockBtn' />";
					
					
					//VIEW
					var cell = row.insertCell(-1);
					cell.id="view_${reportIdLoop.index}";
					cell.innerHTML = "<button id='viewBtn_${reportIdLoop.index}' name='viewBtn_${reportIdLoop.index}' onclick='view(\"${reportIdLoop.index}\");' style='width: 100%'>"+viewBtnTxt+"</button>";
	
					//UNLOCK
					var cell = row.insertCell(-1);
					cell.id="unlock_${reportIdLoop.index}";
					cell.innerHTML = "<button id='unlockBtn_${reportIdLoop.index}' name='unlockBtn_${reportIdLoop.index}' onclick='unlock(\"${reportIdLoop.index}\");' style='width: 100%' disabled='true'>"+unlockBtnTxt+"</button>";
					<openmrs:hasPrivilege privilege="Manage Report Closing">
					document.getElementById('unlockBtn_${reportIdLoop.index}').disabled = false;
					</openmrs:hasPrivilege>
				</c:forEach>
			 
			</c:otherwise>
			</c:choose>
			

			function maxLengthCheck(year) { if (year.value.length > 4) { year.value = year.value.slice(0,4); } }

			function submitForm(id, formAction) {
				document.getElementById("oblast").value = oblastIds[oblastNames.indexOf(document.getElementById("oblast_"+id).innerHTML)];
				document.getElementById("district").value = districtIds[districtNames.indexOf(document.getElementById("district_"+id).innerHTML)];
				document.getElementById("facility").value = facilityIds[facilityNames.indexOf(document.getElementById("facility_"+id).innerHTML)];
				document.getElementById("year").value = document.getElementById("year_"+id).innerHTML;
				document.getElementById("quarter").value = document.getElementById("quarter_"+id).innerHTML;
				document.getElementById("month").value = document.getElementById("month_"+id).innerHTML;
				document.getElementById("reportName").value = document.getElementById("reportName_"+id).innerHTML;
				document.getElementById("reportDate").value = document.getElementById("reportDate_"+id).innerHTML;
				document.getElementById("formAction").value = formAction; 
				document.getElementById("reportType").value = '${reportType}'; 
				document.getElementById("viewReport").submit();
			}
			
			function view(id) { 
				submitForm(id, "view");
			}
			function unlock(id) { 
				if(confirm('<spring:message code="mdrtb.unlockClosedReportMessage" />')) {
					submitForm(id, "unlock");
				}
			}
		</script>
	</body>
</html>
<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbFooter.jsp"%>