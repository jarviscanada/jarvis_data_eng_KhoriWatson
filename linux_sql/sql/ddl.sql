\c host_agent;

CREATE TABLE IF NOT EXISTS host_info (
    id SERIAL NOT NULL PRIMARY KEY,
    hostname VARCHAR (255) NOT NULL UNIQUE,
    num_cpu INTEGER NOT NULL,
    cpu_architecture VARCHAR (255) NOT NULL,
    cpu_model VARCHAR (255) NOT NULL,
    cpu_mhz NUMERIC NOT NULL,
    L2_cache NUMERIC NOT NULL,
    total_mem NUMERIC NOT NULL,
    "timestamp" TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS host_usage (
    "timestamp" TIMESTAMP NOT NULL,
    host_id INTEGER NOT NULL,
    memory_free NUMERIC NOT NULL,
    cpu_idle NUMERIC NOT NULL,
    cpu_kernel NUMERIC NOT NULL,
    disk_io NUMERIC NOT NULL,
    disk_available NUMERIC NOT NULL,
    FOREIGN KEY (host_id)
        REFERENCES host_info (id)
);