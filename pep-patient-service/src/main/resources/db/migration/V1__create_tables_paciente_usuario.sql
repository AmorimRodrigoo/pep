CREATE TABLE paciente (
                          id              BIGSERIAL PRIMARY KEY,
                          cpf             VARCHAR(11) NOT NULL UNIQUE,
                          nome            VARCHAR(150) NOT NULL,
                          data_nascimento DATE NOT NULL,
                          telefone        VARCHAR(20),
                          email           VARCHAR(150),
                          criado_em       TIMESTAMP NOT NULL DEFAULT now(),
                          versao          INTEGER NOT NULL DEFAULT 0  -- usado para optimistic locking (@Version)
);

CREATE INDEX idx_paciente_cpf ON paciente (cpf);

CREATE TABLE usuario (
                         id         BIGSERIAL PRIMARY KEY,
                         nome       VARCHAR(150) NOT NULL,
                         email      VARCHAR(150) NOT NULL UNIQUE,
                         senha_hash VARCHAR(255) NOT NULL,
                         papel      VARCHAR(20) NOT NULL CHECK (papel IN ('MEDICO','ENFERMEIRO','ADMIN'))
);