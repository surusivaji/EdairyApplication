package com.siva.edairy.service;

import org.springframework.data.domain.Page;

import com.siva.edairy.model.Entry;
import com.siva.edairy.model.User;

public interface IEntryService {
	
	Entry addEntry(Entry entry);
	Page<Entry> getAllEntries(User user, int pageNo);
	Entry getEntryById(int id);
	boolean removeEntryById(int id);
	boolean removeEntriesByUser(User user);
	
	
}
