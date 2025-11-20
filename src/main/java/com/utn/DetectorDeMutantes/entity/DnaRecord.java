package com.utn.DetectorDeMutantes.entity;
import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder

@Entity
@Table(name = "DnaRecord")
public class DnaRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dna_hash", length = 64, unique = true, nullable = false)
    private String dnaHash;

    @Column(name = "is_mutant", nullable = false)
    private Boolean isMutant;

    @Column(name = "created_at")
    private Timestamp createdAt;
}

