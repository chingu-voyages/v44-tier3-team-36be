package com.nyctransittracker.mainapp.repository;

import com.nyctransittracker.mainapp.model.Stop;
import com.nyctransittracker.mainapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StopRepository
        extends JpaRepository<Stop, String> {
}