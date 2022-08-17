package br.com.mv.projeto.cadastro.service;

import br.com.mv.projeto.cadastro.model_entities.Curso;
import br.com.mv.projeto.cadastro.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CursoService {

    @Autowired
    CursoRepository cursoRepository;
    public Curso findByNome(String nomeCurso) {
        return cursoRepository.findByNome(nomeCurso);
    }

    public Optional<Curso> findByNomeOptional(String nomeCurso) {
        return Optional.ofNullable(cursoRepository.findByNome(nomeCurso));
    }

    public Page<Curso> searchCursoByCoordenador(String coordenador, Pageable paginacao) {
        return cursoRepository.findByCoordenador(coordenador, paginacao);
    }

    public Page<Curso> getCursos(Pageable paginacao) {
        return cursoRepository.findAll(paginacao);
    }

    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Optional<Curso> getCursoById(Long id) {
        return cursoRepository.findById(id);
    }

    public void deleteCurso(Long id) {
        cursoRepository.deleteById(id);
    }

}
