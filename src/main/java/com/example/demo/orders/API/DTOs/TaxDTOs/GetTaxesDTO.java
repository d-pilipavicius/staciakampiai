package com.example.demo.orders.API.DTOs.TaxDTOs;

<<<<<<< HEAD
import com.example.demo.orders.API.DTOs.TaxDTOs.TaxDTOsObjects.FullTax;
=======
import com.example.demo.orders.API.DTOs.BaseDTOs.GetBaseDTO;
import com.example.demo.orders.API.DTOs.TaxDTOs.TaxDTOsObjects.TaxObject;
import lombok.AllArgsConstructor;
>>>>>>> 04c1e4641a5e9c3ebbe6c4e06a668b5a8f806baf
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTaxesDTO extends GetBaseDTO {
    private List<TaxObject> items;
}
