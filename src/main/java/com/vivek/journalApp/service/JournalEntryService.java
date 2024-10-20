package com.vivek.journalApp.service;

import com.vivek.journalApp.entity.JournalEntry;
import com.vivek.journalApp.entity.User;
import com.vivek.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public List<JournalEntry> getAllEntriesByUserName(String userName) {
        User user = userService.findByUserName(userName);
        return user.getJournalEntries();
    }

    public Optional<JournalEntry> findById(String id) {
        return journalEntryRepository.findById(id);
    }

    public JournalEntry saveEntry(JournalEntry journalEntry, String userName) {
        User savedUser = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
        savedUser.getJournalEntries().add(savedJournalEntry);
        userService.saveUser(savedUser);
        return savedJournalEntry;
    }

    public void deleteById(String userName, String id) {
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
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
