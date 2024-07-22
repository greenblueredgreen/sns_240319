package com.sns.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.domain.CommentView;
import com.sns.comment.mapper.CommentMapper;
import com.sns.post.repository.PostRepository;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class CommentBO {
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private UserBO userBO;

	public List<CommentView> generateCommentViewListByPostId(int postId){
		
		List<CommentView> commentViewList = new ArrayList<>();
	
		//댓글들 가져온다
		List<Comment> commentList = commentMapper.selectComment
	
		//반복문 순회 => comment -> CommentView => list에 담음
		for(Comment comment : commentList) {
			CommentView commentView = new CommentView();
			
			// 댓글 1개
			commentView.setComment(comment);
			
			//댓글 쓰니
			UserEntity user = userBO.getUserEntityById(comment.getUserId());
			commentView.setUser(user);
			
			//!!!!!!!!!commentViewList에 반드시 넣기
			commentViewList.add(commentView);
		}
		
		return commentViewList;
	}
}
