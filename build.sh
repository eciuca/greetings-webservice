#!/bin/bash

docker run --name greetings-ws-build-container \
 -it --rm \
 -v "$(pwd)":/usr/src/mymaven \
 -w /usr/src/mymaven \
 maven:3.6-jdk-13 \
 mvn clean package
