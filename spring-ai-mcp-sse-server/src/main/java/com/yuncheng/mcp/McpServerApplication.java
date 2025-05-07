package com.yuncheng.mcp;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class McpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpServerApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider weatherTools(WeatherService weatherService) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherService)
                .build();
    }

    @Bean
    public CommandLineRunner toolCheck(ToolCallbackProvider toolCallbackProvider) {
        return args -> {
            System.out.println(">> 注册的工具名称如下：");
            Arrays.stream(toolCallbackProvider.getToolCallbacks()).forEach(tool -> {
                System.out.println(" - " + tool.getName());
            });
        };
    }

}