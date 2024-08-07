package com.sns.user.bo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest  //필수
class UserBOTest {
	
	@Autowired
	UserBO userBO;
	
	@Transactional  //springframe // insert하고 다시 롤백(없었던일로해준다)-> 테스트용도
	@Test
	void 회원가입() {
		userBO.addUser("TEST", "TEST", "이름", "이메일");
	}
	
	//실행방법 : 클래스 우클릭 -> run as -> junit test
	//이런식으로 자주 활용할 것
	//given, when, then
}
