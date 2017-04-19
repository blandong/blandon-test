var express = require('express');
var app = express();

//get method
app.get('/', function(req, res){
	console.log("This is a get request");
	res.send("Hello Get");
});

app.post('/', function(req, res){
	console.log('This is a post request');
	res.send("Hello Post");
});

app.delete('/del_user', function(req, res){
	console.log('This is a delete request');
	res.send("Hello Delete");
});

app.get('/ab*cd', function(req, res){
	console.log('Get for /ab*cd');
	res.send("Hello get /ab*cd")
});


var server = app.listen(8081, function(){
	 var host = server.address().address
	 var port = server.address().port

	 console.log("Example app listening at http://%s:%s", host, port)
});