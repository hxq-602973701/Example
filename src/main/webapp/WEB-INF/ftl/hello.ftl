
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
</head>
<script src="\jquery\jquery.js"></script>
<script src="\layer\layer.js"></script>
<script src="\laytpl-v1.1\laytpl\laytpl.js"></script>
<body>
<button id="test1">${username}' </button>
<script>

    $('#test1').on('click', function(){
        layer.msg('Hello layer',{icon: 0});
    });

</script>
<a type="button" id="save" class="">添加</a>
<script id="bookTemp" type="text/html">
    {{# for(var i = 0, len = d.length; i < len; i++){ }}
    <tr >
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
</table>

<script>
    $(function(){

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
    })
</script>
</body>
</html>
