package com.vivek.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)   // spring don't do indexing by itself
    @NonNull
    private String username;

    @NonNull
    private String password;

    @DBRef   // ref of object from journal entry -- act as foreign key
    List<JournalEntry> journalEntries = new ArrayList<>();

}
