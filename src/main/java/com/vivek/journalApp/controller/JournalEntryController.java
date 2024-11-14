package com.vivek.journalApp.controller;

import com.vivek.journalApp.entity.JournalEntry;
import com.vivek.journalApp.entity.User;
import com.vivek.journalApp.repository.UserRepository;
import com.vivek.journalApp.service.JournalEntryService;
import com.vivek.journalApp.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllEntriesByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  // security context
        String username = authentication.getName();
        List<JournalEntry> allEntries = journalEntryService.getAllEntriesByUserName(username);
        if (allEntries != null) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  // security context
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        List<JournalEntry> journalEntries = user.getJournalEntries();
        List<JournalEntry> list = journalEntries.stream().filter(x -> x.getId().equals(id)).toList();
        if(!list.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  // security context
        String username = authentication.getName();

        JournalEntry journalEntry1 = journalEntryService.saveEntry(journalEntry, username);
        if (journalEntry1 != null) {
            return new ResponseEntity<>(journalEntry1, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  // security context
        String username = authentication.getName();
        try {
            journalEntryService.deleteById(username, id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateEntryById(@PathVariable String id, @RequestBody JournalEntry journalEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  // security context
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<JournalEntry> list = journalEntries.stream().filter(x -> x.getId().equals(id)).toList();
            if (!list.isEmpty()) {
                Optional<JournalEntry> journalEntryFromDB = journalEntryService.findById(id);
                if (journalEntryFromDB.isPresent()) {
                    JournalEntry journalEntry1 = journalEntryService.updateEntryById(id, journalEntry);
                    return new ResponseEntity<>(journalEntry1, HttpStatus.OK);

                }
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
