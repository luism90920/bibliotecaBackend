
package com.codeOpen.biblioteca.security.jwt;

//  se encarga de comprobar si hay un token válido, sino va a devolver 
//  una respuesta 401(no autorizado)

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {
    
    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.error("fail en el método commence");
        
        // HttpServletResponse.SC_UNAUTHORIZED indique que se está enviado un código de
        // estado 401
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"no autorizado");
    }
    
}
