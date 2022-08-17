package br.com.mv.projeto.cadastro.dto;

import br.com.mv.projeto.cadastro.model_entities.Curso;
import org.springframework.data.domain.Page;

public class CursoDto {

    private Long id;
    private String nome;
    private String coordenador;
    private int duracao;

    public CursoDto(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
        this.coordenador = curso.getCoordenador();
        this.duracao = curso.getDuracao();

    }

    public static Page<CursoDto> converte(Page<Curso> listCursos) {
        return listCursos.map(CursoDto::new);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCoordenador() {
        return coordenador;
    }

    public int getDuracao() {
        return duracao;
    }

}
