var _url ="/cssnj/ajax.action?tld=GzlStartTestService";
function init() {
	$("#lcslid").val(cssnj_workflow_lcslh);
}

// 初始化表格
function initDatagrid() {
}
// 加载成功执行方法
function dataLoadSuccess() {
}
function saveData() {
	alertBottomRightAutoClose("\u63d0\u793a", "\u4fdd\u5b58\u6210\u529f"+cssnj_workflow_lcslh);
}
//页面初始化
$(function () {
			//初始化工作流 
	cssnj_workflow_init();
});

//加载工作流数据成功
function cssnj_workflow_init_success() {
	if (cssnj_workflow_lcslh == null || cssnj_workflow_lcslh == "") {
	} else {
		//业务初始化方法
		init();
	}
}

//加载工作流数据失败
function cssnj_workflow_init_fail(errorMsg) {
	cssnj_workflow_alert(errorMsg);
}

//启动成功后事件
function cssnj_workflow_start_after_success(lcslh) {
}

//保存前
function cssnj_workflow_save_before() {
		//表单验证
	if (cssnj_workflow_lcslh == null || cssnj_workflow_lcslh == "") {
			//启动工作流
		var dataObj = new Object();
		var lcdyid = "GX_NN_0001";
		dataObj.bizId = lcdyid;
		dataObj.bizMc = lcdyid;
		dataObj.lcdyid = lcdyid;
		dataObj.lcjgdm = "23201051100";
		dataObj.qdgw = "232000000040";
		dataObj.qdjg = "23201051100";
		dataObj.qdry = "23201000946";
		cssnj_workflow_start_self(dataObj);
			//推送人员列表 
		dataObj.cssnj_workflow_params_hj1 = "1";
		dataObj.tsryList ='{GX_NN_0001_1:[{dbry_dm:"23201000946",dbry_jgdm:"23201051100",dbry_gwdm:"232000000040"},{dbry_dm:"23201001282",dbry_jgdm:"23201231200",dbry_gwdm:"232000000019"}]}';
		dataObj.csryList ='{GX_NN_0001_1:[{dbry_dm:"23201000946",dbry_jgdm:"23201051100",dbry_gwdm:"232000000040"},{dbry_dm:"23201001282",dbry_jgdm:"23201231200",dbry_gwdm:"232000000019"}]}';
			//cssnj_workflow_start(dataObj);
	} else {
			//保存业务方法
		saveData();
	}
}
//启动前事件
function cssnj_workflow_start_before() {
	return true;
}
function cssnj_workflow_start_after_fail(errorMsg) {
	alert(errorMsg);
}

// 推送前事件
function cssnj_workflow_send_before() {
	cssnj_workflow_setVarible("tybj", $("#tybj").textbox('getValue'));
	cssnj_workflow_setVarible("ts", $("#ts").textbox('getValue'));
	cssnj_workflow_setVarible("user", $("#zsbj").textbox('getValue'));
	cssnj_workflow_setVarible("yhbj", $("#yhbj").textbox('getValue'));
	cssnj_workflow_setVarible("a", "0");
	saveData();
	return true;
}

// 回退前事件
function cssnj_workflow_back_before() {
	return true;
}

// 跳转前事件
function cssnj_workflow_goTo_before() {
	return true;
}

// 委托前事件
function cssnj_workflow_wt_before() {
	return true;
}

//回退委托前
function cssnj_workflow_htwt_before() {
	return true;
}


// 作废前事件
function cssnj_workflow_zflc_before() {
	return true;
}

// 激活前事件
function cssnj_workflow_jhlc_before() {
	return true;
}

// 挂起前事件
function cssnj_workflow_gqlc_before() {
	return true;
}

