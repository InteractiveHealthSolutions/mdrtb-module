<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>

<html>
	<head>
		<title>
			${reportName}
		</title>
		<style>
			th {vertical-align:middle; text-align:center;}
			th, td {font-size:smaller;}
		</style>
	</head>
	<body>
		<div>
			${html}
		</div>
		<button id="backBtn" name="backBtn" onclick="location.href='../reporting/viewClosedReports.form';" >Back</button>
		<button id="changeBtn" name="changeBtn" onclick="viewChanges();" >View Changes</button>
		
	<script>
		function viewChanges() {
			var oblast = "${oblast}";
			var location = "${location}";
			var year = "${year}";
			var quarter = "${quarter}";
			var month = "${month}";
			var reportName = "${reportName}";
			var reportDate = "${reportDate}";
			
			var form = document.createElement("FORM");
	
			form.setAttribute("id", "viewClosedReportChangesForm");
		    form.setAttribute("name", "viewClosedReportChangesForm");
		    form.setAttribute("method", "post");
		    form.setAttribute("action", "viewClosedReportChanges.form");
		    
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
		    input.setAttribute("value", location);
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
		    input.setAttribute("id", "reportName");
		    input.setAttribute("name", "reportName");
		    input.setAttribute("value", reportName);
		    form.appendChild(input);
		    
		    form.submit();
		}
	</script>
	
	</body>
</html>