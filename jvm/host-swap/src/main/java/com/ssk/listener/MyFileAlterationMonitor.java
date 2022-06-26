package com.ssk.listener;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * @author 惊云
 * @date 2021/12/18 16:39
 */
public class MyFileAlterationMonitor {
    private String path;		// 文件夹目录

    private String fileSuffix;	// 需要监听的文件名后缀

    private long interval;		// 监听间隔

    private static final long DEFAULT_INTERVAL = 10 * 1000; // 默认监听间隔10s

    private FileAlterationListenerAdaptor listener;	// 事件处理类对象

    public MyFileAlterationMonitor(){
        this.interval = DEFAULT_INTERVAL;
    }

    public MyFileAlterationMonitor(String path, String fileSuffix, FileAlterationListenerAdaptor listenerAdaptor){
        this.path = path;
        this.fileSuffix = fileSuffix;
        this.interval = DEFAULT_INTERVAL;
        this.listener = listenerAdaptor;
    }

    public MyFileAlterationMonitor(String path, String fileSuffix, long interval, FileAlterationListenerAdaptor listenerAdaptor) {
        this.path = path;
        this.fileSuffix = fileSuffix;
        this.interval = interval;
        this.listener = listenerAdaptor;
    }

    /***
     * 开启监听
     */
    public void start() {
        if(path == null) {
            throw new IllegalStateException("Listen path must not be null");
        }
        if(listener==null) {
            throw new IllegalStateException("Listener must not be null");
        }

        // 设定观察者，监听.properties文件
        FileAlterationObserver observer = new FileAlterationObserver(path,
                FileFilterUtils.suffixFileFilter(fileSuffix));

        // 给观察者添加监听事件
        observer.addListener(listener);

        // 开启一个监视器，监听频率是5s一次
        // FileAlterationMonitor本身实现了 Runnable，是单独的一个线程，按照设定的时间间隔运行，默认间隔是 10s
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval);

        monitor.addObserver(observer);

        try {
            monitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public void setAdaptor(FileAlterationListenerAdaptor listenerAdaptor) {
        this.listener = listenerAdaptor;
    }

}
