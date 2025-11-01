package com.shopverse.customerservice.dto;

import com.shopverse.customerservice.model.Address;

public record CustomerResponse(
    String id,
    String firstname,
    String lastname,
    String email,
    Address address
) {

}
