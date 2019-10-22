
var modalDiv = 
	"<div class=\"modal bs-example-modal-lg\" onclick=\"modalHide('#tsModal', '', hideCallBack);\" id=\"tsModal\">\n" +
	"    <div style='width:80%;height:80%' class=\"modal-dialog modal-lg\">\n" + 
	"        <div class=\"modal-content\" >\n" + 
	"            <div class=\"modal-header\">\n" + 
	"                <button type=\"button\" onclick=\"modalHide('#tsModal', '', hideCallBack);\" class=\"close\" data-dismiss=\"modal\"><span aria-hidden=\"true\">×</span><span  class=\"sr-only\">Close</span></button>\n" + 
	"                <h4 id='title' class=\"modal-title\">选择推送人员</h4>\n" + 
	"            </div>\n" + 
	"            <div class=\"modal-body\">\n" + 
	"            <iframe id='iframe' style='width:100%;height:500px;' frameborder=0 src=''></iframe>" +
	"            </div>\n" + 
	"        </div>\n" + 
	"    </div>\n" + 
	"</div>";

function cssnj_workflow_init_ext() {
	$("body").append(modalDiv);
}


$.extend({ 
includePath: '', 
include: function(file) 
{ 
var files = typeof file == "string" ? [file] : file; 
for (var i = 0; i < files.length; i++) 
{ 
var name = files[i].replace(/^\s|\s$/g, ""); 
var att = name.split('.'); 
var ext = att[att.length - 1].toLowerCase(); 
var isCSS = ext == "css"; 
var tag = isCSS ? "link" : "script"; 
var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' "; 
var link = (isCSS ? "href" : "src") + "='" + $.includePath + name + "'"; 
if ($(tag + "[" + link + "]").length == 0) $("head").prepend("<" + tag + attr + link + "></" + tag + ">"); 
} 
} 
}); 

$.include(cssnjworkflowprojectname + '/workflow_client/ext/css/animate.min.css'); 
$.include(cssnjworkflowprojectname + '/workflow_client/ext/css/bootstrap.min.css');
$.include(cssnjworkflowprojectname + '/workflow_client/ext/js/bootstrap.min.js');
//$.include('http://www.jq22.com/jquery/jquery-1.10.2.js');



 //animate.css动画触动一次方法
        $.fn.extend({
            animateCss: function (animationName) {
                var animationEnd = 'webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend';
                this.addClass('animated ' + animationName).one(animationEnd, function() {
                    $(this).removeClass('animated ' + animationName);
                });
            }
        });
        /**
         * 显示模态框方法
         * @param targetModel 模态框选择器，jquery选择器
         * @param animateName 弹出动作
         * @ callback 回调方法
         */
        var modalShow = function(targetModel, animateName, callback){
            var animationIn = ["bounceIn","bounceInDown","bounceInLeft","bounceInRight","bounceInUp",
                  "fadeIn", "fadeInDown", "fadeInLeft", "fadeInRight", "fadeOutUp",
                "fadeInDownBig", "fadeInLeftBig", "fadeOutRightBig", "fadeOutUpBig","flipInX","flipInY",
            "lightSpeedIn","rotateIn","rotateInDownLeft","rotateInDownRight","rotateInUpLeft","rotateInUpRight",
            "zoomIn","zoomInDown","zoomInLeft","zoomInRight","zoomInUp","slideInDown","slideInLeft",
            "slideInRight", "slideInUp","rollIn"];
            if(!animateName || animationIn.indexOf(animateName)==-1){
                console.log(animationIn.length);
                var intRandom =  Math.floor(Math.random()*animationIn.length);
                animateName = animationIn[intRandom];
            }
           // console.log(targetModel + " " + animateName);
            $(targetModel).show().animateCss(animateName);
            callback.call(this);
        }
        /**
         * 隐藏模态框方法
         * @param targetModel 模态框选择器，jquery选择器
         * @param animateName 隐藏动作
         * @ callback 回调方法
         */
        var modalHide = function(targetModel, animateName, callback){
            var animationOut = ["bounceOut","bounceOutDown","bounceOutLeft","bounceOutRight","bounceOutUp",
                "fadeOut", "fadeOutDown", "fadeOutLeft", "fadeOutRight", "fadeOutUp",
                 "fadeOutDownBig", "fadeOutLeftBig", "fadeOutRightBig", "fadeOutUpBig","flipOutX","flipOutY",
            "lightSpeedOut","rotateOut","rotateOutDownLeft","rotateOutDownRight","rotateOutUpLeft","rotateOutUpRight",
                "zoomOut","zoomOutDown","zoomOutLeft","zoomOutRight","zoomOutUp",
                "zoomOut","zoomOutDown","zoomOutLeft","zoomOutRight","zoomOutUp","slideOutDown","slideOutLeft",
                "slideOutRight", "slideOutUp","rollOut"];
            if(!animateName || animationOut.indexOf(animateName)==-1){
                console.log(animationOut.length);
                var intRandom =  Math.floor(Math.random()*animationOut.length);
                animateName = animationOut[intRandom];
            }
            $(targetModel).children().click(function(e){e.stopPropagation()});
            $(targetModel).animateCss(animateName);
            $(targetModel).delay(500).hide(1,function(){
                $(this).removeClass('animated ' + animateName);
            });
            callback.call(this);
        }

        /*
        var modalDataInit = function(info){
            alert(info);
            //填充数据，对弹出模态框数据样式初始化或修改
        }*/
        
        function showCallBack() {
        	
        }
        
        function hideCallBack() {
        	
        }

        
//==================== 
//重写 cssnj_workflow_open方法  
function cssnj_workflow_open(url, nodeName) {
	
	$('#iframe').attr("src", url);
	$('#title').html(nodeName);
	modalShow('#tsModal', '', showCallBack);//modalDataInit('test')
	
}



//重写创建toolbar方法 
//--添加工作流按钮 
function cssnj_workflow_init_toolbar3() {


	
$("<button  id='workflow_toolbar_bc' type=\"button\" class=\"btn\" onclick=\"cssnj_workflow_save()\">\n" +
"  <span class=\"cx_lu\"></span>保存\n" + 
"</button>").appendTo($("#workflow_toolbar"));

$("<button  id='workflow_toolbar_ts' type=\"button\" class=\"btn\" onclick=\"cssnj_workflow_send()\">\n" +
		"  <span class=\"cx_lu\"></span>推送\n" + 
		"</button>").appendTo($("#workflow_toolbar"));


$("<button  id='workflow_toolbar_wt' type=\"button\" class=\"btn\" onclick=\"cssnj_workflow_wt()\">\n" +
		"  <span class=\"cx_lu\"></span>委托\n" + 
		"</button>").appendTo($("#workflow_toolbar"));

$("<button  id='workflow_toolbar_ht' type=\"button\" class=\"btn\" onclick=\"cssnj_workflow_ht()\">\n" +
		"  <span class=\"cx_lu\"></span>回退\n" + 
		"</button>").appendTo($("#workflow_toolbar"));


$("<button  id='workflow_toolbar_tz' type=\"button\" class=\"btn\" onclick=\"cssnj_workflow_tz()\">\n" +
		"  <span class=\"cx_lu\"></span>跳转\n" + 
		"</button>").appendTo($("#workflow_toolbar"));


$("<button  id='workflow_toolbar_htwt' type=\"button\" class=\"btn\" onclick=\"cssnj_workflow_htwt()\">\n" +
		"  <span class=\"cx_lu\"></span>退回委托\n" + 
		"</button>").appendTo($("#workflow_toolbar"));


$("<button  id='workflow_toolbar_gqlc' type=\"button\" class=\"btn\" onclick=\"cssnj_workflow_gqlc()\">\n" +
		"  <span class=\"cx_lu\"></span>挂起流程\n" + 
		"</button>").appendTo($("#workflow_toolbar"));



$("<button  id='workflow_toolbar_jhlc' type=\"button\" class=\"btn\" onclick=\"cssnj_workflow_jhlc()\">\n" +
		"  <span class=\"cx_lu\"></span>激活流程\n" + 
		"</button>").appendTo($("#workflow_toolbar"));


$("<button  id='workflow_toolbar_zflc' type=\"button\" class=\"btn\" onclick=\"cssnj_workflow_zflc()\">\n" +
		"  <span class=\"cx_lu\"></span>作废流程\n" + 
		"</button>").appendTo($("#workflow_toolbar"));


$("<button  id='workflow_toolbar_cklc' type=\"button\" class=\"btn\" onclick=\"cssnj_workflow_cklc()\">\n" +
		"  <span class=\"cx_lu\"></span>查看流程\n" + 
		"</button>").appendTo($("#workflow_toolbar"));

	
}




//重写创建toolbar方法 
//--添加工作流按钮 
function cssnj_workflow_init_toolbar() {

	
$("<a id='workflow_toolbar_bc' style='display:none' href=\"javascript:void(0);\" class=\"easyui-linkbutton l-btn l-btn-small l-btn-plain\"\n" +
"data-options=\"plain:true,iconCls:'icon-save'\" onclick=\"cssnj_workflow_save()\" group=\"\" id=\"\">\n" + 
"<span class=\"l-btn-left l-btn-icon-left\"><span class=\"l-btn-text\">保存</span>\n" + 
"<span class=\"l-btn-icon icon-save\">&nbsp;</span></span></a>\n" + 
"</span>\n" + 
"</a>").appendTo($("#workflow_toolbar"));

$("<a id='workflow_toolbar_ts' style='display:none' href=\"javascript:void(0);\" class=\"easyui-linkbutton l-btn l-btn-small l-btn-plain\"\n" +
"data-options=\"plain:true,iconCls:'icon-ok'\" onclick=\"cssnj_workflow_send()\" group=\"\" id=\"\">\n" + 
"<span class=\"l-btn-left l-btn-icon-left\"><span class=\"l-btn-text\">推送</span>\n" + 
"<span class=\"l-btn-icon icon-ok\">&nbsp;</span></span></a>\n" + 
"</span>\n" + 
"</a>").appendTo($("#workflow_toolbar"));


$("<a id='workflow_toolbar_wt' style='display:none' href=\"javascript:void(0);\" class=\"easyui-linkbutton l-btn l-btn-small l-btn-plain\"\n" +
"data-options=\"plain:true,iconCls:'icon-user-config'\" onclick=\"cssnj_workflow_wt()\" group=\"\" id=\"\">\n" + 
"<span class=\"l-btn-left l-btn-icon-left\"><span class=\"l-btn-text\">委托</span>\n" + 
"<span class=\"l-btn-icon icon-user-config\">&nbsp;</span></span></a>\n" + 
"</span>\n" + 
"</a>").appendTo($("#workflow_toolbar"));


$("<a id='workflow_toolbar_ht' style='display:none' href=\"javascript:void(0);\" class=\"easyui-linkbutton l-btn l-btn-small l-btn-plain\"\n" +
"data-options=\"plain:true,iconCls:'icon-back'\" onclick=\"cssnj_workflow_ht()\" group=\"\" id=\"\">\n" + 
"<span class=\"l-btn-left l-btn-icon-left\"><span class=\"l-btn-text\">回退</span>\n" + 
"<span class=\"l-btn-icon icon-back\">&nbsp;</span></span></a>\n" + 
"</span>\n" + 
"</a>").appendTo($("#workflow_toolbar"));


$("<a id='workflow_toolbar_tz' style='display:none' href=\"javascript:void(0);\" class=\"easyui-linkbutton l-btn l-btn-small l-btn-plain\"\n" +
"data-options=\"plain:true,iconCls:'icon-redo'\" onclick=\"cssnj_workflow_tz()\" group=\"\" id=\"\">\n" + 
"<span class=\"l-btn-left l-btn-icon-left\"><span class=\"l-btn-text\">跳转</span>\n" + 
"<span class=\"l-btn-icon icon-redo\">&nbsp;</span></span></a>\n" + 
"</span>\n" + 
"</a>").appendTo($("#workflow_toolbar"));


$("<a id='workflow_toolbar_htwt' style='display:none' href=\"javascript:void(0);\" class=\"easyui-linkbutton l-btn l-btn-small l-btn-plain\"\n" +
"data-options=\"plain:true,iconCls:'icon-edit'\" onclick=\"cssnj_workflow_htwt()\" group=\"\" id=\"\">\n" + 
"<span class=\"l-btn-left l-btn-icon-left\"><span class=\"l-btn-text\">退回委托</span>\n" + 
"<span class=\"l-btn-icon icon-edit\">&nbsp;</span></span></a>\n" + 
"</span>\n" + 
"</a>").appendTo($("#workflow_toolbar"));

$("<a id='workflow_toolbar_gqlc' style='display:none'  href=\"javascript:void(0);\" class=\"easyui-linkbutton l-btn l-btn-small l-btn-plain\"\n" +
"data-options=\"plain:true,iconCls:'icon-lock-password'\" onclick=\"cssnj_workflow_gqlc()\" group=\"\" id=\"\">\n" + 
"<span class=\"l-btn-left l-btn-icon-left\"><span class=\"l-btn-text\">挂起流程</span>\n" + 
"<span class=\"l-btn-icon icon-lock-password\">&nbsp;</span></span></a>\n" + 
"</span>\n" + 
"</a>").appendTo($("#workflow_toolbar"));

$("<a id='workflow_toolbar_jhlc' style='display:none' href=\"javascript:void(0);\" class=\"easyui-linkbutton l-btn l-btn-small l-btn-plain\"\n" +
"data-options=\"plain:true,iconCls:'icon-lock-go'\" onclick=\"cssnj_workflow_jhlc()\" group=\"\" id=\"\">\n" + 
"<span class=\"l-btn-left l-btn-icon-left\"><span class=\"l-btn-text\">激活流程</span>\n" + 
"<span class=\"l-btn-icon icon-lock-go\">&nbsp;</span></span></a>\n" + 
"</span>\n" + 
"</a>").appendTo($("#workflow_toolbar"));


$("<a id='workflow_toolbar_zflc' style='display:none' href=\"javascript:void(0);\" class=\"easyui-linkbutton l-btn l-btn-small l-btn-plain\"\n" +
"data-options=\"plain:true,iconCls:'icon-cancel'\" onclick=\"cssnj_workflow_zflc()\" group=\"\" id=\"\">\n" + 
"<span class=\"l-btn-left l-btn-icon-left\"><span class=\"l-btn-text\">作废流程</span>\n" + 
"<span class=\"l-btn-icon icon-cancel\">&nbsp;</span></span></a>\n" + 
"</span>\n" + 
"</a>").appendTo($("#workflow_toolbar"));


$("<a id='workflow_toolbar_cklc' style='display:none'  href=\"javascript:void(0);\" class=\"easyui-linkbutton l-btn l-btn-small l-btn-plain\"\n" +
"data-options=\"plain:true,iconCls:'icon-search'\" onclick=\"cssnj_workflow_cklc()\" group=\"\" id=\"\">\n" + 
"<span class=\"l-btn-left l-btn-icon-left\"><span class=\"l-btn-text\">查看流程</span>\n" + 
"<span class=\"l-btn-icon icon-search\">&nbsp;</span></span></a>\n" + 
"</span>\n" + 
"</a>").appendTo($("#workflow_toolbar"));

	
}


//--创建审批意见
//创建审批意见 
function addSpyj() {
	var spyjDiv = document.createElement("div");
	document.body.appendChild(spyjDiv);
	$('<div  id="spyjDiv"  style="width:100%;height:60px;"><table class="default-table" width="100%"><tr><th><label>处理意见:</label></th><td width="87%"><textarea id="cssnjwfspyj" style="width:100%;height:65px;"></textarea></td></tr></table></div>').appendTo($('body'));
}

