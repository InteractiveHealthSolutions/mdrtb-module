<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%>
<%@ include file="../mdrtbHeader.jsp"%>

<html>
	<head>
		<title>${reportName} Changes</title>

		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/moduleResources/mdrtb/css/datatables.min.css"/>
		<script type="text/javascript" src="<%= request.getContextPath() %>/moduleResources/mdrtb/jquery/jquery.dataTables.min.js"></script>
		<script>
			$(document).ready(function() {
				$('#encountersTable').DataTable({
					"bFilter": false,
					"ordering": false,
					"paging" : false,
					"lengthChange" : false,
					"searching" : false,
					"ordering" : false,
					"info" : false,
					"autoWidth" : false
				});
				$('#patientsTable').DataTable({
					"bFilter": false,
					"ordering": false,
					"paging" : false,
					"lengthChange" : false,
					"searching" : false,
					"ordering" : false,
					"info" : false,
					"autoWidth" : false
				});
				$('#obsTable').DataTable({
					"bFilter": false,
					"ordering": false,
					"paging" : false,
					"lengthChange" : false,
					"searching" : false,
					"ordering" : false,
					"info" : false,
					"autoWidth" : false
				});
			});
		</script>
	</head>
	<body>
		<h4>Encounters Changed: ${encounterDataSize}</h4>
		<c:if test="${encounterDataSize > 0 }">
			<table class="display" id="encountersTable" border="1">
				<thead>
					<tr>
						<th>Id</th>
						<th>Type Id</th>
						<th>Type Name</th>
						<th>Date Enrolled</th>
						<th>Date Created</th>
						<th>Date Changed</th>
						<th>Patient Id</th>
						<th>Patient Name</th>
						<th>Obs Data</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="encounters" items="${encounterData}" varStatus="encountersLoop">
						<tr>
							<c:forEach var="encounter" items="${encounters}" varStatus="encounterLoop">
								<td>${encounter}</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if> 
		
		<h4>Patients Changed: ${patientDataSize}</h4>
		<c:if test="${patientDataSize > 0 }">
			<table class="display" id="patientsTable" border="1">
				<thead>
					<tr>
						<th>Id</th>
						<th>Name</th>
						<th>Age</th>
						<th>Date Created</th>
						<th>Date Changed</th>
						<th>Encounter Id</th>
						<th>Encounter Type Id</th>
						<th>Encounter Type name</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="patients" items="${patientData}" varStatus="patientsLoop">
						<tr>
							<c:forEach var="patient" items="${patients}" varStatus="patientLoop">
								<td>${patient}</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		
		<h4>Obs Changed: ${obsDataSize}</h4>
		<c:if test="${obsDataSize > 0 }">
			<table class="display" id="obssTable" border="1">
				<thead>
					<tr>
						<th>Id</th>
						<th>Concept Name</th>
						<th>Date Enrolled</th>
						<th>Date Created</th>
						<th>Date Changed</th>
						<th>Encounter Id</th>
						<th>Encounter Type Id</th>
						<th>Encounter Type Name</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="obs" items="${obsData}" varStatus="obssLoop">
						<tr>
							<c:forEach var="obs" items="${obss}" varStatus="obsLoop">
								<td>${obs}</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</body>
</html>

<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbFooter.jsp"%>
