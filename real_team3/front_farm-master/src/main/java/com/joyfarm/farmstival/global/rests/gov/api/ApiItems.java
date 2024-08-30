package com.joyfarm.farmstival.global.rests.gov.api;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ApiItems {
    private List<Map<String, String>> item;
}
