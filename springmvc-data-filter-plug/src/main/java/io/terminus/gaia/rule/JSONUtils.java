package io.terminus.gaia.rule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
public class JSONUtils {

    public static JSONObject changeJSONObject(JSONObject object,Map<String, List<RulesItem>> rulesItem){
        JSONObject result = new JSONObject(object.size());
        object.forEach((key,value)->{
            if(value instanceof JSONObject){
                result.put(key,changeJSONObject((JSONObject) value,rulesItem));
            }else if(value instanceof JSONArray){
                result.put(key,changeJSONArray((JSONArray)value,rulesItem));
            }else{
                //这里进行字段脱敏
                result.put(key,desensitization(key,String.valueOf(value),rulesItem));
            }
        });
        return result;
    }


    /**
     * 递归遍历JOSNArray
     * @param jsonArray
     * @return
     */
    public static JSONArray changeJSONArray(JSONArray jsonArray,Map<String, List<RulesItem>> rulesItem){
        if(jsonArray!=null){
            JSONArray array = new JSONArray(jsonArray.size());
            jsonArray.forEach(object->{
                if(object instanceof JSONObject){
                    array.add(changeJSONObject((JSONObject)object,rulesItem));
                }else{
                    array.add(object);
                }
            });
            return  array;
        }else {
            return null;
        }
    }

    public static String desensitization(String key,String value,Map<String,List<RulesItem>> ruleMatch){
        if(null == value || "".equals(value)){
            return value;
        }
        if(ruleMatch.containsKey(key)){
            List<RulesItem> arrays = ruleMatch.get(key);
            // 这里arrays是 JSONArray类型需要在再转一次
            List<RulesItem> rulesItems = JSON.parseArray(JSON.toJSONString(arrays), RulesItem.class);
            for (RulesItem temp : rulesItems) {
                value = DesensiStringUtils.desensiString(value, temp);
            }
        }
        return value;
    }





    public static void main(String[] args) {

        String oriJSON = "{\n" +
                "    \"username\":\"abcdefghijklmn\",\n" +
                "    \"password\":\"helloWorld\",\n" +
                "    \"telephone\":\"17789098907\",\n" +
                "    \"idCard\":\"441279199012043817\",\n" +
                "    \"innerUser\":[\n" +
                "        {\n" +
                "            \"telephone\":\"18979089876\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
//        System.out.println(JSONUtils.changeJSONObject(JSON.parseObject(oriJSON)).toJSONString());

    }

}
