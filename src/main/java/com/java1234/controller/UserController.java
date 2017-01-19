package com.java1234.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java1234.entity.Book;
import com.java1234.service.BookService;
import com.java1234.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {

    @Resource
    private BookService bookService;

        private static  ObjectMapper objectMapper;
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("username", "张三");
        return "hello";
    }

    @RequestMapping("/world")
    public String helloWorld(Model model) {
        model.addAttribute("username","李四");
        return "world";
    }

    @RequestMapping(value = "/book",method = RequestMethod.GET)
    public void bookList(Book book, HttpServletResponse response) throws Exception{
        List<Book> bookList = bookService.selectAll(book);
        objectMapper= new ObjectMapper();
        String json = objectMapper.writeValueAsString(bookList);
        //String json = JSON.toJSONString(bookList);
        ResponseUtil.write(json,response);
    }

    @RequestMapping(value = "/book/show",method = RequestMethod.GET)
    public String bookShow(final Model model,Book book) throws Exception{
        if(book.getBookId()!=null){
            List<Book> bookList = bookService.selectAll(book);
            book = bookList.get(0);
            model.addAttribute("book",book);
        }

        return "/book/show";
    }

    @RequestMapping(value = "/book/saveOrUpdate",method = RequestMethod.POST)
    @ResponseBody
    public String bookSaveOrUpdate(final Model model,String peopleBaseStr) throws Exception{
        Book book = objectMapper.readValue(peopleBaseStr,Book.class);
        if(book.getBookId()!=null){
            bookService.updateByPK(book);
        }else{
            bookService.save(book);
        }

        return null;
       /* return objectMapper.writeValueAsString(book);*/
    }


}
