<#--
 所属模块：系统模块
 页面名称：系统参数编辑
 创建时间：2018/06/11
 创建人员：lt
 -->

<#-- 主体部分 -->
<@override name="main">
<div class="row">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content" style="padding-top: 30px;padding-bottom: 35px;">
                <table class="table table-bordered table-input">
                    <tr>
                        <th class="required-input" style="width: 100px;">参数名称</th>
                        <td>
                            <input type="hidden" class="config-id" value="${config.configId}">
                            <input type="text"
                                   class="form-control config-key v-req"
                                   placeholder="请输入系统参数名称"
                                   value="${config.configKey?html}">
                        </td>
                    </tr>
                    <tr>
                        <th class="required-input" style="width: 100px;">参数值</th>
                        <td>
                            <input type="hidden" class="config-id" value="${config.configId}">
                            <input type="text"
                                   class="form-control config-value v-req"
                                   placeholder="请输入系统参数值"
                                   value="${config.configValue?html}">
                        </td>
                    </tr>
                    <tr>
                        <th class="required-input" style="width: 100px;">所属模块</th>
                        <td>
                            <input type="text"
                                   class="form-control config-module v-req"
                                   placeholder="请输入系统参所属模块"
                                   value="${config.configModule?html}">
                        </td>
                    </tr>
                    <#if branchSelectShow>
                        <tr>
                            <th class="required-input" style="width: 100px;">分局范围</th>
                            <td class="related-dept-judge">
                                <select id="branchList" class="form-control dept-parent-id v-req" multiple
                                        data-placeholder="请选择分局单位">
                                    <#list brachList as branch>
                                        <option value="${branch.deptId}">${branch.deptName}</option>
                                    </#list>
                                </select>
                            </td>
                        </tr>
                    </#if>
                    <tr>
                        <th class="required-input" style="width: 100px;">参数说明</th>
                        <td>
              <textarea rows="8"
                        class="form-control config-memo v-req"
                        placeholder="请输入系统参数说明">${config.configMemo?html}</textarea>
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

        //打开多选插件
        $("#branchList").chosen({
            width: "100%"
        });

        $(".chosen-container .chosen-results").css({"height": "100px"});


        //初始化设置值
        $("#branchList").val(${branchIdList});//根据value设置选中值，设置的时候如果多个 传数组
        $("#branchList").trigger('chosen:updated');//传递完成之后刷新

        // 保存按钮
        $('.btn-save').on('click', function () {

            //验证
            if (!validate.verify()) {
                return false;
            }

            var configId = $('.config-id').val(),
                    configKey = $.trim($('.config-key').val()),
                    configValue = $.trim($('.config-value').val()),
                    configModule = $.trim($('.config-module').val()),
                    configMemo = $.trim($('.config-memo').val()),
                    isUpdate = (configId != ''),
                    branchIds = $("#branchList").val();
            kirin.ajax({
                url: "/system/config.json",
                type: "POST",
                data: {
                    configId: configId,
                    configKey: configKey,
                    configValue: configValue,
                    configModule: configModule,
                    configMemo: configMemo,
                    deptIds: branchIds
                }
            }).done(function () {
                layer.alert('系统参数保存成功。', {icon: 1}, function () {
                    kirin.popup.success(isUpdate ? 'update' : 'insert');
                });
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
