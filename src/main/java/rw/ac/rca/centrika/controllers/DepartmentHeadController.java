package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.CreateDepartmentHeadDTO;
import rw.ac.rca.centrika.dtos.UpdateDepartmentHeadDTO;
import rw.ac.rca.centrika.models.DepartmentHead;
import rw.ac.rca.centrika.services.DepartmentHeadService;
import rw.ac.rca.centrika.utils.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping("/api/department-heads")
public class DepartmentHeadController {

    private final DepartmentHeadService departmentHeadService;

    @Autowired
    public DepartmentHeadController(DepartmentHeadService departmentHeadService) {
        this.departmentHeadService = departmentHeadService;
    }

    @GetMapping
    public ResponseEntity getAllDepartmentHeads() {
        List<DepartmentHead> departmentHeads = departmentHeadService.getAllDepartmentHeads();
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all department heads",
                departmentHeads
        ));
    }

    @GetMapping("/{departmentHeadId}")
    public ResponseEntity getDepartmentHeadById(@PathVariable UUID departmentHeadId) {
        DepartmentHead departmentHead = departmentHeadService.getDepartmentHeadById(departmentHeadId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched department head by id",
                departmentHead
        ));
    }

    @PostMapping("/create")
    public ResponseEntity createDepartmentHead(@RequestBody CreateDepartmentHeadDTO departmentHeadDTO) {
        DepartmentHead departmentHead = departmentHeadService.createDepartmentHead(departmentHeadDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully created department head",
                departmentHead
        ));
    }

    @PutMapping("/{departmentHeadId}")
    public ResponseEntity updateDepartmentHead(@PathVariable UUID departmentHeadId, @RequestBody UpdateDepartmentHeadDTO departmentHeadDTO) {
        DepartmentHead departmentHead = departmentHeadService.updateDepartmentHead(departmentHeadId, departmentHeadDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated department head",
                departmentHead
        ));
    }

    @DeleteMapping("/{departmentHeadId}")
    public ResponseEntity deleteDepartmentHead(@PathVariable UUID departmentHeadId) {
        DepartmentHead departmentHead = departmentHeadService.deleteDepartmentHead(departmentHeadId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully deleted department head",
                departmentHead
        ));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity getAllDepartmentHeadByDepartmentId(@PathVariable UUID departmentId) {
        DepartmentHead departmentHead = departmentHeadService.getAllDepartmentHeadByDepartmentId(departmentId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched department head by department id",
                departmentHead
        ));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getDepartmentHeadByUserId(@PathVariable UUID userId) {
        DepartmentHead departmentHead = departmentHeadService.getDepartmentHeadByUserId(userId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched department head by user id",
                departmentHead
        ));
    }

}
