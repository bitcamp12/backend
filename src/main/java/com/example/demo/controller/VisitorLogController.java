package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.VisitorLogDTO;
import com.example.demo.service.VisitorLogService;
import com.example.demo.util.ApiResponse;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/visitors")
public class VisitorLogController {
    
    @Autowired
    private VisitorLogService visitorLogService;

    // MainNa.jsx에서 호출하는 방문 로그 저장 API
    @PostMapping("/log")
    public ResponseEntity<ApiResponse<Void>> logVisit(@RequestBody VisitorLogDTO visitorLog) {
        try {
            // react utc 시간 -> 한국 시간
            ZonedDateTime utcTime = ZonedDateTime.parse(visitorLog.getVisitTime());
            ZonedDateTime koreanTime = utcTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
            
            //mysql datetime 형식으로 변하ㅗㄴ하기
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            visitorLog.setVisitTime(koreanTime.format(formatter));
            visitorLog.setHourOfDay(koreanTime.getHour());

            System.out.println("방문로그 (한국시간): " + visitorLog); // 디버깅용 로그
            visitorLogService.logVisit(visitorLog);
            
            return ResponseEntity.ok(new ApiResponse<>(200, "방문 기록 저장 성공", null));
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(400, "방문 기록 저장 실패: " + e.getMessage(), null));
        }
    }

    // 시간대별 통계 - 한국 시간 기준
    @GetMapping("/stats/hourly")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getHourlyStats() {
        try {
            List<Map<String, Object>> stats = visitorLogService.getHourlyStats();
            return ResponseEntity.ok(new ApiResponse<>(200, "시간대별 통계 조회 성공", stats));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(400, "통계 조회 실패: " + e.getMessage(), null));
        }
    }

    // 요일별 통계
    @GetMapping("/stats/daily")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getDailyStats() {
        try {
            List<Map<String, Object>> stats = visitorLogService.getDailyStats();
            return ResponseEntity.ok(new ApiResponse<>(200, "요일별 통계 조회 성공", stats));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(400, "통계 조회 실패: " + e.getMessage(), null));
        }
    }

    // 특정 기간 시간대별 통계 - 한국 시간 기준
    @GetMapping("/stats/hourly/date-range")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getHourlyStatsByDateRange(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        try {
            // 입력받은 날짜를 한국 시간대로 처리
            ZonedDateTime startDateTime = ZonedDateTime.parse(startDate)
                .withZoneSameInstant(ZoneId.of("Asia/Seoul"));
            ZonedDateTime endDateTime = ZonedDateTime.parse(endDate)
                .withZoneSameInstant(ZoneId.of("Asia/Seoul"));

            List<Map<String, Object>> stats = visitorLogService.getHourlyStatsByDateRange(
                startDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                endDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            );
            return ResponseEntity.ok(new ApiResponse<>(200, "기간별 통계 조회 성공", stats));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(400, "통계 조회 실패: " + e.getMessage(), null));
        }
    }

    // 특정 요일의 시간대별 통계
    @GetMapping("/stats/hourly/{dayOfWeek}")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getHourlyStatsByDayOfWeek(
            @PathVariable("dayOfWeek") String dayOfWeek) {
        try {
            List<Map<String, Object>> stats = visitorLogService.getHourlyStatsByDayOfWeek(dayOfWeek);
            return ResponseEntity.ok(new ApiResponse<>(200, "요일별 시간대 통계 조회 성공", stats));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(400, "통계 조회 실패: " + e.getMessage(), null));
        }
    }

    // 전체 방문자 수
    @GetMapping("/stats/total")
    public ResponseEntity<ApiResponse<Long>> getTotalVisitorCount() {
        try {
            long totalCount = visitorLogService.getTotalVisitorCount();
            return ResponseEntity.ok(new ApiResponse<>(200, "전체 방문자 수 조회 성공", totalCount));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(400, "조회 실패: " + e.getMessage(), null));
        }
    }
}