package com.product.product.APIcall;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApiEndereco {

    private static final String API_URL = "http://26.211.150.30:3333/adress/";

    public void getProdutos() {
        RestTemplate restTemplate = new RestTemplate();

        // Fazendo a chamada GET
        ResponseEntity<String> response = restTemplate.getForEntity(API_URL, String.class);

        // Exibindo o resultado
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Resposta: " + response.getBody());
        } else {
            System.out.println("Erro ao chamar a API. Código: " + response.getStatusCode());
        }
    }
}