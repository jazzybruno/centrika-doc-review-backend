package rw.ac.rca.centrika.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.EDocStatus;
import rw.ac.rca.centrika.enumerations.EReviewStatus;

import javax.annotation.Nullable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDocumentDTO {
    private UUID reviewer;
    private UUID reviewDocId;
    private String commentContent;
    private EReviewStatus status;

}
