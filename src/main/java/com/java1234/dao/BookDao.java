package com.java1234.dao;


import com.java1234.entity.Book;

import java.util.List;

public interface BookDao {

    List<Book> selectAll(Book book);

    List<Book> selectByBookList(Book book);

    void updateByPK(Book book);

    void save(Book book);
}
