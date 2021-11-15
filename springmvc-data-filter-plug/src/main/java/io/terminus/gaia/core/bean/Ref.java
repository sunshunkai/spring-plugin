package io.terminus.gaia.core.bean;


import java.lang.reflect.Field;
import java.util.List;

/**
 * 使用反射修改 Bean 的值
 *
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
public class Ref {

    private static final String ARRAY = "[*]";

    public Object exec(Object object, List<String[]> ruleList){

        Class<?> aClass = object.getClass();

        // 遍历每个规则
        for (String[] strings : ruleList) {
            // 遍历规则中的字段
        }
        return object;
    }


    /**
     * 获取到最底层字段
     */
    private void process(Object object, Class clazz, String[] fieldList) throws NoSuchFieldException, IllegalAccessException {
        Object currentObj = object;
        for (String field : fieldList) {
            currentObj = process(currentObj,currentObj.getClass(),field);
        }

    }

    /**
     * 获取当前字段值
     */
    private Object process(Object object, Class clazz, String field) throws NoSuchFieldException, IllegalAccessException {
        if(isArray(field)){
            // 集合
            Field currentField = clazz.getField(getArrayName(field));
            Object o = currentField.get(object);
        }else {

            Field currentField = clazz.getField(field);
            Object o = currentField.get(object);
        }
        return null;
    }

    /**
     * 给字段脱敏
     */
    private void setField(Object object,String field) throws NoSuchFieldException, IllegalAccessException {
        Class<?> aClass = object.getClass();
        Field updateField = aClass.getField(field);
        updateField.setAccessible(true);
        updateField.set(object,"*******");
    }

    /**
     * 判断当前自动是否是数据
     * @param filed
     * @return
     */
    private boolean isArray(String filed){
        return filed.contains(ARRAY);
    }

    /**
     * 获取数据下标
     * @param filed
     * @return
     */
    private String getArrayName(String filed){
        return filed.replaceAll(ARRAY,"");
    }




}
