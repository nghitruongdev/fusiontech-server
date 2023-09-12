package com.vnco.fusiontech.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base abstract class for entities which will hold definitions for created, last modified, created by,
 * last modified by attributes.
 */
@Setter@Getter
@SuperBuilder
@NoArgsConstructor@AllArgsConstructor
@MappedSuperclass
@EntityListeners (AuditingEntityListener.class)
@JsonIgnoreProperties (value = {"createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate" }, allowGetters = true)
public abstract class AbstractAuditingEntity<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public abstract T getId();

    @CreatedBy
    @ManyToOne
    @JoinColumn (name = "created_by", nullable = false, updatable = false)
    @JsonIncludeProperties({"id, firstName"})
    protected AppUser createdBy;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    protected LocalDateTime createdDate = LocalDateTime.now();

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "last_modified_by")
    @JsonIncludeProperties({"id", "name"})
    protected AppUser lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    protected LocalDateTime lastModifiedDate = LocalDateTime.now();
    
    public void setLastModifiedBy(AppUser lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
    
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
