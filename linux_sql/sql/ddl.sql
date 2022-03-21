\c host_agent;

CREATE TABLE IF NOT EXISTS host_info (
    id SERIAL NOT NULL PRIMARY KEY,
    host_id VARCHAR (255) NOT NULL UNIQUE,
    num_cpu INTEGER NOT NULL,
    cpu_architecture VARCHAR (255) NOT NULL,
    cpu_model VARCHAR (255) NOT NULL,
    cpu_mhz REAL NOT NULL,
    L2_cache INTEGER NOT NULL,
    total_mem INTEGER NOT NULL,
    "timestamp" TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS host_usage (
    "timestamp" TIMESTAMP NOT NULL,
    host_id VARCHAR (255) NOT NULL UNIQUE,
    memory_free INTEGER NOT NULL,
    cpu_idle INTEGER NOT NULL,
    cpu_kernel INTEGER NOT NULL,
    disk_io INTEGER NOT NULL,
    disk_available INTEGER NOT NULL,
    FOREIGN KEY (host_id)
        REFERENCES host_info (host_id)
);