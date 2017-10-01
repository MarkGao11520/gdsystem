/**
 * Created by gaowenfeng on 2017/6/3.
 */
var id ;
var state;
var sdtitleid;
var content;
(function getUser() {
    $.ajax({
        type:'get',
        url:GETUSERINFO_URL,
        success:function (result) {
            id = result.result.studentid;
            state = result.result.state;
            sdtitleid = result.result.teacherId1;
            if(result.result.state!=0){
                $('#myTitleName').html(result.result.title.titlename);
                $('#myTitleClassName').html(result.result.title.titleClass.titleclassname);
                $('#myTitleContent').html(result.result.title.titlecontent);
                $('#teacher1').html(result.result.teacherId2);
                $('#teacher2').html(result.result.teacher1.teachername);
                $('#t1_phone').html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机号："+(result.result.teacherId2Phone==null?'-':result.result.teacherId2Phone==''?'-':result.result.teacherId2Phone));
                $('#t2_qq').html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;qq："+(result.result.teacher1.qq==null?'-':result.result.teacher1.qq==''?'-':result.result.teacher1.qq));
                $('#t2_wechat').html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;微信："+(result.result.teacher1.wechat==null?'-':result.result.teacher1.wechat==''?'-':result.result.teacher1.wechat));
                $('#t2_phone').html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机号："+(result.result.teacher1.phone==null?'-':result.result.teacher1.phone==''?'-':result.result.teacher1.phone));
            }else if(result.result.state==0){
                bootbox.alert("题目未分配");
            }
            if(state==2){
                $('#change').attr("disabled","disabled");
                if(confirm("换题申请被拒绝，如有疑问请联系指导老师")){
                    updateState(3);
                }else{
                    updateState(3);
                }
            }
            if (state==5){
                if (confirm("您设计的题目审核被拒绝，如有问题请联系指导老师")){
                    updateState(3);
                }else {
                    updateState(3);
                }
            }
        },
        error:function (error) {
            bootbox.alert("访问服务器失败")
        }
    })
})();
function getTitleClassList() {
    $.ajax({
        type:'get',
        url:GETTITLECLASSKV_URL,
        async:'false',
        success:function (result) {
            var str = "";
            for (temp in result){
                str += '<option value='+result[temp].titleclassid+'>'+result[temp].titleclassname+'</option>';
            }
            $('#tcList').html(str);
        },
        error:function (error) {
            bootbox.alert("请求服务器失败");
        }
    })
}
/**
 * 编辑
 * @param id
 */
function updateState(s) {
    content = $('#tcontent').val();

        $.ajax({
            type:"get",
            url:UPDATE_STUDENT_URL,
            async:true,
            data:{
                "studentid":id,
                "state":s,
                "content":content
            },
            success:function(result){
                if(result==1){
                    if(s==1){
                        bootbox.alert("申请已经提交，请耐心等待");
                        cloasPanal();
                    }
                }else{
                    bootbox.alert("申请提交失败");
                }
            },
            error:function(error){
                bootbox.alert("访问服务器失败");
            }

        });
}

function updateP() {
    $('#widgetHeader').html("换题原因");
    $('#submitbtn').attr('onclick','updateState(1)');
    openFormPanal();
}
function openFormPanal() {
    $('#widgetP').show();
}
function cloasPanal() {
    $('#widgetP').hide();
    clear();
}

function addD() {
    $('#widgetDesign').html("添加题目类别");
    $('#submittitlebtn').attr('onclick','addtitle()');
    getTitleClassList();
    openFormPanal1();
}
function openFormPanal1() {
    $('#widgetD').show();
}
function cloasPanal1() {
    $('#widgetD').hide();
    clear1();
}


function addtitle() {
    if(check()){
        $.ajax({
            type:"get",
            url:STUDENTADDTITLE_URL,
            async:true,
            data:{
                "titlename":$('#titlename').val(),
                "titlecontent":$('#titlecontent').val(),
                "titleclassid":$('#tcList').val(),
                "studentdesignid":sdtitleid,
            },
            success:function(result){
                if(result==1){
                    bootbox.alert("设计成功，等待老师审批！");
                    cloasPanal1();
                }
                else{
                    bootbox.alert("设计失败！");
                }
            },
            error:function(error){
                bootbox.alert("访问服务器失败");
            }
        });
    }
}
function check() {   //验证
    if($('#titlename').val()==""||$('#titlename').val()==null){
        bootbox.alert("题目名称不能为空");
        return false;
    }
    if($('#titlecontent').val()==""||$('#titlecontent').val()==null){
        bootbox.alert("题目内容不能为空");
        return false;
    }
    return true;
}


function clear() {
    $('#tcontent').val("");
}
function clear1() {
    $('titlename').val("");
    $('#titlecontent').val("");
}
function go() {
    window.location.href="openReport";
}
