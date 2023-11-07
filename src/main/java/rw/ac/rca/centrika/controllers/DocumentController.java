package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.rca.centrika.dtos.requests.CreateDocumentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentDTO;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.services.DocumentService;
import rw.ac.rca.centrika.services.FileService;
import rw.ac.rca.centrika.services.serviceImpl.FileServiceImpl;
import rw.ac.rca.centrika.utils.ApResponse;
import rw.ac.rca.centrika.utils.ApiResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping("/api/document")
public class DocumentController {
    private final DocumentService documentService;
    private final FileServiceImpl fileService;
    @Autowired
    public DocumentController(DocumentService documentService , FileServiceImpl fileService) {
        this.documentService = documentService;
        this.fileService = fileService;
    }

    @GetMapping("/download/{docName}")
    public ResponseEntity downloadDocument(@PathVariable String docName) throws IOException {
        File  file = fileService.getFile(docName);
        // create a resource
        Resource resource = new UrlResource(file.toURI());
        // check if the resource is available and readable
        if(resource.exists() && resource.isReadable()){
            return  ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""  + resource.getFilename() + "\"")
                    .body(resource);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok().body(new ApiResponse(
                 true,
                "The documents where fetched successfully",
                documents
        ));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getDocumentByUser(@PathVariable UUID userId) {
        List<Document> documents = documentService.getDocumentByUser(userId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "The documents where fetched successfully by user",
                documents
        ));
    }

    @GetMapping("/department/{deptId}")
    public ResponseEntity getDocumentByDepartment(@PathVariable UUID deptId) {
        List<Document> documents = documentService.getDocumentByDepartment(deptId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "The documents where fetched successfully by department",
                documents
        ));
    }

    @GetMapping("/{docId}")
    public ResponseEntity getDocumentById(@PathVariable UUID docId) {
        Document document = documentService.getDocumentById(docId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "The document where fetched successfully",
                document
        ));
    }

    @PostMapping("/create")
    public ResponseEntity createDocument(@RequestParam("file") MultipartFile docFile, @RequestParam("data") CreateDocumentDTO createDocumentDTO) throws IOException {
        Document document = documentService.createDocument(docFile, createDocumentDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "The document was created successfully",
                document
        ));
    }

    @PutMapping("/{docId}")
    public ResponseEntity updateDocument(@PathVariable UUID docId, @RequestBody UpdateDocumentDTO updateDocumentDTO) {
        Document document = documentService.updatedocument(docId, updateDocumentDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "The document was updated successfully",
                document
        ));
    }

    @PutMapping("/{docId}/update-file")
    public ResponseEntity updateDocFile(@PathVariable UUID docId, @RequestParam("file") MultipartFile docFile) {
        Document document = documentService.updateDocFile(docId, docFile);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "The document was updated successfully",
                document
        ));
    }

    @DeleteMapping("/{docId}")
    public ResponseEntity deleteDocument(@PathVariable UUID docId) {
        Document document = documentService.deleteDocument(docId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "The document was deleted successfully",
                document
        ));
    }

    @PutMapping("/{docId}/approve")
    public ResponseEntity approveDocument(@PathVariable UUID docId) {
        Document document = documentService.approveDocument(docId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "The documents where fetched successfully",
                document
        ));
    }
}

