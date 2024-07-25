package com.sns.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.domain.Comment;
import com.sns.comment.domain.CommentView;
import com.sns.comment.mapper.CommentMapper;
import com.sns.post.repository.PostRepository;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class CommentBO {

	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private UserBO userBO;
	
	//input : postId, userId, content
	//output : x
	public void addComment(int postId, int userId, String content) {
		commentMapper.insertComment(postId, userId, content);
	}

	public List<CommentView> generateCommentViewListByPostId(int postId){
		
		List<CommentView> commentViewList = new ArrayList<>();
	
		//댓글들 가져온다
		List<Comment> commentList = commentMapper.selectCommentListByPostId(postId);
	
		// 한 게시물에 대한 댓글들 순회
		// 반복문 순회 => comment -> CommentView => list에 담음
		for(Comment comment : commentList) {
			
			CommentView commentView = new CommentView();
			
			// 댓글 1개
			commentView.setComment(comment);  //한 댓글 넣기 
			
			//댓글 쓰니
			UserEntity user = userBO.getUserEntityById(comment.getUserId()); // 그 댓글의 userId가져오기 -> 그 아이디로 bo조회
			commentView.setUser(user);
			
			//!!!!!!!!!commentViewList에 반드시 넣기
			commentViewList.add(commentView);
		}
		
		return commentViewList;
	}
	
	public void deleteCommentById(int id) {
		commentMapper.deleteCommentById(id);
	}
}
