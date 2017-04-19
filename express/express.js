var express = require('express');
var app = express();

app.use(express.static('img'));

app.get('/', function(req, res){
	res.send("Hello 3");
});

var server = app.listen(8081, function(){
	var host = server.address().address;
	var port = server.address().port;
	
	console.log("host:"+host+", port: "+port)
});



//http://localhost:8081/test.png