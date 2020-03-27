package se.kf.stream.kafkastream.rest;

import se.kf.stream.kafkastream.service.StatusStore;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController implements QueryApi {

  private final StatusStore statusStateStore;

  public QueryController(StatusStore statusStateStore) {
    this.statusStateStore = statusStateStore;
  }

  @Override
  public ResponseEntity<String> queryStore(String applicationId) {
    return ResponseEntity.ok(statusStateStore.getStatusById(applicationId));
  }
}
