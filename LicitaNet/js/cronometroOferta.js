
function initCronometro(data){
	var dataHora = data.split(" ");
	var anoMesDia = dataHora[0].split("-");
	var horaMinutoSegundo = dataHora[1].split(":");
	var newYear = new Date(); 
	newYear = new Date(parseInt(anoMesDia[0]), parseInt(anoMesDia[1]) - 1, parseInt(anoMesDia[2]), parseInt(horaMinutoSegundo[0]), parseInt(horaMinutoSegundo[1]), parseInt(horaMinutoSegundo[2].split(".")[0])); 
	$('#defaultCountdown').countdown({until: newYear});	
}

