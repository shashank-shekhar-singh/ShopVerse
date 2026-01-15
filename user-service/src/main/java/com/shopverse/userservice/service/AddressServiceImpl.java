package com.shopverse.userservice.service;

import com.shopverse.userservice.domain.entity.Address;
import com.shopverse.userservice.dto.AddressRequest;
import com.shopverse.userservice.dto.AddressResponse;
import com.shopverse.userservice.exception.AddressNotFoundException;
import com.shopverse.userservice.exception.UserNotFoundException;
import com.shopverse.userservice.mapper.AddressMapper;
import com.shopverse.userservice.repository.AddressRepository;
import com.shopverse.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.addressMapper = addressMapper;
    }


    @Override
    public AddressResponse addAddressToUser(UUID userId, AddressRequest request) {
        validateUserExists(userId);

        // If request.default=true, unset existing default for same type
        if(request.isDefault()) {
            addressRepository.unsetDefaultAddress(userId, request.type());
        }

        Address address = addressRepository.save(addressMapper.toEntity(request));
        return addressMapper.toResponseDTO(address);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<AddressResponse> getAddressesByUserId(UUID userId) {
        validateUserExists(userId);
        return addressRepository.findByUserId(userId).stream()
                .map(addressMapper::toResponseDTO)
                .toList();
    }

    @Override
    public AddressResponse updateAddress(UUID userId, UUID addressId, AddressRequest request) {
        return null;
    }

    @Override
    public void deleteAddress(UUID userId, UUID addressId) {
        validateUserExists(userId);
        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new AddressNotFoundException(addressId));
        addressRepository.delete(address);
    }

    private void validateUserExists(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
