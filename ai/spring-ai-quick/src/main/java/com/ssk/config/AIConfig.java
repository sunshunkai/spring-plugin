package com.ssk.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.DefaultChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    // 配置ChatMemory（默认使用内存存储）
    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder().build();
    }

    // 配置ChatClient并集成ChatMemory
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder,ChatMemory chatMemory) {
        return chatClientBuilder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory)
                                .build()
                )
                .build();
    }
}
