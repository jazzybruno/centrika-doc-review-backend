package rw.ac.rca.centrika.dtos.requests;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.ac.rca.centrika.enumerations.ECategory;
import rw.ac.rca.centrika.enumerations.EDocStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestReviewDTO {
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private ECategory category;
    private UUID departmentId;
    public UUID reviewer;
    public UUID creator;

    @Override
    public String toString() {
        return "RequestReviewDTO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", departmentId=" + departmentId +
                ", reviewer=" + reviewer +
                ", creator=" + creator +
                '}';
    }
}
