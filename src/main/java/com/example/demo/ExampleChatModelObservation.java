package com.example.demo;

import io.micrometer.core.instrument.Clock;
import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;

import java.util.List;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class ExampleChatModelObservation {
    public static void main(String[] args) {
        ObservationRegistry registry = ObservationRegistry.create();
        registry.observationConfig().observationHandler(new LoggingObservationListener(Clock.SYSTEM));
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder().withModel("gpt-4o").build();
        OpenAiChatModel openAiChatModel = new OpenAiChatModel(new OpenAiApi("https://api.gogoai.cloud", "sk-xxx"),chatOptions,null, List.of(), RetryUtils.DEFAULT_RETRY_TEMPLATE,registry);
        ChatClient chatClient = ChatClient.builder(openAiChatModel).build();
        String chatResponse = chatClient.prompt().user("hi").call().content();
        System.out.println(chatResponse);
    }
}
