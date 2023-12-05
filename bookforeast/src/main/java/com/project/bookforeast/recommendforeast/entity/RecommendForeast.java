package com.project.bookforeast.recommendforeast.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.project.bookforeast.file.entity.FileGroup;
import com.project.bookforeast.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendForeast {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long recommendForeastId;
	
	private String title;
	private Long state;
	private String address;

	@OneToOne
	@JoinColumn(name = "content_file_id")
	private FileGroup contentFileGroup;
	
	@OneToOne
	@JoinColumn(name = "main_image_id")
	private FileGroup mainImageFileGroup;
	
	private Long lat; // 위도
	private Long lng; // 경도
	
	@ManyToOne
	@JoinColumn(name = "manager_id")
	private User manager;
	
	
	
	
	@ManyToOne
	@JoinColumn(name = "updater_id")
	private User updateUser;
	
	@CreatedDate
	private LocalDateTime registDt;
	
	@UpdateTimestamp
	private Date updateDt;
	
}