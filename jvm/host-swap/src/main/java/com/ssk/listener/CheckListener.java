package com.ssk.listener;

import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import cn.hutool.core.lang.Console;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @author 惊云
 * @date 2021/12/18 17:37
 */
public class CheckListener {

    /**
     * 监听方法
     * 持续监听，检索代码变化，这里只监听文件或目录的修改事件
     *
     * @param file 被监听的目录或者文件
     */
    public static void listener(File file) {
        WatchMonitor watchMonitor = WatchMonitor.create(file, WatchMonitor.ENTRY_MODIFY);
        watchMonitor.setWatcher(new Watcher() {
            @Override
            public void onCreate(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("创建：{}-> {}", currentPath, obj);
            }

            @SneakyThrows
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("修改：{}-> {}", currentPath, obj);
                //TODO 重新创建classloader 加载对应类
//                SysApplication.restart();
            }

            @Override
            public void onDelete(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("删除：{}-> {}", currentPath, obj);
            }

            @Override
            public void onOverflow(WatchEvent<?> event, Path currentPath) {
                Object obj = event.context();
                Console.log("Overflow：{}-> {}", currentPath, obj);
            }
        });
        //设置监听目录的最大深入，目录层级大于制定层级的变更将不被监听，默认只监听当前层级目录
        watchMonitor.setMaxDepth(3);
        //启动监听
        watchMonitor.start();
    }



}
