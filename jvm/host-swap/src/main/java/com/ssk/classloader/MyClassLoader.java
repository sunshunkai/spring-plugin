package com.ssk.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 惊云
 * @date 2021/12/18 20:02
 */
public class MyClassLoader extends ClassLoader{

    public String rootPath;
    public String[] classPaths;
    public List<String> clazzs;

    public MyClassLoader(){}

    public MyClassLoader(String rootPath, String... classPaths) throws Exception {
        this.rootPath = rootPath;
        clazzs = new ArrayList<>();
        this.classPaths = classPaths;
        for (String classPath : classPaths) {
            this.findClass(classPath);
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> aClass = findClass(name);
        if(aClass == null){
            super.loadClass(name,resolve);
        }
        return aClass;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = findLoadedClass(name);
        if (clazz == null) {
            File file = new File(name);
            if (file.isDirectory()) {
                for (File f : Objects.requireNonNull(file.listFiles())) {
                    this.findClass(f.getPath());
                }
            } else {
                try {
                    String fileName = file.getName();
                    String filePath = file.getPath();
                    String endName = fileName.substring(fileName.lastIndexOf(".") + 1);
                    if (endName.equals("class")) {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] bytes = new byte[(int) file.length()];
                        fileInputStream.read(bytes);
                        String className = filePath.replace(rootPath, "").replaceAll("/", ".");
                        className = className.substring(1, className.lastIndexOf("."));
                        clazzs.add(className);
                        // class文件已经到了虚拟机了
                        return defineClass(className, bytes, 0, bytes.length);
                    }
                } catch (Exception e) {
                    return super.findClass(name);
                }
            }
        }
        return clazz;
    }


}
