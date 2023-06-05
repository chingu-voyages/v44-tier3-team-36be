package com.nyctransittracker.mainapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

@Entity
@Table(name="STOPS")

public class Stop {
    @Id
    @Column(name = "ID")
    private String uniqueId;
    @Column(name="GTFS_STOP_ID", length=50, nullable=false)
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
    @Column(name="SUBSCRIBED_USERS", unique=false)
    @ManyToMany
    @JoinTable(
            name = "SUBSCRIPTIONS",
            joinColumns = @JoinColumn(name = "STOP_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private Set<User> subscribedUsers;
}