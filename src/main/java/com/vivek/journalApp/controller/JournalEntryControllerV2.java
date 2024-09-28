package com.vivek.journalApp.controller;

import com.vivek.journalApp.entity.JournalEntry;
import com.vivek.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll() {
        List<JournalEntry> allEntries = journalEntryService.getAll();
        if (allEntries != null) {
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable String id) {
        Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry journalEntry1 = journalEntryService.saveEntry(journalEntry);
        if (journalEntry1 != null) {
            return new ResponseEntity<>(journalEntry1, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable String id) {
        try {
            journalEntryService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateEntryById(@PathVariable String id, @RequestBody JournalEntry journalEntry) {
        try {
            JournalEntry journalEntry1 = journalEntryService.updateEntryById(id, journalEntry);
            if (journalEntry1 != null) {
                return new ResponseEntity<>(journalEntry1, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
