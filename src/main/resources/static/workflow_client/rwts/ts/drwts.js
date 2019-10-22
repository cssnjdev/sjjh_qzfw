var _url = cssnj_workflow_server + '/cssnj/ajax.action?tld=TsgzService';
var _url_init = _url + '_init';
var _url_select = _url + '_select';
var _url_insert = _url + '_insertSelective';
var _url_updateByPKeySelective = _url + '_updateByPKeySelective';
var _url_selectByPKey = _url + '_selectByPKey';
var _url_deleteByPKey = _url + '_deleteByPKey';

var tsryTb = '#tsryTb';
var gcryTb = '#gcryTb';
var jgjc = "";
var jgfw;
var gwDm;
var xzlx;
var kzxx1 = "";
var kzxx2 = "";
var kzxx3 = "";
var kzxx4 = "";
var getTreeList = "";
var jgdm = "";

var cs_jgfw;
var cs_jgjc;
var cs_gwDm;
var cs_xzlx;
var cs_kzxx1 = "";
var cs_kzxx2 = "";
var cs_kzxx3 = "";
var cs_kzxx4 = "";
var cs_getTreeList = "";
var cs_jgdm = "";
var bizId = "";
var hddyid = "";

var tsTreeLoaded = false;
var gcTreeLoaded = false;
var tsryTree = "#tsryTree";
var gcryTree = "#gcryTree";
var treeData = null;
var tsgzuuid;
var csgzuuid;
var lcslid;
var workItemId;

var fields = [[{
			title : '选择',
			field : 'ck',
			checkbox : 'true'
		}, {
			title : cssnj_workflow_rwts_rymc,
			field : 'swryxm',
			sortable : 'true',
			width : '30%',
			hidden : cssnj_workflow_rwts_rymc_hidden
		}, {
			title : cssnj_workflow_rwts_gwmc,
			field : 'gwmc',
			sortable : 'true',
			width : '30%',
			hidden : cssnj_workflow_rwts_gwmc_hidden
		}, {
			title : cssnj_workflow_rwts_jgmc,
			field : 'swjgmc',
			sortable : 'true',
			width : '30%',
			hidden : cssnj_workflow_rwts_jgmc_hidden
		}, {
			field : 'swrydm',
			hidden : true
		}, {
			field : 'swjgdm',
			hidden : true
		}, {
			field : 'gwdm',
			hidden : true
		}]];

$(function() {
			var urldz = location.href;
			tsgzuuid = urldz.GetValue("tsgzuuid");
			csgzuuid = urldz.GetValue("csgzuuid");
			lcslid = urldz.GetValue("lcslid");
			workItemId=urldz.GetValue("workItemId");
			hddyid = urldz.GetValue("hddyId");
			getTsgz();
			
			if (csgzuuid != null && csgzuuid != "") {
				getCsgz();
			} else {
				var gcrTab = $('#tabs').tabs("getTab", "观察人员").panel('options').tab;
				gcrTab.hide();
			}
		});

function getTsgz() {
	$.ajax({
				type : "post",
				url : _url_init + "&callback=systemInit",
				data : eval({
							'tsgzuuid' : tsgzuuid,
							'lcslid' : lcslid,
							'workItemId' : workItemId
						}),
				dataType : "jsonp",
				cache : false,
				async : false,
				success : function(data) {
				}
			});

}

String.prototype.GetValue = function(parm) {
	var reg = new RegExp("(^|&)" + parm + "=([^&]*)(&|$)");
	var r = this.substr(this.indexOf("\?") + 1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}
function systemInit(data) {
	jgjc = data.jgjc;
	jgfw = data.jgfw;
	gwDm = data.gwDm;
	jgdm = data.jgdm;
	xzlx = data.xzlx;
	kzxx1 = data.kzxx1;
	kzxx2 = data.kzxx2;
	kzxx3 = data.kzxx3;
	kzxx4 = data.kzxx4;
	bizId = data.bizId;
	getTreeList = data.getTreeList;
	getTreeData();
}

function systemInit2(data) {
	cs_jgjc = data.jgjc;
	cs_jgfw = data.jgfw;
	cs_gwDm = data.gwDm;
	cs_jgdm = data.jgdm;
	cs_xzlx = data.xzlx;
	cs_kzxx1 = data.kzxx1;
	cs_kzxx2 = data.kzxx2;
	cs_kzxx3 = data.kzxx3;
	cs_kzxx4 = data.kzxx4;
	bizId = data.bizId;
	cs_getTreeList = data.getTreeList;
	getTreeData2();
}

function getTreeData() {
	
	var ajaxUrl = "";
	if (getTreeList != null && getTreeList != "") {
		ajaxUrl = getTreeList;
	} else {
		ajaxUrl = _url + "_getTree";
		
	}
	//alert(ajaxUrl + "&callback=initTree");
	

		$.ajax({
			type : "post",
			url : ajaxUrl + "&callback=initTree",
			data :  eval({
						'jgfw' : jgfw,
						'jgjc' : jgjc,
						'gwDm' : gwDm,
						'jgDm' : jgdm,
						'kzxx1' : kzxx1,
						'kzxx2' : kzxx2,
						'kzxx3' : kzxx3,
						'kzxx4' : kzxx4,
						'lcslh' : lcslid,
						'bizId' : bizId,
						'hddyid' : hddyid
					}),
			dataType : "jsonp",
			cache : false,
			async : false,
			success : function(data) {
			}
		});
		
}



function getTreeData2() {
	
	var ajaxUrl = "";
	if (cs_getTreeList != null && cs_getTreeList != "") {
		ajaxUrl = cs_getTreeList;
	} else {
		ajaxUrl = _url + "_getTree";
		
	}
	//alert(ajaxUrl);
	

		$.ajax({
			type : "post",
			url : ajaxUrl + "&callback=initTree2",
			data :  eval({
						'jgfw' : cs_jgfw,
						'jgjc' : cs_jgjc,
						'gwDm' : cs_gwDm,
						'jgDm' : cs_jgdm,
						'kzxx1' : cs_kzxx1,
						'kzxx2' : cs_kzxx2,
						'kzxx3' : cs_kzxx3,
						'kzxx4' : cs_kzxx4,
						'lcslh' : lcslid,
						'bizId' : bizId,
						'hddyid' : hddyid
					}),
			dataType : "jsonp",
			cache : false,
			async : false,
			success : function(data) {
			}
		});
		
}




function initTree(data) {
	treeData = data;
	initTsryTree();
}

function initTree2(data) {
	treeData = data;
	initGcryTree();
}

function getCsgz() {
	$.ajax({
				type : "post",
				url : _url_init + "&callback=systemInit2",
				data : eval({
							'csgzuuid' : csgzuuid,
							'lcslid' : lcslid,
							'workItemId' : workItemId
							
						}),
				dataType : "jsonp",
				cache : false,
				async : false,
				success : function(data) {
				}
			});

}

function initTsryTree() {
	// 加载树
	$(tsryTree).tree({
		data : treeData,
		onSelect : function(node) {
			// $(this).tree('toggle', node.target);
			// swjgDm = node.id + ",";
			// if ($('#jgTree').tree('isLeaf', node.target)) {
			// submit_search_ff();
			// } else {
			// var children = $('#jgTree').tree('getChildren',
			// node.target);
			// for (var i = 0; i < children.length; i++) {
			// swjgDm += children[i].id + ',';
			// }
			// }
		},
		onBeforeLoad : function(node, param) {
			if (tsTreeLoaded) {
				return false;
			}
		},
		onCheck : function(node, checked) {
			if ($(tsryTree).tree('isLeaf', node.target)) {
				if (checked) {
					var row = {
						swryxm : node.rymc,
						swrydm : node.rydm,
						gwmc : node.gwmc,
						swjgmc : node.jgmc,
						swjgdm : node.jgdm,
						gwdm : node.gwdm
					};
					if(node.rydm == null) {
						return false;
					}
					$(tsryTb).datagrid('appendRow', row);
				} else {
					var index = $(tsryTb).datagrid('getRowIndex', node.id);
					if (index > -1) {
						$(tsryTb).datagrid('deleteRow', index);
					}
				}
			} else {
				if (checked) {
					var children = $(tsryTree).tree('getChildren', node.target);
					for (var i = 0; i < children.length; i++) {
						if ($(tsryTree).tree('isLeaf', children[i].target)) {
							var parent = $(tsryTree).tree("getParent",
									children[i].target);
							var row = {
								swryxm : children[i].rymc,
								swrydm : children[i].rydm,
								gwmc : children[i].gwmc,
								swjgmc : children[i].jgmc,
								swjgdm : children[i].jgdm,
								gwdm : children[i].gwdm
							};
							$(tsryTb).datagrid('appendRow', row);
						}
					}
				} else {
					var children = $(tsryTree).tree('getChildren', node.target);
					for (var i = 0; i < children.length; i++) {
						if ($(tsryTree).tree('isLeaf', children[i].target)) {
							var index = $(tsryTb).datagrid('getRowIndex',
									children[i].id);
							if (index > -1) {
								$(tsryTb).datagrid('deleteRow', index);
							}
						}
					}
				}
			}
		},
		onLoadSuccess : function() {
			treeLoadSuccess();
		}
	});
}

function initGcryTree() {
	// 加载树
	$(gcryTree).tree({
		data : treeData,
		onSelect : function(node) {
			// $(this).tree('toggle', node.target);
			// swjgDm = node.id + ",";
			// if ($('#jgTree').tree('isLeaf', node.target)) {
			// submit_search_ff();
			// } else {
			// var children = $('#jgTree').tree('getChildren',
			// node.target);
			// for (var i = 0; i < children.length; i++) {
			// swjgDm += children[i].id + ',';
			// }
			// }
		},
		onBeforeLoad : function(node, param) {
			if (gcTreeLoaded) {
				return false;
			}
		},
		onCheck : function(node, checked) {
			if ($(gcryTree).tree('isLeaf', node.target)) {
				if (checked) {
					var row = {
							swryxm : node.rymc,
							swrydm : node.rydm,
							gwmc : node.gwmc,
							swjgmc : node.jgmc,
							swjgdm : node.jgdm,
							gwdm : node.gwdm
					};
					$(gcryTb).datagrid('appendRow', row);
				} else {
					var index = $(gcryTb).datagrid('getRowIndex', node.id);
					if (index > -1) {
						$(gcryTb).datagrid('deleteRow', index);
					}
				}
			} else {
				if (checked) {
					var children = $(gcryTree).tree('getChildren', node.target);
					for (var i = 0; i < children.length; i++) {
						if ($(gcryTree).tree('isLeaf', children[i].target)) {
							var parent = $(gcryTree).tree("getParent",
									children[i].target);
							var jgmc = parent.text;
							var row = {
									swryxm : children[i].rymc,
									swrydm : children[i].rydm,
									gwmc : children[i].gwmc,
									swjgmc : children[i].jgmc,
									swjgdm : children[i].jgdm,
									gwdm : children[i].gwdm
							};
							$(gcryTb).datagrid('appendRow', row);
						}
					}
				} else {
					var children = $(gcryTree).tree('getChildren', node.target);
					for (var i = 0; i < children.length; i++) {
						if ($(gcryTree).tree('isLeaf', children[i].target)) {
							var index = $(gcryTb).datagrid('getRowIndex',
									children[i].id);
							if (index > -1) {
								$(gcryTb).datagrid('deleteRow', index);
							}
						}
					}
				}
			}
		},
		onLoadSuccess : function() {
			gcryTreeLoadSuccess();
		}
	});
}

function treeLoadSuccess() {
	tsTreeLoaded = true;
	initDatagrid();
}

function gcryTreeLoadSuccess() {
	gcTreeLoaded = true;
	initGcryDatagrid();
}

// function getSwjdDm() {// 参数为树的ID，注意不要添加#
// var roots = $('#jgTree').tree('getRoots'), children, i, j;
// for (i = 0; i < roots.length; i++) {
// swjgDm += roots[i].id + ",";
// children = $('#jgTree').tree('getChildren', roots[i].target);
// for (j = 0; j < children.length; j++) {
// swjgDm += children[j].id + ",";
// }
// }
// }
//
// function getChildren() {
// var node = $('#jgTree').tree('getSelected');
// if (node) {
// var children = $('#jgTree').tree('getChildren', node.target);
// } else {
// var children = $('#jgTree').tree('getChildren');
// }
// var s = '';
// for (var i = 0; i < children.length; i++) {
// s += children[i].id + ',';
// }
// }

// 初始化表格
function initDatagrid() {
	$(tsryTb).datagrid({
				title : "已选择推送人员",
				idField : 'swrydm',
				collapsible : false,
				rownumbers : true,
				remoteSort : false,
				nowrap : true,
				striped : true,
				fitColumns : true,
				singleSelect : false,
				fit : false,
				checkbox : true,
				columns : fields,
				toolbar : "#tsryBar",
				onLoadSuccess : function() {
					// dataLoadSuccess();
				}
			});
}

function initGcryDatagrid() {
	$(gcryTb).datagrid({
				title : '已选择观察人员',
				idField : 'swrydm',
				collapsible : false,
				rownumbers : true,
				remoteSort : false,
				nowrap : true,
				striped : true,
				fitColumns : true,
				singleSelect : false,
				fit : false,
				checkbox : true,
				columns : fields,
				toolbar : "#gcryBar",
				onLoadSuccess : function() {
					// dataLoadSuccess();
				}
			});
}

// 初始化表格
// function initDatalist() {
// getSwjdDm();
// $(_tt).datalist({
// url : _url_select,
// method : 'get', // 请求方式
// queryParams : {
// swjgDm : swjgDm,
// gwDm : gwDm
// },
// checkbox : true,
// valueField : 'SWRYDM',
// textField : 'SWRYXM',
// singleSelect : true,// true 单选 false 多选
// // toolbar : "#ttbar",
// onLoadSuccess : function() {
// dataLoadSuccess();
// }
// });
// }
// 加载成功执行方法
function dataLoadSuccess() {
};
// 查询提交
function submit_search_ff() {
	if (swjgDm != '' && swjgDm != null) {
		$(tsryTb).datagrid({
					queryParams : {
						swjgDm : swjgDm,
						gwDm : gwDm
					}
				});
	}
}

function deleteRow(treeName, gridName) {
	var rows = $(gridName).datagrid("getChecked");
	if (rows.length > 0) {
		for (var i = rows.length - 1; i >= 0; i--) {
			var index = $(gridName).datagrid("getRowIndex", rows[i]);
			var swrydm = rows[i].swrydm;
			$(gridName).datagrid("deleteRow", index);
			var checkedNodes = $(treeName).tree("getChecked");
			if (checkedNodes) {
				for (var j = 0; j < checkedNodes.length; j++) {
					if (swrydm == checkedNodes[j].id) {
						$(treeName).tree("uncheck", checkedNodes[j].target);
					}
				}
			}
		}
	}
}

function send() {
	var rows = $(tsryTb).datagrid("getRows");
	var tsrys = new Array();
	if (rows.length > 0) {
		for (var i = 0; i < rows.length; i++) {
			var swrydm = rows[i].swrydm;
			var gwdm = rows[i].gwdm;
			var swjgdm = rows[i].swjgdm;
			var tsry = {
				dbry_dm : swrydm,
				dbry_jgdm : swjgdm,
				dbry_gwdm : gwdm
			}
			tsrys.push(tsry);
		}
	} else {
		 $.messager.alert("警告", "推送人员不能为空", "error");
	}
	
	var gcrys = new Array();
	if (csgzuuid != null && csgzuuid != "") {
		rows = $(gcryTb).datagrid("getRows");
		
		for (var i = 0; i < rows.length; i++) {
			var swrydm = rows[i].swrydm;
			var gwdm = rows[i].gwdm;
			var swjgdm = rows[i].swjgdm;
			var tsry = {
				dbry_dm : swrydm,
				dbry_jgdm : swjgdm,
				dbry_gwdm : gwdm
			}
			gcrys.push(tsry);
		}
	} else {
		gcrys.push("");
	}

	// window.open-----------
	// window.opener.cssnj_workflow_setTsry(JSON.stringify(tsrys),
	// JSON.stringify(gcrys));
	// window.close();

	// 模态窗口
	parent.cssnj_workflow_tsfs = "5";
	parent.cssnj_workflow_setTsry(JSON.stringify(tsrys), JSON.stringify(gcrys));
	parent.modalHide('#tsModal', '', parent.hideCallBack);
}
