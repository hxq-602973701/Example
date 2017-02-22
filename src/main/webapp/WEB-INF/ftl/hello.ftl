
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>Title</title>
</head>
<script src="\jquery\jquery.js"></script>
<script src="\layer\layer.js"></script>
<script src="\laytpl-v1.1\laytpl\laytpl.js"></script>
<script src="\laytpl-v1.1\laytpl\common-v2.js"></script>
<script src="\laypage\skin\laypage.css"></script>
<script src="\laypage\laypage.js"></script>
<link rel="stylesheet" href="//res.layui.com/layui/build/css/layui.css"  media="all">

<body>
<button id="test1">${username}' </button>
<script>

    $('#test1').on('click', function(){
        layer.msg('Hello layer',{icon: 0});
    });

</script>
<a type="button" id="save" class="">添加</a>&nbsp;
<button id="del" type="button" class="btn btn-sm btn-danger">删除</button>
<button id="export" type="button" class="btn btn-sm btn-danger">导出</button>
<script id="bookTemp" type="text/html">
    {{# for(var i = 0, len = d.length; i < len; i++){ }}
    <tr>
        <td><input type="checkbox" class="i-checks" name="checkrow" value="{{ d[i].bookId }}"></td>
        <td>{{=i+1}}</td>
        <td>{{=d[i].bookName}}</td>
        <td>{{=d[i].bookAuthor}}</td>
        <td>
            <button type="button" class="btn-look" data-book-id="{{=d[i].bookId}}">查看
        </button>
        </td>
    </tr>
        {{# } }}
</script>
<table id="tableView">
    <thead>
    <tr>
        <th style="width: 40px;">No.</th>
        <th style="width: 150px">书名</th>
        <th>作者</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="view">
    </tbody>
    <#--<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">-->
        <#--<legend>是时候看一下完整功能了！</legend>-->
    <#--</fieldset>-->
    <#--<div id="demo6"></div>-->
</table>
<script>
    var curPage = 1;
    var pageSize = 5;
    $(function(){

       /* laypage({
            cont: 'demo6'
            ,pages: 100
            ,skip: true
            ,skin: '#c00'
        });*/

        $.ajax({
            url:'/book.html',
            async:true,
            type:'get',
            timeout:5000,
            dataType:'json',
            success:function(data,textStatus,jqXHR){
                console.log(data)
                console.log(textStatus)
                console.log(jqXHR)
                var gettpl = document.getElementById('bookTemp').innerHTML;
                laytpl(gettpl).render(data, function(html){
                    document.getElementById('view').innerHTML = html;
                });
            },
            error:function(xhr,textStatus){
                console.log('出错了')
            }
        })
      /*  var pageing = kirin.pageing(
                {
                    view: '#view',
                    tpl: '#bookTemp'
                }, function (data) {
                    //data.dutyDeptId = 1;
                    //判断现在的主责部门是研发部
                    return kirin.ajax({
                        url: '/book.html'
                    });
                });*/


        $('#tableView').on('click', '.btn-look', function () {
         var  bookId = $(this).data("bookId");
            console.log(bookId);
            layer.open({
                type     : 2,
                title    : '查看',
                area     : ['800px', '655px'],
                fix      : false,
                maxmin   : false,
                scrollbar: false,
                content  : "/book/show.html?bookId="+bookId
        });
        });

        $("#save").on("click",function () {
            layer.open({
                type     : 2,
                title    : '添加',
                area     : ['800px', '655px'],
                fix      : false,
                maxmin   : false,
                scrollbar: false,
                content: '/book/show.html'
            })
        });

        $("#del").on("click",function () {
            var checkNum = 0;
            var ids = "";
            $('input[name="checkrow"]:checked').each(function(){
                if(checkNum > 0){
                    ids += ",";
                }
                ids += parseInt($(this).val());
                checkNum++;
            });
            if(checkNum <= 0){
                layer.alert("请选择需要删除的数据！");
                return;
            }

            layer.confirm('确认删除？', {
                btn: ['是','否'], //按钮
                shade: false //不显示遮罩
            }, function(){
                $.ajax({
                    type: 'GET',
                    url: '/book/book_del.html',
                    traditional: true,
                    dataType: 'json',
                    cache: false,
                    data: {
                        ids: ids
                    },
                    success: function (data) {
                        console.log(data);
                        layer.alert('删除成功。', 0);
                        refreshList();
                    }, error: function () {
                        layer.alert("删除失败", 0);
                    }
                });
            });
        })

       function refreshList(){
            $.ajax({
                url:'/book.html',
                async:true,
                type:'get',
                timeout:5000,
                dataType:'json',
                success:function(data,textStatus,jqXHR){
                    var gettpl = document.getElementById('bookTemp').innerHTML;
                    laytpl(gettpl).render(data, function(html){
                        document.getElementById('view').innerHTML = html;
                    });
                },
                error:function(xhr,textStatus){
                    window.location.reload();
                }
            })
        }

        //导出
        $("#export").click(function () {
            // 调用导出Excel
            exportEvent('view', 'bookTemp', '0,5', '/book.html',"");
        });


        // 导出事件
        function exportEvent(tableId, tableTempId, igCol, exportUrl, exportParams) {
            var opsCol = "";
            if (typeof(igCol) != "undefined" && igCol != null && "" != igCol) {
                opsCol = "[" + igCol + "]";
            }
            // 获取所有数据
            $.getJSON(exportUrl, exportParams, function (resdata) {
                    //渲染
                    var griddata = resdata.response.list;
                    var gettpl = document.getElementById(tableTempId).innerHTML;
                    laytpl(gettpl).render(griddata, function (html) {
                        var theadHtml = $("#" + tableId).find("thead").html();
                        var allHtml = "<table><thead>" + theadHtml + "</thead><tbody>" + html + "</tbody></table>";
                        var exportFileName = $('.custom-font-localmenu').text();
                        var subhead = (new Date()).toLocaleString();
                        $(allHtml).tableExport({type: 'excel', escape: 'false', ignoreColumn: opsCol, fileName: exportFileName,subhead:subhead});
                    });

            });
        }
    })
</script>
</body>
</html>
