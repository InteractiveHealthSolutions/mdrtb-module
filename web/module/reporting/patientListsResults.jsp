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
<a href="${pageContext.request.contextPath}/module/mdrtb/reporting/patientLists.form"><spring:message code="mdrtb.back" text="Back"/></a><br/><br/>
<spring:message code="mdrtb.oblast" text="Oblast"/>: ${oblast }<br/>
<spring:message code="mdrtb.district" text="District"/>: ${district}<br/>
<spring:message code="mdrtb.facility" text="Facility"/>: ${facility}<br/>
<spring:message code="mdrtb.year" text="Year"/>: ${year }<br/>
<spring:message code="mdrtb.quarter" text="Quarter"/>: ${quarter }<br/>
<spring:message code="mdrtb.month" text="Month"/>: ${month }<br/>
<spring:message code="mdrtb.list" text="List"/>: ${listName }<br/>
<br/>
${report}
</div>
<br/>
<a href="${pageContext.request.contextPath}/module/mdrtb/reporting/patientLists.form"><spring:message code="mdrtb.back" text="Back"/></a><br/><br/>

<%@ include file="/WEB-INF/template/footer.jsp" %>
