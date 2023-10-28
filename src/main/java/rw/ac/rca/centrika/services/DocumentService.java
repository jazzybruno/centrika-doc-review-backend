package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.requests.CreateDocumentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentDTO;
import rw.ac.rca.centrika.models.Document;

import java.util.List;
import java.util.UUID;

public interface DocumentService {
    public List<Document> getAllDocuments();
    public List<Document> getDocumentByUser();
    public List<Document> getDocumentByDepartment(UUID dept_id );
    public Document getDocumentById(UUID doc_id );
    public Document createDocument(CreateDocumentDTO createDocumentDTO);
    public Document updatedocument(UUID doc_id , UpdateDocumentDTO updateDocumentDTO);
    public Document deleteDocument(UUID doc_id );
}
