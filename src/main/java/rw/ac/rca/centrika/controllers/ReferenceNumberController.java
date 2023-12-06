package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.CreateReferenceNumberDTO;
import rw.ac.rca.centrika.enumerations.ERefNumStatus;
import rw.ac.rca.centrika.models.ReferenceNumber;
import rw.ac.rca.centrika.services.ReferenceNumberService;
import rw.ac.rca.centrika.utils.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping("/api/reference-numbers")
public class ReferenceNumberController {

    private final ReferenceNumberService referenceNumberService;

    @Autowired
    public ReferenceNumberController(ReferenceNumberService referenceNumberService) {
        this.referenceNumberService = referenceNumberService;
    }

    @GetMapping
    public ResponseEntity getAllReferenceNumbers() {
        List<ReferenceNumber> referenceNumbers = referenceNumberService.getAllReferenceNumbers();
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all reference numbers",
                referenceNumbers
        ));
    }

    @GetMapping("/{referenceNumberId}")
    public ResponseEntity getReferenceNumberById(@PathVariable UUID referenceNumberId) {
        ReferenceNumber referenceNumber = referenceNumberService.getReferenceNumberById(referenceNumberId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched reference number by id",
                referenceNumber
        ));
    }

    @PostMapping("/create")
    public ResponseEntity createReferenceNumber(@RequestBody CreateReferenceNumberDTO referenceNumberDTO) {
        ReferenceNumber referenceNumber = referenceNumberService.createReferenceNumber(referenceNumberDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully created reference number",
                referenceNumber
        ));
    }

    @DeleteMapping("/{referenceNumberId}")
    public ResponseEntity deleteReferenceNumber(@PathVariable UUID referenceNumberId) {
        ReferenceNumber referenceNumber = referenceNumberService.deleteReferenceNumber(referenceNumberId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully deleted reference number",
                referenceNumber
        ));
    }

    @PutMapping("/update-status/{referenceNumberId}")
    public ResponseEntity updateReferenceNumberStatus(@PathVariable UUID referenceNumberId, @RequestParam ERefNumStatus status) {
        ReferenceNumber referenceNumber = referenceNumberService.updateReferenceNumberStatus(referenceNumberId, status);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated reference number status",
                referenceNumber
        ));
    }

    @PutMapping("/update-destination/{referenceNumberId}")
    public ResponseEntity updateReferenceNumberDestination(@PathVariable UUID referenceNumberId, @RequestParam String destination) {
        ReferenceNumber referenceNumber = referenceNumberService.updateReferenceNumberDestination(referenceNumberId, destination);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated reference number destination",
                referenceNumber
        ));
    }

    @GetMapping("/by-department/{departmentId}")
    public ResponseEntity getReferenceNumbersByDepartment(@PathVariable UUID departmentId) {
        List<ReferenceNumber> referenceNumbers = referenceNumberService.getReferenceNumbersByDepartment(departmentId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched reference numbers by department",
                referenceNumbers
        ));
    }


    @GetMapping("/by-user/{userId}")
    public ResponseEntity getReferenceNumbersByUser(@PathVariable UUID userId) {
        List<ReferenceNumber> referenceNumbers = referenceNumberService.getReferenceNumbersByUser(userId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched reference numbers by user",
                referenceNumbers
        ));
    }

    @GetMapping("/search-by-destination")
    public ResponseEntity searchReferenceNumberByDestination(@RequestParam String destination) {
        List<ReferenceNumber> referenceNumbers = referenceNumberService.searchReferenceNumberByDestination(destination);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully searched reference numbers by destination",
                referenceNumbers
        ));
    }

    @GetMapping("/search-by-status")
    public ResponseEntity searchReferenceNumberByStatus(@RequestParam ERefNumStatus status) {
        List<ReferenceNumber> referenceNumbers = referenceNumberService.searchReferenceNumberByStatus(status);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully searched reference numbers by status",
                referenceNumbers
        ));
    }
}
