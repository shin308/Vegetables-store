function changePass()
{
	var old = document.getElementById("old").value;
	var new1 = document.getElementById("new1").value;
	var new2 = document.getElementById("new2").value;
	var flag = 0;
	if(old === null || old.match(/^ *$/) !== null)
	{
		flag = 1;
		document.getElementById("oldWarning").style.display = "block";
		document.getElementById("oldWarning").innerHTML = "Không được để trống";	
	}else{
        document.getElementById("oldWarning").style.display = "none";
    }

	if(new1 === null || new1.match(/^ *$/) !== null){
        flag = 1;
        document.getElementById("new1Warning").style.display = "block";
        document.getElementById("new1Warning").innerHTML = "Không được để trống";
    } else if(new1.length < 8 || new1.length > 32){
		flag = 1;
		document.getElementById("new1Warning").style.display = "block";
		document.getElementById("new1Warning").innerHTML = "Mật khẩu phải từ 8 đến 32 ký tự kí tự";
	} else{
	    document.getElementById("new1Warning").style.display = "none";
	}

    if(new2 === null || new2.match(/^ *$/) !== null){
        flag = 1;
        document.getElementById("new2Warning").style.display = "block";
        document.getElementById("new2Warning").innerHTML = "Không được để trống";
    } else if(new1 != new2)
	{
		flag = 1;
		document.getElementById("new2Warning").style.display = "block";
		document.getElementById("new2Warning").innerHTML = "Mật khẩu xác nhận không trùng mật khẩu mới";
	} else{
        document.getElementById("new2Warning").style.display = "none";
	}


	if(flag == 1)
	{
		return;
	}
	var object = new Object();
	object.oldPassword = old;
	object.newPassword = new1;
	data = JSON.stringify(object)
	$.ajax({
			type: "POST",	
			data: data,	
			contentType : "application/json",
			url: "http://localhost:8080/updatePassword",
			success: function(result){
				if(result.status == "old")
				{
					alert("Sai mật khẩu cũ");
//					window.location.reload();
					return;
				}else{
					alert("Mật khẩu đã thay đổi");
					window.location.href = "/logout";
				}
				
			},
			error : function(e){
				alert("Error: ",e);
				console.log("Error" , e );
			}
		});
}