package com.jmt.ai.controllers;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RequestMessage {
    private String model = "mistralai/Mistral-Nemo-Instruct-2407";
    private int max_tokens = 10000;
    private boolean stream;
    @NonNull
    private List<Map<String, String>> messages;
}
