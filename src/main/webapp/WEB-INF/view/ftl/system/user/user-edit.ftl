<#--
 所属模块：系统模块
 页面名称：系统用户编辑
 创建时间：2018/06/08
 创建人员：lt
 -->

<#-- 主体部分 -->
<@override name="main">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <table class="table table-bordered table-input">
            <tbody>
            <tr>
                <th class="required-input">用户帐户</th>
                <td>
                    <input type="hidden" class="form-control user-id" name="userId" value="${user.userId}">
                    <input type="hidden" class="dept-id" name="deptId" value="${deptId}">
                    <input type="text" name="account" id="account" class="form-control v-req v-account-double"
                           value="${user.account?html}" placeholder="请输入用户帐户">
                </td>
                <th class="required-input">用户姓名</th>
                <td>
                    <input type="text" name="userName" class="form-control v-req" value="${user.userName?html}"
                           placeholder="请输入用户姓名">
                </td>
            </tr>
            <tr>
                <th class="required-input">身份证号</th>
                <td>
                    <input type="text" name="idCardNum" id="idCardNum"
                           class="form-control v-req v-id-card-num v-id-card-num-double"
                           value="${user.idCardNum?html}" placeholder="请输入身份证号码">
                </td>
                <th class="required-input">手机号码</th>
                <td>
                    <input type="text" name="phone" id="phone" class="form-control v-req v-phone v-phone-double"
                           value="${user.phone?html}" placeholder="请输入手机号码">
                </td>
            </tr>
            <tr>
                <th class="required-input">所属分局</th>
                <td>
                    <input type="hidden" name="branchId" class="form-control v-req" value="${branchId}">
                    <input name="branchName" class="v-req" value="${branchName?html}" readonly>
                </td>
                <th class="required-input">所属单位</th>
                <td>
                    <#if deptName != null>
                        <select name="deptId" class="form-control v-req" <#if type = "view">disabled</#if>>
                            <@common_macro.deptSelect deptList deptId/>
                        </select>
                    <#else>
                        <select name="deptId" class="form-control v-req select-dept" <#if type = "view">disabled</#if>>
                            <@common_macro.deptSelect deptList deptId/>
                        </select>
                    </#if>
                </td>
            </tr>
            <tr>
                <th class="required-input">用户角色</th>
                <td colspan="3">
                    <div style="padding: 8px 10px;">
                        <#list roleList as role>
                            <#if role.authType = 1 && !flag>

                            <#else>
                                <label class="u-checkbox" style="width: 160px;" <#if type = "view">hidden</#if>>
                                    <input type="checkbox" name="authType" data-set="true"
                                           <#if utils.isAuthCheck(user.authType, role.authType)>checked</#if>
                                           class="form-control v-req"
                                           value="${role.authType?html}">
                                    <span class="check-box"></span>
                                    <span>${role.roleName?html}</span>
                                </label>
                            </#if>
                        </#list>
                    </div>
                </td>
            </tr>
            <tr>
                <th class="required-input">登录方式</th>
                <td colspan="3">
                    <div style="padding: 8px 10px;">
                        <label class="u-checkbox" style="width: 140px;" <#if type = "view">hidden</#if>>
                            <input type="checkbox" name="loginWay" data-set="true"
                                   <#if utils.isAuthCheck(user.loginWay,1)>checked</#if> class="form-control v-req"
                                   value="1">
                            <span class="check-box"></span>
                            <span>用户帐号登录</span>
                        </label>
                        <label class="u-checkbox" style="width: 140px;" <#if type = "view">hidden</#if>>
                            <input type="checkbox" name="loginWay" data-set="true"
                                   <#if utils.isAuthCheck(user.loginWay,2)>checked</#if>
                                   class="form-control v-req" value="2">
                            <span class="check-box"></span>
                            <span>数字证书登录</span>
                        </label>
                        <label class="u-checkbox" style="width: 140px;" <#if type = "view">hidden</#if>>
                            <input type="checkbox" name="loginWay" data-set="true"
                                   <#if utils.isAuthCheck(user.loginWay,4)>checked</#if>
                                   class="form-control v-req" value="4">
                            <span class="check-box"></span>
                            <span>App登录</span>
                        </label>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <input type="hidden" id="type" value="${type?html}">
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

        //回显单位值
        $('.select-dept').val('${deptId}');

        var verify = function (data) {
            var result = true;
            var userId = $('.user-id').val();
            if (userId) {
                data = $.extend({userId: userId}, data);
            }
            kirin.ajax({
                url: "/user/verify.json",
                async: false,
                data: data
            }).done(function (data) {
                result = data.exist;
            });
            return result;
        };

        //初始化验证
        var validate = $('.form-control').uloveValidate({
            rules: {
                //验证用户帐号是否重复
                'v-account-double': {
                    rule: function (element) {
                        return verify({account: $.trim(element.val())});
                    }, msg: '用户帐号已经存在。'
                },
                //验证手机号是否重复
                'v-phone-double': {
                    rule: function (element) {
                        return verify({phone: $.trim(element.val())});
                    }, msg: '手机号码已经存在。'
                },
                //验证身份证是否重复
                'v-id-card-num-double': {
                    rule: function (element) {
                        return verify({idCardNum: $.trim(element.val())});
                    }, msg: '身份证号码已经存在。'
                }
            }
        });


        //编辑页面时(无用户id)，身份证号和账号不可改
        if ($("input[name=userId]").val() == null || $("input[name=userId]").val() == "" || $("input[name=userId]").val() == undefined) {
        }
        else {
            $("input[name=account]").prop("readonly", "readonly").removeClass("v-account-double");
            $("input[name=idCardNum]").prop("readonly", "readonly").removeClass("v-id-card-num v-id-card-num-double");
        }

        var type = $("#type").val();
        if (type == "view") {
            $("input[name=authType]").prop("disabled", true);
            $("input[name=loginWay]").prop("disabled", true);
//        $("input:checked").parent().css('display','block');
            $("input:checked").parent().show();

        }

        // 保存按钮
        $('.btn-save').on('click', function () {

            //验证
            if (!validate.verify()) {
                return false;
            }

            //登录方式必填提示
            if (typeof ($("input[name='loginWay']:checked").val()) == "undefined") {
                layer.msg("登录方式为必填选项！");
                return false;
            }

            //用户权限必填提示
            if (typeof ($("input[name='authType']:checked").val()) == "undefined") {
                layer.msg("用户权限为必填项！");
                return false;
            }

            var userId = $('.user-id').val(),
                    isUpdate = (userId != '');


            kirin.ajax({
                url: "/system/user.json",
                type: "POST",
                serialize: '.form-control',
                debug: true
            }).done(function () {
                layer.msg('用户保存成功。', {icon: 1}, function () {
                    kirin.popup.success(isUpdate ? 'update' : 'insert');
                }, {time: 1500});
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
