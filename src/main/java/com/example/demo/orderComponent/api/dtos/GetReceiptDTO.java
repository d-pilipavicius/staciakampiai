package com.example.demo.orderComponent.api.dtos;

import com.example.demo.orderComponent.api.dtos.OrderHelperDTOs.OrderReceiptDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetReceiptDTO {
    private OrderReceiptDTO orderReceipt;
}
