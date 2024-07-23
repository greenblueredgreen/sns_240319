package com.sns.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.like.domain.Like;
import com.sns.like.mapper.LikeMapper;

@Service
public class LikeBO {
	
	@Autowired
	private LikeMapper likeMapper;
	
	public void removeLike(int postId, int userId) {
		likeMapper.deleteLike(postId, userId);
	}
	
	public void addLike(int postId, int userId) {
		likeMapper.insertLike(postId, userId);
	}

	// input : int postId, int userId
	// output : x
	public void likeToggle(int postId, int userId) {
		// 조회
		// select는 담을 공간이 있어야한다
		int count = likeMapper.selectLikeCountByPostIdUserId(postId, userId);
		
		// 여부 => 삭제 or 추가
		if(count > 0) {
			// 좋아요 있음 -> 좋아요 제거
			addLike(userId, postId);
		} else {
			// 좋아요 없음 -> 좋아요 추가
			removeLike(userId, postId);
		}
	}

}
