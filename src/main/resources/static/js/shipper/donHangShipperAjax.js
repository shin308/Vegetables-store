$(document).ready(function() {

	// load first when coming page
    ajaxGet(1);

	function ajaxGet(page){
		var data = { trangThai : $('#status').val(), idShipper: parseInt($('#idShipper').val()) }
		$.ajax({
			type: "GET",
			data: data,
			contentType : "application/json",
			url: "http://localhost:8080/api/shipper/orders/all" + '?page=' + page,
			success: function(result){
				$.each(result.content, function(i, orders){
					// tính giá trị đơn hàng\
					var sum = 0;
					var check = orders.orderStatus == "Hoàn thành" || orders.orderStatus == "Chờ duyệt";
					if(check){
						$.each(orders.orderDetailList, function(i, details){
							sum += details.quantity;
						});
					} else {
						$.each(orders.orderDetailList, function(i, details){
							sum += details.quantity * details.totalAmount;
						});
					}

					var donHangRow = '<tr class="item">' +
					                  '<td>' + orders.id+ '</td>' +
					                  '<td>' + orders.user + '</td>' +
					                  '<td>' + orders.orderStatus + '</td>' +
					                  '<td>' + sum + '</td>' +
					                  '<td>' + orders.orderDay + '</td>' +
					                  '<td>' + orders.deliveryDay + '</td>' +
					                  '<td>' + orders.receiveDay + '</td>' +
					                  '<td width="0%">'+'<input type="hidden" class="donHangId" value=' + orders.id + '>'+ '</td>'+
					                  '<td><button class="btn btn-warning btnChiTiet" data-bs-toggle="modal" data-bs-target="#chiTietModal">Chi Tiết</button>';
					     if(orders.orderStatus == "Đang giao"){
                         					    	 donHangRow += ' &nbsp;<button class="btn btn-warning btnCapNhat" data-bs-toggle="modal" data-bs-target="#capNhatTrangThaiModal">Cập Nhật</button> </td>';
                         					     }

					$('.donHangTable tbody').append(donHangRow);

					$('td').each( function(i){
						if ($(this).html() == 'null'){
							$(this).html('');
						}
					});
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

    // event khi click vào button Chi tiết đơn
	$(document).on('click', '.btnPhanCong', function (event){
		event.preventDefault();
		var donHangId = $(this).parent().prev().children().val();
		$("#donHangId").val(donHangId);
		console.log(donHangId);
		$("#phanCongModal").modal();
	});

	$(document).on('click', '#btnPhanCongSubmit', function (event) {
		var email = $("select[name=shipper]").val();
		var donHangId = $("#donHangId").val();
        console.log(donHangId);
		ajaxPostPhanCongDonHang(email, donHangId)

	});

	function ajaxPostPhanCongDonHang(email, donHangId) {

    	 $.ajax({
     		async:false,
 			type : "POST",
 			contentType : "application/json",
 			url : "http://localhost:8080/api/orders/assign?shipper="+email+"&donHangId="+donHangId,
 			enctype: 'multipart/form-data',

 			success : function(response) {
 				alert("Phân công đơn hàng thành công");
 				var trangThai = $('#status').val();
			    location.href = window.location.href;
 			},
 			error : function(e) {
 				alert("Error!")
 				console.log("ERROR: ", e);
 			}
 		});
    }

	$('#status').mouseup(function() {
    		var open = $(this).data("isopen");
    		if (open) {
    			resetData();
    		}
    		$(this).data("isopen", !open);
    	});

    // reset table after post, put, filter
    function resetData(){
    	var page = $('li.active').children().text();
    	$('.donHangTable tbody tr').remove();
    	$('.pagination li').remove();
        ajaxGet(page);
    };

    // event khi click vào phân trang Đơn hàng
	$(document).on('click', '.pageNumber', function (event){
//		event.preventDefault();
		var page = $(this).text();
    	$('.donHangTable tbody tr').remove();
    	$('.pagination li').remove();
    	ajaxGet(page);
	});

    // event khi click vào button Chi tiết đơn
	$(document).on('click', '.btnChiTiet', function (event){
		event.preventDefault();

		var donHangId = $(this).parent().prev().children().val();
//		console.log(donHangId);
		var href = "http://localhost:8080/api/shipper/orders/"+donHangId;
		$.get(href, function(order) {
			$('#maDonHang').text("Mã đơn hàng: "+ order.id);
			$('#hoTenNguoiNhan').text("Người nhận: "+ order.email);
			$('#sdtNhanHang').text("SĐT: "+ order.phone);
			$('#diaChiNhan').text("Địa chỉ: "+ order.address);
			$('#trangThaiDonHang').text("Trạng thái đơn: "+ order.orderStatus);
			$("#ngayDatHang").text("Ngày đặt: "+ order.orderDay);

			if(order.deliveryDay != null){
				$("#ngayShipHang").text("Ngày giao: "+ order.deliveryDay);
			}

			if(order.receiveDay != null){
				$("#ngayNhanHang").text("Ngày nhận: "+ order.receiveDay);
			}

			if(order.note != null){
				$("#ghiChu").html("<strong>Ghi chú</strong>:<br> "+ order.note);
			}

			if(order.user != null){
				$("#nguoiDat").html("<strong>Người đặt</strong>:  "+ order.user.firstName);
			}

			if(order.shipper != null){
				$("#shipper").html("<strong>Shipper</strong>: "+ order.shipper.firstName);
			}

			var check = order.orderStatus == "Hoàn thành" || order.orderStatus == "Chờ duyệt" ;
//			if(check){
//				$('.chiTietTable').find('thead tr').append('<th id="soLuongNhanTag" class="border-0 text-uppercase small font-weight-bold"> SỐ LƯỢNG NHẬN </th>');
//			}
			// thêm bảng:
			var sum = 0; // tổng giá trị đơn
			var stt = 1;
			$.each(order.orderDetailList, function(i, chiTiet){
				var chiTietRow = '<tr>' +
				'<td>' + stt + '</td>' +
                '<td>' + chiTiet.product.productName + '</td>' +
                '<td>' + chiTiet.totalAmount + '</td>'+
                '<td>' + chiTiet.quantity + '</td>';

				if(check){
//					chiTietRow += '<td>' + chiTiet.soLuongNhanHang + '</td>';
					sum += chiTiet.totalAmount;
					//* chiTiet.soLuongNhanHang;
				} else {
	                sum += chiTiet.totalAmount * chiTiet.quantity;
				}

				$('.chiTietTable tbody').append(chiTietRow);
                stt++;
	    	  });

			$("#tongTien").text("Tổng : "+ sum);
		});
		$("#chiTietModal").modal();
	});


    // event khi click vào nhấn phím vào ô tìm kiếm đơn hàng theo id
	$(document).on('keyup', '#searchById', function (event){
		event.preventDefault();
		var donHangId = $('#searchById').val();
		console.log(donHangId);
		if(donHangId != ''){
    	  $('.donHangTable tbody tr').remove();
    	  $('.pagination li').remove();
		  var href = "http://localhost:8080/api/orders/"+donHangId;
		  $.get(href, function(donHang) {
				// tính giá trị đơn hàng
			  var sum = 0;
			  var check = donHang.trangThaiDonHang == "Hoàn thành" || donHang.trangThaiDonHang == "Chờ duyệt";

				if(check){
					$.each(donHang.danhSachChiTiet, function(i, chiTiet){
						sum += chiTiet.donGia * chiTiet.soLuongNhanHang;
					});
				} else {
					$.each(donHang.danhSachChiTiet, function(i, chiTiet){
						sum += chiTiet.donGia * chiTiet.soLuongDat;
					});
				}

			 var donHangRow = '<tr>' +
             '<td>' + donHang.id+ '</td>' +
             '<td>' + donHang.hoTenNguoiNhan + '</td>' +
             '<td>' + donHang.trangThaiDonHang + '</td>' +
             '<td>' + sum + '</td>' +
             '<td>' + donHang.ngayDatHang + '</td>' +
             '<td>' + donHang.ngayGiaoHang + '</td>' +
             '<td>' + donHang.ngayNhanHang + '</td>' +
             '<td width="0%">'+'<input type="hidden" id="donHangId" value=' + donHang.id + '>'+ '</td>'+
             '<td><button class="btn btn-primary btnChiTiet" >Chi Tiết</button>';

			 if(donHang.trangThaiDonHang == "Đang chờ giao" || donHang.trangThaiDonHang == "Đang giao"){
		    	 donHangRow += ' &nbsp;<button class="btn btn-danger btnPhanCong">Phân công</button>';
		     } else if (donHang.trangThaiDonHang == "Chờ duyệt"){
		         donHangRow += ' &nbsp;<button class="btn btn-warning btnCapNhat" >Cập Nhật</button> </td>';
		     }

             $('.donHangTable tbody').append(donHangRow);
			 $('td').each( function(i){
				if ($(this).html() == 'null'){
					$(this).html('');
				}
			 });
		  });
		} else {
			resetData();
		}
	});

    // event khi click vào button cập nhật đơn
	$(document).on('click', '.btnCapNhat', function (event){
		event.preventDefault();
		var donHangId = $(this).parent().prev().children().val();
		$("#donHangId").val(donHangId);
		var href = "http://localhost:8080/api/shipper/orders/"+donHangId;
		$.get(href, function(order) {
			// thêm bảng:
			var stt = 1;
			$.each(order.orderDetailList, function(i, chiTiet){
				var chiTietRow = '<tr>' +
				'<td>' + stt + '</td>' +
                '<td>' + chiTiet.product.productName + '</td>' +
                '<td>' + chiTiet.totalAmount + '</td>'+
                '<td>' + chiTiet.quantity + '</td>'+
                '<td><input type="hidden" value="'+chiTiet.id+'" ></td>'
				 $('.chiTietCapNhatTable tbody').append(chiTietRow);
                stt++;
	    	  });
			var sum = 0;
			$.each(order.orderDetailList, function(i, chiTiet){
				sum += chiTiet.totalAmount;
				//* chiTiet.soLuongNhanHang;
			});
			$("#tongTienXacNhan").text("Tổng : "+ sum);
		});
		$("#capNhatTrangThaiModal").modal();
	});

    $(document).on('click', '#btnXacNhan', function (event) {
    	event.preventDefault();
    	ajaxPostCapNhatTrangThaiDon();
		resetData();
    });

    // post request cập nhật trạng thái đơn shipper
    	function ajaxPostCapNhatTrangThaiDon() {

       	     var listChiTietCapNhat = [] ;
    		 var table = $(".chiTietCapNhatTable tbody");
         	 table.find('tr').each(function (i) {
    		      var chiTietCapNhat = { idChiTiet : $(this).find("td:eq(4) input[type='hidden']").val() };
    		      listChiTietCapNhat.push(chiTietCapNhat);
    		 });


        	 var data = { idDonHang : $("#donHangId").val(),
        			      ghiChuShipper: $("#ghiChuShipper").val(),
        			      danhSachCapNhatChiTietDon: listChiTietCapNhat } ;
        	 console.log(data);
        	 $.ajax({
         		async:false,
     			type : "POST",
     			contentType : "application/json",
     			url : "http://localhost:8080/api/shipper/orders/update",
     			enctype: 'multipart/form-data',

    			data : JSON.stringify(data),
                // dataType : 'json',
    			success : function(response) {
    				$("#capNhatTrangThaiModal").modal('hide');
    				alert("Cập nhật giao đơn hàng thành công");
    			},
    			error : function(e) {
    				alert("Error!")
    				console.log("ERROR: ", e);
    			}
    		});
        }


	// event khi ẩn modal chi tiết
	$('#chiTietModal,#capNhatTrangThaiModal').on('hidden.bs.modal', function(e) {
		e.preventDefault();
		$("#chiTietForm p").html(""); // reset text thẻ p
		$("#capNhatTrangThaiForm h4").text("");
		$("#ghiChuAdmin").text("");
		$('.chiTietTable #soLuongNhanTag').remove();
		$('.chiTietTable tbody tr').remove();
		$('.chiTietCapNhatTable tbody tr').remove();
	});
});