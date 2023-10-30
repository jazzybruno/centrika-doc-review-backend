package rw.ac.rca.centrika.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHistoryDTO {
    private UUID documentId;
    private UUID requesterId;
    private UUID approverId;
    private UUID documentReviewId;

}
