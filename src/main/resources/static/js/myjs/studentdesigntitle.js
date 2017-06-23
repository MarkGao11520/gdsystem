/**
 * Created by gaowenfeng on 2017/6/3.
 */
var controller = "imageController";  //Controller名
var currentPages = 0;  //当前分页组
var resultPages;  //总页数
var resultRows;   //行数
var pageNum;      //当前页
var total;        //总数
var list;         //数据
var sid;


(function() {
    findLists(1,10);
})()

/**
 * 获取列表
 * @param page
 * @param rows
 */
function findLists(page,rows){
    $.ajax({
        type:"get",
        url:GET_DESIGNSTUDENTLIST,
        async:true,
        data:{
            "page":page,
            "rows":rows,
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

function assigenOneSpecified(id) {
    $.ajax({
        type:'get',
        url:ASSIGEN_SUBJECT_ONE_SPECIFIED_URL,
        data:{
            classid:$('#classList').val(),
            studentid:id,
            titleid:$('#titleList').val()
        },
        success:function (result) {
            if(result.result==1){
                bootbox.alert("换题成功");
                findLists(pageNum,10);
                cloasAssigenPanal();
            }else if(result.result == -2){
                bootbox.alert("可分配题目数量不足")
            }else if(result.result == -3){
                bootbox.alert("权限错误");
            }else{
                bootbox.alert("分配失败");
            }
        },
        error:function(error){
            bootbox.alert("访问服务器失败");
            bootbox.alert(error);
        }
    })
}

function changeTitleBatch() {
    var sids = '' ;
    var cids = ''
    for (var i = 0; i < document.getElementsByName('studentid').length; i++) {
        if (document.getElementsByName('studentid')[i].checked) {
            if(sids==''){
                sids+=document.getElementsByName('studentid')[i].value;
                for(var j=0;j<list.length;j++) {
                    if (document.getElementsByName('studentid')[i].value == list[j].studentid) {
                        var obj = list[j];
                        cids+=obj.classid;
                    }
                }
            }else{
                sids+=","+document.getElementsByName('studentid')[i].value;
                for(var j=0;j<list.length;j++) {
                    if (document.getElementsByName('studentid')[i].value == list[j].studentid) {
                        var obj = list[j];
                        cids+=","+obj.classid;
                    }
                }
            }
        }
    }
    if (sids=='') {
        bootbox.alert("您没有选择任何数据");
    } else {
        $.ajax({
            url: CHANGE_TITLE_URL,
            type: 'post',
            cache: false,
            data: {
                sids:sids,
                cids:cids
            },
            success: function (result) {
                if(result.result==1){
                    bootbox.alert("全部换题成功");
                    findLists(pageNum,10);
                }else{
                    bootbox.alert("换题失败");
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
 * 打开分配窗口
 * @param id
 */
function openAssigenPanal(id) {
    $.ajax({
        type:'get',
        url:AGREESTUDENTDESIGNTITLE,
        data:{
            studentid:id
        },
        success:function (result) {
            bootbox.alert("已将该学生设计的题目录入数据库");
            findLists(1,10);
        },
        error:function(error){
            bootbox.alert("访问服务器失败");
            bootbox.alert(error);
        }
    })
}

/**
 * 打开分配窗口
 */
function cloasAssigenPanal() {
    $('#assigenWidget').hide();
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
            "<td>" + (obj.teacherId2==null?'无':obj.teacherId2==null) + "</td>"+
            "<td>" + (obj.teacherId1==null?'无':obj.teacher1.teachername) + "</td>"+
            "<td>" + (obj.title==null?'未选题':obj.title.titlename) + "</td>"+
            "<td>" + obj.designtitlename.titlename + "</td>"+
            "<td>" + obj.designtitlename.titlecontent + "</td>"+
            "<td class=\"action-td\">" +
            "<a href=\"javascript:updateState("+obj.studentid+")\" class=\"btn btn-small btn-warning\">拒绝换题</a>" +
            "<a href=\"javascript:openAssigenPanal("+obj.studentid+")\" class=\"btn btn-small btn-warning\">同意换题</a>" +
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
 * 拒绝换题
 * @param id
 */
function updateState(id) {
    $.ajax({
        type:"get",
        url:UPDATE_STUDENT_URL,
        async:true,
        data:{
            "studentid":id,
            "state":5
        },
        success:function(result){
            if(result==1){
                bootbox.alert("已经通知该学生");
                findLists(pageNum,10);
            }else{
            }
        },
        error:function(error){
            bootbox.alert("访问服务器失败");
        }
    });
}

/**
 * 删除
 */
function changeall() {
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
        var strs = "";
        for (var j=0;j<str.length;j++){
            if (j==0)
                strs+=str
            else
                strs+=","+str;
        }
        $.ajax({
            url: CHANGE_STATE_URL,
            type: 'post',
            cache: false,
            data: {
                studentIds:strs,
                state:5
            },
            success: function (result) {
                if(result.result==1){
                    bootbox.alert("已通知所以拒绝的学生");
                    findLists(pageNum,10);
                }else{
                    bootbox.alert("拒绝失败");
                }
            },
            error:function(error){
                bootbox.alert("访问服务器失败");
                bootbox.alert(error);
            }

        });

    }
}
