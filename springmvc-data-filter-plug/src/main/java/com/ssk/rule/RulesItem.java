package com.ssk.rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/11/14
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RulesItem implements Serializable {

    /**
     * 脱敏开始位置，从1开始
     */
    private int start;
    /**
     * 脱敏结束位置
     */
    private int end;



}
