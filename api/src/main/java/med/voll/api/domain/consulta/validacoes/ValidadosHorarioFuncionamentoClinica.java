package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadosHorarioFuncionamentoClinica implements ValidadorAgendamentoDeConsulta{

  public void validar(DadosAgendamentoConsulta dados){
    var dataConsulta = dados.data();

    var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SATURDAY);
    var antesDaAbertudaDaClinica = dataConsulta.getHour() < 7;
    var depoisDoEncerramentoDaClinica = dataConsulta.getHour()>18;

    if(domingo || antesDaAbertudaDaClinica || depoisDoEncerramentoDaClinica){
      throw new ValidacaoException("Consulta fora do horario do funcionamento");
    }
  }
}
