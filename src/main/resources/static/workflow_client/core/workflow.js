
//document.write("<script language=javascript src='" + cssnjworkflowprojectname + "/workflow_client/config/workflow_config.js'></script>");
var cssnj_workflow_processInstanceId = "";
var cssnj_workflow_lcslh = "";
var cssnj_workflow_workItemId = "";
//当前workitem的任务类型 是 推送任务、回退任务、委托任务
var cssnj_workflow_rwlx = "";
var cssnj_workflow_taskId =  "";
var cssnj_workflow_hddyId = "";
var cssnj_workflow_target = "";
//默认推送方式为1推送、2退回、3跳转、4委托
var cssnj_workflow_tsfs = "1";
var cssnj_workflow_wtgz = "";
var cssnj_workflow_tsjgfw = "";
//默认状态为运行----
var cssnj_workflow_lczt = "1";
var cssnj_workflow_rwzt = "1";

//---任务超期 流程超期
var cssnj_workflow_rwcq = "";
var cssnj_workflow_lccq = "";

//是否审批 和  是否审批意见 
var cssnj_workflow_sfspyj = "";
var cssnj_workflow_spyj = "";
//流程参数
var cssnj_workflow_varibles = "";

//是否查询到回退列表 
var cssnj_workflow_canBack = "0";
//抄送3  在办 1  已办2
var cssnj_workflow_csswflx = "";

//是否控制toolbar 显示或者隐藏
var cssnj_workflow_toolbar = "0";
//---toolbar show or hide
var cssnj_workflow_toolbar_bc = "1";
var cssnj_workflow_toolbar_ts = "";
var cssnj_workflow_toolbar_wt = "";
var cssnj_workflow_toolbar_thwt = "";
var cssnj_workflow_toolbar_ht = "";
var cssnj_workflow_toolbar_tz = "";
var cssnj_workflow_toolbar_gq = "";
var cssnj_workflow_toolbar_jh = "";
var cssnj_workflow_toolbar_zf = "";
var cssnj_workflow_toolbar_ck = "";


//-------判断页面是否加载过toolbar
var cssnj_workflow_toolbar_created = "0";

//推送页面
var cssnj_workflow_tsymUrl = cssnjworkflowprojectname + "/workflow_client/rwts/ts/rwts.html?a=" + Math.random();
//委托页面 
var cssnj_workflow_wtymUrl = cssnjworkflowprojectname + "/workflow_client/rwts/wt/rwwt.html?a=" + Math.random();
//跳转页面 
var cssnj_workflow_tzymUrl = cssnjworkflowprojectname + "/workflow_client/rwts/tz/rwtz.html?a=" + Math.random();
//回退页面
var cssnj_workflow_htymUrl = cssnjworkflowprojectname + "/workflow_client/rwts/ht/rwht.html?a=" + Math.random();
//流程历史页面
var cssnj_workflow_historyymUrl = cssnjworkflowprojectname + "/workflow_client/history/history.html?a=" + Math.random();
//启动完成页面
var cssnj_workflow_qdwcUrl = cssnjworkflowprojectname + "/workflow_client/response/qdwc.html?a=" + Math.random();
//推送完成页面
var cssnj_workflow_tswcUrl = cssnjworkflowprojectname + "/workflow_client/response/tswc.html?a=" + Math.random();
//委托完成页面
var cssnj_workflow_wtwcUrl = cssnjworkflowprojectname + "/workflow_client/response/wtwc.html?a=" + Math.random();
//委托退回页面
var cssnj_workflow_wtthUrl = cssnjworkflowprojectname + "/workflow_client/response/wtth.html?a=" + Math.random();
//作废流程返回页面
var cssnj_workflow_zfwcUrl = cssnjworkflowprojectname + "/workflow_client/response/zfwc.html?a=" + Math.random();
//激活流程返回页面
var cssnj_workflow_jhwcUrl = cssnjworkflowprojectname + "/workflow_client/response/jhwc.html?a=" + Math.random();
//挂起流程返回页面
var cssnj_workflow_gqwcUrl = cssnjworkflowprojectname + "/workflow_client/response/gqwc.html?a=" + Math.random();


// 参数对象--
var variblesObj = new Object();

// 下一环节信息
var cssnj_workflow_nextNodes;

// -------------------支持用户重载 -----------------
function cssnj_workflow_alert(errorMsg) {
	alert(errorMsg);
}

// 初始化工作流自定义扩展js 必须实现的init方法
function cssnj_workflow_init_ext() {
	
}
//------------------before-----------------------

//启动前事件
function cssnj_workflow_start_before() {
	return true;
}

// 暂存前事件
function cssnj_workflow_save_before() {
	return true;
}

// 推送前事件
function cssnj_workflow_send_before() {
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


//------------------------------------------------




//------------------success-----------------------
// 加载工作流数据成功
function cssnj_workflow_init_success() {
}

// 启动成功事件
function cssnj_workflow_start_after_success(lcslh) {
	cssnj_workflow_alert("启动成功");
}

//启动成功事件
function cssnj_workflow_start_after_success(lcslh, workItemId) {
	cssnj_workflow_alert("启动成功");
}


// 推送完成事件
function cssnj_workflow_send_after_success(data) {
	if("br"==data.tsjgfw){
			cssnj_workflow_jsonp(cssnj_workflow_server  + "/wfctrl/workflow.action?tld=WfWebUtil_claimTaskBrDbrw", data, "cssnj_workflow_getBrDbrw_callBack");
	}else{
		cssnj_workflow_alert("推送成功");
	}
}
function cssnj_workflow_getBrDbrw_callBack(data){
	if(data.url!=""){
		window.close();
		window.open(data.URL + '&lcslh=' + data.LCSLH + '&workItemId=' + data.WORKITEMID);
	}else{
		cssnj_workflow_alert("任务完成");
	}
}

// 回退完成事件
function cssnj_workflow_back_after_success(data) {
	cssnj_workflow_alert("回退成功");
}


// 跳转完成事件
function cssnj_workflow_goTo_after_success(data) {
	cssnj_workflow_alert("跳转成功");
}


// 委托完成事件
function cssnj_workflow_wt_after_success(data) {
	cssnj_workflow_alert("委托成功");
}


//回退委托成功 
function cssnj_workflow_htwt_after_success() {
	cssnj_workflow_alert("回退委托成功");
}

// 作废 完成事件
function cssnj_workflow_zflc_after_success(data) {
	cssnj_workflow_alert("作废成功");
}

//激活完成事件
function cssnj_workflow_jhlc_after_success(data) {
	cssnj_workflow_alert("激活成功");
}

//挂起完成事件
function cssnj_workflow_gqlc_after_success(data) {
	cssnj_workflow_alert("挂起成功");
}

//------------------------------------------------

//------------------fail-----------------------
// 加载工作流数据失败
function cssnj_workflow_init_fail(errorMsg) {
	cssnj_workflow_alert(errorMsg);
}

// 启动失败事件
function cssnj_workflow_start_after_fail(errorMsg) {
	cssnj_workflow_alert(errorMsg);
}

// 推送失败事件
function cssnj_workflow_send_after_fail(tsfs, errorMsg) {
	cssnj_workflow_alert(errorMsg);
}


//回退委托失败 
function cssnj_workflow_htwt_after_fail(errorMsg) {
	cssnj_workflow_alert(errorMsg);
}

// 委托失败事件
function cssnj_workflow_wt_after_fail(errorMsg) {
	cssnj_workflow_alert(errorMsg);
}

// 作废失败事件
function cssnj_workflow_zflc_after_fail(errorMsg) {
	cssnj_workflow_alert(errorMsg);
}

// 激活失败事件
function cssnj_workflow_jhlc_after_fail(errorMsg) {
	cssnj_workflow_alert(errorMsg);
}

// 挂起失败事件
function cssnj_workflow_gqlc_after_fail(errorMsg) {
	cssnj_workflow_alert(errorMsg);
}


//------------------------------------------------




// ----------工作流弹框方法
function cssnj_workflow_open(url, nodeName) {
	
	document.getElementById("cssnjworkflowiframe").src = '';
	document.getElementById("cssnjworkflowopenwindowtitle").innerHTML = nodeName;
	document.getElementById('cssnj_workflow_oepnwindow_div').style.display='block';
	document.getElementById('cssnj_workflow_oepnwindow_mask').style.display='block';
	document.getElementById("cssnjworkflowiframe").src = url + "&a=" + Math.random();
	//window.open(url, 'newwindow', 'height=700, width=900, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
}




// -----------
//---初始化工作流 ---
//自动初始化
var urldz = location.href;
function cssnj_workflow_init() {
		cssnj_workflow_initProcess(urldz.Cssnj_workflow_GetValue("lcslh"), urldz.Cssnj_workflow_GetValue("workItemId"), urldz.Cssnj_workflow_GetValue("csswflx"));
}
//手动传参初始化
function cssnj_workflow_init2(obj) {
	
	if (obj != null) {
		cssnj_workflow_initProcess(obj.lcslh, obj.workItemId, obj.csswflx);
	} else {
		
	}
}

//jsonp 请求方法 
function cssnj_workflow_jsonp(url, params, callback) {
	
	if (callback != null && callback != '') {
		url = url +  "&callback=" + callback;
	}
	
    var JSONP=document.createElement("script");    
    JSONP.type="text/javascript";    
    JSONP.src=url + "&" + cssnj_workflow_parseParam(params);    
    document.getElementsByTagName("head")[0].appendChild(JSONP);
    
    /*
    $.ajax({  
	      type: "post",  
	      async: false,  
	      url: url,  
	      dataType: "jsonp", 
	      data: params
	  });*/
}



//初始化弹出窗口
function cssnj_workflow_init_openwindow() {
	
	var cssnjOpenDiv = document.createElement("div");
	cssnjOpenDiv.innerHTML =  "<div id=\"cssnj_workflow_oepnwindow_div\" style=\"display:none;position:absolute;top:15%;left:10%;width:80%;height:570px;padding:2px;border:6px solid lightgray;background-color:white;z-index:1002;overflow:auto;\">\n" +
	"    <div style=\"height:20px;padding:15px;border-bottom:1px solid #e5e5e5\">\n" + 
	"    <span id=\"cssnjworkflowopenwindowtitle\" style=\"float:left\">标题</span>\n" + 
	"    <span style=\"float:right;cursor:pointer\"> <label href=\"javascript:void(0)\" onclick=\"document.getElementById('cssnj_workflow_oepnwindow_div').style.display='none';document.getElementById('cssnj_workflow_oepnwindow_mask').style.display='none'\">\n" + 
	"    x</label></span>\n" + 
	"  </div>\n" + 
	"  <hr>\n" + 
	"    <div style=\"width:100%;height:1\">\n" + 
	"    <iframe id='cssnjworkflowiframe' frameborder=0 src=\"\" style=\"width:100%;height:500px\"></iframe>\n" + 
	"  </div>\n" + 
	"</div>\n" + 
	"<div id=\"cssnj_workflow_oepnwindow_mask\" style=\"display:none;position:absolute;top:0%;left:0%;width:100%;height:100%;background-color:#aaa;z-index:1001;-moz-opacity:0.8;opacity:.80;filter:alpha(opacity=80);\">\n" + 
	"</div>";
	document.body.appendChild(cssnjOpenDiv);
}



// 初始化 流程实例号与任务号
function cssnj_workflow_initProcess(lcslh, workItemId, csswflx) {
	
	//初始化弹出框
	cssnj_workflow_init_openwindow();
	
	cssnj_workflow_csswflx = csswflx;
	
	var obj = new Object();
	obj.lcslh = lcslh;
	obj.workItemId = workItemId;
	
	cssnj_workflow_jsonp(cssnj_workflow_server  + "/wfctrl/workflow.action?tld=WfWebUtil_initProcess", obj, "cssnj_workflow_init_callBack");
	
	// 初始化工作流自定义扩展js 实现的init方法
	try {
		cssnj_workflow_init_ext();
	} catch (e) {
		
	}
	  
}

// cssnj_workflow_init 回调方法
function cssnj_workflow_init_callBack(data) {
	
	if (data.success == "1") {
		cssnj_workflow_processInstanceId = data.workFlowData.processInstanceId;
		cssnj_workflow_taskId = data.workFlowData.taskId;
		cssnj_workflow_lcslh = data.workFlowData.lcslh;
		cssnj_workflow_workItemId = data.workFlowData.workItemId;
		cssnj_workflow_wtgz = data.workFlowData.wtgzId;
		cssnj_workflow_rwlx = data.workFlowData.rwlx;
		cssnj_workflow_rwzt = data.workFlowData.rwzt;
		cssnj_workflow_lczt = data.workFlowData.lczt;
		cssnj_workflow_sfspyj = data.workFlowData.sfspyj;
		cssnj_workflow_spyj = data.workFlowData.spyj;
		cssnj_workflow_varibles = data.workFlowData.variblesList;
		cssnj_workflow_canBack = data.workFlowData.canBack;
		
		cssnj_workflow_toolbar_bc = data.workFlowData.bc;
		cssnj_workflow_toolbar_ts = data.workFlowData.ts;
		cssnj_workflow_toolbar_wt = data.workFlowData.wt;
		cssnj_workflow_toolbar_thwt = data.workFlowData.thwt;
		cssnj_workflow_toolbar_ht = data.workFlowData.ht;
		cssnj_workflow_toolbar_tz = data.workFlowData.tz;
		cssnj_workflow_toolbar_gq = data.workFlowData.gq;
		cssnj_workflow_toolbar_jh = data.workFlowData.jh;
		cssnj_workflow_toolbar_zf = data.workFlowData.zf;
		cssnj_workflow_toolbar_ck = data.workFlowData.ck;
		cssnj_workflow_toolbar = data.workFlowData.sfsy_toolbar;
		
		cssnj_workflow_rwcq = data.workFlowData.rwcq;
		cssnj_workflow_lccq = data.workFlowData.lccq;
		
		cssnj_workflow_hddyId = data.workFlowData.hddyid;
		
		
		//alert(cssnj_workflow_toolbar_rwcq);
		//alert(cssnj_workflow_toolbar_lccq);
		
		cssnj_workflow_init_success();
		
		
		
		//是否使用工作流toolbar
		
		if (cssnj_workflow_toolbar == "1") {
			//添加工作流 按钮区域 ----
			if (document.getElementById("workflow_toolbar")) {
				
				if (cssnj_workflow_toolbar_created == "0") {
					cssnj_workflow_init_toolbar();
					cssnj_workflow_toolbar_created = "1";
				}
				
			}
			
			
			//默认初始化隐藏所有按钮 ，在创建按钮时默认为隐藏，双重保险
			cssnj_workflow_hide_toolbar();
			//按钮显示隐藏 
			cssnj_workflow_toolbar_show();
			
		}
		
		//--是否审批 
		if (cssnj_workflow_sfspyj == "1") {
			addSpyj();
			
			document.getElementById("cssnjwfspyj").value = cssnj_workflow_spyj;
		}
		
 	} else {
 		//cssnj_workflow_init_fail("初始化失败" + data.message);
 	}
	
}

//创建审批意见 
function addSpyj() {
	var spyjDiv = document.createElement("div");
	spyjDiv.id = 'spyjDiv';
	spyjDiv.innerHTML = '<table border=1 width="100%"><tr><td>处理意见</td><td><textarea id="cssnjwfspyj" style="width:100%;height:65px;border:1px solid gray"></textarea></td></tr></table>';
	document.body.appendChild(spyjDiv);
}

	
//根据条件 显示工作流按钮 --
function cssnj_workflow_toolbar_show() {
	
	
	//判断 按钮 的 显示 隐藏 
	if (cssnj_workflow_lcslh == "" || cssnj_workflow_lcslh == null || cssnj_workflow_lcslh == undefined) {
		document.getElementById("workflow_toolbar_bc").style.display = '';
	} else {
		
		if (cssnj_workflow_csswflx == "1")  {
			//alert(cssnj_workflow_lczt);
			//--流程状态 为运行 状态 
			if (cssnj_workflow_lczt == "1" && cssnj_workflow_rwzt == "1") {
				
			
				//任务超期 流程超期判断
				if (cssnj_workflow_rwcq == "0" && cssnj_workflow_lccq == "0") {
					
					
					if (cssnj_workflow_toolbar_bc == "1") {
						document.getElementById("workflow_toolbar_bc").style.display = '';
					}
					
					if (cssnj_workflow_toolbar_ts == "1") {
						document.getElementById("workflow_toolbar_ts").style.display = '';
					}
					
					if (cssnj_workflow_toolbar_wt == "1") {
						document.getElementById("workflow_toolbar_wt").style.display = '';
					}
					
					if (cssnj_workflow_toolbar_tz == "1") {
						document.getElementById("workflow_toolbar_tz").style.display = '';
					}
					
					if (cssnj_workflow_toolbar_gq == "1") {
						document.getElementById("workflow_toolbar_gqlc").style.display = '';
					}
					
					//alert(cssnj_workflow_canBack);
					//alert(cssnj_workflow_toolbar_ht);
					if (cssnj_workflow_canBack == "1") {
						if (cssnj_workflow_toolbar_ht == "1") {
							document.getElementById("workflow_toolbar_ht").style.display = '';
						}
					}
					
					if (cssnj_workflow_toolbar_zf == "1") {
						document.getElementById("workflow_toolbar_zflc").style.display = '';
					}
					
					if (cssnj_workflow_toolbar_ck == "1") {
						document.getElementById("workflow_toolbar_cklc").style.display = '';
					}
					
					
					//如果是委托任务，则可退回 
					if(cssnj_workflow_rwlx == "4") {
						if (cssnj_workflow_toolbar_thwt == "1") {
							document.getElementById("workflow_toolbar_htwt").style.display = '';
						}
					}
					
				} else {
						if (cssnj_workflow_toolbar_ck == "1") {
							document.getElementById("workflow_toolbar_cklc").style.display = '';
						}
						
						if (cssnj_workflow_lccq == "1") {
							cssnj_workflow_alert("流程超期");	
						}
						if (cssnj_workflow_rwcq == "1") {
							cssnj_workflow_alert("任务超期");	
						} 
						
						
				}
				
			} else if (cssnj_workflow_lczt == "2") {
				
				//挂起状态
				
				if (cssnj_workflow_toolbar_jh == "1") {
					document.getElementById("workflow_toolbar_jhlc").style.display = '';
				}
				
				if (cssnj_workflow_toolbar_ck == "1") {
					document.getElementById("workflow_toolbar_cklc").style.display = '';
				}
				
			} else if (cssnj_workflow_lczt == "3"){
				
				document.getElementById("workflow_toolbar_cklc").style.display = '';
				
			}  else if (cssnj_workflow_lczt == "4"){
				
				cssnj_workflow_alert("流程已作废");	
			}  else if (cssnj_workflow_rwzt != "1"){
				
				document.getElementById("workflow_toolbar_cklc").style.display = '';
			} 
		} else if (cssnj_workflow_csswflx == "2") {
			document.getElementById("workflow_toolbar_cklc").style.display = '';
		} else if (cssnj_workflow_csswflx == "3") {
			document.getElementById("workflow_toolbar_cklc").style.display = '';
		}
	}		
}

//--添加工作流按钮 
function cssnj_workflow_init_toolbar() {
	
	document.getElementById("workflow_toolbar").innerHTML = '<a id="workflow_toolbar_bc"  style="display:none"   onclick="cssnj_workflow_save()">保存</a>' +
	'<a id="workflow_toolbar_ts"  style="display:none"  onclick="cssnj_workflow_send()">推送</a>' +
	'<a id="workflow_toolbar_wt" style="display:none"  onclick="cssnj_workflow_wt()">委托</a>' + 
	'<a id="workflow_toolbar_ht"  style="display:none"  onclick="cssnj_workflow_ht()">回退</a>' + 
	'<a id="workflow_toolbar_tz"  style="display:none" onclick="cssnj_workflow_tz()">跳转</a>' + 
	'<a id="workflow_toolbar_htwt"  style="display:none" onclick="cssnj_workflow_htwt()">退回委托</a>' + 
	'<a id="workflow_toolbar_gqlc"  style="display:none"  onclick="cssnj_workflow_gqlc()">挂起流程</a>' +
	'<a id="workflow_toolbar_jhlc"  style="display:none"  onclick="cssnj_workflow_jhlc()">激活流程</a>' + 
	'<a id="workflow_toolbar_zflc" style="display:none"   onclick="cssnj_workflow_zflc()">作废流程</a>' + 
	'<a id="workflow_toolbar_cklc"  style="display:none"  onclick="cssnj_workflow_cklc()">查看流程</a>' + document.getElementById("workflow_toolbar").innerHTML;
}


//--隐藏所有按钮 
function cssnj_workflow_hide_toolbar() {
	
	document.getElementById("workflow_toolbar_bc").style.display = 'none';
	document.getElementById("workflow_toolbar_ts").style.display = 'none';
	document.getElementById("workflow_toolbar_wt").style.display = 'none';
	document.getElementById("workflow_toolbar_ht").style.display = 'none';
	document.getElementById("workflow_toolbar_tz").style.display = 'none';
	document.getElementById("workflow_toolbar_htwt").style.display = 'none';
	document.getElementById("workflow_toolbar_gqlc").style.display = 'none';
	document.getElementById("workflow_toolbar_jhlc").style.display = 'none';
	document.getElementById("workflow_toolbar_zflc").style.display = 'none';
	document.getElementById("workflow_toolbar_cklc").style.display = 'none';
	
}
	


//工作流暂存方法
function cssnj_workflow_save() {
	
	if(cssnj_workflow_save_before()) {
		
	}
}	



// 作废流程 ----
function  cssnj_workflow_zflc() {
	// alert(_cssnj_domain_path);
	$.messager.confirm('确认','您确认想要作废吗？',function(r){    
	    if (r){    
	    	if (cssnj_workflow_processInstanceId != undefined && cssnj_workflow_processInstanceId != null && cssnj_workflow_processInstanceId != "") {
	    		
	    		if (cssnj_workflow_zflc_before()) {
	    			var obj = new Object();
	    			obj.lcslh = cssnj_workflow_lcslh;
	    			cssnj_workflow_jsonp(cssnj_workflow_server  + "/wfctrl/workflow.action?tld=WfWebUtil_voidProceessByLcslid", obj, "cssnj_workflow_zflc_callBack");
	    		}
	    		
	    	} 
	    }    
	});  
	

}


// zflc jsonp callBack
function cssnj_workflow_zflc_callBack(data) {
	
	
	if (data.success == "1") {
 		
 		cssnj_workflow_zflc_after_success(data);
 		location.href = cssnj_workflow_zfwcUrl + "&workItemId=" + cssnj_workflow_workItemId + "&lcslh=" + cssnj_workflow_lcslh; 	   
 	} else {
 		cssnj_workflow_zflc_after_fail(data.message);
 	}
	
}



// 挂起流程 ----
function  cssnj_workflow_gqlc() {
	// alert(_cssnj_domain_path);
	
	if (cssnj_workflow_processInstanceId != undefined && cssnj_workflow_processInstanceId != null && cssnj_workflow_processInstanceId != "") {
		
		if (cssnj_workflow_gqlc_before()) {
			
			var obj = new Object();
			obj.lcslh = cssnj_workflow_lcslh;
			cssnj_workflow_jsonp(cssnj_workflow_server  + "/wfctrl/workflow.action?tld=WfWebUtil_suspendProcessByLcslid", obj, "cssnj_workflow_gqlc_callBack");
		}
		
	} 

}


// 挂起 jsonp callBack
function cssnj_workflow_gqlc_callBack(data) {
	
	
	if (data.success == "1") {
 		
 		cssnj_workflow_gqlc_after_success(data);
 		location.href = cssnj_workflow_gqwcUrl + "&workItemId=" + cssnj_workflow_workItemId + "&lcslh=" + cssnj_workflow_lcslh; 	   
 	} else {
 		cssnj_workflow_gqlc_after_fail(data.message);
 	}
	
}


//设置流程参数 --
function cssnj_workflow_setVarible(key, value) {
	variblesObj["cssnj_workflow_params_" + key] = value;
}

//获取参数 --
function cssnj_workflow_getVarible() {
	
}


//设置处理意见 --
function cssnj_workflow_setClyj(value) {
	cssnj_workflow_spyj = value;
}

//获取处理意见
function cssnj_workflow_getClyj() {
	return cssnj_workflow_spyj;
}

// 激活流程 ----
function  cssnj_workflow_jhlc() {
	// alert(_cssnj_domain_path);
	
	if (cssnj_workflow_processInstanceId != undefined && cssnj_workflow_processInstanceId != null && cssnj_workflow_processInstanceId != "") {
		
		if (cssnj_workflow_jhlc_before()) {
			
			var obj = new Object();
			obj.lcslh = cssnj_workflow_lcslh;
			
			cssnj_workflow_jsonp(cssnj_workflow_server  + "/wfctrl/workflow.action?tld=WfWebUtil_activateProceessByLcslid", obj, "cssnj_workflow_jhlc_callBack");
		
		}

	} else {
		
	}

}


// 激活 jsonp callBack
function cssnj_workflow_jhlc_callBack(data) {
	
	
	if (data.success == "1") {
 		
 		cssnj_workflow_jhlc_after_success(data);
 		location.href = cssnj_workflow_jhwcUrl + "&workItemId=" + cssnj_workflow_workItemId + "&lcslh=" + cssnj_workflow_lcslh; 	   
 	} else {
 		cssnj_workflow_jhlc_after_fail(data.message);
 	}
	
}




// 启动工作流----
function  cssnj_workflow_start(obj) {
	// alert(_cssnj_domain_path);
	
	if (cssnj_workflow_processInstanceId != undefined && cssnj_workflow_processInstanceId != null && cssnj_workflow_processInstanceId != "") {
		// 类型1 启动时 已经存在流程实例号
		cssnj_workflow_start_after_fail("已经有流程实例号 不能启动");
		return;
	}
	
	if (cssnj_workflow_start_before()) {
		cssnj_workflow_jsonp(cssnj_workflow_server  + "/wfctrl/workflow.action?tld=WfWebUtil_startProcess", obj, "cssnj_workflow_start_callBack");    
	}

}

// start jsonp callBack
function cssnj_workflow_start_callBack(data) {
	
	if (data.success == "1") {
 		cssnj_workflow_processInstanceId = data.processInstanceId; 
 		cssnj_workflow_lcslh = data.lcslh;
 		cssnj_workflow_start_after_success(cssnj_workflow_lcslh);
 		location.href = cssnj_workflow_qdwcUrl + "&workItemId=" + cssnj_workflow_workItemId + "&lcslh=" + cssnj_workflow_lcslh; 	   
 	} else {
 		cssnj_workflow_start_after_fail(data.message);
 	}
	
}

// start jsonp callBack
function cssnj_workflow_startSelf_callBack(data) {
	
	if (data.success == "1") {
		cssnj_workflow_lcslh = data.lcslh;
		
		var obj = new Object();
		obj.lcslh = data.lcslh;
		obj.workItemId = data.workItemId;
		obj.csswflx = '1';
		cssnj_workflow_init2(obj);
		cssnj_workflow_start_after_success(data.lcslh, data.workItemId);
 	} else {
 		cssnj_workflow_start_after_fail(data.message);
 	}
	
}



// 启动自己工作流----
function  cssnj_workflow_start_self(obj) {
	
	
	if (cssnj_workflow_processInstanceId != undefined && cssnj_workflow_processInstanceId != null && cssnj_workflow_processInstanceId != "") {
		// 类型1 启动时 已经存在流程实例号
		cssnj_workflow_start_after_fail("已经有流程实例号 不能启动");
		return;
	}
	//var data = JSON.stringify(obj);
	
	//try {
		
		if (cssnj_workflow_start_before()) {	
			//data = eval('(' + data + ')');
			//var obj = new Object();
			//obj.lcslh = cssnj_workflow_lcslh;
			
			cssnj_workflow_jsonp(cssnj_workflow_server  + "/wfctrl/workflow.action?tld=WfWebUtil_startSelfProcess", obj, "cssnj_workflow_startSelf_callBack");
       
		 }  
	//} catch (e) {
	//	cssnj_workflow_start_after_fail("启动失败");
	//}

}



function cssnj_workflow_send() {
		
		
		
	
		
		cssnj_workflow_tsfs = "1";

	// 获取推送 下一节点（并行则为多个节点）的信息 。
		
		if (cssnj_workflow_processInstanceId == undefined || cssnj_workflow_processInstanceId == null || cssnj_workflow_processInstanceId == "") {
			//不存在流程实例号
			cssnj_workflow_alert("流程实例号为空,不可操作");
			return;
		} 

		if (cssnj_workflow_taskId == undefined || cssnj_workflow_taskId == null || cssnj_workflow_taskId == "") {
			//不存在任务号
			cssnj_workflow_alert("任务号为空,不可操作");
			return;
		}
		
		if(cssnj_workflow_lczt != "1") {
			cssnj_workflow_alert("流程非运行状态,不可操作");
			return;
		} 
		if(cssnj_workflow_rwzt != "1") {
			cssnj_workflow_alert("任务非运行状态,不可操作");
			return;
		}
		
		
		
		if (cssnj_workflow_send_before()) {
			
			//获取审批意见 ----
			//--是否审批 
			if (cssnj_workflow_sfspyj == "1") {
				cssnj_workflow_setClyj(document.getElementById("cssnjwfspyj").value);
			}
			
			
			
			// 复制 设置的扩展参数对象为 new 的对象
			var dataObj = variblesObj;
			dataObj.lcslh = cssnj_workflow_lcslh;
			dataObj.processInstanceId = cssnj_workflow_processInstanceId;
			dataObj.taskId = cssnj_workflow_taskId;
			dataObj.tsfs = cssnj_workflow_tsfs;
			dataObj.target = cssnj_workflow_target;// 正常推送为空
			dataObj.workItemId = cssnj_workflow_workItemId;
			
			cssnj_workflow_jsonp(cssnj_workflow_server  + "/wfctrl/workflow.action?tld=WfWebUtil_getNextNodes", dataObj, "cssnj_workflow_getnextnodes_callBack");
			
		
		}
	
}

//回退委托 
function cssnj_workflow_htwt() {
	
	if (cssnj_workflow_processInstanceId == undefined || cssnj_workflow_processInstanceId == null || cssnj_workflow_processInstanceId == "") {
		//不存在流程实例号
		cssnj_workflow_alert("流程实例号为空,不可操作");
		return;
	} 

	if (cssnj_workflow_taskId == undefined || cssnj_workflow_taskId == null || cssnj_workflow_taskId == "") {
		//不存在任务号
		cssnj_workflow_alert("任务号为空,不可操作");
		return;
	}
	
	if(cssnj_workflow_lczt != "1") {
		cssnj_workflow_alert("流程非运行状态,不可操作");
		return;
	} 
	if(cssnj_workflow_rwzt != "1") {
		cssnj_workflow_alert("任务非运行状态,不可操作");
		return;
	}
	
	
	if (cssnj_workflow_rwlx == "4") {
		
		if (cssnj_workflow_htwt_before()) {
			
			var dataObj = new Object;
			dataObj.lcslh = cssnj_workflow_lcslh;
			dataObj.workItemId = cssnj_workflow_workItemId;
			
			cssnj_workflow_jsonp(cssnj_workflow_server  + "/wfctrl/workflow.action?tld=WfWebUtil_backWtWorkItem", dataObj, "cssnj_workflow_htwt_callBack");

		}
	} else {
		cssnj_workflow_htwt_after_fail("非委托任务,不可退回委托");
	}
}

//回退委托回掉
function cssnj_workflow_htwt_callBack(data){
	
	if (data.success == "1") {
		cssnj_workflow_htwt_after_success();
		location.href = cssnj_workflow_wtthUrl + "&workItemId=" + cssnj_workflow_workItemId + "&lcslh=" + cssnj_workflow_lcslh;
	} else {
		cssnj_workflow_htwt_after_fail(data.message);
	}
	
}


//--弹出 查看流程
function cssnj_workflow_cklc() {
	
	if (cssnj_workflow_processInstanceId == undefined || cssnj_workflow_processInstanceId == null || cssnj_workflow_processInstanceId == "") {
		//不存在流程实例号
		cssnj_workflow_alert("流程实例号为空,不可操作");
		return;
	} 
	
	cssnj_workflow_open(cssnj_workflow_historyymUrl + "&lcslh=" + cssnj_workflow_lcslh + "&processInstanceId=" + cssnj_workflow_processInstanceId, "查看流程");
}


// --弹出 委托页面
function cssnj_workflow_wt() {
	
	if (cssnj_workflow_processInstanceId == undefined || cssnj_workflow_processInstanceId == null || cssnj_workflow_processInstanceId == "") {
		//不存在流程实例号
		cssnj_workflow_alert("流程实例号为空,不可操作");
		return;
	} 

	if (cssnj_workflow_taskId == undefined || cssnj_workflow_taskId == null || cssnj_workflow_taskId == "") {
		//不存在任务号
		cssnj_workflow_alert("任务号为空,不可操作");
		return;
	}
	
	if(cssnj_workflow_lczt != "1") {
		cssnj_workflow_alert("流程非运行状态,不可操作");
		return;
	} 
	if(cssnj_workflow_rwzt != "1") {
		cssnj_workflow_alert("任务非运行状态,不可操作");
		return;
	}
	
	if (cssnj_workflow_wtgz == "") {
		cssnj_workflow_alert("没有配置委托规则");
		return;
	}
	
	if(cssnj_workflow_wt_before()) {
		cssnj_workflow_open(cssnj_workflow_wtymUrl + "&wtgzuuid=" +  cssnj_workflow_wtgz  + "&lcslid=" + cssnj_workflow_lcslh + "&workItemId=" + cssnj_workflow_workItemId + "&hddyId=" + cssnj_workflow_hddyId, "委托");
	}
}
// --弹出 跳转页面
function cssnj_workflow_tz() {
	
	if (cssnj_workflow_processInstanceId == undefined || cssnj_workflow_processInstanceId == null || cssnj_workflow_processInstanceId == "") {
		//不存在流程实例号
		cssnj_workflow_alert("流程实例号为空,不可操作");
		return;
	} 

	if (cssnj_workflow_taskId == undefined || cssnj_workflow_taskId == null || cssnj_workflow_taskId == "") {
		//不存在任务号
		cssnj_workflow_alert("任务号为空,不可操作");
		return;
	}
	
	if(cssnj_workflow_lczt != "1") {
		cssnj_workflow_alert("流程非运行状态,不可操作");
		return;
	} 
	if(cssnj_workflow_rwzt != "1") {
		cssnj_workflow_alert("任务非运行状态,不可操作");
		return;
	}
	
	if (cssnj_workflow_goTo_before()) {
		cssnj_workflow_open(cssnj_workflow_tzymUrl + "&lcslid=" + cssnj_workflow_lcslh + "&workItemId=" + cssnj_workflow_workItemId, "跳转");
	}
}
// --弹出 回退 页面
function cssnj_workflow_ht() {
	
	if (cssnj_workflow_processInstanceId == undefined || cssnj_workflow_processInstanceId == null || cssnj_workflow_processInstanceId == "") {
		//不存在流程实例号
		cssnj_workflow_alert("流程实例号为空,不可操作");
		return;
	} 

	if (cssnj_workflow_taskId == undefined || cssnj_workflow_taskId == null || cssnj_workflow_taskId == "") {
		//不存在任务号
		cssnj_workflow_alert("任务号为空,不可操作");
		return;
	}
	
	if(cssnj_workflow_lczt != "1") {
		cssnj_workflow_alert("流程非运行状态,不可操作");
		return;
	} 
	if(cssnj_workflow_rwzt != "1") {
		cssnj_workflow_alert("任务非运行状态,不可操作");
		return;
	}
	
	if (cssnj_workflow_back_before()) {
		cssnj_workflow_open(cssnj_workflow_htymUrl+"&taskId="+cssnj_workflow_taskId+"&lcslid=" + cssnj_workflow_lcslh + "&workItemId=" + cssnj_workflow_workItemId, "回退");
	}
}


// 保存委托-----
function cssnj_workflow_saveWt(obj) {
	
	if(cssnj_workflow_wt_before()) {
		

		// 复制 设置的扩展参数对象为 new 的对象
		var dataObj = new Object();
		dataObj.lcslh = cssnj_workflow_lcslh;
		dataObj.workItemId = cssnj_workflow_workItemId;
		dataObj.wtkssj = obj.wtkssj;
		dataObj.wtjssj = obj.wtjssj;
		dataObj.wtms = obj.wtms;
		dataObj.wtryList = "{wtrylist:" + obj.wtryList + "}";
		
		cssnj_workflow_jsonp(cssnj_workflow_server  +  "/wfctrl/workflow.action?tld=WfWebUtil_saveWt", dataObj, "cssnj_workflow_saveWt_callBack");
		
	     
	}  
}

// 保存委托 callback方法
function cssnj_workflow_saveWt_callBack(data) {
	if (data.success == "1") {
		cssnj_workflow_wt_after_success();
		location.href = cssnj_workflow_wtwcUrl + "&workItemId=" + cssnj_workflow_workItemId + "&lcslh=" + cssnj_workflow_lcslh;
	} else {
		cssnj_workflow_wt_after_fail(data.message);
	}
}


// start jsonp callBack--- 正常推送
function cssnj_workflow_getnextnodes_callBack(data) {
	
	if (data.success == "1") {
		
		if (data.nextNodesList == "") {
			
			if (data.hasNextNode == "1") {
				
				// 类型3 推送时 根据条件 找不到下个环节
				cssnj_workflow_send_after_fail(cssnj_workflow_tsfs, "根据当前条件 没有找到到 下一环节信息 ,不能推送");
				
				return;
			} else {
				// 当前为最后一个环节
				cssnj_workflow_complete();
				return;
			}
			
		}
	   
	    cssnj_workflow_nextNodes = data.nextNodesList;
	    cssnj_workflow_complete();
		
	} else {
		
		// 类型4 获取推送下个节点信息 报错
		cssnj_workflow_send_after_fail(cssnj_workflow_tsfs, jsonData.message);
		return;
	}
	
}


var index = 0;
// 推送校验 方法（校验推送人员）
function cssnj_workflow_complete_check() {
	
	var doComplete = "1";
    // 循环 下个节点信息--- 并行流程 会有多个下个节点 ---
	// 推送页面调用保存方法，保存方法成功会修改cssnj_workflow_nextNodes里面的推送方式，只有等所有推送方式都为0的时候 才会走
	// complete方法-------
	
    for (o in cssnj_workflow_nextNodes) {
    	
		index = o;
		if (cssnj_workflow_nextNodes[o].czzt == "0") {
    		doComplete = "0";
    		// 如果弹出方式为 ---1
        	if (cssnj_workflow_nextNodes[o].sfxsxr == "1") {
        		// 推送人员
        		cssnj_workflow_open(cssnj_workflow_tsymUrl + "&tsgzuuid=" +  cssnj_workflow_nextNodes[o].tsgz + "&csgzuuid=" +  cssnj_workflow_nextNodes[o].csgz  + "&lcslid=" + cssnj_workflow_lcslh + "&workItemId=" + cssnj_workflow_workItemId + "&hddyId=" +  cssnj_workflow_nextNodes[o].nodeId, cssnj_workflow_nextNodes[o].nodeMc);
        		return;
        	}  else {
        		
        		
        		// -----------
        		// 判断后台是否配置自定义获取人员接口--后台
        		// 如果有，则 jsonp 获取 -----
        		
        		if (cssnj_workflow_nextNodes[o].getrylist != null && cssnj_workflow_nextNodes[o].getrylist != "" ) {
        			
        			var getrylist = cssnj_workflow_nextNodes[o].getrylist;
        			
        				//自定义后台接口 
        			
        			var dataObj = cssnj_workflow_nextNodes[o];
        			dataObj.lcslid = cssnj_workflow_lcslh;
        			dataObj.workItemId = cssnj_workflow_workItemId;
        			dataObj.tsgzuuid = cssnj_workflow_nextNodes[o].tsgz;
        			dataObj.csgzuuid = cssnj_workflow_nextNodes[o].csgz;
        			dataObj.getrylist = '';
        			
        			//alert(getrylist);
        			cssnj_workflow_jsonp(getrylist, dataObj, "cssnj_workflow_getNodeTsry_callBack");
        			
        			
        		
        		} else {
        			
        			//默认后台计算人员接口

        			var dataObj = cssnj_workflow_nextNodes[o];
        			dataObj.lcslid = cssnj_workflow_lcslh;
        			dataObj.workItemId = cssnj_workflow_workItemId;
        			
        			if (cssnj_workflow_nextNodes[o].tsgz == "") {
        				cssnj_workflow_alert("推送规则为空");
        				return;
        			}
        			
        			dataObj.tsgzuuid = cssnj_workflow_nextNodes[o].tsgz;
        			dataObj.csgzuuid = cssnj_workflow_nextNodes[o].csgz;
        			dataObj.getrylist = '';
        			
        			cssnj_workflow_jsonp(cssnj_workflow_server  +  "/wfctrl/workflow.action?tld=TsgzService_getTsry", dataObj, "cssnj_workflow_getNodeTsry_callBack");
 
        		}
        		
        		return;
        	}
    		
    	}
    	
    }
    
    if (doComplete == "1") {
    	return true;
    } else {
    	return false;
    }
	
}


// 环节后台获取推送人员
function cssnj_workflow_getNodeTsry_callBack(data) {
	
	if (data.success == "1") {
		
		// alert(data.tsryList);
		// alert(data.csryList);
		 // cssnj_workflow_nextNodes[index].tsryList =
			// "{dbry_dm:'wj',dbry_jgdm:'23200000000',dbry_gwdm:'gwdm'},{dbry_dm:'wj2',dbry_jgdm:'23200000000',dbry_gwdm:'gwdm'}";
		 if (data.tsryList == null || JSON.stringify(data.tsryList) == "[]") {
			// 类型5 推送时 设置推送人员出错
			cssnj_workflow_send_after_fail(cssnj_workflow_tsfs , cssnj_workflow_nextNodes[index].nodeMc + "：推送人员为空");
			return false;
		 }
		 cssnj_workflow_nextNodes[index].tsryList = JSON.stringify(data.tsryList);
		 cssnj_workflow_nextNodes[index].csryList = JSON.stringify(data.csryList);
    	 cssnj_workflow_tsjgfw=data.ts_jgfw;
    	 cssnj_workflow_nextNodes[index].czzt = "1";
    	 cssnj_workflow_complete();
		 
 	} else {
 		// 类型5 推送时 设置推送人员出错
		cssnj_workflow_send_after_fail( cssnj_workflow_tsfs,  cssnj_workflow_nextNodes[index].nodeMc + "：推送人员为空");
 	}
	
}


// 回退
function cssnj_workflow_back(target) {
	cssnj_workflow_target = "";
	cssnj_workflow_tsfs = "2";
	
	if (target == "") {
		cssnj_workflow_send_after_fail(cssnj_workflow_tsfs, "回退节点为空");
		return;
	}
	
	cssnj_workflow_target = target;
	
	if (cssnj_workflow_processInstanceId == undefined || cssnj_workflow_processInstanceId == null || cssnj_workflow_processInstanceId == "") {
		// 类型1 推送时 不存在流程实例号
		cssnj_workflow_send_after_fail( cssnj_workflow_tsfs, "流程实例号为空 不能回退");
		return;
	} 

	if (cssnj_workflow_taskId == undefined || cssnj_workflow_taskId == null || cssnj_workflow_taskId == "") {
		// 类型2 推送时 不存在任务号
		cssnj_workflow_send_after_fail( cssnj_workflow_tsfs, "任务号为空 不能回退");
		return;
	}
	
	// 获取推送 下一节点（并行则为多个节点）的信息 。
	
	//获取审批意见 ----
	//--是否审批 
	if (cssnj_workflow_sfspyj == "1") {
		cssnj_workflow_setClyj(document.getElementById("cssnjwfspyj").value);
	}
	
		
	/*
		// 复制 设置的扩展参数对象为 new 的对象
		var dataObj = variblesObj;
		dataObj.lcslh = cssnj_workflow_lcslh;
		dataObj.processInstanceId = cssnj_workflow_processInstanceId;
		dataObj.taskId = cssnj_workflow_taskId;
		dataObj.tsfs = cssnj_workflow_tsfs;
		dataObj.target = cssnj_workflow_target;
		
		cssnj_workflow_jsonp(cssnj_workflow_server  +  "/wfctrl/workflow.action?tld=WfWebUtil_getNextNodes", dataObj, "cssnj_workflow_getnextnodes_callBack");
	*/
	
		// 回退不需要校验 推送人员 -- 回退-后台计算原 节点的人员
		// 参数对象为 new object
    	var dataObj = variblesObj;
	    dataObj.lcslh = cssnj_workflow_lcslh;
	    dataObj.processInstanceId = cssnj_workflow_processInstanceId;
	    dataObj.taskId = cssnj_workflow_taskId;
	    dataObj.workItemId = cssnj_workflow_workItemId;
	    dataObj.target = cssnj_workflow_target;
	    dataObj.tsfs = cssnj_workflow_tsfs;
	    dataObj.clyj = cssnj_workflow_spyj;
		cssnj_workflow_jsonp(cssnj_workflow_server  +   "/wfctrl/workflow.action?tld=WfWebUtil_backTask", dataObj, "cssnj_workflow_complete_callBack");

	
}

// 跳转 
function cssnj_workflow_goTo(target) {
	cssnj_workflow_target = "";
	//alert(target);
	cssnj_workflow_tsfs = "3";
	
	if (target == "") {
		cssnj_workflow_send_after_fail( cssnj_workflow_tsfs, "跳转节点为空");
		return;
	}
	cssnj_workflow_target = target;
	// 获取推送 下一节点（并行则为多个节点）的信息 。
	if (cssnj_workflow_processInstanceId == undefined || cssnj_workflow_processInstanceId == null || cssnj_workflow_processInstanceId == "") {
		// 类型1 推送时 不存在流程实例号
		cssnj_workflow_send_after_fail( cssnj_workflow_tsfs, "流程实例号为空 不能推送");
		return;
	} 

	if (cssnj_workflow_taskId == undefined || cssnj_workflow_taskId == null || cssnj_workflow_taskId == "") {
		// 类型2 推送时 不存在任务号
		cssnj_workflow_send_after_fail(cssnj_workflow_tsfs ,"任务号为空 不能推送");
		return;
	}
	
	//获取审批意见 ----
	//--是否审批 
	if (cssnj_workflow_sfspyj == "1") {
		cssnj_workflow_setClyj($("#cssnjwfspyj").val());
	}
		
		
		// 复制 设置的扩展参数对象为 new 的对象
		var dataObj = variblesObj;
		dataObj.lcslh = cssnj_workflow_lcslh;
		dataObj.processInstanceId = cssnj_workflow_processInstanceId;
		dataObj.taskId = cssnj_workflow_taskId;
		dataObj.workItemId = cssnj_workflow_workItemId;
		dataObj.tsfs = cssnj_workflow_tsfs;
		dataObj.target = cssnj_workflow_target;
		
		cssnj_workflow_jsonp(cssnj_workflow_server  +  "/wfctrl/workflow.action?tld=WfWebUtil_getNextNodes", dataObj, "cssnj_workflow_getnextnodes_callBack");

}



// 保存推送人员
function cssnj_workflow_setTsry(tsryList, csryList) {
	if (tsryList == undefined || tsryList == null || tsryList == "[]") {
		// 类型5 推送时 设置推送人员出错
		cssnj_workflow_send_after_fail( cssnj_workflow_tsfs, cssnj_workflow_nextNodes[index].nodeMc + "：推送人员为空");
		return;
	}
	
	cssnj_workflow_nextNodes[index].tsryList = tsryList;
	cssnj_workflow_nextNodes[index].csryList = csryList;
	cssnj_workflow_nextNodes[index].czzt = "1";
	
	
	setTimeout("cssnj_workflow_complete();",1000);
	
}

// 具体推送方法
function cssnj_workflow_complete() {
	
	// 根据推送方式判断 -----------------1正常2回退3跳转
	if (cssnj_workflow_tsfs == "1" ) {
		
		if (cssnj_workflow_complete_check()) {
			// 参数对象为 new object
	    	var dataObj = variblesObj;
	    	var tsryList = "{";
	    	var csryList = "{";
			// 将推送人员的 key 和 值 放入 参数中
		    for (o in cssnj_workflow_nextNodes) {
		    	tsryList +=  cssnj_workflow_nextNodes[o].nodeId + ":" + cssnj_workflow_nextNodes[o].tsryList + ",";
		    	csryList +=  cssnj_workflow_nextNodes[o].nodeId + ":" + cssnj_workflow_nextNodes[o].csryList + ",";
		    }	
		    if (tsryList != "{") {
		    	tsryList = tsryList.substring(0, tsryList.length - 1) + "}";
		    } else {
		    	tsryList = "";
		    }
		    if (csryList != "{") {
		    	csryList = csryList.substring(0, csryList.length - 1) + "}";
		    } else {
		    	csryList = "";
		    }
		    dataObj.tsryList = tsryList;
		    dataObj.csryList = csryList;
		    dataObj.lcslh = cssnj_workflow_lcslh;
		    dataObj.processInstanceId = cssnj_workflow_processInstanceId;
		    dataObj.taskId = cssnj_workflow_taskId;
		    dataObj.workItemId = cssnj_workflow_workItemId;
		    dataObj.target = cssnj_workflow_target;
		    dataObj.tsfs = cssnj_workflow_tsfs;
		    dataObj.clyj = cssnj_workflow_spyj;
		    dataObj.tsjgfw = cssnj_workflow_tsjgfw;
			
			cssnj_workflow_jsonp(cssnj_workflow_server  +   "/wfctrl/workflow.action?tld=WfWebUtil_completeTask", dataObj, "cssnj_workflow_complete_callBack");
		
		}
		
	} else if (cssnj_workflow_tsfs == "3" ) {
		
		if (cssnj_workflow_complete_check()) {
			// 参数对象为 new object
	    	var dataObj = variblesObj;
	    	var tsryList = "";
	    	var csryList = "";
			// 将推送人员的 key 和 值 放入 参数中
		    for (o in cssnj_workflow_nextNodes) {
		    	tsryList = "{" + cssnj_workflow_nextNodes[o].nodeId + ":" + cssnj_workflow_nextNodes[o].tsryList + "";
		    	csryList = "{" + cssnj_workflow_nextNodes[o].nodeId + ":" + cssnj_workflow_nextNodes[o].csryList + "";
		    }	
		    if (tsryList != "") {
		    	tsryList += "}";
		    }
		    if (csryList != "") {
		    	csryList += "}";
		    }
		    dataObj.tsryList = tsryList;
		    dataObj.csryList = csryList;
		    dataObj.lcslh = cssnj_workflow_lcslh;
		    dataObj.processInstanceId = cssnj_workflow_processInstanceId;
		    dataObj.taskId = cssnj_workflow_taskId;
		    dataObj.workItemId = cssnj_workflow_workItemId;
		    dataObj.target = cssnj_workflow_target;
		    dataObj.tsfs = cssnj_workflow_tsfs;
		    dataObj.clyj = cssnj_workflow_spyj;
		    
			
			cssnj_workflow_jsonp(cssnj_workflow_server  +   "/wfctrl/workflow.action?tld=WfWebUtil_goToTask", dataObj, "cssnj_workflow_complete_callBack");

		}
		
	}


}


// start jsonp callBack
function cssnj_workflow_complete_callBack(data) {
	
	 if (data.success == "1") {
		  
		  cssnj_workflow_taskId = "";
		  cssnj_workflow_workItemId = "";
		 // 调用 工作流接口 ------- 完成接口
		  if (cssnj_workflow_tsfs == "1") {
			  setTimeout(function(){cssnj_workflow_send_after_success(data);},500);
			  cssnj_workflow_initProcess(cssnj_workflow_lcslh, cssnj_workflow_workItemId, "2");
		  } else if (cssnj_workflow_tsfs == "2") {
			  setTimeout(function(){cssnj_workflow_back_after_success(data);},500);
			 cssnj_workflow_initProcess(cssnj_workflow_lcslh, cssnj_workflow_workItemId, "2");
		  } else if (cssnj_workflow_tsfs == "3") {
			  setTimeout(function(){cssnj_workflow_goTo_after_success(data);},500);
			  cssnj_workflow_initProcess(cssnj_workflow_lcslh, cssnj_workflow_workItemId, "2");
		  }
		  
	  } else {
		  
		  // 类型6 具体推送方法时报错
		  cssnj_workflow_send_after_fail( cssnj_workflow_tsfs, data.message);
		  
	  }
	
}
//===================================================================
//-------------获取url后面参数---------------
String.prototype.Cssnj_workflow_GetValue = function(parm) {
	var reg = new RegExp("(^|&)" + parm + "=([^&]*)(&|$)");
	var r = this.substr(this.indexOf("\?") + 1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}
// ------------------------------------------------------------


/** 
 * param 将要转为URL参数字符串的对象 
 * key URL参数字符串的前缀 
 * encode true/false 是否进行URL编码,默认为true 
 *  
 * return URL参数字符串 
 */  
var cssnj_workflow_parseParam = function (param, key, encode) {  
  if(param==null) return '';  
  var paramStr = '';  
  var t = typeof (param);  
  if (t == 'string' || t == 'number' || t == 'boolean') {  
    paramStr += '&' + key + '=' + ((encode==null||encode) ? encodeURIComponent(param) : param);  
  } else {  
    for (var i in param) {  
      var k = key == null ? i : key + (param instanceof Array ? '[' + i + ']' : '.' + i);  
      paramStr += cssnj_workflow_parseParam(param[i], k, encode);  
    }  
  }  
  return paramStr;  
}; 

