CREATE KEYSPACE slothsandra
    WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

CREATE TABLE channel (
    slack_name text,
    channel_name text,
    channel_id text,
    count_message counter,
    PRIMARY KEY ((slack_name), channel_name, channel_id)
)
WITH CLUSTERING ORDER BY (channel_name ASC, channel_id ASC);

CREATE TABLE user (
    slack_name text,
    username text,
    user_id text,
    PRIMARY KEY ((slack_name), username, user_id)
)
WITH CLUSTERING ORDER BY (username ASC, user_id ASC);

CREATE TABLE user_by_channel (
    channel_name text,
    username text,
    count_message counter,
    PRIMARY KEY ((channel_name), username)
)
WITH CLUSTERING ORDER BY (username ASC);

CREATE TABLE message_by_channel (
    channel_name text,
    year int,
    uuid_time timeuuid,
    created_time timestamp,
    username text,
    message text,
    PRIMARY KEY ((channel_name, year), uuid_time, created_time)
)
WITH CLUSTERING ORDER BY (uuid_time DESC, created_time DESC);

CREATE TABLE message_by_user_channel (
    channel_name text,
    username text,
    year int,
    uuid_time timeuuid,
    created_time timestamp,
    message text,
    PRIMARY KEY ((channel_name, username, year), uuid_time, created_time)
)
WITH CLUSTERING ORDER BY (uuid_time DESC, created_time DESC);

CREATE TABLE year_by_channel (
    channel_name text,
    year int,
    PRIMARY KEY ((channel_name), year)
)
WITH CLUSTERING ORDER BY (year DESC);