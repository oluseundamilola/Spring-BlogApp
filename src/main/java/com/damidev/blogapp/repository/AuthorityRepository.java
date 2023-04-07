package com.damidev.blogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.damidev.blogapp.models.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {

}
