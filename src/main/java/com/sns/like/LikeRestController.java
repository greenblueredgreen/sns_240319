package com.sns.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sns.like.bo.LikeBO;
import com.sns.like.domain.Like;

import jakarta.servlet.http.HttpSession;

@RestController
public class LikeRestController {
	
	@Autowired
	private LikeBO likeBO;
	
	@Autowired
	private Like like;

	// GET: /like?postId = 13    @RequestParam("postId")
	// GET: /like/13		     @PathVariable
	@RequestMapping("/like/{postId}")
	public Map<String, Object> likeToggle(
			@PathVariable(name="postId") int postId,
			HttpSession session){  //로그인 여부 session안에 있다
		
		//로그인 여부 확인
		Integer userId = (Integer) session.getAttribute("userId");
		Map<String, Object> result = new HashMap<>();
		if(userId == null) {
			// 비로그인일때
			result.put("code", 403);
			result.put("error_message", "로그인을 해주세요");
			return result;
		}
		
		// likeToggle BO로 요청
		// 글번호, userId(로그인 된 사람 = 나) 를 넘긴다, postId만 넘기면 글번호관련 정보만 넘어와서 쓸모가 없다
		likeBO.likeToggle(postId, userId);
		
		//성공 응답
		//로그인 되어있음
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}
