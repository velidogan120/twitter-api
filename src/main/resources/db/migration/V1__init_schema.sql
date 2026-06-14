CREATE SCHEMA IF NOT EXISTS twitter;

CREATE TABLE twitter.users (
                               id BIGSERIAL PRIMARY KEY,
                               first_name VARCHAR(255) NOT NULL,
                               last_name VARCHAR(255) NOT NULL,
                               email VARCHAR(255) NOT NULL UNIQUE,
                               password VARCHAR(255) NOT NULL
);

CREATE TABLE twitter.roles (
                               id BIGSERIAL PRIMARY KEY,
                               authority VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE twitter.users_roles (
                                     id BIGSERIAL PRIMARY KEY,
                                     user_id BIGINT NOT NULL,
                                     role_id BIGINT NOT NULL
);

CREATE TABLE twitter.tweet (
                               id BIGSERIAL PRIMARY KEY,
                               text TEXT NOT NULL,
                               user_id BIGINT NOT NULL
);

CREATE TABLE twitter.retweet (
                                 id BIGSERIAL PRIMARY KEY,
                                 user_id BIGINT NOT NULL,
                                 tweet_id BIGINT NOT NULL
);

CREATE TABLE twitter.likes (
                                    id BIGSERIAL PRIMARY KEY,
                                    user_id BIGINT NOT NULL,
                                    tweet_id BIGINT NOT NULL
);

CREATE TABLE twitter.comment (
                                 id BIGSERIAL PRIMARY KEY,
                                 text TEXT NOT NULL,
                                 user_id BIGINT NOT NULL,
                                 tweet_id BIGINT NOT NULL
);