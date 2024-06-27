package med.voll.api.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfiguration implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    //WebMvcConfigurer.super.addCorsMappings(registry);
    registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000") //endereco front-end
            .allowedMethods("GET"); //permissao
  }
}
