package rw.ac.rca.centrika.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.ERelationType;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocumentRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "parent_document_id")
    private Document parentDocument;
    @ManyToOne
    @JoinColumn(name = "child_document_id")
    private Document childDocument;

    @Enumerated(EnumType.STRING)
    private ERelationType relationType;

    public DocumentRelation(Document parentDocument, Document childDocument, ERelationType relationType) {
        this.parentDocument = parentDocument;
        this.childDocument = childDocument;
        this.relationType = relationType;
    }
}
