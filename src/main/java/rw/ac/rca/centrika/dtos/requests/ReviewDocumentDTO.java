package rw.ac.rca.centrika.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.EDocStatus;
import rw.ac.rca.centrika.enumerations.EReviewStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDocumentDTO {
    private UUID reviewer;
    private UUID reviewDocId;
    private String commentContent;
    private EReviewStatus eReviewStatus;
    private UUID deptId;
    private UUID newReviewerId;

    public ReviewDocumentDTO(UUID reviewer, UUID reviewDocId, String commentContent, EReviewStatus eReviewStatus) {
        this.reviewer = reviewer;
        this.reviewDocId = reviewDocId;
        this.commentContent = commentContent;
        this.eReviewStatus = eReviewStatus;
    }
}
