
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="script/jquery.maskAndUnmask.js" type="text/javascript" charset="utf-8"></script>
<script language="javascript">
var $jq=jQuery.noConflict(); 
$jq(document).ready(function(){
	var theForm = jQuery("#indexForm");
	password = theForm.find("#password");
	password.maskUnmask();
})
</script>

<form id="indexForm" method="post" action="/user/save.do?name1=test1">

	<table>
		<tr>
			<td>Username:</td>
			<td><input name="name" type="text"/>
		</tr>
		
		<tr>
			<td>Age: </td>
			<td><input name="age" type="text"></td>
		</tr>
		
		<tr>
			<td><input id="show-password" name="show-password" type="checkbox" /></td>
			<td>Show the password</td>
		</tr>
		
		<tr>
			<td>Password:</td>
			<td><input name="password" type="password" data-typetoggle='#show-password'></td>
		</tr>
		
		<tr align="center">
			<td><input type="submit" name="submit" value="Submit"></td>
		</tr>
		
	</table>
	
	
	
	

</form>