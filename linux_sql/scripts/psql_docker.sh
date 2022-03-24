#!/bin/bash

#Extract command line arguments
command=$1
db_username=$2
db_password=$3

#Start docker if not started
sudo systemctl status docker || sudo systemctl start docker

docker container inspect jrvs-psql
container_status=$?

#Case dependent on command line argument
case $command in
  create)
    #Check if container exists
    if [ $container_status -eq 0 ]; then
      echo 'Container already exists'
      exit 1
    fi
    #Check if there are a sufficient number of command line arguments
    if [ $# -ne 3 ]; then
      echo 'Create requires a username and password'
      exit 1
    fi

    #Create container
    docker volume create pgdata
    docker run --name jrvs-psql -e POSTGRES_PASSWORD="$db_password" -e POSTGRES_USER="$db_username" -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
    exit $?
  ;;

  start|stop)
    #Check if
    if [ $container_status -ne 0 ]; then
      echo 'Container does not exist (must create container first)'
      exit 1
    fi

    docker container "$command" jrvs-psql
    exit $?
  ;;

  *)
    echo 'Illegal command'
    echo 'Commands: start|stop|create'
    exit 1
  ;;

esac
