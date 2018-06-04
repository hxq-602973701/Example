package com.java1234.dal.dao.sys.menu.impl;

import com.java1234.dal.dao.base.impl.BaseDAOImpl;
import com.java1234.dal.dao.sys.menu.MenuDAO;
import com.java1234.dal.entity.main.sys.menu.Menu;
import com.java1234.dal.mapper.main.sys.menu.MenuMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * MenuDAO
 *
 * @author lt 2018-6-1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class MenuDAOImpl extends BaseDAOImpl<Menu> implements MenuDAO {
    
    /**
     * MenuMapper
     */
    @Resource
    private MenuMapper menuMapper;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected Mapper<Menu> getMapper() {
        return menuMapper;
    }
}