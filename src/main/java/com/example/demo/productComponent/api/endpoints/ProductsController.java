package com.example.demo.productComponent.api.endpoints;

import com.example.demo.helper.validator.ValidationForDifferentHTTPCodes;
import com.example.demo.productComponent.api.dtos.GetProductsDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.GetModifiersDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PatchModifierDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.PostModifierDTO;
import com.example.demo.productComponent.api.dtos.ModifierDTOs.ResponseModifierDTO;
import com.example.demo.productComponent.api.dtos.PatchProductDTO;
import com.example.demo.productComponent.api.dtos.PostProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductDTO;
import com.example.demo.productComponent.api.dtos.ProductAndModifierHelperDTOs.ProductModifierDTO;
import com.example.demo.productComponent.api.dtos.ResponseProductDTO;
import com.example.demo.productComponent.applicationServices.ProductApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/products")
@AllArgsConstructor
public class ProductsController {

    private final ProductApplicationService productApplicationService;

    @PostMapping
    public ResponseEntity<Object> createProduct(
            @RequestParam UUID employeeId,
            @Validated @RequestBody PostProductDTO postProductDTO,
            BindingResult bindingResult
    ) {

        // Check for 400
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        ProductDTO productDTO = productApplicationService.createProduct(postProductDTO);

        // Check for 404
        ValidationForDifferentHTTPCodes.checkFor404(productDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }

    @GetMapping
    public ResponseEntity<Object> getProducts(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam UUID businessId,
            @RequestParam UUID employeeId
    ){

        GetProductsDTO products = productApplicationService.getProductsByBusinessId(pageNumber, pageSize, businessId);

        ValidationForDifferentHTTPCodes.checkFor404(products);

        return ResponseEntity.ok(products);
    }

    @PatchMapping("/{productId}")
    public  ResponseEntity<Object> updateProduct(
            @PathVariable UUID productId,
            @RequestParam UUID employeeId,
            @Validated @RequestBody PatchProductDTO patchDiscountDTO,
            BindingResult bindingResult
    ){

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        ResponseProductDTO updatedProduct = productApplicationService.updateProduct(patchDiscountDTO, productId);

        ValidationForDifferentHTTPCodes.checkFor404(updatedProduct);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> deleteProduct(
            @PathVariable UUID productId,
            @RequestParam UUID employeeId
    ){
        productApplicationService.deleteProduct(productId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/modifiers")
    public ResponseEntity<ProductModifierDTO> createProductModifier(
            @RequestParam UUID employeeId,
            @RequestBody PostModifierDTO postModifierDTO
    ) {
        ProductModifierDTO createdModifier = productApplicationService.createModifier(postModifierDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdModifier);
    }

    @GetMapping("/modifiers")
    public ResponseEntity<GetModifiersDTO> getProductModifiers(
            @RequestParam int pageNumber,
            @RequestParam int pageSize,
            @RequestParam UUID businessId,
            @RequestParam UUID employeeId
    ) {

        ResponseEntity<Object> badResponse = ValidationForDifferentHTTPCodes.checkFor403(employeeId);

        GetModifiersDTO modifiers = productApplicationService.getModifiersByBusinessId(pageNumber, pageSize, businessId);

        return ResponseEntity.ok(modifiers);
    }

    @PatchMapping("/modifiers/{modifierId}")
    public ResponseEntity<Object> updateProductModifier(
            @PathVariable UUID modifierId,
            @RequestParam UUID employeeId,
            @Validated @RequestBody PatchModifierDTO patchModifierDTO,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        ResponseModifierDTO updatedModifier = productApplicationService.updateProductModifier(patchModifierDTO, modifierId);

        return ResponseEntity.ok(updatedModifier);
    }

    @DeleteMapping("/modifiers/{modifierId}")
    public ResponseEntity<Void> deleteProductModifier(
            @PathVariable UUID modifierId,
            @RequestParam UUID employeeId
    ) {

        ResponseEntity<Object> badResponse = ValidationForDifferentHTTPCodes.checkFor403(employeeId);

        productApplicationService.deleteProductModifier(modifierId);

        return ResponseEntity.noContent().build();
    }
}
