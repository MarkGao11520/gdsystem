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
        url:OBTAIN_ADMINLIST_URL,
        async:true,
        data:{
            "page":page,
            "rows":rows,
            "aname":$('#aNameKey').val(),
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

/**
 * 添加
 */
function add() {  //添加广告位
    if(check()){
        $.ajax({
            type:"get",
            url:ADD_ADMIN_URL,
            async:true,
            data:{
                "realname":$('#aname').val(),
                "sex":$('#asex').val(),
                "age":$('#aage').val(),
                "qq":$('#aqq').val(),
                "wechat":$('#awechat').val(),
                "phone":$('#aphone').val()
            },
            success:function(result){
                if(result==1){
                    alert("添加成功");
                    cloasPanal();
                    if(total%10!=0||total==0){
                        findLists(resultPages,10);
                    }else {
                        findLists(resultPages+1,10);
                    }
                }else if(result == -1){
                    alert("该用户已经存在");
                }else if (result == -2){
                    alert("您权限不够");
                }
                else{
                    alert("添加失败");
                }
            },
            error:function(error){
                alert("访问服务器失败");
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
            url:UPDATE_ADMIN_URL,
            async:true,
            data:{
                "adminid":id,
                "realname":$('#aname').val(),
                "sex":$('#asex').val(),
                "age":$('#aage').val(),
                "qq":$('#aqq').val(),
                "wechat":$('#awechat').val(),
                "phone":$('#aphone').val()
            },
            success:function(result){
                if(result==1){
                    alert("编辑成功");
                    cloasPanal();
                    findLists(pageNum,10);
                }else if (result == -1){
                    alert("您没有权限");
                }
                else {
                    alert("编辑失败");
                }
            },
            error:function(error){
                alert("访问服务器失败");
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
    for (var i = 0; i < document.getElementsByName('adminid').length; i++) {
        if (document.getElementsByName('adminid')[i].checked) {
            str[j++]=document.getElementsByName('adminid')[i].value;
        }
    }
    if (str.length==0) {
        alert("您没有选择任何数据");
    } else {
        $.ajax({
            url: DELETE_ADMIN_URL,
            type: 'post',
            cache: false,
            data: JSON.stringify(str),
            contentType: "application/json;charset=utf-8", // 因为上面是JSON数据
            success: function (result) {
                /*switch (result){
                    case 0:
                        alert("删除失败");
                        break;
                    case 1:
                        alert("删除成功");
                        break;
                    case -1:
                        alert("您没有权限");
                        break;
                }*/
                if(result==1){
                    alert("删除成功");
                    findLists(pageNum,10);
                }if(result==-1){
                    alert("你没有权限");
                }else if (result==0){
                    alert("删除失败");
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
 * 将数据添加到表格
 * @param list
 */
function table(list) {   //
    var str = "";
    for(var i=0;i<list.length;i++){
        var obj = list[i];
        str += "<tr id=\"admin_"+obj.adminid+"\">" +
            "<td>"+(i+1)+"</td>" +
            "<td><input name=\'adminid\' value=\'"+obj.adminid+"\' type=\"checkbox\" /></td>" +
            "<td>" + obj.realname + "</td>"+
            "<td>" + obj.age + "</td>"+
            "<td>" + (obj.sex==1?'男':'女') + "</td>"+
            "<td>" + obj.phone + "</td>"+
            "<td class=\"action-td\"><a href=\"javascript:updateAdmin("+obj.adminid+")\" class=\"btn btn-small btn-warning\">编辑</a></td></tr>";
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
    $('#adminWidget').show();
}

/**
 * 关闭窗口
 */
function cloasPanal() {
    $('#adminWidget').hide();
    clear();
}

function addAdmin() {
    $('#adminWidgetHeader').html("添加管理员");
    $('#submitbtn').attr('onclick','add()');
    openFormPanal();
}

function updateAdmin(id) {
    $('#adminWidgetHeader').html("编辑管理员");
    for(var i=0;i<list.length;i++) {
        if (id == list[i].adminid) {
            var obj = list[i];
            $('#aname').val(obj.realname);
            $('#aage').val(obj.age);
            $('#aphone').val(obj.phone);
            $('#aqq').val(obj.qq);
            $('#awechat').val(obj.wechat);
        }
    }
    $('#submitbtn').attr('onclick','update('+id+')');
    openFormPanal();
}


/**
 * 清空表格
 */
function clear() {
    $('#aname').val("");
    $('#aage').val("");
    $('#asex').val("");
    $('#aphone').val("");
    $('#aqq').val("");
    $('#awechat').val("");
}

/**
 * 验证
 * @returns {boolean}
 */
function check() {   //验证
    if($('#aname').val()==""||$('#aname').val()==null){
        alert("姓名不能为空");
        return false;
    }
    if($('#aphone').val()==""||$('#aphone').val()==null){
        alert("手机号不能为空");
        return false;
    }
    return true;
}
