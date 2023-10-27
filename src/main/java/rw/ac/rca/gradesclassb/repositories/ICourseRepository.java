package rw.ac.rca.gradesclassb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.rca.gradesclassb.models.Course;

import java.util.UUID;

@Repository
public interface ICourseRepository extends JpaRepository<Course, UUID> {
}
