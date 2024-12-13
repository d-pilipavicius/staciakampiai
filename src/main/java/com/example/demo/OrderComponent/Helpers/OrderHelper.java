package com.example.demo.OrderComponent.Helpers;

import com.example.demo.helper.enums.Currency;
import com.example.demo.OrderComponent.Domain.Entities.OrderItem;
import com.example.demo.OrderComponent.Domain.Entities.OrderItemModifier;
import com.example.demo.OrderComponent.Repositories.IOrderItemModifierRepository;

import java.math.BigDecimal;
import java.util.List;

public class OrderHelper {
    public static BigDecimal calculateOriginalPrice(List<OrderItem> items, IOrderItemModifierRepository orderItemModifierRepository) {
        return items.stream()
                .map(item -> {
                    BigDecimal itemPrice = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

                    List<OrderItemModifier> modifiers = orderItemModifierRepository.findByOrderItemId(item.getId());
                    BigDecimal modifiersPrice = modifiers.stream()
                            .map(modifier -> modifier.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return itemPrice.add(modifiersPrice);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static Currency determineCurrency(List<OrderItem> items) {
        return items.isEmpty() ? Currency.EUR : items.get(0).getCurrency();
    }
}