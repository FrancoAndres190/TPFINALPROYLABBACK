package com.gymapp.gym.security.Filter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapp.gym.security.AuthCredentials;
import com.gymapp.gym.security.TokenUtils;
import com.gymapp.gym.security.Impl.UserDetailImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private TokenUtils tokenUtils;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        AuthCredentials authCredentials = new AuthCredentials();

        try{

            authCredentials = new ObjectMapper().readValue(
                    request.getReader(),
                    AuthCredentials.class);

            System.out.println(" Email: " + authCredentials.getEmail());
            System.out.println(" Password: " + authCredentials.getPassword());

        }catch (IOException e) {
            System.out.println("Error al leer authCredential - attemptAuthentication");
        }

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getEmail(),
                authCredentials.getPassword()
        );

        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserDetailImpl userDetails = (UserDetailImpl) authResult.getPrincipal();

        String token = tokenUtils.createToken(
                userDetails.getUserID(),
                userDetails.getFirstName(),
                userDetails.getUsername(),
                userDetails.isMembershipActive(),
                new HashSet<>(userDetails.getAuthorities()));

        response.addHeader("Authorization", "Bearer " + token);

        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("token", token);

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }

}
