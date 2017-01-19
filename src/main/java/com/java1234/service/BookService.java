package com.java1234.service;

import com.java1234.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> selectAll(Book book);

    List<Book> selectByBookList(Book book);

    void updateByPK(Book book);

    void save(Book book);
}
