package br.com.mv.projeto.cadastro.form;

import br.com.mv.projeto.cadastro.model_entities.Aluno;
import br.com.mv.projeto.cadastro.model_entities.Curso;
import br.com.mv.projeto.cadastro.service.CursoService;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AlunoForm {

    private Long id;

    @NotNull @NotEmpty @Length(min = 5)
    private String nome;
    @NotNull @NotEmpty @Length(min = 10)
    private String email;
    @NotNull
    private Integer idade;
    @NotNull @NotEmpty @Length(min = 5)
    private String nomeCurso;

    public Aluno converter(CursoService cursoService) {
        Curso curso = cursoService.findByNome(nomeCurso);
        return new Aluno(id, nome, email, idade, curso);
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

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

}
