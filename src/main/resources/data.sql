-- ===============================
-- LIMPEZA (evita erro se rodar mais de uma vez)
-- ===============================
DELETE FROM tb_pessoa_roles;
DELETE FROM tb_item_venda;
DELETE FROM tb_venda;
DELETE FROM tb_produto;
DELETE FROM tb_departamento;
DELETE FROM tb_pessoa_fisica;
DELETE FROM tb_pessoa_juridica;
DELETE FROM tb_pessoa;
DELETE FROM tb_role;

-- ===============================
-- RESTART IDENTITY (H2)
-- ===============================
ALTER TABLE tb_role ALTER COLUMN id RESTART WITH 1;
ALTER TABLE tb_pessoa ALTER COLUMN id RESTART WITH 1;
ALTER TABLE tb_produto ALTER COLUMN id RESTART WITH 1;
ALTER TABLE tb_venda ALTER COLUMN id RESTART WITH 1;
ALTER TABLE tb_item_venda ALTER COLUMN id RESTART WITH 1;

-- ===============================
-- ROLES
-- ===============================
INSERT INTO tb_role (id, nome) VALUES (1, 'ROLE_USER');
INSERT INTO tb_role (id, nome) VALUES (2, 'ROLE_ADMIN');

-- garante que o próximo role_id seja 3
ALTER TABLE tb_role ALTER COLUMN id RESTART WITH 3;

-- ===============================
-- PESSOAS (sem coluna role!)
-- ===============================
INSERT INTO tb_pessoa (id, email, telefone, password)
VALUES (1, 'joao@email.com', '9999-9999',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoOa3P5xLrZJjF8EwF0lF6Z6MZlZQ4E8xC');

INSERT INTO tb_pessoa_fisica (id, nome, cpf)
VALUES (1, 'João Silva', '12345678900');

INSERT INTO tb_pessoa (id, email, telefone, password)
VALUES (2, 'maria@email.com', '1111-9999',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoOa3P5xLrZJjF8EwF0lF6Z6MZlZQ4E8xC');

INSERT INTO tb_pessoa_fisica (id, nome, cpf)
VALUES (2, 'Maria Joaquina', '12345678978');

INSERT INTO tb_pessoa (id, email, telefone, password)
VALUES (3, 'empresa@email.com', '8888-8888',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoOa3P5xLrZJjF8EwF0lF6Z6MZlZQ4E8xC');

INSERT INTO tb_pessoa_juridica (id, razao_social, cnpj)
VALUES (3, 'Tech Solutions Ltda', '1112223330001');

INSERT INTO tb_pessoa (id, email, telefone, password)
VALUES (4, 'admin@admin.com', '11999998888',
        '$2a$10$2iy3DcqYknmfKS2PmpO.kOHwayn./YExIquR8SeuflbXcYFWX138u');

INSERT INTO tb_pessoa_fisica (id, nome, cpf)
VALUES (4, 'Administrador', '04510018185');

-- ===============================
-- RELAÇÃO PESSOA x ROLES
-- ===============================
-- João -> USER
INSERT INTO tb_pessoa_roles (pessoa_id, role_id) VALUES (1, 1);

-- Maria -> USER
INSERT INTO tb_pessoa_roles (pessoa_id, role_id) VALUES (2, 1);

-- Empresa -> ADMIN
INSERT INTO tb_pessoa_roles (pessoa_id, role_id) VALUES (3, 2);

-- Admin -> USER + ADMIN
INSERT INTO tb_pessoa_roles (pessoa_id, role_id) VALUES (4, 1);
INSERT INTO tb_pessoa_roles (pessoa_id, role_id) VALUES (4, 2);

-- ===============================
-- AJUSTE DO AUTO-INCREMENT PARA CONTINUAR DEPOIS DOS INSERTS
-- ===============================
ALTER TABLE tb_pessoa ALTER COLUMN id RESTART WITH 5;


-- ===============================
-- DEPARTAMENTOS
-- ===============================

INSERT INTO tb_departamento (id, nome, descricao) VALUES
(1, 'Notebooks', 'Notebooks e Laptops de diversas marcas e configurações.'),
(2, 'Fones de Ouvido', 'Fones de ouvido com e sem fio, headsets gamers e profissionais.'),
(3, 'Teclados', 'Teclados mecânicos, de membrana, gamers e ergonômicos.'),
(4, 'Mouses', 'Mouses gamers, sem fio e ergonômicos.'),
(5, 'Memórias RAM', 'Módulos de memória RAM DDR4 e DDR5 para desktops e notebooks.'),
(6, 'PCs', 'Computadores de mesa, PCs Gamers e Workstations.'),
(7, 'Monitores', 'Monitores para trabalho e jogos.'),
(8, 'Armazenamento', 'HDs, SSDs e NVMe para armazenamento de dados.'),
(9, 'Placas de Vídeo', 'GPUs para jogos e criação de conteúdo.'),
(10, 'Processadores', 'CPUs Intel e AMD para diversas finalidades.'),
(11, 'Placas Mãe', 'Placas mãe compatíveis com processadores Intel e AMD.'),
(12, 'Fontes', 'Fontes de alimentação para PCs.'),
(13, 'Gabinetes', 'Gabinetes para PC e modelos gamers.'),
(14, 'Coolers', 'Sistemas de refrigeração para CPU e gabinetes.'),
(15, 'Periféricos', 'Outros periféricos como webcams, microfones, caixas de som, etc.');


-- ===============================
-- PRODUTOS
-- ===============================

INSERT INTO tb_produto (id, descricao, valor, image_url, departamento_id) VALUES
(1, 'Notebook i5 8GB RAM', 3599.00, '/imagens/produtos/notebooki5.png', 1),
(2, 'Notebook i7 16GB RAM', 4899.00, '/imagens/produtos/notebooki7.png', 1),
(3, 'Notebook Gamer RTX', 6999.00, '/imagens/produtos/notebookgamer.png', 1),
(4, 'Fone de Ouvido Bluetooth', 250.00, '/imagens/produtos/fonedeouvido.png', 2),
(5, 'Headset Gamer', 249.00, '/imagens/produtos/headsetgamer.png', 2),
(6, 'Teclado Mecânico RGB Pro', 400.00, '/imagens/produtos/tecladomecanico.png', 3),
(7, 'Teclado Mecânico RGB', 299.00, '/imagens/produtos/tecladopro.png', 3),
(8, 'Mouse Gamer Wireless', 180.00, '/imagens/produtos/mousegamerwifi.png', 4),
(9, 'Mouse Gamer RGB', 199.00, '/imagens/produtos/mouse.jpg', 4),
(10, 'Memória RAM 16GB RGB', 350.00, '/imagens/produtos/memoriaram16.png', 5),
(11, 'Memória RAM 8GB DDR4', 179.00, '/imagens/produtos/memoriaram8.png', 5),
(12, 'Memória RAM 16GB DDR4', 299.00, '/imagens/produtos/memoriaram16rgb.png', 5),
(13, 'Memória RAM 32GB DDR4', 549.00, '/imagens/produtos/memoriaram32.png', 5),
(14, 'PC Gamer I5', 2599.00, '/imagens/produtos/pcgameri5.png', 6),
(15, 'PC Workstation i9', 9800.00, '/imagens/produtos/workstation.png', 6),
(16, 'Monitor 21 Polegadas', 899.00, '/imagens/produtos/monitor21.png', 7),
(17, 'Monitor Gamer 27 Polegadas', 1599.00, '/imagens/produtos/monitor27.png', 7),
(18, 'Monitor Ultrawide Curvo', 2500.00, '/imagens/produtos/monitorcurvo.png', 7),
(19, 'HD 1TB', 299.00, '/imagens/produtos/hd1tb.png', 8),
(20, 'HD 2TB', 450.00, '/imagens/produtos/hd2tb.png', 8),
(21, 'SSD 240GB', 199.00, '/imagens/produtos/ssd240.png', 8),
(22, 'SSD 480GB', 349.00, '/imagens/produtos/ssd480.png', 8),
(23, 'SSD NVMe 1TB', 699.00, '/imagens/produtos/ssdnvme1tb.png', 8),
(24, 'SSD M.2 NVMe 2TB', 1200.00, '/imagens/produtos/ssdnvme2tb.png', 8),
(25, 'Pen Drive 32GB', 49.00, '/imagens/produtos/pendrive32.png', 8),
(26, 'Pen Drive 64GB', 79.00, '/imagens/produtos/pendrive64.png', 8),
(27, 'HD Externo 1TB', 499.00, '/imagens/produtos/hdexterno.png', 8),
(28, 'Placa de Vídeo GTX 1660', 1899.00, '/imagens/produtos/rtx1660.png', 9),
(29, 'Placa de Vídeo RTX 3060', 2899.00, '/imagens/produtos/rtx3060.png', 9),
(30, 'Placa de Vídeo RTX 4060', 3399.00, '/imagens/produtos/rtx4080.png', 9),
(31, 'Placa de Vídeo RTX 4080', 8000.00, '/imagens/produtos/rtx4080.png', 9),
(32, 'Processador Intel i3', 699.00, '/imagens/produtos/corei3.png', 10),
(33, 'Processador Intel i5', 1199.00, '/imagens/produtos/corei5.png', 10),
(34, 'Processador Intel i7', 1999.00, '/imagens/produtos/corei7.png', 10),
(35, 'Processador Ryzen 5', 1099.00, '/imagens/produtos/ryzen5.png', 10),
(36, 'Processador Ryzen 7', 1799.00, '/imagens/produtos/ryzen7.png', 10),
(37, 'Processador AMD Ryzen 9', 3500.00, '/imagens/produtos/ryzen9.png', 10),
(38, 'Placa Mãe ASUS B450', 599.00, '/imagens/produtos/placamaeasus.png', 11),
(39, 'Placa Mãe Gigabyte B550', 799.00, '/imagens/produtos/placamaegigabyte.png', 11),
(40, 'Placa Mãe MSI X570', 1199.00, '/imagens/produtos/placamaemsi.png', 11),
(41, 'Placa Mãe ATX Z790', 1500.00, '/imagens/produtos/placamaeatx.png', 11),
(42, 'Fonte ATX', 50.00, '/imagens/produtos/fonteatx.png', 12),
(43, 'Fonte 500W', 249.00, '/imagens/produtos/fonte500w.png', 12),
(44, 'Fonte 650W', 349.00, '/imagens/produtos/fonte650w.png', 12),
(45, 'Fonte 750W', 499.00, '/imagens/produtos/fonte750w.png', 12),
(46, 'Fonte Modular 850W', 600.00, '/imagens/produtos/fonte850w.png', 12),
(47, 'Gabinete ATX', 299.00, '/imagens/produtos/gabineteatx.png', 13),
(48, 'Gabinete Gamer RGB', 499.00, '/imagens/produtos/gabinetegamer.png', 13),
(49, 'Gabinete Full Tower', 700.00, '/imagens/produtos/gabinetefull.png', 13),
(50, 'Cooler para Processador', 99.00, '/imagens/produtos/cooler.png', 14),
(51, 'Water Cooler 240mm', 399.00, '/imagens/produtos/watercooler240.png', 14),
(52, 'Water Cooler 360mm RGB', 550.00, '/imagens/produtos/watercooler360.png', 14),
(53, 'Webcam Full HD', 199.00, '/imagens/produtos/webcan.png', 15),
(54, 'Caixa de Som USB', 99.00, '/imagens/produtos/caixadesom.png', 15),
(55, 'Microfone Condensador', 299.00, '/imagens/produtos/microfone.png', 15),
(56, 'Webcam 4K', 450.00, '/imagens/produtos/webcan4k.png', 15),
(57, 'Impressora Multifuncional', 599.00, '/imagens/produtos/impressora.png', 15),
(58, 'Scanner de Mesa', 499.00, '/imagens/produtos/scanner.png', 15),
(59, 'Roteador Wi-Fi', 199.00, '/imagens/produtos/roteador.png', 15),
(60, 'Switch 8 Portas', 179.00, '/imagens/produtos/switch.png', 15),
(61, 'Cabo HDMI 2m', 39.00, '/imagens/produtos/cabohdmi.png', 15),
(62, 'Cabo de Rede CAT6', 29.00, '/imagens/produtos/caboderede.png', 15),
(63, 'Suporte para Notebook', 89.00, '/imagens/produtos/suportenotebook.png', 1),
(64, 'Estabilizador', 199.00, '/imagens/produtos/estabilizador.png', 15),
(65, 'Nobreak 1200VA', 699.00, '/imagens/produtos/nobreak.png', 15),
(66, 'Mouse Pad Gamer', 59.00, '/imagens/produtos/mousepad.png', 4);


-- ===============================
-- VENDAS
-- ===============================

INSERT INTO tb_venda (id, data, id_cliente)
VALUES (1, '2025-10-01 00:00:00', 1);

INSERT INTO tb_venda (id, data, id_cliente)
VALUES (2, '2024-10-01 00:00:00', 2);


-- ===============================
-- ITENS DA VENDA
-- ===============================

INSERT INTO tb_item_venda (id, quantidade, id_venda, id_produto)
VALUES (1, 1, 1, 1); -- Notebook i5 8GB RAM na venda 1

INSERT INTO tb_item_venda (id, quantidade, id_venda, id_produto)
VALUES (2, 1, 1, 8); -- Mouse Gamer Wireless na venda 1

INSERT INTO tb_item_venda (id, quantidade, id_venda, id_produto)
VALUES (3, 1, 1, 6); -- Teclado Mecânico RGB Pro na venda 1

INSERT INTO tb_item_venda (id, quantidade, id_venda, id_produto)
VALUES (4, 3, 2, 7); -- Teclado Mecânico RGB na venda 2

-- ===============================
-- AJUSTE FINAL DO AUTO-INCREMENT (DEPOIS DOS INSERTS!)
-- ===============================
ALTER TABLE tb_pessoa       ALTER COLUMgoN id RESTART WITH 5;
ALTER TABLE tb_role         ALTER COLUMN id RESTART WITH 3;
ALTER TABLE tb_departamento ALTER COLUMN id RESTART WITH 16;
ALTER TABLE tb_produto      ALTER COLUMN id RESTART WITH 67;
ALTER TABLE tb_venda        ALTER COLUMN id RESTART WITH 3;
ALTER TABLE tb_item_venda   ALTER COLUMN id RESTART WITH 5;