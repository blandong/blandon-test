var express = require('express');
var cookieParser = require('cookie-parser');

var app = express();
app.use(cookieParser());

app.get('/', function(req, res){
	console.log("Cookies: ", req.cookies);
	res.send("cookies retrieved");
});

var server = app.listen(8081, function(){
	 var host = server.address().address
	 var port = server.address().port
	 console.log("Example app listening at http://%s:%s", host, port)
})
