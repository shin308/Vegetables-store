$(document).ready(function(){
	
	// click event button Cap nhật thông tin
	$('.btnCapNhatThongTin').on("click", function(event) {
		event.preventDefault();
		var nguoiDungId = $(".nguoiDungId").val();
		
		console.log(nguoiDungId);
		
		var href = "http://localhost:8080/api/profile/"+nguoiDungId;
		$.get(href, function(nguoiDung, status) {
			populate('.formCapNhat', nguoiDung);
		});
		
		$('.formCapNhat #capNhatModal').modal();
	});

    // fill input form với JSon Object
    function populate(frm, data) {
    	$.each(data, function(key, value){
    		 if(key != "id"){
    	        $('[name='+key+']', frm).val(value);
    		 }
    	});
    }
    
	$('.btnDoiMatKhau').on("click", function(event) {
		event.preventDefault();
		removeElementsByClass("error");
		$('.formDoiMatKhau #doiMKModal').modal();
	});
	
	$(document).on('click', '#btnXacNhanDoiMK', function(event) {
		event.preventDefault();
		removeElementsByClass("error");
		ajaxPostChangePass();
	});
	
	function ajaxPostChangePass() {
	    	// PREPATEE DATA
	    	 var data = $('.formDoiMatKhau').serializeFormJSON();   	 
	    	 // do post
	    	 $.ajax({
	     		async:false,
	 			type : "POST",
	 			contentType : "application/json",
	 			url : "http://localhost:8080/api/profile/doiMatKhau",
	 			data : JSON.stringify(data),
	 			success : function(response) {
	 				if(response.status == "success"){
	 					$('#doiMKModal').modal('hide');
	 					alert("Đổi mật khẩu thành công. Bạn phải đăng nhập lại để xác nhận");
	 					location.href = "http://localhost:8080/vegetable-shop/logout";
	 				} else {
	 			    	$('input').next().remove();
	 		            $.each(response.errorMessages, function(key,value){
	 		   	              $('input[name='+ key +']').after('<span class="error">'+value+'</span>');
	 		              });
	 				}	    	
	 			},
	 			error : function(e) {
	 				alert("Error!")
	 				console.log("ERROR: ", e);
	 			}
	 		}); 
	  }
	
    (function ($) {
        $.fn.serializeFormJSON = function () {

            var o = {};
            var a = this.serializeArray();
            $.each(a, function () {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        };
    })(jQuery);
    
    function removeElementsByClass(className){
        var elements = document.getElementsByClassName(className);
        while(elements.length > 0){
            elements[0].parentNode.removeChild(elements[0]);
        }
    }
})