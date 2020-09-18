create table resources (
   id integer not null,
   create_date date,
   isPublic bit not null,
   modify_date date,
   name varchar(255),
   size double precision not null,
   type varchar(255),
   version double precision not null,
   fileId integer,
   primary key (id)
) engine=InnoDB;

create table user_resource (
   id_resource integer not null,
   id_user integer not null,
   ownerType varchar(255),
   primary key (id_resource, id_user)
) engine=InnoDB;

create table users (
   id integer not null,
   email varchar(255),
   password varchar(255),
   username varchar(255),
   primary key (id)
) engine=InnoDB;

alter table resources 
   add constraint FKnrsyg82ow99ppvyw7gh6gsyei 
   foreign key (fileId) 
   references resources (id);

alter table user_resource 
   add constraint FK6tcup4v9e1b6asr2x4vhb5lje 
   foreign key (id_resource) 
   references resources (id);

alter table user_resource 
   add constraint FKa3rkn3pbp08vwlbp3jt4bnrhj 
   foreign key (id_user) 
   references users (id);