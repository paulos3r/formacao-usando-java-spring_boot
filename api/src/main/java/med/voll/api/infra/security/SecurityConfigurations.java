package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

  @Autowired
  private SecurityFilter securityFilter;

  // desabilitar bloqueio padrao de seguranca para configurar nosso proprio padrao
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    return  http.csrf(AbstractHttpConfigurer::disable) // csrf->csrf.disable()
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(req->{
              req.requestMatchers(HttpMethod.POST, "/login").permitAll(); // permitir a tela de login autorizacao somente a tela de login somente o post
              req.anyRequest().authenticated(); // qualquer ouro precisa de autorizacao
            })
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // ordem dos filtro a ser chamado pois o spring tambem tem esse filtro, tem que ter a ordem de execulcao
            .build();
  }
    // como configurar
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
    return configuration.getAuthenticationManager();
  }
    // hash de senha
  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder(); // formatar a senha com cliptografia
  }
}