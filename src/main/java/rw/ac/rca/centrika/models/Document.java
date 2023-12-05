package rw.ac.rca.centrika.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.ECategory;
import rw.ac.rca.centrika.enumerations.EDocStatus;

import java.util.Date;
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

   @OneToOne
   @JoinColumn(name = "reference_number_id")
   private ReferenceNumber referenceNumber;
   @ManyToOne
   @JoinColumn(name = "created_by")
   private User createdBy;
   @Column(name = "created_at")
   private Date createdAt;
   @Column(name = "updated_at")
   private Date updatedAt;

   public Document(String title, String description, String fileUrl, ECategory category, EDocStatus status, User createdBy) {
      this.title = title;
      this.description = description;
      this.fileUrl = fileUrl;
      this.category = category;
      this.status = status;
      this.createdBy = createdBy;
   }
}
