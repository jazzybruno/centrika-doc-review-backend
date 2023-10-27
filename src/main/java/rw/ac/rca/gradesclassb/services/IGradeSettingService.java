package rw.ac.rca.gradesclassb.services;


import rw.ac.rca.gradesclassb.dtos.CreateGradeSettingDTO;
import rw.ac.rca.gradesclassb.models.Course;
import rw.ac.rca.gradesclassb.models.GradeSetting;

import java.util.List;
import java.util.UUID;

public interface IGradeSettingService {

    GradeSetting save(CreateGradeSettingDTO dto);
    boolean delete(UUID id);
    GradeSetting update(UUID id, CreateGradeSettingDTO dto);
    GradeSetting getById(UUID id);
    GradeSetting getByCourse(UUID course);
    GradeSetting getByCourse(Course course);
    List<GradeSetting> getAll();
}
