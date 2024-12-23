package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.VisitorLogDAO;
import com.example.demo.dto.VisitorLogDTO;

@Service
public class VisitorLogService {
	
	@Autowired
	private VisitorLogDAO visitorLogDAO;
	
	// 방문 기록 저장
    public void logVisit(VisitorLogDTO visitorLog) {
        visitorLogDAO.insertVisitorLog(visitorLog);
    }
	
	// 시간대별 통계
	public List<Map<String, Object>> getHourlyStats(){
		return visitorLogDAO.getHourlyStats();
	}
	
	// 요일별 통계
	public List<Map<String, Object>> getDailyStats(){
		return visitorLogDAO.getDailyStats();
	}
	
	// 기간 & 시간대별 
    public List<Map<String, Object>> getHourlyStatsByDateRange(String startDate, String endDate) {
        return visitorLogDAO.getHourlyStatsByDateRange(startDate, endDate);
    }

    // 요일 & 시간대별 
    public List<Map<String, Object>> getHourlyStatsByDayOfWeek(String dayOfWeek) {
    	return visitorLogDAO.getHourlyStatsByDayOfWeek(dayOfWeek);
    }
    
    // 전체 방문자 수 조회
    public long getTotalVisitorCount() {
    	return visitorLogDAO.getTotalVisitorCount();
    }
}
