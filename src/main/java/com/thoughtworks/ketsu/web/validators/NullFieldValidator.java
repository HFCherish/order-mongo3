package com.thoughtworks.ketsu.web.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NullFieldValidator {
    public Map<String, List> getNullFieldsMap(List<String> toValidates, Map<String, Object> info) {
        List<Map> nullFields = getNullFieldsList(toValidates, info);

        if(nullFields.size() == 0)  return null;
        return new HashMap() {{
           put("items", nullFields);
        }};
    }

    public List<Map> getNullFieldsList(List<String> toValidates, Map<String, Object> info) {
        List<Map> nullFields = new ArrayList<>();
        for(String toValidate: toValidates) {
            if(info.get(toValidate) == null) {
                addErrorItems(nullFields, toValidate, toValidate + " can not be empty.");
            }
        }
        return nullFields;
    }

    void addErrorItems(List<Map> errorItemList, String errorField, String errorMessage) {
        errorItemList.add(new HashMap(){{
            put("field", errorField);
            put("message", errorMessage);
        }});
    }
}
