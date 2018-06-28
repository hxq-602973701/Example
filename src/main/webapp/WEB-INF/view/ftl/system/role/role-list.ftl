<#--
 所属模块：系统模块
 页面名称：角色列表
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
                <table id="tbl-role" class="table table-bordered table-striped table-hover">
                    <thead>
                    <tr>
                        <th style="width: 30px;">
                            <input type="checkbox" id="chk-all"/>
                            <label for="chk-all" class="check-box"></label>
                        </th>
                        <th class="u-table-center" style="width: 30px;">No.</th>
                        <th class="u-table-center" style="width: 250px;">角色名称</th>
                        <th class="u-table-center">角色说明</th>
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
<!-- 角色行模版 -->
<script id="tpl-role-row" type="text/html">
    {{# for(var i = 0, len = d.length; i < len; i++){ }}
    <tr>
        <td class="u-table-center">
            <input type="checkbox" id="chk-row-{{d[i]['roleId']}}" class="chk-row" value="{{ d[i]['roleId'] }}"/>
            <label for="chk-row-{{d[i]['roleId']}}" class="check-box"></label>
        </td>
        <td class="u-table-center">{{= i+1 }}</td>
        <td>{{= d[i]['roleName'] }}</td>
        <td>{{= d[i]['roleMemo'] }}</td>
        <td class="u-table-center">
            <button type="button" data-id="{{= d[i]['roleId'] }}" class="btn btn-sm btn-outline btn-primary btn-edit">
                编辑
            </button>
            <button type="button" data-id="{{= d[i]['roleId'] }}" class="btn btn-sm btn-outline btn-success btn-auth">
                菜单
            </button>
            <button type="button" data-id="{{= d[i]['roleId'] }}" class="btn btn-sm btn-outline btn-danger btn-del">删除
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
                    view: '#tbl-role',
                    tpl: '#tpl-role-row'
                },
                function (data) {
                    return kirin.ajax({
                        url: '/system/role-page.json',
                        data: data
                    });
                }
        );

        // 新增按钮
        $('.btn-insert').on('click', function () {
            kirin.popup.open({
                title: '编辑角色',
                width: 600,
                height: 350,
                maxmin: false,
                content: '/system/role.html',
            }).done(function (data) {
                if (data === 'update') {
                    pageing.reload();
                } else {
                    pageing.init();
                }
            });
        });


        // 编辑按钮
        $('#tbl-role').on('click', '.btn-edit', function () {
            var roleId = $(this).data('id');
            kirin.popup.open({
                title: '编辑角色',
                width: 600,
                height: 350,
                maxmin: false,
                content: ['/system/role.html?roleId=', roleId].join(''),
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


            var roleIds = [];

            //获取所有选中的角色ID
            $('.chk-row:checked').each(function () {
                roleIds.push(Number($(this).val()));
            });

            if (roleIds.length == 0) {
                layer.msg('请选择要删除的用户。', {icon: 2}, {time: 1500});
//                    layer.alert('请选择要删除的用户。');
                return false;
            }
            else {
                //询问框
                layer.confirm('与此相关的菜单与用户都将失效！<br>确定删除选中系统角色？',
                        {
                            icon: 0,
                            title: '删除角色'
                        },
                        function () {
                            // 删除
                            kirin.ajax({
                                type: 'DELETE',
                                url: '/system/role.json',
                                data: {
                                    roleIds: roleIds
                                }
                            }).done(function () {
                                pageing.reload();
                                layer.msg('系统角色删除成功。', {icon: 1}, {time: 1500});
                            });
                        });
            }
        });

        //全选/全不选
        $('#chk-all').on('click', function () {
            var checked = $(this).prop('checked');
            $('.chk-row').prop('checked', checked);
        });

        // 菜单权限按钮
        $('#tbl-role').on('click', '.btn-auth', function () {
            var roleId = $(this).data('id');
            kirin.popup.open({
                title: '编辑角色菜单',
                width: 405,
                height: 600,
                maxmin: false,
                content: ['/system/role-menu-tree.html?roleId=', roleId].join(''),
            }).done(function (data) {
                console.log(data);
            }).fail(function (data) {
                console.log(data);
            });
        });

        // 删除按钮
        $('#tbl-role').on('click', '.btn-del', function () {
            $('.chk-row').prop('checked', false);
            var roleId = $(this).data('id');
            $('#chk-row-' + roleId).prop('checked', true);
            $('.btn-dels').trigger('click');
        });

    });
</script>
</@override>

<#-- 集成的Layout，必需在底部 -->
<@extends name="/common/layout/layout.ftl"/>
