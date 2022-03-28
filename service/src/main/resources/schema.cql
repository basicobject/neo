CREATE KEYSPACE neo WITH replication = {
    'class': 'SimpleStrategy',
    'replication_factor': 1
};

CREATE TABLE IF NOT EXISTS neo.codewords (
    name text,
    context  text,
    path text,
    lang text,
    line_no int,
    filename text,
    extension text,
    score bigint,
    PRIMARY KEY (name, context)
);
