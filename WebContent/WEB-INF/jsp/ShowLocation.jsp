<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>FCISquare Application</title>
<script src="http://maps.googleapis.com/maps/api/js">
	
</script>
<script>
	function getLocation() {
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(showPosition, showError);
		} else {

		}
	}

	function showPosition(position) {
		document.getElementById('lat').value = position.coords.latitude;
		document.getElementById('long').value = position.coords.longitude;
		var myLatLng = {
			lat : position.coords.latitude,
			lng : position.coords.longitude
		};
		var map = {
			center : myLatLng,
			zoom : 15,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		var map = new google.maps.Map(document.getElementById("googleMap"), map);
		var marker = new google.maps.Marker({
			position : myLatLng,
			map : map,
			title : 'You are here, ta2reban ensh2allah ya3ny :D :D '
		});

	}

	function showError(error) {
		switch (error.code) {
		case error.PERMISSION_DENIED:
			x.innerHTML = "User denied the request for Geolocation."
			break;
		case error.POSITION_UNAVAILABLE:
			x.innerHTML = "Location information is unavailable."
			break;
		case error.TIMEOUT:
			x.innerHTML = "The request to get user location timed out."
			break;
		case error.UNKNOWN_ERROR:
			x.innerHTML = "An unknown error occurred."
			break;
		}
	}
	google.maps.event.addDomListener(window, 'load', getLocation);
</script>
</head>
<body>

	<div id="googleMap" style="width: 500px; height: 380px;"></div>
	<br>
	<form action="updateMyLocation" method="post">

		<input id="lat" type="hidden" name="lat" /> <input id="long"
			type="hidden" name="long" /> <input type="submit"
			value="update my current position" />
	</form>
</body>
</html>