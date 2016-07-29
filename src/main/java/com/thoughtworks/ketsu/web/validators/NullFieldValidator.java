package com.thoughtworks.ketsu.web.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NullFieldValidator {
    public Map<String, List> getNullFields(List<String> toValidates, Map<String, Object> info) {
        List<Map<String, Object>> nullFields = new ArrayList<>();
        for(String toValidate: toValidates) {
            if(info.get(toValidate) == null) {
                nullFields.add(new HashMap(){{
                    put("field", toValidate);
                    put("message", toValidate + " can not be empty.");
                }});
            }
        }

        if(nullFields.size() == 0)  return null;
        return new HashMap() {{
           put("items", nullFields);
        }};
    }
}
