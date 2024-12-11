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
@RequestMapping("/v1/discounts")
public class DiscountsController {
    private DiscountApplicationService discountAppService;

    @PostMapping
    public ResponseEntity<DiscountDTO> createDiscount (@NotNull @RequestParam UUID employeeId, @NotNull @Valid @RequestBody PostDiscountDTO postDiscountDTO){
        DiscountDTO createdDiscountDTO = discountAppService.createDiscount(employeeId, postDiscountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscountDTO);
    }

    @GetMapping
    public ResponseEntity<GetDiscountsDTO> getDiscounts (@NotNull @RequestParam int pageNumber, @NotNull @RequestParam int pageSize, @NotNull @RequestParam UUID businessId){
        GetDiscountsDTO discountsDTO = discountAppService.getDiscountsByBusinessId(businessId, pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.CREATED).body(discountsDTO);
    }

    @PutMapping("/{discountId}")
    public  ResponseEntity<DiscountDTO> updateDiscount(@NotNull @PathVariable UUID discountId, @NotNull @RequestParam UUID  employeeId,
                                                       @NotNull @Valid @RequestBody PutDiscountDTO putDiscountDTO){
        DiscountDTO updatedDiscountDTO = discountAppService.updateDiscount(discountId, employeeId, putDiscountDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDiscountDTO);
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<Object> deleteDiscount(@NotNull @PathVariable UUID discountId, @NotNull @RequestParam UUID employeeId){
         discountAppService.deleteDiscountById(discountId, employeeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
