 -- allow to make duplicate validation dependent on the publisher. Site sites want to send several offers to the same email address,
 -- and still control the sends from outside (i.e. not use resends inside pony leads)
alter table publishers add column allow_duplicates tinyint default 0 after password;
