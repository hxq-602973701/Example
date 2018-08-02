<#--
 所属模块：系统模块
 页面名称：任务编辑
 创建时间：2018/08/2
 创建人员：lt
 -->

<#-- 主体部分 -->
<@override name="main">
<div class="row">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <form id="addForm">
                    <table class="table table-bordered table-input">
                        <tr>
                            <th class="required-input" style="width: 100px;">任务名称</th>
                            <td>
                                <input type="hidden" class="job-id" value="${scheduleJob.jobId}" name="jobId">
                                <input type="hidden" class="job-status" value="0" name="jobStatus">
                                <input type="text"
                                       class="form-control job-name v-req" name="jobName"
                                       placeholder="请输入任务名称"
                                       value="${scheduleJob.jobName?html}"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="required-input" style="width: 100px;">任务组名</th>
                            <td>
                                <input type="text"
                                       class="form-control job-group v-req" name="jobGroup"
                                       placeholder="请输入任务组名" value="${scheduleJob.jobGroup?html}"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="required-input" style="width: 100px;">cron表达式</th>
                            <td>
                                <input type="text"
                                       class="form-control cron-expression v-req" name="cronExpression"
                                       placeholder="请输入cron表达式" value="${scheduleJob.cronExpression?html}"/>
                            </td>
                        </tr>
                        <tr>
                            <th style="width: 100px;">描述</th>
                            <td>
                                <input type="text"
                                       class="form-control description" name="description"
                                       placeholder="请输入描述" value="${scheduleJob.description?html}"/>
                            </td>
                        </tr>

                        <tr>
                            <th style="width: 100px;">类路径</th>
                            <td>
                                <input type="text"
                                       class="form-control bean-class" name="beanClass"
                                       placeholder="请输入类路径" value="${scheduleJob.beanClass?html}"/>
                            </td>
                        </tr>
                        <tr>
                            <th style="width: 100px;">Spring id</th>
                            <td>
                                <input type="text"
                                       class="form-control spring-id" name="springId"
                                       placeholder="请输入spring id" value="${scheduleJob.springId?html}"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="required-input" style="width: 100px;">方法名</th>
                            <td>
                                <input type="text"
                                       class="form-control method-name v-req" name="methodName"
                                       placeholder="请输入方法名" value="${scheduleJob.methodName?html}"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="required-input" style="width: 100px;">是否同步</th>
                            <td>
                                <select name="isConcurrent" id="isConcurrent" class="form-control">
                                    <option value="1">同步</option>
                                    <option value="0">不同步</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                </form>
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

            if ($.trim($('.bean-class').val()) == '' && $.trim($('.spring-id').val()) == '') {
                $('.bean-class').focus();
                layer.msg('类路径和spring id至少填写一个',{icon: 5});
                return false;
            }

            var jobId = $('.job-id').val();

            var isUpdate = (jobId != '');

            var data = $("#addForm").serialize();

            $.ajax({
                type: "POST",
                async: false,
                dataType: "JSON",
                cache: false,
                url: "/task/add.json",
                data: data,//对表单进行序列化
                success: function (data) {
                    if (data.meta.code==200) {
                        layer.alert('任务保存成功。', {icon: 1}, function () {
                            kirin.popup.success(isUpdate ? 'update' : 'insert');
                        });
                    } else {
                        layer.msg(data.meta.message,{icon: 5});
                    }
                }//end-callback
            });//end-ajax


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