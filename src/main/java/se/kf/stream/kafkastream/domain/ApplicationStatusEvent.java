package se.kf.stream.kafkastream.domain;

import lombok.Data;

@Data
public class ApplicationStatusEvent {
  String id;
  String status;
}
