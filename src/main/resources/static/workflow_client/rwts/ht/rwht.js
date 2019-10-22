var workItemId;
var taskId;
var rwxxData;
var fields = [[{
			field : 'ck',
			title : '选择',
			halign : 'center',align:'center',
			width : '10%',
			checkbox : true
		}, {
			field : 'HDDYMC',
			title : '环节名称',
			halign : 'center',align:'center',
			width : '20%'
		}, {
			field : 'RWLXMC',
			title : '环节类型',
			halign : 'center',align:'center',
			width : '10%'
		}, {
			field : 'RYMC',
			title : '办理人员',
			halign : 'center',align:'center',
			sortable : true,
			width : '15%'
		}, {
			field : 'JGMC',
			title : '办理机关',
			halign : 'center',align:'center',
			sortable : true,
			width : '15%'
		}, {
			field : 'GWMC',
			title : '办理岗位',
			halign : 'center',align:'center',
			sortable : true,
			hidden:true,
			width : '15%'
		}, {
			field : 'WCSJ',
			title : '完成时间',
			halign : 'center',align:'center',
			sortable : true,
			width : '15%'
		},
		{
			field : 'WORKITEMID',
			hidden: true
		}
		]];

String.prototype.GetValue = function(parm) {
	var reg = new RegExp("(^|&)" + parm + "=([^&]*)(&|$)");
	var r = this.substr(this.indexOf("\?") + 1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}
$(function() {
			var urldz = location.href;
			workItemId = urldz.GetValue("workItemId");
			taskId = urldz.GetValue("taskId");
			getRwxx();
		});

/*******************************************************************************
 * 获取任务信息
 */
function getRwxx() {
	$.ajax({
		type : "post",
		url : cssnj_workflow_server
				+ '/wfctrl/workflow.action?tld=WfWebUtil_getBackTaskList&callback=getRwxxBack',
		data : eval({
					"workItemId" : workItemId,
					"taskId" : taskId
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
	if (data.success == "1") {
		rwxxData = data.backList;
		initGrid();
	} else {
		alert("加载失败");
	}
}

function initGrid() {
	$('#rwtb').datagrid({
				striped : true,
				nowrap : true,
				pagination : true,
				rownumbers : true,
				singleSelect : true,
				fit : true,
				toolbar : '#tBar',
				data : rwxxData,
				columns : fields
			});
}

function back() {
	
	var rows = $("#rwtb").datagrid("getSelections");

	for (var i = 0; i < rows.length; i++) {
		parent.cssnj_workflow_back(rows[i].WORKITEMID);
		return;
	}
	alert("请选择回退节点");
	
}
