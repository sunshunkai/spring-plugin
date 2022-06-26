package com.ssk.classloader;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

/**
 * @author 惊云
 * @date 2021/12/18 20:07
 */
public class FileLisener extends FileAlterationListenerAdaptor {

    @Override
    public void onFileChange(File file) {
        if (file.getPath().contains(".class")) {
            try {
                MyClassLoader myClassLoader = new MyClassLoader(Application1.rootPath, Application1.rootPath + "/dsvshx");
                Application1.start0(myClassLoader);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void startFileMino(String rootPath) throws Exception{
        FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(rootPath);
        fileAlterationObserver.addListener(new FileLisener());
        FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(5000);
        fileAlterationMonitor.addObserver(fileAlterationObserver);
        fileAlterationMonitor.start();
    }

}
