calculateOrder()
function calculateOrder()
{
	var element = document.getElementsByClassName("total");
	var res = 0;
	for (i = 0; i < element.length; i++) {
		res = res + parseInt(element[i].textContent);
	}
	var element2 = document.getElementById("ordertotal");
	
	resConvert = accounting.formatMoney(res);
	element2.innerHTML = resConvert+ " VND";
	var element3 = document.getElementById("tongGiaTri");
	element3.setAttribute("value",res);
	if(res == 0)
	{
		document.getElementById("submit").disabled = true;		
	}	
}
