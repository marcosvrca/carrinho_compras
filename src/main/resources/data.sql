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

