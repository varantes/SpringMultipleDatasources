# noinspection SqlResolveForFile

use mydb;

create table if not exists tbl_pessoa
(
    id   int auto_increment
        primary key,
    cpf  varchar(11)  not null,
    name varchar(250) not null,
    constraint tbl_first_pk2
        unique (cpf)
);

INSERT INTO mydb.tbl_pessoa (id, cpf, name) VALUES (1, '00000000001', 'valdemar');
INSERT INTO mydb.tbl_pessoa (id, cpf, name) VALUES (2, '00000000002', 'marcela');

use test;

create table if not exists tbl_curso
(
    id          int auto_increment
        primary key,
    nome        varchar(150)                                          not null,
    data_evento datetime                                              not null,
    evento      set ('MATRICULAR', 'CANCELAR', 'TRANCAR', 'CONCLUIR') not null,
    aluno_cpf   varchar(11)                                           not null
);

INSERT INTO test.tbl_curso (id, nome, data_evento, evento, aluno_cpf) VALUES (1, 'Calculo 1', '1985-02-18 09:25:00', 'MATRICULAR', '00000000001');
INSERT INTO test.tbl_curso (id, nome, data_evento, evento, aluno_cpf) VALUES (2, 'Calculo 1', '1985-06-30 18:00:00', 'CONCLUIR', '00000000001');
INSERT INTO test.tbl_curso (id, nome, data_evento, evento, aluno_cpf) VALUES (3, 'Calculo 1', '1999-03-10 00:00:00', 'MATRICULAR', '00000000002');

