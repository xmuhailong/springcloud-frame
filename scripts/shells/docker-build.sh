#!/usr/bin/env bash

cd /Users/zhaozhonglong/ideaWorkspace/springcloud-frame/eureka
mvn clean package docker:build -Dmaven.test.skip=true

cd /Users/zhaozhonglong/ideaWorkspace/springcloud-frame/spring-cloud-config
mvn clean package docker:build -Dmaven.test.skip=true

cd /Users/zhaozhonglong/ideaWorkspace/springcloud-frame/apigateway
mvn clean package docker:build -Dmaven.test.skip=true

cd /Users/zhaozhonglong/ideaWorkspace/springcloud-frame/authentication-service
mvn clean package docker:build -Dmaven.test.skip=true