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
        url:GET_CHANGE_STUDENT,
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
            alert("访问服务器失败");
            alert(error);
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
                alert("换题成功");
                findLists(pageNum,10);
                cloasAssigenPanal();
            }else if(result.result == -2){
                alert("可分配题目数量不足")
            }else if(result.result == -3){
                alert("权限错误");
            }else{
                alert("分配失败");
            }
        },
        error:function(error){
            alert("访问服务器失败");
            alert(error);
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
        alert("您没有选择任何数据");
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
                    alert("全部换题成功");
                    findLists(pageNum,10);
                }else{
                    alert("换题失败");
                }
            },
            error:function(error){
                alert("访问服务器失败");
                alert(error);
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
            alert("访问服务器失败");
            alert(error);
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
            "state":2
        },
        success:function(result){
            if(result==1){
                alert("已经通知该学生");
                findLists(pageNum,10);
            }else{
            }
        },
        error:function(error){
            alert("访问服务器失败");
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
        alert("您没有选择任何数据");
    } else {
        $.ajax({
            url: CHANGE_STATE_URL,
            type: 'post',
            cache: false,
            data: JSON.stringify(str),
            contentType: "application/json;charset=utf-8", // 因为上面是JSON数据
            success: function (result) {
                if(result.result==1){
                    alert("已通知所以拒绝的学生");
                    findLists(pageNum,10);
                }else{
                    alert("拒绝失败");
                }
            },
            error:function(error){
                alert("访问服务器失败");
                alert(error);
            }

        });

    }
}