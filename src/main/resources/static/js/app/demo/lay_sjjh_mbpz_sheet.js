var editMb_frameSrc = "/tyGoPage/html?model=demo&p=lay_sjjh_mbpz_colmx";
var table,form  ;
$(document).ready(function() {
  layui.use(['table','layer','form','laydate'],function(){
	    table =layui.table;
    var layer=layui.layer;
    	form= layui.form;
    var laydate = layui.laydate;
    table.render({
        elem:'#mbSheetTable',
        data:[],
        cols:[[
            {field:'MBID',title:'模板ID',width:"10%"},    
            {field:'SHEET_INDEX',title:'sheet位置',width:"10%",height:80}, 
            {field:'TABLENAME',title:'表名',edit: 'text',width:"15%",height:80},    
            {field:'QSHS',title:'起始行数',edit: 'text',width:"10%",height:80},  
            {field:'QSLS',title:'起始列数',edit: 'text',width:"10%",height:80},  
            {field:'TABLE_MS',title:'表注释',edit: 'text',width:"20%",height:80},  
            {field:'TABLE_SCHEMA',title:'所在用户',edit: 'text',width:"10%",height:80},
            {field:'CZ', title: '操作', width:"15%",toolbar:"#lineBtns"}
        ]],
        done:function(res,curr,count){  
        	 
        },
        page:'true',
        parseData:function(res){
        	
        },
        id: 'mbSheetTable'
    });
    
    laydate.render({
        elem: '#lrrq',
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
  //监听单元格编辑
    table.on('edit(mbSheetTable)', function(obj){
      
    });
    /*表格 行内操作(编辑  以及  删除 按钮操作)  */
        table.on('tool(mbSheetTable)', function(obj){
         var data = obj.data; //获得当前行数据
         var tr=obj.tr//活动当前行tr 的  DOM对象
         var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
         if(layEvent === 'del'){ //删除
             layer.confirm('确定删除吗？',{title:'删除'}, function(index){
            	 $.getJSON(ctx+'/tykf/request?tld=sjjhmbpzService_deleteMbSheet',{mbid: data.MBID,sheetIndex:data.SHEET_INDEX}, function(ret){
            		 layer.alert('删除成功',{icon:1,title:'提示'},function(i){
            			 obj.del();
            			 layer.close(i);
            		 });
            	 });
             });
         } else if(layEvent === 'edit'){
             layer.open({
                 type: 2,
                 skin: 'layui-layer-molv',
                 area: ['1200px', '500px'],
                 title: ['编辑模板COLNAME信息','font-size:18px'],
                 btn: ['确定', '取消'] ,
                 shadeClose: true,
                 shade: 0, 
                 id: 'lay_sjjh_mbpz_sheet_col',
                 maxmin: true,
                 content:editMb_frameSrc+"&mbid="+data.MBID+"&sheetIndex="+data.SHEET_INDEX,
                 success:function(layero,index){
                      
                 },
                 yes:function(index,layero){
                	 var body = layer.getChildFrame('body', index);
                	 var iframeWin = window[layero.find('iframe')[0]['name']];//得到iframe页的窗口对象，执行iframe页的方法：
                	 var getNums=iframeWin.saveMbColpz();
                 },
                 end: function(){
                	 
                 }
             });
         }
      });
	});
	
	$("#addRowSheet").click(function(){
		var dataBak = [];   //定义一个空数组,用来存储之前编辑过的数据已经存放新数据
		var tableBak = table.cache.mbSheetTable; 
		for (var i = 0; i < tableBak.length; i++) {
		    dataBak.push(tableBak[i]);      //将之前的数组备份
		}
		dataBak.push({   
		    "MBID": mbid
		    ,"SHEET_INDEX": tableBak.length+1
		    ,"TABLENAME": ""
		    ,"QSHS": ""
		    ,"QSLS": ""
		    ,"QSLS": ""
		    ,"TABLE_MS": ""
		    ,"TABLE_SCHEMA": ""
		});

		table.reload("mbSheetTable",{
		    data:dataBak   // 将新数据重新载入表格
		});
	 });
	
	 /*****动态加载数据*******/
    $.getJSON(ctx+'/tykf/request?tld=sjjhmbpzService_queryMbSheetList',{"mbid":mbid},
    		  function(resdata){
			    	table.reload("mbSheetTable",{
					    data:resdata.list  
					});
			    	if(resdata.jhfwMbFile!=null){
			    		$("#mbmc").val(resdata.jhfwMbFile.mbMc); 
			    		$("#mbms").val(resdata.jhfwMbFile.mbMs); 
			    		$("#mblx").val(resdata.jhfwMbFile.mbLx); 
			    		$("#mblx").find("option[value=2]")[0].selected=true; 
			    		$("#lrrq").val(resdata.jhfwMbFile.str_lrrq); 
			    	}
    	});
});
/****保存主表js****/
function saveMbpz(){
	var tabledata=JSON.stringify(table.cache.mbSheetTable); 
	var mbFormData=$("#mbFormData");
	var data=new Object();
	data.tabledata=tabledata;
	data.mbFormData=JSON.stringify($.cssnj_formdata_json(mbFormData));
	$.getJSON(ctx+'/tykf/request?tld=sjjhmbpzService_saveMb',data,
	  function(data){
		    if(data.code==1){
		        layer.alert('保存成功',{icon:1,title:'提示'},function(i){
		            layer.close(i);
		            window.parent.table.reload('mbTableReload', {});
		        });
		    }else{
		    	layer.alert(data.errmsg,{icon:2,title:'失败'},function(i){
		    		layer.close(i);
		    	});
		    }
		});
}