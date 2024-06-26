package rw.ac.rca.centrika.services;

import org.springframework.web.multipart.MultipartFile;
import rw.ac.rca.centrika.dtos.requests.CreateDocumentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentDTO;
import rw.ac.rca.centrika.enumerations.EDocStatus;
import rw.ac.rca.centrika.models.Document;

import javax.print.Doc;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface DocumentService {
    public List<Document> getAllDocuments();
    public List<Document> getDocumentByUser(UUID userId);
    public List<Document> getDocumentByDepartment(UUID deptId );
    public Document getDocumentById(UUID doc_id );
    public Document createDocument(MultipartFile docFile , CreateDocumentDTO createDocumentDTO) throws IOException;
    public Document updatedocument(UUID doc_id , UpdateDocumentDTO updateDocumentDTO);
    public Document updateDocFile(UUID docId ,  MultipartFile docFile);
    public Document deleteDocument(UUID doc_id );
    public Document getDocumentByReferenceNumber(UUID referenceNumberId);
    public Document updateReferenceNumber(UUID docId , UUID referenceNumberId);
    public Document updateDocumentStatus(UUID docId , EDocStatus status);
    List<Document> getDocumentsRequestedToMe(UUID userId);
    List<Document> getDocumentRequestedByMe(UUID userId);
}
