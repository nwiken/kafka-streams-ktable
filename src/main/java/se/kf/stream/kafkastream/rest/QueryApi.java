package se.kf.stream.kafkastream.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Api( tags = "Query")
public interface QueryApi {

  @ApiOperation(value = "Query status by application id",
      tags = "Query",
      notes = "Query the id of an an application in local state store",
      nickname = "getContext")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = String.class),
      @ApiResponse(code = 404, message = "Not found")})
  @GetMapping(value = "/query/{application-id}", produces = MediaType.TEXT_PLAIN_VALUE)
  ResponseEntity<String> queryStore(@PathVariable("application-id") String applicationId);
}
