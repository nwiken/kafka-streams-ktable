package se.svt.stream.kafkastream.rest;

import se.svt.stream.kafkastream.service.StatusStore;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationStatusController implements ApplicationStatusApi {

  private final StatusStore statusStateStore;

  public ApplicationStatusController(StatusStore statusStateStore) {
    this.statusStateStore = statusStateStore;
  }

  @Override
  public ResponseEntity<String> getStatus(String applicationId) {
    return ResponseEntity.ok(statusStateStore.getStatusById(applicationId));
  }
}
