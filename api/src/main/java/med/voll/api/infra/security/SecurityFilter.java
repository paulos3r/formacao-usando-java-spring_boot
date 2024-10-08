package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UsuarioRepository repository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    var tokenJWT = recuperarToken(request); // recupera o token do cabecalho

    if (tokenJWT != null) {
      var subject = tokenService.getSubject(tokenJWT); // valida o token
      var usuario = repository.findByLogin(subject); // pegar o usuario

      var authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);
      System.out.println("logado");
    }

    filterChain.doFilter(request, response); // bloqueia, intercepita a requisicao
  }

  private String recuperarToken(HttpServletRequest request) {
    var authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader != null){
      return authorizationHeader.replace("Bearer ", "").trim();
    }

    return null;
  }
}
