package com.archeruu.ai.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiApi;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import retrofit2.Retrofit;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.theokanning.openai.service.OpenAiService.*;

/**
 * 调用openai API
 *
 * 确保7890接口可以访问国外网
 *
 * @author Archer
 */
public class ChatApiLocalProxyUtil {

    public static String chatApi(String content) {
        String key = "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";
        // 配置 V2ray 代理
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
        OkHttpClient client = defaultClient(key, Duration.ofMinutes(1))
                .newBuilder()
                .proxy(proxy)
                .build();

        // 创建 OpenAiService
        ObjectMapper mapper = defaultObjectMapper();
        Retrofit retrofit = defaultRetrofit(client, mapper);
        OpenAiApi api = retrofit.create(OpenAiApi.class);
        OpenAiService service = new OpenAiService(api);

        // 创建 ChatCompletionRequest
        ChatMessage chatMessage = new ChatMessage("user", content);
        List<ChatMessage> chatMessageList = new ArrayList<>();
        chatMessageList.add(chatMessage);
        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(chatMessageList)
                .model("gpt-3.5-turbo")

                .maxTokens(500)
                .temperature(0.2)
                .build();

        // 调用 OpenAiService
        ChatCompletionResult completionResult = service.createChatCompletion(completionRequest);
        String result = completionResult.getChoices().get(0).getMessage().getContent();
        if (result.startsWith("{")) {
            String error = "出了点小问题呢，换个问题试试。";
            return error;
        }

        return result.trim();
    }
}
