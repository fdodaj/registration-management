package al.ikubinfo.registrationmanagement.controller;

import al.ikubinfo.registrationmanagement.dto.authDtos.LoginDto;
import al.ikubinfo.registrationmanagement.security.TokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class AuthController {

    private static final String LOGIN = "login";

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    // Login form

    @GetMapping("/login")
    public ModelAndView login(@Valid LoginDto login, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("/login");
        modelAndView.addObject(LOGIN, login);
        return modelAndView;
    }

    // Login form with error
    @GetMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login.html";
    }

    @PostMapping(value = "/login")
    public ModelAndView authorize(@ModelAttribute("login") LoginDto login, BindingResult result, Model model) {
        try {
            generateToken(login.getEmail(), login.getPassword());
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("redirect:/user/all");
            return modelAndView;
        } catch (Exception e) {
            ObjectError error = new ObjectError(LOGIN, "Invalid Email or Password");
            result.addError(error);
            ModelAndView mv = new ModelAndView(LOGIN);
            mv.addObject(LOGIN, login);
            return mv;
        }
    }

    public void generateToken(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        tokenProvider.setJwt(token);
    }

    @GetMapping(value = "/signout")
    public ModelAndView logout() {
        tokenProvider.clearToken();
        return new ModelAndView("redirect:/login");
    }
}
