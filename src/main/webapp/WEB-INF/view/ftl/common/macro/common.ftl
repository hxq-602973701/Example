<#-- 即时刷新 -->
<#macro livereload>
    <#if utils.isDebugMode()>
    <#--
    <script>document.write('<script src="http://' + (location.host || 'localhost').split(':')[0] + ':35729/livereload.js?snipver=1"></' + 'script>')</script>
    -->
    </#if>
</#macro>

<#-- 数字格式化 -->
<#macro number val><#if val??>${val?string('0.00')}</#if></#macro>


<#-- 单位 -->
<#macro deptSelect deptList value isDefault=true>
    <#if isDefault>
    <option value="">--请选择--</option>
    </#if>
    <#if value??>
        <#list deptList as dept>
        <option <#if dept.deptId == "${value}">selected</#if> value="${dept.deptId}">${dept.deptName}</option>
        </#list>
    <#else>
        <#list deptList as dept>
        <option value="${dept.deptId}">${dept.deptName}</option>
        </#list>
    </#if>
</#macro>

<#-- 角色 -->
<#macro roleSelect roleList value isDefault=true>
    <#if isDefault>
    <option value="">--请选择--</option>
    </#if>
    <#list roleList as role>
    <option <#if utils.isAuthCheck(value, role.authType)>selected</#if> value="${role.authType}">${role.roleName}</option>
    </#list>
</#macro>