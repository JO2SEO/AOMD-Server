#!/bin/bash

if [ $# -lt 1 ]; then
  echo "docker-build.sh [TAG] [push]"
  exit 1
fi

DOCKER_NAME="aomd-server"

VERSION_TAG=$1

echo "docker build -t $DOCKER_NAME/portfolio:$VERSION_TAG ."
docker build -t $DOCKER_NAME/portfolio:$VERSION_TAG ./services/portfolio


#if [ "$2" = "push" ]; then
#  echo "$DOCKER_NAME:$VERSION_TAG"
#  docker push 
#fi
