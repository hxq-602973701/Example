
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Title</title>
</head>
<script src="\jquery\jquery.js"></script>
<script src="\layer\layer.js"></script>
<script src="\laytpl-v1.1\laytpl\laytpl.js"></script>
<body>
<button id="test1">小小按钮</button>
<script>

</script>

<form>
    <input type="hidden" id="bookId" name="bookId" value="${book.bookId?default("")}">
    <table >
        <tr>
            <th class="required-input">书名</th>
            <td>
                <input placeholder="请输入书名" type="text" class="" id="bookName" name="bookName" value="${book.bookName?default("")}">
            </td>
        </tr>
        <tr>
            <th>作者</th>
            <td>
                <input placeholder="请输入作者" type="text" class="" id="bookAuthor" name="bookAuthor" value="${book.bookAuthor?default("")}">
            </td>
        </tr>
        <a type="button" id="save" class="">保存</a>
    </table>
</form>
<script>
    $(function(){

        var thisindex = parent.layer.getFrameIndex(window.name); //获取窗口索引
        /**
         * 保存
         */
        $("#save").on("click", function () {
            var peopleBase = {};
            $("input[name],select[name]").each(function () {
                if ($(this).val() != "") {
                    peopleBase[$(this).attr("name")] = $(this).val();
                }
            });
            var peopleBaseStr = JSON.stringify(peopleBase);
            console.log(peopleBaseStr);
                layer.confirm("确定要保存么", {
                    btn: ['确定', '取消']
                }, function (index) {
                    $.ajax({
                        url:'/book/saveOrUpdate.html',
                        type:"post",
                        dataType:"json",

                        async:true,
                        timeout:5000,
                        data:{peopleBaseStr:peopleBaseStr},
                        success:function(data,textStatus,jqXHR){
                           layer.alert("保存成功!",function (index2) {
                               layer.close(index2);
                               refreshParent();
                               parent.layer.close(thisindex);
                           })

                        },
                        error : function(XMLHttpRequest, textStatus, errorThrown,data) {
                            console.log(XMLHttpRequest.responseText);
                            console.log(XMLHttpRequest.status);
                            console.log(XMLHttpRequest.readyState);
                            console.log(textStatus); // parser error;
                            console.log(data)
                        }
                    })
                });
        });
        function refreshParent() {
            parent.location.reload();
            window.close();
        }
    })
</script>
</body>
</html>
