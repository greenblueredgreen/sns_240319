package com.sns.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
	
	public int selectLikeCountByPostIdUserId(
			@Param("postId")int postId, 
			@Param("userId") int userId
			);
	
	//파라미터 1개라 @Param 아님
	public int selectLikeCountByPostId(int postId);
	
	//카운트 쿼리 하나로 합치기
	public int selectLikeCountByPostIdOrUserId(
		@Param("postId")int postId, 
		@Param("userId") Integer userId //null 허용
		);
	
	public void insertLike(
			@Param("postId")int postId, 
			@Param("userId") int userId
			);
	
	public void deleteLike(
			@Param("postId")int postId, 
			@Param("userId") int userId
			);
}
