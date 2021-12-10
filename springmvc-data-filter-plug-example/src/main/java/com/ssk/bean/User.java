package com.ssk.bean;

import lombok.Data;

import java.util.List;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
@Data
public class User extends BaseBO{

    private Long id;

    private String name;

    private Integer age;

    private List<Book> bookList;
}
