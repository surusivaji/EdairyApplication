package com.siva.edairy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siva.edairy.model.Entry;
import com.siva.edairy.model.User;
import com.siva.edairy.repository.EntryRepository;

@Service
public class IEntryServiceImpl implements IEntryService {
	
	@Autowired
	private EntryRepository entryRepository;
	
	@Override
	public Entry addEntry(Entry entry) {
		try {
			Entry save = entryRepository.save(entry);
			if (save!=null) {
				return save;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Entry> getAllEntries(User user) {
		try {
			List<Entry> entries = entryRepository.findByUser(user);
			return entries;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Entry getEntryById(int id) {
		try {
			Optional<Entry> optional = entryRepository.findById(id);
			if (optional.isPresent()) {
				return optional.get();
			}
			else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public boolean removeEntryById(int id) {
		try {
			entryRepository.deleteById(id);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
}
