package com.samirdev.spring_chat_app.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

record HelloMessage(String name) {}

record Greeting(String content) {}

@Controller
public class GreetingController {

    @MessageMapping("/hello") // Recebe mensagens no destino /app/hello
    @SendTo("/topic/greetings") // Envia o retorno para todos os inscritos em /topic/greetings
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // Simula um delay
        return new Greeting(message.name());
    }
}
