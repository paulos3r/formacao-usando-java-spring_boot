package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacrienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {
  @Autowired
  private ConsultaRepository repository;

  @Autowired
  private MedicoRepository medicoRepository;

  @Autowired
  private PacrienteRepository pacrienteRepository;

  @Autowired
  private List<ValidadorAgendamentoDeConsulta> validadores; // cria um lista de todos os validadores que implementa a classe para evitar instanciar uma por uma SOLID

  @Autowired
  private List<ValidadorCancelamentoConsulta> validadorCancelamento;
  public DadosDetalhamentoConsulta agendar( DadosAgendamentoConsulta dados){

    if (!pacrienteRepository.existsById(dados.idPaciente())){
      throw new ValidacaoException("id do paciente informado nao existe");
    }

    if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
      throw new ValidacaoException("id medico nao existe");
    }

    validadores.forEach(v->v.validar(dados));

    var paciente = pacrienteRepository.getReferenceById(dados.idPaciente());
    var medico = escolherMedico(dados);

    if( medico == null ){
      throw new ValidacaoException("Nao existe medico disponivel para essa data");
    }

    var consulta = new Consulta(null, medico, paciente, dados.data(),null);
    repository.save(consulta);

    return new DadosDetalhamentoConsulta(consulta);
  }

  private Medico escolherMedico(DadosAgendamentoConsulta dados) {
    if(dados.idMedico() != null){
      return medicoRepository.getReferenceById(dados.idMedico());
    }

    if (dados.especialidade() == null){
      throw new ValidacaoException("Especialidade é obrigatorio quando medico nao for escolhido");
    }
    return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
  }

  public void cancelar(DadosCancelamentoConsulta dados) {
    if (!repository.existsById(dados.idConsulta())) {
      throw new ValidacaoException("Id da consulta informado não existe!");
    }

    validadorCancelamento.forEach(e->e.validar(dados));

    var consulta = repository.getReferenceById(dados.idConsulta());
    consulta.cancelar(dados.motivo());
  }
}
