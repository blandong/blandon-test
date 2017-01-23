<!DOCTYPE html>
<html>
<body>

<div id="demo">
<h1>Load User from Server.</h1>
<button type="button" onclick="loadDoc()">Load User</button>
</div>

<br><br>

<div id="user"></div>


<br><br>

<div id="parseJson">
	Name: <input type ="text" id="name"></input><br/>
	Age: <input type ="text" id="age"></input>
</div>

<script>
function loadDoc() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = loadUser;
  xhttp.open("POST", "/user/populateUser.do", true);
  xhttp.send();
}

function loadUser(){
	if (this.readyState == 4 && this.status == 200) {
	      document.getElementById("user").innerHTML = this.responseText;
	      
	      var user = JSON.parse(this.responseText);
	      document.getElementById("name").value=user.name;
	      document.getElementById("age").value=user.age;
	      
	    }
}

</script>

</body>
</html>