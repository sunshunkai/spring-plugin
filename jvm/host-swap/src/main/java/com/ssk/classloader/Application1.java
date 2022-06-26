package com.ssk.classloader;

import java.io.File;
import java.lang.reflect.Method;

/**
 * @author 惊云
 * @date 2021/12/18 20:05
 */
public class Application1 {

    public static String rootPath;

    public static void run(Class<?> clazz) throws Exception {
        String path = clazz.getResource("/").getPath().replaceAll("%20", " ");
        String rootPath = new File(path).getPath();
        Application1.rootPath = rootPath;
        FileLisener.startFileMino(rootPath);
        MyClassLoader myClassLoader = new MyClassLoader(rootPath, rootPath +"/com/ssl/classloader");
        start0(myClassLoader);
    }

    public void start(){
        System.out.println("==");
    }


    public static void start0(MyClassLoader myClassLoader) throws Exception {
        Class<?> aClass = myClassLoader.loadClass("com.ssk.classloader.Application1");
        Object o = aClass.newInstance();
        aClass.getMethod("start").invoke(o);
    }

    public static void main(String[] args) throws Exception {

        TestCode testCode = new TestCode();
        System.out.println(testCode.hashCode());
        MyClassLoader classLoader = new MyClassLoader();
        Class<?> aClass = classLoader.loadClass("D:\\work\\code\\ssk\\spring-plugin\\jvm\\host-swap\\target\\classes\\com\\ssk\\classloader\\TestCode.class");

        testCode.hello();
        TestCode o = (TestCode)aClass.newInstance();
        o.hello();
    }


}
