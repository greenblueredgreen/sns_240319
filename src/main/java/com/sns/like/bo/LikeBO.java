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
	
	
	// input : postId, userId
	// output : int
	public int getLikeCountByPostIdUserId(int postId, int userId) {
		return likeMapper.selectLikeCountByPostIdUserId(postId, userId);
	}
	
	/**
	 * 좋아요 개수 반환 bo
	 * @param postId
	 * @return
	 */
	// input : postId
	// output : int (좋아요 개수)
	public int getLikeCountByPostId(int postId) {
		return likeMapper.selectLikeCountByPostId(postId);
	}
	
	/**
	 * 로그인이면 좋아요 채우기 가능, 비로그인이면 좋아요 채우기 불가능
	 * @param postId
	 * @param userId
	 * @return
	 */
	// 좋아요 채울지 여부
	// input : 	postId(필수), userId(로그인/ 비로그인)
	// output :  boolean(채울지 여부)
	public boolean filledLikeByPostIdUserId(int postId, Integer userId) {
		// 비로그인이면 DB에 조회해볼 필요가 없다 -> 하트 채우지 않음
		if(userId == null) {
			return false;
		} 
		
		// 로그인일때) 1. 행이 있으면 (1) true 2.행이 없으면(0) false
		return likeMapper.selectLikeCountByPostIdUserId(postId, userId) == 1 ? true : false;
	}

}
