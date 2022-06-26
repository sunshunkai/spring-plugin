//package com.ssk.classloader;
//
//import cn.hutool.core.io.FileUtil;
//import cn.hutool.core.io.file.FileReader;
//
//import java.io.File;
//import java.net.URL;
//
///**
// * @author 惊云
// * @date 2021/12/18 17:32
// */
//public class HotDeploymentClassLoaderV3 extends ClassLoader{
//
//    public static String dir;
//
//    static {
//        // 获取target 加载的路径
//        URL resource = Thread.currentThread().getContextClassLoader().getResource("");
//        dir = resource.getPath();
//    }
//
//    @Override
//    public Class<?> loadClass(String name) throws ClassNotFoundException {
//        // 加锁
//        synchronized (getClassLoadingLock(name)){
//            // 判断当前包名类，是否在当前用户classpath 下，如果存在，就执行自定义的findclass
//            // 不是，就走父类的类加载器
//            String file = dir + File.separator + (name.replace(".", "/")) + ".class";
//            if(FileUtil.exist(file)){
//                // 执行findclass
//                Class<?> aClass = findClass(name);
//                if(aClass == null){
//                    throw new ClassNotFoundException("未找到该类");
//                }
//                return aClass;
//            }
//            return super.loadClass(name);
//        }
//    }
//
//
//    @Override
//    protected Class<?> findClass(String name) throws ClassNotFoundException {
//        String file = dir + File.separator + (name.replace(".", "/")) + ".class";
//        FileReader fileReader = new FileReader(file);
//        byte[] bytes = fileReader.readBytes();
//        // 调用defineClass 将二进制数据转化为class对象
//        return defineClass(name, bytes, 0, bytes.length);
//    }
//
//
//}
