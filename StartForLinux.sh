#!/bin/sh
clear
mvn clean install
cd target
clear
java -jar team-37-1.0-SNAPSHOT.jar