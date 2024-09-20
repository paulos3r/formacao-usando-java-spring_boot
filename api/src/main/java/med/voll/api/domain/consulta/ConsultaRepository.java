package med.voll.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
  Boolean existsByMedicoIdandData(Long aLong, LocalDateTime data);

  Boolean existsByPaceienteIdAndDataBetween(Long aLong, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
}