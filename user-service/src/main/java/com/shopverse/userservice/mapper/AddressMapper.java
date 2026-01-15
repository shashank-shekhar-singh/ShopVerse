package com.shopverse.userservice.mapper;

import com.shopverse.userservice.domain.entity.Address;
import com.shopverse.userservice.dto.AddressRequest;
import com.shopverse.userservice.dto.AddressResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressRequest request);
    AddressResponse toResponseDTO(Address address);
}
