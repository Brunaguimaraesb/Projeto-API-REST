package br.com.mv.projeto.cadastro.controller;

import br.com.mv.projeto.cadastro.dto.AlunoDto;
import br.com.mv.projeto.cadastro.form.AlunoForm;
import br.com.mv.projeto.cadastro.model_entities.Aluno;
import br.com.mv.projeto.cadastro.model_entities.Curso;
import br.com.mv.projeto.cadastro.repository.AlunoRepository;
import br.com.mv.projeto.cadastro.service.AlunoService;
import br.com.mv.projeto.cadastro.service.CursoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoService cursoService;

    @GetMapping
    @Cacheable(value = "listaAlunos")
    public Page<AlunoDto> buscarAlunos(String email, @PageableDefault(page = 0, size = 2, sort = "nome", direction = Sort.Direction.ASC) Pageable paginacao){

        if(email != null){
            return AlunoDto.converter(alunoService.searchAlunoByEmail(email, paginacao));

        }
        return AlunoDto.converter(alunoService.getAlunos(paginacao));
    }

    @GetMapping("/filter/curso")
    @Cacheable(value = "listaAlunos")
    public Page<AlunoDto> buscarCursos(String nomeCurso, @PageableDefault(page = 0, size = 2, sort = "nome", direction = Sort.Direction.ASC) Pageable paginacao){

        if(nomeCurso != null){
            return AlunoDto.converter(alunoService.searchAlunoByNomeCurso(nomeCurso, paginacao));
        }
        return AlunoDto.converter(alunoService.getAlunos(paginacao));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> detalhar(@PathVariable Long id) {

        Optional<Aluno> alunoOptional = alunoRepository.findById(id);

        if(alunoOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(alunoRepository.findById(id));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O aluno não existe!");
    }


    @PostMapping
    @CacheEvict(value = "listaAlunos", allEntries = true)
    @Transactional
    public ResponseEntity<Object> cadastrar (@RequestBody @Valid AlunoForm alunoForm) {

        Optional<Curso> optionalCurso = cursoService.findByNomeOptional(alunoForm.getNomeCurso());

        if (!optionalCurso.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("O curso não existe, não foi possivel cadastrar o aluno");
        }
        Optional<Aluno> optionalAluno = alunoService.getAluno(alunoForm.getNome());

        if(!optionalAluno.isPresent()){
            Aluno aluno = alunoForm.converter(cursoService);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(alunoService.saveAluno(aluno));

        } else if (!(Objects.equals(optionalAluno.get().getCurso().getNome(), alunoForm.getNomeCurso()))) {
        Aluno aluno = alunoForm.converter(cursoService);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alunoService.saveAluno(aluno));
    }

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
            .body("Olá, o aluno já esta cadastrado/O curso não existe!");
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "listaAlunos", allEntries = true)
    @Transactional
    public  ResponseEntity<Object> delete(@PathVariable Long id) {

        Optional<Aluno> alunoOptional = alunoService.getAlunosById(id);

        if(alunoOptional.isPresent()) {
            alunoService.deleteAluno(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Aluno deletado com sucesso");
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Aluno não encontrado!");

    }

    @PutMapping("/{id}")
    @CacheEvict(value = "listaAlunos", allEntries = true)
    @Transactional
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid  AlunoForm alunoForm){

        Optional<Aluno> optionalAluno = alunoService.getAlunosById(id);
        Optional<Curso> optionalCurso = cursoService.findByNomeOptional(alunoForm.getNomeCurso());

        if(!optionalAluno.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Aluno não cadastrado!");
        }else if(!optionalCurso.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Curso não cadastrado!");
        }

        Aluno aluno = new Aluno();
        BeanUtils.copyProperties(alunoForm, aluno);
        aluno.setCurso(optionalCurso.get());
        aluno.setId(optionalAluno.get().getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alunoService.saveAluno(aluno));
    }


}
