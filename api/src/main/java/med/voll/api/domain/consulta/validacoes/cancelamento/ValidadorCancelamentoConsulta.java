package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorCancelamentoConsulta implements ValidadorCancelamentoDeConsulta {

  @Autowired
  private ConsultaRepository repository;

  public void validar(DadosCancelamentoConsulta dados) {

    var dataConsulta = repository.getReferenceById(dados.idConsulta());

    var dataAgora = LocalDateTime.now();
    var diferencaEmHoras = Duration.between(dataAgora, dataConsulta.getData()).toHours();

    if(diferencaEmHoras < 24){
      throw new ValidacaoException("Prazo esgotado para o cancelamento da consulta");
    }
  }
}
