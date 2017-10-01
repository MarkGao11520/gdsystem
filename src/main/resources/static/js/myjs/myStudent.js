/**
 * Created by gaowenfeng on 2017/6/1.
 */
var controller = "imageController";  //Controller名
var currentPages = 0;  //当前分页组
var resultPages;  //总页数
var resultRows;   //行数
var pageNum;      //当前页
var total;        //总数
var list;         //数据
var sid;


(function () {
    findLists(1,10);
    preImport();
})()
/**
 * 获取班级列表
 */
function getClassList() {
    $.ajax({
        type:'get',
        url:GETCLASSSELECT_URL,
        async:false,
        success:function (result) {
            var str = "";
            for(temp in result){
                str += '<option value='+result[temp].classid+'>'+result[temp].classname+'</option>';
            }
            $('#classList').html(str);
        }
    })
}

/**
 * 获取列表
 * @param page
 * @param rows
 */
function findLists(page,rows){
    $.ajax({
        type:"get",
        url:OBTIAN_STUDENTLISTBYTEACHER_URL,
        async:true,
        data:{
            "page":page,
            "rows":rows
        },
//		dataType:"JSONP",
        success:function(result){
            table(result.list);
            resultPages = result.pages;
            resultRows = result.pageSize;
            pageNum = result.pageNum;
            total = result.total;
            list = result.list;
            pages(resultPages,resultRows);
        },
        error:function(error){
            bootbox.alert("访问服务器失败");
            bootbox.alert(error);
        }
    });
}






/**
 * 重置学生密码
 */
function resetStudentPassword() {
    var str = new Array() ;
    var j=0
    for (var i = 0; i < document.getElementsByName('studentid').length; i++) {
        if (document.getElementsByName('studentid')[i].checked) {
            str[j++]=document.getElementsByName('studentid')[i].value;
        }
    }
    if (str.length==0) {
        bootbox.alert("您没有选择任何数据");
    } else {
        $.ajax({
            url: RESETSTUDENTPASSWORD_URL,
            type: 'post',
            cache: false,
            data: JSON.stringify(str),
            contentType: "application/json;charset=utf-8", // 因为上面是JSON数据
            success: function (result) {
                if(result==1){
                    bootbox.alert("重置成功，且默认学生密码为66666666");
                    findLists(pageNum,10);
                }else if (result==-1){
                    bootbox.alert("您没有权限");
                }else {
                    bootbox.alert("重置失败");
                }
            },
            error:function(error){
                bootbox.alert("访问服务器失败");
                bootbox.alert(error);
            }

        });

    }
}





/**
 * 将数据添加到表格
 * @param list
 */
function table(list) {   //
    var str = "";
    for(var i=0;i<list.length;i++){
        var obj = list[i];
        str += "<tr id=\"student_"+obj.studentid+"\">" +
            "<td>"+(i+1)+"</td>" +
            "<td><input name=\'studentid\' value=\'"+obj.studentid+"\' type=\"checkbox\" /></td>" +
            "<td>" + obj.studentname + "</td>"+
            "<td>" + obj.studentcode + "</td>"+
            "<td>" + (obj.teacherId2==null?'无':obj.teacherId2==''?'无':obj.teacherId2) + "</td>"+
            "<td>" + (obj.teacherId1==null?'无':obj.teacherId1==''?'无':obj.teacher1.teachername) + "</td>"+
            "<td>" + (obj.title==null?'未选题':obj.title.titlename) + "</td>"+
            "<td class=\"action-td\"><a href=\"javascript:updateStudent("+obj.studentid+")\" class=\"btn btn-small btn-warning\">详&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;情</a>" +
            "</td></tr>";
    }
    $("#tbody").html(str);
}


/**
 * 分页
 * @param pages
 * @param rows
 */
function pages(pages,rows) {
    var str = "";
    if(currentPages>0){
        str += "<li><a href=\"javascript:changePages(-1)\">上页</a></li>";
    }else {
        str += "<li><a href=\"#\">上页</a></li>";
    }
    for(var i=1;i<6;i++){
        var page = i+(5*currentPages);
        if(page<=pages){
            if(page==pageNum){
                str += "<li><a href=\"javascript:findLists("+page+","+rows+")\">"+page+"</a></li>";
            }else {
                str += "<li><a href=\"javascript:findLists(" + page + "," + rows + ")\">" + page + "</a></li>";
            }
        }
    }
    if(currentPages<parseInt(resultPages/5)){
        str += "<li><a href=\"javascript:changePages(1)\">下页</a></li>";
    }else {
        str += "<li><a href=\"#\">下页</a></li>";
    }
    $('#page_ul').html(str);
}

/**
 * 翻页
 * @param temp
 */
function changePages(temp){
    if(temp==1){
        currentPages++;
        pages(resultPages,resultRows);
    }else if(temp==-1){
        currentPages--;
        pages(resultPages,resultRows);
    }
}

/**
 * 打开窗口
 * @param id
 */
function openFormPanal() {
    $('#studentWidget').show();
}

/**
 * 关闭窗口
 */
function cloasPanal() {
    $('#studentWidget').hide();
    clear();
}

/**
 * 打开导入窗口
 * @param id
 */
function openExportFormPanal() {
    $('#importWidget').show();
}

/**
 * 关闭导入窗口
 */
function cloasExportPanal() {
    $('#importWidget').hide();
}

/**
 * 打开分配窗口
 * @param id
 */
function openTeacherPanal(id) {
    $.ajax({
        type:'get',
        url:TEACHER_KVLIST_URL,
        success:function (result) {
            var str = ""
            for(var i=0;i<result.length;i++){
                str += '<option value='+result[i].teacherid+'>'+result[i].teachername+'</option>';
            }
            $('#teacher1').html(str);
                for(var i=0;i<list.length;i++) {
                    if (id == list[i].studentid) {
                        if(list[i].teacherId1!=null&&list[i].teacherId1!="") {
                            $('#teacher1').val(list[i].teacherId1);
                        }
                        if(list[i].teacherId2!=null&&list[i].teacherId2!="") {
                            $('#teacher2').val(list[i].teacherId2);
                        }
                        // if(list[i].teacherId2Phone!=null&&list[i].teacherId2Phone!="") {
                        //     $('#teacherId2Phone').val(list[i].teacherId2Phone);
                        // }
                    }
                }
        },
        error:function(error){
            bootbox.alert("访问服务器失败");
            bootbox.alert(error);
        }
    })
    $('#teacherSubmitBtn').attr('onclick','teacher('+id+')')
    $('#teacherWidget').show();
}

/**
 * 关闭分配窗口
 */
function closeTeacherPanal() {
    $('#teacherWidget').hide();
    $('#teacher2').val("");
}

/**
 * 打开分配窗口
 * @param id
 */
function openAssigenPanal(id) {
    $.ajax({
        type:'get',
        url:GET_SELECTED_TITLE_URL,
        data:{
            classid:$('#classList').val()
        },
        success:function (result) {
            var str = ""
            for(var i=0;i<result.length;i++){
                str += '<option value='+result[i].titleid+'>'+result[i].titlename+'</option>';
            }
            $('#titleList').html(str);
        },
        error:function(error){
            bootbox.alert("访问服务器失败");
            bootbox.alert(error);
        }
    })
    $('#assSubmitBtn').attr('onclick','assigenOneSpecified('+id+')')
    $('#assigenWidget').show();
}

/**
 * 打开分配窗口
 */
function cloasAssigenPanal() {
    $('#assigenWidget').hide();
}

function addStudent() {
    $('#studentWidgetHeader').html("添加学生");
    $('#submitbtn').attr('onclick','add()');
    openFormPanal();
}

function updateStudent(id) {
    $('#studentWidgetHeader').html("详情");
    for(var i=0;i<list.length;i++) {
        if (id == list[i].studentid) {
            var obj = list[i];
            $('#sname').val(obj.studentname);
            $('#scode').val(obj.studentcode);
            $('#sage').val(obj.age);
            $('#ssex').val(obj.sex);
            $('#sphone').val(obj.phone);
            $('#sdepartments').val(obj.departments);
            $('#sgrage').val(obj.grade);
            $('#smajor').val(obj.major);
            $('#className').val(obj.classes.className);

        }
    }
    $('#submitbtn').attr('onclick','update('+id+')');
    openFormPanal();
}


/**
 * 清空表格
 */
function clear() {
    $('#sname').val("");
    $('#uname').val("");
    $('#scode').val("");
    $('#stitle').val("");
    $('#sqq').val("");
    $('#swechat').val("");
    $('#sgrage').val("");
    $('#sdepartments').val("");
    $('#sphone').val("");
}

/**
 * 验证
 * @returns {boolean}
 */
function check() {   //验证
    if($('#sname').val()==""||$('#sname').val()==null){
        bootbox.alert("姓名不能为空");
        return false;
    }
    if($('#scode').val()==""||$('#scode').val()==null){
        bootbox.alert("学号不能为空");
        return false;
    }
    if($('#sphone').val()==""||$('#sphone').val()==null){
        bootbox.alert("手机号不能为空");
        return false;
    }
    return true;
}

function preImport() {
    var control = $('#excel');
    control.fileinput({
        language:'zh',  //设置语言
        uploadUrl:STUDENT_EXCEL_IMPORT_URL,
//                uploadAsync:false,
        dropZoneEnabled:true,
        showCaption:false,   //是否显示标题
        showPreview:true,
        showUpload : true,//是否显示上传按钮
        allowedFileExtensions:['xls'],
        maxFileCount:10,
        enctype:'multipart/form-data',
        browseClass:"btn btn-warning"
    })
        .on("fileuploaded",function (event,data,c,d) {
            if((data.response.result==1)){
                    bootbox.alert("处理成功");
                getClassList();
                findLists(1,10);
                cloasExportPanal();
            }else if(data.response.result==-1){
                bootbox.alert("学号为"+data.response.batchResult+"的记录重复，请检查后重新添加");
                getClassList();
                findLists(1,10);
                cloasExportPanal();
            }else if(data.response.result == -5){
                bootbox.alert("excel格式有误，请下载模板比对后重新导入");
                cloasExportPanal();
            }else{
                bootbox.alert("处理失败");
                getClassList();
                findLists(1,10);
                cloasExportPanal();
            }
        })
        // .on("filebatchselected",function (event,files) {
        //     $(this).fileinput("upload");
        // })
        .on("fileerror",function (a,b,c) {
            bootbox.alert("访问服务器失败");
        });
}

function download() {
    window.location.href="./temp/student.xls";
}




