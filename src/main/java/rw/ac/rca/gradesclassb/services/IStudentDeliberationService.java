package rw.ac.rca.gradesclassb.services;


import rw.ac.rca.gradesclassb.dtos.CreateDeliberationDTO;
import rw.ac.rca.gradesclassb.enumerations.EDecision;
import rw.ac.rca.gradesclassb.models.Student;
import rw.ac.rca.gradesclassb.models.StudentDeliberation;

import java.util.List;
import java.util.UUID;

public interface IStudentDeliberationService {

    StudentDeliberation save(CreateDeliberationDTO dto);
    boolean delete(UUID id);
    StudentDeliberation update(UUID id, CreateDeliberationDTO dto);
    StudentDeliberation getById(UUID id);
    StudentDeliberation getByStudentAndAcademicYear(Student student, String academicYear);
    List<StudentDeliberation> getAllByStudent(Student student);
    List<StudentDeliberation> getAllByAcademicYear(String academicYear);
    List<StudentDeliberation> getAllByAcademicYearAndDecision(String academicYear, EDecision decision);
}
