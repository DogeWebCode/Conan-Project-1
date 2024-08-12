package org.school.stylish.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.school.stylish.dto.report.PaymentsDTO;
import org.school.stylish.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/1.0/report/payments")
    public Map<String, List<PaymentsDTO>> getAllOrders() {
        List<PaymentsDTO> orders = reportService.getAllOrders();
        Map<String, List<PaymentsDTO>> response = new HashMap<>();
        response.put("data", orders);
        return response;
    }

    @PostMapping("/2.0/report/payments")
    public ResponseEntity<Map<String, String>> submitReportJob() throws JsonProcessingException {
        reportService.submitReportJob();
        Map<String, String> response = new HashMap<>();
        response.put("status", "Report job submitted");
        return ResponseEntity.ok().body(response);
    }
}
