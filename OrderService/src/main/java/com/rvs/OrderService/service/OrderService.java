package com.rvs.OrderService.service;

import com.rvs.OrderService.dto.InventoryResponse;
import com.rvs.OrderService.dto.OrderLineItemsDto;
import com.rvs.OrderService.dto.OrderRequest;
import com.rvs.OrderService.model.Order;
import com.rvs.OrderService.model.OrderLineItems;
import com.rvs.OrderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

       List<OrderLineItems> orderLineItems =  orderRequest.getOrderLineItemsDtoList()
                .stream().map(this::mapTodto).toList();

       order.setOrderLineItemsList(orderLineItems);
       List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();
       // call inventory service here, and check for stock availability, if stock exists, proceed further
       InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
                        .uri("http://inventory-service/api/inventory",
                                uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                        .retrieve().bodyToMono(InventoryResponse[].class)
                        .block();

       boolean allProducts =  Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInstock);
       //all match will check if all the Inventoryresponse have the instock variable as true, then only it will
        //make all products true
       // web client will construct that inventory service uri in this way ->
        //http://localhost:8082/api/inventory?skuCode=prod1&skuCode=prod2
       if(allProducts){
           orderRepository.save(order);
       }else{
           throw new IllegalArgumentException("Product not in stock, try later");
       }

    }

    private OrderLineItems mapTodto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems =  new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
