function httpJump(url){
	window.location.href=url;
}

function verifyClass(element,merrorMessage){
	$(element).val("");
	$(element).attr("placeholder",merrorMessage);
	$(element).css("border","1px solid red");
}