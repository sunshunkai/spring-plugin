package com.ssk.core.json.custome;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
public class CustomFilter {

    private static final String ARRAY = "[*]";


    public Object exec(JSONObject jsonObject, List<String> ruleList){

        List<String[]> split = split(ruleList);

        for (String[] strings : split) {
            
        }

        return jsonObject;
    }

    @Data
    public static class Rule{

        /**
         * a.b.c
         * a[*].b.c
         */
        List<String> ruleList;

    }

    private List<String[]> split(List<String> ruleList){

        List<String[]> rule = new ArrayList<>(ruleList.size());

        for (String s : ruleList) {
            String[] split = s.split(".");
            rule.add(split);
        }
        return rule;
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
