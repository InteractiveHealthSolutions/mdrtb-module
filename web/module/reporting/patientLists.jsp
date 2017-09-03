<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp" %>

<%@ include file="/WEB-INF/template/headerMinimal.jsp" %>
<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js"/>
<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui-1.7.2.custom.min.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/css/redmond/jquery-ui-1.7.2.custom.css" />

<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.dimensions.pack.js"/>
<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.tooltip.js" />
<openmrs:htmlInclude file="/moduleResources/mdrtb/jquery.tooltip.css" />
<openmrs:htmlInclude file="/moduleResources/mdrtb/mdrtb.css"/>

<b class="boxHeader" style="margin:0px"><spring:message code="mdrtb.patientLists" text="Lists"/></b>
<div class="box" style="margin:0px;">
<br/>
	<form method="post">
	<spring:message code="mdrtb.oblast" />
	<select name="oblast">
				    <option value=""></option>
					<c:forEach var="o" items="${oblasts}">
						<option value="${o.id}">${o.name}</option>
					</c:forEach>
				</select>
			
		    <spring:message code="mdrtb.or" />
		    <spring:message code="mdrtb.district" />
				<select name="location">
				    <option value=""></option>
					<c:forEach var="loc" items="${locations}">
						<option value="${loc.id}">${loc.name}</option>
					</c:forEach>
				</select>
			<br/>
			<spring:message code="mdrtb.year" />&nbsp;&nbsp;&nbsp;&nbsp;<input name="year" type="text" size="4"/><br/>
			<spring:message code="mdrtb.quarter" /><input name="quarter" type="text" size="7"/></td>
			<spring:message code="mdrtb.or" />&nbsp;<spring:message code="mdrtb.month" />&nbsp;<input name="month" type="text" size="7"/>
		    <br/><br/><br/><br/>
		    
		    <table>
		    <tr>
		    <td><spring:message code="mdrtb.dotsCasesByBregistationGroup" /></td>
		    <td><button onClick="submit('dotsCasesByBregistationGroup')"><spring:message code="mdrtb.generate"/></button></td>
		    </tr>
		    </table>
		    
		    </form>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>
