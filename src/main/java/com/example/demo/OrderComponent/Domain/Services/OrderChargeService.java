package com.example.demo.OrderComponent.Domain.Services;

import com.example.demo.OrderComponent.API.DTOs.AppliedServiceChargeDTO;
import com.example.demo.OrderComponent.Domain.Entities.AppliedServiceCharge;
import com.example.demo.OrderComponent.Domain.Entities.Order;
import com.example.demo.OrderComponent.Helpers.Mappers.OrderMapper;
import com.example.demo.OrderComponent.Repositories.IAppliedServiceChargeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderChargeService {
    private final IAppliedServiceChargeRepository appliedServiceChargeRepository;

    public List<AppliedServiceCharge> saveServiceCharges(List<AppliedServiceChargeDTO> charges, Order order) {
        if (charges == null || charges.isEmpty()) {
            return List.of();
        }

        List<AppliedServiceCharge> appliedCharges = charges.stream()
                .map(dto -> OrderMapper.mapToAppliedServiceCharge(dto, order))
                .collect(Collectors.toList());
        appliedServiceChargeRepository.saveAll(appliedCharges);
        return appliedCharges;
    }

    public void updateServiceCharges(List<AppliedServiceChargeDTO> charges, Order order) {
        appliedServiceChargeRepository.deleteByOrderId(order.getId());
        saveServiceCharges(charges, order);
    }

    public List<AppliedServiceCharge> findAppliedServiceChargesByOrderId(UUID orderId) {
        return appliedServiceChargeRepository.findByOrderId(orderId);
    }
}
