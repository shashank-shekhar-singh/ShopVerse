package com.shopverse.userservice.service;

import com.shopverse.userservice.dto.AddressRequest;
import com.shopverse.userservice.dto.AddressResponse;

import java.util.List;
import java.util.UUID;

public interface AddressService {
    AddressResponse addAddressToUser(UUID userId, AddressRequest request);
    List<AddressResponse> getAddressesByUserId(UUID userId);
    AddressResponse updateAddress(UUID userId, UUID addressId, AddressRequest request);
    void deleteAddress(UUID userId, UUID addressId);
}
