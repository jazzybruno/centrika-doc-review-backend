package rw.ac.rca.gradesclassb.services;


import rw.ac.rca.gradesclassb.dtos.CreateStudentCourseMarkDTO;
import rw.ac.rca.gradesclassb.enumerations.EDecision;
import rw.ac.rca.gradesclassb.models.Course;
import rw.ac.rca.gradesclassb.models.Student;
import rw.ac.rca.gradesclassb.models.StudentCourseMark;

import java.util.List;
import java.util.UUID;

public interface IStudentMarkService {

    StudentCourseMark save(CreateStudentCourseMarkDTO dto);
    boolean delete(UUID id);
    StudentCourseMark update(UUID id, CreateStudentCourseMarkDTO dto);
    StudentCourseMark getById(UUID id);
    StudentCourseMark getByCourseAndStudent(UUID course, UUID student, String academicYear);
    StudentCourseMark getByCourseAndStudent(Course course, Student student, String academicYear);
    List<StudentCourseMark> getAllByCourseAndStudent(Course course, Student student);
    List<StudentCourseMark> getAllByCourseAndDecision(Course course, EDecision decision);
}
