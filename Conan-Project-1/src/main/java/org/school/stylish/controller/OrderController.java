package org.school.stylish.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.school.stylish.dto.order.OrderDTO;
import org.school.stylish.dto.other.ErrorResponse;
import org.school.stylish.exception.NotFoundException;
import org.school.stylish.exception.PaymentFailedException;
import org.school.stylish.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1.0/order")
@RequiredArgsConstructor
@Log4j2
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody OrderDTO orderDTO) {
        try {
            String orderNumber = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(orderService.createSuccessResponse(orderNumber));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ErrorResponse("Product not found"));
        } catch (CannotCreateTransactionException e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Database error"));
        } catch (PaymentFailedException e) {
            return ResponseEntity.status(400).body(new ErrorResponse("Payment failed"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ErrorResponse("Server error"));
        }
    }
}
