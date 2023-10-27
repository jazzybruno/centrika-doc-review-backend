package rw.ac.rca.centrika.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.ECategory;
import rw.ac.rca.centrika.enumerations.EDocStatus;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Document {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private UUID id;
   private String title;
   private String description;
   private String fileUrl;
   @Enumerated(EnumType.STRING)
   private ECategory category;
   @Enumerated(EnumType.STRING)
   private EDocStatus status;
   private int referenceNumber;
   @ManyToOne
   @JoinColumn(name = "created_by")
   private User createdBy;
   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "document_department" , joinColumns = @JoinColumn(name = "user_id") , inverseJoinColumns = @JoinColumn(name = "document_id"))
   private Set<Department> departments = new HashSet<Department>();
}
