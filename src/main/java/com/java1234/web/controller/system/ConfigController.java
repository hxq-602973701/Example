package com.java1234.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.java1234.dal.annotation.AuthCheck;
import com.java1234.dal.constant.page.SystemPageID;
import com.java1234.dal.entity.main.sys.config.Config;
import com.java1234.dal.entity.main.sys.dept.Dept;
import com.java1234.dal.enums.ConfigEnum;
import com.java1234.dal.enums.DeptTypeEnum;
import com.java1234.dal.utils.DataPipe;
import com.java1234.dal.utils.LoginContext;
import com.java1234.service.sys.config.ConfigService;
import com.java1234.service.sys.dept.DeptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/6/11.
 */
@Controller
public class ConfigController {

    /**
     * 系统参数Service
     */
    @Resource
    private ConfigService configService;

    /**
     * 单位Service
     */
    @Resource
    private DeptService deptService;

    /**
     * 显示系统参数列表页面
     *
     * @return
     */
    @AuthCheck(page = SystemPageID.CONFIG_LIST)
    @RequestMapping(value = "/system/config-list", method = RequestMethod.GET)
    public String configListView() {
        return "/system/config/config-list";
    }

    /**
     * 系统参数列表接口
     *
     * @param model
     * @param param
     */
    @AuthCheck(page = SystemPageID.CONFIG_LIST)
    @RequestMapping(value = "/system/config-page", method = RequestMethod.GET)
    public void configPageingApi(final Model model, Config param) {
        // 分页获取系统参数列表
        param.setDelFlag(false);

//        final Set<EntityColumn> pkSet = EntityHelper.getPKColumns(param.getClass());
//        final Optional<EntityColumn> firstPk = pkSet.stream().findFirst();
//        // 根据主键名排序,如果存在的话。
//        if (firstPk.isPresent()) {
//            final String pkName = firstPk.get().getColumn();
//            if (StringUtils.isNotBlank(pkName)) {
//                PageHelper.orderBy(pkName);
//            }
//        }
//        // 启用分页
//        PageHelper.startPage(param.getPageNum(), param.getPageSize(), true);
//        final PageInfo<Config> pageList = new PageInfo<>(configService.select(param));
        final PageInfo<Config> pageList = configService.selectPage(param);
        DataPipe.in(model).response(pageList);
    }

    /**
     * 显示系统参数编辑页面
     *
     * @param model
     * @param configId
     * @return
     */
    @AuthCheck(page = SystemPageID.CONFIG_LIST)
    @RequestMapping(value = "/system/config", method = RequestMethod.GET)
    public String configDetailView(final Model model, Integer configId) {

        final Config config = configService.selectByPrimaryKey(configId);
        if (config != null) {
            //信息员代号自动生成
            if (config.getConfigKey().equals(ConfigEnum.INFORMER_CLERK_SYMBOL.getValue()) || config.getConfigKey().equals(ConfigEnum.INFORMER_EYE_SYMBOL.getValue()) || config.getConfigKey().equals(ConfigEnum.INFORMER_AGENT_SYMBOL.getValue())) {
                List<Dept> deptList = deptService.selectAll();
                List<Dept> brachList = deptList.stream().filter(dept -> dept.getDeptType() == DeptTypeEnum.DISTRICT_BUREAU.getValue()).collect(Collectors.toList());

                model.addAttribute("branchSelectShow", true);
                model.addAttribute("brachList", brachList);
            }
            String branchIds = config.getDeptIds();
            if (branchIds != null) {
                List<Long> branchIdList = new ArrayList<>();
                String[] branchIdsArray = branchIds.split(",");
                for (String branchId : branchIdsArray) {
                    branchIdList.add(Long.valueOf(branchId));
                }
                //将集合转化成JSON字符串
                String branchBackIds = JSON.toJSONString(branchIdList);
                model.addAttribute("branchIdList", branchBackIds);
            }
        }
        model.addAttribute("config", config);

        return "/system/config/config-edit";
    }

    /**
     * 保存系统参数
     *
     * @param param
     * @return
     */
    @AuthCheck(page = SystemPageID.CONFIG_LIST)
    @RequestMapping(value = "/system/config", method = RequestMethod.POST)
    public void saveConfigApi(Config param) {

        //所属分县局ID
//        param.setDeptIds(LoginContext.getUser().getBranchId());

        //操作用户
        param.setModifiedUid(LoginContext.getUserId());

        // 保存系统参数
        configService.saveConfig(param);
    }

    /**
     * 批量删除系统参数
     *
     * @param configIds
     * @return
     */
    @AuthCheck(page = SystemPageID.CONFIG_LIST)
    @RequestMapping(value = "/system/config", method = RequestMethod.DELETE)
    public void deleteConfigsApi(Integer[] configIds) {

        // 批量删除系统参数
        configService.deleteWithLogicByPrimaryKeys(configIds, LoginContext.getUserId());
    }
}
