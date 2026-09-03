package com.ai.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.model.AssessmentRequest;
import com.ai.service.OllamaService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AssessmentController {

    private final OllamaService service;

    @PostMapping("/assess")
    public String assess(@RequestBody AssessmentRequest request) {
        
        return service.assess(request);
    }
    
}
