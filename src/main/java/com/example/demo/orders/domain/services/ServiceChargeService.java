package com.example.demo.orders.domain.services;

import com.example.demo.helper.mapper.ServiceChargeMapper;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.orders.api.DTOs.ServiceChargeDTOs.GetServiceChargesDTO;
import com.example.demo.orders.api.DTOs.ServiceChargeDTOs.PatchServiceChargeDTO;
import com.example.demo.orders.api.DTOs.ServiceChargeDTOs.PostPatchReturnServiceChargeDTO;
import com.example.demo.orders.domain.entities.ServiceCharge;
import com.example.demo.orders.repository.ServiceChargeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.example.demo.orders.api.DTOs.ServiceChargeDTOs.PostServiceChargeDTO;
import com.example.demo.orders.api.DTOs.ServiceChargeDTOs.ServiceChargeDTOsObjects.FullServiceCharge;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceChargeService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceChargeService.class);

    private final ServiceChargeRepository serviceChargeRepository;

    public ServiceChargeService(ServiceChargeRepository serviceChargeRepository) {
        this.serviceChargeRepository = serviceChargeRepository;
    }

    public void createServiceCharge(PostServiceChargeDTO postServiceChargeDTO){
        serviceChargeRepository.save(
                Mapper.mapToModel(
                        postServiceChargeDTO,
                        ServiceChargeMapper.TO_MODEL
                )
        );
    }

    public GetServiceChargesDTO getServiceCharges(){
        List<FullServiceCharge> serviceCharges = serviceChargeRepository.findAll().stream()
                .map(ServiceChargeMapper.TO_DTO::map)
                .toList();

        // TODO: add constructor to GetServiceChargesDTO
        GetServiceChargesDTO getServiceChargesDTO = new GetServiceChargesDTO();
        getServiceChargesDTO.setItems(serviceCharges);

        return getServiceChargesDTO;
    }

    public GetServiceChargesDTO getServiceCharges(int page, int size){
        List<FullServiceCharge> serviceCharges = serviceChargeRepository.findAll(PageRequest.of(page, size)).stream()
                .map(ServiceChargeMapper.TO_DTO::map)
                .toList();

        // TODO: add constructor to GetServiceChargesDTO
        GetServiceChargesDTO getServiceChargesDTO = new GetServiceChargesDTO();
        getServiceChargesDTO.setItems(serviceCharges);
        getServiceChargesDTO.setCurrentPage(page);
        getServiceChargesDTO.setTotalItems(serviceCharges.size());
        getServiceChargesDTO.setTotalPages(size);

        return getServiceChargesDTO;
    }

    @Transactional
    public PostPatchReturnServiceChargeDTO updateServiceCharge(PatchServiceChargeDTO patchServiceChargeDTO, UUID id){
        Optional<ServiceCharge> serviceCharge = serviceChargeRepository.findById(id);

        if(serviceCharge.isEmpty()){
            logger.error("ServiceCharge with id {} not found", id);
            return null;
        }

        patchServiceChargeDTO.getTitle().ifPresent(serviceCharge.get()::setTitle);
        patchServiceChargeDTO.getValue().ifPresent(serviceCharge.get()::setValue);
        patchServiceChargeDTO.getValueType().ifPresent(serviceCharge.get()::setValueType);
        patchServiceChargeDTO.getCurrency().ifPresent(serviceCharge.get()::setCurrency);

        PostPatchReturnServiceChargeDTO newServiceChargeDTO = new PostPatchReturnServiceChargeDTO();
        newServiceChargeDTO.setServiceCharge(Mapper.mapToDTO(
                serviceChargeRepository.save(serviceCharge.get()),
                ServiceChargeMapper.TO_DTO
        ));

        return newServiceChargeDTO;
    }

    public void deleteServiceCharge(UUID id){
        serviceChargeRepository.deleteById(id);
    }
}
