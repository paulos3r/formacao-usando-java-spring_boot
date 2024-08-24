package med.voll.api.domain.paciente;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import med.voll.api.domain.endereco.Endereco;

public record DadosLisagemPaciente(@NotBlank String nome,
                                   @NotBlank @Email String email,
                                   @NotBlank String cpf,
                                   Endereco endereco) {

  public DadosLisagemPaciente(Paciente paciente){
    this( paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getEndereco());
  }
}