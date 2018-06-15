package com.java1234.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.java1234.dal.annotation.AuthCheck;
import com.java1234.dal.constant.page.SystemPageID;
import com.java1234.dal.entity.main.sys.property.Property;
import com.java1234.dal.utils.DataPipe;
import com.java1234.service.sys.property.PropertyService;
import com.java1234.util.Ids;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/11.
 */
@Controller
public class PropertyController {

    @Resource
    private PropertyService propertyService;

    /**
     * 进入业务参数页面
     *
     * @return
     */
    @AuthCheck(page = SystemPageID.PROPERTY_LIST)
    @RequestMapping(value = "/property/property-index", method = RequestMethod.GET)
    public String propertyView() {
        return "/system/property/property-index";
    }


    /**
     * 获取业务参数树结构
     */
    @AuthCheck(page = SystemPageID.PROPERTY_LIST)
    @RequestMapping(value = "/property/property_tree", method = RequestMethod.GET)
    public void getPropTree(Model model, Property param) {

        final String root = propertyService.selectPropTreeHierarchy(param);
        DataPipe.in(model).response(root);
    }

    /**
     * 获取prop分页列表数据
     *
     * @param model
     * @param param
     */
    @AuthCheck(page = SystemPageID.PROPERTY_LIST)
    @RequestMapping(value = "/property/paging", method = RequestMethod.GET)
    public void propPageApi(Model model, Property param) {

        param.setDelFlag(false);
        PageInfo<Property> pageInfo = propertyService.selectPageByProperty(param);
        DataPipe.in(model).response(pageInfo);
    }

    /**
     * 业务参数编辑界面
     *
     * @param model
     */
    @RequestMapping(value = "/property/edit", method = RequestMethod.GET)
    @AuthCheck(page = SystemPageID.PROPERTY_LIST)
    public String editPropView(Model model, Property param) {

        if (Ids.verifyId(param.getPropId())) {
            //如果存在id为编辑状态
            Property property = propertyService.selectByPrimaryKey(param);
            model.addAttribute("prop", property);
        }

        //如果选中节点创建
        if (param.getPropParentId() != null) {
            model.addAttribute("parent", param.getPropParentId());
        }

        //获取父节点参数列表
        Property prop = new Property();
        prop.setDelFlag(false);
        List<Property> parentList = propertyService.select(prop);
        model.addAttribute("parentList", parentList);

        return "/system/property/property-edit";
    }

    /**
     * 保存业务参数数据
     *
     * @param param
     */
    @AuthCheck(page = SystemPageID.PROPERTY_LIST)
    @RequestMapping(value = "/property/edit", method = RequestMethod.POST)
    public void savePropApi(Property param) {

        propertyService.saveProp(param);
    }

    /**
     * 批量逻辑删除
     *
     * @param param
     */
    @AuthCheck(page = SystemPageID.PROPERTY_LIST)
    @RequestMapping(value = "/property/edit", method = RequestMethod.DELETE)
    public void deletePropApi(Long[] param) {

        propertyService.deleteProp(param);
    }

    /**
     * 根据父节点获取业务参数列表json格式
     *
     * @return
     */
    @RequestMapping(value = "/system/property-get-sub", method = RequestMethod.GET)
    public void getPropertyJsonByParentId(final Model model, Long parentId) {
        Property param = new Property();
        param.setPropParentId(parentId);
        param.setDelFlag(false);
        List<Map> listMap = propertyService.getAllSubMapByParentId(parentId);
        DataPipe.in(model).response(listMap);
    }

}
