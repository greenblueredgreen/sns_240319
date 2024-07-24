package com.sns.timeline.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.domain.CommentView;
import com.sns.like.bo.LikeBO;
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
	
	@Autowired
	private LikeBO likeBO;
	
	//input: userId(로그인 된 사람의 번호) -> 비로그인도 접근 가능하게 Integer
	//output: List<CardView>
	public List<CardView> generateCardViewList(Integer userId){ //비로그인도 타임라인은 보여지게 한다 -> null 가능
		List<CardView> cardViewList = new ArrayList<>();
		
		// 글 목록을 가져오기 List<PostEntity>
		List<PostEntity> postList = postBO.getPostEntityList();
		
		// 글 목록 반복문 순회
		// PostEntity => CardView 로 바꾸기 -> cardViewList에 넣기
		// 글에 대한 반복문
		for(PostEntity post : postList) {
			
			//CardView객체 생성
			CardView card = new CardView();
			
			// 글 - 글 1개를 CardView의 필드 post에 설정
			card.setPost(post); 
			
			// 글쓴이 (글을 쓴사람 번호)
			//post.getUserId(); 글 번호 꺼내기 -> 그 글번호에 해당하는 글쓴이 user를 찾는다
			UserEntity user = userBO.getUserEntityById(post.getUserId());
			card.setUser(user);  //cardView의 필드 user에 글쓴이 넣기
			
			// 댓글 N개   //글의 댓글 모두를 가져오는 과정 -> 글 아이디를 가져온다
			List<CommentView> commentViewList = commentBO.generateCommentViewListByPostId(post.getId());
			//댓글을 카드에 넣는다
			card.setCommentList(commentViewList);  //cardView의 필드 commentList에 설정
			
			// 좋아요 개수
			// 남의 MAPPER을 부르는 것은 위험하다
			// post로 접근 -> 그 글의 id 가져오기
			int likeCount = likeBO.getLikeCountByPostId(post.getId());
			card.setLikeCount(likeCount);
			
			// 좋아요 여부 채우기 - 로그인 된 사람이 글을 좋아요를 눌렀는지 여부
			// 다른 MAPPER 절대 호출하지 않기
			card.setFilledLike(likeBO.filledLikeByPostIdUserId(post.getId(), userId));
			
			// !!!!!!!!!!!!!!!!!!! 반드시 list에 넣는다
			cardViewList.add(card);  // 그 cardViewList에 아까 넣은 정보가 있는 card(글쓴이, 글)를 넣는다.
		}
		
		return cardViewList;
	}
}
