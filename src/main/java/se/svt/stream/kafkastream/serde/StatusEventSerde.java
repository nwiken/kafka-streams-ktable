package se.svt.stream.kafkastream.serde;

import se.svt.stream.kafkastream.domain.ApplicationStatusEvent;

import org.springframework.kafka.support.serializer.JsonSerde;

public class StatusEventSerde extends JsonSerde<ApplicationStatusEvent> {


  public static StatusEventSerde newInstance() {
    return new StatusEventSerde();
  }
}
