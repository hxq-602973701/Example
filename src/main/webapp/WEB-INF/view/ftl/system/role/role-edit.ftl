<#--
 所属模块：系统模块
 页面名称：角色编辑
 创建时间：2018/06/21
 创建人员：lt
 -->

<#-- 主体部分 -->
<@override name="main">
<div class="row">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <table class="table table-bordered table-input">
                    <tr>
                        <th class="required-input" style="width: 100px;">角色名称</th>
                        <td>
                            <input type="hidden" class="role-id" value="${role.roleId}">
                            <input type="text"
                                   class="form-control role-name v-req"
                                   placeholder="请输入角色名称"
                                   value="${role.roleName?html}">
                        </td>
                    </tr>
                    <tr>
                        <th class="required-input" style="width: 100px;">角色说明</th>
                        <td>
                            <textarea rows="8"
                                      class="form-control role-memo v-req"
                                      placeholder="请输入角色说明">${role.roleMemo?html}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <th class="required-input" style="width: 100px;">序号</th>
                        <td>
                          <input type="text" class="form-control role-ranking v-req v-int" placeholder="请输入先后序号"
                                value="${role.ranking?html}">

                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="row u-footer-bar">
    <div class="col-sm-12">
        <input type="button" class="btn btn-outline btn-sm btn-success u-btn-form-bottom btn-save" value="保存">
        <input type="button" class="btn btn-outline btn-sm btn-danger  u-btn-form-bottom btn-cancel" value="取消">
    </div>
</div>
</@override>

<#-- 脚本部分 -->
<@override name="scripts">
<script type="text/javascript">
    $(function () {

        //初始化验证
        var validate = $('.form-control').uloveValidate();

        // 保存按钮
        $('.btn-save').on('click', function () {

            //验证
            if (!validate.verify()) {
                return false;
            }

            var roleId = $('.role-id').val(),
                    roleName = $.trim($('.role-name').val()),
                    roleMemo = $.trim($('.role-memo').val()),
                    ranking = $.trim($('.role-ranking').val()),
                    isUpdate = (roleId != '');

            kirin.ajax({
                url: "/system/role.json",
                type: "POST",
                data: {
                    roleId: roleId,
                    roleName: roleName,
                    roleMemo: roleMemo,
                    ranking : ranking
                }
            }).done(function () {
                layer.msg('系统角色保存成功。', {icon: 1}, function () {
                    kirin.popup.success(isUpdate ? 'update' : 'insert');
                }, {time: 1500});
//                layer.alert('系统角色保存成功。', {icon: 1}, function () {
//                    kirin.popup.success(isUpdate ? 'update' : 'insert');
//                });
            });

        });

        // 取消按钮
        $('.btn-cancel').on('click', function () {
            kirin.popup.close();
        });


    });
</script>
</@override>

<#-- 集成的Layout，必需在底部 -->
<@extends name="/common/layout/layout-popup.ftl"/>