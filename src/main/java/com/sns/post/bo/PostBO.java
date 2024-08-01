package com.sns.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sns.common.FileManagerService;
import com.sns.post.entity.PostEntity;
import com.sns.post.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostBO {
	//주석
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	// input: X
	// output: List<PostEntity>
	public List<PostEntity> getPostEntityList() {
		return postRepository.findByOrderByIdDesc();
	}
	
	//post 올리기
	//input : int userId, String userLoginId, String content, MultipartFile file
	//output : PostEntity
	public PostEntity addPost(int userId, String userLoginId, String content, MultipartFile file) {
		
		// 업로드 후 imagePath를 받아옴
		String imagePath = fileManagerService.uploadFile(file, userLoginId);
		
		return postRepository.save(
				PostEntity.builder()
				.userId(userId)
				.content(content)
				.imagePath(imagePath)
				.build());
	}
	
	@Transactional  //spring frame : 하나라도 에러가 나면 자동으로 롤백된다
	// 글 삭제(글, 댓글, 좋아요, 이미지)
	public void deletePost(int postId, int userId) {
		
		PostEntity post = postRepository.findById(postId).orElse(null);
		if(post == null) {
			log.info("[글 삭제] post is null. postId : {}, userId : {}", postId, userId);
			return;
		}
		
		//글 삭제
		postRepository.delete(post);
				
		//이미지 있으면 삭제
		if( == null) {
			
		} else {
			
		}
		
		//기존 댓글 삭제
		
		//기존 좋아요들 삭제
		
		
	}
}
