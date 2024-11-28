package com.example.demo.orderComponent.domain.services;

import com.example.demo.orderComponent.helper.mapper.OrderMapper;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.orderComponent.api.dtos.GetOrdersDTO;
import com.example.demo.orderComponent.api.dtos.OrderHelperDTOs.OrderDTO;
import com.example.demo.orderComponent.api.dtos.PatchOrderDTO;
import com.example.demo.orderComponent.api.dtos.PostOrderDTO;
import com.example.demo.orderComponent.api.dtos.ResponseOrderDTO;
import com.example.demo.orderComponent.domain.entities.Order;
import com.example.demo.orderComponent.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

// todo: needs rethinking probably

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDTO createOrder(PostOrderDTO postOrderDTO){
        // TODO: also set employeeId of the model before creating it
        return Mapper.mapToDTO(
                orderRepository.save(
                        Mapper.mapToModel(
                                postOrderDTO,
                                OrderMapper.TO_MODEL
                        )
                ),
                OrderMapper.TO_DTO
        );
    }

    public OrderDTO getOrderById(UUID id){
        Optional<Order> order = orderRepository.findById(id);
        return order.map(value -> Mapper.mapToDTO(value, OrderMapper.TO_DTO)).orElse(null);
    }

    public GetOrdersDTO getAllOrders(){

    }

    // todo: Page<Order>?
    public GetOrdersDTO getAllOrders(int page, int size){

    }

    public ResponseOrderDTO updateOrder(PatchOrderDTO patchOrderDTO){
        orderRepository.save(order);
    }

    public void deleteOrder(UUID id){
        orderRepository.deleteById(id);
    }
}
