package rw.ac.rca.centrika.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import rw.ac.rca.centrika.dtos.requests.CreateDocumentDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentDTO;
import rw.ac.rca.centrika.enumerations.ECategory;
import rw.ac.rca.centrika.enumerations.EDocStatus;
import rw.ac.rca.centrika.enumerations.EStatus;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.exceptions.UnAuthorizedException;
import rw.ac.rca.centrika.models.Department;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.models.DocumentReview;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.IDepartmentRepository;
import rw.ac.rca.centrika.repositories.IDocumentRepository;
import rw.ac.rca.centrika.repositories.IDocumentReviewRepository;
import rw.ac.rca.centrika.security.UserPrincipal;
import rw.ac.rca.centrika.services.DocumentReviewService;
import rw.ac.rca.centrika.services.DocumentService;
import rw.ac.rca.centrika.utils.UserUtils;
import rw.ac.rca.centrika.utils.Utility;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final IDocumentRepository documentRepository;
    private final UserServiceImpl userService;
    private final IDepartmentRepository departmentRepository;
    private final  FileServiceImpl  fileService;
    private final IDocumentReviewRepository documentReviewRepository;

    @Autowired
    public DocumentServiceImpl(IDocumentRepository documentRepository, UserServiceImpl userService, IDepartmentRepository departmentRepository, FileServiceImpl fileService, IDocumentReviewRepository documentReviewRepository) {
        this.documentRepository = documentRepository;
        this.userService = userService;
        this.departmentRepository = departmentRepository;
        this.fileService = fileService;
        this.documentReviewRepository = documentReviewRepository;
    }

    @Override
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @Override
    public List<Document> getDocumentByUser(UUID userId) {
        User user = userService.getUserById(userId);
         return documentRepository.findAllByCreatedBy(user);
    }

    @Override
    public List<Document> getDocumentByDepartment(UUID deptId) {
        Department department = departmentRepository.findById(deptId).orElseThrow(() -> {throw new NotFoundException("The Department was not found");
        });
        return documentRepository.findAllByDepartment(department);
    }

    @Override
    public Document getDocumentById(UUID doc_id) {
        return documentRepository.findById(doc_id).orElseThrow(()-> {throw new NotFoundException("The document was not found");});
    }

    @Override
    public Document createDocument(MultipartFile docFile ,  CreateDocumentDTO createDocumentDTO) throws IOException {
        Department department = departmentRepository.findById(createDocumentDTO.getDepartmentId()).orElseThrow(() -> {throw new NotFoundException("The Department was not found");
        });
        DocumentReview documentReview = documentReviewRepository.findById(createDocumentDTO.getDocReviewId()).orElseThrow(() -> {throw new NotFoundException("The Document Review was not found");
        });
//        if(UserUtils.isUserLoggedIn()){
          try {
//              UserPrincipal userPrincipal = UserUtils.getLoggedInUser();
//              assert userPrincipal != null;
//              User user = userService.getUserById(userPrincipal.getId());
              User user = userService.getUserById(createDocumentDTO.getCreator());
              String fileName = fileService.uploadFile(docFile);
              int referenceNumber = Utility.randomNum();
              EDocStatus status = EDocStatus.PENDING;
              ECategory category = createDocumentDTO.getCategory();
              Document document = new Document(
                      createDocumentDTO.getTitle(),
                      createDocumentDTO.getDescription(),
                      fileName,
                      category,
                      status,
                      referenceNumber,
                      user,
                      department,
                      documentReview
              );
              document.setCreatedAt(new Date());
              documentRepository.save(document);
              return document;
          }catch (Exception e){
              throw new InternalServerErrorException(e.getMessage());
          }
//        }else{
//            throw new UnAuthorizedException("You are not authorized to perform this activity");
//        }
    }

    @Override
    @Transactional
    public Document updatedocument(UUID doc_id, UpdateDocumentDTO updateDocumentDTO) {
        Document document = documentRepository.findById(doc_id).orElseThrow(()-> {throw new NotFoundException("The document was not found");});
        Department department = departmentRepository.findById(updateDocumentDTO.getDepartmentId()).orElseThrow(() -> {throw new NotFoundException("The Department was not found");
        });
        try {
            document.setDepartment(department);
            document.setDescription(updateDocumentDTO.getDescription());
            return document;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Document updateDocFile(UUID docId, MultipartFile docFile)  {
        if (UserUtils.isUserLoggedIn()) {
            Document document = documentRepository.findById(docId).orElseThrow(()-> {throw new NotFoundException("The document was not found");});
            String normalName = document.getFileUrl();
            try {
                UserPrincipal userPrincipal = UserUtils.getLoggedInUser();
                assert userPrincipal != null;
                User user = userService.getUserById(userPrincipal.getId());
                String fileName = fileService.updateFile(normalName , docFile);
                document.setFileUrl(fileName);
                return document;
            } catch (Exception e) {
                throw new InternalServerErrorException(e.getMessage());
            }
        }else{
            throw new UnAuthorizedException("You are not authorized to perform this activity");
        }
    }

    @Override
    public Document deleteDocument(UUID doc_id) {
        Document document = documentRepository.findById(doc_id).orElseThrow(()-> {throw new NotFoundException("The document was not found");});
        try {
            documentRepository.deleteById(doc_id);
            return document;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Document approveDocument(UUID doc_id) {
        Document document = documentRepository.findById(doc_id).orElseThrow(()-> {throw new NotFoundException("The document was not found");});
        try {
            EDocStatus eDocStatus = EDocStatus.APPROVED;
            document.setStatus(eDocStatus);
            return document;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public int getReferenceNumber() {
        List<Document> documents = documentRepository.findAllByCategory(ECategory.EXTERNAL);
        return documents.size() + 1;
    }
}
