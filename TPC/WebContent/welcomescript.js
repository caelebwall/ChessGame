$(function(){
	errorCheck();
	$("#newGame").click(function(){
		$.ajax({url:window.location.origin+"/TPC/"+'GameConnectServlet',
	  		type:'GET',
	  		data:{},
	  		success: function(d){
	  			console.log(d);
	  			window.location.replace("connect.html?game="+d.game+"&play="+d.play);
	  		},
	  		error:function(){
	  			console.log("Server failed to respond newGame call");
	  		}
		});	
	});
});

function hasGame(attr){
	var href = decodeURIComponent($(location).attr('href'));
	var game = null;
	if(href.indexOf("?")>0){
		var parameters = href.slice(href.indexOf("?")+1).split("&");
		$.each(parameters, function(k,v){
			if(v.split("=")[0] == attr)
				game = v.split("=")[1];
		});
		return game;
	};
};

function errorCheck(){
	var error = hasGame("error");
	if(error == "UG")
		$("#error").html("No Matching Game Found");

}