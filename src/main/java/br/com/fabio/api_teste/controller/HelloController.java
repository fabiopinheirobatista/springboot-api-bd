package br.com.fabio.api_teste.controller;

import br.com.fabio.api_teste.service.HelloService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class HelloController {

    private final HelloService helloService;

    @GetMapping("/hello")
    public String hello() {
        return helloService.getHelloMessage();
    }
}
