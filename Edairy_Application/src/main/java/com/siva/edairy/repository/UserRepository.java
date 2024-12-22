package com.siva.edairy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siva.edairy.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Boolean existsByEmail(String email);
	Boolean existsByMobile(String mobile);
	User findByEmailAndPassword(String email, String password);
}
