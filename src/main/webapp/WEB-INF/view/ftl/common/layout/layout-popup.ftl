<#--
 所属模块：框架模块
 页面名称：系统模版
 创建时间：2016/05/29
 创建人员：xinfeng.hu
 -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link href="/assets/images/favicon.ico?${RES_TIMESTAMP}" rel="Bookmark">
    <link href="/assets/images/favicon.ico?${RES_TIMESTAMP}" rel="Shortcut Icon"/>

    <link href="/assets/css/bootstrap.min.css?${RES_TIMESTAMP}" rel="stylesheet">
    <link href="/assets/font-awesome/css/font-awesome.min.css?${RES_TIMESTAMP}" rel="stylesheet">
    <link href="/assets/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css?${RES_TIMESTAMP}" rel="stylesheet">
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

    <!-- Common -->
    <script src="/assets/common/common-v2.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/common/file-uploader.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/common/validate.js?${RES_TIMESTAMP}"></script>
    <script src="/assets/statistics-analysis/statistics-analysis.js?${RES_TIMESTAMP}"></script>

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

<body class="gray-bg" style="height: 100%;">
<div id="wrapper" style="margin: 0px;min-height: 0px">
    <!-- main content start -->
    <#-- 主界面 -->
        <@block name="main"></@block>
    <!-- main content end -->
</div>
<#-- 脚本部分 -->
<script>
  $(function () {
    //收起
    $('.ibox-handle').click(function () {
      var cl = $(this);
      cl.next('.ibox-content').slideToggle(600, function () {
        $('span.u-caret',cl).toggleClass('u-back-90')
      })
    });

    //弹出窗口最后一个ibox距离下方按钮距离50px(防止按钮把内容挡住)
    $('.ibox:last').css('margin-bottom','42px')
  })
</script>
<@block name="scripts">
</@block>
</body>
</html>