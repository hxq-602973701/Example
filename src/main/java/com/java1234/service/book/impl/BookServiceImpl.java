package com.java1234.service.book.impl;

import com.java1234.dal.dao.base.BaseDAO;
import com.java1234.dal.dao.book.BookDAO;
import com.java1234.dal.entity.main.book.Book;
import com.java1234.service.base.impl.BaseServiceImpl;
import com.java1234.service.book.BookService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * BookService
 *
 * @author lt 2017/9/1
 * @version 1.0.0
 * @category 南阳理工学院
 */
@Service
public class BookServiceImpl extends BaseServiceImpl<Book> implements BookService {
    
    /**
     * BookDAO
     */
    @Resource
    private BookDAO bookDAO;

    /**
     * Mapper初始化
     *
     * @return
     */
    @Override
    protected BaseDAO<Book> getDAO() {
        return bookDAO;
    }
}