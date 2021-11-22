package io.terminus.gaia.cloud.loadbalancer.context;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/18
 */
public class HttpHeaderContext {

    /**
     * 当前请求标识
     */
    private static final InheritableThreadLocal<String>  instance = new InheritableThreadLocal();

    public static void set(String logicFlow){
        instance.set(logicFlow);
    }

    public static String get(){
        return instance.get();
    }

    public static void remove(){
        instance.remove();
    }

}
