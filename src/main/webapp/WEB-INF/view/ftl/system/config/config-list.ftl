<#--
 所属模块：系统模块
 页面名称：系统参数列表
 创建时间：2018/06/11
 创建人员：lt
 -->

<#-- 主体部分 -->
<@override name="main">
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
                <table id="tbl-config" class="table table-bordered table-striped table-hover">
                    <thead>
                    <tr>
                        <th style="width: 30px;">
                            <input type="checkbox" id="chk-all"/>
                            <label for="chk-all" class="check-box"></label>
                        </th>
                        <th class="u-table-center" style="width: 30px;">No.</th>
                        <th class="u-table-center" style="max-width: 250px;">参数名称</th>
                        <th class="u-table-center" style="max-width: 100px;">参数值</th>
                        <th class="u-table-center" style="min-width: 70px;">所属模块</th>
                        <th class="u-table-center">参数说明</th>
                        <th class="u-table-center" style="max-width: 60px;">操作</th>
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
<!-- 系统配置行模版 -->
<script id="tpl-config-row" type="text/html">
    {{# for(var i = 0, len = d.length; i < len; i++){ }}
    <tr>
        <td class="u-table-center">
            <input type="checkbox" id="chk-row-{{d[i]['configId']}}" class="chk-row" value="{{ d[i]['configId'] }}"/>
            <label for="chk-row-{{d[i]['configId']}}" class="check-box"></label>
        </td>
        <td class="u-table-center">{{= i+1 }}</td>
        <td>{{= d[i]['configKey'] }}</td>
        <td class="u-table-center">{{= d[i]['configValue'] }}</td>
        <td>{{= d[i]['configModule'] }}</td>
        <td>{{= d[i]['configMemo'] }}</td>
        <td class="u-table-center">
            <button type="button" data-id="{{= d[i]['configId'] }}" class="btn btn-sm btn-outline btn-primary btn-edit">
                编辑
            </button>
            <button type="button" data-id="{{= d[i]['configId'] }}" class="btn btn-sm btn-outline btn-danger btn-del">
                删除
            </button>
        </td>
    </tr>
    {{# } }}
</script>
<script type="text/javascript">
    $(function () {

        // 分页
        var pageing = kirin.pageing(
                {
                    view: '#tbl-config',
                    tpl: '#tpl-config-row'
                },
                function (data) {
                    return kirin.ajax({
                        url: '/system/config-page.json',
                        data: data
                    });
                }
        );

        // 新增按钮
        $('.btn-insert').on('click', function () {
            kirin.popup.open({
                title: '新增系统参数',
                width: 600,
                height: 390,
                maxmin: false,
                content: '/system/config.html',
            }).done(function (data) {
                if (data === 'update') {
                    pageing.reload();
                } else {
                    pageing.init();
                }
            });
        });

        // 批量删除按钮
        $('.btn-dels').on('click', function () {

            //询问框
            layer.confirm('删除此系统参数可能导致系统<br>运行异常，请谨慎操作？',
                    {
                        icon: 0,
                        title: '删除系统参数'
                    },
                    function () {
                        var configIds = [];

                        //获取所有选中的角色ID
                        $('.chk-row:checked').each(function () {
                            configIds.push(Number($(this).val()));
                        });

                        if (configIds.length == 0) {
                            layer.alert('请选择待删除的行。');
                            return false;
                        }

                        // 删除
                        kirin.ajax({
                            type: 'DELETE',
                            url: '/system/config.json',
                            data: {
                                configIds: configIds
                            }
                        }).done(function () {
                            pageing.reload();
                            layer.alert('系统参数删除成功。', {icon: 1});
                        });
                    }
            );
        });

        //全选/全不选
        $('#chk-all').on('click', function () {
            var checked = $(this).prop('checked');
            $('.chk-row').prop('checked', checked);
        });

        // 编辑按钮
        $('#tbl-config').on('click', '.btn-edit', function () {
            var configId = $(this).data('id');
            kirin.popup.open({
                title: '编辑系统参数',
                width: 600,
                height: 410,
                maxmin: false,
                content: ['/system/config.html?configId=', configId].join(''),
            }).done(function (data) {
                if (data === 'update') {
                    pageing.reload();
                } else {
                    pageing.init();
                }
            });
        });

        // 删除按钮
        $('#tbl-config').on('click', '.btn-del', function () {
            $('.chk-row').prop('checked', false);
            var configId = $(this).data('id');
            $('#chk-row-' + configId).prop('checked', true);
            $('.btn-dels').trigger('click');
        });

    });
</script>
</@override>

<#-- 集成的Layout，必需在底部 -->
<@extends name="/common/layout/layout.ftl"/>
