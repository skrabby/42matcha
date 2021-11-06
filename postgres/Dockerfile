FROM postgres:11.1

ADD ./01-create-tables.sql /docker-entrypoint-initdb.d/
ADD ./02-create-users.sql /docker-entrypoint-initdb.d/