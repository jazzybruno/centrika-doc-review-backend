package rw.ac.rca.centrika.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewerDTO {
    private UUID userId;
    private UUID documentReviewId;

}
