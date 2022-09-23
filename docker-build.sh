#!/bin/bash

DOCKER_NAME="aomd-server"

if [ $# -lt 1 ]; then
  echo "docker build -t $DOCKER_NAME/portfolio:latest ."
  docker build -t $DOCKER_NAME/portfolio ./services/portfolio
  exit 1
fi

VERSION_TAG=$1

echo "docker build -t $DOCKER_NAME/portfolio:$VERSION_TAG ."
docker build -t $DOCKER_NAME/portfolio:$VERSION_TAG ./services/portfolio


#if [ "$2" = "push" ]; then
#  echo "$DOCKER_NAME:$VERSION_TAG"
#  docker push 
#fi
