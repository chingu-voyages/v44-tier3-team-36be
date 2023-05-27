package com.nyctransittracker.mainapp.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "paths")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Path {
    @Id
    private String id;
    @Indexed(unique = true)
    private String pathName;
    private List<Coordinate> coordinates;
}
