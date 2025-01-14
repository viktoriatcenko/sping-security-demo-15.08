package ru.maxima.spring_security_demo.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.maxima.spring_security_demo.model.Person;
import ru.maxima.spring_security_demo.service.PeopleService;
import ru.maxima.spring_security_demo.validation.PersonValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {


    private final PersonValidator personValidator;
    private final PeopleService peopleService;

    public AuthController(PersonValidator personValidator, PeopleService peopleService) {
        this.personValidator = personValidator;
        this.peopleService = peopleService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("person", new Person());
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("person") Person person,
                           BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }

        peopleService.savePerson(person);

        return "redirect:/auth/login";
    }


}
