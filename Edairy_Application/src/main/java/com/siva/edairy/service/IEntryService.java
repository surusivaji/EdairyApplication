package com.siva.edairy.service;

import java.util.List;

import com.siva.edairy.model.Entry;
import com.siva.edairy.model.User;

public interface IEntryService {
	
	Entry addEntry(Entry entry);
	List<Entry> getAllEntries(User user);
	Entry getEntryById(int id);
	boolean removeEntryById(int id);
	
}
