CREATE TABLE IF NOT EXISTS USERS(
    ID serial primary key,
    NAME VARCHAR NOT NULL,
    EMAIL VARCHAR UNIQUE,
    SEXPREF VARCHAR DEFAULT '',
    BIOGRAPHY TEXT DEFAULT '',
    GENDER VARCHAR DEFAULT '',
    PICT1 bytea DEFAULT '\\000',
    PICT2 bytea DEFAULT '\\000',
    PICT3 bytea DEFAULT '\\000',
    PICT4 bytea DEFAULT '\\000',
    PICT5 bytea DEFAULT '\\000');

create table if not exists TAGS(ID serial primary key, TAG VARCHAR UNIQUE);



