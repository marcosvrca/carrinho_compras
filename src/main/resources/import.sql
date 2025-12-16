-- Pessoas físicas e jurídicas
INSERT INTO tb_pessoa (id, email, telefone) VALUES (1, 'joao@email.com', '9999-9999');
INSERT INTO tb_pessoa_fisica (id, nome, cpf) VALUES (1, 'João Silva', '12345678900');
INSERT INTO tb_pessoa (id, email, telefone) VALUES (3, 'maria@email.com', '1111-9999');
INSERT INTO tb_pessoa_fisica (id, nome, cpf) VALUES (3, 'Maria Joaquina', '12345678978');

INSERT INTO tb_pessoa (id, email, telefone) VALUES (2, 'empresa@email.com', '8888-8888');
INSERT INTO tb_pessoa_juridica (id, razao_social, cnpj) VALUES (2, 'Tech Solutions Ltda', '1112223330001');
INSERT INTO tb_pessoa (id, email, telefone) VALUES (4, 'empresatech@email.com', '8888-8800');
INSERT INTO tb_pessoa_juridica (id, razao_social, cnpj) VALUES (4, 'Empresa Nacional LTDA', '7812225530001');

-- Produtos
INSERT INTO tb_produto (id, descricao, valor) VALUES (1, 'Notebook', 4500.00);
INSERT INTO tb_produto (id, descricao, valor) VALUES (2, 'Mouse', 80.00);
INSERT INTO tb_produto (id, descricao, valor) VALUES (3, 'Teclado', 150.00);
INSERT INTO tb_produto (id, descricao, valor) VALUES (4, 'Memoria Ram', 99.00);
INSERT INTO tb_produto (id, descricao, valor) VALUES (5, 'Fonte ATX', 50.00);
INSERT INTO tb_produto (id, descricao, valor) VALUES (6, 'PC Gamer I5', 2599.00);

-- Venda (cliente 1 - João)
INSERT INTO tb_venda (id, data, id_cliente) VALUES (1, '2025-10-01 00:00:00', 1);

INSERT INTO tb_venda (id, data, id_cliente) VALUES (2, '2024-10-01 00:00:00', 2);


-- Itens da venda
INSERT INTO tb_item_venda (id, quantidade, id_venda, id_produto) VALUES (1, 1, 1, 1);
INSERT INTO tb_item_venda (id, quantidade, id_venda, id_produto) VALUES (2, 1, 1, 2);
INSERT INTO tb_item_venda (id, quantidade, id_venda, id_produto) VALUES (3, 1, 1, 3);
INSERT INTO tb_item_venda (id, quantidade, id_venda, id_produto) VALUES (4, 3, 2, 3);

ALTER TABLE tb_pessoa ALTER COLUMN id RESTART WITH 5;
ALTER TABLE tb_pessoa_fisica ALTER COLUMN id RESTART WITH 5;
ALTER TABLE tb_pessoa_juridica ALTER COLUMN id RESTART WITH 5;
ALTER TABLE tb_produto ALTER COLUMN id RESTART WITH 7;
ALTER TABLE TB_VENDA ALTER COLUMN ID RESTART WITH 3;



