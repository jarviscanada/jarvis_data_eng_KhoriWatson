\c host_agent;

-- Query 1: Find the highest total memory across cpu number groups
SELECT num_cpu, id, total_mem
    FROM host_info
    GROUP BY (num_cpu, id, total_mem)
ORDER BY num_cpu, total_mem DESC;

-- Function that sorts each timestmmp into 5 minute intervals
CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
    BEGIN
        RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
    END;
$$
    LANGUAGE PlPGSQL;

-- Function that returns the percentage of used memory
CREATE FUNCTION getusedmem(total numeric, free numeric) RETURNS int AS
$$
    BEGIN
        RETURN ((total - (free * 1024)) / total * 100):: int;
    END;
$$
    LANGUAGE PLPGSQL;

--Query 2: Average percentage of used memory for each host over 5 minute intervals
SELECT host_usage.host_id,
       host_info.hostname,
       round5(host_usage.timestamp) AS time_stamp,
       AVG(
           getusedmem(host_info.total_mem, host_usage.memory_free)
           ) AS avg_used_mem_percentage
FROM host_info
INNER JOIN host_usage
    ON host_info.id = host_usage.host_id
GROUP BY (host_usage.host_id, host_info.hostname, time_stamp)
ORDER BY host_usage.host_id, time_stamp;

-- Query 3: Detect host failure (<3 entries in a 5 min interval)
select
    host_id,
    round5(host_usage.timestamp) as ts,
    count(*) as num_data_points
from host_usage
group by (host_id, ts)
having count(*) < 3;
