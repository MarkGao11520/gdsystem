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
})()


/**
 * 获取列表
 * @param page
 * @param rows
 */
function findLists(page,rows){   //获取广告位列表
    $.ajax({
        type:"get",
        url:TEACHERLIST_URL,
        async:true,
        data:{
            "page":page,
            "rows":rows,
            "tname":$('#tNameKey').val(),
            "tcode":$('#tCodeKey').val()
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
 * 添加
 */
function add() {  //添加广告位
    if(check()){
        $.ajax({
            type:"get",
            url:ADDTEACHER_URL,
            async:true,
            data:{
                "teachercode":$('#tcode').val(),
                "teachername":$('#tname').val(),
                "sex":$('#tsex').val(),
                "age":$('#tage').val(),
                "qq":$('#tqq').val(),
                "wechat":$('#twechat').val(),
                "phone":$('#tphone').val()
            },
            success:function(result){
                if(result==1){
                    bootbox.alert("添加成功");
                    cloasPanal();
                    if(total%10!=0||total==0){
                        findLists(resultPages,10);
                    }else {
                        findLists(resultPages+1,10);
                    }
                }else if(result == -1){
                    bootbox.alert("该用户已经存在");
                }
                else{
                    bootbox.alert("添加失败");
                }
            },
            error:function(error){
                bootbox.alert("访问服务器失败");
            }
        });
    }
}


/**
 * 编辑
 * @param id
 */
function update(id) {
    if(check()){
        $.ajax({
            type:"get",
            url:UPDATETEACHER_URL,
            async:true,
            data:{
                "teacherid":id,
                "teachercode":$('#tcode').val(),
                "teachername":$('#tname').val(),
                "sex":$('#tsex').val(),
                "age":$('#tage').val(),
                "qq":$('#tqq').val(),
                "wechat":$('#twechat').val(),
                "phone":$('#tphone').val()
            },
            success:function(result){
                if(result==1){
                    bootbox.alert("编辑成功");
                    cloasPanal();
                    findLists(pageNum,10);
                }else{
                    bootbox.alert("编辑失败");
                }
            },
            error:function(error){
                bootbox.alert("访问服务器失败");
            }
        });
    }
}

/**
 * 删除
 */
function deleteall() {
    var str = new Array() ;
    var j=0
    for (var i = 0; i < document.getElementsByName('teacherid').length; i++) {
        if (document.getElementsByName('teacherid')[i].checked) {
            str[j++]=document.getElementsByName('teacherid')[i].value;
        }
    }
    if (str.length==0) {
        bootbox.alert("您没有选择任何数据");
    } else {
        $.ajax({
            url: DELETETEACHER_URL,
            type: 'post',
            cache: false,
            data: JSON.stringify(str),
            contentType: "application/json;charset=utf-8", // 因为上面是JSON数据
            success: function (result) {
                if(result==1){
                    bootbox.alert("删除成功");
                    findLists(pageNum,10);
                }else{
                    bootbox.alert("删除失败");
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
 * 重置教师密码
 */
function resetTeacherPassword() {
    var str = new Array() ;
    var j=0
    for (var i = 0; i < document.getElementsByName('teacherid').length; i++) {
        if (document.getElementsByName('teacherid')[i].checked) {
            str[j++]=document.getElementsByName('teacherid')[i].value;
        }
    }
    if (str.length==0) {
        bootbox.alert("您没有选择任何数据");
    } else {
        $.ajax({
            url: RESETTEACHERPASSWORD_URL,
            type: 'post',
            cache: false,
            data: JSON.stringify(str),
            contentType: "application/json;charset=utf-8", // 因为上面是JSON数据
            success: function (result) {
                if(result==1){
                    bootbox.alert("重置成功，且默认教师密码为88888888");
                    findLists(pageNum,10);
                }else if(result==-1){
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
        str += "<tr id=\"teacher_"+obj.teacherid+"\">" +
            "<td>"+(i+1)+"</td>" +
            "<td><input name=\'teacherid\' value=\'"+obj.teacherid+"\' type=\"checkbox\" /></td>" +
            "<td>" + obj.teachername + "</td>"+
            "<td>" + (obj.teachercode==null?'-':obj.teachercode==""?'-':obj.teachercode) + "</td>"+
            "<td>" + (obj.age==null?'-':obj.age==""?'-':obj.age) + "</td>"+
            "<td>" + (obj.sex==1?'男':'女') + "</td>"+
            "<td>" + (obj.phone==null?'-':obj.phone==""?'-':obj.phone) + "</td>"+
            "<td class=\"action-td\"><a href=\"javascript:updateTeacher("+obj.teacherid+")\" class=\"btn btn-small btn-warning\">编辑</a></td></tr>";
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
    $('#teacherWidget').show();
}

/**
 * 关闭窗口
 */
function cloasPanal() {
    $('#teacherWidget').hide();
    clear();
}

function addTeacher() {
    $('#teacherWidgetHeader').html("添加教师");
    $('#submitbtn').attr('onclick','add()');
    openFormPanal();
}

function updateTeacher(id) {
    $('#teacherWidgetHeader').html("编辑教师");
    $('#uname').attr('disabled',true);
    for(var i=0;i<list.length;i++) {
        if (id == list[i].teacherid) {
            var obj = list[i];
            $('#tname').val(obj.teachername);
            $('#tcode').val(obj.teachercode);
            $('#tage').val(obj.age);
            $('#tphone').val(obj.phone);
            $('#tqq').val(obj.qq);
            $('#twechat').val(obj.wechat);
        }
    }
    $('#submitbtn').attr('onclick','update('+id+')');
    openFormPanal();
}


/**
 * 清空表格
 */
function clear() {
    $('#tname').val("");
    $('#uname').val("");
    $('#tcode').val("");
    $('#tage').val("");
    $('#tsex').val("");
    $('#tphone').val("");
    $('#tqq').val("");
    $('#twechat').val("");
}

/**
 * 验证
 * @returns {boolean}
 */
function check() {   //验证
    if($('#tname').val()==""||$('#tname').val()==null){
        bootbox.alert("姓名不能为空");
        return false;
    }
    if($('#tcode').val()==""||$('#tcode').val()==null){
        bootbox.alert("工号不能为空");
        return false;
    }
    if($('#tphone').val()==""||$('#tphone').val()==null){
        bootbox.alert("手机号不能为空");
        return false;
    }
    return true;
}
