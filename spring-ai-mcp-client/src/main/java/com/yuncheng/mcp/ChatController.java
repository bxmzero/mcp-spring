package com.yuncheng.mcp;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/chat")
public class ChatController {

    private final ChatClient client;


    private final ToolCallbackProvider toolCallbackProvider;

    public ChatController(ChatClient.Builder builder, ToolCallbackProvider toolCallbackProvider) {
        this.client = builder.build();
        this.toolCallbackProvider = toolCallbackProvider;
    }



    @RequestMapping(value = "sse",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@RequestParam String message) {
        Flux<String> content = this.client.prompt().user(message).stream().content();

        return content.concatWith(Flux.just("[complete]"));


    }




}
