<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%>
<%@ include file="../mdrtbHeader.jsp"%>

<form method="post">
	<h2><spring:message code="mdrtb.tb07uParameters" /></h2>
	
	<br/>
	
	<table>
		
		<tr>
		    <td align="right"><spring:message code="mdrtb.oblast" /></td>
			<td>
				<select name="oblast">
				    <option value=""></option>
					<c:forEach var="o" items="${oblasts}">
						<option value="${o.id}">${o.name}</option>
					</c:forEach>
				</select>
			</td>
		    
		    <tr>
		    <td align="right"><spring:message code="mdrtb.or" /></td>
		    </tr>
		
			<td align="right"><spring:message code="mdrtb.facility" /></td>
			<td>
				<select name="location">
				    <option value=""></option>
					<c:forEach var="loc" items="${locations}">
						<option value="${loc.id}">${loc.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><spring:message code="mdrtb.year" /></td>
			<td><input name="year" type="text" size="4"/></td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="right"><spring:message code="mdrtb.quarter" /></td>
			<td><input name="quarter" type="text" size="7"/></td>
		</tr>
		 <tr>
		    <td align="right"><spring:message code="mdrtb.or" /></td>
		    </tr>
		<tr>
			<td align="right"><spring:message code="mdrtb.month" /></td>
			<td><input name="month" type="text" size="7"/></td>
		</tr>

		<tr>
			<td align="right"></td>
			<td>&nbsp;<br/><input type="submit" value="<spring:message code="mdrtb.display" />"/></td>
		</tr>
	</table>

</form>

<%@ include file="../mdrtbFooter.jsp"%>