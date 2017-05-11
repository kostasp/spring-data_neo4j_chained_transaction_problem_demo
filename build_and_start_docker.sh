#!/usr/bin/env bash
echo " start building with maven"
./mvnw clean install -DskipTests=true
./mvnw docker:build
sleep 2s
echo " Starting docker compose services"
./start_all_with_docker_compose.sh
sleep 3s

docker logs test_transaction_app
