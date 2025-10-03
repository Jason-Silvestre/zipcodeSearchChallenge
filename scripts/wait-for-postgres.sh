#!/bin/bash
# wait-for-postgres.sh

set -e

host="$1"
port="$2"
shift 2
cmd="$@"

echo "Aguardando PostgreSQL em $host:$port..."

while ! nc -z $host $port; do
  sleep 1
done

echo "PostgreSQL está pronto! Iniciando aplicação..."
exec $cmd