package com.gymapp.gym.security.Filter;
import com.gymapp.gym.security.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private TokenUtils tokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //Obtenemos la autorizacion del header
        String bearerToken = request.getHeader("Authorization");

        //Verificamos que no sea nulo y que empiece con 'Bearer'
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {

            //Dejamos solo el token
            String token = bearerToken.replace("Bearer ", "");

            //Autenticamos
            UsernamePasswordAuthenticationToken usernamePAT = tokenUtils.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(usernamePAT);
        }

        filterChain.doFilter(request, response);

    }
}
