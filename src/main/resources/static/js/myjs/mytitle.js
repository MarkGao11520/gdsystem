/**
 * Created by gaowenfeng on 2017/6/3.
 */
var id ;
var state;
(function getUser() {
    $.ajax({
        type:'get',
        url:GETUSERINFO_URL,
        success:function (result) {
            id = result.result.studentid;
            state = result.result.state;
            if(result.result.state!=0){
                $('#myTitleName').html(result.result.title.titlename);
                $('#myTitleClassName').html(result.result.title.titleClass.titleclassname);
                $('#myTitleContent').html(result.result.title.titlecontent);
                $('#teacher1').html(result.result.teacherId2);
                $('#teacher2').html(result.result.teacher1.teachername);
                $('#t2_qq').html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;qq："+result.result.teacher1.qq);
                $('#t2_wechat').html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;微信："+result.result.teacher1.wechat);
                $('#t2_phone').html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机号："+result.result.teacher1.phone);
            }else if(result.result.state==0){
                alert("题目未分配");
            }
            if(state==2){
                $('#change').attr("disabled","disabled");
                if(confirm("换题申请被拒绝，如有疑问请联系指导老师")){
                }else{
                }
            }

        },
        error:function (error) {
            alert("访问服务器失败")
        }
    })
})();

/**
 * 编辑
 * @param id
 */
function updateState(s) {
        $.ajax({
            type:"get",
            url:UPDATE_STUDENT_URL,
            async:true,
            data:{
                "studentid":id,
                "state":s
            },
            success:function(result){
                if(result==1){
                    if(s==1){
                        alert("申请已经提交，请耐心等待");
                    }
                }else{
                    alert("申请提交失败");
                }
            },
            error:function(error){
                alert("访问服务器失败");
            }
        });
}



function go() {
    window.location.href="openReport";
}