package com.example.demo.serviceChargeComponent.applicationServices;

import com.example.demo.serviceChargeComponent.api.dtos.GetServiceChargesDTO;
import com.example.demo.serviceChargeComponent.api.dtos.PutServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.PostServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.ResponseServiceChargeDTO;
import com.example.demo.serviceChargeComponent.api.dtos.ServiceChargeHelperDTOs.ServiceChargeDTO;
import com.example.demo.serviceChargeComponent.domain.services.ServiceChargeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ServiceChargeApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceChargeApplicationService.class);

    private final ServiceChargeService serviceChargeService;

    public ServiceChargeApplicationService(ServiceChargeService serviceChargeService) {
        this.serviceChargeService = serviceChargeService;
    }

    @Transactional
    public ServiceChargeDTO createServiceCharge(PostServiceChargeDTO postServiceChargeDTO) {
        return serviceChargeService.createServiceCharge(postServiceChargeDTO);
    }

    public GetServiceChargesDTO getAllServiceCharges() {
        return serviceChargeService.getServiceCharges();
    }

    public GetServiceChargesDTO getAllServiceCharges(int page, int size) {
        return serviceChargeService.getServiceCharges(page, size);
    }

    @Transactional
    public ResponseServiceChargeDTO updateServiceCharge(PutServiceChargeDTO patchServiceChargeDTO, UUID id) {
        return serviceChargeService.updateServiceCharge(patchServiceChargeDTO, id);
    }

    @Transactional
    public void deleteServiceCharge(UUID id) {
        serviceChargeService.deleteServiceCharge(id);
    }
}
