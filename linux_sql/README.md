# Linux Cluster Monitoring Agent 
# Introduction (:TO DO)
This project is a simulation of a real-time hardware monitoring agent. 
It consists of 3 bash scripts and 2 SQL files that collectively provision a Docker container running a PostgreSQL image and populate a database with host hardware information and real-time host usage data updated automatically via a crontab job. The information in the database can then be queried to draw various insights including those surrounding the allocation of computing resources across the hosts on the network, demonstrate whether the network of servers is being used to its full potential or is in need of an expansion, and even detect if a host failure. The end user of a product based on this project could be any individual or group tasked with managing a cluster of servers and that therefore needs up-to-date information about resource utilization. Version Control for this project was handle through Git using a GitFlow framework to ensure that feature development and release ready code remain properly segregated and organized.
# Quick Start (:TO DO)
Code block that shows how to run the project
```
./scripts/psql_docker create [username] [password]

export PGPASSWORD='[password]'

psql -h [hostname] -U [username] -c "CREATE DATABASE host_agent;"

psql -h [hostname] -U [username] -f ./sql/ddl.sql

./scripts/host_info.sh [hostname] [port] [database_name] [username] [password]

crontab -e

#Add the crontab job to execute every minute by adding this line
#* * * * * bash [absolute_path_to_script]/host_usage.sh [hostname] [port] [database_name] [username] [password] > /tmp/host_usage.log
```
# Implementation (:TO DO)
Implementation for this project began with the development of the `psql_docker.sh` script used to start or stop the Docker container or provision one if it's currently nonexistent. The script centers on a case statment that governs the interpretation the command line arguments based on whether the user intendes to start/stop or create a new container. Error checking is done through to ensure the correct number of arguments are based to the script call and prevent the script for creating container that already exists or from starting/stopping one that doesn't. Next, in the `ddl.sql` file, the schemas for the tables that store the host information and the host usage data were created based on what information we wanted to keep track of through this project. Afterwards the two main scripts that function as the backbone of this project `host_info.sh` and `host_usage.sh` were created. Both scripts parse the output of various bash commands that gather hardware information like `lscpu` and `vmstat` using `grep` and `awk` to extract all relevant values. They then construct an SQL statement to insert that data in the relevant schema and then connect to the correct database using the command line arguments provided. Next a crontab job was created to automate the collection of real time host usage data every minute.
## Architecture
![image](./assets/linux_proj_arch.png)
## Scripts (:TO DO)
Shell Script description and usage (code block again)
`./psql_docker`

This script has three modes: `start`, `stop`, and `create`. `start` and `stop` both require 0 additional command line arguments beyond specifying the mode, whereas `create` takes a username and a password used to set up the PostgreSQL instance within the container. The script exits with status code 0 upon successfully starting, stoping, or creating the Docker container. The script exits with exit code 1 when attempting to start or stop a container that doesn't exist or to create a container that already does. After running this command you can use `docker ps -a` to check if the container is running.

`./host_info.sh [hostname] [port] [dbname] [username] [password]`

`./host_usage.sh [hostname] [port] [dbname] [username] [password]`

These two scripts serve similar functions and are structured in similar ways. They both require 5 command lines arguments which enable them to connect to a PostgreSQL instance from within the script itself. Besides connecting to the database, they also gather data about the machines that they run on using the `lscpu`, `vmstat`, and `df` commands as well as the `meminfo` file. This information is then extracted and parsed using `grep` and `awk` and stored in individual variables that are subsequently combined into an SQL insert statement that is finally executed after connecting to the database at the end of each script.

`crontab` file

This file contains one job that runs the `host_usage.sh` script every minute. Each time that script runs, it inserts a new row into the `host_usage` table in the data base allowing for a running real-time log of hardware resource usage for each host.

`queries.sql`

This SQL file contains 3 queries representing potential analyses of memory usage across hosts. The second query in particular generates a result set containing percentage of used memory for all hosts averaged over 5 minute intervals. The third query also counts the number of entries within a 5 minute interval to determine if a host has gone down.

## DB Modeling (:TO DO)
Describe schema used
# Test (:TO DO)
How was the project tested and what were the results
# Deployment (:TO DO)
How was the app deployed 
# Improvements (:TO DO)
Things to work on 
