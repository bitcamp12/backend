package com.example.demo.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlayDiscountDTO {
    private String startTime;
	private String endTime;
	private String startDisTime;
	private String endDisTime;
	private int minRate;
	private int maxRate;
	private LocalDate targetDate;
	private double discountedPrice;
    private int playSeq;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private String imageFileName;
	private String imageOriginalFileName;
	private String address;
	private int price;
    private double discountRate;

    public double calculateSale() {
			// 원래 가격 PlayDTO에서 가져오기
			double originalPrice = getPrice();

			// discountStart, discountEnd, now를 String -> LocalDateTime으로 변환
			LocalDateTime discountStart = LocalDateTime.of(LocalDate.now(), LocalTime.parse(startDisTime));
            LocalDateTime discountEnd = LocalDateTime.of(LocalDate.now(), LocalTime.parse(endDisTime));

			LocalDateTime now = LocalDateTime.now();

			// 현재 시간이 할인 기간이 아니면 할인 적용 X -> 원래 가격 반환
			if(now.isBefore(discountStart) || now.isAfter(discountEnd)) {
				return originalPrice;
			}

			// Duration을 이용해 할인율 계산
			// 위에 만들어놓은 discountStart, discountEnd, now를 이용해 총, 경과 시간 계산
			Duration totalDuration = Duration.between(discountStart, discountEnd);
			Duration elapsedDuration = Duration.between(discountStart, now);
			// (현재시간 - 할인시작시간) = elapsedDuration / (할인종료시간 - 할인시작시간) = totalDuration
			double timeRatio = (double) elapsedDuration.toMillis() / totalDuration.toMillis();

			// 할인율 공식
			double discountRate = minRate + (maxRate - minRate) * timeRatio;
			this.discountRate = discountRate;

			//최종 가격 공식
			return originalPrice * (100 - discountRate) / 100;
	}
}
