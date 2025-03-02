package com.cry.ecom.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cry.ecom.Dto.OrderDto;
import com.cry.ecom.Dto.Response.ApiResponse;
import com.cry.ecom.Entity.Order;
import com.cry.ecom.Exception.ResourceNotFoundException;
import com.cry.ecom.Service.Order.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final OrderService orderService;
    // private final PaymentService paymentService;

    @PostMapping("/user/place-order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Items Order Success!", orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error occurred!", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!", e.getMessage()));
        }
    }

    @GetMapping("/user/{userId}/order")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        try {
            List<OrderDto> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!", e.getMessage()));
        }
    }

    // @PostMapping("/payment/create-payment-intent")
    // public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo
    // paymentInfo) throws StripeException {
    // try {
    // System.out.println("The body :" + paymentInfo);
    // PaymentIntent paymentIntent =
    // paymentService.createPaymentIntent(paymentInfo);
    // String paymentString = paymentIntent.toJson();
    // System.out.println("The payment string :" + paymentString);
    // return ResponseEntity.ok(paymentString);
    // } catch (StripeException e) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    // }
    // }
}
