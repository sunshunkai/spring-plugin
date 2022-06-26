package com.ssk.hot.deployment.entity;

/**
 * @author 惊云
 * @date 2021/12/18 20:05
 */
public class ClassFileEntity {

    /**
     * 类的名称
     */
    private String name;

    /**
     * 最后被更改的时间 文件最后更改的时间
     */
    private long lastModified;

    public ClassFileEntity(String name, long lastModified) {
        this.name = name;
        this.lastModified = lastModified;
    }


    public String getName() {
        return name;
    }


    public long getLastModified() {
        return lastModified;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
