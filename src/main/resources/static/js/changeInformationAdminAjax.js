function changeInformation()
{
	var firstName = document.getElementById("firstName").value;
	var lastName = document.getElementById("lastName").value;
	var phone = document.getElementById("phone").value;
	var address = document.getElementById("address").value;
	var flag = 0;
	if(firstName === null || firstName.match(/^ *$/) !== null)
	{
		flag = 1;
		document.getElementById("firstNameWarning").style.display = "block";
		document.getElementById("firstNameWarning").innerHTML = "Tên không được để trống";
	}else{
	    document.getElementById("firstNameWarning").style.display = "none";
	}

	if(lastName === null || lastName.match(/^ *$/) !== null){
    	flag = 1;
    	document.getElementById("lastNameWarning").style.display = "block";
    	document.getElementById("lastNameWarning").innerHTML = "Họ không được để trống";
    }else{
        document.getElementById("lastNameWarning").style.display = "none";
    }

//	if(phone === null || phone.match(/^ *$/) !== null)
//	{
//		flag = 1;
//		document.getElementById("phoneWarning").innerHTML = "Số điện thoại không được để trống";
//	}else{
//        document.getElementById("phoneWarning").remove();
//    }

	if(address === null || address.match(/^ *$/) !== null){
		flag = 1;
		document.getElementById("addressWarning").style.display = "block";
		document.getElementById("addressWarning").innerHTML = "Địa chỉ không được để trống";
	}else{
        document.getElementById("addressWarning").style.display = "none";
    }

	var pat = "[0-9]+{9,10}";
	if(phone === null || phone.match(/^ *$/) !== null)
    	{
    		flag = 1;
    		document.getElementById("phoneWarning").style.display = "block";
    		document.getElementById("phoneWarning").innerHTML = "Số điện thoại không được để trống";
    } else if(!/^([0-9]{10})$/.test(phone)){
		flag = 1;
		document.getElementById("phoneWarning").style.display = "block";
		document.getElementById("phoneWarning").innerHTML = "Hãy nhập đúng số điện thoại";
	}else{
	    document.getElementById("phoneWarning").style.display = "none";
	}

	if(flag == 1)
	{
		return;
	}
	var send = new Object();
	send.firstName = firstName;
	send.lastName = lastName;
	send.phone = phone;
	send.address = address;
	var data = JSON.stringify(send)
	$.ajax({
			type: "POST",
			data: data,
			contentType : "application/json",
			url: "http://localhost:8080/updateInfo",
			success: function(result){
				alert("Thông tin đã cập nhật");
				window.location.href = "/admin/profile";
			},
			error : function(e){
				alert("Error: ",e);
				console.log("Error" , e );
			}
		});

}