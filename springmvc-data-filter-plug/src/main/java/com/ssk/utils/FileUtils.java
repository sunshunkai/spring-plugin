package com.ssk.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
@Slf4j
public class FileUtils {

    private final static Map<String,String> ruleMap = new HashMap();

    /**
     * 获取文件内容并保存
     * @param filePath
     * @return
     */
    public static String getContentAndSave(String filePath){
        String rule = ruleMap.get(filePath);
        if( !StringUtils.isEmpty(rule) ){
            return rule;
        }
        String content = getContent(filePath);
        ruleMap.put(filePath,content);
        return content;
    }

    /**
     * 从文件中获取内容
     * @param filePath
     * @return
     */
    public static String getContent(String filePath){
        String res = "";
        if(StringUtils.isEmpty(filePath)){
            log.info("文件路径不能为空");
            return res;
        }
        try {
            Resource resource = new ClassPathResource(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(),"UTF-8"));
            StringBuffer sb = new StringBuffer();
            String str = "";
            while((str=br.readLine())!=null) {
                sb.append(str);
            }
            res = sb.toString();
        } catch (Exception e) {
            log.info("读取文件{}时发生异常",filePath);
            e.printStackTrace();
        }
        return res;
    }
}
