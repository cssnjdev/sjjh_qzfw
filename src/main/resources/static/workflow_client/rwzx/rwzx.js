var rwxxData = null;
var rydm = null;
var jgdm = null;
var gwdm = null;
var cxdm = null;
var qsIndex = null;
var method = "getDbrw";
var grid = "#dbrw";
var fields = null;
var dbrwFields = [[{
			field : 'LCSLH',
			title : '流程实例号',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'LCMC',
			title : '流程名称',
			halign : 'center',align:'center',
			width : '10%'
		} ,{
			field : 'BIZMC',
			title : '业务名称',
			halign : 'center',align:'center',
			width : '10%'
		} ,{
			field : 'BIZID',
			title : '业务ID',
			halign : 'center',align:'center',hidden:'true',
			width : '10%'
		}, {
			field : 'SXMC',
			title : '事项名称',
			halign : 'center',align:'center',hidden : 'true',
			width : '10%'
		}/*, {
			field : 'LCXQSJ',
			title : '流程限期时间',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		}*/, {
			field : 'HDDYMC',
			title : '当前环节',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'TSJGMC',
			title : '推送机关',
			halign : 'center',align:'center',hidden : '',
			width : '10%'
		}, {
			field : 'TSGWMC',
			title : '推送岗位',
			halign : 'center',align:'center',hidden : 'true',
			width : '10%'
		}, {
			field : 'RYMC',
			title : '推送人员',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'TSSJ',
			title : '推送时间',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		}, {
			field : 'TSFSMC',
			title : '推送方式',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'CZ',
			title : '操作',
			halign : 'center',align:'center',
			width : '10%',
			formatter : function(value, row, index) {
				return '<label onclick="claim(' + index + ')">签收</label>';
			}
		}, {
			field : 'PROCESSINSTANCEID',
			hidden : 'true'
		}, {
			field : 'TASKID',
			hidden : 'true'
		}, {
			field : 'TSJGDM',
			hidden : 'true'
		}, {
			field : 'TSGWDM',
			hidden : 'true'
		}, {
			field : 'HDDYID',
			hidden : 'true'
		}]];

var csrwFields = [[{
			field : 'LCSLH',
			title : '流程实例号',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'LCMC',
			title : '流程名称',
			halign : 'center',align:'center',
			width : '10%'
		},{
			field : 'BIZMC',
			title : '业务名称',
			halign : 'center',align:'center',
			width : '10%'
		} ,{
			field : 'BIZID',
			title : '业务ID',
			halign : 'center',align:'center',hidden:'true',
			width : '10%'
		}, {
			field : 'SXMC',
			title : '事项名称',
			halign : 'center',align:'center',hidden : 'true',
			width : '10%'
		}, {
			field : 'HDDYMC',
			title : '当前环节',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'BLJGMC',
			title : '办理机关',
			halign : 'center',align:'center',hidden : 'true',
			width : '10%'
		}, {
			field : 'BLGWMC',
			title : '办理岗位',
			halign : 'center',align:'center',hidden : 'true',
			width : '10%'
		}, {
			field : 'BLRYMC',
			title : '签收人员',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'QSSJ',
			title : '签收时间',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		}, {
			field : 'RWZTMC',
			title : '环节状态',
			halign : 'center',align:'center',
			width : '10%'
		},  {
			field : 'RWXQSJ',
			title : '环节期限时间',
			halign : 'center',align:'center',
			width : '10%'
		},{
			field : 'RWWCSJ',
			title : '环节完成时间',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		}, {
			field : 'CZ',
			title : '操作',
			halign : 'center',align:'center',
			width : '10%',
			formatter : function(value, row, index) {
				return '<label onclick="openRwxx(\'' + row.URL + '&lcslh='
						+ row.LCSLH + '&workItemId=' + row.WORKITEMID
						+ '\')">查看</label>';
			}
		}, {
			field : 'PROCESSINSTANCEID',
			hidden : 'true'
		}, {
			field : 'TASKID',
			hidden : 'true'
		}, {
			field : 'URL',
			hidden : 'true'
		}]];

var wtrwFields = [[{
			field : 'LCSLH',
			title : '流程实例号',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'LCMC',
			title : '流程名称',
			halign : 'center',align:'center',
			width : '10%'
		},{
			field : 'BIZMC',
			title : '业务名称',
			halign : 'center',align:'center',
			width : '10%'
		} ,{
			field : 'BIZID',
			title : '业务ID',
			halign : 'center',align:'center',hidden:'true',
			width : '10%'
		}, {
			field : 'SXMC',
			title : '事项名称',
			halign : 'center',align:'center',hidden : 'true',
			width : '10%'
		}, {
			field : 'HDDYMC',
			title : '当前环节',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'WTJGMC',
			title : '委托机关',
			halign : 'center',align:'center',hidden : 'true',
			width : '10%'
		},  {
			field : 'WTGWMC',
			title : '委托岗位',
			halign : 'center',align:'center',hidden : 'true',
			width : '10%'
		},{
			field : 'SWRYXM',
			title : '委托人员',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'WTSJ',
			title : '委托时间',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		}, {
			field : 'WTMS',
			title : '委托描述',
			halign : 'center',align:'center',hidden : 'true',
			width : '10%'
		}, {
			field : 'CZ',
			title : '操作',
			halign : 'center',align:'center',
			width : '10%',
			formatter : function(value, row, index) {
				return '<label onclick="acceptWtrw(' + index + ')">签收</label>';
			}
		}, {
			field : 'PROCESSINSTANCEID',
			hidden : 'true'
		}, {
			field : 'TASKID',
			hidden : 'true'
		}, {
			field : 'HDDYID',
			hidden : 'true'
		}, {
			field : 'WTID',
			hidden : 'true'
		}, {
			field : 'WORKITEMID',
			hidden : 'true'
		}]];

var zbrwFields = [[{
			field : 'LCSLH',
			title : '流程实例号',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'LCMC',
			title : '流程名称',
			halign : 'center',align:'center',
			width : '10%'
		},{
			field : 'BIZMC',
			title : '业务名称',
			halign : 'center',align:'center',
			width : '10%'
		} ,{
			field : 'BIZID',
			title : '业务ID',
			halign : 'center',align:'center',hidden:'true',
			width : '10%'
		}, {
			field : 'SXMC',
			title : '事项名称',
			halign : 'center',align:'center',hidden : 'true',
			width : '10%'
		}, {
			field : 'QDSJ',
			title : '启动时间',
			halign : 'center',align:'center',hidden : 'true',
			sortable : true,
			width : '10%'
		}, {
			field : 'HDDYMC',
			title : '当前环节',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'RWXQSJ',
			title : '环节期限时间',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'QSSJ',
			title : '签收时间',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		}, {
			field : 'JGMC',
			title : '推送机关',
			halign : 'center',align:'center',hidden : 'true',
			width : '10%'
		}, {
			field : 'RYMC',
			title : '推送人员',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'TSSJ',
			title : '推送时间',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		}, {
			field : 'TSFSMC',
			title : '推送方式',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'CZ',
			title : '操作',
			halign : 'center',align:'center',
			width : '10%',
			formatter : function(value, row, index) {
				return '<label onclick="openRwxx(\'' + row.URL + '&lcslh='
						+ row.LCSLH + '&workItemId=' + row.WORKITEMID
						+ '\')">打开</label>';
			}
		}, {
			field : 'PROCESSINSTANCEID',
			hidden : 'true'
		}, {
			field : 'TASKID',
			hidden : 'true'
		}, {
			field : 'HDDYID',
			hidden : 'true'
		}, {
			field : 'URL',
			hidden : 'true'
		}]];

var ybrwFields = [[{
			field : 'LCSLH',
			title : '流程实例号',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'LCMC',
			title : '流程名称',
			halign : 'center',align:'center',
			width : '10%'
		},{
			field : 'BIZMC',
			title : '业务名称',
			halign : 'center',align:'center',
			width : '10%'
		} ,{
			field : 'BIZID',
			title : '业务ID',
			halign : 'center',align:'center',hidden:'true',
			width : '10%'
		}, {
			field : 'SXMC',
			title : '事项名称',
			halign : 'center',align:'center',hidden : 'true',
			width : '10%'
		}, {
			field : 'QDSJ',
			title : '启动时间',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		}, {
			field : 'QSSJ',
			title : '签收时间',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		}, {
			field : 'LCZTMC',
			title : '流程状态',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'HDDYMC',
			title : '环节名称',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'TSJGMC',
			title : '推送机关',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'TSRYMC',
			title : '推送人员',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'TSSJ',
			title : '推送时间',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		}, {
			field : 'RWZTMC',
			title : '环节状态',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'RWWCSJ',
			title : '完成时间',
			halign : 'center',align:'center',
			sortable : true,
			width : '10%'
		}, {
			field : 'CZ',
			title : '操作',
			halign : 'center',align:'center',
			width : '10%',
			formatter : function(value, row, index) {
				return '<label onclick="openRwxx(\'' + row.URL + '&lcslh='
						+ row.LCSLH + '&workItemId=' + row.WORKITEMID
						+ '\')">打开</label>';
			}
		}, {
			field : 'HDSLID',
			hidden : 'true'
		}, {
			field : 'URL',
			hidden : 'true'
		}]];

String.prototype.GetValue = function(parm) {
	var reg = new RegExp("(^|&)" + parm + "=([^&]*)(&|$)");
	var r = this.substr(this.indexOf("\?") + 1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

$(function() {
			var urldz = location.href;
			rydm = urldz.GetValue("rydm");
			jgdm = urldz.GetValue("jgdm");
			gwdm = urldz.GetValue("gwdm");
			cxdm = urldz.GetValue("cxdm");
			fields = dbrwFields;
			
			
			$('#tt').tabs({
				onSelect : function(title, index) {
					if (index == 2) {
						grid = "#csrw";
						method = "getCsrw";
					} else if (index == 0) {
						grid = "#dbrw";
						method = "getDbrw";
					} else if (index == 1) {
						grid = "#dwtrw";
						method = "getWtrw";
					} else if (index == 3) {
						grid = "#zbrw";
						method = "getZbrw";
					} else if (index == 4) {
						grid = "#ybrw";
						method = "getYbrw";
					}
					getRwxx(1,15);
				}
			});
			
			setTimeout("getRwxx(1,15)",10);
		});

/*******************************************************************************
 * 获取任务信息
 */
var  gridTotal = "0";
var  gridPageNumber = "1";
var  gridpageSize="1";
function getRwxx(pageNumber, pageSize) {
	gridPageNumber = pageNumber;
	//console.log(gridPageNumber);
	gridpageSize=pageSize;
	//console.log(pageSize);
	$.ajax({
				type : "post",
				url : cssnj_workflow_server
						+ '/wfctrl/workflow.action?tld=RwzxService_' + method
						+ '&callback=getRwxxBack',
				data : eval({
							"rydm" : rydm,
							"jgdm" : jgdm,
							"gwdm" : gwdm,
							"cxdm" : cxdm,
							"rownum" : "100",
							"pageNumber":pageNumber,
							"pageSize":pageSize
						}),
				dataType : "jsonp",
				cache : false,
				async : false,
				success : function(data) {
				},
				error : function(data) {
				}
			});
}

/*******************************************************************************
 * 获取任务信息回调函数
 * 
 * @param {}
 *            data
 */
function getRwxxBack(data) {
	//alert(222222222222222222);
	var tab = $('#tt').tabs('getSelected');
	var tabIndex = $('#tt').tabs("getTabIndex", tab);
	if ( data.success == "1") {
		//if (data != null) {
		    gridTotal = data.total;
			if (tabIndex == 2) {
				console.log(data);
				rwxxData = data.csrwList;
				fields = csrwFields;
			} else if (tabIndex == 0) {
				rwxxData = data.dbrwList;
				fields = dbrwFields;
			} else if (tabIndex == 1) {
				rwxxData = data.wtrwList;
				fields = wtrwFields;
			} else if (tabIndex == 3) {
				rwxxData = data.zbrwList;
				fields = zbrwFields;
			} else if (tabIndex == 4) {
				rwxxData = data.ybrwList;
				fields = ybrwFields;
			}
			console.log(rwxxData);
			initGrid();
			  
		//}
	} else {
		alert(data.message);
		
	}
}

function initGrid() {
	//alert(11111111111111);
	$(grid).datagrid({
				striped : true,
				nowrap : true,
				idField : 'lcslh',
				pagination : true,
				rownumbers : true,
				remoteSort:false,
				singleSelect : true,
				fit : true,
				data : rwxxData,
				rowStyler: function(index,row){
					var css = "";
					if (row.RWCQ != null) {
						if (row.RWCQ == '1'){
							css = 'background:;color:#c93a49;';
						}
					}
					if (row.LCCQ != null) {
						if (row.LCCQ == '1'){
							css = 'background:;color:#f00;';
						}
					}
					return css;
				},
				onClickRow: function(rowIndex) {
					var row = $(grid).datagrid('getSelected');
					
					if (grid == "#csrw") {
						openRwxx(row.URL + '&lcslh=' + row.LCSLH + '&workItemId=' + row.WORKITEMID);
					} else if (grid == "#dbrw") {
						 claim(rowIndex);
					} else if (grid == "#dwtrw") {
						 acceptWtrw(rowIndex);
					} else if (grid == "#zbrw") {
						openRwxx(row.URL + '&lcslh=' + row.LCSLH + '&workItemId=' + row.WORKITEMID);
					} else if (grid == "#ybrw") {
						openRwxx(row.URL + '&lcslh=' + row.LCSLH + '&workItemId=' + row.WORKITEMID);
					}
				},
				columns : fields,
		        onLoadSuccess : function(){
						var pager = $(grid).datagrid("getPager");
						//alert(333333333333);
						console.log(gridTotal);
						console.log(gridPageNumber);
						pager.pagination({
								total : gridTotal,
								pageNumber : gridPageNumber
						});
		        }
			});
			//alert(gridpageSize);
			// 分页		
			$(grid).datagrid("getPager").pagination({
				showPageList : true,
				showRefresh : true,				
				pageSize : gridpageSize,
				pageList : [15, 30, 45, 60],
				onRefresh : function(pageNumber, pageSize) {
					getRwxx(pageNumber, pageSize);
				},
				onSelectPage : function(pageNumber, pageSize) {
					console.log(pageNumber);
					console.log(pageSize);
					getRwxx(pageNumber, pageSize);
					//$(this).pagination('loading');
					//alert('pageNumber:'+pageNumber+',pageSize:'+pageSize);
					//$(this).pagination('loaded');
					
				}
			});	
			
}



/*******************************************************************************
 * 签收任务
 * 
 * @param {}
 *            index
 */
function claim(index) {
	qsIndex = index;
	var rows = $("#dbrw").datagrid('getRows');
	var row = rows[index];
	var obj = new Object();
	obj.lcslh = row.LCSLH;
	obj.processInstanceId = row.PROCESSINSTANCEID;
	obj.rydm = rydm;
	obj.taskId = row.TASKID;
	obj.hddyId = row.HDDYID;
	obj.jgdm = jgdm;
	obj.gwdm = gwdm;
	var data = JSON.stringify(obj);
	data = eval('(' + data + ')');
	$.ajax({
		type : "post",
		url : cssnj_workflow_server
				+ "/wfctrl/workflow.action?tld=WfWebUtil_claimTask&callback=claimBack",
		data : data,
		cache : false,
		async : false,
		dataType : 'jsonp',
		beforeSend : function() {
		},
		success : function(data) {
		}
	});
}

/*******************************************************************************
 * 任务签收回调方法
 * 
 * @param {}
 *            data
 */
function claimBack(data) {
	if (data != null && data.success == '1') {
		$.messager.show({
					title : '提示',
					msg : "签收成功！"
				})
		var rows = $("#dbrw").datagrid('getRows');
		var row = rows[qsIndex];
		openRwxx(row.URL + '&lcslh=' + row.LCSLH + '&workItemId=' + data.workitemId);
		$(grid).datagrid("deleteRow", qsIndex);
	} else {
		$.messager.show({
					title : '提示',
					msg : "签收失败！"
				})
	}
}

/*******************************************************************************
 * 签收委托任务
 * 
 * @param {}
 *            index
 */
function acceptWtrw(index) {
	qsIndex = index;
	var rows = $("#dwtrw").datagrid('getRows');
	var row = rows[index];
	var obj = new Object();
	obj.rydm = rydm;
	obj.wtId = row.WTID;
	obj.workItemId = row.WORKITEMID;
	obj.jgdm = jgdm;
	obj.gwdm = gwdm;
	var data = JSON.stringify(obj);
	data = eval('(' + data + ')');
	$.ajax({
		type : "post",
		url : cssnj_workflow_server
				+ "/wfctrl/workflow.action?tld=WfWebUtil_acceptWtWorkItem&callback=claimBack",
		data : data,
		cache : false,
		dataType : 'jsonp',
		contentType : "application/x-www-form-urlencoded;charset=UTF-8",
		beforeSend : function() {
		},
		success : function(data) {
		}
	});
}

function openRwxx(taskUrl) {
	window.open(taskUrl);
}
