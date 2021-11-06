do
$do$
declare
	i int;
begin
	for i in 1..20
	loop
	INSERT INTO USERS (email, password, name, orientation, description, gender) VALUES (
                'girl'||i||'@gmail.com',
                '$2a$10$RZgG.3lUz7NmycW9i4EFz.YQ.oqdti7lQhFICWZZlQmN2Vmfaj/Cy', -- 'password'
                'girl'||i,
                'HETERO',
                'Very handsome!',
                'FEMALE') ON CONFLICT DO NOTHING;

	INSERT INTO USERS (email, password, name, orientation, description, gender) VALUES (
                'man'||i||'@gmail.com',
                '$2a$10$RZgG.3lUz7NmycW9i4EFz.YQ.oqdti7lQhFICWZZlQmN2Vmfaj/Cy', -- 'password'
                'man'||i,
                'HETERO',
                'Very handsome!',
                'MALE') ON CONFLICT DO NOTHING;
	end  loop;
end
$do$;

INSERT INTO USERS (email, password, name, orientation, description, gender) VALUES (
                'ductruong1802@gmail.com',
                '$2a$10$RZgG.3lUz7NmycW9i4EFz.YQ.oqdti7lQhFICWZZlQmN2Vmfaj/Cy', -- 'password'
                'Duc',
                'HETERO',
                'Very handsome!',
                'MALE') ON CONFLICT DO NOTHING;
