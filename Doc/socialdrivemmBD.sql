
-- Tabla Rol
CREATE TABLE Rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL
);

-- Tabla Tipo_Marcador
CREATE TABLE Tipo_Marcador (
    id_tipo_marcador INT AUTO_INCREMENT PRIMARY KEY,
    nombre_tipo VARCHAR(50) NOT NULL
);

-- Tabla Usuario
CREATE TABLE Usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    rol_id INT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (rol_id) REFERENCES Rol(id_rol)
);

-- Tabla Marcador
CREATE TABLE Marcador (
    id_marcador INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    latitud DOUBLE NOT NULL,
    longitud DOUBLE NOT NULL,
    descripcion TEXT,
    tipo_marcador_id INT,
    fecha_publicacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id_usuario),
    FOREIGN KEY (tipo_marcador_id) REFERENCES Tipo_Marcador(id_tipo_marcador)
);

-- Cargamos Roles
INSERT INTO Rol (nombre_rol) VALUES ('Admin');
INSERT INTO Rol (nombre_rol) VALUES ('Usuario');

-- Cargamos Marcadores
INSERT INTO Tipo_Marcador (nombre_tipo) VALUES ('Radar Móvil');
INSERT INTO Tipo_Marcador (nombre_tipo) VALUES ('Control Alcoholemia');
INSERT INTO Tipo_Marcador (nombre_tipo) VALUES ('Multa');
INSERT INTO Tipo_Marcador (nombre_tipo) VALUES ('Accidente');
INSERT INTO Tipo_Marcador (nombre_tipo) VALUES ('Atasco');

-- Cargamos Usuarios
INSERT INTO Usuario (username, password, email, rol_id) VALUES ('admin', '1234', 'admin1@gmail.com', 1);
INSERT INTO Usuario (username, password, email, rol_id) VALUES ('user1', '1234', 'user1@gmail.com', 2);

-- Cargamos Marcadores
INSERT INTO Marcador (usuario_id, latitud, longitud, descripcion, tipo_marcador_id) VALUES (2, 37.2713, -6.9485, 'Radar móvil en la Autopista Huelva-Sevilla', 1);
INSERT INTO Marcador (usuario_id, latitud, longitud, descripcion, tipo_marcador_id) VALUES (2, 37.2566, -7.1740, 'Control de Alcoholemia en la rotonda del Polígono de Lepe', 2);
