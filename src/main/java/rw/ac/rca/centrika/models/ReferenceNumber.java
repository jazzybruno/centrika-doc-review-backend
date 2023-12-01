package rw.ac.rca.centrika.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.ERefNumStatus;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReferenceNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String referenceNumber;
    private String title;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private ERefNumStatus status;
    private Date updatedAt;
    private Date deletedAt;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
}
