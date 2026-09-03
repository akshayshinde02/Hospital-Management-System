package com.ai.service.impl;

import org.springframework.stereotype.Service;

import com.ai.client.OllamaClient;
import com.ai.dto.OllamaResponse;
import com.ai.model.AssessmentRequest;
import com.ai.service.OllamaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OllamaServiceImpl implements OllamaService{

    private final OllamaClient ollamaClient;

    @Override
    public String assess(AssessmentRequest request) {

        String prompt = buildPrompt(request);
        
        OllamaResponse response = ollamaClient.generate(prompt);

        return response.getResponse();
    }

    private String buildPrompt(AssessmentRequest request){

        return String.format(
            """
            You are an AI-assisted symptom assessment system.

            Analyze the symptoms provided by the patient.

            Do not provide a definitive diagnosis.
            Do not prescribe medication.

            Patient symptoms:
            %s

            Provide possible conditions,
            recommended department,
            urgency guidance,
            and advise consultation with a doctor.
            """,
            request.getSymptoms()
    );
    }
    
}
