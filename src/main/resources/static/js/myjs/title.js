/**
 * Created by gaowenfeng on 2017/6/1.
 */
var currentPages = 0;  //当前分页组
var resultPages;  //总页数
var resultRows;   //行数
var pageNum;      //当前页
var total;        //总数
var list;         //数据
var titleArray = new Array();
var titleNumArray = new Array();

(function () {
    getCountMap();
    getTcKVList();
    findLists(1,10);
    preImport();
})()


/**
 * 获取列表
 * @param page
 * @param rows
 */
function findLists(page,rows){   //获取广告位列表
    $('#tcList1').val($('#tcList').val())
    $.ajax({
        type:"get",
        url:GETTITLE_URL,
        async:true,
        data:{
            "page":page,
            "rows":rows,
            "tname":$('#tNameKey').val(),
            "titleclassid":$('#tcList').val()==null?0:$('#tcList').val()==""?0:$('#tcList').val()
        },
//		dataType:"JSONP",
        success:function(result){
            table(result.list);
            findKvLists();
            resultPages = result.pages;
            resultRows = result.pageSize;
            pageNum = result.pageNum;
            total = result.total;
            list = result.list;
            pages(resultPages,resultRows);
        },
        error:function(error){
            bootbox.bootbox.alert("访问服务器失败");
        }
    });
}

/**
 * 获取键值对列表
 * @param page
 * @param rows
 */
function findKvLists(){   //获取广告位列表
    $.ajax({
        type:"get",
        url:GETTITLEKV_URL,
        async:true,
        data:{
            "titleclassid":$('#tcList').val()==null?0:$('#tcList').val()==""?0:$('#tcList').val()
        },
        success:function(result){
            titleArray.splice(0,titleArray.length);
            titleNumArray.splice(0,titleNumArray.length);
            for(var i=0;i<result.length;i++){
                var obj = result[i];
                titleArray.push(obj.titlename);
                titleNumArray.push(obj.selectCount);
            }
            echart();
        },
        error:function(error){
            bootbox.bootbox.alert("访问服务器失败");
        }
    });
}

function getTcKVList() {
    $.ajax({
        type:'get',
        url:GETTITLECLASSKV_URL,
        async:false,
        success:function (result) {
            var str = "";
            for(temp in result){
                str += '<option value='+result[temp].titleclassid+'>'+result[temp].titleclassname+'</option>';
            }
            $('#tcList').html(str);
            $('#tcList1').html(str);
        },
        error:function (error) {
            bootbox.bootbox.alert("请求服务器失败");
        }
    })
}

function getCountMap() {
    $.ajax({
        type:'get',
        url:GETCOUNTMAP_URL,
        success:function (result) {
            if(result.result == 1){
                $('#titleClassCount').html(result.titleClassCount)
                $('#titleCount').html(result.titleCount)
                $('#studentCount').html(result.studentCount)
                $('#classCount').html(result.classCount)
            }else{
                bootbox.bootbox.alert("获取总数失败");
            }
        },
        error:function (error) {
            bootbox.bootbox.alert("请求服务器失败");
        }
    })
}

/**
 * 添加
 */
function add() {  //添加广告位
    if(check()){
        $.ajax({
            type:"get",
            url:ADDTITLE_URL,
            async:true,
            data:{
                "titlename":$('#tname').val(),
                "titlecontent":$('#tcontent').val(),
                "titleclassid":$('#tcList1').val()
            },
            success:function(result){
                if(result==1){
                    bootbox.bootbox.alert("添加成功");
                    cloasPanal();
                    if(total%10!=0||total==0){
                        findLists(resultPages,10);
                    }else {
                        findLists(resultPages+1,10);
                    }
                }
                else{
                    bootbox.bootbox.alert("添加失败");
                }
            },
            error:function(error){
                bootbox.bootbox.alert("访问服务器失败");
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
            url:UPDATETITLE_URL,
            async:true,
            data:{
                "titleid":id,
                "titlename":$('#tname').val(),
                "titlecontent":$('#tcontent').val(),
                "titleclassid":$('#tcList1').val()
            },
            success:function(result){
                if(result==1){
                    bootbox.bootbox.alert("编辑成功");
                    cloasPanal();
                    findLists(pageNum,10);
                }else{
                    bootbox.bootbox.alert("编辑失败");
                }
            },
            error:function(error){
                bootbox.bootbox.alert("访问服务器失败");
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
        bootbox.bootbox.alert("您没有选择任何数据");
    } else {
        $.ajax({
            url: DELETETITLE_URL,
            type: 'post',
            cache: false,
            data: JSON.stringify(str),
            contentType: "application/json;charset=utf-8", // 因为上面是JSON数据
            success: function (result) {
                if(result==1){
                    bootbox.bootbox.alert("删除成功");
                    findLists(pageNum,10);
                }else{
                    bootbox.bootbox.alert("删除失败");
                }
            },
            error:function(error){
                bootbox.bootbox.alert("访问服务器失败");
                bootbox.bootbox.alert(error);
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
            "<td>" + obj.titleCode+"</td>" +
            "<td><input name=\'prekey\' value=\'"+obj.titleid+"\' type=\"checkbox\" /></td>" +
            "<td>" + obj.titlename+"</td>" +
            "<td>" + obj.titlecontent+"</td>" +
            "<td>" + obj.create+"</td>" +
            "<td>" + obj.teacher.teachername + "</td>"+
            "<td>" + obj.selectCount+"</td>" +
            "<td class=\"action-td\"><a href=\"javascript:updateP("+obj.titleid+")\" class=\"btn btn-small btn-warning\">编辑</a></td></tr>";
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
/**
 * 打开导入窗口
 * @param id
 */
function openImportFormPanal() {
    $('#importWidget').show();
}

/**
 * 关闭导入窗口
 */
function closeImportPanal() {
    $('#importWidget').hide();
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
        if (id == list[i].titleid) {
            var obj = list[i];
            $('#tname').val(obj.titlename);
            $('#tcontent').val(obj.titlecontent);
            $('#tcList1').val(obj.titleclassid);
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
    $('#tcontent').val("");
}

/**
 * 验证
 * @returns {boolean}
 */
function check() {   //验证
    if($('#tname').val()==""||$('#tname').val()==null){
        bootbox.bootbox.alert("题目名称不能为空");
        return false;
    }
    if($('#tcontent').val()==""||$('#tcontent').val()==null){
        bootbox.bootbox.alert("题目内容不能为空");
        return false;
    }
    return true;
}

function echart() {
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '选题情况统计'
        },
        tooltip: {},
        legend: {
            data:['题目类型1']
        },
        xAxis: {
            data: titleArray
        },
        yAxis: {},
        series: [{
            name: '选题人数',
            type: 'bar',
            data: titleNumArray
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}
function preImport() {
    var control = $('#excel');
    control.fileinput({
        language:'zh',  //设置语言
        uploadUrl:ADMIN_EXCEL_IMPORT_URL,
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
                bootbox.bootbox.alert("处理成功");
                findLists(1,10);
                closeImportPanal();
            }else if(data.response.result==-1){
                bootbox.bootbox.alert("学号为"+data.response.batchResult+"的记录重复，请检查后重新添加");
                findLists(1,10);
                closeImportPanal();
            }else if(data.response.result == -5){
                bootbox.bootbox.alert("excel格式有误，请下载模板比对后重新导入");
                closeImportPanal();
            }else{
                bootbox.bootbox.alert("处理失败");
                findLists(1,10);
                closeImportPanal();
            }
        })
        // .on("filebatchselected",function (event,files) {
        //     $(this).fileinput("upload");
        // })
        .on("fileerror",function (a,b,c) {
            bootbox.bootbox.alert("访问服务器失败");
        });
}

function download() {
    window.location.href="./temp/title.xls";
}
