<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>杭州市公安局人力情报信息平台</title>
    <link href="/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="/assets/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="/assets/css/homemenu.css" rel="stylesheet"/>
    <script src="/assets/js/jquery-2.1.1.min.js"></script>
</head>
<body>
<#assign userProfile = utils.getUserProfile()>
<div class="hd-bg"></div>
<div class="nav">
    <div class="logo">
        <a href="index.html">
            <img src="/assets/img/jinghui.png" width="70" height="70" style="vertical-align:text-bottom; margin-right:15px">
        </a>
    </div>
    <hgroup>
        <p>杭州市公安局人力情报信息平台</p>
        <p>The All Police Intelligence Platform Of Hangzhou Public Security Bureau</p>
    </hgroup>
    <div class="tit-group">
        <a>
            <i class="fa fa-lg fa-user"></i>
            <span>${userProfile.userName?html}</span>
        </a>
        <a href="/user/logout.html">
            <i class="fa fa-lg fa-sign-out"></i>
        </a>
    </div>
</div>
<div class="block">
    <div class="container">
        <ul>
            <#list utils.subSystem() as menu>
                <li>
                    <a href="${menu.menuUrl?html}" target="_blank">
                        <div class="module-item">
                            <img src="${menu.menuImage?html}" alt="${menu.menuName?html}">
                            <#--<span class="label label-success count-info">18</span>-->
                        </div>
                        <div class="typemask"><img src="/assets/img/typemask-03.png" width="250" height="250"></div>
                        <h3>${menu.menuName?html}</h3>
                    </a>
                    <#if !utils.isAuthCheck(userProfile.authType,menu.menuRoles)><div class="no-authType"></div></#if>
                </li>
            </#list>
        </ul>
    </div>
</div>
<footer>
    <div id="footer">
        <a class="qrcode">技术支持：杭州尤拉夫科技有限公司 电话：0571-89906536</a>
    </div>
</footer>
</body>
</html>
<script src="/assets/common/common-v2.js"></script>
<script src="/assets/layui/layer/layer.js?${RES_TIMESTAMP}"></script>
<script src="/assets/layui/laydate/laydate.js?${RES_TIMESTAMP}"></script>
<script src="/assets/layui/laypage/laypage.js?${RES_TIMESTAMP}"></script>
<script src="/assets/layui/laytpl/laytpl.js?${RES_TIMESTAMP}"></script>
<script>
    $(function () {
        $('.qrcode').on('click',function(){

            //弹出技术支持内容
            kirin.popup.open({
                title: '技术支持',
                width: 630,
                height: 480,
                maxmin: false,
                content: '/support'
            }).done(function (data) {
            });
        });
    });
</script>
