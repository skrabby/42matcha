do
$do$
declare
	i int;
begin
	for i in 1..20
	loop
	INSERT INTO USERS (email, password, name, orientation, description, gender, birthday, latitude, longitude,popularity) VALUES (
                'girl'||i||'@gmail.com',
                '$2a$10$RZgG.3lUz7NmycW9i4EFz.YQ.oqdti7lQhFICWZZlQmN2Vmfaj/Cy', -- 'password'
                'girl'||i,
                'HETERO',
                'Very handsome!',
                'FEMALE',
                '1996-05-'||LPAD(i::VARCHAR(2),2,'0'),
                (floor(random()*(40-25+1))+25)::VARCHAR(2)||'.'||(floor(random()*(40-25+1))+25)::VARCHAR(2),
                (floor(random()*(40-25+1))+25)::VARCHAR(2)||'.'||(floor(random()*(40-25+1))+25)::VARCHAR(2),
               	(floor(random()*(50-1+1))+1))ON CONFLICT DO NOTHING;

	INSERT INTO USERS (email, password, name, orientation, description, gender, birthday, latitude, longitude,popularity) VALUES (
                'man'||i||'@gmail.com',
                '$2a$10$RZgG.3lUz7NmycW9i4EFz.YQ.oqdti7lQhFICWZZlQmN2Vmfaj/Cy', -- 'password'
                'man'||i,
                'HETERO',
                'Very handsome!',
                'MALE',
                '1995-05-'||LPAD(i::VARCHAR(2),2,'0'),
                (floor(random()*(40-25+1))+25)::VARCHAR(2)||'.'||(floor(random()*(40-25+1))+25)::VARCHAR(2),
                (floor(random()*(40-25+1))+25)::VARCHAR(2)||'.'||(floor(random()*(40-25+1))+25)::VARCHAR(2),
               	(floor(random()*(50-1+1))+1))ON CONFLICT DO NOTHING;
	end  loop;
end
$do$;

do
$do$
declare
	i int;
begin
	for i in 21..27
	loop
	INSERT INTO USERS (email, password, name, orientation, description, gender, birthday, latitude, longitude,popularity) VALUES (
                'girl'||i||'@gmail.com',
                '$2a$10$RZgG.3lUz7NmycW9i4EFz.YQ.oqdti7lQhFICWZZlQmN2Vmfaj/Cy', -- 'password'
                'girl'||i,
                'LESBIAN',
                'Very handsome!',
                'FEMALE',
                '1996-05-'||LPAD(i::VARCHAR(2),2,'0'),
                (floor(random()*(40-25+1))+25)::VARCHAR(2)||'.'||(floor(random()*(40-25+1))+25)::VARCHAR(2),
                (floor(random()*(40-25+1))+25)::VARCHAR(2)||'.'||(floor(random()*(40-25+1))+25)::VARCHAR(2),
               	(floor(random()*(50-1+1))+1))ON CONFLICT DO NOTHING;

	INSERT INTO USERS (email, password, name, orientation, description, gender, birthday, latitude, longitude,popularity) VALUES (
                'man'||i||'@gmail.com',
                '$2a$10$RZgG.3lUz7NmycW9i4EFz.YQ.oqdti7lQhFICWZZlQmN2Vmfaj/Cy', -- 'password'
                'man'||i,
                'GAY',
                'Very handsome!',
                'MALE',
                '1995-05-'||LPAD(i::VARCHAR(2),2,'0'),
                (floor(random()*(40-25+1))+25)::VARCHAR(2)||'.'||(floor(random()*(40-25+1))+25)::VARCHAR(2),
                (floor(random()*(40-25+1))+25)::VARCHAR(2)||'.'||(floor(random()*(40-25+1))+25)::VARCHAR(2),
               	(floor(random()*(50-1+1))+1))ON CONFLICT DO NOTHING;
	end  loop;
end
$do$;



do
$do$
declare
	i int;
begin
	for i in 28..36
	loop
	INSERT INTO USERS (email, password, name, orientation, description, gender, birthday, latitude, longitude,popularity) VALUES (
                'girl'||i||'@gmail.com',
                '$2a$10$RZgG.3lUz7NmycW9i4EFz.YQ.oqdti7lQhFICWZZlQmN2Vmfaj/Cy', -- 'password'
                'girl'||i,
                'BISEXUAL',
                'Very handsome!',
                'FEMALE',
                '1996-05-'||LPAD(i::VARCHAR(2),2,'0'),
                (floor(random()*(40-25+1))+25)::VARCHAR(2)||'.'||(floor(random()*(40-25+1))+25)::VARCHAR(2),
                (floor(random()*(40-25+1))+25)::VARCHAR(2)||'.'||(floor(random()*(40-25+1))+25)::VARCHAR(2),
               	(floor(random()*(50-1+1))+1))ON CONFLICT DO NOTHING;

	INSERT INTO USERS (email, password, name, orientation, description, gender, birthday, latitude, longitude,popularity) VALUES (
                'man'||i||'@gmail.com',
                '$2a$10$RZgG.3lUz7NmycW9i4EFz.YQ.oqdti7lQhFICWZZlQmN2Vmfaj/Cy', -- 'password'
                'man'||i,
                'BISEXUAL',
                'Very handsome!',
                'MALE',
                '1995-05-'||LPAD(i::VARCHAR(2),2,'0'),
                (floor(random()*(40-25+1))+25)::VARCHAR(2)||'.'||(floor(random()*(40-25+1))+25)::VARCHAR(2),
                (floor(random()*(40-25+1))+25)::VARCHAR(2)||'.'||(floor(random()*(40-25+1))+25)::VARCHAR(2),
               	(floor(random()*(50-1+1))+1))ON CONFLICT DO NOTHING;
	end  loop;
end
$do$;

--Add some tags
do
$do$
declare
	i int;
begin
	for i in 1..65
	loop
	insert into user_tags (user_id, tag_id) values (i,(floor(random()*(2-1+1))+1)), (i,(floor(random()*(4-3+1))+3)), (i,(floor(random()*(6-5+1))+5));
	end  loop;
end
$do$
