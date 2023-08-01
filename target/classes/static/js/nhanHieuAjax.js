$(document).ready(function(){
	
	// click event button Them moi danh muc
	$('.btnThemNhanHieu').on("click", function(event) {
		event.preventDefault();
		$('.nhanHieuForm #nhanHieuModal').modal();
		$('.nhanHieuForm #id').prop("disabled", true);
		$('#form').removeClass().addClass("addForm");
		$('#form #btnSubmit').removeClass().addClass("btn btn-primary btnSaveForm");
	});
	
	$('#nhanHieuModal').on('hidden.bs.modal', function () {
		$('#form').removeClass().addClass("nhanHieuForm");
		$('#form #btnSubmit').removeClass().addClass("btn btn-primary");
		resetText();
	});
	
	// reset text trong form
    function resetText(){
    	$("#id").val("");
    	$("#tenHangSanXuat").val("");
    };
    

	ajaxGet(1);	
	
	// do get
	function ajaxGet(page){
		$.ajax({
			type: "GET",		
			url: "http://localhost:8080/laptopshop/api/nhan-hieu/all?page=" + page,
			success: function(result){
				$.each(result.content, function(i, nhanHieu){
					var nhanHieuRow = '<tr style="text-align: center;">' +
					                  '<td width="20%"">' + nhanHieu.id + '</td>' +
					                  '<td>' + nhanHieu.tenHangSanXuat + '</td>' +
					                  '<td>'+'<input type="hidden" value=' + nhanHieu.id + '>'
					                     + '<button class="btn btn-primary btnCapNhatNhanHieu" >Cập nhật</button>' +
					                     '   <button class="btn btn-danger btnXoaNhanHieu">Xóa</button></td>'
				                      '</tr>';
					$('.nhanHieuTable tbody').append(nhanHieuRow);
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
	
    
    $(document).on('click', '.btnSaveForm', function (event) {
    	event.preventDefault();
		ajaxPost();
		resetData();
    });
    
	function ajaxPost(){
    	// PREPARE FORM DATA
    	var formData = { tenHangSanXuat : $("#tenHangSanXuat").val() } ;
    	
    	// DO POST
    	$.ajax({
    		async:false,
			type : "POST",
			contentType : "application/json",
			url : "http://localhost:8080/laptopshop/api/nhan-hieu/save",
			data : JSON.stringify(formData),
            // dataType : 'json',
			success : function(response) {
				if(response.status == "success"){
					$('#nhanHieuModal').modal('hide');
					alert("Thêm thành công");
				} else {
			    	$('input').next().remove();
		             $.each(response.errorMessages, function(key,value){
		   	            $('input[id='+ key +']').after('<span class="error">'+value+'</span>');
		               });
				}
		    	
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		}); 
    };
   
    // click edit button
    $(document).on("click",".btnCapNhatNhanHieu",function(){
		event.preventDefault();
		$('.nhanHieuForm #id').prop("disabled", true);
		var nhanHieuId = $(this).parent().find('input').val();
		
		$('#form').removeClass().addClass("updateForm");
		$('#form #btnSubmit').removeClass().addClass("btn btn-primary btnUpdateForm");
		var href = "http://localhost:8080/laptopshop/api/nhan-hieu/" + nhanHieuId;
		$.get(href, function(nhanHieu, status) {
			$('.updateForm #id').val(nhanHieu.id);
			$('.updateForm #tenHangSanXuat').val(nhanHieu.tenHangSanXuat);
		});
		
		removeElementsByClass("error");
		
		$('.updateForm #nhanHieuModal').modal();
	});
    
    // put request
    
	$(document).on('click', '.btnUpdateForm', function (event) {
	   event.preventDefault();
	   ajaxPut();
	   resetData();
    });

    
    function ajaxPut(){
    	// PREPARE FORM DATA
    	var formData = {
    			id : $('#id').val(),
    			tenHangSanXuat : $("#tenHangSanXuat").val(),
    	}    	
    	// DO PUT
    	$.ajax({
    		async:false,
			type : "PUT",
			contentType : "application/json",
			url : "http://localhost:8080/laptopshop/api/nhan-hieu/update",
			data : JSON.stringify(formData),
            // dataType : 'json',
			success : function(response) {
				
				if(response.status == "success"){
					$('#nhanHieuModal').modal('hide');
					alert("Cập nhật thành công");
				} else {
			    	$('input').next().remove();
		            $.each(response.errorMessages, function(key,value){
		   	            $('input[id='+ key +']').after('<span class="error">'+value+'</span>');
		               });
				}
			},
			error : function(e) {
				alert("Error!")
				console.log("ERROR: ", e);
			}
		});
    };
 
	// delete request
    $(document).on("click",".btnXoaNhanHieu", function() {
		
		var nhanHieuId = $(this).parent().find('input').val();
		var workingObject = $(this);
		
		var confirmation = confirm("Bạn chắc chắn xóa nhãn hiệu này ?");
		if(confirmation){
		  $.ajax({
			  type : "DELETE",
			  url : "http://localhost:8080/laptopshop/api/nhan-hieu/delete/" + nhanHieuId,
			  success: function(resultMsg){
				 resetDataForDelete();
				 alert("Xóa thành công");
			  },
			  error : function(e) {
				 alert("Không thể xóa nhãn hiệu này, hãy kiểm tra lại");
				 console.log("ERROR: ", e);
			  }
		  });
		}
     });
    
    // reset table after post, put
    function resetData(){
    	$('.nhanHieuTable tbody tr').remove();
    	var page = $('li.active').children().text();
    	$('.pagination li').remove();
    	var count = $('.nhanHieuTable tbody').children().length;
    	ajaxGet(page);
    };
    
    // reset table after post, put
    function resetDataForDelete(){
    	var count = $('.nhanHieuTable tbody').children().length;
    	$('.nhanHieuTable tbody tr').remove();
    	var page = $('li.active').children().text();
    	$('.pagination li').remove();
    	if(count == 1){    	
    		ajaxGet(page -1 );
    	} else {
    		ajaxGet(page);
    	}

    };
    
    // event khi click vào phân trang Nhãn hiệu
	$(document).on('click', '.pageNumber', function (event){
//		event.preventDefault();
		var page = $(this).text();	
    	$('.nhanHieuTable tbody tr').remove();
    	$('.pagination li').remove();
    	ajaxGet(page);	
	});
    
    
    function removeElementsByClass(className){
        var elements = document.getElementsByClassName(className);
        while(elements.length > 0){
            elements[0].parentNode.removeChild(elements[0]);
        }
    }
});