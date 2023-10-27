package rw.ac.rca.gradesclassb.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.gradesclassb.dtos.CreateStudentDTO;
import rw.ac.rca.gradesclassb.models.Student;
import rw.ac.rca.gradesclassb.services.IStudentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/students")
@Slf4j
@RequiredArgsConstructor
public class StudentController {

    public static final String ENTITY = "Student";
    private final IStudentService studentService;

    @PostMapping
    public ResponseEntity<Student> save(
            @Valid @RequestBody CreateStudentDTO dto)  {
        log.info("save new {} with dto {}",ENTITY, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.studentService.save(dto));
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Student> update(
            @PathVariable(value = "id") UUID id,
            @Valid @RequestBody CreateStudentDTO dto) {
        log.info("update {} id: {}: with {}", ENTITY,id,dto);
        return ResponseEntity.status(HttpStatus.OK).body(
                this.studentService.update(id,dto));
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> remove(
            @PathVariable(value = "id") UUID id,
            @Valid @RequestBody CreateStudentDTO dto) {
        log.info("remove {} with id: {}", ENTITY,id);
        return ResponseEntity.status(HttpStatus.OK).body(
                this.studentService.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        log.info("fetch all items of entity  {}", ENTITY);
        return ResponseEntity.ok( this.studentService.getAll());
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Student> getById(
            @PathVariable(value = "id") UUID id) {
        log.info("fetch item of entity  {} by id {}", ENTITY,id);
        return ResponseEntity.ok( this.studentService.getById(id));
    }
    @GetMapping(value = "/registrationCode/{registrationCode}")
    public ResponseEntity<Student> getByRegistrationCode(
            @PathVariable(value = "registrationCode") String registrationCode) {
        log.info("fetch item of entity  {} by registration code {}", ENTITY,registrationCode);
        return ResponseEntity.ok( this.studentService.getByRegistrationCode(registrationCode));
    }
}
