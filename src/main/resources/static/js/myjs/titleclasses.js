/**
 * Created by gaowenfeng on 2017/6/1.
 */
var currentPages = 0;  //当前分页组
var resultPages;  //总页数
var resultRows;   //行数
var pageNum;      //当前页
var total;        //总数
var list;         //数据


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
        url:GETTITLECLASS_URL,
        async:true,
        data:{
            "page":page,
            "rows":rows,
            "tcName":$('#tcNameKey').val()
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
            url:ADDTITLECLASS_URL,
            async:true,
            data:{
                "titleclassname":$('#tcname').val()
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
            url:UPDATETITLECLASS_URL,
            async:true,
            data:{
                "titleclassid":id,
                "titleclassname":$('#tcname').val()
            },
            success:function(result){
                if(result==1){
                    alert("编辑成功");
                    cloasPanal();
                    findLists(pageNum,10);
                }else{
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
    for (var i = 0; i < document.getElementsByName('prekey').length; i++) {
        if (document.getElementsByName('prekey')[i].checked) {
            str[j++]=document.getElementsByName('prekey')[i].value;
        }
    }
    if (str.length==0) {
        alert("您没有选择任何数据");
    } else {
        $.ajax({
            url: DELETETITLECLASS_URL,
            type: 'post',
            cache: false,
            data: JSON.stringify(str),
            contentType: "application/json;charset=utf-8", // 因为上面是JSON数据
            success: function (result) {
                if(result==1){
                    alert("删除成功");
                    findLists(pageNum,10);
                }else{
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
        str += "<tr>" +
            "<td>"+(i+1)+"</td>" +
            "<td><input name=\'prekey\' value=\'"+obj.titleclassid+"\' type=\"checkbox\" /></td>" +
            "<td>" + obj.titleclassname+"</td>" +
            "<td>" + obj.admin.realname + "</td>"+
            "<td class=\"action-td\"><a href=\"javascript:updateP("+obj.titleclassid+")\" class=\"btn btn-small btn-warning\">编辑</a></td></tr>";
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
    $('#widgetP').show();
}

/**
 * 关闭窗口
 */
function cloasPanal() {
    $('#widgetP').hide();
    clear();
}

function addP() {
    $('#widgetHeader').html("添加题目类别");
    $('#submitbtn').attr('onclick','add()');
    openFormPanal();
}

function updateP(id) {
    $('#widgetHeader').html("编辑题目类别");
    $('#uname').attr('disabled',true);
    for(var i=0;i<list.length;i++) {
        if (id == list[i].titleclassid) {
            var obj = list[i];
            $('#tcname').val(obj.titleclassname);
        }
    }
    $('#submitbtn').attr('onclick','update('+id+')');
    openFormPanal();
}


/**
 * 清空表格
 */
function clear() {
    $('#tcname').val("");
}

/**
 * 验证
 * @returns {boolean}
 */
function check() {   //验证
    if($('#tcname').val()==""||$('#tcname').val()==null){
        alert("类别名称不能为空");
        return false;
    }
    return true;
}
