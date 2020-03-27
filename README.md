## Kafka streams example
This small project creates a KTable that persist the latest status submitted to a kafka topic. The Project implement support for running multiple instances of the application. By using the InteractiveQuery service.

### Run
Instructions for running the example

* Create topic *application-status* in kafka
* Start application
* Publish messages one form to the topic *application-status*
```
{"id":"1","status":"DONE"}
```
* Query result
```
curl -X GET http://localhost:8081/app/query/1
```
