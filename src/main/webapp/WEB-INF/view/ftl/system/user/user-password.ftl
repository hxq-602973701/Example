<@override name="main">
<div class="row">
    <div class="col-sm-12">
        <div class="ibox">
            <div class="ibox-content">
                <table class="table" style="width: 860px;margin-top: 40px;margin-bottom: 83px;">
                    <tr style="height: 51px;">
                        <td style="width: 200px;text-align: right;">
                            <p style="font-size: 14px;font-weight: bold;">*&nbsp;旧密码：</p>
                        </td>
                        <td style="width: 300px;">
                            <input type="text" class="form-control v-req" id="oldPwd">
                        </td>
                        <td style="width: 360px;color: blue;">请输入原密码</td>
                    </tr>
                    <tr style="height: 51px;">
                        <td style="width: 200px;text-align: right;">
                            <p style="font-size: 14px;font-weight: bold;">*&nbsp;新密码：</p>
                        </td>
                        <td style="width: 300px;">
                            <input type="text" class="form-control v-req" id="newPwd">
                        </td>
                        <td style="width: 360px;color: blue;">须包含字母、数字、下划线中不少于两种且长度不少于6</td>
                    </tr>
                    <tr style="height: 51px;">
                        <td style="width: 200px;text-align: right;">
                            <p style="font-size: 14px;font-weight: bold;">*&nbsp;安全级别：</p>
                        </td>
                        <td colspan="2">
                            <p style="position:absolute;">
                            <div style="height: 8px;width: 50px;background-color: grey;position:relative;float:left;"
                                 id="num1"></div>
                            <div style="height: 8px;width: 50px;background-color: grey;position:relative;margin-left:10px;float:left;"
                                 id="num2"></div>
                            <div style="height: 8px;width: 50px;background-color: grey;position:relative;margin-left:10px;float:left;margin-right: 20px;"
                                 id="num3"></div>
                            <b class="safe1">很危险</b>
                            <b class="safe2">危险</b>
                            <b class="safe3">安全</b>
                            </p>
                        </td>
                    </tr>
                    <tr style="height: 51px;">
                        <td style="width: 200px;text-align: right;">
                            <p style="font-size: 14px;font-weight: bold;">*&nbsp;新密码确认：</p>
                        </td>
                        <td style="width: 300px;">
                            <input type="text" class="form-control v-req" id="conPwd">
                        </td>
                        <td style="width: 360px;color: blue;">请再次输入新密码</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <div class="u-footer-bar" style="text-align: center;width: 860px;">
        <input type="button" class="btn btn-success btn-sm btn-lg btn-outline u-btn-form-bottom" id="save" value="保存">
        <input type="button" class="btn btn-danger btn-sm btn-lg btn-outline u-btn-form-bottom" id="cancle" value="关闭">
    </div>
</div>
</@override>

<@override name="scripts">
<script type="text/javascript">
    $(function () {

        //使用严格模式
        'use strict';

        //必填验证
        var validata = $("td input").uloveValidate();

        $(".safe1").hide();
        $(".safe2").hide();
        $(".safe3").hide();

        //旧密码
        var oldPwd = "";
        //新密码
        var newPwd = "";
        //确认密码
        var conPwd = "";

        //密码的正则表达式
        var reg = /(?:\d.*_)|(?:_.*\d)|(?:[A-Za-z].*_)|(?:_.*[A-Za-z])|(?:[A-Za-z].*\d)|(?:\d.*[A-Za-z])/;

        $("#oldPwd").blur(function () {
            oldPwd = $("#oldPwd").val();
            if (oldPwd.length > 0) {
                $("#oldPwd").val("* * * * * *");
            }
        });
        $('#oldPwd').focus(function () {
            $("#oldPwd").val(oldPwd);
        });

        $("#newPwd").blur(function () {
            newPwd = $("#newPwd").val();
            if (newPwd.length > 0) {
                $("#newPwd").val("* * * * * *");
            }
            if (newPwd.length < 6) {
                $("#num1").css("background-color", "red");
                $("#num2").css("background-color", "grey");
                $("#num3").css("background-color", "grey");
                $(".safe1").show();
                $(".safe2").hide();
                $(".safe3").hide();
            }
            if (newPwd.length > 5 && !newPwd.match(reg)) {
                $("#num1").css("background-color", "yellow");
                $("#num2").css("background-color", "yellow");
                $("#num3").css("background-color", "grey");
                $(".safe1").hide();
                $(".safe2").show();
                $(".safe3").hide();
            }
            if (newPwd.match(reg)) {
                $("#num1").css("background-color", "green");
                $("#num2").css("background-color", "green");
                $("#num3").css("background-color", "green");
                $(".safe1").hide();
                $(".safe2").hide();
                $(".safe3").show();
            }

            if (!newPwd.match(reg)) {
                layer.msg("新密码不符合规范！");
            }
        });
        $('#newPwd').focus(function () {
            $("#newPwd").val(newPwd);
        });

        $("#conPwd").blur(function () {
            conPwd = $("#conPwd").val();
            if (conPwd.length > 0) {
                $("#conPwd").val("* * * * * *");
            }
            if (conPwd != newPwd) {
                layer.msg("两次输入的密码不一致！");
            }
        });
        $('#conPwd').focus(function () {
            $("#conPwd").val(conPwd);
        });

        /**
         * 保存
         */
        $("#save").click(function () {
            if (!validata.verify()) {
                return false;
            }
            if (conPwd != newPwd) {
                return false;
            }
            if (!newPwd.match(reg)) {
                layer.msg("新密码不符合规范！")
                return false;
            }
            kirin.ajax({
                url: "/user/password/alter.json",
                type: "post",
                dataType: "json",
                data: {oldPwd: oldPwd, newPwd: newPwd},
                success: function (result) {
                    var meta = result.meta;
                    if (meta.code == 200) {
                        layer.msg("保存成功！", {icon: 6});
                        setTimeout("kirin.popup.success('success')", 2000);
                    } else {
                        layer.msg("保存失败！", {icon: 5});
                    }
                }
            });
        });

        $("#cancle").click(function () {
            kirin.popup.close();
        })
    });
</script>
</@override>

<@extends name="/common/layout/layout-popup.ftl"/>