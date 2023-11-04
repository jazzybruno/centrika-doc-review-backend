package rw.ac.rca.centrika.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.EReviewStatus;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForwardDocumentDTO {
    private UUID reviewer;
    private UUID reviewDocId;
    private String commentContent;
    private UUID newReviewerId;

}
