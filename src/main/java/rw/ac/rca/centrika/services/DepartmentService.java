package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.requests.CreateDepartmentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentDTO;
import rw.ac.rca.centrika.models.Department;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {
    public List<Department> getAllDepartments();
    public Department getDepartmentById(UUID deptId);
    public Department createDepartment(CreateDepartmentDTO createDepartmentDTO);
    public Department updateDepartment(UUID deptId , UpdateDocumentDTO updateDocumentDTO);
    public Department deleteDepartment(UUID deptId);

}
