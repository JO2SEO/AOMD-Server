#!/bin/bash

docker rmi $(docker images -f "dangling=true" -q)
docker volume rm $(docker volume ls -f "dangling=true" -q)