package ru.maxima.spring_security_demo.controllers;


import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maxima.spring_security_demo.dto.PersonDTO;
import ru.maxima.spring_security_demo.model.Person;
import ru.maxima.spring_security_demo.security.PersonDetails;
import ru.maxima.spring_security_demo.service.PeopleService;
import ru.maxima.spring_security_demo.util.JWTUtil;
import ru.maxima.spring_security_demo.validation.PersonValidator;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final PeopleService peopleService;
    private final JWTUtil jwtUtil;
    private final ModelMapper mapper;
    private final PersonValidator personValidator;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthController(PeopleService peopleService, JWTUtil jwtUtil, ModelMapper mapper, PersonValidator personValidator, AuthenticationManager authenticationManager) {
        this.peopleService = peopleService;
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
        this.personValidator = personValidator;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody PersonDTO authDTO) {

        UsernamePasswordAuthenticationToken userToken =
                new UsernamePasswordAuthenticationToken(
                        authDTO.getUsername(), authDTO.getPassword()
                );

        try {
            authenticationManager.authenticate(userToken);
        } catch (Exception e) {
            return Map.of("error", "incorrect login or password");
        }



        String token = jwtUtil.generateToken(authDTO.getUsername());

        return Map.of("jwt-token", token);
    }

    @PostMapping("/registration")
    public Map<String, String> registration(@RequestBody @Valid  PersonDTO personDTO,
                                            BindingResult bindingResult) {

        Person person = peopleService.convertDTOToPerson(personDTO);

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return Map.of("message", bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        peopleService.savePerson(person);

        String token = jwtUtil.generateToken(person.getUsername());

        return Map.of("jwt-token", token);
    }

    @GetMapping("/show")
    public String showAuthenticatedUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails principal = (PersonDetails) authentication.getPrincipal();

        return principal.getUsername();

    }
}
