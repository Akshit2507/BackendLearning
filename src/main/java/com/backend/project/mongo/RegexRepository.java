package com.backend.project.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegexRepository extends MongoRepository<RegexRecord,String > {
    RegexRecord findTopByRegex(String regex);
}

