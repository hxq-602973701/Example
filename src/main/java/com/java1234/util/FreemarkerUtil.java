package com.java1234.util;

import com.java1234.dal.entity.main.book.Book;
import com.java1234.service.book.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Freemarker工具类
 *
 * @author lt 2017-9-1晚上21:54:52
 * @version 1.0.0
 */

@Component
public class FreemarkerUtil {

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);

    /**
     * 全局配置
     *
     * @return
     */
    public static Book getBook() {
        BookService bookService = SpringUtil.getBean(BookService.class);
        Book book = new Book();
        book.setBookId(9L);
        return bookService.selectOne(book);
    }

}
