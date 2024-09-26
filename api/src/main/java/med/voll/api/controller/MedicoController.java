package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

  @Autowired
  private MedicoRepository repository;

  @PostMapping
  @Transactional
  public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){
    var medico=new Medico(dados);

    repository.save(medico);
    //Precisa esta aqui pois nao retorna o id
    var dto=new DadosDetalhamentoMedico(medico);

    var uri=uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri(); // para salvar precisamos passar o caminho e tambem o medico que foi criado 201

    return ResponseEntity.created(uri).body(dto);
  }

  @GetMapping
  public ResponseEntity<Page<DadosListagemMedico>> listarMedico(@PageableDefault(size=10,page=0,sort={"nome"}) Pageable page ){
    //return repository.findAll( page ).map(DadosListagemMedico::new);
    var pages = repository.findAllByAtivoTrue( page ).map( DadosListagemMedico::new );
    return ResponseEntity.ok( pages );
  }

  @GetMapping("/{id}")
  public ResponseEntity detalharMedico(@PathVariable Long id){
    var medico = repository.getReferenceById(id);

    return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
  }

  @PutMapping
  @Transactional
  public ResponseEntity atualizarMedico(@RequestBody @Valid DadosAtualizacaoMedicos dados){
    var medico = repository.getReferenceById(dados.id());
    medico.atualizarInformacoes(dados);

    return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity excluirMedico(@PathVariable Long id){
    // repository.deleteById( id );
    var medico = repository.getReferenceById(id);
    medico.excluir();  // excluir logicamente

    return ResponseEntity.noContent().build();
  }
}
