package com.ai.service;

import com.ai.model.AssessmentRequest;

public interface OllamaService {
    
    String assess(AssessmentRequest request);
}
