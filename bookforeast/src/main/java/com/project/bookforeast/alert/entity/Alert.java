package com.project.bookforeast.alert.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.bookforeast.alert.dto.AlertDTO;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long alertId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	
	private String category;
	private String message;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime registDt;
	
	
	public AlertDTO toDTO() {
		AlertDTO.AlertDTOBuilder alertDTOBuilder = AlertDTO.builder()
													.alertId(alertId)
													.category(category)
													.message(message)
													.registDt(registDt);
		
		if(user != null) {
			alertDTOBuilder.userDTO(user.toDTO());
		}
		
		return alertDTOBuilder.build();
	}
}
