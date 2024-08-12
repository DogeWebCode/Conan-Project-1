package org.school.stylish.dao.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ReportDaoImpl {

    private final JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findAllOrders() {
        String sql = "SELECT user_id, total FROM orders";
        return jdbcTemplate.queryForList(sql);
    }

    public int countTotalOrders() {
        String sql = "SELECT COUNT(*) FROM orders";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
