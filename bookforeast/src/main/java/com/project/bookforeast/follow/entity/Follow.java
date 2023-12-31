package com.project.bookforeast.follow.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.follow.dto.FollowDTO;
import com.project.bookforeast.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int followId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "following_id")
	private User followingUser;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "follower_id")
	private User followerUser;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	public FollowDTO toDTO() {
		FollowDTO.FollowDTOBuilder followDTOBuilder = FollowDTO.builder()
														.followId(followId);
		
		if(followingUser != null) {
			followDTOBuilder.followingUserDTO(followingUser.toDTO());
		}
		
		if(followerUser != null) {
			followDTOBuilder.followerUserDTO(followerUser.toDTO());
		}
		
		return followDTOBuilder.build();
	}
 }
