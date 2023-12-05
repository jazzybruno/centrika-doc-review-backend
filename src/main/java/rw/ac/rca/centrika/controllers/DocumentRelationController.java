package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.CreateDocumentRelationDTO;
import rw.ac.rca.centrika.enumerations.ERelationType;
import rw.ac.rca.centrika.models.DocumentRelation;
import rw.ac.rca.centrika.services.DocumentRelationService;
import rw.ac.rca.centrika.utils.ApiResponse;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping("/api/document-relations")
public class DocumentRelationController {

    private final DocumentRelationService documentRelationService;

    @Autowired
    public DocumentRelationController(DocumentRelationService documentRelationService) {
        this.documentRelationService = documentRelationService;
    }

    @GetMapping
    public ResponseEntity getAllDocumentRelations() {
        List<DocumentRelation> documentRelations = documentRelationService.getAllDocumentRelations();
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched all document relations",
                documentRelations
        ));
    }

    @GetMapping("/{documentRelationId}")
    public ResponseEntity getDocumentRelationById(@PathVariable UUID documentRelationId) {
        DocumentRelation documentRelation = documentRelationService.getDocumentRelationById(documentRelationId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched document relation by id",
                documentRelation
        ));
    }

    @PostMapping("/create")
    public ResponseEntity createDocumentRelation(@RequestBody CreateDocumentRelationDTO documentRelationDTO) {
        DocumentRelation documentRelation = documentRelationService.createDocumentRelation(documentRelationDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully created document relation",
                documentRelation
        ));
    }

    @PutMapping("/{documentRelationId}")
    public ResponseEntity updateDocumentRelation(@PathVariable UUID documentRelationId, @RequestBody CreateDocumentRelationDTO documentRelationDTO) {
        DocumentRelation documentRelation = documentRelationService.updateDocumentRelation(documentRelationId, documentRelationDTO);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully updated document relation",
                documentRelation
        ));
    }

    @DeleteMapping("/{documentRelationId}")
    public ResponseEntity deleteDocumentRelation(@PathVariable UUID documentRelationId) {
        DocumentRelation documentRelation = documentRelationService.deleteDocumentRelation(documentRelationId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully deleted document relation",
                documentRelation
        ));
    }

    @GetMapping("/by-relation-type/{relationType}")
    public ResponseEntity getAllDocRelationsByRelationType(@PathVariable ERelationType relationType) {
        List<DocumentRelation> documentRelations = documentRelationService.getAllDocRelationsByRelationType(relationType);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched document relations by relation type",
                documentRelations
        ));
    }

    @GetMapping("/by-parent-doc/{documentId}")
    public ResponseEntity getAllDocRelationByParentDocument(@PathVariable UUID documentId) {
        List<DocumentRelation> documentRelations = documentRelationService.getAllDocRelationByParentDoc(documentId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched document relations by parent document",
                documentRelations
        ));
    }

    @GetMapping("/by-child-doc/{documentId}")
    public ResponseEntity getAllDocRelationByChildDocument(@PathVariable UUID documentId) {
        List<DocumentRelation> documentRelations = documentRelationService.getAllDocRelationByChildDocument(documentId);
        return ResponseEntity.ok().body(new ApiResponse(
                true,
                "Successfully fetched document relations by child document",
                documentRelations
        ));
    }

}
