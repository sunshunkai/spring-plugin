package com.ssk.tools;

import org.springframework.ai.tool.annotation.Tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 *
 */
public class FoodTools {

    @Tool(description = "查询今天吃了哪些食物")
    String getCurrentDateTime() {
        List<String> food = new ArrayList<>();
        food.add("苹果");
        food.add("香蕉");
        food.add("例子");
        food.add("西瓜");
        return food.get(new Random().nextInt(food.size()));
    }

}
