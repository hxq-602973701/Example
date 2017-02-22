package com.java1234.service.impl;

import com.java1234.dao.BookDao;
import com.java1234.entity.Book;
import com.java1234.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService {

	@Resource
	private BookDao bookDao;

    @Override
    public List<Book> selectAll(Book book) {
        return bookDao.selectAll(book);
    }

    @Override
    public List<Book> selectByBookList(Book book) {
        return bookDao.selectByBookList(book);
    }

    @Override
    public void updateByPK(Book book) {
        bookDao.updateByPK(book);
    }

    @Override
    public void save(Book book) {
        bookDao.save(book);
    }

    @Override
    public void delete(Book book) {
        bookDao.delete(book);
    }
}
