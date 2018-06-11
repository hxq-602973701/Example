<#--
 所属模块：系统维护
 页面名称：业务参数编辑页面
 创建时间：2018/06/11
 创建人员：lt
 -->
<@override name="main">
<div class="row">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <table class="table table-bordered table-input">
                    <tbody>
                    <tr>
                        <th class="required-input">参数名称</th>
                        <td>
                            <input type="text" class="form-control v-req key" value="${prop.propKey?html}">
                        </td>
                    </tr>
                    <tr>
                        <th class="required-input">父级</th>
                        <td>
                            <select class="form-control parent v-req">
                                <option value="0">无</option>
                                <#list parentList as prop>
                                    <option value="${prop.propId?html}">${prop.propKey?html}</option>
                                </#list>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th class="required-input">类型</th>
                        <td>
                            <select class="form-control type">
                                <option value="1">文本</option>
                                <option value="2">文件</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th class="required-input">值</th>
                        <td>
                            <input type="text" class="form-control value v-req" value="${prop.propValue?html}">
                        </td>
                    </tr>
                    <tr>
                        <th>字体图标</th>
                        <td>
                            <input type="text" class="form-control icon" value="${prop.propIcon?html}">
                        </td>
                    </tr>
                    <tr>
                        <th>参数字体图标</th>
                        <td>
                            <input type="text" class="form-control font" value="${prop.propIconfont?html}">
                        </td>
                    </tr>
                    <tr>
                        <th>序号</th>
                        <td>
                            <input type="text" class="form-control ranking" value="${prop.ranking?html}">
                        </td>
                    </tr>
                    <tr>
                        <th>说明</th>
                        <td>
                            <textarea class="form-control memo">${prop.propMemo?html}</textarea>
                        </td>
                    </tr>
                    </tbody>
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
    $(document).ready(function () {

        //初始化验证
        var validate = $('.form-control').uloveValidate();
        $('.bootstrap-tagsinput').css({'border': '1px solid #ccc', 'height': '100%'}).addClass("form-control");

        //当前新增的节点
        $('.parent').val('${parent}');

        //保存操作
        $(".btn-save").on('click', function () {
            //验证
            if (!validate.verify()) {
                return false;
            }
            var propId = '${prop.propId}';
            var propKey = $('.key').val();
            var propParentId = $('.parent').val();
            var propType = $('.type').val();
            var propValue = $('.value').val();
            var propIcon = $('.icon').val();
            var propIconfont = $('.font').val();
            var ranking = $('.ranking').val();
            var propMemo = $('.memo').val();

            if (isNaN(ranking)) {
                layer.msg("序号只能输入数字!")
                return;
            }

            var isUpdate = (propId != '');
            kirin.ajax({
                type: 'POST',
                url: '/property/edit.json',
                data: {
                    propId: propId,
                    propKey: propKey,
                    propParentId: propParentId,
                    propType: propType,
                    propValue: propValue,
                    propIcon: propIcon,
                    propIconfont: propIconfont,
                    ranking: ranking,
                    propMemo: propMemo
                }
            }).done(function () {
                layer.msg('保存成功。', {icon: 1}, function () {
                    kirin.popup.success(isUpdate ? 'update' : 'insert');
                }, {time: 1500});
//                layer.alert('保存成功。', {icon: 1}, function () {
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