package br.com.mv.projeto.cadastro.model_entities;

import javax.persistence.*;

@Entity
@Table(name = "ALUNO_TABLE")
public class Aluno {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 70, nullable = false)
    private String nome;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Integer idade;
    @ManyToOne
    private Curso curso;

    public Aluno(Long id, String nome, String email, Integer idade, Curso curso) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.curso = curso;
    }

    public Aluno() {
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getIdade() {
        return idade;
    }
    public void setIdade(Integer idade) {
        this.idade = idade;
    }
    public Curso getCurso() {
        return curso;
    }
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}