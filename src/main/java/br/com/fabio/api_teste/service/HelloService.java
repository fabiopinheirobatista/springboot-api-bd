package br.com.fabio.api_teste.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String getHelloMessage() {
        return "<h1>E aí pessoal!<h1>";
    }

}
