package com.example.demo.orders.services;

import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.helper.mapper.orderMappers.OrderMapper;
import com.example.demo.orders.API.DTOs.OrderDTOs.OrderHelperDTOs.OrderDTO;
import com.example.demo.orders.API.DTOs.OrderDTOs.OrderHelperDTOs.SelectedProductDTO;
import com.example.demo.orders.API.DTOs.OrderDTOs.PostOrderDTO;
import com.example.demo.orders.domain.entities.Order;
import com.example.demo.orders.domain.entities.OrderItem;
import com.example.demo.orders.domain.entities.Product;
import com.example.demo.orders.domain.services.OrderService;
import com.example.demo.orders.domain.services.ProductService;
import com.example.demo.orders.domain.services.ServiceChargeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.example.demo.orders.API.DTOs.OrderDTOs.GetReceiptDTO;

import java.util.List;

@Service
public class OrderApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(OrderApplicationService.class);

    private final OrderService orderService;
    private final ServiceChargeService serviceChargeService;
    private final ProductService productService;

    public OrderApplicationService(
            OrderService orderService,
            ServiceChargeService serviceChargeService,
            ProductService productService
    ) {
        this.orderService = orderService;
        this.serviceChargeService = serviceChargeService;
        this.productService = productService;
    }

    /**
     * Creates a Receipt object based on the specified order.
     *
     * @param orderDTO The order to create a receipt for.
     * @return The receipt for the specified order.
     */
    public GetReceiptDTO getOrderReceipt(GetOrderDTO orderDTO) {
        Order order;
        order = Mapper.mapToModel(orderDTO, OrderMapper.TO_MODEL);
        // TODO: mb take UUID as an argument and get the order from the repository?
        // TODO: Implement this method -> get stuff from other components and compose a ReceiptDTO object and return it
        return null;
    }

    public OrderDTO createOrder(PostOrderDTO postOrderDTO){
        // Create a base of an order
        OrderDTO orderDTO = orderService.createOrder(postOrderDTO);

        // Create Order Items
        List<SelectedProductDTO> selectedProductDTOS = postOrderDTO.getItems();
        selectedProductDTOS.forEach(selectedProductDTO -> {
            // Create an orderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderDTO.getId());

            Product product = productService.findProductById(selectedProductDTO.getProductId()).orElseThrow();
            orderItem.setProduct(product);

            // Validate modifier ids

            // Create a new rows in the tables or are they already created? AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA

        });

    }
}
