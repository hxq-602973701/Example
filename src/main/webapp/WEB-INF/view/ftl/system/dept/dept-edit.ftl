<#--
 所属模块：系统模块
 页面名称：系统参数编辑
 创建时间：2018/06/05
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
                        <th class="required-input" style="width: 100px;">父级单位</th>
                        <td>
                            <select class="form-control dept-parent-id v-req">
                                <option value="">--请选择--</option>
                                <#list deptList as item>
                                    <option <#if item.deptId == deptParentId>selected</#if> value="${item.deptId}">${item.deptName?html}</option>
                                </#list>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th class="required-input" style="width: 100px;">单位编号</th>
                        <td>
                            <input type="text"
                                   class="form-control dept-code v-req"
                                   placeholder="请输入单位编号"
                                   value="${dept.deptCode?html}">
                        </td>
                    </tr>
                    <tr>
                        <th class="required-input" style="width: 100px;">单位名称</th>
                        <td>
                            <input type="hidden" class="dept-id" value="${dept.deptId}">
                            <input type="text"
                                   class="form-control dept-name v-req"
                                   placeholder="请输入单位名称"
                                   value="${dept.deptName?html}">
                        </td>
                    </tr>
                    <tr>
                        <th class="required-input" style="width: 100px;">单位简称</th>
                        <td>
                            <input type="hidden" class="dept-id" value="${dept.deptId}">
                            <input type="text"
                                   class="form-control dept-short-name v-req"
                                   placeholder="请输入单位简称"
                                   value="${dept.deptShortName?html}">
                        </td>
                    </tr>
                    <tr>
                        <th class="required-input" style="width: 100px;">单位类型</th>
                        <td>
                            <select class="form-control dept-type v-req">
                                <option value="">--请选择--</option>
                                <#list property as prop>
                                    <#if isCity == true && prop.propKey?number <= 1024>
                                      <option <#if dept.deptType == prop.propKey>selected</#if> value="${prop.propKey}">${prop.propValue}</option>
                                    <#elseif 2048 <= prop.propKey?number>
                                      <option <#if dept.deptType == prop.propKey>selected</#if> value="${prop.propKey}">${prop.propValue}</option>
                                    </#if>
                                </#list>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th style="width: 100px;">区域坐标</th>
                        <td>
                            <textarea rows="6"
                                      class="form-control dept-area"
                                      placeholder="请输入单位管辖区域经纬度坐标点">${dept.deptArea?html}</textarea>
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

            var deptId = $('.dept-id').val(),
                    deptParentId = $.trim($('.dept-parent-id').val()),
                    deptCode = $.trim($('.dept-code').val()),
                    deptName = $.trim($('.dept-name').val()),
                    deptShortName = $.trim($('.dept-short-name').val()),
                    deptType = $.trim($('.dept-type').val()),
                    deptArea = $.trim($('.dept-area').val()),
                    isUpdate = (deptId != '');

            kirin.ajax({
                url: "/system/dept.json",
                type: "POST",
                data: {
                    deptId: deptId,
                    deptParentId: deptParentId,
                    deptCode:deptCode,
                    deptName: deptName,
                    deptShortName: deptShortName,
                    deptType:deptType,
                    deptArea:deptArea
                }
            }).done(function () {
                layer.msg('单位保存成功。', {icon: 1,time:1500}, function () {
                    kirin.popup.success(isUpdate ? 'update' : 'insert');
                });
//                layer.alert('单位保存成功。', {icon: 1}, function () {
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
