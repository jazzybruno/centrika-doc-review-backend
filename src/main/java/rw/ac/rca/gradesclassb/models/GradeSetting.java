package rw.ac.rca.gradesclassb.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GradeSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Double minScore;

    private Double maxScore;

    private String grade;

}
