#!/bin/bash
docker stop $(docker ps -q --filter ancestor=katalog)
mvn clean package
docker build -t katalog .
docker run -p 8081:8081 katalog