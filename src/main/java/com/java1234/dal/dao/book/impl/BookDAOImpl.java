package com.java1234.dal.dao.book.impl;

import com.java1234.dal.dao.base.impl.BaseDAOImpl;
import com.java1234.dal.dao.book.BookDAO;
import com.java1234.dal.entity.main.book.Book;
import com.java1234.dal.mapper.main.book.BookMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;

/**
 * BookDAO
 *
 * @author lt 2017/9/1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class BookDAOImpl extends BaseDAOImpl<Book> implements BookDAO {

    /**
     * BookMapper
     */
    @Resource
    private BookMapper bookMapper;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected Mapper<Book> getMapper() {
        return bookMapper;
    }
}