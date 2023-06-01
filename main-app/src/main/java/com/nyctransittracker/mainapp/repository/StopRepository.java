package com.nyctransittracker.mainapp.repository;

import com.nyctransittracker.mainapp.model.Stop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StopRepository
        extends JpaRepository<Stop, String> {
}