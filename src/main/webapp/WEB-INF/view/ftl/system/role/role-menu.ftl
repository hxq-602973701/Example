<#--
 所属模块：系统模块
 页面名称：菜单编辑
 创建时间：2016/07/11
 创建人员：lt
 -->

<!-- 样式 -->
<@override name="css">
<!-- 树形视图 -->
<link href="/assets/css/plugins/jsTree/style.min.css" rel="stylesheet">
</@override>

<#-- 主体部分 -->
<@override name="main">
<div class="row">
    <div class="col-sm-3 col-md-3 col-lg-3">
        <div class="ibox ">
            <div class="ibox-content">
                <!-- 左侧单位树 -->
                <div id="tree-menu"></div>
                <input type="hidden" class="role-id" value="${role.roleId}">
                <input type="hidden" class="role-authType" value="${role.authType}">
            </div>
        </div>
    </div>
    <div class="row u-footer-bar">
        <div class="col-sm-12">
            <input type="button" class="btn btn-outline btn-sm btn-success u-btn-form-bottom btn-save" value="保存">
            <input type="button" class="btn btn-outline btn-sm btn-danger  u-btn-form-bottom btn-cancel" value="取消">
        </div>
    </div>
</div>
</@override>

<#-- 脚本部分 -->
<@override name="scripts">
<!-- 树形视图 -->
<script src="/assets/js/plugins/jsTree/jstree.min.js"></script>

<script type="text/javascript">
    $(function () {

        //角色权限
        var roleId = $('.role-id').val();
        var authType = $('.role-authType').val();
        //添加权限和删除权限组
        var addAuthType = {};
        var delAuthType = {};

        // 树结构转化
        var builderTree = function (data, isFirst) {
            var node = {text: data.menuName, icon: 'fa fa-flag', data: data};

            if (isFirst) {
                //node.state = {selected: true};
                console.log(JSON.stringify({
                    name: data.menuName,
                    id: data.menuId,
                    roles: data.menuRoles,
                    authType: authType
                }));
            }
            //判断拥有权限的菜单，并做相应标识
            if ((node.data.menuRoles & authType)
                    && (!data.menuList || data.menuList.length === 0)) {
                node.state = {selected: true};

            }


            isFirst && (node.state = {opened: true});
            if (data.menuList && data.menuList.length > 0) {
                var children = node.children || [];
                $.each(data.menuList, function (i, menu) {
                    children.push(builderTree(menu, false))
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
            url: '/system/menu-tree.json?show=' + 1,

        }).done(function (data) {
            $('#tree-menu').jstree({
                'core': {
                    data: (function () {
                        var rootNodeList = [];
                        $.each(data, function () {
                            rootNodeList.push(builderTree(this, true));
                        });
                        return rootNodeList;
                    })()
                },
                'plugins': ["themes", "json_data", "search", "checkbox"]
            }).on('changed.jstree', function (e, data) {

                if (data.action !== 'select_node' && data.action !== 'deselect_node') {
                    return;
                }

                var instance = data.instance;

                /**
                 * 根据权限判断是否追加到修改列表
                 * @param node
                 */
                var calcItem = function (node, isParent) {

                    var roles = node.data.menuRoles,
                            pageId = node.data.pageId,
                            state = node.state.selected;

                    if (isParent) {

                        var isChildCheck = function (children) {
                            var checked = false;
                            $.each(children, function (idx, nodeId) {
                                var node = instance.get_node(nodeId);
                                var subChecked = node.state.selected;

                                if (subChecked) {
                                    checked = subChecked;
                                    return false;
                                } else if (node.children) {
                                    subChecked = isChildCheck(node.children);
                                    if (subChecked) {
                                        checked = subChecked;
                                        return false;
                                    }
                                }
                            });
                            return checked;
                        };

                        if (node.children) {
                            state = isChildCheck(node.children)
                        }
                    }

                    if (roles & authType > 0) {
                        // 有权限的情况下
                        if (state) {
                            delete delAuthType[pageId];
                            addAuthType[pageId] = node.data.menuName;
                        } else {
                            delete addAuthType[pageId];
                            delAuthType[pageId] = node.data.menuName;
                        }
                    } else {
                        // 无权限的情况下
                        if (state) {
                            addAuthType[pageId] = node.data.menuName;
                            delete delAuthType[pageId];
                        } else {
                            delete addAuthType[pageId];
                            delAuthType[pageId] = node.data.menuName;
                        }
                    }
                };

                //获取当前点击节点的所有父节点
                var parents = data.node.parents;
                $.each(parents, function (idx, nodeId) {
                    if (nodeId === '#') {
                        return;
                    }
                    var node = instance.get_node(nodeId);
                    calcItem(node, true);
                });

                //加如自身
                calcItem(data.node);

                //递归获取所有的子节点
                var child = function (nodeList) {
                    $.each(nodeList, function (idx, nodeId) {
                        var node = instance.get_node(nodeId);
                        calcItem(node);
                        if (node && node.children) {
                            child(node.children);
                        }
                    });
                };

                if (data.node.children) {
                    child(data.node.children);
                }

            }).on('loaded.jstree', function (e, data) {

            });
        });

        function addArray(nodeData, arry) {
            //递归将pageId添加进数组中
            if (nodeData.menuList != "" || null != nodeData.menuList) {
                for (index in nodeData.menuList) {
                    arry.push(nodeData.menuList[index].pageId);
                    addArray(nodeData.menuList[index], arry);
                }
            }
        }

        function openId() {
            //获取当前结点
            //var nodes=$("#tree-menu").jstree("get_checked");
            var nodes = $("#tree-menu").jstree("get_all_checked")
            //var x = $("#tree-menu").jstree("check_node","j1_3");
        }

        // 保存按钮
        $('.btn-save').on('click', function () {

            var addMenu = [];
            $.each(addAuthType, function (key) {
                addMenu.push(key);
            });

            var delMenu = [];
            $.each(delAuthType, function (key) {
                delMenu.push(key);
            });

            //如果新增或删除列表有一项为空，则传递标志
            if (delMenu.length == 0) {
                delMenu = "undefined";
            }
            if (addMenu.length == 0) {
                addMenu = "undefined";
            }
            kirin.ajax({
                url: "/system/menu/update.json",
                type: "POST",
                data: {
                    authType: authType,
                    addMenu: addMenu.toString(),
                    delMenu: delMenu.toString()
                }
            }).done(function () {
                layer.msg('角色菜单保存成功。', {icon: 1}, function () {
                    kirin.popup.success("update");
                }, {time: 1500});
//               layer.alert('角色菜单保存成功。', {icon: 1}, function () {
//                    kirin.popup.success("update");
//                });
            });
        });

        // 取消按钮
        $('.btn-cancel').on('click', function () {
            kirin.popup.close();
        });

        //数组去重复
        function unique(array) {
            var r = [];
            for (var i = 0, l = array.length; i < l; i++) {
                for (var j = i + 1; j < l; j++)
                    if (array[i] === array[j]) j = ++i;
                r.push(array[i]);
            }
            return r;
        }
    });
</script>
</@override>

<#-- 集成的Layout，必需在底部 -->
<@extends name="/common/layout/layout-popup.ftl"/>