<%@ include file="/WEB-INF/view/module/mdrtb/include.jsp"%>

<html>
	<head>
		<title>
			TB-08u
		</title>
	</head>
	<body>
		<h2>
			View All TB-08 (FAST) Reports
		</h2>
		<table border="1" id="tb08u">
			<thead>
			<tr>
				<th>Oblast</th>
				<th>District</th>
				<th>Year</th>
				<th>Quarter</th>
				<th>Month</th>
				<th colspan="3"></th>
			</tr>
			</thead>
			<tbody id="tbody">
			</tbody>
		</table>
		<form id="viewReport" method="post">
			<input type="hidden" id="oblast" name="oblast" />
			<input type="hidden" id="location" name="location" />
			<input type="hidden" id="year" name="year" />
			<input type="hidden" id="quarter" name="quarter" />
			<input type="hidden" id="month" name="month" />
		</form>
		<script>
			var tbody = document.getElementById("tbody");
			var oblastLocationIds = []; var oblastLocationNames = [];
			<c:forEach var="oblast" items="${oblasts}" varStatus="oblastLoop">
				var locationIds = []; var locationNames = [];
				<c:forEach var="location" items="${oblastLocations[oblastLoop.index]}" varStatus="oblastLocationLoop">
					locationIds.push("${location.id}"); locationNames.push("${location.name}");
					var row = tbody.insertRow(-1);

					//OBLAST
					var cell = row.insertCell(-1);
					cell.id="oblast_${oblastLoop.index}_${oblastLocationLoop.index}";
					cell.innerHTML = "${oblast.name}";
					
					//LOCATION
					var cell = row.insertCell(-1);
					cell.id="location_${oblastLoop.index}_${oblastLocationLoop.index}";
					cell.innerHTML = "${location.name}";

					//YEAR
					var cell = row.insertCell(-1);
					var html = "<select id='yearInput_${oblastLoop.index}_${oblastLocationLoop.index}' style='width: 100%;'><option value=''></option>"; for (var i=1989; i<2021; i++) { html += "<option value='"+i+"'>"+i+"</option>"; } html += "</select>";
					cell.id="year_${oblastLoop.index}_${oblastLocationLoop.index}";
					cell.innerHTML = html; 

					//QUARTER
					var cell = row.insertCell(-1);
					cell.id="quarter_${oblastLoop.index}_${oblastLocationLoop.index}";
					cell.innerHTML = "<select id='quarterInput_${oblastLoop.index}_${oblastLocationLoop.index}' style='width: 100%;'><option value=''></option><option value='1'>1</option><option value='2'>2</option><option value='3'>3</option><option value='4'>4</option></select>";

					//MONTH
					var cell = row.insertCell(-1);
					cell.id="month_${oblastLoop.index}_${oblastLocationLoop.index}";
					cell.innerHTML = "<input id='monthInput_${oblastLoop.index}_${oblastLocationLoop.index}' type='month'/>";

					//VIEW
					var cell = row.insertCell(-1);
					cell.id="view_${oblastLoop.index}_${oblastLocationLoop.index}";
					cell.innerHTML = "<button id='viewBtn_${oblastLoop.index}_${oblastLocationLoop.index}' onclick='view(\"${oblastLoop.index}_${oblastLocationLoop.index}\");'>View</button>";

					//CLOSE
					var cell = row.insertCell(-1);
					cell.id="close_${oblastLoop.index}_${oblastLocationLoop.index}";
					cell.innerHTML = "<button id='closeBtn_${oblastLoop.index}_${oblastLocationLoop.index}'>Close</button>";

					//UNLOCK
					var cell = row.insertCell(-1);
					cell.id="unlock_${oblastLoop.index}_${oblastLocationLoop.index}";
					cell.innerHTML = "<button id='unlockBtn_${oblastLoop.index}_${oblastLocationLoop.index}'>Unlock</button>";
				</c:forEach>
				oblastLocationIds.push("${oblast.id}"); oblastLocationIds.push(locationIds);
				oblastLocationNames.push("${oblast.name}"); oblastLocationNames.push(locationNames);
			</c:forEach>

			function maxLengthCheck(year) { if (year.value.length > 4) { year.value = year.value.slice(0,4); } }

			function view(id) { var message = "";
				//Error Check
				if(document.getElementById("yearInput_"+id).value === "") { message += "Year is required.\n"; }
				if(document.getElementById("quarterInput_"+id).value === "" && document.getElementById("monthInput_"+id).value === "") { message += "Anything from Quarter or Month is required."; }
				else if(document.getElementById("quarterInput_"+id).value !== "" && document.getElementById("monthInput_"+id).value !== "") { message += "Please make anything from Quarter or Month empty."; }

				//Success
				if(message === "") {
					document.getElementById("oblast").value = oblastLocationIds[oblastLocationNames.indexOf(document.getElementById("oblast_"+id).innerHTML)];
					document.getElementById("location").value = (oblastLocationIds[oblastLocationNames.indexOf(document.getElementById("oblast_"+id).innerHTML) + 1])[(oblastLocationNames[oblastLocationNames.indexOf(document.getElementById("oblast_"+id).innerHTML) + 1]).indexOf(document.getElementById("location_"+id).innerHTML)];
					document.getElementById("year").value = document.getElementById("yearInput_"+id).value; document.getElementById("quarter").value = document.getElementById("quarterInput_"+id).value; 
					document.getElementById("month").value = (document.getElementById("monthInput_"+id).value).substring(5); 
					document.getElementById("viewReport").submit();
				} else { alert(message); }
			}
			

		</script>
	</body>
</html>