<#--
 所属模块：系统模块
 页面名称：用户列表
 创建时间：2018/06/08
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
    <div class="col-sm-3 col-md-3 col-lg-3 tree-div">
        <div class="ibox ">
            <div class="ibox-title" style="padding: 6px;">
                <h5 style="margin-left: 10px; margin-top: 8px;">组织机构</h5>
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
                <h5 style="margin-left: 10px; margin-top: 8px;">用户列表</h5>
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
                        <input type="text" id="txt-query" placeholder="用户账户、姓名、联系方式。。。" class="form-control">
						<span class="input-group-btn">
                            <button type="button" class="btn btn-outline btn-success btn-query"
                                    style="margin-left: 0"><i class="fa fa-search"></i> 搜索</button>
                        </span>
                    </div>
                </div>
                <table id="tbl-user" class="table table-bordered table-striped table-hover">
                    <thead>
                    <tr>
                        <th style="width: 30px;">
                            <input type="checkbox" id="chk-all"/>
                            <label for="chk-all" class="check-box"></label>
                        </th>
                        <th class="u-table-center" style="width: 30px;">No.</th>
                        <th class="u-table-center">所属单位</th>
                        <th class="u-table-center">用户姓名</th>
                        <th class="u-table-center">联系方式</th>
                        <th class="u-table-center">身份证号</th>
                        <th class="u-table-center">用户权限</th>
                        <th class="u-table-center">允许登录方式</th>
                        <th class="u-table-center u-operation-4">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <div style="padding-top: 10px;" class="m-b-lg">
                    <div id="page-ƒinfo" class="pull-left"></div>
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
<script id="tpl-user-row" type="text/html">
    {{# for(var i = 0, len = d.length; i < len; i++){ }}
    <tr>
        <td class="u-table-center">
            <input type="checkbox" id="chk-row-{{d[i]['userId']}}" class="chk-row" value="{{ d[i]['userId'] }}"/>
            <label for="chk-row-{{d[i]['userId']}}" class="check-box"></label>
        </td>
        <td class="u-table-center">{{= i+1 }}</td>
        <td class="u-table-center">
            {{# if(d[i].dept != undefined){ }}
            {{=d[i].dept.deptName}}
            {{# } }}
        </td>
        <td class="u-table-center">{{= d[i]['userName'] }}</td>
        <td class="u-table-center">{{= d[i]['phone'] }}</td>
        <td class="u-table-center">{{= d[i]['idCardNum'] }}</td>
        <td class="u-table-center">{{= d[i]['authTypeName'] }}</td>
        <td class="u-table-center">{{= d[i]['loginWayName'] }}</td>
        <td class="u-table-center">
            <button type="button" data-id="{{= d[i]['userId'] }}" class="btn btn-sm btn-outline btn-primary btn-edit">
                编辑
            </button>
            <button type="button" data-id="{{= d[i]['userId'] }}" class="btn btn-sm btn-outline btn-danger btn-del">删除
            </button>
            <button type="button" data-id="{{= d[i]['userId'] }}" class="btn btn-sm btn-outline btn-success btn-reset">
                重置密码
            </button>
        </td>
    </tr>
    {{# } }}
</script>
<script type="text/javascript">
    $(function () {

        // 当前选择的节点
        var selectedNode, pageing;
        //selectedNode为当前选择的节点所拥有的数据，selectedNowNode为节点，用于查找父节点
        var selectedNowNode;

        // 树结构转化
        var builderTree = function (data, isFirst) {
            //太长了显示不好看，所以去掉杭州市公安局
            if (data.deptList) {
                data.deptList.forEach(function (item, idx) {
                    console.log(item);
                    item.deptName = item.deptName.replace("杭州市公安局", "");
                });
            }
            var node = {text: data.deptName, icon: 'fa fa-flag', data: data};
            if (isFirst) {
                node.state = {selected: true};
                selectedNode = data;
                selectedNowNode = node;
            }
            isFirst && (node.state = {opened: true});
            if (data.deptList && data.deptList.length > 0) {
                var children = node.children || [];
                $.each(data.deptList, function (i, dept) {
                    children.push(builderTree(dept, false))
                });
                node.children = children;
                if (children.length !== 0) {
                    delete node['icon'];
                }
            }
            return node;
        };


        //构建树结构
        kirin.ajax({
            url: '/system/dept-tree.json',
        }).done(function (data) {

            $('#tree-dept').jstree({
                'core': {
                    data: [builderTree(data, true)]
                }
            }).on('changed.jstree', function (e, data) {
                //设置当前选择的节点
                selectedNode = data.node.data;
                selectedNowNode = data;
                $('#txt-query').val('');
                pageing.init();
            });

            // 分页
            pageing = kirin.pageing(
                    {
                        view: '#tbl-user',
                        tpl: '#tpl-user-row',
                        noDataText: '此条件下暂无用户数据',
                        transform: function (data) {
                            if (data) {
                                $.each(data.list, function (idx, item) {
                                    item.loginWayName = loginWayTransform(item.loginWay);
                                    item.authTypeName = authTypeTransform(item.authType);
                                });
                            }
                            return data;
                        }
                    },
                    function (data) {
                        //如果隶属分局及以上，则查找branch下所有内容，而不是dept；反之，直接查找deptId
                        if (selectedNode.deptType & 1024 || selectedNode.deptType & 128 || selectedNode.deptType & 32) {
                            data.branchId = selectedNode.deptId;
                            //若以deptId为参数查找过一次结果，则此时deptId不为空，本次结果仍然和上次一致
                            //故手动修改查找分局时，deptId为空
                            data.deptId = "";
                        }
                        else {
                            data.deptId = selectedNode.deptId;
                            data.branchId = ""; //同上
                        }
                        data.query = $('#txt-query').val();
                        return kirin.ajax({
                            url: '/system/user-page.json',
                            data: data
                        });
                    }
            );

        });

        // 允许登录方式转换
        var loginWayTransform = function (value) {
            var loginWayTransName = new Array();
            if (value & 1) {
                loginWayTransName.push("用户账号登录");
            }
            if (value & 2) {
                loginWayTransName.push("数字证书登录");
            }
            if (value & 4) {
                loginWayTransName.push("APP登录");
            }
            loginWayTransName.join("、");
            return loginWayTransName;
        };


        var getRoleData = function () {
            var result = "";
            kirin.ajax({
                url: "/system/role-list-all.json",
                async: false
            }).done(function (data) {
                result = data;
            });
            return result;
        };
        var roleData = getRoleData();
        //用户权限转换
        var authTypeTransform = function (value) {
            var authTypeTransName = new Array();
            //获取所有角色名称及权限，进行判断
            for (var index in roleData) {
                if (value & roleData[index].authType) {
                    authTypeTransName.push(roleData[index].roleName);
                }
            }
            authTypeTransName.join("、");
            return authTypeTransName;
        };

        // 新增按钮
        $('.btn-insert').on('click', function () {
            var branchId = judgeNods(selectedNowNode);
            kirin.popup.open({
                title: '新增用户',
                width: 860,
                height: 450,
                maxmin: false,
                content: '/system/user.html?deptId=' + selectedNode.deptId + "&branchId=" + branchId
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


            var policeIds = [];

            //获取所有选中的用户ID
            $('.chk-row:checked').each(function () {
                policeIds.push(Number($(this).val()));
            });

            if (policeIds.length == 0) {
                layer.alert('请选择要删除的系统角色。');
                return false;
            }
            else {
                //询问框
                layer.confirm('是否删除此用户，请谨慎操作？',
                        {
                            icon: 0,
                            title: '删除用户'
                        },
                        function () {
                            kirin.ajax({
                                type: 'DELETE',
                                url: '/system/user.json',
                                data: {
                                    policeIds: policeIds
                                }
                            }).done(function (data) {
                                //判断当用户下信息员为空的时候，删除
                                if (data.length == 0) {
                                    layer.alert('用户删除成功。', {icon: 1});
                                    pageing.reload();
                                } else {
                                    //将后端返回的有信息员的用户姓名进行提示
                                    var userNames = [];
                                    for (var i = 0; i < data.length; i++) {
                                        userNames.push($('#chk-row-' + data[i] + '').parent().next().next().next().text());
                                    }
                                    layer.alert('您想要删除的用户有信息员，请先移交信息员或者删除信息员。有信息员的用户有：' + userNames + '', {icon: 0});
                                }
                            });
                        });
            }
        });

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
        $('#tbl-user').on('click', '.btn-edit', function () {
            var userId = $(this).data('id');
            var branchId = judgeNods(selectedNowNode);
            kirin.popup.open({
                title: '编辑用户',
                width: 860,
                height: 450,
                maxmin: false,
                content: '/system/user.html?userId=' + userId + '&branchId=' + branchId,
            }).done(function (data) {
                if (data === 'update') {
                    pageing.reload();
                } else {
                    pageing.init();
                }
            });
        });

        // 删除按钮
        $('#tbl-user').on('click', '.btn-del', function () {
            $('.chk-row').prop('checked', false);
            var deptId = $(this).data('id');
            $('#chk-row-' + deptId).prop('checked', true);
            $('.btn-dels').trigger('click');
        });

        //密码重置按钮
        $('#tbl-user').on('click', '.btn-reset', function () {
            var data = {};
            data.userId = $(this).data('id');
            layer.confirm('确定重置密码吗？', {icon: 3, title: '重置密码'}, function (index) {
                kirin.ajax({
                    url: '/system/reset-password.json',
                    type: 'POST',
                    data: data,
                    dataType: 'json'
                }).done(function () {
                    layer.msg("密码已重置", {time: 1500});
                });
            });
        });

        //判断节点
        function judgeNods(selectedNowNode) {
            //判断是否属于分局标志
            var flag = "";
            //如果未选中节点，则默认为当前树的顶级节点，无需寻找父节点，直接返回结果
            if (selectedNowNode.node != null) {
                flag = judgeIsbranch(selectedNowNode.node.data);
            }
            else {
                return selectedNowNode.data.deptId;
            }
            //第一次判断有返回结果，则直接返回；否则进入循环寻找父节点
            if (flag != "") {
                return flag;
            }
            else {
                var parents = selectedNowNode.node.parents;
                for (index in parents) {
                    flag = judgeIsbranch(selectedNowNode.instance.get_node(parents[index]).data);
                    if (flag != "") {
                        return flag;
                    }
                }
            }
        }

        //判断选中节点是否属于分局
        function judgeIsbranch(data) {
            //如果是分局，返回deptId做参数
            if (data.deptType & 1024 || data.deptType & 128 || data.deptType & 32) {
                return data.deptId;
            }
            else {
                return "";
            }
        }

    });
</script>
</@override>

<#-- 集成的Layout，必需在底部 -->
<@extends name="/common/layout/layout.ftl"/>

