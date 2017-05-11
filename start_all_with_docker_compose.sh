#!/usr/bin/env bash

docker-compose -f ./src/main/docker/app.yml up -d
echo " WAITING 60 seconds UNTIL Docker containers are downloaded and started"
sleep 60s
if [ $(uname) == "Darwin" ];  then open http://localhost:8074
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then xdg-open http://localhost:8074
elif [ "$(expr substr $(uname -s) 1 10)" == "MINGW32_NT" ]; then echo "NOT SUPPORTED IN WINDOWS COMPATIBLE CMDS"
fi
echo "STARTED"
