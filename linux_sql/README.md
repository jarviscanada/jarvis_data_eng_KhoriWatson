# Linux Cluster Monitoring Agent 
# Introduction (:TO DO)
This project is a simulation of a real-time hardware monitoring agent. 
It consists of 3 bash scripts and 2 SQL files that collectively provision a Docker container running a 
PostgreSQL image and populate a database with host hardware information and real-time host usage data updated automatically via a crontab job.
The information in the database can then be queried to draw various insights including those surrounding the allocation of computing resources across
the hosts on the network, demonstrate whether the network of servers is being used to its full potential or is in need of an expansion, and even detect if
a host failure. The end user of a product based on this project could be any individual or group tasked with managing a cluster of servers and that therefore needs 
up-to-date information about resource utilization. Version Control for this project was handle through Git using a GitFlow framework to ensure that feature development and
release ready code remain properly segregated and organized.
# Quick Start (:TO DO)
Code block that shows how to run the project
# Implementation (:TO DO)
Discussion of project implementation
## Architecture
![image](./assets/linux_proj_arch.png)
## Scripts (:TO DO)
Shell Script description and usage (code block again)
## DB Modeling (:TO DO)
Describe schema used
# Test (:TO DO)
How was the project tested and what were the results
# Deployment (:TO DO)
How was the app deployed 
# Improvements (:TO DO)
Things to work on 