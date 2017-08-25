<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%>

<html>
	<head>
		<title>
			${reportName}
		</title>
	</head>
	<body>
	
		<p>Changed Encounters - (${modifiedEncountersSize} Changes)</p><br/>
		<p>Changed Patients - (${modifiedPatientsSize} Changes)</p>
		<p>Changed Obs - (${modifiedObsSize} Changes)</p>
		
		<%-- 
		<p>Changed Encounters - (${modifiedEncountersSize} Changes)</p><br/>
		<c:forEach var="modifiedEncounter" items="${modifiedEncounters}" varStatus="modifiedEncounterLoop">
			Encounter Id: <c:out value="${modifiedEncounter.encounterId}"/>, Patient: <c:out value="${modifiedEncounter.patient}"/><br/>
		</c:forEach>
		
		<p>Changed Patients - (${modifiedPatientsSize} Changes)</p><br/>
		<c:forEach var="modifiedPatient" items="${modifiedPatients}" varStatus="modifiedPatientLoop">
			Patient Id: <c:out value="${modifiedPatient.patientId}"/>, Patient Name: <c:out value="${modifiedPatient.person.personName}"/><br/>
		</c:forEach>
		
		<p>Changed Obs - (${modifiedObsSize} Changes)</p><br/>
		<c:forEach var="modifiedObservation" items="${modifiedObs}" varStatus="modifiedObsLoop">
			Obs Id: <c:out value="${modifiedObservation.obsId}"/>, Obs Name: <c:out value="${modifiedObservation.obsName}"/><br/>
		</c:forEach> 
		--%>
	</body>
</html>