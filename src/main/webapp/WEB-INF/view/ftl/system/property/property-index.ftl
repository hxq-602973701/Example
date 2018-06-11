<#--
 所属模块：系统模块
 页面名称：业务参数列表
 创建时间：2016/06/11
 创建人员：lt
 -->

<!-- 样式 -->
<@override name="css">
<!-- 树形视图 -->
<link href="/assets/css/plugins/jsTree/style.min.css" rel="stylesheet">
<style>
    #txt-query:focus {
        border-color: #1C84C6 transparent #1C84C6 #1C84C6 !important;
    }
</style>
</@override>

<#-- 主体部分 -->
<@override name="main">
<div class="row">
    <div class="col-sm-3 col-md-3 col-lg-3">
        <div class="ibox ">
            <div class="ibox-title" style="padding: 6px;">
                <h5 style="margin-left: 10px; margin-top: 8px;">业务分类</h5>
            </div>
            <div class="ibox-content">
                <!-- 左侧单位树 -->
                <div id="tree-dept"></div>
            </div>
        </div>
    </div>
    <div class="col-sm-9 col-md-9 col-lg-9 col-padding-left">
        <div class="ibox ">
            <div class="ibox-title" style="padding: 6px;">
                <h5 style="margin-left: 10px; margin-top: 8px;">业务列表</h5>
                </span>
            </div>
            <div class="ibox-content">
                <div class="bars pull-left col-sm-6" style="margin-bottom: 10px;padding-left:0px;">
                    <div class="btn-group" role="group">
                        <button type="button" class="btn btn-outline btn-success btn-insert">
                            <i class="glyphicon glyphicon-plus"></i> 新增
                        </button>
                        <button type="button" class="btn btn-outline btn-danger btn-dels">
                            <i class="glyphicon glyphicon-trash"></i> 删除
                        </button>
                    </div>
                </div>
                <div class="bars pull-right col-sm-6" style="margin-bottom: 10px;padding-right:0px;">
                    <div class="input-group">
                        <input type="text" id="txt-query" placeholder="业务名称。。。" class="form-control">
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-outline btn-success btn-query"
                                    style="margin-left: 0"><i class="fa fa-search"></i> 搜索</button>
                        </span>
                    </div>
                </div>
                <table id="tbl-dept" class="table table-bordered table-striped table-hover">
                    <thead>
                    <tr>
                        <th style="width: 30px;">
                            <input type="checkbox" id="chk-all"/>
                            <label for="chk-all" class="check-box"></label>
                        </th>
                        <th class="u-table-center" style="width: 30px;">No.</th>
                        <th class="u-table-center">名称</th>
                        <th class="u-table-center">类型</th>
                        <th class="u-table-center">值</th>
                        <th class="u-table-center u-operation-4">操作</th>
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
<!-- 树形视图 -->
<script src="/assets/js/plugins/jsTree/jstree.min.js"></script>
<!-- 系统单位行模版 -->
<script id="tpl-dept-row" type="text/html">
    {{# for(var i = 0, len = d.length; i < len; i++){ }}
    <tr>
        <td class="u-table-center">
            <input type="checkbox" id="chk-row-{{d[i]['propId']}}" class="chk-row" value="{{ d[i]['propId'] }}"/>
            <label for="chk-row-{{d[i]['propId']}}" class="check-box"></label>
        </td>
        <td class="u-table-center">{{= i+1 }}</td>
        <td class="u-table-center">{{= d[i]['propKey'] }}</td>
        <td class="u-table-center">{{= d[i]['propTypeName'] }}</td>
        <td class="u-table-center">{{= d[i]['propValue'] }}</td>
        <td class="u-table-center">
            <button type="button" data-id="{{= d[i]['propId'] }}" class="btn btn-sm btn-outline btn-primary btn-edit">
                编辑
            </button>
            <button type="button" data-id="{{= d[i]['propId'] }}" class="btn btn-sm btn-outline btn-danger btn-del">删除
            </button>
        </td>
    </tr>
    {{# } }}
</script>
<script type="text/javascript">
    $(function () {

        // 当前选择的节点
        var selectedNode, pageing;

        //构建树结构
        kirin.ajax({
            url: '/property/property_tree.json',
        }).done(function (data) {
            var data = JSON.parse(data);
            $('#tree-dept').jstree({
                'core': {
                    data: data
                }
            }).on('changed.jstree', function (e, data) {
                //设置当前选择的节点
                selectedNode = data.node.id;
                $('#txt-query').val('');
                pageing.init();
            });

            // 分页
            pageing = kirin.pageing(
                    {
                        view: '#tbl-dept',
                        tpl: '#tpl-dept-row',
                        noDataText: '此参数下暂无子参数',
                        transform: function (data) {
                            if (data) {
                                $.each(data.list, function (idx, item) {
                                    item.propTypeName = propTypeTransform(item.propType);
                                });
                            }
                            return data;
                        }
                    },
                    function (data) {
                        data.propParentId = selectedNode;
                        data.query = $('#txt-query').val();
                        return kirin.ajax({
                            url: '/property/paging.json',
                            data: data
                        });
                    }
            );
        });

        // 新增按钮
        $('.btn-insert').on('click', function () {
            kirin.popup.open({
                title: '新增参数',
                width: 750,
                height: 440,
                maxmin: false,
                content: ['/property/edit.html?propParentId=', selectedNode].join(''),
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
            layer.confirm('删除此参数可能导致<br>运行异常，请谨慎操作？',
                    {
                        icon: 0,
                        title: '删除单位'
                    },
                    function () {
                        var propIds = [];

                        //获取所有选中的单位ID
                        $('.chk-row:checked').each(function () {
                            propIds.push(Number($(this).val()));
                        });

                        if (propIds.length == 0) {
                            layer.alert('请选择待删除的行。');
                            return false;
                        }

                        // 删除
                        kirin.ajax({
                            type: 'DELETE',
                            url: '/property/edit.json',
                            data: {
                                param: propIds
                            }
                        }).done(function () {
                            pageing.reload();
                            layer.msg('单位删除成功。', {icon: 1}, {time: 1500});
//                            layer.alert('单位删除成功。', {icon: 1});
                        });
                    }
            );
        });

        // 参数类型转换
        var propTypeTransform = function (value) {
            switch (value) {
                case 1:
                    return '文本';
                case 2:
                    return '文件';
            }
        };

        // 回车事件
        $('#txt-query').on('keydown', function (e) {
            if (e.keyCode === 13) {
                pageing.init();
            }
        });
        $('.btn-query').on('click', function () {
            pageing.init();
        });

        //全选/全不选
        $('#chk-all').on('click', function () {
            var checked = $(this).prop('checked');
            $('.chk-row').prop('checked', checked);
        });

        // 编辑按钮
        $('#tbl-dept').on('click', '.btn-edit', function () {
            var propId = $(this).data('id');
            kirin.popup.open({
                title: '编辑单位',
                width: 750,
                height: 440,
                maxmin: false,
                content: ['/property/edit.html?propId=', propId].join(''),
            }).done(function (data) {
                if (data === 'update') {
                    pageing.reload();
                } else {
                    pageing.init();
                }
            });
        });

        // 删除按钮
        $('#tbl-dept').on('click', '.btn-del', function () {
            $('.chk-row').prop('checked', false);
            var deptId = $(this).data('id');
            $('#chk-row-' + deptId).prop('checked', true);
            $('.btn-dels').trigger('click');
        });

    });
</script>
</@override>

<#-- 集成的Layout，必需在底部 -->
<@extends name="/common/layout/layout.ftl"/>

