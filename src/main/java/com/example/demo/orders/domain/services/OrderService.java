package com.example.demo.orders.domain.services;

import com.example.demo.helper.mapper.OrderMapper;
import com.example.demo.helper.mapper.base.Mapper;
import com.example.demo.orders.domain.entities.Order;
import com.example.demo.orders.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import com.example.demo.orders.API.DTOs.OrderDTOs.GetOrderDTO;

// todo: needs rethinking probably

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(GetOrderDTO orderDTO){
        orderRepository.save(
                Mapper.mapToModel(
                        orderDTO,
                        OrderMapper.TO_MODEL
                )
        );
    }

    public Optional<GetOrderDTO> getOrderById(UUID id){
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()){
            return Optional.of(Mapper.mapToDTO(
                    order.get(),
                    OrderMapper.TO_DTO
            ));
        }else{
            logger.error("Order with id {} not found", id);
            return Optional.empty();
        }
    }

    public List<GetOrderDTO> getAllOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper.TO_DTO::map)
                .collect(Collectors.toList());
    }

    public Page<Order> getAllOrders(int page, int size){
        return orderRepository.findAll(PageRequest.of(page, size));
    }

    public void updateOrder(Order order){
        orderRepository.save(order);
    }
}
