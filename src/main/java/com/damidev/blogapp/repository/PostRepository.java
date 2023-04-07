package com.damidev.blogapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.damidev.blogapp.models.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
