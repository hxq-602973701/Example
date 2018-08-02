<#--
 所属模块：框架模块
 页面名称：默认登录页面
 创建时间：2018/06/01
 创建人员：lt
 -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>

    <link href="/assets/css/bootstrap.min.css?${RES_TIMESTAMP}" rel="stylesheet">
    <link href="/assets/font-awesome/css/font-awesome.css?${RES_TIMESTAMP}" rel="stylesheet">

    <link href="/assets/css/animate.css?${RES_TIMESTAMP}" rel="stylesheet">
    <link href="/assets/css/style.css?${RES_TIMESTAMP}" rel="stylesheet">

    <script src="/assets/js/jquery-2.1.1.min.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/js/bootstrap.min.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/js/plugins/slimscroll/jquery.slimscroll.min.js?${RES_TIMESTAMP}"></script>

    <!-- layer -->
    <script src="/assets/layui/laydate/laydate.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/layui/layer/layer.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/layui/laypage/laypage.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/layui/laytpl/laytpl.js?${RES_TIMESTAMP}"></script>

    <!-- Common -->
    <script src="/assets/common/common-v2.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/common/file-uploader.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/common/validate.js?${RES_TIMESTAMP}"></script>

    <!-- Custom and plugin javascript -->
    <script src="/assets/js/moment.min.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/js/plugins/pace/pace.min.js?${RES_TIMESTAMP}"></script>

    <title>${SYSTEM_NAME}</title>
    <style>
        body {
            position: relative;
            background-color: #00000a;
            overflow: hidden;
            margin: 0;
            padding: 0;
            font-family: 'helvetica neue', arial, 'hiragino sans gb', 'microsoft yahei', sans-serif;
            line-height: 1;
            min-height: 700px;
            height: 100%;
            background: url(/assets/img/earth-bg.jpg) no-repeat center center;
            background-size: cover;
        }

        header {
            position: fixed;
            top: 0;
            left: 0;
            z-index: 9999999999999999999999999999999;
            padding: .5em;
        }

        header p,
        header > div {
            text-align: left;
            z-index: 99999;
        }

        .banner-name {
            margin-top: 10px;
            display: inline-block;
            position: relative;
            top: 17px;
            left: 10px;
        }

        .banner-name > p:nth-child(1) {
            font-size: 33px;
            font-weight: 700;
        }

        .banner-name > p:nth-child(2) {
            margin-top: 2px;
            font-size: 14px;
            color: #c1dbe4;
        }

        @-webkit-keyframes earthMoveFront {
            from {
                -webkit-transform: translate3d(0, 0, 0);
                transform: translate3d(0, 0, 0)
            }
            to {
                -webkit-transform: translate3d(1950px, 0, 0);
                transform: translate3d(1950px, 0, 0)
            }
        }

        @keyframes earthMoveFront {
            from {
                -webkit-transform: translate3d(0, 0, 0);
                transform: translate3d(0, 0, 0)
            }
            to {
                -webkit-transform: translate3d(1950px, 0, 0);
                transform: translate3d(1950px, 0, 0)
            }
        }

        @-webkit-keyframes earthMoveBack {
            from {
                -webkit-transform: translate3d(0, 0, 0);
                transform: translate3d(0, 0, 0)
            }
            to {
                -webkit-transform: translate3d(-1950px, 0, 0);
                transform: translate3d(-1950px, 0, 0)
            }
        }

        @keyframes earthMoveBack {
            from {
                -webkit-transform: translate3d(0, 0, 0);
                transform: translate3d(0, 0, 0)
            }
            to {
                -webkit-transform: translate3d(-1950px, 0, 0);
                transform: translate3d(-1950px, 0, 0)
            }
        }

        .section-banner .banner-submit > a {
            box-sizing: border-box;
            display: block;
            width: 189px;
            height: 59px;
            margin: 0 auto;
            line-height: 50px;
            font-size: 17px;
            background-color: rgba(14, 66, 123, .4);
            background-repeat: no-repeat;
            -webkit-transition: background .2s;
            transition: background .2s;
            color: #fff;
        }

        .login {
            width: 334px;
            border: 1px solid rgb(160, 177, 196);
            background-color: #F9F9F9;
            position: absolute;
            right: 5%;
            top: 185px !important;
            z-index: 999;
            min-height: 437px;
            border-radius: 5px;
        }

        @media (max-width: 1024px) {
            .login {
                right: 2%;
            }
        }

        .header {
            height: 50px;
            font-family: "lucida Grande", Verdana, "Microsoft YaHei";
            border-bottom: 1px solid #c0cdd9;
            background-color: #f9fbfe;
            position: relative;
        }

        .switch {
            left: 53px;
            height: 46px;
            position: absolute;
            bottom: 0;
            font-size: 16px;
        }

        .switch-btn {
            color: #999;
            display: inline-block;
            height: 45px;
            line-height: 45px;
            outline: 0;
        }

        .switch #pki-login {
            margin-right: 60px;
        }

        .switch-btn-focus {
            color: #333;
            display: inline-block;
            height: 45px;
            line-height: 45px;
            outline: 0;
        }

        .switch-bottom {
            bottom: 0px;
            border-bottom: 1px solid #465767;
        }

        a {
            text-decoration: none;
        }

        .pki-login {
            height: 317px;
            width: 304px;
            position: relative;
        }

        .btn-show {
            left: 12px;
            width: 280px;
            margin-top: 10px;
            height: 200px;
            position: absolute;
            overflow: hidden;
        }

        .nmd {
            padding-top: 55px;
            height: 35px;
        }

        .btn-pki {
            width: 150px;
            height: 150px;
            margin-left: 80px;
            margin-top: 30px;
            font-size: 1.5em;
            font-family: '微软雅黑';
        }

        .account-login {
            height: 330px;
            width: 304px;
            margin: 0 auto;
            position: relative;
            overflow: hidden;
        }

        .login-show {
            position: absolute;
            top: 0;
        }

        .web-login {
            width: 306px;
            padding-bottom: 0;
            min-height: 289px;
            position: relative;
        }

        .login-form {
            width: 284px;
            margin: 0 auto;
        }

        .acarea {
            height: 50px;
            position: relative;
        }

        .input-outer {
            border: 1px solid #96a5b4;
            height: 38px;
            width: 282px;
            border-radius: 2px;
        }

        .inputstyle {
            padding: 10px 40px 10px 10px;
            border-radius: 3px;
            width: 100%;
            position: relative;
            height: 100%;
            line-height: 18px;
            border: 0;
            background: 0;
            color: #000;
            font-family: Verdana, Tahoma, Arial;
            font-size: 16px;
        }

        .uin-del {
            right: 10px;
            height: 22px;
            width: 22px;
            cursor: pointer;
            position: absolute;
            top: 8px;
        }

        .uin-del:hover {
            background-position: 0 100%;
        }

        .submit {
            margin-top: 23px;
            height: 177px;
            position: relative;
        }

        .login-button {
            right: 0px;
            top: 25px;
            background-color: #5a98de;
            outline: none;
            border-radius: 3px;
            position: absolute;
            left: 0;
        }

        .login-button .btn {
            background: none;
            height: 40px;
            line-height: 30px;
            width: 100%;
            outline: none;
            font-weight: normal;
            border: 0;
            font-size: 18px;
            color: #fff;
            cursor: pointer;
        }

        .bottom {
            width: 282px;
            right: 12px;
            margin-bottom: 15px;
            bottom: 2px;
            height: 16px;
            position: absolute;
            text-align: right;
            font-size: 12px;
        }

        .bottom .link {
            color: #225592;
        }

        .dotted {
            color: #bfbfbf;
            margin: 0 5px;
        }

        .a-container {
            width: 820px;
            position: absolute;
            left: 2%;
            top: 185px !important;
            height: 680px;
            overflow: auto;
        }

        .a-container .m-b-lg {
            position: absolute;
            right: 10px;
            bottom: 10px;
        }

        @media (min-width: 1025px) {
            .a-container {
                width: 800px;
            }

            .a-container li {
                height: 180px;
            }
        }

        @media (max-width: 1024px) {
            .a-container {
                width: 500px;
            }

            .a-container li {
                height: 150px;
            }
        }

        @media (max-width: 800px) {
            .a-container {
                width: 400px;
            }

            .a-container li {
                height: 120px;
            }
        }

        @media (max-height: 800px) {
            .a-container {
                width: 620px;
                height: 620px;
            }

            .a-container li {
                height: 180px;
            }
        }

        @media (max-height: 600px) {
            .a-container {
                width: 400px;
                height: 450px;
            }

            .a-container li {
                height: 120px;
            }
        }

        .a-container img {
            border-radius: 8px;
            transform-origin: 50%;
            transform: scale(0.3);
            transition: transform 0.5s linear;
        }

        .a-container li {
            position: relative;
            width: 25%;
            float: left;

        }

        .a-container li:hover img {
            transform: scale(0.4);
        }

        .a-container h2 {
            width: 100%;
            text-align: center;
            position: absolute;
            bottom: 10%;
        }

        .a-container h2 > a {
            font-size: 16px;
        }

        .a-container div > a {
            display: block;
        }

        h2 a {
            color: #33C7FA;
        }

        h2 a:hover {
            color: #0df6f3;
        }

        .laypage_main.laypageskin_default {
            height: 26px;
            min-width: 300px;
        }

        .laypage_main.laypageskin_default span, .laypage_main.laypageskin_default a {
            border: none;
            background-color: transparent;
        }

        .laypage_main.laypageskin_default span, .laypage_main.laypageskin_default a {
            float: left;
            width: 90px;
            color: #33C7FA;
        }
    </style>

</head>
<body style="position:relative;">
<header>
    <img src="/assets/img/jinghui.png" width="80" height="80">

    <div class="banner-name" style="color: #fff">
        <p>杭州市公安局人力情报信息平台</p>

        <p>The All Police Intelligence Platform Of Hangzhou Public Security Bureau</p>
    </div>
</header>


<div class="login">
    <div id="header" class="header">
        <div class="switch">
            <a class="switch-btn-focus" id="pki-login">PKI登陆</a>
            <a class="switch-btn" id="account-login">帐号登录</a>

            <div class="switch-bottom" id="switch-bottom" style="position: absolute; width: 64px; left: 0px;"></div>
        </div>
    </div>
    <div class="nmd"></div>

    <div class="pki-login">
        <div class="btn-show">
            <button class="btn btn-primary btn-pki">PKI登陆</button>
        </div>
        <div class="bottom">
            <a class="link1">技术支持</a>
            <span class="dotted">|</span>
            <a class="link2">关于系统</a>
            <span class="dotted">|</span>
            <a class="link3">常用软件下载</a>
        </div>
    </div>

    <div class="account-login">
        <div class="login-show">
            <div class="web-login">
                <div class="login-form">
                    <form id="form-login">
                        <div class="acarea">
                            <div class="input-outer account-outer">
                                <input type="text" class="inputstyle account" placeholder="请输入账号">
                                <a class="uin-del" id="uin-del" style="display: none;"></a>
                            </div>
                        </div>

                        <div class="pwarea">
                            <div class="input-outer password-outer">
                                <input type="password" class="inputstyle password" placeholder="请输入密码">
                            </div>
                        </div>

                        <div class="submit">
                            <a class="login-button">
                                <input type="submit" value="登 录" class="btn btn-login">
                            </a>
                        </div>
                    </form>
                </div>

                <div class="bottom">
                    <a class="link1">技术支持</a>
                    <span class="dotted">|</span>
                    <a class="link2">关于系统</a>
                    <span class="dotted">|</span>
                    <a class="link3">常用软件下载</a>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="a-container" id="tplUl">
    <ul>

    </ul>
    <div class="m-b-lg" style="padding-top: 10px; margin-bottom: 50px;">
        <div id="page-bar" class="pull-right"></div>
    </div>
</div>

<script>
    $(function () {
        var type = 0;
        $(".link1").click(function () {
            type = 1;
            loadMore();
        });
        $(".link2").click(function () {
            type = 2;
            loadMore();
        });
        $(".link3").click(function () {
            type = 3;
            loadMore();
        });

        function loadMore() {
            kirin.popup.open({
                title: '技术支持',
                width: 630,
                height: 480,
                maxmin: false,
                content: '/support?type=' + type
            });
        }

//    $('.link').on('click', function () {
//
//      //弹出技术支持内容
//      kirin.popup.open({
//        title  : '技术支持',
//        width  : 630,
//        height : 480,
//        maxmin : false,
//        content: '/support'
//      });
//    });

        /**
         * 填充用户账户
         */
        (function () {
            var account = kirin.getCookie('account');
            if (account) {
                $('.account').val(account);
            }
        })();

        //16个字居中
        $(window).resize(function () {
            $('.font-container').css("top", ($(window).height() - $('.font-container').height()) / 2);
        });

        $('.font-container').css("top", ($(window).height() - $('.font-container').height()) / 2);



        /**
         * 切换登录方式
         */
        $('#pki-login').on('click', function () {
            $('.pki-login').css('display', 'block');
            $('.account-login').css('display', 'none');
            $('.switch-bottom').css('left', '0');
            $(this).css('color', '#333');
            $('#account-login').css('color', '#999');
        });
        $('#account-login').on('click', function () {
            $('.pki-login').css('display', 'none');
            $('.account-login').css('display', 'block');
            $('.switch-bottom').css('left', '125px');
            $(this).css('color', '#333');
            $('#pki-login').css('color', '#999');
        });

        $('#account-login').trigger('click');

        //聚焦，失焦样式
        $('.account').focus(function () {
            $('.account-outer').css('border', '1px solid #4892e7');
        });

        $('.account').blur(function () {
            $('.account-outer').css('border', '1px solid #96a5b4');
        });

        $('.password').focus(function () {
            $('.password-outer').css('border', '1px solid #4892e7');
        });

        $('.password').blur(function () {
            $('.password-outer').css('border', '1px solid #96a5b4');
        });

        /**
         * 错误标记
         * @param element
         */
        var markerError = function (select, markerFlag, message) {
            var parent = $(select).parent();
            parent.removeClass('animated shake');
            if (markerFlag == true) {
                layer.tips(message, select);
                parent.addClass('has-error animated shake');
                $(select).focus();
            } else {
                parent.removeClass('has-error animated shake');
            }
        };

        /**
         * 账户登录
         */
        $('#form-login').on('submit', function () {

            var accountObj = $('.account'),
                    passwordObj = $('.password'),
                    account = $.trim(accountObj.val()),
                    password = $.trim(passwordObj.val());

            if (account === '') {
                markerError('.account', true, '请输入用户账户！');
                return false;
            } else {
                markerError('.account', false);
            }

            if (password === '') {
                markerError('.password', true, '请输入用户密码！');
                return false;
            } else {
                markerError('.password', false);
            }

            var loading = layer.msg('登录中...', {time: 200000});

            kirin.ajax({
                type: 'POST',
                url: '/login.json',
                data: {
                    account: account,
                    password: password,
                    remember: true
                },
                dataType: 'json'
            }).done(function (data) {
                layer.close(loading);
                var meta = data.meta;
                if (meta && meta.messageCode === 'user.account.not_exist') {
                    markerError('.account', true, meta.message);
                    return;
                } else {
                    markerError('.account', false);
                }

                if (meta && meta.messageCode === 'user.password.miss') {
                    markerError('.password', true, meta.message);
                    return;
                } else {
                    markerError('.password', false);
                }
                location.href = data.redirectUrl;
                //location.href = '/index'
            }).fail(function () {
                layer.close(loading);
            });

            return false;
        });

        /**
         * PKI登录
         */
        $('.btn-pki').on('click', function () {

            var loading = layer.msg('登录中...', {icon: 16});

            $.ajax({
                url: '${pkiUrl}',
                dataType: 'jsonp',
                jsonp: 'callback',
                success: function (data) {

                    if (!data.idCard) {
                        layer.close(loading);
                        layer.alert('PKI登录失败：' + data.message);
                        return;
                    }

                    $.ajax({
                        type: 'POST',
                        url: '/login/pki.json',
                        data: {
                            idCardNum: data.idCard
                        },
                        dataType: 'json',
                        success: function (data) {
                            layer.close(loading);

                            var meta = data.meta;
                            if (meta && meta.code == 200) {
                                location.href = data.response.redirectUrl;
                            } else {

                                if (meta && meta.messageCode === 'user.account.not_exist') {
                                    markerError('.account', true, meta.message);
                                    return;
                                } else {
                                    markerError('.account', false);
                                }

                                if (meta && meta.messageCode === 'user.password.miss') {
                                    markerError('.password', true, meta.message);
                                    return;
                                } else {
                                    markerError('.password', false);
                                }

                            }

                        },
                        error: function () {
                            layer.close(loading);
                        }
                    });
                },
                error: function () {
                    layer.close(loading);
                    layer.alert('未检测到PKI数字证书');
                }
            });

            return false;
        });

        //登陆框上下居中
        $(window).resize(function () {
            $('.login').css("top", (($(window).height() - 106 - $('.login').height()) / 2 + 106) + 'px');
        });

        $('.login').css("top", (($(window).height() - 106 - $('.login').height()) / 2 + 106) + 'px');
    })
</script>

<script id="tplHomeUrl" type="text/html">
    {{# for(var idx = 0, len = d.length; idx < len; idx++){ }}
    <li>
        <div>
            <a href="{{= d[idx]['urlValue'] }}" target="_blank">
                <img src="{{= d[idx]['urlImage'] }}" class="img img-responsive">
            </a>
        </div>
        <h2><a href="{{= d[idx]['urlValue'] }}" target="_blank">{{= d[idx]['urlName'] }}</a></h2>
    </li>
    {{# } }}
</script>
<script>
</script>
</body>
</html>