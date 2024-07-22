package com.sns.comment.domain;

import javax.xml.stream.events.Comment;

import com.sns.user.entity.UserEntity;

import lombok.Data;
import lombok.ToString;

//댓글 1개
@Data
@ToString
public class CommentView {
	// 댓글 1개
	private Comment comment;
	
	// 댓글 쓴이
	private UserEntity user;
	
}
