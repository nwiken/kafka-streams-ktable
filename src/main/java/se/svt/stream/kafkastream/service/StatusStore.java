package se.svt.stream.kafkastream.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import se.svt.stream.kafkastream.exception.NotFoundException;

import java.util.Optional;

@Slf4j
@RestController
public class StatusStore {

  private static final String STATUS_STORE = "status-state-store";

  private final InteractiveQueryService interactiveQueryService;

  public StatusStore(InteractiveQueryService interactiveQueryService) {
    this.interactiveQueryService = interactiveQueryService;
  }

  public String getStatusById(String id) {
    HostInfo hostInfo = interactiveQueryService.getHostInfo(STATUS_STORE, id, Serdes.String().serializer());
    if (interactiveQueryService.getCurrentHostInfo().equals(hostInfo)) {
      return getStatusFromLocalStore(id);
    } else {
      return getStatusFromRemoteStore(id, hostInfo);
    }
  }

  private String getStatusFromLocalStore(String id) {
    try {
      log.info("Reading status from local store id={}", id);
      ReadOnlyKeyValueStore<String, String> keyValueStore =
          interactiveQueryService.getQueryableStore(STATUS_STORE, QueryableStoreTypes.keyValueStore());
      return Optional.ofNullable(keyValueStore.get(id))
          .orElseThrow(() -> new NotFoundException("Status not found for application " + id));
    } catch (InvalidStateStoreException e) {
      log.warn("Invalid state store might retry. Message {}", e.getMessage());
      throw new InvalidStateStoreException(e);
    }
  }

  private String getStatusFromRemoteStore(String applicationId, HostInfo hostInfo){
    log.info("Reading status from remote store id={}", applicationId);
    RestTemplate restTemplate = new RestTemplate();
    String uri = String.format("http://%s:%d/%s", hostInfo.host(), hostInfo.port(),
        "app/query/" + applicationId);
    return restTemplate.getForEntity(uri, String.class).getBody();
  }
}
