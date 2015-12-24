CREATE KEYSPACE slothsandra
    WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

CREATE TABLE channel (
    slack_name text,
    channel_name text,
    PRIMARY KEY ((slack_name), channel_name)
)
WITH CLUSTERING ORDER BY (channel_name ASC);

CREATE TABLE user_by_channel (
    channel_name text,
    username text,
    PRIMARY KEY ((channel_name), username)
)
WITH CLUSTERING ORDER BY (username ASC);

CREATE TABLE message_by_channel (
    channel_name text,
    timeuuid timeuuid,
    createdtime timestamp,
    message text,
    PRIMARY KEY ((channel_name), timeuuid, createdtime)
)
WITH CLUSTERING ORDER BY (timeuuid DESC, createdtime DESC);

CREATE TABLE message_by_user_channel (
    channel_name text,
    username text,
    timeuuid timeuuid,
    createdtime timestamp,
    message text,
    PRIMARY KEY ((channel_name, username), timeuuid, createdtime)
)
WITH CLUSTERING ORDER BY (timeuuid DESC, createdtime DESC);