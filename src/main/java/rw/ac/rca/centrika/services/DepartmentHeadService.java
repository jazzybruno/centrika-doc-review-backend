package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.CreateDepartmentHeadDTO;
import rw.ac.rca.centrika.models.DepartmentHead;

import java.util.List;
import java.util.UUID;

public interface DepartmentHeadService {
    // CRUD Methods - Create, Read, Update, Delete
    public List<DepartmentHead> getAllDepartmentHeads();
    public DepartmentHead getDepartmentHeadById(UUID departmentHeadId);
    public DepartmentHead createDepartmentHead(CreateDepartmentHeadDTO departmentHeadDTO);
    public DepartmentHead updateDepartmentHead(UUID departmentHeadId, CreateDepartmentHeadDTO departmentHeadDTO);
    public DepartmentHead deleteDepartmentHead(UUID departmentHeadId);

    // Custom Methods
    public DepartmentHead getAllDepartmentHeadByDepartmentId(UUID departmentId);
    public DepartmentHead getDepartmentHeadByUserId(UUID userId);
}
