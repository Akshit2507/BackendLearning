package com.backend.project.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Regex")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegexRecord {

    @Id
    private String id;
    private String regex;

}
