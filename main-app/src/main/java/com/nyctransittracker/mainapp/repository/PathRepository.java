package com.nyctransittracker.mainapp.repository;

import com.nyctransittracker.mainapp.model.Path;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PathRepository extends MongoRepository<Path, String> {
    public Optional<Path> findByPathName(String pathName);
}
