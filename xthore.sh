#!/bin/bash

# Exit on any error
set -e

echo "🚀 Xthore Automation Tool"

function test() {
    echo "🧪 Running unit and integration tests..."
    mvn test
    echo "✅ Tests complete!"
}

function build() {
    echo "🧹🧹 Cleaning, Installing and Building Docker images..."
    # We use install instead of test to ensure reactor dependencies are JARed correctly for Jib
    mvn clean install jib:dockerBuild -DskipTests
    echo "✅ Build complete!"
}

function deploy() {
    echo "🚢 Starting services with Docker Compose..."
    # The docker-compose.yml is in the docker/ directory
    # We specify the env-file located in the root
    docker compose -f docker/docker-compose.yml --env-file .env up -d
    echo "✅ All services are up and running!"
}

case "$1" in
    --test)
        test
        ;;
    --build)
        build
        ;;
    --deploy)
        deploy
        ;;
    "")
        echo "🔄 Running full Test, Build & Deploy sequence..."
        test
        build
        deploy
        ;;
    *)
        echo "Usage: $0 {--test|--build|--deploy|no arguments for all}"
        echo "  --test    : Run unit and integration tests"
        echo "  --build   : Clean, install, and build Docker images using Jib"
        echo "  --deploy  : Start services with Docker Compose"
        echo "  (default) : Run test, build, and deploy"
        exit 1
        ;;
esac
