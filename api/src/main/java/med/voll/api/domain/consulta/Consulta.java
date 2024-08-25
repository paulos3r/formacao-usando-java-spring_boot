package med.voll.api.domain.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Table(name="consultas")
@Entity(name = "Consulta")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch =FetchType.LAZY)
  @JoinColumn(name = "medico_id")
  private Medico medico;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="paciente_id")
  private Paciente paciente;

  private LocalDateTime data;

  private boolean ativo;

  public void cancelar(){
    this.ativo = !this.ativo;
  }

  public Long getId() {
    return id;
  }

  public Medico getMedico() {
    return medico;
  }

  public Paciente getPaciente() {
    return paciente;
  }

  public LocalDateTime getData() {
    return data;
  }
}
