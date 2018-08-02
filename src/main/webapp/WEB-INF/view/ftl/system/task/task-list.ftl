<#--
 所属模块：系统模块
 页面名称：定时任务处理
 创建时间：2018/08/01
 创建人员：lt
 -->
<@override name="css">
<style type="text/css">
    .datagrid-mask {
        background: #ccc;
    }

    .datagrid-mask-msg {
        border-color: #95B8E7;
    }

    .datagrid-mask-msg {
        background: #ffffff url('../images/loading.gif') no-repeat scroll 5px center;
    }

    .datagrid-mask {
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        opacity: 0.3;
        filter: alpha(opacity=30);
        display: none;
    }

    .datagrid-mask-msg {
        position: absolute;
        top: 50%;
        margin-top: -20px;
        padding: 12px 5px 10px 30px;
        width: auto;
        height: 16px;
        border-width: 2px;
        border-style: solid;
        display: none;
    }

    .list_table {
        border: 1px solid #CCCCCC;
        border-collapse: collapse;
        color: #333333;
        margin: 0 0 0;
        width: 100%;
    }

    .list_table tbody td {
        border-top: 1px solid #CCCCCC;
        text-align: left;
    }

    .list_table th {
        line-height: 1.2em;
        vertical-align: top;
    }

    .list_table td {
        line-height: 2em;
        font-size: 12px;
        vertical-align: central;
        align: left;
    }

    .list_table td input {
        width: 90%;
    }

    .list_table tbody tr:hover th, .list_table tbody tr:hover td {
        background: #EEF0F2;
    }

    .list_table thead tr {
        background: none repeat scroll 0 0 #09f;
        color: #fff;
        font-weight: bold;
        border-bottom: 1px solid #CCCCCC;
        border-right: 1px solid #CCCCCC;
    }
</style>
</@override>

<@override name="main">
<#-- 主体部分 -->
<div class="row">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <div class="bars pull-left" style="margin-bottom: 10px">
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-outline btn-success btn-insert">
                            <i class="glyphicon glyphicon-plus"></i> 新增
                        </button>
                        <button type="button" class="btn btn-outline btn-danger btn-dels">
                            <i class="glyphicon glyphicon-trash"></i> 删除
                        </button>
                    </div>
                </div>
                <table id="tbl-task" class="table table-bordered table-striped table-hover">
                    <thead>
                    <tr>
                        <th style="width: 30px;">
                            <input type="checkbox" id="chk-all"/>
                            <label for="chk-all" class="check-box"></label>
                        </th>
                        <th class="u-table-center" style="width: 30px;">No.</th>
                        <th class="u-table-center" style="width: 50px;">name</th>
                        <th class="u-table-center" style="width: 50px;">group</th>
                        <th class="u-table-center" style="width: 250px;">cron表达式</th>
                        <th class="u-table-center" style="width: 250px;">描述</th>
                        <th class="u-table-center" style="width: 180px;">类路径</th>
                        <th class="u-table-center" style="width: 100px;">spring id</th>
                        <th class="u-table-center" style="width: 80px;">方法名</th>
                        <th class="u-table-center" style="width: 70px;">状态</th>
                        <th class="u-table-center" style="width: 180px;">操作</th>


                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>

                <div style="padding-top: 10px;" class="m-b-lg">
                    <div id="page-info" class="pull-left"></div>
                    <div id="page-bar" class="pull-right"></div>
                </div>

            </div>
        </div>
    </div>
</div>


</@override>
<#-- 脚本部分 -->
<@override name="scripts">
 <!-- 行模版 -->
    <script id="tpl-task-row" type="text/html">
        {{# for(var i = 0, len = d.length; i < len; i++){ }}
        <tr>
            <td class="u-table-center">
                <input type="checkbox" id="chk-row-{{d[i]['jobId']}}" class="chk-row" value="{{ d[i]['jobId'] }}"/>
                <label for="chk-row-{{d[i]['jobId']}}" class="check-box"></label>
            </td>
            <td class="u-table-center">{{= i+1 }}</td>
            <td style="text-align: center">{{= d[i]['jobName'] }}</td>
            <td style="text-align: center">{{= d[i]['jobGroup'] }}</td>
            <td style="text-align: center">{{= d[i]['cronExpression'] }}</td>
            <td style="text-align: center">{{= d[i]['description'] }}</td>
            <td style="text-align: center">{{= d[i]['beanClass'] }}</td>
            <td style="text-align: center">{{= d[i]['springId'] }}</td>
            <td style="text-align: center">{{= d[i]['methodName'] }}</td>
            <td style="text-align: center">
                {{# if(d[i]['jobStatus']==0){ }}
                <span class="glyphicon glyphicon-remove-sign"></span>
                {{# }else{ }}
                <span class="glyphicon glyphicon-ok-sign"></span>
                {{# } }}
            </td>
            <td class="u-table-center">
                {{# if(d[i]['jobStatus']==0){ }}
                <button type="button" data-id="{{= d[i]['jobId'] }}"
                        class="btn btn-sm  btn-primary glyphicon glyphicon-play btn-start">
                </button>
                {{# }else{ }}
                <button type="button" data-id="{{= d[i]['jobId'] }}"
                        class="btn btn-sm btn-primary glyphicon glyphicon-pause btn-stop">
                </button>
                {{# } }}
                <button type="button" data-id="{{= d[i]['jobId'] }}"
                        class="btn btn-sm btn-outline btn-primary btn-edit">编辑
                </button>

                <button type="button" data-id="{{= d[i]['jobId'] }}" class="btn btn-sm btn-outline btn-danger btn-del">
                    删除
                </button>
                <button type="button" data-id="{{= d[i]['jobId'] }}"
                        class="btn btn-sm btn-outline btn-success btn-cron">更改cron表达式
                </button>

            </td>
        </tr>
        {{# } }}
    </script>
<script type="text/javascript">
    $(function () {

        //全选/全不选
        $('#chk-all').on('click', function () {
            var checked = $(this).prop('checked');
            $('.chk-row').prop('checked', checked);
        });

        // 分页
        var pageing = kirin.pageing(
                {
                    view: '#tbl-task',
                    tpl: '#tpl-task-row'
                },
                function (data) {
                    return kirin.ajax({
                        url: '/task/task-list-api.json',
                        data: data
                    });
                }
        );

        /**
         * 启动
         */
        $('#tbl-task').on('click', '.btn-start', function () {

            var jobId = $(this).data("id");
            var index = layer.load(2, {time: 10*1000});
            $.ajax({
                type: "POST",
                async: false,
                dataType: "JSON",
                cache: false,
                url: "/task/change-job-status.json",
                data: {
                    jobId: jobId,
                    cmd: 'start'
                },
                success: function (data) {
                    layer.close(index);
                    if (data.meta.code==200) {
                        pageing.reload();
                    }else{
                        layer.msg(data.meta.message, {icon: 5});
                    }

                }//end-callback
            });//end-ajax

        });

        /**
         * 停止
         */
        $('#tbl-task').on('click', '.btn-stop', function () {

            var jobId = $(this).data("id");
            var index = layer.load(2, {time: 10*1000});
            $.ajax({
                type: "POST",
                async: false,
                dataType: "JSON",
                cache: false,
                url: "/task/change-job-status.json",
                data: {
                    jobId: jobId,
                    cmd: 'stop'
                },
                success: function (data) {
                  layer.close(index);
                    if (data.meta.code==200) {
                        pageing.reload();
                    }else{
                        layer.msg(data.meta.message, {icon: 5});
                    }

                }//end-callback
            });//end-ajax
        });

        /**
         * 更改cron表达式
         */
        $('#tbl-task').on('click', '.btn-cron', function () {
            // var cron = prompt("输入cron表达式！", "");
            var jobId = $(this).data("id");

            layer.prompt({title:'请输入cron表达式',formType: 0}, function(val,index){
                if (val) {
                    // showWaitMsg();
                    $.ajax({
                        type: "POST",
                        async: false,
                        dataType: "JSON",
                        cache: false,
                        url: "/task/update-cron.json",
                        data: {
                            jobId: jobId,
                            cron: val
                        },
                        success: function (data) {
                            // hideWaitMsg();
                            if (data.meta.code==200) {
                                pageing.reload();
                            }else{
                                layer.msg(data.meta.message, {icon: 5});
                            }
                        },//end-callback
                        error:function(err){
                            alert(err.responseText.msg);
                        }
                    });//end-ajax
                }
                layer.close(index);
            });
        });

        // 新增按钮
        $('.btn-insert').on('click', function () {
            kirin.popup.open({
                title: '新增任务',
                width: 800,
                height: 400,
                maxmin: true,
                content: '/task/add-view.html',
            }).done(function (data) {
                if (data === 'update') {
                    pageing.reload();
                } else {
                    pageing.init();
                }
            });
        });

        // 编辑按钮
        $('#tbl-task').on('click', '.btn-edit', function () {
            var jobId = $(this).data("id");
            kirin.popup.open({
                title: '编辑任务',
                width: 800,
                height: 400,
                maxmin: true,
                content: '/task/add-view.html?jobId=' + jobId,
            }).done(function (data) {
                if (data === 'update') {
                    pageing.reload();
                } else {
                    pageing.init();
                }
            });
        });

        // 删除按钮
        $('#tbl-task').on('click', '.btn-del', function () {
            $('.chk-row').prop('checked', false);
            var jobId = $(this).data('id');
            $('#chk-row-' + jobId).prop('checked', true);
            $('.btn-dels').trigger('click');
        });

        // 批量删除按钮
        $('.btn-dels').on('click', function () {

            var jobIds = [];

            //获取所有选中的角色ID
            $('.chk-row:checked').each(function () {
                jobIds.push(Number($(this).val()));
            });

            if (jobIds.length == 0) {
                layer.alert('请选择要删除的任务。');
                return false;
            }
            else {
                //询问框
                layer.confirm('相关的定时任务都将立刻失效并停止！<br>确定删除选中任务么？',
                        {
                            icon: 0,
                            title: '删除任务'
                        },
                        function () {
                            // 删除
                            kirin.ajax({
                                type: 'DELETE',
                                url: '/task/system/task.json',
                                data: {
                                    jobIds: jobIds
                                }
                            }).done(function () {
                                pageing.reload();
                                layer.alert('系统任务删除成功。', {icon: 1});
                            });
                        });
            }
        });

        function showWaitMsg(msg) {
            if (msg) {

            } else {
                msg = '正在处理，请稍候...';
            }
            var panelContainer = $("body");
            $("<div id='msg-background' class='datagrid-mask' style=\"display:block;z-index:10006;\"></div>").appendTo(panelContainer);
            var msgDiv = $("<div id='msg-board' class='datagrid-mask-msg' style=\"display:block;z-index:10007;left:50%\"></div>").html(msg).appendTo(
                    panelContainer);
            msgDiv.css("marginLeft", -msgDiv.outerWidth() / 2);
        }

        function hideWaitMsg() {
            $('.datagrid-mask').remove();
            $('.datagrid-mask-msg').remove();
        }

    });
</script>
</@override>

<#-- 集成的Layout，必需在底部 -->
<@extends name="/common/layout/layout.ftl"/>


