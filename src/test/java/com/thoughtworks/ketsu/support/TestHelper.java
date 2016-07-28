package com.thoughtworks.ketsu.support;

import java.util.HashMap;
import java.util.Map;

public class TestHelper {

    public static Map<String, Object> productJsonForTest() {
        return new HashMap(){{
            put("name", "Imran");
            put("description", "teacher");
            put("price", 12.1);
        }};
    }
}
