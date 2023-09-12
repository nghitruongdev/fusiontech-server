package com.vnco.fusiontech.app.repository;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.UUID;

@Table
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Logging {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;
    
    protected String description;
    
    @CreatedBy
    @Column (name = "created_by", nullable = false, length = 50, updatable = false)
    protected String createdBy;
    
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    protected Instant createdDate = Instant.now();
}
