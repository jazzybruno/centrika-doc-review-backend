package rw.ac.rca.centrika.dtos.requests;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;
import rw.ac.rca.centrika.models.DocumentReview;
import rw.ac.rca.centrika.models.User;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentDTO {
    private String content;
    private UUID reviewAction;
}
