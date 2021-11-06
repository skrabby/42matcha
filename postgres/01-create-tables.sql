-- General table for users
CREATE TABLE IF NOT EXISTS USERS
(
    id          BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    email       VARCHAR(100) UNIQUE NOT NULL,
    password    VARCHAR NOT NULL,
    name        VARCHAR(100) NOT NULL,
    orientation VARCHAR(50) DEFAULT '',
    description VARCHAR(500) DEFAULT '',
    gender      VARCHAR DEFAULT '',
    birthday    DATE,
    popularity  int NOT NULL DEFAULT 0,
    latitude    VARCHAR(255),
    longitude   VARCHAR(255),
    avatar_url  VARCHAR(255));

-- Contains all likes for users
CREATE TABLE IF NOT EXISTS LIKES(
    id          BIGSERIAL PRIMARY KEY,
    liker_id    BIGSERIAL REFERENCES USERS(id) ON DELETE CASCADE,
    liked_id    BIGSERIAL REFERENCES USERS(id) ON DELETE CASCADE,
    date        DATE NOT NULL DEFAULT CURRENT_TIMESTAMP);

-- ALL pictures users
CREATE TABLE IF NOT EXISTS PICTURES(
    id      BIGSERIAL PRIMARY KEY,
    url     VARCHAR(255) NOT NULL,
    user_id BIGSERIAL REFERENCES USERS(id) ON DELETE CASCADE);

-- Contains all tags in system
CREATE TABLE IF NOT EXISTS TAGS(
    id          BIGSERIAL PRIMARY KEY,
    tag         VARCHAR(50) NOT NULL);

-- USER TAGS
CREATE TABLE IF NOT EXISTS USER_TAGS(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGSERIAL REFERENCES USERS(id) ON DELETE CASCADE,
    tag_id      BIGSERIAL REFERENCES TAGS(id) ON DELETE CASCADE);

-- Generate General tags
insert  into tags (tag) values ('Music'), ('Sleep'), ('Run');

-- create table if not exists TAGS(ID serial primary key, TAG VARCHAR UNIQUE);



