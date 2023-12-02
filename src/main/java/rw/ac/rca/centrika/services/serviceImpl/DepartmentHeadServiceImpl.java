package rw.ac.rca.centrika.services.serviceImpl;

import rw.ac.rca.centrika.dtos.CreateDepartmentHeadDTO;
import rw.ac.rca.centrika.models.DepartmentHead;
import rw.ac.rca.centrika.services.DepartmentHeadService;

import java.util.List;
import java.util.UUID;

public class DepartmentHeadServiceImpl implements DepartmentHeadService {
    @Override
    public List<DepartmentHead> getAllDepartmentHeads() {
        return null;
    }

    @Override
    public DepartmentHead getDepartmentHeadById(UUID departmentHeadId) {
        return null;
    }

    @Override
    public DepartmentHead createDepartmentHead(CreateDepartmentHeadDTO departmentHeadDTO) {
        return null;
    }

    @Override
    public DepartmentHead updateDepartmentHead(UUID departmentHeadId, CreateDepartmentHeadDTO departmentHeadDTO) {
        return null;
    }

    @Override
    public DepartmentHead deleteDepartmentHead(UUID departmentHeadId) {
        return null;
    }

    @Override
    public DepartmentHead getAllDepartmentHeadByDepartmentId(UUID departmentId) {
        return null;
    }

    @Override
    public DepartmentHead getDepartmentHeadByUserId(UUID userId) {
        return null;
    }
}
