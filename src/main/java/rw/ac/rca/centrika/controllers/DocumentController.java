package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.rca.centrika.dtos.requests.CreateDocumentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentDTO;
import rw.ac.rca.centrika.enumerations.EDocStatus;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.services.DocumentService;
import rw.ac.rca.centrika.utils.ApiResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public ResponseEntity getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all documents",
                documents
        ));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity getDocumentByUser(@PathVariable UUID userId) {
        List<Document> documents = documentService.getDocumentByUser(userId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched documents by user",
                documents
        ));
    }

    @GetMapping("/by-department/{deptId}")
    public ResponseEntity getDocumentByDepartment(@PathVariable UUID deptId) {
        List<Document> documents = documentService.getDocumentByDepartment(deptId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched documents by department",
                documents
        ));
    }

    @GetMapping("/{doc_id}")
    public ResponseEntity getDocumentById(@PathVariable UUID doc_id) {
        Document document = documentService.getDocumentById(doc_id);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched document by id",
                document
        ));
    }

    @PostMapping("/create")
    public ResponseEntity createDocument(@RequestParam("docFile") MultipartFile docFile, @ModelAttribute CreateDocumentDTO createDocumentDTO) {
        try {
            Document document = documentService.createDocument(docFile, createDocumentDTO);
            return ResponseEntity.ok().body(new ApiResponse(
                    true,
                    "Successfully created document",
                    document
            ));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new ApiResponse(
                    false,
                    "Error while processing the request: " + e.getMessage(),
                    null
            ));
        }
    }

    @PutMapping("/{doc_id}")
    public ResponseEntity updateDocument(@PathVariable UUID doc_id, @RequestBody UpdateDocumentDTO updateDocumentDTO) {
        Document document = documentService.updatedocument(doc_id, updateDocumentDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated document",
                document
        ));
    }

    @PutMapping("/update-file/{docId}")
    public ResponseEntity updateDocFile(@PathVariable UUID docId, @RequestParam("docFile") MultipartFile docFile) {
        Document document = documentService.updateDocFile(docId, docFile);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated document file",
                document
        ));
    }

    @DeleteMapping("/{doc_id}")
    public ResponseEntity deleteDocument(@PathVariable UUID doc_id) {
        Document document = documentService.deleteDocument(doc_id);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully deleted document",
                document
        ));
    }

    @GetMapping("/by-reference-number/{referenceNumberId}")
    public ResponseEntity getDocumentByReferenceNumber(@PathVariable UUID referenceNumberId) {
        Document document = documentService.getDocumentByReferenceNumber(referenceNumberId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched document by reference number",
                document
        ));
    }

    @PutMapping("/update-reference-number/{docId}")
    public ResponseEntity updateReferenceNumber(@PathVariable UUID docId, @RequestParam UUID referenceNumberId) {
        Document document = documentService.updateReferenceNumber(docId, referenceNumberId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated document reference number",
                document
        ));
    }

    @PutMapping("/update-status/{docId}")
    public ResponseEntity updateDocumentStatus(@PathVariable UUID docId, @RequestParam EDocStatus status) {
        Document document = documentService.updateDocumentStatus(docId, status);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated document status",
                document
        ));
    }
}
