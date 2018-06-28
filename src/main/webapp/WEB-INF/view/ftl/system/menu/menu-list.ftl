<#--
 所属模块：系统模块
 页面名称：系统菜单列表
 创建时间：2018/06/01
 创建人员：lt
 -->

<#-- 主体部分 -->
<@override name="main">

<div class="row">
    <div class="col-sm-6 col-md-5">
        <div class="ibox ">
            <div class="ibox-title" style="padding: 6px;">
                <h5 style="margin-left: 10px; margin-top: 8px;">系统菜单设置</h5>

                <div class="pull-right">
                    <button type="button" class="btn btn-sm btn-outline btn-success btn-insert">
                        <i class="glyphicon glyphicon-plus"></i>
                        添加菜单
                    </button>

                    <button type="button" class="btn btn-sm btn-outline btn-primary btn-expand">
                        <i class="fa fa-plus-square"></i>
                        展开所有
                    </button>
                    <button type="button" class="btn btn-sm btn-outline btn-warning btn-collapse">
                        <i class="fa fa-minus-square"></i>
                        收起所有
                    </button>
                </div>
            </div>
            <div class="ibox-content">
                <div class="dd" id="menu-tree"></div>
                <button type="button" class="btn btn-primary btn-outline btn-block btn-save-menu-list"
                        style="margin-top: 20px;">
                    保存菜单顺序
                </button>
                <div class="m-t-md">
                    <h5>提示：</h5>
                </div>
                <div class="alert alert-success">
                    <ul class="margin-bottom-none padding-left-lg">
                        <li>菜单可以拖动位置，以调整位置与顺序。</li>
                        <li>菜单最多支持四层。</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 col-md-7">
        <div class="ibox ">
            <div class="ibox-title" style="padding: 6px;">
                <h5 style="margin-left: 10px; margin-top: 8px;" id="menu-area">系统菜单编辑</h5>
            </div>
            <div class="ibox-content">
                <form class="form-horizontal menu-info">
                    <input id="menu-id" type="hidden">
                    <input id="menu-parent-id" type="hidden">
                    <input id="ranking" type="hidden">
                    <table class="table table-bordered table-input">
                        <tr>
                            <th class="required-input">菜单名称</th>
                            <td><input id="menu-name" type="text" class="form-control v-req" maxlength="15"
                                       placeholder="请输入菜单名称"></td>
                        </tr>
                        <tr>
                            <th>提示信息</th>
                            <td><input id="menu-title" type="text" class="form-control" maxlength="50"
                                       placeholder="请输入菜单提示信息"></td>
                        </tr>
                        <tr>
                            <th class="required-input">菜单编号</th>
                            <td><input id="page-id" type="text" class="form-control v-req" maxlength="20"
                                       placeholder="请输入菜单编号"></td>
                        </tr>
                        <tr>
                            <th>菜单图标</th>
                            <td><input id="menu-icon" type="text" class="form-control" maxlength="50"
                                       placeholder="请选择菜单图标"></td>
                        </tr>
                        <tr>
                            <th>菜单图片</th>
                            <td><input id="menu-image" type="text" class="form-control" maxlength="256"
                                       placeholder="请输入菜单图片地址"></td>
                        </tr>
                        <tr>
                            <th class="required-input">菜单地址</th>
                            <td><input id="menu-url" type="text" class="form-control v-req" maxlength="256"
                                       placeholder="请输入菜单地址"></td>
                        </tr>
                        <tr>
                            <th class="required-input">智能跳转</th>
                            <td>
                                <div class="u-form-checkbox-control">
                                    <div class="radio radio-info radio-inline" title="自动获取本子系统中的第一个有权限且非隐藏的菜单URL">
                                        <input type="radio" id="auto-smart-link" class="smart-link" name="smart-link"
                                               value="1" checked>
                                        <label for="auto-smart-link">智能获取</label>
                                    </div>

                                    <div class="radio radio-info radio-inline" title="使用【菜单地址】中指定的URL">
                                        <input type="radio" id="setting-smart-link" class="smart-link" name="smart-link"
                                               value="0">
                                        <label for="setting-smart-link">手动指定</label>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th class="required-input">显示隐藏</th>
                            <td>
                                <div class="u-form-checkbox-control">
                                    <div class="radio radio-info radio-inline" title="在左侧导航栏中显示本菜单">
                                        <input type="radio" id="show-in-menu" class="menu-show" name="menu-show"
                                               value="1" checked>
                                        <label for="show-in-menu">显示菜单</label>
                                    </div>

                                    <div class="radio radio-info radio-inline" title="在左侧导航栏中隐藏本菜单">
                                        <input type="radio" id="hide-in-menu" class="menu-show" name="menu-show"
                                               value="0">
                                        <label for="hide-in-menu">隐藏菜单</label>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th class="required-input">菜单权限</th>
                            <td>
                                <select id="menu-roles" class="form-control v-req" multiple="multiple"
                                        data-placeholder="请添加菜单权限">
                                    <option value=""></option>
                                    <#list roleList as role>
                                        <option value="${role.authType}">${role.roleName}</option>
                                    </#list>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th class="required-input">菜单说明</th>
                            <td><textarea id="menu-memo" rows="5" class="form-control v-req"
                                          placeholder="请输入菜单说明"></textarea></td>
                        </tr>
                    </table>
                    <div class="form-group" style="margin-top: 20px;">
                        <div class="col-sm-6 col-sm-offset-3">
                            <button type="button" class="btn btn-outline btn-primary btn-block btn-save-menu-info">
                                保存菜单信息
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</@override>

<#-- 脚本部分 -->
<@override name="scripts">
<script type="text/javascript">
    $(function () {

        //初始化选择控件
        $('#menu-roles').chosen({no_results_text: '您的选项不存在'});

        //初始化验证
        var validate = $('.menu-info input, .menu-info select,.menu-info #menu-memo').uloveValidate();

        /**
         * 生成菜单节点HTML
         * @param menuTree 菜单树
         */
        window.generateTreeNode = function (data) {
            return laytpl($('#tpl-menu-tree').html()).render(data);
        };

        /**
         * 构建树形菜单，并初始化
         * @param menuTree 菜单树
         */
        var buildMenuTree = function (menuTree) {

            //生成节点
            $('#menu-tree').html(generateTreeNode(menuTree));

            //初始化菜单列表
            $('#menu-tree').nestable({
                group: 1,
                maxDepth: 4
            });

            //全部收缩
            $('.btn-collapse').click();
        };

        /**
         * 绑定菜单菜单构建数据
         */
        var bindMenuTreeData = function () {
            $.ajax({
                type: 'GET',
                url: '/system/menu-tree.json',
                dataType: 'json',
                success: function (data) {
                    buildMenuTree(data.response);
                }
            });
        };

        //绑定
        bindMenuTreeData();

        //添加菜单
        $('.btn-insert').on('click', function () {

            $('#menu-area').text('新增系统菜单');

            validate.clear();

            $('#menu-id').val('');
            $('#menu-parent-id').val();
            $('#ranking').val();

            $('#menu-name').val('');
            $('#page-id').val('');
            $('#menu-title').val('');
            $('#menu-icon').val('');
            $('#menu-image').val('');
            $('#menu-url').val('');
            $('#auto-smart-link').prop('checked', true);
            $('#setting-smart-link').prop('checked', false);
            $('#show-in-menu').prop('checked', true);
            $('#hide-in-menu').prop('checked', false);
            $('#menu-roles option:selected').prop('selected', false);
            $('#menu-roles').trigger("chosen:updated");
            $('#menu-memo').val('');

        });

        //展开所有
        $('.btn-expand').on('click', function () {
            $('.dd').nestable('expandAll');
            $('.btn-expand').hide();
            $('.btn-collapse').show();
        });

        //收起所有
        $('.btn-collapse').on('click', function () {
            $('.dd').nestable('collapseAll');
            $('.btn-expand').show();
            $('.btn-collapse').hide();
        });

        //编辑
        $('.dd').on('click', '.btn-edit', function () {

            layer.load('菜单详情加载中。。。');

            $('#menu-area').text('编辑系统菜单');

            $('.dd .dd-handle').removeClass('active');
            $(this).parent('span').parent('.dd-item').find(' > .dd-handle').addClass('active');

            var menuId = $(this).data('id');

            //设置select多选
            var initAuthType = function (select, authType) {
                $(select).find('option').each(function () {
                    var val = $(this).val();
                    var isAuth = ((authType & Number(val)) != 0);
                    if (isAuth) {
                        $(this).prop('selected', true);
                    } else {
                        $(this).prop('selected', false);
                    }
                });
                $(select).trigger("chosen:updated");
            };

            $.ajax({
                type: 'GET',
                url: ['/system/menu/', menuId, '.json'].join(''),
                dataType: 'json',
                success: function (data) {

                    layer.closeAll();

                    var detail = data.response;

                    validate.clear();

                    $('#menu-id').val(detail.menuId);
                    $('#menu-parent-id').val(detail.menuParentId);
                    $('#ranking').val(detail.ranking);

                    $('#menu-name').val(detail.menuName);
                    $('#page-id').val(detail.pageId);
                    $('#menu-icon').val(detail.menuIcon);
                    $('#menu-image').val(detail.menuImage);
                    $('#menu-url').val(detail.menuUrl);
                    if (detail.smartLink == true) {
                        $('#auto-smart-link').prop('checked', true);
                        $('#setting-smart-link').prop('checked', false);
                    } else {
                        $('#auto-smart-link').prop('checked', false);
                        $('#setting-smart-link').prop('checked', true);
                    }
                    if (detail.menuShow == true) {
                        $('#show-in-menu').prop('checked', true);
                        $('#hide-in-menu').prop('checked', false);
                    } else {
                        $('#show-in-menu').prop('checked', false);
                        $('#hide-in-menu').prop('checked', true);
                    }
                    initAuthType('#menu-roles', detail.menuRoles);
                    $('#menu-memo').val(detail.menuMemo);
                }
            });
        });

        //删除
        $('.dd').on('click', '.btn-delete', function () {

            var menuId = $(this).data('id');
            var menuName = $(this).data('name');

            //解析菜单结构
            var tree = $('#menu-tree').nestable('serialize');
            var subTree = function (tree, pId) {
                var subItem = null;
                $.each(tree, function (idx, item) {
                    var menuId = item.id, sub = item.children;
                    if (menuId == pId) {
                        subItem = item;
                        return false;
                    } else if (sub && sub.length > 0) {
                        subItem = subTree(sub, pId);
                        if (subItem) {
                            return false;
                        }
                    }
                });
                return subItem;
            };
            var delMenuList = function (tree) {
                var lists = [];
                $.each(tree, function (idx, item) {
                    var menuId = item.id, sub = item.children;
                    lists.push(menuId);
                    if (sub && sub.length > 0) {
                        lists = lists.concat(delMenuList(sub));
                    }
                });
                return lists;
            };
            //待删除菜单列表
            var menuIds = delMenuList([subTree(tree, menuId)]);

            var msg = (menuIds.length > 1 ? "所选菜单【" + menuName + "】包含有子菜单，将一并删除！" : "是否删除所选菜单【" + menuName + "】？");

            layer.confirm(msg, {
                btn: ['是', '否'], //按钮
                shade: false //不显示遮罩
            }, function () {
                $.ajax({
                    type: 'POST',
                    url: '/system/menu.json',
                    traditional: true,
                    dataType: 'json',
                    cache: false,
                    data: {
                        DELETE: true, menuIds: menuIds
                    },
                    success: function () {
                        bindMenuTreeData();
                        layer.msg('菜单信息删除成功。', {icon: 1}, {time: 1500});
                    }
                });
            });
        });

        //保存菜单顺序与结构
        $('.btn-save-menu-list').on('click', function () {

            //解析菜单结构
            var tree = $('#menu-tree').nestable('serialize');
            var saveMenuList = function (tree, pId) {
                var lists = [];
                $.each(tree, function (idx, item) {
                    var menuId = item.id, sub = item.children;
                    lists.push({menuId: menuId, ranking: idx, menuParentId: pId});
                    if (sub && sub.length > 0) {
                        lists = lists.concat(saveMenuList(sub, item.id));
                    }
                });
                return lists;
            };

            layer.msg('保存中...', {icon: 16});

            kirin.ajax({
                url: "/system/menu-tree.json",
                type: "PUT",
                data: {
                    menuListJson: JSON.stringify(saveMenuList(tree, 0))
                },
            }).done(function () {
                layer.msg('菜单顺序与结构保存成功。', {icon: 1, time: 1500}, function () {
                    location.reload();
                });
            });

        });

        //保存菜单信息
        $('.btn-save-menu-info').on('click', function () {

            if (!validate.verify()) {
                return false;
            }

            var sumAuthType = function (select) {
                var authType = 0;
                $(select).find('option:selected').each(function () {
                    var val = $(this).val();
                    authType = authType + Number(val);
                });
                return authType;
            };

            var data = {};
            data.menuId = $('#menu-id').val();
            data.menuParentId = $('#menu-parent-id').val() || 0;
            data.ranking = $('#ranking').val();
            data.menuName = $('#menu-name').val();
            data.pageId = $.trim($('#page-id').val()).toUpperCase();
            data.menuIcon = $('#menu-icon').val();
            data.menuImage = $('#menu-image').val();
            data.menuUrl = $('#menu-url').val();
            data.smartLink = $('.smart-link:checked').val();
            data.menuShow = $('.menu-show:checked').val();
            data.menuRoles = sumAuthType('#menu-roles');
            data.menuMemo = $('#menu-memo').val();

            kirin.ajax({
                type: 'POST',
                url: '/system/menu.json',
                dataType: 'json',
                data: data
            }).done(function (data) {
                bindMenuTreeData();
                $('#menu-area').text('新增系统菜单');
                validate.clear();
                $('#menu-id').val('');
                $('#menu-parent-id').val();
                $('#ranking').val();
                $('#menu-name').val('');
                $('#page-id').val('');
                $('#menu-title').val('');
                $('#menu-icon').val('');
                $('#menu-image').val('');
                $('#menu-url').val('');
                $('#auto-smart-link').prop('checked', true);
                $('#setting-smart-link').prop('checked', false);
                $('#show-in-menu').prop('checked', true);
                $('#hide-in-menu').prop('checked', false);
                $('#menu-roles option:selected').prop('selected', false);
                $('#menu-roles').trigger("chosen:updated");
                $('#menu-memo').val('');
                layer.msg('菜单信息保存成功。', {icon: 1, time: 1500}, function () {
                    location.reload();
                });
            });

            return false;
        });
    });
</script>
<script id="tpl-menu-tree" type="text/html">
    <ol class="dd-list">
        {{# for(var idx = 0, len = d.length; idx < len; idx++){ }}
        {{# var m=d[idx]; }}
        <li class="dd-item" data-id="{{=m['menuId']}}">
            <span class="pull-right">
                <button type="button" class="btn btn-outline btn-primary btn-sm btn-edit"
                        data-id="{{=m['menuId']}}">编辑</button>
                <button type="button" class="btn btn-outline btn-danger btn-sm btn-delete" data-id="{{=m['menuId']}}"
                        data-name="{{=m['menuName']}}">删除</button>
            </span>

            <div class="dd-handle">
                <i class="{{=m['menuIcon']}}" style="font-size: 16px;"></i> {{=m['menuName']}}
            </div>
            {{# var subList=m['menuList']; }}
            {{# if(subList && subList.length > 0){ }}
            {{ generateTreeNode(subList) }}
            {{# } }}
        </li>
        {{# } }}
    </ol>
</script>
</@override>

<#-- 集成的Layout，必需在底部 -->
<@extends name="/common/layout/layout.ftl"/>
