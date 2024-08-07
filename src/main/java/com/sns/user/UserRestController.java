package com.sns.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.common.EncryptUtils;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController //data jsons반환
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	/**
	 * 아이디 중복확인 api
	 * @param loginId
	 * @return
	 */
	
	@RequestMapping("/is-duplicated-id")
	public Map<String, Object> isDuplictedId(
			@RequestParam("loginId") String loginId){
		
		//db 조회
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		
		//응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		if(user != null) { // 중복이면
			result.put("is_duplicated_id", true);
		} else {
			result.put("is_duplicated_id", false);
		}
		return result;
	}
	
	/**
	 * 회원가입 화면
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email){
		
		String hashedPassword = EncryptUtils.md5(password);
		
		// 해시값 저장
		UserEntity user = userBO.addUser(loginId, hashedPassword, name, email);
		
		Map<String, Object> result = new HashMap<>();
		if(user!= null) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("result", "회원가입 실패");
		}
		return result;
	}

	/**
	 * 로그인
	 * @param loginId
	 * @param password
	 * @param request
	 * @return
	 */
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request){
		
		//password 해싱
		String hashedPassword = EncryptUtils.md5(password);   
		
		//DB조회
		UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, hashedPassword);
		
		//로그인 처리, 응답값
		Map<String, Object> result = new HashMap<>();
		if(user != null) {
			//session에 사용자 정보 담기
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("userloginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
			
			result.put("code", 200);
			result.put("result", "성공");	
		} else {
			result.put("code", 403);
			result.put("error_message", "존재하지 않는 사용자입니다.");	
		}
		return result;
	}
	
}
