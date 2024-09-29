package med.voll.api.controller;

import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

  @Autowired
  private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

  @MockBean
  private AgendaDeConsultas agendaDeConsultas;

  @Test
  @DisplayName("Deveria devolver codigo http 400 quando informações invalidas")
  @WithMockUser
  void agendar_cenario1() throws Exception {
    var response = mvc.perform(post("/consultas").contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andReturn().getResponse(); // pegar a resposta
    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value()); // verificar se status e 400
  }

  @Test
  @DisplayName("Deveria devolver codigo http 200 quando informações validas")
  @WithMockUser
  void agendar_cenario2() throws Exception {

    var data = LocalDateTime.now().plusHours(1);
    var especialidade = Especialidade.CARDIOLOGIA;
    var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l,5l,data);

    when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);

    var response = mvc
            .perform(
                    post("/consultas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(dadosAgendamentoConsultaJson.write(
                                    new DadosAgendamentoConsulta(2l,5l,data,especialidade)
                            ).getJson())
            )
            .andReturn().getResponse(); // pegar a resposta

    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value()); // verificar se status e 200

    var jsonEsperado = dadosDetalhamentoConsultaJson.write( dadosDetalhamento ).getJson();

    assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
  }

}