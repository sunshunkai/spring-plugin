package com.ssk.hot.deployment.service;

import com.ssk.hot.deployment.classLoader.MayiktClassLoader;
import com.ssk.hot.deployment.entity.ClassFileEntity;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 惊云
 * @date 2021/12/18 20:05
 */
public class HotDeploymentPlug {
    private String path = "D:\\mayikt\\mayikt-jvm";
    private Map<String, ClassFileEntity> classFiles = new HashMap<>();
    private String packageName = "com.mayikt.days01.";

    /**
     * 热部署插件原理 （手写）
     * 1.class 如何判断一个class文件是否发生变化？
     * MD5、操作系统提供api 文件修改时间
     * 2. 判断该class文件修改日期是否有发生变化，如果
     * 有发生变化，则从新使用类加载器读取最新的class文件
     * 到内存中。
     * 3.如何监听-----class文件是否有发生变化呢？ 单独线程
     */
    public void start() {
        new Thread(() -> {
            // 1.读取本地磁盘中 class文件
            // 1.读取该文件下  第一次 读取每个class文件 记录每个class文件修改时间
            while (true) {
                File files = new File(path);
                File[] tempList = files.listFiles();
                for (File file : tempList) {

                    // 文件最后修改时间
                    long lastModified = file.lastModified();
                    // 文件名称 packageName+MeiteEntity.class
                    String className = packageName + file.getName().replace(".class", "");
                    ClassFileEntity mapClassFileEntity = classFiles.get(className);
                    // &&
                    if (mapClassFileEntity != null) {
                        if (mapClassFileEntity.getLastModified() != lastModified) {
                            try {
                                // 说明了 class文件有发生变化
                                MayiktClassLoader mayiktClassLoader = new MayiktClassLoader(file);
                                Class<?> aClass = mayiktClassLoader.loadClass(className);
                                Object o = aClass.newInstance();
                                System.out.println(">className:"+className+" 文件发生了变化<");
                               //java反射机制 调用 mayikt方法
                                Method mayikt = aClass.getMethod("mayikt");
                                Object result = mayikt.invoke(o);
                                // 同步最新的 class文件修改时间
                                mapClassFileEntity.setLastModified(lastModified);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }

                    } else {
                        // 在我们的集合中不存在该 ClassFileEntity
                        ClassFileEntity classFileEntity = new ClassFileEntity(className, lastModified);
                        classFiles.put(className, classFileEntity);
                    }

                }
                // 避免cpu飙高的问题
                try {
                    Thread.sleep(300);
                } catch (Exception e) {

                }
            }
            // 2.读取class文件 存入到 mapClassFiles
            // 2.监听该class文件是否有发生变化
            // 3.如果该class文件最后的修改时间 发生了变化 则从新使用类加载器读取

        }).start();
    }

    public static void main(String[] args) {
        HotDeploymentPlug hotDeploymentPlug = new HotDeploymentPlug();
        hotDeploymentPlug.start();
    }
}
