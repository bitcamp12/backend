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
	//방문 기록 저장
	@Insert("INSERT INTO visitor_log (visit_time, day_of_week, hour_of_day) " +
			"VALUES (#{visitTime}, #{dayOfWeek}, #{hourOfDay})")
	public void insertVisitorLog(VisitorLogDTO visitorLog);
	
	//시간대별 방문자 수
	@Select("SELECT hour_of_day, COUNT(*) as visit_count " +
	"FROM visitor_log " +
	"GROUP BY hour_of_day " +
	"ORDER BY hour_of_day")
	public List<Map<String, Object>> getHourlyStats();

	//요일별 방문자 수
	@Select("SELECT day_of_week, COUNT(*) as visit_count " +
			"FROM visitor_log " +
			"GROUP BY day_of_week" +
			"ORDER BY FIELD(day_of_week, 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN')")
	public List<Map<String, Object>> getDailyStats();

	//지정된 기간의 시간대별 방문자 수
	@Select("SELECT hour_of_day, COUNT(*) as visit_count " +
			"FROM visitor_log " +
			"WHERE visit_time BEWEEN #{startDate} AND #{endDate} " +
			"GROUP BY hour_of_day" +
			"ORDER BY hour_of_day")
	public List<Map<String, Object>> getHourlyStatsByDateRange(
			@Param("startDate") String startDate,
			@Param("endDate") String endDate);
	
    //지정된 요일의 시간대별 방문자 수
    @Select("SELECT hour_of_day, COUNT(*) as visit_count " +
            "FROM visitor_log " +
            "WHERE day_of_week = #{dayOfWeek} " +
            "GROUP BY hour_of_day " +
            "ORDER BY hour_of_day")
    public List<Map<String, Object>> getHourlyStatsByDayOfWeek(@Param("dayOfWeek") String dayOfWeek);
    
    // 전체 방문자 수 조회
    @Select("SELECT COUNT(*) FROM visitor_log")
    public long getTotalVisitorCount();
	
}
