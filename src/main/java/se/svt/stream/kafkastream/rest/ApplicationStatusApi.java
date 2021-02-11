package se.svt.stream.kafkastream.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Api( tags = "Application status")
public interface ApplicationStatusApi {

  @ApiOperation(value = "Get status by application id",
      notes = "Get application status for an application in the local state store",
      nickname = "getStatus")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = String.class),
      @ApiResponse(code = 404, message = "Not found")})
  @GetMapping(value = "/status/{application-id}", produces = MediaType.TEXT_PLAIN_VALUE)
  ResponseEntity<String> getStatus(@PathVariable("application-id") String applicationId);
}
