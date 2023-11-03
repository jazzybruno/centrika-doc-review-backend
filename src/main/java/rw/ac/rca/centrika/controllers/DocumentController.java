package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.rca.centrika.dtos.requests.CreateDocumentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentDTO;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.services.DocumentService;
import rw.ac.rca.centrika.utils.ApResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping("/api/document")
public class DocumentController {
    private final DocumentService documentService;
    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    public ResponseEntity getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(ApResponse.success(documents));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getDocumentByUser(@PathVariable UUID userId) {
        List<Document> documents = documentService.getDocumentByUser(userId);
        return ResponseEntity.ok(ApResponse.success(documents));
    }

    @GetMapping("/department/{deptId}")
    public ResponseEntity getDocumentByDepartment(@PathVariable UUID deptId) {
        List<Document> documents = documentService.getDocumentByDepartment(deptId);
        return ResponseEntity.ok(ApResponse.success(documents));
    }

    @GetMapping("/{docId}")
    public ResponseEntity getDocumentById(@PathVariable UUID docId) {
        Document document = documentService.getDocumentById(docId);
        return ResponseEntity.ok(ApResponse.success(document));
    }

    @PostMapping("/create")
    public ResponseEntity createDocument(@RequestParam("file") MultipartFile docFile, @RequestParam("data") CreateDocumentDTO createDocumentDTO) throws IOException {
        Document document = documentService.createDocument(docFile, createDocumentDTO);
        return ResponseEntity.ok(ApResponse.success(document));
    }

    @PutMapping("/{docId}")
    public ResponseEntity updateDocument(@PathVariable UUID docId, @RequestBody UpdateDocumentDTO updateDocumentDTO) {
        Document document = documentService.updatedocument(docId, updateDocumentDTO);
        return ResponseEntity.ok(ApResponse.success(document));
    }

    @PutMapping("/{docId}/update-file")
    public ResponseEntity updateDocFile(@PathVariable UUID docId, @RequestParam("file") MultipartFile docFile) {
        Document document = documentService.updateDocFile(docId, docFile);
        return ResponseEntity.ok(ApResponse.success(document));
    }

    @DeleteMapping("/{docId}")
    public ResponseEntity deleteDocument(@PathVariable UUID docId) {
        Document document = documentService.deleteDocument(docId);
        return ResponseEntity.ok(ApResponse.success(document));
    }

    @PutMapping("/{docId}/approve")
    public ResponseEntity approveDocument(@PathVariable UUID docId) {
        Document document = documentService.approveDocument(docId);
        return ResponseEntity.ok(ApResponse.success(document));
    }
}

