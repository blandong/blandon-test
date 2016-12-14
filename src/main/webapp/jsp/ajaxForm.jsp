<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/extjs/6.0.0/ext-all.js"></script>
</head>
<body>
<!-- 
<script type="text/javascript" src="/script/ext-2.0/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="/script/ext-2.0/build/debug-min.js"></script>
 
<script type="text/javascript" src="/script/ext/ext-all.js"></script>
-->
<script type="text/javascript">
Ext.onReady(
	      function() {
	        Ext.QuickTips.init();
	        new Ext.Button({
	            handler: addRemoteAttribute,
	            text: 'Submit Button',
	            renderTo: 'ADD_REMOTE_ATTRIBUTE'
	          }) 
	      }
)

function test(){
    alert('You clicked the button!');
}

function submitForm(){
   var form= this.up('form').getForm();
   var userName = form.findField("userName").getValue();
   console.log("user name is: "+ userName);
   
   if (form.isValid()) {
       //create an AJAX request
        Ext.Ajax.request({
            url : '/user.do',
            method:'POST', 
            params : {
                formData: Ext.encode(form.getValues()),
                userName: userName
                
            },
            scope : this,
            //method to call when the request is successful
            success : onSuccess,
            //method to call when the request is a failure
            failure : onFailure
        }); 
    }
}

function onSuccess(){
	alert("Great, you did it!!!");
}

function onFailure(){
	alert("Oops, you failed it!!!");
}

function addRemoteAttribute(){

	Ext.create('Ext.window.Window', {
	    title: 'First Window',
	    height: 700,
	    width: 400,    
	    items: Ext.create('Ext.form.Panel', {
	    renderTo: document.body,
	    height: 800,
	    width: 300,
	    bodyPadding: 10,
	    defaultType: 'textfield',
	    items: [
	        {
	            fieldLabel: 'SP Attribute Name',
	            name: 'spAttributeName'
	        },
	        {
	            fieldLabel: 'Username',
	            name: 'userName'
	        },
	        {
	            inputType: 'password',
	            fieldLabel: 'Password',
	            name: 'password'
	        },
	        {
	            fieldLabel: 'Url',
	            name: 'url'
	        },
	        {
	            fieldLabel: 'AcceptHeaders',
	            name: 'acceptHeaders'
	        },
	        {
	            fieldLabel: 'ContentType',
	            name: 'contentType'
	        },
	        {
	            fieldLabel: 'Timeout',
	            name: 'timeout'
	        },
	        {
	            xtype: 'textareafield',
	            fieldLabel: 'Request',
	            name: 'request'
	        },
	        {
	            xtype: 'textareafield',
	            fieldLabel: 'Response',
	            name: 'response'
	        },
	        Ext.create('Ext.Button', {
	            text: 'Test',
	            handler: test,
	            margin: '10 10 10 10'
	        }),
	        
	      Ext.create('Ext.Button', {
	        text: 'Submit',
	        handler: submitForm,
	        margin: '10 10 10 10'
	        })
	    ]
	})
	}).show();
}


</script>

<form>
	<div id="ADD_REMOTE_ATTRIBUTE"></div>
</form>


</body>
</html>