package com.vivek.journalApp.service;

import com.vivek.journalApp.entity.JournalEntry;
import com.vivek.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(String id) {
        return journalEntryRepository.findById(id);
    }

    public JournalEntry saveEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    public void deleteById(String id) {
        journalEntryRepository.deleteById(id);
    }

    public JournalEntry updateEntryById(String id, JournalEntry journalEntry) {
        Optional<JournalEntry> journalEntryFromDb = journalEntryRepository.findById(id);
        if (journalEntryFromDb.isPresent()) {
            journalEntryFromDb.get().setTitle(journalEntry.getTitle() != null && !journalEntry.getTitle().isEmpty() ?
                    journalEntry.getTitle() : journalEntryFromDb.get().getTitle());
            journalEntryFromDb.get().setContent(journalEntry.getContent() != null && !journalEntry.getContent().isEmpty() ?
                    journalEntry.getContent() : journalEntryFromDb.get().getContent());
            return journalEntryRepository.save(journalEntryFromDb.get());
        }
        return new JournalEntry();
    }


}
