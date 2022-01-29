package com.bf.api.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bf.api.request.ConferenceFetchReq;
import com.bf.api.request.ConferenceRegisterPostReq;
import com.bf.api.request.UserInfoFetchReq;
import com.bf.api.request.UserRegisterPostReq;
import com.bf.api.response.ConferenceRes;
import com.bf.api.response.UserRes;
import com.bf.api.service.ConferenceService;
import com.bf.db.entity.Conference;
import com.bf.db.entity.User;
import com.bf.model.response.BaseResponseBody;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 회의실 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@CrossOrigin
@Api(value = "회의실 API", tags = {"Conferences"})
@RestController
@RequestMapping("/api/conferences")
public class ConferenceController {
	
	@Autowired
	ConferenceService conferenceService;
	
	@PostMapping()
	@ApiOperation(value = "회의실 생성", notes = "<strong>회의실 생성 정보를 입력받아</strong>회의실을 생성한다.") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "성공"),
        @ApiResponse(code = 401, message = "생성 실패"),//처리 추가해야됨
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value = "회의실 생성 정보", required = true)ConferenceRegisterPostReq conferenceRegisterPostReq) throws Exception {
		
		conferenceService.createConference(conferenceRegisterPostReq);
		
		
		return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
	}
	
	
	@GetMapping()
	@ApiOperation(value = "방송중인 전체 회의 조회", notes = "현재 방송중인 상태의 모든 회의 리스트를 return 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "생성 실패"),//처리 추가해야됨
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<List<ConferenceRes>> getConferenceList() {
		
		List<ConferenceRes> confrences= new ArrayList<ConferenceRes>();
		
		confrences = conferenceService.getAllConference();
		
		
		return ResponseEntity.status(200).body(confrences);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "회의 상세 조회", notes = "해당하는 id의 회의의 상세정보를 가져온다") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "생성 실패"),//처리 추가해야됨
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<Optional<Conference>> getConferenceDetail(@PathVariable Long id) {
		
		Optional<Conference> confrence=conferenceService.getConferenceDetail(id);
		
		return ResponseEntity.status(200).body(confrence);
	}
	
	
	@GetMapping("/end")
	@ApiOperation(value = "방송이 종료된 전체 회의 조회", notes = "방송이 끝난 상태의 모든 회의 리스트를 return 한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "생성 실패"),//처리 추가해야됨
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<List<ConferenceRes>> getEndConferenceList() {
		
		List<ConferenceRes> confrences= new ArrayList<ConferenceRes>();
		
		confrences = conferenceService.getAllEndConference();
		
		return ResponseEntity.status(200).body(confrences);
	}
	
	@PutMapping()
	@ApiOperation(value = "회의 수정", notes = "회의실의 정보를 수정한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "생성 실패"),//처리 추가해야됨
        @ApiResponse(code = 500, message = "서버 오류")
     })
	public ResponseEntity<? extends BaseResponseBody> updateConference(
			@RequestBody @ApiParam(value = "회의실 수정 정보", required = true)ConferenceFetchReq conferenceFetchReq) throws Exception {
		
		conferenceService.updateConference(conferenceFetchReq);
		
		return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
	}
	
	@PutMapping("/end/{id}")
	@ApiOperation(value = "회의 종료", notes = "회의실의 정보를 수정한다.") 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "생성 실패"),//처리 추가해야됨
        @ApiResponse(code = 500, message = "서버 오류")
     })
	public ResponseEntity<? extends BaseResponseBody> endConference(@PathVariable Long id){
		conferenceService.endConference(id);
	
		return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
	}
	
       
       
	

}