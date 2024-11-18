package com.example.demo.orders.domain.services;

import com.example.demo.orders.repository.ServiceChargeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ServiceChargeService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceChargeService.class);

    private final ServiceChargeRepository serviceChargeRepository;

    public ServiceChargeService(ServiceChargeRepository serviceChargeRepository) {
        this.serviceChargeRepository = serviceChargeRepository;
    }


}
