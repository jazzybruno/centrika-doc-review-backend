package rw.ac.rca.centrika.dtos.requests;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.models.DocumentReview;
import rw.ac.rca.centrika.models.User;

import java.util.UUID;

public class CreateHistoryDTO {
    private UUID documentId;
    private UUID requesterId;
    private UUID approverId;
    private UUID documentReviewId;

}
