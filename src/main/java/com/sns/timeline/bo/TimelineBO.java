package com.sns.timeline.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.domain.CommentView;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import com.sns.timeline.domain.CardView;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;

@Service
public class TimelineBO {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	private PostBO postBO;
	
	@Autowired
	private CommentBO commentBO;
	
	//input: x
	//output: List<CardView>
	public List<CardView> generateCardViewList(){
		List<CardView> cardViewList = new ArrayList<>();
		
		// 글 목록을 가져오기 List<PostEntity>
		List<PostEntity> postList = postBO.getPostEntityList();
		
		// 글 목록 반복문 순회
		// PostEntity => CardView 로 바꾸기 -> cardViewList에 넣기
		// 글에 대한 반복문
		for(PostEntity post : postList) {
			CardView card = new CardView();
			
			// 글 - 글 1개를 cardView에 설정
			card.setPost(post); 
			
			// 글쓴이
			//post.getUserId(); 글 번호 꺼내기 -> 그 글번호에 해당하는 글쓴이 user를 찾는다
			UserEntity user = userBO.getUserEntityById(post.getUserId());
			card.setUser(user);  //cardView에 글쓴이 넣기
			
			// 댓글 N개   //글의 댓글 모두를 가져오는 과정 -> 글 아이디를 가져온다
			List<CommentView> commentViewList = commentBO.generateCommentViewListByPostId(post.getId());
			//댓글을 카드에 넣는다
			card.setCommentList(commentViewList);
			
			// !!!!!!!!!!!!!!!!!!! 반드시 list에 넣는다
			cardViewList.add(card);  // 그 cardViewList에 아까 넣은 정보가 있는 card(글쓴이, 글)를 넣는다.
		}
		
		return cardViewList;
	}
}
