package io.terminus.gaia.context;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
public class LogicFlowContext {

    /**
     * 当前请求标识
     */
    private static final ThreadLocal<String> instance = new ThreadLocal();

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
