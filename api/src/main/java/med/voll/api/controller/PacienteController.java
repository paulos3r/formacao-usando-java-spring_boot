package med.voll.api.controller;

import med.voll.api.domain.paciente.DadosLisagemPaciente;
import med.voll.api.domain.paciente.PacrienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

  @Autowired
  private PacrienteRepository repository;

  @GetMapping
  public ResponseEntity<Page<DadosLisagemPaciente>> obterListaPacientes(@PageableDefault(size=10,page=0,sort={"nome"}) Pageable page){

    var paciente = repository.findAll(page).map(DadosLisagemPaciente::new);
    paciente.stream().collect(Collectors.toList());

    System.out.println(paciente);
    return ResponseEntity.ok(paciente);
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity desativarCliente(@PathVariable Long id){
    var paciente= repository.getReferenceById(id);

    paciente.excluir();

    return ResponseEntity.noContent().build();
  }
}
