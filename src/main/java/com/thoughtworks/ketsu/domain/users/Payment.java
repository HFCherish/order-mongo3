package com.thoughtworks.ketsu.domain.users;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payment {
    @JsonProperty("pay_type")
    PayType payType;
    @JsonProperty("amount")
    double amount;
    Order order;

    public Payment(PayType payType, double amount, Order order) {
        this.payType = payType;
        this.amount = amount;
        this.order = order;
    }

//    public void setOrder(Order order) {
//        this.order = order;
//    }
}
