<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>

$(document).ready(function(){
	
	$("#getUser").click(function(){
	    $.get("/user/getUser.do", function(data, status){
	        console.log("Data: " + data + "\nStatus: " + status);
	        $("#user").html(data);
	        var user = JSON.parse(data);
		    $("#name").val(user.name);
		    $("#age").val(user.age);
	    });
	});
});

</script>
</head>
<body>

<button id="getUser">Load user</button>
<br><br>

<div id="user"></div>

<br><br>

<div id="parseJson">
	Name: <input type ="text" id="name"></input><br/>
	Age: <input type ="text" id="age"></input>
</div>
</body>
</html>
