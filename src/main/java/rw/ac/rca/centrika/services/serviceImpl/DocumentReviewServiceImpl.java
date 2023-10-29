package rw.ac.rca.centrika.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.centrika.dtos.requests.CreateDocumentReviewDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateDocumentReviewDTO;
import rw.ac.rca.centrika.enumerations.EDocStatus;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.models.DocumentReview;
import rw.ac.rca.centrika.models.User;
import rw.ac.rca.centrika.repositories.IDocumentReviewRepository;
import rw.ac.rca.centrika.services.DocumentReviewService;
import rw.ac.rca.centrika.services.DocumentService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DocumentReviewServiceImpl implements DocumentReviewService {
    private IDocumentReviewRepository documentReviewRepository;
    private DocumentServiceImpl documentService;
    private UserServiceImpl userService;

    @Override
    public List<DocumentReview> getAllDocumentReviews() {
        return documentReviewRepository.findAll();
    }

    @Override
    public DocumentReview getDocumentReviewById(UUID docReviewId) {
        return documentReviewRepository.findById(docReviewId).orElseThrow(() -> {throw new NotFoundException("The document Review was not found");
        });
    }

    @Override
    public DocumentReview requestDocumentReview(CreateDocumentReviewDTO createDocumentReviewDTO) {
        Document document = documentService.getDocumentById(createDocumentReviewDTO.getDocId());
        User user = userService.getUserById(createDocumentReviewDTO.getReviewer());
        try {
            EDocStatus status=EDocStatus.PENDING;
            Date createdAt = new Date();
           Set<User> reviewers = new HashSet<User>();
           reviewers.add(user);
           DocumentReview documentReview = new DocumentReview(
                   createdAt,
                   status,
                   reviewers,
                   document
           );
           documentReviewRepository.save(documentReview);
           return documentReview;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public DocumentReview updateDocumentReview(UUID docReviewId, UpdateDocumentReviewDTO updateDocumentReviewDTO) {
        DocumentReview documentReview = documentReviewRepository.findById(docReviewId).orElseThrow(() -> {throw new NotFoundException("The document Review was not found");
        });
        User user = userService.getUserById(updateDocumentReviewDTO.getReviewer());
        try {
            Set<User> users = documentReview.getReviewers();
            users.add(user);
            documentReview.setReviewers(users);
            return documentReview;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public DocumentReview deleteDocumentReview(UUID docReviewId) {
        DocumentReview documentReview = this.getDocumentReviewById(docReviewId);
        try {
            documentReviewRepository.deleteById(docReviewId);
            return documentReview;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
