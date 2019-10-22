var _url = cssnj_workflow_server + '/cssnj/ajax.action?tld=TsgzService';
var _url_init = _url + '_init';
var _url_select = _url + '_select';
var jgjc = "";
var jgfw;
var gwDm;
var znDm;
var getTreeList = "";
var jgdm = "";
var xzlx;
var kzxx1 = "";
var kzxx2 = "";
var kzxx3 = "";
var kzxx4 = "";
var bizId = "";
var hddyid = "";
var treeLoaded = false;
var treeName = "#wtryTree";
var gridName = '#wtryTb';
var treeData = null;

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

String.prototype.GetValue = function(parm) {
	var reg = new RegExp("(^|&)" + parm + "=([^&]*)(&|$)");
	var r = this.substr(this.indexOf("\?") + 1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

var wtgzuuid;
var lcslid;
var workItemId;
$(function() {
			var urldz = location.href;
			wtgzuuid = urldz.GetValue("wtgzuuid");
			lcslid = urldz.GetValue("lcslid");
			workItemId = urldz.GetValue("workItemId");
			hddyid = urldz.GetValue("hddyId");
			//alert(hddyid);
			$.ajax({
						type : "post",
						url : _url_init + "&callback=systemInit",
						data : eval({
									'wtgzuuid' : wtgzuuid,
									'lcslid' : lcslid,
									'workItemId' : workItemId
								}),
						dataType : "jsonp",
						cache : false,
						async : false,
						success : function(data) {
						}
					})
		});
function systemInit(data) {
	jgjc = data.jgjc;
	jgfw = data.jgfw;
	gwDm = data.gwDm;
	znDm = data.znDm;
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

function getTreeData() {
	
	

	  var ajaxUrl = "";
	  if (getTreeList != null && getTreeList != "") {
	    ajaxUrl = getTreeList;
	  } else {
	    ajaxUrl = _url + "_getTree";
	    
	  }
	  
	  
	    $.ajax({
	      type : "post",
	      url : ajaxUrl + "&callback=initTree",
	      data : eval({
	      		'jgjc' : jgjc,
	            'jgfw' : jgfw,
	            'gwDm' : gwDm,
	            'znDm' : znDm,
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

function initTree(data) {
	// 加载树
	$(treeName).tree({
		data : data,
		onSelect : function(node) {
		},
		onBeforeLoad : function(node, param) {
			if (treeLoaded) {
				return false;
			}
		},
		onCheck : function(node, checked) {
			if ($(treeName).tree('isLeaf', node.target)) {
				if (checked) {
					
					var row = {
							swryxm : node.rymc,
				              swrydm : node.rydm,
				              gwmc : node.gwmc,
				              swjgmc : node.jgmc,
				              swjgdm : node.jgdm,
				              gwdm : node.gwdm
					};
					$(gridName).datagrid('appendRow', row);
				} else {
					var index = $(gridName).datagrid('getRowIndex', node.id);
					if (index > -1) {
						$(gridName).datagrid('deleteRow', index);
					}
				}
			} else {
				if (checked) {
					var root = $(treeName).tree("getRoot");
					var gwdm = root.id;
					var gwmc = root.text;
					var children = $(treeName).tree('getChildren', node.target);
					for (var i = 0; i < children.length; i++) {
						if ($(treeName).tree('isLeaf', children[i].target)) {
							var row = {
									  swryxm : children[i].rymc,
					                  swrydm : children[i].rydm,
					                  gwmc : children[i].gwmc,
					                  swjgmc : children[i].jgmc,
					                  swjgdm : children[i].jgdm,
					                  gwdm : children[i].gwdm
							};
							$(gridName).datagrid('appendRow', row);
						}
					}
				} else {
					var children = $(treeName).tree('getChildren', node.target);
					for (var i = 0; i < children.length; i++) {
						if ($(treeName).tree('isLeaf', children[i].target)) {
							var index = $(gridName).datagrid('getRowIndex',
									children[i].id);
							if (index > -1) {
								$(gridName).datagrid('deleteRow', index);
							}
						}
					}
				}
			}
		},
		onLoadSuccess : function() {
			treeLoaded = true;
			initDatagrid();
		}
	});
}

// 初始化表格
function initDatagrid() {
	$(gridName).datagrid({
				title : "已选择委托人员",
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
				toolbar : "#wtryBar",
				onLoadSuccess : function() {
					dataLoadSuccess();
				}
			});
}

// 加载成功执行方法
function dataLoadSuccess() {
};

function deleteRow() {
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
	var rows = $(gridName).datagrid("getRows");
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
		 $.messager.alert("警告", "委托人员不能为空", "error");
	}

	var obj = new Object();
	obj.wtkssj = $('#wtkssj').val();
	obj.wtjssj = $('#wtjssj').val();
	obj.wtms = $('#wtms').val();
	obj.wtryList = JSON.stringify(tsrys);
	parent.cssnj_workflow_saveWt(obj);
	// 模态窗口
	// parent.cssnj_workflow_setTsry(JSON.stringify(tsrys));
	// parent.modalHide('#tsModal', '', parent.hideCallBack);
}
