#!/bin/bash
#Extract and verify command line arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5


if [ $# -ne 5 ]; then
  echo "Incorrect number of arguments: script requires 5 arguments."
  exit 1
fi

#Extract the hardware information
specs=$(lscpu)
vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)

cpu_number=$(echo "$specs" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$specs" | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$specs" | grep "Model name:" | awk -F":" '{print $2}' | xargs)
cpu_mhz=$(echo "$specs" | grep "CPU MHz:" | awk -F":" '{print $2}' | xargs)
l2_cache=$(echo "$specs" | grep "L2 cache" | awk -F":" '{print $2}' | xargs |  sed 's/[^0-9]//g')
total_mem=$(cat /proc/meminfo | grep "MemTotal" | awk -F":" '{print $2}' | xargs | sed 's/[^0-9]//g')
timestamp=$(vmstat -t | tail -n1 | awk '{print $18 " " $19}'| xargs)

#Construct the insert statement
insert_stmt="INSERT INTO host_info (hostname, num_cpu, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, timestamp)
 VALUES ('$hostname', '$cpu_number','$cpu_architecture', '$cpu_model', '$cpu_mhz', '$l2_cache', '$total_mem', '$timestamp');"

#Set env variables and insert data into database
export PGPASSWORD=$psql_password

psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?
