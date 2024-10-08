package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  private static final String ISSUER = "API Voll.med";

  public String gerarToken(Usuario usuario){
    try {
      var algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
              .withIssuer(ISSUER)
              .withSubject(usuario.getLogin())
              .withExpiresAt(dataExpiracao())
              .sign(algorithm);
    } catch (JWTCreationException exception){
      // Invalid Signing configuration / Couldn't convert Claims.
      throw new RuntimeException("Erro ao gerar token jwt", exception);
    }
  }

  public String getSubject(String tokenJWT){
    try {
      var algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
              // specify any specific claim validations
              .withIssuer(ISSUER)
              // reusable verifier instance
              .build()
              .verify(tokenJWT)
              .getSubject();

    } catch (JWTVerificationException exception){
      // Invalid signature/claims
      throw new RuntimeException("Token jwt inválido ou expirado!" + tokenJWT);
    }
  }

  private Instant dataExpiracao() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
