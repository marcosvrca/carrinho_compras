-- ===============================
-- PESSOAS
-- ===============================

INSERT INTO tb_pessoa (id, email, telefone, password, role)
VALUES (1, 'joao@email.com', '9999-9999',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoOa3P5xLrZJjF8EwF0lF6Z6MZlZQ4E8xC',
        'ROLE_USER');

INSERT INTO tb_pessoa_fisica (id, nome, cpf)
VALUES (1, 'João Silva', '12345678900');


INSERT INTO tb_pessoa (id, email, telefone, password, role)
VALUES (2, 'maria@email.com', '1111-9999',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoOa3P5xLrZJjF8EwF0lF6Z6MZlZQ4E8xC',
        'ROLE_USER');

INSERT INTO tb_pessoa_fisica (id, nome, cpf)
VALUES (2, 'Maria Joaquina', '12345678978');


INSERT INTO tb_pessoa (id, email, telefone, password, role)
VALUES (3, 'empresa@email.com', '8888-8888',
        '$2a$10$7EqJtq98hPqEX7fNZaFWoOa3P5xLrZJjF8EwF0lF6Z6MZlZQ4E8xC',
        'ROLE_ADMIN');

INSERT INTO tb_pessoa_juridica (id, razao_social, cnpj)
VALUES (3, 'Tech Solutions Ltda', '1112223330001');

INSERT INTO tb_pessoa (id, email, telefone, password, role)
VALUES (4,'admin@admin.com','11999998888','$2a$10$2iy3DcqYknmfKS2PmpO.kOHwayn./YExIquR8SeuflbXcYFWX138u','ROLE_ADMIN');

INSERT INTO tb_pessoa_fisica (id, nome, cpf)
VALUES (4, 'Administrador', '04510018185');


-- ===============================
-- PRODUTOS
-- ===============================

INSERT INTO tb_produto (id, descricao, valor, image_url)
VALUES (1, 'Notebook', 4500.00, '/imagens/produtos/notebook.jpg');

INSERT INTO tb_produto (id, descricao, valor, image_url)
VALUES (2, 'Mouse', 80.00, '/imagens/produtos/mouse.jpg');

INSERT INTO tb_produto (id, descricao, valor, image_url)
VALUES (3, 'Teclado', 150.00, '/imagens/produtos/teclado.jpg');

INSERT INTO tb_produto (id, descricao, valor, image_url)
VALUES (4, 'Memoria Ram', 99.00, '/imagens/produtos/ram.jpg');

INSERT INTO tb_produto (id, descricao, valor, image_url)
VALUES (5, 'Fonte ATX', 50.00, '/imagens/produtos/fonte.jpg');

INSERT INTO tb_produto (id, descricao, valor, image_url)
VALUES (6, 'PC Gamer I5', 2599.00, '/imagens/produtos/pc.jpg');
INSERT INTO tb_produto (id, descricao, valor, image_url) VALUES
                                                             (7, 'Monitor 21 Polegadas', 899.00, '/imagens/produtos/pc.jpg'),
                                                             (8, 'Monitor Gamer 27 Polegadas', 1599.00, '/imagens/produtos/pc.jpg'),
                                                             (9, 'HD 1TB', 299.00, '/imagens/produtos/pc.jpg'),
                                                             (10, 'HD 2TB', 450.00, '/imagens/produtos/pc.jpg'),
                                                             (11, 'SSD 240GB', 199.00, '/imagens/produtos/pc.jpg'),
                                                             (12, 'SSD 480GB', 349.00, '/imagens/produtos/pc.jpg'),
                                                             (13, 'SSD NVMe 1TB', 699.00, '/imagens/produtos/pc.jpg'),
                                                             (14, 'Placa de Vídeo GTX 1660', 1899.00, '/imagens/produtos/pc.jpg'),
                                                             (15, 'Placa de Vídeo RTX 3060', 2899.00, '/imagens/produtos/pc-video.jpg'),
                                                             (16, 'Placa de Vídeo RTX 4060', 3399.00, '/imagens/produtos/pc.jpg'),
                                                             (17, 'Processador Intel i3', 699.00, '/imagens/produtos/pc.jpg'),
                                                             (18, 'Processador Intel i5', 1199.00, '/imagens/produtos/pc.jpg'),
                                                             (19, 'Processador Intel i7', 1999.00, '/imagens/produtos/pc.jpg'),
                                                             (20, 'Processador Ryzen 5', 1099.00, '/imagens/produtos/pc.jpg'),
                                                             (21, 'Processador Ryzen 7', 1799.00, '/imagens/produtos/pc.jpg'),
                                                             (22, 'Placa Mãe ASUS B450', 599.00, '/imagens/produtos/pc.jpg'),
                                                             (23, 'Placa Mãe Gigabyte B550', 799.00, '/imagens/produtos/pc.jpg'),
                                                             (24, 'Placa Mãe MSI X570', 1199.00, '/imagens/produtos/pc.jpg'),
                                                             (25, 'Memória RAM 8GB DDR4', 179.00, '/imagens/produtos/pc.jpg'),
                                                             (26, 'Memória RAM 16GB DDR4', 299.00, '/imagens/produtos/pc.jpg'),
                                                             (27, 'Memória RAM 32GB DDR4', 549.00, '/imagens/produtos/pc.jpg'),
                                                             (28, 'Fonte 500W', 249.00, '/imagens/produtos/pc.jpg'),
                                                             (29, 'Fonte 650W', 349.00, '/imagens/produtos/pc.jpg'),
                                                             (30, 'Fonte 750W', 499.00, '/imagens/produtos/pc.jpg'),
                                                             (31, 'Gabinete ATX', 299.00, '/imagens/produtos/pc.jpg'),
                                                             (32, 'Gabinete Gamer RGB', 499.00, '/imagens/produtos/pc.jpg'),
                                                             (33, 'Cooler para Processador', 99.00, '/imagens/produtos/pc.jpg'),
                                                             (34, 'Water Cooler 240mm', 399.00, '/imagens/produtos/pc.jpg'),
                                                             (35, 'Teclado Mecânico RGB', 299.00, '/imagens/produtos/pc.jpg'),
                                                             (36, 'Mouse Gamer RGB', 199.00, '/imagens/produtos/pc.jpg'),
                                                             (37, 'Headset Gamer', 249.00, '/imagens/produtos/pc.jpg'),
                                                             (38, 'Webcam Full HD', 199.00, '/imagens/produtos/pc.jpg'),
                                                             (39, 'Caixa de Som USB', 99.00, '/imagens/produtos/pc.jpg'),
                                                             (40, 'Microfone Condensador', 299.00, '/imagens/produtos/pc.jpg'),
                                                             (41, 'Notebook i5 8GB RAM', 3599.00, '/imagens/produtos/pc.jpg'),
                                                             (42, 'Notebook i7 16GB RAM', 4899.00, '/imagens/produtos/pc.jpg'),
                                                             (43, 'Notebook Gamer RTX', 6999.00, '/imagens/produtos/pc.jpg'),
                                                             (44, 'Impressora Multifuncional', 599.00, '/imagens/produtos/pc.jpg'),
                                                             (45, 'Scanner de Mesa', 499.00, '/imagens/produtos/pc.jpg'),
                                                             (46, 'Roteador Wi-Fi', 199.00, '/imagens/produtos/pc.jpg'),
                                                             (47, 'Switch 8 Portas', 179.00, '/imagens/produtos/pc.jpg'),
                                                             (48, 'Cabo HDMI 2m', 39.00, '/imagens/produtos/pc.jpg'),
                                                             (49, 'Cabo de Rede CAT6', 29.00, '/imagens/produtos/pc.jpg'),
                                                             (50, 'Pen Drive 32GB', 49.00, '/imagens/produtos/pc.jpg'),
                                                             (51, 'Pen Drive 64GB', 79.00, '/imagens/produtos/pc.jpg'),
                                                             (52, 'HD Externo 1TB', 499.00, '/imagens/produtos/pc.jpg'),
                                                             (53, 'Mouse Pad Gamer', 59.00, '/imagens/produtos/pc.jpg'),
                                                             (54, 'Suporte para Notebook', 89.00, '/imagens/produtos/pc.jpg'),
                                                             (55, 'Estabilizador', 199.00, '/imagens/produtos/pc.jpg'),
                                                             (56, 'Nobreak 1200VA', 699.00, '/imagens/produtos/pc.jpg');




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
VALUES (1, 1, 1, 1); -- Notebook na venda 1

INSERT INTO tb_item_venda (id, quantidade, id_venda, id_produto)
VALUES (2, 1, 1, 2); -- Mouse na venda 1

INSERT INTO tb_item_venda (id, quantidade, id_venda, id_produto)
VALUES (3, 1, 1, 3); -- Teclado na venda 1

INSERT INTO tb_item_venda (id, quantidade, id_venda, id_produto)
VALUES (4, 3, 2, 3); -- Teclado na venda 2


ALTER TABLE tb_venda ALTER COLUMN id RESTART WITH 3;
ALTER TABLE tb_item_venda ALTER COLUMN id RESTART WITH 5;

