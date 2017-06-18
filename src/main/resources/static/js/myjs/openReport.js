/**
 * Created by gaowenfeng on 2017/6/4.
 */
var id ;
var editor;
var wordUrl;

(function () {
        editor = CKEDITOR.replace('editor1',{
            language : 'zh-cn',
            height: 400
        });
        getUser();
})()



function getUser() {
    $.ajax({
        type: 'get',
        url: GETUSERINFO_URL,
        success: function (result) {
            id = result.result.studentid;
            wordUrl = result.result.wordUrl;
            editor.setData(result.result.openreport);
            $('#sname').html(result.realname);
            $('#depart').html(result.result.departments);
            $('#major').html(result.result.major);
            $('#classes').html(result.result.classes.classname);
            $('#title').html(result.result.title.titlename);
            $('#teacher').html(result.result.teacher1.teachername);
        },
        error: function (error) {
            alert("访问服务器失败")
        }
    })
}



function upload() {
    $.ajax({
        type:'post',
        url:UPDATEUSERINFO_URL,
        data:{
            openreport:editor.getData()
        },
        success:function (result) {
            if(result.result==1){
                wordUrl = result.wordUrl;
                alert("编辑成功");
            }else {
                alert("编辑失败");
            }
        },
        error:function (error) {
            alert("访问服务器失败")
        }
    })
}

function exportWord() {

    window.location.href="."+wordUrl;
}