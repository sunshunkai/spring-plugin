package io.terminus.gaia.bean;

import lombok.Data;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
@Data
public class Book {

    private Long id;

    private String bookName;

    private User user;

}
