CREATE KEYSPACE slothsandra
    WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

CREATE TABLE channel (
    slack_name text,
    channel_id text,
    channel_name text,
    PRIMARY KEY ((slack_name), channel_id, channel_name)
)
WITH CLUSTERING ORDER BY (channel_name ASC);

CREATE TABLE user (
    slack_name text,
    user_id text,
    username text,
    PRIMARY KEY ((slack_name), user_id, username)
)
WITH CLUSTERING ORDER BY (username ASC);

CREATE TABLE user_by_channel (
    channel_name text,
    username text,
    PRIMARY KEY ((channel_name), username)
)
WITH CLUSTERING ORDER BY (username ASC);

CREATE TABLE message_by_channel (
    channel_name text,
    uuid_time timeuuid,
    created_time timestamp,
    username text,
    message text,
    PRIMARY KEY ((channel_name), uuid_time, created_time)
)
WITH CLUSTERING ORDER BY (uuid_time DESC, created_time DESC);

CREATE TABLE message_by_user_channel (
    channel_name text,
    username text,
    uuid_time timeuuid,
    created_time timestamp,
    message text,
    PRIMARY KEY ((channel_name, username), uuid_time, created_time)
)
WITH CLUSTERING ORDER BY (uuid_time DESC, created_time DESC);