CREATE TABLE Usuario
(
    id_usuario int not null auto_increment,
    nombre     varchar(200),
    email      varchar(200),
    password   varchar(250),
    tipo       varchar(20),
    fecha      timestamp,
    primary key (id_usuario)
);
create table departamento(
    id_departamento int not null auto_increment,
    nombre varchar(250),
    descripcion varchar(250),
    primary key (id_departamento)
);
CREATE TABLE Empleado
(
    id_empleado int not null auto_increment,
    nombre     varchar(200),
    apellido     varchar(200),
    email      varchar(200),
    fecha      timestamp,
    salario   double,
    sexo       varchar(20),
    telefono    int,
    id_departamento int,
    id_usuario int,
    FOREIGN KEY (id_departamento) REFERENCES departamento (id_departamento),
    FOREIGN KEY (id_usuario) REFERENCES Usuario (id_usuario),
    primary key (id_empleado)
);
create table Mensajeria (

    id_mensajeria int not null  auto_increment,
    descripcion varchar(250),
    fecha      timestamp,
    id_departamento int,
    id_empleado int,
    foreign key (id_departamento) REFERENCES departamento (id_departamento),
    foreign key (id_empleado) references Empleado (id_empleado)
);

insert into usuario (nombre, email,password,tipo,fecha)
values ('jhoan','jhoanpaliz@hotmail.com','','usario', current_timestamp);
insert into usuario (nombre, email,password,tipo,fecha)
values ('jhoan','jhoanpaliz@hotmail.com','','usuario', current_timestamp);
insert into empleado (nombre,apellido,email,fecha,salario,sexo,telefono,id_departamento,id_usuario)
values ('jhoan','paliz','jhoanpaliz@hotmail.com',current_timestamp,1200,'hombre',654681542,1,1);
insert into departamento (nombre, descripcion)
values ('recursos humanos','control del personal');
insert into usuario (nombre, email,password,tipo,fecha)
values ('michael','michaelpaliz@hotmail.com','','usuario', current_timestamp);
insert into empleado (nombre,apellido,email,fecha,salario,sexo,telefono,id_departamento,id_usuario)
values ('michael','paliz','michaelpaliz@hotmail.com',current_timestamp,1200,'hombre',654681542,1,2);
insert into empleado (nombre,apellido,email,fecha,salario,sexo,telefono,id_departamento,id_usuario)
values ('michael','paliz','michaelpaliz@hotmail.com',current_timestamp,1200,'hombre',654681542,2,3);

insert into empleado (nombre,apellido,email,fecha,salario,sexo,telefono,id_departamento,id_usuario)
values ('paco','paliz','pacopaliz@hotmail.com',current_timestamp,1200,'hombre',654681542,2,null);

insert into empleado (nombre,apellido,email,fecha,salario,sexo,telefono,id_departamento,id_usuario)
values ('antonio','paliz','antoniopaliz@hotmail.com',current_timestamp,1200,'M',654681542,2,null);