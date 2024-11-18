package com.example.demo.orders.API.DTOs.OrderDTOs;

import com.example.demo.orders.API.DTOs.OrderDTOs.OrderHelperDTOs.OrderReceiptDTO;
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
