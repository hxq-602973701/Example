
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>Title</title>
</head>
<script src="/jquery/jquery.js"></script>

<script src="/layer/layer.js"></script>
<script src="/laytpl-v1.1/laytpl/laytpl.js"></script>
<script src="/laytpl-v1.1/laytpl/common-v2.js"></script>
<script src="/tableexport/tableExport.js"></script>
<script src="/tableexport/FileSaver.js"></script>
<#--<script src="/laypage/skin/laypage.css"></script>-->
<script src="/tableexport/jquery.base64.js"></script>
<script src="/laypage/laypage.js"></script>
<script src="/echarts/echarts.js"></script>

<#--<link rel="stylesheet" href="//res.layui.com/layui/build/css/layui.css"  media="all">-->

<body>
<button id="test1">${username}</button>
<#assign user = utils.getBook()>
<script>

    $('#test1').on('click', function(){
        layer.msg('Hello layer',{icon: 0});
    });

</script>
<button id="save" type="button" class="btn btn-sm btn-danger">添加</button>
<button id="del" type="button" class="btn btn-sm btn-danger">删除</button>
<button id="export" type="button" class="btn btn-sm btn-danger">导出</button>
<button id="assign" type="button" class="btn btn-sm btn-danger">测试</button>
<script id="bookTemp" type="text/html">
    {{# for(var i = 0, len = d.length; i < len; i++){ }}
    <tr>
        <td><input type="checkbox" class="i-checks" name="checkrow" value="{{ d[i].bookId }}"></td>
        <td>{{=i+1}}</td>
        <td>{{=d[i].bookName}}</td>
        <td>{{=d[i].bookAuthor}}</td>
        <td>
            <button type="button" class="btn-look" data-book-id="{{=d[i].bookId}}">查看${user.bookName!}
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
<#--分页码-->
    <div style="padding-top: 10px;" class="m-b-lg">
        <div id="page-info" class="pull-left"></div>
        <div id="page-bar" class="pull-right"></div>
    </div>
    <#--<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">-->
        <#--<legend>是时候看一下完整功能了！</legend>-->
    <#--</fieldset>-->
    <#--<div id="demo6"></div>-->
</table>
<script>
//    var curPage = 1;
//    var pageSize = 10;
//    var totalPages = 1;
    $(function(){

        //getDataList();
        /**
         * 加载列表
         */
//        function getDataList() {
//            var data = {};
//            $.getJSON('/book2.html',
//                    data,
//                    function (res) { //从第1页开始请求。返回的json格式可以任意定义
//                        console.log(res);
//                            laypage({
//                                cont: 'pager', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
//                                pages: res.total, //通过后台拿到的总页数
//                                curr: 1, //初始化当前页
//                                jump: function (e) { //触发分页后的回调
//                                    curPage = e.curr;
//                                    $('#pageinfo').html('第' + e.curr + '页 &nbsp; 共' + e.pages + '页 &nbsp; &nbsp; 每页<select id="selpage"><option value="10">10</option><option value="20">20</option><option value="50">50</option><option value="100">100</option></select>条记录');
//                                    $('#selpage').val(pageSize);
//                                    $('#selpage').change(function () {
//                                        pageSize = $('#selpage').val();
//                                        getDataList();
//                                    });
//                                    refreshList();
//                                }
//                            });
//                    });
//        }

        // 新增按钮
        $('#assign').on('click', function () {
            var a  = "nihao";
            kirin.popup.open({
                title  : '测试',
                width  : 600,
                height : 350,
                maxmin : false,
                shareData: a,
                content: '/book/shareData.html',
            }).done(function (data) {
            });
        });

        /**
         * 刷新列表
         */
        function refreshList() {

            var data = {};
            data.pageNum = curPage;
            data.pageSize = pageSize;
            $.getJSON('/book.html', data, function (resdata) {
                //totalPages = resdata.response.pages; //重新获取总页数，一般不用写
                    //渲染
                var gettpl = document.getElementById('bookTemp').innerHTML;
                laytpl(gettpl).render(resdata, function(html){
                    document.getElementById('view').innerHTML = html;
                });
            });
        }
//        var curPage = 1;
//        /**
//         * 刷新列表
//         */
//        refresh();
//        function refresh() {
//            pageing.init(curPage);
//            console.log("在线寻人列表在刷新..");
//            setTimeout(function () {
//                refresh()
//            }, 5000);
//        }

        var pageing = kirin.pageing(
                {
                    view: '#tableView',
                    tpl: '#bookTemp'
                }, function (data) {
                    //data.dutyDeptId = 1;
                    //判断现在的主责部门是研发部
                    return   $.ajax({
                        type: 'GET',
                        url: '/book2.html?pageNum='+data.pageNum+"&pageSize="+data.pageSize,
                        traditional: true,
                        dataType: 'json',
                        cache: false


                    });
                });


        $('#tableView').on('click', '.btn-look', function () {
         var  bookId = $(this).data("bookId");
            console.log(bookId);
//            layer.open({
//                type     : 2,
//                title    : '查看',
//                area     : ['800px', '655px'],
//                fix      : false,
//                maxmin   : false,
//                scrollbar: false,
//                content  : "/book/show.html?bookId="+bookId
//        });
            kirin.popup.open({
                title  : '编辑角色',
                width  : 600,
                height : 350,
                maxmin : false,
                content: "/book/show.html?bookId="+bookId
            }).done(function (data) {

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
            $.getJSON('/book.html', '0,5', function (data) {
                var subhead = (new Date().toLocaleString());
                laytpl($('#tplExport').html()).render(data, function (html) {
                    $(html).tableExport({type: 'excel', escape: 'false', fileName: '图书列表',subhead:subhead});
                });
            });
        });
    })
</script>

<!-- 导出Excel模版 -->
<script id="tplExport" type="text/html">
    <table>
        <thead>
        <tr>
            <th style="width: 40px;">No.</th>
            <th style="width: 150px">书名</th>
            <th>作者</th>
        </tr>
        </thead>
        <tbody>
        {{# for(var i = 0, len = d.length; i < len; i++){ }}
        <tr>
            <td>{{=i+1}}</td>
            <td>{{=d[i].bookName}}</td>
            <td>{{=d[i].bookAuthor}}</td>
        </tr>
        {{# } }}
        </tbody>
    </table>
</script>
</body>
</html>
