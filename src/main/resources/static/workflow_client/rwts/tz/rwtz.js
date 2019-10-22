
var workItemId;
var lcslh;
var rwxxData;
var fields = [[{
			field : 'ck',
			title : '选择',
			halign : 'center',align:'center',
			width : '10%',
			checkbox : true
		}, {
			field : 'HDDYID',
			title : '环节ID',
			halign : 'center',align:'center',
			width : '20%'
		}, {
			field : 'HDDYMC',
			title : '环节名称',
			halign : 'center',align:'center',
			width : '30%'
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
			lcslh = urldz.GetValue("lcslid");
			getRwxx();
		});

/*******************************************************************************
 * 获取任务信息
 */
function getRwxx() {
	
	$.ajax({
		type : "post",
		url : cssnj_workflow_server
				+ '/wfctrl/workflow.action?tld=WfWebUtil_getGotoTaskList&callback=getRwxxBack',
		data : eval({
					"workItemId" : workItemId,
					"lcslh"  : lcslh
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
		rwxxData = data.gotoList;
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

function goTo() {
	
	var rows = $("#rwtb").datagrid("getSelections");

	for (var i = 0; i < rows.length; i++) {
		
		//alert(rows[i].HDDYID);
		//return;
		parent.cssnj_workflow_goTo(rows[i].HDDYID);
		
		return;
	}
	alert("请选择跳转的节点");
	
}
