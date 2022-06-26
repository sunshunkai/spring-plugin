package com.ssk.classloader;

/**
 * @author 惊云
 * @date 2021/12/18 17:41
 */
public class TestCode {

    static {
        System.out.println("TestCode类加载器:" + TestCode.class.getClassLoader());
    }



    public void hello(){
        System.out.println("hello88");
    }

}
