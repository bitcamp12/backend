package com.example.demo.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dao.VisitorLogDAO;
import com.example.demo.dto.VisitorLogDTO;

@Service
public class VisitorLogService {
    
    @Autowired
    private VisitorLogDAO visitorLogDAO;
    
    // 방문 기록 저장
    @Transactional
    public void logVisit(VisitorLogDTO visitorLog) {
        try {
            System.out.println("Service Layer - Attempting to save visit log: " + visitorLog);
            visitorLogDAO.insertVisitorLog(visitorLog);
            System.out.println("Service Layer - Successfully saved visit log");
        } catch (Exception e) {
            System.err.println("Service Layer - Error saving visit log: " + e.getMessage());
            throw new RuntimeException("방문 기록 저장 중 오류 발생", e);
        }
    }
    
    // 시간대별 통계
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getHourlyStats() {
        try {
            return visitorLogDAO.getHourlyStats();
        } catch (Exception e) {
            System.err.println("시간대별 통계 조회 중 오류: " + e.getMessage());
            throw new RuntimeException("시간대별 통계 조회 실패", e);
        }
    }
    
    // 요일별 통계
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getDailyStats() {
        try {
            return visitorLogDAO.getDailyStats();
        } catch (Exception e) {
            System.err.println("요일별 통계 조회 중 오류: " + e.getMessage());
            throw new RuntimeException("요일별 통계 조회 실패", e);
        }
    }
    
    // 기간 & 시간대별
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getHourlyStatsByDateRange(String startDate, String endDate) {
        try {
            return visitorLogDAO.getHourlyStatsByDateRange(startDate, endDate);
        } catch (Exception e) {
            System.err.println("기간별 통계 조회 중 오류: " + e.getMessage());
            throw new RuntimeException("기간별 통계 조회 실패", e);
        }
    }

    // 요일 & 시간대별
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getHourlyStatsByDayOfWeek(String dayOfWeek) {
        try {
            return visitorLogDAO.getHourlyStatsByDayOfWeek(dayOfWeek);
        } catch (Exception e) {
            System.err.println("요일별 시간대 통계 조회 중 오류: " + e.getMessage());
            throw new RuntimeException("요일별 시간대 통계 조회 실패", e);
        }
    }
    
    // 전체 방문자 수 조회
    @Transactional(readOnly = true)
    public long getTotalVisitorCount() {
        try {
            return visitorLogDAO.getTotalVisitorCount();
        } catch (Exception e) {
            System.err.println("전체 방문자 수 조회 중 오류: " + e.getMessage());
            throw new RuntimeException("전체 방문자 수 조회 실패", e);
        }
    }
}