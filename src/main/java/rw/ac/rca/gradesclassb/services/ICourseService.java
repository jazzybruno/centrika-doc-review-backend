package rw.ac.rca.gradesclassb.services;


import rw.ac.rca.gradesclassb.dtos.CreateCourseDTO;
import rw.ac.rca.gradesclassb.enumerations.EStatus;
import rw.ac.rca.gradesclassb.models.Course;

import java.util.List;
import java.util.UUID;

public interface ICourseService {

    Course save(CreateCourseDTO dto);
    boolean delete(UUID id);
    Course update(UUID id, CreateCourseDTO dto);
    Course getById(UUID id);
    Course getByCode(String code);
    List<Course> getAll();
    List<Course> getAllByStatus(EStatus status);
}
