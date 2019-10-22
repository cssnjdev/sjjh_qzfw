$(document).ready(function() {
	    var layer;
	    if(developerModel=="1"){
	    		loadJsonTrees("/file/json/module.json");
				setTimeout(function(){loadJsonTrees("/file/json/xtgl.json")},100);
	    }else{
	    	window.location.replace("about:blank");
	    }
});
function loadJsonTrees(url){
	var request = new XMLHttpRequest();            
	request.open("GET", url);/*设置请求方法与路径*/            
	request.send(null);/*不发送数据到服务器*/            
	request.onload = function () {/*XHR对象获取到返回信息后执行*/                
		if (request.status == 200) {/*返回状态为200，即为数据获取成功*/                    
			var treeData= JSON.parse(request.responseText);  
			loadnav(treeData); 
		}else{
			return false;
		}
	}
}
function network(){
  loadnav(xtglTreeData);
}
function loadnav (navdata) {
    for (var i=0;i<navdata.length;i++) {
        var  first_menu=navdata[i];
         var  childMenus=first_menu.list;
         //console.log("子菜单=="+JSON.stringify(childMenus));
         if(childMenus==null||childMenus==undefined||childMenus==""){
             //console.log("没有子菜单=="+first_menu.name);
             loadTopMenu(first_menu);
         }else{
              //console.log("有子菜单=="+first_menu.name);
              loadHasChildMenu(first_menu);
          }
    }
   //列表添加完后再次执行渲染
   xuanran();
}
//加载带有子菜单的
function loadHasChildMenu(data){
    var fragment = document.getElementById("left_nav");
    var list = document.createElement('li');
    list.className="layui-nav-item layui-nav-itemed";
    var child_one_Html='<a href="javascript:;">'+data.name+'</a>';
    var childmenus=data.list;
    var child_two_Html='<dl class="layui-nav-child">';
    var childHtmls="";
    for (var i=0;i<childmenus.length;i++) {
        var childmenu=childmenus[i];
        var childHtml='<dd><a class="site-demo-active"  data-title='+childmenu.name+' data-menuId='+childmenu.menuId+' data-src='+childmenu.url+'>'+childmenu.name+'</a></dd>';
        childHtmls+=childHtml; 
    }
    child_two_Html +=childHtmls+'</dl>';
    list.innerHTML=child_one_Html+child_two_Html;
    fragment.appendChild(list);
    console.log("2.=多级菜单列表加载完毕");
}
//加载顶级菜单(没有子菜单)
function loadTopMenu(data){
    var fragment = document.getElementById("left_nav");
    var list = document.createElement('li'); 
    list.className="layui-nav-item";
    list.innerHTML='<a class="site-demo-active" data-title='+data.name+' data-menuId='+data.menuId+' data-src='+data.url+'>'+data.name+'</a>';
    fragment.appendChild(list);
    console.log("1.=顶级菜单列表加载完毕"); 
}
function xuanran() {
    layui.use(['element','layer'], function(){
      var element = layui.element;
      layer=layui.layer;
      
      var layFilter = $("#left_nav").attr('lay-filter');
      element.render('nav', layFilter); 
      
      var active = {
        //在这里给active绑定几项事件，后面可通过active调用这些事件
        tabAdd: function (url, id, name) {
            //新增一个Tab项 传入三个参数，分别对应其标题，tab页面的地址，还有一个规定的id，是标签中data-id的属性值
        	var body_url="";
        	if(url.startsWith("/")){
        		 body_url=ctx+url;
            }else{
            	 body_url=url;
            }
            console.log("要切换的页面地址="+body_url);
            element.tabAdd('demo', {
                title: name,
                content: '<iframe data-frameid="' + id + '" scrolling="auto" frameborder="0" src="' + body_url + '"></iframe>',
                id: id //规定好的id
            })
            FrameWH();  //计算ifram层的大小
        },
        tabChange: function (id) {
            //切换到指定Tab项
            element.tabChange('demo', id); //根据传入的id传入到指定的tab项
        },
        tabDelete: function (id) {
            element.tabDelete("demo", id);//删除
        }
      };      
      // //当点击有site-demo-active属性的标签时，即左侧菜单栏中内容 ，触发点击事件
      $('.site-demo-active').on('click', function () {
           var dataid = $(this);
           if ($(".layui-tab-title li[lay-id]").length <= 0) {
               //如果比零小，则直接打开新的tab项
               console.log("没有tab页，新建tab页");
            active.tabAdd(dataid.attr("data-src"), dataid.attr("data-menuId"),dataid.attr("data-title"));
         } else {
              //否则判断该tab项是否以及存在
              var isData = false; //初始化一个标志，为false说明未打开该tab项 为true则说明已有
              $.each($(".layui-tab-title li[lay-id]"), function () {
                  //如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开
                  if ($(this).attr("lay-id") == dataid.attr("data-menuId")) {
                      console.log("如果点击左侧菜单栏所传入的id 在右侧tab项中的lay-id属性可以找到，则说明该tab项已经打开");
                    isData = true;                     
               }
              })
              if (isData == false) {
                //标志为false 新增一个tab项
                console.log("新增一个tab项")
                active.tabAdd(dataid.attr("data-src"), dataid.attr("data-menuId"),dataid.attr("data-title"));
              }
         }
        //最后不管是否新增tab，最后都转到要打开的选项页面上
        active.tabChange(dataid.attr("data-menuId"));
      });
    })
    console.log("==列表添加完后再次执行渲染");
}
/*
 * @todo 重新计算iframe高度
 */
function FrameWH() { 
    var h = $(window).height() - 164;
    //console.log("高度问题=="+h);
    $("iframe").css("height", h + "px");
}
