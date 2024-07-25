package com.sns.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sns.post.bo.PostBO;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class PostRestController {
	
	@Autowired
	private PostBO postBO;

	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam(value="content", required = false) String content,
			@RequestParam("file") MultipartFile file,
			HttpSession session
			){
		
		//userLogin by session
		Integer userId = (Integer) session.getAttribute("userId");
		String userLoginId = (String) session.getAttribute("userLoginId");
		
		Map<String, Object> result = new HashMap<>();
		if(userId == null) {
			result.put("code", 403);
			result.put("error_messsage", "로그인을 해주세요");
			return result;
		}
		
		//TODO DB update 
		postBO.addPost(userId, userLoginId, content, file);
		
		
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
	
	@PostMapping("/delete")
	public Map<String, Object> delete(
			@RequestParam("postId") int postId,
			HttpSession session
			){
		
		int userId = (int)session.getAttribute("userId");
		
		//DB delete TODO
		postBO.deletePost(postId, userId);
		
		//응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		return result;
	}
}