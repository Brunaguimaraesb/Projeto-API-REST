package br.com.mv.projeto.cadastro.service;

import br.com.mv.projeto.cadastro.model_entities.Aluno;
import br.com.mv.projeto.cadastro.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;

    public Page<Aluno> getAlunos(Pageable paginacao) {
        return alunoRepository.findAll(paginacao);
    }

    public Page<Aluno> searchAlunoByEmail(String email, Pageable paginacao) {
        return alunoRepository.findByEmail(email, paginacao);
    }

    public Page<Aluno> searchAlunoByNomeCurso(String nomeCurso, Pageable paginacao) {
        return alunoRepository.findByCursoNome(nomeCurso, paginacao);
    }


    public Aluno saveAluno(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public Optional<Aluno> getAlunosById(Long id) {
        return alunoRepository.findById(id);
    }

    public void deleteAluno(Long id) {
        alunoRepository.deleteById(id);
    }

    public Optional<Aluno> getAluno(String nome) {
        return alunoRepository.findByNome(nome);
    }
}
