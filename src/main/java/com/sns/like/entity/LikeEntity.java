package com.sns.like.entity;

import java.time.LocalDateTime;

import com.sns.post.entity.PostEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter	
@Table(name = "post")
@Entity
public class LikeEntity {
	
	@Column(name = "postId")
	private int postId;
	
	@Column(name = "userId")
	private int userId;
	
	@Column(name = "createdAt")
	private int createdAt;
}
