package rw.ac.rca.gradesclassb.services.impl;

import org.springframework.stereotype.Service;
import rw.ac.rca.gradesclassb.dtos.CreateDeliberationDTO;
import rw.ac.rca.gradesclassb.enumerations.EDecision;
import rw.ac.rca.gradesclassb.models.Student;
import rw.ac.rca.gradesclassb.models.StudentDeliberation;
import rw.ac.rca.gradesclassb.services.IStudentDeliberationService;

import java.util.List;
import java.util.UUID;

@Service
public class StudentDeliberationServiceImpl implements IStudentDeliberationService {
    @Override
    public StudentDeliberation save(CreateDeliberationDTO dto) {
        return null;
    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }

    @Override
    public StudentDeliberation update(UUID id, CreateDeliberationDTO dto) {
        return null;
    }

    @Override
    public StudentDeliberation getById(UUID id) {
        return null;
    }

    @Override
    public StudentDeliberation getByStudentAndAcademicYear(Student student, String academicYear) {
        return null;
    }

    @Override
    public List<StudentDeliberation> getAllByStudent(Student student) {
        return null;
    }

    @Override
    public List<StudentDeliberation> getAllByAcademicYear(String academicYear) {
        return null;
    }

    @Override
    public List<StudentDeliberation> getAllByAcademicYearAndDecision(String academicYear, EDecision decision) {
        return null;
    }
}
