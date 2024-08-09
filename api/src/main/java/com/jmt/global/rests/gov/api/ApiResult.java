package com.jmt.global.rests.gov.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResult {
    private Map<String, String> header;
    private List<Map<String, String>> body;
}
