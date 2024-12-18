package com.example.demo.taxComponent.validator;

import com.example.demo.taxComponent.repository.TaxRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaxValidator {
    private final TaxRepository taxRepository;
}
