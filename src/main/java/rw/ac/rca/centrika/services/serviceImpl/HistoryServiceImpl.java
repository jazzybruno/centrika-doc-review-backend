package rw.ac.rca.centrika.services.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.rca.centrika.dtos.requests.CreateHistoryDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateHistoryDTO;
import rw.ac.rca.centrika.exceptions.InternalServerErrorException;
import rw.ac.rca.centrika.exceptions.NotFoundException;
import rw.ac.rca.centrika.models.Document;
import rw.ac.rca.centrika.models.DocumentReview;
import rw.ac.rca.centrika.models.User;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final IHistoryRepository historyRepository;
    private final DocumentServiceImpl documentService;
    private final DocumentReviewServiceImpl documentReviewService;
    private final UserServiceImpl userService;

    @Override
    public List<History> getAllHistory() {
        return historyRepository.findAll();
    }

    @Override
    public History getHistoryById(UUID historyId) {
        return historyRepository.findById(historyId).orElseThrow(() -> {throw new NotFoundException("The History was not found");
        });
    }

    @Override
    public History createHistory(CreateHistoryDTO createHistoryDTO) {
        Document document = documentService.getDocumentById(createHistoryDTO.getDocumentId());
        DocumentReview documentReview = documentReviewService.getDocumentReviewById(createHistoryDTO.getDocumentReviewId());
        User requester = userService.getUserById(createHistoryDTO.getRequesterId());
        User approver = userService.getUserById(createHistoryDTO.getApproverId());
        History history = new History(
              document,
              requester,
              approver,
              documentReview
        );
        try {
            historyRepository.save(history);
            return history;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public History updateHistory(UUID historyId, UpdateHistoryDTO updateHistoryDTO) {
        History history = this.getHistoryById(historyId);
        Document document = documentService.getDocumentById(updateHistoryDTO.getDocumentId());
        DocumentReview documentReview = documentReviewService.getDocumentReviewById(updateHistoryDTO.getDocumentReviewId());
        User requester = userService.getUserById(updateHistoryDTO.getRequesterId());
        User approver = userService.getUserById(updateHistoryDTO.getApproverId());
        try {
            history.setApprover(approver);
            history.setDocument(document);
            history.setRequester(requester);
            history.setDocumentReview(documentReview);
            return history;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public History deleteHistory(UUID historyId) {
        History history = historyRepository.findById(historyId).orElseThrow(() -> {throw new NotFoundException("The History was not found");
        });
        try {
            historyRepository.deleteById(historyId);
            return history;
        }catch (Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }
}
