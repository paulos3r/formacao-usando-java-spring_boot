package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Entity
@Table(name = "pacientes")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nome;
  private String telefone;
  private String cpf;
  private String email;
  private boolean ativo;

  @Embedded
  private Endereco endereco;

  public Paciente(DadosCadastroPaciente paciente) {
    this.nome = paciente.nome();
    this.cpf = paciente.cpf();
    this.email = paciente.email();
    endereco = new Endereco(paciente.dadosEndereco());
  }

  public void excluir(){
    this.ativo = !this.ativo;
  }

  public Long getId() {
    return id;
  }

  public String getNome() {
    return nome;
  }

  public String getTelefone() {
    return telefone;
  }

  public String getCpf() {
    return cpf;
  }

  public String getEmail() {
    return email;
  }

  public Endereco getEndereco() {
    return endereco;
  }

  public boolean isAtivo() {
    return ativo;
  }
}
