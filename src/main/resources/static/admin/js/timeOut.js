$(document).ajaxComplete(function (event, xhr, settings) {
		if (xhr.getResponseHeader("session-status") == "timeOut"){
			top.location.href="/";
		}
});