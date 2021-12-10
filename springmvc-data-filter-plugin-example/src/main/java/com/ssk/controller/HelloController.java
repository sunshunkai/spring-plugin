package com.ssk.controller;

import com.ssk.bean.User;
import com.ssk.bean.Book;
import com.ssk.configuration.MyWebMvcConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
@RestController
public class HelloController {

    @Autowired
    private MyWebMvcConfigurer myWebMvcConfigurer;

    @RequestMapping("hello")
    public User hello(){

        User user = new User();
        user.setAge(24);
        user.setName("zhangsan");
        user.setId(1L);
        user.setBaseName("baseName");


        Book book = new Book();
        book.setId(10L);
        book.setBookName("JAVA开发");
//        book.setUser(user);

        Book book1 = new Book();
        book1.setId(11L);
        book1.setBookName("MySQL 性能调优");
//        book1.setUser(user);

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        bookList.add(book1);

        user.setBookList(bookList);

//        int i = 1/0;
        myWebMvcConfigurer.getFilerUrl();

        return user;
    }

}
