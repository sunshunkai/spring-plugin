package com.ssk.controller;

import com.ssk.tools.FoodTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MyController {

    String conversationId = "007";


    @Autowired
    private ChatClient chatClient;

    @Autowired
    private ChatMemory chatMemory;



    @GetMapping("/ai")
    public String generation(String userInput) {
        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

    @GetMapping("/ai/simple")
    public Map<String, String> completion(@RequestParam(value = "message", defaultValue = "讲一个笑话") String message) {
        return Map.of("completion", this.chatClient.prompt().user(message).call().content());
    }

    /**
     * 聊天记忆
     */
    @GetMapping("/ai/memory")
    public String memory(String userInput) {

        UserMessage userMessage = new UserMessage(userInput);
        chatMemory.add(conversationId,userMessage);
        return chatClient.prompt(new Prompt(chatMemory.get(conversationId))).call().content();

    }

    /**
     * Tools
     */
    @GetMapping("/ai/tools")
    public String chatWithTool(String userInput) {
        System.out.println("tools");
        return chatClient.prompt(userInput).tools(new FoodTools()).call().content();

    }


}
