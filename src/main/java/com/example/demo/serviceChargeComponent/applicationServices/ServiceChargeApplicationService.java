package com.example.demo.serviceChargeComponent.applicationServices;

import com.example.demo.serviceChargeComponent.api.dtos.GetServiceChargesDTO;
import com.example.demo.serviceChargeComponent.api.dtos.PutServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.PostServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.ServiceChargeHelperDTOs.ServiceChargeDTO;
import com.example.demo.serviceChargeComponent.domain.services.ServiceChargeService;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ServiceChargeApplicationService {

    private final ServiceChargeService serviceChargeService;

    public ServiceChargeDTO createServiceCharge(PostServiceChargeDTO postServiceChargeDTO) {
        return serviceChargeService.createServiceCharge(postServiceChargeDTO);
    }

    public GetServiceChargesDTO getAllServiceCharges(int page, int size) {
        return serviceChargeService.getServiceCharges(page, size);
    }


    public ServiceChargeDTO updateServiceCharge(PutServiceChargeDTO patchServiceChargeDTO, UUID id) {
        return serviceChargeService.updateServiceCharge(patchServiceChargeDTO, id);
    }


    public void deleteServiceCharge(UUID id) {
        serviceChargeService.deleteServiceCharge(id);
    }
}
