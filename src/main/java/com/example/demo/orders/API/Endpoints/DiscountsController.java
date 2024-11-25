package com.example.demo.orders.API.Endpoints;

import com.example.demo.helper.validator.ValidationForDifferentHTTPCodes;
import com.example.demo.orders.API.DTOs.DiscountDTO.GetDiscountsDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.PatchDiscountDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.PostDiscountDTO;
import com.example.demo.orders.API.DTOs.DiscountDTO.ResponseDiscountDTO;
import com.example.demo.orders.services.DiscountApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/v1/discounts")
public class DiscountsController {
    private DiscountApplicationService discountService;

    @Autowired
    public DiscountsController(DiscountApplicationService discountService){
        this.discountService = discountService;
    }
    @PostMapping
    public ResponseEntity<Object> createDiscount (@RequestParam UUID employeeId, @RequestBody PostDiscountDTO postDiscountDTO){
        ResponseEntity<Object> badResponse = ValidationForDifferentHTTPCodes.checkFor403(employeeId);
        if(badResponse != null){
            return badResponse;
        }

        if(postDiscountDTO.getValidFrom().compareTo(postDiscountDTO.getValidUntil()) > 0){
             throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        ResponseDiscountDTO createdDiscount = discountService.createDiscount();

        ValidationForDifferentHTTPCodes.checkFor404(createdDiscount);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdDiscount);
    }

    @GetMapping
    public ResponseEntity<Object> getDiscounts (@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam UUID businessId,
                                                @RequestParam UUID employeeId){
        ResponseEntity<Object> badResponse = ValidationForDifferentHTTPCodes.checkFor403(employeeId);
        if(badResponse != null){
            return badResponse;
        }

        GetDiscountsDTO discounts = discountService.getDiscountsByBusinessId(businessId);

        ValidationForDifferentHTTPCodes.checkFor404(discounts);

        return ResponseEntity.status(HttpStatus.CREATED).body(discounts);
    }

    @PatchMapping("/{discountId}")
    public  ResponseEntity<Object> updateDiscount(@PathVariable UUID discountId, @RequestParam UUID  employeeId,
                                                  @RequestBody PatchDiscountDTO patchDiscountDTO){
        /*do validation for thing*/

        ResponseDiscountDTO updatedDiscount = discountService.updateAndReturnDiscount(employeeId, patchDiscountDTO);

        ValidationForDifferentHTTPCodes.checkFor404(updatedDiscount);

        return ResponseEntity.status(HttpStatus.OK).body(updatedDiscount);
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<Object> deleteDiscount(@PathVariable UUID discountId, @RequestParam UUID employeeId){
        ResponseEntity<Object> badResponse = ValidationForDifferentHTTPCodes.checkFor403(employeeId);
        if(badResponse != null){
            return badResponse;
        }

        boolean wasDeletionSuccesful = discountService.deleteDiscountById(discountId);

        ValidationForDifferentHTTPCodes.checkFor404(wasDeletionSuccesful); //cringe, right now need to get null from wasDeletionSuccessful

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
