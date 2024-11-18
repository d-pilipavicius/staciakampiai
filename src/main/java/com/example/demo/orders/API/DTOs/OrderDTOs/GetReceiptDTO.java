package com.example.demo.orders.API.DTOs.OrderDTOs;

import com.example.demo.orders.API.DTOs.OrderDTOs.OrderDTOsObjects.FullReceipt;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetReceiptDTO {
    private FullReceipt orderReceipt;
}
