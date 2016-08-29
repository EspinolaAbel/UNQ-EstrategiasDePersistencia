CREATE DATABASE Bichomon;
USE Bichomon;
CREATE TABLE Especie(nombre VARCHAR(30), tipo VARCHAR(20), altura INT,peso FLOAT, cantidad_de_bichos INT, url_foto VARCHAR(100), energia_inicial Int, PRIMARY KEY(nombre));
CREATE TABLE Bicho(nombre VARCHAR(30), nombre_especie VARCHAR(30), PRIMARY KEY(nombre), CONSTRAINT fk_Bicho FOREIGN KEY (nombre_especie) REFERENCES Especie(nombre));


#NOTA
#----
##Cambié algunos nombre de columnas porque estaban en cammel case, ahora están con guión bajo. También agregué la columna url_foto
##a la tabla Especie.
##Si ya cargaste esta BD antes del cambio, tenés que cambiarle los 
##nombres a esas columnas corriendo lo que está abajo (descomentar primero).

#use Bichomon;
#alter table Especie change column cantidadDeBichos cantidad_de_bichos Integer;
#alter table Bicho drop foreign key fk_Bicho;
#alter table Bicho change column nombreEspecie nombre_especie Varchar(20) ;
#alter table Bicho add constraint fk_Bicho foreign key (nombre_especie) references Especie(nombre);

#alter table Especie add column url_foto varchar(100);
#alter table Especie add column energia_inicial Int;

