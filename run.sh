#!/bin/bash

docker run --name greetings-ws-build-container \
 --rm \
 -p 8080:8080 \
 -v "$(pwd)/target":/usr/src/myapp \
 -w /usr/src/myapp \
 openjdk:13 \
 java -jar greetings-webservice-0.0.1-SNAPSHOT.jar
