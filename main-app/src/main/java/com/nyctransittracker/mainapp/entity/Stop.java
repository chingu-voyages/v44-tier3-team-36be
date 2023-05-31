package com.nyctransittracker.mainapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

@Entity
@Table(name="STOP")

public class Stop {
    @Id
    @Column(name="ID", length=50, nullable=false)
    private Long uniqueId;
    @Column(name="STOP_ID", length=50, nullable=false)
    @JsonProperty("id")
    private String GTFSStopID;
    @Transient
    private Map<String, List> routes;
    @Column(name="ROUTE", length=50, nullable=false, unique=false)
    private String route;
    @Column(name="DIRECTION", length=50, nullable=false, unique=false)
    private String direction;
    @Column(name="NAME", length=50, nullable=false, unique=false)
    private String name;
    @Column(name="LATITUDE", length=50, nullable=false, unique=false)
    private Double latitude;
    @Column(name="LONGITUDE", length=50, nullable=false, unique=false)
    private Double longitude;
}