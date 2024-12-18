package com.example.demo.discountComponent.api.endpoints;

import com.example.demo.discountComponent.api.dtos.DiscountDTO;
import com.example.demo.discountComponent.api.dtos.GetDiscountsDTO;
import com.example.demo.discountComponent.api.dtos.PutDiscountDTO;
import com.example.demo.discountComponent.api.dtos.PostDiscountDTO;
import com.example.demo.discountComponent.applicationServices.DiscountApplicationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/discounts/{businessId}")
public class DiscountsController {
    private DiscountApplicationService discountAppService;

    @PostMapping
    public ResponseEntity<DiscountDTO> createDiscount (@NotNull @Valid @RequestBody PostDiscountDTO postDiscountDTO){
        DiscountDTO createdDiscountDTO = discountAppService.createDiscount(postDiscountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscountDTO);
    }

    @GetMapping
    public ResponseEntity<GetDiscountsDTO> getDiscounts (@PathVariable UUID businessId, @NotNull @RequestParam int pageNumber, @NotNull @RequestParam int pageSize){
        GetDiscountsDTO discountsDTO = discountAppService.getDiscountsByBusinessId(businessId, pageNumber, pageSize, false);
        return ResponseEntity.status(HttpStatus.CREATED).body(discountsDTO);
    }

    @GetMapping("/giftcards")
    public ResponseEntity<GetDiscountsDTO> getGiftcards(@PathVariable UUID businessId, @NotNull @RequestParam int pageNumber, @NotNull @RequestParam int pageSize){
        GetDiscountsDTO discountsDTO = discountAppService.getDiscountsByBusinessId(businessId, pageNumber, pageSize, true);
        return ResponseEntity.status(HttpStatus.CREATED).body(discountsDTO);
    }

    @PutMapping("/{discountId}")
    public  ResponseEntity<DiscountDTO> updateDiscount(@PathVariable UUID discountId,
                                                       @NotNull @Valid @RequestBody PutDiscountDTO putDiscountDTO){
        DiscountDTO updatedDiscountDTO = discountAppService.updateDiscount(discountId, putDiscountDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDiscountDTO);
    }

    @PutMapping("/{discountId}/increaseUsage")
    public ResponseEntity<DiscountDTO> increaseUsageCount(@PathVariable UUID discountId){
        DiscountDTO increasedUsageDiscountDTO = discountAppService.updateUsage(discountId);
        return ResponseEntity.status(HttpStatus.OK).body(increasedUsageDiscountDTO);
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<Object> deleteDiscount(@PathVariable UUID discountId){
         discountAppService.deleteDiscountById(discountId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
