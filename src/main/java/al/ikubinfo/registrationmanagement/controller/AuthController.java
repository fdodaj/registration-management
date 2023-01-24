package al.ikubinfo.registrationmanagement.controller;

import al.ikubinfo.registrationmanagement.dto.authDtos.LoginDto;
import al.ikubinfo.registrationmanagement.security.JWTFilter;
import al.ikubinfo.registrationmanagement.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
@RestController
public class AuthController {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * User login request
     *
     * @param loginRequest  email, password
     * @return ModelAndView
     */
    @PostMapping (value = "/api/login")
    public ResponseEntity<String> authorize(@RequestBody LoginDto loginRequest) {
        String token = generateToken(loginRequest.getEmail(), loginRequest.getPassword());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER,
                "Bearer " + token);
        return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
    }
    /**
     * Destroys previously created token
     *
     * @return ModelAndView
     */
    @GetMapping(value = "/logout")
    public ResponseEntity<Void> logout() {
        tokenProvider.clearToken();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Generates one time user token
     *
     * @param username username
     * @param password password
     */
    public String generateToken(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        tokenProvider.setJwt(token);
        return token;
    }


}
