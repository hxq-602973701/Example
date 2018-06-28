<!-----------------------------------------------------中心指令发布信息----------------------------------------------------------------->
<#macro center_release id>
    <#local demandReleaseModel=utils.getdemandReleaseById(id)/>
<tbody>
<tr>
    <th>指令编号</th>
    <td>
        <input class="form-control" name="demandReleaseCode" type="text" value="${demandReleaseModel.demandReleaseCode}"
               readonly>
    </td>
    <th class="required-input">指令类别</th>
    <td>
        <select class="form-control" name="demandType">
            <option value="0">--请选择--</option>
            <option value="1">刑事</option>
            <option value="2">治安</option>
            <option value="3">社会信息</option>
            <option value="4">重大维稳</option>
        </select>
    </td>
    <th class="required-input">采集时间</th>
    <td>
        <input type="text" id="collectionTime" name="demandCollectionTime" class="form-control laydate-icon"
               value="${demandReleaseModel.demandCollectionTime?string("yyyy-MM-dd HH:mm:ss")}">
    </td>
</tr>

<tr>
    <th class="required-input">指令标题</th>
    <td colspan="5">
        <input type="text" name="demandTitle" class="form-control" placeholder="指令标题"
               value="${demandReleaseModel.demandTitle}">
    </td>
</tr>

<tr>
    <th class="required-input">指令描述</th>
    <td colspan="5">
        <textarea rows="5" name="demandDescribe" class="form-control">${demandReleaseModel.demandDescribe}</textarea>
    </td>
</tr>
<tr>
    <th>附件</th>
    <td colspan="5">
        <input type="text" id="attached" name="attached" value='${demandReleaseModel.attached}'>
    </td>
</tr>

<tr>
    <th class="required-input">采集要求</th>
    <td colspan="5">
        <textarea rows="5" name="demandContent" class="form-control">${demandReleaseModel.demandContent}</textarea>
    </td>
</tr>
<tr>
    <th>采集时限（小时）</th>
    <td colspan="5">
        <input type="text" name="limitTime">
    </td>
</tr>

<tr>
    <th class="required-input">紧急程度</th>
    <td colspan="5">
        <div class="u-form-checkbox-control">
            <div class="radio radio-info radio-inline">
                <input type="radio" id="general" value="1" name="emergencyLevel"
                       <#if demandReleaseModel.emergencyLevel==1>checked="checked"<#else >checked="checked"</#if>>
                <label for="general">平急</label>
            </div>

            <div class="radio radio-warning radio-inline">
                <input type="radio" id="urgent" value="2" name="emergencyLevel"
                       <#if demandReleaseModel.emergencyLevel==2>checked="checked"</#if>>
                <label for="urgent">紧急</label>
            </div>

            <div class="radio radio-danger radio-inline">
                <input type="radio" id="special" value="3" name="emergencyLevel"
                       <#if demandReleaseModel.emergencyLevel==3>checked="checked"</#if>>
                <label for="special">特急</label>
            </div>
        </div>
    </td>
</tr>

<tr>
    <th class="required-input">发布范围</th>
    <td colspan="5">
        <div class="u-dept-tit">
      <span class="m-r-sm">
        <input type="checkbox" id="checkAll" value=""/>
        <label for="checkAll" class="check-box"></label>
        <span class="u_checkbox-text">全选</span>
      </span>
        </div>

        <table class="u-dept-table">
            <tbody>
            <tr>
                <td style="width: 20%">
          <span class="m-r-sm">
            <input type="checkbox" id="uy" name="deptCheckbox" class="check-dept" value="11111111"/>
            <label for="uy" class="check-box"></label>
            <span class="u_checkbox-text">测试部门</span>
          </span>
                </td>
                <td style="width: 20%">
          <span class="m-r-sm">
            <input type="checkbox" id="66" name="deptCheckbox" value="33010002"/>
            <label for="66" class="check-box"></label>
            <span class="u_checkbox-text">刑侦大队</span>
          </span>
                </td>
                <td style="width: 20%">
          <span class="m-r-sm">
            <input type="checkbox" id="11" name="deptCheckbox" value="33010003"/>
            <label for="11" class="check-box"></label>
            <span class="u_checkbox-text">经侦（禁毒）大队</span>
          </span>
                </td>
                <td style="width: 20%">
          <span class="m-r-sm">
            <input type="checkbox" id="22" name="deptCheckbox" value="33010004"/>
            <label for="22" class="check-box"></label>
            <span class="u_checkbox-text">治安大队</span>
          </span>
                </td>
                <td style="width: 20%">
          <span class="m-r-sm">
            <input type="checkbox" id="33" name="deptCheckbox" value="33010006"/>
            <label for="33" class="check-box"></label>
            <span class="u_checkbox-text">巡特警大队</span>
          </span>
                </td>
            </tr>

            <tr>
                <td style="width: 20%">
          <span class="m-r-sm">
            <input type="checkbox" id="aa" name="deptCheckbox" value="33010007"/>
            <label for="aa" class="check-box"></label>
            <span class="u_checkbox-text">出入境管理大队（窗口）</span>
          </span>
                </td>
                <td style="width: 20%">
          <span class="m-r-sm">
            <input type="checkbox" id="bb" name="deptCheckbox" value="33010009"/>
            <label for="bb" class="check-box"></label>
            <span class="u_checkbox-text">白杨派出所</span>
          </span>
                </td>
                <td style="width: 20%">
          <span class="m-r-sm">
            <input type="checkbox" id="cc" name="deptCheckbox" value="33010010"/>
            <label for="cc" class="check-box"></label>
            <span class="u_checkbox-text">金沙湖派出所</span>
          </span>
                </td>
                <td style="width: 20%">
          <span class="m-r-sm">
            <input type="checkbox" id="dd" name="deptCheckbox" value="33010011"/>
            <label for="dd" class="check-box"></label>
            <span class="u_checkbox-text">闻潮派出所</span>
          </span>
                </td>
                <td style="width: 20%">
          <span class="m-r-sm">
            <input type="checkbox" id="ee" name="deptCheckbox" value="33010012"/>
            <label for="ee" class="check-box"></label>
            <span class="u_checkbox-text">下沙派出所</span>
          </span>
                </td>
            </tr>
            </tbody>
        </table>

    </td>
</tr>

<tr>
    <th>发布单位</th>
    <td>
        <input type="text" class="form-control" value="${demandReleaseModel.dept.deptName}" readonly>
    </td>
    <th>发布人</th>
    <td>
        <input type="text" class="form-control" value="${demandReleaseModel.user.userName}" readonly>
    </td>
    <th>发布时间</th>
    <td>
        <input type="text" name="createTime" class="form-control"
               value="${demandReleaseModel.createTime?string("yyyy-MM-dd HH:mm:ss")}" readonly>
    </td>
</tr>

</tbody>
</#macro>
<!-------------------------------------------------------查询条件---------------------------------------------------------------------------------->
<#macro query>
<div class="row ibox-search">
    <div class="col-xs-10 col-md-11 form-inline search-input">
        <div class="input-group">
            <span class="input-group-addon">发布时间</span>
            <input type="text" id="startTime" name="startTime" class="form-control layer-date laydate-icon"
                   placeholder="请输入开始时间">
        </div>
        <div class="input-group">
            <span class="input-group-addon">到</span>
            <input type="text" id="endTime" name="endTime" class="form-control layer-date laydate-icon"
                   placeholder="请输入结束时间">
        </div>
        <div class="input-group">
            <span class="input-group-addon">采集要求</span>
            <input type="text" class="form-control" name="demandContent" placeholder="请输入采集要求">
        </div>
        <div class="input-group">
            <span class="input-group-addon">紧急程度</span>
            <select class="form-control" name="emergencyLevel">
                <option value="">--请选择--</option>
                <option value="FLAT_EMERGENCY">平急</option>
                <option value="NERVOUS_EMERGENCY">紧急</option>
                <option value="SPECIAL_EMERGENCY">特急</option>
            </select>
        </div>
        <div class="input-group">
            <span class="input-group-addon">指令类别</span>
            <select class="form-control" name="demandType">
                <option value="">--请选择--</option>
                <option value="NEEDS_CRIMINAL">刑事</option>
                <option value="NEEDS_SECURITY">治安</option>
                <option value="NEEDS_SOCIAL_INFORMATION">社会信息</option>
                <option value="NEEDS_GREAT_STABILITY">重大维稳</option>
            </select>
        </div>
        <div class="input-group">
            <span class="input-group-addon">处理状态</span>
            <select class="form-control">
                <option>--请选择--</option>
                <option value="DEMAND_NEED_SIGNED">待签收</option>
                <option value="DEMAND_ALREADY_SIGN">已签收</option>
                <option value="">已反馈</option>
            </select>
        </div>
        <div class="input-group">
            <span class="input-group-addon">指令标题</span>
            <input type="text" class="form-control" name="demandTitle" placeholder="请输入指令标题">
        </div>
        <div class="input-group">
            <span class="input-group-addon">指令描述</span>
            <input type="text" class="form-control" name="demandDescribe" placeholder="请输入指令描述">
        </div>
    </div>
    <div class="col-xs-2 col-md-1 u-btn-center">
        <button type="button" class="btn btn-success btn-lg btn-outline pull-right" id="btnQuery"><i
                class="fa fa-search"></i> 查询
        </button>
        <button type="button" class="btn btn-success btn-lg btn-outline pull-right" id="btnExport"><i
                class="fa fa-print"></i> 导出
        </button>
    </div>
</div>
<script>
    function getQueryData() {
        var queryData = {};
        $(".ibox-search input[name],select[name]").each(function () {
            if ($(this).val() != "") {
                queryData[$(this).attr("name")] = $.trim($(this).val());
            }
        });
        return queryData;
    }
</script>
</#macro>