CREATE TABLE IF NOT EXISTS USERS
(
    id          BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    email       VARCHAR(100) UNIQUE NOT NULL,
    password    VARCHAR NOT NULL,
    name        VARCHAR(100) NOT NULL,
    orientation VARCHAR(50) DEFAULT '',
    description VARCHAR(500) DEFAULT '',
    gender      VARCHAR DEFAULT '',
    pic1        BYTEA DEFAULT '\\000',
    pic2        BYTEA DEFAULT '\\000',
    pic3        BYTEA DEFAULT '\\000',
    pic4        BYTEA DEFAULT '\\000',
    pic5        BYTEA DEFAULT '\\000'
);

INSERT INTO USERS (email, password, name, orientation, description, gender) VALUES (
                'ductruong1802@gmail.com',
                '$2a$10$RZgG.3lUz7NmycW9i4EFz.YQ.oqdti7lQhFICWZZlQmN2Vmfaj/Cy', -- 'password'
                'Duc',
                'HETERO',
                'Very handsome!',
                'MALE') ON CONFLICT DO NOTHING;


-- create table if not exists TAGS(ID serial primary key, TAG VARCHAR UNIQUE);

--Create Chat Rooms
CREATE TABLE IF NOT EXISTS CHAT_ROOMS(
	    id          BIGSERIAL PRIMARY KEY,
	    user_id1     BIGSERIAL REFERENCES USERS(id) ON DELETE cascade,
	    user_id2     BIGSERIAL REFERENCES USERS(id) ON DELETE cascade)


