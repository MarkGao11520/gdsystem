/**
 * Created by gaowenfeng on 2017/6/1.
 */
var roleid;
(function getUser() {
    $.ajax({
        type:'get',
        url:GETUSERINFO_URL,
        success:function (result) {
            $('#username').val(result.username);
            $('#realname').val(result.realname);
            $('#age').val(result.result.age);
            $('#qq').val(result.result.qq);
            $('#wechat').val(result.result.wechat);
            $('#phone').val(result.result.phone);
            $('#sex').val(result.result.sex);
            $('#departments').val(result.result.departments);
            $('#major').val(result.result.major);
            $('#code').val(result.code);
            roleid = result.role;
        },
        error:function (error) {
            bootbox.alert("访问服务器失败")
        }
    })
})();


function updateUserinfo() {
    $.ajax({
        type:'post',
        url:UPDATEUSERINFO_URL,
        data:{
            teachername:$('#realname').val(),
            studentname:$('#realname').val(),
            realname:$('#realname').val(),
            studentcode:$('#code').val(),
            teachercode:$('#code').val(),
            age:$('#age').val(),
            sex:$('#sex').val(),
            phone:$('#phone').val(),
            qq:$('#qq').val(),
            wechat:$('#wechat').val(),
            departments:$('#departments').val(),
            major:$('#major').val()
        },
        success:function (result) {
            if(result.result==1){
                bootbox.alert("编辑成功");
            }else {
                bootbox.alert("编辑失败");
            }
        },
        error:function (error) {
            bootbox.alert("访问服务器失败")
        }
    })
}

function updateUpwd() {
    if(check()) {
        $.ajax({
            type: 'post',
            url: UPDATEPASSWORD_URL,
            data: {
                username: $('#username').val(),
                password: $('#oldP').val(),
                newPassword: $('#newP').val()
            },
            success: function (result) {
                if (result.result == 1) {
                    bootbox.alert("修改密码成功,请重新登录");
                    window.location.href = "./logout";
                } else {
                    bootbox.alert("修改密码失败,请检查原始密码是否核对正确");
                }
            },
            error: function (error) {
                bootbox.alert("访问服务器失败")
            }
        })
    }
}

function check() {
    if($('#oldP').val()==null||$('#oldP').val()==""){
        bootbox.alert("旧密码不能为空");
        return false;
    }
    if($('#newP').val()==null||$('#newP').val()==""){
        bootbox.alert("新密码不能为空");
        return false;
    }
    if($('#newP').val()!=$('#newP1').val()){
        bootbox.alert("两次密码不能重复");
        return false;
    }
    return true;
}
