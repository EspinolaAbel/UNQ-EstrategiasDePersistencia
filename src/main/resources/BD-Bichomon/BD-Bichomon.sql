CREATE DATABASE Bichomon;
USE Bichomon;
CREATE TABLE Especie(nombre VARCHAR(30), tipo VARCHAR(20), altura INT,peso FLOAT, cantidadDeBichos INT, PRIMARY KEY(nombre));
CREATE TABLE Bicho(nombre VARCHAR(30), nombreEspecie VARCHAR(30), PRIMARY KEY(nombre), CONSTRAINT fk_Bicho FOREIGN KEY (nombreEspecie) REFERENCES Especie(nombre));
