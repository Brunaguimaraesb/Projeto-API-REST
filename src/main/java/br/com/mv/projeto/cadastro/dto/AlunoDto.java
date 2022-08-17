package br.com.mv.projeto.cadastro.dto;

import br.com.mv.projeto.cadastro.model_entities.Aluno;
import br.com.mv.projeto.cadastro.model_entities.Curso;
import org.springframework.data.domain.Page;

public class AlunoDto {

    private Long id;
    private String nome;
    private String email;
    private Integer idade;
    private Curso curso;

    public AlunoDto(Aluno aluno) {
       this.id = aluno.getId();
       this.nome = aluno.getNome();
       this.email = aluno.getEmail();
       this.idade = aluno.getIdade();
       this.curso = aluno.getCurso();
    }
    public static Page<AlunoDto> converter(Page<Aluno> listAlunos) {
        return listAlunos.map(AlunoDto::new);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Integer getIdade() {
        return idade;
    }

    public Curso getCurso() {
        return curso;
    }
}
