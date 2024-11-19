package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerDTO {
	private String name; //공연명
	private LocalDateTime start_time; //공연시작날짜
	private LocalDateTime end_time;
	private String image_file_name;
	private String image_original_file_name;
	private String discription;
	private String address;
	private String total_actor;
}
