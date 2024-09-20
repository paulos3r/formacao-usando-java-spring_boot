package med.voll.api.domain.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacrienteRepository extends JpaRepository<Paciente, Long> {

  Boolean findAtivoById(Long aLong);
  // Page<Paciente> findByPacienteOrderByNomeAsc(Pageable page);
}
