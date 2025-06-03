DROP TABLE IF EXISTS credencial;
DROP TABLE IF EXISTS administrador;
DROP TABLE IF EXISTS candidato;
DROP TABLE IF EXISTS moderador;
DROP TABLE IF EXISTS supervisor;
DROP TABLE IF EXISTS votante;

-- Paso 1: crear tablas sin relaciones cruzadas
CREATE TABLE administrador (
	id BIGSERIAL PRIMARY KEY,   
 	nombre VARCHAR(100),
    correo VARCHAR(100),
    contrasena VARCHAR(20)
);

CREATE TABLE moderador (
	id BIGSERIAL PRIMARY KEY,    
	nombre VARCHAR(255),
    correo VARCHAR(255),
    contrasena VARCHAR(255)
);

CREATE TABLE candidato (
	id BIGSERIAL PRIMARY KEY,   
	nombre VARCHAR(255),
    correo VARCHAR(255),
    contrasena VARCHAR(255),
    estado VARCHAR(50) DEFAULT 'PENDIENTE',
    moderador_id BIGINT
);

CREATE TABLE supervisor (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL,
    contrasena VARCHAR(255) NOT NULL
);

CREATE TABLE votante (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    doc_identidad INTEGER NOT NULL
);

-- Paso 2: crear credencial
CREATE TABLE credencial (
	id BIGSERIAL PRIMARY KEY,   
	correo VARCHAR(100) NOT NULL,
    contrasena VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL,
	usuario_id BIGINT,
	
    administrador_id BIGINT UNIQUE,
    moderador_id BIGINT UNIQUE,
    supervisor_id BIGINT UNIQUE,
    candidato_id BIGINT UNIQUE,
    votante_id BIGINT UNIQUE
);


CREATE TABLE proceso_electoral (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT
);

CREATE TABLE cargo (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    proceso_electoral_id BIGINT,
    CONSTRAINT fk_cargo_proceso FOREIGN KEY (proceso_electoral_id) REFERENCES proceso_electoral(id) ON DELETE CASCADE
);

-- Paso 3: agregar relaciones con ALTER TABLE
ALTER TABLE proceso_electoral ADD supervisor_id BIGINT;

ALTER TABLE administrador ADD COLUMN credencial_id BIGINT;
ALTER TABLE votante ADD COLUMN proceso_electoral_id BIGINT;
ALTER TABLE proceso_electoral ADD COLUMN votante_id BIGINT;

ALTER TABLE moderador ADD COLUMN credencial_id BIGINT;
ALTER TABLE candidato ADD COLUMN credencial_id BIGINT;
ALTER TABLE supervisor ADD COLUMN credencial_id BIGINT UNIQUE;
ALTER TABLE votante ADD COLUMN credencial_id BIGINT UNIQUE;

-- Relaciones entre entidades y credencial
ALTER TABLE administrador ADD CONSTRAINT fk_administrador_credencial FOREIGN KEY (credencial_id) REFERENCES credencial(id);
ALTER TABLE proceso_electoral
ADD CONSTRAINT fk_supervisor FOREIGN KEY (supervisor_id)
REFERENCES supervisor(id);
ALTER TABLE candidato ADD proceso_electoral_id BIGINT;
ALTER TABLE candidato ADD CONSTRAINT fk_proceso FOREIGN KEY (proceso_electoral_id) REFERENCES proceso_electoral(id);

ALTER TABLE moderador ADD CONSTRAINT fk_moderador_credencial FOREIGN KEY (credencial_id) REFERENCES credencial(id) ON DELETE SET NULL;
ALTER TABLE candidato ADD CONSTRAINT fk_candidato_moderador FOREIGN KEY (moderador_id) REFERENCES moderador(id) ON DELETE SET NULL;
ALTER TABLE candidato ADD CONSTRAINT fk_candidato_credencial FOREIGN KEY (credencial_id) REFERENCES credencial(id) ON DELETE SET NULL;
ALTER TABLE supervisor ADD CONSTRAINT fk_supervisor_credencial FOREIGN KEY (credencial_id) REFERENCES credencial(id) ON DELETE CASCADE;
ALTER TABLE votante ADD CONSTRAINT fk_votante_credencial FOREIGN KEY (credencial_id) REFERENCES credencial(id) ON DELETE CASCADE;
ALTER TABLE votante ADD CONSTRAINT fk_votante_proceso
FOREIGN KEY (proceso_electoral_id) REFERENCES proceso_electoral(id);

-- Relaciones inversas desde credencial
ALTER TABLE credencial ADD CONSTRAINT fk_credencial_admin FOREIGN KEY (administrador_id) REFERENCES administrador(id) ON DELETE CASCADE;
ALTER TABLE credencial ADD CONSTRAINT fk_credencial_mod FOREIGN KEY (moderador_id) REFERENCES moderador(id) ON DELETE CASCADE;
ALTER TABLE credencial ADD CONSTRAINT fk_credencial_sup FOREIGN KEY (supervisor_id) REFERENCES supervisor(id) ON DELETE CASCADE;
ALTER TABLE credencial ADD CONSTRAINT fk_credencial_cand FOREIGN KEY (candidato_id) REFERENCES candidato(id) ON DELETE CASCADE;
ALTER TABLE credencial ADD CONSTRAINT fk_credencial_vot FOREIGN KEY (votante_id) REFERENCES votante(id) ON DELETE CASCADE;
