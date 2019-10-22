$(document).ready(function () {
    //zylVerCode();
    setTimeout("zylVerCode()",1000);
});
function zylVerCode(){
    var data = {
        method: 'reloadCode'
    };
    window.parent.postMessage(JSON.stringify(data),portal_login_url);
}

layui.use(['carousel', 'form'], function(){
    var carousel = layui.carousel
        ,form = layui.form;

    //自定义验证规则
    form.verify({
        userName: function(value){
            if(value.length < 5){
                return '账号至少得5个字符';
            }
        }
        ,pass: [/^[\S]{1,12}$/,'密码必须6到12位，且不能出现空格']
        /*,vercodes: function(value){
            //获取验证码
            var zylVerCode = $(".zylVerCode").html();
            if(value!=zylVerCode){
                return '验证码错误（区分大小写）';
            }
        }*/
        ,content: function(value){
            layedit.sync(editIndex);
        }
    });

    //监听提交
    form.on('submit(demo1)', function(data){
        /*layer.alert(JSON.stringify(data.field),{
            title: '最终的提交信息'
        });*/
        var data = {
            method: 'dologin',
            username:data.field.userName,
            password:data.field.nuse,
            vocde:data.field.vercode,
            rememberMe:false
        };
        window.parent.postMessage(JSON.stringify(data),portal_login_url);
        return false;
    });

    window.addEventListener("message", receiveMessage, false);

    function receiveMessage(e){
        if (portal_login_url.indexOf(e.origin) == -1){
            return;
        }
        var data = JSON.parse(e.data);
        if (data) {
            if(data.method){
                if(data.method=='getVcode'){
                    $(".zylVerCode").html(data.vcode);
                }else if (data.method=='login'){

                }
            }
        }
    }

    //设置轮播主体高度
    var zyl_login_height = $(window).height()/1.3;
    var zyl_car_height = $(".zyl_login_height").css("cssText","height:" + zyl_login_height + "px!important");

    //Login轮播主体
    carousel.render({
        elem: '#zyllogin'//指向容器选择器
        ,width: '100%' //设置容器宽度
        ,height:'zyl_car_height'
        ,arrow: 'always' //始终显示箭头
        ,anim: 'fade' //切换动画方式
        ,autoplay: false //是否自动切换false true
        ,arrow: 'hover' //切换箭头默认显示状态||不显示：none||悬停显示：hover||始终显示：always
        ,indicator: 'none' //指示器位置||外部：outside||内部：inside||不显示：none
        ,interval: '3000' //自动切换时间:单位：ms（毫秒）
    });

    //监听轮播--案例暂未使用
    carousel.on('change(zyllogin)', function(obj){
        var loginCarousel = obj.index;
    });

    //粒子线条
    $(".zyl_login_cont").jParticle({
        background: "rgba(0,0,0,0)",//背景颜色
        color: "#fff",//粒子和连线的颜色
        particlesNumber:100,//粒子数量
        //disableLinks:true,//禁止粒子间连线
        //disableMouse:true,//禁止粒子间连线(鼠标)
        particle: {
            minSize: 1,//最小粒子
            maxSize: 3,//最大粒子
            speed: 30,//粒子的动画速度
        }
    });
});
