package com.example.demo;

import com.example.demo.orders.domain.entities.Order;
import com.example.demo.orders.domain.entities.enums.OrderStatus;
import com.example.demo.orders.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@SpringBootApplication
public class DemoApplication {

	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(OrderRepository orderRepository) {
		return args -> {
			orderRepository.save(new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), Date.from(Instant.now()), OrderStatus.NEW));
			orderRepository.save(new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), Date.from(Instant.now()), OrderStatus.CLOSED));
			orderRepository.save(new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), Date.from(Instant.now()), OrderStatus.IN_PROGRESS));
			orderRepository.save(new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), Date.from(Instant.now()), OrderStatus.RETURNED));
			orderRepository.save(new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), Date.from(Instant.now()), OrderStatus.NEW));

			logger.info("Orders found with findAll():");
			logger.info("__________________________");
			orderRepository.findAll().forEach(order ->
					logger.info(order.toString())
			);
			logger.info("");

				logger.info("Order found with findOrderByStatus(OrderStatus.NEW):");
				logger.info("_______________________________________________");

			orderRepository.findOrderByStatus(OrderStatus.NEW).forEach(order -> {
				logger.info(order.toString());
			});
			logger.info("");
		};
	}

}
