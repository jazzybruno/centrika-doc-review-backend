package rw.ac.rca.centrika.dtos.requests;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.models.DocumentReview;
import rw.ac.rca.centrika.models.User;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHistoryDTO {
    private UUID documentId;
    private UUID requesterId;
    private UUID approverId;
    private UUID documentReviewId;

}
