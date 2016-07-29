package com.thoughtworks.ketsu.web.validators;

import com.thoughtworks.ketsu.domain.products.ProductRepository;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderValidator extends NullFieldValidator {
    @Inject
    ProductRepository productRepository;

    public Map<String, List> getNullFieldsMap(Map<String, Object> info) {
        List<Map> nullFieldsList = getNullFieldsList(Arrays.asList("name", "address", "phone", "order_items"), info);

        Object order_items = info.get("order_items");
        if(order_items != null) {
            for(Map item: (List<Map>)order_items) {
                nullFieldsList.addAll(getNullFieldsList(Arrays.asList("product_id", "quantity"), item));

                Object product_id = item.get("product_id");
                if(product_id!=null && !productRepository.findById(product_id.toString()).isPresent()) {
                    addErrorItems(nullFieldsList, "product_id", "product_id is invalid.");
                }
            }
        }

        if(nullFieldsList.size() == 0)  return null;
        return new HashMap() {{
            put("items", nullFieldsList);
        }};
    }

}
