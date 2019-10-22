var table,form  ;
$(document).ready(function() {
  layui.use(['table','layer','form','laydate'],function(){
	    table =layui.table;
    var layer=layui.layer;
    	form= layui.form;
    var laydate = layui.laydate;
    table.render({
        elem:'#mbSheetColTable',
        cellMinWidth: 80,
        data:[],
        cols:[[
            {field:'MBID',title:'模板ID'},    
            {field:'SHEET_INDEX',title:'sheet位置',height:80}, 
            {field:'DB_COLMC',title:'字段名称',edit: 'text',height:80},  
            {field:'TABLE_NAME',title:'源数据表',edit: 'text',height:80},    
            {field:'EXCEL_COLMC',title:'excel_col',edit: 'text',height:80},  
            {field:'ZD_MC',title:'字段中文',edit: 'text',height:80},  
            {field:'DB_COLMC_TYPE',title:'字段类型',edit: 'text',height:80},
            {field:'DB_COLMC_LEN',title:'字段长度',edit: 'text',height:80},
            {field:'ZF_BJ',title:'作废标记',edit: 'text',height:80},
            {field:'CZ', title: '操作',toolbar:"#lineBtns"}
        ]],
        done:function(res,curr,count){  
        	 
        },
        page:'true',
        parseData:function(res){
        	
        },
        id: 'mbSheetColTable'
    });
    
    laydate.render({
        elem: '#date',
        trigger: 'click' //采用click弹出
    });
    
    form.render();
    
  //监听单元格编辑
    table.on('edit(mbSheetColTable)', function(obj){
      
    });
    /*表格 行内操作(编辑  以及  删除 按钮操作)  */
        table.on('tool(mbSheetColTable)', function(obj){
         var data = obj.data; //获得当前行数据
         var tr=obj.tr//活动当前行tr 的  DOM对象
         var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
         if(layEvent === 'del'){ //删除
             //layer.confirm('确定删除吗？',{title:'删除'}, function(index){
            			 obj.del();
            			 //layer.close(index);
            //});
         } else if(layEvent === 'edit'){
             
         }
      });
	});
	
	$("#addRowSheet").click(function(){
		var dataBak = [];   //定义一个空数组,用来存储之前编辑过的数据已经存放新数据
		var tableBak = table.cache.mbSheetColTable; 
		for (var i = 0; i < tableBak.length; i++) {
		    dataBak.push(tableBak[i]);      //将之前的数组备份
		}
		dataBak.push({   
		    "MBID": mbid
		    ,"SHEET_INDEX":sheetIndex
		    ,"DB_COLMC": ""
		    ,"TABLE_NAME": ""
		    ,"EXCEL_COLMC": ""
		    ,"ZD_MC": ""
		    ,"DB_COLMC_TYPE": ""
		    ,"DB_COLMC_LEN": ""
		    ,"ZF_BJ": ""
		});

		table.reload("mbSheetColTable",{
		    data:dataBak   // 将新数据重新载入表格
		});
	 });
	
	 /*****动态加载数据*******/
    $.getJSON(ctx+'/tykf/request?tld=sjjhmbpzService_queryMbSheetColmxList',{"mbid":mbid,"sheetIndex":sheetIndex},
    		  function(resdata){
    				if(resdata.list.length>0){
    					table.reload("mbSheetColTable",{
    						data:resdata.list  
    					});
    				}else{
    					var dataBak = []; 
    					dataBak.push({   
    					    "MBID": mbid
    					    ,"SHEET_INDEX":sheetIndex
    					    ,"DB_COLMC": ""
    					    ,"TABLE_NAME": ""
    					    ,"EXCEL_COLMC": ""
    					    ,"ZD_MC": ""
    					    ,"DB_COLMC_TYPE": ""
    					    ,"DB_COLMC_LEN": ""
    					    ,"ZF_BJ": ""
    					});
    					table.reload("mbSheetColTable",{
    					    data:dataBak   // 将新数据重新载入表格
    					});
    				}
    	});
});
/****保存明细表js****/
function saveMbColpz(){
	var tabledata=JSON.stringify(table.cache.mbSheetColTable); 
	var data=new Object();
	data.tabledata=tabledata;
	data.sheetIndex=sheetIndex;
	data.mbid=mbid;
	$.getJSON(ctx+'/tykf/request?tld=sjjhmbpzService_saveMbSheetColmx',data,
	  function(data){
		    if(data.code==1){
		        layer.alert('保存成功',{icon:1,title:'提示'},function(i){
		            layer.close(i);
		        });
		    }else{
		    	layer.alert(data.errmsg,{icon:2,title:'失败'},function(i){
		    		layer.close(i);
		    	});
		    	
		    }
		});
}