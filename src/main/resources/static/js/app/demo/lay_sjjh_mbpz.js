var editMb_frameSrc = "/tyGoPage/html?model=demo&p=lay_sjjh_mbpz_sheet";
var table,form;
$(document).ready(function() {
layui.use(['table','layer','form','laydate'],function(){
        table =layui.table;
    var layer=layui.layer;
        form = layui.form;
    var laydate = layui.laydate;
    table.render({
        elem:'#mbTable',
        url:ctx+"/tykf/request?tld=sjjhmbpzService_queryMbpzList",
        method:'post',
        cols:[[
            {field:'MBID',title:'模板ID',width:"15%",height:80},    
            {field:'MBMC',title:'模板名称',width:"15%",height:80}, 
            {field:'MBMS',title:'模板描述',width:"15%",height:80},  
            {field:'MBLX',title:'模板类型',width:"15%",height:80},  
            {field:'LRRQ',title:'录入日期',width:"15%",height:80},  
            {field:'YXBJ',title:'有效标记',width:"10%",height:80},
            {field:'CZ', title: '操作', width:"15%",toolbar:"#lineBtns"}
        ]],
        page:'true',
        parseData:function(res){
        	return {
        		"code":0,
        		"msg":"",
        		"count":10,
        		data:res
        	}
        },
        id: 'mbTableReload'
    });
    laydate.render({
        elem: '#lrsj',
        trigger: 'click' //采用click弹出
    });
    
    form.render();
    
    //上方菜单操作栏(查询、以及  增加  按钮  )
    var $ = layui.$, active = {
            //查询
            reload: function () {
            }, 
            add: function () { //添加
            }
      }
    /*表格 行内操作(编辑  以及  删除 按钮操作)  */
        table.on('tool(mbTable)', function(obj){
         var data = obj.data; //获得当前行数据
         var tr=obj.tr//活动当前行tr 的  DOM对象
         var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
         if(layEvent === 'del'){ //删除
             layer.confirm('确定删除吗？',{title:'删除'}, function(index){
                 //向服务端发送删除指令og
                 $.getJSON(ctx+'/tykf/request?tld=sjjhmbpzService_deleteMb',{mbid: data.MBID}, function(ret){
                	 	if(ret.code==1){
	                         layer.close(index);//关闭弹窗
	                         table.reload('mbTableReload', {//重载表格
                         });
                	 	}
                 });
                 layer.close(index);
             });
         } else if(layEvent === 'edit'){
             layer.open({
                 type: 2,
                 skin: 'layui-layer-molv',
                 area: ['1200px', '600px'],
                 title: ['编辑模板SHEET信息','font-size:18px'],
                 btn: ['确定', '取消'] ,
                 shadeClose: true,
                 shade: 0, 
                 maxmin: true,
                 id: 'lay_sjjh_mbpz_sheet',
                 content:editMb_frameSrc+"&mbid="+data.MBID,
                 success:function(layero,index){
                      
                 },
                 yes:function(index,layero){
                	 var body = layer.getChildFrame('body', index);
                	 var iframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
                	 var getNums=iframeWin.saveMbpz();
                 },
                 end: function(){
                	 
                 }
             });
         }
      });
	});
	$("#newMb").click(function(){
		layer.open({
            type: 2,
            skin: 'layui-layer-molv',
            area: ['1000px', '600px'],
            title: ['新增模板信息','font-size:18px'],
            btn: ['确定', '取消'] ,
            shadeClose: true,
            shade: 0, 
            maxmin: true,
            id: 'lay_sjjh_mbpz_sheet',
            content:editMb_frameSrc,
            success:function(layero,index){
                 
            },
            yes:function(index,layero){
           	 var body = layer.getChildFrame('body', index);
           	 var iframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
           	 var getNums=iframeWin.saveMbpz();
            },
            end: function(){
           	 
            }
        });
	});
	$("#queryMb").click(function(){
		var data=$.cssnj_formdata_json($("#cxtjForm"));
		table.reload("mbTableReload",{
			page:{curr:1},
			where:data
		});
	});
});