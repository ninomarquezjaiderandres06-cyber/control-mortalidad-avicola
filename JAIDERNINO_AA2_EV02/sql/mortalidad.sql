-- Script de base de datos: control de mortalidad avicola
CREATE DATABASE IF NOT EXISTS mortalidad_avicola
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE mortalidad_avicola;

CREATE TABLE IF NOT EXISTS registro_mortalidad (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    galpon         INT          NOT NULL,           -- numero de galpon (1 a 8)
    fecha          DATE         NOT NULL,           -- fecha del registro
    aves_muertas   INT          NOT NULL DEFAULT 0, -- pollos muertos en el dia
    descartes      INT          NOT NULL DEFAULT 0, -- pollos descartados
    observaciones  VARCHAR(255) NULL,
    CONSTRAINT chk_galpon CHECK (galpon BETWEEN 1 AND 8),
    CONSTRAINT chk_aves   CHECK (aves_muertas >= 0 AND descartes >= 0)
);

-- Datos de ejemplo
INSERT INTO registro_mortalidad (galpon, fecha, aves_muertas, descartes, observaciones) VALUES
(1, '2026-11-01', 12, 2, 'Dia normal'),
(2, '2026-11-01', 8, 1, 'Sin novedad');
