package com.vivek.journalApp.repository;

import com.vivek.journalApp.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, Object> {
}
