package com.example.demo.discountComponent.api.endpoints;

import com.example.demo.discountComponent.api.dtos.GetDiscountsDTO;
import com.example.demo.discountComponent.api.dtos.PatchDiscountDTO;
import com.example.demo.discountComponent.api.dtos.PostDiscountDTO;
import com.example.demo.discountComponent.api.dtos.ResponseDiscountDTO;
import com.example.demo.discountComponent.applicationServices.DiscountApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/discounts")
public class DiscountsController {
    private DiscountApplicationService discountAppService;

    @Autowired
    public DiscountsController(DiscountApplicationService discountAppService){
        this.discountAppService = discountAppService;
    }

    @PostMapping
    public ResponseEntity<Object> createDiscount (@RequestParam UUID employeeId, @Valid @RequestBody PostDiscountDTO postDiscountDTO){
        ResponseDiscountDTO createdDiscount = discountAppService.createDiscount(employeeId, postDiscountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscount);
    }

    @GetMapping
    public ResponseEntity<Object> getDiscounts (@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam UUID businessId){
        GetDiscountsDTO discounts = discountAppService.getDiscountsByBusinessId(businessId, pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.CREATED).body(discounts);
    }

    @PatchMapping("/{discountId}")
    public  ResponseEntity<Object> updateDiscount(@PathVariable UUID discountId, @RequestParam UUID  employeeId,
                                                  @Valid @RequestBody PatchDiscountDTO patchDiscountDTO){
        ResponseDiscountDTO updatedDiscount = discountAppService.updateDiscount(discountId, employeeId, patchDiscountDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDiscount);
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<Object> deleteDiscount(@PathVariable UUID discountId, @RequestParam UUID employeeId){
         discountAppService.deleteDiscountbyId(discountId, employeeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
