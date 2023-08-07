
package com.codeOpen.biblioteca.security.jwt;



import com.codeOpen.biblioteca.security.secDto.JwtDto;
import com.codeOpen.biblioteca.security.secEntity.UsuarioPrincipal;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.text.ParseException;
import javax.crypto.spec.SecretKeySpec;

// genera el token, más unos métodos de validación para ver que esté bien formado,
// que no esté expirado, etc.
@Component
public class JwtProvider {
    
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private String expiration;
    
    
    // Generar token de acceso
    public String generateToken(Authentication authentication){
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
        
        return Jwts.builder().setSubject(usuarioPrincipal.getUsername()) //indicamos el sujeto del token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration))) // expiración
                .signWith(getSecret(secret)) // firmamos, firma encriptada con getSecret() 
                .compact();
    }
    
    
    // Obtener firma del token
    private Key getSecret(String secret){
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }
    
    
    // Obtener nombre de usuario con el token
    public String getNombreUsuarioFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token).getBody().getSubject(); 
    }
    
    
    // Validar el token de acceso
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("token mal formado");
        }catch (UnsupportedJwtException e) {
            logger.error("token no soportado");
        }catch (ExpiredJwtException e) {
            logger.error("token expirado");
        }catch (IllegalArgumentException e) {
            logger.error("token vacio");
        }catch (SignatureException e) {
            logger.error("fail en la firma");
        }
        return false;
    }
    
    /*public String refreshToken(JwtDto jwtDto) throws ParseException {
        try {
            Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJwt(jwtDto.get)
        } catch (Exception e) {
        }
    }*/
    
}
