$(function() {
	if (hasGame("game") == null | hasGame("play") == null) {
		window.location.replace("welcome.html");
	} else {
		if(hasGame("game").trim() == "" || hasGame("game").trim().length < 5 )
			window.location.replace("welcome.html?error=UG");
		if(hasGame("play")=="white")
			$("#opposition").html(window.location.href.toString().replace("white", "black"));
		else
			$("#opposition").html(window.location.href.toString().replace("black", "white"));
		$.ajax({
			url : "/TPC/"+'GameConnectServlet',
			type : 'GET',
			data : {
			begin : true,
			game : hasGame("game"),
			play : hasGame("play")	},
			success : function(data) {
				if(data.error == "file not found")
					window.location.replace("welcome.html?error=UG");
				else{
					fillBoard(data);
					lockBoard(data.whoToGo);
					if ($("#gameboard").css('pointer-events') == 'none') {
						$.ajax({
							url : "/TPC/"+'GameConnectServlet',
							type : 'POST',
							data : {
								game : hasGame("game"),
								play : hasGame("play")	},
							success : function(d) {
								if(d.timeout)
									timeOutError();
								else{
									fillBoard(d);	
								}
								},
							error : function() {
								console.log("Server failed to respond onload call");
							}
						});
					}
				}
			}
		});
	}
	;
});

// Redraws Board
function fillBoard(gs){
	var whoToGo = gs.whoToGo;
	var play = hasGame("play");
	var game = gs.game;
	var board = JSON.parse(gs.board);
	var pieces = [' ',       //Empty cell
	              '&#9817;', // White pawn
	              '&#9814;', // White rook
	              '&#9816;', 
	              '&#9815;', 
	              '&#9813;', 
	              '&#9812;', 
	              '&#9823;', 
	              '&#9820;', 
	              '&#9822;', 
	              '&#9821;', 
	              '&#9819;',  
	              '&#9818;' ];
	for(var i=0;i<64;i++){
		var x = Math.floor(i/8);
		var y = i-(x*8);
		var ID = "x"+x+"_y"+y;
		$("#"+ID).empty();
		if(isMobile())
			$("#" + ID).addClass("mdrop");
		
		if(board[x][y] > 0){
			td = document.createElement('div');
			$(td).addClass("drag")
				 .html(pieces[board[x][y]])
				 .appendTo($("#"+ID));
		}

	}
		//fill stats div 
		updateStats(gs);
		if (!isMobile()) {
			$(function() {
				$('.drag').draggable({
					containment : "#gameboard",
					scroll : false,
					start : function(event, ui) {
						$(this).attr('data-start', '');
						$(this).attr('data-start', $(this).parent().attr("id"));
					}
				});

				$('.drop').droppable({
					drop : function(event, ui) {
						sp = ui.draggable.attr("data-start");
						ep = $(this).attr("id");
						if (sp != ep) {
							makeMove(sp,ep,whoToGo,board,play,game);
						}
					}
				});
			});

		} else {
			$('.mdrop').off().on('click', function() {
				//first click in cell with piece
				if (!$(".active")[0] && $(this).children("div").length){
					console.log($(this).attr("id")+"fst");
					$(this).children("div").addClass("active");
					sp = $(this).attr("id");
				//second click is not the same move piece
				}else if (sp != $(this).attr("id") && sp != null){
					ep = $(this).attr("id");
					var start = $("#" + sp).position(); 
					var end = $(this).position(); 
					var left = end.left - start.left; 
					var top = end.top - start.top; 
					$("#"+sp).children("div").animate({top:top, left:left});
					makeMove(sp,ep,whoToGo,board,play,game);
					$("#" + sp).children("div").removeClass("active");
					sp = null;
					ep = null;
				//second click is same piece
				}else if ($(this).children("div").length){
					console.log($(this).attr("id")+"trd");
					$(this).children("div").removeClass("active");
					sp = null;
				}
			});
		}
	
	lockBoard(whoToGo);
}

// Make call to server to validate move
function makeMove(sp,ep,whoToGo,board,play,game){
	$("#move").html( "<p>"+sp +" -> "+ep+"</p>");
	var move = [sp,ep];
	$.ajax({url:'GameConnectServlet', 
			type:'POST', 						
			data:{	game:game,
					play:play,
					move:JSON.stringify(move),
					whoToGo:whoToGo,
					board:JSON.stringify(board)
			},
			success: function(data){
				if(JSON.parse(data.valid)){
					fillBoard(data);
					document.getElementById("move").style.backgroundColor = "lime";
					$.ajax({url:"/TPC/"+'GameConnectServlet',
				  		type:'POST',
				  		data:{game:data.game,play:play},
				  		success: function(d){
				  			if(d.timeout)
								timeOutError();
							else
								fillBoard(d);
				  		},
				  		error:function(){
				  			console.log("Server failed to respond makeMove call two");
				  		}
					});
				}else{
					for(var i=0;i<64;i++){
						var x = Math.floor(i/8);
						var y = i-(x*8);
						var ID = "x"+x+"_y"+y;
						if(document.getElementById(ID).childNodes.length>0)
							$(document.getElementById(ID).childNodes[0]).animate({top:"0px", left:"0px"},1500);
						}
					document.getElementById("move").style.backgroundColor = "red";
				}
			},
			error:function(){
				console.log("Server failed to respond makeMove call one");
			}
	});
}

function updateStats(gs){
	turn = (gs.whoToGo == 0 )? "white":"black";
	$("#whoToGo").html("whoToGo: "+turn);
	var board = JSON.parse(gs.board); 
	var game = "<h2>Current gamestate :</h2><table>";
	for(var i=0;i<8;i++){
		game += "<tr>";
		for(var j=0;j<8;j++){
			game += "<td>"+board[i][j]+"</td>";	
		}
		game += "</tr>";
	}
	game += "</table>";
	$("#gamestate").html(game);
}

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

function lockBoard(turn){
	var whoToGo = (turn < 1)? "white" : "black";
	var play = hasGame("play");
	if(whoToGo == play){
		$("#gameboard").css({'pointer-events':'auto'});	
		$('#status').html('Awaiting Your Move');
	}else{
		$("#gameboard").css({'pointer-events':'none'});
		$('#status').html('Waiting .........');
	};
};

function timeOutError(){
	$("#timeout").html("Waiting for player move has timed out please refresh your screen to continue playing.<br />");
	$("#timeout").css({"visibility":"visible"});
	$("#timeout").append("OR <br /><a href ="+window.location.href.toString()+">Click Here</a>");	
}

function isMobile(){
	 if(/Android|iPad|iPhone/i.test(navigator.userAgent))	
		 return true;
	 return false;
	}