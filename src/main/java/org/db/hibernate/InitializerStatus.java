package org.db.hibernate;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "initializer_status")
@Getter
@Setter
public class InitializerStatus {
    @Id
    private int id;
    @Column(nullable = false)
    private boolean isInitialized;
}
