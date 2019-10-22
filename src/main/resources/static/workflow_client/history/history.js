			
String.prototype.GetValue = function(parm) {
	var reg = new RegExp("(^|&)" + parm + "=([^&]*)(&|$)");
	var r = this.substr(this.indexOf("\?") + 1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}


var fields = [[{
			field : 'HDDYMC',
			title : '环节名称',
			halign : 'center',align:'center',
			width : '10%'
		},  {
			field : 'BLRYJG',
			title : '办理机关',
			halign : 'center',align:'center',
			width : '10%'
		},{
			field : 'BLRYGW',
			title : '办理岗位',
			halign : 'center',align:'center',hidden:true,
			width : '10%'
		}, {
			field : 'BLRY',
			title : '办理人员',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'QSSJ',
			title : '签收时间',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		}, {
			field : 'WCSJ',
			title : '完成时间',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		}, {
			field : 'CLYJ',
			title : '处理意见',
			hidden: true,
			halign : 'center',align:'center'

		}, {
			field : 'RWZTMC',
			title : '环节状态',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		},{
			field : 'TSDYMC',
			title : '推送环节',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		}, {
			field : 'TSRY',
			title : '推送人员',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'TSRYJG',
			title : '推送机关',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'TSRYGW',
			title : '推送岗位',
			halign : 'center',align:'center',hidden:true,
			width : '10%'
		}
		, {
			field : 'TSFSMC',
			title : '推送方式',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'TSSJ',
			title : '推送时间',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		}
		]];

var lcslh;
var processInstanceId;

var wftooltip;
			$(function() {
											
			
			
				var urldz = location.href;
				lcslh = urldz.GetValue("lcslh");
				processInstanceId = urldz.GetValue("processInstanceId");
				//$("#image").attr("src", cssnj_workflow_server + "/wfctrl/viewImage.action?tld=WfWebUtil_viewImage&processInstanceId=" + processInstanceId + "&a=" + Math.random());
				getHistory();
				//setTimeout("getCurrentNodes()",10);
				
				paper = new Raphael(document.getElementById('canvas_container'));
				getWorkFlow();
				
			
				
				$("#tab2").scroll(function(){
			        if(top!=$("#tab2").scrollTop()){
			            // 上下滚动
			        	//alert($("#tab2").scrollLeft());
			        	//$(".abc").css('left',   $(".abc").attr('left').replace(/px/,'')*1 - ($("#tab2").scrollLeft()+'').replace(/px/,'')*1);
			        }else{
			            // 左右滚动
			        	//alert(2);
			        }
			    })
			    
			    
			});
			
			function historyNodeTipIn(x, y ,w ,h, info) {
				var data = JSON.parse(info);
				
				$("#content").html("");
				var html = "";
				for(var o in data){
					html += " <b>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</b><font color=''> 已完成</font></br>";
					html += " <b>处理人员:</b> <font color=''>" + data[o].RYMC + "</font></br>";
					html += " <b>签收时间:</b> <font style='letter-spacing:0px;'>" + data[o].QSSJ + "</font></br>";
					html += " <b>完成时间:</b> <font style='letter-spacing:0px;'>" + data[o].WCSJ + "</font></br>";
				    //  alert(data[o].RYMC);
				     // alert("name:"+data[o].name+" age:"+data[o].age)
				}
				$("#content").html(html);
				//alert(x);
				//alert(y);
				//alert(w);
				//alert(h);
				$("#jBox1").css("top",y);
				$("#jBox1").css("left",x);
				$("#jBox1").css("marginTop", (- $("#jBox1").height() + h/2  - 11));
				$("#jBox1").css("marginLeft", (- $("#jBox1").width()/2 + w/2 - 10));
				$("#jBox1").show();
				//wftooltip.open();
			}
			
			
			function activeNodeTipIn(x, y ,w ,h, info) {
				var data = JSON.parse(info);
				
				$("#content").html("");
				var html = "";
				
				for(var o in data){
					
					//已签收
					if (data[o].RWZT == "1") {
						
						html += " <b>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</b> <font color=''>已签收</font></br>";
						html += " <b>处理人员: </b><font color=''>" + data[o].RYMC + "</font></br>";
						html += " <b>推送时间: </b><font style='letter-spacing:0px;'>" + data[o].TSSJ + "</font></br>";
						html += " <b>签收时间:</b> <font style='letter-spacing:0px;'>" + data[o].QSSJ + "</font></br>";
						
						
					} else {
						//未签收
						html += " <b>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</b> <font color='#ef4136'>未签收</font></br>";
						html += " <b>推送时间:</b> <font style='letter-spacing:0px;color:#ef4136;'>" + data[o].TSSJ + "</font></br>";
						html += " <b>待办人员:</b> <font style='color:#ef4136;letter-spacing:0px;'>" + data[o].RYMC + "</font></br>";
					}
					
				    //  alert(data[o].RYMC);
				     // alert("name:"+data[o].name+" age:"+data[o].age)
				}
				$("#content").html(html);
				//alert(x);
				//alert(y);
				//alert(w);
				//alert(h);
				$("#jBox1").css("top",y);
				$("#jBox1").css("left",x);
				$("#jBox1").css("marginTop", (- $("#jBox1").height() + h/2  - 11));
				$("#jBox1").css("marginLeft", (- $("#jBox1").width()/2 + w/2 - 10));
				$("#jBox1").show();
				//wftooltip.open();
			}
			
			
			function tipOut() {
				$("#jBox1").hide();
			}
			
		/*	
		function getCurrentNodes() {	
			$.ajax({  
	             type: "post",  
	             async: false,  
	             url: cssnj_workflow_server + "/wfctrl/workflow.action?tld=WfWebUtil_getCurrentActivityCoordinates&callback=cssnj_workflow_getCurrentActivityCoordinates_callBack",  
	             dataType: "jsonp", 
	             data : {"processInstanceId": processInstanceId},
	             success: function(data){ 
	        	 	
	             },  
	             error: function(){  
	            	
	             }  
	         });  
    
		}*/
		
			/*
		function cssnj_workflow_getCurrentActivityCoordinates_callBack(data) {
		
			if (data.success == "1") {
				
		 		var jsonData = data.activityList;
	        	//alert(jsonData);
				for (o in jsonData) {
					
					var activityDiv = '<div left='+ (jsonData[o].x - 10) + ' class="abc" style="height:' + (jsonData[o].height + 12) + 'px;width:' + (jsonData[o].width + 12) + 'px;position:absolute;left:' + (jsonData[o].x - 10) + 'px;top:' + (jsonData[o].y  + 30 ) + 'px;"></div>';			
					//alert(activityDiv);
					$("#content").append(activityDiv);
				}
		 	} else {
		 		alert("获取当前活动节点失败");
		 	}
		}*/
		
		
			function getHistory() {
				
				$.ajax({  
		             type: "post",  
		             async: false,  
		             url: cssnj_workflow_server + "/wfctrl/workflow.action?tld=WfWebUtil_getHistory&callback=cssnj_workflow_getHistory_callBack",  
		             dataType: "jsonp", 
		             data : {"processInstanceId": processInstanceId, "lcslh" : lcslh},
		             success: function(data){ 
		        	 	
		             },  
		             error: function(){  
		            	
		             }  
		         });  
        
			}
			
			function cssnj_workflow_getHistory_callBack(data) {
				
				if (data.success == "1") {
				
		        	
			 		$("#history").datagrid({
						striped : true,
						nowrap : true,
						idField : 'lcslh',
						pagination : true,
						rownumbers : true,
						singleSelect : true,
						fit : true,
						data : data.historyList,
						columns : fields
					});
			 		
			 		
			 	} else {
			 		alert("失败");
			 	}
			}
	
			//===================================================
			function getWorkFlow() {
				
				var url = cssnj_workflow_server + '/wfctrl/workflow.action?tld=WfWebUtil_getActivityCoordinates&processInstanceId=' + processInstanceId + '&lcslh=' + lcslh + '&callback=initWorkFlow';
				var JSONP=document.createElement("script");    
				JSONP.type="text/javascript";    
				JSONP.src=url;    
				document.getElementsByTagName("head")[0].appendChild(JSONP);
			}
			
			
			function showLct(lx) {
				if (lx == "1") {
					
					for (o in data) {
					

						if (data[o].isHistory != "1") {	
								if (data[o].type == "line") {
										eval(data[o].bpmnElement + ".animate({opacity:1})");
								} else {
										eval(data[o].bpmnElement + ".animate({opacity:1})");
										 eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle1 + ")");
								}
						} 
							
					} 
				} else {
					for (o in data) {
						if (data[o].isHistory != "1") {	
								if (data[o].type == "line") {
										eval(data[o].bpmnElement + ".animate({opacity:0})");
								} else {
										eval(data[o].bpmnElement + ".animate({opacity:0})");
										eval(data[o].bpmnElement + "glow.remove()");
								}
						} 
					}	
				}
			}
			
			
			var colorIndex = 0;
			var colorsArray = ['#00bfff', '#009ad6'];
			var data;
			//已执行line的样式
			var lineStyle1 = "{fill: '', stroke:'#00bfff','opacity': 1, 'stroke-width': 2.5,  'arrow-end':'block-wide-long'}";
			//未执行line的样式
			var lineStyle2 = "{fill: '', stroke:'#a1a3a6','opacity': 1, 'stroke-width': 2.5,  'arrow-end':'classic-wide-long'}";
			
			//未执行节点样式
			var nodeStyle1 = "{cursor : 'pointer',fill: '#a1a3a6',opacity: 1, stroke:'#eee'}";
			//已执行节点样式
			var nodeStyle2 = "{cursor : 'pointer',fill: '#00bfff',opacity: 1, stroke:'#eee'}";
			//正在执行节点样式
			var nodeStyle3 = "{cursor : 'pointer',fill: '#00bfff',opacity: 1, stroke:'#eee'}";
			
			//SubProcess样式
			var subProcessStyle = "{cursor : 'pointer',fill: '',opacity: 1, stroke:'#eee'}";
			
			//未执行 glow样式
			var glowStyle1 = "{width: 8, fill: false, opacity: 0.6, offsetx: 0, offsety: 0,color: '#a1a3a6'}";
			//已 执行 glow样式
			var glowStyle2 = "{width: 10, fill: false, opacity: 0.6, offsetx: 0, offsety: 0,color: 'dodgerblue'}";//dodgerblue
			//正在 执行 glow样式
			var glowStyle3 = "{width: 10, fill: false, opacity: 0.6, offsetx: 0, offsety: 0,color: 'dodgerblue'}";
			
			
			
			
			//文本样式 start-end -网关
			var timeTextStyle = '{cursor : "pointer","fill":"#00bfff","font-size":"10px","font-weight":"bold","text-anchor":"middle"}';
			
			var textStyle1 = '{cursor : "pointer","fill":"#fff","font-size":"10px","font-weight":"bold","text-anchor":"middle"}';
			//节点
			var textStyle2 = '{cursor : "pointer","fill":"#fff","font-size":"12px","font-weight":"bold","text-anchor":"middle"}';
			//错误
			var textStyle3 = '{cursor : "pointer","fill":"#fff","font-size":"10px","font-weight":"bold","text-anchor":"middle"}';
			//边界事件
			var textStyle4 = '{cursor : "pointer","fill":"black","font-size":"10px","font-weight":"bold","text-anchor":"middle"}';
			
			//未执行状态节点的 hover 语句块
			var hoverInMovement1 = 'this.attr({"stroke-width": 2, stroke:"#fff"});';
			var hoverOutMovement1 = 'this.attr({"stroke-width": 1,stroke:"#eee"});';
			//已执行和正在执行节点的 hover 语句块
			var hoverInMovement2 = 'this.attr({"stroke-width": 3, color:"#fff"});';
			var hoverOutMovement2 = 'this.attr({"stroke-width": 1,stroke:"#eee"});';
			
			function getRandomColor (){    
					
				  return (function(m,s,c){    
					return (c ? arguments.callee(m,s,c-1) : '#') +    
					  s[m.floor(m.random() * 16)]    
				  })(Math,'0123456789abcdef',5)    
			} 
			
			function getColor() {
				colorIndex ++;
				if (colorIndex > 1) {
					colorIndex = 0;
				}
				return colorsArray[colorIndex];
			}
			
			  
			  function activeNode(node) {
				
				var color = getRandomColor();
				node.animate(
                  {
                     fill: color
                  }, 1000);
				//node.glow({color: color});
				
			  }
			  
			  
			  //未使用，---------------------在用 data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)
			  function isPointInside(x,y,width,height,pointX, pointY) {
					//alert(x + "," + y + "," + width + "," + height + "," + pointX + "," + pointY);
					//alert(pointX*1 >= x*1);
					//alert(pointX*1 <= (x*1 + width*1));
					//alert(pointY >= y * 1);
					//alert(pointY <= (y*1 + height*1));
					if (pointX*1 >= x*1 && pointX*1 <= (x*1 + width*1) && pointY >= y * 1 && pointY <= (y*1 + height*1)) {
						return true;
					} else {
						return false;
					}
			  }
			  
			  
			  function initWorkFlow(actData) {
					paper = new Raphael(document.getElementById('canvas_container'));
					data = actData.activityList;
					for (o in data) {
							//线
							if (data[o].type == "line") {
								linePath = "";
								for (i in data[o].point) {
										if (i == 0) {
											linePath = " M " +  data[o].point[i].x  + " " + data[o].point[i].y;
										} else {
											linePath += " L " + data[o].point[i].x  + " " + data[o].point[i].y;
										}
								}

								if (data[o].isHistory == "1") {	
									eval(" " + data[o].bpmnElement + " =  paper.path('" + linePath + "').attr(" + lineStyle1 + ").toBack()");
									
								} else {
									eval(" " + data[o].bpmnElement + " =  paper.path('" + linePath + "').attr(" + lineStyle2 + ").toBack()");
								}
								
							} 
							//开始
							else if (data[o].type == "StartEvent"){
								//历史运行节点
								if (data[o].isHistory == "1") {
									eval(" " +  data[o].bpmnElement + " = paper.circle(" + 	(data[o].x * 1 + data[o].width/2) + ", "
										+ (data[o].y * 1 +  data[o].width/2) + "," + data[o].width/2 + ").attr(" + nodeStyle2 + ")");
									eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle2 + ")");
									eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
													+ (data[o].y * 1 + data[o].height/2) + ',"开始").attr(' + textStyle1 + ')');
									eval(data[o].bpmnElement + '.hover(function() {' +  hoverInMovement2  + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' +  hoverOutMovement2  + '}});');
									
									//启动时间
									eval(' ' + data[o].bpmnElement + '_text_qdsj = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
											+ (data[o].y * 1 + data[o].height*1 + 20) + ',"' + actData.lcxx.QDSJ + '").attr(' + timeTextStyle + ')');
									
								} else {
									eval(" " +  data[o].bpmnElement + " = paper.circle(" + 
									(data[o].x * 1 + data[o].width/2) + ", " + (data[o].y * 1 +  data[o].width/2) + "," + 
									data[o].width/2 + ").attr(" + nodeStyle1 + ")");
									eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle1 + ")");

									eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
													+ (data[o].y * 1 + data[o].height/2) + ',"开始").attr(' + textStyle1 + ')');
											eval(data[o].bpmnElement + '.hover(function() {	' + hoverInMovement1 + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' + hoverOutMovement1 + '}});');

								}
								
							}
							//结束
							else if (data[o].type == "EndEvent"){
								var nodeName=data[o].name==""?"结束":data[o].name;
								if (data[o].isHistory == "1") {
									 eval(" " +  data[o].bpmnElement + " = paper.circle(" + 
									 (data[o].x * 1 + data[o].width/2) + ", " + (data[o].y * 1 +  data[o].width/2) + "," + 
									 data[o].width/2 + ").attr(" + nodeStyle2 + ")");
									 eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle2 + ")");
									 eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
													+ (data[o].y * 1 + data[o].height/2) + ',"'+nodeName+'").attr(' + textStyle3 + ')');
									eval(data[o].bpmnElement + '.hover(function() {	' +  hoverInMovement2  + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' +  hoverOutMovement2  + '}});');

									
									//结束时间
									eval(' ' + data[o].bpmnElement + '_text_qdsj = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
											+ (data[o].y * 1 + data[o].height*1 + 20) + ',"' + actData.lcxx.WCSJ + '").attr(' + timeTextStyle + ')');
									
								} else {
									 eval(" " +  data[o].bpmnElement + " = paper.circle(" + 
									 (data[o].x * 1 + data[o].width/2) + ", " + (data[o].y * 1 +  data[o].width/2) + "," + 
									 data[o].width/2 + ").attr(" + nodeStyle1 + ")");
									 eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle1 + ")");

									  eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
													+ (data[o].y * 1 + data[o].height/2) + ',"'+nodeName+'").attr(' + textStyle3 + ')');
											eval(data[o].bpmnElement + '.hover(function() {	' + hoverInMovement1 + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' + hoverOutMovement1 + '}});');


								}
							} 
							//任务节点
							else if (data[o].type == "UserTask") {
							
								
								
								//当前活动节点 
								if (data[o].isActive == "1") {
											eval(" " +  data[o].bpmnElement + " = paper.rect(" + data[o].x + ", " + data[o].y + "," + data[o].width + ", " + data[o].height + 
												", 10).attr(" + nodeStyle3 + ")");
										    eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle3 + ")");
											eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
												+ (data[o].y * 1 + data[o].height/2) + ',"' + data[o].name + '").attr(' + textStyle2 + ')');
												
											eval('setInterval("activeNode(' + data[o].bpmnElement + ')",1000)');	
									eval(data[o].bpmnElement + '.hover(function() {	activeNodeTipIn(' + data[o].x + ', ' + data[o].y + ',' + data[o].width + ',' + data[o].height + ',\'' + JSON.stringify(data[o].info) + '\');' +  hoverInMovement2  + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){tipOut();' +  hoverOutMovement2  + '}});');
									
									
								} else  {
										
										//历史运行节点
										if (data[o].isHistory == "1") {
											
											
											//默认，未执行节点
											eval(" " +  data[o].bpmnElement + " = paper.rect(" + data[o].x + ", " + data[o].y + "," + data[o].width + ", " + data[o].height + 
												", 10).attr(" + nodeStyle2 + ")");
											eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle2 + ")");
											eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
												+ (data[o].y * 1 + data[o].height/2) + ',"' + data[o].name + '").attr(' + textStyle2 + ')');
											eval(data[o].bpmnElement + '.hover(function() { historyNodeTipIn(' + data[o].x + ', ' + data[o].y + ',' + data[o].width + ',' + data[o].height + ',\'' + JSON.stringify(data[o].info) + '\');' +  hoverInMovement2  + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){tipOut();' +  hoverOutMovement2  + '}});');
											
											//eval(data[o].bpmnElement + '.hover(function() {	this.attr({"stroke-width": 2, color:"#fff"});this.g = this.glow({width: 20, fill: true, opacity: 1,color: "dodgerblue"});}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){this.attr({"stroke-width": 1,color:"#eee"});this.g.remove();}});');
										
											//签收时间-
											eval(' ' + data[o].bpmnElement + '_text_qssj = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
													+ (data[o].y * 1 - 20) + ',"' + data[o].info[0].RYMC + "\\n" + data[o].info[0].WCSJ + '").attr(' + timeTextStyle + ')');
										
										} else {
											
											
											//默认，未执行节点
											eval(" " +  data[o].bpmnElement + " = paper.rect(" + data[o].x + ", " + data[o].y + "," + data[o].width + ", " + data[o].height + 
												", 10).attr(" + nodeStyle1 + ")");
											eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle1 + ")");
											eval(data[o].bpmnElement + '.hover(function() {	' + hoverInMovement1 + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' + hoverOutMovement1 + '}});');

											eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
												+ (data[o].y * 1 + data[o].height/2) + ',"' + data[o].name + '").attr(' + textStyle2 + ')');	
										}
								
								} 
								
							} 
							//ServiceTask任务节点
							else if (data[o].type == "ServiceTask") {
										//默认，未执行节点
										eval(" " +  data[o].bpmnElement + " = paper.rect(" + data[o].x + ", " + data[o].y + "," + data[o].width + ", " + data[o].height + 
												", 10).attr(" + nodeStyle2 + ")");
										eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle2 + ")");
										eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
												+ (data[o].y * 1 + data[o].height/2) + ',"' + data[o].name + '").attr(' + textStyle2 + ')');
										//签收时间-
										eval(' ' + data[o].bpmnElement + '_text_qssj = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
												+ (data[o].y * 1 - 20) + ',"' + "" + "\\n" + "" + '").attr(' + timeTextStyle + ')');
							} 
							//分支网关
							else if (data[o].type == "ExclusiveGateway") {
							
								if (data[o].isActive == "1") {
								
										path  = " M " +  (data[o].x * 1 +  data[o].width/2)   + " " + data[o].y * 1
										+ " L " + (data[o].x * 1 +  data[o].width * 1) +  " " + (data[o].y * 1 + data[o].height/2) 
										+ " L " + (data[o].x * 1 +  data[o].width /2) +  " " + (data[o].y * 1 + data[o].height * 1) 
										+ " L " + data[o].x * 1 +  " " + (data[o].y * 1 + data[o].height/2)  + " Z";		
										eval(" " + data[o].bpmnElement + " =  paper.path('" + path + "').attr(" + nodeStyle3 + ")");
										eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle3 + ")");
										eval('setInterval("activeNode(' + data[o].bpmnElement + ')",1000)');	
										eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
													+ (data[o].y * 1 + data[o].height/2) + ',"分支").attr(' + textStyle1 + ')');
									eval(data[o].bpmnElement + '.hover(function() {	' +  hoverInMovement2  + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' +  hoverOutMovement2  + '}});');
			
								
								
								} else {
									if (data[o].isHistory == "1") {	
										
										path  = " M " +  (data[o].x * 1 +  data[o].width/2)   + " " + data[o].y * 1
										+ " L " + (data[o].x * 1 +  data[o].width * 1) +  " " + (data[o].y * 1 + data[o].height/2) 
										+ " L " + (data[o].x * 1 +  data[o].width /2) +  " " + (data[o].y * 1 + data[o].height * 1) 
										+ " L " + data[o].x * 1 +  " " + (data[o].y * 1 + data[o].height/2)  + " Z";		
										eval(" " + data[o].bpmnElement + " =  paper.path('" + path + "').attr(" + nodeStyle2 + ")");
										eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle2 + ")");
										eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
													+ (data[o].y * 1 + data[o].height/2) + ',"分支").attr(' + textStyle1 + ')');
									eval(data[o].bpmnElement + '.hover(function() {	' +  hoverInMovement2  + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' +  hoverOutMovement2  + '}});');
			
											
									} else {
										path  = " M " +  (data[o].x * 1 +  data[o].width/2)   + " " + data[o].y * 1
										+ " L " + (data[o].x * 1 +  data[o].width * 1) +  " " + (data[o].y * 1 + data[o].height/2) 
										+ " L " + (data[o].x * 1 +  data[o].width /2) +  " " + (data[o].y * 1 + data[o].height * 1) 
										+ " L " + data[o].x * 1 +  " " + (data[o].y * 1 + data[o].height/2)  + " Z";		
										eval(" " + data[o].bpmnElement + " =  paper.path('" + path + "').attr(" + nodeStyle1 + ")");
										eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle1 + ")");
								
										eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
													+ (data[o].y * 1 + data[o].height/2) + ',"分支").attr(' + textStyle1 + ')');
										eval(data[o].bpmnElement + '.hover(function() {	' + hoverInMovement1 + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' + hoverOutMovement1 + '}});');

									}
								}
							}
							//并行网关
							else if (data[o].type == "ParallelGateway") {
							
								if (data[o].isActive == "1") {
								
										path  = " M " +  (data[o].x * 1 +  data[o].width/2)   + " " + data[o].y * 1
										+ " L " + (data[o].x * 1 +  data[o].width * 1) +  " " + (data[o].y * 1 + data[o].height/2) 
										+ " L " + (data[o].x * 1 +  data[o].width /2) +  " " + (data[o].y * 1 + data[o].height * 1) 
										+ " L " + data[o].x * 1 +  " " + (data[o].y * 1 + data[o].height/2)  + " Z";		
										eval(" " + data[o].bpmnElement + " =  paper.path('" + path + "').attr({fill: '#00bfff', stroke:'#eee','opacity': 1, 'stroke-width': 1.5,})");
										eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow({width: 10, fill: false, opacity: 0.6, offsetx: 0, offsety: 0,color: 'dodgerblue'})");
										eval('setInterval("activeNode(' + data[o].bpmnElement + ')",1000)');	
										eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
													+ (data[o].y * 1 + data[o].height/2) + ',"并行").attr({' 
													+ 'cursor : "pointer","fill":"#fff","font-size":"10px","font-weight":"bold","text-anchor":"middle"})');
									eval(data[o].bpmnElement + '.hover(function() {	' +  hoverInMovement2  + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' +  hoverOutMovement2  + '}});');
		
								
								} else {
									if (data[o].isHistory == "1") {	
										//eval("var " +  data[o].bpmnElement + " = paper.rect(" + data[o].x + ", " + data[o].y + "," + data[o].width + ", " + data[o].height + 
											//",50).attr({fill: '#00bfff',opacity: 1, stroke:'dodgerblue'})");
										path  = " M " +  (data[o].x * 1 +  data[o].width/2)   + " " + data[o].y * 1
										+ " L " + (data[o].x * 1 +  data[o].width * 1) +  " " + (data[o].y * 1 + data[o].height/2) 
										+ " L " + (data[o].x * 1 +  data[o].width /2) +  " " + (data[o].y * 1 + data[o].height * 1) 
										+ " L " + data[o].x * 1 +  " " + (data[o].y * 1 + data[o].height/2)  + " Z";		
										eval(" " + data[o].bpmnElement + " =  paper.path('" + path + "').attr({fill: '#00bfff', stroke:'#eee','opacity': 1, 'stroke-width': 1.5,})");
										eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow({width: 10, fill: false, opacity: 0.6, offsetx: 0, offsety: 0,color: 'dodgerblue'})");
										eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
													+ (data[o].y * 1 + data[o].height/2) + ',"并行").attr({' 
													+ 'cursor : "pointer","fill":"#fff","font-size":"10px","font-weight":"bold","text-anchor":"middle"})');
										eval(data[o].bpmnElement + '.hover(function() {	' +  hoverInMovement2  + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' +  hoverOutMovement2  + '}});');
	
									} else {
										path  = " M " +  (data[o].x * 1 +  data[o].width/2)   + " " + data[o].y * 1
										+ " L " + (data[o].x * 1 +  data[o].width * 1) +  " " + (data[o].y * 1 + data[o].height/2) 
										+ " L " + (data[o].x * 1 +  data[o].width /2) +  " " + (data[o].y * 1 + data[o].height * 1) 
										+ " L " + data[o].x * 1 +  " " + (data[o].y * 1 + data[o].height/2)  + " Z";		
										eval(" " + data[o].bpmnElement + " =  paper.path('" + path + "').attr(" + nodeStyle1 + ")");
										eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle1 + ")");
										eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
													+ (data[o].y * 1 + data[o].height/2) + ',"并行").attr({' 
													+ 'cursor : "pointer","fill":"#fff","font-size":"10px","font-weight":"bold","text-anchor":"middle"})');
											eval(data[o].bpmnElement + '.hover(function() {	' + hoverInMovement1 + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' + hoverOutMovement1 + '}});');
			
									}
								}
							}
							//包含网关 = 分支+并行
							else if (data[o].type == "InclusiveGateway") {
							
								if (data[o].isActive == "1") {
								
										path  = " M " +  (data[o].x * 1 +  data[o].width/2)   + " " + data[o].y * 1
										+ " L " + (data[o].x * 1 +  data[o].width * 1) +  " " + (data[o].y * 1 + data[o].height/2) 
										+ " L " + (data[o].x * 1 +  data[o].width /2) +  " " + (data[o].y * 1 + data[o].height * 1) 
										+ " L " + data[o].x * 1 +  " " + (data[o].y * 1 + data[o].height/2)  + " Z";		
										eval(" " + data[o].bpmnElement + " =  paper.path('" + path + "').attr({fill: '#00bfff', stroke:'#eee','opacity': 1, 'stroke-width': 1.5,})");
										eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow({width: 10, fill: false, opacity: 0.6, offsetx: 0, offsety: 0,color: 'dodgerblue'})");
										eval('setInterval("activeNode(' + data[o].bpmnElement + ')",1000)');	
										eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
													+ (data[o].y * 1 + data[o].height/2) + ',"包含").attr({' 
													+ 'cursor : "pointer","fill":"#fff","font-size":"10px","font-weight":"bold","text-anchor":"middle"})');
									eval(data[o].bpmnElement + '.hover(function() {	' +  hoverInMovement2  + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' +  hoverOutMovement2  + '}});');

								
								} else {
									if (data[o].isHistory == "1") {	
										//eval("var " +  data[o].bpmnElement + " = paper.rect(" + data[o].x + ", " + data[o].y + "," + data[o].width + ", " + data[o].height + 
											//",50).attr({fill: '#00bfff',opacity: 1, stroke:'dodgerblue'})");
										path  = " M " +  (data[o].x * 1 +  data[o].width/2)   + " " + data[o].y * 1
										+ " L " + (data[o].x * 1 +  data[o].width * 1) +  " " + (data[o].y * 1 + data[o].height/2) 
										+ " L " + (data[o].x * 1 +  data[o].width /2) +  " " + (data[o].y * 1 + data[o].height * 1) 
										+ " L " + data[o].x * 1 +  " " + (data[o].y * 1 + data[o].height/2)  + " Z";		
										eval(" " + data[o].bpmnElement + " =  paper.path('" + path + "').attr({fill: '#00bfff', stroke:'#eee','opacity': 1, 'stroke-width': 1.5,})");
										eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow({width: 10, fill: false, opacity: 0.6, offsetx: 0, offsety: 0,color: 'dodgerblue'})");
										eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
													+ (data[o].y * 1 + data[o].height/2) + ',"包含").attr({' 
													+ 'cursor : "pointer","fill":"#fff","font-size":"10px","font-weight":"bold","text-anchor":"middle"})');
										eval(data[o].bpmnElement + '.hover(function() {	' +  hoverInMovement2  + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' +  hoverOutMovement2  + '}});');
	
									} else {
										path  = " M " +  (data[o].x * 1 +  data[o].width/2)   + " " + data[o].y * 1
										+ " L " + (data[o].x * 1 +  data[o].width * 1) +  " " + (data[o].y * 1 + data[o].height/2) 
										+ " L " + (data[o].x * 1 +  data[o].width /2) +  " " + (data[o].y * 1 + data[o].height * 1) 
										+ " L " + data[o].x * 1 +  " " + (data[o].y * 1 + data[o].height/2)  + " Z";		
										eval(" " + data[o].bpmnElement + " =  paper.path('" + path + "').attr(" + nodeStyle1 + ")");
										eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle1 + ")");
										eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
													+ (data[o].y * 1 + data[o].height/2) + ',"包含").attr({' 
													+ 'cursor : "pointer","fill":"#fff","font-size":"10px","font-weight":"bold","text-anchor":"middle"})');
											eval(data[o].bpmnElement + '.hover(function() {	' + hoverInMovement1 + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' + hoverOutMovement1 + '}});');
			
									}
								}
							}
							//子任务节点
							else if (data[o].type == "SubProcess") {
							
								//当前活动节点 
								if (data[o].isActive == "1") {
											eval(" " +  data[o].bpmnElement + " = paper.rect(" + data[o].x + ", " + data[o].y + "," + data[o].width + ", " + data[o].height + 
												", 10).attr(" + subProcessStyle + ")");
										    eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle3 + ")");
											eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
												+ (data[o].y * 1 + data[o].height/2) + ',"' + data[o].name + '").attr(' + textStyle2 + ')');
												
											eval('setInterval("activeNode(' + data[o].bpmnElement + ')",1000)');	
									eval(data[o].bpmnElement + '.hover(function() {	' +  hoverInMovement2  + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' +  hoverOutMovement2  + '}});');
									
									
								} else  {
										
										//历史运行节点
										if (data[o].isHistory == "1") {
											
											
											//默认，未执行节点
											eval(" " +  data[o].bpmnElement + " = paper.rect(" + data[o].x + ", " + data[o].y + "," + data[o].width + ", " + data[o].height + 
												", 10).attr(" + subProcessStyle + ")");
											eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle2 + ")");
											eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
												+ (data[o].y * 1 + data[o].height/2) + ',"' + data[o].name + '").attr(' + textStyle2 + ')');
											eval(data[o].bpmnElement + '.hover(function() {	' +  hoverInMovement2  + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' +  hoverOutMovement2  + '}});');
										
											//eval(data[o].bpmnElement + '.hover(function() {	this.attr({"stroke-width": 2, color:"#fff"});this.g = this.glow({width: 20, fill: true, opacity: 1,color: "dodgerblue"});}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){this.attr({"stroke-width": 1,color:"#eee"});this.g.remove();}});');
										} else {
											
											
											//默认，未执行节点
											eval(" " +  data[o].bpmnElement + " = paper.rect(" + data[o].x + ", " + data[o].y + "," + data[o].width + ", " + data[o].height + 
												", 10).attr(" + subProcessStyle + ")");
											eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle1 + ")");
											eval(data[o].bpmnElement + '.hover(function() {	' + hoverInMovement1 + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' + hoverOutMovement1 + '}});');

											eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
												+ (data[o].y * 1 + data[o].height/2) + ',"' + data[o].name + '").attr(' + textStyle2 + ')');	
										}
								
								} 
								
							}
							//BoundaryEvent
							else if (data[o].type == "BoundaryEvent") {
							var nodeName=data[o].name==""?"边界":data[o].name;
										path  = " M " +  (data[o].x * 1 +  data[o].width/2)   + " " + data[o].y * 1
										+ " L " + (data[o].x * 1 +  data[o].width * 1) +  " " + (data[o].y * 1 + data[o].height/2) 
										+ " L " + (data[o].x * 1 +  data[o].width /2) +  " " + (data[o].y * 1 + data[o].height * 1) 
										+ " L " + data[o].x * 1 +  " " + (data[o].y * 1 + data[o].height/2)  + " Z";		
										eval(" " + data[o].bpmnElement + " =  paper.path('" + path + "').attr(" + nodeStyle1 + ")");
										eval(data[o].bpmnElement + "glow=" + data[o].bpmnElement + ".glow(" + glowStyle1 + ")");
								
										eval(' ' + data[o].bpmnElement + '_text = paper.text(' + (data[o].x * 1 + data[o].width/2) + ','
													+ (data[o].y * 1 + data[o].height/2) + ',"'+nodeName+'").attr(' + textStyle4 + ')');
										eval(data[o].bpmnElement + '.hover(function() {	' + hoverInMovement1 + '}, function(e){ if(!' + data[o].bpmnElement  +'.isPointInside(e.offsetX,e.offsetY)){' + hoverOutMovement1 + '}});');

							} 
					}
				}
				
			  
			