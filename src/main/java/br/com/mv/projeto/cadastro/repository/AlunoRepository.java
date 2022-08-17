package br.com.mv.projeto.cadastro.repository;

import br.com.mv.projeto.cadastro.model_entities.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Page<Aluno> findByEmail(String nome, Pageable paginacao);

    Optional<Aluno> findByNome(String nome);

    Page<Aluno> findByCursoNome(String nomeCurso, Pageable paginacao);
}
