package med.voll.api.domain.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacrienteRepository extends JpaRepository<Paciente, Long> {

  @Query("""
          select p.ativo
          from Paciente p
          where p.id = :id
          """)
  Boolean findAtivoById(Long id);
  // Page<Paciente> findByPacienteOrderByNomeAsc(Pageable page);
}
