package se.kf.stream.kafkastream.serde;

import se.kf.stream.kafkastream.domain.ApplicationStatusEvent;

import org.springframework.kafka.support.serializer.JsonSerde;

public class StatusEventSerde extends JsonSerde<ApplicationStatusEvent> {


  public static StatusEventSerde newInstance() {
    return new StatusEventSerde();
  }
}
