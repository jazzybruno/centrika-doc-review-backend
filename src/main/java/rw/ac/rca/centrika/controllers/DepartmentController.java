package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.requests.CreateDepartmentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDepartmentDTO;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.services.serviceImpl.DepartmentServiceImp;
import rw.ac.rca.centrika.utils.ApiResponse;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    private final DepartmentServiceImp departmentService;

    @Autowired
    public DepartmentController(DepartmentServiceImp departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllDepartments() {
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all departments",
                departmentService.getAllDepartments()
        ));
    }

    @GetMapping("/id/{deptId}")
    public ResponseEntity<ApiResponse> getDepartmentById(@PathVariable UUID deptId) {
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched the department",
                departmentService.getDepartmentById(deptId)
        ));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createDepartment(@RequestBody CreateDepartmentDTO createDepartmentDTO) {
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully created the department",
                departmentService.createDepartment(createDepartmentDTO)
        ));
    }

    @PutMapping("/update/{deptId}")
    public ResponseEntity<ApiResponse> updateDepartment(@PathVariable UUID deptId, @RequestBody UpdateDepartmentDTO updateDepartmentDTO) {
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated the departments",
                departmentService.updateDepartment(deptId, updateDepartmentDTO)
        ));
    }

    @DeleteMapping("/delete/{deptId}")
    public ResponseEntity<ApiResponse> deleteDepartment(@PathVariable UUID deptId) {
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully deleted the departments",
                departmentService.deleteDepartment(deptId)
        ));
    }

}
