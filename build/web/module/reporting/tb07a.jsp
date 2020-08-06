<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%>
<%@ include file="../mdrtbHeader.jsp"%>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>

<script type="text/javascript">
function fun1()
{
	var e = document.getElementById("oblast");
	var val = e.options[e.selectedIndex].value;
	var year = document.getElementById("year").value;
	var quarter =  "\"" + document.getElementById("quarter").value +  "\"";
	var month =  "\"" + document.getElementById("month").value +  "\"";
	if(val!="")
		window.location.replace("${pageContext.request.contextPath}/module/mdrtb/reporting/tb07.form?ob="+val+"&yearSelected="+year+"&quarterSelected="+quarter+"&monthSelected="+month)
}

function fun2()
{
	var e = document.getElementById("oblast");
	var val1 = e.options[e.selectedIndex].value;
	var e = document.getElementById("district");
	var val2 = e.options[e.selectedIndex].value;
	var year = document.getElementById("year").value;
	var quarter = "\"" + document.getElementById("quarter").value +  "\"";
	var month =  "\"" + document.getElementById("month").value +  "\"";
	if(val2!="")
		window.location.replace("${pageContext.request.contextPath}/module/mdrtb/reporting/tb07.form?loc="+val2+"&ob="+val1+"&yearSelected="+year+"&quarterSelected="+quarter+"&monthSelected="+month)
}

</script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</script>
<script>
$(document).ready(function(){
	$('#oblast').val(${oblastSelected});
	$('#district').val(${districtSelected});
	$('#year').val(${yearSelected});
	$('#quarter').val(${quarterSelected});
	$('#month').val(${monthSelected});

});
</script>

<form method="post">
	<h2><spring:message code="mdrtb.tb07Parameters" /></h2>
	
	<br/>
	
	<table>
		
		<table>
		
		<tr id="oblastDiv">
			<td align="right"><spring:message code="mdrtb.oblast" /></td>
			<td><select name="oblast" id="oblast" onchange="fun1()">
					<option value=""></option>
					<c:forEach var="o" items="${oblasts}">
						<option value="${o.id}">${o.name}</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr id="districtDiv">
			<td align="right"><spring:message code="mdrtb.district" /></td>
			<td><select name="district" id="district" onchange="fun2()">
					<option value=""></option>
					<c:forEach var="dist" items="${districts}">
						<option value="${dist.id}">${dist.name}</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr id="facilityDiv">
			<td align="right"><spring:message code="mdrtb.facility" /></td>
			<td><select name="facility" id="facility">
					<option value=""></option>
					<c:forEach var="f" items="${facilities}">
						<option value="${f.id}">${f.name}</option>
					</c:forEach>
			</select></td>
		<tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><spring:message code="dotsreports.year" /></td>
			<td><input name="year" id="year" type="text" size="4"/></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><spring:message code="dotsreports.quarter" /></td>
			<td><input name="quarter" id="quarter" type="text" size="7"/></td>
		</tr>
		 <tr>
		    <td align="right"><spring:message code="dotsreports.or" /></td>
		    </tr>
		<tr>
			<td align="right"><spring:message code="dotsreports.month" /></td>
			<td><input name="month" id="month" type="text" size="7"/></td>
		</tr>

		<tr>
			<td align="right"></td>
			<td>&nbsp;<br/><input type="submit" value="<spring:message code="mdrtb.display" />"/></td>
		</tr>
	</table>

</form>

<%@ include file="../mdrtbFooter.jsp"%>