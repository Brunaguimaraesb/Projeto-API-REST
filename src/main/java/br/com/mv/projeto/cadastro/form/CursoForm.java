package br.com.mv.projeto.cadastro.form;

import br.com.mv.projeto.cadastro.model_entities.Curso;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CursoForm {

    private Long id;

    @NotNull @NotEmpty @Length(min = 5)
    private String nome;

    @NotNull @NotEmpty @Length(min = 10)
    private String coordenador;

    @NotNull
    private int duracao;

    public Curso converte(){
        return new Curso(id, nome, coordenador, duracao);
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
