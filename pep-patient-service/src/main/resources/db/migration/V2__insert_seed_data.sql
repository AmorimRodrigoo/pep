-- 1. Carga inicial de Pacientes para desenvolvimento e testes
INSERT INTO paciente (cpf, nome, data_nascimento, telefone, email, criado_em, versao)
VALUES
    ('52998224725', 'Maria Oliveira', '1992-08-20', '81988887777', 'maria.oliveira@email.com', now(), 0),
    ('11144477735', 'João Carlos Souza', '1980-03-15', '81999996666', 'joao.souza@email.com', now(), 0),
    ('98765432100', 'Ana Beatriz Lima', '2001-11-05', '81977775555', 'ana.lima@email.com', now(), 0)
    ON CONFLICT (cpf) DO NOTHING;

-- 2. Carga inicial de Usuários para testes de Login/RBAC
-- A senha_hash abaixo corresponde a "123456" gerada com BCrypt ($2a$10$...)
INSERT INTO usuario (nome, email, senha_hash, papel)
VALUES
    ('Dr. Roberto Almeida', 'medico@pep.mv.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'MEDICO'),
    ('Enf. Carla Mendes', 'enfermeiro@pep.mv.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ENFERMEIRO'),
    ('Administrador Sistema', 'admin@pep.mv.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN')
    ON CONFLICT (email) DO NOTHING;