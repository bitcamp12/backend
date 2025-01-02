package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.PlayDAO;
import com.example.demo.dto.PlayDTO;
import com.example.demo.dto.PlayDiscountDTO;
import com.example.demo.entity.Play;
import com.example.demo.repository.PlayRepository;
import com.example.demo.util.ApiResponse;

@Service
public class PlayService {
	 

	@Autowired
	private PlayDAO playDAO;
	
	@Autowired
	private PlayRepository playRepository;
	
	 @Autowired
	 private CacheManager cacheManager;

	private List<PlayDiscountDTO> cachedDiscountedPlays;

	@Cacheable(value = "play", key = "#p0") // 새 캐시 저장
	public PlayDTO getPlayOne(String playSeq) {
	    System.out.println("Fetching playSeq from DB: " + playSeq);
	    PlayDTO playDTO = playDAO.getPlayOne(playSeq);
	    System.out.println("Returned playDTO: " + playDTO);
	    return playDTO;  
	}
	

	//민웅 사용자 메인 페이지

//@Cacheable(value = "getPlayAll", key = "#page + '-' + #size")
	public List<Play> getPlayAll(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<Play> playPage = playRepository.findAll(pageable);
		return playPage.getContent();
	}

	public List<PlayDTO> searchList(String name) {
		return playDAO.searchList(name);
	}
	

//@Cacheable(value = "PlayRandom")
    public List<Play> getPlayRandom() {
        List<Play> allPlays = playRepository.findAll();
		Collections.shuffle(allPlays);
		return allPlays.stream().limit(10).collect(Collectors.toList());
    }

	public List<PlayDiscountDTO> getPlaySale() {
        return cachedDiscountedPlays;
    }
	
//	@Cacheable(value = "searchListEntity", key = "#p0")
	public List<Play> searchListEntity(String name) {
		 System.out.println("Fetching random plays from DB...searchListEntity");
		System.out.println(name+"**entity");
		List<Play> list = playRepository.findByNameContaining(name);
		
		 if (!list.isEmpty()) {
		        return list;
		   } else {
		        return new ArrayList<>(); 
		  }
	}

//@Cacheable(value = "getPlaysEndingSoon", key = "#page + '-' + #size")
	public List<Play> getPlaysEndingSoon(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "endTime"));
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime thirtyDaysFromNow = now.plusDays(30);
		Page<Play> playPage = playRepository.findByEndTimeBetween(now, thirtyDaysFromNow, pageable);
		return playPage.getContent();
	}
//@Cacheable(value = "getPlaysComingSoon", key = "#page + '-' + #size")
	public List<Play> getPlaysComingSoon(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "startTime"));
		Page<Play> playPage = playRepository.findByStartTimeAfter(LocalDateTime.now(), pageable);
		return playPage.getContent();
	}
//@Cacheable(value = "getPlaysLimited", key = "#page + '-' + #size")
	public List<Play> getPlaysLimited(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "price"));
		Page<Play> playPage = playRepository.findByPriceGreaterThanEqual(60000, pageable);
		return playPage.getContent();
	}


public int cacheRefresh() {
    if (cacheManager != null) {
        cacheManager.getCacheNames().forEach(cacheName -> {
            System.out.println("Clearing cache: " + cacheName); // 로그 출력
            cacheManager.getCache(cacheName).clear(); // 캐시 초기화
        });
        return 1; // 캐시 삭제 성공 시 1 반환
    } else {
        
        return 0; // 캐시 삭제 실패 시 0 반환
    }
}


}