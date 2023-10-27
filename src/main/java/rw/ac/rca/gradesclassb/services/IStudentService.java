package rw.ac.rca.gradesclassb.services;


import rw.ac.rca.gradesclassb.dtos.CreateStudentDTO;
import rw.ac.rca.gradesclassb.enumerations.EGender;
import rw.ac.rca.gradesclassb.enumerations.EStatus;
import rw.ac.rca.gradesclassb.models.Student;

import java.util.List;
import java.util.UUID;

public interface IStudentService {

    Student save(CreateStudentDTO dto);
    boolean delete(UUID id);
    Student update(UUID id, CreateStudentDTO dto);
    Student getById(UUID id);
    Student getByRegistrationCode(String registrationCode);
    List<Student> getAll();
    List<Student> getAllByStatus(EStatus status);
    List<Student> getAllByGender(EGender status);
}
