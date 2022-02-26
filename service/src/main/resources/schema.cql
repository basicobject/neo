CREATE KEYSPACE neo WITH replication = {
    'class': 'SimpleStrategy',
    'replication_factor': 1
};

CREATE TABLE IF NOT EXISTS neo.names (
    name text,
    context  text,
    score bigint,
    PRIMARY KEY (name, context)
);
