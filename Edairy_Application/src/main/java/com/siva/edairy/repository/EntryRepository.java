package com.siva.edairy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.siva.edairy.model.Entry;
import com.siva.edairy.model.User;

public interface EntryRepository extends JpaRepository<Entry, Integer> {
	
	List<Entry> findByUser(User user);
	Page<Entry> findByUser(User user, Pageable pageable);
	
}
