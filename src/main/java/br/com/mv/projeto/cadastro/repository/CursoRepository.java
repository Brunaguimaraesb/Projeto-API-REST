package br.com.mv.projeto.cadastro.repository;

import br.com.mv.projeto.cadastro.model_entities.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNome(String nome);

    Page<Curso> findByCoordenador(String coordenador, Pageable paginacao);
}
