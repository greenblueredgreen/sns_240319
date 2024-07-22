package com.sns.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.user.entity.UserEntity;
import com.sns.user.repository.UserRepository;

@Service
public class UserBO {
	
	@Autowired
	private UserRepository userRepository;

	//중복확인
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	}
	
	//회원가입
	public UserEntity addUser(String loginId, String password, String name, String email) {
		return userRepository.save(UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build());
	}
	
	//로그인 : 아이디, 비밀번호 
	public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) {
		return userRepository.findByLoginIdAndPassword(loginId, password);
	}
	
	// pk로 조회
	public UserEntity getUserEntityById(int userId) {
		return userRepository.findById(userId).orElse(null);
	}
}
