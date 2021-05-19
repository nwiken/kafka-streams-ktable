package se.svt.stream.kafkastream.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.svt.stream.kafkastream.domain.ApplicationStatusEvent;

import java.util.function.Consumer;

@Slf4j
@EnableBinding
@Configuration
public class StreamConfig {

  private static final String STATUS_STORE = "status-state-store";

  @Bean
  public Consumer<KStream<String, ApplicationStatusEvent>> statusEvent() {
    return stream -> stream
        .selectKey((key, val) -> val.getId())
        .peek((key, val) -> log.info("Updating status for id {} to {}", val.getId(), val.getStatus()))
        .mapValues(ApplicationStatusEvent::getStatus)
        .groupByKey()
        .reduce((aggValue, newValue) -> newValue, Materialized.as(STATUS_STORE));
  }
}
