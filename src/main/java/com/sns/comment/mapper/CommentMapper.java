package com.sns.comment.mapper;

p
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {

	public int insertComment(
			@Param("postId") int postId, 
			@Param("userId") int userId, 
			@Param("content") String content);
	
	public List<Comment> selectCommentListByPostId(int postId);
}