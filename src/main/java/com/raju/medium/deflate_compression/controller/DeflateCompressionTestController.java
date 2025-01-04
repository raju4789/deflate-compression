package com.raju.medium.deflate_compression.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DeflateCompressionTestController {

    @GetMapping("/data")
    public Map<String, String> getData() {
        Map<String, String> data = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            data.put("key" + i, "value" + i);
        }
        return data;
    }
}