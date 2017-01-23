<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<script>
	$(document).ready(function(){
		$("#btest").click(function(){
			 $.ajax({
				 type: "POST",
				 url: "/user/postUser.do",
				 data: $("#myForm").serialize(),
				 success: function(response){
		            $("#user").html(response);
		        },
		        error: function(response) {
		            $("#errorDiv").html(response);
		        }
			 
			 });
		});
		
	});
	
</script>
</head>
<body>
	<div id="errorDiv"></div>
	<div id="user"></div>

	<form action="/user/postUser.do" id="myForm" method="post">
		Name: <input type="text" name="name" id="name"></input><br/>
		Age:  <input type="text" name="age" id="age"></input><br/>
		
		<button type="button" id="btest">Submit</button>
	
	</form>

</body>
</html>
