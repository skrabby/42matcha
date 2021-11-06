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
    popularity  int NOT NULL DEFAULT 0);

-- Contains all likes for users
CREATE TABLE IF NOT EXISTS LIKES(
    id          BIGSERIAL PRIMARY KEY,
    liker_id    BIGSERIAL REFERENCES USERS(id) ON DELETE CASCADE,
    liked_id    BIGSERIAL REFERENCES USERS(id) ON DELETE CASCADE,
    date        DATE NOT NULL);

-- Users pictures

CREATE TABLE IF NOT EXISTS USERS_PICTURES(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGSERIAL REFERENCES USERS(id) ON DELETE CASCADE,
    file_name   VARCHAR(255) NOT NULL,
    is_avatar   BOOLEAN NOT NULL DEFAULT FALSE);

-- Contains all tags in system
CREATE TABLE IF NOT EXISTS TAGS(
    id          BIGSERIAL PRIMARY KEY,
    tag         VARCHAR(50) NOT NULL);

-- USER TAGS

CREATE TABLE IF NOT EXISTS USERS_TAGS(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGSERIAL REFERENCES USERS(id) ON DELETE CASCADE,
    tag_id      BIGSERIAL REFERENCES TAGS(id) ON DELETE CASCADE);


-- create table if not exists TAGS(ID serial primary key, TAG VARCHAR UNIQUE);



