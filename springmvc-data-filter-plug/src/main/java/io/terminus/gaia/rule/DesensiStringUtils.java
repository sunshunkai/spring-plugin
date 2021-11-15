package io.terminus.gaia.rule;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/15
 */
@Slf4j
public class DesensiStringUtils {

    /**
     * 对原始字符串进行替换操作
     * @param value 字段值
     * @param item 屏蔽规则
     * @return 屏蔽后的字段值
     */
    public static String desensiString(String value, RulesItem item){
        StringBuilder sb = new StringBuilder(value);
        int length = value.length();
        int filledLength = item.getEnd() - item.getStart() + 1 ;
        //保护，当问文件中的配置不是正数时，赋默认值1
        if(item.getStart()<=0){
            item.setStart(1);
        }
        if(item.getEnd() <=0){
            item.setEnd(1);
        }
        //保护，当原始字符串太短时，特殊处理，不处理，或者全部替换成*;
        if(item.getStart()>sb.length()){
            return getFilledString('*',sb.length());
        }
        log.info("start:"+(item.getStart()-1)+"end:"+(item.getEnd()-1));
        sb.replace(item.getStart()-1,item.getEnd()-1,getFilledString('*', Math.min(filledLength, length)));
        return sb.toString();
    }

    /**
     * 生成固定长度的填充串
     * @param filledChar 填充串的字符
     * @param length 填充串的长度
     * @return 生成好的字符串
     */
    public static String getFilledString(char filledChar,int length){
        StringBuilder sb = new StringBuilder();
        for (int i= 0 ;i<length;i++){
            sb.append(filledChar);
        }
        return sb.toString();
    }
}
