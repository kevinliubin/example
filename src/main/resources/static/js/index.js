$(function(){
    var dataUrl = 'http://106.2.237.150:8089';
    document.body.addEventListener('focusout',()=>{
        //软件盘收起事件
        setTimeout(()=>{
            const scrollHeight = document.documentElement.scrollTop || document.body.scrollTop || 0;
            window.scrollTo(0,Math.max(scrollHeight - 1, 0));
        },100);
    });
    var timer=null;
    $(".rb_submit_btn").click(function(){
        if( verifOrder() ){
            var phone = $("#phone").val()
            var code = $("#code").val()
            var orderId = $("#orderId").val()
            $.ajax({
                type: 'POST',
                url:  '/order',
                data: {
                    phone:phone,
                    code:code,
                    name:orderId,
                },
                dataType: 'json',
                success: function(data){
                    if(data.code === 200){
                        $(".rb_box_input").css('display',"none");
                        $(".rb_submit_success").css('display',"block");
                    }else if(data.code === 400){
                        $("#msgMask").css({
                            "display":'block',
                            "color" : "#FFFFFF"
                        }).html(data.msg).delay(2000).hide(0);
                    }
                },error:function(err){
                    console.log(err)
                }
            })
        }
    })
    $(".code_img").click(function(){
        if( checkPhone() ){
            resetCode();
            var phone = $("#phone").val();
            $.ajax({
                type: 'POST',
                url:  '/sendNote',
                data: {
                    phone:phone,
                },
                dataType: 'json',
                success: function(data){
                    if(data.code === 200){
                        $("#msgMask").css({
                            "display":'block',
                            "color" : "#FFFFFF"
                        }).html('验证码发送成功！').delay(2000).hide(0);
                    }else if(data.code === 400){
                        $("#msgMask").css({
                            "display":'block',
                            "color" : "#FFFFFF"
                        }).html(data.msg).delay(2000).hide(0);
                    }
                },error:function(err){
                    console.log(err)
                }
            })

        }else{
            $("#phone").focus();
        }
    });
    //倒计时
    function resetCode(){
        $('.code_img').hide();
        $('.code_time').show();
        clearInterval(timer);
        var time = 180;
        var that= this;//习惯
        timer = setInterval(function(){
            if(time <= 0){
                clearInterval(timer);
                $(".code_img").html('');
                $('.code_time').html('');
                $(".code_img").html('重新发送');
                $('.code_img').show();
                $('.code_time').hide();
            }else {
                $('.code_img').hide();
                $('.code_time').show();
                $(".code_time").html('');
                $(".code_img").html('');
                $(".code_time").html("剩余"+(time)+"秒");
                time--;
            }
        },1000);
    }
    //验证手机号码
    function checkPhone(){
        var flag = true;
        $("#phone_error").html('');
        var myreg = /^((\d{3}-\d{8}|\d{4}-\d{7,8})|(1[3|5|7|8][0-9]{9}))$/;
        if($("#phone").val() == ''){
            $("#msgMask").css({
                "display":'block',
                "color" : "#FFFFFF"
            }).html('请输入手机号！').delay(2000).hide(0);
            flag = false
            return flag
        }
        if(!myreg.test($("#phone").val())){
            $("#msgMask").css({
                "display":'block',
                "color" : "#FFFFFF"
            }).html('请输入正确的手机号！').delay(2000).hide(0);
            flag = false
            return flag
        }
        flag = true;
        $("#phone_error").html('');
        $("#phone_error").html('');
        return flag;
    }
    function verifOrder() {
        var flag = true;
        $("#phone_error").html('');
        $("#code_error").html('');
        var myreg = /^((\d{3}-\d{8}|\d{4}-\d{7,8})|(1[3|5|7|8][0-9]{9}))$/;
        if($("#phone").val() == ''){
            $("#msgMask").css({
                "display":'block',
                "color" : "#FFFFFF"
            }).html('请输入手机号！').delay(2000).hide(0);
            flag = false
            return flag
        }
        if(!myreg.test($("#phone").val())){
            $("#msgMask").css({
                "display":'block',
                "color" : "#FFFFFF"
            }).html('请输入正确的手机号！').delay(2000).hide(0);
            flag = false
            return flag
        }
        if($("#code").val() == ''){
            $("#msgMask").css({
                "display":'block',
                "color" : "#FFFFFF"
            }).html('请输入验证码！').delay(2000).hide(0);
            flag = false
            return flag
        }
        flag = true;
        $("#phone_error").html('');
        $("#phone_error").html('');
        return flag;
    }
})