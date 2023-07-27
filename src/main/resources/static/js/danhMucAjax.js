$(document).ready(function(){
	
	// click event button Them moi danh muc
	$('.btnThemDanhMuc').on("click", function(event) {
		event.preventDefault();
		$('.danhMucForm #danhMucModal').modal();
		$('.danhMucForm #id').prop("disabled", true);
		$('#form').removeClass().addClass("addForm");
		$('#form #btnSubmit').removeClass().addClass("btn btn-primary btnSaveForm");
	});
	
	// event khi hide modal
	$('#danhMucModal').on('hidden.bs.modal', function () {
		$('#form').removeClass().addClass("danhMucForm");
		$('#form #btnSubmit').removeClass().addClass("btn btn-primary");
		resetText();
	});
	
	// reset text trong form
    function resetText(){
    	$("#id").val("");
    	$("#tenDanhMuc").val("");
    };
    

	ajaxGet(1);	
	
	// do get
	function ajaxGet(page){
		$.ajax({
			type: "GET",		
			url: "http://localhost:8080/laptopshop/api/danh-muc/all" + "?page=" + page,
			success: function(result){
				$.each(result.content, function(i, danhMuc){
					var danhMucRow = '<tr style="text-align: center;">' +
					                  '<td width="20%">' + danhMuc.id + '</td>' +
					                  '<td>' + danhMuc.tenDanhMuc + '</td>' +
					                  '<td>'+'<input type="hidden" value=' + danhMuc.id + '>'
					                     + '<button class="btn btn-primary btnCapNhatDanhMuc" >Cập nhật</button>' +
					                     '   <button class="btn btn-danger btnXoaDanhMuc">Xóa</button></td>'
				                      '</tr>';
					$('.danhMucTable tbody').append(danhMucRow);
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
	
    
//    // SUBMIT FORM
//    $(".customerForm").submit(function(event) {
//        event.preventDefault();
//        ajaxPost();
//        resetData();
//    });
    
    $(document).on('click', '.btnSaveForm', function (event) {
    	event.preventDefault();
		ajaxPost();
		resetData();
    });
    
	function ajaxPost(){
    	// PREPARE FORM DATA
    	var formData = { tenDanhMuc : $("#tenDanhMuc").val() } ;
    	// DO POST
    	$.ajax({
    		async:false,
			type : "POST",
			contentType : "application/json",
			url : "http://localhost:8080/laptopshop/api/danh-muc/save",
			data : JSON.stringify(formData),
            // dataType : 'json',
			success : function(response) {
				if(response.status == "success"){
					$('#danhMucModal').modal('hide');
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
    $(document).on("click",".btnCapNhatDanhMuc",function(){
		event.preventDefault();
		$('.danhMucForm #id').prop("disabled", true);
		var danhMucId = $(this).parent().find('input').val();
		$('#form').removeClass().addClass("updateForm");
		$('#form #btnSubmit').removeClass().addClass("btn btn-primary btnUpdateForm");
		var href = "http://localhost:8080/laptopshop/api/danh-muc/"+danhMucId;
		$.get(href, function(danhMuc, status) {
			$('.updateForm #id').val(danhMuc.id);
			$('.updateForm #tenDanhMuc').val(danhMuc.tenDanhMuc);
		});
		
		removeElementsByClass("error");
		
		$('.updateForm #danhMucModal').modal();
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
    			tenDanhMuc : $("#tenDanhMuc").val(),
    	}    	
    	// DO PUT
    	$.ajax({
    		async:false,
			type : "PUT",
			contentType : "application/json",
			url : "http://localhost:8080/laptopshop/api/danh-muc/update",
			data : JSON.stringify(formData),
            // dataType : 'json',
			success : function(response) {
				
				if(response.status == "success"){
					$('#danhMucModal').modal('hide');
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
    $(document).on("click",".btnXoaDanhMuc", function() {
		
		var danhMucId = $(this).parent().find('input').val();
		var workingObject = $(this);
		
		var confirmation = confirm("Bạn chắc chắn xóa danh mục này ?");
		if(confirmation){
		  $.ajax({
			  type : "DELETE",
			  url : "http://localhost:8080/laptopshop/api/danh-muc/delete/" + danhMucId,
			  success: function(resultMsg){
				 resetDataForDelete();
				 alert("Xóa thành công");
			  },
			  error : function(e) {
				 alert("Không thể xóa danh mục này ! Hãy kiểm tra lại");
				 console.log("ERROR: ", e);
			  }
		  });
		}
     });
    
    // reset table after post, put
    function resetData(){
    	$('.danhMucTable tbody tr').remove();
    	var page = $('li.active').children().text();
    	$('.pagination li').remove();
    	ajaxGet(page);
    };
    
    // reset table after delete
    function resetDataForDelete(){
       	var count = $('.danhMucTable tbody').children().length;
    	console.log("số cột " + count);
    	$('.danhMucTable tbody tr').remove();
    	var page = $('li.active').children().text();
    	$('.pagination li').remove();
    	console.log(page);
    	if(count == 1){    	
    		ajaxGet(page -1 );
    	} else {
    		ajaxGet(page);
    	}

    };
    
    // event khi click vào phân trang Danh mục
	$(document).on('click', '.pageNumber', function (event){
//		event.preventDefault();
		var page = $(this).text();	
    	$('.danhMucTable tbody tr').remove();
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