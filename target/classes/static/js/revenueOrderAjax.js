$(document).ready(function() {

    ajaxGetDay();

    $(document).on('click', '#buttons', function (event){
        var target = event.target;
        if(target.id == 'revenueDay'){
            $('.revenue').remove();
            $('#revenueWeek').removeClass("btn-fill").addClass("btn-bd");
            $('#revenueMonth').removeClass("btn-fill").addClass("btn-bd");
            $('#revenueDay').removeClass("btn-bd").addClass("btn-fill");
            ajaxGetDay();
        }else if(target.id == 'revenueWeek'){
            $('.revenue').remove();
            $('#revenueDay').removeClass("btn-fill").addClass("btn-bd");
            $('#revenueMonth').removeClass("btn-fill").addClass("btn-bd");
            $('#revenueWeek').removeClass("btn-bd").addClass("btn-fill");
            ajaxGetWeek();
        }else{
            $('.revenue').remove();
            $('#revenueWeek').removeClass("btn-fill").addClass("btn-bd");
            $('#revenueDay').removeClass("btn-fill").addClass("btn-bd");
            $('#revenueMonth').removeClass("btn-bd").addClass("btn-fill");
            ajaxGetMonth();
        }
    });
    function ajaxGetDay(){
        var myChart1 = '<div class="revenue">'
                    + '<canvas id="myChart1" width="50px" height="300px"></canvas>'
                    + '<h4 style="text-align: center; padding-right: 10%">Biểu đồ tổng giá trị đơn hàng hoàn thành theo ngày</h4>'
                    + '</div>';
        $('div.statistic').append(myChart1);

        var data = [];
        		var label = [];
        		var dataForDataSets = [];

        		$.ajax({
        			async : false,
        			type : "GET",
        			data : data,
        			contentType : "application/json",
        			url : "http://localhost:8080/api/orders/statistic-revenue-day",
        			success : function(data) {
        				for (var i = 0; i < data.length; i++) {
        					label.push(data[i][0] + "/" + data[i][1]);
        					dataForDataSets.push(data[i][2]);
        				}
        			},
        			error : function(e) {
        				alert("Error: ", e);
        				console.log("Error", e);
        			}
        		});

        		var canvas = document.getElementById('myChart1');

        		data = {
        			labels : label,
        			datasets : [ {
        				label : "Tổng giá trị (VNĐ)",
        				backgroundColor : "#5CB85C",
        				borderColor : "#5CB85C",
        				borderWidth : 2,
        				hoverBackgroundColor : "#0043ff",
        				hoverBorderColor : "#0043ff",
        				data : dataForDataSets,
        			} ]
        		};
        		var option = {
        			scales : {
        				yAxes : [ {
        					stacked : true,
        					gridLines : {
        						display : true,
        						color : "rgba(255,99,132,0.2)"
        					}
        				} ],
        				xAxes : [ {
        					barPercentage: 0.5,
        					gridLines : {
        						display : false
        					}
        				} ]
        			},
        			maintainAspectRatio: false,
        			legend: {
        	            labels: {
        	                // This more specific font property overrides the global property
        	                fontSize: 20
        	            }
        			}
        		};

        		var myBarChart = Chart.Bar(canvas, {
        			data : data,
        			options : option
        		});
    }

    function ajaxGetWeek(){
            var myChart2 = '<div class="revenue">'
                        + '<canvas id="myChart2" width="50px" height="300px"></canvas>'
                        + '<h4 style="text-align: center; padding-right: 10%">Biểu đồ tổng giá trị đơn hàng hoàn thành theo tuần</h4>'
                        + '</div>';
            $('div.statistic').append(myChart2);
            var data = [];
            		var label = [];
            		var dataForDataSets = [];

            		$.ajax({
            			async : false,
            			type : "GET",
            			data : data,
            			contentType : "application/json",
            			url : "http://localhost:8080/api/orders/statistic-revenue-week",
            			success : function(data) {
            				for (var i = 0; i < data.length; i++) {
            					label.push(data[i][0] + "/" + data[i][1]);
            					dataForDataSets.push(data[i][2]);
            				}
            			},
            			error : function(e) {
            				alert("Error: ", e);
            				console.log("Error", e);
            			}
            		});

            		var canvas = document.getElementById('myChart2');

            		data = {
            			labels : label,
            			datasets : [ {
            				label : "Tổng giá trị (VNĐ)",
            				backgroundColor : "#5CB85C",
            				borderColor : "#5CB85C",
            				borderWidth : 2,
            				hoverBackgroundColor : "#0043ff",
            				hoverBorderColor : "#0043ff",
            				data : dataForDataSets,
            			} ]
            		};
            		var option = {
            			scales : {
            				yAxes : [ {
            					stacked : true,
            					gridLines : {
            						display : true,
            						color : "rgba(255,99,132,0.2)"
            					}
            				} ],
            				xAxes : [ {
            					barPercentage: 0.5,
            					gridLines : {
            						display : false
            					}
            				} ]
            			},
            			maintainAspectRatio: false,
            			legend: {
            	            labels: {
            	                // This more specific font property overrides the global property
            	                fontSize: 20
            	            }
            			}
            		};

            		var myBarChart = Chart.Bar(canvas, {
            			data : data,
            			options : option
            		});
        }

    function ajaxGetMonth(){
                var myChart3 = '<div class="revenue">'
                            + '<canvas id="myChart3" width="50px" height="300px"></canvas>'
                            + '<h4 style="text-align: center; padding-right: 10%">Biểu đồ tổng giá trị đơn hàng hoàn thành theo tháng</h4>'
                            + '</div>';
                $('div.statistic').append(myChart3);
                var data = [];
                		var label = [];
                		var dataForDataSets = [];

                		$.ajax({
                			async : false,
                			type : "GET",
                			data : data,
                			contentType : "application/json",
                			url : "http://localhost:8080/api/orders/statistic-revenue-month",
                			success : function(data) {
                				for (var i = 0; i < data.length; i++) {
                					label.push(data[i][0] + "/" + data[i][1]);
                					dataForDataSets.push(data[i][2]);
                				}
                			},
                			error : function(e) {
                				alert("Error: ", e);
                				console.log("Error", e);
                			}
                		});

                		var canvas = document.getElementById('myChart3');

                		data = {
                			labels : label,
                			datasets : [ {
                				label : "Tổng giá trị (VNĐ)",
                				backgroundColor : "#5CB85C",
                				borderColor : "#5CB85C",
                				borderWidth : 2,
                				hoverBackgroundColor : "#0043ff",
                				hoverBorderColor : "#0043ff",
                				data : dataForDataSets,
                			} ]
                		};
                		var option = {
                			scales : {
                				yAxes : [ {
                					stacked : true,
                					gridLines : {
                						display : true,
                						color : "rgba(255,99,132,0.2)"
                					}
                				} ],
                				xAxes : [ {
                					barPercentage: 0.5,
                					gridLines : {
                						display : false
                					}
                				} ]
                			},
                			maintainAspectRatio: false,
                			legend: {
                	            labels: {
                	                // This more specific font property overrides the global property
                	                fontSize: 20
                	            }
                			}
                		};

                		var myBarChart = Chart.Bar(canvas, {
                			data : data,
                			options : option
                		});
            }
});

//window.onload = function() {
//		var data = [];
//		var label = [];
//		var dataForDataSets = [];
//
//		$.ajax({
//			async : false,
//			type : "GET",
//			data : data,
//			contentType : "application/json",
//			url : "http://localhost:8080/api/orders/statistic-revenue-week",
//			success : function(data) {
//				for (var i = 0; i < data.length; i++) {
//					label.push(data[i][0] + "/" + data[i][1]);
//					dataForDataSets.push(data[i][2]/1000000);
//				}
//			},
//			error : function(e) {
//				alert("Error: ", e);
//				console.log("Error", e);
//			}
//		});
//
//		var canvas = document.getElementById('myChart');
//
//		data = {
//			labels : label,
//			datasets : [ {
//				label : "Tổng giá trị (đồng)",
//				backgroundColor : "#0000ff",
//				borderColor : "#0000ff",
//				borderWidth : 2,
//				hoverBackgroundColor : "#0043ff",
//				hoverBorderColor : "#0043ff",
//				data : dataForDataSets,
//			} ]
//		};
//		var option = {
//			scales : {
//				yAxes : [ {
//					stacked : true,
//					gridLines : {
//						display : true,
//						color : "rgba(255,99,132,0.2)"
//					}
//				} ],
//				xAxes : [ {
//					barPercentage: 0.5,
//					gridLines : {
//						display : false
//					}
//				} ]
//			},
//			maintainAspectRatio: false,
//			legend: {
//	            labels: {
//	                // This more specific font property overrides the global property
//	                fontSize: 20
//	            }
//			}
//		};
//
//		var myBarChart = Chart.Bar(canvas, {
//			data : data,
//			options : option
//		});
//	}