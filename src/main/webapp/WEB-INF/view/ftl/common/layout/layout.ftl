<#--
 所属模块：框架模块
 页面名称：系统模版
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
    <link href="/assets/images/favicon.ico?${RES_TIMESTAMP}" rel="Bookmark">
    <link href="/assets/images/favicon.ico?${RES_TIMESTAMP}" rel="Shortcut Icon"/>

    <link href="/assets/css/bootstrap.min.css?${RES_TIMESTAMP}" rel="stylesheet">
    <link href="/assets/font-awesome/css/font-awesome.min.css?${RES_TIMESTAMP}" rel="stylesheet">
    <link href="/assets/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css?${RES_TIMESTAMP}"
          rel="stylesheet">
    <link href="/assets/css/plugins/tagsinput/bootstrap-tagsinput.css?${RES_TIMESTAMP}" rel="stylesheet">
    <link href="/assets/css/animate.css?${RES_TIMESTAMP}" rel="stylesheet">

    <link href="/assets/css/style.css?${RES_TIMESTAMP}" rel="stylesheet">
    <link href="/assets/css/file-uploader.css?${RES_TIMESTAMP}" rel="stylesheet">
    <link href="/assets/css/checkbox-animate.css?${RES_TIMESTAMP}" rel="stylesheet">

    <!-- Simditor -->
    <link href="/assets/css/plugins/simditor/simditor.css" rel="stylesheet"/>

    <!-- ystep -->
    <link href="/assets/css/plugins/ystep/ystep.css?${RES_TIMESTAMP}" rel="stylesheet">

    <script src="/assets/js/jquery-2.1.1.min.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/js/bootstrap.min.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/js/plugins/metisMenu/jquery.metisMenu.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/js/plugins/slimscroll/jquery.slimscroll.min.js?${RES_TIMESTAMP}"></script>

    <!-- layer -->
    <script src="/assets/layui/layer/layer.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/layui/laydate/laydate.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/layui/laypage/laypage.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/layui/laytpl/laytpl.js?${RES_TIMESTAMP}"></script>

    <!-- Tags input -->
    <script src="/assets/js/plugins/tagsinput/bootstrap-tagsinput.js?${RES_TIMESTAMP}"></script>

    <!-- Custom and plugin javascript -->
    <script src="/assets/js/moment.min.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/js/hplus.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/js/plugins/pace/pace.min.js?${RES_TIMESTAMP}"></script>

    <!-- echart -->
    <script src="/assets/js/plugins/echarts/echarts-all.js?${RES_TIMESTAMP}"></script>

    <!-- Simditor -->
    <script src="/assets/js/plugins/simditor/module.js"></script>
    <script src="/assets/js/plugins/simditor/uploader.js"></script>
    <script src="/assets/js/plugins/simditor/hotkeys.js"></script>
    <script src="/assets/js/plugins/simditor/simditor.js"></script>

    <!-- chosen -->
    <link href="/assets/css/plugins/chosen/chosen.css?${RES_TIMESTAMP}" rel="stylesheet">
    <script src="/assets/js/plugins/chosen/chosen.jquery.js?${RES_TIMESTAMP}"></script>

    <!-- webuploader -->
    <link href="/assets/css/plugins/webuploader/webuploader.css?${RES_TIMESTAMP}" rel="stylesheet">
    <script src="/assets/js/plugins/webuploader/webuploader.js?${RES_TIMESTAMP}"></script>

    <!-- 可拖拽树形视图 -->
    <script src="/assets/js/plugins/nestable/jquery.nestable.js?${RES_TIMESTAMP}"></script>

    <!-- 导出 -->
    <script src="/assets/js/plugins/tableexport/tableExport.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/js/plugins/tableexport/jquery.base64.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/js/plugins/tableexport/FileSaver.js?${RES_TIMESTAMP}"></script>

    <!-- ystep -->
    <script src="/assets/js/plugins/ystep/ystep.js?${RES_TIMESTAMP}"></script>

    <!-- underscore -->
    <script src="/assets/js/underscore-min.js?${RES_TIMESTAMP}"></script>

    <!-- Common -->
    <script src="/assets/common/common-v2.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/common/file-uploader.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/common/validate.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/statistics-analysis/statistics-analysis.js?${RES_TIMESTAMP}"></script>

    <!-- cookie -->
    <script src="/assets/js/plugins/cookie/jquery.cookie.js?${RES_TIMESTAMP}"></script>
<#-- 头部内容 -->
<@block name="header">
    <title>${SYSTEM_NAME}</title>
</@block>

<#-- 样式 -->
<@block name="css">
</@block>

<#-- 自动刷新功能 -->
<@common_macro.livereload/>
</head>
<#assign userProfile = utils.getUserProfile()>
<body class="fixed-nav fixed-sidebar">
<div
        id="wrapper"
>
    <!-- top nav start -->


    <div class="row">
        <nav class="navbar navbar-fixed-top custom-bgcolor" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <div class="system-name welcome-message custom-font-white">
                    <img class="headpic" src="/assets/img/police_emblem.png">${SYSTEM_NAME}
                </div>
            </div>
            <div style="margin-left: 450px;height: 68px;background: url('/assets/img/inner_top_banner.png') no-repeat left bottom;">
                <ul class="nav navbar-top-links navbar-right">
                    <!-- message notice start -->
                    <audio id="notice-sound" src="/assets/sound/alert.mp3"></audio>
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-bell" style="color: white;"></i>
                            <span class="label label-danger msg_count">0</span>
                        </a>
                        <ul class="dropdown-menu dropdown-alerts animated fadeInDown" id="div_msg"
                            style="overflow-y: scroll; max-height: 500px;">
                            暂无消息
                        </ul>
                    </li>
                    <!-- message notice end -->
                    <li>
                        <a data-toggle="dropdown" class="dropdown-toggle">
                            <span class="m-r-sm text-muted welcome-message custom-font-white">
                                <i class="fa fa-user custom-font-white"></i>您好，${userProfile.userName}！
                            </span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li>
                                <a href="javascript:editProfile()">个人资料</a>
                                <a class="changePassword">修改密码</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="/user/logout.html">
                            <i class="fa fa-sign-out custom-font-white"></i> <span class="custom-font-white">退出</span>
                        </a>
                    </li>
                </ul>
            </div>
            <ul class="nav navbar-nav navbar-list custom-bgcolor-menu">
            <#list utils.navbar(pageId) as menu>
                <li title="${menu.menuTitle!}"
                    class="<#if menu.menuActive??>${menu.menuActive?string('open','')}</#if>">
                    <a href="${menu.menuUrl!}"><span class="custom-font-white">${menu.menuName!}</span></a>
                </li>
            </#list>
            </ul>
        </nav>
    </div>


    <!-- top nav end -->
    <!-- left sider nav start -->


    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="slimScrollDiv"
             style="position: relative; overflow: hidden; width: auto; height: calc(100% - 56px);">
            <div class="sidebar-collapse" style="overflow: hidden; width: auto; height: calc(100% - 56px);">
                <ul class="nav" id="side-menu">
                <#list utils.sidebar(pageId) as menu>
                    <li title="${menu.menuTitle!}"
                        class="<#if menu.menuActive??>${menu.menuActive?string('active','')}</#if>">
                        <a href="${menu.menuUrl!}">
                            <i class="${menu.menuIcon!}"></i>${menu.menuName!}
                        </a>
                    </li>
                </#list>
                </ul>
            </div>
        </div>
    </nav>


    <!-- left sider nav end -->

    <div id="page-wrapper" class="gray-bg dashbard-1" style="margin-left: 0;">
        <!-- path nav start -->

        <div class="row wrapper border-bottom white-bg page-location">
            <div class="col-lg-10">
                <ol class="breadcrumb"><span class="custom-font-grey">当前位置：</span>
                <#--<#list utils.breadcrumbBar(pageId) as menu>-->
                <#--<li>-->
                <#--<#if menu_has_next>-->
                <#--<a class="custom-font-localmenu" href="${menu.menuUrl}">${menu.menuName}</a>-->
                <#--<#else>-->
                <#--<span>${menu.menuName}</span>-->
                <#--</#if>-->
                <#--</li>-->
                <#--</#list>-->

                <#list utils.breadcrumbBar(pageId) as menu>
                    <li>
                        <#if menu_has_next>
                            <a>${menu.menuName}</a>
                        <#else>
                            <span>${menu.menuName}</span>
                        </#if>
                    </li>
                </#list>
                </ol>
            </div>
        </div>

        <!-- path nav end-->
        <!-- main content start -->
        <div class="wrapper wrapper-content">
        <@block name="main"></@block>
        </div>
        <!-- main content end -->
    </div>
</div>

<script id="msg_tpl" type="text/html">
    {{# for(var i = 0, len = d.length; i < len; i++){ }}
    <li>
        <a href="{{d[i]['url']}}">
            <div>
                <i class="fa fa-envelope fa-fw" style="color: #1AB394"></i>
                {{d[i]['title']}}
                <br>
                <span class="pull-right text-muted small">{{ kirin.dataFormat(d[i]['gmtCreate'],'YYYY-MM-DD HH:mm:ss')}}</span>
            </div>
        </a>
    </li>
    {{# } }}
</script>

<script type="text/javascript">
    $(function () {

//        var msg_val = $.cookie('msg');
//        console.log("cookies",msg_val);

        //10秒钟ping一次，获取铃铛消息
        getMsgCount();
        setTimeout(getMsgCount, 1000 * 10);

        // 设置左侧滚动条样式
        $('.sidebar-collapse').slimScroll({
            height: 'calc(100% - 56px)',
            railOpacity: 0.9
        });

        // 设置右侧内容区高度
        var initMainHeight = function () {
            $("#page-wrapper").css("min-height", $("body").height());
        };
        initMainHeight();
        $(window).on('resize', function () {
            initMainHeight();
        });
        //收起
        $('.ibox-handle').click(function () {
            var cl = $(this);
            cl.next('.ibox-content').slideToggle(600, function () {
                $('span.u-caret', cl).toggleClass('u-back-90')
            })
        });

    });
    function editProfile() {
        kirin.popup.open({
            title: '信息',
            width: 850,
            height: 450,
            maxmin: false,
            content: '/user/profile'
        });
    }
    ;
    $('.changePassword').click(function () {
        kirin.popup.open({
            title: '密码修改',
            width: 860,
            height: 450,
            maxmin: false,
            content: '/user/pwd'
        }).done(function (data) {
            if (data == "success") {
                window.location.href = "/login";
            }
        });
    });

    //获取待签收的指令和条数
    function getMsgCount() {
        $.ajax({
            type: 'GET',
            url: '/intelligence/index_ping_online_intelligence.json',
            traditional: true,
            dataType: 'json',
            cache: false,
            data: {
                ids: 90
            },
            success: function (data) {
                console.log(data);
                var meta = data.meta;
                if (meta && meta.code === 200) {
                    var list = data.response;
                    console.log("获取消息条数" + list.length);
                    $('.msg_count').text(list.length);
                    $.cookie('msg', JSON.stringify(list));
                    if (list.length > 0) {
                        var gettpl = document.getElementById('msg_tpl').innerHTML;
                        laytpl(gettpl).render(list, function (html) {
                            document.getElementById('div_msg').innerHTML = html;
                        });
                    }
                }
            },
            error: function () {
                console.log("消息获取失败");
            }
        });
    }
</script>
<#-- 脚本部分 -->
<@block name="scripts">
</@block>
</body>
</html>