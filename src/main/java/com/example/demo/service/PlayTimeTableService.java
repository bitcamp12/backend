package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PlayTimeTableDAO;
import com.example.demo.dto.PlayDiscountDTO;
import com.example.demo.dto.PlayTimeTableDTO;

@Service
public class PlayTimeTableService {

	@Autowired
	private PlayTimeTableDAO playTimeTableDAO;

	public List<PlayTimeTableDTO> playTimeTables(int playSeq, String targetDate) {
		return playTimeTableDAO.playTimeTables(playSeq,targetDate);
	}

	@Scheduled(fixedRate = 60000)
    public List<PlayDiscountDTO> calculateDiscount() {
        List<PlayDiscountDTO> discountedPlays = playTimeTableDAO.getPlayWithDiscount();
        
        for (PlayDiscountDTO playDiscountDTO : discountedPlays) {
            double discountedPrice = playDiscountDTO.calculateSale();
            playDiscountDTO.setDiscountedPrice(discountedPrice);

			PlayTimeTableDTO playTimeTableDTO = new PlayTimeTableDTO();
			playTimeTableDTO.setPlayTimeTableSeq(playDiscountDTO.getPlayTimeTableSeq());
            playTimeTableDTO.setDiscountRate(playDiscountDTO.getDiscountRate());
            playTimeTableDTO.setDiscountedPrice(playDiscountDTO.getDiscountedPrice());

			playTimeTableDAO.updateDiscounts(playTimeTableDTO);
        }
		
		return discountedPlays;

    }
}
