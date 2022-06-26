package com.ssl.listener;

import com.ssk.listener.MyFileAlterationMonitor;
import com.ssk.listener.MyFileListenerAdaptor;

/**
 * @author 惊云
 * @date 2021/12/18 16:40
 */
public class Test {

    public static void main(String[] args) {

        MyFileAlterationMonitor monitor = new MyFileAlterationMonitor(
                Test.class.getClassLoader().getResource("properties").getPath(),
                ".properties",
                new MyFileListenerAdaptor());
        monitor.start();

    }

}
