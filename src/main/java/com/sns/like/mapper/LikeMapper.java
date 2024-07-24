package com.sns.like.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
	
	public int selectLikeCountByPostIdUserId(
			@Param("postId")int postId, 
			@Param("userId") int userId
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