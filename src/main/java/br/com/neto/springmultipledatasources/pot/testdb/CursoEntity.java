package br.com.neto.springmultipledatasources.pot.testdb;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@SuppressWarnings({"unused", "JpaDataSourceORMInspection"})
@Entity
@Table(name = "tbl_curso", schema = "test", catalog = "test")
public class CursoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    @Basic
    private String nome;
    @Basic
    @Column(name = "data_evento")
    private Timestamp dataEvento;
    @Basic
    private Object evento;
    @Basic
    @Column(name = "aluno_cpf")
    private String alunoCpf;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Timestamp getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Timestamp dataEvento) {
        this.dataEvento = dataEvento;
    }

    public Object getEvento() {
        return evento;
    }

    public void setEvento(Object evento) {
        this.evento = evento;
    }

    public String getAlunoCpf() {
        return alunoCpf;
    }

    public void setAlunoCpf(String alunoCpf) {
        this.alunoCpf = alunoCpf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CursoEntity that = (CursoEntity) o;
        return id == that.id && Objects.equals(nome, that.nome) && Objects.equals(dataEvento, that.dataEvento) && Objects.equals(evento, that.evento) && Objects.equals(alunoCpf, that.alunoCpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, dataEvento, evento, alunoCpf);
    }
}
