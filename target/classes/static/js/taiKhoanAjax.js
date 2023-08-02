$(document).ready(function() {
	
	// load first when coming page
	ajaxGet(1);	
	
	function ajaxGet(page){		
		var data = { roleName:$("#vaiTro").val()};
		$.ajax({
			type: "GET",	
			data: data,
			contentType : "application/json",
			url: "http://localhost:8080/api/account/all" + '?page=' + page,
			success: function(result){
				$.each(result.content, function(i, taiKhoan){
					var taiKhoanRow = '<tr>' +
					                  '<td>' + taiKhoan.id+ '</td>' +
					                  '<td>' + taiKhoan.lastName + '</td>' +
					                  '<td>' + taiKhoan.firstName + '</td>' +
					                  '<td>' + taiKhoan.email + '</td>' +
					                  '<td>' + taiKhoan.phone + '</td>'+
					                   '<td>' + taiKhoan.address + '</td>'+'<td>';
					
					  $.each(taiKhoan.role, function(i, vaiTro){
						  taiKhoanRow += vaiTro.roleName;
						  taiKhoanRow += "<br>";
					  });
					  
					  taiKhoanRow +='</td>' +
					                  '<td width="0%">'+'<input type="hidden" id="idTaiKhoan" value=' + taiKhoan.id + '>'+ '</td>'+
//					                  '<td><button class="btn btn-primary btnCapNhat" >Cập nhật</button></td>' + 
					                  '<td><button class="btn btn-danger btnXoa" >Xóa</button></td>';			;				                  
					$('.taiKhoanTable tbody').append(taiKhoanRow);

				});
								
				if(result.totalPages > 1 ){
					for(var numberPage = 1; numberPage <= result.totalPages; numberPage++) {
						var li = '<li class="page-item "><a class="pageNumber">'+numberPage+'</a></li>';
					    $('.pagination').append(li);
					};				
					
					// active page pagination
			    	$(".pageNumber").each(function(index){	
			    		if($(this).text() == page){
			    			$(this).parent().removeClass().addClass("page-item active");
			    		}
			    	});
				};
			},
			error : function(e){
				alert("Error: ",e);
				console.log("Error" , e );
			}
		});
	};
	
	// event khi click vào dropdown chọn danh mục thêm sản phẩm
	$('#vaiTro').mouseup(function() {
		var open = $(this).data("isopen");
		if (open) {
			resetData();
		}
		$(this).data("isopen", !open);
	});
	
	// click thêm tài khoản
    $(document).on('click', '.btnThemTaiKhoan', function (event) {
    	event.preventDefault();
        $("#taiKhoanModal").modal();
    });
    
    // xác nhận thêm tài khoản
    $(document).on('click', '#btnSubmit', function (event) {
    	event.preventDefault();
        ajaxPostTaiKhoan();
        resetData();
    });
    
    function ajaxPostTaiKhoan() {    	
    	var data = JSON.stringify($('.taiKhoanForm').serializeJSON());
    	console.log(data);
    	
    	 // do post
    	 $.ajax({
     		async:false,
 			type : "POST",
 			contentType : "application/json",
 			url : "http://localhost:8080/api/account/save",
 			enctype: 'multipart/form-data',
 			data : data,      
 			success : function(response) {
 				if(response.status == "success"){
 					$('#taiKhoanModal').modal('hide');
 					alert("Thêm thành công");
 				} else {
 			    	$('input').next().remove();
 		            $.each(response.errorMessages, function(key,value){
 		            	if(key != "id"){
 		   	                $('input[name='+ key +']').after('<span class="error">'+value+'</span>');
 		            	};
 		              }); 
 				}
 		    	
 			},
 			error : function(e) {
 				alert("Error!")
 				console.log("ERROR: ", e);
 			}
 		}); 
    }    
    
	// delete request
    $(document).on("click",".btnXoa", function() {
		
		var taiKhoanId = $(this).parent().prev().children().val();
		var confirmation = confirm("Bạn chắc chắn xóa tài khoản này ?");
		if(confirmation){
		  $.ajax({
			  type : "DELETE",
			  url : "http://localhost:8080/api/account/delete/" + taiKhoanId,
			  success: function(resultMsg){
				  resetData();
			  },
			  error : function(e) {
				 console.log("ERROR: ", e);
			  }
		  });
		}
     });
    
	// event khi ẩn modal form
	$('#taiKhoanModal').on('hidden.bs.modal', function(e) {
		e.preventDefault();
		$('.taiKhoanForm input').next().remove();
	});
    
    // reset table after post, put, filter
    function resetData(){   	
    	var page = $('li.active').children().text();
    	$('.taiKhoanTable tbody tr').remove();
    	$('.pagination li').remove();
        ajaxGet(page);
    };
    
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
    
    // remove element by class name
    function removeElementsByClass(className){
        var elements = document.getElementsByClassName(className);
        while(elements.length > 0){
            elements[0].parentNode.removeChild(elements[0]);
        }
    }
});