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

    // key identification
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // details of the reference number
    private String referenceNumber;
    private String title;
    private String destination;
    @Enumerated(EnumType.STRING)
    private ERefNumStatus status;

    // details of the expiration and creation
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    // relationships
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
}
