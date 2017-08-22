<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%>
<%@ include file="../mdrtbHeader.jsp"%>
<html>
	<head>
		<title>
			${reportName} - ${reportDate}
		</title>
	</head>
	<body>
		<div>
			${html}
		</div>
	</body>
</html>
<%@ include file="/WEB-INF/view/module/mdrtb/mdrtbFooter.jsp"%>