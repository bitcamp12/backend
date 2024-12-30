package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PlayDTO;
import com.example.demo.entity.Play;
import com.example.demo.service.PlayService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.util.ApiResponse;


@RestController
@RequestMapping(value="/api/plays")
public class PlayController {
    
	@Autowired
	private PlayService playService;
	
	//페이지 정보가져오기
	@GetMapping("/getPlayOne")
	public ResponseEntity<ApiResponse<PlayDTO>> getPlayOne(@RequestParam("playSeq") String playSeq) {
	    System.out.println("Received playSeq: " + playSeq);

	    try {
	        PlayDTO playDTO = playService.getPlayOne(playSeq);
	        if (playDTO != null) {
	            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "데이터 있음", playDTO));
	        } else {
	            return ResponseEntity.status(HttpStatus.OK)  // 잘못된 요청
	                    .body(new ApiResponse<>(400, "잘못된 요청", null));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)  // 서버 에러
	                .body(new ApiResponse<>(500, "서버 에러", null));
	    }
	}

	//민웅 사용자 메인 페이지
	@GetMapping("/getPlayAll")
	public ResponseEntity<ApiResponse<List<Play>>> getPlayAll(@RequestParam("page") int page, @RequestParam("size") int size) {
		List<Play> plays = playService.getPlayAll(page, size);
		return ResponseEntity.ok(new ApiResponse<>(200, "Data retrieved", plays));
	}

	
	
	@PostMapping("/searchList")
	public ResponseEntity<ApiResponse<List<Play>>> searchList(@RequestBody PlayDTO dto) {
		System.out.println(dto.getName());
	    if (dto.getName() == null || dto.getName().isEmpty()) {
	        return ResponseEntity.badRequest()
	                .body(new ApiResponse<>(400, "검색결과가 없습니다", null));
	    }
	    try {
	       // List<PlayDTO> searchList = playService.searchList(dto.getName());
	    	 List<Play> searchList = playService.searchListEntity(dto.getName());
	        if (searchList.isEmpty()) {
	            return ResponseEntity.ok(new ApiResponse<>(204, "검색된 데이터가없습니다", searchList));
	        }
	        return ResponseEntity.ok(new ApiResponse<>(200, "데이터 목록입니다", searchList));
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(new ApiResponse<>(500, "서버 에러 발생", null));
	    }
	}
	
	@GetMapping("/getPlayRandom")
	public ResponseEntity<ApiResponse<List<Play>>> getPlayRandom() {
		List<Play> plays = playService.getPlayRandom();
		return ResponseEntity.ok(new ApiResponse<>(200, "Data retrieved", plays));
	}

	@GetMapping("/getPlayEndingSoon")
	public ResponseEntity<ApiResponse<List<Play>>> getPlayEndingSoon(@RequestParam("page") int page, @RequestParam("size") int size) {
		try {
			List<Play> plays = playService.getPlaysEndingSoon(page, size);
			if (plays.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse<>(204, "검색된 데이터가없습니다", plays));
			}
			return ResponseEntity.ok(new ApiResponse<>(200, "데이터 목록입니다", plays));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(500, "서버 에러 발생", null));
		}
	}

	@GetMapping("/getPlayComingSoon")
	public ResponseEntity<ApiResponse<List<Play>>> getPlayComingSoon(@RequestParam("page") int page, @RequestParam("size") int size) {
		try {
			List<Play> plays = playService.getPlaysComingSoon(page, size);
			if (plays.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse<>(204, "검색된 데이터가없습니다", plays));
			}
			return ResponseEntity.ok(new ApiResponse<>(200, "데이터 목록입니다", plays));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(500, "서버 에러 발생", null));
		}
	}

	@GetMapping("/getPlayLimited")
	public ResponseEntity<ApiResponse<List<Play>>> getPlayLimited(@RequestParam("page") int page, @RequestParam("size") int size) {
		try {
			List<Play> plays = playService.getPlaysLimited(page, size);
			if (plays.isEmpty()) {
				return ResponseEntity.ok(new ApiResponse<>(204, "검색된 데이터가없습니다", plays));
			}
			return ResponseEntity.ok(new ApiResponse<>(200, "데이터 목록입니다", plays));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(500, "서버 에러 발생", null));
		}
	}

	
	@GetMapping("cacheRefresh")
	public ResponseEntity<ApiResponse<String>> cacheRefresh(@RequestParam String param) {
		
		int result=playService.cacheRefresh();
		return ResponseEntity.ok(new ApiResponse<>(204, "검색된 데이터가없습니다", null));
	}
	
}
