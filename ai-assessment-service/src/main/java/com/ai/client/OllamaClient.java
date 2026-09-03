package com.ai.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.ai.dto.OllamaRequest;
import com.ai.dto.OllamaResponse;

@Component
public class OllamaClient {
    
    private RestClient restClient;
    private final String model = "llama3.2";

    public OllamaClient(){
        this.restClient = RestClient.builder()
                    .baseUrl("http://localhost:11434")
                    .build();
    }

    public OllamaResponse generate(String prompt){
         
        OllamaRequest request = new OllamaRequest();
        request.setModel(model);
        request.setPrompt(prompt);
        request.setStream(false);

        return restClient.post()
        .uri("/api/generate")
        .body(request)
        .retrieve()
        .body(OllamaResponse.class);
    }
}
