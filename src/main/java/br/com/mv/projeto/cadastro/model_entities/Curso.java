package br.com.mv.projeto.cadastro.model_entities;

import javax.persistence.*;

@Entity
@Table(name = "CURSO_TABLE")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, nullable = false)
    private String nome;

    @Column(length = 150, nullable = false)
    private String coordenador;

    @Column(nullable = false)
    private int duracao;

    public Curso(Long id, String nome, String coordenador, int duracao) {
        this.id = id;
        this.nome = nome;
        this.coordenador = coordenador;
        this.duracao = duracao;
    }

    public Curso() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(String coordenador) {
        this.coordenador = coordenador;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
}
