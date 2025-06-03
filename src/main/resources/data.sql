
-- Luego referenciamos la credencial desde el administrador
INSERT INTO administrador (nombre, correo, contrasena)
VALUES ('Admin Principal','admin@votacion.com','admin123');


INSERT INTO credencial (correo, contrasena, rol, usuario_id)
VALUES ('admin@votacion.com', 'admin123', 'ADMINISTRADOR', 1);

UPDATE ADMINISTRADOR SET CREDENCIAL_ID = 1 WHERE ID = 1;
