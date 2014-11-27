$(document).ready(function(){
	console.log(isMobile());
	$.ajax({url:window.location.origin+"/DWP/"+'ChessGameServlet',
		type:'POST',
		data:{begin:true},
		success: function(data){
			fillBoard(data);
		}
	});
});

// Fills board from server response
// Creates pieces in empty cells or updates pieces
// Removes old pieces
function fillBoard(gamestate) {
	var gs = JSON.parse(gamestate);
	var whoToGo = gs.whoToGo;
	var sp = null;
	var ep = null;
	var board = JSON.parse(gs.board);
	var pieces = [ ' ', // Empty cell
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

	for (var i = 0; i < 64; i++) {
		var x = Math.floor(i / 8);
		var y = i - (x * 8);
		var ID = "x" + x + "_y" + y;
		$("#" + ID).empty();
		if ((Math.floor(i / 8) + (i % 8)) % 2 == 1)
			$("#" + ID).addClass("dark");
		if(isMobile())
			$("#" + ID).addClass("mdrop");

		if (board[x][y] > 0) {
			td = document.createElement('div');
			$(td).addClass("drag")
			     .html(pieces[board[x][y]])
			     .appendTo($("#" + ID));
		}
	}

	// fill stats div
	updateStats(gamestate);

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
						makeMove(sp, ep, whoToGo, board);
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
				makeMove(sp, ep, whoToGo, board);
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

}


// Make call to server to validate move
function makeMove(sp,ep,whoToGo,board){
	$("#move").html( "<p>"+sp +" -> "+ep+"</p>");
	var move = [sp,ep];
	$.ajax({url:window.location.origin+"/DWP/"+'ChessGameServlet', 
			type:'POST', 						
			data:{	move:JSON.stringify(move),
					whoToGo:whoToGo,
					board:JSON.stringify(board)
			},
			success: function(data){
				gs = JSON.parse(data);
				if(gs.valid == true){
					fillBoard(data);
					document.getElementById("move").style.backgroundColor = "lime";
				}else{
					for(var i=0;i<64;i++){
						var x = Math.floor(i/8);
						var y = i-(x*8);
						var ID = "x"+x+"_y"+y;
						if($("#"+ID).children("div").length)
							$("#"+ID).children("div").animate({top:"0px", left:"0px"},1500);
						}
					document.getElementById("move").style.backgroundColor = "red";
				}
			}
	});
}

// Updates the game statistics portion of the page
function updateStats(gamestate){
	gs = JSON.parse(gamestate);
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

function isMobile(){
 if(/Android|iPad|iPhone/i.test(navigator.userAgent))	
	 return true;
 return false;
}