#!/bin/bash

echo "Building application..."
mvn clean package

echo "Building Docker image..."
docker-compose build

echo "Starting services..."
docker-compose up