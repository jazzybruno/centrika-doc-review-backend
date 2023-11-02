package rw.ac.rca.centrika.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.centrika.dtos.requests.CreateHistoryDTO;
import rw.ac.rca.centrika.dtos.requests.UpdateHistoryDTO;
import rw.ac.rca.centrika.models.History;
import rw.ac.rca.centrika.services.HistoryService;
import rw.ac.rca.centrika.utils.ApResponse;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
@RequestMapping("/api/history")
public class HistoryController {

    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/all")
    public ResponseEntity getAllHistory() {
        List<History> histories = historyService.getAllHistory();
        return ResponseEntity.ok(ApResponse.success(histories));
    }

    @GetMapping("/id/{historyId}")
    public ResponseEntity getHistoryById(@PathVariable UUID historyId) {
        History history = historyService.getHistoryById(historyId);
        return ResponseEntity.ok(ApResponse.success(history));
    }

    @PostMapping("/create")
    public ResponseEntity createHistory(@RequestBody CreateHistoryDTO createHistoryDTO) {
        History history = historyService.createHistory(createHistoryDTO);
        return ResponseEntity.ok(ApResponse.success(history));
    }

    @PutMapping("/update/{historyId}")
    public ResponseEntity updateHistory(@PathVariable UUID historyId, @RequestBody UpdateHistoryDTO updateHistoryDTO) {
        History history = historyService.updateHistory(historyId, updateHistoryDTO);
        return ResponseEntity.ok(ApResponse.success(history));
    }

    @DeleteMapping("/delete/{historyId}")
    public ResponseEntity deleteHistory(@PathVariable UUID historyId) {
        History history = historyService.deleteHistory(historyId);
        return ResponseEntity.ok(ApResponse.success(history));
    }
}
