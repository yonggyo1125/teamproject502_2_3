package com.jmt.global.rests.gov.api;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ApiResult {
    private Map<String, String> header;
    private List<Map<String, String>> body;
}
