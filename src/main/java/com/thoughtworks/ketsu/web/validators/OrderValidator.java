package com.thoughtworks.ketsu.web.validators;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OrderValidator extends NullFieldValidator {
    public Map<String, List> getNullFields(Map<String, Object> info) {
        return super.getNullFields(Arrays.asList("name", "address", "phone"), info);
    }
}
