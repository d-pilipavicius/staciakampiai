package com.example.demo.productComponent.helper.validator;

import com.example.demo.helper.CustomExceptions.HTTPExceptions.HTTPExceptionJSON;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PutModifierDTO;
import com.example.demo.productComponent.repository.ProductModifierRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Component
public class ProductModifierValidator {

    private static final Logger logger = LoggerFactory.getLogger(ProductModifierValidator.class);

    private final ProductModifierRepository productModifierRepository;

    public boolean modifiersExist(List<UUID> modifierIds) {
         long count = productModifierRepository.countByIdIn(modifierIds);
         return count == modifierIds.size();
    }

    public void modifierExists(UUID modifierId) {
        if (!productModifierRepository.existsById(modifierId)) {
            logger.error("Modifier with id {} does not exist", modifierId);
            throw new HTTPExceptionJSON(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    "Invalid data",
                    "Modifier with id " + modifierId + " does not exist"
            );
        }
    }
}
