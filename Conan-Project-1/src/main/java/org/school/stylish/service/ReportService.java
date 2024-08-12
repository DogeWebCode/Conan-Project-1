package org.school.stylish.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.school.stylish.dto.report.PaymentsDTO;

import java.util.List;

public interface ReportService {
    List<PaymentsDTO> getAllOrders();

    void submitReportJob() throws JsonProcessingException;
}
