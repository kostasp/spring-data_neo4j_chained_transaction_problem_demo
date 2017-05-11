#!/usr/bin/env bash

echo "RUNNING ALL STEPS TO SETUP THE ENVIRONMENT"
./build_and_start_docker.sh
./test.sh

echo "TEAR DOWN ALL DOCKER CONTAINERS"
docker-compose -f ./src/main/docker/app.yml down

