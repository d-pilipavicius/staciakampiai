package com.example.demo.serviceChargeComponent.helper.validator;

import com.example.demo.serviceChargeComponent.repository.ServiceChargeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
public class ServiceChargeValidator {

    private final ServiceChargeRepository serviceChargeRepository;

    public void validateServiceChargeIds(List<UUID> serviceChargeIds) {
        if(!serviceChargeIds.stream().allMatch(serviceChargeRepository::existsById))
            throw new IllegalArgumentException("ServiceCharge not found");
    }
}
