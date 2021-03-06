/**
 * Created by gaowenfeng on 2017/6/5.
 */
(function getUser() {
    preImageUpload();
    $.ajax({
        type:'get',
        url:GETUSERINFO_URL,
        success:function (result) {
            $('#userName1').html(result.realname);
            $('#userName2').html(result.realname);
            if(result.pic!=null&&result.pic!="")
            $('#userPic').attr('src',result.pic);
            $('#num1').html(result.num);
            $('#num2').html(result.num);
        },
        error:function (error) {
            alert("访问服务器失败")
        }
    })
})();


/**
 * 打开导入窗口
 * @param id
 */
function openImagePanal() {
    $('#imageWidget').show();
}

/**
 * 关闭导入窗口
 */
function closeImagePanal() {
    $('#imageWidget').hide();
}

function preImageUpload() {
    var control = $('#photo');
    control.fileinput({
        language:'zh',  //设置语言
        uploadUrl:"userController/updatePic",
//                uploadAsync:false,
        dropZoneEnabled:true,
        showCaption:false,   //是否显示标题
        showPreview:true,
        showUpload : true,//是否显示上传按钮
        allowedFileExtensions:['jpg','png','jpeg'],
        maxFileCount:10,
        enctype:'multipart/form-data',
        browseClass:"btn btn-warning"
    })
        .on("fileuploaded",function (event,data,c,d) {
            if((data.response.result==1)){
                alert("上传成功");
                $('#userPic').attr("src",data.response.url);
                closeImagePanal();
            }else{
                alert("上传失败");
                closeImagePanal();
            }
        })
        // .on("filebatchselected",function (event,files) {
        //     $(this).fileinput("upload");
        // })
        .on("fileerror",function (a,b,c) {
            alert("访问服务器失败");
        });
}