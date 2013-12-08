function displayDiv(divId,visible){
	if (visible){
		$("#"+divId).show();
	}else{
		$("#"+divId).hide();
	}
}

function toggleDiv(divId){
	$("#"+divId).toggle();
}