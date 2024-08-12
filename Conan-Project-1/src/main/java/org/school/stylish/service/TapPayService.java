package org.school.stylish.service;

import org.school.stylish.dto.order.OrderDTO;
import org.school.stylish.dto.order.tappay.TapPayRequestDTO;
import org.school.stylish.dto.order.tappay.TapPayResponseDTO;

public interface TapPayService {
    TapPayResponseDTO processPayment(OrderDTO orderDTO, String orderNumber);

    TapPayRequestDTO createPaymentRequest(OrderDTO orderDTO, String orderNumber);

}
