-- Borrar la BD si existe
DROP DATABASE socialdrivers IF EXISTS;
CREATE DATABASE socialdrivers;
USE socialdrivers;

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

-- Función almacenada para devolver el rol de un usuario
DELIMITER $$
CREATE FUNCTION rolnombre(nameuser VARCHAR(50)) 
RETURNS VARCHAR(50) DETERMINISTIC
BEGIN
    DECLARE nombrerol VARCHAR(50);

    SELECT r.nombre_rol
    INTO nombrerol
    FROM Rol r
    JOIN Usuario u ON r.id_rol = u.rol_id
    WHERE u.username = nameuser
    LIMIT 1; 

    RETURN nombrerol;
END$$
DELIMITER ;

-- Insertamos Roles
INSERT INTO Rol (nombre_rol) VALUES ('Admin');
INSERT INTO Rol (nombre_rol) VALUES ('Usuario');

-- Insertamos Tipos de Marcador
INSERT INTO Tipo_Marcador (nombre_tipo) VALUES ('Radar Móvil');
INSERT INTO Tipo_Marcador (nombre_tipo) VALUES ('Control Alcoholemia');
INSERT INTO Tipo_Marcador (nombre_tipo) VALUES ('Multa');
INSERT INTO Tipo_Marcador (nombre_tipo) VALUES ('Accidente');
INSERT INTO Tipo_Marcador (nombre_tipo) VALUES ('Atasco');

-- Insertamos Usuarios
INSERT INTO Usuario (username, password, email, rol_id)
    VALUES ('admin', '1234', 'admin1@gmail.com', 1);
INSERT INTO Usuario (username, password, email, rol_id)
    VALUES ('user1', '1234', 'user1@gmail.com', 2);

-- Insertamos Marcadores (asociados a user1, que tiene id=2)
INSERT INTO Marcador (usuario_id, latitud, longitud, descripcion, tipo_marcador_id)
    VALUES (2, 37.2713, -6.9485, 'Radar móvil en la Autopista Huelva-Sevilla', 1);
INSERT INTO Marcador (usuario_id, latitud, longitud, descripcion, tipo_marcador_id)
    VALUES (2, 37.2566, -7.1740, 'Control de Alcoholemia en la rotonda del Polígono de Lepe', 2);

INSERT INTO Marcador (usuario_id, latitud, longitud, descripcion, tipo_marcador_id)
VALUES
    (3,  40.4167, -3.7033, 'Radar móvil en la M-30, Madrid', 1),
    (3,  40.4172, -3.7035, 'Control de Alcoholemia cerca de la Puerta del Sol, Madrid', 2),
    (3,  40.4169, -3.7028, 'Accidente leve en la Calle Mayor, Madrid', 4),

    (4,  40.4165, -3.7029, 'Radar móvil en la Gran Vía, Madrid', 1),
    (4,  40.4175, -3.7037, 'Control de Alcoholemia en la Plaza Mayor, Madrid', 2),
    (4,  40.4168, -3.7030, 'Accidente en la Calle Alcalá, Madrid', 4),

    (5,  40.4166, -3.7031, 'Radar móvil en la Calle Atocha, Madrid', 1),
    (5,  40.4170, -3.7032, 'Control de Alcoholemia en la Plaza de España, Madrid', 2),
    (5,  40.4164, -3.7034, 'Accidente cerca del Parque del Retiro, Madrid', 4),

    (6,  41.3851,  2.1734, 'Radar móvil en la Avinguda Diagonal, Barcelona', 1),
    (6,  41.3855,  2.1732, 'Control de Alcoholemia cerca de la Sagrada Familia, Barcelona', 2),
    (6,  41.3849,  2.1736, 'Accidente en la Rambla de Catalunya, Barcelona', 4),

    (7,  41.3852,  2.1735, 'Radar móvil en la Gran Via de les Corts Catalanes, Barcelona', 1),
    (7,  41.3856,  2.1733, 'Control de Alcoholemia en la Plaça de Catalunya, Barcelona', 2),
    (7,  41.3848,  2.1737, 'Accidente en Passeig de Gràcia, Barcelona', 4),

    (8,  41.3853,  2.1736, 'Radar móvil en la Av. Meridiana, Barcelona', 1),
    (8,  41.3857,  2.1734, 'Control de Alcoholemia en la Calle Mallorca, Barcelona', 2),
    (8,  41.3847,  2.1738, 'Accidente cerca del Port Vell, Barcelona', 4),

    (9,  39.4702, -0.3768, 'Radar móvil en la Av. del Cid, Valencia', 1),
    (9,  39.4705, -0.3769, 'Control de Alcoholemia en la Plaza del Ayuntamiento, Valencia', 2),
    (9,  39.4699, -0.3767, 'Accidente cerca de la Ciudad de las Artes, Valencia', 4),

    (10, 39.4703, -0.3770, 'Radar móvil en la Av. Blasco Ibáñez, Valencia', 1),
    (10, 39.4706, -0.3771, 'Control de Alcoholemia en la Calle Colón, Valencia', 2),
    (10, 39.4698, -0.3766, 'Accidente en el Puente de las Flores, Valencia', 4),

    (11, 39.4704, -0.3772, 'Radar móvil en la Av. Aragón, Valencia', 1),
    (11, 39.4707, -0.3773, 'Control de Alcoholemia en la Plaza de la Reina, Valencia', 2),
    (11, 39.4697, -0.3765, 'Accidente cerca del Estadio Mestalla, Valencia', 4),

    (12, 37.3891, -5.9845, 'Radar móvil en la SE-30, Sevilla', 1),
    (12, 37.3894, -5.9847, 'Control de Alcoholemia en la Av. de la Constitución, Sevilla', 2),
    (12, 37.3887, -5.9843, 'Accidente en la Plaza de España, Sevilla', 4),

    (13, 37.3892, -5.9846, 'Radar móvil en la Calle Sierpes, Sevilla', 1),
    (13, 37.3895, -5.9848, 'Control de Alcoholemia cerca de la Giralda, Sevilla', 2),
    (13, 37.3886, -5.9842, 'Accidente en la Puerta de Jerez, Sevilla', 4),

    (14, 37.3893, -5.9849, 'Radar móvil en la Av. de la Palmera, Sevilla', 1),
    (14, 37.3896, -5.9850, 'Control de Alcoholemia en la Calle San Fernando, Sevilla', 2),
    (14, 37.3885, -5.9841, 'Accidente en la Plaza Nueva, Sevilla', 4),

    (15, 41.6488, -0.8891, 'Radar móvil en la Z-30, Zaragoza', 1),
    (15, 41.6491, -0.8892, 'Control de Alcoholemia en la Plaza del Pilar, Zaragoza', 2),
    (15, 41.6485, -0.8890, 'Accidente en la Calle Alfonso I, Zaragoza', 4),

    (16, 41.6489, -0.8893, 'Radar móvil en la Av. de Goya, Zaragoza', 1),
    (16, 41.6492, -0.8894, 'Control de Alcoholemia en la Gran Vía, Zaragoza', 2),
    (16, 41.6484, -0.8889, 'Accidente en la Calle Don Jaime I, Zaragoza', 4),

    (17, 41.6490, -0.8895, 'Radar móvil en la Av. de Madrid, Zaragoza', 1),
    (17, 41.6493, -0.8896, 'Control de Alcoholemia en la Plaza de España, Zaragoza', 2),
    (17, 41.6483, -0.8888, 'Accidente cerca del Puente de Piedra, Zaragoza', 4),

    (18, 36.7213, -4.4214, 'Radar móvil en la MA-20, Málaga', 1),
    (18, 36.7216, -4.4216, 'Control de Alcoholemia en la Plaza de la Constitución, Málaga', 2),
    (18, 36.7209, -4.4212, 'Accidente en la Calle Larios, Málaga', 4),

    (19, 36.7214, -4.4217, 'Radar móvil en la Av. de Andalucía, Málaga', 1),
    (19, 36.7217, -4.4218, 'Control de Alcoholemia cerca de la Alcazaba, Málaga', 2),
    (19, 36.7208, -4.4211, 'Accidente en la Malagueta, Málaga', 4),

    (20, 36.7215, -4.4219, 'Radar móvil en la Calle Carretería, Málaga', 1),
    (20, 36.7218, -4.4220, 'Control de Alcoholemia en la Plaza de la Merced, Málaga', 2),
    (20, 36.7207, -4.4210, 'Accidente en el Paseo del Parque, Málaga', 4),

    (21, 37.9834, -1.1299, 'Radar móvil en la A-30, Murcia', 1),
    (21, 37.9837, -1.1301, 'Control de Alcoholemia en la Gran Vía Escultor Salzillo, Murcia', 2),
    (21, 37.9831, -1.1297, 'Accidente en la Plaza de las Flores, Murcia', 4),

    (22, 37.9835, -1.1302, 'Radar móvil en la Av. de la Libertad, Murcia', 1),
    (22, 37.9838, -1.1303, 'Control de Alcoholemia cerca de la Catedral de Murcia', 2),
    (22, 37.9830, -1.1296, 'Accidente en la Calle Trapería, Murcia', 4),

    (23, 37.9836, -1.1304, 'Radar móvil en la Ronda de Levante, Murcia', 1),
    (23, 37.9839, -1.1305, 'Control de Alcoholemia en la Plaza Cardenal Belluga, Murcia', 2),
    (23, 37.9829, -1.1295, 'Accidente en la Calle de la Aurora, Murcia', 4),

    (24, 39.5696,  2.6502, 'Radar móvil en la Ma-20, Palma de Mallorca', 1),
    (24, 39.5699,  2.6504, 'Control de Alcoholemia en la Plaza Mayor, Palma', 2),
    (24, 39.5692,  2.6500, 'Accidente en el Paseo Marítimo, Palma', 4),

    (25, 39.5697,  2.6505, 'Radar móvil en la Av. de Gabriel Alomar, Palma', 1),
    (25, 39.5700,  2.6506, 'Control de Alcoholemia cerca de la Catedral de Mallorca', 2),
    (25, 39.5691,  2.6499, 'Accidente en la Calle San Miguel, Palma', 4),

    (26, 39.5698,  2.6507, 'Radar móvil en la Calle Manacor, Palma', 1),
    (26, 39.5701,  2.6508, 'Control de Alcoholemia en la Plaza de España, Palma', 2),
    (26, 39.5690,  2.6498, 'Accidente en la Calle Sindicato, Palma', 4),

    (27, 43.2630, -2.9350, 'Radar móvil en la BI-631, Bilbao', 1),
    (27, 43.2633, -2.9352, 'Control de Alcoholemia en la Plaza Moyúa, Bilbao', 2),
    (27, 43.2627, -2.9348, 'Accidente en la Gran Vía de Don Diego López, Bilbao', 4),

    (28, 43.2631, -2.9353, 'Radar móvil en la Calle Autonomía, Bilbao', 1),
    (28, 43.2634, -2.9354, 'Control de Alcoholemia cerca del Guggenheim, Bilbao', 2),
    (28, 43.2626, -2.9347, 'Accidente en la Calle Ercilla, Bilbao', 4),

    (29, 43.2632, -2.9355, 'Radar móvil en la Av. Sabino Arana, Bilbao', 1),
    (29, 43.2635, -2.9356, 'Control de Alcoholemia en la Plaza Circular, Bilbao', 2),
    (29, 43.2625, -2.9346, 'Accidente cerca del Casco Viejo, Bilbao', 4),

    (30, 37.1765, -3.5979, 'Radar móvil en la GR-30, Granada', 1),
    (30, 37.1768, -3.5981, 'Control de Alcoholemia en la Plaza Nueva, Granada', 2),
    (30, 37.1762, -3.5977, 'Accidente en la Calle Reyes Católicos, Granada', 4),

    (31, 37.1766, -3.5982, 'Radar móvil en la Av. de la Constitución, Granada', 1),
    (31, 37.1769, -3.5983, 'Control de Alcoholemia cerca de la Alhambra, Granada', 2),
    (31, 37.1761, -3.5976, 'Accidente en el Paseo de los Tristes, Granada', 4),

    (32, 37.1767, -3.5984, 'Radar móvil en la Calle Gran Vía de Colón, Granada', 1),
    (32, 37.1770, -3.5985, 'Control de Alcoholemia en la Plaza Bib-Rambla, Granada', 2),
    (32, 37.1760, -3.5975, 'Accidente cerca de la Catedral, Granada', 4);

