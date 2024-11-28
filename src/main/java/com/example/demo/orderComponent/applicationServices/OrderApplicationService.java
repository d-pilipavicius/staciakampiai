package com.example.demo.orderComponent.applicationServices;

import com.example.demo.discountComponent.domain.entities.AppliedDiscount;
import com.example.demo.discountComponent.domain.entities.Discount;
import com.example.demo.discountComponent.domain.services.DiscountService;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.orderComponent.api.dtos.GetOrdersDTO;
import com.example.demo.orderComponent.api.dtos.OrderHelperDTOs.SelectedDiscountDTO;
import com.example.demo.orderComponent.domain.entities.OrderItemModifier;
import com.example.demo.orderComponent.helper.mapper.OrderMapper;
import com.example.demo.orderComponent.api.dtos.OrderHelperDTOs.OrderDTO;
import com.example.demo.orderComponent.api.dtos.OrderHelperDTOs.SelectedProductDTO;
import com.example.demo.orderComponent.api.dtos.PostOrderDTO;
import com.example.demo.orderComponent.domain.entities.Order;
import com.example.demo.orderComponent.domain.entities.OrderItem;
import com.example.demo.productComponent.domain.entities.Product;
import com.example.demo.orderComponent.domain.services.OrderService;
import com.example.demo.productComponent.domain.entities.ProductModifier;
import com.example.demo.productComponent.domain.services.ProductService;
import com.example.demo.serviceChargeComponent.domain.entities.enums.Currency;
import com.example.demo.serviceChargeComponent.domain.entities.enums.DiscountType;
import com.example.demo.serviceChargeComponent.domain.entities.enums.OrderStatus;
import com.example.demo.serviceChargeComponent.domain.services.ServiceChargeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.demo.orderComponent.api.dtos.GetReceiptDTO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(OrderApplicationService.class);

    // TODO: change to application services
    private final OrderService orderService;
    private final ServiceChargeService serviceChargeService;
    private final ProductService productService;
    private final DiscountService discountService;

    public OrderApplicationService(
            OrderService orderService,
            ServiceChargeService serviceChargeService,
            ProductService productService,
            DiscountService discountService) {
        this.orderService = orderService;
        this.serviceChargeService = serviceChargeService;
        this.productService = productService;
        this.discountService = discountService;
    }

    /**
     * Creates a Receipt object based on the specified order.
     *
     * @param orderDTO The order to create a receipt for.
     * @return The receipt for the specified order.
     */
    public GetReceiptDTO getOrderReceipt(GetOrdersDTO orderDTO) {
        Order order;
        order = Mapper.mapToModel(orderDTO, OrderMapper.TO_MODEL);
        // TODO: mb take UUID as an argument and get the order from the repository?
        // TODO: Implement this method -> get stuff from other components and compose a ReceiptDTO object and return it
        return null;
    }

    public OrderDTO createOrder(PostOrderDTO postOrderDTO){
        // Create a base of an order
        OrderDTO orderDTO = orderService.createOrder(postOrderDTO);

        // add order discounts, todo: add mapper
        List<SelectedDiscountDTO> orderDiscountsDTOs = postOrderDTO.getDiscounts();
        orderDiscountsDTOs.forEach(selectedDiscountDTO -> {
            AppliedDiscount appliedDiscount = new AppliedDiscount();
            appliedDiscount.setOrderId(orderDTO.getId());
            // Note: here we dont set orderItemId since its a discount for the whole order
            appliedDiscount.setEmployeeId(orderDTO.getEmployeeId());
            appliedDiscount.setType(selectedDiscountDTO.getType());
            appliedDiscount.setValue(selectedDiscountDTO.getValue());
            appliedDiscount.setCurrency(selectedDiscountDTO.getCurrency().orElse(Currency.EUR));
            appliedDiscount.setValueType(selectedDiscountDTO.getValueType());

            // if discount code is present -> search for Discount by code and set discountId to the found Discount's id
            // idk how else i should get the Discount bruh
            selectedDiscountDTO.getCode().ifPresent(code -> {
                if(selectedDiscountDTO.getType() != DiscountType.FLEXIBLE){
                    Discount discount = discountService.findDiscountByCode(code).orElseThrow();
                    appliedDiscount.setDiscountId(discount.getId());
                }
            });

            // save appliedDiscount to the repository, change to dto later
            discountService.saveAppliedDiscount(appliedDiscount);
        });

        // todo: add selected modifiers to the orderItemModifier
        // Create Order Items
        List<SelectedProductDTO> selectedProductDTOS = postOrderDTO.getItems();
        selectedProductDTOS.forEach(selectedProductDTO -> {
            // Create an orderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderDTO.getId());
            orderItem.setQuantity(selectedProductDTO.getQuantity());

            Product product = productService.findProductById(selectedProductDTO.getProductId()).orElseThrow();
            orderItem.setProduct(product);
            orderItem.setTitle("Kam/kaip sitas vps?");
            // todo: set unit price, cj applyint discounts arba pridet is modifier kaina
            orderItem.setCurrency(selectedProductDTO.getDiscounts().get(0).getCurrency().orElse(Currency.EUR));

            // set unit price to product price initially, since i cannot create orderItemModifier without orderItem
            orderItem.setUnitPrice(product.getPrice());

            // todo: save orderItem to the repository, change to pass the dto instead
            // todo: reiktu pasavint po to kai suskaiciuoju price, kad nereiktu updatint price
            OrderItem savedOrderItem = orderService.saveOrderItem(orderItem);

            // add orderitem discounts
            List<SelectedDiscountDTO> orderItemDiscountsDTOs = selectedProductDTO.getDiscounts();
            // create a list of appliedDiscounts
            orderItemDiscountsDTOs.forEach(selectedDiscountDTO -> {
                AppliedDiscount appliedDiscount = new AppliedDiscount();
                appliedDiscount.setOrderId(orderDTO.getId());
                appliedDiscount.setOrderItemId(savedOrderItem.getId());
                // discountId skip since its never provided ig (not here)
                appliedDiscount.setEmployeeId(orderDTO.getEmployeeId());
                appliedDiscount.setType(selectedDiscountDTO.getType());
                appliedDiscount.setValue(selectedDiscountDTO.getValue());
                appliedDiscount.setCurrency(selectedDiscountDTO.getCurrency().orElse(Currency.EUR));
                appliedDiscount.setValueType(selectedDiscountDTO.getValueType());

                // if discount code is present -> search for Discount by code and set discountId to the found Discount's id
                // idk how else i should get the Discount bruh
                selectedDiscountDTO.getCode().ifPresent(code -> {
                    if(selectedDiscountDTO.getType() != DiscountType.FLEXIBLE){
                        Discount discount = discountService.findDiscountByCode(code).orElseThrow();
                        appliedDiscount.setDiscountId(discount.getId());
                    }
                });

                // save appliedDiscount to the repository, change to dto later
                discountService.saveAppliedDiscount(appliedDiscount);
            });

            // Calculate product price with all modifiers -> initialy is set to product price
            AtomicReference<BigDecimal> totalModifierPrice = new AtomicReference<>(
                    product.getPrice() != null ? product.getPrice() : BigDecimal.ZERO
            );

            // Create OrderItemModifier for each selected modifier
            List<ProductModifier> productModifiers = productService.findProductModifiersById(selectedProductDTO.getSelectedModifierIds());
            productModifiers.forEach(productModifier -> {
                // Check if product modifier is compatible
                if(!product.getProductModifiers().contains(productModifier)){
                    throw new IllegalArgumentException("Product modifier is not compatible with the product");
                }

                // create a orderItemModifier for selected productModifier
                OrderItemModifier orderItemModifier = new OrderItemModifier();
                orderItemModifier.setOrderItemId(savedOrderItem.getId());
                orderItemModifier.setProductModifierId(productModifier.getId());
                orderItemModifier.setTitle(productModifier.getTitle());
                orderItemModifier.setPrice(productModifier.getPrice());
                orderItemModifier.setCurrency(savedOrderItem.getCurrency());

                // save orderItemModifier to the repository, change to pass the dto instead
                orderService.saveOrderItemModifier(orderItemModifier);

                totalModifierPrice.set(totalModifierPrice.get().add(productModifier.getPrice()));
            });

            // Sum of all product prices and all modifier prices of a single orderItem
            orderItem.setUnitPrice(totalModifierPrice.get());

        });

        // todo: create a list of orderItemModifier (received modifierIds)
        // -> pagal productId -> find all productModifiers -> filter those that are in the list of modifierIds
        // -> create a new OrderItemModifier for each of them
        // Validate modifier ids
        // Adjust appliedServicesCharges -> add orderId to all of them
        postOrderDTO.getServiceChargeIds().forEach(serviceChargeId -> {
            serviceChargeService.addOrderToServiceCharge(serviceChargeId, orderDTO.getId());
        });

        // todo: set orderItem.unitPrice to product.price + allModifiers Prices

        orderDTO.setStatus(OrderStatus.NEW);
        // set the createdAt timestamp to Now
        orderDTO.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        // Set original price -> sum of all orderItems, which should include all modifiers

        // set discounts price

        // set subtotalprice

        // set taxTotal

        // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA WTF???? IS THIS ajksdhkashdfkl

        return orderDTO;
    }
}
