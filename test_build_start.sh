#!/bin/bash

#NOWDIR=$(pwd)
#
#H2=/Users/hyunwoo/Desktop/Dev/local/Spring/h2/bin
#
#cd $H2
#
#./h2.sh
#
#cd $NOWDIR

./gradlew clean build

APP=./services/portfolio/build/libs/aomd-server.jar

java -jar $APP