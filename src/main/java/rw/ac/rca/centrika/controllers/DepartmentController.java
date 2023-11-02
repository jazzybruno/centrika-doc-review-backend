package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.requests.CreateDepartmentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDepartmentDTO;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.services.serviceImpl.DepartmentServiceImp;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/department")
public class DepartmentController {
    private final DepartmentServiceImp departmentService;

    @Autowired
    public DepartmentController(DepartmentServiceImp departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/id/{deptId}")
    public Department getDepartmentById(@PathVariable UUID deptId) {
        return departmentService.getDepartmentById(deptId);
    }

    @PostMapping("/create")
    public Department createDepartment(@RequestBody CreateDepartmentDTO createDepartmentDTO) {
        return departmentService.createDepartment(createDepartmentDTO);
    }

    @PutMapping("/update/{deptId}")
    public Department updateDepartment(@PathVariable UUID deptId, @RequestBody UpdateDepartmentDTO updateDepartmentDTO) {
        return departmentService.updateDepartment(deptId, updateDepartmentDTO);
    }

    @DeleteMapping("/delete/{deptId}")
    public Department deleteDepartment(@PathVariable UUID deptId) {
        return departmentService.deleteDepartment(deptId);
    }
}
