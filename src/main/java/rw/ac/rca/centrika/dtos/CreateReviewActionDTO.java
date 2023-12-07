package rw.ac.rca.centrika.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.EReviewStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewActionDTO {
    private UUID reviewerId;
    private UUID documentReviewId;
    private EReviewStatus action;
    private String comment;
}
