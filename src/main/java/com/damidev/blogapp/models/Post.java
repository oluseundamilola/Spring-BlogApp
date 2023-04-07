package com.damidev.blogapp.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "posts")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String body;
	
	private String tag;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime lastUpdatedAt;
	
	@NotNull
	@ManyToOne
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private Account account;
	
	

}
