package com.example.demo.orders.domain.services;

import com.example.demo.helper.mapper.ProductMapper;
import com.example.demo.helper.mapper.ProductModifierMapper;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.orders.API.DTOs.ProductDTOs.GetProductsDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs.GetModifiersDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs.PatchModifierDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs.PostModifierDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ModifierDTOs.ResponseModifierDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.PatchProductDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.PostProductDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierHelperDTOs.ProductDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ProductAndModifierHelperDTOs.ProductModifierDTO;
import com.example.demo.orders.API.DTOs.ProductDTOs.ResponseProductDTO;
import com.example.demo.orders.domain.entities.Product;
import com.example.demo.orders.domain.entities.ProductModifier;
import com.example.demo.orders.repository.ProductModifierRepository;
import com.example.demo.orders.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final ProductModifierRepository productModifierRepository;

    public ProductService(
            ProductRepository productRepository,
            ProductModifierRepository productModifierRepository
    ) {
        this.productRepository = productRepository;
        this.productModifierRepository = productModifierRepository;
    }

    @Transactional
    public ProductDTO createProduct(PostProductDTO postProductDTO){
        List<ProductModifier> productModifiers = productModifierRepository
                .findAllById(
                        postProductDTO.getCompatibleModifierIds()
                );

        // Validation?
        if(productModifiers.size() != postProductDTO.getCompatibleModifierIds().size()){
            throw new IllegalArgumentException("Some of the modifiers are not found");
        }

        Product product = Mapper.mapToModel(
                postProductDTO,
                ProductMapper.TO_MODEL
        );

        product.setProductModifiers(productModifiers);

        return Mapper.mapToDTO(
                productRepository.save(product),
                ProductMapper.TO_DTO
        );
    }

    public ProductModifierDTO createProductModifier(PostModifierDTO postModifierDTO){
        return Mapper.mapToDTO(
                productModifierRepository.save(
                        Mapper.mapToModel(
                                postModifierDTO,
                                ProductModifierMapper.TO_MODEL
                        )
                ),
                ProductModifierMapper.TO_DTO
        );
    }

    public GetProductsDTO getAllProducts(){
        List<ProductDTO> productDTOS = productRepository.findAll().stream()
                .map(ProductMapper.TO_DTO::map)
                .toList();

        GetProductsDTO getProductsDTO = new GetProductsDTO();
        getProductsDTO.setItems(productDTOS);
        getProductsDTO.setBusinessId(productDTOS.get(0).getBusinessId());
        return getProductsDTO;
    }

    public GetProductsDTO getAllProducts(int page, int size){
        List<ProductDTO> productDTOS = productRepository.findAll(PageRequest.of(page, size)).stream()
                .map(ProductMapper.TO_DTO::map)
                .toList();

        return new GetProductsDTO(
                productDTOS.size(),
                size,
                page,
                productDTOS,
                productDTOS.get(0).getBusinessId()
        );
    }

    public GetModifiersDTO getAllProductModifiers(){
        List<ProductModifierDTO> productModifierDTOS = productModifierRepository.findAll().stream()
                .map(ProductModifierMapper.TO_DTO::map)
                .toList();

        GetModifiersDTO getModifiersDTO = new GetModifiersDTO();
        getModifiersDTO.setItems(productModifierDTOS);
        return getModifiersDTO;
    }

    public GetModifiersDTO getAllProductModifiers(int page, int size){
        List<ProductModifierDTO> productModifierDTOS = productModifierRepository.findAll(PageRequest.of(page, size)).stream()
                .map(ProductModifierMapper.TO_DTO::map)
                .toList();

        return new GetModifiersDTO(
                productModifierDTOS.size(),
                size,
                page,
                productModifierDTOS
        );
    }

    // TODO: do smth with rowVersion
    @Transactional
    public ResponseProductDTO updateProduct(PatchProductDTO patchProductDTO, UUID id){
        Product product = productRepository.findById(id).orElseThrow();

        patchProductDTO.getTitle().ifPresent(product::setTitle);
        patchProductDTO.getQuantityInStock().ifPresent(product::setQuantityInStock);
        patchProductDTO.getPrice().ifPresent(
                moneyDTO -> {
                    product.setAmount(moneyDTO.getAmount());
                    product.setCurrency(moneyDTO.getCurrency());
                }
        );

        // Update compatible modifiers
        if(patchProductDTO.getCompatibleModifierIds().isPresent()){
            List<ProductModifier> productModifiers = productModifierRepository
                    .findAllById(
                            patchProductDTO.getCompatibleModifierIds().get()
                    );

            // Validation?
            if(productModifiers.size() != patchProductDTO.getCompatibleModifierIds().get().size()){
                throw new IllegalArgumentException("Some of the modifiers are not found");
            }

            product.setProductModifiers(productModifiers);
        }

        ResponseProductDTO responseProductDTO = new ResponseProductDTO();
        responseProductDTO.setProduct(Mapper.mapToDTO(
                productRepository.save(product),
                ProductMapper.TO_DTO
        ));

        return responseProductDTO;
    }

    @Transactional
    public ResponseModifierDTO updateProductModifier(PatchModifierDTO patchModifierDTO, UUID id){
        ProductModifier productModifier = productModifierRepository.findById(id).orElseThrow();

        patchModifierDTO.getTitle().ifPresent(productModifier::setTitle);
        patchModifierDTO.getQuantityInStock().ifPresent(productModifier::setQuantityInStock);
        patchModifierDTO.getPrice().ifPresent(
                moneyDTO -> {
                    productModifier.setPrice(moneyDTO.getAmount());
                    productModifier.setCurrency(moneyDTO.getCurrency());
                }
        );

        ResponseModifierDTO responseModifierDTO = new ResponseModifierDTO();
        responseModifierDTO.setProductModifier(Mapper.mapToDTO(
                productModifierRepository.save(productModifier),
                ProductModifierMapper.TO_DTO
        ));

        return responseModifierDTO;
    }

    public void deleteProduct(UUID id){
        productRepository.deleteById(id);
    }

    public void deleteProductModifier(UUID id){
        productModifierRepository.deleteById(id);
    }
}
