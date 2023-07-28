function changeInformation()
{
	var name = document.getElementById("name").value;
	var phone = document.getElementById("phone").value;
	var address = document.getElementById("address").value;
	var flag = 0;
	if(name.length == 0)
	{
		flag = 1;
		document.getElementById("nameWarning").innerHTML = "Tên không được để trống";	
	}
	if(phone.length == 0)
	{
		flag = 1;
		document.getElementById("phoneWarning").innerHTML = "Số điện thoại không được để trống";
	}
	if(address.length == 0)
	{
		flag = 1;
		document.getElementById("addressWarning").innerHTML = "Địa chỉ không được để trống";	
	}
	var pat = "[0-9]+{9,10}"
	if(!/^([0-9]{9,10})$/.test(phone))
	{
		flag = 1;
		document.getElementById("phoneWarning").innerHTML = "Hãy nhập số điện thoại từ 9-10 số";
	}
	if(flag == 1)
	{
		return;
	}
	var send = new Object();
	send.hoTen = name;
	send.soDienThoai = phone;
	send.diaChi = address;
	var data = JSON.stringify(send)
	$.ajax({
			type: "POST",	
			data: data,	
			contentType : "application/json",
			url: "http://localhost:8080/laptopshop/updateInfo",
			success: function(result){
				alert("Thông tin đã cập nhật");
				window.location.href = "/laptopshop/account";
			},
			error : function(e){
				alert("Error: ",e);
				console.log("Error" , e );
			}
		});
	
}