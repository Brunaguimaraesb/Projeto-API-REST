package br.com.mv.projeto.cadastro.controller;

import br.com.mv.projeto.cadastro.dto.CursoDto;
import br.com.mv.projeto.cadastro.form.CursoForm;
import br.com.mv.projeto.cadastro.model_entities.Aluno;
import br.com.mv.projeto.cadastro.model_entities.Curso;
import br.com.mv.projeto.cadastro.repository.CursoRepository;
import br.com.mv.projeto.cadastro.service.AlunoService;
import br.com.mv.projeto.cadastro.service.CursoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    CursoService cursoService;

    @Autowired
    AlunoService alunoService;

    @Autowired
    CursoRepository cursoRepository;

    @GetMapping
    @Cacheable(value = "listaCursos")
    public Page<CursoDto> buscarCursos(String coordenador, @PageableDefault(page = 0, size = 2, sort = "nome", direction = Sort.Direction.ASC) Pageable paginacao) {

        if (coordenador != null) {
            return CursoDto.converte(cursoService.searchCursoByCoordenador(coordenador, paginacao));
        }

        return CursoDto.converte(cursoService.getCursos(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> detalhar(@PathVariable Long id) {

        Optional<Curso> alunoOptional = cursoRepository.findById(id);

        if(alunoOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(cursoRepository.findById(id));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O curso não existe!");
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "listaCursos", allEntries = true)
    public ResponseEntity<Object> salvar(@RequestBody @Valid CursoForm cursoForm) {

        Optional<Curso> optionalCurso = cursoService.findByNomeOptional(cursoForm.getNome());

        if (!optionalCurso.isPresent()) {
            Curso curso = cursoForm.converte();
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(curso));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Olá, o curso já está cadastrado!");
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "listaCursos", allEntries = true)
    @Transactional
    public ResponseEntity<Object> deletar(@PathVariable Long id) {

        Optional<Curso> cursoOptional = cursoService.getCursoById(id);


        if(!cursoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso não encontrado");
        }

        Pageable pageable = PageRequest.of(0, 20);
        Page<Aluno> listaAlunos = alunoService.searchAlunoByNomeCurso(cursoOptional.get().getNome(), pageable);

        if(!listaAlunos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso não pode ser deletado pois existe aluno cadastrado no curso!");
        }

        cursoService.deleteCurso(id);
        return ResponseEntity.status(HttpStatus.OK).body("Curso deletado com sucesso!");

    }

    @PutMapping("/{id}")
    @CacheEvict(value = "listaCursos", allEntries = true)
    @Transactional
    public ResponseEntity<Object> atualizarCurso(@PathVariable Long id, @RequestBody @Valid CursoForm cursoForm) {
        Optional<Curso> optionalCurso= cursoService.getCursoById(id);

        if (!optionalCurso.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não cadastrado!");
        }

        Curso curso = new Curso();
        BeanUtils.copyProperties(cursoForm, curso);
        curso.setId(optionalCurso.get().getId());

        return ResponseEntity.status(HttpStatus.OK).body(cursoService.save(curso));
    }
}
