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

timestamp=$(vmstat -t | tail -n1 | awk '{print $18 " " $19}'| xargs)
memory_free=$(echo "$vmstat_mb" | tail -n1 | awk '{print $4}' | xargs)
cpu_idle=$(echo "$vmstat_mb"  | tail -n1 | awk '{print $15}' | xargs)
cpu_kernel=$(echo "$vmstat_mb"  | tail -n1 | awk '{print $14}' | xargs)
disk_io=$(vmstat -d |tail -n1 | awk '{print $10}' | xargs)
disk_available=$(df -BM | egrep "*[[:space:]]/$" | awk '{print $4}'| xargs | sed 's/[^0-9]//g')

host_id="(SELECT id FROM host_info WHERE hostname='$hostname')"

#Construct the insert statement
insert_stmt="INSERT INTO host_usage (timestamp, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
 VALUES ('$timestamp', $host_id,'$memory_free', '$cpu_idle', '$cpu_kernel', '$disk_io', '$disk_available');"

export PGPASSWORD=$psql_password
#Insert date into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?
