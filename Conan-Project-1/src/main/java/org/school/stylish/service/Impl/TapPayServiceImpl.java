package org.school.stylish.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.school.stylish.dto.order.OrderDTO;
import org.school.stylish.dto.order.tappay.TapPayCardholderDTO;
import org.school.stylish.dto.order.tappay.TapPayRequestDTO;
import org.school.stylish.dto.order.tappay.TapPayResponseDTO;
import org.school.stylish.service.TapPayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
@Log4j2
public class TapPayServiceImpl implements TapPayService {
    @Value("${tappay.partner_key}")
    private String partnerKey;

    @Value("${tappay.merchant_id}")
    private String merchantId;

    @Value("${tappay.api-url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    @Override
    public TapPayResponseDTO processPayment(OrderDTO orderDTO, String orderNumber) {
        TapPayRequestDTO request = createPaymentRequest(orderDTO, orderNumber);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", partnerKey);

        HttpEntity<TapPayRequestDTO> entity = new HttpEntity<>(request, headers);

        ResponseEntity<TapPayResponseDTO> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                TapPayResponseDTO.class
        );
        return responseEntity.getBody();
    }

    @Override
    public TapPayRequestDTO createPaymentRequest(OrderDTO orderDTO, String orderNumber) {
        TapPayRequestDTO request = new TapPayRequestDTO();
        request.setPartnerKey(partnerKey);
        request.setMerchantId(merchantId);
        request.setOrderNumber(orderNumber);
        request.setPrime(orderDTO.getPrime());
        request.setAmount(orderDTO.getOrder().getTotal());
        request.setDetails("Stylish Order");

        TapPayCardholderDTO cardholder = new TapPayCardholderDTO();
        cardholder.setPhoneNumber(orderDTO.getOrder().getRecipient().getPhone());
        cardholder.setName(orderDTO.getOrder().getRecipient().getName());
        cardholder.setEmail(orderDTO.getOrder().getRecipient().getEmail());
        request.setCardholder(cardholder);

        return request;
    }


}
