package rw.ac.rca.centrika.services;

import rw.ac.rca.centrika.dtos.requests.CreateHistoryDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateHistoryDTO;

import java.util.List;
import java.util.UUID;

public interface HistoryService {
    // crud application
    public List<History> getAllHistory();
    public History getHistoryById(UUID historyId);
    public History createHistory(CreateHistoryDTO createHistoryDTO);
    public History updateHistory(UUID historyId , UpdateHistoryDTO updateHistoryDTO);
    public History deleteHistory(UUID historyId);
     // other methods
}
