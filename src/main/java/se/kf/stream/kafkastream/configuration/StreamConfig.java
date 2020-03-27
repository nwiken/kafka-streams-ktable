package se.kf.stream.kafkastream.configuration;

import se.kf.stream.kafkastream.domain.ApplicationStatusEvent;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@EnableBinding
@Configuration
public class StreamConfig {

  private static final String STATUS_STORE = "status-state-store";

  @Bean
  public java.util.function.Consumer<KStream<Object, ApplicationStatusEvent>> statusEvent() {
    return stream -> stream
        .selectKey((key, val) -> val.getId())
        .peek((key, val) -> log.info("Updating status for id {} to {}", val.getId(), val.getStatus()))
        .mapValues(ApplicationStatusEvent::getStatus)
        .groupBy((key, val) -> key)
        .reduce((a1, a2) -> a2, Materialized.as(STATUS_STORE));
  }
}
