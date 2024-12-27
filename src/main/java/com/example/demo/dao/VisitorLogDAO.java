package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.dto.VisitorLogDTO;
@Mapper
public interface VisitorLogDAO {
	@Insert("INSERT INTO visitor_log (visit_time, day_of_week, hour_of_day) " +
	        "VALUES (#{visitorLog.visitTime}, #{visitorLog.dayOfWeek}, #{visitorLog.hourOfDay})")
	public void insertVisitorLog(@Param("visitorLog") VisitorLogDTO visitorLog);

    // 시간대별 방문자 수
    @Select("SELECT " +
            "   hour_of_day AS hourOfDay, " +
            "   COUNT(*) AS visitCount " +
            "FROM visitor_log " +
            "GROUP BY hour_of_day " +
            "ORDER BY hour_of_day")
    public List<Map<String, Object>> getHourlyStats();

    // 요일별 방문자 수
    @Select("SELECT " +
            "   day_of_week AS dayOfWeek, " +
            "   COUNT(*) AS visitCount " +
            "FROM visitor_log " +
            "GROUP BY day_of_week " +
            "ORDER BY " +
            "CASE day_of_week " +
            "WHEN 'MON' THEN 1 " +
            "WHEN 'TUE' THEN 2 " +
            "WHEN 'WED' THEN 3 " +
            "WHEN 'THU' THEN 4 " +
            "WHEN 'FRI' THEN 5 " +
            "WHEN 'SAT' THEN 6 " +
            "WHEN 'SUN' THEN 7 " +
            "END")
    public List<Map<String, Object>> getDailyStats();

    // 지정된 기간의 시간대별 방문자 수
    @Select("SELECT " +
            "   hour_of_day AS hourOfDay, " +
            "   COUNT(*) AS visitCount " +
            "FROM visitor_log " +
            "WHERE visit_time BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY hour_of_day " +
            "ORDER BY hour_of_day")
    public List<Map<String, Object>> getHourlyStatsByDateRange(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);

    // 지정된 요일의 시간대별 방문자 수
    @Select("SELECT " +
            "   hour_of_day AS hourOfDay, " +
            "   COUNT(*) AS visitCount " +
            "FROM visitor_log " +
            "WHERE day_of_week = #{dayOfWeek} " +
            "GROUP BY hour_of_day " +
            "ORDER BY hour_of_day")
    public List<Map<String, Object>> getHourlyStatsByDayOfWeek(
            @Param("dayOfWeek") String dayOfWeek);

    // 전체 방문자 수 조회
    @Select("SELECT COUNT(*) FROM visitor_log")
    public long getTotalVisitorCount();
}